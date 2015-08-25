package com.lily.dap.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>EntityPropertity</code>
 * <p>ʵ�������ע��</p>
 *
 * @author zouxuemo
 */
@Target(ElementType.TYPE)   
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EntityPropertity {
	/**
	 * ʵ�����ǩ��
	 * 
	 * @return
	 */
	String label() default "";
}
