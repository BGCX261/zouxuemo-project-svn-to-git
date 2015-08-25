package com.lily.dap.service;

import java.io.Serializable;
import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * �����Ĺ���ӿڣ����ṩ����֧�֣�ʵ���˶���������CRUD����
 * 
 * @author zouxuemo
 * 
 */
public interface GenericManager<T extends BaseEntity> {
	/**
	 * ��������ID�ĸ��������
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public T get(final long id) throws DataNotExistException;
	
	/**
	 * ����������ѯ�����ĸ�������󼯺�
	 * ���ؼ������Ľ��
	 * 
	 * @param condition
	 * @return 
	 */
	public List<T> query(final Condition condition);
	
	/**
	 * ����������ѯ�����ĸ�������󼯺����������Բ�ѯ�����еķ�ҳ������
	 * 
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ���ؼ�¼����
	 */
	public long count(final Condition condition);
	
	/**
	 * ����������ѯ�����ĸ�������󼯺����������Բ�ѯ�����еķ�ҳ������
	 * 
	 * @param field Ҫͳ�Ƶ����������ֶ����ͱ�������ֵ�ͣ�byte, short, int, long, float, double�����װ�ࣩ
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ���غϼ�ֵ�������ֶ�Ϊbyte, short, int, long���ͣ�����long�������ֶ�Ϊfloat, double������double
	 */
	public Number sum(String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶ�ƽ��ֵ
	 * 
	 * @param field Ҫͳ�Ƶ����������ֶ����ͱ�������ֵ�ͣ�byte, short, int, long, float, double�����װ�ࣩ
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ����ƽ��ֵ
	 */
	public double avg(String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶε����ֵ
	 * 
	 * @param field Ҫͳ�Ƶ�������
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return �������ֵ������ֵ�������ֶ�������ͬ
	 */
	public <X> X max(String field, final Condition condition);
	
	/**
	 * ͳ�Ƹ�����ѯ�����ĸ��������ĳ���ֶε���Сֵ
	 * 
	 * @param field Ҫͳ�Ƶ�������
	 * @param condition �����Ĳ�ѯ���������Բ�ѯ�����еķ�ҳ���������������
	 * @return ������Сֵ������ֵ�������ֶ�������ͬ
	 */
	public <X> X min(String field, final Condition condition);
	
	/**
	 * ����ʵ�����IDֵ�Ƿ�Ϊ0���б�����������������ֱ�ӱ��棬������ֶ�ע���Ƿ����˿��޸��ֶ�
	 * 
	 * @param entity
	 * @return
	 */
	public T saveOrUpdate(final T entity);
	
	/**
	 * ���ݶ����ID�Ƿ������Զ����»򴴽�ʵ����󣬽����ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param entity
	 */
	public T save(final T entity);
	
	/**
	 * ����ʵ��������ݣ������ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param entity
	 * @return
	 */
	public T create(final T entity);
	
	/**
	 * ����ʵ��������ݣ������ݸ����ֶ��Ƿ�����������µ�ע����ȷ�����޸��ֶ�
	 * 
	 * @param entity
	 * @return
	 */
	public T modify(final T entity);
	
	/**
	 * ������������������󣬸���ʵ�����IDֵ�Ƿ�Ϊ0���б���������������
	 * 
	 * @param entitys
	 */
	public void batchSaveOrUpdate(final T[] entitys);
	
	/**
	 * ɾ������ID�ĸ��������
	 * 
	 * @param id
	 */
	public void remove(final long id) throws DataNotExistException;
	
	/**
	 * ����ɾ������������ID��Ӧ������󣬷���ɾ���ļ�¼����
	 * 
	 * @param ids
	 */
	public int batchRemove(final long[] ids);
	
	/**
	 * ����ɾ������������ID��Ӧ������󣬷���ɾ���ļ�¼����
	 * 
	 * @param clazz
	 * @param ids
	 */
	public int batchRemove(final Serializable[] ids);
	
	/**
	 * ����ɾ��������ѯ���������и�������󼯺ϣ�����ɾ���ļ�¼����
	 * 
	 * @param condition
	 */
	public int batchRemove(final Condition condition);
}
