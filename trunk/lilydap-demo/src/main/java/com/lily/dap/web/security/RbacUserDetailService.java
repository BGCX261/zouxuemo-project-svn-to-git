/*
 * package com.lily.dap.web.security;
 * class UserDetailservice
 * 
 * �������� 2006-2-28
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
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
 * ����RBAC���ƣ����û���֤ʹ�õ��û���½��Ϣ��ȡ������
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
	 * ��ȡ�û�װ�ش���ʵ�֣�����ӵ�Map��
	 *
	 * @param handles
	 */
	public void setDetailServiceHandles(List<DetailServiceHandle> handles) {
		for (DetailServiceHandle handle : handles) {
			String supportType = handle.supportUserType();
			
			detailServiceHandleMap.put(supportType, handle);
		}
	}

	/* ���� Javadoc��
     * @see org.acegisecurity.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
    	//----------------���ܹ����ܷ�ʽ���ܶο�ʼ---------------------
      //��ϵͳ��Ҫ����ʱ���⿪���´��룬����dogjava.dll������web-inf/classes����
    	/*try{
    		JavaDOG dog = new JavaDOG();
        	if(!dog.isCheckOk())
        		throw new InsufficientAuthenticationException("��ϵͳδ����Ȩ,��ʹ���������!");
    	}catch(Exception e){
    		throw new InsufficientAuthenticationException("��ϵͳδ����Ȩ,��ʹ���������!");
    	}*/
    	//----------------���ܹ����ܶν���---------------------  	
    	
    	//----------------ע��������ܷ�ʽ���ܶο�ʼ---------------------
/*    	
    	String errorMSG = "";
    	//����ע���뷽ʽ���ܣ���ϵͳ��Ҫ����ʱ���⿪���´��롣
    	try{
    		//�ж��Ƿ��Ѿ���ȷע�ᣬ���δע�᲻�����¼��
    		if(!MachineUtil.checkEncrypt()){
    			errorMSG = "��ϵͳδ��ע����Ȩ,��ʹ���������!";
    		}
    		//�жϵ�ǰ��ע���ļ������ݿ��Ƿ�ͬ���������ͬ����˵���û��޸�������һ������Ϣ���������½��
    		if(!MachineUtil.checkRegandSql()){
    			errorMSG = "��ϵͳδ��ע����Ȩ,��ʹ���������!";
    		}
    		
    		//�ж�����汾����������ð�����Ҫ�ж��Ƿ����������ڡ�
    		if(MachineUtil.getUserType().equals(MachineUtil.TRYVERSION)){
    			if(!MachineUtil.checkTrialDays()){
    				errorMSG = "��ϵͳ�������ѹ�,����ϵ������!";
    			}
    		}
    		
    		//��ע���ļ���д������¼����
    		if(!MachineUtil.InsertDateToReg()){
    			errorMSG = "ϵͳ��Ϣд��ʧ��,����ϵ������!";    		
    		} 
    		
		}catch(IOException ioe){//��ע���ļ����Ӳ����Ϣ���ִ���		
			errorMSG = "��ϵͳδ��ע����Ȩ,��ʹ���������!";    		
		}catch(NativeException nativee){//��Ӳ����Ϣ���ִ���	
			errorMSG = "ϵͳ�ڲ�����,����ϵ������!";
		}catch(IllegalAccessException iae){//��Ӳ����Ϣ���ִ���	
			errorMSG = "ϵͳ�ڲ�����,����ϵ������!";
		}catch(Exception e){
			errorMSG = "ϵͳ�ڲ�����,����ϵ������!";
		}
		
		if(!errorMSG.equals("")){
			throw new InsufficientAuthenticationException(errorMSG); 
		}  
*/    	
    	//----------------ע��������ܼ��ܶν���---------------------
		
		//----------------��ѵ��ʱ�����ƶο�ʼ���������ѵ����⿪ע�ͣ�������ѵ����Ҫע������---------------------
    	/*
    	 * �û���¼ʱ�������
    	 */
    	/*Calendar now = Calendar.getInstance();
    	String nowDateStr = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	
    	try {
    		Date nowDate = formatter.parse(nowDateStr);
    		Date startDate = formatter.parse("2011-03-27");
    		Date endDate = formatter.parse("2011-07-29");
			
			if (!(nowDate.after(startDate) && nowDate.before(endDate))) {
				throw new InvalidateUserTypeException("ϵͳ�ѹ��ڣ�����ϵ�����̣�");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		//----------------��ѵ��ʱ�����ƶν���---------------------
    	
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
    		throw new InvalidateUserTypeException("��ʶ��ĵ�¼���ͣ���ѡ����ȷ�ĵ�¼���ͣ�");
    		
    	return handle.loadUserByUsername(userName, rolePrefix);
    }
}
