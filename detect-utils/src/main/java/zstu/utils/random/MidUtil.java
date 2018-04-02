package zstu.utils.random;

/**
 * 生成消息ID
 *
 * @author Aning
 **/
public class MidUtil {
    /**
     * 获取递增的临时mid
     *
     * @return
     */
    public static String getMid() {
        return IDInc.incr();
    }

}
