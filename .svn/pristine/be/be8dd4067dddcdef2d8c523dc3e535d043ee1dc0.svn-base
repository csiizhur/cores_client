package com.lanen.util.popup;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



public class test extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
		

	}

	@Override
	public void start(Stage stage) throws Exception {
		 Group root = new Group();
		 HBox box=new HBox();
		 stage.setResizable(false);
		 stage.setScene(new Scene(root, 250,150));
		 Button button =new Button("Comfirm2");
		 Button button1 =new Button("Alert");
		 Button button11 =new Button("Alert2");
		 Button button2 =new Button("Comfirm");
		 box.getChildren().addAll(button,button1,button2,button11);
		 root.getChildren().add(box);
		 button.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println(Confirm2.create(null,"是否保存对\"hhhh\"的更改？"));
			}});
		 button2.setOnAction(new EventHandler<ActionEvent>(){
			 
			 @Override
			 public void handle(ActionEvent arg0) {
				System.out.println(Confirm.create(null,"保存修改","如果改变文件扩展名，可能会导致文件不可用","确定要更改吗？"));
			 }});
		 button1.setOnAction(new EventHandler<ActionEvent>(){
			 
			 @Override
			 public void handle(ActionEvent arg0) {
				 Alert.create("保存成功保存成功保存成功保存成功保存成功保存成功！");
			 }});
		 button11.setOnAction(new EventHandler<ActionEvent>(){
			 
			 @Override
			 public void handle(ActionEvent arg0) {
				 Alert2.create("保存成功保存成功保存成功保存成功保存成功保存成功保存成功！");
			 }});
		 stage.show();
		
	}

}
