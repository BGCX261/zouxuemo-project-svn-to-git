/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize.impl;

import org.springframework.context.ApplicationEvent;

import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.organize.Person;

/**
 * <code>PostChangeEvent</code>
 * <p>岗位信息改变事件</p>
 *
 * @author 邹学模
 * @date 2008-3-26
 */
public class PostChangeEvent extends ApplicationEvent {
	/**
	 * <code>serialVersionUID</code> 属性注释
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
	 * 返回 post.
	 * @return post
	 */
	public Post getPost() {
		return post;
	}

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
	public PostChangeEvent(Post post, String op) {
		super(op);
		
		this.post = post;
		this.op = op;
	}

	/**
	 * 构造函数
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
