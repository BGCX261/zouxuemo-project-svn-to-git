package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataHaveIncludeException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.DataNotIncludeException;

/**
 * <code>DepartmentManager</code>
 * <p>部门/岗位管理接口</p>
 *
 * @author 邹学模
 * @date 2008-3-19
 */
public interface DepartmentManager extends Manager {
	/**
     * 检索根部门信息
     * 
     * @return 返回检索到的部门信息
     * @throws DataNotExistException 检索数据不存在异常
     */
    public Department getRootDepartment() throws DataNotExistException;

	/**
	 * 判断给定的部门是否跟部门
	 * 
	 * @param department
	 * @return
	 */
	public boolean isRootDepartment(Department department);
	
    /**
     * 检索指定ID的部门
     *
     * @param id
     * @return
     * @throws DataNotExistException
     */
    public Department getDepartment(long id) throws DataNotExistException;

	/**
	 * 检索指定level的部门
	 *
	 * @param level
	 * @return
	 * @throws DataNotExistException
	 */
	public Department getDepartment(String level)
			throws DataNotExistException;
    
//	/**
//	 * 根据部门类型获取部门信息列表
//	 * 
//	 * @param type 部门类型
//	 * @return List{Department}
//	 */
//	public List<Department> getDepartments(int type);
    
	/**
	 * 检索指定部门下的下级部门集合
	 *
	 * @param parent_id 要检索的父部门ID
	 * @param notIncludeChild 是否不检索子部门的下级部门，为true，则只检索第一级下级部门集合；为false，则检索所有下级部门集合列表
	 * @return
	 */
	public List<Department> getChilds(long parent_id, boolean notIncludeChild);
	
	/**
	 * 获取给定部门的全路径名（格式如：xxx部门－xxx部门－...－xxx部门）
	 *
	 * @param id
	 * @return
	 */
	public String getDepartmentFullName(long id);
	
	/**
	 * 检索指定ID的岗位
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Post getPost(long id) throws DataNotExistException;

	/**
	 * 检索指定code的岗位
	 *
	 * @param code
	 * @return
	 * @throws DataNotExistException
	 */
	public Post getPost(String code) throws DataNotExistException;
	
	/**
	 * 获取部门下或包括其下级部门的所有岗位
	 * 
	 * @param depart_id 部门id
	 * @param notIncludeChild 是否不检索其子部门的岗位，为true，则只检索本部门集合；为false，则检索所有下级部门集合列表
	 * @return List{Post}
	 */
	public List<Post> getPosts(long depart_id, boolean notIncludeChild);
	
//	/**
//	 * 初始化组织机构，并构建根部门
//	 *
//	 * @param clazz 根部门分类
//	 * @param code 根部门编号
//	 * @param name 根部门名称
//	 * @param des 根部门说明
//	 * @return
//	 */
//	public Department initOrganize(int clazz, String code, String name, String des);
	
	/**
	 * 创建部门信息
	 *
	 * @param department
	 * @return
	 * @throws DataNotExistException 上级部门不存在，抛出异常
	 */
	public Department createDepartment(Department department) throws DataNotExistException;
	
	/**
	 * 修改部门信息（修改：code、name、clazz、des）
	 *
	 * @param department
	 * @throws DataNotExistException 要修改的部门不存在抛出异常
	 */
	public void modifyDepartment(Department department) throws DataNotExistException;
	
	/**
	 * 删除部门，如果要求强制删除，则同时删除其下级部门、岗位、以及岗位与人员的关联
	 *
	 * @param id
	 * @param forceRemove 是否强制删除，如果部门下有子部门和岗位，设置为true，则同时删除下级部门、岗位、以及岗位与人员的关联；为false，则抛出异常
	 * @throws DataNotExistException 要删除的部门不存在抛出异常
	 * @throws DataHaveUsedException 部门下包含子部门或者岗位，抛出异常
	 */
	public void removeDepartment(long id,boolean forceRemove) throws DataNotExistException,DataHaveUsedException;
	
	/**
	 * 调整部门排序顺序（同一父部门下）
	 * 
	 * @param id 要排序的部门ID
	 * @param order 排序方向（如果给定负值，则向前排序；否则向后排序。根据数字值，调整调整的步值。直至到头或者到尾）
	 * @throws DataNotExistException 如果给定ID不存在，则抛出异常
	 */
	public void adjustDepartmentOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * 创建岗位信息
	 *
	 * @param post
	 * @return
	 * @throws DataNotExistException 岗位所属部门不存在，抛出异常
	 */
	public Post createPost(Post post) throws DataNotExistException;
	
	/**
	 * 修改岗位信息（修改：code、name、clazz、des）
	 *
	 * @param post
	 * @throws DataNotExistException 要修改的岗位不存在抛出异常
	 */
	public void modifyPost(Post post) throws DataNotExistException;
	
	/**
	 * 删除岗位，如果要求强制删除，则同时删除其关联的人员
	 *
	 * @param id
	 * @param forceRemove 是否强制删除，如果岗位下有人员，设置为true，则同时去除岗位人员的关联；为false，则抛出异常
	 * @throws DataNotExistException 要删除的岗位不存在抛出异常
	 * @throws DataHaveUsedException 岗位下包含人员，抛出异常
	 */
	public void removePost(long id,boolean forceRemove) throws DataNotExistException,DataHaveUsedException;
	
	/**
	 * 调整岗位排序顺序（同一部门下）
	 * 
	 * @param id 要排序的岗位ID
	 * @param order 排序方向（如果给定负值，则向前排序；否则向后排序。根据数字值，调整调整的步值。直至到头或者到尾）
	 * @throws DataNotExistException 如果给定ID不存在，则抛出异常
	 */
	public void adjustPostOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * 添加岗位人员
	 * 
	 * @param post 岗位
	 * @param person 人员
	 * @throws DataNotExistException 岗位或人员不存在抛出异常
	 * @throws DataHaveIncludeException 岗位已经包含人员，抛出异常
	 */
	public void addPostHavePerson(Post post, Person person) throws DataNotExistException, DataHaveIncludeException;
	
	/**
	 * 移除岗位人员
	 * 
	 * @param post 岗位
	 * @param person 人员
	 * @throws DataNotExistException 岗位或人员不存在抛出异常
	 * @throws DataHaveIncludeException 岗位不包含人员，抛出异常
	 */
	public void removePostHavePerson(Post post, Person person) throws DataNotExistException, DataNotIncludeException;
	
	/**
	 * 获取人员对应的岗位信息列表
	 * 
	 * @param person_username
	 * @return
	 */
	public List<Post> getPostsByPerson(String person_username);
	
	/**
	 * 获取人员对应的部门信息列表
	 *
	 * @param person_username
	 * @return
	 */
	public List<Department> getDepartmentsByPerson(String person_username);
	
	/**
	 * 检索指定部门及其下级部门、或者指定岗位下所有人员
	 * 
	 * @param depart_id 部门id
	 * @param post_id 岗位id
	 * @param notIncludeChild 针对检索部门人员时，是否不检索子部门的人员，为true，则只检索当前部门人员；为false，则检索所有下级部门人员列表
	 * @param onlyActivate 是否只检索活动状态的人员（state为STATE_NORMAL的人员）
	 * @return List{Person}
	 */
	public List<Person> getPersons(long depart_id, long post_id, boolean notIncludeChild, boolean onlyActivate);
}
