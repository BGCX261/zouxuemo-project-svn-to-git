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
 * �ֵ��ֶ���Ϣʵ����
 * 
 * @author ��ѧģ
 *
 * hibernate.class table="dictionary"
 */
@Entity
@Table(name = "dict_info")
@EntityPropertity(label="�ֵ�����")
public class Dictionary extends BaseEntity {
	private static final long serialVersionUID = 3695529708026621668L;
    
    /**
     * �ֵ�Ŀ¼����
     */
	@FieldPropertity(label="�ֵ�Ŀ¼", allowModify=false)
    private String catalogCode = "";
    
	/**
	 * �ֵ��е�˳���
	 */
	@FieldPropertity(label="�����")
    private int sn = 0;
    
    /**
     * �ֵ���루ID��
     */
	@FieldPropertity(label="�ֵ����")
    private int dictId = 0;
    
    /**
     * �ֵ���루CODE��
     */
	@FieldPropertity(label="�ֵ����")
    private String dictCode = "";
    
    /**
	 * �ֵ�ֵ
	 */
	@FieldPropertity(label="�ֵ�ֵ")
    private String dictValue = "";
    
    /**
	 * ϵͳ�����־��ϵͳ���룺������ɾ�����޸ġ���ϵͳ���룺����ɾ�����޸ģ�
	 */
	@FieldPropertity(label="ϵͳ����", allowModify=false)
    private int systemFlag = 0;
    
    /**
	 * �Ƿ���ʾ��־������Ϊ�ǣ����ܹ��������б�����ʾ���������������б�����ʾ��
	 */
	@FieldPropertity(label="�Ƿ���ʾ")
    private int showFlag = 1;
    
    /**
	 * �ֵ����ñ�־
	 */
	@FieldPropertity(label="�Ƿ�����")
    private int enableFlag = 1;

    /**
	 * @return the catalogCode
     * 
     * hibernate.property
     * hibernate.column name="catalogCode" not-null="true" length="50"
	 */
    @Column(length = 50)
	public String getCatalogCode() {
		return catalogCode == null ? "" : catalogCode;
	}

	/**
	 * @param catalogCode the catalogCode to set
	 */
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}

	/**
     * @return ���� level��
     * 
     * hibernate.property
     * hibernate.column name="enableFlag" not-null="true"
     */
    @Column
	public int getEnableFlag() {
		return enableFlag;
	}

	/**
	 * @param enableFlag Ҫ���õ� enableFlag��
	 */
	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

    /**
	 * @return the dictCode
     * 
     * hibernate.property
     * hibernate.column name="code" length="50"
	 */
    @Column(length = 50)
	public String getDictCode() {
		return dictCode == null ? "" : dictCode;
	}

	/**
	 * @param dictCode the dictCode to set
	 */
	public void setDictCode(String code) {
		this.dictCode = code;
	}

	/**
     * @return ���� dictValue��
     * 
     * hibernate.property
     * hibernate.column name="dictValue" not-null="true" length="255"
     */
    @Column(length = 255)
	public String getDictValue() {
		return dictValue == null ? "" : dictValue;
	}

	/**
	 * @param dictValue Ҫ���õ� dictValue��
	 */
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	/**
	 * @return ���� showFlag��
     * 
     * hibernate.property
     * hibernate.column name="showFlag" not-null="true"
	 */
    @Column
	public int getShowFlag() {
		return showFlag;
	}

    /**
     * @return ���� showFlag��
     */
	public void setShowFlag(int showFlag) {
		this.showFlag = showFlag;
	}

    /**
     * @return ���� sn��
     * 
     * hibernate.property
     * hibernate.column name="sn" not-null="true"
     */
    @Column
	public int getSn() {
		return sn;
	}

	/**
	 * @param sn Ҫ���õ� sn��
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

    /**
     * @return ���� systemFlag��
     * 
     * hibernate.property
     * hibernate.column name="systemFlag" not-null="true"
     */
    @Column
	public int getSystemFlag() {
		return systemFlag;
	}

	/**
	 * @param systemFlag Ҫ���õ� systemFlag��
	 */
	public void setSystemFlag(int systemFlag) {
		this.systemFlag = systemFlag;
	}

	/**
	 * @return ���� dictId��
	 * 
     * hibernate.property
     * hibernate.column name="dictId" not-null="true"
	 */
    @Column
	public int getDictId() {
		return dictId;
	}

	/**
	 * @param dictId Ҫ���õ� dictId��
	 */
	public void setDictId(int dictId) {
		this.dictId = dictId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("catalogCode", this.catalogCode).append("sn", this.sn)
				.append("id", this.id).append("dictId", this.dictId).append(
						"systemFlag", this.systemFlag).append("showFlag",
						this.showFlag).append("dictCode", this.dictCode)
				.append("dictValue", this.dictValue).append("enableFlag",
						this.enableFlag).toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Dictionary)) {
			return false;
		}
		Dictionary rhs = (Dictionary) object;
		return new EqualsBuilder().append(
				this.catalogCode, rhs.catalogCode).append(this.sn, rhs.sn)
				.append(this.systemFlag, rhs.systemFlag).append(this.dictValue,
						rhs.dictValue).append(this.enableFlag, rhs.enableFlag)
				.append(this.dictId, rhs.dictId).append(this.showFlag,
						rhs.showFlag).append(this.dictCode, rhs.dictCode)
				.append(this.id, rhs.id).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1218332313, -472440225).append(this.catalogCode).append(this.sn)
				.append(this.systemFlag).append(this.dictValue).append(
						this.enableFlag).append(this.dictId).append(
						this.showFlag).append(this.dictCode).append(this.id)
				.toHashCode();
	}

	/* ���� Javadoc��
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Dictionary clone() throws CloneNotSupportedException {
		return (Dictionary)super.clone();
	}
}
