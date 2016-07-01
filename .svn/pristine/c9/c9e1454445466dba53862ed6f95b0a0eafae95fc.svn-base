package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblGeneralObservationForTableView {
	private StringProperty id;				//	数据ID	ID	varchar(20)	20		
	private StringProperty animalId;		//	动物ID	AnimalID	varchar(20)	
	private StringProperty observation;		//	观察所见	Observation	varchar(200)	
	private StringProperty recordTime;		//	记录时间	RecordTime	datetime	
	
	public TblGeneralObservationForTableView(
			String id,String animalId,String observation,Date recordTime
			){
		setId(id);
		setAnimalId(animalId);
		setObservation(observation);
		setRecordTime(recordTime);
	}
	
	
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId =  new SimpleStringProperty(animalId);
	}
	public String getObservation() {
		return observation.get();
	}
	public void setObservation(String observation) {
		this.observation =  new SimpleStringProperty(observation);
	}
	public String getRecordTime() {
		return recordTime.get();
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = new SimpleStringProperty(
				DateUtil.dateToString(recordTime, "HH:mm:ss"));
	}
	
	
	
}
