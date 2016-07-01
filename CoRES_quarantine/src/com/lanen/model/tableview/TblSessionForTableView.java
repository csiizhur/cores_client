package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblSessionForTableView {
	private StringProperty sessionId;		//	会话ID	SessionID	varchar(20)	
	private StringProperty creater;			//	创建人	Creater	varchar(20)	
	private StringProperty createTime;		//	创建时间	CreateTime	datetime		
	private StringProperty sessionType;		//	会话类型	SessionType	varchar(20)	20	
	private StringProperty recId;			//	接收单号	RecID	varchar(20)	20		
	private StringProperty studyNo;			//	课题编号	StudyNo	varchar(20)	20		
	private StringProperty beginTime;			//	开始时间	BeginTime	datetime		
	private StringProperty endTime;			//	结束时间	EndTime	datetime			
	private StringProperty animalType;		//	动物种类	AnimalType	varchar(20)	20	
	private StringProperty signer;			//	签字ID	SignID	varchar(20)	20		
	private StringProperty checker;			//	复核ID	CheckID	varchar(20)	20	
	
	public TblSessionForTableView(){}
	public TblSessionForTableView(
			String sessionId,		
			String creater,			
			Date createTime,		
			String sessionType,		
			String recId,			
			String studyNo,			
			Date beginTime,			
			Date endTime,			
			String animalType,		
			String signer,			
			String checker		
			){
		setSessionId(sessionId);
		setCreater(creater);
		setCreateTime(createTime);
		setSessionType(sessionType);
		setRecId(recId);
		setStudyNo(studyNo);
		setBeginTime(beginTime);
		setEndTime(endTime);
		setAnimalType(animalType);
		setSigner(signer);
		setChecker(checker);
		
	}
	public String getSessionId() {
		return sessionId.get();
	}
	public void setSessionId(String sessionId) {
		this.sessionId = new SimpleStringProperty( sessionId);
	}
	public String getCreater() {
		return creater.get();
	}
	public void setCreater(String creater) {
		this.creater = new SimpleStringProperty( creater);
	}
	public String getCreateTime() {
		return createTime.get();
	}
	public void setCreateTime(Date createTime) {
		this.createTime = new SimpleStringProperty(
				createTime !=null ? DateUtil.dateToString(createTime, "yyyy-MM-dd"):"");
	}
	public String getSessionType() {
		return sessionType.get();
	}
	public void setSessionType(String sessionType) {
		this.sessionType = new SimpleStringProperty( sessionType);
	}
	public String getRecId() {
		return recId.get();
	}
	public void setRecId(String recId) {
		this.recId = new SimpleStringProperty( recId);
	}
	public String getStudyNo() {
		return studyNo.get();
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = new SimpleStringProperty( studyNo);
	}
	public String getBeginTime() {
		return beginTime.get();
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = new SimpleStringProperty( 
				beginTime !=null ? DateUtil.dateToString(beginTime, "HH:mm"):"");
	}
	public String getEndTime() {
		return endTime.get();
	}
	public void setEndTime(Date endTime) {
		this.endTime = new SimpleStringProperty( 
				endTime !=null ? DateUtil.dateToString(endTime, "HH:mm"):"");
	}
	public String getAnimalType() {
		return animalType.get();
	}
	public void setAnimalType(String animalType) {
		this.animalType = new SimpleStringProperty( animalType);
	}
	public String getSigner() {
		return signer.get();
	}
	public void setSigner(String signer) {
		this.signer = new SimpleStringProperty( signer);
	}
	public String getChecker() {
		return checker.get();
	}
	public void setChecker(String checker) {
		this.checker = new SimpleStringProperty( checker);
	}
	
	
}
