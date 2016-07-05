package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 观察记录表格 用
 * @author Administrator
 *
 */
public class WeightTableData {
	private StringProperty animalCode;         //动物编号
    private StringProperty quyuName;       //区域
   
    private StringProperty animalSex;       //性别
    
    private StringProperty weight;
    private StringProperty weightDate;
    private StringProperty bossName;
    public WeightTableData(){
    	
    }
    public WeightTableData(    		
    		String weight,String weightDate,String bossName,        
    		      
    		String animalCode,    
    		String quyuName,  
    		String animalSex
    		){
    		this.weight=new SimpleStringProperty(weight);
    	    this.bossName         =  new SimpleStringProperty(bossName);
    	    this.weightDate=new SimpleStringProperty(weightDate);
    	    this.animalCode		=  new SimpleStringProperty(animalCode	)  ;
    	    this.quyuName	=  new SimpleStringProperty(quyuName)  ;
    	    
    	    this.animalSex	=  new SimpleStringProperty(animalSex);
    	    
    	
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
	public String getWeight() {
		return weight.get();
	}
	public void setWeight(Float weight) {
		this.weight = new SimpleStringProperty(weight.toString());
	}
	public String getBossName() {
		return bossName.get();
	}
	public void setBossName(String bossName) {
		this.bossName =  new SimpleStringProperty(bossName);
	}
	public String getWeightDate() {
		return weightDate.get();
	}
	public void setWeightDate(String weightDate) {
		this.weightDate = new SimpleStringProperty(weightDate);
	}      
	
}
