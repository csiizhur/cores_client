package com.lanen.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录用户角色撤销日志
 * @author Administrator
 *
 */
public class UserRoleLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1227383341477966076L;
	private long id;//id  主键  自动增长
	private String systemId;   //系统ID
	private String userCode;//用户Code
	private String userName;//用户Name
	private String roleId;   //角色ID
	private String type;//授予   \撤销
	private Date createTime;
	private String privilegeList;//权限列表
	
	private User user;//操作者
	
	private String  remark;//原因
	private String MD5;//MD5   moduleId+roleId+revocationTime
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(String privilegeList) {
		this.privilegeList = privilegeList;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
	
	
	
	
}
