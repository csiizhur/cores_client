package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 临检数据表格 用
 * @author Administrator
 *
 */
public class ClinicalTestData {
	private StringProperty dataId;             //数据Id
	private StringProperty tblSpecimenId;  //标本接收登记Id
	private StringProperty studyNo;            //课题编号
	private StringProperty reqNo;              //申请编号
	private StringProperty animalId;           //动物Id
	private StringProperty animalCode;         //动物编号
    private StringProperty specimenCode;       //检验编号号
    private StringProperty testItem;              //检验项目
    private StringProperty testIndex;          //检验指标
    private StringProperty testIndexAbbr;      //检验指标缩写
    private StringProperty testData;           //数据
    private StringProperty testIndexUnit;      //检验指标单位
    private StringProperty collectionMode;        //数据采集方式
    private StringProperty collectionTime;       //数据采集时间(检验时间)
    private StringProperty acceptTime;        //接收时间
    private StringProperty es;               //签字    0：未签字   1：已签字
    private StringProperty confirmFlag;      //1.第一次接收   2，第二次接收
    private StringProperty validFlag;       //是否有效
    
    public ClinicalTestData(){
    	
    }
    public ClinicalTestData(    		
    		String dataId,        
    		String tblSpecimenId, 
    		String studyNo,       
    		String reqNo,         
    		String animalId,      
    		String animalCode,    
    		String specimenCode,  
    		String testItem,      
    		String testIndex,     
    		String testIndexAbbr, 
    		String testData,      
    		String testIndexUnit, 
    		String collectionMode,
    		String collectionTime,
    		String acceptTime,    
    		String es,            
    		String confirmFlag ,
    		String validFlag){
    	    this.dataId         =  new SimpleStringProperty(dataId        );
    	    this.tblSpecimenId  =  new SimpleStringProperty(tblSpecimenId );
    	    this.studyNo        =  new SimpleStringProperty(studyNo       );
    	    this.reqNo			=  new SimpleStringProperty(reqNo		)  ;
    	    this.animalId		=  new SimpleStringProperty(animalId	)  ;
    	    this.animalCode		=  new SimpleStringProperty(animalCode	)  ;
    	    this.specimenCode	=  new SimpleStringProperty(specimenCode)  ;
    	    this.testItem		=  new SimpleStringProperty(testItem	)  ;
    	    this.testIndex		=  new SimpleStringProperty(testIndex	)  ;
    	    this.testIndexAbbr	=  new SimpleStringProperty(testIndexAbbr) ;
    	    this.testData		=  new SimpleStringProperty(testData	)  ;
    	    this.testIndexUnit	=  new SimpleStringProperty(testIndexUnit) ;
    	    this.collectionMode	=  new SimpleStringProperty(collectionMode);
    	    this.collectionTime	=  new SimpleStringProperty(collectionTime);
    	    this.acceptTime		=  new SimpleStringProperty(acceptTime	)  ;
    	    this.es				=  new SimpleStringProperty(es			)  ;
    	    this.confirmFlag	=  new SimpleStringProperty(confirmFlag	  );
    	    this.validFlag	=  new SimpleStringProperty(validFlag	  );
    	
    	
    	
    }
    
    
	public String getDataId() {
		return dataId.get();
	}
	public void setDataId(String dataId) {
		this.dataId =  new SimpleStringProperty(dataId);
	}
	public String getTblSpecimenId() {
		return tblSpecimenId.get();
	}
	public void setTblSpecimenId(String tblSpecimenId) {
		this.tblSpecimenId =  new SimpleStringProperty(tblSpecimenId);
	}
	public String getStudyNo() {
		return studyNo.get();
	}
	public void setStudyNo(String studyNo) {
		this.studyNo =  new SimpleStringProperty(studyNo);
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
	public String getSpecimenCode() {
		return specimenCode.get();
	}
	public void setSpecimenCode(String specimenCode) {
		this.specimenCode =  new SimpleStringProperty(specimenCode);
	}
	public String getTestItem() {
		return testItem.get();
	}
	public void setTestItem(String testItem) {
		this.testItem =  new SimpleStringProperty(testItem);
	}
	public String getTestIndex() {
		return testIndex.get();
	}
	public void setTestIndex(String testIndex) {
		this.testIndex =  new SimpleStringProperty(testIndex);
	}
	public String getTestIndexAbbr() {
		return testIndexAbbr.get();
	}
	public void setTestIndexAbbr(String testIndexAbbr) {
		this.testIndexAbbr =  new SimpleStringProperty(testIndexAbbr);
	}
	public String getTestData() {
		return testData.get();
	}
	public void setTestData(String testData) {
		this.testData =  new SimpleStringProperty(testData);
	}
	public String getTestIndexUnit() {
		return testIndexUnit.get();
	}
	public void setTestIndexUnit(String testIndexUnit) {
		this.testIndexUnit =  new SimpleStringProperty(testIndexUnit);
	}
	public String getCollectionMode() {
		return collectionMode.get();
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = new SimpleStringProperty(collectionMode);
	}
	public String getCollectionTime() {
		return collectionTime.get();
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = new SimpleStringProperty(collectionTime);
	}
	public String getAcceptTime() {
		return acceptTime.get();
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = new SimpleStringProperty(acceptTime);
	}
	public String getEs() {
		return es.get();
	}
	public void setEs(String es) {
		this.es =  new SimpleStringProperty(es);
	}
	public String getConfirmFlag() {
		return confirmFlag.get();
	}
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag =  new SimpleStringProperty(confirmFlag);
	}
	public String getValidFlag() {
		return validFlag.get();
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = new SimpleStringProperty(validFlag);
	}       
    
    
}
