package com.lanen.model.path;


/**切片制作批次排除表
 * @author Administrator
 *
 */
public class TblTissueSliceBatchExcluded implements java.io.Serializable {
	private static final long serialVersionUID = 2029853633122972782L;
	
	private String id;        	//
	private String batchId;     //切片制作批次Id
	private int batchSn;      	//批序号
	private String studyNo;		//专题编号
	private String animalCode;	//动物编号
	private String tissueSliceVisceraId;	//组织取材切片脏器ID
	private String reason;			//原因 40
	
	private int visceraType;           		//脏器类型
	private String visceraCode;       		//脏器编号20
	private String visceraName;        		//脏器名称60
	private String subVisceraCode;    		//子 脏器编号20
	private String subVisceraName;     		//子 脏器名称60
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public int getBatchSn() {
		return batchSn;
	}
	public void setBatchSn(int batchSn) {
		this.batchSn = batchSn;
	}
	public String getTissueSliceVisceraId() {
		return tissueSliceVisceraId;
	}
	public void setTissueSliceVisceraId(String tissueSliceVisceraId) {
		this.tissueSliceVisceraId = tissueSliceVisceraId;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getVisceraType() {
		return visceraType;
	}
	public void setVisceraType(int visceraType) {
		this.visceraType = visceraType;
	}
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
	public String getSubVisceraCode() {
		return subVisceraCode;
	}
	public void setSubVisceraCode(String subVisceraCode) {
		this.subVisceraCode = subVisceraCode;
	}
	public String getSubVisceraName() {
		return subVisceraName;
	}
	public void setSubVisceraName(String subVisceraName) {
		this.subVisceraName = subVisceraName;
	}
	
}
