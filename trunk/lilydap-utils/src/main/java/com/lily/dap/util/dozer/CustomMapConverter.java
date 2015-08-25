package com.lily.dap.util.dozer;

import java.util.Map;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;

/**
 * 针对源类为Map的对象之间字段映射处理，避免从Map复制数据到Bean时会把Bean中属性名在Map中没有对应key的数据设置为null值
 * 
 * 使用方式：
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
			
			//如果要复制的字段对应的字段名在Map中不存在，则不映射
			String sourceFieldKey = fieldMapping.getSrcFieldKey();
			if (!map.containsKey(sourceFieldKey))
				return true;
		}
		
		return false;
	}
}
