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
 * 实体排序顺序工具类，提供读取最大顺序号、调整字段顺序、某个字段上移/下移顺序等功能
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
	 * 检索对象中最大排序号，如果没有，则返回0
	 *
	 * @param clazz 要检索的对象类
	 * @param orderFieldName 要检索的排序字段名
	 * @param filterFieldNames 检索范围（要限制的字段名称数组）
	 * @param filterFieldValues 检索范围（要限制的字段值数组）
	 * @return 最大排序号
	 */
	public <T extends BaseEntity> int getEntityMaxOrder(Class<T> clazz, String orderFieldName, Condition condition) {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.hasText(orderFieldName, "orderFieldName不能为空");
		
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
			throw new ServiceException(clazz.getName() + "." + orderFieldName + "不能作为排序字段，要求字段类型为int或者long！");
		
		return sn;
	}

	/**
	 * 调整对象中的排序顺序
	 *
	 * @param clazz 要调整的对象类
	 * @param id 要调整的对象ID
	 * @param order  排序方向（如果给定负值，则向前排序；否则向后排序。根据数字值，调整调整的步值。直至到头或者到尾）
	 * @param orderFieldName 对象中排序字段名
	 * @param condition 排序范围
	 * @return 返回受到调整的对象列表
	 * @throws DataNotExistException 如果对象未找到，抛出异常
	 */
	public <T extends BaseEntity> List<T> adjustEntityOrder(Class<T> clazz, long id, int order, String orderFieldName, Condition condition)
			throws DataNotExistException {
		Assert.notNull(clazz, "clazz不能为空");
		Assert.hasText(orderFieldName, "orderFieldName不能为空");
		
		if (order == 0)
			return null;
		
		T entity = dao.get(clazz, new Long(id));
		
		Object sn;
		try {
			sn = PropertyUtils.getProperty(entity, orderFieldName);
		} catch (Exception e) {
			logger.warn("调整[" + entity.toString() + "]排序顺序错误：检索排序字段[" + orderFieldName + "]值错误－" + e.getMessage());
			return null;
		}
		
		if (condition == null)
			condition = Condition.create();
		
		int step = order < 0 ? -order : order;
		
		//检索当前对象顺序前/后（包括当前对象）指定step的对象列表（如果是顺序前，则检索集合按照从小到大排序，否则按照从大到小排序）
		if (order < 0) 
			condition.le(orderFieldName, sn).desc(orderFieldName).page(1, step + 1);
		else
			condition.ge(orderFieldName, sn).asc(orderFieldName).page(1, step + 1);

		List<T> adjustEntityList = dao.query(clazz, condition);
		int size = adjustEntityList.size();
		if (size == 1)
			return null;
		
		//替换检索字典的SN值，首先当前字典序号替换成集合中第一个的字典的序号，然后集合从头开始，上一个字典序号替换成下一个字典的序号
		try {
			Object sn0 = PropertyUtils.getProperty(adjustEntityList.get(size-1), orderFieldName);
			for (int i = size-1; i > 0; i--) {
				sn = PropertyUtils.getProperty(adjustEntityList.get(i-1), orderFieldName);
				PropertyUtils.setProperty(adjustEntityList.get(i), orderFieldName, sn);
			}
			PropertyUtils.setProperty(adjustEntityList.get(0), orderFieldName, sn0);
			
			//依次保存每个字典的序号
			for (int i = 0; i < size-1; i++)
				dao.saveOrUpdate(adjustEntityList.get(i));
		} catch (Exception e) {
			logger.warn("调整[" + entity.toString() + "]排序顺序错误：检索或者排序字段[" + orderFieldName + "]值错误－" + e.getMessage());
		}
		
		return adjustEntityList;
	}
	
	/**
	 * 给定条件范围和排序方式下，检索给定ID对象数据的上一条或者下一条对象记录<br>
	 * 要求在Condition必须设置一个且只有一个排序条件，以确定上一条或者下一条的取值方向<br>
	 * 如果未设置排序条件，则抛出异常，如果设置多于一个排序条件，只取第一个条件，其他忽略
	 * 
	 * @param clazz 实体对象类型
	 * @param id 要定位的记录ID
	 * @param condition 提供查询条件和排序方式
	 * @param dir 是查找上一条记录还是下一条记录，为true，查找下一条记录；为false，查找上一条记录
	 * @return 返回查找到的结果
	 * @throws DataNotExistException 如果定位的数据不存在，或者缺省排序字段错误，或者数据已经到头，则抛出异常
	 */
	public <T extends BaseEntity> T offsetOne(Class<T> clazz, long id, Condition condition, boolean dir) throws DataNotExistException {
		T t = dao.get(clazz, new Long(id));
		
		if (condition == null || condition.getOrders().size() == 0)
			throw new ServiceException("检索上一条/下一条记录错误：给定的查询条件中未设置排序条件");
		else if (condition.getOrders().size() > 1)
			logger.warn("offsetOne(Class<T> clazz, long id, Condition condition, String defaultOrder, boolean dir)警告-要求提供的查询条件中只设置一个排序字段，多于一个的排序字段将忽略");
		
		String filedName = null, fieldDir = Condition.ORDER_ASC;
		Object fieldValue = null;
		
		Order order = condition.getOrders().get(0);
		filedName = order.getField();
		fieldDir = order.getOrder();
		
		try {
			fieldValue = PropertyUtils.getProperty(t, filedName);
		} catch (Exception e) {
			throw new ServiceException("offsetOne(Class<T> clazz, long id, Condition condition, boolean dir)错误－提供的排序字段不是类里的字段");
		}

		condition.clearOrder();	//清除原有排序方式
		if ((dir && Condition.ORDER_ASC.equals(fieldDir)) || 
			(!dir && Condition.ORDER_DESC.equals(fieldDir)))	//如果原有顺序是正序并且查找下一条，或者原有顺序是倒序，并且查找上一条，则设置检索大于给定条件并且新排序顺序为正序
			condition.gt(filedName, fieldValue).asc(filedName);
		else 	//如果原有顺序是正序并且查找上一条，或者原有顺序是倒序，并且查找下一条，，则设置检索小于给定条件并且新排序顺序为倒序
			condition.lt(filedName, fieldValue).desc(filedName);
		
		condition.page(1, 1);	//只检索一条记录
		
		List<T> list = dao.query(clazz, condition);
		if (list.size() == 0)
			throw new DataNotExistException("当前记录已经到头！");
		
		return list.get(0);
	}
}
