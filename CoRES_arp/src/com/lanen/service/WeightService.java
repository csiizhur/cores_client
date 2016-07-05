package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Weight;

public interface WeightService extends BaseLongDao<Weight> {

	/**
	 * 该动物的称重记录--weighttype=14.
	 */
	List<?> getAllWeightById(String monkeyid);
	/**
	 * 该动物的称重记录--weighttype=14.
	 */
	List<?> getAllWeightById(String monkeyid,String weightdate);
}
