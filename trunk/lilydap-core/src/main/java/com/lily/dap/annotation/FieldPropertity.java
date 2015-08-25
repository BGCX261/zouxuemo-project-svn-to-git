/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>FieldPropertity</code>
 * <p>ʵ���������ֶβ���ע��</p>
 *
 * @author ��ѧģ
 */
@Target(ElementType.FIELD)   
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FieldPropertity {
	/**
	 * �����ֶα�ǩ��
	 * 
	 * @return
	 */
	String label() default "";
	
	/**
	 * �����ֶ���Grid�б�����ʾ���п��
	 *
	 * @return
	 */
	int columnWidth() default 0;
	
	/**
	 * �Ƿ�����ǰ̨�û��ڱ�����ʱֱ�Ӹ�������ֶ�ֵ
	 *
	 * @return
	 */
	boolean allowCreate() default true;
	
	/**
	 * �Ƿ�����ǰ̨�û��ڱ��༭ʱֱ�Ӹ�������ֶ�ֵ
	 *
	 * @return
	 */
	boolean allowModify() default true;
}
