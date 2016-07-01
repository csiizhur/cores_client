package com.lanen.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 * @author Administrator
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5945451744790952501L;
	private String id;//id  主键
	private String userName;   //用户名
	private String userCode;   //用户编号
	private String realName;   //真实姓名
	private String password;  //密码
	private String userNumber;//用户卡号
	private String flag  ;//有效标志       （  可用   停用）
	private String flagRemark;//无效说明
	
	private String phone;//电话
	private String email;//邮箱
	
	private User user;  //创建人
	
	private Department department;
	private Set<Role> roles=new HashSet<Role>();  //角色
	
	private Date createTime;//创建时间
	private Date updatePasswordTime;
	private String remark;//备注

	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlagRemark() {
		return flagRemark;
	}
	public void setFlagRemark(String flagRemark) {
		this.flagRemark = flagRemark;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Date getUpdatePasswordTime() {
		return updatePasswordTime;
	}
	public void setUpdatePasswordTime(Date updatePasswordTime) {
		this.updatePasswordTime = updatePasswordTime;
	}
	
	

}
