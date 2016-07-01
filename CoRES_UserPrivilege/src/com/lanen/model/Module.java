package com.lanen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 模块
 * @author Administrator
 *
 */
public class Module implements Serializable {

	private static final long serialVersionUID = -2188886609520788302L;
	private String id;//id   模块id
	private String moduleName;//模块名称（包括子系统）
	private Date createTime;//创建时间
	private User user;//创建人
	private String remark;//说明
	
	private Systems system;
	private Set<Privilege> privileges =new HashSet<Privilege>();  //权限列表
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
	public Systems getSystem() {
		return system;
	}
	public void setSystem(Systems system) {
		this.system = system;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	

}
