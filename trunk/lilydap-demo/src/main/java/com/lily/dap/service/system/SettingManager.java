package com.lily.dap.service.system;

import java.util.Date;

import com.lily.dap.entity.system.SystemSetting;

/**
 * @author zouxuemo
 *
 */
public interface SettingManager {
	/**
	 * 获取给定模块给定name的系统参数设置
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	public SystemSetting getSystemSetting(String model, String name); 
	/**
	 * 获取给定模块的给定设置的值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public String getSystemSettingValue(String model, String name, String defaultvalue);
	
	/**
	 * 获取给定模块的给定设置的长整值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public long getSystemSettingLongValue(String model, String name, long defaultvalue);
	
	/**
	 * 获取给定模块的给定设置的整数值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public int getSystemSettingIntValue(String model, String name, int defaultvalue);
	
	/**
	 * 获取给定模块的给定设置的浮点值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public double getSystemSettingDoubleValue(String model, String name, double defaultvalue);
	
	/**
	 * 获取给定模块的给定设置的布尔值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public boolean getSystemSettingBooleanValue(String model, String name, boolean defaultvalue);
	
	/**
	 * 获取给定模块的给定设置的日期类型值，如果没有该设置值，则返回缺省值
	 * 
	 * @param model
	 * @param name
	 * @param defaultvalue
	 * @return
	 */
	public Date getSystemSettingDateValue(String model, String name, Date defaultvalue);
	
	/**
	 * 获取给定模块的所有设置名数组
	 * 
	 * @param model 要返回的模块名称，如果设置为空，则返回所有模块的设置名数组
	 * @return
	 */
	public String[] getSystemSettingNames(String model);
	
	/**
	 * 保存给定模块，设置的设置值。如果值已经存在，则修改值，如果不存在，则添加值
	 * 
	 * @param model
	 * @param name
	 * @param value
	 */
	public void setSystemSetting(String model, String name, Object value);

	/**
	 * 获取给定用户的给定设置的值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public String getPersonSettingValue(String username, String name);
	
	/**
	 * 获取给定用户的给定设置的长整值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public long getPersonSettingLongValue(String username, String name);
	
	/**
	 * 获取给定用户的给定设置的整数值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public int getPersonSettingIntValue(String username, String name);
	
	/**
	 * 获取给定用户的给定设置的浮点值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public double getPersonSettingDoubleValue(String username, String name);
	
	/**
	 * 获取给定用户的给定设置的布尔值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public boolean getPersonSettingBooleanValue(String username, String name);
	
	/**
	 * 获取给定用户的给定设置的日期类型值，如果没有该设置值，则取系统设置默认值
	 * 
	 * @param username
	 * @param name
	 * @return
	 */
	public Date getPersonSettingDateValue(String username, String name);
	
	/**
	 * 获取给定用户的所有设置名数组
	 * 
	 * @param username 要返回的模块名称，如果设置为空，则返回系统默认的设置名数组
	 * @return
	 */
	public String[] getPersonSettingNames(String username);
	
	/**
	 * 保存给定用户，设置的设置值。如果值已经存在，则修改值，如果不存在，则添加值
	 * 
	 * @param username
	 * @param name
	 * @param value
	 * @param isReplaceGlobalSetting 是否同时修改系统设置默认值
	 */
	public void setPersonSetting(String username, String name, Object value, boolean isReplaceDefaultSetting);
}
