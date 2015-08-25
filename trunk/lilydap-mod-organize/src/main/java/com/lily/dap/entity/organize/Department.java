package com.lily.dap.entity.organize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.lily.dap.entity.BaseEntity;

/**   
 *    
 * 部门信息实体类  
 *   
 * zouxuemo
 * 邹学模
 * 2011-2-16 下午01:52:17  
 *   
 * @version 1.0.0  
 *    
 */
public class Department extends BaseEntity {
 	private static final long serialVersionUID = 8591296532388190167L;

	/**   
	 * parentCode:上级部门编码   
	 */   
	private String parentCode;
    
	private int sn = 0;
	
	private String code;
	
	private String name;
	
	private String level;
	
	private int type;

    private int clazz = 0;
	
    private String des;

	private int state;
    
    private String privateRoleCode = "";
    
    @Transient
    private List<Department> childs = new ArrayList<Department>();

    @Transient
    private List<Post> posts = new ArrayList<Post>();
    
    @Transient
    private List<Person> persons = new ArrayList<Person>();
    
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public void addPerson(Person person) {
		this.persons.add(person);
	}

	public List<Department> getChilds() {
		return childs;
	}

	public void setChilds(List<Department> childs) {
		this.childs = childs;
	}
	
	public void addChild(Department child) {
		this.childs.add(child);
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost(Post post) {
		this.posts.add(post);
	}

	public String getPrivateRoleCode() {
		return privateRoleCode;
	}

	public void setPrivateRoleCode(String privateRoleCode) {
		this.privateRoleCode = privateRoleCode;
	}

	public int getClazz() {
		return clazz;
	}

	public void setClazz(int clazz) {
		this.clazz = clazz;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return null;
	}
}
