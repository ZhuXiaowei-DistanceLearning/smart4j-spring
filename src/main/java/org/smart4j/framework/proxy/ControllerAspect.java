package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.smart4j.framework.annotaion.Aspect;
import org.smart4j.framework.annotaion.Controller;

/**
 * 拦截Controller所有方法
 * 
 * @author zxw
 *
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
	private long begin;

	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		begin = System.currentTimeMillis();
		System.out.println("Controller方法拦截前");
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params,Object object) throws Throwable {
		System.out.println("Controller方法拦截后");
	}

}
