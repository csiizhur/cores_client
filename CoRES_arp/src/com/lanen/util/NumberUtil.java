package com.lanen.util;

public class NumberUtil {
	/**
	 * 返回四位  字符串
	 * @param i
	 * @return
	 */
	public static String integerToString(int i){
		i=10000+i;
		String str=i+"";
		
		return str.substring(1);
	}
}
