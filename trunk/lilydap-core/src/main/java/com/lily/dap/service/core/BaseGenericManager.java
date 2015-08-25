package com.lily.dap.service.core;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.ServiceException;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.ReflectionUtils;
import com.lily.dap.util.TransferUtils;

/**
 * ���ͷ�������࣬�ṩ��dao����ķ��ʺͻ���CRUD�����ӿڷ���������̳в�ָ�������ʵ����
 * eg.
 * 	public class ModuleManagerImpl extends BaseGenericManager<Module>
 *
 * date: 2010-12-13
 * @author zouxuemo
 */
public class BaseGenericManager<T extends BaseEntity> {
	/**
	 * DAO�����ӿ�
	 */
	@Autowired
	protected Dao dao;

	protected Class<T> entityClass;

	/**
	 * ͨ������ķ��Ͷ���ȡ�ö�������Class.
	 * eg.
	 * public class ModuleManagerImpl extends BaseGenericManager<Module>
	 */
	public BaseGenericManager() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public BaseGenericManager(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public T get(long id)
			throws DataNotExistException {
		T entity = dao.get(entityClass, id);
		if (entity == null)
			throw new DataNotExistException("idΪ " + id + " �Ķ���[" + entityClass.getName() + "]���ݲ����ڣ�");
		
		return entity;
	}
	
	protected T get(String field, Object value)
		throws DataNotExistException {
		List<T> result = query(field, value);
		if (result.size() == 0)
			throw new DataNotExistException(field + "Ϊ " + value + " �Ķ���[" + entityClass.getName() + "]���ݲ����ڣ�");
		
		return result.get(0);
	}
	
	protected T get(String[] fields, Object[] values)
		throws DataNotExistException {
		List<T> result = query(fields, values);
		if (result.size() == 0)
			throw new DataNotExistException(StringUtils.join(fields) + "Ϊ " + StringUtils.join(values) + " �Ķ���[" + entityClass.getName() + "]���ݲ����ڣ�");
		
		return result.get(0);
	}

	public List<T> query(Condition condition) {
		return dao.query(entityClass, condition);
	}
	
	protected List<T> query(String field, Object value) {
		Assert.notNull(entityClass, "clazz����Ϊ��");
		Assert.hasText(field, "field����Ϊ��");
		
		return dao.query(entityClass, Condition.create().eq(field, value));
	}
	
	protected List<T> query(String[] fields, Object[] values) {
		Assert.notNull(entityClass, "clazz����Ϊ��");
		Assert.notEmpty(fields, "fields����Ϊ��");
		Assert.notEmpty(values, "values����Ϊ��");
		Assert.isTrue(fields.length == values.length, "fields�е��ֶ�������������values�е��ֶ�ֵ�������");

		Condition condition = Condition.create();
		for (int index = 0, len = fields.length; index < len; index++)
			condition.eq(fields[index], values[index]);
			
		return dao.query(entityClass, condition);
	}

	public long count(Condition condition) {
		return dao.count(entityClass, condition);
	}

	public Number sum(String field, Condition condition) {
		return dao.sum(entityClass, field, condition);
	}
	
	public double avg(String field, final Condition condition) {
		return dao.avg(entityClass, field, condition);
	}
	
	@SuppressWarnings("unchecked")
	public <X> X max(String field, final Condition condition) {
		return (X)dao.max(entityClass, field, condition);
	}
	
	@SuppressWarnings("unchecked")
	public <X> X min(String field, final Condition condition) {
		return (X)dao.min(entityClass, field, condition);
	}

	public T saveOrUpdate(T entity) {
		dao.saveOrUpdate(entity);
		
		return entity;
	}

	public T save(T entity) {
		if (entity.getId() > 0)
			return modify(entity);
		else
			return create(entity);
	}

	public T create(T entity) {
		T createEntity = null;
		try {
			createEntity = (T)entityClass.newInstance();
		} catch (Exception e) {
			throw new ServiceException("��������ʵ��ʧ�ܣ�", e);
		}
		
		String[] fieldNames = BaseEntityHelper.getEntityAllowCreateFields(entityClass);
		TransferUtils.copy(entity, createEntity, fieldNames, null, null);
		
		createEntity.setId(0);
		dao.saveOrUpdate(createEntity);
		return createEntity;
	}

	public T modify(T entity) {
		if (entity.getId() == 0)
			throw new DataNotExistException("Ҫ�޸ĵĶ��󲻴��ڣ�");
		
		T modifyEntity = (T)get(entity.getId());
		
		String[] fieldNames = BaseEntityHelper.getEntityAllowModifyFields(entityClass);
		TransferUtils.copy(entity, modifyEntity, fieldNames, null, null);
		
		dao.saveOrUpdate(modifyEntity);
		return modifyEntity;
	}

	public void batchSaveOrUpdate(T[] entitys) {
		dao.batchSaveOrUpdate(entitys);
	}
	
	public int batchRemove(long[] ids) {
		Long[] idAry = null;
		
		if (ids != null) {
			idAry = new Long[ids.length];
			
			for (int i = 0, len = ids.length; i < len; i++)
				idAry[i] = new Long(ids[i]);
		}
		
		return batchRemove(idAry);
	}

	public int batchRemove(Serializable[] ids) {
		return dao.batchRemove(entityClass, ids);
	}

	public int batchRemove(Condition condition) {
		return dao.batchRemove(entityClass, condition);
	}

	public void remove(long id) {
		T entity = get(id);
		
		dao.remove(entity);
	}
}
