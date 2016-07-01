package com.lanen.view.test;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblPathStudyIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class HistopathReview extends Application implements Initializable {
	
//	@FXML
//	private ComboBox<String> sliceCodeComboBox;				//切片编号ComboBox
//	private ObservableList<String> data_sliceCodeComboBox = FXCollections.observableArrayList();
//	/**
//	 * 切片编号索引表List
//	 */
//	List<TblTissueSliceIndex> tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
//	/**
//	 * 当前切片编号索引
//	 */
//	TblTissueSliceIndex tblTissueSliceIndex = null;
	
	@FXML
	private ComboBox<String> contentComboBox;				//显示内容ComboBox
	private ObservableList<String> data_contentComboBox = FXCollections.observableArrayList();
	/**
	 * 显示内容方式
	 */
	private static int contentIndex = 1;
	@FXML
	private ComboBox<String> sortMethodComboBox;			//排序方式ComboBox
	private ObservableList<String> data_sortMethodComboBox = FXCollections.observableArrayList();
	
	@FXML
	private CheckBox onlyShowReviewDataCheckBox;
	
	/**
	 * 排序方式 sortMethod :1,动物  2，脏器
	 */
	private static int sortMethod = 1;	//排序方式 sortMethod :1,动物  2，脏器
	
	@FXML
	private TableColumn<HistopathAnimal,String> animalCodeCol;
	@FXML
	private TableColumn<HistopathAnimal,String> hasAbnormalCol;
	
	/**
	 * 镜检动物表
	 */
	@FXML
	private TableView<HistopathAnimal> histopathAnimalTable;
	private ObservableList<HistopathAnimal> data_histopathAnimalTable = FXCollections.observableArrayList();
 	
//	@FXML
//	private TreeView<String> tissueSliceTree; // 组织切片编号tree
//	private TreeItem<String> rootNode_tissueSliceTree = new TreeItem<String>();//tissueSliceTree 根节点 
//	/**
//	 * 切片编号->切片节点
//	 */
//	private Map<String,TreeItem<String>> sliceCode2SliceTreeItemMap = new HashMap<String,TreeItem<String>>();
	
	/**
	 * 脏器或组织ListView
	 */
	@FXML
	private ListView<String> visceraOrTissueNameListView;
	private ObservableList<String> data_visceraOrTissueNameListView = FXCollections.observableArrayList();
	/**
	 * 脏器或组织 --> map
	 */
	private Map<String,Map<String,Object>> visceraOrTissuename2MapMap = new HashMap<String,Map<String,Object>>();
	
	
	
	@FXML
	private TableColumn<HistopathCheck,String> animalCodeCol_1;
	@FXML
	private TableColumn<HistopathCheck,String> visceraOrTissueNameCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkResultCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkTimeCol;
	@FXML
	private TableColumn<HistopathCheck,String> opinionCol;
	@FXML
	private TableColumn<HistopathCheck,String> stateCol;
	
	@FXML
	private TableColumn<HistopathCheck,String> tumorFlagCol;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
	@FXML
	private TableColumn<HistopathCheck,String> metastasisFlagCol;	//是否转移
	@FXML
	private TableColumn<HistopathCheck,String> primaryVisceraTumorCol;	//原发脏器肿瘤
	@FXML
	private TableColumn<HistopathCheck,String> levelCol;	//病变程度
	@FXML
	private TableColumn<HistopathCheck,String> tumorNumCol;	//肿瘤数量
	@FXML
	private TableColumn<HistopathCheck,String> tumorPosCol;	//肿瘤位置
	@FXML
	private TableColumn<HistopathCheck,String> tumorOccurDateCol;	//肿瘤发生日期
	@FXML
	private TableColumn<HistopathCheck,String> tumorCharacterCol;	//肿瘤特性
	@FXML
	private TableColumn<HistopathCheck,String> remarkCol;	//备注
	
	/**
	 * 组织学所见
	 */
	@FXML
	private TableView<HistopathCheck> histopathCheckTable;
	private ObservableList<HistopathCheck> data_histopathCheckTable = FXCollections.observableArrayList();
	private ObservableList<HistopathCheck> data_histopathCheckTable2 = FXCollections.observableArrayList();
	
	private String studyNo = ""; 
	
	
	
	@FXML
	private Button checkFinishBtn;//提交复核
	@FXML
	private Button dataEditBtn;//数据修改
	@FXML
	private Button signConfirmBtn;//签字确认
	@FXML
	private Button writeReviewMsgBtn;//填写复核意见
	@FXML
	private Button reviewFinishBtn;//复查完成
	@FXML
	private Button printHistopathCheckBtn;//打印镜检结果
	@FXML
	private Button printReviewOpinionBtn;//打印复核意见
	@FXML
	private Button printEditRecordBtn;//打印修改记录
	@FXML
	private Button printBtn;//打印最终结果
	
	@FXML
	private CheckBox printOrderByVisceraCheckBox;//打印数据按脏器排序
	
	/**提交复核/检查完成
	 * @param event
	 */
	@FXML
	private void onCheckFinishBtnAction(ActionEvent event){
		//1.检查是否全部检查完成
		Json json = BaseService.getInstance().getTblHistopathCheckService().checkFinishCheck(studyNo);
		if(json.isSuccess()){
			//2.选择复核者或直接提交
			Stage stage = Main.stageMap.get("SelectPeerreview");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					SelectPeerreview.getInstance().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("SelectPeerreview",stage);
				
			}
			SelectPeerreview.getInstance().loadData();
			stage.showAndWait();
			//3.根据选择，进行  取消  ，提交等工作
			int flag = SelectPeerreview.getInstance().getFlag();
			if(flag > -1){
				//4.签字窗口
				SignMeFrame signMeFrame = new SignMeFrame("");
				Stage stage_me = new Stage();
				stage_me.initModality(Modality.APPLICATION_MODAL);
				stage_me.setTitle("提交");
				try {
					signMeFrame.start(stage_me);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//
				if("OK".equals(SignMeFrame.getType())){
					int histopathReviewRequirement =  flag ;
					String histopathReviewer = SelectPeerreview.getInstance().getHistopathReviewer();
					TblPathStudyIndex tblPathStudyIndex = new TblPathStudyIndex();
					tblPathStudyIndex.setStudyNo(studyNo);
					tblPathStudyIndex.setHistopathReviewRequirement(histopathReviewRequirement);
					tblPathStudyIndex.setHistopathReviewer(histopathReviewer);
					
					//5.提交，更新状态      histopathCheckFinishSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
					String operator = SaveUserInfo.getRealName();
					BaseService.getInstance().getTblHistopathCheckService().checkFinishSign(tblPathStudyIndex,operator);
					
					
					HistopathCheckPage.getInstance().updateBtnState();
					HistopathReview.getInstance().updateBtnState();
					showMessage("提交成功！");
				}
				
				
			}
		}else{
			showErrorMessage(json.getMsg());
		}
	}
	/**数据修改
	 * @param event
	 */
	@FXML
	private void onDataEditBtnAction(ActionEvent event){
//		//1.选择数据 TODO 
//		HistopathCheck selectedItem = histopathCheckTable.getSelectionModel().getSelectedItem();
//		if(null == selectedItem){
//			showErrorMessage("请先选择组织学所见记录数据！");
//			return;
//		}
		
		//1.动物
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.脏器或组织
		String visceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
		if(null == visceraOrTissueName || "".equals(visceraOrTissueName)){
			showErrorMessage("请先选择脏器或组织！");
			return;
		}
			
		
		
		//3.准备数据
		String animalCode = selectedItem.getAnimalCode();
		Map<String,Object> map = visceraOrTissuename2MapMap.get(visceraOrTissueName);
		String sliceVisceraId = (String) map.get("sliceVisceraId");
		String anatomyFingding = (String) map.get("anatomyFingding");
		String visceraCode = (String) map.get("visceraCode");
		if(null == visceraCode || "".equals(visceraCode)){
			visceraCode = "00000000000";
		}
		Integer visceraType = 0;
		String visceraName = null;
		String subVisceraCode =  null;
		String subVisceraName = null;
		if(null == anatomyFingding || "".equals(anatomyFingding.trim())){
			visceraType = (Integer) map.get("visceraType");
			visceraName = (String) map.get("visceraName");
			subVisceraCode = (String) map.get("subVisceraCode");
			subVisceraName = (String) map.get("subVisceraName");
		}
		
		//4.打开  数据修改  窗口
		Stage stage = Main.stageMap.get("HistopathCheckEdit");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				HistopathCheckEdit.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("HistopathCheckEdit",stage);
		}else{
			stage.show();
		}
		
		HistopathCheckEdit.getInstance().loadData(studyNo, animalCode, sliceVisceraId, visceraCode,
				visceraOrTissueName,visceraType,visceraName,subVisceraCode,subVisceraName);
	}
	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignConfrimBtnAction(ActionEvent event){
		//1.检查是否全部检查完成
		Json json = BaseService.getInstance().getTblHistopathCheckService().finalSignCheck(studyNo);
		if(json.isSuccess()){
			//3.签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("最终签字");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			if("OK".equals(SignMeFrame.getType())){
				//3.最终签字，更新状态      histopathReviewFinalSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
				String operator = SaveUserInfo.getRealName();
				BaseService.getInstance().getTblHistopathCheckService().reviewFinalSign(studyNo,operator);
				HistopathReview.getInstance().updateBtnState();
				showMessage("最终签字完成！");
			}
		}else{
			showErrorMessage(json.getMsg());
		}
	}
	/**填写复查意见
	 * @param event
	 */
	@FXML
	private void onWriteReviewMsgBtnAction(ActionEvent event){
		//1.选择数据
		HistopathCheck selectedItem = histopathCheckTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择镜检记录！");
			return;
		}
		//2.打开  复查意见  窗口
		Stage stage = Main.stageMap.get("HistopathReviewOpinion");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				HistopathReviewOpinion.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("HistopathReviewOpinion",stage);
		}else{
			stage.show();
		}
		HistopathReviewOpinion.getInstance().loadData(selectedItem.getId(),selectedItem.getAnimalCode(),selectedItem.getVisceraOrTissueName(),selectedItem.getOpinion());
	}
	/**复查完成
	 * @param event
	 */
	@FXML
	private void onReviewFinishBtnAction(ActionEvent event){
		//1.检查是否已复查
		boolean reviewFlag = false;
		for(HistopathCheck obj:data_histopathCheckTable){
			if(null != obj.getOpinion() && !"".equals(obj.getOpinion().trim())){
				reviewFlag = true;
				break;
			}
		}
		if(!reviewFlag){
			showErrorMessage("请先复查！");
			return;
		}
		
		//2.检查是否已经签字
		TblPathStudyIndex tblPathStudyIndex = BaseService.getInstance().getTblPathStudyIndexService().getByStudyNo(studyNo);
		if(tblPathStudyIndex.getHistopathReviewFlag() >= 2){
			showErrorMessage("复查完成已签字！");
			return;
		}
		
		
//		//3.签字窗口
		SignMeFrame signMeFrame = new SignMeFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("复核完成");
		try {
			signMeFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		if("OK".equals(SignMeFrame.getType())){
			//3.提交，更新状态      histopathReviewSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
			String operator = SaveUserInfo.getRealName();
			BaseService.getInstance().getTblHistopathCheckService().reviewFinishSign(studyNo,operator);
			HistopathReview.getInstance().updateBtnState();
			showMessage("复核完成！");
		}
	}
	/**打印镜检结果
	 * @param event
	 */
	@FXML
	private void onPrintHistopathCheckBtnAction(ActionEvent event){
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("HistopathCheck.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = this.getClass().getResource("/image/logo.jpg");
		int sortType = 1;//动物优先
		if(printOrderByVisceraCheckBox.isSelected()){
			sortType = 2;//脏器优先
		}
		List<Map<String, Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getPrintResultMapListByStudyNo_1(studyNo,sortType);
		
		String number = "";
		number = BaseService.getInstance().getDictReportNumberService()
				.getNumberByReportName("动物镜检记录表");
		map.put("number", number == null ? "" : number);
		// 编号
		map.put("logoImage", url);
		
		//表格标题
		map.put("reportName", "动物镜检记录表");
		
		TblStudyPlan study = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		String animalType = "";
		if(null != study){
			animalType = study.getAnimalType();
			String animalStrain = study.getAnimalStrain();
			if (null != animalStrain || !"".equals(animalStrain)) {
				if (!animalStrain.contains(animalType)) {
					animalType = animalType + " " + animalStrain;
				} else {
					animalType = animalStrain;
				}
			}
		}
		
		map.put("animalType", animalType);
		map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
		
		try {
			jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
		} catch (JRException e) {
			e.printStackTrace();
		}
		//窗口标题
		Main.getInstance().openJFrame(jp, "动物镜检记录表");
	}
	/**打印复核意见
	 * @param event
	 */
	@FXML
	private void onPrintReviewOpinionBtnAction(ActionEvent event){
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("HistopathCheck_reviewOpinion.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = this.getClass().getResource("/image/logo.jpg");
		int sortType = 1;//动物优先
		if(printOrderByVisceraCheckBox.isSelected()){
			sortType = 2;//脏器优先
		}
		List<Map<String, Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getPrintResultMapListByStudyNo_2(studyNo,sortType);
		
		String number = "";
		number = BaseService.getInstance().getDictReportNumberService()
				.getNumberByReportName("动物镜检复核意见表");
		map.put("number", number == null ? "" : number);
		// 编号
		map.put("logoImage", url);
		
		//表格标题
		map.put("reportName", "动物镜检复核意见表");
		
		TblStudyPlan study = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		String animalType = "";
		if(null != study){
			animalType = study.getAnimalType();
			String animalStrain = study.getAnimalStrain();
			if (null != animalStrain || !"".equals(animalStrain)) {
				if (!animalStrain.contains(animalType)) {
					animalType = animalType + " " + animalStrain;
				} else {
					animalType = animalStrain;
				}
			}
		}
		
		map.put("animalType", animalType);
		map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
		
		try {
			jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
		} catch (JRException e) {
			e.printStackTrace();
		}
		//窗口标题
		Main.getInstance().openJFrame(jp, "动物镜检复核意见表");
		
	}
	/**打印修改记录
	 * @param event
	 */
	@FXML
	private void onPrintEditRecordBtnAction(ActionEvent event){
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("HistopathCheck_editRecord.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = this.getClass().getResource("/image/logo.jpg");
		int sortType = 1;//动物优先
		if(printOrderByVisceraCheckBox.isSelected()){
			sortType = 2;//脏器优先
		}
		List<Map<String, Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getPrintResultMapListByStudyNo_3(studyNo,sortType);
		
		String number = "";
		number = BaseService.getInstance().getDictReportNumberService()
				.getNumberByReportName("动物镜检修改记录表");
		map.put("number", number == null ? "" : number);
		// 编号
		map.put("logoImage", url);
		
		//表格标题
		map.put("reportName", "动物镜检修改记录表");
		
		TblStudyPlan study = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		String animalType = "";
		if(null != study){
			animalType = study.getAnimalType();
			String animalStrain = study.getAnimalStrain();
			if (null != animalStrain || !"".equals(animalStrain)) {
				if (!animalStrain.contains(animalType)) {
					animalType = animalType + " " + animalStrain;
				} else {
					animalType = animalStrain;
				}
			}
		}
		
		map.put("animalType", animalType);
		map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
		
		try {
			jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
		} catch (JRException e) {
			e.printStackTrace();
		}
		//窗口标题
		Main.getInstance().openJFrame(jp, "动物镜检修改记录表");
	}
	/**打印最终结果 final
	 * @param event
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event){
			
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("HistopathCheck.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = this.getClass().getResource("/image/logo.jpg");
		int sortType = 1;//动物优先
		if(printOrderByVisceraCheckBox.isSelected()){
			sortType = 2;//脏器优先
		}
		List<Map<String, Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getPrintResultMapListByStudyNo(studyNo,sortType);
		
		String number = "";
		number = BaseService.getInstance().getDictReportNumberService()
				.getNumberByReportName("动物镜检最终记录表");
		map.put("number", number == null ? "" : number);
		// 编号
		map.put("logoImage", url);
		
		//表格标题
		map.put("reportName", "动物镜检最终记录表");
		
		TblStudyPlan study = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		String animalType = "";
		if(null != study){
			animalType = study.getAnimalType();
			String animalStrain = study.getAnimalStrain();
			if (null != animalStrain || !"".equals(animalStrain)) {
				if (!animalStrain.contains(animalType)) {
					animalType = animalType + " " + animalStrain;
				} else {
					animalType = animalStrain;
				}
			}
		}
		
		map.put("animalType", animalType);
		map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
		
		try {
			jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
		} catch (JRException e) {
			e.printStackTrace();
		}
		//窗口标题
		Main.getInstance().openJFrame(jp, "动物镜检最终记录表");
			
//		if(Messager.showSimpleConfirm("", "是否选择打印一个动物一页？")){
//		}else{
//			JasperReport jr = null;
//			JasperPrint jp = null;
//			try {
//				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("HistopathCheck_viscera.jrxml"));
//			} catch (JRException e) {
//				e.printStackTrace();
//			}
//			Map<String, Object> map = new HashMap<String, Object>();
//			URL url = this.getClass().getResource("/image/logo.jpg");
//			List<Map<String, Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
//					.getPrintResultMapListByTaskId(taskId,2);
//			
//			String number = "";
//			number = BaseService.getInstance().getDictReportNumberService()
//					.getNumberByReportName("动物镜检记录表");
//			map.put("number", number == null ? "" : number);
//			;// 编号
//			map.put("logoImage", url);
//			map.put("reportName", "动物镜检记录表");
//			if (null != maplist && maplist.size() > 0) {
//				for (Map<String, Object> obj : maplist) {
//					String animalType = (String) obj.get("animalType");
//					String animalStrain = (String) obj.get("animalStrain");
//					if (null != animalStrain || !"".equals(animalStrain)) {
//						if (!animalStrain.contains(animalType)) {
//							animalType = animalType + " " + animalStrain;
//						} else {
//							animalType = animalStrain;
//						}
//					}
//					obj.put("animalType", animalType);
//				}
//			}
//			if (null != maplist && maplist.size() > 0) {
//				for (Map<String, Object> obj : maplist) {
//					String studyNo = (String) obj.get("studyNo");
//					obj.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
//				}
//			}
//			try {
//				jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
//			} catch (JRException e) {
//				e.printStackTrace();
//			}
//			Main.getInstance().openJFrame(jp, "动物镜检记录表");
//			
//		}

		return;
	}
	/**关闭
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**仅显示复核数据
	 * @param event
	 */
	@FXML
	private void onOnlyShowReviewDataCheckBoxAction(ActionEvent event){
		updateHistopathCheckTable_1();
	}
	
	/**
	 * 个体数据
	 */
	@FXML
	private void onIndividualDataHyperlink(ActionEvent event){
		//1.动物
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		
		//2.
		Stage stage = Main.stageMap.get("IndividualData");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				IndividualData.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("IndividualData",stage);
		}else{
			stage.show();
		}
		IndividualData.getInstance().loadData(studyNo,animalCode);
	}

	private String getSelectedAnimalCode() {
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			return selectedItem.getAnimalCode();
		}
		return null;
	}

	private static HistopathReview instance;
	public static HistopathReview getInstance(){
		if(null == instance){
			instance = new HistopathReview();
		}
		return instance;
	}
	
	public HistopathReview() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//2.初始化动物/脏器Tab
		initAnimalVisceraTab();
	}
	/**
	 * 初始化动物/脏器Tab
	 */
	private void initAnimalVisceraTab() {
		//3.初始化显示内容ComboBox
		initContentComboBox();
		//4.初始化排序方式ComboBox
		initSortMethodComboBox();
		//5.初始化动物TableView
		initHistopathAnimalTable();
		//6.初始化脏器或组织ListView
		initVisceraOrTissueNameListView();
		//7.初始化镜检结果TableView
		initHistopathCheckTable();
	}
	
	/**
	 * 初始化 histopathCheckTable
	 */
	private void initHistopathCheckTable() {
		histopathCheckTable.setItems(data_histopathCheckTable2);
//		histopathCheckTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol_1.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("animalCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("visceraOrTissueName"));
		checkResultCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkResult"));
		checkTimeCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkTime"));
		opinionCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("opinion"));
		stateCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("state"));
		tumorFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorFlag"));
		metastasisFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("metastasisFlag"));
		primaryVisceraTumorCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("primaryVisceraTumor"));
		levelCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("level"));
		tumorNumCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorNum"));
		tumorPosCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorPos"));
		tumorOccurDateCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorOccurDate"));
		tumorCharacterCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorCharacter"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("remark"));
		
		opinionCol.setCellFactory(new Callback<TableColumn<HistopathCheck,String>,TableCell<HistopathCheck,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<HistopathCheck, String> call(
	    			 TableColumn<HistopathCheck, String> param) {
	    		 TableCell<HistopathCheck, String> cell =
	    				 new TableCell<HistopathCheck, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    				 
	    				 if(null != getString() && !"".equals(getString())){
//	    					 getTableRow().setStyle("-fx-background-color:#32fc20;");
	    					 getTableRow().setStyle("-fx-background-color:palegreen;");
	    				 }else{
	    					 getTableRow().setStyle("");
	    				 }
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 return cell;
	    	 }
	     });
		
		histopathCheckTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HistopathCheck>(){

			@Override
			public void changed(ObservableValue<? extends HistopathCheck> arg0,
					HistopathCheck arg1, HistopathCheck newValue) {
				if(null != newValue){
					String animalCode = newValue.getAnimalCode();
					String visceraOrTissueName = newValue.getVisceraOrTissueName();
					selectAnimalAndViscera(animalCode,visceraOrTissueName);
				}
			}

			});
	}
	/**选择的对应动物行脏器，若选中的就是当前则不变
	 * @param animalCode
	 * @param visceraOrTissueName
	 */
	private void selectAnimalAndViscera(String animalCode, String visceraOrTissueName) {
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem || !animalCode.equals(selectedItem.getAnimalCode())){
			for(HistopathAnimal obj:data_histopathAnimalTable){
				if(obj.getAnimalCode().equals(animalCode)){
					histopathAnimalTable.getSelectionModel().select(obj);
					break;
				}
			}
		}
		
		String selectedVisceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
		if(null == selectedVisceraOrTissueName || !selectedVisceraOrTissueName.equals(visceraOrTissueName.trim())){
			visceraOrTissueNameListView.getSelectionModel().select(visceraOrTissueName.trim());
		}
		
	}

	/**
	 *  初始化脏器或组织ListView
	 */
	private void initVisceraOrTissueNameListView() {
		visceraOrTissueNameListView.setItems(data_visceraOrTissueNameListView);
		
		visceraOrTissueNameListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					if(contentIndex == 3 || contentIndex == 4){
						updateHistopathCheckTable_1();
					}
				}
			}
			
		});
		
	}
	
	/**
	 * 初始化动物TableView histopathAnimalTable
	 */
	private void initHistopathAnimalTable() {
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("animalCode"));
		hasAbnormalCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("hasAbnormal"));
		hasAbnormalCol.setCellFactory(new Callback<TableColumn<HistopathAnimal,String>,TableCell<HistopathAnimal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<HistopathAnimal, String> call(
	    			 TableColumn<HistopathAnimal, String> param) {
	    		 TableCell<HistopathAnimal, String> cell =
	    				 new TableCell<HistopathAnimal, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER;");
	    		 return cell;
	    	 }
	     });
		histopathAnimalTable.setItems(data_histopathAnimalTable);
		histopathAnimalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HistopathAnimal>(){

			@Override
			public void changed(ObservableValue<? extends HistopathAnimal> arg0,
					HistopathAnimal arg1, HistopathAnimal newValue) {
				
				String animalCode = null;
//				Integer gender = null;
				if(null != newValue){
					animalCode = newValue.getAnimalCode();
//					gender = newValue.getGender();
					if(contentIndex == 2 || contentIndex == 4){
						updateHistopathCheckTable_1();
					}
				}
				// 更新切片编号tree
				updateVisceraOrTissueNameListView(animalCode);
				
			}
		});
	}

	/**
	 * 初始化排序方式ComboBox
	 */
	private void initSortMethodComboBox() {
		data_sortMethodComboBox.add("动物顺序");
		data_sortMethodComboBox.add("脏器顺序");
		sortMethodComboBox.setItems(data_sortMethodComboBox);
		sortMethodComboBox.getSelectionModel().select(0);
		sortMethodComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue ){
					sortMethod = newValue.intValue()+1;
					updateHistopathCheckTable();
				}
			}});
	}

	/**
	 * 初始化显示内容ComboBox
	 */
	private void initContentComboBox() {
		data_contentComboBox.add("所有动物，所有脏器");
		data_contentComboBox.add("当前动物，所有脏器");
		data_contentComboBox.add("所有动物，当前脏器");
		data_contentComboBox.add("当前动物，当前脏器");
		contentComboBox.setItems(data_contentComboBox);
		contentComboBox.getSelectionModel().select(0);
		contentComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue ){
					contentIndex = newValue.intValue()+1;
					
					updateHistopathCheckTable_1();
				}
			}});
	}

	/**
	 * 更新组织学所见表（tab0）
	 */
	public void updateHistopathCheckTable(){
		HistopathCheck selectedItem = histopathCheckTable.getSelectionModel().getSelectedItem();
		String selectId = null;
		if(null != selectedItem){
			selectId = selectedItem.getId();
		}
		data_histopathCheckTable.clear();
		data_histopathCheckTable2.clear();
		histopathCheckTable.setItems(data_histopathCheckTable2);
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService()
				.getMapListByStudyNoSortMethord(studyNo,sortMethod);
		if(null != mapList && mapList.size() > 0){
//			histopathCheck.animalCode,histopathCheck.tissueSliceVisceraRecordId,histopathCheck.isNoAbnormal,histopathCheck.tumorFlag,
//			 * histopathCheck.metastasisFlag,histopathCheck.histoPos,histopathCheck.lesionFinding,histopathCheck.primaryViscera,
//			 * histopathCheck.primaryTumor,convert(varchar(10),histopathCheck.operateTime,120) as operateTime,visceraOrTissueName
			for(Map<String,Object> map :mapList){
				String animalCode = (String) map.get("animalCode");
				String tissueSliceVisceraRecordId = (String) map.get("tissueSliceVisceraRecordId");
				Integer isNoAbnormal = (Integer) map.get("isNoAbnormal");
				if(isNoAbnormal == -1){
					continue;
				}
				Integer tumorFlag = (Integer) map.get("tumorFlag");
				Integer metastasisFlag = (Integer) map.get("metastasisFlag");
				
				String histoPos = (String) map.get("histoPos");
				String lesionFinding = (String) map.get("lesionFinding");
				String operateTime = (String) map.get("operateTime");
				String visceraOrTissueName = (String) map.get("visceraOrTissueName");
				String id = (String) map.get("id");
				String histopathReviewOpinion = (String) map.get("histopathReviewOpinion");
				Integer historyFlag = (Integer) map.get("historyFlag");
				
				
				String primaryViscera = (String) map.get("primaryViscera");
				String primaryTumor = (String) map.get("primaryTumor");
				String level = (String) map.get("level");
				Integer tumorNum = (Integer) map.get("tumorNum");
				Integer tumorPos = (Integer) map.get("tumorPos");
				String tumorOccurDate = (String) map.get("tumorOccurDate");
				Integer tumorCharacter = (Integer) map.get("tumorCharacter");
				String remark = (String) map.get("remark");
				
				
				
				data_histopathCheckTable.add(new HistopathCheck(animalCode,visceraOrTissueName,operateTime,tissueSliceVisceraRecordId,
						isNoAbnormal,tumorFlag,metastasisFlag,histoPos,lesionFinding,histopathReviewOpinion,id,historyFlag,
						primaryViscera,primaryTumor,level,tumorNum,tumorPos,tumorOccurDate,tumorCharacter,remark
						));
			}
			updateHistopathCheckTable_1();
			if(null != selectId){
				
				int i = 0;
				for(HistopathCheck obj :histopathCheckTable.getItems()){
					if(selectId.equals(obj.getId())){
						histopathCheckTable.getSelectionModel().select(obj);
						histopathCheckTable.scrollTo(i);
						break;
					}
					i++;
				}
			}
		}
		
	}
	
	/**
	 * 更新组织学所见表（tab0）,只是增加过来条件，不查询数据库
	 */
	public void updateHistopathCheckTable_1(){
		
		HistopathCheck selectedItem = histopathCheckTable.getSelectionModel().getSelectedItem();
		String id = null;
		if(null != selectedItem){
			id = selectedItem.getId();
		}
		if (data_histopathCheckTable.size() > 0){
			data_histopathCheckTable2.clear();
			histopathCheckTable.setItems(data_histopathCheckTable2);
			if(!onlyShowReviewDataCheckBox.isSelected()){
				if(contentIndex == 1){
					//所有动物 所有脏器
					histopathCheckTable.setItems(data_histopathCheckTable);
				}else if(contentIndex == 2){
					//当前动物 所有脏器
					HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
					if(null != animal){
						String animalCode = animal.getAnimalCode();
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getAnimalCode().equals(animalCode)){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}else if(contentIndex == 3){
					//所有动物 当前脏器
					//
					String visceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
					if(null != visceraOrTissueName ){
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getVisceraOrTissueName().equals(visceraOrTissueName)){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}else if(contentIndex == 4){
					//当前动物 当前脏器
					
					//当前动物 所有脏器
					HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
					String visceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
					if(null != animal && null != visceraOrTissueName){
						String animalCode = animal.getAnimalCode();
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getVisceraOrTissueName().equals(visceraOrTissueName) && obj.getAnimalCode().equals(animalCode)){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}
			}else{
				if(contentIndex == 1){
					//所有动物 所有脏器
//					histopathCheckTable.setItems(data_histopathCheckTable);
					for(HistopathCheck obj:data_histopathCheckTable){
						if(obj.getOpinion() != null && !"".equals(obj.getOpinion())){
							data_histopathCheckTable2.add(obj);
						}
					}
				}else if(contentIndex == 2){
					//当前动物 所有脏器
					HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
					if(null != animal){
						String animalCode = animal.getAnimalCode();
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getAnimalCode().equals(animalCode) && obj.getOpinion() != null && !"".equals(obj.getOpinion())){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}else if(contentIndex == 3){
					//所有动物 当前脏器
					//
					String visceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
					if(null != visceraOrTissueName ){
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getVisceraOrTissueName().equals(visceraOrTissueName) 
									&& obj.getOpinion() != null && !"".equals(obj.getOpinion())){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}else if(contentIndex == 4){
					//当前动物 当前脏器
					
					//当前动物 所有脏器
					HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
					String visceraOrTissueName = visceraOrTissueNameListView.getSelectionModel().getSelectedItem();
					if(null != animal && null != visceraOrTissueName){
						String animalCode = animal.getAnimalCode();
						for(HistopathCheck obj:data_histopathCheckTable){
							if(obj.getVisceraOrTissueName().equals(visceraOrTissueName) && obj.getAnimalCode().equals(animalCode)
									 && obj.getOpinion() != null && !"".equals(obj.getOpinion())){
								data_histopathCheckTable2.add(obj);
							}
						}
					}
				}
			}
			
			if(null != id){
				
				int i = 0;
				for(HistopathCheck obj :histopathCheckTable.getItems()){
					if(id.equals(obj.getId())){
						histopathCheckTable.getSelectionModel().select(obj);
						histopathCheckTable.scrollTo(i);
						break;
					}
					i++;
				}
			}
			
		}
	}

	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		onlyShowReviewDataCheckBox.setSelected(false);
		
		//1.更新镜检动物列表（专题下已镜检的动物）
		updateHistopathAnimalTable();
		//5.更新组织学所见表（tab0）
		updateHistopathCheckTable();
		//更新按钮状态
		updateBtnState();
	}
	
	/**
	 * 更新按钮状态
	 */
	public void updateBtnState() {
		checkFinishBtn.setDisable(true);
		dataEditBtn.setDisable(true);
		signConfirmBtn.setDisable(true);
		
		writeReviewMsgBtn.setDisable(true);
		reviewFinishBtn.setDisable(true);
		printBtn.setDisable(true);
		//身份信息    1.病理专题负责人或病理负责人    2.同行评议人
		String userName = SaveUserInfo.getUserName();
		int histopathReviewFlag = 0;
		TblPathStudyIndex pathStudyPlan = BaseService.getInstance().getTblPathStudyIndexService().getByStudyNo(studyNo);
		if(null != pathStudyPlan){
			histopathReviewFlag = pathStudyPlan.getHistopathReviewFlag();
		}
		int userFlag = BaseService.getInstance().getTblHistopathCheckService().getUserFlag(studyNo,userName);
		
		if(userFlag > 0){
			if(histopathReviewFlag == 0 && userFlag == 1){
				checkFinishBtn.setDisable(false);
			}else if(histopathReviewFlag == 1 && userFlag == 2){
				writeReviewMsgBtn.setDisable(false);
				reviewFinishBtn.setDisable(false);
			}else if(histopathReviewFlag == 2 && userFlag == 1){
				dataEditBtn.setDisable(false);
				signConfirmBtn.setDisable(false);
			}
		}
		printBtn.setDisable(false);
	}
	/**
	 * 更新镜检动物表格 histopathAnimalTable
	 */
	private void updateHistopathAnimalTable(){
		data_histopathAnimalTable.clear();
			//map中有animalCode,gender,isNoAbnormal
			//查询待镜检动物根据专题编号(animalCode,gender,isNoAbnormal,resultNum),去除自溶的动物
			List<Map<String,Object>>  mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getAnimalByStudyNo(studyNo);
			if(null != mapList && mapList.size() > 0){
				String animalCode = "";
				Integer gender = null;
				Integer isNoAbnormal = null;
				Integer resultNum = null;
				
				String state = "";
				String hasAbnormal = "";
				for(Map<String,Object> map:mapList){
					animalCode = (String) map.get("animalCode");
					gender = (Integer) map.get("gender");
					isNoAbnormal = (Integer) map.get("isNoAbnormal");
					resultNum = (Integer) map.get("resultNum");
					if(null != resultNum && resultNum > 0){
						state = "已开始检查";
						if(null ==isNoAbnormal){
							hasAbnormal = "无";
						}else{
							hasAbnormal = "有";
						}
					}else{
						state = "未开始检查";
						hasAbnormal = "";
					}
					data_histopathAnimalTable.add(new HistopathAnimal(animalCode,state,hasAbnormal,gender));
				}
			}
			
			histopathAnimalTable.getSelectionModel().selectFirst();
	}
	
	/**更新组织切片树
	 * @param animalCode
	 */
	private void updateVisceraOrTissueNameListView(String animalCode) {
		
		data_visceraOrTissueNameListView.clear();
		visceraOrTissuename2MapMap.clear();
		
		if(null != animalCode ){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getTissueSliceViscera(studyNo,animalCode);
			if(null != mapList){
				for(Map<String,Object> map:mapList){
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					if(null != visceraOrTissueName && !data_visceraOrTissueNameListView.contains(visceraOrTissueName.trim())){
						data_visceraOrTissueNameListView.add(visceraOrTissueName.trim());
						visceraOrTissuename2MapMap.put(visceraOrTissueName.trim(), map);
					}
				}
			}
		}
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathReview.fxml"));
		Scene scene = new Scene(root, 1014, 590);
		stage.setScene(scene);
		stage.setTitle("复核与确认");
//		stage.setResizable(false);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setMinWidth(1024);
		stage.setMinHeight(600);
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight());  
		stage.show();
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	/**镜检动物
	 * @author Administrator
	 *
	 */
	public class HistopathAnimal{
		
		private StringProperty animalCode;
		private StringProperty state;
		private StringProperty hasAbnormal;
		private IntegerProperty gender;
		
		public HistopathAnimal(String animalCode,String state,String hasAbnormal,Integer gender){
			setAnimalCode(animalCode);
			setState(state);
			setHasAbnormal(hasAbnormal);
			setGender(gender);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}
		public String getHasAbnormal() {
			return hasAbnormal.get();
		}
		public void setHasAbnormal(String hasAbnormal) {
			this.hasAbnormal = new SimpleStringProperty(hasAbnormal);
		}
		public Integer getGender() {
			return gender.get();
		}
		public void setGender(Integer gender) {
			this.gender = new SimpleIntegerProperty(gender);
		}
		
	}
	
	/**组织学检查
	 * @author Administrator
	 *
	 */
	public class HistopathCheck{
		private StringProperty animalCode;		//动物编号
		private StringProperty visceraOrTissueName;//脏器或组织
		private StringProperty checkResult;//检查结果
		private StringProperty checkTime;	//检查时间
		private StringProperty tissueSliceVisceraRecordId;
		private StringProperty opinion;
		private StringProperty id;
		private IntegerProperty historyFlag;
		private StringProperty state;
		
		private StringProperty tumorFlag;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
		private StringProperty metastasisFlag;	//是否转移
		private StringProperty primaryVisceraTumor;	//原发脏器肿瘤
		private StringProperty level;	//病变程度
		private StringProperty tumorNum;	//肿瘤数量
		private StringProperty tumorPos;	//肿瘤位置
		private StringProperty tumorOccurDate;	//肿瘤发生日期
		private StringProperty tumorCharacter;	//肿瘤特性
		private StringProperty remark;	//备注
		
		public HistopathCheck(String animalCode,String visceraOrTissueName,String checkTime,String tissueSliceVisceraRecordId,Integer isNoAbnormal,Integer tumorFlag,Integer metastasisFlag,
				String histoPos,String lesionFinding,String opinion,String id,Integer historyFlag,
				String primaryViscera,String primaryTumor,String level,Integer tumorNum,Integer tumorPos,String tumorOccurDate,Integer tumorCharacter,String remark){
			setAnimalCode(animalCode);
			setVisceraOrTissueName(visceraOrTissueName.trim());
			setTissueSliceVisceraRecordId(tissueSliceVisceraRecordId);
			setCheckTime(checkTime);
			if(isNoAbnormal == 0){
				if(tumorFlag == 0 || tumorFlag == 1 ){
					setTumorFlag(tumorFlag == 0 ? "非肿瘤":"良性肿瘤");
					setMetastasisFlag("");
					setPrimaryVisceraTumor("");
				}else{
					setTumorFlag("恶性肿瘤");
//					setMetastasisFlag(metastasisFlag == 1 ? "是":"否");
					if(metastasisFlag == 1){
						setMetastasisFlag("转移");
						setPrimaryVisceraTumor((primaryViscera != null ? primaryViscera+" " :"")+(primaryTumor != null ? primaryTumor:""));
					}else if(metastasisFlag == 2){
						setMetastasisFlag("侵袭");
						setPrimaryVisceraTumor("");
					}else{
						setMetastasisFlag("原发");
						setPrimaryVisceraTumor("");
					}
				}
				
				String checkResult = "";
				if(null != histoPos && !"".equals(histoPos)){
					checkResult = histoPos+" "+lesionFinding;
				}else{
					checkResult = lesionFinding;
				}
				setCheckResult(checkResult);
//				setCheckResult(histoPos+" "+lesionFinding);
			}else if(isNoAbnormal == 1){
				setTumorFlag("");
				setMetastasisFlag("");
				setPrimaryVisceraTumor("");
				setCheckResult("未见异常");
			}else if(isNoAbnormal == -1){
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("缺失");
				setPrimaryVisceraTumor("");
			}
			
			setOpinion(opinion);
			setId(id);
			setHistoryFlag(historyFlag);
			if(null != historyFlag && historyFlag > 0){
				setState("已删除");
			}else{
				setState("");
			}
			
			setLevel(level != null ? level :"");
			setTumorNum(tumorNum+"");
			if(null != tumorPos && tumorPos == 1){
				setTumorPos("浅表");
				setTumorOccurDate(tumorOccurDate);
				setTumorCharacter("");
			}else if(null != tumorPos && tumorPos == 2){
				setTumorPos("深部");
				setTumorOccurDate("");
//				1：偶发；2：致死；3：不明确
				switch (tumorCharacter) {
				case 1:	setTumorCharacter("偶发");break;
				case 2:	setTumorCharacter("致死");break;
				case 3:	setTumorCharacter("不明确");break;

				default:setTumorCharacter("");
					break;
				}
			}else{
				setTumorPos("");
				setTumorOccurDate("");
				setTumorCharacter("");
			}
			setRemark(remark != null ? remark :"");
		}
		
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}
		public String getCheckResult() {
			return checkResult.get();
		}
		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}
		public String getCheckTime() {
			return checkTime.get();
		}
		public void setCheckTime(String checkTime) {
			this.checkTime = new SimpleStringProperty(checkTime);
		}

		public String getTissueSliceVisceraRecordId() {
			return tissueSliceVisceraRecordId.get();
		}

		public void setTissueSliceVisceraRecordId(String tissueSliceVisceraRecordId) {
			this.tissueSliceVisceraRecordId = new SimpleStringProperty(tissueSliceVisceraRecordId);
		}

		public String getOpinion() {
			return opinion.get();
		}

		public void setOpinion(String opinion) {
			this.opinion = new SimpleStringProperty(opinion);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public Integer getHistoryFlag() {
			return historyFlag.get();
		}

		public void setHistoryFlag(Integer historyFlag) {
			this.historyFlag = new SimpleIntegerProperty(historyFlag);
		}

		public String getState() {
			return state.get();
		}

		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}

		public String getTumorFlag() {
			return tumorFlag.get();
		}

		public void setTumorFlag(String tumorFlag) {
			this.tumorFlag = new SimpleStringProperty(tumorFlag);
		}

		public String getMetastasisFlag() {
			return metastasisFlag.get();
		}

		public void setMetastasisFlag(String metastasisFlag) {
			this.metastasisFlag = new SimpleStringProperty(metastasisFlag);
		}

		public String getPrimaryVisceraTumor() {
			return primaryVisceraTumor.get();
		}

		public void setPrimaryVisceraTumor(String primaryVisceraTumor) {
			this.primaryVisceraTumor = new SimpleStringProperty(primaryVisceraTumor);
		}

		public String getLevel() {
			return level.get();
		}

		public void setLevel(String level) {
			this.level = new SimpleStringProperty(level);
		}

		public String getTumorNum() {
			return tumorNum.get();
		}

		public void setTumorNum(String tumorNum) {
			this.tumorNum = new SimpleStringProperty(tumorNum);
		}

		public String getTumorPos() {
			return tumorPos.get();
		}

		public void setTumorPos(String tumorPos) {
			this.tumorPos = new SimpleStringProperty(tumorPos);
		}

		public String getTumorOccurDate() {
			return tumorOccurDate.get();
		}

		public void setTumorOccurDate(String tumorOccurDate) {
			this.tumorOccurDate = new SimpleStringProperty(tumorOccurDate);
		}

		public String getTumorCharacter() {
			return tumorCharacter.get();
		}

		public void setTumorCharacter(String tumorCharacter) {
			this.tumorCharacter = new SimpleStringProperty(tumorCharacter);
		}

		public String getRemark() {
			return remark.get();
		}

		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		
	}
}
