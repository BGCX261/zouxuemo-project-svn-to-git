package com.lily.dap.web.util;

import java.util.Map;

public interface ConditionParseCallback {
	/**
	 * ���ɲ�ѯ����ǰ��Ԥ������ǰ̨����Ĳ��������޸�
	 * 
	 * @param param ǰ̨����Ĳ���
	 */
	public void preProcessCondition(Map<String, String> param);
}
