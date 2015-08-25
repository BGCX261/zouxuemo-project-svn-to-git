package com.lily.dap.entity.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Table;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;

@Entity
@Table(appliesTo = "log")
@EntityPropertity(label="日志")
public class Log extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4730375176710763415L;
	
	/**
	 * 操作用户名称
	 */
	@FieldPropertity(label="登录用户")
	private String logUser = "";
	
	/**
	 * 操作人员名称
	 */
	@FieldPropertity(label="人员名称")
	private String logName = "";
	
	/**
	 * 操作计算机IP
	 */
	@FieldPropertity(label="计算机")
	private String logIP = "";
	
	/**
	 * 操作时间
	 */
	@FieldPropertity(label="时间")
	private Date logTime = new Date(System.currentTimeMillis());
	
	/**
	 * 日志项目
	 */
	@FieldPropertity(label="操作")
	private String operation = "";
	
	/**
	 * 详细说明
	 */
	@FieldPropertity(label="详细说明")
	private String des = "";

	/**
	 * @return logUser
	 */
    @Column(length = 20)
	public String getLogUser() {
		return logUser == null ? "" : logUser;
	}

	/**
	 * @param logUser 要设置的 logUser
	 */
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}
	
	/**
	 * @return
	 */
    @Column(length = 20)
	public String getLogName() {
		return logName == null ? "" : logName;
	}

	/**
	 * @param logName
	 */
	public void setLogName(String logName) {
		this.logName = logName;
	}

	/**
	 * @return logIP
	 */
    @Column(length = 20)
	public String getLogIP() {
		return logIP == null ? "" : logIP;
	}

	/**
	 * @param logIP 要设置的 logIP
	 */
	public void setLogIP(String logIP) {
		this.logIP = logIP;
	}

	/**
	 * @return logTime
	 */
	@Column
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime 要设置的 logTime
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/**
	 * @return operation
	 */
    @Column(length = 50)
	public String getOperation() {
		return operation == null ? "" : operation;
	}

	/**
	 * @param operation 要设置的 operation
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return des
	 */
    @Column(length = 1000)
	public String getDes() {
		return des;
	}

	/**
	 * @param des 要设置的 des
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Log)) {
			return false;
		}
		Log rhs = (Log) object;
		//只要确保时间在"年月日时分秒"上准确就可以
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new EqualsBuilder().append(
				sdf.format(this.logTime), sdf.format(rhs.logTime)).append(this.logIP, rhs.logIP)
				.append(this.operation, rhs.operation).append(this.id, rhs.id)
				.append(this.des, rhs.des).append(this.logUser, rhs.logUser)
				.append(this.logName, rhs.logName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1922360705, -204525245).append(this.logTime).append(this.logIP)
				.append(this.operation).append(this.id).append(this.des)
				.append(this.logUser).append(this.logName).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("des", this.des).append(
				"operation", this.operation).append("logIP", this.logIP)
				.append("id", this.id).append("logTime", this.logTime)
				.append("logUser", this.logUser).append("logName", this.logName)
				.toString();
	}
}
