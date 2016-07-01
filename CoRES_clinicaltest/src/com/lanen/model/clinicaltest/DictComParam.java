package com.lanen.model.clinicaltest;

import java.io.Serializable;

/**
 * 设备接口参数（串口）
 * @author Administrator
 *
 */
public class DictComParam  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7317423362759411004L;
	private String instrumentId;      //设备Id
	private String instrumentName;     //设备名称
	private String comPort;           //接入串口名称
	private String baudRate;             //波特率   
	private String dataBit;              //数据位   5 6 7 8
	private String stopBit;              //停止位    1,1.5,2
	private String checkMode;            //校验位    None，Even，Odd，Space，Mark
	private DictInstrument dictInstrument;//设备，类
	
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	public String getComPort() {
		return comPort;
	}
	public void setComPort(String comPort) {
		this.comPort = comPort;
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
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	public String getDataBit() {
		return dataBit;
	}
	public void setDataBit(String dataBit) {
		this.dataBit = dataBit;
	}
	public String getStopBit() {
		return stopBit;
	}
	public void setStopBit(String stopBit) {
		this.stopBit = stopBit;
	}
	public String getCheckMode() {
		return checkMode;
	}
	public void setCheckMode(String checkMode) {
		this.checkMode = checkMode;
	}
	
	
}
