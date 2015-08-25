package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * <code>OrganizeCacheManager</code>
 * <p>组织机构缓存管理接口</p>
 *
 * @author 邹学模
 * @date 2008-3-26
 */
public interface OrganizeCacheManager {
	/**
	 * 检索根部门信息
	 * 
	 * @return 返回检索到的部门信息
	 * @throws DataNotExistException 检索数据不存在异常
	 */
	public abstract Department getRootDepartment() throws DataNotExistException;

	/**
	 * 判断给定的部门是否跟部门
	 * 
	 * @param department
	 * @return
	 */
	public abstract boolean isRootDepartment(Department department);
	
	/**
	 * 检索指定ID的部门
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Department getDepartment(long id)
			throws DataNotExistException;

	/**
	 * 检索指定level的部门
	 *
	 * @param level
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Department getDepartment(String level)
			throws DataNotExistException;

	/**
	 * 检索指定部门下的下级部门集合
	 *
	 * @param parent_id 要检索的父部门ID
	 * @param notIncludeChild 是否不检索子部门的下级部门，为true，则只检索第一级下级部门集合；为false，则检索所有下级部门集合列表
	 * @return
	 */
	public abstract List<Department> getChilds(long parent_id,
			boolean notIncludeChild);

	/**
	 * 检索指定ID的岗位
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Post getPost(long id) throws DataNotExistException;

	/**
	 * 检索指定code的岗位
	 *
	 * @param code
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Post getPost(String code) throws DataNotExistException;

	/**
	 * 获取部门下或包括其下级部门的所有岗位
	 * 
	 * @param depart_id 部门id
	 * @param notIncludeChild 是否不检索其子部门的岗位，为true，则只检索本部门集合；为false，则检索所有下级部门集合列表
	 * @return List{Post}
	 */
	public abstract List<Post> getPosts(long depart_id, boolean notIncludeChild);

	/**
	 * 获取人员对应的岗位信息列表
	 * 
	 * @param person_username
	 * @return
	 */
	public abstract List<Post> getPostsByPerson(String person_username);

	/**
	 * 获取人员对应的部门信息列表
	 *
	 * @param person_username
	 * @return
	 */
	public abstract List<Department> getDepartmentsByPerson(String person_username);

	/**
	 * 检索指定部门及其下级部门、或者指定岗位下所有人员
	 * 
	 * @param depart_id 部门id
	 * @param post_id 岗位id
	 * @param notIncludeChild 针对检索部门人员时，是否不检索子部门的人员，为true，则只检索当前部门人员；为false，则检索所有下级部门人员列表
	 * @param onlyActivate 是否只检索活动状态的人员（state为STATE_NORMAL的人员）
	 * @return List{Person}
	 */
	public abstract List<Person> getPersons(long depart_id, long post_id,
			boolean notIncludeChild, boolean onlyActivate);

	/**
	 * 根据用户编号获取人员信息
	 * 
	 * @param username
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Person getPerson(String username)
			throws DataNotExistException;
	
	/**
	 * 根据用户名称获取人员信息
	 *
	 * @param name
	 * @return
	 * @throws DataNotExistException
	 */
	public Person getPersonByName(String name) throws DataNotExistException;

	/**
	 * 根据ID获取人员信息
	 *
	 * @param id 人员ID
	 * @return
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public abstract Person getPerson(long id) throws DataNotExistException;

	/**
	 * 检索人员对象集合
	 *
	 * @param username 用户编号＝给定值
	 * @param name 人员名称like给定值
	 * @param sex 性别＝给定值
	 * @param mobile 绑定手机＝给定值
	 * @param onlyActivate 是否只检索活动状态的人员（state为STATE_NORMAL的人员）
	 * @return
	 */
	public abstract List<Person> getPersons(String username, String name,
			String sex, String mobile, boolean onlyActivate);

}