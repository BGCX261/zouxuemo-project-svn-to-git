package com.lily.dap.web.action.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.system.SystemSetting;
import com.lily.dap.service.system.SettingManager;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

public class SystemSettingAction extends BaseAction {
	private static final long serialVersionUID = -7713201081295642481L;
	
	@Autowired
	private SettingManager settingManager;

    public String execute() throws Exception {
		return SUCCESS;
    }
       
    /**
     * 得到系统参数信息
     */
    public String get() throws Exception {
    	String model = Struts2Utils.getParameter("model");
    	
    	String[] settingNames = settingManager.getSystemSettingNames(model);
    	
    	StringBuffer sb = new StringBuffer("{totalcount : ");
    	sb.append(settingNames.length + ", data : [");
    	for (String settingName : settingNames) {
			SystemSetting systemSetting = settingManager.getSystemSetting(model, settingName);
			sb.append(JsonHelper.formatObjectToJsonString(systemSetting)+ ","); 
		}
    	
    	sb.deleteCharAt(sb.length()-1);
    	sb.append("] }");
    	
    	Struts2Utils.renderHtml(sb.toString());
   		return null;
    } 
    
    /**
     * 保存系统参数信息
     */
    public String save() throws Exception {
    	String model = Struts2Utils.getParameter("model");
    	String modifiedRecrodsJson = Struts2Utils.getParameter("modifiedRecords");
    	
    	List<SystemSetting> list = JsonHelper.parseJsonStringToList(SystemSetting.class, modifiedRecrodsJson);
    	
    	String json = "";
    	try {
			for (SystemSetting setting : list) {
				settingManager.setSystemSetting(model, setting.getName(), setting.getValue());
				json = 	JsonHelper.combinSuccessJsonString("msg","系统参数保存成功!");
			}
		} catch (Exception e) {
			json = JsonHelper.combinFailJsonString(e);
		}
	
		Struts2Utils.renderHtml(json);
    	return null;
    }
}
