package com.lanen.base;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanen.service.DepartmentService;
import com.lanen.service.ModuleService;
import com.lanen.service.PrivilegeService;
import com.lanen.service.RegulationService;
import com.lanen.service.RoleService;
import com.lanen.service.SystemService;
import com.lanen.service.UserRoleLogService;
import com.lanen.service.UserService;
import com.lanen.service.VersionControlService;
import com.lanen.service.clinicaltest.BillNoService;
import com.lanen.service.clinicaltest.DictInstrumentService;
import com.lanen.service.clinicaltest.DictSpecimenService;
import com.lanen.service.clinicaltest.GetIdService;
import com.lanen.service.clinicaltest.PassagewayService;
import com.lanen.service.clinicaltest.PoolSpecimenCodeService;
import com.lanen.service.clinicaltest.TblClinicalTestDataService;
import com.lanen.service.clinicaltest.TblInstrumentVerificationService;
import com.lanen.service.clinicaltest.TblLogService;
import com.lanen.service.clinicaltest.TblSpecimenService;
import com.lanen.service.clinicaltest.TblTraceService;
import com.lanen.service.contract.TblNotificationService;
import com.lanen.service.studyplan.DictAnimalTypeService;
import com.lanen.service.studyplan.DictBioChemService;
import com.lanen.service.studyplan.DictBloodCoagService;
import com.lanen.service.studyplan.DictHematService;
import com.lanen.service.studyplan.DictReportNumberService;
import com.lanen.service.studyplan.DictUrineService;
import com.lanen.service.studyplan.TblAnimalService;
import com.lanen.service.studyplan.TblClinicalTestReqService;
import com.lanen.service.studyplan.TblStudyPlanService;
import com.lanen.service.studyplan.TblTestIndexPlanService;
import com.lanen.util.messager.Messager;

public class BaseService {
	private static ApplicationContext context = null;
	private static UserService userService =null;
	private static RoleService roleService =null;
	private static PrivilegeService privilegeService =null;
	private static SystemService systemService =null;
	private static DepartmentService departmentService =null;
	private static UserRoleLogService userRoleLogService =null;
	private static RegulationService regulationService=null;
	private static ModuleService moduleService=null;
	//设备登记
	private static DictInstrumentService dictInstrumentService =null;
	private static TblInstrumentVerificationService tblInstrumentVerificationService=null;
	
	//试验计划 临检申请
	private static TblStudyPlanService tblStudyPlanService=null;
	private static TblClinicalTestReqService  tblClinicalTestReqService=null;
	private static DictHematService dictHematService=null;
	private static DictBioChemService dictBioChemService=null;
	private static DictBloodCoagService dictBloodCoagService=null;
	private static DictUrineService dictUrineService=null;
	private static BillNoService billNoService =null;
//	//标本接收
	private static  TblSpecimenService tblSpecimenService=null;     
	//通道号
	private static PassagewayService passagewayService=null;
	//数据接收
	private static TblClinicalTestDataService tblClinicalTestDataService=null;
	//数据修改痕迹
	private static TblTraceService tblTraceService =null;
	//系统日志
	private static TblLogService tblLogService =null;
	//版本控制
	private static VersionControlService versionControlService =null;
	//字典--标本类型
	private static DictSpecimenService dictSpecimenService =null;
	//试验计划-动物列表
	private static TblAnimalService tblAnimalService =null;
	//试验计划-临检指标
	private static TblTestIndexPlanService tblTestIndexPlanService =null;
	//报表编号
	private static DictReportNumberService dictReportNumberService =null;
	//检验编号
	private static PoolSpecimenCodeService poolSpecimenCodeService =null;
	
	//通知信息
	private static TblNotificationService tblNotificationService =null;
	
	private static GetIdService getIdService =null;
	
	private static DictAnimalTypeService dictAnimalTypeService =null;
	
	public BaseService(){
		try {
			context = new ClassPathXmlApplicationContext("rmi-client.xml");
			userService = (UserService) context.getBean("UserService");
			roleService = (RoleService) context.getBean("RoleService");
			privilegeService = (PrivilegeService) context.getBean("PrivilegeService");
			systemService = (SystemService) context.getBean("SystemService");
			departmentService = (DepartmentService) context.getBean("DepartmentService");
			userRoleLogService = (UserRoleLogService) context.getBean("UserRoleLogService");
			regulationService=(RegulationService)context.getBean("RegulationService");
			moduleService=(ModuleService)context.getBean("ModuleService");
			//设备登记
			dictInstrumentService = (DictInstrumentService) context.getBean("DictInstrumentService");
			tblInstrumentVerificationService=(TblInstrumentVerificationService) context.getBean("TblInstrumentVerificationService");
			
			//试验计划 临检申请
			tblStudyPlanService=(TblStudyPlanService) context.getBean("TblStudyPlanService");
			tblClinicalTestReqService=(TblClinicalTestReqService) context.getBean("TblClinicalTestReqService");
			dictHematService=(DictHematService) context.getBean("DictHematService");
			dictBioChemService=(DictBioChemService) context.getBean("DictBioChemService");
			 dictBloodCoagService=(DictBloodCoagService) context.getBean("DictBloodCoagService");
			 dictUrineService=(DictUrineService) context.getBean("DictUrineService");
			//获取流水号
			billNoService =(BillNoService) context.getBean("BillNoService");
			poolSpecimenCodeService =(PoolSpecimenCodeService) context.getBean("PoolSpecimenCodeService");
			//标本接收
			tblSpecimenService=(TblSpecimenService) context.getBean("TblSpecimenService");   
			//通道号
			passagewayService= (PassagewayService) context.getBean("PassagewayService");
			//数据接收
			tblClinicalTestDataService=(TblClinicalTestDataService) context.getBean("TblClinicalTestDataService");
			//数据修改痕迹
			tblTraceService =(TblTraceService) context.getBean("TblTraceService");
			//系统日志
			tblLogService =(TblLogService) context.getBean("TblLogService");
			//版本控制
			versionControlService =(VersionControlService) context.getBean("VersionControlService");
			//字典--标本类型
			dictSpecimenService =(DictSpecimenService) context.getBean("DictSpecimenService");
			tblAnimalService =(TblAnimalService) context.getBean("TblAnimalService");
			tblTestIndexPlanService =(TblTestIndexPlanService) context.getBean("TblTestIndexPlanService");
			dictReportNumberService =(DictReportNumberService) context.getBean("DictReportNumberService");
			tblNotificationService =(TblNotificationService) context.getBean("TblNotificationService");
			getIdService =(GetIdService) context.getBean("GetIdService");
			dictAnimalTypeService =(DictAnimalTypeService) context.getBean("DictAnimalTypeService");
		} catch (Exception e) {
//			Alert2.create("连接服务器失败");
			Messager.showWarnMessage("连接服务器失败！");
		}
	}
	public static DictSpecimenService getDictSpecimenService() {
		return dictSpecimenService;
	}
	public static void setDictSpecimenService(DictSpecimenService dictSpecimenService) {
		BaseService.dictSpecimenService = dictSpecimenService;
	}
	public static UserService getUserService() {
		return userService;
	}
	public static void setUserService(UserService userService) {
		BaseService.userService = userService;
	}
	public static RoleService getRoleService() {
		return roleService;
	}
	public static void setRoleService(RoleService roleService) {
		BaseService.roleService = roleService;
	}
	public static PrivilegeService getPrivilegeService() {
		return privilegeService;
	}
	public static void setPrivilegeService(PrivilegeService privilegeService) {
		BaseService.privilegeService = privilegeService;
	}
	public static DepartmentService getDepartmentService() {
		return departmentService;
	}
	public static void setDepartmentService(DepartmentService departmentService) {
		BaseService.departmentService = departmentService;
	}
	public static UserRoleLogService getUserRoleLogService() {
		return userRoleLogService;
	}
	public static void setUserRoleLogService(UserRoleLogService userRoleLogService) {
		BaseService.userRoleLogService = userRoleLogService;
	}
	public static RegulationService getRegulationService() {
		return regulationService;
	}
	public static void setRegulationService(RegulationService regulationService) {
		BaseService.regulationService = regulationService;
	}
	public static SystemService getSystemService() {
		return systemService;
	}
	public static void setSystemService(SystemService systemService) {
		BaseService.systemService = systemService;
	}
	public static ModuleService getModuleService() {
		return moduleService;
	}
	public static void setModuleService(ModuleService moduleService) {
		BaseService.moduleService = moduleService;
	}
	public static DictInstrumentService getDictInstrumentService() {
		return dictInstrumentService;
	}
	public static void setDictInstrumentService(DictInstrumentService dictInstrumentService) {
		BaseService.dictInstrumentService = dictInstrumentService;
	}
	public static TblInstrumentVerificationService getTblInstrumentVerificationService() {
		return tblInstrumentVerificationService;
	}
	public static void setTblInstrumentVerificationService(
			TblInstrumentVerificationService tblInstrumentVerificationService) {
		BaseService.tblInstrumentVerificationService = tblInstrumentVerificationService;
	}
	public static TblStudyPlanService getTblStudyPlanService() {
		return tblStudyPlanService;
	}
	public static void setTblStudyPlanService(TblStudyPlanService tblStudyPlanService) {
		BaseService.tblStudyPlanService = tblStudyPlanService;
	}
	public static TblClinicalTestReqService getTblClinicalTestReqService() {
		return tblClinicalTestReqService;
	}
	public static void setTblClinicalTestReqService(TblClinicalTestReqService tblClinicalTestReqService) {
		BaseService.tblClinicalTestReqService = tblClinicalTestReqService;
	}
	public static DictHematService getDictHematService() {
		return dictHematService;
	}
	public static void setDictHematService(DictHematService dictHematService) {
		BaseService.dictHematService = dictHematService;
	}
	public static DictBioChemService getDictBioChemService() {
		return dictBioChemService;
	}
	public static void setDictBioChemService(DictBioChemService dictBioChemService) {
		BaseService.dictBioChemService = dictBioChemService;
	}
	public static DictBloodCoagService getDictBloodCoagService() {
		return dictBloodCoagService;
	}
	public static void setDictBloodCoagService(DictBloodCoagService dictBloodCoagService) {
		BaseService.dictBloodCoagService = dictBloodCoagService;
	}
	public static DictUrineService getDictUrineService() {
		return dictUrineService;
	}
	public static void setDictUrineService(DictUrineService dictUrineService) {
		BaseService.dictUrineService = dictUrineService;
	}
//	public static BillNoService getBillNoService() {
//		return billNoService;
//	}
//	public static void setBillNoService(BillNoService billNoService) {
//		BaseService.billNoService = billNoService;
//	}
	public static TblSpecimenService getTblSpecimenService() {
		return tblSpecimenService;
	}
	public static void setTblSpecimenService(TblSpecimenService tblSpecimenService) {     
		BaseService.tblSpecimenService = tblSpecimenService;
	}
	public static PassagewayService getPassagewayService() {
		return passagewayService;
	}
	public static void setPassagewayService(PassagewayService passagewayService) {
		BaseService.passagewayService = passagewayService;
	}
	public static TblClinicalTestDataService getTblClinicalTestDataService() {
		return tblClinicalTestDataService;
	}
	public static void setTblClinicalTestDataService(
			TblClinicalTestDataService tblClinicalTestDataService) {
		BaseService.tblClinicalTestDataService = tblClinicalTestDataService;
	}
	public static TblTraceService getTblTraceService() {
		return tblTraceService;
	}
	public static void setTblTraceService(TblTraceService tblTraceService) {
		BaseService.tblTraceService = tblTraceService;
	}
	public static TblLogService getTblLogService() {
		return tblLogService;
	}
	public static void setTblLogService(TblLogService tblLogService) {
		BaseService.tblLogService = tblLogService;
	}
	public static VersionControlService getVersionControlService() {
		return versionControlService;
	}
	public static void setVersionControlService(VersionControlService versionControlService) {
		BaseService.versionControlService = versionControlService;
	}
	public static TblAnimalService getTblAnimalService() {
		return tblAnimalService;
	}
	public static void setTblAnimalService(TblAnimalService tblAnimalService) {
		BaseService.tblAnimalService = tblAnimalService;
	}
	public static TblTestIndexPlanService getTblTestIndexPlanService() {
		return tblTestIndexPlanService;
	}
	public static void setTblTestIndexPlanService(TblTestIndexPlanService tblTestIndexPlanService) {
		BaseService.tblTestIndexPlanService = tblTestIndexPlanService;
	}
	public static DictReportNumberService getDictReportNumberService() {
		return dictReportNumberService;
	}
	public static void setDictReportNumberService(DictReportNumberService dictReportNumberService) {
		BaseService.dictReportNumberService = dictReportNumberService;
	}
	public static PoolSpecimenCodeService getPoolSpecimenCodeService() {
		return poolSpecimenCodeService;
	}
	public static void setPoolSpecimenCodeService(PoolSpecimenCodeService poolSpecimenCodeService) {
		BaseService.poolSpecimenCodeService = poolSpecimenCodeService;
	}
	public static TblNotificationService getTblNotificationService() {
		return tblNotificationService;
	}
	public static void setTblNotificationService(TblNotificationService tblNotificationService) {
		BaseService.tblNotificationService = tblNotificationService;
	}
	public static GetIdService getGetIdService() {
		return getIdService;
	}
	public static void setGetIdService(GetIdService getIdService) {
		BaseService.getIdService = getIdService;
	}
	public static DictAnimalTypeService getDictAnimalTypeService() {
		return dictAnimalTypeService;
	}
	public static void setDictAnimalTypeService(DictAnimalTypeService dictAnimalTypeService) {
		BaseService.dictAnimalTypeService = dictAnimalTypeService;
	}

}
