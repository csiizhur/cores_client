package com.lanen.view;


import java.net.URL;
import java.util.ResourceBundle;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StopUserFrame extends Application implements Initializable {
	
	
	@FXML
	private TextArea text;
	@FXML
	private Button okBtn=new Button();
	private static String type="";
	private static String remark="";
	
	public StopUserFrame(){
		StopUserFrame.type="Cancel";
		remark="";
	}


	@FXML
	private void onOkBtnAction(ActionEvent event){
		remark=text.getText();
		if("".equals(text.getText())){
			text.requestFocus();
		}else{
			StopUserFrame.setType("OK");
			((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
		}
	}
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		StopUserFrame.setType("Cancel");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("StopUserFrame.fxml"));
		Scene scene = new Scene(root,290,145);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("账户停用");
		stage.initStyle(StageStyle.UTILITY);
//		stage.show();
		stage.showAndWait();

	}
	
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		StopUserFrame.type = type;
	}


	public static String getRemark() {
		return remark;
	}


	public static void setRemark(String remark) {
		StopUserFrame.remark = remark;
	}
	


}
