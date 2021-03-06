package com.lanen.service.studyplan;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.studyplan.DictUrine;

public interface DictUrineService extends BaseDao<DictUrine>{

	/**
	 * 查询所有列表
	 * @return
	 */
	List<DictUrine> getAll();

	/**
	 * 判断名称是否存在
	 * @param name
	 * @return
	 */
	boolean isExistByName(String name);
	/**
	 * 获得      指标缩写，排序   对应map
	 * @return
	 */
	Map<String, Integer> getMap();
	/**
	 * 获得      指标缩写，单位   对应map
	 * @return
	 */
	Map<String, String> geUnittMap();

	/**
	 * 更具检验指标缩写得到名称
	 * @param indexAbbr
	 * @return
	 */
	String getIndex2ByAbbr(String indexAbbr);
	
	/**
	 * 设置顺序
	 * @param orderNoPara
	 * @param orderNoNext
	 */
	void moveOeder(int orderNoPara, int orderNoNext);
	
	/**
	 * 获取下一个排序号
	 * @return
	 */
	int getNextOrderNo();
	/**
	 * 查询所有并排序（升序）
	 * @return
	 */
	List<DictUrine> findAllOrderByOrderNo();
}
