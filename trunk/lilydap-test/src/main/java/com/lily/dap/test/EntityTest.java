package com.lily.dap.test;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.util.ReflectionUtils;

@ContextConfiguration(locations = { 
		"/applicationContext-resources.xml", 
		"/applicationContext-dao.xml" })
public class EntityTest<T extends BaseEntity> extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	protected Dao dao;

	protected Class<T> entityClass;
	
	private int batchInsertCount = 20;
	
	protected void setBatchInsertCount(int batchInsertCount) {
		this.batchInsertCount = batchInsertCount;
	}

	/**
	 * ����ע��
	 *
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		
		dao.batchRemove(entityClass, Condition.create());
	}

	public void doAutoTestCRUD() throws InstantiationException, IllegalAccessException {
		T entity = newAndFillEntity();
		
		//���Բ���ʵ������
		dao.saveOrUpdate(entity);
		Assert.assertTrue(entity.getId() > 0);
		Assert.assertEquals(1, dao.count(entityClass, null));
		
		//���Լ���ʵ������
		dao.flush();
		T entity1 = dao.get(entityClass, entity.getId());
		dao.reload(entity);
		Assert.assertEquals(entity, entity1);
		
		//����ɾ��ʵ������
		dao.remove(entity);
		Assert.assertEquals(0, dao.count(entityClass, null));
	}
	
	public void doAutoTestBatchInsert() throws InstantiationException, IllegalAccessException {
		for (int i = 0; i < batchInsertCount; i++) {
			T entity = newAndFillEntity();
			
			//���Բ���ʵ������
			dao.saveOrUpdate(entity);
			Assert.assertTrue(entity.getId() > 0);
		}
		
		Assert.assertEquals(batchInsertCount, dao.count(entityClass, null));
	}
	
	private T newAndFillEntity() throws InstantiationException, IllegalAccessException {
		T entity = BeanUtils.instantiate(this.entityClass);

		if (!fillEntity(entity))
			defaultFillEntity(entity);
		
		return entity;
	}
	
	/**
	 * ���new��ʵ����ĸ�������ֵ��������ʵ�֣�������಻���ȸ÷�������Ĭ��ʹ���漴�������
	 *
	 * @param entity
	 * @return ����true����ʾ��������ʵ�����ݵ����
	 */
	protected boolean fillEntity(T entity) {
		return false;
	}
	
	private void defaultFillEntity(T entity) {
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
		
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(entityClass);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() == null)
				continue;
			
			String name = pd.getName();
			if ("id".equals(name) || "class".equals(name))
				continue;
			
			Class<?> type = pd.getPropertyType();
			Object value;
			if (String.class.equals(type)) {
				value = RandomStringUtils.random(5);
			}
			else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
				value = RandomUtils.nextBoolean();
			}
			else if (byte.class.equals(type) || Byte.class.equals(type)) {
				value = Byte.parseByte(String.valueOf(RandomUtils.nextInt() % 256));
			}
			else if (short.class.equals(type) || Short.class.equals(type)) {
				value = Byte.parseByte(String.valueOf(RandomUtils.nextInt() % 255));
			}
			else if (int.class.equals(type) || Integer.class.equals(type)) {
				value = RandomUtils.nextInt();
			}
			else if (long.class.equals(type) || Long.class.equals(type)) {
				value = RandomUtils.nextLong();
			}
			else if (float.class.equals(type) || Float.class.equals(type)) {
				value = RandomUtils.nextFloat();
			}
			else if (double.class.equals(type) || Double.class.equals(type) ||
					Number.class.equals(type) || BigDecimal.class.equals(type)) {
				value = RandomUtils.nextDouble();
			}
			else if (byte[].class.equals(type)) {
				value = RandomStringUtils.random(5).getBytes();
			}
			else if (java.util.Date.class.equals(type)) {
				value = new java.util.Date();
			}
			else if (java.sql.Timestamp.class.equals(type)) {
				value = new java.sql.Timestamp(System.currentTimeMillis());
			}
			else if (java.sql.Date.class.equals(type)) {
				value = new java.sql.Date(System.currentTimeMillis());
			}
			else {
				// Some unknown type desired -> rely on getObject.
				value = null;
			}
			
			bw.setPropertyValue(name, value);
		}
	}
}
