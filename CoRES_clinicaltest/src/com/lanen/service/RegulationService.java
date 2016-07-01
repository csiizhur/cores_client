package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.Regulation;

public interface RegulationService extends BaseLongDao<Regulation> {
	

	/**
	 * 判断规则名称是否存在
	 * @param regulationName
	 * @return
	 */
	boolean isExist(String regulationName);

	/**
	 * 保存实体并返回Id
	 * @param entity
	 * @return
	 */
	Long saveReturnId(Regulation entity);
	/**
	 * 更新实体列表
	 * @param list
	 */
	void updateList(List<Regulation> list);
//	/***
//	 * 保存带有创建时间的实体
//	 * @param regulation
//	 */
//	void saveWithTime(Regulation regulation);
//
//	/**
//	 * 修改其他的flag
//	 * @param regulation
//	 */
//	void updatewithFlag(Regulation regulation);

	/**
	 * 密码有没有超出有效期
	 * @param updatePasswordTime
	 * @return
	 */
	boolean isOverTime(int updatePasswordTime);
	
	Regulation getByName(String regulationName) ;


}
