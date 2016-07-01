package com.lanen.view.main;


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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;

public class SignMeFrame extends Application implements Initializable {
	
	
	@FXML
	private Label label1=new Label();
	@FXML
	private Label label2;
	@FXML
	private TextField userNameText;
//	private ComboBox<String> userNameComboBox;
//	private ObservableList<String> data = FXCollections.observableArrayList();
	@FXML
	private PasswordField passwordText;
	private static String type="";
	private static  String prompt="";
	public SignMeFrame(){
		SignMeFrame.type="Cancel";
	}
	
	public SignMeFrame(String prompt){
		label1.setText(prompt);
		SignMeFrame.setPrompt(prompt);
		SignMeFrame.type="Cancel";
	}

	@FXML
	private void onKeyAction(KeyEvent event){
		label2.setText("");
		passwordText.requestFocus();
	}

	@FXML
	private void onOkBtnAction(ActionEvent event) {
		String password = passwordText.getText();
		if (null == password || password.equals("")) {
			label2.setText("请输入密码");
			passwordText.requestFocus();
			return;
		}
		boolean exist = false;
		User user = SaveUserInfo.getUser();
		if(null != user && user.getPassword().equals(DigestUtils.md5Hex(password))){
			SignMeFrame.setType("OK");
			exist=true;
			((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
		}
		if(!exist){
			label2.setText("密码错误！");
		}

	}
	@FXML
	private void onTextAction(MouseEvent event){
		label2.setText("");
	}
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		SignMeFrame.setType("Cancel");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label1.setText(prompt);
		label2.setText("请输入你的密码");
		//初始化用户名 text
		initUserNameText();
		passwordText.requestFocus();
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
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root=FXMLLoader.load(getClass().getResource("SignMeFrame.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		String title = stage.getTitle();
		if(null ==title ||title.isEmpty()){
			stage.setTitle("电子签名");
		}
//		stage.show();
		stage.showAndWait();

	}
	
	/**
	 * 初始化用户名  text
	 */
	private void initUserNameText() {
		userNameText.setText(SaveUserInfo.getUserName());
	}

	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		SignMeFrame.type = type;
	}
	public static void setPrompt(String prompt) {
		SignMeFrame.prompt = prompt;
	}

}
