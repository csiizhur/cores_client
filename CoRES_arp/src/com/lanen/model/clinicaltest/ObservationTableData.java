package com.lanen.model.clinicaltest;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 观察记录表格 用
 * 注意：set/get方法名要对应.
 * @author Administrator
 *
 */
public class ObservationTableData {
	private StringProperty animalCode;         //动物编号
    private StringProperty quyuName;       //区域
   
    private StringProperty animalSex;       //性别
    
    private StringProperty content;//观察内容
    private StringProperty observerName;//观察者
    private StringProperty observationTime;//时间
    private StringProperty observationDate;//日期
    public ObservationTableData(){
    	
    }
    public ObservationTableData(    		
    		String content,String observerName , String observationTime,     
    		String animalCode,    
    		String quyuName,  
    		String animalSex,
    		String observationDate
    		){
    		this.content=new SimpleStringProperty(content);
    	    this.observerName         =  new SimpleStringProperty(observerName);
    	    this.observationTime	=  new SimpleStringProperty(observationTime);
    	    this.animalCode		=  new SimpleStringProperty(animalCode)  ;
    	    this.quyuName	=  new SimpleStringProperty(quyuName);
    	    this.animalSex	=  new SimpleStringProperty(animalSex);
    	    this.observationDate=new SimpleStringProperty(observationDate);
    }
	public String getAnimalCode() {
		return animalCode.get();
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode =  new SimpleStringProperty(animalCode);
	}
	public String getQuyuName() {
		return quyuName.get();
	}
	public void setQuyuName(String quyuName) {
		this.quyuName =  new SimpleStringProperty(quyuName);
	}
	
	public String getAnimalSex() {
		return animalSex.get();
	}
	public void setAnimalSex(String animalSex) {
		this.animalSex = new SimpleStringProperty(animalSex);
	}
	public String getContent() {
		return content.get();
	}
	public void setContent(String content) {
		this.content = new SimpleStringProperty(content);
	}
	public String getObserverName() {
		return observerName.get();
	}
	public void setObserverName(String observerName) {
		this.observerName =  new SimpleStringProperty(observerName);
	}      
	public String getObservationTime() {
		return observationTime.get();
	}
	public void setObservationTime(String observationTime) {
		this.observationTime = new SimpleStringProperty(observationTime);
	}
	public String getObservationDate() {
		return observationDate.get();
	}
	public void setObservationDate(String observationDate) {
		this.observationDate = new SimpleStringProperty(observationDate);
	}
}
