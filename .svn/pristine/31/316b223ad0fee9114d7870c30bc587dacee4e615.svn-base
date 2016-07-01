package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.DictPhysicalExamItem;

public interface DictPhysicalExamItemService extends BaseDao<DictPhysicalExamItem> {

	/**
	 * 查询所有，按orderNo排序
	 * 
	 * @return
	 */
	List<DictPhysicalExamItem> findAllOrderByOrderNo();

	/**
	 * 保存实体并返回排序号
	 * @param dictPhysicalExamItem
	 * @return
	 */
	int saveAndReturnOrderNo(DictPhysicalExamItem dictPhysicalExamItem);

	/**
	 * 查询itemName是否存在
	 * @param itemName
	 * @return
	 */
	boolean isExistByItemName(String itemName);

	/**
	 * 根据排序号删除
	 * @param orderNo
	 */
	void deleteByOrderNo(int orderNo);

	/**
	 * 互换排序号
	 * @param currentOrderNo
	 * @param nextOrderNo
	 */
	void updateOrderNo(int currentOrderNo, int nextOrderNo);

}
