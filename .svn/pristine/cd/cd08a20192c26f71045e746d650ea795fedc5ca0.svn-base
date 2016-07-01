package com.lanen.model.path;

import java.util.Date;

/**
 * 病理检查会话
 * @author 黄国刚
 *
 */
public class TblPathSession implements java.io.Serializable{

	private static final long serialVersionUID = -2459332523356013752L;

	private String sessionId;          //会话id 20
	private String taskId;             //任务id 20
	private int sessionType;           //会话类型
//										1：解剖会话
//										2：脏器称重会话
//										4：固定会话
//										8：固定后称重
//									
//										3:=1+2
//										5:=1+4
//										6=2+4
//										7=1+2+4
	private String sessionCreator;     //会话创建人20
	private Date createdTime;          //创建时间
	private String sessionFinishSign;  //会话确认签字20
	private String sessionReviewSign;  //会话审核签字20
	private String closeRsn;           //会话关闭原因60
	private String balValidationId;    //天平校准记录id20
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public int getSessionType() {
		return sessionType;
	}
	public void setSessionType(int sessionType) {
		this.sessionType = sessionType;
	}
	public String getSessionCreator() {
		return sessionCreator;
	}
	public void setSessionCreator(String sessionCreator) {
		this.sessionCreator = sessionCreator;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getSessionFinishSign() {
		return sessionFinishSign;
	}
	public void setSessionFinishSign(String sessionFinishSign) {
		this.sessionFinishSign = sessionFinishSign;
	}
	public String getSessionReviewSign() {
		return sessionReviewSign;
	}
	public void setSessionReviewSign(String sessionReviewSign) {
		this.sessionReviewSign = sessionReviewSign;
	}
	public String getCloseRsn() {
		return closeRsn;
	}
	public void setCloseRsn(String closeRsn) {
		this.closeRsn = closeRsn;
	}
	public String getBalValidationId() {
		return balValidationId;
	}
	public void setBalValidationId(String balValidationId) {
		this.balValidationId = balValidationId;
	}
	
	
	
}
