package com.lanen.view.test;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.util.messager.Messager;
import com.lanen.view.test.TissueSliceFeichanggui.AnimalTissueCode;

/**其他脏器或组织
 * @author Administrator
 *
 */
public class OtherTissue extends Application implements Initializable {
	
	
	
	@FXML
	private ComboBox<String> combobox;  //
	private ObservableList<String> data_combobox = FXCollections.observableArrayList();
	
	@FXML
	private Label anatomyFindingLabel;//解剖所见
	
	@FXML
	private ListView<String> anatomyPosListView; // 解剖学所见部位ListView
	private ObservableList<String> data_anatomyPosListView = FXCollections.observableArrayList();

	@FXML
	private ListView<String> anatomyFindingListView; // 解剖所见ListView
	private ObservableList<String> data_anatomyFindingListView_tongyong = FXCollections.observableArrayList();
	//存放解剖所通用所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tongyongDictPathCommonMap = new HashMap<String,DictPathCommon>();
	private ObservableList<String> data_anatomyFindingListView_tesu = FXCollections.observableArrayList();
	//存放解剖所特殊所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tesuDictPathCommonMap = new HashMap<String,DictPathCommon>();
	
	@FXML
	private ListView<String> bodySufacePosListView; // 体表部位ListView
	private ObservableList<String> data_bodySufacePosListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> animalCodeListView; // 动物编号
	private ObservableList<String> data_animalCodeListView = FXCollections.observableArrayList();
	private Map<String,Integer> animalCode2GenderMap =new  HashMap<String,Integer>();
	
	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();
	
	@FXML
	private RadioButton tongyongRadioButton; // 普通所见RadioButton
	
	@FXML
	private RadioButton tesuRadioButton; // 特殊所见RadioButton
	
	//存放解剖学所见部位：毒性病理字典
	private Map<String,DictPathCommon> anatomyPosDictPathCommonMap = new HashMap<String,DictPathCommon>();
		
	
	//专题编号
	private String studyNo = null;
	
	private String visceraCode = null;
	
	private int selectType = 0;//0:非常规病变  1：脏器
	private static OtherTissue instance;
	public static OtherTissue getInstance(){
		if(null == instance){
			instance = new OtherTissue();
		}
		return instance;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		
		initComboBox();
		
		//初始化 11个  ListView
		init11ListView();
		inittgRadioButton();
		
		initVisceraTree();
		
		initAnimalCodeListView();
	}

	private void initAnimalCodeListView() {
		animalCodeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					Integer gender = animalCode2GenderMap.get(newValue);
					System.out.println(newValue);
					System.out.println(gender);
					if(null != gender){
						updateVisceraTree(gender);
					}
				}
			}
			
		});
		
	}

	private void initVisceraTree() {
		rootNode.setValue("");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				// 选择脏器树      change事件
				visceraTree.setUserData(true);
				if(null != newValue){
					String visceraName = newValue.getValue();
					bodySufacePosListView.getSelectionModel().clearSelection();
					Map<String,Object> map = visceraName2MapMap.get(visceraName);
					if(null != map){
						visceraCode = (String) map.get("visceraCode");
						//根据脏器编号更新 解剖学所见部位
						updateAnatomyPosListViewData(visceraCode);
						//根据脏器编号更新 解剖学所见(通用，特殊)
						updateAnatomyFindingListViewData(visceraCode);
					}
					visceraTree.setId(visceraName);
					
					
				}else{
					//清空ListView
					updateAnatomyPosListViewData(null);
					updateAnatomyFindingListViewData(null);
					
					visceraTree.setId("");
					updateAnatomyFindingLabelText();
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
				
				// 
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
	}
	/**根据脏器编号更新    解剖学所见部位
	 * @param visceraCode
	 */
	private void updateAnatomyPosListViewData(String visceraCode){
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();
		List<DictPathCommon> dictPathCommon1List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,1);
		if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
					data_anatomyPosListView.add(obj.getDescCn());
					anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
			}
		}
	}
	private void initComboBox() {
		combobox.setItems(data_combobox);
		data_combobox.add("组织");
		data_combobox.add("脏器");
		combobox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					selectType = newValue.intValue();
					clearAllSelectAndSetState(selectType);
				}
			}});
	}

	/**
	 * 清空所有选择，设置对应状态
	 */
	private void clearAllSelectAndSetState(int flag){
		
//		animalCodeListView.getSelectionModel().clearSelection();
//		visceraTree.getSelectionModel().clearSelection();
		anatomyPosListView.getSelectionModel().clearSelection();
		anatomyFindingListView.getSelectionModel().clearSelection();
		bodySufacePosListView.getSelectionModel().clearSelection();
		
		tongyongRadioButton.setSelected(true);
		tongyongRadioButton.setDisable(false);
		tesuRadioButton.setDisable(false);
		if(flag == 0){
			visceraTree.setDisable(false);
			anatomyPosListView.setDisable(false);
			anatomyFindingListView.setDisable(false);
			bodySufacePosListView.setDisable(false);
		}else{
			visceraTree.setDisable(false);
			anatomyPosListView.setDisable(true);
			anatomyFindingListView.setDisable(true);
			bodySufacePosListView.setDisable(true);
			tongyongRadioButton.setDisable(true);
			tesuRadioButton.setDisable(true);
		}
	}
	/**
	 * 初始化通用/特殊 所见 RadioButton
	 */
	private void inittgRadioButton() {
		
		tongyongRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView
					anatomyFindingListView.getSelectionModel().clearSelection();
					anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
					
				}
			}});
		
		tesuRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView  
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem){
						String visceraName = selectItem.getValue();
						Map<String,Object> map = visceraName2MapMap.get(visceraName);
						if(null != map){
							visceraCode = (String) map.get("visceraCode");
							//根据脏器编号更新 解剖学所见(通用，特殊)
							updateAnatomyFindingListViewData(visceraCode);
						}
					}else{
						updateAnatomyFindingListViewData(null);
					}
				}
			}});
	}
	
	
	/**根据脏器编号更新    解剖学所见(通用，特殊)
	 * @param visceraCode
	 */
	private void updateAnatomyFindingListViewData(String visceraCode){
		if(tongyongRadioButton.isSelected()){
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,1);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
//					if(obj.getSpecicalFlag() == 1){
						data_anatomyFindingListView_tesu.add(obj.getDescCn());
						anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
//					}
				}
			}
		}
	}
	
	
	/**
	 * 11个ListView填充数据
	 */
	private void initData11ListView(String id) {
		TblAnatomyCheck anatomyCheck = null;
		List<DictPathCommon> dictPathCommon1List ;
		data_anatomyPosListView.clear();
		if(null != id){
			 anatomyCheck= BaseService.getInstance().getTblAnatomyCheckService().getById(id);
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, anatomyCheck.getVisceraCode(),1);
		}else{
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,1);
		}
	
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();

	    if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
				data_anatomyPosListView.add(obj.getDescCn());
				anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
			}
		}
	    
		anatomyPosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
			}
		});
		
		//2:解剖通用所见
		data_anatomyFindingListView_tongyong.clear();
		anatomyFinding_tongyongDictPathCommonMap.clear();
		int AnatomyFindingFlag = 0;
		if(null != anatomyCheck){
			AnatomyFindingFlag = anatomyCheck.getAnatomyFindingFlag();
		}
		  
		List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(2,1);
		if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
			for(DictPathCommon obj:dictPathCommon2List){
//				if(obj.getSpecicalFlag() == 1){
					data_anatomyFindingListView_tongyong.add(obj.getDescCn());
					anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
//				}
			}
		}
		if(AnatomyFindingFlag == 0){
			
			
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,1);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					if(obj.getSpecicalFlag() == 1){
						data_anatomyFindingListView_tesu.add(obj.getDescCn());
						anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
					}
				}
			}
			tesuRadioButton.setSelected(true);
		}
		  
		//4:体表部位
		data_bodySufacePosListView.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,1);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
			}
		}
		
	}	
	
	/**
	 * 初始化 11个  ListView
	 */
	private void init11ListView() {
		animalCodeListView.setItems(data_animalCodeListView);
		animalCodeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		
		anatomyPosListView.setItems(data_anatomyPosListView);
		anatomyPosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				anatomyPosListView.setUserData(true);
				if(null != newValue){
					anatomyPosListView.setId(newValue);
				}else{
					anatomyPosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		
		//解剖所见(通用)
		anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		anatomyFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		
		bodySufacePosListView.setItems(data_bodySufacePosListView);
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				bodySufacePosListView.setUserData(true);
				if(null != newValue){
					visceraTree.getSelectionModel().clearSelection();
					bodySufacePosListView.setId(newValue);
				}else{
					bodySufacePosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		bodySufacePosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
			}
		});
				
	}
	/**
	 * 更新 anatomyFindingLabel 值
	 */
	private void updateAnatomyFindingLabelText(){
		anatomyFindingLabel.setText("");
	}
	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(String studyNo ){
		//tongyongRadioButton 被选中（通用所见）
		combobox.getSelectionModel().clearSelection();
		combobox.getSelectionModel().select(0);
		initData11ListView(null);
		this.studyNo = studyNo;
		updateAnimalCodeList();
		updateVisceraTree(0);
	}
	
	/**
	 * 更新脏器列表
	 */
	private void updateVisceraTree(int gender) {
		//更新树 
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();

		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceIndexService().getAllVisceraCodeAndName(studyNo,gender);
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
	 * 更新动物表
	 */
	private void updateAnimalCodeList() {
		data_animalCodeListView.clear();
		animalCode2GenderMap.clear();
		List<Map<String,Object>> animalCodeMapList = BaseService.getInstance().getTblTissueSliceIndexService().getAnimalCodeListByStudyNo(studyNo);
		if(null != animalCodeMapList && animalCodeMapList.size() > 0){
			for(Map<String, Object> map:animalCodeMapList){
				String animalCode = (String) map.get("animalCode");
				Integer gender = (Integer) map.get("gender");
				animalCode2GenderMap.put(animalCode, gender);
				
				data_animalCodeListView.add(animalCode);
			}
		}
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		String anatomyFingding =null;
		if(selectType == 0){
			if(null == treeSelectedItem){
				String bodyPos = bodySufacePosListView.getSelectionModel().getSelectedItem();
				if(null == bodyPos){
					showErrorMessage("请先选择脏器或体表部位！");
					return ;
				}
			}
			anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			if(null == anatomyFingding){
				showErrorMessage("请先选择解剖学所见！");
				return ;
			}
		}else{
			if(null == treeSelectedItem){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}
		}
		
		
		String visceraOrTissueName = null; 
		String visceraCode = null; 
		Integer visceraType = 0; 
		String visceraFixedRecordId = null; 
//		animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
//		 *	visceraName,subVisceraCode,subVisceraName,"+
//		 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
//		 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
		String visceraName = null; 
		String subVisceraCode = null; 
		String subVisceraName = null; 
		
		Integer isHandword = 1;
		Integer specialFlag = 0; 
		String anatomyPosCode = null; 
		String anatomyPos = null; 
		String anatomyFindingCode = null; 
		Integer anatomyFindingFlag = 0; 
		String bodySurfacePos = null; 
		
		Map<String,Object> visceraMap = null;
		if(selectType == 0){
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
				if(treeSelectedItem.getParent() == rootNode){
					//父节点
					visceraType = (Integer) visceraMap.get("visceraType");
					visceraCode = (String) visceraMap.get("visceraCode");
					visceraName = (String) visceraMap.get("visceraName");
				}else{
					visceraType = (Integer) visceraMap.get("visceraType");
					visceraCode = (String) visceraMap.get("visceraCode");
					visceraName = (String) visceraMap.get("visceraName");
					subVisceraCode = (String) visceraMap.get("subVisceraCode");
					subVisceraName = (String) visceraMap.get("subVisceraName");
					
				}
				
				anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
				if(null != anatomyPos){
					DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
					anatomyPosCode = dictPathCommon1.getItemCode();
				}
			}
			
			if(tesuRadioButton.isSelected()){
				anatomyFindingFlag = 1;
				DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
				anatomyFindingCode = dictPathCommon2.getItemCode();
				specialFlag = dictPathCommon2.getSpecicalFlag();
			}else{
				DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
				anatomyFindingCode = dictPathCommon3.getItemCode();
				specialFlag = dictPathCommon3.getSpecicalFlag();
			}
			bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			visceraOrTissueName = bodySurfacePos != null ? bodySurfacePos:(subVisceraName != null ? subVisceraName:visceraName);
			if(null != anatomyPos){
				visceraOrTissueName+=" "+anatomyPos;
			}
			visceraOrTissueName+=" "+anatomyFingding;
		}else{
			visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
			if(treeSelectedItem.getParent() == rootNode){
				//父节点
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
			}else{
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				subVisceraName = (String) visceraMap.get("subVisceraName");
			}
			visceraOrTissueName = subVisceraName != null ? subVisceraName:visceraName;
		}
		boolean isExist = TissueSliceFeichanggui.getInstance().isExist(animalCode, visceraOrTissueName);
		if(isExist){
			showErrorMessage("‘"+animalCode+" "+visceraOrTissueName+"’已存在！");
			return ;
		}
		AnimalTissueCode animalTissueCode = new AnimalTissueCode(0, "", animalCode, visceraOrTissueName, visceraType, visceraCode, visceraName, subVisceraCode, subVisceraName, 
				visceraFixedRecordId, 
				isHandword, specialFlag, 
				anatomyPosCode, anatomyPos,
				anatomyFindingFlag,
				anatomyFindingCode, 
				anatomyFingding,
				bodySurfacePos);
		TissueSliceFeichanggui.getInstance().animalVisceraTablePlusOne(animalTissueCode);
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("OtherTissue.fxml"));
		Scene scene = new Scene(root, 870, 506);
		stage.setScene(scene);
		stage.setTitle("");
		stage.setResizable(false);
		stage.setMinWidth(870);
		stage.setMinHeight(506);
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
