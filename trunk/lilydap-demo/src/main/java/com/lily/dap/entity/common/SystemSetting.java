package com.lily.dap.entity.common;

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
 * ϵͳ���� ��Ϣʵ����
 *
 */
@Entity
@Table(name = "system_setting")
@EntityPropertity(label="ϵͳ����")
public class SystemSetting extends BaseEntity {
	private static final long serialVersionUID = -7312197115775437523L;

	/**
	 * ����ģ��
	 */
	@FieldPropertity(allowCreate=true, allowModify=true)
	private String model;

	/**
	 * ϵͳ��������
	 */
	@FieldPropertity(label="ϵͳ��������", allowCreate=true, allowModify=true)
	private String name;

	/**
	 * ϵͳ����ֵ
	 */
	@FieldPropertity(label="ϵͳ����ֵ", allowCreate=true, allowModify=true)
	private String value;


	@FieldPropertity(label="����")
	private String des;

	/**
	 * @return model
	 */
	@Column(length = 50)
	public String getModel() {
		return model == null ? "" : model;
	}

	/**
	 * @param model Ҫ���õ� model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return name
	 */
	@Column(length = 50)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * @param name Ҫ���õ� name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return value
	 */
	@Column(length = 100)
	public String getValue() {
		return value == null ? "" : value;
	}

	/**
	 * @param value Ҫ���õ� value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Column(length = 100)
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemSetting)) {
			return false;
		}
		SystemSetting rhs = (SystemSetting) object;
		return new EqualsBuilder().append(
				this.value, rhs.value).append(this.model, rhs.model).append(
				this.name, rhs.name).append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1519316423, -272505625).append(this.value).append(this.model).append(
				this.name).append(this.id).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append(
				"model", this.model).append("id", this.id).append("value",
				this.value).toString();
	}

	public SystemSetting(String model, String name, String value) {
		this.model = model;
		this.name = name;
		this.value = value;
	}

	public SystemSetting() {
		
	}
}
