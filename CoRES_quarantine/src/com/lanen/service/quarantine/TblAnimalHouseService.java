package com.lanen.service.quarantine;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblAnimalHouse;

public interface TblAnimalHouseService extends BaseDao<TblAnimalHouse> {

	/**
	 * 判断房间号是否存在
	 * @param room
	 * @return
	 */
	boolean isExistByRoom(String room);

	/**
	 * 保存动物房间设置表，动物房间和饲养动物对照表，返回  排序号
	 * @param tblAnimalHouse
	 * @param animalType
	 * @return
	 */
	int saveAndReturnOrderNo(TblAnimalHouse tblAnimalHouse, String animalType);

	/**
	 * 查询 room ，floor，building，动物列表（如：大鼠，小数），排序号，存放于Map中，在存放于list
	 * @return
	 */
	List<Map<String, String>> findListOrderbyOrderNo();
	/**
	 * 获取下一个排序号
	 * @return
	 */
	int getNextOrderNo();
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
	TblAnimalHouse getByOrderNo(int orderNo);
	
	/**
	 * 查询所有，通过orderNo排序
	 * @return
	 */
	List<TblAnimalHouse> findAllOrderByOrderNo();
	
	/**
	 * 通过动物类型，获得该种动物可以安置的所有房间
	 * @param animalType
	 * @return
	 */
	List<String> getRoomListByAnimalType(String animalType);
}
