package com.lily.dap.web.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 *
 * LilyAccessDeniedHandlerImpl.java
 * 
 * <br>���ߣ� ����
 *
 * <br>���ڣ� 2008 4 11  09:33:59
 *
 * <br>��Ȩ���У��Ͳ�����ٺ�����������޹�˾
 */
public class LilyAccessDeniedHandlerImpl extends AccessDeniedHandlerImpl{
	private String errorPage;

    //~ Methods ========================================================================================================
	@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
        throws IOException, ServletException {
		
		HttpServletRequest httpservletrequest = (HttpServletRequest)request;
		String ajaxRequest =httpservletrequest.getHeader("X-Requested-With");
		boolean isAjaxRequest = (null!=ajaxRequest&& ajaxRequest.equals("XMLHttpRequest"))?true:false;
        if (errorPage != null && !isAjaxRequest) {
            // Put exception into request scope (perhaps of use to a view)
            ((HttpServletRequest) request).setAttribute(WebAttributes.ACCESS_DENIED_403,accessDeniedException);
            
            // Perform RequestDispatcher "forward"
            RequestDispatcher rd = request.getRequestDispatcher(errorPage);
            rd.forward(request, response);
        }else if(isAjaxRequest){
        	String results = "{success:false,error:'��ǰ�û���������д������������ϵϵͳ����Ա�������Ӧ��Ȩ��!'}";
        	HttpServletResponse httpResponse = (HttpServletResponse) response;
        	httpResponse.setContentType("text/html; charset=utf-8");
    		PrintWriter out = httpResponse.getWriter();
    		out.write(results);
    		return;
        }

        if (!response.isCommitted()) {
            // Send 403 (we do this after response has been written)
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        }
    }
	
	 public void setErrorPage(String errorPage) {
	        if ((errorPage != null) && !errorPage.startsWith("/")) {
	            throw new IllegalArgumentException("ErrorPage must begin with '/'");
	        }

	        this.errorPage = errorPage;
	    }
}
