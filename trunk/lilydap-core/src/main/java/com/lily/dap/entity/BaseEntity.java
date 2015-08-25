package com.lily.dap.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.lily.dap.annotation.FieldPropertity;

/**
 * ʵ������࣬�����˿�¡�ӿں����л��ӿڣ��������ʵ��toString��equals��hashCode
 *
 * @author ��ѧģ
 */
@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Serializable {    
	private static final long serialVersionUID = -141688884509305350L;
	
	/**
	 * ʵ�����������ֶΣ���ʶΨһ��ʵ����󣬲���������;
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
