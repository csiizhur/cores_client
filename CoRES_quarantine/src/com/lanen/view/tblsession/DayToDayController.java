package com.lanen.view.tblsession;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.quarantine.TblAnimalRecIndex;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.quarantine.tblsession.TblAnimalDeath;
import com.lanen.model.quarantine.tblsession.TblBodyWeight;
import com.lanen.model.quarantine.tblsession.TblGeneralObservation;
import com.lanen.model.quarantine.tblsession.TblPhyExam;
import com.lanen.model.quarantine.tblsession.TblPhyExamResult;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.TblAnimalRecListForTableView;
import com.lanen.model.tableview.TblSessionForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class DayToDayController extends Application implements Initializable {

	private String[] sessionTypes = { "数据浏览", "体重称重", "一般观察", "动物体检", "检疫报告", "动物驯化", "异常处理",
			"动物死亡", "重新安置", "动物移交" };
	/**
	 * 数据浏览
	 */
	@FXML
	private ToggleButton toggleButton1;
	/**
	 * 体重称重
	 */
	@FXML
	private ToggleButton toggleButton2;
	/**
	 * 一般观察
	 */
	@FXML
	private ToggleButton toggleButton3;
	/**
	 * 动物体检
	 */
	@FXML
	private ToggleButton toggleButton4;
	/**
	 * 检疫报告
	 */
	@FXML
	private ToggleButton toggleButton5;
	/**
	 * 动物驯化
	 */
	@FXML
	private ToggleButton toggleButton6;
	/**
	 * 异常处理
	 */
	@FXML
	private ToggleButton toggleButton7;
	/**
	 * 动物死亡
	 */
	@FXML
	private ToggleButton toggleButton8;
	/**
	 * 重新安置
	 */
	@FXML
	private ToggleButton toggleButton9;
	/**
	 * 动物移交
	 */
	@FXML
	private ToggleButton toggleButton10;

	private ToggleGroup group = new ToggleGroup();
	@FXML
	private TabPane tabPane;
	/**
	 * 动物xx记录Tab
	 */
	@FXML
	private Tab tab2;

	@FXML
	private AnchorPane tab2Pane;

	@FXML
	private Button openButton;
	@FXML
	private Button signButton;
	@FXML
	private Button checkButton;
	@FXML
	private Button printButton;

	/**
	 * 新建 button
	 */
	@FXML
	private Button newbuiltButton;
	@FXML
	private ComboBox<String> animalTypeComboBox;
	private ObservableList<String> data_animalTypeComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> recIdComboBox;
	private ObservableList<String> data_recIdComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> studyNoComboBox;
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	/**
	 * 动物表格
	 */
	@FXML
	private TableView<TblAnimalRecListForTableView> animalTable;
	private static ObservableList<TblAnimalRecListForTableView> data_animalTable = FXCollections
			.observableArrayList();
	private TblAnimalRecListForTableView currentSelectItem;
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> snCol;
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> recIdCol; // 接收单号
																		// RecID
																		// varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> animalTypeCol; // 动物种类
																				// AnimalType
																				// varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> animalStrainCol; // 动物品系
																				// AnimalStrain
																				// varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> animalIdCol; // 动物ID号
																			// AnimalID
																			// varchar(30)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> genderCol; // 性别
																			// Gender
																			// integer
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> roomCol; // 申请单号
																		// QRRID
																		// varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> planStudyNoCol; // 分配课题编号
																				// PlanStudyNo
																				// varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView, String> stateCol; // 状态 死亡
																		// ，移交，备库
	@FXML
	private TableColumn<TblAnimalRecListForTableView, Boolean> selectCol; // 选择框

	@FXML
	private Label selectNumLabel;
	@FXML
	private Label totalNumLabel;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private Label label3;

	@FXML
	private Hyperlink selectAllButton;
	@FXML
	private Hyperlink selectNoneButton;
	@FXML
	private Hyperlink selectInvertButton;

	// -----------------------------------------tab2 面板
	@FXML
	private HBox startDateHBox;
	@FXML
	private HBox endDateHBox;
	private DatePicker startDatePane = null;
	private DatePicker endDatePane = null;

	@FXML
	private ComboBox<String> animalTypeComboBox_2;
	private ObservableList<String> data_animalTypeComboBox_2 = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> studyNoComboBox_2;
	private ObservableList<String> data_studyNoComboBox_2 = FXCollections.observableArrayList();
	@FXML
	private TableView<TblSessionForTableView> tblSessionTable;
	private ObservableList<TblSessionForTableView> data_tblSessionTable = FXCollections
			.observableArrayList();
	@FXML
	private TableColumn<TblSessionForTableView, String> sessionIdCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> createrCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> createTimeCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> sessionTypeCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> recIdCol_2;
	@FXML
	private TableColumn<TblSessionForTableView, String> studyNoCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> beginTimeCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> endTimeCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> animalTypeCol_2;
	@FXML
	private TableColumn<TblSessionForTableView, String> signerCol;
	@FXML
	private TableColumn<TblSessionForTableView, String> checkerCol;

	private String startDateStr = "";
	private String endDateStr = "";
	private String animalTypeStr = "";
	private String studyNoStr = "";

	private int currentIndex = 0;// 当前选中按钮的索引 0,1,2,3,4
	//
	private Stage stage = new Stage();
	private static DayToDayController instance;

	public static DayToDayController getInstance() {
		return instance;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(Main.getInstance().getStage());
		currentIndex = 0;
		// 按钮置为一组
		initToggleButton();
		// 初始化 三个下拉框
		initComboBox();
		// 初始化动物表格
		initAnimalTable();
		// 移除tab2
		tabPane.getTabs().remove(tab2);
		newbuiltButton.setDisable(true);
		// 初始化日期选择器
		initDate();
		// 初始化animalTypeComboBox_2
		initAnimalTypeCombobox_2();
		// 初始化studyNoComboBox_2
		initStudyNoCombobox_2();
		// 初始化tblSessionTable
		initTblSessionTable();
	}

	/**
	 * 初始化tblSessionTable
	 */
	private void initTblSessionTable() {
		sessionIdCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"sessionId"));
		createrCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"creater"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"createTime"));
		sessionTypeCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
						"sessionType"));
		recIdCol_2.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"recId"));
		studyNoCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"studyNo"));
		beginTimeCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"beginTime"));
		endTimeCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"endTime"));
		animalTypeCol_2
				.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
						"animalType"));
		signerCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"signer"));
		checkerCol.setCellValueFactory(new PropertyValueFactory<TblSessionForTableView, String>(
				"checker"));
		tblSessionTable.setItems(data_tblSessionTable);
		tblSessionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	/**
	 * 初始化studyNoComboBox_2
	 */
	private void initStudyNoCombobox_2() {
		studyNoComboBox_2.setItems(data_studyNoComboBox_2);
		studyNoComboBox_2.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {
							studyNoStr = newValue;
						} else {
							studyNoStr = "";
						}

					}
				});
	}

	/**
	 * 初始化animalTypeComboBox_2 ，及赋值
	 */
	private void initAnimalTypeCombobox_2() {
		data_animalTypeComboBox_2.clear();
		animalTypeComboBox_2.setItems(data_animalTypeComboBox_2);
		data_animalTypeComboBox_2.add("");
		if (data_animalTypeComboBox.size() > 0) {
			for (String str : data_animalTypeComboBox) {
				data_animalTypeComboBox_2.add(str);
			}
		}
		animalTypeComboBox_2.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {
							animalTypeStr = newValue;
						} else {
							animalTypeStr = "";
						}

					}
				});
	}

	/**
	 * 初始化日期选择器
	 */
	private void initDate() {
		startDatePane = new DatePicker(Locale.CHINA);
		startDatePane.getTextField().setEditable(false);
		startDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		startDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		startDatePane.getCalendarView().setShowWeeks(false);
		startDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		startDatePane.setMaxWidth(110);

		startDateHBox.getChildren().add(startDatePane);
		startDatePane.getTextField().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue,
					String newValue) {
				if (null != newValue && !"".equals(newValue)) {
					// 更新 的值
					startDateStr = newValue;
				}
			}

		});
		startDateStr = DateUtil.getDateAgo(6);
		startDatePane.getTextField().setText(startDateStr);

		endDatePane = new DatePicker(Locale.CHINA);
		endDatePane.getTextField().setEditable(false);
		endDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		endDatePane.getCalendarView().setShowWeeks(false);
		endDatePane.getStylesheets()
				.add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endDatePane.setMaxWidth(110);

		endDateHBox.getChildren().add(endDatePane);
		endDatePane.getTextField().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue,
					String newValue) {
				if (null != newValue && !"".equals(newValue)) {
					// 更新 的值
					endDateStr = newValue;
				}
			}

		});
		endDateStr = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		endDatePane.getTextField().setText(endDateStr);
	}

	/**
	 * 初始化动物表格
	 */
	private void initAnimalTable() {
		selectCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, Boolean>(
						"select"));
		snCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
				"sn"));
		recIdCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
				"recId"));
		animalTypeCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
						"animalType"));
		animalStrainCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
						"animalStrain"));
		animalIdCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
						"animalId"));
		genderCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
						"gender"));
		roomCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
				"room"));
		planStudyNoCol
				.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
						"planStudyNo"));
		stateCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView, String>(
				"state"));
		animalTable.setItems(data_animalTable);
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// animalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectCol
				.setCellFactory(new Callback<TableColumn<TblAnimalRecListForTableView, Boolean>, TableCell<TblAnimalRecListForTableView, Boolean>>() {

					@Override
					public TableCell<TblAnimalRecListForTableView, Boolean> call(
							TableColumn<TblAnimalRecListForTableView, Boolean> p) {
						return new CheckBoxTableCell<TblAnimalRecListForTableView, Boolean>();
					}
				});
		snCol.setCellFactory(new Callback<TableColumn<TblAnimalRecListForTableView, String>, TableCell<TblAnimalRecListForTableView, String>>() {

			@Override
			public TableCell<TblAnimalRecListForTableView, String> call(
					TableColumn<TblAnimalRecListForTableView, String> param) {
				TableCell<TblAnimalRecListForTableView, String> cell = new TableCell<TblAnimalRecListForTableView, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
						if (!empty) {
							if (!((TblAnimalRecListForTableView) getTableRow().getItem())
									.getSelect()) {
								getTableRow().setStyle("");

							}
						}
					}

					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}

				};
				// cell.setStyle("-fx-alignment: CENTER;");
				return cell;

			}

		});
		animalTable.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TblAnimalRecListForTableView>() {

					@Override
					public void changed(
							ObservableValue<? extends TblAnimalRecListForTableView> arg0,
							TblAnimalRecListForTableView arg1, TblAnimalRecListForTableView newValue) {
						if (null != newValue && (currentIndex == 6 || currentIndex == 7)) {
							currentSelectItem = newValue;
							// 更新animalTable状态栏的值
							updateState();
							animalTable.getSelectionModel().clearSelection();
						}
					}
				});
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<TblAnimalRecListForTableView> table = (TableView<TblAnimalRecListForTableView>) event
							.getSource();
					// TblAnimalRecListForTableView obj=
					// table.getSelectionModel().getSelectedItem();
					if ((currentIndex == 6 || currentIndex == 7) && null != currentSelectItem
							&& table.getItems().indexOf(currentSelectItem) > -1) {
						TblAnimalRecListForTableView index = new TblAnimalRecListForTableView(
								currentSelectItem.getId(), Integer.parseInt(currentSelectItem
										.getSn()), currentSelectItem.getRecId(), currentSelectItem
										.getAnimalType(), currentSelectItem.getAnimalStrain(),
								currentSelectItem.getAnimalId(), (currentSelectItem.getGender()
										.equals("♂") ? 1 : 2), currentSelectItem.getRoom(),
								currentSelectItem.getPlanStudyNo(), currentSelectItem.getState(),
								currentSelectItem.getAnimalLevel());
						index.setSelect(!currentSelectItem.getSelect());
						table.getItems().set(table.getItems().indexOf(currentSelectItem), index);
					}
					// table.getSelectionModel().clearSelection();
					// 更新animalTable状态栏的值
					updateState();
				}

			}
		});
	}

	/**
	 * 根据接收单号 更新动物表格数据
	 * 
	 * @param recId
	 */
	private void updateAnimalTable(String recId) {
		data_animalTable.clear();
		if (null != recId && !"".equals(recId)) {
			List<TblAnimalRecList> tblAnimalRecLists = BaseService.getTblAnimalRecListService()
					.getListByRecIdAndState(recId);
			if (null != tblAnimalRecLists && tblAnimalRecLists.size() > 0) {
				String state = "";
				for (TblAnimalRecList obj : tblAnimalRecLists) {
					state = "";
					if (obj.getTransFlag() == 1) {
						state = "移交";
					}
					if (obj.getReserveFlag() == 1) {
						state = "备库";
					}
					if (obj.getDeadFlag() == 1) {
						state = "死亡";
					}
					data_animalTable.add(new TblAnimalRecListForTableView(obj.getId(), obj.getSn(),
							obj.getRecId(), obj.getAnimalType(), obj.getAnimalStrain(), obj
									.getAnimalId(), obj.getGender(), obj.getRoom(), obj
									.getPlanStudyNo(), state, obj.getAnimalLevel()));
				}
			}
		}
		updateState();
	}

	/**
	 * 根据课题编号 更新动物表格数据
	 * 
	 * @param recId
	 */
	private void updateAnimalTableByStudyNo(String studyNo) {
		data_animalTable.clear();
		if (null != studyNo && !"".equals(studyNo)) {
			List<TblAnimalRecList> tblAnimalRecLists = BaseService.getTblAnimalRecListService()
					.getListByStudyNoAndState(studyNo);
			if (null != tblAnimalRecLists && tblAnimalRecLists.size() > 0) {
				String state = "";
				for (TblAnimalRecList obj : tblAnimalRecLists) {
					state = "";
					if (obj.getTransFlag() == 1) {
						state = "移交";
					}
					if (obj.getReserveFlag() == 1) {
						state = "备库";
					}
					if (obj.getDeadFlag() == 1) {
						state = "死亡";
					}
					data_animalTable.add(new TblAnimalRecListForTableView(obj.getId(), obj.getSn(),
							obj.getRecId(), obj.getAnimalType(), obj.getAnimalStrain(), obj
									.getAnimalId(), obj.getGender(), obj.getRoom(), obj
									.getPlanStudyNo(), state, obj.getAnimalLevel()));
				}
			}
		}
		updateState();

	}

	/**
	 * 初始化 三个下拉框
	 */
	private void initComboBox() {
		// 1.动物种类下拉框
		data_animalTypeComboBox.clear();
		animalTypeComboBox.setItems(data_animalTypeComboBox);
		List<DictAnimalType> dictAnimalTypeList = BaseService.getDictAnimalTypeService()
				.findAllOrderByOrderNo();
		if (null != dictAnimalTypeList && dictAnimalTypeList.size() > 0) {
			for (DictAnimalType obj : dictAnimalTypeList) {
				data_animalTypeComboBox.add(obj.getTypeName());
			}
		}

		animalTypeComboBox.getSelectionModel().selectedItemProperty()//
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {
							// 更新 recIdComboBox值
							updateData_recIdComboBox(newValue);
							// 更新studyNoComboBox值
							updateData_studyNoComboBox(newValue);
						}

					}

				});
		// 2.接收批号下拉框
		data_recIdComboBox.clear();
		recIdComboBox.setItems(data_recIdComboBox);
		recIdComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						studyNoComboBox.getSelectionModel().clearSelection();
						// 根据接收单号 更新动物表格数据
						updateAnimalTable(newValue);
					}
				});
		// 3.课题编号下拉框
		data_studyNoComboBox.clear();
		studyNoComboBox.setItems(data_studyNoComboBox);
		studyNoComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						recIdComboBox.getSelectionModel().clearSelection();
						// 根据课题编号 更新动物表格数据
						updateAnimalTableByStudyNo(newValue);
					}
				});

	}

	/**
	 * 更新studyNoComboBox值
	 * 
	 * @param animalType
	 */
	private void updateData_studyNoComboBox(String animalType) {
		if (null != animalType && !"".equals(animalType)) {
			data_studyNoComboBox.clear();
			List<String> studyNoList = BaseService.getTblQRRequestService().getStudyNoListByAnimal(
					animalType);
			if (null != studyNoList) {
				data_studyNoComboBox.add("");
				for (String studyNo : studyNoList) {
					data_studyNoComboBox.add(studyNo);
				}
			}
		}
	}

	/**
	 * 更新 recIdComboBox值
	 * 
	 * @param animalType
	 */
	private void updateData_recIdComboBox(String animalType) {
		if (null != animalType && !"".equals(animalType)) {
			data_recIdComboBox.clear();
			List<String> recIdList = BaseService.getTblAnimalRecIndexService()
					.getRecIdListByAnimal(animalType);
			if (null != recIdList) {
				data_recIdComboBox.add("");
				for (String recId : recIdList) {
					data_recIdComboBox.add(recId);
				}
			}
		}
	}

	/**
	 * 按钮置为一组
	 */
	private void initToggleButton() {
		toggleButton1.setToggleGroup(group);
		toggleButton2.setToggleGroup(group);
		toggleButton3.setToggleGroup(group);
		toggleButton4.setToggleGroup(group);
		toggleButton5.setToggleGroup(group);
		toggleButton6.setToggleGroup(group);
		toggleButton7.setToggleGroup(group);
		toggleButton8.setToggleGroup(group);
		toggleButton9.setToggleGroup(group);
		toggleButton10.setToggleGroup(group);

	}

	@FXML
	private void onToggleButton1(ActionEvent event) {
		toggleButton1.setSelected(true);
		currentIndex = 0;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		if (tabPane.getTabs().size() > 1) {
			tab2.setDisable(true);
			tabPane.getTabs().remove(tab2);
		}
		newbuiltButton.setDisable(true);
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton2(ActionEvent event) {
		toggleButton2.setSelected(true);
		currentIndex = 1;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("体重称重");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton3(ActionEvent event) {
		toggleButton3.setSelected(true);
		currentIndex = 2;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("一般观察");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton4(ActionEvent event) {
		toggleButton4.setSelected(true);
		currentIndex = 3;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("动物体检");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton5(ActionEvent event) {
		toggleButton5.setSelected(true);
		currentIndex = 4;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("检疫报告");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton6(ActionEvent event) {
		toggleButton6.setSelected(true);
		currentIndex = 5;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("动物驯化");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton7(ActionEvent event) {
		toggleButton7.setSelected(true);
		currentIndex = 6;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("异常处理");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton8(ActionEvent event) {
		toggleButton8.setSelected(true);
		currentIndex = 7;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("动物死亡");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(false);
		printButton.setDisable(false);
		//表格  复核列
				checkerCol.setVisible(true);
	}

	@FXML
	private void onToggleButton9(ActionEvent event) {
		toggleButton9.setSelected(true);
		currentIndex = 8;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("重新安置");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(true);
		printButton.setDisable(true);
		//表格  复核列
		checkerCol.setVisible(false);

	}

	@FXML
	private void onToggleButton10(ActionEvent event) {
		toggleButton10.setSelected(true);
		currentIndex = 9;
		// 显示或隐藏 状态栏和selectCol列
		changeState();
		tab2.setText("动物移交");
		if (tabPane.getTabs().size() < 2) {
			tab2.setDisable(false);
			tabPane.getTabs().add(tab2);
		}
		newbuiltButton.setDisable(false);
		// 更新studyNoComboBox_2的值
		updateStudyNoComboBox_2();
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
		
		openButton.setDisable(false);
		signButton.setDisable(false);
		checkButton.setDisable(true);
		printButton.setDisable(false);
		//表格  复核列
		checkerCol.setVisible(false);
	}

	/**
	 * 全选
	 * 
	 * @param event
	 */
	@FXML
	private void onSelectAllButton(ActionEvent event) {
		if (data_animalTable.size() > 0) {
			int index = 0;
			for (TblAnimalRecListForTableView obj : data_animalTable) {
				TblAnimalRecListForTableView rowData = new TblAnimalRecListForTableView(
						obj.getId(), Integer.parseInt(obj.getSn()), obj.getRecId(),
						obj.getAnimalType(), obj.getAnimalStrain(), obj.getAnimalId(), (obj
								.getGender().equals("♂") ? 1 : 2), obj.getRoom(),
						obj.getPlanStudyNo(), obj.getState(), obj.getAnimalLevel());
				rowData.setSelect(true);
				data_animalTable.set(index, rowData);
				index++;
			}
		}
		updateState();
	}

	/**
	 * 全不选
	 * 
	 * @param event
	 */
	@FXML
	private void onSelectNoneButton(ActionEvent event) {
		if (data_animalTable.size() > 0) {
			int index = 0;
			for (TblAnimalRecListForTableView obj : data_animalTable) {
				TblAnimalRecListForTableView rowData = new TblAnimalRecListForTableView(
						obj.getId(), Integer.parseInt(obj.getSn()), obj.getRecId(),
						obj.getAnimalType(), obj.getAnimalStrain(), obj.getAnimalId(), (obj
								.getGender().equals("♂") ? 1 : 2), obj.getRoom(),
						obj.getPlanStudyNo(), obj.getState(), obj.getAnimalLevel());
				rowData.setSelect(false);
				data_animalTable.set(index, rowData);
				index++;
			}
		}
		updateState();
	}

	/**
	 * 反选
	 * 
	 * @param event
	 */
	@FXML
	private void onSelectInvertButton(ActionEvent event) {
		if (data_animalTable.size() > 0) {
			int index = 0;
			for (TblAnimalRecListForTableView obj : data_animalTable) {
				TblAnimalRecListForTableView rowData = new TblAnimalRecListForTableView(
						obj.getId(), Integer.parseInt(obj.getSn()), obj.getRecId(),
						obj.getAnimalType(), obj.getAnimalStrain(), obj.getAnimalId(), (obj
								.getGender().equals("♂") ? 1 : 2), obj.getRoom(),
						obj.getPlanStudyNo(), obj.getState(), obj.getAnimalLevel());
				rowData.setSelect(!obj.getSelect());
				data_animalTable.set(index, rowData);
				index++;
			}
		}
		updateState();
	}

	/**
	 * 更新animalTable状态栏的值
	 */
	private void updateState() {
		if (currentIndex == 6 || currentIndex == 7) {
			int total = data_animalTable.size();
			totalNumLabel.setText(total + "");
			int selectNum = 0;
			if (total > 0) {
				for (TblAnimalRecListForTableView obj : data_animalTable) {
					if (obj.getSelect()) {
						selectNum++;
					}
				}
			}
			selectNumLabel.setText(selectNum + "");
		}
	}

	/**
	 * 显示或隐藏 状态栏和selectCol列
	 */
	private void changeState() {
		// 全不选
		onSelectNoneButton(null);
		if (currentIndex == 6 || currentIndex == 7) {
			selectCol.setVisible(true);
			selectNumLabel.setText("0");
			selectNumLabel.setVisible(true);
			totalNumLabel.setVisible(true);
			label1.setVisible(true);
			label2.setVisible(true);
			label3.setVisible(true);
			selectAllButton.setDisable(false);
			selectNoneButton.setDisable(false);
			selectInvertButton.setDisable(false);
		} else {
			selectCol.setVisible(false);
			selectNumLabel.setText("0");
			selectNumLabel.setVisible(false);
			totalNumLabel.setVisible(false);
			label1.setVisible(false);
			label2.setVisible(false);
			label3.setVisible(false);
			selectAllButton.setDisable(true);
			selectNoneButton.setDisable(true);
			selectInvertButton.setDisable(true);
		}
	}

	/**
	 * 查询 button 事件
	 * 
	 * @param event
	 */
	@FXML
	private void onSearchButton(ActionEvent event) {
		// TODO 按钮
		if (startDateStr.compareTo(endDateStr) > 0) {
			Alert2.create("请前项日期不能晚于后项日期");
			return;
		}

		updateTblSessionTable();
	}

	/**
	 * 更新 tblSessionTable 会话表格数据
	 */
	public void updateTblSessionTable() {
		data_tblSessionTable.clear();
		List<TblSession> tblSessionList = BaseService.getTblSessionService()
				.findByDateAnimalStudyNo(startDateStr, endDateStr, sessionTypes[currentIndex],
						animalTypeStr, studyNoStr);
		if (null != tblSessionList && tblSessionList.size() > 0) {
			List<String> esIdList = new ArrayList<String>();
			String signId = "";
			String checkId = "";
			for (TblSession obj : tblSessionList) {
				signId = obj.getSignId();
				if (null != signId && !"".equals(signId)) {
					esIdList.add(signId);
					checkId = obj.getCheckId();
					if (null != checkId && !"".equals(checkId)) {
						esIdList.add(checkId);
					}
				}
			}
			String[] ids = new String[esIdList.size()];
			esIdList.toArray(ids);
			List<TblES> tblESList = BaseService.getTblESService().getByIds(ids);
			Map<String, String> esIdSignerMap = new HashMap<String, String>();
			if (null != tblESList && tblESList.size() > 0) {
				for (TblES tblES : tblESList) {
					esIdSignerMap.put(tblES.getEsId(), tblES.getSigner());
				}
			}
			String signer = "";
			String checker = "";
			for (TblSession obj : tblSessionList) {
				signer = obj.getSignId();
				if (null != signer && !"".equals(signer)) {
					signer = esIdSignerMap.get(signer);
					checker = obj.getCheckId();
					if (null != checker && !"".equals(checker)) {
						checker = esIdSignerMap.get(checker);
					} else {
						checker = "";
					}
				} else {
					signer = "";
					checker = "";
				}
				data_tblSessionTable.add(new TblSessionForTableView(obj.getSessionId(), obj
						.getCreater(), obj.getCreateTime(), obj.getSessionType(), obj.getRecId(),
						obj.getStudyNo(), obj.getBeginTime(), obj.getEndTime(),
						obj.getAnimalType(), signer, checker));
			}
		}

	}

	/**
	 * 更新studyNoComboBox_2的值
	 */
	public void updateStudyNoComboBox_2() {
		String studyNo = studyNoStr;
		data_studyNoComboBox_2.clear();
		data_studyNoComboBox_2.add("");
		List<String> list = BaseService.getTblSessionService().findStudyNoList(
				sessionTypes[currentIndex]);
		if (null != list && list.size() > 0) {
			for (String str : list) {
				data_studyNoComboBox_2.add(str);
			}
		}
		if (data_studyNoComboBox_2.contains(studyNo)) {
			studyNoComboBox_2.getSelectionModel().select(studyNo);
		}
	}

	/**
	 * 新建 button 事件
	 * 
	 * @param event
	 */
	@FXML
	private void onNewbuiltButton(ActionEvent event) {
		int totalNum = 0;
		if ((currentIndex > 0 && currentIndex < 6) || (currentIndex > 7 && currentIndex < 10)) {
			if (data_animalTable.size() < 1) {
				Alert2.create("请先选择动物");
				return;
			}
			totalNum = data_animalTable.size();

		} else if (currentIndex == 6 || currentIndex == 7) {
			if (data_animalTable.size() > 0) {
				for (TblAnimalRecListForTableView obj : data_animalTable) {
					if (obj.getSelect()) {
						totalNum++;
					}
				}
				if (totalNum == 0) {
					Alert2.create("请先选择动物");
					return;
				}
			} else {
				Alert2.create("请先选择动物");
				return;
			}
		} else {
			return;
		}
		String animalType = animalTypeComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: animalTypeComboBox.getSelectionModel().getSelectedItem();
		String creater = SaveUserInfo.getUser() == null ? "" : SaveUserInfo.getUser().getRealName();
		String createTime = DateUtil.getNow("yyyy-MM-dd HH:mm:ss");
		String sessionType = sessionTypes[currentIndex];
		String recId = recIdComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: recIdComboBox.getSelectionModel().getSelectedItem();
		String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: studyNoComboBox.getSelectionModel().getSelectedItem();

		try {
			SessionFrame.getInstance().start(stage);
			SessionFrame.getInstance().loadData(creater, createTime, sessionType, animalType,
					recId, studyNo, totalNum + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建会话及动物列表（暂不保存到数据库），打开新窗口
	 */
	public void createSessionAndopenNewWindow() {
		// TODO
		// 1.创建会话及动物列表
		TblSession tblSession = new TblSession();
		String animalType = animalTypeComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: animalTypeComboBox.getSelectionModel().getSelectedItem();
		String creater = SaveUserInfo.getUser().getRealName();
		String sessionType = sessionTypes[currentIndex];
		String recId = recIdComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: recIdComboBox.getSelectionModel().getSelectedItem();
		String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem() == null ? ""
				: studyNoComboBox.getSelectionModel().getSelectedItem();

		tblSession.setCreater(creater);
		tblSession.setCreateTime(new Date());
		tblSession.setRecId(recId);
		tblSession.setStudyNo(studyNo);
		tblSession.setSessionType(sessionType);
		tblSession.setAnimalType(animalType);

		String animalStrain = "";
		String animalLevel = "";

		TblSessionAnimals tblSessionAnimals = null;
		List<TblSessionAnimals> TblSessionAnimalsList = new ArrayList<TblSessionAnimals>();
		if ((currentIndex > 0 && currentIndex < 6) || (currentIndex > 7 && currentIndex < 10)) {
			for (TblAnimalRecListForTableView obj : data_animalTable) {
				if ("".equals(animalStrain)) {
					animalStrain = obj.getAnimalStrain();
					animalLevel = obj.getAnimalLevel();
				}
				tblSessionAnimals = new TblSessionAnimals();
				tblSessionAnimals.setAnimalId(obj.getAnimalId());
				tblSessionAnimals.setGender(obj.getGender().equals("♂") ? 1 : 2);
				tblSessionAnimals.setRoom(obj.getRoom());
				TblSessionAnimalsList.add(tblSessionAnimals);
			}

		} else if (currentIndex == 6 || currentIndex == 7) {
			for (TblAnimalRecListForTableView obj : data_animalTable) {
				if (obj.getSelect()) {
					if ("".equals(animalStrain)) {
						animalStrain = obj.getAnimalStrain();
						animalLevel = obj.getAnimalLevel();
					}
					tblSessionAnimals = new TblSessionAnimals();
					tblSessionAnimals.setAnimalId(obj.getAnimalId());
					tblSessionAnimals.setGender(obj.getGender().equals("♂") ? 1 : 2);
					tblSessionAnimals.setRoom(obj.getRoom());
					TblSessionAnimalsList.add(tblSessionAnimals);
				}
			}

		}
		tblSession.setAnimalLevel(animalLevel);
		tblSession.setAnimalStrain(animalStrain);

		// 保存会话（还未分配主键），及动物列表 并返回sessionId
		// String sessionId=BaseService.getTblSessionService()
		// .saveTblSessionAndAnimalList(tblSession,TblSessionAnimalsList);
		// 2.打开窗口
		// openWindow(sessionId);
		openNewWindow(tblSession, TblSessionAnimalsList);
		// 3.更新tblSessionTable 会话表格数据
		// updateTblSessionTable();
		// 4.选中指定行
		// vistedRow(sessionId);

	}

	/**
	 * 选中指定行
	 */
	private void vistedRow(String sessionId) {
		if (data_tblSessionTable.size() > 0) {
			for (TblSessionForTableView rowData : data_tblSessionTable) {
				if (sessionId != null && sessionId.equals(rowData.getSessionId())) {
					tblSessionTable.getSelectionModel().select(rowData);
					break;
				}
			}
		}
	}

	/**
	 * 打开指定窗口，根据当前索引号和会话Id
	 */
	private void openWindow(String sessionId) {
		if (null == sessionId || "".equals(sessionId)) {
			return;
		}
		switch (currentIndex) {
		case 1:// 体重称重
			BodyWeightFrame bodyWeightFrame = new BodyWeightFrame();
			try {
				bodyWeightFrame.start(stage);
				BodyWeightFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:// 一般观察
			try {
				GeneralObservationFrame.getInstance().start(stage);
				GeneralObservationFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:// 动物体检
			try {
				PhyExamFrame.getInstance().start(stage);
				PhyExamFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
			break;
		case 4:
			Alert2.create("未实现");
			break;
		case 5:
			Alert2.create("未实现");
			break;
		case 6:
			Alert2.create("未实现");
			break;
		case 7:// 动物死亡
			try {
				AnimalDeathFrame.getInstance().start(stage);
				AnimalDeathFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 8:// 重新安置
			try {
				AniResiteFrame.getInstance().start(stage);
				AniResiteFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 9://动物移交
			try {
				AniHandoverFrame.getInstance().start(stage);
				AniHandoverFrame.getInstance().loadData(sessionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 打开指定窗口，根据当前索引号和会话及动物列表
	 */
	private void openNewWindow(TblSession tblSession, List<TblSessionAnimals> tblSessionAnimalsList) {
		if (null == tblSession) {
			return;
		}
		if (null == tblSessionAnimalsList || tblSessionAnimalsList.size() < 1) {
			return;
		}
		switch (currentIndex) {
		case 1:// 体重称重
			BodyWeightFrame bodyWeightFrame = new BodyWeightFrame();
			try {
				bodyWeightFrame.start(stage);
				BodyWeightFrame.getInstance().loadDataByTblSession(tblSession,
						tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:// 一般观察
			try {
				GeneralObservationFrame.getInstance().start(stage);
				GeneralObservationFrame.getInstance().loadDataByTblSession(tblSession,
						tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:// 动物体检
			try {
				PhyExamFrame.getInstance().start(stage);
				PhyExamFrame.getInstance().loadDataByTblSession(tblSession, tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			;
			break;
		case 4:
			Alert2.create("未实现");
			break;
		case 5:
			Alert2.create("未实现");
			break;
		case 6:
			Alert2.create("未实现");
			break;
		case 7:// 动物死亡
			try {
				AnimalDeathFrame.getInstance().start(stage);
				AnimalDeathFrame.getInstance().loadDataByTblSession(tblSession,
						tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 8:// 重新安置
			try {
				AniResiteFrame.getInstance().start(stage);
				AniResiteFrame.getInstance()
						.loadDataByTblSession(tblSession, tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 9://动物移交
			try {
				AniHandoverFrame.getInstance().start(stage);
				AniHandoverFrame.getInstance()
						.loadDataByTblSession(tblSession, tblSessionAnimalsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 打开
	 */
	@FXML
	private void onOpenButton() {
		TblSessionForTableView selectItem = tblSessionTable.getSelectionModel().getSelectedItem();
		if (null != selectItem) {
			openWindow(selectItem.getSessionId());
		} else {
			Alert2.create("请先选择数据");
		}
	}

	/**
	 * 签字 按钮
	 */
	@FXML
	private void onSignButton() {
		TblSessionForTableView selectItem = tblSessionTable.getSelectionModel().getSelectedItem();
		if (null != selectItem) {
			sign(selectItem);
		} else {
			Alert2.create("请先选择数据");
		}

	}

	/**
	 * 签字，根据当前索引号和会话Id
	 * 
	 * @param sessionId
	 */
	private void sign(TblSessionForTableView selectItem) {
		String sessionId = selectItem.getSessionId();
		if (null == sessionId || "".equals(sessionId)) {
			Alert2.create("错误");
			return;
		}
		// 1.选中数据是否已签字
		String signer = selectItem.getSigner();
		if (null != signer && !signer.isEmpty()) {
			Alert2.create("该数据已签字，不可重复签字");
			return;
		}
		// 2.对应任务是否完成
		boolean isFinish = false;
		switch (currentIndex) {
		case 1:
			// 称重是否完成
			isFinish = BaseService.getTblSessionService().isFinishAboutBodyWeight(sessionId);
			if (!isFinish) {
				Alert2.create("动物称重未录入完毕，不可签字");
				return;
			}
			break;
		case 2:
			// 是否存在 未观察动物
			isFinish = BaseService.getTblGeneralObservationService()
					.isExistNoObservation(sessionId);
			if (isFinish) {
				Alert2.create("存在未记录观察结果的动物");
				return;
			}
			break;
		case 3:// 是否存在 未体检动物
			boolean isExist = BaseService.getTblPhyExamService().isExistNoExam(sessionId);
			if (isExist) {
				Alert2.create("动物体检还未完成，不可签字");
				return;
			}
			break;
		case 4:
			Alert2.create("未实现");
			break;
		case 5:
			Alert2.create("未实现");
			break;
		case 6:
			Alert2.create("未实现");
			break;
		case 7:
			Alert2.create("未实现");
			break;
		case 8:
			Alert2.create("未实现");
			break;
		case 9:
			Alert2.create("未实现");
			break;

		default:
			break;
		}
		// 3.电子签名
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("签字");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("OK".equals(SignFrame.getType())) {
			// 5,保存签字 更新tblSession 的signId ，endTime 并返回signId
			String realName = SignFrame.getUser().getRealName();
			switch (currentIndex) {
			case 1:
				BaseService.getTblSessionService().sign(sessionId, 10, realName, "检疫，体重称重签字");
				break;
			case 2:
				BaseService.getTblSessionService().sign(sessionId, 12, realName, "检疫，一般观察签字");
				break;
			case 3:
				BaseService.getTblSessionService().sign(sessionId, 14,
						SignFrame.getUser().getRealName(), "检疫，动物体检签字");
				break;
			case 4:
				Alert2.create("未实现");
				break;
			case 5:
				Alert2.create("未实现");
				break;
			case 6:
				Alert2.create("未实现");
				break;
			case 7:
				Alert2.create("未实现");
				break;
			case 8:
				Alert2.create("未实现");
				break;
			case 9:
				Alert2.create("未实现");
				break;

			default:
				break;
			}
			// 6.更新表格数据，
			updateTblSessionTable();
			// 选中对应行
			vistedRow(sessionId);
			Alert.create("签字成功");
		}

	}

	/**
	 * 复核按钮事件
	 */
	@FXML
	private void onCheckButton() {
		TblSessionForTableView selectItem = tblSessionTable.getSelectionModel().getSelectedItem();
		if (null != selectItem) {
			check(selectItem);
		} else {
			Alert2.create("请先选择数据");
		}

	}

	/**
	 * 复核，根据sessionId和当前索引号（currentIndex）
	 * 
	 * @param selectItem
	 */
	private void check(TblSessionForTableView selectItem) {
		String sessionId = selectItem.getSessionId();
		if (null == sessionId || "".equals(sessionId)) {
			Alert2.create("错误");
			return;
		}
		// 1.选中数据是否有签字
		String signer = selectItem.getSigner();
		String checker = selectItem.getChecker();
		if (null == signer || signer.isEmpty()) {
			Alert2.create("该数据未签字，请先签字");
			return;
		}
		if (null != checker && !checker.isEmpty()) {
			Alert2.create("该数据已复核，不可重复复核");
			return;
		}
		// 2.对应任务是否完成
		boolean isFinish = false;
		switch (currentIndex) {
		case 1:
			// 称重是否完成
			isFinish = BaseService.getTblSessionService().isFinishAboutBodyWeight(sessionId);
			if (!isFinish) {
				Alert2.create("动物称重未录入完毕，不可复核");
				return;
			}
			break;
		case 2:// 是否存在 未观察动物
			isFinish = BaseService.getTblGeneralObservationService()
					.isExistNoObservation(sessionId);
			if (isFinish) {
				Alert2.create("存在未记录观察结果的动物");
				return;
			}
			break;
		case 3:
			// 是否存在 未体检动物
			boolean isExist = BaseService.getTblPhyExamService().isExistNoExam(sessionId);
			;
			if (isExist) {
				Alert2.create("动物体检还未完成，不可复核");
				return;
			}
			break;
		case 4:
			Alert2.create("未实现");
			break;
		case 5:
			Alert2.create("未实现");
			break;
		case 6:
			Alert2.create("未实现");
			break;
		case 7:// 动物死亡
			;
			break;
		case 8:
			Alert2.create("未实现");
			break;
		case 9:
			Alert2.create("未实现");
			break;

		default:
			break;
		}
		// 3.电子签名
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("复核");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("OK".equals(SignFrame.getType())) {
			// 5,保存签字 更新tblSession 的checkId ，并返回checkId
			String realName = SignFrame.getUser().getRealName();
			switch (currentIndex) {
			case 1:
				BaseService.getTblSessionService().check(sessionId, 11, realName, "检疫，体重称重复核");
				break;
			case 2:
				BaseService.getTblSessionService().check(sessionId, 13, realName, "检疫，一般观察复核");
				break;
			case 3:
				BaseService.getTblSessionService().check(sessionId, 15,
						SignFrame.getUser().getRealName(), "检疫，动物体检复核");
				break;
			case 4:
				Alert2.create("未实现");
				break;
			case 5:
				Alert2.create("未实现");
				break;
			case 6:
				Alert2.create("未实现");
				break;
			case 7:
				BaseService.getTblSessionService().check(sessionId, 17,
						SignFrame.getUser().getRealName(), "检疫，动物复核复核");
				break;
			case 8:
				Alert2.create("未实现");
				break;
			case 9:
				Alert2.create("未实现");
				break;

			default:
				break;
			}
			// 6.更新表格数据，
			updateTblSessionTable();
			// 选中对应行
			vistedRow(sessionId);
			Alert.create("复核成功");
		}

	}

	/**
	 * 打开指定窗口，根据当前索引号和会话Id
	 */
	private void print(String sessionId) {
		if (null == sessionId || "".equals(sessionId)) {
			return;
		}
		switch (currentIndex) {
		case 1:
			printBodyWeightReport(sessionId);
			break;
		case 2:
			printGeneralObservationReport(sessionId);
			;
			break;
		case 3:
			printPhyExamReport(sessionId);
			break;
		case 4:
			Alert2.create("未实现");
			break;
		case 5:
			Alert2.create("未实现");
			break;
		case 6:
			Alert2.create("未实现");
			break;
		case 7:
			printAnimalDeathReport(sessionId);
			break;
		case 8:
			Alert2.create("未实现");
			break;
		case 9:
			Alert2.create("未实现");
			break;

		default:
			break;
		}

	}

	/**
	 * 打印_动物异常死亡记录表
	 * 
	 * @param sessionId
	 */
	private void printAnimalDeathReport(String sessionId) {
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
					"tblAnimalDeath.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return;
		}

		InputStream logoImage = this.getClass().getResourceAsStream("/image/logo_xishan.jpg");

		TblAnimalDeath tblAnimalDeath = BaseService.getTblAnimalDeathService().getById(sessionId);
		if (tblAnimalDeath != null) {
			/** 编号 */
			String number = BaseService.getDictOutputSettingsService().getShowByLabel(
					"动物异常死亡记录表-编号");
			TblSession tblSession = BaseService.getTblSessionService().getById(sessionId);
			String animalStrain = tblSession.getAnimalStrain();//
			String foundTime = DateUtil.dateToString(tblAnimalDeath.getFoundTime(),
					"yyyy-MM-dd HH:mm");
			String notifyTime = DateUtil.dateToString(tblAnimalDeath.getNotifyTime(),
					"yyyy-MM-dd HH:mm");
			String dealwithOpinion = tblAnimalDeath.getDealwithOpinion();
			String remark = tblAnimalDeath.getRemark();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("logoImage", logoImage);
			map.put("number", number);
			map.put("animalStrain", animalStrain);
			map.put("foundTime", foundTime);
			map.put("notifyTime", notifyTime);
			map.put("dealwithOpinion", dealwithOpinion);
			map.put("remark", remark);

			// 数据集List<?> 动物Id号，课题编号，房间号
			List<?> list = BaseService.getTblAnimalDeathService().getListBySessionId(sessionId);
			if (null == list || list.size() < 1) {
				Alert2.create("死亡登记动物已撤销，无打印数据");
				return;
			}
			List<Map<String, String>> mapList = null;
			if (null != list && list.size() > 0) {
				mapList = new ArrayList<Map<String, String>>();
				Map<String, String> currentmap = null;
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					String animalId = (String) obj[0];
					String studyNo = (String) obj[1];
					String room = (String) obj[2];
					currentmap = new HashMap<String, String>();
					currentmap.put("animalId", animalId);
					currentmap.put("studyNo", studyNo);
					currentmap.put("room", room);
					mapList.add(currentmap);

				}
			}
			try {
				jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(mapList));
			} catch (JRException e) {
				e.printStackTrace();
				Alert2.create("报表加载失败");
				return;
			}
			Main.getInstance().openJFrame(jp, "动物一般观察记录表");
		}
	}

	/**
	 * 打印_动物体检记录表
	 * 
	 * @param sessionId
	 */
	private void printPhyExamReport(String sessionId) {

		JasperReport jr = null;
		JasperPrint jp = null;

		InputStream logoImage = this.getClass().getResourceAsStream("/image/logo_xishan.jpg");

		TblSession tblSession = BaseService.getTblSessionService().getById(sessionId);
		if (tblSession != null) {
			String animalType = tblSession.getAnimalType();
			boolean isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(animalType);
			// 小动物
			if (!isBigAnimal) {
				/** 编号 */
				String number = BaseService.getDictOutputSettingsService().getShowByLabel(
						"小动物体检记录表-编号");
				String animalStrain = tblSession.getAnimalStrain();//

				String producer = "";
				String recDate = "";
				String room = "";

				String signerAndDate = "";
				String checkerAndDate = "";
				String signId = tblSession.getSignId();
				String checkId = tblSession.getCheckId();
				TblES tblES_signer = BaseService.getTblESService().getById(signId);
				TblES tblES_checker = BaseService.getTblESService().getById(checkId);
				if (null != tblES_signer) {
					signerAndDate = tblES_signer.getSigner() + " "
							+ DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
				}
				if (null != tblES_checker) {
					checkerAndDate = tblES_checker.getSigner() + " "
							+ DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("logoImage", logoImage);
				map.put("number", number);
				map.put("animalStrain", animalStrain);
				map.put("signerAndDate", signerAndDate);
				map.put("checkerAndDate", checkerAndDate);
				List<TblPhyExam> tblPhyExamList = BaseService.getTblPhyExamService()
						.getEntityListBySessionId(sessionId);
				if (tblPhyExamList != null && tblPhyExamList.size() > 0) {
					String recId = tblSession.getRecId();
					if (null == recId || recId.equals("")) {
						String animalId = tblPhyExamList.get(0).getAnimalId();
						recId = BaseService.getTblAnimalRecListService().getRecIdByAnimalId(
								animalId);
					}
					if (null != recId && !recId.isEmpty()) {
						TblAnimalRecIndex tblAnimalRecIndex = BaseService
								.getTblAnimalRecIndexService().getById(recId);
						producer = tblAnimalRecIndex.getProducer();
						recDate = DateUtil.dateToString(tblAnimalRecIndex.getRecDate(),
								"yyyy-MM-dd");
						room = tblAnimalRecIndex.getRoom();
						map.put("producer", producer);
						map.put("recDate", recDate);
						map.put("room", room);
					}
					int size = tblPhyExamList.size();
					int count = size % 54;
					int max = 0;// 增加空行数量
					if (count != 0) {
						max = 54 - count;
					}
					for (int i = 0; i < max; i++) {
						tblPhyExamList.add(new TblPhyExam());
					}
					try {
						jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
								"tblPhyExam_s.jrxml"));
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								tblPhyExamList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "小动物体检记录表");
				}
			} else {
				/** 编号 */
				String number = BaseService.getDictOutputSettingsService().getShowByLabel(
						"大动物体检记录表-编号");
				String animalStrain = tblSession.getAnimalStrain();//

				String producer = "";
				String recDate = "";

				String signerAndDate = "";
				String checkerAndDate = "";
				String signId = tblSession.getSignId();
				String checkId = tblSession.getCheckId();
				TblES tblES_signer = BaseService.getTblESService().getById(signId);
				TblES tblES_checker = BaseService.getTblESService().getById(checkId);
				if (null != tblES_signer) {
					signerAndDate = tblES_signer.getSigner() + " "
							+ DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
				}
				if (null != tblES_checker) {
					checkerAndDate = tblES_checker.getSigner() + " "
							+ DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("logoImage", logoImage);
				map.put("number", number);
				map.put("animalStrain", animalStrain);
				map.put("signerAndDate", signerAndDate);
				map.put("checkerAndDate", checkerAndDate);
				List<?> list = BaseService.getTblPhyExamService().getListBySessionId(sessionId);
				if (list != null && list.size() > 0) {
					String recId = tblSession.getRecId();
					if (null == recId || recId.equals("")) {
						String animalId = (String) ((Object[]) list.get(0))[0];
						recId = BaseService.getTblAnimalRecListService().getRecIdByAnimalId(
								animalId);
					}
					if (null != recId && !recId.isEmpty()) {
						TblAnimalRecIndex tblAnimalRecIndex = BaseService
								.getTblAnimalRecIndexService().getById(recId);
						producer = tblAnimalRecIndex.getProducer();
						recDate = DateUtil.dateToString(tblAnimalRecIndex.getRecDate(),
								"yyyy-MM-dd");
						map.put("producer", producer);
						map.put("recDate", recDate);
					}
					// 数据集
					List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
					Map<String, Object> dataMap = null;
					for (int i = 0; i < list.size(); i++) {
						dataMap = new HashMap<String, Object>();

						Object[] obj = (Object[]) list.get(i);
						String animalId = (String) obj[0];
						Integer gender = (Integer) obj[1];
						String bodyWeight = (String) obj[2];
						String temperature = (String) obj[4];
						String room = (String) obj[8];
						dataMap.put("animalId", animalId);
						dataMap.put("gender", gender);
						dataMap.put("bodyWeight", bodyWeight);
						dataMap.put("temperature", temperature);
						dataMap.put("room", room);
						List<TblPhyExamResult> tblPhyExamResultList = BaseService
								.getTblPhyExamService().getResultListBySessionIdAnimalId(sessionId,
										animalId);
						if (null != tblPhyExamResultList && tblPhyExamResultList.size() > 0) {
							int index = 1;
							for (TblPhyExamResult result : tblPhyExamResultList) {
								dataMap.put("item" + index, result.getExamResult());
								index++;
							}
						}
						mapList.add(dataMap);
					}
					try {
						jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
								"tblPhyExam_l.jrxml"));
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								mapList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "大动物体检记录表");
				}

			}

		}

	}

	/**
	 * 打印_动物体重称量记录表
	 * 
	 * @param sessionId
	 */
	private void printBodyWeightReport(String sessionId) {

		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
					"tblBodyWeight.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return;
		}

		InputStream logoImage = this.getClass().getResourceAsStream("/image/logo_xishan.jpg");

		TblSession tblSession = BaseService.getTblSessionService().getById(sessionId);
		if (tblSession != null) {
			/** 编号 */
			String number = BaseService.getDictOutputSettingsService().getShowByLabel(
					"动物体重称量记录表-编号");
			String endDate = DateUtil.dateToString(tblSession.getEndTime(), "yyyy-MM-dd");//
			String studyNo = tblSession.getStudyNo();
			String animalStrain = tblSession.getAnimalStrain();//
			String signerAndDate = "";
			String checkerAndDate = "";
			String signId = tblSession.getSignId();
			String checkId = tblSession.getCheckId();
			TblES tblES_signer = BaseService.getTblESService().getById(signId);
			TblES tblES_checker = BaseService.getTblESService().getById(checkId);
			if (null != tblES_signer) {
				signerAndDate = tblES_signer.getSigner() + " "
						+ DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
			}
			if (null != tblES_checker) {
				checkerAndDate = tblES_checker.getSigner() + " "
						+ DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
			}
			String balId = "";

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("logoImage", logoImage);
			map.put("number", number);
			map.put("endDate", endDate);
			map.put("animalStrain", animalStrain);
			map.put("signerAndDate", signerAndDate);
			map.put("checkerAndDate", checkerAndDate);
			if (studyNo != null && !studyNo.isEmpty()) {
				map.put("studyNo", studyNo);
				List<TblBodyWeight> tblBodyWeightList = BaseService.getTblBodyWeightService()
						.getEntityListBySessionId(sessionId);
				if (tblBodyWeightList != null && tblBodyWeightList.size() > 0) {
					balId = tblBodyWeightList.get(0).getDevId();
					map.put("balId", balId);
					int size = tblBodyWeightList.size();
					int count = size % 124;
					int max = 0;// 增加空行数量
					if (count != 0) {
						max = 124 - count;
					}
					for (int i = 0; i < max; i++) {
						tblBodyWeightList.add(new TblBodyWeight());
					}
					try {
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								tblBodyWeightList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "动物体重称量记录表");
				}
			} else {
				String recId = tblSession.getRecId();
				List<String> studyNoList = BaseService.getTblBodyWeightService()
						.findStudyNoListByRecIdSessionId(recId, sessionId);
				int listSize = 0;
				if (null != studyNoList && studyNoList.size() > 1) {
					// 选择课题编号
					ChooseStudyNoFrame chooseStudyNoFrame = new ChooseStudyNoFrame();
					try {

						chooseStudyNoFrame.updateData(studyNoList);
						chooseStudyNoFrame.start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (null != studyNoList && studyNoList.size() == 1) {
					listSize = 1;
					studyNo = studyNoList.get(0);
				} else {

					return;
				}
				if ("OK".equals(ChooseStudyNoFrame.getType()) || listSize == 1) {
					if (listSize != 1) {
						studyNo = ChooseStudyNoFrame.getStudyNo();
					}
					map.put("studyNo", studyNo);
					List<?> list = BaseService.getTblBodyWeightService()
							.findListByRecIdSessionIdStudyNo(recId, sessionId, studyNo);
					List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
					// List<TblBodyWeight> tblBodyWeightList = new
					// ArrayList<TblBodyWeight>();
					// TblBodyWeight tblBodyWeight = null;
					Map<String, String> valueMap = null;
					if (null != list && list.size() > 0) {
						int count = list.size();
						for (int i = 0; i < count; i++) {
							Object[] obj = (Object[]) list.get(i);
							valueMap = new HashMap<String, String>();
							valueMap.put("animalId", (String) obj[0]);
							valueMap.put("bodyWeight", (String) obj[1]);
							valueMap.put("weightUnit", (String) obj[2]);
							mapList.add(valueMap);
							if (i == 0) {
								balId = (String) obj[3];
							}
						}
						map.put("balId", balId);
					}
					int size = mapList.size();
					int count = size % 124;
					int max = 0;// 增加空行数量
					if (count != 0) {
						max = 124 - count;
					}
					for (int i = 0; i < max; i++) {
						mapList.add(new HashMap<String, String>());
					}
					try {
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								mapList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "动物体重称量记录表");
				}
			}

		}

	}

	/**
	 * 打印_动物一般观察记录表
	 * 
	 * @param sessionId
	 */
	private void printGeneralObservationReport(String sessionId) {

		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
					"tblGeneralObservation.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return;
		}

		InputStream logoImage = this.getClass().getResourceAsStream("/image/logo_xishan.jpg");

		TblSession tblSession = BaseService.getTblSessionService().getById(sessionId);
		if (tblSession != null) {
			/** 编号 */
			String number = BaseService.getDictOutputSettingsService().getShowByLabel(
					"动物一般观察记录表-编号");
			String endDate = DateUtil.dateToString(tblSession.getEndTime(), "yyyy-MM-dd");//
			String studyNo = tblSession.getStudyNo();
			String animalStrain = tblSession.getAnimalStrain();//
			String signerAndDate = "";
			String checkerAndDate = "";
			String signId = tblSession.getSignId();
			String checkId = tblSession.getCheckId();
			TblES tblES_signer = BaseService.getTblESService().getById(signId);
			TblES tblES_checker = BaseService.getTblESService().getById(checkId);
			if (null != tblES_signer) {
				signerAndDate = tblES_signer.getSigner() + " "
						+ DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
			}
			if (null != tblES_checker) {
				checkerAndDate = tblES_checker.getSigner() + " "
						+ DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("logoImage", logoImage);
			map.put("number", number);
			map.put("endDate", endDate);
			map.put("animalStrain", animalStrain);
			map.put("signerAndDate", signerAndDate);
			map.put("checkerAndDate", checkerAndDate);
			if (studyNo != null && !studyNo.isEmpty()) {
				map.put("studyNo", studyNo);
				List<TblGeneralObservation> tblGeneralObservationList = BaseService
						.getTblGeneralObservationService().getListBySessionId(sessionId);
				List<Map<String, String>> mapList = getMapList(tblGeneralObservationList);
				if (mapList != null && mapList.size() > 0) {

					int size = mapList.size();
					int count = size % 62;
					int max = 0;// 增加空行数量
					if (count != 0) {
						max = 62 - count;
					}
					for (int i = 0; i < max; i++) {
						mapList.add(new HashMap<String, String>());
					}
					try {
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								mapList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "动物一般观察记录表");

				}
			} else {
				String recId = tblSession.getRecId();
				List<String> studyNoList = BaseService.getTblGeneralObservationService()
						.findStudyNoListByRecIdSessionId(recId, sessionId);
				int listSize = 0;
				if (null != studyNoList && studyNoList.size() > 1) {
					// 选择课题编号
					ChooseStudyNoFrame chooseStudyNoFrame = new ChooseStudyNoFrame();
					try {

						chooseStudyNoFrame.updateData(studyNoList);
						chooseStudyNoFrame.start(new Stage());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (null != studyNoList && studyNoList.size() == 1) {
					listSize = 1;
					studyNo = studyNoList.get(0);
				} else {

					return;
				}
				if ("OK".equals(ChooseStudyNoFrame.getType()) || listSize == 1) {
					if (listSize != 1) {
						studyNo = ChooseStudyNoFrame.getStudyNo();
					}
					map.put("studyNo", studyNo);
					List<?> list = BaseService.getTblGeneralObservationService()
							.findListByRecIdSessionIdStudyNo(recId, sessionId, studyNo);

					List<Map<String, String>> mapList = getMapListByList(list);
					int size = mapList.size();
					int count = size % 62;
					int max = 0;// 增加空行数量
					if (count != 0) {
						max = 62 - count;
					}
					for (int i = 0; i < max; i++) {
						mapList.add(new HashMap<String, String>());
					}
					try {
						jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
								mapList));
					} catch (JRException e) {
						e.printStackTrace();
						Alert2.create("报表加载失败");
						return;
					}
					Main.getInstance().openJFrame(jp, "动物一般观察记录表");
				}
			}

		}

	}

	/**
	 * 根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * 
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String, String>> getMapList(List<TblGeneralObservation> tblGeneralObservationList) {
		if (null != tblGeneralObservationList && tblGeneralObservationList.size() > 0) {
			List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
			Map<String, String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();
			for (TblGeneralObservation obj : tblGeneralObservationList) {
				String animalId = obj.getAnimalId();
				String observation = obj.getObservation();
				if (!animalIdList.contains(animalId)) {
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				} else {
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2 + "、" + observation);
				}
			}
			Map<String, String> map = null;
			for (String animalId : animalIdList) {
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		}
		return null;
	}

	/**
	 * 根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * 
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String, String>> getMapListByList(List<?> list) {
		if (null != list && list.size() > 0) {
			List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
			Map<String, String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();

			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				String animalId = (String) obj[0];
				String observation = (String) obj[1];
				if (!animalIdList.contains(animalId)) {
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				} else {
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2 + "、" + observation);
				}
			}
			Map<String, String> map = null;
			for (String animalId : animalIdList) {
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		}
		return null;
	}

	/**
	 * 打印按钮
	 */
	@FXML
	private void onPrintButton() {
		TblSessionForTableView selectItem = tblSessionTable.getSelectionModel().getSelectedItem();
		if (null != selectItem) {
			String checker = selectItem.getChecker();
			if (null != checker && !checker.isEmpty()) {
				print(selectItem.getSessionId());
			} else {
				Alert2.create("未复核，复核后才可以打印");
			}
		} else {
			Alert2.create("请先选择数据");
		}

	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DayToDayPane.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("动物登记列表");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/** 加载数据 */
	public void loadData() {
		// TODO
		// 更新 tblSessionTable 会话表格数据
		updateTblSessionTable();
	}

	/**
	 * 更新动物表格数据
	 */
	public void updateAniamlTable() {
		String recId = recIdComboBox.getSelectionModel().getSelectedItem();
		String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		if (null != recId && !recId.isEmpty()) {
			updateAnimalTable(recId);
		} else if (null != studyNo && !studyNo.isEmpty()) {
			updateAnimalTableByStudyNo(studyNo);
		}
	}

	// 在单元格里创建多选框
	public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
		private final CheckBox checkBox;
		private ObservableValue<T> ov;

		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
			checkBox.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// CheckBox check = (CheckBox) event.getSource();
					// if(check)
					getTableView().getSelectionModel().select(getIndex());
				}

			});
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
				if (checkBox.isSelected()) {
					// getTableRow().getStylesheets().add("willPayRow");
					getTableRow().setStyle("-fx-background-color:#0092DC;");
				} else {
					getTableRow().setStyle("");
				}
			}
		}
	}
}
