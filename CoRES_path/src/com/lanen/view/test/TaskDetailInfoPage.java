package com.lanen.view.test;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.lanen.base.BaseService;
import com.lanen.model.path.DictViscera;
import com.lanen.util.DateUtil;
import com.lanen.util.POIExcelUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.ChooseOneDate;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.MainPageController.AnatomyTask;
import com.lanen.view.sign.Sign;
import com.lanen.view.test.DataValidation.AnatomyCheck;
import com.lanen.view.test.DataValidation.VisceraFixed;
import com.lanen.view.test.DataValidation.VisceraFixedCompare;
import com.lanen.view.test.DataValidation.visceraWeight;

public class TaskDetailInfoPage  extends Application implements Initializable {
	
	//Ctrl键 被按下
	private boolean ctrPressed = false;
	private static List<Map<String,Object>> taskMapList;
	private Map<String,String> studyNoAnatomyNumTaskIdMap = new HashMap<String,String>();
	private static TreeItem<String> itemChildren;
	private static TreeItem<String> itemParent; 
	private static AnatomyTask anatomyTask;
	@FXML
	private TabPane tabPane;// 标签面板TabPane
	@FXML
	private Tab anatomyTab; // 动物解剖Tab
	@FXML
	private Tab weightTab;  // 脏器称重Tab
	@FXML
	private Tab fixTab;     // 脏器固定Tab
	@FXML
	private Tab aninalTab;  // 动物信息Tab
	@FXML
	private ComboBox<String> studyNoComboBox;  //专题下拉框
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	//private ComboBox<String> taskComboBox;  //任务下拉框
	@FXML
	private TextField taskTextField;
	@FXML
	private ListView<String> taskListView;
	private ObservableList<String> data_taskComboBox = FXCollections.observableArrayList();
	
	//-------------------剖检---------------------------------
	@FXML
	private ComboBox<String> anatomyCheckSessionComboBox;  //剖检-会话下拉框
	private ObservableList<String> data_anatomyCheckSessionComboBox = FXCollections.observableArrayList();
	private Map<String,Object> antomySessionMap=new HashMap<String,Object>();
	@FXML
	private ComboBox<String> anatomyCheckVisceraNameComboBox;  //剖检-脏器下拉框
	private ObservableList<String> data_anatomyCheckVisceraNameComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> checkResultComboBox;  //剖检结果下拉框
	private ObservableList<String> data_checkResultComboBox = FXCollections.observableArrayList();
	@FXML
	private ListView<String> anatomyAnimalList;    //剖检-动物列表
	private ObservableList<String> data_anatomyAnimalList = FXCollections.observableArrayList();
	@FXML
	private TableView<AnatomyCheck> anatomyCheckTable; //解剖所见
	private ObservableList<AnatomyCheck> data_anatomyCheckTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<AnatomyCheck,String> idCol;        //主键
	
	/*@FXML
	private TableColumn<AnatomyCheck,String> astudyNoCol;        //专题编号
	*/
	@FXML
	private TableColumn<AnatomyCheck,String> animalCodeCol;//动物编号

	@FXML
	private TableColumn<AnatomyCheck,String> visceraNameCol;//脏器名称
	
	@FXML
	private TableColumn<AnatomyCheck,String> anatomyFindingCol;//解剖所见
	
	@FXML
	private TableColumn<AnatomyCheck,String> operatorCol;//操作者
	
	@FXML
	private TableColumn<AnatomyCheck,String> operateTimeCol;//操作时间
	
	@FXML
	private Button saveAnatomyAsExcel;//转出为excel
	
	@FXML
	private Label anatomyCheckLabel;//解剖所见-统计标签

	//----------------------------------------------------
	
	//-------------------称重---------------------------------
	@FXML
	private ComboBox<String> weightSessionComboBox;  //称重-会话下拉框
	private ObservableList<String> data_weightSessionComboBox = FXCollections.observableArrayList();
	private Map<String,Object> weightSessionMap=new HashMap<String,Object>();
	@FXML
	private ComboBox<String> weightVisceraNameComboBox;  //称重-脏器下拉框
	private ObservableList<String> data_weightVisceraNameComboBox = FXCollections.observableArrayList();
	@FXML
	private ListView<String> weightAnimalList;    //称重-动物列表
	private ObservableList<String> data_weightAnimalList = FXCollections.observableArrayList();
	@FXML
	private TabPane weightTabPane;
	@FXML
	private Tab weightDetailTab; // 称重-详细Tab
	@FXML
	private Tab weightCompareTab; // 称重-对比Tab
	private List<String> tableStructure = new ArrayList<>();
	@FXML
	private TableView<visceraWeight> weightDetailTable; //脏器称重-详细信息
	private ObservableList<visceraWeight> data_weightDetailTable = FXCollections.observableArrayList();
	
	private List<DictViscera> visceraSortList = FXCollections.observableArrayList();//按称重顺序排序
	
	@FXML
	private TableView<Map<String, List<visceraWeight>>> weightCompareTable; //脏器称重-对比信息
	private ObservableList<Map<String, List<visceraWeight>>> data_weightCompareTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<visceraWeight,String> widCol;//主键
	
	/*@FXML
	private TableColumn<visceraWeight,String> wstudyNoCol;//专题编号
	*/
	@FXML
	private TableColumn<visceraWeight,String>  wanimalCodeCol;//动物编号
	
	@FXML
	private TableColumn<visceraWeight,String>  wvisceraNameCol;//脏器名称
	
	@FXML
	private TableColumn<visceraWeight,String>  weightCol;//体重
	@FXML
	private TableColumn<visceraWeight,String>  fixedWeighFlagCol;//是否固定后称重   0：否；1：是
	
	@FXML
	private TableColumn<visceraWeight,String>  attachedVisceraNamesCol;//附加脏器列表 
	
	@FXML
	private TableColumn<visceraWeight,String> woperatorCol;//操作者
	
	@FXML
	private TableColumn<visceraWeight,String>  woperateTimeCol;//操作时间
	
	/*@FXML
	private Button previewWeightCompareTable;//预览
	*/
	@FXML
	private Button saveWeightAsExcel;//脏器称重的导出为excel。
	
	
	@FXML
	private TableColumn<visceraWeight,String>  balCodeCol;//采集设备编号
	@FXML
	private Label visceraWeightLabel;//脏器称重-统计标签
	
	private int weightVisceraNumber;     //已称重脏器数量
	//----------------------------------------------------
	
	//-------------------固定---------------------------------
	@FXML
	private ComboBox<String> fixedSessionComboBox;  //固定-会话下拉框
	private ObservableList<String> data_fixedSessionComboBox = FXCollections.observableArrayList();
	private Map<String,Object> fixedSessionMap=new HashMap<String,Object>();
	@FXML
	private ComboBox<String> fixedVisceraNameComboBox;  //固定-脏器下拉框
	private ObservableList<String> data_fixedVisceraNameComboBox = FXCollections.observableArrayList();
	@FXML
	private ListView<String> fixedAnimalList;    //固定-动物列表
	private ObservableList<String> data_fixedAnimalList = FXCollections.observableArrayList();
	@FXML
	private TableView<VisceraFixed> visceraFixedTable; //脏器固定记录展示表
	private ObservableList<VisceraFixed> data_visceraFixedTable=FXCollections.observableArrayList(); 
	@FXML
	private TabPane fixedTabPane;//脏器固定tabpane
	@FXML
	private Tab fixedDetailTab;//脏器固定detail tab
	@FXML
	private Tab fixedCompareTab;//脏器固定compare tab
	@FXML
	private TableView<Map<String, List<List<VisceraFixedCompare>>>> visceraFixedCompareTable; //脏器固定-对比信息
	private ObservableList<Map<String, List<List<VisceraFixedCompare>>>> data_visceraFixedCompareTable = FXCollections.observableArrayList();
	private List<String> fixedTableStructure =new ArrayList<>();
	@FXML
	private TableColumn<VisceraFixed,String> fixedIdCol;  //脏器固定记录ID
	@FXML
	private TableColumn<VisceraFixed,String> fixedStudyNoCol;  //脏器固定记录ID
	@FXML
	private TableColumn<VisceraFixed,String> fixedAnimalCodeCol;  //动物编号
	@FXML
	private TableColumn<VisceraFixed,String> fixedVisceraNameCol; //脏器名称
	@FXML  
	private TableColumn<VisceraFixed,String> fixedOperatorCol;    //操作者
	@FXML
	private TableColumn<VisceraFixed,String> fixedOperateTimeCol; //操作时间
	@FXML
	private TextArea remarks;//备注
	@FXML
	private Button saveFixedAsExcel;//导出脏器固定为excel
	
	@FXML
	private Label fixedLabel;           //脏器固定-统计标签

	private int fixedVisceraNumber;     //已固定脏器数量
	//----------------------------------------------------
	//-------------------动物信息---------------------------------
	@FXML
	private ComboBox<String> animalSessionComboBox;  //动物信息-会话下拉框
	private ObservableList<String> data_animalSessionComboBox = FXCollections.observableArrayList();
	private Map<String,Object> animalSessionMap=new HashMap<String,Object>();
	@FXML
	private ComboBox<String> stateComboBox;  //动物信息-状态下拉框
	private ObservableList<String> data_stateComboBox = FXCollections.observableArrayList();
	@FXML
	private TableView<AnimalInfo> animalTable; //脏器固定记录展示表
	private ObservableList<AnimalInfo> data_animalTable=FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<AnimalInfo,String> info_animalCodeCol;           //动物编号
	@FXML
	private TableColumn<AnimalInfo,String> info_stateCol;                //解剖状态
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyOperatorCol;      //解剖者
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyResultCol;        //剖检结果
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyAnimalDeadTimeCol;//死亡日期
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyStartTimeCol;     //解剖开始时间
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyEndTimeCol;       //解剖结束时间
	@FXML
	private TableColumn<AnimalInfo,String> info_visceraWeighFinishTimeCol;//称重完成时间
	@FXML
	private TableColumn<AnimalInfo,String> info_visceraFixedFinishTimeCol;//固定完成时间
	@FXML
	private TableColumn<AnimalInfo,String> info_anatomyTimeCol;           //解剖耗时
	/*@FXML
	private TableColumn<AnimalInfo,String> info_histopathCheckFinishTimeCol;//镜检完成时间
	@FXML
	private TableColumn<AnimalInfo,String> info_histopathCheckOperatorCol;//镜检者
	@FXML
	private TableColumn<AnimalInfo,String> info_histopathReviewSignCol;   //复核者
*/	
	@FXML
	private Label animalInfoLabel;       //动物信息-统计信息
	private int animal_total;            //任务下动物总数
	private int noAnatomyAnimalNumber;   //未解剖动物数量
	
	private Stage stage;
	
	//----------------------------------------------------
	
	private static TaskDetailInfoPage instance;
	public static TaskDetailInfoPage getInstance(){
		if(null == instance){
			instance = new TaskDetailInfoPage();
		}
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initStudyNoCombobox();
	//	initTaskCombobox();
		initTaskTextField();
		initTaskListView();
		
		initTabPane();
		initAnatomyCheckSessionComboBox();
		initAnatomyCheckVisceraNameComboBox();
		initCheckResultComboBox();
		initanatomyAnimalList();
		inittAnatomyCheckTable();
		initWeightSessionComboBox();
		initWeightVisceraNameComboBox();
		initWeightAnimalList();
		initVisceraWeightTable();
		initVisceraWeightTable();
		initVisceraWeightDetailTable();
		initWeightCompareTable();
		initFixedSessionComboBox();
		initFixedVisceraNameComboBox();
		initFixedAnimalList();
		initVisceraFixedTabPane();
		//initVisceraFixedTable();
		initVisceraFixedCompareTable();
		initVisceraFixedTable();
		initStateSessionComboBox();
		initAnimalSessionComboBox();
		initAnimalTable();
		initSortVirscera();
	}
	/**
	 * 查询所有的脏器，为按sn排序做准备
	 */
	public void initSortVirscera()
	{
		visceraSortList = BaseService.getInstance().getDictVisceraService().findAll();
	}
    /**
     * 数据加载
     * @param anatomyTask 
     * @param item2 
     * @param item 
     */
    public void loadData(List<Map<String,Object>> list, TreeItem<String> item, TreeItem<String> item2, AnatomyTask anatomyTask,Stage stage){
    	this.stage = stage;
    	taskMapList=list;
    	TaskDetailInfoPage.itemChildren=item;
    	TaskDetailInfoPage.itemParent=item2;
    	TaskDetailInfoPage.anatomyTask=anatomyTask;
    	if(null!=list){
    		List<String> studyNoList=new ArrayList<String>();
    		data_studyNoComboBox.clear();
    		
    		for(Map<String,Object> map:list){
    			String studyNo = (String) map.get("studyNo");
    			if(!studyNoList.contains(studyNo)){
    				studyNoList.add(studyNo);
    				data_studyNoComboBox.add(studyNo);
    			}
    		}
    		if(null!=itemParent||null!=itemChildren||null!=anatomyTask){
    			if(null!=itemParent||null!=itemChildren){
    				TreeItem<String> temp = itemParent;
    				if(null!=itemParent&&"解剖任务"!=itemParent.getValue())
    					temp = itemParent;
    				else
    					temp = itemChildren;
    				
    				for(int i=0;i<data_studyNoComboBox.size();i++){
    					String studyNo = "";
    					String itemstr = temp.getValue();
    					studyNo = itemstr.split(" ")[0];
    				//	System.out.println("传过来的studyNo="+studyNo);
    					if(studyNo.equals(data_studyNoComboBox.get(i))){
    						studyNoComboBox.getSelectionModel().select(i);
    					}
//    					if(itemParent.getValue().indexOf(data_studyNoComboBox.get(i))>-1){
//    						studyNoComboBox.getSelectionModel().select(i);
//    					}
    				}
    			}else if(null!=anatomyTask){
    				for(int i=0;i<data_studyNoComboBox.size();i++){
    					if(anatomyTask.getStudyNo().equals(data_studyNoComboBox.get(i))){
    						studyNoComboBox.getSelectionModel().select(i);
    					}
    				}
    			}
    		}else{
    			studyNoComboBox.getSelectionModel().selectFirst();
    		}
    	}
    }
    /**
     * 初始化专题下拉框
     */
    private void initStudyNoCombobox(){
    	studyNoComboBox.setItems(data_studyNoComboBox);
    	studyNoComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					if(null!=taskMapList){
						data_taskComboBox.clear();
						studyNoAnatomyNumTaskIdMap.clear();
						data_taskComboBox.add("全部");
						for(Map<String,Object> map:taskMapList){
							String studyNo = (String) map.get("studyNo");
							String taskCreater = (String) map.get("taskCreater");
							Integer anatomyNum = (Integer) map.get("anatomyNum");
							String taskCreateTime = (String) map.get("taskCreateTime");
							String planBeginDate = (String) map.get("planBeginDate");
							String taskId = (String) map.get("taskId");
							if(studyNo.equals(newValue)){
								//data_taskComboBox.add(anatomyNum+":"+taskCreateTime+" "+taskCreater);
								data_taskComboBox.add(anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")");
								//studyNoAnatomyNumTaskIdMap.put(anatomyNum+":"+taskCreateTime+" "+taskCreater, taskId);
								studyNoAnatomyNumTaskIdMap.put(anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")", taskId);
							}
						}
						String allTask = "";
						for(String s: studyNoAnatomyNumTaskIdMap.values()){
							allTask += s.trim()+",";
						}
						studyNoAnatomyNumTaskIdMap.put("全部", allTask);
						
					}
					if(null!=itemChildren||null!=anatomyTask){
						if(null!=itemChildren){
							if(null!=itemParent&&"解剖任务"!=itemParent.getValue())
							{
								for(int i=0;i<data_taskComboBox.size();i++){
									if(itemChildren.getValue().equals(data_taskComboBox.get(i))){
										taskListView.getSelectionModel().select(i);
									}
								}
							}else{
								taskListView.getSelectionModel().select(0);
							}
						}else if(null!=anatomyTask){
							for(int i=0;i<data_taskComboBox.size();i++){
								String taskInfo=data_taskComboBox.get(i);
								String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
								if(anatomyTask.getTaskId().equals(taskId)){
									taskListView.getSelectionModel().select(i);
								}
							}
						}
					}else{
						taskListView.getSelectionModel().selectFirst();
					}
		
				}
			}
		});
    }
    /**
     * 初始化任务下拉框
     */
    /*
    private void initTaskCombobox(){
    	taskComboBox.setItems(data_taskComboBox);
    	taskComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
				    String taskId=studyNoAnatomyNumTaskIdMap.get(newValue);
				    Tab tab=tabPane.getSelectionModel().getSelectedItem();
				    if(tab.equals(anatomyTab)){
				    	updateAnatomyCheckSessionComboBox(taskId,1);
				    }else if(tab.equals(weightTab)){
				    	upadataWeightSessionComboBox(taskId,2);
				    }else if(tab.equals(fixTab)){
				    	upadataFixedSessionComboBox(taskId,3);
				    }else if(tab.equals(aninalTab)){
				    	updataAnimalSessionComboBox(taskId,4);
				    	upadataStateComboBox();
				    }
				}
			}
		});
    	
    	
    }*/
    /**
	 * 初始化任务TextField
	 */
	private void initTaskTextField() {
		taskTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
					if(!taskListView.isFocused()){
						taskListView.setVisible(false);
						
					}
				
			}});
	}
	/**键盘按下事件（主界面响应事件）
	 * @param event
	 */
	@FXML
	private void OnKeyPressed(KeyEvent event){
		if(event.getCode().getName().equals("Ctrl")){
			ctrPressed = true;
		}
	}
	/**键盘按下事件（主界面响应事件）
	 * @param event
	 */
	@FXML
	private void OnKeyReleased(KeyEvent event){
		if(event.getCode().getName().equals("Ctrl")){
			ctrPressed = false;
		}
	}
    /**快速查找TextField  鼠标事件
	 * @param event
	 */
	@FXML
	private void OnTaskTextFieldKeyReleased(Event event){
//		"Enter";
		//System.out.println(event.getSource().toString());
		//if("Down".equals(event.getCode().getName())){
		//	taskListView.getSelectionModel().select(0);
		//	taskListView.requestFocus();
		//}else{
			showSearchListView();
		//}
	}
	/**
	 * 初始化快速查找ListView 
	 */
	private void initTaskListView() {
		taskListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//多选
		
		
		taskListView.setItems(data_taskComboBox);
		
		taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
				
				List<String> abc = taskListView.getSelectionModel().getSelectedItems();
				if(null != abc){
					//System.out.println("change ctrPressed="+ctrPressed);
					if(!ctrPressed){//与setOnMouseClicked形成互补
						String txt = "";
						String taskId="";
						for(String str:abc)
						{
							if(str!=null&&str!="全部")
							{
								txt+=str+" ";
								taskId += studyNoAnatomyNumTaskIdMap.get(str)+",";
							}else if(str=="全部"){
								txt= "全部";
								taskId = studyNoAnatomyNumTaskIdMap.get(str)+",";
								
								taskListView.getSelectionModel().clearAndSelect(0);
								break;
							}
						}
						taskTextField.setText(txt);
						
					//	System.out.println("changelistenr taskId="+taskId);
						Tab tab=tabPane.getSelectionModel().getSelectedItem();
						if(tab.equals(anatomyTab)){
							updateAnatomyCheckSessionComboBox(taskId,1);
						}else if(tab.equals(weightTab)){
							upadataWeightSessionComboBox(taskId,2);
						}else if(tab.equals(fixTab)){
							upadataFixedSessionComboBox(taskId,3);
						}else if(tab.equals(aninalTab)){
							updataAnimalSessionComboBox(taskId,4);
							upadataStateComboBox();
						}
						
						
					}
					
				}
			}

			
		});
		
		taskListView.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				//System.out.println("mouse release"+ctrPressed);
				if(ctrPressed){
					List<String> abc = taskListView.getSelectionModel().getSelectedItems();
					if(null != abc){
						String txt = "";
						String taskId="";
						for(String str:abc)
						{
							if(str!=null&&str!="全部")
							{
								txt+=str+" ";
								taskId += studyNoAnatomyNumTaskIdMap.get(str)+",";
							}else if(str=="全部"){
								txt= "全部";
								taskId = studyNoAnatomyNumTaskIdMap.get(str)+",";
								
								taskListView.getSelectionModel().clearAndSelect(0);
								break;
							}
						
						}
						taskTextField.setText(txt);
						//System.out.println("mouse event taskId="+taskId);
						Tab tab=tabPane.getSelectionModel().getSelectedItem();
						if(tab.equals(anatomyTab)){
							updateAnatomyCheckSessionComboBox(taskId,1);
						}else if(tab.equals(weightTab)){
							upadataWeightSessionComboBox(taskId,2);
						}else if(tab.equals(fixTab)){
							upadataFixedSessionComboBox(taskId,3);
						}else if(tab.equals(aninalTab)){
							updataAnimalSessionComboBox(taskId,4);
							upadataStateComboBox();
						}
						
					}
					
				}
				
			}
		
			
		});
		
		taskListView.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null == newValue || !newValue){
					
				}
			}
		});
		
					
			

	}
	
	/**
	 * 显示 快速查找ListView 
	 */
	private void showSearchListView() {
		//data_taskComboBox.clear();
		
		int height = data_taskComboBox.size() * 25;	
		if(height > 175){
			height = 175;
		}
		if(height == 0){
			taskListView.setVisible(false);
		}else{
			height = height+1;
			taskListView.setPrefHeight(height);
			taskListView.setVisible(true);
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TaskDetailInfoPage.fxml"));
		Scene scene = new Scene(root, 966, 618);
		stage.setMinWidth(960);
		stage.setMinHeight(618);
		stage.setScene(scene);
		stage.setTitle("详细任务信息");
//		stage.setResizable(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	/**
	 * 初始化tabPane
	 */
	private void initTabPane() {
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){

				@Override
				public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab tab) {
					//总的tabPane变化的时候把其他的清空
					//-------------------剖检---------------------------------
					//剖检-会话下拉框
					data_anatomyCheckSessionComboBox.clear();
					antomySessionMap.clear();
					//剖检-脏器下拉框
					data_anatomyCheckVisceraNameComboBox.clear();
					 //剖检结果下拉框
					data_checkResultComboBox.clear();
					//剖检-动物列表
					 data_anatomyAnimalList.clear();
					//解剖所见
					data_anatomyCheckTable.clear();
					
					
					//-------------------称重---------------------------------
					//称重-会话下拉框
					data_weightSessionComboBox.clear();
					weightSessionMap.clear();
					//称重-脏器下拉框
					data_weightVisceraNameComboBox.clear();
					//称重-动物列表
					data_weightAnimalList.clear();
					//脏器称重-详细信息
					data_weightDetailTable.clear();
					//脏器称重-对比信息
					 data_weightCompareTable.clear();
					//----------------------------------------------------
					
					//-------------------固定---------------------------------
					//固定-会话下拉框
					data_fixedSessionComboBox.clear();
					fixedSessionMap.clear();
					//固定-脏器下拉框
					data_fixedVisceraNameComboBox.clear();
					//固定-动物列表
					data_fixedAnimalList.clear();
					/*//脏器固定记录展示表
					data_visceraFixedTable.clear(); */
					 //脏器固定-对比信息
				    data_visceraFixedCompareTable.clear();
					fixedTableStructure.clear();
					
					//----------------------------------------------------
					//-------------------动物信息---------------------------------
					//动物信息-会话下拉框
					data_animalSessionComboBox.clear();
					animalSessionMap.clear();
					//动物信息-状态下拉框
					data_stateComboBox.clear();
					 //脏器固定记录展示表
					data_animalTable.clear();
			
					
					if(null!=tab){
						List<String> taskInfos=taskListView.getSelectionModel().getSelectedItems();
						String taskId="";
						for(String str:taskInfos)
						{
							taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
						}
						if(tab.equals(anatomyTab)){
							updateAnatomyCheckSessionComboBox(taskId,1);
						}else if(tab.equals(weightTab)){
					    	upadataWeightSessionComboBox(taskId,2);
					    }else if(tab.equals(fixTab)){
					    	upadataFixedSessionComboBox(taskId,3);
					    }else if(tab.equals(aninalTab)){
					    	updataAnimalSessionComboBox(taskId,4);
					    	upadataStateComboBox();
					    }
					}
				}
				
		  });
		}
	/**
	 * 初始化剖检-会话下拉框
	 */
	private void initAnatomyCheckSessionComboBox(){
		anatomyCheckSessionComboBox.setItems(data_anatomyCheckSessionComboBox);
		anatomyCheckSessionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					
					String sessionId=(String) antomySessionMap.get(newValue);
					updataAnatomyCheckVisceraNameComboBox(taskId,sessionId);
					updataCheckResultComboBox(taskId,sessionId);
					updataAnatomyAnimalList(taskId,sessionId);
					
				}
			}
		});
		
	}
	/**更新剖检-会话下拉框
	 * @param taskId
	 */
	private void updateAnatomyCheckSessionComboBox(String taskId,int tabIndex){
		data_anatomyCheckSessionComboBox.clear();
		antomySessionMap.clear();
		List<Map<String, Object>> list=BaseService.getInstance().getTblPathSessionService().getAnatomySessionListByTaskIds(taskId,tabIndex);
		data_anatomyCheckSessionComboBox.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String, Object> map:list){
				String sessionId=(String)map.get("sessionId");
				int sessionType=(Integer)map.get("sessionType");
				String creator=(String)map.get("sessionCreator");
				Date createTime=(Date)map.get("createdTime");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String createTimeStr=sdf.format(createTime);
				String sessionInfo="";
				if(sessionType==1){
					sessionInfo=createTimeStr+" 剖检  "+creator;
				}else if(sessionType==3){
					sessionInfo=createTimeStr+" 剖检、称重 "+creator;
				}else if(sessionType==5){
					sessionInfo=createTimeStr+" 剖检、固定  "+creator;
				}else if(sessionType==7){
					sessionInfo=createTimeStr+" 剖检、称重、固定   "+creator;
				}
				data_anatomyCheckSessionComboBox.add(sessionInfo);
				antomySessionMap.put(sessionInfo, sessionId);
			}
		}
		anatomyCheckSessionComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化剖检-脏器下拉框
	 */
	private void initAnatomyCheckVisceraNameComboBox(){
		anatomyCheckVisceraNameComboBox.setItems(data_anatomyCheckVisceraNameComboBox);
		anatomyCheckVisceraNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=anatomyCheckSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) antomySessionMap.get(sessionInfo);
					String anatomyFinding=checkResultComboBox.getSelectionModel().getSelectedItem();
					String animalCode=anatomyAnimalList.getSelectionModel().getSelectedItem();
					updataAnatomyCheckTable(taskId,sessionId,newValue,anatomyFinding,animalCode);
				}
			}
		});
	}
	/**更新剖检-脏器下拉框
	 * @param taskId
	 * @param sessionId
	 */
	private void updataAnatomyCheckVisceraNameComboBox(String taskId,String sessionId){
		data_anatomyCheckVisceraNameComboBox.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getVisceraNameAndCodeListWithEdits(taskId,sessionId);
		if(null!=list && list.size()>0){
			data_anatomyCheckVisceraNameComboBox.add("全部");
			for(Map<String,Object> map:list){
				String visceraName=(String)map.get("visceraName");
				data_anatomyCheckVisceraNameComboBox.add(visceraName);
			}
			anatomyCheckVisceraNameComboBox.getSelectionModel().selectFirst();
		}
	}
	/**
	 * 初始化剖检-病变（解剖所见）下拉框
	 */
	private void initCheckResultComboBox(){
		checkResultComboBox.setItems(data_checkResultComboBox);
		checkResultComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=anatomyCheckSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) antomySessionMap.get(sessionInfo);
					String visceaName=anatomyCheckVisceraNameComboBox.getSelectionModel().getSelectedItem();
					String animalCode=anatomyAnimalList.getSelectionModel().getSelectedItem();
					updataAnatomyCheckTable(taskId,sessionId,visceaName,newValue,animalCode);
				}
			}
		});
	}
	/**
	 * 更新剖检-病变（解剖所见）下拉框
	 */
	private void updataCheckResultComboBox(String taskId,String sessionId){
		data_checkResultComboBox.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getAnatomyFindingBySessionIdWithEdits(taskId,sessionId);
		if(null!=list && list.size()>0){
			data_checkResultComboBox.add("全部");
			for(Map<String,Object> map:list){
				String anatomyFinding=(String)map.get("anatomyFingding");
				data_checkResultComboBox.add(anatomyFinding);
			}
			checkResultComboBox.getSelectionModel().selectFirst();
		}
	}
	/**
	 * 初始化剖检-动物列表
	 */
	private void initanatomyAnimalList(){
		anatomyAnimalList.setItems(data_anatomyAnimalList);
		anatomyAnimalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=anatomyCheckSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) antomySessionMap.get(sessionInfo);
					String visceaName=anatomyCheckVisceraNameComboBox.getSelectionModel().getSelectedItem();
					String anatomyFinding=checkResultComboBox.getSelectionModel().getSelectedItem();
					updataAnatomyCheckTable(taskId,sessionId,visceaName,anatomyFinding,newValue);
				}
			}
		});
	}
	/**
	 * 更新剖检-动物列表
	 */
	private void updataAnatomyAnimalList(String taskId,String sessionId){
		data_anatomyAnimalList.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getAnimalCodeBySessionIds(taskId,sessionId);
		data_anatomyAnimalList.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String,Object> map:list){
				String animalCode=(String)map.get("animalCode");
				Integer autolyzeFlag=(Integer)map.get("autolyzeFlag");
				if(autolyzeFlag==1){
					data_anatomyAnimalList.add(animalCode+"(自溶)");
				}else{
					data_anatomyAnimalList.add(animalCode);
				}
			}
		}
		anatomyAnimalList.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化解剖所见表格
	 */
	private void inittAnatomyCheckTable(){
		anatomyCheckTable.setItems(data_anatomyCheckTable);
		idCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("id"));//主键
	//	astudyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("astudyNo"));//专题编号
	    animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("animalCode"));//动物编号
        visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("visceraName"));;//脏器名称
		anatomyFindingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("anatomyFinding"));;//解剖所见
       /* anatomyFindingCol.setCellValueFactory(new Callback<CellDataFeatures<AnatomyCheck, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<AnatomyCheck, String> p) {
		         // p.getValue() returns the Person instance for a particular TableView row
		    	 final CellDataFeatures<AnatomyCheck, String> p1 = p;
		    	 ObservableValue<String> s = new ObservableValueBase<String>() {
					@Override
					public String getValue() {
					
						return p1.getValue().getAnatomyFinding();
					}
				};
		         return s;
		     }
		  });*/
		operatorCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("operator"));;//操作者
		operateTimeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("operateTime"));;//操作时间
		anatomyCheckTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyCheck>(){
			@Override
			public void changed(ObservableValue<? extends AnatomyCheck> arg0, AnatomyCheck arg1,
					AnatomyCheck newValue) {
							if(null != newValue){
								
							}		
				}

		});
		
		operateTimeCol.setCellFactory(new Callback<TableColumn<AnatomyCheck,String>,TableCell<AnatomyCheck,String>>(){
	    	 @Override
	    	 public TableCell<AnatomyCheck, String> call(
	    			 TableColumn<AnatomyCheck, String> param) {
	    		 TableCell<AnatomyCheck, String> cell =
	    				 new TableCell<AnatomyCheck, String>() {
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
	    		 cell.setSkin(null);
	    		 return cell;
	    	 }
	     });
		saveAnatomyAsExcel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(data_anatomyCheckTable.size()==0)
				{
					Messager.showMessage("没有数据，请检查！");
				}else
				{
					POIExcelUtil excelUtil = new POIExcelUtil();
					HSSFSheet sheet = excelUtil.newSheet("解剖所见");
					HSSFRow row0 = sheet.createRow(0);
	               row0.createCell(0).setCellValue("动物编号");
	               row0.createCell(1).setCellValue("脏器");
	               row0.createCell(2).setCellValue("解剖所见");
	               row0.createCell(3).setCellValue("操作者");
	               row0.createCell(4).setCellValue("记录时间");
	               for(int i=1;i<=data_anatomyCheckTable.size();i++)
	               {
	            	   AnatomyCheck anatomyCheck = data_anatomyCheckTable.get(i-1);
	            	   HSSFRow row = sheet.createRow(i);
	            	   row.createCell(0).setCellValue(anatomyCheck.getAnimalCode());
	                   row.createCell(1).setCellValue(anatomyCheck.getVisceraName());
	                   row.createCell(2).setCellValue(anatomyCheck.getAnatomyFinding());
	                   row.createCell(3).setCellValue(anatomyCheck.getOperator());
	                   row.createCell(4).setCellValue(anatomyCheck.getOperateTime());
	               }
	               try {
	          		boolean f = excelUtil.save("解剖所见",stage);
	          		if(f)
	          			Messager.showMessage("导出成功！");
	          		//else 
	          			//Messager.showErrorMessage("导出失败！");
	               } catch (IOException e) {
	            	   Messager.showErrorMessage("导出失败！");
	            	   e.printStackTrace();
	               }
				}
				
			}
		});
	}
	/**
	 * 更新剖检-剖检信息表
	 */
	private void updataAnatomyCheckTable(String taskId,String sessionId,String visceraName,String antomyFinding,String animalCode){
		data_anatomyCheckTable.clear();
		List<String> visceraNum = new ArrayList<String>();
		List<String> animalNum = new ArrayList<String>();
		List<String> findingNum = new ArrayList<String>();
		List<Map<String,Object>> list=BaseService.getInstance().getTblAnatomyCheckService().getListByConditions2WithEdits(taskId,sessionId,visceraName,antomyFinding,animalCode);
		if(null != list && list.size()>0){
			for(Map<String,Object> map:list){
				String id = (String) map.get("id");//专题编号
				String astudyNo = (String) map.get("studyNo");//专题编号
				String sessionId1 = (String)map.get("sessionId");
				String animalCode1 = (String) map.get("animalCode");//动物编号
				String  visceraName1 = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  anatomyFinding = (String) map.get("anatomyFingding");//解剖所见
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
			//	String bodySurfacePos = (String) map.get("bodySurfacePos") ;//体表部位
							
				if( null != subVisceraName && !subVisceraName.equals("") ){
					visceraName1 = subVisceraName;
				}
				Integer autolyzaFlag =  (Integer) map.get("autolyzaFlag") ;
				if(null!=autolyzaFlag && autolyzaFlag == 1 ){
					anatomyFinding = "自溶";
				}
				/*if(null == visceraName1 ||visceraName1.equals("")){
					visceraName1 = bodySurfacePos;
				}*/
				if(visceraName1!=null&&!"".equals(visceraName1)&&!exist(visceraNum,visceraName1))
					visceraNum.add(visceraName1);
				if(!exist(animalNum,animalCode1))
					animalNum.add(animalCode1);
				if(!exist(findingNum,anatomyFinding))
					findingNum.add(anatomyFinding);	
				data_anatomyCheckTable.add(new AnatomyCheck(animalCode1,visceraName1,anatomyFinding
						,operator,operateTime,astudyNo,id,sessionId1,operator));
			}
		}
		String anatomyCheckLabelStr="";
		if(sessionId==null ||"全部".equals(sessionId))//会话
		{
			anatomyCheckLabelStr+="";
		}else
		{
			anatomyCheckLabelStr+="会话:("+anatomyCheckSessionComboBox.getSelectionModel().getSelectedItem()+")  ";
		}
		if (animalCode==null ||"全部".equals(animalCode))
		{
//			anatomyCheckLabelStr+="动物:"+(data_anatomyAnimalList.size()-1)+"个    ";
			anatomyCheckLabelStr+="动物数量:"+animalNum.size()+"只  ";
		}else
		{
			anatomyCheckLabelStr+="动物编号:"+animalCode+"  ";
		}
		if(visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) 
		{
//			anatomyCheckLabelStr+="所见脏器:"+(data_anatomyCheckVisceraNameComboBox.size()-1)+"个    ";
			anatomyCheckLabelStr+="脏器:"+visceraNum.size()+"类  ";
		}else
		{
			anatomyCheckLabelStr+="脏器："+visceraName+"  ";
		}
		
		if (antomyFinding==null ||"全部".equals(antomyFinding)||"".equals(antomyFinding))
		{
			//anatomyCheckLabelStr+="病变种类:"+(data_checkResultComboBox.size()-1)+"种";
		//	anatomyCheckLabelStr+="病变种类:"+findingNum.size()+"种";
		}else
		{
			anatomyCheckLabelStr+="病变:"+antomyFinding+"  ";
		}
		anatomyCheckLabelStr+="记录数量:"+data_anatomyCheckTable.size()+"";
		/*if((sessionId==null ||"全部".equals(sessionId)) && (visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) 
				&& (animalCode==null ||"全部".equals(animalCode))&& (antomyFinding==null ||"全部".equals(antomyFinding))||"".equals(antomyFinding)){
			anatomyVisceraNumber=visceraNum.size();
		    if(data_anatomyAnimalList.size()<2){
		    	anatomyAnimalNumber=0;
		    }else{
		    	anatomyAnimalNumber=data_anatomyAnimalList.size()-1;
		    }
		}*/
		//按解剖顺序排序
		//sortAnatomyCheckBySn();
		
		anatomyCheckLabel.setText(anatomyCheckLabelStr);
		
		visceraNum = null;
		animalNum = null;
		findingNum = null;
		
	}
	
	/**
	 * sortAnatomyCheckBySn
	 */
	public void sortAnatomyCheckBySn()
	{
		List<String> visNamelist = new ArrayList<>();
		
		for(AnatomyCheck ac:data_anatomyCheckTable)
		{
			visNamelist.add(ac.getVisceraName());
		}
		if(visNamelist.size()>1)
		{
			Collections.sort(data_anatomyCheckTable,new Comparator<AnatomyCheck>() {
				@Override
				public int compare(AnatomyCheck o1, AnatomyCheck o2) {
					int sn=0,sn2=0;
					for(DictViscera dv:visceraSortList)
					{
						if(dv.getVisceraName().equals(o1.getVisceraName()))
							sn = dv.getSn();
						if(dv.getVisceraName().equals(o2.getVisceraName()))
							sn2 = dv.getSn();
					}
					return sn-sn2;
					
				}
			});
		}
	}
	/**
	 * 初始化称重-会话下拉框
	 */
	private void initWeightSessionComboBox(){
		weightSessionComboBox.setItems(data_weightSessionComboBox);
		weightSessionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionId=(String) weightSessionMap.get(newValue);
					updataWeightVisceraNameComboBox(taskId,sessionId);
					updataWeightAnimalList(taskId,sessionId);
				}
			}
		});
		
	}
	/**
	 * 更新称重-会话下拉框
	 */
	private void upadataWeightSessionComboBox(String taskId,int tabIndex){
		data_weightSessionComboBox.clear();
		weightSessionMap.clear();
		List<Map<String, Object>> list=BaseService.getInstance().getTblPathSessionService().getAnatomySessionListByTaskIds(taskId,tabIndex);
		data_weightSessionComboBox.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String, Object> map:list){
				String sessionId=(String)map.get("sessionId");
				int sessionType=(Integer)map.get("sessionType");
				String creator=(String)map.get("sessionCreator");
				Date createTime=(Date)map.get("createdTime");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String createTimeStr=sdf.format(createTime);
				String sessionInfo="";
				if(sessionType==2){
					sessionInfo=createTimeStr+" 称重   "+creator;
				}else if(sessionType==3){
					sessionInfo=createTimeStr+" 剖检、称重  "+creator;
				}else if(sessionType==6){
					sessionInfo=createTimeStr+" 称重、固定  "+creator;
				}else if(sessionType==7){
					sessionInfo=createTimeStr+" 剖检、称重、固定  "+creator;
				}else if(sessionType==8){
					sessionInfo=createTimeStr+" 固定后称重  "+creator;
				}
				data_weightSessionComboBox.add(sessionInfo);
				weightSessionMap.put(sessionInfo, sessionId);
			}
		}
		weightSessionComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化称重-脏器下拉框
	 */
	private void  initWeightVisceraNameComboBox(){
		weightVisceraNameComboBox.setItems(data_weightVisceraNameComboBox);
		weightVisceraNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=weightSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) weightSessionMap.get(sessionInfo);
					String animalCode=weightAnimalList.getSelectionModel().getSelectedItem();
					
					Tab tab = weightTabPane.getSelectionModel().getSelectedItem();
					if(tab.equals(weightDetailTab)){
						saveWeightAsExcel.setDisable(true);
						updateVisceraWeight(taskId,sessionId,newValue,animalCode);
					}else if(tab.equals(weightCompareTab)){
						saveWeightAsExcel.setDisable(false);
						updateWeightCompareTable(taskId,sessionId,newValue,animalCode);
				    }
				}
			}
		});
	}
	/**
	 * 更新称重-脏器下拉框
	 */
	private void updataWeightVisceraNameComboBox(String taskId,String sessionId){
		data_weightVisceraNameComboBox.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraWeightService().getVisceraNameAndCodeLists(taskId,sessionId);
		if(null!=list && list.size()>0){
			data_weightVisceraNameComboBox.add("全部");
			for(Map<String,Object> map:list){
				String visceraName=(String)map.get("visceraName");
				data_weightVisceraNameComboBox.add(visceraName);
			}
			weightVisceraNameComboBox.getSelectionModel().selectFirst();
		}
		
	}
	/**
	 * 初始化称重-动物列表
	 */
	private void initWeightAnimalList(){
		weightAnimalList.setItems(data_weightAnimalList);
		weightAnimalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
				//	updateVisceraWeight(taskId,sessionId,visceraName,newValue);
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=weightSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) weightSessionMap.get(sessionInfo);
					String visceraName=weightVisceraNameComboBox.getSelectionModel().getSelectedItem();
					Tab tab = weightTabPane.getSelectionModel().getSelectedItem();
					if(tab.equals(weightDetailTab)){
						saveWeightAsExcel.setDisable(true);
						updateVisceraWeight(taskId,sessionId,visceraName,newValue);
					}else if(tab.equals(weightCompareTab)){
						saveWeightAsExcel.setDisable(false);
						updateWeightCompareTable(taskId,sessionId,visceraName,newValue);
				    }
				}
			}
		});
	}
	/**更新称重-动物列表
	 * @param taskId
	 * @param sessionId
	 */
	private void updataWeightAnimalList(String taskId,String sessionId){
		data_weightAnimalList.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraWeightService().getAnimalCodeBySessionIds(taskId,sessionId);
		data_weightAnimalList.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String,Object> map:list){
				String animalCode=(String)map.get("animalCode");
				Integer autolyzeFlag=(Integer)map.get("autolyzeFlag");
				if(autolyzeFlag==1){
					data_weightAnimalList.add(animalCode+"(自溶)");
				}else{
					data_weightAnimalList.add(animalCode);
				}
			}
		}
		weightAnimalList.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化称重-weightTabPane
	 */
	private  void initVisceraWeightTable(){
		weightTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab tab) {
				//-------------------称重---------------------------------
				
				//脏器称重-详细信息
				data_weightDetailTable.clear();
				//脏器称重-对比信息
				data_weightCompareTable.clear();
				
				if(null!=tab){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=weightSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) weightSessionMap.get(sessionInfo);
					String visceraName=weightVisceraNameComboBox.getSelectionModel().getSelectedItem();
					String animalCode = weightAnimalList.getSelectionModel().getSelectedItem();
					if(tab.equals(weightDetailTab)){
						saveWeightAsExcel.setDisable(true);
						updateVisceraWeight(taskId,sessionId,visceraName,animalCode);
					}else if(tab.equals(weightCompareTab)){
						saveWeightAsExcel.setDisable(false);
						updateWeightCompareTable(taskId,sessionId,visceraName,animalCode);
				    }
				}
			}
			
	  });
	
		//previewWeightCompareTable.setDisable(true);
		saveWeightAsExcel.setDisable(true);
		/*previewWeightCompareTable.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event){
					
					
					//report参数
			        Map<String,Object> map = new HashMap<String,Object>();
			    
			   		//生成预览pdf。
			   		JasperReport jr = null;
			        JasperPrint jp = null;
			        
				       
			        map.put("titleName","动物称重详情单");//表名
			        map.put("number","getStudyNo");//专题编号
			        URL url = this.getClass().getResource("/image/logo.jpg");
			        map.put("logoImage", url);
			     
					try {
						
						jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("weightDetailReport.jrxml"));
						jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(data_weightDetailTable) );
					} catch (JRException e) {
						e.printStackTrace();
					}
			        Main.getInstance().openJFrame(jp, "动物称重详情单");	
			   	    
				}
			});
				*/
		saveWeightAsExcel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if(data_weightCompareTable.size()==0)
				{
					Messager.showMessage("没有数据，请检查！");
				}else
				{
				
					POIExcelUtil excelUtil = new POIExcelUtil();
					HSSFSheet sheet = excelUtil.newSheet("称重单");
					HSSFRow row = sheet.createRow(0); //创建Excel工作表的行
					//row.createCell((short)0).setCellStyle(cellStyle); //创建Excel工作表指定行的单元格   
					
					//填充表头 
					for(int i=0;i<tableStructure.size();i++)
					{
						HSSFCell cell = row.createCell(i);  
						cell.setCellValue(tableStructure.get(i));
					}
					
					//填充内容
					for(int j=0;j<data_weightCompareTable.size();j++)
					{
						Map<String,List<visceraWeight>> vwList = data_weightCompareTable.get(j);
						HSSFRow tempRow = sheet.createRow(j+1);
						String key = (String) vwList.keySet().toArray()[0];
						List<visceraWeight> list = vwList.get(key);
						tempRow.createCell(0).setCellValue(key);//第一列
						
						for(visceraWeight vw:list)
						{
							int columnNum = tableStructure.indexOf(vw.getWvisceraName()+"("+vw.getWeightUnit().getValue()+")");
							HSSFCell cell =tempRow.createCell(columnNum);
							cell.setCellValue(vw.getWeight());
						}
						list = null;
						vwList = null;
					}
					try {
					//	String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
						//anatomyNum+":"+planBeginDate+" ("+taskCreateTime+" "+taskCreater+")"
					//	String createTime = taskInfo.substring(taskInfo.indexOf("(")+1,(taskInfo.lastIndexOf(" ")));
						String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
						
					//	boolean f = excelUtil.save(studyNo+" "+createTime+" 脏器称重",stage);
						boolean f = excelUtil.save(studyNo+" 脏器称重",stage);
						if(f)
							Messager.showMessage("导出成功");
						//else
						//	Messager.showMessage("导出失败");
					} catch (IOException e) {
						Messager.showErrorMessage("导出失败");
						e.printStackTrace();
					}
					
					excelUtil = null;
				}
			}
			
		});
		
	}
	/**初始化称重-详细信息
	 * 
	 */
	private  void initVisceraWeightDetailTable(){
		weightDetailTable.setItems(data_weightDetailTable);
		widCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wid"));//主键
	//	wstudyNoCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wstudyNo"));//专题编号
		wanimalCodeCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wanimalCode"));//动物编号
		wvisceraNameCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wvisceraName"));//脏器名称
		weightCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("weight"));//体重
		fixedWeighFlagCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("fixedWeighFlag"));//是否固定后称重   0：否；1：是
		attachedVisceraNamesCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("attachedVisceraNames"));//附加脏器列表 
		woperatorCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("woperator"));//操作者
		woperateTimeCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("woperateTime"));//操作时间
		balCodeCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("balCode"));//天平编号
		fixedWeighFlagCol.setCellFactory(new Callback<TableColumn<visceraWeight,String>,TableCell<visceraWeight,String>>(){
	    	 @Override
	    	 public TableCell<visceraWeight, String> call(
	    			 TableColumn<visceraWeight, String> param) {
	    		 TableCell<visceraWeight, String> cell =
	    				 new TableCell<visceraWeight, String>() {
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
		
		woperateTimeCol.setCellFactory(new Callback<TableColumn<visceraWeight,String>,TableCell<visceraWeight,String>>(){
	    	 @Override
	    	 public TableCell<visceraWeight, String> call(
	    			 TableColumn<visceraWeight, String> param) {
	    		 TableCell<visceraWeight, String> cell =
	    				 new TableCell<visceraWeight, String>() {
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
	    		 cell.setSkin(null);
	    		 return cell;
	    	 }
	     });
		weightDetailTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<visceraWeight>(){

			@Override
			public void changed(ObservableValue<? extends visceraWeight> arg0, visceraWeight arg1,
					visceraWeight newValue) {
				}

		});
	}
	/**
	 * 更新脏器称重-详细信息
	 */
	private void updateVisceraWeight(String taskId,String sessionId,String visceraName,String animalCode){
		data_weightDetailTable.clear();
		//previewWeightCompareTable.setDisable(true);
		saveWeightAsExcel.setDisable(true);
		List<String> visceraNum = new ArrayList<String>();
		List<String> animalNum = new ArrayList<>();
		List<Map<String,Object>> taskMapList = BaseService.getInstance().getTblVisceraWeightService().getListByConditionss(taskId,sessionId,visceraName,animalCode);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String wid = (String) map.get("id");//主键
				//String sessionId = (String)map.get("sessionId");
				String wstudyNo = (String) map.get("studyNo");//专题编号
				String wanimalCode = (String) map.get("animalCode");//动物编号
				String  wvisceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  weight = (String) map.get("weight");//重量
				String  weightUnit = (String) map.get("weightUnit");//重量单位
				//weight = weight + " "+weightUnit+"";
				Integer fixedWeighFlag = (Integer) map.get("fixedWeightFlag");//是否固定后称重
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
				String attachedVisceraNames = (String) map.get("attachedVisceraNames") ;//附加脏器列表
				if( null != subVisceraName && !subVisceraName.equals("") ){
//					wvisceraName = wvisceraName+" "+subVisceraName;
					wvisceraName = subVisceraName;
					attachedVisceraNames = "";
				}
				String balCode = (String) map.get("balCode") ;//天平编号
				if(wvisceraName!=null&&!"".equals(wvisceraName)&&!exist(visceraNum,wvisceraName))
					visceraNum.add(wvisceraName);
				if(!exist(animalNum,wanimalCode))
					animalNum.add(wanimalCode);
				data_weightDetailTable.add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
					wvisceraName, weight,weightUnit,fixedWeighFlag,attachedVisceraNames,
					operator,operateTime,balCode));
			}
			Collections.sort(data_weightDetailTable,new Comparator<visceraWeight>() {

				@Override
				public int compare(visceraWeight o1, visceraWeight o2) {
					return o1.getWanimalCode().compareTo(o2.getWanimalCode());
				}
			});
		}
		String visceraWeightLabelStr="";
		if(sessionId==null ||"全部".equals(sessionId)) 
		{
			visceraWeightLabelStr+="";
			
		}else
		{
			visceraWeightLabelStr+="会话:("+weightSessionComboBox.getSelectionModel().getSelectedItem()+")  ";	
		}
		if(animalCode==null ||"全部".equals(animalCode)||"".equals(animalCode))
		{
			//visceraWeightLabelStr+="动物个数:"+(data_weightAnimalList.size()-1)+"个";
			visceraWeightLabelStr+="动物数量:"+animalNum.size()+"只  ";
		}else
		{
			visceraWeightLabelStr+="动物编号:"+animalCode+"  ";
		}
		if (visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) 
		{
			//visceraWeightLabelStr+="已称重脏器:"+(data_weightVisceraNameComboBox.size()-1)+"个     ";
			visceraWeightLabelStr+="已称重脏器:"+visceraNum.size()+"类  ";
			
		}else
		{
			visceraWeightLabelStr+="脏器:"+visceraName+"  ";
		}
		visceraWeightLabelStr+="记录数量:"+data_weightDetailTable.size()+"";
			
		
		
		/*if((sessionId==null ||"全部".equals(sessionId)) && (visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) && (animalCode==null ||"全部".equals(animalCode)||"".equals(animalCode))){
			weightVisceraNumber=data_weightDetailTable.size();
		    if(data_weightAnimalList.size()<2){
		    	weightAnimalNumber=0;
		    }else{
		    	weightAnimalNumber=data_weightAnimalList.size()-1;
		    }
		}*/
		visceraWeightLabel.setText(visceraWeightLabelStr);
		
		visceraNum = null;
		animalNum = null;
	}
	public boolean exist(List<String> list,String str)
	{
		boolean flag = false;
		for(String strTemp:list)
		{
			if(strTemp.equals(str))
			{
				flag = true;
				break;
			}
		}
		return flag;
		
	}
	
	/**初始化称重-对比信息
	 * 
	 */
	
	private void initWeightCompareTable(){
		weightCompareTable.setItems(data_weightCompareTable);
		//第一列是动物
		TableColumn<Map<String,List<visceraWeight>>,String> animalCol = new TableColumn<Map<String,List<visceraWeight>>,String>("动物编号");
		animalCol.setPrefWidth(100.0);
		tableStructure.clear();
		
		tableStructure.add("动物编号");
		//动物编号
		animalCol.setCellValueFactory(new Callback<CellDataFeatures<Map<String,List<visceraWeight>>, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Map<String,List<visceraWeight>>, String> p) {
		         return new SimpleStringProperty(p.getValue().keySet().toArray()[0].toString());
		     }
		  });
		 weightCompareTable.getColumns().add(animalCol);
		 animalCol = null;
			
	}
	/**
	 * update comparetable structure
	 */
	private  void updateWeightCompareTableStructure(){
		
		
		//从第二列开始是脏器
		for(String columnName:tableStructure)
		{
			if(!columnName.equals("动物编号"))
			{
				boolean flag = false;
				for(TableColumn<Map<String,List<visceraWeight>>,?> tc : weightCompareTable.getColumns())
				{
					if(tc.getText().equals(columnName))
					{
						flag = true;
						break;
					}
				}
				if(!flag)
				{
					TableColumn<Map<String,List<visceraWeight>>,String> weightViscera_TC = new TableColumn<Map<String,List<visceraWeight>>,String>(columnName);
					weightViscera_TC.setMinWidth(10);
					weightViscera_TC.setPrefWidth(100.0);
					weightViscera_TC.setCellValueFactory(new Callback<CellDataFeatures<Map<String,List<visceraWeight>>, String>, ObservableValue<String>>() {
					     public ObservableValue<String> call(CellDataFeatures<Map<String,List<visceraWeight>>, String> p) {
					         		String key =  p.getValue().keySet().toArray()[0].toString();
									List<visceraWeight> values = p.getValue().get(key);
									String columnNameTemp = p.getTableColumn().getText().substring(0,p.getTableColumn().getText().lastIndexOf("("));
									//System.out.println("列名："+columnNameTemp);
									visceraWeight va = null;
									for(visceraWeight vaTemp:values)
									{
										if(vaTemp.getWvisceraName().equals(columnNameTemp))
										{
											va = vaTemp;
											break;
										}
									}
									
									if(va!=null)
									{
										//System.out.println(columnNameTemp+"==="+va.getWeight());
										return new SimpleStringProperty(va.getWeight());
									}else
									{
										//System.out.println(columnNameTemp+"===没找到");
										return new SimpleStringProperty("");
									}
									
									
							//	}
							//};
					        // return s1;
					     }
					  });
					weightCompareTable.getColumns().add(weightViscera_TC);
					weightViscera_TC = null;
					
				}
			}
		}
		
		//sortTableColumn();
		
	}
	
	public void sortTableColumnBysnWeight()
	{
		if(null!=tableStructure&&tableStructure.size()>0)
		{
			Collections.sort(tableStructure,new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					int snWeight=0,snWeight2=0;
					if(o1.equals("动物编号"))
					{ 
						return -1;
					}
					if(o2.equals("动物编号"))
					{
						return 1;
					}
					else
					{
						DictViscera dv1=null,dv2=null;
						for(DictViscera dv:visceraSortList)
						{
							
							if(dv.getVisceraName().equals(o1.substring(0,o1.indexOf("("))))
							{
								if(dv.getpVisceraCode()!=null)
								{
									for(DictViscera pdv:visceraSortList)
									{
										if(dv.getpVisceraCode().equals(pdv.getVisceraCode()))
										{
											dv1 = dv;
											snWeight = pdv.getSnWeight();
										}
									}
								}else
								{
									
									snWeight = dv.getSnWeight();
								}
								
							}
							if(dv.getVisceraName().equals(o2.substring(0,o2.indexOf("("))))
							{
								if(dv.getpVisceraCode()!=null)
								{
									for(DictViscera pdv:visceraSortList)
									{
										if(dv.getpVisceraCode().equals(pdv.getVisceraCode()))
										{
											dv2 = dv;
											snWeight2 = pdv.getSnWeight();
										}
									}
								}else
								{
									snWeight2 = dv.getSnWeight();
								}
								
							}
					
						}
						if(dv1!=null&&dv2!=null)
						{
							if(dv1.getpVisceraCode().equals(dv2.getpVisceraCode()))
							{
								snWeight = dv1.getSnWeight();
								snWeight2 = dv2.getSnWeight();
							}
						}
						
						return snWeight-snWeight2;
					}
				}
			});
			//排序
			Collections.sort(weightCompareTable.getColumns(),new Comparator<TableColumn<Map<String,List<visceraWeight>>,?>>() {
	
				@Override
				public int compare(TableColumn<Map<String,List<visceraWeight>>,?> o1, TableColumn<Map<String,List<visceraWeight>>,?> o2) {
				//	System.out.println(o1.getText()+"------"+o2.getText());
				//	System.out.println(o1.getCellValueFactory()+"===="+o2.getCellValueFactory());
					if(o1.getText().equals("动物编号"))
					{ 
						return -1;
					}
					else if(o2.getText().equals("动物编号"))
					{
						return 1;
					}else
					{
						int snWeight=0,snWeight2=0;
						DictViscera dv1=null,dv2=null;
						for(DictViscera dv:visceraSortList)
						{
							if(dv.getVisceraName().equals(o1.getText().substring(0,o1.getText().indexOf("("))))
							{
								if(dv.getpVisceraCode()!=null)
								{
									for(DictViscera pdv:visceraSortList)
									{
										if(dv.getpVisceraCode().equals(pdv.getVisceraCode()))
										{
											dv1 = dv;
											snWeight = pdv.getSnWeight();
										}
									}
								}else
								{
									snWeight = dv.getSnWeight();
								}
							}
							if(dv.getVisceraName().equals(o2.getText().substring(0,o2.getText().indexOf("("))))
							{
								if(dv.getpVisceraCode()!=null)
								{
									for(DictViscera pdv:visceraSortList)
									{
										if(dv.getpVisceraCode().equals(pdv.getVisceraCode()))
										{
											dv2 = dv;
											snWeight2 = pdv.getSnWeight();
										}
									}
								}else
								{
									snWeight2 = dv.getSnWeight();
								}
							}
						}
						if(dv1!=null&&dv2!=null&&dv1.getpVisceraCode().equals(dv2.getpVisceraCode()))
						{
							snWeight = dv1.getSnWeight();
							snWeight2 = dv2.getSnWeight();
						}
						
						return snWeight-snWeight2;
					}
				}
			});
			
		}
	}

	private void deleteWeightCompareTableStructure()
	{
		List<TableColumn<Map<String,List<visceraWeight>>,?>> deleteList = new ArrayList<>();
		deleteList.addAll(weightCompareTable.getColumns());
		
		for(TableColumn<Map<String,List<visceraWeight>>,?> tc:deleteList)
		{
			tc.setCellValueFactory(null);
			tc.setCellFactory(null);
			tc.setGraphic(null);
			tc.setStyle("-fx-skin:null");
			weightCompareTable.getColumns().remove(tc);
			tc = null;
			
		}//解除tablecolumn和tableview的关联。然后再直接新建一个tableview
		deleteList = null;
		weightCompareTable.setSkin(null);
		// <TableView fx:id="weightCompareTable" prefHeight="396.0" prefWidth="809.0" />
        
		weightCompareTable = new TableView<Map<String, List<visceraWeight>>>();
		weightCompareTable.setPrefHeight(396.0);
		weightCompareTable.setPrefWidth(809.0);
		
		weightCompareTab.setContent(weightCompareTable);
		data_weightCompareTable = FXCollections.observableArrayList();
		initWeightCompareTable();
	   
	}
	/**
	 * 更新脏器称重-对比信息
	 */
	private void updateWeightCompareTable(String taskId,String sessionId,String visceraName,String animalCode){
		data_weightCompareTable.clear();
		tableStructure.removeAll(tableStructure);
		
		deleteWeightCompareTableStructure();
		
		//tableStructure.add("动物编号");
		List<String> visceraNum = new ArrayList<String>();
		List<String> animalNum = new ArrayList<>();
	//	previewWeightCompareTable.setDisable(false);
		saveWeightAsExcel.setDisable(false);
		List<Map<String,Object>> taskMapList = BaseService.getInstance().getTblVisceraWeightService().getListByConditionss(taskId,sessionId,visceraName,animalCode);
		weightVisceraNumber=taskMapList.size();
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String wid = (String) map.get("id");//主键
				//String sessionId = (String)map.get("sessionId");
				String wstudyNo = (String) map.get("studyNo");//专题编号
				String wanimalCode = (String) map.get("animalCode");//动物编号
				String  wvisceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  weight = (String) map.get("weight");//重量
				String  weightUnit = (String) map.get("weightUnit");//重量单位
				//weight = weight + " "+weightUnit+"";
				Integer fixedWeighFlag = (Integer) map.get("fixedWeightFlag");//是否固定后称重
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
				String attachedVisceraNames = (String) map.get("attachedVisceraNames" ) ;//附加脏器列表
				
				if( null != subVisceraName && !subVisceraName.equals("") ){
//					wvisceraName = wvisceraName+" "+subVisceraName;
					wvisceraName = subVisceraName;
				}else if(null!=attachedVisceraNames&&!attachedVisceraNames.equals("")){
//						wvisceraName = wvisceraName+" "+subVisceraName;
						wvisceraName = wvisceraName+"("+attachedVisceraNames+")";
				}
				String balCode = (String) map.get("balCode") ;//天平编号
				
				if(!tableStructure.contains(wvisceraName+"("+weightUnit+")"))
				{
					tableStructure.add(wvisceraName +"("+weightUnit+")");
				}
				if(wvisceraName!=null&&!"".equals(wvisceraName)&&!exist(visceraNum,wvisceraName))
					visceraNum.add(wvisceraName);
				
				boolean flag = false;
				for(Map<String,List<visceraWeight>> animalMap : data_weightCompareTable)
				{
					if(null!=animalMap.get(wanimalCode))
					{
						animalMap.get(wanimalCode).add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
						wvisceraName, weight,weightUnit,fixedWeighFlag,attachedVisceraNames,
						operator,operateTime,balCode));
						flag = true;
						break;
					}
				}
				if(!flag)
				{
					List<visceraWeight> list = new ArrayList<>();
					list.add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
						wvisceraName, weight,weightUnit,fixedWeighFlag,attachedVisceraNames,
						operator,operateTime,balCode));
					Map<String, List<visceraWeight>> map2 = new HashMap<String,List<visceraWeight>>();
					map2.put(wanimalCode,list); 
					
					if(!exist(animalNum,wanimalCode))
					{
						animalNum.add(wanimalCode);
					}
					data_weightCompareTable.add(map2); 
					
					list = null;
					map2 = null;
				}
			}
			Collections.sort(data_weightCompareTable,new Comparator<Map<String,List<visceraWeight>>>() {

				@Override
				public int compare(Map<String,List<visceraWeight>> o1, Map<String,List<visceraWeight>> o2) {
					return ((String)o1.keySet().toArray()[0]).compareTo((String)o2.keySet().toArray()[0]);
				}
			});
		}
		
		updateWeightCompareTableStructure();//刷新表结构。
		//sortTableColumnBysnWeight();//排序表列
	
		String visceraWeightLabelStr="";
		if(sessionId==null ||"全部".equals(sessionId)) 
		{
			visceraWeightLabelStr+="";
			
		}else
		{
			visceraWeightLabelStr+="会话:("+weightSessionComboBox.getSelectionModel().getSelectedItem()+")  ";	
		}
		if(animalCode==null ||"全部".equals(animalCode)||"".equals(animalCode))
		{
			//visceraWeightLabelStr+="动物个数:"+(data_weightAnimalList.size()-1)+"个     ";
			visceraWeightLabelStr+="动物数量:"+animalNum.size()+"只  ";
		}else
		{
			visceraWeightLabelStr+="动物编号:"+animalCode+"  ";
		}
		if (visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) 
		{
			//visceraWeightLabelStr+="脏器:"+(data_weightVisceraNameComboBox.size()-1)+"个     ";
			visceraWeightLabelStr+="已称重脏器:"+visceraNum.size()+"类  ";
		}else
		{
			visceraWeightLabelStr+="脏器:"+visceraName+"  ";
		}
		visceraWeightLabelStr+="记录数量:"+weightVisceraNumber+"";
		/*if((sessionId==null ||"全部".equals(sessionId)) && (visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName)) && (animalCode==null ||"全部".equals(animalCode)||"".equals(animalCode))){
			//weightVisceraNumber=data_weightCompareTable.size();
		    if(data_weightAnimalList.size()<2){
		    	weightAnimalNumber=0;
		    }else{
		    	weightAnimalNumber=data_weightAnimalList.size()-1;
		    }
		}*/
	//	visceraWeightLabel.setText("已称重动物："+weightAnimalNumber+"只"+" 已称重脏器："+weightVisceraNumber+"个");
		visceraWeightLabel.setText(visceraWeightLabelStr);
	}
	/**
	 * 初始化固定-会话下拉框
	 */
	private void initFixedSessionComboBox(){
		fixedSessionComboBox.setItems(data_fixedSessionComboBox);
		fixedSessionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
				//	String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
				//	String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionId=(String) fixedSessionMap.get(newValue);
					updataFixedVisceraNameComboBox(taskId,sessionId);
					updataFixedAnimalList(taskId,sessionId);
				}
			}
		});
		
	}
	/**
	 * 更新固定-会话下拉框
	 */
	private void upadataFixedSessionComboBox(String taskId,int tabIndex){
		data_fixedSessionComboBox.clear();
		fixedSessionMap.clear();
		List<Map<String, Object>> list=BaseService.getInstance().getTblPathSessionService().getAnatomySessionListByTaskIds(taskId,tabIndex);
		data_fixedSessionComboBox.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String, Object> map:list){
				String sessionId=(String)map.get("sessionId");
				int sessionType=(Integer)map.get("sessionType");
				String creator=(String)map.get("sessionCreator");
				Date createTime=(Date)map.get("createdTime");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String createTimeStr=sdf.format(createTime);
				String sessionInfo="";
				if(sessionType==4){
					sessionInfo=createTimeStr+" 固定  "+creator;
				}else if(sessionType==5){
					sessionInfo=createTimeStr+" 剖检、固定  "+creator;
				}else if(sessionType==6){
					sessionInfo=createTimeStr+" 称重、固定  "+creator;
				}else if(sessionType==7){
					sessionInfo=createTimeStr+" 剖检、称重、固定  "+creator;
				}
				data_fixedSessionComboBox.add(sessionInfo);
				fixedSessionMap.put(sessionInfo, sessionId);
			}
		}
		fixedSessionComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化固定-脏器下拉框
	 */
	private void  initFixedVisceraNameComboBox(){
		fixedVisceraNameComboBox.setItems(data_fixedVisceraNameComboBox);
		fixedVisceraNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					
					String sessionInfo=fixedSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) fixedSessionMap.get(sessionInfo);
					//String animalCode=fixedAnimalList.getSelectionModel().getSelectedItem();
					//updateFixedTab(taskId,sessionId,newValue,animalCode);
					String animalName = fixedAnimalList.getSelectionModel().getSelectedItem();
					Tab tab = fixedTabPane.getSelectionModel().getSelectedItem();
					if(tab.equals(fixedDetailTab)){
						saveFixedAsExcel.setDisable(true);
						updateFixedDetailTab(taskId,sessionId,newValue,animalName);
					}else if(tab.equals(fixedCompareTab)){
						saveFixedAsExcel.setDisable(false);
						updateFixedCompareTab(taskId,sessionId,newValue,animalName);
				    }
				}
			}
		});
	}
	/**
	 * 更新固定-脏器下拉框
	 */
	private void updataFixedVisceraNameComboBox(String taskId,String sessionId){
		data_fixedVisceraNameComboBox.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getVisceraNameAndCodeLists(taskId,sessionId);
		if(null!=list && list.size()>0){
			data_fixedVisceraNameComboBox.add("全部");
			for(Map<String,Object> map:list){
				String visceraName=(String)map.get("visceraName");
				data_fixedVisceraNameComboBox.add(visceraName);
			}
			fixedVisceraNameComboBox.getSelectionModel().selectFirst();
		}
		
	}
	/**
	 * 初始化固定-动物列表
	 */
	private void initFixedAnimalList(){
		fixedAnimalList.setItems(data_fixedAnimalList);
		fixedAnimalList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					
					String sessionInfo=fixedSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) fixedSessionMap.get(sessionInfo);
					String visceraName=fixedVisceraNameComboBox.getSelectionModel().getSelectedItem();
					Tab tab = fixedTabPane.getSelectionModel().getSelectedItem();
					if(tab.equals(fixedDetailTab)){
						saveFixedAsExcel.setDisable(true);
						updateFixedDetailTab(taskId,sessionId,visceraName,newValue);
					}else if(tab.equals(fixedCompareTab)){
						saveFixedAsExcel.setDisable(false);
						updateFixedCompareTab(taskId,sessionId,visceraName,newValue);
				    }
					
				}
			}
		});
	}
	/**更新固定-动物列表
	 * @param taskId
	 * @param sessionId
	 */
	private void updataFixedAnimalList(String taskId,String sessionId){
		data_fixedAnimalList.clear();
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getAnimalCodeBySessionIds(taskId,sessionId);
		data_fixedAnimalList.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String,Object> map:list){
				String animalCode=(String)map.get("animalCode");
				Integer autolyzeFlag=(Integer)map.get("autolyzeFlag");
				if(autolyzeFlag==1){
					data_fixedAnimalList.add(animalCode+"(自溶)");
				}else{
					data_fixedAnimalList.add(animalCode);
				}
			}
		}
		fixedAnimalList.getSelectionModel().selectFirst();
		
	}
	/**
	 * 初始化脏器固定记录tabPane
	 */
	public void initVisceraFixedTabPane()
	{
		fixedTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab tab) {
				if(null!=tab){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=fixedSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) fixedSessionMap.get(sessionInfo);
					String visceraName=fixedVisceraNameComboBox.getSelectionModel().getSelectedItem();
					String animalName = fixedAnimalList.getSelectionModel().getSelectedItem();
					if(tab.equals(fixedDetailTab)){
						saveFixedAsExcel.setDisable(true);
						updateFixedDetailTab(taskId,sessionId,visceraName,animalName);
					}else if(tab.equals(fixedCompareTab)){
						saveFixedAsExcel.setDisable(false);
						updateFixedCompareTab(taskId,sessionId,visceraName,animalName);
				    }
				}
			}
		});
		saveFixedAsExcel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(fixedTableStructure.size()==0)
				{
					Messager.showMessage("没有数据，请检查！");
				}else
				{	
				
					POIExcelUtil excelUtil = new POIExcelUtil();
					HSSFSheet sheet = excelUtil.newSheet("固定单");
					HSSFRow row = sheet.createRow(0); //创建Excel工作表的行
					//填充表头，第二列开始是动物编号，
					for(int i=0;i<fixedTableStructure.size();i++)
					{
						HSSFCell cell = row.createCell(i);
						cell.setCellValue(fixedTableStructure.get(i));
					}
					//第二行开始，填充表的内容
				
					
						//填充内容
						for(int j=0;j<data_visceraFixedCompareTable.size();j++)
						{
							Map<String,List<List<VisceraFixedCompare>>> vfMap = data_visceraFixedCompareTable.get(j);
							HSSFRow tempRow = sheet.createRow(j+1);
							String key = (String) vfMap.keySet().toArray()[0];
							List<List<VisceraFixedCompare>> list = vfMap.get(key);
							
							tempRow.createCell(0).setCellValue(key);//第一列
							//第二列以后的值
							
								if(key.contains("完成时间"))
								{
									//list中是这次查询的全部动物,fixedTableStructure的第一行是脏器编号
									for(int temp=1;temp<fixedTableStructure.size();temp++)
									{
										String cellValue = "";
										String animalCode = fixedTableStructure.get(temp);
										for(List<VisceraFixedCompare> vfs:list)
										{
											for(VisceraFixedCompare vf:vfs)
											{
												if(vf.getAnimalCode().equals(animalCode))
												{
													String time = vf.getOperateTime().substring(vf.getOperateTime().indexOf("间")+1);
													
													/*if(time.equals(""))
														cellValue = "没有固定完成";
													else{*/
														
														cellValue =  time;
													//}
													/*if(cellValue.equals(""))
														cellValue = "完成时间列，但是没有找到"+vf.getAnimalCode();
													*/
													HSSFCell cell =tempRow.createCell(temp);
													cell.setCellValue(cellValue);
												}
											}
										}
									
									}
								}
								else
								{
									for(List<VisceraFixedCompare> vfs:list)
									{
										for(VisceraFixedCompare vf:vfs)
										{
											int columnNum = fixedTableStructure.indexOf(vf.getAnimalCode());
											HSSFCell cell =tempRow.createCell(columnNum);
											
											String returnValue="";
											if(vf.get_autolyzaFlag()!=null&&"Y".equals(vf.get_autolyzaFlag()))
											{
												if(vf.getSubVisceraName()!=null)
												{
													
													for(VisceraFixedCompare tempVf:vfs)
													{
														if(tempVf.get_autolyzaFlag()!=null&&"Y".equals(tempVf.get_autolyzaFlag()))
														{
//															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName())&&!tempVf.getSubVisceraName().equals(vf.getSubVisceraName()))
															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName()))
															{
																returnValue += tempVf.getSubVisceraName()+"自溶";
															}
														}
													}
												}else
												{
													returnValue = "自溶";
												}
												
												 
											}
											if(vf.get_missFlag()!=null&&("Y".equals(vf.get_missFlag())||"Y2".equals(vf.get_missFlag())))
											{
												
												if(vf.getSubVisceraName()!=null)
												{
													returnValue = vf.getSubVisceraName()+vf.get_missType();
													
													//查找是否还存在其他的丢失子脏器
													for(VisceraFixedCompare tempVf:vfs)
													{
														if(tempVf.get_missFlag()!=null&&("Y".equals(tempVf.get_missFlag())||"Y2".equals(tempVf.get_missFlag())))
														{
//															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName())&&!tempVf.getSubVisceraName().equals(vf.getSubVisceraName()))
															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName()))
															{
																returnValue += tempVf.getSubVisceraName()+vf.get_missType();
															}
														}
													}
													
												}else
												{
													returnValue = vf.get_missType();
												}
											}
											if(vf.get_fixedFlag()!=null&&"Y".equals(vf.get_fixedFlag()))
											{
												returnValue="√";
											}
											cell.setCellValue(returnValue);
											//cell.setCellValue("√");
										}
									}
								}
								
							list = null;
							vfMap = null;
						}
						
						HSSFRow remarkRow = sheet.createRow(sheet.getLastRowNum()+1); //创建Excel工作表的行
						HSSFCell remarkCell = remarkRow.createCell(0);
						remarkCell.setCellValue(remarks.getText());
						
					try {
						boolean f = excelUtil.save("脏器固定",stage);
						if(f)
							Messager.showMessage("导出成功！");
						//else
						//	Messager.showMessage("导出失败！");
					} catch (IOException e){
						Messager.showErrorMessage("导出失败！");
						e.printStackTrace();
					}
				}
			}
		});
	}
	/**
	 * 初始化脏器固定记录表
	 */
	public void initVisceraFixedTable(){
		visceraFixedTable.setItems(data_visceraFixedTable);
		fixedIdCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("id"));
	//	fixedStudyNoCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("studyNo"));
		fixedAnimalCodeCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("animalCode"));
		fixedVisceraNameCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("visceraName"));
		fixedOperatorCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("operator"));
		fixedOperateTimeCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("operateTime"));
		visceraFixedTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<VisceraFixed>(){

			@Override
			public void changed(ObservableValue<? extends VisceraFixed> arg0, VisceraFixed arg1,
					VisceraFixed newValue) {
							if(null != newValue){
//								anatomyCheckTable.getSelectionModel().clearSelection();
//								visceraWeightTable.getSelectionModel().clearSelection();
//								visceraFixedTable.getSelectionModel().clearSelection();
							}else{
							}
				}

		});
		fixedOperateTimeCol.setCellFactory(new Callback<TableColumn<VisceraFixed,String>,TableCell<VisceraFixed,String>>(){
	    	 @Override
	    	 public TableCell<VisceraFixed, String> call(
	    			 TableColumn<VisceraFixed, String> param) {
	    		 TableCell<VisceraFixed, String> cell =
	    				 new TableCell<VisceraFixed, String>() {
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
	    		 cell.setSkin(null);
	    		 return cell;
	    	 }
	     });
	}
	/**更新脏器固定记录
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 */
	private void updateFixedDetailTab(String taskId,String sessionId,String visceraName,String animalCode){
		data_visceraFixedTable.clear();
		List<String> animalNum = new ArrayList<String>();
		List<String> visceraNum = new ArrayList<String>();
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getListByConditionss(taskId,sessionId,visceraName,animalCode);
		if(null!=list&&list.size()>0){
			for(Map<String,Object> map:list){
				String id=(String) map.get("id");
				String studyNo=(String) map.get("studyNo");
				//String sessionId=(String) map.get("sessionId");
				String animalCode1=(String) map.get("animalCode");
				String visceraName1=(String) map.get("visceraName");
				if(null!=(String) map.get("subVisceraName")){
					visceraName1=visceraName1+","+(String) map.get("subVisceraName");
				}
				String operator=(String) map.get("operator");
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
				Date operateTime=(Date) map.get("operateTime");
				String operateTimeStr= sdf.format(operateTime);
//				if(null != visceraName1 && !visceraName1.equals("") ){
				if(!exist(animalNum,animalCode1))
					animalNum.add(animalCode1);
				if(visceraName1!=null&&!"".equals(visceraName1)&&!exist(visceraNum,visceraName1))
					visceraNum.add(visceraName1);
					
					data_visceraFixedTable.add(new VisceraFixed(id,studyNo,sessionId,animalCode1,visceraName1,operator,operateTimeStr));
//				}
			}
			Collections.sort(data_visceraFixedTable,new Comparator<VisceraFixed>() {

				@Override
				public int compare(VisceraFixed o1, VisceraFixed o2) {
					return o1.getAnimalCode().compareTo(o2.getAnimalCode());
				}
			});
			
		}
		String fixedLabelStr="";
		if(sessionId==null ||"全部".equals(sessionId)) 
		{
			fixedLabelStr+="";
		}else{
			fixedLabelStr+="会话:("+fixedSessionComboBox.getSelectionModel().getSelectedItem()+")  ";
		}
		if (animalCode==null ||"全部".equals(animalCode))
		{
			//fixedLabelStr+="已固定动物:"+(data_fixedAnimalList.size()-1)+"个     ";
			fixedLabelStr+="动物数量:"+animalNum.size()+"只  ";
		}else{
			fixedLabelStr+="动物编号:"+animalCode+"  ";
		}
		if(visceraName==null ||"全部".equals(visceraName))
		{
			//visceraName+="已固定脏器:"+(data_fixedVisceraNameComboBox.size()-1)+"个";
			fixedLabelStr+="已固定脏器:"+visceraNum.size()+"类  ";
		}else{
			fixedLabelStr+="脏器:"+visceraName+"  ";
		}
		fixedLabelStr+="记录数量:"+data_visceraFixedTable.size()+"";
			
		/*if((sessionId==null ||"全部".equals(sessionId)) && (visceraName==null ||"全部".equals(visceraName)) && (animalCode==null ||"全部".equals(animalCode))){
			fixedVisceraNumber=data_visceraFixedTable.size();
		    if(data_fixedAnimalList.size()<2){
		    	fixedAnimalNumber=0;
		    }else{
		    	fixedAnimalNumber=data_fixedAnimalList.size()-1;
		    }
		}*/
		//fixedLabel.setText("已固定动物："+fixedAnimalNumber+"只"+" 已固定脏器："+fixedVisceraNumber+"个");
		fixedLabel.setText(fixedLabelStr);
	}
	/**
	 * 初始化脏器固定对比记录表
	 */
	public void initVisceraFixedCompareTable()
	{
		visceraFixedCompareTable.setItems(data_visceraFixedCompareTable);
		//初始化第一列
		TableColumn<Map<String,List<List<VisceraFixedCompare>>>,String> fixedViscera_TC = new TableColumn<Map<String,List<List<VisceraFixedCompare>>>,String>("脏器编号");
		 //maxWidth="5000.0" minWidth="10.0" prefWidth="113.0" 
		fixedViscera_TC.setMinWidth(10);
		fixedViscera_TC.setPrefWidth(100.0);
		fixedViscera_TC.setSortable(false);
		fixedTableStructure.add("脏器编号");
		fixedViscera_TC.setCellValueFactory(new Callback<CellDataFeatures<Map<String,List<List<VisceraFixedCompare>>>, String>, ObservableValue<String>>() {
		    public ObservableValue<String> call(CellDataFeatures<Map<String,List<List<VisceraFixedCompare>>>, String> p) {
		    	 // p.getValue() returns the Person instance for a particular TableView row
		    //	final CellDataFeatures<Map<String,ObservableList<VisceraFixed>>, String> p1 = p;
					    	
			//	ObservableValue<String> s1 = new ObservableValueBase<String>() {
				//	@Override
				//	public String getValue() {
						String key =  p.getValue().keySet().toArray()[0].toString();
						//return key;
				//	}
				//};
				return new SimpleStringProperty(key);
			}
		});
		visceraFixedCompareTable.getColumns().add(fixedViscera_TC);
		fixedViscera_TC = null;
		
	}

	/**
	 * 更新脏器固定对比记录表
	 * @param taskId
	 * @param sessionId
	 * @param visceraName
	 * @param animalCode
	 */
	private void updateFixedCompareTab(String taskId,String sessionId,String visceraName,String animalCode){
		data_visceraFixedCompareTable.clear();
		fixedTableStructure.removeAll(fixedTableStructure);
		
		deleteFixedCompareTableStructure();
		
		//fixedTableStructure.add("脏器编号");
		int recordNum = 0;
		List<String> animalNum = new ArrayList<String>();
		List<String> visceraNum = new ArrayList<String>();
		String animalState=stateComboBox.getSelectionModel().getSelectedItem();
		updataAnimalTable(taskId,animalState,sessionId);//先更新data_animalTable
		
		List<VisceraFixedCompare> listVisceraFixedFinishTime = new ArrayList<>();
		Map<String, List<List<VisceraFixedCompare>>> mapTemp = new HashMap<String,List<List<VisceraFixedCompare>>>();
		String finishTime = "完成时间";
		for(AnimalInfo animalInfo:data_animalTable)//根据animalCode查找animalInfo。
		{
			VisceraFixedCompare vfFinishTime = new VisceraFixedCompare();
			vfFinishTime.setAnimalCode(animalInfo.getAnimalCode());
			//固定完成时间只要时分。
			//System.out.println("time1 is "+animalInfo.getVisceraFixedFinishTime());
			String time = animalInfo.getVisceraFixedFinishTime();
			if(animalInfo.getVisceraFixedFinishTime()!=null&&!"".equals(animalInfo.getVisceraFixedFinishTime()))
			    time = DateUtil.dateToString(DateUtil.stringToDate(time,"yyyy-mm-dd HH:mm"),"HH:mm");
		//	System.out.println("time is "+time);
			vfFinishTime.setOperateTime(finishTime+time);
			listVisceraFixedFinishTime.add(vfFinishTime);
		}
		List<List<VisceraFixedCompare>> tempL=new ArrayList<>();
		tempL.add(listVisceraFixedFinishTime);
		mapTemp.put("完成时间",tempL); 
		data_visceraFixedCompareTable.add(mapTemp); 
		listVisceraFixedFinishTime = null;
		mapTemp = null;
		
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getListByConditions2s(taskId,sessionId,visceraName,animalCode);
		String fixedRemarkString = "";
		if(null!=list&&list.size()>0){
			recordNum = list.size();

			for(Map<String,Object> map:list){
				String id=(String) map.get("id");
				String studyNo=(String) map.get("studyNo");
				//String sessionId=(String) map.get("sessionId");
				String animalCode1=(String) map.get("animalCode");
				String visceraName1=(String) map.get("visceraName");
				String subVisceraCode =(String) map.get("subVisceraCode");
				String subVisceraName =(String) map.get("subVisceraName");
				/*if(null!=(String) map.get("subVisceraName")){
					visceraName1=visceraName1+","+(String) map.get("subVisceraName");
				}*/
				String operator=(String) map.get("operator");
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
				Date operateTime=(Date) map.get("operateTime");
				String operateTimeStr="";
				if(operateTime!=null)
				 operateTimeStr= sdf.format(operateTime);
				
				String _fixedFlag = (String)map.get("_fixedFlag");
				String _autolyzaFlag = (String)map.get("_autolyzaFlag");
				String _missType = (String)map.get("_missType");
				String _missFlag = (String)map.get("_missFlag");
				String missingRsn = (String)map.get("missingRsn");
				
				if(_missFlag!=null&&("Y".equals(_missFlag)||"Y2".equals(_missFlag)))
				{
					if(subVisceraName!=null)
					{
						if(missingRsn!=null)
							fixedRemarkString += " "+animalCode1+" "+subVisceraName+_missType+":"+missingRsn+";";
						else
							fixedRemarkString += " "+animalCode1+" "+subVisceraName+_missType+";";
					}else
					{
						if(missingRsn!=null)
							fixedRemarkString += " "+animalCode1+" "+visceraName1+_missType+":"+missingRsn+";";
						else
							fixedRemarkString += " "+animalCode1+" "+visceraName1+_missType+";";
					}
				}
				
				if(!fixedTableStructure.contains(animalCode1))
					fixedTableStructure.add(animalCode1);
				if(!exist(animalNum,animalCode1))
					animalNum.add(animalCode1);
				boolean flag = false;
				for(Map<String,List<List<VisceraFixedCompare>>> fixedMap : data_visceraFixedCompareTable)
				{
					if(null!=fixedMap.get(visceraName1))
					{
					if(visceraName1!=null&&!"".equals(visceraName1)&&!exist(visceraNum,visceraName1))
						visceraNum.add(visceraName1);
						VisceraFixedCompare vcompare = new VisceraFixedCompare(id,studyNo,sessionId,animalCode1,visceraName1,subVisceraCode,subVisceraName,operator,operateTimeStr,_fixedFlag,_autolyzaFlag,_missFlag,_missType,missingRsn);
						if(fixedMap.get(visceraName1)==null)
						{
							List<VisceraFixedCompare> vcompares = new ArrayList<>();
							vcompares.add(vcompare);
							fixedMap.get(visceraName1).add(vcompares);
							//不存在的加1
							fixedVisceraNumber++;
						}else
						{
							boolean flag2 = false;
							for(List<VisceraFixedCompare> vcompares2: fixedMap.get(visceraName1))
							{
								if(animalCode1.equals(vcompares2.get(0).getAnimalCode()))
								{
									vcompares2.add(vcompare);
									flag2=true;
									break;
								}
							
							}
							if(!flag2)
							{
								List<VisceraFixedCompare> vcompares = new ArrayList<>();
								vcompares.add(vcompare);
								fixedMap.get(visceraName1).add(vcompares);
							}
						}
						
						flag = true;
						break;
					}
				}
				if(!flag)
				{
					List<VisceraFixedCompare> listVisceraFixed = new ArrayList<>();
					listVisceraFixed.add(new VisceraFixedCompare(id,studyNo,sessionId,animalCode1,visceraName1,subVisceraCode,subVisceraName,operator,operateTimeStr,_fixedFlag,_autolyzaFlag,_missFlag,_missType,missingRsn));
					Map<String, List<List<VisceraFixedCompare>>> map2 = new HashMap<String,List<List<VisceraFixedCompare>>>();
					List<List<VisceraFixedCompare>> temp2 = new ArrayList<>();
					temp2.add(listVisceraFixed);
					map2.put(visceraName1,temp2); 
					if(visceraName1!=null&&!"".equals(visceraName1)&&!exist(visceraNum,visceraName1))
						visceraNum.add(visceraName1);
					data_visceraFixedCompareTable.add(map2); 
					fixedVisceraNumber++;
					
					listVisceraFixed = null;
					map2 = null;
				}
			}
		}
		
		/*for(Map<String,List<List<VisceraFixedCompare>>> vfss:data_visceraFixedCompareTable)
		{
			Object[] keys = vfss.keySet().toArray();
			for(int i=0;i<keys.length;i++)
			{
				String key = (String)keys[i];
				List<List<VisceraFixedCompare>> templist = vfss.get(key);
				System.out.println("key="+key);
				if(!key.equals("完成时间"))
				{
					for(List<VisceraFixedCompare> lists:templist)
					{
						System.out.println("======");
						for(VisceraFixedCompare vf:lists)
						{
							System.out.println("===="+vf.getAnimalCode()+"=="+vf.getVisceraName()+"=="+vf.getSubVisceraName());
						}
					}
				}
				
				
			}
			
		}*/
		
		
		//按动物编号排序列
		Collections.sort( visceraFixedCompareTable.getColumns(),new Comparator<TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?>>() {
			@Override
			public int compare(TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?> o1, TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?> o2) {
				if(o1.getText().equals("脏器编号"))
					return -1;
				if(o2.getText().equals("脏器编号"))
					return 1;
				return o1.getText().compareTo(o2.getText());
			}
		});
		Collections.sort(fixedTableStructure,new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if(o1.equals("脏器编号"))
					return -1;
				if(o2.equals("脏器编号"))
					return 1;
				return o1.compareTo(o2);
			}
		});
		/*if((sessionId==null ||"全部".equals(sessionId)) && (visceraName==null ||"全部".equals(visceraName)) && (animalCode==null ||"全部".equals(animalCode))){
		    if(data_fixedAnimalList.size()<2){
		    	fixedAnimalNumber=0;
		    }else{
		    	fixedAnimalNumber=data_fixedAnimalList.size()-1;
		    }
		}*/
		String fixedLabelStr="";
		if(sessionId==null ||"全部".equals(sessionId)) 
		{
			fixedLabelStr+="";
		}else{
			fixedLabelStr+="会话:("+fixedSessionComboBox.getSelectionModel().getSelectedItem()+")  ";
		}
		if (animalCode==null ||"全部".equals(animalCode))
		{
			//fixedLabelStr+="已固定动物:"+(data_fixedAnimalList.size()-1)+"个     ";
			fixedLabelStr+="动物数量:"+animalNum.size()+"只  ";
		}else{
			fixedLabelStr+="动物编号:"+animalCode+"  ";
		}
		if(visceraName==null ||"全部".equals(visceraName)||"".equals(visceraName))
		{
			//visceraName+="已固定脏器:"+(data_fixedVisceraNameComboBox.size()-1)+"个";
			fixedLabelStr+="已固定脏器:"+visceraNum.size()+"类  ";
			
		}else{
			fixedLabelStr+="脏器:"+visceraName+"  ";
		}
		fixedLabelStr+="记录数量:"+recordNum+"";
		fixedLabel.setText(fixedLabelStr);
		
		List<Map<String,Object>> mapList2 = BaseService.getInstance().getTblVisceraFixedService().getNormallessListByTaskIdAndSessionids(taskId,sessionId);//备注
		//List<Map<String,Object>> mapList2 = BaseService.getInstance().getTblVisceraFixedService().getNormalLessListBySessionid(sessionId);//备注
		String remarksString ;
		    if(null != mapList2 && mapList2.size() > 0 ){
		    	remarksString = "备注：（";
		    	for(Map<String,Object> mapObj:mapList2){
		    		remarksString = remarksString+(String)mapObj.get("animalCode")+" ";//动物编号
		  			String visceraNameString =  (String)mapObj.get("visceraName");//脏器名称
		  			if(null == visceraNameString ){
		  				visceraName = "";
		  			}else{
		  				remarksString = remarksString+" "+visceraNameString;
		  			}
//		  			String subVisceraName =  (String)mapObj.get("subVisceraName");//子脏器名称
//		  			if(null == subVisceraName ){
//		  				subVisceraName = "";
//		  			}else{
//		  				remarks = remarks+" "+subVisceraName;
//		  			}
		  			String anatomyPos  =  (String)mapObj.get("anatomyPos");//解剖学所见部位60
		  			if(null == anatomyPos ){
		  				anatomyPos = "";
		  			}else{
		  				remarksString = remarksString+""+anatomyPos;
		  			}
		  			String bodySurfacePos =  (String)mapObj.get("bodySurfacePos");//体表部位60
		  			if(null == bodySurfacePos ){
		  				bodySurfacePos = "";
		  			}else{
		  				remarksString = remarksString+""+bodySurfacePos;
		  			}
		  			String anatomyFingding =  (String)mapObj.get("anatomyFingding"); //解剖所见100
		  			if(null == anatomyFingding ){
		  				anatomyFingding = "";
		  			}else{
		  				remarksString = remarksString+""+anatomyFingding;
		  			}

		  			remarksString = remarksString+"; ";
//		  			remarks = remarks+animalCode+" "+visceraName+" "+subVisceraName+" "+anatomyPos+" "+anatomyFingding+" "+bodySurfacePos+
//		  					" "+pos+" "+shape+" "+color+" "+texture+" "+number1+" "+range+" "+lesionDegree+" "+size+";";
		  		}
		    	if("".equals(fixedRemarkString))
		    		remarksString = remarksString+ "）";
		    	else
		    		remarksString = remarksString+" "+fixedRemarkString+ "）";
		    }else{
		    	remarksString = "备注：";
		    	if(!"".equals(fixedRemarkString))
		    		remarksString = remarksString+"（ "+fixedRemarkString+ "）";
		    	else
		    		remarksString = remarksString+"NA";
		    }
		    
		remarks.setText(remarksString);
		//fixedLabel.setText("已固定动物："+fixedAnimalNumber+"只"+" 已固定脏器："+fixedVisceraNumber+"个");
		
		updateFixedCompareTableStructure();
		//按固定顺序排列行,数据是按snFixed排序的。所以不用再次排序
		//sortTableRowBysnFixed();
	}
	public void deleteFixedCompareTableStructure()
	{
		
		/*List<TableColumn<Map<String,ObservableList<VisceraFixed>>,?>> deleteList = new ArrayList<>();
		//删除多余的列
		for(TableColumn<Map<String,ObservableList<VisceraFixed>>,?> tc:visceraFixedCompareTable.getColumns())
		{
			if(!column.getText().equals("脏器编号")&&!fixedTableStructure.contains(column.getText()))
			{
				deleteList.add(column);
			}
			boolean flag = false;
			if("脏器编号".equals(tc.getText()))
				flag = true;
			else
			{
				if(tableStructure.contains(tc.getText()))
				{
					flag = true;	
				}
				
			}
			if(!flag)
			{
				deleteList.add(tc);
			}
		}
		 */
		List<TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?>> deleteList =  new ArrayList<>();
		deleteList.addAll(visceraFixedCompareTable.getColumns());
		for(TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?> column:deleteList)
		{
			column.setCellValueFactory(null);
			column.setCellFactory(null);
			column.setGraphic(null);
			column.setStyle("-fx-skin:null");
			
			visceraFixedCompareTable.getColumns().remove(column);
			column = null;
		}//先解除关联。强监听。
		deleteList = null;
		visceraFixedCompareTable.setSkin(null);//解除与skin的关联
		
		//AnchorPane.bottomAnchor="72.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
		visceraFixedCompareTable = new TableView<Map<String, List<List<VisceraFixedCompare>>>>();
		visceraFixedCompareTable.setPrefHeight(336.0000999999975);
		visceraFixedCompareTable.setPrefWidth(816.0);
		
		 AnchorPane anchorpane = new AnchorPane();
	     AnchorPane.setTopAnchor(visceraFixedCompareTable, 0.0);
	     AnchorPane.setLeftAnchor(visceraFixedCompareTable, 0.0);
	     AnchorPane.setRightAnchor(visceraFixedCompareTable, 0.0);
	     AnchorPane.setBottomAnchor(visceraFixedCompareTable, 72.0);
	    // <AnchorPane.bottomAnchor="14.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="-14.0" />
	     remarks = new TextArea();
	     remarks.setEditable(false);
	     remarks.setPrefHeight(48.0);
	     remarks.setPrefWidth(830.0);
	     remarks.setWrapText(true);
	     AnchorPane.setBottomAnchor(remarks, 14.0);
	     AnchorPane.setLeftAnchor(remarks, 0.0);
	     AnchorPane.setRightAnchor(remarks, -14.0);
	     
	     anchorpane.getChildren().addAll(visceraFixedCompareTable,remarks);
	     
	     fixedCompareTab.setContent(anchorpane);
	     
		initVisceraFixedCompareTable();
	}
	public void updateFixedCompareTableStructure()
	{

		//添加不存在的行
		//从第二列开始时脏器
		for(String columnName:fixedTableStructure)
		{
			boolean flag = false;
			for(TableColumn<Map<String,List<List<VisceraFixedCompare>>>,?> tc : visceraFixedCompareTable.getColumns())
			{
				if(tc.getText().equals(columnName))
				{
					flag = true;
					break;
				}
			}
			if(!flag)
			{
				TableColumn<Map<String,List<List<VisceraFixedCompare>>>,String> fixedViscera_TC = new TableColumn<Map<String,List<List<VisceraFixedCompare>>>,String>(columnName);
				 //maxWidth="5000.0" minWidth="10.0" prefWidth="113.0" 
				fixedViscera_TC.setMinWidth(10);
				fixedViscera_TC.setPrefWidth(100.0);
				fixedViscera_TC.setSortable(false);
				
				fixedViscera_TC.setCellValueFactory(new Callback<CellDataFeatures<Map<String,List<List<VisceraFixedCompare>>>, String>, ObservableValue<String>>() {
			    public ObservableValue<String> call(CellDataFeatures<Map<String,List<List<VisceraFixedCompare>>>, String> p) {
							String key =  p.getValue().keySet().toArray()[0].toString();	
							List<List<VisceraFixedCompare>> values = p.getValue().get(key);
							
							if(key.contains("完成时间"))
							{
								for(List<VisceraFixedCompare> vfTemps:values)
								{
									for(VisceraFixedCompare vfTemp :vfTemps)
									{
										if(vfTemp.getAnimalCode().equals(p.getTableColumn().getText()))
										{
											String time = vfTemp.getOperateTime().substring(vfTemp.getOperateTime().indexOf("间")+1);
											
											return new SimpleStringProperty(time);
										}
									}
									
								}
								//return new SimpleStringProperty("完成时间列，但是没有找到"+p.getTableColumn().getText());
								return new SimpleStringProperty("");
							}
							else
							{
								List<VisceraFixedCompare> vfs = null;
								for(List<VisceraFixedCompare> vfTemps:values)
								{
									if(vfTemps.get(0).getAnimalCode().equals(p.getTableColumn().getText()))
									{
										vfs = vfTemps;
										break;
									}
									
								}
								if(vfs!=null)
								{

									String returnValue = "";
									for(VisceraFixedCompare vf:vfs)
									{
										
										if(vf.get_autolyzaFlag()!=null&&"Y".equals(vf.get_autolyzaFlag()))
										{
											 //returnValue="自溶";
											 if(vf.getSubVisceraName()!=null)
												{
													
													for(VisceraFixedCompare tempVf:vfs)
													{
														if(tempVf.get_autolyzaFlag()!=null&&"Y".equals(tempVf.get_autolyzaFlag()))
														{
//															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName())&&!tempVf.getSubVisceraName().equals(vf.getSubVisceraName()))
															if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName()))
															{
																returnValue += tempVf.getSubVisceraName()+"自溶";
															}
														}
													}
												}else
												{
													returnValue = "自溶";
												}
										}
										if(vf.get_missFlag()!=null&&("Y".equals(vf.get_missFlag())||"Y2".equals(vf.get_missFlag())))
										{
											if(vf.getSubVisceraName()!=null)
											{
//												returnValue = vf.getSubVisceraName()+vf.get_missType();
												returnValue ="";
												
												//查找是否还存在其他的丢失子脏器
												for(VisceraFixedCompare tempVf:vfs)
												{
													if(tempVf.get_missFlag()!=null&&("Y".equals(tempVf.get_missFlag())||"Y2".equals(tempVf.get_missFlag())))
													{
//														if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName())&&!tempVf.getSubVisceraName().equals(vf.getSubVisceraName()))
														if(tempVf.getAnimalCode().equals(vf.getAnimalCode())&&tempVf.getVisceraName().equals(vf.getVisceraName()))
														{
															returnValue += tempVf.getSubVisceraName()+vf.get_missType();
														}
													}
												}
												
											}else
											{
												returnValue = vf.get_missType();
											}
										}
										if(vf.get_fixedFlag()!=null&&"Y".equals(vf.get_fixedFlag()))
										{
											returnValue="√";
											break;
										}
										
									}
									return new SimpleStringProperty(returnValue);
								}
								//else return "×";
								else return new SimpleStringProperty("");
							}
							
						//}
					//};
					//return s1;
			    }
				});
				
				visceraFixedCompareTable.getColumns().add(fixedViscera_TC);
				fixedViscera_TC = null;
			}
		}
		
	}
	/**
	 * 排序固定表的列,行按固定顺序排列
	 */
	public void sortTableRowBysnFixed()
	{
		
		Collections.sort(data_visceraFixedCompareTable,new Comparator<Map<String, ?>>() {
			@Override
			public int compare(Map<String, ?> o1, Map<String, ?> o2) {
				int snFixed=0,snFixed2=0;
				for(DictViscera dv:visceraSortList)
				{
					if(dv.getVisceraName().equals(o1.keySet().toArray()[0]))
						snFixed = dv.getSnFixed();
					if(dv.getVisceraName().equals(o2.keySet().toArray()[0]))
						snFixed2 = dv.getSnFixed();
				}
				return snFixed-snFixed2;
			}
		});
		
		
			
			
		
	}
	
	
	
	@FXML
	private void onChangeAnimalDead(ActionEvent event){
		
		AnimalInfo animalInfo0 = animalTable.getSelectionModel().getSelectedItem();
		int index = data_animalTable.indexOf(animalInfo0);
		
		AnimalInfo animalInfo = new AnimalInfo(animalInfo0.getAnimalCode(),
				animalInfo0.getAntomyState(), animalInfo0.getOnatomyOperator(),
				animalInfo0.getAnatomyResult(),
				DateUtil.stringToDate(animalInfo0.getDeadDate(),"yyyy-MM-dd HH:mm"),
				DateUtil.stringToDate(animalInfo0.getAnatomyBeginTime(),"yyyy-MM-dd HH:mm"), 
				DateUtil.stringToDate(animalInfo0.getAnatomyEndTime(),"yyyy-MM-dd HH:mm"),
				DateUtil.stringToDate(animalInfo0.getVisceraWeighFinishTime(),"yyyy-MM-dd HH:mm"),
				DateUtil.stringToDate(animalInfo0.getVisceraFixedFinishTime(),"yyyy-MM-dd HH:mm"),
				animalInfo0.getAnatomyTime(),
				DateUtil.stringToDate(animalInfo0.getHistopathCheckFinishTime(),"yyyy-MM-dd HH:mm"),
				animalInfo0.getHistopathCheckOperator(),
				animalInfo0.getHistopathReviewSign());
		
		
		if(animalInfo!=null)
		{
			Messager.showChooseDate("选择动物死亡日期");
			Date deadDate = null;
			if(ChooseOneDate.getInstance().getIsOKClick())
			{
				deadDate = ChooseOneDate.getInstance().getChooseDate();
				
				Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
				if(null == deadDate || "".equals(deadDate)){
					Messager.showWarnMessage("请选择动物死亡日期！");
					return ;
				}else if(deadDate.after(date)){
					Messager.showWarnMessage("动物死亡日期不能大于当前日期！");
					return ;
				}else{
					//签字确认
					
					//签字通过
					if(Sign.openSignWithReasonFrame("修改原因", "修改动物死亡日期--身份验证")){
						//Messager.showWarnMessage("签字ok");
						String reason = Sign.getReason();
						
						String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
						//更新死亡日期
						
						Map<String, Object> result = BaseService.getInstance().getTblAnatomyAnimalService().updateAnatomyAnimalDeadDate(studyNo,animalInfo.getAnimalCode(),SystemTool.getHostName(),SaveUserInfo.getRealName(),reason,deadDate);
						if((boolean)result.get("success")){
							
							//更新死亡日期
							animalInfo.setDeadDate(new SimpleStringProperty(DateUtil.dateToString(deadDate,"yyyy-MM-dd HH:mm")));
							data_animalTable.set(index,animalInfo);
							
						}else{
							Messager.showWarnMessage((String)result.get("msg"));
						}
						
						
						
					}
				
			
				}
				
			}
			
			
		}else{
			Messager.showWarnMessage("请选择一个动物！");
		}
		
	}
	
	
	
	
	/**
	 * 初始化动物信息-解剖状态下拉框
	 */
	private void initStateSessionComboBox(){
		stateComboBox.setItems(data_stateComboBox);
		stateComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String sessionInfo=animalSessionComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) animalSessionMap.get(sessionInfo);
					updataAnimalTable(taskId,newValue,sessionId);
				}
			}
		});
	}
	/**
	 * 更新动物信息-解剖状态下拉框
	 */
	private void upadataStateComboBox(){
		data_stateComboBox.clear();
		data_stateComboBox.add("全部");
		data_stateComboBox.add("已剖");
		data_stateComboBox.add("自溶");
		data_stateComboBox.add("未剖");
		data_stateComboBox.add("未称重");
		data_stateComboBox.add("未固定");
		//data_stateComboBox.add("未镜检");
		stateComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * 初始化动物信息-会话下拉框
	 */
	private void initAnimalSessionComboBox(){
		animalSessionComboBox.setItems(data_animalSessionComboBox);
		animalSessionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//String taskInfo=taskComboBox.getSelectionModel().getSelectedItem();
					//String taskId=studyNoAnatomyNumTaskIdMap.get(taskInfo);
					List<String>  taskInfos = taskListView.getSelectionModel().getSelectedItems();
					String taskId="";
					for(String str:taskInfos)
					{
						taskId+=studyNoAnatomyNumTaskIdMap.get(str)+",";	
					}
					String animalState=stateComboBox.getSelectionModel().getSelectedItem();
					String sessionId=(String) animalSessionMap.get(newValue);
					updataAnimalTable(taskId,animalState,sessionId);
				}
			}
		});
	}
	/**
	 * 更新动物信息-会话下拉框
	 */
	private void updataAnimalSessionComboBox(String taskId,int tabIndex){
		data_animalSessionComboBox.clear();
		animalSessionMap.clear();
		List<Map<String, Object>> list=BaseService.getInstance().getTblPathSessionService().getAnatomySessionListByTaskIds(taskId,tabIndex);
		data_animalSessionComboBox.add("全部");
		if(null!=list && list.size()>0){
			for(Map<String, Object> map:list){
				String sessionId=(String)map.get("sessionId");
				int sessionType=(Integer)map.get("sessionType");
				String creator=(String)map.get("sessionCreator");
				Date createTime=(Date)map.get("createdTime");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String createTimeStr=sdf.format(createTime);
				String sessionInfo="";
				if(sessionType==1){
					sessionInfo="剖检："+creator+"："+createTimeStr;;
				}else if(sessionType==2){
					sessionInfo="称重："+creator+"："+createTimeStr;;
				}else if(sessionType==3){
					sessionInfo="剖检、称重："+creator+"："+createTimeStr;;
				}else if(sessionType==4){
					sessionInfo="固定："+creator+"："+createTimeStr;;
				}else if(sessionType==5){
					sessionInfo="剖检、固定："+creator+"："+createTimeStr;
				}else if(sessionType==6){
					sessionInfo="称重、固定：："+creator+"："+createTimeStr;;
				}else if(sessionType==7){
					sessionInfo="剖检、称重、固定："+creator+"："+createTimeStr;;
				}else if(sessionType==8){
					sessionInfo="固定后称重："+creator+"："+createTimeStr;;
				}
				data_animalSessionComboBox.add(sessionInfo);
				animalSessionMap.put(sessionInfo, sessionId);
			}
		}
		animalSessionComboBox.getSelectionModel().selectFirst();
		
	}
	/**
	 * 初始化动物信息表
	 */
	private void initAnimalTable(){
		animalTable.setItems(data_animalTable);
		info_animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("animalCode"));
		info_stateCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("antomyState"));
		info_anatomyOperatorCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("onatomyOperator"));
		info_anatomyResultCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("anatomyResult"));
		info_anatomyAnimalDeadTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("deadDate"));
		info_anatomyStartTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("anatomyBeginTime"));
		info_anatomyEndTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("anatomyEndTime"));
		info_visceraWeighFinishTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("visceraWeighFinishTime"));
		info_visceraFixedFinishTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("visceraFixedFinishTime"));
		info_anatomyTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("anatomyTime"));
		/*info_histopathCheckFinishTimeCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("histopathCheckFinishTime"));
		info_histopathCheckOperatorCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("histopathCheckOperator"));
		info_histopathReviewSignCol.setCellValueFactory(new PropertyValueFactory<AnimalInfo,String>("histopathReviewSign"));*/
	}
	/**
	 *更新动物信息表 
	 */
	private void updataAnimalTable(String taskId,String animalState,String sessionId){
		data_animalTable.clear();
		int weipo=0,zirong=0,jiepozhong=0,daichengzhong=0,chengzhongzhong=0,daiguding=0,gudingzhong=0,gudingwancheng=0,chengzhongguding=0;
		if(null==animalState || animalState.equals("全部") ){
			if(sessionId==null || sessionId.equals("全部")){
				List<Map<String, Object>> noAnatamylist=BaseService.getInstance().getTblAnatomyTaskService().getNoAnatomyAnimals(taskId);
				if(null!=noAnatamylist && noAnatamylist.size()>0){
					for(Map<String, Object> map:noAnatamylist){
						String animalCode=(String)map.get("animalCode");
						weipo+=1;
						data_animalTable.add(new AnimalInfo(animalCode,"未剖","","",null,null,null,null,null,null,null,"",""));
					}
					noAnatomyAnimalNumber=noAnatamylist.size();
				}
			}
			
			List<Map<String, Object>> havaAnatomyAnimalLsit=BaseService.getInstance().getTblAnatomyAnimalService().getListByStateAndSessionIds(taskId,animalState,sessionId);
			if(null!=havaAnatomyAnimalLsit && havaAnatomyAnimalLsit.size()>0){
				for(Map<String, Object> map:havaAnatomyAnimalLsit){
					String animalCode=(String)map.get("animalCode");
					int autolyzeFlag=(Integer)map.get("autolyzeFlag");
					if(autolyzeFlag==1){
						zirong+=1;
						data_animalTable.add(new AnimalInfo(animalCode,"自溶","","",null,null,null,null,null,null,null,"",""));
					}else{
						//String anatomySessionId=(String)map.get("anatomySessionId");
						String visceraWeightSessionId=(String)map.get("visceraWeightSessionId");
						String visceraFixedSessionId=(String)map.get("visceraFixedSessionId");
						Integer anatomyCheckFinishFlag=(Integer)map.get("anatomyCheckFinishFlag");
						Integer visceraWeighFinishFlag=(Integer)map.get("visceraWeighFinishFlag");
						Integer visceraFixedFinishFlag=(Integer)map.get("visceraFixedFinishFlag");
						String state="";
						if(anatomyCheckFinishFlag!=1 ){
							jiepozhong+=1;
							state="解剖中";
						}else{
							if( visceraWeightSessionId==null){
								daichengzhong+=1;
								state="待称重";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1){
								chengzhongzhong+=1;
								state="称重中";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId==null){
								daiguding+=1;
								state="待固定";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								gudingzhong+=1;
								state="固定中";
							}else  if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag==1){
								gudingwancheng+=1;
								state="固定完成";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								chengzhongguding+=1;
								state="称重、固定中";
							}
						}
//						SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
						Date anatomyEndTime=(Date)map.get("anatomyEndTime");
						String anatomyResult=(String)map.get("anatomyResult");
						Date deadDate = (Date)map.get("deadDate");
						Date visceraWeighFinishTime=(Date)map.get("visceraWeighFinishTime");
						Date visceraFixedFinishTime=(Date)map.get("visceraFixedFinishTime");
						Date anatomyBeginTime=(Date)map.get("anatomyBeginTime");
						String anatomyTime="";
						if(null!=visceraFixedFinishTime){
							
							Long temp=visceraFixedFinishTime.getTime()/(1000*60)-anatomyBeginTime.getTime()/(1000*60);
							anatomyTime=temp+"分钟";
							if(temp==0)
								anatomyTime="<1分钟";
							/*long hours = temp / 1000 / 3600;                //相差小时数
					        long temp2 = temp % (1000 * 3600);
					        long mins = temp2 / 1000 / 60;    
					        if(hours>0)
					        	anatomyTime=hours+"小时";
					        if(mins>0)
					        	anatomyTime=anatomyTime+mins+"分钟";*/
						}
				        String anatomyOperator=(String)map.get("anatomyOperator");
				        data_animalTable.add(new AnimalInfo(animalCode,state,anatomyOperator,anatomyResult,deadDate,anatomyBeginTime,anatomyEndTime,visceraWeighFinishTime,visceraFixedFinishTime,anatomyTime,null,"",""));
					}
				}
			}
		}else if(animalState.equals("未剖")){
			if(sessionId==null || sessionId.equals("全部")){
				List<Map<String, Object>> noAnatamylist=BaseService.getInstance().getTblAnatomyTaskService().getNoAnatomyAnimals(taskId);
				if(null!=noAnatamylist && noAnatamylist.size()>0){
					for(Map<String, Object> map:noAnatamylist){
						String animalCode=(String)map.get("animalCode");
						weipo+=1;
						data_animalTable.add(new AnimalInfo(animalCode,"未剖","","",null,null,null,null,null,null,null,"",""));
					}
				}
			}
		}else if(animalState.equals("已剖")){
			List<Map<String, Object>> havaAnatomyAnimalLsit=BaseService.getInstance().getTblAnatomyAnimalService().getListByStateAndSessionIds(taskId,animalState,sessionId);
			if(null!=havaAnatomyAnimalLsit && havaAnatomyAnimalLsit.size()>0){
				for(Map<String, Object> map:havaAnatomyAnimalLsit){
					String animalCode=(String)map.get("animalCode");
					int autolyzeFlag=(Integer)map.get("autolyzeFlag");
					if(autolyzeFlag==1){
						data_animalTable.add(new AnimalInfo(animalCode,"自溶","","",null,null,null,null,null,null,null,"",""));
					}else{
						//String anatomySessionId=(String)map.get("anatomySessionId");
						String visceraWeightSessionId=(String)map.get("visceraWeightSessionId");
						String visceraFixedSessionId=(String)map.get("visceraFixedSessionId");
						Integer anatomyCheckFinishFlag=(Integer)map.get("anatomyCheckFinishFlag");
						Integer visceraWeighFinishFlag=(Integer)map.get("visceraWeighFinishFlag");
						Integer visceraFixedFinishFlag=(Integer)map.get("visceraFixedFinishFlag");
						String state="";
						if(anatomyCheckFinishFlag!=1 ){
							jiepozhong+=1;
							state="解剖中";
						}else{
							if( visceraWeightSessionId==null){
								daichengzhong+=1;
								state="待称重";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1){
								chengzhongzhong+=1;
								state="称重中";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId==null){
								daiguding+=1;
								state="待固定";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								gudingzhong+=1;
								state="固定中";
							}else  if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag==1){
								gudingwancheng+=1;
								state="固定完成";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								chengzhongguding+=1;
								state="称重、固定中";
							}
						}
//						SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
						Date anatomyEndTime=(Date)map.get("anatomyEndTime");
						String anatomyResult=(String)map.get("anatomyResult");
						Date deadDate = (Date)map.get("deadDate");
						Date visceraWeighFinishTime=(Date)map.get("visceraWeighFinishTime");
						Date visceraFixedFinishTime=(Date)map.get("visceraFixedFinishTime");
						Date anatomyBeginTime=(Date)map.get("anatomyBeginTime");
						String anatomyTime="";
						if(null!=visceraFixedFinishTime){
							Long temp=visceraFixedFinishTime.getTime()/(1000*60)-anatomyBeginTime.getTime()/(1000*60);
							anatomyTime=temp+"分钟";
							if(temp==0)
								anatomyTime="<1分钟";
							/*Long temp=visceraFixedFinishTime.getTime()-anatomyBeginTime.getTime();
							long hours = temp / 1000 / 3600;                //相差小时数
					        long temp2 = temp % (1000 * 3600);
					        long mins = temp2 / 1000 / 60;   
					        if(hours>0)
					        	anatomyTime=hours+"小时";
					        if(mins>0)
					        	anatomyTime=anatomyTime+mins+"分钟";
					          //  anatomyTime=hours+"小时"+mins+"分钟";
*/						}
				        String anatomyOperator=(String)map.get("anatomyOperator");
				        data_animalTable.add(new AnimalInfo(animalCode,state,anatomyOperator,anatomyResult,deadDate,anatomyBeginTime,anatomyEndTime,visceraWeighFinishTime,visceraFixedFinishTime,anatomyTime,null,"",""));
					}
				}
			}
		}else if( animalState.equals("未称重"))
		{
			List<Map<String, Object>> havaAnatomyAnimalLsit=BaseService.getInstance().getTblAnatomyAnimalService().getListByStateAndSessionIds(taskId,"已剖",sessionId);
			if(null!=havaAnatomyAnimalLsit && havaAnatomyAnimalLsit.size()>0){
				for(Map<String, Object> map:havaAnatomyAnimalLsit){
					String animalCode=(String)map.get("animalCode");
					int autolyzeFlag=(Integer)map.get("autolyzeFlag");
					
					if(autolyzeFlag==1){
						data_animalTable.add(new AnimalInfo(animalCode,"自溶","","",null,null,null,null,null,null,null,"",""));
					}else{
						//String anatomySessionId=(String)map.get("anatomySessionId");
						String visceraWeightSessionId=(String)map.get("visceraWeightSessionId");
						String visceraFixedSessionId=(String)map.get("visceraFixedSessionId");
						Integer anatomyCheckFinishFlag=(Integer)map.get("anatomyCheckFinishFlag");
						Integer visceraWeighFinishFlag=(Integer)map.get("visceraWeighFinishFlag");
						Integer visceraFixedFinishFlag=(Integer)map.get("visceraFixedFinishFlag");
						String state="";
						if(anatomyCheckFinishFlag!=1 ){
							
							state="解剖中";
						}else{
							if( visceraWeightSessionId==null){
								daichengzhong+=1;
								state="待称重";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1){
								chengzhongzhong+=1;
								state="称重中";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId==null){
								
								state="待固定";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								
								state="固定中";
							}else  if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag==1){
							
								state="固定完成";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								chengzhongguding+=1;
								state="称重、固定中";
							}
						}
//						SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
						Date anatomyEndTime=(Date)map.get("anatomyEndTime");
						String anatomyResult=(String)map.get("anatomyResult");
						Date deadDate = (Date)map.get("deadDate");
						Date visceraWeighFinishTime=(Date)map.get("visceraWeighFinishTime");
						Date visceraFixedFinishTime=(Date)map.get("visceraFixedFinishTime");
						Date anatomyBeginTime=(Date)map.get("anatomyBeginTime");
						String anatomyTime="";
						if(null!=visceraFixedFinishTime){
							Long temp=visceraFixedFinishTime.getTime()/(1000*60)-anatomyBeginTime.getTime()/(1000*60);
							anatomyTime=temp+"分钟";
							if(temp==0)
								anatomyTime="<1分钟";
				/*			Long temp=visceraFixedFinishTime.getTime()-anatomyBeginTime.getTime();
							long hours = temp / 1000 / 3600;                //相差小时数
					        long temp2 = temp % (1000 * 3600);
					        long mins = temp2 / 1000 / 60;   
					        if(hours>0)
					        	anatomyTime=hours+"小时";
					        if(mins>0)
					        	anatomyTime=anatomyTime+mins+"分钟";
					          //  anatomyTime=hours+"小时"+mins+"分钟";
*/						}
				        String anatomyOperator=(String)map.get("anatomyOperator");
				        if(state.equals("待称重")||state.equals("称重中")||state.equals("称重、固定中"))
				        {
				        	data_animalTable.add(new AnimalInfo(animalCode,state,anatomyOperator,anatomyResult,deadDate,anatomyBeginTime,anatomyEndTime,visceraWeighFinishTime,visceraFixedFinishTime,anatomyTime,null,"",""));
					
				        }
					}
				}
			}
		}else if(animalState.equals("未固定"))
		{
			List<Map<String, Object>> havaAnatomyAnimalLsit=BaseService.getInstance().getTblAnatomyAnimalService().getListByStateAndSessionIds(taskId,"已剖",sessionId);
			if(null!=havaAnatomyAnimalLsit && havaAnatomyAnimalLsit.size()>0){
				for(Map<String, Object> map:havaAnatomyAnimalLsit){
					String animalCode=(String)map.get("animalCode");
					int autolyzeFlag=(Integer)map.get("autolyzeFlag");
					if(autolyzeFlag==1){
						data_animalTable.add(new AnimalInfo(animalCode,"自溶","","",null,null,null,null,null,null,null,"",""));
					}else{
						//String anatomySessionId=(String)map.get("anatomySessionId");
						String visceraWeightSessionId=(String)map.get("visceraWeightSessionId");
						String visceraFixedSessionId=(String)map.get("visceraFixedSessionId");
						Integer anatomyCheckFinishFlag=(Integer)map.get("anatomyCheckFinishFlag");
						Integer visceraWeighFinishFlag=(Integer)map.get("visceraWeighFinishFlag");
						Integer visceraFixedFinishFlag=(Integer)map.get("visceraFixedFinishFlag");
						String state="";
						if(anatomyCheckFinishFlag!=1 ){
							state="解剖中";
						}else{
							if( visceraWeightSessionId==null){
								
								state="待称重";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1){
								
								state="称重中";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId==null){
								daiguding+=1;
								state="待固定";
							}else if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								gudingzhong+=1;
								state="固定中";
							}else  if(visceraWeightSessionId!=null &&visceraWeighFinishFlag==1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag==1){
								
								state="固定完成";
							}else if(visceraWeightSessionId!=null && visceraWeighFinishFlag!=1 &&visceraFixedSessionId!=null && visceraFixedFinishFlag!=1){
								chengzhongguding+=1;
								state="称重、固定中";
							}
						}
//						SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
						Date anatomyEndTime=(Date)map.get("anatomyEndTime");
						String anatomyResult=(String)map.get("anatomyResult");
						Date deadDate = (Date)map.get("deadDate");
						Date visceraWeighFinishTime=(Date)map.get("visceraWeighFinishTime");
						Date visceraFixedFinishTime=(Date)map.get("visceraFixedFinishTime");
						Date anatomyBeginTime=(Date)map.get("anatomyBeginTime");
						String anatomyTime="";
						if(null!=visceraFixedFinishTime){
							Long temp=visceraFixedFinishTime.getTime()/(1000*60)-anatomyBeginTime.getTime()/(1000*60);
							anatomyTime=temp+"分钟";
							if(temp==0)
								anatomyTime="<1分钟";
							/*Long temp=visceraFixedFinishTime.getTime()-anatomyBeginTime.getTime();
							long hours = temp / 1000 / 3600;                //相差小时数
					        long temp2 = temp % (1000 * 3600);
					        long mins = temp2 / 1000 / 60;   
					        if(hours>0)
					        	anatomyTime=hours+"小时";
					        if(mins>0)
					        	anatomyTime=anatomyTime+mins+"分钟";
					          //  anatomyTime=hours+"小时"+mins+"分钟";
*/						}
				        String anatomyOperator=(String)map.get("anatomyOperator");
				        if(state.equals("待固定")||state.equals("固定中")||state.equals("称重、固定中"))
				        {
				        	data_animalTable.add(new AnimalInfo(animalCode,state,anatomyOperator,anatomyResult,deadDate,anatomyBeginTime,anatomyEndTime,visceraWeighFinishTime,visceraFixedFinishTime,anatomyTime,null,"",""));
				        }
				    }
				}
			}
		}
		
		if((null==animalState|| animalState.equals("全部"))&& (sessionId==null || sessionId.equals("全部"))){
			animal_total=data_animalTable.size();
		}
		Collections.sort(data_animalTable,new Comparator<AnimalInfo>() {

			@Override
			public int compare(AnimalInfo o1, AnimalInfo o2) {
				return o1.getAnimalCode().compareTo(o2.getAnimalCode());
			}
		});
		String labelString = "";
		if(weipo!=0)
			labelString+="未解剖动物："+weipo+"只  ";
		if(zirong!=0)
			labelString+="自溶动物："+zirong+"只  ";
		if(jiepozhong!=0)
			labelString+="解剖中动物："+jiepozhong+"只  ";
		if(daichengzhong!=0)
			labelString+="待称重动物： "+daichengzhong+"只  ";
		if(chengzhongzhong!=0)
			labelString+="称重中动物："+chengzhongzhong+"只  ";
		if(daiguding!=0)
			labelString+="待固定动物："+daiguding+"只  " ;
		if(gudingzhong!=0)
			labelString+="固定中动物："+gudingzhong+"只  ";
		if(gudingwancheng!=0)
			labelString+="固定完成动物："+gudingwancheng+"只  ";
		if(chengzhongguding!=0)
			labelString+="称重固定中："+chengzhongguding+"只  ";
		labelString+="记录数量："+data_animalTable.size()+"";
		
		animalInfoLabel.setText(labelString);
	}
	public static void main(String[] args) {
		
		launch(args);
    }
    /**
     * 
     */
    @FXML
	private void onAnatomyCheckPrintButton(){
		
	}
    public static class AnimalInfo{
    	private StringProperty animalCode;			//动物编号
    	private StringProperty antomyState;         //解剖状态
    	private StringProperty onatomyOperator;     //解剖者
    	private StringProperty anatomyResult;       //剖检结果
    	private StringProperty deadDate;			//死亡日期
    	private StringProperty anatomyBeginTime;	//解剖开始时间
    	private StringProperty anatomyEndTime;      //解剖结束时间
    	private StringProperty visceraWeighFinishTime;//称重完成时间
    	private StringProperty visceraFixedFinishTime;//固定完成时间
    	private StringProperty anatomyTime;         //解剖耗时
    	private StringProperty histopathCheckFinishTime;//镜检完成时间
    	private StringProperty histopathCheckOperator;  //镜检者
    	private StringProperty histopathReviewSign;     //复核者
		public AnimalInfo(String animalCode,
				String antomyState, String onatomyOperator,
				String anatomyResult,Date deadDate,Date anatomyBeginTime, Date anatomyEndTime,
				Date visceraWeighFinishTime,
				Date visceraFixedFinishTime,
				String anatomyTime,
				Date histopathCheckFinishTime,
				String histopathCheckOperator,
				String histopathReviewSign) {
			super();
			this.animalCode = new SimpleStringProperty(animalCode);;
			this.antomyState = new SimpleStringProperty(antomyState);
			this.onatomyOperator = new SimpleStringProperty(onatomyOperator);
			this.anatomyResult = new SimpleStringProperty(anatomyResult);
			this.deadDate = new SimpleStringProperty(DateUtil.dateToString(deadDate,"yyyy-MM-dd HH:mm"));
			this.anatomyBeginTime = new SimpleStringProperty(DateUtil.dateToString(anatomyBeginTime,"yyyy-MM-dd HH:mm"));
			this.anatomyEndTime = new SimpleStringProperty(DateUtil.dateToString(anatomyEndTime, "yyyy-MM-dd HH:mm"));
			this.visceraWeighFinishTime = new SimpleStringProperty(DateUtil.dateToString(visceraWeighFinishTime, "yyyy-MM-dd HH:mm"));
			this.visceraFixedFinishTime = new SimpleStringProperty(DateUtil.dateToString(visceraFixedFinishTime, "yyyy-MM-dd HH:mm"));
			this.anatomyTime = new SimpleStringProperty(anatomyTime);
			this.histopathCheckFinishTime =  new SimpleStringProperty(DateUtil.dateToString(histopathCheckFinishTime, "yyyy-MM-dd HH:mm"));
			this.histopathCheckOperator = new SimpleStringProperty(histopathCheckOperator);
			this.histopathReviewSign = new SimpleStringProperty(histopathReviewSign);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getAntomyState() {
			return antomyState.get();
		}
		public void setAntomyState(String antomyState) {
			this.antomyState = new SimpleStringProperty(antomyState);
		}
		public String getOnatomyOperator() {
			return onatomyOperator.get();
		}
		public void setOnatomyOperator(String onatomyOperator) {
			this.onatomyOperator = new SimpleStringProperty(onatomyOperator);
		}
		public String getDeadDate() {
			return deadDate.get();
		}
		public void setDeadDate(StringProperty deadDate) {
			this.deadDate = deadDate;
		}
		public String getAnatomyResult() {
			return anatomyResult.get();
		}
		public void setAnatomyResult(String anatomyResult) {
			this.anatomyResult = new SimpleStringProperty(anatomyResult);
		}
		public String getAnatomyBeginTime() {
			return anatomyBeginTime.get();
		}
		public void setAnatomyBeginTime(StringProperty anatomyBeginTime) {
			this.anatomyBeginTime = anatomyBeginTime;
		}
		public String getAnatomyEndTime() {
			return anatomyEndTime.get();
		}
		public void setAnatomyEndTime(String anatomyEndTime) {
			this.anatomyEndTime = new SimpleStringProperty(anatomyEndTime);
		}
		public String getVisceraWeighFinishTime() {
			return visceraWeighFinishTime.get();
		}
		public void setVisceraWeighFinishTime(String visceraWeighFinishTime) {
			this.visceraWeighFinishTime = new SimpleStringProperty(visceraWeighFinishTime);
		}
		public String getVisceraFixedFinishTime() {
			return visceraFixedFinishTime.get();
		}
		public void setVisceraFixedFinishTime(String visceraFixedFinishTime) {
			this.visceraFixedFinishTime = new SimpleStringProperty(visceraFixedFinishTime);
		}
		public String getAnatomyTime() {
			return anatomyTime.get();
		}
		public void setAnatomyTime(String anatomyTime) {
			this.anatomyTime = new SimpleStringProperty(anatomyTime);
		}
		public String getHistopathCheckFinishTime() {
			return histopathCheckFinishTime.get();
		}
		public void setHistopathCheckFinishTime(String histopathCheckFinishTime) {
			this.histopathCheckFinishTime = new SimpleStringProperty(histopathCheckFinishTime);
		}
		public String getHistopathCheckOperator() {
			return histopathCheckOperator.get();
		}
		public void setHistopathCheckOperator(String histopathCheckOperator) {
			this.histopathCheckOperator = new SimpleStringProperty(histopathCheckOperator);
		}
		public String getHistopathReviewSign() {
			return histopathReviewSign.get();
		}
		public void setHistopathReviewSign(String histopathReviewSign) {
			this.histopathReviewSign = new SimpleStringProperty(histopathReviewSign);
		}
    	
    	
    }
	public TabPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(TabPane tabPane) {
		this.tabPane = tabPane;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
