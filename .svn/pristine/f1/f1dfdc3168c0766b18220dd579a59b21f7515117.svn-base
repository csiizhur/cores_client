package com.lanen.service.path;

import java.util.List;
import java.util.Map;

import com.lanen.base.BaseDao;
import com.lanen.model.path.DictViscera;

/**
 * 
 * 脏器字典     service
 * @author 黄国刚
 *
 */
/**
 * @author Administrator
 *
 */
public interface DictVisceraService extends BaseDao<DictViscera>{

	/**
	 * 查询脏器列表,以treeData需要展示(包括_parentId,state:'','open','closed')
	 * @return
	 */
	List<Map<String, Object>> queryDataMapList(Integer sortType);

	/**查询脏器类别列表(from dictVisceraType)
	 * @return
	 */
	List<Map<String, Object>> queryVisceraTypeDataMapList();

	/**脏器名称是否存在
	 * @param visceraName
	 * @return
	 */
	boolean isExistByVisceraName(String visceraName);

	/**脏器名称是否存在(出visceraCode脏器外)
	 * @param visceraName
	 * @param visceraCode
	 * @return
	 */
	boolean isExistByVisceraName(String visceraName, String visceraCode);
	/**
	 * 查询 动物种类列表
	 * @return
	 */
	List<Map<String, Object>> queryAnimalTypeDataMapList();

	/**获取下一个SN
	 * @return
	 */
	Integer getNextSn();

	/**添加一个对象
	 * @param dictViscera
	 * @param animalTypeNameList 
	 */
	void addOne(DictViscera dictViscera, List<String> animalTypeNameList);

	/**更新一级脏器,同时对于二级的动物类别与脏器类别进行更新
	 * @param dictViscera
	 * @param animalTypeNameArray 
	 */
	void updateOne(DictViscera dictViscera, String[] animalTypeNameArray);

	/**更新二级脏器
	 * @param dictViscera
	 * @param animalTypeNameArray 
	 */
	void updateSon(DictViscera dictViscera, String[] animalTypeNameArray);

	/**删除脏器及其子脏器
	 * @param visceraCode
	 */
	void delOne(String visceraCode);

	/**上移(一级脏器)
	 * @param sn  序号
	 * @param lineNum 
	 */
	boolean upOneBySn(Integer sn,Integer sortType, Integer lineNum);
	
	/**下移(一级脏器)
	 * @param sn  序号
	 */
	boolean downOneBySn(Integer sn,Integer sortType, Integer lineNum);


	/**
	 * 根据序号,获得下n个序号对应  对象(一级脏器)
	 * @param sn
	 * @return
	 */
	DictViscera getNextOneBySn(Integer sn,Integer sortType,Integer lineNum);
	/**
	 * 根据序号,获得序号对应  对象(一)
	 * @param sn
	 * @return
	 */
	DictViscera getOneBySn(Integer sn,Integer sortType);
	
	/**
	 * 根据序号,获得序号对应  对象(二级)
	 * @param sn
	 * @return
	 */
	DictViscera getOneBySnPvisceraCode(Integer sn,Integer sortType,String pVisceraCode);
	
	/**
	 * 根据序号,获得上 N 个序号对应  对象(一级脏器)
	 * @param sn
	 * @return
	 */
	DictViscera getPrevOneBySn(Integer sn,Integer sortType,Integer lineNum);
	
	/**上移(二级脏器)
	 * @param sn  序号
	 * * @param pvisceraCode
	 */
	boolean upOneBySnPvisceraCode(Integer sn,String pvisceraCode,Integer sortType,Integer lineNum);
	
	/**下移(二级脏器)
	 * @param sn  序号
	 * * @param pvisceraCode
	 */
	boolean downOneBySnPvisceraCode(Integer sn,String pvisceraCode,Integer sortType,Integer lineNum);
	
	
	/**
	 * 根据序号\父节点,获得下n个序号对应  对象(二级脏器)
	 * @param sn
	 * * @param pvisceraCode
	 * @return
	 */
	DictViscera getNextOneBySnPvisceraCode(Integer sn,String pvisceraCode,Integer sortType,Integer lineNum);
	
	/**
	 * 根据序号\父节点,获得上N个序号对应  对象(二级脏器)
	 * @param sn
	 * @param pvisceraCode
	 * @return
	 */
	DictViscera getPrevOneBySnPvisceraCode(Integer sn,String pvisceraCode,Integer sortType,Integer lineNum);

	/**查询所有一级脏器,code,name
	 * @return
	 */
	List<Map<String,Object>> query1LCodeNameList();
	/**根据动物种类查询所有该种类特有以及动物都有的器官
	 * @param AnimalType
	 * @return
	 */
	List<DictViscera> get1LListByAnimalType(String animalType);
	/**根据脏器名查询实体
	 * @param visceraName 
	 * @return
	 */
	DictViscera getByVisceraName(String visceraName);
	/**
	 * 根据很多脏器的名字查寻实体
	 * @param visceraName
	 * @return
	 */
	List<DictViscera> getByVisceraNameList(List<String> visceraNameList);
	/**查询动物种类数量
	 * @return
	 */
	int allAnimalTypeNumber();

	/**查询脏器对应的动物
	 * @param visceraCode
	 * @return
	 */
	List<String> getAnimalTypeNameByVisceraCode(String visceraCode);
	
	/**根据父脏器编号，子脏器列表
	 * @param visceraCode
	 * @return
	 */
	List<DictViscera> getSubVisceraListByVisceraCode(String visceraCode);
	
	/**父脏器，对应区间，对应方式  都加 1
	 * @param prevSn （包含 ）
	 * @param sn(不包含)
	 * @param sortType
	 */
	void oneLevelSnPlus1(Integer prevSn, Integer sn, Integer sortType);
	
	/**子脏器，对应区间，对应方式  都加 1
	 * @param prevSn（包含 ）
	 * @param sn(不包含)
	 * @param pvisceraCode
	 * @param sortType
	 */
	void twoLevelSnPlus1(Integer prevSn, Integer sn,
			String pvisceraCode, Integer sortType);
	
	/**父脏器，对应区间，对应方式  都减 1
	 * @param sn （不包含 ）
	 * @param nextSn(包含)
	 * @param sortType
	 */
	void oneLevelSnMinus1(Integer sn, Integer nextSn, Integer sortType);
	
	/**子脏器，对应区间，对应方式  都减 1
	 * @param sn（不包含 ）
	 * @param nextSn(包含)
	 * @param pvisceraCode
	 * @param sortType
	 */
	void twoLevelSnMinus1(Integer sn, Integer nextSn,
			String pvisceraCode, Integer sortType);
}
