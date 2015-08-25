package com.lily.dap.web.action.organize;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.DepartmentManager;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

@Results({@Result(name = "success", location = "/WEB-INF/page/system/organize/postMgr.jsp")})
public class PostAction extends BaseAction {
	private static final long serialVersionUID = 4444381574379692360L;
	
	@Autowired
	private DepartmentManager departmentManager;
	
	@Autowired
	private PersonManager personManager;
	
    /**
     * Ĭ�ϣ������������򿪸�λ����ҳ��
     * 
     * @throws Exception
     */
    public String execute() throws Exception {
		return SUCCESS;
    }
    
    /**
     * ������λ��Ϣ�б�
     * 
     * @throws Exception
     */
    public String list() throws Exception {
		String depLevel = Struts2Utils.getParameter("depLevel");
		
		Department dep = departmentManager.getDepartment(depLevel);
		List<Post> postList = departmentManager.getPosts(dep.getId(), true);

		String jsonStr = JsonHelper.formatObjectToJsonString(postList);
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * ��������ID�ĸ�λ��Ϣ�����߳�ʼ���¸�λ��Ϣ
     * 
     * @throws Exception
     */
    public String get() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		Post post = new Post();
    		
    		if (id > 0)
    			post = departmentManager.getPost(id);
    		
    		List<Post> postList = new ArrayList<Post>();
    		postList.add(post);
    		
    		jsonStr = JsonHelper.combinSuccessJsonString("data", postList);
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
	
    /**
     * ��ӻ����޸ĸ�λ��Ϣ
     * 
     * @throws Exception
     */
    public String save() throws Exception {
    	Post post = new Post();
    	long id = Struts2Utils.getLongParameter("id", 0);
    	String[] changedFields;
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		if (id == 0){
    			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Post.class);
    			Struts2Utils.writeModel(post, changedFields);
    			post = departmentManager.createPost(post);
    		}
    		else{
    			post = departmentManager.get(Post.class, id);
    			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Post.class);
    			Struts2Utils.writeModel(post, changedFields);
    			
    			departmentManager.modifyPost(post);
    		}
    		
    		jsonStr = JsonHelper.combinSuccessJsonString("data", post);
		} catch (DataContentRepeatException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
    	return null;
    }
    
    /**
     * ɾ������ID�ĸ�λ
     * 
     * @throws Exception
     */
    public String remove() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString("id", id);
		
		try {
			departmentManager.removePost(id, false);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
	
    /**
     * ������λ����˳��
     * 
     * @throws Exception
     */
    public String adjustOrder() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		int order = Struts2Utils.getIntParameter("order", 0);
		
		departmentManager.adjustPostOrder(id, order);
		
		return null;
    }
    
    /**
     * ������λ�µ���Ա��Ϣ�б�
     * 
     * @throws Exception
     */
    public String listHavePerson() throws Exception {
		long postId = Struts2Utils.getLongParameter("postId", 0);
		
		List<Person> personList = departmentManager.getPersons(0, postId, true, true);

		String jsonStr = JsonHelper.formatObjectToJsonString(personList);
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * ��Ӹ�λ��������Ա
     * 
     * @throws Exception
     */
    public String addHavePerson() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		Post post = departmentManager.getPost(id);
    		Person person = personManager.getPerson(haveId);
    		departmentManager.addPostHavePerson(post, person);
   			
    		jsonStr = JsonHelper.combinSuccessJsonString("data", haveId);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * ȥ����λ��������Ա
     * 
     * @throws Exception
     */
    public String removeHavePerson() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		Post post = departmentManager.getPost(id);
    		Person person = personManager.getPerson(haveId);
    		departmentManager.removePostHavePerson(post, person);
   			
    		jsonStr = JsonHelper.combinSuccessJsonString("data", haveId);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
	
}
