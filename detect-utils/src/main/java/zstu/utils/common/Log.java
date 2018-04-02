package zstu.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日志工具类
 */
public class Log {


    /**
     * 获取日志对象
     */
    private static Logger getLog(Object obj) {
        return LoggerFactory.getLogger(obj.getClass());
    }

    public static void info(Object obj, String msg) {
        getLog(obj).info(msg);
    }

    public static void warn(Object obj, String msg) {
        getLog(obj).warn(msg);
    }

    public static void debug(Object obj, String msg) {
        getLog(obj).debug(msg);
    }

    public static void error(Object obj, Throwable e) {
        getLog(obj).error(e.getMessage(), e);
    }
}
