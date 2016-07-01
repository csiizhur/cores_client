package com.lanen.model.tableview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblSessionAnimalForTableView {
	private StringProperty animalId;	//	动物ID	AnimalID	varchar(20)	
	private StringProperty gender;			//	性别	Gender	integer			
	private StringProperty room;	//	房间	
	private BooleanProperty select;
	
	public TblSessionAnimalForTableView(){
	}
	public TblSessionAnimalForTableView(
			String animalId,int gender,String room
			){
		setAnimalId(animalId);
		setGender(gender);
		setRoom(room);
		setSelect(false);
	}
	public BooleanProperty selectProperty(){return select;};
	public boolean getSelect() {
		return select.get();
	}
	public void setSelect(boolean select) {
		this.select =  new SimpleBooleanProperty(select);
	}
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId = new SimpleStringProperty(animalId);
	}
	public String getGender() {
		return gender.get();
	}
	public void setGender(int gender) {
		this.gender = new SimpleStringProperty(gender == 1? "♂":"♀");
	}
	public String getRoom() {
		return room.get();
	}
	public void setRoom(String room) {
		this.room = new SimpleStringProperty(room);
	}
	
	
	
}
