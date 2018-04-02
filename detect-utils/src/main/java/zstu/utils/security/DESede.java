package zstu.utils.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: zsf
 * Date: 13-10-9
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
public class DESede {

    private static final String ALGORITHM = "DESede";    //加密算法

    private static final byte[] KEY_BYTES = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
            , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
            , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2};

    public static String encrypt(String src) {
        byte[] destByte = null;
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(KEY_BYTES, ALGORITHM);

            //加密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            destByte = c1.doFinal(src.getBytes());
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if( destByte != null )
        {
            return Hex.byteToString(destByte);
        }
        return null;
    }

    public static String decrypt(String src) {
        byte[] destByte = null;
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(KEY_BYTES, ALGORITHM);
            //解密
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            destByte = c1.doFinal(Hex.hexStringToByte(src));
        } catch (java.security.NoSuchAlgorithmException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }catch(javax.crypto.NoSuchPaddingException e2){
            e2.printStackTrace();
        }catch(Exception e3){
            e3.printStackTrace();
        }
        if( destByte != null ){
            return new String(destByte);
        }
        return null;
    }



    public static void main(String[] args) {
//        System.out.println(DESede.encrypt("1234567812345678123"));
//        System.out.println(DESede.decrypt("D4F7DDF6D2B9A0E7"));
        Double a = new Double("11619.108572");
        a/=100;
        System.out.print(a);
    }

}
