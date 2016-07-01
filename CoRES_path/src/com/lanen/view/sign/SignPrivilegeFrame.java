package com.lanen.view.sign;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.view.main.Main;

public class SignPrivilegeFrame extends Application implements Initializable {
	
	
	@FXML
	private Label label1=new Label();
	@FXML
	private Label label2;
	@FXML
	private ComboBox<String> userNameComboBox;
	private ObservableList<String> data = FXCollections.observableArrayList();
	@FXML
	private PasswordField passwordText;
	private User signUser;
	
	private List<User> userList = null;

	@FXML
	private void onKeyAction(KeyEvent event){
		label2.setText("");
		passwordText.requestFocus();
	}

	@FXML
	private void onOkBtnAction(ActionEvent event) {
		String userName = userNameComboBox.getSelectionModel().getSelectedItem();
		String password = passwordText.getText();
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
//		List<User> list= SaveUserInfo.getClinicalTestUserList();
		boolean exist = false;
		for(User user:userList){
			if(user.getUserName().equals(userName)&&user.getPassword().equals(DigestUtils.md5Hex(password))){
				signUser = user;
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
		signUser = null;
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	private static SignPrivilegeFrame instance;
	public static SignPrivilegeFrame getInstance(){
		if(null == instance){
			instance = new SignPrivilegeFrame();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		label2.setText("请输入您的密码");
		//初始化用户名  下拉框
		initUserNameComboBox();
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initOwner(Main.mainWidow);
		Parent root=FXMLLoader.load(getClass().getResource("SignPrivilegeFrame.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
//		stage.showAndWait();

	}
	
	
	/**加载数据
	 * @param msg
	 * @param privilegeName 
	 */
	public void loadData(String msg, String privilegeName){
		label1.setText(msg);
		label2.setText("");
		passwordText.clear();
		userNameComboBox.requestFocus();
		signUser = null;
		updateUserNameComboBox(privilegeName);
	}
	/**加载数据
	 * @param msg
	 * @param privilegeName 
	 */
	public void loadData(String msg, List<String> userNameList){
		label1.setText(msg);
		label2.setText("");
		passwordText.clear();
		userNameComboBox.requestFocus();
		signUser = null;
		updateUserNameComboBox(userNameList);
	}
	private void updateUserNameComboBox(List<String> userNameList) {
//		//有 临检登录  权限的用户列表
		
		data.clear();
		userList = SaveUserInfo.getPathUserList();
		if(null!=userNameList && userNameList.size()>0){
			for(String userName : userNameList){
				data.add(userName);
			}
		}
		
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
	 * 更新用户列表
	 * @param privilegeName 
	 */
	private void updateUserNameComboBox(String privilegeName){
//		//有 临检登录  权限的用户列表
//		List<User> list=SaveUserInfo.getClinicalTestUserList();
		
		userList = BaseService.getInstance().getUserService().findByPrivilegeName2(privilegeName);
		
		data.clear();
		if(null!=userList && userList.size()>0){
			for(User user : userList){
				if(user.getFlag().equals("可用")){
					data.add(user.getUserName());
				}
			}
		}
//		userNameComboBox.getSelectionModel().select(0);
	}
	public User getSignUser() {
		return signUser;
	}

	
}
