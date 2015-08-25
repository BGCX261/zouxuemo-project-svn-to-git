/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.entity.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;

/**
 * <code>Menu</code>
 * <p>�˵�����ʵ����</p>
 *
 * @author ��ѧģ
 * @date 2008-5-10
 */
@Entity
@Table(name = "common_menu")
@EntityPropertity(label="�˵�")
public class Menu extends BaseEntity {
	private static final long serialVersionUID = 800094989757594135L;
	
	/**
	 * <code>loadCode</code> �˵�����װ�ر��루һ��ϵͳ���Զ���ü��ײ˵�����ͬ���û�����ѡ����벻ͬ�Ĳ˵���
	 */
	@FieldPropertity(allowModify=false)
	private String loadCode = "";
	
	/**
	 * UID��ʶ����ʶ�˵�Ψһ����
	 */
	@FieldPropertity(allowModify=false)
	private String uid = "";
	
	/**
	 * <code>parentUid</code> ���˵�ID
	 */
	@FieldPropertity(allowModify=false)
	private String parentUid = "";
	
	/**
	 * <code>sn</code> ͬ���ֵ��µ�����˳��
	 */
	@FieldPropertity(label="�˵�˳��", allowModify=false)
	private int sn = 0;
	
	/**
	 * <code>text</code> �˵��ı�
	 */
	@FieldPropertity(label="�˵��ı�")
	private String text = "";
	
	/**
	 * <code>description</code> �˵�˵��
	 */
	@FieldPropertity(label="�˵�˵��")
	private String description = "";
	
	/**
	 * <code>icon</code> �˵�ͼƬ
	 */
	@FieldPropertity(label="�˵�ͼƬ")
	private String icon = "";
	
	/**
	 * <code>link</code> �˵�����
	 */
	@FieldPropertity(label="�˵�����")
	private String link = "";
	
	/**
	 * <code>accessRight</code> ����Ȩ��
	 */
	@FieldPropertity(label="����Ȩ��")
	private String accessRight = "";

	/**
	 * <code>childs</code> �Ӳ˵��б�
	 */
	@Transient
	@FieldPropertity(allowModify=false)
	private List<Menu> childs = new ArrayList<Menu>();

	/**
	 * ���� loadCode.
	 * @return loadCode
	 */
    @Column(length = 30)
	public String getLoadCode() {
		return loadCode == null ? "" : loadCode;
	}

	/**
	 * ���� loadCode ֵΪ <code>loadCode</code>.
	 * @param loadCode Ҫ���õ� loadCode ֵ
	 */
	public void setLoadCode(String loadCode) {
		this.loadCode = loadCode;
	}

	@Column(length = 20)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * ���� parentUid.
	 * @return parentUid
	 */
	public String getParentUid() {
		return parentUid;
	}

	/**
	 * ���� parentUid ֵΪ <code>parentUid</code>.
	 * @param parentUid Ҫ���õ� parentUid ֵ
	 */
	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	/**
	 * ���� sn.
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * ���� sn ֵΪ <code>sn</code>.
	 * @param sn Ҫ���õ� sn ֵ
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

	/**
	 * ���� text.
	 * @return text
	 */
    @Column(length = 40)
	public String getText() {
		return text == null ? "" : text;
	}

	/**
	 * ���� text ֵΪ <code>text</code>.
	 * @param text Ҫ���õ� text ֵ
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * ���� description.
	 * @return description
	 */
    @Column(length = 100)
	public String getDescription() {
		return description == null ? "" : description;
	}

	/**
	 * ���� description ֵΪ <code>description</code>.
	 * @param description Ҫ���õ� description ֵ
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * ���� icon.
	 * @return icon
	 */
    @Column(length = 200)
	public String getIcon() {
		return icon == null ? "" : icon;
	}

	/**
	 * ���� icon ֵΪ <code>icon</code>.
	 * @param icon Ҫ���õ� icon ֵ
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * ���� link.
	 * @return link
	 */
    @Column(length = 200)
	public String getLink() {
		return link == null ? "" : link;
	}

	/**
	 * ���� link ֵΪ <code>link</code>.
	 * @param link Ҫ���õ� link ֵ
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * ���� accessRight.
	 * @return accessRight
	 * 
	 * @form type="textfield"
	 */
    @Column(length = 1000)
	public String getAccessRight() {
		return accessRight == null ? "" : accessRight;
	}

	/**
	 * ���� accessRight ֵΪ <code>accessRight</code>.
	 * @param accessRight Ҫ���õ� accessRight ֵ
	 */
	public void setAccessRight(String right) {
		this.accessRight = right;
	}

	/**
	 * ���� childs.
	 * @return childs
	 */
	public List<Menu> getChilds() {
		return childs;
	}

	/**
	 * ���� childs ֵΪ <code>childs</code>.
	 * @param childs Ҫ���õ� childs ֵ
	 */
	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}
	
	/**
	 * ����Ӳ˵�
	 *
	 * @param menu
	 */
	public void addChildMenu(Menu menu) {
		childs.add(menu);
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Menu)) {
			return false;
		}
		Menu rhs = (Menu) object;
		return new EqualsBuilder().append(
				this.text, rhs.text).append(this.description, rhs.description)
				.append(this.icon, rhs.icon).append(this.loadCode, rhs.loadCode)
				.append(this.parentUid, rhs.parentUid).append(this.accessRight,
				rhs.accessRight).append(this.link, rhs.link).append(this.id, rhs.id)
				.append(this.uid, rhs.uid).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2136368291, 1910161495)
				.append(this.text).append(this.description)
				.append(this.icon).append(this.parentUid).append(this.loadCode)
				.append(this.accessRight).append(this.link).append(this.id).append(this.uid)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("parentUid", this.parentUid)
				.append("icon", this.icon).append("text", this.text)
				.append("description", this.description).append("uid", this.uid)
				.append("accessRight", this.accessRight).append("id", this.id)
				.append("link", this.link).append("loadCode", this.loadCode).toString();
	}
}
