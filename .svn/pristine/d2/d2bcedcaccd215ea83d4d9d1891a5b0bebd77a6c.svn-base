package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.DictInstrument;

public interface DictInstrumentService extends BaseDao<DictInstrument>{

	/**
	 * 查询所有列表
	 * @return
	 */
	List<DictInstrument> getAll();

	/**
	 * 判断设备ID是否存在
	 * @param name
	 * @return
	 */
	boolean isExistByInstrumentId(String instrumentId);

	/**
	 * 根据  检测项目  查询设备列表
	 * @param testItem
	 * @return
	 */
	List<DictInstrument> findByTestItem(int testItem);

}
