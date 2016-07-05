package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Miscarriage;
import com.lanen.model.Miscarriage_Json;

public interface MiscarriageService extends BaseLongDao<Miscarriage> {
	/**
	 * 获取动物流产信息
	 */
	List<Miscarriage_Json> getAllMiscarriageById(String monkeyid);
}
