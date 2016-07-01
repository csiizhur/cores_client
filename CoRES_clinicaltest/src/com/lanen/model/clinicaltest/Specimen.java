package com.lanen.model.clinicaltest;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 标本表格用
 * @author Administrator
 *
 */
public class Specimen implements Serializable {

	private static final long serialVersionUID = 5207064853864558675L;
	private StringProperty studyPlanNo;
	private StringProperty reqNo;
	private StringProperty animalId;
	private StringProperty animalCode;
	private StringProperty recDate;
	private StringProperty recTime;
	private StringProperty testItem;
	private StringProperty specimenCode;
	private StringProperty specimenKind;  
	
	private StringProperty sender;//送检人
	private StringProperty receiver;//接收人
	
	public Specimen(){}
	public Specimen(String studyPlanNo,String reqNo,String animalId,
			String animalCode,String recDate,String recTime,
			String testItem,String specimenCode,String specimenKind,String sender,String receiver){
		this.studyPlanNo = new SimpleStringProperty(studyPlanNo);
		this.reqNo = new SimpleStringProperty(reqNo);
		this.animalId = new SimpleStringProperty(animalId);
		this.animalCode = new SimpleStringProperty(animalCode);
		this.recDate = new SimpleStringProperty(recDate);
		this.recTime = new SimpleStringProperty(recTime);
		this.testItem = new SimpleStringProperty(testItem);
		this.specimenCode = new SimpleStringProperty(specimenCode);
		setSpecimenKind(specimenKind);
		setSender(sender);
		setReceiver(receiver);
	}
	public String getStudyPlanNo() {
		return studyPlanNo.get();
	}
	public void setStudyPlanNo(String studyPlanNo) {
		this.studyPlanNo =  new SimpleStringProperty(studyPlanNo);
	}
	public String getReqNo() {
		return reqNo.get();
	}
	public void setReqNo(String reqNo) {
		this.reqNo =  new SimpleStringProperty(reqNo);
	}
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId =  new SimpleStringProperty(animalId);
	}
	public String getAnimalCode() {
		return animalCode.get();
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode =  new SimpleStringProperty(animalCode);
	}
	public String getRecDate() {
		return recDate.get();
	}
	public void setRecDate(String recDate) {
		this.recDate =  new SimpleStringProperty(recDate);
	}
	public String getRecTime() {
		return recTime.get();
	}
	public void setRecTime(String recTime) {
		this.recTime =  new SimpleStringProperty(recTime);
	}
	public String getTestItem() {
		return testItem.get();
	}
	public void setTestItem(String testItem) {
		this.testItem =  new SimpleStringProperty(testItem);
	}
	public String getSpecimenCode() {
		return specimenCode.get();
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode = new SimpleStringProperty(specimenCode);
	}
	public String getSpecimenKind() {
		return specimenKind.get();
	}
	public void setSpecimenKind(String specimenKind) {
		this.specimenKind = new SimpleStringProperty(specimenKind);
	}
	public String getSender() {
		return sender.get();
	}
	public void setSender(String sender) {
		this.sender = new SimpleStringProperty(sender);
	}
	public String getReceiver() {
		return receiver.get();
	}
	public void setReceiver(String receiver) {
		this.receiver =  new SimpleStringProperty(receiver);
	}
	
}
