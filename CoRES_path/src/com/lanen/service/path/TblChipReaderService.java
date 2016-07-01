package com.lanen.service.path;

import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblChipReader;

public interface TblChipReaderService extends BaseDao<TblChipReader>{

	/** 查询芯片阅读器的   设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 * @param hostName
	 * @param i
	 * @return
	 */
	Map<String, Object> findChipReaderMapByHostNameEnable(String hostName, int i);

	/**在YYDB.dbo.TBICard 查询  studyNo，animalCode
	 * @param iCardCode
	 * @return
	 */
	Map<String, Object> getStudyNoAnimalCodeMapByICardCode(String iCardCode);
}