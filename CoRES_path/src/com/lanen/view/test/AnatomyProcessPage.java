package com.lanen.view.test;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.User;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.path.TblBalConnect;
import com.lanen.model.path.TblBalReg;
import com.lanen.model.path.TblPathSession;
import com.lanen.model.path.TblVisceraFixed;
import com.lanen.model.path.TblVisceraMissing;
import com.lanen.model.path.TblVisceraWeight;
import com.lanen.model.path.TblVisceraWeightHis;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.balreg.AddTblBalCalibration;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;
import com.lanen.view.sign.Sign;

/**数据采集
 * @author Administrator
 *
 */
public class AnatomyProcessPage extends Application implements Initializable , Runnable {

	
	/**
	 * 刚进入界面,动物还未选择
	 */
	private static boolean noAnimalSelected = true;
	
	/**
	 * 一次都未打开天平
	 */
	private static boolean noOpenBal = true;
	
	@FXML
	private Label handlersLabel; // 当前操作者
	@FXML
	private Label msgLabel; // 称重页面提示信息label
	@FXML
	private Label msgLabel_fixed; // 固定未完成，提示信息label
	private static Timer timer = null;
	
	@FXML
	private Hyperlink saveWeightUpperAndLowerLink;

	@FXML
	private TableView<BalanceRrge> balanceRrgeTbale;//天平tbale
	private ObservableList<BalanceRrge> data_balanceRrgeTbale = FXCollections.observableArrayList();

	@FXML
	private TableColumn<BalanceRrge, String> balCodeCol; // 天平编号column
	
	@FXML
	private TableColumn<BalanceRrge, String> balStateCol; // 天平状态column
	
	@FXML
	private TableColumn<BalanceRrge, String> baudComboCol; // 天平接入端口column
	
	@FXML
	private TableView<Animal> animalTable; // 动物table
	private ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	
	// 右击菜单
	private ContextMenu contextMenu = new ContextMenu();
	// 右击菜单项（删除）
	private	MenuItem menuItem_delete;
	
	@FXML
	private TableColumn<Animal, String> animalCodeCol; // 动物编号column

	@FXML
	private TableColumn<Animal, String> stateCol; // 状态column

	@FXML
	private TableColumn<Animal, String> anatomyOperatorCol; // 解剖者column

	@FXML
	private TableColumn<Animal, String> studyNoCol; // 专题编号column
	
	
	/**
	 * 按解剖时间排序
	 */
	@FXML
	private CheckBox anatomyTimeCheckBox;

	@FXML
	private Button autolyzeBtn; // 自溶button（动物自溶）

	@FXML
	private Button selectAnimalBtn; // 选择动物button

	@FXML
	private TabPane tabPane; // 标签面板TabPane

	@FXML
	private Tab anatomyTab; // 动物解剖Tab
	@FXML
	private AnchorPane anatomyPane;
	@FXML
	private AnchorPane weighPane;
	@FXML
	private AnchorPane fixedPane;
	
	@FXML
	private Tab weightTab; // 脏器称重Tab
	
	@FXML
	private Tab fixTab; // 脏器固定Tab

	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();
	@FXML		
	private Button otherVisceraBtn;			//其他脏器
	@FXML
	private ListView<String> anatomyPosListView; // 解剖学所见部位ListView
	private ObservableList<String> data_anatomyPosListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_anatomyPos = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所所见部位：毒性病理字典
	private Map<String,DictPathCommon> anatomyPosDictPathCommonMap = new HashMap<String,DictPathCommon>();
	@FXML
	private ListView<String> anatomyFindingListView; // 解剖所见ListView
	private ObservableList<String> data_anatomyFindingListView_tongyong = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_tongyong = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所通用所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tongyongDictPathCommonMap = new HashMap<String,DictPathCommon>();
	private ObservableList<String> data_anatomyFindingListView_tesu = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_teshu = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所特殊所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tesuDictPathCommonMap = new HashMap<String,DictPathCommon>();

	@FXML
	private ListView<String> bodySufacePosListView; // 体表部位ListView
	private ObservableList<String> data_bodySufacePosListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_bodySuface = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> posListView; // 位置ListView
	private ObservableList<String> data_posListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_pos = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> shapeListView; // 形状ListView
	private ObservableList<String> data_shapeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_shape = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> colorListView; // 颜色ListView
	private ObservableList<String> data_colorListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_color = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> textureListView; // 硬度ListView
	private ObservableList<String> data_textureListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_texture = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> numberListView; // 数量ListView
	private ObservableList<String> data_numberListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_number = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> rangeListView; // 范围ListView
	private ObservableList<String> data_rangeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_range = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> lesionDegreeListView; // 病变程度ListView
	private ObservableList<String> data_lesionDegreeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_lesionDegree = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> sizeListView; // 大小ListView
	private ObservableList<String> data_sizeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_size = new HashMap<String,String>();//处方拼音首字母-中文名称
	@FXML
	private TextField sizeTextField;
	@FXML
	private TableView<AnatomyCheck> anatomyResultTable; // 解剖结果TableView
	/**
	 * anatomyResultTalbe 数据集
	 */
	private ObservableList<AnatomyCheck> data_anatomyResultTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnatomyCheck, String> visceraNameCol; // 脏器column

	@FXML
	private TableColumn<AnatomyCheck, String> findingCol; // 解剖所见column
	@FXML
	private TableColumn<AnatomyCheck, Boolean> operateCol; // 解剖所见column

	@FXML
	private Button registerBtn; // 登记button
	
	@FXML
	private Button autolyzeBtn2; // 自溶button（脏器）

	@FXML
	private Button otherNABtn; // 其余未见异常
	
	@FXML
	private Button symptomObservationBtn; // 症状观察

//	@FXML
//	private Button confirmBtn; // 完成button

	@FXML
	private ComboBox<String> searchComboBox; // 快速查找ComboBox
	@FXML
	private TextField searchTextField;//快速查找TextField
	@FXML
	private ListView<String> searchListView ; //快速查找ListView
	private ObservableList<String> data_searchListView = FXCollections.observableArrayList();
	
	
	@FXML
	private ComboBox<String> dictSortMethodComboBox; // 字典排序方式
	private static int sortMethod = 1;	//排序方式 sortMethod :1,字典排序  2，字母排序 3,使用频度

	@FXML
	private ToggleGroup tg;

	@FXML
	private RadioButton tongyongRadioButton; // 普通所见RadioButton
	
	@FXML
	private RadioButton tesuRadioButton; // 特殊所见RadioButton
	@FXML
	private Label anatomyFindingLabel;
	
	
	@FXML
	private Label animalCodeLabel_anatomy;	//动物编号
	@FXML
	private Label anatomyOperatorLabel_anatomy;//解剖者
	@FXML
	private Label anatomyBeginTimeLabel_anatomy;//解剖开始时间
	
	
	//--------------------------------脏器固定-----------------------------
	
//	private static List<String> needFixedVisceraName=new ArrayList<>();  //需要固定的脏器列表
	/**
	 * 脏器固定--选择脏器ListView
	 */
	@FXML
	private ListView<CheckBox> visceraListView;
	private ObservableList<CheckBox> data_visceraListView = FXCollections.observableArrayList();
	
	/**
	 * 固定（>>）
	 */
	@FXML
	private Button fixedBtn;
	@FXML
	private Button visceraMissingButton_fixed;//登记脏器缺失
	@FXML
	private Button abnFixedButton; //登记异常脏器固定
	/**
	 * 固定完成
	 */
	@FXML
	private Button fixedFinishBtn;
    @FXML
    private Label abnVisceraLabel;
	@FXML
	private TableView<VisceraFixed> visceraFixedTable;			//脏器固定结果
	private ObservableList<VisceraFixed> data_visceraFixedTable = FXCollections.observableArrayList();
	@FXML	
	private TableColumn<VisceraFixed,String> visceraNameCol_fixed;	//脏器
	@FXML
	private TableColumn<VisceraFixed,Boolean> operateCol_fixed;		//操作
	
	@FXML
	private TableView<AbnViscera> abnVisceraTable;  //异常的组织和脏器
	private ObservableList<AbnViscera> data_abnVisceraTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AbnViscera,String> abnVisceraCol;		//异常组织和脏器名
	@FXML
	private TableColumn<AbnViscera,Boolean> operateCol_AbnFixed;
	@FXML
	private AnchorPane abnVisceraPane;
	@FXML
	private ListView<CheckBox> abnVisceraList; //异常的组织和脏器、
	private ObservableList<CheckBox> data_abnVisceraList = FXCollections.observableArrayList();
	//显示其他病变
	private boolean showOtherAbn = false;;
	@FXML
	private CheckBox showOtherAbnCheckBox;
	
//	private List<AbnViscera> listAbnViscera=new ArrayList<AbnViscera>();
	private List<TblAnatomyCheck> abnAnatomyCheckList; //异常的剖检记录
	
	/**
	 * 必须固定的异常组织剖检ID
	 */
	private List<String> mustFixedAnatomyCheckId=new ArrayList<String>();    //必须固定的异常组织剖检ID
	//--------------------------------脏器称重-----------------------------
	@FXML
	private TreeView<Label> visceraTree_weight; // 选择脏器tree
	private TreeItem<Label> rootNode_weight=new TreeItem<Label>();//visceraTree_weight 根节点
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	private Map<String,TreeItem<Label>> visceraCodeTreeItemMap_weight = new HashMap<String,TreeItem<Label>>();
	
	@FXML
	private CheckBox isFixedCheckbox;   //称重完立即固定选择项
	private static String idFixed_W=null;
	
//	/**
//	 * 天平编号
//	 */
//	@FXML
//	private ComboBox<String> balCodeComboBox;
//	private ObservableList<String> data_balCodeComboBox = FXCollections.observableArrayList();
//	
	
	
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
	public  Enumeration<?> portList;
	//RS-232的串行口 *
//	public static CommPortIdentifier isUsePortId;	//
//	public static SerialPort isUseingSerialPort ;	//备用，用来关闭的
	public static List<CommPortIdentifier> isUsePortIdList = new ArrayList<CommPortIdentifier>();//
	public static List<SerialPort> isUseingSerialPortList = new ArrayList<SerialPort>();//
	//线程
	private Thread readThread;
	private static boolean isOpening =false;	//连接设备中
	
	private static double otherWeight ;
	
	/**
	 * true:数据接收正常,false:当弹出对话框时停止接收数据
	 */
	private static boolean isget = true ;//数据接收正常,当弹出对话框时停止接收数据
//	
	/**
     * 以上为  串口 通讯 用   
     */
//	@FXML
//	private Label msgLabel;				//重量
	
	@FXML
	private Button visceraMissingButton_weight;//登记脏器缺失
	
	@FXML
	private Label studyNoLabel;				//专题编号
	@FXML
	private Label animalCodeLabel;			//动物编号
	@FXML
	private Label preViscerNameLabel;		//上一个完成称重的脏器
	@FXML
	private Label weightLabel;				//重量
	@FXML
	private Label nextViscerNameLabel;		//待称重脏器
	@FXML
	private Label otherWeightLabel;			//同组其他脏器重量(平均值)
	
	@FXML
	private Label fOtherWeightLabel;			//已称完的同组其他脏器重量
	
	/*
	 * 同组动物该脏器的重量   table
	 */
	@FXML 
	private TableView<OtherAnimalWeight> fOtherAnimalWeightTable;
	private ObservableList<OtherAnimalWeight> data_ftherAnimalWeightTable = FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<OtherAnimalWeight,String> animalCodeCol_fOtherAnimalWeightTable;
	@FXML
	private TableColumn<OtherAnimalWeight,String> weightCol_fOtherAnimalWeightTable;
	
	/*
	 * 同组动物该脏器的重量   table
	 */
	@FXML 
	private TableView<OtherAnimalWeight> otherAnimalWeightTable;
	private ObservableList<OtherAnimalWeight> data_otherAnimalWeightTable = FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<OtherAnimalWeight,String> animalCodeCol_otherAnimalWeightTable;
	@FXML
	private TableColumn<OtherAnimalWeight,String> weightCol_otherAnimalWeightTable;
	
	/*
	 * 脏器称重记录   table
	 */
	@FXML 
	private TableView<VisceraWeight> visceraWeightTable;
	private ObservableList<VisceraWeight> data_visceraWeightTable = FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<VisceraWeight,String> viscerNameCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,String> weightCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,Boolean> operateCol_visceraWeightTable;

	@FXML
	private Button weightFinishBtn;
	
	
	@FXML
	private Label weightLowerLabel;//下限
	
	
	@FXML
	private Label weightUpperLabel;//上限
	
	
	/**
	 * 任务Id --》  是否已经称重或设置称重范围
	 */
	private Map<String,Boolean> taskIdScopeMap = new HashMap<String,Boolean>();
	/**
	 * 是否已经称重或设置称重范围
	 */
	private boolean isWeight = false;
//	/**
//	 * 获取当前页面定义的  stage
//	 * @return
//	 */
//	public Stage getStage(){
//		if(null == stage){
//			stage = new Stage();
//			stage.initOwner(Main.mainWidow);
//			stage.initModality(Modality.APPLICATION_MODAL);
//		}
//		return stage;
//	}
	
	/**
	 * 自溶（动物）
	 * 
	 * @param event
	 */
	@FXML
	void onAutolyzeBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		if(null != data_anatomyResultTable && data_anatomyResultTable.size() > 0){
			showErrorMessage("该动物已有剖检结果，不可设为自溶！");
			return ;
		}
		if(Messager.showSimpleConfirm("提示","确定将该动物标识为‘自溶’吗？")){
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("动物自溶--身份验证");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				Json json = BaseService.getInstance().getTblAnatomyAnimalService()
						.setAutolyzeFlag(animal.getTaskId(),animal.getAnimalCode());
				if(json.isSuccess()){
					updateAnimalTable(sessionIdList, animal.getStudyNo(), animal.getAnimalCode());
				}else{
					showErrorMessage(json.getMsg());
				}
			}
		}
	}

	/**更新使用频度
	 * @param event
	 */
	@FXML
	void onUpdateUserFrequentnessHyperlink(ActionEvent event){
//		tabPane.getSelectionModel().select(1);
//		tabPane.getSelectionModel().selectNext();
		BaseService.getInstance().getTblAnatomyCheckService().updateFreqCount();
		showMessage("使用频度更新成功！");
	}
	
	/**
	 * 选择动物
	 * 
	 * @param event
	 */
	@FXML
	void onSelectAnimalBtnAction(ActionEvent event) {
		try {
			for(String sessionId:sessionIdList){
			    //可以确认数据，将检查会话是否已确认
			    TblPathSession t=BaseService.getInstance().getTblPathSessionService().getById(sessionId);
			    if(null != t.getSessionFinishSign()){
			    	showErrorMessage("所选会话已签字确认,不可继续操作！");
			    	return;
			    }
			}
			if(sessionType == 4 || sessionType == 5 || sessionType == 6 || sessionType == 7){
				List<String> animalCodeList = BaseService.getInstance().getTblAnatomyAnimalService().getNoFixedFinishAnimalList(sessionIdList);
				if(null != animalCodeList && animalCodeList.size() > 0){
					String animalCodes = "";
					int i = 0;
					for(String animalCode :animalCodeList){
						if(i == 0){
							animalCodes = animalCodes+""+animalCode;
						}else if(i<4){
							animalCodes = animalCodes+"、"+animalCode;
						}
						i++;
					}
					if(i>4){
						animalCodes = animalCodes+"...";
					}
					if(!Messager.showConfirm("提示", animalCodes+" 固定未完成，", "是否继续？")){
						return;
					}
				}
			}
			//打开动物窗口
			openAnatomyAnimalPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 其他脏器
	 * 
	 * @param event
	 */
	@FXML
	void onOtherVisceraBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null != animal){
			try {
				
//				OtherViscera.getInstance().start(getStage());
				Stage stage = Main.stageMap.get("OtherViscera");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					OtherViscera.getInstance().start(stage);
					Main.stageMap.put("OtherViscera",stage);
				}else{
					stage.show();
				}
				OtherViscera.getInstance().loadData(null,animal.getTaskId(),animal.getStudyNo(),animal.getAnimalCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 登记
	 * 
	 * @param event
	 */
	@FXML
	void onRegisterBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null == treeSelectedItem){
			String bodyPos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			if(null == bodyPos){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}
		}
		String anatomyFinding = anatomyFindingListView.getSelectionModel().getSelectedItem();
		if(null == anatomyFinding){
			showErrorMessage("请先选择解剖学所见！");
			return ;
		}
		
		String msgStr = getAnatomyFinding();
//		if(data_anatomyResultTable.size() > 0){
//			for(AnatomyCheck anatomycheck:data_anatomyResultTable){
//				if(anatomycheck.getFinding().equals(msgStr)){
//					showErrorMessage("不可重复添加！");
//					return ;
//				}
//			}
//		}
		String sessionId = taskIdSessionIdMap.get(animal.getTaskId());
		String studyNo = animal.getStudyNo();
		String animalCode = animal.getAnimalCode();
		Integer visceraType = 100;
		String visceraCode = null;
		String visceraName = null;
		String subVisceraCode = null;
		String subVisceraName = null;
		
		Integer anatomyFindingFlag = 0;
		Integer autolyzaFlag = 0;
		Integer specialFlag = 0;//是否肿瘤，
		String anatomyFindingCode = null;
		if(tesuRadioButton.isSelected()){
			anatomyFindingFlag = 1;
			DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon2.getItemCode();
			specialFlag = dictPathCommon2.getSpecicalFlag();
		}else{
			DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon3.getItemCode();
			specialFlag = dictPathCommon3.getSpecicalFlag();
		}
		if(specialFlag == 2){
			autolyzaFlag = 2;
			specialFlag = 0;
		}
		if(autolyzaFlag == 2 && null == treeSelectedItem){
			showErrorMessage("请选择脏器！");
			return ;
		}
		
		Map<String,Object> visceraMap = null;
		if(null != treeSelectedItem){
			visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
			
			
//			if(treeSelectedItem.isLeaf()){
//				visceraType = (Integer) visceraMap.get("visceraType");
//				visceraCode = (String) visceraMap.get("visceraCode");
//				visceraName = (String) visceraMap.get("visceraName");
//				subVisceraCode = (String) visceraMap.get("subVisceraCode");
//				subVisceraName = (String) visceraMap.get("subVisceraName");
//			}else{
//				visceraType = (Integer) visceraMap.get("visceraType");
//				visceraCode = (String) visceraMap.get("visceraCode");
//				visceraName = (String) visceraMap.get("visceraName");
//			}
			if(treeSelectedItem.getParent() == rootNode){
				//父节点
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
			}else{
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				subVisceraName = (String) visceraMap.get("subVisceraName");
				
			}
			if(autolyzaFlag == 2 ){
				//判断该脏器是否已经标记自溶
				boolean isHasRecord = BaseService.getInstance().getTblAnatomyCheckService().isHasRecord(sessionId,animalCode,visceraCode,subVisceraCode);
				if(isHasRecord){
					showErrorMessage("该脏器已有异常记录或已标记自溶！");
					return ;
				}
			}
			//判断该脏器是否已经标记自溶
			boolean isHasAutolyze = BaseService.getInstance().getTblAnatomyCheckService().isHasAutolyze(sessionId,animalCode,visceraCode,subVisceraCode);
			if(isHasAutolyze){
				showErrorMessage("该脏器已标记自溶！");
				return ;
			}
			boolean isMissing = BaseService.getInstance().getTblAnatomyCheckService().isMissing(sessionId,animalCode,visceraCode,subVisceraCode);
			if(isMissing){
				showErrorMessage("该脏器已标记缺失！");
				return ;
			}
			if(data_anatomyResultTable.size() > 0){
				for(AnatomyCheck anatomycheck:data_anatomyResultTable){
					if(anatomycheck.getFinding().trim().replaceAll(" ", "").equals(msgStr.trim().replaceAll(" ", ""))
							&&((visceraName.equals(anatomycheck.getVisceraName()) 
									&& null == subVisceraCode)||
									anatomycheck.getVisceraName().equals(subVisceraName))){
						showErrorMessage("不可重复添加！");
						return ;
					}
				}
			}
		}else{
			if(data_anatomyResultTable.size() > 0){
				for(AnatomyCheck anatomycheck:data_anatomyResultTable){
					if(anatomycheck.getFinding().trim().replaceAll(" ", "").equals(msgStr.trim().replaceAll(" ", ""))){
						showErrorMessage("不可重复添加！");
						return ;
					}
				}
			}
		}
		
		String anatomyPosCode = null;
		String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
		if(null != anatomyPos){
			DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
			anatomyPosCode = dictPathCommon1.getItemCode();
		}
		
		boolean isQueRu = false;
		//确如，填写缺如原因
		if(autolyzaFlag == 2 ){
			String currentVisceraName = (String) visceraMap.get("visceraName");
			String subvisceraName = (String) visceraMap.get("subVisceraName");
			if(null != subvisceraName && !subvisceraName.equals("")){
				currentVisceraName = currentVisceraName +" - "+subvisceraName;
    		}
			
			try{
//    			MissingVisceraRecord.getInstance().start(getStage());
    			Stage stage = Main.stageMap.get("MissingVisceraRecord");
    			if(null == stage){
    				stage =new Stage();
    				stage.initOwner(Main.mainWidow);
    				stage.initModality(Modality.APPLICATION_MODAL);
    				MissingVisceraRecord.getInstance().start(stage);
    				Main.stageMap.put("MissingVisceraRecord",stage);
    			}
    			
    			MissingVisceraRecord.getInstance().loadDate(animal.getTaskId(),sessionId, studyNo, animalCode, currentVisceraName,visceraMap,sessionType,true);
    			stage.showAndWait();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
			if(MissingVisceraRecord.getInstance().isSuccess()){
				isQueRu = true;
			}else{
				return;
			}
		}
		
		
		String currentUserName = SaveUserInfo.getUserName();
		Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		
		TblAnatomyCheck tblAnatomyCheck = new TblAnatomyCheck();
		tblAnatomyCheck.setSessionId(sessionId);
		tblAnatomyCheck.setStudyNo(studyNo);
		tblAnatomyCheck.setAnimalCode(animalCode);
		tblAnatomyCheck.setVisceraType(visceraType);
		tblAnatomyCheck.setVisceraCode(visceraCode);
		tblAnatomyCheck.setVisceraName(visceraName);
		
		tblAnatomyCheck.setSubVisceraCode(subVisceraCode);
		tblAnatomyCheck.setSubVisceraName(subVisceraName);
		
		
		tblAnatomyCheck.setAnatomyPosCode(anatomyPosCode);
		tblAnatomyCheck.setAnatomyPos(anatomyPos);
		tblAnatomyCheck.setAnatomyFindingFlag(anatomyFindingFlag);
		
		tblAnatomyCheck.setSpecialFlag(specialFlag);
		
		tblAnatomyCheck.setAnatomyFindingCode(anatomyFindingCode);
		tblAnatomyCheck.setAnatomyFingding(anatomyFinding);
		String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setBodySurfacePos(bodySurfacePos);
		String pos = posListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setPos(pos);
		String shape = shapeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setShape(shape);
		String color = colorListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setColor(color);
		String texture = textureListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setTexture(texture);
		String number = numberListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setNumber(number);
		String range = rangeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setRange(range);
		String lesionDegree = lesionDegreeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setLesionDegree(lesionDegree);
		String size = sizeTextField.getText();
		tblAnatomyCheck.setSize(size);
		
		tblAnatomyCheck.setOperator(currentUserName);
		tblAnatomyCheck.setOperateTime(currentDate);
		tblAnatomyCheck.setAutolyzaFlag(autolyzaFlag);
		
		Json json =  null;
		if(isQueRu){
			json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck,MissingVisceraRecord.getInstance().getMissingRsn());
		}else{
			json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck);
		}
		
		
		if(json.isSuccess()){
			//
			tblAnatomyCheck.setId(json.getMsg());
			//增加一行 解剖结果 放在最后一行，并选中最后一行
			AddOneDataToAnatomyResultTable(tblAnatomyCheck);
			//清空 11个ListView 的选择
			clear11ListViewSelect();
			//脏器树 选择下一个
//			selectNext_visceraTree();
		}else{
			showMessage(json.getMsg());
		}
	}

	/**
	 * 自溶（脏器）
	 * 
	 * @param event
	 */
	//--------------------------------增加签字
	@FXML
	void onAutolyzeBtn2Action(ActionEvent event) {
		
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null == treeSelectedItem){
			showErrorMessage("请先选择脏器！");
			return ;
		}
		String sessionId = taskIdSessionIdMap.get(animal.getTaskId());
		String studyNo = animal.getStudyNo();
		String animalCode = animal.getAnimalCode();
		Map<String,Object> visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
		Integer visceraType = (Integer) visceraMap.get("visceraType");
		String visceraCode = (String) visceraMap.get("visceraCode");
		String visceraName = (String) visceraMap.get("visceraName");
		String subVisceraCode = null;
		String subVisceraName = null;
		if(treeSelectedItem.getParent() != rootNode ){
			subVisceraCode = (String) visceraMap.get("subVisceraCode");
			subVisceraName = (String) visceraMap.get("subVisceraName");
		}
		//判断该脏器是否已经标记自溶
		boolean isHasRecord = BaseService.getInstance().getTblAnatomyCheckService().isHasRecord(sessionId,animalCode,visceraCode,subVisceraCode);
		if(isHasRecord){
			showErrorMessage("该脏器已有异常记录或已标记自溶！");
			return ;
		}
		if(Messager.showSimpleConfirm("提示","确定将该脏器标识为‘自溶’吗？")){
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("动物自溶--身份验证");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				
				Integer autolyzeFlag = 1;//自溶
				
				String currentUserName = SaveUserInfo.getUserName();
				Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
				
				TblAnatomyCheck tblAnatomyCheck = new TblAnatomyCheck();
				tblAnatomyCheck.setSessionId(sessionId);
				tblAnatomyCheck.setStudyNo(studyNo);
				tblAnatomyCheck.setAnimalCode(animalCode);
				tblAnatomyCheck.setVisceraType(visceraType);
				tblAnatomyCheck.setVisceraCode(visceraCode);
				tblAnatomyCheck.setVisceraName(visceraName);
				if(treeSelectedItem.getParent() != rootNode ){
					tblAnatomyCheck.setSubVisceraCode(subVisceraCode);
					tblAnatomyCheck.setSubVisceraName(subVisceraName);
				}
				
				tblAnatomyCheck.setAutolyzaFlag(autolyzeFlag);//自溶解剖1
				
				tblAnatomyCheck.setOperator(currentUserName);
				tblAnatomyCheck.setOperateTime(currentDate);	
				
				Json json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck);
				if(json.isSuccess()){
					//
					tblAnatomyCheck.setId(json.getMsg());
					//增加一行 解剖结果 放在最后一行，并选中最后一行
					AddOneDataToAnatomyResultTable(tblAnatomyCheck);
					//清空 11个ListView 的选择
					clear11ListViewSelect();
					//脏器树 选择下一个
					selectNext_visceraTree();
				}else{
					showMessage(json.getMsg());
				}
				
			}
		}
		
		
	}

	/**
	 * 其余未见异常
	 * 
	 * @param event
	 */
	@FXML
	void onOtherNaBtn(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
//		String taskId=animal.getTaskId();
//		String sessionId = taskIdSessionIdMap.get(animal.getTaskId());
		String animalCode = animal.getAnimalCode();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblPathSessionService().getUserNameRealName(sessionIdList);
		if(null != mapList && mapList.size() > 1){
			//有多个解剖者
			List<String> realNameList = new ArrayList<String>();
			for(Map<String,Object> map :mapList){
//				String userName = (String) map.get("userName");
//				String realName = (String) map.get("realName");
				realNameList.add((String) map.get("realName"));
			}
			
			try {
				Stage stage = Main.stageMap.get("ConfirmAnatomy");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					ConfirmAnatomy.getInstance().start(stage);
					Main.stageMap.put("ConfirmAnatomy",stage);
				}
				ConfirmAnatomy.getInstance().loadData(realNameList,animal.getAnatomyOperator(),animalCode);
				stage.showAndWait();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(ConfirmAnatomy.getInstance().isSuccess()){
				Json json = BaseService.getInstance().getTblAnatomyAnimalService().setAnatomyFinish(animal.getStudyNo(),animal.getAnimalCode());
				if(json.isSuccess()){
					//更新动物表格数据并选中指定动物
					updateAnimalTable(sessionIdList,animal.getStudyNo(),animal.getAnimalCode());
					if(sessionType == 1){
//					animalTable.getSelectionModel().selectNext();
						int index=animalTable.getSelectionModel().getSelectedIndex();
						if(index==data_animalTable.size()-1){
							//打开动物窗口
							openAnatomyAnimalPage();
						}else{
							animalTable.getSelectionModel().selectNext();
						}
					}else{
						tabPane.getSelectionModel().select(1);
					}
//				List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService()
//						.getVisceraMapListByTaskIdSessionIdAnimalCode(taskId, sessionId, animalCode);
//				if(null!=mapList && mapList.size()>0){
//					for(Map<String,Object> map :mapList){
//						String visceraName = (String) map.get("visceraName");
//						needFixedVisceraName.add(visceraName);
//					}
//				}
				}else{
					showErrorMessage(json.getMsg());
				}
			}
			
			
		}else{
			//仅有一个解剖者
			if(Messager.showConfirm("提示","标记'其余未见异常'后将不可以修改","确定继续吗？")){
				Json json = BaseService.getInstance().getTblAnatomyAnimalService().setAnatomyFinish(animal.getStudyNo(),animal.getAnimalCode());
				if(json.isSuccess()){
					//更新动物表格数据并选中指定动物
					updateAnimalTable(sessionIdList,animal.getStudyNo(),animal.getAnimalCode());
					if(sessionType == 1){
//					animalTable.getSelectionModel().selectNext();
						int index=animalTable.getSelectionModel().getSelectedIndex();
						if(index==data_animalTable.size()-1){
							//打开动物窗口
							openAnatomyAnimalPage();
						}else{
							animalTable.getSelectionModel().selectNext();
						}
					}else{
						tabPane.getSelectionModel().select(1);
					}
//				List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService()
//						.getVisceraMapListByTaskIdSessionIdAnimalCode(taskId, sessionId, animalCode);
//				if(null!=mapList && mapList.size()>0){
//					for(Map<String,Object> map :mapList){
//						String visceraName = (String) map.get("visceraName");
//						needFixedVisceraName.add(visceraName);
//					}
//				}
				}else{
					showErrorMessage(json.getMsg());
				}
			}
		}
	}
	/**解剖学所见部位(刷新ListView)
	 * @param event
	 */
	@FXML
	void onanatomyPosHyperlink(ActionEvent event){
		
		String selectItem = anatomyPosListView.getSelectionModel().getSelectedItem();
		
		//解剖学所见部位
		TreeItem<String> newValue = visceraTree.getSelectionModel().getSelectedItem();
		if(null != newValue){
			String visceraName = newValue.getValue();
			Map<String,Object> map = visceraName2MapMap.get(visceraName);
			if(null != map){
				String visceraCode = (String) map.get("visceraCode");
				//根据脏器编号更新 解剖学所见部位
				updateAnatomyPosListViewData(visceraCode);
			}
		}else{
			//清空ListView
			updateAnatomyPosListViewData(null);
		}
		if(null != selectItem && data_anatomyPosListView.size() > 0){
			for(String str : data_anatomyPosListView){
				if(str.equals(selectItem)){
					anatomyPosListView.getSelectionModel().select(str);
					break;
				}
			}
		}
	}

	/**解剖所见(刷新ListView)
	 * @param event
	 */
	@FXML
	void onanatomyFindingHyperlink(ActionEvent event){
		//解剖所见
		String selectItem  = anatomyFindingListView.getSelectionModel().getSelectedItem();
		
		//解剖通用所见
			data_anatomyFindingListView_tongyong.clear();
			pyDescCNMap_tongyong.clear();
			anatomyFinding_tongyongDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictType(2,sortMethod);
			if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
				for(DictPathCommon obj:dictPathCommon2List){
					data_anatomyFindingListView_tongyong.add(obj.getDescCn());
					if(obj.getDescCn().equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(selectItem);
					}
					anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
					pyDescCNMap_tongyong.put(obj.getPy(), obj.getDescCn());
				}
			}
			
			//解剖特殊所见
			//更新ListView  
			TreeItem<String> treeSelectItem = visceraTree.getSelectionModel().getSelectedItem();
			if(null != treeSelectItem){
				String visceraName = treeSelectItem.getValue();
				Map<String,Object> map = visceraName2MapMap.get(visceraName);
				if(null != map){
					String visceraCode = (String) map.get("visceraCode");
					//根据脏器编号更新 解剖学所见(通用，特殊)
					updateAnatomyFindingListViewData(visceraCode);
				}
			}else{
				updateAnatomyFindingListViewData(null);
			}
			
		if(tongyongRadioButton.isSelected()){
			if(null != selectItem && data_anatomyFindingListView_tongyong.size() > 0){
				for(String str : data_anatomyFindingListView_tongyong){
					if(str.equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(str);
						break;
					}
				}
			}
		}else{
			if(null != selectItem && data_anatomyFindingListView_tesu.size() > 0){
				for(String str : data_anatomyFindingListView_tesu){
					if(str.equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(str);
						break;
					}
				}
			}
		}
		
	}
	/**体表部位(刷新ListView)
	 * @param event
	 */
	@FXML
	void onbodySufaceHyperlink(ActionEvent event){
		//4:体表部位
		String selectItem  = bodySufacePosListView.getSelectionModel().getSelectedItem();
		data_bodySufacePosListView.clear();
		pyDescCNMap_bodySuface.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,sortMethod);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					bodySufacePosListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_bodySuface.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**位置(刷新ListView)
	 * @param event
	 */
	@FXML
	void onposHyperlink(ActionEvent event){
		//5:位置
		String selectItem  = posListView.getSelectionModel().getSelectedItem();
		data_posListView.clear();
		pyDescCNMap_pos.clear();
		List<DictPathCommon> dictPathCommon5List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(5,sortMethod);
		if(null != dictPathCommon5List && dictPathCommon5List.size() > 0){
			for(DictPathCommon obj:dictPathCommon5List){
				data_posListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					posListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_pos.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**范围(刷新ListView)
	 * @param event
	 */
	@FXML
	void onrangeHyperlink(ActionEvent event){
		//6:分布、范围
		String selectItem  = rangeListView.getSelectionModel().getSelectedItem();
		data_rangeListView.clear();
		pyDescCNMap_range.clear();
		List<DictPathCommon> dictPathCommon6List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(6,sortMethod);
		if(null != dictPathCommon6List && dictPathCommon6List.size() > 0){
			for(DictPathCommon obj:dictPathCommon6List){
				data_rangeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					rangeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_range.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**数量(刷新ListView)
	 * @param event
	 */
	@FXML
	void onnumberHyperlink(ActionEvent event){
		//7:数量
		String selectItem  = numberListView.getSelectionModel().getSelectedItem();
		data_numberListView.clear();
		pyDescCNMap_number.clear();
		List<DictPathCommon> dictPathCommon7List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(7,sortMethod);
		if(null != dictPathCommon7List && dictPathCommon7List.size() > 0){
			for(DictPathCommon obj:dictPathCommon7List){
				data_numberListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					numberListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_number.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**形状(刷新ListView)
	 * @param event
	 */
	@FXML
	void onshapeHyperlink(ActionEvent event){
		//8:形状
		String selectItem  = shapeListView.getSelectionModel().getSelectedItem();
		data_shapeListView.clear();
		pyDescCNMap_shape.clear();
		List<DictPathCommon> dictPathCommon8List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(8,sortMethod);
		if(null != dictPathCommon8List && dictPathCommon8List.size() > 0){
			for(DictPathCommon obj:dictPathCommon8List){
				data_shapeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					shapeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_shape.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**颜色(刷新ListView)
	 * @param event
	 */
	@FXML
	void oncolorHyperlink(ActionEvent event){
		//9:颜色
		String selectItem  = colorListView.getSelectionModel().getSelectedItem();
		data_colorListView.clear();
		pyDescCNMap_color.clear();
		List<DictPathCommon> dictPathCommon9List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(9,sortMethod);
		if(null != dictPathCommon9List && dictPathCommon9List.size() > 0){
			for(DictPathCommon obj:dictPathCommon9List){
				data_colorListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					colorListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_color.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**硬度(刷新ListView)
	 * @param event
	 */
	@FXML
	void ontextureHyperlink(ActionEvent event){
		//10:硬度
		String selectItem  = textureListView.getSelectionModel().getSelectedItem();
		data_textureListView.clear();
		pyDescCNMap_texture.clear();
		List<DictPathCommon> dictPathCommon10List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(10,sortMethod);
		if(null != dictPathCommon10List && dictPathCommon10List.size() > 0){
			for(DictPathCommon obj:dictPathCommon10List){
				data_textureListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					textureListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_texture.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**大小(刷新ListView)
	 * @param event
	 */
	@FXML
	void onSizeHyperlink(ActionEvent event){
		//11:大小
		String selectItem  = sizeListView.getSelectionModel().getSelectedItem();
		data_sizeListView.clear();
		pyDescCNMap_size.clear();
		List<DictPathCommon> dictPathCommon11List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(11,sortMethod);
		if(null != dictPathCommon11List && dictPathCommon11List.size() > 0){
			for(DictPathCommon obj:dictPathCommon11List){
				data_sizeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					sizeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_size.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**病变程度(刷新ListView)
	 * @param event
	 */
	@FXML
	void onlesionHyperlink(ActionEvent event){
		//12:病变程度
		String selectItem  = lesionDegreeListView.getSelectionModel().getSelectedItem();
		data_lesionDegreeListView.clear();
		pyDescCNMap_lesionDegree.clear();
		List<DictPathCommon> dictPathCommon12List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(12,sortMethod);
		if(null != dictPathCommon12List && dictPathCommon12List.size() > 0){
			for(DictPathCommon obj:dictPathCommon12List){
				data_lesionDegreeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					lesionDegreeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_lesionDegree.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**快速查找TextField  键盘按下起来事件
	 * @param event
	 */
	@FXML
	private void OnSearchTextFieldKeyReleased(KeyEvent event){
//		"Enter";
		System.out.println(event.getCode().getName());
		if("Down".equals(event.getCode().getName())){
			searchListView.getSelectionModel().select(0);
			searchListView.requestFocus();
		}else{
			showSearchListView();
		}
	}
	
	
	

	/**
	 * 全选
	 * 
	 * @param event
	 */
	@FXML
	void onSelectAllHyperlink(ActionEvent event) {
		if(null != data_visceraListView){
			for(CheckBox checkbox:data_visceraListView){
				checkbox.setSelected(true);
			}
		}
	}
	/**
	 * 全不选
	 * 
	 * @param event
	 */
	@FXML
	void onSelectNoOneHyperlink(ActionEvent event) {
		if(null != data_visceraListView){
			for(CheckBox checkbox:data_visceraListView){
				checkbox.setSelected(false);
			}
		}
	}
	/**
	 * 筛选无需称重脏器
	 * 
	 * @param event
	 */
	@FXML
	void onChooseSelectHyperlink(ActionEvent event) {
		onSelectNoOneHyperlink(null);
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null != animal){
			String studyNo=animal.getStudyNo();
			String taskId=animal.getTaskId();
			String sessionId = taskIdSessionIdMap.get(taskId);
			String animalCode = animal.getAnimalCode();
			List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getNeedNotWeighMapList(taskId, sessionId, animalCode,studyNo);
			if(null!=list && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> map=list.get(i);
					for(int j=0;j<data_visceraListView.size();j++){
						if(data_visceraListView.get(j).getText().equals((String)map.get("visceraName"))){
							CheckBox checkbox=data_visceraListView.get(j);
							checkbox.setSelected(true);
							break;
						}
					}
				}
			}
		}
	}
	/**
	 * 固定（>>）
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	void onFixedBtn(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		//已固定的脏器名称列表
		List<String> fixedViscerNameList = new ArrayList<String>();
		if(data_visceraFixedTable.size() > 0){
			for(VisceraFixed viscerafixed:data_visceraFixedTable){
				fixedViscerNameList.add(viscerafixed.getVisceraName());
			}
		}
		//选择的脏器名称列表
		List<String> selectedViscerNameList = new ArrayList<String>();
		//选择的脏器列表(map:visceraType,visceraName,visceraCode,sn)
		List<Map<String,Object>> selectedMapList = new ArrayList<Map<String,Object>>();
		if(data_visceraListView.size() > 0){
			for(CheckBox checkBox:data_visceraListView){
				if(checkBox.isSelected()){
					selectedViscerNameList.add(checkBox.getText());
					if(!fixedViscerNameList.contains(checkBox.getText())){
						selectedMapList.add((Map<String, Object>) checkBox.getUserData());
					}
				}
			}
		}
		
		if(selectedViscerNameList.size()<1){
			showErrorMessage("请先选择脏器！");
			return ;
		}
		
		
		
		boolean flag = false;	//没有重复添加
		//选择的脏器名称列表     与
		//已固定的脏器名称列表   是否有重复
		for(String visceraName1:selectedViscerNameList){
			for(String visceraName2:fixedViscerNameList){
				if(visceraName1.equals(visceraName2)){
					flag = true;
					break;
				}
			}
			if(flag){
				break;
			}
		}
		String taskId = animal.getTaskId();
		String sessionId = taskIdSessionIdMap.get(taskId);
		String studyNo = animal.getStudyNo();
		String animalCode = animal.getAnimalCode();
		//存在重复
		if(flag){
			if(Messager.showConfirm("提示","重复固定的脏器数据将被忽略，","确定继续吗？")){
				saveViscerFixedAndClearSelect(sessionId,studyNo,animalCode,selectedMapList);
				updateData_visceraListView(taskId, sessionId, animalCode);
			}
		}else{
			saveViscerFixedAndClearSelect(sessionId,studyNo,animalCode,selectedMapList);
			updateData_visceraListView(taskId, sessionId, animalCode);
		}
	}
	/**  保存 固定信息，清空脏器选择列表，刷新固定信息表
	 * @param sessionId
	 * @param studyNo
	 * @param animalCode
	 * @param selectedMapList  (map:visceraType,visceraName,visceraCode,sn)
	 */
	private void saveViscerFixedAndClearSelect(String sessionId, String studyNo, String animalCode,
			List<Map<String, Object>> selectedMapList) {
		String userName = SaveUserInfo.getUserName();
		Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		Json json = BaseService.getInstance().getTblVisceraFixedService().saveList(sessionId,studyNo,animalCode,userName,date,selectedMapList);
		if(json.isSuccess()){
			//清空脏器选择列表
			onSelectNoOneHyperlink(null);
			//刷新固定信息表
			updateData_visceraFixedTable(sessionId, animalCode,true);
		}else{
			showErrorMessage(json.getMsg());
			return ;
		}
	}
     /**脏器固定-缺失脏器登记
     * @param event
     */
    @SuppressWarnings("unchecked")
	@FXML
     private void onVisceraMissingButton(ActionEvent event){
    	try{
    		Animal animal = animalTable.getSelectionModel().getSelectedItem();
    		if(null == animal){
    			showErrorMessage("请先选择动物！");
    			return ;
    		}
    		

    		String selectId = tabPane.getSelectionModel().getSelectedItem().getText();
    		String visceraName= "";
    		Map<String,Object> map = new HashMap<String, Object>(); 
            if(selectId.equals("脏器称重")){
            	TreeItem<Label> treeSelectedItem = visceraTree_weight.getSelectionModel().getSelectedItem();
        		if(null == treeSelectedItem){
        			showErrorMessage("请先选择脏器！");
        			return ;
        		}
        		map = (Map<String, Object>)treeSelectedItem.getValue().getUserData();
       	        visceraName= (String)map.get("visceraName");
       	        
       	        Integer partVisceraSeparateWeigh = (Integer) map.get("partVisceraSeparateWeigh");
	       	    if(null != partVisceraSeparateWeigh && partVisceraSeparateWeigh == 1){
	     			if(!treeSelectedItem.isLeaf()){
	     				showErrorMessage("成对脏器分开请分开登记！");
	     				return ;
	     			}
	     		}
	       	    String subvisceraName= (String)map.get("subVisceraName");
        		if(null != subvisceraName && !subvisceraName.equals("")){
        			visceraName = visceraName +" - "+subvisceraName;
        		}
        		
//        		String selectedVisceraName = treeSelectedItem.getValue().getText().trim();
//        		for(VisceraWeight visceraWeight:data_visceraWeightTable){
//        			if(selectedVisceraName.equals(visceraWeight.getVisceraName())){
//        				
//        			}
//        		}
        	     
        	     
            }else if(selectId.equals("脏器固定")){
            	//选择的脏器名称列表
        		List<String> selectedViscerNameList = new ArrayList<String>();
        		//选择的脏器列表(map:visceraType,visceraName,visceraCode,sn)
        		List<Map<String,Object>> selectedMapList = new ArrayList<Map<String,Object>>();
        		if(data_visceraListView.size() > 0){
        			for(CheckBox checkBox:data_visceraListView){
        				if(checkBox.isSelected()){
        					selectedViscerNameList.add(checkBox.getText());
        					selectedMapList.add((Map<String, Object>) checkBox.getUserData());
        				}
        			}
        		}
        		if(selectedViscerNameList.size()<1){
        			showErrorMessage("请先选择脏器！");
        			return ;
        		}
        		if(selectedViscerNameList.size()>1){
        			showErrorMessage("脏器缺失登记需逐个进行！");
        			return ;
        		}
        		visceraName= selectedViscerNameList.get(0);
        		map = selectedMapList.get(0);
            }
    	
    		
    		String taskId=animal.getTaskId();
    		String sessionId=taskIdSessionIdMap.get(taskId);
    		String studyNo=animal.getStudyNo();
    		String animalCode=animal.getAnimalCode();
    		
//    		MissingVisceraRecord.getInstance().start(getStage());
//    		MissingVisceraRecord.getInstance().loadDate(taskId,sessionId, studyNo, animalCode, visceraName,map,sessionType);
    		try{
//    			MissingVisceraRecord.getInstance().start(getStage());
    			Stage stage = Main.stageMap.get("MissingVisceraRecord");
    			if(null == stage){
    				stage =new Stage();
    				stage.initOwner(Main.mainWidow);
    				stage.initModality(Modality.APPLICATION_MODAL);
    				MissingVisceraRecord.getInstance().start(stage);
    				Main.stageMap.put("MissingVisceraRecord",stage);
    			}
    			
    			MissingVisceraRecord.getInstance().loadDate(taskId,sessionId, studyNo, animalCode, visceraName,map,sessionType,false);
    			stage.show();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    		
		}catch(Exception e){
			e.printStackTrace();
		}
     }
	/**
	 * 固定完成
	 * 
	 * @param event
	 */
	@FXML
	void onFixedFinishBtn(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
//		String taskId=animal.getTaskId();
//		String sessionId=taskIdSessionIdMap.get(taskId);
//		String studyNo=animal.getStudyNo();
		String animalCode=animal.getAnimalCode();
		List<String> haveFixedViscera=new ArrayList<String>();
		if(data_visceraFixedTable.size()>0){
			for(int i=0;i<data_visceraFixedTable.size();i++){
				String visceraName=data_visceraFixedTable.get(i).getVisceraName();
				haveFixedViscera.add(visceraName);
			}
		}
//		if(data_visceraFixedTable.size()<1){
//			showErrorMessage("请先添加脏器固定信息！");
//			return ;
//		}
//		List<String> notFixedVisceraList=new ArrayList<String>();
		if(data_visceraListView.size() > 0){
			for(CheckBox checkBox:data_visceraListView){
//				if(!haveFixedViscera.contains(checkBox.getText())){
//					notFixedVisceraList.add(checkBox.getText());
//				}
//				notFixedVisceraList.add(checkBox.getText());
				showErrorMessage(checkBox.getText()+"未固定");
				return;
			}
		}
		//判断未固定的脏器是否已自溶
//		if(notFixedVisceraList.size()>0){
//			for(int i=0;i<notFixedVisceraList.size();i++){
//				TblAnatomyCheck tblAnatomyCheck=BaseService.getInstance().getTblAnatomyCheckService().getByVisceraName(sessionId,studyNo,animalCode,notFixedVisceraList.get(i));
//				if(tblAnatomyCheck==null){
//					Alert2.create(notFixedVisceraList.get(i)+"未固定");
//					return;
//				}else{
//					if(tblAnatomyCheck.getAutolyzaFlag()!=1){
//						Alert2.create(notFixedVisceraList.get(i)+"未固定");
//						return;
//					}
//				}
//			}
//			List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getVisceraNameAndisHasAutolyzeMap(sessionId,studyNo,animalCode,notFixedVisceraList);
//			if(null!=list && list.size()>0){
//				for(Map<String,Object> map:list){
//					if((Integer)map.get("autolyzaFlag")!=1){
//						Alert2.create((String)map.get("visceraName")+"未固定");
//						return;
//					}
//				}
//			}
//		}
		//必须固定的非常规病变 
		TblStudyPlan tblStudyPlan=BaseService.getInstance().getTblStudyPlanService().getByStudyNo(animal.getStudyNo());
		//异常组织需要固定
		if(null!=tblStudyPlan && tblStudyPlan.getAbnVisceraFixedFlag()==1){
			if(null!=mustFixedAnatomyCheckId && mustFixedAnatomyCheckId.size()>0){
				for(int i=0;i<mustFixedAnatomyCheckId.size();i++){
					TblVisceraFixed tblVisceraFixed=BaseService.getInstance().getTblVisceraFixedService().getByAnatomyCheckId(mustFixedAnatomyCheckId.get(i));
					if(tblVisceraFixed==null){
						TblAnatomyCheck tblAnatomyCheck=BaseService.getInstance().getTblAnatomyCheckService().getById(mustFixedAnatomyCheckId.get(i));
						String antomyFinding="";
						String bodySurfacePos="";
						String visceraName="";
						if(null!=tblAnatomyCheck){
							antomyFinding=tblAnatomyCheck.getAnatomyFingding();
							bodySurfacePos=tblAnatomyCheck.getBodySurfacePos();
							visceraName=tblAnatomyCheck.getVisceraName();
						}
						String msg="";
						if(null!=visceraName){
							msg=visceraName+antomyFinding+"未固定";
						}else{
							msg=bodySurfacePos+antomyFinding+"未固定";
						}
						showErrorMessage(msg);
						return;
					}
				}
			}
		}
		
		//检查动物是否是否称重完成
		boolean visceraWeighFinish = BaseService.getInstance().getTblAnatomyTaskService().isVisceraWeighFinish(animal.getTaskId(),animal.getAnimalCode());
		if(!visceraWeighFinish){
			showErrorMessage("动物（"+animalCode+"）称重未完成！");
			return ;
		}
		if(Messager.showConfirm("提示","动物："+animalCode+"固定完成，","是否确认？")){
			Json json = BaseService.getInstance().getTblAnatomyAnimalService().setVisceraFixedFinishSign(animal.getStudyNo(),animal.getAnimalCode());
			if(json.isSuccess()){
				//更新动物表格数据并选中指定动物
				updateAnimalTable(sessionIdList,animal.getStudyNo(),animal.getAnimalCode());
				fixedFinishBtn.setDisable(true);
				if(sessionType==4){
					 
				}else{
					tabPane.getSelectionModel().select(0);
				}
				int index=animalTable.getSelectionModel().getSelectedIndex();
				 if(index==data_animalTable.size()-1){
					//打开动物窗口
					openAnatomyAnimalPage();
				 }else{
					 animalTable.getSelectionModel().selectNext();
				 }
			}else{
				showErrorMessage(json.getMsg());
			}
		}
		
	}
	/**
	 * 称重完成
	 * 
	 * @param event
	 */
	@FXML
	private void onWeightFinishBtn(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			isget =  false;
			showErrorMessage("请先选择动物！");
			isget =  true;
			return ;
		}
//		if(data_visceraWeightTable.size()<1){
//			isget =  false;
//			showErrorMessage("请先添加脏器称重信息！");
//			isget =  true;
//			return ;
//		}

		 ObservableList<TreeItem<Label>> children = visceraTree_weight.getRoot().getChildren();
		 for(int i = 0 ;i < children.size();i++){
			 TreeItem<Label> treeItem = children.get(i);
		     @SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>)treeItem.getValue().getUserData();
//			 String visceraName = (String) map.get("visceraName");
//			 String subVisceraName = (String) map.get("subVisceraName");
			 Integer partVisceraSeparateWeigh = (Integer) map.get("partVisceraSeparateWeigh");
			 String visceraCode = (String) map.get("visceraCode");
			//脏器是否缺失
				TblVisceraMissing visceraMissing = BaseService.getInstance().getTblVisceraMissingService().
				getVisceraByStudyNoAndAnimalCodeAndVisceraCodeOrSubVisceraCode(animal.getStudyNo(), animal.getAnimalCode(), visceraCode, null);
				if(null == visceraMissing || visceraMissing.equals("")){
				    if(treeItem.isLeaf()){
				       boolean falg = false;
					   for(int k = 0; k < data_visceraWeightTable.size() ; k++){
						   String 	kvisceraName = data_visceraWeightTable.get(k).getVisceraName();//要校验的脏器
							if(treeItem.getValue().getText().equals( kvisceraName)){
								falg = true;
							}
					    }
					   if(!falg){
							showErrorMessage("脏器 “"+treeItem.getValue().getText()+"”未称重，请检查录入！");
							isget =  true;
							visceraTree_weight.getSelectionModel().select(treeItem);
							return;
					    }
					}else{
						
						 ObservableList<TreeItem<Label>> children2 = treeItem.getChildren();
						 if(partVisceraSeparateWeigh == 1){
							 for(int j = 0 ;j < children2.size();j++){
								 TreeItem<Label> treeItem2 = children2.get(j);
								  boolean falg = false;
								    for(int k = 0; k < data_visceraWeightTable.size() ; k++){
										String 	kvisceraName = data_visceraWeightTable.get(k).getVisceraName();//要校验的脏器
										if(treeItem2.getValue().getText().equals( kvisceraName)){
											falg = true;
										}
								    }
								    if(!falg){
								    	showErrorMessage("脏器 “"+treeItem2.getValue().getText()+"”未称重，请检查录入！");
								    	isget =  true;
								    	visceraTree_weight.getSelectionModel().select(treeItem2);
								    	return;
								    }
								    
							 }
						 }else{
							 
							 boolean falg = false;
							 for(int k = 0; k < data_visceraWeightTable.size() ; k++){
									String 	kvisceraName = data_visceraWeightTable.get(k).getVisceraName();//要校验的脏器
									
									if(treeItem.getValue().getText().equals( kvisceraName)){
										falg = true;
									}
							    }
							 if(!falg){
								 for(int j = 0 ;j < children2.size();j++){
									 TreeItem<Label> treeItem2 = children2.get(j);
									  falg = false;
									    for(int k = 0; k < data_visceraWeightTable.size() ; k++){
											String 	kvisceraName = data_visceraWeightTable.get(k).getVisceraName();//要校验的脏器
											if(treeItem2.getValue().getText().equals( kvisceraName)){
												falg = true;
											}
									    }
									    if(!falg){
									    	showErrorMessage("脏器 “"+treeItem2.getValue().getText()+"”未称重，请检查录入！");
									    	isget =  true;
									    	visceraTree_weight.getSelectionModel().select(treeItem2);
									    	return;
									    }
									    
								 }
							  }
							 
						}
						
						
					 }
				}
		  }
		
//	    boolean falg0= true;
//		int j = 0;
//		while (falg0) {
//			 TreeItem<Label>  treeSelectedItem = visceraTree_weight.getTreeItem(j);
//			 if(null != treeSelectedItem){
//				    Map<String,Object> map = (Map<String, Object>)treeSelectedItem.getValue().getUserData();
//					String visceraName = (String) map.get("visceraName");
//					String subVisceraName = (String) map.get("subVisceraName");
//					Integer partVisceraSeparateWeigh = (Integer) map.get("partVisceraSeparateWeigh");
//					String visceraCode = (String) map.get("visceraCode");
//					if(treeSelectedItem.isLeaf() && null != subVisceraName && (!subVisceraName.equals(""))){
//						if(null != partVisceraSeparateWeigh && partVisceraSeparateWeigh == 1){
//								boolean falg = false;
//							   for(int i = 0; i < data_visceraWeightTable.size() ; i++){
//									if(	data_visceraWeightTable.get(i).getVisceraName().equals(subVisceraName)){
//										falg = true;
//									}
//									 
//								}
//							   if(!falg){
//								   String subVisceraCode = (String) map.get("subVisceraCode");
//								   TblVisceraMissing visceraMissing = BaseService.getInstance().getTblVisceraMissingService().
//											getVisceraByStudyNoAndAnimalCodeAndVisceraCodeOrSubVisceraCode(animal.getStudyNo(), animal.getAnimalCode(), visceraCode, subVisceraCode);
//								   if(null == visceraMissing || visceraMissing.equals("")){
//									   showErrorMessage("脏器  “"+subVisceraName+"”未称重，请检查录入！");
//									   isget =  true;
//									   visceraTree_weight.getSelectionModel().select(j);
//									   return;
//								   }
//							   }
//						}
//					}else{
//						boolean isHave = false;
//						for(int i = 0; i < data_visceraWeightTable.size() ; i++){
//							if(	data_visceraWeightTable.get(i).getVisceraName().equals(visceraName)){
//								isHave = true;
//								break;
//							}
//							 
//						}
//							if( isHave){
//								//脏器是否缺失
//								TblVisceraMissing visceraMissing = BaseService.getInstance().getTblVisceraMissingService().
//								getVisceraByStudyNoAndAnimalCodeAndVisceraCodeOrSubVisceraCode(animal.getStudyNo(), animal.getAnimalCode(), visceraCode, null);
//								if(null == visceraMissing || visceraMissing.equals("")){
//									List<TreeItem<Label>> List= treeSelectedItem.getChildren();
//									if(!isHave){
//										for(TreeItem<Label> obj:List){
//											 Map<String,Object> submap = (Map<String, Object>)obj.getValue().getUserData();
//											 String subVisceraNames = (String) submap.get("subVisceraName");
//											 for(int i = 0; i < data_visceraWeightTable.size() ; i++){
//												 if(!subVisceraNames.equals(data_visceraWeightTable.get(i).getVisceraName())){
//													 showErrorMessage("脏器 “"+visceraName+"”未称重，请检查录入！");
//														isget =  true;
//														visceraTree_weight.getSelectionModel().select(obj);
//														return;
//												 }
//			    							 }
//										}
//								}
//							}
//							
//						}
//						
//						
//					}
//					j++;
//		    }else{
//		    	 falg0= false;
//		    }
//			
//		}
		String animalCode =  animalCodeLabel.getText();
		if(Messager.showConfirm("提示","动物"+animalCode+"称重完成","确定继续吗？")){
			
			TblAnatomyAnimal tblAnatomyAnimal_ = BaseService.getInstance().getTblAnatomyAnimalService()
					.getByStudyNoAnimalCode(animal.getStudyNo(), animal.getAnimalCode());
			if(null != tblAnatomyAnimal_ && tblAnatomyAnimal_.getAnatomyCheckFinishFlag() == 0){
				showErrorMessage("动物"+tblAnatomyAnimal_.getAnimalCode()+"解剖未完成！");
				return;
			}
			
			String state  = animal.getState();
			boolean falg = false;
			if(state.equals("固定后称重中")){
				falg = true;
			}
			Json json = BaseService.getInstance().getTblAnatomyAnimalService().setVisceraWeightFinishSign(animal.getStudyNo(),animal.getAnimalCode(),falg);
			if(json.isSuccess()){
				updateAnimalTable(sessionIdList,animal.getStudyNo(),animal.getAnimalCode());
				weightFinishBtn.setDisable(true);
				visceraMissingButton_weight.setDisable(true);
				visceraMissingButton_weight.setDisable(true);
//				tabPane.getSelectionModel().selectNext();
				if(sessionType==3){
					tabPane.getSelectionModel().select(0);
					int index=animalTable.getSelectionModel().getSelectedIndex();
					 if(index==data_animalTable.size()-1){
						//打开动物窗口
						openAnatomyAnimalPage();
					 }else{
						 animalTable.getSelectionModel().selectNext();
					 }
				}else if(sessionType==6||sessionType==7){
					tabPane.getSelectionModel().selectNext();
				}else if(sessionType==2){
					int index=animalTable.getSelectionModel().getSelectedIndex();
					 if(index==data_animalTable.size()-1){
						//打开动物窗口
						openAnatomyAnimalPage();
					 }else{
						 animalTable.getSelectionModel().selectNext();
					 }
				}
				
			}else{
				showErrorMessage(json.getMsg());
			}
			isget =  true;
		}else{
			isget =  true;
		}
		
		
	}
	
	/**天平校准
	 * @param event
	 */
	@FXML
	private void onBalCalibration(ActionEvent event){
		try{
			closePort();
			Stage stage = Main.stageMap.get("AddTblBalCalibration");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AddTblBalCalibration.getInstance().start(stage);
				Main.stageMap.put("AddTblBalCalibration",stage);
			}else{
				stage.show();
			}
			String balCode = "";
			if(data_balanceRrgeTbale.size()>0){
				for(int i = 0;i<data_balanceRrgeTbale.size();i++){
					if(data_balanceRrgeTbale.get(i).getBaudCombo().equals("天平未校准")){
						balCode = data_balanceRrgeTbale.get(i).getBalCode();
						break;
					}
				}
				if(balCode.equals("")  ){
					balCode = data_balanceRrgeTbale.get(0).getBalCode();
				}
			}else{
				balCode = null;
			}
			
			AddTblBalCalibration.getInstance().loadData(balCode,"weight");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭
	 * 
	 * @param event
	 */
	@FXML
	void onExitBtn(ActionEvent event) {
		closePort();
		
		closeTimer();
//		animalTable.getSelectionModel().selectedItemProperty().
		
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	

	private static AnatomyProcessPage instance;

	public static AnatomyProcessPage getInstance() {
		if (null == instance) {
			instance = new AnatomyProcessPage();
		}
		return instance;
	}

	public AnatomyProcessPage() {
	}

	//任务id列表
	private static List<String> taskIdList = null;
	//存放任务Id与sessionId的对应关系（在该页面，是一对一的关系）
	private static Map<String,String> taskIdSessionIdMap = null;
	//会话类型
	private static Integer sessionType = null;
	//会话id列表
	static List<String> sessionIdList = null;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//  初始化
		instance = this;
		//1.初始化 当前操作者
		initHandlerLabel();
		//2.初始化动物表
		initAnimalTable();
		//2.1初始化"按解剖时间排序"CheckBox
		initAnatomyTimeCheckBox();
		
		//3.初始化动物解剖标签页
		initAnatomyTab();
		//4.初始化脏器称重标签页
		initWeightTab();
		//5.初始化脏器固定标签页
		initFixedTab();
		//6.初始化 tabPane
		initTabPane();
	}
	
	/**
	 * 初始化"按解剖时间排序"CheckBox
	 */
	private void initAnatomyTimeCheckBox() {
		// 
		anatomyTimeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				// 
				if(null != newValue){
					updateAnimalTable(sessionIdList);
				}
			}
		});
	}

	/**
	 * 初始化tabPane
	 */
	private void initTabPane() {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab tab) {

				Animal newValue = animalTable.getSelectionModel().getSelectedItem();
				if (null != newValue && null != tab) {

					// 动物表格 onChange
					String taskId = newValue.getTaskId();
					String sessionId = taskIdSessionIdMap.get(taskId);
					String animalCode = newValue.getAnimalCode();
					String studyNo = newValue.getStudyNo();
					if (tab.equals(anatomyTab)) {
						// 更新 选择脏器树
						updateVisceraTree(taskId, newValue.getStudyNo(), animalCode, newValue
								.getState().equals("解剖中"));
						// 设置5按钮状态,true可用，false不可用(不包括其他脏器按钮)
						set5BtnState(newValue.getState().equals("解剖中"));
						// 更新解剖结果表格（anatomyResultTalbe）
						updateAnatomyResultTableData(sessionId, animalCode, newValue.getState()
								.equals("解剖中"));

						// 动物编号 解剖者 解剖开始时间 Label
						animalCodeLabel_anatomy.setText(newValue.getAnimalCode());
						anatomyOperatorLabel_anatomy.setText(newValue.getAnatomyOperator());
						anatomyBeginTimeLabel_anatomy.setText(newValue.getAnatomyBeginTime());
					} else if (tab.equals(weightTab)) {

						Boolean falg = false;
						if (newValue.getState().equals("固定后称重中")) {
							falg = "0".equals(newValue.getVisceraFixedWeighFinishFlag());
						} else {
							falg = "0".equals(newValue.getVisceraWeighFinishFlag());
						}

						// 设置称重面板上 按钮状态TODO
//						set1WeightBtnAndStudyNoAnimalCodeLabel(
//								"0".equals(newValue.getAutolyzeFlag())
//										&& "1".equals(newValue.getAnatomyCheckFinishFlag()) && falg,
//								newValue.getStudyNo(), newValue.getAnimalCode());
						set1WeightBtnAndStudyNoAnimalCodeLabel(
								"0".equals(newValue.getAutolyzeFlag())
								 && falg,
								newValue.getStudyNo(), newValue.getAnimalCode());
						// 更新 选择脏器树 ，根据任务id、动物编号(脏器称重)
						updateVisceraTree_weight(taskId, newValue.getStudyNo(), animalCode,
								sessionType == 8);

						// 更新称重结果表
						updateVisceraWeightTable(null);
						// 选择第一行
						visceraTree_weight.getSelectionModel().selectFirst();
						if(noOpenBal){
							updateBalanceRrgeTbale();// 更新天平信息
							//更新 称重报警范围 link
							updateWeightUpperAndLowerLinkState();
						}
						
						// 上限下限
						updateUpperLabelAndLowerLabel();
					} else if (tab.equals(fixTab)) {
						initAbnVisceraTable(); // 初始化异常脏器
						updateAbnVisceraTable(newValue.getStudyNo(), newValue.getAnimalCode());// 更新异常脏器
						// 更新 固定-选择脏器 ListView
						updateData_visceraListView(taskId, sessionId, animalCode);
						// 设置 固定面板上2 按钮状态
						set2FixedBtn("0".equals(newValue.getAutolyzeFlag())
								&& "1".equals(newValue.getAnatomyCheckFinishFlag())
								&& "0".equals(newValue.getVisceraFixedFinishFlag()));

						// 更新 固定结果表（viscerFixedTable）
						updateData_visceraFixedTable(
								sessionId,
								animalCode,
								("0".equals(newValue.getAutolyzeFlag())
										&& "1".equals(newValue.getAnatomyCheckFinishFlag()) && "0"
										.equals(newValue.getVisceraFixedFinishFlag())));
						update_AbnVisceraTable(
								studyNo,
								animalCode,
								("0".equals(newValue.getAutolyzeFlag())
										&& "1".equals(newValue.getAnatomyCheckFinishFlag()) && "0"
										.equals(newValue.getVisceraFixedFinishFlag())));
					}
					// update_AbnVisceraTable(newValue.getStudyNo(),newValue.getAnimalCode(),true);

				} else {

					if (anatomyTab.isSelected()) {
						// 更新 选择脏器树 ，根据任务id
						updateVisceraTree(null, null, null, false);
						set5BtnState(false);
						updateAnatomyResultTableData(null, null, false);

						// 动物编号 解剖者 解剖开始时间 Label
						animalCodeLabel_anatomy.setText("");
						anatomyOperatorLabel_anatomy.setText("");
						anatomyBeginTimeLabel_anatomy.setText("");

					} else if (weightTab.isSelected()) {
						// 设置称重面板上 按钮状态TODO
						set1WeightBtnAndStudyNoAnimalCodeLabel(false, "", "");
						// 更新 选择脏器树 ，根据任务id、动物编号(脏器称重)
						updateVisceraTree_weight(null, null, null, false);

						// 更新称重结果表
						updateVisceraWeightTable(null);
						//清空天平列表
						data_balanceRrgeTbale.clear();
					} else if (fixTab.isSelected()) {
						updateData_visceraListView(null, null, null);
						set2FixedBtn(false);
						// 更新 固定结果表（viscerFixedTable）
						updateData_visceraFixedTable(null, null, false);
						update_AbnVisceraTable(null, null, false);

					}

				}
			}

		});
	}

	/**
	 * 初始化 当前操作者
	 */
	private void initHandlerLabel() {
		handlersLabel.setText(SaveUserInfo.getRealName() != null ? SaveUserInfo.getRealName():"");
	}
	/**
	 * 初始化脏器固定-异常脏器表
	 */
	private void initAbnVisceraTable(){
		Animal animal=animalTable.getSelectionModel().getSelectedItem();
		if(null!=animal){
			TblStudyPlan tblStudyPlan=BaseService.getInstance().getTblStudyPlanService().getByStudyNo(animal.getStudyNo());
			if(null!=tblStudyPlan && tblStudyPlan.getAbnVisceraFixedFlag()==1){
				abnVisceraPane.setVisible(true);
				abnVisceraLabel.setVisible(true);
				abnVisceraList.setVisible(true);
				abnVisceraList.setItems(data_abnVisceraList);
				
				abnVisceraList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
					@Override
					public void changed(ObservableValue<? extends CheckBox> arg0, CheckBox arg1,
							CheckBox newValue) {
						if(null != newValue){
							abnVisceraList.getSelectionModel().clearSelection();
							newValue.setSelected(!newValue.isSelected());
							
						}
						
					}
				});
				abnVisceraTable.setItems(data_abnVisceraTable);
				abnVisceraCol.setCellValueFactory(new PropertyValueFactory<AbnViscera,String>("abnViscera"));
				operateCol_AbnFixed.setCellValueFactory(new PropertyValueFactory<AbnViscera,Boolean>("operate"));
				operateCol_AbnFixed.setCellFactory(new Callback<TableColumn<AbnViscera, Boolean>, TableCell<AbnViscera, Boolean>>() {

		            public TableCell<AbnViscera, Boolean> call(TableColumn<AbnViscera, Boolean> p) {

		            	HyperlinkCell_AbnFixed<AbnViscera, Boolean> cell = new HyperlinkCell_AbnFixed<AbnViscera, Boolean>();
		                return cell;

		            }

		        });
				
			}
			
		}
	}
	/**已固定的异常脏器
	 * @param studyNo
	 * @param animalCode
	 */
	private void update_AbnVisceraTable(String studyNo,String animalCode,boolean flag){
		data_abnVisceraTable.clear();
		List<TblVisceraFixed> list=BaseService.getInstance().getTblVisceraFixedService().getListByStudyNoAnimalCode(studyNo, animalCode);
		if(null!=list && list.size()>0){
			for(TblVisceraFixed t:list){
				String id=t.getId();
				String anatomyCheckId=t.getAnatomyCheckRecordId();
				TblAnatomyCheck tblAnatomyCheck=BaseService.getInstance().getTblAnatomyCheckService().getById(anatomyCheckId);
				String abnViscera="";
				String visceraName="";
				if(null!=tblAnatomyCheck){
					visceraName=tblAnatomyCheck.getVisceraName();
					if(null!=visceraName && !"".equals(visceraName)){
						String subVisceraName = tblAnatomyCheck.getSubVisceraName();
						if(null!=subVisceraName && !"".equals(subVisceraName)){
							visceraName = subVisceraName;
						}
						if(null!=tblAnatomyCheck.getAnatomyPos()){
							abnViscera=visceraName+tblAnatomyCheck.getAnatomyPos()+" "+tblAnatomyCheck.getAnatomyFingding();
						}else{
							abnViscera=visceraName+" "+tblAnatomyCheck.getAnatomyFingding();
						}
					}else{
						if(null!=tblAnatomyCheck.getBodySurfacePos()){
							abnViscera=tblAnatomyCheck.getBodySurfacePos()+" "+tblAnatomyCheck.getAnatomyFingding();
						}else{
							abnViscera=tblAnatomyCheck.getAnatomyFingding();
						}
					}
				}
				data_abnVisceraTable.add(new AbnViscera(id,visceraName,abnViscera, flag));
			}
		}
	}
	/**
	 * 更新脏器固定-异常脏器表
	 */
	private void updateAbnVisceraTable(String studyNo,String animalCode){
		data_abnVisceraList.clear();
		mustFixedAnatomyCheckId.clear();
//		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getListByStudyNoAndAnimalCode(studyNo,animalCode);
		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getListByStudyNoAndAnimalCode(studyNo,animalCode,showOtherAbn);
//		list=new ArrayList<TblAnatomyCheck>();
//		TblAnatomyCheck tblAnatomyCheck=new TblAnatomyCheck();
//		tblAnatomyCheck.setAnimalCode("1101");
//		tblAnatomyCheck.setAnatomyFingding("心脏肿块");
//		list.add(tblAnatomyCheck);
//		abnAnatomyCheckList=list;
		CheckBox checkbox=null;
		if(null!=list && list.size()>0){
			for(Map<String,Object> map:list){
				String visceraName=(String) map.get("visceraName");
				Integer specialFlag = (Integer) map.get("specialFlag");
				if(visceraName == null && null != specialFlag && specialFlag == 1 ){
					mustFixedAnatomyCheckId.add((String)map.get("id"));
				}
				String abnViscera=(String) map.get("anatomyFingding");
//				AbnViscera a=new AbnViscera(visceraName,abnViscera);
				if(null!=visceraName){
					String subVisceraName=(String) map.get("subVisceraName");
					if(null != subVisceraName && !"".equals(subVisceraName)){
						visceraName = subVisceraName;
					}
					String anatomyPos=(String) map.get("anatomyPos");
					if(null!=anatomyPos){
						checkbox = new CheckBox(visceraName+anatomyPos+" "+abnViscera);
					}else{
						checkbox = new CheckBox(visceraName+" "+abnViscera);
					}
					
				}else{
					String bodySurfacePos=(String) map.get("bodySurfacePos");
					if(null!=bodySurfacePos){
						checkbox = new CheckBox(bodySurfacePos+" "+abnViscera);
					}else{
						checkbox = new CheckBox(abnViscera);
					}
				}
//				listAbnViscera.add(a);
				checkbox.setUserData(map);
				data_abnVisceraList.add(checkbox);
			}
		}else{
			abnFixedButton.setDisable(true);
		}
	}
    /**
     * 异常脏器固定登记
     */
    @FXML
    private void onAbnVisceraOkButton(){
    	//选择的脏器名称列表
//		List<String> selectedAbnViscerNameList = new ArrayList<String>();
		List<String> abnAnotomyCheckIdList=new ArrayList<String>();
		if(null!=abnAnatomyCheckList && abnAnatomyCheckList.size()>0){
			for(TblAnatomyCheck tac:abnAnatomyCheckList){
				abnAnotomyCheckIdList.add(tac.getId());
			}
		}
//		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		List<TblVisceraFixed> listFixed=new ArrayList<TblVisceraFixed>();
		//选择的脏器列表(map:visceraType,visceraName,visceraCode,sn)
//		List<Map<String,Object>> selectedMapList = new ArrayList<Map<String,Object>>();
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		String taskId=animal.getTaskId();
		String sessionId=taskIdSessionIdMap.get(taskId);
		String studyNo=animal.getStudyNo();
		String animalCode=animal.getAnimalCode();
		if(data_abnVisceraList.size() > 0){
			Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			for(CheckBox checkBox:data_abnVisceraList){
				if(checkBox.isSelected()){
//					Map<String,Object> map= new HashMap<String,Object>();
//					selectedAbnViscerNameList.add(checkBox.getText());
					@SuppressWarnings("unchecked")
					Map<String,Object> map=(Map<String,Object>) checkBox.getUserData();
					TblVisceraFixed tblVisceraFixed=new TblVisceraFixed();
					String id=BaseService.getInstance().getTblVisceraFixedService().getKey();
					tblVisceraFixed.setId(id);
//					map.put("anatomyCheckId", t.getId());
					
		    		tblVisceraFixed.setSessionId(sessionId);
		    		
		    		tblVisceraFixed.setStudyNo(studyNo);
		    		tblVisceraFixed.setOperateTime(currentDate);
		    		tblVisceraFixed.setOperator(SaveUserInfo.getUserName());
		    		tblVisceraFixed.setAnimalCode(animalCode);
		    		tblVisceraFixed.setAnatomyCheckRecordId((String)map.get("id"));
		    		tblVisceraFixed.setFixedType(1);
		    		map.put("sessionId", sessionId);
		    		listFixed.add(tblVisceraFixed);
//					list.add(map);
				}else{
				
				}
			}
			BaseService.getInstance().getTblVisceraFixedService().saveAbnFixedList(abnAnotomyCheckIdList,listFixed);
			updateAbnVisceraTable(studyNo,animalCode);
			update_AbnVisceraTable(studyNo,animalCode,true);
		}
    }
    
    /**
     * 初始化天平
     */
    private void initBalanceRrgeTbale(){
    	balanceRrgeTbale.setItems( data_balanceRrgeTbale);
    	balanceRrgeTbale.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	balCodeCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("balCode"));
    	balStateCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("balState"));
    	baudComboCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("baudCombo"));
    	balanceRrgeTbale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BalanceRrge>(){
	    	@Override
			public void changed(ObservableValue<? extends BalanceRrge> arg0, BalanceRrge arg1, BalanceRrge newValue) {
				if(null != newValue){
					balanceRrgeTbale.getSelectionModel().clearSelection();
				}
	    	}
    	});
    }
    
    //更新天平数据
    public void updateBalanceRrgeTbale(){
    	closePort();
    	data_balanceRrgeTbale.clear();
    	noOpenBal = false;
    	String houstName = SystemTool.getHostName();//计算机名称
		List<TblBalConnect> list = BaseService.getInstance().getTblBalConnectService().findByHostNameList(houstName,1);
		if(null != list && list.size()>0){
			for(TblBalConnect connect:list){
				String commName = connect.getCommName();
				String balCode = connect.getBalCode();
				//Integer enabledStr = connect.getEnabled();----------------------------------------------------------------------------------------//
				boolean flag = BaseService.getInstance().getTblBalCalibrationIndexService().isExistByBalCode(balCode);
				String balState = "";
				if(!flag){
					balState = "天平未校准";
				}else{
					//查询天平的 设备Id:balCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
					Map<String,Object> balMap = BaseService.getInstance().getTblBalRegService().findBalMapByBalCodeHostName(balCode,houstName);
					if(null == balMap ){
						balState = "连接信息未设置";
					}else{
						balState = openPort(balMap);
					}
				}
				data_balanceRrgeTbale.add(new BalanceRrge(balCode,commName,balState));
			}

		}
    }
	/**
	 * 初始化动物表
	 */
	private void initAnimalTable() {
		animalTable.setItems(data_animalTable);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		stateCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("state"));
		anatomyOperatorCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("anatomyOperator"));
		studyNoCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("studyNo"));
//		animalTable.setOnMousePressed(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent arg0) {
//				animalTable.getSelectionModel().clearSelection();
//			}});
		animalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Animal>(){

			@Override
			public void changed(ObservableValue<? extends Animal> arg0, Animal arg1, Animal newValue) {
				if(null != newValue){
					if(noAnimalSelected){
						set3PaneDisabled(false);
					}
					//  动物表格 onChange
					String taskId = newValue.getTaskId();
					String sessionId = taskIdSessionIdMap.get(taskId);
					String animalCode = newValue.getAnimalCode();
					String studyNo =newValue.getStudyNo();
					if(anatomyTab.isSelected()){
						//更新 选择脏器树 
						updateVisceraTree(taskId,newValue.getStudyNo(),animalCode,newValue.getState().equals("解剖中"));
						//设置5按钮状态,true可用，false不可用(不包括其他脏器按钮)
						set5BtnState(newValue.getState().equals("解剖中"));
						//更新解剖结果表格（anatomyResultTalbe）
						updateAnatomyResultTableData(sessionId,animalCode,newValue.getState().equals("解剖中"));
						
						//动物编号  解剖者  解剖开始时间 Label
						animalCodeLabel_anatomy.setText(newValue.getAnimalCode());
						anatomyOperatorLabel_anatomy.setText(newValue.getAnatomyOperator());
						anatomyBeginTimeLabel_anatomy.setText(newValue.getAnatomyBeginTime());
					}else if(weightTab.isSelected()){
						
						Boolean falg = false;
						if(newValue.getState().equals("固定后称重中")){
							falg = "0".equals(newValue.getVisceraFixedWeighFinishFlag());
						}else{
							falg = "0".equals(newValue.getVisceraWeighFinishFlag());
						}
						
						//设置称重面板上 按钮状态TODO
//						set1WeightBtnAndStudyNoAnimalCodeLabel("0".equals(newValue.getAutolyzeFlag()) 
//								&& "1".equals(newValue.getAnatomyCheckFinishFlag())
//								&& falg ,
//								newValue.getStudyNo(),newValue.getAnimalCode());
						set1WeightBtnAndStudyNoAnimalCodeLabel("0".equals(newValue.getAutolyzeFlag()) 
								&& falg ,
								newValue.getStudyNo(),newValue.getAnimalCode());
						//更新 选择脏器树 ，根据任务id、动物编号(脏器称重)
						updateVisceraTree_weight(taskId,newValue.getStudyNo(),animalCode,sessionType == 8);
						
						//更新称重结果表
						updateVisceraWeightTable(null);
						//选择第一行
						visceraTree_weight.getSelectionModel().selectFirst();
						if(noOpenBal){
							updateBalanceRrgeTbale();//更新天平信息
							//更新 称重报警范围 link
							updateWeightUpperAndLowerLinkState();
						}
						//上限下限
						updateUpperLabelAndLowerLabel();
					}else if(fixTab.isSelected()){
						initAbnVisceraTable(); //初始化异常脏器
						updateAbnVisceraTable(newValue.getStudyNo(),newValue.getAnimalCode());//更新异常脏器
						//更新 固定-选择脏器 ListView
						updateData_visceraListView(taskId,sessionId,animalCode);
						//设置 固定面板上2 按钮状态
						set2FixedBtn("0".equals(newValue.getAutolyzeFlag()) 
								&& "1".equals(newValue.getAnatomyCheckFinishFlag())
								&&  "0".equals(newValue.getVisceraFixedFinishFlag()));
						
						//更新 固定结果表（viscerFixedTable）
						updateData_visceraFixedTable(sessionId,animalCode,
								("0".equals(newValue.getAutolyzeFlag()) 
										&& "1".equals(newValue.getAnatomyCheckFinishFlag())
										&& "0".equals(newValue.getVisceraFixedFinishFlag()) ) );
						update_AbnVisceraTable(studyNo,animalCode,
								("0".equals(newValue.getAutolyzeFlag()) 
										&& "1".equals(newValue.getAnatomyCheckFinishFlag())
										&& "0".equals(newValue.getVisceraFixedFinishFlag()) ) );
					}
//					update_AbnVisceraTable(newValue.getStudyNo(),newValue.getAnimalCode(),true);
					noAnimalSelected = false;
				}else{
					
					if(anatomyTab.isSelected()){
						//更新 选择脏器树 ，根据任务id
						updateVisceraTree(null,null,null,false);
						set5BtnState(false);
						updateAnatomyResultTableData(null,null,false);
						
						//动物编号  解剖者  解剖开始时间 Label
						animalCodeLabel_anatomy.setText("");
						anatomyOperatorLabel_anatomy.setText("");
						anatomyBeginTimeLabel_anatomy.setText("");
						
					}else if(weightTab.isSelected()){
						//设置称重面板上 按钮状态TODO
						set1WeightBtnAndStudyNoAnimalCodeLabel(false,"","");
						//更新 选择脏器树 ，根据任务id、动物编号(脏器称重)
						updateVisceraTree_weight(null,null,null,false);
						
						//更新称重结果表
						updateVisceraWeightTable(null);
						
					}else if(fixTab.isSelected()){
						updateData_visceraListView(null,null,null);
						set2FixedBtn(false);
						//更新 固定结果表（viscerFixedTable）
						updateData_visceraFixedTable(null,null,
								false);
						update_AbnVisceraTable(null,null,false);
						
					}
				}
				
				//右键
				if(sessionType == 1 || sessionType == 3 || sessionType == 5 || sessionType == 7){
					if(null != newValue){
						if("0".equals(newValue.getAutolyzeFlag())&& "0".equals(newValue.getAnatomyCheckFinishFlag())){
								contextMenu.getItems().clear();
								contextMenu.getItems().add(menuItem_delete);
						}else{
							contextMenu.getItems().clear();
						}
					}else{
						contextMenu.getItems().clear();
//						contextMenu.getItems().add(menuItem_delete);
					}
				}
				
			}
			
		});
		animalCodeCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
	    	 
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
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER;");
	    		 return cell;
	    	 }
	     });
		stateCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
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
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
		anatomyOperatorCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
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
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
		});
		
		
		// 右击菜单项（标记不解剖）
		menuItem_delete = new MenuItem("删除");
				
		contextMenu.getItems().add(menuItem_delete);
		menuItem_delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Animal selectItem = animalTable.getSelectionModel().getSelectedItem();
				if (null != selectItem) {
					if(data_anatomyResultTable.size() > 0){
						showErrorMessage("当前动物已有解剖记录，不可以删除！");
					}else{
						if(Messager.showSimpleConfirm("确认", "确定删除动物 "+selectItem.getAnimalCode()+" ？")){
							//签字窗口
							SignMeFrame signMeFrame = new SignMeFrame("");
							Stage stage = new Stage();
							stage.initModality(Modality.APPLICATION_MODAL);
							stage.setTitle("");
							try {
								signMeFrame.start(stage);
							} catch (Exception e) {
								e.printStackTrace();
							}
							//签字通过
							if("OK".equals(SignMeFrame.getType())){
								Json json = BaseService.getInstance().getTblAnatomyAnimalService()
										.deleteAimal(selectItem.getStudyNo(),selectItem.getAnimalCode(),SaveUserInfo.getRealName());
								if(json.isSuccess()){
									updateAnimalTable(sessionIdList);
								}else{
									showErrorMessage(json.getMsg());
								}
							}
						}
					}
				}
			}

		});
		// 右击菜单(在表格中实现)
		animalTable.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				@SuppressWarnings("unchecked")
				TableView<Animal> t = (TableView<Animal>) e.getSource();
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.show(t, e.getScreenX(), e.getScreenY());
				} else {
					contextMenu.hide();
				}
			}
		});
	}

	/**
	 * 初始化动物解剖标签页
	 */
	private void initAnatomyTab() {
		//初始化选择脏器树
		initTaskTree();
		initBodySufacePosListView();
		//初始化通用/特殊 所见 RadioButton
		inittgRadioButton();
		//初始化 11个  ListView
		init11ListView();
		//10个ListView填充数据(2,4,5,6,7,8,9,10,11,12)
		initData10ListView();
		//初始化 字典排序ComboBox
		initDictSortMethodComboBox();
		//初始化 解剖信息表（anatomyResultTable）
		initAnatomyResultTalbe();
		//初始化快速查找ComboBox
		initSerachComboBox();
		//初始化快速查找TextField
		initSearchTextField();
		//初始化快速查找ListView 
		initSearchListView();
//		bodySufacePosListView.setStyle("-fx-border-color:blue;");
//		bodySufacePosListView.setStyle("");
	}

	
	/**
	 * 初始化快速查找ListView 
	 */
	private void initSearchListView() {
		searchListView.setItems(data_searchListView);
		searchListView.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				String newValue = searchListView.getSelectionModel().getSelectedItem();
				if(null != newValue){
					//快速查找输入框清空
					searchTextField.clear();
					searchComboBox.requestFocus();
					selectandScrollToValue(newValue);
				}
			}
		});
//		searchListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				String newValue = searchListView.getSelectionModel().getSelectedItem();
//				System.out.println("searchListView 点击事件");
//				if(null != newValue){
//					//快速查找输入框清空
//					searchTextField.clear();
//					searchTextField.requestFocus();
//					selectandScrollToValue(newValue);
//				}
//			}
//		});
		
		searchListView.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if("Enter".equals(event.getCode().getName())){
					String newValue = searchListView.getSelectionModel().getSelectedItem();
					if(null != newValue){
						//快速查找输入框清空
						searchTextField.clear();
						searchComboBox.requestFocus();
						selectandScrollToValue(newValue);
					}
				}
			}

		});
		
		searchListView.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null == newValue || !newValue){
					searchListView.setVisible(false);
				}
			}
		});
//		searchListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
//
//			@Override
//			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
//				if(null != newValue){
//					selectandScrollToValue(newValue);
//				}
//				
//			}
//
//		});
	}
	
	/**快速查找，选择指定项，并移到指定行
	 * @param newValue
	 */
	private void selectandScrollToValue(String newValue) {
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		if("解剖学所见部位".equals(selectItem)){
			anatomyPosListView.getSelectionModel().select(newValue);
			int index=anatomyPosListView.getSelectionModel().getSelectedIndex();
			anatomyPosListView.getFocusModel().focus(index);
			anatomyPosListView.scrollTo(getIndex(data_anatomyPosListView,newValue));
		}else if("解剖通用所见".equals(selectItem)){
			anatomyFindingListView.getSelectionModel().select(newValue);
//			anatomyFindingListView.getFocusModel().
			int index=anatomyFindingListView.getSelectionModel().getSelectedIndex();
			anatomyFindingListView.getFocusModel().focus(index);
			anatomyFindingListView.scrollTo(getIndex(data_anatomyFindingListView_tongyong,newValue));
		}else if("解剖特殊所见".equals(selectItem)){
			anatomyFindingListView.getSelectionModel().select(newValue);
			showMessage(getIndex(data_anatomyFindingListView_tongyong,newValue)+"");
			anatomyFindingListView.scrollTo(getIndex(data_anatomyFindingListView_tesu,newValue));
		}else if("体表部位".equals(selectItem)){
			bodySufacePosListView.getSelectionModel().select(newValue);
			bodySufacePosListView.scrollTo(getIndex(data_bodySufacePosListView,newValue));
		}else if("位置".equals(selectItem)){
			posListView.getSelectionModel().select(newValue);
			posListView.scrollTo(getIndex(data_posListView,newValue));
		}else if("形状".equals(selectItem)){
			shapeListView.getSelectionModel().select(newValue);
			shapeListView.scrollTo(getIndex(data_shapeListView,newValue));
		}else if("颜色".equals(selectItem)){
			colorListView.getSelectionModel().select(newValue);
			colorListView.scrollTo(getIndex(data_colorListView,newValue));
		}else if("硬度".equals(selectItem)){
			textureListView.getSelectionModel().select(newValue);
			textureListView.scrollTo(getIndex(data_textureListView,newValue));
		}else if("数量".equals(selectItem)){
			numberListView.getSelectionModel().select(newValue);
			numberListView.scrollTo(getIndex(data_numberListView,newValue));
		}else if("范围".equals(selectItem)){
			rangeListView.getSelectionModel().select(newValue);
			rangeListView.scrollTo(getIndex(data_rangeListView,newValue));
		}else if("病变程度".equals(selectItem)){
			lesionDegreeListView.getSelectionModel().select(newValue);
			lesionDegreeListView.scrollTo(getIndex(data_lesionDegreeListView,newValue));
		}else if("大小".equals(selectItem)){
			sizeListView.getSelectionModel().select(newValue);
			sizeListView.scrollTo(getIndex(data_sizeListView,newValue));
		}
	}
	
	/**获取索引
	 * @param data_ListView
	 * @param newValue
	 * @return
	 */
	private int getIndex(ObservableList<String> data_ListView, String newValue) {
		int i = 0;
		for(String str : data_ListView){
			if(str.equals(newValue)){
				return i;
			}
			i++;
		}
		return 0;
	}

	/**
	 * 显示 快速查找ListView 
	 */
	private void showSearchListView() {
		data_searchListView.clear();
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		String py = searchTextField.getText();
		if("解剖学所见部位".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_anatomyPos,py);
		}else if("解剖通用所见".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_tongyong,py);
		}else if("解剖特殊所见".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_teshu,py);
		}else if("体表部位".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_bodySuface,py);
		}else if("位置".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_pos,py);
		}else if("形状".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_shape,py);
		}else if("颜色".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_color,py);
		}else if("硬度".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_texture,py);
		}else if("数量".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_number,py);
		}else if("范围".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_range,py);
		}else if("病变程度".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_lesionDegree,py);
		}else if("大小".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_size,py);
		}
		int height = data_searchListView.size() * 25;	
		if(height > 175){
			height = 175;
		}
		if(height == 0){
			searchListView.setVisible(false);
		}else{
			height = height+1;
			searchListView.setPrefHeight(height);
			searchListView.setVisible(true);
		}
	}
	/**更新searchListView的值
	 * @param map
	 * @param py
	 */
	private void updateData_searchListView(Map<String,String> map,String py){
		if(null != py && !"".equals(py)){
			List<String> pyList = new ArrayList<String>(map.keySet());
			if(null != pyList){
				py = py.toLowerCase();
				for(String str:pyList){
					if(str.toLowerCase().contains(py)){
						data_searchListView.add(map.get(str));
					}
				}
			}
		}
	}
	/**
	 * 初始化快速查找TextField
	 */
	private void initSearchTextField() {
		searchTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null == newValue || !newValue){
					if(!searchListView.isFocused()){
						searchListView.setVisible(false);
						searchTextField.clear();
					}
				}
				
			}});
	}

	/**
	 * 初始化 解剖信息表（anatomyResultTable）
	 */
	private void initAnatomyResultTalbe() {
		// 
		anatomyResultTable.setItems(data_anatomyResultTable);
		anatomyResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//清空选择
		anatomyResultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyCheck>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyCheck> arg0, AnatomyCheck arg1,
					AnatomyCheck arg2) {
				anatomyResultTable.getSelectionModel().clearSelection();
			}
		});
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("visceraName"));
		findingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("finding"));
		operateCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<AnatomyCheck, Boolean>, TableCell<AnatomyCheck, Boolean>>() {

            public TableCell<AnatomyCheck, Boolean> call(TableColumn<AnatomyCheck, Boolean> p) {

            	HyperlinkCell<AnatomyCheck, Boolean> cell = new HyperlinkCell<AnatomyCheck, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * 初始化快速查找ComboBox
	 */
	private void initSerachComboBox(){
		searchComboBox.getSelectionModel().select(0);
		anatomyPosListView.setStyle("-fx-border-color:blue;");
		searchComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null != newValue){
					searchTextField.setPromptText("快速检索文本(拼音首字母)");
					if("解剖通用所见".equals(newValue)){
						if(!tongyongRadioButton.isSelected()){
							tongyongRadioButton.setSelected(true);
						}
					}else if("解剖特殊所见".equals(newValue)){
						if(!tesuRadioButton.isSelected()){
							tesuRadioButton.setSelected(true);
						}
					}
					setStyle(newValue);
				}
				if(null != oldValue){
					clearStyle(oldValue);
				}
			}});
	}
	
	/**设置ListView 的边框颜色
	 * @param listName
	 */
	private void setStyle(String listName){
		if("解剖学所见部位".equals(listName)){
			anatomyPosListView.setStyle("-fx-border-color:blue;");
		}else if("解剖通用所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color:blue;");
		}else if("解剖特殊所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color:blue;");
		}else if("体表部位".equals(listName)){
			bodySufacePosListView.setStyle("-fx-border-color:blue;");
		}else if("位置".equals(listName)){
			posListView.setStyle("-fx-border-color:blue;");
		}else if("形状".equals(listName)){
			shapeListView.setStyle("-fx-border-color:blue;");
		}else if("颜色".equals(listName)){
			colorListView.setStyle("-fx-border-color:blue;");
		}else if("硬度".equals(listName)){
			textureListView.setStyle("-fx-border-color:blue;");
		}else if("数量".equals(listName)){
			numberListView.setStyle("-fx-border-color:blue;");
		}else if("范围".equals(listName)){
			rangeListView.setStyle("-fx-border-color:blue;");
		}else if("病变程度".equals(listName)){
			lesionDegreeListView.setStyle("-fx-border-color:blue;");
		}else if("大小".equals(listName)){
			sizeListView.setStyle("-fx-border-color:blue;");
		}
	}
	/**清除ListView 的边框颜色
	 * @param listName
	 */
	private void clearStyle(String listName){
		if("解剖学所见部位".equals(listName)){
			anatomyPosListView.setStyle("-fx-border-color: #919191;");
		}else if("解剖通用所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color: #919191;");
		}else if("解剖特殊所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color: #919191;");
		}else if("体表部位".equals(listName)){
			bodySufacePosListView.setStyle("-fx-border-color: #919191;");
		}else if("位置".equals(listName)){
			posListView.setStyle("-fx-border-color: #919191;");
		}else if("形状".equals(listName)){
			shapeListView.setStyle("-fx-border-color: #919191;");
		}else if("颜色".equals(listName)){
			colorListView.setStyle("-fx-border-color: #919191;");
		}else if("硬度".equals(listName)){
			textureListView.setStyle("-fx-border-color: #919191;");
		}else if("数量".equals(listName)){
			numberListView.setStyle("-fx-border-color: #919191;");
		}else if("范围".equals(listName)){
			rangeListView.setStyle("-fx-border-color: #919191;");
		}else if("病变程度".equals(listName)){
			lesionDegreeListView.setStyle("-fx-border-color: #919191;");
		}else if("大小".equals(listName)){
			sizeListView.setStyle("-fx-border-color: #919191;");
		}
	}
	
	/**
	 * 初始化 字典排序ComboBox
	 */
	private void initDictSortMethodComboBox() {
		//  字典排序
		dictSortMethodComboBox.getSelectionModel().select(0);
		sortMethod = 1;
		dictSortMethodComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					sortMethod = newValue.intValue()+1;
					//解剖学所见部位(刷新ListView)
					 onanatomyPosHyperlink(null);
					//解剖所见(刷新ListView)
						onanatomyFindingHyperlink(null);
						//体表部位(刷新ListView)
						onbodySufaceHyperlink(null);
						//位置(刷新ListView)
						onposHyperlink(null);
						//范围(刷新ListView)
						onrangeHyperlink(null);
						//数量(刷新ListView)
						onnumberHyperlink(null);
						//形状(刷新ListView)
						onshapeHyperlink(null);
						//颜色(刷新ListView)
						oncolorHyperlink(null);
						//硬度(刷新ListView)
						 ontextureHyperlink(null);
						//大小(刷新ListView)
						 onSizeHyperlink(null);
						//病变程度(刷新ListView)
						 onlesionHyperlink(null);
				}
				
			}
		});
	}

	/**
	 * 10个ListView填充数据(2,4,5,6,7,8,9,10,11,12)
	 */
	private void initData10ListView() {
		//2:解剖通用所见 
		data_anatomyFindingListView_tongyong.clear();
		pyDescCNMap_tongyong.clear();
		anatomyFinding_tongyongDictPathCommonMap.clear();
		List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(2,sortMethod);
		if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
			for(DictPathCommon obj:dictPathCommon2List){
				data_anatomyFindingListView_tongyong.add(obj.getDescCn());
				anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
				pyDescCNMap_tongyong.put(obj.getPy(), obj.getDescCn());
			}
		}
		//4:体表部位
		data_bodySufacePosListView.clear();
		pyDescCNMap_bodySuface.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,sortMethod);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
				pyDescCNMap_bodySuface.put(obj.getPy(), obj.getDescCn());
			}
		}
		//5:位置
		data_posListView.clear();
		pyDescCNMap_pos.clear();
		List<DictPathCommon> dictPathCommon5List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(5,sortMethod);
		if(null != dictPathCommon5List && dictPathCommon5List.size() > 0){
			for(DictPathCommon obj:dictPathCommon5List){
				data_posListView.add(obj.getDescCn());
				pyDescCNMap_pos.put(obj.getPy(), obj.getDescCn());
			}
		}
		//6:分布、范围
		data_rangeListView.clear();
		pyDescCNMap_range.clear();
		List<DictPathCommon> dictPathCommon6List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(6,sortMethod);
		if(null != dictPathCommon6List && dictPathCommon6List.size() > 0){
			for(DictPathCommon obj:dictPathCommon6List){
				data_rangeListView.add(obj.getDescCn());
				pyDescCNMap_range.put(obj.getPy(), obj.getDescCn());
			}
		}
		//7:数量
		data_numberListView.clear();
		pyDescCNMap_number.clear();
		List<DictPathCommon> dictPathCommon7List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(7,sortMethod);
		if(null != dictPathCommon7List && dictPathCommon7List.size() > 0){
			for(DictPathCommon obj:dictPathCommon7List){
				data_numberListView.add(obj.getDescCn());
				pyDescCNMap_number.put(obj.getPy(), obj.getDescCn());
			}
		}
		//8:形状
		data_shapeListView.clear();
		pyDescCNMap_shape.clear();
		List<DictPathCommon> dictPathCommon8List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(8,sortMethod);
		if(null != dictPathCommon8List && dictPathCommon8List.size() > 0){
			for(DictPathCommon obj:dictPathCommon8List){
				data_shapeListView.add(obj.getDescCn());
				pyDescCNMap_shape.put(obj.getPy(), obj.getDescCn());
			}
		}
		//9:颜色
		data_colorListView.clear();
		pyDescCNMap_color.clear();
		List<DictPathCommon> dictPathCommon9List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(9,sortMethod);
		if(null != dictPathCommon9List && dictPathCommon9List.size() > 0){
			for(DictPathCommon obj:dictPathCommon9List){
				data_colorListView.add(obj.getDescCn());
				pyDescCNMap_color.put(obj.getPy(), obj.getDescCn());
			}
		}
		//10:硬度
		data_textureListView.clear();
		pyDescCNMap_texture.clear();
		List<DictPathCommon> dictPathCommon10List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(10,sortMethod);
		if(null != dictPathCommon10List && dictPathCommon10List.size() > 0){
			for(DictPathCommon obj:dictPathCommon10List){
				data_textureListView.add(obj.getDescCn());
				pyDescCNMap_texture.put(obj.getPy(), obj.getDescCn());
			}
		}
		//11:大小
		data_sizeListView.clear();
		pyDescCNMap_size.clear();
		List<DictPathCommon> dictPathCommon11List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(11,sortMethod);
		if(null != dictPathCommon11List && dictPathCommon11List.size() > 0){
			for(DictPathCommon obj:dictPathCommon11List){
				data_sizeListView.add(obj.getDescCn());
				pyDescCNMap_size.put(obj.getPy(), obj.getDescCn());
			}
		}
		//12:病变程度
		data_lesionDegreeListView.clear();
		pyDescCNMap_lesionDegree.clear();
		List<DictPathCommon> dictPathCommon12List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(12,sortMethod);
		if(null != dictPathCommon12List && dictPathCommon12List.size() > 0){
			for(DictPathCommon obj:dictPathCommon12List){
				data_lesionDegreeListView.add(obj.getDescCn());
				pyDescCNMap_lesionDegree.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}

	/**
	 * 初始化 11个  ListView
	 */
	private void init11ListView() {
		//解剖学所见部位
		anatomyPosListView.setItems(data_anatomyPosListView);
		anatomyPosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				anatomyPosListView.setUserData(true);
				if(null != newValue){
					anatomyPosListView.setId(newValue);
				}else{
					anatomyPosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		anatomyPosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("解剖学所见部位");
			}
		});
		
		//解剖所见(通用)
		anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		anatomyFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
//				if(tongyongRadioButton.isSelected()){
//					//快速查找选中指定项
//					searchComboBox.getSelectionModel().select("解剖通用所见");
//				}else{
//					//快速查找选中指定项
//					searchComboBox.getSelectionModel().select("解剖特殊所见");
//				}
			}
		});
		anatomyFindingListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(tongyongRadioButton.isSelected()){
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖通用所见");
				}else{
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖特殊所见");
				}
			}});
		
		//体表部位
		bodySufacePosListView.setItems(data_bodySufacePosListView);
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				bodySufacePosListView.setUserData(true);
				if(null != newValue){
					bodySufacePosListView.setId(newValue);
				}else{
					bodySufacePosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		bodySufacePosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("体表部位");
			}
		});
		//位置
		posListView.setItems(data_posListView);
		posListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				posListView.setUserData(true);
				if(null != newValue){
					posListView.setId(newValue);
				}else{
					posListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		posListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("位置");
			}
		});
		//形状
		shapeListView.setItems(data_shapeListView);
		shapeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				shapeListView.setUserData(true);
				if(null != newValue){
					shapeListView.setId(newValue);
				}else{
					shapeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		shapeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("形状");
			}
		});
		//颜色
		colorListView.setItems(data_colorListView);
		colorListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				colorListView.setUserData(true);
				if(null != newValue){
					colorListView.setId(newValue);
				}else{
					colorListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		colorListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("颜色");
			}
		});
		//硬度
		textureListView.setItems(data_textureListView);
		textureListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				textureListView.setUserData(true);
				if(null != newValue){
					textureListView.setId(newValue);
				}else{
					textureListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		textureListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("硬度");
			}
		});
		
		//数量
		numberListView.setItems(data_numberListView);
		numberListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				numberListView.setUserData(true);
				if(null != newValue){
					numberListView.setId(newValue);
				}else{
					numberListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		numberListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("数量");
			}
		});
		//范围
		rangeListView.setItems(data_rangeListView);
		rangeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				rangeListView.setUserData(true);
				if(null != newValue){
					rangeListView.setId(newValue);
				}else{
					rangeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		rangeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("范围");
			}
		});
		//病变程度
		lesionDegreeListView.setItems(data_lesionDegreeListView);
		lesionDegreeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				lesionDegreeListView.setUserData(true);
				if(null != newValue){
					lesionDegreeListView.setId(newValue);
				}else{
					lesionDegreeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		lesionDegreeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("病变程度");
			}
		});
		//大小
		sizeListView.setItems(data_sizeListView);
		sizeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				sizeListView.setUserData(true);
				if(null != newValue){
					sizeListView.setId(newValue);
					
					sizeTextField.setText(newValue);
					
				}else{
					sizeListView.setId("");
				}
				
			}
		});
		sizeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
						
						sizeTextField.setText("");
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("大小");
			}
		});
		
		sizeTextField.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue && !"".equals(newValue)){
					sizeListView.getSelectionModel().clearSelection();
					if(data_sizeListView.size() > 0){
						for(String str :data_sizeListView){
							if(newValue.equals(str)){
								sizeListView.getSelectionModel().select(str);
							}
						}
					}
				}else{
					sizeListView.getSelectionModel().clearSelection();
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
				
			}
		});
	}

	/**
	 * 初始化选择脏器树
	 */
	private void initTaskTree() {
		rootNode.setValue("选择脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				// 选择脏器树      change事件
				visceraTree.setUserData(true);
				if(null != newValue){
					String visceraName = newValue.getValue();
					bodySufacePosListView.getSelectionModel().clearSelection();
					Map<String,Object> map = visceraName2MapMap.get(visceraName);
					if(null != map){
						String visceraCode = (String) map.get("visceraCode");
						//根据脏器编号更新 解剖学所见部位
						updateAnatomyPosListViewData(visceraCode);
						//根据脏器编号更新 解剖学所见(通用，特殊)
						updateAnatomyFindingListViewData(visceraCode);
					}
					visceraTree.setId(visceraName);
					
					
				}else{
					//清空ListView
					updateAnatomyPosListViewData(null);
					updateAnatomyFindingListViewData(null);
					
					visceraTree.setId("");
					updateAnatomyFindingLabelText();
				}
				
			}
			
		});
		
		visceraTree.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				Boolean isChange = (Boolean) visceraTree.getUserData();
				if(null != isChange && !isChange){
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.getValue().equals(visceraTree.getId())){
						visceraTree.getSelectionModel().clearSelection();
					}
				}
				visceraTree.setUserData(false);
				
				// 
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
	}
	public void initBodySufacePosListView(){
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					visceraTree.getSelectionModel().clearSelection();
					tongyongRadioButton.setSelected(true);
				}
			}
		});

		
	}
	/**
	 * 初始化通用/特殊 所见 RadioButton
	 */
	private void inittgRadioButton() {
		
		tongyongRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView
					anatomyFindingListView.getSelectionModel().clearSelection();
					anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
					
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖通用所见");
				}
			}});
		
		tesuRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView  
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem){
						String visceraName = selectItem.getValue();
						Map<String,Object> map = visceraName2MapMap.get(visceraName);
						if(null != map){
							String visceraCode = (String) map.get("visceraCode");
							//根据脏器编号更新 解剖学所见(通用，特殊)
							updateAnatomyFindingListViewData(visceraCode);
						}
					}else{
						updateAnatomyFindingListViewData(null);
					}
					
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖特殊所见");
				}
			}});
	}
	
	/**
	 * 初始化脏器称重标签页
	 */
	private void initWeightTab() {
		//1.初始化 称重标签页，选择脏器树
		initVisceraTree_weight();
		//2.初始化天平编号combobox(包括填充数据)
//		initBalCodeComboBox();
		//3.初始化称重记录表格
		initVisceraWeightTable();
		//4.初始化 otherAnimalWeightTable
		initOtherAnimalWeightTable();
		//5初始化fOtherAnimalWeightTable
		initfOtherAnimalWeightTable();
		//6初始化天平
		initBalanceRrgeTbale();
		
		
		
		
	}

	/**
	 * 更新  称重报警范围 link 
	 */
	private void updateWeightUpperAndLowerLinkState(){
//		String userId = SaveUserInfo.getUser().getId();
//		boolean ishave  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_称重报警设置");
//		if(ishave){
//			saveWeightUpperAndLowerLink.setDisable(false);
//		}else{
//			saveWeightUpperAndLowerLink.setDisable(true);
//		}
	}
	
	/**
	 * 更新上限下限
	 */
	public void updateUpperLabelAndLowerLabel(){
		// 1：第一次称重时，2：且未设报名范围？
		
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		String  taskId = null;
		if(null != animal){
			taskId = animal.getTaskId();
		}
		if(null != taskId){
			TblAnatomyTask anatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
			
			String  weightLower = anatomyTask.getWeightLower();//下限
			String weightUpper = anatomyTask.getWeightUpper();//上限
			
			if(null  != weightLower && !weightLower.equals("") || null  != weightUpper && !weightUpper.equals("")){
				isWeight = true;
				taskIdScopeMap.put(taskId, isWeight);
			}else{
				isWeight = false;
				Boolean isWeight2 = taskIdScopeMap.get(taskId);
				if(null != isWeight2 && isWeight2){
					isWeight = true;
				}else{
					isWeight = BaseService.getInstance().getTblAnatomyTaskService().isWeightByTaskId(taskId);;
					taskIdScopeMap.put(taskId, isWeight);
				}
			}
			
			if(null  == weightLower || weightLower.equals("")){
				weightLowerLabel.setText("暂无报警下限");
			}else{
				weightLowerLabel.setText(weightLower+"%");
			}
			if(null  == weightUpper || weightUpper.equals("")){
				weightUpperLabel.setText("暂无报警上限");
			}else{
				weightUpperLabel.setText(weightUpper+"%");
			}
		}else{
			weightLowerLabel.setText("");
			weightUpperLabel.setText("");
		}
		
	}
	/**
	 * 症状观察 
	 */
	@FXML
	private void symptomObservationBtnAction(){
		if(null == animalTable.getSelectionModel().getSelectedItem()){
			showErrorMessage("请先选择相应动物！");
			return;
		}
		String  studyNo = animalTable.getSelectionModel().getSelectedItem().getStudyNo();
		String  animalCode = animalTable.getSelectionModel().getSelectedItem().getAnimalCode();
		try {
//			SymptomObservation.getInstance().start(getStage());
			 Stage stage = Main.stageMap.get("SymptomObservation");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					SymptomObservation.getInstance().start(stage);
					Main.stageMap.put("SymptomObservation",stage);
				}else{
					stage.show();
				}
			SymptomObservation.getInstance().loadData(studyNo, animalCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
    /**
     * 设置范围参数
     */
    @FXML
    private void saveWeightUpperLabelAndLower(){
//    	User signUser = Sign.openSignWithPrivilegeName("身份验证", "", "毒性病理_称重报警设置");
//    	if(null != signUser){
//    	}
    	try {
    		Stage stage = Main.stageMap.get("UpdateWeightUpAndLo");
    		if(null == stage){
    			stage =new Stage();
    			stage.initOwner(Main.mainWidow);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			UpdateWeightUpAndLo.getInstance().start(stage);
    			Main.stageMap.put("UpdateWeightUpAndLo",stage);
    		}else{
    			stage.show();
    		}
    		UpdateWeightUpAndLo.getInstance().loadData(taskIdList);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
//    	
//    	Animal animal = animalTable.getSelectionModel().getSelectedItem();
//    	String  taskId = null;
//    	if(null != animal){
//    		taskId = animal.getTaskId();
//    	}
//    	
    }

	/**
	 * 初始化 otherAnimalWeightTable
	 */
	private void initOtherAnimalWeightTable() {
		// otherAnimalWeightTable
		otherAnimalWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		otherAnimalWeightTable.setItems(data_otherAnimalWeightTable);
		animalCodeCol_otherAnimalWeightTable.setCellValueFactory(new PropertyValueFactory<OtherAnimalWeight,String>("animalCode"));
		weightCol_otherAnimalWeightTable.setCellValueFactory(new PropertyValueFactory<OtherAnimalWeight,String>("weight"));
	}

	/**
	 * 初始化fOtherAnimalWeightTable
	 */
	private void initfOtherAnimalWeightTable(){
		fOtherAnimalWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		fOtherAnimalWeightTable.setItems(data_ftherAnimalWeightTable);
		animalCodeCol_fOtherAnimalWeightTable.setCellValueFactory(new PropertyValueFactory<OtherAnimalWeight,String>("animalCode"));
		weightCol_fOtherAnimalWeightTable.setCellValueFactory(new PropertyValueFactory<OtherAnimalWeight,String>("weight"));
	}
	
	/**
	 * 初始化称重记录表格
	 */
	private void initVisceraWeightTable() {
		visceraWeightTable.setItems(data_visceraWeightTable);
		visceraWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		viscerNameCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("visceraName"));
		weightCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("weight"));
		operateCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,Boolean>("operate"));
		operateCol_visceraWeightTable.setCellFactory(new Callback<TableColumn<VisceraWeight, Boolean>, TableCell<VisceraWeight, Boolean>>() {

            public TableCell<VisceraWeight, Boolean> call(TableColumn<VisceraWeight, Boolean> p) {
            	HyperlinkCell_weight<VisceraWeight, Boolean> cell = new HyperlinkCell_weight<VisceraWeight, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * .初始化 称重标签页，选择脏器树
	 */
	private void initVisceraTree_weight() {
		
		rootNode_weight.setValue(new Label("选择脏器"));
		rootNode_weight.setExpanded(true);
		visceraTree_weight.setRoot(rootNode_weight);
		visceraTree_weight.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Label>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<Label>> arg0,
					TreeItem<Label> arg1, TreeItem<Label> newValue) {
				// 选择脏器树      change事件
				if(null != newValue){
					//待称重脏器名称
					nextViscerNameLabel.setText(newValue.getValue().getText());
					//更新 同组动物该脏器的重量及表格数据
					@SuppressWarnings("unchecked")
					Map<String,Object> map = (Map<String, Object>)newValue.getValue().getUserData();
					String visceraCode = (String) map.get("visceraCode");
					String subVisceraCode = (String) map.get("subVisceraCode");
					if(newValue.isLeaf()){
						updateOtherAnimalWeightTable(studyNoLabel.getText(),animalCodeLabel.getText(),visceraCode,subVisceraCode);
					}else{
						updateOtherAnimalWeightTable(studyNoLabel.getText(),animalCodeLabel.getText(),visceraCode,null);
					}
					
					boolean falg = false;
					for(int i = 0; i < data_visceraWeightTable.size() ; i++){
						if(	data_visceraWeightTable.get(i).getVisceraName().equals(newValue.getValue().getText())){
							falg = true;
							break;
						}
						if(null != newValue.getChildren()){
							for(TreeItem<Label> obj:newValue.getChildren()){
								 @SuppressWarnings("unchecked")
								Map<String,Object> submap = (Map<String, Object>)obj.getValue().getUserData();
								 String subVisceraNames = (String) submap.get("subVisceraName");
								 for(int j = 0; j < data_visceraWeightTable.size() ; j++){
									 if(subVisceraNames.equals(data_visceraWeightTable.get(j).getVisceraName())){
										 falg = true;
										 break;
									 }
    							 }
							}
						}
					}
					String state = animalTable.getSelectionModel().getSelectedItem().getState();
					if(!falg  && !state.equals("自溶")){
						visceraMissingButton_weight.setDisable(false);
					}else{
						visceraMissingButton_weight.setDisable(true);
					}
					//非叶子节点
					if(!newValue.isLeaf()){
						visceraMissingButton_weight.setDisable(true);
					}
				}else{
					//待称重脏器名称
					nextViscerNameLabel.setText("");
					//更新 同组动物该脏器的重量及表格数据
					updateOtherAnimalWeightTable(null,null,null,null);
					visceraMissingButton_weight.setDisable(true);
				}
				
			}
			
		});
		
	}
	
	/**
	 * 打开串口 
	 * @param chipReaderMap设备Id:balCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 */
	private String openPort(Map<String,Object> chipReaderMap) {
		if(null == chipReaderMap ){
//			msgLabel.setText("天平的连接信息未设置。");
			return "天平的连接信息未设置";
		}
		String balCode = (String) chipReaderMap.get("balCode");
		String comPort = (String) chipReaderMap.get("commName");     //波特率                             
		Integer baudRate = (Integer) chipReaderMap.get("baud");      //数据位   5 6 7 8                   
		Integer dataBit = (Integer) chipReaderMap.get("dataBit");    //停止位    1,1.5,2                  
		Integer stopBit = (Integer) chipReaderMap.get("stopBit");    //校验位    None，Even，Odd，Space，Mark 
		if(stopBit == 15){
			stopBit = 3;
		}
		Integer checkMode = (Integer) chipReaderMap.get("parit");
		if(null == comPort || "".equals(comPort) ){
//			msgLabel.setText("天平的连接信息未设置。");
			return "天平的连接信息未设置";
		}
		//获取系统中所有的通讯端口
		portList=CommPortIdentifier.getPortIdentifiers();
		boolean isExistComPort=false;   //设置的端口是否存在
		while(portList.hasMoreElements()){
			SerialPort serialPort =null;
			System.out.println(portList.hasMoreElements());
			//强制转换为通讯端口类型
			CommPortIdentifier portId=(CommPortIdentifier) portList.nextElement();
			if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
				System.out.println(portId.getName());
				if(portId.getName().equals(comPort)){//判断串口名是否相同
					isExistComPort=true;//串口存在
					try {
						if(portId.isCurrentlyOwned()){
//							msgLabel.setText("天平端口（"+comPort+"）被占用！");
							return "天平端口（"+comPort+"）被占用";
						}
						serialPort = (SerialPort) portId.open("0001", 2000);//打开端口
						
						//isUsePortId = portId;	//留备份，关闭用
						isUsePortIdList.add(portId);
						isUseingSerialPortList.add(serialPort);
						//isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
//						msgLabel.setText("天平端口（"+comPort+"）被占用！");
						//e1.printStackTrace();
						return "天平端口（"+comPort+"）被占用";
					}
					//设置串口监听器
					try {
						serialPort.addEventListener(new SerialPortEventListener_bal(balCode));
					} catch (TooManyListenersException e1) {
						serialPort.close(); 
					}
					//侦听到串口有数据,触发串口事件
					serialPort.notifyOnDataAvailable(true);  
					isOpening =true;
					try {
						serialPort.setSerialPortParams(baudRate, 
								dataBit, 
								stopBit, 
								checkMode);
//						msgLabel.setText("天平已连接。");
						return "天平已连接";
					} catch (UnsupportedCommOperationException e1) {
						e1.printStackTrace();
					}
					
				}//if end;
				//已经找到端口，退出while循环
				if(isExistComPort){
					break;
				}
			}//if end;
		}//while end;
		if(!isExistComPort){
//			msgLabel.setText("天平端口（"+comPort+"）不存在！");
			return "天平端口（"+comPort+"）不存在";
		}else{
			isExistComPort=false;
		}
		readThread=new Thread(this);
		readThread.start();//线程负责每接收一次数据休眠2秒钟
		return "";
	}

	/**
	 * 关闭端口
	 */
	private void closePort(){
		if(null != isUsePortIdList  && isUsePortIdList.size() > 0 ){
			//关闭关闭端口连接
			for(int i = 0;i<isUseingSerialPortList.size();i++){
				isUseingSerialPortList.get(i).removeEventListener();
				isUseingSerialPortList.get(i).notifyOnDataAvailable(false); 
				isUseingSerialPortList.get(i).close();
			}
//			isUseingSerialPort.removeEventListener();
//			isUseingSerialPort.notifyOnDataAvailable(false); 
//			isUseingSerialPort.close();
			isOpening =false;
			
		}
//		if(null != msgLabel){
//			msgLabel.setText("");
//		}
		//isUsePortId = null;
		isUsePortIdList = new ArrayList<CommPortIdentifier>();
//		isUseingSerialPort = null;
		isUseingSerialPortList = new ArrayList<SerialPort>();
		portList = null;
	}
	
	
	/**
	 * 初始化脏器固定标签页
	 */
	private void initFixedTab() {
		// 
		//初始化  脏器选择ListView
		initVisceraListView();
		//初始化 固定信息表
		initVisceraFixedTable();
		
		initShowOtherAbnCheckBox();
	}


	
	private void initShowOtherAbnCheckBox() {
		showOtherAbnCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue == true){
					showOtherAbn = true;
				}else{
					showOtherAbn = false;
				}
				Animal selectedItem = animalTable.getSelectionModel().getSelectedItem();
				updateAbnVisceraTable(selectedItem.getStudyNo(),selectedItem.getAnimalCode());
			}
		});
	}

	/**
	 * 初始化 固定信息表
	 */
	private void initVisceraFixedTable() {
		visceraFixedTable.setItems(data_visceraFixedTable);
		//清空选择
		visceraFixedTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<VisceraFixed>(){

			@Override
			public void changed(ObservableValue<? extends VisceraFixed> arg0, VisceraFixed arg1,
					VisceraFixed arg2) {
				visceraFixedTable.getSelectionModel().clearSelection();
			}
		});
		visceraNameCol_fixed.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("visceraName"));
		operateCol_fixed.setCellValueFactory(new PropertyValueFactory<VisceraFixed,Boolean>("operate"));
		operateCol_fixed.setCellFactory(new Callback<TableColumn<VisceraFixed, Boolean>, TableCell<VisceraFixed, Boolean>>() {

            public TableCell<VisceraFixed, Boolean> call(TableColumn<VisceraFixed, Boolean> p) {

            	HyperlinkCell_Fixed<VisceraFixed, Boolean> cell = new HyperlinkCell_Fixed<VisceraFixed, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * 初始化  脏器选择ListView
	 */
	private void initVisceraListView() {
		visceraListView.setItems(data_visceraListView);
		visceraListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
			@Override
			public void changed(ObservableValue<? extends CheckBox> arg0, CheckBox arg1,
					CheckBox newValue) {
				if(null != newValue){
					visceraListView.getSelectionModel().clearSelection();
					newValue.setSelected(!newValue.isSelected());
				}
				
			}
		});
	}

	/**
	 * 加载数据
	 * @param sessionType 
	 * @param taskIdList 
	 * @param sessionIdList 
	 */
	public void loadData(List<String> taskIdList, Integer sessionType, List<String> sessionIdList) {
		noAnimalSelected = true;
		noOpenBal = true;
		msgLabel_fixed.setText("");
		// 加载数据
		AnatomyProcessPage.taskIdList = taskIdList;
		AnatomyProcessPage.sessionType = sessionType;
		AnatomyProcessPage.sessionIdList = sessionIdList;
		updateAnimalTable(sessionIdList);
		//调整tab的显示
		updatePaneTab(sessionType);
		//设置三面板为灰色(anatomyPane,weighPane,fixedPane)
		set3PaneDisabled(true);
		//设置 taskIdSessionIdMap的值
		setTaskIdSessionIdMap(sessionIdList);
		isFixedCheckbox.setSelected(false);
		if(sessionType==6 || sessionType==7){
			isFixedCheckbox.setDisable(false);
		}else{
			isFixedCheckbox.setDisable(true);
		}
		isget =  true;
		
		initHandlerLabel();
		
		if(sessionType == 1 || sessionType == 3 || sessionType== 5 || sessionType== 7){
			contextMenu.getItems().clear();
			contextMenu.getItems().add(menuItem_delete);
		}else{
			contextMenu.getItems().clear();
		}
		
		//显示其他病变
		showOtherAbn = false;;
		showOtherAbnCheckBox.setSelected(false) ;
		
		taskIdScopeMap.clear();
		isWeight = false;
		
		//开启定时器
		startTimer();
	}

	/**
	 * 开启定时器
	 */
	private void startTimer(){
		if(sessionType == 4 || sessionType == 6 || sessionType == 7){
			timer = new Timer();
			timer.schedule(new TimerTask(){
				
				@Override
				public void run() {
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							String msg = "";
							List<String> animalCodeList = BaseService.getInstance().getTblAnatomyAnimalService()
									.fixedTimeoutBySessionIdList(AnatomyProcessPage.sessionIdList);
							if(null != animalCodeList && animalCodeList.size() > 0){
								if(animalCodeList.size() > 3){
									msg = animalCodeList.get(0)+"、"+animalCodeList.get(1)+"、"+animalCodeList.get(2)+" 等" +
											"未固定完成，请及时固定完成！";
								}else{
									msg = animalCodeList.get(0);
									for(int i = 1;i<animalCodeList.size() ;i++){
										msg = msg+"、";
										msg = msg +animalCodeList.get(i);
									}
									msg = msg +" 未固定完成，请及时固定完成！";
								}
								
							}
							showFixedMsgLabel(msg);
						}
					});
					
				}}, 1000,60*1000);
		}
	}
	
	/**
	 * 关闭定时器
	 */
	private void closeTimer(){
		if(sessionType == 4 || sessionType == 6 || sessionType == 7){
			timer.cancel();
		}
	}
	
	/**设置三面板为灰色(anatomyPane,weighPane,fixedPane)
	 * @param b
	 */
	private void set3PaneDisabled(boolean b) {
		anatomyPane.setDisable(b);
		weighPane.setDisable(b);
		fixedPane.setDisable(b);
	}

	/**
	 * 设置 taskIdSessionIdMap的值
	 */
	private void setTaskIdSessionIdMap(List<String> sessionIdList) {
		taskIdSessionIdMap= BaseService.getInstance()
				.getTblPathSessionService().getTaskIdSessionIdMapBySessionIdList(sessionIdList);
		
	}

	/**调整tab的显示
	 * @param sessionType2
	 */
	private void updatePaneTab(Integer sessionType2) {
//		private TabPane tabPane; // 标签面板TabPane
//		private Tab anatomyTab; // 动物解剖Tab
//		private Tab weightTab; // 脏器称重Tab
//		private Tab fixTab; // 脏器固定Tab
		
		tabPane.getTabs().clear();
//		tabPane.getTabs().add(anatomyTab);
//		tabPane.getTabs().add(weightTab);
//		tabPane.getTabs().add(fixTab);
		if(null != sessionType2){
			switch (sessionType2) {
			case 1:
				anatomyTab.setDisable(false);
				weightTab.setDisable(true);
				fixTab.setDisable(true);
				tabPane.getTabs().add(anatomyTab);
//				tabPane.getTabs().add(weightTab);
//				tabPane.getTabs().add(fixTab);
				break;
			case 2:
				anatomyTab.setDisable(true);
				weightTab.setDisable(false);
				fixTab.setDisable(true);
//				tabPane.getTabs().add(anatomyTab);
				tabPane.getTabs().add(weightTab);
//				tabPane.getTabs().add(fixTab);
				break;
			case 3:
				anatomyTab.setDisable(false);
				weightTab.setDisable(false);
				fixTab.setDisable(true);
				tabPane.getTabs().add(anatomyTab);
				tabPane.getTabs().add(weightTab);
//				tabPane.getTabs().add(fixTab);
				break;
			case 4:
				anatomyTab.setDisable(true);
				weightTab.setDisable(true);
				fixTab.setDisable(false);
//				tabPane.getTabs().add(anatomyTab);
//				tabPane.getTabs().add(weightTab);
				tabPane.getTabs().add(fixTab);
				break;
			case 5:
				anatomyTab.setDisable(false);
				weightTab.setDisable(true);
				fixTab.setDisable(false);
				tabPane.getTabs().add(anatomyTab);
//				tabPane.getTabs().add(weightTab);
				tabPane.getTabs().add(fixTab);
				break;
			case 6:
				anatomyTab.setDisable(true);
				weightTab.setDisable(false);
				fixTab.setDisable(false);
//				tabPane.getTabs().add(anatomyTab);
				tabPane.getTabs().add(weightTab);
				tabPane.getTabs().add(fixTab);
				break;
			case 7:
				anatomyTab.setDisable(false);
				weightTab.setDisable(false);
				fixTab.setDisable(false);
				tabPane.getTabs().add(anatomyTab);
				tabPane.getTabs().add(weightTab);
				tabPane.getTabs().add(fixTab);
				break;
			case 8:
				anatomyTab.setDisable(true);
				weightTab.setDisable(false);
				fixTab.setDisable(true);
//				tabPane.getTabs().add(anatomyTab);
				tabPane.getTabs().add(weightTab);
//				tabPane.getTabs().add(fixTab);
				break;

			default:
				break;
			}
		}
	}

	/**更新动物表格数据
	 * @param sessionIdList2
	 */
	/**
	 * @param sessionIdList2
	 */
	private void updateAnimalTable(List<String> sessionIdList2) {
		animalTable.getSelectionModel().clearSelection();
		data_animalTable.clear();
//		根据会话id列表，返回对应动物列表（mapList，map中对应字段有：taskId,studyNo，
//		 animalCode，sessionCreator操作者 ，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识,
//		 visceraWeighFinishFlag称重完成标识,visceraFixedFinishFlag固定完成标识,
//		 visceraFixedWeighFinishFlag固定后称重完成标识,anatomyOperator解剖者,
//		 anatomyBeginTime解剖开始时间
		List<Map<String,Object>> mapList = null;
		if(!anatomyTimeCheckBox.isSelected()){
			mapList = BaseService.getInstance().getTblAnatomyAnimalService()
					.getListBySessionIdList(sessionIdList2);
		}else{
			mapList = BaseService.getInstance().getTblAnatomyAnimalService()
					.getListBySessionIdListOrderByAnatomyBeginTime(sessionIdList2);
		}
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				String studyNo = (String) map.get("studyNo");
				String taskId = (String) map.get("taskId");
				String sessionCreator = (String) map.get("sessionCreator");
				String animalCode = (String) map.get("animalCode");
				Integer anatomyCheckFinishFlag = (Integer) map.get("anatomyCheckFinishFlag");
				Integer autolyzeFlag = (Integer) map.get("autolyzeFlag");
				Integer visceraWeighFinishFlag = (Integer) map.get("visceraWeighFinishFlag");
				Integer visceraFixedFinishFlag = (Integer) map.get("visceraFixedFinishFlag");
				Integer visceraFixedWeighFinishFlag = (Integer) map.get("visceraFixedWeighFinishFlag");
				String anatomyOperator = (String) map.get("anatomyOperator");
				Date anatomyBeginTime = (Date) map.get("anatomyBeginTime");
				String state = "";
				if(null == autolyzeFlag || autolyzeFlag == 0){
					if(null != sessionType && sessionType == 8){
						//固定后称重
						if(null != visceraFixedWeighFinishFlag && visceraFixedWeighFinishFlag == 1){
							state = "固定后称重已完成";
						}else{
							state = "固定后称重中";
						}
					}else if(null != sessionType){
						
						switch (sessionType) {
						case 1:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								state = "解剖完成";
							}else{
								state = "解剖中";
							}
							break;
						case 2:
							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
								state = "称重完成";
							}else{
								state = "称重中";
							}
							break;
						case 3:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
									state = "称重完成";
								}else{
									state = "称重中";
								}
							}else{
								state = "解剖中";
							}
							break;
						case 4:
							if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
								state = "固定完成";
							}else{
								state = "固定中";
							}
							break;
						case 5:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
									state = "固定完成";
								}else{
									state = "固定中";
								}
							}else{
								state = "解剖中";
							}
							break;
						case 6:
							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
								if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
									state = "固定完成";
								}else{
									state = "固定中";
								}
							}else{
								state = "称重中";
							}
							break;
						case 7:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
									if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
										state = "固定完成";
									}else{
										state = "固定中";
									}
								}else{
									state = "称重中";
								}
							}else{
								state = "解剖中";
							}
							break;
							
						default:
							break;
						}
					}
				}else{
					state = "自溶";
				}
				data_animalTable.add(new Animal(animalCode,state,sessionCreator,studyNo,taskId
					,autolyzeFlag,anatomyCheckFinishFlag,visceraWeighFinishFlag,visceraFixedFinishFlag,
					visceraFixedWeighFinishFlag,anatomyOperator,anatomyBeginTime
						));
			}
		}else{
			
			for(String sessionId:sessionIdList){
			    //可以确认数据，将检查会话是否已确认
			    TblPathSession t=BaseService.getInstance().getTblPathSessionService().getById(sessionId);
			    if(null != t.getSessionFinishSign()){
//			    	showErrorMessage("所选会话已签字确认,不可继续操作！");
			    	return;
			    }
			}
			//打开动物窗口
			openAnatomyAnimalPage();
			
		}
		
		
	}
	
	/**
	 * 打开动物窗口
	 */
	private void openAnatomyAnimalPage(){
		Stage stage = Main.stageMap.get("AnatomyAnimalPage");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				AnatomyAnimalPage.getInstance().start(stage);
				AnatomyAnimalPage.getInstance().loadData(taskIdList, sessionType,sessionIdList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AnatomyAnimalPage",stage);
			
//			AnatomyAnimalPage.getInstance().loadData(taskIdList, sessionType,sessionIdList);
		}else{
			AnatomyAnimalPage.getInstance().loadData(taskIdList, sessionType,sessionIdList);
			stage.show();
		}
	}
	
	/**更新动物表格数据并选中指定动物
	 * @param sessionIdList2
	 */
	public void updateAnimalTable(List<String> sessionIdList2,String currentstudyNo,String currentanimalCode) {
		animalTable.getSelectionModel().clearSelection();
		data_animalTable.clear();
//		根据会话id列表，返回对应动物列表（mapList，map中对应字段有：taskId,studyNo，
//		 animalCode，sessionCreator操作者 ，anatomyCheckFinishFlag剖检完成标识,autolyzeFlag自溶标识,
//		 visceraWeighFinishFlag称重完成标识,visceraFixedFinishFlag固定完成标识,
//		 visceraFixedWeighFinishFlag固定后称重完成标识,anatomyOperator解剖者,
//		 anatomyBeginTime解剖开始时间
//		 
//		List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyAnimalService()
//				.getListBySessionIdList(sessionIdList2);
		List<Map<String,Object>> mapList = null;
		if(!anatomyTimeCheckBox.isSelected()){
			mapList = BaseService.getInstance().getTblAnatomyAnimalService()
					.getListBySessionIdList(sessionIdList2);
		}else{
			mapList = BaseService.getInstance().getTblAnatomyAnimalService()
					.getListBySessionIdListOrderByAnatomyBeginTime(sessionIdList2);
		}
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				String studyNo = (String) map.get("studyNo");
				String taskId = (String) map.get("taskId");
				String sessionCreator = (String) map.get("sessionCreator");
				String animalCode = (String) map.get("animalCode");
				Integer anatomyCheckFinishFlag = (Integer) map.get("anatomyCheckFinishFlag");
				Integer autolyzeFlag = (Integer) map.get("autolyzeFlag");
				Integer visceraWeighFinishFlag = (Integer) map.get("visceraWeighFinishFlag");
				Integer visceraFixedFinishFlag = (Integer) map.get("visceraFixedFinishFlag");
				Integer visceraFixedWeighFinishFlag = (Integer) map.get("visceraFixedWeighFinishFlag");
				String anatomyOperator = (String) map.get("anatomyOperator");
				Date anatomyBeginTime = (Date) map.get("anatomyBeginTime");
				String state = "";
				if(null == autolyzeFlag || autolyzeFlag == 0){
					if(null != sessionType && sessionType == 8){
						//固定后称重
						if(null != visceraFixedWeighFinishFlag && visceraFixedWeighFinishFlag == 1){
							state = "固定后称重已完成";
						}else{
							state = "固定后称重中";
						}
					}else if(null != sessionType){
						
						switch (sessionType) {
						case 1:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								state = "解剖完成";
							}else{
								state = "解剖中";
							}
							break;
						case 2:
							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
								state = "称重完成";
							}else{
								state = "称重中";
							}
							break;
						case 3:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
									state = "称重完成";
								}else{
									state = "称重中";
								}
							}else{
								state = "解剖中";
							}
							break;
						case 4:
							if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
								state = "固定完成";
							}else{
								state = "固定中";
							}
							break;
						case 5:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
									state = "固定完成";
								}else{
									state = "固定中";
								}
							}else{
								state = "解剖中";
							}
							break;
						case 6:
							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
								if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
									state = "固定完成";
								}else{
									state = "固定中";
								}
							}else{
								state = "称重中";
							}
							break;
						case 7:
							if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
								if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
									if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
										state = "固定完成";
									}else{
										state = "固定中";
									}
								}else{
									state = "称重中";
								}
							}else{
								state = "解剖中";
							}
							break;
							
						default:
							break;
						}
					}
				}else{
					state = "自溶";
				}
				data_animalTable.add(new Animal(animalCode,state,sessionCreator,studyNo,taskId
					,autolyzeFlag,anatomyCheckFinishFlag,visceraWeighFinishFlag,visceraFixedFinishFlag,
					visceraFixedWeighFinishFlag,anatomyOperator,anatomyBeginTime
						));
			}
		}
		//选中指定动物
		if(null != currentstudyNo && null != currentanimalCode){
			int index = 0;
			if(null != data_animalTable && data_animalTable.size() > 0 ){
				for(Animal animal:data_animalTable){
					if(currentstudyNo.equals(animal.getStudyNo()) 
							&& currentanimalCode.equals(animal.getAnimalCode())){
						break;
					}
					index++;
				}
			}
			animalTable.getSelectionModel().select(index);
		}
	}

	/**动物表格中 最后一行数据中的  解剖操作者
	 * @return
	 */
	public String getLastOperatorOfAnimalTable(){
		if(data_animalTable.size() > 0){
			return data_animalTable.get(data_animalTable.size() - 1).getAnatomyOperator();
		}
		return null;
	}
	/**
	 * 增加一行动物树放在最后一行，并选中最后一行
	 */
//	public void AddOneDataToAnimalTable(Map<String,Object> map){
//		if(null != map ){
//			String studyNo = (String) map.get("studyNo");
//			String taskId = (String) map.get("taskId");
//			String sessionCreator = (String) map.get("sessionCreator");
//			String animalCode = (String) map.get("animalCode");
//			Integer anatomyCheckFinishFlag = (Integer) map.get("anatomyCheckFinishFlag");
//			Integer autolyzeFlag = (Integer) map.get("autolyzeFlag");
//			Integer visceraWeighFinishFlag = (Integer) map.get("visceraWeighFinishFlag");
//			Integer visceraFixedFinishFlag = (Integer) map.get("visceraFixedFinishFlag");
//			Integer visceraFixedWeighFinishFlag = (Integer) map.get("visceraFixedWeighFinishFlag");
//			String anatomyOperator = (String) map.get("anatomyOperator");
//			Date anatomyBeginTime = (Date) map.get("anatomyBeginTime");
//			String state = "";
//			if(null == autolyzeFlag || autolyzeFlag == 0){
//				if(null != sessionType && sessionType == 8){
//					//固定后称重
//					if(null != visceraFixedWeighFinishFlag && visceraFixedWeighFinishFlag == 1){
//						state = "固定后称重已完成";
//					}else{
//						state = "固定后称重中";
//					}
//				}else if(null != sessionType){
//					
//					switch (sessionType) {
//					case 1:
//						if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
//							state = "解剖完成";
//						}else{
//							state = "解剖中";
//						}
//						break;
//					case 2:
//						if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
//							state = "称重完成";
//						}else{
//							state = "称重中";
//						}
//						break;
//					case 3:
//						if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
//							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
//								state = "称重完成";
//							}else{
//								state = "称重中";
//							}
//						}else{
//							state = "解剖中";
//						}
//						break;
//					case 4:
//						if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
//							state = "固定完成";
//						}else{
//							state = "固定中";
//						}
//						break;
//					case 5:
//						if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
//							if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
//								state = "固定完成";
//							}else{
//								state = "固定中";
//							}
//						}else{
//							state = "解剖中";
//						}
//						break;
//					case 6:
//						if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
//							if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
//								state = "固定完成";
//							}else{
//								state = "固定中";
//							}
//						}else{
//							state = "称重中";
//						}
//						break;
//					case 7:
//						if(null != anatomyCheckFinishFlag && anatomyCheckFinishFlag == 1){
//							if(null != visceraWeighFinishFlag && visceraWeighFinishFlag == 1){
//								if(null != visceraFixedFinishFlag && visceraFixedFinishFlag == 1){
//									state = "固定完成";
//								}else{
//									state = "固定中";
//								}
//							}else{
//								state = "称重中";
//							}
//						}else{
//							state = "解剖中";
//						}
//						break;
//						
//					default:
//						break;
//					}
//				}
//			}else{
//				state = "自溶";
//			}
//			data_animalTable.add(new Animal(animalCode,state,sessionCreator,studyNo,taskId
//				,autolyzeFlag,anatomyCheckFinishFlag,visceraWeighFinishFlag,visceraFixedFinishFlag,
//				visceraFixedWeighFinishFlag,anatomyOperator,anatomyBeginTime
//					));
//		}
//	}

	/**更新 选择脏器树 
	 * @param taskId		任务id号
	 * @param studyNo		课题编号
	 * @param animalCode	动物编号
	 * @param isAnatomying    是否解剖中
	 */
	private void updateVisceraTree(String taskId,String studyNo,String animalCode,boolean isAnatomying){
		//更新脏器树（解剖tab）	
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		if(null != taskId){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblAnatomyCheckService().getVisceraCodeAndName(taskId,studyNo,animalCode);
			if(null != mapList && mapList.size() > 0 ){
				for(Map<String,Object> map:mapList){
					String visceraCode = (String) map.get("visceraCode");
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					TreeItem<String> depNode = null;
					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap.get(visceraCode);
					}else{
						depNode = new TreeItem<String>(visceraName);
						visceraName2MapMap.put(visceraName, map);
						rootNode.getChildren().add(depNode);
						visceraCodeTreeItemMap.put(visceraCode, depNode);
					}
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						TreeItem<String> leafNode = new TreeItem<String>();
						leafNode.setValue(subVisceraName);
						visceraName2MapMap.put(subVisceraName, map);
						depNode.getChildren().add(leafNode);
					}
				}
			}
		}
		
		if(isAnatomying){
			TblStudyPlan tblStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
			if(null != tblStudyPlan){
				if(tblStudyPlan.getAbnVisceraAnatomyCheck() == 1){
					otherVisceraBtn.setDisable(false);
				}else{
					otherVisceraBtn.setDisable(true);
				}
			}
		}else{
			//非解剖中，其他脏器按钮不可用
			otherVisceraBtn.setDisable(true);
		}
	}
	
	/**
	 * viseraTree增加一数据并，选中它；成功返回true，失败返回false：msg
	 * @param map
	 * @return
	 */
	public Json addOneItemAndSelectIt(String currentVisceraName,Map<String, Object> map) {
		Json json = new Json();
		
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			json.setMsg("请先在解剖窗口中选择动物！");
			return json;
		}
		if(null != map && null != currentVisceraName){
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			//String subVisceraCode = (String) map.get("subVisceraCode");
			String subVisceraName = (String) map.get("subVisceraName");
			
			//判断是否存在
			if(visceraName2MapMap.keySet().contains(currentVisceraName)){
				json.setMsg("脏器（"+currentVisceraName+"）已存在!");
			}else{
				TreeItem<String> depNode = null;
				//是子节点
				if(currentVisceraName.equals(subVisceraName)){
					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap.get(visceraCode);
					}else{
						depNode = new TreeItem<String>(visceraName);
						visceraName2MapMap.put(visceraName, map);
						rootNode.getChildren().add(depNode);
						visceraCodeTreeItemMap.put(visceraCode, depNode);
					}
					//增加子节点
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(leafNode);
					visceraTree.getFocusModel().focus(rootNode.getChildren().size()-1);
					visceraTree.scrollTo(rootNode.getChildren().size());
				}else{
					depNode = new TreeItem<String>(visceraName);
					visceraName2MapMap.put(visceraName, map);
					rootNode.getChildren().add(depNode);
					visceraCodeTreeItemMap.put(visceraCode, depNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(depNode);
					visceraTree.getFocusModel().focus(rootNode.getChildren().size());
					visceraTree.scrollTo(rootNode.getChildren().size());
				}
				
			}
			
			
		}
		return json;
	}
	
	/**根据脏器编号更新    解剖学所见部位
	 * @param visceraCode
	 */
	private void updateAnatomyPosListViewData(String visceraCode){
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();
		pyDescCNMap_anatomyPos.clear();
		List<DictPathCommon> dictPathCommon1List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,sortMethod);
		if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
				data_anatomyPosListView.add(obj.getDescCn());
				anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
				pyDescCNMap_anatomyPos.put(obj.getPy(), obj.getDescCn());
			}
		}
	}
	
	/**根据脏器编号更新    解剖学所见(通用，特殊)
	 * @param visceraCode
	 */
	private void updateAnatomyFindingListViewData(String visceraCode){
		if(tongyongRadioButton.isSelected()){
			if(visceraCode != null){
//				anatomyFindingListView.getSelectionModel().clearSelection();
				anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
			}
			
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			pyDescCNMap_teshu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,sortMethod);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					data_anatomyFindingListView_tesu.add(obj.getDescCn());
					anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
					pyDescCNMap_teshu.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}
	
	/**
	 * 设置5按钮状态,true可用，false不可用
	 */
	private void set5BtnState(boolean flag){
		autolyzeBtn.setDisable(!flag);
//		otherVisceraBtn.setDisable(!flag);
		registerBtn.setDisable(!flag);
		autolyzeBtn2.setDisable(!flag);
		otherNABtn.setDisable(!flag);
	}
	
	/**
	 * 更新 anatomyFindingLabel 值
	 */
	private void updateAnatomyFindingLabelText(){
		String finding = "";
		
		finding = finding + (visceraTree.getSelectionModel().getSelectedItem() == null ?
				"":visceraTree.getSelectionModel().getSelectedItem().getValue()+" ");
		finding = finding + (bodySufacePosListView.getSelectionModel().getSelectedItem() == null ?
				"":bodySufacePosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyPosListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyPosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (posListView.getSelectionModel().getSelectedItem() == null ?
				"":posListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (numberListView.getSelectionModel().getSelectedItem() == null ?
				"":numberListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (rangeListView.getSelectionModel().getSelectedItem() == null ?
				"":rangeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + 
				((sizeTextField.getText() == null || "".equals(sizeTextField.getText())) ? 
				"":sizeTextField.getText()+" ");
		finding = finding + (colorListView.getSelectionModel().getSelectedItem() == null ?
				"":colorListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (textureListView.getSelectionModel().getSelectedItem() == null ?
				"":textureListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (shapeListView.getSelectionModel().getSelectedItem() == null ?
				"":shapeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		finding = finding + (lesionDegreeListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionDegreeListView.getSelectionModel().getSelectedItem()+" ");
		
		anatomyFindingLabel.setText(finding);
	}
	
	/**不含脏器名称
	 * @return
	 */
	private String getAnatomyFinding(){
		String finding = "";
		
//		finding = finding + (visceraTree.getSelectionModel().getSelectedItem() == null ?
//				"":visceraTree.getSelectionModel().getSelectedItem().getValue()+" ");
		finding = finding + (bodySufacePosListView.getSelectionModel().getSelectedItem() == null ?
				"":bodySufacePosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyPosListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyPosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (posListView.getSelectionModel().getSelectedItem() == null ?
				"":posListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (numberListView.getSelectionModel().getSelectedItem() == null ?
				"":numberListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (rangeListView.getSelectionModel().getSelectedItem() == null ?
				"":rangeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + 
				((sizeTextField.getText() == null || "".equals(sizeTextField.getText())) ? 
				"":sizeTextField.getText()+" ");
		finding = finding + (colorListView.getSelectionModel().getSelectedItem() == null ?
				"":colorListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (textureListView.getSelectionModel().getSelectedItem() == null ?
				"":textureListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (shapeListView.getSelectionModel().getSelectedItem() == null ?
				"":shapeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		finding = finding + (lesionDegreeListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionDegreeListView.getSelectionModel().getSelectedItem()+" ");
		
		return finding;
	}
	
	
	/**
	 * 清空 11个ListView 的选择
	 */
	private void clear11ListViewSelect(){
		
		anatomyPosListView.getSelectionModel().clearSelection(); // 解剖学所见部位ListView
		anatomyFindingListView.getSelectionModel().clearSelection(); // 解剖所见ListView

		bodySufacePosListView.getSelectionModel().clearSelection(); // 体表部位ListView

		posListView.getSelectionModel().clearSelection(); // 位置ListView
		
		shapeListView.getSelectionModel().clearSelection(); // 形状ListView
		
		colorListView.getSelectionModel().clearSelection(); // 颜色ListView

		textureListView.getSelectionModel().clearSelection(); // 硬度ListView
		
		numberListView.getSelectionModel().clearSelection(); // 数量ListView

		rangeListView.getSelectionModel().clearSelection(); // 范围ListView

		lesionDegreeListView.getSelectionModel().clearSelection(); // 病变程度ListView
		
		sizeTextField.setText("");
		sizeListView.getSelectionModel().clearSelection(); // 大小ListView

	}
	
	/**
	 * 脏器树 选择下一个
	 */
	private void selectNext_visceraTree(){
//		visceraTree.getSelectionModel().selectNext();
	}
	/**更新解剖结果表格（anatomyResultTalbe）
	 * @param sessionId
	 * @param flag
	 */
	private void updateAnatomyResultTableData(String sessionId,String animalCode,boolean flag){
		
		data_anatomyResultTable.clear();
		List<TblAnatomyCheck> tblAnatomyCheckList = BaseService.getInstance()
				.getTblAnatomyCheckService().getListBySessionId(sessionId,animalCode);
		if(null != tblAnatomyCheckList){
			for(TblAnatomyCheck tblAnatomyCheck:tblAnatomyCheckList){
				String id = tblAnatomyCheck.getId();
				String visceraName = tblAnatomyCheck.getVisceraName();
				String subVisceraName = tblAnatomyCheck.getSubVisceraName();
				if(null != subVisceraName && !"".equals(subVisceraName)){
					visceraName = subVisceraName;
				}
				String finding = "";
				int autolyzaFlag =  tblAnatomyCheck.getAutolyzaFlag();         //自溶标识       0:否     1:是
				if(autolyzaFlag == 1){
					finding = "自溶";
				}else if(autolyzaFlag == 2){
					String missingRsn = BaseService.getInstance().getTblAnatomyCheckService().getMissRsnByAnatomyCheckId(tblAnatomyCheck.getId());
					if(null !=tblAnatomyCheck.getAnatomyFingding() && null != missingRsn && !"".equals(missingRsn)){
						finding = tblAnatomyCheck.getAnatomyFingding()+":"+missingRsn;
					}else{
						finding = tblAnatomyCheck.getAnatomyFingding();
					}
				}else{
					finding = finding +(isNull(tblAnatomyCheck.getBodySurfacePos()) ? 
							"" :tblAnatomyCheck.getBodySurfacePos()+" "); //体表部位60
					finding = finding + (isNull(tblAnatomyCheck.getAnatomyPos()) ? 
							"" :tblAnatomyCheck.getAnatomyPos()+" ");//解剖学所见部位60
					finding = finding +(isNull(tblAnatomyCheck.getPos()) ? 
							"" :tblAnatomyCheck.getPos()+" ");//位置60
					finding = finding +(isNull(tblAnatomyCheck.getNumber()) ? 
							"" :tblAnatomyCheck.getNumber()+" "); //数量60
					finding = finding +(isNull(tblAnatomyCheck.getRange()) ? 
							"" :tblAnatomyCheck.getRange()+" ");//范围60
					finding = finding +(isNull(tblAnatomyCheck.getSize()) ? 
							"" :tblAnatomyCheck.getSize()+" ");//大小 20
					finding = finding +(isNull(tblAnatomyCheck.getColor()) ? 
							"" :tblAnatomyCheck.getColor()+" ");//颜色60
					finding = finding +(isNull(tblAnatomyCheck.getTexture()) ? 
							"" :tblAnatomyCheck.getTexture()+" ");//硬度60
					finding = finding +(isNull(tblAnatomyCheck.getShape()) ? 
							"" :tblAnatomyCheck.getShape()+" ");//形状60
					finding = finding +(isNull(tblAnatomyCheck.getAnatomyFingding()) ? 
							"" :tblAnatomyCheck.getAnatomyFingding()+" ");  //通用/特殊所见标识     1:通用   2:特殊
					
					finding = finding +(isNull(tblAnatomyCheck.getLesionDegree()) ? 
							"" :tblAnatomyCheck.getLesionDegree()+" ");//病变程度60
				}
				data_anatomyResultTable.add(new AnatomyCheck(id,sessionId,visceraName,finding,flag));
			}
		}
	}
	
	/**
	 * @return
	 */
	private boolean isNull(String str){
		if(null == str){
			return true;
		}else if("".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 增加一行   解剖结果   放在最后一行，并选中最后一行
	 */
	private void AddOneDataToAnatomyResultTable(TblAnatomyCheck tblAnatomyCheck){
		if(null != tblAnatomyCheck){
			String id = tblAnatomyCheck.getId();
			String visceraName = tblAnatomyCheck.getVisceraName();
			String subVisceraName = tblAnatomyCheck.getSubVisceraName();
			if(null != subVisceraName && !"".equals(subVisceraName)){
				visceraName = subVisceraName;
			}
			String finding = "";
			int autolyzaFlag =  tblAnatomyCheck.getAutolyzaFlag();     //自溶标识       0:否     1:是
			if(autolyzaFlag == 1){
				finding = "自溶";
			}else if(autolyzaFlag == 2){
				String missingRsn = BaseService.getInstance().getTblAnatomyCheckService().getMissRsnByAnatomyCheckId(tblAnatomyCheck.getId());
				if(null !=tblAnatomyCheck.getAnatomyFingding() && null != missingRsn && !"".equals(missingRsn)){
					finding = tblAnatomyCheck.getAnatomyFingding()+":"+missingRsn;
				}else{
					finding = tblAnatomyCheck.getAnatomyFingding();
				}
			}else{
				finding = finding +(isNull(tblAnatomyCheck.getBodySurfacePos()) ? 
						"" :tblAnatomyCheck.getBodySurfacePos()+" "); //体表部位60
				finding = finding + (isNull(tblAnatomyCheck.getAnatomyPos()) ? 
						"" :tblAnatomyCheck.getAnatomyPos()+" ");//解剖学所见部位60
				finding = finding +(isNull(tblAnatomyCheck.getPos()) ? 
						"" :tblAnatomyCheck.getPos()+" ");//位置60
				finding = finding +(isNull(tblAnatomyCheck.getNumber()) ? 
						"" :tblAnatomyCheck.getNumber()+" "); //数量60
				finding = finding +(isNull(tblAnatomyCheck.getRange()) ? 
						"" :tblAnatomyCheck.getRange()+" ");//范围60
				finding = finding +(isNull(tblAnatomyCheck.getSize()) ? 
						"" :tblAnatomyCheck.getSize()+" ");//大小 20
				finding = finding +(isNull(tblAnatomyCheck.getColor()) ? 
						"" :tblAnatomyCheck.getColor()+" ");//颜色60
				finding = finding +(isNull(tblAnatomyCheck.getTexture()) ? 
						"" :tblAnatomyCheck.getTexture()+" ");//硬度60
				finding = finding +(isNull(tblAnatomyCheck.getShape()) ? 
						"" :tblAnatomyCheck.getShape()+" ");//形状60
				finding = finding +(isNull(tblAnatomyCheck.getAnatomyFingding()) ? 
						"" :tblAnatomyCheck.getAnatomyFingding()+" ");  //通用/特殊所见标识     1:通用   2:特殊
				
				finding = finding +(isNull(tblAnatomyCheck.getLesionDegree()) ? 
						"" :tblAnatomyCheck.getLesionDegree()+" ");//病变程度60
			}
			data_anatomyResultTable.add(new AnatomyCheck(id,tblAnatomyCheck.getSessionId(),visceraName,finding,true));
			anatomyResultTable.getSelectionModel().selectLast();
		}
	}
	
	/**
	 * 更新  固定-选择脏器  ListView
	 */
	 void updateData_visceraListView(String taskId,String sessionId,String animalCode){
		data_visceraListView.clear();
		if(null != taskId && null != sessionId && null != animalCode){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService()
					.getVisceraMapListByTaskIdSessionIdAnimalCode(taskId, sessionId, animalCode);
			if(null != mapList && mapList.size() > 0){
				CheckBox checkBox = null;
				for(Map<String,Object> map :mapList){
					String visceraName = (String) map.get("visceraName");
					checkBox = new CheckBox(visceraName);
					checkBox.setUserData(map);
					data_visceraListView.add(checkBox);
				}
			}
		}
	}
	
	/**设置 固定面板上2 按钮状态
	 * @param flag
	 */
	private void set2FixedBtn(boolean flag){
//		System.out.println(flag);
		//flag==true 称重中
		fixedBtn.setDisable(!flag);
		visceraMissingButton_fixed.setDisable(!flag);
		fixedFinishBtn.setDisable(!flag);
		abnFixedButton.setDisable(!flag);
	}
	
	/**更新 固定结果表（viscerFixedTable）
	 * @param sessionId
	 * @param animalCode
	 * @param flag   是否可操作
	 */
	private void updateData_visceraFixedTable(String sessionId,String animalCode,boolean flag){
		data_visceraFixedTable.clear();
		if(null != sessionId && null != animalCode){
			List<TblVisceraFixed> tblVisceraFixedList = BaseService.getInstance().getTblVisceraFixedService()
					.getListBySessionIdAnimalCode(sessionId,animalCode);
			if(null != tblVisceraFixedList && tblVisceraFixedList.size() > 0){
				for(TblVisceraFixed obj:tblVisceraFixedList){
					data_visceraFixedTable.add(new VisceraFixed(obj.getId(),obj.getSessionId(),obj.getVisceraName(),flag));
				}
			}
			
		}
	}
	/**设置称重面板上1按钮状态及  课题编号和动物编号labe
	 * @param flag
	 */
	private void set1WeightBtnAndStudyNoAnimalCodeLabel(boolean flag,String studyNo,String animalCode){
		weightFinishBtn.setDisable(!flag);
		visceraMissingButton_weight.setDisable(!flag);
		visceraMissingButton_weight.setDisable(!flag);
		studyNoLabel.setText(studyNo);
		animalCodeLabel.setText(animalCode);
	}
	
	/**
	 * 更新 选择脏器树 ，根据任务id、动物编号(脏器称重)
	 * updateVisceraTree(String taskId,String studyNo,String animalCode,boolean isAnatomying)
	 */
	public void updateVisceraTree_weight(String taskId,String studyNo,String animalCode,boolean isFixed){
		rootNode_weight.getChildren().clear();
		visceraCodeTreeItemMap_weight.clear();
		visceraTree_weight.getSelectionModel().clearSelection();
		if(null != taskId && null != animalCode){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblVisceraWeightService().getVisceraMapListByTaskIdAnimalCode(taskId,animalCode,isFixed);
		
			if(null != mapList && mapList.size() > 0 ){
				for(Map<String,Object> map:mapList){
					String visceraCode = (String) map.get("visceraCode");
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					TreeItem<Label> depNode = null;
					if(visceraCodeTreeItemMap_weight.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap_weight.get(visceraCode);
					}else{
						Integer attachedVisceraFlag = (Integer) map.get("attachedVisceraFlag"); //有无附加脏器
						if(null != attachedVisceraFlag && attachedVisceraFlag == 1){
							String attachedVisceraNames = (String) map.get("attachedVisceraNames");
							visceraName = visceraName+"("+attachedVisceraNames+")";
						}
						Label label = new Label(visceraName);
						label.setUserData(map);
						label.setMinSize(150, 20);
						depNode = new TreeItem<Label>(label);
						depNode.setExpanded(false);
						rootNode_weight.getChildren().add(depNode);
						visceraCodeTreeItemMap_weight.put(visceraCode, depNode);
					}
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						TreeItem<Label> leafNode = new TreeItem<Label>();
						Label label = new Label(subVisceraName);
						label.setUserData(map);
						leafNode.setValue(label);
						label.setMinSize(150, 20);
						depNode.getChildren().add(leafNode);
					}
				}
			}
		}
	}
	
	/**
	 * 增加一行称重信息，并刷新称重结果表
	 */
	public void addOneWeightAndUpdateVisceraWeightTable(String weight,String weightUnit,String balCode){
		if(isget){
			//
			//1.是否可以称重
			if(weightFinishBtn.isDisabled()){
				isget =  false;
				showErrorMessage("该动物称重完毕或未解剖完成！");
				isget =  true;
				return ;
			}
			//2.选择的动物
			Animal animal = animalTable.getSelectionModel().getSelectedItem();
			if(null == animal){
				isget =  false;
				showErrorMessage("请先选择动物！");
				isget =  true;
				return ;
			}
			
			if(!isWeight){
				boolean isset = Messager.showSimpleConfirm("提示", "此任务未设置称重报警范围，是否设置？");
				if(isset){
					// 
					try {
			    		Stage stage = Main.stageMap.get("UpdateWeightUpAndLo");
			    		if(null == stage){
			    			stage =new Stage();
			    			stage.initOwner(Main.mainWidow);
			    			stage.initModality(Modality.APPLICATION_MODAL);
			    			UpdateWeightUpAndLo.getInstance().start(stage);
			    			Main.stageMap.put("UpdateWeightUpAndLo",stage);
			    		}else{
			    			stage.show();
			    		}
			    		UpdateWeightUpAndLo.getInstance().loadData(taskIdList);
			    	} catch (Exception e) {
			    		e.printStackTrace();
			    	}
					return;
				}else{
					isWeight = true;
					taskIdScopeMap.put(animal.getTaskId(), true);
				}
			}
			
			String weightLower =  weightLowerLabel.getText();
			String weightUpper =  weightUpperLabel.getText();
			Double lower = 0.0;
			Double upper = 0.0;
		    String s1 = weightLower.replace("%","");
		    String s2 = weightUpper.replace("%","");
		    //  称重报警范围
			if(!(otherWeight+"").equals(0.0)){
				if(null != weightLower && !weightLower.equals("") && !s1.equals("暂无报警下限")  ){
					lower = otherWeight-(otherWeight*Double.parseDouble(s1))/100;
					if(( lower > Double.parseDouble(weight) ) && lower > 0 ){
						isget =  false;
						if(Messager.showSimpleConfirm("提示","低于同组该脏器的平均值，是否继续？")){
							isget =  true;
						}else{
							isget =  true;	
							return;
						}
					}
				}
					
		        if(null != weightUpper && !weightUpper.equals("")&& !s2.equals("暂无报警上限")){
		        	 upper = otherWeight+(otherWeight*Double.parseDouble(s2))/100;
		        	 if( (upper < Double.parseDouble(weight)) && upper > 0 ){
		        		isget =  false;
		 				if(Messager.showSimpleConfirm("提示","高于同组该脏器的平均值，是否继续？")){
		 					isget =  true;	
		 				}else{
		 					isget =  true;	
		 					return;
		 				}
		 			}
				}
			}
	        
			TreeItem<Label> treeSelectedItem = visceraTree_weight.getSelectionModel().getSelectedItem();
			
			if(null == treeSelectedItem){
				isget =  false;
				showErrorMessage("请先选择脏器！");
				isget =  true;
				return ;
			}
			
			String sessionId = taskIdSessionIdMap.get(animal.getTaskId());
			String studyNo = animal.getStudyNo();
			String animalCode = animal.getAnimalCode();
			String taskId = animal.getTaskId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>)treeSelectedItem.getValue().getUserData();
			
			Integer visceraType = (Integer) map.get("visceraType");
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraCode = (String) map.get("subVisceraCode");
			String subVisceraName = (String) map.get("subVisceraName");
			Integer partVisceraSeparateWeigh = (Integer) map.get("partVisceraSeparateWeigh");
			Integer attachedVisceraFlag = (Integer) map.get("attachedVisceraFlag");
			String attachedVisceraNames = (String) map.get("attachedVisceraNames");

			
//			TblPathPlanVisceraWeigh pathPlanVisceraWeigh =  BaseService.getInstance().getTblPathPlanVisceraWeighService().getByVisceraCode(visceraCode, studyNo);
//	        partVisceraSeparateWeigh =  pathPlanVisceraWeigh.getPartVisceraSeparateWeigh();
			//3.是否成对脏器分开称重
			if(null != partVisceraSeparateWeigh && partVisceraSeparateWeigh == 1){
				if(!treeSelectedItem.isLeaf()){
					isget =  false;
					showErrorMessage("成对脏器分开称重！");
					isget = true;
					return ;
				}
			}
			//4.固定后称重
			Integer fixedWeighFlag = 0;
			if(sessionType == 8){
				fixedWeighFlag = 1;
			}
			//5.
//			balCode
			
			String hostName = SystemTool.getHostName();
			
			String operator = SaveUserInfo.getUserName();
			Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			
			//设备检定有效期
			Date balValidDate = null;
			TblBalReg tblBalReg = BaseService.getInstance().getTblBalRegService().getById(balCode);
			if(null != tblBalReg){
				balValidDate = tblBalReg.getValidDate();
			}
			//天平校准ID
			String calIndexId = BaseService.getInstance().getTblBalCalibrationIndexService().getPassCalId(balCode);
			
			//6.保存
			TblVisceraWeight tblVisceraWeight = new TblVisceraWeight();
			
			tblVisceraWeight.setSessionId(sessionId);
			tblVisceraWeight.setStudyNo(studyNo);
			tblVisceraWeight.setAnimalCode(animalCode);
			tblVisceraWeight.setVisceraType(visceraType);
			tblVisceraWeight.setVisceraCode(visceraCode);
			tblVisceraWeight.setVisceraName(visceraName);
			if(treeSelectedItem.isLeaf()){
				tblVisceraWeight.setSubVisceraCode(subVisceraCode);
				tblVisceraWeight.setSubVisceraName(subVisceraName);
			}
			tblVisceraWeight.setWeight(weight);
			tblVisceraWeight.setWeightUnit(weightUnit);
			tblVisceraWeight.setAttachedVisceraFlag(attachedVisceraFlag);
			tblVisceraWeight.setFixedWeightFlag(fixedWeighFlag);
			tblVisceraWeight.setAttachedVisceraNames(attachedVisceraNames);
			tblVisceraWeight.setOperator(operator);
			tblVisceraWeight.setOperateTime(currentDate);
			tblVisceraWeight.setBalCode(balCode);
			tblVisceraWeight.setBalValidDate(balValidDate);
			tblVisceraWeight.setHostName(hostName);
			tblVisceraWeight.setCalIndexId(calIndexId);
			TblVisceraWeight oldObj = BaseService.getInstance().getTblVisceraWeightService().getOne(tblVisceraWeight.getStudyNo(),
					tblVisceraWeight.getAnimalCode(), tblVisceraWeight.getVisceraCode()
					,tblVisceraWeight.getSubVisceraCode());
			
			int isHave=0;
			String userName_Fixed=null;
			boolean falg = false;
			if(null != oldObj ){
				isget =  false;
				if(Messager.showSimpleConfirm("提示","该脏器已称重，是否对该脏器重新称重？")){
					
					//签字通过
					if(Sign.openSignWithReasonFrame("重新称量原因", "重新称量")){
						isHave=1;
						String userName = "";
						User user = SaveUserInfo.getUser();
						if(null != user){
							userName = user.getUserName();
							userName_Fixed=userName;
						}
						TblVisceraWeightHis his = new TblVisceraWeightHis();
						his.setId(BaseService.getInstance().getTblVisceraWeightHisService().getKey());
						his.setOldId(oldObj.getId());
						his.setAnimalCode(oldObj.getAnimalCode());
						his.setAttachedVisceraFlag(oldObj.getAttachedVisceraFlag());
						his.setAttachedVisceraNames(oldObj.getAttachedVisceraNames());
						his.setBalCode(oldObj.getBalCode());
						his.setBalValidDate(oldObj.getBalValidDate());
						his.setCalIndexId(oldObj.getCalIndexId());
						his.setFixedWeightFlag(oldObj.getFixedWeightFlag());
						his.setHostName(oldObj.getHostName());
						his.setOperate("重新称量");
						his.setOperateDate(currentDate);
						his.setOperater(userName);
						his.setOperateTime(oldObj.getOperateTime());
						his.setOperator(oldObj.getOperator());
						his.setSessionId(oldObj.getSessionId());
						his.setStudyNo(oldObj.getStudyNo());
						his.setSubVisceraCode(oldObj.getSubVisceraCode());
						his.setSubVisceraName(oldObj.getSubVisceraName());
						his.setVisceraCode(oldObj.getVisceraCode());
						his.setVisceraName(oldObj.getVisceraName());
						his.setVisceraType(oldObj.getVisceraType());
						his.setWeight(oldObj.getWeight());
						his.setWeightUnit(oldObj.getWeightUnit());
						//重新称量原因
						his.setOperateRsn(Sign.getReason());
						BaseService.getInstance().getTblVisceraWeightHisService().save(his);
						falg = true;
					}
				}else{
					falg = false;
				};
				isget =  true;
			}else{
				falg = true;
			}
			if(falg){
				
				String id = BaseService.getInstance().getTblVisceraWeightService().saveOrUpdateOne(tblVisceraWeight);
				//7.更新称重结果表
				updateVisceraWeightTable(id);
				
				System.out.println(isHave);

				if(isHave==0){
					if(isFixedCheckbox.isSelected()){
						System.out.println(isHave);
						List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService()
								.getVisceraMapListByTaskIdSessionIdAnimalCode(taskId, sessionId, animalCode);
						List<String> needFixedVisceraCode=new ArrayList<String>();
						
						if(null!=mapList && mapList.size()>0){
							for(Map<String,Object> map1 :mapList){
								String visceraCode1 = (String) map1.get("visceraCode");
								needFixedVisceraCode.add(visceraCode1);
							}
						}
						if(needFixedVisceraCode.contains(visceraCode)){
							TblVisceraFixed tblv=BaseService.getInstance().getTblVisceraFixedService().getByVisceraName(studyNo,animalCode,visceraName);
							if(null==tblv){
								TblVisceraFixed tblVisceraFixed=new TblVisceraFixed();
								String idFixed=BaseService.getInstance().getTblVisceraFixedService().getKey();
								idFixed_W=idFixed;
								tblVisceraFixed.setId(idFixed);
								tblVisceraFixed.setSessionId(sessionId);
								tblVisceraFixed.setStudyNo(studyNo);
								tblVisceraFixed.setAnimalCode(animalCode);
								tblVisceraFixed.setVisceraType(visceraType);
								tblVisceraFixed.setVisceraCode(visceraCode);
								tblVisceraFixed.setVisceraName(visceraName);
								tblVisceraFixed.setOperator(operator);
								tblVisceraFixed.setOperateTime(currentDate);
								tblVisceraFixed.setFixedType(0);
								BaseService.getInstance().getTblVisceraFixedService().save(tblVisceraFixed);
//								updateData_visceraFixedTable(sessionId, animalCode,true);
//								updateData_visceraListView(taskId, sessionId, animalCode);
								isget =  false;
								showmsgLabel("脏器'"+visceraName+"'已称重，请及时固定。");
								isget =  true;
							}
						}
					}
				}else{
					if(isFixedCheckbox.isSelected()){
						if(null!=idFixed_W){
							TblVisceraFixed tblVisceraFixed=BaseService.getInstance().getTblVisceraFixedService().getById(idFixed_W);
							if(null!=tblVisceraFixed){
								tblVisceraFixed.setOperateTime(currentDate);
								tblVisceraFixed.setOperator(userName_Fixed);
								BaseService.getInstance().getTblVisceraFixedService().update(tblVisceraFixed);
//								updateData_visceraFixedTable(sessionId, animalCode,true);
//								updateData_visceraListView(taskId, sessionId, animalCode);
							}
						}else{
							List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService()
									.getVisceraMapListByTaskIdSessionIdAnimalCode(taskId, sessionId, animalCode);
							List<String> needFixedVisceraCode=new ArrayList<String>();
							
							if(null!=mapList && mapList.size()>0){
								for(Map<String,Object> map1 :mapList){
									String visceraCode1 = (String) map1.get("visceraCode");
									needFixedVisceraCode.add(visceraCode1);
								}
							}
							if(needFixedVisceraCode.contains(visceraCode)){
								TblVisceraFixed tblv=BaseService.getInstance().getTblVisceraFixedService().getByVisceraName(studyNo,animalCode,visceraName);
								if(null==tblv){
									TblVisceraFixed tblVisceraFixed=new TblVisceraFixed();
									String idFixed=BaseService.getInstance().getTblVisceraFixedService().getKey();
									idFixed_W=idFixed;
									tblVisceraFixed.setId(idFixed);
									tblVisceraFixed.setSessionId(sessionId);
									tblVisceraFixed.setStudyNo(studyNo);
									tblVisceraFixed.setAnimalCode(animalCode);
									tblVisceraFixed.setVisceraType(visceraType);
									tblVisceraFixed.setVisceraCode(visceraCode);
									tblVisceraFixed.setVisceraName(visceraName);
									tblVisceraFixed.setOperator(operator);
									tblVisceraFixed.setOperateTime(currentDate);
									tblVisceraFixed.setFixedType(0);
									BaseService.getInstance().getTblVisceraFixedService().save(tblVisceraFixed);
//									updateData_visceraFixedTable(sessionId, animalCode,true);
//									updateData_visceraListView(taskId, sessionId, animalCode);
									isget =  false;
									showmsgLabel("脏器'"+visceraName+"'已称重，请立刻固定。");
									isget =  true;
								}
								
							}
							
						}
					}else{
						if(null!=idFixed_W){
							BaseService.getInstance().getTblVisceraFixedService().delete(idFixed_W);
//							updateData_visceraFixedTable(sessionId, animalCode,true);
//							updateData_visceraListView(taskId, sessionId, animalCode);
						}
					}
				}
				
			}
			//8.更新已称脏器重量平均值及同组动物重量列表
				updateFOtherAnimalWeightTable();
			//9.显示已称重脏器及脏器重量(不选下一个，在5206行和5198行已经设定)
			showVisceraNameAndData(treeSelectedItem.getValue().getText(),weight,weightUnit);
			//10.判断是否是最后一个
			 ObservableList<TreeItem<Label>> children = visceraTree_weight.getRoot().getChildren();
			 if(null != children && children.size() > 0){
				 TreeItem<Label> treeItem = children.get(children.size()-1);
				 if(treeItem.isLeaf()){
					if( treeItem.getValue().getText().equals( visceraTree_weight.getSelectionModel().getSelectedItem().getValue().getText())){
						isFinishWeightFinishBtn();
					}else{
						visceraTree_weight.getSelectionModel().selectNext();
					}
				 }else{
					 ObservableList<TreeItem<Label>> children2 = treeItem.getChildren();
					 TreeItem<Label> treeItem2 = children2.get(children2.size() - 1);
					if(  treeItem2.getValue().getText().equals( visceraTree_weight.getSelectionModel().getSelectedItem().getValue().getText())){
						isFinishWeightFinishBtn();
					}else{
						visceraTree_weight.getSelectionModel().selectNext();
					}
				 }
			 }
		}
		
		
	}
	
	//判断是称重完毕并签字
	private void isFinishWeightFinishBtn() {
		isget =  false;
//		String animalCode =  animalCodeLabel.getText();
//		if(Confirm.create(Main.mainWidow,"提示","动物"+animalCode+"是否进行称重完成确认","是否确认继续吗？")){
			onWeightFinishBtn(null);
//		}
		
	}
	
	/**显示已称重脏器及脏器重量
	 * @param weight
	 * @param weightUnit
	 */
	private void showVisceraNameAndData(String visceraName,String weight, String weightUnit) {
		preViscerNameLabel.setText(visceraName);
		weightLabel.setText(weight+" "+weightUnit);
	}

	/**更新表格数据并选中指定项
	 * @param id
	 */
	private void updateVisceraWeightTable(String id) {
		data_visceraWeightTable.clear();
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null != animal){
			String studyNo = animal.getStudyNo();
			String animalCode = animal.getAnimalCode();
			boolean operate = false;//是否  可以删除
			if(sessionType == 8 && !"1".equals(animal.getVisceraFixedWeighFinishFlag())){
				//固定后称重完成
				operate = true;
			}else if(sessionType != 8 && !"1".equals(animal.getVisceraWeighFinishFlag())){
				//称重完成
				operate = true;
			}
			List<TblVisceraWeight> tblVisceraWeightList = BaseService.getInstance().getTblVisceraWeightService().getList(studyNo,animalCode,sessionType == 8);
			if(null != tblVisceraWeightList && tblVisceraWeightList.size() > 0){
				for(TblVisceraWeight obj:tblVisceraWeightList){
					String visceraName = obj.getVisceraName();
					String subVisceraName = obj.getSubVisceraName();
					int attachedVisceraFlag = obj.getAttachedVisceraFlag();
					String attachedVisceraNames = obj.getAttachedVisceraNames();
					if(attachedVisceraFlag == 1){
						visceraName = visceraName+"("+attachedVisceraNames+")";
//						if(null != subVisceraName && !"".equals(subVisceraName)){
//							subVisceraName = subVisceraName+"("+attachedVisceraNames+")";
//						}
					}
					data_visceraWeightTable.add(new VisceraWeight(obj.getId(),visceraName,
							subVisceraName,obj.getWeight(),obj.getWeightUnit(),operate));
				}
			}
			
			if(null != id && !"".equals(id) && data_visceraWeightTable.size() > 0){
				for(VisceraWeight obj :data_visceraWeightTable){
					if(id.equals(obj.getId())){
						visceraWeightTable.getSelectionModel().select(obj);
					}
				}
			}
		}
		
	}

	/**
	 * 更新      同组动物该脏器的重量及表格数据
	 */
	private void updateOtherAnimalWeightTable(String studyNo,String animalCode,
			String visceraCode,String subVisceraCode){
		data_otherAnimalWeightTable.clear();
		otherWeightLabel.setText("");
		AnatomyProcessPage.otherWeight = 0;
		if(null != studyNo && null != animalCode && null != visceraCode ){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraWeightService()
					.getOtherAnimalWeightMapList(studyNo,animalCode,visceraCode,subVisceraCode);
			if(null != mapList && mapList.size() > 0){
				int i = 0;//数量
				float f = 0;
				String weightUnit = "";
				for(Map<String,Object> map : mapList){
					String animalCode2 = (String) map.get("animalCode");
					String weight = (String) map.get("weight");
					weightUnit = (String) map.get("weightUnit");
					data_otherAnimalWeightTable.add(new OtherAnimalWeight(animalCode2,weight,weightUnit));
					i++;
					f = f+Float.valueOf(weight);
				}
				if(i>0){
					f = f/i;
					DecimalFormat decimalFormat=new DecimalFormat("0.000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
					decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
					String p=decimalFormat.format(f);//format 返回的是字符串
//					if(p.length() > 4){
//						 p = p.substring(0, 4);
//					}
					otherWeightLabel.setText("平均  "+p+" "+weightUnit);
					AnatomyProcessPage.otherWeight = f;
				}
					
			}
		}
	}
	
	/**
	 * 更新同组动物该脏器的重量及表格数据
	 */
	private void updateFOtherAnimalWeightTable(){
		data_ftherAnimalWeightTable.clear();
		fOtherWeightLabel.setText(otherWeightLabel.getText());
		for(OtherAnimalWeight otherAnimalWeight:data_otherAnimalWeightTable){
			OtherAnimalWeight otherAnimalWeight2 = new OtherAnimalWeight();
			otherAnimalWeight2.setAnimalCode(otherAnimalWeight.getAnimalCode());
			otherAnimalWeight2.setWeight(otherAnimalWeight.getWeight());
			data_ftherAnimalWeightTable.add(otherAnimalWeight2);
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AnatomyProcess.fxml"));
		Scene scene = new Scene(root, 1100, 650);
		stage.setScene(scene);
		stage.setTitle("解剖过程");
		 stage.setResizable(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// event.consume();
				closePort();
				closeTimer();
			}
		});
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setMinWidth(1124);
		stage.setMinHeight(700);
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight());  
		stage.show();

	}

	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
	
	void showmsgLabel(String msg){
		msgLabel.setText(msg);
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(msgLabel.opacityProperty(), 1), new KeyValue(msgLabel.translateZProperty(), 10)),
                new KeyFrame(Duration.seconds(3.0), new KeyValue(msgLabel.opacityProperty(), 0), new KeyValue(msgLabel.translateZProperty(), 0)));
        tl.play();
	}
	
	/**显示固定未完成信息
	 * @param msg
	 */
	void showFixedMsgLabel(String msg){
		msgLabel_fixed.setText(msg);
        Timeline tl = new Timeline(
        		new KeyFrame(Duration.seconds(0.3), new KeyValue(msgLabel_fixed.opacityProperty(), 1), new KeyValue(msgLabel_fixed.translateZProperty(), 10)),
        		new KeyFrame(Duration.seconds(57.0), new KeyValue(msgLabel_fixed.opacityProperty(), 1), new KeyValue(msgLabel_fixed.translateZProperty(), 10)),
        		new KeyFrame(Duration.seconds(59.0), new KeyValue(msgLabel_fixed.opacityProperty(), 0), new KeyValue(msgLabel_fixed.translateZProperty(), 0))
        		);
        tl.play();
	}
	/**
	 * 病理检查会话
	 * @author Administrator
	 *
	 */
	public static class Animal{
		
		private StringProperty animalCode;			//动物编号
		private StringProperty state;             	//状态
		private StringProperty sessionCreator;      //操作者
		private StringProperty studyNo;            	//专题编号
		private StringProperty taskId;				//任务id
		
		private StringProperty anatomyCheckFinishFlag;//解剖完成标识0,1
		private StringProperty autolyzeFlag;		  	//自溶标识  0,1
		
		private StringProperty visceraWeighFinishFlag;	//称重完成标识
		private StringProperty visceraFixedFinishFlag;	//固定完成标识
		private StringProperty visceraFixedWeighFinishFlag;	//固定后称重完成标识
		
		private StringProperty anatomyOperator;			//解剖者
		private StringProperty anatomyBeginTime;		//解剖开始时间
		
		public Animal() {
			super();
		}
		
		public Animal(String animalCode, String state,
				String sessionCreator, String studyNo,String taskId,
				Integer autolyzeFlag,Integer anatomyCheckFinishFlag,
				Integer visceraWeightFinishFlag,Integer visceraFixedFinishFlag,
				Integer visceraFixedWeighFinishFlag,String anatomyOperator,
				Date anatomyBeginTime
				) {
			this.animalCode = new SimpleStringProperty(animalCode);
			this.state = new SimpleStringProperty(state);
			this.sessionCreator = new SimpleStringProperty(sessionCreator);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.taskId = new SimpleStringProperty(taskId);
			setAutolyzeFlag(autolyzeFlag+"");
			setAnatomyCheckFinishFlag(anatomyCheckFinishFlag+"");
			setVisceraWeighFinishFlag(visceraWeightFinishFlag+"");
			setVisceraFixedFinishFlag(visceraFixedFinishFlag+"");
			setVisceraFixedWeighFinishFlag(visceraFixedWeighFinishFlag+"");
			setAnatomyOperator(anatomyOperator);
			setAnatomyBeginTime(DateUtil.dateToString(anatomyBeginTime, "yyyy-MM-dd HH:mm"));
		}

		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}
		public String getSessionCreator() {
			return sessionCreator.get();
		}
		public void setSessionCreator(String sessionCreator) {
			this.sessionCreator = new SimpleStringProperty(sessionCreator);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}

		public String getTaskId() {
			return taskId.get();
		}

		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}

		public String getAnatomyCheckFinishFlag() {
			return anatomyCheckFinishFlag.get();
		}

		public void setAnatomyCheckFinishFlag(String anatomyCheckFinishFlag) {
			this.anatomyCheckFinishFlag =  new SimpleStringProperty(anatomyCheckFinishFlag);
		}

		public String getAutolyzeFlag() {
			return autolyzeFlag.get();
		}

		public void setAutolyzeFlag(String autolyzeFlag) {
			this.autolyzeFlag = new SimpleStringProperty(autolyzeFlag);
		}

		public String getVisceraWeighFinishFlag() {
			return visceraWeighFinishFlag.get();
		}

		public void setVisceraWeighFinishFlag(String visceraWeighFinishFlag) {
			this.visceraWeighFinishFlag = new SimpleStringProperty(visceraWeighFinishFlag);
		}

		public String getVisceraFixedFinishFlag() {
			return visceraFixedFinishFlag.get();
		}

		public void setVisceraFixedFinishFlag(String visceraFixedFinishFlag) {
			this.visceraFixedFinishFlag = new SimpleStringProperty(visceraFixedFinishFlag);
		}

		public String getVisceraFixedWeighFinishFlag() {
			return visceraFixedWeighFinishFlag.get();
		}

		public void setVisceraFixedWeighFinishFlag(String visceraFixedWeighFinishFlag) {
			this.visceraFixedWeighFinishFlag = new SimpleStringProperty(visceraFixedWeighFinishFlag);
		}

		public String getAnatomyOperator() {
			return anatomyOperator.get();
		}

		public void setAnatomyOperator(String anatomyOperator) {
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
		}

		public String getAnatomyBeginTime() {
			return anatomyBeginTime.get();
		}

		public void setAnatomyBeginTime(String anatomyBeginTime) {
			this.anatomyBeginTime = new SimpleStringProperty(anatomyBeginTime);
		}

		
		
	}
	
	/**剖检记录
	 * @author Administrator
	 *
	 */
	public static class AnatomyCheck{
		private StringProperty id;			//id
		private StringProperty sessionId;	//会话id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty finding;		//解剖所见
		private BooleanProperty  operate;	//操作
		
		public AnatomyCheck() {
			super();
		}
		public AnatomyCheck(String id, String sessionId, String visceraName, String finding,
				boolean operate) {
			this.id = new SimpleStringProperty(id);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.finding = new SimpleStringProperty(finding == null ? "":finding);
			this.operate = new SimpleBooleanProperty(operate);
		}


		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getSessionId() {
			return sessionId.get();
		}
		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getFinding() {
			return finding.get();
		}
		public void setFinding(String finding) {
			this.finding = new SimpleStringProperty(finding);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
		
	}
	/**固定记录记录
	 * @author Administrator
	 *
	 */
	public static class VisceraFixed{
		private StringProperty id;			//id
		private StringProperty sessionId;	//会话id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private BooleanProperty  operate;	//操作
		
		public VisceraFixed() {
			super();
		}
		public VisceraFixed(String id, String sessionId, String visceraName,boolean operate) {
			this.id = new SimpleStringProperty(id);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.operate = new SimpleBooleanProperty(operate);
		}
		
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getSessionId() {
			return sessionId.get();
		}
		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
		
	}
	/**
	 * 脏器称重记录
	 */
	public static class VisceraWeight{
		private StringProperty id;			//id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty weight;		//重量
		private BooleanProperty  operate;	//操作
		public VisceraWeight(){}
		public VisceraWeight(String id,String visceraName,String subVisceraName,
				String weight,String weightUnit,boolean operate){
			setId(id);
			if(null != subVisceraName && !"".equals(subVisceraName) ){
				setVisceraName(subVisceraName);
			}else{
				setVisceraName(visceraName);
			}
			setWeight(weight+" "+weightUnit);
			setOperate(operate);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getWeight() {
			return weight.get();
		}
		public void setWeight(String weight) {
			this.weight = new SimpleStringProperty(weight);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
	}
	
	/**同组其他动物该脏器重量
	 * @author Administrator
	 *
	 */
	public static class OtherAnimalWeight{
		private StringProperty animalCode;	//动物编号
		private StringProperty weight;		//重量
		
		
		public OtherAnimalWeight() {
		}
		public OtherAnimalWeight(String animalCode, String weight,String weightUnit) {
			setAnimalCode(animalCode);
			setWeight(weight+" "+weightUnit);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getWeight() {
			return weight.get();
		}
		public void setWeight(String weight) {
			this.weight = new SimpleStringProperty(weight);
		}
		
	}
	
	/**剖检信息专用
	 * @author Administrator
	 *
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
	    private ObservableValue<T> ov;  
	    private Map<String,String> vmap;  
	      
	    public Map<String, String> getVmap() {  
	        return vmap;  
	    }  
	  
	    public void setVmap(Map<String, String> vmap) {  
	        this.vmap = vmap;  
	    }  
	  
	    public HyperlinkCell() {  
	        this.hyperlink = new Hyperlink();  
	        this.hyperlink.setUnderline(true);  
	        setAlignment(Pos.CENTER);  
	        setGraphic(hyperlink);  
	    }  
	  
	    @Override  
	    protected void updateItem(T item, boolean empty) {  
	        super.updateItem(item, empty);  
	        if (empty) {  
	            setText(null);  
	            setGraphic(null);  
	        } else {  
	            setGraphic(hyperlink);  
	            ov = getTableColumn().getCellObservableValue(getIndex());  
	            if (ov instanceof SimpleBooleanProperty) {  
	            	SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
	            	if(!booleanValue.get()){
	            		hyperlink.setDisable(true);
	            	}else
	            	{
	            		hyperlink.setDisable(false);
	            	}
	            } 
//	            System.out.println(this.getTableRow().getItem().getClass().getSimpleName());
				AnatomyCheck ck = (AnatomyCheck) getTableRow().getItem();
				if(ck.getFinding().equals("自溶")){
					hyperlink.setDisable(true);
				}
	            hyperlink.setText("删除");
	            hyperlink.setPrefWidth(50);
	            hyperlink.setPrefHeight(20);
	            hyperlink.setUserData(this.getTableRow().getItem());
	            final TableView<?> tableView = this.getTableView();
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						AnatomyCheck anatomyCheck = (AnatomyCheck) hyperlink.getUserData();
						Json json = BaseService.getInstance().getTblAnatomyCheckService().deleteOne(anatomyCheck.getId());
						if(json.isSuccess()){
							tableView.getItems().remove(anatomyCheck);
						}else{
							showErrorMessage(json.getMsg());
						}
					}
				});
	        }  
	    }  
	}  
	/**固定信息专用
	 * @author Administrator
	 *
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell_Fixed<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
		private ObservableValue<T> ov;  
		private Map<String,String> vmap;  
		
		public Map<String, String> getVmap() {  
			return vmap;  
		}  
		
		public void setVmap(Map<String, String> vmap) {  
			this.vmap = vmap;  
		}  
		
		public HyperlinkCell_Fixed() {  
			this.hyperlink = new Hyperlink();  
			this.hyperlink.setUnderline(true);  
			setAlignment(Pos.CENTER);  
			setGraphic(hyperlink);  
		}  
		
		@Override  
		protected void updateItem(T item, boolean empty) {  
			super.updateItem(item, empty);  
			if (empty) {  
				setText(null);  
				setGraphic(null);  
			} else {  
				setGraphic(hyperlink);  
				ov = getTableColumn().getCellObservableValue(getIndex());  
				if (ov instanceof SimpleBooleanProperty) {  
					SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
					if(!booleanValue.get()){
						hyperlink.setDisable(true);
					}else
	            	{
	            		hyperlink.setDisable(false);
	            	}
				} 
				//	            System.out.println(this.getTableRow().getItem().getClass().getSimpleName());
				hyperlink.setText("删除");
				hyperlink.setPrefWidth(50);
				hyperlink.setPrefHeight(20);
				hyperlink.setUserData(this.getTableRow().getItem());
				final TableView<?> tableView = this.getTableView();
				hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){
					
					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						VisceraFixed visceraFixed = (VisceraFixed) hyperlink.getUserData();
						Json json = BaseService.getInstance().getTblVisceraFixedService().deleteOne(visceraFixed.getId());
						if(json.isSuccess()){
							tableView.getItems().remove(visceraFixed);
							Animal animal = animalTable.getSelectionModel().getSelectedItem();
							String taskId=animal.getTaskId();
							String sessionId=taskIdSessionIdMap.get(taskId);
//							String studyNo=animal.getStudyNo();
							String animalCode=animal.getAnimalCode();
							updateData_visceraListView(taskId,sessionId,animalCode);
						}else{
							showErrorMessage(json.getMsg());
						}
					}
				});
			}  
		}  
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			if(!isOpening){
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * 天平
	 */
	public static class BalanceRrge{
		private StringProperty balCode;
		private StringProperty balState;
		private StringProperty baudCombo;
		
		public BalanceRrge(String balCode,String balState,String baudCombo){
			this.balCode =  new  SimpleStringProperty(balCode);
			this.balState = new  SimpleStringProperty(balState);
			this.baudCombo = new SimpleStringProperty(baudCombo);
		}

		public String getBalCode() {
			return balCode.get();
		}

		public void setBalCode(String balCode) {
			this.balCode = new SimpleStringProperty(balCode);
		}

		public String getBalState() {
			return balState.get();
		}

		public void setBalState(String balState) {
			this.balState = new SimpleStringProperty(balState);
		}

		public String getBaudCombo() {
			return baudCombo.get();
		}

		public void setBaudCombo(String baudCombo) {
			this.baudCombo = new SimpleStringProperty(baudCombo);
		}
		
	   
		
	}
	
	
	public static class AbnViscera{
		private StringProperty id;
		private StringProperty visceraName; //脏器名称
		private StringProperty abnViscera;	//异常组织和脏器解剖所见
		private BooleanProperty  operate;	//操作
		
		public AbnViscera(String id,String visceraName,String abnViscera,boolean operate){
			this.id = new SimpleStringProperty(id);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.abnViscera = new SimpleStringProperty(abnViscera);
			this.operate = new SimpleBooleanProperty(operate);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getAbnViscera() {
			return abnViscera.get();
		}
		public void setAbnViscera(String abnViscera) {
			this.abnViscera = new SimpleStringProperty(abnViscera);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
	}
	
 
	/**固定信息专用
	 * @author Administrator
	 *
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell_AbnFixed<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
		private ObservableValue<T> ov;  
		private Map<String,String> vmap;  
		
		public Map<String, String> getVmap() {  
			return vmap;  
		}  
		
		public void setVmap(Map<String, String> vmap) {  
			this.vmap = vmap;  
		}  
		
		public HyperlinkCell_AbnFixed() {  
			this.hyperlink = new Hyperlink();  
			this.hyperlink.setUnderline(true);  
			setAlignment(Pos.CENTER);  
			setGraphic(hyperlink);  
		}  
		
		@Override  
		protected void updateItem(T item, boolean empty) {  
			super.updateItem(item, empty);  
			if (empty) {  
				setText(null);  
				setGraphic(null);  
			} else {  
				setGraphic(hyperlink);  
				ov = getTableColumn().getCellObservableValue(getIndex());  
				if (ov instanceof SimpleBooleanProperty) {  
					SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
					if(!booleanValue.get()){
						hyperlink.setDisable(true);
					}else
	            	{
	            		hyperlink.setDisable(false);
	            	}
				} 
				//	            System.out.println(this.getTableRow().getItem().getClass().getSimpleName());
				hyperlink.setText("删除");
				hyperlink.setPrefWidth(50);
				hyperlink.setPrefHeight(20);
				hyperlink.setUserData(this.getTableRow().getItem());
				final TableView<?> tableView = this.getTableView();
				hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){
					
					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						AbnViscera abnViscera = (AbnViscera) hyperlink.getUserData();
						Json json = BaseService.getInstance().getTblVisceraFixedService().deleteOne(abnViscera.getId());
						if(json.isSuccess()){
							tableView.getItems().remove(abnViscera);
							Animal animal = animalTable.getSelectionModel().getSelectedItem();
//							String taskId=animal.getTaskId();
//							String sessionId=taskIdSessionIdMap.get(taskId);
							String studyNo=animal.getStudyNo();
							String animalCode=animal.getAnimalCode();
							updateAbnVisceraTable(studyNo,animalCode);
//							CheckBox c=new CheckBox(abnViscera.getAbnViscera());
//							data_abnVisceraList.add(c);
							abnFixedButton.setDisable(false);
						}else{
							showErrorMessage(json.getMsg());
						}
					}
				});
			}  
		}  
	}
	
	

	/**称重记录专用
	 * @author Administrator
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell_weight<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
	    private ObservableValue<T> ov;  
	    private Map<String,String> vmap;  
	      
	    public Map<String, String> getVmap() {  
	        return vmap;  
	    }  
	  
	    public void setVmap(Map<String, String> vmap) {  
	        this.vmap = vmap;  
	    }  
	  
	    public HyperlinkCell_weight() {  
	        this.hyperlink = new Hyperlink();  
	        this.hyperlink.setUnderline(true);  
	        setAlignment(Pos.CENTER);  
	        setGraphic(hyperlink);  
	    }  
	  
	    @Override  
	    protected void updateItem(T item, boolean empty) {  
	        super.updateItem(item, empty);  
	        if (empty) {  
	            setText(null);  
	            setGraphic(null);  
	        } else {  
	            setGraphic(hyperlink);  
	            ov = getTableColumn().getCellObservableValue(getIndex());  
	            if (ov instanceof SimpleBooleanProperty) {  
	            	SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
	            	if(!booleanValue.get()){
	            		hyperlink.setDisable(true);
	            	}else
	            	{
	            		hyperlink.setDisable(false);
	            	}
	            } 
	            hyperlink.setText("删除");
	            hyperlink.setPrefWidth(50);
	            hyperlink.setPrefHeight(20);
	            hyperlink.setUserData(this.getTableRow().getItem());
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						VisceraWeight visceraWeight = (VisceraWeight) hyperlink.getUserData();
						isget = false;
						if(Sign.openSignWithReasonFrame("删除原因","称重数据删除")){
							String reason = Sign.getReason();
							String operator = SaveUserInfo.getUserName();
							Json json = BaseService.getInstance().getTblVisceraWeightService().deleteOne(visceraWeight.getId(),reason,operator);
							if(json.isSuccess()){
								//更新称重结果表
								updateVisceraWeightTable("");
							}else{
								showErrorMessage(json.getMsg());
							}
							
						}
						isget = true;
					}
				});
	        }  
	    }  
	}  
}
