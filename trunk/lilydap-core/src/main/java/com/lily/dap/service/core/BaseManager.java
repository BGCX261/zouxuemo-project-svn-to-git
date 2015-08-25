package com.lily.dap.service.core;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.ServiceException;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.TransferUtils;

/**
 * ��������࣬�ṩ��dao����ķ��ʺͻ���CRUD�����ӿڷ���
 * 
 *
 * date: 2010-12-13
 * @author zouxuemo
 */
public class BaseManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * DAO�����ӿ�
	 */
	@Autowired
	@Qualifier("dao")
	protected Dao dao;

	public <T extends BaseEntity> T get(Class<T> clazz, long id)
			throws DataNotExistException {
		T entity = dao.get(clazz, id);
		if (entity == null)
			throw new DataNotExistException("idΪ " + id + " �Ķ���[" + clazz.getName() + "]���ݲ����ڣ�");
		
		return entity;
	}
	
	protected <T extends BaseEntity> T get(Class<T> clazz, String field, Object value)
		throws DataNotExistException {
		List<T> result = query(clazz, field, value);
		if (result.size() == 0)
			throw new DataNotExistException(field + "Ϊ " + value + " �Ķ���[" + clazz.getName() + "]���ݲ����ڣ�");
		
		return result.get(0);
	}
	
	protected <T extends BaseEntity> T get(Class<T> clazz, String[] fields, Object[] values)
		throws DataNotExistException {
		List<T> result = query(clazz, fields, values);
		if (result.size() == 0)
			throw new DataNotExistException(StringUtils.join(fields) + "Ϊ " + StringUtils.join(values) + " �Ķ���[" + clazz.getName() + "]���ݲ����ڣ�");
		
		return result.get(0);
	}

	public <T extends BaseEntity> List<T> query(Class<T> clazz,
			Condition condition) {
		return dao.query(clazz, condition);
	}
	
	protected <T extends BaseEntity> List<T> query(Class<T> clazz, String field, Object value) {
		Assert.notNull(clazz, "clazz����Ϊ��");
		Assert.hasText(field, "field����Ϊ��");
		
		return dao.query(clazz, Condition.create().eq(field, value));
	}
	
	protected <T extends BaseEntity> List<T> query(Class<T> clazz, String[] fields, Object[] values) {
		Assert.notNull(clazz, "clazz����Ϊ��");
		Assert.notEmpty(fields, "fields����Ϊ��");
		Assert.notEmpty(values, "values����Ϊ��");
		Assert.isTrue(fields.length == values.length, "fields�е��ֶ�������������values�е��ֶ�ֵ�������");

		Condition condition = Condition.create();
		for (int index = 0, len = fields.length; index < len; index++)
			condition.eq(fields[index], values[index]);
			
		return dao.query(clazz, condition);
	}
	
	public long count(Class<?> clazz, Condition condition) {
		return dao.count(clazz, condition);
	}

	public Number sum(Class<?> clazz, String field, Condition condition) {
		return dao.sum(clazz, field, condition);
	}
	
	public double avg(final Class<?> clazz, String field, final Condition condition) {
		return dao.avg(clazz, field, condition);
	}
	
	@SuppressWarnings("unchecked")
	public <X> X max(final Class<?> clazz, String field, final Condition condition) {
		return (X)dao.max(clazz, field, condition);
	}
	
	@SuppressWarnings("unchecked")
	public <X> X min(final Class<?> clazz, String field, final Condition condition) {
		return (X)dao.min(clazz, field, condition);
	}

	public <T extends BaseEntity> T saveOrUpdate(T entity) {
		dao.saveOrUpdate(entity);
		
		return entity;
	}

	public <T extends BaseEntity> T save(T entity) {
		if (entity.getId() > 0)
			return modify(entity);
		else
			return create(entity);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T create(T entity) {
		Class<? extends BaseEntity> clazz = entity.getClass();
		T createEntity = null;
		
		try {
			createEntity = (T)clazz.newInstance();
		} catch (Exception e) {
			throw new ServiceException("��������ʵ��ʧ�ܣ�", e);
		}
		
		String[] fieldNames = BaseEntityHelper.getEntityAllowCreateFields(clazz);
		TransferUtils.copy(entity, createEntity, fieldNames, null, null);
		
		createEntity.setId(0);
		dao.saveOrUpdate(createEntity);
		return createEntity;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T modify(T entity) {
		if (entity.getId() == 0)
			throw new DataNotExistException("Ҫ�޸ĵĶ��󲻴��ڣ�");
		
		Class<? extends BaseEntity> clazz = entity.getClass();
		T modifyEntity = null;
		
		modifyEntity = (T)get(clazz, entity.getId());
		
		String[] fieldNames = BaseEntityHelper.getEntityAllowModifyFields(clazz);
		TransferUtils.copy(entity, modifyEntity, fieldNames, null, null);
		
		dao.saveOrUpdate(modifyEntity);
		return modifyEntity;
	}

	public <T extends BaseEntity> void batchSaveOrUpdate(T[] entitys) {
		dao.batchSaveOrUpdate(entitys);
	}
	
	public <T extends BaseEntity> int batchRemove(Class<T> clazz, long[] ids) {
		Long[] idAry = null;
		
		if (ids != null) {
			idAry = new Long[ids.length];
			
			for (int i = 0, len = ids.length; i < len; i++)
				idAry[i] = new Long(ids[i]);
		}
		
		return batchRemove(clazz, idAry);
	}

	public <T extends BaseEntity> int batchRemove(Class<T> clazz,
			Serializable[] ids) {
		return dao.batchRemove(clazz, ids);
	}

	public <T extends BaseEntity> int batchRemove(Class<T> clazz,
			Condition condition) {
		return dao.batchRemove(clazz, condition);
	}

	public <T extends BaseEntity> void remove(Class<T> clazz, long id) {
		T entity = get(clazz, id);
		
		dao.remove(entity);
	}
}
