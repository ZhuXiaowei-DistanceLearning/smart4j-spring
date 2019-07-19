package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.utils.ReflectionUtil;

/**
 * 获取到类和对象，存放在一个静态Map中，key对应类名，value对应实例
 * 
 * @author zxw
 *
 */
public final class BeanHelper {
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for (Class<?> beanClass : beanClassSet) {
			Object object = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, object);
		}
	}

	/**
	 * 获取Bean映射
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取Bean实例
	 */
	public static <T> T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			throw new RuntimeException("不能获取到类" + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}

	/**
	 * 	存放Bean实例
	 */
	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}
