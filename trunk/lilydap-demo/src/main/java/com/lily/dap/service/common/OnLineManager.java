/**
 * 
 */
package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.entity.organize.Person;

/**
 * 在线用户管理服务接口
 * 
 * @author zouxuemo
 *
 */
public interface OnLineManager {
	/**
	 * 用户登录
	 *  
	 * @param ip
	 * @param sessionId
	 * @param person
	 */
	public void loginUser(String ip, String sessionId, Person person);
	
	/**
	 * 用户注销
	 * 
	 * @param sessionId
	 */
	public void logoutUser(String sessionId);

	/**
	 * 获取给定session id的在线用户，如果未找到返回null
	 * 
	 * @param sessionId
	 * @return
	 */
	public OnLineInfo getOnlineUser(String sessionId);
	
	/**
	 * 获取所有在线用户信息列表
	 * 
	 * @return
	 */
	public List<OnLineInfo> getOnlineUsers();
	
	/**
	 * 获取当前所有登录的人员列表（如果有重复登录人员则只列出一个人员）
	 * 
	 * @return
	 */
	public List<Person> getOnlinePersons();
}
