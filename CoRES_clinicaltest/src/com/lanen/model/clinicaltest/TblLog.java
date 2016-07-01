package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;

public class TblLog implements Serializable{
	private static final long serialVersionUID = -1438881632929033272L;
	
	private int id;
	private String systemName;  //系统名称
	private String systemVersion;//系统版本
	private String operatType;     //操作类型
	private String operatOject;    //操作对象
	private String operator;       //操作者
	private Date operatTime;       //操作时间
	private String operatContent;  //操作内容
	private String operatHost;     //操作位置
	private String remark;         //备注
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperatType() {
		return operatType;
	}
	public void setOperatType(String operatType) {
		this.operatType = operatType;
	}
	public String getOperatOject() {
		return operatOject;
	}
	public void setOperatOject(String operatOject) {
		this.operatOject = operatOject;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	public String getOperatContent() {
		return operatContent;
	}
	public void setOperatContent(String operatContent) {
		this.operatContent = operatContent;
	}
	public String getOperatHost() {
		return operatHost;
	}
	public void setOperatHost(String operatHost) {
		this.operatHost = operatHost;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	
	
}
