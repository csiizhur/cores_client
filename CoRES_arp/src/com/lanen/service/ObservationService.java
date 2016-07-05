package com.lanen.service;
import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Observation;

public interface ObservationService extends BaseLongDao<Observation> {

	/**
	 * 该动物编号的观察记录
	 */
	List<?> getAllObservationById(String monkeyid);
	/**
	 * 该动物编号的观察记录
	 */
	List<?> getAllObservationById(String monkeyid,String observationdate);
}
