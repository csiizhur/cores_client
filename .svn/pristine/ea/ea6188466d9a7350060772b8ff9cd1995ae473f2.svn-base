package com.lanen.view.clinicaltest;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.lanen.base.BaseService;
import com.lanen.util.messager.Messager;
import com.lanen.zero.main.MainFrame;

public class ChooseSpecKindFrame extends Application implements Initializable {
	
	
	@FXML
	private ComboBox<String> specimenKindComboBox1;
	//血液生化、血液常规、血凝  共用一个数据集
	private static ObservableList<String>  data_specimenKindComboBox1 = 
			FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> specimenKindComboBox2;
	@FXML
	private ComboBox<String> specimenKindComboBox3;
	@FXML
	private ComboBox<String> specimenKindComboBox4;
	private static ObservableList<String>  data_specimenKindComboBox4 = 
			FXCollections.observableArrayList();
	//    1:标本类型
	private Map<Integer,String> specimenKindMap = new HashMap<Integer,String>();
	
	private boolean isPass = false;
	private List<Integer> testItemList = null;
	private static ChooseSpecKindFrame instance;
	public static ChooseSpecKindFrame getInstance(){
		if(null == instance)
			instance = new ChooseSpecKindFrame();
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initComboBox();
		
	}
	/**初始化四个ComboBox  并加载数据**/
	private void initComboBox() {
		specimenKindComboBox1.setItems(data_specimenKindComboBox1);
		specimenKindComboBox2.setItems(data_specimenKindComboBox1);
		specimenKindComboBox3.setItems(data_specimenKindComboBox1);
		specimenKindComboBox4.setItems(data_specimenKindComboBox4);
		
	}
	
	/**
	 * 更新ComboBox 数据的状态
	 */
	private void updateComboBox(){
		//加载数据1
		data_specimenKindComboBox1.clear();
		List<String> bloodSpecimenKindList = BaseService.getDictSpecimenService().getBloodSpecimenKindList();
		if(null != bloodSpecimenKindList){
			for(String str :bloodSpecimenKindList){
				data_specimenKindComboBox1.add(str);
			}
		}
		//加载数据4
		data_specimenKindComboBox4.clear();
		List<String> urineSpecimenKindList = BaseService.getDictSpecimenService().getUrineSpecimenKindList();
		if(null != urineSpecimenKindList){
			for(String str :urineSpecimenKindList){
				data_specimenKindComboBox4.add(str);
			}
		}
		//这定哪些CheckboBox 可用
		if (testItemList.contains(1)) {
			specimenKindComboBox1.setDisable(false);
		} else {
			specimenKindComboBox1.setDisable(true);
		}
		if (testItemList.contains(2)) {
			specimenKindComboBox2.setDisable(false);
		} else {
			specimenKindComboBox2.setDisable(true);
		}
		if (testItemList.contains(3)) {
			specimenKindComboBox3.setDisable(false);
		} else {
			specimenKindComboBox3.setDisable(true);
		}
		if (testItemList.contains(4)) {
			specimenKindComboBox4.setDisable(false);
		} else {
			specimenKindComboBox4.setDisable(true);
		}
	}
	/**
	 * 确定
	 */
	@FXML
	private void onConfirmButton(ActionEvent event ){
		specimenKindMap.clear();
		if (testItemList.contains(1)) {
			String specimenKind = specimenKindComboBox1.getSelectionModel().getSelectedItem();
			if(null == specimenKind){
//				Alert2.create("请先选择’血液生化检查‘标本类型");
				Messager.showWarnMessage("请先选择’血液生化检查‘标本类型！");
				specimenKindComboBox1.requestFocus();
				return ;
			}
			specimenKindMap.put(1, specimenKind);
		} 
		if (testItemList.contains(2)) {
			String specimenKind = specimenKindComboBox2.getSelectionModel().getSelectedItem();
			if(null == specimenKind){
//				Alert2.create("请先选择’血液常规检查‘标本类型");
				Messager.showWarnMessage("请先选择’血液常规检查‘标本类型！");
				specimenKindComboBox2.requestFocus();
				return ;
			}
			specimenKindMap.put(2, specimenKind);
		} 
		if (testItemList.contains(3)) {
			String specimenKind = specimenKindComboBox3.getSelectionModel().getSelectedItem();
			if(null == specimenKind){
//				Alert2.create("请先选择’凝血功能检查‘标本类型");
				Messager.showWarnMessage("请先选择’凝血功能检查‘标本类型！");
				specimenKindComboBox3.requestFocus();
				return ;
			}
			specimenKindMap.put(3, specimenKind);
		}
		if (testItemList.contains(4)) {
			String specimenKind = specimenKindComboBox4.getSelectionModel().getSelectedItem();
			if(null == specimenKind){
//				Alert2.create("请先选择’尿液常规检查‘标本类型");
				Messager.showWarnMessage("请先选择’尿液常规检查‘标本类型！");
				specimenKindComboBox4.requestFocus();
				return ;
			}
			specimenKindMap.put(4, specimenKind);
		}
		isPass = true;
		Button button = (Button) event.getSource();
		button.getScene().getWindow().hide();
	}
	/**
	 * 取消
	 */
	@FXML
	private void onCancelButton(ActionEvent event ){
		isPass = false;
		Button button = (Button) event.getSource();
		button.getScene().getWindow().hide();
	}

	public void loadData(List<Integer> list) {
		isPass = false;
		testItemList = list;
		updateComboBox();
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ChooseSpecKind.fxml"));
		stage.initOwner(MainFrame.mainWidow);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setMaxWidth(333);
		stage.setMaxHeight(242);
		stage.setMinWidth(333);
		stage.setMinHeight(242);
		stage.setTitle("选择标本类型");
//		stage.show();
//		stage.showAndWait();
	}
	public static void main(String[] args) {
		launch(args);
	}
	public Map<Integer, String> getSpecimenKindMap() {
		return specimenKindMap;
	}
	public boolean isPass() {
		return isPass;
	}
	

}
