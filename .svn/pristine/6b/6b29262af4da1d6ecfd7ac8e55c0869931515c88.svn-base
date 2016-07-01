package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**选择动物&脏器
 * @author Administrator
 *
 */
public class DataEdit_AnimalViscera extends Application implements Initializable {
	
	private Stage stage;
	 
	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	
	@FXML   
	private ComboBox<String> studyNoComboBox;//专题编号

    private ObservableList<String> studyNodata = FXCollections.observableArrayList();
    
	
	@FXML
	private Button addButton; //添加按钮
    
	@FXML   
	private ComboBox<String> animalCodeComboBox;//动物编号

    private ObservableList<String> animalCodedata = FXCollections.observableArrayList();
	
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();

	
	private static DataEdit_AnimalViscera instance;
	
	
//	
//	//任务id列表
//	private static List<String> taskIdList = null;
//	
//	//会话id列表
//	private static List<String> sessionIdList = null;
	
	private String taskId;
	
	public static DataEdit_AnimalViscera getInstance(){
		if(null == instance){
			instance =  new DataEdit_AnimalViscera();
		}
		return instance;
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTaskTree();
	    initComboBoxStudyNo();
	    initComboBoxAnimalCode();
	}
	
	
	/**
	 * 初始化专题
	 */
	private void initComboBoxStudyNo(){
		studyNoComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateComboBoxAnimalCode(newValue);
				}
			}
		});
	}
	
	
	private void updateComboBoxStudyNo() {
		//初始化service
//        List<String> list=new ArrayList<String>();
//        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyTaskService().getStudyNoByTaskIdList(taskIdList);
//    	if(null != mapList && mapList.size()>0){
//			for(Map<String,Object> map:mapList){
//				String animalCode = (String) map.get("studyNo");//
//				list.add(animalCode);
//		    }
//    	}
//    	studyNodata.clear();
//		if(null!=list && list.size()>0){
//			for(String dataBit : list){
//				studyNodata.add(dataBit);
//			}
//		}
		TblAnatomyTask anatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		String studyNo = "";
		if(null != anatomyTask){
			studyNo = anatomyTask.getStudyNo();
		}
		studyNodata.clear();
		studyNodata.add(studyNo);
		studyNoComboBox.setItems(studyNodata);
		studyNoComboBox.getSelectionModel().selectFirst();
	}
	
	/**
	 * 初始化动物编号
	 */
	private void initComboBoxAnimalCode(){
		animalCodeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
				    String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
				    if(null != studyNo){
				    	TblAnatomyAnimal anatomyAnimal= BaseService.getInstance().getTblAnatomyAnimalService().getByStudyNoAnimalCode(studyNo,newValue);
					    if(null == anatomyAnimal ){
					    	showMessage("该动物未解剖，请选择已解剖的动物！");
					    	animalCodeComboBox.getSelectionModel().clearSelection();
					    }else{
					    	//更新脏器树
					    	updateVisceraTree(anatomyAnimal.getTaskId(),studyNo,newValue);
					    }
				    }
					
				}else{
					//清空脏器树
					updateVisceraTree(null, null, null);
				}
			}
		});
		
	}
	
	/**
	 * 初始化动物编号
	 */
	private void updateComboBoxAnimalCode(String studyNo){
		animalCodeComboBox.getSelectionModel().clearSelection();
        List<String> list=new ArrayList<String>();
		//         List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyReqAnimalListService().getAnimalCodeByStudyNo(studyNo,sessionIdList);
        List<String> taskIdList = new ArrayList<String>();
        taskIdList.add(taskId);
        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyReqAnimalListService().getAnimalCodeByTaskIdList(taskIdList);
     	if(null != mapList && mapList.size()>0){
 			for(Map<String,Object> map:mapList){
 				String animalCode = (String) map.get("animalCode");//
 				list.add(animalCode);
 		    }
     	}
         animalCodedata.clear();
 		if(null!=list && list.size()>0){
 			for(String dataBit : list){
 				animalCodedata.add(dataBit);
 			}
 		}
 		animalCodeComboBox.setItems(animalCodedata);
 		animalCodeComboBox.getSelectionModel().selectFirst();
	}
	
		
	/**
	 * 初始化选择脏器树
	 */
	private void initTaskTree() {
		rootNode.setValue("选择脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				    visceraTree.setUserData(true);
					if(null != newValue){
						addButton.setDisable(false);
						visceraTree.setId(newValue.getValue());
					}else{
						addButton.setDisable(false);
						visceraTree.setId("");
					}
			}
			
		});
		
		visceraTree.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				Boolean isChange = (Boolean) visceraTree.getUserData();
				if(null != isChange && !isChange){
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.getValue().equals(visceraTree.getId())){
						visceraTree.getSelectionModel().clearSelection();
					}
				}
				visceraTree.setUserData(false);
			}
		});
		
	}
	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(String taskId) {
//		DataEdit_AnimalViscera.taskIdList = taskIdList;
//		DataEdit_AnimalViscera.sessionIdList = sessionIdList;
		this.taskId = taskId;
	    updateComboBoxStudyNo() ;
	}
	
	/**
	 * 其他脏器
	 * 
	 * @param event
	 */
	@FXML
	void onOtherVisceraBtnAction(ActionEvent event) {
		try {
		    String  animalCode = animalCodeComboBox.getSelectionModel().getSelectedItem();
		    String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		    TblAnatomyAnimal anatomyAnimal = BaseService.getInstance().getTblAnatomyAnimalService().getByStudyNoAnimalCode(studyNo, animalCode);
		    String taskId =anatomyAnimal.getTaskId();
		    
		    
//			OtherViscera.getInstance().start(getStage());
		    Stage stage = Main.stageMap.get("OtherViscera");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				OtherViscera.getInstance().start(stage);
				Main.stageMap.put("OtherViscera",stage);
			}else{
				stage.show();
			}
			OtherViscera.getInstance().loadData("DataEdit_AnimalViscera",taskId,studyNo,animalCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新 选择脏器树 ，根据任务id
	 */
	private void updateVisceraTree(String taskId,String studyNo,String animalCode){
		//清空树
		rootNode.getChildren().clear();
		
		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		if(null != studyNo && null != animalCode){
//			List<Map<String,Object>> mapList = BaseService.getInstance()
//					.getTblAnatomyCheckService().getVisceraCodeAndNameByStudyNo(studyNo);
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblAnatomyCheckService().getVisceraCodeAndName(taskId,studyNo,animalCode);
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
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						TreeItem<String> leafNode = new TreeItem<String>();
						leafNode.setValue(subVisceraName);
						visceraName2MapMap.put(subVisceraName, map);
						depNode.getChildren().add(leafNode);
					}
				}
				visceraTree.getSelectionModel().clearSelection();
			}
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
	 * 确定
	 * @param event
	 */
	@FXML
	private void onaddBtnAction(ActionEvent event){
	    	TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
	    	Map<String,Object> visceraMap=null;
	    	if(null!=treeSelectedItem){
	    		visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
	    		if(treeSelectedItem.getParent() == rootNode){
	    			//TODO 
	    			visceraMap.remove("subVisceraCode");
	    			visceraMap.remove("subVisceraName");
	    		}
	    	}
		    String  animalCode = animalCodeComboBox.getSelectionModel().getSelectedItem();
		    String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		    TblAnatomyAnimal anatomyAnimal= BaseService.getInstance().getTblAnatomyAnimalService().getByStudyNoAnimalCode(studyNo,animalCode);
		    if(null == anatomyAnimal ){
		    	showMessage("该动物未解剖，请选择已解剖的动物！");
		    }else{
		    	try {
//					EditAnatomy.getInstance().start(getStage());
//					EditAnatomy.getInstance().loadData(null,"add",visceraMap,anatomyAnimal);
					DataEdit_AddEdit.getInstance().loadDataVisceraSelect(visceraMap, anatomyAnimal);
					Stage stage =  Main.stageMap.get("DataEdit_AnimalViscera");
					if(null!=stage){
						stage.hide();
					}
//					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
	}
	
	/**
	 * viseraTree增加一数据并，选中它；成功返回true，失败返回false：msg
	 * @param map
	 * @return
	 */
	public Json addOneItemAndSelectIt(String currentVisceraName,Map<String, Object> map) {
		
		
		Json json = new Json();
		
		if(null != map && null != currentVisceraName){
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			//String subVisceraCode = (String) map.get("subVisceraCode");
			String subVisceraName = (String) map.get("subVisceraName");
			
			//判断是否存在
			if(visceraName2MapMap.keySet().contains(currentVisceraName)){
				json.setMsg("脏器（"+currentVisceraName+"）已存在!");
			}else{
				TreeItem<String> depNode = null;
				//是子节点
				if(currentVisceraName.equals(subVisceraName)){
					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap.get(visceraCode);
					}else{
						depNode = new TreeItem<String>(visceraName);
						visceraName2MapMap.put(visceraName, map);
						rootNode.getChildren().add(depNode);
						visceraCodeTreeItemMap.put(visceraCode, depNode);
					}
					//增加子节点
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(leafNode);
				}else{
					depNode = new TreeItem<String>(visceraName);
					visceraName2MapMap.put(visceraName, map);
					rootNode.getChildren().add(depNode);
					visceraCodeTreeItemMap.put(visceraCode, depNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(depNode);
				}
				
			}
			
			
		}
		return json;
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DataEdit_AnimalViscera.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("选择动物&脏器");
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
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
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
