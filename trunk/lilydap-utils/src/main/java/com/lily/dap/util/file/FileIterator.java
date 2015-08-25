/**
 * FileIterator.java
 * Copyright (C), 2009 百合软件.
 * Author : 邹学模      
 * 2009-10-5 下午03:35:05
 */
package com.lily.dap.util.file;

/**
 * @ClassName: FileIterator
 * @author 邹学模
 * @Description : 文件遍历器接口，调用文件操作类FileUtil的iterateFiles方法时使用
 * 
 */
public interface FileIterator {
	// 遍历控制，在调用遍历器接口返回时控制下一步的遍历行为
	public static int ITERATE_OK = 1; // 当前文件路径存入返回文件路径列表，并继续遍历
	public static int ITERATE_IGNORE = 2; // 当前文件路径不存入返回文件路径列表，并继续遍历
	public static int ITERATE_STOP = 0; // 当前文件路径不存入返回文件路径列表，并停止遍历

	/**
	 * 遍历调用接口，在遍历文件时调用本接口方法，并根据返回值控制是否继续往下遍历
	 * 
	 * @param level
	 *            当前遍历目录的深度层级，根目录下文件深度为1，往下一级目录下文件深度为2，往下依次类推
	 * @param dir
	 *            遍历到的文件所在目录
	 * @param file
	 *            遍历到的文件名
	 * @param isDirectory
	 *            遍历到的文件是否是目录
	 * @return 返回值见上面遍历控制常量
	 */
	public int iterate(int level, String dir, String file, boolean isDirectory);
}
