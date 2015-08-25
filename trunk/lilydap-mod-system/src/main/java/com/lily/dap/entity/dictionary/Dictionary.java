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
 * 字典字段信息实体类
 * 
 * @author 邹学模
 *
 * hibernate.class table="dictionary"
 */
@Entity
@Table(name = "dict_info")
@EntityPropertity(label="字典数据")
public class Dictionary extends BaseEntity {
	private static final long serialVersionUID = 3695529708026621668L;
    
    /**
     * 字典目录编码
     */
	@FieldPropertity(label="字典目录", allowModify=false)
    private String catalogCode = "";
    
	/**
	 * 字典中的顺序号
	 */
	@FieldPropertity(label="排序号")
    private int sn = 0;
    
    /**
     * 字典编码（ID）
     */
	@FieldPropertity(label="字典编码")
    private int dictId = 0;
    
    /**
     * 字典编码（CODE）
     */
	@FieldPropertity(label="字典编码")
    private String dictCode = "";
    
    /**
	 * 字典值
	 */
	@FieldPropertity(label="字典值")
    private String dictValue = "";
    
    /**
	 * 系统编码标志（系统编码：不允许删除和修改、非系统编码：允许删除和修改）
	 */
	@FieldPropertity(label="系统编码", allowModify=false)
    private int systemFlag = 0;
    
    /**
	 * 是否显示标志（设置为是：则能够在下拉列表中显示，否：则不能在下拉列表中显示）
	 */
	@FieldPropertity(label="是否显示")
    private int showFlag = 1;
    
    /**
	 * 字典启用标志
	 */
	@FieldPropertity(label="是否启用")
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
     * @return 返回 level。
     * 
     * hibernate.property
     * hibernate.column name="enableFlag" not-null="true"
     */
    @Column
	public int getEnableFlag() {
		return enableFlag;
	}

	/**
	 * @param enableFlag 要设置的 enableFlag。
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
     * @return 返回 dictValue。
     * 
     * hibernate.property
     * hibernate.column name="dictValue" not-null="true" length="255"
     */
    @Column(length = 255)
	public String getDictValue() {
		return dictValue == null ? "" : dictValue;
	}

	/**
	 * @param dictValue 要设置的 dictValue。
	 */
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	/**
	 * @return 返回 showFlag。
     * 
     * hibernate.property
     * hibernate.column name="showFlag" not-null="true"
	 */
    @Column
	public int getShowFlag() {
		return showFlag;
	}

    /**
     * @return 返回 showFlag。
     */
	public void setShowFlag(int showFlag) {
		this.showFlag = showFlag;
	}

    /**
     * @return 返回 sn。
     * 
     * hibernate.property
     * hibernate.column name="sn" not-null="true"
     */
    @Column
	public int getSn() {
		return sn;
	}

	/**
	 * @param sn 要设置的 sn。
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

    /**
     * @return 返回 systemFlag。
     * 
     * hibernate.property
     * hibernate.column name="systemFlag" not-null="true"
     */
    @Column
	public int getSystemFlag() {
		return systemFlag;
	}

	/**
	 * @param systemFlag 要设置的 systemFlag。
	 */
	public void setSystemFlag(int systemFlag) {
		this.systemFlag = systemFlag;
	}

	/**
	 * @return 返回 dictId。
	 * 
     * hibernate.property
     * hibernate.column name="dictId" not-null="true"
	 */
    @Column
	public int getDictId() {
		return dictId;
	}

	/**
	 * @param dictId 要设置的 dictId。
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

	/* （非 Javadoc）
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Dictionary clone() throws CloneNotSupportedException {
		return (Dictionary)super.clone();
	}
}
