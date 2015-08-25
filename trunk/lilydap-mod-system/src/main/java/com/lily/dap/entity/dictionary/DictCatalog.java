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
 * �ֵ�Ŀ¼ʵ����
 * 
 * <p>�����ֵ�ṹ���ֵ�Ŀ¼����(DictClassify)��>�ֵ�Ŀ¼(DictCatalog)��>�ֵ��¼(Dictionary)
 * @author ����
 */
@Entity
@Table(name = "dict_catalog")
@EntityPropertity(label="�ֵ�Ŀ¼")
public class DictCatalog extends BaseEntity {
	private static final long serialVersionUID = -3773268529373581527L;

	public static int MODE_ID_VALUE = 1;
	public static int MODE_CODE_VALUE = 2;
	public static int MODE_VALUE = 3;
	
	public static int EDIT_NONE = 0;
	public static int EDIT_ALLOW = 1;

	/**
	 * �ֵ�Ŀ¼����
	 */
	@FieldPropertity(label="Ŀ¼����", allowModify=false)
	private String code = "";
	
	/**
	 * �ֵ�Ŀ¼����
	 */
	@FieldPropertity(label="Ŀ¼����")
	private String name = "";
	
	/**
	 * �ֵ�������
	 */
	@FieldPropertity(label="�ֵ����")
	private String classify = "";
	
	/**
	 * �ֵ�ʹ��ģʽ��1��ID��Valueģʽ��2��Code��Valueģʽ��3��Valueģʽ��
	 */
	@FieldPropertity(label="ʹ��ģʽ", allowModify=false)
	private int opMode = MODE_ID_VALUE;
	
	/**
	 * �ֵ�Ŀ¼˳��
	 */
	@FieldPropertity(label="�����")
	private int sn = 1;
	
	/**
	 * �ֵ�Ŀ¼˵��
	 */
	@FieldPropertity(label="Ŀ¼˵��")
	private String des = "";
	
	/**
	 * �Ƿ�����༭��0��������༭������1������༭��
	 */
	@FieldPropertity(label="����༭")
	private int editFlag = EDIT_ALLOW;

    /**
     * @return ���� level��
     */
	@Column(length=255)
	public String getDes() {
		return des;
	}

	/**
	 * @param des Ҫ���õ� des��
	 */
	public void setDes(String des) {
		this.des = des;
	}
    /**
     * @return ���� level��
     */
	@Column(length=50)
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
     * @return ���� level��
     */
	@Column
	public int getEditFlag() {
		return editFlag;
	}

	/**
	 * @param editflag Ҫ���õ� editflag��
	 */
	public void setEditFlag(int editFlag) {
		this.editFlag = editFlag;
	}

	/**
	 * @return ���� code��
	 */
	@Column(length=50)
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
	 * @return ���� code��
	 */
	@Column(length=50)
	public String getClassify() {
		return classify == null ? "" : classify;
	}

	/**
	 * @param classify Ҫ���õ� classify
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
