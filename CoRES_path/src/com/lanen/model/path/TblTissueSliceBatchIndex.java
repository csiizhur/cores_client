package com.lanen.model.path;

import java.util.Date;

/**切片制作批次索引
 * @author Administrator
 *
 */
public class TblTissueSliceBatchIndex implements java.io.Serializable {

	private static final long serialVersionUID = -2859034401527038460L;
	
	private String id;              //
	private String studyNo;			//专题编号20
	private int batchSn;      //批序号
	private int sliceType;      //切片编号类型1：常规组织	非常规组织2：加做常规组织
	private String operatorSign;    //操作者签字20	(保存签字Id)
	private Date createTime;        //创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getOperatorSign() {
		return operatorSign;
	}
	public void setOperatorSign(String operatorSign) {
		this.operatorSign = operatorSign;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getBatchSn() {
		return batchSn;
	}
	public void setBatchSn(int batchSn) {
		this.batchSn = batchSn;
	}
	public int getSliceType() {
		return sliceType;
	}
	public void setSliceType(int sliceType) {
		this.sliceType = sliceType;
	}
	
}
