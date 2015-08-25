/**
 * 
 */
package com.lily.dap.util.file;

/**
 * @author xuemozou
 * 
 *         文件删除过滤器
 */
public interface RemoveFilter {
	/**
	 * 是否允许文件删除,true：允许删除；false：不允许删除
	 * 
	 * @param level
	 *            目录深度层级，根目录深度为0，根目录下文件深度为1，往下一级目录下文件深度为2，往下依次类推
	 * @param directory
	 *            删除的源目录
	 * @param filename
	 *            删除的文件名
	 * @param isDirectory
	 *            要删除的文件是否是目录
	 * @return
	 */
	public boolean filter(int level, String directory, String filename,
			boolean isDirectory);
}
