package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.TblSuperficialTumorViscera;

/**浅表肿瘤脏器登记表 service
 * @author Administrator
 *
 */
public interface TblSuperficialTumorVisceraService extends BaseDao<TblSuperficialTumorViscera>{

	/**查询浅表肿瘤脏器列表（visceraCode，visceraName按系统顺序排列）
	 * @return
	 */
	List<Map<String,Object>> getSuperficialTumorVisceraList(); 
	
	/**查询非浅表肿瘤脏器列表（visceraCode，visceraName按系统顺序排列）
	 * @return
	 */
	List<Map<String,Object>> getNoSuperficialTumorVisceraList(); 
	
	/**判断当前脏器是否是浅表肿瘤脏器
	 * @param visceraCode
	 * @return
	 */
	boolean isExistByVisceraCode(String visceraCode);
}
