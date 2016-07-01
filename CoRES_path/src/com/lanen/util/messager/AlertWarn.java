package com.lanen.util.messager;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lanen.view.main.Main;

public class AlertWarn extends Application implements Initializable {
	
	@FXML
	private Text text1;//信息
	
	/**
	 * 确定按钮
	 * @param event
	 */
	@FXML
	private void onOkBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	private static AlertWarn instance;
	public static AlertWarn getInstance(){
		if(null == instance){
			instance = new AlertWarn();
		}
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}

	public void loadData(String message){
		text1.setText(message);
	}
	@Override
	public void start(final Stage stage) throws Exception {
		Parent root =FXMLLoader.load(getClass().getResource("Alert_warn.fxml"));
		stage.addEventFilter(KeyEvent.KEY_PRESSED, (new EventHandler<KeyEvent>(){
		    @Override
		    public void handle(KeyEvent event) {
		     if(event.getCode()==KeyCode.O){
		    	 stage.hide();
		     }
		    }
		   }));
		stage.initOwner(Main.mainWidow);
		stage.setScene(new Scene(root));
		stage.initStyle(StageStyle.UTILITY);
		stage.setTitle("警告");
		stage.setResizable(false);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
