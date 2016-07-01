package com.lanen.view;


import com.lanen.util.popup.Confirm2;
import com.lanen.zero.main.MainFrame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegulationSetFrame extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("RegulationSetFrame.fxml"));
		
		Scene scene = new Scene(root,505,338);
		stage.setScene(scene);
		stage.setTitle("系统规则设置");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if(RegulationSetFrameController.isEdited==1){
					if(Confirm2.create(MainFrame.mainWindow,"你确定要放弃刚刚的修改吗？")){
						return ;
					}else{
						event.consume();
					}
				}else{
					return ;
				}
			}});
		stage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);

	}

}
