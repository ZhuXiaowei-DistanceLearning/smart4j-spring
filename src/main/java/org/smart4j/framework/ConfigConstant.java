package org.smart4j.framework;

public interface ConfigConstant {
	String CONFIG_FILE = "smart.properties";

	String JDBC_DRVIER = "smart.framework.jdbc.driver";
	String JDBC_USERNAME = "smart.framework.jdbc.username";
	String JDBC_PASSWORD = "smart.framework.jdbc.password";
	String JDBC_URL = "smart.framework.jdbc.url";
	String APP_UPLOAD_LIMIT = "smart.framework.app.upload_limit";

	/**
	 *  jsp:jsp地址 asset:静态资源
	 */
	String APP_BASE_PACKAGE = "smart.framework.app.base_package";
	String APP_JSP_PATH = "smart.framework.app.jsp_path";
	String APP_ASSET_PATH = "smart.framework.app.asset_path";
}
