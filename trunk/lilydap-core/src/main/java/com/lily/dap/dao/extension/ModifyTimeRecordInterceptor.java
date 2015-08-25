/**
 * 
 */
package com.lily.dap.dao.extension;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 自动设置实体对象的修改时间的拦截器，通过Aspect注解实现在调用Dao接口的saveOrUpdate和batchSaveOrUpdate方法前执行<br>
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