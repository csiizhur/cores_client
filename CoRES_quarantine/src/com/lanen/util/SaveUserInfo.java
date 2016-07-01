package com.lanen.util;

import java.util.ArrayList;
import java.util.List;

import com.lanen.model.User;

public class SaveUserInfo {
	private static User user=null;//用户

	//有 检疫期管理系统登录  权限的用户列表
	private static List<User> quarantineUserList = new ArrayList<User>();
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SaveUserInfo.user = user;
	}

	public static List<User> getQuarantineUserList() {
		return quarantineUserList;
	}

	public static void setQuarantineUserList(List<User> quarantineUserList) {
		SaveUserInfo.quarantineUserList = quarantineUserList;
	}

	
	
	

}
