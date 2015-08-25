/*
 * package com.lily.dap.model;
 * class Module
 * 
 * �������� 2005-8-2
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.entity.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Table;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;


/**
 * ģ��ʵ���࣬�洢ģ����Ϣ
 * 
 * @author zouxuemo
*/
@Entity
@Table(appliesTo = "module")
@EntityPropertity(label="ģ��")
public class Module extends BaseEntity {
    /**
	 * <code>serialVersionUID</code> serialVersionUID
	 */
	private static final long serialVersionUID = -1552972417171335804L;

	/**
     * <code>code</code> ģ�����
     */
	@FieldPropertity(label="ģ�����")
    private String code = "";
    
    /**
     * <code>name</code> ģ������
     */
	@FieldPropertity(label="ģ������")
    private String name = "";
    
    /**
     * <code>des</code> ģ��˵��
     */
	@FieldPropertity(label="ģ��˵��")
    private String des = "";
    
    /**
     * <code>createDate</code> ��������
     */
	@FieldPropertity(label="��������", allowModify=false)
    private Date createDate = new Date();
    
    /**
     * <code>defaultModule</code> �Ƿ�ȱʡģ��
     */
	@FieldPropertity(label="�Ƿ�ȱʡģ��", allowModify=false)
    private boolean defaultModule = false;

    private double num = 0;
    
    private int cnt = 0;
    
    @Column(length = 20)
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(length = 50)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Lob
    @Column(nullable = true)
    public String getDes() {
        return des;
    }
    
    public void setDes(String des) {
        this.des = des;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(boolean defaultModule) {
		this.defaultModule = defaultModule;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	/**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof Module)) {
            return false;
        }
        Module rhs = (Module) object;
        return new EqualsBuilder().append(this.id, rhs.id).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1268848737, 997999421).append(this.id).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("code", this.code)
        		.append("name", this.name).append("des", this.des).append("createDate", this.createDate)
        		.append("defaultModule", this.defaultModule).toString();
    }
}
