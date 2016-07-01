package com.lanen.view.main;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.apache.commons.codec.digest.DigestUtils;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

public class AlterPassword extends Application implements Initializable {


	@FXML
	private Label userNameLabel;
	@FXML
	private Label remarkLabel;
	@FXML
	private Label errorNewPasswordLabel;
	@FXML
	private Label errorPasswordLabel;
	@FXML
	private Label noSameLabel;
	@FXML
	private TextField passwordText;
	@FXML
	private TextField newPasswordText;
	@FXML
	private TextField repeatPasswordText;
	
	private static User user;
	private static String type="Cancel";
	private static String remark="";
	
	public AlterPassword(){}
	public AlterPassword(User user,String remark){
		AlterPassword.user=user;
		type="Cancel";
		AlterPassword.remark=remark;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remarkLabel.setText(remark);
		userNameLabel.setText(user.getUserName());
		errorPasswordLabel.setText("");
		errorNewPasswordLabel.setText("");
		noSameLabel.setText("");
	}

	/**
	 * 确定按钮事件
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		boolean empty=false;//输入不为空
		String password =passwordText.getText();
		if("".equals(password)){
			errorPasswordLabel.setText("请输入密码");
			empty=true;//输入不为空
		}
		String newPassword=newPasswordText.getText();
		if("".equals(newPassword)){
			errorNewPasswordLabel.setText("请输入新密码");
			empty=true;//输入不为空
		}else if(newPassword.length()>11 || newPassword.length()<6){
			errorNewPasswordLabel.setText("密码长度在6-11之间");
			empty=true;//输入不为空
		}
		String repeatPassword=repeatPasswordText.getText();
		if("".equals(repeatPassword)){
			noSameLabel.setText("请输入新密码");
			empty=true;//输入不为空
		}
		if(empty){//输入为空
			return;
		}
		if(!DigestUtils.md5Hex(password).equals(user.getPassword())){//密码不正确
			errorPasswordLabel.setText("密码不正确");
			return;
		}
		if(!newPassword.equals(repeatPassword)){//两次输入密码不相同
			noSameLabel.setText("两次输入密码不相同");
			return;
		}
		if(newPassword.equals(password)){//两次输入密码不相同
			errorNewPasswordLabel.setText("新旧密码不能相同");
			return;
		}
		user.setPassword(DigestUtils.md5Hex(newPassword));
//		user.setUpdatePasswordTime(new Date());
		user.setUpdatePasswordTime(BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate());
		BaseService.getInstance().getUserService().updatePwd(user, newPassword);
		AlterPassword.type="OK";
		
		List<User> list=BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_登录");
		//存放有   临检登录   权限 的用户列表
		SaveUserInfo.setPathUserList(list);
		
		Messager.showMessage("密码修改成功");
		//关闭窗口
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	@FXML
	private void onPasswordTestAction(MouseEvent event){
		errorPasswordLabel.setText("");
	}
	@FXML
	private void onNewPasswordTestAction(MouseEvent event){
		errorNewPasswordLabel.setText("");
	}
	@FXML
	private void onRepeatPasswordTestAction(MouseEvent event){
		noSameLabel.setText("");
	}
	
	
	/**
	 * 返回按钮事件
	 * @param event
	 */
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		//关闭窗口
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AlterPassword.fxml"));
		Scene scene = new Scene(root,337,208);
		stage.setScene(scene);
		stage.setTitle("密码修改");
		stage.setResizable(false);
		stage.showAndWait();
//		stage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	public static User getUser() {
		return user;
	}
	public static void setUser(User user) {
		AlterPassword.user = user;
	}
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		AlterPassword.type = type;
	}

}
