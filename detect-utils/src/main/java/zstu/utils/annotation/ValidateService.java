package zstu.utils.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zstu.utils.common.DateUtils;
import zstu.utils.common.StringUtil;
import zstu.utils.constants.ErrorCodesEnum;

import java.lang.reflect.Field;


/**
 * 注解解析
 *
 * @author Aning
 */
public class ValidateService {

    private static Logger logger = LoggerFactory.getLogger(ValidateService.class);


    public ValidateService() {
        super();
    }

    public static int valid(int method, Object[] objects) {
        int ret = 0;
        for (Object object : objects) {
            ret = valid(method, object);
            if (ret > 0) {
                return ret;
            }
        }
        return ret;
    }

    //解析的入口

    /**
     * method,1:查询类,2:新增类,3:修改类,4:删除类
     *
     * @param method
     * @param object
     * @return
     */
    public static int valid(int method, Object object) {

        int ret = 0;
        try {
            //获取object的类型
            Class<? extends Object> clazz = object.getClass();
            //获取该类型声明的成员
            Field[] fields = clazz.getDeclaredFields();
            //遍历属性
            for (Field field : fields) {
                //对于private私有化的成员变量，通过setAccessible来修改器访问权限
                field.setAccessible(true);
                ret = validate(method, field, object);
                //重新设置会私有权限
                field.setAccessible(false);
                if (ret > 0) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ErrorCodesEnum.ERROR_CODE_SERVER_ERROR.getCode();
        }

        return ret;
    }


    private static int validate(int method, Field field, Object object) throws Exception {

        String description;
        Object value;

        //获取对象的成员的注解信息
        PropertyValidation PropertyValidation = field.getAnnotation(PropertyValidation.class);
        value = field.get(object);

        if (PropertyValidation == null) {
            return 0;
        }

        description = "".equals(PropertyValidation.description()) ? field.getName() : PropertyValidation.description();

        /*************注解解析工作开始******************/
        if (!PropertyValidation.nullable() && method == 2) {//新增类接口才判断非空
            if (value == null || StringUtil.isEmpty(value.toString())) {
                logger.info(description + "不能为空");
                return ErrorCodesEnum.ERROR_CODE_PARAM_NULL.getCode();
            }
        }
        if (value != null && StringUtil.notEmpty(value.toString())) {
            if (value.toString().length() != PropertyValidation.equalLength() && PropertyValidation.equalLength() != 0) {
                logger.info(description + "长度不等于" + PropertyValidation.equalLength() + "\t Value = " + value);
                return ErrorCodesEnum.ERROR_CODE_PARAM_EQUAL.getCode();
            }

            if (value.toString().length() > PropertyValidation.maxLength() && PropertyValidation.maxLength() != 0) {
                logger.info(description + "长度不能超过" + PropertyValidation.maxLength() + "\t Value = " + value);
                return ErrorCodesEnum.ERROR_CODE_PARAM_MAX.getCode();
            }

            if (value.toString().length() < PropertyValidation.minLength() && PropertyValidation.minLength() != 0) {
                logger.info(description + "长度不能小于" + PropertyValidation.minLength());
                return ErrorCodesEnum.ERROR_CODE_PARAM_MIN.getCode();
            }

            if (PropertyValidation.regexType() != RegexType.NONE) {
                switch (PropertyValidation.regexType()) {
                    case NONE:
                        break;
                    case SPECIALCHAR:
                        if (RegexUtils.hasSpecialChar(value.toString())) {
                            logger.info(description + "不能含有特殊字符");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_SPECIAL.getCode();
                        }
                        break;
                    case CHINESE:
                        if (RegexUtils.isChinese2(value.toString())) {
                            logger.info(description + "不能含有中文字符");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_CHINESE.getCode();
                        }
                        break;
                    case EMAIL:
                        if (!RegexUtils.isEmail(value.toString())) {
                            logger.info(description + "地址格式不正确");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_EMAIL.getCode();
                        }
                        break;
                    case IP:
                        if (!RegexUtils.isIp(value.toString())) {
                            logger.info(description + "IP格式不正确");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_IP.getCode();
                        }
                        break;
                    case NUMBER:
                        if (!RegexUtils.isNumber(value.toString())) {
                            logger.info(description + "不是数字");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_NUMBER.getCode();
                        }
                        break;
                    case PHONENUMBER:
                        if (!RegexUtils.isPhoneNumber(value.toString())) {
                            logger.info(description + "不是电话号码");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_PHONE.getCode();
                        }
                        break;
                    case BIRTHDAY:
                        if (!StringUtil.checkBirthDay(value.toString())) {
                            logger.info(description + "不是生日");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_BIRTHDAY.getCode();
                        }
                        break;
                    case SEX:
                        if (!RegexUtils.isNumber(value.toString()) || !RegexUtils.isSex(Integer.parseInt(value.toString()))) {
                            logger.info(description + "不是性别");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_SEX.getCode();
                        }
                        break;
                    case DATE:
                        if (!DateUtils.isValidDate(value.toString())) {
                            logger.info(description + "不是日期格式");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_DATE.getCode();
                        }
                        break;
                    case URL:
                        if (!RegexUtils.isUrl(value.toString())) {
                            logger.info(description + "不是URL格式");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_URL.getCode();
                        }
                        break;
                    case STATUS:
                        if (!RegexUtils.isStatus(value.toString())) {
                            logger.info(description + "不是0或者1");
                            return ErrorCodesEnum.ERROR_CODE_PARAM_INVALID.getCode();
                        }
                        break;
                    default:
                        break;
                }
            }

            if (!"".equals(PropertyValidation.regexExpression())) {
                if (!value.toString().matches(PropertyValidation.regexExpression())) {
                    logger.info(description + "格式不正确");
                    return ErrorCodesEnum.ERROR_CODE_PARAM_INVALID.getCode();
                }
            }
        }
        /*************注解解析工作结束******************/

        return 0;
    }
}