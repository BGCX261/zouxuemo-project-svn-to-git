/*
 * package com.lily.dap.service.right.permission;
 * class Permission
 * 
 * �������� 2005-8-1
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
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
 * ����һ��Ȩ�����Bean���洢Ȩ�������Ϣ
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_permission"
 */
@Entity
@Table(name = "right_permission")
@EntityPropertity(label="Ȩ�����")
public class Permission extends BaseEntity {
    /**
	 * @return ���� serialVersionUID
	 */
	private static final long serialVersionUID = 400864644111822070L;


    /**
      * <code>roleCode<code> Hibernateʹ�ã�ʵ�����ɫ��һ�Զ��ϵ
      */
    private String roleCode = "";

    /**
     * <code>objectCode</code> Ȩ�޶������
     */
    private String objectCode = "";

    /**
     * <code>ri_op</code> Ȩ�޲��������б��м���","�ָ�����������"read,write,..."�ĸ�ʽ
     */
    private String haveOperations = "";
    
    /**
     * <code>fullName</code> ȫ���ƣ���ʾ��ɰ�����Ȩ�޶����Ȩ�޲���˵�������磺ϵͳ����:��,д����·���ɷ����ӿڸ����������д�뵽��������
     */
    @Transient
	@FieldPropertity(label="����Ȩ��")
    private String fullName = "";

    /**
     * <code>extend_property_id</code> �����չ����
     */
    private long extend_property_id = 0;

    /**
     * <code>positive_flag</code> ����ɱ�־��������������ɣ�Ϊtrue������Ϊfalse��
     */
    private boolean positive_flag = true;

    /**
     * <code>des</code> ���˵��
     */
	@FieldPropertity(label="˵��")
    private String des = "";

    /**
	 * @return ���� roleCode��
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
	 * @param roleCode Ҫ���õ� roleCode��
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
     * @return ���� objectCode��
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
     * @param objectCode Ҫ���õ� objectCode��
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }
    
    /**
     * @return ���� haveOperations��
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
     * @param haveOperations Ҫ���õ� haveOperations��
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
     * @return ���� extend_property_id��
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
     * @param extend_property_id Ҫ���õ� extend_property_id��
     */
    public void setExtend_property_id(long extend_property_id) {
        this.extend_property_id = extend_property_id;
    }

    /**
     * @return ���� positive_flag��
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
     * @param positive_flag Ҫ���õ� positive_flag��
     */
    public void setPositive_flag(boolean positive_flag) {
        this.positive_flag = positive_flag;
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
