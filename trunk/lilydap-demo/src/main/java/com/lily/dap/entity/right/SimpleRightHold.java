/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.entity.right;


/**
 * <code>SimpleRightHold</code>
 * <p>类/接口功能注释</p>
 *
 * @author 邹学模
 * @date 2008-3-31
 */
public class SimpleRightHold implements RightHold {
	private long id;
	
	private String name;
	
	private String privateCode;

	public SimpleRightHold(long id, String name, String privateCode) {
		this.id = id;
		this.name = name;
		this.privateCode = privateCode;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.right.RightHold#getId()
	 */
	public long getId() {
		return id;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.right.RightHold#getName()
	 */
	public String getName() {
		return name;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.right.RightHold#getPrivateRoleCode()
	 */
	public String getPrivateRoleCode() {
		return privateCode;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.right.RightHold#setPrivateRoleCode(java.lang.String)
	 */
	public void setPrivateRoleCode(String roleCode) {
		privateCode = roleCode;
	}
}
