package com.lanen.model.clinicaltest;

import java.io.Serializable;

/**
 * 顺序号（例如：130822001）
 * @author Administrator
 *
 */
public class PoolSpecimenId implements Serializable{

	private static final long serialVersionUID = 1901201361749715671L;
	private Long  testItem;         //主键  ，区分给谁编号（血常规  2、血凝  3、血生化  1  ,）
	private String currentDate;  //当前 日期，（2013-08-22）
	private String prefix;       //前缀（130822_ _ _）
	private int serialNumLen;             //几位流水号
	private String currentSerialNum;      //当前流水号
	
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Long getTestItem() {
		return testItem;
	}
	public void setTestItem(Long testItem) {
		this.testItem = testItem;
	}
	public int getSerialNumLen() {
		return serialNumLen;
	}
	public void setSerialNumLen(int serialNumLen) {
		this.serialNumLen = serialNumLen;
	}
	public String getCurrentSerialNum() {
		return currentSerialNum;
	}
	public void setCurrentSerialNum(String currentSerialNum) {
		this.currentSerialNum = currentSerialNum;
	}
	
	
	
	
}
