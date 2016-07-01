package com.lanen.model.studyplan;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 试验计划
 * @author Administrator
 *
 */
public class TblStudyPlan   implements Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8055460051643933218L;
	private String  studyNo;            //课题编号
	private String studyTypeCode;       //课题类别编码
	private String studyName;           //课题名称
	private String studydirector;       //课题负责人
	private int isGLP;                  //是否GLP     0：否       1：是
	private String animalType;          //动物种类
	private String animalStrain;        //动物品系
	private Date studyStartDate;        //试验启动日期
	private Date animalImportDate;      //动物引入日期
	private Date preStudyDate;          //预试验日期
	private Date studyBeginDate;        //正式试验时期
	private String smplCode;            //供试品编号
	private String dosageUnit;          //剂量单位
	private String qa;                  //QA负责人
	private String pathDirector;        //病理负责人
	private String clinicalTestDirector;//临检负责人
	private String studyState;          //试验状态   0 未提交 1 提交 2 申请再编辑 3再编辑
	private int temp;                   //0  ，非临时     1，临时
	private String  client;             //委托单位
	private int isValidation;           //0,否  1，是
	private int animalCodeMode ;        //1:A   2:B
	private int abnVisceraAnatomyCheck;    //异常组织剖检标识0,否  1，是
	
	private int abnVisceraFixedFlag;    //异常组织固定标识0,否  1，是
	private int abnVisceraHistopathCheckFlag;  //异常组织镜检标识0,否  1，是
	private int abnVisceraWeighFlag;           //异常组织称重标识0,否  1，是
	private int adminSiteHistopathCheck;//给药部位镜检0,否  1，是      (暂未维护)
	private Date studyFinishDate;       //实验完成日期
	private String pathSD;              //专题病理负责人 (暂未维护)
	
	
	private int histopathReviewRequirement;    //镜检是否需要复审0,否  1，是
	private String histopathReviewer;   //镜检复审人
	
	
	private String volumeUnit;			//给药容积单位			
	private String thicknessUnit;		//给药浓度单位
	private int isNoGender;			//不分性别  0:未设置    1：分性别  2：不分性别
	private int isIndentical;		//雌雄动物剂量是否相同   0：未设置   1：相同  2：不相同
	private int doseSettingFlag;	//剂量组设计确认标记    0:未确认   1：已确认
	
	//0:尚未启动
	//1:试验启动
	//2：动物引入
	//3：动物检疫适应期
	//4：动物分组
	//5：试验进行中
	//6：活体试验结束
	//7：试验结束
	//8：报告草稿
	//9：报告完成
	//10：试验结束
	private Set<TblDoseSetting> tblDoseSettings =new HashSet<TblDoseSetting>();//剂量设置
	private Set<TblDissectPlan> tblDissectPlans = new HashSet<TblDissectPlan>();//解剖计划
	private Set<TblAnimal> tblAnimals = new HashSet<TblAnimal>();               //动物列表 
	private Set<TblTestIndexPlan> tblTestIndexPlans = new HashSet<TblTestIndexPlan>();//课题计划检验指标
	private Set<TblClinicalTestReq> tblClinicalTestReqs = new HashSet<TblClinicalTestReq>();//临检申请
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	
	public String getStudydirector() {
		return studydirector;
	}
	public void setStudydirector(String studydirector) {
		this.studydirector = studydirector;
	}
	public int getIsGLP() {
		return isGLP;
	}
	public void setIsGLP(int isGLP) {
		this.isGLP = isGLP;
	}
	public String getAnimalType() {
		return animalType;
	}
	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}
	public String getAnimalStrain() {
		return animalStrain;
	}
	public void setAnimalStrain(String animalStrain) {
		this.animalStrain = animalStrain;
	}
	public Date getStudyStartDate() {
		return studyStartDate;
	}
	public void setStudyStartDate(Date studyStartDate) {
		this.studyStartDate = studyStartDate;
	}
	public Date getAnimalImportDate() {
		return animalImportDate;
	}
	public void setAnimalImportDate(Date animalImportDate) {
		this.animalImportDate = animalImportDate;
	}
	public Date getPreStudyDate() {
		return preStudyDate;
	}
	public void setPreStudyDate(Date preStudyDate) {
		this.preStudyDate = preStudyDate;
	}
	public Date getStudyBeginDate() {
		return studyBeginDate;
	}
	public void setStudyBeginDate(Date studyBeginDate) {
		this.studyBeginDate = studyBeginDate;
	}
	public String getDosageUnit() {
		return dosageUnit;
	}
	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}
	public String getQa() {
		return qa;
	}
	public void setQa(String qa) {
		this.qa = qa;
	}
	public String getPathDirector() {
		return pathDirector;
	}
	public void setPathDirector(String pathDirector) {
		this.pathDirector = pathDirector;
	}
	public String getClinicalTestDirector() {
		return clinicalTestDirector;
	}
	public void setClinicalTestDirector(String clinicalTestDirector) {
		this.clinicalTestDirector = clinicalTestDirector;
	}
	public String getStudyState() {
		return studyState;
	}
	public void setStudyState(String studyState) {
		this.studyState = studyState;
	}
	public Set<TblDoseSetting> getTblDoseSettings() {
		return tblDoseSettings;
	}
	public void setTblDoseSettings(Set<TblDoseSetting> tblDoseSettings) {
		this.tblDoseSettings = tblDoseSettings;
	}
	public Set<TblDissectPlan> getTblDissectPlans() {
		return tblDissectPlans;
	}
	public void setTblDissectPlans(Set<TblDissectPlan> tblDissectPlans) {
		this.tblDissectPlans = tblDissectPlans;
	}
	public Set<TblAnimal> getTblAnimals() {
		return tblAnimals;
	}
	public void setTblAnimals(Set<TblAnimal> tblAnimals) {
		this.tblAnimals = tblAnimals;
	}
	public Set<TblTestIndexPlan> getTblTestIndexPlans() {
		return tblTestIndexPlans;
	}
	public void setTblTestIndexPlans(Set<TblTestIndexPlan> tblTestIndexPlans) {
		this.tblTestIndexPlans = tblTestIndexPlans;
	}
	public Set<TblClinicalTestReq> getTblClinicalTestReqs() {
		return tblClinicalTestReqs;
	}
	public void setTblClinicalTestReqs(Set<TblClinicalTestReq> tblClinicalTestReqs) {
		this.tblClinicalTestReqs = tblClinicalTestReqs;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public int getIsValidation() {
		return isValidation;
	}
	public void setIsValidation(int isValidation) {
		this.isValidation = isValidation;
	}
	public String getStudyTypeCode() {
		return studyTypeCode;
	}
	public void setStudyTypeCode(String studyTypeCode) {
		this.studyTypeCode = studyTypeCode;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public int getAnimalCodeMode() {
		return animalCodeMode;
	}
	public void setAnimalCodeMode(int animalCodeMode) {
		this.animalCodeMode = animalCodeMode;
	}
	public String getSmplCode() {
		return smplCode;
	}
	public void setSmplCode(String smplCode) {
		this.smplCode = smplCode;
	}
	public String getVolumeUnit() {
		return volumeUnit;
	}
	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}
	public String getThicknessUnit() {
		return thicknessUnit;
	}
	public void setThicknessUnit(String thicknessUnit) {
		this.thicknessUnit = thicknessUnit;
	}
	public int getIsNoGender() {
		return isNoGender;
	}
	public void setIsNoGender(int isNoGender) {
		this.isNoGender = isNoGender;
	}
	public int getIsIndentical() {
		return isIndentical;
	}
	public void setIsIndentical(int isIndentical) {
		this.isIndentical = isIndentical;
	}
	public int getDoseSettingFlag() {
		return doseSettingFlag;
	}
	public void setDoseSettingFlag(int doseSettingFlag) {
		this.doseSettingFlag = doseSettingFlag;
	}
	
	public int getAbnVisceraFixedFlag() {
		return abnVisceraFixedFlag;
	}
	public void setAbnVisceraFixedFlag(int abnVisceraFixedFlag) {
		this.abnVisceraFixedFlag = abnVisceraFixedFlag;
	}
	public int getAbnVisceraHistopathCheckFlag() {
		return abnVisceraHistopathCheckFlag;
	}
	public void setAbnVisceraHistopathCheckFlag(int abnVisceraHistopathCheckFlag) {
		this.abnVisceraHistopathCheckFlag = abnVisceraHistopathCheckFlag;
	}
	public int getAdminSiteHistopathCheck() {
		return adminSiteHistopathCheck;
	}
	public void setAdminSiteHistopathCheck(int adminSiteHistopathCheck) {
		this.adminSiteHistopathCheck = adminSiteHistopathCheck;
	}
	public int getAbnVisceraWeighFlag() {
		return abnVisceraWeighFlag;
	}
	public void setAbnVisceraWeighFlag(int abnVisceraWeighFlag) {
		this.abnVisceraWeighFlag = abnVisceraWeighFlag;
	}
	public Date getStudyFinishDate() {
		return studyFinishDate;
	}
	public void setStudyFinishDate(Date studyFinishDate) {
		this.studyFinishDate = studyFinishDate;
	}
	public String getPathSD() {
		return pathSD;
	}
	public void setPathSD(String pathSD) {
		this.pathSD = pathSD;
	}
	public int getHistopathReviewRequirement() {
		return histopathReviewRequirement;
	}
	public void setHistopathReviewRequirement(int histopathReviewRequirement) {
		this.histopathReviewRequirement = histopathReviewRequirement;
	}
	public String getHistopathReviewer() {
		return histopathReviewer;
	}
	public void setHistopathReviewer(String histopathReviewer) {
		this.histopathReviewer = histopathReviewer;
	}
	public void setAbnVisceraAnatomyCheck(int abnVisceraAnatomyCheck) {
		this.abnVisceraAnatomyCheck = abnVisceraAnatomyCheck;
	}
	public int getAbnVisceraAnatomyCheck() {
		return abnVisceraAnatomyCheck;
	}
	
}
