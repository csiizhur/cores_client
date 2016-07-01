package com.lanen.model.path;

/**
 * 天平接入信息
 * @author 黄国刚
 *
 */
public class TblBalConnect implements java.io.Serializable{

	private static final long serialVersionUID = 5030387956353622104L;
	
	private String id; 			//						varchar(20)
	private String hostName;	//计算机编号 				varchar(100)
	private String balCode;     //天平编号(TblBalReg)	varchar(60)
	private String commName;    //接入端口				varchar(10)
	private int enabled;        //是否启用       0:否      1:是
	private int type;// 1 计算机 2 芯片阅读器
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
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
	public String getBalCode() {
		return balCode;
	}
	public void setBalCode(String balCode) {
		this.balCode = balCode;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	
}
