package com.lily.dap.entity.dictionary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.entity.BaseEntity;

/**
 * 字典使用情况实体类
 * 
 */
@Entity
@Table(name = "dict_access")
public class DictAccess extends BaseEntity {
	private static final long serialVersionUID = -3773264529273581527L;

	/**
	 * 使用的字典目录编码
	 */
	private String catalogCode = "";
	
	/**
	 * 关联实体类全类名（例如：com.lily.dap.model.Log）
	 */
	private String accessModelName = "";
	
	/**
	 * 关联实体类属性名（例如：operation）
	 */
	private String accessFieldName = "";

	/**
	 * @return
	 */
    @Column(length = 50)
	public String getCatalogCode() {
		return catalogCode == null ? "" : catalogCode;
	}

	/**
	 * @param catalogCode
	 */
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	/**
	 * @return
	 */
    @Column(length = 100)
	public String getAccessModelName() {
		return accessModelName == null ? "" : accessModelName;
	}

	/**
	 * @param accessModelName
	 */
	public void setAccessModelName(String accessModelName) {
		this.accessModelName = accessModelName;
	}

	/**
	 * @return
	 */
    @Column(length = 50)
	public String getAccessFieldName() {
		return accessFieldName == null ? "" : accessFieldName;
	}

	/**
	 * @param accessFieldName
	 */
	public void setAccessFieldName(String accessFieldName) {
		this.accessFieldName = accessFieldName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("catalogCode",
				catalogCode).append("accessModelName", accessModelName).append("accessFieldName", accessFieldName)
				.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(final Object other) {
		if (!(other instanceof DictAccess))
			return false;
		DictAccess castOther = (DictAccess) other;
		return new EqualsBuilder().append(catalogCode, castOther.catalogCode).append(accessModelName, castOther.accessModelName).append(accessFieldName, castOther.accessFieldName).isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(catalogCode).append(accessModelName).append(accessFieldName).toHashCode();
	}
}
