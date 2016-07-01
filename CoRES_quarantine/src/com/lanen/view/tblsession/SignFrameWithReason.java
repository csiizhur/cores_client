package com.lanen.view.tblsession;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.view.main.Main;

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
	private static String type="";
	private static String reason="";
	private static  String prompt="";
	private static User user;
	public SignFrameWithReason(){
		SignFrameWithReason.type="Cancel";
	}
	public SignFrameWithReason(String prompt){
		label1.setText(prompt);
		SignFrameWithReason.setPrompt(prompt);
		SignFrameWithReason.type="Cancel";
	}

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
		List<User> list= SaveUserInfo.getQuarantineUserList();
		boolean exist = false;
		for(User user:list){
			if(user.getUserName().equals(userName)&&user.getPassword().equals(DigestUtils.md5Hex(password))){
				SignFrameWithReason.setType("OK");
				SignFrameWithReason.setUser(user);
				SignFrameWithReason.setReason(reason);
				exist=true;
				((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
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
		SignFrameWithReason.setType("Cancel");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label1.setText(prompt);
		label2.setText("请输入你的密码");
		//初始化用户名  下拉框
		initUserNameComboBox();
		reasonText.requestFocus();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initOwner(Main.getInstance().getStage());
		Parent root=FXMLLoader.load(getClass().getResource("SignFrameWithReason.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
		stage.showAndWait();

	}
	
	/**
	 * 初始化用户名  下拉框
	 */
	private void initUserNameComboBox() {
		//有 临检登录  权限的用户列表
		List<User> list=SaveUserInfo.getQuarantineUserList();
		data.clear();
		if(null!=list&&list.size()>0){
			for(User user : list){
				data.add(user.getUserName());
			}
		}
		userNameComboBox.setItems(data);
		userNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					passwordText.requestFocus();
					label2.setText("");
				}
			}});
		userNameComboBox.getSelectionModel().select(SaveUserInfo.getUser()== null? "":SaveUserInfo.getUser().getUserName());
	}

	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		SignFrameWithReason.type = type;
	}
	public static void setPrompt(String prompt) {
		SignFrameWithReason.prompt = prompt;
	}
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		SignFrameWithReason.user = user;
	}
	public static String getReason() {
		return reason;
	}
	public static void setReason(String reason) {
		SignFrameWithReason.reason = reason;
	}

}
