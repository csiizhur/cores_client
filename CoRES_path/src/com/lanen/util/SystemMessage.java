package com.lanen.util;

public class SystemMessage {

	private static String systemName = "毒性病理管理系统";
	private static String systemVersion = "v2.0";
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
