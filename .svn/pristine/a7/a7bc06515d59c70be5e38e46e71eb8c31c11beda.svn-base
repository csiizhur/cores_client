package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.DictSpecimen;

/**
 * @author Administrator
 *
 */
public interface DictSpecimenService extends BaseDao<DictSpecimen> {
	
	/**
	 * 查询所有列表
	 * @return
	 */
	List<DictSpecimen> getAll();
	
	/**
	 * 判断标本种类是否存在
	 * @param name
	 * @return
	 */
	boolean isExistBySpecKind(String SpecKind);

	/** 获取  血液或其他的标本类型列表
	 * @return
	 */
	List<String> getBloodSpecimenKindList();

	/**获取 尿液或其他的标本类型列表
	 * @return
	 */
	List<String> getUrineSpecimenKindList();

}
