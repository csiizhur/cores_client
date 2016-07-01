package com.lanen.view.test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.util.messager.Messager;

/**复查意见
 * @author Administrator
 *
 */
public class HistopathReviewOpinion extends Application implements Initializable {
	
	@FXML
	private Label animalCodeLabel;
	@FXML
	private Label visceraOrTissueNameLabel;
	@FXML
	private TextArea opinionTextArea;
	
	private String id;//组织病理学检查记录 id
	
	private static HistopathReviewOpinion instance;
	public static HistopathReviewOpinion getInstance(){
		if(null == instance){
			instance = new HistopathReviewOpinion();
		}
		return instance;
	}
	
	public HistopathReviewOpinion() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
	}
	
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String opinion = opinionTextArea.getText().trim();
		if(null == opinion || "".equals(opinion)){
			showErrorMessage("请输入复查意见！");
			opinionTextArea.requestFocus();
			return;
		}
		if(opinion.getBytes().length>200){
			showErrorMessage("复查意见长度不能大于200！");
			opinionTextArea.requestFocus();
			return;
		}
		
		BaseService.getInstance().getTblHistopathCheckService().reviewOpinion(id,opinion);
		
		HistopathReview.getInstance().updateHistopathCheckTable();
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**同意
	 * @param event
	 */
	@FXML
	private void onAgreeAction(ActionEvent event){
		opinionTextArea.setText("同意");
		opinionTextArea.requestFocus();
	}
	/**
	 * 加载数据
	 */
	public void loadData(String id,String animalCode,String visceraOrTissueName,String opinion) {
		this.id = id;
		animalCodeLabel.setText(animalCode);
		visceraOrTissueNameLabel.setText(visceraOrTissueName);
		opinionTextArea.setText(opinion);
		opinionTextArea.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathReviewOpinion.fxml"));
		Scene scene = new Scene(root, 440, 267);
		stage.setScene(scene);
		stage.setTitle("复查意见");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

}
