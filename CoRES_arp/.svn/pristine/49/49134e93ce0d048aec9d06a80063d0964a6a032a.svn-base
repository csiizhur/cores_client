package com.lanen.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class About extends Application implements Initializable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initOwner(MainFrame.mainWidow);
		stage.setTitle("关于");
		stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);
		stage.show();
	}

}
