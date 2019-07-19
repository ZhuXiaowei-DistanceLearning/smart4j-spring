package org.smart4j.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.utils.CastUtils;
import org.smart4j.framework.utils.CollectionUtil;

public class Param {
	private Map<String, Object> paramMap;

	private List<FormParam> formParamList;

	private List<FileParam> fileParamLsit;

	public Param(List<FormParam> formParamList) {
		this.formParamList = formParamList;
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamLsit) {
		this.formParamList = formParamList;
		this.fileParamLsit = fileParamLsit;
	}

	public List<FormParam> getFormParamList() {
		return formParamList;
	}

	public void setFormParamList(List<FormParam> formParamList) {
		this.formParamList = formParamList;
	}

	public List<FileParam> getFileParamLsit() {
		return fileParamLsit;
	}

	public void setFileParamLsit(List<FileParam> fileParamLsit) {
		this.fileParamLsit = fileParamLsit;
	}

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 判断参数是否为空
	 */
	public boolean isEmpty() {
		return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamLsit);
	}

	/**
	 * 根据参数名称获取long型参数值
	 */
	public long getLong(String name) {
		return CastUtils.castLong(paramMap.get(name));
	}

	/**
	 * 获取所有字段信息
	 */
	public Map<String, Object> getMap() {
		return paramMap;
	}

	/**
	 * 获取请求参数映射
	 */
	public Map<String, Object> getFieldMap() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		if (CollectionUtil.isNotEmpty(formParamList)) {
			for (FormParam formParam : formParamList) {
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				if (fieldMap.containsKey(fieldName)) {
					fieldValue = fieldMap.get(fieldName) + String.valueOf((char) 29) + fieldValue;
				}
				fieldMap.put(fieldName, fieldValue);
			}
		}
		return fieldMap;

	}

	/**
	 * 获取上传文件映射
	 */
	public Map<String, List<FileParam>> getFileMap() {
		Map<String, List<FileParam>> fileMap = new HashMap<>();
		if (CollectionUtil.isNotEmpty(fileParamLsit)) {
			for (FileParam fileParam : fileParamLsit) {
				String filedName = fileParam.getFiledName();
				List<FileParam> fileParamList;
				if (fileMap.containsKey(filedName)) {
					fileParamList = fileMap.get(filedName);
				} else {
					fileParamList = new ArrayList<FileParam>();
				}
				fileParamList.add(fileParam);
				fileMap.put(filedName, fileParamList);
			}
		}
		return fileMap;
	}

	/**
	 * 获取所有上传的文件
	 */
	public List<FileParam> getFileList(String fieldName) {
		return getFileMap().get(fieldName);
	}

	/**
	 * 获取唯一上传文件
	 */
	public FileParam getFile(String fieldName) {
		List<FileParam> list = getFileList(fieldName);
		if (CollectionUtil.isNotEmpty(list) && fileParamLsit.size() == 1) {
			return fileParamLsit.get(0);
		}
		return null;
	}

	/**
	 * 验证参数是否为空
	 */
	public boolean FileisEmpty() {
		return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamLsit);
	}

	/**
	 * 根据参数名获取String参数值
	 */
	public String getString(String name) {
		return CastUtils.castString(getFieldMap().get(name));
	}

	/**
	 * 根据参数名获取double型参数
	 */
	public double getDouble(String name) {
		return CastUtils.castDouble(getFieldMap().get(name));
	}

	/**
	 * 根据参数名获取int型参数
	 */
	public int getInt(String name) {
		return CastUtils.castInt(getFieldMap().get(name));
	}

	/**
	 * 根据参数名获取boolean型参数
	 */
	public boolean getBoolean(String name) {
		return CastUtils.castBoolean(getFieldMap().get(name));
	}

}