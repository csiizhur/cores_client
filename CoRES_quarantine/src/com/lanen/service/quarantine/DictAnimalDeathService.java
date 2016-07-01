package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.DictAnimalDeath;

public interface DictAnimalDeathService extends BaseDao<DictAnimalDeath>{
	/**
	 * 查询所有，按orderNo排序
	 * 
	 * @return
	 */
	List<DictAnimalDeath> findAllOrderByOrderNo();
	
	/**
	 * 保存实体并返回排序号
	 * @param dictPhysicalExamItem
	 * @return
	 */
	int saveAndReturnOrderNo(DictAnimalDeath dictlAnimalDeath);
	/**
	 * 获取下一个排序号
	 * @return
	 */
	int getNextOrderNo();
	
	/**
	 * 查询itemName是否存在
	 * @param itemName
	 * @return
	 */
	boolean isExistByDeadType(String deadType);
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
	/**
	 * 根据排序号 查询实体
	 * @param orderNo
	 */
	DictAnimalDeath getByOrderNo(int orderNo);
}
