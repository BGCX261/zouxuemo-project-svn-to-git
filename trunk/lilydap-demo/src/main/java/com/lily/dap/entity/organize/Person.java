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
 * 人员信息实体类
 *
 * @author zouxuemo
 */
@Entity
@DiscriminatorValue("Person")
@EntityPropertity(label="人员")
public class Person extends User implements RightHold, HandlersTag {
	private static final long serialVersionUID = 7497209364294946844L;

	/**
	 * 类标识
	 */
	public static final String IDENTIFIER = "person";
	
	/**
	 * <code>STATE_NORMAL</code> 人员状态－在线
	 */
	public static final int STATE_NORMAL = 0;
	
	/**
	 * <code>STATE_OFFLINE</code> 人员状态－离职
	 */
	public static final int STATE_OFFLINE = 99;
	
	/**
	 * <code>name<code> 人员姓名
	 */
	@FieldPropertity(label="姓名")
	private String name = "";

	/**
	 * <code>sex<code> 性别
	 */
	@FieldPropertity(label="性别")
	private String sex = "";

	/**
	 * <code>birthday<code> 出生日期
	 */
	@FieldPropertity(label="出生日期")
	private String birthday = "";

	/**
	 * <code>mobilePhone</code> 手机号码
	 */
	@FieldPropertity(label="绑定手机")
	private String mobile = "";
	
	/**
	 * <code>phone</code> 常用电话号码
	 */
	@FieldPropertity(label="联系电话")
	private String phone = "";
	
	/**
	 * <code>state</code> 人员状态
	 */
	@FieldPropertity(label="人员状态", allowModify=false)
	private int state = STATE_NORMAL;

	/**
	 * 备注
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
	 * @return 返回 name。
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
	 *            要设置的 name。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 返回 des。
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
	 *            要设置的 des。
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
	 * @return 返回 sex。
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
	 *            要设置的 sex。
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * @return 返回 birthDate。
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
	 *            要设置的 birthDate。
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
	 * 返回 state.
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * 设置 state 值为 <code>state</code>.
	 * @param state 要设置的 state 值
	 */
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public Person clone() throws CloneNotSupportedException {
		return (Person) super.clone();
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.model.common.AddressTag#getSerializable()
	 */
    @Transient
	public Serializable getSerializable() {
		return new Long(id);
	}

	/* （非 Javadoc）
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
