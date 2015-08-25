/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext;

import java.util.List;

/**
 * <code>ExtItemsInterface</code>
 * <p>存放items数据容器</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 */
public interface ExtItemsInterface {
	/**
	 * 返回存放items数据的script字符串列表
	 *
	 * @return
	 */
	public List<String> getItemsScriptList();
}
