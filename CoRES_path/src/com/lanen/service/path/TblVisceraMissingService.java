package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblVisceraMissing;

/**脏器缺失记录Service
 * @author Administrator
 *
 */
public interface TblVisceraMissingService extends BaseDao<TblVisceraMissing> {
	
	/**
	 * 通过专题编号，动物编号，脏器编号或子脏器编号查询实体
	 * @return
	 */
	TblVisceraMissing getVisceraByStudyNoAndAnimalCodeAndVisceraCodeOrSubVisceraCode(String studyNo,String animalCode,String visceraCode,String subVisceraCode  );
	
	/**
	 * 通过sessionidList查询缺失脏器的动物
	 * @param sessionIdList
	 * @return
	 */
	int getVisceraMissingCountBySessionIdList(List<String> sessionIdList);
	
}
