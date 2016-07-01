package com.lanen.model.quarantine.tblsession;

import java.util.Date;

/**
 * 动物重新安置记录
 * 
 * @author 黄国刚
 * 
 */
public class TblAniResite implements java.io.Serializable {

	private static final long serialVersionUID = -3186122575511800875L;

	private String sessionId; // 会话ID SessionID varchar(20) 20
	private Date resiteDate; // 日期 ResiteDate datetime
	private Date resiteTime; // 时间 ResiteTime datetime
	private String newRoom; // 新房间号 NewRoom varchar(20) 20
	private String resiteRsn; // 重新安置原因 ResiteRsn varchar(50)
	private String remark; // 备注 Remark varchar(50) 50
	private String signId; // 签字ID SignID varchar(20) 20
	private String checkId; // 复核ID CheckID varchar(20) 20

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getResiteDate() {
		return resiteDate;
	}

	public void setResiteDate(Date resiteDate) {
		this.resiteDate = resiteDate;
	}

	public Date getResiteTime() {
		return resiteTime;
	}

	public void setResiteTime(Date resiteTime) {
		this.resiteTime = resiteTime;
	}

	public String getNewRoom() {
		return newRoom;
	}

	public void setNewRoom(String newRoom) {
		this.newRoom = newRoom;
	}

	public String getResiteRsn() {
		return resiteRsn;
	}

	public void setResiteRsn(String resiteRsn) {
		this.resiteRsn = resiteRsn;
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
