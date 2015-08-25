/*
 * package com.lily.dap.model.right;
 * class RightObject
 * 
 * 创建日期 2005-7-19
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 * 
 * 权限对象Bean,保存权限对象信息
 */
package com.lily.dap.entity.right;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.entity.BaseEntity;
/**
 * 这是一个权限对象Bean，存储权限对象信息
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_object"
 */
@Entity
@Table(name = "right_object")
@EntityPropertity(label="权限")
public class RightObject extends BaseEntity {
    /**
	 * @return 返回 serialVersionUID
	 */
	private static final long serialVersionUID = -3875270164253397919L;

    /**
     * <code>clazz</code> 权限对象分类
     */
    private String clazz = "";
    
    /**
     * <code>code</code> 权限对象代码
     */
    private String code = "";
    
    /**
     * <code>name</code> 权限对象名称
     */
    private String name = "";
    
    /**
     * <code>des</code> 权限对象说明
     */
    private String des = "";
    
    /**
     * @return 返回 clazz。
     * 
     * hibernate.property
     * hibernate.column name="clazz" not-null="true"
     * 
     * @struts.form-field
     */
    @Column(length = 30)
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
		return new ToStringBuilder(this).append("des", this.des).append("name",
				this.name).append("clazz", this.clazz).append("id", this.id)
				.append("code", this.code).toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RightObject)) {
			return false;
		}
		RightObject rhs = (RightObject) object;
		return new EqualsBuilder().append(
				this.code, rhs.code).append(this.clazz, rhs.clazz).append(
				this.name, rhs.name).append(this.id, rhs.id).append(this.des,
				rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1569195567, 16050627).append(this.code).append(this.clazz).append(
				this.name).append(this.id).append(this.des).toHashCode();
	}
}
