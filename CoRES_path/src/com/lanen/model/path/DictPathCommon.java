package com.lanen.model.path;

import java.io.Serializable;

/**
 * 毒性病理字典
 * @author 黄国刚
 *
 */
public class DictPathCommon implements Serializable {

	private static final long serialVersionUID = -262575721276968073L;
	
	private String itemCode;        // 编号
	private Integer dictType;       // 字典项类别
							//	（剖检）1：解剖学所见（注：脏器相关）
							//	（剖检）2：剖检通用所见
							//	（剖检）3：剖检特殊所见（注：脏器相关）
							//	（剖检）4：体表部位
							//	（剖检）5：位置
							//	（剖检）6：分布
							//	（剖检）7：数量
							//	（剖检）8：形状
							//	（剖检）9：颜色
							//	（剖检）10：硬度
							//	（剖检）11：大小
							//	（剖检）12：病变程度
							//
							//	（镜检）13：组织学所见（注：脏器相关）
							//	（镜检）14：肿瘤性病变（良性）（注：脏器相关）
							//	（镜检）15：肿瘤性病变（恶性）（注：脏器相关）
							//
							//	（镜检）16：非肿瘤性病变
	private String visceraCode;     // 脏器编号
		private Integer visceraType;    // 脏器类型
		private String visceraName;     // 脏器名称
	private Integer specicalFlag;   // 特殊所见标志    dictType为2或3时使用,  0:无,1:肿瘤
	private String descCn;          // 名称(中文)
	private String descEn;          // 名称(英文)
	private String descJp;          // 名称(日文)
	private String py;              // 拼音首字母
	private Integer sortNum;        // 排序号
	private Integer freqCount;      // 使用频度
	public Integer getDictType() {
		return dictType;
	}
	public void setDictType(Integer dictType) {
		this.dictType = dictType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Integer getVisceraType() {
		return visceraType;
	}
	public void setVisceraType(Integer visceraType) {
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
	public Integer getSpecicalFlag() {
		return specicalFlag;
	}
	public void setSpecicalFlag(Integer specicalFlag) {
		this.specicalFlag = specicalFlag;
	}
	public String getDescCn() {
		return descCn;
	}
	public void setDescCn(String descCn) {
		this.descCn = descCn;
	}
	public String getDescEn() {
		return descEn;
	}
	public void setDescEn(String descEn) {
		this.descEn = descEn;
	}
	public String getDescJp() {
		return descJp;
	}
	public void setDescJp(String descJp) {
		this.descJp = descJp;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getFreqCount() {
		return freqCount;
	}
	public void setFreqCount(Integer freqCount) {
		this.freqCount = freqCount;
	}
	
	
	
}
