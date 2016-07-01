package com.lanen.service.quarantine;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.quarantine.DictOutputSettings;

public interface DictOutputSettingsService extends BaseDao<DictOutputSettings> {

	/**
	 * 查询所有，根据orderNo排序
	 * @return
	 */
	List<DictOutputSettings> getListOrderByOrderNo();

	/**
	 * 更新所有
	 * @param list
	 */
	void updateList(List<DictOutputSettings> list);

	/**
	 * 根据label查询show
	 * @param label
	 * @return
	 */
	String getShowByLabel(String label);

}
