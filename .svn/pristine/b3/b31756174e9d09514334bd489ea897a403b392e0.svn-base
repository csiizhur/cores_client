package com.lanen.view.sign;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.zero.main.MainFrame;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignFrameWithReason extends Application implements Initializable {
	
	
	@FXML
	private Label label1=new Label();
	@FXML
	private Label label2;
	@FXML
	private ComboBox<String> userNameComboBox;
	private ObservableList<String> data = FXCollections.observableArrayList();
	@FXML
	private PasswordField passwordText;
	@FXML
	private TextField reasonText;
	private String reason="";
	private boolean isPass = false;
	private User signUser = null;

	@FXML
	private void onKeyAction(KeyEvent event){
		label2.setText("");
		passwordText.requestFocus();
	}

	@FXML
	private void onOkBtnAction(ActionEvent event) {
		String reason= reasonText.getText().toString().trim();
		String userName = userNameComboBox.getSelectionModel().getSelectedItem();
		String password = passwordText.getText();
		if("".equals(reason)){
			label2.setText("请写明原因");
			reasonText.requestFocus();
			return;
		}
		if(reason.getBytes().length>200){
			label2.setText("'原因'长度不能大于200");
			reasonText.requestFocus();
			return;
		}
		if (null  == userName ||"".equals(userName) ) {
			label2.setText("请选择用户");
			passwordText.requestFocus();
			return;
		} 
		if (null == password || password.equals("")) {
			label2.setText("请输入密码");
			passwordText.requestFocus();
			return;
		}
		List<User> list= SaveUserInfo.getClinicalTestUserList();
		boolean exist = false;
		for(User user:list){
			if(user.getUserName().equals(userName)&&user.getPassword().equals(DigestUtils.md5Hex(password))){
				isPass = true;
				signUser = user;
				this.reason = reason;
				exist=true;
				((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
			}
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
		isPass = false;
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
//		label1.setText(prompt);
//		label2.setText("请输入你的密码");
		//初始化用户名  下拉框
		initUserNameComboBox();
//		reasonText.requestFocus();
	}
	
	/**加载数据
	 * @param msg
	 */
	public void loadData(String msg){
		isPass = false;
		signUser = null;
		reason = null;
		
		label1.setText(msg);
		label2.setText("请输入删除原因");
		reasonText.clear();
		passwordText.clear();
		//更新用户名combobox
		updateUserNameComboBox();
		reasonText.requestFocus();
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initOwner(MainFrame.mainWidow);
		Parent root=FXMLLoader.load(getClass().getResource("SignFrameWithReason.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
//		stage.showAndWait();

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
					label2.setText("");
				}
			}});
		
	}

	/**
	 * 更新用户名combobox
	 */
	public void updateUserNameComboBox(){
		//有 临检登录  权限的用户列表
		List<User> list=SaveUserInfo.getClinicalTestUserList();
		data.clear();
		if(null!=list&&list.size()>0){
			for(User user : list){
//						data.add(user.getUserName());
				if(user.getFlag().equals("可用")){
					data.add(user.getUserName());
				}
			}
		}
		userNameComboBox.getSelectionModel().select(SaveUserInfo.getUser()== null? "":SaveUserInfo.getUser().getUserName());
	}
	
	
	public String getReason() {
		return reason;
	}

	public boolean isPass() {
		return isPass;
	}

	public User getSignUser() {
		return signUser;
	}

	

}
