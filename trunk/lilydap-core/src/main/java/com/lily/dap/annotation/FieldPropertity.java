/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
 * <p>实体类属性字段参数注解</p>
 *
 * @author 邹学模
 */
@Target(ElementType.FIELD)   
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FieldPropertity {
	/**
	 * 属性字段标签名
	 * 
	 * @return
	 */
	String label() default "";
	
	/**
	 * 属性字段在Grid列表中显示的列宽度
	 *
	 * @return
	 */
	int columnWidth() default 0;
	
	/**
	 * 是否允许前台用户在表单创建时直接更改这个字段值
	 *
	 * @return
	 */
	boolean allowCreate() default true;
	
	/**
	 * 是否允许前台用户在表单编辑时直接更改这个字段值
	 *
	 * @return
	 */
	boolean allowModify() default true;
}
