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
 * ����У�鹤���࣬ʵ����ҵ���߼���ص�����У�鷽��
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
	 * �жϸ���ʵ������еĸ����ֶ������ֶ�ֵ��Ϊ�գ����Ϊ�գ����׳�ServiceException�쳣
	 * ϵͳ����ֶ�����Ϊ
	 * 	1.�ַ��������Ϊnull����ַ��������
	 * 	2.���ڣ����Ϊnull��1900-01-01���������
	 * 	3.��ֵ�����Ϊnull��0�����
	 * 	4.�������ͺ���
	 * 
	 * @param entity Ҫ���Ķ���
	 * @param fieldName Ҫ�����ֶ���
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
			//������ܼ����ֶ�ֵ������Բ�����
		}
	}

	/**
	 * �жϸ��������Ķ����Ƿ���ڣ���������ڣ��׳�DataNotExistException�쳣
	 *
	 * @param clazz Ҫ���Ķ���
	 * @param fieldName �����ֶ�
	 * @param fieldValue ����ֵ
	 * @param msg ������ݲ����ڣ��׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataExist(Class<?> clazz, String fieldName, Object fieldValue, String msg) {
		Condition condition = Condition.create().eq(fieldName, fieldValue);
		
		if (dao.count(clazz, condition) == 0) {
			if (msg == null)
				msg = clazz.getName() + "�е�[" + fieldName + "]�ֶ�Ϊ" + fieldValue +"�����ݲ����ڣ���������������";
			
			throw new DataNotExistException(msg);
		}
	}

	/**
	 * �жϸ�����������Ķ����Ƿ���ڣ���������ڣ��׳�DataNotExistException�쳣
	 *
	 * @param clazz Ҫ���Ķ���
	 * @param filedName �����ֶ�����
	 * @param filedValue ����ֵ����
	 * @param msg ������ݲ����ڣ��׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataExist(Class<?> clazz, String[] fieldName, Object[] fieldValue, String msg) {
		Condition condition = Condition.create().eq(fieldName, fieldValue);
		
		if (dao.count(clazz, condition) == 0) {
			if (msg == null)
				msg = clazz.getName() + "�е�[" + StringUtils.join(fieldName, ',') + "]�ֶ�Ϊ" + StringUtils.join(fieldValue, ',') +"�����ݲ����ڣ���������������";
			
			throw new DataNotExistException(msg);
		}
	}
	
	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz Ҫ���Ķ���
	 * @param repeatFiledName �ж��Ƿ��ظ��ֶ�
	 * @param repeatFiledValue �ж��Ƿ��ظ�ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[0], new String[0], new Object[0], msg);
	}

	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ
	 * @param filterFieldName �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶ�
	 * @param filterFieldValue ��Ҫ���˵��ֶ�ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String filterFieldName, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[]{filterFieldName}, new String[0], new Object[]{filterFieldValue}, msg);
	}

	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ
	 * @param filterFieldName �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶ�
	 * @param filterFieldOp �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶεĲ���
	 * @param filterFieldValue ��Ҫ���˵��ֶ�ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String repeatFieldName, Object repeatFieldValue, String filterFieldName, String filterFieldOp, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, new String[]{repeatFieldName}, new Object[]{repeatFieldValue}, new String[]{filterFieldName}, new String[]{filterFieldOp}, new Object[]{filterFieldValue}, msg);
	}
	
	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz Ҫ���Ķ���
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ��б�������1-n���ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ�б���Ӧ���ظ��ֶ��б��еĲ������ظ�ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[0], new String[0], new Object[0], msg);
	}
	
	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz Ҫ���Ķ���
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ��б�������1-n���ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ�б���Ӧ���ظ��ֶ��б��еĲ������ظ�ֵ
	 * @param filterFieldName �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶ�
	 * @param filterFieldValue ��Ҫ���˵��ֶ�ֵ����Ӧ�������ֶ��б��еĹ���ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String filterFieldName, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[]{filterFieldName}, new String[0], new Object[]{filterFieldValue}, msg);
	}
	
	
	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz Ҫ���Ķ���
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ��б�������1-n���ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ�б���Ӧ���ظ��ֶ��б��еĲ������ظ�ֵ
	 * @param filterFieldName �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶ�
	 * @param filterFieldOp �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶεĲ���
	 * @param filterFieldValue ��Ҫ���˵��ֶ�ֵ����Ӧ�������ֶ��б��еĹ���ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
	 */
	public void assertDataNotRepeat(Class<?> clazz, String[] repeatFieldName, Object[] repeatFieldValue, String filterFieldName, String filterFieldOp, Object filterFieldValue, String msg) {
		assertDataNotRepeat(clazz, repeatFieldName, repeatFieldValue, new String[]{filterFieldName}, new String[]{filterFieldOp}, new Object[]{filterFieldValue}, msg);
	}
	
	/**
	 * �жϸ��������ָ���ֶ��Ƿ��ظ�������ظ����׳�DataContentRepeatException�쳣
	 * 
	 * @param clazz Ҫ���Ķ���
	 * @param repeatFieldName �ж��Ƿ��ظ��ֶ��б�������1-n���ֶ�
	 * @param repeatFieldValue �ж��Ƿ��ظ�ֵ�б���Ӧ���ظ��ֶ��б��еĲ������ظ�ֵ
	 * @param filterFieldName �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶ��б����ֵΪnull�����ʾ��ָ�������ֶ�
	 * @param filterFieldOp �����Щ����Ҫ���ˣ�����������ָ����Ҫ���˵��ֶεĲ����б����Ϊnull����������ĳ��Ϊnull�����ʾ���������л��߸��в���Ϊ"<>"
	 * @param filterFieldValue ��Ҫ���˵��ֶ�ֵ����Ӧ�������ֶ��б��еĹ���ֵ
	 * @param msg ��������ظ����׳����쳣����ʾ�Ĵ�����Ϣ�����Ϊnull����ʹ��Ĭ�ϴ�����Ϣ
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
				msg = clazz.getName() + "�е�[" + StringUtils.join(repeatFieldName, ",") + "]�ֶ������ظ�����������������";
			
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
