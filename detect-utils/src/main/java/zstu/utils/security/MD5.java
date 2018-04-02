package zstu.utils.security;

import zstu.utils.common.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: MAX
 * Date: 13-10-28
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */

/*
 * MD5 算法
*/
public class MD5 {

    public MD5() {
    }





    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(Hex.byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj){
        return getMd5String(strObj);
    }
    public static String getMd5String(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static String getMd5Bytes(byte[] strObj) {
        String resultString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5.getMd5String("2014-12-22T15:11:22"+"MobileApp"));
    }

    public static boolean checkMd5(String key, String md5) {
        if (!StringUtil.isEmpty(key) && !StringUtil.isEmpty(md5)) {
            if (key.length() == 32 && md5.length() == 32 && key.trim().equalsIgnoreCase(md5)) {
                return true;
            }
            if (key.length() == 16 && md5.length() == 32 && key.trim().equalsIgnoreCase(md5.substring(8, 24))) {
                return true;
            }
        }
        return false;
    }


    /**
     * 文件MD5
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        MessageDigest messagedigest = DigestUtils.getDigest("MD5");
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        //String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        fis.close();
        return bytes2HexString(messagedigest.digest());
    }
    public static String getFileStreamMD5String(InputStream fis) throws IOException {
        MessageDigest messagedigest = DigestUtils.getDigest("MD5");
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        //String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        fis.close();
        return bytes2HexString(messagedigest.digest());
    }

    /**
     *把字节数组转换为16进制的形式
     * @param b
     * @return
     */
    private static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[ i ] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            //ret += hex.toUpperCase();
            ret += hex;
        }
        return ret;
    }

}