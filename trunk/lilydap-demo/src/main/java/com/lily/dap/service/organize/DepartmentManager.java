package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataHaveIncludeException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.DataNotIncludeException;

/**
 * <code>DepartmentManager</code>
 * <p>����/��λ����ӿ�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-19
 */
public interface DepartmentManager extends Manager {
	/**
     * ������������Ϣ
     * 
     * @return ���ؼ������Ĳ�����Ϣ
     * @throws DataNotExistException �������ݲ������쳣
     */
    public Department getRootDepartment() throws DataNotExistException;

	/**
	 * �жϸ����Ĳ����Ƿ������
	 * 
	 * @param department
	 * @return
	 */
	public boolean isRootDepartment(Department department);
	
    /**
     * ����ָ��ID�Ĳ���
     *
     * @param id
     * @return
     * @throws DataNotExistException
     */
    public Department getDepartment(long id) throws DataNotExistException;

	/**
	 * ����ָ��level�Ĳ���
	 *
	 * @param level
	 * @return
	 * @throws DataNotExistException
	 */
	public Department getDepartment(String level)
			throws DataNotExistException;
    
//	/**
//	 * ���ݲ������ͻ�ȡ������Ϣ�б�
//	 * 
//	 * @param type ��������
//	 * @return List{Department}
//	 */
//	public List<Department> getDepartments(int type);
    
	/**
	 * ����ָ�������µ��¼����ż���
	 *
	 * @param parent_id Ҫ�����ĸ�����ID
	 * @param notIncludeChild �Ƿ񲻼����Ӳ��ŵ��¼����ţ�Ϊtrue����ֻ������һ���¼����ż��ϣ�Ϊfalse������������¼����ż����б�
	 * @return
	 */
	public List<Department> getChilds(long parent_id, boolean notIncludeChild);
	
	/**
	 * ��ȡ�������ŵ�ȫ·��������ʽ�磺xxx���ţ�xxx���ţ�...��xxx���ţ�
	 *
	 * @param id
	 * @return
	 */
	public String getDepartmentFullName(long id);
	
	/**
	 * ����ָ��ID�ĸ�λ
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public Post getPost(long id) throws DataNotExistException;

	/**
	 * ����ָ��code�ĸ�λ
	 *
	 * @param code
	 * @return
	 * @throws DataNotExistException
	 */
	public Post getPost(String code) throws DataNotExistException;
	
	/**
	 * ��ȡ�����»�������¼����ŵ����и�λ
	 * 
	 * @param depart_id ����id
	 * @param notIncludeChild �Ƿ񲻼������Ӳ��ŵĸ�λ��Ϊtrue����ֻ���������ż��ϣ�Ϊfalse������������¼����ż����б�
	 * @return List{Post}
	 */
	public List<Post> getPosts(long depart_id, boolean notIncludeChild);
	
//	/**
//	 * ��ʼ����֯������������������
//	 *
//	 * @param clazz �����ŷ���
//	 * @param code �����ű��
//	 * @param name ����������
//	 * @param des ������˵��
//	 * @return
//	 */
//	public Department initOrganize(int clazz, String code, String name, String des);
	
	/**
	 * ����������Ϣ
	 *
	 * @param department
	 * @return
	 * @throws DataNotExistException �ϼ����Ų����ڣ��׳��쳣
	 */
	public Department createDepartment(Department department) throws DataNotExistException;
	
	/**
	 * �޸Ĳ�����Ϣ���޸ģ�code��name��clazz��des��
	 *
	 * @param department
	 * @throws DataNotExistException Ҫ�޸ĵĲ��Ų������׳��쳣
	 */
	public void modifyDepartment(Department department) throws DataNotExistException;
	
	/**
	 * ɾ�����ţ����Ҫ��ǿ��ɾ������ͬʱɾ�����¼����š���λ���Լ���λ����Ա�Ĺ���
	 *
	 * @param id
	 * @param forceRemove �Ƿ�ǿ��ɾ����������������Ӳ��ź͸�λ������Ϊtrue����ͬʱɾ���¼����š���λ���Լ���λ����Ա�Ĺ�����Ϊfalse�����׳��쳣
	 * @throws DataNotExistException Ҫɾ���Ĳ��Ų������׳��쳣
	 * @throws DataHaveUsedException �����°����Ӳ��Ż��߸�λ���׳��쳣
	 */
	public void removeDepartment(long id,boolean forceRemove) throws DataNotExistException,DataHaveUsedException;
	
	/**
	 * ������������˳��ͬһ�������£�
	 * 
	 * @param id Ҫ����Ĳ���ID
	 * @param order ���������������ֵ������ǰ���򣻷���������򡣸�������ֵ�����������Ĳ�ֵ��ֱ����ͷ���ߵ�β��
	 * @throws DataNotExistException �������ID�����ڣ����׳��쳣
	 */
	public void adjustDepartmentOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * ������λ��Ϣ
	 *
	 * @param post
	 * @return
	 * @throws DataNotExistException ��λ�������Ų����ڣ��׳��쳣
	 */
	public Post createPost(Post post) throws DataNotExistException;
	
	/**
	 * �޸ĸ�λ��Ϣ���޸ģ�code��name��clazz��des��
	 *
	 * @param post
	 * @throws DataNotExistException Ҫ�޸ĵĸ�λ�������׳��쳣
	 */
	public void modifyPost(Post post) throws DataNotExistException;
	
	/**
	 * ɾ����λ�����Ҫ��ǿ��ɾ������ͬʱɾ�����������Ա
	 *
	 * @param id
	 * @param forceRemove �Ƿ�ǿ��ɾ���������λ������Ա������Ϊtrue����ͬʱȥ����λ��Ա�Ĺ�����Ϊfalse�����׳��쳣
	 * @throws DataNotExistException Ҫɾ���ĸ�λ�������׳��쳣
	 * @throws DataHaveUsedException ��λ�°�����Ա���׳��쳣
	 */
	public void removePost(long id,boolean forceRemove) throws DataNotExistException,DataHaveUsedException;
	
	/**
	 * ������λ����˳��ͬһ�����£�
	 * 
	 * @param id Ҫ����ĸ�λID
	 * @param order ���������������ֵ������ǰ���򣻷���������򡣸�������ֵ�����������Ĳ�ֵ��ֱ����ͷ���ߵ�β��
	 * @throws DataNotExistException �������ID�����ڣ����׳��쳣
	 */
	public void adjustPostOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * ��Ӹ�λ��Ա
	 * 
	 * @param post ��λ
	 * @param person ��Ա
	 * @throws DataNotExistException ��λ����Ա�������׳��쳣
	 * @throws DataHaveIncludeException ��λ�Ѿ�������Ա���׳��쳣
	 */
	public void addPostHavePerson(Post post, Person person) throws DataNotExistException, DataHaveIncludeException;
	
	/**
	 * �Ƴ���λ��Ա
	 * 
	 * @param post ��λ
	 * @param person ��Ա
	 * @throws DataNotExistException ��λ����Ա�������׳��쳣
	 * @throws DataHaveIncludeException ��λ��������Ա���׳��쳣
	 */
	public void removePostHavePerson(Post post, Person person) throws DataNotExistException, DataNotIncludeException;
	
	/**
	 * ��ȡ��Ա��Ӧ�ĸ�λ��Ϣ�б�
	 * 
	 * @param person_username
	 * @return
	 */
	public List<Post> getPostsByPerson(String person_username);
	
	/**
	 * ��ȡ��Ա��Ӧ�Ĳ�����Ϣ�б�
	 *
	 * @param person_username
	 * @return
	 */
	public List<Department> getDepartmentsByPerson(String person_username);
	
	/**
	 * ����ָ�����ż����¼����š�����ָ����λ��������Ա
	 * 
	 * @param depart_id ����id
	 * @param post_id ��λid
	 * @param notIncludeChild ��Լ���������Աʱ���Ƿ񲻼����Ӳ��ŵ���Ա��Ϊtrue����ֻ������ǰ������Ա��Ϊfalse������������¼�������Ա�б�
	 * @param onlyActivate �Ƿ�ֻ�����״̬����Ա��stateΪSTATE_NORMAL����Ա��
	 * @return List{Person}
	 */
	public List<Person> getPersons(long depart_id, long post_id, boolean notIncludeChild, boolean onlyActivate);
}
