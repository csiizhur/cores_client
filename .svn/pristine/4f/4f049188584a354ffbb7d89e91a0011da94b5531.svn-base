package com.lanen.zero.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginFrame extends Application {
	
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setTitle("系统登录");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		stage.setResizable(false);
		stage.sizeToScene();
//		stage.initStyle(StageStyle.UTILITY);//仅显示一个关闭按钮
		stage.show();

	}

}
