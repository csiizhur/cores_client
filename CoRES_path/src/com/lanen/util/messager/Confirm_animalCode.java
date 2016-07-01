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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Confirm_animalCode extends Application implements Initializable {
	
	@FXML
	private Label animalCodeLabel;
	@FXML
	private Label studyNoLabel;
	private boolean pass = false;
	
	/**
	 *确定
	 * @param event
	 */
	@FXML
	private void onOkBtnAction(ActionEvent event){
		pass=true;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 取消
	 * @param event
	 */
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		pass=false;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	private static Confirm_animalCode instance;
	public static Confirm_animalCode getInstance(){
		if(null == instance){
			instance = new Confirm_animalCode();
		}
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}


	public void loadData(String studyNo,String animalCode){
		pass = false;
		studyNoLabel.setText("（专题编号："+studyNo+"）");
		animalCodeLabel.setText(animalCode);
		
	}
	@Override
	public void start(final Stage stage) throws Exception {
		Parent root =FXMLLoader.load(getClass().getResource("Confirm_animalCode.fxml"));
		stage.addEventFilter(KeyEvent.KEY_PRESSED, (new EventHandler<KeyEvent>(){
		    @Override
		    public void handle(KeyEvent event) {
		     if(event.getCode()==KeyCode.Y){
		    	 pass=true;
		    	 stage.hide();
		     }else if(event.getCode()==KeyCode.N){
		    	 pass=false;
		    	 stage.hide();
		     }
		    }
		   }));
		stage.setScene(new Scene(root,339,113));
		stage.initStyle(StageStyle.DECORATED);
		stage.setTitle("提示");
		stage.setResizable(false);
		stage.setIconified(false);
	}
	public boolean isPass() {
		return pass;
	}

}
