package com.lanen.util;

import java.io.InputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRAbstractScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.fill.JRFillField;

import com.lanen.base.BaseService;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblStudyPlan;

public class ClinicalTestReqReportScriptlet extends JRAbstractScriptlet {

	private TblStudyPlan studyPlan = new TblStudyPlan();
	
	private TblClinicalTestReq clinicalTestReq = new TblClinicalTestReq();
	
	@Override
	public void afterColumnInit() throws JRScriptletException {
		
	}

	@Override
	public void afterDetailEval() throws JRScriptletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterGroupInit(String arg0) throws JRScriptletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPageInit() throws JRScriptletException {
		//获取试验计划
		studyPlan = (TblStudyPlan) StaticDate.STATIC_DATE_MAP.get("studyPlan");
		//获取临检申请全部信息
		clinicalTestReq = (TblClinicalTestReq) StaticDate.STATIC_DATE_MAP.get("clinicalTestReq");
		
		//设置检测项目
		String testIndex1="";
		String testIndex2="";
		String testIndex3="";
		String testIndex4="";
		//拼接每一个检测项目的字符串
		 //懒加载    
		List<TblClinicalTestReqIndex> list = BaseService.getTblClinicalTestReqService().getReqIndexByReqNo(clinicalTestReq.getTblStudyPlan(), clinicalTestReq.getReqNo());
		for (TblClinicalTestReqIndex obj: list) {
			if(obj.getTestitem()==1){
				testIndex1= testIndex1+obj.getTestIndex()+"("+obj.getTestIndexAbbr()+")   ";
			}
			if(obj.getTestitem()==2){
				testIndex2= testIndex2+obj.getTestIndex()+"("+obj.getTestIndexAbbr()+")   ";
			}  
			if(obj.getTestitem()==3){
				testIndex3= testIndex3+obj.getTestIndex()+"("+obj.getTestIndexAbbr()+")   ";
			}
			if(obj.getTestitem()==4){
				testIndex4= testIndex4+obj.getTestIndex()+"("+obj.getTestIndexAbbr()+")   ";
			}
		}
		String testIndex5=clinicalTestReq.getTestOther();
		String testIndex6=clinicalTestReq.getRemark();
		String testName1="血液生化检查";
		String testName2="血液常规检查";
		String testName3="凝血功能检查";
		String testName4="尿液检查";
		String testName5="其他检查项目";
		String testName6="备注";
		
		setVariableValue("studyNo", studyPlan.getStudyNo());
		setVariableValue("animalType", studyPlan.getAnimalType());
//		setVariableValue("studyType", studyPlan.getIsGLP()==1?"GLP研究":"非GLP研究");
		setVariableValue("testPhase", clinicalTestReq.getTestPhase());
		setVariableValue("testName1", testName1);
		setVariableValue("testIndex1", testIndex1);
		setVariableValue("testName2", testName2);
		setVariableValue("testIndex2", testIndex2);
		setVariableValue("testName3", testName3);
		setVariableValue("testIndex3", testIndex3);
		setVariableValue("testName4", testName4);
		setVariableValue("testIndex4", testIndex4);
		setVariableValue("testName5", testName5);
		setVariableValue("testIndex5", testIndex5);
		setVariableValue("testName6", testName6);
		setVariableValue("testIndex6", testIndex6);
//		setVariableValue("reqNo", clinicalTestReq.getReqNo());
		
		
		 InputStream logoImage =(InputStream) StaticDate.STATIC_DATE_MAP.get("logoImage");
		 setVariableValue("logoImage", logoImage);
	}

	@Override
	public void afterReportInit() throws JRScriptletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeColumnInit() throws JRScriptletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeDetailEval() throws JRScriptletException {
//		setFieldValue("beginDate", DateUtil.dateToString(clinicalTestReq.getBeginDate(), "yyyy-MM-dd"));
//		setFieldValue("endDate", DateUtil.dateToString(clinicalTestReq.getEndDate(), "yyyy-MM-dd"));
//		setFieldValue("createDate", DateUtil.dateToString(clinicalTestReq.getCreateDate(), "yyyy-MM-dd"));

	}

	@Override
	public void beforeGroupInit(String arg0) throws JRScriptletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePageInit() throws JRScriptletException {
		
	}

	@Override
	public void beforeReportInit() throws JRScriptletException {
		

	}
	
	public void setFieldValue(String fieldName, Object value) throws JRScriptletException
    {
        JRFillField field = (JRFillField)this.fieldsMap.get(fieldName);
        if (field == null)
        {
            throw new JRScriptletException("FieldName not found : " + fieldName);
        }
        
        field.setValue(value);
    }


}
