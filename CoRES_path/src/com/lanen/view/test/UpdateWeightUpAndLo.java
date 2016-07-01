package com.lanen.view.test;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;

public class UpdateWeightUpAndLo extends Application implements Initializable{
	
	@FXML
	private ComboBox<String> taskComboBox;//解剖任务
	private ObservableList<String> data_taskComboBox = FXCollections.observableArrayList();
	// studyNo:anatomyNum -> taskId
	private Map<String,TblAnatomyTask> task2TblAnatomyTaskMap  = new HashMap<String,TblAnatomyTask>();

	@FXML
	private TextField 	lowerTextField;//下限
	
	@FXML
	private TextField upperTextField;//上限
	
	private static UpdateWeightUpAndLo instance;
	
	public static UpdateWeightUpAndLo getInstance(){
		if(null == instance){
			instance = new UpdateWeightUpAndLo();
		}
		return instance;
	}
	
	public UpdateWeightUpAndLo() {
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTaskComboBox();
	}
	
	
	/**
	 * 初始化 taskComboBox
	 */
	private void initTaskComboBox() {
		taskComboBox.setItems(data_taskComboBox);
		taskComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					TblAnatomyTask anatomyTask = task2TblAnatomyTaskMap.get(newValue);
					String  weightLower = anatomyTask.getWeightLower();//下限
					String weightUpper = anatomyTask.getWeightUpper();//上限
					lowerTextField.clear();
					upperTextField.clear();
					if(null  != weightLower && !weightLower.equals("")){
						lowerTextField.setText(weightLower);
					}
					if(null  != weightUpper && !weightUpper.equals("")){
						upperTextField.setText(weightUpper);
					}
				}else{
					lowerTextField.clear();
					upperTextField.clear();
				}
				
			}});
	}

	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * 保存
	 * @param event
	 */
	@FXML
	private void saveUpdateWeightUpAndLo(ActionEvent event){
		
		String selectedItem = taskComboBox.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请选择解剖任务！");
			return;
		}
		String  weightLower =lowerTextField.getText().trim();
		String weightUpper = upperTextField.getText().trim();
		if(!NumberValidationUtils.isRealNumber(weightLower)){
			showErrorMessage("请输入正确数据格式！");
			lowerTextField.requestFocus();
			return;
		}
		if(!NumberValidationUtils.isRealNumber(weightUpper)){
			showErrorMessage("请输入正确数据格式！");
			upperTextField.requestFocus();
			return;
		}
		
		TblAnatomyTask anatomyTask = task2TblAnatomyTaskMap.get(selectedItem);
		
//		User signUser = Sign.openSignWithPrivilegeName("身份验证", "", "毒性病理_称重报警设置");
		User signUser = SaveUserInfo.getUser();
		if(null != signUser){
			anatomyTask.setWeightLower(weightLower);
			anatomyTask.setWeightUpper(weightUpper);
			BaseService.getInstance().getTblAnatomyTaskService().update(anatomyTask);
			AnatomyProcessPage.getInstance().updateUpperLabelAndLowerLabel();
			// 记录退出系统日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());// 系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());// 系统版本
			tblLog.setOperatType("修改称重报警范围");
			tblLog.setOperatOject(SystemMessage.getSystemFullName());
//			User user = SaveUserInfo.getUser();
//			if (null != user) {
				tblLog.setOperator(signUser.getRealName());
//			}
			tblLog.setOperatContent("修改专题"+anatomyTask.getStudyNo()+"第"+anatomyTask.getAnatomyNum()+"解剖的称重报警范围");
			tblLog.setOperatHost(SystemTool.getHostName() + ","
					+ SystemTool.getIPAddress());
			try {

				BaseService.getInstance().getTblLogService().save(tblLog);
			} catch (Exception e) {
				System.exit(1);
			}
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}
		//签字窗口
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("身份验证");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//签字通过
//		if("OK".equals(SignFrame.getType())){
//			
//		}
	}
	
	/**
	 * 更新taskComboBox值
	 */
	private void updateData_taskComboBox(List<String> taskIdList){
		task2TblAnatomyTaskMap.clear();
		taskComboBox.getSelectionModel().clearSelection();
		data_taskComboBox.clear();
		if(null != taskIdList && taskIdList.size() > 0){
			for(String taskId:taskIdList){
				TblAnatomyTask anatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
				data_taskComboBox.add(anatomyTask.getStudyNo()+":"+anatomyTask.getAnatomyNum());
				task2TblAnatomyTaskMap.put(anatomyTask.getStudyNo()+":"+anatomyTask.getAnatomyNum(), anatomyTask);
			}
			if(taskIdList.size() == 1){
				taskComboBox.getSelectionModel().select(0);
			}
		}
	}
	
	/**
	 * 加载数据
	 */
	public void loadData(List<String> taskIdList) {
		//更新taskComboBox值
		updateData_taskComboBox(taskIdList);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("UpdateWeightUpAndLo.fxml"));
		Scene scene = new Scene(root, 330, 250);
		stage.setScene(scene);
		stage.setTitle("称重报警范围设置");
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
