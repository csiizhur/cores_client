package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 接收登记
 * @author Administrator
 *
 */
public class TblAnimalRecIndexForTableView {
	 private StringProperty recId;              //	接收单号	RecID	varchar(20)	
	 private StringProperty recDate;              //	接收日期	RecDate	datetime		
	 private StringProperty animalType;         //	动物种类	AnimalType	varchar(20)	
	 private StringProperty animalStrain;       //	动物品系	AnimalStrain	varchar(20)	
	 private StringProperty animalLevel;        //	动物级别	AnimalLevel	varchar(20)	
	 private StringProperty producer;           //	实验动物生产商	Producer	varchar(200)	
	 private StringProperty certificate;        //	动物合格证号	Certificate	varchar(50)	
	 private StringProperty room;               //	安置房间号	Room	varchar(50)	
	 private StringProperty numMale;               //	数量雄	NumMale	integer			
	 private StringProperty numFemale;             //	数量雌	NumFemale	integer			
	 private StringProperty ageMaleUL;       //	年龄雄范围
	 private StringProperty ageFemaleUL;     //	年龄雌范围	
	 private StringProperty receiver;           //	接收人	Receiver	varchar(20)	
	 private StringProperty checker;            //	复核者	Checker	varchar(20)	
	 
	 public TblAnimalRecIndexForTableView(
			 String recId,              //	接收单号	RecID	varchar(20)	
			 Date recDate,              //	接收日期	RecDate	datetime		
			 String animalType,         //	动物种类	AnimalType	varchar(20)	
			 String animalStrain,       //	动物品系	AnimalStrain	varchar(20)	
			 String animalLevel,        //	动物级别	AnimalLevel	varchar(20)	
			 String producer,           //	实验动物生产商	Producer	varchar(200)	
			 String certificate,        //	动物合格证号	Certificate	varchar(50)	
			 String room,               //	安置房间号	Room	varchar(50)	
			 int numMale,               //	数量雄	NumMale	integer			
			 int numFemale,             //	数量雌	NumFemale	integer			
			 String ageMaleUL,       //	年龄雄范围
			 String ageFemaleUL,     //	年龄雌范围	
			 String receiver,           //	接收人	Receiver	varchar(20)	
			 String checker 
			 ){
		   this.recId=new SimpleStringProperty(recId);        
		   if (null != recDate) {
				this.recDate = new SimpleStringProperty(DateUtil.dateToString(recDate,
						"yyyy-MM-dd")); //接收时间
			} else {
				this.recDate = new SimpleStringProperty("");
			}
		   this.animalType=new SimpleStringProperty(animalType);   
		   this.animalStrain=new SimpleStringProperty(animalStrain); 
		   this.animalLevel=new SimpleStringProperty(animalLevel);  
		   this.producer=new SimpleStringProperty(producer);     
		   this.certificate=new SimpleStringProperty(certificate);  
		   this.room=new SimpleStringProperty(room);         
		   this.numMale=new SimpleStringProperty(numMale+"");      
		   this.numFemale=new SimpleStringProperty(numFemale+"");    
		   this.ageMaleUL=new SimpleStringProperty(ageMaleUL);    
		   this.ageFemaleUL=new SimpleStringProperty(ageFemaleUL);  
		   this.receiver=new SimpleStringProperty(receiver);     
		   this.checker=new SimpleStringProperty(checker);      
	 }
	 
	 
	public String getRecId() {
		return recId.get();
	}
	public void setRecId(String recId) {
		this.recId = new SimpleStringProperty( recId);
	}
	public String getRecDate() {
		return recDate.get();
	}
	public void setRecDate(String recDate) {
		this.recDate = new SimpleStringProperty( recDate);
	}
	public String getAnimalType() {
		return animalType.get();
	}
	public void setAnimalType(String animalType) {
		this.animalType = new SimpleStringProperty( animalType);
	}
	public String getAnimalStrain() {
		return animalStrain.get();
	}
	public void setAnimalStrain(String animalStrain) {
		this.animalStrain = new SimpleStringProperty( animalStrain);
	}
	public String getAnimalLevel() {
		return animalLevel.get();
	}
	public void setAnimalLevel(String animalLevel) {
		this.animalLevel = new SimpleStringProperty( animalLevel);
	}
	public String getProducer() {
		return producer.get();
	}
	public void setProducer(String producer) {
		this.producer = new SimpleStringProperty( producer);
	}
	public String getCertificate() {
		return certificate.get();
	}
	public void setCertificate(String certificate) {
		this.certificate = new SimpleStringProperty( certificate);
	}
	public String getRoom() {
		return room.get();
	}
	public void setRoom(String room) {
		this.room = new SimpleStringProperty( room);
	}
	public String getNumMale() {
		return numMale.get();
	}
	public void setNumMale(String numMale) {
		this.numMale = new SimpleStringProperty( numMale);
	}
	public String getNumFemale() {
		return numFemale.get();
	}
	public void setNumFemale(String numFemale) {
		this.numFemale = new SimpleStringProperty( numFemale);
	}
	public String getAgeMaleUL() {
		return ageMaleUL.get();
	}
	public void setAgeMaleUL(String ageMaleUL) {
		this.ageMaleUL = new SimpleStringProperty( ageMaleUL);
	}
	public String getAgeFemaleUL() {
		return ageFemaleUL.get();
	}
	public void setAgeFemaleUL(String ageFemaleUL) {
		this.ageFemaleUL = new SimpleStringProperty( ageFemaleUL);
	}
	public String getReceiver() {
		return receiver.get();
	}
	public void setReceiver(String receiver) {
		this.receiver = new SimpleStringProperty( receiver);
	}
	public String getChecker() {
		return checker.get();
	}
	public void setChecker(String checker) {
		this.checker = new SimpleStringProperty( checker);
	}
	 
}
