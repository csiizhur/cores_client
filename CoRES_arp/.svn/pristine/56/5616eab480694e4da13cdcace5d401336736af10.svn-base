package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.Vaccine;
import com.lanen.model.Vaccine_Json;

public interface VaccineService extends BaseDao<Vaccine> {

	/**
	 * 根据动物编号查询疫苗记录.
	 */
	List<Vaccine_Json> getVaccineById(String monkeyid,String checkId);
	/**
	 * 根据动物编号，检疫编号查询该动物疫苗检疫信息.
	 */
	List<?> getVaccineId(String monkeyid,String normalid);
}
