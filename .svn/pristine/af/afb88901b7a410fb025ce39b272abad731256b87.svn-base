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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblTissueSliceBatchExcluded;
import com.lanen.model.path.TblTissueSliceBatchIndex;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**组织取材索引页
 * @author Administrator
 *
 */
public class TissueSliceBatch_add extends Application implements Initializable {
	
	
	/**
	 * 专题编号
	 */
	private String studyNo = "";
	/**
	 * 切片类型   1：常规非常规   2：追加
	 */
	private int sliceType;
	/**
	 * 当前 取材批次所有表
	 */
	private TblTissueSliceBatchIndex tblTissueSliceBatchIndex;
	
	/**
	 * 可编辑
	 */
	private boolean editable = false; 
	
	@FXML
	private Tab taskTab;	//解剖任务tab
	
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
	
	/**
	 * 解剖任务动物表格
	 */
	@FXML
	private TableView<Animal> animalTable_task;
	ObservableList<Animal> data_animalTable_task = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Animal,String> dosageDescCol_task;
	@FXML
	private TableColumn<Animal,String> animalCodeCol_task;
	@FXML
	private TableColumn<Animal,String> genderCol_task;
 	/**
	 * 剂量组动物表格
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
	
	/**
	 * 选择ComboBox
	 */
	@FXML
	private ComboBox<String> beginComboBox;
	ObservableList<String> data_beginComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> endComboBox;
	ObservableList<String> data_endComboBox = FXCollections.observableArrayList();
	@FXML
	private CheckBox maleCheckbox;
	@FXML
	private CheckBox femaleCheckbox;
	/**
	 * 剂量组及对应雌性数量，dosageNum,maleNum,femaleNum(打开窗口时更新，后不变)
	 */
	private List<Map<String,Object>> dosageMapList = new ArrayList<Map<String,Object>>();
	/**
	 * 剂量动物列表（专题下所有动物）,animalCode,gender,dosageNum,dosageDesc,anatomyDate(打开窗口时更新，后不变)
	 */
	private List<Map<String,Object>> dosageAnimalMapList = new ArrayList<Map<String,Object>>();
	
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
	private TableColumn<Animal,String> anatomyDateCol_select;
	
	/**
	 * 取材批次脏器列表
	 */
	@FXML
	private TableView<BatchViscera> batchVisceraTable;
	ObservableList<BatchViscera> data_batchVisceraTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<BatchViscera,Boolean> selectCol;
	@FXML
	private TableColumn<BatchViscera,String> sliceCodeCol;
	@FXML
	private TableColumn<BatchViscera,String> visceraOrTissueNameCol;
	
	/**
	 * 排除表
	 */
	@FXML
	private TableView<BatchExcluded> batchExcludedTable;
	ObservableList<BatchExcluded> data_batchExcludedTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<BatchExcluded,String> animalCodeCol;
	@FXML
	private TableColumn<BatchExcluded,String> visceraNameCol;
	@FXML
	private TableColumn<BatchExcluded,String> reasonCol;
	
	
	@FXML
	private Button signBtn;		//签字确认
	@FXML
	private Button saveBtn;		//保存
	@FXML
	private Button exitBtn;//取消
	@FXML
	private Button selectBtn;		//选择
	@FXML
	private Button addBtn_animal;	//添加——动物
	@FXML
	private Button delBtn_animal;	//删除——动物
	@FXML
	private Button addBtn_excluded;	//添加——排除
	@FXML
	private Button delBtn_excluded;	//删除——排除
	
	@FXML
	private Hyperlink selectAll_taskTable;		//全选——任务表
	@FXML
	private Hyperlink unSelectAll_taskTable;	 //全不选——任务表
	@FXML
	private Hyperlink selectAll_taskAnimal;		//全选——任务动物
	@FXML
	private Hyperlink unSelectAll_taskAnimal;	//全不选——任务动物
	@FXML
	private Hyperlink selectAll_selectAnimal;		//全选——任已选动物
	@FXML
	private Hyperlink unSelectAll_selectAnimal;	//全不选——已选动物
	@FXML
	private Hyperlink selectAll_batchViscera;		//全选——批次脏器
	@FXML
	private Hyperlink unSelectAll_batchViscera;	//全不选——批次脏器
	@FXML
	private Hyperlink autoWriteBtn;	//全不选——批次脏器
	
	private static TissueSliceBatch_add instance;
	public static TissueSliceBatch_add getInstance(){
		if(null == instance){
			instance = new TissueSliceBatch_add();
		}
		return instance;
	}

	public TissueSliceBatch_add() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//1.初始化解剖任务表
		initAnatomyTaskTable();
		//2.初始化解剖任务动物列表
		initAnimalTable_task();
		//3.初始化解剖任务动物列表
		initAnimalTable_dosage();
		//4.初始化 查询ComboBox
		initSelectComboBox();
		//5.初始化已选动物列表
		initAnimalTable_select();
		//6.初始化取材批次脏器表
		initBatchVisceraTable();
		//7.初始化排除表
		initBatchExcludedTable();
	}
	
	/**
	 * 初始化排除表
	 */
	private void initBatchExcludedTable() {
		batchExcludedTable.setItems(data_batchExcludedTable);
		batchExcludedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("animalCode"));
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("showVisceraName"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("reason"));
	}

	/**
	 * 初始化取材批次脏器表
	 */
	private void initBatchVisceraTable() {
		batchVisceraTable.setItems(data_batchVisceraTable);
		batchVisceraTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol.setCellValueFactory(new PropertyValueFactory<BatchViscera,Boolean>("select"));
		sliceCodeCol.setCellValueFactory(new PropertyValueFactory<BatchViscera,String>("sliceCode"));
		visceraOrTissueNameCol.
		setCellValueFactory(new PropertyValueFactory<BatchViscera,String>("visceraOrTissueName"));
		selectCol.setCellFactory(new Callback<TableColumn<BatchViscera, Boolean>, TableCell<BatchViscera, Boolean>>() {

            public TableCell<BatchViscera, Boolean> call(TableColumn<BatchViscera, Boolean> p) {

                return new CheckBoxTableCell<BatchViscera, Boolean>();

            }

        });
	}

	/**
	 * 初始化 查询ComboBox
	 */
	private void initSelectComboBox() {
		beginComboBox.setItems(data_beginComboBox);
		endComboBox.setItems(data_endComboBox);
	}

	/**
	 * 初始化已选动物列表
	 */
	private void initAnimalTable_select() {
		animalTable_select.setItems(data_animalTable_select);
		animalTable_select.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		anatomyDateCol_select.setCellValueFactory(new PropertyValueFactory<Animal,String>("anatomyDate"));
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
	 * 初始化解剖任务动物列表
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
	 * 初始化解剖任务动物列表
	 */
	private void initAnimalTable_task() {
		animalTable_task.setItems(data_animalTable_task);
		animalTable_task.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol_task.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol_task.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol_task.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		//动物数量居中,选中设置行颜色
		genderCol_task.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			    	 
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
			animalTable_task.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
								.getSelect(), obj.getTaskId(),obj.getPlanAnatomyDate(),obj.getAnimalNum());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
					//更新解剖任务动物列表
					updateAnimalTable_task();
				}

			}

		});
	}

	/**选中
	 * @param event
	 */
	@FXML
	private void onSelectBtnAction(ActionEvent event){
		selectAnimalTable_dosage();
	}
	/**添加——排除
	 * @param event
	 */
	@FXML
	private void onAddBtnAction_excluded(ActionEvent event){
		//1.选择动物
		//1.查找待删除动物
		List<String> selectAnimalCodeList = new ArrayList<String>();
		for(Animal obj:data_animalTable_select){
			if(obj.getSelect()){
				selectAnimalCodeList.add(obj.getAnimalCode());
			}
		}
		if(selectAnimalCodeList.size() < 1){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//2.选择脏器
		String selectVisceraName = "";
		String tissueSliceVisceraId = "";
		
		BatchViscera selectedItem = batchVisceraTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			if(!selectedItem.getSliceCode().contains("g")){
				if(selectedItem.getSelect()){
					selectVisceraName = selectedItem.getVisceraOrTissueName();
					tissueSliceVisceraId = selectedItem.getTissueSliceVisceraId();
				}else{
					showErrorMessage("请选择已选脏器！");
					return ;
				}
			}else{
				showErrorMessage("请选择常规编号脏器！");
				return ;
			}
		}else{
			showErrorMessage("请先选择脏器！");
			return ;
		}
		//3.检查是否有重复添加
//		for(String animalCode:selectAnimalCodeList){
//			for(BatchExcluded obj:data_batchExcludedTable){
//				if(animalCode.equals(obj.getAnimalCode() )
//						&& tissueSliceVisceraId.equals(obj.getTissueSliceVisceraId())){
//					showErrorMessage("排除的脏器表中已存在["+animalCode+" "+visceraName+"]！");
//					return ;
//				}
//			}
//		}
		boolean isexist = isExistInBatchExcluded(selectVisceraName);
		if(isexist){
			return ;
		}
		//4.选择脏器及排除原因
		String reason ="";
		Map<String,Object> visceraMap = null;
		Stage stage = Main.stageMap.get("TissueSliceBatch_reason");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceBatch_reason.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceBatch_reason",stage);
		}
		TissueSliceBatch_reason.getInstance().loadData(tissueSliceVisceraId,selectVisceraName);
		stage.showAndWait();
		
		if(TissueSliceBatch_reason.getInstance().isConfirm()){
			reason = TissueSliceBatch_reason.getInstance().getReason();
			visceraMap = TissueSliceBatch_reason.getInstance().getVisceraMap();
		}else{
			return;
		}
		
		//5.添加
		Integer visceraType = (Integer) visceraMap.get("visceraType");
		String visceraCode = (String) visceraMap.get("visceraCode");
		String visceraName = (String) visceraMap.get("visceraName");
		String subVisceraCode = (String) visceraMap.get("subVisceraCode");
		String subVisceraName = (String) visceraMap.get("subVisceraName");
		for(String animalCode:selectAnimalCodeList){
			data_batchExcludedTable.add(new BatchExcluded(animalCode,tissueSliceVisceraId,reason,
					visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName));
		}
		//6.排序
		Collections.sort(data_batchExcludedTable, new Comparator<BatchExcluded>(){

			@Override
			public int compare(BatchExcluded o1, BatchExcluded o2) {
				if(NumberValidationUtils.isPositiveInteger(o1.getAnimalCode()) &&
						NumberValidationUtils.isPositiveInteger(o2.getAnimalCode())	){
					return Integer.parseInt(o1.getAnimalCode()) -Integer.parseInt(o2.getAnimalCode());
				}
				return 0;
			}
			
		});
		//6.清楚动物选择，脏器选择
		onUnSelectAllAction_selectAnimal(null);
		batchVisceraTable.getSelectionModel().clearSelection();
		//7.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("取消");
	}
	
	/**是否存在，在排除表中
	 * @param animalCode
	 * @param visceraName
	 * @return
	 */
	public boolean isExistInBatchExcluded(String visceraName){
		List<String> selectAnimalCodeList = new ArrayList<String>();
		for(Animal obj:data_animalTable_select){
			if(obj.getSelect()){
				selectAnimalCodeList.add(obj.getAnimalCode());
			}
		}
		for(String animalCode:selectAnimalCodeList){
			for(BatchExcluded obj:data_batchExcludedTable){
				if(animalCode.equals(obj.getAnimalCode() )
						&& visceraName.equals(obj.getShowVisceraName())){
					showErrorMessage("排除的脏器表中已存在["+animalCode+" "+visceraName+"]！");
					return true;
				}
			}
		}
		 return false;
	}
	
	/**删除——排除
	 * @param event
	 */
	@FXML
	private void onDelBtnAction_excluded(ActionEvent event){
		//1.选择排除的动物脏器
		BatchExcluded selectedItem = batchExcludedTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请选择排除的动物脏器！");
			return ;
		}
		//2.删除
		data_batchExcludedTable.remove(selectedItem);
		//3.清空选择
		batchExcludedTable.getSelectionModel().clearSelection();
		//4.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("取消");
	}
	/**添加——动物
	 * @param event
	 */
	@FXML
	private void onAddBtnAction_animal(ActionEvent event){
		//1.查找待添加动物
		List<Animal> selectAnimalList = new ArrayList<Animal>();
		List<String> selectAnimalCodeList = new ArrayList<String>();
		if(taskTab.isSelected()){
			for(Animal obj:data_animalTable_task){
				if(obj.getSelect()){
					selectAnimalList.add(obj);
					selectAnimalCodeList.add(obj.getAnimalCode());
				}
			}
		}else{
			for(Animal obj:data_animalTable_dosage){
				if(obj.getSelect()){
					selectAnimalList.add(obj);
					selectAnimalCodeList.add(obj.getAnimalCode());
				}
			}
		}
		if(selectAnimalList.size() < 1){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//2.已选动物列表的全不选
		onUnSelectAllAction_selectAnimal(null);
		//3.添加动物
		for(Animal obj:selectAnimalList){
			selectAnimalCodeSet.add(obj.getAnimalCode());
			data_animalTable_select.add(new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
		}
		//4.删除待选
		updateAnimalTable_task();
		updateAnimalTable_dosage();
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
		if(sliceType == 1){
			//6.添加非常规编号脏器到取材批次脏器列表，未选中
			addFCGCodeVisceraToBatchVisceraTable(selectAnimalCodeList);
		}
		//6.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("取消");
	}
	/**删除——动物
	 * @param event
	 */
	@FXML
	private void onDelBtnAction_animal(ActionEvent event){
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
		//2.删除动物
		data_animalTable_select.removeAll(selectAnimalList);
		//3.清除  selectAnimalCodeSet中对应动物编号
		for(Animal obj:selectAnimalList){
			selectAnimalCodeSet.remove(obj.getAnimalCode());
		}
		//4.更新任务动物表和剂量动物表
		updateAnimalTable_task();
		updateAnimalTable_dosage();
		
		//5.选中删除项
		animalTalbeSelectAllByAnimalCodelList(animalTable_task,selectAnimalCodeList);
		animalTalbeSelectAllByAnimalCodelList(animalTable_dosage,selectAnimalCodeList);
		
		if(sliceType == 1){
			//6.删除非常规编号脏器在取材批次脏器列表
			delFCGCodeVisceraToBatchVisceraTable(selectAnimalCodeList);
		}
		//6.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("取消");
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
					items.set(i, new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
				}
				i++;
			}
		}
		animalTableScrollToSelectIndex(animalTable);
	}
	/**全不选_批次脏器
	 * @param event
	 */
	@FXML
	private void onUnSelectAllAction_batchViscera(ActionEvent event){
		ObservableList<BatchViscera> data_batchVisceraTable2 = FXCollections.observableArrayList(data_batchVisceraTable);
		data_batchVisceraTable.clear();
		if(null != data_batchVisceraTable2 && data_batchVisceraTable2.size() > 0){
			for(BatchViscera obj:data_batchVisceraTable2){
				data_batchVisceraTable.add(new BatchViscera(false,obj.getTissueSliceVisceraId(),obj.getSliceCode(),
						obj.getVisceraOrTissueName(),obj.getVisceraCode(),obj.getVisceraName(),
						obj.getSubVisceraCode(),obj.getSubVisceraName()));
			}
		}
	}
	/**全选_批次脏器
	 * @param event
	 */
	@FXML
	private void onSelectAllAction_batchViscera(ActionEvent event){
		ObservableList<BatchViscera> data_batchVisceraTable2 = FXCollections.observableArrayList(data_batchVisceraTable);
		data_batchVisceraTable.clear();
		if(null != data_batchVisceraTable2 && data_batchVisceraTable2.size() > 0){
			for(BatchViscera obj:data_batchVisceraTable2){
				data_batchVisceraTable.add(new BatchViscera(true,obj.getTissueSliceVisceraId(),obj.getSliceCode(),
						obj.getVisceraOrTissueName(),obj.getVisceraCode(),obj.getVisceraName(),
						obj.getSubVisceraCode(),obj.getSubVisceraName()));
			}
		}
	}
	/**全不选_任务
	 * @param event
	 */
	@FXML
	private void onUnSelectAllAction_taskTalbe(ActionEvent event){
		ObservableList<AnatomyTask> data_anatomyTaskTable2 = FXCollections.observableArrayList(data_anatomyTaskTable);
		data_anatomyTaskTable.clear();
		if(null != data_anatomyTaskTable2 && data_anatomyTaskTable2.size() > 0){
			for(AnatomyTask obj:data_anatomyTaskTable2){
				data_anatomyTaskTable.add(new AnatomyTask(false,obj.getTaskId(),obj.getPlanAnatomyDate(),obj.getAnimalNum()));
			}
		}
		data_animalTable_task.clear();
	}
	/**全选_任务
	 * @param event
	 */
	@FXML
	private void onSelectAllAction_taskTalbe(ActionEvent event){
		ObservableList<AnatomyTask> data_anatomyTaskTable2 = FXCollections.observableArrayList(data_anatomyTaskTable);
		data_anatomyTaskTable.clear();
		if(null != data_anatomyTaskTable2 && data_anatomyTaskTable2.size() > 0){
			for(AnatomyTask obj:data_anatomyTaskTable2){
				data_anatomyTaskTable.add(new AnatomyTask(true,obj.getTaskId(),obj.getPlanAnatomyDate(),obj.getAnimalNum()));
			}
		}
		updateAnimalTable_task();
	}
	/**全不选_任务动物
	 * @param event
	 */
	@FXML
	private void onUnSelectAllAction_taskAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable_task2 = FXCollections.observableArrayList(data_animalTable_task);
		data_animalTable_task.clear();
		if(null != data_animalTable_task2 && data_animalTable_task2.size() > 0){
			for(Animal obj:data_animalTable_task2){
				data_animalTable_task.add(new Animal(false,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全选_任务
	 * @param event
	 */
	@FXML
	private void onSelectAllAction_taskAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable_task2 = FXCollections.observableArrayList(data_animalTable_task);
		data_animalTable_task.clear();
		if(null != data_animalTable_task2 && data_animalTable_task2.size() > 0){
			for(Animal obj:data_animalTable_task2){
				data_animalTable_task.add(new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全不选_已选动物
	 * @param event
	 */
	@FXML
	private void onUnSelectAllAction_selectAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable_select2 = FXCollections.observableArrayList(data_animalTable_select);
		data_animalTable_select.clear();
		if(null != data_animalTable_select2 && data_animalTable_select2.size() > 0){
			for(Animal obj:data_animalTable_select2){
				data_animalTable_select.add(new Animal(false,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**全选_已选动物
	 * @param event
	 */
	@FXML
	private void onSelectAllAction_selectAnimal(ActionEvent event){
		ObservableList<Animal> data_animalTable_select2 = FXCollections.observableArrayList(data_animalTable_select);
		data_animalTable_select.clear();
		if(null != data_animalTable_select2 && data_animalTable_select2.size() > 0){
			for(Animal obj:data_animalTable_select2){
				data_animalTable_select.add(new Animal(true,obj.getDosageDesc(),obj.getAnimalCode(),obj.getGender(),obj.getAnatomyDate()));
			}
		}
	}
	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignBtnAction(ActionEvent event){
		//1.选择的动物
		List<String> animalCodeList = new ArrayList<String>();
		if(selectAnimalCodeSet.size() < 1){
			showErrorMessage("请先选择动物！");
			return;
		}else{
			for(Animal obj:data_animalTable_select){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		//2.选择的脏器或组织
		List<String> tissueSliceVisceraIdList = new ArrayList<String>();
		for(BatchViscera obj:data_batchVisceraTable){
			if(obj.getSelect()){
				tissueSliceVisceraIdList.add(obj.getTissueSliceVisceraId());
			}
		}
		if(tissueSliceVisceraIdList.size() < 1){
			showErrorMessage("请选择脏器或组织！");
			return;
		}
		
		//3.排除的脏器或组织
		List<TblTissueSliceBatchExcluded> batchExcludedList = new ArrayList<TblTissueSliceBatchExcluded>();
		TblTissueSliceBatchExcluded tblTissueSliceBatchExcluded= null;
		for(BatchExcluded obj:data_batchExcludedTable){
			tblTissueSliceBatchExcluded = new TblTissueSliceBatchExcluded();
			tblTissueSliceBatchExcluded.setAnimalCode(obj.getAnimalCode());
			tblTissueSliceBatchExcluded.setTissueSliceVisceraId(obj.getTissueSliceVisceraId());
			tblTissueSliceBatchExcluded.setReason(obj.getReason());
			
			tblTissueSliceBatchExcluded.setVisceraType(obj.getVisceraType());
			tblTissueSliceBatchExcluded.setVisceraCode(obj.getVisceraCode());
			tblTissueSliceBatchExcluded.setVisceraName(obj.getVisceraName());
			tblTissueSliceBatchExcluded.setSubVisceraCode(obj.getSubVisceraCode());
			tblTissueSliceBatchExcluded.setSubVisceraName(obj.getSubVisceraName());
			
			batchExcludedList.add(tblTissueSliceBatchExcluded);
		}
		
		//4.准备索引
		if(null == tblTissueSliceBatchIndex){
			tblTissueSliceBatchIndex = new TblTissueSliceBatchIndex();
			tblTissueSliceBatchIndex.setStudyNo(studyNo);
			tblTissueSliceBatchIndex.setSliceType(sliceType);
			
			//5.保存
			tblTissueSliceBatchIndex = BaseService.getInstance().getTblTissueSliceBatchService().
					saveOne(tblTissueSliceBatchIndex,animalCodeList,tissueSliceVisceraIdList,batchExcludedList);
		}else{
			//5.更新
			tblTissueSliceBatchIndex = BaseService.getInstance().getTblTissueSliceBatchService().
					updateOne(tblTissueSliceBatchIndex,animalCodeList,tissueSliceVisceraIdList,batchExcludedList);
		}
		
		//6.签字窗口
		SignMeFrame signMeFrame = new SignMeFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("签字确认");
		try {
			signMeFrame.start(stage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//7.提交签字
		//签字通过
		if("OK".equals(SignMeFrame.getType())){
			tblTissueSliceBatchIndex = BaseService.getInstance().getTblTissueSliceBatchService()
					.signTissueSliceBatch(tblTissueSliceBatchIndex,SaveUserInfo.getRealName());
			
			//8.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
			exitBtn.setText("关闭");
			//9.设置按钮状态
			setButtonDisable(true);
			showMessage("签字成功！");
		}
		
	}
	/**保存
	 * @param event
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		//1.选择的动物
		List<String> animalCodeList = new ArrayList<String>();
		if(selectAnimalCodeSet.size() < 1){
			showErrorMessage("请先选择动物！");
			return;
		}else{
			for(Animal obj:data_animalTable_select){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		//2.选择的脏器或组织
		List<String> tissueSliceVisceraIdList = new ArrayList<String>();
		for(BatchViscera obj:data_batchVisceraTable){
			if(obj.getSelect()){
				tissueSliceVisceraIdList.add(obj.getTissueSliceVisceraId());
			}
		}
		if(tissueSliceVisceraIdList.size() < 1){
			showErrorMessage("请选择脏器或组织！");
			return;
		}
		
		//3.排除的脏器或组织
		List<TblTissueSliceBatchExcluded> batchExcludedList = new ArrayList<TblTissueSliceBatchExcluded>();
		TblTissueSliceBatchExcluded tblTissueSliceBatchExcluded= null;
		for(BatchExcluded obj:data_batchExcludedTable){
			tblTissueSliceBatchExcluded = new TblTissueSliceBatchExcluded();
			tblTissueSliceBatchExcluded.setAnimalCode(obj.getAnimalCode());
			tblTissueSliceBatchExcluded.setTissueSliceVisceraId(obj.getTissueSliceVisceraId());
			tblTissueSliceBatchExcluded.setReason(obj.getReason());
			
			tblTissueSliceBatchExcluded.setVisceraType(obj.getVisceraType());
			tblTissueSliceBatchExcluded.setVisceraCode(obj.getVisceraCode());
			tblTissueSliceBatchExcluded.setVisceraName(obj.getVisceraName());
			tblTissueSliceBatchExcluded.setSubVisceraCode(obj.getSubVisceraCode());
			tblTissueSliceBatchExcluded.setSubVisceraName(obj.getSubVisceraName());
			
			batchExcludedList.add(tblTissueSliceBatchExcluded);
		}
		
		//4.准备索引
		if(null == tblTissueSliceBatchIndex){
			tblTissueSliceBatchIndex = new TblTissueSliceBatchIndex();
			tblTissueSliceBatchIndex.setStudyNo(studyNo);
			tblTissueSliceBatchIndex.setSliceType(sliceType);
			
			//5.保存
			tblTissueSliceBatchIndex = BaseService.getInstance().getTblTissueSliceBatchService().
					saveOne(tblTissueSliceBatchIndex,animalCodeList,tissueSliceVisceraIdList,batchExcludedList);
		}else{
			//5.更新
			tblTissueSliceBatchIndex = BaseService.getInstance().getTblTissueSliceBatchService().
					updateOne(tblTissueSliceBatchIndex,animalCodeList,tissueSliceVisceraIdList,batchExcludedList);
		}
		//6.保存成功
		showMessage("保存成功！");
		//7.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("关闭");
		
	}

	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		if(null != tblTissueSliceBatchIndex){
			TissueSliceBatch.getInstance().updateBatchIndexTable(tblTissueSliceBatchIndex.getId());
		}
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**自动填写
	 * @param event
	 */
	@FXML
	private void onAutoWriteBtnAction(ActionEvent event){
		//1.查询需要填写的动物编号列表	
		List<String> animalCodeList = new ArrayList<String>();
		if(selectAnimalCodeSet.size() < 1){
			showErrorMessage("请先选择动物！");
			return;
		}else{
			for(Animal obj:data_animalTable_select){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		//2.查询该专题下，这些动物的自溶、缺如、缺失脏器列表 
		//		animalCode,visceraCode,visceraName,subVisceraCode,subVisceraName,reason
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService()
				.getMissViscera(studyNo,animalCodeList);
		//3.查询选中的脏器，并添加
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> obj:mapList){
				String animalCode = (String) obj.get("animalCode");
				Integer visceraType = (Integer) obj.get("visceraType");
				String visceraName = (String) obj.get("visceraName");
				String visceraCode = (String) obj.get("visceraCode");
				String subVisceraCode = (String) obj.get("subVisceraCode");
				String subVisceraName = (String) obj.get("subVisceraName");
				String reason = (String) obj.get("reason");
				if(null != subVisceraCode && !"".equals(subVisceraCode)){
					//子脏器
					for(BatchViscera batchViscera:data_batchVisceraTable){
						if(batchViscera.getSelect()){
							String sliceCode = batchViscera.getSliceCode();
							//常规、或追加的
							if(!sliceCode.contains("g")){
								String subVisceraCode_ = batchViscera.getSubVisceraCode();
								if(subVisceraCode.equals(subVisceraCode_)){
									String tissueSliceVisceraId = batchViscera.getTissueSliceVisceraId();
									//检查是否存在，若不存在，则添加
									boolean exist = false;
									for(BatchExcluded batchExcluded:data_batchExcludedTable){
										if(animalCode.equals(batchExcluded.getAnimalCode() )
												&& subVisceraName.equals(batchExcluded.getShowVisceraName())){
											exist = true;
											break;
										}
									}
									//不存在
									if(!exist){
										//添加
										data_batchExcludedTable.add(new BatchExcluded(animalCode,tissueSliceVisceraId,reason,
												visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName));
										break;
									}
								}
							}
						}
					}
				}else{
					//父脏器
					for(BatchViscera batchViscera:data_batchVisceraTable){
						if(batchViscera.getSelect()){
							String sliceCode = batchViscera.getSliceCode();
							//常规、或追加的
							if(!sliceCode.contains("g")){
								String visceraCode_ = batchViscera.getVisceraCode();
								String subVisceraCode_ = batchViscera.getSubVisceraCode();
								if(visceraCode.equals(visceraCode_) && (null ==subVisceraCode_ || "".equals(subVisceraCode_) )){
									String tissueSliceVisceraId = batchViscera.getTissueSliceVisceraId();
									//检查是否存在，若不存在，则添加
									boolean exist = false;
									for(BatchExcluded batchExcluded:data_batchExcludedTable){
										if(animalCode.equals(batchExcluded.getAnimalCode() )
												&& visceraName.equals(batchExcluded.getShowVisceraName())){
											exist = true;
											break;
										}
									}
									//不存在
									if(!exist){
										//添加
										data_batchExcludedTable.add(new BatchExcluded(animalCode,tissueSliceVisceraId,reason,
												visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName));
										//6.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
										exitBtn.setText("取消");
										break;
									}
								}
							}
						}
					}
				}
			}
			
			
			//4.排序
			Collections.sort(data_batchExcludedTable, new Comparator<BatchExcluded>(){
	
				@Override
				public int compare(BatchExcluded o1, BatchExcluded o2) {
					if(NumberValidationUtils.isPositiveInteger(o1.getAnimalCode()) &&
							NumberValidationUtils.isPositiveInteger(o2.getAnimalCode())	){
						return Integer.parseInt(o1.getAnimalCode()) -Integer.parseInt(o2.getAnimalCode());
					}
					return 0;
				}
				
			});
		}
	}
	/**
	 * 加载数据（新建时）
	 * @param taskId 
	 */
	public void loadData(String studyNo,int sliceType) {
		this.studyNo = studyNo;
		this.sliceType = sliceType;
		tblTissueSliceBatchIndex = null;
		selectAnimalCodeSet.clear();
		
		//1.更新解剖任务表格及查询所有解剖动物
		updateAnatomyTaskTable();
		//2.查询剂量组列表，及剂量组动物列表
		selectDosageAnimalMapList();
		//3.更新查询ComboBox及CheckBox
		updateSelectComboBox();
		//4.更新剂量动物列表
		updateAnimalTable_dosage();
		//5.若是常规非常规，则加载常规编号列表
		if(this.sliceType == 1){
			//加载常规编号脏器到取材批次脏器列表，未选中
			loadCGCodeVisceraToBatchVisceraTable();
		}else{
			//加载追加编号脏器到取材批次脏器列表，未选中
			loadZJCodeVisceraToBatchVisceraTable();
		}
		//6.清空已选动物列表和任务动物列表以及排除表
		data_animalTable_select.clear();
		data_animalTable_task.clear();
		data_batchExcludedTable.clear();
		//7.按钮设为可用状态
		setButtonDisable(false);
		
		//8.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("关闭");
	}
	/**
	 * 加载数据（编辑时）
	 * @param taskId 
	 */
	public void loadData(TblTissueSliceBatchIndex tblTissueSliceBatchIndex) {
		this.studyNo = tblTissueSliceBatchIndex.getStudyNo();
		this.sliceType = tblTissueSliceBatchIndex.getSliceType();
		this.tblTissueSliceBatchIndex = tblTissueSliceBatchIndex;
		//1.更新已选动物表
		updateAnimalTable_select();
					
		//2.更新解剖任务表格及查询所有解剖动物
		updateAnatomyTaskTable();
		//3.查询剂量组列表，及剂量组动物列表
		selectDosageAnimalMapList();
		//4.更新查询ComboBox及CheckBox
		updateSelectComboBox();
		//5.更新剂量动物列表
		updateAnimalTable_dosage();
		//6.若是常规非常规，则加载常规编号列表及非常规编号列表
		if(this.sliceType == 1){
			//加载常规编号脏器到取材批次脏器列表，未选中
			loadCGCodeVisceraToBatchVisceraTable();
			//添加非常规编号脏器到取材批次脏器列表，未选中
			addFCGCodeVisceraToBatchVisceraTable(new ArrayList<String>(selectAnimalCodeSet));
		}else{
			//加载追加编号脏器到取材批次脏器列表，未选中
			loadZJCodeVisceraToBatchVisceraTable();
		}
		//7.清空任务动物列表
		data_animalTable_task.clear();
		//8.按钮设状态
		if(null != tblTissueSliceBatchIndex && null != tblTissueSliceBatchIndex.getOperatorSign()
				&& !"".equals(tblTissueSliceBatchIndex.getOperatorSign())){
			setButtonDisable(true);
		}else{
			setButtonDisable(false);
		}
		//9.刚进入、保存、签字等过后设为 关闭，，，添加、删除等后设为 取消
		exitBtn.setText("关闭");
		//10.设置批次脏器或组织列表 选中状态
		setBatchVisceraTableSelectState();
		//11.更新排除表
		updateBatchExcludedTable();
	}
	
	/**
	 * 设置批次脏器或组织列表 选中状态
	 */
	private void setBatchVisceraTableSelectState() {
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceBatchService().getBatchVisceraMapListByBatchId(tblTissueSliceBatchIndex.getId());
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
				String tissueSliceVisceraId = (String) obj.get("tissueSliceVisceraId");
//				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				int index = 0;
				for(BatchViscera batchViscera:data_batchVisceraTable){
					if(tissueSliceVisceraId.equals(batchViscera.getTissueSliceVisceraId())){
						data_batchVisceraTable.set(index,new BatchViscera(true,batchViscera.getTissueSliceVisceraId(),
								batchViscera.getSliceCode(),batchViscera.getVisceraOrTissueName(),
								batchViscera.getVisceraCode(),batchViscera.getVisceraName(),
								batchViscera.getSubVisceraCode(),batchViscera.getSubVisceraName()));
					}
					index++;
				}
			}
		}
		
	}

	/**
	 * 更新排除表
	 */
	private void updateBatchExcludedTable() {
		data_batchExcludedTable.clear();
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblTissueSliceBatchService().getBatchExcludedMapListByBatchId(tblTissueSliceBatchIndex.getId());
			if(null != mapList && mapList.size() > 0){
				for(Map<String, Object> obj:mapList){
					String animalCode = (String) obj.get("animalCode");
//					String visceraName = (String) obj.get("visceraName");
					String reason = (String) obj.get("reason");
					String tissueSliceVisceraId = (String) obj.get("tissueSliceVisceraId");
					//TODO
					Integer visceraType = (Integer) obj.get("visceraType");
					String visceraCode = (String) obj.get("visceraCode");
					String visceraName = (String) obj.get("visceraName");
					String subVisceraCode = (String) obj.get("subVisceraCode");
					String subVisceraName = (String) obj.get("subVisceraName");
					
					data_batchExcludedTable.add(new BatchExcluded(animalCode,tissueSliceVisceraId,reason,
							visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName));
				}
			}
		
	}

	/**
	 * 更新已选动物表
	 */
	private void updateAnimalTable_select() {
		data_animalTable_select.clear();
		selectAnimalCodeSet.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService()
				.getBatchAnimalMapListByBatchId(tblTissueSliceBatchIndex.getId());
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
//				animalCode,gender,dosageNum,dosageDesc,anatomyDate
				String animalCode = (String) obj.get("animalCode");
				String dosageDesc = (String) obj.get("dosageDesc");
				Integer gender = (Integer) obj.get("gender");
				String anatomyDate = (String) obj.get("anatomyDate");
				
				selectAnimalCodeSet.add(animalCode);
				data_animalTable_select.add(new Animal(false,dosageDesc,animalCode,gender,anatomyDate));
			}
		}
		
	}

	/**设置按钮状态
	 * @param flag
	 */
	private void setButtonDisable(boolean flag){
		
		editable = !flag;
		
		signBtn.setDisable(flag);		//签字确认
		saveBtn.setDisable(flag);		//保存
		selectBtn.setDisable(flag);		//选择
		addBtn_animal.setDisable(flag);	//添加——动物
		delBtn_animal.setDisable(flag);	//删除——动物
		addBtn_excluded.setDisable(flag);	//添加——排除
		delBtn_excluded.setDisable(flag);	//删除——排除
		selectAll_taskTable.setDisable(flag);		//全选——任务表
		unSelectAll_taskTable.setDisable(flag);	 //全不选——任务表
		selectAll_taskAnimal.setDisable(flag);		//全选——任务动物
		unSelectAll_taskAnimal.setDisable(flag);	//全不选——任务动物
		selectAll_selectAnimal.setDisable(flag);		//全选——任已选动物
		unSelectAll_selectAnimal.setDisable(flag);	//全不选——已选动物
		selectAll_batchViscera.setDisable(flag);		//全选——批次脏器
		unSelectAll_batchViscera.setDisable(flag);	//全不选——批次脏器
		autoWriteBtn.setDisable(flag);	//全不选——批次脏器
	}
	/**
	 * 加载常规编号脏器到取材批次脏器列表，新增的未选中
	 */
	private void loadZJCodeVisceraToBatchVisceraTable() {
		data_batchVisceraTable.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceBatchService().getZJSliceCodeVisceraMapListByStudyNo(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
				String tissueSliceVisceraId = (String) obj.get("tissueSliceVisceraId");
				String sliceCode = (String) obj.get("sliceCode");
				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				
				String visceraCode = (String) obj.get("visceraCode");
				String visceraName = (String) obj.get("visceraName");
				String subVisceraCode = (String) obj.get("subVisceraCode");
				String subVisceraName = (String) obj.get("subVisceraName");
				
				data_batchVisceraTable.add(new BatchViscera(false,tissueSliceVisceraId,
						sliceCode,visceraOrTissueName,visceraCode,visceraName,
						subVisceraCode,subVisceraName));
			}
		}
	}
	/**
	 * 加载常规编号脏器到取材批次脏器列表，新增的未选中
	 */
	private void loadCGCodeVisceraToBatchVisceraTable() {
		data_batchVisceraTable.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceBatchService().getCGSliceCodeVisceraMapListByStudyNo(studyNo);
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
				String tissueSliceVisceraId = (String) obj.get("tissueSliceVisceraId");
				String sliceCode = (String) obj.get("sliceCode");
				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				
				String visceraCode = (String) obj.get("visceraCode");
				String visceraName = (String) obj.get("visceraName");
				String subVisceraCode = (String) obj.get("subVisceraCode");
				String subVisceraName = (String) obj.get("subVisceraName");
				
				data_batchVisceraTable.add(new BatchViscera(false,tissueSliceVisceraId,
						sliceCode,visceraOrTissueName,visceraCode,visceraName,
						subVisceraCode,subVisceraName));
			}
		}
	}
	/**
	 * 添加非常规编号脏器到取材批次脏器列表，新增的为未选中
	 */
	private void addFCGCodeVisceraToBatchVisceraTable(List<String> animalCodeList) {
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceBatchService().getFCGSliceCodeVisceraMapList(studyNo,animalCodeList);
		if(null != mapList && mapList.size() > 0){
			for(Map<String, Object> obj:mapList){
				String tissueSliceVisceraId = (String) obj.get("tissueSliceVisceraId");
				String sliceCode = (String) obj.get("sliceCode");
				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				
				String visceraCode = (String) obj.get("visceraCode");
				String visceraName = (String) obj.get("visceraName");
				String subVisceraCode = (String) obj.get("subVisceraCode");
				String subVisceraName = (String) obj.get("subVisceraName");
				
				data_batchVisceraTable.add(new BatchViscera(false,tissueSliceVisceraId,
						sliceCode,visceraOrTissueName,visceraCode,visceraName,
						subVisceraCode,subVisceraName));
			}
		}
	}
	/**
	 * 删除非常规编号脏器在取材批次脏器列表
	 */
	private void delFCGCodeVisceraToBatchVisceraTable(List<String> animalCodeList) {
		if(null != animalCodeList && animalCodeList.size() > 0){
			List<BatchViscera> waitDelList = new ArrayList<BatchViscera>();
			for(BatchViscera obj:data_batchVisceraTable){
				String animalCode = obj.getSliceCode();
				if(animalCode.contains("g")){
					animalCode = animalCode.substring(0,animalCode.indexOf("g"));
					if(animalCodeList.contains(animalCode)){
						waitDelList.add(obj);
					}
				}
			}
			if(waitDelList.size() > 0){
				data_batchVisceraTable.removeAll(waitDelList);
			}
		}
	}

	/**
	 * 更新剂量动物列表
	 */
	private void updateAnimalTable_dosage(){
		
		data_animalTable_dosage.clear();
		
		//更新剂量动物列表
		if(null != dosageAnimalMapList && dosageAnimalMapList.size() > 0){
			for(Map<String, Object> obj:dosageAnimalMapList){
//				animalCode,gender,dosageNum,dosageDesc,anatomyDate
				String animalCode = (String) obj.get("animalCode");
				if(!selectAnimalCodeSet.contains(animalCode)){
					String dosageDesc = (String) obj.get("dosageDesc");
					Integer gender = (Integer) obj.get("gender");
					String anatomyDate = (String) obj.get("anatomyDate");
					data_animalTable_dosage.add(new Animal(false,dosageDesc,animalCode,gender,anatomyDate));
				}
			}
		}
		
	}
	
	/**
	 * 选择剂量动物列表
	 */
	private void selectAnimalTable_dosage(){
		
		data_animalTable_dosage.clear();
		
		int minNum = beginComboBox.getSelectionModel().getSelectedIndex()+1;
		int maxNum = endComboBox.getSelectionModel().getSelectedIndex()+1;
		if(null != dosageMapList && dosageMapList.size() > 0 && 
				null != dosageAnimalMapList && dosageAnimalMapList.size() > 0){
			for(Map<String,Object> dosageMap:dosageMapList){
				//
				Integer dosageNum = (Integer) dosageMap.get("dosageNum");
				int maleNumIndex = 1;
				int femaleNumIndex = 1;
				for(Map<String, Object> obj:dosageAnimalMapList){
//					animalCode,gender,dosageNum,dosageDesc,anatomyDate
					Integer currentDosageNum = (Integer) obj.get("dosageNum");
					if(currentDosageNum.intValue() == dosageNum.intValue()){
						//当前剂量组
						String animalCode = (String) obj.get("animalCode");
						Integer gender = (Integer) obj.get("gender");
						if(!selectAnimalCodeSet.contains(animalCode)){
							String dosageDesc = (String) obj.get("dosageDesc");
							String anatomyDate = (String) obj.get("anatomyDate");
							boolean select = false;
							if(gender == 1 && maleCheckbox.isSelected() && 
								( maleNumIndex >= minNum && maleNumIndex <= maxNum
								||  maleNumIndex >= maxNum && maleNumIndex <= minNum )){
								select = true;
							}else if(gender == 2 && femaleCheckbox.isSelected() && 
									( femaleNumIndex >= minNum && femaleNumIndex <= maxNum
									||  femaleNumIndex >= maxNum && femaleNumIndex <= minNum )){
								select = true;
							}
							data_animalTable_dosage.add(new Animal(select,dosageDesc,animalCode,gender,anatomyDate));
						}
						
						if(gender == 1){
							maleNumIndex++;
						}else{
							femaleNumIndex++;
						}
					}
				}
			}
		}
		animalTableScrollToSelectIndex(animalTable_dosage);
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
	
	/**
	 * 更新查询ComboBox及CheckBox
	 */
	private void updateSelectComboBox() {
		data_beginComboBox.clear();
		data_endComboBox.clear();
		//剂量组中，最多组的动物数量
		int maxNum = 0;
		if(null != dosageMapList && dosageMapList.size() > 0){
			for(Map<String,Object> map:dosageMapList){
				Integer maleNum = (Integer) map.get("maleNum");
				Integer femaleNum = (Integer) map.get("femaleNum");
				if(maleNum > maxNum){
					maxNum = maleNum ;
				}
				if(femaleNum > maxNum){
					maxNum = femaleNum ;
				}
			}
		}
		if(maxNum > 0){
			for(int i = 1;i<= maxNum;i++){
				data_beginComboBox.add(i+"");
				data_endComboBox.add(i+"");
			}
			beginComboBox.getSelectionModel().selectFirst();
			endComboBox.getSelectionModel().selectFirst();
		}
		
		maleCheckbox.setSelected(true);
		femaleCheckbox.setSelected(true);
	}

	/**
	 * 查询剂量组列表，及剂量组动物列表
	 */
	private void selectDosageAnimalMapList() {
		dosageMapList = BaseService.getInstance().getTblTissueSliceBatchService().getDosageMapListByStudyNo(studyNo);
		dosageAnimalMapList = BaseService.getInstance().getTblTissueSliceBatchService().getDosageAnimalMapListByStudyNo(studyNo);
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
				data_anatomyTaskTable.add(new AnatomyTask(false,taskId,planAnatomyDate,animalNum+""));
			}
		}
		
		taskAnimaMaplList = BaseService.getInstance().getTblTissueSliceBatchService().getTaskAnimalMapListByStudyNo(studyNo);
	}
	
	/**
	 * 更新解剖任务动物列表
	 */
	private void updateAnimalTable_task(){
		
		data_animalTable_task.clear();
		//存选中的TaskId
		Set<String> taskIdSet = new HashSet<String>();
		//取选中的taskId
		if(null != data_anatomyTaskTable && data_anatomyTaskTable.size() > 0){
			for(AnatomyTask obj:data_anatomyTaskTable){
				if(obj.getSelect()){
					taskIdSet.add(obj.getTaskId());
				}
			}
		}
		//更新任务动物列表
		if(null != taskAnimaMaplList && taskAnimaMaplList.size() > 0 && taskIdSet.size() > 0){
			for(Map<String, Object> obj:taskAnimaMaplList){
				String taskId = (String) obj.get("taskId");
				if(taskIdSet.contains(taskId)){
					String animalCode = (String) obj.get("animalCode");
					if(!selectAnimalCodeSet.contains(animalCode)){
						String dosageDesc = (String) obj.get("dosageDesc");
						Integer gender = (Integer) obj.get("gender");
						String anatomyDate = (String) obj.get("anatomyDate");
						data_animalTable_task.add(new Animal(false,dosageDesc,animalCode,gender,anatomyDate));
					}
				}
			}
		}
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceBatch_add.fxml"));
		Scene scene = new Scene(root, 1024, 660);
		stage.setScene(scene);
		stage.setTitle("新建组织取材");
		stage.setMinWidth(1024);
		stage.setMinHeight(660);
		
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight());
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				if(null != tblTissueSliceBatchIndex){
					TissueSliceBatch.getInstance().updateBatchIndexTable(tblTissueSliceBatchIndex.getId());
				}
			}
		});
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
		
		public AnatomyTask(){}
		public AnatomyTask(boolean select,String taskId,String planAnatomyDate,String animalNum){
			setSelect(select);
			setTaskId(taskId);
			setPlanAnatomyDate(planAnatomyDate);
			setAnimalNum(animalNum+"");
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
	
	/**取材批次脏器
	 * @author Administrator
	 *
	 */
	public class BatchViscera{
		private BooleanProperty select;
		private StringProperty tissueSliceVisceraId;
		private StringProperty sliceCode;
		private StringProperty visceraOrTissueName;
		
		private StringProperty visceraCode;
		private StringProperty visceraName;
		private StringProperty subVisceraCode;
		private StringProperty subVisceraName;
		private BatchViscera(){}
		private BatchViscera(boolean select,String tissueSliceVisceraId,String sliceCode,
				String visceraOrTissueName,String visceraCode,String visceraName
				,String subVisceraCode,String subVisceraName){
			setSelect(select);
			setTissueSliceVisceraId(tissueSliceVisceraId);
			setSliceCode(sliceCode);
			setVisceraOrTissueName(visceraOrTissueName);
			
			setVisceraCode(visceraCode);
			setVisceraName(visceraName);
			setSubVisceraCode(subVisceraCode);
			setSubVisceraName(subVisceraName);
		}
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getTissueSliceVisceraId() {
			return tissueSliceVisceraId.get();
		}
		public void setTissueSliceVisceraId(String tissueSliceVisceraId) {
			this.tissueSliceVisceraId = new SimpleStringProperty(tissueSliceVisceraId);
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
		public String getSubVisceraCode() {
			return subVisceraCode.get();
		}
		public void setSubVisceraCode(String subVisceraCode) {
			this.subVisceraCode = new SimpleStringProperty(subVisceraCode);
		}
		public String getSubVisceraName() {
			return subVisceraName.get();
		}
		public void setSubVisceraName(String subVisceraName) {
			this.subVisceraName = new SimpleStringProperty(subVisceraName);
		}
	}
	/**取材批次排除表
	 * @author Administrator
	 *
	 */
	public class BatchExcluded{
		private StringProperty animalCode;
		private StringProperty showVisceraName;
		private StringProperty reason;
		private StringProperty tissueSliceVisceraId;
		private IntegerProperty visceraType;
		private StringProperty visceraCode;
		private StringProperty visceraName;
		private StringProperty subVisceraCode;
		private StringProperty subVisceraName;
		
		private BatchExcluded(){}
		private BatchExcluded(String animalCode,String tissueSliceVisceraId,
				String reason,
				Integer visceraType,String visceraCode,String visceraName,String subVisceraCode,String subVisceraName){
			setAnimalCode(animalCode);
			setReason(reason);
			setTissueSliceVisceraId(tissueSliceVisceraId);
			
			if(null != subVisceraCode && !"".equals(subVisceraCode)){
				setShowVisceraName(subVisceraName);
			}else{
				setShowVisceraName(visceraName);
			}
			
			setVisceraType(visceraType);
			setVisceraCode(visceraCode);
			setVisceraName(visceraName);
			setSubVisceraCode(subVisceraCode);
			setSubVisceraName(subVisceraName);
			
		}
		public String getTissueSliceVisceraId() {
			return tissueSliceVisceraId.get();
		}
		public void setTissueSliceVisceraId(String tissueSliceVisceraId) {
			this.tissueSliceVisceraId = new SimpleStringProperty(tissueSliceVisceraId);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		
		public String getReason() {
			return reason.get();
		}
		public void setReason(String reason) {
			this.reason = new SimpleStringProperty(reason);
		}
		public String getShowVisceraName() {
			return showVisceraName.get();
		}
		public void setShowVisceraName(String showVisceraName) {
			this.showVisceraName = new SimpleStringProperty(showVisceraName);
		}
		public Integer getVisceraType() {
			return visceraType.get();
		}
		public void setVisceraType(Integer visceraType) {
			this.visceraType = new SimpleIntegerProperty(visceraType);
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
		public String getSubVisceraCode() {
			return subVisceraCode.get();
		}
		public void setSubVisceraCode(String subVisceraCode) {
			this.subVisceraCode = new SimpleStringProperty(subVisceraCode);
		}
		public String getSubVisceraName() {
			return subVisceraName.get();
		}
		public void setSubVisceraName(String subVisceraName) {
			this.subVisceraName = new SimpleStringProperty(subVisceraName);
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
                checkBox.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						if(!editable){
							CheckBox  cb = (CheckBox) event.getSource();
							cb.setSelected(!cb.isSelected());
						}
						
					}
                });
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
//                if(checkBox.isSelected()){
//	 				getTableRow().setStyle("-fx-background-color:palegreen ;");
//                }
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
