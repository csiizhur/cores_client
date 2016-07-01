package com.lanen.view.main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.path.TblPathStudyIndex;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.balreg.AddTblBalCalibration;
import com.lanen.view.balreg.BalConnect;
import com.lanen.view.balreg.BalanceRge;
import com.lanen.view.balreg.ChipReaderController;
import com.lanen.view.balreg.ParameterSetting;
import com.lanen.view.balreg.TblBalCalibrationIndexController;
import com.lanen.view.privilege.UserManagePage;
import com.lanen.view.test.AdditionalPage;
import com.lanen.view.test.AnatomyReqPage;
import com.lanen.view.test.AnimalChipSelectPage;
import com.lanen.view.test.DataEditPage;
import com.lanen.view.test.HistopathCheckPage;
import com.lanen.view.test.HistopathReview;
import com.lanen.view.test.PathSessionPage;
import com.lanen.view.test.SelectStudyNoPage;
import com.lanen.view.test.TaskDetailInfoPage;
import com.lanen.view.test.TaskInfoPage;
import com.lanen.view.test.TissueSliceBatch;
import com.lanen.view.test.TissueSlicePage;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class MainPageController implements Initializable {

	//Ctrl键 被按下
	private boolean ctrPressed = false;
	
//	private Stage stage ;
	@FXML
	private Label msg11Label;//共选择多少任务，或
	@FXML
	private Label msg21Label;
	@FXML
	private Label msg31Label;
	@FXML
	private Label msg12Label;//共选择多少任务，或
	@FXML
	private Label msg22Label;
	@FXML
	private Label msg32Label;
	
	@FXML
	private Label userNameLable;		//当前用户姓名
	@FXML
	private MenuItem mi_userManage;		//系统_用户管理	菜单项
	@FXML
	private MenuItem mi_changePassword;	//系统_修改密码	菜单项
	@FXML
	private MenuItem mi_changeUser;		//系统_切换用户	菜单项
	@FXML
	private MenuItem mi_log;			//系统_系统日志	菜单项
	@FXML
	private MenuItem mi_exit;			//系统_退出		菜单项
	
	@FXML
	private MenuItem mi_dataEdit;		//原始数据_数据修改	菜单项
	
	
	@FXML
	private Button newTaskButton;		//新建任务 button
	@FXML
	private Button taskInfoButton;		//任务信息 button
	@FXML
	private Button tissueSliceButton;		//组织取材编号 button
	@FXML
	private Button histopathCheckButton;		//组织学检查 button
	
	@FXML
	private MenuItem tissueSliceMenuItem;		//组织取材编号  菜单项
	@FXML
	private MenuItem histopathCheckMenuItem;		//组织学检查  菜单项
	@FXML
	private MenuItem tissueSliceBatchMenuItem;		//组织取材（批次）菜单项
	
	
	@FXML
	private MenuItem mi_balCalibration; //原始数据_天平校准  菜单项
	
	@FXML
	private MenuItem tl_BalanceRrg;		//工具_天平登记	菜单项
	
	@FXML
	private MenuItem mi_onNewTaskButton; //原始数据_天平校准  菜单项
	
	//---------
	//日期查询
	@FXML
	private HBox beginHbox;
	@FXML
	private HBox endHbox;
	DatePicker  beginPane=null;
	DatePicker  endPane=null;
	@FXML
	private TabPane tabPane;//按专题/按任务
	private int currentTab = 0;//按专题
	@FXML
	private TreeView<String> taskTree;//任务树
	private TreeItem<String> rootNode=new TreeItem<String>();//clinicalTestReqTree 根节点
	//存放树中的    " 课题编号，任务序号:创建日期（yyyy-MM-dd）" ："任务id"
	/*
	 * 父节点+“，”+叶子节点  ——> 任务Id
	 */
	private Map<String,String> studyNoAnatomyNumTaskIdMap = new HashMap<String,String>();
	/*
	 * 父节点  ——> 专题编号
	 */
	private Map<String,String> studyNoAnatomyNumStudyNoMap = new HashMap<String,String>();
	@FXML
	private TableView<AnatomyTask> anatomyTaskTable;		//任务表
	private ObservableList<AnatomyTask> data_anatomyTaskTable = FXCollections.observableArrayList();

	@FXML
	private TableColumn<AnatomyTask,String> studyNoCol;        //专题编号
	@FXML
	private TableColumn<AnatomyTask,String> anatomyNumCol;      //解剖任务序号
	@FXML
	private TableColumn<AnatomyTask,String> taskCreateDateCol;        //任务创建日期
	@FXML
	private TableColumn<AnatomyTask,String> taskCreaterCol;      //任务创建者
	@FXML
	private TableColumn<AnatomyTask,String> planBeginDateCol;      //解剖解剖日期
	
	private static List<Map<String,Object>> taskDataList;        //存放根据日期查询的任务信息
	
	
	
	//----------------------会话相关----------------------- 
	@FXML
	private ComboBox<String> sessionTypeComboBox;	//会话类型
	@FXML
	private Button createSessionButton;		//创建会话
	@FXML
	private Button openSessionButton;		//打开会话
	@FXML
	private MenuItem createSessionMenuItem;		//创建会话  菜单项
	@FXML
	private MenuItem openSessionMenuItem;		//打开会话  菜单项
	
	// 会话表
	@FXML
	private TableView<PathSession> sessionTable;
	private ObservableList<PathSession> data_sessionTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<PathSession,String> studyNoCol2;
	@FXML
	private TableColumn<PathSession,String> sessionTypeCol;
	@FXML
	private TableColumn<PathSession,String> sessionCreatorCol;
	@FXML
	private TableColumn<PathSession,String> createTimeCol;
	@FXML
	private TableColumn<PathSession,String> finishSignerCol;
	@FXML
	private TableColumn<PathSession,String> finishSignDateCol;
	
	
	/**
	 * 搜索框
	 */
	@FXML
	private TextField searchText;
	/**
	 * 搜索按钮
	 */
	@FXML
	private Button searchBtn;
	
	private static MainPageController instance;

	public static MainPageController getInstance() {
	   return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化 sessionTypeComboBox
		initSessionTypeComboBox();
		//初始化日期区间
		initDatePane();
		//初始化 tabPane
		initTabPane();
		//初始化任务树
		initTaskTree();
		//初始化 table
		initTaskTable();
		//初始化sessionTable
		initSessionTable();
		//初始化权限
		initMenuItem();
	}
	
	
	
	/**
	 * 初始化权限
	 */
	private void initMenuItem(){
		String userId = SaveUserInfo.getUser().getId();
		boolean ishave  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "病理负责人");
		if(ishave){
		     mi_userManage.setDisable(false);
		     mi_dataEdit.setDisable(false);
		}else{
			 mi_userManage.setDisable(true);
			 mi_dataEdit.setDisable(true);
		}
		
		boolean onCreateTempReq  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_创建临时任务");
		boolean onCreateAnatomyTask  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_创建任务");
		if(onCreateTempReq || onCreateAnatomyTask){
			newTaskButton.setDisable(false);
			mi_onNewTaskButton.setDisable(false);
		}else{
			newTaskButton.setDisable(true);
			mi_onNewTaskButton.setDisable(true);
		}
		
	}
	
	
	/**
	 * 初始化 sessionTypeComboBox
	 */
	private void initSessionTypeComboBox() {
		//会话类型选择 "全部会话"
		sessionTypeComboBox.getSelectionModel().selectFirst();
		sessionTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){ 
					//更新会话表格数据
					updateSessionTable();
				}
			}
		});
	}
	/**
	 * 初始化 tabPane
	 */
	private void initTabPane() {
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					currentTab = newValue.intValue();
				}
				
			}});
		
	}
	

	
	/**
	 * 初始化日期区间
	 */
	private void initDatePane() {
		beginPane = new DatePicker(Locale.CHINA);
		beginPane.getTextField().setEditable(false);
		beginPane.getTextField().setFocusTraversable(true);
		beginPane.getTextField().setMaxWidth(100);
		beginPane.getTextField().setMinWidth(100);
		beginPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		beginPane.getCalendarView().todayButtonTextProperty().set("今天");
		beginPane.getCalendarView().setShowWeeks(false);
		beginPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		beginPane.setMaxWidth(100);
		beginHbox.getChildren().add(beginPane);
		beginPane.getTextField().setFocusTraversable(false);
		
		endPane = new DatePicker(Locale.CHINA);
		endPane.getTextField().setEditable(false);
		endPane.getTextField().setFocusTraversable(true);
		endPane.getTextField().setMaxWidth(100);
		endPane.getTextField().setMinWidth(100);
		endPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endPane.getCalendarView().todayButtonTextProperty().set("今天");
		endPane.getCalendarView().setShowWeeks(false);
		endPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endPane.setMaxWidth(100);
		endHbox.getChildren().add(endPane);
		endPane.getTextField().setFocusTraversable(false);
		
		beginPane.getTextField().setText(DateUtil.getDateAgo(30));
		endPane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
		
		beginPane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTaskTreeAndTaskTable();
				}
			}
			
		});
		endPane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTaskTreeAndTaskTable();
				}
			}
			
		});
	}

	/**
	 * 初始化 table
	 */
	private void initTaskTable() {
		anatomyTaskTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		anatomyTaskTable.setItems(data_anatomyTaskTable);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("studyNo"));
		anatomyNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("anatomyNum"));
		taskCreateDateCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreateDate"));
		taskCreaterCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreater"));
		planBeginDateCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("planBeginDate"));
		
		anatomyTaskTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<AnatomyTask>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyTask> arg0, AnatomyTask arg1,
					AnatomyTask newValue) {
				// 任务table chang事件
				//1.取消taskTree 的选中项
				if(null != newValue)
					taskTree.getSelectionModel().clearSelection();
				if(!ctrPressed){//与setOnMouseClicked形成互补
					//2.更新右边  会话 相关的按钮状态及表格数据,还包括任务信息按钮
					updateRightSide();
				}
			}
		});
		anatomyTaskTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				if(ctrPressed){
					//2.更新右边  会话 相关的按钮状态及表格数据
					updateRightSide();
				}
			}
		});
		
		taskCreaterCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyTask, String> call(
	    			 TableColumn<AnatomyTask, String> param) {
	    		 TableCell<AnatomyTask, String> cell =
	    				 new TableCell<AnatomyTask, String>() {
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
		taskCreateDateCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
			
			@Override
			public TableCell<AnatomyTask, String> call(
					TableColumn<AnatomyTask, String> param) {
				TableCell<AnatomyTask, String> cell =
						new TableCell<AnatomyTask, String>() {
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
		anatomyNumCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
			
			@Override
			public TableCell<AnatomyTask, String> call(
					TableColumn<AnatomyTask, String> param) {
				TableCell<AnatomyTask, String> cell =
						new TableCell<AnatomyTask, String>() {
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
	}
	
	/**
	 * 初始化sessionTable
	 */
	private void initSessionTable() {
		//  
		sessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sessionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		sessionTable.setItems(data_sessionTable);
		studyNoCol2.setCellValueFactory(new PropertyValueFactory<PathSession,String>("studyNo"));
		sessionTypeCol.setCellValueFactory(new PropertyValueFactory<PathSession,String>("sessionType"));
		sessionCreatorCol.setCellValueFactory(new PropertyValueFactory<PathSession,String>("sessionCreator"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<PathSession,String>("createTime"));
		finishSignerCol.setCellValueFactory(new PropertyValueFactory<PathSession,String>("finishSigner"));
		finishSignDateCol.setCellValueFactory(new PropertyValueFactory<PathSession,String>("finishSignDate"));
		
		sessionTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<PathSession>(){

			@Override
			public void changed(ObservableValue<? extends PathSession> arg0, PathSession arg1,
					PathSession newValue) {
				if(null != newValue){
					if(!ctrPressed){//与setOnMouseClicked形成互补
						//更新打开会话 按钮状态
						updateOpenSessionButtonState();
					}
				}else{
					openSessionButton.setDisable(true);
					openSessionMenuItem.setDisable(true);
				}
			}

		});
		sessionTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				if(ctrPressed){
					//更新打开会话 按钮状态
					updateOpenSessionButtonState();
				}
				
				int count = arg0.getClickCount();
				if(count==2)//double click
				{
					//System.out.println("==============double click happen,"+sessionTable.getSelectionModel().getSelectedItems());
					onOpenSessionButton(null);
				}
			}
		});
		
		sessionCreatorCol.setCellFactory(new Callback<TableColumn<PathSession,String>,TableCell<PathSession,String>>(){
			
			@Override
			public TableCell<PathSession, String> call(
					TableColumn<PathSession, String> param) {
				TableCell<PathSession, String> cell =
						new TableCell<PathSession, String>() {
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
		createTimeCol.setCellFactory(new Callback<TableColumn<PathSession,String>,TableCell<PathSession,String>>(){
			
			@Override
			public TableCell<PathSession, String> call(
					TableColumn<PathSession, String> param) {
				TableCell<PathSession, String> cell =
						new TableCell<PathSession, String>() {
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
		finishSignerCol.setCellFactory(new Callback<TableColumn<PathSession,String>,TableCell<PathSession,String>>(){
			
			@Override
			public TableCell<PathSession, String> call(
					TableColumn<PathSession, String> param) {
				TableCell<PathSession, String> cell =
						new TableCell<PathSession, String>() {
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
		finishSignDateCol.setCellFactory(new Callback<TableColumn<PathSession,String>,TableCell<PathSession,String>>(){
			
			@Override
			public TableCell<PathSession, String> call(
					TableColumn<PathSession, String> param) {
				TableCell<PathSession, String> cell =
						new TableCell<PathSession, String>() {
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
		
	}
	
	/**
	 * 更新打开会话 按钮状态
	 */
	private void updateOpenSessionButtonState() {
		
		openSessionButton.setDisable(true);
		openSessionMenuItem.setDisable(true);
		
		String currentRealName = SaveUserInfo.getRealName();
		boolean creatorFlag = true;		//创建者一致
		boolean sessionTypeFlag = true;	//会话类型一致
		boolean taskIdFlag = true;		//任务id不重复，
		boolean isleader = false;//是否是病理负责人或病理专题负责人
		String userId = SaveUserInfo.getUser().getId();
		String realName = SaveUserInfo.getUser().getRealName();
	
		boolean ishave  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "病理负责人");//病理负责人
		if(ishave){
			isleader = true;
		}
		List<String>  slist = new ArrayList<String>();
		String studyNo = data_sessionTable.get(0).getStudyNo();
		slist.add(studyNo);
	
		List<Map<String,Object>> plist  = BaseService.getInstance().getTblPathSessionService().getAllPathSD(slist);//所有病理专题负责人
		boolean falg = false;
		if(null != plist && plist.size() >0  ){
			for(Map<String,Object> pathSD:plist){
				String path = (String) pathSD.get("pathSD");
				if(!realName.equals(path)){
					falg = true;
				}
			}
			if(!falg){
				isleader = true;
			}
		}
   		
		String firstSessionType = "";
		ObservableList<PathSession> itemList = sessionTable.getSelectionModel().getSelectedItems();
		
		if(null != itemList && itemList.size() > 0 ){
			if(itemList.size() == 1){
				if(!currentRealName.equals(itemList.get(0).getSessionCreator())){
					creatorFlag = false;
				}
			}else{
				firstSessionType = itemList.get(0).getSessionType();
				for(PathSession pathSesion:itemList){
					if(!currentRealName.equals(pathSesion.getSessionCreator())){
						creatorFlag = false;
						break;
					}
					if(!firstSessionType.equals(pathSesion.getSessionType())){
						sessionTypeFlag = false;
						break;
					}
				}
				
				for(int i = 0;i < itemList.size();i++){
					for(int j = i+1;j < itemList.size();j++){
						if(itemList.get(i).getTaskId().equals(itemList.get(j).getTaskId())){
							taskIdFlag = false;
							break;
						}
					}
					if(!taskIdFlag){
						break;
					}
				}
			}
		}else{
			return;
		}
		
		if((isleader || creatorFlag ) && sessionTypeFlag && taskIdFlag){
			openSessionButton.setDisable(false);
			openSessionMenuItem.setDisable(false);
		}
	}
	
	/**
	 * 初始化任务树
	 */
	private void initTaskTree() {
		rootNode.setValue("解剖任务");
		rootNode.setExpanded(true);
		taskTree.setRoot(rootNode);
		taskTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		taskTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				//  任务树      change事件
				//1.取消anatomyTaskTable 的选中项
				if(null != newValue)
					anatomyTaskTable.getSelectionModel().clearSelection();
				if(!ctrPressed){//与setOnMouseClicked形成互补
					//2.更新右边  会话 相关的按钮状态及表格数据
					updateRightSide();
					
				}
				updateMsgLabel();
			}
			
		});
		taskTree.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(ctrPressed){
					//2.更新右边  会话 相关的按钮状态及表格数据
					updateRightSide();
					
				}
				updateMsgLabel();
			}
			
		});
	}
	
	/**
	 *更新三个label信息 
	 */
	private void updateMsgLabel(){
		msg11Label.setText("");
		msg21Label.setText("");
		msg31Label.setText("");
		msg12Label.setText("");
		msg22Label.setText("");
		msg32Label.setText("");
		ObservableList<TreeItem<String>> itemList =taskTree.getSelectionModel().getSelectedItems();
		if(null != itemList && itemList.size() > 0){
			if(itemList.size() == 1){
				//仅选中一个节点
				TreeItem<String> item = itemList.get(0);
				if(item.isLeaf()){
					//叶子节点
					String leafValue = item.getValue();
					String parentValue = item.getParent().getValue();
					String taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
					Map<String,Object> map = BaseService.getInstance().getTblAnatomyTaskService().getMap(taskId);
					
					String studyNo = "";
					String sd = "";
					String reqDateStr = "";
					String animalType = "";
					Integer animalNum = 0;
					String taskCreater = "";
					String taskDateStr = "";
//					String anatomyRsn = "";
					//,reqBeginDateStr,reqEndDateStr
//					String planAnatomyDate = "";
					if(null != map){
						studyNo = (String) map.get("studyNo");
						sd = (String) map.get("sd");
						reqDateStr = (String) map.get("reqDateStr");
						animalType = (String) map.get("animalType");
						animalNum = (Integer) map.get("animalNum");
//						anatomyRsn = (String) map.get("anatomyRsn");
						taskCreater = (String) map.get("taskCreater");
						taskDateStr = (String) map.get("taskDateStr");
						String reqBeginDateStr = (String) map.get("reqBeginDateStr");
//						String reqEndDateStr = (String) map.get("reqEndDateStr");
//						if(null != reqBeginDateStr && reqBeginDateStr.equals(reqEndDateStr)){
//							planAnatomyDate = reqBeginDateStr;
//						}else if(null != reqBeginDateStr && null != reqEndDateStr){
//							planAnatomyDate = reqBeginDateStr+" ~ "+reqEndDateStr;
//						}
						msg11Label.setText(studyNo+" ("+sd+")");
						msg12Label.setText("申请日期："+reqDateStr);
						msg21Label.setText("解剖动物："+animalType+" （"+animalNum+"只）");
						msg22Label.setText("计划解剖："+reqBeginDateStr);
						msg31Label.setText("任务建立："+taskCreater);
						msg32Label.setText("建立日期："+taskDateStr);
					}
				}else{
					//非叶子节点
					String value = item.getValue();
					String [] strs = value.split(" ");
					String studyNo = "";
					String sd = "";
					if(null != strs && strs.length > 1){
						studyNo = strs[0];
						sd = strs[1];
					}
					msg11Label.setText(studyNo+" ("+sd+")");
				}
				
			}else{
				int num = 0;//叶子节点数
				for(TreeItem<String> item:itemList){
					if(item.isLeaf()){
						num++;
					}
				}
				if(num > 0){
					msg11Label.setText("已选择 "+num+" 项解剖任务");
				}
			}
		}
	}
	
	/**
	 * 更新 任务树  及 任务表格
	 */
	public void updateTaskTreeAndTaskTable(){
		//清空树    
		taskTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		studyNoAnatomyNumTaskIdMap.clear();
		studyNoAnatomyNumStudyNoMap.clear();
		//清空任务表格
		anatomyTaskTable.getSelectionModel().clearSelection();
		data_anatomyTaskTable.clear();
		
		String beginDateStr = beginPane.getTextField().getText();
		String endDateStr = endPane.getTextField().getText();
		if(null != beginDateStr && !"".equals(beginDateStr) && null != endDateStr && !"".equals(endDateStr) ){
//			//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）；
//			//以专题启动日期为日期区间,专题启动日期降序排列
//			List<Map<String,Object>> mapList = 
//					BaseService.getInstance().getTblAnatomyTaskService().getListByStudyStartDate(beginDateStr,endDateStr);
			
			//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）
			//以任务创建日期为区间,任务创建时间降序排列
			List<Map<String,Object>> taskMapList = 
					BaseService.getInstance().getTblAnatomyTaskService().getListByTaskCreateDate(beginDateStr,endDateStr);
			taskDataList=taskMapList;
			if(null != taskMapList){
				//存放studyNo,及对应树节点
				Map<String,TreeItem<String>> studyNoTreeItemMap = new HashMap<String,TreeItem<String>>();
				for(Map<String,Object> map:taskMapList){
					String studyNo = (String) map.get("studyNo");
					String studydirector = (String) map.get("studydirector");
					String taskCreater = (String) map.get("taskCreater");
					Integer anatomyNum = (Integer) map.get("anatomyNum");
					String taskCreateTime = (String) map.get("taskCreateTime");
					String taskId = (String) map.get("taskId");
					String planBeginDate = (String) map.get("planBeginDate");
					TreeItem<String> depNode = null;
					if(studyNoTreeItemMap.keySet().contains(studyNo)){
						depNode = studyNoTreeItemMap.get(studyNo);
					}else{
						depNode = new TreeItem<String>(studyNo+" "+studydirector);
						depNode.setExpanded(false);
						rootNode.getChildren().add(depNode);
						studyNoTreeItemMap.put(studyNo, depNode);
					}
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")");
					
					studyNoAnatomyNumTaskIdMap.put(studyNo+" "+studydirector+","+anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")",taskId);
					studyNoAnatomyNumTaskIdMap.put(studyNo+" "+studydirector,studyNo);
					depNode.getChildren().add(leafNode);
				}
			}
			
			
//			//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）
//			//以任务创建日期为区间,任务创建时间降序排列
//			List<Map<String,Object>> taskMapList = 
//					BaseService.getInstance().getTblAnatomyTaskService().getListByTaskCreateDate(beginDateStr,endDateStr);
			if(null != taskMapList && taskMapList.size()>0){
				for(Map<String,Object> map:taskMapList){
					String studyNo = (String) map.get("studyNo");
					String taskId = (String) map.get("taskId");
					Integer anatomyNum = (Integer) map.get("anatomyNum");
					String taskCreateTime = (String) map.get("taskCreateTime");
					String taskCreater = (String) map.get("taskCreater");
					String planBeginDate = (String) map.get("planBeginDate");
					data_anatomyTaskTable.add(new AnatomyTask(taskId,studyNo,anatomyNum,taskCreateTime,taskCreater,planBeginDate));
				}
			}
			
			searchText.clear();
			searchBtn.setText("搜索");
		}
		
	}
	/**
	 * 新建解剖任务后选择
	 */
	public void selectNewTask(TblAnatomyTask tblAnatomyTask){
//		for(int i=0;i<data_anatomyTaskTable.size();i++){
//			if(tblAnatomyTask.getTaskId().equals(data_anatomyTaskTable.get(i).getTaskId())){
//				anatomyTaskTable.getSelectionModel().select(i);
//			}
//		}
		int selectIndex=tabPane.getSelectionModel().getSelectedIndex();
		if(selectIndex==0){
			String studyNo=tblAnatomyTask.getStudyNo();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String taskTime =sdf.format(tblAnatomyTask.getTaskCreateTime());
			String value=tblAnatomyTask.getAnatomyNum()+":"+taskTime;
			for(int i=0;i<rootNode.getChildren().size();i++){
				 ObservableList<TreeItem<String>> node=rootNode.getChildren().get(i).getChildren();
				 for(int j = 0;j < node.size();j++){
//					 System.out.println(node.get(j).getValue());
//					 System.out.println(value);
//					 System.out.println(node.get(j).getValue().indexOf(value));
//					 System.out.println(rootNode.getChildren().get(i).getValue());
//					 System.out.println(studyNo);
//					 System.out.println(rootNode.getChildren().get(i).getValue().indexOf(studyNo));
					 if(node.get(j).getValue().indexOf(value)>-1 && rootNode.getChildren().get(i).getValue().indexOf(studyNo)>-1){
						 taskTree.getSelectionModel().select(node.get(j))	;
//						    taskTree.scrollTo(i);
						 return;
					 }
				 }
				
			}
		}else{
			String studyNo=tblAnatomyTask.getStudyNo();
			int num=tblAnatomyTask.getAnatomyNum();
//			anatomyTaskTable.getSelectionModel()
			for(int i=0;i<data_anatomyTaskTable.size();i++){
				if(data_anatomyTaskTable.get(i).getStudyNo().equals(studyNo) && data_anatomyTaskTable.get(i).getAnatomyNum().equals(num+"")){
					anatomyTaskTable.getSelectionModel().select(i);
					return;
				}
			}
		}
		
	}
	/**
	 * 更新右边  会话 相关的按钮状态及表格数据，还包括任务信息按钮(taskTree 或 taskTable onchange时调用)
	 */
	public void updateRightSide() {
		//1.创建会话\打开会话  按钮是否可用
		if(currentTab == 0){
			ObservableList<TreeItem<String>> itemList =taskTree.getSelectionModel().getSelectedItems();
			if(null != itemList && itemList.size()>0){
				boolean allLeaf = true;//全部是叶子节点，是否
				for(TreeItem<String> item:itemList){
					if(!item.isLeaf()){
						allLeaf = false;
						break;
					}
				}
				//全是叶子节点，创建会话可用，否则不可用
				createSessionButton.setDisable(!allLeaf);
				createSessionMenuItem.setDisable(!allLeaf);
				if(allLeaf){
					//有任务被签字确认了   创建会话   不可用
					updateState_createSessionButton(itemList);
				}
				updateState_createSessionButton(itemList);
				
				
				//仅一个节点，且是叶子节点
				taskInfoButton.setDisable(!(allLeaf && itemList.size()== 1));
				
				//仅一个节点
				if(itemList.size()== 1){
					String userId = SaveUserInfo.getUserName();
					boolean isHasTissueSlice = BaseService.getInstance().getUserService().isHasPrivilege(userId , "毒性病理_组织学取材编号");
					tissueSliceButton.setDisable(!isHasTissueSlice);
					tissueSliceMenuItem.setDisable(!isHasTissueSlice);
					//组织取材（批次）菜单项
					tissueSliceBatchMenuItem.setDisable(!isHasTissueSlice);
//					String leafValue = itemList.get(0).getValue();
//					String parentValue =  itemList.get(0).getParent().getValue();
//					String taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
//					//1、病理负责人，可以     2、病理专题负责人，对应专题可以    ，3、同行评议人     ，对应任务且该任务镜检已提交 可以
//					boolean isHistopath = BaseService.getInstance().getTblHistopathCheckService().isHasHistopathPriviege(userId,taskId);
//					histopathCheckButton.setDisable(!isHistopath);
//					histopathCheckMenuItem.setDisable(!isHistopath);
					setDisable_histopathCheckButton();
				}else{
					tissueSliceButton.setDisable(true);
					histopathCheckButton.setDisable(true);
					tissueSliceMenuItem.setDisable(true);
					histopathCheckMenuItem.setDisable(true);
					//组织取材（批次）菜单项
					tissueSliceBatchMenuItem.setDisable(true);
				}
			}else{
				createSessionButton.setDisable(true);
				createSessionMenuItem.setDisable(true);
				taskInfoButton.setDisable(true);
				tissueSliceButton.setDisable(true);
				histopathCheckButton.setDisable(true);
				tissueSliceMenuItem.setDisable(true);
				histopathCheckMenuItem.setDisable(true);
				//组织取材（批次）菜单项
				tissueSliceBatchMenuItem.setDisable(true);
			}
		}else{
			ObservableList<AnatomyTask> taskList = anatomyTaskTable.getSelectionModel().getSelectedItems();
			if(null != taskList && taskList.size()>0){
				createSessionButton.setDisable(false);
				createSessionMenuItem.setDisable(false);
				//有  签过字的不可 用
				updateState_createSessionButton2(taskList);
				
				taskInfoButton.setDisable(taskList.size() != 1);
//				tissueSliceButton.setDisable(taskList.size() != 1);
//				histopathCheckButton.setDisable(taskList.size() != 1);
				if(taskList.size() == 1){
					String userId = SaveUserInfo.getUserName();
					boolean isHasTissueSlice = BaseService.getInstance().getUserService().isHasPrivilege(userId , "毒性病理_组织学取材编号");
					tissueSliceButton.setDisable(!isHasTissueSlice);
					tissueSliceMenuItem.setDisable(!isHasTissueSlice);
					//组织取材（批次）菜单项
					tissueSliceBatchMenuItem.setDisable(!isHasTissueSlice);
//					String taskId = taskList.get(0).getTaskId();
//					//1、病理负责人，可以     2、病理专题负责人，对应专题可以    ，3、同行评议人     ，对应任务且该任务镜检已提交 可以
//					boolean isHistopath = BaseService.getInstance().getTblHistopathCheckService().isHasHistopathPriviege(userId,taskId);
//					histopathCheckButton.setDisable(!isHistopath);
//					histopathCheckMenuItem.setDisable(!isHistopath);
					setDisable_histopathCheckButton();
				}else{
					tissueSliceButton.setDisable(true);
					histopathCheckButton.setDisable(true);
					tissueSliceMenuItem.setDisable(true);
					histopathCheckMenuItem.setDisable(true);
					//组织取材（批次）菜单项
					tissueSliceBatchMenuItem.setDisable(true);
				}
			}else{
				createSessionButton.setDisable(true);
				createSessionMenuItem.setDisable(true);
				taskInfoButton.setDisable(true);
				tissueSliceButton.setDisable(true);
				histopathCheckButton.setDisable(true);
				tissueSliceMenuItem.setDisable(true);
				histopathCheckMenuItem.setDisable(true);
				//组织取材（批次）菜单项
				tissueSliceBatchMenuItem.setDisable(true);
			}
		}
		
		String userId = SaveUserInfo.getUser().getId();
		boolean weight  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_称重");
		boolean check  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_剖检");
		boolean fix  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定");
		boolean weightAndfix  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定后称重");
		if( !(weight || check || fix || weightAndfix) ){
			//全是叶子节点，创建会话可用，否则不可用
			createSessionButton.setDisable(true);
			createSessionMenuItem.setDisable(true);
		}
		//打开会话按钮不可用
		openSessionButton.setDisable(true);
		openSessionMenuItem.setDisable(true);
		
		//2.更新会话表格数据
		updateSessionTable();
		
		
	}
	
	/**
	 * 设置组织学检查按钮状态（此时树仅选中一个）
	 */
	private void setDisable_histopathCheckButton(){
		String studyNo = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item){
				if(item.isLeaf()){
					String parentValue = item.getParent().getValue();
					studyNo = studyNoAnatomyNumTaskIdMap.get(parentValue);
				}else{
					studyNo = studyNoAnatomyNumTaskIdMap.get(item.getValue());
				}
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				studyNo = anatomyTask.getStudyNo();
			}
		}
		if(null != studyNo && !"".equals(studyNo)){
			String userId = SaveUserInfo.getUserName();
			//1、病理负责人，可以     2、病理专题负责人，对应专题可以    ，3、同行评议人     ，对应任务且该任务镜检已提交 可以
			boolean isHistopath = BaseService.getInstance().getTblHistopathCheckService().isHasHistopathPriviege(userId,studyNo);
			histopathCheckButton.setDisable(!isHistopath);
			histopathCheckMenuItem.setDisable(!isHistopath);
		}
	}
	
	/**更新  创建会话  按钮状态
	 * @param taskList   全叶子节点
	 */
	private void updateState_createSessionButton2(ObservableList<AnatomyTask> taskList) {
		//  Auto-generated method stub
		if(null != taskList && taskList.size() > 0){
			for(AnatomyTask anatomyTask:taskList){
				String taskId = anatomyTask.getTaskId();
				TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
				if(null != tblAnatomyTask){
					String anatomyCheckFinishSign = tblAnatomyTask.getAnatomyCheckFinishSign();
					String visceraFixedWeightFinishSign = tblAnatomyTask.getVisceraFixedWeightFinishSign();
					boolean hasVisceraFixedWeigh = BaseService.getInstance().getTblAnatomyTaskService().hasFixedWeigh(taskId);
					if(null != anatomyCheckFinishSign && !"".equals(anatomyCheckFinishSign) && 
							null != visceraFixedWeightFinishSign && !"".equals(visceraFixedWeightFinishSign) ){
						createSessionButton.setDisable(true);
						createSessionMenuItem.setDisable(true);
						break;
					}else if(null != anatomyCheckFinishSign && !"".equals(anatomyCheckFinishSign) &&
							!hasVisceraFixedWeigh){
						createSessionButton.setDisable(true);
						createSessionMenuItem.setDisable(true);
						break;
					}
				}else{
					createSessionButton.setDisable(true);
					createSessionMenuItem.setDisable(true);
					break;
				}
			}
		}else{
			createSessionButton.setDisable(true);
			createSessionMenuItem.setDisable(true);
		}
	}
	/**更新  创建会话  按钮状态
	 * @param itemList   全叶子节点
	 */
	private void updateState_createSessionButton(ObservableList<TreeItem<String>> itemList) {
		// 
		if(null != itemList && itemList.size()>0){
			for(TreeItem<String> item:itemList){
				String taskId = "";
				TreeItem<String> nodeItem = item.getParent();
				taskId = studyNoAnatomyNumTaskIdMap.get(nodeItem.getValue()+","+item.getValue());
				TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
				if(null != tblAnatomyTask){
					String anatomyCheckFinishSign = tblAnatomyTask.getAnatomyCheckFinishSign();
					String visceraFixedWeightFinishSign = tblAnatomyTask.getVisceraFixedWeightFinishSign();
					boolean hasVisceraFixedWeigh = BaseService.getInstance().getTblAnatomyTaskService().hasFixedWeigh(taskId);
					if(null != anatomyCheckFinishSign && !"".equals(anatomyCheckFinishSign) && 
							null != visceraFixedWeightFinishSign && !"".equals(visceraFixedWeightFinishSign) ){
						createSessionButton.setDisable(true);
						createSessionMenuItem.setDisable(true);
						break;
					}else if(null != anatomyCheckFinishSign && !"".equals(anatomyCheckFinishSign) &&
							!hasVisceraFixedWeigh){
						createSessionButton.setDisable(true);
						createSessionMenuItem.setDisable(true);
						break;
					}
				}else{
					createSessionButton.setDisable(true);
					createSessionMenuItem.setDisable(true);
					break;
				}
			}
		}
	}
	/**
	 * 更新会话表格数据
	 */
	public void updateSessionTable() {
		//  更新会话表格数据
		
		//1.清空原数据
		data_sessionTable.clear();
		sessionTable.getSelectionModel().clearSelection();
		//2.获得任务idList
		//任务id列表
		List<String> taskIdList = new ArrayList<String>();
		if(currentTab == 0){
			ObservableList<TreeItem<String>> itemList =taskTree.getSelectionModel().getSelectedItems();
			if(null != itemList && itemList.size()>0){
				for(TreeItem<String> item:itemList){
					if(item.isLeaf()){
						String leafValue = item.getValue();
						String parentValue = item.getParent().getValue();
						String taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
						if(null != taskId){
							taskIdList.add(taskId);
						}
					}
				}
			}else{
				return ;
			}
		}else{
			ObservableList<AnatomyTask> taskList = anatomyTaskTable.getSelectionModel().getSelectedItems();
			if(null != taskList && taskList.size()>0){
				for(AnatomyTask obj:taskList){
					String taskId = obj.getTaskId();
					if(null != taskId){
						taskIdList.add(taskId);
					}
				}
			}else{
				return ;
			}
		}
		//3.获得 会话类型
		int sessionType = 0;
		int index = sessionTypeComboBox.getSelectionModel().getSelectedIndex();
		if(index > 2){
			if(index == 3){
				sessionType = 4;
			}else if(index == 4){
				sessionType = 8;
			}
		}else{
			sessionType = index;
		}
		//查询List<Map> ,map 中有 sessionId,  taskId,  studyNo,
		// sessionType,  sessionCreator, createTime,  finishSigner,
		// finishSignDate;已sessionId 降序排列
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblPathSessionService().getMapList(taskIdList,sessionType);
		//4.查询数据库数据，并填充
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				String sessionId = (String) map.get("sessionId");
				String taskId = (String) map.get("taskId");
				String studyNo = (String) map.get("studyNo");
				String sessionCreator = (String) map.get("sessionCreator");
				String createTime = (String) map.get("createTime");
				String finishSigner = (String) map.get("finishSigner");
				String finishSignDate = (String) map.get("finishSignDate");
				Integer sessionType2 = (Integer) map.get("sessionType");
				data_sessionTable.add(new PathSession(sessionId,taskId,studyNo,sessionType2,
						sessionCreator,createTime,finishSigner,finishSignDate));
			}
		}
		
	}
	
//	/**
//	 * 获取当前页面定义的  stage
//	 * @return
//	 */
//	public Stage getStage(){
//		if(null == stage){
//			stage = new Stage();
//			stage.initOwner(Main.mainWidow);
//			stage.initModality(Modality.APPLICATION_MODAL);
//		}
//		return stage;
//	}
	
	/**
	 * 初始化数据
	 */
	public void initPane(){
		//当前用户
		userNameLable.setText(SaveUserInfo.getRealName() != null ? SaveUserInfo.getRealName():"");
		
		//TODO更新 任务树 及 任务表格
		updateTaskTreeAndTaskTable();
		
	}
	
	/**
	 * 系统_用户管理   菜单项 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onMI_userManageAction(ActionEvent event){
		try {
//			UserManagePage.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("UserManagePage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				UserManagePage.getInstance().start(stage);
				Main.stageMap.put("UserManagePage",stage);
			}else{
				stage.show();
			}
			UserManagePage.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 系统_天平管理   菜单项 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onTL_ChipReaderControllerAction(ActionEvent event){
		try {
//			ChipReaderController.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("ChipReaderController");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				ChipReaderController.getInstance().start(stage);
				Main.stageMap.put("ChipReaderController",stage);
			}else{
				stage.show();
			}
			ChipReaderController.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 系统_芯片阅读器 菜单项 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onTL_BalanceRrgeAction(ActionEvent event){
		try {
//			BalanceRge.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("BalanceRge");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				BalanceRge.getInstance().start(stage);
				Main.stageMap.put("BalanceRge",stage);
			}else{
				stage.show();
			}
			BalanceRge.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 校准参数设置的按键
	 * @param event
	 */
	@FXML
	private void parameterSetting(ActionEvent event){
		try {
//			ParameterSetting.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("ParameterSetting");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				ParameterSetting.getInstance().start(stage);
				Main.stageMap.put("ParameterSetting",stage);
			}else{
				stage.show();
			}
			ParameterSetting.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 系统_修改密码   菜单项 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onMI_changePasswordAction(ActionEvent event){
		AlterPassword alterFrame= new AlterPassword(SaveUserInfo.getUser(),"");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			alterFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改成功进入主界面
		if(AlterPassword.getType().equals("OK")){
			//保存用户信息
			SaveUserInfo.setUser(AlterPassword.getUser());
			
			//List<User> list=	BaseService.getInstance().getUserService().findByPrivilegeName("毒性病理_登录");
    		//存放有  '毒性病理_登录'   权限 的用户列表
    		//SaveUserInfo.setPathUserList(list);
		}
	}
	
	/**
	 * 系统_切换用户   菜单项 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onMI_changeUserAction(ActionEvent event){
		if(Messager.showSimpleConfirm("提示","您确定要退出,更换其他账号登录吗？")){
			Main.getInstance().enterLoginScene();
		}
	}
	
	/**
	 * 系统_退出    菜单项 onAction 相应函数
	 */
	@FXML
	private void onMI_exitAction(ActionEvent event){
		if(Messager.showSimpleConfirm("退出系统","确定要退出系统吗？")){
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("退出系统");
			tblLog.setOperatOject(SystemMessage.getSystemFullName());
			tblLog.setOperator(SaveUserInfo.getRealName());
			tblLog.setOperatContent("");
			tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			try {
				BaseService.getInstance().getTblLogService().save(tblLog);
			} catch (Exception e) {
				System.exit(1);
			}
			System.exit(1);
		}
	}
	
	/**新建任务 按钮 onAction 相应函数(试验_新建任务  菜单项 )
	 * @param event
	 */
	@FXML
	private void onMI_about(ActionEvent event){
		try {
			
			Stage stage = Main.stageMap.get("About");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initStyle(StageStyle.UTILITY);
				About about = new About();
				about.start(stage);
				Main.stageMap.put("About",stage);
			}else{
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**新建任务 按钮 onAction 相应函数(试验_新建任务  菜单项 )
	 * @param event
	 */
	@FXML
	private void onNewTaskButton(ActionEvent event){
		try {
			
//			AnatomyReqPage.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("AnatomyReqPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AnatomyReqPage.getInstance().start(stage);
				Main.stageMap.put("AnatomyReqPage",stage);
			}else{
				stage.show();
			}
			AnatomyReqPage.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**原始数据-解剖所见（任务详细信息）按钮事件
	 * @param event
	 */
	@FXML
	private void onTaskDetailInfoButton(ActionEvent event){
        try {
			
//			AnatomyReqPage.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("TaskDetailInfoPage");
			//获取该用户拥有的权限的专题
			String beginDateStr = beginPane.getTextField().getText();
			String endDateStr = endPane.getTextField().getText();
			if(null != beginDateStr && !"".equals(beginDateStr) && null != endDateStr && !"".equals(endDateStr) ){
			   taskDataList = BaseService.getInstance().getTblAnatomyTaskService().getTaskDetailListByStudyCreateDate(SaveUserInfo.getUser(),beginDateStr,endDateStr);
			}
			
			if(taskDataList==null||taskDataList.size()==0)
			{
				showMessage("本用户没有相关的专题可以查看！");
			}else
			{
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					TaskDetailInfoPage.getInstance().start(stage);
					Main.stageMap.put("TaskDetailInfoPage",stage);
				}else{
					stage.show();
				}
				TreeItem<String> item=taskTree.getSelectionModel().getSelectedItem();//任务树下选择的任务用于在解剖任务详细信心下自动选择任务
				TreeItem<String> item2=null;
				if(null!=item){
					item2=item.getParent();
				}
				AnatomyTask anatomyTask=anatomyTaskTable.getSelectionModel().getSelectedItem();//任务表下的任务用于在解剖任务详细信心下自动选择任务
				String clickText = ((MenuItem)event.getSource()).getText();
				
				if(clickText.equals("解剖所见"))
					TaskDetailInfoPage.getInstance().getTabPane().getSelectionModel().select(1);
				else if(clickText.equals("脏器称重"))
				    TaskDetailInfoPage.getInstance().getTabPane().getSelectionModel().select(2);
				else if(clickText.equals("脏器固定"))
			    TaskDetailInfoPage.getInstance().getTabPane().getSelectionModel().select(3);
			
				TaskDetailInfoPage.getInstance().loadData(taskDataList,item,item2,anatomyTask,stage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**原始数据-数据修改    按钮事件
	 * @param event
	 */
	@FXML
	private void onDataEditButton(ActionEvent event){
		String taskId = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item && item.isLeaf()){
				String leafValue = item.getValue();
				String parentValue = item.getParent().getValue();
				taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				taskId = anatomyTask.getTaskId();
			}
		}
		TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		if(null == tblAnatomyTask || null == tblAnatomyTask.getAnatomyCheckFinishSign() 
				|| "".equals(tblAnatomyTask.getAnatomyCheckFinishSign())){
			showErrorMessage("当前解剖任务未解剖签字！");
			return;
		}
		if(null != taskId && !"".equals(taskId)){
			try {
				Stage stage = Main.stageMap.get("DataEditPage");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					DataEditPage.getInstance().start(stage);
					Main.stageMap.put("DataEditPage",stage);
				}else{
					stage.show();
				}
				DataEditPage.getInstance().loadData(taskId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			showErrorMessage("请先选择解剖任务！");
			return ;
		}
	}
	
	/**补录数据
	 * @param event
	 */
	@FXML
	private void onAdditionalDataButton(ActionEvent event){
		String studyNo = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item){
				if(item.isLeaf()){
					String parentValue = item.getParent().getValue();
					studyNo = studyNoAnatomyNumTaskIdMap.get(parentValue);
				}else{
					studyNo = studyNoAnatomyNumTaskIdMap.get(item.getValue());
				}
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				studyNo = anatomyTask.getStudyNo();
			}
		}
		studyNo = selectStudyNo(studyNo);
		if(null == studyNo || "".equals(studyNo)){
			return;
		}
		try {
			Stage stage = Main.stageMap.get("AdditionalPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AdditionalPage.getInstance().start(stage);
				Main.stageMap.put("AdditionalPage",stage);
			}else{
				stage.show();
			}
			AdditionalPage.getInstance().loadData(studyNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**任务信息  按钮 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onTaskInfoButton(ActionEvent event){
		String taskId = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(item.isLeaf()){
				String leafValue = item.getValue();
				String parentValue = item.getParent().getValue();
				taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				taskId = anatomyTask.getTaskId();
			}
		}
		if(null != taskId && !"".equals(taskId)){
			try {
				Stage stage = Main.stageMap.get("TaskInfoPage");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					TaskInfoPage.getInstance().start(stage);
					Main.stageMap.put("TaskInfoPage",stage);
				}else{
					stage.show();
				}
				TaskInfoPage.getInstance().loadData(taskId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			showErrorMessage("请先选择解剖任务！");
			return ;
		}
	}
	
	
	/**组织取材(批次)菜单项 
	 * @param event
	 */
	@FXML
	private void onMI_tissueSliceBatch(ActionEvent event){
		String studyNo = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item){
				if(item.isLeaf()){
					String parentValue = item.getParent().getValue();
					studyNo = studyNoAnatomyNumTaskIdMap.get(parentValue);
				}else{
					studyNo = studyNoAnatomyNumTaskIdMap.get(item.getValue());
				}
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				studyNo = anatomyTask.getStudyNo();
			}
		}
		if(null != studyNo && !"".equals(studyNo)){
			
			studyNo = selectStudyNo(studyNo);
			if(null == studyNo || "".equals(studyNo)){
				return;
			}
			
			Stage stage = Main.stageMap.get("TissueSliceBatch");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					TissueSliceBatch.getInstance().start(stage);
					stage.setTitle("组织取材（"+studyNo+"）");
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("TissueSliceBatch",stage);
			}else{
				stage.setTitle("组织取材（"+studyNo+"）");
				stage.show();
			}
			TissueSliceBatch.getInstance().loadData(studyNo);
		}else{
			showErrorMessage("请先选择解剖任务或专题！");
			return ;
		}
	}
	/**组织取材编号  按钮 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onTissueSliceButton(ActionEvent event){
		// 
		String studyNo = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item){
				if(item.isLeaf()){
					String parentValue = item.getParent().getValue();
					studyNo = studyNoAnatomyNumTaskIdMap.get(parentValue);
				}else{
					studyNo = studyNoAnatomyNumTaskIdMap.get(item.getValue());
				}
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				studyNo = anatomyTask.getStudyNo();
			}
		}
		if(null != studyNo && !"".equals(studyNo)){
			
			studyNo = selectStudyNo(studyNo);
			if(null == studyNo || "".equals(studyNo)){
				return;
			}
			
			try {
				Stage stage = Main.stageMap.get("TissueSlicePage");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					TissueSlicePage.getInstance().start(stage);
					Main.stageMap.put("TissueSlicePage",stage);
					stage.setTitle("组织取材编号（"+studyNo+"）");
				}else{
					stage.setTitle("组织取材编号（"+studyNo+"）");
					stage.show();
				}
				TissueSlicePage.getInstance().loadData(studyNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			showErrorMessage("请先选择解剖任务或专题！");
			return ;
		}
	}
	
	/**弹出选择专题编号窗口，返回选择的专题编号
	 * @param studyNo
	 * @return
	 */
	private String selectStudyNo(String studyNo){
		String selectStudyNo = "";
		
		List<String> studyNoList = new ArrayList<String>();
		if(null != data_anatomyTaskTable && data_anatomyTaskTable.size() > 0){
			for(AnatomyTask obj :data_anatomyTaskTable){
				if(!studyNoList.contains(obj.getStudyNo())){
					studyNoList.add(obj.getStudyNo());
				}
			}
		}
		
		Stage stage = Main.stageMap.get("SelectStudyNoPage");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.WINDOW_MODAL);
			try {
				SelectStudyNoPage.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SelectStudyNoPage",stage);
		}
		
		SelectStudyNoPage.getInstance().loadData(studyNoList,studyNo);
		stage.showAndWait();
		
		selectStudyNo = SelectStudyNoPage.getInstance().getStudyNo();
		return selectStudyNo;
	}
	
	/**组织学检查  按钮 onAction 相应函数
	 * @param event
	 */
	@FXML
	private void onHistopathCheckButton(ActionEvent event){
		String studyNo = "";
		if(currentTab == 0){
			TreeItem<String> item =taskTree.getSelectionModel().getSelectedItem();
			if(null != item){
				if(item.isLeaf()){
					String parentValue = item.getParent().getValue();
					studyNo = studyNoAnatomyNumTaskIdMap.get(parentValue);
				}else{
					studyNo = studyNoAnatomyNumTaskIdMap.get(item.getValue());
				}
			}
		}else{
			AnatomyTask anatomyTask = anatomyTaskTable.getSelectionModel().getSelectedItem();
			if(null != anatomyTask ){
				studyNo = anatomyTask.getStudyNo();
			}
		}
		if(null != studyNo && !"".equals(studyNo)){
			studyNo = selectStudyNo(studyNo);
			if(null == studyNo || "".equals(studyNo)){
				return;
			}
			
			//是否有权限进入该专题的组织学检查
			String userId = SaveUserInfo.getUserName();
			//1、病理负责人，可以     2、病理专题负责人，对应专题可以    ，3、同行评议人     ，对应任务且该任务镜检已提交 可以
			boolean isHistopath = BaseService.getInstance().getTblHistopathCheckService().isHasHistopathPriviege(userId,studyNo);
			if(!isHistopath){
				showErrorMessage("组织学检查（"+studyNo+"）无法打开！");
				return ;
			}
			//是复核者，还是病理专题负责人
			String userName = SaveUserInfo.getUserName();
			int histopathReviewFlag = 0;
			TblPathStudyIndex pathStudyPlan = BaseService.getInstance().getTblPathStudyIndexService().getByStudyNo(studyNo);
			if(null != pathStudyPlan){
				histopathReviewFlag = pathStudyPlan.getHistopathReviewFlag();
				int userFlag = BaseService.getInstance().getTblHistopathCheckService().getUserFlag(studyNo,userName);
				
				if(userFlag == 2 && histopathReviewFlag > 0){
					//镜检复核窗口
					Stage stage = Main.stageMap.get("HistopathReview");
					if(null == stage){
						stage =new Stage();
						stage.initOwner(Main.mainWidow);
						stage.initModality(Modality.APPLICATION_MODAL);
						try {
							HistopathReview.getInstance().start(stage);
						} catch (Exception e) {
							e.printStackTrace();
						}
						Main.stageMap.put("HistopathReview",stage);
					}else{
						stage.show();
					}
					HistopathReview.getInstance().loadData(studyNo);
				}else{
					//镜检窗口
					try {
						Stage stage = Main.stageMap.get("HistopathCheckPage");
						if(null == stage){
							stage =new Stage();
							stage.initOwner(Main.mainWidow);
							stage.initModality(Modality.WINDOW_MODAL);
							HistopathCheckPage.getInstance().start(stage);
							Main.stageMap.put("HistopathCheckPage",stage);
						}else{
							stage.show();
						}
						HistopathCheckPage.getInstance().loadData(studyNo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				//镜检窗口
				try {
					Stage stage = Main.stageMap.get("HistopathCheckPage");
					if(null == stage){
						stage =new Stage();
						stage.initOwner(Main.mainWidow);
						stage.initModality(Modality.WINDOW_MODAL);
						HistopathCheckPage.getInstance().start(stage);
						Main.stageMap.put("HistopathCheckPage",stage);
					}else{
						stage.show();
					}
					HistopathCheckPage.getInstance().loadData(studyNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			showErrorMessage("请先选择解剖任务！");
			return ;
		}
	}
	
//	/**查询 按钮 onAction 相应函数
//	 * @param event
//	 */
//	@FXML
//	private void onSearchButton(ActionEvent event){
//		//更新 任务树 及 任务表格 
//		updateTaskTreeAndTaskTable();
//	}
	
	/**新建会话 按钮 onAction 响应函数
	 * @param event
	 */
	@FXML
	private void onCreateSessionButton(ActionEvent event){
		//任务id列表
		List<String> taskIdList = new ArrayList<String>();
		if(currentTab == 0){
			ObservableList<TreeItem<String>> itemList =taskTree.getSelectionModel().getSelectedItems();
			if(null != itemList && itemList.size()>0){
				for(TreeItem<String> item:itemList){
					if(item.isLeaf()){
						String leafValue = item.getValue();
						String parentValue = item.getParent().getValue();
						String taskId = studyNoAnatomyNumTaskIdMap.get(parentValue+","+leafValue);
						if(null != taskId){
							taskIdList.add(taskId);
						}
					}
				}
			}else{
				showErrorMessage("请先选择解剖任务！");
				return ;
			}
		}else{
			ObservableList<AnatomyTask> taskList = anatomyTaskTable.getSelectionModel().getSelectedItems();
			if(null != taskList && taskList.size()>0){
				for(AnatomyTask obj:taskList){
					String taskId = obj.getTaskId();
					if(null != taskId){
						taskIdList.add(taskId);
					}
				}
			}else{
				showErrorMessage("请先选择解剖任务！");
				return ;
			}
		}
		try {
//			PathSessionPage.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("PathSessionPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				PathSessionPage.getInstance().start(stage);
				Main.stageMap.put("PathSessionPage",stage);
			}else{
				stage.show();
			}
			PathSessionPage.getInstance().loadData(taskIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**搜索 事件（搜索按钮、清除按钮）
	 * @param event
	 */
	@FXML
	private void onSearchBtn(ActionEvent event){
		//TODO
		if("搜索".equals(searchBtn.getText())){
			String studyNo = searchText.getText().trim();
			if(null != studyNo && !"".equals(studyNo)){
				updateTaskTreeAndTaskTableByStudyNo(studyNo);
			}
		}else{
			//更新 任务树 及 任务表格
			updateTaskTreeAndTaskTable();
			searchBtn.setText("搜索");
			searchText.clear();
		}
	}
	/**搜索 事件(搜索框)
	 * @param event
	 */
	@FXML
	private void onSearchText(ActionEvent event){
		String studyNo = searchText.getText().trim();
		if(null != studyNo && !"".equals(studyNo)){
			updateTaskTreeAndTaskTableByStudyNo(studyNo);
		}
	}
	
	/**
	 * 更新 任务树  及 任务表格根据专题编号
	 */
	public void updateTaskTreeAndTaskTableByStudyNo(String studyNo){
		//清空树    
		taskTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		studyNoAnatomyNumTaskIdMap.clear();
		studyNoAnatomyNumStudyNoMap.clear();
		//清空任务表格
		anatomyTaskTable.getSelectionModel().clearSelection();
		data_anatomyTaskTable.clear();
		
		String beginDateStr = beginPane.getTextField().getText();
		String endDateStr = endPane.getTextField().getText();
		if(null != beginDateStr && !"".equals(beginDateStr) && null != endDateStr && !"".equals(endDateStr) ){
			
			//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）
			//以任务创建日期为区间,任务创建时间降序排列
//			List<Map<String,Object>> taskMapList = 
//					BaseService.getInstance().getTblAnatomyTaskService().getListByTaskCreateDate(beginDateStr,endDateStr);
			List<Map<String,Object>> taskMapList = 
					BaseService.getInstance().getTblAnatomyTaskService().getListByStudyNo(studyNo);
			taskDataList=taskMapList;
			if(null != taskMapList){
				//存放studyNo,及对应树节点
				Map<String,TreeItem<String>> studyNoTreeItemMap = new HashMap<String,TreeItem<String>>();
				for(Map<String,Object> map:taskMapList){
					String studydirector = (String) map.get("studydirector");
					String taskCreater = (String) map.get("taskCreater");
					Integer anatomyNum = (Integer) map.get("anatomyNum");
					String taskCreateTime = (String) map.get("taskCreateTime");
					String taskId = (String) map.get("taskId");
					String planBeginDate = (String) map.get("planBeginDate");
					TreeItem<String> depNode = null;
					if(studyNoTreeItemMap.keySet().contains(studyNo)){
						depNode = studyNoTreeItemMap.get(studyNo);
					}else{
						depNode = new TreeItem<String>(studyNo+" "+studydirector);
						depNode.setExpanded(true);
						rootNode.getChildren().add(depNode);
						studyNoTreeItemMap.put(studyNo, depNode);
					}
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")");
					
					studyNoAnatomyNumTaskIdMap.put(studyNo+" "+studydirector+","+anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")",taskId);
					studyNoAnatomyNumTaskIdMap.put(studyNo+" "+studydirector,studyNo);
					depNode.getChildren().add(leafNode);
				}
			}
			
			
//			//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）
//			//以任务创建日期为区间,任务创建时间降序排列
//			List<Map<String,Object>> taskMapList = 
//					BaseService.getInstance().getTblAnatomyTaskService().getListByTaskCreateDate(beginDateStr,endDateStr);
			if(null != taskMapList && taskMapList.size()>0){
				for(Map<String,Object> map:taskMapList){
					String taskId = (String) map.get("taskId");
					Integer anatomyNum = (Integer) map.get("anatomyNum");
					String taskCreateTime = (String) map.get("taskCreateTime");
					String taskCreater = (String) map.get("taskCreater");
					String planBeginDate = (String) map.get("planBeginDate");
					data_anatomyTaskTable.add(new AnatomyTask(taskId,studyNo,anatomyNum,taskCreateTime,taskCreater,planBeginDate));
				}
			}
			
		}
		
		searchBtn.setText("清除");
	}
	
	/**打开会话 按钮 onAction 响应函数
	 * @param event
	 */
	@FXML
	private void onOpenSessionButton(ActionEvent event){
		ObservableList<PathSession> itemList = sessionTable.getSelectionModel().getSelectedItems();
		if(null != itemList && itemList.size() > 0 ){
			List<String> sessionIdList = new ArrayList<String>();
			List<String> taskIdList = new ArrayList<String>();
			for(PathSession pathSession:itemList){
				sessionIdList.add(pathSession.getSessionId());
				if(!taskIdList.contains(pathSession.getTaskId())){
					taskIdList.add(pathSession.getTaskId());
				}
			}
			try {
//				PathSessionPage.getInstance().start(getStage());
				Stage stage = Main.stageMap.get("PathSessionPage");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					PathSessionPage.getInstance().start(stage);
					Main.stageMap.put("PathSessionPage",stage);
				}else{
					stage.show();
				}
				PathSessionPage.getInstance().loadData(sessionIdList,taskIdList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**键盘按下事件（主界面响应事件）
	 * @param event
	 */
	@FXML
	private void OnKeyPressed(KeyEvent event){
		if(event.getCode().getName().equals("Ctrl")){
			ctrPressed = true;
		}
	}
	/**键盘按下事件（主界面响应事件）
	 * @param event
	 */
	@FXML
	private void OnKeyReleased(KeyEvent event){
		if(event.getCode().getName().equals("Ctrl")){
			ctrPressed = false;
		}
	}
	/**试验-天平校准按钮
	 * @param event
	 */
	@FXML
	private void onMi_toBalCalibration(ActionEvent event){
//		try{
//			TblBalCalibrationIndexController.getInstance().start(getStage());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		try{
//			AddTblBalCalibration.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("AddTblBalCalibration");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AddTblBalCalibration.getInstance().start(stage);
				Main.stageMap.put("AddTblBalCalibration",stage);
			}else{
				stage.show();
			}
			AddTblBalCalibration.getInstance().loadData(null, null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 动物芯片查询
	 * @param event
	 */
	@FXML
	private void onAnimalChipReaderAction(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("AnimalChipSelectPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AnimalChipSelectPage.getInstance().start(stage);
				Main.stageMap.put("AnimalChipSelectPage",stage);
			}else{
				stage.show();
			}
			AnimalChipSelectPage.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**新建任务 按钮 onAction 相应函数(登记_天平登记信息 菜单项 )
	 * @param event
	 */
	@FXML
	private void onToBalConnectButton(ActionEvent event){
		try {
//			BalConnect.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("BalConnect");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				BalConnect.getInstance().start(stage);
				Main.stageMap.put("BalConnect",stage);
			}else{
				stage.show();
			}
			BalConnect.getInstance().loadData("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**原始数据-天平校准记录按钮
	 * @param event
	 */
	@FXML
	private void onMi_toBalCalibationIndex(ActionEvent event){
		try{
//			TblBalCalibrationIndexController.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("TblBalCalibrationIndexController");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				TblBalCalibrationIndexController.getInstance().start(stage);
				Main.stageMap.put("TblBalCalibrationIndexController",stage);
				TblBalCalibrationIndexController.getInstance().loadData();
			}else{
				stage.show();
				TblBalCalibrationIndexController.getInstance().loadData();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
	
	/**
	 * 解剖任务
	 * @author Administrator
	 *
	 */
	public static class AnatomyTask{
		
		private StringProperty taskId;             //任务id
		private StringProperty studyNo;        //专题编号
		private StringProperty anatomyNum;          //解剖序号
		private StringProperty taskCreateDate; //任务创建日期
		private StringProperty taskCreater;		//任务创建者
		private StringProperty planBeginDate;		//任务创建者
		
		
		private StringProperty animalType;		//动物种类
		private StringProperty animalNum;		//动物数量
		private StringProperty anatomyRsn;		//解剖原因
		private StringProperty finish;//签字确认
		
		public AnatomyTask() {
			super();
		}
		
		public AnatomyTask(String taskId, String studyNo, int anatomyNum,
				String taskCreateDate, String taskCreater,String planBeginDate) {
			this.taskId = new SimpleStringProperty(taskId);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.anatomyNum = new SimpleStringProperty(anatomyNum+"");
			this.taskCreateDate = new SimpleStringProperty(taskCreateDate);
			this.taskCreater = new SimpleStringProperty(taskCreater);
			this.planBeginDate = new SimpleStringProperty(planBeginDate);
		}
		public AnatomyTask(String taskId, String studyNo, int anatomyNum,
				String taskCreateDate, String taskCreater,String animalType,int animalNum,String anatomyRsn) {
			this.taskId = new SimpleStringProperty(taskId);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.anatomyNum = new SimpleStringProperty(anatomyNum+"");
			this.taskCreateDate = new SimpleStringProperty(taskCreateDate);
			this.taskCreater = new SimpleStringProperty(taskCreater);
			
			this.animalType = new SimpleStringProperty(animalType);
			this.animalNum = new SimpleStringProperty(animalNum+"");
			this.anatomyRsn = new SimpleStringProperty(anatomyRsn);
		}
		public AnatomyTask(String taskId, String studyNo, int anatomyNum,
				String taskCreateDate, String taskCreater,String animalType,int animalNum,String anatomyRsn,String finish) {
			this.taskId = new SimpleStringProperty(taskId);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.anatomyNum = new SimpleStringProperty(anatomyNum+"");
			this.taskCreateDate = new SimpleStringProperty(taskCreateDate);
			this.taskCreater = new SimpleStringProperty(taskCreater);
			
			this.animalType = new SimpleStringProperty(animalType);
			this.animalNum = new SimpleStringProperty(animalNum+"");
			this.anatomyRsn = new SimpleStringProperty(anatomyRsn);
			this.finish = new SimpleStringProperty(finish);
		}

		public String getTaskId() {
			return taskId.get();
		}
		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}
		
		public String getTaskCreateDate() {
			return taskCreateDate.get();
		}
		public void setTaskCreateDate(String taskCreateDate) {
			this.taskCreateDate = new SimpleStringProperty(taskCreateDate);
		}
		public String getTaskCreater() {
			return taskCreater.get();
		}
		public void setTaskCreater(String taskCreater) {
			this.taskCreater = new SimpleStringProperty(taskCreater);
		}

		public String getAnatomyNum() {
			return anatomyNum.get();
		}

		public void setAnatomyNum(String anatomyNum) {
			this.anatomyNum = new SimpleStringProperty(anatomyNum);
		}

		public String getAnimalType() {
			return animalType.get();
		}

		public void setAnimalType(String animalType) {
			this.animalType = new SimpleStringProperty(animalType);
		}

		public String getAnimalNum() {
			return animalNum.get();
		}

		public void setAnimalNum(String animalNum) {
			this.animalNum = new SimpleStringProperty(animalNum);
		}

		public String getAnatomyRsn() {
			return anatomyRsn.get();
		}

		public void setAnatomyRsn(String anatomyRsn) {
			this.anatomyRsn = new SimpleStringProperty(anatomyRsn);
		}

		public String getFinish() {
			return finish.get();
		}

		public void setFinish(String finish) {
			this.finish =  new SimpleStringProperty(finish);
		}

		public String getPlanBeginDate() {
			return planBeginDate.get();
		}

		public void setPlanBeginDate(String planBeginDate) {
			this.planBeginDate =  new SimpleStringProperty(planBeginDate);
		}
		
		
	}
	/**
	 * 病理检查会话
	 * @author Administrator
	 *
	 */
	public static class PathSession{
		
		private StringProperty sessionId;            	//任务id
		private StringProperty taskId;             		//任务id
		private StringProperty studyNo;        			//专题编号
		private StringProperty sessionType;         	//解剖序号
		private StringProperty sessionCreator;			//任务创建者
		private StringProperty createTime; 				//任务创建日期
		private StringProperty finishSigner;			//签字者
		private StringProperty finishSignDate;			//签字日期
		
		public PathSession() {
			super();
		}
		
		public PathSession(String sessionId, String taskId, String studyNo,
				int sessionType, String sessionCreator,
				String createTime, String finishSigner,
				String finishSignDate) {
			super();
			this.sessionId = new SimpleStringProperty(sessionId);
			this.taskId = new SimpleStringProperty(taskId);
			this.studyNo = new SimpleStringProperty(studyNo);
			
			String sessionTypeStr = "";
			switch (sessionType) {
				case 1:	sessionTypeStr ="动物解剖";break;
				case 2:	sessionTypeStr ="脏器称重";break;
				case 3:	sessionTypeStr ="动物解剖、脏器称重";break;
				case 4:	sessionTypeStr ="脏器固定";break;
				case 5:	sessionTypeStr ="动物解剖、脏器固定";break;
				case 6:	sessionTypeStr ="脏器称重、脏器固定";break;
				case 7:	sessionTypeStr ="动物解剖、脏器称重、脏器固定";break;
				case 8:	sessionTypeStr ="固定后称重";break;
				default:
				break;
			}
			
			this.sessionType = new SimpleStringProperty(sessionTypeStr);
			this.sessionCreator = new SimpleStringProperty(sessionCreator);
			this.createTime = new SimpleStringProperty(createTime);
			this.finishSigner = new SimpleStringProperty(finishSigner);
			this.finishSignDate = new SimpleStringProperty(finishSignDate);
		}



		public String getSessionId() {
			return sessionId.get();
		}
		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
		}
		public String getTaskId() {
			return taskId.get();
		}
		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}
		public String getSessionType() {
			return sessionType.get();
		}
		public void setSessionType(String sessionType) {
			this.sessionType = new SimpleStringProperty(sessionType);
		}
		public String getSessionCreator() {
			return sessionCreator.get();
		}
		public void setSessionCreator(String sessionCreator) {
			this.sessionCreator = new SimpleStringProperty(sessionCreator);
		}
		public String getCreateTime() {
			return createTime.get();
		}
		public void setCreateTime(String createTime) {
			this.createTime = new SimpleStringProperty(createTime);
		}
		public String getFinishSigner() {
			return finishSigner.get();
		}
		public void setFinishSigner(String finishSigner) {
			this.finishSigner = new SimpleStringProperty(finishSigner);
		}
		public String getFinishSignDate() {
			return finishSignDate.get();
		}
		public void setFinishSignDate(String finishSignDate) {
			this.finishSignDate = new SimpleStringProperty(finishSignDate);
		}
		
		
		
		
	}
}
