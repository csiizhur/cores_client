package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblHistopathCheckContentIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**镜检内容组合page
 * @author Administrator
 *
 */
public class HistopathCheckContent extends Application implements Initializable {
	
	
	/**
	 * 专题编号
	 */
	private String studyNo = "";
	
	@FXML
	private Label studyNoLabel;         //专题编号
	@FXML                               //
	private Label animalTypeLabel;      //动物种类
	@FXML                               //
	private Label sdLabel;         		//sd
	@FXML                               //
	private Label pathSDLabel;          //病理专题负责人
	@FXML                               //
	private Button saveBtn;
	@FXML                               //
	private Button delBtn;
	/**
	 * 点击tab上表格后，动物列表才切换(及切换到那个tab且点击了表格)
	 */
	private int no_animalFromTab = 0;		//动物来自哪个tab  0　1  2
	
	/**
	 * 切换到哪个tab
	 */
	private int no_currentTab = 0;		//动物来自哪个tab  0　1  2
	
	@FXML
	private TabPane tabPane;			//TabPane
	
	/**
	 * 解剖计划
	 */
	@FXML
	private TableView<DissectPlan> dissectPlanTable;
	ObservableList<DissectPlan> data_dissectPlanTable = FXCollections.observableArrayList();
	/**
	 * 解剖计划动物列表,dissectNum,animalCode,gender,dosageDesc,anatomyDate(打开窗口时更新，后不变)
	 */
	List<Map<String,Object>> dissectPlanAnimaMaplList = new ArrayList<Map<String,Object>>();
	@FXML
	private TableColumn<DissectPlan,String> dissectNumCol;
	@FXML
	private TableColumn<DissectPlan,String> animalNumCol_0;
	@FXML
	private TableColumn<DissectPlan,String> beginDateCol;
	@FXML
	private TableColumn<DissectPlan,String> endDateCol;
	
	/**
	 * 解剖任务
	 */
	@FXML
	private TableView<AnatomyTask> anatomyTaskTable;
	ObservableList<AnatomyTask> data_anatomyTaskTable = FXCollections.observableArrayList();
	/**
	 * 解剖动物列表（专题下所有解剖动物）,taskId,animalCode,gender,dosageDesc,anatomyDate(打开窗口时更新，后不变)
	 */
	List<Map<String,Object>> taskAnimaMaplList = new ArrayList<Map<String,Object>>();
	
	@FXML
	private TableColumn<AnatomyTask,String> planAnatomyDateCol;
	@FXML
	private TableColumn<AnatomyTask,String> animalNumCol;
	@FXML
	private TableColumn<AnatomyTask,String> reasonCol;
	
	/**
	 * 组织取材批次表
	 */
	@FXML
	private TableView<Batch> batchTable;
	ObservableList<Batch> data_batchTable = FXCollections.observableArrayList();
	/**
	 * 组织取材批次动物列表,batchId,animalCode,gender,dosageDesc,anatomyDate(打开窗口时更新，后不变)
	 */
	List<Map<String,Object>> batchAnimaMaplList = new ArrayList<Map<String,Object>>();
	
	@FXML
	private TableColumn<Batch,String> batchSnCol;
	@FXML
	private TableColumn<Batch,String> animalNumCol_2;
	@FXML
	private TableColumn<Batch,String> createTimeCol;
	@FXML
	private TableColumn<Batch,String> sliceTypeCol;
	
	@FXML
	private ComboBox<String> comboBox;
	ObservableList<String> data_comboBox = FXCollections.observableArrayList();
	ChangeListener<Number> comboBoxChangeListener = null;
	/**
	 * 组合索引Id列表
	 */
	List<String> contentIndexIdList = new ArrayList<String>();
	
	/**
	 * 已选动物表格
	 */
	@FXML
	private TableView<Animal> animalTable;
	ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Animal,Boolean> selectCol;
	@FXML
	private TableColumn<Animal,String> dosageDescCol;
	@FXML
	private TableColumn<Animal,String> animalCodeCol;
	@FXML
	private TableColumn<Animal,String> genderCol;
	@FXML
	private TableColumn<Animal,String> anatomyDateCol;
	
//	public class ContentSlice{
//		private BooleanProperty select;
//		private StringProperty tissueSliceSnId;
//		private StringProperty sliceCode;
//		private StringProperty visceraOrTissueName;
//		private StringProperty codeDate;
//		private StringProperty animalCode;
	
	/**
	 * 切片列表
	 */
	@FXML
	private TableView<ContentSlice> sliceTable;
	ObservableList<ContentSlice> data_sliceTable = FXCollections.observableArrayList();
	/**
	 * 存放非常规列表
	 */
	ObservableList<ContentSlice> data_sliceTable_fcg = FXCollections.observableArrayList();
	@FXML
	private TableColumn<ContentSlice,Boolean> selectCol_slice;
	@FXML
	private TableColumn<ContentSlice,String> sliceCodeCol;
	@FXML
	private TableColumn<ContentSlice,String> visceraOrTissueNameCol;
	@FXML
	private TableColumn<ContentSlice,String> codeDateCol;
	
	private static HistopathCheckContent instance;
	public static HistopathCheckContent getInstance(){
		if(null == instance){
			instance = new HistopathCheckContent();
		}
		return instance;
	}

	public HistopathCheckContent() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//1.初始化tabPane
		initTabPane();
		//2.初始化解剖计划表
		initDissectPlanTable();
		//3.初始化解剖任务表
		initAnatomyTaskTable();
		//4.初始化组织取材批次表
		initBatchTable();
		//5.初始化已选动物列表
		initAnimalTable();
		//6.初始化切片列表
		initSliceTable();
		//7.初始化ComboBox
		initComboBox();
	}
	
	/**
	 * 初始化ComboBox
	 */
	private void initComboBox() {
		comboBox.setItems(data_comboBox);
		comboBoxChangeListener = new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue && newValue.intValue() > -1){
					String contentIndexId = contentIndexIdList.get(newValue.intValue());
					//更新动物表，切片表
					updatePage(contentIndexId);
				}
			}

		};
		comboBox.getSelectionModel().selectedIndexProperty().addListener(comboBoxChangeListener);
	}

	/**更新页面，
	 * @param contentIndexId
	 */
	private void updatePage(String contentIndexId) {
		// TODO 
		//1.清除 解剖计划、解剖任务 或组织取材批次表的选择
		clearSelectTable();
		//2.根据组合索引Id ，加载动物列表（并全选中）
		List<String> animalCodeList = updateAnimalTable(contentIndexId);
		//3.切片表 全不选
		onUnSelectAllAction_slice(null);
		//4.更新切片表，非常规切片部分(不查询数据库)
		updateSliceTable_fcg(animalCodeList );
		//5.根据组合索引Id ,加载切片列表
		List<String> sliceCodeList = BaseService.getInstance().getTblHistopathCheckContentService().getSliceCodeList(contentIndexId);
		//6.选中切片列表
		selectSliceTable(sliceCodeList);
		//7.delBtn 按钮可用
		delBtn.setDisable(false);
		
	}
	
	/**选中对应切片
	 * @param sliceCodeList
	 */
	private void selectSliceTable(List<String> sliceCodeList) {
		if(null != sliceCodeList && sliceCodeList.size() > 0){
			int index = 0 ;
			for(ContentSlice obj:data_sliceTable){
				if(sliceCodeList.contains(obj.getSliceCode())){
					data_sliceTable.set(index, new ContentSlice(true,obj.getTissueSliceSnId(),obj.getSliceCode(),obj.getVisceraOrTissueName()
							,obj.getCodeDate(),obj.getAnimalCode(),obj.getSliceCodeType()));
				}
				index++;
			}
		}
	}

	/**根据组合索引Id ，加载动物列表（并全选中）
	 * @param contentIndexId
	 * @return
	 */
	private List<String> updateAnimalTable(String contentIndexId) {
		data_animalTable.clear();
		
		List<String> animalCodeList = new ArrayList<String>();
		
		List<Map<String,Object>> animaMaplList = BaseService.getInstance().getTblHistopathCheckContentService()
				.getAnimalMapList(contentIndexId);;
		if(null != animaMaplList && animaMaplList.size() > 0){
			for(Map<String,Object> map :animaMaplList){
				String animalCode = (String) map.get("animalCode");
				String dosageDesc = (String) map.get("dosageDesc");
				Integer gender = (Integer) map.get("gender");
				String anatomyDate = (String) map.get("anatomyDate");
				data_animalTable.add(new Animal(true,
						dosageDesc,animalCode,gender,anatomyDate));
				
				animalCodeList.add(animalCode);
			}
		}
		
		return animalCodeList;
	}

	/**
	 * 初始化切片列表
	 */
	private void initSliceTable() {
		sliceTable.setItems(data_sliceTable);
		sliceTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol_slice.setCellValueFactory(new PropertyValueFactory<ContentSlice,Boolean>("select"));
		sliceCodeCol.setCellValueFactory(new PropertyValueFactory<ContentSlice,String>("sliceCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<ContentSlice,String>("visceraOrTissueName"));
		codeDateCol.setCellValueFactory(new PropertyValueFactory<ContentSlice,String>("codeDate"));
		
		selectCol_slice.setCellFactory(new Callback<TableColumn<ContentSlice,Boolean>,TableCell<ContentSlice,Boolean>>(){

			@Override
			public TableCell<ContentSlice, Boolean> call(TableColumn<ContentSlice, Boolean> arg0) {
				return new CheckBoxTableCell<ContentSlice,Boolean>();
			}
		});
		
		//解剖次数居中
		sliceCodeCol.setCellFactory(new Callback<TableColumn<ContentSlice,String>,TableCell<ContentSlice,String>>(){
					
			@Override
			public TableCell<ContentSlice, String> call(
					TableColumn<ContentSlice, String> param) {
				TableCell<ContentSlice, String> cell =
						new TableCell<ContentSlice, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
//							    				 ContentSlice anatomyTask = getTableView().getItems().get(getIndex());
						ContentSlice dissectPlan = (ContentSlice) getTableRow().getItem();
						if(null != dissectPlan){
							if(dissectPlan.getSelect()){
//							    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
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
		sliceTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<ContentSlice> table = (TableView<ContentSlice>) event.getSource();
					ContentSlice obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						ContentSlice obj2 = new ContentSlice(!obj.getSelect(),obj.getTissueSliceSnId(),obj.getSliceCode(),
								obj.getVisceraOrTissueName(),obj.getCodeDate(),obj.getAnimalCode(),obj.getSliceCodeType());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
				}
			}
		});
	}

	/**
	 * 初始化组织取材批次表
	 */
	private void initBatchTable() {
		
		batchTable.setItems(data_batchTable);
		batchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		batchSnCol.setCellValueFactory(new PropertyValueFactory<Batch,String>("batchSn"));
		animalNumCol_2.setCellValueFactory(new PropertyValueFactory<Batch,String>("animalNum"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<Batch,String>("createTime"));
		sliceTypeCol.setCellValueFactory(new PropertyValueFactory<Batch,String>("sliceTypeDesc"));
		
		//解剖次数居中
		animalNumCol_2.setCellFactory(new Callback<TableColumn<Batch,String>,TableCell<Batch,String>>(){
			
			@Override
			public TableCell<Batch, String> call(
					TableColumn<Batch, String> param) {
				TableCell<Batch, String> cell =
						new TableCell<Batch, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
//					    				 Batch anatomyTask = getTableView().getItems().get(getIndex());
						Batch dissectPlan = (Batch) getTableRow().getItem();
						if(null != dissectPlan){
							if(dissectPlan.getSelect()){
//					    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
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
		batchTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<Batch> table = (TableView<Batch>) event.getSource();
					Batch obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						Batch obj2 = new Batch(!obj.getSelect(),obj.getBatchId(),obj.getBatchSn(),
								obj.getAnimalNum(),obj.getCreateTime(),obj.getSliceType());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
					
					if(no_currentTab != no_animalFromTab){
						no_animalFromTab = 2;
						//清空其他表的选择
						resetOtherTwoTable(no_animalFromTab);
					}
					//更新动物表 
					updateAnimalTable();
					//根据批次表选择项，选中对应切片编号
					selectSliceTableByBatchTable();
				}
				
			}


			
		});
	}
	/**
	 * 根据批次表选择项，选中对应切片编号 TODO 
	 */
	private void selectSliceTableByBatchTable() {
		// 1.
		//已选择的组织取材批次Id
		Set<String> batchIdSet = new HashSet<String>();
		for(Batch obj:data_batchTable){
			if(obj.getSelect()){
				batchIdSet.add(obj.getBatchId());
			}
		}
		//2.切片表 全不选
		onUnSelectAllAction_slice(null);
		//3.查询切片编号列表
		List<String> sliceCodeList = BaseService.getInstance().getTblHistopathCheckContentService().getSliceCodeListByBatchIdSet(batchIdSet);
		//4.选中切片列表
		selectSliceTable(sliceCodeList);
	}
	/**清除  解剖计划、解剖任务、取材批次表的选择
	 * @param no_animalFromTab
	 */
	private void clearSelectTable() {
		if(no_animalFromTab == 0){
			//解剖计划表置为都没选中
			int i= 0;
			for(DissectPlan obj:data_dissectPlanTable){
				data_dissectPlanTable.set(i,new DissectPlan(false,obj.getDissectNum(),
						obj.getAnimalNum(),obj.getBeginDate(),obj.getEndDate()));
				i++;
			}
		}else if(no_animalFromTab == 1){
			int i= 0;
			//解剖任务表置为都没选中
			for(AnatomyTask obj:data_anatomyTaskTable){
				data_anatomyTaskTable.set(i, new AnatomyTask(false,obj.getTaskId(),
						obj.getPlanAnatomyDate(),obj.getAnimalNum(),obj.getReason()));
				i++;
			}
		}else{
			int i= 0;
			//组织取材批次表置为都没选中
			for(Batch obj:data_batchTable){
				data_batchTable.set(i, new Batch(false,obj.getBatchId(),obj.getBatchSn(),
						obj.getAnimalNum(),obj.getCreateTime(),obj.getSliceType()));
				i++;
			}
		}
		
	}
	/**清空其他表的选择
	 * @param no_animalFromTab
	 */
	private void resetOtherTwoTable(int no_animalFromTab) {
		if(no_animalFromTab == 0){
			int i= 0;
			//解剖任务表置为都没选中
			for(AnatomyTask obj:data_anatomyTaskTable){
				data_anatomyTaskTable.set(i, new AnatomyTask(false,obj.getTaskId(),
						obj.getPlanAnatomyDate(),obj.getAnimalNum(),obj.getReason()));
				i++;
			}
			i= 0;
			//组织取材批次表置为都没选中
			for(Batch obj:data_batchTable){
				data_batchTable.set(i, new Batch(false,obj.getBatchId(),obj.getBatchSn(),
						obj.getAnimalNum(),obj.getCreateTime(),obj.getSliceType()));
				i++;
			}
		}else if(no_animalFromTab == 1){
			//解剖计划表置为都没选中
			int i= 0;
			for(DissectPlan obj:data_dissectPlanTable){
				data_dissectPlanTable.set(i,new DissectPlan(false,obj.getDissectNum(),
						obj.getAnimalNum(),obj.getBeginDate(),obj.getEndDate()));
				i++;
			}
			i= 0;
			//组织取材批次表置为都没选中
			for(Batch obj:data_batchTable){
				data_batchTable.set(i, new Batch(false,obj.getBatchId(),obj.getBatchSn(),
						obj.getAnimalNum(),obj.getCreateTime(),obj.getSliceType()));
				i++;
			}
		}else{
			//解剖计划表置为都没选中
			int i= 0;
			for(DissectPlan obj:data_dissectPlanTable){
				data_dissectPlanTable.set(i,new DissectPlan(false,obj.getDissectNum(),
						obj.getAnimalNum(),obj.getBeginDate(),obj.getEndDate()));
				i++;
			}
			i= 0;
			//解剖任务表置为都没选中
			for(AnatomyTask obj:data_anatomyTaskTable){
				data_anatomyTaskTable.set(i, new AnatomyTask(false,obj.getTaskId(),
						obj.getPlanAnatomyDate(),obj.getAnimalNum(),obj.getReason()));
				i++;
			}
		}
		
	}
	/**
	 * 初始化解剖计划表
	 */
	private void initDissectPlanTable() {

		dissectPlanTable.setItems(data_dissectPlanTable);
		dissectPlanTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dissectNumCol.setCellValueFactory(new PropertyValueFactory<DissectPlan,String>("dissectNumDesc"));
		animalNumCol_0.setCellValueFactory(new PropertyValueFactory<DissectPlan,String>("animalNum"));
		beginDateCol.setCellValueFactory(new PropertyValueFactory<DissectPlan,String>("beginDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<DissectPlan,String>("endDate"));
		
		//解剖次数居中
		animalNumCol_0.setCellFactory(new Callback<TableColumn<DissectPlan,String>,TableCell<DissectPlan,String>>(){
					
					@Override
					public TableCell<DissectPlan, String> call(
							TableColumn<DissectPlan, String> param) {
						TableCell<DissectPlan, String> cell =
								new TableCell<DissectPlan, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								setText(empty ? null : getString());
								setGraphic(null);
//					    				 DissectPlan anatomyTask = getTableView().getItems().get(getIndex());
								DissectPlan dissectPlan = (DissectPlan) getTableRow().getItem();
								if(null != dissectPlan){
									if(dissectPlan.getSelect()){
//					    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
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
		dissectPlanTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
					
					@SuppressWarnings("unchecked")
					@Override
					public void handle(MouseEvent event) {
						// 单击
						if (event.getButton() == MouseButton.PRIMARY) {
							TableView<DissectPlan> table = (TableView<DissectPlan>) event.getSource();
							DissectPlan obj = table.getSelectionModel().getSelectedItem();
							if (null != obj) {
								DissectPlan obj2 = new DissectPlan(!obj.getSelect(),obj.getDissectNum(),
										obj.getAnimalNum(),obj.getBeginDate(),obj.getEndDate());
								
								table.getItems().set(table.getSelectionModel().getSelectedIndex(),
										obj2);
							}
							table.getSelectionModel().clearSelection();
							
							if(no_currentTab != no_animalFromTab){
								no_animalFromTab = 0;
								//清空其他表的选择
								resetOtherTwoTable(no_animalFromTab);
							}
							//更新动物表 
							updateAnimalTable();
							
						}
						
					}
					
				});
	}

	/**
	 * 初始化tabPane
	 */
	private void initTabPane() {
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				no_currentTab = newValue.intValue();
			}
			
		});
	}

	/**
	 * 初始化已选动物列表
	 */
	private void initAnimalTable() {
		animalTable.setItems(data_animalTable);
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol.setCellValueFactory(new PropertyValueFactory<Animal,Boolean>("select"));
		dosageDescCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		anatomyDateCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("anatomyDate"));
		
		selectCol.setCellFactory(new Callback<TableColumn<Animal,Boolean>,TableCell<Animal,Boolean>>(){

			@Override
			public TableCell<Animal, Boolean> call(TableColumn<Animal, Boolean> arg0) {
				return new CheckBoxTableCell<Animal,Boolean>();
			}
			
		});
		//动物数量居中,选中设置行颜色
		genderCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
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
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<Animal> table = (TableView<Animal>) event.getSource();
					Animal obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						Animal obj2 = new Animal(!obj.getSelect(),obj.getDosageDesc(),
								obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
					
					
				}
				
			}
			
		});
	}

	/**
	 * 初始化解剖任务表
	 */
	private void initAnatomyTaskTable(){
		anatomyTaskTable.setItems(data_anatomyTaskTable);
		anatomyTaskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		planAnatomyDateCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("planAnatomyDate"));
		animalNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("animalNum"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("reason"));
		//动物数量居中,选中设置行颜色
		animalNumCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyTask, String> call(
	    			 TableColumn<AnatomyTask, String> param) {
	    		 TableCell<AnatomyTask, String> cell =
	    				 new TableCell<AnatomyTask, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
//	    				 AnatomyTask anatomyTask = getTableView().getItems().get(getIndex());
	    				 AnatomyTask anatomyTask = (AnatomyTask) getTableRow().getItem();
	    				 if(null != anatomyTask){
	    					 if(anatomyTask.getSelect()){
//	    						 getTableRow().setStyle("-fx-background-color:palegreen ;");
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
		anatomyTaskTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<AnatomyTask> table = (TableView<AnatomyTask>) event.getSource();
					AnatomyTask obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						AnatomyTask obj2 = new AnatomyTask(!obj
								.getSelect(), obj.getTaskId(),obj.getPlanAnatomyDate(),obj.getAnimalNum(),obj.getReason());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
					
					if(no_currentTab != no_animalFromTab){
						no_animalFromTab = 1;
						//清空其他表的选择
						resetOtherTwoTable(no_animalFromTab);
					}
					//更新动物表 
					updateAnimalTable();
				}

			}

		});
	}

	/**全不选_雄性
	 * @param event
	 */
	@FXML
	private void onSelectAllMaleAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable2 = FXCollections.observableArrayList(data_animalTable);
		data_animalTable.clear();
		if(null != data_animalTable2 && data_animalTable2.size() > 0){
			for(Animal obj:data_animalTable2){
				data_animalTable.add(new Animal(obj.getGender().equals("♂"),obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全选_雌性
	 * @param event
	 */
	@FXML
	private void onSelectAllFemaleAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable2 = FXCollections.observableArrayList(data_animalTable);
		data_animalTable.clear();
		if(null != data_animalTable2 && data_animalTable2.size() > 0){
			for(Animal obj:data_animalTable2){
				data_animalTable.add(new Animal(obj.getGender().equals("♀"),obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全不选_动物
	 * @param event
	 */
	@FXML
	private void onUnSelectAllActionAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable2 = FXCollections.observableArrayList(data_animalTable);
		data_animalTable.clear();
		if(null != data_animalTable2 && data_animalTable2.size() > 0){
			for(Animal obj:data_animalTable2){
				data_animalTable.add(new Animal(false,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全选_动物
	 * @param event
	 */
	@FXML
	private void onSelectAllActionAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable2 = FXCollections.observableArrayList(data_animalTable);
		data_animalTable.clear();
		if(null != data_animalTable2 && data_animalTable2.size() > 0){
			for(Animal obj:data_animalTable2){
				data_animalTable.add(new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全不选_切片
	 * @param event
	 */
	@FXML
	private void onUnSelectAllAction_slice(ActionEvent event){
		ObservableList<ContentSlice> data_sliceTable2 = FXCollections.observableArrayList(data_sliceTable);
		data_sliceTable.clear();
		if(null != data_sliceTable2 && data_sliceTable2.size() > 0){
			for(ContentSlice obj:data_sliceTable2){
				data_sliceTable.add(new ContentSlice(false,obj.getTissueSliceSnId(),obj.getSliceCode(),
						obj.getVisceraOrTissueName(),obj.getCodeDate(),obj.getAnimalCode(),obj.getSliceCodeType()));
			}
		}
	}
	/**全选_切片
	 * @param event
	 */
	@FXML
	private void onSelectAllAction_slice(ActionEvent event){
		ObservableList<ContentSlice> data_sliceTable2 = FXCollections.observableArrayList(data_sliceTable);
		data_sliceTable.clear();
		if(null != data_sliceTable2 && data_sliceTable2.size() > 0){
			for(ContentSlice obj:data_sliceTable2){
				data_sliceTable.add(new ContentSlice(true,obj.getTissueSliceSnId(),obj.getSliceCode(),
						obj.getVisceraOrTissueName(),obj.getCodeDate(),obj.getAnimalCode(),obj.getSliceCodeType()));
			}
		}
	}
	/**保存
	 * @param event
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		//1.选择的动物
		List<String> animalCodeList = new ArrayList<String>();
		for(Animal obj:data_animalTable){
			if(obj.getSelect()){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		if(animalCodeList.size() < 1){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//2.选择的切片编号Id列表
		List<String> tissueSliceSnIdList = new ArrayList<String>();
		List<String> sliceCodeList = new ArrayList<String>();
		for(ContentSlice obj:data_sliceTable){
			if(obj.getSelect()){
				tissueSliceSnIdList.add(obj.getTissueSliceSnId());
				sliceCodeList.add(obj.getSliceCode());
			}
		}
		if(tissueSliceSnIdList.size() < 1){
			showErrorMessage("请先选择切片！");
			return;
		}
		
		//3.镜检组合名称
		String realName = SaveUserInfo.getRealName();
		String userName = SaveUserInfo.getUserName();
		String contentName = DateUtil.getNow("yyyy-MM-dd")+"镜检组合-"+realName;
		
		Stage stage = Main.stageMap.get("HistopathCheckContent_Name");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				HistopathCheckContent_Name.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("HistopathCheckContent_Name",stage);
		}
		HistopathCheckContent_Name.getInstance().loadData(contentName);
		stage.showAndWait();
		
		if(HistopathCheckContent_Name.getInstance().isConfirm()){
			contentName = HistopathCheckContent_Name.getInstance().getContentName();
		}else{
			return;
		}
		
		
		//4.准别数据，保存
		TblHistopathCheckContentIndex contentIndex = new TblHistopathCheckContentIndex();
		contentIndex.setStudyNo(studyNo);
		contentIndex.setCreator(userName);
		contentIndex.setContentName(contentName);
		
		contentIndex = BaseService.getInstance().getTblHistopathCheckContentService()
				.saveOne(contentIndex,animalCodeList,tissueSliceSnIdList,sliceCodeList);
		//5.
		data_comboBox.add(contentName);
		contentIndexIdList.add(contentIndex.getId());
		//6.取消combobox的监听事件，选中某行，再回复监听事件
		comboBox.getSelectionModel().selectedIndexProperty().removeListener(comboBoxChangeListener);
		comboBox.getSelectionModel().selectLast();
		comboBox.getSelectionModel().selectedIndexProperty().addListener(comboBoxChangeListener);
		//7.delBtn 按钮可用
		delBtn.setDisable(false);
		//8.保存成功
		showMessage("保存成功！");
	}

	/**取消
	 * @param event
	 */
	@FXML
	private void ondelBtnAction(ActionEvent event){
		//1.选中的索引
		int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0){
			showErrorMessage("请先选择待删除的组合！");
			return;
		}
		//2.待删除组合索引Id
		String contentIndexId = contentIndexIdList.get(selectedIndex);
		//3.删除
		BaseService.getInstance().getTblHistopathCheckContentService().deleteOne(contentIndexId);
		//4.更新ComboBox
		updateComboBox();
		//5.delBtn 按钮可用
		delBtn.setDisable(false);
		//6.
		showMessage("删除成功！");
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		//已选的动物编号列表
		List<String> animalCodeList = new ArrayList<String>();
		//已选组织切片序号Id列表
		List<String> tissueSliceSnIdList = new ArrayList<String>();
		for(ContentSlice obj:data_sliceTable){
			if(obj.getSelect()){
				tissueSliceSnIdList.add(obj.getTissueSliceSnId());
				if(obj.getSliceCodeType() == 1){
					animalCodeList.add(obj.getAnimalCode());
				}
			}
		}
		
		for(Animal obj:data_animalTable){
			if(obj.getSelect()){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		if(tissueSliceSnIdList.size() > 0 && animalCodeList.size() > 0 ){
			//更新动物表和切片树
			HistopathCheckPage.getInstance().updateAnimalTableAndSliceTree(animalCodeList,tissueSliceSnIdList);
			
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}else{
			if(animalCodeList.size() > 0){
				showErrorMessage("请先选择切片！");
			}else{
				showErrorMessage("请先选择动物！");
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
	 * 加载数据（新建时）
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		data_animalTable.clear();
		delBtn.setDisable(true);
		
		//1.更新专题信息
		updateStudyLabel();
		//2.更新解剖计划表及查询所有解剖计划动物
		updateDissectPlanTable();
		//3.更新解剖任务表格及查询所有解剖动物
		updateAnatomyTaskTable();
		//4.更新组织取材批次表及查询所有批次动物
		updateBatchTable();
		//5.更新切片表(查询数据库)
		updateSliceTable();
		//6.更新ComboBox
		updateComboBox();
	}
	
	/**更新ComboBox
	 * ComboBox
	 */
	private void updateComboBox() {
		data_comboBox.clear();
		contentIndexIdList.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckContentService()
				.getContentIndexMapList(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
				String contentIndexId = (String) obj.get("contentIndexId");
				String contentName = (String) obj.get("contentName");
				data_comboBox.add(contentName);
				contentIndexIdList.add(contentIndexId);
			}
		}
	}

	/**
	 * 更新切片表，非常规切片部分(不查询数据库)
	 */
	private void updateSliceTable_fcg(List<String> animalCodeList){
		//待添加的动物编号列表
		List<String> animalCodeList_add = new ArrayList<String>(animalCodeList);
		//待删除的切片列表
		List<ContentSlice> contentSliceList_del = new ArrayList<ContentSlice>();
		//1.确定  animalCodeList_add 和  contentSliceList_del
		for(ContentSlice obj:data_sliceTable){
			//非常规切片
			if(obj.getSliceCodeType() == 1){
				if(!animalCodeList.contains(obj.getAnimalCode())){
					contentSliceList_del.add(obj);
				}else{
					animalCodeList_add.remove(obj.getAnimalCode());
				}
			}

		}
		//2.删除待删除的切片列表
		data_sliceTable.removeAll(contentSliceList_del);
		//3.添加待添加的
		//待添加的切片列表
		List<ContentSlice> contentSliceList_add = new ArrayList<ContentSlice>();
		for(ContentSlice obj:data_sliceTable_fcg){
			if(animalCodeList_add.contains(obj.getAnimalCode())){
				contentSliceList_add.add(new ContentSlice(false,obj.getTissueSliceSnId(),obj.getSliceCode(),
						obj.getVisceraOrTissueName(),obj.getCodeDate(),obj.getAnimalCode(),obj.getSliceCodeType()));
			}
		}
		data_sliceTable.addAll(contentSliceList_add);
		//4.排序
		Collections.sort(data_sliceTable, new Comparator<ContentSlice>(){
			@Override
			public int compare(ContentSlice o1, ContentSlice o2) {
				if(o1.getSliceCodeType() == o2.getSliceCodeType()){
					if(o1.getSliceCodeType() == 0){
						return Integer.parseInt(o1.getSliceCode()) - Integer.parseInt(o2.getSliceCode());
					}else{
						return o1.getSliceCode().compareTo(o2.getSliceCode());
					}
				}else{
					return o1.getSliceCodeType() - o2.getSliceCodeType();
				}
			}
		});
	}
	
	/**
	 * 更新切片表(查询数据库)
	 */
	private void updateSliceTable() {
		data_sliceTable.clear();
		data_sliceTable_fcg.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckContentService().getSliceCodeByStudyNo(studyNo);
		if(null != mapList){
//			tissueSliceSnId,sliceCode,visceraOrTissueName,codeDate,animalCode,sliceCodeType
			String preTissueSliceSnId="";
			String preSliceCode="";
			String preVisceraOrTissueName="";
			String preCodeDate = "";
			String preAnimalCode = "";
			Integer preSliceCodeType = 0;
			for(Map<String,Object> map:mapList){
				String tissueSliceSnId = (String) map.get("tissueSliceSnId");
				String sliceCode = (String) map.get("sliceCode");
				String visceraOrTissueName = (String) map.get("visceraOrTissueName");
				String codeDate = (String) map.get("codeDate");
				String animalCode = (String) map.get("animalCode");
				Integer sliceCodeType = (Integer) map.get("sliceCodeType");
				if("".equals(preSliceCode)){
					preTissueSliceSnId = tissueSliceSnId;
					preSliceCode = sliceCode;
					preVisceraOrTissueName = visceraOrTissueName;
					preCodeDate = codeDate;
					preAnimalCode = animalCode;
					preSliceCodeType = sliceCodeType;
					
				}else if(preSliceCode.equals(sliceCode)){
					preVisceraOrTissueName += "、"+visceraOrTissueName;
				}else{
					if(preSliceCodeType != 1){
						data_sliceTable.add(new ContentSlice(false,preTissueSliceSnId,preSliceCode,
								preVisceraOrTissueName,preCodeDate,preAnimalCode,preSliceCodeType));
					}else{
						data_sliceTable_fcg.add(new ContentSlice(false,preTissueSliceSnId,preSliceCode,
								preVisceraOrTissueName,preCodeDate,preAnimalCode,preSliceCodeType));
					}
					
					preTissueSliceSnId = tissueSliceSnId;
					preSliceCode = sliceCode;
					preVisceraOrTissueName = visceraOrTissueName;
					preCodeDate = codeDate;
					preAnimalCode = animalCode;
					preSliceCodeType = sliceCodeType;
				}
				
			}
			if(!"".equals(preSliceCode)){
				if(preSliceCodeType != 1){
					data_sliceTable.add(new ContentSlice(false,preTissueSliceSnId,preSliceCode,
							preVisceraOrTissueName,preCodeDate,preAnimalCode,preSliceCodeType));
				}else{
					data_sliceTable_fcg.add(new ContentSlice(false,preTissueSliceSnId,preSliceCode,
							preVisceraOrTissueName,preCodeDate,preAnimalCode,preSliceCodeType));
				}
			}
		}
	}

	/**
	 * 更新组织取材批次表及查询所有批次动物
	 */
	private void updateBatchTable() {
		
		data_batchTable.clear();
		batchAnimaMaplList.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckContentService().getBatchIndexListByStudyNo(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map :mapList){
				String batchId = (String) map.get("batchId");
				Integer batchSn = (Integer) map.get("batchSn");
				Integer animalNum = (Integer) map.get("animalNum");
				String createTime = (String) map.get("createTime");
				Integer sliceType = (Integer) map.get("sliceType");
				data_batchTable.add(new Batch(false,batchId,batchSn+"",animalNum+"",createTime,sliceType+""));
			}
		}
		
		batchAnimaMaplList = BaseService.getInstance().getTblHistopathCheckContentService().getBatchAnimalMapListByStudyNo(studyNo);
	}
	/**
	 * 更新解剖计划表
	 */
	private void updateDissectPlanTable() {
//		/**
//		 * 解剖计划动物列表,dissectNum,animalCode,gender,dosageDesc,anatomyDate(打开窗口时更新，后不变)
//		 */
//		List<Map<String,Object>> dissectPlanAnimaMaplList = new ArrayList<Map<String,Object>>();
		
		data_dissectPlanTable.clear();
		dissectPlanAnimaMaplList.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckContentService().getDissectPlanListByStudyNo(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map :mapList){
				Integer dissectNum = (Integer) map.get("dissectNum");
				Integer animalNum = (Integer) map.get("animalNum");
				String beginDate = (String) map.get("beginDate");
				String endDate = (String) map.get("endDate");
				data_dissectPlanTable.add(new DissectPlan(false,dissectNum+"",animalNum+"",beginDate,endDate));
			}
		}
		
		dissectPlanAnimaMaplList = BaseService.getInstance().getTblHistopathCheckContentService().getDissectPlanAnimalMapListByStudyNo(studyNo);
	}

	/**
	 * 更新专题信息(4个Label)
	 */
	private void updateStudyLabel() {
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		studyNoLabel.setText(studyNo);
		if(null != studyPlan){
			sdLabel.setText(studyPlan.getStudydirector());
			animalTypeLabel.setText(studyPlan.getAnimalType());
		}
		String pathSd = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(studyNo);
		pathSDLabel.setText(pathSd);
	}

	/**
	 * 更新已选动物表
	 */
	private void updateAnimalTable() {
		//动物表里的所有动物编号
		List<String> animalCodeList = new ArrayList<String>();
		//1.已选中的动物编号列表
		Set<String> animalCodeSet = new HashSet<String>();
		for(Animal obj:data_animalTable){
			if(obj.getSelect()){
				animalCodeSet.add(obj.getAnimalCode());
			}
		}
		//2.清空
		data_animalTable.clear();
		//3.加载数据
		if(no_animalFromTab == 0){
			//已选择的解剖次数
			Set<String> dissectNumSet = new HashSet<String>();
			for(DissectPlan obj:data_dissectPlanTable){
				if(obj.getSelect()){
					dissectNumSet.add(obj.getDissectNum());
				}
			}
			if(dissectNumSet.size() > 0 && null != dissectPlanAnimaMaplList && dissectPlanAnimaMaplList.size() > 0){
				for(Map<String,Object> map :dissectPlanAnimaMaplList){
					Integer dissectNum = (Integer) map.get("dissectNum");
					if(dissectNumSet.contains((dissectNum+"").trim())){
						String animalCode = (String) map.get("animalCode");
						String dosageDesc = (String) map.get("dosageDesc");
						Integer gender = (Integer) map.get("gender");
						String anatomyDate = (String) map.get("anatomyDate");
						data_animalTable.add(new Animal(animalCodeSet.contains(animalCode),
								dosageDesc,animalCode,gender,anatomyDate));
						
						animalCodeList.add(animalCode);
					}
				}
			}
		}else if(no_animalFromTab == 1){
			//已选择的解剖任务Id
			Set<String> taskIdSet = new HashSet<String>();
			for(AnatomyTask obj:data_anatomyTaskTable){
				if(obj.getSelect()){
					taskIdSet.add(obj.getTaskId());
				}
			}
			if(taskIdSet.size() > 0 && null != taskAnimaMaplList && taskAnimaMaplList.size() > 0){
				for(Map<String,Object> map :taskAnimaMaplList){
					String taskId = (String) map.get("taskId");
					if(taskIdSet.contains(taskId)){
						String animalCode = (String) map.get("animalCode");
						String dosageDesc = (String) map.get("dosageDesc");
						Integer gender = (Integer) map.get("gender");
						String anatomyDate = (String) map.get("anatomyDate");
						data_animalTable.add(new Animal(animalCodeSet.contains(animalCode),
								dosageDesc,animalCode,gender,anatomyDate));
						
						animalCodeList.add(animalCode);
					}
				}
			}
		}else{
			//已选择的组织取材批次Id
			Set<String> batchIdSet = new HashSet<String>();
			for(Batch obj:data_batchTable){
				if(obj.getSelect()){
					batchIdSet.add(obj.getBatchId());
				}
			}
			if(batchIdSet.size() > 0 && null != batchAnimaMaplList && batchAnimaMaplList.size() > 0){
				for(Map<String,Object> map :batchAnimaMaplList){
					String batchId = (String) map.get("batchId");
					if(batchIdSet.contains(batchId)){
						String animalCode = (String) map.get("animalCode");
						if(!animalCodeList.contains(animalCode)){
							String dosageDesc = (String) map.get("dosageDesc");
							Integer gender = (Integer) map.get("gender");
							String anatomyDate = (String) map.get("anatomyDate");
							data_animalTable.add(new Animal(animalCodeSet.contains(animalCode),
									dosageDesc,animalCode,gender,anatomyDate));
							
							animalCodeList.add(animalCode);
						}
					}
				}
			}
		}
		//4.更新切片表
		updateSliceTable_fcg(animalCodeList);
	}
	
	/**
	 * .更新解剖任务表格及查询所有解剖动物
	 */
	private void updateAnatomyTaskTable(){
		data_anatomyTaskTable.clear();
		taskAnimaMaplList.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService().getAnatomyTaskListByStudyNo(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map :mapList){
				String taskId = (String) map.get("taskId");
				String planAnatomyDate = (String) map.get("planAnatomyDate");
				Integer animalNum = (Integer) map.get("animalNum");
				String reason = (String) map.get("reason");
				data_anatomyTaskTable.add(new AnatomyTask(false,taskId,planAnatomyDate,animalNum+"",reason));
			}
		}
		
		taskAnimaMaplList = BaseService.getInstance().getTblTissueSliceBatchService().getTaskAnimalMapListByStudyNo(studyNo);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathCheckContent.fxml"));
		Scene scene = new Scene(root, 1024, 606);
		stage.setScene(scene);
		stage.setTitle("选择镜检组合");
		stage.setMinWidth(1024);
		stage.setMinHeight(606);
//		stage.setResizable(false);
		
		stage.show();
		
	}
	
	/**解剖任务
	 * @author Administrator
	 *
	 */
	public class AnatomyTask{
		private BooleanProperty select;
		private StringProperty taskId;
		private StringProperty planAnatomyDate;	//仅选开始日期
		private StringProperty animalNum;		//动物数量
		private StringProperty reason;			//解剖原因
		
		public AnatomyTask(){}
		public AnatomyTask(boolean select,String taskId,String planAnatomyDate,String animalNum,String reason){
			setSelect(select);
			setTaskId(taskId);
			setPlanAnatomyDate(planAnatomyDate);
			setAnimalNum(animalNum+"");
			setReason(reason);
		}
		public BooleanProperty selectProperty(){return select;}
		public boolean getSelect() {
			return select.get();
		}
		public void setSelect(boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getTaskId() {
			return taskId.get();
		}
		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}
		public String getPlanAnatomyDate() {
			return planAnatomyDate.get();
		}
		public void setPlanAnatomyDate(String planAnatomyDate) {
			this.planAnatomyDate = new SimpleStringProperty(planAnatomyDate);
		}
		public String getAnimalNum() {
			return animalNum.get();
		}
		public void setAnimalNum(String animalNum) {
			this.animalNum = new SimpleStringProperty(animalNum);
		}
		public String getReason() {
			return reason.get();
		}
		public void setReason(String reason) {
			this.reason = new SimpleStringProperty(reason);
		}
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
		private StringProperty anatomyDate;
		
		public Animal(){}
		public Animal(boolean select,String dosageDesc,String animalCode,int gender,String anatomyDate){
			setSelect(select);
			setDosageDesc(dosageDesc);
			setAnimalCode(animalCode);
			setGender(gender == 1 ? "♂":"♀" );
			setAnatomyDate(anatomyDate);
		}
		public Animal(boolean select,String dosageDesc,String animalCode,String gender,String anatomyDate){
			setSelect(select);
			setDosageDesc(dosageDesc);
			setAnimalCode(animalCode);
			setGender(gender);
			setAnatomyDate(anatomyDate);
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
		public String getAnatomyDate() {
			return anatomyDate.get();
		}
		public void setAnatomyDate(String anatomyDate) {
			this.anatomyDate = new SimpleStringProperty(anatomyDate);
		}
		public String getDosageDesc() {
			return dosageDesc.get();
		}
		public void setDosageDesc(String dosageDesc) {
			this.dosageDesc = new SimpleStringProperty(dosageDesc);
		}
	}
	
	/**解剖计划
	 * @author Administrator
	 *
	 */
	public class DissectPlan{
		private BooleanProperty select;
		private StringProperty dissectNum;
		private StringProperty dissectNumDesc;
		private StringProperty animalNum;
		private StringProperty beginDate;
		private StringProperty endDate;
		
		private DissectPlan(){}
		private DissectPlan(boolean select,String dissectNum,String animalNum,
				String beginDate,String endDate){
			setSelect(select);
			setDissectNum(dissectNum);
			setDissectNumDesc("第"+dissectNum+"次解剖");
			if(null == animalNum || "null".equals(animalNum)){
				setAnimalNum("0");
			}else{
				setAnimalNum(animalNum);
			}
			setBeginDate(beginDate);
			setEndDate(endDate);
		}
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		
		public String getDissectNum() {
			return dissectNum.get();
		}
		public void setDissectNum(String dissectNum) {
			this.dissectNum = new SimpleStringProperty(dissectNum);
		}
		public String getDissectNumDesc() {
			return dissectNumDesc.get();
		}
		public void setDissectNumDesc(String dissectNumDesc) {
			this.dissectNumDesc = new SimpleStringProperty(dissectNumDesc);
		}
		public String getAnimalNum() {
			return animalNum.get();
		}
		public void setAnimalNum(String animalNum) {
			this.animalNum =  new SimpleStringProperty(animalNum);
		}
		public String getBeginDate() {
			return beginDate.get();
		}
		public void setBeginDate(String beginDate) {
			this.beginDate =  new SimpleStringProperty(beginDate);
		}
		public String getEndDate() {
			return endDate.get();
		}
		public void setEndDate(String endDate) {
			this.endDate =  new SimpleStringProperty(endDate);
		}
		
	}
	
	/**批次
	 * @author Administrator
	 *
	 */
	public class Batch{
		private BooleanProperty select;
		private StringProperty batchId;
		private StringProperty batchSn;
		private StringProperty animalNum;
		private StringProperty createTime;
		private StringProperty sliceType;
		private StringProperty sliceTypeDesc;
		
		public Batch(){}
		public Batch(boolean select,String batchId,String batchSn,String animalNum,String createTime,String sliceType){
			setSelect(select);
			setBatchId(batchId);
			setBatchSn(batchSn);
			setCreateTime(createTime);
			setAnimalNum(animalNum);
			setSliceType(sliceType);
			if(sliceType.trim().equals("1")){
				setSliceTypeDesc("常规组织取材、非常规组织取材");
			}else{
				setSliceTypeDesc("追加组织取材");
			}
		}
		
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getBatchId() {
			return batchId.get();
		}
		public void setBatchId(String batchId) {
			this.batchId = new SimpleStringProperty(batchId);
		}
		public String getBatchSn() {
			return batchSn.get();
		}
		public void setBatchSn(String batchSn) {
			this.batchSn = new SimpleStringProperty(batchSn);
		}
		public String getCreateTime() {
			return createTime.get();
		}
		public void setCreateTime(String createTime) {
			this.createTime = new SimpleStringProperty(createTime);
		}
		public String getSliceType() {
			return sliceType.get();
		}
		public void setSliceType(String sliceType) {
			this.sliceType = new SimpleStringProperty(sliceType);
		}
		public String getAnimalNum() {
			return animalNum.get();
		}
		public void setAnimalNum(String animalNum) {
			this.animalNum = new SimpleStringProperty(animalNum);
		}
		public String getSliceTypeDesc() {
			return sliceTypeDesc.get();
		}
		public void setSliceTypeDesc(String sliceTypeDesc) {
			this.sliceTypeDesc = new SimpleStringProperty(sliceTypeDesc);
		}
	}
	
	/**组合切片
	 * @author Administrator
	 *
	 */
	public class ContentSlice{
		private BooleanProperty select;
		private StringProperty tissueSliceSnId;
		private StringProperty sliceCode;
		private StringProperty visceraOrTissueName;
		private StringProperty codeDate;
		private StringProperty animalCode;
		private IntegerProperty sliceCodeType;
		
		private ContentSlice(){}
		private ContentSlice(boolean select,String tissueSliceSnId,String sliceCode,String visceraOrTissueName,String codeDate,
				String animalCode,int sliceCodeType){
			setSelect(select);
			setTissueSliceSnId(tissueSliceSnId);
			setSliceCode(sliceCode);
			setVisceraOrTissueName(visceraOrTissueName);
			setCodeDate(codeDate);
			setAnimalCode(animalCode);
			setSliceCodeType(sliceCodeType);
		}
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getTissueSliceSnId() {
			return tissueSliceSnId.get();
		}
		public void setTissueSliceSnId(String tissueSliceSnId) {
			this.tissueSliceSnId = new SimpleStringProperty(tissueSliceSnId);
		}
		public String getSliceCode() {
			return sliceCode.get();
		}
		public void setSliceCode(String sliceCode) {
			this.sliceCode = new SimpleStringProperty(sliceCode);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}
		public String getCodeDate() {
			return codeDate.get();
		}
		public void setCodeDate(String codeDate) {
			this.codeDate = new SimpleStringProperty(codeDate);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public Integer getSliceCodeType() {
			return sliceCodeType.get();
		}
		public void setSliceCodeType(Integer sliceCodeType) {
			this.sliceCodeType = new SimpleIntegerProperty(sliceCodeType);
		}
		
	}
	
	// 在单元格里创建多选框
	public class CheckBoxTableCell<S, T> extends TableCell<S, T> {
			        private final CheckBox checkBox;
			        private ObservableValue<T> ov;
		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
			
		}
        @Override 
        public void updateItem(T item, boolean empty) {
        	super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
                if(checkBox.isSelected()){
//	 				getTableRow().setStyle("-fx-background-color:palegreen ;");
	 				getTableRow().setStyle("-fx-background-color:#0092DC;");
                }else{
                	getTableRow().setStyle("");
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
