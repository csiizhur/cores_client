package com.lanen.model.path;

import java.io.Serializable;

/**
 * 解剖申请-脏器称重-附加脏器
 * @author 曾锋
 */
public class TblAnatomyReqAttachedViscera implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -988538135746883967L;
	private String id;        //记录ID
	private String anatomyReqVisceraWeighID;  //PID(解剖申请-脏器称重表ID)
	private int visceraType; //脏器类型
    private String visceraCode;  //脏器编号
    private String visceraName;  //脏器名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAnatomyReqVisceraWeighID() {
		return anatomyReqVisceraWeighID;
	}
	public void setAnatomyReqVisceraWeighID(String anatomyReqVisceraWeighID) {
		this.anatomyReqVisceraWeighID = anatomyReqVisceraWeighID;
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
}
