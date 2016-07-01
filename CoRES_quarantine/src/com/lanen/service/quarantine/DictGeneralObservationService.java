package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.DictGeneralObservation;

public interface DictGeneralObservationService  extends BaseDao<DictGeneralObservation>{

	/**
	 * 查询    一般观察    节点（非叶子节点）列表，itemName为空或'' ,根据OrderNo 排序 
	 * @return
	 */
	List<DictGeneralObservation> getNodeList();

	/**
	 * 查询   某 节点（非叶子节点）下的多有子节点列表，itemName不为    空或'' ,根据OrderNo 排序 
	 * @param nodeName
	 * @return
	 */
	List<DictGeneralObservation> getLeafList(String nodeName);

	/**
	 * 查询  ItemName 是否已存在
	 * @param itemName
	 * @return
	 */
	boolean isExistByItemName(String itemName);

	/**
	 * 如果  ItemType 已存在，则直接保存，否则先保存     ‘组’（node） ，再保存leaf 
	 * @param dictGeneralObservation
	 */
	void saveNodeOrLeaf(DictGeneralObservation dictGeneralObservation);

	/**
	 * 删除一组
	 * @param value
	 */
	void deleteByItemType(String itemType);

	/**
	 * 删除一项
	 * @param value
	 */
	void deleteByItemName(String itemName);

	/**
	 *  叶子向上移一位
	 * @param itemName
	 */
	void upOrderNoByItemName(String itemName);
	/**
	 * 组向上移一位
	 * @param itemType
	 */
	void upOrderNoByItemType(String itemType);

	/**
	 *  叶子向下移一位
	 * @param itemName
	 */
	void downOrderNoByItemName(String itemName);

	/**
	 *  叶子向下移一位
	 * @param itemType
	 */
	void downOrderNoByItemType(String itemType);
	
	/**查询objservationCode是否存在
	 * @param observationCode
	 * @return
	 */
	boolean isExistByObservationCode(String observationCode);

}
