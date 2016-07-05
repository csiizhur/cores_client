package com.lanen.service;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.Changeroom;

public interface ChangeroomService extends BaseDao<Changeroom> {

	/**根据动物号获取调拨记录
	 * @return
	 */
	List<Changeroom> getChangeroomById(String monkeyid);
}
