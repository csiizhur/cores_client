package com.lanen.model.studyplan;

import java.io.Serializable;

/**
 * 课题计划检验指标
 * @author Administrator
 *
 */
public class TblTestIndexPlan   implements Serializable    {

	private static final long serialVersionUID = 3870759258342913827L;
	private String id;                     //主键，自增长
     private TblStudyPlan tblStudyPlan;   //试验计划，  类
     private int testItem;                //检验项目标号
     private String testIndex;            //检验指标名称
     private String testIndexAbbr;        //检验指标缩写
     private int precision;               //保留精度
	
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
	public int getTestItem() {
		return testItem;
	}
	public void setTestItem(int testItem) {
		this.testItem = testItem;
	}
	public String getTestIndex() {
		return testIndex;
	}
	public void setTestIndex(String testIndex) {
		this.testIndex = testIndex;
	}
	public String getTestIndexAbbr() {
		return testIndexAbbr;
	}
	public void setTestIndexAbbr(String testIndexAbbr) {
		this.testIndexAbbr = testIndexAbbr;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
     
}
