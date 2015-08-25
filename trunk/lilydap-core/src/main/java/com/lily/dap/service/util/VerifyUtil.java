/**
 * 
 */
package com.lily.dap.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.ServiceException;

/**
 * 数据校验工具类，实现与业务逻辑相关的数据校验方法
 * 
 * @author zouxuemo
 *
 */
public class VerifyUtil {
	private Dao dao;
	
	private static Date zeroDate; 
	static {
		try {
			zeroDate = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-02");
		} catch (ParseException e) {}
	}
	
	public VerifyUtil(Dao dao) {
		super();
		this.dao = dao;
	}
	
	/**
	 * 判断给定实体对象中的给定字段名的字段值不为空，如果为空，则抛出ServiceException异常
	 * 系统检查字段类型为
	 * 	1.字符串：如果为null或空字符串则出错
	 * 	2.日期：如果为null或1900-01-01日期则出错
	 * 	3.数值：如果为null或0则出错
	 * 	4.其它类型忽略
	 * 
	 * @param entity 要检查的对象
	 * @param fieldName 要检查的字段名
	 */
	public void assertDataNotEmpty(Object entity, String fieldName, String msg) {
		try {
			Object val = PropertyUtils.getProperty(entity, fieldName);
			if (val == null)
				throw new ServiceException(msg);
				
			if (val instanceof String) {
				if (!"".equals((String)val))
					return;
			} else if (val instanceof Date) {
				if (zeroDate.getTime() != ((Date)val).getTime())
					return;
			} else if (val instanceof Number) {
				if (0 != ((Number)val).doubleValue())
					return;
			}
			
			throw new ServiceException(msg);
		} catch (Exception e) {
			//如果不能检索字段值，则忽略不处理
		}
	}

	/**
	 * 判断给定条件的对象是否存在，如果不存在，抛出DataNotExistException异常
	 *
	 * @param clazz 要检查的对象
	 * @param fieldName 条件字段
	 * @param fieldValue 条件值
	 * @param msg 如果数据不存在，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataExist(Class<?> clazz, String fieldName, Object fieldValue, String msg) {
		Condition condition = Condition.create().eq(fieldName, fieldValue);
		
		if (dao.count(clazz, condition) == 0) {
			if (msg == null)
				msg = clazz.getName() + "中的[" + fieldName + "]字段为" + fieldValue +"的数据不存在，请重新输入数据";
			
			throw new DataNotExistException(msg);
		}
	}

	/**
	 * 判断给定多个条件的对象是否存在，如果不存在，抛出DataNotExistException异常
	 *
	 * @param clazz 要检查的对象
	 * @param filedName 条件字段数组
	 * @param filedValue 条件值数组
	 * @param msg 如果数据不存在，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataExist(Class<?> clazz, String[] fieldName, Object[] fieldValue, String msg) {
		Condition condition = Condition.create().eq(fieldName, fieldValue);
		
		if (dao.count(clazz, condition) == 0) {
			if (msg == null)
				msg = clazz.getName() + "中的[" + StringUtils.join(fieldName, ',') + "]字段为" + StringUtils.join(fieldValue, ',') +"的数据不存在，请重新输入数据";
			
			throw new DataNotExistException(msg);
		}
	}
	
	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz 要检查的对象
	 * @param repeatFiledName 判断是否重复字段
	 * @param repeatFiledValue 判断是否重复值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[0], new String[0], new Object[0], msg);
	}

	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz
	 * @param repeatFieldName 判断是否重复字段
	 * @param repeatFieldValue 判断是否重复值
	 * @param filterFieldName 如果有些列需要过滤，可以在这里指定需要过滤的字段
	 * @param filterFieldValue 需要过滤的字段值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String filterFieldName, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[]{filterFieldName}, new String[0], new Object[]{filterFieldValue}, msg);
	}

	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz
	 * @param repeatFieldName 判断是否重复字段
	 * @param repeatFieldValue 判断是否重复值
	 * @param filterFieldName 如果有些列需要过滤，可以在这里指定需要过滤的字段
	 * @param filterFieldOp 如果有些列需要过滤，可以在这里指定需要过滤的字段的操作
	 * @param filterFieldValue 需要过滤的字段值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String filterFieldName, String filterFieldOp, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[]{filterFieldName}, new String[]{filterFieldOp}, new Object[]{filterFieldValue}, msg);
	}
	
	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz 要检查的对象
	 * @param repeatFieldName 判断是否重复字段列表，可以是1-n个字段
	 * @param repeatFieldValue 判断是否重复值列表，对应到重复字段列表中的不允许重复值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[0], new String[0], new Object[0], msg);
	}
	
	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz 要检查的对象
	 * @param repeatFieldName 判断是否重复字段列表，可以是1-n个字段
	 * @param repeatFieldValue 判断是否重复值列表，对应到重复字段列表中的不允许重复值
	 * @param filterFieldName 如果有些列需要过滤，可以在这里指定需要过滤的字段
	 * @param filterFieldValue 需要过滤的字段值，对应到过滤字段列表中的过滤值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String filterFieldName, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[]{filterFieldName}, new String[0], new Object[]{filterFieldValue}, msg);
	}
	
	
	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz 要检查的对象
	 * @param repeatFieldName 判断是否重复字段列表，可以是1-n个字段
	 * @param repeatFieldValue 判断是否重复值列表，对应到重复字段列表中的不允许重复值
	 * @param filterFieldName 如果有些列需要过滤，可以在这里指定需要过滤的字段
	 * @param filterFieldOp 如果有些列需要过滤，可以在这里指定需要过滤的字段的操作
	 * @param filterFieldValue 需要过滤的字段值，对应到过滤字段列表中的过滤值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String filterFieldName, String filterFieldOp, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[]{filterFieldName}, new String[]{filterFieldOp}, new Object[]{filterFieldValue}, msg);
	}
	
	/**
	 * 判断给定对象的指定字段是否重复，如果重复，抛出DataContentRepeatException异常
	 * 
	 * @param clazz 要检查的对象
	 * @param repeatFieldName 判断是否重复字段列表，可以是1-n个字段
	 * @param repeatFieldValue 判断是否重复值列表，对应到重复字段列表中的不允许重复值
	 * @param filterFieldName 如果有些列需要过滤，可以在这里指定需要过滤的字段列表，如果值为null，则表示不指定过滤字段
	 * @param filterFieldOp 如果有些列需要过滤，可以在这里指定需要过滤的字段的操作列表，如果为null或者数组中某项为null，则表示整个过滤列或者该列操作为"<>"
	 * @param filterFieldValue 需要过滤的字段值，对应到过滤字段列表中的过滤值
	 * @param msg 如果数据重复，抛出的异常中显示的错误信息，如果为null，则使用默认错误信息
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String[] filterFieldName, String[] filterFieldOp, Object[] filterFieldValue, String msg) {
		Condition condition = Condition.create();
		
		for (int i = 0; i < repeatFieldName.length; i++)
			condition.eq(repeatFieldName[i], repeatFieldValue[i]);
		
		if (filterFieldName != null && filterFieldName.length > 0) {
			for (int i = 0; i < filterFieldName.length; i++)
				if (filterFieldOp == null || filterFieldOp[i] == null)
					condition.ne(filterFieldName[i], filterFieldValue[i]);
				else
					condition.condition(filterFieldName[i], filterFieldOp[i], filterFieldValue[i]);
		}
		
		if (dao.count(clazz, condition) > 0) {
			if (msg == null)
				msg = clazz.getName() + "中的[" + StringUtils.join(repeatFieldName, ",") + "]字段数据重复，请重新输入数据";
			
			throw new DataContentRepeatException(msg);
		}
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d = sdf.parse("1900-01-02");
		System.out.println(d.getTime());
		System.out.println(sdf.format(d));
		
		Number n = 123l;
		System.out.println(n.doubleValue());
		System.out.println(n.doubleValue() == 0);
	}
}
