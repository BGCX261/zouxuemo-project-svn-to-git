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
             * ����������"yyyy-MM-dd HH:mm:ss"�������ڸ�ʽ���������ʧ�ܣ���������"yyyy-MM-dd"����
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
    	 * ��"yyyy-MM-dd HH:mm:ss"ת�����ڸ�ʽ���ַ���
    	 * Ȼ���ж�����������ַ�����ʱ���ʽΪ"00:00:00"����ֻ�������ڸ�ʽ"yyyy-MM-dd"
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
