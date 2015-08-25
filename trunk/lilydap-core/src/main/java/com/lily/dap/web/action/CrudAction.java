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
 * 提供了基本的CRUD操作Action，通过前台参数传入要操作的实体类名称和操作数据，实现对该实体类的CRUD操作
 * 
 * @author 邹学模
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
			jsonStr = JsonHelper.combinFailJsonString("检索的数据不存在，可能已经被删除！");
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
				throw new RuntimeException("不能创建实体类[" + BaseEntityHelper.getEntityLabel(entityClass, true)
						+ "]，请检查实体类是否定义默认构造函数、或者默认构造函数是否为公共可访问！", e);
			}
			
			changedFields = BaseEntityHelper.getEntityAllowCreateFields(entityClass);
		}
		
		Struts2Utils.writeModel(entity, changedFields);

		String jsonStr;
		try {
			entity = manager.saveOrUpdate(entity);

			jsonStr = JsonHelper.combinSuccessJsonString("message", "数据保存成功！");
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

			jsonStr = JsonHelper.combinSuccessJsonString("message", "数据删除成功！");
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString("要删除的数据不存在！");
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
}
