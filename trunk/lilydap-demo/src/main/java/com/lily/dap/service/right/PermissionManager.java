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
 * Ȩ�޶���Ȩ�޲��������ӿ�
 * 
 * <br>���ߣ� ��ѧģ
 *
 * <br>���ڣ� 2008-03-14
 *
 * <br>��Ȩ���У��Ͳ��ٺ����
 */
public interface PermissionManager extends Manager {
	/**
	 * ��ȡȨ�޶���
	 * 
	 * @param code Ȩ�޶������
	 * @return RightObject
	 * @throws DataNotExistException Ȩ�޶��󲻴����쳣
	 */
	public RightObject getRightObject(String code) throws DataNotExistException;
	
	/**
	 * ��ȡ����Ȩ�޶�������б�
	 * 
	 * @return List{String}
	 */
	public List<String> getRightObjectClasss();
	
	/**
	 * ��ȡȨ�޷��������Ȩ�޶���
	 * 
	 * @param clazz Ȩ�޷���
	 * @return List{RightObject}
	 */
	public List<RightObject> getRightObjects(String clazz);
	
	/**
	 * ��ȡȨ�޲���
	 * 
	 * @param objectCode Ȩ�޶������
	 * @param operationCode Ȩ�޲�������
	 * @return RightOperation
	 * @throws DataNotExistException Ȩ�޶������Ȩ�޲����������쳣
	 */
	public RightOperation getRightOperation(String objectCode, String operationCode) throws DataNotExistException;
	
	/**
	 * ��ȡȨ�޶����Ӧ��Ȩ�޲���
	 * 
	 * @param objectCode Ȩ�޶������
	 * @return List{RightOperation}
	 */
	public List<RightOperation> getRightOperations(String objectCode);
	
	/**
	 * ��������ID����ɶ���
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Permission getPermission(long id) throws DataNotExistException;
}
