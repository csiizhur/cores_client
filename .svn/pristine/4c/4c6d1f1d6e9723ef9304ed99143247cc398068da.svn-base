package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblHostBalForTableView {
	private StringProperty id;          //
	private StringProperty balId;		//天平Id
	private StringProperty comPort;     //串口名称
	private StringProperty enable;			//是否启用
	
	
	public TblHostBalForTableView(){}
	public TblHostBalForTableView(String id,String balId,String comPort,int enable){
		setId(id);
		setBalId(balId);
		setComPort(comPort);
		setEnable(enable);
	}
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	public String getBalId() {
		return balId.get();
	}
	public void setBalId(String balId) {
		this.balId = new SimpleStringProperty(balId);
	}
	public String getComPort() {
		return comPort.get();
	}
	public void setComPort(String comPort) {
		this.comPort = new SimpleStringProperty(comPort);
	}
	public String getEnable() {
		return enable.get();
	}
	public void setEnable(int enable) {
		this.enable = new SimpleStringProperty(enable == 1 ? "是":"否");
	}
	
}
