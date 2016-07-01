package com.lanen.zero.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import com.lanen.util.popup.Confirm2;

public class MainFrame extends Application {
	
	public static Window mainWindow = null;
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException{
		mainWindow = stage;
		Parent root=FXMLLoader.load(getClass().getResource("MainFrame.fxml"));;
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("授权管理系统");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
//		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
					if(Confirm2.create(MainFrame.mainWindow,"确定要退出系统吗？")){
						return ;
					}else{
						event.consume();
					}
			}
		});
		stage.show();
		

	}

}
