package com.lanen.model.quarantine;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 试验动物需求单
 * @author 黄国刚
 *
 */
public class TblQRRequest  implements Serializable  {

	private static final long serialVersionUID = 7017935151076040942L;
	
	private String qrRID ;				//	申请单号	QRRID	varchar(20)	
	private String iacucCode;			//	IACUC编号	IACUCCode	varchar(40)	
	private int studyPlanFinished;	//	有无试验方案	StudyPlanFinished	integer	 0：无  1：有		
	private String animalType;			//	动物种类	AnimalType	varchar(20)	
    private String animalStrain;        //	动物品系	AnimalStrain	varchar(20)	
    private String animalLevel;         //	动物级别	AnimalLevel	varchar(20)
    private int numMale;                 //	数量雄	NumMale	integer			
    private int numFemale;              //	数量雌	NumFemale	integer			
    private float ageMaleU ;       //	年龄雄上	AgeMaleU	decimal(8,3)	
    private float ageMaleL;        //	年龄雄下	AgeMaleL	decimal(8,3)	
    private float ageFemaleU;      //	年龄雌上	AgeFemaleU	decimal(8,3)	
    private float ageFemaleL;      //	年龄雌下	AgeFemaleL	decimal(8,3)	
    private String ageUnit;             //	年龄单位	AgeUnit	varchar(20)	
    private float weightMaleU;     //	体重雄上	WeightMaleU	decimal(8,3)	
    private float weightMaleL;     //	体重雄下	WeightMaleL	decimal(8,3)	
    private float weightFemaleU;   //	体重雌上	WeightFemaleU	decimal(8,3)	
    private float weightFemaleL;   //	体重雌下	WeightFemaleL	decimal(8,3)	
    private String weightUnit;          //	体重单位	WeightUnit	varchar(20)	
    private String specialRearReq;      //	特殊饲养要求	SpecialRearReq	varchar(400)	
    private String intendRoom;          //	拟占用房间时间	IntendRoom	varchar(60)	60		
    private String remark;              //	备注	Remark	varchar(200)	
    private String applicant;           //	申请人	Applicant	varchar(20)	20		
    private Date applyDate;             //	申请日期	ApplyDate	datetime			
    private Date submitTime;            //	提交时间	SubmitTime	datetime			
    private int acceptState;            //	受理状态	AcceptState	integer			
    private String acceptMan;           //	受理人	AcceptMan	varchar(20)	20		
    private Date acceptDate;            //	受理时间	AcceptDate	datetime			
    private int transferState;          //	移交状态	TransferState	integer			
	private int planState;              //分配状态
    Set<TblQRRequestStudyNo> tblQRRequestStudyNos = new HashSet<TblQRRequestStudyNo>();

	public String getQrRID() {
		return qrRID;
	}

	public void setQrRID(String qrRID) {
		this.qrRID = qrRID;
	}

	public String getIacucCode() {
		return iacucCode;
	}

	public void setIacucCode(String iacucCode) {
		this.iacucCode = iacucCode;
	}

	public int getStudyPlanFinished() {
		return studyPlanFinished;
	}

	public void setStudyPlanFinished(int studyPlanFinished) {
		this.studyPlanFinished = studyPlanFinished;
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

	public String getSpecialRearReq() {
		return specialRearReq;
	}

	public void setSpecialRearReq(String specialRearReq) {
		this.specialRearReq = specialRearReq;
	}

	public String getIntendRoom() {
		return intendRoom;
	}

	public void setIntendRoom(String intendRoom) {
		this.intendRoom = intendRoom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public int getAcceptState() {
		return acceptState;
	}

	public void setAcceptState(int acceptState) {
		this.acceptState = acceptState;
	}

	public String getAcceptMan() {
		return acceptMan;
	}

	public void setAcceptMan(String acceptMan) {
		this.acceptMan = acceptMan;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public int getTransferState() {
		return transferState;
	}

	public void setTransferState(int transferState) {
		this.transferState = transferState;
	}

	public Set<TblQRRequestStudyNo> getTblQRRequestStudyNos() {
		return tblQRRequestStudyNos;
	}

	public void setTblQRRequestStudyNos(
			Set<TblQRRequestStudyNo> tblQRRequestStudyNos) {
		this.tblQRRequestStudyNos = tblQRRequestStudyNos;
	}

	public int getPlanState() {
		return planState;
	}

	public void setPlanState(int planState) {
		this.planState = planState;
	}
    
    
}
