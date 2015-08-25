package com.lily.dap.service.common.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.Log;
import com.lily.dap.service.common.LogManager;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.util.EntityOrderUtil;

/**
 * 日志管理实现
 * 
 * @author 邹学模
 *
 */
@Service("logManager")
public class LogManagerImpl extends BaseManager implements LogManager {
	private EntityOrderUtil entityOrderUtil;
	
	@PostConstruct
	public void init() {
	    	entityOrderUtil = new EntityOrderUtil(dao);
	 }
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.LogManager#get(long)
	 */
	public Log get(long id) throws DataNotExistException {
		return dao.get(Log.class, id);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.LogManager#gets(com.lily.dap.dao.QueryCondition)
	 */
	public List<Log> gets(Condition cond) {
		if (cond.getOrders().size() == 0) {
			cond.desc("logTime");
		}
		
		return dao.query(Log.class, cond);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.LogManager#log(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Log log(String username, String name, String ip, String operation, String des) {
		Log log = new Log();
		log.setLogUser(username);
		log.setLogName(name);
		log.setLogIP(ip);
		log.setOperation(operation);
		log.setDes(des);

		return saveOrUpdate(log);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.LogManager#next(long, com.lily.dap.dao.QueryCondition)
	 */
	public Log next(long id, Condition cond) throws DataNotExistException {
		return entityOrderUtil.offsetOne(Log.class, id, cond.desc("logTime"), true);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.LogManager#prev(long, com.lily.dap.dao.QueryCondition)
	 */
	public Log prev(long id, Condition cond) throws DataNotExistException {
		return entityOrderUtil.offsetOne(Log.class, id, cond.desc("logTime"), false);
	}
}
