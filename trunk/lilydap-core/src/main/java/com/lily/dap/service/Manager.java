package com.lily.dap.service;

import java.io.Serializable;
import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * �����Ĺ���ӿڣ�ʵ���˶���������CRUD����
 * 
 * @author zouxuemo
 * 
 */
public interface Manager {
	/**
	 * ��������ID�ĸ��������
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T get(final Class<T> clazz, final long id) throws DataNotExistException;
	
	/**
	 * ����������ѯ�����ĸ�������󼯺�
	 * ���ؼ������Ľ��
	 * 
	 * @param clazz
	 * @param condition
	 * @return 
	 */
	public <T extends BaseEntity> List<T> query(final Class<T> clazz, final Condition condition);
	
	/**
	 * ����������ѯ�����ĸ�������󼯺����������Բ�ѯ�����еķ�ҳ������
	 * 
	 * @param clazz Ҫͳ�Ƶ�ʵ����
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ���ؼ�¼����
	 */
	public long count(final Class<?> clazz, final Condition condition);
	
	/**
	 * ����������ѯ�����ĸ�������󼯺����������Բ�ѯ�����еķ�ҳ������
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
	 * ����ʵ�����IDֵ�Ƿ�Ϊ0���б�����������������ֱ�ӱ��棬������ֶ�ע���Ƿ����˿��޸��ֶ�
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T saveOrUpdate(final T entity);
	
	/**
	 * ���ݶ����ID�Ƿ������Զ����»򴴽�ʵ����󣬽����ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T extends BaseEntity> T save(final T entity);
	
	/**
	 * ����ʵ��������ݣ������ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T create(final T entity);
	
	/**
	 * ����ʵ��������ݣ������ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T modify(final T entity);
	
	/**
	 * ������������������󣬸���ʵ�����IDֵ�Ƿ�Ϊ0���б���������������
	 * 
	 * @param entitys
	 */
	public <T extends BaseEntity> void batchSaveOrUpdate(final T[] entitys);
	
	/**
	 * ɾ������ID�ĸ��������
	 * 
	 * @param clazz
	 * @param id
	 */
	public <T extends BaseEntity> void remove(final Class<T> clazz, final long id) throws DataNotExistException;
	
	/**
	 * ����ɾ������������ID��Ӧ������󣬷���ɾ���ļ�¼����
	 * 
	 * @param clazz
	 * @param ids
	 */
	public <T extends BaseEntity> int batchRemove(final Class<T> clazz, final long[] ids);
	
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
}
