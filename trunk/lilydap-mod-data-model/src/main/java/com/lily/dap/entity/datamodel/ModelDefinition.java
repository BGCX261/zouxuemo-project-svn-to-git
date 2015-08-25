/*
 * package com.lily.dap.model.businessmodel;
 * class ModelDefinition
 * 
 * <br>����: fangaide
 *
 * <br>���ڣ� 2009-1-7
 *
 * <br>�Ͳ�����ٺ�����������޹�˾
 * 
 * <br> ҵ��ģ�Ͷ���
 * 
 */
package com.lily.dap.entity.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;

/**
 * ����ģ�Ͷ��壬��������ģ�ͱ�š����ơ��汾����Ϣ
 * 
 * @author zouxuemo
 *
 */
@Entity
@Table(name = "dm_model_definition")
@EntityPropertity(label="����ģ�Ͷ���")
public class ModelDefinition extends BaseEntity{
	private static final long serialVersionUID = -7249514773220450856L;

	/**
	 * <code>FLAG_CREATE</code> ������־��������ģ��
	 */
	public static final int FLAG_CREATE = 0;
	/**
	 * <code>FLAG_MODIFY</code> ������־���޸�����ģ��
	 */
	public static final int FLAG_MODIFY = 1;
	/**
	 * <code>FLAG_DEPLOY</code> ������־������ģ��
	 */
	public static final int FLAG_DEPLOY = 9;
	/**
	 * <code>FLAG_DEPRECATED</code> ������־��ģ�������·�������ǰģ�Ͷ������
	 */
	public static final int FLAG_DEPRECATED = -1;
	
	/**
	 * <code>STATE_ENABLED</code> ģ��״̬������
	 */
	public static final int STATE_ENABLED = 1;
	/**
	 * <code>STATE_LOCK</code> ģ��״̬������
	 */
	public static final int STATE_LOCK = 2;
	/**
	 * <code>STATE_DISABLED</code> ģ��״̬������
	 */
	public static final int STATE_DISABLED = 9;
	
	/**
	 * ģ��UID
	 */
	@FieldPropertity(label="ģ��UID", allowModify=false)
	private String uid = "";
	
	/**
	 * ģ�ͷ���
	 */
	@FieldPropertity(label="ģ�ͷ���", allowModify=false)
	private String classify = "";
	
	/**
	 * ģ�ͱ���
	 */
	@FieldPropertity(label="ģ�ͱ���", allowModify=false)
	private String code = "";
	
	/**
	 * ģ������
	 */
	@FieldPropertity(label="ģ������")
	private String name = "";
	
	/**
	 * ���ݱ���
	 */
	@FieldPropertity(label="���ݱ���", allowModify=false)
	private String tableName = "";
	
	/**
	 * ģ��˵��
	 */
	@FieldPropertity(label="ģ��˵��")
	private String des = "";
	
	/**
	 * ģ��״̬
	 */
	@FieldPropertity(label="ģ��״̬", allowModify=false)
	private int state = STATE_ENABLED;
	
	/**
	 * ������־
	 */
	@FieldPropertity(label="������־", allowModify=false)
	private int flag = FLAG_CREATE;
	
	/**
	 * ������
	 */
	@FieldPropertity(label="��������", allowModify=false)
	private Date createDate = new Date();
	
	/**
	 * ������
	 */
	@FieldPropertity(label="�� �� ��", allowModify=false)
	private String creator = "";

	/**
	 * @return
	 */
	@Column(length = 32)
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return
	 */
	@Column(length = 30)
	public String getClassify() {
		return classify;
	}

	/**
	 * @param classify
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}

	/**
	 * ���� code
	 * @return code
	 */
	@Column(length = 30)
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
	 * ���� name
	 * @return name
	 */
	@Column(length = 50)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * 
	 * @param name Ҫ���õ� name ֵ
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� tableName
	 * @return tableName
	 */
	@Column(length = 50)
	public String getTableName() {
		return tableName == null ? "" : tableName;
	}

	/**
	 * 
	 * @param tableName Ҫ���õ� tableName ֵ
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * ���� des
	 * @return des
	 */
	@Column(length = 200)
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

	/**
	 * ���� state
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * 
	 * @param state Ҫ���õ� state ֵ
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * ���� flag
	 * @return flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * 
	 * @param flag Ҫ���õ� flag ֵ
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	

	/**
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return
	 */
	@Column(length = 30)
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "ModelDefinition [classify=" + classify + ", code=" + code
				+ ", createDate=" + createDate + ", creator=" + creator
				+ ", des=" + des + ", flag=" + flag + ", name=" + name
				+ ", state=" + state + ", tableName=" + tableName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 7352525;
		result = prime * result
				+ ((classify == null) ? 0 : classify.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + flag;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ModelDefinition other = (ModelDefinition) obj;
		if (classify == null) {
			if (other.classify != null)
				return false;
		} else if (!classify.equals(other.classify))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (flag != other.flag)
			return false;
		return true;
	}

	public ModelDefinition clone() throws CloneNotSupportedException {
		return (ModelDefinition)super.clone();
	}
}
