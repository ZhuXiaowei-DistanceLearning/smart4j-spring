package org.smart4j.framework.bean;

/**
 * 请求封装信息
 * 
 * @author zxw 1.通过ClassHelper已经获取了所有定义Controller注解的类
 *         通过反射获取该类中所有带有Action注解的方法，获取去Action中的请求表达式，进而获取请求方法与请求路径
 *         封装一个请求对象与处理对象，最后将Request与Handler建立一个映射关系，放入一个Actino Map中
 */
public class Request {
	/**
	 * 请求方法
	 */
	private String requestMethod;

	/**
	 * 请求路径
	 */
	private String requestPath;

	public Request(String requestMethod, String requestPath) {
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		result = prime * result + ((requestPath == null) ? 0 : requestPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (requestMethod == null) {
			if (other.requestMethod != null)
				return false;
		} else if (!requestMethod.equals(other.requestMethod))
			return false;
		if (requestPath == null) {
			if (other.requestPath != null)
				return false;
		} else if (!requestPath.equals(other.requestPath))
			return false;
		return true;
	}

}
