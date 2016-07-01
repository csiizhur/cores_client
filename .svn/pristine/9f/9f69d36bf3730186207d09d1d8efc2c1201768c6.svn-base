package com.lanen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.lanen.model.Privilege;
import com.lanen.model.Role;
import com.lanen.model.User;

public class CheckPrivilege {
	
	public static boolean checkPrivilege(User user,String privielgeName){
		//判断用户是否为空
		if(null==user){//为空直接   返回 false
			return false;
		}else{
			//超级管理员  ，直接返回 true
			if("admin".equals(user.getUserName())||"administrator".equals(user.getUserName())){
				return true;
			}
		}
		//权限名称  为空   或者  为“”
		if(null==privielgeName || privielgeName.equals("")){
			return false;
		}
		Set<Role> roles=user.getRoles();
		if( null!=roles && roles.size()>0 ){//角色不为空
			// 用户的角色列表
			List<Role> roleList=new ArrayList<Role>(roles);  
			for(Role role : roleList){
				Set<Privilege> privileges=role.getPrivileges();
				if(null!=privileges && privileges.size()>0){
					//角色对应的权限列表
					List<Privilege> privilegeList=new ArrayList<Privilege>(privileges);
					for(Privilege privielge :privilegeList){
						//查询到该权限  ,返回   true
						if(privielgeName.equals(privielge.getPrivilegeName())){
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

}
