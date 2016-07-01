 package com.lanen.util;

public class StringUtil {
	/**
	 * int转String指定位数前补零
	 * @param source
	 * @param length
	 * @return
	 */
	public static String intToString(int source , int length){
		String str="";
		int value=0;
		switch (length) {
		case 2:value= 100+source;
			   str=value+"";
			   str= str.substring(1);
			break;
		case 3:value= 1000+source;
		str=value+"";
		str= str.substring(1);
		break;
		case 4:value= 10000+source;
		str=value+"";
		str= str.substring(1);
		break;
		case 5:value= 100000+source;
		str=value+"";
		str= str.substring(1);
		break;
		default:str="";
			break;
		}
		return str;
	}
	
	/**专题编号，去除"（Fn）"后返回，解决繁殖试验问题
	 * @param studyNo
	 * @return
	 */
	public static String studyNoRemoveFN(String studyNo){
		
		int begin=0,end=0;
		String content="";
		studyNo = studyNo.trim();
		if(studyNo!=null&&!studyNo.equals(""))
		{
			studyNo = studyNo.trim();
			if(studyNo.contains("(")&&studyNo.contains(")")&&studyNo.endsWith(")"))
			{
				begin = studyNo.lastIndexOf("(");
				end = studyNo.lastIndexOf(")");
			
			}else if(studyNo.contains("（")&&studyNo.contains("）")&&studyNo.endsWith("）"))
			{
				begin = studyNo.lastIndexOf("（");
				end = studyNo.lastIndexOf("）");
				
			}else if(studyNo.contains("[")&&studyNo.contains("]")&&studyNo.endsWith("]"))
			{
				begin = studyNo.lastIndexOf("[");
				end = studyNo.lastIndexOf("]");
				
			}else if(studyNo.contains("【")&&studyNo.contains("】")&&studyNo.endsWith("】"))
			{
				begin = studyNo.lastIndexOf("【");
				end = studyNo.lastIndexOf("】");
				
			}
			
			if(begin>=1&&begin<end-2)
			{
				content = studyNo.substring(begin+1, begin+3);
				if(content.trim().equalsIgnoreCase("F0")||content.trim().equalsIgnoreCase("F1")||content.trim().equalsIgnoreCase("F2"))
				{
//					studyNo = studyNo.replace(""+studyNo.charAt(end), "");
//					studyNo = studyNo.replace(content, "");
//					studyNo = studyNo.replace(""+studyNo.charAt(begin), "");
					studyNo = studyNo.substring(0,begin);
				}
			}
		}
		return studyNo;
	}
}
