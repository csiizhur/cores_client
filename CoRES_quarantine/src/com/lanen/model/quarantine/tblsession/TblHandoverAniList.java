package com.lanen.model.quarantine.tblsession;
/**
 * 移交动物列表
 * @author 黄国刚
 *
 */
public class TblHandoverAniList implements java.io.Serializable {

	private static final long serialVersionUID = 2081559904179294545L;

	private String id;			//	数据ID	ID	varchar(20)	20		
	private String sessionId;	//	会话ID	SessionID	varchar(20)	
	private String animalId;	//	动物ID	AnimalID	varchar(20)	
	private int gender;			//	动物性别	Gender	integer		
	private String room;        //房间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAnimalId() {
		return animalId;
	}
	public void setAnimalId(String animalId) {
		this.animalId = animalId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
}
