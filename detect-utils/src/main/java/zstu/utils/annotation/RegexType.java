package zstu.utils.annotation;

/**
 * 常用的数据类型枚举
 * @author Goofy
 *
 */
public enum RegexType {

    /*没有格式*/
    NONE,
    /*特殊字符*/
    SPECIALCHAR,
    /*中文汉字*/
    CHINESE,
    /*邮箱*/
    EMAIL,
    /*IP格式*/
    IP,
    /*数字格式*/
    NUMBER,
    /*电话号码*/
    PHONENUMBER,
    /*生日*/
    BIRTHDAY,
    /*性别*/
    SEX,
    /*日期格式*/
    DATE,
    /*url*/
    URL,
    /*0/1*/
    STATUS

}
