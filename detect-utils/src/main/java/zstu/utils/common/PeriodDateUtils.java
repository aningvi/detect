package zstu.utils.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PeriodDateUtils {

    public static void main(String[] argus) {
        /*List<Map.Entry<String,String>> mappingList = getPeriodList("2016-01-01T01:00:00","2016-01-02T03:00:00","real");
        for(Map.Entry<String,String> mapping:mappingList){
            System.out.println(mapping.getKey()+"---"+mapping.getValue());
        }*/

        List<Map.Entry<String, String>> mappingList1 = getPeriodList("2016-01-01T01:00:00", "2016-12-31T03:00:00", "months");
        for (Map.Entry<String, String> mapping : mappingList1) {
            System.out.println(mapping.getKey() + "---" + mapping.getValue());
        }

        System.out.println(getDaysOfMonth("2017-02"));
        getDaysOfWeek("2017-53");
    }


    public static String getMonthFirstDays(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int month = Integer.valueOf(date.substring(5, 7));
        int year = Integer.valueOf(date.substring(0, 4));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDate = sdf.format(calendar.getTime());
        return firstDate;
    }


    public static String getMonthLastDays(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int month = Integer.valueOf(date.substring(5, 7));
        int year = Integer.valueOf(date.substring(0, 4));
        int day = Integer.valueOf(date.substring(8, 9));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(calendar.getTime());
    }


    public static List<Map.Entry<String, String>> getPeriodList(String startTime, String endTime, String type) {
        Map<String, String> periodMap = new HashMap<String, String>();
        int start = 0, end = 0;
        start = Integer.parseInt(startTime.substring(11, 13));
        end = Integer.parseInt(endTime.substring(11, 13));
        String newStartTime = startTime.substring(0, 10);
        String newEndTime = endTime.substring(0, 10);

        if ("real".equals(type)) {
            if (newStartTime.equals(newEndTime)) {
                periodMap = getPeriodsMapI(periodMap, newStartTime, start, end);
            } else {
                periodMap = getPeriodsMapI(periodMap, newStartTime, start, 23);
                periodMap = getPeriodsMapI(periodMap, newEndTime, 0, end);
            }

        }

        if ("day".equals(type)) {
            periodMap = getPeriodsMapI(periodMap, newStartTime, 0, 23);
        }

        if ("week".equals(type)) {
            int startY = Integer.parseInt(startTime.substring(0, 4));
            int endY = Integer.parseInt(endTime.substring(0, 4));
            start = Integer.parseInt(startTime.substring(8, 10));
            end = Integer.parseInt(endTime.substring(8, 10));
            Integer startM = Integer.parseInt(startTime.substring(5, 7));
            Integer endM = Integer.parseInt(endTime.substring(5, 7));
            String time = getMonthLastDays(startTime).substring(8, 10);
            Integer lastTime = Integer.parseInt(time);
            if (startY == endY) {
                if (start < end && startM.equals(endM)) {
                    periodMap = getPeriodsMapII(periodMap, startTime, start, end);
                }
                if (startM < endM) {
                    periodMap = getPeriodsMapII(periodMap, startTime, start, lastTime);
                    periodMap = getPeriodsMapII(periodMap, endTime, 1, end);
                }
            } else {
                periodMap = getPeriodsMapII(periodMap, startTime, start, lastTime);
                periodMap = getPeriodsMapII(periodMap, endTime, 1, end);
            }
        }

        if ("month".equals(type)) {
            periodMap = getDayOfMonth(startTime, "hour");
        }

        if ("months".equals(type)) {
            periodMap = getDayOfMonth(startTime, "day");
        }

        if ("quarter".equals(type)) {
            periodMap = getQuatas(startTime);
        }

        if ("year".equals(type)) {
            periodMap = getYears(startTime.substring(0, 4));
        }

        List<Map.Entry<String, String>> mappingList = getSortListMap(periodMap);
        return mappingList;
    }

    private static List<Map.Entry<String, String>> getSortListMap(Map<String, String> periodMap) {
        List<Map.Entry<String, String>> mappingList = null;
        mappingList = new ArrayList<Map.Entry<String, String>>(periodMap.entrySet());
        Collections.sort(mappingList, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });
        return mappingList;
    }


    private static Map<String, String> getQuatas(String startTime) {
        Map<String, String> periodMap = new HashMap<String, String>();
        int quata = Integer.valueOf(startTime.substring(5, 7));
        String statQuata = startTime.substring(0, 4);
        switch (quata) {
            case 1:
                periodMap = getQuarterMap(periodMap, quata, statQuata, 1, 3);
                break;
            case 4:
                periodMap = getQuarterMap(periodMap, quata, statQuata, 4, 6);
                break;
            case 7:
                periodMap = getQuarterMap(periodMap, quata, statQuata, 7, 9);
                break;
            case 10:
                periodMap = getQuarterMap(periodMap, quata, statQuata, 10, 12);
                break;
            default:
                break;
        }
        return periodMap;
    }


    private static Map<String, String> getYears(String dataes) {
        Map<String, String> periodMap = new HashMap<String, String>();
        String statquata = dataes.substring(0, 4);
        for (int i = 1; i <= 12; i++) {
            Map<String, String> periodMaps = getDayOfMonth(statquata + "-" + (i <= 9 ? "0" + i : i), "day");
            for (Map.Entry<String, String> entry : periodMaps.entrySet()) {
                periodMap.put(entry.getKey(), entry.getValue());
            }
        }
        return periodMap;
    }


    private static Map<String, String> getDayOfMonth(String dates, String type) {
        Map<String, String> periodMap = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int month = Integer.valueOf(dates.substring(5, 7)) - 1;
        int year = Integer.valueOf(dates.substring(0, 4));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int date = 1;
        calendar.set(year, month, date);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        String preTime = "";
        for (int i = minDay; i <= maxDay; i++) {
            calendar.set(year, month, i);
            preTime = sdf.format(calendar.getTime()).substring(0, 10);
            if ("hour".equals(type)) {
                for (int j = 0; j <= 23; j++) {
                    periodMap.put(preTime + "T" + (j <= 9 ? "0" + j : j) + ":00:00", String.format("% .3f", 0.0));
                }
            } else {
                periodMap.put(preTime, String.format("% .3f", 0.0));
            }

        }
        return periodMap;
    }


    private static Map<String, String> getPeriodsMapI(Map<String, String> periodMap, String startTime, int start, int end) {
        for (int i = start; i <= end; i++) {
            periodMap.put(startTime + "T" + (i <= 9 ? "0" + i : i) + ":00:00", String.format("% .3f", 0.0));
        }
        return periodMap;
    }


    private static Map<String, String> getPeriodsMapII(Map<String, String> periodMap, String startTime, int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = 0; j <= 23; j++) {
                periodMap.put(startTime.substring(0, 8) + (i <= 9 ? "0" + i : i) + "T" + (j <= 9 ? "0" + j : j) + ":00:00", String.format("% .3f", 0.0));
            }
        }
        return periodMap;
    }

    private static Map<String, String> getQuarterMap(Map<String, String> periodMap, int quatr, String statQuatr, int start, int end) {
        for (int i = start; i <= end; i++) {
            Map<String, String> periodMaps = getDayOfMonth(statQuatr + (quatr < 10 ? "-0" : "-") + i, "day");
            for (Map.Entry<String, String> entry : periodMaps.entrySet()) {
                periodMap.put(entry.getKey(), entry.getValue());
            }
        }
        return periodMap;
    }

    /**
     * 获取某月的天数
     *
     * @param yearMonth 日期，格式：2017-10 代表2017年10月
     * @return 天数
     */
    public static int getDayCountOfMonth(String yearMonth) {
        String[] date = yearMonth.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取某月的天List yyyy-MM-dd
     *
     * @param yearMonth 日期，格式：2017-10 代表2017年10月
     * @return dayList
     */
    public static List<String> getDaysOfMonth(String yearMonth) {
        List<String> dayList = new ArrayList<>();
        int dayNumOfMonth = getDayCountOfMonth(yearMonth);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateUtils.parse(yearMonth, new SimpleDateFormat("yyyy-MM")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {
            String day = simpleDateFormat.format(cal.getTime());
            dayList.add(day);
        }
        return dayList;
    }

    /**
     * 获取某周的所有日期
     *
     * @param yearWeek 日期，格式：2017-10代表2017年第10周
     * @return dayList 周一到周日
     */
    public static List<String> getDaysOfWeek(String yearWeek) {
        String[] date = yearWeek.split("-");
        int year = Integer.parseInt(date[0]);
        int week = Integer.parseInt(date[1]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        List<String> dayList = new ArrayList<>();
        for (int i = Calendar.MONDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            String key = simpleDateFormat.format(cal.getTime());
            dayList.add(key);
        }
        //add next Sunday
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String sunday = simpleDateFormat.format(cal.getTime());
        dayList.add(sunday);
        return dayList;
    }
}
