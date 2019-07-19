package org.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 	代理管理器
 * 	切面类在目标方法被调用前后增加相应的逻辑
 * 	提供一个创建所有代理对象的方法
 * 	输入一个目标类和一组Proxy接口,输出一个代理对象
 * @author zxw
 *
 */
public class ProxyManager {
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param targetClass 目标类
	 * @param proxyList 代理对象
	 * @return
	 */
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		// 第一个参数为目标类
		// 第二个参数为回调方法
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
						.doProxyChain();
			}
		});
	}
}
