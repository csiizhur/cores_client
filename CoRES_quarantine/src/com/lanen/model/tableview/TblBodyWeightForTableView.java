package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblBodyWeightForTableView {
	private StringProperty animalId;	//	动物ID	AnimalID	varchar(20)	
	private StringProperty gender;			//	性别	Gender	integer			
	private StringProperty bodyWeight;	//	体重	BodyWeight	varchar(20)	20	
	private StringProperty weightUnit;
	private StringProperty weightTime;	//	称量时间	WeighTime	datetime	
	
	public TblBodyWeightForTableView(){
	}
	public TblBodyWeightForTableView(
			String animalId,int gender,String bodyWeight,String weightUnit,Date weightTime
			){
		setAnimalId(animalId);
		setGender(gender);
		setBodyWeight(bodyWeight);
		setWeightTime(weightTime);
		setWeightUnit(weightUnit);
		
	}
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId = new SimpleStringProperty(animalId);
	}
	public String getGender() {
		return gender.get();
	}
	public void setGender(int gender) {
		this.gender = new SimpleStringProperty(gender == 1? "♂":"♀");
	}
	public String getBodyWeight() {
		return bodyWeight.get();
	}
	public void setBodyWeight(String bodyWeight) {
		this.bodyWeight =new SimpleStringProperty( bodyWeight);
	}
	public String getWeightTime() {
		return weightTime.get();
	}
	public void setWeightTime(Date weightTime) {
		this.weightTime =new SimpleStringProperty(
				weightTime !=null ? DateUtil.dateToString(weightTime, "yyyyMMdd HH:mm:ss"):"");
	}
	public String getWeightUnit() {
		return weightUnit.get();
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = new SimpleStringProperty(weightUnit);
	}
	
	
}
