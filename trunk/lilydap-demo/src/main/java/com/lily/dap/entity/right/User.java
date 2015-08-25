/*
 * package com.lily.dap.model.right;
 * class User
 * 
 * 创建日期 2005-8-2
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
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
 * 这是一个用户登陆Bean，存储用户登陆信息
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "user_member")
@EntityPropertity(label="用户登陆信息")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name="memberType",
	discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("User")
public class User extends BaseEntity implements UserDetails {
    /**
	 * @return 返回 serialVersionUID
	 */
	private static final long serialVersionUID = -7430610962924552046L;

    /**
     * <code>code</code> 登陆用户
     */
	@FieldPropertity(label="用户名", allowModify=false)
    protected String username = "";
    
    /**
     * <code>password</code> 登陆口令
     */
	@FieldPropertity(allowModify=false)
    protected String password = "123456";
    
    /**
     * <code>passwordHint</code> 忘记密码提示
     */
	@FieldPropertity(allowModify=false)
    protected String passwordHint = "";

    /**
     * <code>enable</code> 用户激活标志
     */
	@FieldPropertity(label="激活标志", allowModify=false)
    protected boolean enabled = true;

    /**
     * <code>accountExpired</code> 帐号到期标志
     */
	@FieldPropertity(allowModify=false)
    protected boolean accountExpired = false;
    
    /**
     * <code>accountLocked</code> 帐号锁定标志
     */
	@FieldPropertity(allowModify=false)
    protected boolean accountLocked = false;
    
    /**
     * <code>credentialsExpired</code> 审查到期标志
     */
	@FieldPropertity(allowModify=false)
    protected boolean credentialsExpired = false;
    
    /**
      * <code>roles<code> 登陆用户包含所有角色对象集合（包括因为包含关系而拥有的角色集合）
      */
   @Transient
   @FieldPropertity(allowModify=false)
   protected Set<Role> roles = new HashSet<Role>();
    
    /**
      * <code>permissionMap<code> 登陆用户拥有的所有许可集合（包括因为角色包含关系而拥有的许可集合）
      */
   @Transient
   protected Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();
    
	/**
     * @return 返回 username。
     * 
     */
	@Column(length = 50)
    public String getUsername() {
        return username == null ? "" : username;
    }
    
    /**
     * @param code 要设置的 code。
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return 返回 password。
     * 
     * @hibernate.property
     * @hibernate.column name="password" not-null="true" length="255"
     */
    @Column(length = 255)
    public String getPassword() {
        return password == null ? "" : password;
    }
    
    /**
     * @param pwd 要设置的 password。
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return 返回 passwordHint。
     * @hibernate.property
     * @hibernate.column name="passwordHint" not-null="true" length="255"
     */
    @Column(length = 255)
    public String getPasswordHint() {
        return passwordHint == null ? "" : passwordHint;
    }
    
    /**
     * @param passwordHint 要设置的 passwordHint。
     */
    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
    
    /**
     * @return 返回 enabled。
     * 
     * @hibernate.property column="account_enabled" not-null="true" type="yes_no"
     */
    @Column(name = "account_enabled")
    @Type(type = "yes_no")
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * @param enabled 要设置的 enabled。
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
	 * @return 返回 accountExpired。
     * 
     * @hibernate.property column="account_expired" not-null="true" type="yes_no"
     */
    @Column(name = "account_expired")
    @Type(type = "yes_no")
    public boolean isAccountExpired() {
        return accountExpired;
    }

	/**
	 * @return 返回 accountLocked。
     * 
     * @hibernate.property column="account_locked" not-null="true" type="yes_no"
     */
    @Column(name = "account_locked")
    @Type(type = "yes_no")
    public boolean isAccountLocked() {
        return accountLocked;
    }

	/**
	 * @return 返回 credentialsExpired。
     * 
     * @hibernate.property column="credentials_expired" not-null="true"  type="yes_no"
     */
    @Column(name = "credentials_expired")
    @Type(type = "yes_no")
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

	/**
	 * @param accountExpired 要设置的 accountExpired。
	 */
    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

	/**
	 * @param accountLocked 要设置的 accountLocked。
	 */
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

	/**
	 * @param credentialsExpired 要设置的 credentialsExpired。
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
    * 添加拥有的角色
    * 
    * @param role 要添加的 role
    */
   public void addRole(Role role) {
       roles.add(role);
   }
   
   /**
    * 清除角色集合
	*
	*/
   public void clearRoles() {
	   roles.clear();
   }
   
   /**
    * 添加拥有的许可
    * 
    * @param permission 要添加的 permission
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
    * 返回对给定权限对象操作的许可集合
    * 
    * @param ri_ob_code 许可集合操作的权限对象
    * @return 返回 List
    */
   @Transient
   public List<String> getPermissions(String ri_ob_code) {
       if (!permissionMap.containsKey(ri_ob_code))
           return new ArrayList<String>();
       else
           return permissionMap.get(ri_ob_code);
   }
   
   /**
    * 清除许可集合
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
