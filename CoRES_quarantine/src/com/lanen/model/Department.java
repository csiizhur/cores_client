package com.lanen.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Department implements Serializable  {

	private static final long serialVersionUID = -7770931076602009727L;
	
	
	private String id;
	private String name;
	private String remark;
	private Set<User> users=new HashSet<User>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	

}
