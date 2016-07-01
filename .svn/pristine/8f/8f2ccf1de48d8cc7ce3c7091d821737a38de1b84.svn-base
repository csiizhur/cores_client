package com.lanen.model.tableview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DictOutputSettingsForTableView {
	private StringProperty id;
	private StringProperty label;
	private StringProperty show;
	private IntegerProperty orderNo;
	
	public DictOutputSettingsForTableView(
			String id,String label,String show,int orderNo){
		setId(id);
		setLabel(label);
		setShow(show);
		setOrderNo(orderNo);
	}
	
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	public String getLabel() {
		return label.get();
	}
	public void setLabel(String label) {
		this.label =  new SimpleStringProperty(label);
	}
	public String getShow() {
		return show.get();
	}
	public void setShow(String show) {
		this.show =  new SimpleStringProperty(show);
	}

	public int getOrderNo() {
		return orderNo.get();
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = new SimpleIntegerProperty(orderNo);
	}
	
}
