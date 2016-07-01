package com.lanen.model.tableview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblAnimalRecListForTableView {

	private BooleanProperty select;
	private StringProperty id;				//	数据ID	ID	varchar(20)	
	private StringProperty sn;				//	序号	SN	integer			
	private StringProperty recId;			//	接收单号	RecID	varchar(20)	
	private StringProperty animalType;		//	动物种类	AnimalType	varchar(20)	
	private StringProperty animalStrain;	//	动物品系	AnimalStrain	varchar(20)	
	private StringProperty animalId;		//	动物ID号	AnimalID	varchar(30)	
	private StringProperty gender;				//	性别	Gender	integer			
	private StringProperty room;			//	申请单号	QRRID	varchar(20)	
	private StringProperty planStudyNo;		//	分配课题编号	PlanStudyNo	varchar(20)
	private StringProperty state;           //状态   死亡  ，移交，备库
	private StringProperty animalLevel;   //动物级别
	
	public TblAnimalRecListForTableView(){
		setSelect(false);
	}
	public TblAnimalRecListForTableView(
			
			String id,				//	数据ID	ID	varchar(20)	
			int sn,				//	序号	SN	integer			
			String recId,			//	接收单号	RecID	varchar(20)	
			String animalType,		//	动物种类	AnimalType	varchar(20)	
			String animalStrain,	//	动物品系	AnimalStrain	varchar(20)	
			String animalId,		//	动物ID号	AnimalID	varchar(30)	
			int gender,				//	性别	Gender	integer			
			String room,			//	分配房间	varchar(20)	
			String planStudyNo,		//	分配课题编号	PlanStudyNo	varchar(20)
			String state ,        //状态   死亡  ，移交，备库
			String animalLevel
			){
		setSelect(false);
		this.id=new SimpleStringProperty(id);				//	数据ID	ID	varchar(20)	
		this.sn=new SimpleStringProperty(sn+"");				//	序号	SN	integer			
		this.recId=new SimpleStringProperty(recId);			//	接收单号	RecID	varchar(20)	
		this.animalType=new SimpleStringProperty(animalType);		//	动物种类	AnimalType	varchar(20)	
		this.animalStrain=new SimpleStringProperty(animalStrain);	//	动物品系	AnimalStrain	varchar(20)	
		this.animalId=new SimpleStringProperty(animalId);//	动物ID号	AnimalID	varchar(30)	
		if(gender ==1){
			this.gender=new SimpleStringProperty("♂");
		}else if(gender ==2){
			this.gender=new SimpleStringProperty("♀");
		}else{
			this.gender=new SimpleStringProperty("");
		}
		this.room=new SimpleStringProperty(room);			//	分配房间	
		this.planStudyNo=new SimpleStringProperty(planStudyNo);		//	分配课题编号	PlanStudyNo	varchar(20)
		this.state=new SimpleStringProperty(state);  
		setAnimalLevel(animalLevel);
	}
	
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id = new SimpleStringProperty( id);
	}
	public String getSn() {
		return sn.get();
	}
	public void setSn(String sn) {
		this.sn = new SimpleStringProperty( sn);
	}
	public String getRecId() {
		return recId.get();
	}
	public void setRecId(String recId) {
		this.recId = new SimpleStringProperty( recId);
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
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId = new SimpleStringProperty( animalId);
	}
	public String getGender() {
		return gender.get();
	}
	public void setGender(String gender) {
		this.gender = new SimpleStringProperty( gender);
	}
	public String getRoom() {
		return room.get();
	}
	public void setRoom(String room) {
		this.room = new SimpleStringProperty( room);
	}
	public String getPlanStudyNo() {
		return planStudyNo.get();
	}
	public void setPlanStudyNo(String planStudyNo) {
		this.planStudyNo = new SimpleStringProperty( planStudyNo);
	}
	public String getState() {
		return state.get();
	}
	public void setState(String state) {
		this.state = new SimpleStringProperty( state);
	}
	public BooleanProperty selectProperty(){return select;};
	public boolean getSelect() {
		return select.get();
	}
	public void setSelect(boolean select) {
		this.select =  new SimpleBooleanProperty(select);
	}
	public String getAnimalLevel() {
		return animalLevel.get();
	}
	public void setAnimalLevel(String animalLevel) {
		this.animalLevel =new SimpleStringProperty( animalLevel);
	}
	
	
}
