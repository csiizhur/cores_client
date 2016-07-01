package com.lanen.model.quarantine.tblsession;
/**
 * 动物体检检查结果
 * @author 黄国刚
 *
 */
public class TblPhyExamResult implements java.io.Serializable {

	private static final long serialVersionUID = 5206422413339221514L;
		
	private String id;				//	数据ID	ID	varchar(20)	20		
	private String sessionId;		//	会话ID	SessionID	varchar(20)	
	private String animalId;		//	动物ID	AnimalID	varchar(20)	
	private String exanItem;		//	检查项目	ExamItem	varchar(100)
	private String remark ;    		//  检测项目说明（观察方法）varchar (200)
	private String examResult;		//	检查结果	ExamResult	varchar(100)
	private int orderNo;      	 	//排序
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAnimalId() {
		return animalId;
	}

	public void setAnimalId(String animalId) {
		this.animalId = animalId;
	}

	public String getExanItem() {
		return exanItem;
	}

	public void setExanItem(String exanItem) {
		this.exanItem = exanItem;
	}

	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
}
