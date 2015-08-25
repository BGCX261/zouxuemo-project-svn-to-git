package com.lily.dap.dao.support;

import java.util.List;

public class SqlUtils {

	/**
	 * Split an SQL script into separate statements delimited with the provided
	 * delimiter character. Each individual statement will be added to the
	 * provided <code>List</code>.
	 * 
	 * @param script the SQL script
	 * @param delim character delimiting each statement - typically a ';'
	 * character
	 * @param statements the List that will contain the individual statements
	 */
	public static void splitSqlScript(String script, char delim, List<String> statements) {
		StringBuilder sb = new StringBuilder();
		boolean inLiteral = false;
		char[] content = script.toCharArray();
		for (int i = 0; i < script.length(); i++) {
			if (content[i] == '\'') {
				inLiteral = !inLiteral;
			}
			if (content[i] == delim && !inLiteral) {
				if (sb.length() > 0) {
					statements.add(sb.toString().trim());
					sb = new StringBuilder();
				}
			}
			else {
				sb.append(content[i]);
			}
		}
		if (sb.length() > 0) {
			statements.add(sb.toString());
		}
	}
}
