/**
 * 
 */
package com.lily.dap.dao.extension;

import java.util.Date;

/**
 * 保存时记录修改时间接口，如果实体类实现了该接口，则在保存时自动调用接口方法保存本次修改的时间
 * 
 * @author zouxuemo
 *
 */
public interface ModifyTimeRecorder {
	/**
	 * 在实体类中设置修改时间字段为当前时间
	 * 
	 * @param modifyTime 当前修改时间
	 */
	public void recordModifyTime(Date modifyTime);
}
