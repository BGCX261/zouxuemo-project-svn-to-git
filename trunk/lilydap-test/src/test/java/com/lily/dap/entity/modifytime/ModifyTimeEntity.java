package com.lily.dap.entity.modifytime;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Table;

import com.lily.dap.dao.extension.ModifyTimeRecorder;
import com.lily.dap.entity.BaseEntity;

@Entity
@Table(appliesTo = "modify_time_entity")
public class ModifyTimeEntity extends BaseEntity implements ModifyTimeRecorder{
	private static final long serialVersionUID = 8026755482526849669L;

	private String value = "";
	
	private Date modifyTime;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.dao.extension.ModifyTimeRecorder#recordModifyTime(java.util.Date)
	 */
	public void recordModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof ModifyTimeEntity)) {
            return false;
        }
        ModifyTimeEntity rhs = (ModifyTimeEntity) object;
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
        return new ToStringBuilder(this).append("id", this.id).append("value", this.value)
        		.append("modifyTime", this.modifyTime).toString();
    }
}
