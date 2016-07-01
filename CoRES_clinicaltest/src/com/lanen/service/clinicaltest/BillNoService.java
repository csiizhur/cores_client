package com.lanen.service.clinicaltest;

import java.util.List;

import com.lanen.base.BaseLongDao;
import com.lanen.model.clinicaltest.PoolSpecimenId;

public interface BillNoService extends BaseLongDao<PoolSpecimenId> {

	/**
	 * 获取下一个流水号
	 * @return
	 */
	String getNextBillNo(int  item);

	/**
	 * 获得连续几个流水号
	 * @param item     
	 * @param selected
	 * @return
	 */
	List<String> getMuchNextBillNo(int item, int selected);
}
