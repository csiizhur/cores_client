package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备检定信息登记
 * @author Administrator
 *
 */
public class TblInstrumentVerification  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2913783344854560627L;
	private String id;                           //主键id，自增长
	private DictInstrument dictInstrument;    //设备
	private String instrumentName;            //设备名称
	private String verType;                   //检定类型
	private Date verDate;                     //检定日期
	private Date valiDate;                    //有效期
	private String operator;                  //检定人
	private String operatUnit;                //检定单位

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DictInstrument getDictInstrument() {
		return dictInstrument;
	}
	public void setDictInstrument(DictInstrument dictInstrument) {
		this.dictInstrument = dictInstrument;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getVerType() {
		return verType;
	}
	public void setVerType(String verType) {
		this.verType = verType;
	}
	public Date getVerDate() {
		return verDate;
	}
	public void setVerDate(Date verDate) {
		this.verDate = verDate;
	}
	public Date getValiDate() {
		return valiDate;
	}
	public void setValiDate(Date valiDate) {
		this.valiDate = valiDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatUnit() {
		return operatUnit;
	}
	public void setOperatUnit(String operatUnit) {
		this.operatUnit = operatUnit;
	}
	
	
}
