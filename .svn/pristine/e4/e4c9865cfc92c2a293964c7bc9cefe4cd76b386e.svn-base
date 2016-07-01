package com.lanen.view.test;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.messager.Messager;

/**选择或输入专题编号
 * @author Administrator
 *
 */
public class SelectStudyNoPage extends Application implements Initializable {
	
	
	@FXML
	private ComboBox<String> studyNoComboBox;
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	
	@FXML
	private Button confirmBtn;
	
	/**
	 * 专题编号
	 */
	private String studyNo = null;
	
	private static SelectStudyNoPage instance;
	public static SelectStudyNoPage getInstance(){
		if(null == instance){
			instance = new SelectStudyNoPage();
		}
		return instance;
	}

	public SelectStudyNoPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initStudyNoComboBox();
	}

	
	/**
	 * 初始化 studyNoComboBox
	 */
	private void initStudyNoComboBox() {
		studyNoComboBox.setItems(data_studyNoComboBox);
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
		this.studyNo = null;
		javafx.scene.control.Control source =  (Control) event.getSource();
		source.requestFocus();
		
		String currentStudyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		if(null != currentStudyNo && !"".equals(currentStudyNo.trim())){
			TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(currentStudyNo);
			if(null != studyPlan ){
				this.studyNo = currentStudyNo;
				source.getScene().getWindow().hide(); 
			}else{
				showErrorMessage("对应专题不存在，请检查专题编号！");
			}
		}else{
			showErrorMessage("请选择或输入专题编号！");
		}
	}
	/**
	 * @param event
	 */
	@FXML
	private void onComboBoxAction(ActionEvent event){
		this.studyNo = null;
		confirmBtn.requestFocus();
		
		String currentStudyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		if(null != currentStudyNo && !"".equals(currentStudyNo.trim())){
			TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(currentStudyNo);
			if(null != studyPlan ){
				this.studyNo = currentStudyNo;
				javafx.scene.control.Control source =  (Control) event.getSource();
				source.getScene().getWindow().hide(); 
			}else{
				showErrorMessage("对应专题不存在，请检查专题编号！");
			}
		}else{
			showErrorMessage("请选择或输入专题编号！");
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		this.studyNo = null;
		Button source = (Button) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(List<String> studyNoList ,String studyNo) {
		this.studyNo = null;
		
		updateStudyNoComboBox(studyNoList,studyNo);
	}


	/**更新studyNoComboBox
	 * @param studyNoList
	 * @param studyNo2
	 */
	private void updateStudyNoComboBox(List<String> studyNoList, String studyNo2) {
		data_studyNoComboBox.clear();
		if(null != studyNoList && studyNoList.size() > 0){
			for(String studyNo :studyNoList){
				data_studyNoComboBox.add(studyNo);
			}
			if(null != studyNo2 && !"".equals(studyNo2)){
				studyNoComboBox.getSelectionModel().select(studyNo2);
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("SelectStudyNoPage.fxml"));
		Scene scene = new Scene(root, 503, 328);
		stage.setScene(scene);
		stage.setTitle("专题编号");
		stage.setResizable(false);
		stage.setMinWidth(503);
		stage.setMinHeight(328);
//		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//
//			@Override
//			public void handle(WindowEvent event) {
//					//event.consume();
//			}
//		});
//		stage.showAndWait();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

	public String getStudyNo() {
		return studyNo;
	}

	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}

}
