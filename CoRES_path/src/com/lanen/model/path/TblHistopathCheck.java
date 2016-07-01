package com.lanen.model.path;

import java.util.Date;

/**
 * 组织病理学检查
 * @author 黄国刚
 *
 */
public class TblHistopathCheck implements java.io.Serializable{

	private static final long serialVersionUID = -1051149480450337785L;

	private String id;                 	// 20
	private String taskId;          		//任务id 20
	private String studyNo;            		//课题编号20
	private String animalCode;         		//动物编号20
	private String tissueSliceVisceraRecordId;  //组织切片脏器记录ID
	private int visceraType;           		//脏器类型(暂未使用)  开始使用
	private String visceraCode;       		//脏器编号20(暂未使用)  开始使用
	private String visceraName;        		//脏器名称60(暂未使用)  开始使用
	private String subVisceraCode;    		//子 脏器编号20(暂未使用)  开始使用
	private String subVisceraName;     		//子 脏器名称60(暂未使用)  开始使用
	private String visceraFixedRecordId;	//固定记录Id20(暂未使用) 
	
	private int isNoAbnormal;				//是否     未见异常    0:否   1：是    -1：登记缺失
	
	private int tumorFlag;				     //是否肿瘤
	private int metastasisFlag;              //是否转移
	private String histoPosCode;             //组织学用语编号20
	private String histoPos;                 //组织血用语100
	private String lesionFindingCode;        //病变编号20
	private String lesionFinding;            //病变描述100
	private int tumorNum;                 //肿瘤数量
	private String primaryVisceraCode;       //原发脏器编号20
	private String primaryViscera;           //原发脏器100
	private String primaryTumor;             //原发肿瘤100
	private String refId;                    //参照所见源id20
	private String operator;                 //操作者20
	private Date operateTime ;               //操作时间
	private int historyFlag;                 //历史记录标识0：非历史数据，1：历史数据 （也即删除标记）
	private int histopathReviewFlag;         //复审完成标识0：未（提交复查前或复查完成后添加的）；
											//1：提交复查（提交复查时全部置为1，包括已被删除的）    
												//2：抽查到的（即填写复查意见的）
	private String histopathReviewOpinion;   //复审意见200
	private Date histopathReviewTime ;       //复审时间
	
	private String deleteSignId;			//签字Id    删除签字   ，或登记缺失签字
	private String reason;					 //原因    删除原因，或登记缺失原因
	
	private String level;	//程度（病变程度）
	private String remark;	//备注
	
	private int tumorPos;			//1：浅表；2：深部
	private int tumorCharacter;		//（只对深部肿瘤有效）1：偶发；2：致死；3：不明确
	private Date tumorOccurDate;	//（只对浅表肿瘤有效）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public int getTumorFlag() {
		return tumorFlag;
	}
	public void setTumorFlag(int tumorFlag) {
		this.tumorFlag = tumorFlag;
	}
	public int getMetastasisFlag() {
		return metastasisFlag;
	}
	public void setMetastasisFlag(int metastasisFlag) {
		this.metastasisFlag = metastasisFlag;
	}
	public String getHistoPosCode() {
		return histoPosCode;
	}
	public void setHistoPosCode(String histoPosCode) {
		this.histoPosCode = histoPosCode;
	}
	public String getHistoPos() {
		return histoPos;
	}
	public void setHistoPos(String histoPos) {
		this.histoPos = histoPos;
	}
	public String getLesionFindingCode() {
		return lesionFindingCode;
	}
	public void setLesionFindingCode(String lesionFindingCode) {
		this.lesionFindingCode = lesionFindingCode;
	}
	public String getLesionFinding() {
		return lesionFinding;
	}
	public void setLesionFinding(String lesionFinding) {
		this.lesionFinding = lesionFinding;
	}
	public int getTumorNum() {
		return tumorNum;
	}
	public void setTumorNum(int tumorNum) {
		this.tumorNum = tumorNum;
	}
	public String getPrimaryVisceraCode() {
		return primaryVisceraCode;
	}
	public void setPrimaryVisceraCode(String primaryVisceraCode) {
		this.primaryVisceraCode = primaryVisceraCode;
	}
	public String getPrimaryViscera() {
		return primaryViscera;
	}
	public void setPrimaryViscera(String primaryViscera) {
		this.primaryViscera = primaryViscera;
	}
	public String getPrimaryTumor() {
		return primaryTumor;
	}
	public void setPrimaryTumor(String primaryTumor) {
		this.primaryTumor = primaryTumor;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
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
	public int getHistoryFlag() {
		return historyFlag;
	}
	public void setHistoryFlag(int historyFlag) {
		this.historyFlag = historyFlag;
	}
	public int getHistopathReviewFlag() {
		return histopathReviewFlag;
	}
	public void setHistopathReviewFlag(int histopathReviewFlag) {
		this.histopathReviewFlag = histopathReviewFlag;
	}
	
	public Date getHistopathReviewTime() {
		return histopathReviewTime;
	}
	public void setHistopathReviewTime(Date histopathReviewTime) {
		this.histopathReviewTime = histopathReviewTime;
	}
	
	public String getTissueSliceVisceraRecordId() {
		return tissueSliceVisceraRecordId;
	}
	public void setTissueSliceVisceraRecordId(String tissueSliceVisceraRecordId) {
		this.tissueSliceVisceraRecordId = tissueSliceVisceraRecordId;
	}
	public String getVisceraFixedRecordId() {
		return visceraFixedRecordId;
	}
	public void setVisceraFixedRecordId(String visceraFixedRecordId) {
		this.visceraFixedRecordId = visceraFixedRecordId;
	}
	public int getIsNoAbnormal() {
		return isNoAbnormal;
	}
	public void setIsNoAbnormal(int isNoAbnormal) {
		this.isNoAbnormal = isNoAbnormal;
	}
	
	public String getDeleteSignId() {
		return deleteSignId;
	}
	public void setDeleteSignId(String deleteSignId) {
		this.deleteSignId = deleteSignId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getHistopathReviewOpinion() {
		return histopathReviewOpinion;
	}
	public void setHistopathReviewOpinion(String histopathReviewOpinion) {
		this.histopathReviewOpinion = histopathReviewOpinion;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getTumorPos() {
		return tumorPos;
	}
	public void setTumorPos(int tumorPos) {
		this.tumorPos = tumorPos;
	}
	public int getTumorCharacter() {
		return tumorCharacter;
	}
	public void setTumorCharacter(int tumorCharacter) {
		this.tumorCharacter = tumorCharacter;
	}
	public Date getTumorOccurDate() {
		return tumorOccurDate;
	}
	public void setTumorOccurDate(Date tumorOccurDate) {
		this.tumorOccurDate = tumorOccurDate;
	}
	
}
