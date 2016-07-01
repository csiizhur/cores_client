package com.lanen.model.quarantine.tblsession;

import java.util.Date;

/**
 * 动物移交记录
 * @author 黄国刚
 *
 */
public class TblAniHandover implements java.io.Serializable {

	private static final long serialVersionUID = -8245157523867749685L;

	private String sessionId;		//	会话ID	SessionID	varchar(20)	20		
	private Date handoverDate;		//	移交日期	HandoverDate	datetime		
	private String studyNo;			//	移交课题编号	StudyNo	varchar(20)	20		
	private String qrRId;			//	移交申请单号	QRRID	varchar(20)	20		
	private int handoverFlag;		//	移交类型	HandoverFlag	integer			
	private String animalType;		//	动物种类	AnimalType	varchar(20)	20		
	private String animalStrain;	//	动物品系	AnimalStrain	varchar(20)	20	
	private String newRoom;			//	移交后房间	NewRoom	varchar(20)	20		
	private int numMale;			//	移交数量雄	NumMale	integer				
	private int numFemale;			//	移交数量雌	NumFemale	integer			
	private float weightMaleU;		//	体重范围雄上	WeightMaleU	decimal(8,3)	8	
	private float weightMaleL;		//	体重范围雄下	WeightMaleL	decimal(8,3)	8	
	private float weightFemaleU;	//	体重范围雌上	WeightFemaleU	decimal(8,3)	
	private float weightFemaleL;	//	体重范围雌下	WeightFemaleL	decimal(8,3)	
	private String weightUnit;		//	体重单位	WeightUnit	varchar(20)	20		
	private float ageMaleU;			//	年龄范围雄上	AgeMaleU	decimal(8,3)	8	
	private float ageMaleL;			//	年龄范围雄下	AgeMaleL	decimal(8,3)	8	
	private float ageFemaleU;		//	年龄范围雌上	AgeFemaleU	decimal(8,3)	8	
	private float ageFemaleL;		//	年龄范围雌下	AgeFemaleL	decimal(8,3)	8	
	private String ageUnit;			//	年龄单位	AgeUnit	varchar(20)	20		
	private String aniState;		//	动物状况描述	AniState	varchar(100)	100	
	private String aniStateSignId;	//	兽医签字ID	AniStateSignID	varchar(20)	20	
	private String remark;			//	备注	Remark	varchar(100)	100		
	private String operator;		//	移交者	Operator	varchar(20)	20		
	private String receiver;		//	接受者	Receiver	varchar(20)	20		
	private String signId;			//	签字ID	SignID	varchar(20)	20		
	private String checkId;			//	复核ID	CheckID	varchar(20)	20		
	

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getHandoverDate() {
		return handoverDate;
	}

	public void setHandoverDate(Date handoverDate) {
		this.handoverDate = handoverDate;
	}

	public String getStudyNo() {
		return studyNo;
	}

	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}

	public String getQrRId() {
		return qrRId;
	}

	public void setQrRId(String qrRId) {
		this.qrRId = qrRId;
	}

	public int getHandoverFlag() {
		return handoverFlag;
	}

	public void setHandoverFlag(int handoverFlag) {
		this.handoverFlag = handoverFlag;
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

	public String getNewRoom() {
		return newRoom;
	}

	public void setNewRoom(String newRoom) {
		this.newRoom = newRoom;
	}

	public int getNumMale() {
		return numMale;
	}

	public void setNumMale(int numMale) {
		this.numMale = numMale;
	}

	public int getNumFemale() {
		return numFemale;
	}

	public void setNumFemale(int numFemale) {
		this.numFemale = numFemale;
	}

	public float getWeightMaleU() {
		return weightMaleU;
	}

	public void setWeightMaleU(float weightMaleU) {
		this.weightMaleU = weightMaleU;
	}

	public float getWeightMaleL() {
		return weightMaleL;
	}

	public void setWeightMaleL(float weightMaleL) {
		this.weightMaleL = weightMaleL;
	}

	public float getWeightFemaleU() {
		return weightFemaleU;
	}

	public void setWeightFemaleU(float weightFemaleU) {
		this.weightFemaleU = weightFemaleU;
	}

	public float getWeightFemaleL() {
		return weightFemaleL;
	}

	public void setWeightFemaleL(float weightFemaleL) {
		this.weightFemaleL = weightFemaleL;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public float getAgeMaleU() {
		return ageMaleU;
	}

	public void setAgeMaleU(float ageMaleU) {
		this.ageMaleU = ageMaleU;
	}

	public float getAgeMaleL() {
		return ageMaleL;
	}

	public void setAgeMaleL(float ageMaleL) {
		this.ageMaleL = ageMaleL;
	}

	public float getAgeFemaleU() {
		return ageFemaleU;
	}

	public void setAgeFemaleU(float ageFemaleU) {
		this.ageFemaleU = ageFemaleU;
	}

	public float getAgeFemaleL() {
		return ageFemaleL;
	}

	public void setAgeFemaleL(float ageFemaleL) {
		this.ageFemaleL = ageFemaleL;
	}

	public String getAgeUnit() {
		return ageUnit;
	}

	public void setAgeUnit(String ageUnit) {
		this.ageUnit = ageUnit;
	}

	public String getAniState() {
		return aniState;
	}

	public void setAniState(String aniState) {
		this.aniState = aniState;
	}

	public String getAniStateSignId() {
		return aniStateSignId;
	}

	public void setAniStateSignId(String aniStateSignId) {
		this.aniStateSignId = aniStateSignId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
}
