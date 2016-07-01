package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 字典-设备登记
 * @author Administrator
 *
 */
public class DictInstrument  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5269762019494277742L;
	private String instrumentId;        //设备Id
	private String instrumentName;      //设备名称
	private int  instrumentType;         //类别（检测项目）
	private String manufacturer;        //生产厂家
	private String modelNumber;         //型号
	private Date createDate;            //生产日期
	private String director;            //负责人
	private DictComParam dictComParam;   //设备接口参数,类
	private Set<TblInstrumentVerification> tblInstrumentVerifications=new HashSet<TblInstrumentVerification>();
	private Set<TblPassageway> passageways=new HashSet<TblPassageway>();    //通道设置
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
	public Set<TblInstrumentVerification> getTblInstrumentVerifications() {
		return tblInstrumentVerifications;
	}
	public void setTblInstrumentVerifications(
			Set<TblInstrumentVerification> tblInstrumentVerifications) {
		this.tblInstrumentVerifications = tblInstrumentVerifications;
	}
	public int getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(int instrumentType) {
		this.instrumentType = instrumentType;
	}
	public DictComParam getDictComParam() {
		return dictComParam;
	}
	public void setDictComParam(DictComParam dictComParam) {
		this.dictComParam = dictComParam;
	}
	public Set<TblPassageway> getPassageways() {
		return passageways;
	}
	public void setPassageways(Set<TblPassageway> passageways) {
		this.passageways = passageways;
	}
	

}
