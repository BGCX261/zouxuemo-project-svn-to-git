/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.AddressTag;
import com.lily.dap.entity.common.HandlersTag;
import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.organize.PostPerson;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataHaveIncludeException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.DataNotIncludeException;
import com.lily.dap.service.organize.DepartmentManager;
import com.lily.dap.service.organize.OrganizeCacheManager;
import com.lily.dap.service.right.RightHoldManager;
import com.lily.dap.service.util.EntityOrderUtil;
import com.lily.dap.service.util.VerifyUtil;

/**
 * <code>DepartmentManagerImpl</code>
 * <p>����/��λ����ӿ�ʵ��</p>
 *
 * @author ��ѧģ
 * @date 2008-3-19
 */
@Service("departmentManager")
public class DepartmentManagerImpl extends BaseManager implements ApplicationEventPublisherAware,
		DepartmentManager {
	@Autowired
	private RightHoldManager rightHoldManager;
	
	@Autowired
	private OrganizeCacheManager organizeCacheManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private EntityOrderUtil entityOrderUtil;
	
    private VerifyUtil verifyUtil;
    
    @PostConstruct
    public void init() {
    	verifyUtil = new VerifyUtil(dao);
    	entityOrderUtil = new EntityOrderUtil(dao);
    }    

//	/* ���� Javadoc��
//	 * @see com.lily.dap.service.organize.DepartmentManager#initOrganize(int, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	public Department initOrganize(int clazz, String code, String name, String des) {
//		Department root = new Department();
//		root.setParent_id(0);
//		root.setSn(1);
//		root.setLevel("001");
//		root.setType(Department.TYPE_ROOT);
//		root.setClazz(clazz);
//		root.setCode(code);
//		root.setName(name);
//		root.setDes(des);
//		
//		rightHoldManager.providerHoldsRole(root);
//		
//		root = saveOrUpdate(root);
//		
//		//������Ӳ����¼�
//		applicationEventPublisher.publishEvent(new DepartmentChangeEvent(root, DepartmentChangeEvent.ADD));
//		return root;
//	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#createDepartment(com.lily.dap.entity.organize.Department)
	 */
	public Department createDepartment(Department department)
			throws DataNotExistException {
		Department parent = getDepartment(department.getParentLevel());
		
		//����˽�н�ɫ�����õ�������
		rightHoldManager.providerHoldsRole(department);
		
		department.setSn(getDepartmentMaxSN(parent.getLevel())+1);
		department.setLevel(generateDepartmentLevel(parent));
		verifyUtil.assertDataNotRepeat(Department.class, 
				new String[] {"parentLevel","name"}, 
				new Object[] {department.getParentLevel(),department.getName()}, 
				"ͬ��֮�䣬�������Ʋ������ظ���");
		department = saveOrUpdate(department);

		//���ò������ϼ����ŵĽ�ɫ����
		rightHoldManager.addHaveHold(department, parent);
		
		//������Ӳ����¼�
		applicationEventPublisher.publishEvent(new DepartmentChangeEvent(department, DepartmentChangeEvent.ADD));
		return department;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#createPost(com.lily.dap.entity.organize.Post)
	 */
	public Post createPost(Post post) throws DataNotExistException {
		Department department = getDepartment(post.getDepLevel());
		
		//����˽�н�ɫ�����õ�������
		rightHoldManager.providerHoldsRole(post);
		
		post.setSn(getPostMaxSN(department.getLevel())+1);
		post.setCode(generatePostCode(department));
		post = saveOrUpdate(post);
		
		rightHoldManager.addHaveHold(post, department);
		
		//������Ӹ�λ�¼�
		applicationEventPublisher.publishEvent(new PostChangeEvent(post, PostChangeEvent.ADD));
		return post;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getChilds(long, boolean)
	 */
	public List<Department> getChilds(long parent_id,
			boolean notIncludeChild) {
		return organizeCacheManager.getChilds(parent_id, notIncludeChild);
		
//		Department parent = getDepartment(parent_id);
//		
//		Condition cond = new Condition();
//		if (notIncludeChild) {
//			cond.putCondition("parent_id", parent.getId());
//			cond.addOrder("sn");
//		} else {
//			cond.putCondition("level", QueryExpression.OP_RLIKE, parent.getLevel() + "0");	//��һ��0��ֹ������Ҳ��������
//			cond.addOrder("level").addOrder("sn");
//		}
//		
//		return gets(Department.class, cond);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getDepartment(long)
	 */
	public Department getDepartment(long id) throws DataNotExistException {
		return organizeCacheManager.getDepartment(id);
		
//		return get(Department.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.DepartmentManager#getDepartment(java.lang.String)
	 */
	public Department getDepartment(String level) throws DataNotExistException {
		return  organizeCacheManager.getDepartment(level);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.DepartmentManager#getDepartmentsByPerson(java.lang.String, java.lang.String)
	 */
	public List<Department> getDepartmentsByPerson(String person_username) {
		return organizeCacheManager.getDepartmentsByPerson(person_username);
		
//		Condition cond = new Condition();
//		cond.putCondition("id", QueryExpression.OP_INQUERY, "select dep_id from Post where id in (select post_id from PostPerson where person_username = '" + person_username + "')");
//		
//		return gets(Department.class, cond);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getDepartmentFullName(long)
	 */
	public String getDepartmentFullName(long id) {
		Department dep = getDepartment(id);
		
		StringBuffer buf = new StringBuffer();
		buf.append(dep.getName());
		
		if (!organizeCacheManager.isRootDepartment(dep)) {
			dep = getDepartment(dep.getParentLevel());
			
			do {
				buf.insert(0, dep.getName() + "��");
				
				dep = getDepartment(dep.getParentLevel());
			} while (!organizeCacheManager.isRootDepartment(dep));
		}
		
		return buf.toString();
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getPersons(long, long, boolean, boolean)
	 */
	public List<Person> getPersons(long depart_id, long post_id, boolean notIncludeChild, boolean onlyActivate) {
		return organizeCacheManager.getPersons(depart_id, post_id, notIncludeChild, onlyActivate);
		
//		Condition cond = new Condition();
//		if (post_id > 0) {
//			cond.putCondition("username", QueryExpression.OP_INQUERY, "select person_username from PostPerson where post_id = " + post_id);
//		} else {
//			if (notIncludeChild) {
//				cond.putCondition("username", QueryExpression.OP_INQUERY, "select person_username from PostPerson where post_id in (select id from Post where dep_id = " + depart_id + ")");
//			} else {
//				Department department = getDepartment(depart_id);
//				String level = department.getLevel();
//				cond.putCondition("username", QueryExpression.OP_INQUERY, "select person_username from PostPerson where post_id in (select id from Post where dep_id in (select id from Department where level like '" + level + "%'))");
//			}
//		}
//		cond.addOrder("name");
//		
//		return gets(Person.class, cond);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getPost(long)
	 */
	public Post getPost(long id) throws DataNotExistException {
		return organizeCacheManager.getPost(id);
		
//		return get(Post.class, id);
	}
	
	public Post getPost(String code) throws DataNotExistException {
		return organizeCacheManager.getPost(code);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getPosts(long, boolean)
	 */
	public List<Post> getPosts(long depart_id, boolean notIncludeChild) {
		return organizeCacheManager.getPosts(depart_id, notIncludeChild);
		
//		Condition cond = new Condition();
//		if (notIncludeChild) {
//			cond.putCondition("dep_id", depart_id);
//			cond.addOrder("sn");
//		} else {
//			Department department = getDepartment(depart_id);
//			String level = department.getLevel();
//			
//			cond.putCondition("dep_id", QueryExpression.OP_INQUERY, "select id from Department where level like '" + level + "%'");
//			cond.addOrder("dep_id").addOrder("sn");
//		}
//		
//		return gets(Post.class, cond);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.DepartmentManager#getPostsByPerson(java.lang.String, java.lang.String)
	 */
	public List<Post> getPostsByPerson(String person_username) {
		return organizeCacheManager.getPostsByPerson(person_username);
		
//		Condition cond = new Condition();
//		cond.putCondition("id", QueryExpression.OP_INQUERY, "select post_id from PostPerson where person_username = '" + person_username + "'");
//		
//		return gets(Post.class, cond);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#getRootDepartment()
	 */
	public Department getRootDepartment() throws DataNotExistException {
		return organizeCacheManager.getRootDepartment();
		
//		return get(Department.class, "parent_id", 0);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.DepartmentManager#isRootDepartment(com.lily.dap.entity.organize.Department)
	 */
	public boolean isRootDepartment(Department department) {
		return organizeCacheManager.isRootDepartment(department);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#modifyDepartment(com.lily.dap.entity.organize.Department)
	 */
	public void modifyDepartment(Department department)
			throws DataNotExistException {
		verifyUtil.assertDataNotRepeat(Department.class, 
				new String[] { "level","name" }, 
				new Object[] {department.getLevel(),department.getName() }, 
				new String[] { "id" }, null,
			new Object[] { department.getId() }, "ͬ��֮�䣬�������Ʋ������ظ���");
		department = saveOrUpdate(department);
		
		//�����޸Ĳ�����Ϣ�¼�
		applicationEventPublisher.publishEvent(new DepartmentChangeEvent(department, DepartmentChangeEvent.MODIFY));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#modifyPost(com.lily.dap.entity.organize.Post)
	 */
	public void modifyPost(Post post) throws DataNotExistException {
		post = saveOrUpdate(post);
		
		//�����޸ĸ�λ��Ϣ�¼�
		applicationEventPublisher.publishEvent(new PostChangeEvent(post, PostChangeEvent.MODIFY));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#removeDepartment(long, boolean)
	 */
	synchronized public void removeDepartment(long id, boolean forceRemove)
			throws DataNotExistException, DataHaveUsedException {
		Department department = getDepartment(id);
		if (department.isSystemFlag())
			throw new DataHaveUsedException("����[" + department.getName() + "]��ϵͳ���ţ�����ɾ����");
		
		//����Ƿ�����¼����ţ�����У�������ǿ��ɾ������ɾ���¼�����
		List<Department> childs = getChilds(id, true);
		if (childs.size() > 0) {
			if (!forceRemove) {
				throw new DataHaveUsedException("����[" + department.getName() + "]�������¼����ţ�����ɾ����");
			} else {
				for (Department child : childs) {
					removeDepartment(child.getId(), true);
				}
			}
		}
		
		//����Ƿ������λ������У�������ǿ��ɾ������ɾ����λ
		List<Post> posts = getPosts(id, true);
		if (posts.size() > 0) {
			if (!forceRemove) {
				throw new DataHaveUsedException("����[" + department.getName() + "]�����˸�λ������ɾ����");
			} else {
				for (Post post : posts) {
					removePost(post.getId(), true);
				}
			}
		}
	
		//ɾ���������ϼ����ŵĽ�ɫ����
		Department parent = getDepartment(department.getParentLevel());
		rightHoldManager.removeHaveHold(department, parent);
		
		rightHoldManager.removeHoldsRoles(department);
		
		dao.remove(department);
		
		//����ɾ��������Ϣ�¼�
		applicationEventPublisher.publishEvent(new DepartmentChangeEvent(department, DepartmentChangeEvent.REMOVE));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#removePost(long, boolean)
	 */
	synchronized public void removePost(long id, boolean forceRemove)
			throws DataNotExistException, DataHaveUsedException {
		Post post = getPost(id);
		
		//����Ƿ������Ա������У�������ǿ��ɾ������ɾ����λ����Ա����
		List<Person> persons = getPersons(0, id, true, false);
		if (persons.size() > 0) {
			if (!forceRemove) {
				throw new DataHaveUsedException("��λ[" + post.getName() + "]��������Ա������ɾ����");
			} else {
				for (Person person : persons) {
					removePostHavePerson(post, person);
				}
			}
		}
		
		//ɾ����λ���������ŵĽ�ɫ����
		Department department = getDepartment(post.getDepLevel());
		rightHoldManager.removeHaveHold(post, department);
		
		rightHoldManager.removeHoldsRoles(post);
		
		dao.remove(post);
		
		//����ɾ����λ��Ϣ�¼�
		applicationEventPublisher.publishEvent(new PostChangeEvent(post, PostChangeEvent.REMOVE));
	}
	
	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#addPostHavePerson(com.lily.dap.entity.organize.Post, com.lily.dap.entity.organize.Person)
	 */
	public void addPostHavePerson(Post post, Person person)
			throws DataNotExistException, DataHaveIncludeException {
		//��鵱ǰ�Ƿ��и�λ����Ա����������Ѿ��������׳��쳣
		List<PostPerson> list = getPostPersons(post.getCode(), person.getUsername());
		if (list.size() > 0)
			throw new DataHaveIncludeException("��λ[" + post.getName() + "]�Ѿ���������Ա[" + person.getName() + "]");
		
		//������λ����Ա��ϵ
		PostPerson postPerson = new PostPerson();
		postPerson.setPost_code(post.getCode());
		postPerson.setPerson_username(person.getUsername());
		dao.saveOrUpdate(postPerson);
		
		//������λ����Ա�Ľ�ɫ������ϵ����Աӵ�и�λ��Ȩ�ޣ�
		rightHoldManager.addHaveHold(person, post);
		
		//������Ӹ�λ��Ա�¼�
		applicationEventPublisher.publishEvent(new PostChangeEvent(post, person, PostChangeEvent.ADD_HAVE_PERSON));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#removePostHavePerson(com.lily.dap.entity.organize.Post, com.lily.dap.entity.organize.Person)
	 */
	synchronized public void removePostHavePerson(Post post, Person person)
			throws DataNotExistException, DataNotIncludeException {
		//��鵱ǰ�Ƿ��и�λ����Ա���������δ�������׳��쳣
		List<PostPerson> list = getPostPersons(post.getCode(), person.getUsername());
		if (list.size() == 0)
			throw new DataNotIncludeException("��λ[" + post.getName() + "]����������Ա[" + person.getName() + "]");
		
		//ɾ����λ����Ա��ϵ
		dao.remove(list.get(0));
		
		//ɾ����λ����Ա�Ľ�ɫ������ϵ����Աӵ�и�λ��Ȩ�ޣ�
		rightHoldManager.removeHaveHold(person, post);
		
		//����ȥ����λ��Ա�¼�
		applicationEventPublisher.publishEvent(new PostChangeEvent(post, person, PostChangeEvent.REMOVE_HAVE_PERSON));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#adjustDepartmentOrder(long, int)
	 */
	public void adjustDepartmentOrder(long id, int order)
			throws DataNotExistException {
		Department department = getDepartment(id);
		
		List<Department> adjustDepartments = entityOrderUtil.adjustEntityOrder(Department.class, id, order, "sn", Condition.create().eq("parentLevel", department.getParentLevel()));
		
		//���ÿ�������Ĳ��ţ������޸Ĳ�����Ϣ�¼�
		for (Department dep : adjustDepartments)
			applicationEventPublisher.publishEvent(new DepartmentChangeEvent(dep, DepartmentChangeEvent.MODIFY));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.DepartmentManager#adjustPostOrder(long, int)
	 */
	public void adjustPostOrder(long id, int order)
			throws DataNotExistException {
		Post post = getPost(id);
		
		List<Post> adjustPosts = entityOrderUtil.adjustEntityOrder(Post.class, id, order, "sn", Condition.create().eq("depLevel", post.getDepLevel()));
		
		//���ÿ�������ĸ�λ�������޸ĸ�λ��Ϣ�¼�
		for (Post p : adjustPosts)
			applicationEventPublisher.publishEvent(new PostChangeEvent(p, PostChangeEvent.MODIFY));
	}
	
	/**
	 * ����������λ��������Ա��PostPerson����
	 *
	 * @param post_code ��λcode
	 * @param person_key ��Աkey�����Ϊ�գ������������λ�µ�������Ա
	 * @return
	 */
	private List<PostPerson> getPostPersons(String post_code, String person_key) {
		Condition cond = new Condition();
		cond.eq("post_code", post_code);
		if (person_key != null)
			cond.eq("person_username", person_key);
		
		return query(PostPerson.class, cond);
	}

	/**
	 * ��ȡͬһ�����µ��Ӳ�����������
	 *
	 * @param parentLevel
	 * @return
	 */
	private int getDepartmentMaxSN(String parentLevel) {
		return entityOrderUtil.getEntityMaxOrder(Department.class, "sn", Condition.create().eq("parentLevel", parentLevel));
	}

	/**
	 * ��ȡͬһ�����µĸ�λ��������
	 *
	 * @param depLevel
	 * @return
	 */
	private int getPostMaxSN(String depLevel) {
		return entityOrderUtil.getEntityMaxOrder(Post.class, "sn", Condition.create().eq("depLevel", depLevel));
	}
	
	/**
	 * �����½��Ӳ��ŵ��ڲ�����level
	 * 
	 * @param parent
	 * @return
	 */
	private String generateDepartmentLevel(Department parent){
		Condition cond = new Condition();
		cond.eq("parentLevel", parent.getLevel()).desc("level");
		cond.page(1, 1);
		
		List<Department> childs = query(Department.class, cond);
		
		String tail = null;
		if(childs.isEmpty()){
			tail = "01";
		} else {
			String s = childs.get(0).getLevel();
			s = s.substring(s.length() - 2);
			int sn = Integer.parseInt(s) + 1;
			tail = String.valueOf(100+sn).substring(1);
		}
		
		return parent.getLevel() + tail;
	}

	private String generatePostCode(Department department){
		List<Post> posts = dao.query(Post.class, Condition.create().eq("depLevel", department.getLevel()).desc("code").page(1, 1));
		
		String tail = null;
		if(posts.isEmpty()){
			tail = "01";
		} else {
			String s = posts.get(0).getCode();
			s = s.substring(s.length() - 2);
			int sn = Integer.parseInt(s) + 1;
			tail = String.valueOf(100+sn).substring(1);
		}
		
		return department.getLevel() + tail;
	}

	/* ���� Javadoc��
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

// ----------------------------- AddressParseStrategy Implements ----------------------------------
	
	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getAddressClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Class getAddressClass(String type) throws DataNotExistException {
		if (Department.IDENTIFIER.equals(type))
			return Department.class;
		else if (Post.IDENTIFIER.equals(type))
			return Post.class;
		else
			throw new DataNotExistException("����[ " + type + " ]��֧��");
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getAddressTag(java.lang.String, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public AddressTag getAddressTag(String type, Serializable serializable)
			throws DataNotExistException {
		Class clazz = getAddressClass(type);
		long id = ((Long)serializable).longValue();
		
		if (clazz == Department.class)
			return getDepartment(id);
		else
			return getPost(id);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getDisplayName(com.lily.dap.entity.common.AddressTag)
	 */
	public String getDisplayName(AddressTag tag) {
		if (tag instanceof Department)
			return "-" + ((Department)tag).getName() + "-";
		else if (tag instanceof Post)
			return "[" + ((Post)tag).getName() + "]";
		else
			return null;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getIncludeAddress(com.lily.dap.entity.common.AddressTag)
	 */
	public String getIncludeAddress(AddressTag tag) {
		return null;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#isCanParseHandlersTag(com.lily.dap.entity.common.AddressTag)
	 */
	public boolean isCanParseHandlersTag(AddressTag tag) {
		if (tag instanceof Department || tag instanceof Post)
			return true;
		else
			return false;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#isIncludeAddress(com.lily.dap.entity.common.AddressTag)
	 */
	public boolean isIncludeAddress(AddressTag tag) {
		return false;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#parseHandlersTag(com.lily.dap.entity.common.AddressTag)
	 */
	public List<HandlersTag> parseHandlersTag(AddressTag tag) {
		long depart_id = 0, post_id = 0;
		
		if (tag instanceof Department) {
			Department department = (Department)tag;
			depart_id = department.getId();
		} else if (tag instanceof Post) {
			Post post = (Post)tag;
			post_id = post.getId();
		} else
			return null;
		
		List<Person> list = getPersons(depart_id, post_id, false, true);
		
		List<HandlersTag> tags = new ArrayList<HandlersTag>();
		tags.addAll(list);
		
		return tags;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.impl.AddressParseStrategy#supportAddressType()
	 */
	public String[] supportAddressType() {
		return new String[]{Department.IDENTIFIER, Post.IDENTIFIER};
	}
}
