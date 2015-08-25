package com.lily.dap.service.system;

import java.util.Date;

import com.lily.dap.entity.system.SystemSetting;

/**
 * @author zouxuemo
 *
 */
public interface SettingManager {
	/**
	 * ��ȡ����ģ�����name��ϵͳ��������
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	public SystemSetting getSystemSetting(String model, String name); 
	/**
	 * ��ȡ����ģ��ĸ������õ�ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public String getSystemSettingValue(String model, String name, String defaultvalue);
	
	/**
	 * ��ȡ����ģ��ĸ������õĳ���ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public long getSystemSettingLongValue(String model, String name, long defaultvalue);
	
	/**
	 * ��ȡ����ģ��ĸ������õ�����ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public int getSystemSettingIntValue(String model, String name, int defaultvalue);
	
	/**
	 * ��ȡ����ģ��ĸ������õĸ���ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public double getSystemSettingDoubleValue(String model, String name, double defaultvalue);
	
	/**
	 * ��ȡ����ģ��ĸ������õĲ���ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public boolean getSystemSettingBooleanValue(String model, String name, boolean defaultvalue);
	
	/**
	 * ��ȡ����ģ��ĸ������õ���������ֵ�����û�и�����ֵ���򷵻�ȱʡֵ
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public Date getSystemSettingDateValue(String model, String name, Date defaultvalue);
	
	/**
	 * ��ȡ����ģ�����������������
	 * 
	 * @param model Ҫ���ص�ģ�����ƣ��������Ϊ�գ��򷵻�����ģ�������������
	 * @return
	 */
	public String[] getSystemSettingNames(String model);
	
	/**
	 * �������ģ�飬���õ�����ֵ�����ֵ�Ѿ����ڣ����޸�ֵ����������ڣ������ֵ
	 * 
	 * @param model
	 * @param name
	 * @param value
	 */
	public void setSystemSetting(String model, String name, Object value);

	/**
	 * ��ȡ�����û��ĸ������õ�ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public String getPersonSettingValue(String username, String name);
	
	/**
	 * ��ȡ�����û��ĸ������õĳ���ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public long getPersonSettingLongValue(String username, String name);
	
	/**
	 * ��ȡ�����û��ĸ������õ�����ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public int getPersonSettingIntValue(String username, String name);
	
	/**
	 * ��ȡ�����û��ĸ������õĸ���ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public double getPersonSettingDoubleValue(String username, String name);
	
	/**
	 * ��ȡ�����û��ĸ������õĲ���ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public boolean getPersonSettingBooleanValue(String username, String name);
	
	/**
	 * ��ȡ�����û��ĸ������õ���������ֵ�����û�и�����ֵ����ȡϵͳ����Ĭ��ֵ
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public Date getPersonSettingDateValue(String username, String name);
	
	/**
	 * ��ȡ�����û�����������������
	 * 
	 * @param username Ҫ���ص�ģ�����ƣ��������Ϊ�գ��򷵻�ϵͳĬ�ϵ�����������
	 * @return
	 */
	public String[] getPersonSettingNames(String username);
	
	/**
	 * ��������û������õ�����ֵ�����ֵ�Ѿ����ڣ����޸�ֵ����������ڣ������ֵ
	 * 
	 * @param username
	 * @param name
	 * @param value
	 * @param isReplaceGlobalSetting �Ƿ�ͬʱ�޸�ϵͳ����Ĭ��ֵ
	 */
	public void setPersonSetting(String username, String name, Object value, boolean isReplaceDefaultSetting);
}
