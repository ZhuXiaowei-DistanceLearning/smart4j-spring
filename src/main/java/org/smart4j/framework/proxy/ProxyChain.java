package org.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理链
 * 	
 * @author zxw
 *
 */
public class ProxyChain {
	// 目标实现类
	private final Class<?> targetClass;
	// 目标对象
	private final Object targetObject;
	// 目标方法
	private final Method targetMethod;
	// 方法代理(CGLIB提供)
	private final MethodProxy methodProxy;
	// 方法参数
	private final Object[] methodParams;
	// 代理列表，所有标注了@Aspect的类
	private List<Proxy> proxyList = new ArrayList<Proxy>();
	// 代理索引
	private int proxyIndex = 0;

	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		super();
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	/**
	 * proxyIndex充当代理对象的计数器
	 * @return
	 * @throws Throwable
	 */
	public Object doProxyChain() throws Throwable {
		Object methodResult;
		// 判断列表大小， 如果小于
		if (proxyIndex < proxyList.size()) {
			// Proxy 接口的实现中会提供相应的横切逻辑，并调用doProxyChain方法
			// 得到标注了@Aspect注解的切面类
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		} else {
			// 执行目标对象的业务逻辑
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
}
