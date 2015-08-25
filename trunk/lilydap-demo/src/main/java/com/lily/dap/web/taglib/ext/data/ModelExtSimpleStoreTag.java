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
 * 任意实体对象输出SimpleStore数据标签
 * 
 * @author 邹学模
 *
 * @jsp.tag name="modelExtSimpleStore"
 */
@SuppressWarnings("unchecked")
public class ModelExtSimpleStoreTag extends TagSupport implements ExtJsonReaderInterface {
	private static final long serialVersionUID = 3991067188168020447L;

	/**
	 * 生成store的变量名称，必须输入
	 */
	private String name = null;

	/**
	 * <code>id</code> Store的ID值，可选
	 */
	private String id = null;
	
	/**
	 * 要检索的对象类名，必须输入
	 */
	private String model = null;
	
	/**
	 * 要输出的字段名称字符串，多个名称之间以","分隔，必须字段
	 */
	private String fields = null;
	
	/**
	 * 条件参数1，可选
	 */
	private String cond1 = null;
	
	/**
	 * 条件参数值1，可选
	 */
	private String value1 = null;
	
	/**
	 * 条件参数2，可选
	 */
	private String cond2 = null;
	
	/**
	 * 条件参数值2，可选
	 */
	private String value2 = null;
	
	/**
	 * 条件参数3，可选
	 */
	private String cond3 = null;
	
	/**
	 * 条件参数值3，可选
	 */
	private String value3 = null;
	
	/**
	 * 排序字段1，可选
	 */
	private String order1 = null;
	
	/**
	 * 排序字段3，可选
	 */
	private String order2 = null;
	
	/**
	 * 排序字段3，可选
	 */
	private String order3 = null;
    /**
     * 查询起始记录，从0开始
     * 
     * <code>start</code>
     */
	private String start = "0";
    /**
     * 查询返回记录条数，默认为-1，表示不设置返回记录范围
     * 
     * <code>limit</code>
     */
	private String limit = "0";
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	/**
	 * 在数据最前面中增加一个数据项，为选择所有或者不选择的文本字符串。可选，默认为"-所有-"；必须和selectProperty同时用
	 */
	private String selectText = "";
	
	/**
	 * 因为通用的MODEL转换时我们并不知道哪一列是key值哪一列是value值，所以需要定义一个value值这一列。
	 * 与selectText所对应的属性，即哪个属性作为显示selectText用，必须和selectText同时用；
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
	 * 设置 id 值为 <code>id</code>.
	 * @param id 要设置的 id 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 设置 fields 值为 <code>fields</code>.
	 * @param fields 要设置的 fields 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	/**
	 * 设置 cond1 值为 <code>cond1</code>.
	 * @param cond1 要设置的 cond1 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond1(String cond1) {
		this.cond1 = cond1;
	}

	/**
	 * 设置 value1 值为 <code>value1</code>.
	 * @param value1 要设置的 value1 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * 设置 cond2 值为 <code>cond2</code>.
	 * @param cond2 要设置的 cond2 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond2(String cond2) {
		this.cond2 = cond2;
	}

	/**
	 * 设置 value2 值为 <code>value2</code>.
	 * @param value2 要设置的 value2 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * 设置 cond3 值为 <code>cond3</code>.
	 * @param cond3 要设置的 cond3 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCond3(String cond3) {
		this.cond3 = cond3;
	}

	/**
	 * 设置 value3 值为 <code>value3</code>.
	 * @param value3 要设置的 value3 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}

	/**
	 * 设置 order1 值为 <code>order1</code>.
	 * @param order1 要设置的 order1 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder1(String order1) {
		this.order1 = order1;
	}

	/**
	 * 设置 order2 值为 <code>order2</code>.
	 * @param order2 要设置的 order2 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder2(String order2) {
		this.order2 = order2;
	}

	/**
	 * 设置 order3 值为 <code>order3</code>.
	 * @param order3 要设置的 order3 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOrder3(String order3) {
		this.order3 = order3;
	}
	/**
	 * 设置 start 值为 <code>start</code>.
	 * @param start 要设置的 start 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStart(String start) {
		this.start = start;
	} 
	/**
	 * 设置 limit 值为 <code>limit</code>.
	 * @param limit 要设置的 limit 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLimit(String limit) {
		this.limit = limit;
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
	 * @param selectProperty the extendText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectProperty(String selectProperty) throws JspException {
		this.selectProperty = selectProperty;
	}
	// -------------------------------- 以下为私有变量 ----------------------------------
	/**
	 * <code>convertScriptMap</code> 定义的转换器Map
	 */
	private Map<String, String> convertScriptMap = new HashMap<String, String>();

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.ExtJsonReaderInterface#getConvertScriptMap()
	 */
	public Map<String, String> getConvertScriptMap() {
		return convertScriptMap;
	}

	/* （非 Javadoc）
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
			throw new JspException("指定的类名[" + model + "]不存在！");			
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
					PropertyUtils.setNestedProperty(tempEntity, selectProperty, "-显示所有-");
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
	 * 转换JSON的数据字符串为SimpleStore的数据字符串，例如：
	 * [{"id":989,"serviceItem":"川菜、鲁菜"},{"id":990,"serviceItem":"水饺"},{"id":991,"serviceItem":"无水蛋糕"}]
	 * 转换为
	 * [[989,'川菜、鲁菜'],[990,'水饺'],[991,'无水蛋糕']]
	 *
	 * @param jsonData
	 * @return
	 */
	private String convertJsonDataToSimpleData(String jsonData) {
		//首先转换前面是'{ ,'，以'"'开头，以':'结尾的字符串，用空字符串替换
		jsonData = jsonData.replaceAll("\"[^{,:]+?:", "");
		jsonData = jsonData.replace('{', '[');
		jsonData = jsonData.replace('}', ']');
		jsonData = jsonData.replace('"', '\'');
		
		return jsonData;
	}
}
