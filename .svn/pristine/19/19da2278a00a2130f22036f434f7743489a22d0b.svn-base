package com.lanen.service.path;

import java.util.List;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblAnatomyReqAttachedViscera;
/**
 * 解剖申请-脏器称重-附加脏器   service
 *@author 
 */
public interface TblAnatomyReqAttachedVisceraService extends BaseDao<TblAnatomyReqAttachedViscera> {

	/**根据解剖申请-脏器称重表ID查找附加脏器信息
	 * @param Pid
	 * @return
	 */
	List<TblAnatomyReqAttachedViscera> getListByPid(String Pid);

	/**获得附件脏器名称（已'、'分开）
	 * @param id
	 * @return
	 */
	String getAttachedVisceraNamesByPid(String id);

}
