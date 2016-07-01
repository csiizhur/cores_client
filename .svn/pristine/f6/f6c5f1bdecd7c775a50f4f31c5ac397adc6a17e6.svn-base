package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.util.messager.Messager;

/**组织取材_排除原因
 * @author Administrator
 *
 */
public class TissueSliceBatch_reason extends Application implements Initializable {
	
	
	@FXML
	private TextField reasonText;
	
	private boolean isConfirm = false;
	
	/**
	 * 脏器名称ListView
	 */
	@FXML
	private ListView<String> visceraNameListView;
	ObservableList<String> data_visceraNameListView = FXCollections.observableArrayList();
	List<Map<String,Object>> visceraMapList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 选择的脏器（map:visceraType，visceraCode，visceraName，subVisceraCode，subVisceraName）
	 */
	private Map<String,Object> visceraMap = new HashMap<String,Object>();
	/**
	 * 排除原因
	 */
	private String reason = "";
	
	private static TissueSliceBatch_reason instance;
	public static TissueSliceBatch_reason getInstance(){
		if(null == instance){
			instance = new TissueSliceBatch_reason();
		}
		return instance;
	}

	public TissueSliceBatch_reason() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initVisceraNameListView();
	}

	
	/**
	 * 初始化 visceraNameListView
	 */
	private void initVisceraNameListView() {
		visceraNameListView.setItems(data_visceraNameListView);
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
		visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
		
		String visceraName = visceraNameListView.getSelectionModel().getSelectedItem();
		boolean isexist = TissueSliceBatch_add.getInstance().isExistInBatchExcluded(visceraName);
		if(isexist){
			return ;
		}
		
		reason = reasonText.getText().trim();
		if(null == reason || "".equals(reason)){
			showErrorMessage("请填写排除原因！");
			reasonText.requestFocus();
			return ;
		}
		if(reason.getBytes().length > 40){
			showErrorMessage("排除原因长度不能大于40！");
			reasonText.requestFocus();
			return ;
		}
		
		isConfirm = true;
		javafx.scene.control.Control source =  (Control) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		isConfirm = false;
		reason = "";
		Button source = (Button) event.getSource();
		source.getScene().getWindow().hide(); 
	}
	/**
	 * 加载数据
	 * @param visceraName 
	 * @param tissueSliceVisceraId 
	 * @param taskId 
	 */
	public void loadData(String tissueSliceVisceraId, String visceraName) {
		isConfirm = false;
		visceraMap.clear();
		reason = "";
		reasonText.clear();
		reasonText.requestFocus();
		updateVisceraNameListView(tissueSliceVisceraId,visceraName);
	}


	/**更新脏器列表
	 * @param tissueSliceVisceraId
	 * @param visceraName
	 */
	private void updateVisceraNameListView(String tissueSliceVisceraId, String visceraName) {
		data_visceraNameListView.clear();
		visceraMapList.clear();
		visceraMapList = BaseService.getInstance().getTblTissueSliceBatchService()
				.getMapListByTissueSliceVisceraId(tissueSliceVisceraId);
		if(null != visceraMapList && visceraMapList.size() > 0){
			for(Map<String,Object> obj:visceraMapList){
				String currentVisceraName = (String) obj.get("visceraName");
				String subVisceraName = (String) obj.get("subVisceraName");
				
				if(null != subVisceraName && !"".equals(subVisceraName.trim())){
					currentVisceraName = subVisceraName;
				}
				data_visceraNameListView.add(currentVisceraName);
			}
			visceraNameListView.getSelectionModel().select(visceraName);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceBatch_reason.fxml"));
		Scene scene = new Scene(root, 503, 328);
		stage.setScene(scene);
		stage.setTitle("脏器&排除原因");
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Map<String, Object> getVisceraMap() {
		return visceraMap;
	}

	public void setVisceraMap(Map<String, Object> visceraMap) {
		this.visceraMap = visceraMap;
	}

	
}
