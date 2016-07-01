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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.util.messager.Messager;

/**组织取材_选择页（常规非常规 or 追加）
 * @author Administrator
 *
 */
public class TissueSliceBatch_select extends Application implements Initializable {
	
	@FXML
	private Button confirmBtn;
	
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	
	/**
	 * 专题编号
	 */
	private boolean isConfirm = false;
	
	/**
	 * 选择类型   0：没选    1：常规组织取材、非常规组织取材   2：追加组织取材
	 */
	private int selectType = 0;
	
	private static TissueSliceBatch_select instance;
	public static TissueSliceBatch_select getInstance(){
		if(null == instance){
			instance = new TissueSliceBatch_select();
		}
		return instance;
	}

	public TissueSliceBatch_select() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
	}

	@FXML
	private void OnRadio1Action(ActionEvent event){
		selectType = 1;
		confirmBtn.setDisable(false);
	}
	@FXML
	private void OnRadio2Action(ActionEvent event){
		selectType = 2;
		confirmBtn.setDisable(false);
	}
	
	/**常规取材编号
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
		if(selectType > 0){
			isConfirm = true;
			Button source = (Button) event.getSource();
			source.getScene().getWindow().hide(); 
		}
	}
	/**非常规组织取材编号
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		isConfirm = false;
		Button source = (Button) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData() {
		isConfirm = false;
		selectType = 0;
		confirmBtn.setDisable(true);
		//重置 RadioButton
		resetRadioButton();
	}

	/**
	 * 重置 RadioButton
	 */
	private void resetRadioButton() {
		radio1.setSelected(false);
		radio2.setSelected(false);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceBatch_select.fxml"));
		Scene scene = new Scene(root, 503, 328);
		stage.setScene(scene);
		stage.setTitle("新建组织取材");
		stage.setResizable(false);
		stage.setMinWidth(503);
		stage.setMinHeight(328);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
//		stage.showAndWait();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}
	
}
