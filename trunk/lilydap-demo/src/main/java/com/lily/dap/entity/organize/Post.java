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
import com.lily.dap.entity.common.AddressTag;
import com.lily.dap.entity.right.RightHold;


/**
 * 岗位信息实体类
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "organize_post")
@EntityPropertity(label="岗位")
public class Post extends BaseEntity implements RightHold, AddressTag {
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -741683827609088517L;

	/**
	 * 类标识
	 */
	public static final String IDENTIFIER = "post";

    /**
      * <code>depLevel<code> 岗位所属部门
      */
	@FieldPropertity(allowModify=false)
    private String depLevel = "";
    
	/**
	 * <code>sn</code> 同一个部门下的排序号
	 */
	@FieldPropertity(allowModify=false)
	private int sn = 0;
    
    /**
      * <code>code<code> 岗位编码，作为同部门下岗位排序使用
      */
	@FieldPropertity(label="岗位编码", allowModify=false)
    private String code = "";
    
    /**
      * <code>name<code> 岗位名称
      */
	@FieldPropertity(label="岗位名称")
    private String name = "";
 
    /**
     * <code>clazz<code> 岗位分类－由数据字典中的PostClass维护
     */
	@FieldPropertity(label="岗位分类")
    private String clazz = "";
    
    /**
      * <code>des<code> 备注
      */
	@FieldPropertity(label="备注")
    private String des = "";
    
    /**
      * <code>role_id<code> 私有角色
      */
	@FieldPropertity(allowModify=false)
    private String roleCode = "";
    
    @Transient
    public String getPrivateRoleCode() {
        return roleCode;
    }

    public void setPrivateRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return 返回 depLevel。
     * 
     * hibernate.property
     * hibernate.column name="depLevel" not-null="true"
     * 
     * @struts.form-field
     */
    public String getDepLevel() {
        return depLevel;
    }

    /**
     * @param depLevel 要设置的 depLevel。
     */
    public void setDepLevel(String depLevel) {
        this.depLevel = depLevel;
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
     * @return 返回 clazz。
     */
    @Column
    public String getClazz() {
		return clazz == null ? "" : clazz;
	}

    /**
     * @param clazz 要设置的 clazz。
     */
	public void setClazz(String clazz) {
		this.clazz = clazz;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("des", this.des).append("clazz", this.clazz).append(
						"name", this.name).append("id", this.id).append("sn", this.sn).append(
						"roleCode", this.roleCode).append("code", this.code)
				.append("depLevel", this.depLevel).toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Post)) {
			return false;
		}
		Post rhs = (Post) object;
		return new EqualsBuilder().append(
				this.code, rhs.code).append(this.depLevel, rhs.depLevel).append(this.sn, rhs.sn)
				.append(this.roleCode, rhs.roleCode)
				.append(this.clazz, rhs.clazz).append(this.name, rhs.name)
				.append(this.id, rhs.id).append(this.des, rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1352355593, -2123363197).append(this.code).append(this.depLevel)
				.append(this.sn).append(this.roleCode).append(this.clazz).append(
				this.name).append(this.id).append(this.des).toHashCode();
	}
	
	@Override
	public Post clone() throws CloneNotSupportedException {
		Post clone = (Post)super.clone();
		
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
}
