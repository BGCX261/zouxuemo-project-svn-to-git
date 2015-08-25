/*
 * package com.lily.dap.service.right.permission;
 * class Permission
 * 
 * 创建日期 2005-8-1
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.entity.right;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
/**
 * 这是一个权限许可Bean，存储权限许可信息
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_permission"
 */
@Entity
@Table(name = "right_permission")
@EntityPropertity(label="权限许可")
public class Permission extends BaseEntity {
    /**
	 * @return 返回 serialVersionUID
	 */
	private static final long serialVersionUID = 400864644111822070L;


    /**
      * <code>roleCode<code> Hibernate使用，实现与角色的一对多关系
      */
    private String roleCode = "";

    /**
     * <code>objectCode</code> 权限对象代码
     */
    private String objectCode = "";

    /**
     * <code>ri_op</code> 权限操作代码列表，中间以","分隔开，类似于"read,write,..."的格式
     */
    private String haveOperations = "";
    
    /**
     * <code>fullName</code> 全名称，显示许可包含的权限对象和权限操作说明，比如：系统对象:读,写。该路径由服务层接口负责解析，并写入到本属性中
     */
    @Transient
	@FieldPropertity(label="包含权限")
    private String fullName = "";

    /**
     * <code>extend_property_id</code> 许可扩展属性
     */
    private long extend_property_id = 0;

    /**
     * <code>positive_flag</code> 正许可标志（如果许可是正许可，为true，否则，为false）
     */
    private boolean positive_flag = true;

    /**
     * <code>des</code> 许可说明
     */
	@FieldPropertity(label="说明")
    private String des = "";

    /**
	 * @return 返回 roleCode。
     * hibernate.property
     * hibernate.column name="roleCode" not-null="true"
     * 
     * @struts.form-field
	 */
    @Column(name = "role_code", length = 20)
	public String getRoleCode() {
		return roleCode == null ? "" : roleCode;
	}

	/**
	 * @param roleCode 要设置的 roleCode。
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
     * @return 返回 objectCode。
     * 
     * hibernate.property 
     * hibernate.column name="object_code" not-null="true" length="20"
     * 
     * @struts.form-field
     */
	@Column(name = "object_code", length = 20)
    public String getObjectCode() {
        return objectCode == null ? "" : objectCode;
    }
    
    /**
     * @param objectCode 要设置的 objectCode。
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }
    
    /**
     * @return 返回 haveOperations。
     * 
     * hibernate.property 
     * hibernate.column name="operation_code" not-null="true" length="255"
     * 
     * @struts.form-field
     */
    @Column(name = "operation_code", length = 255)
    public String getHaveOperations() {
        return haveOperations == null ? "" : haveOperations;
    }
    
    /**
     * @param haveOperations 要设置的 haveOperations。
     */
    public void setHaveOperations(String haveOperations) {
        this.haveOperations = haveOperations;
    }
    
    /**
     * @return
     */
    @Transient
    public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
     * @return 返回 extend_property_id。
     * 
     * hibernate.property
     * hibernate.column name="extend_property" not-null="true"
     * 
     * @struts.form-field
     */
	@Column(name = "extend_property")
    public long getExtend_property_id() {
        return extend_property_id;
    }

    /**
     * @param extend_property_id 要设置的 extend_property_id。
     */
    public void setExtend_property_id(long extend_property_id) {
        this.extend_property_id = extend_property_id;
    }

    /**
     * @return 返回 positive_flag。
     * 
     * hibernate.property
     * hibernate.column name="positive_flag" not-null="true"
     * 
     * @struts.form-field
     */
    public boolean isPositive_flag() {
        return positive_flag;
    }

    /**
     * @param positive_flag 要设置的 positive_flag。
     */
    public void setPositive_flag(boolean positive_flag) {
        this.positive_flag = positive_flag;
    }

    /**
     * @return 返回 des。
     * 
     * hibernate.property
     * hibernate.column name="des" length="255"
     * 
     * @struts.form-field
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
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("positive_flag",
				this.positive_flag).append("des", this.des).append("id",
				this.id).append("roleCode", this.roleCode).append("fullName",
				this.fullName).append("extend_property_id",
				this.extend_property_id).append("haveOperations",
				this.haveOperations).append("objectCode", this.objectCode)
				.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Permission)) {
			return false;
		}
		Permission rhs = (Permission) object;
		return new EqualsBuilder().append(
				this.haveOperations, rhs.haveOperations).append(
				this.positive_flag, rhs.positive_flag).append(this.objectCode,
				rhs.objectCode).append(this.extend_property_id,
				rhs.extend_property_id)
				.append(this.roleCode, rhs.roleCode).append(this.id, rhs.id)
				.append(this.des, rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(291191775, 1759043749).append(this.haveOperations).append(
				this.positive_flag).append(this.objectCode).append(
				this.extend_property_id).append(
				this.roleCode).append(this.id).append(this.des).toHashCode();
	}
}
