package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblHostBal;

public interface TblHostBalService extends BaseDao<TblHostBal> {

	/**
	 * 根据主机名查询实体列表
	 * @param hostName
	 * @return
	 */
	List<TblHostBal> findListByHostName(String hostName);
	
	/**
	 * 判断  主机与设备    是否存在
	 * @param hostName
	 * @param balId
	 * @return
	 */
	boolean isExistByHostNameBalId(String hostName,String balId);
	
	/**
	 * 保存实体，并返回  id
	 * @param tblHostBal
	 * @return
	 */
	String saveReturnId(TblHostBal tblHostBal);

	/**
	 * 判断  主机与串口    是否存在
	 * @param hostName
	 * @param balId
	 * @return
	 */
	boolean isExistByHostNameCom(String hostName, String comPort);

	/**
	 * 查询  a.balId,a.comPort,b.baudRate,b.dataBit,b.stopBit,b.checkMode
	 * @param hostName
	 * @param enable
	 * @return
	 */
	List<?> findListByHostNameEnable(String hostName, int enable);

}
