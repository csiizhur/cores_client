package com.lanen.model.clinicaltest;

import java.io.Serializable;

/**
 * 字典--标本种类
 * @author Administrator
 *
 */
public class DictSpecimen  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2189457814569875319L;
	
	private String  id;
	private String  specKind;//标本类型
	private int specType;//标本种类   1:血液  2：尿液 3：其他

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecKind() {
		return specKind;
	}
	public void setSpecKind(String specKind) {
		this.specKind = specKind;
	}
	public int getSpecType() {
		return specType;
	}
	public void setSpecType(int specType) {
		this.specType = specType;
	}
	
	

}
