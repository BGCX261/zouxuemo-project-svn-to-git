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
	 * 父菜单层级编码，必须指定，允许通过该编码获取一组菜单树。
	 */
	@FieldPropertity(allowModify=false)
	private String parentCode = "";
	
	/**
	 * 菜单层级编码，第一级菜单层级编码为001、002、...，其子菜单层级编码为父菜单层级编码+三位顺序号，层级编码也作为标识菜单唯一主键
	 */
	@FieldPropertity(allowModify=false)
	private String levelCode = "";
	
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
	 * 返回 parentCode.
	 * @return parentCode
	 */
    @Column(length = 30)
	public String getParentCode() {
		return parentCode == null ? "" : parentCode;
	}

	/**
	 * 设置 parentCode 值为 <code>parentCode</code>.
	 * @param parentCode 要设置的 parentCode 值
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Column(length = 20)
	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
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
				.append(this.icon, rhs.icon).append(this.parentCode, rhs.parentCode)
				.append(this.levelCode, rhs.levelCode).append(this.accessRight,
				rhs.accessRight).append(this.link, rhs.link).append(this.id, rhs.id)
				.append(this.sn, rhs.sn).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2136368291, 1910161495)
				.append(this.text).append(this.description).append(this.sn)
				.append(this.icon).append(this.levelCode).append(this.parentCode)
				.append(this.accessRight).append(this.link).append(this.id).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("levelCode", this.levelCode)
				.append("icon", this.icon).append("text", this.text)
				.append("description", this.description).append("sn", this.sn)
				.append("accessRight", this.accessRight).append("id", this.id)
				.append("link", this.link).append("parentCode", this.parentCode).toString();
	}

	@Override
	public Menu clone() throws CloneNotSupportedException {
		return (Menu)super.clone();
	}
}
