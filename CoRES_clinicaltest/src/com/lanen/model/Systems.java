package com.lanen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统表
 * @author Administrator
 *
 */
public class Systems implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1350815331143741327L;
	private String id;//id   主键(系统编号)
	private String systemName;//系统名称
	private Date createTime;//创建时间
	private User user;//创建人
	private String remark;//说明
	
	private Set<Module> modules=new HashSet<Module>(); //子系统集合
	
	private Set<Role> roles = new HashSet<Role>();                //角色集合

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
