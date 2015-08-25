/**
 * 
 */
package com.lily.dap.service.report2.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lily.dap.service.core.Evaluator.ComplexVariableResolverImpl;
import com.lily.dap.service.core.Evaluator.ELEvaluator;
import com.lily.dap.service.core.Evaluator.EvaluatException;
import com.lily.dap.service.core.Evaluator.FunctionMapper;
import com.lily.dap.service.core.Evaluator.VariableResolver;
import com.lily.dap.util.LilyFunctionMapperImpl;

/**
 * @author Administrator
 *
 * 对用包含符包含字段的表达式（例如："{field1}+{field2}*10"）进行处理的处理工具类
 */
public class ReportFieldTokenProcessor {
    protected final Log logger = LogFactory.getLog(getClass());
    
	/**
	 * 包含了字段的表达式
	 */
	private StringBuffer expression;
	
	/**
	 * 左包含符
	 */
	private char include_symbol_left;
	/**
	 * 右包含符
	 */
	private char include_symbol_right;
	
	public ReportFieldTokenProcessor(char include_symbol_left, char include_symbol_right) {
		this.include_symbol_left = include_symbol_left;
		this.include_symbol_right = include_symbol_right;
	}
	
	public ReportFieldTokenProcessor(String exp, char include_symbol_left, char include_symbol_right) {
		this.expression = new StringBuffer(exp);
		this.include_symbol_left = include_symbol_left;
		this.include_symbol_right = include_symbol_right;
	}
	
	public void setExpression(String exp) {
		this.expression = new StringBuffer(exp);
	}
	
	/**
	 * 搜索表达式中所有的字段，返回包含的字段列表
	 * <p>例如：{field1}+{field2}*10，将会返回{'field1','field2'}两个表达式的列表
	 * 
	 * @return
	 */
	public List<String> searchFieldToken() {
		List<String> result = new ArrayList<String>();
		
		int pos1 = 0, pos2 = 0, length = expression.length();
		for (int i = 0; i < length; i++) {
			if (expression.charAt(i) == include_symbol_left) {
				pos1 = i;
			} else if (expression.charAt(i) == include_symbol_right) {
				pos2 = i;
				
				String field = expression.substring(pos1+1, pos2);
				result.add(field);
				
				pos2++;
			}
		}
		
		return result;
	}
	
	/**
	 * 搜索表达式中所有的可作为变量字段，返回包含的变量列表
	 * <p>这个方法与searchFieldToken不同之处在于本方法为静态方法，并且本方法检查变量字段不是用"{}"包围，而是分析变量字段是不是以字符、"$"或"_"开头，后面跟字符、数字、"$"或"_"
	 * <p>例如：例如：a1+$$*10 b-_c/d_d，将会返回{'a1' '$$' 'b' '_c' 'd_d'}变量字段列表
	 * 
	 * @return
	 */
	public static List<String> searchVariableToken(String searchStr) {
		List<String> result = new ArrayList<String>();
		
		int pos1 = -1, pos2 = 0, length = searchStr.length();
		for (int i = 0; i < length; i++) {
			if (pos1 < 0 && Character.isJavaIdentifierStart(searchStr.charAt(i))) {
				pos1 = i;
			} else if (pos1 >= 0 && !Character.isJavaIdentifierPart(searchStr.charAt(i))) {
				pos2 = i;
				
				String field = searchStr.substring(pos1, pos2);
				result.add(field);
				
				pos1 = -1;
			}
		}
		
		if (pos1 >= 0) {
			pos2 = length;
			
			String field = searchStr.substring(pos1, pos2);
			result.add(field);
		}
		
		return result;
	}
	
	/**
	 * 搜索表达式中所有的可作为变量字段，返回包含的变量列表
	 * <p>这个方法与searchVariableToken不同之处在于本方法认为"aa.bb.cc"也是合法的嵌套变量，提供了对嵌套变量的提取支持
	 * <p>例如：例如：a.b+c.d，将会返回{'a.b' 'c.d'}变量字段列表
	 * 
	 * @return
	 */
	public static List<String> searchNestedVariableToken(String searchStr) {
		List<String> result = new ArrayList<String>();
		
		int pos1 = -1, pos2 = 0, length = searchStr.length();
		for (int i = 0; i < length; i++) {
			char ch = searchStr.charAt(i);
			if (pos1 < 0 && Character.isJavaIdentifierStart(ch)) {
				pos1 = i;
			} else if (pos1 >= 0 && !Character.isJavaIdentifierPart(ch) && ch != '.') {
				pos2 = i;
				
				String field = searchStr.substring(pos1, pos2);
				result.add(field);
				
				pos1 = -1;
			}
		}
		
		if (pos1 >= 0) {
			pos2 = length;
			
			String field = searchStr.substring(pos1, pos2);
			result.add(field);
		}
		
		return result;
	}
	
	public String evaluateFieldToken(String exp, Object...objs) throws EvaluatException {
		if (!StringUtils.contains(exp, '{'))
			return exp;
		
		exp = StringUtils.replace(exp, "{", "${");
		
		VariableResolver variableResolver = new ComplexVariableResolverImpl (objs);
		FunctionMapper functionMapper = new LilyFunctionMapperImpl();
		ELEvaluator mEvaluator = new ELEvaluator(variableResolver);
		
		Object result = mEvaluator.evaluate(exp, null, String.class, functionMapper, null);
		return (String)result;
	}
}
