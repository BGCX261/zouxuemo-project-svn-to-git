/**
 * 
 */
package com.lily.dap.web.taglib.ext;

import java.util.List;

/**
 * 提供Ext 的Panel属性接口
 * 
 * @author 邹学模
 *
 */
public interface ExtPanelInterface {
	/**
	 * 返回 bundle.
	 * @return the bundle
	 */
	public String getBundle();
	
	/**
	 * @return the tbarScriptList
	 */
	public List<String> getTbarScriptList();

	/**
	 * @return the bbarScriptList
	 */
	public List<String> getBbarScriptList();
}
