package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.smart4j.framework.annotaion.Controller;
import org.smart4j.framework.annotaion.Service;
import org.smart4j.framework.utils.ClassUtils;

/**
 * 类操作助手类 获取所加载的类，但无法通过类来实例化对象
 * 
 * @author zxw
 *
 */
public final class ClassHelper {
	/**
	 * 定义类集合
	 */
	private static final Set<Class<?>> CLASS_SET;

	static {

		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtils.getClassSet(basePackage);
	}

	/**
	 * 获取应用包名下的所有类
	 */
	private static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}

	/**
	 * 获取应用包名下的所有service类
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(Service.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包名下所有Controller类
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(Controller.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包名下所有Bean类
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> beanCalssSet = new HashSet<>();
		// 获取所有Service类
		beanCalssSet.addAll(getServiceClassSet());
		// 获取所有Class类
		beanCalssSet.addAll(getControllerClassSet());
		return beanCalssSet;
	}

	/**
	 * 	获取应用包名下某父类(或接口)的所有子类(或实现类)
	 * 	需要扩展AspectProxy抽象类的所有具体类
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			// 判断是否为某个类的父类,instance of 判断是否某个类的子类
			if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 	获取应用包名下带有某注解的所有类
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
}
