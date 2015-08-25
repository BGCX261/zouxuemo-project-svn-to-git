/*
 * package com.lily.dap.model.right;
 * class User
 * 
 * �������� 2005-8-2
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.entity.right;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
/**
 * ����һ���û���½Bean���洢�û���½��Ϣ
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "user_member")
@EntityPropertity(label="�û���½��Ϣ")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name="memberType",
	discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("User")
public class User extends BaseEntity implements UserDetails {
    /**
	 * @return ���� serialVersionUID
	 */
	private static final long serialVersionUID = -7430610962924552046L;

    /**
     * <code>code</code> ��½�û�
     */
	@FieldPropertity(label="�û���", allowModify=false)
    protected String username = "";
    
    /**
     * <code>password</code> ��½����
     */
	@FieldPropertity(allowModify=false)
    protected String password = "123456";
    
    /**
     * <code>passwordHint</code> ����������ʾ
     */
	@FieldPropertity(allowModify=false)
    protected String passwordHint = "";

    /**
     * <code>enable</code> �û������־
     */
	@FieldPropertity(label="�����־", allowModify=false)
    protected boolean enabled = true;

    /**
     * <code>accountExpired</code> �ʺŵ��ڱ�־
     */
	@FieldPropertity(allowModify=false)
    protected boolean accountExpired = false;
    
    /**
     * <code>accountLocked</code> �ʺ�������־
     */
	@FieldPropertity(allowModify=false)
    protected boolean accountLocked = false;
    
    /**
     * <code>credentialsExpired</code> ��鵽�ڱ�־
     */
	@FieldPropertity(allowModify=false)
    protected boolean credentialsExpired = false;
    
    /**
      * <code>roles<code> ��½�û��������н�ɫ���󼯺ϣ�������Ϊ������ϵ��ӵ�еĽ�ɫ���ϣ�
      */
   @Transient
   @FieldPropertity(allowModify=false)
   protected Set<Role> roles = new HashSet<Role>();
    
    /**
      * <code>permissionMap<code> ��½�û�ӵ�е�������ɼ��ϣ�������Ϊ��ɫ������ϵ��ӵ�е���ɼ��ϣ�
      */
   @Transient
   protected Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();
    
	/**
     * @return ���� username��
     * 
     */
	@Column(length = 50)
    public String getUsername() {
        return username == null ? "" : username;
    }
    
    /**
     * @param code Ҫ���õ� code��
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return ���� password��
     * 
     * @hibernate.property
     * @hibernate.column name="password" not-null="true" length="255"
     */
    @Column(length = 255)
    public String getPassword() {
        return password == null ? "" : password;
    }
    
    /**
     * @param pwd Ҫ���õ� password��
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return ���� passwordHint��
     * @hibernate.property
     * @hibernate.column name="passwordHint" not-null="true" length="255"
     */
    @Column(length = 255)
    public String getPasswordHint() {
        return passwordHint == null ? "" : passwordHint;
    }
    
    /**
     * @param passwordHint Ҫ���õ� passwordHint��
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
    
    /**
     * @return ���� enabled��
     * 
     * @hibernate.property column="account_enabled" not-null="true" type="yes_no"
     */
    @Column(name = "account_enabled")
    @Type(type = "yes_no")
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * @param enabled Ҫ���õ� enabled��
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
	 * @return ���� accountExpired��
     * 
     * @hibernate.property column="account_expired" not-null="true" type="yes_no"
     */
    @Column(name = "account_expired")
    @Type(type = "yes_no")
    public boolean isAccountExpired() {
        return accountExpired;
    }

	/**
	 * @return ���� accountLocked��
     * 
     * @hibernate.property column="account_locked" not-null="true" type="yes_no"
     */
    @Column(name = "account_locked")
    @Type(type = "yes_no")
    public boolean isAccountLocked() {
        return accountLocked;
    }

	/**
	 * @return ���� credentialsExpired��
     * 
     * @hibernate.property column="credentials_expired" not-null="true"  type="yes_no"
     */
    @Column(name = "credentials_expired")
    @Type(type = "yes_no")
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

	/**
	 * @param accountExpired Ҫ���õ� accountExpired��
	 */
    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

	/**
	 * @param accountLocked Ҫ���õ� accountLocked��
	 */
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

	/**
	 * @param credentialsExpired Ҫ���õ� credentialsExpired��
	 */
    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

	/**
	 * @return the permissionMap
	 */
	@Transient
	public Map<String, List<String>> getPermissionMap() {
		return permissionMap;
	}

	/**
	 * @param permissionMap the permissionMap to set
	 */
	
	public void setPermissionMap(Map<String, List<String>> permissionMap) {
		this.permissionMap = permissionMap;
	}

	/**
	 * @return the roles
	 */
	@Transient
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	   
   /**
    * ���ӵ�еĽ�ɫ
    * 
    * @param role Ҫ��ӵ� role
    */
   public void addRole(Role role) {
       roles.add(role);
   }
   
   /**
    * �����ɫ����
	*
	*/
   public void clearRoles() {
	   roles.clear();
   }
   
   /**
    * ���ӵ�е����
    * 
    * @param permission Ҫ��ӵ� permission
    */
   public void addPermission(Permission permission) {
       String ri_ob_code = permission.getObjectCode();
       
       List<String> list = null;
       if (!permissionMap.containsKey(ri_ob_code)) {
           list = new ArrayList<String>();
           permissionMap.put(ri_ob_code, list);
       } else
    	   list = permissionMap.get(ri_ob_code);

       String[] operations = StringUtils.split(permission.getHaveOperations(), ',');
       for (String operation : operations) {
    	   if (!list.contains(operation))
    		   list.add(operation);
       }
   }

   /**
    * ���ضԸ���Ȩ�޶����������ɼ���
    * 
    * @param ri_ob_code ��ɼ��ϲ�����Ȩ�޶���
    * @return ���� List
    */
   @Transient
   public List<String> getPermissions(String ri_ob_code) {
       if (!permissionMap.containsKey(ri_ob_code))
           return new ArrayList<String>();
       else
           return permissionMap.get(ri_ob_code);
   }
   
   /**
    * �����ɼ���
	*
	*/
   public void clearPermissions() {
	   permissionMap.clear();
   }
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append(
				"accountExpired", this.accountExpired).append("accountNonExpired",
				this.isAccountNonExpired()).append("credentialsNonExpired",
				this.isCredentialsNonExpired()).append("enabled", this.enabled)
				.append("accountNonLocked", this.isAccountNonLocked()).append(
						"passwordHint", this.passwordHint).append(
						"credentialsExpired", this.credentialsExpired).append(
						"username", this.username).append("password",
						this.password).append("accountLocked",
						this.accountLocked).toString();
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof User)) {
			return false;
		}
		User rhs = (User) object;
		return new EqualsBuilder().append(
				this.password, rhs.password).append(this.passwordHint,
				rhs.passwordHint).append(this.credentialsExpired,
				rhs.credentialsExpired).append(this.enabled, rhs.enabled)
				.append(this.username, rhs.username).append(this.accountExpired,
						rhs.accountExpired)
				.append(this.accountLocked, rhs.accountLocked).append(this.id,
						rhs.id).isEquals();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(631495001, -75461837).append(this.password).append(
				this.passwordHint).append(this.credentialsExpired).append(
				this.enabled).append(this.username).append(this.accountExpired).append(
						this.accountLocked).append(this.id).toHashCode();
	}
	
// ----------------------- UserDetails Implement -----------------------------------
	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetails#getAuthorities()
	 */
   @Transient
   public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
		
		for (GrantedAuthority grantedAuthority : roles)
			grantedAuthoritys.add(grantedAuthority);
		
        return grantedAuthoritys;
	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
	 */
   @Transient
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
	 */
   @Transient
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
	 */
   @Transient
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}
}
