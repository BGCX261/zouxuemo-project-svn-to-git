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
 * 字典目录实体类
 * 
 * <p>　　字典结构：字典目录分类(DictClassify)－>字典目录(DictCatalog)－>字典记录(Dictionary)
 * @author 孙暾
 */
@Entity
@Table(name = "dict_catalog")
@EntityPropertity(label="字典目录")
public class DictCatalog extends BaseEntity {
	private static final long serialVersionUID = -3773268529373581527L;

	public static int MODE_ID_VALUE = 1;
	public static int MODE_CODE_VALUE = 2;
	public static int MODE_VALUE = 3;
	
	public static int EDIT_NONE = 0;
	public static int EDIT_ALLOW = 1;

	/**
	 * 字典目录编码
	 */
	@FieldPropertity(label="目录编码", allowModify=false)
	private String code = "";
	
	/**
	 * 字典目录名称
	 */
	@FieldPropertity(label="目录名称")
	private String name = "";
	
	/**
	 * 字典分类编码
	 */
	@FieldPropertity(label="字典分类")
	private String classify = "";
	
	/**
	 * 字典使用模式（1：ID－Value模式、2：Code－Value模式、3：Value模式）
	 */
	@FieldPropertity(label="使用模式", allowModify=false)
	private int opMode = MODE_ID_VALUE;
	
	/**
	 * 字典目录顺序
	 */
	@FieldPropertity(label="排序号")
	private int sn = 1;
	
	/**
	 * 字典目录说明
	 */
	@FieldPropertity(label="目录说明")
	private String des = "";
	
	/**
	 * 是否允许编辑（0：不允许编辑；大于1：允许编辑）
	 */
	@FieldPropertity(label="允许编辑")
	private int editFlag = EDIT_ALLOW;

    /**
     * @return 返回 level。
     */
	@Column(length=255)
	public String getDes() {
		return des;
	}

	/**
	 * @param des 要设置的 des。
	 */
	public void setDes(String des) {
		this.des = des;
	}
    /**
     * @return 返回 level。
     */
	@Column(length=50)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * @param name 要设置的 name。
	 */
	public void setName(String name) {
		this.name = name;
	}


    /**
     * @return 返回 level。
     */
	@Column
	public int getEditFlag() {
		return editFlag;
	}

	/**
	 * @param editflag 要设置的 editflag。
	 */
	public void setEditFlag(int editFlag) {
		this.editFlag = editFlag;
	}

	/**
	 * @return 返回 code。
	 */
	@Column(length=50)
	public String getCode() {
		return code == null ? "" : code;
	}

	/**
	 * @param code 要设置的 code。
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 返回 code。
	 */
	@Column(length=50)
	public String getClassify() {
		return classify == null ? "" : classify;
	}

	/**
	 * @param classify 要设置的 classify
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}

	@Column
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	/**
	 * @return
	 */
	@Column
	public int getOpMode() {
		return opMode;
	}

	/**
	 * @param opMode
	 */
	public void setOpMode(int mode) {
		this.opMode = mode;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append(
				"des", this.des).append("sn", this.sn)
				.append("opMode", this.opMode).append("id", this.id).append(
						"editFlag", this.editFlag).append("code", this.code)
				.append("classify", this.classify).toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(641236913, 1531424311).append(this.code).append(this.sn).append(
				this.classify).append(this.editFlag).append(this.opMode).append(
				this.name).append(this.id).append(this.des).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DictCatalog)) {
			return false;
		}
		DictCatalog rhs = (DictCatalog) object;
		return new EqualsBuilder().append(
				this.code, rhs.code).append(this.sn, rhs.sn).append(
				this.classify, rhs.classify)
				.append(this.editFlag, rhs.editFlag)
				.append(this.opMode, rhs.opMode).append(this.name, rhs.name)
				.append(this.id, rhs.id).append(this.des, rhs.des).isEquals();
	}

}
