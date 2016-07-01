package com.lanen.model.quarantine;

import java.io.Serializable;

/**
 * 动物房房间设置表
 * @author 黄国刚
 *
 */
public class TblAnimalHouse implements Serializable{
	
	private static final long serialVersionUID = 5727865230749286718L;
	
	private String roomId;		//	房间ID	RoomID	varchar(20)	
	private String building;	//	建筑名称	Building	varchar(50)	
	private String floor;		//	楼层	Floor	varchar(20)	20		
	private String room;		//	房间号	Room	varchar(20)	20		
	private int studyPhase;		//	用于试验阶段	StudyPhase	integer		
	private int orderNo;        //  排序
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getStudyPhase() {
		return studyPhase;
	}
	public void setStudyPhase(int studyPhase) {
		this.studyPhase = studyPhase;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
