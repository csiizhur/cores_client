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
import com.lanen.service.clinicaltest.TblESService;
import com.lanen.service.clinicaltest.TblLogService;
import com.lanen.service.clinicaltest.TblTraceService;
import com.lanen.service.quarantine.DictAnimalDeathService;
import com.lanen.service.quarantine.DictGeneralObservationService;
import com.lanen.service.quarantine.DictOutputSettingsService;
import com.lanen.service.quarantine.DictPhysicalExamItemService;
import com.lanen.service.quarantine.TblAnimalHouseService;
import com.lanen.service.quarantine.TblAnimalRecIndexService;
import com.lanen.service.quarantine.TblAnimalRecListService;
import com.lanen.service.quarantine.TblBalService;
import com.lanen.service.quarantine.TblHostBalService;
import com.lanen.service.quarantine.TblQRRequestService;
import com.lanen.service.quarantine.TblQRRequestStudyNoService;
import com.lanen.service.quarantine.TblRoomAndAniTypeService;
import com.lanen.service.quarantine.tblsession.TblAniHandoverService;
import com.lanen.service.quarantine.tblsession.TblAniResiteService;
import com.lanen.service.quarantine.tblsession.TblAnimalDeathService;
import com.lanen.service.quarantine.tblsession.TblBodyWeightService;
import com.lanen.service.quarantine.tblsession.TblGeneralObservationService;
import com.lanen.service.quarantine.tblsession.TblPhyExamService;
import com.lanen.service.quarantine.tblsession.TblSessionAnimalsService;
import com.lanen.service.quarantine.tblsession.TblSessionService;
import com.lanen.service.studyplan.DictAnimalStrainService;
import com.lanen.service.studyplan.DictAnimalTypeService;

public class BaseService {
	private  ApplicationContext context = null;//权限
	private  ApplicationContext context2 = null;//试验计划\临检
	private  ApplicationContext context3 = null;//检疫
	private static UserService userService =null;
	private static RoleService roleService =null;
	private static PrivilegeService privilegeService =null;
	private static SystemService systemService =null;
	private static DepartmentService departmentService =null;
	private static UserRoleLogService userRoleLogService =null;
	private static RegulationService regulationService=null;
	private static ModuleService moduleService=null;
	private static TblLogService tblLogService = null;
	private static TblESService tblESService = null;
	private static TblTraceService tblTraceservice =null;
	
	private static DictAnimalTypeService dictAnimalTypeService = null;
	private static DictAnimalStrainService dictAnimalStrainService = null;
	
	private static TblQRRequestService tblQRRequestService = null;
	private static TblQRRequestStudyNoService tblQRRequestStudyNoService = null;
	private static TblAnimalRecIndexService tblAnimalRecIndexService = null;
	private static TblAnimalRecListService tblAnimalRecListService = null;
	private static DictGeneralObservationService dictGeneralObservationService = null;
	private static DictPhysicalExamItemService dictPhysicalExamItemService = null;
	private static DictAnimalDeathService dictAnimalDeathService = null;
	private static TblRoomAndAniTypeService tblRoomAndAniTypeService = null;
	private static TblAnimalHouseService tblAnimalHouseService = null;
	private static TblSessionService tblSessionService = null;
	private static TblSessionAnimalsService tblSessionAnimalsService = null;
	private static TblBodyWeightService tblBodyWeightService = null;
	private static TblBalService tblBalService = null;
	private static TblHostBalService tblHostBalService = null;
	private static DictOutputSettingsService dictOutputSettingsService = null;
	private static TblGeneralObservationService tblGeneralObservationService = null;
	private static TblPhyExamService tblPhyExamService = null;
	private static TblAnimalDeathService tblAnimalDeathService = null;
	private static TblAniResiteService tblAniResiteService = null;
	private static TblAniHandoverService tblAniHandoverService = null;
	
	
	public BaseService(){
		try {
			context = new ClassPathXmlApplicationContext("rmi-userprivilege.xml");
			context2 = new ClassPathXmlApplicationContext("rmi-study.xml");
			context3 = new ClassPathXmlApplicationContext("rmi-quarantine.xml");
			//权限
			userService = (UserService) context.getBean("UserService");
			roleService = (RoleService) context.getBean("RoleService");
			privilegeService = (PrivilegeService) context.getBean("PrivilegeService");
			systemService = (SystemService) context.getBean("SystemService");
			departmentService = (DepartmentService) context.getBean("DepartmentService");
			userRoleLogService = (UserRoleLogService) context.getBean("UserRoleLogService");
			regulationService=(RegulationService)context.getBean("RegulationService");
			moduleService=(ModuleService)context.getBean("ModuleService");
			tblLogService= (TblLogService) context.getBean("TblLogService");
			tblESService= (TblESService) context.getBean("tblESService");
			tblTraceservice =(TblTraceService) context.getBean("TblTraceService");
			
			//试验计划、临检
			dictAnimalTypeService=(DictAnimalTypeService) context2.getBean("DictAnimalTypeService");
			dictAnimalStrainService=(DictAnimalStrainService) context2.getBean("DictAnimalStrainService");
			
			//检疫
			tblQRRequestService = (TblQRRequestService) context3.getBean("TblQRRequestService");
			tblQRRequestStudyNoService = (TblQRRequestStudyNoService) context3.getBean("TblQRRequestStudyNoService");
			tblAnimalRecIndexService = (TblAnimalRecIndexService) context3.getBean("TblAnimalRecIndexService");
			tblAnimalRecListService = (TblAnimalRecListService) context3.getBean("TblAnimalRecListService");
			dictGeneralObservationService = (DictGeneralObservationService) context3.getBean("DictGeneralObservationService");
			dictPhysicalExamItemService = (DictPhysicalExamItemService) context3.getBean("DictPhysicalExamItemService");
			dictAnimalDeathService = (DictAnimalDeathService) context3.getBean("DictAnimalDeathService");
			tblRoomAndAniTypeService = (TblRoomAndAniTypeService) context3.getBean("TblRoomAndAniTypeService");
			tblAnimalHouseService = (TblAnimalHouseService) context3.getBean("TblAnimalHouseService");
			tblSessionService = (TblSessionService) context3.getBean("TblSessionService");
			tblSessionAnimalsService = (TblSessionAnimalsService) context3.getBean("TblSessionAnimalsService");
			tblBodyWeightService = (TblBodyWeightService) context3.getBean("TblBodyWeightService");
			tblBalService = (TblBalService) context3.getBean("TblBalService");
			tblHostBalService = (TblHostBalService) context3.getBean("TblHostBalService");
			dictOutputSettingsService = (DictOutputSettingsService) context3.getBean("DictOutputSettingsService");
			tblGeneralObservationService = (TblGeneralObservationService) context3.getBean("TblGeneralObservationService");
			tblPhyExamService = (TblPhyExamService) context3.getBean("TblPhyExamService");
			tblAnimalDeathService = (TblAnimalDeathService) context3.getBean("TblAnimalDeathService");
			tblAniResiteService = (TblAniResiteService) context3.getBean("TblAniResiteService");
			tblAniHandoverService = (TblAniHandoverService) context3.getBean("TblAniHandoverService");
		} catch (Exception e) {
//			Alert2.create("连接服务器失败");
			e.printStackTrace();
		}
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
	public static TblLogService getTblLogService() {
		return tblLogService;
	}
	public static void setTblLogService(TblLogService tblLogService) {
		BaseService.tblLogService = tblLogService;
	}
	public static DictAnimalTypeService getDictAnimalTypeService() {
		return dictAnimalTypeService;
	}
	public static void setDictAnimalTypeService(DictAnimalTypeService dictAnimalTypeService) {
		BaseService.dictAnimalTypeService = dictAnimalTypeService;
	}
	public static DictAnimalStrainService getDictAnimalStrainService() {
		return dictAnimalStrainService;
	}
	public static void setDictAnimalStrainService(DictAnimalStrainService dictAnimalStrainService) {
		BaseService.dictAnimalStrainService = dictAnimalStrainService;
	}
	public static TblQRRequestService getTblQRRequestService() {
		return tblQRRequestService;
	}
	public static void setTblQRRequestService(TblQRRequestService tblQRRequestService) {
		BaseService.tblQRRequestService = tblQRRequestService;
	}
	public static TblQRRequestStudyNoService getTblQRRequestStudyNoService() {
		return tblQRRequestStudyNoService;
	}
	public static void setTblQRRequestStudyNoService(
			TblQRRequestStudyNoService tblQRRequestStudyNoService) {
		BaseService.tblQRRequestStudyNoService = tblQRRequestStudyNoService;
	}
	public static TblAnimalRecIndexService getTblAnimalRecIndexService() {
		return tblAnimalRecIndexService;
	}
	public static void setTblAnimalRecIndexService(TblAnimalRecIndexService tblAnimalRecIndexService) {
		BaseService.tblAnimalRecIndexService = tblAnimalRecIndexService;
	}
	public static TblESService getTblESService() {
		return tblESService;
	}
	public static void setTblESService(TblESService tblESService) {
		BaseService.tblESService = tblESService;
	}
	public static TblAnimalRecListService getTblAnimalRecListService() {
		return tblAnimalRecListService;
	}
	public static void setTblAnimalRecListService(TblAnimalRecListService tblAnimalRecListService) {
		BaseService.tblAnimalRecListService = tblAnimalRecListService;
	}
	public static TblTraceService getTblTraceservice() {
		return tblTraceservice;
	}
	public static void setTblTraceservice(TblTraceService tblTraceservice) {
		BaseService.tblTraceservice = tblTraceservice;
	}
	public static DictGeneralObservationService getDictGeneralObservationService() {
		return dictGeneralObservationService;
	}
	public static void setDictGeneralObservationService(
			DictGeneralObservationService dictGeneralObservationService) {
		BaseService.dictGeneralObservationService = dictGeneralObservationService;
	}
	public static DictPhysicalExamItemService getDictPhysicalExamItemService() {
		return dictPhysicalExamItemService;
	}
	public static void setDictPhysicalExamItemService(
			DictPhysicalExamItemService dictPhysicalExamItemService) {
		BaseService.dictPhysicalExamItemService = dictPhysicalExamItemService;
	}
	public static DictAnimalDeathService getDictAnimalDeathService() {
		return dictAnimalDeathService;
	}
	public static void setDictAnimalDeathService(DictAnimalDeathService dictAnimalDeathService) {
		BaseService.dictAnimalDeathService = dictAnimalDeathService;
	}
	public static TblRoomAndAniTypeService getTblRoomAndAniTypeService() {
		return tblRoomAndAniTypeService;
	}
	public static void setTblRoomAndAniTypeService(TblRoomAndAniTypeService tblRoomAndAniTypeService) {
		BaseService.tblRoomAndAniTypeService = tblRoomAndAniTypeService;
	}
	public static TblAnimalHouseService getTblAnimalHouseService() {
		return tblAnimalHouseService;
	}
	public static void setTblAnimalHouseService(TblAnimalHouseService tblAnimalHouseService) {
		BaseService.tblAnimalHouseService = tblAnimalHouseService;
	}
	public static TblSessionService getTblSessionService() {
		return tblSessionService;
	}
	public static void setTblSessionService(TblSessionService tblSessionService) {
		BaseService.tblSessionService = tblSessionService;
	}
	public static TblSessionAnimalsService getTblSessionAnimalsService() {
		return tblSessionAnimalsService;
	}
	public static void setTblSessionAnimalsService(TblSessionAnimalsService tblSessionAnimalsService) {
		BaseService.tblSessionAnimalsService = tblSessionAnimalsService;
	}
	public static TblBodyWeightService getTblBodyWeightService() {
		return tblBodyWeightService;
	}
	public static void setTblBodyWeightService(TblBodyWeightService tblBodyWeightService) {
		BaseService.tblBodyWeightService = tblBodyWeightService;
	}
	public static TblBalService getTblBalService() {
		return tblBalService;
	}
	public static void setTblBalService(TblBalService tblBalService) {
		BaseService.tblBalService = tblBalService;
	}
	public static TblHostBalService getTblHostBalService() {
		return tblHostBalService;
	}
	public static void setTblHostBalService(TblHostBalService tblHostBalService) {
		BaseService.tblHostBalService = tblHostBalService;
	}
	public static DictOutputSettingsService getDictOutputSettingsService() {
		return dictOutputSettingsService;
	}
	public static void setDictOutputSettingsService(DictOutputSettingsService dictOutputSettingsService) {
		BaseService.dictOutputSettingsService = dictOutputSettingsService;
	}
	public static TblGeneralObservationService getTblGeneralObservationService() {
		return tblGeneralObservationService;
	}
	public static void setTblGeneralObservationService(
			TblGeneralObservationService tblGeneralObservationService) {
		BaseService.tblGeneralObservationService = tblGeneralObservationService;
	}
	public static TblPhyExamService getTblPhyExamService() {
		return tblPhyExamService;
	}
	public static void setTblPhyExamService(TblPhyExamService tblPhyExamService) {
		BaseService.tblPhyExamService = tblPhyExamService;
	}
	public static TblAnimalDeathService getTblAnimalDeathService() {
		return tblAnimalDeathService;
	}
	public static void setTblAnimalDeathService(TblAnimalDeathService tblAnimalDeathService) {
		BaseService.tblAnimalDeathService = tblAnimalDeathService;
	}
	public static TblAniResiteService getTblAniResiteService() {
		return tblAniResiteService;
	}
	public static void setTblAniResiteService(TblAniResiteService tblAniResiteService) {
		BaseService.tblAniResiteService = tblAniResiteService;
	}
	public static TblAniHandoverService getTblAniHandoverService() {
		return tblAniHandoverService;
	}
	public static void setTblAniHandoverService(TblAniHandoverService tblAniHandoverService) {
		BaseService.tblAniHandoverService = tblAniHandoverService;
	}
	
	

}
