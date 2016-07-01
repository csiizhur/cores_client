package com.lanen.model.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblAnimalHouseForTableView {

	private StringProperty building;	//	建筑名称	Building	varchar(50)	
	private StringProperty floor;		//	楼层	Floor	varchar(20)	20		
	private StringProperty room;		//	房间号	Room	varchar(20)	20		
	private StringProperty orderNo;       //排序
	private StringProperty animalType;  //动物种类
	
	public TblAnimalHouseForTableView(){}
	public TblAnimalHouseForTableView(String room ,String floor,String building,String animalType,int orderNo){
		setRoom(room);
		setFloor(floor);
		setBuilding(building);
		setAnimalType(animalType);
		setOrderNo(orderNo);
	}
	
	public String getBuilding() {
		return building.get();
	}
	public void setBuilding(String building) {
		this.building = new SimpleStringProperty(building);
	}
	public String getFloor() {
		return floor.get();
	}
	public void setFloor(String floor) {
		this.floor = new SimpleStringProperty(floor);
	}
	public String getRoom() {
		return room.get();
	}
	public void setRoom(String room) {
		this.room = new SimpleStringProperty(room);
	}
	public int getOrderNo() {
		return Integer.parseInt(orderNo.get());
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = new SimpleStringProperty(orderNo+"");
	}
	public String getAnimalType() {
		return animalType.get();
	}
	public void setAnimalType(String animalType) {
		this.animalType = new SimpleStringProperty(animalType);
	}
	
	
	
}
