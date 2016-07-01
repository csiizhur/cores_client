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
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.text.Text;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//import com.lanen.view.main.Main;
//
//public class Alert2 extends Application implements Initializable {
//	
////	@FXML
////	private Label label1;
//	@FXML
//	private Text text1;
//	private static String message="";
//	public static void create(String msg){
//		message=msg;
//		Stage stage=new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		try {
//			new Alert2().start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 *确定按钮
//	 * @param event
//	 */
//	@FXML
//	private void onOkBtnAction(ActionEvent event){
//		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//	}
//	
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		text1.setText(message);
//	}
//
//	@Override
//	public void start(final Stage stage) throws Exception {
//		Parent root =FXMLLoader.load(getClass().getResource("Alert2.fxml"));
//		stage.addEventFilter(KeyEvent.KEY_PRESSED, (new EventHandler<KeyEvent>(){
//		    @Override
//		    public void handle(KeyEvent event) {
//		     if(event.getCode()==KeyCode.O){
//		    	 stage.close();
//		     }
//		    }
//		   }));
//		stage.setScene(new Scene(root));
//		stage.initOwner(Main.mainWidow);
//		stage.initStyle(StageStyle.UTILITY);
////		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("提示");
//		stage.setResizable(false);
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
