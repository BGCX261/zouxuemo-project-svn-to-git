package com.lily.dap.web.action.organize;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.ExtTreeData;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.exception.DataContentInvalidateException;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.service.organize.RegisterManager;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.ExtUtils;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.security.RightUtils;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

@Results({@Result(name = "success", location = "/WEB-INF/page/system/personMgr.jsp")})
public class PersonAction extends BaseAction {
	private static final long serialVersionUID = 1209488321385632307L;

	@Autowired
	private PersonManager personManager;
	
	@Autowired
	private RegisterManager registerManager;
	
    /**
     * 默认（不带参数）打开人员管理页面
     * 
     * @throws Exception
     */
    public String execute() throws Exception {
		return SUCCESS;
    }
    
    /**
     * 检索给定ID的人员信息，或者初始化新人员信息
     * 
     * @throws Exception
     */
    public String get() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		Person person = new Person();
    		
    		if (id > 0)
    			person = personManager.getPerson(id);
    		
    		List<Person> personList = new ArrayList<Person>();
    		personList.add(person);
    		
    		jsonStr = JsonHelper.combinSuccessJsonString("data",personList);
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 给定查询条件，查询人员数据
     * 
     * @throws Exception
     */
    public String query() throws Exception {
    	String scope = Struts2Utils.getParameter("scope");
    	
    	Condition condition = Struts2Utils.buildCondition(Person.class);
		
		boolean onlyActivate = Struts2Utils.getBooleanParameter("activate", false);
		
		boolean dialogFlag = Struts2Utils.getBooleanParameter("dialog", false);
		List<Person> personList = new ArrayList<Person>();
		/*
		 * 如果scope是currentDepartment则只列出登录用户所以部门为根的人员列表。
		 */
		if(scope.equals("currentDepartment")){
			if(RightUtils.getCurrPersonsDepartments().size() > 0){
				long rootId = RightUtils.getCurrPersonsDepartments().get(0).getId();;
				personList = personManager.getPersons(rootId, 0, "", onlyActivate);
			}
		}else{
			personList = personManager.getPersons(condition, onlyActivate);
		}
		
		
		int count = (int) personManager.count(Person.class, condition);

		StringBuffer buf = new StringBuffer();
		buf.append("{\"total\":\"");
		buf.append(count);
		buf.append("\",\"data\":");
		
		if (!dialogFlag) {
			buf.append(JsonHelper.formatObjectToJsonString(personList));
		} else {	//为人员列表对话框提供数据
			List<ExtTreeData> dataList = new ArrayList<ExtTreeData>();
			
			for (Person person : personList) {
				ExtTreeData data = new ExtTreeData();
				data.setData("id", person.getId());
				data.setData("name", person.getName());
				data.setData("sex", person.getSex());
				
				dataList.add(data);
			}
			
			buf.append(ExtUtils.buildJSONString(dataList));
		}
		
		buf.append("}");
		
		String jsonStr = buf.toString();
		Struts2Utils.renderJson(jsonStr);
		return null;
    }
	
    /**
     * 添加或者修改人员信息
     * 
     * @throws Exception
     */
    public String save() throws Exception {
    	Person person = new Person();
    	long id = Struts2Utils.getLongParameter("id", 0);
    	String[] changedFields;
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		if (id == 0) {
    			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Person.class);
    			Struts2Utils.writeModel(person, changedFields);
    			
    			person = registerManager.register(person);
    		} else {
    			person = personManager.get(Person.class, id);
    			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Person.class);
    			Struts2Utils.writeModel(person, changedFields);
    			personManager.modifyPerson(person);
    		}
    		jsonStr = JsonHelper.combinSuccessJsonString("data", person);
		} catch (DataContentRepeatException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
    	return null;
    }
    
    /**
     * 删除给定ID的人员
     * 
     * @throws Exception
     */
    public String remove() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
		registerManager.unregister(id);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString("id",id);
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 激活/锁定人员
     * 
     * @throws Exception
     */
    public String enable() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		boolean enabled = Struts2Utils.getBooleanParameter("enabled");
		
		personManager.enablePerson(id, enabled);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString("id",id);
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 停用人员
     * 
     * @throws Exception
     */
    public String stop() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
		personManager.stopPerson(id); 
		
    	String jsonStr = JsonHelper.combinSuccessJsonString("id",id);
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }

	public String changePwd() {
		String oldPassword = Struts2Utils.getParameter("oldPwd");
		String newPassword = Struts2Utils.getParameter("newPwd");
		String repassword = Struts2Utils.getParameter("confirmPwd");
		
		String jsonStr;
		try {
			Person person = RightUtils.getCurrPerson();
			
			if (!person.getPassword().equals(personManager.encryptPassword(oldPassword)))
				throw new DataContentInvalidateException("原始密码输入错误，请检查！");
			else if (!newPassword.equals(repassword))
				throw new DataContentInvalidateException("两次输入的密码不一致，请检查！");
			
			personManager.changeUserPassword(RightUtils.getCurrPerson().getUsername(), newPassword);
			jsonStr = "{success:true, tip: '密码保存成功！'}";
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		} catch (DataContentInvalidateException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
    /**
     * 根据用户名和新密码修改用户口令
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String changePwdByManager(){
    	String username = Struts2Utils.getParameter("username");
		String newPwd = Struts2Utils.getParameter("newPwd");
		String confirmPwd = Struts2Utils.getParameter("confirmPwd");
		
		String jsonStr = null;
		if (!confirmPwd.equals(newPwd))
			jsonStr = JsonHelper.combinFailJsonString("新口令和确认口令不一致！");
		else {
			personManager.changeUserPassword(username, newPwd);
			jsonStr = JsonHelper.combinSuccessJsonString("tip","口令修改成功！");
	    }
	
		Struts2Utils.renderHtml(jsonStr);
		return null;
    }
}
