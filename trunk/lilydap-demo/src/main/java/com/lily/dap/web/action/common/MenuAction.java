package com.lily.dap.web.action.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.entity.common.Menu;
import com.lily.dap.service.common.MenuManager;
import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.security.RightUtils;
import com.lily.dap.web.util.JsonHelper;
import com.lily.dap.web.util.Struts2Utils;

@Results({
		  @Result(name = "success", location = "/WEB-INF/page/system/menuMgr.jsp"),
		  @Result(name = "portal", location = "/portal.jsp")
		})
public class MenuAction extends BaseAction {
	/**
	 * <code>serialVersionUID</code> 描述字段名称。
	 */
	private static final long serialVersionUID = 3576160143085426203L;
	@Autowired
	private MenuManager menuManager;
	
	
	  public String execute() throws Exception {
			return SUCCESS;
	    }

	    public String portal() throws Exception {
	    	return "portal";
	    }
	    
	 public String menu(){
			String loadCode = "";	//ParamUtils.getParameter(request, "loadCode");
	    	String uid = Struts2Utils.getParameter("id");//此处菜单关联采用UID
			
	    	String jsonStr;
	    	if ("".equals(uid)) {
	        	List<Menu> menuList = menuManager.gets(loadCode, String.valueOf(uid));

	        	StringBuffer buf = new StringBuffer();
	        	buf.append("[");
	        	
	        	boolean first = true;
	    		for (Menu menu : menuList) {
	    			
	    			if (!RightUtils.rbacAuthorize(menu.getAccessRight()))
	    				continue;
	    			
	    			if (first)
	    				first = false;
	    			else
	    				buf.append(", ");
	    			
	        		buf.append("{");
	        		buf.append("id: \"").append(menu.getUid()).append("\", ");
	        		buf.append("text: \"").append(menu.getText()).append("\", ");
	        		buf.append("icon: \"").append(menu.getIcon()).append("\"");
	        		buf.append("}");
	    		}
	    		
	    		buf.append("]");
	    		jsonStr = buf.toString();
	    	} else {
	    		List<Menu> menuList = menuManager.loadMenuTree(loadCode, String.valueOf(uid));
	    		
	    		jsonStr = createMenuConfigJSON(menuList);
	    		if (jsonStr == null)
	    			jsonStr = "[]";
	    	}
			
			Struts2Utils.renderHtml(jsonStr);
			return null;
	    }
	 
	 private String createMenuConfigJSON(List<Menu> menuList) {
	    	StringBuffer buf = new StringBuffer();
	    	buf.append("[");
	    	
	    	boolean first = true;
	    	for (Menu menu : menuList) {
	    		
				if (!RightUtils.rbacAuthorize(menu.getAccessRight()))
					continue;
				
	    		if (first)
	    			first = false;
	    		else
	    			buf.append(", ");
	    		
	    		buf.append("{");
	    		buf.append("id: \"").append(menu.getId()).append("\", ");
	    		buf.append("text: \"").append(menu.getText()).append("\", ");
	    		buf.append("icon: \"").append(menu.getIcon()).append("\", ");
	    		buf.append("qtip: \"").append(menu.getDescription()).append("\", ");
	    		
	    		Menu tempMenu = null;
	    		tempMenu = menuManager.get(menu.getParentUid());

	    		if(tempMenu.getParentUid() == null || "".equals(tempMenu.getParentUid())){
	    			buf.append("expanded: true, ");
	    		}else{
	    			buf.append("expanded: true, ");
	    		}    	
	    		
	    		String link = menu.getLink();
	    		if (link == null) {
	    			buf.append("link: \"\", ");
	    		} else {
	    			buf.append("link: \"").append(link).append("\", ");
	    		}
	    		
	    		String childsJSON = createMenuConfigJSON(menu.getChilds());
	    		
	    		if (childsJSON == null)
	    			buf.append("leaf: true");
	    		else {
	    			buf.append("children: ").append(childsJSON).append("");
	    		}
	    		
	    		buf.append("}");
	    	}
	    	
	    	if (buf.length() == 1)
	    		return null;
	    	
	    	buf.append("]");
	    	return buf.toString();
	    }
	 
	 public String list() {
			String loadCode = Struts2Utils.getParameter("loadCode");
			String parentUid = Struts2Utils.getParameter("parentUid");

			List<Menu> menuList = menuManager.gets(loadCode, String.valueOf(parentUid));
			int count = menuList.size();

			String jsonStr = JsonHelper.combinSuccessJsonString("totalCount",count,"data",menuList);
			
			Struts2Utils.renderHtml(jsonStr);
	   		return null;
	    }
	 
	 public String hierarchical() throws Exception {
			String uid = Struts2Utils.getParameter("uid");
			
			List<Menu> hierarchicals = new ArrayList<Menu>();

			String jsonStr;
			if (!"".equals(uid)) {
				try {
					while (!"".equals(uid)) {
						Menu menu = menuManager.get(uid);
						hierarchicals.add(0, menu);
						
						uid = menu.getParentUid();
					}
				} catch (Exception e) {
					jsonStr = JsonHelper.combinFailJsonString(e.getMessage());
				}
			}
			
			Menu menu = new Menu();
			menu.setId(0);
			menu.setUid("");
			menu.setParentUid("0");
			menu.setText("根菜单");
			hierarchicals.add(0, menu);
			
			if (hierarchicals.size() > 0) {
				jsonStr = JsonHelper.combinSuccessJsonString("hierarchical",hierarchicals);
			} else 
			jsonStr = JsonHelper.combinSuccessJsonString("hierarchical","[]");
			
			Struts2Utils.renderHtml(jsonStr);
	   		return null;
	    }
	 
	 public String get() {
			String loadCode = Struts2Utils.getParameter("loadCode");
			long parentUid = Struts2Utils.getLongParameter("parentUid", 0);
			long id = Struts2Utils.getLongParameter("id", 0);

	    	String jsonStr = JsonHelper.combinSuccessJsonString();
	    	try {
	    		Menu menu;

	    		if (id  > 0) {
		    		menu = menuManager.get(id);
	    		} else {
	    			menu = menuManager.createMenu(loadCode, parentUid);
	    		}

	    		List<Menu> menuList = new ArrayList<Menu>();
	    		menuList.add(menu);
	    		
	    		jsonStr = JsonHelper.combinSuccessJsonString("data",menuList);
			} catch (Exception e) {
				jsonStr = JsonHelper.combinFailJsonString(e.getMessage());
			}

			Struts2Utils.renderHtml(jsonStr);
	   		return null;
	    }
	 
	 /**
	     *调整菜单顺序以改变结果列显示时的顺序
	     * 
	     */
	    public String changeMenuSort() throws Exception {
	    	long id = Struts2Utils.getLongParameter("id", 0);
			int order = Struts2Utils.getIntParameter("order", 0);
			 
			menuManager.adjustMenuOrder(id, order);
			
			return null;
	    }
	    
	    public String save()throws Exception {
	    	long id = Struts2Utils.getLongParameter("id", 0);
	    	String jsonStr = JsonHelper.combinSuccessJsonString();
	    	String[] changedFields;
	    	Menu menu;
	    	try {
	    		if (id > 0) {
	    			menu = menuManager.get(id);
	    			changedFields = BaseEntityHelper.getEntityAllowModifyFields(Menu.class);
	    			
	    			Struts2Utils.writeModel(menu, changedFields);
	    			menuManager.modifyMenu(menu);
	    		} else {
	    			menu = new Menu();
	    			changedFields = BaseEntityHelper.getEntityAllowCreateFields(Menu.class);
	    			Struts2Utils.writeModel(menu, changedFields);
	    			menu = menuManager.addMenu(menu);
	    		}

	    		jsonStr = JsonHelper.combinSuccessJsonString("id",menu.getId());
			} catch (Exception e) {
				jsonStr = JsonHelper.combinFailJsonString(e.getMessage());
			}

			Struts2Utils.renderHtml(jsonStr);
	    	return null;
	    }
	    
	    public String remove() throws Exception {
			long id = Struts2Utils.getLongParameter("id", 0);

	    	String jsonStr = JsonHelper.combinSuccessJsonString("id",id);

			try {
				menuManager.removeMenu(id);
			} catch (Exception e) {
				jsonStr = JsonHelper.combinFailJsonString(e.getMessage());
			}

			Struts2Utils.renderHtml(jsonStr);
	   		return null;
	}
}
