/**
 * 
 */
package com.lily.dap.service.right.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightObject;
import com.lily.dap.entity.right.RightOperation;
import com.lily.dap.entity.right.Role;
import com.lily.dap.entity.right.RoleRole;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataHaveIncludeException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.DataNotIncludeException;
import com.lily.dap.service.exception.IllegalDataStateException;
import com.lily.dap.service.exception.IllegalHierarchicalException;
import com.lily.dap.service.right.RoleManager;
import com.lily.dap.service.util.VerifyUtil;
import com.lily.dap.util.RandomGUID;

/**
/**
 * <code>RoleManagerImpl</code>
 * <p>角色维护、角色许可维护接口实现</p>
 *
 * @author 邹学模
 */
@Service("roleManager")
public class RoleManagerImpl extends BaseManager implements RoleManager {
    private VerifyUtil verifyUtil;
    
    @PostConstruct
    public void init() {
    	verifyUtil = new VerifyUtil(dao);
    }    
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#addHavePermission(long, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public Permission addHavePermission(Permission permission) throws DataNotExistException {
		getRole(permission.getRoleCode());

		dao.saveOrUpdate(permission);
		return permission;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#addHaveRole(long, long)
	 */
	public void addHaveRole(long id, long have_role_id)
			throws DataNotExistException, DataHaveIncludeException, IllegalHierarchicalException {
		Role role = getRole(id);
		Role have_role = getRole(have_role_id);

		List<Role> childs = getHaveRoles(id, false, true);
		if (childs.contains(have_role))
			throw new DataHaveIncludeException("角色[" + role.getName() + "]已经包含有角色[" + have_role.getName() + "]，不能再次建立包含关系！");

		List<Role> parents = getByHaveRoles(id, Role.FLAG_ALL, false);
		if (parents.contains(have_role))
			throw new IllegalHierarchicalException("角色[" + role.getName() + "]已经被角色[" + have_role.getName() + "]或其包含角色包含，建立包含关系逻辑错误！");
		
		RoleRole rr = new RoleRole(role.getCode(), have_role.getCode());
		dao.saveOrUpdate(rr);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#createPrivateRole(java.lang.String)
	 */
	public Role createPrivateRole(String des) {
		String code, name;

		long count = 0;
		
		//自动生成私有角色的编码和名称，并确保编码和名称不重复
		do {
			code = "priv-code-" + new RandomGUID().toString().substring(0, 10);
			count = dao.count(Role.class, new Condition().eq("code", code));
		} while (count > 0);
		
		do {
			name = "priv-name-" + new RandomGUID().toString().substring(0, 20);
			count = dao.count(Role.class, new Condition().eq("name", name));
		} while (count > 0);
		
		Role role = new Role();
		role.setCode(code);
		role.setName(name);
		role.setDes(des);
		role.setFlag(Role.FLAG_PRIVATE_ROLE);
		dao.saveOrUpdate(role);
		return role;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#createPublicRole(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Role createPublicRole(Role role)
			throws DataContentRepeatException {
		verifyUtil.assertDataNotRepeat(Role.class, "code", role.getCode(), "角色编码重复，请输入新的角色编码值！");
		verifyUtil.assertDataNotRepeat(Role.class, "name", role.getName(), "角色名称重复，请输入新的角色名称值！");
		
		role.setFlag(Role.FLAG_PUBLIC_ROLE);
		dao.saveOrUpdate(role);
		return role;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#getHavePermissions(long, boolean, boolean)
	 */
	public List<Permission> getHavePermissions(long id,
			boolean notIncludeChild, boolean showPermissionFullName) {
		List<Role> roles;
		if (notIncludeChild)
			roles = new ArrayList<Role>();
		else
			roles = getHaveRoles(id, false, notIncludeChild);

		Role me = getRole(id);
		roles.add(0, me);
		
		List<Permission> permissions = new ArrayList<Permission>();
		for (Role role : roles) {
			permissions.addAll(getRolesPermission(role));
		}
		
		if (showPermissionFullName) {
			for (Permission permission : permissions) {
				permission.setFullName(showPermissionFullName(permission));
			}
		}
		
		return permissions;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#getHaveRoles(long, boolean, boolean)
	 */
	public List<Role> getHaveRoles(long id, boolean onlyPublicRole,
			boolean notIncludeChild) {
		Role role = getRole(id);
		
		Set<Role> list = new HashSet<Role>();
		retrieveHaveRolesToList(list, role, notIncludeChild);
		
		List<Role> results = new ArrayList<Role>();
		for (Role r : list) {
			if (!onlyPublicRole || Role.FLAG_PUBLIC_ROLE == r.getFlag())
				results.add(r);
		}
		
		return results;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.right.RoleManager#getByHaveRoles(long, boolean, boolean)
	 */
	public List<Role> getByHaveRoles(long id, int range,
			boolean notIncludeParent) {
		Role role = getRole(id);

		Set<Role> list = new HashSet<Role>();
		retrieveByHaveRolesToList(list, role, notIncludeParent);
		
		List<Role> results = new ArrayList<Role>();
		for (Role r : list) {
			if (range == Role.FLAG_ALL || range == r.getFlag())
				results.add(r);
		}
		
		return results;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#getRole(long)
	 */
	public Role getRole(long id) throws DataNotExistException {
		return get(Role.class, id);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#getRole(java.lang.String)
	 */
	public Role getRole(String code) throws DataNotExistException {
		return get(Role.class, "code", code);
	}

	public List<Role> getRoles(boolean onlyPublicRole) {
		Condition cond = new Condition();
		if (onlyPublicRole)
			cond.eq("flag", Role.FLAG_PUBLIC_ROLE);
		
		return query(Role.class, cond);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#modifyRole(long, java.lang.String, java.lang.String)
	 */
	public void modifyRole(Role role)
			throws DataContentRepeatException, DataNotExistException {
		verifyUtil.assertDataNotRepeat(Role.class, "name", role.getName(), "id", role.getId(), "角色名称重复，请输入新的角色名称值！");

		dao.saveOrUpdate(role);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#removeHavePermission(long, long)
	 */
	public void removeHavePermission(long have_permission_id) throws DataNotExistException {
		Permission permission = get(Permission.class, have_permission_id);
		
		dao.remove(permission);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#removeHaveRole(long, long)
	 */
	public void removeHaveRole(long id, long have_role_id)
			throws DataNotExistException, DataNotIncludeException {
		Role role = getRole(id);
		Role have_role = getRole(have_role_id);
		
		if (0 == removeHaveRoles(role, have_role))
			throw new DataNotIncludeException("角色[" + role.getName() + "]未包含角色[" + have_role.getName() + "]，请先去除角色使用关联后再删除！");
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.RoleManager#removeRole(long)
	 */
	synchronized public void removeRole(long id, boolean forceRemove) throws DataNotExistException,
			DataHaveUsedException, IllegalDataStateException {
		Role role = getRole(id);
		
		if (role.isSystemFlag()) 
			throw new IllegalDataStateException("角色[" + role.getName() + "]为系统角色，不允许删除！");
		
		List<Role> roles = getByHaveRoles(id, Role.FLAG_ALL, true);
		if (roles.size() > 0) {
			if (forceRemove)
				for (Role r : roles) {
					removeHaveRole(r.getId(), id);
				}
			else
				throw new DataHaveUsedException("角色[" + role.getName() + "]已经被使用，不允许删除！");
		}
		
		removeHaveRoles(role, null);
		
		List<Permission> permissions = getRolesPermission(role);
		for (Permission permission : permissions) {
			dao.remove(permission);
		}
		
		dao.remove(role);
	}
	
	/**
	 * 获取角色拥有的角色列表并保存到列表集合中
	 *
	 * @param list
	 * @param role
	 * @param onlyPublicRole
	 * @param notIncludeChild
	 */
	private void retrieveHaveRolesToList(Set<Role> set, Role role, boolean notIncludeChild) {
		Condition cond = new Condition();
		cond.inquery("code", "select fk_have_role from RoleRole where fk_role = '" + role.getCode() + "'");
		
		List<Role> roles = query(Role.class, cond);
		for (Role r : roles) {
			if (!set.contains(r));
			set.add(r);
		}
		
		if (!notIncludeChild) {
			for (Role r : roles) {
				retrieveHaveRolesToList(set, r, notIncludeChild);
			}
		}
	}
	
	/**
	 * 获取包含角色的角色列表并保存到列表集合中
	 *
	 * @param list
	 * @param role
	 * @param onlyPublicRole
	 * @param notIncludeChild
	 */
	private void retrieveByHaveRolesToList(Set<Role> set, Role role, boolean notIncludeParent) {
		Condition cond = new Condition();
		cond.inquery("code", "select fk_role from RoleRole where fk_have_role = '" + role.getCode() + "'");
		
		List<Role> roles = query(Role.class, cond);
		for (Role r : roles) {
			if (!set.contains(r));
			set.add(r);
		}
		
		if (!notIncludeParent) {
			for (Role r : roles) {
				retrieveByHaveRolesToList(set, r, notIncludeParent);
			}
		}
	}

	/**
	 * 返回给定许可的权限设置说明（如：系统对象－读，写）
	 *
	 * @param permission
	 * @return
	 */
	private String showPermissionFullName(Permission permission) {
		String objectCode = permission.getObjectCode();
		String haveOperations = permission.getHaveOperations();
		
		RightObject object = get(RightObject.class, "code", objectCode);
		Object[] haveOperationArray = haveOperations.split(",");
		List<RightOperation> operations = query(RightOperation.class, new Condition().eq("objectCode", objectCode).in("code", haveOperationArray));
		
		StringBuffer buf = new StringBuffer();
		buf.append(object.getName()).append("－");
		boolean first = true;
		for (RightOperation operation : operations) {
			if (first)
				first = false;
			else
				buf.append("，");
			buf.append(operation.getName());
		}
		
		return buf.toString();
	}
	
	/**
	 * 删除给定角色的指定包含角色之间的包含关联，如果包含角色未指定，则删除给定角色下的所有包含关联
	 *
	 * @param role 要删除关联的角色
	 * @param haveRole 要删除关联的被包含角色，如果为null，则删除所有包含关联
	 * @return 删除的关联数量
	 */
	private int removeHaveRoles(Role role, Role haveRole) {
		Condition cond = new Condition().eq("fk_role", role.getCode());
		if (haveRole != null)
			cond.eq("fk_have_role", haveRole.getCode());
		
		List<RoleRole> roleRoles = query(RoleRole.class, cond);

		for (RoleRole rr : roleRoles) {
			dao.remove(rr);
		}
		
		return roleRoles.size();
	}
	
	private List<Permission> getRolesPermission(Role role) {
		return query(Permission.class, new Condition().eq("roleCode", role.getCode()));
	}
}
