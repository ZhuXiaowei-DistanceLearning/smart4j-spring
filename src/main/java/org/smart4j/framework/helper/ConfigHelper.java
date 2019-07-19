package org.smart4j.framework.helper;

import java.util.Properties;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.utils.PropsUtil;

public final class ConfigHelper {
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

	/**
	 * 获取JDBC驱动
	 * 
	 * @return
	 */
	public static String getJdbcDriver() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRVIER);
	}

	/**
	 * 获取jdbc地址
	 * 
	 * @return
	 */
	public static String getJdbcUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}

	/**
	 * 获取jdbc用户名
	 * 
	 * @return
	 */
	public static String getJdbcUsername() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}

	/**
	 * 获取jdbc密码
	 * 
	 * @return
	 */
	public static String getJdbcPassword() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}

	/**
	 * 获取应用基础包名
	 * 
	 * @return
	 */
	public static String getAppBasePackage() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 获取应用jsp路径
	 * 
	 * @return
	 */
	public static String getAppJspPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
	}

	/**
	 * 获取应用静态资源路径
	 * 
	 * @return
	 */
	public static String getAppAssetPath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
	}

	/**
	 * 获取应用文件上传限制
	 */
	public static int getAppUploadLimit() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT);
	}
}
