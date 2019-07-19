package org.smart4j.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

public class ClassUtils {

	/**
	 * 获取类加载器
	 */
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {
		Class<?> cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 获取指定包名下的所有类
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		Set<Class<?>> classSet = new HashSet<>();
		ClassLoader loader = getClassLoader();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol(); // 获取此url的协议名称
					if (protocol.equals("file")) {
						String packagePath = url.getPath().replaceAll("%20", " ");
						addClass(classSet, packagePath, packageName);
					} else if (protocol.equals("jar")) {
						JarURLConnection openConnection = (JarURLConnection) url.openConnection();
						if (openConnection != null) {
							JarFile jarFile = openConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while (jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if (jarEntryName.endsWith(".class")) {
										String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
												.replaceAll("/", ".");
										doAddClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return classSet;
	}

	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> cls = loadClass(className, false);
		classSet.add(cls);
	}
	
	/**
	 * 该方法传入根路径，与项目基础包名
	 * @param classSet 传入类
	 * @param packagePath 通过getResource获取项目classpath根路径
	 * @param packageName 基础包名
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return (f.isFile() && f.getName().endsWith(".class")) || f.isDirectory();
			}
		});

		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) { // 判断是目录还是文件 
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNotEmpty(packageName)) {
					className = packageName + "." + className; // 得到包路径+类名
				}
				doAddClass(classSet, className);
			} else { // 如果是文件时目录，则进入该目录的下层目录继续寻找文件和目录
				String subPackagePath = fileName;
				if (StringUtils.isNotEmpty(subPackagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath; //首先获取子包的路径
				}
				String subPackageName = fileName;
				if (StringUtils.isNotEmpty(subPackageName)) {
					subPackageName = packageName + "." + subPackageName; // 获取子包的包名
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}
}
