package com.lanen.model;

import java.util.Date;

/**
 * 规则表
 * @author Administrator
 *
 */
public class Regulation implements java.io.Serializable{
	
	private static final long serialVersionUID = 5297696781607773204L;
	
	private Long id;   				//主键   ，自动增长
	private String regulationName;	//规则名称
	private String type;		//类型
	private String  defaultValue;//缺省值
	private String 	setValue;	//设定值
	private Date setTime;      //设定时间
	private String setter;     //设定人
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegulationName() {
		return regulationName;
	}
	public void setRegulationName(String regulationName) {
		this.regulationName = regulationName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSetValue() {
		return setValue;
	}
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
	public Date getSetTime() {
		return setTime;
	}
	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}
	public String getSetter() {
		return setter;
	}
	public void setSetter(String setter) {
		this.setter = setter;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	

}
