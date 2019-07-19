package org.smart4j.framework;

import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.utils.ClassUtils;

/**
 * 加载相应的Hepler类
 * 
 * @author zxw
 *
 */
public final class HelperLoader {
	public static void init() {
		Class<?>[] classList = {
				// 获取所有的类
				ClassHelper.class,
				// 将类实例化
				BeanHelper.class,
				// AOP核心：通过代理对象去执行方法
				// AOP初始化阶段主要任务，实例化每个目标类的代理对象
				// AOP实例化，初始化获取所有目标类与代理对象之间的映射关系
				// 1.创建代理对象
				// 2.怎么样创建代理对象，创建代理对象所需要的是 目标类和代理类
				// 3.首先映射出目标类与代理类之间的关系,通过Set<代理类,List<目标类>>
				// 4.通过映射关系获取目标类与代理对象集合之间的关系, 实例化代理类，然后通过Map添加映射关系
				// 5.创建代理对象(实例化代理对象和创建代理对象不是同一概念)
				AopHelper.class,
				// 将注入的对象进行实例化
				IocHelper.class,
				// 获取所有Controller类
				ControllerHelper.class };
		for (Class<?> cls : classList) {
			ClassUtils.loadClass(cls.getName(), true);
		}
	}
}
