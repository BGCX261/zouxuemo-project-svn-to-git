/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
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
 * <p>��Ա��¼����</p>
 *
 * @author ��ѧģ
 * @date 2008-3-31
 */
public class PersonDetailServiceHandle implements DetailServiceHandle {
	public static final String BASE_ROLE_CODE = "guest";
	
	public static final String BASE_ROLE_NAME = "�ο�";
	
//	public static final String BASE_PERMISSION_RIGHT_OBJECT = "system";
//	
//	public static final String BASE_PERMISSION_RIGHT_OPERATION = "operation";
	
    private RoleManager roleManager;
    
    private PersonManager personManager;

    /**
     * @param roleManager Ҫ���õ� roleManager��
     */
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    /**
     * @param personManager Ҫ���õ� personManager��
     */
    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

	/* ���� Javadoc��
	 * @see com.lily.dap.web.security.DetailServiceHandle#supportUserType()
	 */
	public String supportUserType() {
		return "person";
	}

	/* ���� Javadoc��
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
            throw new UsernameNotFoundException("��Ա '" + username + "' δ�ҵ�...");
        }
        
        if (!person.isEnabled() || Person.STATE_NORMAL != person.getState())
        	throw new UsernameNotFoundException("�û��Ѿ��������������û��Ѿ���ͣ�ã�");
        
        Role role = roleManager.getRole(person.getPrivateRoleCode());
        
        retrieveRoleAndInsertToUser(person, role.getId(), rolePrefix);
        retrievePermissionAndInsertToUser(person, role.getId());
        
        return person;
    }
    
    protected void retrieveRoleAndInsertToUser(User user, long roleId, String rolePrefix) {
    	//���û���ϸ������һ��������ɫ�������ɫ��Ҫ������һЩ����ҪȨ�޿��Ƶ�ҳ�棬���������ɫ��
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
//    	//���û���ϸ������һ��������ɣ���������Ҫ������һЩ����ҪȨ�޿��Ƶ�ҳ�棬���������ɡ�
//    	Permission permission = new Permission();
//    	permission.setRi_ob(BASE_PERMISSION_RIGHT_OBJECT);
//    	permission.setRi_ops(BASE_PERMISSION_RIGHT_OPERATION);
//    	userDetails.addPermission(permission);
    	
        List<Permission> permissions = roleManager.getHavePermissions(roleId, false, false);
        for (Permission permission : permissions)
        	user.addPermission(permission);
    }
}
