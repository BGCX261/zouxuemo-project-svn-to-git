/*
 * package com.lily.dap.service.right;
 * class RightHoldManager
 * 
 * 创建日期 2006-3-9
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.service.right;

import java.util.List;

import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightHold;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * 权限拥有者的权限管理接口
 * 
 * @author 邹学模
 *
 */
public interface RightHoldManager extends Manager {
	/**
     * 检索给定类型和ID的RightHold对象信息
     * 
	 * @param type
	 * @param id
	 * @return
	 */
	public RightHold getRightHold(String type, long id) throws DataNotExistException;
	
    /**
     * 给给定的权限拥有者建立一个私有角色
     * 
     * @param hold
     */
    public void providerHoldsRole(RightHold hold); 
    
    /**
     * 去除给定权限拥有者的私有角色，删除其他权限拥有者与该权限拥有者的关系（包含和被包含关系），删除该权限拥有者的所有权限
     * 
     * @param hold
     */
    public void removeHoldsRoles(RightHold hold);
    
    /**
     * 权限拥有者添加包含的权限拥有者
     * 
     * @param hold
     * @param haveHold
     */
    public void addHaveHold(RightHold hold, RightHold haveHold);
    
    /**
     * 去除权限拥有者包含的权限拥有者
     * 
     * @param hold
     * @param haveHold
     */
    public void removeHaveHold(RightHold hold, RightHold haveHold);
    
    /**
     * 添加权限拥有者包含的角色
     * 
     * @param hold
     * @param role
     */
    public void addHaveRole(RightHold hold, Role haveRole);
    
    /**
     * 去除权限拥有者包含的角色
     * 
     * @param hold
     * @param role
     */
    public void removeHaveRole(RightHold hold, Role haveRole);
    
    /**
     * 列出权限拥有者包含的角色（直接包含的公共角色，不包括其包含的权限拥有者包含的角色）
     * 
     * @param hold
     * @return
     */
    public List<Role> listHoldsRoles(RightHold hold);
    
    /**
     * 添加权限拥有者包含的许可
     * 
     * @param hold
     * @param objectCode
     * @param haveOperations
     * @param des
     * @return
     */
    public Permission addHavePermission(RightHold hold, String objectCode, String haveOperations, String des);
    
    /**
     * 删除权限拥有者包含的许可
     * 
     * @param hold
     * @param permission
     */
    public void removeHavePermission(RightHold hold, Permission permission);

    /**
     * 列出权限拥有者包含的许可（直接包含的许可集合，不包括其包含的权限拥有者包含的许可）
     * 
     * @param hold
     * @return
     */
    public List<Permission> listHoldsPermissions(RightHold hold);
}
