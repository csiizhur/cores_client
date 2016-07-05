package com.lanen.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Xcg_Json implements Serializable {

	/**
	 * 血常规
	 */
	private static final long serialVersionUID = 2583332237235112539L;
	
	private Integer id;
	private String monkeyid;
	private String recorder;
	private Timestamp createtime;
	private Integer modified_by;
	private Integer created_by;
	private Timestamp lastmodifytime;
	private Byte deleted;
	private String veterinarian;
	private Integer normal_id;
		
	private Date cdate;
	private String ptype;
	private String dateid;
	
	private String wbc;
	private String rbc;
	private String hgb;
	private String hct;
	private String plt;
	private String mcv;
	private String mch;
	private String mchc;
	private String lym;
	private String mid;
	private String gra;
	private String bhao;
	private String process;
	private String pccode;

	private String title;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMonkeyid() {
		return monkeyid;
	}
	public void setMonkeyid(String monkeyid) {
		this.monkeyid = monkeyid;
	}
	
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Integer getModified_by() {
		return modified_by;
	}
	public void setModified_by(Integer modified_by) {
		this.modified_by = modified_by;
	}
	public Integer getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}
	public Timestamp getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(Timestamp lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
	public Byte getDeleted() {
		return deleted;
	}
	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
	
	public String getVeterinarian() {
		return veterinarian;
	}
	public void setVeterinarian(String veterinarian) {
		this.veterinarian = veterinarian;
	}
	public Integer getNormal_id() {
		return normal_id;
	}
	public void setNormal_id(Integer normal_id) {
		this.normal_id = normal_id;
	}
	
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getDateid() {
		return dateid;
	}
	public void setDateid(String dateid) {
		this.dateid = dateid;
	}
	
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getPccode() {
		return pccode;
	}
	public void setPccode(String pccode) {
		this.pccode = pccode;
	}
	public String getWbc() {
		return wbc;
	}
	public void setWbc(String wbc) {
		this.wbc = wbc;
	}
	public String getRbc() {
		return rbc;
	}
	public void setRbc(String rbc) {
		this.rbc = rbc;
	}
	public String getHgb() {
		return hgb;
	}
	public void setHgb(String hgb) {
		this.hgb = hgb;
	}
	public String getHct() {
		return hct;
	}
	public void setHct(String hct) {
		this.hct = hct;
	}
	public String getPlt() {
		return plt;
	}
	public void setPlt(String plt) {
		this.plt = plt;
	}
	public String getMcv() {
		return mcv;
	}
	public void setMcv(String mcv) {
		this.mcv = mcv;
	}
	public String getMch() {
		return mch;
	}
	public void setMch(String mch) {
		this.mch = mch;
	}
	public String getMchc() {
		return mchc;
	}
	public void setMchc(String mchc) {
		this.mchc = mchc;
	}
	public String getLym() {
		return lym;
	}
	public void setLym(String lym) {
		this.lym = lym;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getGra() {
		return gra;
	}
	public void setGra(String gra) {
		this.gra = gra;
	}
	public String getBhao() {
		return bhao;
	}
	public void setBhao(String bhao) {
		this.bhao = bhao;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
