package com.lanen.view.sign;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;

public class SignFrameUserRsn extends Application implements Initializable {
	
	
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
	private ComboBox<String> userNameComboBox;
	private ObservableList<String> data = FXCollections.observableArrayList();
	/**
	 * 密码
	 */
	@FXML
	private PasswordField passwordText;

	/**
	 * 原因
	 */
	private String reason;
	
	private List<User> userList = null;
	
	private User signUser;
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
		String userName = userNameComboBox.getSelectionModel().getSelectedItem();
		if (null  == userName ||"".equals(userName) ) {
			msglabel.setText("请选择用户");
			passwordText.requestFocus();
			return;
		} 
		boolean exist = false;
		for(User user:userList){
			if(user.getUserName().equals(userName)&&user.getPassword().equals(DigestUtils.md5Hex(password))){
				pass = true;
				signUser = user;
				exist=true;
				((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
			}
		}
		if(!exist){
			msglabel.setText("用户名或密码错误");
		}
		

	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		signUser = null;
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	
	private static SignFrameUserRsn instance;
	public static SignFrameUserRsn getInstance(){
		if(null == instance){
			instance = new SignFrameUserRsn();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		//初始化用户名  下拉框
		initUserNameComboBox();
	}
	/**
	 * 初始化用户名  下拉框
	 */
	private void initUserNameComboBox() {
		
		userNameComboBox.setItems(data);
		userNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					passwordText.requestFocus();
				}
			}});
		
	}
	/**加载数据
	 * @param reasonLabelMsg
	 */
	public void loadData(String reasonLabelMsg,List<String> userNameList) {
		reasonLabel.setText(reasonLabelMsg);
		reason = "";
		signUser = null;
		pass = false;
		//更新用户名
		updateUserNameComboBox(userNameList);
		
		reasonText.clear();
		reasonText.requestFocus();
		passwordText.clear();
	}
	private void updateUserNameComboBox(List<String> userNameList) {
//		//有 临检登录  权限的用户列表
		
		data.clear();
		userList = SaveUserInfo.getPathUserList();
		if(null!=userNameList && userNameList.size()>0){
			for(String userName : userNameList){
				data.add(userName);
			}
			userNameComboBox.getSelectionModel().select(0);
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("SignFrameUserRsn.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
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
	public User getSignUser() {
		return signUser;
	}

}
