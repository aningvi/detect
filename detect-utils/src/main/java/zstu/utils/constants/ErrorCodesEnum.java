package zstu.utils.constants;

/**
 * 错误码定义，支持多国语言，语言顺序固定：
 * 中文，英文.....
 * 默认返回中文
 *
 * Created by zhaop on 2017/6/10.
 */
public enum ErrorCodesEnum {

    /************系统类错误码**************/
    ERROR_CODE_SERVER_ERROR(20001,new String[]{"服务器内部错误，请联系管理员","Server internal error, please contact the administrator"}),
    ERROR_CODE_REQUEST_MORE(20002,new String[]{"请求太频繁","The request is too frequent"}),
    ERROR_CODE_SAVE_ERROR(20006,new String[]{"保存失败","Save failure"}),
    ERROR_CODE_DELETE_ERROR(20007,new String[]{"删除失败","delete failure"}),
    ERROR_CODE_UPDATE_ERROR(20008,new String[]{"更新失败","Update failure"}),
    ERROR_CODE_BLACKIP_ERROR(20009,new String[]{"请求IP在黑名单内","Request IP in the blacklist"}),
    ERROR_CODE_BLACKUID_ERROR(20010,new String[]{"请求用户在黑名单内","Request the user in the blacklist"}),
    ERROR_CODE_QUERY_ERROR(20011,new String[]{"查询失败","Query failure"}),

    /************参数类错误码**************/
    ERROR_CODE_HEADER_ERROR(20021,new String[]{"header参数不足，缺少必要的header内容","Lack of header parameters and lack of necessary header content"}),
    ERROR_CODE_PARAM_NULL(20022,new String[]{"参数不足，有些必传参数没有传入","The parameters are insufficient and some must pass parameters are not introduced"}),
    ERROR_CODE_PARAM_INVALID(20023,new String[]{"参数不合法，请检查参数格式","The parameter is unlawful, please check the parameter format"}),
    ERROR_CODE_PARAM_EQUAL(20024,new String[]{"参数长度错误","Parameter length error"}),
    ERROR_CODE_PARAM_MAX(20025,new String[]{"参数超长","Ultra long parameter"}),
    ERROR_CODE_PARAM_MIN(20026,new String[]{"参数太短","The parameters are too short"}),
    ERROR_CODE_PARAM_SPECIAL(20027,new String[]{"参数包含特殊字符","Parameters contain special characters"}),
    ERROR_CODE_PARAM_CHINESE(20028,new String[]{"参数包含中文","Parameters include Chinese"}),
    ERROR_CODE_PARAM_EMAIL(20029,new String[]{"邮箱地址错误","Email error"}),
    ERROR_CODE_PARAM_IP(20030,new String[]{"IP格式错误","IP Error"}),
    ERROR_CODE_PARAM_NUMBER(20031,new String[]{"数字格式错误","Num error"}),
    ERROR_CODE_PARAM_PHONE(20032,new String[]{"电话号码格式错误","Phone num error"}),
    ERROR_CODE_PARAM_DATE(20133,new String[]{"出生日期格式错误","Birthday error"}),
    ERROR_CODE_PARAM_URL(20034,new String[]{"URL格式错误","URL error"}),
    ERROR_CODE_PARAM_BIRTHDAY(20035,new String[]{"出生日期格式错误","Date formatting error"}),
    ERROR_CODE_PARAM_SEX(20036,new String[]{"性别格式错误","Gender formatting error"}),
    ERROR_CODE_PARAM_PASSWD(20037,new String[]{"密码错误","Password error"}),


    /************数据类错误码**************/
    ERROR_CODE_DATA_NULL(20038,new String[]{"请求资源不存在","Request resources do not exist"}),//比如更新或查询数据的时候传的ID找不到数据
    ERROR_CODE_DATA_EXIST_ERROR(20039,new String[]{"数据已存在","The data has already existed"}),//比如新增数据的时候，出现重复的业务主键


    /************业务类错误码**************/
    ERROR_CODE_FILE_SIZE_MAX(20040,new String[]{"上传文件过大，请检查文件大小","The upload file is too large, please check the file size"}),
    ERROR_CODE_OPERAT_FREQUENTLY_ERROR(20041,new String[]{"操作频繁","Frequent operation"}),
    ERROR_CODE_ALREADY_BIND(20042,new String[]{"用户已经绑定设备","The user has been bound to the device"}),
    ERROR_CODE_VALCODE_ERROR(20043,new String[]{"验证码不正确","Validation code error"}),
    ERROR_CODE_DEVICE_QR_CODE_ERROR(20044,new String[]{"设备共享二维码已失效","Device sharing two dimensional code has failed"}),
    ERROR_CODE_DEVICE_QR_CODE_OUT_OF_DATE_ERROR(20045,new String[]{"设备共享二维码已过期","Device sharing two dimensional code has expired"}),
    ERROR_CODE_OTA_PUSH_FAIL(20046,new String[]{"推送升级失败","Push and upgrade failure"}),
    ERROR_CODE_TRY_AGAIN(20047,new String[]{"60秒内只能发送一次","It can only be sent once within 60 seconds"}),
    ERROR_CODE_NO_MORE_THAN_TEN(20048,new String[]{"一个手机一天只能发送十条短信","A cell phone can only send ten messages a day"}),
    ERROR_CODE_FILE_ISNULL(21049,new String[]{"文件为空","File is empty"}),
    ERROR_CODE_FILE_FORMAT_ERROR(21050,new String[]{"文件格式不正确","File format is incorrect"}),

    //用户相关
    ERROR_CODE_PLATFORM_NOT_EXIST(20051,new String[]{"平台不存在","The platform does not exist"}),
    ERROR_CODE_VAL_ERROR(20052,new String[]{"无效校验类型","Invalid check type"}),
    ERROR_CODE_VALCODE_TIME_OUT(20053,new String[]{"验证码失效","Verification code out of time"}),
    ERROR_CODE_FUID_EXIST(20054,new String[]{"好友已存在","Friends have already existed"}),
    ERROR_CODE_SMSCOUNT_OUT(20055,new String[]{"平台短信次数不足","Lack of short message on the platform"}),
    ERROR_CODE_NOT_FRIENDS(20056,new String[]{"对方不是您的好友,无权操作","The other is not your friend, no right to operate"}),
    ERROR_CODE_NOT_USER(20057,new String[]{"用户不存在","User is not exist"}),
    ERROR_CODE_ERROR_OLDPWD(20058,new String[]{"旧密码错误","Old password error"}),
    ERROR_CODE_ERROR_REGIST(20059,new String[]{"邮箱或者手机号有且只能传一个","The email or the phone number has and can only pass one"}),
    ERROR_CODE_NO_SUBSCRIBE_WEIXIN(20130,new String[]{"未关注微信","Not subscribed to WeChat"}),
    ERROR_CODE_UNRELEANCE_USER_WEIXIN(20131,new String[]{"未关联微信用户","Unrelated WeChat users"}),
    ERROR_CODE_RELEANCE_USER_WEIXIN_NOT_EXIST(20132,new String[]{"关联的用户不存在","The associated user does not exist"}),
    ERROR_CODE_ERROR_TS_PARAM(200126,new String[]{"请求时间不正确，请检查本地时间","Error request time"});



    private Integer code;
    private String[] msg;

    ErrorCodesEnum(Integer code, String[] msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg[0];
    }

    public String getMsg(String locale) {
        if("zh_CN".equals(locale)) {

                return msg[0];
        }
        if("en_US".equals(locale)) {
            if(msg.length>=2) {
                return msg[1];
            }else{

                return msg[0];
            }
        }
        return msg[0];
    }

}
