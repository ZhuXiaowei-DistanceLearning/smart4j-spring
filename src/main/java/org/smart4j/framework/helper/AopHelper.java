package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotaion.Aspect;
import org.smart4j.framework.annotaion.Service;
import org.smart4j.framework.annotaion.Transaction;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

public final class AopHelper {

	static {
		// JDK代理3个核心：
		//	1.接口
		//	2.接口实现类
		//	3.代理类
		//	CGLIB动态代理核心：
		//	1.Enhancer.create(Class<?> cls)创建一个动态对象
		//	2.方法
		//	3.参数
		// 	4.MethdoProxy
		// 初始化步骤为了得到代理对象
		try {
			/**
			 * 	获取代理类及其目标类集合的映射关系，进一步获取目标类与代理对象列表的映射关系
			 * 	一个代理类可对应一个或多个目标类
			 * 	遍历映射关系，从中获取目标类与代理对象列表，调用createProxy方法获取代理对象
			 * 	调用BeanHelper.setBean方法，将该代理对象重新方法Bean Map中
			 */
			// 获取代理类和目标类(实现类)之间的映射关系
			// 目前主要存放的是：
			// 普通切面代理类: 实现了@Aspect注解中的类
			// 事务切面代理类: 所有service类
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			// 获取了代理类和目标类(实现类)之间的关系就能获取目标类(实现类)与代理对象之间的映射关系
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			// 循环目标类和代理集合
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				// 获取目标类(实现类)
				Class<?> targetClass = targetEntry.getKey();
				// 获取代理集合
				List<Proxy> proxyList = targetEntry.getValue();
				// 通过代理管理器创建代理对象(返回接口)
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				// 添加到容器中
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			System.out.println("切面初始化失败");
		}
	}

	/**
	 *	 普通切面代理
	 * @param proxyMap
	 * @throws Exception
	 */
	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
		// 获取抽象父类AspectProxy下的所有子类
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		// 遍历所有子类
		for (Class<?> proxyClass : proxyClassSet) {
			// 判断子类上是否标注了Aspect注解
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				// 获取注解
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				// 获取标注了@Aspect注解中的注解的类
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				// 添加代理类与目标类(实现类)之间的关系
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}
	
	/**
	 * 	普通事务代理
	 * @param proxyMap
	 */
	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
		// 获取所有Service注解类
		Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, serviceClassSet); 	
	}

	/**
	 *	 获取Aspect注解中设置的类
	 * 
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
		Set<Class<?>> targetClassSet = new HashSet<>();
		// 得到@Aspect注解中标注的类
		Class<? extends Annotation> annotation = aspect.value();
		// 如果该类不是Aspect类，则可调用ClassHelper.getClassSetByAnnotation获取相关类s
		if (annotation != null && !annotation.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}

	/**
	 * 获取代理类和目标类之间的映射关系 一个代理类可以对应一个或多个目标类 代理类:切面类
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		// 获取普通切面类
		// 获取了所有继承了AspectProxy的类
		// 获取了@Aspect注解中所标注的类
		// 存储格式 代理类:注解中标注的类
		addAspectProxy(proxyMap);
		// 获取事务类
		// 获取所有Service类
		// 存储格式 注解代理类:所有service类
		addTransactionProxy(proxyMap);
		return proxyMap;
	}

	/**
	 * 	目标类与代理对象列表之间的映射关系
	 *	获取所有的目标类及其被拦截的切面类的实例
	 * @param proxyMap
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
			// 得到代理类
			Class<?> proxyClass = proxyEntry.getKey();
			// 得到目标类(实现类)
			// 普通切面类，获取的是标注了@Aspect注解的注解类
			// 事务切面类，获取是service类
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			// 循环目标类(实现类)
			for (Class<?> targetClass : targetClassSet) {
				// 代理类调用实例化方法,得到Proxy对象
				// 获取代理类实例，代理类继承了AspectProxy抽象接口，抽象接口实现了Proxy接口
				Proxy proxy = (Proxy) proxyClass.newInstance();
				// 判断集合中是否包含目标类(实现类)
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(proxy);
				} else {
					// 如果不包含 则创建一个代理集合
					List<Proxy> proxyList = new ArrayList<Proxy>();
					// 添加代理
					proxyList.add(proxy);
					// 添加目标类(实现类)和代理实体
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}

}
