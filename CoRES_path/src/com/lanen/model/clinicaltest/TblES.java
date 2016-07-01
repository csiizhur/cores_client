package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 电子签名表
 * @author Administrator
 *
 */
public class TblES  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1413822119881156100L;
	private String esId;                                        //电子签名ID
	private String signer;                                      //签字人
	private int esType;                                         //签字类型
	private String esTypeDesc;                                  //签字类型说明
	private Date dateTime;                                      //签字时间
	private String dataSource;                                  //签字目标
	private Set<TblESLink> tblESLinks=new HashSet<TblESLink>(); //签字链接表   s
	public String getEsId() {
		return esId;
	}
	public void setEsId(String esId) {
		this.esId = esId;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
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
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public Set<TblESLink> getTblESLinks() {
		return tblESLinks;
	}
	public void setTblESLinks(Set<TblESLink> tblESLinks) {
		this.tblESLinks = tblESLinks;
	}

}
