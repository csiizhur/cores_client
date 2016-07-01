package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblPhyExamResultForTableView {
	
	private StringProperty itemName;	//	项目名称	ItemName	varchar(60) 
	private StringProperty remark;		//	操作说明	Remark	varchar(200)	
	private StringProperty orderNo;        //排序
	private StringProperty examResult  ;   //结果
	
	public TblPhyExamResultForTableView(){
		
	}
	public TblPhyExamResultForTableView(
			String itemName,String remark,String examResult,int orderNo){
		setItemName(itemName);
		setRemark(remark);
		setExamResult(examResult);
		setOrderNo(orderNo);
		
	}
	public String getItemName() {
		return itemName.get();
	}
	public void setItemName(String itemName) {
		this.itemName =  new SimpleStringProperty(itemName);
	}
	public String getRemark() {
		return remark.get();
	}
	public void setRemark(String remark) {
		this.remark =  new SimpleStringProperty(remark);
	}
	public int getOrderNo() {
		return Integer.parseInt(orderNo.get());
	}
	public void setOrderNo(int orderNo) {
		this.orderNo =  new SimpleStringProperty(orderNo+"");
	}
	public String getExamResult() {
		return examResult.get();
	}
	public void setExamResult(String examResult) {
		this.examResult = new SimpleStringProperty(examResult);
	}
	
	
}
