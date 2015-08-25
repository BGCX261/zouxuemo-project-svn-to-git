package com.lily.dap.service;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Dao;
import com.lily.dap.service.core.BaseManager;

@Service("otherManager")
public class OtherManagerImpl extends BaseManager implements Manager {
	/**
	 * DAO²Ù×÷½Ó¿Ú
	 */
	@Autowired
	@Qualifier("otherDao")
	protected Dao otherDao;
	
	private Dao thisDao;
	
	@PostConstruct
	public void init() {
		thisDao = dao;
	}
	
	public void switchOtherDataSource() {
		switchDataSource(otherDao);
	}
	
	public void switchThisDataSource() {
		switchDataSource(thisDao);
	}
	
	public void switchDataSource(Dao switdhDao) {
		dao = switdhDao;
		
		try {
			System.out.println("switch dao's dbms url: " + dao.getSession().connection().getMetaData().getURL());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}