package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

/**
 * 	提供一个模板方法，并在该抽象类的具体实现中扩展相应的抽象方法 切面代理
 * 	调用ProxyManager
 * 	模板方法设计模式,提供模板算法
 * @author zxw
 *
 */
public abstract class AspectProxy implements Proxy {

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		// 得到切面拦截的类
		Class<?> cls = proxyChain.getTargetClass();
		// 得到要执行的方法
		Method method = proxyChain.getTargetMethod();
		// 得到方法参数
		Object[] params = proxyChain.getMethodParams();
		// 钩子函数
		begin();
		try {
			if (intercept(cls, method, params)) {
				// 钩子函数
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				// 钩子函数
				after(cls, method, params,result);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			System.out.println("切面代理失败");
			throw e;
		} finally {
			end();
		}
		return result;
	}

	public void begin() {

	}

	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

	}

	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

	}

	public void error(Class<?> cls, Method method, Object[] params) throws Throwable {

	}

	public void end() {

	}
}
