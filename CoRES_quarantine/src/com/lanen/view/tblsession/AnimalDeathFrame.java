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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.DictAnimalDeath;
import com.lanen.model.quarantine.tblsession.TblAnimalDeath;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.TblSessionAnimalForTableView;
import com.lanen.model.tableview.TblTraceForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;
import com.lanen.view.tblsession.DayToDayController.CheckBoxTableCell;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AnimalDeathFrame extends Application implements Initializable {

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
	private HBox deadDateHBox;// 死亡日期
	@FXML
	private HBox foundDateHBox;// 发现日期
	@FXML
	private HBox notifyDateHBox;// 通知日期
	private DatePicker deadDatePane = null;
	private DatePicker foundDatePane = null;
	private DatePicker notifyDatePane = null;

	@FXML
	private ComboBox<String> foundTimeHComboBox;// 发现时间——时
	@FXML
	private ComboBox<String> foundTimeMComboBox;// 发现时间——分
	@FXML
	private ComboBox<String> notifyTimeHComboBox;// 通知时间——时
	@FXML
	private ComboBox<String> notifyTimeMComboBox;// 通知时间——分
	@FXML
	private ComboBox<String> deadTypeComboBox;// 死亡原因
	private ObservableList<String> data_deadTypeComboBox = FXCollections.observableArrayList();
	private Map<String,String> deadTypeDeadRsnMap = new HashMap<String, String>();
	@FXML
	private TextField deadRsnText;
	@FXML
	private TextArea dealwithOpinionText;// 动物死亡处理意见
	@FXML
	private TextArea remarkText;// 备注

	/**
	 * 动物列表
	 */
	@FXML
	private static TableView<TblSessionAnimalForTableView> animalTable;
	private static ObservableList<TblSessionAnimalForTableView> data_animalTable = FXCollections
			.observableArrayList();
	@FXML
	private TableColumn<TblSessionAnimalForTableView, Boolean> selectCol; // 选择框
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> animalIdCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> genderCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView, String> roomCol;

	/**
	 * 撤销 button
	 */
	@FXML
	private Button cancelButton;

	/**
	 * 修改痕迹表格
	 */
	@FXML
	private TableView<TblTraceForTableView> tblTraceTable;
	/**
	 * 修改痕迹表格数据集
	 */
	private static ObservableList<TblTraceForTableView> data_tblTraceTable = FXCollections
			.observableArrayList();
	@FXML
	private TableColumn<TblTraceForTableView, String> animalIdCol_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> operateModeCode_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> oldValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> newValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> operatorCol_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> modifyReasonCol_2;
	@FXML
	private TableColumn<TblTraceForTableView, String> modifyTimeCol_2;

	/**
	 * 复核后，禁止编辑
	 */
	@FXML
	private HBox hbox;

	private static TblSession tblSession = null;
	private static String sessionId = "";
	private static List<TblSessionAnimals> tblSessionAnimalsList = null;

	private static String signId = "";
	private static String checkId = "";
	private static Date beginDate = null; // 开始采集时间
	/** 离开页面时，是否需要更新DayToDayPane上tblSession表数据 */
	private static boolean updateFlag = false;// 离开页面时，是否需要更新DayToDayPane上tblSession表数据
	/** 离开页面时，是否需要更新DayToDayPane上animalTable表数据 */
	private static boolean updateAnimalListFlag = false;

	private static AnimalDeathFrame instance;

	public static AnimalDeathFrame getInstance() {
		if (null == instance) {
			instance = new AnimalDeathFrame();
		}
		updateFlag = false;
		updateAnimalListFlag = false;
		return instance;
	}

	public AnimalDeathFrame() {
		updateFlag = false;
		updateAnimalListFlag = false;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		// 初始化animalTable
		initAnimalTable();
		// 修改痕迹
		ininTblTraceTalbe();
		// 初始化日期选择器及时分
		initDate();
		// 死亡原因ComboBox 初始化
		initDeadTypeComboBox();
	}

	/**
	 * deadTypeComboBox初始化
	 */
	private void initDeadTypeComboBox() {
		data_deadTypeComboBox.clear();
		deadTypeComboBox.setItems(data_deadTypeComboBox);
		List<DictAnimalDeath> dictAnimalDeathList = BaseService.getDictAnimalDeathService().findAllOrderByOrderNo();
		if (null != dictAnimalDeathList && dictAnimalDeathList.size()>0) {
			
			 for(DictAnimalDeath dictAnimalDeath:dictAnimalDeathList){
				 data_deadTypeComboBox.add(dictAnimalDeath.getDeadType());
				 deadTypeDeadRsnMap.put(dictAnimalDeath.getDeadType(),
						 dictAnimalDeath.getDeadRsn());
			 }
		}
		deadTypeComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue && !newValue.isEmpty()){
					deadRsnText.setText(deadTypeDeadRsnMap.get(newValue));
				}else{
					deadRsnText.setText("");
				}
				
			}
			
		});

	}

	/**
	 * 初始化日期选择器及时分
	 */
	private void initDate() {
		deadDatePane = new DatePicker(Locale.CHINA);
		deadDatePane.getTextField().setEditable(false);
		deadDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		deadDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		deadDatePane.getCalendarView().setShowWeeks(false);
		deadDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		deadDatePane.setMaxWidth(110);

		deadDateHBox.getChildren().add(deadDatePane);
		deadDatePane.getTextField().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue,
					String newValue) {
				if (null != newValue && !"".equals(newValue)) {
					if ("".equals(foundDatePane.getTextField().getText().trim())) {
						foundDatePane.getTextField().setText(newValue);
					}
					if ("".equals(notifyDatePane.getTextField().getText().trim())) {
						notifyDatePane.getTextField().setText(newValue);
					}
				}
			}

		});
		foundDatePane = new DatePicker(Locale.CHINA);
		foundDatePane.getTextField().setEditable(false);
		foundDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		foundDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		foundDatePane.getCalendarView().setShowWeeks(false);
		foundDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		foundDatePane.setMaxWidth(110);

		foundDateHBox.getChildren().add(foundDatePane);

		notifyDatePane = new DatePicker(Locale.CHINA);
		notifyDatePane.getTextField().setEditable(false);
		notifyDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		notifyDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		notifyDatePane.getCalendarView().setShowWeeks(false);
		notifyDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		notifyDatePane.setMaxWidth(110);

		notifyDateHBox.getChildren().add(notifyDatePane);

		foundTimeHComboBox.getSelectionModel().select(0);
		foundTimeMComboBox.getSelectionModel().select(0);
		notifyTimeHComboBox.getSelectionModel().select(0);
		notifyTimeMComboBox.getSelectionModel().select(0);

	}

	/**
	 * 初始化animalTable
	 */
	private void initAnimalTable() {
		selectCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, Boolean>(
						"select"));
		animalIdCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
						"animalId"));
		genderCol
				.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
						"gender"));
		roomCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>(
				"room"));
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol
				.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView, Boolean>, TableCell<TblSessionAnimalForTableView, Boolean>>() {

					@Override
					public TableCell<TblSessionAnimalForTableView, Boolean> call(
							TableColumn<TblSessionAnimalForTableView, Boolean> p) {
						CheckBoxTableCell<TblSessionAnimalForTableView, Boolean> checkBoxTableCell = new CheckBoxTableCell<TblSessionAnimalForTableView, Boolean>();
						checkBoxTableCell.setDisable(true);
						return checkBoxTableCell;
					}
				});
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
		// animalTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
		//
		// @Override
		// public void handle(MouseEvent event) {
		// //单击
		// if(event.getButton()==MouseButton.PRIMARY){
		// TblSessionAnimalForTableView currentSelectItem =
		// animalTable.getSelectionModel().getSelectedItem();
		// if(null != currentSelectItem){
		// TblSessionAnimalForTableView index = new
		// TblSessionAnimalForTableView(
		// currentSelectItem.getAnimalId(),(currentSelectItem.getGender().equals("♂")
		// ? 1 : 2)
		// ,currentSelectItem.getRoom());
		// index.setSelect(!currentSelectItem.getSelect());
		// animalTable.getItems().set(animalTable.getItems().indexOf(currentSelectItem),
		// index);
		// animalTable.getSelectionModel().clearSelection();
		// }
		// }
		//
		// }});
	}

	/**
	 * 初始化tblTraceTalbe
	 */
	private void ininTblTraceTalbe() {
		animalIdCol_2.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
				"animalId"));
		operateModeCode_2
				.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
						"operateMode"));
		oldValueCol_2.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
				"oldValue"));
		newValueCol_2.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
				"newValue"));
		operatorCol_2.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
				"operator"));
		modifyReasonCol_2
				.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
						"modifyReason"));
		modifyTimeCol_2.setCellValueFactory(new PropertyValueFactory<TblTraceForTableView, String>(
				"modifyTime"));

		tblTraceTable.setItems(data_tblTraceTable);
	}

	/**
	 * 加载animalTable数据
	 */
	public void loadData_animalTable() {
		data_animalTable.clear();
		if (null != sessionId && !sessionId.isEmpty()) {
			// 会话动物列表
			List<TblSessionAnimals> animalList = BaseService.getTblSessionAnimalsService()
					.getListBySessionId(sessionId);
			List<String> animalIdList = BaseService.getTblAnimalDeathService()
					.getAnimalIdListBySessionId(sessionId);
			if (null != animalList && animalList.size() > 0) {
				if (null == animalIdList) {
					animalIdList = new ArrayList<String>();
				}
				String animalId = "";
				int gender = 0;
				String room = "";
				for (TblSessionAnimals obj : animalList) {
					animalId = obj.getAnimalId();
					gender = obj.getGender();
					room = obj.getRoom();
					TblSessionAnimalForTableView tblSessionAnimalForTableView = new TblSessionAnimalForTableView(
							animalId, gender, room);
					if (animalIdList.contains(animalId)) {
						tblSessionAnimalForTableView.setSelect(true);
					}
					data_animalTable.add(tblSessionAnimalForTableView);
				}
			}
		}
	}

	/**
	 * 加载animalTable数据
	 */
	private void loadData_animalTable(List<TblSessionAnimals> tblSessionAnimalsList) {
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
				tblSessionAnimalForTableView.setSelect(true);
				data_animalTable.add(tblSessionAnimalForTableView);
			}
		}
	}

	/**
	 * 更新TblTraceTable数据
	 */
	public void updateTblTraceTable() {
		data_tblTraceTable.clear();
		if (null != sessionId && !sessionId.isEmpty()) {
			List<TblTrace> tblTraceList = BaseService.getTblTraceservice()
					.getListByTableNameAndDataId("TblAnimalDeath", sessionId);
			if (null != tblTraceList && tblTraceList.size() > 0) {
				for (TblTrace tblTrace : tblTraceList) {
					String newValue = tblTrace.getNewValue();
					String[] newValues = newValue.split(",");
					String animalId = newValues[0];
					switch (tblTrace.getOperateMode()) {
					case 1:
						newValue = newValues[1];
						break;
					case 2:
						newValue = newValues[1];
						break;
					case 3:
						newValue = newValues[1];
						break;
					default:
						break;
					}
					data_tblTraceTable
							.add(new TblTraceForTableView(animalId, tblTrace.getOldValue(),
									newValue, tblTrace.getOperator(), tblTrace.getModifyReason(),
									tblTrace.getModifyTime(), tblTrace.getOperateMode()));
				}
			}
		}
	}

	/**
	 * 撤销按钮
	 */
	@FXML
	private void onCancelButton() {
		// 签过未复核
		if ((null == checkId || checkId.isEmpty()) && (null != signId && !signId.isEmpty())) {
			ObservableList<TblSessionAnimalForTableView> selectData_animalTable = FXCollections
					.observableArrayList();
			for (TblSessionAnimalForTableView obj : data_animalTable) {
				if (obj.getSelect()) {
					selectData_animalTable.add(new TblSessionAnimalForTableView(obj.getAnimalId(),
							obj.getGender().equals("♂") ? 1 : 2, obj.getRoom()));
				}
			}
			if (selectData_animalTable.size() > 0) {
				try {
					CancelDeathFrame.getInstance().loadData(sessionId, selectData_animalTable);
					CancelDeathFrame.getInstance().start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert2.create("没有待撤销的动物");
			}
		}
	}

	/**
	 * 增加按钮
	 */
	@FXML
	private void onAddButton() {
		// 添加
		if (signId == null || "".equals(signId)) {
			// .未签字
			// 1.检查输入项
			String deadDateStr = deadDatePane.getTextField().getText().trim();
			if (null == deadDateStr || deadDateStr.isEmpty()) {
				Alert2.create("请先选择死亡日期");
				deadDatePane.getTextField().requestFocus();
				return;
			}
			String foundDateStr = foundDatePane.getTextField().getText().trim();
			if (null == foundDateStr || foundDateStr.isEmpty()) {
				Alert2.create("请先选择发现时间");
				foundDatePane.getTextField().requestFocus();
				return;
			}
			String notifyDateStr = notifyDatePane.getTextField().getText().trim();
			if (null == notifyDateStr || notifyDateStr.isEmpty()) {
				Alert2.create("请先选择通知时间");
				notifyDatePane.getTextField().requestFocus();
				return;
			}

			String deadType = deadTypeComboBox.getSelectionModel().getSelectedItem();
			if (null == deadType || deadType.isEmpty()) {
				Alert2.create("请先选择或填写死亡原因");
				deadTypeComboBox.requestFocus();
				return;
			}

			String deadRsn = deadRsnText.getText().trim();
			if (null == deadRsn || deadRsn.isEmpty()) {
				Alert2.create("请先选填写死亡原因描述");
				deadRsnText.requestFocus();
				return;
			}
			// 选中的动物id号列表
			List<String> animalIdList = new ArrayList<String>();
			for (TblSessionAnimalForTableView obj : data_animalTable) {
				if (obj.getSelect()) {
					animalIdList.add(obj.getAnimalId());
				}
			}
			if (animalIdList.size() < 1) {
				Alert2.create("请先选择动物");
				return;
			}

			String dealwithOpinion = dealwithOpinionText.getText().trim();
			if (null == dealwithOpinion || dealwithOpinion.isEmpty()) {
				Alert2.create("请先填写对死亡动物的处理意见");
				deadDatePane.getTextField().requestFocus();
				return;
			}

			String remark = remarkText.getText().trim();

			// 1.5 如果无 sessionId ,则先保存 会话和会话动物列表
			if (null == sessionId || "".equals(sessionId)) {
				sessionId = BaseService.getTblSessionService().saveTblSessionAndAnimalList(
						tblSession, tblSessionAnimalsList);
				tblSession.setSessionId(sessionId);
			}
			// 2.动物死亡登记
			Date deadDate = DateUtil.stringToDate(deadDateStr, "yyyy-MM-dd");

			String foundTimeStr = foundDateStr + " "
					+ foundTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ foundTimeMComboBox.getSelectionModel().getSelectedItem();
			Date foundTime = DateUtil.stringToDate(foundTimeStr, "yyyy-MM-dd HH:mm");

			String notifyTimeStr = notifyDateStr + " "
					+ notifyTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ notifyTimeMComboBox.getSelectionModel().getSelectedItem();
			Date notifyTime = DateUtil.stringToDate(notifyTimeStr, "yyyy-MM-dd HH:mm");

			TblAnimalDeath tblAnimalDeath = new TblAnimalDeath();
			tblAnimalDeath.setSessionId(sessionId);
			tblAnimalDeath.setDeadDate(deadDate);
			tblAnimalDeath.setFoundTime(foundTime);
			tblAnimalDeath.setNotifyTime(notifyTime);
			tblAnimalDeath.setDeadType(deadType);
			tblAnimalDeath.setDeadRsn(deadRsn);
			tblAnimalDeath.setDealwithOpinion(dealwithOpinion);
			tblAnimalDeath.setRemark(remark);
			BaseService.getTblAnimalDeathService().saveOrUpdate(tblAnimalDeath, animalIdList);

			// 3.如果是第一个 一般观察， 更新会话里的beginDate
			if (null == beginDate) {
				beginDate = new Date();
				tblSession.setBeginTime(beginDate);
				BaseService.getTblSessionService().update(tblSession);
				updateFlag = true;
			}

			Alert.create("死亡登记保存成功");

		} else if (checkId == null || "".equals(checkId)) {
			// // .已签字，未复核
			// 1.检查输入项
			// if (null == animalId || animalId.isEmpty()) {
			// Alert2.create("请先选择动物");
			// return;
			// }
			// if (0 == gender) {
			// Alert2.create("请先选择动物");
			// return;
			// }
			// if (null == observation || observation.isEmpty()) {
			// Alert2.create("请先选择观察所见内容");
			// return;
			// }
			// if (null == observationCode || observationCode.isEmpty()) {
			// Alert2.create("请先选择观察所见内容");
			// return;
			// }
			// //判断是否已存在
			// boolean isExist = false;
			// if(observation.equals("-")){
			// //待添加的正常，则不可以存在 观察结果
			// isExist =
			// BaseService.getTblGeneralObservationService().isExist(sessionId,
			// animalId);
			// if(isExist){
			// Alert2.create("该动物已有观察结果，不可提交'正常'结果");
			// return;
			// }
			// }else{
			// isExist =
			// BaseService.getTblGeneralObservationService().isExist(sessionId,animalId,observation);
			// if(isExist){
			// Alert2.create("该记录已存在或存在‘正常’结果");
			// return;
			// }
			//
			// }
			// // 2.提示，并电子签名
			// if (Confirm
			// .create(Main.getInstance().getStage(), "提示",
			// "一般观察已签字，新增信息将记录修改痕迹", "确定继续吗？")) {
			// // 电子签名及原因
			// SignFrameWithReason signFrameWithReason = new
			// SignFrameWithReason("");
			// Stage stage = new Stage();
			// stage.initModality(Modality.APPLICATION_MODAL);
			// stage.setTitle("身份确认");
			// try {
			// signFrameWithReason.start(stage);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// // 签字通过
			// if ("OK".equals(SignFrameWithReason.getType())) {
			// // 3.保存修改痕迹
			// // 动物Id号+“，”+观察所见
			// String newValue = animalId + "," +observation;
			// Date date = new Date();
			// TblTrace tblTrace = new TblTrace();
			// tblTrace.setTableName("TblGeneralObservation");// 表名
			// tblTrace.setDataId(sessionId); // 这里放的是会话Id
			// tblTrace.setOperateMode(3);// 增加
			// tblTrace.setOldValue("");
			// tblTrace.setNewValue(newValue);
			// tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
			// tblTrace.setHost(SystemTool.getIPAddress());
			// tblTrace.setModifyReason(SignFrameWithReason.getReason());
			// tblTrace.setModifyTime(date);
			// BaseService.getTblTraceservice().save(tblTrace);
			// // 4.增加数据()
			// TblGeneralObservation tblGeneralObservation = new
			// TblGeneralObservation();
			// tblGeneralObservation.setSessionId(sessionId);
			// tblGeneralObservation.setAnimalId(animalId);
			// tblGeneralObservation.setGender(gender);
			// tblGeneralObservation.setObservation(observation);
			// tblGeneralObservation.setObservationCode(observationCode);
			// tblGeneralObservation.setRecordTime(date);
			// tblGeneralObservation.setTblSession(tblSession);
			// String id =
			// BaseService.getTblGeneralObservationService().saveNoIdReturnId(tblGeneralObservation);
			// // 5.tblGeneralObservationTable表中添加一行记录
			// TblGeneralObservationForTableView
			// tblGeneralObservationForTableView = new
			// TblGeneralObservationForTableView(
			// id,animalId, observation, date);
			// data_tblGeneralObservationTable.add(tblGeneralObservationForTableView);
			// tblGeneralObservationTable.scrollTo(1000);
			// tblGeneralObservationTable.getSelectionModel().selectLast();
			// // 6. 更新修改痕迹表格
			// addTblTraceTable(tblTrace);
			// }
		} else {
			Alert2.create("已复核，无法修改数据");
			return;
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
		String deadDateStr = deadDatePane.getTextField().getText().trim();
		if (null == deadDateStr || deadDateStr.isEmpty()) {
			Alert2.create("请先选择死亡日期");
			deadDatePane.getTextField().requestFocus();
			return;
		}
		String foundDateStr = foundDatePane.getTextField().getText().trim();
		if (null == foundDateStr || foundDateStr.isEmpty()) {
			Alert2.create("请先选择发现时间");
			foundDatePane.getTextField().requestFocus();
			return;
		}
		String notifyDateStr = notifyDatePane.getTextField().getText().trim();
		if (null == notifyDateStr || notifyDateStr.isEmpty()) {
			Alert2.create("请先选择通知时间");
			notifyDatePane.getTextField().requestFocus();
			return;
		}

		String deadType = deadTypeComboBox.getSelectionModel().getSelectedItem();
		if (null == deadType || deadType.isEmpty()) {
			Alert2.create("请先选择死亡原因");
			deadTypeComboBox.requestFocus();
			return;
		}

		String deadRsn = deadRsnText.getText().trim();
		if (null == deadRsn || deadRsn.isEmpty()) {
			Alert2.create("请先选填写死亡原因描述");
			deadRsnText.requestFocus();
			return;
		}
		if(deadRsn.getBytes().length>50){
			Alert2.create("'死亡原因描述'长度不能大于50");
			deadRsnText.requestFocus();
			return;
		}
		// 选中的动物id号列表
		List<String> animalIdList = new ArrayList<String>();
		for (TblSessionAnimalForTableView obj : data_animalTable) {
			if (obj.getSelect()) {
				animalIdList.add(obj.getAnimalId());
			}
		}
		if (animalIdList.size() < 1) {
			Alert2.create("请先选择动物");
			return;
		}

		String dealwithOpinion = dealwithOpinionText.getText().trim();
		if (null == dealwithOpinion || dealwithOpinion.isEmpty()) {
			Alert2.create("请先填写对死亡动物的处理意见");
			dealwithOpinionText.requestFocus();
			return;
		}
		if(dealwithOpinion.getBytes().length>100){
			Alert2.create("'对死亡动物的处理意见'长度不能大于100");
			dealwithOpinionText.requestFocus();
			return;
		}
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

			// 4 无 sessionId ，保存 会话和会话动物列表，以及 beginDate
			beginDate = new Date();
			tblSession.setBeginTime(beginDate);
			sessionId = BaseService.getTblSessionService().saveTblSessionAndAnimalList(tblSession,
					tblSessionAnimalsList);
			tblSession.setSessionId(sessionId);

			// 离开页面时，是否需要更新DayToDayPane上tblSession表数据
			updateFlag = true;
			// 离开页面时，是否需要更新DayToDayPane上animalTable表数据
			updateAnimalListFlag = true;

			// 5.动物死亡登记及列表
			Date deadDate = DateUtil.stringToDate(deadDateStr, "yyyy-MM-dd");

			String foundTimeStr = foundDateStr + " "
					+ foundTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ foundTimeMComboBox.getSelectionModel().getSelectedItem();
			Date foundTime = DateUtil.stringToDate(foundTimeStr, "yyyy-MM-dd HH:mm");

			String notifyTimeStr = notifyDateStr + " "
					+ notifyTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ notifyTimeMComboBox.getSelectionModel().getSelectedItem();
			Date notifyTime = DateUtil.stringToDate(notifyTimeStr, "yyyy-MM-dd HH:mm");

			TblAnimalDeath tblAnimalDeath = new TblAnimalDeath();
			tblAnimalDeath.setSessionId(sessionId);
			tblAnimalDeath.setDeadDate(deadDate);
			tblAnimalDeath.setFoundTime(foundTime);
			tblAnimalDeath.setNotifyTime(notifyTime);
			tblAnimalDeath.setDeadType(deadType);
			tblAnimalDeath.setDeadRsn(deadRsn);
			tblAnimalDeath.setDealwithOpinion(dealwithOpinion);
			tblAnimalDeath.setRemark(remark);
			BaseService.getTblAnimalDeathService().saveOrUpdate(tblAnimalDeath, animalIdList);

			int deadFlag = 1;
			String deadOperator = tblSession.getCreater();
			Date deadRegTime = tblSession.getBeginTime();

			BaseService.getTblAnimalRecListService().updateAnimalDeadState(deadFlag, deadDate,
					deadRsn, deadOperator, deadRegTime, animalIdList);

			signId = BaseService.getTblSessionService().sign(sessionId, 16,
					SignFrame.getUser().getRealName(), "检疫，动物死亡签字");

			cancelButton.setDisable(false);
			Alert.create("保存及签字成功");
		}

	}

	/**
	 * 复核
	 */
	@FXML
	private void onCheckButton() {
		if (null == signId || signId.isEmpty()) {
			Alert2.create("未签字，请先签字！");
			return;
		}
		if (null != checkId && !checkId.isEmpty()) {
			Alert2.create("已复核，不可重复复核！");
			return;
		}

		// 2.检查输入项
		String deadDateStr = deadDatePane.getTextField().getText().trim();
		if (null == deadDateStr || deadDateStr.isEmpty()) {
			Alert2.create("请先选择死亡日期");
			deadDatePane.getTextField().requestFocus();
			return;
		}
		String foundDateStr = foundDatePane.getTextField().getText().trim();
		if (null == foundDateStr || foundDateStr.isEmpty()) {
			Alert2.create("请先选择发现时间");
			foundDatePane.getTextField().requestFocus();
			return;
		}
		String notifyDateStr = notifyDatePane.getTextField().getText().trim();
		if (null == notifyDateStr || notifyDateStr.isEmpty()) {
			Alert2.create("请先选择通知时间");
			notifyDatePane.getTextField().requestFocus();
			return;
		}

		String deadType = deadTypeComboBox.getSelectionModel().getSelectedItem();
		if (null == deadType || deadType.isEmpty()) {
			Alert2.create("请先选择死亡原因");
			deadTypeComboBox.requestFocus();
			return;
		}

		String deadRsn = deadRsnText.getText().trim();
		if (null == deadRsn || deadRsn.isEmpty()) {
			Alert2.create("请先选填写死亡原因描述");
			deadRsnText.requestFocus();
			return;
		}
		if(deadRsn.getBytes().length>50){
			Alert2.create("'死亡原因描述'长度不能大于50");
			deadRsnText.requestFocus();
			return;
		}
		String dealwithOpinion = dealwithOpinionText.getText().trim();
		if (null == dealwithOpinion || dealwithOpinion.isEmpty()) {
			Alert2.create("请先填写对死亡动物的处理意见");
			dealwithOpinionText.requestFocus();
			return;
		}
		if(dealwithOpinion.getBytes().length>100){
			Alert2.create("'对死亡动物的处理意见'长度不能大于100");
			dealwithOpinionText.requestFocus();
			return;
		}
		String remark = remarkText.getText().trim();
		if(remark.getBytes().length>100){
			Alert2.create("'备注'长度不能大于100");
			remarkText.requestFocus();
			return;
		}

		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("复核");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("OK".equals(SignFrame.getType())) {
			// 3.更新死亡登记
			Date deadDate = DateUtil.stringToDate(deadDateStr, "yyyy-MM-dd");

			String foundTimeStr = foundDateStr + " "
					+ foundTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ foundTimeMComboBox.getSelectionModel().getSelectedItem();
			Date foundTime = DateUtil.stringToDate(foundTimeStr, "yyyy-MM-dd HH:mm");

			String notifyTimeStr = notifyDateStr + " "
					+ notifyTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ notifyTimeMComboBox.getSelectionModel().getSelectedItem();
			Date notifyTime = DateUtil.stringToDate(notifyTimeStr, "yyyy-MM-dd HH:mm");
			TblAnimalDeath tblAnimalDeath = new TblAnimalDeath();
			tblAnimalDeath.setSessionId(sessionId);
			tblAnimalDeath.setDeadDate(deadDate);
			tblAnimalDeath.setFoundTime(foundTime);
			tblAnimalDeath.setNotifyTime(notifyTime);
			tblAnimalDeath.setDeadType(deadType);
			tblAnimalDeath.setDeadRsn(deadRsn);
			tblAnimalDeath.setDealwithOpinion(dealwithOpinion);
			tblAnimalDeath.setRemark(remark);
			BaseService.getTblAnimalDeathService().saveOrUpdate(tblAnimalDeath);

			checkId = BaseService.getTblSessionService().check(sessionId, 17,
					SignFrame.getUser().getRealName(), "检疫，动物复核复核");

			updateFlag = true;

			cancelButton.setDisable(true);
			hbox.setVisible(true);
			dealwithOpinionText.setEditable(false);
			remarkText.setEditable(false);
			Alert.create("编辑及复核成功");
		}

	}

	/**
	 * 打印
	 */
	@FXML
	private void onPrintButton() {
		if (null != checkId && !checkId.isEmpty()) {
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

			TblAnimalDeath tblAnimalDeath = BaseService.getTblAnimalDeathService().getById(
					sessionId);
			if (tblAnimalDeath != null) {
				/** 编号 */
				String number = BaseService.getDictOutputSettingsService().getShowByLabel(
						"动物异常死亡记录表-编号");
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
				if(null == list || list.size()<1){
					Alert2.create("死亡登记动物已撤销，无打印数据");
					return ;
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
			Alert2.create("未复核，复核后才可以打印");
		}

	}

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
			// 2.判断是否需要更新DayToDayPane上animalTable表数据
			if (updateAnimalListFlag) {
				DayToDayController.getInstance().updateAniamlTable();
			}
			// 3.关闭窗口
			Button button = (Button) event.getSource();
			button.getScene().getWindow().hide();
		}
	}

	/**
	 * 加载数据，控件，表格
	 * 
	 * @param sessionId
	 */
	public void loadData(String sessionId) {
		AnimalDeathFrame.sessionId = sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		AnimalDeathFrame.tblSessionAnimalsList = null;
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

			TblAnimalDeath tblAnimalDeath = BaseService.getTblAnimalDeathService().getById(
					sessionId);
			if (null != tblAnimalDeath) {
				String deadDateStr = DateUtil.dateToString(tblAnimalDeath.getDeadDate(),
						"yyyy-MM-dd");
				String foundDateStr = DateUtil.dateToString(tblAnimalDeath.getFoundTime(),
						"yyyy-MM-dd");
				String notifyDateStr = DateUtil.dateToString(tblAnimalDeath.getNotifyTime(),
						"yyyy-MM-dd");
				String foundTimeHStr = DateUtil.dateToString(tblAnimalDeath.getFoundTime(), "HH");
				String foundTimeMStr = DateUtil.dateToString(tblAnimalDeath.getFoundTime(), "mm");
				String notifyTimeHDateStr = DateUtil.dateToString(tblAnimalDeath.getNotifyTime(),
						"HH");
				String notifyTimeMDateStr = DateUtil.dateToString(tblAnimalDeath.getNotifyTime(),
						"mm");
				String deadType = tblAnimalDeath.getDeadType();
				String deadRsn = tblAnimalDeath.getDeadRsn();
				String dealWithOpinion = tblAnimalDeath.getDealwithOpinion();
				String remark = tblAnimalDeath.getRemark();

				deadDatePane.getTextField().setText(deadDateStr);
				foundDatePane.getTextField().setText(foundDateStr);
				notifyDatePane.getTextField().setText(notifyDateStr);
				foundTimeHComboBox.getSelectionModel().select(foundTimeHStr);
				foundTimeMComboBox.getSelectionModel().select(foundTimeMStr);
				notifyTimeHComboBox.getSelectionModel().select(notifyTimeHDateStr);
				notifyTimeMComboBox.getSelectionModel().select(notifyTimeMDateStr);

				deadTypeComboBox.getSelectionModel().select(deadType);
				deadRsnText.setText(deadRsn);
				dealwithOpinionText.setText(dealWithOpinion);
				remarkText.setText(remark);
			}

			// 2.签字后启用cancel
			signId = tblSession.getSignId();
			if (null != signId && !"".equals(signId)) {
				cancelButton.setDisable(false);
			}
			// 复核后 禁用 cancelButton 以及其他
			checkId = tblSession.getCheckId();
			if (null != checkId && !"".equals(checkId)) {
				cancelButton.setDisable(true);
				hbox.setVisible(true);
				dealwithOpinionText.setEditable(false);
				remarkText.setEditable(false);
			}
			beginDate = tblSession.getBeginTime();
			// 3.加载animalTable数据
			loadData_animalTable();

			// 4.加载修改痕迹表格数据
			updateTblTraceTable();
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
		AnimalDeathFrame.sessionId = null;
		AnimalDeathFrame.tblSession = tblSession;
		AnimalDeathFrame.tblSessionAnimalsList = tblSessionAnimalsList;
		signId = null;
		checkId = null;
		beginDate = null;
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

			// 3.加载animalTable数据
			loadData_animalTable(tblSessionAnimalsList);

			// 4.加载修改痕迹表格数据
			data_tblTraceTable.clear();
		}

	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(this.getClass().getResource("AnimalDeathFrame.fxml"));
		Scene scene = new Scene(root, 650, 640);
		stage.setScene(scene);
		stage.setTitle("动物死亡");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				if ((null == sessionId || "".equals(sessionId))
						&& !Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")) {
					event.consume();
				} else {
					// 2.判断是否需要更新DayToDayPane上tblSession表数据
					if (updateFlag) {
						DayToDayController.getInstance().loadData();
					}
					// 3.判断是否需要更新DayToDayPane上animalTable表数据
					if (updateAnimalListFlag) {
						DayToDayController.getInstance().updateAniamlTable();
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
