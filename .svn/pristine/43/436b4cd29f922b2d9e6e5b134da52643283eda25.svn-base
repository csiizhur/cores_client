package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StateForTableView {
	private StringProperty gender;					//	死亡原因
	private StringProperty number;					//	死亡描述
	private StringProperty selectedNumber;			//	排序
	
	public StateForTableView(String gender ,String number,String selectedNumber){
		setGender(gender);
		setNumber(number);
		setSelectedNumber(selectedNumber);
	}
	public String getGender() {
		return gender.get();
	}
	public void setGender(String gender) {
		this.gender = new SimpleStringProperty(gender);
	}
	public String getNumber() {
		return number.get();
	}
	public void setNumber(String number) {
		this.number = new SimpleStringProperty(number);
	}
	public String getSelectedNumber() {
		return selectedNumber.get();
	}
	public void setSelectedNumber(String selectedNumber) {
		this.selectedNumber = new SimpleStringProperty(selectedNumber);
	}
	
}
