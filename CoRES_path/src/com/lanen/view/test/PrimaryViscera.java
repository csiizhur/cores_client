package com.lanen.view.test;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.util.messager.Messager;

/**源发脏器肿瘤
 * @author Administrator
 *
 */
public class PrimaryViscera extends Application implements Initializable {
	@FXML
	private TableColumn<VisceraTumor,String> visceraOrTissueName;
	@FXML
	private TableColumn<VisceraTumor,String> checkResult;
	
	@FXML
	private TableView<VisceraTumor> table;
	ObservableList<VisceraTumor> data_table = FXCollections.observableArrayList();

	private String studyNo = null;
	private String animalCode = null;
	private String visceraCode = null;
	//选择数据后放到哪里
	private String toWhere = null;
	
	private static PrimaryViscera instance;
	public static PrimaryViscera getInstance(){
		if(null == instance){
			instance = new PrimaryViscera();
		}
		return instance;
	}
	
	public PrimaryViscera() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTable();
	}
	
	private void initTable() {
		table.setItems(data_table);
		visceraOrTissueName.setCellValueFactory(new PropertyValueFactory<VisceraTumor,String>("visceraOrTissueName"));
		checkResult.setCellValueFactory(new PropertyValueFactory<VisceraTumor,String>("checkResult"));
		
		table.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					VisceraTumor selectedItem = table.getSelectionModel().getSelectedItem();
					if(null != selectedItem){
						//TODO 
						if("HistopathCheckPage".equals(toWhere)){
							HistopathCheckPage.getInstance().setPrimaryViscerTumor(selectedItem.getVisceraCode(),
									selectedItem.getVisceraName(), selectedItem.getCheckResult());
						}else if("HistopathCheckEdit".equals(toWhere)){
							HistopathCheckEdit.getInstance().setPrimaryViscerTumor(selectedItem.getVisceraCode(),
									selectedItem.getVisceraName(), selectedItem.getCheckResult());
						}
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					}
				}
				
			}});
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		VisceraTumor selectedItem = table.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			//TODO 
			if("HistopathCheckPage".equals(toWhere)){
				HistopathCheckPage.getInstance().setPrimaryViscerTumor(selectedItem.getVisceraCode(),
						selectedItem.getVisceraName(), selectedItem.getCheckResult());
			}else if("HistopathCheckEdit".equals(toWhere)){
				HistopathCheckEdit.getInstance().setPrimaryViscerTumor(selectedItem.getVisceraCode(),
						selectedItem.getVisceraName(), selectedItem.getCheckResult());
			}
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}else{
			showErrorMessage("请先选择原发脏器肿瘤！");
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String animalCode,String visceraCode,String toWhere) {
		this.studyNo  = studyNo;
		this.animalCode  = animalCode;
		this.visceraCode  = visceraCode;
		
		this.toWhere  = toWhere;
		
		updateTaskTable();
	}

	
	private void updateTaskTable() {
		
		data_table.clear();
		List<Map<String,Object>> maplist = BaseService.getInstance().getTblHistopathCheckService().getMapListByStudyNoAnimalCodeVisceraCode(studyNo,animalCode,visceraCode);
		if(null != maplist && maplist.size() > 0){
			for(Map<String, Object> obj:maplist){
				String visceraCode = (String) obj.get("visceraCode");
				String visceraName = (String) obj.get("visceraName");
				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				String histoPos = (String) obj.get("histoPos");
				String lesionFinding = (String) obj.get("lesionFinding");
				data_table.add(new VisceraTumor(visceraCode,visceraName,visceraOrTissueName,histoPos,lesionFinding));
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("PrimaryViscera.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("原发脏器/肿瘤");
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
	
	/**源发脏器肿瘤
	 * @author Administrator
	 *
	 */
	public class VisceraTumor{
		
		private StringProperty visceraCode;
		private StringProperty visceraName;
		private StringProperty visceraOrTissueName;
		private StringProperty checkResult;
		
		public VisceraTumor(String visceraCode,String visceraName,String visceraOrTissueName,String histoPos,String lesionFinding){
			setVisceraCode(visceraCode);
			setVisceraName(visceraName);
			setVisceraOrTissueName(visceraOrTissueName);
			String checkResult = "";
			if(null != histoPos && !"".equals(histoPos)){
				checkResult = histoPos+" "+lesionFinding;
			}else{
				checkResult = lesionFinding;
			}
			setCheckResult(checkResult);
		}

		public String getVisceraCode() {
			return visceraCode.get();
		}

		public void setVisceraCode(String visceraCode) {
			this.visceraCode = new SimpleStringProperty(visceraCode);
		}

		public String getVisceraName() {
			return visceraName.get();
		}

		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}

		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}

		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}

		public String getCheckResult() {
			return checkResult.get();
		}

		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}
		
		
		
	}

}
