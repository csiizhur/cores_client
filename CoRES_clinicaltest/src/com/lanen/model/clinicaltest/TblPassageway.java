package com.lanen.model.clinicaltest;

import java.io.Serializable;

/**
 * 通道号
 * @author Administrator
 *
 */
public class TblPassageway implements Serializable{

	private static final long serialVersionUID = -4988083604601058743L;
	private String  id;                    //主键
	private int testItem;                //检测项目
	private String passageway;           //通道号
	private String testIndex;                //检测指标
	private DictInstrument diactInstrument;//类，设备
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTestItem() {
		return testItem;
	}
	public void setTestItem(int testItem) {
		this.testItem = testItem;
	}
	public String getPassageway() {
		return passageway;
	}
	public void setPassageway(String passageway) {
		this.passageway = passageway;
	}
	
	public DictInstrument getDiactInstrument() {
		return diactInstrument;
	}
	public void setDiactInstrument(DictInstrument diactInstrument) {
		this.diactInstrument = diactInstrument;
	}
	public String getTestIndex() {
		return testIndex;
	}
	public void setTestIndex(String testIndex) {
		this.testIndex = testIndex;
	}

	
	
}
