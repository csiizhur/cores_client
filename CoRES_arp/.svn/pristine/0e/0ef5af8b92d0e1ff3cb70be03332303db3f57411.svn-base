package com.lanen.service;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Area;

public interface AreaService extends BaseLongDao<Area> {

	/**得到所以的父区域名字和ID
	 * @return
	 */
	List<Map<String, Object>> getAllPareaIdName();

	/**根据父区域ID拿到所有房间
	 * @param blongArea
	 * @return
	 */
	List<Map<String, Object>> getAllRoomIdName(Long blongArea);
	List<?> getAnimal(Long id);
	
	/**加载所有未删除的区域和房间数据
	 * @return
	 */
	List<?> getAllAreaList();
	
	/**得到所有动物的饲养员（不重复）
	 * @return
	 */
	List<Long> getAllKeeper();
	
	/**
	 * 根据区域获取动物
	 * @param room
	 * @return
	 */
	List<?> getAnimalByArea(Long room);
	/**
	 * 在场动物
	 */
	List<?> getInAnimal(Long id);
	/**
	 * 待销售
	 * @param id
	 * @return
	 */
	List<?> getOutAnimal(Long id);
	/**
	 * 根据饲养员获取动物.
	 */
	List<?> getAnimalByKeeper(Long keeper);
	/**
	 * 根据饲养员获取在场动物.
	 */
	List<?> getInAnimalByKeeper(Long keeper);
	/**
	 * 根据饲养员获取待销售动物.
	 */
	List<?> getOutAnimalByKeeper(Long keeper);
}
