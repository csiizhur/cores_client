//package com.lanen.view.clinicaltest;
//
//import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//
//import com.lanen.util.messager.Messager;
//
//public class DataAcceptFrame extends Application {
//	
//	public static boolean isAdd = false;//增加了数据
//	public DataAcceptFrame(){
//		isAdd =false;
//	}
//	@Override
//	public void start(Stage stage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("DataAcceptFrame.fxml"));
//		Scene scene = new Scene(root,780,565);
//		stage.setScene(scene);
//		stage.setTitle("数据接收");
////		stage.setResizable(false);
//		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
//		stage.setMinWidth(760);
//		stage.setMinHeight(545);
//		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
//
//			@Override
//			public void handle(WindowEvent event) {
//				if(DataAcceptFrameController.isUsed){
////					if(Confirm.create(MainFrame.mainWidow,"提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？")){
//					if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？")){
//						DataAcceptFrameController.serialPort.removeEventListener();
//						DataAcceptFrameController.serialPort.notifyOnDataAvailable(false); 
//						DataAcceptFrameController.serialPort.close();
//						DataAcceptFrameController.isUsed =false;
//					}else{
//						event.consume();
//					}
//					
//				}
//			}});
//		stage.showAndWait();
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//}
