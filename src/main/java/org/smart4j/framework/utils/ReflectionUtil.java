package org.smart4j.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {
	/**
	 * 创建实例
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			System.out.println("实例化失败");
			throw new RuntimeException();
		}
		return instance;
	}

	/**
	 * 调用方法
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
			System.out.println("方法映射执行失败");
			throw new RuntimeException();
		}
		return result;
	}
	
	/**
	 * 设置成员变量的值
	 */
	public static void setField(Object obj,Field field,Object value) {
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (Exception e) {
			System.out.println("设置成员变量值失败");
			throw new RuntimeException();
		}
		
	}
	
}
