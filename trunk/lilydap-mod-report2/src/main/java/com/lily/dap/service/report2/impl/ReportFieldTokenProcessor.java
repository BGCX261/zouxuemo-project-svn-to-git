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
 * ���ð����������ֶεı��ʽ�����磺"{field1}+{field2}*10"�����д���Ĵ�������
 */
public class ReportFieldTokenProcessor {
    protected final Log logger = LogFactory.getLog(getClass());
    
	/**
	 * �������ֶεı��ʽ
	 */
	private StringBuffer expression;
	
	/**
	 * �������
	 */
	private char include_symbol_left;
	/**
	 * �Ұ�����
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
	 * �������ʽ�����е��ֶΣ����ذ������ֶ��б�
	 * <p>���磺{field1}+{field2}*10�����᷵��{'field1','field2'}�������ʽ���б�
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
	 * �������ʽ�����еĿ���Ϊ�����ֶΣ����ذ����ı����б�
	 * <p>���������searchFieldToken��֮ͬ�����ڱ�����Ϊ��̬���������ұ������������ֶβ�����"{}"��Χ�����Ƿ��������ֶ��ǲ������ַ���"$"��"_"��ͷ��������ַ������֡�"$"��"_"
	 * <p>���磺���磺a1+$$*10 b-_c/d_d�����᷵��{'a1' '$$' 'b' '_c' 'd_d'}�����ֶ��б�
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
	 * �������ʽ�����еĿ���Ϊ�����ֶΣ����ذ����ı����б�
	 * <p>���������searchVariableToken��֮ͬ�����ڱ�������Ϊ"aa.bb.cc"Ҳ�ǺϷ���Ƕ�ױ������ṩ�˶�Ƕ�ױ�������ȡ֧��
	 * <p>���磺���磺a.b+c.d�����᷵��{'a.b' 'c.d'}�����ֶ��б�
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
