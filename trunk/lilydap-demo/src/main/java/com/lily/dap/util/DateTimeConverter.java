package com.lily.dap.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.sql.Timestamp;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


/**
 * This class is converts a java.util.Date to a String
 * and a String to a java.util.Date. 
 * 
 * <p>
 * <a href="DateTimeConverter.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class DateTimeConverter implements Converter {
	static String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss",
								 "yyyy-MM-dd" };
	
    public Object convert(Class type, Object value) {
    	for(int i=0; i<PATTERNS.length; i++){
	        try {
				if (value == null) {
				    return null;
				} else if (type == Date.class) {
				    return convertToDate(type, value, PATTERNS[i]);
				} else if (type == String.class) {
				    return convertToString(type, value,PATTERNS[i]);
				} else if (type == Object.class) {
					return value;
				}
			} catch (ParseException e) {
				System.out.println("当前模式无法解析该对象，尝试使用下一种模式！");
				continue;
			}
    	}
        throw new ConversionException("Could not convert " +
                                      value.getClass().getName() + " to " +
                                      type.getName());
    }

    protected Object convertToDate(Class type, Object value, String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        if (value instanceof String) {
                if (StringUtils.isEmpty(value.toString())) {
                    return null;
                }
                date = df.parse((String) value);
        }
        return date;   
    }

    protected Object convertToString(Class type, Object value,String pattern) {        
        if (value instanceof Date) {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(value);
        } else {
            return value.toString();
        }
    }
    
    
    public static void main(String[] args) {
    	DateTimeConverter dtc = new DateTimeConverter();
    	Date date = (Date)dtc.convert(Date.class, "2008-03-02 21:33:23");
		System.out.println(date); 
    	Date date1 = (Date)dtc.convert(Date.class, "2008-03ss-02 21:33:23");
    	System.out.println(date1); 
    }  

}
