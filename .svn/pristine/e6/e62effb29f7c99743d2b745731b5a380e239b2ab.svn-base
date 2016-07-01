package com.lanen.view.tblsession;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.quarantine.TblQRRequest;
import com.lanen.model.quarantine.TblQRRequestStudyNo;
import com.lanen.model.quarantine.tblsession.TblAniHandover;
import com.lanen.model.quarantine.tblsession.TblAniResite;
import com.lanen.model.quarantine.tblsession.TblHandoverAniList;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.StateForTableView;
import com.lanen.model.tableview.TblAnimalRecListForTableView;
import com.lanen.model.tableview.TblSessionAnimalForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

public class AniHandoverFrame extends Application implements Initializable {

	@FXML
	private Label createrLabel;
	@FXML
	private Label animalTypeLabel;
	@FXML
	private Label createTimeLabel;
	@FXML
	private Label sessionTypeLabel;
	@FXML
	private Label studyNoLabel;

	@FXML
	private ComboBox<String> studyNoComboBox;// 申请编号
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> qrIdComboBox;// 申请单号
	private ObservableList<String> data_qrIdComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> roomComboBox;// 房间号
	private ObservableList<String> data_roomComboBox = FXCollections.observableArrayList();
	@FXML
	private ListView<String> studyNoListView ;
	private ObservableList<String> data_studyNoListView = FXCollections.observableArrayList();
	@FXML
	private TextArea aniStateText;//动物状况
	
	@FXML
	private TextArea remarkText;// 备注

	@FXML
	private RadioButton studyNoRadioButton;//课题
	@FXML
	private RadioButton qrRadioButton;//申请单
	@FXML
	private RadioButton beikuRadioButton;//备库
	private ToggleGroup group = new ToggleGroup();
	
	
	/**
	 * 动物列表(待选)
	 */
	@FXML
	private static TableView<TblSessionAnimalForTableView> animalTable;
	private static ObservableList<TblSessionAnimalForTableView> data_animalTable = FXCollections
			.observableArrayList();
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> animalIdCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> genderCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> roomCol;
	/**
	 * 动物列表（已选）
	 */
	@FXML
	private static TableView<TblSessionAnimalForTableView> selectedAnimalTable;
	private static ObservableList<TblSessionAnimalForTableView> data_selectedAnimalTable = FXCollections
			.observableArrayList();
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> animalIdCol2;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> genderCol2;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> roomCol2;

	@FXML
	private static TableView<StateForTableView> stateTable;
	private static ObservableList<StateForTableView> data_stateTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<StateForTableView,String> genderCol3;
	@FXML
	private TableColumn<StateForTableView,String> numberCol3;
	@FXML
	private TableColumn<StateForTableView,String> selectedNumberCol3;
	
	
	/**
	 * 复核后，禁止编辑
	 */
	@FXML
	private HBox hbox;
	@FXML
	private HBox hbox1;

	private static TblSession tblSession = null;
	private static String sessionId = "";
	private static List<TblSessionAnimals> tblSessionAnimalsList = null;
	private int handoverFlag = 0;//移交类型
	private static String signId = "";
	/** 离开页面时，是否需要更新DayToDayPane上tblSession表数据 */
	private static boolean updateFlag = false;// 离开页面时，是否需要更新DayToDayPane上tblSession表数据
	boolean isBeiku = true;
	private static AniHandoverFrame instance;

	public static AniHandoverFrame getInstance() {
		if (null == instance) {
			instance = new AniHandoverFrame();
		}
		updateFlag = false;
		return instance;
	}

	public AniHandoverFrame() {
		updateFlag = false;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		// 初始化animalTable
		initAnimalTable();
		//初始化animalTable（已选）
		initSelectedAnimalTable();
		//初始化stateTable
		initStateTable();
		
		//studyNoComboBox初始化
		initStudyNoComboBox();
		//qrIdComboBox初始化
		initQrIdComboBox();
		// 房间号ComboBox 初始化
		initRoomComboBox();
		//初始化studyNoListView
		initStudyNoListView();
		// 单选按钮置为一组
		initToggleGroup();
	}
	/**
	 *  单选按钮置为一组
	 * */
	private void initToggleGroup() {
		studyNoRadioButton.setToggleGroup(group);
		qrRadioButton.setToggleGroup(group);
		beikuRadioButton.setToggleGroup(group);
//		group.selectToggle(radioButtonF);
		studyNoRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					studyNoComboBox.setDisable(false);
					handoverFlag = 1;
				}else{
					studyNoComboBox.setDisable(true);
				}
				
			}});
		qrRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					qrIdComboBox.setDisable(false);
					studyNoListView.setDisable(false);
					handoverFlag = 2;
				}else{
					qrIdComboBox.setDisable(true);
					studyNoListView.setDisable(true);
				}
				
			}});
		beikuRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					handoverFlag = 3;
				}
				
			}});
		
	}
	/**
	 * studyNoComboBox初始化
	 */
	private void initStudyNoComboBox() {
		data_studyNoComboBox.clear();
		studyNoComboBox.setItems(data_studyNoComboBox);
	}
	
	/**
	 * 更新 studyNoComboBox 及  或选中 studyNoRadiobutton
	 */
	private  void updateStudyNoComboBox(){
		if(tblSession != null ){
			String animalType = tblSession.getAnimalType();
			String animalStrain = tblSession.getAnimalStrain();
			String animalLevel = tblSession.getAnimalLevel();
			List<String> studyNoList = BaseService.getTblQRRequestStudyNoService()
					.getStudyNoListByAnimal(animalType,animalStrain,animalLevel);
			if(null != studyNoList){
				for(String studyNo : studyNoList){
					data_studyNoComboBox.add(studyNo);
				}
				
				String studyNo = tblSession.getStudyNo();
				if(null != studyNo && !studyNo.isEmpty()){
					studyNoComboBox.getSelectionModel().select(studyNo);
					studyNoRadioButton.setSelected(true);
				}
			}
		}
	}
	/**
	 * qrIdComboBox初始化
	 */
	private void initQrIdComboBox() {
		data_qrIdComboBox.clear();
		qrIdComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null !=newValue){
					updateStudyNoListView(newValue);
				}else{
					data_studyNoListView.clear();
				}
				
			}});
		qrIdComboBox.setItems(data_qrIdComboBox);
	}
	
	/**
	 * 更新 qrIdComboBox 及  或选中 qrIdRadiobutton
	 */
	private  void updateqrIdComboBox(){
		if(tblSession != null ){
			String animalType = tblSession.getAnimalType();
			String animalStrain = tblSession.getAnimalStrain();
			String animalLevel = tblSession.getAnimalLevel();
			List<String> qrIdList = BaseService.getTblQRRequestService()
					.getQrIdListByAnimal(animalType,animalStrain,animalLevel);
			if(null != qrIdList){
				for(String qrId : qrIdList){
					data_qrIdComboBox.add(qrId);
				}
				
				String studyNo = tblSession.getStudyNo();
				if(null != studyNo && !studyNo.isEmpty()){
					String qrId = BaseService.getTblQRRequestStudyNoService().getQrIdByStudyNo(studyNo);
					qrIdComboBox.getSelectionModel().select(qrId);
				}
			}
		}
	}
	/**
	 * roomComboBox初始化
	 */
	private void initRoomComboBox() {
		data_roomComboBox.clear();
		roomComboBox.setItems(data_roomComboBox);
	}
	/**
	 * 初始化studyNoListView
	 */
	private void initStudyNoListView() {
		data_studyNoListView.clear();
		studyNoListView.setItems(data_studyNoListView);
	}
	
	/**更新studyNoListView ,根据qrId
	 * @param qrId
	 */
	private void updateStudyNoListView(String qrId){
		data_studyNoListView.clear();
		List<String> studyNoList = BaseService.getTblQRRequestStudyNoService().getStudyNoListsStrByQRRId(qrId);
		if(null != studyNoList && studyNoList.size()>0){
			for(String studyNo :studyNoList){
				data_studyNoListView.add(studyNo);
			}
		}
	}
	/**更新 roomComboBox*/
	public void updateRoomComboBox(String animalType){
		List<String> roomList = BaseService.getTblRoomAndAniTypeService().getRoomListbyAnimalType(animalType);
		if (null != roomList && roomList.size()>0) {
			data_roomComboBox.addAll(roomList);
		}
	}


	/**
	 * 初始化animalTable(待选)
	 */
	private void initAnimalTable() {
		animalIdCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
						"animalId"));
		genderCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
						"gender"));
		roomCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
				"room"));
		animalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		genderCol
				.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView, String>, TableCell<TblSessionAnimalForTableView, String>>() {

					@Override
					public TableCell<TblSessionAnimalForTableView, String> call(
							TableColumn<TblSessionAnimalForTableView, String> arg0) {
						TableCell<TblSessionAnimalForTableView, String> cell = new TableCell<TblSessionAnimalForTableView, String>() {

							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								setText(empty ? null : getString());
								setGraphic(null);
							}

							private String getString() {
								return getItem() == null ? "" : getItem().toString();
							}

						};
						cell.setStyle("-fx-alignment: CENTER;");
						return cell;
					}

				});
		animalTable.setItems(data_animalTable);
	}
	/**
	 * 初始化animalTable（已选）
	 */
	private void initSelectedAnimalTable() {
		animalIdCol2
		.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
				"animalId"));
		genderCol2
		.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
				"gender"));
		roomCol2.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
				"room"));
		selectedAnimalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		genderCol2
		.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView, String>, TableCell<TblSessionAnimalForTableView, String>>() {
			
			@Override
			public TableCell<TblSessionAnimalForTableView, String> call(
					TableColumn<TblSessionAnimalForTableView, String> arg0) {
				TableCell<TblSessionAnimalForTableView, String> cell = new TableCell<TblSessionAnimalForTableView, String>() {
					
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
			
		});
		selectedAnimalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedAnimalTable.setItems(data_selectedAnimalTable);
	}
	/**
	 * 初始化stateTable
	 */
	private void initStateTable() {
		genderCol3
		.setCellValueFactory(new PropertyValueFactory<StateForTableView, String>(
				"gender"));
		numberCol3.setCellValueFactory(new PropertyValueFactory<StateForTableView, String>(
				"number"));
		selectedNumberCol3.setCellValueFactory(new PropertyValueFactory<StateForTableView, String>(
				"selectedNumber"));
		stateTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		genderCol3
		.setCellFactory(new Callback<TableColumn<StateForTableView, String>, TableCell<StateForTableView, String>>() {
			
			@Override
			public TableCell<StateForTableView, String> call(
					TableColumn<StateForTableView, String> arg0) {
				TableCell<StateForTableView, String> cell = new TableCell<StateForTableView, String>() {
					
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
			
		});
		numberCol3
		.setCellFactory(new Callback<TableColumn<StateForTableView, String>, TableCell<StateForTableView, String>>() {
			
			@Override
			public TableCell<StateForTableView, String> call(
					TableColumn<StateForTableView, String> arg0) {
				TableCell<StateForTableView, String> cell = new TableCell<StateForTableView, String>() {
					
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
			
		});
		selectedNumberCol3
		.setCellFactory(new Callback<TableColumn<StateForTableView, String>, TableCell<StateForTableView, String>>() {
			
			@Override
			public TableCell<StateForTableView, String> call(
					TableColumn<StateForTableView, String> arg0) {
				TableCell<StateForTableView, String> cell = new TableCell<StateForTableView, String>() {
					
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
			
		});
		data_stateTable.add(new StateForTableView("雄","0","0"));
		data_stateTable.add(new StateForTableView("雌","0","0"));
		stateTable.setItems(data_stateTable);
	}
	/**
	 * 加载animalTable数据
	 */
	public void updateStateTable(){
		data_stateTable.clear();
		int numMale =0;
		int numFemale =0;
		int selectedNumMale =0;
		int selectedNumFemale =0;
		for(TblSessionAnimalForTableView obj:data_animalTable){
			if(obj.getGender().equals("♂")){
				numMale++;
			}else{
				numFemale++;
			}
		}
		for(TblSessionAnimalForTableView obj:data_selectedAnimalTable){
			if(obj.getGender().equals("♂")){
				numMale++;
				selectedNumMale++;
			}else{
				numFemale++;
				selectedNumFemale++;
			}
		}
		data_stateTable.add(new StateForTableView("雄",""+numMale,""+selectedNumMale));
		data_stateTable.add(new StateForTableView("雌",""+numFemale,""+selectedNumFemale));
	}
	/**
	 * 加载animalTable数据（去除已被选择的动物）
	 */
	public void updateAnimalTable(){
		data_animalTable.clear();
		if (null != sessionId && !sessionId.isEmpty()) {
			// 会话动物列表
			List<TblSessionAnimals> animalList = BaseService.getTblSessionAnimalsService()
					.getListBySessionId(sessionId);
			//已选动物Id号列表
			List<String> animalIdList = new ArrayList<String>();
			for(TblSessionAnimalForTableView obj:data_selectedAnimalTable){
				animalIdList.add(obj.getAnimalId());
			}
			if (null != animalList && animalList.size() > 0) {
				String animalId = "";
				int gender = 0;
				String room = "";
				for (TblSessionAnimals obj : animalList) {
					animalId = obj.getAnimalId();
					if(animalIdList.contains(animalId)){
						continue;
					}
					gender = obj.getGender();
					room = obj.getRoom();
					TblSessionAnimalForTableView tblSessionAnimalForTableView = new TblSessionAnimalForTableView(
							animalId, gender, room);
					data_animalTable.add(tblSessionAnimalForTableView);
				}
			}
		}
	}

	/**
	 * 加载animalTable数据
	 */
	private void updateAnimalTable(List<TblSessionAnimals> tblSessionAnimalsList) {
		data_animalTable.clear();
		if (null != tblSessionAnimalsList && tblSessionAnimalsList.size() > 0) {
			String animalId = "";
			int gender = 0;
			String room = "";
			for (TblSessionAnimals obj : tblSessionAnimalsList) {
				animalId = obj.getAnimalId();
				gender = obj.getGender();
				room = obj.getRoom();
				TblSessionAnimalForTableView tblSessionAnimalForTableView = new TblSessionAnimalForTableView(
						animalId, gender, room);
				data_animalTable.add(tblSessionAnimalForTableView);
			}
		}
	}
	/**
	 * 加载selectedAnimalTable数据
	 */
	public void updateSelectedAnimalTable(){
		data_selectedAnimalTable.clear();
		if (null != sessionId && !sessionId.isEmpty()) {
			// 移交动物列表
			List<TblHandoverAniList> tblHandoverAniListList = BaseService.getTblAniHandoverService()
					.getAniListBySessionId(sessionId);
			if (null != tblHandoverAniListList && tblHandoverAniListList.size() > 0) {
				String animalId = "";
				int gender = 0;
				String room = "";
				for (TblHandoverAniList obj : tblHandoverAniListList) {
					animalId = obj.getAnimalId();
					gender = obj.getGender();
					room = obj.getRoom();
					TblSessionAnimalForTableView tblSessionAnimalForTableView = new TblSessionAnimalForTableView(
							animalId, gender, room);
					data_selectedAnimalTable.add(tblSessionAnimalForTableView);
				}
			}
		}
	}

	/**
	 * 签字
	 */

	@FXML
	private void onSignButton() {
		
		// 1.检查是否已签字
		if (null != signId && !signId.isEmpty()) {
			Alert2.create("已签字，不可重复签字！");
			return;
		}
		// 2.检查输入项
		// 2.1选择动物
		if (data_selectedAnimalTable.size() < 1) {
			Alert2.create("请先选择动物");
			return;
		}
		// 2.2选择移交“目标”
		if(group.getSelectedToggle() == null ){
			Alert2.create("请先选择“移交目标”");
			return;
		}
		String studyNo ="";
		String qrRId ="";
		/**对应课题移交完成*/
		boolean studyNoFinish =  false;//对应课题移交完成（）
		/**对应申请单完成*/
		boolean qrRIdFinish = false;//对应申请单完成（）
		/**雄性 申请单  总需求数*/
		int totalMaleNumber =0;
		/**雌性 申请单  总需求数*/
		int totalFemaleNumber = 0;
		/**雄性 申请单  已移交数*/
		int finishMaleNumber =0;
		/**雌性 申请单   已移交数*/
		int finishFemaleNumber = 0;
		/**雄性 课题  总需求数*/
		int studyTotalMaleNumber =0;
		/**雌性 课题  总需求数*/
		int studyTotalFemaleNumber = 0;
		/**雄性 课题   已移交数*/
		int studyFinishMaleNumber =0;
		/**雌性 课题   已移交数*/
		int studyFinishFemaleNumber = 0;
		/**雄性 当前   选择数*/
		int selectMaleNumber =0;
		/**雌性 当前   选择数*/
		int selectFemaleNumber = 0;
		/**申请单*/
		TblQRRequest tblQRRequest = null;
		/**课题编号列表*/
		List<String> studyNoList =null;
		/**已经移交的动物数量*/
		Map<String,Integer> finishNumberMap =null;
		switch (handoverFlag) {
		case 1://移交到课题
			studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
			if(null == studyNo || studyNo.isEmpty()){
				Alert2.create("请先选择课题编号");
				studyNoComboBox.requestFocus();
				return ;
			}
			qrRId = BaseService.getTblQRRequestStudyNoService().getQrIdByStudyNo(studyNo);
			if(null == qrRId || qrRId.isEmpty()){
				Alert2.create("数据库出错");
				studyNoComboBox.requestFocus();
				return ;
			}
			tblQRRequest = BaseService.getTblQRRequestService().getById(qrRId);
			/**雄性 申请单  总需求数*/
			totalMaleNumber =tblQRRequest.getNumMale();
			/**雌性 申请单  总需求数*/
			totalFemaleNumber = tblQRRequest.getNumFemale();
			
			studyNoList = BaseService.getTblQRRequestStudyNoService().getStudyNoListsStrByQRRId(qrRId);
			//已经移交的动物数量     map<"male",80>  或者map<"female",80>
			finishNumberMap = 
					BaseService.getTblAnimalRecListService().getFinishNumberMapBy(qrRId,studyNoList);
			/**雄性 申请单  已移交数*/
			finishMaleNumber =finishNumberMap.get("male");
			/**雌性 申请单   已移交数*/
			finishFemaleNumber = finishNumberMap.get("female");
			
			TblQRRequestStudyNo tblQRRequestStudyNo = BaseService.getTblQRRequestStudyNoService().getByStudyNo(studyNo);
			/**雄性 课题  总需求数*/
			studyTotalMaleNumber =tblQRRequestStudyNo.getNumMale();
			/**雌性 课题  总需求数*/
			studyTotalFemaleNumber = tblQRRequestStudyNo.getNumFemale();
			
			//当前课题 已经移交的动物数量 map<"male",80> 或者map<"female",80>
			Map<String,Integer> studyFinishNumberMap = 
					BaseService.getTblAnimalRecListService().getStudyFinishNumberMapByStudyNo(studyNo);
			/**雄性 课题   已移交数*/
			studyFinishMaleNumber =studyFinishNumberMap.get("male");
			/**雌性 课题   已移交数*/
			studyFinishFemaleNumber = studyFinishNumberMap.get("female");
			
			
			/**雄性 当前   选择数*/
			selectMaleNumber =0;
			/**雌性 当前   选择数*/
			selectFemaleNumber = 0;
			for(TblSessionAnimalForTableView obj:data_selectedAnimalTable){
				if(obj.getGender().equals("♂")){
					selectMaleNumber++;
				}else{
					selectFemaleNumber++;
				}
			}
			
			//
			if(studyTotalMaleNumber-studyFinishMaleNumber  < selectMaleNumber ||
					studyTotalFemaleNumber-studyFinishFemaleNumber  < selectFemaleNumber ){
				//课题剩余需求   小于  当前选择的
				Alert2.create("课题"+studyNo+" 需求:雄 "+studyTotalMaleNumber+" 雌"+studyTotalFemaleNumber+"" +
						" 已移交数量:雄"+studyFinishMaleNumber+" 雌"+studyFinishFemaleNumber +"" +
						" 当前选择数量:雄"+selectMaleNumber+" 雌"+selectFemaleNumber);
				return ;
			}else if(totalMaleNumber-finishMaleNumber  < selectMaleNumber ||
					totalFemaleNumber-finishFemaleNumber  < selectFemaleNumber ){
				//申请单剩余需求   小于  当前选择的
				Alert2.create("申请单"+qrRId+" 需求:雄 "+totalMaleNumber+" 雌"+totalFemaleNumber+"" +
						" 已移交数量:雄"+finishMaleNumber+" 雌"+finishFemaleNumber +"" +
						" 当前选择数量:雄"+selectMaleNumber+" 雌"+selectFemaleNumber);
				return ;
			}else if(studyTotalMaleNumber-studyFinishMaleNumber  == selectMaleNumber ||
					studyTotalFemaleNumber-studyFinishFemaleNumber  == selectFemaleNumber ){
				//课题剩余需求   等于  当前选择的
				studyNoFinish = true;
				if(totalMaleNumber-finishMaleNumber  == selectMaleNumber ||
						totalFemaleNumber-finishFemaleNumber  == selectFemaleNumber ){
					qrRIdFinish =true;
				}
				
			}else if(totalMaleNumber-finishMaleNumber  == selectMaleNumber ||
					totalFemaleNumber-finishFemaleNumber  == selectFemaleNumber ){
				qrRIdFinish =true;
			}
			
			break;
		case 2://移交到申请单
			qrRId = qrIdComboBox.getSelectionModel().getSelectedItem();
			if(null == qrRId || qrRId.isEmpty()){
				Alert2.create("请先选择申请单号");
				qrIdComboBox.requestFocus();
				return ;
			}
			tblQRRequest = BaseService.getTblQRRequestService().getById(qrRId);
			/**雄性 申请单  总需求数*/
			totalMaleNumber =tblQRRequest.getNumMale();
			/**雌性 申请单  总需求数*/
			totalFemaleNumber = tblQRRequest.getNumFemale();
			
			studyNoList = BaseService.getTblQRRequestStudyNoService().getStudyNoListsStrByQRRId(qrRId);
			//已经移交的动物数量     map<"male",80>  或者map<"female",80>
			finishNumberMap = 
					BaseService.getTblAnimalRecListService().getFinishNumberMapBy(qrRId,studyNoList);
			//雄性 申请单  已移交数
			finishMaleNumber =finishNumberMap.get("male");
			//雌性 申请单   已移交数
			finishFemaleNumber = finishNumberMap.get("female");
			for(TblSessionAnimalForTableView obj:data_selectedAnimalTable){
				if(obj.getGender().equals("♂")){
					selectMaleNumber++;
				}else{
					selectFemaleNumber++;
				}
			}
			//
			if(totalMaleNumber-finishMaleNumber  < selectMaleNumber ||
					totalFemaleNumber-finishFemaleNumber  < selectFemaleNumber ){
				//申请单剩余需求   小于  当前选择的
				Alert2.create("申请单"+qrRId+" 需求:雄 "+totalMaleNumber+" 雌"+totalFemaleNumber+"" +
						" 已移交数量:雄"+finishMaleNumber+" 雌"+finishFemaleNumber +"" +
						" 当前选择数量:雄"+selectMaleNumber+" 雌"+selectFemaleNumber);
				return ;
			}else if(totalMaleNumber-finishMaleNumber  == selectMaleNumber ||
					totalFemaleNumber-finishFemaleNumber  == selectFemaleNumber ){
				qrRIdFinish =true;
			}
			
			break;
			
		case 3:
			if(isBeiku) Alert2.create("备库动物不能再移交到备库");
			break;

		default: 
			Alert2.create("请先选择“移交目标”");
			return;
		}
		// 2.3选择房间号
		String room = roomComboBox.getSelectionModel().getSelectedItem();
		if (null == room || room.isEmpty()) {
			Alert2.create("请先选择房间号");
			roomComboBox.requestFocus();
			return;
		}
		// 2.4动物
		String aniState = aniStateText.getText().trim();
		if(aniState.getBytes().length>100){
			Alert2.create("'动物状况描述'长度不能大于100");
			aniStateText.requestFocus();
			return;
		}
		// 2.5 备注
		String remark = remarkText.getText().trim();
		if(remark.getBytes().length>100){
			Alert2.create("'备注'长度不能大于100");
			remarkText.requestFocus();
			return;
		}
		
		
		// 3.签字
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("签字");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("OK".equals(SignFrame.getType())) {
			// 4 无 sessionId ，保存 会话和会话动物列表，以及 beginDate及endTime
			Date beginDate = new Date();
			tblSession.setBeginTime(beginDate);
			tblSession.setEndTime(beginDate);
			sessionId = BaseService.getTblSessionService().saveTblSessionAndAnimalList(tblSession,
					tblSessionAnimalsList);
			tblSession.setSessionId(sessionId);

			// 离开页面时，是否需要更新DayToDayPane上tblSession表数据
			updateFlag = true;
			
			// 5.动物移交记录
			TblAniHandover tblAniHandover = new TblAniHandover();
			tblAniHandover.setSessionId(sessionId);
			tblAniHandover.setHandoverDate(beginDate);
			tblAniHandover.setHandoverFlag(handoverFlag);
			switch (handoverFlag) {
			case 1:
				tblAniHandover.setStudyNo(studyNo);
				break;
			case 2:
				tblAniHandover.setQrRId(qrRId);
				break;
			default:
				break;
			}
			tblAniHandover.setAnimalType(tblSession.getAnimalType());
			tblAniHandover.setAnimalStrain(tblSession.getAnimalStrain());
			tblAniHandover.setNewRoom(room);
			tblAniHandover.setNumMale(selectMaleNumber);
			tblAniHandover.setNumFemale(selectFemaleNumber);
			String applicant ="";//接受者
			if(null !=tblQRRequest){
				tblAniHandover.setWeightMaleU(tblQRRequest.getWeightMaleU());
				tblAniHandover.setWeightMaleL(tblQRRequest.getWeightMaleL());
				tblAniHandover.setWeightFemaleU(tblQRRequest.getWeightFemaleU());
				tblAniHandover.setWeightFemaleL(tblQRRequest.getWeightFemaleL());
				tblAniHandover.setWeightUnit(tblQRRequest.getWeightUnit());
				
				tblAniHandover.setAgeMaleU(tblQRRequest.getAgeMaleU());
				tblAniHandover.setAgeMaleL(tblQRRequest.getAgeMaleL());
				tblAniHandover.setAgeFemaleU(tblQRRequest.getAgeFemaleU());
				tblAniHandover.setAgeFemaleL(tblQRRequest.getAgeFemaleL());
				tblAniHandover.setAgeUnit(tblQRRequest.getAgeUnit());
				
				//接受者
				applicant = tblQRRequest.getApplicant();
			}
			tblAniHandover.setReceiver(applicant);
			//移交者
			tblAniHandover.setOperator(SignFrame.getUser().getRealName());
			tblAniHandover.setAniState(aniState);
			tblAniHandover.setRemark(remark);
			/**移交动物列表*/
			List<TblHandoverAniList> tblHandoverAniListList= new ArrayList<TblHandoverAniList>();
			List<String> animalIdList = new ArrayList<String>();
			TblHandoverAniList tblHandoverAniList = null ;
			
			for(TblSessionAnimalForTableView obj:data_selectedAnimalTable){
				tblHandoverAniList = new TblHandoverAniList();
				tblHandoverAniList.setSessionId(sessionId);
				tblHandoverAniList.setAnimalId(obj.getAnimalId());
				tblHandoverAniList.setGender(obj.getGender().equals("♂") ? 1:2);
				tblHandoverAniListList.add(tblHandoverAniList);
				animalIdList.add(obj.getAnimalId());
			}
			BaseService.getTblAniHandoverService().saveEntityAndList(tblAniHandover,tblHandoverAniListList);

			//6.更新接收动物列表中对应的  备库、进入备库时间、 移交标志、移交接收人、移交时间、
			//    移交申请单号、移交课题编号   、移交后房间
			int reserveFlag = 0 ;	//备库标志
			Date reserveDate =null;	//进入备库时间
			int  transFlag =0;		//移交标志
			switch (handoverFlag) {
			case 1:
				reserveFlag =0;
				reserveDate = null;
				transFlag = 1;
				BaseService.getTblAnimalRecListService().updateAnimalHandover(animalIdList,reserveFlag
						,reserveDate,transFlag,applicant,beginDate,"",studyNo,room);
				break;
			case 2:
				reserveFlag =0;
				reserveDate = null;
				transFlag = 1;
				BaseService.getTblAnimalRecListService().updateAnimalHandover(animalIdList,reserveFlag
						,reserveDate,transFlag,applicant,beginDate,qrRId,"",room);
				break;
			case 3:
				reserveFlag =1;
				reserveDate = beginDate;
				transFlag = 0;
				BaseService.getTblAnimalRecListService().updateAnimalHandover(animalIdList,reserveFlag
						,reserveDate,transFlag,"",null,"","",room);
				break;

			default:
				break;
			}
			//7.签字
			signId = BaseService.getTblSessionService().sign(sessionId, 19,
					SignFrame.getUser().getRealName(), "检疫，动物移交签字");
			//8.更新  申请单、申请单课题编号   中的移交状态
			switch(handoverFlag){
				case 1://移交课题
					//对应申请单移交完成
					if(qrRIdFinish){
						//更新申请单及对应所有课题  的移交状态置为1
						BaseService.getTblQRRequestService().updateTransferStateByQRRId(qrRId, 1);
					}else{
						//对应课题移交完成
						if(studyNoFinish){
							//课题  的移交状态置为1
							BaseService.getTblQRRequestStudyNoService().updateTransferStateByStudyNo(studyNo, 1);
							//判断该课题对应申请单下的其他课题是否也都完成，
							boolean allStudyNoFinish = BaseService.getTblQRRequestStudyNoService().isAllStudyNoFinishByQrRId(qrRId);
							//如果是，则更新申请单的移交状态置为 1
							if(allStudyNoFinish){
								BaseService.getTblQRRequestService().updateTransferStateByQRRId(qrRId, 1);
							}
							
						}
					}
					break;
				case 2://移交申请单
					//对应申请单移交完成
					if(qrRIdFinish){
						//更新申请单及对应所有课题  的移交状态置为1
						BaseService.getTblQRRequestService().updateTransferStateByQRRId(qrRId, 1);
					}
					break;
				default:break;
			}
			
			
//			
//			// 9.禁用 一些控件
			hbox.setVisible(true);
			hbox1.setVisible(true);
			Alert.create("保存及签字成功");
		}

	}

	/**
	 * 打印
	 */
	@FXML
	private void onPrintButton() {}

	/**
	 * 关闭
	 */
	@FXML
	private void onExitButton(ActionEvent event) {
		if ((null == sessionId || "".equals(sessionId))
				&& !Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")) {
		} else {
			// 1.判断是否需要更新DayToDayPane上tblSession表数据
			if (updateFlag) {
				DayToDayController.getInstance().loadData();
			}
			// 2.关闭窗口
			Button button = (Button) event.getSource();
			button.getScene().getWindow().hide();
		}
	}

	/**
	 * 全选
	 * @param event
	 */
	@FXML
	private void onSelectAllButton(ActionEvent event){
		if(data_animalTable.size()>0){
			animalTable.getSelectionModel().selectAll();
		}
	}
	/**
	 * 全不选
	 * @param event
	 */
	@FXML
	private void onSelectNoneButton(ActionEvent event){
		if(data_animalTable.size()>0){
			animalTable.getSelectionModel().clearSelection();
		}
	}
	/**
	 * 反选
	 * @param event
	 */
	@FXML
	private void onSelectInvertButton(ActionEvent event){
		if(data_animalTable.size()>0){
			for(int i=0;i<data_animalTable.size();i++){
				if(animalTable.getSelectionModel().isSelected(i)){
					animalTable.getSelectionModel().clearSelection(i);
				}else{
					animalTable.getSelectionModel().select(i);
				}
			}
		}
	}

	/**
	 * 向右
	 * 
	 * @param event
	 */
	@FXML
	private void onRightButton(ActionEvent event) {

		int total = data_animalTable.size();
		int selectNum = animalTable.getSelectionModel().getSelectedItems().size();
		if (selectNum > 0) {
			ObservableList<TblSessionAnimalForTableView> selectList = FXCollections
					.observableArrayList(animalTable.getSelectionModel().getSelectedItems());
			// 左边 删除选中项
			data_animalTable.removeAll(selectList);
			// 左边 清空选中项
			animalTable.getSelectionModel().clearSelection();

			// 右边 添加选中项
			for (TblSessionAnimalForTableView obj : selectList) {
				data_selectedAnimalTable.add(obj);
			}
			// 右边 清楚原选中项
			selectedAnimalTable.getSelectionModel().clearSelection();
			// 右边 排序
			Collections.sort(data_selectedAnimalTable,
					new Comparator<TblSessionAnimalForTableView>() {
						@Override
						public int compare(TblSessionAnimalForTableView o1,
								TblSessionAnimalForTableView o2) {
							return o1.getAnimalId().compareTo(o2.getAnimalId());
						}
					});
			// 右边 选择选中项
			for (TblSessionAnimalForTableView obj : selectList) {
				selectedAnimalTable.getSelectionModel().select(obj);
			}
			if ((data_selectedAnimalTable.size() - selectList.size()) > 0) {
				int index = selectedAnimalTable.getSelectionModel().getSelectedIndices().get(0);
				selectedAnimalTable.scrollTo(index);
			}

			// 更新状态表格
			updateStateTable();

		} else {
			Alert2.create("请先选择数据");
			return;
		}

	}
	/**向左
	 * @param event
	 */
	@FXML
	private void onLeftButton(ActionEvent event){

		int selectNum = selectedAnimalTable.getSelectionModel().getSelectedItems().size();
		if (selectNum > 0) {
			ObservableList<TblSessionAnimalForTableView> selectList = FXCollections
					.observableArrayList(selectedAnimalTable.getSelectionModel().getSelectedItems());
			// 右边 删除选中项
			data_selectedAnimalTable.removeAll(selectList);
			// 右边 清空选中项
			selectedAnimalTable.getSelectionModel().clearSelection();

			// 左边 添加选中项
			for (TblSessionAnimalForTableView obj : selectList) {
				data_animalTable.add(obj);
			}
			// 左边 清楚原选中项
			animalTable.getSelectionModel().clearSelection();
			// 左边 排序
			Collections.sort(data_animalTable,
					new Comparator<TblSessionAnimalForTableView>() {
						@Override
						public int compare(TblSessionAnimalForTableView o1,
								TblSessionAnimalForTableView o2) {
							return o1.getAnimalId().compareTo(o2.getAnimalId());
						}
					});
			// 左边 选择选中项
			for (TblSessionAnimalForTableView obj : selectList) {
				animalTable.getSelectionModel().select(obj);
			}
			if ((data_animalTable.size() - selectList.size()) > 0) {
				int index = animalTable.getSelectionModel().getSelectedIndices().get(0);
				animalTable.scrollTo(index);
			}

			// 更新状态表格
			updateStateTable();

		} else {
			Alert2.create("请先选择数据");
			return;
		}

	
	}
	/**
	 * 加载数据，控件，表格
	 * 
	 * @param sessionId
	 */
	public void loadData(String sessionId) {
		AniHandoverFrame.sessionId = sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		AniHandoverFrame.tblSessionAnimalsList = null;
		if (null != tblSession) {
			// 1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(tblSession.getCreateTime(),
					"yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if (null != studyNo && !"".equals(studyNo)) {
				studyNoLabel.setText("课题编号：" + studyNo);
			} else {
				studyNoLabel.setText("接收批号：" + tblSession.getRecId());
			}

			// 2.加载animalTable数据及selectedAnimalTable
			updateSelectedAnimalTable();
			updateAnimalTable();
			
			//3.加载房间号comboBox
			updateRoomComboBox(tblSession.getAnimalType());
			//4.加载stateTable数据
			updateStateTable();
			//5.更新 studyNoComboBox 初始化及 或选中 studyNoRadiobutton
			updateStudyNoComboBox();
			studyNoComboBox.getSelectionModel().clearSelection();
			//6.更新 qrIdComboBox 及 或选中 qrRadiobutton
			updateqrIdComboBox();
			qrIdComboBox.getSelectionModel().clearSelection();
			//7. 签字标记
			signId = tblSession.getSignId();
			hbox.setVisible(true);
			hbox1.setVisible(true);
			
			TblAniHandover tblAniHandover = BaseService.getTblAniHandoverService().getById(sessionId);
			if(null != tblAniHandover){
				handoverFlag = tblAniHandover.getHandoverFlag();
				String aniState = tblAniHandover.getAniState();
				String remark = tblAniHandover.getRemark();
				String room = tblAniHandover.getNewRoom();
				aniStateText.setText(aniState);
				remarkText.setText(remark);
				roomComboBox.getSelectionModel().select(room);
				switch (handoverFlag) {
				case 1:
					studyNoRadioButton.setSelected(true);
					studyNoComboBox.getSelectionModel().select(tblAniHandover.getStudyNo());
					break;
				case 2:
					qrRadioButton.setSelected(true);
					qrIdComboBox.getSelectionModel().select(tblAniHandover.getQrRId());
					break;
				case 3:
					beikuRadioButton.setSelected(true);
					break;

				default:
					break;
				}
				
			}
		}
	}

	/**
	 * 加载数据，控件，表格( 暂无sessionId )
	 * 
	 * @param tblSession
	 * @param tblSessionAnimalsList
	 */
	public void loadDataByTblSession(TblSession tblSession,
			List<TblSessionAnimals> tblSessionAnimalsList) {
		AniHandoverFrame.sessionId = null;
		AniHandoverFrame.tblSession = tblSession;
		AniHandoverFrame.tblSessionAnimalsList = tblSessionAnimalsList;
		signId = null;
		if (null != tblSession) {
			// 1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(tblSession.getCreateTime(),
					"yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if (null != studyNo && !"".equals(studyNo)) {
				studyNoLabel.setText("课题编号：" + studyNo);
			} else {
				studyNoLabel.setText("接收批号：" + tblSession.getRecId());
			}

			// 2.加载animalTable数据
			updateAnimalTable(tblSessionAnimalsList);
			data_selectedAnimalTable.clear();
			
			//3.加载房间号comboBox
			updateRoomComboBox(tblSession.getAnimalType());
			//4.加载stateTable数据
			updateStateTable();
			//5.更新 studyNoComboBox 初始化及 或选中 studyNoRadiobutton
			updateStudyNoComboBox();
			//6.更新 qrIdComboBox 及 或选中 qrRadiobutton
			updateqrIdComboBox();
			//7.判断是否是备库动物，若是则beikuRadioButton 禁用
			isBeiku = isBeiku();
			if(isBeiku){
				qrRadioButton.setDisable(true);
			}
		}

	}

	/**判断是否是备库动物
	 * @return
	 */
	private boolean isBeiku() {
		String animalId ="";
		if(data_animalTable.size()>0){
			animalId = data_animalTable.get(0).getAnimalId();
		}else if(data_selectedAnimalTable.size()>0){
			animalId = data_selectedAnimalTable.get(0).getAnimalId();
		}
		if(animalId != null && !animalId.isEmpty()){
			return 
					BaseService.getTblAnimalRecListService().isBeikuByAnimalId(animalId);
		}
		return false;
	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(this.getClass().getResource("AniHandoverFrame.fxml"));
		Scene scene = new Scene(root, 850, 550);
		stage.setScene(scene);
		stage.setTitle("重新安置");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				if ((null == sessionId || "".equals(sessionId))
						&& !Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")) {
					event.consume();
				} else {
					// 判断是否需要更新DayToDayPane上tblSession表数据
					if (updateFlag) {
						DayToDayController.getInstance().loadData();
					}
				}
			}
		});
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
