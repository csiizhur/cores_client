package com.lanen.model.path;

import java.util.Date;

/**
 * 脏器固定记录(历史记录表)
 * @author 黄国刚
 *
 */
public class TblVisceraFixedHis implements java.io.Serializable{

	private static final long serialVersionUID = -5320485732680622254L;
	
	private String id;
	private String sessionId;          //会话id 20
	private String studyNo;            //课题编号20
	private String animalCode;         //动物编号20
	private int fixedType;             //0：常规脏器，1：非常规组织
	private int visceraType;           //脏器类型
	private String visceraCode;        //脏器编号20
	private String visceraName;        //脏器名称60
	private String subVisceraCode;     //子 脏器编号20
	private String subVisceraName;     //子 脏器名称60
	private String anatomyCheckRecordId; //剖检记录ID
	private String operator;           //操作者20
	private Date operateTime;          //操作时间
	
	private String operate;             // 操作 (添加、编辑、删除)
	private String operateRsn;         // 操作原因200
	private Date operateDate;           //修改时间
	private String operater;             // 操作者
	private String oldId;             // 源主键
	
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
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public int getVisceraType() {
		return visceraType;
	}
	public void setVisceraType(int visceraType) {
		this.visceraType = visceraType;
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
	public String getSubVisceraCode() {
		return subVisceraCode;
	}
	public void setSubVisceraCode(String subVisceraCode) {
		this.subVisceraCode = subVisceraCode;
	}
	public String getSubVisceraName() {
		return subVisceraName;
	}
	public void setSubVisceraName(String subVisceraName) {
		this.subVisceraName = subVisceraName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public void setFixedType(int fixedType) {
		this.fixedType = fixedType;
	}
	public int getFixedType() {
		return fixedType;
	}
	public void setAnatomyCheckRecordId(String anatomyCheckRecordId) {
		this.anatomyCheckRecordId = anatomyCheckRecordId;
	}
	public String getAnatomyCheckRecordId() {
		return anatomyCheckRecordId;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperateRsn() {
		return operateRsn;
	}
	public void setOperateRsn(String operateRsn) {
		this.operateRsn = operateRsn;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public String getOldId() {
		return oldId;
	}
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
}
