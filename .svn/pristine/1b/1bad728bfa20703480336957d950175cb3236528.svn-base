package com.lanen.model.path;

import java.util.Date;

/**死亡动物致死原因记录表
 * @author Administrator
 *
 */
public class TblDeadAnimalDeathReason implements java.io.Serializable{

	private static final long serialVersionUID = 927040242366368747L;
	
	private String id;                   //
	private String anatomyAnimalId;      //解剖动物记录Id
	private String studyNo;              //专题编号
	private String animalCode;           //动物编号
	private int deathReasonType;     //死亡原因分类   1：来自字典  2：来自镜检
	private String deathReason;          //死亡原因，    类型1：将字典项内容复制到这里；
										//类型2：将病变描述登记到这里（LesionFinding），使用时，以HistopathCheckID为准
	private String histopathCheckId;     //镜检记录Id，死亡原因分类为2 时记录
	private String remark;               //备注
	private String signId;               //签字Id
	private int delFlag;             //删除标记     0：正常    1：删除
	private String delRsn;               //删除原因
	private Date delTime;                //删除时间
	private String delSignId;            //删除签字Id（删除时，签字Id）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnatomyAnimalId() {
		return anatomyAnimalId;
	}
	public void setAnatomyAnimalId(String anatomyAnimalId) {
		this.anatomyAnimalId = anatomyAnimalId;
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
	public int getDeathReasonType() {
		return deathReasonType;
	}
	public void setDeathReasonType(int deathReasonType) {
		this.deathReasonType = deathReasonType;
	}
	public String getDeathReason() {
		return deathReason;
	}
	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}
	public String getHistopathCheckId() {
		return histopathCheckId;
	}
	public void setHistopathCheckId(String histopathCheckId) {
		this.histopathCheckId = histopathCheckId;
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
