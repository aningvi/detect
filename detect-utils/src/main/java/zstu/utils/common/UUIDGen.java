package zstu.utils.common;

import java.util.UUID;

/**
 *
 * @author liupeizhi
 *
 */
public class UUIDGen {
	public static String generate(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
