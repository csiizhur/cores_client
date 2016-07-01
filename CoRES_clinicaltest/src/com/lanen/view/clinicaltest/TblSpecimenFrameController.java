package com.lanen.view.clinicaltest;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.Specimen;
import com.lanen.model.clinicaltest.TblSpecimen;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.Sign;
import com.lanen.zero.main.Main;
import com.lanen.zero.main.MainFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class TblSpecimenFrameController  extends Application  implements Initializable {

	@FXML
	private TextField studyNoText;
	@FXML
	private TextField reqNoText;
	@FXML
	private TextField recDateText;
	// 动物信息表
	@FXML
	private TableView<Animal> animalTable;
	@FXML
	private TableColumn<Animal, Boolean> selectCol;
	@FXML
	private TableColumn<Animal, String> animalIdCol;
	@FXML
	private TableColumn<Animal, String> sexCol;
	@FXML
	private TableColumn<Animal, String> animalCodeCol;
	private static ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private Label totalLabel;//共多少只
	@FXML
	private static Label selectLabel;//选中多少只

	@FXML
	private TabPane tabPane;

	// 标本接收 表格
	@FXML
	private TableView<Specimen> specimenTable;
	private ObservableList<Specimen> data_specimenTable = FXCollections.observableArrayList();
	private ObservableList<Specimen> data_specimenTable2 = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Specimen, String> studyPlanNoCol;
	@FXML
	private TableColumn<Specimen, String> reqNoCol;
	@FXML
	private TableColumn<Specimen, String> animalIdCol_specimen;
	@FXML
	private TableColumn<Specimen, String> animalCodeCol_specimen;
	@FXML
	private TableColumn<Specimen, String> recDateCol;
	@FXML
	private TableColumn<Specimen, String> recTimeCol;
	@FXML
	private TableColumn<Specimen, String> testItemCol;
	@FXML
	private TableColumn<Specimen, String> specimenCodeCol;
	@FXML
	private TableColumn<Specimen, String> specimenKindCol;
	@FXML
	private TableColumn<Specimen, String> senderCol;
	@FXML
	private TableColumn<Specimen, String> receiverCol;

	@FXML
	private CheckBox bioChemCheckBox;
	@FXML
	private CheckBox hematCheckBox;
	@FXML
	private CheckBox bloodCoagCheckBox;
	@FXML
	private CheckBox urineCheckBox;

	// 高级查询
	@FXML
	private ComboBox<String> studyNoComboBox;
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	// private Map<String,TblStudyPlan> map_studyNoComboBox = new
	// HashMap<String,TblStudyPlan>();//课题编号，课题
	@FXML
	private ComboBox<String> reqNoComboBox;
	private ObservableList<String> data_reqNoComboBox = FXCollections.observableArrayList();
	private Map<String, TblClinicalTestReq> map_reqNoComboBox = new HashMap<String, TblClinicalTestReq>();
	@FXML
	private ComboBox<String> animalIdComboBox;
	private ObservableList<String> data_animalIdComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> testItemComboBox_select;
	@FXML
	private TextField specimenCodeText;
	@FXML
	private HBox recDateHBox;
	DatePicker recDatePane = null;
	@FXML
	private CheckBox recDateCheckBox;
	@FXML
	private AnchorPane anchorPane;// 高级查询面板
	@FXML
	private Button queryBtn;
	// 打印时用的参数
	private String recDate = "";
	private String studyNo = "";
	private String reqNo = "";
	private String animalId = "";
	private String testItemString = "";
	private String specimenCode ="";
	
	@FXML
	private Button printButton;

	// 全局临检申请
	private TblClinicalTestReq tblClinicalTestReq = null;
	// 存放该申请下的所有动物列表
	private static ObservableList<Animal> data_animalTable2 = FXCollections.observableArrayList();
	// 存放该申请下的所有的标本接收记录
	private static List<TblSpecimen> list_tblSpecimen = new ArrayList<TblSpecimen>();
	// 选择列表
	List<Integer> list = new ArrayList<Integer>();
	List<String> testItemList = new ArrayList<String>();
	// 右击菜单
	private ContextMenu contextMenu = new ContextMenu();

	private static TblSpecimenFrameController instance;
	public static TblSpecimenFrameController getInstance(){
		if(null == instance){
			instance = new TblSpecimenFrameController();
		}
		return instance;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		// 初始化动物信息表
		initAnimalTable();

		// 初始化标本接收 表格
		initSpecimenTable();

		// 初始化高级选项卡面板
		initQueryPane();
		tabPane.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number oldValue,
							Number newValue) {
						if (newValue.intValue() == 1) {
							// data_specimenTable.clear();
							// 查询按钮
							onQueryBtnAction(null);
						} else if (newValue.intValue() == 0) {
							updateSpecimenTableByTestItems(testItemList);
						}
					}
				});
	}

	/**加载数据
	 * @param tblClinicalTestReq
	 */
	public void loadData(TblClinicalTestReq tblClinicalTestReq){
		
		this.tblClinicalTestReq = tblClinicalTestReq;
		//1.选择tab0
		tabPane.getSelectionModel().selectFirst();
		//2.检测项目全不选
		bioChemCheckBox.setSelected(false);
		hematCheckBox.setSelected(false);
		bloodCoagCheckBox.setSelected(false);
		urineCheckBox.setSelected(false);
		//3.查询&打印  面板值清空
		recDatePane.getTextField().clear();
		recDateCheckBox.setSelected(false);
		recDateCheckBox.setVisible(false);
		testItemComboBox_select.getSelectionModel().clearSelection();
		specimenCodeText.clear();
		//4.动物表格值 清空
		data_animalTable.clear();
		
		if (null != tblClinicalTestReq) {
			studyNoText.setText(tblClinicalTestReq.getTblStudyPlan().getStudyNo());
			reqNoText.setText(tblClinicalTestReq.getReqNo() + "");
			recDateText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		// 更新 该申请下的动物列表（data_animalTable2 存放该申请下的所有动物）
		updateData_animalTable2(tblClinicalTestReq);
		// 更新该申请下的所有的标本接收记录
		updateList_tblSpecimen(tblClinicalTestReq);

		// 更新 标本接收 表格 的值
		updateSpecimenTable(tblClinicalTestReq);
		//5 。共  0 只选中0只
		totalLabel.setText("0");
		selectLabel.setText("0");
		
	}
	
	/**
	 * 初始化高级查询选项卡面板
	 */
	private void initQueryPane() {
		// 初始化接收日期
		initDatePane();
		// 初始化 课题编号 下拉框
		initStudyNoComboBox();
		// 初始化 临检申请编号 下拉框
		initReqNoComboBox();
		// 初始化 动物Id号 下拉框
		initAnimalIdComboBox();
	}

	/**
	 * 初始化 课题编号 下拉框
	 */
	private void initStudyNoComboBox() {
		studyNoComboBox.setItems(data_studyNoComboBox);
		updateStudyNoComboBox("");
		studyNoComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {// 选定某值
							// 临检申请编号 下拉框 可用
							reqNoComboBox.setDisable(false);
							// 更新临检申请编号 下拉框的值
							updateReqNoComboBox(newValue);

						} else {

							// 临检申请编号 下拉框 值清空， 且不能用
							reqNoComboBox.getSelectionModel().clearSelection();
							reqNoComboBox.setDisable(true);

							// 动物ID号 下拉框 值清空， 且不能用 ，
							animalIdComboBox.getSelectionModel().clearSelection();
							animalIdComboBox.setDisable(true);
						}
					}
				});
	}

	/**
	 * 更新申请编号下拉框的值
	 * 
	 * @param newValue
	 */
	private void updateReqNoComboBox(String studyNo) {
		data_reqNoComboBox.clear();
		if (null == studyNo || studyNo.isEmpty()) {
			return;
		}
		TblStudyPlan tblStudyPlan = new TblStudyPlan();
		tblStudyPlan.setStudyNo(studyNo);

		List<TblClinicalTestReq> list = BaseService.getTblClinicalTestReqService().//
				getSetByStudyPlan(tblStudyPlan);
		if (null != list && list.size() > 0) {
			data_reqNoComboBox.add("");
			for (TblClinicalTestReq obj : list) {
				if (obj.getEs() == 1) {
					data_reqNoComboBox.add(obj.getReqNo() + "");
					map_reqNoComboBox.put(obj.getReqNo() + "", obj);
				}
			}
		}
		reqNoComboBox.setItems(data_reqNoComboBox);
	}

	/**
	 * 接收日期 多选框 onAction 事件
	 */
	@FXML
	private void onRecDateCheckBox(ActionEvent event) {
		if (!recDateCheckBox.isSelected()) {// 没被选中
			// 选择日期清空
			recDatePane.getTextField().setText("");

			recDateCheckBox.setVisible(false);
		}
	}

	/**
	 * 更新 课题编号 下拉框 的值
	 * 
	 * @param recDate
	 *            yyyy-MM-dd
	 */
	private void updateStudyNoComboBox(String recDate) {

		// 清空
		data_studyNoComboBox.clear();

		if (null == recDate || recDate.isEmpty()) {
			// 无接收日期，专题编号下拉框 可录
			studyNoComboBox.setEditable(true);
			return;
		}
		// 有接收日期，专题编号下拉框 不 可录
		studyNoComboBox.setEditable(false);
		// 查询 专题编号列表，
		List<String> studyNoList = BaseService.getTblSpecimenService().getStudyNoListByRecDate(
				recDate);
		// 赋值
		if (null != studyNoList && studyNoList.size() > 0) {
			data_studyNoComboBox.add("");
			for (String obj : studyNoList) {
				data_studyNoComboBox.add(obj);
			}
		}
		// 更新
		studyNoComboBox.setItems(data_studyNoComboBox);
	}

	/**
	 * 初始化 临检申请编号 下拉框
	 */
	private void initReqNoComboBox() {
		reqNoComboBox.setItems(data_reqNoComboBox);
		reqNoComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {
							// 动物ID号 下拉框 可用
							animalIdComboBox.setDisable(false);
							// 更新动物ID号 下拉框的值
							updateAnimalIdComboBox(newValue);
						} else {
							// 动物ID号 下拉框 值清空， 且不可用 ，
							animalIdComboBox.getSelectionModel().clearSelection();
							animalIdComboBox.setDisable(true);
						}
					}

				});
	}

	/**
	 * 更新动物Id号 下拉框 的值
	 * 
	 * @param newValue
	 */
	private void updateAnimalIdComboBox(String reqNo) {
		data_animalIdComboBox.clear();
		if (null == reqNo || reqNo.isEmpty()) {
			return;
		}
		TblClinicalTestReq tblClinicalTestReq = map_reqNoComboBox.get(reqNo);
		// Set<TblClinicalTestReqIndex2>
		// set=tblClinicalTestReq.getTblClinicalTestReqIndex2s();
		// 懒加载问题
		List<TblClinicalTestReqIndex2> list = BaseService.getTblClinicalTestReqService().//
				getTblClinicalTestReqIndex2ListByTblClinicalTestReq(tblClinicalTestReq);
		if (null != list && list.size() > 0) {
			// List<TblClinicalTestReqIndex2> list=new
			// ArrayList<TblClinicalTestReqIndex2>(set);
			for (TblClinicalTestReqIndex2 obj : list) {
				data_animalIdComboBox.add(obj.getAnimalId());
			}
		}
		animalIdComboBox.setItems(data_animalIdComboBox);

	}

	/**
	 * 初始化 动物Id号 下拉框
	 */
	private void initAnimalIdComboBox() {
		animalIdComboBox.setItems(data_animalIdComboBox);
	}

	/**
	 *  更新 标本接收表 的值(查询数据库)
	 * @param tblClinicalTestReq
	 */
	private void updateSpecimenTable(TblClinicalTestReq tblClinicalTestReq) {
		data_specimenTable.clear();
		data_specimenTable2.clear();
		List<?> mangItemList = BaseService.getTblSpecimenService().findListByTblClinicalTestReq(tblClinicalTestReq);
//		List<TblSpecimen> list = BaseService.getTblSpecimenService().findByTblClinicalTestReq(
//				tblClinicalTestReq);
		if (null != mangItemList && mangItemList.size() > 0) {
			String studyNo = "";
			String reqNo ="";
			String animalId ="";
			String animalCode ="";
			Date recDate =null;
			String recDateStr ="";
			Date recTime =null ;
			String recTimeStr="";
			int testItem =0;
			String testItemStr = "";
			String specimenCode ="";
			String specimenKind ="";
			String sender ="";
			String receiver ="";
			for (Object obj : mangItemList) {
				Object[] objs = (Object[]) obj;
				studyNo = (String) objs[0];
				reqNo =(Integer) objs[1]+"";
				animalId =(String) objs[2];
				animalCode =(String) objs[3];
				recDate =(Date) objs[4];
				recDateStr =DateUtil.dateToString(recDate, "yyyy-MM-dd");
				recTime =(Date) objs[5];
				recTimeStr=DateUtil.dateToString(recTime, "HH:mm");
				testItem =(Integer) objs[6];
				testItemStr = "";
				specimenCode =(String) objs[7];
				specimenKind =(String) objs[8];
				sender =(String) objs[9];
				receiver =(String) objs[10];
				
				switch (testItem) {
				case 1:
					testItemStr = "生化检验";
					break;
				case 2:
					testItemStr = "血液检验";
					break;
				case 3:
					testItemStr = "血凝检验";
					break;
				case 4:
					testItemStr = "尿液检验";
					break;
				default:
					break;
				}
				data_specimenTable.add(new Specimen(studyNo, reqNo + "", animalId, animalCode,
						recDateStr,recTimeStr, testItemStr, specimenCode, specimenKind,sender,receiver));
				data_specimenTable2.add(new Specimen(studyNo, reqNo + "", animalId, animalCode,
						recDateStr,recTimeStr, testItemStr, specimenCode, specimenKind,sender,receiver));
			}// end for
			specimenTable.setItems(data_specimenTable);
		}// end if

	}
	/**
	 *  更新 标本接收表 的值(查询数据库)(查询按钮用)
	 * @param tblClinicalTestReq
	 */
	private void updateSpecimenTable(String studyNo2 ,String reqNo2,String animalId2,
			String testItemString2 ,String specimenCode2 ,String recDate2){
		data_specimenTable.clear();
		List<?> mangItemList = BaseService.getTblSpecimenService().findListByCondition(studyNo2,reqNo2,
				animalId2,testItemString2,specimenCode2,recDate2);
//		List<TblSpecimen> list = BaseService.getTblSpecimenService().findByTblClinicalTestReq(
//				tblClinicalTestReq);
		if (null != mangItemList && mangItemList.size() > 0) {
			String studyNo = "";
			String reqNo ="";
			String animalId ="";
			String animalCode ="";
			Date recDate =null;
			String recDateStr ="";
			Date recTime =null ;
			String recTimeStr="";
			int testItem =0;
			String testItemStr = "";
			String specimenCode ="";
			String specimenKind ="";
			String sender ="";
			String receiver ="";
			for (Object obj : mangItemList) {
				Object[] objs = (Object[]) obj;
				studyNo = (String) objs[0];
				reqNo =(Integer) objs[1]+"";
				animalId =(String) objs[2];
				animalCode =(String) objs[3];
				recDate =(Date) objs[4];
				recDateStr =DateUtil.dateToString(recDate, "yyyy-MM-dd");
				recTime =(Date) objs[5];
				recTimeStr=DateUtil.dateToString(recTime, "HH:mm");
				testItem =(Integer) objs[6];
				testItemStr = "";
				specimenCode =(String) objs[7];
				specimenKind =(String) objs[8];
				sender =(String) objs[9];
				receiver =(String) objs[10];
				
				switch (testItem) {
				case 1:
					testItemStr = "生化检验";
					break;
				case 2:
					testItemStr = "血液检验";
					break;
				case 3:
					testItemStr = "血凝检验";
					break;
				case 4:
					testItemStr = "尿液检验";
					break;
				default:
					break;
				}
				data_specimenTable.add(new Specimen(studyNo, reqNo + "", animalId, animalCode,
						recDateStr,recTimeStr, testItemStr, specimenCode, specimenKind,sender,receiver));
			}// end for
			specimenTable.setItems(data_specimenTable);
		}// end if
		
	}

	/**
	 * 更新specimenTable（不查数据库）
	 * @param list2   检验项目列表
	 */
	private void updateSpecimenTableByTestItems(List<String> list2) {
		data_specimenTable.clear();
		if (null != data_specimenTable2 && data_specimenTable2.size() > 0) {
			if (null == list2 || list2.size() < 1) {
				for (Specimen specimen : data_specimenTable2) {
					data_specimenTable.add(specimen);
				}
			} else {
				for (Specimen specimen : data_specimenTable2) {
					if (list2.contains(specimen.getTestItem())) {
						data_specimenTable.add(specimen);
					}
				}
			}
		}

	}

	// 清空 标本接收表
	private void clearSpecimenTable() {
		data_specimenTable.clear();
		specimenTable.setItems(data_specimenTable);

	}

	/**
	 * 初始化标本接收 表
	 */
	private void initSpecimenTable() {
		studyPlanNoCol
				.setCellValueFactory(new PropertyValueFactory<Specimen, String>("studyPlanNo"));
		reqNoCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>("reqNo"));
		animalIdCol_specimen.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"animalId"));
		animalCodeCol_specimen.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"animalCode"));
		recDateCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>("recDate"));
		recTimeCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>("recTime"));
		testItemCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>("testItem"));
		specimenCodeCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"specimenCode"));
		specimenKindCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"specimenKind"));
		receiverCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"receiver"));
		senderCol.setCellValueFactory(new PropertyValueFactory<Specimen, String>(
				"sender"));
		specimenTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		specimenTable.setItems(data_specimenTable);
		// 右击菜单项（删除）
		MenuItem menuItem_delete = new MenuItem("删除");

//		contextMenu.getItems().add(menuItem_delete);

		menuItem_delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Specimen specimen = specimenTable.getSelectionModel().getSelectedItem();
				if (null != specimen) {
					String studyNo = specimen.getStudyPlanNo();
					int reqNo = Integer.parseInt(specimen.getReqNo());
					String animalId = specimen.getAnimalId();
					String recDate = specimen.getRecDate() + " " + specimen.getRecTime();
					String specimenCode = specimen.getSpecimenCode();
					// 已经检测
					if (BaseService.getTblSpecimenService().isExistBySpecimen(studyNo, reqNo,
							animalId, recDate, specimenCode)) {
						// 不能删除
//						Alert2.create("该标本已检测，无法删除");
						Messager.showWarnMessage("该标本已检测，无法删除！");
						return;
					} else {
						// 可以删除
//						SignFrameWithReason signFrameWithReason = new SignFrameWithReason("编号:"
//								+ specimenCode + "动物Id：" + animalId + "确定删除吗？");
//						Stage stage = new Stage();
//						stage.initModality(Modality.APPLICATION_MODAL);
//						stage.setTitle("签字确认");
//						try {
//							signFrameWithReason.start(stage);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						Map<String,Object> map =Sign.openSignWithReason("签字确认", "编号:"
								+ specimenCode + "动物Id：" + animalId + "确定删除吗？");
						// 删除签字通过
						if ((boolean) map.get("isPass")) {
							// oldValue(原数据):课题编号： 申请编号 ： 日期： 检验项目： 检验编号：
							String oldValue = "课题编号：" + studyNo + " 申请编号  ：" + reqNo + "接收日期："
									+ recDate + " 检验项目：  " + specimen.getTestItem() + "  检验编号："
									+ specimenCode;
							// 表名（类名）
							String entityName = "TblSpecimen";
							// 数据Id
							String dataId = BaseService.getTblSpecimenService().getIdBySpecimen(
									studyNo, reqNo, animalId, recDate, specimenCode);
							// 操作人
//							String userName = SignFrameWithReason.getUser().getUserName();
							String userName = "";
							User signUser = (User) map.get("signUser");
							if(null != signUser){
								userName = signUser.getUserName();
							}

							InetAddress addr = null;
							try {
								addr = InetAddress.getLocalHost();
							} catch (UnknownHostException e) {
								e.printStackTrace();
							}
							// 操作计算机
							String computerName = addr.getHostName().toString();
							// 操作方式
							int operateMode = 2;// 表示删除
							// 操作原因
//							String reason = SignFrameWithReason.getReason();
							String reason = (String) map.get("reason");
							TblTrace trlTrace = new TblTrace();
							trlTrace.setTableName(entityName);// 表名
							trlTrace.setDataId(dataId);// 数据Id
							trlTrace.setOperateMode(operateMode);// 操作方式 2，删除
							trlTrace.setModifyReason(reason);// 原因
							trlTrace.setOldValue(oldValue);// 原数据
							trlTrace.setOperator(userName);// 操作人
							trlTrace.setHost(computerName);// 操作计算机

							// 保存数据修改痕迹(在service中 添加了时间和主键)
							BaseService.getTblTraceService().save(trlTrace);
							// 表格中删除 该行
							data_specimenTable.remove(specimenTable.getSelectionModel()
									.getSelectedIndex());
							specimenTable.getSelectionModel().clearSelection();
//							Alert.create("删除成功");
							Messager.showMessage("删除成功！");
						} else {// 未通过

						}
					}
				}
			}

		});
		// 右击菜单(在表格中实现)
		specimenTable.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				@SuppressWarnings("unchecked")
				TableView<Specimen> t = (TableView<Specimen>) e.getSource();
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.show(t, e.getScreenX(), e.getScreenY());
				} else {
					contextMenu.hide();
				}
			}
		});

	}

	/**
	 * 初始化动物信息表
	 */
	private void initAnimalTable() {
		data_animalTable.clear();
		selectCol.setCellValueFactory(new PropertyValueFactory<Animal, Boolean>("select"));
		selectCol
				.setCellFactory(new Callback<TableColumn<Animal, Boolean>, TableCell<Animal, Boolean>>() {

					@Override
					public TableCell<Animal, Boolean> call(TableColumn<Animal, Boolean> p) {
						return new CheckBoxTableCell_0<Animal, Boolean>();
					}
				});
		animalIdCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("animalId"));
		sexCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("sex"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal, String>("animalCode"));
		animalTable.setItems(data_animalTable);
		 animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<Animal> table = (TableView<Animal>) event.getSource();
					Animal obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						Animal index = new Animal(!obj.getSelect(), obj.getAnimalId(),
								obj.getSex(), obj.getAnimalCode());
						table.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
					}
					table.getSelectionModel().clearSelection();
				}

			}
		});
	}

	/**
	 * 更新 该申请下的所有动物列表
	 */
	private void updateData_animalTable2(TblClinicalTestReq tblClinicalTestReq) {
		data_animalTable2.clear();
		if (null != tblClinicalTestReq) {
			// Set<TblClinicalTestReqIndex2> set =
			// tblClinicalTestReq.getTblClinicalTestReqIndex2s();
			// 懒加载问题
			List<TblClinicalTestReqIndex2> list = BaseService.getTblClinicalTestReqService().//
					getTblClinicalTestReqIndex2ListByTblClinicalTestReq(tblClinicalTestReq);
			if (null != list && list.size() > 0) {
				// List<TblClinicalTestReqIndex2> list=new
				// ArrayList<TblClinicalTestReqIndex2>(set);
				for (TblClinicalTestReqIndex2 obj : list) {
					data_animalTable2.add(new Animal(false, obj.getAnimalId(),
							obj.getGender() == 1 ? "♂" : (obj.getGender() == 2 ? "♀" : ""), obj.getAnimalCode()));
				}

			}
		}
	}

	/**
	 * 更新该申请下的所有的标本接收记录
	 * 
	 * @param tblClinicalTestReq2
	 */
	private void updateList_tblSpecimen(TblClinicalTestReq tblClinicalTestReq2) {
		list_tblSpecimen.clear();
		if (null != tblClinicalTestReq2) {
			list_tblSpecimen = BaseService.getTblSpecimenService().findByTblClinicalTestReq(
					tblClinicalTestReq2);
		}

	}

	/**
	 * 标本接收面板 -- 4个检测项目 多选框 onAction 事件
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event) {

		list.clear();// 清空
		testItemList.clear();
		if (bioChemCheckBox.isSelected()) {
			list.add(1);
			testItemList.add("生化检验");
		}
		if (hematCheckBox.isSelected()) {
			list.add(2);
			testItemList.add("血液检验");
		}
		if (bloodCoagCheckBox.isSelected()) {
			list.add(3);
			testItemList.add("血凝检验");
		}
		if (urineCheckBox.isSelected()) {
			list.add(4);
			testItemList.add("尿液检验");
		}
		updateSpecimenTableByTestItems(testItemList);
		updateAnimalTable(list);

	}

	/**
	 * 更新动物表格
	 * 
	 * @param list
	 */
	private void updateAnimalTable(List<Integer> list) {

		data_animalTable.clear();// 清空
		totalLabel.setText(data_animalTable.size() + "");
		// list 检测项目列表(勾选的检测项目)
		// tblClinicalTestReq 临检申请
		// data_animalTable2 存放该申请下的所有动物列表
		// list_tblSpecimen 存放该申请下的所有的标本接收记录

		if (null == list || list.size() < 1) {// 选择为空
			return;
		}
		if (null == tblClinicalTestReq) {
			return;
		}
		if (null == data_animalTable2 || data_animalTable2.size() < 1) {
			return;
		}

		// 临检申请 检测列表
		// Set<TblClinicalTestReqIndex> set =
		// tblClinicalTestReq.getTblClinicalTestReqIndexs();
		// 懒加载问题
		List<TblClinicalTestReqIndex> list_TblClinicalTestReqIndex = BaseService
				.getTblClinicalTestReqService().//
				getReqIndexByReqNo(tblClinicalTestReq.getTblStudyPlan(),
						tblClinicalTestReq.getReqNo());
		if (null != list_TblClinicalTestReqIndex && list_TblClinicalTestReqIndex.size() > 0) {
			// List<TblClinicalTestReqIndex> list_TblClinicalTestReqIndex = new
			// ArrayList<TblClinicalTestReqIndex>(
			// set);
			boolean exist = false;
			for (Integer i : list) {
				exist = false;
				for (TblClinicalTestReqIndex obj : list_TblClinicalTestReqIndex) {
					if (i == obj.getTestitem()) {
						exist = true;
						continue;
					}
				}
				if (!exist) {// 勾选检测项目在 申请里不存在，
					return;
				}
			}

		} else {// 临检申请检测列表为空
			return;
		}

		if (null == list_tblSpecimen || list_tblSpecimen.size() < 1) {
			data_animalTable.addAll(data_animalTable2);
			totalLabel.setText(data_animalTable.size() + "");
			return;
		}
		boolean exist = false;
		for (Animal animal : data_animalTable2) {// 全部动物
			exist = false;// 不存在
			for (Integer i : list) {// 勾选项目
				for (TblSpecimen tblSpecimen : list_tblSpecimen) {
					if (i == tblSpecimen.getTestItem()
							&& animal.getAnimalId().equals(tblSpecimen.getAnimalId())) {
						exist = true;
					}
				}
			}
			if (!exist) {
				data_animalTable.add(new Animal(false, animal.getAnimalId(), animal.getSex(),
						animal.getAnimalCode()));
			}
		}

		totalLabel.setText(data_animalTable.size() + "");
	}
	/**
	 * 全选
	 */
	@FXML
	private void onSelectAll(){
		int i = 0;
		for(Animal  obj :data_animalTable){
			data_animalTable.set(i, new Animal(true,obj.getAnimalId(),obj.getSex(),obj.getAnimalCode()));
			i++;
		}
//		animalTable.getItems().set(table.getSelectionModel().getSelectedIndex(), index);
	}
	/**
	 * 全不选
	 */
	@FXML
	private void onSelectNone(){
		int i = 0;
		for(Animal  obj :data_animalTable){
			data_animalTable.set(i, new Animal(false,obj.getAnimalId(),obj.getSex(),obj.getAnimalCode()));
			i++;
		}
	}
	/**
	 * 点击确定按钮事件
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event) {
		String studyPlanNo = studyNoText.getText();
		String reqNo = reqNoText.getText().trim();
		int reqNoInt = Integer.valueOf(reqNo);
		String recDate = recDateText.getText();
		if ("".equals(studyPlanNo) || "".equals(reqNo) || "".equals(recDate)) {
//			Alert2.create("信息不完整");
			Messager.showWarnMessage("信息不完整！");
			return;
		}
		if (null == list || list.size() < 1) {
//			Alert2.create("请选择检测项目");
			Messager.showWarnMessage("请选择检测项目！");
			return;
		}
		int selected = getAnimalSelectedNumber();// 被选中的动物数
		if (selected < 1) {
//			Alert2.create("请选择标本的动物信息");
			Messager.showWarnMessage("请选择标本的动物信息！");
			return;
		}
		List<String> animalIdList = new ArrayList<String>();
		for (Animal obj : data_animalTable) {
			if (obj.getSelect()) {
				animalIdList.add(obj.getAnimalId());
			}
		}
//		List<String> list1 = null;// 生化 检验编号
		Map<String,String> map1= null;// animalId:specimenCode
		if (list.contains(1)) {
//			list1 = BaseService.getBillNoService().getMuchNextBillNo(1, selected);
			map1 = BaseService.getPoolSpecimenCodeService().getMuchNextSpecimenCode(studyPlanNo, reqNoInt, 1, animalIdList);
		}
//		List<String> list2 = null;// 血常规 检验编号
		Map<String,String> map2= null;// animalId:specimenCode
		if (list.contains(2)) {
//			list2 = BaseService.getBillNoService().getMuchNextBillNo(2, selected);
			map2 = BaseService.getPoolSpecimenCodeService().getMuchNextSpecimenCode(studyPlanNo, reqNoInt, 2, animalIdList);
		}
//		List<String> list3 = null;// 血凝 检验编号
		Map<String,String> map3= null;// animalId:specimenCode
		if (list.contains(3)) {
//			list3 = BaseService.getBillNoService().getMuchNextBillNo(3, selected);
			map3 = BaseService.getPoolSpecimenCodeService().getMuchNextSpecimenCode(studyPlanNo, reqNoInt, 3, animalIdList);
		}
//		List<String> list4 = null;// 尿常规 检验编号
		Map<String,String> map4= null;// animalId:specimenCode
		if (list.contains(4)) {
//			list4 = new ArrayList<String>();
//			String mostSpecimen = BaseService.getTblSpecimenService().getToDayMostCode();
//			int bit4 = Integer.parseInt(mostSpecimen.substring(0, 1));
//			bit4++;
//			if (bit4 < 10) {
//				bit4 = bit4 * 1000;
//				for (int i = 0; i < selected; i++) {
//					list4.add((bit4 + 1 + i) + "");
//				}
//			} else {
//				Alert.create("每天最多接收9批次尿常规标本");
//				return;
//			}
			map4 = BaseService.getPoolSpecimenCodeService().getMuchNextSpecimenCode(studyPlanNo, reqNoInt, 4, animalIdList);
		}
		// if(!"尿液检查".equals(testItem)){
		// //非 尿液检查 的检测编号
		// list=BaseService.getBillNoService().getMuchNextBillNo("DictBioChem",selected);
		// }else{//尿液检查 1001 4,代表检测项目为尿液检查
		// list=new ArrayList<String>();
		// String
		// mostSpecimen=BaseService.getTblSpecimenService().getToDayMostCode(studyPlanNo,Integer.parseInt(reqNo),4);
		// int bit4=Integer.parseInt(mostSpecimen.substring(0, 1));
		// bit4++;
		// bit4=bit4*1000;
		// for(int i=0;i<selected;i++){
		// list.add((bit4+1+i)+"");
		// }
		// }

		// 选择标本类型
		Stage stage = Main.stageMap.get("ChooseSpecKindFrame");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(MainFrame.mainWidow);
			stage.initStyle(StageStyle.UTILITY);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				ChooseSpecKindFrame.getInstance().start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Main.stageMap.put("SignFrameWithReason",stage);
		}
		ChooseSpecKindFrame.getInstance().loadData(list);
		stage.showAndWait();
		if (!ChooseSpecKindFrame.getInstance().isPass()) {
			return;
		}
		Map<Integer, String> specimenKindMap = ChooseSpecKindFrame.getInstance()
				.getSpecimenKindMap();

//		//送检人签字
//		SignFrame2 signFrame2 = new SignFrame2("");
//		Stage stage2 = new Stage();
//		stage2.initModality(Modality.WINDOW_MODAL);
//		stage2.setTitle("送检人签字");
//		try {
//			signFrame2.start(stage2);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		//签字通过
//		if(!"OK".equals(SignFrame2.getType())){
//			return;
//		}
		User signUser = Sign.openSignNoUserName("送检人签字", "");
		if(null == signUser){
			return;
		}
		
		ObservableList<Specimen> data_number = FXCollections.observableArrayList();// 传到签字确认界面
		Date currentDate = BaseService.getTblStudyPlanService().getCurrentDate();
		String recTime = DateUtil.dateToString(currentDate, "HH:mm");
		int i = 0;
		for (Animal obj : data_animalTable) {
			if (obj.getSelect()) {
				if (null != map1 && map1.size() > 0) {
					data_number.add(new Specimen(studyPlanNo, reqNo, obj.getAnimalId(), obj
							.getAnimalCode(), recDate, recTime, "生化检验", map1.get(obj.getAnimalId()),
							specimenKindMap.get(1),"",""));
				}
				if (null != map2 && map2.size() > 0) {
					data_number.add(new Specimen(studyPlanNo, reqNo, obj.getAnimalId(), obj
							.getAnimalCode(), recDate, recTime, "血液检验",  map2.get(obj.getAnimalId()),
							specimenKindMap.get(2),"",""));
				}
				if (null != map3 && map3.size() > 0) {
					data_number.add(new Specimen(studyPlanNo, reqNo, obj.getAnimalId(), obj
							.getAnimalCode(), recDate, recTime, "血凝检验",map3.get(obj.getAnimalId()),
							specimenKindMap.get(3),"",""));
				}
				if (null != map4 && map4.size() > 0) {
					data_number.add(new Specimen(studyPlanNo, reqNo, obj.getAnimalId(), obj
							.getAnimalCode(), recDate, recTime, "尿液检验",map4.get(obj.getAnimalId()),
							specimenKindMap.get(4),"",""));
				}
				i++;
				Map<Object,Object>  testItemMap = new HashMap<Object, Object>();
				testItemMap.put(1, "生化检验");
				testItemMap.put(2, "血液检验");
				testItemMap.put(3, "血凝检验");
				testItemMap.put(4, "尿液检验");
			}
		}

		// 选择标本类型
		Stage stage2 = Main.stageMap.get("TblSpecimenNumberFrameController");
		if(null == stage2){
			stage2 =new Stage();
			stage2.initOwner(MainFrame.mainWidow);
			stage2.initStyle(StageStyle.UTILITY);
			stage2.initModality(Modality.APPLICATION_MODAL);
			try {
				TblSpecimenNumberFrameController.getInstance().start(stage2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Main.stageMap.put("TblSpecimenNumberFrameController",stage2);
		}
		TblSpecimenNumberFrameController.getInstance().loadData(signUser.getRealName(),data_number);
		stage2.showAndWait();
		
		// 标本数据已经保存
		if (TblSpecimenNumberFrameController.getInstance().isPass()) {
			// Alert.create("签字确认成功");
			updateSpecimenTable(tblClinicalTestReq);
			// 更新该申请下的所有的标本接收记录
			updateList_tblSpecimen(tblClinicalTestReq);
			// 勾选清空
			list.clear();
			data_animalTable.clear();

			bioChemCheckBox.setSelected(false);
			hematCheckBox.setSelected(false);
			bloodCoagCheckBox.setSelected(false);
			urineCheckBox.setSelected(false);
		}

	}

	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		recDatePane = new DatePicker(Locale.CHINA);
		recDatePane.getTextField().setEditable(false);

		// 以下两事件为 清除日期
		recDatePane.getTextField().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				recDateCheckBox.setVisible(true);
				recDateCheckBox.setSelected(true);

			}
		});
		recDatePane.getTextField().textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				// 更新 课题编号 下拉框 的值
				updateStudyNoComboBox(newValue);
			}

		});
		anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				String recDateString = recDatePane.getTextField().getText();
				if ("".equals(recDateString)) {
					recDateCheckBox.setVisible(false);
				}
			}
		});

		recDatePane.getTextField().setMaxWidth(115);
		recDatePane.getTextField().setMinWidth(115);
		recDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		recDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		recDatePane.getCalendarView().setShowWeeks(false);
		recDatePane.getStylesheets()
				.add(Junit.class.getResource("DatePicker.css").toExternalForm());
		recDatePane.setMaxWidth(115);
		recDateHBox.getChildren().add(recDatePane);

	}

	/**
	 * 查询按钮事件
	 */
	@FXML
	private void onQueryBtnAction(ActionEvent event) {
		// 目的让 studyNoComboBox 失去焦点
		testItemComboBox_select.requestFocus();

		studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		reqNo = reqNoComboBox.getSelectionModel().getSelectedItem();
		animalId = animalIdComboBox.getSelectionModel().getSelectedItem();
		testItemString = testItemComboBox_select.getSelectionModel().getSelectedItem();
		specimenCode = specimenCodeText.getText().trim();
		recDate = recDatePane.getTextField().getText();
		// 一个条件都没选
		if ((null == studyNo || studyNo.isEmpty()) && (null == reqNo || reqNo.isEmpty())
				&& (null == animalId || animalId.isEmpty())
				&& (null == testItemString || testItemString.isEmpty()) && "".equals(specimenCode)
				&& "".equals(recDate)) {
			clearSpecimenTable();
		} else {
//			List<TblSpecimen> list = BaseService.getTblSpecimenService().findByCondition(studyNo,
//					reqNo, animalId, testItemString, specimenCode, recDate);
			updateSpecimenTable(studyNo,
					reqNo, animalId, testItemString, specimenCode, recDate);
		}
		if (null != recDate && !recDate.isEmpty() && data_specimenTable.size() > 0) {
			printButton.setDisable(false);
		} else {
			printButton.setDisable(true);
		}
	}

	/**打印
	 * @param event
	 */
	@FXML
	private void onPrintButton(ActionEvent event) {
		// TODO
		if (null != recDate && !recDate.isEmpty() && data_specimenTable.size() > 0) {

			JasperReport jr = null;
			JasperPrint jp = null;
			try {
				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
						"tblspecimen.jrxml"));
			} catch (JRException e) {
				e.printStackTrace();
//				Alert2.create("报表加载失败");
				Messager.showWarnMessage("报表加载失败！");
				return;
			}

			URL logoImage = getClass().getResource("/image/clinicaltest/logo.jpg");
			/** 编号 */
			String number = BaseService.getDictReportNumberService().getNumberByReportName("临床检验标本接收记录表");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("logoImage", logoImage);
			map.put("number", number);
			map.put("recDate", recDate);
			
			Map<Object,Object>  testItemMap = new HashMap<Object, Object>();
			testItemMap.put(1, "生化检验");
			testItemMap.put(2, "血液检验");
			testItemMap.put(3, "血凝检验");
			testItemMap.put(4, "尿液检验");
			
			List<?> list = BaseService.getTblSpecimenService().findList(studyNo,
					reqNo, animalId, testItemString, specimenCode, recDate);
			Map<String,Boolean> bigAnimalStudyNo = new HashMap<>();
			List<Map<String,Object>> dataMapList = new ArrayList<Map<String,Object>>();
			Map<String,Object> dataMap = null;
			if(null != list){
				int i = 1;
				for(Object obj :list){
					Object[] objs = (Object[]) obj;
					dataMap = new HashMap<String,Object>();
					dataMap.put("sn", i+"");
					dataMap.put("studyPlanNo", objs[0]);
					boolean isBigAnimal = false;
					if(bigAnimalStudyNo.get((String)objs[0])==null)
					{
						TblStudyPlan tblStudyPlan = BaseService.getTblStudyPlanService().getById((String)objs[0]);
					    isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(tblStudyPlan.getAnimalType());
					    bigAnimalStudyNo.put((String)objs[0], isBigAnimal);
					}else
					{
						isBigAnimal = bigAnimalStudyNo.get((String)objs[0]);
					}
					if(!isBigAnimal&&null!=objs[2]&&!"".equals(objs[2])){
						 dataMap.put("animalId","NA");
					 }else{
						 //动物Id号
						 dataMap.put("animalId", objs[1]);
					 }
					if(null!=objs[2]&&!"".equals(objs[2]))
						dataMap.put("animalCode", objs[2]);
					else
						dataMap.put("animalCode", "NA");
					
					//dataMap.put("animalId", objs[1]);
					//dataMap.put("animalCode", objs[2]);
					Date date = (Date) objs[3];
					String recTimeStr = DateUtil.dateToString(date, "HH:mm");
					dataMap.put("recTime",recTimeStr);
					dataMap.put("testItem",testItemMap.get(objs[4]));
					dataMap.put("specimenCode", objs[5]);
					dataMap.put("receiver", objs[6]);
					dataMap.put("sender", objs[7]);
					dataMapList.add(dataMap);
					i++;
				}
			}
			try {
				jp = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(
						dataMapList));
			} catch (JRException e) {
				e.printStackTrace();
//				Alert2.create("报表加载失败");
				Messager.showWarnMessage("报表加载失败！");
				return;
			}
			MainFrame.getInstance().openJFrame(jp, "标本接收记录表");
		} else {

		}
	}

	/**
	 * 类,动物列表
	 * 
	 * @author Administrator
	 * 
	 */
	public class Animal {

		private BooleanProperty select;
		private StringProperty animalId;
		private StringProperty sex;
		private StringProperty animalCode;

		public Animal() {
		}

		public Animal(boolean select, String animalId, String sex, String animalCode) {
			this.select = new SimpleBooleanProperty(select);
			this.animalId = new SimpleStringProperty(animalId);
			this.sex = new SimpleStringProperty(sex);
			this.animalCode = new SimpleStringProperty(animalCode);
//			this.select.addListener(new ChangeListener<Boolean>() {
//
//				@Override
//				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
//						Boolean newValue) {
//			});
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

		public String getAnimalId() {
			return animalId.get();
		}

		public void setAnimalId(String animalId) {
			this.animalId = new SimpleStringProperty(animalId);
		}

		public String getSex() {
			return sex.get();
		}

		public void setSex(String sex) {
			this.sex = new SimpleStringProperty(sex);
		}

		public String getAnimalCode() {
			return animalCode.get();
		}

		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
	}

	// 在单元格里创建多选框
	public static class CheckBoxTableCell_0<S, T> extends TableCell<S, T> {
		private final CheckBox checkBox;
		private ObservableValue<T> ov;

		public CheckBoxTableCell_0() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
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
				if (checkBox.isSelected()) {
					// getTableRow().getStylesheets().add("willPayRow");
					getTableRow().setStyle("-fx-background-color:palegreen ;");
					
					updateSelectLabel();
				} else {
					getTableRow().setStyle("");
					updateSelectLabel();
				}
			}
		}

	}
	/**
	 * 更新选中动物数 selectLabel
	 */
	private static void updateSelectLabel() {
		int selectedNumber = 0;
		for(Animal animal:data_animalTable){
			if(animal.getSelect()){
				selectedNumber++;
			}
		}
		selectLabel.setText(selectedNumber+"");
	}

	// 统计被选中的动物树
	public static int getAnimalSelectedNumber() {
		int num = 0;
		if (data_animalTable.size() > 0) {

			for (int i = 0; i < data_animalTable.size(); i++) {
				if (data_animalTable.get(i).getSelect()) {
					num++;
				}
			}
		}
		return num;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("TblSpecimenFrame.fxml"));
		Scene scene =new Scene(root,780,565);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setScene(scene);
		stage.setTitle("标本接收");
		stage.setMinWidth(760);
		stage.setMinHeight(575);
//		stage.setResizable(false);
		stage.show();
		
	}
}
