package com.lily.dap.service;

import java.io.Serializable;
import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * 基本的管理接口，实现了对任意对象的CRUD操作
 * 
 * @author zouxuemo
 * 
 */
public interface Manager {
	/**
	 * 检索给定ID的给定类对象
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T get(final Class<T> clazz, final long id) throws DataNotExistException;
	
	/**
	 * 检索给定查询条件的给定类对象集合
	 * 返回检索出的结果
	 * 
	 * @param clazz
	 * @param condition
	 * @return 
	 */
	public <T extends BaseEntity> List<T> query(final Class<T> clazz, final Condition condition);
	
	/**
	 * 检索给定查询条件的给定类对象集合数量（忽略查询条件中的分页参数）
	 * 
	 * @param clazz 要统计的实体类
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回记录条数
	 */
	public long count(final Class<?> clazz, final Condition condition);
	
	/**
	 * 检索给定查询条件的给定类对象集合数量（忽略查询条件中的分页参数）
	 * 
	 * @param clazz 要统计的实体类
	 * @param field 要统计的属性名，字段类型必须是数值型（byte, short, int, long, float, double及其包装类）
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回合计值，对于字段为byte, short, int, long类型，返回long，对于字段为float, double，返回double
	 */
	public Number sum(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * 统计给定查询条件的给定类对象某个字段平均值
	 * 
	 * @param clazz 要统计的实体类
	 * @param field 要统计的属性名，字段类型必须是数值型（byte, short, int, long, float, double及其包装类）
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回平均值
	 */
	public double avg(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * 统计给定查询条件的给定类对象某个字段的最大值
	 * 
	 * @param clazz 要统计的实体类
	 * @param field 要统计的属性名
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回最大值，返回值类型与字段类型相同
	 */
	public <X> X max(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * 统计给定查询条件的给定类对象某个字段的最小值
	 * 
	 * @param clazz 要统计的实体类
	 * @param field 要统计的属性名
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回最小值，返回值类型与字段类型相同
	 */
	public <X> X min(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * 根据实体类的ID值是否为0进行保存或更新类对象操作，直接保存，不检查字段注解是否定义了可修改字段
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T saveOrUpdate(final T entity);
	
	/**
	 * 根据对象的ID是否设置自动更新或创建实体对象，将根据各个字段是否定义了允许更新的注解来确定可修改字段
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T extends BaseEntity> T save(final T entity);
	
	/**
	 * 更新实体对象数据，将根据各个字段是否定义了允许更新的注解来确定可修改字段
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T create(final T entity);
	
	/**
	 * 创建实体对象数据，将根据各个字段是否定义了允许更新的注解来确定可修改字段
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T modify(final T entity);
	
	/**
	 * 保存数组中所有类对象，根据实体类的ID值是否为0进行保存或更新类对象操作
	 * 
	 * @param entitys
	 */
	public <T extends BaseEntity> void batchSaveOrUpdate(final T[] entitys);
	
	/**
	 * 删除给定ID的给定类对象
	 * 
	 * @param clazz
	 * @param id
	 */
	public <T extends BaseEntity> void remove(final Class<T> clazz, final long id) throws DataNotExistException;
	
	/**
	 * 批量删除数组中所有ID对应的类对象，返回删除的记录条数
	 * 
	 * @param clazz
	 * @param ids
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final long[] ids);
	
	/**
	 * 批量删除数组中所有ID对应的类对象，返回删除的记录条数
	 * 
	 * @param clazz
	 * @param ids
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final Serializable[] ids);
	
	/**
	 * 批量删除给定查询条件的所有给定类对象集合，返回删除的记录条数
	 * 
	 * @param clazz
	 * @param condition
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final Condition condition);
}
