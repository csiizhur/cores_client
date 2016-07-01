package com.lanen.service.clinicaltest;

import com.lanen.base.BaseDao;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblInstrumentVerification;

public interface TblInstrumentVerificationService extends BaseDao<TblInstrumentVerification> {
	
	/**
	 * 保存实体（不带主键），同时返回实体（带主键）
	 * @param verification
	 * @return
	 */
	TblInstrumentVerification saveHaveReturn(TblInstrumentVerification verification);

	/**
	 * 删除该设备的所有检定信息
	 * @param dictInstrument
	 */
	void deleteByDictInstrument(DictInstrument dictInstrument);

}
