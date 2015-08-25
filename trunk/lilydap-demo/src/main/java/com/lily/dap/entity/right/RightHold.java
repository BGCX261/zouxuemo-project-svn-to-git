/*
 * package com.lily.dap.model.organize;
 * class RightHold
 * 
 * �������� 2006-3-3
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.entity.right;

/**
 * Ȩ��ӵ���߽ӿڣ��ܹ�ӵ��˽�н�ɫ��ʵ�ֱ��ӿڵ�ʵ�����ܹ��������ָ��Ȩ�޺���ɲ���
 * 
 * @author zouxuemo
 *
 */
public interface RightHold {
    /**
     * ��ȡʵ�����˽�н�ɫ����Code
     * 
     * @return
     */
    public String getPrivateRoleCode();
    
    /**
     * ����ʵ�����˽�н�ɫ����Code
     * 
     * @param roleCode
     */
    public void setPrivateRoleCode(String roleCode);

    /**
     * ��ȡʵ����ID
     * 
     * @return
     */
    public long getId();
    
    /**
     * ��ȡʵ���������
     * 
     * @return
     */
    public String getName();
}
