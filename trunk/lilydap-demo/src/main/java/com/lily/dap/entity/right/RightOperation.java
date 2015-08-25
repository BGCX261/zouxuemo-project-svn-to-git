/*
 * package com.lily.dap.model.right;
 * class RightOperate
 * 
 * �������� 2005-7-19
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
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
 * ����һ��Ȩ�޲���Bean���洢Ȩ�޲�����Ϣ
 * 
 * @author zouxuemo
 *
 * hibernate.class table="right_operation"
 */
@Entity
@Table(name = "right_operation")
@EntityPropertity(label="Ȩ�޲���")
public class RightOperation extends BaseEntity {
    /**
	 * @return ���� serialVersionUID
	 */
	private static final long serialVersionUID = -760105678257922147L;

    /**
     * <code>object_id</code> ������Ȩ�޶�����
     */
    private String objectCode = "";
    
    /**
     * <code>code</code> Ȩ�޲�������
     */
	@FieldPropertity(label="��������")
    private String code = "";
    
    /**
     * <code>name</code> Ȩ�޲�������
     */
	@FieldPropertity(label="��������")
    private String name = "";
    
//    /**
//     * <code>have_operation</code> Ȩ�޲����������Ӳ��������б��м���","�ָ�����������"read,write,..."�ĸ�ʽ
//     */
//    private String have_operation = "";
    
    /**
     * <code>des</code> Ȩ�޲���˵��
     */
	@FieldPropertity(label="����˵��")
    private String des = "";
    
    /**
     * @return ���� objectCode��
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
     * @param objectCode Ҫ���õ� objectCode��
     */
    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
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

//    /**
//     * @return ���� have_operation��
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
//     * @param have_operation Ҫ���õ� have_operation��
//     */
//    public void setHave_operation(String have_operation) {
//        this.have_operation = have_operation;
//    }
    
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
