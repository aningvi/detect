package zstu.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    private static final DecimalFormat DECIMAL_FORMAT;

    /**
     * <p>The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int PAD_LIMIT = 8192;

    final public static char COMMA = ',';

    final public static String COMMA_STR = ",";

    final public static char ESCAPE_CHAR = '\\';

    final static String REG_EX = "[\u4e00-\u9fa5]";

    final static Pattern PATTERN = compile(REG_EX);

    public static String speed(long bytes, long ms) {
        double sec = (double) ms / 1000;
        double bytesPerSec = bytes / sec;
        return StringUtil.byteDesc((long) bytesPerSec) + "/s";
    }

    static {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DECIMAL_FORMAT = (DecimalFormat) numberFormat;
        DECIMAL_FORMAT.applyPattern("#.##");
    }


    final static char[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) {

        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    final static String regEx = "[\u4e00-\u9fa5]";

    final static Pattern pat = Pattern.compile(regEx);

    public static boolean isContainsChinese(String str) {
        Matcher matcher = pat.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public boolean isEmail(String email) {
        if(StringUtil.isEmpty(email)) {
            return false;
        }
        //正则表达式
  /*
    String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
    return email.matches(regex);
   */

        //不适用正则
        if (email == null || "".equals(email)) {
            return false;
        }
        if (!containsOneWord('@', email) || !containsOneWord('.', email)) {
            return false;
        }
        String prefix = email.substring(0, email.indexOf("@"));
        String middle = email.substring(email.indexOf("@") + 1, email.indexOf("."));
        String subfix = email.substring(email.indexOf(".") + 1);
        System.out.println("prefix=" + prefix + "  middle=" + middle + "  subfix=" + subfix);

        if (prefix == null || prefix.length() > 40 || prefix.length() == 0) {
            return false;
        }
        if (!isAllWords(prefix)) {
            return false;
        }
        if (middle == null || middle.length() > 40 || middle.length() == 0) {
            return false;
        }
        if (!isAllWordsAndNo(middle)) {
            return false;
        }
        if (subfix == null || subfix.length() > 3 || subfix.length() < 2) {
            return false;
        }
        if (!isAllWords(subfix)) {
            return false;
        }
        return true;
    }

    //判断字符串只包含指定的一个字符c
    private boolean containsOneWord(char c, String word) {
        char[] array = word.toCharArray();
        int count = 0;
        for (Character ch : array) {
            if (c == ch) {
                count++;
            }
        }
        return count == 1;
    }

    //检查一个字符串是否全部是字母
    private boolean isAllWords(String prefix) {
        char[] array = prefix.toCharArray();
        for (Character ch : array) {
            if (ch < 'A' || ch > 'z' || (ch < 'a' && ch > 'Z')) {
                return false;
            }
        }
        return true;
    }

    //检查一个字符串是否包含字母和数字
    private boolean isAllWordsAndNo(String middle) {
        char[] array = middle.toCharArray();
        for (Character ch : array) {
            if (ch < '0' || ch > 'z') {
                return false;
            } else if (ch > '9' && ch < 'A') {
                return false;
            } else if (ch > 'Z' && ch < 'a') {
                return false;
            }
        }
        return true;
    }

    public static boolean containsChinese(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean validateLong(String str) {
        try {
            long longstr = Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Split a string using the default separator
     *
     * @param str a string that may have escaped separator
     * @return an array of strings
     */
    public static String[] split(String str) {
        return split(str, ESCAPE_CHAR, COMMA);
    }

    /**
     * Split a string using the given separator
     *
     * @param str        a string that may have escaped separator
     * @param escapeChar a char that be used to escape the separator
     * @param separator  a separator char
     * @return an array of strings
     */
    public static String[] split(
            String str, char escapeChar, char separator) {
        if (str == null) {
            return null;
        }
        ArrayList<String> strList = new ArrayList<String>();
        StringBuilder split = new StringBuilder();
        int index = 0;
        while ((index = findNext(str, separator, escapeChar, index, split)) >= 0) {
            ++index; // move over the separator for next search
            strList.add(split.toString());
            split.setLength(0); // reset the buffer
        }
        strList.add(split.toString());
        // remove trailing empty split(s)
        int last = strList.size(); // last split
        while (--last >= 0 && "".equals(strList.get(last))) {
            strList.remove(last);
        }
        return strList.toArray(new String[strList.size()]);
    }


    /**
     * Finds the first occurrence of the separator character ignoring the escaped
     * separators starting from the index. Note the substring between the index
     * and the position of the separator is passed.
     *
     * @param str        the source string
     * @param separator  the character to find
     * @param escapeChar character used to escape
     * @param start      from where to search
     * @param split      used to pass back the extracted string
     */

    public static int findNext(String str, char separator, char escapeChar,
                               int start, StringBuilder split) {
        int numPreEscapes = 0;
        for (int i = start; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (numPreEscapes == 0 && curChar == separator) { // separator
                return i;
            } else {
                split.append(curChar);
                numPreEscapes = (curChar == escapeChar)
                        ? (++numPreEscapes) % 2
                        : 0;
            }
        }
        return -1;
    }


    /**
     * Escape commas in the string using the default escape char
     *
     * @param str a string
     * @return an escaped string
     */
    public static String escapeString(String str) {
        return escapeString(str, ESCAPE_CHAR, COMMA);
    }


    /**
     * Escape <code>charToEscape</code> in the string
     * with the escape char <code>escapeChar</code>
     *
     * @param str          string
     * @param escapeChar   escape char
     * @param charToEscape the char to be escaped
     * @return an escaped string
     */
    public static String escapeString(
            String str, char escapeChar, char charToEscape) {
        return escapeString(str, escapeChar, new char[]{charToEscape});
    }


    // check if the character array has the character
    private static boolean hasChar(char[] chars, char character) {
        for (char target : chars) {
            if (character == target) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param charsToEscape array of characters to be escaped
     */
    public static String escapeString(String str, char escapeChar,
                                      char[] charsToEscape) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        // Let us specify good enough capacity to constructor of StringBuilder sothat
        // resizing would not be needed(to improve perf).
        StringBuilder result = new StringBuilder((int) (len * 1.5));

        for (int i = 0; i < len; i++) {
            char curChar = str.charAt(i);
            if (curChar == escapeChar || hasChar(charsToEscape, curChar)) {
                // special char
                result.append(escapeChar);
            }
            result.append(curChar);
        }
        return result.toString();
    }


    /**
     * Unescape commas in the string using the default escape char
     *
     * @param str a string
     * @return an unescaped string
     */
    public static String unEscapeString(String str) {
        return unEscapeString(str, ESCAPE_CHAR, COMMA);
    }


    /**
     * Unescape <code>charToEscape</code> in the string
     * with the escape char <code>escapeChar</code>
     *
     * @param str          string
     * @param escapeChar   escape char
     * @param charToEscape the escaped char
     * @return an unescaped string
     */
    public static String unEscapeString(
            String str, char escapeChar, char charToEscape) {
        return unEscapeString(str, escapeChar, new char[]{charToEscape});
    }


    /**
     * @param charsToEscape array of characters to unescape
     */
    public static String unEscapeString(String str, char escapeChar,
                                        char[] charsToEscape) {
        if (str == null) {
            return null;
        }
        StringBuilder result = new StringBuilder(str.length());
        boolean hasPreEscape = false;
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (hasPreEscape) {
                if (curChar != escapeChar && !hasChar(charsToEscape, curChar)) {
                    // no special char
                    throw new IllegalArgumentException("Illegal escaped string " + str +
                            " unescaped " + escapeChar + " at " + (i - 1));
                }
                // otherwise discard the escape char
                result.append(curChar);
                hasPreEscape = false;
            } else {
                if (hasChar(charsToEscape, curChar)) {
                    throw new IllegalArgumentException("Illegal escaped string " + str +
                            " unescaped " + curChar + " at " + i);
                } else if (curChar == escapeChar) {
                    hasPreEscape = true;
                } else {
                    result.append(curChar);
                }
            }
        }
        if (hasPreEscape) {
            throw new IllegalArgumentException("Illegal escaped string " + str +
                    ", not expecting " + escapeChar + " in the end.");
        }
        return result.toString();
    }


    /**
     * Return hostname without throwing exception.
     *
     * @return hostname
     */
    public static String getHostname() {
        try {
            return "" + InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            return "" + uhe;
        }
    }


    /**
     * Return a message for logging.
     *
     * @param prefix prefix keyword for the message
     * @param msg    content of the message
     * @return a message for logging
     */
    private static String toStartupShutdownString(String prefix, String[] msg) {
        StringBuffer b = new StringBuffer(prefix);
        b.append("\n/************************************************************");
        for (String s : msg) {
            b.append("\n" + prefix + s);
        }
        b.append("\n************************************************************/");
        return b.toString();
    }


    /**
     * The traditional binary prefixes, kilo, mega, ..., exa,
     * which can be represented by a 64-bit integer.
     * TraditionalBinaryPrefix symbol are case insensitive.
     */
    public static enum TraditionalBinaryPrefix {
        /*KB数量*/
        KILO(1024),
        /*KB数量*/
        MEGA(KILO.value << 10),
        /*KB数量*/
        GIGA(MEGA.value << 10),
        /*KB数量*/
        TERA(GIGA.value << 10),
        /*KB数量*/
        PETA(TERA.value << 10),
        /*PPB数量*/
        EXA(PETA.value << 10);

        public final long value;

        public final char symbol;


        TraditionalBinaryPrefix(long value) {
            this.value = value;
            this.symbol = toString().charAt(0);
        }


        /**
         * @return The TraditionalBinaryPrefix object corresponding to the symbol.
         */
        public static TraditionalBinaryPrefix valueOf(char symbol) {
            symbol = Character.toUpperCase(symbol);
            for (TraditionalBinaryPrefix prefix : TraditionalBinaryPrefix.values()) {
                if (symbol == prefix.symbol) {
                    return prefix;
                }
            }
            throw new IllegalArgumentException("Unknown symbol '" + symbol + "'");
        }


        /**
         * Convert a string to long.
         * The input string is first be trimmed
         * and then it is parsed with traditional binary prefix.
         * <p/>
         * For example,
         * "-1230k" will be converted to -1230 * 1024 = -1259520;
         * "891g" will be converted to 891 * 1024^3 = 956703965184;
         *
         * @param s input string
         * @return a long value represented by the input string.
         */
        public static long string2long(String s) {
            s = s.trim();
            final int lastpos = s.length() - 1;
            final char lastchar = s.charAt(lastpos);
            if (Character.isDigit(lastchar)) {
                return Long.parseLong(s);
            } else {
                long prefix = TraditionalBinaryPrefix.valueOf(lastchar).value;
                long num = Long.parseLong(s.substring(0, lastpos));
                if (num > (Long.MAX_VALUE / prefix) || num < (Long.MIN_VALUE / prefix)) {
                    throw new IllegalArgumentException(s + " does not fit in a Long");
                }
                return num * prefix;
            }
        }
    }

    public static boolean checkBirthDay(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean result = true;
        Date date1 = null;
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            result = false;
        }
        if (date1 != null) {
            // 获取当前日期的毫秒数
            long currentTime = System.currentTimeMillis();

            // 获取生日的毫秒数
            long birthTime = date1.getTime();

            Calendar calendar = Calendar.getInstance();
            Calendar birth = Calendar.getInstance();
            birth.setTime(date1);
            if (calendar.get(Calendar.YEAR) - birth.get(Calendar.YEAR) > 120) {
                return false;
            }
            // 如果当前时间小于生日，生日不合法。反之合法
            if (birthTime > currentTime) {
                return false;
            }
        }

        return result;
    }

    /**
     * Escapes HTML Special characters present in the string.
     *
     * @param string
     * @return HTML Escaped String representation
     */
    public static String escapeHTML(String string) {
        if (string == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean lastCharacterWasSpace = false;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (c == ' ') {
                if (lastCharacterWasSpace) {
                    lastCharacterWasSpace = false;
                    sb.append("&nbsp;");
                } else {
                    lastCharacterWasSpace = true;
                    sb.append(" ");
                }
            } else {
                lastCharacterWasSpace = false;
                switch (c) {
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    default:
                        sb.append(c);
                        break;
                }
            }
        }

        return sb.toString();
    }


    /**
     * Return an abbreviated English-language desc of the byte length
     */
    public static String byteDesc(long len) {
        double val = 0.0;
        String ending = "";
        if (len < 1024 * 1024) {
            val = (1.0 * len) / 1024;
            ending = " KB";
        } else if (len < 1024 * 1024 * 1024) {
            val = (1.0 * len) / (1024 * 1024);
            ending = " MB";
        } else if (len < 1024L * 1024 * 1024 * 1024) {
            val = (1.0 * len) / (1024 * 1024 * 1024);
            ending = " GB";
        } else if (len < 1024L * 1024 * 1024 * 1024 * 1024) {
            val = (1.0 * len) / (1024L * 1024 * 1024 * 1024);
            ending = " TB";
        } else {
            val = (1.0 * len) / (1024L * 1024 * 1024 * 1024 * 1024);
            ending = " PB";
        }
        return limitDecimalTo2(val) + ending;
    }


    /**
     * Capitalize a word
     *
     * @param s the input string
     * @return capitalized string
     */
    public static String capitalize(String s) {
        int len = s.length();
        if (len == 0) {
            return s;
        }
        return new StringBuilder(len).append(Character.toTitleCase(s.charAt(0)))
                .append(s.substring(1)).toString();
    }


    /**
     * Convert SOME_STUFF to SomeStuff
     *
     * @param s input string
     * @return camelized string
     */
    public static String camelize(String s) {
        StringBuilder sb = new StringBuilder();
        String[] words = split(s.toLowerCase(Locale.US), ESCAPE_CHAR, '_');

        for (String word : words) {
            sb.append(capitalize(word));
        }

        return sb.toString();
    }


    public static synchronized String limitDecimalTo2(double d) {
        return DECIMAL_FORMAT.format(d);
    }


    /**
     * Make a string representation of the exception.
     *
     * @param e The exception to stringify
     * @return A string with exception name and call stack.
     */
    public static String stringifyException(Throwable e) {
        StringWriter stm = new StringWriter();
        PrintWriter wrt = new PrintWriter(stm);
        e.printStackTrace(wrt);
        wrt.close();
        return stm.toString();
    }


    public static String unicode(String str) {
        StringBuilder buffer = new StringBuilder();
        char[] tmpUnicode = new char[4];
        for (char c : str.toCharArray()) {
            if (c == 34 || c == 92 || c == 47) {
                buffer.append("\\").append(c);
            } else if (c == 8) {
                buffer.append("\\b");
            } else if (c == 12) {
                buffer.append("\\f");
            } else if (c == 10) {
                buffer.append("\\n");
            } else if (c == 13) {
                buffer.append("\\r");
            } else if (c == 9) {
                buffer.append("\\t");
            } else if (c > 127) {
                //System.out.println("Integer.toHexString(c) = " + Integer.toHexString(c));
                tmpUnicode[3] = DIGITS[c & 0xF];
                tmpUnicode[2] = DIGITS[c >> 4 & 0xF];
                tmpUnicode[1] = DIGITS[c >> 8 & 0xF];
                tmpUnicode[0] = DIGITS[c >> 12 & 0xF];
                //System.out.println("tmpUnicode(c):" + Arrays.toString(tmpUnicode));
                buffer.append("\\u").append(tmpUnicode);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }


    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }


    public static Float toFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return 0f;
        }
    }


    public static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int toInt2(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -2;
        }
    }


    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static boolean notEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 至少有一个字符串为空白
     *
     * @param strs
     * @return
     */
    public static boolean hasEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    public static List<String> toStringList(String str) {
        return Arrays.asList(str.split(","));
    }


    public static List<Long> toLongList(String str) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }

        ArrayList<Long> longList = new ArrayList<Long>();
        for (String s : str.split(",")) {
            longList.add(Long.valueOf(s));
        }
        return longList;
    }


    public static String toString(List<?> longList) {
        if (longList == null || longList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object aLong : longList) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(aLong);
        }
        return sb.toString();
    }


    public static String join(char sp, List<?> parameters) {
        StringBuilder sb = new StringBuilder();
        for (Object i : parameters) {
            if (i == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(sp);
            }
            sb.append("'").append(i).append("'");
        }
        return sb.toString();
    }


    public static String genRandom(int len) {
        char[] randomArray = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            randomArray[i] = DIGITS[Math.abs(r.nextInt(DIGITS.length))];
        }
        return new String(randomArray);
    }

    /**
     * 获取验证码
     * @return
     */
    public static String randomSix() {
        String a = "0123456789";
        String valCode = "";
        char[] rands = new char[6];
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        for (int i = 0; i < rands.length; i++) {
            valCode += rands[i];
        }
        return valCode;
    }


    public static String getNewIconName(String base, String end, String oldIcon) {
        if (StringUtil.isEmpty(oldIcon) || !oldIcon.contains(base)) {
            return base + "_1" + end;
        }

        Pattern pattern = compile(base + "_([0-9]+)" + end);

        Matcher matcher = pattern.matcher(oldIcon);
        if (!matcher.matches()) {
            return base + "_1" + end;
        }

        int i = Integer.parseInt(matcher.group(1));
        return base + "_" + (i + 1) + end;

    }


    public static String getSuffix(String name) {
        if (isEmpty(name)) {
            return "";
        }
        int dot = name.lastIndexOf('.');
        if (dot == -1) {
            return "";
        }
        return name.substring(dot);
    }

    public static boolean checkDateFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        boolean result = true;
        try {
            format.parse(date);
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }


    public static boolean checkStringLength(String value, int min, int max) {
        if (StringUtil.notEmpty(value) && value.length() <= max && value.length() >= min) {
            return true;
        }
        return false;
    }

    public static String fixJSONStr(String str) {
        return str.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\").replaceAll("\\\\\\\\/", "/").replaceAll("[\"][{]", "{").replaceAll("[}][\"]", "}");
    }


    public static void main(String[] args) {
        String speed = speed(22321321332321L, 321444);
        System.out.println("speed = " + isPhoneLegal("18660473575"));

//        String suffix = getSuffix("a.tesfc.a.b.d.e.f.dasfds.txt");
//        System.out.println("suffix = " + suffix);
//
//        String newIcon = getNewIconName("base", ".jpg", "base_11235.jpg");
//        System.out.println("newIcon = " + newIcon);
//        for (int i = 0; i < 10000; i++) {
//            String s = StringUtil.genRandom(12);
//            System.out.println("s = " + s);
//        }
//        long s = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            StringUtil.md5("123456789801234567890123456789012345678901234567890");
//        }
//        System.out.println("s = " + (System.currentTimeMillis() - s));
//        String fdsafsdfds = StringUtil.md5("fdsafsdfds");
//        System.out.println("fdsafsdfds = " + fdsafsdfds);
//        char[] sChar = new char[]{'\"', '\\', '/', '\b', '\f', '\n', '\r', '\t'};
//        String testStr = "你\\ \" \' 丫的\n共\t产党！Fuck!" + new String(sChar) + "end";
//        String unicode = StringUtil.unicode(testStr);
//        System.out.println("unicode = " + unicode);
//
//
//        String s = JSON.toJSONString(testStr);
//        System.out.println("s = " + s);

//        long s = System.currentTimeMillis();
//        for(int i=0;i<1000000;i++){
//           StringUtil.unicode(testStr);
//        }
//        System.out.println((System.currentTimeMillis() - s));
//        for(int i=0;i<1000000;i++){
//            StringUtil.unicode(testStr);
//        }
//        System.out.println((System.currentTimeMillis() - s));
//        for(int i=0;i<1000000;i++){
//            StringUtil.unicode(testStr);
//        }
//        System.out.println((System.currentTimeMillis() - s));
//        for(int i=0;i<1000000;i++){
//             StringUtil.unicode(testStr);
//        }
//
//        System.out.println((System.currentTimeMillis() - s));
//        for(int i=0;i<1000000;i++){
//             StringUtil.unicode(testStr);
//        }
//        System.out.println((System.currentTimeMillis() - s));
        //  StringUtil.startupShutdownMessage(StringUtil.class, new String[]{"xxx"}, StringUtil.log);

    }


    public static boolean allowVersion(String allowVersion, String clientVersion) {
        try {
            allowVersion = allowVersion.replace("-r", "");
            clientVersion = clientVersion.replace("-r", "");
            if (StringUtil.toLong(allowVersion) <= StringUtil.toLong(clientVersion)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkDate(String startdate, String enddate) {
        String[] startdates = startdate.split("-");
        String[] enddates = enddate.split("-");
        if (startdates.length == 3 && enddates.length == 3) {
            for (int i = 0; i < 3; i++) {
                if (toInt(startdates[i]) == 0) {
                    return false;
                }
                if (toInt(enddates[i]) == 0) {
                    return false;
                }
            }
        } else {
            return false;
        }
        int startYear = toInt(startdates[0]);
        int endYear = toInt(enddates[0]);
        int startMonth = toInt(startdates[1]);
        int endMonth = toInt(enddates[1]);
        int startDay = toInt(startdates[2]);
        int endDay = toInt(enddates[2]);

        if (checkStartYearMonthAndDay(startYear, startMonth, startDay)) {
            if (checkEndYearMonthAndDay(startYear, startMonth, startDay, endYear, endMonth, endDay)) {
                ////  此时日期格式判断正确
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public static boolean checkMonth(int month) {
        if (month <= 12 && month >= 1) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkStartYearMonthAndDay(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        int nowDay = cal.get(Calendar.DATE);
        int nowMonth = cal.get(Calendar.MONTH) + 1;
        int nowYear = cal.get(Calendar.YEAR);

        if (year >= nowYear && year - nowYear <= 3) {  //////开始年限大于等于当前年限，不超过当前年限3年；
            if (year == nowYear) {
                if (month >= nowMonth) {
                    if (month == nowMonth) {
                        if (day < nowDay) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            boolean leap = isLeapYear(year);    ////true为闰年，2月为29天；否则为平年，2月为28天
            if (month <= 12 && month >= 1) {
                switch (month) {
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (day <= 30 && day >= 1) {
                            return true;
                        } else {
                            return false;
                        }
                    case 2:
                        if (leap) {
                            if (day <= 29 && day >= 1) {           //闰年
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            if (day <= 28 && day >= 1) { //平年
                                return true;
                            } else {
                                return false;
                            }
                        }
                    default:
                        if (day <= 31 && day >= 1) {
                            return true;
                        } else {
                            return false;
                        }
                }
            } else {
                return false;
            }

        } else {
            return false;
        }

    }


    public static boolean checkEndYearMonthAndDay(int startyear, int startMonth, int startDay, int year, int month, int day) {
        if (year >= startyear && year - startyear <= 3) {  //////结束年限大于等于开始年限，不大于开始年限3年
            if (year == startyear) {
                if (month >= startMonth) {
                    if (month == startMonth) {
                        if (day < startDay) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            if (year - startyear == 3) {
                if (month > startMonth) {
                    return false;
                } else {
                    if (day > startDay) {
                        return false;
                    }
                }
            }
            boolean leap = isLeapYear(year);    ////true为闰年，2月为29天；否则为平年，2月为28天
            if (month <= 12 && month >= 1) {
                switch (month) {
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (day <= 30 && day >= 1) {
                            return true;
                        } else {
                            return false;
                        }
                    case 2:
                        if (leap) {
                            if (day <= 29 && day >= 1) {           //闰年
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            if (day <= 28 && day >= 1) { //平年
                                return true;
                            } else {
                                return false;
                            }
                        }
                    default:
                        if (day <= 31 && day >= 1) {
                            return true;
                        } else {
                            return false;
                        }
                }
            } else {
                return false;
            }

        } else {
            return false;
        }

    }


    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkLoction(Double longitude, double latitude) {
        try {
            if (latitude > 90 || latitude < -90) {
                return false;
            } else if (longitude > 180 || longitude < -180) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            //经纬度不是数字
            return false;
        }
    }


    public static boolean checkEmail(String email) {
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkUrl(String url) {
        if (url == null || "".equals(url.trim())) {
            return true;
        }
        String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
        Pattern p = compile(regEx);
        Matcher matcher = p.matcher(url);
        return matcher.matches();
    }

    public static boolean checkFax(String fax) {
        if (fax == null || "".equals(fax.trim())) {
            return true;
        }
        String regx = "0\\d{2,3}-\\d{7,8}";
        Pattern p = compile(regx);
        Matcher m = p.matcher(fax);
        return m.matches();
    }

    public static boolean checkTel(String str) {
        String string = notEmpty(str) ? str : "";
        long intStr = -1;
        if ("".equals(string)) {
            return false;
        } else {
            try {
                intStr = Long.parseLong(str);
                if (intStr > 0) {
                    if (str.length() == 11 || str.length() == 12) {
                        if (str.charAt(0) == '0' || str.charAt(0) == '1') {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    //校验名称是否为汉字、字母、数字、特殊字符+-._/() 或组合
    public static boolean checkName(String name) {
        String REGEX = "^[a-zA-Z0-9\\u4e00-\\u9fa5\\(\\)/\\-+._]+$";
        Pattern pattern = compile(REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private static String htmlEncode(char c) {

        switch (c) {

            case '&':

                return "&amp;";

            case '<':

                return "&lt;";

            case '>':

                return "&gt;";

            case '"':

                return "&quot;";

            case ' ':

                return "&nbsp;";

            default:

                return c + "";

        }

    }

    public static String escape(String keyword) {
        if (keyword.contains("%") || keyword.contains("_")) {
            keyword = keyword.replaceAll("\\\\", "\\\\\\\\")
                    .replaceAll("\\%", "\\\\%")
                    .replaceAll("\\_", "\\\\_");
        }
        return keyword;
    }

    /**
     * 对传入的字符串str进行Html encode转换
     * 反XSS攻击
     */

    public static String htmlEncode(String str) {

        if (str == null || "".equals(str.trim())) {
            return str;
        }

        StringBuilder encodeStrBuilder = new StringBuilder();

        for (int i = 0, len = str.length(); i < len; i++) {

            encodeStrBuilder.append(htmlEncode(str.charAt(i)));

        }

        return encodeStrBuilder.toString();

    }

    /**
     * <p>Left pad a String with a specified String.</p>
     *
     * <p>Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str 目标字符串
     * @return 是与否
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        } else {
            int sz = str.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

}
