package com.lanen.model.studyplan;

public class DictReportNumber implements java.io.Serializable {
	private static final long serialVersionUID = 4292306850028102835L;
	private String id;
	private String reportName;   //报表名称100
	private String number ;		 //编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
}
