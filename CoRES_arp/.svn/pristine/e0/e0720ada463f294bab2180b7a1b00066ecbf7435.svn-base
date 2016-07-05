package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 检疫数据表格 用
 * @author Administrator
 *
 */
public class VaccineTableData {
	private StringProperty animalCode;         //动物编号
    private StringProperty specimenCode;       //检验编号
   
    private StringProperty collectionTime;       //数据采集时间(检验时间)
    
    //疫苗指标
    private StringProperty measles;
    private StringProperty hepatitisA;
    private StringProperty hepatitisB;

    public VaccineTableData(){
    	
    }
    public VaccineTableData(    		
    		String measles,String hepatitisA,String hepatitisB,         
    		      
    		String animalCode,    
    		String specimenCode,  
    		String collectionTime
    		){
    		this.measles=new SimpleStringProperty(measles);
    	    this.hepatitisA         =  new SimpleStringProperty(hepatitisA);
    	    this.hepatitisB=new SimpleStringProperty(hepatitisB);
 
    	    this.animalCode		=  new SimpleStringProperty(animalCode	)  ;
    	    this.specimenCode	=  new SimpleStringProperty(specimenCode)  ;
    	    
    	    this.collectionTime	=  new SimpleStringProperty(collectionTime);
    	    
    	
    }
	public String getAnimalCode() {
		return animalCode.get();
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode =  new SimpleStringProperty(animalCode);
	}
	public String getSpecimenCode() {
		return specimenCode.get();
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode =  new SimpleStringProperty(specimenCode);
	}
	
	public String getCollectionTime() {
		return collectionTime.get();
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = new SimpleStringProperty(collectionTime);
	}
	public String getMeasles() {
		return measles.get();
	}
	public void setMeasles(String measles) {
		this.measles = new SimpleStringProperty(measles);
	}
	public String getHepatitisA() {
		return hepatitisA.get();
	}
	public void setHepatitisA(String hepatitisA) {
		this.hepatitisA =  new SimpleStringProperty(hepatitisA);
	}
	public String getHepatitisB() {
		return hepatitisB.get();
	}
	public void setHepatitisB(String hepatitisB) {
		this.hepatitisB = new SimpleStringProperty(hepatitisB);
	}      
    
}
