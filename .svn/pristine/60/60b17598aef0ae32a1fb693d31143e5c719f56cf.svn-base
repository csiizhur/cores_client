package com.lanen.model.studyplan;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 临检申请
 * @author Administrator
 *
 */
public class TblClinicalTestReq   implements Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1152276604228684283L;
	private String id;                     //主键，自增长
	private TblStudyPlan tblStudyPlan;   //试验计划，   类
	private String studyNo;              //课题编号
	private int reqNo;                   //申请编号    1,2,3,4,5....
	private String testPhase;            //试验阶段
	private Date beginDate;              //计划检查开始日期
	private Date endDate;                //计划检查结束日期
	private String testOther;            //其他检查项目
	private String remark;               //备注
	private Date createDate;             //创建日期
	private int  es  ;                   //  0,为签字   1，签字
	private int temp;                    //  0,临时   ，1  ，非临时
	
	private int parentReqNo;             //  0 无父申请， 
	
	//临检申请单-检验指标
	private Set<TblClinicalTestReqIndex> tblClinicalTestReqIndexs = new HashSet<TblClinicalTestReqIndex>();
	//临检申请单-动物编号
	private Set<TblClinicalTestReqIndex2> tblClinicalTestReqIndex2s = new HashSet<TblClinicalTestReqIndex2>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReqNo() {
		return reqNo;
	}
	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
	}
	public String getTestPhase() {
		return testPhase;
	}
	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTestOther() {
		return testOther;
	}
	public void setTestOther(String testOther) {
		this.testOther = testOther;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<TblClinicalTestReqIndex> getTblClinicalTestReqIndexs() {
		return tblClinicalTestReqIndexs;
	}
	public void setTblClinicalTestReqIndexs(
			Set<TblClinicalTestReqIndex> tblClinicalTestReqIndexs) {
		this.tblClinicalTestReqIndexs = tblClinicalTestReqIndexs;
	}
	public Set<TblClinicalTestReqIndex2> getTblClinicalTestReqIndex2s() {
		return tblClinicalTestReqIndex2s;
	}
	public void setTblClinicalTestReqIndex2s(
			Set<TblClinicalTestReqIndex2> tblClinicalTestReqIndex2s) {
		this.tblClinicalTestReqIndex2s = tblClinicalTestReqIndex2s;
	}
	public TblStudyPlan getTblStudyPlan() {
		return tblStudyPlan;
	}
	public void setTblStudyPlan(TblStudyPlan tblStudyPlan) {
		this.tblStudyPlan = tblStudyPlan;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getEs() {
		return es;
	}
	public void setEs(int es) {
		this.es = es;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public int getParentReqNo() {
		return parentReqNo;
	}
	public void setParentReqNo(int parentReqNo) {
		this.parentReqNo = parentReqNo;
	}
	
	
}
