package org.smart4j.framework.bean;

import java.io.InputStream;

public class FileParam {
	// 字段名
	private String filedName;
	// 文件名
	private String fileName;
	// 文件大小
	private long fileSize;
	// 文件类型
	private String contentType;
	// 输入流
	private InputStream inputStream;

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public FileParam(String filedName, String fileName, long fileSize, String contentType, InputStream inputStream) {
		super();
		this.filedName = filedName;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

}
