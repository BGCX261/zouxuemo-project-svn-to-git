package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.entity.organize.Department;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.entity.organize.Post;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * <code>OrganizeCacheManager</code>
 * <p>��֯�����������ӿ�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-26
 */
public interface OrganizeCacheManager {
	/**
	 * ������������Ϣ
	 * 
	 * @return ���ؼ������Ĳ�����Ϣ
	 * @throws DataNotExistException �������ݲ������쳣
	 */
	public abstract Department getRootDepartment() throws DataNotExistException;

	/**
	 * �жϸ����Ĳ����Ƿ������
	 * 
	 * @param department
	 * @return
	 */
	public abstract boolean isRootDepartment(Department department);
	
	/**
	 * ����ָ��ID�Ĳ���
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Department getDepartment(long id)
			throws DataNotExistException;

	/**
	 * ����ָ��level�Ĳ���
	 *
	 * @param level
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Department getDepartment(String level)
			throws DataNotExistException;

	/**
	 * ����ָ�������µ��¼����ż���
	 *
	 * @param parent_id Ҫ�����ĸ�����ID
	 * @param notIncludeChild �Ƿ񲻼����Ӳ��ŵ��¼����ţ�Ϊtrue����ֻ������һ���¼����ż��ϣ�Ϊfalse������������¼����ż����б�
	 * @return
	 */
	public abstract List<Department> getChilds(long parent_id,
			boolean notIncludeChild);

	/**
	 * ����ָ��ID�ĸ�λ
	 *
	 * @param id
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Post getPost(long id) throws DataNotExistException;

	/**
	 * ����ָ��code�ĸ�λ
	 *
	 * @param code
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Post getPost(String code) throws DataNotExistException;

	/**
	 * ��ȡ�����»�������¼����ŵ����и�λ
	 * 
	 * @param depart_id ����id
	 * @param notIncludeChild �Ƿ񲻼������Ӳ��ŵĸ�λ��Ϊtrue����ֻ���������ż��ϣ�Ϊfalse������������¼����ż����б�
	 * @return List{Post}
	 */
	public abstract List<Post> getPosts(long depart_id, boolean notIncludeChild);

	/**
	 * ��ȡ��Ա��Ӧ�ĸ�λ��Ϣ�б�
	 * 
	 * @param person_username
	 * @return
	 */
	public abstract List<Post> getPostsByPerson(String person_username);

	/**
	 * ��ȡ��Ա��Ӧ�Ĳ�����Ϣ�б�
	 *
	 * @param person_username
	 * @return
	 */
	public abstract List<Department> getDepartmentsByPerson(String person_username);

	/**
	 * ����ָ�����ż����¼����š�����ָ����λ��������Ա
	 * 
	 * @param depart_id ����id
	 * @param post_id ��λid
	 * @param notIncludeChild ��Լ���������Աʱ���Ƿ񲻼����Ӳ��ŵ���Ա��Ϊtrue����ֻ������ǰ������Ա��Ϊfalse������������¼�������Ա�б�
	 * @param onlyActivate �Ƿ�ֻ�����״̬����Ա��stateΪSTATE_NORMAL����Ա��
	 * @return List{Person}
	 */
	public abstract List<Person> getPersons(long depart_id, long post_id,
			boolean notIncludeChild, boolean onlyActivate);

	/**
	 * �����û���Ż�ȡ��Ա��Ϣ
	 * 
	 * @param username
	 * @return
	 * @throws DataNotExistException
	 */
	public abstract Person getPerson(String username)
			throws DataNotExistException;
	
	/**
	 * �����û����ƻ�ȡ��Ա��Ϣ
	 *
	 * @param name
	 * @return
	 * @throws DataNotExistException
	 */
	public Person getPersonByName(String name) throws DataNotExistException;

	/**
	 * ����ID��ȡ��Ա��Ϣ
	 *
	 * @param id ��ԱID
	 * @return
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public abstract Person getPerson(long id) throws DataNotExistException;

	/**
	 * ������Ա���󼯺�
	 *
	 * @param username �û���ţ�����ֵ
	 * @param name ��Ա����like����ֵ
	 * @param sex �Ա𣽸���ֵ
	 * @param mobile ���ֻ�������ֵ
	 * @param onlyActivate �Ƿ�ֻ�����״̬����Ա��stateΪSTATE_NORMAL����Ա��
	 * @return
	 */
	public abstract List<Person> getPersons(String username, String name,
			String sex, String mobile, boolean onlyActivate);

}