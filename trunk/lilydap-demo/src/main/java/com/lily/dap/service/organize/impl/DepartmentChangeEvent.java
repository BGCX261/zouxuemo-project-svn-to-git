/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Department;

/**
 * <code>DepartmentChangeEvent</code>
 * <p>部门信息改变事件</p>
 *
 * @author 邹学模
 * @date 2008-3-26
 */
public class DepartmentChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 2306364160105099383L;

	public static final String ADD = "add";
	
	public static final String REMOVE = "remove";
	
	public static final String MODIFY = "modify";
	
	private Department department;
	
	private String op;
	
	/**
	 * 返回 department.
	 * @return department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * 返回 op.
	 * @return op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * 构造函数
	 *
	 * @param source
	 */
	public DepartmentChangeEvent(Department department, String op) {
		super(op);
		
		this.department = department;
		this.op = op;
	}
}
