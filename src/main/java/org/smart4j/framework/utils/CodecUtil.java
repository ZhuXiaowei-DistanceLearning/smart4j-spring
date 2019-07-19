package org.smart4j.framework.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CodecUtil {
	/**
	 * 将URL编码
	 */
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			System.out.println("编码转换失败");
			throw new RuntimeException();
		}
		return target;
	}

	/**
	 * 将URL解码
	 */
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "utf-8");
		} catch (Exception e) {
			System.out.println("解码失败");
			throw new RuntimeException();
		}
		return target;
	}
	
	/**
	 * 	加密
	 */
	public static String md5(String source) {
		return null;
	}
}
