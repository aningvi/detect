package zstu.utils.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Aning
 */
public class IPUtils {

    private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);


    public static String getLocalIP() {
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();//获得本机IP
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        return ip;
    }

    public static boolean isInner(String ip) {
        String reg = "(127[.]0[.]0[.]1)|" + "(localhost)|" +
                "(^10(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){3}$)|" +
                "(^172\\.([1][6-9]|[2]\\d|3[01])(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){2}$)|" +
                "(^192\\.168(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){2}$)";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    /**
     * 判断IP地址的合法性，这里采用了正则表达式的方法来判断
     * return true，合法
     */
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String ip = IPUtils.getLocalIP();
        System.out.println(ip);
        System.out.println(isInner("127.0.0.1"));
        System.out.println(isInner("192.168.0.1"));
        System.out.println(isInner("10.0.0.81"));
        System.out.println(isInner("60.211.201.42"));
        System.out.println(ipCheck("192.256.0.177"));
    }
}
