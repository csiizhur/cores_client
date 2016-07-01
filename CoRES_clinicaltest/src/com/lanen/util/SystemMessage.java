package com.lanen.util;

public class SystemMessage {

	private static String systemName = "临床检验管理系统";
	private static String systemVersion = "1.0";
	public static String getSystemName() {
		return systemName;
	}
	public static String getSystemVersion() {
		return systemVersion;
	}
	public static String getSystemFullName() {
		return SystemMessage.systemName+SystemMessage.systemVersion ;
	}
	
}
