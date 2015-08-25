/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: WebUtils.java 671 2009-11-26 17:28:59Z calvinxiu $
 */
package com.lily.dap.web.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Web Utils��������.
 * 
 * @author calvin
 */
public class WebUtils {

	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * ������������������ضԻ����Header.
	 * 
	 * @param fileName
	 *            ���غ���ļ���.
	 */
	public static void setDownloadableHeader(HttpServletResponse response,
			String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
	}

	/**
	 * ����LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response,
			long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * ����Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * ���ù���ʱ�� Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response,
			long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis()
				+ expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
	}

	/**
	 * �����޻���Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * ���������ͻ����Ƿ�֧��gzip����.
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		// Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if (StringUtils.contains(acceptEncoding, "gzip")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����Gzip Header������GZIPOutputStream.
	 */
	public static OutputStream buildGzipOutputStream(
			HttpServletResponse response) throws IOException {
		response.setHeader("Content-Encoding", "gzip");
		return new GZIPOutputStream(response.getOutputStream());
	}

	/**
	 * ���������If-Modified-Since Header, �����ļ��Ƿ����޸�.
	 * 
	 * ������޸�, checkIfModify����false ,����304 not modify status.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * ��������� If-None-Match Header,����Etag�Ƿ���Ч.
	 * 
	 * ���Etag��Ч,checkIfNoneMatch����false, ����304 not modify status.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
			HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!headerValue.equals("*")) {
				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * ȡ�ô���ͬǰ׺��Request Parameters.
	 * 
	 * ���صĽ��Parameter����ȥ��ǰ׺.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParametersStartingWith(
			HttpServletRequest request, String prefix) {
		if (prefix == null)
			prefix = "";
		
		Map params = new TreeMap();
		Enumeration paramNames = request.getParameterNames();
		
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0)
					;// Do nothing, no values found at all.
				else if (values.length > 1)
					params.put(unprefixed, StringUtils.join(values));
				else
					params.put(unprefixed, values[0]);
				
			}
		}
		
		return params;
	}
}
