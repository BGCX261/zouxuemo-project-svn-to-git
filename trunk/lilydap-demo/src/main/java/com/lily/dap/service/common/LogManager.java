package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.Log;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * ��־����ӿ�
 * 
 * @author ��ѧģ
 *
 */
public interface LogManager extends Manager {
	/**
	 * ��¼��־��Ϣ
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
	 * ����������������־��Ϣ��Ĭ�ϰ���ʱ�䵹������
	 * 
	 * @param cond
	 * @return
	 */
	public List<Log> gets(Condition cond);
	
	/**
	 * ��������ID����־��¼
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException ���δ�ҵ����׳��쳣
	 */
	public Log get(long id) throws DataNotExistException;
	
	/**
	 * ��������ID��־����һ����־��¼��Ĭ�ϰ���ʱ������
	 * 
	 * @param id
	 * @param cond ���Ʋ�ѯ��Χ������˳��
	 * @return 
	 * @throws DataNotExistException �����ǰ��־�Ѿ���ͷ���׳��쳣
	 */
	public Log prev(long id, Condition cond) throws DataNotExistException;
	
	/**
	 * ��������ID��־����һ����־��¼��Ĭ�ϰ���ʱ������
	 * 
	 * @param id
	 * @param cond ���Ʋ�ѯ��Χ������˳��
	 * @return 
	 * @throws DataNotExistException �����ǰ��־�Ѿ���β���׳��쳣
	 */
	public Log next(long id, Condition cond) throws DataNotExistException;
}
