package com.lily.dap.web.action.right;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.ExtTreeData;
import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightObject;
import com.lily.dap.entity.right.RightOperation;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.right.PermissionManager;
import com.lily.dap.service.right.RoleManager;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.ExtUtils;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

/**
 * @author zouxuemo
 *
 * 实现对Role对象进行CRUD操作的Action类
 */
@Results({@Result(name = "success", location = "/WEB-INF/page/system/right/roleMgr.jsp")})
public class RoleAction extends BaseAction {
	private static final long serialVersionUID = -8084973905799148679L;

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private PermissionManager permissionManager;
	
	/**
	 * 默认（不带参数）打开人员管理页面
	 * 
	 * @throws Exception
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
    
	/**
	 * 检索角色信息列表
	 * 
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Role> roleList = roleManager.getRoles(true);

		String jsonStr = JsonHelper.formatObjectToJsonString(roleList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 检索给定ID的角色信息，或者初始化新角色信息
	 * 
	 * @throws Exception
	 */
	public String get() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);

		String jsonStr;
		try {
			Role role = new Role();

			if (id > 0)
				role = roleManager.getRole(id);

			List<Role> roleList = new ArrayList<Role>();
			roleList.add(role);
			
			jsonStr = JsonHelper.combinSuccessJsonString("data", roleList);
		} catch (DataNotExistException e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
	
	/**
	 * 添加或者修改角色信息
	 * 
	 * @throws Exception
	 */
	public String save() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);

		Role role;
		String[] changedFields;
		boolean isCreate = true;
		if (id > 0) {
			role = roleManager.getRole(id);

			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Role.class);

			isCreate = false;
		} else {
			try {
				role = new Role();
			} catch (Exception e) {
				throw new RuntimeException("不能创建实体类[模块]，请检查实体类是否定义默认构造函数、或者默认构造函数是否为公共可访问！", e);
			}

			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Role.class);
		}

		Struts2Utils.writeModel(role, changedFields);

		String jsonStr;
		try {
			if (isCreate) {
				role = roleManager.createPublicRole(role);
			} else {
				roleManager.modifyRole(role);
			}
			
			jsonStr = JsonHelper.combinSuccessJsonString("data", role);
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

		String jsonStr;
		try {
			roleManager.removeRole(id, true);
			
			jsonStr = JsonHelper.combinSuccessJsonString("id", id);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 提供角色选择信息列表数据
	 * 
	 * @throws Exception
	 */
	public String select() throws Exception {
		long filterId = Struts2Utils.getLongParameter("filterId", 0);

		List<Role> roleList = roleManager.getRoles(true);
		if (filterId > 0) {
			List<Role> filterRoleList = roleManager.getHaveRoles(filterId, true, true);
			filterRoleList.add(roleManager.getRole(filterId));

			for (Role role : filterRoleList) {
				if (roleList.contains(role))
					roleList.remove(role);
			}
		}
		
		String jsonStr = JsonHelper.formatObjectToJsonString(roleList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
    /**
     * 检索角色包含角色信息列表
     * 
     * @throws Exception
     */
	public String listHaveRoles() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);

		List<Role> roleList = roleManager.getHaveRoles(id, true, true);
		
		String jsonStr = JsonHelper.formatObjectToJsonString(roleList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
    /**
     * 添加角色包含角色
     * 
     * @throws Exception
     */
	public String addHaveRole() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);

		String jsonStr;
		try {
			roleManager.addHaveRole(id, haveId);

			Role haveRole = roleManager.getRole(haveId);
			
			jsonStr = JsonHelper.combinSuccessJsonString("data", haveRole);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 去除角色包含角色
	 * 
	 * @throws Exception
	 */
	public String removeHaveRole() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);

		String jsonStr;
		try {
			roleManager.removeHaveRole(id, haveId);

			jsonStr = JsonHelper.combinSuccessJsonString("data", haveId);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 检索角色包含许可信息列表
	 * 
	 * @throws Exception
	 */
	public String listHavePermissions() throws Exception {
		long id = Struts2Utils.getLongParameter("id", 0);

		List<Permission> permissionList = roleManager.getHavePermissions(id, true, true);

		String jsonStr = JsonHelper.formatObjectToJsonString(permissionList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 添加角色包含许可
	 * 
	 * @throws Exception
	 */
	public String addHavePermission() throws Exception {
		Permission permission;
		String[] changedFields;
		try {
			permission = new Permission();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体类[模块]，请检查实体类是否定义默认构造函数、或者默认构造函数是否为公共可访问！", e);
		}

		changedFields = BaseEntityHelper.getEntityAllowCreateFields(Permission.class);
		Struts2Utils.writeModel(permission, changedFields);

		String jsonStr;
		try {
			permission = roleManager.addHavePermission(permission);
			
			jsonStr = JsonHelper.combinSuccessJsonString("data", permission);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 去除角色包含许可
	 * 
	 * @throws Exception
	 */
	public String removeHavePermission() throws Exception {
		long havePermissionId = Struts2Utils.getLongParameter("havePermissionId", 0);

		String jsonStr;
		try {
			roleManager.removeHavePermission(havePermissionId);

			jsonStr = JsonHelper.combinSuccessJsonString("data", havePermissionId);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
	
	/**
	 * 返回权限对象树JSON数据
	 * 
	 * @throws Exception
	 */
	public String listRightObject() throws Exception {
		List<String> rightObjectClasss = permissionManager.getRightObjectClasss();

		List<ExtTreeData> treeList = new ArrayList<ExtTreeData>();
		int index = 1;
		for (String classify : rightObjectClasss) {
			List<RightObject> rightObjectList = permissionManager.getRightObjects(classify);
			if (rightObjectList.size() == 0)
				continue;

			ExtTreeData classifyData = new ExtTreeData();
			treeList.add(classifyData);

			classifyData.setData("type", "classify");
			classifyData.setData("id", "classify_" + index++);
			classifyData.setData("code", classify);
			classifyData.setData("text", classify);
			classifyData.setData("qtip", classify);
			classifyData.setData("leaf", false);
			classifyData.setData("cls", "folder");
			classifyData.setData("iconCls", "tree-rightclassify");

			for (RightObject rightObject : rightObjectList) {
				ExtTreeData rightObjectData = new ExtTreeData();
				classifyData.addChildren(rightObjectData);

				rightObjectData.setData("type", "object");
				rightObjectData.setData("id", "catalog" + rightObject.getId());
				rightObjectData.setData("code", rightObject.getCode());
				rightObjectData.setData("text", rightObject.getName());
				rightObjectData.setData("qtip", rightObject.getDes());
				rightObjectData.setData("leaf", true);
				rightObjectData.setData("cls", "file");
				rightObjectData.setData("iconCls", "tree-rightobject");
			}
		}

		String jsonStr = ExtUtils.buildJSONString(treeList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
    
	/**
	 * 检索对象可用操作列表
	 * 
	 * @throws Exception
	 */
	public String listRightOperations() throws Exception {
		String objectCode = Struts2Utils.getParameter("objectCode");

		List<RightOperation> operationList = permissionManager.getRightOperations(objectCode);

		String jsonStr = JsonHelper.formatObjectToJsonString(operationList);
		Struts2Utils.renderJson(jsonStr);
		return null;
	}
}
