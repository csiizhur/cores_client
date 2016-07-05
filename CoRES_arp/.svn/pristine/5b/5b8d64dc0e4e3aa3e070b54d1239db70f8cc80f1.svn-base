package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 调栏记录表格 用
 * @author Administrator
 *
 */
public class ChangeroomTableData {
	private StringProperty animalCode;         //动物编号
   
    private StringProperty animalSex;       //性别
    
    private StringProperty yAreaName;
    private StringProperty yKeeperName;
    private StringProperty changeroomDate;
    private StringProperty xAreaName;
    private StringProperty xKeeperName;
    public ChangeroomTableData(){
    	
    }
    public ChangeroomTableData(    		
    		String yAreaName,String yKeeperName,String xAreaName,        
    		String xKeeperName,String changeroomDate,      
    		String animalCode,    
    		String animalSex
    		){
    	this.xAreaName=new SimpleStringProperty(xAreaName);
    	this.xKeeperName=new SimpleStringProperty(xKeeperName);
    	    this.yKeeperName=new SimpleStringProperty(yKeeperName);
    	    this.yAreaName=new SimpleStringProperty(yAreaName);
    	    this.changeroomDate=new SimpleStringProperty(changeroomDate);
    	    this.animalCode		=  new SimpleStringProperty(animalCode	); 
    	    this.animalSex	=  new SimpleStringProperty(animalSex);
    	    
    	
    }
	public String getAnimalCode() {
		return animalCode.get();
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode =  new SimpleStringProperty(animalCode);
	}
	public String getAnimalSex() {
		return animalSex.get();
	}
	public void setAnimalSex(String animalSex) {
		this.animalSex = new SimpleStringProperty(animalSex);
	}
	public String getYAreaName(){
		return yAreaName.get();
	}
	public void setYAreaName(String yAreaName){
		this.yAreaName=new SimpleStringProperty(yAreaName);
	}
	public String getYKeeperName(){
		return yKeeperName.get();
	}
	public void setYKeeperName(String yKeeperName){
		this.yKeeperName=new SimpleStringProperty(yKeeperName);
	}
	public String getXAreaName(){
		return xAreaName.get();
	}
	public void setXAreaName(String xAreaName){
		this.xAreaName=new SimpleStringProperty(xAreaName);
	}
	public String getXKeeperName(){
		return xKeeperName.get();
	}
	public void setXKeeperName(String xKeeperName){
		this.xKeeperName=new SimpleStringProperty(xKeeperName);
	}
	public String getChangeroomDate(){
		return changeroomDate.get();
	}
	public void setChangeroomDate(String changeroomDate){
		this.changeroomDate=new SimpleStringProperty(changeroomDate);
	}
	
}

