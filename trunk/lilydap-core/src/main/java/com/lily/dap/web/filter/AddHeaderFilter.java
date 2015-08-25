package com.lily.dap.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 允许在页面中添加html头信息的过滤器
 * 
 * @author zouxuemo
 *
 */
public class AddHeaderFilter implements Filter {
	public Map<String, String> headers = new HashMap<String, String>();

	public void init(FilterConfig config) throws ServletException {
		String headersStr = config.getInitParameter("headers");
		String[] headersl = headersStr.split(",");
		
		for (int i = 0; i < headersl.length; i++) {
			String[] temp = headersl[i].split("==");
			
			headers.put(temp[0].trim(), temp[1].trim());
		}
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (res instanceof HttpServletResponse) {
			HttpServletResponse response = (HttpServletResponse)res;
			
			for (Iterator<Map.Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				
				response.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		chain.doFilter(req, res);
	}
}