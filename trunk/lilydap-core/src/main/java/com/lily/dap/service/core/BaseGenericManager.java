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
 * 泛型服务类基类，提供对dao对象的访问和基本CRUD操作接口方法，子类继承并指定具体的实体类
 * eg.
 * 	public class ModuleManagerImpl extends BaseGenericManager<Module>
 *
 * date: 2010-12-13
 * @author zouxuemo
 */
public class BaseGenericManager<T extends BaseEntity> {
	/**
	 * DAO操作接口
	 */
	@Autowired
	protected Dao dao;

	protected Class<T> entityClass;

	/**
	 * 通过子类的泛型定义取得对象类型Class.
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
			throw new DataNotExistException("id为 " + id + " 的对象[" + entityClass.getName() + "]数据不存在！");
		
		return entity;
	}
	
	protected T get(String field, Object value)
		throws DataNotExistException {
		List<T> result = query(field, value);
		if (result.size() == 0)
			throw new DataNotExistException(field + "为 " + value + " 的对象[" + entityClass.getName() + "]数据不存在！");
		
		return result.get(0);
	}
	
	protected T get(String[] fields, Object[] values)
		throws DataNotExistException {
		List<T> result = query(fields, values);
		if (result.size() == 0)
			throw new DataNotExistException(StringUtils.join(fields) + "为 " + StringUtils.join(values) + " 的对象[" + entityClass.getName() + "]数据不存在！");
		
		return result.get(0);
	}

	public List<T> query(Condition condition) {
		return dao.query(entityClass, condition);
	}
	
	protected List<T> query(String field, Object value) {
		Assert.notNull(entityClass, "clazz不能为空");
		Assert.hasText(field, "field不能为空");
		
		return dao.query(entityClass, Condition.create().eq(field, value));
	}
	
	protected List<T> query(String[] fields, Object[] values) {
		Assert.notNull(entityClass, "clazz不能为空");
		Assert.notEmpty(fields, "fields不能为空");
		Assert.notEmpty(values, "values不能为空");
		Assert.isTrue(fields.length == values.length, "fields中的字段名数量必须与values中的字段值数量相等");

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
			throw new ServiceException("创建对象实例失败！", e);
		}
		
		String[] fieldNames = BaseEntityHelper.getEntityAllowCreateFields(entityClass);
		TransferUtils.copy(entity, createEntity, fieldNames, null, null);
		
		createEntity.setId(0);
		dao.saveOrUpdate(createEntity);
		return createEntity;
	}

	public T modify(T entity) {
		if (entity.getId() == 0)
			throw new DataNotExistException("要修改的对象不存在！");
		
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
