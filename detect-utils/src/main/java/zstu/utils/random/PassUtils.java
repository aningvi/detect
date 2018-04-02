package zstu.utils.random;

/**
 * Created with IntelliJ IDEA.
 * User: Aning
 * 常用工具包。包括生成各种密码随机串，加密解密，编码解码，执行url等
 */

import java.util.Random;


public class PassUtils {


    /**
     * 生成随即密码
     *
     * @param pwd_len 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int pwd_len) {
        final int maxNum = 42;
        int i;  //生成的随机数
        int count = 0; //生成的密码的长度
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','@','$','%','^','(',')'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            i = Math.abs(r.nextInt(maxNum));

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 生成密码.
     *
     * @param count   密码位数
     * @param letters 是否包含字符
     * @param numbers 是否包含数字
     * @return String password
     */
    public static String getPassword(int count, boolean letters, boolean numbers) {
        return RandomStringUtils.random(count, letters, numbers);
    }

    /**
     * 生成字符数字混合的密码.
     *
     * @param count 密码位数
     * @return String password
     */
    private static String getPassword(int count) {
        return getPassword(count, true, true);
    }


    /**
     * 生成纯数字密码.
     *
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfNumber(int count) {
        return getPassword(count, false, true);
    }

    /**
     * 生成纯字符密码.
     *
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfCharacter(int count) {
        return getPassword(count, true, false);
    }


}


