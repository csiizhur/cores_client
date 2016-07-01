package com.lanen.model.studyplan;

import java.io.Serializable;

/**
 * 剂量设置
 * @author Administrator
 *
 */
public class TblDoseSetting   implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4868081828336991915L;
	private String id;                     //    主键，自增长
	private TblStudyPlan tblStudyPlan ;  //试验计划，     类
	private int dosageNum;               //剂量组编号
	private String dosageDesc;           //剂量组说明
	private String dosage;               //剂量
	private int maleNum;                 //雄性数量
	private int femaleNum;               //雌性数量
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TblStudyPlan getTblStudyPlan() {
		return tblStudyPlan;
	}
	public void setTblStudyPlan(TblStudyPlan tblStudyPlan) {
		this.tblStudyPlan = tblStudyPlan;
	}
	public int getDosageNum() {
		return dosageNum;
	}
	public void setDosageNum(int dosageNum) {
		this.dosageNum = dosageNum;
	}
	public String getDosageDesc() {
		return dosageDesc;
	}
	public void setDosageDesc(String dosageDesc) {
		this.dosageDesc = dosageDesc;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public int getMaleNum() {
		return maleNum;
	}
	public void setMaleNum(int maleNum) {
		this.maleNum = maleNum;
	}
	public int getFemaleNum() {
		return femaleNum;
	}
	public void setFemaleNum(int femaleNum) {
		this.femaleNum = femaleNum;
	}
	
}
