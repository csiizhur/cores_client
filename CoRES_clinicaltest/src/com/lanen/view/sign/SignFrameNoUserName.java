package com.lanen.view.sign;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.zero.main.MainFrame;

public class SignFrameNoUserName extends Application implements Initializable {
	
	
	@FXML
	private Label label1=new Label();
	@FXML
	private Label label2;
	@FXML
	private TextField userNameText;
	@FXML
	private PasswordField passwordText;
	private User signUser = null;

	@FXML
	private void onKeyAction(KeyEvent event){
		label2.setText("");
	}
	
	/**
	 * 用户名文本框  
	 */
	@FXML
	private void onUserNameText(){
		if(userNameText.getText().trim().equals("")){
			userNameText.requestFocus();
		}else{
			passwordText.requestFocus();
		}
	}
	@FXML
	private void onOkBtnAction(ActionEvent event) {
		String userName = userNameText.getText().trim();
		String password = passwordText.getText();
		if (null  == userName ||"".equals(userName) ) {
			label2.setText("请输入用户名");
			userNameText.requestFocus();
			return;
		} 
		if (null == password || password.equals("")) {
			label2.setText("请输入密码");
			passwordText.requestFocus();
			return;
		}
		boolean exist = false;
		
		User user = BaseService.getUserService().findUserByUserNamePassword(userName, password);
		if(null != user && user.getFlag().equals("可用")){
			signUser = user;
			exist=true;
			((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
		}
		if(!exist){
			label2.setText("用户名或密码错误");
		}

	}
	@FXML
	private void onTextAction(MouseEvent event){
		label2.setText("");
	}
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		signUser = null;
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	
	private static SignFrameNoUserName instance;
	public static SignFrameNoUserName getInstance(){
		if(null == instance){
			instance = new SignFrameNoUserName();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}
	
	/**加载数据
	 * @param msg1
	 * @param msg2
	 */
	public void loadData(String msg1){
		signUser = null;
		label1.setText(msg1);
		label2.setText("请输入用户名");
		userNameText.clear();
		passwordText.clear();
		userNameText.requestFocus();
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initOwner(MainFrame.mainWidow);
		Parent root=FXMLLoader.load(getClass().getResource("SignFrameNoUserName.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
//		stage.showAndWait();

	}

	public User getSignUser() {
		return signUser;
	}
	
	

}
