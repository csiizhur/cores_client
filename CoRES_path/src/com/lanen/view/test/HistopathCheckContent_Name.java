package com.lanen.view.test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.util.messager.Messager;

/**组织取材_排除原因
 * @author Administrator
 *
 */
public class HistopathCheckContent_Name extends Application implements Initializable {
	
	
	@FXML
	private TextField text;
	
	/**
	 * 专题编号
	 */
	private boolean isConfirm = false;
	
	/**
	 * 排除原因
	 */
	private String contentName = "";
	
	private static HistopathCheckContent_Name instance;
	public static HistopathCheckContent_Name getInstance(){
		if(null == instance){
			instance = new HistopathCheckContent_Name();
		}
		return instance;
	}

	public HistopathCheckContent_Name() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
	}

	
	/**确定
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
		
		contentName = text.getText().trim();
		if(null == contentName || "".equals(contentName)){
			showErrorMessage("请填写名称！");
			text.requestFocus();
			return ;
		}
		if(contentName.getBytes().length > 200){
			showErrorMessage("名称长度不能大于200！");
			text.requestFocus();
			return ;
		}
		
		isConfirm = true;
		javafx.scene.control.Control source =  (Control) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		isConfirm = false;
		contentName = "";
		Button source = (Button) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String contentName) {
		isConfirm = false;
		this.contentName = "";
		text.setText(contentName);
		text.requestFocus();
	}


	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathCheckContent_name.fxml"));
		Scene scene = new Scene(root, 503, 328);
		stage.setScene(scene);
		stage.setTitle("镜检内容组合名称");
		stage.setResizable(false);
		stage.setMinWidth(503);
		stage.setMinHeight(328);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
//		stage.showAndWait();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	
}
