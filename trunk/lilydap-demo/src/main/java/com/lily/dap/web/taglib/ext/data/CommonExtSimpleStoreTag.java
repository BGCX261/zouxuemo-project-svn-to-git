package com.lily.dap.web.taglib.ext.data;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;

import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.Manager;
import com.lily.dap.service.SpringContextHolder;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.taglib.TagUtils;

/**
 * 通用转换数据源标签
 * <lilydap:commonExtSimpleStore name="JiliangLeibieStore" model="energy.JiliangLeibie" valueField="code" displayField="name" selectText="-显示所有-"  extendText="key:01,value:测试~key:02,value:测试2..."/>
 * 执行后将生成类似于如下代码：fields: ['dictCode', 'dictValue']之所以用和字典表一样的字段是因为在前台可以采用现有字典表的所有功能，而不用再写。
 * 	var JiliangLeibieStore = new Ext.data.SimpleStore({
 * 		fields: ['dictCode', 'dictValue'],
 * 		data : [['', '-显示所有-'],['001', '质量'],['002', '电力']……]
 * 	})
 * 
 * @author 孙成才
 *
 * @jsp.tag name="commonExtSimpleStore"
 */
public class CommonExtSimpleStoreTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1517114886737579450L;

	/**
	 * 生成store的变量名称，可选；如果不设置，默认为"{字典目录名称}Store"
	 */
	private String name = null;

	/**
	 * <code>id</code> Store的ID值，可选
	 */
	private String id = null;
	
	/**
	 * 要检索的字典目录，必须输入
	 */
	private String model = null;
	
	/**
	 * 是否只检索showFlag为true的字典，可选，默认为true
	 */
	private String valueField = "";
	
	/**
	 * <code>dictCodeHead</code> 字典Code限定开头字符串，可选
	 */
	private String displayField = "";		
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;

	/**
	 * 在数据最前面中增加一个数据项，为选择所有或者不选择的文本字符串。可选，默认为"-所有-"；
	 */
	private String selectText = "";
	
	/**
	 * 在数据中增加扩展项目，格式为key；
	 */
	private String extendText = "";
	/**
	 * @param name
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置 id 值为 <code>id</code>.
	 * @param id 要设置的 id 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param model
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) throws JspException {
		this.model = model;
	}
	
	/**
	 * @param valueField
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	/**
	 * 设置 displayField 值为 <code>displayField</code>.
	 * @param displayField 要设置的 displayField 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	
	/**
	 * 设置 bundle 值为 <code>bundle</code>.
	 * @param bundle 要设置的 bundle 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @param selectText the selectText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectText(String selectText) throws JspException {
		this.selectText = selectText;
	}
	
	/**
	 * @param extendText the extendText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setExtendText(String extendText) throws JspException {
		this.extendText = extendText;
	}

	public int doStartTag() throws JspException {
		if (name == null)
			throw new JspException("store的名称未定义！");
		
    	List<? extends BaseEntity> objectList;
		try {
			Class<? extends BaseEntity> clazz = BaseEntityHelper.parseEntity(model);
			
	    	Manager manager = (Manager)SpringContextHolder.getBean("manager");
			objectList = manager.query(clazz, null);
		} catch (Exception e) {
			throw new JspException(e);		
		} 
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append("var ").append(name).append(" = new Ext.data.SimpleStore({\r\n");
    	
    	if (id != null)
    		buf.append("	id: '" + id + "',\r\n");

    	buf.append("	fields: ['code', 'value'],\r\n");    	
    	buf.append("	data: [");
    	
    	boolean first = true;   
    	
    	if(!selectText.equals("")){
    		buf.append("['', '" + TagUtils.getMessage(pageContext, bundle, selectText) + "']");
    		first = false;
    	}     
    	
    	if (!"".equals(extendText)) {
    		//textStr="key:01,value:测试~key:02,value:测试2..."
    		String textStr = TagUtils.getMessage(pageContext, bundle, extendText);    		
    		String textArr[] = textStr.split("~");
    		for(int i = 0; i < textArr.length; i++){
    			String text[] = textArr[i].split(",");
    			String key = text[0].split(":")[1];    			
    			String value = text[1].split(":")[1];
    			
    			if(value.equals("$EMPTY$"))
    				value = "";
    			if(i == 0){	
    				buf.append("['" + key + "', '" + value + "']");
    			}else{
    				buf.append(",['" + key + "', '" + value + "']");
    			}
    		}
                	
    		first = false;
    	}
   	
    	for(int tempI = 0; tempI < objectList.size(); tempI++) {
    		
        	if (first)
        		first = false;
        	else
        		buf.append(", ");  
        	
        	Object orig = objectList.get(tempI); 
        	Object valueFieldValue = null;
        	Object displayFieldValue = null;
        	
			try {
				valueFieldValue = PropertyUtils.getNestedProperty(orig, valueField).toString();
				displayFieldValue = PropertyUtils.getNestedProperty(orig, displayField).toString();
				
			} catch (IllegalAccessException e) {			
				e.printStackTrace();
			} catch (InvocationTargetException e) {			
				e.printStackTrace();
			} catch (NoSuchMethodException e) {				
				e.printStackTrace();
			}
        
           buf.append("['" + valueFieldValue + "', '" + displayFieldValue + "']");         
        
        }
    	
		buf.append("]\r\n");
    	buf.append("});\r\n");
    	
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException ioe) {
			throw new JspTagException(ioe.toString(), ioe);
		}
		return SKIP_BODY;
	}
}

