package com.lanen.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserRoleLogFrame extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root =  FXMLLoader.load(getClass().getResource("UserRoleLogFrame.fxml"));
		Scene scene = new Scene (root);
		stage.setScene(scene);
		stage.setTitle("授权变更日志");
        stage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}
