/*
 * com.lily.dap.model.businessmodel;
 * class FieldDefinition
 * 
 *br>����: liuyuanke
 *
 * <br>���ڣ� 2009-1-7
 *
 * <br>�Ͳ�����ٺ�����������޹�˾
 * 
 * <br> ҵ��ģ�Ͷ���
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
 * ����ģ���ֶζ��壬�����ֶα��롢���Ƽ��������
 * 
 * @author zouxuemo
 *
 */
@Entity
@Table(name = "dm_field_definition")
@EntityPropertity(label="ģ���ֶζ���")
public class FieldDefinition extends BaseEntity {
	private static final long serialVersionUID = 6188076350576857695L;
	
	/**
	 * <code>MODIFY_FLAG_NONE</code> �޸ı�־��δ�޸�
	 */
	public static final int MODIFY_FLAG_NONE = 0;
	/**
	 * <code>MODIFY_FLAG_ADD</code> �޸ı�־������ֶ�
	 */
	public static final int MODIFY_FLAG_ADD = 1;
	/**
	 * <code>MODIFY_FLAG_MODIFY</code> �޸ı�־���޸��ֶ�
	 */
	public static final int MODIFY_FLAG_MODIFY = 2;
	/**
	 * <code>MODIFY_FLAG_REMOVE</code> �޸ı�־��ɾ���ֶ�
	 */
	public static final int MODIFY_FLAG_REMOVE = 3;
    
    /**
	 * ����ģ��UID
	 */
	@FieldPropertity(label="����ģ��UID", allowModify=false)
    private String modelUid = "";
    
    
    /**
     * �����
     */
	@FieldPropertity(label="�� �� ��", allowModify=false)
    private int sn = 0;
    
    /**
     * �ֶ���
     */
	@FieldPropertity(label="�ֶα���", allowModify=false)
    private String code = "";
    
    /**
     * ��ǩ��
     */
	@FieldPropertity(label="�ֶα�ǩ")
    private String label = "";
    
	/**
	 * <code>fieldName</code> ��Ӧ���ݿ��ֶ���
	 */
	@FieldPropertity(label=" ���ֶ���")
	private String fieldName = "";
	
    /**
     * �ֶ����� 
     */
	@FieldPropertity(label=" �ֶ����� ")
    private String type = "";
	 
	
	 /**
     * �ֶγ���
     */
	@FieldPropertity(label=" �ֶγ���")
    private int fieldLength = 0;
    
	
	 /**
     * �Ƿ�����Ϊ�� 
     */
	@FieldPropertity(label=" ����Ϊ�� ")
    private boolean allowNull = false;
    
    
    /**
     * ��ֵ��ʽ
     */
	@FieldPropertity(label=" ��ֵ��ʽ")
    private String initVal = "";
    
    /**
     * ¼����ʾ
     */
	@FieldPropertity(label="¼����ʾ")
    private String des = "";
	
	/**
	 * <code>modifyFlag</code> ��ǰ�ֶ��޸ı�־
	 */
	private int modifyFlag = MODIFY_FLAG_NONE;

	
	

	/**
	 * ���� modelCode
	 * @return modelCode
	 */
	 @Column(length=30)  
	public String getModelUid() {
		return modelUid == null ? "" : modelUid;
	}
    
	 /**
		 * ���� modelCode
		 * @param modelCode
		 */
	public void setModelUid(String modelCode) {
		this.modelUid = modelCode;
	}

	/**
	 * ���� sn
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * 
	 * @param code Ҫ���õ� sn ֵ
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}
    
	
	 
	/**
	 * ���� code
	 * @return code
	 */
	 @Column(length=30)  
	public String getCode() {
		return code == null ? "" : code;
	}

	/**
	 * 
	 * @param code Ҫ���õ� code ֵ
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���� label
	 * @return label
	 */
	 @Column(length=50)  
	public String getLabel() {
		return label == null ? "" : label;
	}

	/**
	 * 
	 * @param label Ҫ���õ� label ֵ
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * ���� fieldName.
	 * @return fieldName
	 */
	 @Column(length=30) 
	public String getFieldName() {
		return fieldName == null ? "" : fieldName;
	}

	/**
	 * ���� fieldName ֵΪ <code>fieldName</code>.
	 * @param fieldName Ҫ���õ� fieldName ֵ
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * ���� type
	 * @return type
	 */
	 @Column(length=30) 
	public String getType() {
		return type == null ? "" : type;
	}

	/**
	 * 
	 * @param type Ҫ���õ� type ֵ
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * ���� length
	 * @return length
	 */
	public int getFieldLength() {
		return fieldLength;
	}

	/**
	 * 
	 * @param length Ҫ���õ� length ֵ
	 */
	public void setFieldLength(int length) {
		this.fieldLength = length;
	}

	
	/**
	 * ���� des
	 * @return des
	 */
	 @Column(length=100)
	public String getDes() {
		return des;
	}

	/**
	 * 
	 * @param des Ҫ���õ� des ֵ
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/* ���� allowNull
	 * @return allowNull
	 */
	 @Type(type = "yes_no") 
	public boolean isAllowNull() {
		return allowNull;
	}

	 /**
		 * 
		 * @param allowNull Ҫ���õ� allowNull ֵ
		 */
	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	 /* ���� initVal
	 * @return initVal
	 */
	 @Column(length=200)
	public String getInitVal() {
		return initVal == null ? "" : initVal;
	}
    
	 /**
	  * 
	  * @param initVal Ҫ���õ� initVal ֵ
	  */
	public void setInitVal(String initVal) {
		this.initVal = initVal;
	}

	/**
	 * ���� modifyFlag
	 * @return modifyFlag
	 */
	public int getModifyFlag() {
		return modifyFlag;
	}

	/**
	 * 
	 * @param modifyFlag Ҫ���õ� modifyFlag ֵ
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
