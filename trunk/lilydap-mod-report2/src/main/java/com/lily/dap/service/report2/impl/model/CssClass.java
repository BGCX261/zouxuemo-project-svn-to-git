/**
 * 
 */
package com.lily.dap.service.report2.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.util.convert.ConvertUtils;

/**
 * @author xuemozou
 *
 * 报表样式配置
 */
public class CssClass implements Cloneable {
	private static Map<String, Class<?>>typeClassMap = new HashMap<String, Class<?>>();
	static {
		typeClassMap.put("string", String.class);
		typeClassMap.put("short", Short.class);
		typeClassMap.put("byte", Byte.class);
		typeClassMap.put("int", Integer.class);
		typeClassMap.put("long", Long.class);
		typeClassMap.put("boolean", Boolean.class);
		typeClassMap.put("float", Float.class);
	}
/*	
     * Normal boldness (not bold)
    public final static short  BOLDWEIGHT_NORMAL   			= 0x190;

     * Bold boldness (bold)
    public final static short  BOLDWEIGHT_BOLD     			= 0x2bc;

     * normal type of black color.
    public final static short  COLOR_NORMAL        			= 0x7fff;

     * Dark Red color
    public final static short  COLOR_RED           			= 0xa;

     * not underlined
    public final static byte   U_NONE              			= 0;

     * single (normal) underline
    public final static byte   U_SINGLE            			= 1;

     * double underlined
    public final static byte   U_DOUBLE            			= 2;

     * accounting style single underline
    public final static byte   U_SINGLE_ACCOUNTING 			= 0x21;

     * accounting style double underline
    public final static byte   U_DOUBLE_ACCOUNTING 			= 0x22;

     * general (normal) horizontal alignment
    public final static short    ALIGN_GENERAL              = 0x0;

     * left-justified horizontal alignment
    public final static short    ALIGN_LEFT                 = 0x1;

     * center horizontal alignment
    public final static short    ALIGN_CENTER               = 0x2;

     * right-justified horizontal alignment
    public final static short    ALIGN_RIGHT                = 0x3;

     * fill? horizontal alignment
    public final static short    ALIGN_FILL                 = 0x4;

     * justified horizontal alignment
    public final static short    ALIGN_JUSTIFY              = 0x5;

     * center-selection? horizontal alignment
    public final static short    ALIGN_CENTER_SELECTION     = 0x6;

     * top-aligned vertical alignment
    public final static short    VERTICAL_TOP               = 0x0;

     * center-aligned vertical alignment
    public final static short    VERTICAL_CENTER            = 0x1;

     * bottom-aligned vertical alignment
    public final static short    VERTICAL_BOTTOM            = 0x2;

     * vertically justified vertical alignment
    public final static short    VERTICAL_JUSTIFY           = 0x3;
*/	
	/**
	 * 样式类名
	 */
	private String className = null;
	
	/**
	 * 样式类继承名
	 */
	private String extend = null;
	
	/**
	 * 设置的样式
	 */
	private Map<String, Style> styles = new HashMap<String, Style>();

	public CssClass(String className, String extend) {
		this.className = className;
		this.extend = extend;
	}

	public CssClass(String className, String extend, Map<String, Style> styles) {
		this.className = className;
		this.extend = extend;
		this.styles.putAll(styles);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Collection<Style> getStyles(String scope) {
		List<Style> list = new ArrayList<Style>();
		
		if (scope == null || "".equals(scope))
			list.addAll(styles.values());
		else {
			for (Style style : styles.values()) {
				String myscope = style.getScope();
				if (myscope == null || "".equals(myscope) || myscope.indexOf(scope) >= 0)
					list.add(style);
			}
		}
		
		return list;
	}

	public void setStyles(Map<String, Style> styles) {
		this.styles = styles;
	}
	
	public Map<String, Style> getStyles() {
		return styles;
	}

	public void addStyle(String name, String scope, String type, String value) {
		if (styles.containsKey(name))
			styles.remove(name);
		
		styles.put(name, new Style(name, scope, type, value));
	}
	
	public void addStyles(CssClass cssClass) {
		Map<String, Style> addStyle = cssClass.getStyles();
		for (String name : addStyle.keySet()) {
			if (styles.containsKey(name))
				styles.remove(name);
			
			styles.put(name, addStyle.get(name));
		}
	}
	
	public CssClass clone() throws CloneNotSupportedException {
		CssClass clone = (CssClass)super.clone();
		
		if (styles.size() > 0) {
			Map<String, Style> cloneStyles = new HashMap<String, Style>();
			cloneStyles.putAll(styles);
			clone.setStyles(cloneStyles);
		}
		
		return clone;
	}

	public class Style {
		private String name = null;
		
		private String scope = null;
		
		private String type = null;
		
		private Object value = null;

		public Style(String name, String scope, String type, String val) {
			this.name = name;
			this.scope = scope;
			this.type = type;
			try {
				
				this.value = ConvertUtils.convert(val, typeClassMap.get(type));
			} catch (Exception e) {
				this.value = val;
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
}
