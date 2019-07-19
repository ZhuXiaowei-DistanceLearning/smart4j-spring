package org.smart4j.framework.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.utils.CollectionUtil;

public final class UploadHelper {
	/**
	 * Servlet文件上传对象
	 */
	private static ServletFileUpload servletFileUpload;

	/**
	 * 初始化
	 */
	public static void init(ServletContext servletContext) {
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(
				new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if (uploadLimit != 0) {
			servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
		}
	}

	/**
	 * 判断是否为multipart类型
	 */
	public static boolean isMultpart(HttpServletRequest request) {
		return ServletFileUpload.isMultipartContent(request);
	}

	/**
	 * 创建请求对象
	 * 
	 * @throws IOException
	 */
	public static Param createParam(HttpServletRequest request) throws IOException {
		List<FormParam> formParamList = new ArrayList<FormParam>();
		List<FileParam> fileParams = new ArrayList<FileParam>();
		try {
			Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
			if (CollectionUtil.isNotEmpty(fileItemListMap)) {
				for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
					String fieldName = fileItemListEntry.getKey();
					List<FileItem> fileItemList = fileItemListEntry.getValue();
					if (CollectionUtil.isNotEmpty(fileItemList)) {
						for (FileItem fileItem : fileItemList) {
							if (fileItem.isFormField()) {
								String fieldValue = fileItem.getString("UTF-8");
								formParamList.add(new FormParam(fieldName, fieldValue));
							} else {
								String fileName = FileUtils.getFile(new String(fileItem.getName().getBytes(), "utf-8"))
										.getName();
								if (StringUtils.isNotEmpty(fileName)) {
									long size = fileItem.getSize();
									String contentType = fileItem.getContentType();
									InputStream inputStream = fileItem.getInputStream();
									fileParams.add(new FileParam(fieldName, fileName, size, contentType, inputStream));
								}
							}
						}
					}
				}
			}
		} catch (FileUploadException e) {
			System.out.println("请求对象创建失败");
			throw new RuntimeException();
		}
		return new Param(formParamList, fileParams);
	}
	
	/**
	 * 上传文件
	 */
	public static void uploadFile(String basePath, FileParam fileParam) {
		try {
			if(fileParam != null) {
				String filePath = basePath + fileParam.getFiledName();
//				FileUtils.
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 批量上传文件
	 */
	public static void uploadFile(String basePath,List<FileParam> fileParamList) {
		
	}
}
