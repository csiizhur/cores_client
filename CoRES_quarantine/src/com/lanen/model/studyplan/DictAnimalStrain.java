package com.lanen.model.studyplan;

import java.io.Serializable;

/**
 * 字典—动物品系
 * @author Administrator
 *
 */

public class DictAnimalStrain   implements Serializable  {
	
	private static final long serialVersionUID = 99048016514948149L;
	private String id;                //名称
	private String animalTypeId;      //动物主键Id
	private String strainName;        //名称
	private String orderNo;           //排序
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnimalTypeId() {
		return animalTypeId;
	}
	public void setAnimalTypeId(String animalTypeId) {
		this.animalTypeId = animalTypeId;
	}
	public String getStrainName() {
		return strainName;
	}
	public void setStrainName(String strainName) {
		this.strainName = strainName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

}
