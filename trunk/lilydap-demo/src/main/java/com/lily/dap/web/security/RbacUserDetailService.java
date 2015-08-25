/*
 * package com.lily.dap.web.security;
 * class UserDetailservice
 * 
 * 创建日期 2006-2-28
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.web.security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 基于RBAC机制，给用户认证使用的用户登陆信息获取服务类
 * 
 * @author zouxuemo
 *
 */
public class RbacUserDetailService implements UserDetailsService {
    private static final String DEFAULT_USER_TYPE = "person";
    
    private String rolePrefix = "ROLE_";
    
    private Map<String, DetailServiceHandle> detailServiceHandleMap = new HashMap<String, DetailServiceHandle>();
    	
	/**
	 * @param rolePrefix the rolePrefix to set
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/**
	 * 读取用户装载处理实现，并添加到Map中
	 *
	 * @param handles
	 */
	public void setDetailServiceHandles(List<DetailServiceHandle> handles) {
		for (DetailServiceHandle handle : handles) {
			String supportType = handle.supportUserType();
			
			detailServiceHandleMap.put(supportType, handle);
		}
	}

	/* （非 Javadoc）
     * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
    	//----------------加密狗加密方式加密段开始---------------------
      //当系统需要加密时，解开如下代码，并将dogjava.dll拷贝到web-inf/classes下面
    	/*try{
    		JavaDOG dog = new JavaDOG();
        	if(!dog.isCheckOk())
        		throw new InsufficientAuthenticationException("此系统未经授权,请使用正版软件!");
    	}catch(Exception e){
    		throw new InsufficientAuthenticationException("此系统未经授权,请使用正版软件!");
    	}*/
    	//----------------加密狗加密段结束---------------------  	
    	
    	//----------------注册码软加密方式加密段开始---------------------
/*    	
    	String errorMSG = "";
    	//根据注册码方式加密，当系统需要加密时，解开如下代码。
    	try{
    		//判断是否已经正确注册，如果未注册不允许登录。
    		if(!MachineUtil.checkEncrypt()){
    			errorMSG = "此系统未经注册授权,请使用正版软件!";
    		}
    		//判断当前的注册文件与数据库是否同步，如果不同步则说明用户修改了其中一个的信息，不允许登陆。
    		if(!MachineUtil.checkRegandSql()){
    			errorMSG = "此系统未经注册授权,请使用正版软件!";
    		}
    		
    		//判断软件版本，如果是试用版则需要判断是否还在试用期内。
    		if(MachineUtil.getUserType().equals(MachineUtil.TRYVERSION)){
    			if(!MachineUtil.checkTrialDays()){
    				errorMSG = "此系统试用期已过,请联系开发商!";
    			}
    		}
    		
    		//向注册文件中写入最后登录日期
    		if(!MachineUtil.InsertDateToReg()){
    			errorMSG = "系统信息写入失败,请联系开发商!";    		
    		} 
    		
		}catch(IOException ioe){//读注册文件或读硬件信息出现错误		
			errorMSG = "此系统未经注册授权,请使用正版软件!";    		
		}catch(NativeException nativee){//读硬件信息出现错误	
			errorMSG = "系统内部错误,请联系开发商!";
		}catch(IllegalAccessException iae){//读硬件信息出现错误	
			errorMSG = "系统内部错误,请联系开发商!";
		}catch(Exception e){
			errorMSG = "系统内部错误,请联系开发商!";
		}
		
		if(!errorMSG.equals("")){
			throw new InsufficientAuthenticationException(errorMSG); 
		}  
*/    	
    	//----------------注册码软加密加密段结束---------------------
		
		//----------------培训版时间限制段开始，如果是培训版请解开注释，不是培训版需要注释起来---------------------
    	/*
    	 * 用户登录时间段限制
    	 */
    	/*Calendar now = Calendar.getInstance();
    	String nowDateStr = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	
    	try {
    		Date nowDate = formatter.parse(nowDateStr);
    		Date startDate = formatter.parse("2011-03-27");
    		Date endDate = formatter.parse("2011-07-29");
			
			if (!(nowDate.after(startDate) && nowDate.before(endDate))) {
				throw new InvalidateUserTypeException("系统已过期，请联系开发商！");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		//----------------培训版时间限制段结束---------------------
    	
    	String[] tmp = StringUtils.split(username, '|');
    	
    	String userType, userName;
    	if (tmp.length == 1) {
    		userType = DEFAULT_USER_TYPE;
    		userName = username;
    	} else {
    		userType = tmp[0];
    		userName = tmp[1];
    	}
    	
    	DetailServiceHandle handle = detailServiceHandleMap.get(userType);
    	if (handle == null)
    		throw new InvalidateUserTypeException("不识别的登录类型，请选择正确的登录类型！");
    		
    	return handle.loadUserByUsername(userName, rolePrefix);
    }
}
