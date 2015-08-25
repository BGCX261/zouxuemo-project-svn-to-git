package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.Log;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * 日志管理接口
 * 
 * @author 邹学模
 *
 */
public interface LogManager extends Manager {
	/**
	 * 记录日志信息
	 * 
	 * @param username
	 * @param name
	 * @param ip
	 * @param operation
	 * @param des
	 * @return
	 */
	public Log log(String username, String name, String ip, String operation, String des);

	/**
	 * 检索给定条件的日志信息（默认按照时间倒序排序）
	 * 
	 * @param cond
	 * @return
	 */
	public List<Log> gets(Condition cond);
	
	/**
	 * 检索给定ID的日志记录
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException 如果未找到，抛出异常
	 */
	public Log get(long id) throws DataNotExistException;
	
	/**
	 * 检索给定ID日志的上一条日志记录（默认按照时间排序）
	 * 
	 * @param id
	 * @param cond 限制查询范围和排序顺序
	 * @return 
	 * @throws DataNotExistException 如果当前日志已经到头，抛出异常
	 */
	public Log prev(long id, Condition cond) throws DataNotExistException;
	
	/**
	 * 检索给定ID日志的下一条日志记录（默认按照时间排序）
	 * 
	 * @param id
	 * @param cond 限制查询范围和排序顺序
	 * @return 
	 * @throws DataNotExistException 如果当前日志已经到尾，抛出异常
	 */
	public Log next(long id, Condition cond) throws DataNotExistException;
}
