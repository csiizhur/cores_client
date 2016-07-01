package com.lanen.model.quarantine.tblsession;

import java.util.Date;

/**
 * 一般观察
 * @author 黄国刚
 *
 */
public class TblGeneralObservation implements java.io.Serializable {

	private static final long serialVersionUID = 5590939011838299678L;
	
	private String id;				//	数据ID	ID	varchar(20)	20		
	private String sessionId;		//	会话ID	SessionID	varchar(20)
	private String animalId;		//	动物ID	AnimalID	varchar(20)	
	private int gender;				//	性别	Gender	integer			
	private String observation;		//	观察所见	Observation	varchar(200)	
	private String observationCode;	//	观察所见编号	ObservationCode	varchar(20)	
	private Date recordTime;		//	记录时间	RecordTime	datetime	
	
	private TblSession tblSession = new TblSession();   //会话登记表

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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getObservationCode() {
		return observationCode;
	}

	public void setObservationCode(String observationCode) {
		this.observationCode = observationCode;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public TblSession getTblSession() {
		return tblSession;
	}

	public void setTblSession(TblSession tblSession) {
		this.tblSession = tblSession;
	}
	
	
}
