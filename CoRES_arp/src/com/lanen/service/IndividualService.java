package com.lanen.service;


import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.Individual;
import com.lanen.model.Individual_Json;
import com.lanen.model.Normal;

public interface IndividualService extends BaseDao<Individual> {

	/**
	 * 根据动物编号获取实体
	 * @param monkeyid
	 * @return
	 */
	Individual getByMonkeyid(String monkeyid);
	
	Individual_Json getIndividualJsonById(String monkeyidid);
	
	/**
	 * 根据编号查询所有检疫编号.
	 * @param monkeyid
	 * @return
	 */
	List<Normal> getCheckInfoById(String monkeyid);
	
	/**
	 * 获取所有的猴号
	 * @return
	 */
	List<Map<String,String>> getAllMonkeyidCombobox();
	/**
	 * 根据chipid查monkeyid
	 */
	String getMonkeyIdByChipId(String chipid);
}
