/**
 * 
 */
package com.lily.dap.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.common.OnLineInfo;
import com.lily.dap.service.common.OnLineManager;

/**
 * 在线人员服务类实现
 * 
 * @author zouxuemo
 *
 */
@Service("onlineManager")
public class OnLineManagerImpl implements OnLineManager {
	private List<OnLineInfo> onlineInfoList = new ArrayList<OnLineInfo>();
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.OnLineManager#getOnlineUsers()
	 */
	public List<OnLineInfo> getOnlineUsers() {
		return onlineInfoList;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.OnLineManager#loginUser(java.lang.String, java.lang.String, com.lily.dap.entity.organize.Person)
	 */
	public void loginUser(String ip, String sessionId, Person person) {
		if (getOnlineUser(sessionId) != null)
			return;
		
		OnLineInfo onlineInfo = new OnLineInfo(ip, sessionId, person);
		
		onlineInfoList.add(onlineInfo);
	}
 
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.OnLineManager#logoutUser(java.lang.String)
	 */
	public void logoutUser(String sessionId) {
		OnLineInfo onlineInfo = getOnlineUser(sessionId);
		if (onlineInfo != null)
			onlineInfoList.remove(onlineInfo);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.OnLineManager#getOnlineUser(java.lang.String)
	 */
	public OnLineInfo getOnlineUser(String sessionId) {
		for (OnLineInfo onlineInfo : onlineInfoList) {
			if (sessionId.equals(onlineInfo.getSessionId()))
				return onlineInfo;
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.OnLineManager#getOnlinePersons()
	 */
	public List<Person> getOnlinePersons() {
		List<Person> result = new ArrayList<Person>();
		
		for (OnLineInfo onlineInfo : onlineInfoList) {
			if (!result.contains(onlineInfo.getPerson()))
				result.add(onlineInfo.getPerson());
		}
		
		return result;
	}
}
