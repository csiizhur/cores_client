package com.lanen.model.path;

import java.io.Serializable;

/**
 * 脏器类别字典(该变程序未维护,仅能在数据库中维护)
 * @author 黄国刚
 *
 */
public class DictVisceraType implements Serializable{
	
	private static final long serialVersionUID = -8339434079456812519L;
	private Integer id;	//
	private String visceraTypeName;//
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVisceraTypeName() {
		return visceraTypeName;
	}
	public void setVisceraTypeName(String visceraTypeName) {
		this.visceraTypeName = visceraTypeName;
	}
	
	
}
