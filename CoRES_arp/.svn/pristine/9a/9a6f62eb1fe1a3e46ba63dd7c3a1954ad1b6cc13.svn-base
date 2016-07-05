package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 检疫数据表格 用
 * @author Administrator
 *
 */
public class VirusTableData {
	private StringProperty animalCode;         //动物编号
    private StringProperty specimenCode;       //检验编号
   
    private StringProperty collectionTime;       //数据采集时间(检验时间)
    
    //血常规指标
    private StringProperty xueq;
    private StringProperty bv;
    private StringProperty stlv;
    private StringProperty srv;
    private StringProperty siv;
    private StringProperty filo;

    public VirusTableData(){
    	
    }
    public VirusTableData(    		
    		String xueq,String bv,String stlv,String srv,String siv,String filo,         
    		      
    		String animalCode,    
    		String specimenCode,  
    		String collectionTime
    		){
    		this.xueq=new SimpleStringProperty(xueq);
    	    this.bv         =  new SimpleStringProperty(bv);
    	    this.stlv=new SimpleStringProperty(stlv);
    	    this.srv=new SimpleStringProperty(srv);
    	    this.siv=new SimpleStringProperty(siv);
    	    this.filo=new SimpleStringProperty(filo);
 
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
	public String getXueq() {
		return xueq.get();
	}
	public void setXueq(String xueq) {
		this.xueq = new SimpleStringProperty(xueq);
	}
	public String getBv() {
		return bv.get();
	}
	public void setBv(String bv) {
		this.bv =  new SimpleStringProperty(bv);
	}
	public String getStlv() {
		return stlv.get();
	}
	public void setStlv(String stlv) {
		this.stlv = new SimpleStringProperty(stlv);
	}
	public String getSrv() {
		return srv.get();
	}
	public void setSrv(String srv) {
		this.srv = new SimpleStringProperty(srv);
	}
	public String getSiv() {
		return siv.get();
	}
	public void setSiv(String siv) {
		this.siv = new SimpleStringProperty(siv);
	}
	public String getFilo() {
		return filo.get();
	}
	public void setFilo(String filo) {
		this.filo = new SimpleStringProperty(filo);
	}      
    
}
