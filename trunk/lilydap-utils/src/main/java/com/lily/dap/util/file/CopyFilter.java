/**
 * 
 */
package com.lily.dap.util.file;

/**
 * @author xuemozou
 * 
 *         文件复制过滤器
 */
public interface CopyFilter {
	/**
	 * 拷贝过滤器，控制当前文件是否拷贝
	 * 
	 * @param level
	 *            目录拷贝深度层级，根目录下文件深度为1，往下一级目录下文件深度为2，往下依次类推
	 * @param srcDirectory
	 *            复制的源目录
	 * @param tgtDirectory
	 *            复制的目标目录
	 * @param filename
	 *            复制到文件名
	 * @param isDirectory
	 *            要复制的文件是否是目录
	 * @return 返回true：允许拷贝，返回false：不允许拷贝
	 */
	public boolean filter(int level, String srcDirectory, String tgtDirectory,
			String filename, boolean isDirectory);
}
