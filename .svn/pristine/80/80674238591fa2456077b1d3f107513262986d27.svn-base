package com.lanen.model.tableview;

import java.util.Date;

import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TblTraceForTableView {
	
	private StringProperty animalId;      //动物Id号
	private StringProperty oldValue;      //原数据
	private StringProperty newValue;      //新数据
	private StringProperty operator;      //修改人
	private StringProperty modifyReason;  //修改原因
	private StringProperty modifyTime;    //修改时间
	private StringProperty operateMode;   //操作类型
	
	public TblTraceForTableView(
			String animalId,      
			String oldValue,      
			String newValue,      
			String operator,      
			String modifyReason,  
			Date modifyTime ,
			int operateMode
			){
		setAnimalId(animalId);
		setOldValue(oldValue);
		setNewValue(newValue);
		setOperator(operator);
		setModifyReason(modifyReason);
		setModifyTime(modifyTime);
		setOperateMode(operateMode);
	}
	
	
	public String getAnimalId() {
		return animalId.get();
	}
	public void setAnimalId(String animalId) {
		this.animalId = new SimpleStringProperty(animalId);
	}
	public String getOldValue() {
		return oldValue.get();
	}
	public void setOldValue(String oldValue) {
		this.oldValue = new SimpleStringProperty(oldValue);
	}
	public String getNewValue() {
		return newValue.get();
	}
	public void setNewValue(String newValue) {
		this.newValue = new SimpleStringProperty(newValue);
	}
	public String getOperator() {
		return operator.get();
	}
	public void setOperator(String operator) {
		this.operator = new SimpleStringProperty(operator);
	}
	public String getModifyReason() {
		return modifyReason.get();
	}
	public void setModifyReason(String modifyReason) {
		this.modifyReason = new SimpleStringProperty(modifyReason);
	}
	public String getModifyTime() {
		return modifyTime.get();
	}
	public void setModifyTime(Date modifyTime) {
		String modifyTimeStr ="";
		if(null !=modifyTime){
			modifyTimeStr = DateUtil.dateToString(modifyTime, "yyyy-MM-dd HH:mm:ss");
		}
		this.modifyTime = new SimpleStringProperty(modifyTimeStr);
	}


	public String getOperateMode() {
		return operateMode.get();
	}


	public void setOperateMode(int operateMode) {
//		this.operateMode = operateMode;
		switch (operateMode) {
		case 1:
			this.operateMode = new SimpleStringProperty("修改");
			break;
		case 2:
			this.operateMode = new SimpleStringProperty("删除");
			break;
		case 3:
			this.operateMode = new SimpleStringProperty("添加");
			break;

		default: this.operateMode = new SimpleStringProperty("");
			break;
		}
	}

}
