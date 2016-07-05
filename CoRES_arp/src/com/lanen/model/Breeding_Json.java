package com.lanen.model;

import java.io.Serializable;
import java.util.Date;

public class Breeding_Json implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5070271607567342507L;
	private Long id;              //主键（自增长）
    private String monkeyid;      //猴子编号
    private Date  oestrusdate;    //发情日期
    private Integer oestrustype;  //发情类型27.自然发情28.人工发情
    private Date breedingdate;    //配种日期
    private Date matingdate;      //交配日期
    private String malesmonkeyid; //公猴编号（不止一个逗号隔开）
    private Long veterinarian;    //兽医（ID）
    private String veterinarianName; //兽医（名字）
    private Long protector;       //保定人员（ID）
    private String protectorName; //保定人员（名字）
    private Long recorder;        //记录人员（ID）
    private String recorderName;  //记录人员（名字）
    private Long operater;        //档案录入（ID）
    private String operaterName;  //档案录入（名字）
    private int deleted;          //删除标记
    private Date createtime;     //录入时间
    private Long created_by;     //录入者
    private Long modified_by;    //修改者
    private Date lastmodifytime; //最后修改时间
    private String remark;       //备注
    
    private String oestrustypeName;
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
	public Date getOestrusdate() {
		return oestrusdate;
	}
	public void setOestrusdate(Date oestrusdate) {
		this.oestrusdate = oestrusdate;
	}
	public Integer getOestrustype() {
		return oestrustype;
	}
	public void setOestrustype(Integer oestrustype) {
		this.oestrustype = oestrustype;
	}
	public Date getBreedingdate() {
		return breedingdate;
	}
	public void setBreedingdate(Date breedingdate) {
		this.breedingdate = breedingdate;
	}
	public Date getMatingdate() {
		return matingdate;
	}
	public void setMatingdate(Date matingdate) {
		this.matingdate = matingdate;
	}
	public String getMalesmonkeyid() {
		return malesmonkeyid;
	}
	public void setMalesmonkeyid(String malesmonkeyid) {
		this.malesmonkeyid = malesmonkeyid;
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
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}
	public String getOperaterName() {
		return operaterName;
	}
	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}
	public String getRecorderName() {
		return recorderName;
	}
	public void setProtectorName(String protectorName) {
		this.protectorName = protectorName;
	}
	public String getProtectorName() {
		return protectorName;
	}
	public void setVeterinarianName(String veterinarianName) {
		this.veterinarianName = veterinarianName;
	}
	public String getVeterinarianName() {
		return veterinarianName;
	}
	public String getOestrustypeName() {
		return oestrustypeName;
	}
	public void setOestrustypeName(String oestrustypeName) {
		this.oestrustypeName = oestrustypeName;
	}
    
}
