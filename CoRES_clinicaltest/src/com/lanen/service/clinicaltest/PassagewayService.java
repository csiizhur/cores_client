package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblPassageway;

public interface PassagewayService extends BaseDao<TblPassageway>{

	/**
	 * 根据  检验项目、检验指标 和设备id   查询
	 * @param testItem
	 * @param abbr
	 * @param instrumentId 
	 * @return
	 */
	List<TblPassageway> getByTestItem(int testItem, String abbr, String instrumentId);

	/**
	 * 该检测项目、该设备下      的通道号是否存在
	 * @param testItem
	 * @param instrumentId
	 * @param abbr
	 * @param passagewayName 
	 * @return
	 */
	boolean isExist(int testItem, String instrumentId, String passagewayName);

	/**
	 * 保存 检测项目、该设备   该指标   的通道号 s
	 * @param testItem
	 * @param instrumentId
	 * @param abbr
	 * @param strList
	 */
	void saveAll(int testItem, String instrumentId, String abbr, List<String> strList);

	/**
	 * 删除该设备的所有通道设置
	 * @param dictInstrument
	 */
	void deleteByDictInstrument(DictInstrument dictInstrument);

	/**
	 * 根据  检验项目、设备通道 和设备id   查询
	 * @param testItem
	 * @param passageway
	 * @param instrumentId
	 * @return
	 */
	TblPassageway getByTestItemPassagewayInstrumentId(int testItem, String passageway,
			String instrumentId);
	/**
	 * 保存 检测项目、该设备   该指标   的通道号 
	 * @param testItem
	 * @param instrumentId
	 * @param abbr
	 * @param passagewayName
	 */
	void save(int testItem, String instrumentId, String abbr, String passagewayName);

	/**
	 * 根据通道号名称删除通道号
	 * @param selected
	 */
	void deleteByPassageway(String selected);

}
