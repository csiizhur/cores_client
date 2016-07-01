package com.lanen.view.tblsession;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lanen.view.main.Main;

public class ChooseStudyNoFrame extends Application implements Initializable {
	
	
	@FXML
	private Label label2;
	@FXML
	private ComboBox<String> studyNoComboBox;
	private static ObservableList<String> data = FXCollections.observableArrayList();
	private static String type="";
	private static String studyNo ="";
	private static ChooseStudyNoFrame instance =null;
	public static  ChooseStudyNoFrame getInstance(){
		return instance;
	}
	public ChooseStudyNoFrame(){
		ChooseStudyNoFrame.type="Cancel";
		ChooseStudyNoFrame.studyNo="";
	}

	@FXML
	private void onKeyAction(KeyEvent event){
		label2.setText("");
	}

	@FXML
	private void onOkBtnAction(ActionEvent event) {
		String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		if (null  == studyNo ||"".equals(studyNo) ) {
			label2.setText("请选择课题编号！");
			return;
		}
		ChooseStudyNoFrame.setStudyNo(studyNo);
		ChooseStudyNoFrame.setType("OK");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@FXML
	private void onTextAction(MouseEvent event){
		label2.setText("");
	}
	@FXML
	private void onCancelBtnAction(ActionEvent event){
		ChooseStudyNoFrame.setType("Cancel");
		ChooseStudyNoFrame.setStudyNo("");
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance= this;
		studyNoComboBox.setItems(data);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initOwner(Main.getInstance().getStage());
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root=FXMLLoader.load(getClass().getResource("ChooseStudyNo.fxml"));
		Scene scene = new Scene(root,335,208);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		String title = stage.getTitle();
		if(null ==title ||title.isEmpty()){
			stage.setTitle("选择课题编号");
		}
//		stage.show();
		stage.showAndWait();

	}
	public void updateData(List<String> list){
		data.clear();
		if(list != null && list.size()>0){
			
			for(String str :list){
				if(str == null ||str.isEmpty()){
					data.add("空");
				}else{
					data.add(str);
				}
			}
		}
	}

	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		ChooseStudyNoFrame.type = type;
	}

	public static String getStudyNo() {
		return studyNo.equals("空") ? "":studyNo;
	}

	public static void setStudyNo(String studyNo) {
		ChooseStudyNoFrame.studyNo = studyNo;
	}
	

}
