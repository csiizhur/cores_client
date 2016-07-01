package com.lanen.zero.main;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.ClinicalTestData;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.studyplan.DictBioChem;
import com.lanen.model.studyplan.DictBloodCoag;
import com.lanen.model.studyplan.DictHemat;
import com.lanen.model.studyplan.DictUrine;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanCollectionDataSource;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.clinicaltest.AuditFrame;
import com.lanen.view.clinicaltest.DataAcceptESFrame;
import com.lanen.view.clinicaltest.DataAcceptFrameController;
import com.lanen.view.clinicaltest.DictInstrumentFrame;
import com.lanen.view.clinicaltest.PassagewayFrame;
import com.lanen.view.clinicaltest.TblInstrumentVerificationFrame;
import com.lanen.view.clinicaltest.TblSpecimenFrameController;
import com.lanen.view.clinicaltest.TempTaskFrame;
import com.lanen.view.sign.Sign;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class MainFrameController implements Initializable {
	
	@FXML
	private MenuItem tempTaskMenu;
	@FXML
	private MenuItem instrumentMenu;
	@FXML
	private MenuItem passagewayMenu;
	@FXML
	private MenuItem instrumentVerificatiMenu;
	@FXML
	private MenuItem alterPasswordMenu;
	@FXML
	private MenuItem auditMenu;//  审计
	@FXML
	private MenuItem exitMenu;
	@FXML
	private MenuItem aboutMenu;
	@FXML
	private Button menuBtn1;
	@FXML
	private Button menuBtn2;
	@FXML
	private Button menuBtn3;
	@FXML
	private Button menuBtn4;
	@FXML
	private Button menuBtn5;
	@FXML
	private Button menuBtn6;
	
	@FXML
	private ComboBox<String> yearComboBox;
	private ObservableList<String> data_yearComboBox=FXCollections.observableArrayList() ;
	@FXML
	private TreeView<String> clinicalTestReqTree;//临检树
	private TreeItem<String> rootNode=new TreeItem<String>();//clinicalTestReqTree 根节点
	private Map<String,TblStudyPlan> studyPlanMap=new HashMap<String,TblStudyPlan>(); //  存放树 节点对应值     
	private Map<String,TblClinicalTestReq> clinicalTestReqMap = new HashMap<String,TblClinicalTestReq>();//存放叶子节点对应值 
	
	@FXML
	private TreeView<String> clinicalTestReqTree2;//临检树2（按日期）
	private TreeItem<String> rootNode2=new TreeItem<String>();//clinicalTestReqTree2 根节点
	private Map<String,TblClinicalTestReq> clinicalTestReqMap2 = new HashMap<String,TblClinicalTestReq>();//存放叶子节点对应值 
	
	//是否显示临时任务
	private  boolean showTempTask=false;
	@FXML
	private CheckBox showTempTaskCheckBox1;
	//是否显示验证试验
	private boolean showValidation=false;
	@FXML
	private CheckBox showValidationCheckBox;
	
	
	
	@FXML
	private TextField  studyNoText;            //课题编号
	@FXML
	private TextField dictStudyTypeText;//课题类别     类
	@FXML
	private TextField studydirectorText;       //课题负责人
	@FXML
	private TextField isGLPText;                  //是否GLP  
	@FXML
	private TextField animalTypeText;          //动物种类
	@FXML
	private TextField animalStrainText;        //动物品系
	@FXML
	private TextField dosageUnitText;          //剂量单位
	@FXML
	private TextField clinicalTestDirectorText;//临检负责人
	
	@FXML
	private Label currentUserLabel;       //当前用户
	@FXML
	private Label studyNoLabel;           //课题编号
	@FXML
	private Label studydirectorLabel;     //SD
	
	@FXML
	private ListView<String> hematList;           //血常规检查  List
	private ObservableList<String> data_hematList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> bloodCoagList;           //凝血 检查  List
	private ObservableList<String> data_bloodCoagList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> bioChemList;           //生化检查  List
	private ObservableList<String> data_bioChemList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> urineList;           //尿常规检查  List
	private ObservableList<String> data_urineList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> animalList;           //动物  List
	private ObservableList<String> data_animalList= FXCollections.observableArrayList();
	
	@FXML
	private TextField createDateText_clinicalTest;
	@FXML
	private TextField reqNoText_clinicalTest;
	@FXML
	private TextField beginDateText_clinicalTest;
	@FXML
	private TextField endDateText_clinicalTest;
	@FXML
	private Button specimenBtn;//标本接收
	
	//打印
	@FXML
	private Button printClinicalTestReqBtn;//打印按钮(临检申请单)
	@FXML
	private Button printTestDataBtn;//打印按钮（检测结果A4）
	
	@FXML
	private Button dataConfirmBtn;//数据确认按钮
	
	
	@FXML
	private TabPane clinicalTestReqTabPane;//临检申请单/检测结果面板
	private int currentTabNo = 0;//临检申请单
	
	
	@FXML
	private TabPane clinicalTestDataTabPane;//检测结果面板
	 
	//检测结果    面板
	
	@FXML
	private Label esNumberLabel;//已签字检测数量指标
	
	@FXML
	private ListView<String> animalList2;           //动物  List2(检测数据标签上)
	private ObservableList<String> data_animalList2= FXCollections.observableArrayList();
	private Map<String,TblClinicalTestReqIndex2> map_animalList2 =new HashMap<String,TblClinicalTestReqIndex2>();
	
	//打印检测结果   动物排序用
	private Map<String,Integer> dataIdAniSerialNumMap = new HashMap<String,Integer>();
	//生化 表
	@FXML
	private TableView<ClinicalTestData> bioChemTable;
	private ObservableList<ClinicalTestData> data_bioChemTable=FXCollections.observableArrayList();
	private ObservableList<ClinicalTestData> data_bioChemTable2=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalTestData,String> dataIdCol;             //数据Id
	@FXML
	private TableColumn<ClinicalTestData,String> tblSpecimenIdCol;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestData,String> studyNoCol;            //课题编号
	@FXML
	private TableColumn<ClinicalTestData,String> reqNoCol;              //申请编号
	@FXML
	private TableColumn<ClinicalTestData,String> animalIdCol;           //动物Id
	@FXML
	private TableColumn<ClinicalTestData,String> animalCodeCol;         //动物编号
	@FXML
    private TableColumn<ClinicalTestData,String> specimenCodeCol;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestData,String> testItemCol;              //检验项目
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexCol;          //检验指标
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexAbbrCol;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestData,String> testDataCol;           //数据
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexUnitCol;//检验指标单位
	@FXML
    private TableColumn<ClinicalTestData,String> collectionModeCol;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestData,String> collectionTimeCol;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestData,String> acceptTimeCol;        //接收时间
    @FXML
    private TableColumn<ClinicalTestData,String> esCol;               //签字    0：未签字   1：已签字
    @FXML
    private TableColumn<ClinicalTestData,String> confirmFlagCol;      //1.第一次接收   2，第二次接收
    @FXML
    private TableColumn<ClinicalTestData,String> validFlagCol;      //有效性
	//血常规 表
	@FXML
	private TableView<ClinicalTestData> hematTable;
	private ObservableList<ClinicalTestData> data_hematTable=FXCollections.observableArrayList();
	private ObservableList<ClinicalTestData> data_hematTable2=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalTestData,String> dataIdCol2;             //数据Id
	@FXML
	private TableColumn<ClinicalTestData,String> tblSpecimenIdCol2;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestData,String> studyNoCol2;            //课题编号
	@FXML
	private TableColumn<ClinicalTestData,String> reqNoCol2;              //申请编号
	@FXML
	private TableColumn<ClinicalTestData,String> animalIdCol2;           //动物Id
	@FXML
	private TableColumn<ClinicalTestData,String> animalCodeCol2;         //动物编号
	@FXML
    private TableColumn<ClinicalTestData,String> specimenCodeCol2;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestData,String> testItemCol2;              //检验项目
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexCol2;          //检验指标
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexAbbrCol2;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestData,String> testDataCol2;           //数据
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexUnitCol2;//检验指标单位
	@FXML
    private TableColumn<ClinicalTestData,String> collectionModeCol2;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestData,String> collectionTimeCol2;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestData,String> acceptTimeCol2;        //接收时间
    @FXML
    private TableColumn<ClinicalTestData,String> esCol2;               //签字    0：未签字   1：已签字
    @FXML
    private TableColumn<ClinicalTestData,String> confirmFlagCol2;      //1.第一次接收   2，第二次接收
    @FXML
    private TableColumn<ClinicalTestData,String> validFlagCol2;      //有效性
    
	//血凝  表
	@FXML
	private TableView<ClinicalTestData> bloodCoagTable;
	private ObservableList<ClinicalTestData> data_bloodCoagTable=FXCollections.observableArrayList();
	private ObservableList<ClinicalTestData> data_bloodCoagTable2=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalTestData,String> dataIdCol3;             //数据Id
	@FXML
	private TableColumn<ClinicalTestData,String> tblSpecimenIdCol3;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestData,String> studyNoCol3;            //课题编号
	@FXML
	private TableColumn<ClinicalTestData,String> reqNoCol3;              //申请编号
	@FXML
	private TableColumn<ClinicalTestData,String> animalIdCol3;           //动物Id
	@FXML
	private TableColumn<ClinicalTestData,String> animalCodeCol3;         //动物编号
	@FXML
    private TableColumn<ClinicalTestData,String> specimenCodeCol3;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestData,String> testItemCol3;              //检验项目
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexCol3;          //检验指标
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexAbbrCol3;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestData,String> testDataCol3;           //数据
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexUnitCol3;//检验指标单位
	@FXML
    private TableColumn<ClinicalTestData,String> collectionModeCol3;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestData,String> collectionTimeCol3;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestData,String> acceptTimeCol3;        //接收时间
    @FXML
    private TableColumn<ClinicalTestData,String> esCol3;               //签字    0：未签字   1：已签字
    @FXML
    private TableColumn<ClinicalTestData,String> confirmFlagCol3;      //1.第一次接收   2，第二次接收
    @FXML
    private TableColumn<ClinicalTestData,String> validFlagCol3;      //有效性
	//尿常规表
	@FXML
	private TableView<ClinicalTestData> urineTable;
	private ObservableList<ClinicalTestData> data_urineTable=FXCollections.observableArrayList();
	private ObservableList<ClinicalTestData> data_urineTable2=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalTestData,String> dataIdCol4;             //数据Id
	@FXML
	private TableColumn<ClinicalTestData,String> tblSpecimenIdCol4;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestData,String> studyNoCol4;            //课题编号
	@FXML
	private TableColumn<ClinicalTestData,String> reqNoCol4;              //申请编号
	@FXML
	private TableColumn<ClinicalTestData,String> animalIdCol4;           //动物Id
	@FXML
	private TableColumn<ClinicalTestData,String> animalCodeCol4;         //动物编号
	@FXML
    private TableColumn<ClinicalTestData,String> specimenCodeCol4;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestData,String> testItemCol4;              //检验项目
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexCol4;          //检验指标
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexAbbrCol4;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestData,String> testDataCol4;           //数据
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexUnitCol4;//检验指标单位
	@FXML
    private TableColumn<ClinicalTestData,String> collectionModeCol4;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestData,String> collectionTimeCol4;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestData,String> acceptTimeCol4;        //接收时间
    @FXML
    private TableColumn<ClinicalTestData,String> esCol4;               //签字    0：未签字   1：已签字
    @FXML
    private TableColumn<ClinicalTestData,String> confirmFlagCol4;      //1.第一次接收   2，第二次接收
    @FXML
    private TableColumn<ClinicalTestData,String> validFlagCol4;      //有效性
	
	
	//按日期
	@FXML
	private ComboBox<String> dateComboBox;
	@FXML
	private Button queryBtn;
	@FXML
	private HBox beginDateHBox;
	@FXML
	private HBox endDateHBox;
	DatePicker  beginPane=null;
	DatePicker  endPane=null;
	@FXML
	private HBox hBox;
	
	//接收数据窗口用
//	private DataAcceptFrame dataAcceptFrame =new DataAcceptFrame();
//	private Stage stage=new Stage();
	//数据确认窗口用
//	public static Stage dataESStage = null ;
	
	//懒加载
	//临检申请-动物列表
	private List<TblClinicalTestReqIndex2> tblClinicalTestReqIndex2List =null;
	//临检申请-检验指标
	private List<TblClinicalTestReqIndex> tblClinicalTestReqIndexList =null;
	
	
	@Override
	public void initialize(URL url, ResourceBundle arg1) {
		//初始化主窗口
//		mainWindow=clinicalTestReqTree.getScene().getWindow();
		//当前用户
		currentUserLabel.setText(null!=SaveUserInfo.getUser() ? SaveUserInfo.getUser().getRealName() : "" );
		//初始化工具栏按钮
		initMenuBtn();
		//初始化菜单栏
		initMenu();
		
		//初始化  yearComboBox
		initYearComboBox();
		//初始化   临检申请树
		initClinicalTestReqTree();
		
		//初始化日期选择器
		initDatePane();
		//初始化   临检申请树2
		initClinicalTestReqTree2();
		// 初始化  临检申请单
		initClinicalTestReqindex();
		// 初始化  检测结果    标签
		initClinicalTestData();
		//初始化 dateComboBox
		initDateComboBox();
		
		//临检申请单、检测结果面板     切换事件
		initClinicalTestReqTab();
//		检测结果4 检测项目   
		initClinicalTestDataTabPane();
	}

	/**
	 * 临检申请单、检测结果面板     切换事件
	 */
	private void initClinicalTestReqTab() {
		currentTabNo = 0;
		clinicalTestReqTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			//
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newValue) {
				if(newValue!=null){
					if(newValue.getText().equals("检验结果")){
						currentTabNo = 1;
						if(bioChemTable.getItems()==null){
							bioChemTable.setItems(data_bioChemTable);
							urineTable.setItems(data_urineTable);
							bloodCoagTable.setItems(data_bloodCoagTable);
							hematTable.setItems(data_hematTable);
						}
					}else{
						currentTabNo = 0;
					}
				}
			}
			
		});
	}
	/**
	 * 检测结果4 检测项目    切换事件
	 */
	private void initClinicalTestDataTabPane() {
		clinicalTestDataTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			//
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newValue) {
				updateESNumberLabel();
			}
			
		});
	}

	/**
	 * 初始化菜单栏
	 */
	private void initMenu() {
		Node icon1 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/临时任务m.png")));
		Node icon2 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/设备登记m.png")));
		Node icon3 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/通道号设置m.png")));
		Node icon4 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/设备检定信息m.png")));
		Node icon5 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/修改密码m.png")));
		Node icon6 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/退出m.png")));
		Node icon7 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/关于m.png")));
		Node icon8 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/审计m.png")));
		
		tempTaskMenu.setGraphic(icon1);
		instrumentMenu.setGraphic(icon2);
		passagewayMenu.setGraphic(icon3);
		instrumentVerificatiMenu.setGraphic(icon4);
		alterPasswordMenu.setGraphic(icon5);
		exitMenu.setGraphic(icon6);
		aboutMenu.setGraphic(icon7);
		auditMenu.setGraphic(icon8);
	}


	/**
	 * 初始化工具栏按钮
	 */
	private void initMenuBtn() {
//		Node icon1 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/临时任务.png")));
//		menuBtn1.setGraphic(icon1);
//		Node icon2 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/设备登记.png")));
//		menuBtn2.setGraphic(icon2);
//		Node icon3 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/通道号设置.png")));
//		menuBtn3.setGraphic(icon3);
//		Node icon4 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/设备检定信息.png")));
//		menuBtn4.setGraphic(icon4);
//		Node icon5 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/修改密码.png")));
//		menuBtn5.setGraphic(icon5);
//		Node icon6 = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/退出.png")));
//		menuBtn6.setGraphic(icon6);
		menuBtn1.setTooltip(new Tooltip("临时任务"));
		menuBtn2.setTooltip(new Tooltip("设备登记"));
		menuBtn3.setTooltip(new Tooltip("通道号设置"));
		menuBtn4.setTooltip(new Tooltip("设备检定信息"));
		menuBtn5.setTooltip(new Tooltip("修改密码"));
		menuBtn6.setTooltip(new Tooltip("退出"));
		
	}


	/**
	 * 初始化   临检申请树2
	 */
	private void initClinicalTestReqTree2() {
		rootNode2.setValue("临检申请2");
		rootNode2.setExpanded(true);
		clinicalTestReqTree2.setRoot(rootNode2);
		clinicalTestReqTree2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable,
					TreeItem<String> oldValue, TreeItem<String> newValue) {
				//清空临检树的选中状态
				clinicalTestReqTree.getSelectionModel().clearSelection();
				if(null!=newValue){
					if(clinicalTestReqMap2.keySet().contains(newValue.getValue())){
						TblClinicalTestReq clinicalTestReq=clinicalTestReqMap2.get(newValue.getValue());
						TblStudyPlan studyPlan=clinicalTestReq.getTblStudyPlan();
						//更新课题信息区域
						updateAllText(studyPlan);
						//更新临检申请单 区域
						updateClinicalTestReq(clinicalTestReq);
						//更新    检测结果    面板
						updateTestResult(clinicalTestReq);
					}
				}
			}
			});
	}


	/**
	 * 初始化 dateComboBox    在 按日期面板
	 */
	private void initDateComboBox() {
		dateComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(newValue!=null&&!"".equals(newValue)){
					if("  7天".equals(newValue)){
						hBox.setVisible(true);//防止日期被编辑
						endPane.getTextField().setText("");
						beginPane.getTextField().setText(DateUtil.getDateAgo(7));
						endPane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
					}else if("  30天 ".equals(newValue)){
						hBox.setVisible(true);//防止日期被编辑
						endPane.getTextField().setText("");
						beginPane.getTextField().setText(DateUtil.getDateAgo(30));
						endPane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
					}else if("  90天".equals(newValue)){
						hBox.setVisible(true);//防止日期被编辑
						endPane.getTextField().setText("");
						beginPane.getTextField().setText(DateUtil.getDateAgo(90));
						endPane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
					}else if("  自定义".equals(newValue)){
						hBox.setVisible(false);//日期可以被编辑
//						endPane.getTextField().setText("");
//						beginPane.getTextField().setText("");
						return;
					}
					//更新课题信息区域     清空
					updateAllText(null);
					//更新临检申请单 区域   清空
					updateClinicalTestReq(null);
					//更新    检测结果    面板
					updateTestResult(null);
					
				}
			}
		});
		dateComboBox.getSelectionModel().clearAndSelect(0);
	}
		
	/**
	 * 更新 临检申请  树2
	 * @param beginDate
	 * @param endDate
	 */
	private void updateClinicalTestReqTree2(String beginDate, String endDate) {
		
		//清空临检树2
		rootNode2.getChildren().clear();
		clinicalTestReqMap2.clear();
		
		if(!beginDate.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")||!endDate.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
			return;
		}
		List<TblClinicalTestReq> clinicalTestReqList=BaseService.getTblClinicalTestReqService().findByDate(beginDate,endDate);
		if(null!=clinicalTestReqList && clinicalTestReqList.size()>0){//临检列表非空
			//日期列表
			List<String> dateList=new ArrayList<String>();
			TreeItem<String> depNode=null;
			for(TblClinicalTestReq obj:clinicalTestReqList){
				String dateString=DateUtil.dateToString(obj.getBeginDate(), "yyyy-MM-dd");
				//先判断是否为验证试验
				if(1==obj.getTblStudyPlan().getIsValidation()){
					if(!showValidation){//不     显示验证试验
						continue;
					}
				}
				
				if(1==obj.getTemp()){//临时任务的临检申请
					if(showTempTask){//显示临时任务
						if(!dateList.contains(dateString)){
							dateList.add(dateString);
							depNode=new TreeItem<String>(dateString);
							rootNode2.getChildren().add(depNode);
						}
						TreeItem<String> leaf=new TreeItem<String>(obj.getTblStudyPlan().getStudyNo()+":"+obj.getTblStudyPlan().getClient());
						depNode.getChildren().add(leaf);
						clinicalTestReqMap2.put(obj.getTblStudyPlan().getStudyNo()+":"+obj.getTblStudyPlan().getClient(), obj);
					}else{//不显示临时任务
						continue;
					}
				}else{//非临时任务
					if(obj.getEs() == 1){//临检申请已签字
						if(!dateList.contains(dateString)){
							dateList.add(dateString);
							depNode=new TreeItem<String>(dateString);
							rootNode2.getChildren().add(depNode);
						}
						TreeItem<String> leaf=new TreeItem<String>(obj.getTblStudyPlan().getStudyNo()+":"+obj.getReqNo());
						depNode.getChildren().add(leaf);
						clinicalTestReqMap2.put(obj.getTblStudyPlan().getStudyNo()+":"+obj.getReqNo(), obj);
					}
				}
			}
		}else{
//			Alert2.create("在"+beginDate+"与"+endDate+"之间没有临检申请");
		}
	}

	/**
	 * 初始化  临检申请单
	 */
	private void initClinicalTestReqindex() {
		hematList.setItems(data_hematList);
		bloodCoagList.setItems(data_bloodCoagList);
		bioChemList.setItems(data_bioChemList);
		urineList.setItems(data_urineList);
		animalList.setItems(data_animalList);
		
	}
	/**
	 * 初始化  检测结果  
	 */
	private void initClinicalTestData() {
		
		animalList2.setItems(data_animalList2);
		animalList2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					if("查看全部".equals(newValue.trim())){
						
						bioChemTable.setItems(data_bioChemTable);
						
						hematTable.setItems(data_hematTable);
						bloodCoagTable.setItems(data_bloodCoagTable);
						urineTable.setItems(data_urineTable);
					}else{
						TblClinicalTestReqIndex2 entity=map_animalList2.get(newValue);
						if(null!=entity){
							if(data_bioChemTable.size()>0){
								data_bioChemTable2.clear();
								for(ClinicalTestData obj:data_bioChemTable){
									if(obj.getAnimalId().equals(entity.getAnimalId())){
										data_bioChemTable2.add(obj);
									}
								}
								bioChemTable.setItems(data_bioChemTable2);
							}
							if(data_hematTable.size()>0){
								data_hematTable2.clear();
								for(ClinicalTestData obj:data_hematTable){
									if(obj.getAnimalId().equals(entity.getAnimalId())){
										data_hematTable2.add(obj);
									}
								}
								hematTable.setItems(data_hematTable2);
							}
							if(data_bloodCoagTable.size()>0){
								data_bloodCoagTable2.clear();
								for(ClinicalTestData obj:data_bloodCoagTable){
									if(obj.getAnimalId().equals(entity.getAnimalId())){
										data_bloodCoagTable2.add(obj);
									}
								}
								bloodCoagTable.setItems(data_bloodCoagTable2);
							}
							if(data_urineTable.size()>0){
								data_urineTable2.clear();
								for(ClinicalTestData obj:data_urineTable){
									if(obj.getAnimalId().equals(entity.getAnimalId())){
										data_urineTable2.add(obj);
									}
								}
								urineTable.setItems(data_urineTable2);
							}
							
						}
					}
				}else{
					
					bioChemTable.setItems(data_bioChemTable);
					hematTable.setItems(data_hematTable);
					bloodCoagTable.setItems(data_bloodCoagTable);
					urineTable.setItems(data_urineTable);
				}
				
				updateESNumberLabel();
				
			}});
		//初始化生化表
		 dataIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("dataId"));             //数据Id
		 tblSpecimenIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("tblSpecimenId"));  //标本接收登记Id
		 studyNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("studyNo"));            //课题编号
		 reqNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("reqNo"));              //申请编号
		 animalIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalId"));           //动物Id
		 animalCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalCode"));         //动物编号
	     specimenCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("specimenCode"));       //检验编号号
	     testItemCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testItem"));              //检验项目
	     testIndexCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndex"));          //检验指标
	     testIndexAbbrCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testData"));           //数据
	     testIndexUnitCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("acceptTime"));        //接收时间
	     esCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("confirmFlag"));      //1.第一次接收   2，第二次接收
	     validFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("validFlag"));      //有效性
	     esCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    				 if(getString().equals("√")){
	    					 getTableRow().setStyle("-fx-background-color:palegreen;");
	    				 }else{
	    					 getTableRow().setStyle("");
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
	     validFlagCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
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
	     testDataCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
	     bioChemTable.setItems(data_bioChemTable);
	     //初始化血常规表
	     dataIdCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("dataId"));             //数据Id
	     tblSpecimenIdCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("tblSpecimenId"));  //标本接收登记Id
	     studyNoCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("studyNo"));            //课题编号
	     reqNoCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("reqNo"));              //申请编号
	     animalIdCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalId"));           //动物Id
	     animalCodeCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalCode"));         //动物编号
	     specimenCodeCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("specimenCode"));       //检验编号号
	     testItemCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testItem"));              //检验项目
	     testIndexCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndex"));          //检验指标
	     testIndexAbbrCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testData"));           //数据
	     testIndexUnitCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("acceptTime"));        //接收时间
	     esCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("confirmFlag"));      //12.第一次接收   2，第二次接收
	     validFlagCol2.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("validFlag"));      //有效性
	     esCol2.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    				 if(getString().equals("√")){
	    					 getTableRow().setStyle("-fx-background-color:palegreen;");
	    				 }else{
	    					 getTableRow().setStyle("");
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
	     validFlagCol2.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
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
	     testDataCol2.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
	     hematTable.setItems(data_hematTable);
	     //初始化血凝表
	     dataIdCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("dataId"));             //数据Id
	     tblSpecimenIdCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("tblSpecimenId"));  //标本接收登记Id
	     studyNoCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("studyNo"));            //课题编号
	     reqNoCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("reqNo"));              //申请编号
	     animalIdCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalId"));           //动物Id
	     animalCodeCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalCode"));         //动物编号
	     specimenCodeCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("specimenCode"));       //检验编号号
	     testItemCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testItem"));              //检验项目
	     testIndexCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndex"));          //检验指标
	     testIndexAbbrCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testData"));           //数据
	     testIndexUnitCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("acceptTime"));        //接收时间
	     esCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("confirmFlag"));      //13.第一次接收   2，第二次接收
	     validFlagCol3.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("validFlag"));      //有效性
	     esCol3.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    				 if(getString().equals("√")){
	    					 getTableRow().setStyle("-fx-background-color:palegreen;");
	    				 }else{
	    					 getTableRow().setStyle("");
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
	     validFlagCol3.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
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
	     testDataCol3.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
	     
	     bloodCoagTable.setItems(data_bloodCoagTable);
	     //初始尿常规化表
	     dataIdCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("dataId"));             //数据Id
	     tblSpecimenIdCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("tblSpecimenId"));  //标本接收登记Id
	     studyNoCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("studyNo"));            //课题编号
	     reqNoCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("reqNo"));              //申请编号
	     animalIdCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalId"));           //动物Id
	     animalCodeCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalCode"));         //动物编号
	     specimenCodeCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("specimenCode"));       //检验编号号
	     testItemCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testItem"));              //检验项目
	     testIndexCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndex"));          //检验指标
	     testIndexAbbrCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testData"));           //数据
	     testIndexUnitCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("acceptTime"));        //接收时间
	     esCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("confirmFlag"));      //14.第一次接收   2，第二次接收
	     validFlagCol4.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("validFlag"));      //有效性
	     esCol4.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    				 if(getString().equals("√")){
	    					 getTableRow().setStyle("-fx-background-color:palegreen;");
	    				 }else{
	    					 getTableRow().setStyle("");
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
	     validFlagCol4.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
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
	     testDataCol4.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
	     urineTable.setItems(data_urineTable);
		
	}
	
	/**
	 * 更新临检申请单的值
	 */
	private void updateClinicalTestReq(TblClinicalTestReq tblClinicalTestReq){
		data_hematList.clear();
		data_bloodCoagList.clear();
		data_bioChemList.clear();
		data_urineList.clear();
		data_animalList.clear();
		
		 reqNoText_clinicalTest.setText("");
		 createDateText_clinicalTest.setText("");
		 beginDateText_clinicalTest.setText("");
		 endDateText_clinicalTest.setText("");
		 specimenBtn.setDisable(true);//标本接收按钮       置灰
		 printTestDataBtn.setDisable(true);//打印 按钮       置灰
		 printClinicalTestReqBtn.setDisable(true);;//
		 
		if(null!=tblClinicalTestReq){
			specimenBtn.setDisable(false);//标本接收按钮       置亮
			printTestDataBtn.setDisable(false);//打印 按钮     置亮
			printClinicalTestReqBtn.setDisable(false);;//
			reqNoText_clinicalTest.setText(tblClinicalTestReq.getReqNo()+"");
			createDateText_clinicalTest.setText(DateUtil.dateToString(tblClinicalTestReq.getCreateDate(), "yyyy-MM-dd"));
			beginDateText_clinicalTest.setText(DateUtil.dateToString(tblClinicalTestReq.getBeginDate(), "yyyy-MM-dd"));
			endDateText_clinicalTest.setText(DateUtil.dateToString(tblClinicalTestReq.getEndDate(), "yyyy-MM-dd"));
			
			//临检申请单-检验指标
			//Set<TblClinicalTestReqIndex> set =tblClinicalTestReq.getTblClinicalTestReqIndexs();
			//懒加载异常
			tblClinicalTestReqIndexList =BaseService.getTblClinicalTestReqService()//
					.getReqIndexByReqNo(tblClinicalTestReq.getTblStudyPlan(), tblClinicalTestReq.getReqNo());
			//TODO
			if(null!=tblClinicalTestReqIndexList&&tblClinicalTestReqIndexList.size()>0){
				//存放申请指标对应缩写
		   		List<String> bioChemTestIndexAbbrList = new ArrayList<String>();
		   		List<String> hematTestIndexAbbrList = new ArrayList<String>();
		   		List<String> bloodCoagTestIndexAbbrList = new ArrayList<String>();
		   		List<String> urineTestIndexAbbrList = new ArrayList<String>();
		   		for (TblClinicalTestReqIndex obj: tblClinicalTestReqIndexList) {
		   			if(obj.getTestitem()==1){
		   				bioChemTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   			if(obj.getTestitem()==2){
		   				hematTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}  
		   			if(obj.getTestitem()==3){
		   				bloodCoagTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   			if(obj.getTestitem()==4){
		   				urineTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   		}
				
				
				//List<TblClinicalTestReqIndex> list=new ArrayList<TblClinicalTestReqIndex>(set);
//				for(TblClinicalTestReqIndex obj:tblClinicalTestReqIndexList){
//					switch (obj.getTestitem()) {
//					case 1:
//						data_bioChemList.add(obj.getTestIndex()+"( "+obj.getTestIndexAbbr()+" )");
//						break;
//					case 2:
//						data_hematList.add(obj.getTestIndex()+"( "+obj.getTestIndexAbbr()+" )");
//						break;
//					case 3:
//						data_bloodCoagList.add(obj.getTestIndex()+"( "+obj.getTestIndexAbbr()+" )");
//						break;
//					case 4:
//						data_urineList.add(obj.getTestIndex()+"( "+obj.getTestIndexAbbr()+" )");
//						break;
//
//					default:
//						break;
//					}
//				}
		   	//获取生化指标
		   		List<DictBioChem> biochemList = BaseService.getDictBioChemService().getAll();
		   		List<DictHemat> hematList = BaseService.getDictHematService().getAll();
		   		List<DictBloodCoag> bloodCoagList = BaseService.getDictBloodCoagService().getAll();
		   		List<DictUrine> urineList = BaseService.getDictUrineService().getAll();
				
		   		for (DictBioChem obj: biochemList) {
		   			if(bioChemTestIndexAbbrList.contains(obj.getAbbr())){
		   				data_bioChemList.add(obj.getName()+"( "+obj.getAbbr()+" )");
		   			}
		   		}
		   		for (DictHemat obj: hematList) {
		   			if(hematTestIndexAbbrList.contains(obj.getAbbr())){
		   				data_hematList.add(obj.getName()+"( "+obj.getAbbr()+" )");
		   			}
		   		}
		   		for (DictBloodCoag obj: bloodCoagList) {
		   			if(bloodCoagTestIndexAbbrList.contains(obj.getAbbr())){
		   				data_bloodCoagList.add(obj.getName()+"( "+obj.getAbbr()+" )");
		   			}
		   		}
		   		for (DictUrine obj: urineList) {
		   			if(urineTestIndexAbbrList.contains(obj.getAbbr())){
		   				data_urineList.add(obj.getName()+"( "+obj.getAbbr()+" )");
		   			}
		   		}
			}
			
			//临检申请单-动物编号
			//Set<TblClinicalTestReqIndex2> animalSet=tblClinicalTestReq.getTblClinicalTestReqIndex2s();
			//懒加载异常 
			tblClinicalTestReqIndex2List = BaseService.getTblClinicalTestReqService().getTblClinicalTestReqIndex2ListByTblClinicalTestReq(tblClinicalTestReq);
			if(null!=tblClinicalTestReqIndex2List&&tblClinicalTestReqIndex2List.size()>0){
				//List<TblClinicalTestReqIndex2> list=new ArrayList<TblClinicalTestReqIndex2>(animalSet);
				for(TblClinicalTestReqIndex2 obj:tblClinicalTestReqIndex2List){
					data_animalList.add((obj.getAnimalCode()==null ? "    ":obj.getAnimalCode())+"  "+(obj.getGender()==1 ? "♂" :(obj.getGender()==2 ? "♀" :" "))+"  "+obj.getAnimalId());
				}
			}
		}
	}

	/**
	 * 更新检测结果的值
	 */
	private void updateTestResult(TblClinicalTestReq tblClinicalTestReq){
		//清空动物列表2
		data_animalList2.clear();
		map_animalList2.clear();
		//清空表格的值
		data_bioChemTable.clear();
		data_hematTable.clear();
		data_bloodCoagTable.clear();
		data_urineTable.clear();
		
		dataIdAniSerialNumMap.clear();
		
		//非空
		if(null!=tblClinicalTestReq){
			//临检申请单-动物编号
			//Set<TblClinicalTestReqIndex2> animalSet=tblClinicalTestReq.getTblClinicalTestReqIndex2s();
			//懒加载问题
			
//			List<TblClinicalTestReqIndex2> animlList = BaseService.getTblClinicalTestReqService()//
//					.getTblClinicalTestReqIndex2ListByTblClinicalTestReq(tblClinicalTestReq);
			if(null!=tblClinicalTestReqIndex2List&&tblClinicalTestReqIndex2List.size()>0){
				//List<TblClinicalTestReqIndex2> list=new ArrayList<TblClinicalTestReqIndex2>(animalSet);
				for(TblClinicalTestReqIndex2 obj:tblClinicalTestReqIndex2List){
					data_animalList2.add((obj.getAnimalCode()==null ? "    ":obj.getAnimalCode())+"    "+(obj.getGender()==1 ? "♂" :(obj.getGender()==2 ? "♀" :" "))+" "+obj.getAnimalId());
					map_animalList2.put((obj.getAnimalCode()==null ?  "    ":obj.getAnimalCode())+"    "+(obj.getGender()==1 ? "♂" :(obj.getGender()==2 ? "♀" :" "))+" "+obj.getAnimalId(), obj);
				}
				if(null!=data_animalList2&&data_animalList2.size()>1){
					data_animalList2.add("      查看全部");
				}
			}
			//检测结果列表 
			List<TblClinicalTestData> list =BaseService.getTblClinicalTestDataService().findByStudyNoReqNo(tblClinicalTestReq.getTblStudyPlan().getStudyNo(), tblClinicalTestReq.getReqNo());
			bioChemTable.setItems(null);
			urineTable.setItems(null);
			bloodCoagTable.setItems(null);
			hematTable.setItems(null);
			if(null!=list&&list.size()>0){
				for(TblClinicalTestData obj:list){
					//采集时间
					String collectionTime="";
					if(null!=obj.getCollectionTime()){
						collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "yyyyMMdd HH:mm:ss");
					}
					//接收时间
					String acceptTime="";
					if(null!=obj.getAcceptTime()){
						acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "yyyyMMdd HH:mm:ss");
					}
					ClinicalTestData clinicalTestData =  new ClinicalTestData(
							obj.getDataId(),
							obj.getTblSpecimen().getSpecimenId(),
							obj.getStudyNo(),
							obj.getReqNo(),
							obj.getAnimalId(),
							obj.getAnimalCode(),
							obj.getSpecimenCode(),
							"",//检查项目
							obj.getTestIndex(),
							obj.getTestIndexAbbr(),
							obj.getTestData(),
							obj.getTestIndexUnit(),
							obj.getCollectionMode()==1 ? "自动":"手动",
									collectionTime,
									acceptTime,
									obj.getEs()==0?"":"√",
											obj.getConfirmFlag()+"",
											obj.getValidFlag()==0?"":"√"
							);
					String testItemStr ="";
					switch (obj.getTestItem()) {
					//
					case 1:testItemStr ="血液生化检查";clinicalTestData.setTestItem(testItemStr);
							data_bioChemTable.add(clinicalTestData);
						break;
					case 2:testItemStr ="血液常规检查";clinicalTestData.setTestItem(testItemStr);
							data_hematTable.add(clinicalTestData);
						break;
					case 3:testItemStr ="凝血功能检查";clinicalTestData.setTestItem(testItemStr);
							data_bloodCoagTable.add(clinicalTestData);
						break;
					case 4:testItemStr ="尿液检查";clinicalTestData.setTestItem(testItemStr);
							data_urineTable.add(clinicalTestData);
						break;
					default:
						break;
					}
					dataIdAniSerialNumMap.put(obj.getDataId(),obj.getAniSerialNum());
				}
			}
			if(currentTabNo == 1){
				bioChemTable.setItems(data_bioChemTable);
				urineTable.setItems(data_urineTable);
				bloodCoagTable.setItems(data_bloodCoagTable);
				hematTable.setItems(data_hematTable);
			}
			
			
		}//end 临检申请非空
		
		updateESNumberLabel();
	}
	/**
	 * 更新检测结果的值(根据studyNo,reqNo)
	 */
	private void updateTestResult(String studyNo,int reqNo ){
		//清空动物列表2
		data_animalList2.clear();
		map_animalList2.clear();
		//清空表格的值
		data_bioChemTable.clear();
		data_hematTable.clear();
		data_bloodCoagTable.clear();
		data_urineTable.clear();
		
		dataIdAniSerialNumMap.clear();
		
		//非空
		if(null!=studyNo && !studyNo.isEmpty() && reqNo!=0){
			if(null!=tblClinicalTestReqIndex2List&&tblClinicalTestReqIndex2List.size()>0){
				for(TblClinicalTestReqIndex2 obj:tblClinicalTestReqIndex2List){
					data_animalList2.add((obj.getAnimalCode()==null ? "    ":obj.getAnimalCode())+"    "+(obj.getGender()==1 ? "♂" :(obj.getGender()==2 ? "♀" :""))+"  "+obj.getAnimalId());
					map_animalList2.put( (obj.getAnimalCode()==null ? "    ":obj.getAnimalCode())+"    "+(obj.getGender()==1 ? "♂" :(obj.getGender()==2 ? "♀" :""))+"  "+obj.getAnimalId(), obj);
				}
				if(null!=data_animalList2&&data_animalList2.size()>1){
					data_animalList2.add("      查看全部");
				}
			}
			//检测结果列表 
			List<TblClinicalTestData> list =BaseService.getTblClinicalTestDataService().findByStudyNoReqNo(studyNo,reqNo);
			bioChemTable.setItems(null);
			urineTable.setItems(null);
			bloodCoagTable.setItems(null);
			hematTable.setItems(null);
			if(null!=list&&list.size()>0){
				for(TblClinicalTestData obj:list){
					//采集时间
					String collectionTime="";
					if(null!=obj.getCollectionTime()){
						collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "HH:mm:ss");
					}
					//接收时间
					String acceptTime="";
					if(null!=obj.getAcceptTime()){
						acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "HH:mm:ss");
					}
					ClinicalTestData clinicalTestData =  new ClinicalTestData(
							obj.getDataId(),
							obj.getTblSpecimen().getSpecimenId(),
							obj.getStudyNo(),
							obj.getReqNo(),
							obj.getAnimalId(),
							obj.getAnimalCode(),
							obj.getSpecimenCode(),
							"",//检查项目
							obj.getTestIndex(),
							obj.getTestIndexAbbr(),
							obj.getTestData(),
							obj.getTestIndexUnit(),
							obj.getCollectionMode()==1 ? "自动":"手动",
									collectionTime,
									acceptTime,
									obj.getEs()==0?"":"√",
											obj.getConfirmFlag()+"",
											obj.getValidFlag()==0?"":"√"
							);
					String testItemStr ="";
					switch (obj.getTestItem()) {
					//
					case 1:testItemStr ="血液生化检查";clinicalTestData.setTestItem(testItemStr);
					data_bioChemTable.add(clinicalTestData);
					break;
					case 2:testItemStr ="血液常规检查";clinicalTestData.setTestItem(testItemStr);
					data_hematTable.add(clinicalTestData);
					break;
					case 3:testItemStr ="凝血功能检查";clinicalTestData.setTestItem(testItemStr);
					data_bloodCoagTable.add(clinicalTestData);
					break;
					case 4:testItemStr ="尿液检查";clinicalTestData.setTestItem(testItemStr);
					data_urineTable.add(clinicalTestData);
					break;
					default:
						break;
					}
					dataIdAniSerialNumMap.put(obj.getDataId(),obj.getAniSerialNum());
				}
			}
			if(currentTabNo == 1){
				bioChemTable.setItems(data_bioChemTable);
				urineTable.setItems(data_urineTable);
				bloodCoagTable.setItems(data_bloodCoagTable);
				hematTable.setItems(data_hematTable);
			}
			
			
		}//end 临检申请非空
		
		updateESNumberLabel();
	}
	
	/**更新 esNumberLabel 
	 * @param number
	 */
	private void updateESNumberLabel(){
		int selectedIndex = clinicalTestDataTabPane.getSelectionModel().getSelectedIndex();
		ObservableList<ClinicalTestData> items = null;
		switch (selectedIndex+1) {
		case 1:
			 items = bioChemTable.getItems();
			break;
		case 2:
			items = hematTable.getItems();
			break;
		case 3:
			 items = bloodCoagTable.getItems();
			break;
		case 4:
			items = urineTable.getItems();
			break;

		default:
			break;
		}
		if(null != items && items.size() > 1){
			int number = 0;
			for(ClinicalTestData obj:items){
				if(obj.getEs().equals("√")){
					number++;
				}
			}
			esNumberLabel.setText("（已确认 "+number+" 条）");
		}else{
			esNumberLabel.setText("");
		}
	}
	
	/**
	 * 初始化   临检申请树  
	 */
	private void initClinicalTestReqTree() {
		rootNode.setValue("临检申请");
		rootNode.setExpanded(true);
		clinicalTestReqTree.setRoot(rootNode);
		clinicalTestReqTree.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){

			@Override
			public TreeCell<String> call(TreeView<String> arg0) {
				return new TreeFormatCell();
			}});
		clinicalTestReqTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
					TreeItem<String> newValue) {
				//清空临检树2的选中状态
				clinicalTestReqTree2.getSelectionModel().clearSelection();
				
				
				if(null!=newValue&&!"".equals(newValue.getValue())){
					if(studyPlanMap.keySet().contains(newValue.getValue())){//选中的是课题编号
						
						//更新  课题信息  区域  的所有Text
						updateAllText(studyPlanMap.get(newValue.getValue()));
						//更新临检申请区域
						updateClinicalTestReq(null);
						//更新    检测结果    面板
						updateTestResult(null);
					}else {//选中的是临检申请
						//父节点值
						String parentValue=newValue.getParent().getValue().toString();
						if(!studyPlanMap.keySet().contains(parentValue)){
							parentValue =newValue.getParent().getParent().getValue().toString();
						}
						//更新  课题信息  区域  的所有Text
						updateAllText(studyPlanMap.get(parentValue));
						
							String newValueString=newValue.getValue().toString();
							if(newValueString.indexOf(":")>0){
								String reqNo=newValueString.substring(0,newValueString.indexOf(":"));
								if(parentValue.indexOf(":")>0){//临时任务节点
									//截取课题编号
									parentValue=parentValue.substring(0, parentValue.indexOf(":"));
								}
								String leafString=reqNo+":"+parentValue;
								//更新临检申请区域
								updateClinicalTestReq(clinicalTestReqMap.get(leafString));
								
								//更新    检测结果    面板
								updateTestResult(clinicalTestReqMap.get(leafString));
							}
					}
				}else{
					//更新  课题信息  区域  的所有Text置空
					updateAllText(null);
					//更新临检申请区域
					updateClinicalTestReq(null);
					//更新    检测结果    面板
					updateTestResult(null);
					
				}
			}});
	}
	/**
	 * 更新   临检申请树  的值    
	 */
	private void updateClinicalTestReqTree(String year){
		rootNode.getChildren().clear();
		studyPlanMap.clear();
		clinicalTestReqMap.clear();
		//查询试验计划列表
		List<TblStudyPlan> tblStudyPlanList=BaseService.getTblStudyPlanService().findWithYear(year);
//		List<TblStudyPlan> tblStudyPlanList=BaseService.getTblStudyPlanService().findStartStudyPlanWithYear(year);
		if(null!=tblStudyPlanList && tblStudyPlanList.size()>0){
			for(TblStudyPlan obj:tblStudyPlanList){
				TreeItem<String> depNode =null;
				//先判断是否为验证试验
				if(1==obj.getIsValidation()){
					if(!showValidation){//不     显示验证试验
						continue;
					}
				}
				
				//判断是否为临时任务
				if(1==obj.getTemp()){//是临时任务
					if(showTempTask){
						depNode = new TreeItem<String>(obj.getStudyNo()+":"+obj.getClient());//节点
						studyPlanMap.put(obj.getStudyNo()+":"+obj.getClient(), obj);
					}else{
						continue;
					}
				}else{//非临时任务
					if(!obj.getStudyState().equals("0")){//试验已启动
						depNode = new TreeItem<String>(obj.getStudyNo());//节点
						studyPlanMap.put(obj.getStudyNo(), obj);
					}else{
						continue;
					}
				}
//				depNode.setExpanded(true);   //展开
				//临检列表(已签字，已排序)
				List<TblClinicalTestReq> clinicalTestReqList = BaseService.getTblClinicalTestReqService().getSetByStudyPlan(obj);
				if(null!=clinicalTestReqList && clinicalTestReqList.size()>0){
					//添加节点（课题编号）
					rootNode.getChildren().add(depNode);
					int i = 0;
					int size = clinicalTestReqList.size();
					for(TblClinicalTestReq entity:clinicalTestReqList){
						if(entity.getParentReqNo() == 0){
							//临检申请    申请编号+":"+开始检查日期
							TreeItem<String> leaf =null;
							if(entity.getTemp()==2){
								leaf = new TreeItem<String>(entity.getReqNo()+":"+DateUtil.dateToString(entity.getBeginDate(), "yyyy-MM-dd")+" ");
							}else if(entity.getTemp()==3){
								leaf = new TreeItem<String>(entity.getReqNo()+":"+DateUtil.dateToString(entity.getBeginDate(), "yyyy-MM-dd")+"  ");
							}else{
								leaf = new TreeItem<String>(entity.getReqNo()+":"+DateUtil.dateToString(entity.getBeginDate(), "yyyy-MM-dd"));
							}
							depNode.getChildren().add(leaf);
							//Map key    申请编号+":"+课题编号[+:client]
							clinicalTestReqMap.put(entity.getReqNo()+":"+entity.getTblStudyPlan().getStudyNo(), entity);
							 for(int j = i+1; j<size ;j++){
								if(clinicalTestReqList.get(j).getParentReqNo() ==entity.getReqNo() ){
									//临检申请    申请编号+":"+开始检查日期
									TreeItem<String> subLeaf = new TreeItem<String>(clinicalTestReqList.get(j).getReqNo()+":"+DateUtil.dateToString(clinicalTestReqList.get(j).getBeginDate(), "yyyy-MM-dd"));
									leaf.getChildren().add(subLeaf);
									//Map key    申请编号+":"+课题编号[+:client]
									clinicalTestReqMap.put(clinicalTestReqList.get(j).getReqNo()+":"+clinicalTestReqList.get(j).getTblStudyPlan().getStudyNo(),clinicalTestReqList.get(j));
								} else{
									break;
								}
							 }
						}
						i++;
					}
				}
				
			}
		}
		
	}
	/**
	 * 显示临时任务CheckBox  的onAction事件
	 * @param event
	 */
	@FXML
	private void  onShowTempTaskCheckBoxAction(ActionEvent event){
		CheckBox checkBox=(CheckBox)event.getSource();
		if(checkBox.isSelected()){
			showTempTask=true;
			showTempTaskCheckBox1.setSelected(true);
		}else{
			showTempTask=false;
			showTempTaskCheckBox1.setSelected(false);
		}
		//更新临申请树1
		updateClinicalTestReqTree(yearComboBox.getSelectionModel().getSelectedItem());
		updateClinicalTestReqTree2(beginPane.getTextField().getText(),endPane.getTextField().getText());
	}
	/**
	 * 显示验证试验CheckBox  的onAction事件
	 * @param event
	 */
	@FXML
	private void  onShowValidationCheckBoxAction(ActionEvent event){
		CheckBox checkBox=(CheckBox)event.getSource();
		if(checkBox.isSelected()){
			showValidation=true;
			showValidationCheckBox.setSelected(true);
		}else{
			showValidation=false;
			showValidationCheckBox.setSelected(false);
		}
		//更新临申请树1
		updateClinicalTestReqTree(yearComboBox.getSelectionModel().getSelectedItem());
		//更新临申请树2
		updateClinicalTestReqTree2(beginPane.getTextField().getText(),endPane.getTextField().getText());
	}
	/**
	 * 初始化  yearComboBox
	 */
	private void initYearComboBox() {
		data_yearComboBox.clear();
//		SimpleDateFormat format=new SimpleDateFormat("yyyy");
//		String year =format.format(new Date());
//		int yearInt =Integer.parseInt(year);
//		data_yearComboBox.add(year);
//		yearInt--;
//		data_yearComboBox.add(yearInt+"");
//		data_yearComboBox.add(yearInt+"之前");
		yearComboBox.setItems(data_yearComboBox);
		List<Object> yearStrList = BaseService.getTblStudyPlanService().getYearStrList();
		if(null != yearStrList){
			for(Object year :yearStrList){
				data_yearComboBox.add(year.toString());
			}
		}
		yearComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					updateClinicalTestReqTree(newValue);
					//更新  课题信息  区域  的所有Text 置空
					updateAllText(null);
					//更新临检申请区域   置空
					updateClinicalTestReq(null);
					//更新    检测结果    面板
					updateTestResult(null);
				}
			}});
		
		
		yearComboBox.getSelectionModel().clearAndSelect(0);
	}

	/**
	 * 更新试验基本信息的值
	 * @param entity
	 */
	private void updateAllText(TblStudyPlan entity){
		if(null!=entity){
			studyNoLabel.setText(entity.getStudyNo());           //课题编号
			studydirectorLabel.setText(entity.getStudydirector());     //SD
			
			studyNoText.setText(entity.getStudyNo());            //课题编号
			//TODO
			if(null!=entity.getStudyName()){
				dictStudyTypeText.setText(entity.getStudyName());//课题类别     类
			}else{
				dictStudyTypeText.setText("");
			}
			studydirectorText.setText(entity.getStudydirector());       //课题负责人
			if(entity.getIsGLP()==1){
				isGLPText.setText("是");                  //是否GLP  
			}else{
				isGLPText.setText("否");
			}
			animalTypeText.setText(entity.getAnimalType());          //动物种类
			animalStrainText.setText(entity.getAnimalStrain());        //动物品系
			dosageUnitText.setText(entity.getDosageUnit());          //剂量单位
			clinicalTestDirectorText.setText(entity.getClinicalTestDirector());//临检负责人
		}else{
			
			studyNoLabel.setText(""); // 课题编号
			studydirectorLabel.setText(""); // SD

			studyNoText.setText(""); // 课题编号
			dictStudyTypeText.setText("");// 课题类别 类
			studydirectorText.setText(""); // 课题负责人
			isGLPText.setText(""); // 是否GLP
			animalTypeText.setText(""); // 动物种类
			animalStrainText.setText(""); // 动物品系
			dosageUnitText.setText(""); // 剂量单位
			clinicalTestDirectorText.setText("");// 临检负责人
		
		}
	}
	
	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		beginPane = new DatePicker(Locale.CHINA);
		beginPane.getTextField().setEditable(false);
		beginPane.getTextField().setFocusTraversable(false);
		beginPane.getTextField().setMaxWidth(111);
		beginPane.getTextField().setMinWidth(111);
		beginPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		beginPane.getCalendarView().todayButtonTextProperty().set("今天");
		beginPane.getCalendarView().setShowWeeks(false);
		beginPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		beginPane.setMaxWidth(111);
		beginDateHBox.getChildren().add(beginPane);
		
		endPane = new DatePicker(Locale.CHINA);
		endPane.getTextField().setEditable(false);
		endPane.getTextField().setFocusTraversable(false);
		endPane.getTextField().setMaxWidth(111);
		endPane.getTextField().setMinWidth(111);
		endPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endPane.getCalendarView().todayButtonTextProperty().set("今天");
		endPane.getCalendarView().setShowWeeks(false);
		endPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endPane.setMaxWidth(111);
		endDateHBox.getChildren().add(endPane);
		//
		beginPane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					updateClinicalTestReqTree2(newValue, endPane.getTextField().getText());
				}else{
					updateClinicalTestReqTree2("", "");
				}
			}
			});
		endPane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					updateClinicalTestReqTree2(beginPane.getTextField().getText(), newValue);
				}else{
					updateClinicalTestReqTree2("", "");
				}
			}
		});
		
	}
	/**
	 * 查询按钮事件 
	 * @param event
	 */
	@FXML
	private void onQueryBtnAction(ActionEvent event){
		String beginDateString=beginPane.getTextField().getText();
		String endDateString = endPane.getTextField().getText();
		if("".equals(beginDateString)||"".equals(endDateString)){
//			Alert2.create("请选择查询日期区间");
			Messager.showWarnMessage("请选择查询日期区间！");
			return;
		}else{
			Date beginDate=DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
			Date endDate=DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
			if(beginDate.after(endDate)){
//				Alert2.create("开始日期不能大于结束日期");
				Messager.showWarnMessage("开始日期不能大于结束日期！");
				return;
			}else{
				updateClinicalTestReqTree2(beginDateString,endDateString);
			}
		}
	}
	/**
	 * 设备登记菜单onAction事件
	 */
	@FXML
	private void onInstrumentMenuAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.WINDOW_MODAL);
//		stage.setTitle("设备登记--身份验证");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		//签字通过
//		if("OK".equals(SignFrame.getType())){
		User signUser = Sign.openSign("设备登记--身份验证", "");
		//签字通过
		if(null != signUser){
			//记录设备登记日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("身份验证");
			tblLog.setOperatOject("设备登记");
			User user = signUser;
			if(null!=user){
				tblLog.setOperator(user.getRealName());
				tblLog.setOperatContent("通过，id:"+user.getId()+", userName:"+user.getUserName());
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				BaseService.getTblLogService().save(tblLog);
			}
			
			Stage stage1=new Stage();
			stage1.initOwner(MainFrame.mainWidow);
			
			//stage.initModality(Modality.APPLICATION_MODAL);
			DictInstrumentFrame dictInstrumentFrame=new DictInstrumentFrame();
			try {
				dictInstrumentFrame.start(stage1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 设备检定信息 菜单onAction事件
	 */
	@FXML
	private void onInstrumentVerificationMenuAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.WINDOW_MODAL);
//		stage.setTitle("设备检定信息--身份验证");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		//签字通过
//		if("OK".equals(SignFrame.getType())){
		User signUser = Sign.openSign("设备登记--身份验证", "");
		//签字通过
		if(null != signUser){
			//记录设备检定信息日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("身份验证");
			tblLog.setOperatOject("设备检定信息设置");
			User user =signUser;
			if(null!=user){
				tblLog.setOperator(user.getRealName());
				tblLog.setOperatContent("通过，id:"+user.getId()+", userName:"+user.getUserName());
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				BaseService.getTblLogService().save(tblLog);
			}
			
			Stage stage1=new Stage();
			stage1.initOwner(MainFrame.mainWidow);
			stage1.initModality(Modality.WINDOW_MODAL);
			TblInstrumentVerificationFrame dictInstrumentFrame=new TblInstrumentVerificationFrame();
			try {
				dictInstrumentFrame.start(stage1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 通道号设置 菜单onAction事件
	 */
	@FXML
	private void onPassagewayMenuAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.WINDOW_MODAL);
//		stage.setTitle("通道号设置--身份验证");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		//签字通过
//		if("OK".equals(SignFrame.getType())){
		User signUser = Sign.openSign("设备登记--身份验证", "");
		//签字通过
		if(null != signUser){
			//记录通道号设置日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("身份验证");
			tblLog.setOperatOject("通道号设置");
			User user = signUser;
			if(null!=user){
				tblLog.setOperator(user.getRealName());
				tblLog.setOperatContent("通过，id:"+user.getId()+", userName:"+user.getUserName());
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				BaseService.getTblLogService().save(tblLog);
			}
			
			Stage stage1=new Stage();
			stage1.initOwner(MainFrame.mainWidow);
			stage1.initModality(Modality.WINDOW_MODAL);
			PassagewayFrame passagewayFrame=new PassagewayFrame();
			try {
				passagewayFrame.start(stage1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 临时任务 菜单项onAction 事件
	 */
	@FXML
	private void onTempTaskMenuAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
		Stage stage=new Stage();
		stage.initOwner(MainFrame.mainWidow);
		stage.initModality(Modality.WINDOW_MODAL);
		TempTaskFrame tempTaskFrame = new TempTaskFrame();
		try {
			tempTaskFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(tempTaskFrame.getTemp()==1){
			showTempTaskCheckBox1.setSelected(true);
			showTempTask = true;
			SimpleDateFormat format=new SimpleDateFormat("yyyy");
			String year =format.format(new Date());
			//更新临检树1
			updateClinicalTestReqTree(year);
			//更新临检树2
			updateClinicalTestReqTree2(beginPane.getTextField().getText(), endPane.getTextField().getText());
			TblStudyPlan  tblStudyPlan = BaseService.getTblStudyPlanService().getById(tempTaskFrame.getStudyNo());
			//选中新增项
			selectedNoteOnClinicalTestReqTree(tblStudyPlan,tempTaskFrame.getReqNo());
		}else if(tempTaskFrame.getTemp()==2){
			TblStudyPlan  tblStudyPlan = BaseService.getTblStudyPlanService().getById(tempTaskFrame.getStudyNo());
			SimpleDateFormat format=new SimpleDateFormat("yyyy");
			String year =format.format(tblStudyPlan.getStudyStartDate());
			//更新临检树1
			updateClinicalTestReqTree(year);
			//更新临检树2
			updateClinicalTestReqTree2(beginPane.getTextField().getText(), endPane.getTextField().getText());
			
			//选中新增项
			selectedNoteOnClinicalTestReqTree(tblStudyPlan,tempTaskFrame.getReqNo());
		}
	}
	
	/**
	 * 选中指定值
	 */
	private void selectedNoteOnClinicalTestReqTree(TblStudyPlan tblStudyPlan ,int reqNo){
		if(null != tblStudyPlan){
			String noteValue = "";
			String reqNoNoteValue ="";
			if(tblStudyPlan.getTemp() == 1){
				noteValue= tblStudyPlan.getStudyNo()+":"+tblStudyPlan.getClient();
				reqNoNoteValue = "1:"+DateUtil.dateToString(tblStudyPlan.getStudyStartDate(), "yyyy-MM-dd");
			}else{
				TblClinicalTestReq tblClinicalTestReq = BaseService.getTblClinicalTestReqService()
						.findByStudyNoAndReqNO(tblStudyPlan.getStudyNo(), reqNo);
				noteValue = tblStudyPlan.getStudyNo();
				reqNoNoteValue = reqNo+":"+DateUtil.dateToString(tblClinicalTestReq.getCreateDate(), "yyyy-MM-dd")
				+" ";
			}
			ObservableList<TreeItem<String>> list1 =  rootNode.getChildren();
			if(null != list1){
				for( TreeItem<String> note:list1){
					if(note.getValue().equals(noteValue)){
						note.setExpanded(true);
						ObservableList<TreeItem<String>> list2 =  note.getChildren();
						if(null != list2){
							for( TreeItem<String> note2:list2){
								if(note2.getValue().equals(reqNoNoteValue)){
									clinicalTestReqTree.getSelectionModel().select(note2);
									break;
								}
								if(!note2.isLeaf()){
									ObservableList<TreeItem<String>> list3 =  note2.getChildren();
									if(null != list3){
										for( TreeItem<String> note3:list3){
											if(note3.getValue().equals(reqNoNoteValue)){
												note2.setExpanded(true);
												clinicalTestReqTree.getSelectionModel().select(note3);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 审计菜单onAction事件 //
	 * @param event
	 */
	@FXML
	private void onAuditMenuAction(ActionEvent event){
		AuditFrame.setStudyNo(studyNoText.getText());
		Stage stage=new Stage();
		stage.initOwner(MainFrame.mainWidow);
		stage.initModality(Modality.WINDOW_MODAL);
		AuditFrame auditFrame = new AuditFrame();
		try {
			auditFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改密码 菜单onAction事件
	 */
	@FXML
	private void onAlterPasswordMenuAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
		Stage stage=new Stage();
		stage.initOwner(MainFrame.mainWidow);
		stage.initModality(Modality.WINDOW_MODAL);
		AlterPassword alterPassword=new AlterPassword(SaveUserInfo.getUser(),"");
		try {
			alterPassword.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改成功
		if(AlterPassword.getType().equals("OK")){
			SaveUserInfo.setUser(AlterPassword.getUser());
			
			List<User> list=BaseService.getUserService().findByPrivilegeName("临检登录");
			//存放有   临检登录   权限 的用户列表
			SaveUserInfo.setClinicalTestUserList(list);
		}
	}
	/**
	 * 退出菜单项onAction事件
	 * @param event
	 */
	@FXML
	private void OnExitMenuAction(ActionEvent event){
		if(DataAcceptFrameController.isUsed){
//			if(Confirm.create(MainFrame.mainWidow,"提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
			if(Messager.showConfirm("提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
				DataAcceptFrameController.serialPort.removeEventListener();
				DataAcceptFrameController.serialPort.notifyOnDataAvailable(false); 
				DataAcceptFrameController.serialPort.close();
				DataAcceptFrameController.isUsed =false;
				
				TblLog tblLog = new TblLog();
				tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
				tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
				tblLog.setOperatType("退出系统");
				tblLog.setOperatOject(SystemMessage.getSystemFullName());
				User user = SaveUserInfo.getUser();
				if(null!=user){
					tblLog.setOperator(user.getRealName());
				}
				tblLog.setOperatContent("");
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				try {
					BaseService.getTblLogService().save(tblLog);
				} catch (Exception e) {
					System.exit(1);
				}
				System.exit(1);
				
			}else{
				event.consume();
			}
			
		}else{
//			if(Confirm2.create(MainFrame.mainWidow,"确定要退出系统吗？")){
			if(Messager.showSimpleConfirm("提示", "确定要退出系统吗？")){
				TblLog tblLog = new TblLog();
				tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
				tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
				tblLog.setOperatType("退出系统");
				tblLog.setOperatOject(SystemMessage.getSystemFullName());
				User user = SaveUserInfo.getUser();
				if(null!=user){
					tblLog.setOperator(user.getRealName());
				}
				tblLog.setOperatContent("");
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				try {
					BaseService.getTblLogService().save(tblLog);
				} catch (Exception e) {
					System.exit(1);
				}
				System.exit(1);
			}
		}
		
		
	}
	/**
	 * 关于菜单项onAction事件
	 * @param event
	 */
	@FXML
	private void OnAboutMenuAction(ActionEvent event){
		Stage stage=new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		About about = new About();
		try {
			about.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 标本接收按钮事件
	 * @param event
	 */
	@FXML
	private void onSpecimenBtnAction(ActionEvent event){
		String studyNo=studyNoText.getText();
		String reqNo=reqNoText_clinicalTest.getText();
		if("".equals(studyNo)||"".equals(reqNo)){
//			Alert2.create("请先选择临检申请");
			Messager.showWarnMessage("请先选择临检申请！");
			return;
		}else{
			int reqNoInt=Integer.parseInt(reqNo);
			TblClinicalTestReq tblClinicalTestReq=BaseService.getTblClinicalTestReqService().findByStudyNoAndReqNO(studyNo,reqNoInt);
	//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
	//		Stage stage=new Stage();
	//		stage.initOwner(MainFrame.mainWidow);
	//		stage.initModality(Modality.WINDOW_MODAL);
	//		TblSpecimenFrame tblSpecimenFrame=new TblSpecimenFrame();
	//		TblSpecimenFrame.setTblClinicalTestReq(tblClinicalTestReq);
	//		try {
	//			tblSpecimenFrame.start(stage);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
			Stage stage = Main.stageMap.get("TblSpecimenFrameController");
			if(null == stage){
				try {
					stage =new Stage();
					stage.initOwner(MainFrame.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					TblSpecimenFrameController.getInstance().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("TblSpecimenFrameController",stage);
			}else{
				stage.show();
			}
			TblSpecimenFrameController.getInstance().loadData(tblClinicalTestReq);
		}
	}
	
	/**
	 * 数据接收收按钮事件
	 * 
	 */
	@FXML
	private void onTblClinicalTestDataBtnAction(ActionEvent event){
		
//		DataAcceptFrame dataAcceptFrame =new DataAcceptFrame();
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
//		Stage stage=new Stage();
//		stage.initOwner(MainFrame.mainWidow);
//		stage.initModality(Modality.APPLICATION_MODAL);
		Stage stage = Main.stageMap.get("DataAcceptFrameController");
		if(null == stage){
			try {
				stage =new Stage();
//				stage.initOwner(MainFrame.mainWidow);
//				stage.initModality(Modality.APPLICATION_MODAL);
				DataAcceptFrameController.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DataAcceptFrameController",stage);
		}
		if(!stage.isShowing()){
			DataAcceptESFrame.getInstance().setType("Cancel");
			DataAcceptFrameController.getInstance().loadData();
			stage.showAndWait();
//			if(DataAcceptFrame.isAdd || "OK".equals(DataAcceptESFrame.getType())){
			if(DataAcceptFrameController.getInstance().isHasAdd() || "OK".equals(DataAcceptESFrame.getInstance().getType())){
				String studyNo =studyNoText.getText().toString().trim();
				int reqNo = 0;
				String reqNoStr = reqNoText_clinicalTest.getText().trim();
				if(!studyNo.isEmpty() && !reqNoStr.isEmpty()){
					reqNo = Integer.parseInt(reqNoStr);
					//更新检测结果的值(根据studyNo,reqNo)
					updateTestResult(studyNo,reqNo);
				}
			}
		}else{
			stage.centerOnScreen();
			stage.toFront();
		}
	}
	/**
	 * 数据确认按钮事件
	 * @throws JRException 
	 */
	@FXML
	private  void onDataConfirmBtnAction(ActionEvent event) {
		
//		DataAcceptESFrame.getInstance()
		
		Stage stage = Main.stageMap.get("DataAcceptESFrame");
		if(null == stage){
			try {
				stage =new Stage();
				stage.initOwner(MainFrame.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				DataAcceptESFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DataAcceptESFrame",stage);
		}
		if(!stage.isShowing()){
			DataAcceptESFrame.getInstance().loadData(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			stage.showAndWait();
		}else{
			stage.toFront();
		}
		if("OK".equals(DataAcceptESFrame.getInstance().getType())){
			String studyNo =studyNoText.getText().toString().trim();
			int reqNo = 0;
			String reqNoStr = reqNoText_clinicalTest.getText().trim();
			if(!studyNo.isEmpty() && !reqNoStr.isEmpty()){
				reqNo = Integer.parseInt(reqNoStr);
				//更新检测结果的值(根据studyNo,reqNo)
				updateTestResult(studyNo,reqNo);
			}
			
		}
	}
	/**
	 * 打印 按钮事件      
	 * @throws JRException 
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event) throws JRException {
		
		Button button = (Button) event.getSource();
		//得到按钮Id
		String buttonId=button.getId();
		
		String studyNo = studyNoText.getText();
		String reqNo = reqNoText_clinicalTest.getText();
		if ("".equals(studyNo) || "".equals(reqNo)) {
//			Alert2.create("请先选择临检申请");
			Messager.showWarnMessage("请先选择临检申请！");
			return;
		} else {
			int reqNoInt = Integer.parseInt(reqNo);
			TblClinicalTestReq tblClinicalTestReq = BaseService.getTblClinicalTestReqService()
					.findByStudyNoAndReqNO(studyNo, reqNoInt);
			//点击的是打印临检申请单  按钮
			if("printClinicalTestReqBtn".equals(buttonId)){
				//打印临检临检申请单
				TblStudyPlan tblStudyPlan=tblClinicalTestReq.getTblStudyPlan();
//				StaticDate.STATIC_DATE_MAP.put("studyPlan", tblStudyPlan);
//				StaticDate.STATIC_DATE_MAP.put("clinicalTestReq", tblClinicalTestReq);
				
//		        InputStream logoImage =  getClass().getResourceAsStream("/image/clinicaltest/logo.jpg");
//		        StaticDate.STATIC_DATE_MAP.put("logoImage", logoImage);
				//懒加载问题  （改用全局的）
				//List<TblClinicalTestReqIndex2> tblClinicalTestReqIndex2List = new ArrayList<TblClinicalTestReqIndex2>(tblClinicalTestReq.getTblClinicalTestReqIndex2s());
				
				JasperReport jr = null;
		           JasperPrint jp = null;
//		           String xmlFilePath=mainFilePath+"clinicalTestReq.jrxml";
//		           String xmlFilePath ="bin/com/lanen/zero/main/clinicalTestReq.jrxml);
		           jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("clinicalTestReq.jrxml"));
		           Map<String,Object> map = new HashMap<String,Object>();
		           
//		           map.put("beginDate", tblClinicalTestReq.getBeginDate());
//		           map.put("endDate", tblClinicalTestReq.getEndDate());
		           String date = "";
		           String beginDateStr = DateUtil.dateToString(tblClinicalTestReq.getBeginDate(), "yyyy-MM-dd");
		           String endDateStr   = DateUtil.dateToString(tblClinicalTestReq.getEndDate(),"yyyy-MM-dd");
		           if(beginDateStr.equals(endDateStr) || endDateStr==null || "".equals(endDateStr)){
		        	   date = beginDateStr;
		           }else{
		        	   date = beginDateStr+" -- "+endDateStr;
		           }
		           map.put("date",date);
		           
		           String number = BaseService.getDictReportNumberService().getNumberByReportName("临床检验申请单");
		           map.put("number", number == null ? "":number);
		           
		         //设置检测项目
		   		String testIndex1="";
		   		String testIndex2="";
		   		String testIndex3="";
		   		String testIndex4="";
		   		//拼接每一个检测项目的字符串 TODO 
		   		List<TblClinicalTestReqIndex> tblClinicalTestReqIndexs = BaseService.getTblClinicalTestReqService().getReqIndexByReqNo(tblStudyPlan, reqNoInt);
		   		//存放申请指标对应缩写
		   		List<String> bioChemTestIndexAbbrList = new ArrayList<String>();
		   		List<String> hematTestIndexAbbrList = new ArrayList<String>();
		   		List<String> bloodCoagTestIndexAbbrList = new ArrayList<String>();
		   		List<String> urineTestIndexAbbrList = new ArrayList<String>();
		   		for (TblClinicalTestReqIndex obj: tblClinicalTestReqIndexs) {
		   			if(obj.getTestitem()==1){
		   				bioChemTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   			if(obj.getTestitem()==2){
		   				hematTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}  
		   			if(obj.getTestitem()==3){
		   				bloodCoagTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   			if(obj.getTestitem()==4){
		   				urineTestIndexAbbrList.add(obj.getTestIndexAbbr());
		   			}
		   		}
		   		
		   		//获取生化指标
		   		List<DictBioChem> biochemList = BaseService.getDictBioChemService().getAll();
		   		List<DictHemat> hematList = BaseService.getDictHematService().getAll();
		   		List<DictBloodCoag> bloodCoagList = BaseService.getDictBloodCoagService().getAll();
		   		List<DictUrine> urineList = BaseService.getDictUrineService().getAll();
				
		   		for (DictBioChem obj: biochemList) {
		   			if(bioChemTestIndexAbbrList.contains(obj.getAbbr())){
		   				testIndex1= testIndex1+obj.getName()+"("+obj.getAbbr()+")   ";
		   			}
		   		}
		   		for (DictHemat obj: hematList) {
		   			if(hematTestIndexAbbrList.contains(obj.getAbbr())){
		   				testIndex2= testIndex2+obj.getName()+"("+obj.getAbbr()+")   ";
		   			}
		   		}
		   		for (DictBloodCoag obj: bloodCoagList) {
		   			if(bloodCoagTestIndexAbbrList.contains(obj.getAbbr())){
		   				testIndex3= testIndex3+obj.getName()+"("+obj.getAbbr()+")   ";
		   			}
		   		}
		   		for (DictUrine obj: urineList) {
		   			if(urineTestIndexAbbrList.contains(obj.getAbbr())){
		   				testIndex4= testIndex4+obj.getName()+"("+obj.getAbbr()+")   ";
		   			}
		   		}
		   		String testIndex5=tblClinicalTestReq.getTestOther();
		   		if(testIndex5 == null || testIndex5.isEmpty()){
		   			testIndex5 = "  NA";
		   		}
		   		String testIndex6=tblClinicalTestReq.getRemark();
		   		if(testIndex6 == null || testIndex6.isEmpty()){
		   			testIndex6 = "  NA";
		   		}
		   		String testName1="血液生化检查";
		   		String testName2="血液常规检查";
		   		String testName3="凝血功能检查";
		   		String testName4="尿液检查";
		   		String testName5="其他检测项目";
		   		String testName6="备注";
		   		
		   		map.put("studyNo", tblStudyPlan.getStudyNo());
		   		
		   		String animalType = tblStudyPlan.getAnimalType();
				String animalStrain = tblStudyPlan.getAnimalStrain();
				if(null != animalStrain && null != animalType ){
					if(animalStrain.contains(animalType.trim())){
						map.put("animalType", animalStrain);
					}else {
						map.put("animalType", animalType+" "+animalStrain);
					}
				}else{
					map.put("animalType", animalType);
				}
		   		
//		   		map.put("animalType", tblStudyPlan.getAnimalType());
//		   		paraMap.put("studyType", tblStudyPlan.getIsGLP()==1?"GLP研究":"非GLP研究");
		   		String testPnase = tblClinicalTestReq.getTestPhase();
		   		if(testPnase == null || testPnase.isEmpty()){
		   			testPnase = "";
		   		}
		   		map.put("testPhase",testPnase );
		   		map.put("testName1", testName1);
		   		map.put("testIndex1", testIndex1.equals("") ? "  NA" :testIndex1);
		   		map.put("testName2", testName2);
		   		map.put("testIndex2", testIndex2.equals("") ? "  NA" :testIndex2);
		   		map.put("testName3", testName3);
		   		map.put("testIndex3", testIndex3.equals("") ? "  NA" :testIndex3);
		   		map.put("testName4", testName4);
		   		map.put("testIndex4", testIndex4.equals("") ? "  NA" :testIndex4);
		   		map.put("testName5", testName5);
		   		map.put("testIndex5", testIndex5);
		   		map.put("testName6", testName6);
		   		map.put("testIndex6", testIndex6);
//		   		paraMap.put("reqNo", tblClinicalTestReq.getReqNo());
//		   		String imageRead = request.getRealPath("/logo.jpg");
//		   		获得图片路径
//		   		File imageFile   =   new   File(imageRead);
//		   		InputStream   imageIn =null;
//		   		try {
//		   			imageIn   =   new   FileInputStream(imageFile);
//		   			paraMap.put("logoImage", imageIn);
//		   		} catch (FileNotFoundException e) {
//		   			e.printStackTrace();
//		   		}
		   		URL url = this.getClass().getResource("/image/clinicaltest/logo.jpg");
		   		map.put("logoImage", url);
		   		boolean isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(tblStudyPlan.getAnimalType());
		   		//小动物不显示动物id号
		   		List<TblClinicalTestReqIndex2> dataList = new ArrayList<TblClinicalTestReqIndex2>();
		   		
		   		TblClinicalTestReqIndex2 data = null;
		   		String animalCode = null;
		   		for(TblClinicalTestReqIndex2 obj:tblClinicalTestReqIndex2List){
		   			data = new TblClinicalTestReqIndex2();
		   			animalCode = obj.getAnimalCode();
		   			data.setAnimalCode(animalCode);
		   			data.setGender(obj.getGender());
		           if(!isBigAnimal && null != animalCode && !"".equals(animalCode) ){
					    data.setAnimalId("NA");
		           }else{
		        	   data.setAnimalId(obj.getAnimalId());
		           }
		           dataList.add(data);
		   		}
//		           jp = JasperFillManager.fillReport(jr, map,new ClassBeanCollectionDataSource(tblClinicalTestReqIndex2List));
		           jp = JasperFillManager.fillReport(jr, map,new ClassBeanCollectionDataSource(dataList));
		        
		           MainFrame.getInstance().openJFrame(jp, "临床检验申请单");
//		           if(null!=jFrame){
//		        	   jFrame.remove(view);
//		           }else{
//		        	   jFrame=new JFrame();
//		        	   jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//					   jFrame.setAlwaysOnTop(true);
//					   jFrame.setSize(850, 700);
//					   
//					   //设置居中显示
//					   Toolkit tookit =jFrame.getToolkit();
//						Dimension dimension =tookit.getScreenSize();
//						int screenWidth=dimension.width;
//						int screenHeight=dimension.height;
//						int frameWidth=jFrame.getWidth();
//						int frameHeight=jFrame.getHeight();
//						jFrame.setLocation((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2);
//		           }
//		           jFrame.setTitle("临检申请单");
//		           view=new JRViewer(jp);
//					
//				   JRViewerToolbar toolbar=(JRViewerToolbar) view.getComponent(0);
//				   JRSaveContributor   savePdf=toolbar.getSaveContributors()[1];
//				   JRSaveContributor   saveDocx=toolbar.getSaveContributors()[4];
//				   JRSaveContributor   saveXls=toolbar.getSaveContributors()[6];
//				   toolbar.setSaveContributors(new JRSaveContributor[]{savePdf,saveDocx,saveXls});
//				   jFrame.add(view);
//					jFrame.setVisible(true);
//					//窗口打开后关闭（AlwaysOnTop属性）
//					jFrame.addWindowListener(new WindowAdapter(){
//						@Override
//						public void windowOpened(WindowEvent e) {
////							super.windowOpened(e);
//							JFrame jframe=(JFrame) e.getSource();
//							jframe.setAlwaysOnTop(false);
//						}
//						
//					} );
//					jFrame.setFocusable(true);
//					jFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		           //记录打印临检申请单日志
					TblLog tblLog = new TblLog();
					  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
					  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
					  tblLog.setOperatType("打印");
					  tblLog.setOperatOject("临检申请单");
					  User user = SaveUserInfo.getUser();
					  if(null!=user){
						  tblLog.setOperator(user.getRealName());
					  }
					  tblLog.setOperatContent(studyNo+":"+reqNoInt);
					  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
					  BaseService.getTblLogService().save(tblLog);
				return;
			}else{
				//判断是否有签过字的数据
				boolean isHasData = BaseService.getTblClinicalTestDataService().isHasESData(studyNo,reqNo);
				if(!isHasData){
//					Alert2.create("当前申请暂无数据(确认后)");
					Messager.showWarnMessage("当前申请暂无数据(确认后)！");
					return;
				}
				try {
					ChoosePrintFrame.getInstance().loadData(studyNo,reqNo);
					ChoosePrintFrame.getInstance().start(new Stage());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 刷新按钮事件
	 */
	@FXML
	private void onRefreshButton(){
		String newValue = yearComboBox.getSelectionModel().getSelectedItem();
		updateClinicalTestReqTree(newValue);
		//更新  课题信息  区域  的所有Text 置空
		updateAllText(null);
		//更新临检申请区域   置空
		updateClinicalTestReq(null);
		//更新    检测结果    面板
		updateTestResult(null);
	}
	/**
	 * 刷新按钮事件
	 */
	@FXML
	private void onRefreshButton1() {
		String beginDateString = beginPane.getTextField().getText();
		String endDateString = endPane.getTextField().getText();
		if ("".equals(beginDateString) || "".equals(endDateString)) {
//			Alert2.create("请选择查询日期区间");
			Messager.showWarnMessage("请选择查询日期区间！");
			return;
		} else {
			updateClinicalTestReqTree2(beginDateString, endDateString);
			// 更新课题信息区域 清空
			updateAllText(null);
			// 更新临检申请单 区域 清空
			updateClinicalTestReq(null);
			// 更新 检测结果 面板
			updateTestResult(null);
		}
	}
	
    private static class TreeFormatCell extends TreeCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(item == null ? "" : item);

            if (item != null) {
            	if(item.contains("  ")){
            		setTextFill(Color.BLUE);
            	}else if(item.contains(" ")){
            		setTextFill(Color.RED);
            	}else{
            		setTextFill(Color.BLACK);
            	}
            }
        }
    } 
}
