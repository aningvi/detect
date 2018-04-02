package zstu.utils.annotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

/**
 * 常用的正则表达式
 * @author Aning
 */
public class RegexUtils {

	/**
	 * 判断是否是正确的IP地址
	 * 
	 * @param ip
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isIp(String ip) {
		if (null == ip || "".equals(ip)) {
            return false;
        }
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		return ip.matches(regex);
	}

	/**
	 * 验证邮箱
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String str) {
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}


	/**
	 * 验证网址Url
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUrl(String str) {
		String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
		return match(regex, str);
	}


	/**
	 * @param regex
	 * 正则表达式字符串
	 * @param str
	 * 要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断是否含有中文，仅适合中国汉字，不包括标点
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isChinese(String text) {
		if (null == text || "".equals(text)) {
            return false;
        }
		Pattern p = compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(text);
		return m.find();
	}

	/**
	 * 判断是否正整数
	 * 
	 * @param number
	 *            数字
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isNumber(String number) {
		if (null == number || "".equals(number)) {
            return false;
        }
		String regex = "[0-9]*";
		return number.matches(regex);
	}

	/**
	 * 判断几位小数(正数)
	 * 
	 * @param decimal
	 *            数字
	 * @param count
	 *            小数位数
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean isDecimal(String decimal, int count) {
		if (null == decimal || "".equals(decimal)) {
            return false;
        }
		String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count
				+ "})?$";
		return decimal.matches(regex);
	}


	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneNumber(String str) {

		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 * 此方法中前三位格式有：
	 * 13+任意数
	 * 15+除4的任意数
	 * 18+任意数
	 * 17+除9的任意数
	 * 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
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

	/**
	 * 判断是否含有特殊字符
	 * 
	 * @param text
	 * @return boolean true,通过，false，没通过
	 */
	public static boolean hasSpecialChar(String text) {
		if (null == text || "".equals(text)) {
            return false;
        }
		if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
			// 如果不包含特殊字符
			return true;
		}
		return false;
	}
	
	/**
	 * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
	 */
	public static boolean isChinese2(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static boolean isSex(int sex) {
		if (sex < 0 || sex > 2) {
			return false;
		}
		return true;
	}

	public static boolean isStatus(String status) {
		String regex = "^[01]$";
		return status.matches(regex);
	}


}
