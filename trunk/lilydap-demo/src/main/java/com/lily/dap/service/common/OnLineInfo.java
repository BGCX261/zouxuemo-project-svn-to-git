package com.lily.dap.service.common;

import com.lily.dap.entity.organize.Person;

public class OnLineInfo {
	private String ip;
	
	private String sessionId;
	
	private Person person;

	public OnLineInfo(String ip, String sessionId, Person person) {
		this.ip = ip;
		this.sessionId = sessionId;
		this.person = person;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
