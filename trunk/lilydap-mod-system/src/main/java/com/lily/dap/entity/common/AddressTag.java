/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.entity.common;

import java.io.Serializable;

/**
 * <code>AddressTag</code>
 * <p>地址标记接口，对于需要由地址分析器管理的地址实现本接口</p>
 *
 * @author 邹学模
 * @date 2008-3-27
 */
public interface AddressTag {
	/**
	 * 返回地址类型
	 *
	 * @return
	 */
	public String getAddressType();
	
	/**
	 * 返回地址主键值
	 *
	 * @return
	 */
	public Serializable getSerializable();
}
