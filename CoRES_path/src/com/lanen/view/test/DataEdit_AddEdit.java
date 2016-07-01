package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblAnatomyCheckEdit;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

/**解剖所见数据修改_添加编辑
 * @author Administrator
 *
 */
public class DataEdit_AddEdit extends Application implements Initializable {
	
	private static DataEdit_AddEdit instance;
	@FXML
	private Button okButton;
	
	@FXML
	private Label anatomyFindingLabel;//解剖所见
	@FXML
	private Hyperlink selectAddVisceraButton;//添加解剖所见时，选择脏器
//	//任务id列表
//	private static List<String> taskIdList = null;
//	
//	//会话id列表
//	private static List<String> sessionIdList = null;
	
	private String taskId;
	
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
	private ListView<String> posListView; // 位置ListView
	private ObservableList<String> data_posListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> shapeListView; // 形状ListView
	private ObservableList<String> data_shapeListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> colorListView; // 颜色ListView
	private ObservableList<String> data_colorListView = FXCollections.observableArrayList();

	@FXML
	private ListView<String> textureListView; // 硬度ListView
	private ObservableList<String> data_textureListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> numberListView; // 数量ListView
	private ObservableList<String> data_numberListView = FXCollections.observableArrayList();

	@FXML
	private ListView<String> rangeListView; // 范围ListView
	private ObservableList<String> data_rangeListView = FXCollections.observableArrayList();

	@FXML
	private ListView<String> lesionDegreeListView; // 病变程度ListView
	private ObservableList<String> data_lesionDegreeListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> sizeListView; // 大小ListView
	private ObservableList<String> data_sizeListView = FXCollections.observableArrayList();
	@FXML
	private TextField sizeTextField;
	
	@FXML
	private RadioButton tongyongRadioButton; // 普通所见RadioButton
	
	@FXML
	private RadioButton tesuRadioButton; // 特殊所见RadioButton
	
	//存放解剖所所见部位：毒性病理字典
	private Map<String,DictPathCommon> anatomyPosDictPathCommonMap = new HashMap<String,DictPathCommon>();
		
	private static int sortMethod = 1;	//排序方式 sortMethod :1,字典排序  2，字母排序
	
	private String tblAnatomyCheckId = null;
	
	private TblAnatomyCheck tblAnatomyCheck = null;
	
	//任务id列表
	private static String visceraCode = null;
	
	//解剖动物信息
	private static TblAnatomyAnimal anatomyAnimal = null;
	
	//动物编号   
	private static String animalCode = null;
		
	//专题编号
	private static String studyNo = null;
	
    private static Map<String,Object> visceraMap = null;
    
    private static String addOrEdit;
	
	public static DataEdit_AddEdit getInstance(){
		if(null == instance){
			instance = new DataEdit_AddEdit();
		}
		return instance;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		//初始化 11个  ListView
		init11ListView();
		inittgRadioButton();
	}

	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DataEdit_AddEdit.fxml"));
		Scene scene = new Scene(root, 930, 600);
		stage.setScene(scene);
		stage.setTitle("数据修改");
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
					if(null != visceraCode){
							//根据脏器编号更新 解剖学所见(通用，特殊)
							updateAnatomyFindingListViewData(visceraCode);
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
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,sortMethod);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					data_anatomyFindingListView_tesu.add(obj.getDescCn());
					anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
				}
			}
		}
	}
	
	
	/**选择脏器按钮事件
	 * @param event
	 */
	@FXML
	private void on_selectAddVisceraButton(ActionEvent event){
		try {
			Stage stage =  Main.stageMap.get("DataEdit_AnimalViscera");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				DataEdit_AnimalViscera.getInstance().start(stage);
				Main.stageMap.put("DataEdit_AnimalViscera",stage);
			}else{
				stage.show();
			}
			DataEdit_AnimalViscera.getInstance().loadData(taskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 11个ListView填充数据
	 */
	private void initData11ListView(TblAnatomyCheck anatomyCheck) {
		List<DictPathCommon> dictPathCommon1List ;
		data_anatomyPosListView.clear();
		if(null != anatomyCheck){
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, anatomyCheck.getVisceraCode(),sortMethod);
		}else{
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,sortMethod);
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
				.getDictPathCommonService().getListByDictType(2,sortMethod);
		if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
			for(DictPathCommon obj:dictPathCommon2List){
				data_anatomyFindingListView_tongyong.add(obj.getDescCn());
				anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
			}
		}
		if(AnatomyFindingFlag == 0){
			
			
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,sortMethod);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					data_anatomyFindingListView_tesu.add(obj.getDescCn());
					anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
				}
			}
			tesuRadioButton.setSelected(true);
		}
		  
		//4:体表部位
		data_bodySufacePosListView.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,sortMethod);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
			}
		}
		
		
		
		//5:位置
		data_posListView.clear();
		List<DictPathCommon> dictPathCommon5List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(5,sortMethod);
		if(null != dictPathCommon5List && dictPathCommon5List.size() > 0){
			for(DictPathCommon obj:dictPathCommon5List){
				data_posListView.add(obj.getDescCn());
			}
		}
		 
		posListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		//6:分布、范围
		data_rangeListView.clear();
		List<DictPathCommon> dictPathCommon6List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(6,sortMethod);
		if(null != dictPathCommon6List && dictPathCommon6List.size() > 0){
			for(DictPathCommon obj:dictPathCommon6List){
				data_rangeListView.add(obj.getDescCn());
			}
		}
		
		//7:数量
		data_numberListView.clear();
		List<DictPathCommon> dictPathCommon7List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(7,sortMethod);
		if(null != dictPathCommon7List && dictPathCommon7List.size() > 0){
			for(DictPathCommon obj:dictPathCommon7List){
				data_numberListView.add(obj.getDescCn());
			}
		}
		
		//8:形状
		data_shapeListView.clear();
		List<DictPathCommon> dictPathCommon8List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(8,sortMethod);
		if(null != dictPathCommon8List && dictPathCommon8List.size() > 0){
			for(DictPathCommon obj:dictPathCommon8List){
				data_shapeListView.add(obj.getDescCn());
			}
		}
		
		//9:颜色
		data_colorListView.clear();
		List<DictPathCommon> dictPathCommon9List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(9,sortMethod);
		if(null != dictPathCommon9List && dictPathCommon9List.size() > 0){
			for(DictPathCommon obj:dictPathCommon9List){
				data_colorListView.add(obj.getDescCn());
			}
		}
		
		//10:硬度
		data_textureListView.clear();
		List<DictPathCommon> dictPathCommon10List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(10,sortMethod);
		if(null != dictPathCommon10List && dictPathCommon10List.size() > 0){
			for(DictPathCommon obj:dictPathCommon10List){
				data_textureListView.add(obj.getDescCn());
			}
		}
		
		//11:大小
		data_sizeListView.clear();
		sizeTextField.setText("");
		sizeListView.getSelectionModel().clearSelection(); // 大小ListView
		List<DictPathCommon> dictPathCommon11List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(11,sortMethod);
		if(null != dictPathCommon11List && dictPathCommon11List.size() > 0){
			for(DictPathCommon obj:dictPathCommon11List){
				data_sizeListView.add(obj.getDescCn());
			}
		}
		sizeTextField.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue && !"".equals(newValue)){
					sizeListView.getSelectionModel().clearSelection();
					if(data_sizeListView.size() > 0){
						for(String str :data_sizeListView){
							if(newValue.equals(str)){
								sizeListView.getSelectionModel().select(str);
							}
						}
					}
				}else{
					sizeListView.getSelectionModel().clearSelection();
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
				
			}
		});
		
		//12:病变程度
		data_lesionDegreeListView.clear();
		List<DictPathCommon> dictPathCommon12List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(12,sortMethod);
		if(null != dictPathCommon12List && dictPathCommon12List.size() > 0){
			for(DictPathCommon obj:dictPathCommon12List){
				data_lesionDegreeListView.add(obj.getDescCn());
			}
		}
			
		if(null != anatomyCheck){
	    	//选择解剖所见部位
			anatomyPosListView.getSelectionModel().select(anatomyCheck.getAnatomyPos());
			//选择解剖所见
			String anatomyFingding = anatomyCheck.getAnatomyFingding();
			anatomyFindingListView.getSelectionModel().select(anatomyFingding);
			//选择体表部位
			String bodySurfacePos = anatomyCheck.getBodySurfacePos();
			bodySufacePosListView.getSelectionModel().select(bodySurfacePos);
			//选择位置
			String pos = anatomyCheck.getPos();
			posListView.getSelectionModel().select(pos);
			//选择分布范围		
			String range = anatomyCheck.getRange();	
			rangeListView.getSelectionModel().select(range);
			//选择数量
			String number = anatomyCheck.getNumber();
			numberListView.getSelectionModel().select(number);
			String shape = anatomyCheck.getShape();
			shapeListView.getSelectionModel().select(shape);
			String color = anatomyCheck.getColor();
			colorListView.getSelectionModel().select(color);
			String texture =  anatomyCheck.getTexture();
			textureListView.getSelectionModel().select(texture);
			String size = anatomyCheck.getSize();
			sizeListView.getSelectionModel().select(size);
			String lesionDegree = anatomyCheck.getLesionDegree();
			lesionDegreeListView.getSelectionModel().select(lesionDegree);	
	    }
	}	
	
	/**
	 * 初始化 11个  ListView
	 */
	private void init11ListView() {
		
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
				
		posListView.setItems(data_posListView);
		posListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				posListView.setUserData(true);
				if(null != newValue){
					posListView.setId(newValue);
				}else{
					posListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		
		
		
		shapeListView.setItems(data_shapeListView);
		shapeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				shapeListView.setUserData(true);
				if(null != newValue){
					shapeListView.setId(newValue);
				}else{
					shapeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		
		shapeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
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
		
		
		colorListView.setItems(data_colorListView);
		colorListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				colorListView.setUserData(true);
				if(null != newValue){
					colorListView.setId(newValue);
				}else{
					colorListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		colorListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		
		textureListView.setItems(data_textureListView);
		textureListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				textureListView.setUserData(true);
				if(null != newValue){
					textureListView.setId(newValue);
				}else{
					textureListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		textureListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		
		numberListView.setItems(data_numberListView);
		numberListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				numberListView.setUserData(true);
				if(null != newValue){
					numberListView.setId(newValue);
				}else{
					numberListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		numberListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		
		rangeListView.setItems(data_rangeListView);
		rangeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				rangeListView.setUserData(true);
				if(null != newValue){
					rangeListView.setId(newValue);
				}else{
					rangeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		rangeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		
		lesionDegreeListView.setItems(data_lesionDegreeListView);
		lesionDegreeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				lesionDegreeListView.setUserData(true);
				if(null != newValue){
					lesionDegreeListView.setId(newValue);
				}else{
					lesionDegreeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		lesionDegreeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
		//大小
		sizeListView.setItems(data_sizeListView);
		sizeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				sizeListView.setUserData(true);
				if(null != newValue){
					sizeListView.setId(newValue);
					
					sizeTextField.setText(newValue);
					
				}else{
					sizeListView.setId("");
				}
				
			}
		});
		sizeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
						
						sizeTextField.setText("");
					}
				}
				list.setUserData(false);
				
			}
		});
		
		sizeTextField.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue && !"".equals(newValue)){
					sizeListView.getSelectionModel().clearSelection();
					if(data_sizeListView.size() > 0){
						for(String str :data_sizeListView){
							if(newValue.equals(str)){
								sizeListView.getSelectionModel().select(str);
							}
						}
					}
				}else{
					sizeListView.getSelectionModel().clearSelection();
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
				
			}
		});
	}
	/**
	 * 更新 anatomyFindingLabel 值
	 */
	private void updateAnatomyFindingLabelText(){
		anatomyFindingLabel.setText("");
		String visceraName = "";
	   if(addOrEdit.equals("add")){
		   if(null!=visceraMap){
			   visceraName = (String) visceraMap.get("visceraName");
				String subVisceraName = (String) visceraMap.get("subVisceraName");
				if(null!=visceraName&& !visceraName.equals("") &&null != subVisceraName && !subVisceraName.equals("")){
					visceraName = visceraName +" - "+subVisceraName;
				}
		   }
		    
	    }else{
	    	TblAnatomyCheck anatomyCheck= BaseService.getInstance().getTblAnatomyCheckService().getById(tblAnatomyCheckId);
	    	visceraName = anatomyCheck.getVisceraName();
	    	studyNo = anatomyCheck.getStudyNo();
	    	animalCode = anatomyCheck.getAnimalCode();
	    }
	   String finding = "";
	   if(null!=visceraName && !visceraName.equals("")){
		   finding = "专题编号："+studyNo+" 动物编号："+animalCode+" 脏器："+visceraName+" 解剖所见：";
		   finding = finding + (anatomyPosListView.getSelectionModel().getSelectedItem() == null ?
					"":anatomyPosListView.getSelectionModel().getSelectedItem()+" ");
	   	}else{
	   		finding = "专题编号："+studyNo+" 动物编号："+animalCode+" 解剖所见：";
	   		finding = finding + (bodySufacePosListView.getSelectionModel().getSelectedItem() == null ?
					"":bodySufacePosListView.getSelectionModel().getSelectedItem()+" ");
	   	}
//	   finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
//			   "":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
	   
	   finding = finding + (posListView.getSelectionModel().getSelectedItem() == null ?
				"":posListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (numberListView.getSelectionModel().getSelectedItem() == null ?
				"":numberListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (rangeListView.getSelectionModel().getSelectedItem() == null ?
				"":rangeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + 
				((sizeTextField.getText() == null || "".equals(sizeTextField.getText())) ? 
				"":sizeTextField.getText()+" ");
		finding = finding + (colorListView.getSelectionModel().getSelectedItem() == null ?
				"":colorListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (textureListView.getSelectionModel().getSelectedItem() == null ?
				"":textureListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (shapeListView.getSelectionModel().getSelectedItem() == null ?
				"":shapeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		finding = finding + (lesionDegreeListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionDegreeListView.getSelectionModel().getSelectedItem()+" ");
		anatomyFindingLabel.setText(finding);
	}
	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(String tblAnatomyCheckId,String addOrEdit,String taskId ){
		
		this.taskId = taskId;
		//tongyongRadioButton 被选中（通用所见）
		tongyongRadioButton.setSelected(true);
		if(addOrEdit.equals("add")){
			DataEdit_AddEdit.addOrEdit="add";
			selectAddVisceraButton.setDisable(false);
			anatomyFindingLabel.setVisible(false);
			visceraCode=null;
			okButton.setDisable(true);
			initData11ListView(null);
		}else{
			this.tblAnatomyCheckId = tblAnatomyCheckId;
			DataEdit_AddEdit.addOrEdit="edit";
			selectAddVisceraButton.setDisable(true);
			anatomyFindingLabel.setVisible(true);
			TblAnatomyCheck anatomyCheck= BaseService.getInstance().getTblAnatomyCheckService().getById(tblAnatomyCheckId);
			this.tblAnatomyCheck = anatomyCheck;
			DataEdit_AddEdit.visceraCode = anatomyCheck.getVisceraCode();
			if(null != DataEdit_AddEdit.visceraCode){
				bodySufacePosListView.setDisable(true);
			}else{
				bodySufacePosListView.setDisable(false);
			}
			DataEdit_AddEdit.visceraMap = null; 
//			String anatomyPos  = anatomyCheck.getAnatomyPos();//解剖学所见部位
//			String anatomyFinding = anatomyCheck.getAnatomyFingding();//解剖所见
//			String bodySurfacePos = anatomyCheck.getBodySurfacePos();//体表部位
//			String pos = anatomyCheck.getPos();//位置
//			String shape = anatomyCheck.getShape();//形状
//			String color = anatomyCheck.getColor();//颜色
//			String texture = anatomyCheck.getTexture();//硬度
//			String number = anatomyCheck.getNumber();//数量
//			String range = anatomyCheck.getRange();//范围
//			String lesionDegree = anatomyCheck.getLesionDegree();//病变程度
//			String anatomyFindingStr = "";
//	    	String visceraName = anatomyCheck.getVisceraName();
//	    	if(null!=visceraName){
//	    		anatomyFindingStr = "专题编号："+anatomyCheck.getStudyNo()+" 动物编号："+anatomyCheck.getAnimalCode()+" 脏器："+visceraName+" 解剖所见：";
//	    		if(null != anatomyPos && !anatomyPos.equals("")){
//					anatomyFindingStr = anatomyFindingStr+anatomyPos;
//				}
//				if(null != anatomyFinding && !anatomyFinding.equals("")){
//					anatomyFindingStr = anatomyFindingStr+" "+anatomyFinding;
//				}
//	    	}else{
//	    		anatomyFindingStr = "专题编号："+anatomyCheck.getStudyNo()+" 动物编号："+anatomyCheck.getAnimalCode()+" 解剖所见：";
//	    		if(null != bodySurfacePos && !bodySurfacePos.equals("")){
//					anatomyFindingStr = anatomyFindingStr+" "+bodySurfacePos;
//				}
//	    		if(null != anatomyFinding && !anatomyFinding.equals("")){
//					anatomyFindingStr = anatomyFindingStr+" "+anatomyFinding;
//				}
//	    	}
//		    
//			if(null != pos && !pos.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+pos;
//			}
//			if(null != shape && !shape.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+shape;
//			}
//			if(null != color && !color.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+color;
//			}
//			if(null != texture && !texture.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+texture;
//			}
//			if(null != number && !number.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+number;
//			}
//			if(null != range && !range.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+range;
//			}
//			if(null != lesionDegree && !lesionDegree.equals("")){
//				anatomyFindingStr = anatomyFindingStr+" "+lesionDegree;
//			}
//			anatomyFindingLabel.setText(anatomyFindingStr);
			
			initData11ListView(anatomyCheck);
			okButton.setDisable(false);
		}
	}
	/**脏器选择后更新数据
	 * @param visceraMap
	 * @param anatomyAnimal
	 */
	public void loadDataVisceraSelect(Map<String,Object> visceraMap, TblAnatomyAnimal anatomyAnimal){
		if(null!=visceraMap){
			String visceraCode = (String) visceraMap.get("visceraCode");
			DataEdit_AddEdit.visceraCode = visceraCode;
			bodySufacePosListView.setDisable(true);
			//tongyongRadioButton 被选中（通用所见）
			tongyongRadioButton.setSelected(true);
		}else{
			DataEdit_AddEdit.visceraCode = null;
			data_anatomyPosListView.clear();
			bodySufacePosListView.setDisable(false);
			//tongyongRadioButton 被选中（通用所见）
			tongyongRadioButton.setSelected(true);
		}
		DataEdit_AddEdit.anatomyAnimal = anatomyAnimal;
		DataEdit_AddEdit.visceraMap = visceraMap;
		DataEdit_AddEdit.animalCode = anatomyAnimal.getAnimalCode();
		DataEdit_AddEdit.studyNo = anatomyAnimal.getStudyNo();
		anatomyFindingLabel.setText("");
		okButton.setDisable(false);
		anatomyFindingLabel.setVisible(true);
		updateAnatomyFindingLabelText();
		initData11ListView(null);
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * 保存解剖所见编辑
	 * 
	 * @param event
	 */
	@FXML
	void onRegisterBtnAction(ActionEvent event) {
		String anatomyFinding = anatomyFindingListView.getSelectionModel().getSelectedItem();
		if(null == anatomyFinding){
			showErrorMessage("请先选择解剖学所见！");
			return ;
		}
		if(null == DataEdit_AddEdit.visceraCode && null == bodySufacePosListView.getSelectionModel().getSelectedItem() ){
			DictPathCommon dictPathCommon = null;
			if(tesuRadioButton.isSelected()){
				dictPathCommon = anatomyFinding_tesuDictPathCommonMap.get(anatomyFinding);
			}else{
				dictPathCommon = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFinding);
			}
			Integer specialFlag = dictPathCommon.getSpecicalFlag();
			if(specialFlag == 2){
				showErrorMessage("请先选择脏器！");
				return ;
			}else{
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}
		}
		
		
		String anatomyPosCode = null;
		String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
		if(null != anatomyPos){
			DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
			anatomyPosCode = dictPathCommon1.getItemCode();
		}
		Integer anatomyFindingFlag = 0;
		Integer autolyzaFlag = 0;
		Integer specialFlag = 0;//是否肿瘤，仅针对特殊所见
		String anatomyFindingCode = null;
		if(tesuRadioButton.isSelected()){
			anatomyFindingFlag = 1;
			DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon2.getItemCode();
			specialFlag = dictPathCommon2.getSpecicalFlag();
		}else{
			DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon3.getItemCode();
			specialFlag = dictPathCommon3.getSpecicalFlag();
		}
		
		String currentUserName = SaveUserInfo.getUserName();
		Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		
		TblAnatomyCheckEdit tblAnatomyCheckEdit = new TblAnatomyCheckEdit();
		if(addOrEdit.equals("add")){
			String sessionId = anatomyAnimal.getAnatomySessionId(); //剖检会话id 
			String studyNo = anatomyAnimal.getStudyNo(); 
			String animalCode = anatomyAnimal.getAnimalCode(); 
			if(null!=visceraMap){
				Integer visceraType = (Integer) visceraMap.get("visceraType");
				String visceraCode = (String) visceraMap.get("visceraCode");
				String visceraName = (String) visceraMap.get("visceraName");
				String subVisceraCode = (String) visceraMap.get("subVisceraCode");
				String subVisceraName = (String) visceraMap.get("subVisceraName");
				tblAnatomyCheckEdit.setVisceraType(visceraType);
				tblAnatomyCheckEdit.setVisceraCode(visceraCode);
				tblAnatomyCheckEdit.setVisceraName(visceraName);
				tblAnatomyCheckEdit.setSubVisceraCode(subVisceraCode);
				tblAnatomyCheckEdit.setSubVisceraName(subVisceraName);
				if(specialFlag == 2){
					//判断该脏器是否已经标记自溶
					boolean isHasRecord = BaseService.getInstance().getTblAnatomyCheckService().isHasRecord(sessionId,animalCode,visceraCode,subVisceraCode);
					if(isHasRecord){
						showErrorMessage("该脏器已有异常记录或已标记自溶！");
						return ;
					}
//					boolean isWeighOrFixed = BaseService.getInstance().getTblVisceraFixedService().isFixed(studyNo,animalCode,visceraCode);
//					if(isWeighOrFixed){
//						//已固定
//						if(null == subVisceraCode || "".equals(subVisceraCode)){
//							//1.标记的为主脏器
//							showErrorMessage("该脏器已固定，不可标记为"+anatomyFinding+"！");
//							return ;
//						}else{
//							//2.子脏器是否都已经‘标记缺失’
//							boolean isAllMissing = BaseService.getInstance().getTblVisceraFixedService().isAllMissing(studyNo,animalCode,visceraCode,subVisceraCode);
//							if(isAllMissing){
//								showErrorMessage(visceraName+"已固定，且"+visceraName+"的其他子脏器已标记为缺失或自溶,"+subVisceraName+"不可标记为'"+anatomyFinding+"'！");
//								return ;
//							}else{
//								//未全缺失，
//								if(!Messager.showConfirm("提示", visceraName+"已固定，是否继续把"+subVisceraName+"标记为"+anatomyFinding+"？","")){
//									return ;
//								}
//							}
//							
//						}
//					}
				}
				//判断该脏器是否已经标记自溶
				boolean isHasAutolyze = BaseService.getInstance().getTblAnatomyCheckService().isHasAutolyze(sessionId,animalCode,visceraCode,subVisceraCode);
				if(isHasAutolyze){
					showErrorMessage("该脏器已标记自溶！");
					return ;
				}
				boolean isMissing = BaseService.getInstance().getTblAnatomyCheckService().isMissing(sessionId,animalCode,visceraCode,subVisceraCode);
				if(isMissing){
					showErrorMessage("该脏器已标记缺失！");
					return ;
				}
			}
			
//			String id = BaseService.getInstance().getTblAnatomyCheckService().getKey();
//			tblAnatomyCheck.setId(id);
//			tblAnatomyCheck.setSessionId(sessionId);
			tblAnatomyCheckEdit.setTaskId(taskId);
			tblAnatomyCheckEdit.setStudyNo(studyNo);
			tblAnatomyCheckEdit.setAnimalCode(animalCode);
//			tblAnatomyCheckId = id;
		}else{
			tblAnatomyCheckEdit.setTaskId(taskId);
			tblAnatomyCheckEdit.setStudyNo(tblAnatomyCheck.getStudyNo());
			tblAnatomyCheckEdit.setAnimalCode(tblAnatomyCheck.getAnimalCode());
			tblAnatomyCheckEdit.setVisceraType(tblAnatomyCheck.getVisceraType());
			tblAnatomyCheckEdit.setVisceraCode(tblAnatomyCheck.getVisceraCode());
			tblAnatomyCheckEdit.setVisceraName(tblAnatomyCheck.getVisceraName());
			tblAnatomyCheckEdit.setSubVisceraCode(tblAnatomyCheck.getSubVisceraCode());
			tblAnatomyCheckEdit.setSubVisceraName(tblAnatomyCheck.getSubVisceraName());
			
			tblAnatomyCheckEdit.setOldId(tblAnatomyCheck.getId());
		}
		if(specialFlag == 2){
			autolyzaFlag = 2;
			specialFlag = 0;
		}
		if(autolyzaFlag == 2 && (null == tblAnatomyCheckEdit.getVisceraCode() || "".equals(tblAnatomyCheckEdit.getVisceraCode()))){
			showErrorMessage("请选择脏器！");
			return ;
		}
		
		tblAnatomyCheckEdit.setAnatomyPosCode(anatomyPosCode);
		tblAnatomyCheckEdit.setAnatomyPos(anatomyPos);
		tblAnatomyCheckEdit.setAnatomyFindingFlag(anatomyFindingFlag);
		tblAnatomyCheckEdit.setSpecialFlag(specialFlag);
		tblAnatomyCheckEdit.setAnatomyFindingCode(anatomyFindingCode);
		tblAnatomyCheckEdit.setAnatomyFingding(anatomyFinding);
		tblAnatomyCheckEdit.setAutolyzaFlag(autolyzaFlag);
		String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setBodySurfacePos(bodySurfacePos);
		String pos = posListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setPos(pos);
		String shape = shapeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setShape(shape);
		String color = colorListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setColor(color);
		String texture = textureListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setTexture(texture);
		String number = numberListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setNumber(number);
		String range = rangeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setRange(range);
		String lesionDegree = lesionDegreeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheckEdit.setLesionDegree(lesionDegree);
		String size = sizeTextField.getText();
		tblAnatomyCheckEdit.setSize(size);
		if(addOrEdit.equals("edit")){
			tblAnatomyCheckEdit.setOperator(tblAnatomyCheck.getOperator());
			tblAnatomyCheckEdit.setOperateTime(tblAnatomyCheck.getOperateTime());
			tblAnatomyCheckEdit.setEditType(2);//编辑
		}else{
			tblAnatomyCheckEdit.setOperator(currentUserName);
			tblAnatomyCheckEdit.setOperateTime(currentDate);
			tblAnatomyCheckEdit.setEditType(1);//添加
		}
		tblAnatomyCheckEdit.setEditTime(currentDate);
		
		if(addOrEdit.equals("edit")){
			//签字通过
			if(Sign.openSignWithReasonFrame("编辑原因", "")){
				tblAnatomyCheckEdit.setEditRsn(Sign.getReason());
				
				Json json =  null;
				json = BaseService.getInstance().getTblAnatomyCheckEditService().saveOne(tblAnatomyCheckEdit,SaveUserInfo.getRealName());
				if(json.isSuccess()){
					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					DataEditPage.getInstance().updateAnatomyCheckTable();
					String id = json.getMsg();
					DataEditPage.getInstance().selectRowById(id);
				}else{
					showMessage(json.getMsg());
				}
			}
		}else{
			
			 boolean visceraFixed = false;//脏器是否要固定
			 int specialFlag4 = tblAnatomyCheckEdit.getSpecialFlag();
			 //肿物
			 String visceraCode = tblAnatomyCheckEdit.getVisceraCode();
			 // 肿物（体表部位）
			 if((null == visceraCode ||"".equals(visceraCode)) && specialFlag4 == 1){
				 visceraFixed = true;
				 showMessage("添加解剖所见时，将添加对应的固定记录。");
			 }else {
				 visceraFixed = Messager.showSimpleConfirm("提示", "添加解剖所见时,是否添加对应的固定记录?");
			 }
		 
			//签字通过
			if(Sign.openSignWithReasonFrame("添加原因", "")){
				tblAnatomyCheckEdit.setEditRsn(Sign.getReason());
				
				Json json =  null;
//				json = BaseService.getInstance().getTblAnatomyCheckEditService().saveOne(tblAnatomyCheckEdit);
				if(!visceraFixed){
					json = BaseService.getInstance().getTblAnatomyCheckEditService().saveOne(tblAnatomyCheckEdit);
				}else{
					json = BaseService.getInstance().getTblAnatomyCheckEditService().saveOne_1(tblAnatomyCheckEdit);
				}
				if(json.isSuccess()){
					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					DataEditPage.getInstance().updateAnatomyCheckTable();
					String id = json.getMsg();
					DataEditPage.getInstance().selectRowById(id);
				}else{
					showMessage(json.getMsg());
				}
			}
		}
	}
	
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
}
