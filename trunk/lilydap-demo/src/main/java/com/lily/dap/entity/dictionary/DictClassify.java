package com.lily.dap.entity.dictionary;

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
 * �ֵ�Ŀ¼������Ϣʵ����
 * 
 *    <p>�����ֵ�ṹ���ֵ�Ŀ¼����(DictClassify)��>�ֵ�Ŀ¼(DictCatalog)��>�ֵ��¼(Dictionary)
 */
@Entity
@Table(name = "dict_classify") 
@EntityPropertity(label="�ֵ����")
public class DictClassify  extends BaseEntity {
	private static final long serialVersionUID = -6770375333238005950L;
					
	/**
	 * �ֵ�������
	 */
	@FieldPropertity(label="�������", allowModify=false)
	private String code = "";
	
	/**
	 * �ֵ�Ŀ¼��������
	 */
	@FieldPropertity(label="��������")
	private String name = "";
	
	/**
	 * �����
	 */
	@FieldPropertity(label="�����")
	private int sn = 0;
	
	/**
	 * �ֵ����˵��
	 */
	@FieldPropertity(label="����˵��")
	private String des = "";

	@Column(length = 50)
	public String getCode() {
		return code == null ? "" : code;
	}


	@Column(length=50)
	public String getName() {
		return name == null ? "" : name;
	}

	@Column
	public int getSn() {
		return sn;
	}


	@Column(length=255)
	public String getDes() {
		return des;
	}


	/**
	 * @param code Ҫ���õ� code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param des Ҫ���õ� des
	 */
	public void setDes(String des) {
		this.des = des;
	}


	/**
	 * @param name Ҫ���õ� name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param sn Ҫ���õ� sn
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DictClassify)) {
			return false;
		}
		DictClassify rhs = (DictClassify) object;
		return new EqualsBuilder().append(
				this.code, rhs.code).append(this.name, rhs.name).append(
				this.id, rhs.id).append(this.des, rhs.des).isEquals();
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(2056022637, 1737948301).append(this.code).append(this.name).append(
				this.id).append(this.des).toHashCode();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("des", this.des).append("name",
				this.name).append("sn", this.sn).append("id", this.id).append(
				"code", this.code).toString();
	}
}
