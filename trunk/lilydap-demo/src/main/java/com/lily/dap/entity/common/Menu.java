/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
 * <p>菜单定义实体类</p>
 *
 * @author 邹学模
 * @date 2008-5-10
 */
@Entity
@Table(name = "common_menu")
@EntityPropertity(label="菜单")
public class Menu extends BaseEntity {
	private static final long serialVersionUID = 800094989757594135L;
	
	/**
	 * <code>loadCode</code> 菜单所属装载编码（一个系统可以定义好几套菜单，不同的用户可以选择进入不同的菜单）
	 */
	@FieldPropertity(allowModify=false)
	private String loadCode = "";
	
	/**
	 * UID标识，标识菜单唯一主键
	 */
	@FieldPropertity(allowModify=false)
	private String uid = "";
	
	/**
	 * <code>parentUid</code> 父菜单ID
	 */
	@FieldPropertity(allowModify=false)
	private String parentUid = "";
	
	/**
	 * <code>sn</code> 同级字典下的排序顺序
	 */
	@FieldPropertity(label="菜单顺序", allowModify=false)
	private int sn = 0;
	
	/**
	 * <code>text</code> 菜单文本
	 */
	@FieldPropertity(label="菜单文本")
	private String text = "";
	
	/**
	 * <code>description</code> 菜单说明
	 */
	@FieldPropertity(label="菜单说明")
	private String description = "";
	
	/**
	 * <code>icon</code> 菜单图片
	 */
	@FieldPropertity(label="菜单图片")
	private String icon = "";
	
	/**
	 * <code>link</code> 菜单链接
	 */
	@FieldPropertity(label="菜单链接")
	private String link = "";
	
	/**
	 * <code>accessRight</code> 访问权限
	 */
	@FieldPropertity(label="访问权限")
	private String accessRight = "";

	/**
	 * <code>childs</code> 子菜单列表
	 */
	@Transient
	@FieldPropertity(allowModify=false)
	private List<Menu> childs = new ArrayList<Menu>();

	/**
	 * 返回 loadCode.
	 * @return loadCode
	 */
    @Column(length = 30)
	public String getLoadCode() {
		return loadCode == null ? "" : loadCode;
	}

	/**
	 * 设置 loadCode 值为 <code>loadCode</code>.
	 * @param loadCode 要设置的 loadCode 值
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
	 * 返回 parentUid.
	 * @return parentUid
	 */
	public String getParentUid() {
		return parentUid;
	}

	/**
	 * 设置 parentUid 值为 <code>parentUid</code>.
	 * @param parentUid 要设置的 parentUid 值
	 */
	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	/**
	 * 返回 sn.
	 * @return sn
	 */
	public int getSn() {
		return sn;
	}

	/**
	 * 设置 sn 值为 <code>sn</code>.
	 * @param sn 要设置的 sn 值
	 */
	public void setSn(int sn) {
		this.sn = sn;
	}

	/**
	 * 返回 text.
	 * @return text
	 */
    @Column(length = 40)
	public String getText() {
		return text == null ? "" : text;
	}

	/**
	 * 设置 text 值为 <code>text</code>.
	 * @param text 要设置的 text 值
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回 description.
	 * @return description
	 */
    @Column(length = 100)
	public String getDescription() {
		return description == null ? "" : description;
	}

	/**
	 * 设置 description 值为 <code>description</code>.
	 * @param description 要设置的 description 值
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 icon.
	 * @return icon
	 */
    @Column(length = 200)
	public String getIcon() {
		return icon == null ? "" : icon;
	}

	/**
	 * 设置 icon 值为 <code>icon</code>.
	 * @param icon 要设置的 icon 值
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 返回 link.
	 * @return link
	 */
    @Column(length = 200)
	public String getLink() {
		return link == null ? "" : link;
	}

	/**
	 * 设置 link 值为 <code>link</code>.
	 * @param link 要设置的 link 值
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 返回 accessRight.
	 * @return accessRight
	 * 
	 * @form type="textfield"
	 */
    @Column(length = 1000)
	public String getAccessRight() {
		return accessRight == null ? "" : accessRight;
	}

	/**
	 * 设置 accessRight 值为 <code>accessRight</code>.
	 * @param accessRight 要设置的 accessRight 值
	 */
	public void setAccessRight(String right) {
		this.accessRight = right;
	}

	/**
	 * 返回 childs.
	 * @return childs
	 */
	public List<Menu> getChilds() {
		return childs;
	}

	/**
	 * 设置 childs 值为 <code>childs</code>.
	 * @param childs 要设置的 childs 值
	 */
	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}
	
	/**
	 * 添加子菜单
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
