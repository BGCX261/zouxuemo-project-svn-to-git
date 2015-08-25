/*
 * package com.lily.dap.web.security;
 * class RightUtil
 * 
 * 创建日期 2006-3-2
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
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
     * 获取到认证对象的登陆信息，如果当前用户未登录，返回null
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
     * 返回当前用户登录IP
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
     * 获得当前登陆用户的人员信息，如果当前用户未登录，返回null
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
	 * 获取当前登陆用户所在岗位列表，如果当前用户未登录，返回null
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
	 * 获取当前登陆用户所在部门列表，如果当前用户未登录，返回null
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
     * 当前用户是否包含指定的许可
     *
     * @param role
     * @return
     */
    public static boolean isCurrUserHaveRole(String role) {
    	UserDetails userDetails = getCurrUserDetails();
    	if (userDetails == null)
    		return false;
    	
    	//TODO 不好的味道，看以后能够怎么调整
    	role = ROLE_PREFIX + role;
    	
    	return isAuthenticationHaveRole(userDetails, role);
    }
    
    /**
     * 获取给定部门或者岗位喜爱，包含指定角色的人员列表
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
     * 判断给定的授权是否允许
     *
     * @param Granted 授权中可以包括角色，也可以包括许可，格式如下：ROLE_guest,PERMISSION_rightobject~rightoperate1#rightoperate2,...
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
     * 判断给定的授权是否允许
     * 
     * @param ifAllGranted 如果所有的授权都包含，则允许
     * @param ifAnyGranted 如果其中满足一个授权，则允许
     * @param ifNotGranted 如果包含其中的授权，则不允许
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
     * 分析传入的许可字符串参数，分解成许可集合
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
     * 检查认证用户拥有给定许可集合的那些许可
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
