package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotaion.Inject;
import org.smart4j.framework.utils.CollectionUtil;
import org.smart4j.framework.utils.ReflectionUtil;

public final class IocHelper {
	static {
		// 获取所有bean类与bean实例之间的映射关系
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if (CollectionUtil.isNotEmpty(beanMap)) {
			// 遍历BeanMap
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				// 从BeanMap中获取Bean类与Bean实例
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				// 获取Bean类定义的所有类成员变量(简称Bean Field)
				Field[] beanFields = beanClass.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(beanFields)) {
					// 遍历Bean Field
					for (Field beanField : beanFields) {
						// 判断当前Bean Field 是否带有Inject 注解
						if (beanField.isAnnotationPresent(Inject.class)) {
							// 在Bean Map中获取Bean Field 对应的实例
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if (beanFieldInstance != null) {
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
