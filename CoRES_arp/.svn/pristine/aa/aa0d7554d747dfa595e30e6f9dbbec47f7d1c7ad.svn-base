package com.lanen.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**   数据接收信息
 * @author Administrator
 *
 */
public class DataAcceptMeaage extends Application implements Initializable{
	
	@FXML
	private TextArea textArea;
	
	private static DataAcceptMeaage instance;
	public static DataAcceptMeaage getInstance(){
		if(null == instance){
			instance = new DataAcceptMeaage();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		
	}

	/**加载数据
	 * @param list
	 */
	public void loadData(List<String> list){
		updateTextAreaData(list);
	}
	/**更新文本框值
	 * @param list
	 */
	private void updateTextAreaData(List<String> list) {
		textArea.clear();
		if(null != list && list.size() > 0){
			for(String str : list){
				textArea.setText(textArea.getText()+str+"\n");
			}
		}
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DataAcceptMeaage.fxml"));
		Scene scene = new Scene(root,580,394);
		stage.setScene(scene);
		stage.setTitle("警告信息");
		stage.setMinWidth(580);
		stage.setMinHeight(394);
	}
	/**
	 * 关闭按钮  点击事件
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}

	
}
