package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblBalConnect;
/**
 * 天平接入信息
 * @author Administrator
 *
 */
public interface TblBalConnectService extends BaseDao<TblBalConnect>{
   
	/**
	 * 判断同一个天平下的计算机是否重复
	 * @param hostname
	 * @param balCode
	 * @return
	 */
	boolean isExistByHostnameAndBalCode(String hostname,String balCode);
	
	/**
	 * 判断是否已经有计算机和天平接入
	 * @param balCode
	 * @return
	 */
	boolean isEnabledByBalCode(String balCode);
	
	/**
	 * 根据天平编号停用
	 * @param balCode
	 */
	void closedEnabledByBalCode(String balCode);
	
	/**
	 * 根据主机名判断是否有芯片阅读器接入
	 * @param hostname
	 * @return
	 */
	List<TblBalConnect>  isHaveChipReaderByHostname(String hostname);
	
	
	/**
	 * 根据天平编号查询
	 * @param balCode
	 * @return
	 */
	List<TblBalConnect> findByBalCodeList(String balCode);

	
	/**
	 * 判断是否已经有多个计算机和天平接入
	 * @param balCode
	 * @return
	 */
	boolean isEnabledByBalCode2(String balCode);

	/**根据天平编号和计算机编号查找链接的端口
	 * @param balCode
	 * @param hostName
	 * @return
	 */
	String getComPort(String balCode, String hostName);
	
	/**
	 * 根据计算机编号查询
	 * @param hostName
	 * @return
	 */
	List<TblBalConnect> findByHostNameList(String hostName);
	
	/**
	 * 根据计算机编号查询
	 * @param hostName
	 * @param isEnable 是否启用   0:否   1:是
	 * @return
	 */
	List<TblBalConnect> findByHostNameList(String hostName,int isEnable);
	

}
