package com.lanen.model.path;

/**
 * 砝码允差
 * @author 黄国刚
 *
 */
public class TblCounterpoise implements java.io.Serializable {

	private static final long serialVersionUID = -1432323827458110253L;

	private String id;                     //id				varchar(20)
	private String cpCode;                 //砝码编号		varchar(20)
	private String cpWeight;               //砝码重量		varchar(20)
	private String balPrecision;           //天平精度		varchar(20)
	private String toleranceLower;         //允差下限		varchar(20)
	private String toleranceUpper;         //允差上限		varchar(20)
	
	
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
	public String getBalPrecision() {
		return balPrecision;
	}
	public void setBalPrecision(String balPrecision) {
		this.balPrecision = balPrecision;
	}
	public String getToleranceLower() {
		return toleranceLower;
	}
	public void setToleranceLower(String toleranceLower) {
		this.toleranceLower = toleranceLower;
	}
	public String getToleranceUpper() {
		return toleranceUpper;
	}
	public void setToleranceUpper(String toleranceUpper) {
		this.toleranceUpper = toleranceUpper;
	}
	
	
}
