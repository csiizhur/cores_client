//package com.lanen.view.clinicaltest;
//
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//import com.lanen.base.BaseService;
//import com.lanen.model.User;
//import com.lanen.zero.main.MainFrame;
//
//public class SignFrame2 extends Application implements Initializable {
//	
//	
//	@FXML
//	private Label label1=new Label();
//	@FXML
//	private Label label2;
//	@FXML
//	private TextField userNameText;
//	@FXML
//	private PasswordField passwordText;
//	private static String type="";
//	private static  String prompt="";
//	private static User user;
//	public SignFrame2(){
//		SignFrame2.type="Cancel";
//		SignFrame2.user=null;
//	}
//	public SignFrame2(String prompt){
//		label1.setText(prompt);
//		SignFrame2.setPrompt(prompt);
//		SignFrame2.type="Cancel";
//		SignFrame2.user=null;
//	}
//
//	@FXML
//	private void onKeyAction(KeyEvent event){
//		label2.setText("");
//	}
//	
//	/**
//	 * 用户名文本框  
//	 */
//	@FXML
//	private void onUserNameText(){
//		if(userNameText.getText().trim().equals("")){
//			userNameText.requestFocus();
//		}else{
//			passwordText.requestFocus();
//		}
//	}
//	@FXML
//	private void onOkBtnAction(ActionEvent event) {
//		String userName = userNameText.getText().trim();
//		String password = passwordText.getText();
//		if (null  == userName ||"".equals(userName) ) {
//			label2.setText("请输入用户名");
//			userNameText.requestFocus();
//			return;
//		} 
//		if (null == password || password.equals("")) {
//			label2.setText("请输入密码");
//			passwordText.requestFocus();
//			return;
//		}
//		boolean exist = false;
//		
//		User user = BaseService.getUserService().findUserByUserNamePassword(userName, password);
//		if(null != user && user.getFlag().equals("可用")){
//			SignFrame2.setType("OK");
//			SignFrame2.setUser(user);
//			exist=true;
//			((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
//		}
//		if(!exist){
//			label2.setText("用户名或密码错误");
//		}
//
//	}
//	@FXML
//	private void onTextAction(MouseEvent event){
//		label2.setText("");
//	}
//	@FXML
//	private void onCancelBtnAction(ActionEvent event){
//		SignFrame2.setType("Cancel");
//		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
//	}
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		label1.setText(prompt);
////		label2.setText("请输入你的密码");
////		passwordText.requestFocus();
//	}
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		Application.launch(args);
//		
//	}
//	@Override
//	public void start(Stage stage) throws Exception {
//		stage.initOwner(MainFrame.mainWidow);
//		Parent root=FXMLLoader.load(getClass().getResource("SignFrame2.fxml"));
//		Scene scene = new Scene(root,335,208);
//		stage.setScene(scene);
//		stage.setResizable(false);
//		stage.initStyle(StageStyle.UTILITY);
////		stage.show();
//		stage.showAndWait();
//
//	}
//	
//	public static String getType() {
//		return type;
//	}
//	public static void setType(String type) {
//		SignFrame2.type = type;
//	}
//	public static void setPrompt(String prompt) {
//		SignFrame2.prompt = prompt;
//	}
//	public static User getUser() {
//		return user;
//	}
//	public static void setUser(User user) {
//		SignFrame2.user = user;
//	}
//
//}
