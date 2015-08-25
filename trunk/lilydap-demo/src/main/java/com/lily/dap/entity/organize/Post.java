/*
 * package com.lily.dap.model.organize;
 * class Person
 * 
 * �������� 2006-3-3
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
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
 * ��λ��Ϣʵ����
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "organize_post")
@EntityPropertity(label="��λ")
public class Post extends BaseEntity implements RightHold, AddressTag {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -741683827609088517L;

	/**
	 * ���ʶ
	 */
	public static final String IDENTIFIER = "post";

    /**
      * <code>depLevel<code> ��λ��������
      */
	@FieldPropertity(allowModify=false)
    private String depLevel = "";
    
	/**
	 * <code>sn</code> ͬһ�������µ������
	 */
	@FieldPropertity(allowModify=false)
	private int sn = 0;
    
    /**
      * <code>code<code> ��λ���룬��Ϊͬ�����¸�λ����ʹ��
      */
	@FieldPropertity(label="��λ����", allowModify=false)
    private String code = "";
    
    /**
      * <code>name<code> ��λ����
      */
	@FieldPropertity(label="��λ����")
    private String name = "";
 
    /**
     * <code>clazz<code> ��λ���࣭�������ֵ��е�PostClassά��
     */
	@FieldPropertity(label="��λ����")
    private String clazz = "";
    
    /**
      * <code>des<code> ��ע
      */
	@FieldPropertity(label="��ע")
    private String des = "";
    
    /**
      * <code>role_id<code> ˽�н�ɫ
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
     * @return ���� depLevel��
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
     * @param depLevel Ҫ���õ� depLevel��
     */
    public void setDepLevel(String depLevel) {
        this.depLevel = depLevel;
    }

    /**
	 * ���� sn.
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * ���� sn ֵΪ <code>sn</code>.
	 * @param sn Ҫ���õ� sn ֵ
	 */
	public void setSn(int sn) {
		this.sn = sn;
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
     * @return ���� clazz��
     */
    @Column
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
     * @return ���� des��
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
     * @param des Ҫ���õ� des��
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
	 * ���� roleCode.
	 * @return roleCode
	 */
	@Column(length = 20)
	public String getRoleCode() {
		return roleCode == null ? "" : roleCode;
	}

	/**
	 * ���� roleCode ֵΪ <code>roleCode</code>.
	 * @param roleCode Ҫ���õ� roleCode ֵ
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

	/* ���� Javadoc��
	 * @see com.lily.dap.model.common.AddressTag#getAddressType()
	 */
    @Transient
	public String getAddressType() {
		return IDENTIFIER;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.common.AddressTag#getSerializable()
	 */
    @Transient
	public Serializable getSerializable() {
		return new Long(id);
	}
}
