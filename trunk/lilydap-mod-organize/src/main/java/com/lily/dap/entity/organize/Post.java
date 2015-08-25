package com.lily.dap.entity.organize;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.lily.dap.entity.BaseEntity;

/**   
 *    
 * 岗位信息实体类
 *   
 * zouxuemo
 * 邹学模
 * 2011-2-16 下午01:51:48  
 *   
 * @version 1.0.0  
 *    
 */
public class Post extends BaseEntity {
	private static final long serialVersionUID = -4411944529575216189L;
	
	private String departmentCode;
	
	private int sn = 0;
    
    private String code = "";
    
    private String name = "";
    
	private int type;

    private String clazz = "";
    
    private String des = "";

	private int state;
    
    private String privateRoleCode = "";
    
    @Transient
    private List<Person> persons = new ArrayList<Person>();
	
	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return null;
	}
}
