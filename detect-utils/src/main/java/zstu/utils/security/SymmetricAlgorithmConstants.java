package zstu.utils.security;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Administrator
 * Date: 14-9-25
 * Time: 下午3:01
 */
public class SymmetricAlgorithmConstants {
    public static final String AES = "AES";
    public static final Set<String> SA_SET = new HashSet<String>();

    static {
        SA_SET.add(AES);
    }
}
