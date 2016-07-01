package com.lanen.service.path;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblCounterWeight;

public interface TblCounterWeightService extends BaseDao<TblCounterWeight>{
	
	
	/**
	 * 根据砝码编号判断砝码编号是否重复
	 * @param cpCode
	 * @return
	 */
	boolean isHaveCpCodeByCpCode(String cpCode);
	
	/**
	 * 删除砝码的时候把砝码校准记录也删除
	 * @param id
	 */
	void delectAll(String id);

}
