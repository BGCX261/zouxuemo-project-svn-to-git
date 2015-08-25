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
 * 字典目录分类信息实体类
 * 
 *    <p>　　字典结构：字典目录分类(DictClassify)－>字典目录(DictCatalog)－>字典记录(Dictionary)
 */
@Entity
@Table(name = "dict_classify") 
@EntityPropertity(label="字典分类")
public class DictClassify  extends BaseEntity {
	private static final long serialVersionUID = -6770375333238005950L;
					
	/**
	 * 字典分类编码
	 */
	@FieldPropertity(label="分类编码", allowModify=false)
	private String code = "";
	
	/**
	 * 字典目录分类名称
	 */
	@FieldPropertity(label="分类名称")
	private String name = "";
	
	/**
	 * 排序号
	 */
	@FieldPropertity(label="排序号")
	private int sn = 0;
	
	/**
	 * 字典分类说明
	 */
	@FieldPropertity(label="分类说明")
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
	 * @param code 要设置的 code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param des 要设置的 des
	 */
	public void setDes(String des) {
		this.des = des;
	}


	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param sn 要设置的 sn
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
