/*
 * package com.lily.dap.web.security;
 * class RightUtil
 * 
 * �������� 2006-3-2
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.right.User;
import com.lily.dap.service.SpringContextHolder;
import com.lily.dap.service.organize.OrganizeCacheManager;
import com.lily.dap.service.organize.PersonManager;

/**
 * @author zouxuemo
 *
 */
public class RightUtils {
	public static final String ROLE_PREFIX = "ROLE_";
	public static final String PERMISSION_PREFIX = "PERMISSION_";
	
    private static final String rightObjectSplit = "~";

    private static final String rightOperationSplit = "#";
    
    /**
     * ��ȡ����֤����ĵ�½��Ϣ�������ǰ�û�δ��¼������null
     * 
     * @return
     */
    public static  UserDetails getCurrUserDetails() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (null == currentUser)
            return null;
        
        UserDetails userDetails = (UserDetails)currentUser.getPrincipal();
        return userDetails;
    }
    
    /**
     * ���ص�ǰ�û���¼IP
     *
     * @return
     */
    public static String getCurrUserIP() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication)
            return null;
        
		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails))
			return null;
		
		return ((WebAuthenticationDetails) details).getRemoteAddress(); 
    }
    
    /**
     * ��õ�ǰ��½�û�����Ա��Ϣ�������ǰ�û�δ��¼������null
     * 
     * @return
     */
    public static Person getCurrPerson() {
		UserDetails userDetails = getCurrUserDetails();
		
		if (userDetails instanceof Person)
			return (Person)userDetails;
		else
			return null;
	}
    
	/**
	 * ��ȡ��ǰ��½�û����ڸ�λ�б������ǰ�û�δ��¼������null
	 * 
	 * @return
	 */
	public static List<Post> getCurrPersonsPosts() {
		Person person = getCurrPerson();
		if (person == null)
			return null;
		
		OrganizeCacheManager organizeCacheManager = SpringContextHolder.getBean("organizeCacheManager");
		return organizeCacheManager.getPostsByPerson(person.getUsername());
	}
    
	/**
	 * ��ȡ��ǰ��½�û����ڲ����б������ǰ�û�δ��¼������null
	 * 
	 * @return
	 */
	public static List<Department> getCurrPersonsDepartments() {
		Person person = getCurrPerson();
		if (person == null)
			return null;
		
		OrganizeCacheManager organizeCacheManager = SpringContextHolder.getBean("organizeCacheManager");
		return organizeCacheManager.getDepartmentsByPerson(person.getUsername());
	}
    
    /**
     * ��ǰ�û��Ƿ����ָ�������
     *
     * @param role
     * @return
     */
    public static boolean isCurrUserHaveRole(String role) {
    	UserDetails userDetails = getCurrUserDetails();
    	if (userDetails == null)
    		return false;
    	
    	//TODO ���õ�ζ�������Ժ��ܹ���ô����
    	role = ROLE_PREFIX + role;
    	
    	return isAuthenticationHaveRole(userDetails, role);
    }
    
    /**
     * ��ȡ�������Ż��߸�λϲ��������ָ����ɫ����Ա�б�
     *
     * @param depart_id
     * @param post_id
     * @param haveRole
     * @return
     */
    public static List<Person> getHaveRolesPersons(long depart_id, long post_id, String haveRole) {
    	PersonManager personManager = SpringContextHolder.getBean("personManager");
    	
    	return personManager.getPersons(depart_id, post_id, haveRole, true);
    }
    
    /**
     * �жϸ�������Ȩ�Ƿ�����
     *
     * @param Granted ��Ȩ�п��԰�����ɫ��Ҳ���԰�����ɣ���ʽ���£�ROLE_guest,PERMISSION_rightobject~rightoperate1#rightoperate2,...
     * @return
     */
    public static boolean rbacAuthorize(String Granted) {
        UserDetails userDetails = getCurrUserDetails();
        if (userDetails == null)
            return false;
        
        if ((null == Granted) || "".equals(Granted))
        	return true;
        
    	String[] grants = Granted.split(",");
    	
    	for (String grant : grants) {
    		grant = grant.trim();
    		
    		if (grant.indexOf(ROLE_PREFIX) == 0) {
    			if (isAuthenticationHaveRole(userDetails, grant))
    				return true;
    		} else if (grant.indexOf(PERMISSION_PREFIX) == 0) {
    			String permission = grant.substring(PERMISSION_PREFIX.length());
    			
                if (isAuthenticationHavePermission(userDetails, permission))
                    return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * �жϸ�������Ȩ�Ƿ�����
     * 
     * @param ifAllGranted ������е���Ȩ��������������
     * @param ifAnyGranted �����������һ����Ȩ��������
     * @param ifNotGranted ����������е���Ȩ��������
     * @return
     */
    public static boolean rbacAuthorize(String ifNotGranted, String ifAllGranted, String ifAnyGranted) {
        if (((null == ifAllGranted) || "".equals(ifAllGranted))
                && ((null == ifAnyGranted) || "".equals(ifAnyGranted))
                && ((null == ifNotGranted) || "".equals(ifNotGranted))) {
                return false;
            }
        
        UserDetails userDetails = getCurrUserDetails();
        if (userDetails == null)
            return false;
        
        if ((null != ifNotGranted) && !"".equals(ifNotGranted)) {
            Set<String> grantedCopy = containPermission(userDetails, parsePermissionsString(ifNotGranted));
            if (!grantedCopy.isEmpty()) {
                return false;
            }
        }
        
        if ((null != ifAllGranted) && !"".equals(ifAllGranted)) {
            Set<String> allPermission = parsePermissionsString(ifAllGranted);
            Set<String> grantedPermission = containPermission(userDetails, allPermission);
            if (grantedPermission.size() < allPermission.size())
                return false;
        }

       if ((null != ifAnyGranted) && !"".equals(ifAnyGranted)) {
            Set<String> grantedCopy = containPermission(userDetails, parsePermissionsString(ifAnyGranted));
            if (grantedCopy.isEmpty()) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * �������������ַ����������ֽ����ɼ���
     * 
     * @param permissionsString
     * @return
     */
    public static Set<String> parsePermissionsString(String permissionsString) {
        final Set<String> requiredPermission = new HashSet<String>();
        final String[] permissions = StringUtils.commaDelimitedListToStringArray(permissionsString);

        for (int i = 0; i < permissions.length; i++) {
         // Remove the permission's whitespace characters without depending on JDK 1.4+ 
         // Includes space, tab, new line, carriage return and form feed. 
         String permission = StringUtils.replace(permissions[i], " ", ""); 
         permission = StringUtils.replace(permission, "\t", ""); 
         permission = StringUtils.replace(permission, "\r", ""); 
         permission = StringUtils.replace(permission, "\n", ""); 
         permission = StringUtils.replace(permission, "\f", ""); 

         requiredPermission.add(permission);
        }

        return requiredPermission;
    }
    
    /**
     * �����֤�û�ӵ�и�����ɼ��ϵ���Щ���
     * 
     * @param userDetails
     * @param grantedSet
     * @return
     */
    public static Set<String> containPermission(UserDetails userDetails, Set<String> grantedSet) {
        Set<String> returnSet = new HashSet<String>(); 
        
        for (String permission : grantedSet) {
            if (isAuthenticationHavePermission(userDetails, permission))
                returnSet.add(permission);
        }
        
        return returnSet;
    }
    
    public static boolean isAuthenticationHaveRole(UserDetails userDetails, String role) {
    	Collection<GrantedAuthority> roles = userDetails.getAuthorities();
    	for (GrantedAuthority r : roles) {
    		if (r.getAuthority().equals(role))
    			return true;
    	}
    		
    	return false;
    }
    
    public static boolean isAuthenticationHavePermission(UserDetails userDetails, String permission) {
        PermissionStruct struct = parsePermissionString(permission);
        if (struct == null)
            return false;
        
        return isHavePermission(userDetails, struct);
    }
    
    public static PermissionStruct parsePermissionString(String permission) {
        String items[] = permission.split(rightObjectSplit);
        if (items != null && items.length > 1) {
            PermissionStruct struts = new PermissionStruct();
            struts.ri_ob_code = items[0];
            
            struts.ri_op_codes = items[1].split(rightOperationSplit);
            
            return struts;
        }
        
        return null;
    }
    
    public static boolean isHavePermission(UserDetails userDetails, PermissionStruct permissionStruct) {
    	if (!(userDetails instanceof User))
    		return false;
    	
    	User user = (User)userDetails;
    	
        List<String> operations = user.getPermissions(permissionStruct.ri_ob_code);
        if (operations.isEmpty())
            return false;
        
        for (String ri_op_code : permissionStruct.ri_op_codes)
        	if (operations.contains(ri_op_code))
                return true;
        
        return false;
    }
}
