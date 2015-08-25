/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.organize.Person;

/**
 * <code>PostChangeEvent</code>
 * <p>��λ��Ϣ�ı��¼�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-26
 */
public class PostChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -3393553887256982852L;

	public static final String ADD = "add";
	
	public static final String REMOVE = "remove";
	
	public static final String MODIFY = "modify";
	
	public static final String ADD_HAVE_PERSON = "addperson";
	
	public static final String REMOVE_HAVE_PERSON = "removeperson";
	
	private Post post;
	
	private Person person;
	
	private String op;
	
	/**
	 * ���� post.
	 * @return post
	 */
	public Post getPost() {
		return post;
	}

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
	public PostChangeEvent(Post post, String op) {
		super(op);
		
		this.post = post;
		this.op = op;
	}

	/**
	 * ���캯��
	 *
	 * @param source
	 */
	public PostChangeEvent(Post post, Person person, String op) {
		super(op);
		
		this.post = post;
		this.person = person;
		this.op = op;
	}
}
