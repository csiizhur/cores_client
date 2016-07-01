package com.lanen.model.quarantine;
/**
 * 动物房房间与饲养动物对照表
 * @author 黄国刚
 *
 */
public class TblRoomAndAniType implements java.io.Serializable{

	private static final long serialVersionUID = 2420131100029629737L;
	
	private String id;				//	数据ID	ID	varchar(20)	
	private String roomId;			//	房间ID	RoomID	varchar(20)	
	private String animalType;		//	饲养动物种类	AnimalType	varchar(20)	
	private String animalStrain;	//	动物品系	AnimalStrain	varchar(20)	
	private String animalLevel;		//	动物级别	AnimalLevel	varchar(20)	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getAnimalType() {
		return animalType;
	}
	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}
	public String getAnimalStrain() {
		return animalStrain;
	}
	public void setAnimalStrain(String animalStrain) {
		this.animalStrain = animalStrain;
	}
	public String getAnimalLevel() {
		return animalLevel;
	}
	public void setAnimalLevel(String animalLevel) {
		this.animalLevel = animalLevel;
	}
	
	

}
