package com.lily.dap.web.action.organize;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.ExtTreeData;
import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.DepartmentManager;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.ExtUtils;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.security.RightUtils;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

@Results( {@Result(name = "success", location = "/WEB-INF/page/system/organize/departmentMgr.jsp")})
public class DepartmentAction extends BaseAction {

	private static final long serialVersionUID = 4767256347563217590L;

	@Autowired
	private DepartmentManager departmentManager;
	
    /**
     * 默认（不带参数）打开部门管理页面
     * 
     * @throws Exception
     */
    public String execute() throws Exception {
		return SUCCESS;
    }
    
    /**
     * 提供部门－岗位－人员树JSON数据
     * 
     * @throws Exception
     */
    public String tree() throws Exception {
		String scope = Struts2Utils.getParameter("scope");
		
		boolean dialogFlag = Struts2Utils.getBooleanParameter("dialog", false);
		
		/*
		 * mode提供以下四种模式
		 * 	dep：只生成部门树
		 * 	dep-post：生成部门－岗位树
		 * 	dep-person：生成部门－人员树
		 * 	dep-post-person：生成部门－岗位－人员树
		 */
		String mode = Struts2Utils.getParameter("mode");
		long rootId = 0;
		
		Department department = null;
		
		/*
		 * 如果scope是currentDepartment则只列出登录用户所以部门为根的下级部门岗位人员列表。
		 */
		if(scope.equals("currentDepartment")){
			if(RightUtils.getCurrPersonsDepartments().size() > 0){
				rootId = RightUtils.getCurrPersonsDepartments().get(0).getId();
			}
		}
		
		if (rootId == 0) {
			department = departmentManager.getRootDepartment();
			rootId = department.getId();
		} else {
			department = departmentManager.getDepartment(rootId);
		}
		
		ExtTreeData treeData = new DepartmentTreeHelper(dialogFlag).retrieveDepartmentTree(departmentManager, department, mode);
		String jsonStr = "[" + ExtUtils.buildJSONString(treeData) + "]";
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 检索部门信息列表
     * 
     * @throws Exception
     */
    public String list() throws Exception {
		long parentId = Struts2Utils.getLongParameter("parentId", 0);
		
		if (parentId == 0) {
			Department department = departmentManager.getRootDepartment();
			parentId = department.getId();
		}
		
		List<Department> departmentList = departmentManager.getChilds(parentId, true);
		
		String jsonStr = JsonHelper.formatObjectToJsonString(departmentList);
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 列出给定ID的部门往上部门的层级关系表
     * 
     * @throws Exception
     */
    public String hierarchical() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
		List<Department> hierarchicals = new ArrayList<Department>();

		String jsonStr = null;
		if (id == 0) {
			hierarchicals.add(departmentManager.getRootDepartment());
		} else {
			try {
				Department department = departmentManager.getDepartment(id);
				while (!departmentManager.isRootDepartment(department)) {
					hierarchicals.add(0, department);
					
					department = departmentManager.getDepartment(department.getParentLevel());
				}
				 
				hierarchicals.add(0, department);
			} catch (DataNotExistException e) {
				jsonStr = JsonHelper.combinFailJsonString(e);
			}
		}
		
		if (hierarchicals.size() > 0) {
			jsonStr = JsonHelper.combinSuccessJsonString("hierarchical", hierarchicals);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
    
    /**
     * 检索给定ID的部门信息，或者初始化新部门信息
     * 
     * @throws Exception
     */
    public String get() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString();
    	try {
    		Department department = new Department();
    		
    		if (id > 0)
    			department = departmentManager.getDepartment(id);
    		
    		List<Department> dpList = new ArrayList<Department>();
    		dpList.add(department);
    		
    		jsonStr = JsonHelper.combinSuccessJsonString("data", dpList);
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
	
    /**
     * 添加或者修改部门信息
     * 
     * @throws Exception
     */
    public String save() throws Exception {
    	String jsonStr;
    	try {
    		Department department = null;
    		
         	long id = Struts2Utils.getLongParameter("id", 0);
         	String[] changedFields;
    		if (id == 0){
    			department = new Department();
    			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Department.class);
    			Struts2Utils.writeModel(department, changedFields);
    			department = departmentManager.createDepartment(department);
    		} else {
    			department = departmentManager.get(Department.class, id);
    			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Department.class);
    			Struts2Utils.writeModel(department, changedFields);
    			departmentManager.modifyDepartment(department);
    		}
    		
    		jsonStr = JsonHelper.combinSuccessJsonString("data", department);
		} catch (DataContentRepeatException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
    	return null;
    }
    
    /**
     * 删除给定ID的部门
     * 
     * @throws Exception
     */
    public String remove() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		
    	String jsonStr = JsonHelper.combinSuccessJsonString("id", id);
		
		try {
			departmentManager.removeDepartment(id, false);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}
		
		Struts2Utils.renderJson(jsonStr);
   		return null;
    }
	
    /**
     * 调整部门排列顺序
     * 
     * @throws Exception
     */
    public String adjustOrder() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		int order = Struts2Utils.getIntParameter("order", 0);
		
		departmentManager.adjustDepartmentOrder(id, order);
		
		return null;
    }
}

class DepartmentTreeHelper {
	private int index = 0;
	
	private boolean rootFlag = true;
	
	private boolean dialog;
	
	public DepartmentTreeHelper(boolean dialog) {
		this.dialog = dialog;
	}
	
	public void resetRootFlag() {
		rootFlag = true;
	}
	
	public ExtTreeData retrieveDepartmentTree(DepartmentManager departmentManager, Department department, String mode) {
		ExtTreeData root = buildDepData(department);
		
		if (rootFlag) {
			root.setData("expanded", true);
			rootFlag = false;
		}
		
		List<Department> departmentList = departmentManager.getChilds(department.getId(), true);
		for (Department dep : departmentList) {
			ExtTreeData data = retrieveDepartmentTree(departmentManager, dep, mode);
			
			root.addChildren(data);
		}
		
		if (mode.indexOf("post") >= 0) {
			List<Post> postList = departmentManager.getPosts(department.getId(), true);
			for (Post post : postList) {
				ExtTreeData data = retrievePostTree(departmentManager, post, mode);
				
				root.addChildren(data);
			}
		} else if (mode.indexOf("person") >= 0) {
			List<Person> personList = departmentManager.getPersons(department.getId(), 0, true, true);
			for (Person person : personList) {
				ExtTreeData data = buildPersontData(person);
				
				root.addChildren(data);
			}
		}
		
		if (root.getChildren() != null && root.getChildren().size() > 0) {
			root.setData("leaf", false);
		}
		
		return root;
	}
	
	private ExtTreeData retrievePostTree(DepartmentManager departmentManager, Post post, String mode) {
		ExtTreeData root = buildPostData(post);
		
		if (mode.indexOf("person") >= 0) {
			List<Person> personList = departmentManager.getPersons(0, post.getId(), true, true);
			for (Person person : personList) {
				ExtTreeData data = buildPersontData(person);
				
				root.addChildren(data);
			}
		}
		
		if (root.getChildren() != null && root.getChildren().size() > 0) {
			root.setData("leaf", false);
		}
		
		return root;
	}
	
	private ExtTreeData buildDepData(Department department) {
		ExtTreeData data = new ExtTreeData();
		if (dialog) {
			data.setData("id", Department.IDENTIFIER + index++);
			
			data.setData("level", department.getLevel());
			data.setData("text", department.getName());
			data.setData("iconCls", "tree-department");
			data.setData("leaf", true);
		} else {
			data.setData("type", Department.IDENTIFIER);
			data.setData("id", Department.IDENTIFIER + department.getId());
			data.setData("_id", department.getId());
			data.setData("code", department.getCode());
			data.setData("level", department.getLevel());
			data.setData("text", department.getName());
			data.setData("qtip", department.getDes());
			data.setData("iconCls", "tree-department");
			data.setData("leaf", true);
		}
		
		return data;
	}
	
	private ExtTreeData buildPostData(Post post) {
		ExtTreeData data = new ExtTreeData();
		if (dialog) {
			data.setData("id", Post.IDENTIFIER + index++);
			
			data.setData("code", post.getCode());
			data.setData("text", post.getName());
			data.setData("iconCls", "tree-post");
			data.setData("leaf", true);
		} else {
			data.setData("type", Post.IDENTIFIER);
			data.setData("id", Post.IDENTIFIER + post.getId());
			data.setData("_id", post.getId());
			data.setData("code", post.getCode());
			data.setData("text", post.getName());
			data.setData("qtip", post.getDes());
			data.setData("iconCls", "tree-post");
			data.setData("leaf", true);
		}
		
		return data;
	}
	
	private ExtTreeData buildPersontData(Person person) {
		ExtTreeData data = new ExtTreeData();
		if (dialog) {
			data.setData("id", Post.IDENTIFIER + index++);
			
			data.setData("text", person.getName());
			data.setData("iconCls", "tree-person");
			data.setData("leaf", true);
		} else {
			data.setData("type", Person.IDENTIFIER);
			data.setData("id", Person.IDENTIFIER + index++);	//因为可能多岗一人，所以用人员ID就不合适了
			data.setData("_id", person.getId());
			data.setData("code", person.getUsername());
			data.setData("text", person.getName());
			data.setData("qtip", person.getDes());
			data.setData("iconCls", "tree-person");
			data.setData("leaf", true);
		}
		
		return data;
	}	
}
