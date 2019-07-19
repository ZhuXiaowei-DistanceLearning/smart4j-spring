package org.smart4j.framework.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * 文件操作工具类
 * 
 * @author zxw
 *
 */
public final class FileUtil {
	/**
	 * 获取真实文件名(自动去掉文件路径)
	 */
	public static String getRealFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	/**
	 * 创建文件
	 */
	public static File createFile(String filePath) {
		File file;
		try {
			file = new File(filePath);
			File parentDir = file.getParentFile();
			if (!parentDir.exists()) {
				FileUtils.forceMkdir(parentDir);
			}
		} catch (Exception e) {
			System.out.println("文件创建失败");
			throw new RuntimeException(e);
		}
		return file;
	}
}
