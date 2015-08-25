/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Department;

/**
 * <code>DepartmentChangeEvent</code>
 * <p>������Ϣ�ı��¼�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-26
 */
public class DepartmentChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 2306364160105099383L;

	public static final String ADD = "add";
	
	public static final String REMOVE = "remove";
	
	public static final String MODIFY = "modify";
	
	private Department department;
	
	private String op;
	
	/**
	 * ���� department.
	 * @return department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * ���� op.
	 * @return op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * ���캯��
	 *
	 * @param source
	 */
	public DepartmentChangeEvent(Department department, String op) {
		super(op);
		
		this.department = department;
		this.op = op;
	}
}
