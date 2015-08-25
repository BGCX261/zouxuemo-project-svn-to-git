/*
 * package com.lily.dap.model.businessmodel;
 * class ModelDefinition
 * 
 * <br>作者: fangaide
 *
 * <br>日期： 2009-1-7
 *
 * <br>淄博东软百合软件开发有限公司
 * 
 * <br> 业务模型定义
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
 * 数据模型定义，定义数据模型编号、名称、版本等信息
 * 
 * @author zouxuemo
 *
 */
@Entity
@Table(name = "dm_model_definition")
@EntityPropertity(label="数据模型定义")
public class ModelDefinition extends BaseEntity{
	private static final long serialVersionUID = -7249514773220450856L;

	/**
	 * <code>FLAG_CREATE</code> 发布标志－创建新模型
	 */
	public static final int FLAG_CREATE = 0;
	/**
	 * <code>FLAG_MODIFY</code> 发布标志－修改现有模型
	 */
	public static final int FLAG_MODIFY = 1;
	/**
	 * <code>FLAG_DEPLOY</code> 发布标志－发布模型
	 */
	public static final int FLAG_DEPLOY = 9;
	/**
	 * <code>FLAG_DEPRECATED</code> 发布标志－模型已有新发布，当前模型定义废弃
	 */
	public static final int FLAG_DEPRECATED = -1;
	
	/**
	 * <code>STATE_ENABLED</code> 模型状态－启用
	 */
	public static final int STATE_ENABLED = 1;
	/**
	 * <code>STATE_LOCK</code> 模型状态－锁定
	 */
	public static final int STATE_LOCK = 2;
	/**
	 * <code>STATE_DISABLED</code> 模型状态－禁用
	 */
	public static final int STATE_DISABLED = 9;
	
	/**
	 * 模型UID
	 */
	@FieldPropertity(label="模型UID", allowModify=false)
	private String uid = "";
	
	/**
	 * 模型分类
	 */
	@FieldPropertity(label="模型分类", allowModify=false)
	private String classify = "";
	
	/**
	 * 模型编码
	 */
	@FieldPropertity(label="模型编码", allowModify=false)
	private String code = "";
	
	/**
	 * 模型名称
	 */
	@FieldPropertity(label="模型名称")
	private String name = "";
	
	/**
	 * 数据表名
	 */
	@FieldPropertity(label="数据表名", allowModify=false)
	private String tableName = "";
	
	/**
	 * 模型说明
	 */
	@FieldPropertity(label="模型说明")
	private String des = "";
	
	/**
	 * 模型状态
	 */
	@FieldPropertity(label="模型状态", allowModify=false)
	private int state = STATE_ENABLED;
	
	/**
	 * 发布标志
	 */
	@FieldPropertity(label="发布标志", allowModify=false)
	private int flag = FLAG_CREATE;
	
	/**
	 * 发布人
	 */
	@FieldPropertity(label="发布日期", allowModify=false)
	private Date createDate = new Date();
	
	/**
	 * 发布人
	 */
	@FieldPropertity(label="发 布 人", allowModify=false)
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
	 * 返回 code
	 * @return code
	 */
	@Column(length = 30)
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
	 * 返回 name
	 * @return name
	 */
	@Column(length = 50)
	public String getName() {
		return name == null ? "" : name;
	}

	/**
	 * 
	 * @param name 要设置的 name 值
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 tableName
	 * @return tableName
	 */
	@Column(length = 50)
	public String getTableName() {
		return tableName == null ? "" : tableName;
	}

	/**
	 * 
	 * @param tableName 要设置的 tableName 值
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回 des
	 * @return des
	 */
	@Column(length = 200)
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

	/**
	 * 返回 state
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * 
	 * @param state 要设置的 state 值
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 返回 flag
	 * @return flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * 
	 * @param flag 要设置的 flag 值
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
