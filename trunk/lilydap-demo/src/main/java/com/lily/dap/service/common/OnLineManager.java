/**
 * 
 */
package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.entity.organize.Person;

/**
 * �����û��������ӿ�
 * 
 * @author zouxuemo
 *
 */
public interface OnLineManager {
	/**
	 * �û���¼
	 *  
	 * @param ip
	 * @param sessionId
	 * @param person
	 */
	public void loginUser(String ip, String sessionId, Person person);
	
	/**
	 * �û�ע��
	 * 
	 * @param sessionId
	 */
	public void logoutUser(String sessionId);

	/**
	 * ��ȡ����session id�������û������δ�ҵ�����null
	 * 
	 * @param sessionId
	 * @return
	 */
	public OnLineInfo getOnlineUser(String sessionId);
	
	/**
	 * ��ȡ���������û���Ϣ�б�
	 * 
	 * @return
	 */
	public List<OnLineInfo> getOnlineUsers();
	
	/**
	 * ��ȡ��ǰ���е�¼����Ա�б�������ظ���¼��Ա��ֻ�г�һ����Ա��
	 * 
	 * @return
	 */
	public List<Person> getOnlinePersons();
}
