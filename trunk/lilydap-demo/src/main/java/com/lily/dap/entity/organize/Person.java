package com.lily.dap.entity.organize;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.common.HandlersTag;
import com.lily.dap.entity.right.RightHold;
import com.lily.dap.entity.right.User;

/**
 * ��Ա��Ϣʵ����
 *
 * @author zouxuemo
 */
@Entity
@DiscriminatorValue("Person")
@EntityPropertity(label="��Ա")
public class Person extends User implements RightHold, HandlersTag {
	private static final long serialVersionUID = 7497209364294946844L;

	/**
	 * ���ʶ
	 */
	public static final String IDENTIFIER = "person";
	
	/**
	 * <code>STATE_NORMAL</code> ��Ա״̬������
	 */
	public static final int STATE_NORMAL = 0;
	
	/**
	 * <code>STATE_OFFLINE</code> ��Ա״̬����ְ
	 */
	public static final int STATE_OFFLINE = 99;
	
	/**
	 * <code>name<code> ��Ա����
	 */
	@FieldPropertity(label="����")
	private String name = "";

	/**
	 * <code>sex<code> �Ա�
	 */
	@FieldPropertity(label="�Ա�")
	private String sex = "";

	/**
	 * <code>birthday<code> ��������
	 */
	@FieldPropertity(label="��������")
	private String birthday = "";

	/**
	 * <code>mobilePhone</code> �ֻ�����
	 */
	@FieldPropertity(label="���ֻ�")
	private String mobile = "";
	
	/**
	 * <code>phone</code> ���õ绰����
	 */
	@FieldPropertity(label="��ϵ�绰")
	private String phone = "";
	
	/**
	 * <code>state</code> ��Ա״̬
	 */
	@FieldPropertity(label="��Ա״̬", allowModify=false)
	private int state = STATE_NORMAL;

	/**
	 * ��ע
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
	 * @return ���� name��
	 * 
	 * hibernate.property
	 * hibernate.column name="name" not-null="true" length="50"
	 * 
	 * @struts.form-field
	 */
	@Column(length = 30)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * @param name
	 *            Ҫ���õ� name��
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return ���� des��
	 * hibernate.property 
	 * hibernate.column name="des" sql-type="text" not-null="true"
     * 
     * @struts.form-field
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des
	 *            Ҫ���õ� des��
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
	 * @return ���� sex��
	 * 
	 * hibernate.property
	 * hibernate.column name="sex" length="8"
	 * 
	 * @struts.form-field
	 */
	@Column(length = 8)
	public String getSex() {
		return sex == null ? "" : sex;
	}

	/**
	 * @param sex
	 *            Ҫ���õ� sex��
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * @return ���� birthDate��
	 * hibernate.property
	 * hibernate.column name="birthDate" not-null="true"
	 * 
	 * @struts.form-field
	 */
	@Column(name = "birthday")
	public String getBirthday() {
		return birthday == null ? "" : birthday;
	}

	/**
	 * @param birthDate
	 *            Ҫ���õ� birthDate��
	 */
	public void setBirthday(String birthDate) {
		this.birthday = birthDate;
	}

	/**
	 * @return the mobilePhone
	 * hibernate.property
	 * hibernate.column name="mobilePhone" not-null="true"
	 * 
	 * @struts.form-field
	 */
	public String getMobile() {
		return mobile == null ? "" : mobile;
	}

	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the phone
	 * hibernate.property
	 * hibernate.column name="phone" not-null="true"
	 * 
	 * @struts.form-field
	 */
	public String getPhone() {
		return phone == null ? "" : phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * ���� state.
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * ���� state ֵΪ <code>state</code>.
	 * @param state Ҫ���õ� state ֵ
	 */
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public Person clone() throws CloneNotSupportedException {
		return (Person) super.clone();
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.common.AddressTag#getSerializable()
	 */
    @Transient
	public Serializable getSerializable() {
		return new Long(id);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.model.common.AddressTag#getAddressType()
	 */
    @Transient
	public String getAddressType() {
		return IDENTIFIER;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		Person myClass = (Person) object;
		return new CompareToBuilder().append(this.birthday, myClass.birthday).append(this.des, myClass.des).append(
				this.phone, myClass.phone).append(this.sex, myClass.sex)
				.append(this.passwordHint, myClass.passwordHint).append(this.state, myClass.state).append(
						this.accountExpired, myClass.accountExpired).append(this.password, myClass.password).append(
						this.permissionMap, myClass.permissionMap).append(this.username, myClass.username).append(this.name,
						myClass.name).append(this.enabled, myClass.enabled).append(this.roleCode, myClass.roleCode).append(
						this.accountLocked, myClass.accountLocked).append(this.roles, myClass.roles).append(this.credentialsExpired,
						myClass.credentialsExpired).append(this.mobile,
						myClass.mobile).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Person)) {
			return false;
		}
		Person rhs = (Person) object;
		return new EqualsBuilder().append(this.birthday, rhs.birthday).append(
				this.des, rhs.des).append(this.phone, rhs.phone).append(this.sex, rhs.sex).append(this.passwordHint,
				rhs.passwordHint).append(this.state, rhs.state).append(this.accountExpired, rhs.accountExpired).append(this.password,
				rhs.password).append(this.permissionMap, rhs.permissionMap).append(this.username, rhs.username).append(this.name, rhs.name)
				.append(this.enabled, rhs.enabled).append(this.roleCode, rhs.roleCode).append(this.accountLocked,
				rhs.accountLocked).append(this.roles, rhs.roles).append(this.credentialsExpired, rhs.credentialsExpired)
				.append(this.mobile, rhs.mobile).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1429109647, 823883615).append(this.birthday).append(
				this.des).append(this.phone).append(this.sex).append(this.passwordHint).append(this.state).append(
				this.accountExpired).append(this.password).append(this.permissionMap).append(this.username)
				.append(this.name).append(this.enabled).append(this.roleCode).append(this.accountLocked)
				.append(this.roles).append(this.credentialsExpired).append(this.mobile).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("state", this.state).append("accountNonExpired", this.isAccountNonExpired())
						.append("serializable", this.getSerializable()).append("accountLocked",
						this.accountLocked).append("phone", this.phone).append("id", this.getId()).append("authorities",
						this.getAuthorities()).append("passwordHint", this.passwordHint).append("privateRoleCode",
						this.getPrivateRoleCode()).append("mobile", this.mobile)
						.append("accountExpired", this.accountExpired).append("password", this.password).append(
						"permissionMap", this.permissionMap).append("enabled", this.enabled)
						.append("roles", this.roles).append("credentialsNonExpired", this.isCredentialsNonExpired()).append(
						"name", this.name).append("credentialsExpired", this.credentialsExpired).append("des", this.des)
						.append("username", this.username).append("birthday", this.birthday).append(
						"accountNonLocked", this.isAccountNonLocked()).append("sex", this.sex).append("roleCode", this.roleCode).toString();
	}
    
}
