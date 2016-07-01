package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DictPhysicalExamItemForTableView {
	
	private StringProperty id;			//	数据ID	ID	varchar(20)	
	private StringProperty itemType;	//	项目类别	ItemType	varchar(60)	 
	private StringProperty itemName;	//	项目名称	ItemName	varchar(60) 
	private StringProperty remark;		//	操作说明	Remark	varchar(200)	
	private StringProperty orderNo;        //排序
	
	public DictPhysicalExamItemForTableView(){
		
	}
	public DictPhysicalExamItemForTableView(String id,String itemType,
			String itemName,String remark,int orderNo){
		this.id = new SimpleStringProperty(id);
		this.itemType = new SimpleStringProperty(itemType);
		this.itemName = new SimpleStringProperty(itemName);
		this.remark = new SimpleStringProperty(remark);
		this.orderNo = new SimpleStringProperty(orderNo+"");
	}
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id =  new SimpleStringProperty(id);
	}
	public String getItemType() {
		return itemType.get();
	}
	public void setItemType(String itemType) {
		this.itemType =  new SimpleStringProperty(itemType);
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
	
	
}
