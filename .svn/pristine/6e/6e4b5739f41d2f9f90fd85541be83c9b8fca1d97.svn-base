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
import javafx.stage.Stage;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;

public class SignFrameWithReason extends Application implements Initializable {
	
	
	/**
	 * 提示信息
	 */
	@FXML
	private Label msglabel;
	/**
	 * 原因label（如删除原因）
	 */
	@FXML
	private Label reasonLabel;
	/**
	 * 原因text
	 */
	@FXML
	private TextField reasonText;
	/**
	 * 用户名
	 */
	@FXML
	private TextField userNameText;
	/**
	 * 密码
	 */
	@FXML
	private PasswordField passwordText;

	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 是否通过（选是  还是否）
	 */
	private boolean pass = false;
	
	/**原因text回车事件
	 * @param event
	 */
	@FXML
	private void onReasonTextAction(ActionEvent event){
		reason = reasonText.getText().trim();
		if("".equals(reason)){
			reasonText.clear();
			msglabel.setText(reasonLabel.getText()+"不能为空！");
			reasonText.requestFocus();
		}else if("".equals(passwordText.getText())){
			msglabel.setText("请输入密码！");
			passwordText.requestFocus();
		}else{
			//点击确定按钮
			onOkBtnAction(null);
		}
	}
	/**输入框鼠标点击事件（密码text、原因text）
	 * @param event
	 */
	@FXML
	private void onTextKeyPressedAction(KeyEvent event){
		msglabel.setText("");
	}

	/**确定按钮或 密码输入框回车
	 * @param event
	 */
	@FXML
	private void onOkBtnAction(ActionEvent event) {
		reason= reasonText.getText().toString().trim();
		String password = passwordText.getText();
		if("".equals(reason)){
			msglabel.setText(reasonLabel.getText()+"不能为空！");
			reasonText.requestFocus();
			return;
		}
		if(reason.getBytes().length>200){
			msglabel.setText(reasonLabel.getText()+"长度不能大于200！");
			reasonText.requestFocus();
			return;
		}
		if (null == password || password.equals("")) {
			msglabel.setText("请输入密码！");
			passwordText.requestFocus();
			return;
		}
		User user = SaveUserInfo.getUser();
		if(null != user && user.getPassword().equals(DigestUtils.md5Hex(password))){
			pass = true;
//			((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
			passwordText.getScene().getWindow().hide();
		}else{
			msglabel.setText("密码错误！");
			passwordText.requestFocus();
			return;
		}

	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	
	private static SignFrameWithReason instance;
	public static SignFrameWithReason getInstance(){
		if(null == instance){
			instance = new SignFrameWithReason();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
	}
	/**加载数据
	 * @param reasonLabelMsg
	 */
	public void loadData(String reasonLabelMsg) {
		reasonLabel.setText(reasonLabelMsg);
		reason = "";
		pass = false;
		//更新用户名
		updateUserNameText();
		
		reasonText.clear();
		reasonText.requestFocus();
		passwordText.clear();
	}
	
	/**
	 * 更新用户名
	 */
	private void updateUserNameText(){
		userNameText.setText(SaveUserInfo.getUserName());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("SignFrameWithReason.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
//		stage.showAndWait();
//		stage.show();
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	

}
