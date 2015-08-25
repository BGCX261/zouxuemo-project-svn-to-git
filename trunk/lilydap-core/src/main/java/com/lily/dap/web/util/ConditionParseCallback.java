package com.lily.dap.web.util;

import java.util.Map;

public interface ConditionParseCallback {
	/**
	 * 生成查询条件前的预处理，对前台传入的参数进行修改
	 * 
	 * @param param 前台传入的参数
	 */
	public void preProcessCondition(Map<String, String> param);
}
