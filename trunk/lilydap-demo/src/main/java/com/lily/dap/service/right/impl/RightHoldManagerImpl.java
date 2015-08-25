/**
 * 
 */
package com.lily.dap.service.right.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightHold;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.right.RightHoldManager;
import com.lily.dap.service.right.RoleManager;

/**
/**
 * <code>RightHoldManagerImpl</code>
 * <p>权限拥有者角色设置、许可设置管理接口实现</p>
 *
 * @author 邹学模
 * @date 2008-3-16
 */
@Service("rightHoldManager")
public class RightHoldManagerImpl extends BaseManager implements RightHoldManager {
	@Autowired
	private RoleManager roleManager;

	private Map<String, String> rightHoldTypeMap = new HashMap<String, String>();
	{
		rightHoldTypeMap.put(Person.IDENTIFIER, Person.class.getName());
		rightHoldTypeMap.put(Post.IDENTIFIER, Post.class.getName());
		rightHoldTypeMap.put(Department.IDENTIFIER, Department.class.getName());
	}
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#getRightHold(java.lang.String, long)
	 */
	@SuppressWarnings("unchecked")
	public RightHold getRightHold(String type, long id) throws DataNotExistException {
		if (!rightHoldTypeMap.containsKey(type))
			throw new DataNotExistException("权限操作对象数据未找到：给定的类型[" + type + "]不识别");
		
		String className = rightHoldTypeMap.get(type);
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {}
		
		return (RightHold)get(clazz, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#addHaveHold(com.lily.dap.entity.right.RightHold, com.lily.dap.entity.right.RightHold)
	 */
	public void addHaveHold(RightHold hold, RightHold haveHold) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		Role haveRole = roleManager.getRole(haveHold.getPrivateRoleCode());
		
		roleManager.addHaveRole(role.getId(), haveRole.getId());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#addHavePermission(com.lily.dap.entity.right.RightHold, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public Permission addHavePermission(RightHold hold, String objectCode,
			String haveOperations, String des) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		Permission permission = new Permission();
		permission.setRoleCode(role.getCode());
		permission.setObjectCode(objectCode);
		permission.setHaveOperations(haveOperations);
		permission.setDes(des);

		permission = roleManager.addHavePermission(permission);
		return permission;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#addHaveRole(com.lily.dap.entity.right.RightHold, com.lily.dap.entity.right.Role)
	 */
	public void addHaveRole(RightHold hold, Role haveRole) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		roleManager.addHaveRole(role.getId(), haveRole.getId());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#listHoldsPermissions(com.lily.dap.entity.right.RightHold)
	 */
	public List<Permission> listHoldsPermissions(RightHold hold) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		return roleManager.getHavePermissions(role.getId(), true, true);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#listHoldsRoles(com.lily.dap.entity.right.RightHold)
	 */
	public List<Role> listHoldsRoles(RightHold hold) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		return roleManager.getHaveRoles(role.getId(), true, true);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#providerHoldsRole(com.lily.dap.entity.right.RightHold)
	 */
	public void providerHoldsRole(RightHold hold) {
		String des = hold.getClass().getName() + "[name:" + hold.getName() + "]'s private role";
		Role role = roleManager.createPrivateRole(des);
		
		hold.setPrivateRoleCode(role.getCode());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#removeHaveHold(com.lily.dap.entity.right.RightHold, com.lily.dap.entity.right.RightHold)
	 */
	public void removeHaveHold(RightHold hold,
			RightHold haveHold) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		Role haveRole = roleManager.getRole(haveHold.getPrivateRoleCode());
		
		roleManager.removeHaveRole(role.getId(), haveRole.getId());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#removeHavePermission(com.lily.dap.entity.right.RightHold, com.lily.dap.entity.right.Permission)
	 */
	public void removeHavePermission(RightHold hold,
			Permission permission) {
		roleManager.removeHavePermission(permission.getId());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#removeHaveRole(com.lily.dap.entity.right.RightHold, com.lily.dap.entity.right.Role)
	 */
	public void removeHaveRole(RightHold hold, Role haveRole) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		roleManager.removeHaveRole(role.getId(), haveRole.getId());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RightHoldManager#removeHoldsRoles(com.lily.dap.entity.right.RightHold)
	 */
	public void removeHoldsRoles(RightHold hold) {
		Role role = roleManager.getRole(hold.getPrivateRoleCode());
		
		roleManager.removeRole(role.getId(), true);
		hold.setPrivateRoleCode("");
	}
}
