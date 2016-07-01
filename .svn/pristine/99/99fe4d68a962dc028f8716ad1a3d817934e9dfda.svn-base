package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblBalForTableView {

	private StringProperty balId;				//天平Id
	private StringProperty balDesc;				// 设备描述
	private StringProperty commEnable;              //是否自动采集
	private StringProperty baudRate;             //波特率   
	private StringProperty dataBit;              //数据位   5 6 7 8
	private StringProperty stopBit;              //停止位    1,1.5,2
	private StringProperty checkMode;            //校验位    None，Even，Odd，Space，Mark 
	private StringProperty orderNo;
	
	public TblBalForTableView(){
		
	}
	public TblBalForTableView(
			String balId,	
			String balDesc,	
			int commEnable,  
			String baudRate,   
			String dataBit,     
			String stopBit,     
			String checkMode, 
			int orderNo
			){
		setBalId(balId);
		setBalDesc(balDesc);
		setCommEnable(commEnable);
		setBaudRate(baudRate);
		setDataBit(dataBit);
		setStopBit(stopBit);
		setCheckMode(checkMode);
		setOrderNo(orderNo);
		
	}
	
	
	public String getBalId() {
		return balId.get();
	}
	public void setBalId(String balId) {
		this.balId = new SimpleStringProperty(balId);
	}
	public String getBalDesc() {
		return balDesc.get();
	}
	public void setBalDesc(String balDesc) {
		this.balDesc = new SimpleStringProperty( balDesc);
	}
	public String getCommEnable() {
		return commEnable.get();
	}
	public void setCommEnable(int commEnable) {
		this.commEnable = new SimpleStringProperty( (commEnable == 1 ? "是" : "否"));
	}
	public String getBaudRate() {
		return baudRate.get();
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = new SimpleStringProperty( (baudRate == null ? "" : baudRate));
	}
	public String getDataBit() {
		return dataBit.get();
	}
	public void setDataBit(String dataBit) {
		this.dataBit = new SimpleStringProperty( (dataBit == null ? "" : dataBit));
	}
	public String getStopBit() {
		return stopBit.get();
	}
	public void setStopBit(String stopBit) {
		this.stopBit = new SimpleStringProperty( (stopBit == null ? "" : stopBit));
	}
	public String getCheckMode() {
		return checkMode.get();
	}
	public void setCheckMode(String checkMode) {
		this.checkMode = new SimpleStringProperty( (checkMode == null ? "" : checkMode));
	}
	public int getOrderNo() {
		return Integer.parseInt(orderNo.get());
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = new SimpleStringProperty( orderNo+"");
	}
	
	
	
}
