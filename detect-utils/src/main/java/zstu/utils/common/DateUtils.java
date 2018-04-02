/*
 * Created on Jan 4, 2008
 */
package zstu.utils.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期常用方法
 * 主要方法有四种：
 * String2Date:字符串转日期
 * Date2String：日期转字符串
 * formatDate：格式化字符串到日期
 * parseString：格式化日期到字符串
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);


    // some static date formats
    public static SimpleDateFormat[] DATE_FARMATS = loadDateFormats();

    private static SimpleDateFormat[] loadDateFormats() {
        SimpleDateFormat[] temp = {
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyyMMdd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("HH:mm"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"),
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US),
                new SimpleDateFormat("yyyy-M"),
                new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz", Locale.US)
        };

        new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz", Locale.US).setTimeZone(TimeZone.getTimeZone("GMT"));
        return temp;
    }


    /**
     * Gets the array of SimpleDateFormats that DateUtils knows about.
     */
    private static SimpleDateFormat[] getFormats() {
        return DATE_FARMATS;
    }


    /**
     * 字符串转换为java.util.Date<br>
     * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
     * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
     * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
     * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
     * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
     *
     * @param time String 字符串<br>
     * @return Date 日期<br>
     */
    public static Date stringToDate(String time) {
        return parseFromFormats(time);
    }

    /**
     * 将java.util.Date 格式转换为字符串格式'yyyy-MM-ddTHH:mm:ss'(24小时制)<br>
     * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11T17:24:21'<br>
     *
     * @param time Date 日期<br>
     * @return String   字符串<br>
     */
    public static String dateToString(Date time) {
        String ctime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(time);
        return ctime;
    }


    /**
     * 字符串转成日期，根据日期格式的优先级，第一个匹配的返回
     *
     * @param aValue
     * @return
     */
    public static Date parseFromFormats(String aValue) {
        if (StringUtil.isEmpty(aValue)) {
            return null;
        }

        SimpleDateFormat[] formats = DateUtils.getFormats();
        if (formats == null) {
            return null;
        }

        for (int i = 0; i < formats.length; i++) {
            try {
                Date myDate = DateUtils.parse(aValue, formats[i]);
                return myDate;
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }


    /**
     * 字符串返回日期的毫秒数
     *
     * @param aValue
     * @return
     */
    public static java.sql.Timestamp parseTimestampFromFormats(String aValue) {
        if (StringUtil.isEmpty(aValue)) {
            return null;
        }

        Date myDate = DateUtils.parseFromFormats(aValue);
        if (myDate != null) {
            return new java.sql.Timestamp(myDate.getTime());
        }
        return null;
    }

    public static String getSetDayAgoTime(Integer num, Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -num + 1);
        Date temp = calendar.getTime();
        return format(temp, new SimpleDateFormat(format));
    }


    /**
     * 通用的转换字符的方法，日期转换成字符串
     * Returns p string the represents the passed-in date parsed
     * according to the passed-in format.  Returns an empty string
     * if the date or the format is null.
     */
    public static String format(Date aDate, SimpleDateFormat aFormat) {
        if (aDate == null || aFormat == null) {
            return "";
        }
        synchronized (aFormat) {
            return aFormat.format(aDate);
        }
    }


    /**
     * 字符串转换成另一种格式的字符串
     * Tries to take the passed-in String and format it as p date string in the
     * the passed-in format.
     */
    public static String formatDateString(String aString, SimpleDateFormat aFormat) {
        if (StringUtil.isEmpty(aString) || aFormat == null) {
            return "";
        }
        try {
            java.sql.Timestamp aDate = parseTimestampFromFormats(aString);
            if (aDate != null) {
                return DateUtils.format(aDate, aFormat);
            }
        } catch (Exception e) {
            // Could not parse aString.
        }
        return "";
    }

    //-----------------------------------------------------------------------


    /**
     * 通用的字符串转换成日期的方法
     * Returns p Date using the passed-in string and format.  Returns null if the string
     * is null or empty or if the format is null.  The string must match the format.
     */
    public static Date parse(String aValue, SimpleDateFormat aFormat) throws ParseException {
        if (StringUtil.isEmpty(aValue) || aFormat == null) {
            return null;
        }
        synchronized (aFormat) {
            return aFormat.parse(aValue);
        }
    }


    //-----------------------------------------------------------------------
    // returns full timestamp format
    public static SimpleDateFormat defaultTimestampFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    //-----------------------------------------------------------------------
    // convenience method returns long friendly timestamp format
    public static SimpleDateFormat friendlyTimestampFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }


    /**
     * 获取年龄
     *
     * @param time
     * @return
     */
    public static int getAge(long time) {
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar calNow = new GregorianCalendar();
        cal.setTimeInMillis(time);
        calNow.setTimeInMillis(System.currentTimeMillis());
        int year = cal.get(Calendar.YEAR);
        int yearNow = calNow.get(Calendar.YEAR);
        return yearNow - year;
    }

    public static int getAge(Date date) {
        return getAge(date.getTime());
    }


    /**
     * 获取时间差
     * 返回参照时间是当前时间的什么时候
     *
     * @param time    参照时间
     * @param timeNow 当前时间
     * @return
     */
    public static String getFriendlyTimeDiff(long time, long timeNow) {
        long diff = timeNow - time;
        String str = "";
        if (diff > 0) {
            long s = diff / (60 * 1000);
            long h = s / 60;
            long d = h / 24;
            long m = d / 30;
            long y = m / 12;
            if (y > 0) {
                str = y + "年前";
            } else if (m > 0) {
                str = m + "月前";
            } else if (d > 0) {
                str = d + "天前";
            } else if (h > 0) {
                str = h + "小时前";
            } else if (s > 0) {
                str = s + "分钟前";
            } else {
                str = "刚刚";
            }
        }
        return str;
    }


    /**
     * 当天零点
     *
     * @return
     */
    public static long getToDayZeroClock() {
        return getToDayZeroClock(System.currentTimeMillis());
    }


    public static long getToDayZeroClock(Long time) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date(time));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTimeInMillis();
    }

    /**
     * 获取天的起始时间
     *
     * @return
     */
    public static Date getDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取天的结束时间
     *
     * @return
     */
    public static Date getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取周的开始时间 周一
     *
     * @return
     */
    public static Date getBeginDayOfWeekChina(Date date) {
        if (date == null) {
            date = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取周的结束时间 周天
     *
     * @return
     */
    public static Date getEndDayOfWeekChina(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeekChina(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取月的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取月的结束时间
     *
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDayEndTime(cal.getTime());
    }

    /**
     * date的前n小时
     *
     * @param date
     * @return
     */
    public static Date getPreHour(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -n);
        date = calendar.getTime();
        return date;
    }

    /**
     * date的前n小时的起始时间 如13:00:00.000
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getPreHourStartTime(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - n);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        date = calendar.getTime();
        return date;
    }

    /**
     * date的前n小时的结束时间 如13:59:59.999
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getPreHourEndTime(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - n);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = calendar.getTime();
        return date;
    }

    /**
     * date的前一天
     *
     * @param date
     * @return
     */
    public static Date getPreDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * date的前n天
     *
     * @param date
     * @return
     */
    public static Date getPreDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        date = calendar.getTime();
        return date;
    }

    /**
     * date的前一月
     *
     * @param date
     * @return
     */
    public static Date getPreMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 校验日期格式
     *
     * @param date       日期
     * @param dateFormat 日期格式
     * @return
     */
    private static boolean isValidDate(String date, SimpleDateFormat dateFormat) {
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDate(String date) {
        for (SimpleDateFormat dateFormat : DATE_FARMATS) {
            if (isValidDate(date, dateFormat)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算两个时间相差几天
     *
     * @param paramDate1
     * @param paramDate2
     * @return
     * @throws Exception
     */
    public static int getDaysBetween(Date paramDate1,
                                     Date paramDate2) throws Exception {
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar1.setTime(paramDate1);
        localCalendar2.setTime(paramDate2);
        if (localCalendar1.after(localCalendar2)) {
            throw new Exception("起始日期小于终止日期!");
        }
        int i = localCalendar2.get(Calendar.DAY_OF_YEAR) - localCalendar1.get(Calendar.DAY_OF_YEAR);
        int j = localCalendar2.get(Calendar.FEBRUARY);
        if (localCalendar1.get(Calendar.FEBRUARY) != j) {
            localCalendar1 = (Calendar) localCalendar1.clone();
            do {
                i += localCalendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
                localCalendar1.add(Calendar.FEBRUARY, 1);
            } while (localCalendar1.get(Calendar.FEBRUARY) != j);
        }
        return i;
    }

    /**
     * 增加几天
     *
     * @param paramDate
     * @param paramInt
     * @return
     */
    public static Date addDays(Date paramDate, int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        int i = localCalendar.get(Calendar.DAY_OF_YEAR);
        localCalendar.set(Calendar.DAY_OF_YEAR, i + paramInt);
        return localCalendar.getTime();
    }

    /**
     * 增加几天，参数为字符串
     *
     * @param paramString1
     * @param paramInt
     * @return
     * @throws Exception
     */
    public static Date addDays(String paramString1,
                                         int paramInt) throws Exception {
        Calendar localCalendar = Calendar.getInstance();
        Date localDate = parseFromFormats(paramString1);
        localCalendar.setTime(localDate);
        int i = localCalendar.get(6);
        localCalendar.set(6, i + paramInt);
        return localCalendar.getTime();
    }


    /**
     * 当前时刻距当天凌晨秒数
     *
     * @return
     */
    public static int secondsSinceDay() {
        return secondsSinceDay(new Date());
    }

    public static int secondsSinceDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        return hour * 3600 + minute * 60 + second;
    }


    public static int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期，标准格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前日期字符串
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 转化成标准日期字符串，标准格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 标准日期字符串
     */
    public static String getFormatDate(String originDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(sdf.parse(originDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> findDates(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(startTime);
        Date dEnd = sdf.parse(endTime);
        List<String> lDate = new ArrayList();
        lDate.add(startTime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sdf.format(calBegin.getTime()));
        }
        return lDate;
    }


    public static void main(String[] args) throws ParseException {
        String start = "2012-02-01";
        String end = "2012-03-02";

        List<String> lDate = findDates(start, end);
        for (String date : lDate) {
            System.out.println(date);
        }


    }


}
