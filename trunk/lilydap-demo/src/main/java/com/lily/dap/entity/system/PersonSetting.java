/**
 * 
 */
package com.lily.dap.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.entity.BaseEntity;

/**
 * 个人参数设置
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "person_setting")
@EntityPropertity(label="个人参数")
public class PersonSetting extends BaseEntity {
	public static final String DEFAULT_SETTING_USERNAME = "[default_setting_user]";
	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4098021449505364947L;
	
	private String username = "";
	
	private String name = "";
	
	private String value = "";

	public PersonSetting() {
		
	}

	/**
	 * @return the username
     * 
     * @hibernate.property
     * @hibernate.column name="username" not-null="true" length="30"
	 */
	@Column(length = 30)
	public String getUsername() {
		return username == null ? "" : username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the name
     * 
     * @hibernate.property
     * @hibernate.column name="name" not-null="true" length="30"
	 */
	@Column(length = 50)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
     * 
     * @hibernate.property
     * @hibernate.column name="value" not-null="true" length="2000"
	 */
	@Column(length = 100)
	public String getValue() {
		return value == null ? "" : value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-234257991, -1439710459).append(this.value).append(this.username).append(
				this.name).append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PersonSetting)) {
			return false;
		}
		PersonSetting rhs = (PersonSetting) object;
		return new EqualsBuilder().append(
				this.value, rhs.value).append(this.username, rhs.username).append(
				this.name, rhs.name).append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append(
				"username", this.username).append("id", this.id).append("value",
				this.value).toString();
	}

	public PersonSetting(String username, String name, String value) {
		this.username = username;
		this.name = name;
		this.value = value;
	}
	
}
