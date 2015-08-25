/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.data;

/**
 * <code>ExtDataTargetInterface</code>
 * <p>数据源使用者接口</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 */
@SuppressWarnings("unchecked")
public interface ExtDataTargetInterface {
	/**
	 * 检索使用者设置的实体类型
	 *
	 * @return
	 */
	public Class getEntity();
	
	/**
	 * 调用使用者的设置数据源脚本方法设置使用者的数据源
	 *
	 * @param dataScript
	 */
	public void setDataScript(String dataScript);
}
