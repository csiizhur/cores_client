package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;

import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;

/**
 * 标本接收登记
 * @author Administrator
 *
 */
public class TblSpecimen  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8966892700723074809L;
	private  String specimenId;                                 //标本Id
	
	private int reqNo;                                            //临检申请编号
	
	private TblStudyPlan tblStudyPlan;                          //类，试验计划
	private TblClinicalTestReq tblClinicalTestReq;              //类，临检申请
	private TblClinicalTestReqIndex2 tblClinicalTestReqIndex2;  //类，临检申请单-动物编号
	private String animalId;                                    //动物Id
	private String animalCode;                                  //动物编号
	private Date recDate;                                       //接收日期
	private Date recTime;                                       //接收时间
	private int  testItem;                                      //检验项目
	private String specimenCode;                                //检验编号
	private int aniSerialNum;                                   //动物序号
	private String specimenKind;								//标本类型
	public String getSpecimenId() {
		return specimenId;
	}
	public void setSpecimenId(String specimenId) {
		this.specimenId = specimenId;
	}
	public TblStudyPlan getTblStudyPlan() {
		return tblStudyPlan;
	}
	public void setTblStudyPlan(TblStudyPlan tblStudyPlan) {
		this.tblStudyPlan = tblStudyPlan;
	}
	public TblClinicalTestReq getTblClinicalTestReq() {
		return tblClinicalTestReq;
	}
	public void setTblClinicalTestReq(TblClinicalTestReq tblClinicalTestReq) {
		this.tblClinicalTestReq = tblClinicalTestReq;
	}
	public TblClinicalTestReqIndex2 getTblClinicalTestReqIndex2() {
		return tblClinicalTestReqIndex2;
	}
	public void setTblClinicalTestReqIndex2(
			TblClinicalTestReqIndex2 tblClinicalTestReqIndex2) {
		this.tblClinicalTestReqIndex2 = tblClinicalTestReqIndex2;
	}
	public String getAnimalId() {
		return animalId;
	}
	public void setAnimalId(String animalId) {
		this.animalId = animalId;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public Date getRecDate() {
		return recDate;
	}
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}
	public Date getRecTime() {
		return recTime;
	}
	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}
	public int getTestItem() {
		return testItem;
	}
	public void setTestItem(int testItem) {
		this.testItem = testItem;
	}
	public String getSpecimenCode() {
		return specimenCode;
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode = specimenCode;
	}
	public int getAniSerialNum() {
		return aniSerialNum;
	}
	public void setAniSerialNum(int aniSerialNum) {
		this.aniSerialNum = aniSerialNum;
	}
	public int getReqNo() {
		return reqNo;
	}
	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
	}
	public String getSpecimenKind() {
		return specimenKind;
	}
	public void setSpecimenKind(String specimenKind) {
		this.specimenKind = specimenKind;
	}
	
	

}
