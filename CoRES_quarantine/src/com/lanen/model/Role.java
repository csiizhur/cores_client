package com.lanen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 * @author Administrator
 *
 */
public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3081136035731775158L;
	private String  id;
	private String roleName;  //角色名
	
	private User user;  //创建人
	
	private Date createTime; //创建时间
	private String remark;//备注
	private Set<Privilege> privileges =new HashSet<Privilege>();  //权限列表
	private Set<User> users=new HashSet<User>();//用户列表

	private Systems system;//所属系统
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Systems getSystem() {
		return system;
	}
	public void setSystem(Systems system) {
		this.system = system;
	}
	
	
	
	

}
