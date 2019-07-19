package org.smart4j.framework.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {

	/**
	 * 加载配置文件
	 *  URI形式的绝对资源路径 如：file:/D:/java/eclipse32/workspace/jbpmtest3/bin/aaa.b
	 *  URL是URI的特例，URL可以打开资源，URI不行
	 */
	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			//this.getClass().getClassLoader().getResource("");得到当前classpath的绝对URI路径
			// this.getClass().getResource(""); 得到当前classpath的绝对URI路径
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName); // 得到当前classpath的绝对URI路径
			if (is == null) {
				throw new FileNotFoundException(fileName + "不存在");
			}
			props = new Properties();
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
					System.out.println("输入流关闭异常");
				}
			}
		}
		return props;
	}

	/**
	 * 获取字符型属性
	 */
	public static String getString(Properties props, String key) {
		return getString(props, key, "");
	}

	public static String getString(Properties props, String key, String defaultValue) {
		String value = defaultValue;
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}

	/**
	 * 获取数字属性
	 */
	public static int getInt(Properties props, String key) {
		return getInt(props, key, 0);
	}

	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtils.castInt(props.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取布尔类型
	 */
	public static boolean getBoolean(Properties props, String key) {
		return getBoolean(props, key, false);
	}

	public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if (props.containsKey(key)) {
			value = CastUtils.castBoolean(props.getProperty(key));
		}
		return value;
	}
}
