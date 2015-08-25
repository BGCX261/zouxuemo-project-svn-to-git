package com.lily.dap.web.action.right;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightHold;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.right.PermissionManager;
import com.lily.dap.service.right.RightHoldManager;
import com.lily.dap.service.right.RoleManager;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

/**
 * @author zouxuemo
 *
 * 对权限操作对象进行权限操作的Action
 */
@Results({@Result(name = "success", location = "/WEB-INF/page/system/right/rightHoldMgr.jsp")})
public class RightHoldAction extends BaseAction {
	private static final long serialVersionUID = 3566599159208701480L;

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private PermissionManager permissionManager;
	
	@Autowired
	private RightHoldManager rightHoldManager;
	
	/**
	 * 默认（不带参数）打开人员管理页面
	 * 
	 * @throws Exception
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}
    
	/**
	 * 检索角色包含角色信息列表
	 * 
	 * @throws Exception
	 */
	public String listHaveRoles() throws Exception {
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);

		RightHold rightHold = rightHoldManager.getRightHold(type, id);
		List<Role> roleList = rightHoldManager.listHoldsRoles(rightHold);

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
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);

		String jsonStr;
		try {
			RightHold rightHold = rightHoldManager.getRightHold(type, id);
			Role haveRole = roleManager.getRole(haveId);

			rightHoldManager.addHaveRole(rightHold, haveRole);

			jsonStr = JsonHelper.combinSuccessJsonString("data", haveId);
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
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);
		long haveId = Struts2Utils.getLongParameter("haveId", 0);

		String jsonStr;
		try {
			RightHold rightHold = rightHoldManager.getRightHold(type, id);
			Role haveRole = roleManager.getRole(haveId);

			rightHoldManager.removeHaveRole(rightHold, haveRole);

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
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);

		RightHold rightHold = rightHoldManager.getRightHold(type, id);
		List<Permission> permissionList = rightHoldManager.listHoldsPermissions(rightHold);

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
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);
		String objectCode = Struts2Utils.getParameter("objectCode");
		String haveOperations = Struts2Utils.getParameter("haveOperations");

		String jsonStr;
		try {
			RightHold rightHold = rightHoldManager.getRightHold(type, id);
			Permission permission = rightHoldManager.addHavePermission(rightHold, objectCode, haveOperations, "");

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
		String type = Struts2Utils.getParameter("type");
		long id = Struts2Utils.getLongParameter("id", 0);
		long havePermissionId = Struts2Utils.getLongParameter("havePermissionId", 0);

		String jsonStr;
		try {
			RightHold rightHold = rightHoldManager.getRightHold(type, id);
			Permission permission = permissionManager.getPermission(havePermissionId);

			rightHoldManager.removeHavePermission(rightHold, permission);

			jsonStr = JsonHelper.combinSuccessJsonString("data", havePermissionId);
		} catch (Exception e) {
			jsonStr = JsonHelper.combinFailJsonString(e);
		}

		Struts2Utils.renderJson(jsonStr);
		return null;
	}
}
