/**
 * 
 */
package com.lily.dap.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zouxuemo
 *
 */
public class TreeNode {
	private String id = "";
	
	private String text = "";
	
	private String des = "";
	
	private String type = null;
	
	private Object data = null;
	
	private List<TreeNode> childs = new ArrayList<TreeNode>();

	public TreeNode(String id, String text, String des, String type, Object data) {
		super();
		this.id = id;
		this.text = text;
		this.des = des;
		this.type = type;
		this.data = data;
	}

	public TreeNode(String id, String text, String type, Object data) {
		super();
		this.id = id;
		this.text = text;
		this.type = type;
		this.data = data;
	}

	public TreeNode(String id, String text, Object data) {
		super();
		this.id = id;
		this.text = text;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<TreeNode> getChilds() {
		return childs;
	}

	public void setChilds(List<TreeNode> childs) {
		this.childs = childs;
	}
	
	public void addChild(TreeNode treeNode) {
		this.childs.add(treeNode);
	}
}
