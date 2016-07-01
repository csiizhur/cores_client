package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AnatomyAnimalConfirm extends Application implements Initializable {
	
	@FXML
	private Label studyNoLabel;
	@FXML
	private Label animalCodeLabel;
	@FXML
	private Label anatomyDateLabel;
	@FXML
	private ComboBox<String> operatorComboBox;
	private ObservableList<String> data_operatorComboBox = FXCollections.observableArrayList();
	private Map<String,String> realNameUserNameMap =new HashMap<String,String>();
//	private Map<String,String> userNameRealNameMap =new HashMap<String,String>();
	
	@FXML
	private VBox deadVbox;
	DatePicker  deadDatePicker = null;	//动物死亡日期
	
	private static String studyNo = "";
	private static String animalCode = "";
	private static String sessionId = "";
	private static String taskId = "";
	private static Integer sessionType = 0;
	private static String anatomyOperator = "";
	
	
	@FXML
	private ComboBox<String> hourComboBox;//时
	@FXML
	private ComboBox<String> minuteComboBox;//分
	
	private static AnatomyAnimalConfirm instance;
	public static AnatomyAnimalConfirm getInstance(){
		if(null == instance){
			instance = new AnatomyAnimalConfirm();
		}
		return instance;
	}
	public static AnatomyAnimalConfirm getInstance2(){
		instance = new AnatomyAnimalConfirm();
	return instance;
}
	
	public AnatomyAnimalConfirm() {
		
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化  hourComboBox，minuteComboBox
		//初始化 operatorComboBox 
		initOperatorComboBox();
		
		initDeadDate();
	}
	/**
	 * 初始化 operatorComboBox 
	 */
	private void initOperatorComboBox() {
		operatorComboBox.setItems(data_operatorComboBox);
	}

	/**
	 * 初始化动物死亡日期
	 */
	private void initDeadDate() {
		deadDatePicker = new DatePicker(Locale.CHINA);
		deadDatePicker.getTextField().setEditable(false);
		deadDatePicker.getTextField().setFocusTraversable(true);
		deadDatePicker.getTextField().setMaxWidth(100);
		deadDatePicker.getTextField().setMinWidth(100);
		deadDatePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		deadDatePicker.getCalendarView().todayButtonTextProperty().set("今天");
		deadDatePicker.getCalendarView().setShowWeeks(false);
		deadDatePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		deadDatePicker.setMaxWidth(100);
		deadVbox.getChildren().add(deadDatePicker);
		deadDatePicker.getTextField().setFocusTraversable(false);
		
	}

	
	/**
	 * @param studyNo
	 * @param animalCode
	 */
	private void updateStudyNoAnimalLabelText(){
		studyNoLabel.setText(studyNo);
		animalCodeLabel.setText(animalCode);
	}
	
	/**更新 
	 * @param sessionId
	 */
	private void updateData_operatorComboBox(){
		data_operatorComboBox.clear();
		realNameUserNameMap.clear();
//		userNameRealNameMap.clear();
		List<String> sessionIdList = new ArrayList<String>();
		sessionIdList.add(sessionId);
		if(sessionType == 1 || sessionType == 3 || sessionType == 5 || sessionType == 7){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblPathSessionService().getUserNameRealName(sessionIdList);
//			String baseSelect="";
			if(null != mapList && mapList.size() > 0){
				for(Map<String,Object> map :mapList){
					String userName = (String) map.get("userName");
					String realName = (String) map.get("realName");
					data_operatorComboBox.add(realName);
//					String currentUserName=SaveUserInfo.getUserName();
//					if(userName.equals(currentUserName)){
//						baseSelect=realName;
//					}
					realNameUserNameMap.put(realName, userName);
//				userNameRealNameMap.put(userName, realName);
				}
			}
			if(data_operatorComboBox.size() == 1){
				operatorComboBox.getSelectionModel().select(0);
			}
//			if(!baseSelect.equals("")){
//				operatorComboBox.getSelectionModel().select(baseSelect);
//			}else{
//				operatorComboBox.getSelectionModel().selectFirst();
//			}
			
//			String lastOperator = AnatomyProcessPage.getInstance().getLastOperatorOfAnimalTable();
//			if(null != lastOperator){
//				for(String str:data_operatorComboBox){
//					if(str.equals(lastOperator)){
//						operatorComboBox.getSelectionModel().select(lastOperator);
//					}
//				}
//			}
		}
	}
	
	/**
	 * 判断是否含解剖(若不含,则 不可以选解剖者和时间,同时赋值解剖者及解剖开始时间)
	 */
	private void updateDataAndState(){
		if(sessionType == 1 || sessionType == 3 || sessionType == 5 || sessionType == 7){
			operatorComboBox.setDisable(false);
			hourComboBox.setDisable(false);
			minuteComboBox.setDisable(false);
			deadDatePicker.getTextField().setDisable(false);
			
			Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date);
			hourComboBox.getSelectionModel().select(calendar.get(Calendar.HOUR_OF_DAY));
			minuteComboBox.getSelectionModel().select(calendar.get(Calendar.MINUTE));
			anatomyDateLabel.setText(DateUtil.dateToString(calendar.getTime(), "yyyy年MM月dd日"));
			deadDatePicker.getTextField().setText(DateUtil.dateToString(calendar.getTime(), "yyyy-MM-dd"));
			
		}else{
			operatorComboBox.setDisable(true);
			hourComboBox.setDisable(true);
			minuteComboBox.setDisable(true);
			deadDatePicker.getTextField().setDisable(true);
			TblAnatomyAnimal tblAnatomyAnimal = BaseService.getInstance().getTblAnatomyAnimalService().getByTaskIdAnimalCode(taskId, animalCode);
			if(null != tblAnatomyAnimal){
				Date anatomyBeginTime = tblAnatomyAnimal.getAnatomyBeginTime();
//				String operator = tblAnatomyAnimal.getAnatomyOperator();
				
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(anatomyBeginTime);
				hourComboBox.getSelectionModel().select(calendar.get(Calendar.HOUR_OF_DAY));
				minuteComboBox.getSelectionModel().select(calendar.get(Calendar.MINUTE));
				anatomyDateLabel.setText(DateUtil.dateToString(calendar.getTime(), "yyyy年MM月dd日"));
				
				deadDatePicker.getTextField().setText(DateUtil.dateToString(tblAnatomyAnimal.getDeadDate(), "yyyy-MM-dd"));
				
				data_operatorComboBox.add(anatomyOperator);
				operatorComboBox.getSelectionModel().select(anatomyOperator);
			}
		}
	}
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String operator = operatorComboBox.getSelectionModel().getSelectedItem();
		Calendar calendar = Calendar.getInstance();
		Date deadDate = null;
		if(sessionType == 1 || sessionType == 3 || sessionType == 5 || sessionType == 7){
			if(null == operator ){
				showErrorMessage("请先选择解剖者");
				return ;
			}
			String hourStr = hourComboBox.getSelectionModel().getSelectedItem();
			String minuteStr = minuteComboBox.getSelectionModel().getSelectedItem();
			
			Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			calendar.setTime(date);
			
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hourStr));
			calendar.set(Calendar.MINUTE, Integer.valueOf(minuteStr));
			
			if(date.before(calendar.getTime())){
				showErrorMessage("解剖开始时间不能大于当前时间！");
				return ;
			}
			
			String deadDateStr = deadDatePicker.getTextField().getText();
			if(null == deadDateStr || "".equals(deadDateStr)){
				showErrorMessage("请选择动物死亡日期！");
				return ;
			}
			
			deadDate = DateUtil.stringToDate(deadDateStr, "yyyy-MM-dd");
			if(deadDate.after(calendar.getTime())){
				showErrorMessage("动物死亡日期不能大于当前日期！");
				return ;
			}
		}
		//操作者（用户名）
		String userName = realNameUserNameMap.get(operator);
		Map<String,Object> map = BaseService.getInstance().getTblAnatomyAnimalService().saveOrUpdate(taskId,sessionId,sessionType,animalCode,userName,calendar.getTime(),deadDate);
		
		if(null != map && null != map.get("msg") && "".equals(map.get("msg"))){
			String studyNo = (String) map.get("studyNo");
			String animalCode = (String) map.get("animalCode");
			//3,病理操作窗口上的动物增加一行数据，并选中最后一行
//			AnatomyProcessPage.getInstance().AddOneDataToAnimalTable(map);
			AnatomyProcessPage.getInstance().updateAnimalTable(AnatomyProcessPage.sessionIdList, studyNo, animalCode);
			//4.关闭当前窗口
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			//5.关闭动物选择窗口
			AnatomyAnimalPage.getInstance().closeWindow();
		}else{
			showErrorMessage(map.get("msg")+"");
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
//	/**
//	 * 加载数据
//	 */
//	public void loadData() {
//		//根据课题编号，更新SD，主题病理负责人，动物种类
//		updateBaseinfoByStudyNo();
//	}

	public void loadData(String taskId,String sessionId,Integer sessionType 
			,String studyNo, String animalCode,String anatomyOperator) {
		// TODO 加载数据
		AnatomyAnimalConfirm.taskId = taskId;
		AnatomyAnimalConfirm.sessionId = sessionId;
		AnatomyAnimalConfirm.studyNo = studyNo;
		AnatomyAnimalConfirm.animalCode = animalCode;
		AnatomyAnimalConfirm.sessionType = sessionType;
		AnatomyAnimalConfirm.anatomyOperator = anatomyOperator;
		updateData_operatorComboBox();
		updateStudyNoAnimalLabelText();
		updateDataAndState();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AnatomyAnimalConfirm.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("动物确认");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
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


}
