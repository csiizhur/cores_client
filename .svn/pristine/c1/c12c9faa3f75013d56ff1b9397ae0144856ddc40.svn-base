package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.TblRoomAndAniType;

public interface TblRoomAndAniTypeService extends BaseDao<TblRoomAndAniType> {

	/**
	 * 通过roomId查询动物种类列表    并转成的字符串
	 * @param roomId
	 * @return
	 */
	String getAnimalTypesByRoomId(String roomId);

	/**  查询 tblRoomAndAniType和tblAnimalHouse表，roomId相等
	 * @param animalType
	 * @return
	 */
	List<String> getRoomListbyAnimalType(String animalType);

	
}
