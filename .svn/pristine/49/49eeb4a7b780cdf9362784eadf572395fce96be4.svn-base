package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * 需求申请
 * @author Administrator
 *
 */
public class TblQRRequestForTableView {

	private StringProperty qrRID; // 申请单号
	private StringProperty iacucCode; // IACUC编号
	private StringProperty studyPlanFinished; // 有无试验方案 0：无 1：有
	private StringProperty animalType; // 动物种类
	private StringProperty animalStrain; // 动物品系
	private StringProperty animalLevel; // 动物级别
	private StringProperty numMale; // 数量雄
	private StringProperty numFemale; // 数量雌
	private StringProperty ageMaleUL; // 雄年龄 范围
	private StringProperty ageFemaleUL; // 雌年龄范围
	private StringProperty weightMaleUL; // 雄体重范围
	private StringProperty weightFemaleUL; // 雌体重范围
	private StringProperty applicant; // 申请人
	private StringProperty submitTime; // 提交时间
	private StringProperty acceptState; // 受理状态
	private StringProperty transferState; // 提交状态
	private StringProperty planState; // 分配状态

	public TblQRRequestForTableView() {
		super();
	}

	public TblQRRequestForTableView(String qrRID, String iacucCode, int studyPlanFinished,
			String animalType, String animalStrain, String animalLevel, int numMale,
			int numFemale, String ageMaleUL, String ageFemaleUL, String weightMaleUL,
			String weightFemaleUL, String applicant, Date submitTime, int acceptState,
			int transferState,int planState) {
		this.qrRID = new SimpleStringProperty(qrRID); // 申请单号
		this.iacucCode = new SimpleStringProperty(iacucCode); // IACUC编号
		if (studyPlanFinished == 1) {
			this.studyPlanFinished = new SimpleStringProperty("有"); // 有无试验方案
																	// 0：无 1：有
		} else {
			this.studyPlanFinished = new SimpleStringProperty("无");
		}
		this.animalType = new SimpleStringProperty(animalType); // 动物种类
		this.animalStrain = new SimpleStringProperty(animalStrain); // 动物品系
		this.animalLevel = new SimpleStringProperty(animalLevel); // 动物级别
		this.numMale = new SimpleStringProperty(numMale+""); // 数量雄
		this.numFemale = new SimpleStringProperty(numFemale+""); // 数量雌
		this.ageMaleUL = new SimpleStringProperty(ageMaleUL); // 雄年龄 范围
		this.ageFemaleUL = new SimpleStringProperty(ageFemaleUL); // 雌年龄范围
		this.weightMaleUL = new SimpleStringProperty(weightMaleUL); // 雄体重范围
		this.weightFemaleUL = new SimpleStringProperty(weightFemaleUL); // 雌体重范围
		this.applicant = new SimpleStringProperty(applicant); // 申请人
		if (null != submitTime) {
			this.submitTime = new SimpleStringProperty(DateUtil.dateToString(submitTime,
					"yyyy-MM-dd")); // 提交时间
		} else {
			this.submitTime = new SimpleStringProperty("");
		}
		if (acceptState == 1) {
			this.acceptState = new SimpleStringProperty("是"); // 受理状态
		} else if(acceptState == 0){
			this.acceptState = new SimpleStringProperty("否");
		}else{
			this.acceptState = new SimpleStringProperty("取消");
		}
		if (transferState == 1) {
			this.transferState = new SimpleStringProperty("是"); // 提交状态
		} else {
			this.transferState = new SimpleStringProperty("否");
		}
		if (planState == 1) {
			this.planState = new SimpleStringProperty("是"); // 分配状态
		} else {
			this.planState = new SimpleStringProperty("否");
		}
	}

	public String getQrRID() {
		return qrRID.get();
	}

	public void setQrRID(String qrRID) {
		this.qrRID = new SimpleStringProperty( qrRID);
	}

	public String getIacucCode() {
		return iacucCode.get();
	}

	public void setIacucCode(String iacucCode) {
		this.iacucCode = new SimpleStringProperty( iacucCode);
	}

	public String getStudyPlanFinished() {
		return studyPlanFinished.get();
	}

	public void setStudyPlanFinished(String studyPlanFinished) {
		this.studyPlanFinished = new SimpleStringProperty( studyPlanFinished);
	}

	public String getAnimalType() {
		return animalType.get();
	}

	public void setAnimalType(String animalType) {
		this.animalType = new SimpleStringProperty( animalType);
	}

	public String getAnimalStrain() {
		return animalStrain.get();
	}

	public void setAnimalStrain(String animalStrain) {
		this.animalStrain = new SimpleStringProperty( animalStrain);
	}

	public String getAnimalLevel() {
		return animalLevel.get();
	}

	public void setAnimalLevel(String animalLevel) {
		this.animalLevel = new SimpleStringProperty( animalLevel);
	}

	public String getNumMale() {
		return numMale.get();
	}

	public void setNumMale(String numMale) {
		this.numMale = new SimpleStringProperty( numMale);
	}

	public String getNumFemale() {
		return numFemale.get();
	}

	public void setNumFemale(String numFemale) {
		this.numFemale = new SimpleStringProperty( numFemale);
	}

	public String getAgeMaleUL() {
		return ageMaleUL.get();
	}

	public void setAgeMaleUL(String ageMaleUL) {
		this.ageMaleUL = new SimpleStringProperty( ageMaleUL);
	}

	public String getAgeFemaleUL() {
		return ageFemaleUL.get();
	}

	public void setAgeFemaleUL(String ageFemaleUL) {
		this.ageFemaleUL = new SimpleStringProperty( ageFemaleUL);
	}

	public String getWeightMaleUL() {
		return weightMaleUL.get();
	}

	public void setWeightMaleUL(String weightMaleUL) {
		this.weightMaleUL = new SimpleStringProperty( weightMaleUL);
	}

	public String getWeightFemaleUL() {
		return weightFemaleUL.get();
	}

	public void setWeightFemaleUL(String weightFemaleUL) {
		this.weightFemaleUL = new SimpleStringProperty( weightFemaleUL);
	}

	public String getApplicant() {
		return applicant.get();
	}

	public void setApplicant(String applicant) {
		this.applicant = new SimpleStringProperty( applicant);
	}

	public String getSubmitTime() {
		return submitTime.get();
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = new SimpleStringProperty( submitTime);
	}

	public String getAcceptState() {
		return acceptState.get();
	}

	public void setAcceptState(String acceptState) {
		this.acceptState = new SimpleStringProperty( acceptState);
	}

	public String getTransferState() {
		return transferState.get();
	}

	public void setTransferState(String transferState) {
		this.transferState = new SimpleStringProperty( transferState);
	}

	public String getPlanState() {
		return planState.get();
	}

	public void setPlanState(String planState) {
		this.planState = new SimpleStringProperty(planState);
	}

}
