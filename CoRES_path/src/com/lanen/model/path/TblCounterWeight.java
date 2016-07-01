package com.lanen.model.path;

/**
 * 砝码
 *
 */
public class TblCounterWeight implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7344596470703290476L;
	
	private String id;                     //id				varchar(20)
	private String cpCode;                 //砝码编号		varchar(20)
	private String cpWeight;               //砝码重量		varchar(20)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public String getCpWeight() {
		return cpWeight;
	}
	public void setCpWeight(String cpWeight) {
		this.cpWeight = cpWeight;
	}
	
	

}
