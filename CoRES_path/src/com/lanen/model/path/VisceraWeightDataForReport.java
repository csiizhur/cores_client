package com.lanen.model.path;

public class VisceraWeightDataForReport {
	private String animalCode;         //动物编号
    private int gender ;			//性别
    private String testIndexAbbr;      //检验指标缩写
    private String testData;           //数据
    private String studyNo;
    

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
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}


}
