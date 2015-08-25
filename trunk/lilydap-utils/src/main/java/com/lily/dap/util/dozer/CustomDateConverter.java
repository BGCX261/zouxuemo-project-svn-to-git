package com.lily.dap.util.dozer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class CustomDateConverter implements CustomConverter {
	private static SimpleDateFormat[] sdfs = {
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") };

	private static final Pattern W3CDATE = Pattern.compile(
			//data
			"^(\\d{4})" +//YYYY1
				"(?:" +
					"\\-(\\d{1,2})" +//MM2
					"(?:" +
						"\\-(\\d{1,2})" +//DD3
						//time
						"(?:" +
							"T(\\d{2})\\:(\\d{2})" +//hour:4,minutes:5
							"(?:\\:(\\d{2}(\\.\\d+)?))?"+//seconds//6
							"(Z|[+\\-]\\d{2}\\:?\\d{2})?" +//timeZone:7
						")?" +
					")?"+
				")?$");

	public Object convert(Object destination, Object source,
			Class<?> destClass, Class<?> sourceClass) {
		if (source == null || "".equals(source))
			return null;

		if (source instanceof Date) {
			String s = sdfs[2].format((Date) source);

			if (s.endsWith(" 00:00:00"))
				s = s.substring(0, s.length() - 9);
			else if (s.endsWith(":00"))
				s = s.substring(0, s.length() - 3);

			return s;
		} else if (source instanceof String) {
			Matcher m = W3CDATE.matcher((String)source);
			if(m.find()){
				Calendar ca = Calendar.getInstance();
				ca.clear();
				String timeZone = m.group(7);
				if(timeZone!=null){
					ca.setTimeZone(TimeZone.getTimeZone("GMT"+timeZone));
				}
				ca.set(Calendar.YEAR, Integer.parseInt(m.group(1)));// year
				String month = m.group(2);
				if (month != null) {
					ca.set(Calendar.MONTH, Integer.parseInt(month) - 1);
					String date = m.group(3);
					if (date != null) {
						ca.set(Calendar.DATE, Integer.parseInt(date));
						String hour = m.group(4);
						if (hour != null) {
							String minutes = m.group(5);
							ca.set(Calendar.HOUR, Integer.parseInt(hour));
							ca.set(Calendar.MINUTE, Integer.parseInt(minutes));
							String seconds = m.group(6);
							if(seconds == null){
							}else if (seconds.length() > 2) {
								float f = Float.parseFloat(seconds);
								ca.set(Calendar.SECOND, (int) f);
								ca.set(Calendar.MILLISECOND,
										((int) f * 1000) % 1000);
							} else {
								ca.set(Calendar.SECOND, Integer.parseInt(seconds));
							}
						}
					}
				}
				return ca.getTime();
			} else {
				String s = (String) source;

				Date d = null;
				try {
					d = sdfs[2].parse(s);
				} catch (ParseException e) {
					try {
						d = sdfs[1].parse(s);
					} catch (ParseException e1) {
						try {
							d = sdfs[0].parse(s);
						} catch (ParseException e2) {
							throw new MappingException(
									"Converter CustomDateConverter used incorrectly. Arguments passed in were:"
											+ destination + " and " + source);
						}
					}
				}

				return d;
			}
		} else {
			throw new MappingException(
					"Converter CustomDateConverter used incorrectly. Arguments passed in were:"
							+ destination + " and " + source);
		}
	}
}