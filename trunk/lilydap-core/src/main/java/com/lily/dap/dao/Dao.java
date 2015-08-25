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
 * ���ݲ����ӿڣ��ṩ���ʵ������CRUD����
 *
 * @author zouxuemo
 */
public interface Dao {
	/**
	 * ��������ID�ĸ���������������id��ʵ����󲻴��ڣ�����null
	 * 
	 * @param clazz Ҫ������ʵ����
	 * @param id Ҫ������ʵ����id
	 * @return 
	 */
	public <T extends BaseEntity> T get(final Class<T> clazz, final Serializable id);
	
	/**
	 * ������������ID����ĸ�������󼯺ϣ����ID�����ĳ��id��Ӧ��ʵ����󲻴��ڣ�����ԣ�����������ڣ����ؿ����ݵ��б���(sizeΪ0)
	 * 
	 * 
	 * @param clazz Ҫ������ʵ����
	 * @param ids Ҫ������ʵ����id����
	 * @return 
	 */
	public <T extends BaseEntity> List<T> gets(final Class<T> clazz, final Serializable[] ids);
	
	/**
	 * ����������ѯ�����ĸ�������󼯺�
	 * 
	 * 
	 * @param clazz Ҫ������ʵ����
	 * @param condition ������ѯ�������������ͷ�ҳ��Ϣ
	 * @return 
	 */
	public <T extends BaseEntity> List<T> query(final Class<T> clazz, final Condition condition);
	
	/**
	 * ʹ��DetachedCriteria���в�ѯ
	 * 
	 * @param criteria
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final DetachedCriteria criteria);
	
	/**
	 * ʹ��DetachedCriteria���в�ѯ������ָ��ָ����ʼ�����޶������Ĳ�ѯ�����
	 * 
	 * @param criteria
	 * @param start
	 * @param limit
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final DetachedCriteria criteria, final int start, final int limit);
	
	/**
	 * ִ�и�����HQL����ѯ�����ز�ѯ�����
	 * 
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final Object... values);
	
	/**
	 * ִ�и�����HQL����ѯ�����ز�ѯ�����
	 * 
	 * @param hql
	 * @param values ��������,�����ư�.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final Map<String, ?> values);
	
	/**
	 * ִ�и�����HQL����ѯ������ָ��ָ����ʼ�����޶������Ĳ�ѯ�����
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final int start, final int limit, final Object... values);
	
	/**
	 * ִ�и�����HQL����ѯ������ָ��ָ����ʼ�����޶������Ĳ�ѯ�����
	 * 
	 * @param hql
	 * @param start
	 * @param limit
	 * @param values ��������,�����ư�.
	 * @return
	 */
	public <T extends BaseEntity> List<T> query(final String hql, final int start, final int limit, final Map<String, ?> values);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ���������¼����
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ���ؼ�¼����
	 */
	public long count(final Class<?> clazz, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶεĺϼ�ֵ
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param field Ҫͳ�Ƶ����������ֶ����ͱ�������ֵ�ͣ�byte, short, int, long, float, double�����װ�ࣩ
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ���غϼ�ֵ�������ֶ�Ϊbyte, short, int, long���ͣ�����long�������ֶ�Ϊfloat, double������double
	 */
	public Number sum(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶ�ƽ��ֵ
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param field Ҫͳ�Ƶ����������ֶ����ͱ�������ֵ�ͣ�byte, short, int, long, float, double�����װ�ࣩ
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ����ƽ��ֵ
	 */
	public double avg(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶε����ֵ
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param field Ҫͳ�Ƶ�������
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return �������ֵ������ֵ�������ֶ�������ͬ
	 */
	public <X> X max(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶε���Сֵ
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param field Ҫͳ�Ƶ�������
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ������Сֵ������ֵ�������ֶ�������ͬ
	 */
	public <X> X min(final Class<?> clazz, String field, final Condition condition);
	
	/**
	 * ͨ��ִ��hql��䣬����Ψһֵ��֧�ֲ������ݣ����磺select count(*) from Module where code like ?
	 * 
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public <X> X unique(final String hql, final Object... values);
	
	/**
	 * ͨ��ִ��hql��䣬����Ψһֵ��֧�ֲ������ݣ����磺select count(*) from Module where code like :code
	 * 
	 * @param hql
	 * @param values ��������,�����ư�.
	 * @return
	 */
	public <X> X unique(final String hql, final Map<String, ?> values);
	
	/**
	 * ˢ�»���
	 *
	 */
	public void flush();
	
	/**
	 * ����װ�����������ˢ�»��棩
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void reload(T entity);
	
	/**
	 * ����ʵ�����IDֵ�Ƿ�Ϊ0���б���������������
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void saveOrUpdate(final T entity);
	
	/**
	 * ������������������󣬸���ʵ�����IDֵ�Ƿ�Ϊ0���б���������������
	 * 
	 * @param entitys
	 */
	public <T extends BaseEntity> void batchSaveOrUpdate(final T[] entitys);
	
	/**
	 * ɾ������ʵ�����
	 * 
	 * @param entity
	 */
	public <T extends BaseEntity> void remove(final T entity);
	
	/**
	 * ɾ������ID�ĸ��������
	 * 
	 * @param clazz
	 * @param id
	 */
	public <T extends BaseEntity> void remove(final Class<T> clazz, final Serializable id);
	
	/**
	 * ����ɾ������������ID��Ӧ������󣬷���ɾ���ļ�¼����
	 * 
	 * @param clazz
	 * @param ids
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final Serializable[] ids);
	
	/**
	 * ����ɾ��������ѯ���������и�������󼯺ϣ�����ɾ���ļ�¼����
	 * 
	 * @param clazz
	 * @param condition
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final Condition condition);
	
	/**
	 * �������¸�����ѯ���������ݣ����ظ��µļ�¼����
	 * 
	 * @param clazz
	 * @param fields
	 * @param values
	 * @param condition
	 */
	public <T extends BaseEntity> int batchUpdate(final Class<T> clazz, final String[] fields, final Object[] values, final Condition condition);
	
	/**
	 * ִ�и�����HQL��䣬����Ӱ��ļ�¼����
	 * 
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public int execute(final String hql, final Object... values);
	
	/**
	 * ִ�и�����HQL��䣬����Ӱ��ļ�¼����
	 * 
	 * @param hql
	 * @param values ��������,�����ư�.
	 * @return
	 */
	public int execute(final String hql, final Map<String, ?> values);
	
	/**
	 * ִ��SQL��ѯ�����ش���ֶ������ֶ�ֵӳ���Map��List����<br>
	 * ͨ������Condition�Ĳ�ѯ�����������������ݣ���׷�ӵ�sql����У��γ�������sql���
	 * 
	 * @param sql ���԰���where�����������ܰ���order by����������sql���
	 * @param condition ������ѯ���������������ͷ�ҳ��Ϣ��Ҫ�����condition�еĲ�ѯ����ֵҪ�Ͳ�ѯ�ֶε�������ƥ��
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final Condition condition);
	
	/**
	 * ִ��SQL��ѯ�����ش���ֶ������ֶ�ֵӳ���Map��List����
	 * 
	 * @param sql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final Object... values);
	
	/**
	 * ִ��SQL��ѯ�����ش���ֶ������ֶ�ֵӳ���Map��List����
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public List<Map<String, Object>> sqlQuery(final String sql, final int start, final int limit, final Object... values);
	
	/**
	 * ִ��SQL��ѯ�����ظ���ʵ������List����<br>
	 * ͨ������Condition�Ĳ�ѯ�����������������ݣ���׷�ӵ�sql����У��γ�������sql���
	 * 
	 * @param clazz
	 * @param sql ���԰���where�����������ܰ���order by����������sql���
	 * @param condition ������ѯ���������������ͷ�ҳ��Ϣ��Ҫ�����condition�еĲ�ѯ����ֵҪ�Ͳ�ѯ�ֶε�������ƥ��
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final Condition condition);
	
	/**
	 * ִ��SQL��ѯ�����ظ���ʵ������List����
	 * 
	 * @param clazz
	 * @param sql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final Object... values);
	
	/**
	 * ִ��SQL��ѯ�����ظ���ʵ������List����<br>
	 * ֧�ֶ��������������ݿ��ֶ���֮��ʹ�õ���֮���д�Զ�ת�»���Сдӳ��ģʽ�������������ΪcreateDate�����ԣ���Ӧ������Ϊcreate_date�����ݿ��ֶΣ���ִ��sql������Զ�ӳ��
	 * 
	 * @param clazz
	 * @param sql
	 * @param start
	 * @param limit
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public <T> List<T> sqlQuery(final Class<T> clazz, final String sql, final int start, final int limit, final Object... values);
	
	/**
	 * ִ��SQL��ѯ������List���ϵ�������<br>
	 * ͨ������Condition�Ĳ�ѯ��������׷�ӵ�sql����У��γ�������sql���
	 * 
	 * @param sql ���԰���where�����������ܰ���order by����������sql���
	 * @param condition ������ѯ������Ҫ�����condition�еĲ�ѯ����ֵҪ�Ͳ�ѯ�ֶε�������ƥ��
	 * @return
	 */
	public long sqlCount(final String sql, final Condition condition);
	
	/**
	 * ִ��SQL��ѯ������List���ϵ�������
	 * 
	 * @param sql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 */
	public long sqlCount(final String sql, final Object... values);
	
	/**
	 * ִ��SQL�������<br>
	 * ͨ������Condition�Ĳ�ѯ�������ݣ���׷�ӵ�sql����У��γ�������sql���
	 * 
	 * @param sql ���԰���where�����������ܰ���order by����������sql���
	 * @param condition condition ������ѯ���������Բ�ѯ�����еķ�ҳ�����������������Ҫ�����condition�еĲ�ѯ����ֵҪ�Ͳ�ѯ�ֶε�������ƥ��
	 */
	public void sqlExecute(final String sql, final Condition condition);
	
	/**
	 * ִ��SQL�������
	 * 
	 * @param sql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 */
	public void sqlExecute(final String sql, final Object... values);
	
	/**
	 * ����ִ��SQL�������
	 * 
	 * @param sqls
	 */
	public void batchSqlExecute(final String[] sqls);
	
	/**
	 * ����ִ��SQL�������
	 * 
	 * @param sql ��";"�ָ��˶���sql����ַ���
	 */
	public void batchSqlExecute(final String sql);
	
	/**
	 * ȡ�õ�ǰSession�����δ�ҵ�������������ص�ǰ�̴߳�����Session���統ǰ�߳�δ����Session���򴴽�֮
	 * <p>����Ǵ�thread�л�ȡsession����Ҫ�����������ݿ������ɺ����clearThreadSession�������ͷ����ݿ�����
	 * 
	 * @return
	 */
	public Session getSession();
	
	/**
	 * ������رյ�ǰ�̴߳�����Session��������getSession�����д�����Session��
	 * 
	 */
	public void clearThreadSession();
	
	/**
	 * ���õ�ǰHibernate��SessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(final SessionFactory sessionFactory);
	
	/**
	 * ���ص�ǰHibernate��SessionFactory
	 * 
	 * @return
	 */
	public SessionFactory getSessionFactory();
}
