/*
 * package com.lily.dap.model.organize;
 * class Person
 * 
 * 创建日期 2006-3-3
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.entity.organize;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.entity.common.AddressTag;
import com.lily.dap.entity.right.RightHold;

/**
 * 部门信息实体类
 * 
 * author zouxuemo
 */
@Entity
@Table(name = "organize_department")
@EntityPropertity(label="部门")
public class Department extends BaseEntity implements RightHold, AddressTag {
	/**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 4542759062027587547L;

	/**
	 * 类标识
	 */
	public static final String IDENTIFIER = "department";
    
    public static final int TYPE_ROOT = 1;				//根部门
    public static final int TYPE_SUBDEPARTMENT = 2;		//总队
    public static final int TYPE_SUBDEPARTMENT2 = 3;	//支队
    public static final int TYPE_SUBDEPARTMENT3 = 8;	//企业
    
    public static final String DEFAULT_ROOT_PARENT_LEVEL = "37";

    /**
      * <code>parent_id<code> 父部门id，关联到上级部门的ID，如果是根部门，其ID为0
      */
	@FieldPropertity(allowModify=false)
    private String parentLevel = "";
    
	/**
	 * <code>sn</code> 同一个父部门下的排序号
	 */
	@FieldPropertity(allowModify=false)
	private int sn = 0;
	
    /**
      * <code>code<code> 部门编码，允许重复，并且作为同级部门排序使用
      */
	@FieldPropertity(label="部门编码")
    private String code = "";
    
    /**
      * <code>level<code> 部门层级编码（内部编码，对于同一级别按照大小分配，每个级别三位数字。比如：根部门为：001，根部门下子部门为：001001、001002、...，001001下的子部门为：001001001、001001002、...,通过内部编码可以快速了解组织机构的树深度及检索整个组织机构。内部编码建立后将不能再修改）
      */
	@FieldPropertity(allowModify=false)
    private String level = "";
    
    /**
      * <code>name<code> 部门名称
      */
	@FieldPropertity(label="部门名称")
    private String name = "";
    
    /**
      * <code>type<code> 部门类型，通过类型，能够区分部门类型，比如：总公司、分公司、部门、科室、...
      */
	@FieldPropertity(label="部门类型")
    private int type = TYPE_SUBDEPARTMENT;
    
    /**
     * <code><code>type<code> 部门分类，通过分类，可以查询同一分类的部门，比如：财务部门、生产部门、销售部门、...
     */
	@FieldPropertity(label="部门分类")
    private int clazz = 0;
    
    /**
     * <code><code>createDate<code> 建立日期
     */
	@FieldPropertity(label="建立日期", allowModify=false)
    private Date createDate = new Date(System.currentTimeMillis());
    
    /**
      * <code>des<code> 部门备注
      */
	@FieldPropertity(label="备注")
    private String des = "";
    
    /**
      * <code>role_id<code> 部门关联的私有角色ID
      */
	@FieldPropertity(allowModify=false)
    private String roleCode = "";
    
	/**
     * <code><code> 联系电话
     */
	@FieldPropertity(label="联系电话")
    private String phone = "";

	/**
     * <code><code> 联系电话
     */
	@FieldPropertity(label="是否系统", allowModify=false)
	private boolean systemFlag = false;
	
    @Transient
    public String getPrivateRoleCode() {
        return roleCode == null ? "" : roleCode;
    }

    public void setPrivateRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return 返回 parent_id。
     * 
     * hibernate.property
     * hibernate.column name="parent_id" not-null="true"
     * 
     * @struts.form-field
     */
    public String getParentLevel() {
        return parentLevel;
    }

    /**
     * @param parent_id 要设置的 parent_id。
     */
    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }

    /**
	 * 返回 sn.
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * 设置 sn 值为 <code>sn</code>.
	 * @param sn 要设置的 sn 值
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

	/**
     * @return 返回 level。
     * 
     * hibernate.property
     * hibernate.column name="level" not-null="true" length="255"
     * 
     * @struts.form-field
     */
    @Column(name="level_code", length = 255)
    public String getLevel() {
        return level == null ? "" : level;
    }

    /**
     * @param level 要设置的 level。
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return 返回 code。
     * 
     * hibernate.property
     * hibernate.column name="code" not-null="true" length="20"
     * 
     * @struts.form-field
     */
    @Column(length = 20)
    public String getCode() {
        return code == null ? "" : code;
    }

    /**
     * @param code 要设置的 code。
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 返回 name。
     * 
     * hibernate.property
     * hibernate.column name="name" not-null="true" length="50"
     * 
     * @struts.form-field
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
     * @return 返回 type。
     * 
     * hibernate.property
     * hibernate.column name="type" not-null="true"
     * 
     * @struts.form-field
     */
    @Column
    public int getType() {
        return type;
    }

    /**
     * @param type 要设置的 type。
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
	 * @return clazz
	 */
    @Column
	public int getClazz() {
		return clazz;
	}

	/**
	 * @param clazz 要设置的 clazz
	 */
	public void setClazz(int clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return createDate
	 */
    @Column
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate 要设置的 createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    /**
     * @return 返回 des。
     * 
     * hibernate.property
     * hibernate.column name="des" not-null="true" length="255"
     * 
     * @struts.form-field
     */
    @Column(length = 255)
    public String getDes() {
        return des;
    }

    /**
     * @param des 要设置的 des。
     */
    public void setDes(String des) {
        this.des = des;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Type(type = "yes_no")
	public boolean isSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(boolean systemFlag) {
		this.systemFlag = systemFlag;
	}
	
    /**
	 * 返回 roleCode.
	 * @return roleCode
	 */
	@Column(length = 20)
	public String getRoleCode() {
		return roleCode == null ? "" : roleCode;
	}

	/**
	 * 设置 roleCode 值为 <code>roleCode</code>.
	 * @param roleCode 要设置的 roleCode 值
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	
	@Override
	public Department clone() throws CloneNotSupportedException {
		Department clone = (Department) super.clone();
		
		return clone;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.common.AddressTag#getAddressType()
	 */
    @Transient
	public String getAddressType() {
		return IDENTIFIER;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.common.AddressTag#getSerializable()
	 */
    @Transient
	public Serializable getSerializable() {
		return new Long(id);
	}


	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Department myClass = (Department) object;
		return new CompareToBuilder().append(this.level, myClass.level).append(
				this.id, myClass.id).append(this.code, myClass.code).append(this.name, myClass.name)
				.append(this.type, myClass.type).append(this.sn, myClass.sn).append(
						this.roleCode, myClass.roleCode).append(this.parentLevel,
						myClass.parentLevel).append(this.clazz, myClass.clazz)
				.append(this.createDate, myClass.createDate).append(this.des,
						myClass.des).append(this.phone, myClass.phone).append(
								this.systemFlag, myClass.systemFlag).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Department)) {
			return false;
		}
		Department rhs = (Department) object;
		return new EqualsBuilder().append(this.level, rhs.level).append(
				this.id, rhs.id).append(this.code, rhs.code).append(this.name, rhs.name).append(this.type,
				rhs.type).append(this.sn,
				rhs.sn).append(this.roleCode, rhs.roleCode).append(
				this.parentLevel, rhs.parentLevel).append(this.clazz, rhs.clazz)
				.append(this.createDate, rhs.createDate).append(this.phone, rhs.phone).
				append(this.systemFlag, rhs.systemFlag).append(this.des,
						rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-692054445, 997324791).append(this.level)
				.append(this.id).append(this.code).append(
						this.name).append(this.type)
				.append(this.sn).append(this.roleCode).append(this.parentLevel)
				.append(this.clazz).append(this.createDate).append(this.des)
				.append(this.phone).append(this.systemFlag)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("parent_id", this.parentLevel)
				.append("id", this.id).append("roleCode", this.roleCode)
				.append("type", this.type).append("clazz", this.clazz).append("privateRoleCode",
						this.getPrivateRoleCode()).append("addressType",
						this.getAddressType()).append("serializable",
						this.getSerializable()).append("des", this.des).append(
						"name", this.name).append("sn", this.sn).append("code",
						this.code).append(
						"createDate", this.createDate).append("level",
						this.level).append("phone", this.phone).append("systemFlag",
								this.systemFlag).toString();
	}

}
