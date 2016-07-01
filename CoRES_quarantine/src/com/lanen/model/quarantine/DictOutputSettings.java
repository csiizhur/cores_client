package com.lanen.model.quarantine;

import java.io.Serializable;

/**界面输出设置
 * @author 黄国刚
 *
 */
public class DictOutputSettings implements Serializable{

	private static final long serialVersionUID = -8178165733581493325L;
	
	
	private String id;			
	private String label;		//标签
	private String show;		//显示
	private int orderNo;        //排序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	

	
}
