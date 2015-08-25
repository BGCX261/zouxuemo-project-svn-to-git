/**
 * 
 */
package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * <code>PersonManager</code>
 * 人员注册、维护、激活/锁定、停用管理接口
 *
 * @author 邹学模
 * @date 2008-3-12
 */
public interface PersonManager extends Manager {
	/**
	 * 根据用户编号获取人员信息
	 * 
	 * @param username 用户编号
	 * @return Person
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public Person getPerson(String username) throws DataNotExistException;
	
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
	public Person getPerson(long id) throws DataNotExistException;
	
	/**
	 * 检索人员对象集合
	 *
	 * @param queryCondition
	 * @param onlyActivate 是否只检索活动状态的人员（state为STATE_NORMAL的人员）
	 * @return
	 */
	public List<Person> getPersons(Condition queryCondition, boolean onlyActivate);
	
	/**
	 * 检索给定部门或岗位下的包含给定权限角色的人员对象集合
	 *
	 * @param depart_id 要检索的部门ID，如果没有，则检索所有部门
	 * @param post_id 要检索的岗位ID，如果没有，则检索所有岗位
	 * @param haveRole 要包含的角色CODE
	 * @param onlyActivate 是否只检索活动状态的人员（state为STATE_NORMAL的人员）
	 * @return
	 */
	public List<Person> getPersons(long depart_id, long post_id, String haveRole, boolean onlyActivate);
	
	/**
	 * 判断给定人员是否包含给定的许可
	 *
	 * @param person_id
	 * @param haveRole
	 * @return
	 */
	public boolean isPersonHaveRole(long person_id, String haveRole);
	
	/**
	 * 创建新人员信息
	 *
	 * @param person
	 * @return
	 * @throws DataContentRepeatException 用户编号重复、或者用户名称重复抛出异常
	 */
	public Person createPerson(Person  person) throws DataContentRepeatException;
	
	/**
	 * 修改人员信息
	 * <p>只能修改人员姓名、性别、出生日期、手机号码、常用电话号码信息</p>
	 *
	 * @param person
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 * @throws DataContentRepeatException 用户名称重复抛出异常
	 */
	public void modifyPerson(Person person) throws DataNotExistException, DataContentRepeatException;
	
	/**
	 * 删除给定人员
	 *
	 * @param id
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public void removePerson(long id) throws DataNotExistException;
	
	/**
	 * 激活/锁定人员
	 *
	 * @param id
	 * @param enabled true：激活人员、false：锁定人员
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public void enablePerson(long id, boolean enabled) throws DataNotExistException;
	
	/**
	 * 停用人员
	 *
	 * @param id
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public void stopPerson(long id) throws DataNotExistException;
	
	/**
	 * 修改用户密码信息
	 * 
	 * @param username
	 * @param password
	 * @throws DataNotExistException 检索人员不存在抛出异常
	 */
	public void changeUserPassword(String username, String password) throws DataNotExistException;

	/**
	 * 对密码字符串使用系统设置的加密方式进行加密
	 *
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password);
}
