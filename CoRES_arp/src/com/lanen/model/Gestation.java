package com.lanen.model;

import java.io.Serializable;
import java.util.Date;

public class Gestation implements Serializable {

	/**
	 * 妊娠检查
	 */
	private static final long serialVersionUID = -9171550112944174872L;
    private Long id;              //主键（自增长）
    private String monkeyid;      //猴子编号(母)
    private Date checkdate;       //检查日期
    private Integer ishave;       //是否怀孕
    private Date estimateddate;   //预估产期
    private String remarks;       //备注
    private Long veterinarian;    //兽医（ID）
    private Long protector;       //保定人员（ID）
    private Long recorder;        //记录人员（ID）
    private Long operater;        //档案录入（ID）
    private Integer deleted;      //删除标记
    private Date createtime;     //录入时间
    private Long created_by;     //录入者
    private Long modified_by;    //修改者
    private Date lastmodifytime; //最后修改时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonkeyid() {
		return monkeyid;
	}
	public void setMonkeyid(String monkeyid) {
		this.monkeyid = monkeyid;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public Integer getIshave() {
		return ishave;
	}
	public void setIshave(Integer ishave) {
		this.ishave = ishave;
	}
	public Date getEstimateddate() {
		return estimateddate;
	}
	public void setEstimateddate(Date estimateddate) {
		this.estimateddate = estimateddate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getVeterinarian() {
		return veterinarian;
	}
	public void setVeterinarian(Long veterinarian) {
		this.veterinarian = veterinarian;
	}
	public Long getProtector() {
		return protector;
	}
	public void setProtector(Long protector) {
		this.protector = protector;
	}
	public Long getRecorder() {
		return recorder;
	}
	public void setRecorder(Long recorder) {
		this.recorder = recorder;
	}
	public Long getOperater() {
		return operater;
	}
	public void setOperater(Long operater) {
		this.operater = operater;
	}
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Long createdBy) {
		created_by = createdBy;
	}
	public Long getModified_by() {
		return modified_by;
	}
	public void setModified_by(Long modifiedBy) {
		modified_by = modifiedBy;
	}
	public Date getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
    
}
