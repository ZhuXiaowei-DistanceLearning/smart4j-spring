package org.smart4j.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	/**
	 * 将POJO转为JSON
	 */
	public static <T> String toJson(T obj) {
		String json;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			System.out.println("转化为json失败");
			throw new RuntimeException();
		}
		return json;
	}
	
	/**
	 * json转为POJO
	 */
	public static <T> T fromJson(String json,Class<T> type) {
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return pojo;
	}
}
