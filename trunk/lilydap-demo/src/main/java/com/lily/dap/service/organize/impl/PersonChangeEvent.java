/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Person;

/**
 * <code>PersonChangeEvent</code>
 * <p>人员信息改变事件</p>
 *
 * @author 邹学模
 * @date 2008-3-26
 */
public class PersonChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -7721413377252965269L;

	public static final String ADD = "add";
	
	public static final String REMOVE = "remove";
	
	public static final String MODIFY = "modify";
	
	private Person person;
	
	private String op;
	
	/**
	 * 返回 person.
	 * @return person
	 */
	public Person getPerson() {
		return person;
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
	public PersonChangeEvent(Person person, String op) {
		super(op);
		
		this.person = person;
		this.op = op;
	}
}
