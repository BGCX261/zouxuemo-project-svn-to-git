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
@EntityPropertity(label="��־")
public class Log extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4730375176710763415L;
	
	/**
	 * �����û�����
	 */
	@FieldPropertity(label="��¼�û�")
	private String logUser = "";
	
	/**
	 * ������Ա����
	 */
	@FieldPropertity(label="��Ա����")
	private String logName = "";
	
	/**
	 * ���������IP
	 */
	@FieldPropertity(label="�����")
	private String logIP = "";
	
	/**
	 * ����ʱ��
	 */
	@FieldPropertity(label="ʱ��")
	private Date logTime = new Date(System.currentTimeMillis());
	
	/**
	 * ��־��Ŀ
	 */
	@FieldPropertity(label="����")
	private String operation = "";
	
	/**
	 * ��ϸ˵��
	 */
	@FieldPropertity(label="��ϸ˵��")
	private String des = "";

	/**
	 * @return logUser
	 */
    @Column(length = 20)
	public String getLogUser() {
		return logUser == null ? "" : logUser;
	}

	/**
	 * @param logUser Ҫ���õ� logUser
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
	 * @param logIP Ҫ���õ� logIP
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
	 * @param logTime Ҫ���õ� logTime
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
	 * @param operation Ҫ���õ� operation
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
	 * @param des Ҫ���õ� des
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
		//ֻҪȷ��ʱ����"������ʱ����"��׼ȷ�Ϳ���
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
