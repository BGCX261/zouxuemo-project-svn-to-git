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
 * ʵ������������ֶη��ʹ����࣬�ṩ��ʵ��������Ա�������ע��Ķ�ȡ����
 * Ϊ�˼ӿ�����ٶȣ���ʵ����������ṩ����֧�֣��ڵ�һ�η���ʵ������ʱ�����Դ��뻺�棬���Ժ����ʱֱ�Ӵӻ����ȡ
 *  
 *
 * @author zouxuemo
 */
public class BaseEntityHelper {
	private static Map<String, Class<? extends BaseEntity>> entityClassCache = new HashMap<String, Class<? extends BaseEntity>>();
	
	private static Map<String, BaseEntityPropertity> entityPropertityCache = new HashMap<String, BaseEntityPropertity>();
	
	/**
	 * ����ʵ���࣬����ʵ������<br>
	 * ʵ�����ַ����İ�������ʡ��ǰ�沿��"com.lily.dap.entity."�����磺exam.ExamScope<br>
	 * �������Ϊ�ա������಻���ڡ������಻��ʵ���࣬���׳�IllegalArgumentException�쳣<br>
	 * 
	 * @param entityName ʵ������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends BaseEntity> parseEntity(String entityName) {
		Assert.hasText(entityName, "δָ��ʵ�������ƣ�");
		
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
		
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		Assert.isAssignable(BaseEntity.class, entityClass, "�������಻����ʵ���࣡");
		
		entityClassCache.put(entityName, (Class<? extends BaseEntity>)entityClass);
		return (Class<? extends BaseEntity>)entityClass;
	}
	
	/**
	 * ����ʵ������ǩע��
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param useClassNameWhileNotDefine ��EntityPropertityע��δ�����label����ֵΪ���ַ���ʱ�����������Ϊtrue�������������棬����Ϊ���ַ���
	 * @return
	 */
	public static String getEntityLabel(Class<? extends BaseEntity> entityClass, boolean useClassNameWhileNotDefine) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		if (baseEntityPropertity.entityLabel.length() == 0 && useClassNameWhileNotDefine)
			return baseEntityPropertity.entitySimpleName;
		
		return baseEntityPropertity.entityLabel;
	}
	
	/**
	 * ����ʵ������ĳ�������ֶεı�ǩע��
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param fieldName ʵ���������ֶ���
	 * @param useFieldNameWhileNotDefine ��FieldPropertityע��δ�����label����ֵΪ���ַ���ʱ�����������Ϊtrue�������ֶ������棬����Ϊ���ַ���
	 * @return
	 */
	public static String getEntityFieldLabel(Class<? extends BaseEntity> entityClass, String fieldName, boolean useFieldNameWhileNotDefine) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		Assert.hasText(fieldName, "fieldName����Ϊ��");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldNameָ������������в����ڣ�");
		
		if (baseEntityFieldPropertity.fieldLabel.length() == 0 && useFieldNameWhileNotDefine)
			return fieldName;
		
		return baseEntityFieldPropertity.fieldLabel;
	}
	
	/**
	 * ����ʵ�������������ֶ���������
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @return
	 */
	public static String[] getEntityFieldNames(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		String[] fieldNames = new String[baseEntityPropertity.fieldPropertitys.length];
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++)
			fieldNames[i] = baseEntityPropertity.fieldPropertitys[i].fieldName;
		
		return fieldNames;
	}
	
	/**
	 * ����ʵ��������������ֶεı�ǩע���Map���ϣ�����Map��key��Ӧ�����ֶ�����value��Ӧ��ǩע��
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param useFieldNameWhileNotDefine ��FieldPropertityע��δ�����label����ֵΪ���ַ���ʱ�����������Ϊtrue�������ֶ������棬����Ϊ���ַ���
	 * @return
	 */
	public static Map<String, String> getEntityFieldLabels(Class<? extends BaseEntity> entityClass, boolean useFieldNameWhileNotDefine) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
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
	 * ����ʵ������ĳ�������ֶε��п��ע��
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param fieldName ʵ���������ֶ���
	 * @param defaultColumnWidth ��FieldPropertityע��δ�����columnWidth����ֵΪ0ʱ�����ص�Ĭ���п��ֵ
	 * @return
	 */
	public static int getEntityFieldColumnWidth(Class<? extends BaseEntity> entityClass, String fieldName, int defaultColumnWidth) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		Assert.hasText(fieldName, "fieldName����Ϊ��");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldNameָ������������в����ڣ�");
		
		if (baseEntityFieldPropertity.columnWidth == 0)
			return defaultColumnWidth;
		
		return baseEntityFieldPropertity.columnWidth;
	}
	
	/**
	 * ����ʵ��������������ֶε��п��ע���Map���ϣ�����Map��key��Ӧ�����ֶ�����value��Ӧ�п��ע��
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param defaultColumnWidth ��FieldPropertityע��δ�����columnWidth����ֵΪ0ʱ�����õ�Ĭ���п��ֵ
	 * @return
	 */
	public static Map<String, Integer> getEntityFieldColumnWidths(Class<? extends BaseEntity> entityClass, int defaultColumnWidth) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
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
	 * ����ʵ������ĳ�������ֶ��Ƿ�����ֱ�Ӵ���ע��<br>
	 * ��FieldPropertityע��δ���壬Ĭ�Ϸ���������ֱ�Ӵ���
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param fieldName ʵ���������ֶ���
	 * @return
	 */
	public static boolean isEntityFieldAllowCreate(Class<? extends BaseEntity> entityClass, String fieldName) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		Assert.hasText(fieldName, "fieldName����Ϊ��");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldNameָ������������в����ڣ�");
		
		return baseEntityFieldPropertity.allowCreate;
	}
	
	/**
	 * ����ʵ�����������ֱ�Ӵ����������ֶ�������<br>
	 * ��FieldPropertityע��δ���壬Ĭ��������ֱ�Ӵ���
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @return
	 */
	public static String[] getEntityAllowCreateFields(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		
		List<String> allowCreateList = new ArrayList<String>();
		for (int i = 0, len = baseEntityPropertity.fieldPropertitys.length; i < len; i++) {
			if (baseEntityPropertity.fieldPropertitys[i].allowCreate)
				allowCreateList.add(baseEntityPropertity.fieldPropertitys[i].fieldName);
		}
		
		return allowCreateList.toArray(new String[0]);
	}
	
	/**
	 * ����ʵ������ĳ�������ֶ��Ƿ�����ֱ���޸�ע��<br>
	 * ��FieldPropertityע��δ���壬Ĭ�Ϸ���������ֱ���޸�
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @param fieldName ʵ���������ֶ���
	 * @return
	 */
	public static boolean isEntityFieldAllowModify(Class<? extends BaseEntity> entityClass, String fieldName) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		Assert.hasText(fieldName, "fieldName����Ϊ��");
		
		BaseEntityPropertity baseEntityPropertity = obtainEntityPropertity(entityClass);
		BaseEntityFieldPropertity baseEntityFieldPropertity = findFieldPropertity(baseEntityPropertity, fieldName);
		Assert.notNull(baseEntityFieldPropertity, "fieldNameָ������������в����ڣ�");
		
		return baseEntityFieldPropertity.allowModify;
	}
	
	/**
	 * ����ʵ�����������ֱ���޸ĵ������ֶ�������<br>
	 * ��FieldPropertityע��δ���壬Ĭ��������ֱ���޸�
	 *
	 * @param entityClass �̳���BaseEntity��ʵ����
	 * @return
	 */
	public static String[] getEntityAllowModifyFields(Class<? extends BaseEntity> entityClass) {
		Assert.notNull(entityClass, "ʵ�������Ʋ����ڣ�");
		
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