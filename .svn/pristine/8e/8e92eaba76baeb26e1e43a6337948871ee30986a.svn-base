package com.lanen.service.contract;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.contract.TblNotification;

public interface TblNotificationService extends BaseDao< TblNotification>{
	
	
	/**设置接收时间
	 * @param idList 
	 * @param date
	 */
	void setRecTime(List<String> idList, Date date);


	/**保存通知信息，
	 * @param tblNotification  无id，无接收人
	 * @param receiverList
	 */
	void save(TblNotification tblNotification, List<String> receiverList);
	/**
	 *获取未接收的邮件id
	 * */
    List<String> getUnRecMail(String userName);
    
    /**根据接收者,查询所有发送者列表(去重,加  -1:&nbsp;)
	 * @param userName
	 * @return
	 */
	List<Map<String, String>> getAllSenderByReceiver(String receiver);
	
	/**查询日期区间内的信息列表,如果该信息列表中有未设置接收时间的,则设为当前时间
	 * @param receiver				//接收者
	 * @param sender				//发送者,为空时为所有
	 * @param startDate				
	 * @param endDate
	 * @return
	 */
	List<TblNotification> getListBySenderReceiverDate(String receiver,
			String sender, Date startDate, Date endDate);
}
