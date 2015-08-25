/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize.impl;

import java.io.Serializable;
import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.organize.PostPerson;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.OrganizeCacheManager;

/**
 * <code>OrganizeCacheManagerImpl</code>
 * <p>组织机构缓存管理实现类，负责缓存的同步更新，以及提供部门、岗位、人员的各种查询检索</p>
 *
 * @author 邹学模
 * @date 2008-3-26
 */
@Service("organizeCacheManager")
public class OrganizeCacheManagerImpl extends BaseManager implements ApplicationListener<ApplicationEvent>, OrganizeCacheManager {
	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * <code>departmentCacheMap</code> 部门缓存Map，通过部门ID与部门一一对应
	 */
	private Map<Serializable, Department> departmentCacheMap = new HashMap<Serializable, Department>();
	private Map<String, Department> departmentCacheMap2 = new HashMap<String, Department>();
	
	/**
	 * <code>postCacheMap</code> 岗位缓存Map，通过岗位ID与岗位一一对应
	 */
	private Map<Serializable, Post> postCacheMap = new HashMap<Serializable, Post>();
	private Map<String, Post> postCacheMap2 = new HashMap<String, Post>();

	/**
	 * <code>personCacheMap</code> 人员缓存Map，通过人员ID与人员一一对应
	 */
	private Map<Serializable, Person> personCacheMap = new HashMap<Serializable, Person>();
	private Map<String, Person> personCacheMap2 = new HashMap<String, Person>();

	private Map<String, List<String>> departmentTreeRelationshipCacheMap = new HashMap<String, List<String>>();
	private Map<String, List<String>> departmentPostRelationshipCacheMap = new HashMap<String, List<String>>();
	
	private Map<String, List<String>> postPersonRelationshipCacheMap = new HashMap<String, List<String>>();
	private Map<String, List<String>> personPostRelationshipCacheMap = new HashMap<String, List<String>>();
	
	/**
	 * <code>root</code> 根部门
	 */
	private Department root = null;
	
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getRootDepartment()
	 */
    public Department getRootDepartment() throws DataNotExistException {
    	if (root == null)
    		throw new DataNotExistException("根部门不存在，请初始化组织机构信息");
    	
    	return root;
    }

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.OrganizeCacheManager#isRootDepartment(com.lily.dap.entity.organize.Department)
	 */
	public boolean isRootDepartment(Department department) {
		return root.getLevel().equals(department.getLevel());
	}
	
    /* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getDepartment(long)
	 */
    public Department getDepartment(long id) throws DataNotExistException {
    	Long Identity = new Long(id);
    	
    	if (!(departmentCacheMap.containsKey(Identity)))
    		throw new DataNotExistException("ID为[" + id + "]的部门未找到");
    	
    	return departmentCacheMap.get(Identity);
    }

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.OrganizeCacheManager#getDepartment(java.lang.String)
	 */
	public Department getDepartment(String level) throws DataNotExistException {
	   	if (!(departmentCacheMap2.containsKey(level)))
    		throw new DataNotExistException("level为[" + level + "]的部门未找到");
    	
    	return departmentCacheMap2.get(level);
	}
     
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getChilds(long, boolean)
	 */
    public List<Department> getChilds(long parent_id, boolean notIncludeChild) {
    	Department department = getDepartment(parent_id);
    	
    	List<String> childLevelList = new ArrayList<String>();
    	insertDepartmentLevelToList(childLevelList, department.getLevel(), notIncludeChild);

    	List<Department> result = new ArrayList<Department>();
    	for (String childLevel : childLevelList)
    		result.add(getDepartment(childLevel));
    	
    	Collections.sort(result, new DepartmentComparator());
    	return result;
    }
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPost(long)
	 */
    public Post getPost(long id) throws DataNotExistException {
    	Long Identity = new Long(id);
    	
    	if (!(postCacheMap.containsKey(Identity)))
    		throw new DataNotExistException("ID为[" + id + "]的岗位未找到");
    	
    	return postCacheMap.get(Identity);
    }

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.OrganizeCacheManager#getPost(java.lang.String)
	 */
	public Post getPost(String code) throws DataNotExistException {
		if (!(postCacheMap2.containsKey(code)))
    		throw new DataNotExistException("code为[" + code + "]的岗位未找到");
    	
    	return postCacheMap2.get(code);
	}
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPosts(long, boolean)
	 */
    public List<Post> getPosts(long depart_id, boolean notIncludeChild) {
    	Department department = getDepartment(depart_id);
    	
    	List<String> depLevelList = new ArrayList<String>();
    	depLevelList.add(department.getLevel());
    	
    	if (!notIncludeChild)
    		insertDepartmentLevelToList(depLevelList, department.getLevel(), notIncludeChild);

    	List<String> postCodeList = new ArrayList<String>();
    	for (String depLevel : depLevelList) {
    		List<String> list = getOrNewMapsStringListValue(departmentPostRelationshipCacheMap, depLevel);
       		postCodeList.addAll(list);
    	}
    	
    	List<Post> result = new ArrayList<Post>();
    	for (String postCode : postCodeList)
    		result.add(getPost(postCode));
    	
    	Collections.sort(result, new PostComparator());
    	return result;
    }
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPostsByPerson(java.lang.String)
	 */
    public List<Post> getPostsByPerson(String person_username) {
    	String person_key = person_username;
    	
    	List<Post> result = new ArrayList<Post>();
    	
    	List<String> postCodeList = getOrNewMapsStringListValue(personPostRelationshipCacheMap, person_key);
		for (String postCode : postCodeList)
    		result.add(getPost(postCode));
    	
    	return result;
    }
    
    /* (non-Javadoc)
     * @see com.lily.dap.service.organize.OrganizeCacheManager#getDepartmentsByPerson(java.lang.String, java.lang.String)
     */
    public List<Department> getDepartmentsByPerson(String person_username) {
    	List<Post> posts = getPostsByPerson(person_username);
    	
    	List<Department> result = new ArrayList<Department>();
    	for (Post post : posts)
    		result.add(getDepartment(post.getDepLevel()));
    	
    	return result;
    }
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPersons(long, long, boolean, boolean)
	 */
    public List<Person> getPersons(long depart_id, long post_id, boolean notIncludeChild, boolean onlyActivate) {
    	List<Person> result = new ArrayList<Person>();
    	
    	List<Post> postList = new ArrayList<Post>();
    	if (post_id > 0) {
    		postList.add(getPost(post_id));
    	} else if (depart_id > 0) {
    		postList = getPosts(depart_id, notIncludeChild);
    	} else {
    		throw new DataNotExistException("未指定要查找人员所在部门或岗位");
    	}
    	
    	Map<String, Person> personMap = new HashMap<String, Person>();
    	for (Post post : postList) {
    		for (String personKey : getOrNewMapsStringListValue(postPersonRelationshipCacheMap, post.getCode())) {
    			if (!personMap.containsKey(personKey))
    				personMap.put(personKey, personCacheMap2.get(personKey));
    		}
    	}
    	
    	result.addAll(personMap.values());
    	
    	if (onlyActivate) {
        	for (int index = result.size() - 1; index >= 0; index--) {
        		Person person = result.get(index);

        		if (Person.STATE_NORMAL != person.getState())
        			result.remove(index);
        	}
    	}
    		
    	Collections.sort(result, new PersonComparator());
    	return result;
    }
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPerson(java.lang.String)
	 */
    public Person getPerson(String username) throws DataNotExistException {
    	String personKey = username;
    	
    	if (!personCacheMap2.containsKey(personKey))
    		throw new DataNotExistException("用户编号为[" + username + "]的人员未找到");
    	
    	return personCacheMap2.get(personKey);
    }

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.OrganizeCacheManager#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(String name) throws DataNotExistException {
		String key = name;
		
    	for (Person person : personCacheMap.values())
    		if (key.equals(person.getName()))
    			return person;
    	
    	throw new DataNotExistException("名称为[" + name + "]的人员未找到");
	}
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPerson(long)
	 */
    public Person getPerson(long id) throws DataNotExistException {
    	Long Identity = new Long(id);
    	
    	if (!(personCacheMap.containsKey(Identity)))
    		throw new DataNotExistException("ID为[" + id + "]的人员未找到");
    	
    	return personCacheMap.get(Identity);
    }
    
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.impl.OrganizeCacheManager#getPersons(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
    public List<Person> getPersons(String username, String name, String sex, String mobile, boolean onlyActivate) {
    	List<Person> result = new ArrayList<Person>();
    	
    	boolean findUsernameFlag = (username != null && !"".equals(username));
    	boolean findNameFlag = (name != null && !"".equals(name));
    	boolean findSexFlag = (sex != null && !"".equals(sex));
    	boolean findMobildFlag = (mobile != null && !"".equals(mobile));
    	
    	for (Person person : personCacheMap.values()) {
    		if (onlyActivate && person.getState() != Person.STATE_NORMAL)
    			continue;
    		
    		if (findUsernameFlag && !(username.equals(person.getUsername())))
    			continue;
    		
    		if (findNameFlag && !(person.getUsername().indexOf(name) < 0))
    			continue;
    		
    		if (findSexFlag && !(sex.equals(person.getSex())))
    			continue;
    		
    		if (findMobildFlag && !(mobile.equals(person.getMobile())))
    			continue;
    		
    		result.add(person);
    	}

    	Collections.sort(result, new PersonComparator());
    	return result;
    }
    
    /**
     * 系统启动时初始化组织机构缓存，读取部门、岗位、人员数据，并构造缓存结构
     *
     */
    @PostConstruct
    public void init() {
    	if (log.isDebugEnabled())
    		log.debug("组织机构缓存开始装载数据...");
    	
    	//装载部门数据，并根据上下级关联建立部门树结构
    	List<Department> departments = dao.query(Department.class, new Condition().asc("parentLevel").asc("sn"));
    	for (Department department : departments) {
    		departmentCacheMap.put(new Long(department.getId()), department);
    		departmentCacheMap2.put(department.getLevel(), department);

    		if ("".equals(department.getParentLevel()))
    			root = department;
    		
    		String parentLevel = department.getParentLevel();
    		if (!"".equals(parentLevel)) {
    			List<String> childDepList = getOrNewMapsStringListValue(departmentTreeRelationshipCacheMap, parentLevel);
    			childDepList.add(department.getLevel());
    		}
    	}
    	
    	if (log.isDebugEnabled())
    		log.debug("-->共载入 " + departments.size() + " 个部门数据.");
    	
    	//装载岗位数据，并根据关联部门建立部门－岗位关联
    	List<Post> posts = dao.query(Post.class, new Condition().asc("depLevel").asc("sn"));
    	for (Post post : posts) {
    		postCacheMap.put(new Long(post.getId()), post);
    		postCacheMap2.put(post.getCode(), post);
    		
    		String depLevel = post.getDepLevel();
    		List<String> childPostList = getOrNewMapsStringListValue(departmentPostRelationshipCacheMap, depLevel);
			childPostList.add(post.getCode());
    	}
    	
    	if (log.isDebugEnabled())
    		log.debug("-->共载入 " + posts.size() + " 个岗位数据.");
    	
    	//装载人员数据
    	List<Person> persons = dao.query(Person.class, new Condition().asc("name"));
    	for (Person person : persons) {
    		personCacheMap.put(new Long(person.getId()), person);
    		personCacheMap2.put(person.getUsername(), person);
    	}
    	
    	if (log.isDebugEnabled())
    		log.debug("-->共载入 " + persons.size() + " 条人员数据.");

    	//读取岗位人员关联表，并建立岗位－人员关联表
    	List<PostPerson> pps = dao.query(PostPerson.class, new Condition().asc("post_code"));
    	for (PostPerson pp : pps) {
    		String postCode = pp.getPost_code();
    		String personKey = pp.getPerson_username();
    		
    		List<String> personCodeList = getOrNewMapsStringListValue(postPersonRelationshipCacheMap, postCode);
			personCodeList.add(personKey);
			
    		
    		List<String> postCodeList = getOrNewMapsStringListValue(personPostRelationshipCacheMap, personKey);
			postCodeList.add(postCode);
    	}
}
    
	/* 
	 * 捕获部门、岗位、人员改变事件，并相应更新缓存数据
	 * 
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent e) {
		if (e instanceof DepartmentChangeEvent) {
			DepartmentChangeEvent event = (DepartmentChangeEvent)e;
			
			processDepartmentChangeEvent(event.getOp(), event);
		} else if (e instanceof PostChangeEvent) {
			PostChangeEvent event = (PostChangeEvent)e;
			
			processPostChangeEvent(event.getOp(), event);
		} else if (e instanceof PersonChangeEvent) {
			PersonChangeEvent event = (PersonChangeEvent)e;
			
			processPersonChangeEvent(event.getOp(), event);
		} else
			return;

    	if (log.isDebugEnabled())
    		log.debug("组织机构缓存处理 " + e.getClass().getSimpleName() + " 的" + e.getSource() + "事件,同步组织机构缓存数据.");
	}
	
	private void processDepartmentChangeEvent(String op, DepartmentChangeEvent event) {
		Department department = event.getDepartment();
		
		//对于删除和修改部门时，都是删除部门数据，以及其上级部门中与该部门的关联
		if (DepartmentChangeEvent.REMOVE.equals(op) || DepartmentChangeEvent.MODIFY.equals(op)) {
			departmentCacheMap.remove(new Long(department.getId()));
			departmentCacheMap2.remove(department.getLevel());
		
			getOrNewMapsStringListValue(departmentTreeRelationshipCacheMap, department.getParentLevel()).remove(department.getLevel());
		}
		 
		//对于添加和修改部门，都是添加部门数据，以及添加上级部门与该部门的关联关系
		if (DepartmentChangeEvent.ADD.equals(op) || DepartmentChangeEvent.MODIFY.equals(op)) {
			try {
				department = department.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return;
			}
			
			departmentCacheMap.put(new Long(department.getId()), department);
			departmentCacheMap2.put(department.getLevel(), department);
			
			List<String> list = getOrNewMapsStringListValue(departmentTreeRelationshipCacheMap, department.getParentLevel());
			list.add(department.getLevel());
		}
	}
	
	private void processPostChangeEvent(String op, PostChangeEvent event) {
		Post post = event.getPost();
		Person person = event.getPerson();

		//对于删除和修改岗位时，都是删除岗位数据，以及其所属部门中与岗位的关联
		if (PostChangeEvent.REMOVE.equals(op) || PostChangeEvent.MODIFY.equals(op)) {
			postCacheMap.remove(new Long(post.getId()));
			postCacheMap2.remove(post.getCode());
			
			getOrNewMapsStringListValue(departmentPostRelationshipCacheMap, post.getDepLevel()).remove(post.getCode());
		}
		 
		//对于添加和修改岗位，都是添加岗位数据，以及添加所属部门中与岗位的关联
		if (PostChangeEvent.ADD.equals(op) || PostChangeEvent.MODIFY.equals(op)) {
			try {
				post = post.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return;
			}
			
			postCacheMap.put(new Long(post.getId()), post);
			postCacheMap2.put(post.getCode(), post);
		
			List<String> list = getOrNewMapsStringListValue(departmentPostRelationshipCacheMap, post.getDepLevel());
			list.add(post.getCode());
		}
		
		//处理添加岗位和人员关联关系
		if (PostChangeEvent.ADD_HAVE_PERSON.equals(op)) {
			String postCode = post.getCode();
			String personKey = person.getUsername();

			getOrNewMapsStringListValue(postPersonRelationshipCacheMap, postCode).add(personKey);
			getOrNewMapsStringListValue(personPostRelationshipCacheMap, personKey).add(postCode);
		}
		
		//处理删除岗位和人员关联关系
		if (PostChangeEvent.REMOVE_HAVE_PERSON.equals(op)) {
			String postCode = post.getCode();
			String personKey = person.getUsername();

			getOrNewMapsStringListValue(postPersonRelationshipCacheMap, postCode).remove(personKey);
			getOrNewMapsStringListValue(personPostRelationshipCacheMap, personKey).remove(postCode);
		}
	}
	
	private void processPersonChangeEvent(String op, PersonChangeEvent event) {
		Person person = event.getPerson();
		
		//对于删除和修改人员时，都是删除人员数据
		if (PersonChangeEvent.REMOVE.equals(op) || PersonChangeEvent.MODIFY.equals(op)) {
			personCacheMap.remove(new Long(person.getId()));
			personCacheMap2.remove(person.getUsername());
		}
		 
		//对于添加和修改人员，都是添加人员数据
		if (PersonChangeEvent.ADD.equals(op) || PersonChangeEvent.MODIFY.equals(op)) {
			try {
				person = event.getPerson().clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return;
			}
			
			personCacheMap.put(new Long(person.getId()), person);
			personCacheMap2.put(person.getUsername(), person);
		}
	}
	
    private void insertDepartmentLevelToList(List<String> result, String level, boolean notIncludeChild) {
    	List<String> childDepLevelList = departmentTreeRelationshipCacheMap.get(level);
    	if (childDepLevelList == null)
    		return;
    	
    	result.addAll(childDepLevelList);
    	
    	if (!notIncludeChild)
    		for (String childDepLevel : childDepLevelList)
    			insertDepartmentLevelToList(result, childDepLevel, notIncludeChild);
    }
	
	private List<String> getOrNewMapsStringListValue(Map<String, List<String>> map, String key) {
		List<String> list = map.get(key);
		if (list == null) {
			list = new ArrayList<String>();
			map.put(key, list);
		}
		
		return list;
	}
}

class DepartmentComparator implements Comparator<Department> {
	public int compare(Department o1, Department o2) {
		int v = (o1.getParentLevel()).compareTo(o2.getParentLevel());
		if (v != 0)
			return v;
		
		v = o1.getSn() - o2.getSn();
		return v;
	}
}

class PostComparator implements Comparator<Post> {
	public int compare(Post o1, Post o2) {
		int v = (o1.getDepLevel()).compareTo(o2.getDepLevel());
		if (v != 0)
			return v;
		
		v = o1.getSn() - o2.getSn();
		return v;
	}
}

class PersonComparator implements Comparator<Person> {
    private Collator collator = Collator.getInstance();
    
    private String orderFieldName = "name";

	public PersonComparator() {
	}
    
	public PersonComparator(String orderFieldName) {
		this.orderFieldName = orderFieldName;
	}

	public int compare(Person o1, Person o2) {
		try {
			CollationKey key1 = collator.getCollationKey(PropertyUtils.getProperty(o1, orderFieldName).toString());
			CollationKey key2 = collator.getCollationKey(PropertyUtils.getProperty(o2, orderFieldName).toString());
			
			return key1.compareTo(key2);
		} catch (Exception e) {
			return 0;
		}
	}
}