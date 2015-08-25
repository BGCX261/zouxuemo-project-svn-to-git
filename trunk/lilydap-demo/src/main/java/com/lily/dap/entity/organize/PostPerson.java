/*
 * package com.lily.dap.model.organize;
 * class GroupMember
 * 
 * �������� 2006-3-3
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.entity.organize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.entity.BaseEntity;

/**
 * ��λ��Ա�м�����
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "organize_post_person")
@EntityPropertity(label="��λ��Ա")
public class PostPerson extends BaseEntity {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>post_code</code> ��λ���
     */
    private String post_code = "";
    
    /**
     * <code>person_username</code> ��ԱID
     */
    private String person_username = "";

    /**
     * @return ���� post_code��
     * 
     * hibernate.property
     * hibernate.column name="fk_post" not-null="true"
     */
    @Column(name = "fk_post")
    public String getPost_code() {
        return post_code;
    }

    /**
     * @param post_code Ҫ���õ� post_code��
     */
    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    /**
     * @return ���� person_username��
     * 
     * hibernate.property
     * hibernate.column name="fk_person" not-null="true"
     */
    @Column(name = "fk_person")
    public String getPerson_username() {
        return person_username;
    }

    /**
     * @param person_username Ҫ���õ� person_username��
     */
    public void setPerson_username(String person_username) {
        this.person_username = person_username;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {
        if (!(other instanceof PostPerson))
            return false;
        PostPerson castOther = (PostPerson) other;
        return new EqualsBuilder().append(id, castOther.id).append(post_code,
                castOther.post_code).append(person_username, castOther.person_username)
                .isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(post_code).append(
                person_username).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("post_code",
                post_code).append("person_username", person_username).toString();
    }
}
