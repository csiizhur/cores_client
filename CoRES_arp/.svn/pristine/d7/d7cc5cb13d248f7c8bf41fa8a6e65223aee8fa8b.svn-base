package com.lanen.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import com.lanen.base.BaseService;
import com.lanen.model.Area;
import com.lanen.model.Breeding_Json;
import com.lanen.model.Changeroom;
import com.lanen.model.Childbirth;
import com.lanen.model.Employee;
import com.lanen.model.Gestation;
import com.lanen.model.Individual_Json;
import com.lanen.model.Iplogin;
import com.lanen.model.Leavebreast;
import com.lanen.model.Miscarriage_Json;
import com.lanen.model.Normal;
import com.lanen.model.Normal_Json;
import com.lanen.model.Quarantine;
import com.lanen.model.Vaccine_Json;
import com.lanen.model.Virus_Json;
import com.lanen.model.Xcg_Json;
import com.lanen.model.Xysh_Json;
import com.lanen.model.clinicaltest.ChangeroomTableData;
import com.lanen.model.clinicaltest.XyshTableData;
import com.lanen.model.clinicaltest.ObservationTableData;
import com.lanen.model.clinicaltest.VaccineTableData;
import com.lanen.model.clinicaltest.VirusTableData;
import com.lanen.model.clinicaltest.WeightTableData;
import com.lanen.model.clinicaltest.XcgData;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.AnimalsInfoFrameController;
import com.lanen.view.AnimalsObservationFrameController;
import com.lanen.view.AnimalsWeightFrameController;

public class MainFrameController implements Initializable {
	
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
	private ComboBox<String> checkItemId;
	private ObservableList<String> data_checkItemId=FXCollections.observableArrayList() ;
	@FXML
	private TreeView<String> areaTree;//区域树
	private TreeItem<String> rootNode=new TreeItem<String>();//clinicalTestReqTree 根节点
	private Map<String,Area> areaMap=new HashMap<String,Area>(); //  存放树 节点对应值     
	private Map<String,String> individualMap = new HashMap<String,String>();//存放叶子节点对应值 
	
	@FXML
	private TreeView<String> keeperTree2;//饲养员树2（按日期）
	private TreeItem<String> rootNode2=new TreeItem<String>();//根节点
	private Map<String,String> keeperMap2 = new HashMap<String,String>();//存放叶子节点对应值 
	
	@FXML
	private TextField  monkeyNoText;            //动物编号
	@FXML
	private TextField monkeySexTypeText;//动物性别
	@FXML
	private TextField monkeyKeeperText;       //饲养人员
	@FXML
	private TextField areaNameText;                  //区域房舍  
	@FXML
	private TextField animalTypeText;          //动物种类
	@FXML
	private TextField animalAgeText;        //年龄阶段
	@FXML
	private TextField monkeyBirthdayText;          //出生日期
	@FXML
	private TextField monkeyLeavebreastDateText;//离乳日期
	
	@FXML
	private Label currentUserLabel;       //当前用户
	@FXML
	private Label monkeyNoLabel;           //动物编号
	@FXML
	private Label monkeyKeeperLabel;     //饲养员
	
	@FXML
	private ListView<String> oestrusList;           //发情记录 
	private ObservableList<String> data_oestrusList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> gestationList;           //妊娠记录
	private ObservableList<String> data_gestationList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> childBirthList;           //产仔记录
	private ObservableList<String> data_childBirthList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> miscarriageList;           //流产记录
	private ObservableList<String> data_miscarriageList= FXCollections.observableArrayList();
	@FXML
	private ListView<String> animalList;           //离乳记录 
	private ObservableList<String> data_animalList= FXCollections.observableArrayList();
	
	@FXML
	private TextField checkItemDate;
	@FXML
	private TextField checkItemBoss;	
	
	@FXML
	private TabPane clinicalTestReqTabPane;//动物信息/检测结果面板/观察记录/称重记录/调栏记录
	private int currentTabNo = 0;//动物信息页
	
	
	@FXML
	private TabPane clinicalTestDataTabPane;//检测结果面板
		
	//生化 表
	@FXML
	private TableView<XyshTableData> bioChemTable;
	private ObservableList<XyshTableData> data_bioChemTable=FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<XyshTableData,String> animalCodeCol;         //动物编号
	@FXML
    private TableColumn<XyshTableData,String> specimenCodeCol;       //检验编号
    @FXML
    private TableColumn<XyshTableData,String> collectionTimeCol;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<XyshTableData,String> bhaoCol;
    @FXML
    private TableColumn<XyshTableData,String> astCol;
    @FXML
    private TableColumn<XyshTableData,String> altCol;
    @FXML
    private TableColumn<XyshTableData,String> tpCol;
    @FXML
    private TableColumn<XyshTableData,String> albCol;
    @FXML
    private TableColumn<XyshTableData,String> ggtCol;
    @FXML
    private TableColumn<XyshTableData,String> tbilCol;
    @FXML
    private TableColumn<XyshTableData,String> bunCol;
    @FXML
    private TableColumn<XyshTableData,String> creaCol;
    @FXML
    private TableColumn<XyshTableData,String> gluCol;
    @FXML
    private TableColumn<XyshTableData,String> tgCol;
    @FXML
    private TableColumn<XyshTableData,String> cholCol;
    @FXML
    private TableColumn<XyshTableData,String> ldhCol;
    @FXML
    private TableColumn<XyshTableData,String> ckCol;
    @FXML
    private TableColumn<XyshTableData,String> naCol;
    @FXML
    private TableColumn<XyshTableData,String> kCol;
    @FXML
    private TableColumn<XyshTableData,String> ciCol;
    
	//血常规 表
	@FXML
	private TableView<XcgData> hematTable;
	private ObservableList<XcgData> data_hematTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<XcgData,String> bhaoCol2;             //数据Id	
	@FXML
	private TableColumn<XcgData,String> animalCodeCol2;         //动物编号
	@FXML
    private TableColumn<XcgData,String> specimenCodeCol2;       //检验编号号	
    @FXML
    private TableColumn<XcgData,String> collectionTimeCol2;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<XcgData,String> wbcCol;
    @FXML
    private TableColumn<XcgData,String> rbcCol;
    @FXML
    private TableColumn<XcgData,String> hgbCol;
    @FXML
    private TableColumn<XcgData,String> hctCol;
    @FXML
    private TableColumn<XcgData,String> pltCol;
    @FXML
    private TableColumn<XcgData,String> mcvCol;
    @FXML
    private TableColumn<XcgData,String> mchCol;
    @FXML
    private TableColumn<XcgData,String> mchcCol;
    @FXML
    private TableColumn<XcgData,String> lymCol;
    @FXML
    private TableColumn<XcgData,String> midCol;
    @FXML
    private TableColumn<XcgData,String> graCol;
	//病毒  表
	@FXML
	private TableView<VirusTableData> bloodCoagTable;
	private ObservableList<VirusTableData> data_bloodCoagTable=FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<VirusTableData,String> animalCodeCol3;         //动物编号
	@FXML
    private TableColumn<VirusTableData,String> specimenCodeCol3;       //检验编号
    @FXML
    private TableColumn<VirusTableData,String> collectionTimeCol3;       //数据采集时间(检验时间)
    @FXML
	private TableColumn<VirusTableData,String> xueqCol;             //血清号
    @FXML
	private TableColumn<VirusTableData,String> bvCol;
    @FXML
	private TableColumn<VirusTableData,String> stlvCol;
    @FXML
	private TableColumn<VirusTableData,String> srvCol;
    @FXML
	private TableColumn<VirusTableData,String> sivCol;
    @FXML
	private TableColumn<VirusTableData,String> filoCol;
    
    //疫苗表
	@FXML
	private TableView<VaccineTableData> urineTable;
	private ObservableList<VaccineTableData> data_urineTable=FXCollections.observableArrayList();
	             
	@FXML
	private TableColumn<XyshTableData,String> animalCodeCol4;         //动物编号
	@FXML
    private TableColumn<XyshTableData,String> specimenCodeCol4;       //检验编号
    @FXML
    private TableColumn<XyshTableData,String> collectionTimeCol4;       //数据采集时间(检验时间)
    @FXML
	private TableColumn<VaccineTableData,String> measlesCol;
    @FXML
	private TableColumn<VaccineTableData,String> hepatitisACol;
    @FXML
	private TableColumn<VaccineTableData,String> hepatitisBCol;
	
	@FXML
	private HBox hBox;
		
	@FXML
	private TextField queryMonkeyText;
	//观察记录
	@FXML
	private TableView<ObservationTableData> observationTable;
	private ObservableList<ObservationTableData> data_observationTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ObservationTableData,String> monkeyidCol;         
	@FXML
    private TableColumn<ObservationTableData,String> quyuCol;       
    @FXML
    private TableColumn<ObservationTableData,String> sexCol;       
    @FXML
	private TableColumn<ObservationTableData,String> contentCol;
    @FXML
	private TableColumn<ObservationTableData,String> observerCol;
    @FXML
	private TableColumn<ObservationTableData,String> observationtimeCol;
    
  //称重记录
  	@FXML
  	private TableView<WeightTableData> weightTable;
  	private ObservableList<WeightTableData> data_weightTable=FXCollections.observableArrayList();
  	@FXML
  	private TableColumn<WeightTableData,String> monkeyidCol1;         
  	@FXML
    private TableColumn<WeightTableData,String> areaNameCol;       
    @FXML
    private TableColumn<WeightTableData,String> animalSexCol;       
    @FXML
  	private TableColumn<WeightTableData,String> weightCol;
    @FXML
  	private TableColumn<WeightTableData,String> bossNameCol;
    @FXML
  	private TableColumn<WeightTableData,String> weightDateCol;
    
  //调栏记录
  	@FXML
  	private TableView<ChangeroomTableData> changeroomTable;
  	private ObservableList<ChangeroomTableData> data_changeroomTable=FXCollections.observableArrayList();
  	@FXML
  	private TableColumn<ChangeroomTableData,String> monkeyidCol2;         
    @FXML
    private TableColumn<ChangeroomTableData,String> animalSexCol2;       
    @FXML
  	private TableColumn<ChangeroomTableData,String> yAreaNameCol;
    @FXML
  	private TableColumn<ChangeroomTableData,String> yKeeperNameCol;
    @FXML
  	private TableColumn<ChangeroomTableData,String> xAreaNameCol;
    @FXML
  	private TableColumn<ChangeroomTableData,String> xKeeperNameCol;
    @FXML
  	private TableColumn<ChangeroomTableData,String> changeroomDateCol;
    
    @FXML
    private RadioButton monkeyInButn;//在场按钮
    @FXML
    private RadioButton monkeyOutButn;//待销售按钮
    @FXML
    private RadioButton allMonkeyButn;//全部
    
    public static boolean comPortIsUsed=false;//端口是否关闭
    public static int dataAcceptItem;//0:RFID查询1:动物称重2:动物观察
    
    @FXML
    private ComboBox<String> keeperComboBox;
    private ObservableList<String> data_keeperName=FXCollections.observableArrayList();
    private Map<String,Object> keeperNameIDMap=new HashMap<String,Object>();
    private String keeperId;
	@Override
	public void initialize(URL url, ResourceBundle arg1) {
		//当前用户
		currentUserLabel.setText(null!=SaveUserInfo.getUser() ? SaveUserInfo.getUser().getName() : "" );
		//初始化工具栏按钮
		initMenuBtn();
		//初始化菜单栏
		initMenu();
		
		//初始化   区域树
		initAreaTree();
		
		//初始化  饲养员树
		initKeeperTree2();
		// 初始化  常规记录信息
		initNormalCheckInfo();
		// 初始化  检测结果
		initQuarantineTestData();
		
		//动物信息、检测结果  观察记录 称重记录 调栏记录 面板     切换事件
		initChangePanelTab();
		//检测结果4 检测项目   
		initClinicalTestDataTabPane();
		//初始化 观察记录
		initObservationData();
		//初始化 称重记录
		initWeightData();
		//初始化 调栏记录
		initChangeroomData();
		//初始化显示的button
		ToggleGroup group = new ToggleGroup();
		monkeyInButn.setToggleGroup(group);
		monkeyOutButn.setToggleGroup(group);
		allMonkeyButn.setToggleGroup(group);
		//初始化饲养员选择框
		initKeeperComboBox();
	}

	/**
	 * 动物信息、检测结果面板   观察记录 称重记录 调栏记录  切换事件
	 */
	private void initChangePanelTab() {
		currentTabNo = 0;
		clinicalTestReqTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
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
					}else if(newValue.getText().equals("观察记录")){
						currentTabNo = 2;
						if(observationTable.getItems()==null){
							observationTable.setItems(data_observationTable);
						}
					}else if(newValue.getText().equals("称重记录")){
						currentTabNo=3;
						if(weightTable.getItems()==null){
							weightTable.setItems(data_weightTable);
						}
					}else if(newValue.getText().equals("调栏记录")){
						currentTabNo=4;
						if(changeroomTable.getItems()==null){
							changeroomTable.setItems(data_changeroomTable);
						}
					}else{
						currentTabNo=0;
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
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab newValue) {
			}
			
		});
	}

	/**
	 * 初始化菜单栏
	 */
	private void initMenu() {
		Node icon2 = new ImageView( new Image(getClass().getResourceAsStream("/image/设备登记m.png")));
		Node icon3 = new ImageView( new Image(getClass().getResourceAsStream("/image/通道号设置m.png")));
		Node icon4 = new ImageView( new Image(getClass().getResourceAsStream("/image/设备检定信息m.png")));
		Node icon5 = new ImageView( new Image(getClass().getResourceAsStream("/image/修改密码m.png")));
		Node icon6 = new ImageView( new Image(getClass().getResourceAsStream("/image/退出m.png")));
		Node icon7 = new ImageView( new Image(getClass().getResourceAsStream("/image/关于m.png")));
		Node icon8 = new ImageView( new Image(getClass().getResourceAsStream("/image/审计m.png")));
		
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
		menuBtn1.setTooltip(new Tooltip("临时任务"));
		menuBtn2.setTooltip(new Tooltip("设备登记"));
		menuBtn3.setTooltip(new Tooltip("通道号设置"));
		menuBtn4.setTooltip(new Tooltip("设备检定信息"));
		menuBtn5.setTooltip(new Tooltip("修改密码"));
		menuBtn6.setTooltip(new Tooltip("退出"));
		
	}


	/**
	 * 初始化   饲养员树
	 */
	private void initKeeperTree2() {
		//所有动物编号.
		List<Map<String,String>> allIndividual=BaseService.getIndividualService().getAllMonkeyidCombobox();
		if(allIndividual!=null){
			for(Map<String,String> mi:allIndividual){
				keeperMap2.put(mi.get("id"), mi.get("text"));
			}
		}
		rootNode2.setValue("饲养员");
		rootNode2.setExpanded(true);
		keeperTree2.setRoot(rootNode2);
		//由tree1切换到tree2：Exception in thread "JavaFX Application Thread" java.lang.IndexOutOfBoundsException
		keeperTree2.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
			@Override
			public TreeCell<String> call(TreeView<String> arg0) {
				return new TreeFormatCell();
		}});
		List<Long> list = BaseService.getAreaService().getAllKeeper();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (Long keeper : list) {
			Employee user = BaseService.getUserService().getById(keeper);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", keeper);
			map.put("text", user.getName());
			list2.add(map);
		}
		for(Map<String, Object> m:list2){
			TreeItem<String> item = new TreeItem<String> (m.get("text")+"");              
            rootNode2.getChildren().add(item);
            List<?> child=BaseService.getAreaService().getAnimalByKeeper(Long.parseLong(m.get("id")+""));
            for(Object m2:child){
            	TreeItem<String> childItem=new TreeItem<String>(m2+"");
            	item.getChildren().add(childItem);
            }
		}
		keeperTree2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable,
					TreeItem<String> oldValue, TreeItem<String> newValue) {
				//清空区域选中状态?为什么晴空
				areaTree.getSelectionModel().clearSelection();
				if(null!=newValue){
					
					if(keeperMap2.keySet().contains(newValue.getValue())){
						Individual_Json individual=BaseService.getIndividualService().getIndividualJsonById(newValue.getValue());							
						
						//更新  动物信息  区域  的所有Text
						updateAllText(individual);
						
						//更新常规记录的信息。
						updateNormalRecorder(newValue.getValue());
						
						//更新   检测结果    面板
						updateTestResult(newValue.getValue(),null);
						
						//给检疫编号赋值.
						initCheckItemIdComboBox(newValue.getValue());
						
						//更新观察记录
						updateObservationRecoder(newValue.getValue());
						
						//更新称重记录
						updateWeightRecoder(newValue.getValue());
						
						//更新调栏记录
						updateChangeroomRecoder(newValue.getValue());
					}
				}
			}
			});
	}
		
	/**
	 * 更新 饲养员  树--在场
	 * @param beginDate
	 * @param endDate
	 */
	private void updateInAnimalsByKeeperTree2() {
		rootNode2.getChildren().clear();
		
		List<Long> list = BaseService.getAreaService().getAllKeeper();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (Long keeper : list) {
			Employee user = BaseService.getUserService().getById(keeper);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", keeper);
			map.put("text", user.getName());
			list2.add(map);
		}
		for(Map<String, Object> m:list2){
			TreeItem<String> item = new TreeItem<String> (m.get("text")+"");              
            rootNode2.getChildren().add(item);
            List<?> child=BaseService.getAreaService().getInAnimalByKeeper(Long.parseLong(m.get("id")+""));
            for(Object m2:child){
            	TreeItem<String> childItem=new TreeItem<String>(m2+"");
            	item.getChildren().add(childItem);
            }
		}
				
	}
	/**
	 * 更新 饲养员  树2--全部
	 * @param beginDate
	 * @param endDate
	 */
	private void updateAnimalsByKeeperTree2() {
		
		rootNode2.getChildren().clear();
		
		List<Long> list = BaseService.getAreaService().getAllKeeper();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (Long keeper : list) {
			Employee user = BaseService.getUserService().getById(keeper);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", keeper);
			map.put("text", user.getName());
			list2.add(map);
		}
		for(Map<String, Object> m:list2){
			TreeItem<String> item = new TreeItem<String> (m.get("text")+"");              
            rootNode2.getChildren().add(item);
            List<?> child=BaseService.getAreaService().getAnimalByKeeper(Long.parseLong(m.get("id")+""));
            for(Object m2:child){
            	TreeItem<String> childItem=new TreeItem<String>(m2+"");
            	item.getChildren().add(childItem);
            }
		}
				
	}

	/**
	 * 更新 饲养员  树--待销售
	 * @param beginDate
	 * @param endDate
	 */
	private void updateOutAnimalsByKeeperTree2() {
		rootNode2.getChildren().clear();
		
		List<Long> list = BaseService.getAreaService().getAllKeeper();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (Long keeper : list) {
			Employee user = BaseService.getUserService().getById(keeper);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", keeper);
			map.put("text", user.getName());
			list2.add(map);
		}
		for(Map<String, Object> m:list2){
			TreeItem<String> item = new TreeItem<String> (m.get("text")+"");              
            rootNode2.getChildren().add(item);
            List<?> child=BaseService.getAreaService().getOutAnimalByKeeper(Long.parseLong(m.get("id")+""));
            for(Object m2:child){
            	TreeItem<String> childItem=new TreeItem<String>(m2+"");
            	item.getChildren().add(childItem);
            }
		}
				
	}
	/**
	 * 初始化  常规记录
	 */
	private void initNormalCheckInfo() {
		oestrusList.setItems(data_oestrusList);
		gestationList.setItems(data_gestationList);
		childBirthList.setItems(data_childBirthList);
		miscarriageList.setItems(data_miscarriageList);
		animalList.setItems(data_animalList);
		
	}
	/**
	 * 初始化  检测结果  
	 */
	private void initQuarantineTestData() {
		//初始化生化表
		 
		 animalCodeCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("animalCode"));         //动物编号
	     specimenCodeCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("specimenCode"));       //检验编号号
	     collectionTimeCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("collectionTime"));       //数据采集时间(检验时间)
	     bhaoCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("bhao"));
	     astCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("ast"));
	     altCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("alt"));
	     tpCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("tp"));
	     albCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("alb"));
	     ggtCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("ggt"));
	     tbilCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("tbil"));
	     bunCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("bun"));
	     creaCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("crea"));
	     gluCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("glu"));
	     tgCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("tg"));
	     cholCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("chol"));
	     ldhCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("ldh"));
	     ckCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("ck"));
	     naCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("na"));
	     kCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("k"));
	     ciCol.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("ci"));
	     /*esCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
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
	    	 
	     });*/
	     /*validFlagCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
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
	    	 
	     });*/
	     bioChemTable.setItems(data_bioChemTable);
	     //初始化血常规表
	     bhaoCol2.setCellValueFactory(new PropertyValueFactory<XcgData,String>("bhao"));             //检疫编号
	    
	     animalCodeCol2.setCellValueFactory(new PropertyValueFactory<XcgData,String>("animalCode"));         //动物编号
	     specimenCodeCol2.setCellValueFactory(new PropertyValueFactory<XcgData,String>("specimenCode"));       //检验编号号
	     
	     collectionTimeCol2.setCellValueFactory(new PropertyValueFactory<XcgData,String>("collectionTime"));       //数据采集时间(检验时间)
	     wbcCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("wbc"));
	     rbcCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("rbc"));
	     hgbCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("hgb"));
	     hctCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("hct"));
	     pltCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("plt"));
	     mcvCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("mcv"));
	     mchCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("mch"));
	     mchcCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("mchc"));
	     lymCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("lym"));
	     midCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("mid"));
	     graCol.setCellValueFactory(new PropertyValueFactory<XcgData,String>("gra"));
	     hematTable.setItems(data_hematTable);
	     //初始化病毒表     
	     animalCodeCol3.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("animalCode"));         //动物编号
	     specimenCodeCol3.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("specimenCode"));       //检验编号号
	     
	     collectionTimeCol3.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("collectionTime"));       //数据采集时间(检验时间)
	     xueqCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("xueq"));
	     bvCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("bv"));
	     stlvCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("stlv"));
	     srvCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("srv"));
	     sivCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("siv"));
	     filoCol.setCellValueFactory(new PropertyValueFactory<VirusTableData,String>("filo"));
	     
	     bloodCoagTable.setItems(data_bloodCoagTable);
	     //初始疫苗表
	     measlesCol.setCellValueFactory(new PropertyValueFactory<VaccineTableData,String>("measles"));             //数据Id
	     hepatitisACol.setCellValueFactory(new PropertyValueFactory<VaccineTableData,String>("hepatitisA"));
	     hepatitisBCol.setCellValueFactory(new PropertyValueFactory<VaccineTableData,String>("hepatitisB"));
	     
	     animalCodeCol4.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("animalCode"));         //动物编号
	     specimenCodeCol4.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("specimenCode"));       //检验编号号
	     
	     collectionTimeCol4.setCellValueFactory(new PropertyValueFactory<XyshTableData,String>("collectionTime"));       //数据采集时间(检验时间)
	     
	     urineTable.setItems(data_urineTable);
		
	}
	
	/**
	 * 更新常规记录的值
	 */
	private void updateNormalRecorder(String monkeyid){
		data_oestrusList.clear();
		data_gestationList.clear();
		data_childBirthList.clear();
		data_miscarriageList.clear();
		data_animalList.clear();
				
		 checkItemDate.setText("");
		 checkItemBoss.setText("");
		 
		if(null!=monkeyid){
			
			List<Leavebreast> leavebreastList=BaseService.getLeavebreastService().getLeavebreastById(monkeyid);
			for(Leavebreast le:leavebreastList){
				data_animalList.add("离乳日期："+le.getLeavebreastdate()+"	体重："+le.getLeavebreastweight());
			}
			animalList.setItems(data_animalList);
			
			List<Breeding_Json> oestrusList1=BaseService.getOestrusService().getAllOestrusById(monkeyid);
			for(Breeding_Json oestrus:oestrusList1){
				data_oestrusList.add("发情日期："+oestrus.getOestrusdate());
			}
			oestrusList.setItems(data_oestrusList);
			
			List<Gestation> gestationDataList=BaseService.getGestationService().getGestationById(monkeyid);
			for (Gestation ge:gestationDataList) {
				data_gestationList.add("检查日期：" + ge.getCheckdate());
				
			}
			gestationList.setItems(data_gestationList);
			
			List<Childbirth> childbirthList=BaseService.getChildbirthService().getChaildbirthById(monkeyid);
			for(Childbirth cb:childbirthList){
				data_childBirthList.add("分娩日期:"+cb.getLabordate().substring(0, 10));
			}
			childBirthList.setItems(data_childBirthList);
			
			List<Miscarriage_Json> miscarriageList1=BaseService.getMiscarriageService().getAllMiscarriageById(monkeyid);
			for(Miscarriage_Json ge:miscarriageList1){
				data_miscarriageList.add("流产日期:"+ge.getMiscarriagedate());
			}
			miscarriageList.setItems(data_miscarriageList);
			
		}
	}

	/**
	 * 更新检测结果的值(根据monkeyid)
	 */
	private void updateTestResult(String monkeyid,String checkId ){
		//清空表格的值
		data_bioChemTable.clear();
		data_hematTable.clear();
		data_bloodCoagTable.clear();
		data_urineTable.clear();
				
		if(null!=monkeyid){
			
			List<Virus_Json> virusList = BaseService.getVirusService().getVirusById(monkeyid,checkId);
			if (virusList!=null) {
				for (Virus_Json va : virusList) {
					VirusTableData data = new VirusTableData();
					data.setAnimalCode(monkeyid);
					data.setSpecimenCode(va.getTitle());
					data.setCollectionTime(va.getCdate() + "");
					data.setXueq(va.getXueq());
					data.setBv(va.getBv());
					data.setStlv(va.getStlv());
					data.setSrv(va.getSrv());
					data.setSiv(va.getSiv());
					data.setFilo(va.getFilo());
					data_bloodCoagTable.add(data);
				}
			}
			List<Xcg_Json> xcgList=BaseService.getXcgService().getXcgById(monkeyid,checkId);
			if (xcgList!=null) {
				for (Xcg_Json ob : xcgList) {
					XcgData data = new XcgData();
					data.setAnimalCode(ob.getMonkeyid());
					data.setCollectionTime(ob.getCdate()+"");
					data.setSpecimenCode( ob.getTitle());
					data.setBhao(ob.getBhao());
					data.setWbc(ob.getWbc());
					data.setRbc(ob.getRbc());
					data.setHgb(ob.getHgb());
					data.setHct(ob.getHct());
					data.setPlt(ob.getPlt());
					data.setMcv(ob.getMcv());
					data.setMch(ob.getMch());
					data.setMchc(ob.getMchc());
					data.setLym(ob.getLym());
					data.setMid(ob.getMid());
					data.setGra(ob.getGra());
					data_hematTable.add(data);
				}
			}
			
			List<Xysh_Json> xyshList = BaseService.getXyshService().getXyshById(monkeyid,checkId);
			if (xyshList!=null) {
				for (Xysh_Json ob : xyshList) {
					XyshTableData data = new XyshTableData();
					data.setAnimalCode(ob.getMonkeyid());
					data.setCollectionTime(ob.getCdate()+"");
					data.setSpecimenCode(ob.getTitle());
					data.setBhao(ob.getBhao());
					data.setAst(ob.getAst());
					data.setAlt(ob.getAlt());
					data.setTp(ob.getTp());
					data.setAlb(ob.getAlb());
					data.setGgt(ob.getGgt());
					data.setTbil(ob.getTbil());
					data.setBun(ob.getBun());
					data.setCrea(ob.getCrea());
					data.setGlu(ob.getGlu());
					data.setTg(ob.getTg());
					data.setChol(ob.getChol());
					data.setLdh(ob.getLdh());
					data.setCk(ob.getCk());
					data.setNa(ob.getNa());
					data.setK(ob.getK());
					data.setCi(ob.getCi());
					data_bioChemTable.add(data);
				}
			}
			//1,获取该动物疫苗检疫(3条只取一条)
			List<Vaccine_Json> vaccineList=BaseService.getVaccineService().getVaccineById(monkeyid,checkId);
			if (vaccineList!=null) {
				for (Vaccine_Json va : vaccineList) {
					
					VaccineTableData data = new VaccineTableData();
					data.setAnimalCode(monkeyid);
					data.setSpecimenCode(va.getTitle());
					data.setCollectionTime(va.getCdate() + "");
					
					String monkeyId=va.getMonkeyid();
					String normalId=va.getNormal_id()+"";
					if(monkeyid!=null&&!"".equals(monkeyid)){
						//2,获取该动物疫苗检疫
						List<?> li=BaseService.getVaccineService().getVaccineId(monkeyId, normalId);
						for(Object oj:li){
							Object[]ojs=(Object[])oj;
							if((ojs[0]==null||"".equals(ojs[0]))&& (ojs[1]==null||"".equals(ojs[1]))){
								
							}
							if (ojs!=null&&!"".equals(ojs)) {
								String vaccineName = null;
								if (!"".equals(ojs[0])&&ojs[0]!=null) {
									Long id=Long.valueOf((String) ojs[0]);
									if(id!=null){
										//3,获取该动物的疫苗
										Quarantine q = BaseService.getQuarantineService().getById(id);
										vaccineName=q.getName();
									}
								}
								if (("Measles").equals(vaccineName)) {
									data.setMeasles((String)ojs[1]);
																	
								}else{
								}
								if (("HepatitisA").equals(vaccineName)) {
									data.setHepatitisA((String)ojs[1]);
								}else{
								}
								if (("HepatitisB").equals(vaccineName)) {
									data.setHepatitisB((String)ojs[1]);
								}else{
								}
							}
						}
					}
					
					data_urineTable.add(data);
				}
			}
			
			//检测结果列表 
			bioChemTable.setItems(null);
			urineTable.setItems(null);
			bloodCoagTable.setItems(null);
			hematTable.setItems(null);
			
			if(currentTabNo == 1){
				bioChemTable.setItems(data_bioChemTable);
				urineTable.setItems(data_urineTable);
				bloodCoagTable.setItems(data_bloodCoagTable);
				hematTable.setItems(data_hematTable);
			}
			
			
		}
		
	}
	
	
	/**
	 * 初始化   区域树  
	 */
	private void initAreaTree() {
		rootNode.setValue("区域");
		rootNode.setExpanded(true);
		areaTree.setRoot(rootNode);
		areaTree.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
			@Override
			public TreeCell<String> call(TreeView<String> arg0) {
				return new TreeFormatCell();
			}});
		List<Map<String,Object>> size=BaseService.getAreaService().getAllPareaIdName();
		for(Map<String,Object> m:size){
			TreeItem<String> item = new TreeItem<String> (m.get("text")+"");              
            rootNode.getChildren().add(item);
            List<Map<String,Object>> child=BaseService.getAreaService().getAllRoomIdName(Long.parseLong(m.get("id")+""));
            for(Map<String,Object> m2:child){
            	TreeItem<String> childItem=new TreeItem<String>(m2.get("text")+"");
            	item.getChildren().add(childItem);
            	List<String> animals=(List<String>) BaseService.getAreaService().getAnimalByArea(Long.parseLong(m2.get("id")+""));
            	for(String ob:animals){
            		TreeItem<String> childres=new TreeItem<String>(ob);
            		childItem.getChildren().add(childres);
            	}
            }
		}
		areaTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){
			
			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
					TreeItem<String> newValue) {
				//清空临检树2的选中状态
				keeperTree2.getSelectionModel().clearSelection();
				
				List<?> listArea=BaseService.getAreaService().getAllAreaList();
				
				for(Object ob:listArea){
					Object[]objs=(Object[])ob;
					Area area=new Area();
					area.setId(Long.parseLong(objs[0]+""));
					area.setAreaname(objs[1]+"");
					areaMap.put(area.getAreaname(), area);
				}
				if(null!=newValue&&!"".equals(newValue.getValue())){
					if(areaMap.keySet().contains(newValue.getValue())){//选中的是区域。
						
					}else {//选中的是动物个体。
						//父节点值
						String parentValue=newValue.getParent().getValue().toString();
						
						String newValueString=newValue.getValue().toString();
						
						if(!areaMap.keySet().contains(parentValue)){
							parentValue =newValue.getParent().getParent().getValue().toString();
						}
						Individual_Json individual=BaseService.getIndividualService().getIndividualJsonById(newValue.getValue());
						
						//更新  动物信息  区域  的所有Text
						updateAllText(individual);
						
						//更新常规记录的信息。
						updateNormalRecorder(newValueString);
						
						//更新   检测结果    面板
						updateTestResult(newValueString,null);
						
						//给检疫编号赋值.
						initCheckItemIdComboBox(newValueString);
						
						//更新观察记录
						updateObservationRecoder(newValueString);
						
						//更新称重记录
						updateWeightRecoder(newValueString);
						
						//更新调栏记录
						updateChangeroomRecoder(newValueString);
						
					}
				}else{
					//更新  课题信息  区域  的所有Text置空
					updateAllText(null);
					//更新常规记录的值
					updateNormalRecorder(null);
					
				}
			}});
	}
	/**
	 * 更新   区域树  的值    --在场动物
	 */
	private void updateInAnimalsByAreaTree(){
		rootNode.getChildren().clear();
		//查询区域
		List<Map<String,Object>> areaList=BaseService.getAreaService().getAllPareaIdName();
		if(areaList.size()>0&&areaList!=null){
			for(Map<String,Object> m:areaList){
				TreeItem<String> depNode =new TreeItem<String>(m.get("text")+"");//节点	
				rootNode.getChildren().add(depNode);
				//区域下的房舍
				List<Map<String,Object>> childAreaList=BaseService.getAreaService().getAllRoomIdName(Long.valueOf(""+m.get("id")));
				if(childAreaList.size()>0&&childAreaList!=null){
					
					for(Map<String,Object> m2:childAreaList){
							TreeItem<String> leaf =null;
							leaf = new TreeItem<String>(m2.get("text")+"");
							depNode.getChildren().add(leaf);	
						//查询下属在场动物.
						List<String> list=(List<String>) BaseService.getAreaService().getInAnimal(Long.valueOf(m2.get("id")+""));
						for(String ob:list){
							TreeItem<String> animal=new TreeItem<String>(ob);
							leaf.getChildren().add(animal);
						}
					}
				}
			}
		}
		
	}
	/**
	 * 更新   区域树  的值    --全部
	 */
	private void updateAnimalsByAreaTree(){
		rootNode.getChildren().clear();
		
		//查询区域
		List<Map<String,Object>> areaList=BaseService.getAreaService().getAllPareaIdName();
		if(areaList.size()>0&&areaList!=null){
			for(Map<String,Object> m:areaList){
				TreeItem<String> depNode =new TreeItem<String>(m.get("text")+"");//节点	
				rootNode.getChildren().add(depNode);
				//区域下的房舍
				List<Map<String,Object>> childAreaList=BaseService.getAreaService().getAllRoomIdName(Long.valueOf(""+m.get("id")));
				if(childAreaList.size()>0&&childAreaList!=null){
					
					for(Map<String,Object> m2:childAreaList){
							TreeItem<String> leaf =null;
							leaf = new TreeItem<String>(m2.get("text")+"");
							depNode.getChildren().add(leaf);	
						//查询下属在场动物.
						List<String> list=(List<String>) BaseService.getAreaService().getAnimalByArea(Long.valueOf(m2.get("id")+""));
						for(String ob:list){
							TreeItem<String> animal=new TreeItem<String>(ob);
							leaf.getChildren().add(animal);
						}
					}
				}
			}
		}
		
	}
	/**
	 * 更新   区域树  的值    --待销售动物
	 */
	private void updateOutAnimalsByAreaTree(){
		rootNode.getChildren().clear();
		//查询区域
		List<Map<String,Object>> areaList=BaseService.getAreaService().getAllPareaIdName();
		if(areaList.size()>0&&areaList!=null){
			for(Map<String,Object> m:areaList){
				TreeItem<String> depNode =new TreeItem<String>(m.get("text")+"");//节点	
				rootNode.getChildren().add(depNode);
				//区域下的房舍
				List<Map<String,Object>> childAreaList=BaseService.getAreaService().getAllRoomIdName(Long.valueOf(""+m.get("id")));
				if(childAreaList.size()>0&&childAreaList!=null){
					
					for(Map<String,Object> m2:childAreaList){
							TreeItem<String> leaf =null;
							leaf = new TreeItem<String>(m2.get("text")+"");
							depNode.getChildren().add(leaf);	
						//查询下属在场动物.
						List<String> list=(List<String>) BaseService.getAreaService().getOutAnimal(Long.valueOf(m2.get("id")+""));
						for(String ob:list){
							TreeItem<String> animal=new TreeItem<String>(ob);
							leaf.getChildren().add(animal);
						}
					}
				}
			}
		}
		
	}
	/**
	 * 显示在场CheckBox  的onAction事件
	 * @param event
	 */
	@FXML
	private void  onShowMonkeyInButnAction(ActionEvent event){
		RadioButton checkBox=(RadioButton)event.getSource();
		if(checkBox.isSelected()){
			monkeyInButn.setSelected(true);
			
			//更新区域树
			updateInAnimalsByAreaTree();
			//更新饲养员
			updateInAnimalsByKeeperTree2();
			
			//清空检疫编号Combobox
			data_checkItemId.clear();
			//更新  动物信息  区域  的所有Text
			updateAllText(null);
			//更新常规记录的信息。
			updateNormalRecorder(null);
			//清空检疫结果
			updateTestResult(null,null);
			//清空观察记录
			updateObservationRecoder(null);
			//清空称重记录
			updateWeightRecoder(null);
			//清空调栏记录
			updateChangeroomRecoder(null);
		}else{
			monkeyInButn.setSelected(false);
			updateAnimalsByAreaTree();
			updateAnimalsByKeeperTree2();
		}
	}
	/**
	 * 显示待销售CheckBox  的onAction事件
	 * @param event
	 */
	@FXML
	private void  onShowMonkeyOutButnAction(ActionEvent event){
		RadioButton radio=(RadioButton)event.getSource();
		if(radio.isSelected()){
			monkeyOutButn.setSelected(true);
			updateOutAnimalsByAreaTree();
			updateOutAnimalsByKeeperTree2();
			//清空检疫编号Combobox
			data_checkItemId.clear();
			//更新  动物信息  区域  的所有Text
			updateAllText(null);
			//更新常规记录的信息。
			updateNormalRecorder(null);
			//清空检疫结果
			updateTestResult(null,null);
			//清空观察记录
			updateObservationRecoder(null);
			//清空称重记录
			updateWeightRecoder(null);
			//清空调栏记录
			updateChangeroomRecoder(null);
		}else{
			monkeyOutButn.setSelected(false);
			updateAnimalsByAreaTree();
			updateAnimalsByKeeperTree2();
			
		}
		
	}
	/**
	 * 显示全部CheckBox  的onAction事件
	 * @param event
	 */
	@FXML
	private void  onShowAllMonkeyButnAction(ActionEvent event){
		RadioButton radio=(RadioButton)event.getSource();
		if(radio.isSelected()){
			updateAnimalsByAreaTree();
			updateAnimalsByKeeperTree2();
			//清空检疫编号Combobox
			data_checkItemId.clear();
			//更新  动物信息  区域  的所有Text
			updateAllText(null);
			//更新常规记录的信息。
			updateNormalRecorder(null);
			//清空检疫结果
			updateTestResult(null,null);
			//清空观察记录
			updateObservationRecoder(null);
			//清空称重记录
			updateWeightRecoder(null);
			//清空调栏记录
			updateChangeroomRecoder(null);
		}else{
			allMonkeyButn.setSelected(false);
			updateAnimalsByAreaTree();
			updateAnimalsByKeeperTree2();
			
		}
		
	}
	/**
	 * 初始化  检疫编号
	 */
	private void initCheckItemIdComboBox(String monkeyid) {
		final String  monkeyId=monkeyid;
		data_checkItemId.clear();

		checkItemId.setItems(data_checkItemId);
		List<Normal> checkItemIdStrList = BaseService.getIndividualService().getCheckInfoById(monkeyid);
		if(checkItemIdStrList!=null){
			for(Normal normal:checkItemIdStrList){
				data_checkItemId.add(normal.getTitle());
			}
		}
		checkItemId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					
					//检疫编号对应的检疫日期.检疫
					updateCheckItemInfo(newValue);
					//检疫结果只显示该编号的检疫信息。
					Normal_Json normal=BaseService.getNormalService().getCheckItemInfo(newValue);
					updateTestResult(monkeyId,normal.getId()+"");
				}
			}});
		
		
		checkItemId.getSelectionModel().clearAndSelect(0);//加载第一项选中
	}
	/**
	 * 更新检疫信息.
	 */
	private void updateCheckItemInfo(String title) {
		checkItemDate.clear();
		checkItemBoss.clear();
		Normal_Json normal=BaseService.getNormalService().getCheckItemInfo(title);
		if(normal!=null){
			checkItemDate.setText(normal.getCheckdate().toString());
			checkItemBoss.setText(normal.getBossName());
		}else{
			checkItemDate.setText("");
			checkItemBoss.setText("");
		}
		
		
	}
	/**
	 * 更新区域基本信息的值
	 * @param entity
	 */
	private void updateAllText(Individual_Json entity){
		if(null!=entity){
			monkeyNoLabel.setText(entity.getMonkeyid());           //动物编号
			monkeyKeeperLabel.setText(entity.getKeeperp());     //饲养员
			
			monkeyNoText.setText(entity.getMonkeyid());            //动物编号
			if(null!=entity.getSex()&&entity.getSex()==0){
				monkeySexTypeText.setText("公");//动物性别
			}else{
				monkeySexTypeText.setText("母");
			}
			monkeyKeeperText.setText(entity.getKeeperp());       //饲养人
			if(entity.getQuyu()!=null){
				areaNameText.setText(entity.getQuyu()+":"+entity.getRoomName());                  //区域房舍  
			}
			animalTypeText.setText(entity.getAnimaltypeName());          //动物种类
			if (entity.getAgetype()==1) {
				animalAgeText.setText("仔猴"); //年龄阶段
			}else if(entity.getAgetype()==2){
				animalAgeText.setText("育成猴"); //年龄阶段
			}else{
				animalAgeText.setText("成年猴"); //年龄阶段
			}
			monkeyBirthdayText.setText(entity.getBirthday());          //出生日期
			monkeyLeavebreastDateText.setText(entity.getLeavebreastdate());//离乳日期
		}else{
			
			monkeyNoLabel.setText(""); // 动物编号
			monkeyKeeperLabel.setText(""); // 饲养员

			monkeyNoText.setText(""); // 动物编号
			monkeySexTypeText.setText("");// 动物性别
			monkeyKeeperText.setText(""); // 饲养员
			areaNameText.setText(""); // 区域房舍
			animalTypeText.setText(""); // 动物种类
			animalAgeText.setText(""); // 年龄阶段
			monkeyBirthdayText.setText(""); // 出生日期
			monkeyLeavebreastDateText.setText("");// 离乳日期
		
		}
	}
	/**
	 * 设备登记菜单onAction事件
	 */
	@FXML
	private void onInstrumentMenuAction(ActionEvent event){

	}
	/**
	 * 设备检定信息 菜单onAction事件
	 */
	@FXML
	private void onInstrumentVerificationMenuAction(ActionEvent event){

	}
	/**
	 * 通道号设置 菜单onAction事件
	 */
	@FXML
	private void onPassagewayMenuAction(ActionEvent event){

	}
	/**
	 * 临时任务 菜单项onAction 事件
	 */
	@FXML
	private void onTempTaskMenuAction(ActionEvent event){

	}
	
	/**
	 * 审计菜单onAction事件 
	 * @param event
	 */
	@FXML
	private void onAuditMenuAction(ActionEvent event){
		
	}
	/**
	 * 修改密码 菜单onAction事件
	 */
	@FXML
	private void onAlterPasswordMenuAction(ActionEvent event){
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
			
			List<Employee> list=BaseService.getUserService().findByPrivilegeName("客户端_登录");
			//存放有   客户端登录   权限 的用户列表
			SaveUserInfo.setClinicalTestUserList(list);
		}
	}
	/**
	 * 退出菜单项onAction事件
	 * @param event
	 */
	@FXML
	private void OnExitMenuAction(ActionEvent event){
		if(AnimalsInfoFrameController.isUsed){
			if(Messager.showConfirm("提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
				if (MainFrameController.dataAcceptItem==0) {
					AnimalsInfoFrameController.serialPort
							.removeEventListener();
					AnimalsInfoFrameController.serialPort
							.notifyOnDataAvailable(false);
					AnimalsInfoFrameController.serialPort.close();
					AnimalsInfoFrameController.isUsed = false;
					MainFrameController.comPortIsUsed=false;
				}
				if (MainFrameController.dataAcceptItem==1) {
					AnimalsWeightFrameController.serialPort
							.removeEventListener();
					AnimalsWeightFrameController.serialPort
							.notifyOnDataAvailable(false);
					AnimalsWeightFrameController.serialPort.close();
					AnimalsWeightFrameController.isUsed = false;
					MainFrameController.comPortIsUsed=false;
				}
				if (MainFrameController.dataAcceptItem==2) {
					AnimalsObservationFrameController.serialPort
							.removeEventListener();
					AnimalsObservationFrameController.serialPort
							.notifyOnDataAvailable(false);
					AnimalsObservationFrameController.serialPort
							.close();
					AnimalsObservationFrameController.isUsed = false;
					MainFrameController.comPortIsUsed=false;
				}
				
				Iplogin tblLog = new Iplogin();
				
				Employee user = SaveUserInfo.getUser();
				if(null!=user){
					tblLog.setCreated_by(user.getId());
				}
				tblLog.setIp(SystemTool.getIPAddress());
				try {
					BaseService.getIploginService().save(tblLog);
				} catch (Exception e) {
					System.exit(1);
				}
				System.exit(1);
				
			}else{
				event.consume();
			}
			
		}else{
			if(Messager.showSimpleConfirm("提示", "确定要退出系统吗？")){
				Iplogin tblLog = new Iplogin();
				
				Employee user = SaveUserInfo.getUser();
				if(null!=user){
					tblLog.setCreated_by(user.getId());
				}
				tblLog.setIp(SystemTool.getIPAddress());
				try {
					BaseService.getIploginService().save(tblLog);
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
	 * RFID数据接收收按钮事件
	 * 
	 */
	@FXML
	private void onAnimalsInfoByRFIDBtnAction(ActionEvent event){
		
		Stage stage = Main.stageMap.get("AnimalsInfoFrameController");
		dataAcceptItem=0;
		if(null == stage){
			try {
				stage =new Stage();
				AnimalsInfoFrameController.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AnimalsInfoFrameController",stage);
		}
		if(!stage.isShowing()){
			AnimalsInfoFrameController.getInstance().loadData();
			stage.showAndWait();
			if(AnimalsInfoFrameController.getInstance().isHasAdd()){
				//清空子面板
				AnimalsInfoFrameController.getInstance().clearReceiveData();
				//更新主面板
				String monkeyid=AnimalsInfoFrameController.getInstance().getMonkeyId();
				Individual_Json ij=BaseService.getIndividualService().getIndividualJsonById(monkeyid);
				updateAllText(ij);
				updateNormalRecorder(monkeyid);
				updateTestResult(monkeyid,null);
				initCheckItemIdComboBox(monkeyid);
				updateObservationRecoder(monkeyid);
				updateWeightRecoder(monkeyid);
				updateChangeroomRecoder(monkeyid);
			}
			
		}else{
			stage.centerOnScreen();
			stage.toFront();
		}
	}
	
	/**
	 * 刷新按钮事件
	 */
	@FXML
	private void onRefreshButton(){
		//更新  动物信息  区域  的所有Text 置空
		updateAllText(null);
		data_checkItemId.clear();
		//更新常规记录区域   置空
		updateNormalRecorder(null);
		//更新    检测结果    面板
		updateTestResult(null,null);
		//更新 检疫信息
		//更新 观察信息
		updateObservationRecoder(null);
		//更新 称重信息
		updateWeightRecoder(null);
		//更新 调栏信息
		updateChangeroomRecoder(null);
		//清空动物编号
		updateQueryMonkeyTextField();
	}
	/**
	 * 刷新按钮事件
	 */
	@FXML
	private void onRefreshButton1() {
			//检疫编号清空
			data_checkItemId.clear();
			// 更新动物信息区域 清空
			updateAllText(null);
			// 更新常规记录 区域 清空
			updateNormalRecorder(null);
			// 更新 检测结果 面板
			updateTestResult(null,null);
			//更新 观察信息
			updateObservationRecoder(null);
			//更新 称重信息
			updateWeightRecoder(null);
			//更新 调栏信息
			updateChangeroomRecoder(null);
			//更新饲养原选择框
			updateKeeperComboBox();
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
    
    /**
     * 根据编号查询
     */
    @FXML
    private void onQueryMonkeyByIdBtnAction(ActionEvent even){
    	//更新动物信息.
    	updateAllText(null);
    	String monkeyid = queryMonkeyText.getText();
    	Individual_Json individual=BaseService.getIndividualService().getIndividualJsonById(monkeyid);
    	updateAllText(individual);
    	//更新检疫编号
    	initCheckItemIdComboBox(monkeyid);
    	//更新常规记录信息
    	updateNormalRecorder(monkeyid);
    	//更新检疫信息
    	updateTestResult(monkeyid,null);
    	//更新观察记录
    	updateObservationRecoder(monkeyid);
    	//更新称重记录
    	updateWeightRecoder(monkeyid);
    	//更新 调栏记录
    	updateChangeroomRecoder(monkeyid);
    	//选中动物
		updateSelectMonkey();
    	
    }
    
    /**
	 * 初始化观察记录表  
	 */
	private void initObservationData() {
		 
		 monkeyidCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("animalCode"));
	     quyuCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("quyuName"));       
	     sexCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("animalSex"));       
	     contentCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("content"));
	     observationtimeCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("observationTime"));
	     observerCol.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("observerName"));
	     
	     observationTable.setItems(data_observationTable);
		
	}
	/**
	 * 更新观察记录(根据monkeyid)
	 */
	private void updateObservationRecoder(String monkeyid ){
		//清空表格的值
		data_observationTable.clear();
		
		//非空
		if(null!=monkeyid){
			
			List<?> observationList = BaseService.getObservationService().getAllObservationById(monkeyid);
			if (observationList!=null) {
				for (Object ob : observationList) {
					Object[]objs=(Object[])ob;
					ObservationTableData data = new ObservationTableData();
					data.setAnimalCode(monkeyid);
					data.setAnimalSex(objs[1]=="0"?"公":"母");
					data.setQuyuName((String)objs[2]+""+objs[3]);
					data.setContent((String)objs[5]);
					data.setObserverName((String)objs[7]);
					data.setObservationDate((String)objs[8]);
					data.setObservationTime((String) objs[6]);
					
					data_observationTable.add(data);
				}
			}

			//观察记录列表 
			observationTable.setItems(null);
			
			if(currentTabNo == 2){
				observationTable.setItems(data_observationTable);
			}
			
			
		}
	}
	/**
	 * 初始化称重记录表  
	 */
	private void initWeightData() {
		 
		 monkeyidCol1.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("animalCode"));
	     areaNameCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("quyuName"));       
	     animalSexCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("animalSex"));       
	     weightCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("weight"));
	     weightDateCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("weightDate"));
	     bossNameCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("bossName"));
	     
	     weightTable.setItems(data_weightTable);
		
	}
	/**
	 * 更新称重记录(根据monkeyid)
	 */
	private void updateWeightRecoder(String monkeyid ){
		//清空表格的值
		data_weightTable.clear();
		
		//非空
		if(null!=monkeyid){
			
			List<?> weightList = BaseService.getWeightService().getAllWeightById(monkeyid);
			if (weightList!=null) {
				for (Object ob : weightList) {
					Object[]objs=(Object[])ob;
					WeightTableData data = new WeightTableData();
					data.setAnimalCode(monkeyid);
					data.setAnimalSex(objs[5]=="0"?"公":"母");
					data.setWeight((Float)objs[2]);
					data.setWeightDate((String)objs[3]);
					data.setBossName((String)objs[4]);
					data.setQuyuName(objs[6]+":"+objs[7]);
					data_weightTable.add(data);
				}
			}

			//称重记录列表 
			weightTable.setItems(null);
			
			if(currentTabNo == 3){
				weightTable.setItems(data_weightTable);
			}
			
			
		}
	}
	/**
	 * 初始化调栏记录表  
	 */
	private void initChangeroomData() {
		 
		 monkeyidCol2.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("animalCode"));
	     animalSexCol2.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("animalSex"));       
	     yAreaNameCol.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("yAreaName"));
	     yKeeperNameCol.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("yKeeperName"));
	     xAreaNameCol.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("xAreaName"));
	     xKeeperNameCol.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("xKeeperName"));
	     changeroomDateCol.setCellValueFactory(new PropertyValueFactory<ChangeroomTableData,String>("changeroomDate"));
	     changeroomTable.setItems(data_changeroomTable);
		
	}
	/**
	 * 更新调栏记录(根据monkeyid)
	 */
	private void updateChangeroomRecoder(String monkeyid ){
		//清空表格的值
		data_changeroomTable.clear();
		
		//非空
		if(null!=monkeyid){
			//获取该动物
			Individual_Json in=BaseService.getIndividualService().getIndividualJsonById(monkeyid);
			List<Changeroom> changeroomList = BaseService.getChangeroomService().getChangeroomById(monkeyid);
			if (changeroomList!=null) {
				for (Changeroom chg : changeroomList) {
					ChangeroomTableData data = new ChangeroomTableData();
					data.setAnimalCode(monkeyid);
					data.setAnimalSex(in.getSex().toString()=="0"?"公":"母");
					//
					Area area=BaseService.getAreaService().getById(Long.parseLong(chg.getChangeinarea()));
					Area area1=BaseService.getAreaService().getById(Long.parseLong(chg.getChangeinroom()));
					Area area2=BaseService.getAreaService().getById(chg.getYarea());
					Area area3=BaseService.getAreaService().getById(chg.getYroom());
					data.setYAreaName(area2.getAreaname()+":"+area3.getAreaname()+"("+chg.getYlh()+"笼)");
					data.setXAreaName(area.getAreaname()+":"+area1.getAreaname()+"("+chg.getLhao()+"笼)");
					Employee user=BaseService.getUserService().getById(chg.getYkeeper());
					Employee user1=BaseService.getUserService().getById(Long.parseLong(chg.getOperater()));
					data.setXKeeperName(user1.getName());
					data.setYKeeperName(user.getName());
					data.setChangeroomDate(DateUtil.dateToString(chg.getChangeroomdate(), "yyyy-MM-dd"));
					data_changeroomTable.add(data);
				}
			}

			//调栏记录列表 
			changeroomTable.setItems(null);
			
			if(currentTabNo == 4){
				changeroomTable.setItems(data_changeroomTable);
			}
			
			
		}
	}
	/**
	 * 动物称重
	 * @param even
	 */
	@FXML
	private void onAnimalsWeightButnAction(ActionEvent even){
		Stage stage = Main.stageMap.get("AnimalsWeightFrameController");
		dataAcceptItem=1;
		if(null == stage){
			try {
				stage =new Stage();
				AnimalsWeightFrameController.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AnimalsWeightFrameController",stage);
		}
		if(!stage.isShowing()){
			AnimalsWeightFrameController.getInstance().loadData();
			stage.showAndWait();
			if(AnimalsWeightFrameController.getInstance().isHasAdd()){
				AnimalsWeightFrameController.getInstance().clearReceiveData();
			}
		}else{
			stage.centerOnScreen();
			stage.toFront();
		}
	}
	
	/**
	 * 动物观察
	 * @param even
	 */
	@FXML
	private void onAnimalsObservationAction(ActionEvent even){
		Stage stage = Main.stageMap.get("AnimalsObservationFrameController");
		dataAcceptItem=2;
		if(null == stage){
			try {
				stage =new Stage();
				AnimalsObservationFrameController.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AnimalsObservationFrameController",stage);
		}
		if(!stage.isShowing()){
			AnimalsObservationFrameController.getInstance().loadData();
			stage.showAndWait();
			//数据是否有增加
			if(AnimalsObservationFrameController.getInstance().isHasAdd()){
				AnimalsObservationFrameController.getInstance().clearReceiveData();
			}
		}else{
			stage.centerOnScreen();
			stage.toFront();
		}
	}
	/**
	 * 初始化keeper
	 */
	private void initKeeperComboBox(){
		keeperComboBox.setItems(data_keeperName);

		Map<String, Object> listMap = BaseService.getUserService().getAllEmployee();
		Set set=listMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> m=(Map.Entry<String, Object>) it.next();
			data_keeperName.add(m.getKey());
		}
		
		keeperNameIDMap=listMap;
		
		keeperComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					
					keeperId=keeperNameIDMap.get(newValue).toString();
					//更新饲养员树,选中的打开，未选中关闭
					Iterator it=rootNode2.getChildren().iterator();
					while(it.hasNext()){
						TreeItem<String> tt=(TreeItem<String>) it.next();
						if(newValue.equals(tt.getValue())){
							tt.setExpanded(true);
						}else{
							tt.setExpanded(false);
						}
					}
				}
		}});
	}
	/**
	 * 清空饲养员选择框
	 */
	private void updateKeeperComboBox(){
		data_keeperName.clear();
		if(monkeyInButn.isSelected()){
			monkeyInButn.setSelected(false);
		}
		if(monkeyOutButn.isSelected()){
			monkeyOutButn.setSelected(false);
		}
		if(allMonkeyButn.isSelected()){
			allMonkeyButn.setSelected(false);
		}
	}
	/**
	 * 清空动物编号
	 */
	private void updateQueryMonkeyTextField(){
		queryMonkeyText.clear();
		if(monkeyInButn.isSelected()){
			monkeyInButn.setSelected(false);
		}
		if(monkeyOutButn.isSelected()){
			monkeyOutButn.setSelected(false);
		}
		if(allMonkeyButn.isSelected()){
			allMonkeyButn.setSelected(false);
		}
	}
	
	/**
	 * 选中动物
	 */
	private void updateSelectMonkey(){
		String monkey=queryMonkeyText.getText();
		
		//更新树,打开
		Iterator it=rootNode.getChildren().iterator();
		while(it.hasNext()){
			TreeItem<String> tt=(TreeItem<String>) it.next();
			Iterator it2=tt.getChildren().iterator();
			while(it2.hasNext()){
				TreeItem<String> tt2=(TreeItem<String>)it2.next();
				Iterator it3=tt2.getChildren().iterator();
				while(it3.hasNext()){
					TreeItem<String> tt3=(TreeItem<String>)it3.next();
					if(tt3.getValue().equals(monkey)){
						tt.setExpanded(true);
						tt2.setExpanded(true);
						//选中
					}
				}
			}
		}
	}
}
