package com.lanen.view;


import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.util.SaveUserInfo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PasswordResetFrame extends Application implements Initializable {
	
	
	@FXML
	private Label label1=new Label();
	@FXML
	private Label label2;
	@FXML
	private TextField text;
	@FXML
	private Button okBtn=new Button();
	private static String type="";
	private static  String prompt="";
	
	public PasswordResetFrame(){
		PasswordResetFrame.type="Cancel";
	}
	public PasswordResetFrame(String prompt){
		label1.setText(prompt);
		PasswordResetFrame.setPrompt(prompt);
		PasswordResetFrame.type="Cancel";
	}


	@FXML
	private void onOkBtnAction(ActionEvent event){
		String password=text.getText();
		if(null!=SaveUserInfo.getUser()){
			if(DigestUtils.md5Hex(password).equals(SaveUserInfo.getUser().getPassword())){
				PasswordResetFrame.setType("OK");
				((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
			}else{
				label2.setText("密码不正确！");
			}
		}else{
			label2.setText("密码不正确！");
		}
	}
	@FXML
	private void onTextAction(MouseEvent event){
		label2.setText("");
	}
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		PasswordResetFrame.setType("Cancel");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label1.setText(prompt);
		label2.setText("");
		text.setFocusTraversable(false);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("PasswordResetFrame.fxml"));
		Scene scene = new Scene(root,285,125);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("密码重置");
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
		stage.showAndWait();

	}
	
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		PasswordResetFrame.type = type;
	}
	public static void setPrompt(String prompt) {
		PasswordResetFrame.prompt = prompt;
	}
	


}
