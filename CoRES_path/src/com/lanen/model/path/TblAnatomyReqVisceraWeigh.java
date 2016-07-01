package com.lanen.model.path;

import java.io.Serializable;

/**
 * 解剖申请-脏器称重
 * @author 曾锋
 */
public class TblAnatomyReqVisceraWeigh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720843571325975629L;
	private String id;        //记录ID
    private String studyNo;    //课题编号
    private int reqNo;         //申请编号
    private int visceraType; //脏器类型
    private String visceraCode;  //脏器编号
    private String visceraName;  //脏器名称
    private int attachedVisceraFlag; //有无附加脏器
    private int partVisceraSeparateWeigh; //成对脏器分开称重
    private int fixedWeighFlag;  //固定后称重
    private int sn;          //序号
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
	public int getReqNo() {
		return reqNo;
	}
	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
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
	public int getAttachedVisceraFlag() {
		return attachedVisceraFlag;
	}
	public void setAttachedVisceraFlag(int attachedVisceraFlag) {
		this.attachedVisceraFlag = attachedVisceraFlag;
	}
	public int getPartVisceraSeparateWeigh() {
		return partVisceraSeparateWeigh;
	}
	public void setPartVisceraSeparateWeigh(int partVisceraSeparateWeigh) {
		this.partVisceraSeparateWeigh = partVisceraSeparateWeigh;
	}
	public int getFixedWeighFlag() {
		return fixedWeighFlag;
	}
	public void setFixedWeighFlag(int fixedWeighFlag) {
		this.fixedWeighFlag = fixedWeighFlag;
	}
	public int getSn() {
		return sn;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
    
}
