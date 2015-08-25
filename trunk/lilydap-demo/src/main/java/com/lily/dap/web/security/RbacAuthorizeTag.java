/*
 * package com.lily.dap.web.security;
 * class RbacAuthorizeTag
 * 
 * 创建日期 2006-3-1
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.web.security;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * 基于RBAC机制的授权判断标签实现
 * 
 * @author zouxuemo
 *
 * @jsp.tag name="rbacAuthorize"
 */
public class RbacAuthorizeTag extends TagSupport {
    //~ Instance fields ========================================================

    /**
     * <code>serialVersionUID</code> 的注释
     */
    private static final long serialVersionUID = -1510672562012020505L;
    
    private String ifAllGranted = "";
    private String ifAnyGranted = "";
    private String ifNotGranted = "";

    //~ Methods ================================================================

    /**
     * @param ifAllGranted
     * @throws JspException
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setIfAllGranted(String ifAllGranted) throws JspException {
        this.ifAllGranted = ifAllGranted;
    }

    public String getIfAllGranted() {
        return ifAllGranted;
    }

    /**
     * @param ifAnyGranted
     * @throws JspException
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setIfAnyGranted(String ifAnyGranted) throws JspException {
        this.ifAnyGranted = ifAnyGranted;
    }

    public String getIfAnyGranted() {
        return ifAnyGranted;
    }

    /**
     * @param ifNotGranted
     * @throws JspException
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setIfNotGranted(String ifNotGranted) throws JspException {
        this.ifNotGranted = ifNotGranted;
    }

    public String getIfNotGranted() {
        return ifNotGranted;
    }
    
    public int doStartTag() throws JspException {
        if (((null == ifAllGranted) || "".equals(ifAllGranted))
                && ((null == ifAnyGranted) || "".equals(ifAnyGranted))
                && ((null == ifNotGranted) || "".equals(ifNotGranted))) {
                return Tag.SKIP_BODY;
            }
        
        final String evaledIfNotGranted = ExpressionEvaluationUtils.evaluateString("ifNotGranted", ifNotGranted, pageContext);
        final String evaledIfAllGranted = ExpressionEvaluationUtils.evaluateString("ifAllGranted", ifAllGranted, pageContext);
        final String evaledIfAnyGranted = ExpressionEvaluationUtils.evaluateString("ifAnyGranted", ifAnyGranted, pageContext);
        if (!RightUtils.rbacAuthorize(evaledIfNotGranted, evaledIfAllGranted, evaledIfAnyGranted))
        	return Tag.SKIP_BODY;
        
        return Tag.EVAL_BODY_INCLUDE;
    }
}
