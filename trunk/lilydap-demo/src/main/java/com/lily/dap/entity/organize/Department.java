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
 * ������Ϣʵ����
 * 
 * author zouxuemo
 */
@Entity
@Table(name = "organize_department")
@EntityPropertity(label="����")
public class Department extends BaseEntity implements RightHold, AddressTag {
	/**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 4542759062027587547L;

	/**
	 * ���ʶ
	 */
	public static final String IDENTIFIER = "department";
    
    public static final int TYPE_ROOT = 1;				//������
    public static final int TYPE_SUBDEPARTMENT = 2;		//�ܶ�
    public static final int TYPE_SUBDEPARTMENT2 = 3;	//֧��
    public static final int TYPE_SUBDEPARTMENT3 = 8;	//��ҵ
    
    public static final String DEFAULT_ROOT_PARENT_LEVEL = "37";

    /**
      * <code>parent_id<code> ������id���������ϼ����ŵ�ID������Ǹ����ţ���IDΪ0
      */
	@FieldPropertity(allowModify=false)
    private String parentLevel = "";
    
	/**
	 * <code>sn</code> ͬһ���������µ������
	 */
	@FieldPropertity(allowModify=false)
	private int sn = 0;
	
    /**
      * <code>code<code> ���ű��룬�����ظ���������Ϊͬ����������ʹ��
      */
	@FieldPropertity(label="���ű���")
    private String code = "";
    
    /**
      * <code>level<code> ���Ų㼶���루�ڲ����룬����ͬһ�����մ�С���䣬ÿ��������λ���֡����磺������Ϊ��001�����������Ӳ���Ϊ��001001��001002��...��001001�µ��Ӳ���Ϊ��001001001��001001002��...,ͨ���ڲ�������Կ����˽���֯����������ȼ�����������֯�������ڲ����뽨���󽫲������޸ģ�
      */
	@FieldPropertity(allowModify=false)
    private String level = "";
    
    /**
      * <code>name<code> ��������
      */
	@FieldPropertity(label="��������")
    private String name = "";
    
    /**
      * <code>type<code> �������ͣ�ͨ�����ͣ��ܹ����ֲ������ͣ����磺�ܹ�˾���ֹ�˾�����š����ҡ�...
      */
	@FieldPropertity(label="��������")
    private int type = TYPE_SUBDEPARTMENT;
    
    /**
     * <code><code>type<code> ���ŷ��࣬ͨ�����࣬���Բ�ѯͬһ����Ĳ��ţ����磺�����š��������š����۲��š�...
     */
	@FieldPropertity(label="���ŷ���")
    private int clazz = 0;
    
    /**
     * <code><code>createDate<code> ��������
     */
	@FieldPropertity(label="��������", allowModify=false)
    private Date createDate = new Date(System.currentTimeMillis());
    
    /**
      * <code>des<code> ���ű�ע
      */
	@FieldPropertity(label="��ע")
    private String des = "";
    
    /**
      * <code>role_id<code> ���Ź�����˽�н�ɫID
      */
	@FieldPropertity(allowModify=false)
    private String roleCode = "";
    
	/**
     * <code><code> ��ϵ�绰
     */
	@FieldPropertity(label="��ϵ�绰")
    private String phone = "";

	/**
     * <code><code> ��ϵ�绰
     */
	@FieldPropertity(label="�Ƿ�ϵͳ", allowModify=false)
	private boolean systemFlag = false;
	
    @Transient
    public String getPrivateRoleCode() {
        return roleCode == null ? "" : roleCode;
    }

    public void setPrivateRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return ���� parent_id��
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
     * @param parent_id Ҫ���õ� parent_id��
     */
    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
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
     * @return ���� level��
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
     * @param level Ҫ���õ� level��
     */
    public void setLevel(String level) {
        this.level = level;
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
     * @return ���� type��
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
     * @param type Ҫ���õ� type��
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
	 * @param clazz Ҫ���õ� clazz
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
	 * @param createDate Ҫ���õ� createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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


	
	@Override
	public Department clone() throws CloneNotSupportedException {
		Department clone = (Department) super.clone();
		
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
