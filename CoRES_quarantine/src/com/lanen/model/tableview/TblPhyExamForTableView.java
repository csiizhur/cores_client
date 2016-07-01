package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblPhyExamForTableView {
	private StringProperty animalId;	//	动物ID	AnimalID	varchar(20)	
	private StringProperty gender;			//	动物性别	Gender	integer			
	private StringProperty bodyWeight;	//	体重	BodyWeight	varchar(20)	20	
	private StringProperty temperature;	//	体温	Temperature	varchar(20)	20	
	private StringProperty recordTime;	//	记录时间	
	private StringProperty examResult;	//	体检结果	ExamResult	varchar(200)
	private StringProperty id;          
	
	public TblPhyExamForTableView(
			String animalId,int gender,String bodyWeight,
			String temperature,Date recordTime,String examResult,String id
			){
		setAnimalId(animalId);
		setGender(gender);
		setBodyWeight(bodyWeight);
		setTemperature(temperature);
		setExamResult(examResult);
		setRecordTime(recordTime);
		setId(id);
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
		this.gender = new SimpleStringProperty( gender == 1 ? "♂":"♀");
	}
	public String getBodyWeight() {
		return bodyWeight.get();
	}
	public void setBodyWeight(String bodyWeight) {
		this.bodyWeight = new SimpleStringProperty(bodyWeight);
	}

	public String getTemperature() {
		return temperature.get();
	}
	public void setTemperature(String temperature) {
		this.temperature = new SimpleStringProperty(temperature);
	}
	public String getExamResult() {
		return examResult.get();
	}
	public void setExamResult(String examResult) {
		this.examResult = new SimpleStringProperty(examResult);
	}


	public String getRecordTime() {
		return recordTime.get();
	}

	public void setRecordTime(Date  recordTime) {
		this.recordTime = new SimpleStringProperty(
				DateUtil.dateToString(recordTime, "yyyy-MM-dd HH:mm"));
	}


	public String getId() {
		return id.get();
	}


	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	
	
	
}
