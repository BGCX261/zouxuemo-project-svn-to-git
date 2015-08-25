/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.security;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.Role;
import com.lily.dap.entity.right.User;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.service.right.RoleManager;

/**
 * <code>PersonDetailServiceHandle</code>
 * <p>人员登录处理</p>
 *
 * @author 邹学模
 * @date 2008-3-31
 */
public class PersonDetailServiceHandle implements DetailServiceHandle {
	public static final String BASE_ROLE_CODE = "guest";
	
	public static final String BASE_ROLE_NAME = "游客";
	
//	public static final String BASE_PERMISSION_RIGHT_OBJECT = "system";
//	
//	public static final String BASE_PERMISSION_RIGHT_OPERATION = "operation";
	
    private RoleManager roleManager;
    
    private PersonManager personManager;

    /**
     * @param roleManager 要设置的 roleManager。
     */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * @param personManager 要设置的 personManager。
     */
    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

	/* （非 Javadoc）
	 * @see com.lily.dap.web.security.DetailServiceHandle#supportUserType()
	 */
	public String supportUserType() {
		return "person";
	}

	/* （非 Javadoc）
     * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username, String rolePrefix)
            throws UsernameNotFoundException, DataAccessException {
        Person person = null;
        try {
			person = personManager.getPerson(username).clone();
			
			person.clearRoles();
			person.clearPermissions();
        } catch (CloneNotSupportedException e) {
        	
        } catch (DataNotExistException e) {
            throw new UsernameNotFoundException("人员 '" + username + "' 未找到...");
        }
        
        if (!person.isEnabled() || Person.STATE_NORMAL != person.getState())
        	throw new UsernameNotFoundException("用户已经被锁定，或者用户已经被停用！");
        
        Role role = roleManager.getRole(person.getPrivateRoleCode());
        
        retrieveRoleAndInsertToUser(person, role.getId(), rolePrefix);
        retrievePermissionAndInsertToUser(person, role.getId());
        
        return person;
    }
    
    protected void retrieveRoleAndInsertToUser(User user, long roleId, String rolePrefix) {
    	//在用户明细中增加一个基本角色，这个角色主要用于在一些不需要权限控制的页面，赋予基本角色。
    	Role r = new Role();
    	r.setCode(rolePrefix + BASE_ROLE_CODE);
    	r.setName(BASE_ROLE_NAME);
    	user.addRole(r);
    	
        List<Role> roles = roleManager.getHaveRoles(roleId, true, false);
        for (Role role :roles) {
        	r = new Role();
        	r.setCode(rolePrefix + role.getCode());
        	r.setName(role.getName());
        	user.addRole(r);
        }
    }
    
    protected void retrievePermissionAndInsertToUser(User user, long roleId) {
//    	//在用户明细中增加一个基本许可，这个许可主要用于在一些不需要权限控制的页面，赋予基本许可。
//    	Permission permission = new Permission();
//    	permission.setRi_ob(BASE_PERMISSION_RIGHT_OBJECT);
//    	permission.setRi_ops(BASE_PERMISSION_RIGHT_OPERATION);
//    	userDetails.addPermission(permission);
    	
        List<Permission> permissions = roleManager.getHavePermissions(roleId, false, false);
        for (Permission permission : permissions)
        	user.addPermission(permission);
    }
}
