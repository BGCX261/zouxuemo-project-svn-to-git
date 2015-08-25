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
 * ͨ��ת������Դ��ǩ
 * <lilydap:commonExtSimpleStore name="JiliangLeibieStore" model="energy.JiliangLeibie" valueField="code" displayField="name" selectText="-��ʾ����-"  extendText="key:01,value:����~key:02,value:����2..."/>
 * ִ�к��������������´��룺fields: ['dictCode', 'dictValue']֮�����ú��ֵ��һ�����ֶ�����Ϊ��ǰ̨���Բ��������ֵ������й��ܣ���������д��
 * 	var JiliangLeibieStore = new Ext.data.SimpleStore({
 * 		fields: ['dictCode', 'dictValue'],
 * 		data : [['', '-��ʾ����-'],['001', '����'],['002', '����']����]
 * 	})
 * 
 * @author ��ɲ�
 *
 * @jsp.tag name="commonExtSimpleStore"
 */
public class CommonExtSimpleStoreTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1517114886737579450L;

	/**
	 * ����store�ı������ƣ���ѡ����������ã�Ĭ��Ϊ"{�ֵ�Ŀ¼����}Store"
	 */
	private String name = null;

	/**
	 * <code>id</code> Store��IDֵ����ѡ
	 */
	private String id = null;
	
	/**
	 * Ҫ�������ֵ�Ŀ¼����������
	 */
	private String model = null;
	
	/**
	 * �Ƿ�ֻ����showFlagΪtrue���ֵ䣬��ѡ��Ĭ��Ϊtrue
	 */
	private String valueField = "";
	
	/**
	 * <code>dictCodeHead</code> �ֵ�Code�޶���ͷ�ַ�������ѡ
	 */
	private String displayField = "";		
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;

	/**
	 * ��������ǰ��������һ�������Ϊѡ�����л��߲�ѡ����ı��ַ�������ѡ��Ĭ��Ϊ"-����-"��
	 */
	private String selectText = "";
	
	/**
	 * ��������������չ��Ŀ����ʽΪkey��
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
	 * ���� id ֵΪ <code>id</code>.
	 * @param id Ҫ���õ� id ֵ
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
	 * ���� displayField ֵΪ <code>displayField</code>.
	 * @param displayField Ҫ���õ� displayField ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	
	/**
	 * ���� bundle ֵΪ <code>bundle</code>.
	 * @param bundle Ҫ���õ� bundle ֵ
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
			throw new JspException("store������δ���壡");
		
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
    		//textStr="key:01,value:����~key:02,value:����2..."
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

