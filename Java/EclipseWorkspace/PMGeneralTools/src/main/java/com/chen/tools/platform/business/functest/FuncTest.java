package com.chen.tools.platform.business.functest;

import org.apache.commons.lang3.StringUtils;

public class FuncTest {
	
	public static void main(String[] args) {
		
		String formula = "pm4h_ad.f_multi(f1(a+b) + c + pm4h_ad.F_MULTI(c + d)) + pm4h_ad.f_multi(f2(a) + f3(d) + e)";
		
		System.out.println("before : " + formula);
		
		formula = replaceNestingFunc(formula, "pm4h_ad.f_multi", "collect_list", "f_multi");
		
		System.out.println("after  : " + formula);
		
		
	}
	
	public static String replaceNestingFunc(String formula, String beReplacedFunc, String... replaceFuncs) 
	{

		formula = formula.toLowerCase();
		
		int index = formula.indexOf(beReplacedFunc);

		if (index != -1) {
			
			String beforeFuncStr = formula.substring(0, index);
			String funcStr = "";
			String afterFuncStr = "";

			char[] chars = formula.substring(index).toCharArray();

			int leftCount = 0;
			int rightCount = 0;
			for (int i = 0; i < chars.length; i++) {
				if (chars[i] == '(') {
					leftCount++;
				} else if (chars[i] == ')') {
					rightCount++;
					if (rightCount == leftCount) {
						funcStr = formula.substring(index, index + i + 1);
						afterFuncStr = formula.substring(index + i + 1);
						break;
					}
				}
			}

			if (!"".equals(funcStr)) {
				String funcBody = funcStr.substring(beReplacedFunc.length());
				while (funcBody.indexOf(beReplacedFunc) > 0)
				{
					funcBody = replaceNestingFunc(funcBody, beReplacedFunc, replaceFuncs);
					
					for (String replaceFunc : replaceFuncs) {						
						funcBody = replaceFunc + ((StringUtils.startsWith(funcBody, "(") && StringUtils.endsWith(funcBody, ")")) ? funcBody : "(" + funcBody + ")");
					}
				}

				for (String replaceFunc : replaceFuncs) {						
					funcBody = replaceFunc + ((StringUtils.startsWith(funcBody, "(") && StringUtils.endsWith(funcBody, ")")) ? funcBody : "(" + funcBody + ")");
				}
				return beforeFuncStr + funcBody + replaceNestingFunc(afterFuncStr, beReplacedFunc, replaceFuncs);
			}

		}

		return formula;
	}

}
