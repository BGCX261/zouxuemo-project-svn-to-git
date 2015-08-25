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
 * 提供对字符串中定义的变量进行处理（简单替换或者执行表达式计算）的工具类
 * 
 * <p>1.搜索字符串中所有以包含符包含的变量字段，返回包含的变量列表
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.searchFieldToken("select {field1}, {field2} from table");	//返回{'field1','field2'}的变量集合
 * 
 * <p>2.搜索字符串中所有以包含符包含的变量字段，并从给定上下文参数中替换字符串变量，返回替换变量后的字符串信息
 * <br>Map<String, Object> params = new HashMap<String, Object>();
 * <br>params.put("a", 123);
 * <br>params.put("b", 123.456);
 * <br>params.put("c", true);
 * <br>params.put("d", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
 * <br>
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, false);	//返回123,123.456,true,2012-12-21 12:24:42,
 * <br>processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, true);		//抛出异常
 * 
 * <p>3.搜索字符串中所有以包含符包含的表达式字段，并根据给定上下文参数计算表达式字段，返回计算表达式后的字符串信息
 * <br>Map<String, Object> context = new HashMap<String, Object>();
 * <br>context.put("field1", "abc");
 * <br>context.put("field2", 123);
 * <br>context.put("field3", 111.1);
 * <br>context.put("field4", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
 * <br>
 * <br>FieldTokenProcessor processor = new FieldTokenProcessor();
 * <br>processor.evaluateFieldToken("field1+field2+field3+field4, result: {field1+field2+field3+'('+date_to_string(field4,'yyyy-MM-dd HH:mm:ss')+')'}", map);	//返回field1+field2+field3+field4, result: abc123111.1(2012-12-21 12:24:42)
 */
public class FieldTokenProcessor {
    protected final Log logger = LogFactory.getLog(getClass());

    private static FieldTokenProcessor instance = new FieldTokenProcessor();
    
	/**
	 * 左包含符
	 */
	private char include_symbol_left;
	/**
	 * 右包含符
	 */
	private char include_symbol_right;
	
	/**
	 * 默认构造函数
	 */
	public FieldTokenProcessor() {
		this('{', '}');
	}
	
	/**
	 * 构造函数，要求提供左右包含符字符
	 * 
	 * @param include_symbol_left 左包含符字符
	 * @param include_symbol_right 右包含符字符
	 */
	public FieldTokenProcessor(char include_symbol_left, char include_symbol_right) {
		this.include_symbol_left = include_symbol_left;
		this.include_symbol_right = include_symbol_right;
	}
	
	public static FieldTokenProcessor defaultProcessor() {
		return instance;
	} 
	
	/**
	 * 搜索字符串中所有以包含符包含的变量字段，返回包含的变量列表
	 * 
	 * @param str 要搜索的字符串
	 * @return
	 */
	public List<String> searchFieldToken(String str) {
		List<String> result = new ArrayList<String>();
		
		int pos1 = 0, pos2 = 0, length = str.length();
		boolean find = false;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) == include_symbol_left) {
				if (find)
					throw new RuntimeException("表达式重复嵌套变量！");
				
				find = true;
				pos1 = i;
			} else if (str.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("表达式变量未设置起始嵌套符！");
					
				pos2 = i;
				find = false;
				
				String field = str.substring(pos1+1, pos2);
				result.add(field);
				
				pos2++;
			}
		}
		
		if (find)
			throw new RuntimeException("表达式变量未设置结束嵌套符！");
		
		return result;
	}
	
	/**
	 * 搜索字符串中所有的可作为变量字段，返回包含的变量列表
	 * <p>这个方法与searchFieldToken不同之处在于本方法检查变量字段不是用"{}"包围，而是分析变量字段是不是以字符、"$"或"_"开头，后面跟字符、数字、"$"或"_"
	 * <br>例如：a1+$$*10 b-_c/d_d，将会返回{'a1' '$$' 'b' '_c' 'd_d'}变量字段列表
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
	 * 搜索字符串中所有的可作为变量字段，返回包含的变量列表
	 * <p>这个方法与searchAvailableVariableToken不同之处在于本方法认为"aa.bb.cc"也是合法的嵌套变量，提供了对嵌套变量的提取支持
	 * <br>例如：a.b+c.d，将会返回{'a.b' 'c.d'}变量字段列表
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
	 * 搜索字符串中所有以包含符包含的变量字段，并从给定上下文参数中替换字符串变量，返回替换变量后的字符串信息
	 * 
	 * @param str 要替换的字符串
	 * @param context 给定变量值的上下文，可以是一个Map&lt;String, Object&gt;，或者是一个Bean
	 * @param throwExceptionParamNotFound 如果为true，当变量值在上下文中未提供时抛出异常，否则忽略该变量
	 * @return
	 */
	public String replaceFieldToken(String str, Object context, boolean throwExceptionParamNotFound) {
		return processFieldToken(str, context, true, throwExceptionParamNotFound);
	}
	
	/**
	 * 搜索字符串中所有以包含符包含的表达式字段，并根据给定上下文参数计算表达式字段，返回计算表达式后的字符串信息
	 * 
	 * @param str 要计算的字符串
	 * @param context 给定变量值的上下文，可以是一个Map&lt;String, Object&gt;，或者是一个Bean
	 * @return
	 */
	public String evaluateFieldToken(String str, Object context) {
		return processFieldToken(str, context, false, false);
	}
	
	/**
	 * 分析表达式，返回分析结果
	 * 实例：
	 * 	List<Object> expressionFragment = parseExpression("my name is {name}, i have age {datediff(sysdate(), string_to_date(birthday, 'yyyy-MM-dd'), 'y')}, thank you!")
	 * 	//结果：["my name is ", Expression("name"), ", i have age ", Expression("datediff(sysdate(), string_to_date(birthday, 'yyyy-MM-dd'), 'y')"), ", thank you!"]
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
					throw new RuntimeException("表达式重复嵌套变量！");
				
				pos1 = i;
				find = true;
				
				String str = expression.substring(pos2, pos1);
				if (!"".equals(str))
					expressionFragment.add(str);
			} else if (expression.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("表达式变量未设置起始嵌套符！");
					
				pos2 = i;
				find = false;
				
				String exp = expression.substring(pos1+1, pos2);
				if (!"".equals(exp))
					expressionFragment.add(AviatorEvaluator.compile(exp, true));
				
				pos2++;
			}
		}
		
		if (find)
			throw new RuntimeException("表达式变量未设置结束嵌套符！");
		
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
					throw new RuntimeException("表达式重复嵌套变量！");
				
				pos1 = i;
				find = true;
				
				buf.append(str.substring(pos2, pos1));
			} else if (str.charAt(i) == include_symbol_right) {
				if (!find)
					throw new RuntimeException("表达式变量未设置起始嵌套符！");
					
				pos2 = i;
				find = false;
				
				String exp = str.substring(pos1+1, pos2);
				
				Object result;
				if (simpleReplaceParam) {
					if (!env.containsKey(exp)) {
						if (throwExceptionIfParamNotFound)
							throw new RuntimeException("表达式变量[" + exp + "]未提供变量值！");
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
			throw new RuntimeException("表达式变量未设置结束嵌套符！");
		
		buf.append(str.substring(pos2));
		
		return buf.toString();
	}
	
	public static void main(String[] args) {
		FieldTokenProcessor processor = new FieldTokenProcessor();
		
		System.out.println(processor.parseExpression("否"));
		System.out.println(processor.parseExpression(""));
		System.out.println(processor.parseExpression("否{}"));
		System.out.println(processor.parseExpression("{ddd}"));
		System.out.println(processor.parseExpression("{ddd}否"));
	}
}
