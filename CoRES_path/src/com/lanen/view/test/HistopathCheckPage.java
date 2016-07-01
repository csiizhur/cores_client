package com.lanen.view.test;

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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.DictLevel;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblHistopathCheck;
import com.lanen.model.path.TblPathStudyIndex;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.path.TblTissueSliceViscera;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

import datecontrol.DatePicker;
import datecontrol.Junit;

/**镜检
 * @author Administrator
 *
 */
public class HistopathCheckPage extends Application implements Initializable {
	
	@FXML
	private Label studyLabel;
	@FXML
	private Label currentUserNameLabel;
	
	@FXML
	private TabPane tabPane;	
	@FXML
	private TabPane tabPane2;	//镜检组合，手工录入
	
	@FXML
	private ComboBox<String> stateComboBox;				//状态ComboBox
	private ObservableList<String> data_stateComboBox = FXCollections.observableArrayList();
	private int state = 0; //0:全部  1:未开始检查 2：已开始检查
	/**
	 * 切片编号索引表List
	 */
	List<TblTissueSliceIndex> tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
	/**
	 * 当前切片编号索引
	 */
	TblTissueSliceIndex tblTissueSliceIndex = null;
	
	@FXML
	private ComboBox<String> contentComboBox;				//显示内容ComboBox
	private ObservableList<String> data_contentComboBox = FXCollections.observableArrayList();
	/**
	 * 显示内容方式
	 */
	private static int contentIndex = 1;
	@FXML
	private ComboBox<String> sortMethodComboBox;			//排序方式ComboBox
	private ObservableList<String> data_sortMethodComboBox = FXCollections.observableArrayList();
	
	/**
	 * 排序方式 sortMethod :1,动物  2，脏器
	 */
	private static int sortMethod = 1;	//排序方式 sortMethod :1,动物  2，脏器
	
	@FXML
	private RadioButton animalRadioButton;					//动物优先RadioButton
	@FXML
	private RadioButton sliceRadioButton;					//切片优先RadioButton
	/**
	 * 检查顺序 checkOrder :1,动物优先  2，切片/脏器优先
	 */
//	private static int checkOrder = 1;	//检查顺序 checkOrder :1,动物优先  2，切片/脏器优先
	
	
	@FXML
	private TableColumn<HistopathAnimal,String> animalCodeCol;
	@FXML
	private TableColumn<HistopathAnimal,String> genderCol;
	@FXML
	private TableColumn<HistopathAnimal,String> stateCol;
	@FXML
	private TableColumn<HistopathAnimal,String> hasAbnormalCol;
	
	
	/**
	 * 镜检组合选择的动物列表
	 */
	private List<String> animalCodeList;
	/**
	 * 镜检组合选择的切片序号Id列表
	 */
	private List<String> tissueSliceSnIdList;
	
	/**
	 * 切片优先时，根据该表，实现一个脏器或一个动物，存放  所有动物切片脏器（去掉了性别、排除）
	 */
	private List<Map<String,Object>> sliceCodeAnimalCodeSliceVisceraMapList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 镜检动物表
	 */
	@FXML
	private TableView<HistopathAnimal> histopathAnimalTable;
	private ObservableList<HistopathAnimal> data_histopathAnimalTable = FXCollections.observableArrayList();
	private ObservableList<HistopathAnimal> data_histopathAnimalTable2 = FXCollections.observableArrayList();
 	
	@FXML
	private TreeView<String> tissueSliceTree; // 组织切片编号tree
	private TreeItem<String> rootNode_tissueSliceTree = new TreeItem<String>();//tissueSliceTree 根节点 
	/**
	 * 切片编号->切片节点
	 */
	private Map<String,TreeItem<String>> sliceCode2SliceTreeItemMap = new HashMap<String,TreeItem<String>>();
	
	@FXML
	private TableColumn<HistopathCheck,String> animalCodeCol_1;
	@FXML
	private TableColumn<HistopathCheck,String> visceraOrTissueNameCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkResultCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkTimeCol;
	
	@FXML
	private TableColumn<HistopathCheck,String> tumorFlagCol_0;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
	@FXML
	private TableColumn<HistopathCheck,String> metastasisFlagCol_0;	//是否转移
	@FXML
	private TableColumn<HistopathCheck,String> primaryVisceraTumorCol_0;	//原发脏器肿瘤
	@FXML
	private TableColumn<HistopathCheck,String> levelCol;	//病变程度
	@FXML
	private TableColumn<HistopathCheck,String> tumorNumCol;	//肿瘤数量
	@FXML
	private TableColumn<HistopathCheck,String> tumorPosCol;	//肿瘤位置
	@FXML
	private TableColumn<HistopathCheck,String> tumorOccurDateCol;	//肿瘤发生日期
	@FXML
	private TableColumn<HistopathCheck,String> tumorCharacterCol;	//肿瘤特性
	@FXML
	private TableColumn<HistopathCheck,String> remarkCol;	//备注
	
	/**
	 * 组织学所见
	 */
	@FXML
	private TableView<HistopathCheck> histopathCheckTable;
	private ObservableList<HistopathCheck> data_histopathCheckTable = FXCollections.observableArrayList();
	private ObservableList<HistopathCheck> data_histopathCheckTable2 = FXCollections.observableArrayList();
	
	@FXML
	private Button noAbnormalBtn;		//未见异常
	@FXML	
	private Button registerResultBtn;	//登记结果——动物、脏器tab
	@FXML
	private Button noAbnormalBtn_handword;		//未见异常
	@FXML	
	private Button registerResultBtn_handword;	//登记结果——手工录入
	
	
	//组织学所见tab----------------------------------------------------------------------
	
	@FXML
	private Label animalCodeVisceraNameLabel;
	
	@FXML	
	private Button registerResultBtn_1;	//登记结果——动物、脏器tab
	@FXML
	private Button referToBtn;		//参照所见
	@FXML
	private Hyperlink missingRegister;		//缺失登记
	@FXML
	private Button checkFinishBtn;		//检查完成
	
	@FXML
	private ComboBox<String> searchComboBox; // 快速查找ComboBox
	@FXML
	private TextField searchTextField;//快速查找TextField
	@FXML
	private ListView<String> searchListView ; //快速查找ListView
	private ObservableList<String> data_searchListView = FXCollections.observableArrayList();
	
	/**
	 * 脏器名称ListView
	 */
	@FXML
	private ListView<String> visceraNameListView;
	ObservableList<String> data_visceraNameListView = FXCollections.observableArrayList();
	List<Map<String,Object>> visceraMapList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 组织学用语ListView
	 */
	@FXML
	private ListView<String> histoPoListView;
	private ObservableList<String> data_histoPoListView = FXCollections.observableArrayList();
	/**
	 * 中文名称-DictPathCommon
	 */
	private Map<String,DictPathCommon> descCNDictPathCommonMap_histoPoListView = new HashMap<String,DictPathCommon>();
	private Map<String,String> pyDescCNMap_histoPoListView = new HashMap<String,String>();//拼音首字母-中文名称
	/**
	 * 病变描述ListView
	 */
	@FXML
	private ListView<String> lesionFindingListView;
	private ObservableList<String> data_lesionFindingListView = FXCollections.observableArrayList();
	/**
	 * 中文名称-DictPathCommon
	 */
	private Map<String,DictPathCommon> descCNDictPathCommonMap_lesionFindingListView = new HashMap<String,DictPathCommon>();
	private Map<String,String> pyDescCNMap_lesionFindingListView = new HashMap<String,String>();//拼音首字母-中文名称
	@FXML
	private Label histopathCheckLabel;				//解剖学所见Label
	
	@FXML
	private ComboBox<String> dictSortMethodComboBox; // 字典排序方式
	/**
	 * 字典排序方式  :1,字典排序  2，字母排序 3,使用频度
	 */
	private static int dictSortMethod = 1;	//字典排序方式  :1,字典排序  2，字母排序 3,使用频度
	
	@FXML
	private HBox hbox;
	@FXML
	private Label tumorNumberLabel;			//肿瘤数量Label
	@FXML
	private ComboBox<String> tumorNumberComboBox;			//肿瘤数量ComboBox
	@FXML
	private Label levelLabel;			//程度Label
	@FXML
	private ComboBox<String> levelComboBox;			//肿瘤数量ComboBox
	private ObservableList<String> data_levelComboBox = FXCollections.observableArrayList();
	
	@FXML
	private TextField remarkText;		//备注TextField
	
	
//	@FXML
//	private TableView<AnatomyCheck> anatomyResultTable; // 解剖结果TableView
//	/**
//	 * anatomyResultTalbe 数据集
//	 */
//	private ObservableList<AnatomyCheck> data_anatomyResultTable = FXCollections.observableArrayList();
//	@FXML
//	private TableColumn<AnatomyCheck, String> visceraNameCol; // 脏器column
//
//	@FXML
//	private TableColumn<AnatomyCheck, String> findingCol; // 解剖所见column
	
	
	/**
	 * 未见异常 RadioButton
	 */
	@FXML
	private RadioButton noAbnormalRadio;
	/**
	 * 异常 RadioButton
	 */
	@FXML
	private RadioButton abnormalRadio;
	/**
	 * 非肿瘤 RadioButton
	 */
	@FXML
	private RadioButton noTumorRadio;
	/**
	 * 良性(肿瘤) RadioButton
	 */
	@FXML
	private RadioButton lxTumorRadio;
	/**
	 * 恶性（肿瘤） RadioButton
	 */
	@FXML
	private RadioButton exTumorRadio;
	/**
	 * 非转移 RadioButton
	 */
	@FXML
	private RadioButton noMetastasisRadio;
	/**
	 * 侵袭 RadioButton
	 */
	@FXML
	private RadioButton qinxiRadio;
	/**
	 * 转移 RadioButton
	 */
	@FXML
	private RadioButton metastasisRadio;
	
	/**
	 * 原发脏器  Hyperlink
	 */
	@FXML
	private Hyperlink primaryViscerHyperlink;
	
	
	@FXML
	private AnchorPane tumorPosPane;		//肿瘤位置AnchorPane
	@FXML
	private AnchorPane tumorCharacterPane;	//肿瘤特性AnchorPane		
	@FXML
	private AnchorPane tumorOccurPane;		//肿瘤发生日期	AnchorPane
	
	@FXML
	private RadioButton tumorPosRadio1;			//肿瘤位置  1：浅表
	@FXML
	private RadioButton tumorPosRadio2;			//肿瘤位置  2：深部
	@FXML
	private RadioButton tumorCharacterRadio1;			//肿瘤特性  1：偶发
	@FXML
	private RadioButton tumorCharacterRadio2;			//肿瘤特性  2：致死
	@FXML
	private RadioButton tumorCharacterRadio3;			//肿瘤特性  3：不确定
	
	@FXML
	private HBox tumorOccurDateHbox;			//肿瘤发生日期Hbox
	
	DatePicker  occurDatePicker = null;
	
	/**
	 * 组织学所见TableView(tab2上的)
	 */
	@FXML	
	private TableView<HistopathResult> histopathResultTable;
	private ObservableList<HistopathResult> data_histopathResultTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<HistopathResult,String> visceraOrTissueNameCol_1;
	@FXML
	private TableColumn<HistopathResult,String> tumorFlagCol;
	@FXML
	private TableColumn<HistopathResult,String> metastasisFlagCol;
	@FXML
	private TableColumn<HistopathResult,String> checkResultCol_1;
	@FXML
	private TableColumn<HistopathResult,String> primaryVisceraTumorCol;
	@FXML
	private TableColumn<HistopathResult,Boolean> operateCol;
	
	@FXML
	private TableColumn<HistopathResult,String> levelCol_1;	//病变程度
	@FXML
	private TableColumn<HistopathResult,String> tumorNumCol_1;	//肿瘤数量
	@FXML
	private TableColumn<HistopathResult,String> tumorPosCol_1;	//肿瘤位置
	@FXML
	private TableColumn<HistopathResult,String> tumorOccurDateCol_1;	//肿瘤发生日期
	@FXML
	private TableColumn<HistopathResult,String> tumorCharacterCol_1;	//肿瘤特性
	@FXML
	private TableColumn<HistopathResult,String> remarkCol_1;	//备注
	
	
	
	 
	//死亡动物致死原因，靶器官
	@FXML
	private Label deathReasonLabel;	//动物致死原因Label
	//死亡动物致死原因
	@FXML
	private Label deathReasonLabel_1;	//动物致死原因Label
	
	@FXML 
	private Button deathReasonBtn;	//致死原因
	@FXML 
	private Button targetOrganBtn;	//靶器官登记
	
	private String studyNo = "";
	private String anatomyRsn = "";
	private String animalStrain = "";
	/**
	 * 记录当前选中的脏器编号（在切换到组织学所见Tab时更新）
	 */
	private String visceraCode = "";
	/**
	 * 记录当前选中的脏器编号（在切换到组织学所见Tab时更新）
	 */
	private String sliceVisceraId = "";
	
	private boolean hasEdit = false;//有编辑
	
	
	/**
	 * 登记检查结果时清空，metastasisRadio not Selected 是清空
	 */
	private String primaryVisceraCode = null;	//源发脏器编号
	private String primaryVisceraName = null;	//源发脏器名称
	private String primaryTumor = null;			//源发肿瘤
	
	
	/************************手工录入 start****************************/
	@FXML
	private ListView<String> anatomyPosListView; // 解剖学所见部位ListView
	private ObservableList<String> data_anatomyPosListView = FXCollections.observableArrayList();

	@FXML
	private ListView<String> anatomyFindingListView; // 解剖所见ListView
	private ObservableList<String> data_anatomyFindingListView_tongyong = FXCollections.observableArrayList();
	//存放解剖所通用所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tongyongDictPathCommonMap = new HashMap<String,DictPathCommon>();
	private ObservableList<String> data_anatomyFindingListView_tesu = FXCollections.observableArrayList();
	//存放解剖所特殊所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tesuDictPathCommonMap = new HashMap<String,DictPathCommon>();
	
	@FXML
	private ListView<String> bodySufacePosListView; // 体表部位ListView
	private ObservableList<String> data_bodySufacePosListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> animalCodeListView; // 动物编号
	private ObservableList<String> data_animalCodeListView = FXCollections.observableArrayList();
	private Map<String,Integer> animalCode2GenderMap =new  HashMap<String,Integer>();
	
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
	private RadioButton tongyongRadioButton; // 普通所见RadioButton
	
	@FXML
	private RadioButton tesuRadioButton; // 特殊所见RadioButton
	
	//存放解剖学所见部位：毒性病理字典
	private Map<String,DictPathCommon> anatomyPosDictPathCommonMap = new HashMap<String,DictPathCommon>();
	/************************手工录入 end *********************************/
	
	
	
	/**致死原因
	 * @param event
	 */
	@FXML
	private void onDeathReasonButtonAction(ActionEvent event){
		
		//动物编号
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		
		//1.判断是否有致死原因记录，
		//2.如否：判断是否是计划解剖
		//3.是计划解剖，提示“是否继续”，继续的话执行4，否则跳过
		//4.打开登记致死原因窗口		
		//5.如有致死原因记录，直接打开致死原因列表窗口
		
		boolean isHasRecord = BaseService.getInstance().getTblDeadAnimalDeathReasonService().isHasRecord(studyNo,animalCode);
		if(isHasRecord){
			//打开致死原因列表窗口
			Stage stage = Main.stageMap.get("DeathReasonPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					DeathReasonPage.getInstance().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("DeathReasonPage",stage);
			}else{
				stage.show();
			}
			DeathReasonPage.getInstance().loadData(studyNo,animalCode,anatomyRsn,animalStrain);
		}else{
			if("计划解剖".equals(anatomyRsn)){
				if(!Messager.showSimpleConfirm("", "当前动物是计划解剖的，是否继续？")){
					return;
				}
			}
			//打开致死原因登记窗口
			Stage stage = Main.stageMap.get("DeathReasonRegister");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					DeathReasonRegister.getInstance().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("DeathReasonRegister",stage);
			}else{
				stage.show();
			}
			DeathReasonRegister.getInstance().loadData(studyNo,animalCode,anatomyRsn,animalStrain);
		}
		
	}
	/**靶器官登记
	 * @param event
	 */
	@FXML
	private void onTargetOrganButtonAction(ActionEvent event){
		//打开致死原因登记窗口
		Stage stage = Main.stageMap.get("TargetOrganPage");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TargetOrganPage.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TargetOrganPage",stage);
		}else{
			stage.show();
		}
		TargetOrganPage.getInstance().loadData(studyNo,animalStrain);
	}
	
	/**复查和确认
	 * @param event
	 */
	@FXML
	private void onReviewAndConfirmAction(ActionEvent event){
		Stage stage = Main.stageMap.get("HistopathReview");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				HistopathReview.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("HistopathReview",stage);
		}else{
			stage.show();
		}
		HistopathReview.getInstance().loadData(studyNo);
	}
	/**关闭
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**解剖任务
	 * @param event
	 */
	@FXML
	private void onAnatomyTaskHyperlink(ActionEvent event){
		Stage stage = Main.stageMap.get("HistopathCheckContent");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				HistopathCheckContent.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("HistopathCheckContent",stage);
		}else{
			stage.show();
		}
		HistopathCheckContent.getInstance().loadData(studyNo);
	}
	/**
	 * 个体数据
	 */
	@FXML
	private void onIndividualDataHyperlink(ActionEvent event){
		//1.动物
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		
		//2.
		Stage stage = Main.stageMap.get("IndividualData");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				IndividualData.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("IndividualData",stage);
		}else{
			stage.show();
		}
		IndividualData.getInstance().loadData(studyNo,animalCode);
	}
	
	/**
	 * 原发脏器/肿瘤
	 */
	@FXML
	private void onPrimaryVisceraHyperlink(ActionEvent event){
		//1.动物
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.脏器或组织
//		TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
//		Map<String,Object> map = null;
//		String visceraCode = null;
//		if(null == selectedTreeItem || !selectedTreeItem.isLeaf()){
//			showErrorMessage("请先选择脏器或组织！");
//			return;
//		}else{
//			if(!selectedTreeItem.isLeaf() ){
//				if(selectedTreeItem.getParent().getChildren().size() > 1){
//					showErrorMessage("请先选择脏器或组织！");
//					return;
//				}else{
//					map = (Map<String, Object>) selectedTreeItem.getParent().getChildren().get(0).getGraphic().getUserData();
//				}
//			}else{
//				map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
//			}
//		}
//		visceraCode = (String) map.get("visceraCode");
		if(null == visceraCode || "".equals(visceraCode)){
			showErrorMessage("请先选择脏器或组织！");
			return;
		}
		//3.
		Stage stage = Main.stageMap.get("PrimaryViscera");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				PrimaryViscera.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("PrimaryViscera",stage);
		}else{
			stage.show();
		}
		PrimaryViscera.getInstance().loadData(studyNo,animalCode,visceraCode,"HistopathCheckPage");
		
	}
	public void setOccurDate(String date){
		occurDatePicker.getTextField().setText(date);
	}
	
	
	/**获得选中的动物编号
	 * @return
	 */
	private String getSelectedAnimalCode(){
		String animalCode = null;
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
			if(null != selectedItem){
				animalCode = selectedItem.getAnimalCode();
			}
		}else{
			animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
		}
		return animalCode;
	}
	/**
	 * 肿瘤发生日期 选择日期
	 */
	@FXML
	private void onTumorOccurDateHyperlink(ActionEvent event){
		//1.动物
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		
		//2.
		Stage stage = Main.stageMap.get("WatchPage");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				WatchPage.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("WatchPage",stage);
		}else{
			stage.show();
		}
		WatchPage.getInstance().loadData(studyNo,animalCode,"HistopathCheckPage");
	}
	/**
	 * 组织学用户
	 */
	@FXML
	private void onHistoposHyperlink(ActionEvent event){
		updateHistoPoListView();
	}
	/**
	 * 病变描述
	 */
	@FXML
	private void onLesionHyperlink(ActionEvent event){
		updateLesionFindingListView();
	}
	/**更新使用频度
	 * @param event
	 */
	@FXML
	void onUpdateUserFrequentnessHyperlink(ActionEvent event){
		BaseService.getInstance().getTblHistopathCheckService().updateFreqCount();
		showMessage("使用频度更新成功！");
	}
	
	/**检查完成
	 * @param event
	 */
	@FXML
	private void onCheckFinishBtn(ActionEvent event){
		if(data_histopathResultTable.size() < 1){
			showErrorMessage("请登记检查结果！");
			return ;
		}
		tabPane.getSelectionModel().select(0);
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			selectNextAnimalOrViscera();
		}
	}
	/**设置当前动物状态
	 * @param selectedItem
	 * @param isNoAbnormal
	 */
	private void setAnimalState(HistopathAnimal selectedItem ,boolean isNoAbnormal){
		int index = 0;
		String visceraName = null;
		TreeItem<String> selectTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null != selectTreeItem && selectTreeItem.isLeaf()){
			visceraName = ((Label)selectTreeItem.getGraphic()).getText()+((Label)selectTreeItem.getParent().getGraphic()).getText();
		}
		if(isNoAbnormal){
			//未见异常
			if(selectedItem.getState().equals("未开始检查")){
				index = getIndex2(data_histopathAnimalTable,selectedItem.getAnimalCode());
				if(index > -1){
					data_histopathAnimalTable.set(index, new HistopathAnimal(selectedItem.getAnimalCode()
							,"已开始检查","无",selectedItem.getGender()));
//					selectedItem.setState("已开始检查");
//					selectedItem.setHasAbnormal("无");
				}
				index = getIndex2(data_histopathAnimalTable2,selectedItem.getAnimalCode());
				if(index > -1){
					data_histopathAnimalTable2.set(index, new HistopathAnimal(selectedItem.getAnimalCode()
							,"已开始检查","无",selectedItem.getGender()));
//					selectedItem.setState("已开始检查");
//					selectedItem.setHasAbnormal("无");
				}
			}
		}else{
			//有情况
			index = getIndex2(data_histopathAnimalTable,selectedItem.getAnimalCode());
			if(index > -1){
				data_histopathAnimalTable.set(index, new HistopathAnimal(selectedItem.getAnimalCode()
						,"已开始检查","有",selectedItem.getGender()));
//				selectedItem.setState("已开始检查");
//				selectedItem.setHasAbnormal("有");
			}
			index = getIndex2(data_histopathAnimalTable2,selectedItem.getAnimalCode());
			if(index > -1){
				data_histopathAnimalTable2.set(index, new HistopathAnimal(selectedItem.getAnimalCode()
						,"已开始检查","有",selectedItem.getGender()));
//				selectedItem.setState("已开始检查");
//				selectedItem.setHasAbnormal("有");
			}
		}
		
		if(null != visceraName){
			ObservableList<TreeItem<String>> children = rootNode_tissueSliceTree.getChildren();
			for(TreeItem<String> obj:children){
				ObservableList<TreeItem<String>> children2 = obj.getChildren();
				for(TreeItem<String> obj2:children2){
					String text = ((Label)obj2.getGraphic()).getText()+((Label)obj2.getParent().getGraphic()).getText();
					if(visceraName.equals(text)){
						tissueSliceTree.getSelectionModel().select(obj2);
						return;
					}
				}
			}
		}
	}
	private int getIndex2(ObservableList<HistopathAnimal> list ,String animlaCode){
		int i = -1;
		if(null != list && list.size() > 0){
			for(HistopathAnimal obj:list){
				if(animlaCode.equals(obj.getAnimalCode())){
					i++;
					break;
				}
				i++;
			}
		}
		return i ;
	}
	
	/**未见异常_手工录入
	 * @param event
	 */
	@FXML
	private void onNoAbnormalBtn_handword(ActionEvent event){
		//1.动物 
		String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
		String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
		String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
		if(null == bodySurfacePos && null == treeSelectedItem){
			showErrorMessage("请先选择脏器或体表部位！");
			return ;
		}else{
			if(null != bodySurfacePos ){
				if(null == anatomyFingding){
					showErrorMessage("请先选择解剖学所见！");
					return ;
				}
			}else{
				if(null != anatomyPos){
					showErrorMessage("请先选择解剖学所见！");
					return ;
				}
			}
		}
			
//		String visceraOrTissueName = null; 
		String visceraCode = null; 
		Integer visceraType = 0; 
		String visceraFixedRecordId = null; 
//		animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
//		 *	visceraName,subVisceraCode,subVisceraName,"+
//		 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
//		 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
		String visceraName = null; 
		String subVisceraCode = null; 
		String subVisceraName = null; 
		
		Integer isHandword = 1;
		Integer specialFlag = 0; 
		String anatomyPosCode = null; 
//		String anatomyPos = null; 
		String anatomyFindingCode = null; 
		Integer anatomyFindingFlag = 0; 
//		String bodySurfacePos = null; 
		
		Map<String,Object> visceraMap = null;
		if(null != treeSelectedItem){
			visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
			
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
			
			if(null != anatomyPos){
				DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
				anatomyPosCode = dictPathCommon1.getItemCode();
			}
		}
		if(null != anatomyFingding){
			if(tesuRadioButton.isSelected()){
				anatomyFindingFlag = 1;
				DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
				anatomyFindingCode = dictPathCommon2.getItemCode();
				specialFlag = dictPathCommon2.getSpecicalFlag();
			}else{
				DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
				anatomyFindingCode = dictPathCommon3.getItemCode();
				specialFlag = dictPathCommon3.getSpecicalFlag();
			}
		}else{
			//是否已登记缺失
			boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
			if(isRegisterMissing){
				showErrorMessage("当前脏器已登记缺失！");
				return;
			}
		}
//		visceraOrTissueName = bodySurfacePos != null ? bodySurfacePos:(subVisceraName != null ? subVisceraName:visceraName);
//		if(null != anatomyPos){
//			visceraOrTissueName+=" "+anatomyPos;
//		}
//		if(null != anatomyFingding){
//			visceraOrTissueName+=" "+anatomyFingding;
//		}
		
		//3.查询组织取材编号脏器Id
		String sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService()
				.getSliceVisceraId(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
		if(null == sliceVisceraId){
			
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
				}else{
					showErrorMessage("当前脏器或组织已登记‘异常’！");
				}
				return;
			}
			
			TblTissueSliceViscera tblTissueSliceViscera = new TblTissueSliceViscera();
			tblTissueSliceViscera.setVisceraType(visceraType);
			tblTissueSliceViscera.setVisceraCode(visceraCode);
			tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
			
			tblTissueSliceViscera.setVisceraName(visceraName);
			tblTissueSliceViscera.setSubVisceraCode(subVisceraCode);
			tblTissueSliceViscera.setSubVisceraName(subVisceraName);
			
			tblTissueSliceViscera.setIsHandwork(isHandword);
			
			tblTissueSliceViscera.setSpecialFlag(specialFlag);
			tblTissueSliceViscera.setAnatomyPos(anatomyPos);
			tblTissueSliceViscera.setAnatomyPosCode(anatomyPosCode);
			tblTissueSliceViscera.setAnatomyFindingCode(anatomyFindingCode);
			tblTissueSliceViscera.setAnatomyFindingFlag(anatomyFindingFlag);
			tblTissueSliceViscera.setAnatomyFingding(anatomyFingding);
			tblTissueSliceViscera.setBodySurfacePos(bodySurfacePos);
			
			tblTissueSliceViscera.setAppendFlag(1);//补录
			tblTissueSliceViscera.setAppendStudyNo(studyNo);
			tblTissueSliceViscera.setAppendAnimalCode(animalCode);
			
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			int isNoAbnormal = 1;
			
			String operator = SaveUserInfo.getUserName();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			tblHistopathCheck.setOperator(operator);
			
			//id,operateTime 在服务器端设置，保存
			BaseService.getInstance().getTblHistopathCheckService().saveHandWordRecord(tblTissueSliceViscera,tblHistopathCheck );
		}else{
			//4.判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
//			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId);
			Json json = null;
			if(isNull(anatomyFingding)){
				json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			}else{
				json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,null,null);
			}
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
				}else{
					showErrorMessage("当前脏器或组织已登记‘异常’！");
				}
				return;
			}
			
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			int isNoAbnormal = 1;
			
			String operator = SaveUserInfo.getUserName();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			tblHistopathCheck.setOperator(operator);
			
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
			}
			
			//id,operateTime 在服务器端设置，保存
			BaseService.getInstance().getTblHistopathCheckService().saveOne(tblHistopathCheck );
		}
		
		//5.更新histopathCheckTable
		updateHistopathCheckTable();
	}
	/**未见异常_镜检组合
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void onNoAbnormalBtn(ActionEvent event){
		//1.动物
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		String animalCode = null;
		if(null == selectedItem){
			showErrorMessage("请先选择动物编号！");
			return;
		}else{
			animalCode = selectedItem.getAnimalCode();
		}
		//2.脏器或组织
		TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		Map<String,Object> map = null;
		if(null == selectedTreeItem || !selectedTreeItem.isLeaf()){
			showErrorMessage("请先选择脏器或组织！");
			return;
		}else{
			if(!selectedTreeItem.isLeaf() ){
				if(selectedTreeItem.getParent().getChildren().size() > 1){
					showErrorMessage("请先选择脏器或组织！");
					return;
				}else{
					map = (Map<String, Object>) selectedTreeItem.getParent().getChildren().get(0).getGraphic().getUserData();
				}
			}else{
				map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
			}
		}
		//3.判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
		String sliceVisceraId = (String) map.get("sliceVisceraId");
		
		String anatomyFingding = (String) map.get("anatomyFingding");
		String visceraCode = null;
		String subVisceraCode = null;
		if(isNull(anatomyFingding)){
			//	
			visceraCode = (String) map.get("visceraCode");
			subVisceraCode = (String) map.get("subVisceraCode");
			//是否已登记缺失
			boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
			if(isRegisterMissing){
				showErrorMessage("当前脏器已登记缺失！");
				return;
			}
		}
		
		
		Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
		if(!json.isSuccess()){
			if("1".equals(json.getMsg())){
				showErrorMessage("当前脏器或组织已登记‘未见异常’！");
			}else{
				showErrorMessage("当前脏器或组织已登记‘异常’！");
			}
			
			return;
		}
		//4.保存未见异常记录
//		sliceVisceraId,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
//		 *	visceraName,subVisceraCode,subVisceraName,"+
//		 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
//		 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos,
		
//		taskId,studyNo,animalCode,sliceVisceraId,
		Integer visceraType = (Integer) map.get("visceraType");
		visceraCode = (String) map.get("visceraCode");
		String visceraName = (String) map.get("visceraName");
		subVisceraCode = (String) map.get("subVisceraCode");
		String subVisceraName = (String) map.get("subVisceraName");
//		String visceraFixedRecordId = (String) map.get("visceraFixedRecordId");
		int isNoAbnormal = 1;
		
		String operator = SaveUserInfo.getUserName();
		
		TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
		tblHistopathCheck.setTaskId("");
		tblHistopathCheck.setStudyNo(studyNo);
		tblHistopathCheck.setAnimalCode(animalCode);
		tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
		tblHistopathCheck.setVisceraType(visceraType);
		tblHistopathCheck.setVisceraCode(visceraCode);
		tblHistopathCheck.setVisceraName(visceraName);
		tblHistopathCheck.setSubVisceraCode(subVisceraCode);
		tblHistopathCheck.setSubVisceraName(subVisceraName);
		
//		tblHistopathCheck.setVisceraFixedRecordId(visceraFixedRecordId);   暂不使用
		tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
		tblHistopathCheck.setOperator(operator);
		
		//id,operateTime 在服务器端设置，保存
		BaseService.getInstance().getTblHistopathCheckService().saveOne(tblHistopathCheck );
		
		
		//5.更新histopathCheckTable
		updateHistopathCheckTable();
//		if(!selectNextAnimalOrViscera){
//			showMessage("‘未见异常’登记成功！");
//		}
		//6.动物优先，当前切片变蓝
		if(animalRadioButton.isSelected()){
			setBlue(selectedTreeItem,true);
		}
		//7.
		setAnimalState(selectedItem, true);
		
		//8.选在下一个动物或下一个脏器
		selectNextAnimalOrViscera();
	}
	
	
	/**设置蓝色（表示已经检查过）
	 * @param selectedTreeItem
	 * @param flag  true:蓝色   false:黑色
	 */
	private void setBlue(TreeItem<String> selectedTreeItem,boolean flag){
		if(null != selectedTreeItem){
			if(flag){
				selectedTreeItem.getGraphic().setStyle("-fx-text-fill: blue;");
			}else{
				selectedTreeItem.getGraphic().setStyle("");
			}
		}
	}
	
	
	/**登记结果
	 * @param event
	 */
	@FXML
	private void onRegisterResultBtn(ActionEvent event){
		//1.动物
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.脏器或组织
		TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null == selectedTreeItem || !selectedTreeItem.isLeaf()){
			showErrorMessage("请先选择脏器或组织！");
			return;
		}
		
		//3.选择组织学所见Tab
		tabPane.getSelectionModel().select(1);
		
	}
	/**登记结果——手工录入
	 * @param event
	 */
	@FXML
	private void onRegisterResultBtn_handword(ActionEvent event){
		
		//1.动物
		String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
		String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
		String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
		if(null == bodySurfacePos && null == treeSelectedItem){
			showErrorMessage("请先选择脏器或体表部位！");
			return ;
		}else{
			if(null != bodySurfacePos ){
				if(null == anatomyFingding){
					showErrorMessage("请先选择解剖学所见！");
					return ;
				}
			}else{
				if(null != anatomyPos && null == anatomyFingding){
					showErrorMessage("请先选择解剖学所见！");
					return ;
				}
			}
		}
		
		//3.选择组织学所见Tab
		tabPane.getSelectionModel().select(1);
		
	}
	
	/**参照所见
	 * @param event
	 */
	@FXML
	private void onReferBtn(ActionEvent event){
		//1.动物
		String animalCode = getSelectedAnimalCode();
		if(null == animalCode || "".equals(animalCode)){
			showErrorMessage("请先选择动物编号！");
			return;
		}
		//2.1脏器或组织
		if(null == visceraCode || "".equals(visceraCode)){
			showErrorMessage("请先选择脏器或组织！");
			return;
		}
		
		//2.2判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
		if(null != sliceVisceraId && !"".equals(sliceVisceraId)){
			
			String visceraCode = null;
			String subVisceraCode = null;
			if(null != visceraMapList && visceraMapList.size() > 0){
				Map<String,Object> visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
//				Integer visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
//				String visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
//				String subVisceraName = (String) visceraMap.get("subVisceraName");
				
			}
			
			//是否已登记缺失
			boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
			if(isRegisterMissing){
				showErrorMessage("当前脏器已登记缺失！");
				return;
			}
			
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					return;
				}
			}
		}
		
		//3.
		Stage stage = Main.stageMap.get("ReferFinding");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				ReferFinding.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("ReferFinding",stage);
		}else{
			stage.show();
		}
		ReferFinding.getInstance().loadData(studyNo,animalCode,visceraCode,"HistopathCheckPage");
	}
	/**增加参照所见
	 * @param id
	 * @param tumorNum
	 */
	public void AddReferItem(String referId, int tumorNum) {
		//1.动物
		String animalCode = getSelectedAnimalCode();
		
		
		//2.脏器或组织
		if(null != sliceVisceraId && !"".equals(sliceVisceraId)){
			String visceraCode = null;
			String subVisceraCode = null;
			if(null != visceraMapList && visceraMapList.size() > 0){
				Map<String,Object> visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
				visceraCode = (String) visceraMap.get("visceraCode");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
			}
			
			//2.1判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					return;
				}
			}
			//3.准备数据
			String operator = SaveUserInfo.getUserName();
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setOperator(operator);
			tblHistopathCheck.setTumorNum(tumorNum);
			
			if(null != visceraMapList && visceraMapList.size() > 0){
				Map<String,Object> visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
				Integer visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				String visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				String subVisceraName = (String) visceraMap.get("subVisceraName");
				
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
			}
			
			//4.保存
			BaseService.getInstance().getTblHistopathCheckService().saveOne(referId,tblHistopathCheck);
		}else{
			//2.2脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
			String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			if(null == bodySurfacePos && null == treeSelectedItem){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}else{
				if(null != bodySurfacePos ){
					if(null == anatomyFingding){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}else{
					if(null != anatomyPos){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}
			}
				
			String visceraCode = null; 
			Integer visceraType = 0; 
			String visceraFixedRecordId = null; 
			String visceraName = null; 
			String subVisceraCode = null; 
			String subVisceraName = null; 
			
			Integer isHandword = 1;
			Integer specialFlag = 0; 
			String anatomyPosCode = null; 
//			String anatomyPos = null; 
			String anatomyFindingCode = null; 
			Integer anatomyFindingFlag = 0; 
//			String bodySurfacePos = null; 
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
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
				
				if(null != anatomyPos){
					DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
					anatomyPosCode = dictPathCommon1.getItemCode();
				}
			}
			if(null != anatomyFingding){
				if(tesuRadioButton.isSelected()){
					anatomyFindingFlag = 1;
					DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon2.getItemCode();
					specialFlag = dictPathCommon2.getSpecicalFlag();
				}else{
					DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon3.getItemCode();
					specialFlag = dictPathCommon3.getSpecicalFlag();
				}
			}
			//2.3准备  切片脏器数据（补录）
			TblTissueSliceViscera tblTissueSliceViscera =  new TblTissueSliceViscera();
			tblTissueSliceViscera.setVisceraType(visceraType);
			tblTissueSliceViscera.setVisceraCode(visceraCode);
			tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
			
			tblTissueSliceViscera.setVisceraName(visceraName);
			tblTissueSliceViscera.setSubVisceraCode(subVisceraCode);
			tblTissueSliceViscera.setSubVisceraName(subVisceraName);
			
			tblTissueSliceViscera.setIsHandwork(isHandword);
			
			tblTissueSliceViscera.setSpecialFlag(specialFlag);
			tblTissueSliceViscera.setAnatomyPos(anatomyPos);
			tblTissueSliceViscera.setAnatomyPosCode(anatomyPosCode);
			tblTissueSliceViscera.setAnatomyFindingCode(anatomyFindingCode);
			tblTissueSliceViscera.setAnatomyFindingFlag(anatomyFindingFlag);
			tblTissueSliceViscera.setAnatomyFingding(anatomyFingding);
			tblTissueSliceViscera.setBodySurfacePos(bodySurfacePos);
			
			tblTissueSliceViscera.setAppendFlag(1);//补录
			tblTissueSliceViscera.setAppendStudyNo(studyNo);
			tblTissueSliceViscera.setAppendAnimalCode(animalCode);
				
			//3.准备数据
			String operator = SaveUserInfo.getUserName();
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
//			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setOperator(operator);
			tblHistopathCheck.setTumorNum(tumorNum);
			
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
			}
			
			//4.保存
			sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService().saveOne(referId,tblHistopathCheck,tblTissueSliceViscera);
		}
		//5.更新界面上一些数据
		//5.1更新组织学所见表（tab1）
		updateHistopathResultTable(animalCode,sliceVisceraId);
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			if(animalRadioButton.isSelected()){
				//5.2设置动物状态
				HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
				TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
				setAnimalState(selectedItem, false);
				//5.3设置蓝色
				setBlue(selectedTreeItem,true);
			}
		}
	}
	/**缺失登记
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void onMissingRegister(ActionEvent event){
		
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			//1.动物
			HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
			String animalCode = null;
			if(null == selectedItem){
				showErrorMessage("请先选择动物编号！");
				return;
			}else{
				animalCode = selectedItem.getAnimalCode();
			}
			//2.脏器或组织
			TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
			Map<String,Object> map = null;
			if(null == selectedTreeItem || !selectedTreeItem.isLeaf()){
				showErrorMessage("请先选择脏器或组织！");
				return;
			}else{
				map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
			}
			//3.开始准备数据
			String sliceVisceraId = (String) map.get("sliceVisceraId");
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			
			String anatomyFingding = (String) map.get("anatomyFingding");
			String visceraCode = null;
			String subVisceraCode = null;
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				Map<String,Object> visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
				Integer visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				String visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				String subVisceraName = (String) visceraMap.get("subVisceraName");
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
				
				//是否已登记缺失
				boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
				if(isRegisterMissing){
					showErrorMessage("当前脏器已登记缺失！");
					return;
				}
			}
			
			//未见异常
			int isNoAbnormal =  -1; //：登记缺失
			//3.1判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
				}else{
					showErrorMessage("当前脏器或组织已登记‘异常’！");
				}
				return;
			}
			tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			
			//签字通过
			if(Sign.openSignWithReasonFrame("缺失原因", "缺失登记")){

	        	String currentUserName = SaveUserInfo.getUserName();
	        	Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
	    		String reason = Sign.getReason();
	    		
	    		tblHistopathCheck.setOperator(currentUserName);
	    		tblHistopathCheck.setOperateTime(date);
	    		tblHistopathCheck.setReason(reason);
			}else{
				return;
			}
				
			//4.缺失登记
			
			//id,operateTime 在服务器端设置，保存
			BaseService.getInstance().getTblHistopathCheckService().missingRegister(tblHistopathCheck);
			
			//tab切回去的时候更新histopathCheckTable
			hasEdit = true;
			//5.2更新组织学所见表（tab1）
			updateHistopathResultTable(animalCode,sliceVisceraId);
			
			if(animalRadioButton.isSelected()){
				//7.设置动物状态
				setAnimalState(selectedItem, isNoAbnormal == 1);
				//8.设置蓝色
				setBlue(selectedTreeItem,true);
			}
		}else{

			//1.动物  
			String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
			if(null == animalCode || "".equals(animalCode)){
				showErrorMessage("请先选择动物编号！");
				return;
			}
			//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
			String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			if(null == bodySurfacePos && null == treeSelectedItem){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}else{
				if(null != bodySurfacePos ){
					if(null == anatomyFingding){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}else{
					if(null != anatomyPos){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}
			}
				
			String visceraCode = null; 
			Integer visceraType = 0; 
			String visceraFixedRecordId = null; 
			String visceraName = null; 
			String subVisceraCode = null; 
			String subVisceraName = null; 
			
			Integer isHandword = 1;
			Integer specialFlag = 0; 
			String anatomyPosCode = null; 
//			String anatomyPos = null; 
			String anatomyFindingCode = null; 
			Integer anatomyFindingFlag = 0; 
//			String bodySurfacePos = null; 
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
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
				
				if(null != anatomyPos){
					DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
					anatomyPosCode = dictPathCommon1.getItemCode();
				}
			}
			if(null != anatomyFingding){
				if(tesuRadioButton.isSelected()){
					anatomyFindingFlag = 1;
					DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon2.getItemCode();
					specialFlag = dictPathCommon2.getSpecicalFlag();
				}else{
					DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon3.getItemCode();
					specialFlag = dictPathCommon3.getSpecicalFlag();
				}
			}	
			
			//3.查询组织取材编号脏器Id
			sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService()
					.getSliceVisceraId(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
			//4.准备数据
			TblTissueSliceViscera tblTissueSliceViscera = null;
			if(null == sliceVisceraId){
				tblTissueSliceViscera = new TblTissueSliceViscera();
				tblTissueSliceViscera.setVisceraType(visceraType);
				tblTissueSliceViscera.setVisceraCode(visceraCode);
				tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
				
				tblTissueSliceViscera.setVisceraName(visceraName);
				tblTissueSliceViscera.setSubVisceraCode(subVisceraCode);
				tblTissueSliceViscera.setSubVisceraName(subVisceraName);
				
				tblTissueSliceViscera.setIsHandwork(isHandword);
				
				tblTissueSliceViscera.setSpecialFlag(specialFlag);
				tblTissueSliceViscera.setAnatomyPos(anatomyPos);
				tblTissueSliceViscera.setAnatomyPosCode(anatomyPosCode);
				tblTissueSliceViscera.setAnatomyFindingCode(anatomyFindingCode);
				tblTissueSliceViscera.setAnatomyFindingFlag(anatomyFindingFlag);
				tblTissueSliceViscera.setAnatomyFingding(anatomyFingding);
				tblTissueSliceViscera.setBodySurfacePos(bodySurfacePos);
				
				tblTissueSliceViscera.setAppendFlag(1);//补录
				tblTissueSliceViscera.setAppendStudyNo(studyNo);
				tblTissueSliceViscera.setAppendAnimalCode(animalCode);
				
			}
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			
			//未见异常
			int isNoAbnormal = -1;//缺失登记
			if(null != sliceVisceraId){
				//判断该  专题、该动物、该组织切片脏器记录Id 对应脏器  是否有记录
				Json json = null;
				if(isNull(anatomyFingding)){
					json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
				}else{
					json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,null,null);
				}
				if(!json.isSuccess()){
					if("1".equals(json.getMsg())){
						showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					}else{
						showErrorMessage("当前脏器或组织已登记‘异常’！");
					}
					return;
				}
			}else{
				Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
				if(!json.isSuccess()){
					if("1".equals(json.getMsg())){
						showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					}else{
						showErrorMessage("当前脏器或组织已登记‘异常’！");
					}
					return;
				}
			}
			tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			
			//签字通过
			if(Sign.openSignWithReasonFrame("缺失原因", "缺失登记")){

	        	String currentUserName = SaveUserInfo.getUserName();
	        	Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
	    		String reason = Sign.getReason();
	    		
	    		tblHistopathCheck.setOperator(currentUserName);
	    		tblHistopathCheck.setOperateTime(date);
	    		tblHistopathCheck.setReason(reason);
			}else{
				return;
			}	
			
			//5.保存记录
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
			}
			if(null != sliceVisceraId ){
				//id,operateTime 在服务器端设置，保存
				BaseService.getInstance().getTblHistopathCheckService().missingRegister(tblHistopathCheck );
			}else{
				sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService().saveHandWordRecordAndMissingRegister(tblTissueSliceViscera,tblHistopathCheck );
			}
			//6.
			//tab切回去的时候更新histopathCheckTable
			hasEdit = true;
			//8.更新组织学所见表（tab1）
			updateHistopathResultTable(animalCode,sliceVisceraId);
		}
		
	}
	/**登记结果(组织学所见tab)
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void onRegisterResultBtn_1(ActionEvent event){
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			//1.动物
			HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
			String animalCode = null;
			if(null == selectedItem){
				showErrorMessage("请先选择动物编号！");
				return;
			}else{
				animalCode = selectedItem.getAnimalCode();
			}
			//2.脏器或组织
			TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
			Map<String,Object> map = null;
			if(null == selectedTreeItem || !selectedTreeItem.isLeaf()){
				showErrorMessage("请先选择脏器或组织！");
				return;
			}else{
				map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
			}
			//3.开始准备数据
			String sliceVisceraId = (String) map.get("sliceVisceraId");
			String operator = SaveUserInfo.getUserName();
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setOperator(operator);
			
			String anatomyFingding = (String) map.get("anatomyFingding");
			String visceraCode = null;
			String subVisceraCode = null;
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				Map<String,Object> visceraMap = visceraMapList.get(visceraNameListView.getSelectionModel().getSelectedIndex());
				Integer visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				String visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				String subVisceraName = (String) visceraMap.get("subVisceraName");
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
				
				//是否已登记缺失
				boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
				if(isRegisterMissing){
					showErrorMessage("当前脏器已登记缺失！");
					return;
				}
			}
			
			//未见异常
			int isNoAbnormal = 0;
			if(noAbnormalRadio.isSelected()){
				//3.1判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
				Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
				if(!json.isSuccess()){
					if("1".equals(json.getMsg())){
						showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					}else{
						showErrorMessage("当前脏器或组织已登记‘异常’！");
					}
					return;
				}
				
				isNoAbnormal = 1;
				tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			}else{
				//异常
				
				//3.2判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
				Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
				if(!json.isSuccess()){
					if("1".equals(json.getMsg())){
						showErrorMessage("当前脏器或组织已登记‘未见异常’！");
						return;
					}
				}
				String histoPos = histoPoListView.getSelectionModel().getSelectedItem();
//				if(null == histoPos){
//					showErrorMessage("请选择’组织学用语‘！");
//					searchComboBox.getSelectionModel().select(0);
//					return ;
//				}
				String lesionFinding = lesionFindingListView.getSelectionModel().getSelectedItem();
				if(null == lesionFinding){
					showErrorMessage("请选择’病变描述‘！");
					searchComboBox.getSelectionModel().select(1);
					return ;
				}
				
				
				int tumorFlag = 0;			//是否肿瘤
				int metastasisFlag = 0;		//是否转移
				int tumorNumber = 0;
//			String primaryVisceraCode = null;       //原发脏器编号20
//			String primaryViscera = null;           //原发脏器100
//			String primaryTumor = null;             //原发肿瘤100
				if(noTumorRadio.isSelected()){
					tumorFlag = 0;
					String level = levelComboBox.getSelectionModel().getSelectedItem();
					tblHistopathCheck.setLevel(level);
				}else {
					if(lxTumorRadio.isSelected()){
						tumorFlag = 1;
					}else{
						tumorFlag = 2;
						if(noMetastasisRadio.isSelected()){
							metastasisFlag = 0;
						}else if(metastasisRadio.isSelected()){
							metastasisFlag = 1;
							if(null == primaryTumor || "".equals(primaryTumor)){
								showErrorMessage("请选择原发脏器肿瘤！");
								return ;
							}
						}else{
							metastasisFlag = 2;
						}
					}
					String tumorNumberStr = tumorNumberComboBox.getSelectionModel().getSelectedItem();
					if(!NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
						showErrorMessage("请选择或录入正确的肿瘤数量！");
						tumorNumberComboBox.requestFocus();
						return ;
					}
					tumorNumber = Integer.parseInt(tumorNumberStr);
				}
				
				if(tumorFlag > 0){
					int tumorPos = 0;
					int tumorCharacter = 0;
					Date tumorOccurDate = null;
					
					if(tumorPosRadio1.isSelected() || tumorPosRadio2.isSelected()){
						if(tumorPosRadio2.isSelected()){
							tumorPos = 2;
							if(tumorCharacterRadio1.isSelected()){
								tumorCharacter = 1;
							}else if(tumorCharacterRadio2.isSelected()){
								tumorCharacter = 2;
							}else if(tumorCharacterRadio3.isSelected()){
								tumorCharacter = 3;
							}
							if(tumorCharacter == 0){
								showErrorMessage("请选择肿瘤特性！");
								return;
							}
						}else{
							tumorPos = 1;
							String tumorOccurDateStr = "";
							tumorOccurDateStr = occurDatePicker.getTextField().getText().trim();
							if(null != tumorOccurDateStr && !"".equals(tumorOccurDateStr)){
								tumorOccurDate = DateUtil.stringToDate(tumorOccurDateStr, "yyyy-MM-dd");
							}else{
								showErrorMessage("请选择肿瘤发生日期！");
								return;
							}
						}
						
						tblHistopathCheck.setTumorPos(tumorPos);
						tblHistopathCheck.setTumorCharacter(tumorCharacter);
						tblHistopathCheck.setTumorOccurDate(tumorOccurDate);
						
					}else{
						showErrorMessage("请选择肿瘤位置！");
						return;
					}
				}
				
				tblHistopathCheck.setTumorFlag(tumorFlag);
				tblHistopathCheck.setMetastasisFlag(metastasisFlag);
				tblHistopathCheck.setTumorNum(tumorNumber);
				if(null != histoPos){
					tblHistopathCheck.setHistoPos(histoPos);
					String histoPosCode = descCNDictPathCommonMap_histoPoListView.get(histoPos).getItemCode();
					tblHistopathCheck.setHistoPosCode(histoPosCode);
				}
				tblHistopathCheck.setLesionFinding(lesionFinding);
				String lesionFindingCode = descCNDictPathCommonMap_lesionFindingListView.get(lesionFinding).getItemCode();
				tblHistopathCheck.setLesionFindingCode(lesionFindingCode );
				
				tblHistopathCheck.setPrimaryTumor(primaryTumor);
				tblHistopathCheck.setPrimaryViscera(primaryVisceraName);
				tblHistopathCheck.setPrimaryVisceraCode(primaryVisceraCode);
				
			}
			
			//3.5备注
			String remark = remarkText.getText().trim();
			if(remark.getBytes().length > 199){
				showErrorMessage("备注长度不能大于200！");
				remarkText.requestFocus();
				return ;
			}
			tblHistopathCheck.setRemark(remark);
			
			//4.保存记录
			
			//id,operateTime 在服务器端设置，保存
			BaseService.getInstance().getTblHistopathCheckService().saveOne(tblHistopathCheck );
			
			//tab切回去的时候更新histopathCheckTable
			hasEdit = true;
			//5.若未见异常，则切回tab0，否则更新组织学所见表（tab1）
			if(isNoAbnormal == 1){
				tabPane.getSelectionModel().select(0);
				//5.1选在下一个动物或下一个脏器
				boolean selectNextAnimalOrViscera = selectNextAnimalOrViscera();
				if(!selectNextAnimalOrViscera){
//					更新组织学所见表（tab1）
					updateHistopathResultTable(animalCode,sliceVisceraId);
				}
			}else{
				//5.2更新组织学所见表（tab1）
				updateHistopathResultTable(animalCode,sliceVisceraId);
			}
			if(animalRadioButton.isSelected()){
				//7.设置动物状态
				setAnimalState(selectedItem, isNoAbnormal == 1);
				//8.设置蓝色
				setBlue(selectedTreeItem,true);
			}
			
		}else{
			//1.动物  
			String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
			if(null == animalCode || "".equals(animalCode)){
				showErrorMessage("请先选择动物编号！");
				return;
			}
			//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
			String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			if(null == bodySurfacePos && null == treeSelectedItem){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}else{
				if(null != bodySurfacePos ){
					if(null == anatomyFingding){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}else{
					if(null != anatomyPos){
						showErrorMessage("请先选择解剖学所见！");
						return ;
					}
				}
			}
				
			String visceraCode = null; 
			Integer visceraType = 0; 
			String visceraFixedRecordId = null; 
			String visceraName = null; 
			String subVisceraCode = null; 
			String subVisceraName = null; 
			
			Integer isHandword = 1;
			Integer specialFlag = 0; 
			String anatomyPosCode = null; 
//			String anatomyPos = null; 
			String anatomyFindingCode = null; 
			Integer anatomyFindingFlag = 0; 
//			String bodySurfacePos = null; 
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
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
				
				if(null != anatomyPos){
					DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
					anatomyPosCode = dictPathCommon1.getItemCode();
				}
			}
			if(null != anatomyFingding){
				if(tesuRadioButton.isSelected()){
					anatomyFindingFlag = 1;
					DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon2.getItemCode();
					specialFlag = dictPathCommon2.getSpecicalFlag();
				}else{
					DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon3.getItemCode();
					specialFlag = dictPathCommon3.getSpecicalFlag();
				}
			}else{
				//是否已登记缺失
				boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
				if(isRegisterMissing){
					showErrorMessage("当前脏器已登记缺失！");
					return;
				}
			}
			
			//3.查询组织取材编号脏器Id
			sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService()
					.getSliceVisceraId(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
			//4.准备数据
			TblTissueSliceViscera tblTissueSliceViscera = null;
			if(null == sliceVisceraId){
				tblTissueSliceViscera = new TblTissueSliceViscera();
				tblTissueSliceViscera.setVisceraType(visceraType);
				tblTissueSliceViscera.setVisceraCode(visceraCode);
				tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
				
				tblTissueSliceViscera.setVisceraName(visceraName);
				tblTissueSliceViscera.setSubVisceraCode(subVisceraCode);
				tblTissueSliceViscera.setSubVisceraName(subVisceraName);
				
				tblTissueSliceViscera.setIsHandwork(isHandword);
				
				tblTissueSliceViscera.setSpecialFlag(specialFlag);
				tblTissueSliceViscera.setAnatomyPos(anatomyPos);
				tblTissueSliceViscera.setAnatomyPosCode(anatomyPosCode);
				tblTissueSliceViscera.setAnatomyFindingCode(anatomyFindingCode);
				tblTissueSliceViscera.setAnatomyFindingFlag(anatomyFindingFlag);
				tblTissueSliceViscera.setAnatomyFingding(anatomyFingding);
				tblTissueSliceViscera.setBodySurfacePos(bodySurfacePos);
				
				tblTissueSliceViscera.setAppendFlag(1);//补录
				tblTissueSliceViscera.setAppendStudyNo(studyNo);
				tblTissueSliceViscera.setAppendAnimalCode(animalCode);
				
			}
			String operator = SaveUserInfo.getUserName();
			TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
			tblHistopathCheck.setTaskId("");
			tblHistopathCheck.setStudyNo(studyNo);
			tblHistopathCheck.setAnimalCode(animalCode);
			tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
			tblHistopathCheck.setOperator(operator);
			
			//未见异常
			int isNoAbnormal = 0;
			if(noAbnormalRadio.isSelected()){
				if(null != sliceVisceraId){
					//判断该  专题、该动物、该组织切片脏器记录Id 对应脏器  是否有记录
					Json json = null;
					if(isNull(anatomyFingding)){
						json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
					}else{
						json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,null,null);
					}
					if(!json.isSuccess()){
						if("1".equals(json.getMsg())){
							showErrorMessage("当前脏器或组织已登记‘未见异常’！");
						}else{
							showErrorMessage("当前脏器或组织已登记‘异常’！");
						}
						return;
					}
				}else{
					Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
					if(!json.isSuccess()){
						if("1".equals(json.getMsg())){
							showErrorMessage("当前脏器或组织已登记‘未见异常’！");
						}else{
							showErrorMessage("当前脏器或组织已登记‘异常’！");
						}
						return;
					}
				}
				
				isNoAbnormal = 1;
				tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
			}else{
				//异常
				if(null != sliceVisceraId){
					//判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
//					Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId);
					Json json = null;
					if(isNull(anatomyFingding)){
						json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
					}else{
						json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,null,null);
					}
					if(!json.isSuccess()){
						if("1".equals(json.getMsg())){
							showErrorMessage("当前脏器或组织已登记‘未见异常’！");
							return;
						}
					}
				}else{
					//判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
					Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
					if(!json.isSuccess()){
						if("1".equals(json.getMsg())){
							showErrorMessage("当前脏器或组织已登记‘未见异常’！");
							return;
						}
					}
				}
				String histoPos = histoPoListView.getSelectionModel().getSelectedItem();
//				if(null == histoPos){
//					showErrorMessage("请选择’组织学用语‘！");
//					searchComboBox.getSelectionModel().select(0);
//					return ;
//				}
				String lesionFinding = lesionFindingListView.getSelectionModel().getSelectedItem();
				if(null == lesionFinding){
					showErrorMessage("请选择’病变描述‘！");
					searchComboBox.getSelectionModel().select(1);
					return ;
				}
				
				
				int tumorFlag = 0;			//是否肿瘤
				int metastasisFlag = 0;		//是否转移
				int tumorNumber = 0;
				if(noTumorRadio.isSelected()){
					tumorFlag = 0;
					String level = levelComboBox.getSelectionModel().getSelectedItem();
					tblHistopathCheck.setLevel(level);
				}else {
					if(lxTumorRadio.isSelected()){
						tumorFlag = 1;
					}else{
						tumorFlag = 2;
						if(noMetastasisRadio.isSelected()){
							metastasisFlag = 0;
						}else if(metastasisRadio.isSelected()){
							metastasisFlag = 1;
							if(null == primaryTumor || "".equals(primaryTumor)){
								showErrorMessage("请选择原发脏器肿瘤！");
								return ;
							}
						}else{
							metastasisFlag = 2;
						}
					}
					String tumorNumberStr = tumorNumberComboBox.getSelectionModel().getSelectedItem();
					if(!NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
						showErrorMessage("请选择或录入正确的肿瘤数量！");
						tumorNumberComboBox.requestFocus();
						return ;
					}
					tumorNumber = Integer.parseInt(tumorNumberStr);
				}
				
				if(tumorFlag > 0){
					int tumorPos = 0;
					int tumorCharacter = 0;
					Date tumorOccurDate = null;
					
					if(tumorPosRadio1.isSelected() || tumorPosRadio2.isSelected()){
						if(tumorPosRadio2.isSelected()){
							tumorPos = 2;
							if(tumorCharacterRadio1.isSelected()){
								tumorCharacter = 1;
							}else if(tumorCharacterRadio2.isSelected()){
								tumorCharacter = 2;
							}else if(tumorCharacterRadio3.isSelected()){
								tumorCharacter = 3;
							}
							if(tumorCharacter == 0){
								showErrorMessage("请选择肿瘤特性！");
								return;
							}
						}else{
							tumorPos = 1;
							String tumorOccurDateStr = "";
							tumorOccurDateStr = occurDatePicker.getTextField().getText().trim();
							if(null != tumorOccurDateStr && !"".equals(tumorOccurDateStr)){
								tumorOccurDate = DateUtil.stringToDate(tumorOccurDateStr, "yyyy-MM-dd");
							}else{
								showErrorMessage("请选择肿瘤发生日期！");
								return;
							}
						}
						
						tblHistopathCheck.setTumorPos(tumorPos);
						tblHistopathCheck.setTumorCharacter(tumorCharacter);
						tblHistopathCheck.setTumorOccurDate(tumorOccurDate);
						
					}else{
						showErrorMessage("请选择肿瘤位置！");
						return;
					}
				}
				
				tblHistopathCheck.setTumorFlag(tumorFlag);
				tblHistopathCheck.setMetastasisFlag(metastasisFlag);
				tblHistopathCheck.setTumorNum(tumorNumber);
				if(null != histoPos){
					tblHistopathCheck.setHistoPos(histoPos);
					String histoPosCode = descCNDictPathCommonMap_histoPoListView.get(histoPos).getItemCode();
					tblHistopathCheck.setHistoPosCode(histoPosCode);
				}
				tblHistopathCheck.setLesionFinding(lesionFinding);
				String lesionFindingCode = descCNDictPathCommonMap_lesionFindingListView.get(lesionFinding).getItemCode();
				tblHistopathCheck.setLesionFindingCode(lesionFindingCode );
				
				tblHistopathCheck.setPrimaryTumor(primaryTumor);
				tblHistopathCheck.setPrimaryViscera(primaryVisceraName);
				tblHistopathCheck.setPrimaryVisceraCode(primaryVisceraCode);
				
			}
			//备注
			String remark = remarkText.getText().trim();
			if(remark.getBytes().length > 199){
				showErrorMessage("备注长度不能大于200！");
				remarkText.requestFocus();
				return ;
			}
			tblHistopathCheck.setRemark(remark);
			
			//5.保存记录
			if(isNull(anatomyFingding)){
				//	TODO 保存时加  动物脏器
				tblHistopathCheck.setVisceraType(visceraType);
				tblHistopathCheck.setVisceraCode(visceraCode);
				tblHistopathCheck.setVisceraName(visceraName);
				tblHistopathCheck.setSubVisceraCode(subVisceraCode);
				tblHistopathCheck.setSubVisceraName(subVisceraName);
			}
			if(null != sliceVisceraId ){
				//id,operateTime 在服务器端设置，保存
				BaseService.getInstance().getTblHistopathCheckService().saveOne(tblHistopathCheck );
			}else{
				sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService().saveHandWordRecord(tblTissueSliceViscera,tblHistopathCheck );
			}
			//6.
			//tab切回去的时候更新histopathCheckTable
			hasEdit = true;
			//7.未见时，切回tab0
			if(isNoAbnormal == 1){
				tabPane.getSelectionModel().select(0);
			}
			//8.更新组织学所见表（tab1）
			updateHistopathResultTable(animalCode,sliceVisceraId);
			
		}
		//9.重置 组织学所见tab
		primaryVisceraCode = null;	//源发脏器编号
		primaryVisceraName = null;	//源发脏器名称
		primaryTumor = null;	
		
		histoPoListView.getSelectionModel().clearSelection();
		lesionFindingListView.getSelectionModel().clearSelection();
		
		remarkText.clear();
		levelComboBox.getSelectionModel().select(0);
	}
	
	/**快速查找TextField  键盘按下起来事件
	 * @param event
	 */
	@FXML
	private void OnSearchTextFieldKeyReleased(KeyEvent event){
//		"Enter";
		if("Down".equals(event.getCode().getName())){
			searchListView.getSelectionModel().select(0);
			searchListView.requestFocus();
		}else{
			showSearchListView();
		}
	}
	/**
	 * 显示 快速查找ListView 
	 */
	private void showSearchListView() {
		data_searchListView.clear();
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		String py = searchTextField.getText();
		if("组织学用语".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_histoPoListView,py);
		}else{
			updateData_searchListView(pyDescCNMap_lesionFindingListView,py);
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
	 * 选择下一个动物或下一个脏器
	 */
	private boolean selectNextAnimalOrViscera() {
		boolean flag = false;//移动成功
		
		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
		TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		String animalCode = "";
		String sliceVisceraId = "";
		String sliceCode = "";
		//1.先查询当前选择的脏器和动物，若没有则返回 false
		if(null != selectedTreeItem && selectedTreeItem.isLeaf() && null != selectedItem ){
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
			sliceVisceraId = (String) map.get("sliceVisceraId");
			sliceCode = (String) map.get("sliceCode");
			
			animalCode = selectedItem.getAnimalCode();
		}else {
			return false;
		}
		//2.选择下一个
		//动物优先
		if(animalRadioButton.isSelected()){
			ObservableList<TreeItem<String>> children_viscera = selectedTreeItem.getParent().getChildren();
			//若 不是当前切片的最后一个脏器，则选择下一个脏器
			if(!children_viscera.get(children_viscera.size()-1).equals(selectedTreeItem)){
				tissueSliceTree.getSelectionModel().selectNext();
				flag = true;
			}else{
			//若是当前脏器的最后一个脏器，则选择下一个切片的第一个脏器
				//1)获得切片节点
				selectedTreeItem = selectedTreeItem.getParent();
				//2)获得下一个切片节点
				//切片节点列表
				ObservableList<TreeItem<String>> children_slice = rootNode_tissueSliceTree.getChildren();
				int index = children_slice.indexOf(selectedTreeItem);
				if(index+1 < children_slice.size()){
					//不是最后一个节点，选中下个切片节点第一脏器
					tissueSliceTree.getSelectionModel().select(children_slice.get(index+1).getChildren().get(0));
					flag = true;
				}else{
					//3）若已是最后一个切片，选择下个动物，并选中第一个切片的第一脏器
					histopathAnimalTable.getSelectionModel().selectNext();
					ObservableList<TreeItem<String>> children_slice2 = rootNode_tissueSliceTree.getChildren();
					if(null != children_slice2 && children_slice2.size() > 0){
						tissueSliceTree.getSelectionModel().select(children_slice2.get(0).getChildren().get(0));
						flag = true;
					}
				}
			}
		}else{
		//切片优先
			if(null != sliceCodeAnimalCodeSliceVisceraMapList && sliceCodeAnimalCodeSliceVisceraMapList.size() > 0){
				int index = 0;
				for(Map<String, Object> obj:sliceCodeAnimalCodeSliceVisceraMapList){
					String currentSliceCode = (String) obj.get("sliceCode");
					String currentSliceVisceraId = (String) obj.get("sliceVisceraId");
					String currentAnimalCode = (String) obj.get("animalCode");
					if(currentSliceCode.equals(sliceCode) && currentSliceVisceraId.equals(sliceVisceraId)
							&& currentAnimalCode.equals(animalCode)){
						break;
					}
					index++;
				}
				if(index+1 < sliceCodeAnimalCodeSliceVisceraMapList.size()){
					Map<String, Object> obj = sliceCodeAnimalCodeSliceVisceraMapList.get(index+1);
					String nextAnimalCode = (String) obj.get("animalCode");;
					String nextSliceVisceraId = (String) obj.get("sliceVisceraId");;
					String nextSliceCode = (String) obj.get("sliceCode");
					//先选中切片，再选中动物
					TreeItem<String> slicetreeItem = sliceCode2SliceTreeItemMap.get(nextSliceCode);
					ObservableList<TreeItem<String>> children = slicetreeItem.getChildren();
					for(TreeItem<String> treeitem:children){
						@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) treeitem.getGraphic().getUserData();
						String sliceViscersId = (String) map.get("sliceVisceraId");
						if(nextSliceVisceraId.equals(sliceViscersId)){
							tissueSliceTree.getSelectionModel().select(treeitem);
							break;
						}
					}
					
					ObservableList<HistopathAnimal> items = histopathAnimalTable.getItems();
					int i = 0;
					for(HistopathAnimal item:items){
						if(item.getAnimalCode().equals(nextAnimalCode)){
							histopathAnimalTable.getSelectionModel().select(item);
							histopathAnimalTable.scrollTo(i);
							break;
						}
						i++;
					}
					flag = true;
				}
			}
		}
		
		
		return flag;
	}


	private static HistopathCheckPage instance;
	public static HistopathCheckPage getInstance(){
		if(null == instance){
			instance = new HistopathCheckPage();
		}
		return instance;
	}
	
	public HistopathCheckPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//1.初始化 tabPane
		initTabPane();
		//2.初始化动物/脏器Tab
		initAnimalVisceraTab();
		//3.初始化组织学所见Tab
		initHistopathTab();
		//4.初始化手工录入 Tab
		initHandWordTab();
	}
//	/*
//	 * 初始化 解剖信息表（anatomyResultTable）
//	 */
//	private void initAnatomyResultTalbe() {
//		// 
//		anatomyResultTable.setItems(data_anatomyResultTable);
//		anatomyResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		//清空选择
//		anatomyResultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyCheck>(){
//
//			@Override
//			public void changed(ObservableValue<? extends AnatomyCheck> arg0, AnatomyCheck arg1,
//					AnatomyCheck arg2) {
//				anatomyResultTable.getSelectionModel().clearSelection();
//			}
//		});
//		visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("visceraName"));
//		findingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("finding"));
//	}
	

	/**
	 * 初始化手工录入 Tab
	 */
	private void initHandWordTab() {
		//初始化 11个  ListView
		init11ListView();
		inittgRadioButton();
		
		initVisceraTree();
		
		initAnimalCodeListView();
		
	}
	/**
	 * 初始化动物表
	 */
	private void initAnimalCodeListView() {
		animalCodeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					Integer gender = animalCode2GenderMap.get(newValue);
//					System.out.println(newValue);
//					System.out.println(gender);
					if(null != gender){
						updateVisceraTree(gender);
					}
				}
				updateDeathReasonLabel_1(newValue);
				updateHistopathCheckTable_1();
			}
			
		});
		
	}
	/**
	 * 更新脏器列表
	 */
	private void updateVisceraTree(int gender) {
		//更新树 
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();

		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance()
				.getTblTissueSliceIndexService().getAllVisceraCodeAndName(studyNo,gender);
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
				if(null != subVisceraCode){
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
				}
			}
		}
	}
	/**
	 * 初始化 脏器树
	 */
	private void initVisceraTree() {
		rootNode.setValue("");
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
						visceraCode = (String) map.get("visceraCode");
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
//					updateAnatomyFindingLabelText();
				}
				
				updateHistopathCheckTable_1();
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
//				updateAnatomyFindingLabelText();
			}
		});
	}
	
	/**根据脏器编号更新    解剖学所见部位
	 * @param visceraCode
	 */
	private void updateAnatomyPosListViewData(String visceraCode){
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();
		List<DictPathCommon> dictPathCommon1List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,1);
		if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
					data_anatomyPosListView.add(obj.getDescCn());
					anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
			}
		}
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
				}
			}});
	}
	
	/**根据脏器编号更新    解剖学所见(通用，特殊)
	 * @param visceraCode
	 */
	private void updateAnatomyFindingListViewData(String visceraCode){
		if(tongyongRadioButton.isSelected()){
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,1);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
//					if(obj.getSpecicalFlag() == 1){
						data_anatomyFindingListView_tesu.add(obj.getDescCn());
						anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
//					}
				}
			}
		}
	}
	/**
	 * 初始化 11个  ListView
	 */
	private void init11ListView() {
		animalCodeListView.setItems(data_animalCodeListView);
		animalCodeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
			}
		});
		
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
				updateHistopathCheckTable_1();
			}
		});
		
		//解剖所见(通用)
		anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		anatomyFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
				
				anatomyFindingListView.setUserData(true);
				if(null != newValue){
					anatomyFindingListView.setId(newValue);
				}else{
					anatomyFindingListView.setId("");
				}
				updateHistopathCheckTable_1();
			}
		});
		anatomyFindingListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				
			}
		});
		bodySufacePosListView.setItems(data_bodySufacePosListView);
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				bodySufacePosListView.setUserData(true);
				if(null != newValue){
					visceraTree.getSelectionModel().clearSelection();
					bodySufacePosListView.setId(newValue);
				}else{
					bodySufacePosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				
				updateHistopathCheckTable_1();
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
				
			}
		});
				
	}
	/**
	 * 初始化组织学所见Tab
	 */
	private void initHistopathTab() {
		//1.快速查找ComboBox
		initSerachComboBox();
		//2.0快速查找TextField
		initSearchTextField();
		//2.1初始化快速查找ListView 
		initSearchListView();
		//3.字典排序方式ComboBox
		initDictSortMethodComboBox();
		//4.异常类型 RadioButton
		initRadioButton();
		//5.组织学用语ListView
		initHistoPoListView();
		//6.病变描述ListView
		initLesionFindingListView();
		//7.肿瘤数量ComboBox及程度ComboBox
		initLevelComboBox();
		//8.组织学所见Table_1
		initHistopathResultTable();
		//9.肿瘤位置 RaidoButton
		initTumorPosRadio();
		//10.初始化发生日期
		initOccurDate();
		//11.病变程度
		initlevelComboBox();
		
		//12.初始化 visceraNameListView
		initVisceraNameListView();
	}
	
	/**
	 * 初始化 visceraNameListView
	 */
	private void initVisceraNameListView() {
		visceraNameListView.setItems(data_visceraNameListView);
		visceraNameListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(null != oldValue && null != newValue){
					//清空
					String text = animalCodeVisceraNameLabel.getText();
					int index = text.indexOf("所选脏器或组织：");
					if(index > -1){
						text = text.substring(0,index);
						text = text+"所选脏器或组织："+newValue;
						animalCodeVisceraNameLabel.setText(text);
					}
				}
			}});
	}
	
	private void initlevelComboBox() {
		levelComboBox.setItems(data_levelComboBox);
	}
	/**
	 * 更新病变程度levelComboBox
	 */
	private void updatelevelComboBox(){
		data_levelComboBox.clear();
		List<DictLevel> list = BaseService.getInstance().getDictLevelService().findAll();
		data_levelComboBox.add("");
		if(null != list && list.size() > 0){
			for(DictLevel obj:list){
				data_levelComboBox.add(obj.getLevel());
			}
		}
	}
	/**
	 * 初始化发生日期
	 */
	private void initOccurDate() {
		occurDatePicker = new DatePicker(Locale.CHINA);
		occurDatePicker.getTextField().setEditable(false);
		occurDatePicker.getTextField().setFocusTraversable(true);
		occurDatePicker.getTextField().setMaxWidth(100);
		occurDatePicker.getTextField().setMinWidth(100);
		occurDatePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		occurDatePicker.getCalendarView().todayButtonTextProperty().set("今天");
		occurDatePicker.getCalendarView().setShowWeeks(false);
		occurDatePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		occurDatePicker.setMaxWidth(100);
		tumorOccurDateHbox.getChildren().add(occurDatePicker);
		occurDatePicker.getTextField().setFocusTraversable(false);
		
	}
	/**
	 * 初始化肿瘤位置 RaidoButton
	 */
	private void initTumorPosRadio() {
		tumorPosRadio1.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
					Toggle newValue) {
				tumorCharacterPane.setVisible(false);
				tumorOccurPane.setVisible(false);
				if(tumorPosRadio1.isSelected()){
					tumorOccurPane.setVisible(true);
					occurDatePicker.getTextField().clear();
					String animalCode = getSelectedAnimalCode();
					if(null != studyNo && !"".equals(studyNo) && null != animalCode && !"".equals(animalCode)){
						TblAnatomyAnimal anatomyAnimal = BaseService.getInstance().getTblAnatomyAnimalService()
								.getByStudyNoAnimalCode(studyNo, animalCode);
						if(null != anatomyAnimal && null != anatomyAnimal.getAnatomyBeginTime()){
							occurDatePicker.getTextField().setText(DateUtil.dateToString(anatomyAnimal.getAnatomyBeginTime(), "yyyy-MM-dd"));
						}
					}
				}else{
					if(tumorPosRadio2.isSelected()){
						tumorCharacterPane.setVisible(true);
						tumorCharacterRadio1.setSelected(false);
						tumorCharacterRadio2.setSelected(false);
						tumorCharacterRadio3.setSelected(false);
					}
				}
				
			}
		});
		
	}
	/**
	 * 肿瘤数量ComboBox及程度ComboBox
	 */
	private void initLevelComboBox() {
		levelComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateHistopathCheckLabelText();
				}
			}
		});
		tumorNumberComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateHistopathCheckLabelText();
				}
			}
		});
		
	}
	/**
	 * 组织学所见Table_1
	 */
	private void initHistopathResultTable() {
		histopathResultTable.setItems(data_histopathResultTable);
//		histopathResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		visceraOrTissueNameCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("visceraOrTissueName"));
		tumorFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorFlag"));
		metastasisFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("metastasisFlag"));
		checkResultCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("checkResult"));
		primaryVisceraTumorCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("primaryVisceraTumor"));
		
		levelCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("level"));
		tumorNumCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorNum"));
		tumorPosCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorPos"));
		tumorOccurDateCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorOccurDate"));
		tumorCharacterCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorCharacter"));
		remarkCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("remark"));
		
		metastasisFlagCol.setCellFactory(new Callback<TableColumn<HistopathResult,String>,TableCell<HistopathResult,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<HistopathResult, String> call(
	    			 TableColumn<HistopathResult, String> param) {
	    		 TableCell<HistopathResult, String> cell =
	    				 new TableCell<HistopathResult, String>() {
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
		operateCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<HistopathResult, Boolean>, TableCell<HistopathResult, Boolean>>() {

            public TableCell<HistopathResult, Boolean> call(TableColumn<HistopathResult, Boolean> p) {

            	HyperlinkCell<HistopathResult, Boolean> cell = new HyperlinkCell<HistopathResult, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * 病变描述ListView
	 */
	private void initLesionFindingListView() {
		
		lesionFindingListView.setItems(data_lesionFindingListView);
		lesionFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				lesionFindingListView.setUserData(true);
				if(null != newValue){
					lesionFindingListView.setId(newValue);
				}else{
					lesionFindingListView.setId("");
				}
				// 更新 histopathCheckLabel 值
				updateHistopathCheckLabelText();
			}
		});
		
		lesionFindingListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("病变描述");
			}
		});
	}
	/**
	 * 组织学用语ListView
	 */
	private void initHistoPoListView() {
		
		histoPoListView.setItems(data_histoPoListView);
		histoPoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				histoPoListView.setUserData(true);
				if(null != newValue){
					histoPoListView.setId(newValue);
				}else{
					histoPoListView.setId("");
				}
				// 更新 histopathCheckLabel 值
				updateHistopathCheckLabelText();
			}
		});
		
		histoPoListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
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
				searchComboBox.getSelectionModel().select("组织学用语");
			}
		});
	}

	/**
	 * 更新 histopathCheckLabel 值
	 */
	private void updateHistopathCheckLabelText(){
		String finding = "组织学所见：";
		
		finding = finding + (histoPoListView.getSelectionModel().getSelectedItem() == null ?
				"":histoPoListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (lesionFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		if(null != primaryVisceraName || null != primaryTumor){
			finding = finding+"  原发于";
			finding = finding + (primaryVisceraName == null ?
					"":primaryVisceraName+" ");
			finding = finding + (primaryTumor == null ?
					"":primaryTumor+"");
		}
		if(!"组织学所见：".equals(finding.trim())){
			if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
				String tumorNumberStr = tumorNumberComboBox.getSelectionModel().getSelectedItem();
				if(NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
					finding = finding +"（ 数量 "+tumorNumberStr+"个 ）";
				}
			}else if(noTumorRadio.isSelected()){
				String selectedItem = levelComboBox.getSelectionModel().getSelectedItem();
				if(null != selectedItem && !"".equals(selectedItem)){
					finding = finding +"（ 程度 "+selectedItem+" ）";
				}
			}
			
		}
		histopathCheckLabel.setText(finding);
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
	}
	
	/**快速查找，选择指定项，并移到指定行
	 * @param newValue
	 */
	private void selectandScrollToValue(String newValue) {
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		if("组织学用语".equals(selectItem)){
			histoPoListView.getSelectionModel().select(newValue);
			int index=histoPoListView.getSelectionModel().getSelectedIndex();
			histoPoListView.getFocusModel().focus(index);
			histoPoListView.scrollTo(getIndex(data_histoPoListView,newValue));
		}else{
			lesionFindingListView.getSelectionModel().select(newValue);
			int index=lesionFindingListView.getSelectionModel().getSelectedIndex();
			lesionFindingListView.getFocusModel().focus(index);
			lesionFindingListView.scrollTo(getIndex(data_lesionFindingListView,newValue));
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
	 * 初始化快速查找ComboBox
	 */
	private void initSerachComboBox(){
		searchComboBox.getSelectionModel().select(0);
		histoPoListView.setStyle("-fx-border-color:blue;");
		searchComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null != newValue){
					searchTextField.setPromptText("快速检索文本(拼音首字母)");
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
		if("组织学用语".equals(listName)){
			histoPoListView.setStyle("-fx-border-color:blue;");
		}else{
			lesionFindingListView.setStyle("-fx-border-color:blue;");
		}
	}
	/**清除ListView 的边框颜色
	 * @param listName
	 */
	private void clearStyle(String listName){
		if("组织学用语".equals(listName)){
			histoPoListView.setStyle("-fx-border-color: #919191;");
		}else{
			lesionFindingListView.setStyle("-fx-border-color: #919191;");
		}
	}
	/**
	 * 异常类型 RadioButton
	 */
	private void initRadioButton() {
		//异常
		abnormalRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(abnormalRadio.isSelected()){
					//非肿瘤选中
					if(null ==noTumorRadio.getToggleGroup().getSelectedToggle()){
						noTumorRadio.setSelected(true);
					}
					
				}else{
					noTumorRadio.setSelected(false);
					lxTumorRadio.setSelected(false);
					exTumorRadio.setSelected(false);
					noMetastasisRadio.setSelected(false);
					qinxiRadio.setSelected(false);
					metastasisRadio.setSelected(false);
					
					updateTumorNumberComboBoxState();
				}
				
				if(null !=newValue && newValue ){
					histoPoListView.setDisable(false);
					lesionFindingListView.setDisable(false);
				}else{
					//未见异常，组织学用语和病变描述 不可用
					histoPoListView.setDisable(true);
					lesionFindingListView.setDisable(true);
				}
			}});
		
		//恶性肿瘤
		exTumorRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(exTumorRadio.isSelected()){
					if(null == noMetastasisRadio.getToggleGroup().getSelectedToggle()){
						noMetastasisRadio.setSelected(true);
					}
				}else{
					noMetastasisRadio.setSelected(false);
					qinxiRadio.setSelected(false);
					metastasisRadio.setSelected(false);
				}
				
			}
		});
		//非肿瘤、良性肿瘤、恶性肿瘤
		exTumorRadio.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle oldValue, Toggle newValue) {
				if(null != newValue){
					abnormalRadio.setSelected(true);
				}
				
				if(null == oldValue || null == newValue || ((RadioButton)oldValue).getText().equals("非肿瘤")
						|| ((RadioButton)newValue).getText().equals("非肿瘤")){
					//更新 病变描述语ListView  
					updateLesionFindingListView();
					//更新肿瘤数量ComboBox状态
					updateTumorNumberComboBoxState();
					
					updateTumorPosPaneState();
				}
			}

		});
		
		//转移
		metastasisRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(metastasisRadio.isSelected()){
					primaryViscerHyperlink.setVisible(true);
				}else{
					primaryViscerHyperlink.setVisible(false);
					
					primaryVisceraCode = null;	//源发脏器编号
					primaryVisceraName = null;	//源发脏器名称
					primaryTumor = null;	
					
					updateHistopathCheckLabelText();
				}
				
			}
		});
		
		metastasisRadio.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newValue) {
				if(null != newValue){
					exTumorRadio.setSelected(true);
				}
				
			}
		});
		
		noTumorRadio.setSelected(false);
		lxTumorRadio.setSelected(false);
		exTumorRadio.setSelected(false);
		noMetastasisRadio.setSelected(false);
		qinxiRadio.setSelected(false);
		metastasisRadio.setSelected(false);
		
		primaryViscerHyperlink.setVisible(false);
	}
	
	/**
	 * 更新 tumorPosPane hidden与否
	 */
	private void updateTumorPosPaneState(){
		if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
			tumorPosPane.setVisible(true);
			if(null != visceraCode && !"".equals(visceraCode)){
				boolean exist = BaseService.getInstance().getTblSuperficialTumorVisceraService().isExistByVisceraCode(visceraCode);
				if(exist){
					tumorPosRadio1.setSelected(true);
				}
			}
		}else{
			tumorPosPane.setVisible(false);
			tumorPosRadio1.setSelected(false);
			tumorPosRadio2.setSelected(false);
		}
	}

	/**
	 * 更新肿瘤数量ComboBox状态及程度ComboBox状态
	 */
	private void updateTumorNumberComboBoxState() {
		hbox.getChildren().remove(tumorNumberLabel);
		hbox.getChildren().remove(tumorNumberComboBox);
		hbox.getChildren().remove(levelLabel);
		hbox.getChildren().remove(levelComboBox);
		levelComboBox.getSelectionModel().select("");
		tumorNumberComboBox.getSelectionModel().select(0);
		if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
//			tumorNumberComboBox.setDisable(false);
//			tumorNumberLabel.setVisible(true);
//			tumorNumberComboBox.setVisible(true);
			String selectedItem = tumorNumberComboBox.getSelectionModel().getSelectedItem();
			if(null == selectedItem || "".equals(selectedItem) || "0".equals(selectedItem)){
				tumorNumberComboBox.getSelectionModel().select(0);
			}
			
//			levelLabel.setVisible(false);
//			levelComboBox.setVisible(false);
//			levelComboBox.getSelectionModel().select("");
			hbox.getChildren().add(0, tumorNumberLabel);
			hbox.getChildren().add(1, tumorNumberComboBox);
		}else if(noTumorRadio.isSelected()){
			//非肿瘤
//			tumorNumberLabel.setVisible(false);
//			tumorNumberComboBox.setVisible(false);
//			tumorNumberComboBox.getSelectionModel().select("0");
			
//			levelLabel.setVisible(true);
//			levelComboBox.setVisible(true);
			levelComboBox.getSelectionModel().select("");
			hbox.getChildren().add(0, levelLabel);
			hbox.getChildren().add(1, levelComboBox);
		}else{
			//未见异常
//			tumorNumberLabel.setVisible(false);
//			tumorNumberComboBox.setVisible(false);
//			tumorNumberComboBox.getSelectionModel().select("0");
//			
//			levelLabel.setVisible(false);
//			levelComboBox.setVisible(false);
//			levelComboBox.getSelectionModel().select("");
		}
		
	}
	/**
	 * 初始化动物/脏器Tab
	 */
	private void initAnimalVisceraTab() {
		//1.初始化状态ComboBox
		initStateCombobox();
		//3.初始化显示内容ComboBox
		initContentComboBox();
		//4.初始化排序方式ComboBox
		initSortMethodComboBox();
		//5.初始化动物TableView
		initHistopathAnimalTable();
		//6.初始化组织切片编号tree
		initTissueSliceTree();
		//7.初始化镜检结果TableView
		initHistopathCheckTable();
		//8.初始化检查顺序RadioButton
		initCheckOrderRadioButton();
		
	}
	
	/**
	 * 初始化 histopathCheckTable
	 */
	private void initHistopathCheckTable() {
		histopathCheckTable.setItems(data_histopathCheckTable2);
//		histopathCheckTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol_1.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("animalCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("visceraOrTissueName"));
		checkResultCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkResult"));
		checkTimeCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkTime"));
		
		tumorFlagCol_0.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorFlag"));
		metastasisFlagCol_0.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("metastasisFlag"));
		primaryVisceraTumorCol_0.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("primaryVisceraTumor"));
		levelCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("level"));
		tumorNumCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorNum"));
		tumorPosCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorPos"));
		tumorOccurDateCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorOccurDate"));
		tumorCharacterCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("tumorCharacter"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("remark"));
	}

	/**
	 *  初始化组织切片编号tree
	 */
	private void initTissueSliceTree() {
		rootNode_tissueSliceTree.setValue("1");
		rootNode_tissueSliceTree.setGraphic(new Label("组织取材编号"));
		rootNode_tissueSliceTree.setExpanded(true);
		tissueSliceTree.setRoot(rootNode_tissueSliceTree);
		tissueSliceTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> oldValue, TreeItem<String> newValue) {
				if(null != newValue){
					if(contentIndex == 3 || contentIndex == 4){
						updateHistopathCheckTable_1();
					}
					if(sliceRadioButton.isSelected() && newValue.isLeaf()){
					//切片优先，切换动物列表
						String newSliceVisceraId = "";
						@SuppressWarnings("unchecked")
						Map<String,Object> map = (Map<String, Object>) newValue.getGraphic().getUserData();
						newSliceVisceraId = (String) map.get("sliceVisceraId");
						//更新动物列表
						updateHistopathAnimalTable(studyNo,animalCodeList,newSliceVisceraId);
					}
				}
			}

		});
		
	}
	
	/**
	 * 初始化动物TableView histopathAnimalTable
	 */
	private void initHistopathAnimalTable() {
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("genderStr"));
		stateCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("state"));
		hasAbnormalCol.setCellValueFactory(new PropertyValueFactory<HistopathAnimal,String>("hasAbnormal"));
		histopathAnimalTable.setItems(data_histopathAnimalTable);
		histopathAnimalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		genderCol.setCellFactory(new Callback<TableColumn<HistopathAnimal,String>,TableCell<HistopathAnimal,String>>(){
			
			@Override
			public TableCell<HistopathAnimal, String> call(
					TableColumn<HistopathAnimal, String> param) {
				TableCell<HistopathAnimal, String> cell =
						new TableCell<HistopathAnimal, String>() {
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
		hasAbnormalCol.setCellFactory(new Callback<TableColumn<HistopathAnimal,String>,TableCell<HistopathAnimal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<HistopathAnimal, String> call(
	    			 TableColumn<HistopathAnimal, String> param) {
	    		 TableCell<HistopathAnimal, String> cell =
	    				 new TableCell<HistopathAnimal, String>() {
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
		histopathAnimalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HistopathAnimal>(){

			@Override
			public void changed(ObservableValue<? extends HistopathAnimal> arg0,
					HistopathAnimal arg1, HistopathAnimal newValue) {
				
				String animalCode = null;
				Integer gender = null;
				if(null != newValue){
					animalCode = newValue.getAnimalCode();
					gender = newValue.getGender();
					if(contentIndex == 2 || contentIndex == 4){
						updateHistopathCheckTable_1();
					}
				}
				//动物优先时，切换  切片编号树
				if(animalRadioButton.isSelected()){
					// 更新切片编号tree
					updateTissueSliceTree(animalCode,gender);
				}
				
				//更新致死原因Label
				updateDeathReasonLabel(animalCode);
			}

		});
	}
	
	/**更新致死原因Label（）
	 * @param animalCode
	 */
	public void updateDeathReasonLabel_01(){
		int selectedIndex = tabPane2.getSelectionModel().getSelectedIndex();
		if(selectedIndex == 0){
			HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
			if(null != animal){
				updateDeathReasonLabel(animal.getAnimalCode());
			}
		}else{
			String animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
			updateDeathReasonLabel_1(animalCode);
		}
	}
	
	/**更新致死原因Label
	 * @param animalCode
	 */
	private void updateDeathReasonLabel(String animalCode) {
		if(null != animalCode){
			String reason = BaseService.getInstance().getTblDeadAnimalDeathReasonService().getDeathReason(studyNo,animalCode);
			if(null != reason){
				deathReasonLabel.setText("当前动物致死原因："+reason);
			}else{
				deathReasonLabel.setText("当前动物致死原因：");
			}
		}else{
			deathReasonLabel.setText("当前动物致死原因：");
		}
		
	}
	/**更新致死原因Label
	 * @param animalCode
	 */
	private void updateDeathReasonLabel_1(String animalCode) {
		if(null != animalCode){
			String reason = BaseService.getInstance().getTblDeadAnimalDeathReasonService().getDeathReason(studyNo,animalCode);
			if(null != reason){
				deathReasonLabel_1.setText("当前动物致死原因："+reason);
			}else{
				deathReasonLabel_1.setText("当前动物致死原因：");
			}
		}else{
			deathReasonLabel_1.setText("当前动物致死原因：");
		}
		
	}

	/**
	 * 初始化检查顺序RadioButton
	 */
	private void initCheckOrderRadioButton() {
		animalRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				updateAnimalTableAndSliceTree(animalCodeList,tissueSliceSnIdList);
			}});
		
	}

	/**
	 * 初始化排序方式ComboBox
	 */
	private void initSortMethodComboBox() {
		data_sortMethodComboBox.add("动物顺序");
		data_sortMethodComboBox.add("脏器顺序");
		sortMethodComboBox.setItems(data_sortMethodComboBox);
		sortMethodComboBox.getSelectionModel().select(0);
		sortMethodComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue ){
					sortMethod = newValue.intValue()+1;
					updateHistopathCheckTable();
				}
				
			}});
	}

	/**
	 * 初始化显示内容ComboBox
	 */
	private void initContentComboBox() {
		data_contentComboBox.add("所有动物，所有脏器");
		data_contentComboBox.add("当前动物，所有脏器");
		data_contentComboBox.add("所有动物，当前脏器");
		data_contentComboBox.add("当前动物，当前脏器");
		contentComboBox.setItems(data_contentComboBox);
		contentComboBox.getSelectionModel().select(0);
		contentComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue ){
					contentIndex = newValue.intValue()+1;
					
					updateHistopathCheckTable_1();
				}
			}});
	}

	/**
	 * 初始化状态ComboBox
	 */
	private void initStateCombobox() {
		data_stateComboBox.add("全部");
		data_stateComboBox.add("未开始检查");
		data_stateComboBox.add("已开始检查");
//		data_stateComboBox.add("已提交复核");
//		data_stateComboBox.add("正在复核");
//		data_stateComboBox.add("复核完成");
//		data_stateComboBox.add("已完成检查");
		stateComboBox.setItems(data_stateComboBox);
		stateComboBox.getSelectionModel().select(0);
		stateComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue ){
					state = newValue.intValue();
					updateHistopathAnimalTable2();
				}
				
			}});
	}

	/**
	 * 初始化 字典排序ComboBox
	 */
	private void initDictSortMethodComboBox() {
		//  字典排序
		dictSortMethodComboBox.getSelectionModel().select(0);
		dictSortMethod = 1;
		dictSortMethodComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					dictSortMethod = newValue.intValue()+1;
					updateHistoPoListView();
					updateLesionFindingListView();
				}
				
			}
		});
	}
	/**
	 * 初始化 tabPane
	 */
	private void initTabPane() {
		tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue && newValue.intValue() == 1){
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							//根据动物编号和脏器  更新整个解剖所见Tab 
							updateHistopathCheckTab();
						}});
					hasEdit = false;
				}else{
					//
					if(hasEdit){
						updateHistopathCheckTable();
					}
					
					hasEdit = false;
				}
				
			}

		});
		
	}
	/**
	 * 根据动物编号和脏器  更新整个解剖所见Tab
	 */
	private void updateHistopathCheckTab() {
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			String animalCode = "";
			String visceraOrTissueName = "";
			visceraCode = "";
			sliceVisceraId = "";
			
			String sliceCode = "";
			
			HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
			if(null != selectedItem){
				animalCode = selectedItem.getAnimalCode();
			}
			
			TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
			if(null != selectedTreeItem && selectedTreeItem.isLeaf()){
				visceraOrTissueName = ((Label)selectedTreeItem.getGraphic()).getText();
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
				visceraCode = (String) map.get("visceraCode");
				sliceVisceraId = (String) map.get("sliceVisceraId");
				if(null == visceraCode || "".equals(visceraCode)){
					visceraCode = "00000000000";
				}
				
				sliceCode = (String) map.get("sliceCode");
			}
			String text = "";
			if(null != animalCode && !"".equals(animalCode) && 
					null != visceraOrTissueName && !"".equals(visceraOrTissueName)){
				text = text+"动物编号："+animalCode+"    ";
				text = text+"所选脏器或组织："+visceraOrTissueName;
//				if(!text.equals(animalCodeVisceraNameLabel.getText())){
//				}
					animalCodeVisceraNameLabel.setText(text);
					//1.更新 组织学用语ListView
					updateHistoPoListView();
					//2.
					noAbnormalRadio.setSelected(true);
					abnormalRadio.setSelected(true);
					//3.更新组织学所见表（tab2）
					updateHistopathResultTable(animalCode,sliceVisceraId);
			}else{
				//清空
				text = text+"动物编号："+animalCode+"    所选脏器或组织：";
				animalCodeVisceraNameLabel.setText(text);
				//1.更新 组织学用语ListView
				updateHistoPoListView();
				//2.
				noAbnormalRadio.setSelected(true);
				abnormalRadio.setSelected(true);
				//3.更新组织学所见表（tab2）
				updateHistopathResultTable(animalCode,sliceVisceraId);
			}
			
			if(!sliceCode.contains("g")){
				//更新脏器ListView
				updateVisceraListView(animalCode,sliceVisceraId,visceraCode,visceraOrTissueName);
			}else{
				//更新脏器ListView(非常规切片，不存在子脏器问题)
				updateVisceraListView(animalCode,"",visceraCode,visceraOrTissueName);
			}
			
		}else{
			String animalCode = "";
			String visceraOrTissueName = "";
			visceraCode = "";
			
			//1.动物
			animalCode = animalCodeListView.getSelectionModel().getSelectedItem();
			if(null == animalCode){
				animalCode = "";
			}
			//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
			String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			String visceraName = null; 
			String subVisceraName = null; 
			String subVisceraCode = null; 
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
				if(treeSelectedItem.getParent() == rootNode){
					visceraCode = (String) visceraMap.get("visceraCode");
					visceraName = (String) visceraMap.get("visceraName");
				}else{
					visceraCode = (String) visceraMap.get("visceraCode");
					visceraName = (String) visceraMap.get("visceraName");
					subVisceraName = (String) visceraMap.get("subVisceraName");
					subVisceraCode = (String) visceraMap.get("subVisceraCode");
				}
			}else{
				if(null != bodySurfacePos){
					visceraCode = "00000000000";
				}
			}
			visceraOrTissueName = bodySurfacePos != null ? bodySurfacePos:(subVisceraName != null ? subVisceraName:visceraName);
			if(null != anatomyPos){
				visceraOrTissueName+=" "+anatomyPos;
			}
			if(null != anatomyFingding){
				visceraOrTissueName+=" "+anatomyFingding;
			}
			//3.查询组织取材编号脏器Id
			String anatomyFindingCode = null;
			if(null != anatomyFingding){
				if(tesuRadioButton.isSelected()){
					DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon2.getItemCode();
				}else{
					DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFingding);
					anatomyFindingCode = dictPathCommon3.getItemCode();
				}
			}
			
			sliceVisceraId = BaseService.getInstance().getTblHistopathCheckService()
					.getSliceVisceraId(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
			//4.
			String text = "";
			if(null != animalCode && !"".equals(animalCode) && 
					null != visceraOrTissueName && !"".equals(visceraOrTissueName)){
				text = text+"动物编号："+animalCode+"    ";
				text = text+"所选脏器或组织："+visceraOrTissueName;
//				if(!text.equals(animalCodeVisceraNameLabel.getText())){
//				}
					animalCodeVisceraNameLabel.setText(text);
					//1.更新 组织学用语ListView
					updateHistoPoListView();
					//2.
					noAbnormalRadio.setSelected(true);
					abnormalRadio.setSelected(true);
					//3.更新组织学所见表（tab2）
					if(null != sliceVisceraId){
						updateHistopathResultTable(animalCode,sliceVisceraId);
					}else{
						updateHistopathResultTable(animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
					}
			}else{
				//清空
				text = text+"动物编号："+animalCode+"    所选脏器或组织：";
				animalCodeVisceraNameLabel.setText(text);
				//1.更新 组织学用语ListView
				updateHistoPoListView();
				//2.
				noAbnormalRadio.setSelected(true);
				abnormalRadio.setSelected(true);
				//3.更新组织学所见表（tab2）
				updateHistopathResultTable(animalCode,sliceVisceraId);
			}
			
			//更新脏器ListView
			updateVisceraListView(animalCode,"",visceraCode,visceraOrTissueName);
		}
		
//		updateAnatomyResultTableData(studyNo,animalCode);
	}
	
	/**更新脏器列表
	 * @param animalCode
	 * @param sliceVisceraId2
	 * @param visceraCode2
	 * @param visceraOrTissueName 
	 */
	private void updateVisceraListView(String animalCode, String sliceVisceraId2,
			String visceraCode2, String visceraOrTissueName) {
		// TODO Auto-generated method stub
		data_visceraNameListView.clear();
		visceraMapList.clear();
		if(!isNull(animalCode) && !isNull(sliceVisceraId2) 
				&& !isNull(visceraCode2) && !"00000000000".equals(visceraCode2) ){
			visceraMapList = BaseService.getInstance().getTblTissueSliceBatchService()
					.getMapListByStudyNoAnimalCodeSliceVisceraId(studyNo,animalCode,sliceVisceraId2);
			if(null != visceraMapList && visceraMapList.size() > 0){
				for(Map<String,Object> obj:visceraMapList){
					String currentVisceraName = (String) obj.get("visceraName");
					String subVisceraName = (String) obj.get("subVisceraName");
					
					if(null != subVisceraName && !"".equals(subVisceraName.trim())){
						currentVisceraName = subVisceraName;
					}
					data_visceraNameListView.add(currentVisceraName);
				}
				visceraNameListView.getSelectionModel().select(0);
			}
		}else if(!isNull(visceraOrTissueName)){
			data_visceraNameListView.add(visceraOrTissueName);
			visceraNameListView.getSelectionModel().select(0);
		}
	}
	
	/**
	 * @return
	 */
	private boolean isNull(String str){
		if(null == str || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	private void updateHistopathResultTable(String animalCode, String visceraCode2,
			String subVisceraCode, String anatomyFindingCode, String bodySurfacePos) {
		data_histopathResultTable.clear();
		//3.查询数据准备数据
		if(null != animalCode && !"".equals(animalCode)  ){
			List<TblHistopathCheck> tblHistopathCheckList = BaseService.getInstance().getTblHistopathCheckService()
					.getListByStudyNoAnimalCodeSliceViscera(studyNo,animalCode,visceraCode,subVisceraCode,anatomyFindingCode,bodySurfacePos);
			if(null != tblHistopathCheckList){
				String visceraOrTissueName = getselectVisceraOrTissueName();
				for(TblHistopathCheck obj:tblHistopathCheckList){
					String tumorOccurDate = DateUtil.dateToString(obj.getTumorOccurDate(), "yyyy-MM-dd");
					
					String visceraName = "";
					if(null != obj.getSubVisceraName() && !"".equals(obj.getSubVisceraName().trim())){
						visceraName = obj.getSubVisceraName();
					}else if(null != obj.getVisceraName() && !"".equals(obj.getVisceraName().trim())){
						visceraName = obj.getVisceraName();
					}else{
						visceraName = visceraOrTissueName;
					}
					
					data_histopathResultTable.add(new HistopathResult(obj.getId(),obj.getIsNoAbnormal(),obj.getTumorFlag()
							,obj.getMetastasisFlag(),obj.getHistoPos(),obj.getLesionFinding()
							,obj.getPrimaryViscera(),obj.getPrimaryTumor(),true,
							obj.getLevel(),obj.getTumorNum(),obj.getTumorPos(),tumorOccurDate ,obj.getTumorCharacter(),obj.getRemark(),visceraName));
				}
			}
		}
		
	}
	/**
	 * 更新组织学所见表（tab0）
	 */
	private void updateHistopathCheckTable(){
		data_histopathCheckTable.clear();
		data_histopathCheckTable2.clear();
		histopathCheckTable.setItems(data_histopathCheckTable2);
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService()
				.getMapListByStudyNoSortMethord(studyNo,sortMethod);
		if(null != mapList && mapList.size() > 0){
//			histopathCheck.animalCode,histopathCheck.tissueSliceVisceraRecordId,histopathCheck.isNoAbnormal,histopathCheck.tumorFlag,
//			 * histopathCheck.metastasisFlag,histopathCheck.histoPos,histopathCheck.lesionFinding,histopathCheck.primaryViscera,
//			 * histopathCheck.primaryTumor,convert(varchar(10),histopathCheck.operateTime,120) as operateTime,visceraOrTissueName
			for(Map<String,Object> map :mapList){
				Integer historyFlag = (Integer) map.get("historyFlag");
				if(null != historyFlag && historyFlag > 0){
					continue;
				}
				String animalCode = (String) map.get("animalCode");
				String tissueSliceVisceraRecordId = (String) map.get("tissueSliceVisceraRecordId");
				Integer isNoAbnormal = (Integer) map.get("isNoAbnormal");
				Integer tumorFlag = (Integer) map.get("tumorFlag");
				Integer metastasisFlag = (Integer) map.get("metastasisFlag");
				
				String histoPos = (String) map.get("histoPos");
				String lesionFinding = (String) map.get("lesionFinding");
				String operateTime = (String) map.get("operateTime");
				String visceraOrTissueName = (String) map.get("visceraOrTissueName");
				
				String primaryViscera = (String) map.get("primaryViscera");
				String primaryTumor = (String) map.get("primaryTumor");
				String level = (String) map.get("level");
				Integer tumorNum = (Integer) map.get("tumorNum");
				Integer tumorPos = (Integer) map.get("tumorPos");
				String tumorOccurDate = (String) map.get("tumorOccurDate");
				Integer tumorCharacter = (Integer) map.get("tumorCharacter");
				String remark = (String) map.get("remark");
				String visceraCode = (String) map.get("visceraCode");
				data_histopathCheckTable.add(new HistopathCheck(animalCode,visceraOrTissueName,operateTime,tissueSliceVisceraRecordId,
						isNoAbnormal,tumorFlag,metastasisFlag,histoPos,lesionFinding,
						primaryViscera,primaryTumor,level,tumorNum,tumorPos,tumorOccurDate,tumorCharacter,remark,visceraCode
						));
			}
			updateHistopathCheckTable_1();
		}
		
	}
	
	/**
	 * 更新组织学所见表（tab0）,只是增加过滤条件，不查询数据库
	 */
	private void updateHistopathCheckTable_1(){
		
		if (data_histopathCheckTable.size() > 0){
			data_histopathCheckTable2.clear();
			histopathCheckTable.setItems(data_histopathCheckTable2);
			if(contentIndex == 1){
				//所有动物 所有脏器
				histopathCheckTable.setItems(data_histopathCheckTable);
			}else if(contentIndex == 2){
				//当前动物 所有脏器
//				HistopathAnimal animal = histopathAnimalTable.getSelectionModel().getSelectedItem();
				String animalCode = getSelectedAnimalCode();
				if(null != animalCode){
					for(HistopathCheck obj:data_histopathCheckTable){
						if(obj.getAnimalCode().equals(animalCode)){
							data_histopathCheckTable2.add(obj);
						}
					}
				}
			}else if(contentIndex == 3){
				//所有动物 当前脏器
				String visceraOrTissueName = getselectVisceraOrTissueName();
				String visceraCode = getselectVisceraCode();
				if(null != visceraOrTissueName ){
					for(HistopathCheck obj:data_histopathCheckTable){
						if(obj.getVisceraOrTissueName().trim().equals(visceraOrTissueName.trim()) ||
								obj.getVisceraCode().equals(visceraCode)){
							data_histopathCheckTable2.add(obj);
						}
					}
				}
			}else if(contentIndex == 4){
				//当前动物 当前脏器
				String animalCode = getSelectedAnimalCode();
				String visceraOrTissueName = getselectVisceraOrTissueName();
				String visceraCode = getselectVisceraCode();
				if(null != animalCode && null != visceraOrTissueName ){
					for(HistopathCheck obj:data_histopathCheckTable){
						if((obj.getVisceraOrTissueName().trim().equals(visceraOrTissueName.trim()) || obj.getVisceraCode().equals(visceraCode))
								&& obj.getAnimalCode().equals(animalCode)  ){
							data_histopathCheckTable2.add(obj);
						}
					}
				}
			}
		}
	}
	/**
	 * 获取当前选中的visceraCode
	 */
	private String getselectVisceraCode(){
		String visceraCode = null;
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
			if( null != selectedTreeItem && selectedTreeItem.isLeaf()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
				visceraCode = (String) map.get("visceraCode");
			}
		}else{
			//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
					visceraCode = (String) visceraMap.get("visceraCode");
			}
		}
		
		return visceraCode;
	}
	/**
	 * 获取当前选中的VisceraorTissueName
	 */
	private String getselectVisceraOrTissueName(){
		String visceraOrTissueName = null;
		if(tabPane2.getSelectionModel().getSelectedIndex() == 0){
			TreeItem<String> selectedTreeItem = tissueSliceTree.getSelectionModel().getSelectedItem();
			if( null != selectedTreeItem && selectedTreeItem.isLeaf()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) selectedTreeItem.getGraphic().getUserData();
				visceraOrTissueName = (String) map.get("visceraOrTissueName");
			}
		}else{
			//2.脏器  或   脏器 解剖所见  或脏器 解剖学所见部位 解剖所见   或  体表部位  解剖所见
			TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
			String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
			String anatomyFingding = anatomyFindingListView.getSelectionModel().getSelectedItem();
			String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			String visceraName = null; 
			String subVisceraName = null; 
			
			Map<String,Object> visceraMap = null;
			if(null != treeSelectedItem){
				visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
				
				if(treeSelectedItem.getParent() == rootNode){
					visceraName = (String) visceraMap.get("visceraName");
				}else{
					visceraName = (String) visceraMap.get("visceraName");
					subVisceraName = (String) visceraMap.get("subVisceraName");
				}
			}
			visceraOrTissueName = bodySurfacePos != null ? bodySurfacePos:(subVisceraName != null ? subVisceraName:visceraName);
			if(null != anatomyPos){
				visceraOrTissueName+=" "+anatomyPos;
			}
			if(null != anatomyFingding){
				visceraOrTissueName+=" "+anatomyFingding;
			}
		}
		
		return visceraOrTissueName;
	}
	/**
	 * 更新组织学所见表（tab1）
	 */
	void updateHistopathResultTable(String animalCode,String sliceVisceraId){
		data_histopathResultTable.clear();
		//3.查询数据准备数据
		if(null != animalCode && !"".equals(animalCode) && null != sliceVisceraId && !"".equals(sliceVisceraId)  ){
			List<TblHistopathCheck> tblHistopathCheckList = BaseService.getInstance().getTblHistopathCheckService()
					.getListByStudyNoAnimalCodeSliceVisceraId(studyNo,animalCode,sliceVisceraId);
			if(null != tblHistopathCheckList){
				String visceraOrTissueName = getselectVisceraOrTissueName();
				for(TblHistopathCheck obj:tblHistopathCheckList){
					String tumorOccurDate = DateUtil.dateToString(obj.getTumorOccurDate(), "yyyy-MM-dd");
					
					String visceraName = "";
					if(null != obj.getSubVisceraName() && !"".equals(obj.getSubVisceraName().trim())){
						visceraName = obj.getSubVisceraName();
					}else if(null != obj.getVisceraName() && !"".equals(obj.getVisceraName().trim())){
						visceraName = obj.getVisceraName();
					}else{
						visceraName = visceraOrTissueName;
					}
					
					data_histopathResultTable.add(new HistopathResult(obj.getId(),obj.getIsNoAbnormal(),obj.getTumorFlag()
							,obj.getMetastasisFlag(),obj.getHistoPos(),obj.getLesionFinding()
							,obj.getPrimaryViscera(),obj.getPrimaryTumor(),true,
							obj.getLevel(),obj.getTumorNum(),obj.getTumorPos(),tumorOccurDate ,obj.getTumorCharacter(),obj.getRemark(),visceraName));
				}
			}
		}
		
	}
	/**
	 * 更新组织学所见表（tab1）
	 */
	void updateHistopathResultTable(String sliceVisceraId){
		data_histopathResultTable.clear();
		
		String animalCode = getSelectedAnimalCode();
		if(null != animalCode && !"".equals(animalCode) && null != sliceVisceraId && !"".equals(sliceVisceraId)  ){
			//3.查询数据准备数据
			List<TblHistopathCheck> tblHistopathCheckList = BaseService.getInstance().getTblHistopathCheckService()
					.getListByStudyNoAnimalCodeSliceVisceraId(studyNo,animalCode,sliceVisceraId);
			if(null != tblHistopathCheckList){
				String visceraOrTissueName = getselectVisceraOrTissueName();
				for(TblHistopathCheck obj:tblHistopathCheckList){
					String tumorOccurDate = DateUtil.dateToString(obj.getTumorOccurDate(), "yyyy-MM-dd");
					String visceraName = "";
					if(null != obj.getSubVisceraName() && !"".equals(obj.getSubVisceraName().trim())){
						visceraName = obj.getSubVisceraName();
					}else if(null != obj.getVisceraName() && !"".equals(obj.getVisceraName().trim())){
						visceraName = obj.getVisceraName();
					}else{
						visceraName = visceraOrTissueName;
					}
					data_histopathResultTable.add(new HistopathResult(obj.getId(),obj.getIsNoAbnormal(),obj.getTumorFlag()
							,obj.getMetastasisFlag(),obj.getHistoPos(),obj.getLesionFinding()
							,obj.getPrimaryViscera(),obj.getPrimaryTumor(),true,
							obj.getLevel(),obj.getTumorNum(),obj.getTumorPos(),tumorOccurDate ,obj.getTumorCharacter(),obj.getRemark(),visceraName));
				}
			}
		}
		
	}
	
	/**更新 组织学用语ListView
	 * @param visceraCode
	 */
	private void updateHistoPoListView() {
		histoPoListView.getSelectionModel().clearSelection();
		data_histoPoListView.clear();
		pyDescCNMap_histoPoListView.clear();
		descCNDictPathCommonMap_histoPoListView.clear();
		if(null != visceraCode && !"".equals(visceraCode)){
			List<DictPathCommon> dictPathCommon13List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(13, visceraCode,dictSortMethod);
			if(null != dictPathCommon13List && dictPathCommon13List.size() > 0){
				for(DictPathCommon obj:dictPathCommon13List){
					data_histoPoListView.add(obj.getDescCn());
					descCNDictPathCommonMap_histoPoListView.put(obj.getDescCn(), obj);
					pyDescCNMap_histoPoListView.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}
	/**更新 病变描述语ListView  
	 * @param visceraCode
	 */
	private void updateLesionFindingListView() {
//		lesionFindingListView;
		lesionFindingListView.getSelectionModel().clearSelection();
		data_lesionFindingListView.clear();
		pyDescCNMap_lesionFindingListView.clear();
		descCNDictPathCommonMap_lesionFindingListView.clear();
		if(null != visceraCode && !"".equals(visceraCode)){
			List<DictPathCommon> dictPathCommon146List = null;
			if(noTumorRadio.isSelected()){
				dictPathCommon146List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(16, visceraCode,dictSortMethod);
			}else if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
				dictPathCommon146List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(14, visceraCode,dictSortMethod);
			}
			if(null != dictPathCommon146List && dictPathCommon146List.size() > 0){
				for(DictPathCommon obj:dictPathCommon146List){
					data_lesionFindingListView.add(obj.getDescCn());
					descCNDictPathCommonMap_lesionFindingListView.put(obj.getDescCn(), obj);
					pyDescCNMap_lesionFindingListView.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}

	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		//1.设置当前用户
		updateCurrentUserNameLabel();
		//2.更新studyLabel 
		updateStudyTaskLabel();
		//3.默认选中tab 0
		tabPane.getSelectionModel().select(0);
		//4.默认选中tab 0
		tabPane2.getSelectionModel().select(0);
		//5.更新组织学所见表（tab0）
		updateHistopathCheckTable();
		//6.更新n按钮状态
		updateBtnState();
		//7.重置  动物列表  切片脏器id列表  ，动物表，切片树 ，等
		resetAnimalAndSlice();
		//8.更新手工录入Tab
		updateHandWordTab();
		//9.更新病变程度levelComboBox
		updatelevelComboBox();
	}
	
	/**
	 * 更新手工录入Tab
	 */
	private void updateHandWordTab() {
		initData11ListView(null);
		updateAnimalCodeList();
		updateVisceraTree(0);
	}
	/**
	 * 更新动物表
	 */
	private void updateAnimalCodeList() {
		data_animalCodeListView.clear();
		animalCode2GenderMap.clear();
		List<Map<String,Object>> animalCodeMapList = BaseService.getInstance().getTblTissueSliceIndexService().getAnimalCodeListByStudyNo(studyNo);
		if(null != animalCodeMapList && animalCodeMapList.size() > 0){
			for(Map<String, Object> map:animalCodeMapList){
				String animalCode = (String) map.get("animalCode");
				Integer gender = (Integer) map.get("gender");
				animalCode2GenderMap.put(animalCode, gender);
				
				data_animalCodeListView.add(animalCode);
			}
		}
	}
	/**
	 * 11个ListView填充数据
	 */
	private void initData11ListView(String id) {
		TblAnatomyCheck anatomyCheck = null;
		List<DictPathCommon> dictPathCommon1List ;
		data_anatomyPosListView.clear();
		if(null != id){
			 anatomyCheck= BaseService.getInstance().getTblAnatomyCheckService().getById(id);
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, anatomyCheck.getVisceraCode(),1);
		}else{
			 dictPathCommon1List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,1);
		}
	
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();

	    if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
				data_anatomyPosListView.add(obj.getDescCn());
				anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
			}
		}
	    
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
			}
		});
		
		//2:解剖通用所见
		data_anatomyFindingListView_tongyong.clear();
		anatomyFinding_tongyongDictPathCommonMap.clear();
		int AnatomyFindingFlag = 0;
		if(null != anatomyCheck){
			AnatomyFindingFlag = anatomyCheck.getAnatomyFindingFlag();
		}
		  
		List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(2,1);
		if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
			for(DictPathCommon obj:dictPathCommon2List){
//				if(obj.getSpecicalFlag() == 1){
					data_anatomyFindingListView_tongyong.add(obj.getDescCn());
					anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
//				}
			}
		}
		if(AnatomyFindingFlag == 0){
			
			
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,1);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					if(obj.getSpecicalFlag() == 1){
						data_anatomyFindingListView_tesu.add(obj.getDescCn());
						anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
					}
				}
			}
			tesuRadioButton.setSelected(true);
		}
		  
		//4:体表部位
		data_bodySufacePosListView.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,1);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
			}
		}
		
	}
	
	/**
	 * 重置  动物列表  切片序号id列表  ，动物表，切片树 ，等
	 */
	private void resetAnimalAndSlice() {
		animalCodeList = null;
		tissueSliceSnIdList = null;
		sliceCodeAnimalCodeSliceVisceraMapList = null;
		
		histopathAnimalTable.getItems().clear();
		rootNode_tissueSliceTree.getChildren().clear();
	}
	/**
	 * 更新几个按钮状态
	 */
	public void updateBtnState() {
//		TblAnatomyTask task = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		TblPathStudyIndex pathStudyPlan = BaseService.getInstance().getTblPathStudyIndexService().getByStudyNo(studyNo);
		if(null != pathStudyPlan && pathStudyPlan.getHistopathReviewFlag() > 0){
			noAbnormalBtn.setDisable(true);
			registerResultBtn.setDisable(true);
			noAbnormalBtn_handword.setDisable(true);
			registerResultBtn_handword.setDisable(true);
			registerResultBtn_1.setDisable(true);
			referToBtn.setDisable(true);
			checkFinishBtn.setDisable(true);
			primaryViscerHyperlink.setDisable(true);
			
			missingRegister.setDisable(true);
			
			tumorPosPane.setDisable(true);
			tumorCharacterPane.setDisable(true);
			tumorOccurPane.setDisable(true);
			
			//致死原因，靶器官按钮
			deathReasonBtn.setDisable(true);
			targetOrganBtn.setDisable(true);
		}else{
			noAbnormalBtn.setDisable(false);
			registerResultBtn.setDisable(false);
			noAbnormalBtn_handword.setDisable(false);
			registerResultBtn_handword.setDisable(false);
			registerResultBtn_1.setDisable(false);
			referToBtn.setDisable(false);
			checkFinishBtn.setDisable(false);
			primaryViscerHyperlink.setDisable(false);
			
			missingRegister.setDisable(false);
			
			tumorPosPane.setDisable(false);
			tumorCharacterPane.setDisable(false);
			tumorOccurPane.setDisable(false);
			
			//致死原因，靶器官按钮
			deathReasonBtn.setDisable(false);
			targetOrganBtn.setDisable(false);
		}
		
	}
	/**设置源发脏器肿瘤值
	 * @param visceraCode
	 * @param visceraName
	 * @param primaryTumor
	 */
	public void setPrimaryViscerTumor(String visceraCode,String visceraName,String primaryTumor){
		
		this.primaryVisceraCode = visceraCode;
		this.primaryVisceraName = visceraName;
		this.primaryTumor = primaryTumor;
		updateHistopathCheckLabelText();
	}
	
	/**更新镜检动物表格 histopathAnimalTable
	 * @param studyNo
	 * @param animalCodeList
	 * @param sliceVisceraId
	 */
	private void updateHistopathAnimalTable(String studyNo, List<String> animalCodeList,
			String sliceVisceraId) {
		data_histopathAnimalTable.clear();
		data_histopathAnimalTable2.clear();
		if(null != animalCodeList && animalCodeList.size() > 0){
			//map中有animalCode,gender,isNoAbnormal
			List<Map<String,Object>>  mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getAnimalByStudyNoAnimalCodeListSliceVisceraId(studyNo,animalCodeList,sliceVisceraId);
			if(null != mapList && mapList.size() > 0){
				String animalCode = "";
				Integer gender = null;
				Integer isNoAbnormal = null;
				Integer resultNum = null;
				
				String state = "";
				String hasAbnormal = "";
				for(Map<String,Object> map:mapList){
					animalCode = (String) map.get("animalCode");
					gender = (Integer) map.get("gender");
					isNoAbnormal = (Integer) map.get("isNoAbnormal");
					resultNum = (Integer) map.get("resultNum");
					if(null != resultNum && resultNum > 0){
						state = "已开始检查";
						if(null ==isNoAbnormal){
							hasAbnormal = "无";
						}else{
							hasAbnormal = "有";
						}
					}else{
						state = "未开始检查";
						hasAbnormal = "";
					}
					data_histopathAnimalTable.add(new HistopathAnimal(animalCode,state,hasAbnormal,gender));
				}
			}
			
			updateHistopathAnimalTable2();
		}
	}
	
	/**
	 * 更新镜检动物表格 histopathAnimalTable
	 */
	private void updateHistopathAnimalTable(List<String> animalCodeList){
		data_histopathAnimalTable.clear();
		data_histopathAnimalTable2.clear();
		if(null != animalCodeList && animalCodeList.size() > 0){
			//map中有animalCode,gender,isNoAbnormal
			List<Map<String,Object>>  mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getAnimalByStudyNoAnimalCodeList(studyNo,animalCodeList);
			if(null != mapList && mapList.size() > 0){
				String animalCode = "";
				Integer gender = null;
				Integer isNoAbnormal = null;
				Integer resultNum = null;
				
				String state = "";
				String hasAbnormal = "";
				for(Map<String,Object> map:mapList){
					animalCode = (String) map.get("animalCode");
					gender = (Integer) map.get("gender");
					isNoAbnormal = (Integer) map.get("isNoAbnormal");
					resultNum = (Integer) map.get("resultNum");
					if(null != resultNum && resultNum > 0){
						state = "已开始检查";
						if(null ==isNoAbnormal){
							hasAbnormal = "无";
						}else{
							hasAbnormal = "有";
						}
					}else{
						state = "未开始检查";
						hasAbnormal = "";
					}
					data_histopathAnimalTable.add(new HistopathAnimal(animalCode,state,hasAbnormal,gender));
				}
			}
			
			updateHistopathAnimalTable2();
			
			histopathAnimalTable.getSelectionModel().selectFirst();
		}
	}
	
	/**
	 * 更新镜检动物表格 histopathAnimalTable（不查询接数据库）
	 */
	private void updateHistopathAnimalTable2(){
		data_histopathAnimalTable2.clear();
		if(state == 0){
			histopathAnimalTable.setItems(data_histopathAnimalTable);
		}else if(state == 1){
			histopathAnimalTable.setItems(data_histopathAnimalTable2);
			for(HistopathAnimal obj:data_histopathAnimalTable){
				if("未开始检查".equals(obj.getState())){
					data_histopathAnimalTable2.add(new HistopathAnimal(obj.getAnimalCode(),obj.getState(),obj.getHasAbnormal(),obj.getGender()));
				}
			}
		}else if(state == 2){
			histopathAnimalTable.setItems(data_histopathAnimalTable2);
			for(HistopathAnimal obj:data_histopathAnimalTable){
				if("已开始检查".equals(obj.getState())){
					data_histopathAnimalTable2.add(new HistopathAnimal(obj.getAnimalCode(),obj.getState(),obj.getHasAbnormal(),obj.getGender()));
				}
			}
		}
	}
	
	
	
	
	
	/**更新动物表和切片树
	 * @param animalCodeList
	 * @param tissueSliceSnIdList
	 */
	public void updateAnimalTableAndSliceTree(List<String> animalCodeList, List<String> tissueSliceSnIdList){
		if(null != animalCodeList && null != tissueSliceSnIdList){
			this.animalCodeList = animalCodeList;
			this.tissueSliceSnIdList = tissueSliceSnIdList;
			stateComboBox.getSelectionModel().selectFirst();
//			System.out.println(animalCodeList.toString());
//			System.out.println(tissueSliceSnIdList.toString());
			//动物优先
			if(animalRadioButton.isSelected()){
				updateHistopathAnimalTable(animalCodeList);
			}else {
				//切片优先
				updateTissueSliceTree(studyNo,tissueSliceSnIdList);
				
				sliceCodeAnimalCodeSliceVisceraMapList = BaseService.getInstance().getTblHistopathCheckService()
						.getAllSliceCodeAnimalCodeSliceViscersId(studyNo,animalCodeList,tissueSliceSnIdList);
			}
		}
	}
	
	/**更新组织切片树
	 * @param studyNo
	 * @param tissueSliceSnIdList
	 */
	private void updateTissueSliceTree(String studyNo, List<String> tissueSliceSnIdList) {
		tissueSliceTree.getSelectionModel().clearSelection();
		rootNode_tissueSliceTree.getChildren().clear();
		sliceCode2SliceTreeItemMap.clear();
		if(null != tissueSliceSnIdList && tissueSliceSnIdList.size() > 0 ){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getSliceCodeVisceraByTissueSliceSnIdList(tissueSliceSnIdList,studyNo);
			if(null != mapList){
				for(Map<String,Object> map:mapList){
					String sliceCode = (String) map.get("sliceCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					TreeItem<String> sliceTreeItem = null;
					if(sliceCode2SliceTreeItemMap.keySet().contains(sliceCode)){
						sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
					}else{
						sliceTreeItem = new TreeItem<String>("");
						Label sliceLabel = new Label("切片"+sliceCode);
						//label的UserData存放的是sliceCode
						sliceLabel.setUserData(sliceCode);
						sliceTreeItem.setGraphic(sliceLabel);
						sliceTreeItem.setExpanded(true);
						rootNode_tissueSliceTree.getChildren().add(sliceTreeItem);
						sliceCode2SliceTreeItemMap.put(sliceCode, sliceTreeItem);
					}
					TreeItem<String> leafNode = new TreeItem<String>("");
					Label leafLabel = new Label();
					leafLabel.setText(visceraOrTissueName);
					leafLabel.setUserData(map);
					leafNode.setGraphic(leafLabel);
					
					sliceTreeItem.getChildren().add(leafNode);
				}
				tissueSliceTree.getSelectionModel().select(rootNode_tissueSliceTree.getChildren().get(0).getChildren().get(0));
			}
		}
	}
	/**更新组织切片树
	 * @param animalCode
	 * @param gender
	 */
	private void updateTissueSliceTree(String animalCode,Integer gender) {
		
		tissueSliceTree.getSelectionModel().clearSelection();
		rootNode_tissueSliceTree.getChildren().clear();
		sliceCode2SliceTreeItemMap.clear();
		
		if(null != tissueSliceSnIdList && tissueSliceSnIdList.size() > 0 && null != animalCode){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService()
					.getSliceCodeVisceraByTissueSliceSnIdListAnimalCode(tissueSliceSnIdList,studyNo,animalCode,gender);
			if(null != mapList){
				for(Map<String,Object> map:mapList){
					String sliceCode = (String) map.get("sliceCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					String sueSliceVisceraRecordId_hascheck = (String) map.get("sueSliceVisceraRecordId_hascheck");
					String anatomyfinding = (String) map.get("anatomyfinding");
					TreeItem<String> sliceTreeItem = null;
					if(sliceCode2SliceTreeItemMap.keySet().contains(sliceCode)){
						sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
					}else{
						sliceTreeItem = new TreeItem<String>("");
						Label sliceLabel = new Label("切片"+sliceCode);
						//label的UserData存放的是sliceCode
						sliceLabel.setUserData(sliceCode);
						sliceTreeItem.setGraphic(sliceLabel);
						sliceTreeItem.setExpanded(true);
						rootNode_tissueSliceTree.getChildren().add(sliceTreeItem);
						sliceCode2SliceTreeItemMap.put(sliceCode, sliceTreeItem);
					}
					TreeItem<String> leafNode = new TreeItem<String>("");
					Label leafLabel = new Label();
					leafLabel.setText(visceraOrTissueName);
					leafLabel.setUserData(map);
					leafNode.setGraphic(leafLabel);
					if(null != sueSliceVisceraRecordId_hascheck && !"".equals(sueSliceVisceraRecordId_hascheck)){
						leafLabel.setStyle("-fx-text-fill: blue;");
//						sliceTreeItem.getGraphic().setStyle("-fx-text-fill: blue;");
					}else{
						if(null != anatomyfinding && !"".equals(anatomyfinding)){
							leafLabel.setStyle("-fx-text-fill: #32fc20;");
						}
					}
					
					sliceTreeItem.getChildren().add(leafNode);
				}
			}
		}
	}
	

	/**
	 * 更新当前用户 currentUserNameLabel
	 */
	private void updateCurrentUserNameLabel() {
		String realName = SaveUserInfo.getRealName();
		if(null != realName && !"".equals(realName)){
			currentUserNameLabel.setText("当前用户："+realName);
		}else{
			currentUserNameLabel.setText("");
		}
	}
	/**
	 * 更新studyLabel 赋值
	 */
	private void updateStudyTaskLabel() {
		String str = "专题编号："+studyNo+"      ";
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		if(null != studyPlan){
			str = str+"SD："+studyPlan.getStudydirector()+"      ";
			str = str+"动物种类："+studyPlan.getAnimalType()+"      ";
		}
		String pathSd = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(studyNo);
		if(null != pathSd ){
			str = str+"病理专题负责人："+pathSd;
		}
		
		studyLabel.setText(str);
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathCheck.fxml"));
		Scene scene = new Scene(root, 1014, 590);
		stage.setScene(scene);
		stage.setTitle("组织学检查");
//		stage.setResizable(false);
		
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight()); 

		stage.setMinHeight(700);
		stage.setMinWidth(1024);
		stage.setMaxHeight(bounds.getMaxY());
		stage.setMaxWidth(bounds.getMaxX());
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
//				event.consume();
//				Window source = (Window) event.getSource();
//				source.hide();
			}
		});
		
		stage.show();
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	/**镜检动物
	 * @author Administrator
	 *
	 */
	public class HistopathAnimal{
		
		private StringProperty animalCode;
		private StringProperty genderStr;
		private StringProperty state;
		private StringProperty hasAbnormal;
		private IntegerProperty gender;
		
		public HistopathAnimal(String animalCode,String state,String hasAbnormal,Integer gender){
			setAnimalCode(animalCode);
			setState(state);
			setHasAbnormal(hasAbnormal);
			setGender(gender);
			if(null != gender && gender == 1){
				setGenderStr("♂");
			}else{
				setGenderStr("♀");
			}
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
		public String getHasAbnormal() {
			return hasAbnormal.get();
		}
		public void setHasAbnormal(String hasAbnormal) {
			this.hasAbnormal = new SimpleStringProperty(hasAbnormal);
		}
		public Integer getGender() {
			return gender.get();
		}
		public void setGender(Integer gender) {
			this.gender = new SimpleIntegerProperty(gender);
		}
		public String getGenderStr() {
			return genderStr.get();
		}
		public void setGenderStr(String genderStr) {
			this.genderStr = new SimpleStringProperty(genderStr);
		}
		
		
	}
	
	/**组织学检查
	 * @author Administrator
	 *
	 */
	public class HistopathCheck{
		private StringProperty animalCode;		//动物编号
		private StringProperty visceraOrTissueName;//脏器或组织
		private StringProperty checkResult;//检查结果
		private StringProperty checkTime;	//检查时间
		private StringProperty tissueSliceVisceraRecordId;
		
		private StringProperty tumorFlag;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
		private StringProperty metastasisFlag;	//是否转移
		private StringProperty primaryVisceraTumor;	//原发脏器肿瘤
		private StringProperty level;	//病变程度
		private StringProperty tumorNum;	//肿瘤数量
		private StringProperty tumorPos;	//肿瘤位置
		private StringProperty tumorOccurDate;	//肿瘤发生日期
		private StringProperty tumorCharacter;	//肿瘤特性
		private StringProperty remark;	//备注
		
		private StringProperty visceraCode;	//脏器编号
		
		public HistopathCheck(String animalCode,String visceraOrTissueName,String checkTime,String tissueSliceVisceraRecordId,Integer isNoAbnormal,Integer tumorFlag,Integer metastasisFlag,
				String histoPos,String lesionFinding,
				String primaryViscera,String primaryTumor,String level,Integer tumorNum,Integer tumorPos,String tumorOccurDate,Integer tumorCharacter,String remark,
				String visceraCode){
			setAnimalCode(animalCode);
			setVisceraOrTissueName(visceraOrTissueName);
			setTissueSliceVisceraRecordId(tissueSliceVisceraRecordId);
			setCheckTime(checkTime);
			if(isNoAbnormal == 0){
				if(tumorFlag == 0 || tumorFlag == 1 ){
					setTumorFlag(tumorFlag == 0 ? "非肿瘤":"良性肿瘤");
					setMetastasisFlag("");
					setPrimaryVisceraTumor("");
				}else{
					setTumorFlag("恶性肿瘤");
//					setMetastasisFlag(metastasisFlag == 1 ? "是":"否");
					if(metastasisFlag == 1){
						setMetastasisFlag("转移");
						setPrimaryVisceraTumor((primaryViscera != null ? primaryViscera+" " :"")+(primaryTumor != null ? primaryTumor:""));
					}else if(metastasisFlag == 2){
						setMetastasisFlag("侵袭");
						setPrimaryVisceraTumor("");
					}else{
						setMetastasisFlag("原发");
						setPrimaryVisceraTumor("");
					}
				}
				
				String checkResult = "";
				if(null != histoPos && !"".equals(histoPos)){
					checkResult = histoPos+" "+lesionFinding;
				}else{
					checkResult = lesionFinding;
				}
				setCheckResult(checkResult);
//				setCheckResult(histoPos+" "+lesionFinding);
			}else if(isNoAbnormal == 1){
				setTumorFlag("");
				setMetastasisFlag("");
				setPrimaryVisceraTumor("");
				setCheckResult("未见异常");
			}else if(isNoAbnormal == -1){
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("缺失");
				setPrimaryVisceraTumor("");
			}
			
			
			setLevel(level != null ? level :"");
			setTumorNum(tumorNum+"");
			if(null != tumorPos && tumorPos == 1){
				setTumorPos("浅表");
				setTumorOccurDate(tumorOccurDate);
				setTumorCharacter("");
			}else if(null != tumorPos && tumorPos == 2){
				setTumorPos("深部");
				setTumorOccurDate("");
//				1：偶发；2：致死；3：不明确
				switch (tumorCharacter) {
				case 1:	setTumorCharacter("偶发");break;
				case 2:	setTumorCharacter("致死");break;
				case 3:	setTumorCharacter("不明确");break;

				default:setTumorCharacter("");
					break;
				}
			}else{
				setTumorPos("");
				setTumorOccurDate("");
				setTumorCharacter("");
			}
			setRemark(remark != null ? remark :"");
			
			setVisceraCode(visceraCode);
		}
		
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}
		public String getCheckResult() {
			return checkResult.get();
		}
		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}
		public String getCheckTime() {
			return checkTime.get();
		}
		public void setCheckTime(String checkTime) {
			this.checkTime = new SimpleStringProperty(checkTime);
		}

		public String getTissueSliceVisceraRecordId() {
			return tissueSliceVisceraRecordId.get();
		}

		public void setTissueSliceVisceraRecordId(String tissueSliceVisceraRecordId) {
			this.tissueSliceVisceraRecordId = new SimpleStringProperty(tissueSliceVisceraRecordId);
		}
		
		public String getTumorFlag() {
			return tumorFlag.get();
		}

		public void setTumorFlag(String tumorFlag) {
			this.tumorFlag = new SimpleStringProperty(tumorFlag);
		}

		public String getMetastasisFlag() {
			return metastasisFlag.get();
		}

		public void setMetastasisFlag(String metastasisFlag) {
			this.metastasisFlag = new SimpleStringProperty(metastasisFlag);
		}

		public String getPrimaryVisceraTumor() {
			return primaryVisceraTumor.get();
		}

		public void setPrimaryVisceraTumor(String primaryVisceraTumor) {
			this.primaryVisceraTumor = new SimpleStringProperty(primaryVisceraTumor);
		}

		public String getLevel() {
			return level.get();
		}

		public void setLevel(String level) {
			this.level = new SimpleStringProperty(level);
		}

		public String getTumorNum() {
			return tumorNum.get();
		}

		public void setTumorNum(String tumorNum) {
			this.tumorNum = new SimpleStringProperty(tumorNum);
		}

		public String getTumorPos() {
			return tumorPos.get();
		}

		public void setTumorPos(String tumorPos) {
			this.tumorPos = new SimpleStringProperty(tumorPos);
		}

		public String getTumorOccurDate() {
			return tumorOccurDate.get();
		}

		public void setTumorOccurDate(String tumorOccurDate) {
			this.tumorOccurDate = new SimpleStringProperty(tumorOccurDate);
		}

		public String getTumorCharacter() {
			return tumorCharacter.get();
		}

		public void setTumorCharacter(String tumorCharacter) {
			this.tumorCharacter = new SimpleStringProperty(tumorCharacter);
		}

		public String getRemark() {
			return remark.get();
		}

		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}

		public String getVisceraCode() {
			return visceraCode.get();
		}

		public void setVisceraCode(String visceraCode) {
			this.visceraCode = new SimpleStringProperty(visceraCode);
		}
		
		
	}
	/**组织学检查_1
	 * @author Administrator
	 *
	 */
	public class HistopathResult{
		private StringProperty id;			//
		private StringProperty tumorFlag;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
		private StringProperty metastasisFlag;	//是否转移
		private StringProperty checkResult;		//检查结果
		private StringProperty primaryVisceraTumor;	//原发脏器肿瘤
		private BooleanProperty  operate;	//操作
		
		private StringProperty level;	//病变程度
		private StringProperty tumorNum;	//肿瘤数量
		private StringProperty tumorPos;	//肿瘤位置
		private StringProperty tumorOccurDate;	//肿瘤发生日期
		private StringProperty tumorCharacter;	//肿瘤特性
		private StringProperty remark;	//备注
		
		private StringProperty visceraOrTissueName;//脏器或组织
		
		
		public HistopathResult(String id,Integer isNoAbnormal,Integer tumorFlag,Integer metastasisFlag,
				String histoPos,String lesionFinding,String primaryViscera,String primaryTumor,boolean operate,
				String level,Integer tumorNum,Integer tumorPos,String tumorOccurDate,Integer tumorCharacter,String remark,
				String visceraOrTissueName){
			setId(id);
			if(isNoAbnormal == 0){
				if(tumorFlag == 0 || tumorFlag == 1 ){
					setTumorFlag(tumorFlag == 0 ? "非肿瘤":"良性肿瘤");
					setMetastasisFlag("");
					setPrimaryVisceraTumor("");
				}else{
					setTumorFlag("恶性肿瘤");
//					setMetastasisFlag(metastasisFlag == 1 ? "是":"否");
					if(metastasisFlag == 1){
						setMetastasisFlag("转移");
						setPrimaryVisceraTumor((primaryViscera != null ? primaryViscera+" " :"")+(primaryTumor != null ? primaryTumor:""));
					}else if(metastasisFlag == 2){
						setMetastasisFlag("侵袭");
						setPrimaryVisceraTumor("");
					}else{
						setMetastasisFlag("原发");
						setPrimaryVisceraTumor("");
					}
				}
				String checkResult = "";
				if(null != histoPos && !"".equals(histoPos)){
					checkResult = histoPos+" "+lesionFinding;
				}else{
					checkResult = lesionFinding;
				}
				setCheckResult(checkResult);
			}else if(isNoAbnormal == 1) {
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("未见异常");
				setPrimaryVisceraTumor("");
			}else if(isNoAbnormal == -1){
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("缺失");
				setPrimaryVisceraTumor("");
			}
			setOperate(operate);
			
			setLevel(level != null ? level :"");
			setTumorNum(tumorNum+"");
			if(null != tumorPos && tumorPos == 1){
				setTumorPos("浅表");
				setTumorOccurDate(tumorOccurDate);
				setTumorCharacter("");
			}else if(null != tumorPos && tumorPos == 2){
				setTumorPos("深部");
				setTumorOccurDate("");
//				1：偶发；2：致死；3：不明确
				switch (tumorCharacter) {
				case 1:	setTumorCharacter("偶发");break;
				case 2:	setTumorCharacter("致死");break;
				case 3:	setTumorCharacter("不明确");break;

				default:setTumorCharacter("");
					break;
				}
			}else{
				setTumorPos("");
				setTumorOccurDate("");
				setTumorCharacter("");
			}
			setRemark(remark != null ? remark :"");
			
			setVisceraOrTissueName(visceraOrTissueName);
			
		}
		
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}

		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}

		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		public String getId() {
			return id.get();
		}


		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}


		public String getTumorFlag() {
			return tumorFlag.get();
		}


		public void setTumorFlag(String tumorFlag) {
			this.tumorFlag = new SimpleStringProperty(tumorFlag);
		}


		public String getMetastasisFlag() {
			return metastasisFlag.get();
		}


		public void setMetastasisFlag(String metastasisFlag) {
			this.metastasisFlag = new SimpleStringProperty(metastasisFlag);
		}


		public String getPrimaryVisceraTumor() {
			return primaryVisceraTumor.get();
		}


		public void setPrimaryVisceraTumor(String primaryVisceraTumor) {
			this.primaryVisceraTumor = new SimpleStringProperty(primaryVisceraTumor);
		}


		public String getCheckResult() {
			return checkResult.get();
		}
		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}
		
		public String getLevel() {
			return level.get();
		}

		public void setLevel(String level) {
			this.level = new SimpleStringProperty(level);
		}

		public String getTumorNum() {
			return tumorNum.get();
		}

		public void setTumorNum(String tumorNum) {
			this.tumorNum = new SimpleStringProperty(tumorNum);
		}

		public String getTumorPos() {
			return tumorPos.get();
		}

		public void setTumorPos(String tumorPos) {
			this.tumorPos = new SimpleStringProperty(tumorPos);
		}

		public String getTumorOccurDate() {
			return tumorOccurDate.get();
		}

		public void setTumorOccurDate(String tumorOccurDate) {
			this.tumorOccurDate = new SimpleStringProperty(tumorOccurDate);
		}

		public String getTumorCharacter() {
			return tumorCharacter.get();
		}

		public void setTumorCharacter(String tumorCharacter) {
			this.tumorCharacter = new SimpleStringProperty(tumorCharacter);
		}

		public String getRemark() {
			return remark.get();
		}

		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
	}
	/**镜检删除
	 * @author Administrator
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
	            hyperlink.setText("删除");
	            hyperlink.setPrefWidth(50);
	            hyperlink.setPrefHeight(20);
	            hyperlink.setUserData(this.getTableRow().getItem());
	            HistopathResult histopathResult = (HistopathResult) this.getTableRow().getItem();
	            if(histopathResult.getCheckResult().equals("缺失")){
	            	hyperlink.setDisable(true);
	            }
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						HistopathResult histopathResult = (HistopathResult) hyperlink.getUserData();
//						if(Sign.openSignWithReasonFrame("删除原因","数据删除")){
//							String reason = Sign.getReason();
//							String operator = SaveUserInfo.getRealName();
//							Json json = BaseService.getInstance().getTblHistopathCheckService().deleteOne(histopathResult.getId(),reason,operator);
//							if(json.isSuccess()){
//								//更新
//								updateHistopathResultTable(sliceVisceraId);
//								updateHistopathCheckTable();
//							}else{
//								showErrorMessage(json.getMsg());
//							}
//							
//						}
						//改为无需签字的
						if(Messager.showSimpleConfirm("提示", "确定删除当前数据吗？")){
							String reason = "";
							String operator = "";
							Json json = BaseService.getInstance().getTblHistopathCheckService().deleteOne(histopathResult.getId(),reason,operator);
							if(json.isSuccess()){
								//更新
								updateHistopathResultTable(sliceVisceraId);
								updateHistopathCheckTable();
							}else{
								showErrorMessage(json.getMsg());
							}
							
						}
					}
				});
	        }  
	    }  
	}
	
}
