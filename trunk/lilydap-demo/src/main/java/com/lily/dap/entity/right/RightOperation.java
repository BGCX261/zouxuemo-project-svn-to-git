/*
 * package com.lily.dap.model.right;
 * class RightOperate
 * 
 * 创建日期 2005-7-19
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.entity.right;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
/**
 * 这是一个权限操作Bean，存储权限操作信息
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_operation"
 */
@Entity
@Table(name = "right_operation")
@EntityPropertity(label="权限操作")
public class RightOperation extends BaseEntity {
    /**
	 * @return 返回 serialVersionUID
	 */
	private static final long serialVersionUID = -760105678257922147L;

    /**
     * <code>object_id</code> 所属的权限对象编号
     */
    private String objectCode = "";
    
    /**
     * <code>code</code> 权限操作代码
     */
	@FieldPropertity(label="操作编码")
    private String code = "";
    
    /**
     * <code>name</code> 权限操作名称
     */
	@FieldPropertity(label="操作名称")
    private String name = "";
    
//    /**
//     * <code>have_operation</code> 权限操作包含的子操作代码列表，中间以","分隔开，类似于"read,write,..."的格式
//     */
//    private String have_operation = "";
    
    /**
     * <code>des</code> 权限操作说明
     */
	@FieldPropertity(label="操作说明")
    private String des = "";
    
    /**
     * @return 返回 objectCode。
     * 
     * hibernate.property
     * hibernate.column name="objectCode" not-null="true" length="20"
     * 
     * @struts.form-field
     */
    @Column(length = 20)
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

//    /**
//     * @return 返回 have_operation。
//     * 
//     * hibernate.property
//     * hibernate.column name="have_operation" length="255" not-null="true"
//     * 
//     * @struts.form-field
//     */
//    @Column(length = 255)
//    public String getHave_operation() {
//        return have_operation;
//    }
//    
//    /**
//     * @param have_operation 要设置的 have_operation。
//     */
//    public void setHave_operation(String have_operation) {
//        this.have_operation = have_operation;
//    }
    
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
				this.name).append("id", this.id).append("code", this.code)
				.append("objectCode", this.objectCode).toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RightOperation)) {
			return false;
		}
		RightOperation rhs = (RightOperation) object;
		return new EqualsBuilder().append(
				this.code, rhs.code).append(this.objectCode, rhs.objectCode)
				.append(this.name, rhs.name).append(this.id, rhs.id).append(
						this.des, rhs.des).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1176576357, 547408091).append(this.code).append(this.objectCode)
				.append(this.name).append(this.id).append(this.des)
				.toHashCode();
	}
}
