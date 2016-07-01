package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DictAnimalDeathForTableView {
	private StringProperty deadType;		//死亡原因
	private StringProperty deadRsn;			//死亡描述
	private StringProperty orderNo;			//排序
	
	public DictAnimalDeathForTableView(){
		
	}
	public DictAnimalDeathForTableView(String deadType,String deadRsn,int orderNo){
		setDeadType(deadType);
		setDeadRsn(deadRsn);
		setOrderNo(orderNo);
	}
	
	public String getDeadType() {
		return deadType.get();
	}
	public void setDeadType(String deadType) {
		this.deadType = new SimpleStringProperty(deadType);
	}
	public String getDeadRsn() {
		return deadRsn.get();
	}
	public void setDeadRsn(String deadRsn) {
		this.deadRsn =  new SimpleStringProperty(deadRsn);
	}
	public int getOrderNo() {
		return Integer.parseInt(orderNo.get());
	}
	public void setOrderNo(int orderNo) {
		this.orderNo =  new SimpleStringProperty(orderNo+"");
	}
	
	
}
