package com.lanen.view.clinicaltest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TestItem;
import com.lanen.model.contract.TblNotification;
import com.lanen.model.studyplan.DictBioChem;
import com.lanen.model.studyplan.DictBloodCoag;
import com.lanen.model.studyplan.DictHemat;
import com.lanen.model.studyplan.DictUrine;
import com.lanen.model.studyplan.TblAnimal;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.model.studyplan.TblTestIndexPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.Sign;
import com.lanen.zero.main.MainFrame;

public class TempTaskFrame extends Application implements Initializable {

	private List<TestItem> indexList = new ArrayList<TestItem>();
	private List<String> animalIdList = new ArrayList<String>();

	@FXML
	private TextField taskNoText;
	@FXML
	private TextField animalTypeText;
	@FXML
	private TextField clinicalTestDirectorText;
	@FXML
	private TextField createDateText;
	@FXML
	private ToggleGroup toggleGroup;	//
	@FXML
	private RadioButton noRadioButton;  //否
	@FXML
	private RadioButton yesRadioButton; //是
	@FXML
	private ToggleGroup toggleGroup2;	//
	@FXML
	private RadioButton animalIdRadioButton;  //动物Id号
	@FXML
	private RadioButton animalCodeRadioButton; //动物编号
	
	@FXML
	private Label animalLabel;  //默认是动物Id号
	
	
	@FXML
	private ComboBox<String> clientComboBox;
	private ObservableList<String> data_client = FXCollections.observableArrayList();
	@FXML
	private ListView<String> listView;
	private ObservableList<String> data_list = FXCollections.observableArrayList();
	/**专题编号[(申请编号)]：临检申请实体*/
	private  Map<String,TblClinicalTestReq> map = new HashMap<String,TblClinicalTestReq>();
	@FXML
	private ComboBox<String> yearComboBox;
	private ObservableList<String> data_year = FXCollections.observableArrayList();
	@FXML
	private Button newBuiltBtn;
	private boolean editting=true;   //默认为编辑状态
	private boolean isInner = true;  //是否内部
	private boolean isOnAnimalId = true;//默认是animalIdRadioButton
	private TblClinicalTestReq tblClinicalTestReq = null ;
	private TblStudyPlan tblStudyPlan = null;;

	// 动物列表区域
	@FXML
	private TextField animalIdText;
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private ListView<String> animalListView;
	private ObservableList<String> data_animalListView= FXCollections.observableArrayList();
	private ObservableList<String> data2_animalListView= FXCollections.observableArrayList();
	// 检验指标区域
	   // 动物信息表
	@FXML
	private TableView<Index> table1;
	@FXML
	private TableColumn<Index, Boolean> selectCol1;
	@FXML
	private TableColumn<Index, String> testIndexCol1;
	@FXML
	private TableColumn<Index, String> testIndexAbbrCol1;
	private static ObservableList<Index> data1 = FXCollections.observableArrayList();

	@FXML
	private TableView<Index> table2;
	@FXML
	private TableColumn<Index, Boolean> selectCol2;
	@FXML
	private TableColumn<Index, String> testIndexCol2;
	@FXML
	private TableColumn<Index, String> testIndexAbbrCol2;
	private static ObservableList<Index> data2 = FXCollections.observableArrayList();

	@FXML
	private TableView<Index> table3;
	@FXML
	private TableColumn<Index, Boolean> selectCol3;
	@FXML
	private TableColumn<Index, String> testIndexCol3;
	@FXML
	private TableColumn<Index, String> testIndexAbbrCol3;
	private static ObservableList<Index> data3 = FXCollections.observableArrayList();

	@FXML
	private TableView<Index> table4;
	@FXML
	private TableColumn<Index, Boolean> selectCol4;
	@FXML
	private TableColumn<Index, String> testIndexCol4;
	@FXML
	private TableColumn<Index, String> testIndexAbbrCol4;
	private static ObservableList<Index> data4 = FXCollections.observableArrayList();

	@FXML
	private Button saveButton;
	@FXML
	private HBox tableHBox;//不让表格被修改用
	
	/**离开页面时，刷新主界面树用*/
	private static int temp = 0;
	private static String studyNo = "";
	private static int reqNo = 0;
	
	public TempTaskFrame(){
		temp = 0;
		studyNo ="";
		reqNo = 0;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//初始化  yearComboBox
		initYearComboBox();
		//初始化 clientComboBox
		initClientComboBox();
		//初始化ListView
		initListView();
		// 初始化table
		initTable1();
		initTable2();
		initTable3();
		initTable4();
		//初始化动物列表
		initAnimalListView();
		//初始化  toggleGroup2
		initAnimalRadioButton();
	}
	/**
	 * 初始化  toggleGroup2
	 */
	private void initAnimalRadioButton() {
		animalIdRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					animalLabel.setText("动物Id号");
					isOnAnimalId = true;
					animalListView.setItems(data_animalListView);
					animalIdText.requestFocus();
				}
				
			}});
		animalCodeRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					animalLabel.setText("动物编号");
					isOnAnimalId = false;
					animalListView.setItems(data2_animalListView);
					animalIdText.requestFocus();
				}
				
			}});
	}
	/**
	 * 初始化动物列表
	 */
	private void initAnimalListView() {
		animalListView.setItems(data_animalListView);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		listView.setItems(data_list);
		 listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	            @Override public ListCell<String> call(ListView<String> list) {
	                return new ListFormatCell();
	            }
	        });
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					//设为编辑状态
					editting=true;
					newBuiltBtn.setDisable(false);
					tblClinicalTestReq = map.get(newValue);
					tblStudyPlan = tblClinicalTestReq.getTblStudyPlan();
					if(tblClinicalTestReq.getTemp()==2 || tblClinicalTestReq.getTemp()==3){
						isInner = true;
					}else{
						isInner =false;
					}
					// 更新其他区域值
					updateOtherData(tblClinicalTestReq);
				}else{
					editting = false;
					tblClinicalTestReq = null;
					tblStudyPlan = null;
					// 更新其他区域值（清空）
					updateOtherData(null);
				}
			}
			
		});
	}
	/**
	 * 更新ListView 的值
	 */
	private void  updateListView(String year){
		data_list.clear();
		map.clear();
		List<TblClinicalTestReq> list = BaseService.getTblClinicalTestReqService().findTempReqWithyear(year);
		if(null!=list && list.size()>0){
			for(TblClinicalTestReq obj:list){
				if(obj.getTblStudyPlan().getTemp() == 1){
					data_list.add(obj.getTblStudyPlan().getStudyNo());
					map.put(obj.getTblStudyPlan().getStudyNo(), obj);
				}else if(obj.getTemp() == 2){
					data_list.add(obj.getTblStudyPlan().getStudyNo()+"("+obj.getReqNo()+")");
					map.put(obj.getTblStudyPlan().getStudyNo()+"("+obj.getReqNo()+")", obj);
				}else{
					data_list.add(obj.getTblStudyPlan().getStudyNo()+"("+obj.getReqNo()+") ");
					map.put(obj.getTblStudyPlan().getStudyNo()+"("+obj.getReqNo()+") ", obj);
				}
			}
		}
		
	}
	/**
	 * 更新其他区域的值
	 */
	private void updateOtherData(TblClinicalTestReq entity){
		//
		if(null !=entity){
			updateControlsState(entity.getTemp());
		}
		//请空
		taskNoText.setText("");
		animalTypeText.setText("");
		clinicalTestDirectorText.setText("");
		createDateText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		clientComboBox.getSelectionModel().clearSelection();
		//验证试验(否)
		noRadioButton.setSelected(true);
		
		data_animalListView.clear();
		data2_animalListView.clear();
		animalIdText.setText("");
		//清空检验指标（四个表格）
		clearTestIndex();
		
		if(null!=entity){
			taskNoText.setText(entity.getTblStudyPlan().getStudyNo());
			animalTypeText.setText(entity.getTblStudyPlan().getAnimalType());
			clinicalTestDirectorText.setText(entity.getTblStudyPlan().getClinicalTestDirector());
			createDateText.setText(DateUtil.dateToString(entity.getCreateDate(),"yyyy-MM-dd"));
			clientComboBox.getSelectionModel().select(entity.getTblStudyPlan().getClient());
			//验证试验
			toggleGroup.getToggles().get(entity.getTblStudyPlan().getIsValidation()).setSelected(true);
			updateAnimalId(entity);
			updateTestIndex(entity);
		}
	}
	/**
	 * 更新其他区域,根据试验计划
	 */
	private void updateOtherDateByStudyPlan(){
		//请空
		taskNoText.setText("");
		animalTypeText.setText("");
		clinicalTestDirectorText.setText("");
		createDateText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		clientComboBox.getSelectionModel().clearSelection();
		//验证试验(否)
		noRadioButton.setSelected(true);
		
		data_animalListView.clear();
		data2_animalListView.clear();
		animalIdText.setText("");
		//清空检验指标（四个表格）
		clearTestIndex();
		
		if(null!=tblStudyPlan){
			taskNoText.setText(tblStudyPlan.getStudyNo());
			animalTypeText.setText(tblStudyPlan.getAnimalType());
			clinicalTestDirectorText.setText(tblStudyPlan.getClinicalTestDirector());
			//验证试验
			toggleGroup.getToggles().get(tblStudyPlan.getIsValidation()).setSelected(true);
			updateTestIndexByStudyPlan();
		}
	}
	/**
	 * 更新动物列表
	 * @param entity
	 */
	private void updateAnimalId(TblClinicalTestReq entity) {
		data_animalListView.clear();
		data2_animalListView.clear();
		if(null!=entity){
			//Set<TblClinicalTestReqIndex2> set= entity.getTblClinicalTestReqIndex2s();
			//懒加载问题
			List<TblClinicalTestReqIndex2> list=BaseService.getTblClinicalTestReqService().getTblClinicalTestReqIndex2ListByTblClinicalTestReq(entity);
			if(null!=list && list.size()>0){
				//List<TblClinicalTestReqIndex2> list = new ArrayList<TblClinicalTestReqIndex2>(set);
				if(null!=list && list.size()>0){
					Collections.sort(list, new Comparator<TblClinicalTestReqIndex2>(){

						@Override
						public int compare(TblClinicalTestReqIndex2 entity1, TblClinicalTestReqIndex2 entity2){
							if(entity1.getAniSerialNum()!=entity2.getAniSerialNum()){
								return entity1.getAniSerialNum()-entity2.getAniSerialNum();
							}
							return 0;
						}});
					for(TblClinicalTestReqIndex2 obj:list){
						data_animalListView.add(obj.getAnimalId());
						data2_animalListView.add(obj.getAnimalCode());
					}
				}
			}
		}
		
	}

	/**
	 * 更新检验指标（四个表格）
	 */
	private void updateTestIndex(TblClinicalTestReq entity) {
		if (null != entity) {
			//Set<TblClinicalTestReqIndex> set = entity.getTblClinicalTestReqIndexs();
			//懒加载问题
			List<TblClinicalTestReqIndex> indexList =BaseService.getTblClinicalTestReqService()//
					.getReqIndexByReqNo(entity.getTblStudyPlan(), entity.getReqNo());
			if (null != indexList && indexList.size() > 0) {
				//List<TblClinicalTestReqIndex> indexList = new ArrayList<TblClinicalTestReqIndex>(set);
				// 指标名称+检测项目列表（如：葡萄糖1      ，葡糖糖4）
				List<String> list = new ArrayList<String>();
				for (TblClinicalTestReqIndex obj : indexList) {
					list.add(obj.getTestIndex()+obj.getTestitem());
				}
				if (null != list && list.size() > 0) {
					if (data1.size() > 0) {
						int i = 0;
						for (Index obj : data1) {
							if (list.contains(obj.getTestIndex()+1)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data1.set(i, index);
							}
							i++;
						}
					}
					if (data2.size() > 0) {
						int i = 0;
						for (Index obj : data2) {
							if (list.contains(obj.getTestIndex()+2)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data2.set(i, index);
							}
							i++;
						}
					}
					if (data3.size() > 0) {
						int i = 0;
						for (Index obj : data3) {
							if (list.contains(obj.getTestIndex()+3)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data3.set(i, index);
							}
							i++;
						}
					}
					if (data4.size() > 0) {
						int i = 0;
						for (Index obj : data4) {
							if (list.contains(obj.getTestIndex()+4)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data4.set(i, index);
							}
							i++;
						}
					}
				}

			}
		}

	}
	/**
	 * 更新检验指标（四个表格）根据试验计划
	 */
	private void updateTestIndexByStudyPlan() {
		if (null != tblStudyPlan) {
			//懒加载问题
//			List<TblClinicalTestReqIndex> indexList =BaseService.getTblClinicalTestReqService()//
//					.getReqIndexByReqNo(entity.getTblStudyPlan(), entity.getReqNo());
			List<TblTestIndexPlan> indexList = BaseService.getTblTestIndexPlanService().getByStudyNo(tblStudyPlan);
			if (null != indexList && indexList.size() > 0) {
				//List<TblClinicalTestReqIndex> indexList = new ArrayList<TblClinicalTestReqIndex>(set);
				// 指标名称+检测项目列表（如：葡萄糖1      ，葡糖糖4）
				List<String> list = new ArrayList<String>();
				for (TblTestIndexPlan obj : indexList) {
					list.add(obj.getTestIndex()+obj.getTestItem());
				}
				if (null != list && list.size() > 0) {
					if (data1.size() > 0) {
						int i = 0;
						for (Index obj : data1) {
							if (list.contains(obj.getTestIndex()+1)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data1.set(i, index);
							}
							i++;
						}
					}
					if (data2.size() > 0) {
						int i = 0;
						for (Index obj : data2) {
							if (list.contains(obj.getTestIndex()+2)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data2.set(i, index);
							}
							i++;
						}
					}
					if (data3.size() > 0) {
						int i = 0;
						for (Index obj : data3) {
							if (list.contains(obj.getTestIndex()+3)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data3.set(i, index);
							}
							i++;
						}
					}
					if (data4.size() > 0) {
						int i = 0;
						for (Index obj : data4) {
							if (list.contains(obj.getTestIndex()+4)) {
								obj.setSelect(true);
								Index index = new Index(true, obj.getTestIndex(),
										obj.getTestIndexAbbr());
								data4.set(i, index);
							}
							i++;
						}
					}
				}
				
			}
		}
		
	}
	/**
	 * 清空检验指标（四个表格）
	 */
	private void clearTestIndex() {
		if(null!=data1 && data1.size()>0){
			int i=0;
			for(Index obj:data1){
				if(obj.getSelect()){
					Index entity=new Index(false,obj.getTestIndex(),obj.getTestIndexAbbr());
					data1.set(i, entity );
				}
				i++;
			}
		}
		if(null!=data2 && data2.size()>0){
			int i=0;
			for(Index obj:data2){
				if(obj.getSelect()){
					Index entity=new Index(false,obj.getTestIndex(),obj.getTestIndexAbbr());
					data2.set(i, entity );
				}
				i++;
			}
		}
		if(null!=data3 && data3.size()>0){
			int i=0;
			for(Index obj:data3){
				if(obj.getSelect()){
					Index entity=new Index(false,obj.getTestIndex(),obj.getTestIndexAbbr());
					data3.set(i, entity );
				}
				i++;
			}
		}
		if(null!=data4 && data4.size()>0){
			int i=0;
			for(Index obj:data4){
				if(obj.getSelect()){
					Index entity=new Index(false,obj.getTestIndex(),obj.getTestIndexAbbr());
					data4.set(i, entity );
				}
				i++;
			}
		}
		
	}
	/**更新更控件状态*/
	private void updateControlsState(int temp){
		if(temp == 1){
			//外部临时
			if(editting){
				taskNoText.setEditable(false);
			}else{
				taskNoText.setEditable(true);
			}
			animalTypeText.setEditable(true);
			clinicalTestDirectorText.setEditable(true);
			clientComboBox.setDisable(false);
			createDateText.setEditable(false);
			noRadioButton.setDisable(false);
			yesRadioButton.setDisable(false);
			animalIdRadioButton.setSelected(true);
			animalIdRadioButton.setDisable(true);
			animalCodeRadioButton.setDisable(true);
			animalIdText.setEditable(true);
			tableHBox.setVisible(false);
			saveButton.setDisable(false);
			delBtn.setDisable(false);
			addBtn.setDisable(false);
		}else if(temp == 2){
			//内部临时
			taskNoText.setEditable(false);
			animalTypeText.setEditable(false);
			clinicalTestDirectorText.setEditable(false);
			clientComboBox.setDisable(true);
			createDateText.setEditable(false);
			noRadioButton.setDisable(true);
			yesRadioButton.setDisable(true);
			animalIdRadioButton.setSelected(true);
			animalIdRadioButton.setDisable(false);
			animalCodeRadioButton.setDisable(false);
			animalIdText.setEditable(true);
			tableHBox.setVisible(false);
			saveButton.setDisable(false);
			delBtn.setDisable(false);
			addBtn.setDisable(false);
		}else if(temp ==3){
			//内部临时转正
			taskNoText.setEditable(false);
			animalTypeText.setEditable(false);
			clinicalTestDirectorText.setEditable(false);
			clientComboBox.setDisable(true);
			createDateText.setEditable(false);
			noRadioButton.setDisable(true);
			yesRadioButton.setDisable(true);
			animalIdRadioButton.setSelected(true);
			animalIdRadioButton.setDisable(false);
			animalCodeRadioButton.setDisable(false);
			animalIdText.setEditable(false);
			tableHBox.setVisible(true);
			saveButton.setDisable(true);
			delBtn.setDisable(true);
			addBtn.setDisable(true);
		}
	}
	/**
	 * 
	 * 初始化 clientComboBox
	 */
	private void initClientComboBox() {
		data_client.clear();
		List<String> clientList= BaseService.getTblStudyPlanService().findAllTempClient();
		if(null!=clientList&&clientList.size()>0){
			for(String str:clientList){
				data_client.add(str);
			}
		}
		clientComboBox.setItems(data_client);
	}
	/**
	 * 初始化  yearComboBox及select(0)
	 */
	private void initYearComboBox() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
		String year =format.format(new Date());
		int yearInt =Integer.parseInt(year);
		data_year.add(year);
		yearInt--;
		data_year.add(yearInt+"");
		data_year.add(yearInt+"之前");
		yearComboBox.setItems(data_year);
		
		yearComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					//更新ListView
					updateListView(newValue);
				}
			}});
		
		
		yearComboBox.getSelectionModel().clearAndSelect(0);
	}

	/**
	 * 
	 */
	/**
	 * 初始化table1
	 */
	private void initTable1() {
		selectCol1.setCellValueFactory(new PropertyValueFactory<Index, Boolean>("select"));
		selectCol1
				.setCellFactory(new Callback<TableColumn<Index, Boolean>, TableCell<Index, Boolean>>() {

					@Override
					public TableCell<Index, Boolean> call(TableColumn<Index, Boolean> p) {
						return new CheckBoxTableCell<Index, Boolean>();
					}
				});
		testIndexCol1.setCellValueFactory(new PropertyValueFactory<Index, String>("testIndex"));
		testIndexAbbrCol1.setCellValueFactory(new PropertyValueFactory<Index, String>(
				"testIndexAbbr"));
		table1.setItems(data1);
		
		
		data1.clear();
		List<DictBioChem> list = BaseService.getDictBioChemService().findAllOrderByOrderNo();
		if (null != list && list.size() > 0) {
			for (DictBioChem obj : list) {
				data1.add(new Index(false, obj.getName(), obj.getAbbr()));
			}
		}
//		//改变
//		table1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
//					Number newValue) {
//				if(newValue.intValue()>-1){
//					Index index = new Index(!data1.get(newValue.intValue()).getSelect(),data1.get(newValue.intValue()).getTestIndex(),data1.get(newValue.intValue()).getTestIndexAbbr());
//					data1.set(newValue.intValue(), index);
//				}
//			}});
		table1.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					TableView<Index> table=(TableView<Index>) event.getSource();
					Index obj= table.getSelectionModel().getSelectedItem();
					if(null!=obj){
						Index index = new Index(!obj.getSelect(),obj.getTestIndex(),obj.getTestIndexAbbr());
						table.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}});
	}

	/**
	 * 初始化table2
	 */
	private void initTable2() {
		selectCol2.setCellValueFactory(new PropertyValueFactory<Index, Boolean>("select"));
		selectCol2
				.setCellFactory(new Callback<TableColumn<Index, Boolean>, TableCell<Index, Boolean>>() {

					@Override
					public TableCell<Index, Boolean> call(TableColumn<Index, Boolean> p) {
						return new CheckBoxTableCell<Index, Boolean>();
					}
				});
		testIndexCol2.setCellValueFactory(new PropertyValueFactory<Index, String>("testIndex"));
		testIndexAbbrCol2.setCellValueFactory(new PropertyValueFactory<Index, String>(
				"testIndexAbbr"));
		table2.setItems(data2);
		data2.clear();
		List<DictHemat> list = BaseService.getDictHematService().findAllOrderByOrderNo();
		if (null != list && list.size() > 0) {
			for (DictHemat obj : list) {
				data2.add(new Index(false, obj.getName(), obj.getAbbr()));
			}
		}
		table2.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					TableView<Index> table=(TableView<Index>)event.getSource();
					Index obj= table.getSelectionModel().getSelectedItem();
					if(null!=obj){
						Index index = new Index(!obj.getSelect(),obj.getTestIndex(),obj.getTestIndexAbbr());
						table.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}});
	}

	/**
	 * 初始化table3
	 */
	private void initTable3() {
		selectCol3.setCellValueFactory(new PropertyValueFactory<Index, Boolean>("select"));
		selectCol3
				.setCellFactory(new Callback<TableColumn<Index, Boolean>, TableCell<Index, Boolean>>() {

					@Override
					public TableCell<Index, Boolean> call(TableColumn<Index, Boolean> p) {
						return new CheckBoxTableCell<Index, Boolean>();
					}
				});
		testIndexCol3.setCellValueFactory(new PropertyValueFactory<Index, String>("testIndex"));
		testIndexAbbrCol3.setCellValueFactory(new PropertyValueFactory<Index, String>(
				"testIndexAbbr"));
		table3.setItems(data3);
		data3.clear();
		List<DictBloodCoag> list = BaseService.getDictBloodCoagService().findAllOrderByOrderNo();
		if (null != list && list.size() > 0) {
			for (DictBloodCoag obj : list) {
				data3.add(new Index(false, obj.getName(), obj.getAbbr()));
			}
		}
		table3.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					TableView<Index> table=(TableView<Index>)event.getSource();
					Index obj= table.getSelectionModel().getSelectedItem();
					if(null!=obj){
						Index index = new Index(!obj.getSelect(),obj.getTestIndex(),obj.getTestIndexAbbr());
						table.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}});
	}

	/**
	 * 初始化table4
	 */
	private void initTable4() {
		selectCol4.setCellValueFactory(new PropertyValueFactory<Index, Boolean>("select"));
		selectCol4
				.setCellFactory(new Callback<TableColumn<Index, Boolean>, TableCell<Index, Boolean>>() {

					@Override
					public TableCell<Index, Boolean> call(TableColumn<Index, Boolean> p) {
						return new CheckBoxTableCell<Index, Boolean>();
					}
				});
		testIndexCol4.setCellValueFactory(new PropertyValueFactory<Index, String>("testIndex"));
		testIndexAbbrCol4.setCellValueFactory(new PropertyValueFactory<Index, String>(
				"testIndexAbbr"));
		table4.setItems(data4);
		data4.clear();
		List<DictUrine> list = BaseService.getDictUrineService().findAllOrderByOrderNo();
		if (null != list && list.size() > 0) {
			for (DictUrine obj : list) {
				data4.add(new Index(false, obj.getName(), obj.getAbbr()));
			}
		}
		table4.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					TableView<Index> table=(TableView<Index>)event.getSource();
					Index obj= table.getSelectionModel().getSelectedItem();
					if(null!=obj){
						Index index = new Index(!obj.getSelect(),obj.getTestIndex(),obj.getTestIndexAbbr());
						table.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}});
	}
	/**
	 * 新建按钮 onAction
	 */
	@FXML
	private void onNewBuiltBtnAction(ActionEvent event){
		ChooseTempNoFrame chooseTempNoFrame = new ChooseTempNoFrame();
		Stage stage = new Stage();
		stage.initOwner(MainFrame.mainWidow);
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			chooseTempNoFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(ChooseTempNoFrame.type)){
			editting=false;// 非编辑
			isInner = ChooseTempNoFrame.isInner;
			yearComboBox.getSelectionModel().select(0);//回到当前年
			listView.getSelectionModel().clearSelection();//清除选中
			if(isInner){
				updateControlsState(2);
				String studyNo = ChooseTempNoFrame.studyNo;
				tblStudyPlan = BaseService.getTblStudyPlanService().getById(studyNo);
				updateOtherDateByStudyPlan();
				
				animalIdText.requestFocus();
			}else{
				updateControlsState(1);
				updateOtherData(null);
				
				taskNoText.requestFocus();
			}
			
		}
//		//可编辑
//		taskNoText.setEditable(true);
//		newBuiltBtn.setDisable(true);
//		taskNoText.requestFocus();
	}

	/**
	 * 保存按钮 onAction
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event) {
		String taskNo = taskNoText.getText();
		if("".equals(taskNo.trim())){
//			Alert2.create("任务编号不能为空");
			Messager.showWarnMessage("任务编号不能为空！");
			taskNoText.requestFocus();
			return;
		}
		if (!editting && !isInner) {
			// 判断任务编号是否存在
			if (BaseService.getTblStudyPlanService().isExistById(taskNo)) {
//				Alert2.create("任务编号已存在");
				Messager.showWarnMessage("任务编号已存在！");
				return;
			}
		}
		String animalType = animalTypeText.getText();
		if("".equals(animalType.trim())){
//			Alert2.create("动物种类不能为空");
			Messager.showWarnMessage("动物种类不能为空！");
			animalTypeText.requestFocus();
			return;
		}
		String clinicalTestDirector = clinicalTestDirectorText.getText();
		if(!isInner && "".equals(clinicalTestDirector.trim())){
//			Alert2.create("临检负责人不能为空");
			Messager.showWarnMessage("临检负责人不能为空！");
			clinicalTestDirectorText.requestFocus();
			return;
		}
		String client = clientComboBox.getSelectionModel().getSelectedItem();
		if (!isInner &&( null == client || "".equals(client.trim()))) {
//			Alert2.create("委托单位不能为空");
			Messager.showWarnMessage("委托单位不能为空！");
			clientComboBox.requestFocus();
			return;
		}
		
		//验证试验
		String validation=((RadioButton)toggleGroup.getSelectedToggle()).getText().toString().trim();
		int isValidation =validation.equals("否")? 0 :1;
		if (!countAnimalIdList()) {
//			Alert2.create("请录入动物列表");
			Messager.showWarnMessage("请录入动物列表！");
			return;
		}
		// 统计选中指标,无 返回false
		if (!countIndexList()) {
//			Alert2.create("请选择检测指标");
			Messager.showWarnMessage("请选择检测指标！");
			return;
		}
//		//签字窗口
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("临时任务--身份验证");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//签字通过
//		if("OK".equals(SignFrame.getType())){
		User signUser = Sign.openSign("临时任务--身份验证", "");
		if(null != signUser){
			
			//记录临时任务日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("数据操作");
			tblLog.setOperatOject("临时任务");
			User user = signUser;
			if(null!=user){
				tblLog.setOperator(user.getRealName());
			}
			
			tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			
			
			if (!editting) { //非编辑状态，新建状态
				if(!isInner){
					BaseService.getTblClinicalTestReqService().saveStudyPlanAndClinicalTestReq(taskNo,
							animalType, clinicalTestDirector, client, animalIdList,
							indexList,isValidation);
					//更新listView
					updateListView(yearComboBox.getSelectionModel().getSelectedItem());
					initClientComboBox();
					listView.getSelectionModel().select(taskNo);//
//					Alert.create("新增成功");
					Messager.showMessage("新增成功！");
					tblLog.setOperatContent("新增临时任务，任务编号："+taskNo);
					
					temp = 1;
					studyNo = taskNo;
					reqNo = 1;
				}else{
					int reqNo = BaseService.getTblClinicalTestReqService().saveTempClinicalTestReq(tblStudyPlan, animalIdList,
							indexList);
					
					
					//更新listView
					updateListView(yearComboBox.getSelectionModel().getSelectedItem());
					listView.getSelectionModel().select(tblStudyPlan.getStudyNo()+"("+reqNo+")");//
//					Alert.create("新增成功");
					Messager.showMessage("新增成功！");
					tblLog.setOperatContent("新增临时任务，任务编号："+tblStudyPlan.getStudyNo()+"("+reqNo+")");
					
					temp = 2;
					studyNo = taskNo;
					TempTaskFrame.reqNo = reqNo;
					
					//发送通知给SD TODO
					String sender = "";
					User currentUser = SaveUserInfo.getUser();
					if(null != currentUser){
						sender = currentUser.getRealName();
					}
					String receiver = BaseService.getUserService().getUserNameByRealName(tblStudyPlan.getStudydirector());
					writeNotification(taskNo,reqNo+"",sender,receiver);
				}
			} else {
				//编辑状态，先判断临检申请是否  已经产生  标本接收数据，如何有，提示不能修改，   没有则可以更新
//			String selected=listView.getSelectionModel().getSelectedItem();
//			TblClinicalTestReq tblClinicalTestReq=null;
//			if(null!=selected && !selected.equals("")){
//				tblClinicalTestReq=map.get(selected);
//			}else{// 没有选中项
//				return;
//			}
//			if(BaseService.getTblSpecimenService().isExistByClinicalTestReq(tblClinicalTestReq)){
//				
//				Alert2.create("该任务已经接收了标本，无法修改");
//				return;
//			}
				if(!isInner){
					BaseService.getTblClinicalTestReqService().updateStudyPlanAndClinicalTestReq(taskNo,
							animalType, clinicalTestDirector, client, animalIdList,
							indexList,isValidation);
					
					//更新listView
					updateListView(yearComboBox.getSelectionModel().getSelectedItem());
					initClientComboBox();
					listView.getSelectionModel().select(taskNo);
//					Alert.create("保存成功");
					Messager.showMessage("保存成功！");
					tblLog.setOperatContent("编辑临时任务，任务编号："+taskNo);
					
				}else{
					//编辑内部
					BaseService.getTblClinicalTestReqService().updateTempClinicalTestReq(tblClinicalTestReq, animalIdList,
							indexList);
					//更新listView
//					Alert.create("保存成功");
					Messager.showMessage("保存成功！");
					tblLog.setOperatContent("编辑临时任务，任务编号："+tblStudyPlan.getStudyNo()+"("+tblClinicalTestReq.getReqNo()+")");
				}
			}
			//保存临时任务日志
			BaseService.getTblLogService().save(tblLog);
			
		}
	}
	/**
	 * 发送通知
	 */
	private void writeNotification(String studyNo,String reqNo,String sender,String receiver){
		
		//当前时间
		String currentDate = DateUtil.dateToString(new Date(), "yyyy年MM月dd日 HH时mm分");
		
		//通知消息
		TblNotification tblNotification = new TblNotification();
		tblNotification.setMsgTitle(""+sender+"提交临时临检申请单提醒");
		String msgContent = "SD,您好<br>&nbsp;&nbsp;&nbsp;&nbsp;";
		msgContent = msgContent+""+sender+" 于"+currentDate+"提交了临时临检申请单," +
				"专题编号为"+studyNo+",申请单号为"+reqNo+",特此提醒";
		tblNotification.setMsgContent(msgContent);
		tblNotification.setMsgType(1);//系统消息
		
		tblNotification.setSender(sender);
		
		tblNotification.setSendTime(new Date());
		//接收者列表
		List<String> receiverList = new ArrayList<String>();
		receiverList.add(receiver);
		BaseService.getTblNotificationService().save(tblNotification,receiverList);
	}
	
	
	
	/**
	 * 统计动物列表
	 * @return
	 */
	private boolean countAnimalIdList() {
		animalIdList.clear();
		if(null!=data_animalListView && data_animalListView.size()>0){
			for(String obj:data_animalListView){
				animalIdList.add(obj);
			}
		}else{
			return false;
		}
		return true;
	}

	/**
	 * 统计选中指标
	 */
	private boolean countIndexList() {
		indexList.clear();
		//查询被选中指标个数
		if(0==findAllSelectedIndex()){
			return false;
		}else{
			//选中的  检验指标列表
			if(data1.size()>0){
				for(Index obj:data1){
					if(obj.getSelect()){
						TestItem testItem= new TestItem();
						testItem.setIndex2(obj.getTestIndex());
						testItem.setAbbr(obj.getTestIndexAbbr());
						testItem.setTestItem(1);
						indexList.add(testItem);
					}
				}
			}
			if(data2.size()>0){
				for(Index obj:data2){
					if(obj.getSelect()){
						TestItem testItem= new TestItem();
						testItem.setIndex2(obj.getTestIndex());
						testItem.setAbbr(obj.getTestIndexAbbr());
						testItem.setTestItem(2);
						indexList.add(testItem);
					}
				}
			}
			if(data3.size()>0){
				for(Index obj:data3){
					if(obj.getSelect()){
						TestItem testItem= new TestItem();
						testItem.setIndex2(obj.getTestIndex());
						testItem.setAbbr(obj.getTestIndexAbbr());
						testItem.setTestItem(3);
						indexList.add(testItem);
					}
				}
			}
			if(data4.size()>0){
				for(Index obj:data4){
					if(obj.getSelect()){
						TestItem testItem= new TestItem();
						testItem.setIndex2(obj.getTestIndex());
						testItem.setAbbr(obj.getTestIndexAbbr());
						testItem.setTestItem(4);
						indexList.add(testItem);
					}
				}
			}
		}
		return true;
	}
	/**
	 * 查询被选中指标个数
	 * @return
	 */
	private int findAllSelectedIndex() {
		int total=0;
		if(data1.size()>0){
			for(Index obj:data1){
				if(obj.getSelect()){
					total++;
				}
			}
		}
		if(data2.size()>0){
			for(Index obj:data2){
				if(obj.getSelect()){
					total++;
				}
			}
		}
		if(data3.size()>0){
			for(Index obj:data3){
				if(obj.getSelect()){
					total++;
				}
			}
		}
		if(data4.size()>0){
			for(Index obj:data4){
				if(obj.getSelect()){
					total++;
				}
			}
		}
		
		return total;
	}
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * ADD 按钮事件（+）
	 * 
	 * @param event
	 */
	@FXML
	private void onAddAction(ActionEvent event) {
		//已接收标本动物不可以再编辑
		if(null != tblClinicalTestReq){
			boolean isExist = BaseService.getPoolSpecimenCodeService().isExistSpecimenCode(tblClinicalTestReq.getStudyNo(), tblClinicalTestReq.getReqNo());
			if(isExist){
//				Alert2.create("该临时任务已接收过标本，动物信息不可以编辑");
				Messager.showWarnMessage("该临时任务已接收过标本，动物信息不可以编辑！");
				return ;
			}
		}
		//TODO data2_animalListView
		String animalIdStr = animalIdText.getText().toString().trim();
		if ("".equals(animalIdStr)) {// 文本内无数据 则不添加（直接返回）
			return;
		}
		if(isOnAnimalId){
			for(String obj :data_animalListView){
				if(animalIdStr.equals(obj)){
//					Alert2.create("动物Id号重复");
					Messager.showWarnMessage("动物Id号重复！");
					return;
				}
			}
		}else{
			for(String obj :data2_animalListView){
				if(animalIdStr.equals(obj)){
//					Alert2.create("动物编号重复");
					Messager.showWarnMessage("动物编号重复！");
					return;
				}
			}
		}
		//内部
		if(isInner){
			TblAnimal tblAnimal =null;
			if(isOnAnimalId){
				tblAnimal = BaseService.getTblAnimalService().getByStudyPlanAnimalId(tblStudyPlan,animalIdStr);
				if(null != tblAnimal){
					data_animalListView.add(tblAnimal.getAnimalId());
					data2_animalListView.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
				}else{
//					Alert2.create("动物ID号不存在");
					Messager.showWarnMessage("动物ID号不存在！");
					return;
				}
			}else{
				tblAnimal = BaseService.getTblAnimalService().getByStudyPlanAnimalCode(tblStudyPlan,animalIdStr);
				if(null != tblAnimal){
					data_animalListView.add(tblAnimal.getAnimalId());
					data2_animalListView.add(tblAnimal.getAnimalCode()== null ?"":tblAnimal.getAnimalCode());
				}else{
//					Alert2.create("动物编号不存在");
					Messager.showWarnMessage("动物编号不存在！");
					return;
				}
			}
		}else{
			data_animalListView.add(animalIdStr);
			animalIdText.setText("");
		}
		animalIdText.setText("");
		animalIdText.requestFocus();
	}
	
	/**
	 * DEL按钮事件（-）
	 * 
	 * @param event
	 */
	@FXML
	private void onDelAction(ActionEvent event) {
		
		//已接收标本动物不可以再编辑
		if(null != tblClinicalTestReq){
			boolean isExist = BaseService.getPoolSpecimenCodeService().isExistSpecimenCode(tblClinicalTestReq.getStudyNo(), tblClinicalTestReq.getReqNo());
			if(isExist){
//				Alert2.create("该临时任务已接收过标本，动物信息不可以编辑");
				Messager.showWarnMessage("该临时任务已接收过标本，动物信息不可以编辑！");
				return ;
			}
		}
		
		//TODO data2_animalListView
		String selected= animalListView.getSelectionModel().getSelectedItem();
		int index = animalListView.getSelectionModel().getSelectedIndex();
		if(null!=selected && !"".equals(selected)){
			if(editting){//编辑状态下，判断是否有标本数据
				if(!isInner){
					//选中的临时任务 的课题编号
					if(BaseService.getTblSpecimenService().isExistByClinicalTestReqAndAnimalId(tblClinicalTestReq,selected)){
//						Alert2.create("该动物Id号已接收标本，无法删除");
						Messager.showWarnMessage("该动物Id号已接收标本，无法删除！");
						return;
					}
					data_animalListView.remove(selected);
				}else{
					if(isOnAnimalId){
						//选中的临时任务 的课题编号
						if(BaseService.getTblSpecimenService().isExistByClinicalTestReqAndAnimalId(tblClinicalTestReq,selected)){
//							Alert2.create("该动物Id号已接收标本，无法删除");
							Messager.showWarnMessage("该动物Id号已接收标本，无法删除！");
							return;
						}
						data_animalListView.remove(index);
						data2_animalListView.remove(index);
					}else{
						String animalId = data_animalListView.get(index);
						//选中的临时任务 的课题编号
						if(BaseService.getTblSpecimenService().isExistByClinicalTestReqAndAnimalId(tblClinicalTestReq,animalId)){
//							Alert2.create("该动物号已接收标本，无法删除");
							Messager.showWarnMessage("该动物号已接收标本，无法删除！");
							return;
						}
						data_animalListView.remove(index);
						data2_animalListView.remove(index);
					}
				}
			}else{
				if(!isInner){
					data_animalListView.remove(selected);
				}else{
					if(isOnAnimalId){
						data_animalListView.remove(index);
						data2_animalListView.remove(index);
					}else{
						data_animalListView.remove(index);
						data2_animalListView.remove(index);
					}
				}
			}
			animalListView.getSelectionModel().clearSelection();
			
		}else{
//			Alert2.create("请先选择要删除的数据");
			Messager.showWarnMessage("请先选择要删除的数据！");
			return;
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TempTaskFrame.fxml"));
		Scene scene = new Scene(root, 816, 610);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setTitle("临时任务");
		stage.setResizable(false);
		stage.showAndWait();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * 类,指标列表
	 * 
	 * @author Administrator
	 * 
	 */
	public class Index {

		private BooleanProperty select;
		private StringProperty testIndex;
		private StringProperty testIndexAbbr;

		public Index() {
		}

		public Index(boolean select, String testIndex, String testIndexAbbr) {
			this.select = new SimpleBooleanProperty(select);
			this.testIndex = new SimpleStringProperty(testIndex);
			this.testIndexAbbr = new SimpleStringProperty(testIndexAbbr);
		}

		public BooleanProperty selectProperty() {
			return select;
		};

		public boolean getSelect() {
			return select.get();
		}

		public void setSelect(boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}

		public String getTestIndex() {
			return testIndex.get();
		}

		public void setTestIndex(String testIndex) {
			this.testIndex = new SimpleStringProperty(testIndex);
		}

		public String getTestIndexAbbr() {
			return testIndexAbbr.get();
		}

		public void setTestIndexAbbr(String testIndexAbbr) {
			this.testIndexAbbr = new SimpleStringProperty(testIndexAbbr);
		}
	}
	// 在单元格里创建多选框
	public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
		private final CheckBox checkBox;
		private ObservableValue<T> ov;

		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			// this.checkBox.setDisable(true);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
		}

		@Override
		protected void updateItem(T item, boolean empty) {
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
//					getTableRow().getStylesheets().add("willPayRow");
	 				getTableRow().setStyle("-fx-background-color:palegreen ;");
				}else{
					getTableRow().setStyle("");
				}
			}
			
		}
	}
	 private static class ListFormatCell extends ListCell<String> {
	        @Override
	        public void updateItem(String item, boolean empty) {
	            super.updateItem(item, empty);
	            
	            setText(item == null ? "" : item);
	            if(null != item ){
	            	if(item.contains(") ")){
//	            		setStyle("-fx-background-color:#ededed;");
	            		 setTextFill(Color.BLUE);
	            	}else if(item.contains(")")){
//	            		setStyle("-fx-background-color:e0ffff;");
	            		 setTextFill(Color.RED);
	            	}else{
	            		 setTextFill(Color.BLACK);
	            	};
	            }
	            
	            
	        }
	    }
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		TempTaskFrame.temp = temp;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		TempTaskFrame.studyNo = studyNo;
	}
	public int getReqNo() {
		return reqNo;
	}
	public void setReqNo(int reqNo) {
		TempTaskFrame.reqNo = reqNo;
	}  
	

}
