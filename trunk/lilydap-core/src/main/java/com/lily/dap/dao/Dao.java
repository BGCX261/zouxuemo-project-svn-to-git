/**
 * 
 */
package com.lily.dap.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.lily.dap.entity.BaseEntity;

/**
 * 数据操作接口，提供针对实体对象的CRUD操作
 *
 * @author zouxuemo
 */
public interface Dao {
	/**
	 * 检索给定ID的给定类对象，如果给定id的实体对象不存在，返回null
	 * 
	 * @param clazz 要检索的实体类
	 * @param id 要检索的实体类id
	 * @return 
	 */
	public <T extends BaseEntity> T get(final Class<T> clazz, final Serializable id);
	
	/**
	 * 检索给定给定ID数组的给定类对象集合，如果ID数组的某个id对应的实体对象不存在，则忽略；如果都不存在，返回空数据的列表集合(size为0)
	 * 
	 * 
	 * @param clazz 要检索的实体类
	 * @param ids 要检索的实体类id数组
	 * @return 
	 */
	public <T extends BaseEntity> List<T> gets(final Class<T> clazz, final Serializable[] ids);
	
	/**
	 * 检索给定查询条件的给定类对象集合
	 * 
	 * 
	 * @param clazz 要检索的实体类
	 * @param condition 包含查询条件排序条件和分页信息
	 * @return 
	 */
	public <T extends BaseEntity> List<T> query(final Class<T> clazz, final Condition condition);
	
	/**
	 * 使用DetachedCriteria进行查询
	 * 
	 * @param criteria
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final DetachedCriteria criteria);
	
	/**
	 * 使用DetachedCriteria进行查询，返回指定指定起始条和限定条数的查询结果集
	 * 
	 * @param criteria
	 * @param start
	 * @param limit
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final DetachedCriteria criteria, final int start, final int limit);
	
	/**
	 * 执行给定的HQL语句查询，返回查询结果集
	 * 
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final Object... values);
	
	/**
	 * 执行给定的HQL语句查询，返回查询结果集
	 * 
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final Map<String, ?> values);
	
	/**
	 * 执行给定的HQL语句查询，返回指定指定起始条和限定条数的查询结果集
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final int start, final int limit, final Object... values);
	
	/**
	 * 执行给定的HQL语句查询，返回指定指定起始条和限定条数的查询结果集
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values 命名参数,按名称绑定.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final int start, final int limit, final Map<String, ?> values);
	
	/**
	 * 统计给定查询条件的给定类对象记录条数
	 * 
	 * @param clazz 要统计的实体类
	 * @param condition 给定的查询条件（忽略查询条件中的分页参数和排序参数）
	 * @return 返回记录条数
	 */
	public long count(final Class<?> clazz, final Condition condition);
	
	/**
	 * 统计给定查询条件的给定类对象某个字段的合计值
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
	 * 通过执行hql语句，返回唯一值，支持参数传递，例如：select count(*) from Module where code like ?
	 * 
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <X> X unique(final String hql, final Object... values);
	
	/**
	 * 通过执行hql语句，返回唯一值，支持参数传递，例如：select count(*) from Module where code like :code
	 * 
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return
	 */
	public <X> X unique(final String hql, final Map<String, ?> values);
	
	/**
	 * 刷新缓存
	 *
	 */
	public void flush();
	
	/**
	 * 重新装入类对象（重新刷新缓存）
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void reload(T entity);
	
	/**
	 * 根据实体类的ID值是否为0进行保存或更新类对象操作
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void saveOrUpdate(final T entity);
	
	/**
	 * 保存数组中所有类对象，根据实体类的ID值是否为0进行保存或更新类对象操作
	 * 
	 * @param entitys
	 */
	public <T extends BaseEntity> void batchSaveOrUpdate(final T[] entitys);
	
	/**
	 * 删除给定实体对象
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void remove(final T entity);
	
	/**
	 * 删除给定ID的给定类对象
	 * 
	 * @param clazz
	 * @param id
	 */
	public <T extends BaseEntity> void remove(final Class<T> clazz, final Serializable id);
	
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
	
	/**
	 * 批量更新给定查询条件的数据，返回更新的记录条数
	 * 
	 * @param clazz
	 * @param fields
	 * @param values
	 * @param condition
	 */
	public <T extends BaseEntity> int batchUpdate(final Class<T> clazz, final String[] fields, final Object[] values, final Condition condition);
	
	/**
	 * 执行给定的HQL语句，返回影响的记录条数
	 * 
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public int execute(final String hql, final Object... values);
	
	/**
	 * 执行给定的HQL语句，返回影响的记录条数
	 * 
	 * @param hql
	 * @param values 命名参数,按名称绑定.
	 * @return
	 */
	public int execute(final String hql, final Map<String, ?> values);
	
	/**
	 * 执行SQL查询，返回存放字段名－字段值映射的Map的List集合<br>
	 * 通过分析Condition的查询条件和排序条件数据，并追加到sql语句中，形成完整的sql语句
	 * 
	 * @param sql 可以包含where条件，但不能包含order by排序条件的sql语句
	 * @param condition 包含查询条件、排序条件和分页信息，要求给定condition中的查询条件值要和查询字段的类型相匹配
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final Condition condition);
	
	/**
	 * 执行SQL查询，返回存放字段名－字段值映射的Map的List集合
	 * 
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final Object... values);
	
	/**
	 * 执行SQL查询，返回存放字段名－字段值映射的Map的List集合
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final int start, final int limit, final Object... values);
	
	/**
	 * 执行SQL查询，返回给定实体对象的List集合<br>
	 * 通过分析Condition的查询条件和排序条件数据，并追加到sql语句中，形成完整的sql语句
	 * 
	 * @param clazz
	 * @param sql 可以包含where条件，但不能包含order by排序条件的sql语句
	 * @param condition 包含查询条件、排序条件和分页信息，要求给定condition中的查询条件值要和查询字段的类型相匹配
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final Condition condition);
	
	/**
	 * 执行SQL查询，返回给定实体对象的List集合
	 * 
	 * @param clazz
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final Object... values);
	
	/**
	 * 执行SQL查询，返回给定实体对象的List集合<br>
	 * 支持对象属性名与数据库字段名之间使用单词之间大写自动转下划线小写映射模式，即：如果名称为createDate的属性，对应的名称为create_date的数据库字段，则执行sql语句后会自动映射
	 * 
	 * @param clazz
	 * @param sql
	 * @param start
	 * @param limit
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final int start, final int limit, final Object... values);
	
	/**
	 * 执行SQL查询，返回List集合的总条数<br>
	 * 通过分析Condition的查询条件，并追加到sql语句中，形成完整的sql语句
	 * 
	 * @param sql 可以包含where条件，但不能包含order by排序条件的sql语句
	 * @param condition 包含查询条件，要求给定condition中的查询条件值要和查询字段的类型相匹配
	 * @return
	 */
	public long sqlCount(final String sql, final Condition condition);
	
	/**
	 * 执行SQL查询，返回List集合的总条数
	 * 
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 */
	public long sqlCount(final String sql, final Object... values);
	
	/**
	 * 执行SQL操作语句<br>
	 * 通过分析Condition的查询条件数据，并追加到sql语句中，形成完整的sql语句
	 * 
	 * @param sql 可以包含where条件，但不能包含order by排序条件的sql语句
	 * @param condition condition 包含查询条件（忽略查询条件中的分页参数和排序参数），要求给定condition中的查询条件值要和查询字段的类型相匹配
	 */
	public void sqlExecute(final String sql, final Condition condition);
	
	/**
	 * 执行SQL操作语句
	 * 
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public void sqlExecute(final String sql, final Object... values);
	
	/**
	 * 批量执行SQL操作语句
	 * 
	 * @param sqls
	 */
	public void batchSqlExecute(final String[] sqls);
	
	/**
	 * 批量执行SQL操作语句
	 * 
	 * @param sql 用";"分隔了多条sql语句字符串
	 */
	public void batchSqlExecute(final String sql);
	
	/**
	 * 取得当前Session，如果未找到，则检索并返回当前线程创建的Session，如当前线程未创建Session，则创建之
	 * <p>如果是从thread中获取session，则要求在所有数据库操作完成后调用clearThreadSession方法以释放数据库连接
	 * 
	 * @return
	 */
	public Session getSession();
	
	/**
	 * 清除并关闭当前线程创建的Session（可能在getSession方法中创建的Session）
	 * 
	 */
	public void clearThreadSession();
	
	/**
	 * 设置当前Hibernate的SessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory);
	
	/**
	 * 返回当前Hibernate的SessionFactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory();
}
