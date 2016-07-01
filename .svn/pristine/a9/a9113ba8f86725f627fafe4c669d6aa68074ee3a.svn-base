package com.lanen.view.tblsession;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.quarantine.tblsession.TblAniResite;
import com.lanen.model.quarantine.tblsession.TblAnimalDeath;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.TblSessionAnimalForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;
import com.lanen.view.tblsession.DayToDayController.CheckBoxTableCell;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AniResiteFrame extends Application implements Initializable {

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
	private HBox resiteDateHBox;// 安置日期
	private DatePicker resiteDatePane = null;

	@FXML
	private ComboBox<String> resiteTimeHComboBox;// 安置时间——时
	@FXML
	private ComboBox<String> resiteTimeMComboBox;// 安置时间——分
	@FXML
	private ComboBox<String> roomComboBox;// 新房间号
	private ObservableList<String> data_roomComboBox = FXCollections.observableArrayList();
	@FXML
	private TextArea remarkText;// 备注
	@FXML
	private TextField resiteRsnText;

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
	 * 复核后，禁止编辑
	 */
	@FXML
	private HBox hbox;
	@FXML
	private HBox hbox1;

	private static TblSession tblSession = null;
	private static String sessionId = "";
	private static List<TblSessionAnimals> tblSessionAnimalsList = null;

	private static String signId = "";
	/** 离开页面时，是否需要更新DayToDayPane上tblSession表数据 */
	private static boolean updateFlag = false;// 离开页面时，是否需要更新DayToDayPane上tblSession表数据

	private static AniResiteFrame instance;

	public static AniResiteFrame getInstance() {
		if (null == instance) {
			instance = new AniResiteFrame();
		}
		updateFlag = false;
		return instance;
	}

	public AniResiteFrame() {
		updateFlag = false;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		// 初始化animalTable
		initAnimalTable();
		// 初始化日期选择器及时分
		initDate();
		// 房间号ComboBox 初始化
		initRoomComboBox();
	}

	/**
	 * roomComboBox初始化
	 */
	private void initRoomComboBox() {
		data_roomComboBox.clear();
		roomComboBox.setItems(data_roomComboBox);
	}
	/**更新 roomComboBox*/
	public void updateRoomComboBox(String animalType){
		List<String> roomList = BaseService.getTblRoomAndAniTypeService().getRoomListbyAnimalType(animalType);
		if (null != roomList && roomList.size()>0) {
			data_roomComboBox.addAll(roomList);
		}
	}

	/**
	 * 初始化日期选择器及时分
	 */
	private void initDate() {
		resiteDatePane = new DatePicker(Locale.CHINA);
		resiteDatePane.getTextField().setEditable(false);
		resiteDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		resiteDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		resiteDatePane.getCalendarView().setShowWeeks(false);
		resiteDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		resiteDatePane.setMaxWidth(110);

		resiteDateHBox.getChildren().add(resiteDatePane);

		resiteTimeHComboBox.getSelectionModel().select(0);
		resiteTimeMComboBox.getSelectionModel().select(0);

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
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TblSessionAnimalForTableView currentSelectItem = animalTable
							.getSelectionModel().getSelectedItem();
					if (null != currentSelectItem) {
						TblSessionAnimalForTableView index = new TblSessionAnimalForTableView(
								currentSelectItem.getAnimalId(), (currentSelectItem.getGender()
										.equals("♂") ? 1 : 2), currentSelectItem.getRoom());
						index.setSelect(!currentSelectItem.getSelect());
						animalTable.getItems().set(
								animalTable.getItems().indexOf(currentSelectItem), index);
						animalTable.getSelectionModel().clearSelection();
					}
				}

			}
		});
	}


	/**
	 * 加载animalTable数据
	 */
	public void updateAnimalTable(){
		data_animalTable.clear();
		if (null != sessionId && !sessionId.isEmpty()) {
			// 会话动物列表
			List<TblSessionAnimals> animalList = BaseService.getTblSessionAnimalsService()
					.getListBySessionId(sessionId);
			List<String> animalIdList = BaseService.getTblAniResiteService()
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
	 * 加载animalTable数据(全选中)
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
				tblSessionAnimalForTableView.setSelect(true);
				data_animalTable.add(tblSessionAnimalForTableView);
			}
		}
	}

	/**
	 * 禁用 动物表格  checkbox
	 */
	private void disableAnimalTalbeCheckBox(){
		hbox1.setVisible(true);
		animalTable.setOnMouseClicked(null);
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

		String resiteDateStr = resiteDatePane.getTextField().getText().trim();
		if (null == resiteDateStr || resiteDateStr.isEmpty()) {
			Alert2.create("请先选择日期");
			resiteDatePane.getTextField().requestFocus();
			return;
		}
		String room = roomComboBox.getSelectionModel().getSelectedItem();
		if (null == room || room.isEmpty()) {
			Alert2.create("请先选择房间号");
			roomComboBox.requestFocus();
			return;
		}
		String resiteRsn = resiteRsnText.getText().trim();
		if (null == resiteRsn || resiteRsn.isEmpty()) {
			Alert2.create("请先填写安置原因");
			resiteRsnText.requestFocus();
			return;
		}
		if(resiteRsn.getBytes().length>50){
			Alert2.create("'安置原因'长度不能大于50");
			resiteRsnText.requestFocus();
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
			Date beginDate = new Date();
			tblSession.setBeginTime(beginDate);
			tblSession.setEndTime(beginDate);
			sessionId = BaseService.getTblSessionService().saveTblSessionAndAnimalList(tblSession,
					tblSessionAnimalsList);
			tblSession.setSessionId(sessionId);

			// 离开页面时，是否需要更新DayToDayPane上tblSession表数据
			updateFlag = true;
			
			// 5.动物重新安置记录

			String resiteTimeStr = resiteDateStr + " "
					+ resiteTimeHComboBox.getSelectionModel().getSelectedItem() + ":"
					+ resiteTimeMComboBox.getSelectionModel().getSelectedItem();
			Date resiteTime = DateUtil.stringToDate(resiteTimeStr, "yyyy-MM-dd HH:mm");

			TblAniResite tblAniResite = new TblAniResite();
			tblAniResite.setSessionId(sessionId);
			tblAniResite.setResiteDate(resiteTime);
			tblAniResite.setResiteTime(resiteTime);
			tblAniResite.setNewRoom(room);
			tblAniResite.setRemark(remark);
			tblAniResite.setResiteRsn(resiteRsn);
			BaseService.getTblAniResiteService().saveEntityAndList(tblAniResite, animalIdList);
			//6.更新接收动物列表中对应的  room
			BaseService.getTblAnimalRecListService().updateAnimalRoom(room, animalIdList);

			signId = BaseService.getTblSessionService().sign(sessionId, 18,
					SignFrame.getUser().getRealName(), "检疫，重新安置签字");
			
			// 7.禁用 动物表格 checkbox及其他控件
			disableAnimalTalbeCheckBox();
			hbox.setVisible(true);
			Alert.create("保存及签字成功");
		}

	}

	/**
	 * 复核
	 */
	@FXML
	private void onCheckButton() {}

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
	 * 加载数据，控件，表格
	 * 
	 * @param sessionId
	 */
	public void loadData(String sessionId) {
		AniResiteFrame.sessionId = sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		AniResiteFrame.tblSessionAnimalsList = null;
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

			TblAniResite tblAniResite = BaseService.getTblAniResiteService().getById(
					sessionId);
			//2.加载房间号comboBox
			updateRoomComboBox(tblSession.getAnimalType());
			if (null != tblAniResite) {
				String resiteDateStr = DateUtil.dateToString(tblAniResite.getResiteDate(),
						"yyyy-MM-dd");
				String resiteTimeHDateStr = DateUtil.dateToString(tblAniResite.getResiteTime(),
						"HH");
				String resiteTimeMDateStr = DateUtil.dateToString(tblAniResite.getResiteTime(),
						"mm");
				String room = tblAniResite.getNewRoom();
				String resiteRsn = tblAniResite.getResiteRsn();
				String remark = tblAniResite.getRemark();

				resiteDatePane.getTextField().setText(resiteDateStr);
				resiteTimeHComboBox.getSelectionModel().select(resiteTimeHDateStr);
				resiteTimeMComboBox.getSelectionModel().select(resiteTimeMDateStr);

				roomComboBox.getSelectionModel().select(room);
				resiteRsnText.setText(resiteRsn);
				remarkText.setText(remark);
			}

			// 3.签字后启用cancel
			signId = tblSession.getSignId();
			if (null != signId && !"".equals(signId)) {
				hbox.setVisible(true);
			}
			// 4.加载animalTable数据
			updateAnimalTable();
			// 5.禁用 动物表格 checkbox
			disableAnimalTalbeCheckBox();
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
		AniResiteFrame.sessionId = null;
		AniResiteFrame.tblSession = tblSession;
		AniResiteFrame.tblSessionAnimalsList = tblSessionAnimalsList;
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
			//3.加载房间号comboBox
			updateRoomComboBox(tblSession.getAnimalType());
		}

	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(this.getClass().getResource("AniResiteFrame.fxml"));
		Scene scene = new Scene(root, 595, 470);
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
