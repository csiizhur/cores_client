package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;

/**
 * 签名链接表
 * @author Administrator
 *
 */
public class TblESLink  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2753906715804183734L;
	private String linkId;        //链接ID
	private String tableName;    //类名，实体名
	private String dataId;        //数据识别ID
	private int esType;           //签名类型
	private String esTypeDesc;    //签名类型说明
	private Date recordTime;      //记录时间
	private TblES tblES;          //类，电子签名表
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public int getEsType() {
		return esType;
	}
	public void setEsType(int esType) {
		this.esType = esType;
	}
	public String getEsTypeDesc() {
		return esTypeDesc;
	}
	public void setEsTypeDesc(String esTypeDesc) {
		this.esTypeDesc = esTypeDesc;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public TblES getTblES() {
		return tblES;
	}
	public void setTblES(TblES tblES) {
		this.tblES = tblES;
	}
	
	

}
