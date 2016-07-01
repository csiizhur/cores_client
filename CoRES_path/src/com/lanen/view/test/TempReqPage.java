package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyReq;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.studyplan.TblAnimal;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.BackgroundRunner;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.SignFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class TempReqPage extends Application implements Initializable, Runnable {
	
	
	private String fromPageName = "";
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Label msgLabel;
	@FXML
	private TextField studyNoText;	//专题编号输入框
	
	private String studyNo = "";	//专题编号
	
	private TblStudyPlan tblStudyPlan = null;
	
	@FXML
	private Label reqDateLabel;			//申请日期   Label
	@FXML
	private Label sdLabel;			//SD   Label
	@FXML
	private Label pathDirectorLabel;//专题病理负责人Label
	@FXML
	private Label animalTypeLabel;	//动物种类Label
	
	@FXML
	private HBox anatomyDateHBox;
	DatePicker  anatomyDatePane=null;
	
	@FXML
	private ComboBox<String> testPhaseComboBox;	//试验阶段，ComboBox
	@FXML
	private ComboBox<String> anatomyRsnComboBox;//解剖原因ComboBox
 	
	
	@FXML
	private ListView<String> animalListView ;	//动物列表
	//动物列表值，for 动物id号
	private ObservableList<String> data_animalListView_animalId = FXCollections.observableArrayList();
	//动物列表值，for 动物标号
	private ObservableList<String> data_animalListView_animalCode = FXCollections.observableArrayList();
	@FXML
	private ToggleGroup toggleGroup;	//
	@FXML
	private RadioButton animalIdRadioButton;  //动物Id号
	@FXML
	private RadioButton animalCodeRadioButton; //动物编号
	
	private boolean isOnAnimalId = true;
	@FXML
	private TextField animalText;	//动物编号或动物Id号输入框
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	
	
	
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
//	public  CommPortIdentifier portId;  
	//Enumeration 为枚举型类,在util中 
	public  Enumeration<?> portList;
	//RS-232的串行口 *
//	public static SerialPort serialPort =null;
	public static CommPortIdentifier isUsePortId;	//
	public static SerialPort isUseingSerialPort ;	//备用，用来关闭的
	//线程
	private Thread readThread;
	private static boolean isOpening =false;	//连接设备中
//	private String devIds ="";//连接成功的设备Id
//	private  int baudRateInt=0;
//	private  int dataBitInt=0;
//	private  int stopBitInt=0;
//	private  int checkModeInt=0;
//	
	/**
     * 以上为  串口 通讯 用   
     */
	
	

	private static TempReqPage instance;
	public static TempReqPage getInstance(){
		if(null == instance){
			instance = new TempReqPage();
		}
		return instance;
	}
	
	public TempReqPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化studyNoText
		initStudyNoText();
		//申请日期
		reqDateLabel.setText(DateUtil.getNow("yyyy-MM-dd"));
		//初始化日期选择器
		initDatePane();
		//初始化动物列表
		initAnimalListView();
		//初始化 toggleGroup
		initAnimalRadioButton();
	}
	/**
	 * 初始化studyNoComboBox
	 */
	private void initStudyNoText() {
//		studyNoText.setOnKeyReleased(new EventHandler<KeyEvent>(){
//
//			@Override
//			public void handle(KeyEvent event) {
//				System.out.println(event.getCode()+"");
//				if (event.getSource() instanceof TextField){
//					System.out.println("-----");
//				}
//				TextField textField =(javafx.scene.control.TextField) event.getSource();
//			}
//		});
//		studyNoText.setOnMouseExited(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				TextField textField =(javafx.scene.control.TextField) event.getSource();
//				System.out.println(textField.getText());
//				animalText.requestFocus();
//				textField.requestFocus();
//				textField.end();
//			}
//		});
		studyNoText.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(null != newValue && !newValue){
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							//根据课题编号，更新SD，主题病理负责人，动物种类
							updateBaseinfoByStudyNo_new();
						}
					});
				}
			}

		});
	}
	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		anatomyDatePane = new DatePicker(Locale.CHINA);
		anatomyDatePane.getTextField().setEditable(false);
		anatomyDatePane.getTextField().setFocusTraversable(true);
		anatomyDatePane.getTextField().setMaxWidth(105);
		anatomyDatePane.getTextField().setMinWidth(105);
		anatomyDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		anatomyDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		anatomyDatePane.getCalendarView().setShowWeeks(false);
		anatomyDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		anatomyDatePane.setMaxWidth(105);
		anatomyDateHBox.getChildren().add(anatomyDatePane);
		anatomyDatePane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
	}
	
	/**
	 * 初始化动物列表
	 */
	private void initAnimalListView() {
		animalListView.setItems(data_animalListView_animalId);
	}
	
	/**
	 * 初始化  toggleGroup
	 */
	private void initAnimalRadioButton() {
		animalIdRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					isOnAnimalId = true;
					animalListView.setItems(data_animalListView_animalId);
					animalText.requestFocus();
				}
				
			}});
		animalCodeRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					isOnAnimalId = false;
					animalListView.setItems(data_animalListView_animalCode);
					animalText.requestFocus();
				}
				
			}});
	}
	
	
	
	
	/**
	 * 根据课题编号，更新SD，主题病理负责人，动物种类
	 */
	private void updateBaseinfoByStudyNo_old() {
		
		 new BackgroundRunner() {
			 String currentStudyNo = null;
	         boolean hasChange = false;			//动物编号是否变化
	         TblStudyPlan currentStudyPlan = null;	//

	         
			@Override
			public void background() throws Exception {
				currentStudyNo = studyNoText.getText().trim();
				if(!studyNo.equals(currentStudyNo) && !currentStudyNo.equals("")){
					hasChange = true;
					//根据专题编号查询实体，若专题编号不存在，则返回null，若查询的实体无动物（无动物种类）或无病理计划则返回null
					currentStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo2(currentStudyNo);
				}else if(!studyNo.equals(currentStudyNo)){
					hasChange = true;
				}
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
                e.printStackTrace();
				
			}

			@Override
			public void finish() throws Exception {
				studyNo = currentStudyNo;
				tblStudyPlan = currentStudyPlan;
				
				if("".equals(studyNo)){
					updateState(false,null);
				}else if(hasChange){
					//有改变，
					updateState(hasChange,currentStudyPlan);
				}else if(sdLabel.getText().equals("")){
					studyNoText.requestFocus();
				}
				
			}}.run();
		
	}
	/**
	 * 根据课题编号，更新SD，主题病理负责人，动物种类
	 */
	private void updateBaseinfoByStudyNo_new() {
		String currentStudyNo = null;
		boolean hasChange = false;			//动物编号是否变化
		TblStudyPlan currentStudyPlan = null;	//
		
		currentStudyNo = studyNoText.getText().trim();
		if(!studyNo.equals(currentStudyNo) && !currentStudyNo.equals("")){
			hasChange = true;
			//根据专题编号查询实体，若专题编号不存在，则返回null，若查询的实体无动物（无动物种类）或无病理计划则返回null
			currentStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo2(currentStudyNo);
		}else if(!studyNo.equals(currentStudyNo)){
			hasChange = true;
		}
		
		studyNo = currentStudyNo;
		tblStudyPlan = currentStudyPlan;
		
		if("".equals(studyNo)){
			updateState(false,null);
		}else if(hasChange){
			//有改变，
			updateState(hasChange,currentStudyPlan);
		}else if(sdLabel.getText().equals("")){
			studyNoText.requestFocus();
		}
			
		if(null == tblStudyPlan && testPhaseComboBox.isEditable()){
			testPhaseComboBox.setEditable(false);
			anatomyRsnComboBox.setEditable(false);
		}else if(null != tblStudyPlan && !testPhaseComboBox.isEditable()){
			testPhaseComboBox.setEditable(true);
			anatomyRsnComboBox.setEditable(true);
		}
	}
	
	/**
	 * 更新状态
	 */
	private void updateState(boolean hasChange,TblStudyPlan tblStudyPlan){
		if(hasChange){
			if(null != tblStudyPlan){
				String sd = BaseService.getInstance().getTblStudyPlanService().getSDByStudyNo(tblStudyPlan.getStudyNo());
				String pathSD = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(tblStudyPlan.getStudyNo());
				if(null != sd){
					sdLabel.setText(sd);
				}else{
					sdLabel.setText("");
				}
				pathDirectorLabel.setText(pathSD);
				
				animalTypeLabel.setText(tblStudyPlan.getAnimalType());
				
				animalIdRadioButton.setDisable(false);
				animalCodeRadioButton.setDisable(false);
				addBtn.setDisable(false);
				delBtn.setDisable(false);
				animalText.setEditable(true);
				//清空listview
				data_animalListView_animalId.clear();
				data_animalListView_animalCode.clear();
			}else{
				sdLabel.setText("");
				pathDirectorLabel.setText("");
				animalTypeLabel.setText("");
				
				animalIdRadioButton.setDisable(true);
				animalCodeRadioButton.setDisable(true);
				addBtn.setDisable(true);
				delBtn.setDisable(true);
				animalText.setText("");
				animalText.setEditable(false);
				//清空listview
				data_animalListView_animalId.clear();
				data_animalListView_animalCode.clear();
				
				studyNoText.requestFocus();
				showErrorMessage("该专题编号不存在或该专题无病理计划！");
				
			}
		}else{
			
			sdLabel.setText("");
			pathDirectorLabel.setText("");
			animalTypeLabel.setText("");
			
			animalIdRadioButton.setDisable(true);
			animalCodeRadioButton.setDisable(true);
			addBtn.setDisable(true);
			delBtn.setDisable(true);
			animalText.setText("");
			animalText.setEditable(false);
			
			studyNoText.requestFocus();
		}
	}
	
	/**
	 * 鼠标单击面板时间
	 */
	@FXML
	public void onMouseClicked(MouseEvent event ){
		AnchorPane anchorPane = (javafx.scene.layout.AnchorPane) event.getSource();
		anchorPane.requestFocus();
//		animalText.requestFocus();
//		studyNoText.requestFocus();
//		studyNoText.end();
	}
	/**
	 * 专题编号onAction
	 */
	@FXML
	public void onStudyNoTextAction(ActionEvent event ){
		anchorPane.requestFocus();
	}
	
	/**
	 * ADD 按钮事件（+）
	 * 
	 * @param event
	 */
	@FXML
	private void onAddAction(ActionEvent event) {
		String animalStr = animalText.getText().toString().trim();
		if ("".equals(animalStr)) {// 文本内无数据 则不添加（直接返回）
			return;
		}
		//内部
		TblAnimal tblAnimal =null;
		if(isOnAnimalId){
			if(data_animalListView_animalId.contains(animalStr)){
				showErrorMessage("不可重复添加！");
				return;
			}
			//根据专题计划和动物id号查询实体（该动物未被解剖），若未查询到则返回null
			tblAnimal = BaseService.getInstance().getTblAnimalService().getByStudyPlanAnimalId2(tblStudyPlan,animalStr);
			if(null != tblAnimal){
				data_animalListView_animalId.add(tblAnimal.getAnimalId());
				data_animalListView_animalCode.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
			}else{
				showErrorMessage("动物ID号不存在或该动物已解剖！");
				return;
			}
		}else{
			if(data_animalListView_animalCode.contains(animalStr)){
				showErrorMessage("不可重复添加！");
				return;
			}
			//根据专题计划和动物编号查询实体（该动物未被解剖），若未查询到则返回null
			tblAnimal = BaseService.getInstance().getTblAnimalService().getByStudyPlanAnimalCode2(tblStudyPlan,animalStr);
			if(null != tblAnimal){
				data_animalListView_animalId.add(tblAnimal.getAnimalId());
				data_animalListView_animalCode.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
			}else{
				//该动物是否已解剖或不存在
				boolean isHasAnatomy = BaseService.getInstance().getTblAnatomyReqService()
						.isHasAnatomyByStudyNoAnimalCode(tblStudyPlan.getStudyNo(),animalStr);
				if(isHasAnatomy){
					showErrorMessage("动物编号不存在或该动物已解剖！");
					return;
				}else{
					data_animalListView_animalId.add("");
					data_animalListView_animalCode.add(animalStr);
				}
			}
		}
		animalText.setText("");
		animalText.requestFocus();
	}
	
	/**
	 * DEL按钮事件（-）
	 * 
	 * @param event
	 */
	@FXML
	private void onDelAction(ActionEvent event) {
		String selected= animalListView.getSelectionModel().getSelectedItem();
		int index = animalListView.getSelectionModel().getSelectedIndex();
		if(null!=selected && !"".equals(selected)){
				data_animalListView_animalId.remove(index);
				data_animalListView_animalCode.remove(index);
		}else{
			showErrorMessage("请先选择要删除的数据！");
			return;
		}
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){

		String sd = sdLabel.getText().trim();
		if("".equals(sd)){
			showErrorMessage("请输入专题编号！");
			studyNoText.requestFocus();
			return ;
		}
		String anatomyDateStr = anatomyDatePane.getTextField().getText();
		Date anatomyDate = DateUtil.stringToDate(anatomyDateStr, "yyyy-MM-dd");
		String  testPhase= testPhaseComboBox.getSelectionModel().getSelectedItem();
		if(null == testPhase || "".equals(testPhase)){
			showErrorMessage("请选择试验阶段！");
			testPhaseComboBox.requestFocus();
			return ;
		}
		String  anatomyRsn= anatomyRsnComboBox.getSelectionModel().getSelectedItem();
		if(null == anatomyRsn || "".equals(anatomyRsn)){
			showErrorMessage("请选择解剖原因！");
			anatomyRsnComboBox.requestFocus();
			return ;
		}
		//动物编号列表
		List<String> animalCodeList = new ArrayList<String>();
		if(data_animalListView_animalId.size()>0){
			for(int i = 0;i<data_animalListView_animalCode.size();i++){
				animalCodeList.add(data_animalListView_animalCode.get(i));
			}
		}else{
			animalText.requestFocus();
			showErrorMessage("请录入解剖动物！");
			return ;
		}
//		String userName = SaveUserInfo.getUserName();
//		Date currentDate = new Date();
		TblAnatomyReq tblAnatomyReq = new TblAnatomyReq();
		
		tblAnatomyReq.setStudyNo(studyNo);//专题编号
//		tblAnatomyReq.setAuthor(userName);//创建者
//		tblAnatomyReq.setCreateTime(currentDate);//创建日期
		tblAnatomyReq.setSubmitFlag(1);//已提交
//		tblAnatomyReq.setSubmitter(userName);//提交者
//		tblAnatomyReq.setSubmitDate(currentDate);//提交日期
		tblAnatomyReq.setTestPhase(testPhase);//试验阶段
		tblAnatomyReq.setAnatomyRsn(anatomyRsn.equals("濒死解剖") ? 2:3);//死亡原因
		tblAnatomyReq.setBeginDate(anatomyDate);//解剖日期（始）
		tblAnatomyReq.setEndDate(anatomyDate);//解剖日期（末）
		tblAnatomyReq.setTempFlag(1);//临时申请
		
		
		//判断动物时否都传出
		Json json = BaseService.getInstance().getTblAnatomyReqService().isComeOut(studyNo,animalCodeList);
		//都传出 = false
		boolean isAllComeOut = false;
		if(json.isSuccess()){
			isAllComeOut = true;
		}else{
			isAllComeOut = Messager.showSimpleConfirm("提示", json.getMsg());
		}
		
		if(isAllComeOut){
			//签字窗口
			SignFrame signFrame = new SignFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("身份验证");
			try {
				signFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignFrame.getType())){
				String userName = "";
//				Date currentDate = new Date();
				Date currentDate = BaseService.getInstance().getTblStudyPlanService().getCurrentDate();
				User user = SignFrame.getUser();
				if(null != user){
					userName = user.getUserName();
				}
				
				tblAnatomyReq.setAuthor(userName);//创建者
				tblAnatomyReq.setCreateTime(currentDate);//创建日期
				tblAnatomyReq.setSubmitter(userName);//提交者
				tblAnatomyReq.setSubmitDate(currentDate);//提交日期
				
				//保存临时申请及解剖任务
				String taskId = BaseService.getInstance().getTblAnatomyReqService().saveTempReqAndAnatomyTask(tblAnatomyReq,animalCodeList);
				if(null != taskId && !"".equals(taskId)){
					if(fromPageName.equals("AnimalChipSelectPage")){
						//1.刷新，选中
						//刷新主页面上的任务树及任务表格
						MainPageController.getInstance().updateTaskTreeAndTaskTable();
						TblAnatomyTask tblAnatomyTask1=BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
						MainPageController.getInstance().selectNewTask(tblAnatomyTask1);
						//2.关闭读卡器
						closePort();
						//3.处理动物芯片查询，去除已建任务动物，如果无动物剩下，则关闭动物芯片查询，否则打开串口
						AnimalChipSelectPage.getInstance().delTable(studyNo,animalCodeList);
						//4.关闭当前窗口
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					}else{
						//2.关闭读卡器
						closePort();
						//4.关闭当前窗口
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
						
						AnatomyReqPage.getInstance().updateAnatomyReqTable(1);
						
						//刷新主页面上的任务树及任务表格
						MainPageController.getInstance().updateTaskTreeAndTaskTable();
//						MainPageController.getInstance().selectNewTask(tblAnatomyTask);
						TblAnatomyTask tblAnatomyTask1=BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
						MainPageController.getInstance().selectNewTask(tblAnatomyTask1);
					}
					
				}
			}
		}
		
		
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		closePort();
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 */
	public void loadData() {
		
		this.studyNo = "";
		this.tblStudyPlan = null;
		
		studyNoText.clear();
		
		//根据课题编号，更新SD，主题病理负责人，动物种类
		updateBaseinfoByStudyNo_new();
		
		fromPageName = "";
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				//打开串口
				openPort();
			}
			
		});
		
		testPhaseComboBox.getSelectionModel().clearSelection();
		anatomyRsnComboBox.getSelectionModel().clearSelection();
	}
	
	/**
	 * 加载数据
	 * @param studyPlan 
	 */
	public void loadData(String studyNo,List<String> animalCodeList, TblStudyPlan studyPlan) {
		fromPageName = "AnimalChipSelectPage";
		this.studyNo = "";
		this.tblStudyPlan = null;
		
		this.studyNo = studyNo;
		this.tblStudyPlan = studyPlan;
		studyNoText.clear();
		studyNoText.setText(studyNo);
		
		//根据课题编号，更新SD，主题病理负责人，动物种类
//		updateBaseinfoByStudyNo();
		
		animalCodeRadioButton.setSelected(true);
		addAnimalCodeList(studyNo,animalCodeList);
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				//打开串口
				openPort();
			}
			
		});
		testPhaseComboBox.getSelectionModel().clearSelection();
		anatomyRsnComboBox.getSelectionModel().clearSelection();
	}
	
	/**
	 * 打开串口
	 */
	public  void openPort(){
		String hostName = SystemTool.getHostName();
		//查询询芯片阅读器的 设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
		Map<String,Object> chipReaderMap = BaseService.getInstance().getTblChipReaderService().findChipReaderMapByHostNameEnable(hostName,1);
		//打开串口
		openPort(chipReaderMap);
	}
	/**
	 * 打开串口 
	 * @param chipReaderMap设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 */
	private void openPort(Map<String,Object> chipReaderMap) {
		if(null == chipReaderMap ){
			msgLabel.setText("芯片阅读器的连接信息未设置。");
			return ;
		}
		String comPort = (String) chipReaderMap.get("commName");     //波特率                             
		Integer baudRate = (Integer) chipReaderMap.get("baud");      //数据位   5 6 7 8                   
		Integer dataBit = (Integer) chipReaderMap.get("dataBit");    //停止位    1,1.5,2                  
		Integer stopBit = (Integer) chipReaderMap.get("stopBit");    //校验位    None，Even，Odd，Space，Mark 
		if(stopBit == 15){
			stopBit = 3;
		}
		Integer checkMode = (Integer) chipReaderMap.get("parit");
		if(null == comPort || "".equals(comPort) ){
			msgLabel.setText("芯片阅读器的连接信息未设置。");
			return ;
		}
		//获取系统中所有的通讯端口
		portList=CommPortIdentifier.getPortIdentifiers();
		boolean isExistComPort=false;   //设置的端口是否存在
		while(portList.hasMoreElements()){
			SerialPort serialPort =null;
			System.out.println(portList.hasMoreElements());
			//强制转换为通讯端口类型
			CommPortIdentifier portId=(CommPortIdentifier) portList.nextElement();
			if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
				System.out.println(portId.getName());
				if(portId.getName().equals(comPort)){//判断串口名是否相同
					isExistComPort=true;//串口存在
					try {
						if(portId.isCurrentlyOwned()){
							msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用！");
							return;
						}
						serialPort = (SerialPort) portId.open("0001", 2000);//打开端口
						
						isUsePortId = portId;	//留备份，关闭用
						isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
						msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用！");
						//e1.printStackTrace();
						return;
					}
					//设置串口监听器
					try {
						serialPort.addEventListener(new SerialPortEventListener_tempReqPage());
					} catch (TooManyListenersException e1) {
						serialPort.close(); 
					}
					//侦听到串口有数据,触发串口事件
					serialPort.notifyOnDataAvailable(true);  
					isOpening =true;
					try {
						serialPort.setSerialPortParams(baudRate, 
								dataBit, 
								stopBit, 
								checkMode);
						msgLabel.setText("芯片阅读器已连接。");
					} catch (UnsupportedCommOperationException e1) {
						e1.printStackTrace();
					}
					
				}//if end;
				//已经找到端口，退出while循环
				if(isExistComPort){
					break;
				}
			}//if end;
		}//while end;
		if(!isExistComPort){
			msgLabel.setText("芯片阅读器端口（"+comPort+"）不存在！");
			return;
		}else{
			isExistComPort=false;
		}
		readThread=new Thread(this);
		readThread.start();//线程负责每接收一次数据休眠2秒钟
	}
	/**
	 * 关闭端口
	 */
	private void closePort(){
		if(null != isUsePortId ){
			//关闭关闭端口连接
			isUseingSerialPort.removeEventListener();
			isUseingSerialPort.notifyOnDataAvailable(false); 
			isUseingSerialPort.close();
			isOpening =false;
		}
		isUsePortId = null;
		isUseingSerialPort = null;
		portList = null;
	}
	
	/**
	 * 添加动物编号
	 */
	private void addAnimalCodeList(String studyNo,List<String> animalCodeList){
		data_animalListView_animalId.clear();
		data_animalListView_animalCode.clear();
		TblAnimal tblAnimal = null;
		for(String animalCode :animalCodeList){
			//根据专题计划和动物编号查询实体（该动物未被解剖），若未查询到则返回null
			tblAnimal = BaseService.getInstance().getTblAnimalService().getByStudyPlanAnimalCode2(tblStudyPlan,animalCode);
			if(null != tblAnimal){
				data_animalListView_animalId.add(tblAnimal.getAnimalId());
				data_animalListView_animalCode.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
			}else{
				//该动物是否已解剖或不存在
				boolean isHasAnatomy = BaseService.getInstance().getTblAnatomyReqService()
						.isHasAnatomyByStudyNoAnimalCode(tblStudyPlan.getStudyNo(),animalCode);
				if(isHasAnatomy){
					showErrorMessage("动物编号不存在或该动物已解剖！");
					return;
				}else{
					data_animalListView_animalId.add("");
					data_animalListView_animalCode.add(animalCode);
				}
			}
		}
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TempReq.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("临时申请&解剖任务");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				closePort();
			}
		});
		stage.show();
		
	}
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			if(!isOpening){
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**查询动物芯片，并添加动物
	 * @param iCardCode
	 */
	public void selectAnimalByICardCode(String iCardCode) {
		//1.
		if(null == iCardCode || "".equals(iCardCode) ){
			showErrorMessage("请重新扫描！");
			return;
		}
		//2.
		animalCodeRadioButton.setSelected(true);
		//3.查询
		Map<String,Object> studyNoAnimalCodeMap = BaseService.getInstance().getTblChipReaderService().getStudyNoAnimalCodeMapByICardCode(iCardCode);
		if(null != studyNoAnimalCodeMap){
			String studyNo = (String) studyNoAnimalCodeMap.get("studyNo");
			String animalCode = (String) studyNoAnimalCodeMap.get("animalCode");
			if(data_animalListView_animalCode.size() > 0){
				if(this.studyNo.equals(studyNo)){
					if(data_animalListView_animalCode.contains(animalCode)){
						showErrorMessage("不可重复添加！");
						return;
					}
					//根据专题计划和动物编号查询实体（该动物未被解剖），若未查询到则返回null
					TblAnimal tblAnimal = BaseService.getInstance().getTblAnimalService().getByStudyPlanAnimalCode2(tblStudyPlan,animalCode);
					if(null != tblAnimal){
						data_animalListView_animalId.add(tblAnimal.getAnimalId());
						data_animalListView_animalCode.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
					}else{
						//该动物是否已解剖或不存在
						boolean isHasAnatomy = BaseService.getInstance().getTblAnatomyReqService()
								.isHasAnatomyByStudyNoAnimalCode(tblStudyPlan.getStudyNo(),animalCode);
						if(isHasAnatomy){
							showErrorMessage("动物编号不存在或该动物已解剖！");
							return;
						}else{
							data_animalListView_animalId.add("");
							data_animalListView_animalCode.add(animalCode);
						}
					}
				}else{
					showErrorMessage("该动物非当前专题下动物！");
				}
			}else{
				this.studyNo = studyNo;
				tblStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo2(studyNo);
				studyNoText.setText(studyNo);
				updateState(true,tblStudyPlan);
				
				if(null != tblStudyPlan){
					
					//根据专题计划和动物编号查询实体（该动物未被解剖），若未查询到则返回null
					TblAnimal tblAnimal = BaseService.getInstance().getTblAnimalService().getByStudyPlanAnimalCode2(tblStudyPlan,animalCode);
					if(null != tblAnimal){
						data_animalListView_animalId.add(tblAnimal.getAnimalId());
						data_animalListView_animalCode.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
					}else{
						//该动物是否已解剖或不存在
						boolean isHasAnatomy = BaseService.getInstance().getTblAnatomyReqService()
								.isHasAnatomyByStudyNoAnimalCode(tblStudyPlan.getStudyNo(),animalCode);
						if(isHasAnatomy){
							showErrorMessage("动物编号不存在或该动物已解剖！");
							return;
						}else{
							data_animalListView_animalId.add("");
							data_animalListView_animalCode.add(animalCode);
						}
					}
					
					
				}else{
					showErrorMessage("该专题编号（"+studyNo+"）在专题系统中未登记！");
					return;
				}
			}
		}else{
			showErrorMessage("卡号（"+iCardCode+"）不存在！");
		}
		
		if(null == tblStudyPlan && testPhaseComboBox.isEditable()){
			testPhaseComboBox.setEditable(false);
			anatomyRsnComboBox.setEditable(false);
		}else if(null != tblStudyPlan && !testPhaseComboBox.isEditable()){
			testPhaseComboBox.setEditable(true);
			anatomyRsnComboBox.setEditable(true);
		}
	}
}
