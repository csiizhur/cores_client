package com.lanen.main;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.util.messager.Messager;

public class Main extends Application {

	public static Map<String,Stage> stageMap = new HashMap<String,Stage>();
	@Override
	public void start(Stage stage) {
		new BaseService();
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		LoginFrame loginFrame =new LoginFrame();
		try {
			loginFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			Messager.showWarnMessage("登陆窗口启动失败！");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
