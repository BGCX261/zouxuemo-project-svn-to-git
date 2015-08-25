package com.lily.dap.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lily.dap.Constants;
import com.lily.dap.annotation.EntityPropertity;
import com.lily.dap.annotation.FieldPropertity;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.util.BeanUtils;

/**
 * <code>BaseEntityHelper</code>
 * 实体对象及其属性字段访问工具类，提供对实体对象属性遍历及其注解的读取访问
 * 为了加快访问速度，对实体对象属性提供缓存支持，在第一次访问实体属性时把属性存入缓存，在以后访问时直接从缓存获取
 *  
 *
 * @author zouxuemo
 */
public class BaseEntityHelper {
	private static Map<String, Class<? extends BaseEntity>> entityClassCache = new HashMap<String, Class<? extends BaseEntity>>();
	
	private static Map<String, BaseEntityPropertity> entityPropertityCache = new HashMap<String, BaseEntityPropertity>();
	
	/**
	 * 分析实体类，返回实体类型<br>
	 * 实体类字符串的包名允许省略前面部分"com.lily.dap.entity."，例如：exam.ExamScope<br>
	 * 如果类名为空、或者类不存在、或者类不是实体类，将抛出IllegalArgumentException异常<br>
	 * 
	 * @param entityName 实体类名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends BaseEntity> parseEntity(String entityName) {
		Assert.hasText(entityName, "未指定实体类名称！");
		
		if (entityClassCache.containsKey(entityName))
			return entityClassCache.get(entityName);
		
		Class<?> entityClass = null;
		try {
			entityClass = Class.forName(entityName);
		} catch (ClassNotFoundException e) {
			try {
				entityClass = Class.forName(Constants.PACKAGE_ENTITY + "." + entityName);
			} catch (ClassNotFoundException e1) {
				;
			}
		}
		
		Assert.notNull(entityClass, "实体类名称不存在！");
		Assert.isAssignable(BaseEntity.class, entityClass, "给定的类不属于实体类！");
		
		entityClassCache.put(entityName, (Class<? extends BaseEntity>)entityClass);
		return (Class<? extends BaseEntity>)entityClass;
	}
	
	/**
	 * 返回实体对象标签注解
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param useClassNameWhileNotDefine 当EntityPropertity注解未定义或label属性值为空字符串时，如果本参数为true，则用类名代替，否则为空字符串
	 * @return
	 */
	public static String getEntityLabel(Class<? extends BaseEntity> entityClass, boolean useClassNameWhileNotDefine) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		if (baseEntityPropertity.entityLabel.length() == 0 && useClassNameWhileNotDefine)
			return baseEntityPropertity.entitySimpleName;
		
		return baseEntityPropertity.entityLabel;
	}
	
	/**
	 * 返回实体对象的某个属性字段的标签注解
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param fieldName 实体类属性字段名
	 * @param useFieldNameWhileNotDefine 当FieldPropertity注解未定义或label属性值为空字符串时，如果本参数为true，则用字段名代替，否则为空字符串
	 * @return
	 */
	public static String getEntityFieldLabel(Class<? extends BaseEntity> entityClass, String fieldName, boolean useFieldNameWhileNotDefine) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		Assert.hasText(fieldName, "fieldName不能为空");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldName指向的属性在类中不存在！");
		
		if (baseEntityFieldPropertity.fieldLabel.length() == 0 && useFieldNameWhileNotDefine)
			return fieldName;
		
		return baseEntityFieldPropertity.fieldLabel;
	}
	
	/**
	 * 返回实体对象包含属性字段名称数组
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @return
	 */
	public static String[] getEntityFieldNames(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		String[] fieldNames = new String[baseEntityPropertity.fieldPropertitys.length];
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++)
			fieldNames[i] = baseEntityPropertity.fieldPropertitys[i].fieldName;
		
		return fieldNames;
	}
	
	/**
	 * 返回实体对象所有属性字段的标签注解的Map集合，其中Map的key对应属性字段名，value对应标签注解
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param useFieldNameWhileNotDefine 当FieldPropertity注解未定义或label属性值为空字符串时，如果本参数为true，则用字段名代替，否则为空字符串
	 * @return
	 */
	public static Map<String, String> getEntityFieldLabels(Class<? extends BaseEntity> entityClass, boolean useFieldNameWhileNotDefine) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		Map<String, String> fieldLabelMap = new HashMap<String, String>();
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++) {
			String fieldName = baseEntityPropertity.fieldPropertitys[i].fieldName;
			String fieldLabel = baseEntityPropertity.fieldPropertitys[i].fieldLabel;
			
			if (fieldLabel.length() == 0 && useFieldNameWhileNotDefine)
				fieldLabel = fieldName;
			
			fieldLabelMap.put(fieldName, fieldLabel);
		}
		
		return fieldLabelMap;
	}
	
	/**
	 * 返回实体对象的某个属性字段的列宽度注解
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param fieldName 实体类属性字段名
	 * @param defaultColumnWidth 当FieldPropertity注解未定义或columnWidth属性值为0时，返回的默认列宽度值
	 * @return
	 */
	public static int getEntityFieldColumnWidth(Class<? extends BaseEntity> entityClass, String fieldName, int defaultColumnWidth) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		Assert.hasText(fieldName, "fieldName不能为空");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldName指向的属性在类中不存在！");
		
		if (baseEntityFieldPropertity.columnWidth == 0)
			return defaultColumnWidth;
		
		return baseEntityFieldPropertity.columnWidth;
	}
	
	/**
	 * 返回实体对象所有属性字段的列宽度注解的Map集合，其中Map的key对应属性字段名，value对应列宽度注解
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param defaultColumnWidth 当FieldPropertity注解未定义或columnWidth属性值为0时，设置的默认列宽度值
	 * @return
	 */
	public static Map<String, Integer> getEntityFieldColumnWidths(Class<? extends BaseEntity> entityClass, int defaultColumnWidth) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		Map<String, Integer> columnWidthMap = new HashMap<String, Integer>();
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++) {
			String fieldName = baseEntityPropertity.fieldPropertitys[i].fieldName;
			int columnWidth = baseEntityPropertity.fieldPropertitys[i].columnWidth;
			
			if (columnWidth == 0)
				columnWidth = defaultColumnWidth;
			
			columnWidthMap.put(fieldName, columnWidth);
		}
		
		return columnWidthMap;
	}
	
	/**
	 * 返回实体对象的某个属性字段是否允许直接创建注解<br>
	 * 当FieldPropertity注解未定义，默认返回是允许直接创建
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param fieldName 实体类属性字段名
	 * @return
	 */
	public static boolean isEntityFieldAllowCreate(Class<? extends BaseEntity> entityClass, String fieldName) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		Assert.hasText(fieldName, "fieldName不能为空");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldName指向的属性在类中不存在！");
		
		return baseEntityFieldPropertity.allowCreate;
	}
	
	/**
	 * 返回实体对象中允许直接创建的属性字段名数组<br>
	 * 当FieldPropertity注解未定义，默认是允许直接创建
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @return
	 */
	public static String[] getEntityAllowCreateFields(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		List<String> allowCreateList = new ArrayList<String>();
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++) {
			if (baseEntityPropertity.fieldPropertitys[i].allowCreate)
				allowCreateList.add(baseEntityPropertity.fieldPropertitys[i].fieldName);
		}
		
		return allowCreateList.toArray(new String[0]);
	}
	
	/**
	 * 返回实体对象的某个属性字段是否允许直接修改注解<br>
	 * 当FieldPropertity注解未定义，默认返回是允许直接修改
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @param fieldName 实体类属性字段名
	 * @return
	 */
	public static boolean isEntityFieldAllowModify(Class<? extends BaseEntity> entityClass, String fieldName) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		Assert.hasText(fieldName, "fieldName不能为空");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldName指向的属性在类中不存在！");
		
		return baseEntityFieldPropertity.allowModify;
	}
	
	/**
	 * 返回实体对象中允许直接修改的属性字段名数组<br>
	 * 当FieldPropertity注解未定义，默认是允许直接修改
	 *
	 * @param entityClass 继承于BaseEntity的实体类
	 * @return
	 */
	public static String[] getEntityAllowModifyFields(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "实体类名称不存在！");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		List<String> allowModifyList = new ArrayList<String>();
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++) {
			if (baseEntityPropertity.fieldPropertitys[i].allowModify)
				allowModifyList.add(baseEntityPropertity.fieldPropertitys[i].fieldName);
		}
		
		return allowModifyList.toArray(new String[0]);
	}
	
	private static BaseEntityPropertity obtainEntityPropertity(Class<? extends BaseEntity> entityClass) {
		String entityName = entityClass.getName();
		
		BaseEntityPropertity baseEntityPropertity = entityPropertityCache.get(entityName);
		if (baseEntityPropertity == null) { 
			baseEntityPropertity = parseEntity(entityClass);
			
			entityPropertityCache.put(entityName, baseEntityPropertity);
		}
		
		return baseEntityPropertity;
	}
	
	private static BaseEntityPropertity parseEntity(Class<? extends BaseEntity> entityClass) {
		BaseEntityPropertity entityPropertity = new BaseEntityPropertity();
		entityPropertity.entityClass = entityClass;
		entityPropertity.entitySimpleName = entityClass.getSimpleName();

		String label = null;
		if(entityClass.isAnnotationPresent(EntityPropertity.class))
			label = entityClass.getAnnotation(EntityPropertity.class).label();
		
		if (label == null)	label = "";
		entityPropertity.entityLabel = label;
		
		List<Field> fieldList = BeanUtils.getDeclaredFields(entityClass);
		BaseEntityFieldPropertity[] baseEntityFieldPropertitys = new BaseEntityFieldPropertity[fieldList.size()];
		
		int i = 0;
		for (Field field : fieldList)
			baseEntityFieldPropertitys[i++] = parseField(field);
		
		entityPropertity.fieldPropertitys = baseEntityFieldPropertitys;
		return entityPropertity;
	}
	
	private static BaseEntityFieldPropertity parseField(Field field) {
		BaseEntityFieldPropertity baseEntityFieldPropertity = new BaseEntityFieldPropertity();
		baseEntityFieldPropertity.fieldName = field.getName();
		
		if(field.isAnnotationPresent(FieldPropertity.class)) {
			FieldPropertity fieldPropertity = field.getAnnotation(FieldPropertity.class);
			
			baseEntityFieldPropertity.fieldLabel = fieldPropertity.label();
			baseEntityFieldPropertity.columnWidth = fieldPropertity.columnWidth();
			baseEntityFieldPropertity.allowCreate = fieldPropertity.allowCreate();
			baseEntityFieldPropertity.allowModify = fieldPropertity.allowModify();
		} else {
			baseEntityFieldPropertity.fieldLabel = "";
			baseEntityFieldPropertity.columnWidth = 0;
			baseEntityFieldPropertity.allowCreate = true;
			baseEntityFieldPropertity.allowModify = true;
		}
		
		return baseEntityFieldPropertity;
	}
	
	private static BaseEntityFieldPropertity findFieldPropertity(BaseEntityPropertity baseEntityPropertity, String fieldName) {
		for (BaseEntityFieldPropertity baseEntityFieldPropertity : baseEntityPropertity.fieldPropertitys) {
			if (fieldName.equals(baseEntityFieldPropertity.fieldName))
				return baseEntityFieldPropertity;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Class<? extends BaseEntity> entityClass = BaseEntityHelper.parseEntity("Module");
		System.out.println(entityClass.getName());
		
		try {
			BaseEntityHelper.parseEntity("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			BaseEntityHelper.parseEntity("Module1");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			BaseEntityHelper.parseEntity("com.lily.dap.Constants");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

class BaseEntityPropertity {
	public Class<?> entityClass;
	
	public String entitySimpleName;
	
	public String entityLabel;
	
	public BaseEntityFieldPropertity[] fieldPropertitys;
}

class BaseEntityFieldPropertity {
	public String fieldName;
	
	public String fieldLabel;
	
	public int columnWidth;
	
	public boolean allowCreate;
	
	public boolean allowModify;
}