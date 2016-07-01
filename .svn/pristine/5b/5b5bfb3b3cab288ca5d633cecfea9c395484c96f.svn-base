package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.util.messager.Messager;

public class OtherViscera extends Application implements Initializable {
	
	@FXML
	private TreeView<String> visceraTree;//脏器树
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	
	/**
	 * 点击确定后返回哪里
	 */
	private static String wherego;
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	private Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();

	private static OtherViscera instance;
	public static OtherViscera getInstance(){
		if(null == instance){
			instance = new OtherViscera();
		}
		return instance;
	}
	
	public OtherViscera() {}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initVisceraTree();
	
	}
	
	
//	public void loadData(){
//		updateVisceraTree();
//	}
	
	/**
	 * 加载数据（创建会话）
	 */
	public void loadData(String where,String taskId,String studyNo,String animalCode) {
		OtherViscera.wherego = where;
		updateVisceraTree(taskId,studyNo,animalCode);
	}
	
	/**加载数据，常规、追加组织切片编号
	 * @param where
	 * @param taskId
	 * @param studyNo
	 * @param gender  0，1，2
	 * @param showHistopathViscera 是否仅显示镜检脏器 
	 */
	public void loadData2(String where, String studyNo, boolean showHistopathViscera) {
		OtherViscera.wherego = where;
		updateVisceraTree2(studyNo, showHistopathViscera);
	}

	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null != selectItem){
			String visceraName = selectItem.getValue();
			Map<String,Object> map = visceraName2MapMap.get(visceraName);
			if(null != map){
				//viseraTree增加一数据并，选中它；成功返回true，失败返回false：msg
				Json json = null;
				if(null == wherego || wherego.equals("")){
					json = AnatomyProcessPage.getInstance().addOneItemAndSelectIt(visceraName,map);
				}else if(wherego.equals("addAnatomy")){
					json = AddAnatomy.getInstance().addOneItemAndSelectIt(visceraName,map);
				}else if(wherego.equals("DataEdit_AnimalViscera")){
					json = DataEdit_AnimalViscera.getInstance().addOneItemAndSelectIt(visceraName,map);
				}else if(wherego.equals("TissueSliceAdd")){
					String currentVisceraCode = "";
					String visceraName2 = (String) map.get("visceraName");
					if(visceraName.equals(visceraName2)){
						currentVisceraCode = (String) map.get("visceraCode");
					}else{
						currentVisceraCode = (String) map.get("subVisceraCode");
					}
					List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
					if(selectItem.isLeaf() && selectItem.getParent().equals(rootNode)){
						mapList.add(map);
					}else{
						ObservableList<TreeItem<String>> children = null;
						if(selectItem.isLeaf()){
							 children = selectItem.getParent().getChildren();
						}else{
							children = selectItem.getChildren();
						}
						for(TreeItem<String> obj:children){
							mapList.add(visceraName2MapMap.get(obj.getValue()));
						}
					}
					json = TissueSliceAdd.getInstance().addOneItemAndSelectIt(visceraName,currentVisceraCode,mapList);
				}else if(wherego.equals("AdditionalPage_anatomy")){
					json = AdditionalPage_anatomy.getInstance().addOneItemAndSelectIt(visceraName,map);
				}
			
				if(json.isSuccess()){
					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
				}else{
					showErrorMessage(json.getMsg());
				}
			}else{
				showErrorMessage("请先选择脏器！");
			}
		}else{
			showErrorMessage("请先选择脏器！");
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}


	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * 初始化脏器树
	 */
	private void initVisceraTree() {
		rootNode.setValue("脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
	}
	
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("OtherViscera.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("选择脏器");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	/**
	 * 更新 脏器树
	 */
	private void updateVisceraTree(String taskId,String studyNo,String animalCode){
		//更新树 TODO
		rootNode.getChildren().clear();

		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblAnatomyCheckService().getOtherVisceraCodeAndName(taskId,studyNo,animalCode);
		if(null != mapList && mapList.size() > 0 ){
			for(Map<String,Object> map:mapList){
				String visceraCode = (String) map.get("visceraCode");
				String visceraName = (String) map.get("visceraName");
				String subVisceraCode = (String) map.get("subVisceraCode");
				String subVisceraName = (String) map.get("subVisceraName");
				TreeItem<String> depNode = null;
				if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
					depNode = visceraCodeTreeItemMap.get(visceraCode);
				}else{
					depNode = new TreeItem<String>(visceraName);
					visceraName2MapMap.put(visceraName, map);
					rootNode.getChildren().add(depNode);
					visceraCodeTreeItemMap.put(visceraCode, depNode);
				}
				if(null != subVisceraCode){
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
				}
			}
		}
	
	}
	/**
	 * 更新 脏器树
	 */
	private void updateVisceraTree2(String studyNo,boolean showHistopathViscera){
		//更新树 TODO
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceIndexService().getOtherVisceraCodeAndName(studyNo,showHistopathViscera);
		if(null != mapList && mapList.size() > 0 ){
			for(Map<String,Object> map:mapList){
				String visceraCode = (String) map.get("visceraCode");
				String visceraName = (String) map.get("visceraName");
				String subVisceraCode = (String) map.get("subVisceraCode");
				String subVisceraName = (String) map.get("subVisceraName");
				TreeItem<String> depNode = null;
				if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
					depNode = visceraCodeTreeItemMap.get(visceraCode);
				}else{
					depNode = new TreeItem<String>(visceraName);
					visceraName2MapMap.put(visceraName, map);
					rootNode.getChildren().add(depNode);
					visceraCodeTreeItemMap.put(visceraCode, depNode);
				}
				if(null != subVisceraCode){
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
				}
			}
		}
		
	}
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

		
}
