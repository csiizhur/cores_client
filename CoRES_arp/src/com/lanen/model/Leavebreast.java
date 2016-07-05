package com.lanen.model;

import java.io.Serializable;
import java.util.Date;

public class Leavebreast implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9165123125924111258L;
//	private Long id;                //主键（自增长）
//	private String monkeyid;        //幼猴编号
//	private String motherid;        //猴子编号(母)
//	private String leavebreastdate; //离乳日期
//	private Float leavebreastweight;//离乳体重
//	private Long keeper;            //饲养员（ID）
//	private Long recorder;          //记录人员（ID）
//    private Long operater;          //档案录入（ID）
//    private Byte deleted;           //删除标记
//    private Date createtime;        //录入时间
//    private Long created_by;        //录入者
//    private Long modified_by;       //修改者
//    private Date lastmodifytime;    //最后修改时间
//	private String remark;          //备注
	
	private Long id;                //主键（自增长）
	private String monkeyid;        //幼猴编号
	private String motherid;        //猴子编号(母)
	private String leavebreastdate; //离乳日期
	private Float leavebreastweight;//离乳体重
	private Integer keeper;            //饲养员（ID）
	private Integer recorder;          //记录人员（ID）
    private Integer operater;          //档案录入（ID）
    private Integer deleted;           //删除标记
    private Date createtime;        //录入时间
    private Integer created_by;        //录入者
    private Integer modified_by;       //修改者
    private Date lastmodifytime;    //最后修改时间
	private String remark;          //备注
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
	public String getMotherid() {
		return motherid;
	}
	public void setMotherid(String motherid) {
		this.motherid = motherid;
	}
	public String getLeavebreastdate() {
		return leavebreastdate;
	}
	public void setLeavebreastdate(String leavebreastdate) {
		this.leavebreastdate = leavebreastdate;
	}
	public Float getLeavebreastweight() {
		return leavebreastweight;
	}
	public void setLeavebreastweight(Float leavebreastweight) {
		this.leavebreastweight = leavebreastweight;
	}
	public Integer getKeeper() {
		return keeper;
	}
	public void setKeeper(Integer keeper) {
		this.keeper = keeper;
	}
	public Integer getRecorder() {
		return recorder;
	}
	public void setRecorder(Integer recorder) {
		this.recorder = recorder;
	}
	public Integer getOperater() {
		return operater;
	}
	public void setOperater(Integer operater) {
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
	public Integer getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Integer createdBy) {
		created_by = createdBy;
	}
	public Integer getModified_by() {
		return modified_by;
	}
	public void setModified_by(Integer modifiedBy) {
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
	
}
