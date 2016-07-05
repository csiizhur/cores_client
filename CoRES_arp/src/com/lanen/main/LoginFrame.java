package com.lanen.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFrame extends Application {
	
	//密码录入错误次数
	public static int passwordErrorTimes = 0;
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//密码录入错误次数 为0
		LoginFrame.passwordErrorTimes=0;
		
		Parent root = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setTitle("系统登录");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();

	}

}
