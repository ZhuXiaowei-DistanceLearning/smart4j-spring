package org.smart4j.framework.bean;

import java.util.Map;

/**
 * 返回视图对象
 * @author zxw
 *
 */
public class View {
	/**
	 * 视图路径
	 */
	private String path;

	/**
	 * 模型数据
	 */
	private Map<String, Object> model;

	public View(String path) {
		super();
		this.path = path;
	}

	public View addModel(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

}
