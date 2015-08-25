/*
 * package com.lily.dap.web.security;
 * class PermissionVoter
 * 
 * �������� 2006-2-27
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.web.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ������ɽ�����Ȩ�жϵ�ͶƱ��ʵ��<br>
 * ��Ҫ�󣺱���ʹ��RbacUserDetailService��Ϊ�û���֤���û���½��Ϣ��ȡ�����࣬����ͶƱ�߽����ܻ�ȡRbacUserDetails��ȡ�û���ɽ���ͶƱ��
 * 
 * @author zouxuemo
 *
 */
public class PermissionVoter implements AccessDecisionVoter {
    //~ Instance fields ========================================================

    private String permissionPrefix = "PERMISSION_";
    
    //~ Methods ================================================================

    /**
     * Allows the default permission prefix of <code>PERMISSION_</code> to be overriden.
     * May be set to an empty value, although this is usually not desireable.
     *
     * @param permissionPrefix the new prefix
     */
    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix;
    }

    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null)
            && attribute.getAttribute().startsWith(getPermissionPrefix())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This implementation supports any type of class, because it does not
     * query the presented secure object.
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
	public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        Iterator<ConfigAttribute> iter = attributes.iterator();

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails))
        	return ACCESS_ABSTAIN;
        
        UserDetails userDetails = (UserDetails)principal;
        
        while (iter.hasNext()) {
            ConfigAttribute attribute = iter.next();

            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                String permission = attribute.getAttribute().substring(getPermissionPrefix().length());
                if (RightUtils.isAuthenticationHavePermission(userDetails, permission))
                    return ACCESS_GRANTED;
            }
        }

        return result;
    }
}
