package com.lanen.model.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知信息
 * @author Administrator
 *
 */
public class TblNotification implements Serializable {

	private static final long serialVersionUID = -4919681897625080620L;
	
	private String id;
	
	private String msgTitle;			// 消息标题
	private String msgContent;			// 消息内容
	private String msgSource;			// 消息源
	private String msgLink;				// 处理链接
	private String sender;				// 发送者
	private String receiver;			// 接受者
	private Date sendTime;				// 发送时间
	private Date recTime;				// 接受时间
	private int needReceipt;			// 是否需要回执
	private int msgType;				// 消息类型      1：系统消息，2：个人信息，3：通知公告，9：回执
	private int readFlag;				// 已读标志
	private int attachmentFlag;			// 是否有附件
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgSource() {
		return msgSource;
	}
	public void setMsgSource(String msgSource) {
		this.msgSource = msgSource;
	}
	public String getMsgLink() {
		return msgLink;
	}
	public void setMsgLink(String msgLink) {
		this.msgLink = msgLink;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getRecTime() {
		return recTime;
	}
	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}
	public int getNeedReceipt() {
		return needReceipt;
	}
	public void setNeedReceipt(int needReceipt) {
		this.needReceipt = needReceipt;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public int getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}
	public int getAttachmentFlag() {
		return attachmentFlag;
	}
	public void setAttachmentFlag(int attachmentFlag) {
		this.attachmentFlag = attachmentFlag;
	}

}
