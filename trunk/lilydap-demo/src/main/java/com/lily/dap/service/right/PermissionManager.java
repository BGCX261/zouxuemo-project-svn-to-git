package com.lily.dap.service.right;

import java.util.List;

import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightObject;
import com.lily.dap.entity.right.RightOperation;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;
/**
 * 
 * PermissionManager.java
 * 权限对象、权限操作检索接口
 * 
 * <br>作者： 邹学模
 *
 * <br>日期： 2008-03-14
 *
 * <br>版权所有：淄博百合软件
 */
public interface PermissionManager extends Manager {
	/**
	 * 获取权限对象
	 * 
	 * @param code 权限对象代码
	 * @return RightObject
	 * @throws DataNotExistException 权限对象不存在异常
	 */
	public RightObject getRightObject(String code) throws DataNotExistException;
	
	/**
	 * 获取所有权限对象分类列表
	 * 
	 * @return List{String}
	 */
	public List<String> getRightObjectClasss();
	
	/**
	 * 获取权限分组的所有权限对象
	 * 
	 * @param clazz 权限分组
	 * @return List{RightObject}
	 */
	public List<RightObject> getRightObjects(String clazz);
	
	/**
	 * 获取权限操作
	 * 
	 * @param objectCode 权限对象代码
	 * @param operationCode 权限操作代码
	 * @return RightOperation
	 * @throws DataNotExistException 权限对象或者权限操作不存在异常
	 */
	public RightOperation getRightOperation(String objectCode, String operationCode) throws DataNotExistException;
	
	/**
	 * 获取权限对象对应的权限操作
	 * 
	 * @param objectCode 权限对象代码
	 * @return List{RightOperation}
	 */
	public List<RightOperation> getRightOperations(String objectCode);
	
	/**
	 * 检索给定ID的许可对象
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Permission getPermission(long id) throws DataNotExistException;
}
