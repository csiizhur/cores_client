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

public class BaseService {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("rmi-userprivilege.xml");
	private static UserService userService = (UserService) context.getBean("UserService");
	private static RoleService roleService = (RoleService) context.getBean("RoleService");
	private static PrivilegeService privilegeService = (PrivilegeService) context.getBean("PrivilegeService");
	private static SystemService systemService = (SystemService) context.getBean("SystemService");
	private static DepartmentService departmentService = (DepartmentService) context.getBean("DepartmentService");
	private static UserRoleLogService userRoleLogService = (UserRoleLogService) context.getBean("UserRoleLogService");
	private static RegulationService regulationService=(RegulationService)context.getBean("RegulationService");
	private static ModuleService moduleService=(ModuleService)context.getBean("ModuleService");
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
	
	

}
