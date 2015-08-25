package com.lily.dap.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

/**
 * 执行SQL更新的Work
 * 
 * 
 * date: 2010-12-2
 * 
 * @author zouxuemo
 */
public class SqlExecuteWork implements Work {
	private String sql = null;

	private Object[] values = null;

	public SqlExecuteWork(String sql, Object... values) {
		this.sql = sql;
		this.values = values;
	}

	public void execute(Connection connection) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++)
					pstmt.setObject(i + 1, values[i]);
			}

			pstmt.executeUpdate();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {}
		}
	}
}
