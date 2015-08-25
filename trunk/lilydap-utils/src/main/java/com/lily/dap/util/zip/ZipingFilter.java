/**
 * 
 */
package com.lily.dap.util.zip;

/**
 * @author xuemozou
 * 
 *         压缩和解压缩过滤器，控制文件是否需要压缩/解压缩
 */
public interface ZipingFilter {
	/**
	 * 是否允许文件压缩/解压缩,true：允许压缩/解压缩；false：不允许压缩/解压缩
	 * 
	 * @param pathname
	 *            文件在压缩文件中相对路径名
	 * @param isDirectory
	 *            文件是否为目录
	 * @return
	 */
	public boolean filter(String pathname, boolean isDirectory);
}
