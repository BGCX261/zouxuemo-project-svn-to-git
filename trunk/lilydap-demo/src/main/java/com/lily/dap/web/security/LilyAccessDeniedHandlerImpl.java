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
 * <br>作者： 刘鹏
 *
 * <br>日期： 2008 4 11  09:33:59
 *
 * <br>版权所有：淄博东软百合软件开发有限公司
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
        	String results = "{success:false,error:'当前用户不允许进行此项操作。请联系系统管理员，获得相应的权限!'}";
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
