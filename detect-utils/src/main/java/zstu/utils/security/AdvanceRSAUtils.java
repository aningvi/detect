package zstu.utils.security;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @version 1.0
 * @date 2012-4-26
 */
public class AdvanceRSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair(String seed) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = new SecureRandom();
        random.setSeed(seed.getBytes());
        keyPairGen.initialize(1024, random);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }


    public static void main(String[] args) throws Exception {
        test();
    }

    static void test() throws Exception {
        String publicKey;
        String privateKey;

        try {
            Map<String, Object> keyMap = AdvanceRSAUtils.genKeyPair("secret");
            publicKey = AdvanceRSAUtils.getPublicKey(keyMap);
            privateKey = AdvanceRSAUtils.getPrivateKey(keyMap);
            System.out.println("公钥: \n\r" + publicKey);
            System.out.println("私钥： \n\r" + privateKey);
            System.out.println("公钥加密——私钥解密");
            String source = "13475773098";
            System.out.println("\r加密前文字：\r\n" + source);
            byte[] data = source.getBytes();
            byte[] encodedData = AdvanceRSAUtils.encryptByPublicKey(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQ4wcXkmYztAEYMvGWGyEt/gesvD1neA8miHQD\n" +
                    "+nZG/E/meCW5uJUZ4S2O2gmbYVSrfxkTRaWe+YvvB7jUXR2wup+UZ5llnwbGg8iyLVYo0orA2JN8\n" +
                    "JeDNF7iEif+zMXws1v96WBmiv4BHLNBtKuBRlsxaK20Q9uv4qy4s4JziCQIDAQAB\n" +
                    "\n");
            String displayEncode = Hex.encodeHexStr(encodedData);//显示的加密后字符串，传输中用到
            System.out.println("加密后文字：\r\n" + Base64Utils.encode(encodedData));
            byte[] decodedData = AdvanceRSAUtils.decryptByPrivateKey(encodedData, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJDjBxeSZjO0ARgy8ZYbIS3+B6y8\n" +
                    "PWd4DyaIdAP6dkb8T+Z4Jbm4lRnhLY7aCZthVKt/GRNFpZ75i+8HuNRdHbC6n5RnmWWfBsaDyLIt\n" +
                    "VijSisDYk3wl4M0XuISJ/7MxfCzW/3pYGaK/gEcs0G0q4FGWzForbRD26/irLizgnOIJAgMBAAEC\n" +
                    "gYBVH41DJBg3uEIMXaHidv3/b4hCzDWnXLpKAVFoJPborcSju2MuwmuXUNzbWO7cA0sjlwC8eebc\n" +
                    "h/DW9sp4ulx+XLoosWuSlGBGNm4LbOm/XosICxt/HrnNJssgtmcr1d3mIw3mom56yWzJGcpdY0q0\n" +
                    "l2YHCB1w9tNy+yc9fw/MIQJBANkR948E+FfC4wrab+mEeM4zC6cCdn6HjfFuuSSd6o5zD1vztn+j\n" +
                    "EdqkUUXOR/rNn3wWZrUK2cZ/J2WccolgkCcCQQCq3wJp6vBxiBz6hlCNnIXjnYBjmBTZ30+iYIJ2\n" +
                    "nbaSe3ILlmsSCHuQ0rBXG7wZ8hdqei09BBA56qa6yJ1qxipPAkEApdqehj5L6fJUO4SvIYNAMny6\n" +
                    "GH8/PmogQCpTd/DYuMKdJ6rM9DUKfT4zgtycDrtxcgRDsWx6/LlGxtKEiQao3QJADfDh/ohHk8u6\n" +
                    "KYp52gPyPI7mIboPwXLhyWq8Wjcl5S+jL2TXWYJNqpQ2BPVjVG6XEH3lYPwK4t7NOaISjdqbKwJA\n" +
                    "HSZzNp2eAKQxa48F3G9RZg+XUVc9DE3yXKZ/vMXlTDHHaO62GhjhoGoo3z9y78qf7o7Q0ys43WV4\n" +
                    "3tfXYGLYDQ==");
            String target = new String(decodedData);
            System.out.println("解密后文字: \r\n" + target);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    static void testSign() throws Exception {
//        String publicKey;
//        String privateKey;
//
//        try {
//            Map<String, Object> keyMap = AdvanceRSAUtils.genKeyPair("secret");
//            publicKey = AdvanceRSAUtils.getPublicKey(keyMap);
//            privateKey = AdvanceRSAUtils.getPrivateKey(keyMap);
//            System.err.println("公钥: \n\r" + publicKey);
//            System.err.println("私钥： \n\r" + privateKey);
//
//            System.err.println("私钥加密——公钥解密");
//            String source = "这是一行测试RSA数字签名的无意义文字";
//            System.out.println("原文字：\r\n" + source);
//            byte[] data = source.getBytes();
//            byte[] encodedData = AdvanceRSAUtils.encryptByPrivateKey(data, privateKey);
//            System.out.println("加密后：\r\n" + new String(encodedData));
//            byte[] decodedData = AdvanceRSAUtils.decryptByPublicKey(encodedData, publicKey);
//            String target = new String(decodedData);
//            System.out.println("解密后: \r\n" + target);
//            System.err.println("私钥签名——公钥验证签名");
//            String sign = AdvanceRSAUtils.sign(encodedData, privateKey);
//            System.err.println("签名:\r" + sign);
//            boolean status = AdvanceRSAUtils.verify(encodedData, publicKey, sign);
//            System.err.println("验证结果:\r" + status);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
