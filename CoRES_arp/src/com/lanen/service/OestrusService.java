package com.lanen.service;

import java.util.List;
import com.lanen.base.BaseLongDao;
import com.lanen.model.Breeding;
import com.lanen.model.Breeding_Json;

public interface OestrusService extends BaseLongDao<Breeding> {

	/**
	 * 获取动物发情信息
	 */
	List<Breeding_Json> getAllOestrusById(String monkeyid);
}
