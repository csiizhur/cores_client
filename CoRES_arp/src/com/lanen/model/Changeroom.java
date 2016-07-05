package com.lanen.model;

import java.io.Serializable;
import java.util.Date;

public class Changeroom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3507239540336343786L;
    private Long id;       //调栏记录表主键ID
    private String monkeyid; //动物编号
    private Date changeroomdate; //调栏日期
    private String changeinarea; //调入区域
    private String changeinroom; //调入房间
    private String protector;    //保定人员
    private String recorder;     //记录人员
    private String operater;     //档案录入
    private Date createtime;     //录入时间
    private Long modified_by;    //修改者
    private Long created_by;     //录入者
    private Date lastmodifytime; //最后修改时间
    private int deleted;         //删除标志
    private String remark;       //备注
    private String lhao;         //笼号
    private Long yarea;          //原区域
    private Long yroom;          //原房间
    private Long ykeeper;        //原饲养员
    private String ylh;          //原笼号
    private String pccode;       //电脑编号
    private String process;      //进程
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
	public Date getChangeroomdate() {
		return changeroomdate;
	}
	public void setChangeroomdate(Date changeroomdate) {
		this.changeroomdate = changeroomdate;
	}
	public String getChangeinarea() {
		return changeinarea;
	}
	public void setChangeinarea(String changeinarea) {
		this.changeinarea = changeinarea;
	}
	public String getChangeinroom() {
		return changeinroom;
	}
	public void setChangeinroom(String changeinroom) {
		this.changeinroom = changeinroom;
	}
	public String getProtector() {
		return protector;
	}
	public void setProtector(String protector) {
		this.protector = protector;
	}
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getModified_by() {
		return modified_by;
	}
	public void setModified_by(Long modifiedBy) {
		modified_by = modifiedBy;
	}
	public Long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Long createdBy) {
		created_by = createdBy;
	}
	public Date getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLhao() {
		return lhao;
	}
	public void setLhao(String lhao) {
		this.lhao = lhao;
	}
	public Long getYarea() {
		return yarea;
	}
	public void setYarea(Long yarea) {
		this.yarea = yarea;
	}
	public Long getYroom() {
		return yroom;
	}
	public void setYroom(Long yroom) {
		this.yroom = yroom;
	}
	public Long getYkeeper() {
		return ykeeper;
	}
	public void setYkeeper(Long ykeeper) {
		this.ykeeper = ykeeper;
	}
	public String getYlh() {
		return ylh;
	}
	public void setYlh(String ylh) {
		this.ylh = ylh;
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
    
}
