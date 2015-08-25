/*
 * package com.lily.dap.model.right;
 * class RightObject
 * 
 * �������� 2005-7-19
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 * 
 * Ȩ�޶���Bean,����Ȩ�޶�����Ϣ
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
 * ����һ��Ȩ�޶���Bean���洢Ȩ�޶�����Ϣ
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_object"
 */
@Entity
@Table(name = "right_object")
@EntityPropertity(label="Ȩ��")
public class RightObject extends BaseEntity {
    /**
	 * @return ���� serialVersionUID
	 */
	private static final long serialVersionUID = -3875270164253397919L;

    /**
     * <code>clazz</code> Ȩ�޶������
     */
    private String clazz = "";
    
    /**
     * <code>code</code> Ȩ�޶������
     */
    private String code = "";
    
    /**
     * <code>name</code> Ȩ�޶�������
     */
    private String name = "";
    
    /**
     * <code>des</code> Ȩ�޶���˵��
     */
    private String des = "";
    
    /**
     * @return ���� clazz��
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
     * @param clazz Ҫ���õ� clazz��
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    
    /**
     * @return ���� code��
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
     * @param code Ҫ���õ� code��
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return ���� name��
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
     * @param name Ҫ���õ� name��
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ���� des��
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
     * @param des Ҫ���õ� des��
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
