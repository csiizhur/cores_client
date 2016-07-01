package com.lanen.view.tblsession;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.DictPhysicalExamItem;
import com.lanen.model.quarantine.TblAnimalRecIndex;
import com.lanen.model.quarantine.tblsession.TblGeneralObservation;
import com.lanen.model.quarantine.tblsession.TblPhyExam;
import com.lanen.model.quarantine.tblsession.TblPhyExamResult;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.TblPhyExamForTableView;
import com.lanen.model.tableview.TblPhyExamResultForTableView;
import com.lanen.model.tableview.TblTraceForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

public class PhyExamFrame extends Application implements Initializable {

	@FXML
	private Label createrLabel;
	@FXML
	private Label animalTypeLabel;
	@FXML
	private Label createTimeLabel;
	@FXML
	private Label sessionTypeLabel;
	@FXML
	private Label studyNoLabel;
	
	
	/**
	 * 动物体检 表
	 */
	@FXML
	private static TableView<TblPhyExamForTableView> tblPhyExamTable;
	private static ObservableList<TblPhyExamForTableView> data_tblPhyExamTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblPhyExamForTableView,String> animalIdCol;
	@FXML
	private TableColumn<TblPhyExamForTableView,String> genderCol;
	@FXML
	private TableColumn<TblPhyExamForTableView,String> bodyWeightCol;
	@FXML
	private TableColumn<TblPhyExamForTableView,String> temperatureCol;
	@FXML
	private TableColumn<TblPhyExamForTableView,String> recordTimeCol;
	@FXML
	private TableColumn<TblPhyExamForTableView,String> examResultCol;
	
	@FXML
	private Label animaIdLabel;
	@FXML
	private TextField bodyWeightText;
	@FXML
	private ComboBox<String> weightUnitComboBox;
	@FXML
	private TextField temperatureText;
	
	@FXML
	private Button othersButton;
	@FXML
	private Button addButton;
	
	
	
	//选中的动物列表中的动物
	private String id="";
	private String animalId ="";
	private int gender = 0;
	private String bodyWeight ="";
	private String weightUnit ="";
	private String temperature="";
	private String examResult="";

	/**
	 * 体检结果
	 */
	@FXML
	private TableView<TblPhyExamResultForTableView> tblPhyExamResultTable;
	private ObservableList<TblPhyExamResultForTableView> data_tblPhyExamResultTable=
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblPhyExamResultForTableView,String> itemNameCol_1;
	@FXML
	private TableColumn<TblPhyExamResultForTableView,String> remarkCol_1;
	@FXML
	private TableColumn<TblPhyExamResultForTableView,String> examResultCol_1;
	
	
	
	/**
	 * 修改痕迹表格
	 */
	@FXML
	private TableView<TblTraceForTableView> tblTraceTable;
	/**
	 * 修改痕迹表格数据集
	 */
	private static ObservableList<TblTraceForTableView> data_tblTraceTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblTraceForTableView,String> animalIdCol_2;
//	@FXML
//	private TableColumn<TblTraceForTableView,String> operateModeCode_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> oldValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> newValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> operatorCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyReasonCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyTimeCol_2;
	
	
	private static TblSession tblSession = null;
	private static String sessionId ="";
	private static List<TblSessionAnimals> tblSessionAnimalsList = null;
	private static String signId ="";
	private static String checkId="";
	private static Date beginDate =null;   //开始采集时间
	/**离开页面时，是否需要更新DayToDayPane上tblSession表数据*/
	private static boolean updateFlag  = false;//离开页面时，是否需要更新DayToDayPane上tblSession表数据
	
	private static PhyExamFrame instance;
	public static PhyExamFrame getInstance(){
		if(null == instance){
			instance = new PhyExamFrame();
		}
		updateFlag = false;
		return instance;
	}
	public PhyExamFrame(){
		updateFlag = false;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化tblPhyExamTable
		initTblPhyExamTable();
		//修改痕迹
		ininTblTraceTalbe();
		//初始化tblPhyExamResultTable及更新数据
		ininTblPhyExamResultTable();
		//初始化  weightUnitComboBox  
		initWeightUnitComboBox();
		
	}
	

	/**
	 * 初始化  weightUnitComboBox  
	 */
	private void initWeightUnitComboBox() {
		weightUnitComboBox.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(newValue.intValue()>-1){
					if(newValue.intValue() == 0){
						weightUnit="kg";
					}else{
						weightUnit="g";
					}
				}else{
					weightUnit="";
				}
				
			}});
	}
	/**
	 * 初始化tblPhyExamTable
	 */
	private void initTblPhyExamTable() {
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("animalId"));
		genderCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("gender"));
		bodyWeightCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("bodyWeight"));
		temperatureCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("temperature"));
		examResultCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("examResult"));
		recordTimeCol.setCellValueFactory(new PropertyValueFactory<TblPhyExamForTableView, String>("recordTime"));
		tblPhyExamTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		genderCol.setCellFactory(new Callback<TableColumn<TblPhyExamForTableView, String>, TableCell<TblPhyExamForTableView, String>>(){

			@Override
			public TableCell<TblPhyExamForTableView, String> call(
					TableColumn<TblPhyExamForTableView, String> arg0) {
				TableCell<TblPhyExamForTableView, String> cell = 
						new TableCell<TblPhyExamForTableView, String>(){

							@Override
							protected void updateItem(String item, boolean empty) {
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
		tblPhyExamTable.setItems(data_tblPhyExamTable);
		tblPhyExamTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<TblPhyExamForTableView>() {

			@Override
			public void changed(ObservableValue<? extends TblPhyExamForTableView> arg0,
					TblPhyExamForTableView arg1, TblPhyExamForTableView newValue) {
				if(null != newValue){
					id=newValue.getId();
					animalId = newValue.getAnimalId();
					if(newValue.getGender().equals("♂")){
						gender = 1;
					}else{
						gender = 2;
					}
					examResult = newValue.getExamResult();
					//更新TblPhyExamResultTable值
					if(null == examResult || examResult.isEmpty()){
						updateTblPhyExamResultTable();
					}else{
						updateTblPhyExamResultTable(sessionId,animalId);
					}
					
					bodyWeight = newValue.getBodyWeight().trim();
					temperature = newValue.getTemperature();
					if(bodyWeight != null && !bodyWeight.isEmpty()){
						String[] splits = bodyWeight.split(" ");
						bodyWeight = splits[0];
						weightUnit = splits[1];
						weightUnitComboBox.getSelectionModel().select(weightUnit);
					}
				}else{
					id="";
					animalId = "";
					gender = 0;
				}
				animaIdLabel.setText(animalId);
				bodyWeightText.setText(bodyWeight);
				temperatureText.setText(temperature);
			}
		});
	}

	
	/**
	 * 初始化tblPhyExamResultTable
	 */
	private void ininTblPhyExamResultTable() {
		itemNameCol_1.setCellValueFactory(
				new PropertyValueFactory<TblPhyExamResultForTableView, String>("itemName"));
		remarkCol_1.setCellValueFactory(
				new PropertyValueFactory<TblPhyExamResultForTableView, String>("remark"));
		examResultCol_1.setCellValueFactory(
				new PropertyValueFactory<TblPhyExamResultForTableView, String>("examResult"));
		Callback<TableColumn<TblPhyExamResultForTableView,String>,TableCell<TblPhyExamResultForTableView,String>> cellFactory = 
				new Callback<TableColumn<TblPhyExamResultForTableView,String>,TableCell<TblPhyExamResultForTableView,String>>(){

			@Override
			public TableCell<TblPhyExamResultForTableView,String> call(TableColumn<TblPhyExamResultForTableView,String> p) {
				
				return new TextFieldTableCellImpl();
			}
			
		};
		examResultCol_1.setCellFactory(cellFactory);
		tblPhyExamResultTable.setItems(data_tblPhyExamResultTable);
	}
	/**
	 * 更新表格数据tblPhyExamResultTable(查询字典项)
	 */
	private void updateTblPhyExamResultTable(){
		data_tblPhyExamResultTable.clear();
		List<DictPhysicalExamItem> list = BaseService.getDictPhysicalExamItemService().findAllOrderByOrderNo();
		if(null != list && list.size()>0){
			for(DictPhysicalExamItem obj:list){
				data_tblPhyExamResultTable.add(new TblPhyExamResultForTableView(
						obj.getItemName(),obj.getRemark(),"",obj.getOrderNo()));
			}
		}
		if(null == animalId || animalId.isEmpty()){
			examResultCol_1.setEditable(false);
		}else{
			examResultCol_1.setEditable(true);
		}
	}
	/**
	 * 更新表格数据tblPhyExamResultTable  
	 * @param sessionId
	 * @param animalId
	 */
	private void updateTblPhyExamResultTable(String sessionId,String animalId){
		data_tblPhyExamResultTable.clear();
		if(null != sessionId && !sessionId.isEmpty() && animalId != null && !animalId.isEmpty()){
			List<TblPhyExamResult> list = BaseService.getTblPhyExamService()
					.getResultListBySessionIdAnimalId(sessionId,animalId);
			if(null != list && list.size()>0){
				for(TblPhyExamResult obj:list){
					data_tblPhyExamResultTable.add(new TblPhyExamResultForTableView(
							obj.getExanItem(),obj.getRemark(),obj.getExamResult(),obj.getOrderNo()));
				}
			}
		}
		if(null == animalId || animalId.isEmpty()){
			examResultCol_1.setEditable(false);
		}else{
			examResultCol_1.setEditable(true);
		}
	}
	
	
	/**
	 * 初始化tblTraceTalbe
	 */
	private void ininTblTraceTalbe() {
		animalIdCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("animalId"));
//		operateModeCode_2.setCellValueFactory(
//				new PropertyValueFactory<TblTraceForTableView, String>("operateMode"));
		oldValueCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("oldValue"));
		newValueCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("newValue"));
		operatorCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("operator"));
		modifyReasonCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("modifyReason"));
		modifyTimeCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("modifyTime"));
		
		tblTraceTable.setItems(data_tblTraceTable);
	}



	/**
	 * 更新tblPhyExamTable数据
	 */
	private void updateTblPhyExamTable(){
		data_tblPhyExamTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			//animalId,gender ,bodyWeight,weightUnit,temperature,recordTime,examResult
			List<?> list = BaseService.getTblPhyExamService().getListBySessionId(sessionId);
			if(null != list && list.size()>0){
				for(int i= 0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					String animalId = (String) obj[0];
					Integer gender = (Integer) obj[1];
					String bodyWeight =  (obj[2] == null ? "":obj[2]) +" "+ (obj[3] == null ? "":obj[3]);
					String temperature = (String) obj[4];
					Date recordTime = (Date) obj[5];
					String examResult = (String) obj[6];
					String id = (String) obj[7];
					data_tblPhyExamTable.add(new TblPhyExamForTableView(animalId,gender,bodyWeight,temperature,recordTime,examResult,id));
				}
			}
		}
	}
	/**
	 * 更新tblPhyExamTable数据(暂无sessionId  使用)
	 */
	private void updateTblPhyExamTable(List<TblSessionAnimals> tblSessionAnimalsList){
		data_tblPhyExamTable.clear();
			if(null != tblSessionAnimalsList && tblSessionAnimalsList.size()>0){
				for(int i= 0;i<tblSessionAnimalsList.size();i++){
					TblSessionAnimals obj = tblSessionAnimalsList.get(i);
					String animalId = obj.getAnimalId();
					Integer gender = obj.getGender();
					String bodyWeight = "";
					String temperature = "";
					Date recordTime = null;
					String examResult = "";
					String id = "";
					data_tblPhyExamTable.add(new TblPhyExamForTableView(animalId,gender,bodyWeight,temperature,recordTime,examResult,id));
				}
			}
	}
	
	/**
	 * 更新TblTraceTable数据
	 */
	private void updateTblTraceTable(){
		data_tblTraceTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			List<TblTrace> tblTraceList = BaseService.getTblTraceservice().
					getListByTableNameAndDataId("TblPhyExam",sessionId);
			if(null != tblTraceList && tblTraceList.size()>0){
				for(TblTrace tblTrace : tblTraceList){
					String newValue = tblTrace.getNewValue();
					String[] newValues = newValue.split(",");
					String animalId = newValues[0];
					switch (tblTrace.getOperateMode()) {
					case 1:
						newValue = newValues[1];
						break;
					case 2:
						newValue ="";
						break;
					case 3:
						newValue = newValues[1];
						break;
					default:
						break;
					}
					data_tblTraceTable.add(new TblTraceForTableView(
							animalId,tblTrace.getOldValue(),newValue,
							tblTrace.getOperator(),tblTrace.getModifyReason(),
							tblTrace.getModifyTime(),tblTrace.getOperateMode()
							));
				}
			}
		}
	}
	/**
	 * 增加修改痕迹数据
	 */
	private void addTblTraceTable(TblTrace tblTrace) {
		if (null != tblTrace) {
			String newValue = tblTrace.getNewValue();
			String[] newValues = newValue.split(",");
			String animalId = newValues[0];
			switch (tblTrace.getOperateMode()) {
			case 1:
				newValue = newValues[1];
				break;
			case 2:
				newValue ="";
				break;
			case 3:
				newValue = newValues[1];
				break;
			default:
				break;
			}
			data_tblTraceTable.add(new TblTraceForTableView(
					animalId,tblTrace.getOldValue(),newValue,
					tblTrace.getOperator(),tblTrace.getModifyReason(),
					tblTrace.getModifyTime(),tblTrace.getOperateMode()
					));
		}
	}
	
	
	/**
	 * 当前动物体检完成
	 */
	@FXML
	private void onAddButton() {
		// 添加
		if (signId == null || "".equals(signId)) {
			// .未签字
			// 1.检查输入项
			if (null == animalId || animalId.isEmpty()) {
				Alert2.create("请先选择动物");
				return;
			}
			if (0 == gender) {
				Alert2.create("请先选择动物");
				return;
			}
			bodyWeight = bodyWeightText.getText().trim();
			if (null == bodyWeight || bodyWeight.isEmpty()) {
				Alert2.create("请先填写'体重'");
				bodyWeightText.requestFocus();
				return;
			}
			if(!NumberValidationUtils.isPositiveRealNumber(bodyWeight)){
				Alert2.create("'体重'格式不正确");
				bodyWeightText.requestFocus();
				return;
			}
			temperature = temperatureText.getText().trim();
			if (null == temperature || temperature.isEmpty()) {
				Alert2.create("请先填写'体温'");
				temperatureText.requestFocus();
				return;
			}
			if(!NumberValidationUtils.isPositiveRealNumber(temperature)){
				Alert2.create("'体温'格式不正确");
				temperatureText.requestFocus();
				return;
			}
			//当前动物检测结果未录入完毕
			boolean isNoFinish =false;
			for(TblPhyExamResultForTableView obj:data_tblPhyExamResultTable){
				String examResult = obj.getExamResult().trim();
				if(examResult == null || examResult.equals("")){
					isNoFinish = true;
					break;
				}
			}
			if(isNoFinish){
				Alert2.create("当前动物检测结果未录入完毕");
				return ;
			}
			
			//1.5 如果无 sessionId ,则先保存 会话和会话动物列表
			if(null == sessionId || "".equals(sessionId)){
				sessionId = BaseService.getTblSessionService().
						saveTblSessionAndAnimalList(tblSession, tblSessionAnimalsList);
				tblSession.setSessionId(sessionId);
			}
			
			
			//检测结果列表
			List<TblPhyExamResult> tblPhyExamResultList = new ArrayList<TblPhyExamResult>();
			TblPhyExamResult tblPhyExamResult =null;
			//检测结果   字符串
			String examResult ="";
			for(TblPhyExamResultForTableView obj:data_tblPhyExamResultTable){
				tblPhyExamResult = new TblPhyExamResult();
				String currentExamItem = obj.getItemName().trim();
				String currentRemark = obj.getRemark().trim();
				int orderNo = obj.getOrderNo();
				String currentExamResult = obj.getExamResult().trim();
				tblPhyExamResult.setAnimalId(animalId);
				tblPhyExamResult.setSessionId(sessionId);
				tblPhyExamResult.setExanItem(currentExamItem);
				tblPhyExamResult.setRemark(currentRemark);
				tblPhyExamResult.setExamResult(currentExamResult);
				tblPhyExamResult.setOrderNo(orderNo);
				tblPhyExamResultList.add(tblPhyExamResult);
				if(!currentExamResult.equals("√")){
					if(examResult.equals("")){
						examResult = currentExamResult;
					}else{
						examResult= examResult+"、"+currentExamResult;
					}
				}
			}
			if(examResult.equals("")){
				examResult = "√";
			}
			// 2.动物体检
			TblPhyExam tblPhyExam = new TblPhyExam();
			tblPhyExam.setSessionId(sessionId);
			tblPhyExam.setAnimalId(animalId);
			tblPhyExam.setGender(gender);
			tblPhyExam.setBodyWeight(bodyWeight);
			tblPhyExam.setTemperature(temperature);
			tblPhyExam.setWeightUnit(weightUnit);
			tblPhyExam.setExamResult(examResult);
			Date date = new Date();
			tblPhyExam.setRecordTime(date);
			tblPhyExam.setTblSession(tblSession);

			if(null == id || id.isEmpty() ){
				 id = BaseService.getTblPhyExamService().saveAndResultList(tblPhyExam,tblPhyExamResultList);
			}else{
				tblPhyExam.setId(id);
				BaseService.getTblPhyExamService().updateAndResultList(tblPhyExam,tblPhyExamResultList);
			}

			// 3.如果是第一个 动物体检， 更新会话里的beginDate
			if (null == beginDate) {
				beginDate = date;
				tblSession.setBeginTime(beginDate);
				BaseService.getTblSessionService().update(tblSession);
				updateFlag = true;
			}

			// 4.tblPhyExamTable表中更新当前行
			TblPhyExamForTableView tblPhyExamForTableView = new TblPhyExamForTableView(
					animalId,gender,bodyWeight+" "+weightUnit,temperature,date,examResult,id);
			int index = tblPhyExamTable.getSelectionModel().getSelectedIndex();
			data_tblPhyExamTable.set(index, tblPhyExamForTableView);
			
			this.examResult = examResult;
			tblPhyExamTable.getSelectionModel().selectNext();
			tblPhyExamTable.scrollTo(index);
			
			//
//			
		} else if (checkId == null || "".equals(checkId)) {
			// // .已签字，未复核
			// 1.检查输入项
			if (null == animalId || animalId.isEmpty()) {
				Alert2.create("请先选择动物");
				return;
			}
			if (0 == gender) {
				Alert2.create("请先选择动物");
				return;
			}
			bodyWeight = bodyWeightText.getText().trim();
			if (null == bodyWeight || bodyWeight.isEmpty()) {
				Alert2.create("请先填写'体重'");
				bodyWeightText.requestFocus();
				return;
			}
			if (!NumberValidationUtils.isPositiveRealNumber(bodyWeight)) {
				Alert2.create("'体重'格式不正确");
				bodyWeightText.requestFocus();
				return;
			}
			temperature = temperatureText.getText().trim();
			if (null == temperature || temperature.isEmpty()) {
				Alert2.create("请先填写'体温'");
				temperatureText.requestFocus();
				return;
			}
			if (!NumberValidationUtils.isPositiveRealNumber(temperature)) {
				Alert2.create("'体温'格式不正确");
				temperatureText.requestFocus();
				return;
			}
			// 当前动物检测结果未录入完毕
			boolean isNoFinish = false;
			for (TblPhyExamResultForTableView obj : data_tblPhyExamResultTable) {
				String examResult = obj.getExamResult().trim();
				if (examResult == null || examResult.equals("")) {
					isNoFinish = true;
					break;
				}
			}
			if (isNoFinish) {
				Alert2.create("当前动物检测结果未录入完毕");
				return;
			}
			// 检测结果列表
			List<TblPhyExamResult> tblPhyExamResultList = new ArrayList<TblPhyExamResult>();
			TblPhyExamResult tblPhyExamResult = null;
			// 检测结果 字符串
			String examResult = "";
			for (TblPhyExamResultForTableView obj : data_tblPhyExamResultTable) {
				tblPhyExamResult = new TblPhyExamResult();
				String currentExamItem = obj.getItemName().trim();
				String currentRemark = obj.getRemark().trim();
				int orderNo = obj.getOrderNo();
				String currentExamResult = obj.getExamResult().trim();
				tblPhyExamResult.setAnimalId(animalId);
				tblPhyExamResult.setSessionId(sessionId);
				tblPhyExamResult.setExanItem(currentExamItem);
				tblPhyExamResult.setRemark(currentRemark);
				tblPhyExamResult.setExamResult(currentExamResult);
				tblPhyExamResult.setOrderNo(orderNo);
				tblPhyExamResultList.add(tblPhyExamResult);
				if (!currentExamResult.equals("√")) {
					if (examResult.equals("")) {
						examResult = currentExamResult;
					} else {
						examResult = examResult + "、" + currentExamResult;
					}
				}
			}
			if (examResult.equals("")) {
				examResult = "√";
			}
			TblPhyExam oldTblPhyExam = BaseService.getTblPhyExamService().getById(id);
			if(oldTblPhyExam.getBodyWeight().equals(bodyWeight) && oldTblPhyExam.getWeightUnit().equals(weightUnit)
				&& oldTblPhyExam.getTemperature().equals(temperature) && oldTblPhyExam.getExamResult().equals(examResult)){
				Alert2.create("请编辑后再保存");
				return ;
			}
			// 2.提示，并电子签名
			if (Confirm
					.create(Main.getInstance().getStage(), "提示", "动物体检已签字，编辑信息将记录修改痕迹", "确定继续吗？")) {
				// 电子签名及原因
				SignFrameWithReason signFrameWithReason = new SignFrameWithReason("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("身份确认");
				try {
					signFrameWithReason.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 签字通过
			if ("OK".equals(SignFrameWithReason.getType())) {
				// 3.保存修改痕迹
				// 动物Id号+“，”+观察所见
				String newValue = animalId + "," +bodyWeight+weightUnit+temperature+"℃"+examResult;
				Date date = new Date();
				TblTrace tblTrace = new TblTrace();
				tblTrace.setTableName("TblPhyExam");// 表名
				tblTrace.setDataId(sessionId); // 这里放的是会话Id
				tblTrace.setOperateMode(1);// 编辑
				tblTrace.setOldValue(oldTblPhyExam.getBodyWeight()+oldTblPhyExam.getWeightUnit()
						+oldTblPhyExam.getTemperature()+"℃"+this.examResult);
				tblTrace.setNewValue(newValue);
				tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
				tblTrace.setHost(SystemTool.getIPAddress());
				tblTrace.setModifyReason(SignFrameWithReason.getReason());
				tblTrace.setModifyTime(date);
				BaseService.getTblTraceservice().save(tblTrace);
				// 4.动物体检
				TblPhyExam tblPhyExam = new TblPhyExam();
				tblPhyExam.setSessionId(sessionId);
				tblPhyExam.setAnimalId(animalId);
				tblPhyExam.setGender(gender);
				tblPhyExam.setBodyWeight(bodyWeight);
				tblPhyExam.setTemperature(temperature);
				tblPhyExam.setWeightUnit(weightUnit);
				tblPhyExam.setExamResult(examResult);
				tblPhyExam.setRecordTime(date);
				tblPhyExam.setTblSession(tblSession);

				tblPhyExam.setId(id);
				BaseService.getTblPhyExamService()
						.updateAndResultList(tblPhyExam, tblPhyExamResultList);
				// 4.tblPhyExamTable表中更新当前行
				TblPhyExamForTableView tblPhyExamForTableView = new TblPhyExamForTableView(
						animalId,gender,bodyWeight+" "+weightUnit,temperature,date,examResult,id);
				int index = tblPhyExamTable.getSelectionModel().getSelectedIndex();
				data_tblPhyExamTable.set(index, tblPhyExamForTableView);
				
				this.examResult = examResult;
//				tblPhyExamTable.getSelectionModel().selectNext();
//				tblPhyExamTable.scrollTo(index);
				// 6. 更新修改痕迹表格
				addTblTraceTable(tblTrace);
			}
			
		} else {
			Alert2.create("已复核，无法修改数据");
			return;
		}
	}
	/**
	 * 其他项目未见异常
	 */
	@FXML
	private void onOthersButton() {
		if(checkId != null && !checkId.isEmpty()){
			Alert2.create("已复核无法操作");
			return;
		}
		if(animalId == null || animalId.isEmpty()){
			Alert2.create("请先选择动物");
			return;
		}
		int index =0;
		for(TblPhyExamResultForTableView obj:data_tblPhyExamResultTable){
			if(obj.getExamResult() == null || obj.getExamResult().isEmpty()){
//				obj.setExamResult("√");
				data_tblPhyExamResultTable.set(index, new TblPhyExamResultForTableView(
						obj.getItemName(),obj.getRemark(),"√",obj.getOrderNo()));
			}
			index++;
		}
	}

	/**
	 * 签字
	 */
	
	@FXML
	private void onSignButton(){
		if(null != signId && !signId.isEmpty()){
			Alert2.create("已签字，不可重复签字！");
			return;
		}
		//是否存在        未体检动物
		boolean isExist = false;
		for(TblPhyExamForTableView obj:data_tblPhyExamTable){
			if(obj.getId() == null || obj.getId().equals("")){
				isExist = true;
				break;
			}
		}
		if(isExist){
			Alert2.create("动物体检还未完成，不可签字");
			return ;
		}
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("签字");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(SignFrame.getType())){
			signId = BaseService.getTblSessionService().sign(sessionId, 14, 
					SignFrame.getUser().getRealName(), "检疫，动物体检签字");
			updateFlag = true;
			Alert.create("签字成功");
		}
	}
	/**
	 * 复核
	 */
	@FXML
	private void onCheckButton(){
		if(null == signId || signId.isEmpty()){
			Alert2.create("未签字，请先签字！");
			return;
		}
		if (null != checkId && !checkId.isEmpty()) {
			Alert2.create("已复核，不可重复复核！");
			return;
		}
		// 是否存在 未体检动物
		boolean isExist = false;
		for (TblPhyExamForTableView obj : data_tblPhyExamTable) {
			if (obj.getId() == null || obj.getId().equals("")) {
				isExist = true;
				break;
			}
		}
		if (isExist) {
			Alert2.create("动物体检还未完成，不可复核");
			return;
		}
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("复核");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(SignFrame.getType())){
			checkId = BaseService.getTblSessionService().check(sessionId, 15, 
					SignFrame.getUser().getRealName(), "检疫，动物体检复核");
			
			updateFlag = true;
			addButton.setDisable(true);
			othersButton.setDisable(true);
			weightUnitComboBox.setDisable(true);
			tblPhyExamResultTable.setEditable(false);
			bodyWeightText.setDisable(true);
			temperatureText.setDisable(true);
			
			Alert.create("复核成功");
		}
		
	}
	
	/**
	 * 兽医确认
	 */
	@FXML
	private void onHorseDoctorCheck(){
		//暂时不做
	}
	/**
	 * 打印
	 */
	
	@FXML
	private void onPrintButton(){
		if(null != checkId && !checkId.isEmpty()){
	 		JasperReport jr = null;
	 		JasperPrint jp = null;
	        
	        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
	        
	        TblSession tblSession  = BaseService.getTblSessionService().getById(sessionId);
	        if(tblSession!=null){
	        	String animalType = tblSession.getAnimalType();
	        	boolean isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(animalType);
	        	//小动物
	        	if(!isBigAnimal){
	        		/**编号*/
	        		String number =BaseService.getDictOutputSettingsService().getShowByLabel("小动物体检记录表-编号");         
	        		String animalStrain=tblSession.getAnimalStrain();//   

	        		String producer="";
	        		String recDate ="";
	        		String room ="";
	        		
	        		String signerAndDate ="";
	        		String checkerAndDate ="";
	        		signId = tblSession.getSignId();
	        		checkId= tblSession.getCheckId();
	        		TblES tblES_signer = BaseService.getTblESService().getById(signId);
	        		TblES tblES_checker =  BaseService.getTblESService().getById(checkId);
	        		if(null != tblES_signer){
	        			signerAndDate =tblES_signer.getSigner()+" "+
	        					DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
	        		}
	        		if(null != tblES_checker){
	        			checkerAndDate =tblES_checker.getSigner()+" "+
	        					DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
	        		}
	        		
	        		Map<String, Object> map = new HashMap<String, Object>();
	        		map.put("logoImage", logoImage);
	        		map.put("number", number);
	        		map.put("animalStrain", animalStrain);
	        		map.put("signerAndDate", signerAndDate);
	        		map.put("checkerAndDate", checkerAndDate);
	        		List<TblPhyExam> tblPhyExamList = BaseService
	        				.getTblPhyExamService().getEntityListBySessionId(sessionId);
		        	if (tblPhyExamList != null && tblPhyExamList.size() > 0) {
			   	     	String recId = tblSession.getRecId();
			   	     	if(null ==recId || recId.equals("")){
			   	     		String animalId = tblPhyExamList.get(0).getAnimalId();
			   	     		recId = BaseService.getTblAnimalRecListService().getRecIdByAnimalId(animalId);
			   	     	}
			   	     	if(null !=recId && !recId.isEmpty()){
			   	     		TblAnimalRecIndex tblAnimalRecIndex =
			   	     				BaseService.getTblAnimalRecIndexService().getById(recId);
				   	     	producer=tblAnimalRecIndex.getProducer();
			        		recDate =DateUtil.dateToString(tblAnimalRecIndex.getRecDate(),
			        				"yyyy-MM-dd");
			        		room =tblAnimalRecIndex.getRoom();
			        		map.put("producer", producer);
			        		map.put("recDate", recDate);
			        		map.put("room", room);
			   	     	}
						int size = tblPhyExamList.size();
						int count = size % 54;
						int max = 0;// 增加空行数量
						if (count != 0) {
							max = 54 - count;
						}
						for (int i = 0; i < max; i++) {
							tblPhyExamList.add(new TblPhyExam());
						}
		        			try {
		        				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblPhyExam_s.jrxml"));
		        				jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
		        						tblPhyExamList));
		        			} catch (JRException e) {
		        				e.printStackTrace();
		        				Alert2.create("报表加载失败");
		        				return;
		        			}
		        			Main.getInstance().openJFrame(jp, "小动物体检记录表");
		        	}
	        	}else{
	        		/**编号*/
	        		String number =BaseService.getDictOutputSettingsService().getShowByLabel("大动物体检记录表-编号");         
	        		String animalStrain=tblSession.getAnimalStrain();//   

	        		String producer="";
	        		String recDate ="";
	        		
	        		String signerAndDate ="";
	        		String checkerAndDate ="";
	        		signId = tblSession.getSignId();
	        		checkId= tblSession.getCheckId();
	        		TblES tblES_signer = BaseService.getTblESService().getById(signId);
	        		TblES tblES_checker =  BaseService.getTblESService().getById(checkId);
	        		if(null != tblES_signer){
	        			signerAndDate =tblES_signer.getSigner()+" "+
	        					DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
	        		}
	        		if(null != tblES_checker){
	        			checkerAndDate =tblES_checker.getSigner()+" "+
	        					DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
	        		}
	        		
	        		Map<String, Object> map = new HashMap<String, Object>();
	        		map.put("logoImage", logoImage);
	        		map.put("number", number);
	        		map.put("animalStrain", animalStrain);
	        		map.put("signerAndDate", signerAndDate);
	        		map.put("checkerAndDate", checkerAndDate);
	        		List<?> list = BaseService.getTblPhyExamService()
	        				.getListBySessionId(sessionId);
		        	if (list != null && list.size() > 0) {
			   	     	String recId = tblSession.getRecId();
			   	     	if(null ==recId || recId.equals("")){
			   	     		String animalId = (String) ((Object[])list.get(0))[0];
			   	     		recId = BaseService.getTblAnimalRecListService().getRecIdByAnimalId(animalId);
			   	     	}
			   	     	if(null !=recId && !recId.isEmpty()){
			   	     		TblAnimalRecIndex tblAnimalRecIndex =
			   	     				BaseService.getTblAnimalRecIndexService().getById(recId);
				   	     	producer=tblAnimalRecIndex.getProducer();
			        		recDate =DateUtil.dateToString(tblAnimalRecIndex.getRecDate(),
			        				"yyyy-MM-dd");
			        		map.put("producer", producer);
			        		map.put("recDate", recDate);
			   	     	}
			   	     	//数据集
			   	     	List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
			   	     	Map<String,Object> dataMap =null;
				   	    for(int i= 0;i<list.size();i++){
				   	    		dataMap = new HashMap<String, Object>();
				   	    		
								Object[] obj = (Object[]) list.get(i);
								String animalId = (String) obj[0];
								Integer gender = (Integer) obj[1];
								String bodyWeight =  (String) obj[2];
								String temperature = (String) obj[4];
								String room = (String) obj[8];
								dataMap.put("animalId", animalId);
								dataMap.put("gender", gender);
								dataMap.put("bodyWeight", bodyWeight);
								dataMap.put("temperature", temperature);
								dataMap.put("room", room);
								List<TblPhyExamResult> tblPhyExamResultList = BaseService.getTblPhyExamService()
										.getResultListBySessionIdAnimalId(sessionId, animalId);
								if(null != tblPhyExamResultList && tblPhyExamResultList.size()>0){
									int index = 1;
									for(TblPhyExamResult result:tblPhyExamResultList){
										dataMap.put("item"+index, result.getExamResult());
										index++;
									}
								}
								mapList.add(dataMap);
						}
			        	try {
			        		jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblPhyExam_l.jrxml"));
			        		jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(mapList));
			        	} catch (JRException e) {
			        		e.printStackTrace();
			        		Alert2.create("报表加载失败");
			        		return;
			        	}
			        	Main.getInstance().openJFrame(jp, "大动物体检记录表");
		        	}
	        		
	        	}
	     		
	        }
		}else{
			Alert2.create("未复核，复核后才可以打印");
		}
	}
	
	/**根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String,String>> getMapList(List<TblGeneralObservation> tblGeneralObservationList){
		if(null != tblGeneralObservationList && tblGeneralObservationList.size()>0){
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
			Map<String,String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();
			for(TblGeneralObservation obj: tblGeneralObservationList){
				String animalId = obj.getAnimalId();
				String observation = obj.getObservation();
				if(!animalIdList.contains(animalId)){
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				}else{
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2+"、"+observation);
				}
			}
			Map<String,String> map =null;
			for(String animalId:animalIdList){
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		} 
		return null;
	}
	/**根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String,String>> getMapListByList(List<?> list){
		if(null != list && list.size()>0){
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
			Map<String,String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();
			
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				String animalId = (String) obj[0];
				String observation = (String) obj[1];
				if(!animalIdList.contains(animalId)){
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				}else{
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2+"、"+observation);
				}
			}
			Map<String,String> map =null;
			for(String animalId:animalIdList){
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		} 
		return null;
	}
	
	/**
	 * 关闭
	 */
	@FXML
	private void onExitButton(ActionEvent event){
		
		if((null == sessionId || "".equals(sessionId)) && 
				!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
			
		}else{
			//2.判断是否需要更新DayToDayPane上tblSession表数据
			if(updateFlag){
				DayToDayController.getInstance().loadData();
			}
			//3.关闭窗口
			
			Button button = (Button) event.getSource();
			button.getScene().getWindow().hide();
		}
	}
	/**
	 * 加载数据，控件，表格
	 * @param sessionId
	 */
	public void loadData(String sessionId){
		PhyExamFrame.sessionId =sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		PhyExamFrame.tblSessionAnimalsList=null;
		if(null!=tblSession){
			
			//1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(
					tblSession.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if(null != studyNo && !"".equals(studyNo)){
				studyNoLabel.setText("课题编号："+studyNo);
			}else{
				studyNoLabel.setText("接收批号："+tblSession.getRecId());
			}
			if(tblSession.getAnimalType().equals("犬")||tblSession.getAnimalType().equals("猴")){
				weightUnitComboBox.getSelectionModel().select(0);
			}else{
				weightUnitComboBox.getSelectionModel().select(1);
			}
			animalId ="";
			temperature="";
			bodyWeight="";
			examResult="";
			gender =0;
			//2.复核后 禁用  addButton ,othersButton, weightUnitComboBox  禁编辑tblPhyExamResultTable
			signId =tblSession.getSignId();
//			if(null != signId && !"".equals(signId)){
//				//签字后禁用   othersButton
//				
//			}
			checkId =tblSession.getCheckId();
			if(null != checkId && !"".equals(checkId)){
				addButton.setDisable(true);
				othersButton.setDisable(true);
				weightUnitComboBox.setDisable(true);
				tblPhyExamResultTable.setEditable(false);
				bodyWeightText.setDisable(true);
				temperatureText.setDisable(true);
			}
			beginDate =tblSession.getBeginTime();
			//3.更新tblPhyExamTable数据
			updateTblPhyExamTable();
			
			//4.加载修改痕迹表格数据 
			updateTblTraceTable();
			//5.更新表格数据tblPhyExamResultTable
			updateTblPhyExamResultTable();
		}
	}
	/**
	 * 加载数据，控件，表格（暂无sessionId时用）
	 * @param sessionId
	 */
	public void loadDataByTblSession(TblSession tblSession,List<TblSessionAnimals> tblSessionAnimalsList){
		PhyExamFrame.sessionId =tblSession.getSessionId();;
		PhyExamFrame.tblSession = tblSession;
		PhyExamFrame.tblSessionAnimalsList=tblSessionAnimalsList;
		if(null!=tblSession){
			
			//1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(
					tblSession.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if(null != studyNo && !"".equals(studyNo)){
				studyNoLabel.setText("课题编号："+studyNo);
			}else{
				studyNoLabel.setText("接收批号："+tblSession.getRecId());
			}
			if(tblSession.getAnimalType().equals("犬")||tblSession.getAnimalType().equals("猴")){
				weightUnitComboBox.getSelectionModel().select(0);
			}else{
				weightUnitComboBox.getSelectionModel().select(1);
			}
			animalId ="";
			temperature="";
			bodyWeight="";
			examResult="";
			gender =0;
			
			signId = null;
			checkId=null;
			beginDate =null;
			//3.更新tblPhyExamTable数据
			updateTblPhyExamTable(tblSessionAnimalsList);
			
			data_tblTraceTable.clear();
			
			//5.更新表格数据tblPhyExamResultTable
			updateTblPhyExamResultTable();
			
		}
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(this.getClass().getResource("PhyExam.fxml"));
		Scene scene = new Scene(root,768,640);
		stage.setScene(scene);
		stage.setTitle("动物体检");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				
				if((null == sessionId || "".equals(sessionId)) && 
						!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
					event.consume();
				}else{
					//2.判断是否需要更新DayToDayPane上tblSession表数据
					if(updateFlag){
						DayToDayController.getInstance().loadData();
					}
				}
				
			}});
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public class TextFieldTableCellImpl extends TableCell<TblPhyExamResultForTableView, String> {

		private TextField textField;

		public TextFieldTableCellImpl() {
			super();
		}

		@Override
		public void startEdit() {
			super.startEdit();
//			if (textField == null) {
				createTextField();
//			}
			textField.setEditable(true);
			setText(null);
			setGraphic(textField);
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(null);
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);

				} else {
					getTableView().getItems().get(getIndex()).setExamResult(getString());
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}

			});
			textField.setOnMouseMoved(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					textField.requestFocus();
					textField.end();
				}
			});
			getTableRow().selectedProperty().addListener(new ChangeListener<Boolean>(){

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
						Boolean newValue) {
					if(!newValue){
						commitEdit(textField.getText());
					}
					
				}});
			getTableView().setOnMouseExited(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					commitEdit(textField.getText());
				}});
		}

		@Override
		public void commitEdit(String text) {
			if(text.getBytes().length>100){
				return ;
			}
			super.commitEdit(text);
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}
