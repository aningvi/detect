package zstu.utils.security;


/**
 * User: Aning
 */
public class SymmetricAlgorithmUtil {

    public static boolean available(String encode) {
        return SymmetricAlgorithmConstants.SA_SET.contains(encode);
    }

    public static String encryption(String to_encrypt, String type, String password) throws Exception {
        if (SymmetricAlgorithmConstants.AES.equals(type)) {
            byte[] key = Hex.decodeHex((password).toCharArray());
            byte[] encryptData = AESCoder.encrypt(to_encrypt.getBytes(), key);
            String encrypt = Hex.encodeHexStr(encryptData);
            return encrypt;
        }
        return null;
    }

    public static String decrypt(String to_decrypt, String type, String password) throws Exception {
        if (SymmetricAlgorithmConstants.AES.equals(type)) {
            byte[] key = Hex.decodeHex((password).toCharArray());
            byte[] decryptData = AESCoder.decrypt(Hex.decodeHex(to_decrypt.toCharArray()), key);
            String decrypt = new String(decryptData);
            return decrypt;
        }
        return null;
    }
}
