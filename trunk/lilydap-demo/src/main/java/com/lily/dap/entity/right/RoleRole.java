package com.lily.dap.entity.right;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.entity.BaseEntity;

/**
 * RoleRole.java
 * 
 * <br>���ߣ� ����
 *
 * <br>���ڣ� 2006-3-21
 *
 * <br>��Ȩ���У��Ͳ��ٺ����
 * hibernate.class table="right_role_role"
 */
@Entity
@Table(name = "right_role_role")
public class RoleRole extends BaseEntity {
	
	/**
	  * <code>serialVersionUID<code>
	  */
	private static final long serialVersionUID = 4274105209662820256L;
	
	/**
	  * <code>fk_role<code> ��ɫid
	  */
	private String fk_role = "";
	
	/**
	  * <code>fk_have_role<code> ��ɫӵ�еĽ�ɫ
	  */
	private String fk_have_role = "";

	public RoleRole() {
		
	}
	
	public RoleRole(String fk_role, String fk_have_role) {
		this.fk_role = fk_role;
		this.fk_have_role = fk_have_role;
	}

	/**
	 * @return ���� fk_role��
	 * hibernate.property
     * hibernate.column name="fk_role" not-null="true"
	 */
	@Column(name = "fk_role", length = 20)
	public String getFk_role() {
		return fk_role;
	}

	/**
	 * @param fk_role Ҫ���õ� fk_role��
	 */
	public void setFk_role(String fk_role) {
		this.fk_role = fk_role;
	}
	
	
	/**
	 * @return ���� fk_have_role��
	 * hibernate.property
     * hibernate.column name="fk_have_role" not-null="true"
	 */
	@Column(name = "fk_have_role", length = 20)
	public String getFk_have_role() {
		return fk_have_role;
	}

	/**
	 * @param fk_have_role Ҫ���õ� fk_have_role��
	 */
	public void setFk_have_role(String fk_have_role) {
		this.fk_have_role = fk_have_role;
	}

	

	public boolean equals(final Object other) {
		if (!(other instanceof RoleRole))
			return false;
		RoleRole castOther = (RoleRole) other;
		return new EqualsBuilder().append(id, castOther.id).append(fk_role,
				castOther.fk_role).append(fk_have_role, castOther.fk_have_role)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(id).append(fk_role).append(
				fk_have_role).toHashCode();
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("fk_role",
				fk_role).append("fk_have_role", fk_have_role).toString();
	}
}
