package org.smart4j.framework.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 助手类
 * 
 * @author zxw
 *
 */
public final class ServletHelper {
	/**
	 * 使每个线程独自拥有一份ServletHelper实例
	 */
	private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

	private HttpServletRequest request;
	private HttpServletResponse response;

	public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 初始化
	 */
	public static void init(HttpServletRequest request, HttpServletResponse response) {
		SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
	}

	/**
	 * 销毁
	 */
	public static void destory() {
		SERVLET_HELPER_HOLDER.remove();
	}

	/**
	 * 获取Request对象
	 */
	public static HttpServletRequest getRequest() {
		return SERVLET_HELPER_HOLDER.get().request;
	}

	/**
	 * 获取Response对象
	 */
	public static HttpServletResponse getResponse() {
		return SERVLET_HELPER_HOLDER.get().response;
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

	public static void setRequestAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public static <T> T getRequestAttribute(String key) {
		return (T) getRequest().getAttribute(key);
	}

	public static void removeRequestAttribute(String key) {
		getRequest().removeAttribute(key);
	}
}
