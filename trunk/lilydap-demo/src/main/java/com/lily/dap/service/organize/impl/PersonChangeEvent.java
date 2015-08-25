/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Person;

/**
 * <code>PersonChangeEvent</code>
 * <p>��Ա��Ϣ�ı��¼�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-26
 */
public class PersonChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -7721413377252965269L;

	public static final String ADD = "add";
	
	public static final String REMOVE = "remove";
	
	public static final String MODIFY = "modify";
	
	private Person person;
	
	private String op;
	
	/**
	 * ���� person.
	 * @return person
	 */
	public Person getPerson() {
		return person;
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
	public PersonChangeEvent(Person person, String op) {
		super(op);
		
		this.person = person;
		this.op = op;
	}
}
