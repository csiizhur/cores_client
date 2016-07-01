package com.lanen.model.studyplan;

import java.io.Serializable;
import java.util.Date;

/**
 * 解剖计划
 * @author Administrator
 *
 */
public class TblDissectPlan   implements Serializable  {
                                         
	/**
	 * 
	 */
	private static final long serialVersionUID = -5576862619778572662L;
	private String id;                     //主键，自增长
	private TblStudyPlan tblStudyPlan;   //试验计划， 类
	private int dissectNum;              //解剖次数
	private Date beginDate;              //开始日期
	private Date endDate;                //结束日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TblStudyPlan getTblStudyPlan() {
		return tblStudyPlan;
	}
	public void setTblStudyPlan(TblStudyPlan tblStudyPlan) {
		this.tblStudyPlan = tblStudyPlan;
	}
	public int getDissectNum() {
		return dissectNum;
	}
	public void setDissectNum(int dissectNum) {
		this.dissectNum = dissectNum;
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
	
}
