package com.lanen.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserFrame extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("UserFrame.fxml"));
		Scene scene =new Scene(root,584,465);
        stage.setScene(scene);
        stage.setTitle("用户管理");
        stage.setResizable(false);
        stage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
