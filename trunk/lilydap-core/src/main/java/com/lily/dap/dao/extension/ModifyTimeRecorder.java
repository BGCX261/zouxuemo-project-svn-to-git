/**
 * 
 */
package com.lily.dap.dao.extension;

import java.util.Date;

/**
 * ����ʱ��¼�޸�ʱ��ӿڣ����ʵ����ʵ���˸ýӿڣ����ڱ���ʱ�Զ����ýӿڷ������汾���޸ĵ�ʱ��
 * 
 * @author zouxuemo
 *
 */
public interface ModifyTimeRecorder {
	/**
	 * ��ʵ�����������޸�ʱ���ֶ�Ϊ��ǰʱ��
	 * 
	 * @param modifyTime ��ǰ�޸�ʱ��
	 */
	public void recordModifyTime(Date modifyTime);
}
