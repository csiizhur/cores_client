package com.lanen.zero.main;

import java.text.DecimalFormat;

public class ttttt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String   a= "0.0290";
//		System.out.println(a);
//		float b = Float.valueOf(a);
//		System.out.println(b);
//		float c = b*1000;
//		System.out.println(c);
//		String d = ""+c;
//		System.out.println(d);
//		
//		String  aa = "11.6";
//		System.out.println(aa);
//		float bb = Float.valueOf(aa);
//		System.out.println(bb);
//		float cc = bb*10;
//		System.out.println(cc);
//		String dd = ""+cc;
//		System.out.println(dd);
//		System.out.println("/");
//		System.out.println("\\");
//		
//		
//		
//		String   a= "+11110.590";
//		System.out.println(a);
//		float b = Float.valueOf(a);
////		System.out.println();
//		// 得到本地的缺省格式
//		DecimalFormat df1 = new DecimalFormat("0");
//		System.out.println(df1.format(b));
//		String remark ="1,1@TP:56.7、ALB:30.1";
//		String remark2 ="";
//		if(null != remark && remark.contains("@")){
//			String[] strs = remark.split("@");
//			remark = strs[1];
//			remark2 = strs[0];
//			System.out.println(remark);
//			System.out.println(remark2);
//		}
//		String maxSpecimenCode ="0001";
//		String numberStr = maxSpecimenCode;
//		int number = Integer.valueOf(1+numberStr);
//		number = number /10;
//		number=number+1;
//		number = number*10;
//		number =number+1;
//		
//		numberStr =number+"";
//		numberStr = numberStr.substring(1);
//		System.out.println(numberStr);
		
		System.out.println(plusSpecimenCode(3,"140730001",20));
	}
	/**检验编号  +  i
	 * @param testItem
	 * @param specimenCode
	 * @param i
	 * @return
	 */
	public static String plusSpecimenCode(int testItem,String specimenCode,int i){
		String goalSpecimenCode ="";
		if(testItem ==1 || testItem ==2 || testItem ==3){
			//"140730"
			goalSpecimenCode = specimenCode.substring(0, 6);
			//"101"
			String numberStr = specimenCode.substring(6);
			//1101
			int number = Integer.valueOf(1+numberStr);
			number=number+i;
			if(number>1999){
				//超界
				return null;
			}
			
			numberStr =number+"";
			numberStr = numberStr.substring(1);
			goalSpecimenCode =goalSpecimenCode+numberStr;
		}else{
			String numberStr = 1+specimenCode;
			int number = Integer.valueOf(numberStr);
			number=number+i;
			if(number>19999){
				//超界
				return null;
			}
			
			numberStr =number+"";
			numberStr = numberStr.substring(1);
			goalSpecimenCode = numberStr;
		}
		
		return goalSpecimenCode;
	};

}
