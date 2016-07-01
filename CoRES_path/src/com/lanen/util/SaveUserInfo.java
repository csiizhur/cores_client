package com.lanen.util;

import java.util.ArrayList;
import java.util.List;

import com.lanen.model.User;

public class SaveUserInfo {
	private static User user=null;//用户

	//有 检疫期管理系统登录  权限的用户列表
	private static List<User> pathUserList = new ArrayList<User>();
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SaveUserInfo.user = user;
	}
	public  static String getRealName(){
		if(null != user){
			return user.getRealName();
		}
		return null;
	}
	public  static String getUserName(){
		if(null != user){
			return user.getUserName();
		}
		return null;
	}

	public static List<User> getPathUserList() {
		return pathUserList;
	}

	public static void setPathUserList(List<User> pathUserList) {
		SaveUserInfo.pathUserList = pathUserList;
	}

	
	
	

}
