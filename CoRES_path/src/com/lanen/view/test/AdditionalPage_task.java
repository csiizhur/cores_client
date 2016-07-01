package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AdditionalPage_task extends Application implements Initializable {
	
	private String studyNo = "";	//专题编号
	private String taskId = "";		//任务编号
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField studyNoText;	//专题编号输入框
	
	
	@FXML
	private Label sdLabel;			//SD   Label
	@FXML
	private Label pathDirectorLabel;//专题病理负责人Label
	@FXML
	private Label animalTypeLabel;	//动物种类Label
	
	@FXML
	private HBox anatomyDateHBox;
	DatePicker  anatomyDatePane=null;
	
	@FXML
	private ComboBox<String> anatomyRsnComboBox;//解剖原因ComboBox
	@FXML
	private ComboBox<String> anatomyPlanNumComboBox;//计划  第几次解剖
	private ObservableList<String> data_anatomyPlanNumComboBox = FXCollections.observableArrayList();
 	
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private Button confirmBtn;
	
	
	/**
	 * 已选动物表格
	 */
	@FXML
	private TableView<Animal> animalTable_select;
	ObservableList<Animal> data_animalTable_select = FXCollections.observableArrayList();
	/**
	 * 已选择的动物编号列表
	 */
	Set<String> selectAnimalCodeSet = new HashSet<String>();
	@FXML
	private TableColumn<Animal,String> dosageDescCol_select;
	@FXML
	private TableColumn<Animal,String> animalCodeCol_select;
	@FXML
	private TableColumn<Animal,String> genderCol_select;
	@FXML
	private TableColumn<Animal,String> deadDateCol;
	
	/**
	 * 未选动物表格
	 */
	@FXML
	private TableView<Animal> animalTable_dosage;
	ObservableList<Animal> data_animalTable_dosage = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Animal,String> dosageDescCol_dosage;
	@FXML
	private TableColumn<Animal,String> animalCodeCol_dosage;
	@FXML
	private TableColumn<Animal,String> genderCol_dosage;
	
	private static AdditionalPage_task instance;
	public static AdditionalPage_task getInstance(){
		if(null == instance){
			instance = new AdditionalPage_task();
		}
		return instance;
	}
	
	public AdditionalPage_task() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化studyNoText
		initStudyNoText();
		//初始化日期选择器
		initDatePane();
		//初始化解剖次数combobox
		initAnatomyPlanNumComboBox();
		//初始化解剖原因combobox
		initAnatomyRsnComboBox();
		
		initAnimalTable_select();
		
		initAnimalTable_dosage();
	}
	
	/**
	 * 初始化未选动物列表
	 */
	private void initAnimalTable_dosage() {
		animalTable_dosage.setItems(data_animalTable_dosage);
		animalTable_dosage.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol_dosage.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol_dosage.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol_dosage.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		//动物数量居中,选中设置行颜色
		genderCol_dosage.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
			@Override
			public TableCell<Animal, String> call(
					TableColumn<Animal, String> param) {
				TableCell<Animal, String> cell =
						new TableCell<Animal, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
//			    				 Animal anatomyTask = getTableView().getItems().get(getIndex());
						Animal anatomyTask = (Animal) getTableRow().getItem();
						if(null != anatomyTask){
							if(anatomyTask.getSelect()){
//			    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
								getTableRow().setStyle("-fx-background-color:#0092DC;");
							}else{
								getTableRow().setStyle("");
							}
						}
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
		//单击事件
		animalTable_dosage.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<Animal> table = (TableView<Animal>) event.getSource();
					Animal obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						Animal obj2 = new Animal(!obj.getSelect(),obj.getDosageDesc(),
								obj.getAnimalCode(),obj.getGender(),obj.getDosageNum(),obj.getDeadDate());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}
			
		});
	}
	
	/**
	 * 初始化已选动物列表
	 */
	private void initAnimalTable_select() {
		animalTable_select.setItems(data_animalTable_select);
		animalTable_select.setEditable(true);
//		animalTable_select.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		
		genderCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		//TODO
		deadDateCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("deadDate"));
		Callback<TableColumn<Animal,String>,TableCell<Animal,String>> cellFactory = new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){

			@SuppressWarnings("rawtypes")
			@Override
			public TableCell call(TableColumn p) {
				
				return new TextFieldTableCellImpl();
			}
			
		};
		deadDateCol.setCellFactory(cellFactory);
		//更新值
		deadDateCol.setOnEditCommit(new EventHandler<CellEditEvent<Animal,String>>(){

			@Override
			public void handle(CellEditEvent<Animal, String> t) {
				((Animal) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDeadDate(t.getNewValue());
			}});
		
		
		
		
		
		//动物数量居中,选中设置行颜色
		genderCol_select.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
			@Override
			public TableCell<Animal, String> call(
					TableColumn<Animal, String> param) {
				TableCell<Animal, String> cell =
						new TableCell<Animal, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
//			    				 Animal anatomyTask = getTableView().getItems().get(getIndex());
						Animal anatomyTask = (Animal) getTableRow().getItem();
						if(null != anatomyTask){
							if(anatomyTask.getSelect()){
//			    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
								getTableRow().setStyle("-fx-background-color:#0092DC;");
							}else{
								getTableRow().setStyle("");
							}
						}
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
		//单击事件
		animalTable_select.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<Animal> table = (TableView<Animal>) event.getSource();
					Animal obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						Animal obj2 = new Animal(!obj.getSelect(),obj.getDosageDesc(),
								obj.getAnimalCode(),obj.getGender(),obj.getDosageNum(),obj.getDeadDate());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}
			
		});
	}
	
	/**
	 * 初始化解剖原因combobox
	 */
	private void initAnatomyRsnComboBox() {
		anatomyRsnComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null != newValue && newValue.equals("计划解剖")){
					anatomyPlanNumComboBox.setVisible(true);
				}else{
					anatomyPlanNumComboBox.getSelectionModel().clearSelection();
					anatomyPlanNumComboBox.setVisible(false);
				}
			}
			
		});
	}

	/**
	 * 初始化解剖次数combobox
	 */
	private void initAnatomyPlanNumComboBox() {
		anatomyPlanNumComboBox.setItems(data_anatomyPlanNumComboBox);
		anatomyPlanNumComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					//选择了解剖次数
					if(null == taskId || "".equals(taskId)){
						if(null != studyNo && !"".equals(studyNo)){
							//更新已选动物列表（计划解剖的）
							// 更新已选动物表，根据当前专题编号及计划第几次解剖
							updateAimalTable_select(studyNo,newValue.intValue()+1);
							//更新待选 动物表，根据当前studyNo(填充时，去除已选动物)
							updateAnimalTable_dosage(studyNo,taskId);
						}
					}
				}else{
					
				}
			}

			
		});
	}

	/**
	 * 初始化studyNoComboBox
	 */
	private void initStudyNoText() {
		studyNoText.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(null != newValue && !newValue){
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							//根据课题编号，更新SD，主题病理负责人，动物种类
							updateBaseinfoByStudyNo_new();
						}
						
					});
				}
			}

		});
	}
	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		anatomyDatePane = new DatePicker(Locale.CHINA);
		anatomyDatePane.getTextField().setEditable(false);
		anatomyDatePane.getTextField().setFocusTraversable(true);
		anatomyDatePane.getTextField().setMaxWidth(105);
		anatomyDatePane.getTextField().setMinWidth(105);
		anatomyDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		anatomyDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		anatomyDatePane.getCalendarView().setShowWeeks(false);
		anatomyDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		anatomyDatePane.setMaxWidth(105);
		anatomyDateHBox.getChildren().add(anatomyDatePane);
		anatomyDatePane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
	}
	
	/**
	 * 根据课题编号，更新SD，主题病理负责人，动物种类
	 */
	private void updateBaseinfoByStudyNo_new() {
		String currentStudyNo = null;
		TblStudyPlan currentStudyPlan = null;	//
		
		currentStudyNo = studyNoText.getText().trim();
		if("".equals(currentStudyNo)){
			resetData();
			studyNoText.requestFocus();
		}else if(!studyNo.equals(currentStudyNo)){
			studyNo = currentStudyNo;
			//根据专题编号查询实体，若专题编号不存在，则返回null，若查询的实体无动物（无动物种类）或无病理计划则返回null
			currentStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo2(currentStudyNo);
			
			//有改变，
			updateState(currentStudyPlan);
			
		}
	}
	
	/**
	 * 有改变，更新数据
	 */
	private void updateState(TblStudyPlan tblStudyPlan){
		if(null != tblStudyPlan){
			String sd = BaseService.getInstance().getTblStudyPlanService().getSDByStudyNo(tblStudyPlan.getStudyNo());
			String pathSD = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(tblStudyPlan.getStudyNo());
			if(null != sd){
				sdLabel.setText(sd);
			}else{
				sdLabel.setText("");
			}
			pathDirectorLabel.setText(pathSD);
			animalTypeLabel.setText(tblStudyPlan.getAnimalType());
			
			addBtn.setDisable(false);
			delBtn.setDisable(false);
			confirmBtn.setDisable(false);
			
			//更新已选动物表，根据当前任务Id 
			updateAnimalTable_select(taskId);
			updateAnimalTable_dosage(studyNo,taskId);
			
			updateAnatomyPlanNumCombobox(studyNo);
			
		}else{
			sdLabel.setText("");
			pathDirectorLabel.setText("");
			animalTypeLabel.setText("");
			anatomyRsnComboBox.getSelectionModel().clearSelection();
			anatomyPlanNumComboBox.getSelectionModel().clearSelection();
			anatomyPlanNumComboBox.setVisible(false);
			addBtn.setDisable(true);
			delBtn.setDisable(true);
			confirmBtn.setDisable(true);
			data_animalTable_select.clear();
			selectAnimalCodeSet.clear();
			data_animalTable_dosage.clear();
			
			showErrorMessage("该专题编号不存在或该专题无病理计划！");
			studyNoText.requestFocus();
			
		}
	}
	
	/**更新解剖计划combobox
	 * @param studyNo2
	 */
	private void updateAnatomyPlanNumCombobox(String studyNo) {
		data_anatomyPlanNumComboBox.clear();
		List<Integer> anatomyPlanNumList = BaseService.getInstance().getTblAnatomyTaskService().getAnatomyPlanNumListByStudyNo(studyNo);
		if(null != anatomyPlanNumList && anatomyPlanNumList.size() > 0){
			for(Integer anatomyPlanNum:anatomyPlanNumList){
				data_anatomyPlanNumComboBox.add("第"+anatomyPlanNum+"次解剖");
			}
		}
	}

	/**更新待选 动物表(包括当前任务下的)，根据当前studyNo(填充时，去除已选动物)
	 * @param studyNo2
	 */
	private void updateAnimalTable_dosage(String studyNo,String taskId) {
		data_animalTable_dosage.clear();
		if(null != studyNo && !"".equals(studyNo)){
			List<Map<String,Object>> animalMapList = null;
			if(null == taskId || "".equals(taskId)){
				//查询该专题下的待选动物列表（去除1:已提交解剖申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表），补录解剖数据用
				animalMapList = BaseService.getInstance().getTblAnatomyTaskService().getNoSelectAnimalByStudyNo(studyNo);
			}else{
				//查询该专题下的待选动物列表（去除1:已提交解剖申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表(当前任务除外)），补录解剖数据用
				animalMapList = BaseService.getInstance().getTblAnatomyTaskService().getNoSelectAnimalByStudyNoTaskId(studyNo,taskId);
			}
			if(null != animalMapList && animalMapList.size() > 0){
				for(Map<String, Object> obj:animalMapList){
//					animalCode,gender,dosageNum,dosageDesc
					String animalCode = (String) obj.get("animalCode");
					if(!selectAnimalCodeSet.contains(animalCode)){
						String dosageDesc = (String) obj.get("dosageDesc");
						Integer gender = (Integer) obj.get("gender");
						Integer dosageNum = (Integer) obj.get("dosageNum");
						data_animalTable_dosage.add(new Animal(false,dosageDesc,animalCode,gender,dosageNum,""));
					}
				}
			}
		}
		
	}

	/**更新已选动物表，根据当前任务Id
	 * @param taskId2
	 */
	private void updateAnimalTable_select(String taskId) {
		data_animalTable_select.clear();
		selectAnimalCodeSet.clear();
		if(null != taskId && !"".equals(taskId)){
			//查询该任务下已选动物列表，补录解剖数据专用
			List<Map<String,Object>> animalMapList = BaseService.getInstance().getTblAnatomyTaskService().getSelectAnimalByTaskId(taskId);
			if(null != animalMapList && animalMapList.size() > 0){
				for(Map<String, Object> obj:animalMapList){
//					animalCode,gender,dosageNum,dosageDesc
					String animalCode = (String) obj.get("animalCode");
						String dosageDesc = (String) obj.get("dosageDesc");
						Integer gender = (Integer) obj.get("gender");
						Integer dosageNum = (Integer) obj.get("dosageNum");
						String deadDate = (String) obj.get("deadDate");
						if(null == deadDate || "".equals(deadDate)){
							deadDate = DateUtil.getNow("yyyy-MM-dd");
						}
						data_animalTable_select.add(new Animal(false,dosageDesc,animalCode,gender,dosageNum,deadDate));
						selectAnimalCodeSet.add(animalCode);
				}
			}
		}
	}
	
	/**更新已选动物表，根据当前专题编号及计划第几次解剖
	 * @param studyNo
	 * @param i
	 */
	private void updateAimalTable_select(String studyNo, int i) {
		data_animalTable_select.clear();
		selectAnimalCodeSet.clear();
		if(null != studyNo && !"".equals(studyNo) && i > 0){
			//查询解剖计划下的动物列表已选动物列表（去除1:已提交解剖申请的对应动物列表（动物有去除的除外） 2：补录任务对应的动物列表），补录解剖数据专用
			List<Map<String,Object>> animalMapList = BaseService.getInstance().getTblAnatomyTaskService().getSelectAnimalByStudyNoAnatomyPlanNum(studyNo,i);
			if(null != animalMapList && animalMapList.size() > 0){
				for(Map<String, Object> obj:animalMapList){
//					animalCode,gender,dosageNum,dosageDesc
					String animalCode = (String) obj.get("animalCode");
						String dosageDesc = (String) obj.get("dosageDesc");
						Integer gender = (Integer) obj.get("gender");
						Integer dosageNum = (Integer) obj.get("dosageNum");
						
						String deadDate = anatomyDatePane.getTextField().getText();
						data_animalTable_select.add(new Animal(false,dosageDesc,animalCode,gender,dosageNum,deadDate));
						selectAnimalCodeSet.add(animalCode);
				}
			}
		}
	}

	/**
	 * 鼠标单击面板时间
	 */
	@FXML
	public void onMouseClicked(MouseEvent event ){
		AnchorPane anchorPane = (javafx.scene.layout.AnchorPane) event.getSource();
		anchorPane.requestFocus();
	}
	/**
	 * 专题编号onAction
	 */
	@FXML
	public void onStudyNoTextAction(ActionEvent event ){
		anchorPane.requestFocus();
	}
	
	/**
	 * ADD 按钮事件（+）
	 * 
	 * @param event
	 */
	@FXML
	private void onAddAction(ActionEvent event) {
		//1.查找待添加动物
		List<Animal> selectAnimalList = new ArrayList<Animal>();
		List<String> selectAnimalCodeList = new ArrayList<String>();
		
		for(Animal obj:data_animalTable_dosage){
			if(obj.getSelect()){
				selectAnimalList.add(obj);
				selectAnimalCodeList.add(obj.getAnimalCode());
			}
		}
		
		if(selectAnimalList.size() < 1){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//2.已选动物列表的全不选
		onUnSelectAllAction_selectAnimal(null);
		//3.添加动物
		String deadDate = anatomyDatePane.getTextField().getText();
		for(Animal obj:selectAnimalList){
			selectAnimalCodeSet.add(obj.getAnimalCode());
			data_animalTable_select.add(new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getDosageNum(),deadDate));
		}
		//4.删除待选
		updateAnimalTable_dosage(studyNo,taskId);
		//5.排序
		Collections.sort(data_animalTable_select, new Comparator<Animal>(){

			@Override
			public int compare(Animal o1, Animal o2) {
				if(NumberValidationUtils.isPositiveInteger(o1.getAnimalCode()) &&
						NumberValidationUtils.isPositiveInteger(o2.getAnimalCode())	){
					return Integer.parseInt(o1.getAnimalCode()) -Integer.parseInt(o2.getAnimalCode());
				}
				return 0;
			}
			
		});
		
	}
	
	/**
	 * DEL按钮事件（-）
	 * 
	 * @param event
	 */
	@FXML
	private void onDelAction(ActionEvent event) {
		
		//1.查找待删除动物
		List<Animal> selectAnimalList = new ArrayList<Animal>();
		List<String> selectAnimalCodeList = new ArrayList<String>();
		for(Animal obj:data_animalTable_select){
			if(obj.getSelect()){
				selectAnimalList.add(obj);
				selectAnimalCodeList.add(obj.getAnimalCode());
			}
		}
		if(selectAnimalList.size() < 1){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//2.检查删除的动物是否有解剖或脏器称重信息(有解剖或称重信息，返回 false 及Msg  ，没有返回true)
		Json json = BaseService.getInstance().getTblAnatomyTaskService().checkAnatomyOrWeigh(studyNo,selectAnimalCodeList); 
		if(!json.isSuccess()){
			showErrorMessage(json.getMsg());
			return;
		}
		//3.删除动物
		data_animalTable_select.removeAll(selectAnimalList);
		//4.清除  selectAnimalCodeSet中对应动物编号
		for(Animal obj:selectAnimalList){
			selectAnimalCodeSet.remove(obj.getAnimalCode());
		}
		//5.更新任务动物表和剂量动物表
		updateAnimalTable_dosage(studyNo,taskId);
		
		//6.选中删除项
		animalTalbeSelectAllByAnimalCodelList(animalTable_dosage,selectAnimalCodeList);
		
	}
	/**动物表格选择对应动物编号
	 * @param animalTable
	 * @param selectAnimalCodeList
	 */
	private void animalTalbeSelectAllByAnimalCodelList(TableView<Animal> animalTable, List<String> selectAnimalCodeList){
		ObservableList<Animal> items = animalTable.getItems();
		if(null != items && items.size() > 0){
			int i = 0;
			for(Animal obj:items){
				if(selectAnimalCodeList.contains(obj.getAnimalCode())){
					items.set(i, new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getDosageNum(),obj.getDeadDate()));
				}
				i++;
			}
		}
		animalTableScrollToSelectIndex(animalTable);
	}
	
	/**滚动到最后的选行
	 * @param animalTable
	 */
	private void animalTableScrollToSelectIndex(TableView<Animal> animalTable){
		ObservableList<Animal> items = animalTable.getItems();
		
		if(null != items && items.size() > 0){
			int index = 0;
			int selectIndex = 0;
			for(Animal obj:items){
				if(obj.getSelect()){
					selectIndex = index;
				}
				index++;
			}
			animalTable.scrollTo(selectIndex);
		}
	}
	
	/**全不选_已选动物
	 * @param event
	 */
	private void onUnSelectAllAction_selectAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable_select2 = FXCollections.observableArrayList(data_animalTable_select);
		data_animalTable_select.clear();
		if(null != data_animalTable_select2 && data_animalTable_select2.size() > 0){
			for(Animal obj:data_animalTable_select2){
				data_animalTable_select.add(new Animal(false,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getDosageNum(),obj.getDeadDate()));
			}
		}
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		//1.基本信息
		String sd = sdLabel.getText().trim();
		if("".equals(sd)){
			showErrorMessage("请输入专题编号！");
			studyNoText.requestFocus();
			return ;
		}
		String anatomyDateStr = anatomyDatePane.getTextField().getText();
		Date anatomyDate = DateUtil.stringToDate(anatomyDateStr, "yyyy-MM-dd");
		String  anatomyRsn= anatomyRsnComboBox.getSelectionModel().getSelectedItem();
		int deadFlag = anatomyRsnComboBox.getSelectionModel().getSelectedIndex()+1;
		if(null == anatomyRsn || "".equals(anatomyRsn)){
			showErrorMessage("请选择解剖原因！");
			anatomyRsnComboBox.requestFocus();
			return ;
		}
		int isPlan = 0;//是否计划解剖
		int anatomyPlanNum = 0;	//计划第几次解剖
		if(anatomyRsn.equals("计划解剖")){
			isPlan = 1;
			int selectedIndex = anatomyPlanNumComboBox.getSelectionModel().getSelectedIndex();
			if(selectedIndex == -1){
				showErrorMessage("请选择第几次解剖！");
				anatomyPlanNumComboBox.requestFocus();
				return ;
			}else{
				anatomyPlanNum = selectedIndex + 1;
			}
		}
		//2.动物列表
		//动物编号列表
		if(data_animalTable_select.size() < 1){
			showErrorMessage("请选择动物！");
			return ;
		}
		//3.准备数据
		String userName = SaveUserInfo.getUserName();
		if(null == taskId || "".equals(taskId)){
			//3.1新建 
			TblAnatomyTask task = new TblAnatomyTask();
			task.setStudyNo(studyNo);
//			task.setAnatomyNum(anatomyNum);解剖序号
			task.setIsplan(isPlan);
			task.setAnatomyPlanNum(anatomyPlanNum);
			task.setAnatomyRsn(anatomyRsn);
			task.setTaskCreater(userName);
//			task.setTaskCreateTime(taskCreateTime);任务创建时间
			task.setTempFlag(2);//补录解剖任务
			
			//解剖动物列表
			List<TblAnatomyAnimal> tblAnatomyAnimalList = new ArrayList<TblAnatomyAnimal>();
			TblAnatomyAnimal tblAnatomyAnimal = null;
			for(Animal obj:data_animalTable_select){
				tblAnatomyAnimal = new TblAnatomyAnimal();
				tblAnatomyAnimal.setStudyNo(studyNo);
				tblAnatomyAnimal.setAnimalCode(obj.getAnimalCode());
				tblAnatomyAnimal.setGroupId(obj.getDosageNum());
				tblAnatomyAnimal.setGender(obj.getGender().equals("♂") ? 1:2);
				tblAnatomyAnimal.setAnatomyPlanNum(anatomyPlanNum);
//				tblAnatomyAnimal.setTaskId("");任务Id
//				tblAnatomyAnimal.setDeadFlag(1);
				tblAnatomyAnimal.setDeadFlag(deadFlag);
				Date deadDate = DateUtil.stringToDate(obj.getDeadDate(), "yyyy-MM-dd");
				tblAnatomyAnimal.setDeadDate(deadDate );
				
				tblAnatomyAnimal.setDeadRsn(anatomyRsn);
				tblAnatomyAnimal.setAnatomyBeginTime(anatomyDate);
				tblAnatomyAnimal.setAnatomyEndTime(anatomyDate);
				tblAnatomyAnimal.setVisceraWeighFinishTime(anatomyDate);
				
				tblAnatomyAnimalList.add(tblAnatomyAnimal);
			}
			
			//4.1保存
			taskId = BaseService.getInstance().getTblAnatomyTaskService().saveAll(task,tblAnatomyAnimalList);
		}else{
			//3.2编辑保存
			TblAnatomyTask task = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
//			task.setStudyNo(studyNo);
//			task.setAnatomyNum(anatomyNum);解剖序号
			task.setIsplan(isPlan);
			task.setAnatomyPlanNum(anatomyPlanNum);
			task.setAnatomyRsn(anatomyRsn);
//			task.setTaskCreater(userName);
//			task.setTaskCreateTime(taskCreateTime);任务创建时间
//			task.setTempFlag(2);//补录解剖任务
			
			//解剖动物列表
			List<TblAnatomyAnimal> tblAnatomyAnimalList = new ArrayList<TblAnatomyAnimal>();
			TblAnatomyAnimal tblAnatomyAnimal = null;
			for(Animal obj:data_animalTable_select){
				tblAnatomyAnimal = new TblAnatomyAnimal();
				tblAnatomyAnimal.setStudyNo(studyNo);
				tblAnatomyAnimal.setAnimalCode(obj.getAnimalCode());
				tblAnatomyAnimal.setGroupId(obj.getDosageNum());
				tblAnatomyAnimal.setGender(obj.getGender().equals("♂") ? 1:2);
				tblAnatomyAnimal.setAnatomyPlanNum(anatomyPlanNum);
				tblAnatomyAnimal.setTaskId(taskId);//任务Id
//				tblAnatomyAnimal.setDeadFlag(1);
				tblAnatomyAnimal.setDeadFlag(deadFlag);
				Date deadDate = DateUtil.stringToDate(obj.getDeadDate(), "yyyy-MM-dd");
				tblAnatomyAnimal.setDeadDate(deadDate );
				
				tblAnatomyAnimal.setDeadRsn(anatomyRsn);
				tblAnatomyAnimal.setAnatomyBeginTime(anatomyDate);
				tblAnatomyAnimal.setAnatomyEndTime(anatomyDate);
				tblAnatomyAnimal.setVisceraWeighFinishTime(anatomyDate);
				
				tblAnatomyAnimalList.add(tblAnatomyAnimal);
			}
			//4.2更新
			BaseService.getInstance().getTblAnatomyTaskService().updateAll(task,tblAnatomyAnimalList);
		}
		
		//5.更新任务列表（补录任务主窗口）
		AdditionalPage.getInstance().updateStudyNoComboBoxAndSelectTask(studyNo, taskId);
		//6.关闭窗口
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据（新建时，taskId 为“”或null）
	 */
	public void loadData(String studyNo,String taskId) {
		//1.重置
		resetData();
		this.taskId = taskId;
		studyNoText.setText(studyNo);
		//2.
		//根据课题编号，更新SD，主题病理负责人，动物种类以及动物列表（有taskId时，更新已选动物列表）
		updateBaseinfoByStudyNo_new();
		
		//编辑时，专题编号不可以重新输入
		if(null != taskId && !"".equals(taskId)){
			//3.专题编号不可编辑
			studyNoText.setDisable(true);
			//4.解剖原因及第几次解剖
			TblAnatomyTask anatomytask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
			String anatomyRsn = anatomytask.getAnatomyRsn();
			anatomyRsnComboBox.getSelectionModel().select(anatomyRsn);
			if("计划解剖".equals(anatomyRsn)){
				anatomyPlanNumComboBox.getSelectionModel().select(anatomytask.getAnatomyPlanNum()-1);
			}
			//5.已签字确认，不可以编辑
			if(null != anatomytask.getAnatomyCheckFinishSign() && !"".equals(anatomytask.getAnatomyCheckFinishSign())){
				confirmBtn.setDisable(true);
				addBtn.setDisable(true);
				delBtn.setDisable(true);
			}
		}
		animalTable_select.requestFocus();
	}

	/**
	 * 重置界面数据值
	 */
	private void resetData() {
		this.taskId = "";
		this.studyNo = "";
		studyNoText.setDisable(false);
		studyNoText.clear();
		sdLabel.setText("");
		pathDirectorLabel.setText("");
		animalTypeLabel.setText("");
		anatomyRsnComboBox.getSelectionModel().clearSelection();
		anatomyPlanNumComboBox.getSelectionModel().clearSelection();
		anatomyPlanNumComboBox.setVisible(false);
		addBtn.setDisable(true);
		delBtn.setDisable(true);
		confirmBtn.setDisable(true);
		data_animalTable_select.clear();
		selectAnimalCodeSet.clear();
		data_animalTable_dosage.clear();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AdditionalPage_task.fxml"));
		Scene scene = new Scene(root, 829, 467);
		stage.setScene(scene);
		stage.setTitle("补录解剖任务");
//		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
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
	/**动物
	 * @author Administrator
	 *
	 */
	public class Animal{
		private BooleanProperty select;
		private StringProperty dosageDesc;
		private StringProperty animalCode;
		private StringProperty gender;
		private IntegerProperty dosageNum;
		private StringProperty deadDate;
		
		public Animal(){}
		public Animal(boolean select,String dosageDesc,String animalCode,int gender,int dosageNum,String deadDate){
			setSelect(select);
			setDosageDesc(dosageDesc);
			setAnimalCode(animalCode);
			setGender(gender == 1 ? "♂":"♀" );
			setDosageNum(dosageNum);
			setDeadDate(deadDate);
		}
		public Animal(boolean select,String dosageDesc,String animalCode,String gender,int dosageNum,String deadDate){
			setSelect(select);
			setDosageDesc(dosageDesc);
			setAnimalCode(animalCode);
			setGender(gender);
			setDosageNum(dosageNum);
			setDeadDate(deadDate);
		}
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getGender() {
			return gender.get();
		}
		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}
		public String getDosageDesc() {
			return dosageDesc.get();
		}
		public void setDosageDesc(String dosageDesc) {
			this.dosageDesc = new SimpleStringProperty(dosageDesc);
		}
		public Integer getDosageNum() {
			return dosageNum.get();
		}
		public void setDosageNum(Integer dosageNum) {
			this.dosageNum = new SimpleIntegerProperty(dosageNum);
		}
		public String getDeadDate() {
			return deadDate.get();
		}
		public void setDeadDate(String deadDate) {
			this.deadDate = new SimpleStringProperty(deadDate);
		}
		
	}
	public  class TextFieldTableCellImpl extends TableCell<Animal,String>{
		
		private DatePicker  deadDatePane;
		private String oldVaule;
		public TextFieldTableCellImpl(){
		}
		
		@Override
		public void startEdit() {
			super.startEdit();
			if(deadDatePane ==null){
				createTextField();
			}
			setText(null);
			setGraphic(deadDatePane);
//			deadDatePane.getTextField().requestFocus();
		}
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			
			setText((String)getItem());
			setGraphic(null);
		}


		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if(empty){
				setText(null);
				setGraphic(null);
			}else{
				if(isEditing()){
					if(deadDatePane.getTextField() != null){
						deadDatePane.getTextField().setText(getString());
					}
					setText(null);
					setGraphic(deadDatePane);
				}else{
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField(){
			oldVaule=getString();
			if(null == deadDatePane){
				deadDatePane = new DatePicker(Locale.CHINA);
				deadDatePane.getTextField().setEditable(false);
				deadDatePane.getTextField().setFocusTraversable(true);
				deadDatePane.getTextField().setMaxWidth(105);
				deadDatePane.getTextField().setMinWidth(105);
				deadDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				deadDatePane.getCalendarView().todayButtonTextProperty().set("今天");
				deadDatePane.getCalendarView().setShowWeeks(false);
				deadDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
				deadDatePane.setMaxWidth(105);
				deadDatePane.getTextField().setMinHeight(19);
				
//				deadDatePane.focusedProperty().addListener(new ChangeListener<Boolean>(){
//
//					@Override
//					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
//							Boolean newValue) {
//						if(null !=newValue && !newValue){
//							deadDatePane.hidePopup();
//						}
//						
//					}});
				deadDatePane.setOnKeyReleased(new EventHandler<KeyEvent>(){
					
					@Override
					public void handle(KeyEvent t) {
						if(t.getCode()==KeyCode.ENTER){
//							if(!oldVaule.equals(deadDatePane.getTextField().getText())){
								commitEdit(deadDatePane.getTextField().getText());
//							}else{
//								cancelEdit();
//							}
						}else if(t.getCode()==KeyCode.ESCAPE){
							cancelEdit();
						}
					}
					
				});
				deadDatePane.getCalendarView().setOnKeyReleased(new EventHandler<KeyEvent>(){
					
					@Override
					public void handle(KeyEvent t) {
						if(t.getCode()==KeyCode.ENTER){
//							if(!oldVaule.equals(deadDatePane.getTextField().getText())){
								commitEdit(deadDatePane.getTextField().getText());
//							}else{
//								cancelEdit();
//							}
						}else if(t.getCode()==KeyCode.ESCAPE){
							cancelEdit();
						}
					}
					
				});
//			deadDatePane.setOnMouseExited(new EventHandler<MouseEvent>(){
//
//				@Override
//				public void handle(MouseEvent arg0) {
//					if(!oldVaule.equals(deadDatePane.getTextField().getText())){
//						commitEdit(deadDatePane.getTextField().getText());
//					}else{
//						cancelEdit();
//					}
//				}
//			});
				deadDatePane.getCalendarView().setOnMouseExited(new EventHandler<MouseEvent>(){
					
					@Override
					public void handle(MouseEvent arg0) {
//						if(!oldVaule.equals(deadDatePane.getTextField().getText())){
							commitEdit(deadDatePane.getTextField().getText());
//						}else{
//							cancelEdit();
//						}
					}
				});
//				deadDatePane.getTextField().focusedProperty().addListener(new ChangeListener<Boolean>(){
//
//					@Override
//					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
//							Boolean newValue) {
//						if(null != newValue && newValue){
//							deadDatePane.showPopup();
//						}
//						
//					}});
			}
//			deadDatePane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
				deadDatePane.getTextField().setText(oldVaule);
			
			
		}
		private String getString(){
			return getItem()==null ? "":getItem().toString();
		}
	}
}
