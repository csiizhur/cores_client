package com.lanen.model.quarantine.tblsession;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 会话登记表
 * @author 黄国刚
 *
 */
public class TblSession implements java.io.Serializable {

	private static final long serialVersionUID = -7516903732524835400L;
	
	private String sessionId;		//	会话ID	SessionID	varchar(20)	
	private String creater;			//	创建人	Creater	varchar(20)	
	private Date createTime;		//	创建时间	CreateTime	datetime		
	private String sessionType;		//	会话类型	SessionType	varchar(20)	20	
	private String recId;			//	接收单号	RecID	varchar(20)	20		
	private String studyNo;			//	课题编号	StudyNo	varchar(20)	20		
	private Date beginTime;			//	开始时间	BeginTime	datetime		
	private Date endTime;			//	结束时间	EndTime	datetime			
	private String animalType;		//	动物种类	AnimalType	varchar(20)	20	
	private String animalStrain;	//	动物品系	AnimalStrain	varchar(20)	
	private String animalLevel;		//	动物级别	AnimalLevel	varchar(20)	20	

	private String signId;			//	签字ID	SignID	varchar(20)	20		
	private String checkId;			//	复核ID	CheckID	varchar(20)	20	
	
	//会话动物列表s
	private Set<TblSessionAnimals>   tblSessionAnimalss = new HashSet<TblSessionAnimals>();
//	//一般观察s
//	private Set<TblGeneralObservation>   tblGeneralObservations = new HashSet<TblGeneralObservation>();
//	//动物异常处理s
//	private Set<TblAbnormality>   tblAbnormalitys = new HashSet<TblAbnormality>();
//	//动物移交记录s
//	private Set<TblAniHandover>   tblAniHandovers = new HashSet<TblAniHandover>();
//	//动物死亡登记
//	private Set<TblAnimalDeath>   tblAnimalDeaths = new HashSet<TblAnimalDeath>();
//	//动物重新安置记录s
//	private Set<TblAniResite>   tblAniResites = new HashSet<TblAniResite>();
//	//体重称重s
//	private Set<TblBodyWeight>   tblBodyWeights = new HashSet<TblBodyWeight>();
//	//动物驯化s
//	private Set<TblDomestication>   tblDomestications = new HashSet<TblDomestication>();
//	//动物体检表s
//	private Set<TblPhyExam>   tblPhyExams = new HashSet<TblPhyExam>();
//	//检疫报告（小）s
//	private Set<TblQRRpt1>   tblQRRpt1s = new HashSet<TblQRRpt1>();
//	//检疫报告（大）s
//	private Set<TblQRRpt2>   tblQRRpt2s = new HashSet<TblQRRpt2>();
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSessionType() {
		return sessionType;
	}
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public Set<TblSessionAnimals> getTblSessionAnimalss() {
		return tblSessionAnimalss;
	}
	public void setTblSessionAnimalss(Set<TblSessionAnimals> tblSessionAnimalss) {
		this.tblSessionAnimalss = tblSessionAnimalss;
	}
	
}
