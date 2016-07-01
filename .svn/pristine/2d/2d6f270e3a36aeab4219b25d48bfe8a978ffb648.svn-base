package com.lanen.view.clinicaltest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemMessage;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AuditFrame extends Application implements Initializable{

	@FXML
	private TableView<LogTable> tblLogTable; //日志表格
	//日志表格数据集
	private static  ObservableList<LogTable> data_tblLogTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<LogTable, String> operatTypeCol;
	@FXML
	private TableColumn<LogTable, String> operatObjectCol;
	@FXML
	private TableColumn<LogTable, String> operatorCol;
	@FXML
	private TableColumn<LogTable, String> operatTimeCol;
	@FXML
	private TableColumn<LogTable, String> operatHostCol;
	@FXML
	private TableColumn<LogTable, String> operatContentCol;
	@FXML
	private TableColumn<LogTable, String> remarkCol;
	//日期选择器
	@FXML
	private HBox beginDateHBox;
	@FXML
	private HBox endDateHBox;
	DatePicker  beginPane=null;
	DatePicker  endPane=null;
	@FXML
	private ComboBox<String> operatHostCombobox;//操作位置下拉框
	private ObservableList<String> data_operatHostCombobox=FXCollections.observableArrayList() ;
	
	//修改痕迹表
	@FXML
	private TableView<TraceTable> tblTraceTable;
	private static ObservableList<TraceTable> data_tblTraceTable =FXCollections.observableArrayList();
	
//	private StringProperty studyNo; // 
//	private StringProperty reqNo; // 
//	private StringProperty testItem; // 
//	private StringProperty specimenCode;
//	private StringProperty testIndex; // 
//	private StringProperty collectionTime;
//	private StringProperty operatMode;
//	private StringProperty oldValue; // 
//	private StringProperty newValue; // 
//	private StringProperty editTime; // 
//	private StringProperty editReason; // 
//	private StringProperty operatComputer; // 
	@FXML
	private TableColumn<TraceTable, String> studyNoCol;
	@FXML
	private TableColumn<TraceTable, String> reqNoCol;
	@FXML
	private TableColumn<TraceTable, String> testItemCol;
	@FXML
	private TableColumn<TraceTable, String> specimenCodeCol;
	@FXML
	private TableColumn<TraceTable, String> testIndexCol;
	@FXML
	private TableColumn<TraceTable, String> collectionTimeCol;
	@FXML
	private TableColumn<TraceTable, String> operatModeCol;
	@FXML
	private TableColumn<TraceTable, String> oldValueCol;
	@FXML
	private TableColumn<TraceTable, String> newValueCol;
	@FXML
	private TableColumn<TraceTable, String> editTimeCol;
	@FXML
	private TableColumn<TraceTable, String> editReasonCol;
	@FXML
	private TableColumn<TraceTable, String> operatComputerCol;
	
	@FXML
	private TextField studyNoText;
	@FXML
	private ComboBox<String> reqNoCombobox;
	private  ObservableList<String> data_reqNoCombobox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> testItemCombobox;
	private  ObservableList<String> data_testItemCombobox = FXCollections.observableArrayList();
	
	private static String studyNo ="";
	private int reqNo = 0;
	private int testItem = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//初始化日志表
		initTblLogTable();
		//初始化日期选择器
		initDatePane();
		//初始化操作位置下拉框
		initCombobox();
		
		//初始化修改痕迹表
		initTblTraceTable();
		
		//初始化修改痕迹页面其他控件
		initOthers();
	}
	//初始化修改痕迹页面其他控件
	private void initOthers() {
		String studyNo =AuditFrame.studyNo;
		//课题编号文本框
		studyNoText.setText(studyNo);
		
		//检测项目下拉框
		data_testItemCombobox.add("全部");    //0
		data_testItemCombobox.add("生化检验");    //1
		data_testItemCombobox.add("血液检验");  //2
		data_testItemCombobox.add("血凝检验");    //3
		data_testItemCombobox.add("尿液检验");  //4
		testItemCombobox.setItems(data_testItemCombobox);
		testItemCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					if("全部".equals(newValue)){
						testItem =0;
					}else if("生化检验".equals(newValue)){
						testItem =1;
					}else if("血液检验".equals(newValue)){
						testItem =2;
					}else if("血凝检验".equals(newValue)){
						testItem =3;
					}else if("尿液检验".equals(newValue)){
						testItem =4;
					}
					//更新修改痕迹表格
					updateTblTraceTable(AuditFrame.studyNo,reqNo,testItem);
				}
				
			}
			
		});
		//申请编号下拉框
		if(!"".equals(studyNo)){
			reqNoCombobox.setDisable(false);
			testItemCombobox.setDisable(false);
			
			data_reqNoCombobox.add("全部");   //0
			TblStudyPlan tblStudyPlan = new TblStudyPlan();
			tblStudyPlan.setStudyNo(studyNo);
			List<TblClinicalTestReq> list = BaseService.getTblClinicalTestReqService().getSetByStudyPlan(tblStudyPlan);
			if(null!=list && list.size()>0){
				for(TblClinicalTestReq obj:list){
					if(obj.getEs()==1){
						data_reqNoCombobox.add(obj.getReqNo()+"");
					}
				}
			}
			reqNoCombobox.setItems(data_reqNoCombobox);
			reqNoCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(null!=newValue && !"".equals(newValue)){
						if(newValue.equals("全部")){
							reqNo =0;
							//更新修改痕迹表格
							updateTblTraceTable(AuditFrame.studyNo,reqNo,testItem);
						}else{
							reqNo =Integer.parseInt(newValue);
							//更新修改痕迹表格
							updateTblTraceTable(AuditFrame.studyNo,reqNo,testItem);
						}
					}
				}
				
			});
			
			//更新修改痕迹表格
			updateTblTraceTable(AuditFrame.studyNo,reqNo,testItem);
		}
	}

	// 更新修改痕迹表的值
	private void updateTblTraceTable(String studyNo, int reqNo, int testItem) {
		// 课题编号，申请编号，检测项目，检验编号，检验指标缩写，检测完成时间
		data_tblTraceTable.clear();
		if (null != studyNo && !"".equals(studyNo)) {

			// 痕迹列表
			List<TblTrace> tblTraceList = BaseService
					.getTblTraceService()
					.getTblClinicalTestDataTraceListByStudyNoReqNoTestItem(studyNo, reqNo, testItem);
			if (null != tblTraceList && tblTraceList.size() > 0) {
				for (TblTrace tblTrace : tblTraceList) {
					String newValue = tblTrace.getNewValue();
					String[] newValues = newValue.split(",");
					String studyNo1 = newValues[0];
					String reqNo1 = newValues[1];
					int testItem1 = Integer.parseInt(newValues[2]);
					String specimenCode = newValues[3];
					String testIndex = newValues[4];
					String collectionsTime = newValues[5];
					;
					int sperateMode = tblTrace.getOperateMode();
					String oldValue = tblTrace.getOldValue();
					Date editDate = tblTrace.getModifyTime();
					String editReason = tblTrace.getModifyReason();
					String host = tblTrace.getHost();
					data_tblTraceTable.add(new TraceTable(studyNo1, reqNo1, testItem1,
							specimenCode, testIndex, collectionsTime, sperateMode, oldValue, "",
							editDate, editReason, host));
				}
			}
		}
	}
	//初始化操作位置下拉框
	private void initCombobox() {
		List<String> operatHostList = BaseService.getTblLogService().getOperatHostListBySystemName(SystemMessage.getSystemName());
//		List<String> operatHostList = BaseService.getTblLogService().getOperatHostListBySystemName("临床检验管理系统");
		if(null!=operatHostList && operatHostList.size()>0){
			for(String str :operatHostList){
				data_operatHostCombobox.add(str);
			}
		}
		operatHostCombobox.setItems(data_operatHostCombobox);
		operatHostCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue){
					updateTblLogTable(beginPane.getTextField().getText(), endPane.getTextField().getText(), newValue);
				}
				
			}
			
		});
	}
	//初始化修改痕迹表
	private void initTblTraceTable() {
		studyNoCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("studyNo"));
		reqNoCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("reqNo"));
		testItemCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("testItem"));
		specimenCodeCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("specimenCode"));
		testIndexCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("testIndex"));
		collectionTimeCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("collectionTime"));
		operatModeCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("operatMode"));
		oldValueCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("oldValue"));
		newValueCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("newValue"));
		editTimeCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("editTime"));
		editReasonCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("editReason"));
		operatComputerCol.setCellValueFactory(new PropertyValueFactory<TraceTable,String>("operatComputer"));
		tblTraceTable.setItems(data_tblTraceTable);
	}
	//初始化日志表
	private void initTblLogTable() {
		operatTypeCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operatType"));
		operatObjectCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operatObject"));
		operatorCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operator"));
		operatTimeCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operatTime"));
		operatHostCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operatHost"));
		operatContentCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("operatContent"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<LogTable,String>("remark"));
		tblLogTable.setItems(data_tblLogTable);
	}
	//初始化日志表的值
	private void updateTblLogTable(String beginDateStr, String endDateStr, String operatHost) {
		if(beginDateStr==null&&"".equals(beginDateStr)){
			return ;
		}
		if(endDateStr==null&&"".equals(endDateStr)){
			return ;
		}
		if(null==operatHost){
			operatHost="";
		}
		//日期格式验证
		if(!beginDateStr.matches("[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}")||!endDateStr.matches("[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}")){
			return;
		}
		//系统日志列表
		List<TblLog> list = BaseService.getTblLogService().getListByDateHostSystemName(beginDateStr,endDateStr,
				operatHost,SystemMessage.getSystemName());
		data_tblLogTable.clear();
		if(null!=list&& list.size()>0){
			for(TblLog tblLog:list){
				data_tblLogTable.add(new LogTable(tblLog.getOperatType(),tblLog.getOperatOject(),
						tblLog.getOperator(),tblLog.getOperatTime(),
						tblLog.getOperatContent(),tblLog.getOperatHost(),tblLog.getRemark()));
				
			}
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AuditFrame.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setTitle("审计");
		//stage.setResizable(false);最大化
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);

	}
	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		beginPane = new DatePicker(Locale.CHINA);
		beginPane.getTextField().setEditable(false);
		beginPane.getTextField().setMaxWidth(90);
		beginPane.getTextField().setMinWidth(90);
		beginPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		beginPane.getCalendarView().todayButtonTextProperty().set("今天");
		beginPane.getCalendarView().setShowWeeks(false);
		beginPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		beginPane.setMaxWidth(90);
		beginDateHBox.getChildren().add(beginPane);
		
		endPane = new DatePicker(Locale.CHINA);
		endPane.getTextField().setEditable(false);
		endPane.getTextField().setMaxWidth(90);
		endPane.getTextField().setMinWidth(90);
		endPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endPane.getCalendarView().todayButtonTextProperty().set("今天");
		endPane.getCalendarView().setShowWeeks(false);
		endPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endPane.setMaxWidth(90);
		endDateHBox.getChildren().add(endPane);
		//
		beginPane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					updateTblLogTable(newValue, endPane.getTextField().getText(),operatHostCombobox.getSelectionModel().getSelectedItem());
				}else{
					updateTblLogTable("", "",operatHostCombobox.getSelectionModel().getSelectedItem());
				}
			}
			});
		endPane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					updateTblLogTable(beginPane.getTextField().getText(), newValue,operatHostCombobox.getSelectionModel().getSelectedItem());
				}else{
					updateTblLogTable("", "",operatHostCombobox.getSelectionModel().getSelectedItem());
				}
			}

		});
		String endDateStr = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String beginDateStr = DateUtil.getDateAgo(7);
		beginPane.getTextField().setText(beginDateStr);
		endPane.getTextField().setText(endDateStr);
	}
	//日志表
	public class LogTable{
		private StringProperty operatType;     //操作类型
		private StringProperty operatObject;    //操作对象
		private StringProperty operator;       //操作者
		private StringProperty operatTime;       //操作时间
		private StringProperty operatContent;  //操作内容
		private StringProperty operatHost;     //操作位置
		private StringProperty remark;         //备注
		
		public LogTable(){
			super();
		}
		public LogTable(String operatType,String operatObject, String operator,       
				Date operatTime,String operatContent,String operatHost,String remark ){
			this.operatType=new SimpleStringProperty(operatType);
    	    this.operatObject=  new SimpleStringProperty(operatObject);
    	    this.operator  =  new SimpleStringProperty(operator );
    	    this.operatTime =  new SimpleStringProperty(operatTime !=null ? DateUtil.dateToString(operatTime, "yyyy-MM-dd"):"");
    	    this.operatHost			=  new SimpleStringProperty(operatHost)  ;
    	    this.operatContent			=  new SimpleStringProperty(operatContent)  ;
    	    this.remark		=  new SimpleStringProperty(remark)  ;
		}
		public String getOperatType() {
			return operatType.get();
		}
		public void setOperatType(String operatType) {
			this.operatType = new SimpleStringProperty(operatType);
		}
		public String getOperatObject() {
			return operatObject.get();
		}
		public void setOperatObject(String operatObject) {
			this.operatObject = new SimpleStringProperty(operatObject);
		}
		public String getOperator() {
			return operator.get();
		}
		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}
		public String getOperatTime() {
			return operatTime.get();
		}
		public void setOperatTime(String operatTime) {
			this.operatTime = new SimpleStringProperty(operatTime);
		}
		public String getOperatContent() {
			return operatContent.get();
		}
		public void setOperatContent(String operatContent) {
			this.operatContent = new SimpleStringProperty(operatContent);
		}
		public String getOperatHost() {
			return operatHost.get();
		}
		public void setOperatHost(String operatHost) {
			this.operatHost = new SimpleStringProperty(operatHost);
		}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		
	}

	// 修改痕迹表
	public class TraceTable {
		private StringProperty studyNo; // 
		private StringProperty reqNo; // 
		private StringProperty testItem; // 
		private StringProperty specimenCode;
		private StringProperty testIndex; // 
		private StringProperty collectionTime;
		private StringProperty operatMode;
		private StringProperty oldValue; // 
		private StringProperty newValue; // 
		private StringProperty editTime; // 
		private StringProperty editReason; // 
		private StringProperty operatComputer; // 

		public TraceTable() {
			super();
		}
		public TraceTable(String studyNo,String reqNo,int testItem,String specimenCode,String testIndex,
				String collectionTime,int operatMode,
				String oldValue,String newValue,Date editTime,String editReason,String operatComputer) {
			this.studyNo=new SimpleStringProperty(studyNo);
			this.reqNo=new SimpleStringProperty(reqNo);
			switch (testItem) {
				case 1:this.testItem=new SimpleStringProperty("生化检验");
					break;
				case 2:this.testItem=new SimpleStringProperty("血液检验");
					break;
				case 3:this.testItem=new SimpleStringProperty("血凝检验");
					break;
				case 4:this.testItem=new SimpleStringProperty("尿液检验");
					break;
				default:
					break;
			}
			setSpecimenCode(specimenCode);
			
			this.testIndex=new SimpleStringProperty(testIndex);
			setCollectionTime(collectionTime);
			setOperatMode(operatMode);
			this.oldValue=new SimpleStringProperty(oldValue);
			this.newValue=new SimpleStringProperty(newValue);
			this.editTime=new SimpleStringProperty(editTime !=null ? DateUtil.dateToString(editTime, "yyyy-MM-dd"):"");
			this.editReason=new SimpleStringProperty(editReason);
			this.operatComputer=new SimpleStringProperty(operatComputer);
			
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}
		public String getReqNo() {
			return reqNo.get();
		}
		public void setReqNo(String reqNo) {
			this.reqNo = new SimpleStringProperty(reqNo);
		}
		public String getTestItem() {
			return testItem.get();
		}
		public void setTestItem(String testItem) {
			this.testItem = new SimpleStringProperty(testItem);
		}
		public String getTestIndex() {
			return testIndex.get();
		}
		public void setTestIndex(String testIndex) {
			this.testIndex = new SimpleStringProperty(testIndex);
		}
		public String getOldValue() {
			return oldValue.get();
		}
		public void setOldValue(String oldValue) {
			this.oldValue = new SimpleStringProperty(oldValue);
		}
		public String getNewValue() {
			return newValue.get();
		}
		public void setNewValue(String newValue) {
			this.newValue = new SimpleStringProperty(newValue);
		}
		public String getEditTime() {
			return editTime.get();
		}
		public void setEditTime(String editTime) {
			this.editTime = new SimpleStringProperty(editTime);
		}
		public String getEditReason() {
			return editReason.get();
		}
		public void setEditReason(String editReason) {
			this.editReason = new SimpleStringProperty(editReason);
		}
		public String getOperatComputer() {
			return operatComputer.get();
		}
		public void setOperatComputer(String operatComputer) {
			this.operatComputer = new SimpleStringProperty(operatComputer);
		}
		public String getSpecimenCode() {
			return specimenCode.get();
		}
		public void setSpecimenCode(String specimenCode) {
			this.specimenCode = new SimpleStringProperty(specimenCode);
		}
		public String getCollectionTime() {
			return collectionTime.get();
		}
		public void setCollectionTime(String collectionTime) {
			this.collectionTime = new SimpleStringProperty(collectionTime);
		}
		public String getOperatMode() {
			return operatMode.get();
		}
		public void setOperatMode(int operatMode) {
			this.operatMode = new SimpleStringProperty(operatMode ==2 ? "删除":"修改") ;
		}
		
		
	}

	public static String getStudyNo() {
		return studyNo;
	}

	public static void setStudyNo(String studyNo) {
		AuditFrame.studyNo = studyNo;
	}
	
	
}
