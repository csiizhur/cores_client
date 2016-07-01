package com.lanen.model.quarantine.tblsession;

import java.util.Date;

/**
 * 动物死亡登记
 * @author 黄国刚
 *
 */
public class TblAnimalDeath implements java.io.Serializable{

	private static final long serialVersionUID = 6988230567602739265L;

	private String sessionId;	//	会话ID	SessionID	varchar(20)	20	
	private Date deadDate;	//	动物死亡日期	DeadDate	datetime		
	private Date foundTime;	//	发现时间	FoundTime	datetime		
	private Date notifyTime;	//	通知时间	NotifyTime	datetime		
	private String deadType;	//	死亡原因	DeadType	varchar(20)	20	
	private String deadRsn;	//	死亡原因描述	DeadRsn	varchar(50)	50		
	private String dealwithOpinion;	//	处理意见	DealwithOpinion	varchar(100)
	private String remark;	//	备注	Remark	varchar(100)	100		
	private String signId;	//	签字ID	SignID	varchar(20)	20		
	private String checkId;	//	复核ID	CheckID	varchar(20)	20		
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getDeadDate() {
		return deadDate;
	}
	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}
	public Date getFoundTime() {
		return foundTime;
	}
	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}
	public Date getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	public String getDeadType() {
		return deadType;
	}
	public void setDeadType(String deadType) {
		this.deadType = deadType;
	}
	public String getDeadRsn() {
		return deadRsn;
	}
	public void setDeadRsn(String deadRsn) {
		this.deadRsn = deadRsn;
	}
	public String getDealwithOpinion() {
		return dealwithOpinion;
	}
	public void setDealwithOpinion(String dealwithOpinion) {
		this.dealwithOpinion = dealwithOpinion;
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
	
	
}
