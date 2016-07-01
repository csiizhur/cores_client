package com.lanen.view.clinicaltest;

import java.net.URL;
import java.util.ResourceBundle;

import com.lanen.base.BaseService;
import com.lanen.model.studyplan.TblStudyPlan;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChooseTempNoFrame extends Application implements Initializable {

	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private RadioButton surfaceRadioButton ;
	@FXML
	private RadioButton innerRadioButton ;
	@FXML
	private Label studyNoLabel;
	@FXML
	private Label errorMsgLabel;
	@FXML
	private TextField studyNoText;
	public static String type ="cancel";
	public  static boolean isInner = true;
	public static String studyNo ="";
	
	public ChooseTempNoFrame(){
		 type ="cancel";
		isInner = true;
		studyNo = "";
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}


	/** 外部临时任务
	 * @param event
	 */
	@FXML
	private void onSurfaceButton(ActionEvent event ){
		isInner = false;
		errorMsgLabel.setText("");
		studyNoLabel.setVisible(false);
		studyNoText.setVisible(false);
	}
	/** 内部临时任务
	 * @param event
	 */
	@FXML
	private void onInnerButton(ActionEvent event ){
		isInner = true;
		errorMsgLabel.setText("");
		studyNoLabel.setVisible(true);
		studyNoText.setVisible(true);
		studyNoText.requestFocus();
		
	}
	/** 专题编号文本框
	 * @param event
	 */
	@FXML
	private void onText(){
		errorMsgLabel.setText("");
	}
	/** 确定
	 * @param event
	 */
	@FXML
	private void onConfirmButton(ActionEvent event ){
		//内部临时任务
		if(isInner){
			studyNo = studyNoText.getText().trim();
			if(null ==studyNo || studyNo.isEmpty()){
				errorMsgLabel.setText("请输入专题编号");
				studyNoText.requestFocus();
				return;
			}
			TblStudyPlan tblStudyPlan = BaseService.getTblStudyPlanService().getById(studyNo);
			if(null == tblStudyPlan || tblStudyPlan.getTemp() == 1){
				errorMsgLabel.setText("专题编号不存在");
				studyNoText.requestFocus();
				return ;
			}
		}
		type ="OK";
		javafx.scene.Node node =(javafx.scene.Node) event.getSource();
		node.getScene().getWindow().hide();
	}
	/** 取消
	 * @param event
	 */
	@FXML
	private void onCancelButton(ActionEvent event ){
		type ="cancel";
		javafx.scene.control.Control control =(Control) event.getSource();
		control.getScene().getWindow().hide();
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ChooseTempNo.fxml"));
		Scene scene = new Scene(root,241,135);
		stage.setScene(scene);
		stage.setTitle("选择");
		stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);
		stage.showAndWait();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
