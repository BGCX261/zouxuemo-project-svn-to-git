/**
 * 
 */
package com.lily.dap.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.lily.dap.util.convert.ConvertUtils;

/**
 * @author Administrator
 *
 * �ṩ���ַ����ж���ı������д������滻����ִ�б��ʽ���㣩�Ĺ�����
 * 
 * <p>1.�����ַ����������԰����������ı����ֶΣ����ذ����ı����б�
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.searchFieldToken("select {field1}, {field2} from table");	//����{'field1','field2'}�ı�������
 * 
 * <p>2.�����ַ����������԰����������ı����ֶΣ����Ӹ��������Ĳ������滻�ַ��������������滻��������ַ�����Ϣ
 * <br>Map<String, Object> params = new HashMap<String, Object>();
 * <br>params.put("a", 123);
 * <br>params.put("b", 123.456);
 * <br>params.put("c", true);
 * <br>params.put("d", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
 * <br>
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, false);	//����123,123.456,true,2012-12-21 12:24:42,
 * <br>processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, true);		//�׳��쳣
 * 
 * <p>3.�����ַ����������԰����������ı��ʽ�ֶΣ������ݸ��������Ĳ���������ʽ�ֶΣ����ؼ�����ʽ����ַ�����Ϣ
 * <br>Map<String, Object> context = new HashMap<String, Object>();
 * <br>context.put("field1", "abc");
 * <br>context.put("field2", 123);
 * <br>context.put("field3", 111.1);
 * <br>context.put("field4", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
 * <br>
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.evaluateFieldToken("field1+field2+field3+field4, result: {field1+field2+field3+'('+date_to_string(field4,'yyyy-MM-dd HH:mm:ss')+')'}", map);	//����field1+field2+field3+field4, result: abc123111.1(2012-12-21 12:24:42)
 */
public class FieldTokenProcessor {
    protected final Log logger = LogFactory.getLog(getClass());

    private static FieldTokenProcessor instance = new FieldTokenProcessor();
    
	/**
	 * �������
	 */
	private char include_symbol_left;
	/**
	 * �Ұ�����
	 */
	private char include_symbol_right;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public FieldTokenProcessor() {
		this('{', '}');
	}
	
	/**
	 * ���캯����Ҫ���ṩ���Ұ������ַ�
	 * 
	 * @param include_symbol_left ��������ַ�
	 * @param include_symbol_right �Ұ������ַ�
	 */
	public FieldTokenProcessor(char include_symbol_left, char include_symbol_right) {
		this.include_symbol_left = include_symbol_left;
		this.include_symbol_right = include_symbol_right;
	}
	
	public static FieldTokenProcessor defaultProcessor() {
		return instance;
	} 
	
	/**
	 * �����ַ����������԰����������ı����ֶΣ����ذ����ı����б�
	 * 
	 * @param str Ҫ�������ַ���
	 * @return
	 */
	public List<String> searchFieldToken(String str) {
		List<String> result = new ArrayList<String>();
		
		int pos1 = 0, pos2 = 0, length = str.length();
		boolean find = false;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == include_symbol_left) {
				if (find)
					throw new RuntimeException("���ʽ�ظ�Ƕ�ױ�����");
				
				find = true;
				pos1 = i;
			} else if (str.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("���ʽ����δ������ʼǶ�׷���");
					
				pos2 = i;
				find = false;
				
				String field = str.substring(pos1+1, pos2);
				result.add(field);
				
				pos2++;
			}
		}
		
		if (find)
			throw new RuntimeException("���ʽ����δ���ý���Ƕ�׷���");
		
		return result;
	}
	
	/**
	 * �����ַ��������еĿ���Ϊ�����ֶΣ����ذ����ı����б�
	 * <p>���������searchFieldToken��֮ͬ�����ڱ������������ֶβ�����"{}"��Χ�����Ƿ��������ֶ��ǲ������ַ���"$"��"_"��ͷ��������ַ������֡�"$"��"_"
	 * <br>���磺a1+$$*10 b-_c/d_d�����᷵��{'a1' '$$' 'b' '_c' 'd_d'}�����ֶ��б�
	 * 
	 * @return
	 */
	public static List<String> searchAvailableVariableToken(String searchStr) {
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
	 * �����ַ��������еĿ���Ϊ�����ֶΣ����ذ����ı����б�
	 * <p>���������searchAvailableVariableToken��֮ͬ�����ڱ�������Ϊ"aa.bb.cc"Ҳ�ǺϷ���Ƕ�ױ������ṩ�˶�Ƕ�ױ�������ȡ֧��
	 * <br>���磺a.b+c.d�����᷵��{'a.b' 'c.d'}�����ֶ��б�
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
	
	/**
	 * �����ַ����������԰����������ı����ֶΣ����Ӹ��������Ĳ������滻�ַ��������������滻��������ַ�����Ϣ
	 * 
	 * @param str Ҫ�滻���ַ���
	 * @param context ��������ֵ�������ģ�������һ��Map&lt;String, Object&gt;��������һ��Bean
	 * @param throwExceptionParamNotFound ���Ϊtrue��������ֵ����������δ�ṩʱ�׳��쳣��������Ըñ���
	 * @return
	 */
	public String replaceFieldToken(String str, Object context, boolean throwExceptionParamNotFound) {
		return processFieldToken(str, context, true, throwExceptionParamNotFound);
	}
	
	/**
	 * �����ַ����������԰����������ı��ʽ�ֶΣ������ݸ��������Ĳ���������ʽ�ֶΣ����ؼ�����ʽ����ַ�����Ϣ
	 * 
	 * @param str Ҫ������ַ���
	 * @param context ��������ֵ�������ģ�������һ��Map&lt;String, Object&gt;��������һ��Bean
	 * @return
	 */
	public String evaluateFieldToken(String str, Object context) {
		return processFieldToken(str, context, false, false);
	}
	
	/**
	 * �������ʽ�����ط������
	 * ʵ����
	 * 	List<Object> expressionFragment = parseExpression("my name is {name}, i have age {datediff(sysdate(), string_to_date(birthday, 'yyyy-MM-dd'), 'y')}, thank you!")
	 * 	//�����["my name is ", Expression("name"), ", i have age ", Expression("datediff(sysdate(), string_to_date(birthday, 'yyyy-MM-dd'), 'y')"), ", thank you!"]
	 * 
	 * @param expression
	 * @return
	 */
	public List<Object> parseExpression(String expression)  {
		List<Object> expressionFragment = new ArrayList<Object>();
		
		if (expression == null)
			return expressionFragment;
		
		int pos1 = 0, pos2 = 0;
		boolean find = false;
		for (int i = 0, length = expression.length(); i < length; i++) {
			if (expression.charAt(i) == include_symbol_left) {
				if (find)
					throw new RuntimeException("���ʽ�ظ�Ƕ�ױ�����");
				
				pos1 = i;
				find = true;
				
				String str = expression.substring(pos2, pos1);
				if (!"".equals(str))
					expressionFragment.add(str);
			} else if (expression.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("���ʽ����δ������ʼǶ�׷���");
					
				pos2 = i;
				find = false;
				
				String exp = expression.substring(pos1+1, pos2);
				if (!"".equals(exp))
					expressionFragment.add(AviatorEvaluator.compile(exp, true));
				
				pos2++;
			}
		}
		
		if (find)
			throw new RuntimeException("���ʽ����δ���ý���Ƕ�׷���");
		
		String str = expression.substring(pos2);
		if (!"".equals(str))
			expressionFragment.add(str);
		
		return expressionFragment;
	}
	
	public String evaluateExpression(List<Object> expressionFragment,  Map<String, Object> env) {
		if (expressionFragment == null)
			return "";
		
		StringBuffer buf = new StringBuffer();
		for (Object o : expressionFragment) {
			if (o instanceof String) {
				buf.append(o);
			} else {
				Expression exp = (Expression)o;
				Object result = exp.execute(env);
				
				buf.append(result);
			}
		}
		
		return buf.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String processFieldToken(String str, Object context, boolean simpleReplaceParam, boolean throwExceptionIfParamNotFound) {
		StringBuffer buf = new StringBuffer();
		
		Map<String, Object> env;
		if (context instanceof Map)
			env = (Map)context;
		else {
			env = new HashMap<String, Object>();
			
			TransferUtils.copy(context, env, null);
		}
			
		int pos1 = 0, pos2 = 0, length = str.length();
		boolean find = false;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == include_symbol_left) {
				if (find)
					throw new RuntimeException("���ʽ�ظ�Ƕ�ױ�����");
				
				pos1 = i;
				find = true;
				
				buf.append(str.substring(pos2, pos1));
			} else if (str.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("���ʽ����δ������ʼǶ�׷���");
					
				pos2 = i;
				find = false;
				
				String exp = str.substring(pos1+1, pos2);
				
				Object result;
				if (simpleReplaceParam) {
					if (!env.containsKey(exp)) {
						if (throwExceptionIfParamNotFound)
							throw new RuntimeException("���ʽ����[" + exp + "]δ�ṩ����ֵ��");
						else
							result = "";
					} else {
						result = env.get(exp);
					}
				} else {
					Expression expression = AviatorEvaluator.compile(exp, true);
					result = expression.execute(env);
				}
				
				String val = ConvertUtils.convert(result);
				buf.append(val);
				
				pos2++;
			}
		}
		
		if (find)
			throw new RuntimeException("���ʽ����δ���ý���Ƕ�׷���");
		
		buf.append(str.substring(pos2));
		
		return buf.toString();
	}
	
	public static void main(String[] args) {
		FieldTokenProcessor processor = new FieldTokenProcessor();
		
		System.out.println(processor.parseExpression("��"));
		System.out.println(processor.parseExpression(""));
		System.out.println(processor.parseExpression("��{}"));
		System.out.println(processor.parseExpression("{ddd}"));
		System.out.println(processor.parseExpression("{ddd}��"));
	}
}
