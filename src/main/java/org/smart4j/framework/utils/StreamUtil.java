package org.smart4j.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public final class StreamUtil {
	/**
	 * 	从输入流中获取字符串
	 */
	public static String getString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try {
			// 获取request输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("获取输入流失败");
			throw new RuntimeException();
		}
		// 通过StringBuilder 存储流来进行json转换
		return sb.toString();
	}
	
	/**
	 *	 将输入流复制到输出流
	 */
	public static void copyStream(InputStream inputStream,OutputStream outputStream) {
		try {
			int length;
			byte[] buffer = new byte[4*1024];
			while((length=inputStream.read(buffer,0,buffer.length))!=-1) {
				outputStream.write(buffer,0,length);
			}
			outputStream.flush();
		} catch (Exception e) {
			System.out.println("输入流赋值失败");
			throw new RuntimeException(e);
		}finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e2) {
				System.out.println("流关闭失败");
			}
		}
	}
	
//	try (BufferedReader reader = req.getReader()) {
//		char[] buff = new char[1024];
//		int len;
//		while ((len = reader.read(buff)) != -1) {
//			sb.append(buff, 0, len);
//		}
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
}
