package com.lanen.model.clinicaltest;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 检疫数据表格 用
 * @author Administrator
 *
 */
public class XcgData {
	private StringProperty animalCode;         //动物编号
    private StringProperty specimenCode;       //检验编号
   
    private StringProperty collectionTime;       //数据采集时间(检验时间)
    
    //血常规指标
    private StringProperty bhao;
    private StringProperty wbc;
    private StringProperty rbc;
    private StringProperty hgb;
    private StringProperty hct;
    private StringProperty plt;
    private StringProperty mcv;
    private StringProperty mch;
    private StringProperty mchc;
    private StringProperty lym;
    private StringProperty mid;
    private StringProperty gra;
   
   
    public XcgData(){
    	
    }
    public XcgData(    		
    		String bhao,String wbc,String rbc,String hgb,String hct,String plt,String mcv,String mch,String mchc,String lym,String mid,String gra,         
    		      
    		String animalCode,    
    		String specimenCode,  
    		String collectionTime
    		){
    		this.bhao=new SimpleStringProperty(bhao);
    	    this.wbc         =  new SimpleStringProperty(wbc);
    	    this.rbc=new SimpleStringProperty(rbc);
    	    this.hgb=new SimpleStringProperty(hgb);
    	    this.hct=new SimpleStringProperty(hct);
    	    this.plt=new SimpleStringProperty(plt);
    	    this.mcv=new SimpleStringProperty(mcv);
    	    this.mch=new SimpleStringProperty(mch);
    	    this.mchc=new SimpleStringProperty(mchc);
    	    this.lym=new SimpleStringProperty(lym);
    	    this.mid=new SimpleStringProperty(mid);
    	    this.gra=new SimpleStringProperty(gra);
 
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
	public String getBhao() {
		return bhao.get();
	}
	public void setBhao(String bhao) {
		this.bhao = new SimpleStringProperty(bhao);
	}
	public String getWbc() {
		return wbc.get();
	}
	public void setWbc(String wbc) {
		this.wbc =  new SimpleStringProperty(wbc);
	}
	public String getRbc() {
		return rbc.get();
	}
	public void setRbc(String rbc) {
		this.rbc = new SimpleStringProperty(rbc);
	}
	public String getHgb() {
		return hgb.get();
	}
	public void setHgb(String hgb) {
		this.hgb = new SimpleStringProperty(hgb);
	}
	public String getHct() {
		return hct.get();
	}
	public void setHct(String hct) {
		this.hct = new SimpleStringProperty(hct);
	}
	public String getPlt() {
		return plt.get();
	}
	public void setPlt(String plt) {
		this.plt = new SimpleStringProperty(plt);
	}
	public String getMcv() {
		return mcv.get();
	}
	public void setMcv(String mcv) {
		this.mcv = new SimpleStringProperty(mcv);
	}
	public String getMch() {
		return mch.get();
	}
	public void setMch(String mch) {
		this.mch = new SimpleStringProperty(mch);
	}
	public String getMchc() {
		return mchc.get();
	}
	public void setMchc(String mchc) {
		this.mchc = new SimpleStringProperty(mchc);
	}
	public String getLym() {
		return lym.get();
	}
	public void setLym(String lym) {
		this.lym = new SimpleStringProperty(lym);
	}
	public String getMid() {
		return mid.get();
	}
	public void setMid(String mid) {
		this.mid = new SimpleStringProperty(mid);
	}
	public String getGra() {
		return gra.get();
	}
	public void setGra(String gra) {
		this.gra = new SimpleStringProperty(gra);
	}       
    
}
