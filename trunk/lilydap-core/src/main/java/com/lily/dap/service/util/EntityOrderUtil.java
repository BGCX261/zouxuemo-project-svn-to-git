/**
 * 
 */
package com.lily.dap.service.util;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.dao.condition.Order;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.ServiceException;

/**
 * ʵ������˳�򹤾��࣬�ṩ��ȡ���˳��š������ֶ�˳��ĳ���ֶ�����/����˳��ȹ���
 * 
 * @author zouxuemo
 *
 */
public class EntityOrderUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Dao dao;
	
	public EntityOrderUtil(Dao dao) {
		super();
		this.dao = dao;
	}

	/**
	 * �����������������ţ����û�У��򷵻�0
	 *
	 * @param clazz Ҫ�����Ķ�����
	 * @param orderFieldName Ҫ�����������ֶ���
	 * @param filterFieldNames ������Χ��Ҫ���Ƶ��ֶ��������飩
	 * @param filterFieldValues ������Χ��Ҫ���Ƶ��ֶ�ֵ���飩
	 * @return ��������
	 */
	public <T extends BaseEntity> int getEntityMaxOrder(Class<T> clazz, String orderFieldName, Condition condition) {
		Assert.notNull(clazz, "clazz����Ϊ��");
		Assert.hasText(orderFieldName, "orderFieldName����Ϊ��");
		
		if (condition == null)
			condition = Condition.create();
		
		Object o = dao.max(clazz, orderFieldName, condition);
		
		int sn;
		if (o == null)
			sn = 0;
		else if (o instanceof Long)
			sn = ((Long)o).intValue();
		else if (o instanceof Integer)
			sn = ((Integer)o).intValue();
		else
			throw new ServiceException(clazz.getName() + "." + orderFieldName + "������Ϊ�����ֶΣ�Ҫ���ֶ�����Ϊint����long��");
		
		return sn;
	}

	/**
	 * ���������е�����˳��
	 *
	 * @param clazz Ҫ�����Ķ�����
	 * @param id Ҫ�����Ķ���ID
	 * @param order  ���������������ֵ������ǰ���򣻷���������򡣸�������ֵ�����������Ĳ�ֵ��ֱ����ͷ���ߵ�β��
	 * @param orderFieldName �����������ֶ���
	 * @param condition ����Χ
	 * @return �����ܵ������Ķ����б�
	 * @throws DataNotExistException �������δ�ҵ����׳��쳣
	 */
	public <T extends BaseEntity> List<T> adjustEntityOrder(Class<T> clazz, long id, int order, String orderFieldName, Condition condition)
			throws DataNotExistException {
		Assert.notNull(clazz, "clazz����Ϊ��");
		Assert.hasText(orderFieldName, "orderFieldName����Ϊ��");
		
		if (order == 0)
			return null;
		
		T entity = dao.get(clazz, new Long(id));
		
		Object sn;
		try {
			sn = PropertyUtils.getProperty(entity, orderFieldName);
		} catch (Exception e) {
			logger.warn("����[" + entity.toString() + "]����˳����󣺼��������ֶ�[" + orderFieldName + "]ֵ����" + e.getMessage());
			return null;
		}
		
		if (condition == null)
			condition = Condition.create();
		
		int step = order < 0 ? -order : order;
		
		//������ǰ����˳��ǰ/�󣨰�����ǰ����ָ��step�Ķ����б������˳��ǰ����������ϰ��մ�С�������򣬷����մӴ�С����
		if (order < 0) 
			condition.le(orderFieldName, sn).desc(orderFieldName).page(1, step + 1);
		else
			condition.ge(orderFieldName, sn).asc(orderFieldName).page(1, step + 1);

		List<T> adjustEntityList = dao.query(clazz, condition);
		int size = adjustEntityList.size();
		if (size == 1)
			return null;
		
		//�滻�����ֵ��SNֵ�����ȵ�ǰ�ֵ�����滻�ɼ����е�һ�����ֵ����ţ�Ȼ�󼯺ϴ�ͷ��ʼ����һ���ֵ�����滻����һ���ֵ�����
		try {
			Object sn0 = PropertyUtils.getProperty(adjustEntityList.get(size-1), orderFieldName);
			for (int i = size-1; i > 0; i--) {
				sn = PropertyUtils.getProperty(adjustEntityList.get(i-1), orderFieldName);
				PropertyUtils.setProperty(adjustEntityList.get(i), orderFieldName, sn);
			}
			PropertyUtils.setProperty(adjustEntityList.get(0), orderFieldName, sn0);
			
			//���α���ÿ���ֵ�����
			for (int i = 0; i < size-1; i++)
				dao.saveOrUpdate(adjustEntityList.get(i));
		} catch (Exception e) {
			logger.warn("����[" + entity.toString() + "]����˳����󣺼������������ֶ�[" + orderFieldName + "]ֵ����" + e.getMessage());
		}
		
		return adjustEntityList;
	}
	
	/**
	 * ����������Χ������ʽ�£���������ID�������ݵ���һ��������һ�������¼<br>
	 * Ҫ����Condition��������һ����ֻ��һ��������������ȷ����һ��������һ����ȡֵ����<br>
	 * ���δ�����������������׳��쳣��������ö���һ������������ֻȡ��һ����������������
	 * 
	 * @param clazz ʵ���������
	 * @param id Ҫ��λ�ļ�¼ID
	 * @param condition �ṩ��ѯ����������ʽ
	 * @param dir �ǲ�����һ����¼������һ����¼��Ϊtrue��������һ����¼��Ϊfalse��������һ����¼
	 * @return ���ز��ҵ��Ľ��
	 * @throws DataNotExistException �����λ�����ݲ����ڣ�����ȱʡ�����ֶδ��󣬻��������Ѿ���ͷ�����׳��쳣
	 */
	public <T extends BaseEntity> T offsetOne(Class<T> clazz, long id, Condition condition, boolean dir) throws DataNotExistException {
		T t = dao.get(clazz, new Long(id));
		
		if (condition == null || condition.getOrders().size() == 0)
			throw new ServiceException("������һ��/��һ����¼���󣺸����Ĳ�ѯ������δ������������");
		else if (condition.getOrders().size() > 1)
			logger.warn("offsetOne(Class<T> clazz, long id, Condition condition, String defaultOrder, boolean dir)����-Ҫ���ṩ�Ĳ�ѯ������ֻ����һ�������ֶΣ�����һ���������ֶν�����");
		
		String filedName = null, fieldDir = Condition.ORDER_ASC;
		Object fieldValue = null;
		
		Order order = condition.getOrders().get(0);
		filedName = order.getField();
		fieldDir = order.getOrder();
		
		try {
			fieldValue = PropertyUtils.getProperty(t, filedName);
		} catch (Exception e) {
			throw new ServiceException("offsetOne(Class<T> clazz, long id, Condition condition, boolean dir)�����ṩ�������ֶβ���������ֶ�");
		}

		condition.clearOrder();	//���ԭ������ʽ
		if ((dir && Condition.ORDER_ASC.equals(fieldDir)) || 
			(!dir && Condition.ORDER_DESC.equals(fieldDir)))	//���ԭ��˳���������Ҳ�����һ��������ԭ��˳���ǵ��򣬲��Ҳ�����һ���������ü������ڸ�����������������˳��Ϊ����
			condition.gt(filedName, fieldValue).asc(filedName);
		else 	//���ԭ��˳���������Ҳ�����һ��������ԭ��˳���ǵ��򣬲��Ҳ�����һ�����������ü���С�ڸ�����������������˳��Ϊ����
			condition.lt(filedName, fieldValue).desc(filedName);
		
		condition.page(1, 1);	//ֻ����һ����¼
		
		List<T> list = dao.query(clazz, condition);
		if (list.size() == 0)
			throw new DataNotExistException("��ǰ��¼�Ѿ���ͷ��");
		
		return list.get(0);
	}
}
