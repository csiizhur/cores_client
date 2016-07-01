package com.lanen.model.quarantine;

import java.io.Serializable;

/**
 * 本机接入天平
 * @author 黄国刚
 *
 */
public class TblHostBal implements Serializable{

	private static final long serialVersionUID = -9080970608010770140L;
	
	private String id;          //
	private String hostName;	//主机名
	private String balId;		//天平Id
	private String comPort;     //串口名称
	private int enable;			//是否启用
	
	private TblBal tblBal;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getBalId() {
		return balId;
	}

	public void setBalId(String balId) {
		this.balId = balId;
	}

	public String getComPort() {
		return comPort;
	}

	public void setComPort(String comPort) {
		this.comPort = comPort;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public TblBal getTblBal() {
		return tblBal;
	}

	public void setTblBal(TblBal tblBal) {
		this.tblBal = tblBal;
	}
	

}
