package com.lanen.view.test;

public class AnatomyCheckClassBean {
	private String animalCode;
   private String  visceraName;   //脏器名称
   private String  anatomyCheckResult;  //剖检结果
   private String remarks;
   public AnatomyCheckClassBean(String animalCode,String visceraName, String anatomyCheckResult,String remarks) {
	   super();
	   this.visceraName = visceraName;
	   this.anatomyCheckResult = anatomyCheckResult;
	   setAnimalCode(animalCode);
	   setRemarks(remarks);
   }
	public String getVisceraName() {
		return visceraName;
	}
	public void setVisceraName(String visceraName) {
		this.visceraName = visceraName;
	}
	public String getAnatomyCheckResult() {
		return anatomyCheckResult;
	}
	public void setAnatomyCheckResult(String anatomyCheckResult) {
		this.anatomyCheckResult = anatomyCheckResult;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
   
   
   
}
