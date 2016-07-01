package com.lanen.model.quarantine;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 实验动物接收表
 * @author 黄国刚
 *
 */
public class TblAnimalRecIndex implements Serializable{

	private static final long serialVersionUID = -277354688470358769L;
	
	
    private String recId;              //	接收单号	RecID	varchar(20)	
    private Date recDate;              //	接收日期	RecDate	datetime		
    private String animalType;         //	动物种类	AnimalType	varchar(20)	
    private String animalStrain;       //	动物品系	AnimalStrain	varchar(20)	
    private String animalLevel;        //	动物级别	AnimalLevel	varchar(20)	
    private int numMale;               //	数量雄	NumMale	integer			
    private int numFemale;             //	数量雌	NumFemale	integer			
    private float ageMaleU;       //	年龄雄上	AgeMaleU	decimal(8,3)	
    private float ageMaleL;       //	年龄雄下	AgeMaleL	decimal(8,3)	
    private float ageFemaleU;     //	年龄雌上	AgeFemalU	decimal(8,3)	
    private float ageFemaleL;     //	年龄雌下	AgeFemalL	decimal(8,3)	
    private String ageUnit;            //	年龄单位	AgeUnit	varchar(20)	
    private String producer;           //	实验动物生产商	Producer	varchar(200)	
    private String certificate;        //	动物合格证号	Certificate	varchar(50)	
    private String room;               //	安置房间号	Room	varchar(50)	
    private int hasAirConditioner;     //	车辆有无空调	HasAirConditioner	integer			
    private String reqDeviation;       //	与需求偏差	ReqDeviation	varchar(200)	
    private String packDamaged;        //	运输箱是否破损	PackDamaged	varchar(20)	
    private String abnormalInfo;       //	动物异常情况	AbnormalInfo	varchar(100)	
    private String thimerosal;         //	消毒液	Thimerosal	varchar(50)	
    private String cerHealthRpt;       //	证明：健康监测报告	CerHealthRpt	varchar(50)	
    private String cerBackInfo;        //	证明：个体背景资料	CerBackInfo	varchar(50)	
    private String cerReceipt;         //	证明：发票	CerReceipt	varchar(50)	
    private String cerOther;           //	证明：其他	CerOther	varchar(50)	
    private String remark;             //	备注	Remark	varchar(100)	
    private String signId;           //	接收人	Receiver	varchar(20)	
    private String checkId;            //	复核者	Checker	varchar(20)	
    
    private Set<TblAnimalRecList> tblAnimalRecLists = new HashSet<TblAnimalRecList>();

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public Date getRecDate() {
		return recDate;
	}

	public void setRecDate(Date recDate) {
		this.recDate = recDate;
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

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getHasAirConditioner() {
		return hasAirConditioner;
	}

	public void setHasAirConditioner(int hasAirConditioner) {
		this.hasAirConditioner = hasAirConditioner;
	}

	public String getReqDeviation() {
		return reqDeviation;
	}

	public void setReqDeviation(String reqDeviation) {
		this.reqDeviation = reqDeviation;
	}

	public String getPackDamaged() {
		return packDamaged;
	}

	public void setPackDamaged(String packDamaged) {
		this.packDamaged = packDamaged;
	}

	public String getAbnormalInfo() {
		return abnormalInfo;
	}

	public void setAbnormalInfo(String abnormalInfo) {
		this.abnormalInfo = abnormalInfo;
	}

	public String getThimerosal() {
		return thimerosal;
	}

	public void setThimerosal(String thimerosal) {
		this.thimerosal = thimerosal;
	}

	public String getCerHealthRpt() {
		return cerHealthRpt;
	}

	public void setCerHealthRpt(String cerHealthRpt) {
		this.cerHealthRpt = cerHealthRpt;
	}

	public String getCerBackInfo() {
		return cerBackInfo;
	}

	public void setCerBackInfo(String cerBackInfo) {
		this.cerBackInfo = cerBackInfo;
	}

	public String getCerReceipt() {
		return cerReceipt;
	}

	public void setCerReceipt(String cerReceipt) {
		this.cerReceipt = cerReceipt;
	}



	public String getCerOther() {
		return cerOther;
	}

	public void setCerOther(String cerOther) {
		this.cerOther = cerOther;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Set<TblAnimalRecList> getTblAnimalRecLists() {
		return tblAnimalRecLists;
	}

	public void setTblAnimalRecLists(Set<TblAnimalRecList> tblAnimalRecLists) {
		this.tblAnimalRecLists = tblAnimalRecLists;
	}
    
    
}
