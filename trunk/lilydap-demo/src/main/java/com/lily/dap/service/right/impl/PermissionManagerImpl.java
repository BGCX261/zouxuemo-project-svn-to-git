/**
 * 
 */
package com.lily.dap.service.right.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.right.Permission;
import com.lily.dap.entity.right.RightObject;
import com.lily.dap.entity.right.RightOperation;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.right.PermissionManager;

/**
/**
 * <code>PermissionManagerImpl</code>
 * <p>权限对象、权限操作检索接口实现</p>
 *
 * @author 邹学模
 * @date 2008-3-16
 */
@Service("permissionManager")
public class PermissionManagerImpl extends BaseManager implements PermissionManager {
	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.PermissionManager#getRightObject(java.lang.String)
	 */
	public RightObject getRightObject(String code) throws DataNotExistException {
		return get(RightObject.class, "code", code);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.PermissionManager#getRightObjects(java.lang.String)
	 */
	public List<RightObject> getRightObjects(String clazz) {
		return query(RightObject.class, new Condition().eq("clazz", clazz).asc("id"));
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.PermissionManager#getRightOperation(java.lang.String, java.lang.String)
	 */
	public RightOperation getRightOperation(String objectCode, String operationCode) throws DataNotExistException {
		return get(RightOperation.class, new String[]{"objectCode", "code"}, new Object[]{objectCode, operationCode});
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.PermissionManager#getRightOperations(java.lang.String)
	 */
	public List<RightOperation> getRightOperations(String objectCode) {
		return query(RightOperation.class, new Condition().eq("objectCode", objectCode).asc("id"));
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.right.PermissionManager#getRightObjectClasss()
	 */
	public List<String> getRightObjectClasss() {
		List<String> classList = new ArrayList<String>();
		
		List<RightObject> rightObjects = query(RightObject.class, null);
		for (RightObject ro : rightObjects) {
			String clazz = ro.getClazz();
			if (!classList.contains(clazz))
				classList.add(clazz);
		}
		
		return classList;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.right.PermissionManager#getPermission(long)
	 */
	public Permission getPermission(long id) throws DataNotExistException {
		return get(Permission.class, id);
	}
}
