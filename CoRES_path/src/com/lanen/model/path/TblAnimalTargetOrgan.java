package com.lanen.model.path;

import java.util.Date;

/**试验靶器官登记表
 * @author Administrator
 *
 */
public class TblAnimalTargetOrgan implements java.io.Serializable {

	private static final long serialVersionUID = 7699653737720118887L;
	
	private String id;            // 
	private String studyNo;       //专题编号
	private int gender;		//    //性别  （0：性别无关  1：雄    2：雌）
	private int visceraType;      //脏器类型
	private String visceraCode;   //脏器编号（主）
	private String visceraName;   //脏器名称（主）
	private String occurPhase;    //发现阶段（从解剖申请中读取）60
	private int targetOrganFlag;  //靶器官状态（1：发生  2：消失）
	private Date regDate;         //登记日期
	private String remark;        //备注  
	private String signId;        //签字Id
	private int delFlag;          //删除标记
	private String delRsn;        //删除原因
	private Date delTime;         //删除时间
	private String delSignId;     //删除签字Id
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public int getVisceraType() {
		return visceraType;
	}
	public void setVisceraType(int visceraType) {
		this.visceraType = visceraType;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getVisceraCode() {
		return visceraCode;
	}
	public void setVisceraCode(String visceraCode) {
		this.visceraCode = visceraCode;
	}
	public String getVisceraName() {
		return visceraName;
	}
	public void setVisceraName(String visceraName) {
		this.visceraName = visceraName;
	}
	public String getOccurPhase() {
		return occurPhase;
	}
	public void setOccurPhase(String occurPhase) {
		this.occurPhase = occurPhase;
	}
	public int getTargetOrganFlag() {
		return targetOrganFlag;
	}
	public void setTargetOrganFlag(int targetOrganFlag) {
		this.targetOrganFlag = targetOrganFlag;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public String getDelRsn() {
		return delRsn;
	}
	public void setDelRsn(String delRsn) {
		this.delRsn = delRsn;
	}
	public Date getDelTime() {
		return delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public String getDelSignId() {
		return delSignId;
	}
	public void setDelSignId(String delSignId) {
		this.delSignId = delSignId;
	}
	
	
	

}
