/**
 * 
 */
package com.lily.dap.dao.extension;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * �Զ�����ʵ�������޸�ʱ�����������ͨ��Aspectע��ʵ���ڵ���Dao�ӿڵ�saveOrUpdate��batchSaveOrUpdate����ǰִ��<br>
 * 
 * @author zouxuemo
 *
 */
@Aspect
public class ModifyTimeRecordInterceptor {
	@Before("execution (* com.lily.dap.dao.hibernate.HibernateDao.saveOrUpdate(..)) && args(entity)")
	public void beforeSaveOrUpdat(Object entity) {
		if (entity instanceof ModifyTimeRecorder)
			((ModifyTimeRecorder)entity).recordModifyTime(new Date());
	}
	
	@Before("execution (* com.lily.dap.dao.hibernate.HibernateDao.batchSaveOrUpdate(..)) && args(entitys)")
	public void beforeBatchSaveOrUpdate(Object[] entitys) {
		Date now = new Date();
		
		if (entitys != null && entitys.length > 0) {
			for (Object entity : entitys)
				if (entity instanceof ModifyTimeRecorder)
					((ModifyTimeRecorder)entity).recordModifyTime(now);
		}
	}
}