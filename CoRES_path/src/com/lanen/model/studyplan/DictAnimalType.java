package com.lanen.model.studyplan;

import java.io.Serializable;

/**
 * 字典—动物种类
 * @author Administrator
 *
 */

public class DictAnimalType   implements Serializable  {
	
	private static final long serialVersionUID = 4963492481630309186L;
	private String id;        //ID
	private String typeName;  //名称
	private String abbr;      //缩写
	private String orderNo;   //排序
	private int  isBigAnimal;  //1:是     2：否
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public int getIsBigAnimal() {
		return isBigAnimal;
	}
	public void setIsBigAnimal(int isBigAnimal) {
		this.isBigAnimal = isBigAnimal;
	}
	
	
	

}
