package com.lily.dap.service.system.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.system.PersonSetting;
import com.lily.dap.entity.system.SystemSetting;
import com.lily.dap.service.system.SettingManager;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataConvertException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.util.convert.DapDateConvert;

/**
 * @author zouxuemo
 *
 */
@Service("settingManager")
public class SettingManagerImpl extends BaseManager implements SettingManager {
    private static final Long defaultLong = null;
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * 系统参数缓存
     */
    private static Map<String, SystemSetting> systemSettingCache = new HashMap<String, SystemSetting>();
    
    /**
     * 个人设置缓存
     */
    private static Map<String, PersonSetting> personSettingCache = new HashMap<String, PersonSetting>();
    
    static {
        ConvertUtils.register(new DapDateConvert(), Date.class);
        ConvertUtils.register(new DapDateConvert(), String.class);
        ConvertUtils.register(new LongConverter(defaultLong), Long.class);
        ConvertUtils.register(new IntegerConverter(defaultLong), Integer.class);
    }
    
	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getBooleanValue(java.lang.String, java.lang.String, boolean)
	 */
	public boolean getSystemSettingBooleanValue(String model, String name, boolean defaultvalue) {
		String temp = getSystemSettingValue(model, name);
		if (temp == null || "".equals(temp))
			return defaultvalue;
		else {
			boolean ret = defaultvalue;
			try {
				ret = (Boolean)ConvertUtils.convert(temp, Boolean.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getDateValue(java.lang.String, java.lang.String, java.util.Date)
	 */
	public Date getSystemSettingDateValue(String model, String name, Date defaultvalue) {
		String temp = getSystemSettingValue(model, name);
		if (temp == null || "".equals(temp))
			return defaultvalue;
		else {
			Date ret = defaultvalue;
			try {
				ret = (Date)ConvertUtils.convert(temp, Date.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getDoubleValue(java.lang.String, java.lang.String, double)
	 */
	public double getSystemSettingDoubleValue(String model, String name, double defaultvalue) {
		String temp = getSystemSettingValue(model, name);
		if (temp == null || "".equals(temp))
			return defaultvalue;
		else {
			double ret = defaultvalue;
			try {
				ret = (Double)ConvertUtils.convert(temp, Double.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getIntValue(java.lang.String, java.lang.String, int)
	 */
	public int getSystemSettingIntValue(String model, String name, int defaultvalue) {
		String temp = getSystemSettingValue(model, name);
		if (temp == null || "".equals(temp))
			return defaultvalue;
		else {
			int ret = defaultvalue;
			try {
				ret = (Integer)ConvertUtils.convert(temp, Integer.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getLongValue(java.lang.String, java.lang.String, long)
	 */
	public long getSystemSettingLongValue(String model, String name, long defaultvalue) {
		String temp = getSystemSettingValue(model, name);
		if (temp == null || "".equals(temp))
			return defaultvalue;
		else {
			long ret = defaultvalue;
			try {
				ret = (Long)ConvertUtils.convert(temp, Long.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getSystemSettingValue(String model, String name, String defaultvalue) {
		String value = getSystemSettingValue(model, name);
		if (value == null || "".equals(value))
			return defaultvalue;
		else
			return value;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#getValues(java.lang.String)
	 */
	public String[] getSystemSettingNames(String model) {
		List<SystemSetting> list = dao.query(SystemSetting.class, Condition.create().eq("model", model));
		if (list.size() == 0)
			return new String[0];
		
		String[] names = new String[list.size()];
		int ptr = 0;
		Iterator<SystemSetting> it = list.iterator();
		while (it.hasNext()) {
			SystemSetting systemSetting = it.next();
			names[ptr++] = new String(systemSetting.getName());
		}
		
		return names; 
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.system.SettingManager#save(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void setSystemSetting(String model, String name, Object value) {
		String storeValue = "";

		try {
			storeValue = ConvertUtils.convert(value);
		} catch (Exception e) {
			logger.error("保存系统参数错误：数据转换失败！");
			
			throw new DataConvertException("保存系统参数错误：数据转换失败！", e);
		}
		
		SystemSetting systemSetting = getSystemSetting(model, name);
		if (systemSetting == null) {
			systemSetting = new SystemSetting(model, name, storeValue);
		} else
			systemSetting.setValue(storeValue);
		
		dao.saveOrUpdate(systemSetting);
		
		//替换或者添加缓存中系统参数数据
		String key = model + "$" + name;
		if (systemSettingCache.containsKey(key)) {
			systemSettingCache.remove(key);
		}
		
		systemSettingCache.put(key, systemSetting);
	}

	private String getSystemSettingValue(String model, String name) {
		SystemSetting systemSetting = getSystemSetting(model, name);
		if (systemSetting == null)
			return null;
		
		return systemSetting.getValue();
	}
	
	public SystemSetting getSystemSetting(String model, String name) {
		SystemSetting systemSetting = null;
		
		//检查缓存中是否保存指定的系统参数，如果有，直接返回缓存数据，否则，从数据库读取，并把读取的数据存入缓存
		String key = model + "$" + name;
		if (systemSettingCache.containsKey(key)) {
			systemSetting = systemSettingCache.get(key);
		} else {
			try {
				systemSetting = get(SystemSetting.class, new String[]{"model", "name"}, new String[]{model, name});
			} catch (DataNotExistException e) {}
			
			if (systemSetting != null)
				systemSettingCache.put(key, systemSetting);
		}
		
		return systemSetting;
	}

	public boolean getPersonSettingBooleanValue(String username, String name) {
		String temp = getPersonSettingValue(username, name);
		if (temp == null || "".equals(temp))
			return false;
		else {
			boolean ret = false;
			try {
				ret = (Boolean)ConvertUtils.convert(temp, Boolean.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	public Date getPersonSettingDateValue(String username, String name) {
		String temp = getPersonSettingValue(username, name);
		if (temp == null || "".equals(temp))
			return null;
		else {
			Date ret = null;
			try {
				ret = (Date)ConvertUtils.convert(temp, Date.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	public double getPersonSettingDoubleValue(String username, String name) {
		String temp = getPersonSettingValue(username, name);
		if (temp == null || "".equals(temp))
			return 0.0f;
		else {
			double ret = 0.0f;
			try {
				ret = (Double)ConvertUtils.convert(temp, Double.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	public int getPersonSettingIntValue(String username, String name) {
		String temp = getPersonSettingValue(username, name);
		if (temp == null || "".equals(temp))
			return 0;
		else {
			int ret = 0;
			try {
				ret = (Integer)ConvertUtils.convert(temp, Integer.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	public long getPersonSettingLongValue(String username, String name) {
		String temp = getPersonSettingValue(username, name);
		if (temp == null || "".equals(temp))
			return 0;
		else {
			long ret = 0;
			try {
				ret = (Long)ConvertUtils.convert(temp, Long.class);
			} catch (Exception e) {
				logger.warn("获取系统参数警告：数据转换失败，返回默认值！");
			}
			
            return ret;
		}
	}

	public String getPersonSettingValue(String username, String name) {
		PersonSetting personSetting = getPersonSetting(username, name, true);
		if (personSetting == null)
			return null;
		
		return personSetting.getValue();
	}

	public String[] getPersonSettingNames(String username) {
		if (username == null || "".equals(username))
			username = PersonSetting.DEFAULT_SETTING_USERNAME;
		
		List<PersonSetting> list = dao.query(PersonSetting.class, Condition.create().eq("username", username));
		if (list.size() == 0)
			return new String[0];
		
		String[] names = new String[list.size()];
		int ptr = 0;
		Iterator<PersonSetting> it = list.iterator();
		while (it.hasNext()) {
			PersonSetting personSetting = it.next();
			names[ptr++] = new String(personSetting.getName());
		}
		
		return names; 
	}

	public void setPersonSetting(String username, String name, Object value, boolean isReplaceDefaultSetting) {
		String storeValue = "";

		try {
			storeValue = ConvertUtils.convert(value);
		} catch (Exception e) {
			logger.error("保存用户参数错误：数据转换失败！");
			
			throw new DataConvertException("保存用户参数错误：数据转换失败！", e);
		}
		
		PersonSetting personSetting = getPersonSetting(username, name, false);
		if (personSetting == null) {
			personSetting = new PersonSetting(username, name, storeValue);
		} else
			personSetting.setValue(storeValue);
		
		dao.saveOrUpdate(personSetting);
		
		if (isReplaceDefaultSetting)
			setPersonSetting(PersonSetting.DEFAULT_SETTING_USERNAME, name, value, false);

		
		//替换或者添加缓存中个人设置数据
		String key = username + "$" + name;
		if (personSettingCache.containsKey(key)) {
			personSettingCache.remove(key);
		}
		
		personSettingCache.put(key, personSetting);
	}
	
	private PersonSetting getPersonSetting(String username, String name, boolean isGetFromDefaultValueIfNotFound) {
		PersonSetting personSetting = null;
		
		//检查缓存中是否保存指定的个人设置，如果有，直接返回缓存数据，否则，从数据库读取，并把读取的数据存入缓存
		String key = username + "$" + name;
		if (personSettingCache.containsKey(key))
			return personSettingCache.get(key);
		
		try {
			personSetting = get(PersonSetting.class, new String[]{"username", "name"}, new String[]{username, name});
		} catch (DataNotExistException e) {}
		
		
		if (personSetting != null)
			personSettingCache.put(key, personSetting);
		
		if (personSetting == null && isGetFromDefaultValueIfNotFound) {
			try {
				personSetting = get(PersonSetting.class, new String[]{"username", "name"}, new String[]{PersonSetting.DEFAULT_SETTING_USERNAME, name});
			} catch (DataNotExistException e) {}
		}
		
		return personSetting;
	}
}
