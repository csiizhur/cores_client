package com.lanen.model.clinicaltest;
/**
 * 检测结果    交叉表  用
 * @author Administrator
 *
 */
public class ClinicalTestDataForReport {
	private String animalId;           //动物Id
	private String animalCode;         //动物编号
    private String testIndexAbbr;      //检验指标缩写
    private String testData;           //数据
    private String specimenCode;     //检验编号
    private int gender ;			//性别
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
	public String getTestIndexAbbr() {
		return testIndexAbbr;
	}
	public void setTestIndexAbbr(String testIndexAbbr) {
		this.testIndexAbbr = testIndexAbbr;
	}
	public String getTestData() {
		return testData;
	}
	public void setTestData(String testData) {
		this.testData = testData;
	}
	public String getSpecimenCode() {
		return specimenCode;
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode = specimenCode;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}

    
}
