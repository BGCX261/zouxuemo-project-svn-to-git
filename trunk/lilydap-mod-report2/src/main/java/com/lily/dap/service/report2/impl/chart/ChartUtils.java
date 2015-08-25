/**
 * 
 */
package com.lily.dap.service.report2.impl.chart;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.lily.dap.service.core.Evaluator.EvaluatException;
import com.lily.dap.service.report2.impl.ReportFieldTokenProcessor;

/**
 * @author xuemozou
 *
 */
public class ChartUtils {
	private static ReportFieldTokenProcessor fieldTokenProcessor = new ReportFieldTokenProcessor('{', '}');
	
	public static Color createColor(String rgb) {
		int r = Integer.parseInt(rgb.substring(0, 2), 16);
		int g = Integer.parseInt(rgb.substring(2, 4), 16);
		int b = Integer.parseInt(rgb.substring(4), 16);
		
		return new Color(r, g, b);
	}
	
	public static String evaluateParam(String param, Map<String, Object> variableMap) {
		try {
			param = fieldTokenProcessor.evaluateFieldToken(param, variableMap);
		} catch (EvaluatException e1) {}
		
		return param;
	}
	
	public static void main(String[] args) {
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("a", "这是变量{a}的值");
		variableMap.put("0", "这是变量{0}的值");
		
		System.out.println(evaluateParam("{a},{b},{0}, {1}", variableMap));
	}
}