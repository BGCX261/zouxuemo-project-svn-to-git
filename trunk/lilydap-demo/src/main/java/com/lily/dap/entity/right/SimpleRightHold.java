/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.entity.right;


/**
 * <code>SimpleRightHold</code>
 * <p>��/�ӿڹ���ע��</p>
 *
 * @author ��ѧģ
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

	/* ���� Javadoc��
	 * @see com.lily.dap.model.right.RightHold#getId()
	 */
	public long getId() {
		return id;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.right.RightHold#getName()
	 */
	public String getName() {
		return name;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.right.RightHold#getPrivateRoleCode()
	 */
	public String getPrivateRoleCode() {
		return privateCode;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.right.RightHold#setPrivateRoleCode(java.lang.String)
	 */
	public void setPrivateRoleCode(String roleCode) {
		privateCode = roleCode;
	}
}
