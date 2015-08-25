/*
 * package com.lily.dap.model.organize;
 * class RightHold
 * 
 * 创建日期 2006-3-3
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.entity.right;

/**
 * 权限拥有者接口，能够拥有私有角色，实现本接口的实体类能够对其进行指派权限和许可操作
 * 
 * @author zouxuemo
 *
 */
public interface RightHold {
    /**
     * 获取实体类的私有角色对象Code
     * 
     * @return
     */
    public String getPrivateRoleCode();
    
    /**
     * 设置实体类的私有角色对象Code
     * 
     * @param roleCode
     */
    public void setPrivateRoleCode(String roleCode);

    /**
     * 获取实体类ID
     * 
     * @return
     */
    public long getId();
    
    /**
     * 获取实体类的名称
     * 
     * @return
     */
    public String getName();
}
