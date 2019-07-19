package org.smart4j.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotaion.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.utils.ArrayUtil;
import org.smart4j.framework.utils.CollectionUtil;

import jdk.internal.org.objectweb.asm.Handle;

/**
 * 控制器助手类
 * 
 * @author zxw
 *
 */
public final class ControllerHelper {
	/**
	 * 用于存放请求与处理器的映射关系
	 */
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

	static {
		// 获取所有的Controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtil.isNotEmpty(controllerClassSet)) {
			// 遍历这些Controller类
			for (Class<?> controllerClass : controllerClassSet) {
				// 获取 controller类中定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				if (ArrayUtil.isNotEmpty(methods)) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(Action.class)) {
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							// 验证URL 映射规则
							if (mapping.matches("\\w+:/\\w*")) {
								String[] array = mapping.split(":");
								if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
									// 获取请求方法与请求路径
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取Handler
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
