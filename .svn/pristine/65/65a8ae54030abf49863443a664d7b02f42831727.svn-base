package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblCounterpoise;

/**
 *  砝码允差
 * @author Administrator
 *
 */
public interface TblCounterpoiseService extends BaseDao<TblCounterpoise>{
	
	
	/**
	 * 判断是否已经有计算机和天平接入
	 * @param balCode
	 * @return
	 */
	boolean isEnabledByCpCode(String cpCode);
	
	
	
	/**
	 * 砝码天平精度 是否重复
	 * @param cPCode
	 * @param cpWeight
	 * @param balPrecision
	 * @return
	 */
	boolean isEnabledByCpWeightAndCalPrecision(String cPCode,String cpWeight, String balPrecision);

	/**根据天平精度获得砝码允差信息
	 * @param precision
	 * @return
	 */
	List<TblCounterpoise> getByPrecision(String precision);
	
	/**
	 * 根据天平编号查询
	 */
	List<TblCounterpoise> getListByCpCode(String cpCode);

}
