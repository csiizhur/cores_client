package com.lanen.service.path;


import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblBalReg;

/**
 * 天平登记
 * @author Administrator
 *
 */
public interface TblBalRegService extends BaseDao<TblBalReg>  {
	/**
	 * 判断天平编号是否已经存在
	 * @param balCode
	 * @return
	 */
	boolean isExistByBalCode(String balCode);
	
	/**
	 * 判断天平编号除自己外是否以及存在
	 * @param balCode
	 * @param oldbalCode
	 * @return
	 */
	boolean isExistByBalCode(String balCode,String oldbalCode);
	
	/**
	 * 判断天平名称是否以及存在
	 * @param balName
	 * @return
	 */
    boolean isExistBybalName(String balName);
    
    /**
     * 判断天平名称除自己外是否已经存在
     * @param balName
     * @param oldbalName
     * @return
     */
    boolean isExistBybalName(String balName,String oldbalCode);
    /**
     * 加载所有的天平编号
     * @param hostName 
     * @return
     */
    List<String>  getAllBalCode(String hostName);

	/**加载所有启用的天平编号
	 * @param hostName
	 * @return
	 */
	List<String> getEnableBalCode(String hostName);

	/**查询可用天平的 设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 * @param balCode
	 * @param hostName
	 * @return
	 */
	Map<String, Object> findBalMapByBalCodeHostName(String balCode, String hostName);
	
	
	/**
	 * 根据计算机名查询未接入的天平
	 * @param hostName
	 * @return
	 */
	List<Map<String,Object>> getNoHaveTblBalRegByHostName(String hostName);

	/**加载所有的天平编号
	 * @return
	 */
	List<String> getAllBalCode();
}