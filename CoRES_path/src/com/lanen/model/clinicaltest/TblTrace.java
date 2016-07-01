package com.lanen.model.clinicaltest;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据修改痕迹
 * @author Administrator
 *
 */
public class TblTrace  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1438041137593703330L;
	private String id;              //记录Id ，主键
	private String tableName;    //类名，实体名
	private String dataId;        //数据识别Id
	private int operateMode;      //操作方式    1，修改  2，删除 3,添加
	private String oldValue;      //原数据
	private String newValue;      //新数据
	private String operator;      //修改人
	private String modifyReason;  //修改原因
	private Date modifyTime;    //修改时间
	private String host;          //操作计算机
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getModifyReason() {
		return modifyReason;
	}
	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getOperateMode() {
		return operateMode;
	}
	public void setOperateMode(int operateMode) {
		this.operateMode = operateMode;
	}
	
	
}
