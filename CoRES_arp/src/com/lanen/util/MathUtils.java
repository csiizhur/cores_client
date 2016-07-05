package com.lanen.util;

public class MathUtils {

	/**
	 * 取商,如果有余数则进位
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static Integer DivideBigger(Integer dividend , Integer divisor){
		int result = 0;
		if(divisor != 0){
			result = dividend/divisor;
			if(dividend%divisor >0){
				result = result + 1;
			}
		}
		return result;
	}
}
