//package com.lanen.util.popup;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.stage.Window;
//
//public class Confirm2 extends Application implements Initializable {
//	
//	@FXML
//	private Label label1;
//	private static boolean result;
//	private static String message="";
//	public static boolean create(Window mainWindow,String msg){
//		message=msg;
//		Stage stage=new Stage();
//		stage.initOwner(mainWindow);
//		stage.initModality(Modality.APPLICATION_MODAL);
//		try {
//			new Confirm2().start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	/**
//	 * 确定
//	 * @param event
//	 */
//	@FXML
//	private void onOkBtnAction(ActionEvent event){
//		result=true;
//		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//	}
//	/**
//	 * 取消
//	 * @param event
//	 */
//	@FXML
//	private void onCancelBtnAction(ActionEvent event){
//		result=false;
//		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//	}
//	
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		label1.setText(message);
//
//	}
//
//	@Override
//	public  void start(final Stage stage) throws Exception {
//		result=false;
//		Parent root =FXMLLoader.load(getClass().getResource("Comfirm2.fxml"));
//		stage.addEventFilter(KeyEvent.KEY_PRESSED, (new EventHandler<KeyEvent>(){
//		    @Override
//		    public void handle(KeyEvent event) {
//		     if(event.getCode()==KeyCode.Y){
//		    	 result=true;
//		    	 stage.close();
//		     }else if(event.getCode()==KeyCode.N){
//		    	 result=false;
//		    	 stage.close();
//		     }
//		    }
//		   }));
//		stage.setScene(new Scene(root,288,85));
//		stage.initStyle(StageStyle.DECORATED);
////		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("提示");
//		stage.setResizable(false);
//		stage.setIconified(false);
//		
////		stage.show();
//		stage.showAndWait();
//
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
