package com.lily.dap.service.right;

import java.util.List;

import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataHaveIncludeException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.DataNotIncludeException;
import com.lily.dap.service.exception.IllegalDataStateException;
import com.lily.dap.service.exception.IllegalHierarchicalException;
/**
 * 
 * RoleManager.java
 * 角色维护、角色许可维护接口
 * 
 * <br>作者： 邹学模
 *
 * <br>日期： 2008-03-14
 *
 * <br>版权所有：淄博百合软件
 */
public interface RoleManager extends Manager {
	/**
	 * 根据角色ID获取指定的角色
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Role getRole(long id) throws DataNotExistException;
	
	/**
	 * 根据角色编号获取指定的角色
	 * 
	 * @param code 角色编号
	 * @return Role
	 * @throws DataNotExistException 角色不存在异常
	 */
	public Role getRole(String code) throws DataNotExistException;

	/**
	 * 检索所有角色列表
	 *
	 * @param onlyPublicRole 是否只检索公共角色
	 * @return
	 */
	public List<Role> getRoles(boolean onlyPublicRole);
	
	/**
	 * 获取角色拥有的角色列表
	 * 
	 * @param id 角色id
	 * @param onlyPublicRole 是否只检索公共角色
	 * @param notIncludeChild 是否只检索直接拥有的角色，不包括子角色下拥有的角色
	 * @return List{Role}
	 */
	public List<Role> getHaveRoles(long id, boolean onlyPublicRole, boolean notIncludeChild);
	
	/**
	 * 获取包含指定角色的角色列表（上级角色列表）
	 * 
	 * @param id 角色id
	 * @param range 检索范围（Role.FLAG_PUBLIC_ROLE-公共角色、Role.FLAG_PRIVATE_ROLE-私有角色、Role.FLAG_ALL-所有角色）
	 * @param notIncludeParent 是否只检索直接拥有的角色，不包括父角色上的上级角色
	 * @return List{Role}
	 */
	public List<Role> getByHaveRoles(long id, int range, boolean notIncludeParent);
	
	/**
	 * 创建一个公共角色
	 * 
	 * @param role 角色信息
	 * @return Role
	 * @throws DataContentRepeatException 角色编号重复，或者角色名称重复，抛出异常
	 */
	public Role createPublicRole(Role role) throws DataContentRepeatException;
	
	/**
	 * 创建一个私有角色
	 * 私有角色的编码、名称由系统随机生成
	 * 
	 * @param des 私有角色描述
	 * @return Role
	 */
	public Role createPrivateRole(String des);
	
	/**
	 * 修改角色信息，只能修改角色的名称和角色说明
	 * 
	 * @param role 角色信息
	 * @throws DataContentRepeatException 角色名称重复，抛出异常
	 * @throws DataNotExistException 角色不存在异常
	 */
	public void modifyRole(Role role)  throws DataContentRepeatException, DataNotExistException;
	
	/**
	 * 删除角色信息
	 * 
	 * @param id
	 * @param forceRemove 是否强制删除，如果该角色已被其他角色包含，强制删除设为true时，则将删除与其他角色包含关系，强制删除设为false时，则抛出角色被引用异常
	 * @throws DataNotExistException 角色不存在异常
	 * @throws DataHaveUsedException 角色已经被引用，或者角色已经被其他角色包含，抛出异常
	 * @throws IllegalDataStateException 角色为系统角色，不允许删除，抛出异常
	 */
	public void removeRole(long id, boolean forceRemove)  throws DataNotExistException, DataHaveUsedException, IllegalDataStateException;
	
	/**
	 * 给现有的角色添加角色
	 * 
	 * @param id 已有的角色id
	 * @param have_role_id 被添加的角色id
	 * @throws DataNotExistException 角色、或者被包含角色不存在异常
	 * @throws DataHaveIncludeException 角色已经包含了被包含的角色，抛出异常
	 * @throws IllegalHierarchicalException 被包含的角色已经包含了本角色，角色包含关系错误， 抛出异常
	 */
	public void addHaveRole(long id, long have_role_id) throws DataNotExistException, DataHaveIncludeException, IllegalHierarchicalException;
    
	/**
	 * 去除角色拥有的子角色
	 * 
	 * @param id 角色id
	 * @param have_role_id 拥有的角色id
     * @throws DataNotExistException 角色不存在异常
     * @throws DataNotIncludeException 角色未包含被包含的角色，抛出异常
     */
    public void removeHaveRole(long id, long have_role_id) throws DataNotExistException, DataNotIncludeException;
	
	/**
	 * 获取角色拥有的许可列表
	 * 
	 * @param id 角色id
	 * @param notIncludeChild 是否只检索直接拥有的角色，不包括子角色下拥有的角色
	 * @param showPermissionFullName 是否显示许可的具体描述（如：系统对象－读，写）并写入到许可信息的fullName字段
	 * @return List{Permission}
	 */
	public List<Permission> getHavePermissions(long id, boolean notIncludeChild, boolean showPermissionFullName);
	
	/**
	 * 给现有的角色添加许可
	 * 
	 * @param permission 许可信息
	 * @return Permission
     * @throws DataNotExistException 角色不存在异常
	 */
	public Permission addHavePermission(Permission permission)  throws DataNotExistException;
	
	/**
	 * 删除某个许可，并将之从关联的角色中去除
	 * 
	 * @param have_permission_id 许可id
	 * @throws DataNotExistException 许可不存在异常
	 */
	public void removeHavePermission(long have_permission_id) throws DataNotExistException;
}	
