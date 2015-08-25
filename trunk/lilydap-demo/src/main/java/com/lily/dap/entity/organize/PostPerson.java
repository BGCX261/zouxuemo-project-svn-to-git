/*
 * package com.lily.dap.model.organize;
 * class GroupMember
 * 
 * 创建日期 2006-3-3
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
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
 * 岗位人员中间表对象
 * 
 * @author zouxuemo
 */
@Entity
@Table(name = "organize_post_person")
@EntityPropertity(label="岗位人员")
public class PostPerson extends BaseEntity {
    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = 1L;

    /**
     * <code>post_code</code> 岗位编号
     */
    private String post_code = "";
    
    /**
     * <code>person_username</code> 人员ID
     */
    private String person_username = "";

    /**
     * @return 返回 post_code。
     * 
     * hibernate.property
     * hibernate.column name="fk_post" not-null="true"
     */
    @Column(name = "fk_post")
    public String getPost_code() {
        return post_code;
    }

    /**
     * @param post_code 要设置的 post_code。
     */
    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    /**
     * @return 返回 person_username。
     * 
     * hibernate.property
     * hibernate.column name="fk_person" not-null="true"
     */
    @Column(name = "fk_person")
    public String getPerson_username() {
        return person_username;
    }

    /**
     * @param person_username 要设置的 person_username。
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
