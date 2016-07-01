package com.lanen.model.quarantine;
/**
 * 动物体检项目字典
 * @author 黄国刚
 *
 */
public class DictPhysicalExamItem  implements java.io.Serializable{

	private static final long serialVersionUID = 2893034722079628056L;
	
	private String id;			//	数据ID	ID	varchar(20)	
	private String itemType;	//	项目类别	ItemType	varchar(60)	 
	private String itemName;	//	项目名称	ItemName	varchar(60) 
	private String remark;		//	操作说明	Remark	varchar(200)	
	private int orderNo;        //排序
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
