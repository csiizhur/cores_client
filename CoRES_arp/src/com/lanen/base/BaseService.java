package com.lanen.base;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lanen.service.AreaService;
import com.lanen.service.ChangeroomService;
import com.lanen.service.ChildbirthService;
import com.lanen.service.DeathService;
import com.lanen.service.GestationService;
import com.lanen.service.IndividualService;
import com.lanen.service.IploginService;
import com.lanen.service.LeavebreastService;
import com.lanen.service.MiscarriageService;
import com.lanen.service.NormalService;
import com.lanen.service.ObservationService;
import com.lanen.service.OestrusService;
import com.lanen.service.QuarantineService;
import com.lanen.service.UserService;
import com.lanen.service.VaccineService;
import com.lanen.service.VirusService;
import com.lanen.service.WeightService;
import com.lanen.service.XcgService;
import com.lanen.service.XyshService;
import com.lanen.util.messager.Messager;

public class BaseService {
	private static ApplicationContext context = null;
	private static UserService userService =null;
	private static AreaService areaService=null;
	private static IndividualService individualService=null;
	private static LeavebreastService leavebreastService=null;
	private static ChangeroomService changeroomService=null;
	private static DeathService deathService=null;
	private static ChildbirthService childbirthService=null;
	private static GestationService gestationService=null;
	private static VaccineService vaccineService=null;
	private static XcgService xcgService=null;
	private static XyshService xyshService=null;
	private static VirusService virusService=null;
	private static NormalService normalService=null;
	private static QuarantineService quarantineService=null;
	private static ObservationService observationService=null;
	private static WeightService weightService=null;
	private static OestrusService oestrusService=null;
	private static MiscarriageService miscarriageService=null;
	//系统日志
	private static IploginService iploginService =null;
	
	public BaseService(){
		try {
			context = new ClassPathXmlApplicationContext("rmi-arp.xml");
			userService = (UserService) context.getBean("UserService");
			
			//系统日志
			iploginService =(IploginService) context.getBean("IploginService");
			areaService=(AreaService) context.getBean("AreaService");
			individualService=(IndividualService)context.getBean("IndividualService");
			leavebreastService=(LeavebreastService) context.getBean("LeavebreastService");
			changeroomService=(ChangeroomService)context.getBean("ChangeroomService");
			deathService=(DeathService)context.getBean("DeathService");
			childbirthService=(ChildbirthService)context.getBean("ChildbirthService");
			gestationService=(GestationService)context.getBean("GestationService");
			vaccineService=(VaccineService)context.getBean("VaccineService");
			xcgService=(XcgService)context.getBean("XcgService");
			xyshService=(XyshService)context.getBean("XyshService");
			virusService=(VirusService)context.getBean("VirusService");
			normalService=(NormalService)context.getBean("NormalService");
			quarantineService=(QuarantineService)context.getBean("QuarantineService");
			observationService=(ObservationService)context.getBean("ObservationService");
			weightService=(WeightService)context.getBean("WeightService");
			oestrusService=(OestrusService)context.getBean("OestrusService");
			miscarriageService=(MiscarriageService)context.getBean("MiscarriageService");
		} catch (Exception e) {
//			Alert2.create("连接服务器失败");
			Messager.showWarnMessage("连接服务器失败！");
		}
	}
	
	public static UserService getUserService() {
		return userService;
	}
	public static void setUserService(UserService userService) {
		BaseService.userService = userService;
	}

	public static IploginService getIploginService() {
		return iploginService;
	}

	public static void setIploginService(IploginService iploginService) {
		BaseService.iploginService = iploginService;
	}

	public static AreaService getAreaService() {
		return areaService;
	}

	public static void setAreaService(AreaService areaService) {
		BaseService.areaService = areaService;
	}

	public static IndividualService getIndividualService() {
		return individualService;
	}

	public static void setIndividualService(IndividualService individualService) {
		BaseService.individualService = individualService;
	}

	public static LeavebreastService getLeavebreastService() {
		return leavebreastService;
	}

	public static void setLeavebreastService(LeavebreastService leavebreastService) {
		BaseService.leavebreastService = leavebreastService;
	}

	public static ChangeroomService getChangeroomService() {
		return changeroomService;
	}

	public static void setChangeroomService(ChangeroomService changeroomService) {
		BaseService.changeroomService = changeroomService;
	}

	public static DeathService getDeathService() {
		return deathService;
	}

	public static void setDeathService(DeathService deathService) {
		BaseService.deathService = deathService;
	}

	public static ChildbirthService getChildbirthService() {
		return childbirthService;
	}

	public static void setChildbirthService(ChildbirthService childbirthService) {
		BaseService.childbirthService = childbirthService;
	}

	public static GestationService getGestationService() {
		return gestationService;
	}

	public static void setGestationService(GestationService gestationService) {
		BaseService.gestationService = gestationService;
	}

	public static VaccineService getVaccineService() {
		return vaccineService;
	}

	public static void setVaccineService(VaccineService vaccineService) {
		BaseService.vaccineService = vaccineService;
	}

	public static XcgService getXcgService() {
		return xcgService;
	}

	public static void setXcgService(XcgService xcgService) {
		BaseService.xcgService = xcgService;
	}

	public static XyshService getXyshService() {
		return xyshService;
	}

	public static void setXyshService(XyshService xyshService) {
		BaseService.xyshService = xyshService;
	}

	public static VirusService getVirusService() {
		return virusService;
	}

	public static void setVirusService(VirusService virusService) {
		BaseService.virusService = virusService;
	}

	public static NormalService getNormalService() {
		return normalService;
	}

	public static void setNormalService(NormalService normalService) {
		BaseService.normalService = normalService;
	}

	public static QuarantineService getQuarantineService() {
		return quarantineService;
	}

	public static void setQuarantineService(QuarantineService quarantineService) {
		BaseService.quarantineService = quarantineService;
	}

	public static ObservationService getObservationService() {
		return observationService;
	}

	public static void setObservationService(ObservationService observationService) {
		BaseService.observationService = observationService;
	}

	public static WeightService getWeightService() {
		return weightService;
	}

	public static void setWeightService(WeightService weightService) {
		BaseService.weightService = weightService;
	}

	public static OestrusService getOestrusService() {
		return oestrusService;
	}

	public static void setOestrusService(OestrusService oestrusService) {
		BaseService.oestrusService = oestrusService;
	}

	public static MiscarriageService getMiscarriageService() {
		return miscarriageService;
	}

	public static void setMiscarriageService(MiscarriageService miscarriageService) {
		BaseService.miscarriageService = miscarriageService;
	}	

}
