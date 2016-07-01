package com.lanen.zero.main;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.util.SystemMessage;
import com.lanen.util.messager.Messager;

public class Main extends Application {

	public static Map<String,Stage> stageMap = new HashMap<String,Stage>();
	@Override
	public void start(Stage stage) {
		new BaseService();
		boolean isValid = BaseService.getVersionControlService()
		.isValidVersion(SystemMessage.getSystemName(), SystemMessage.getSystemVersion());	
		if(!isValid){
//			Alert2.create("该版本已过期，请升级");
			Messager.showWarnMessage("该版本已过期，请升级！");
			return ;
		}
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		LoginFrame loginFrame =new LoginFrame();
		try {
			loginFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
//			Alert2.create("登陆窗口启动失败！");
			Messager.showWarnMessage("登陆窗口启动失败！");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
