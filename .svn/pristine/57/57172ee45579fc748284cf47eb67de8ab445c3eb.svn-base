package com.lanen.view.balreg;

import java.net.URL;
import java.text.ParseException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblBalReg;
import com.lanen.util.BackgroundRunner;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;

import datecontrol.DatePicker;
import datecontrol.Junit;

/**天平登记
 * @author Administrator
 *
 */
public class AddBalanceRrge extends Application implements Initializable {

//	private Stage stage;

	@FXML
	private TextField balCodeText;// 天平编号

	@FXML
	private TextField balModelText;// 天平型号

	@FXML
	private TextField balNameText;// 天平名称

	@FXML
	private TextField precisionText;// 精度（克）

	@FXML
	private TextField rangeText;// 最大量程
	@FXML
	private TextField idText;// 编辑用的id
	@FXML
	private TextField calCheckPointText;// 校准检查点数

	@FXML
	private ComboBox<String> paritComboBox;// 校验方式
	// 校验方式下拉框的 值列表
	private ObservableList<String> paritdata = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> stopBitComboBox;// 停止位
	// 停止位下拉框的 值列表
	private ObservableList<String> stopBitdata = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> dataBitComboBox;// 数据位
	// 数据位下拉框的 值列表
	private ObservableList<String> dataBitdata = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> baudComboBox;// 波特率
	// 数据位下拉框的 值列表
	private ObservableList<String> bauddata = FXCollections.observableArrayList();

	// 日期查询
	@FXML
	private HBox validDateHbox;
	DatePicker validDatePane = null;

	private static AddBalanceRrge instance;

	public static AddBalanceRrge getInstance() {
		if (null == instance) {
			instance = new AddBalanceRrge();
		}
		return instance;
	}

	public AddBalanceRrge() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initParitComboBox();// 校验方式下拉选
		initStopBitComboBox();// 停止位下拉选
		initdataBitComboBox();// 数据位下拉选
		initBaudComboBox();// 波特率下拉选
		initDatePane();// 检验有效期

	}

	/**
	 * 初始化检验有效期
	 */
	private void initDatePane() {
		validDatePane = new DatePicker(Locale.CHINA);
		validDatePane.getTextField().setEditable(true);
		validDatePane.getTextField().setFocusTraversable(true);
		validDatePane.getTextField().setMaxWidth(200);
		validDatePane.getTextField().setMinWidth(200);
		validDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		validDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		validDatePane.getCalendarView().setShowWeeks(false);
		validDatePane.getStylesheets().add(
				Junit.class.getResource("DatePicker.css").toExternalForm());
		validDatePane.setMaxWidth(200);
		validDateHbox.getChildren().add(validDatePane);
		validDatePane.getTextField().setText(DateUtil.getDateAgo(30));
		validDatePane.getTextField().setFocusTraversable(false);

	}

	/**
	 * 编辑
	 * 
	 * @param tblBalReg
	 */
	public void loadData(TblBalReg tblBalReg) {
		idText.clear();
		idText.setText(tblBalReg.getBalCode());
		balCodeText.clear();
		balModelText.clear();
		balNameText.clear();
		precisionText.clear();
		rangeText.clear();
		balCodeText.setText(tblBalReg.getBalCode());// 天平编号
		balModelText.setText(tblBalReg.getBalModel());// 天平型号
		balNameText.setText(tblBalReg.getBalName());// 天平名称
		precisionText.setText(tblBalReg.getPrecision());// 精度（克）
		rangeText.setText(tblBalReg.getRange() + "");// 最大量程
		// baudComboBox.getSelectionModel().select(tblBalReg.getBaud()+"");
		// dataBitComboBox.getSelectionModel().select(tblBalReg.getDataBit()+"");;//数据位
		// stopBitComboBox.getSelectionModel().select(tblBalReg.getStopBit()+"");;//停止位
		// paritComboBox.getSelectionModel().select(tblBalReg.getParit()+"");;//校验方式
		String date = DateUtil.dateToString(tblBalReg.getValidDate(), "yyyy-MM-dd");
		validDatePane.getTextField().setText(date);
		calCheckPointText.setText(tblBalReg.getCalCheckPoint()+"");

	}

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("addBalanceRrge.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("天平登记");
		stage.setResizable(false);
		// stage.initModality(Modality.APPLICATION_MODAL);
		// stage.initOwner(Main.mainWidow);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// event.consume();
			}
		});
		stage.show();
	}

	/**
	 * 取消
	 * 
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event) {
		((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
	}

	/**
	 * 校验是否是数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^\\d+(\\.\\d+)?$");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/** 新增天平登记 */
	@FXML
	private void onSaveBalanceRegButtonAction(ActionEvent event) throws ParseException {
		Boolean right = true;
		String id = idText.getText().toString();
		String balCode = balCodeText.getText().toString();// 天平编号
		String balModel = balModelText.getText().toString();// 天平型号
		String balName = balNameText.getText().toString();// 天平名称
		String precision = precisionText.getText().toString();// 精度（克）
		String baudstr = baudComboBox.getSelectionModel().getSelectedItem();// 波特率
		String dataBitStr = dataBitComboBox.getSelectionModel().getSelectedItem();// 数据位
		String stopBitStr = stopBitComboBox.getSelectionModel().getSelectedItem();// 停止位
		String paritStr = paritComboBox.getSelectionModel().getSelectedItem();// 校验方式
		String rangeStr = rangeText.getText().toString();// 最大量程
		String calCheckPointStr = calCheckPointText.getText().toString();
		idText.getText().toString();// 老的编号
		String errorStr = "";
		if (null == balCode || balCode.equals("")) {
			errorStr = "请输入正确的天平编号";
			right = false;
		} else if (null == balModel || balModel.equals("")) {
			errorStr = "请输入正确的天平型号";
			right = false;
		} else if (null == balName || balName.equals("")) {
			errorStr = "请输入正确的天平名称";
			right = false;
		} else if (null == precision || precision.equals("") || (!isNumber(precision))) {
			errorStr = "请输入正确的精度";
			right = false;
		} else if (null == baudstr || baudstr.equals("")) {
			errorStr = "请输入正确的波特率";
			right = false;
		} else if (null == dataBitStr || dataBitStr.equals("")) {
			errorStr = "请输入正确的数据位";
			right = false;
		} else if (null == stopBitStr || stopBitStr.equals("")) {
			errorStr = "请输入正确的停止位";
			right = false;
		} else if (null == paritStr || paritStr.equals("")) {
			errorStr = "请输入正确的校验方式";
			right = false;
		} else if (null == rangeStr || rangeStr.equals("") || (!isNumber(rangeStr))) {
			errorStr = "请输入正确的最大量程";
			right = false;
		} else if (null == calCheckPointStr || calCheckPointStr.equals("")
				|| (!isNumber(calCheckPointStr))) {
			errorStr = "请输入正确的校准检查点数";
			right = false;
		}
		Integer baud = null;
		Integer dataBit = null;
		Integer stopBit = null;
		Integer parit = null;
		Integer range = null;
		Integer calCheckPoint = null;

		if (right) {
			if (!id.equals(balCode)) {
				if (BaseService.getInstance().getTblBalRegService().isExistByBalCode(balCode)) {
					errorStr = "天平编号已存在";
					right = false;
				}
				if (BaseService.getInstance().getTblBalRegService().isExistBybalName(balName)) {
					errorStr = "天平名称已存在";
					right = false;
				}
			} else {
				if (BaseService.getInstance().getTblBalRegService().isExistBybalName(balName, balCode)) {
					errorStr = "天平名称已存在";
					right = false;
				}
			}

			if (null != baudstr && !baudstr.equals("")) {
				baud = Integer.parseInt(baudstr);
			}

			if (null != dataBitStr && !dataBitStr.equals("")) {
				dataBit = Integer.parseInt(dataBitStr);
			}
			if (null != calCheckPointStr && !calCheckPointStr.equals("")) {
				calCheckPoint = Integer.parseInt(calCheckPointStr);
			}
			if (null != stopBitStr && !stopBitStr.equals("")) {
				if (stopBitStr.equals("1.5")) {
					stopBit = 15;
				} else {
					stopBit = Integer.parseInt(stopBitStr);
				}
			}

			if (paritStr.equals("Odd")) {
				parit = 1;
			} else if (paritStr.equals("Even")) {
				parit = 2;
			} else if (paritStr.equals("Mark")) {
				parit = 3;
			} else if (paritStr.equals("Space")) {
				parit = 4;
			} else {
				parit = 0;
			}

			if (null != rangeStr && !rangeStr.equals("")) {
				range = Integer.parseInt(rangeStr);
			}

			if (dataBit < 1) {
				errorStr = "数据位不能小于1";
				right = false;
			}
		}

		if (right) {
			String validDateStr = validDatePane.getTextField().getText();
			Date validDate = null;
			if (null != validDateStr && !validDateStr.equals("")) {
				validDate = DateUtil.stringToDate(validDateStr, "yyyy-MM-dd");
			}
			TblBalReg tblBalReg;
			if (!id.equals(balCode)) {
				tblBalReg = new TblBalReg();
			} else {
				tblBalReg = BaseService.getInstance().getTblBalRegService().getById(id);
			}
			tblBalReg.setBalCode(balCode);
			tblBalReg.setBalModel(balModel);
			tblBalReg.setBalName(balName);
			tblBalReg.setPrecision(precision);
			tblBalReg.setBaud(baud);
			tblBalReg.setDataBit(dataBit);
			tblBalReg.setStopBit(stopBit);
			tblBalReg.setParit(parit);
			tblBalReg.setRange(range);
			tblBalReg.setValidDate(validDate);
			tblBalReg.setCalCheckPoint(calCheckPoint);
			try {
				// new BaseService.getInstance()();
				if (!id.equals(balCode)) {
					BaseService.getInstance().getTblBalRegService().save(tblBalReg);
				} else {
					BaseService.getInstance().getTblBalRegService().update(tblBalReg);
				}
				BalanceRge.getInstance().loadData();
				BalanceRge.getInstance().selectRow(tblBalReg);
				((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			showErrorMessage(errorStr);
		}

	}

//	/**
//	 * 获取当前页面定义的 stage
//	 * 
//	 * @return
//	 */
//	public Stage getStage() {
//		if (null == stage) {
//			stage = new Stage();
//			stage.initOwner(Main.mainWidow);
//			stage.initModality(Modality.APPLICATION_MODAL);
//		}
//		return stage;
//
//	}

	/**
	 * 校验方式初始化 下拉框 paritComboBox
	 */
	private void initParitComboBox() {
		// 下拉框 onChange事件
		new BackgroundRunner() {
			String prompt;
			boolean isError;
			boolean ok = false;

			@Override
			public void background() throws Exception {
				try {
					// 初始化service
					List<String> list = new ArrayList<String>();
					list.add("None");
					list.add("Odd");
					list.add("Even");
					list.add("Mark");
					list.add("Space");
					paritdata.clear();
					if (null != list && list.size() > 0) {
						for (String parit : list) {
							paritdata.add(parit);
						}
					}

					paritComboBox.setItems(paritdata);
					ok = true;
				} catch (Exception e) {
					showError("连接服务器失败");
					e.printStackTrace();

				}
			}

			public void showError(String prompt) {
				this.prompt = prompt;
				this.isError = true;
				runForeground();
			}

			@Override
			public void foreground() throws Exception {
				if (isError) {
					showErrorMessage(prompt);
				} else {
					showMessage(prompt);
				}
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
				e.printStackTrace();
			}

			@Override
			public void finish() throws Exception {
				if (ok) {
					// Main.getInstance().enterMainScene();
					// 页面加载完毕，控件置为可用
					paritComboBox.setDisable(false);
					if (paritdata.size() > 0) {
						// paritComboBox.getSelectionModel().selectFirst();
						String id = idText.getText().toString();
						if (null != id && !id.equals("")) {
							TblBalReg tblBalReg = BaseService.getInstance().getTblBalRegService().getById(id);
							if (null != tblBalReg) {
								Integer parit = tblBalReg.getParit();
								// 0:None,1:Odd,2:Even,3:Mark,4:Space
								if (parit.equals(0) || parit == 0) {
									paritComboBox.getSelectionModel().select("None");
								} else if (parit.equals(1) || parit == 1) {
									paritComboBox.getSelectionModel().select("Odd");
								} else if (parit.equals(2) || parit == 2) {
									paritComboBox.getSelectionModel().select("Even");
								} else if (parit.equals(3) || parit == 3) {
									paritComboBox.getSelectionModel().select("Mark");
								} else if (parit.equals(4) || parit == 4) {
									paritComboBox.getSelectionModel().select("Space");
								}

							}

						}
					}
				}
			}

		}.run();
	}

	/**
	 * 停止位初始化 下拉框 stopBitComboBox;
	 */
	private void initStopBitComboBox() {
		// 下拉框 onChange事件
		new BackgroundRunner() {
			String prompt;
			boolean isError;
			boolean ok = false;

			@Override
			public void background() throws Exception {
				try {
					// 初始化service
					List<String> list = new ArrayList<String>();
					list.add("1");
					list.add("1.5");
					list.add("2");
					stopBitdata.clear();
					if (null != list && list.size() > 0) {
						for (String stopBit : list) {
							stopBitdata.add(stopBit);
						}
					}

					stopBitComboBox.setItems(stopBitdata);
					ok = true;
				} catch (Exception e) {
					showError("连接服务器失败");
					e.printStackTrace();

				}
			}

			public void showError(String prompt) {
				this.prompt = prompt;
				this.isError = true;
				runForeground();
			}

			@Override
			public void foreground() throws Exception {
				if (isError) {
					showErrorMessage(prompt);
				} else {
					showMessage(prompt);
				}
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
				e.printStackTrace();
			}

			@Override
			public void finish() throws Exception {
				if (ok) {
					// Main.getInstance().enterMainScene();
					// 页面加载完毕，控件置为可用
					stopBitComboBox.setDisable(false);
					if (stopBitdata.size() > 0) {
						// stopBitComboBox.getSelectionModel().selectFirst();

						String id = idText.getText().toString();
						if (null != id && !id.equals("")) {
							TblBalReg tblBalReg = BaseService.getInstance().getTblBalRegService().getById(id);
							if (null != tblBalReg) {
								Integer stopBit = tblBalReg.getStopBit();
								if (stopBit.equals(15) || stopBit == 15) {
									stopBitComboBox.getSelectionModel().select("1.5");
								} else {
									stopBitComboBox.getSelectionModel().select(stopBit + "");
								}
							}

						}

					}
				}
			}
		}.run();
	}

	/**
	 * 数据位初始化 dataBitComboBox;//数据位
	 */
	private void initdataBitComboBox() {
		// 下拉框 onChange事件
		new BackgroundRunner() {
			String prompt;
			boolean isError;
			boolean ok = false;

			@Override
			public void background() throws Exception {
				try {
					// 初始化service
					List<String> list = new ArrayList<String>();
					list.add("7");
					list.add("8");
					dataBitdata.clear();
					if (null != list && list.size() > 0) {
						for (String dataBit : list) {
							dataBitdata.add(dataBit);
						}
					}

					dataBitComboBox.setItems(dataBitdata);
					ok = true;
				} catch (Exception e) {
					showError("连接服务器失败");
					e.printStackTrace();

				}
			}

			public void showError(String prompt) {
				this.prompt = prompt;
				this.isError = true;
				runForeground();
			}

			@Override
			public void foreground() throws Exception {
				if (isError) {
					showErrorMessage(prompt);
				} else {
					showMessage(prompt);
				}
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
				e.printStackTrace();
			}

			@Override
			public void finish() throws Exception {
				if (ok) {
					// Main.getInstance().enterMainScene();
					// 页面加载完毕，控件置为可用
					dataBitComboBox.setDisable(false);
					if (dataBitdata.size() > 0) {
						// dataBitComboBox.getSelectionModel().selectFirst();

						String id = idText.getText().toString();
						if (null != id && !id.equals("")) {
							TblBalReg tblBalReg = BaseService.getInstance().getTblBalRegService().getById(id);
							if (null != tblBalReg) {
								Integer dataBit = tblBalReg.getDataBit();
								dataBitComboBox.getSelectionModel().select(dataBit + "");
							}

						}
					}
				}
			}
		}.run();
	}

	/**
	 * 波特率初始化 下拉框 baudComboBox;//波特率
	 */
	private void initBaudComboBox() {
		// 下拉框 onChange事件
		new BackgroundRunner() {
			String prompt;
			boolean isError;
			boolean ok = false;

			@Override
			public void background() throws Exception {
				try {
					// 初始化service
//					new BaseService.getInstance()();
					List<String> list = new ArrayList<String>();
					list.add("300");
					list.add("600");
					list.add("1200");
					list.add("2400");
					list.add("4800");
					list.add("9600");
					list.add("19200");
					list.add("38400");
					list.add("43000");
					list.add("56000");
					list.add("57600");
					list.add("115200");
					bauddata.clear();
					if (null != list && list.size() > 0) {
						for (String dataBit : list) {
							bauddata.add(dataBit);
						}
					}

					baudComboBox.setItems(bauddata);
					ok = true;
				} catch (Exception e) {
					showError("连接服务器失败");
					e.printStackTrace();

				}
			}

			public void showError(String prompt) {
				this.prompt = prompt;
				this.isError = true;
				runForeground();
			}

			@Override
			public void foreground() throws Exception {
				if (isError) {
					showErrorMessage(prompt);
				} else {
					showMessage(prompt);
				}
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
				e.printStackTrace();
			}

			@Override
			public void finish() throws Exception {
				if (ok) {
					// Main.getInstance().enterMainScene();
					// 页面加载完毕，控件置为可用
					baudComboBox.setDisable(false);
					if (bauddata.size() > 0) {
						// baudComboBox.getSelectionModel().selectFirst();
						String id = idText.getText().toString();
						if (null != id && !id.equals("")) {
							TblBalReg tblBalReg = BaseService.getInstance().getTblBalRegService().getById(id);
							if (null != tblBalReg) {
								Integer baud = tblBalReg.getBaud();
								baudComboBox.getSelectionModel().select(baud + "");
							}

						}
					}
				}
			}
		}.run();
	}

	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
}
