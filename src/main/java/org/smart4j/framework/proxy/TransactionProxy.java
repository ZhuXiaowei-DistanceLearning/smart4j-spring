package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.smart4j.framework.annotaion.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

public class TransactionProxy implements Proxy {
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {

		@Override
		protected Boolean initialValue() {
			return false;
		}

	};

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		Boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if (!flag && method.isAnnotationPresent(Transaction.class)) {
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransaction();
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				throw e;
			} finally {
				FLAG_HOLDER.remove();
			}
		} else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}
}
