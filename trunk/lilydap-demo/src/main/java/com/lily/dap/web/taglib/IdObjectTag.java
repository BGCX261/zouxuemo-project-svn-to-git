package com.lily.dap.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.lily.dap.util.convert.ConvertUtils;

/**
 * ʵ��ID��������ļ���װ�ؼ�ID�����������ʾ
 *
 * <p>
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 * </p>
 *
 * @author zouxuemo
 */
public class IdObjectTag extends BodyTagSupport {
	private static final long serialVersionUID = 3905528206810167095L;
    private String name;
    private String scope = "request";

    /**
     * @param name Ҫ�����ֵ��б��������
     *
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setName(String name) {
        this.name = name;
    }

	/**
     * Property used to simply stuff the list of countries into a
     * specified scope.
     *
     * @param scope
     *
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setToScope(String scope) {
        this.scope = scope;
    }

    /**
     * Process the start of this tag.
     *
     * @return int status
     *
     * @exception JspException if a JSP exception has occurred
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
    	if (name != null && !"".equals(name)) {
	        List objectList = retrieveObjectList();
	
	        if (scope.equals("page")) {
	            pageContext.setAttribute(name, objectList);
	        } else if (scope.equals("request")) {
	            pageContext.getRequest().setAttribute(name, objectList);
	        } else if (scope.equals("session")) {
	            pageContext.getSession().setAttribute(name, objectList);
	        } else if (scope.equals("application")) {
	            pageContext.getServletContext().setAttribute(name, objectList);
	        } else {
	            throw new JspException("Attribute 'scope' must be: page, request, session or application");
	        }
	
	        return super.doStartTag();
    	} else {
    		return EVAL_BODY_BUFFERED;
    	}
    }

	public int doEndTag() throws JspException {
		if (name == null) {
			BodyContent bc = getBodyContent(); 
			String arg = bc.getString(); 
			bc.clearBody();	
			
			long objectId = (Long)ConvertUtils.convert(arg, Long.class);
		    String strName = getNameForId(objectId);
		    
			JspWriter out = pageContext.getOut();		
			 try {
				out.print(strName);
			} catch (IOException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
		}
		
		 return Tag.EVAL_PAGE;
	   }

    /**
     * Release aquired resources to enable tag reusage.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
    }

    /**
     * �����������󼯺��б�.
     *
     * @return Ҫ�����Ķ��󼯺��б�
     */
    protected List retrieveObjectList() throws JspException {
    	return new ArrayList();
    }
    
    /**
     * ���ظ���ID������
     * 
     * @param id
     * @return
     */
    protected String getNameForId(long id) throws JspException {
    	return "";
    }
}
