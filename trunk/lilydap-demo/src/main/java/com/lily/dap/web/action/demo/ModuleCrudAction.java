package com.lily.dap.web.action.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.demo.Module;
import com.lily.dap.service.demo.ModuleManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.convert.ConvertUtils;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

public class ModuleCrudAction extends BaseAction {
	/**
	 * <code>serialVersionUID</code> serialVersionUID
	 */
	private static final long serialVersionUID = -329608136405380288L;
	
	@Autowired
	private ModuleManager moduleManager;
	
	// @Override
	// public String execute() {
	// return SUCCESS;
	// }

	public String list() {
		/*
		 parameters:
		 	condition.
		 	order
		 	sort
		 	dir
		 	start
		 	limit
		 	pageNo
		 	pageSize
		 */
		Condition condition = Struts2Utils.buildCondition(Module.class);
		
		long count = moduleManager.count(condition);
		List<?> result = moduleManager.query(condition);

		String jsonStr = JsonHelper.combinSuccessJsonString("count", count, "data", result);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}

	public String get() {
		/*
		 parameters:
		 	id
		 */
		long id = Struts2Utils.getLongParameter("id", 0);

		String jsonStr;
		try {
			Module module;
			if (id == 0)
				module = new Module();
			else {
				module = moduleManager.get(id);
			}
			List<Module> list = new ArrayList<Module>();
			list.add(module);
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
		 	entity
		 */
		long id = Struts2Utils.getLongParameter("id", 0);

		Module module;
		String[] changedFields;
		if (id > 0) {
			module = moduleManager.get(id);
			
			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Module.class);
		} else {
			try {
				module = new Module();
			} catch (Exception e) {
				throw new RuntimeException("���ܴ���ʵ����[ģ��]������ʵ�����Ƿ���Ĭ�Ϲ��캯��������Ĭ�Ϲ��캯���Ƿ�Ϊ�����ɷ��ʣ�", e);
			}
			
			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Module.class);
		}
		
		Struts2Utils.writeModel(module, changedFields);

		String jsonStr;
		try {
			module = moduleManager.saveOrUpdate(module);

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
		 	id
		 */
		String jsonStr;
		try {
			long id = Struts2Utils.getLongParameter("id", 0);
			if (id > 0)
				moduleManager.remove(id);
			else {
				String s = Struts2Utils.getParameter("id");
				if (s != null) {
					String[] tmp = s.split(",");
					Long[] v = (Long[])ConvertUtils.convert(tmp, Long.class);
					
					moduleManager.batchRemove(v);
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
