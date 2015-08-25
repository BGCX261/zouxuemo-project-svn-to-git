/*
 * package com.lily.dap.model.right;
 * class Role
 * 
 * 创建日期 2005-8-2
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.entity.right;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
/**
 * 这是一个用户角色Bean，存储用户角色信息
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_role"
 */
@Entity
@Table(name = "right_role")
@EntityPropertity(label="用户角色")
public class Role extends BaseEntity implements Serializable, GrantedAuthority {
    /**
	 * @return 返回 serialVersionUID
	 */
	private static final long serialVersionUID = -3142435304221896694L;
	
	/**
	  * <code>FLAG_ALL<code> 所有角色
	  */
	public static final int FLAG_ALL = -1; 
	
	/**
	  * <code>FLAG_PUBLIC_ROLE<code> 公共角色
	  */
	public static final int FLAG_PUBLIC_ROLE = 0; 
	
	/**
	  * <code>FLAG_PRIVATE_ROLE<code> 私有角色
	  */
	public static final int FLAG_PRIVATE_ROLE = 99;
	
    /**
     * <code>code</code> 角色编号
     */
	@FieldPropertity(label="角色编码", allowModify=false)
    private String code = "";
    
    /**
     * <code>name</code> 角色名称
     */
	@FieldPropertity(label="角色名称")
    private String name = "";
    
    /**
      * <code>flag<code> 标志角色是公共角色（0），还是私有角色（99）
      */
	@FieldPropertity(allowModify=false)
    private int flag = 0;
    
    /**
     * <code>systemFlag</code> 是否系统角色，系统角色不允许删除（这个标识只有在手工插入系统角色的时候可以设置，正常通过界面创建的角色都是非系统角色）
     */
	@FieldPropertity(allowModify=false)
    private boolean systemFlag = false;
    
    /**
      * <code>roles<code> Hibernate字段，角色包含的角色集合
      */
    @Transient
	@FieldPropertity(allowModify=false)
    private List<Role> roles = new ArrayList<Role>();
    
    /**
      * <code>permissions<code> Hibernate字段，角色包含的许可集合
      */
    @Transient
	@FieldPropertity(allowModify=false)
    private List<Permission> permissions = new ArrayList<Permission>();
    
    /**
     * <code>des</code> 角色说明
     */
	@FieldPropertity(label="角色说明")
    private String des = "";
    
    @Transient
    public String getAuthority() {
        return getCode();
    }
    
    /**
	 * @return the code
     * 
     * hibernate.property
     * hibernate.column name="code" not-null="true" length="20"
	 */
    @Column(length = 20)
	public String getCode() {
		return code == null ? "" : code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
     * @return 返回 name。
     * 
     * hibernate.property
     * hibernate.column name="name" not-null="true" length="50"
     */
	@Column(length = 50)
    public String getName() {
        return name == null ? "" : name;
    }
    
    /**
     * @param name 要设置的 name。
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
	 * @return 返回 flag。
	 * hibernate.property
     * hibernate.column name="flag" not-null="true"
	 */
	public int getFlag() {
		return flag;
	}

	 /**
	 * 返回 systemFlag.
	 * @return systemFlag
	 */
    @Type(type = "yes_no")
	public boolean isSystemFlag() {
		return systemFlag;
	}

	/**
	 * 设置 systemFlag 值为 <code>systemFlag</code>.
	 * @param systemFlag 要设置的 systemFlag 值
	 */
	public void setSystemFlag(boolean systemFlag) {
		this.systemFlag = systemFlag;
	}

	/**
	 * @return 返回 roles。
	 */
	@Transient
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * return 返回 permissions。
     * hibernate.set table="right_permission" cascade="delete" inverse="true" lazy="false"
     * hibernate.key column="role_id" 
     * hibernate.one-to-many class="com.lily.dap.model.right.Permission" 
	 */
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Permission.class)
//	@JoinColumn(name = "role_id")
	@Transient
	public List<Permission> getPermissions() {
		return permissions;
	}
    /**
     * @return 返回 des。
     * 
     * hibernate.property
     * hibernate.column name="des" length="255"
     */
	@Column(length = 255, nullable = true)
    public String getDes() {
        return des;
    }
    
    /**
     * @param des 要设置的 des。
     */
    public void setDes(String des) {
        this.des = des;
    }

	/**
	 * @param roles 要设置的 roles。
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param flag 要设置的 flag。
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @param permissions 要设置的 permissions。
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("des", this.des).append("name",
				this.name).append("id", this.id).append("authority",
				this.getAuthority()).append("roles", this.roles).append(
				"permissions", this.permissions).append("code", this.code)
				.append("flag", this.flag).toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Role)) {
			return false;
		}
		Role rhs = (Role) object;
		return new EqualsBuilder().append(
				this.code, rhs.code)
				.append(this.flag, rhs.flag)
				.append(this.name, rhs.name).append(this.id, rhs.id).append(
						this.des, rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-896717107, 1456554303).append(this.code)
				.append(this.flag).append(this.name).append(
						this.id).append(this.des).toHashCode();
	}
}
