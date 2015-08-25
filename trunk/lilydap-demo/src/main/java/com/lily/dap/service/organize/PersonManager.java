/**
 * 
 */
package com.lily.dap.service.organize;

import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * <code>PersonManager</code>
 * ��Աע�ᡢά��������/������ͣ�ù���ӿ�
 *
 * @author ��ѧģ
 * @date 2008-3-12
 */
public interface PersonManager extends Manager {
	/**
	 * �����û���Ż�ȡ��Ա��Ϣ
	 * 
	 * @param username �û����
	 * @return Person
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public Person getPerson(String username) throws DataNotExistException;
	
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
	public Person getPerson(long id) throws DataNotExistException;
	
	/**
	 * ������Ա���󼯺�
	 *
	 * @param queryCondition
	 * @param onlyActivate �Ƿ�ֻ�����״̬����Ա��stateΪSTATE_NORMAL����Ա��
	 * @return
	 */
	public List<Person> getPersons(Condition queryCondition, boolean onlyActivate);
	
	/**
	 * �����������Ż��λ�µİ�������Ȩ�޽�ɫ����Ա���󼯺�
	 *
	 * @param depart_id Ҫ�����Ĳ���ID�����û�У���������в���
	 * @param post_id Ҫ�����ĸ�λID�����û�У���������и�λ
	 * @param haveRole Ҫ�����Ľ�ɫCODE
	 * @param onlyActivate �Ƿ�ֻ�����״̬����Ա��stateΪSTATE_NORMAL����Ա��
	 * @return
	 */
	public List<Person> getPersons(long depart_id, long post_id, String haveRole, boolean onlyActivate);
	
	/**
	 * �жϸ�����Ա�Ƿ�������������
	 *
	 * @param person_id
	 * @param haveRole
	 * @return
	 */
	public boolean isPersonHaveRole(long person_id, String haveRole);
	
	/**
	 * ��������Ա��Ϣ
	 *
	 * @param person
	 * @return
	 * @throws DataContentRepeatException �û�����ظ��������û������ظ��׳��쳣
	 */
	public Person createPerson(Person  person) throws DataContentRepeatException;
	
	/**
	 * �޸���Ա��Ϣ
	 * <p>ֻ���޸���Ա�������Ա𡢳������ڡ��ֻ����롢���õ绰������Ϣ</p>
	 *
	 * @param person
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 * @throws DataContentRepeatException �û������ظ��׳��쳣
	 */
	public void modifyPerson(Person person) throws DataNotExistException, DataContentRepeatException;
	
	/**
	 * ɾ��������Ա
	 *
	 * @param id
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public void removePerson(long id) throws DataNotExistException;
	
	/**
	 * ����/������Ա
	 *
	 * @param id
	 * @param enabled true��������Ա��false��������Ա
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public void enablePerson(long id, boolean enabled) throws DataNotExistException;
	
	/**
	 * ͣ����Ա
	 *
	 * @param id
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public void stopPerson(long id) throws DataNotExistException;
	
	/**
	 * �޸��û�������Ϣ
	 * 
	 * @param username
	 * @param password
	 * @throws DataNotExistException ������Ա�������׳��쳣
	 */
	public void changeUserPassword(String username, String password) throws DataNotExistException;

	/**
	 * �������ַ���ʹ��ϵͳ���õļ��ܷ�ʽ���м���
	 *
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password);
}
