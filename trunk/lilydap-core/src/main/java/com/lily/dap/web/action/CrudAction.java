/**
 * 
 */
package com.lily.dap.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.convert.ConvertUtils;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

/**
 * �ṩ�˻�����CRUD����Action��ͨ��ǰ̨��������Ҫ������ʵ�������ƺͲ������ݣ�ʵ�ֶԸ�ʵ�����CRUD����
 * 
 * @author ��ѧģ
 */
public class CrudAction extends BaseAction {
	private static final long serialVersionUID = 2526918920722669131L;

	@Autowired
	private Manager manager;
	
	// @Override
	// public String execute() {
	// return SUCCESS;
	// }

	public String list() {
		/*
		 parameters:
		 	entityName
		 	condition.
		 	order
		 	sort
		 	dir
		 	start
		 	limit
		 	pageNo
		 	pageSize
		 */
		String entityName = Struts2Utils.getParameter("entityName");
		Class<? extends BaseEntity> entityClass = BaseEntityHelper.parseEntity(entityName);
			
		Condition condition = Struts2Utils.buildCondition(entityClass);
		
		long count = manager.count(entityClass, condition);
		List<?> result = manager.query(entityClass, condition);

		String jsonStr = JsonHelper.combinSuccessJsonString("count", count, "data", result);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}

	public String get() {
		/*
		 parameters:
		 	entityName
		 	id
		 */
		String entityName = Struts2Utils.getParameter("entityName");
		Class<? extends BaseEntity> entityClass = BaseEntityHelper.parseEntity(entityName);

		long id = Struts2Utils.getLongParameter("id", 0);

		String jsonStr;
		try {
			BaseEntity entity;
			if (id == 0)
				entity = entityClass.newInstance();
			else {
				entity = manager.get(entityClass, id);
			}
			
			List<BaseEntity> list = new ArrayList<BaseEntity>();
			list.add(entity);

			jsonStr = JsonHelper.combinSuccessJsonString("data", list);
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString("���������ݲ����ڣ������Ѿ���ɾ����");
		}  catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		} 

		Struts2Utils.renderJson(jsonStr);
		return null;
	}

	public String save() {
		/*
		 parameters:
		 	entityName
		 	entity
		 */
		String entityName = Struts2Utils.getParameter("entityName");
		Class<? extends BaseEntity> entityClass = BaseEntityHelper.parseEntity(entityName);
		
		long id = Struts2Utils.getLongParameter("id", 0);

		BaseEntity entity;
		String[] changedFields;
		if (id > 0) {
			entity = manager.get(entityClass, id);
			
			changedFields = BaseEntityHelper.getEntityAllowModifyFields(entityClass);
		} else {
			try {
				entity = entityClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("���ܴ���ʵ����[" + BaseEntityHelper.getEntityLabel(entityClass, true)
						+ "]������ʵ�����Ƿ���Ĭ�Ϲ��캯��������Ĭ�Ϲ��캯���Ƿ�Ϊ�����ɷ��ʣ�", e);
			}
			
			changedFields = BaseEntityHelper.getEntityAllowCreateFields(entityClass);
		}
		
		Struts2Utils.writeModel(entity, changedFields);

		String jsonStr;
		try {
			entity = manager.saveOrUpdate(entity);

			jsonStr = JsonHelper.combinSuccessJsonString("message", "���ݱ���ɹ���");
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}

	public String remove() {
		/*
		 parameters:
		 	entityName
		 	id
		 */
		String entityName = Struts2Utils.getParameter("entityName");
		Class<? extends BaseEntity> entityClass = BaseEntityHelper.parseEntity(entityName);

		String jsonStr;
		try {
			long id = Struts2Utils.getLongParameter("id", 0);
			if (id > 0)
				manager.remove(entityClass, id);
			else {
				String s = Struts2Utils.getParameter("id");
				if (s != null) {
					String[] tmp = s.split(",");
					Long[] v = ConvertUtils.convert2(tmp, Long.class);
					
					manager.batchRemove(entityClass, v);
				}
			}

			jsonStr = JsonHelper.combinSuccessJsonString("message", "����ɾ���ɹ���");
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString("Ҫɾ�������ݲ����ڣ�");
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
}
