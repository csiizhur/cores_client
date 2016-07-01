package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblBal;

public interface TblBalService extends BaseDao<TblBal>{
	/**
	 * 查询所有，按orderNo排序
	 * 
	 * @return
	 */
	List<TblBal> findAllOrderByOrderNo();
	
	/**
	 * 保存实体并返回排序号
	 * @param tblBal
	 * @return
	 */
	int saveAndReturnOrderNo(TblBal tblBal);
	/**
	 * 获取下一个排序号
	 * @return
	 */
	int getNextOrderNo();
	
	
	/**
	 * 根据balId删除,同时删除对应的连接信息
	 * @param orderNo
	 */
	void deleteByBalId(String balId);
	/**
	 * 互换排序号
	 * @param currentOrderNo
	 * @param nextOrderNo
	 */
	void updateOrderNo(int currentOrderNo, int nextOrderNo);
	/**
	 * 根据排序号 查询实体
	 * @param orderNo
	 */
	TblBal getByOrderNo(int orderNo);

	/**
	 * 根据是否是自动采集，查询balId列表
	 * @param commEnable
	 * @return
	 */
	List<String> getBalIdListByCommEnable(int commEnable);
}
