package com.lanen.model.quarantine.tblsession;

import java.util.Date;


/**
 * 动物体检表
 * @author 黄国刚
 *
 */
public class TblPhyExam implements java.io.Serializable {

	private static final long serialVersionUID = 1386913285871489481L;
	
	private String id;			//	数据ID	ID	varchar(20)	20		
	private String sessionId;	//	会话ID	SessionID	varchar(20)	
	private String animalId;	//	动物ID	AnimalID	varchar(20)	
	private int gender;			//	动物性别	Gender	integer			
	private String bodyWeight;	//	体重	BodyWeight	varchar(20)	20	
	private String weightUnit;	//	体重单位	WeightUnit	varchar(20)	
	private String temperature;	//	体温	Temperature	varchar(20)	20	
	private String examResult;	//	体检结果	ExamResult	varchar(200)
	private Date recordTime;    //记录时间
	
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

	public String getBodyWeight() {
		return bodyWeight;
	}

	public void setBodyWeight(String bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}

	public TblSession getTblSession() {
		return tblSession;
	}

	public void setTblSession(TblSession tblSession) {
		this.tblSession = tblSession;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	
	
}
