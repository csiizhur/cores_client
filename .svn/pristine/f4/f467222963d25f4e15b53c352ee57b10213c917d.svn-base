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
//public class Confirm extends Application implements Initializable {
//	
//	@FXML
//	private Label label1;
//	@FXML
//	private Label label2;
//	private static boolean result;
//	private static String biaoti="";
//	private static String message1="";
//	private static String message2="";
//	public static boolean create(Window mainWindow,String title,String msg1,String msg2){
//		biaoti=title;
//		message1=msg1;
//		message2=msg2;
//		Stage stage=new Stage();
//		stage.initOwner(mainWindow);
//		stage.initModality(Modality.APPLICATION_MODAL);
//		try {
//			new Confirm().start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	/**
//	 *确定
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
//		label1.setText(message1);
//		label2.setText(message2);
//	}
//
//	@Override
//	public void start(final Stage stage) throws Exception {
//		result=false;
//		Parent root =FXMLLoader.load(getClass().getResource("Comfirm.fxml"));
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
//		stage.setScene(new Scene(root,339,113));
//		stage.initStyle(StageStyle.DECORATED);
////		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle(biaoti);
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
