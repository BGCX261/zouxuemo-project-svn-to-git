/*
 * package com.lily.dap.service.right;
 * class RightHoldManager
 * 
 * �������� 2006-3-9
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.service.right;

import java.util.List;

import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightHold;
import com.lily.dap.entity.right.Role;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * Ȩ��ӵ���ߵ�Ȩ�޹���ӿ�
 * 
 * @author ��ѧģ
 *
 */
public interface RightHoldManager extends Manager {
	/**
     * �����������ͺ�ID��RightHold������Ϣ
     * 
	 * @param type
	 * @param id
	 * @return
	 */
	public RightHold getRightHold(String type, long id) throws DataNotExistException;
	
    /**
     * ��������Ȩ��ӵ���߽���һ��˽�н�ɫ
     * 
     * @param hold
     */
    public void providerHoldsRole(RightHold hold); 
    
    /**
     * ȥ������Ȩ��ӵ���ߵ�˽�н�ɫ��ɾ������Ȩ��ӵ�������Ȩ��ӵ���ߵĹ�ϵ�������ͱ�������ϵ����ɾ����Ȩ��ӵ���ߵ�����Ȩ��
     * 
     * @param hold
     */
    public void removeHoldsRoles(RightHold hold);
    
    /**
     * Ȩ��ӵ������Ӱ�����Ȩ��ӵ����
     * 
     * @param hold
     * @param haveHold
     */
    public void addHaveHold(RightHold hold, RightHold haveHold);
    
    /**
     * ȥ��Ȩ��ӵ���߰�����Ȩ��ӵ����
     * 
     * @param hold
     * @param haveHold
     */
    public void removeHaveHold(RightHold hold, RightHold haveHold);
    
    /**
     * ���Ȩ��ӵ���߰����Ľ�ɫ
     * 
     * @param hold
     * @param role
     */
    public void addHaveRole(RightHold hold, Role haveRole);
    
    /**
     * ȥ��Ȩ��ӵ���߰����Ľ�ɫ
     * 
     * @param hold
     * @param role
     */
    public void removeHaveRole(RightHold hold, Role haveRole);
    
    /**
     * �г�Ȩ��ӵ���߰����Ľ�ɫ��ֱ�Ӱ����Ĺ�����ɫ���������������Ȩ��ӵ���߰����Ľ�ɫ��
     * 
     * @param hold
     * @return
     */
    public List<Role> listHoldsRoles(RightHold hold);
    
    /**
     * ���Ȩ��ӵ���߰��������
     * 
     * @param hold
     * @param objectCode
     * @param haveOperations
     * @param des
     * @return
     */
    public Permission addHavePermission(RightHold hold, String objectCode, String haveOperations, String des);
    
    /**
     * ɾ��Ȩ��ӵ���߰��������
     * 
     * @param hold
     * @param permission
     */
    public void removeHavePermission(RightHold hold, Permission permission);

    /**
     * �г�Ȩ��ӵ���߰�������ɣ�ֱ�Ӱ�������ɼ��ϣ��������������Ȩ��ӵ���߰�������ɣ�
     * 
     * @param hold
     * @return
     */
    public List<Permission> listHoldsPermissions(RightHold hold);
}
