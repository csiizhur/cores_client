package com.lanen.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Xysh_Json implements Serializable {

	/**
	 * 血液生化
	 */
	private static final long serialVersionUID = -6114716532068556621L;
	
	private Integer id;
	private String bhao;
	private String monkeyid;
	private Timestamp createtime;
	private Integer modified_by;
	private Integer created_by;
	private Timestamp lastmodifytime;
	private Byte deleted;
	private String veterinarian;
	private Integer normal_id;
	private String ptype;
	private Date cdate;
	private String dateid;
	private String ast;
	private String alt;
	private String alp;
	private String tp;
	private String alb;
	private String ggt;
	private String tbil;
	private String bun;
	private String crea;
	private String glu;
	private String tg;
	private String chol;
	private String ldh;
	private String ck;
	private String na;
	private String k;
	private String ci;
	private String pccode;
	private String process;
	
	private String title;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBhao() {
		return bhao;
	}
	public void setBhao(String bhao) {
		this.bhao = bhao;
	}
	public String getMonkeyid() {
		return monkeyid;
	}
	public void setMonkeyid(String monkeyid) {
		this.monkeyid = monkeyid;
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
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getDateid() {
		return dateid;
	}
	public void setDateid(String dateid) {
		this.dateid = dateid;
	}
	public String getAst() {
		return ast;
	}
	public void setAst(String ast) {
		this.ast = ast;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getAlp() {
		return alp;
	}
	public void setAlp(String alp) {
		this.alp = alp;
	}
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public String getAlb() {
		return alb;
	}
	public void setAlb(String alb) {
		this.alb = alb;
	}
	public String getGgt() {
		return ggt;
	}
	public void setGgt(String ggt) {
		this.ggt = ggt;
	}
	public String getTbil() {
		return tbil;
	}
	public void setTbil(String tbil) {
		this.tbil = tbil;
	}
	public String getBun() {
		return bun;
	}
	public void setBun(String bun) {
		this.bun = bun;
	}
	public String getCrea() {
		return crea;
	}
	public void setCrea(String crea) {
		this.crea = crea;
	}
	public String getGlu() {
		return glu;
	}
	public void setGlu(String glu) {
		this.glu = glu;
	}
	public String getTg() {
		return tg;
	}
	public void setTg(String tg) {
		this.tg = tg;
	}
	public String getChol() {
		return chol;
	}
	public void setChol(String chol) {
		this.chol = chol;
	}
	public String getLdh() {
		return ldh;
	}
	public void setLdh(String ldh) {
		this.ldh = ldh;
	}
	public String getCk() {
		return ck;
	}
	public void setCk(String ck) {
		this.ck = ck;
	}
	public String getNa() {
		return na;
	}
	public void setNa(String na) {
		this.na = na;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getPccode() {
		return pccode;
	}
	public void setPccode(String pccode) {
		this.pccode = pccode;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
