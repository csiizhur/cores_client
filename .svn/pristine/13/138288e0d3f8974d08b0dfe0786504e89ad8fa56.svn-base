package com.lanen.view.tblsession;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SessionFrame extends Application implements Initializable{
	
	@FXML
	private Label createrLabel;
	@FXML
	private Label animalTypeLabel;
	@FXML
	private Label createTimeLabel;
	@FXML
	private Label sessionTypeLabel;
	@FXML
	private Label recIdLabel;
	@FXML
	private Label studyNoLabel;
	@FXML
	private Label totalNumLabel;
	
	private static SessionFrame instance;
	public static SessionFrame getInstance(){
		if(null == instance){
			instance = new SessionFrame();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}

	/**
	 * 取消
	 * @param event
	 */
	@FXML
	private void onExitButton(ActionEvent event){
		Button button =(Button) event.getSource();
		button.getScene().getWindow().hide();
	}
	/**
	 * 确定
	 * @param event
	 */
	@FXML
	private void onOkButton(ActionEvent event ){
		Button button =(Button) event.getSource();
		button.getScene().getWindow().hide();
		//打开对应窗口，及保存呢会话
//		DayToDayController.getInstance().openWindowAndSaveSession();
		
//		创建会话及动物列表（暂不保存到数据库），打开新窗口
		DayToDayController.getInstance().createSessionAndopenNewWindow();
	}
	/**
	 * 控件绑定数据
	 */
	public void loadData(String creater,String createTime,String sessionType,String animalType
			,String recId,String studyNo,String totalNum){
		createrLabel.setText(creater);
		createTimeLabel.setText(createTime);
		sessionTypeLabel.setText(sessionType);
		animalTypeLabel.setText(animalType);
		recIdLabel.setText(recId);
		studyNoLabel.setText(studyNo);
		totalNumLabel.setText(totalNum);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load( this.getClass().getResource("SessionFrame.fxml"));
		Scene scene = new Scene(root,449,236);
		stage.setScene(scene);
		stage.setTitle("创建会话");
		stage.setResizable(false);
		stage.show();
	}

}
