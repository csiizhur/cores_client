package com.lanen.zero.main;

import com.lanen.base.BaseService;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {
			new BaseService();
		} catch (Exception e) {
			Alert2.create("连接服务器失败");
			e.printStackTrace(); 
			return;
		}
		LoginFrame loginFrame =new LoginFrame();
		try {
			loginFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			Alert.create("登陆窗口启动失败！");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
