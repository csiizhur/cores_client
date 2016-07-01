package com.lanen.model.path;


/**切片制作批次动物表
 * @author Administrator
 *
 */
public class TblTissueSliceBatchViscera implements java.io.Serializable {
	private static final long serialVersionUID = 2029853633122972782L;
	
	private String id;        	//
	private String batchId;     //切片制作批次Id
	private int batchSn;      	//批序号
	private String tissueSliceVisceraId;	//组织取材切片脏器ID
	
	
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
	
	
	
}
