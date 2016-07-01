package com.lanen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限表
 * @author Administrator
 *
 */
public class Privilege implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4803157427519819753L;
	private String id;
	private String privilegeName;//权限名称
	private String privilegePath;//控制路径（web）
	private Date createTime;//创建时间
	
	
	private Set<Role> roles=new HashSet<Role>();
	
	private User user;//创建人
	private Module module;//权限所属子模块
	
	private String remark;//备注

	public Privilege(){}
	public Privilege(String id,String name,String path,Date createTime,User user ,Module module ){
		this.id=id;
		this.privilegeName=name;
		this.privilegePath=path;
		this.createTime=createTime;
		this.user=user;
		this.module=module;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegePath() {
		return privilegePath;
	}

	public void setPrivilegePath(String privilegePath) {
		this.privilegePath = privilegePath;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}

	

}
