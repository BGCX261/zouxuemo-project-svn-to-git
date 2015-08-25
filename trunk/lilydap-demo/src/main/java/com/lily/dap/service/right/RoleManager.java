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
 * ��ɫά������ɫ���ά���ӿ�
 * 
 * <br>���ߣ� ��ѧģ
 *
 * <br>���ڣ� 2008-03-14
 *
 * <br>��Ȩ���У��Ͳ��ٺ����
 */
public interface RoleManager extends Manager {
	/**
	 * ���ݽ�ɫID��ȡָ���Ľ�ɫ
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Role getRole(long id) throws DataNotExistException;
	
	/**
	 * ���ݽ�ɫ��Ż�ȡָ���Ľ�ɫ
	 * 
	 * @param code ��ɫ���
	 * @return Role
	 * @throws DataNotExistException ��ɫ�������쳣
	 */
	public Role getRole(String code) throws DataNotExistException;

	/**
	 * �������н�ɫ�б�
	 *
	 * @param onlyPublicRole �Ƿ�ֻ����������ɫ
	 * @return
	 */
	public List<Role> getRoles(boolean onlyPublicRole);
	
	/**
	 * ��ȡ��ɫӵ�еĽ�ɫ�б�
	 * 
	 * @param id ��ɫid
	 * @param onlyPublicRole �Ƿ�ֻ����������ɫ
	 * @param notIncludeChild �Ƿ�ֻ����ֱ��ӵ�еĽ�ɫ���������ӽ�ɫ��ӵ�еĽ�ɫ
	 * @return List{Role}
	 */
	public List<Role> getHaveRoles(long id, boolean onlyPublicRole, boolean notIncludeChild);
	
	/**
	 * ��ȡ����ָ����ɫ�Ľ�ɫ�б��ϼ���ɫ�б�
	 * 
	 * @param id ��ɫid
	 * @param range ������Χ��Role.FLAG_PUBLIC_ROLE-������ɫ��Role.FLAG_PRIVATE_ROLE-˽�н�ɫ��Role.FLAG_ALL-���н�ɫ��
	 * @param notIncludeParent �Ƿ�ֻ����ֱ��ӵ�еĽ�ɫ������������ɫ�ϵ��ϼ���ɫ
	 * @return List{Role}
	 */
	public List<Role> getByHaveRoles(long id, int range, boolean notIncludeParent);
	
	/**
	 * ����һ��������ɫ
	 * 
	 * @param role ��ɫ��Ϣ
	 * @return Role
	 * @throws DataContentRepeatException ��ɫ����ظ������߽�ɫ�����ظ����׳��쳣
	 */
	public Role createPublicRole(Role role) throws DataContentRepeatException;
	
	/**
	 * ����һ��˽�н�ɫ
	 * ˽�н�ɫ�ı��롢������ϵͳ�������
	 * 
	 * @param des ˽�н�ɫ����
	 * @return Role
	 */
	public Role createPrivateRole(String des);
	
	/**
	 * �޸Ľ�ɫ��Ϣ��ֻ���޸Ľ�ɫ�����ƺͽ�ɫ˵��
	 * 
	 * @param role ��ɫ��Ϣ
	 * @throws DataContentRepeatException ��ɫ�����ظ����׳��쳣
	 * @throws DataNotExistException ��ɫ�������쳣
	 */
	public void modifyRole(Role role)  throws DataContentRepeatException, DataNotExistException;
	
	/**
	 * ɾ����ɫ��Ϣ
	 * 
	 * @param id
	 * @param forceRemove �Ƿ�ǿ��ɾ��������ý�ɫ�ѱ�������ɫ������ǿ��ɾ����Ϊtrueʱ����ɾ����������ɫ������ϵ��ǿ��ɾ����Ϊfalseʱ�����׳���ɫ�������쳣
	 * @throws DataNotExistException ��ɫ�������쳣
	 * @throws DataHaveUsedException ��ɫ�Ѿ������ã����߽�ɫ�Ѿ���������ɫ�������׳��쳣
	 * @throws IllegalDataStateException ��ɫΪϵͳ��ɫ��������ɾ�����׳��쳣
	 */
	public void removeRole(long id, boolean forceRemove)  throws DataNotExistException, DataHaveUsedException, IllegalDataStateException;
	
	/**
	 * �����еĽ�ɫ��ӽ�ɫ
	 * 
	 * @param id ���еĽ�ɫid
	 * @param have_role_id ����ӵĽ�ɫid
	 * @throws DataNotExistException ��ɫ�����߱�������ɫ�������쳣
	 * @throws DataHaveIncludeException ��ɫ�Ѿ������˱������Ľ�ɫ���׳��쳣
	 * @throws IllegalHierarchicalException �������Ľ�ɫ�Ѿ������˱���ɫ����ɫ������ϵ���� �׳��쳣
	 */
	public void addHaveRole(long id, long have_role_id) throws DataNotExistException, DataHaveIncludeException, IllegalHierarchicalException;
    
	/**
	 * ȥ����ɫӵ�е��ӽ�ɫ
	 * 
	 * @param id ��ɫid
	 * @param have_role_id ӵ�еĽ�ɫid
     * @throws DataNotExistException ��ɫ�������쳣
     * @throws DataNotIncludeException ��ɫδ�����������Ľ�ɫ���׳��쳣
     */
    public void removeHaveRole(long id, long have_role_id) throws DataNotExistException, DataNotIncludeException;
	
	/**
	 * ��ȡ��ɫӵ�е�����б�
	 * 
	 * @param id ��ɫid
	 * @param notIncludeChild �Ƿ�ֻ����ֱ��ӵ�еĽ�ɫ���������ӽ�ɫ��ӵ�еĽ�ɫ
	 * @param showPermissionFullName �Ƿ���ʾ��ɵľ����������磺ϵͳ���󣭶���д����д�뵽�����Ϣ��fullName�ֶ�
	 * @return List{Permission}
	 */
	public List<Permission> getHavePermissions(long id, boolean notIncludeChild, boolean showPermissionFullName);
	
	/**
	 * �����еĽ�ɫ������
	 * 
	 * @param permission �����Ϣ
	 * @return Permission
     * @throws DataNotExistException ��ɫ�������쳣
	 */
	public Permission addHavePermission(Permission permission)  throws DataNotExistException;
	
	/**
	 * ɾ��ĳ����ɣ�����֮�ӹ����Ľ�ɫ��ȥ��
	 * 
	 * @param have_permission_id ���id
	 * @throws DataNotExistException ��ɲ������쳣
	 */
	public void removeHavePermission(long have_permission_id) throws DataNotExistException;
}	
