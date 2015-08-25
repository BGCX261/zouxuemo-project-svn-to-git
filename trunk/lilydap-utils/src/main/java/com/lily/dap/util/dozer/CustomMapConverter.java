package com.lily.dap.util.dozer;

import java.util.Map;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;

/**
 * ���Դ��ΪMap�Ķ���֮���ֶ�ӳ�䴦�������Map�������ݵ�Beanʱ���Bean����������Map��û�ж�Ӧkey����������Ϊnullֵ
 * 
 * ʹ�÷�ʽ��
 * 		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
 * 		CustomFieldMapper customFieldMapper = new CustomMapConverter();
 * 		((DozerBeanMapper) mapper).setCustomFieldMapper(customFieldMapper);
 * 
 * @author zouxuemo
 *
 */
public class CustomMapConverter implements CustomFieldMapper {
	public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
		if (source instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>)source;
			
			//���Ҫ���Ƶ��ֶζ�Ӧ���ֶ�����Map�в����ڣ���ӳ��
			String sourceFieldKey = fieldMapping.getSrcFieldKey();
			if (!map.containsKey(sourceFieldKey))
				return true;
		}
		
		return false;
	}
}
