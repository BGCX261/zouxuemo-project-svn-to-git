/*
 * com.lily.dap.model.businessmodel;
 * class FieldDefinition
 * 
 *br>作者: liuyuanke
 *
 * <br>日期： 2009-1-7
 *
 * <br>淄博东软百合软件开发有限公司
 * 
 * <br> 业务模型定义
 * 
 */
package com.lily.dap.entity.datamodel;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;

/**
 * 数据模型字段定义，定义字段编码、名称及相关属性
 * 
 * @author zouxuemo
 *
 */
@Entity
@Table(name = "dm_field_definition")
@EntityPropertity(label="模型字段定义")
public class FieldDefinition extends BaseEntity {
	private static final long serialVersionUID = 6188076350576857695L;
	
	/**
	 * <code>MODIFY_FLAG_NONE</code> 修改标志－未修改
	 */
	public static final int MODIFY_FLAG_NONE = 0;
	/**
	 * <code>MODIFY_FLAG_ADD</code> 修改标志－添加字段
	 */
	public static final int MODIFY_FLAG_ADD = 1;
	/**
	 * <code>MODIFY_FLAG_MODIFY</code> 修改标志－修改字段
	 */
	public static final int MODIFY_FLAG_MODIFY = 2;
	/**
	 * <code>MODIFY_FLAG_REMOVE</code> 修改标志－删除字段
	 */
	public static final int MODIFY_FLAG_REMOVE = 3;
    
    /**
	 * 所属模型UID
	 */
	@FieldPropertity(label="所属模型UID", allowModify=false)
    private String modelUid = "";
    
    
    /**
     * 排序号
     */
	@FieldPropertity(label="排 序 号", allowModify=false)
    private int sn = 0;
    
    /**
     * 字段名
     */
	@FieldPropertity(label="字段编码", allowModify=false)
    private String code = "";
    
    /**
     * 标签名
     */
	@FieldPropertity(label="字段标签")
    private String label = "";
    
	/**
	 * <code>fieldName</code> 对应数据库字段名
	 */
	@FieldPropertity(label=" 表字段名")
	private String fieldName = "";
	
    /**
     * 字段类型 
     */
	@FieldPropertity(label=" 字段类型 ")
    private String type = "";
	 
	
	 /**
     * 字段长度
     */
	@FieldPropertity(label=" 字段长度")
    private int fieldLength = 0;
    
	
	 /**
     * 是否允许为空 
     */
	@FieldPropertity(label=" 允许为空 ")
    private boolean allowNull = false;
    
    
    /**
     * 初值公式
     */
	@FieldPropertity(label=" 初值公式")
    private String initVal = "";
    
    /**
     * 录入提示
     */
	@FieldPropertity(label="录入提示")
    private String des = "";
	
	/**
	 * <code>modifyFlag</code> 当前字段修改标志
	 */
	private int modifyFlag = MODIFY_FLAG_NONE;

	
	

	/**
	 * 返回 modelCode
	 * @return modelCode
	 */
	 @Column(length=30)  
	public String getModelUid() {
		return modelUid == null ? "" : modelUid;
	}
    
	 /**
		 * 设置 modelCode
		 * @param modelCode
		 */
	public void setModelUid(String modelCode) {
		this.modelUid = modelCode;
	}

	/**
	 * 返回 sn
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * 
	 * @param code 要设置的 sn 值
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}
    
	
	 
	/**
	 * 返回 code
	 * @return code
	 */
	 @Column(length=30)  
	public String getCode() {
		return code == null ? "" : code;
	}

	/**
	 * 
	 * @param code 要设置的 code 值
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 返回 label
	 * @return label
	 */
	 @Column(length=50)  
	public String getLabel() {
		return label == null ? "" : label;
	}

	/**
	 * 
	 * @param label 要设置的 label 值
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 返回 fieldName.
	 * @return fieldName
	 */
	 @Column(length=30) 
	public String getFieldName() {
		return fieldName == null ? "" : fieldName;
	}

	/**
	 * 设置 fieldName 值为 <code>fieldName</code>.
	 * @param fieldName 要设置的 fieldName 值
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 返回 type
	 * @return type
	 */
	 @Column(length=30) 
	public String getType() {
		return type == null ? "" : type;
	}

	/**
	 * 
	 * @param type 要设置的 type 值
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * 返回 length
	 * @return length
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	/**
	 * 
	 * @param length 要设置的 length 值
	 */
	public void setFieldLength(int length) {
		this.fieldLength = length;
	}

	
	/**
	 * 返回 des
	 * @return des
	 */
	 @Column(length=100)
	public String getDes() {
		return des;
	}

	/**
	 * 
	 * @param des 要设置的 des 值
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/* 返回 allowNull
	 * @return allowNull
	 */
	 @Type(type = "yes_no") 
	public boolean isAllowNull() {
		return allowNull;
	}

	 /**
		 * 
		 * @param allowNull 要设置的 allowNull 值
		 */
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	 /* 返回 initVal
	 * @return initVal
	 */
	 @Column(length=200)
	public String getInitVal() {
		return initVal == null ? "" : initVal;
	}
    
	 /**
	  * 
	  * @param initVal 要设置的 initVal 值
	  */
	public void setInitVal(String initVal) {
		this.initVal = initVal;
	}

	/**
	 * 返回 modifyFlag
	 * @return modifyFlag
	 */
	public int getModifyFlag() {
		return modifyFlag;
	}

	/**
	 * 
	 * @param modifyFlag 要设置的 modifyFlag 值
	 */
	public void setModifyFlag(int modifyState) {
		this.modifyFlag = modifyState;
	}

	@Override
	public String toString() {
		return "FieldDefinition [allowNull=" + allowNull + ", code=" + code
				+ ", des=" + des + ", fieldLength=" + fieldLength
				+ ", fieldName=" + fieldName + ", initVal=" + initVal
				+ ", label=" + label + ", modelUid=" + modelUid
				+ ", modifyFlag=" + modifyFlag + ", sn=" + sn + ", type="
				+ type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3533;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((modelUid == null) ? 0 : modelUid.hashCode());
		result = prime * result + sn;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		FieldDefinition other = (FieldDefinition) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (modelUid == null) {
			if (other.modelUid != null)
				return false;
		} else if (!modelUid.equals(other.modelUid))
			return false;
		if (sn != other.sn)
			return false;
		return true;
	}

	public FieldDefinition clone() throws CloneNotSupportedException {
		return (FieldDefinition)super.clone();
	}
}
