/**
 * 
 */
package com.lily.dap.web.taglib.ext;

import java.util.List;

/**
 * �ṩExt ��Panel���Խӿ�
 * 
 * @author ��ѧģ
 *
 */
public interface ExtPanelInterface {
	/**
	 * ���� bundle.
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
