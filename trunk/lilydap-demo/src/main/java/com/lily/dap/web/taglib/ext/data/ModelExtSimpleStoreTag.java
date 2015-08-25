package com.lily.dap.web.taglib.ext.data;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.condition.Order;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.service.Manager;
import com.lily.dap.service.SpringContextHolder;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.util.TransferUtils;
import com.lily.dap.web.util.ConditionHelper;
import com.lily.dap.web.util.JsonHelper;

/**
 * ����ʵ��������SimpleStore���ݱ�ǩ
 * 
 * @author ��ѧģ
 *
 * @jsp.tag name="modelExtSimpleStore"
 */
@SuppressWarnings("unchecked")
public class ModelExtSimpleStoreTag extends TagSupport implements ExtJsonReaderInterface {
	private static final long serialVersionUID = 3991067188168020447L;

	/**
	 * ����store�ı������ƣ���������
	 */
	private String name = null;

	/**
	 * <code>id</code> Store��IDֵ����ѡ
	 */
	private String id = null;
	
	/**
	 * Ҫ�����Ķ�����������������
	 */
	private String model = null;
	
	/**
	 * Ҫ������ֶ������ַ������������֮����","�ָ��������ֶ�
	 */
	private String fields = null;
	
	/**
	 * ��������1����ѡ
	 */
	private String cond1 = null;
	
	/**
	 * ��������ֵ1����ѡ
	 */
	private String value1 = null;
	
	/**
	 * ��������2����ѡ
	 */
	private String cond2 = null;
	
	/**
	 * ��������ֵ2����ѡ
	 */
	private String value2 = null;
	
	/**
	 * ��������3����ѡ
	 */
	private String cond3 = null;
	
	/**
	 * ��������ֵ3����ѡ
	 */
	private String value3 = null;
	
	/**
	 * �����ֶ�1����ѡ
	 */
	private String order1 = null;
	
	/**
	 * �����ֶ�3����ѡ
	 */
	private String order2 = null;
	
	/**
	 * �����ֶ�3����ѡ
	 */
	private String order3 = null;
    /**
     * ��ѯ��ʼ��¼����0��ʼ
     * 
     * <code>start</code>
     */
	private String start = "0";
    /**
     * ��ѯ���ؼ�¼������Ĭ��Ϊ-1����ʾ�����÷��ؼ�¼��Χ
     * 
     * <code>limit</code>
     */
	private String limit = "0";
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;
	/**
	 * ��������ǰ��������һ�������Ϊѡ�����л��߲�ѡ����ı��ַ�������ѡ��Ĭ��Ϊ"-����-"�������selectPropertyͬʱ��
	 */
	private String selectText = "";
	
	/**
	 * ��Ϊͨ�õ�MODELת��ʱ���ǲ���֪����һ����keyֵ��һ����valueֵ��������Ҫ����һ��valueֵ��һ�С�
	 * ��selectText����Ӧ�����ԣ����ĸ�������Ϊ��ʾselectText�ã������selectTextͬʱ�ã�
	 */
	private String selectProperty = "";
	/**
	 * @param name
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
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
	 * ���� model ֵΪ <code>model</code>.
	 * @param model Ҫ���õ� model ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * ���� fields ֵΪ <code>fields</code>.
	 * @param fields Ҫ���õ� fields ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	/**
	 * ���� cond1 ֵΪ <code>cond1</code>.
	 * @param cond1 Ҫ���õ� cond1 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond1(String cond1) {
		this.cond1 = cond1;
	}

	/**
	 * ���� value1 ֵΪ <code>value1</code>.
	 * @param value1 Ҫ���õ� value1 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * ���� cond2 ֵΪ <code>cond2</code>.
	 * @param cond2 Ҫ���õ� cond2 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond2(String cond2) {
		this.cond2 = cond2;
	}

	/**
	 * ���� value2 ֵΪ <code>value2</code>.
	 * @param value2 Ҫ���õ� value2 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * ���� cond3 ֵΪ <code>cond3</code>.
	 * @param cond3 Ҫ���õ� cond3 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond3(String cond3) {
		this.cond3 = cond3;
	}

	/**
	 * ���� value3 ֵΪ <code>value3</code>.
	 * @param value3 Ҫ���õ� value3 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}

	/**
	 * ���� order1 ֵΪ <code>order1</code>.
	 * @param order1 Ҫ���õ� order1 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder1(String order1) {
		this.order1 = order1;
	}

	/**
	 * ���� order2 ֵΪ <code>order2</code>.
	 * @param order2 Ҫ���õ� order2 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder2(String order2) {
		this.order2 = order2;
	}

	/**
	 * ���� order3 ֵΪ <code>order3</code>.
	 * @param order3 Ҫ���õ� order3 ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder3(String order3) {
		this.order3 = order3;
	}
	/**
	 * ���� start ֵΪ <code>start</code>.
	 * @param start Ҫ���õ� start ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStart(String start) {
		this.start = start;
	} 
	/**
	 * ���� limit ֵΪ <code>limit</code>.
	 * @param limit Ҫ���õ� limit ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLimit(String limit) {
		this.limit = limit;
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
	 * @param selectProperty the extendText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectProperty(String selectProperty) throws JspException {
		this.selectProperty = selectProperty;
	}
	// -------------------------------- ����Ϊ˽�б��� ----------------------------------
	/**
	 * <code>convertScriptMap</code> �����ת����Map
	 */
	private Map<String, String> convertScriptMap = new HashMap<String, String>();

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.ExtJsonReaderInterface#getConvertScriptMap()
	 */
	public Map<String, String> getConvertScriptMap() {
		return convertScriptMap;
	}

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		Class<? extends BaseEntity> clazz;
		try {
			clazz = BaseEntityHelper.parseEntity(model);
		} catch (Exception e) {
			throw new JspException("ָ��������[" + model + "]�����ڣ�");			
		}
		BaseEntity entity;
		try {
			entity = clazz.newInstance();
		} catch (InstantiationException e) {
			throw new JspException(e);
		} catch (IllegalAccessException e) {
			throw new JspException(e);
		}
		
		String[] fieldAry = fields.split(",");
		
		Condition condition = new Condition();
		
		
		Map<String, String> param = new HashMap<String, String>();
		if (cond1 != null && value1 != null)
			param.put(cond1, value1);
		
		if (cond2 != null && value2 != null)
			param.put(cond2, value2);
		
		if (cond3 != null && value3 != null)
			param.put(cond3, value3);
		
		ConditionHelper.fillCondition(condition, clazz, param);
		
		if (order1 != null)
			addOrder(condition, order1);
		
		if (order2 != null)
			addOrder(condition, order2);
		
		if (order3 != null)
			addOrder(condition, order3);
		
		condition.limit(Integer.parseInt(start), Integer.parseInt(limit));
		
    	Manager manager = (Manager)SpringContextHolder.getBean("manager");
    	List tempResult = manager.query(clazz, condition);
    	
    	StringBuffer buf = new StringBuffer();
    	buf.append("var ").append(name).append(" = new Ext.data.SimpleStore({");
    	
    	if (id != null)
    		buf.append("id: '" + id + "', ");
    	
    	buf.append("fields: [");
    	boolean first = true;
    	for (String fieldName : fieldAry) {
    		Class type;
			try {
				type = PropertyUtils.getPropertyType(entity, fieldName);
			} catch (Exception e) {
				throw new JspException(e);
			}
    		String typeName = type.getName();
    		
			StringBuffer buf1 = new StringBuffer();
			if (type == String.class || "char".equals(typeName)) {
				String convertScript = convertScript(fieldName);
				
				if ("".equals(convertScript))
					buf1.append("'").append(fieldName).append("'");
				else
					buf1.append("{name: '").append(fieldName).append("'").append(convertScript).append("}");
				
			} else if (type == Integer.class || type == Long.class || 
						"byte".equals(typeName) || "int".equals(typeName) || "long".equals(typeName)) {
				buf1.append("{name: '").append(fieldName).append("', type: 'int'").append(convertScript(fieldName)).append("}");
				
			} else if (type == Float.class || type == Double.class || 
						"double".equals(typeName) || "float".equals(typeName)) {
				buf1.append("{name: '").append(fieldName).append("', type: 'float'").append(convertScript(fieldName)).append("}");
				
			} else if (type == Boolean.class || "boolean".equals(typeName)) {
				buf1.append("{name: '").append(fieldName).append("', type: 'boolean'").append(convertScript(fieldName)).append("}");
				
			} else if (type == Date.class || type == Timestamp.class) {
				buf1.append("{name: '").append(fieldName).append("', type: 'date'").append(convertScript(fieldName)).append("}");
			} else
				continue;
			
			if (first)
				first = false;
			else
				buf.append(", ");
			
			buf.append(buf1);
    	}
    	buf.append("], ");
    	buf.append("data: ");
    	
    	List result = new ArrayList();
    	
    	if(!selectText.equals("")){
    		BaseEntity tempEntity;
    		try {
    			tempEntity = clazz.newInstance();
    		} catch (InstantiationException e) {
    			throw new JspException(e);
    		} catch (IllegalAccessException e) {
    			throw new JspException(e);
    		}
    		
    		try { 
    			PropertyUtils.setNestedProperty(tempEntity, fieldAry[0], "");
			} catch (Exception e) {
				try {
					PropertyUtils.setNestedProperty(tempEntity, fieldAry[0], -1);
				} catch (Exception e1) {
				}
			} finally {
				try {
					PropertyUtils.setNestedProperty(tempEntity, selectProperty, "-��ʾ����-");
				} catch (Exception e) {					
				}
			}		
    		
			Map map = new HashMap();
			TransferUtils.copy(fieldAry, tempEntity, map, null);
			
    		result.add(map);
    	}  
    	
    	for (Object obj : tempResult) {
    		Map map = new HashMap();
    		TransferUtils.copy(fieldAry, obj, map, null);
    		
    		result.add(map);
    	}
    	
		String jsonStr = JsonHelper.formatObjectToJsonString(result);
		jsonStr = convertJsonDataToSimpleData(jsonStr);
		
		buf.append(jsonStr);
    	buf.append("});");
    	
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException ioe) {
			throw new JspTagException(ioe.toString(), ioe);
		}
    	
    	release();
		return SKIP_BODY;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	@Override
	public void release() {
		id = null;
		name = null;
		model = null;
		
		convertScriptMap.clear();
		
		super.release();
	}
	
	private String convertScript(String fieldName) {
		if (convertScriptMap.containsKey(fieldName))
			return ", convert: " + convertScriptMap.get(fieldName);
		else
			return "";
	}
	
	private void addOrder(Condition condition, String order) {
		String[] items = order.split(",");
		
		if (items.length > 1 && Order.ORDER_DESC.equals(items[1].toLowerCase()))
			condition.desc(items[0]);
    	else
    		condition.asc(items[0]);
	}
	
	/**
	 * ת��JSON�������ַ���ΪSimpleStore�������ַ��������磺
	 * [{"id":989,"serviceItem":"���ˡ�³��"},{"id":990,"serviceItem":"ˮ��"},{"id":991,"serviceItem":"��ˮ����"}]
	 * ת��Ϊ
	 * [[989,'���ˡ�³��'],[990,'ˮ��'],[991,'��ˮ����']]
	 *
	 * @param jsonData
	 * @return
	 */
	private String convertJsonDataToSimpleData(String jsonData) {
		//����ת��ǰ����'{ ,'����'"'��ͷ����':'��β���ַ������ÿ��ַ����滻
		jsonData = jsonData.replaceAll("\"[^{,:]+?:", "");
		jsonData = jsonData.replace('{', '[');
		jsonData = jsonData.replace('}', ']');
		jsonData = jsonData.replace('"', '\'');
		
		return jsonData;
	}
}
