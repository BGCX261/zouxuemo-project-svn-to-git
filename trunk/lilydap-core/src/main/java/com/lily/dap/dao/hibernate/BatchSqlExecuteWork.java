package com.lily.dap.dao.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.jdbc.Work;

/**
 * 执行SQL更新的Work
 * 
 * 
 * date: 2010-12-2
 * 
 * @author zouxuemo
 */
public class BatchSqlExecuteWork implements Work {
	private String[] sqls = null;

	public BatchSqlExecuteWork(String[] sqls) {
		this.sqls = sqls;
	}

	public void execute(Connection connection) throws SQLException {
		Statement stmt = null;

		try {
			stmt = connection.createStatement();

           	for (String sql : sqls) {
        	   	stmt.addBatch(sql);
           	}
           
			stmt.executeBatch();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {}
		}
	}
}
