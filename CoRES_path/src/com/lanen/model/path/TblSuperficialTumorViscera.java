package com.lanen.model.path;

/**浅表肿瘤脏器登记表
 * @author Administrator
 *
 */
public class TblSuperficialTumorViscera implements java.io.Serializable{

	private static final long serialVersionUID = -8287586950500619980L;

	private String visceraCode;		//脏器编号
	private String visceraName;     //脏器名称
	public String getVisceraCode() {
		return visceraCode;
	}
	public void setVisceraCode(String visceraCode) {
		this.visceraCode = visceraCode;
	}
	public String getVisceraName() {
		return visceraName;
	}
	public void setVisceraName(String visceraName) {
		this.visceraName = visceraName;
	}
	
	
}
