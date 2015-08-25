package com.lily.dap.web.action;

import org.apache.struts2.convention.annotation.Result;

@Result(name = "success", location = "/WEB-INF/page/layout.jsp")
public class IndexAction extends BaseAction {
	public String execute() {
		return SUCCESS;
	}
}
