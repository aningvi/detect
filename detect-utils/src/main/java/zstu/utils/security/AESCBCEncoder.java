package zstu.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准
 *
 * @author stone
 * @date 2014-03-10 06:49:19
 */
public class AESCBCEncoder {
    private final static Logger logger = LoggerFactory.getLogger(AESCBCEncoder.class);

    public static byte[] encrypt(byte[] dataBytes, byte[] key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(Hex.subBytes(key, 0, 16), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(Hex.subBytes(key, 16, 16));
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(dataBytes);
            logger.info("加密Key:" + new String(key) + " 加密前:" + new String(dataBytes) + " 加密后：" + Hex.byteToString(encrypted));
            return encrypted;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static byte[] desEncrypt(byte[] data, byte[] key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(Hex.subBytes(key, 0, 16), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(Hex.subBytes(key, 16, 16));
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(data);
            logger.info("解密Key:" + new String(key) + " 解密前:" + Hex.byteToString(data) + " 解密后：" + new String(original));
            return original;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }


    private static int toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }



    public static void main(String[] args) throws Exception {
        String  timestamp = System.currentTimeMillis()+"";
        System.out.println("timestamp="+timestamp);
        String devicePin = "223b2f7709c4b5dcc6003b12b8313e9c".substring(16);
        devicePin = devicePin+devicePin;
        byte[] encode = encrypt(timestamp.getBytes(), devicePin.getBytes());
        String data = Hex.byteToString(encode);
        System.out.println("pin="+data);
        byte[] decode = desEncrypt(Hex.hexStringToByte(data), devicePin.getBytes());
        System.out.println(new String(decode));
        //System.out.println(new String(AESCBCEncoder.desEncrypt("a54cc65fc8089f1c6fbb4952f6d3fbdc".getBytes(), "4cb2930220fff0974cb2930220fff097".getBytes())));
    }

}


