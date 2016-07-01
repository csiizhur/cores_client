package com.lanen.model.path;

import java.util.Date;

/**
 * 剖检记录修改表（解剖任务签字后的修改）
 * @author 黄国刚
 *
 */
public class TblAnatomyCheckEdit implements java.io.Serializable {

	private static final long serialVersionUID = 4098539045477386384L;
	
	private String id;                 // 20
	private String taskId;          	//任务id 20
	private String studyNo;            //课题编号20
	private String animalCode;         //动物编号20
	private int visceraType;           //脏器类型
	private String visceraCode;        //脏器编号20
	private String visceraName;        //脏器名称60
	private String subVisceraCode;     //子 脏器编号20
	private String subVisceraName;     //子 脏器名称60
	private int autolyzaFlag;          //自溶标识       0:否     1:是
	private int specialFlag;           //所见特殊标志
	private String anatomyPosCode;     //解剖学所见部位编号20
	private String anatomyPos;         //解剖学所见部位60
	private int anatomyFindingFlag;	   //通用/特殊所见标识    0:通用   1:特殊
	private String anatomyFindingCode; //解剖所见编号20
	private String anatomyFingding;    //解剖所见100
	private String bodySurfacePos;     //体表部位60
	private String pos;                //位置60
	private String shape;              //形状60
	private String color;              //颜色60
	private String texture;            //硬度60
	private String number;             //数量60
	private String range;              //范围60
	private String lesionDegree;       //病变程度60
	private String size;				//大小 20
	private String operator;           //操作者20
	private Date operateTime;          //操作时间
	
	private String autolyzeSign;           //自溶签字20
	
	
	private int editType;				//修改类型（增删改）  1：添加    2：编辑   3：删除
	private Date editTime;				//修改时间
	private String editRsn;				//修改原因
	private String oldId;				//原数据Id
	
	private int delFlag ;				//编辑后数据（增加或编辑的数据）再删除  0:否   1：删除
	private Date delTime;				//删除时间
	private String delRsn;				//删除原因
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public int getVisceraType() {
		return visceraType;
	}
	public void setVisceraType(int visceraType) {
		this.visceraType = visceraType;
	}
	public String getVisceraCode() {
		return visceraCode;
	}
	public void setVisceraCode(String visceraCode) {
		this.visceraCode = visceraCode;
	}
	public String getVisceraName() {
		return visceraName;
	}
	public void setVisceraName(String visceraName) {
		this.visceraName = visceraName;
	}
	public String getSubVisceraCode() {
		return subVisceraCode;
	}
	public void setSubVisceraCode(String subVisceraCode) {
		this.subVisceraCode = subVisceraCode;
	}
	public String getSubVisceraName() {
		return subVisceraName;
	}
	public void setSubVisceraName(String subVisceraName) {
		this.subVisceraName = subVisceraName;
	}
	public int getAutolyzaFlag() {
		return autolyzaFlag;
	}
	public void setAutolyzaFlag(int autolyzaFlag) {
		this.autolyzaFlag = autolyzaFlag;
	}
	public int getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(int specialFlag) {
		this.specialFlag = specialFlag;
	}
	public String getAnatomyPosCode() {
		return anatomyPosCode;
	}
	public void setAnatomyPosCode(String anatomyPosCode) {
		this.anatomyPosCode = anatomyPosCode;
	}
	public String getAnatomyPos() {
		return anatomyPos;
	}
	public void setAnatomyPos(String anatomyPos) {
		this.anatomyPos = anatomyPos;
	}
	public int getAnatomyFindingFlag() {
		return anatomyFindingFlag;
	}
	public void setAnatomyFindingFlag(int anatomyFindingFlag) {
		this.anatomyFindingFlag = anatomyFindingFlag;
	}
	public String getAnatomyFindingCode() {
		return anatomyFindingCode;
	}
	public void setAnatomyFindingCode(String anatomyFindingCode) {
		this.anatomyFindingCode = anatomyFindingCode;
	}
	public String getAnatomyFingding() {
		return anatomyFingding;
	}
	public void setAnatomyFingding(String anatomyFingding) {
		this.anatomyFingding = anatomyFingding;
	}
	public String getBodySurfacePos() {
		return bodySurfacePos;
	}
	public void setBodySurfacePos(String bodySurfacePos) {
		this.bodySurfacePos = bodySurfacePos;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTexture() {
		return texture;
	}
	public void setTexture(String texture) {
		this.texture = texture;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getLesionDegree() {
		return lesionDegree;
	}
	public void setLesionDegree(String lesionDegree) {
		this.lesionDegree = lesionDegree;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAutolyzeSign() {
		return autolyzeSign;
	}
	public void setAutolyzeSign(String autolyzeSign) {
		this.autolyzeSign = autolyzeSign;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public int getEditType() {
		return editType;
	}
	public void setEditType(int editType) {
		this.editType = editType;
	}
	public String getOldId() {
		return oldId;
	}
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public Date getDelTime() {
		return delTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public String getEditRsn() {
		return editRsn;
	}
	public void setEditRsn(String editRsn) {
		this.editRsn = editRsn;
	}
	public String getDelRsn() {
		return delRsn;
	}
	public void setDelRsn(String delRsn) {
		this.delRsn = delRsn;
	}

	
	
}
