package com.lily.dap.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.lily.dap.annotation.FieldPropertity;

/**
 * 实体类基类，声明了克隆接口和序列化接口，子类必须实现toString、equals、hashCode
 *
 * @author 邹学模
 */
@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Serializable {    
	private static final long serialVersionUID = -141688884509305350L;
	
	/**
	 * 实体对象的主键字段，标识唯一的实体对象，不作其它用途
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@FieldPropertity(label="ID", allowModify=true)
	protected long id = 0;
	
	public BaseEntity() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public abstract boolean equals(Object o);
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public abstract int hashCode();

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public abstract String toString();
}
