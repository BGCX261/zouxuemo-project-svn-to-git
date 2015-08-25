package com.lily.dap.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


/**
 * This class is converts a java.util.Date to a String
 * and a String to a java.util.Date. 
 * 
 * <p>
 * <a href="DateConverter.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class DateConverter implements Converter {
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        } else if (type == Timestamp.class) {
            return convertToDate(type, value);
        } else if (type == Date.class) {
            return convertToDate(type, value);
        } else if (type == String.class) {
            return convertToString(type, value);
        } else if (type == Object.class) {
        	return value;
        }
        
        throw new ConversionException("Could not convert " +
                                      value.getClass().getName() + " to " +
                                      type.getName());
    }

    protected synchronized Object convertToDate(Class type, Object value) {
        if (value instanceof String) {
            if (StringUtils.isEmpty(value.toString()))
                return null;

            /*
             * 首先试着用"yyyy-MM-dd HH:mm:ss"解析日期格式，如果解析失败，则试着用"yyyy-MM-dd"解析
             */
            
        	Date date;
            try {
            	dateFormat.applyPattern(DateUtil.getDateTimePattern());
                date = dateFormat.parse((String) value);
            } catch (Exception e1) {
            	try {
                	dateFormat.applyPattern(DateUtil.getDatePattern());
                    date = dateFormat.parse((String) value);
            	} catch (Exception e2) {
                    throw new ConversionException("Error converting '" + value + "' to Date");
            	}
            }
            
            if (type.equals(Timestamp.class))
                return new Timestamp(date.getTime());
            
            return date;
        }

        throw new ConversionException("Could not convert " +
                                      value.getClass().getName() + " to " +
                                      type.getName());
    }

    protected Object convertToString(Class type, Object value) {    
    	/*
    	 * 用"yyyy-MM-dd HH:mm:ss"转换日期格式到字符串
    	 * 然后判断如果解析的字符串的时间格式为"00:00:00"，则只返回日期格式"yyyy-MM-dd"
    	 */
    	
        if (value instanceof Date) {
        	dateFormat.applyPattern(DateUtil.getDateTimePattern());
        	
            try {
            	String s = dateFormat.format(value);
            	if (type.equals(Timestamp.class))
            		return s;
            	
            	if (s.lastIndexOf(DateUtil.ZERO_TIME) > 0)
            		s = s.substring(0, s.indexOf(' '));
            	
            	return s;
            } catch (Exception e) {
                throw new ConversionException("Error converting Date to String");
            }
        } else {
            return value.toString();
        }
    }
}
