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
import com.lanen.service.clinicaltest.BillNoService;
import com.lanen.service.clinicaltest.DictInstrumentService;
import com.lanen.service.clinicaltest.PassagewayService;
import com.lanen.service.clinicaltest.TblClinicalTestDataService;
import com.lanen.service.clinicaltest.TblInstrumentVerificationService;
import com.lanen.service.clinicaltest.TblSpecimenService;
import com.lanen.service.clinicaltest.TblTraceService;
import com.lanen.service.studyplan.DictBioChemService;
import com.lanen.service.studyplan.DictBloodCoagService;
import com.lanen.service.studyplan.DictHematService;
import com.lanen.service.studyplan.DictUrineService;
import com.lanen.service.studyplan.TblClinicalTestReqService;
import com.lanen.service.studyplan.TblStudyPlanService;
import com.lanen.util.messager.Messager;

public class CopyOfBaseService {
	private static ApplicationContext context = null;
	private static UserService userService = (UserService) context.getBean("UserService");
	private static RoleService roleService = (RoleService) context.getBean("RoleService");
	private static PrivilegeService privilegeService = (PrivilegeService) context.getBean("PrivilegeService");
	private static SystemService systemService = (SystemService) context.getBean("SystemService");
	private static DepartmentService departmentService = (DepartmentService) context.getBean("DepartmentService");
	private static UserRoleLogService userRoleLogService = (UserRoleLogService) context.getBean("UserRoleLogService");
	private static RegulationService regulationService=(RegulationService)context.getBean("RegulationService");
	private static ModuleService moduleService=(ModuleService)context.getBean("ModuleService");
	//设备登记
	private static DictInstrumentService dictInstrumentService = (DictInstrumentService) context.getBean("DictInstrumentService");
	private static TblInstrumentVerificationService tblInstrumentVerificationService=(TblInstrumentVerificationService) context.getBean("TblInstrumentVerificationService");
	
	//试验计划 临检申请
	private static TblStudyPlanService tblStudyPlanService=(TblStudyPlanService) context.getBean("TblStudyPlanService");
	private static TblClinicalTestReqService  tblClinicalTestReqService=(TblClinicalTestReqService) context.getBean("TblClinicalTestReqService");
	private static DictHematService dictHematService=(DictHematService) context.getBean("DictHematService");
	private static DictBioChemService dictBioChemService=(DictBioChemService) context.getBean("DictBioChemService");
	private static DictBloodCoagService dictBloodCoagService=(DictBloodCoagService) context.getBean("DictBloodCoagService");
	private static DictUrineService dictUrineService=(DictUrineService) context.getBean("DictUrineService");
	//获取流水号
	private static BillNoService billNoService =(BillNoService) context.getBean("BillNoService");
	//标本接收
	private static  TblSpecimenService tblSpecimenService=(TblSpecimenService) context.getBean("TblSpecimenService");
	//通道号
	private static PassagewayService passagewayService= (PassagewayService) context.getBean("PassagewayService");
	//数据接收
	private static TblClinicalTestDataService tblClinicalTestDataService=(TblClinicalTestDataService) context.getBean("TblClinicalTestDataService");
	//数据修改痕迹
	private static TblTraceService tblTraceService =(TblTraceService) context.getBean("TblTraceService");
	
	public CopyOfBaseService(){
		System.out.println("             ||||||||||||||||||||||             ");
		try {
			context = new ClassPathXmlApplicationContext("rmi-client.xml");
		} catch (Exception e) {
			System.out.println("+++++++++++++");
			Messager.showWarnMessage("连接服务器失败！");
		}
	}
	public static UserService getUserService() {
		return userService;
	}
	public static void setUserService(UserService userService) {
		CopyOfBaseService.userService = userService;
	}
	public static RoleService getRoleService() {
		return roleService;
	}
	public static void setRoleService(RoleService roleService) {
		CopyOfBaseService.roleService = roleService;
	}
	public static PrivilegeService getPrivilegeService() {
		return privilegeService;
	}
	public static void setPrivilegeService(PrivilegeService privilegeService) {
		CopyOfBaseService.privilegeService = privilegeService;
	}
	public static DepartmentService getDepartmentService() {
		return departmentService;
	}
	public static void setDepartmentService(DepartmentService departmentService) {
		CopyOfBaseService.departmentService = departmentService;
	}
	public static UserRoleLogService getUserRoleLogService() {
		return userRoleLogService;
	}
	public static void setUserRoleLogService(UserRoleLogService userRoleLogService) {
		CopyOfBaseService.userRoleLogService = userRoleLogService;
	}
	public static RegulationService getRegulationService() {
		return regulationService;
	}
	public static void setRegulationService(RegulationService regulationService) {
		CopyOfBaseService.regulationService = regulationService;
	}
	public static SystemService getSystemService() {
		return systemService;
	}
	public static void setSystemService(SystemService systemService) {
		CopyOfBaseService.systemService = systemService;
	}
	public static ModuleService getModuleService() {
		return moduleService;
	}
	public static void setModuleService(ModuleService moduleService) {
		CopyOfBaseService.moduleService = moduleService;
	}
	public static DictInstrumentService getDictInstrumentService() {
		return dictInstrumentService;
	}
	public static void setDictInstrumentService(DictInstrumentService dictInstrumentService) {
		CopyOfBaseService.dictInstrumentService = dictInstrumentService;
	}
	public static TblInstrumentVerificationService getTblInstrumentVerificationService() {
		return tblInstrumentVerificationService;
	}
	public static void setTblInstrumentVerificationService(
			TblInstrumentVerificationService tblInstrumentVerificationService) {
		CopyOfBaseService.tblInstrumentVerificationService = tblInstrumentVerificationService;
	}
	public static TblStudyPlanService getTblStudyPlanService() {
		return tblStudyPlanService;
	}
	public static void setTblStudyPlanService(TblStudyPlanService tblStudyPlanService) {
		CopyOfBaseService.tblStudyPlanService = tblStudyPlanService;
	}
	public static TblClinicalTestReqService getTblClinicalTestReqService() {
		return tblClinicalTestReqService;
	}
	public static void setTblClinicalTestReqService(TblClinicalTestReqService tblClinicalTestReqService) {
		CopyOfBaseService.tblClinicalTestReqService = tblClinicalTestReqService;
	}
	public static DictHematService getDictHematService() {
		return dictHematService;
	}
	public static void setDictHematService(DictHematService dictHematService) {
		CopyOfBaseService.dictHematService = dictHematService;
	}
	public static DictBioChemService getDictBioChemService() {
		return dictBioChemService;
	}
	public static void setDictBioChemService(DictBioChemService dictBioChemService) {
		CopyOfBaseService.dictBioChemService = dictBioChemService;
	}
	public static DictBloodCoagService getDictBloodCoagService() {
		return dictBloodCoagService;
	}
	public static void setDictBloodCoagService(DictBloodCoagService dictBloodCoagService) {
		CopyOfBaseService.dictBloodCoagService = dictBloodCoagService;
	}
	public static DictUrineService getDictUrineService() {
		return dictUrineService;
	}
	public static void setDictUrineService(DictUrineService dictUrineService) {
		CopyOfBaseService.dictUrineService = dictUrineService;
	}
	public static BillNoService getBillNoService() {
		return billNoService;
	}
	public static void setBillNoService(BillNoService billNoService) {
		CopyOfBaseService.billNoService = billNoService;
	}
	public static TblSpecimenService getTblSpecimenService() {
		return tblSpecimenService;
	}
	public static void setTblSpecimenService(TblSpecimenService tblSpecimenService) {
		CopyOfBaseService.tblSpecimenService = tblSpecimenService;
	}
	public static PassagewayService getPassagewayService() {
		return passagewayService;
	}
	public static void setPassagewayService(PassagewayService passagewayService) {
		CopyOfBaseService.passagewayService = passagewayService;
	}
	public static TblClinicalTestDataService getTblClinicalTestDataService() {
		return tblClinicalTestDataService;
	}
	public static void setTblClinicalTestDataService(
			TblClinicalTestDataService tblClinicalTestDataService) {
		CopyOfBaseService.tblClinicalTestDataService = tblClinicalTestDataService;
	}
	public static TblTraceService getTblTraceService() {
		return tblTraceService;
	}
	public static void setTblTraceService(TblTraceService tblTraceService) {
		CopyOfBaseService.tblTraceService = tblTraceService;
	}
	
	
	

}
