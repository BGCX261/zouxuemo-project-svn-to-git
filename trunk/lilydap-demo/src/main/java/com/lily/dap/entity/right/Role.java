/*
 * package com.lily.dap.model.right;
 * class Role
 * 
 * �������� 2005-8-2
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
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
 * ����һ���û���ɫBean���洢�û���ɫ��Ϣ
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_role"
 */
@Entity
@Table(name = "right_role")
@EntityPropertity(label="�û���ɫ")
public class Role extends BaseEntity implements Serializable, GrantedAuthority {
    /**
	 * @return ���� serialVersionUID
	 */
	private static final long serialVersionUID = -3142435304221896694L;
	
	/**
	  * <code>FLAG_ALL<code> ���н�ɫ
	  */
	public static final int FLAG_ALL = -1; 
	
	/**
	  * <code>FLAG_PUBLIC_ROLE<code> ������ɫ
	  */
	public static final int FLAG_PUBLIC_ROLE = 0; 
	
	/**
	  * <code>FLAG_PRIVATE_ROLE<code> ˽�н�ɫ
	  */
	public static final int FLAG_PRIVATE_ROLE = 99;
	
    /**
     * <code>code</code> ��ɫ���
     */
	@FieldPropertity(label="��ɫ����", allowModify=false)
    private String code = "";
    
    /**
     * <code>name</code> ��ɫ����
     */
	@FieldPropertity(label="��ɫ����")
    private String name = "";
    
    /**
      * <code>flag<code> ��־��ɫ�ǹ�����ɫ��0��������˽�н�ɫ��99��
      */
	@FieldPropertity(allowModify=false)
    private int flag = 0;
    
    /**
     * <code>systemFlag</code> �Ƿ�ϵͳ��ɫ��ϵͳ��ɫ������ɾ���������ʶֻ�����ֹ�����ϵͳ��ɫ��ʱ��������ã�����ͨ�����洴���Ľ�ɫ���Ƿ�ϵͳ��ɫ��
     */
	@FieldPropertity(allowModify=false)
    private boolean systemFlag = false;
    
    /**
      * <code>roles<code> Hibernate�ֶΣ���ɫ�����Ľ�ɫ����
      */
    @Transient
	@FieldPropertity(allowModify=false)
    private List<Role> roles = new ArrayList<Role>();
    
    /**
      * <code>permissions<code> Hibernate�ֶΣ���ɫ��������ɼ���
      */
    @Transient
	@FieldPropertity(allowModify=false)
    private List<Permission> permissions = new ArrayList<Permission>();
    
    /**
     * <code>des</code> ��ɫ˵��
     */
	@FieldPropertity(label="��ɫ˵��")
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
     * @return ���� name��
     * 
     * hibernate.property
     * hibernate.column name="name" not-null="true" length="50"
     */
	@Column(length = 50)
    public String getName() {
        return name == null ? "" : name;
    }
    
    /**
     * @param name Ҫ���õ� name��
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
	 * @return ���� flag��
	 * hibernate.property
     * hibernate.column name="flag" not-null="true"
	 */
	public int getFlag() {
		return flag;
	}

	 /**
	 * ���� systemFlag.
	 * @return systemFlag
	 */
    @Type(type = "yes_no")
	public boolean isSystemFlag() {
		return systemFlag;
	}

	/**
	 * ���� systemFlag ֵΪ <code>systemFlag</code>.
	 * @param systemFlag Ҫ���õ� systemFlag ֵ
	 */
	public void setSystemFlag(boolean systemFlag) {
		this.systemFlag = systemFlag;
	}

	/**
	 * @return ���� roles��
	 */
	@Transient
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * return ���� permissions��
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
     * @return ���� des��
     * 
     * hibernate.property
     * hibernate.column name="des" length="255"
     */
	@Column(length = 255, nullable = true)
    public String getDes() {
        return des;
    }
    
    /**
     * @param des Ҫ���õ� des��
     */
    public void setDes(String des) {
        this.des = des;
    }

	/**
	 * @param roles Ҫ���õ� roles��
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param flag Ҫ���õ� flag��
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @param permissions Ҫ���õ� permissions��
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
