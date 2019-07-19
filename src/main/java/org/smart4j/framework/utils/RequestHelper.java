package org.smart4j.framework.utils;

import java.io.IOException;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;

/**
 * 请求助手类
 * 
 * @author zxw
 *
 */
public final class RequestHelper {

	/**
	 * 创建请求对象
	 * 
	 * @throws IOException
	 */
	public static Param createParam(HttpServletRequest request) throws IOException {
		List<FormParam> formParamList = new ArrayList<>();
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
		return new Param(formParamList);
	}

	private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
		List<FormParam> formParamsList = new ArrayList<>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
		if (StringUtils.isNotEmpty(body)) {
			String[] kvs = StringUtils.split("body", "&");
			if (ArrayUtil.isNotEmpty(kvs)) {
				for (String kv : kvs) {
					String[] array = StringUtils.split(kv, "=");
					if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
						String fieldName = array[0];
						String fieldValue = array[1];
						formParamsList.add(new FormParam(fieldName, fieldValue));
					}
				}
			}
		}
		return formParamsList;
	}

	public static List<FormParam> parseParameterNames(HttpServletRequest request) {
		List<FormParam> formParamsList = new ArrayList<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String fieldName = parameterNames.nextElement();
			String[] fieldValues = request.getParameterValues(fieldName);
			if (ArrayUtil.isNotEmpty(fieldValues)) {
				Object fieldValue;
				if (fieldValues.length == 1) {
					fieldValue = fieldValues[0];
				} else {
					StringBuilder sb = new StringBuilder("");
					for (int i = 0; i < fieldValues.length; i++) {
						sb.append(fieldValues[i]);
						if (i != fieldValues.length - 1) {
							sb.append("");
						}
					}
					fieldValue = sb.toString();
				}
				formParamsList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formParamsList;
	}
}
