/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.organize.DepartmentManager;
import com.lily.dap.service.organize.OrganizeCacheManager;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.service.right.RightHoldManager;
import com.lily.dap.service.right.RoleManager;
import com.lily.dap.service.util.VerifyUtil;
import com.lily.dap.util.StringUtil;

/**
 * <code>PersonManagerImpl</code>
 * <p>人员注册、维护、激活/锁定、停用管理接口实现</p>
 *
 * @author 邹学模
 */
@Service("personManager")
public class PersonManagerImpl extends BaseManager implements ApplicationEventPublisherAware, PersonManager {
	@Autowired
	private RightHoldManager rightHoldManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private DepartmentManager departmentManager;
	
	@Autowired
	private OrganizeCacheManager organizeCacheManager;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
    /**
     * <code>encryptPassword</code> 是否对用户密码进行加密
     */
    private boolean encryptPassword = true;
    
    /**
     * <code>algorithm</code> 如果用户密码加密，进行加密的算法，默认使用SHA加密算法
     */
    private String algorithm = "SHA";
    
    private VerifyUtil verifyUtil;
    
    @PostConstruct
    public void init() {
    	verifyUtil = new VerifyUtil(dao);
    }    
	
    /**
     * @param algorithm 要设置的 algorithm。
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @param encryptPassword 要设置的 encryptPassword。
     */
    public void setEncryptPassword(boolean encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.PersonManager#changeUserPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void changeUserPassword(String username, String password)
			throws DataNotExistException {
		Person person = getPerson(username);
		
		if (encryptPassword)
			password = StringUtil.encodePassword(password, algorithm);
		
		person.setPassword(password);
		
		dao.saveOrUpdate(person);
		
		//发布修改人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.MODIFY));
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#createPerson(com.lily.dap.entity.organize.Person)
	 */
	public Person createPerson(Person person) throws DataContentRepeatException {
		verifyUtil.assertDataNotRepeat(Person.class, "username", person.getUsername(), "人员编码重复，请输入新的人员编码值！");
		//verifyUtil.assertDataNotRepeat(Person.class, "name", person.getName(), "人员名称重复，请输入新的人员名称值！");

		if (encryptPassword) {
			String password = person.getPassword();
			password = StringUtil.encodePassword(password, algorithm);
		
			person.setPassword(password);
		}
		
		rightHoldManager.providerHoldsRole(person);
		
		person = saveOrUpdate(person);
		
		//发布添加人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.ADD));
		return person;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#enablePerson(long, boolean)
	 */
	public void enablePerson(long id, boolean enabled)
			throws DataNotExistException {
		Person person = getPerson(id);
		
		person.setEnabled(enabled);
		
		dao.saveOrUpdate(person);
		
		//发布修改人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.MODIFY));
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.PersonManager#getPerson(java.lang.String, java.lang.String)
	 */
	public Person getPerson(String username) throws DataNotExistException {
		return organizeCacheManager.getPerson(username);
		
//		return get(Person.class, "username", username);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.organize.PersonManager#getPersonByName(java.lang.String, java.lang.String)
	 */
	public Person getPersonByName(String name) throws DataNotExistException {
		return organizeCacheManager.getPersonByName(name);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#getPerson(long)
	 */
	public Person getPerson(long id) throws DataNotExistException {
		return organizeCacheManager.getPerson(id);
		
//		return get(Person.class, id);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#getPersons(com.lily.dap.dao.Condition)
	 */
	public List<Person> getPersons(Condition queryCondition, boolean onlyActivate) {
		if (onlyActivate) {
			if (queryCondition == null)
				queryCondition = new Condition();
			
			queryCondition.eq("state", Person.STATE_NORMAL);
		}
		
		//如果未设置排序字段，则默认按照名称排序
		if (queryCondition.getOrders().size() == 0)
			queryCondition.asc("name");
		
		return query(Person.class, queryCondition);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#getPersons(java.lang.String, boolean)
	 */
	public List<Person> getPersons(long depart_id, long post_id, String haveRole, boolean onlyActivate) {
		Role r = roleManager.getRole(haveRole);
		
		List<Person> persons;
		if (depart_id > 0 || post_id > 0)
			persons = organizeCacheManager.getPersons(depart_id, post_id, false, onlyActivate);
		else
			persons = organizeCacheManager.getPersons(null, null, null, null, onlyActivate);
		
		List<Role> roles = roleManager.getByHaveRoles(r.getId(), Role.FLAG_PRIVATE_ROLE, false);
		
		List<Person> result = new ArrayList<Person>();
		for (Person person : persons) {
			String roleCode = person.getPrivateRoleCode();
			
			for (Role role : roles) {
				if (roleCode.equals(role.getCode())) {
					result.add(person);
					break;
				}
			}
		}
		
		return result;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#isPersonHaveRole(long, java.lang.String)
	 */
	public boolean isPersonHaveRole(long person_id, String haveRole) {
		Person person = getPerson(person_id);
		String roleCode = person.getPrivateRoleCode();
		
		Role r = roleManager.getRole(haveRole);
		List<Role> roles = roleManager.getByHaveRoles(r.getId(), Role.FLAG_PRIVATE_ROLE, false);
		for (Role role : roles) {
			if (roleCode.equals(role.getCode())) {
				return true;
			}
		}
		
		return false;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#modifyPerson(com.lily.dap.entity.organize.Person)
	 */
	public void modifyPerson(Person person) throws DataNotExistException,
			DataContentRepeatException {
		//verifyUtil.assertDataNotRepeat(Person.class, "name", person.getName(), "id", person.getId(), "人员名称重复，请输入新的人员名称值！");
		
		person = saveOrUpdate(person);
		
		//发布修改人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.MODIFY));
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#removePerson(long)
	 */
	synchronized public void removePerson(long id) throws DataNotExistException {
		Person person = getPerson(id);
		
		//去除岗位与人员关联关系
		List<Post> posts = organizeCacheManager.getPostsByPerson(person.getUsername());
//		List<Post> posts = departmentManager.getPostsByPerson(person.getUsername());
		for (Post post : posts) {
			departmentManager.removePostHavePerson(post, person);
		}
		
		rightHoldManager.removeHoldsRoles(person);
		
		dao.remove(person);
		
		//发布删除人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.REMOVE));
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.PersonManager#stopPerson(long)
	 */
	public void stopPerson(long id) throws DataNotExistException {
		Person person = getPerson(id);
		
		person.setState(Person.STATE_OFFLINE);
		person.setEnabled(false);
		
		dao.saveOrUpdate(person);
		
		//发布修改人员信息事件
		applicationEventPublisher.publishEvent(new PersonChangeEvent(person, PersonChangeEvent.MODIFY));
	}

	public String encryptPassword(String password) {
		if (encryptPassword)
			password = StringUtil.encodePassword(password, algorithm);
		
		return password;
	}

	/* （非 Javadoc）
	 * @see org.springframework.context.ApplicationEventPublisherAware#setApplicationEventPublisher(org.springframework.context.ApplicationEventPublisher)
	 */
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

// ----------------------------- AddressParseStrategy Implements ----------------------------------
			
		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getAddressClass(java.lang.String)
		 */
		@SuppressWarnings("unchecked")
		public Class getAddressClass(String type) throws DataNotExistException {
			if (Person.IDENTIFIER.equals(type))
				return Person.class;
			else
				throw new DataNotExistException("类型[ " + type + " ]不支持");
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getAddressTag(java.lang.String, java.io.Serializable)
		 */
		public AddressTag getAddressTag(String type, Serializable serializable)
				throws DataNotExistException {
			getAddressClass(type);
			long id = ((Long)serializable).longValue();

			return getPerson(id);
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getDisplayName(com.lily.dap.entity.common.AddressTag)
		 */
		public String getDisplayName(AddressTag tag) {
			if (tag instanceof Person)
				return ((Person)tag).getName();
			
			return null;
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#getIncludeAddress(com.lily.dap.entity.common.AddressTag)
		 */
		public String getIncludeAddress(AddressTag tag) {
			return null;
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#isCanParseHandlersTag(com.lily.dap.entity.common.AddressTag)
		 */
		public boolean isCanParseHandlersTag(AddressTag tag) {
			return false;
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#isIncludeAddress(com.lily.dap.entity.common.AddressTag)
		 */
		public boolean isIncludeAddress(AddressTag tag) {
			return false;
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#parseHandlersTag(com.lily.dap.entity.common.AddressTag)
		 */
		public List<HandlersTag> parseHandlersTag(AddressTag tag) {
			return null;
		}

		/* （非 Javadoc）
		 * @see com.lily.dap.service.common.impl.AddressParseStrategy#supportAddressType()
		 */
		public String[] supportAddressType() {
			return new String[]{Person.IDENTIFIER};
		}
}
