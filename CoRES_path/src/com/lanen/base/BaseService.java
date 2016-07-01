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
import com.lanen.service.path.DictLevelService;
import com.lanen.service.path.DictPathCommonService;
import com.lanen.service.path.DictVisceraService;
import com.lanen.service.path.TblAnatomyAnimalService;
import com.lanen.service.path.TblAnatomyCheckEditService;
import com.lanen.service.path.TblAnatomyCheckHisService;
import com.lanen.service.path.TblAnatomyCheckService;
import com.lanen.service.path.TblAnatomyReqAnimalListService;
import com.lanen.service.path.TblAnatomyReqAttachedVisceraService;
import com.lanen.service.path.TblAnatomyReqPathPlanCheckService;
import com.lanen.service.path.TblAnatomyReqService;
import com.lanen.service.path.TblAnatomyReqVisceraWeighService;
import com.lanen.service.path.TblAnatomyTaskService;
import com.lanen.service.path.TblAnimalTargetOrganService;
import com.lanen.service.path.TblBalCalibrationIndexService;
import com.lanen.service.path.TblBalCalibrationPointService;
import com.lanen.service.path.TblBalCalibrationService;
import com.lanen.service.path.TblBalConnectService;
import com.lanen.service.path.TblBalRegService;
import com.lanen.service.path.TblChipReaderService;
import com.lanen.service.path.TblCounterWeightService;
import com.lanen.service.path.TblCounterpoiseService;
import com.lanen.service.path.TblDeadAnimalDeathReasonService;
import com.lanen.service.path.TblHistopathCheckContentService;
import com.lanen.service.path.TblHistopathCheckService;
import com.lanen.service.path.TblPathPlanVisceraWeighService;
import com.lanen.service.path.TblPathSessionService;
import com.lanen.service.path.TblPathStudyIndexService;
import com.lanen.service.path.TblSuperficialTumorVisceraService;
import com.lanen.service.path.TblTissueSliceBatchService;
import com.lanen.service.path.TblTissueSliceIndexService;
import com.lanen.service.path.TblVisceraFixedService;
import com.lanen.service.path.TblVisceraMissingService;
import com.lanen.service.path.TblVisceraWeightHisService;
import com.lanen.service.path.TblVisceraWeightService;
import com.lanen.service.studyplan.DictAnimalStrainService;
import com.lanen.service.studyplan.DictAnimalTypeService;
import com.lanen.service.studyplan.DictReportNumberService;
import com.lanen.service.studyplan.TblAnimalService;
import com.lanen.service.studyplan.TblDoseSettingService;
import com.lanen.service.studyplan.TblStudyPlanService;

public class BaseService {
	private static BaseService instance;
	
	private  ApplicationContext context = null;//权限
	private  ApplicationContext context2 = null;//study
	private  UserService userService =null;
	private  RoleService roleService =null;
	private  PrivilegeService privilegeService =null;
	private  SystemService systemService =null;
	private  DepartmentService departmentService =null;
	private  UserRoleLogService userRoleLogService =null;
	private  RegulationService regulationService=null;
	private  ModuleService moduleService=null;
	private  TblLogService tblLogService = null;
	private  TblESService tblESService = null;
	private  TblTraceService tblTraceservice =null;
	
	private  DictAnimalTypeService dictAnimalTypeService = null;
	private  DictAnimalStrainService dictAnimalStrainService = null;
	private  TblStudyPlanService tblStudyPlanService = null;
	private  TblAnimalService tblAnimalService = null;
	private  TblAnatomyReqService tblAnatomyReqService = null;
	private  TblAnatomyTaskService tblAnatomyTaskService = null;
	private  TblPathSessionService tblPathSessionService = null;
	private  TblAnatomyAnimalService tblAnatomyAnimalService = null;
	private  TblAnatomyCheckService tblAnatomyCheckService = null;
	private  DictPathCommonService dictPathCommonService = null;
	private  TblBalCalibrationIndexService tblBalCalibrationIndexService=null;
	private  TblBalRegService tblBalRegService = null;
	private  TblBalConnectService tblBalConnectService = null;
	private  TblVisceraFixedService tblVisceraFixedService = null;
	private  TblBalCalibrationPointService tblBalCalibrationPointService = null;
	private  TblCounterpoiseService tblCounterpoiseService = null;
	private  TblBalCalibrationService tblBalCalibrationService=null;
	private  TblChipReaderService tblChipReaderService = null;
	private  TblVisceraWeightService tblVisceraWeightService = null;
	private  TblAnatomyReqAnimalListService tblAnatomyReqAnimalListService = null;
	private  TblVisceraMissingService tblVisceraMissingService = null;
	private  TblCounterWeightService tblCounterWeightService = null;
	private  TblAnatomyCheckHisService tblAnatomyCheckHisService = null;
	private  TblVisceraWeightHisService tblVisceraWeightHisService = null;
	private  TblPathPlanVisceraWeighService tblPathPlanVisceraWeighService = null;
	private  DictReportNumberService dictReportNumberService = null;
	private  TblTissueSliceIndexService tblTissueSliceIndexService = null;
	private  DictVisceraService dictVisceraService = null;
	private  TblDoseSettingService tblDoseSettingService = null;
	private  TblHistopathCheckService tblHistopathCheckService = null;
	private  TblAnatomyReqPathPlanCheckService tblAnatomyReqPathPlanCheckService = null;
	private  TblAnatomyReqVisceraWeighService tblAnatomyReqVisceraWeighService = null;
	private  TblAnatomyReqAttachedVisceraService tblAnatomyReqAttachedVisceraService = null;
	private  TblAnatomyCheckEditService tblAnatomyCheckEditService = null;
	private  TblDeadAnimalDeathReasonService tblDeadAnimalDeathReasonService = null;
	private  TblAnimalTargetOrganService tblAnimalTargetOrganService = null;
	private  TblTissueSliceBatchService tblTissueSliceBatchService = null;
	private  TblPathStudyIndexService tblPathStudyIndexService = null;
	private  TblHistopathCheckContentService tblHistopathCheckContentService = null;
	private  TblSuperficialTumorVisceraService tblSuperficialTumorVisceraService = null;
	private  DictLevelService dictLevelService = null;
	
	
	public static BaseService getInstance(){
		if(null == instance){
			instance = new BaseService();
		}
		return instance;
	}
	
	private BaseService(){
		try {
			context = new ClassPathXmlApplicationContext("rmi-userprivilege.xml");
			context2 = new ClassPathXmlApplicationContext("rmi-study.xml");
			
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
			
			//试验计划、病理
			dictAnimalTypeService=(DictAnimalTypeService) context2.getBean("DictAnimalTypeService");
			dictAnimalStrainService=(DictAnimalStrainService) context2.getBean("DictAnimalStrainService");
			tblStudyPlanService=(TblStudyPlanService) context2.getBean("TblStudyPlanService");
			tblAnimalService=(TblAnimalService) context2.getBean("TblAnimalService");
			tblAnatomyReqService=(TblAnatomyReqService) context2.getBean("TblAnatomyReqService");
			tblAnatomyTaskService=(TblAnatomyTaskService) context2.getBean("TblAnatomyTaskService");
			tblPathSessionService=(TblPathSessionService) context2.getBean("TblPathSessionService");
			tblAnatomyAnimalService=(TblAnatomyAnimalService) context2.getBean("TblAnatomyAnimalService");
			tblAnatomyCheckService=(TblAnatomyCheckService) context2.getBean("TblAnatomyCheckService");
			dictPathCommonService=(DictPathCommonService) context2.getBean("DictPathCommonService");
			tblBalCalibrationIndexService=(TblBalCalibrationIndexService)context2.getBean("TblBalCalibrationIndexService");
			tblBalRegService = (TblBalRegService)context2.getBean("TblBalRegService");
			tblBalConnectService = (TblBalConnectService)context2.getBean("TblBalConnectService");
			tblVisceraFixedService = (TblVisceraFixedService)context2.getBean("TblVisceraFixedService");
			tblBalCalibrationPointService = (TblBalCalibrationPointService)context2.getBean("TblBalCalibrationPointService");
			tblCounterpoiseService = (TblCounterpoiseService)context2.getBean("TblCounterpoiseService");
			tblBalCalibrationService=(TblBalCalibrationService)context2.getBean("TblBalCalibrationService");
			tblChipReaderService = (TblChipReaderService)context2.getBean("TblChipReaderService");
			tblVisceraWeightService = (TblVisceraWeightService)context2.getBean("TblVisceraWeightService");
			tblAnatomyReqAnimalListService = (TblAnatomyReqAnimalListService)context2.getBean("TblAnatomyReqAnimalListService");
			tblVisceraMissingService = (TblVisceraMissingService)context2.getBean("TblVisceraMissingService");
			tblCounterWeightService = (TblCounterWeightService)context2.getBean("TblCounterWeightService");
			tblAnatomyCheckHisService =  (TblAnatomyCheckHisService)context2.getBean("TblAnatomyCheckHisService");
			tblVisceraWeightHisService = (TblVisceraWeightHisService)context2.getBean("TblVisceraWeightHisService");
			tblPathPlanVisceraWeighService = (TblPathPlanVisceraWeighService)context2.getBean("TblPathPlanVisceraWeighService");
			dictReportNumberService = (DictReportNumberService)context2.getBean("DictReportNumberService");
			tblTissueSliceIndexService = (TblTissueSliceIndexService)context2.getBean("TblTissueSliceIndexService");
			dictVisceraService = (DictVisceraService)context2.getBean("DictVisceraService");
			tblDoseSettingService = (TblDoseSettingService)context2.getBean("TblDoseSettingService");
			tblHistopathCheckService = (TblHistopathCheckService)context2.getBean("TblHistopathCheckService");
			tblAnatomyReqPathPlanCheckService = (TblAnatomyReqPathPlanCheckService)context2.getBean("TblAnatomyReqPathPlanCheckService");
			tblAnatomyReqVisceraWeighService = (TblAnatomyReqVisceraWeighService)context2.getBean("TblAnatomyReqVisceraWeighService");
			tblAnatomyReqAttachedVisceraService = (TblAnatomyReqAttachedVisceraService)context2.getBean("TblAnatomyReqAttachedVisceraService");
			tblAnatomyCheckEditService = (TblAnatomyCheckEditService)context2.getBean("TblAnatomyCheckEditService");
			tblDeadAnimalDeathReasonService = (TblDeadAnimalDeathReasonService)context2.getBean("TblDeadAnimalDeathReasonService");
			tblAnimalTargetOrganService = (TblAnimalTargetOrganService)context2.getBean("TblAnimalTargetOrganService");
			tblTissueSliceBatchService = (TblTissueSliceBatchService)context2.getBean("TblTissueSliceBatchService");
			tblPathStudyIndexService = (TblPathStudyIndexService)context2.getBean("TblPathStudyIndexService");
			tblHistopathCheckContentService = (TblHistopathCheckContentService)context2.getBean("TblHistopathCheckContentService");
			tblSuperficialTumorVisceraService = (TblSuperficialTumorVisceraService)context2.getBean("TblSuperficialTumorVisceraService");
			dictLevelService = (DictLevelService)context2.getBean("DictLevelService");
		} catch (Exception e) {
//			Platform.runLater(new Runnable() {
//				
//				@Override
//				public void run() {
//					Messager.showErrorMessage("连接服务器失败！");
//				}
//			});
			e.printStackTrace();
		}
	}


	public  TblAnatomyTaskService getTblAnatomyTaskService() {
		return tblAnatomyTaskService;
	}


	public  void setTblAnatomyTaskService(TblAnatomyTaskService tblAnatomyTaskService) {
		this.tblAnatomyTaskService = tblAnatomyTaskService;
	}


	public  TblAnimalService getTblAnimalService() {
		return tblAnimalService;
	}


	public  void setTblAnimalService(TblAnimalService tblAnimalService) {
		this.tblAnimalService = tblAnimalService;
	}


	public  UserService getUserService() {
		return userService;
	}


	public  void setUserService(UserService userService) {
		this.userService = userService;
	}


	public  RoleService getRoleService() {
		return roleService;
	}


	public  void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	public  PrivilegeService getPrivilegeService() {
		return privilegeService;
	}


	public  void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}


	public  SystemService getSystemService() {
		return systemService;
	}


	public  void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}


	public  DepartmentService getDepartmentService() {
		return departmentService;
	}


	public  void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}


	public  UserRoleLogService getUserRoleLogService() {
		return userRoleLogService;
	}


	public  void setUserRoleLogService(UserRoleLogService userRoleLogService) {
		this.userRoleLogService = userRoleLogService;
	}


	public  RegulationService getRegulationService() {
		return regulationService;
	}


	public  void setRegulationService(RegulationService regulationService) {
		this.regulationService = regulationService;
	}


	public  ModuleService getModuleService() {
		return moduleService;
	}


	public  void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}


	public  TblLogService getTblLogService() {
		return tblLogService;
	}


	public  void setTblLogService(TblLogService tblLogService) {
		this.tblLogService = tblLogService;
	}


	public  TblESService getTblESService() {
		return tblESService;
	}


	public  void setTblESService(TblESService tblESService) {
		this.tblESService = tblESService;
	}


	public  TblTraceService getTblTraceservice() {
		return tblTraceservice;
	}


	public  void setTblTraceservice(TblTraceService tblTraceservice) {
		this.tblTraceservice = tblTraceservice;
	}


	public  DictAnimalTypeService getDictAnimalTypeService() {
		return dictAnimalTypeService;
	}


	public  void setDictAnimalTypeService(DictAnimalTypeService dictAnimalTypeService) {
		this.dictAnimalTypeService = dictAnimalTypeService;
	}


	public  DictAnimalStrainService getDictAnimalStrainService() {
		return dictAnimalStrainService;
	}


	public  void setDictAnimalStrainService(DictAnimalStrainService dictAnimalStrainService) {
		this.dictAnimalStrainService = dictAnimalStrainService;
	}


	public  TblAnatomyReqService getTblAnatomyReqService() {
		return tblAnatomyReqService;
	}


	public  void setTblAnatomyReqService(TblAnatomyReqService tblAnatomyReqService) {
		this.tblAnatomyReqService = tblAnatomyReqService;
	}


	public  TblStudyPlanService getTblStudyPlanService() {
		return tblStudyPlanService;
	}


	public  void setTblStudyPlanService(TblStudyPlanService tblStudyPlanService) {
		this.tblStudyPlanService = tblStudyPlanService;
	}


	public  TblPathSessionService getTblPathSessionService() {
		return tblPathSessionService;
	}


	public  void setTblPathSessionService(TblPathSessionService tblPathSessionService) {
		this.tblPathSessionService = tblPathSessionService;
	}


	public  TblAnatomyAnimalService getTblAnatomyAnimalService() {
		return tblAnatomyAnimalService;
	}


	public  void setTblAnatomyAnimalService(TblAnatomyAnimalService tblAnatomyAnimalService) {
		this.tblAnatomyAnimalService = tblAnatomyAnimalService;
	}


	public  TblAnatomyCheckService getTblAnatomyCheckService() {
		return tblAnatomyCheckService;
	}


	public  void setTblAnatomyCheckService(TblAnatomyCheckService tblAnatomyCheckService) {
		this.tblAnatomyCheckService = tblAnatomyCheckService;
	}


	public  DictPathCommonService getDictPathCommonService() {
		return dictPathCommonService;
	}


	public  void setDictPathCommonService(DictPathCommonService dictPathCommonService) {
		this.dictPathCommonService = dictPathCommonService;
	}


	public  TblBalCalibrationIndexService getTblBalCalibrationIndexService() {
		return tblBalCalibrationIndexService;
	}


	public  void setTblBalCalibrationIndexService(
			TblBalCalibrationIndexService tblBalCalibrationIndexService) {
		this.tblBalCalibrationIndexService = tblBalCalibrationIndexService;
	}


	public  TblBalRegService getTblBalRegService() {
		return tblBalRegService;
	}


	public  void setTblBalRegService(TblBalRegService tblBalRegService) {
		this.tblBalRegService = tblBalRegService;
	}


	public  TblBalConnectService getTblBalConnectService() {
		return tblBalConnectService;
	}


	public  void setTblBalConnectService(
			TblBalConnectService tblBalConnectService) {
		this.tblBalConnectService = tblBalConnectService;
	}


	public  TblVisceraFixedService getTblVisceraFixedService() {
		return tblVisceraFixedService;
	}


	public  void setTblVisceraFixedService(TblVisceraFixedService tblVisceraFixedService) {
		this.tblVisceraFixedService = tblVisceraFixedService;
	}


	public  TblBalCalibrationPointService getTblBalCalibrationPointService() {
		return tblBalCalibrationPointService;
	}


	public  void setTblBalCalibrationPointService(
			TblBalCalibrationPointService tblBalCalibrationPointService) {
		this.tblBalCalibrationPointService = tblBalCalibrationPointService;
	}


	public  TblCounterpoiseService getTblCounterpoiseService() {
		return tblCounterpoiseService;
	}


	public  void setTblCounterpoiseService(
			TblCounterpoiseService tblCounterpoiseService) {
		this.tblCounterpoiseService = tblCounterpoiseService;
	}


	public  TblBalCalibrationService getTblBalCalibrationService() {
		return tblBalCalibrationService;
	}


	public  void setTblBalCalibrationService(
			TblBalCalibrationService tblBalCalibrationService) {
		this.tblBalCalibrationService = tblBalCalibrationService;
	}


	public  TblChipReaderService getTblChipReaderService() {
		return tblChipReaderService;
	}


	public  void setTblChipReaderService(
			TblChipReaderService tblChipReaderService) {
		this.tblChipReaderService = tblChipReaderService;
	}


	public  TblVisceraWeightService getTblVisceraWeightService() {
		return tblVisceraWeightService;
	}


	public  void setTblVisceraWeightService(TblVisceraWeightService tblVisceraWeightService) {
		this.tblVisceraWeightService = tblVisceraWeightService;
	}


	public  TblAnatomyReqAnimalListService getTblAnatomyReqAnimalListService() {
		return tblAnatomyReqAnimalListService;
	}


	public  void setTblAnatomyReqAnimalListService(
			TblAnatomyReqAnimalListService tblAnatomyReqAnimalListService) {
		this.tblAnatomyReqAnimalListService = tblAnatomyReqAnimalListService;
	}


	public  TblVisceraMissingService getTblVisceraMissingService() {
		return tblVisceraMissingService;
	}


	public  void setTblVisceraMissingService(TblVisceraMissingService tblVisceraMissingService) {
		this.tblVisceraMissingService = tblVisceraMissingService;
	}


	public  TblCounterWeightService getTblCounterWeightService() {
		return tblCounterWeightService;
	}


	public  void setTblCounterWeightService(
			TblCounterWeightService tblCounterWeightService) {
		this.tblCounterWeightService = tblCounterWeightService;
	}


	public  TblAnatomyCheckHisService getTblAnatomyCheckHisService() {
		return tblAnatomyCheckHisService;
	}


	public  void setTblAnatomyCheckHisService(
			TblAnatomyCheckHisService tblAnatomyCheckHisService) {
		this.tblAnatomyCheckHisService = tblAnatomyCheckHisService;
	}


	public  TblVisceraWeightHisService getTblVisceraWeightHisService() {
		return tblVisceraWeightHisService;
	}


	public  void setTblVisceraWeightHisService(
			TblVisceraWeightHisService tblVisceraWeightHisService) {
		this.tblVisceraWeightHisService = tblVisceraWeightHisService;
	}


	public  TblPathPlanVisceraWeighService getTblPathPlanVisceraWeighService() {
		return tblPathPlanVisceraWeighService;
	}


	public  void setTblPathPlanVisceraWeighService(
			TblPathPlanVisceraWeighService tblPathPlanVisceraWeighService) {
		this.tblPathPlanVisceraWeighService = tblPathPlanVisceraWeighService;
	}


	public  DictReportNumberService getDictReportNumberService() {
		return dictReportNumberService;
	}


	public  void setDictReportNumberService(
			DictReportNumberService dictReportNumberService) {
		this.dictReportNumberService = dictReportNumberService;
	}


	public  TblTissueSliceIndexService getTblTissueSliceIndexService() {
		return tblTissueSliceIndexService;
	}


	public  void setTblTissueSliceIndexService(
			TblTissueSliceIndexService tblTissueSliceIndexService) {
		this.tblTissueSliceIndexService = tblTissueSliceIndexService;
	}


	public  DictVisceraService getDictVisceraService() {
		return dictVisceraService;
	}


	public  void setDictVisceraService(DictVisceraService dictVisceraService) {
		this.dictVisceraService = dictVisceraService;
	}

	public TblDoseSettingService getTblDoseSettingService() {
		return tblDoseSettingService;
	}

	public void setTblDoseSettingService(TblDoseSettingService tblDoseSettingService) {
		this.tblDoseSettingService = tblDoseSettingService;
	}

	public TblHistopathCheckService getTblHistopathCheckService() {
		return tblHistopathCheckService;
	}

	public TblAnatomyReqPathPlanCheckService getTblAnatomyReqPathPlanCheckService() {
		return tblAnatomyReqPathPlanCheckService;
	}

	public void setTblAnatomyReqPathPlanCheckService(
			TblAnatomyReqPathPlanCheckService tblAnatomyReqPathPlanCheckService) {
		this.tblAnatomyReqPathPlanCheckService = tblAnatomyReqPathPlanCheckService;
	}

	public void setTblHistopathCheckService(TblHistopathCheckService tblHistopathCheckService) {
		this.tblHistopathCheckService = tblHistopathCheckService;
	}

	public TblAnatomyReqVisceraWeighService getTblAnatomyReqVisceraWeighService() {
		return tblAnatomyReqVisceraWeighService;
	}

	public void setTblAnatomyReqVisceraWeighService(
			TblAnatomyReqVisceraWeighService tblAnatomyReqVisceraWeighService) {
		this.tblAnatomyReqVisceraWeighService = tblAnatomyReqVisceraWeighService;
	}

	public TblAnatomyReqAttachedVisceraService getTblAnatomyReqAttachedVisceraService() {
		return tblAnatomyReqAttachedVisceraService;
	}

	public void setTblAnatomyReqAttachedVisceraService(
			TblAnatomyReqAttachedVisceraService tblAnatomyReqAttachedVisceraService) {
		this.tblAnatomyReqAttachedVisceraService = tblAnatomyReqAttachedVisceraService;
	}

	public TblAnatomyCheckEditService getTblAnatomyCheckEditService() {
		return tblAnatomyCheckEditService;
	}

	public void setTblAnatomyCheckEditService(TblAnatomyCheckEditService tblAnatomyCheckEditService) {
		this.tblAnatomyCheckEditService = tblAnatomyCheckEditService;
	}

	public TblDeadAnimalDeathReasonService getTblDeadAnimalDeathReasonService() {
		return tblDeadAnimalDeathReasonService;
	}

	public void setTblDeadAnimalDeathReasonService(
			TblDeadAnimalDeathReasonService tblDeadAnimalDeathReasonService) {
		this.tblDeadAnimalDeathReasonService = tblDeadAnimalDeathReasonService;
	}

	public TblAnimalTargetOrganService getTblAnimalTargetOrganService() {
		return tblAnimalTargetOrganService;
	}

	public void setTblAnimalTargetOrganService(TblAnimalTargetOrganService tblAnimalTargetOrganService) {
		this.tblAnimalTargetOrganService = tblAnimalTargetOrganService;
	}

	public TblTissueSliceBatchService getTblTissueSliceBatchService() {
		return tblTissueSliceBatchService;
	}

	public void setTblTissueSliceBatchService(TblTissueSliceBatchService tblTissueSliceBatchService) {
		this.tblTissueSliceBatchService = tblTissueSliceBatchService;
	}

	public TblPathStudyIndexService getTblPathStudyIndexService() {
		return tblPathStudyIndexService;
	}

	public void setTblPathStudyIndexService(TblPathStudyIndexService tblPathStudyIndexService) {
		this.tblPathStudyIndexService = tblPathStudyIndexService;
	}

	public TblHistopathCheckContentService getTblHistopathCheckContentService() {
		return tblHistopathCheckContentService;
	}

	public void setTblHistopathCheckContentService(
			TblHistopathCheckContentService tblHistopathCheckContentService) {
		this.tblHistopathCheckContentService = tblHistopathCheckContentService;
	}

	public TblSuperficialTumorVisceraService getTblSuperficialTumorVisceraService() {
		return tblSuperficialTumorVisceraService;
	}

	public void setTblSuperficialTumorVisceraService(
			TblSuperficialTumorVisceraService tblSuperficialTumorVisceraService) {
		this.tblSuperficialTumorVisceraService = tblSuperficialTumorVisceraService;
	}

	public DictLevelService getDictLevelService() {
		return dictLevelService;
	}

	public void setDictLevelService(DictLevelService dictLevelService) {
		this.dictLevelService = dictLevelService;
	}

	
}
