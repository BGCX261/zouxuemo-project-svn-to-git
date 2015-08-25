package com.lily.dap.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.dao.support.SqlUtils;
import com.lily.dap.entity.BaseEntity;

/**
 * 数据操作实现类
 * 
 * @author zouxuemo
 */
@SuppressWarnings("unchecked")
@Repository
public class HibernateDao implements Dao {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	private int batchSize = 20;
	
	private char multiSqlDelim = ';';
	
	private static final ThreadLocal<Session> context = new ThreadLocal<Session>();
	
	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setMultiSqlDelim(char multiSqlDelim) {
		this.multiSqlDelim = multiSqlDelim;
	}
//	
//	public Session openSession() {
//		return null;
//	}
	
	public void clearThreadSession() {
		Session session = context.get();
		if (session != null) {
			try {
				session.close();
			} catch (HibernateException e) {
				if(logger.isDebugEnabled())
					logger.debug( "Unable to close thread's session", e );
			}
			
			context.remove();
		}
	}

	/**
	 * 取得当前Session，如果未找到，则检索并返回当前线程创建的Session，如当前线程未创建Session，则创建之
	 */
	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			if(logger.isDebugEnabled())
				logger.debug("call getCurrentSession function failure, from thread to get Session!");
			
			Session session = context.get();
			if (session == null) {
				if(logger.isDebugEnabled())
					logger.debug("...call SessionFactoryUtils.getSession(sessionFactory, true) function to get Session!");
				
				session = SessionFactoryUtils.getSession(sessionFactory, true);
				context.set(session);
			}
			
			return session;
		}
	}

	public <T extends BaseEntity> T get(Class<T> clazz, Serializable id) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.notNull(id, "id不能为空");
		
		if(logger.isDebugEnabled())
			logger.debug("gets entity ({}) by id: {}", clazz.getName(), id);
		
		return (T) getSession().get(clazz, id);
	}

	public <T extends BaseEntity> List<T> gets(Class<T> clazz,
			Serializable[] ids) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.notEmpty(ids, "ids不能为空");
		
		String hql = "from " + clazz.getName() + " where " + getIdName(clazz) + " in (" + StringUtils.join(ids, ',') + ")";
		Query query = getSession().createQuery(hql);
		
		if(logger.isDebugEnabled())
			logger.debug("gets entity ({}) by id: {}", clazz.getName(), ids);
		
		return query.list();
	}

	public <T extends BaseEntity> List<T> query(Class<T> clazz,
			Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String hql = "from " + clazz.getName();
		String where = parser.parse(true);
		if (where != null)
			hql += where;
		
		Object[] params = parser.params();
		return query(hql, condition.getStart(), condition.getLimit(), params);
	}

	public <T extends BaseEntity> List<T> query(DetachedCriteria criteria) {
		return query(criteria, 0, 0);
	}

	public <T extends BaseEntity> List<T> query(DetachedCriteria criteria,
			int start, int limit) {
		Assert.notNull(criteria, "criteria不能为空");
		Assert.isTrue(start >= 0, "start必须为0或者正数");
		Assert.isTrue(limit >= 0, "limit必须为0或者正数");
		
		Criteria c = criteria.getExecutableCriteria(getSession());
		if (start > 0)
			c.setFirstResult(start);
		
		if (limit > 0)
			c.setMaxResults(limit);
		
		if(logger.isDebugEnabled())
			logger.debug("query {}, start: {}, limit: {}", new Object[]{criteria, start, limit});
		
		return c.list();
	}

	public <T extends BaseEntity> List<T> query(String hql, Object... values) {
		return query(hql, 0, 0, values);
	}

	public <T extends BaseEntity> List<T> query(String hql, Map<String, ?> values) {
		return query(hql, 0, 0, values);
	}

	public <T extends BaseEntity> List<T> query(String hql, int start,
			int limit, Object... values) {
		if(logger.isDebugEnabled())
			logger.debug("query hql{{}}, param: {}, start: {}, limit: {}", new Object[]{hql, values, start, limit});
		
		return createQuery(hql, start, limit, values).list();
	}

	public <T extends BaseEntity> List<T> query(String hql, int start,
			int limit, Map<String, ?> values) {
		if(logger.isDebugEnabled())
			logger.debug("query hql{{}}, param: {}, start: {}, limit: {}", new Object[]{hql, values, start, limit});
		
		return createQuery(hql, start, limit, values).list();
	}

	public long count(Class<?> clazz, Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String hql = "select count(*) from " + clazz.getName();
		String where = parser.parse(false);
		if (where != null)
			hql += where;
		
		return (Long)unique(hql, parser.params());
	}

	public Number sum(Class<?> clazz, String field, Condition condition) {
		return (Number)stat(clazz, field, "sum", condition);
	} 

	public double avg(Class<?> clazz, String field, Condition condition) {
		Number result = (Number)stat(clazz, field, "avg", condition);
		
		return result.doubleValue();
	} 

	public <X> X max(Class<?> clazz, String field, Condition condition) {
		return (X)stat(clazz, field, "max", condition);
	} 

	public <X> X min(Class<?> clazz, String field, Condition condition) {
		return (X)stat(clazz, field, "min", condition);
	} 
	
	private <X> X stat(Class<?> clazz, String field, String op, Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.hasText(field, "field不能为空");
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String hql = "select " + op + "(" + field + ") from " + clazz.getName();
		String where = parser.parse(false);
		if (where != null)
			hql += where;
		
		return (X)unique(hql, parser.params());
	}

	public <X> X unique(String hql, Object... values) {
		if(logger.isDebugEnabled())
			logger.debug("unique hql{{}}, param: {}", hql, values);
		
		return (X)createQuery(hql, 0, 0, values).uniqueResult();
	}

	public <X> X unique(String hql, Map<String, ?> values) {
		if(logger.isDebugEnabled())
			logger.debug("unique hql{{}}, param: {}", hql, values);
		
		return (X)createQuery(hql, 0, 0, values).uniqueResult();
	}

	public void flush() {
		if(logger.isDebugEnabled())
			logger.debug("flush hibernate cache");
		
		getSession().flush();
	}
	
	public <T extends BaseEntity> void reload(T entity) {
		if(logger.isDebugEnabled())
			logger.debug("reload entity ({}) by id: {}", entity.getClass().getName(), entity.getId());
		
		getSession().refresh(entity);
	}

	public <T extends BaseEntity> void saveOrUpdate(T entity) {
		Assert.notNull(entity, "entity不能为空");
		
		getSession().saveOrUpdate(entity);
		
		if(logger.isDebugEnabled())
			logger.debug("save entity: {}", entity);
	}

	public <T extends BaseEntity> void batchSaveOrUpdate(T[] entitys) {
		Assert.notEmpty(entitys, "entitys不能为空");

		Session session = getSession();
		
		long[] ids = new long[entitys.length];
		for (int index = 0, len = entitys.length; index < len; index++) {
			session.saveOrUpdate(entitys[index]);
			
			if (index % batchSize == 0) {
				session.flush();
				session.clear();
			}
			
			ids[index] = entitys[index].getId();
		}
		
		if(logger.isDebugEnabled())
			logger.debug("batch save entity ({}), id: {}", entitys[0].getClass().getSimpleName(), ids);
	}

	public <T extends BaseEntity> void remove(T entity) {
		Assert.notNull(entity, "entity不能为空");
		
		if(logger.isDebugEnabled())
			logger.debug("delete entity: {}", entity);
		
		getSession().delete(entity);
	}

	public <T extends BaseEntity> void remove(Class<T> clazz, Serializable id) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.notNull(id, "id不能为空");

		if(logger.isDebugEnabled())
			logger.debug("delete entity ({}), id: {}", clazz.getSimpleName(), id);
		
		T entity = (T)getSession().load(clazz, id);
		getSession().delete(entity);
	}

	public <T extends BaseEntity> int batchRemove(Class<T> clazz, Serializable[] ids) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.notEmpty(ids, "ids不能为空");

		String hql = "delete from " + clazz.getName() + " where " + getIdName(clazz) + " in (" + StringUtils.join(ids, ',') + ")";
		return execute(hql);
	}

	public <T extends BaseEntity> int batchRemove(Class<T> clazz, Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String hql = "delete " + clazz.getName();
		String where = parser.parse(false);
		if (where != null)
			hql += where;
		
		return execute(hql, parser.params());
	}

	public <T extends BaseEntity> int batchUpdate(Class<T> clazz, String[] fields, Object[] values,
			Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.notEmpty(fields, "fields不能为空");
		Assert.notEmpty(values, "values不能为空");
		Assert.isTrue(fields.length == values.length, "fields中的字段名数量必须与values中的字段值数量相等");
		
		StringBuffer buf = new StringBuffer();
		buf.append("update ");
		buf.append(clazz.getName());
		
		boolean first = true;
		for (String field : fields) {
			if (first) {
				buf.append(" set ");
				
				first = false;
			} else
				buf.append(", ");
			
			buf.append(field);
			buf.append(" = ?");
		}
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String where = parser.parse(false);
		if (where != null)
			buf.append(where);
		String hql = buf.toString();
		
		Object[] params = values;
		params = ArrayUtils.addAll(params, parser.params());

		return execute(hql, params);
	}

	public int execute(String hql, Object... values) {
		Assert.hasText(hql, "hql不能为空");
		
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		if(logger.isDebugEnabled())
			logger.debug("execute hql{{}}, param: {}", hql, values);
		
		return query.executeUpdate();
	}

	public int execute(String hql, Map<String, ?> values) {
		Assert.hasText(hql, "hql不能为空");
		
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("execute hql{{}}, param: {}", hql, values);
		
		return query.executeUpdate();
	}

	public List<Map<String, Object>> sqlQuery(String sql, Condition condition) {
		Assert.hasText(sql, "sql不能为空");
		
//		sql = StringUtils.trim(sql);
//		if (sql.charAt(sql.length() - 1) == ';')
//			return sqlQuery(sql);
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String where = parser.parse(true);
		Object[] params = parser.params();
		
		sql = combinSql(sql, where);
		return sqlQuery(sql, condition.getStart(), condition.getLimit(), params);
	}

	public List<Map<String, Object>> sqlQuery(String sql, Object... values) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		
		return sqlQuery(sql, mapper, 0, 0, values);
	}

	public List<Map<String, Object>> sqlQuery(String sql, int start, int limit,
			Object... values) {
		ColumnMapRowMapper mapper = new ColumnMapRowMapper();
		
		return sqlQuery(sql, mapper, start, limit, values);
	}

	public <T> List<T> sqlQuery(Class<T> clazz, String sql, Condition condition) {
		Assert.hasText(sql, "sql不能为空");
		
//		sql = StringUtils.trim(sql);
//		if (sql.charAt(sql.length() - 1) == ';')
//			return sqlQuery(clazz, sql);
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String where = parser.parse(true);
		Object[] params = parser.params();
		
		sql = combinSql(sql, where);
		return sqlQuery(clazz, sql, condition.getStart(), condition.getLimit(), params);
	}

	public <T> List<T> sqlQuery(Class<T> clazz, String sql, 
			Object... values) {
		BeanPropertyRowMapper<T> mapper = new BeanPropertyRowMapper<T>(clazz);
		mapper.setPrimitivesDefaultedForNullValue(true);
		
		return sqlQuery(sql, mapper, 0, 0, values);
	}

	public <T> List<T> sqlQuery(Class<T> clazz, String sql, int start,
			int limit, Object... values) {
		BeanPropertyRowMapper<T> mapper = new BeanPropertyRowMapper<T>(clazz);
		
		return sqlQuery(sql, mapper, start, limit, values);
	}

	protected <T> List<T> sqlQuery(String sql, RowMapper<T> mapper, int start,
			int limit, Object... values) {
		Assert.hasText(sql, "sql不能为空");
		Assert.isTrue(start >= 0, "start必须为0或者正数");
		Assert.isTrue(limit >= 0, "limit必须为0或者正数");
		
		if(logger.isDebugEnabled())
			logger.debug("query sql{{}}, param: {}, start: {}, limit: {}", new Object[]{sql, values, start, limit});
		
		SqlQueryWork work = new SqlQueryWork<T>(sql, mapper, start, limit, values);
		
		getSession().doWork(work);
		return work.getResult();
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.dao.Dao#sqlCount(java.lang.String, com.lily.dap.dao.Condition)
	 */
	public long sqlCount(String sql, Condition condition) {
		Assert.hasText(sql, "sql不能为空");
		
//		sql = StringUtils.trim(sql);
//		if (sql.charAt(sql.length() - 1) == ';')
//			return sqlQuery(sql);
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String where = parser.parse(false);
		Object[] params = parser.params();
		
		sql = "select count(0) as cnt from (" + combinSql(sql, where) + ") mytable";
		List<Map<String, Object>> result = sqlQuery(sql, 0, 0, params);
		
		return (Integer)result.get(0).get("cnt");
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.dao.Dao#sqlCount(java.lang.String, java.lang.Object[])
	 */
	public long sqlCount(String sql, Object... values) {
		int index = StringUtils.lastIndexOfIgnoreCase(sql, "order by");
		if (index >= 0)
			sql = sql.substring(0, index);
		
		sql = "select count(0) as cnt from (" + sql + ") mytable";
		List<Map<String, Object>> result = sqlQuery(sql, 0, 0, values);
		
		return (Integer)result.get(0).get("cnt");
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	protected Query createQuery(final String hql, int start,
			int limit, final Object... values) {
		Assert.hasText(hql, "hql不能为空");
		Assert.isTrue(start >= 0, "start必须为0或者正数");
		Assert.isTrue(limit >= 0, "limit必须为0或者正数");
		
		Query query = getSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		if (start > 0)
			query.setFirstResult(start);
		
		if (limit > 0)
			query.setMaxResults(limit);
		
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	protected Query createQuery(final String hql, int start,
			int limit, final Map<String, ?> values) {
		Assert.hasText(hql, "hql不能为空");
		Assert.isTrue(start >= 0, "start必须为0或者正数");
		Assert.isTrue(limit >= 0, "limit必须为0或者正数");
		
		Query query = getSession().createQuery(hql);
		if (values != null) {
			query.setProperties(values);
		}
		
		if (start > 0)
			query.setFirstResult(start);
		
		if (limit > 0)
			query.setMaxResults(limit);
		
		return query;
	}

	public void sqlExecute(String sql, Condition condition) {
		Assert.hasText(sql, "sql不能为空");
		
		if (condition == null) condition = Condition.create();
		ConditionParser parser = new ConditionParser(condition);
		
		String where = parser.parse(false);
		Object[] params = parser.params();
		
		sql = combinSql(sql, where);
		sqlExecute(sql, params);
	}

	public void sqlExecute(String sql, Object... values) {
		Assert.hasText(sql, "sql不能为空");
		
		SqlExecuteWork work = new SqlExecuteWork(sql, values);
		
		if(logger.isDebugEnabled())
			logger.debug("execute sql{{}}, param: {}", sql, values);
		
		getSession().doWork(work);
	}

	public void batchSqlExecute(String sql) {
		Assert.hasText(sql, "sql不能为空");
		
		List<String> statements = new ArrayList<String>();
		SqlUtils.splitSqlScript(sql, multiSqlDelim, statements);
		
		batchSqlExecute(statements.toArray(new String[0]));
	}

	public void batchSqlExecute(String[] sqls) {
		Assert.notEmpty(sqls, "sql不能为空");
		
		BatchSqlExecuteWork work = new BatchSqlExecuteWork(sqls);
		
		if(logger.isDebugEnabled())
			logger.debug("batch execute sql{{}}", sqls);
		
		getSession().doWork(work);
	}

	/**
	 * 取得对象的主键名.
	 */
	protected String getIdName(Class<?> clazz) {
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		
		return meta.getIdentifierPropertyName();
	}
	
	private String combinSql(String sql, String where) {
		sql = StringUtils.trim(sql);
		
		if (sql == null || "".equals(sql))
			return sql;

		boolean endWithSemicolon = false;
		if (sql.charAt(sql.length() - 1) == ';') {
			endWithSemicolon = true;
			
			sql = sql.substring(0, sql.length() - 1);
		}
		
		if (where != null) {
			if (where.startsWith(" where ")) {		//如果where子句中有where查询条件
				boolean needReplaceWhereSymbol = false;
				
				//检查给定的sql语句结尾是否包含where子句
				int pos = StringUtils.lastIndexOfIgnoreCase(sql, "where");
				if (pos > 0) {
					String s = sql.substring(pos + 5);
					if (StringUtils.indexOfIgnoreCase(s, " from ") == -1 && StringUtils.indexOfIgnoreCase(s, " on ") == -1)
						needReplaceWhereSymbol = true;
				}
				
				//如果给定的sql语句结尾包含where子句，则需要替换where子句开头的" where "为" and "
				if (needReplaceWhereSymbol)
					where = StringUtils.replaceOnce(where, " where ", " and ");
			}
			
			sql += where;
		}
		
		if (endWithSemicolon)
			sql += ';';
		
		return sql;
	}

//	public void save(String entityName, Map<?, ?> map) {
//		Session dynamicSession = getSession().getSession(EntityMode.MAP);
//		
//		dynamicSession.save(entityName, map);
//	}
}
