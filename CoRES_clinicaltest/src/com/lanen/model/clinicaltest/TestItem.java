package com.lanen.model.clinicaltest;

import java.io.Serializable;

/**
 * 临时任务  - 检验指标设置    用（表格）
 * @author Administrator
 *
 */
public class TestItem implements Serializable{
	private static final long serialVersionUID = 9086577989550644363L;
	private  int  testItem ;//检测项目
	private String index2;
	private String abbr;
	public int getTestItem() {
		return testItem;
	}
	public void setTestItem(int testItem) {
		this.testItem = testItem;
	}
	public String getIndex2() {
		return index2;
	}
	public void setIndex2(String index2) {
		this.index2 = index2;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
}
