package com.lanen.service.studyplan;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.DictAnimalType;

public interface DictAnimalTypeService extends BaseDao<DictAnimalType>{

	/**
	 * 查询所有  按orderNo排序
	 * @return
	 */
	List<DictAnimalType> findAllOrderByOrderNo();

	/**
	 * 根据动物种类名称查询实体
	 * @param typeName
	 * @return
	 */
	DictAnimalType getByName(String typeName);
	
	/**根据动物种类判断是否是大动物
	 * @param animalType
	 * @return
	 */
	boolean isBigAnimal(String animalType);

}
