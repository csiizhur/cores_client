package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblAnatomyCheckHis;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.path.TblPathSession;
import com.lanen.model.path.VisceraFixDataForReport;
import com.lanen.model.path.VisceraWeightDataForReport;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.MainPageController.AnatomyTask;
import com.lanen.view.main.SignFrame;
import com.lanen.view.sign.Sign;

/**数据确认
 * @author Administrator
 *
 */
public class DataValidation  extends Application implements Initializable  {

	private Stage stage;

	private static DataValidation instance;
	
	@FXML
	private TabPane tabPane; // 标签面板TabPane
	
	@FXML
	private Tab anatomyTab; // 动物解剖Tab
	
	@FXML
	private Tab weightTab; // 脏器称重Tab
	
	@FXML
	private Tab fixTab; // 脏器固定Tab
	
	@FXML
	private Label anatomyAnimals;//解剖所见
	
	
	@FXML
	private TableView<AnatomyTask> anatomyTaskTable;		//任务表
	private ObservableList<AnatomyTask> data_anatomyTaskTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<AnatomyTask,String> studyNoCol;        //专题编号
	@FXML
	private TableColumn<AnatomyTask,String> anatomyNumCol;      //解剖任务序号
	@FXML
	private TableColumn<AnatomyTask,String> taskCreateDateCol;        //任务创建日期
	@FXML
	private TableColumn<AnatomyTask,String> taskCreaterCol;      //任务创建者
	@FXML
	private TableColumn<AnatomyTask,String> animalTypeCol;      //动物种类
	@FXML
	private TableColumn<AnatomyTask,String> anatomyRsnCol;      //解剖原因
	@FXML
	private TableColumn<AnatomyTask,String> finishCol;      //是否签字确认
	
	
	@FXML
	private Button addButton; //添加按钮
	
	@FXML
	private Button editButton; //编辑按钮
	
	@FXML
	private Button deleteButton; //删除按钮
	
	@FXML
	private Button anatomyPrintButton;//打印按键
	
	
	@FXML
	private Button weighTeditButton; //编辑按钮
	
	
	@FXML
	private Button confirmButton; //签字确认按钮
	
	@FXML
	private TableView<VisceraFixed> visceraFixedTable; //脏器固定记录展示表
	private ObservableList<VisceraFixed> data_visceraFixedTable=FXCollections.observableArrayList(); 
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
	private Label fixedLabel;           //脏器固定-统计标签
	
	
	@FXML
	private TableView<AnatomyCheck> anatomyCheckTable; //解剖所见
	private ObservableList<AnatomyCheck> data_anatomyCheckTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<AnatomyCheck,String> idCol;        //主键
	
	@FXML
	private TableColumn<AnatomyCheck,String> astudyNoCol;        //专题编号
	
	@FXML
	private TableColumn<AnatomyCheck,String> animalCodeCol;//动物编号

	@FXML
	private TableColumn<AnatomyCheck,String> visceraNameCol;//脏器名称
	
	@FXML
	private TableColumn<AnatomyCheck,String> anatomyFindingCol;//解剖所见
	
	@FXML
	private TableColumn<AnatomyCheck,String> anatomyOperatorCol;//解剖者
	
	@FXML
	private TableColumn<AnatomyCheck,String> operateTimeCol;//操作时间
	
	@FXML
	private Label visceraWeightLabel;//解剖所见
	
	@FXML
	private TableView<visceraWeight> visceraWeightTable; //脏器称重
	private ObservableList<visceraWeight> data_visceraWeightTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<visceraWeight,String> widCol;//主键
	
	@FXML
	private TableColumn<visceraWeight,String> wstudyNoCol;//专题编号
	
	@FXML
	private TableColumn<visceraWeight,String>  wanimalCodeCol;//动物编号
	
	@FXML
	private TableColumn<visceraWeight,String>  wvisceraNameCol;//脏器名称
	
	@FXML
	private TableColumn<visceraWeight,String>  weightCol;//体重

//	@FXML
//	private TableColumn<visceraWeight,String>  weightUnitCol;//体重单位
	
	@FXML
	private TableColumn<visceraWeight,String>  fixedWeighFlagCol;//是否固定后称重   0：否；1：是
	
	@FXML
	private TableColumn<visceraWeight,String>  attachedVisceraNamesCol;//附加脏器列表 
	
	@FXML
	private TableColumn<visceraWeight,String> woperatorCol;//操作者
	
	@FXML
	private TableColumn<visceraWeight,String>  woperateTimeCol;//操作时间
	
	@FXML
	private TableColumn<visceraWeight,String>  balCodeCol;//采集设备编号
	
//	@FXML   
//	private ComboBox<String> studyNoComboBox;//专题编号
//
//    private ObservableList<String> studyNodata = FXCollections.observableArrayList();
	
	@FXML   
	private ComboBox<String> animalCodeComboBox;//动物编号

    private ObservableList<String> animalCodedata = FXCollections.observableArrayList();
    
    @FXML   
	private ComboBox<String>  visceraNameComboBox;//脏器

    private ObservableList<String>   visceraNamedata = FXCollections.observableArrayList();
 
//    @FXML   
//	private ComboBox<String> animalCodePrintComboBox;//打印解剖所见用的动物下拉选
//    private ObservableList<String> animalCodePrintdata = FXCollections.observableArrayList();
    
	//任务id列表
	private static List<String> taskIdList = null;
	
	//会话id列表
	private static List<String> sessionIdList = null;
	
	//存放任务Id与sessionId的对应关系（在该页面，是一对一的关系）
    private static Map<String,String> taskIdSessionIdMap = null;
	
	public static DataValidation getInstance(){
		if(null == instance){
			instance = new DataValidation();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initVisceraFixedTable();
		initTaskTable();
		inittAnatomyCheckTable();
		initVisceraWeightTable();
		initComboBoxAnimalCode();
		initComboBoxVisceraName();
	}
	
	/**
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
	}
	

	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(Integer sessionType,List<String> taskIdList,List<String> sessionIdList) {
		DataValidation.taskIdList = taskIdList;
		DataValidation.sessionIdList = sessionIdList;
		//设置 taskIdSessionIdMap的值
		setTaskIdSessionIdMap(sessionIdList);
		updateTable(taskIdList);
	
		//调整tab的显示
		updatePaneTab(sessionType);
		anatomyTaskTable.getSelectionModel().select(0);
//		String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
//		String sessiond = taskIdSessionIdMap.get(taskId);
//		updateAnatomyCheckTable(sessiond);//更新解剖所见列表
//		updateVisceraWeight(sessiond);
//		updateFixedTab(sessiond);
		initAnatomyCheckString(taskIdList);	//加载下标文字
		int sessionFinishSignTime=0;
		List<String> needConfirmSessionList = new ArrayList<String>();
		for(String sessionId:sessionIdList){
		    //可以确认数据，将检查会话是否已确认
		    TblPathSession t=BaseService.getInstance().getTblPathSessionService().getById(sessionId);
		    if(null != t.getSessionFinishSign()){
		    	sessionFinishSignTime++;
		    }else{
		    	needConfirmSessionList.add(sessionId);
		    }
		}
		if(sessionFinishSignTime==sessionIdList.size()){
			confirmButton.setDisable(true);
			addButton.setDisable(true);
		}else{
			confirmButton.setDisable(false);
			addButton.setDisable(false);
		}
	
	}
	

	/**
	 * 设置 taskIdSessionIdMap的值
	 */
	private void setTaskIdSessionIdMap(List<String> sessionIdList) {
		taskIdSessionIdMap= BaseService.getInstance()
				.getTblPathSessionService().getTaskIdSessionIdMapBySessionIdList(sessionIdList);
		
	}
	
	
	/**
	 * 初始化解剖所见的动物信息
	 * @param taskIdList
	 */
	private void initAnatomyCheckString(List<String> taskIdList){
	
		int animalNums = BaseService.getInstance().getTblAnatomyReqAnimalListService().getAnimalNumberByTaskIdList(taskIdList);
		int AnatomyCheckSum = BaseService.getInstance().getTblAnatomyCheckService().getAnatomyCheckSumBySessionID(sessionIdList);
		String str = "动物数量："+animalNums+"只    其中有异常动物："+AnatomyCheckSum+" 只    未见异常动物："+(animalNums - AnatomyCheckSum )+" 只";
		anatomyAnimals.setText(str);
		int needFixedVisceraNumber=0;
		if(null!=taskIdList && taskIdList.size()>0){
			needFixedVisceraNumber=BaseService.getInstance().getTblAnatomyTaskService().getReqFixedVisceraNumberByTaskIdList(taskIdList);
		}
		int fixedAnimalNumber=0;
		if(null!=sessionIdList && sessionIdList.size()>0){
			fixedAnimalNumber=BaseService.getInstance().getTblVisceraFixedService().getAnimalCountBySessionIds(sessionIdList);
		}
		int autolyzeAnimal = BaseService.getInstance().getTblAnatomyAnimalService().getSumAutolyzeFlagAnimalCount(sessionIdList);
		int miss = BaseService.getInstance().getTblVisceraMissingService().getVisceraMissingCountBySessionIdList(sessionIdList);
		autolyzeAnimal = autolyzeAnimal+miss;
		String fixedStr="动物数量："+animalNums+"只"+"    计划要求固定脏器："+needFixedVisceraNumber+"个"+"    按计划已固定的动物："+fixedAnimalNumber+"只"+"    缺失脏器的动物："+autolyzeAnimal+"只" ;
		fixedLabel.setText(fixedStr);
		int sumAnimalWeight = BaseService.getInstance().getTblVisceraWeightService().countAnimalWeightBySessionID(sessionIdList);
		int sumVisceraWeigh = BaseService.getInstance().getTblAnatomyTaskService().countAnimalWeightByTaskIdList(taskIdList);
		String visceraStr="动物数量："+animalNums+"只"+"      计划要求称重的脏器："+  sumVisceraWeigh +" 个    按计划完成称重的动物："+sumAnimalWeight+"只"+"    缺失脏器的动物："+autolyzeAnimal+"只";
		visceraWeightLabel.setText(visceraStr);
	}

	
	/**
	 * 初始化解剖所见表格
	 */
	private void inittAnatomyCheckTable(){
		anatomyCheckTable.setItems(data_anatomyCheckTable);
		anatomyCheckTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		idCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("id"));//主键
		astudyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("astudyNo"));//专题编号
	    animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("animalCode"));//动物编号
        visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("visceraName"));;//脏器名称
		anatomyFindingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("anatomyFinding"));;//解剖所见
		anatomyOperatorCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("anatomyOperator"));;//解剖者
		operateTimeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("operateTime"));;//操作时间
		anatomyCheckTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<AnatomyCheck>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyCheck> arg0, AnatomyCheck arg1,
					AnatomyCheck newValue) {
							if(null != newValue){
								String id=newValue.getId();
								TblAnatomyCheck tblAnatomyCheck=BaseService.getInstance().getTblAnatomyCheckService().getById(id);
								if(tblAnatomyCheck.getAutolyzaFlag()!=1){
									editButton.setDisable(false);
									deleteButton.setDisable(false);
								}else{
									editButton.setDisable(true);
									deleteButton.setDisable(true);
								}
//								anatomyPrintButton.setDisable(false);
//								anatomyCheckTable.getSelectionModel().clearSelection();
								visceraWeightTable.getSelectionModel().clearSelection();
								visceraFixedTable.getSelectionModel().clearSelection();
//								String  animalCode = newValue.getAnimalCode();
//								animalCodeComboBox.getSelectionModel().select(animalCode);
							}else{
								editButton.setDisable(true);
								deleteButton.setDisable(true);
//								anatomyPrintButton.setDisable(true);
							}
							String finish = anatomyTaskTable.getSelectionModel().getSelectedItem().getFinish();
	            			if(finish.equals("1")){
	            				addButton.setDisable(true);
	            				editButton.setDisable(true);
	            				deleteButton.setDisable(true);
	            				weighTeditButton.setDisable(true);
	            			}else{
	            				addButton.setDisable(false);
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
	    		 return cell;
	    	 }
	     });
		
	}
	//称重
	private  void initVisceraWeightTable(){
		visceraWeightTable.setItems(data_visceraWeightTable);
		visceraWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		widCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wid"));//主键
		wstudyNoCol.setCellValueFactory(new PropertyValueFactory<visceraWeight,String>("wstudyNo"));//专题编号
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
		weightCol.setCellFactory(new Callback<TableColumn<visceraWeight,String>,TableCell<visceraWeight,String>>(){
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
				cell.setStyle("-fx-alignment: CENTER_RIGHT;");
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
	    		 return cell;
	    	 }
	     });
		visceraWeightTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<visceraWeight>(){

			@Override
			public void changed(ObservableValue<? extends visceraWeight> arg0, visceraWeight arg1,
					visceraWeight newValue) {
							if(null != newValue){
								weighTeditButton.setDisable(false);
								anatomyCheckTable.getSelectionModel().clearSelection();
//								visceraWeightTable.getSelectionModel().clearSelection();
								visceraFixedTable.getSelectionModel().clearSelection();
							}else{
								weighTeditButton.setDisable(true);
							}
							String finish = anatomyTaskTable.getSelectionModel().getSelectedItem().getFinish();
	            			if(finish.equals("1")){
	            				addButton.setDisable(true);
	            				editButton.setDisable(true);
	            				deleteButton.setDisable(true);
	            				weighTeditButton.setDisable(true);
	            			}
				}

		});
	}
	
	
	/**
	 * 初始化动物编号
	 */
	private void initComboBoxAnimalCode(){
		animalCodeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
					String sessiond = taskIdSessionIdMap.get(taskId);
					updateAnatomyCheckTable(sessiond);//更新解剖所见列表
					updateVisceraWeight(sessiond);
					updateFixedTab(sessiond);
//					anatomyTaskTable.getSelectionModel().clearSelection();
				}
			}
		});
	}
	public void updateAnimalCodeCombobox(){
		List<String> list=new ArrayList<String>();
        //System.out.println("-------"+taskIdList);
//        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyReqAnimalListService().getAnimalCodeByTaskIdList(taskIdList);
        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyAnimalService().getAnimalCodeBySessionIdList(sessionIdList,null);
    	if(null != mapList && mapList.size()>0){
    		list.add("全部");
			for(Map<String,Object> map:mapList){
				String animalCode = (String) map.get("animalCode");//
				if(!list.contains(animalCode)){
    				list.add(animalCode);
				}else{
					continue;
				}
				
		    }
    	}
        animalCodedata.clear();
		if(null!=list && list.size()>0){
			for(String dataBit : list){
				animalCodedata.add(dataBit);
			}
		}
		
		animalCodeComboBox.setItems(animalCodedata);
		
	}
//	/**
//	 * 初始化动物编号
//	 */
//	private void initComboBoxAnimalCodePrint(){
//		animalCodePrintComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
//			@Override
//			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
//				if(null != newValue){
//					boolean isHave = false;
//					 if(null != anatomyCheckTable.getSelectionModel().getSelectedItem()){
//						 String animalCode = anatomyCheckTable.getSelectionModel().getSelectedItem().getAnimalCode();
//						 for(int i = 0;i< data_anatomyCheckTable.size();i++){
//							 if(!animalCode.equals(newValue)){
//								 if(data_anatomyCheckTable.get(i).getAnimalCode().equals(newValue)){
//									 anatomyCheckTable.getSelectionModel().select(i);
//									 isHave = true;
//									 break;
//								 }
//							 }
//							 
//						 }
//						 if(!isHave && !animalCode.equals(newValue) ){
//							 anatomyCheckTable.getSelectionModel().clearSelection();
//						 }
//					 }
//				}
//			}
//		});	
//	}
	
	
//	/**
//	 * 
//	 */
//	private void updateComboBoxAnimalCodePrint(){
//
//    	//初始化service
//        new BaseService.getInstance()();
//        List<String> list=new ArrayList<String>();
////        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyReqAnimalListService().getAnimalCodeByTaskIdList(taskIdList);
//        
////        String studyNoSelected = studyNoComboBox.getSelectionModel().getSelectedItem();
//        String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
//		String sessiond = taskIdSessionIdMap.get(taskId);
//		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
//		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
//		List<String> slist = new ArrayList<String>();
//		slist.add(sessiond);
//		List<Map<String,Object>> taskMapList = 
//				BaseService.getInstance().getTblAnatomyCheckService().getAnatomyCheckBySessionIDs(slist,null,animalCodeSelected,visceraNameSelected);
//		
//    	if(null != taskMapList && taskMapList.size()>0){
//			for(Map<String,Object> map:taskMapList){
//				String animalCode = (String) map.get("animalCode");//
//				if(!list.contains(animalCode)){
//    				list.add(animalCode);
//				}else{
//					continue;
//				}
//				
//		    }
//    	}
//    	animalCodePrintdata.clear();
//		if(null!=list && list.size()>0){
//			for(String dataBit : list){
//				animalCodePrintdata.add(dataBit);
//			}
//		}
//		
//		animalCodePrintComboBox.setItems(animalCodePrintdata);
//    
//	}
	
	/**
	 * 初始化脏器
	 */
	private void initComboBoxVisceraName(){
		visceraNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
					String sessiond = taskIdSessionIdMap.get(taskId);
					updateAnatomyCheckTable(sessiond);//初始化解剖所见列表
					updateVisceraWeight(sessiond);
					updateFixedTab(sessiond);
//					anatomyTaskTable.getSelectionModel().clearSelection();
				}
			}
		});
	}
	public void updateVisceraNameCombobox(){
		List<String> list=new ArrayList<String>();
        List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyCheckService().getVisceraNameBySessionIDsAndTaskIdList(sessionIdList, taskIdList);
    	if(null != mapList && mapList.size()>0){
    		list.add("全部");
			for(Map<String,Object> map:mapList){
				String visceraName = (String) map.get("visceraName");//
				list.add(visceraName);
		    }
    	}
    	visceraNamedata.clear();
		if(null!=list && list.size()>0){
			for(String dataBit : list){
				visceraNamedata.add(dataBit);
			}
		}
		
		visceraNameComboBox.setItems(visceraNamedata);
		visceraNameComboBox.getSelectionModel().selectFirst();
	}
	/**
	 * 选择一行
	 * @param balConnect
	 */
	public void selectRow(String id){
		for(int i = 0;i<data_anatomyCheckTable.size();i++){
			if(data_anatomyCheckTable.get(i).getId().equals(id)){
				anatomyCheckTable.getSelectionModel().select(i);
				anatomyCheckTable.scrollTo(i);
				break;
			}
		}
		
	}
	
	
	//---根据会话sessionid刷新列表
	/**更新解剖所见表格数据
	 * @param taskIdList2
	 */
	public void updateAnatomyCheckTable(String sessionId) {
		data_anatomyCheckTable.clear();
		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
		List<String> sessionIdlist = new ArrayList<String>();
		sessionIdlist.add(sessionId);
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblAnatomyCheckService().getAnatomyCheckBySessionIDs(sessionIdlist,"全部", animalCodeSelected,visceraNameSelected);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String id = (String) map.get("id");//专题编号
				String astudyNo = (String) map.get("studyNo");//专题编号
				//String sessionId = (String)map.get("sessionId");
				String animalCode = (String) map.get("animalCode");//动物编号
				String  visceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  anatomyFinding = (String) map.get("anatomyFingding");//解剖所见
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
//				String bodySurfacePos = (String) map.get("bodySurfacePos") ;//体表部位
				String anatomyOperator = (String) map.get("anatomyOperator") ;//解剖者
				if( null != subVisceraName && !subVisceraName.equals("") ){
					visceraName = subVisceraName;
				}
				Integer autolyzaFlag =  (Integer) map.get("autolyzaFlag") ;
				if(autolyzaFlag == 1 ){
					anatomyFinding = "自溶";
				}
//				if(null == visceraName ||visceraName.equals("")){
//					visceraName = bodySurfacePos;
//				}
				data_anatomyCheckTable.add(new AnatomyCheck(animalCode,visceraName,anatomyFinding
						,operator,operateTime,astudyNo,id,sessionId,anatomyOperator));
			}
		}
		initAnatomyCheckString(taskIdList);	//加载下标文字
		
	}
	/**更新解剖所见表格数据
	 * 
	 */
	public void updateAnatomyCheckTable() {
		data_anatomyCheckTable.clear();
		String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
		String sessionId = taskIdSessionIdMap.get(taskId);
		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
		List<String> sessionIdlist = new ArrayList<String>();
		sessionIdlist.add(sessionId);
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblAnatomyCheckService().getAnatomyCheckBySessionIDs(sessionIdlist,"全部", animalCodeSelected,visceraNameSelected);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String id = (String) map.get("id");//专题编号
				String astudyNo = (String) map.get("studyNo");//专题编号
				//String sessionId = (String)map.get("sessionId");
				String animalCode = (String) map.get("animalCode");//动物编号
				String  visceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  anatomyFinding = (String) map.get("anatomyFingding");//解剖所见
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
//				String bodySurfacePos = (String) map.get("bodySurfacePos") ;//体表部位
				String anatomyOperator = (String) map.get("anatomyOperator") ;//解剖者
				if( null != subVisceraName && !subVisceraName.equals("") ){
					visceraName = subVisceraName;
				}
				Integer autolyzaFlag =  (Integer) map.get("autolyzaFlag") ;
				if(autolyzaFlag == 1 ){
					anatomyFinding = "自溶";
				}
//				if(null == visceraName ||visceraName.equals("")){
//					visceraName = bodySurfacePos;
//				}
				data_anatomyCheckTable.add(new AnatomyCheck(animalCode,visceraName,anatomyFinding
						,operator,operateTime,astudyNo,id,sessionId,anatomyOperator));
			}
		}
		initAnatomyCheckString(taskIdList);	//加载下标文字
		
	}
	
	
	/**
	 * 更新脏器称重
	 */
	private void updateVisceraWeight(String sessionId){
		data_visceraWeightTable.clear();
		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
		List<String> slist = new ArrayList<String>();
		slist.add(sessionId);
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblVisceraWeightService().getVisceraMapListBySessionID(slist,"全部", animalCodeSelected,visceraNameSelected);
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
//				weight = weight + " "+weightUnit+"";
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
				data_visceraWeightTable.add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
					wvisceraName, weight,weightUnit,fixedWeighFlag,attachedVisceraNames,
					operator,operateTime,balCode));
			}
		}

	}
	
	/**
	 * 更新脏器固定记录表
	 */         
	public void updateFixedTab(String sessionId){
		data_visceraFixedTable.clear();
		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
		List<String> slist = new ArrayList<String>();
		slist.add(sessionId);
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getListBySessionIdAnimalCodeVisceraName(slist,"全部",animalCodeSelected,visceraNameSelected);
		
		if(null!=list&&list.size()>0){
			for(Map<String,Object> map:list){
				String id=(String) map.get("id");
				String studyNo=(String) map.get("studyNo");
				//String sessionId=(String) map.get("sessionId");
				String animalCode=(String) map.get("animalCode");
				String visceraName=(String) map.get("visceraName");
				if(null!=(String) map.get("subVisceraName")){
					visceraName=visceraName+","+(String) map.get("subVisceraName");
				}
				String operator=(String) map.get("operator");
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
				Date operateTime=(Date) map.get("operateTime");
				String operateTimeStr= sdf.format(operateTime);
				if(null != visceraName && !visceraName.equals("") ){
					data_visceraFixedTable.add(new VisceraFixed(id,studyNo,sessionId,animalCode,visceraName,operator,operateTimeStr));
				}
				
			}
			List<Map<String,Object>> mapList2 = BaseService.getInstance().getTblVisceraFixedService().getListBySessionid(sessionId);//异常固定
			
			if(null!=mapList2&&mapList2.size()>0){
				for(Map<String,Object> map:mapList2){
					String id=(String) map.get("id");
					String studyNo=(String) map.get("studyNo");
					//String sessionId=(String) map.get("sessionId");
					String animalCode=(String) map.get("animalCode");
					String visceraName=(String) map.get("visceraName");
					if(null!=(String) map.get("subVisceraName")){
						visceraName=(String) map.get("subVisceraName");
					}
					String operator=(String) map.get("operator");
					operator = BaseService.getInstance().getUserService().getByUserName(operator).getRealName();
			
					SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
					Date operateTime=(Date) map.get("operateTime");
					String operateTimeStr= sdf.format(operateTime);
					if(null ==  visceraName  ||visceraName.equals("") ){
						visceraName = (String) map.get("bodySurfacePos");
					}
					String anatomyFingding=(String) map.get("anatomyFingding");
					visceraName = visceraName +" "+ anatomyFingding;
					if(null != visceraName && !visceraName.equals("") ){
						data_visceraFixedTable.add(new VisceraFixed(id,studyNo,sessionId,animalCode,visceraName,operator,operateTimeStr));
					}
					
				}
			}
		}
		
	}
	//---
	
	
	
	
	/**更新解剖所见表格数据
	 * @param taskIdList2
	 */
	public void updateAnatomyCheckTable(String studyNo,String aanimalCode) {
		data_anatomyCheckTable.clear();
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblAnatomyCheckService().getAnatomyCheckBySessionIDs(sessionIdList,studyNo,aanimalCode,null);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String id = (String) map.get("id");//专题编号
				String astudyNo = (String) map.get("studyNo");//专题编号
				String sessionId = (String)map.get("sessionId");
				String animalCode = (String) map.get("animalCode");//动物编号
				String  visceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  anatomyFinding = (String) map.get("anatomyFingding");//解剖所见
				String operator = (String) map.get("realName") ;//操作者
				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
				
			/*	String anatomyPos = (String) map.get("anatomyPos") ; //解剖学所见部位60
				String pos = (String) map.get("pos");                //位置60
				String shape= (String) map.get("shape");              //形状60
				String color= (String) map.get("color");              //颜色60
				String texture= (String) map.get("texture");            //硬度60
				String number= (String) map.get("number");             //数量60
				String range= (String) map.get("range");              //范围60
				String lesionDegree= (String) map.get("lesionDegree");       //病变程度60
				String size= (String) map.get("size");				//大小 20
				*/
//				String bodySurfacePos = (String) map.get("bodySurfacePos") ;//体表部位
				String anatomyOperator = (String) map.get("anatomyOperator") ;//解剖者
				if( null != subVisceraName && !subVisceraName.equals("") ){
					visceraName = subVisceraName;
				}
				Integer autolyzaFlag =  (Integer) map.get("autolyzaFlag") ;
				if(autolyzaFlag == 1 ){
					anatomyFinding = "自溶";
				}
//				if(null == visceraName ||visceraName.equals("")){
//					visceraName = bodySurfacePos;
//				}
				data_anatomyCheckTable.add(new AnatomyCheck(animalCode,visceraName,anatomyFinding
						,operator,operateTime,astudyNo,id,sessionId,anatomyOperator));
			}
		}
		//
		animalCodeComboBox.getSelectionModel().select(aanimalCode);
		initAnatomyCheckString(taskIdList);	//加载下标文字
		
	}
	
	
//	/**
//	 * 更新脏器称重
//	 */
//	private void updateVisceraWeight(){
//		data_visceraWeightTable.clear();
////		String studyNoSelected = studyNoComboBox.getSelectionModel().getSelectedItem();
//		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
//		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
//		List<Map<String,Object>> taskMapList = 
//				BaseService.getInstance().getTblVisceraWeightService().getVisceraMapListBySessionID(sessionIdList,null,animalCodeSelected,visceraNameSelected);
//		if(null != taskMapList && taskMapList.size()>0){
//			for(Map<String,Object> map:taskMapList){
//				String wid = (String) map.get("id");//主键
//				String sessionId = (String)map.get("sessionId");
//				String wstudyNo = (String) map.get("studyNo");//专题编号
//				String wanimalCode = (String) map.get("animalCode");//动物编号
//				String  wvisceraName = (String) map.get("visceraName");//脏器名称
//				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
//				String  weight = (String) map.get("weight");//重量
//				String  weightUnit = (String) map.get("weightUnit");//重量单位
//				weight = weight + " "+weightUnit+"";
//				Integer fixedWeighFlag = (Integer) map.get("fixedWeightFlag");//是否固定后称重
//				String operator = (String) map.get("realName") ;//操作者
//				String  operateTime = DateUtil.dateToString((Date) map.get("operateTime"), "yyyy-MM-dd HH:mm");//操作时间
//				if( null != subVisceraName && !subVisceraName.equals("") ){
////					wvisceraName = wvisceraName+" "+subVisceraName;
//					wvisceraName = subVisceraName;
//				}
//				String attachedVisceraNames = (String) map.get("attachedVisceraNames") ;//附加脏器列表
//				String balCode = (String) map.get("balCode") ;//天平编号
//				data_visceraWeightTable.add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
//					wvisceraName, weight,fixedWeighFlag,attachedVisceraNames,
//					operator,operateTime,balCode));
//			}
//		}
//
//	}
	
//	/**
//	 * 更新脏器固定记录表
//	 */
//	public void updateFixedTab(){
//		data_visceraFixedTable.clear();
////		String studyNoSelected = studyNoComboBox.getSelectionModel().getSelectedItem();
//		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
//		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
//		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getListBySessionIdAnimalCodeVisceraName(sessionIdList, null, animalCodeSelected,visceraNameSelected);
//		if(null!=list&&list.size()>0){
//			for(Map<String,Object> map:list){
//				String id=(String) map.get("id");
//				String studyNo=(String) map.get("studyNo");
//				String sessionId=(String) map.get("sessionId");
//				String animalCode=(String) map.get("animalCode");
//				String visceraName=(String) map.get("visceraName");
//				if(null!=(String) map.get("subVisceraName")){
//					visceraName=visceraName+","+(String) map.get("subVisceraName");
//				}
//				if(visceraName==null || visceraName.equals("")){
//					TblVisceraFixed tblv=BaseService.getInstance().getTblVisceraFixedService().getById(id);
//					String acId=tblv.getAnatomyCheckRecordId();
//					TblAnatomyCheck tbla=BaseService.getInstance().getTblAnatomyCheckService().getById(acId);
//					if(null!=tbla.getVisceraCode()&&!"".equals(tbla.getVisceraCode())){
//						visceraName=tbla.getVisceraName()+tbla.getAnatomyFingding();
//					}else{
//						visceraName=tbla.getAnatomyFingding();
//					}
//				}
//				String operator=(String) map.get("operator");
//				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
//				Date operateTime=(Date) map.get("operateTime");
//				String operateTimeStr = "";
//				if(null != operateTime){
//					operateTimeStr= sdf.format(operateTime);
//				}else{
//					operateTimeStr = "";
//				}
//				data_visceraFixedTable.add(new VisceraFixed(id,studyNo,sessionId,animalCode,visceraName,operator,operateTimeStr));
//			}
//		}
//		
//	}
	
	/**
	 * 更新脏器称重，并选中指定行
	 */
	public void updateVisceraWeightTable(String id){
		data_visceraWeightTable.clear();
//		String studyNoSelected = studyNoComboBox.getSelectionModel().getSelectedItem();
		String animalCodeSelected = animalCodeComboBox.getSelectionModel().getSelectedItem();
		String visceraNameSelected = visceraNameComboBox.getSelectionModel().getSelectedItem();
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblVisceraWeightService().getVisceraMapListBySessionID(sessionIdList,null,animalCodeSelected,visceraNameSelected);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String wid = (String) map.get("id");//主键
				String sessionId = (String)map.get("sessionId");
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
				data_visceraWeightTable.add(new visceraWeight(wid,sessionId, wstudyNo, wanimalCode,
						wvisceraName, weight,weightUnit,fixedWeighFlag,attachedVisceraNames,
						operator,operateTime,balCode));
			}
		}
		//选中指定行
		for(visceraWeight visceraWeight:data_visceraWeightTable){
			if(visceraWeight.getWid().equals(id)){
				visceraWeightTable.getSelectionModel().select(visceraWeight);
				break;
			}
		}
		
	}
	
	
	/**
	 * 初始化 table
	 */
	private void initTaskTable() {
		anatomyTaskTable.setItems(data_anatomyTaskTable);
		anatomyTaskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("studyNo"));
		anatomyNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("anatomyNum"));
		taskCreateDateCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreateDate"));
		taskCreaterCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreater"));
		animalTypeCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("animalType"));
		anatomyRsnCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("anatomyRsn"));
		
		finishCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("finish"));
		
		anatomyTaskTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<AnatomyTask>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyTask> arg0, AnatomyTask arg1,
					AnatomyTask newValue) {
				if(null != newValue){
					String taskId = newValue.getTaskId();
					String sessionId = taskIdSessionIdMap.get(taskId);
					String studyNo = newValue.getStudyNo();
					updateAnatomyCheckTable(sessionId);
					updateFixedTab(sessionId);
					updateVisceraWeight(sessionId);
					updateAnimalCodeCombobox();
					updateVisceraNameCombobox();
//					updateComboBoxAnimalCodePrint();
//					animalCodeComboBox.getSelectionModel().clearSelection();
					animalCodedata.clear();
					List<String> list=new ArrayList<String>();
					List<Map<String,Object>> mapList =  BaseService.getInstance().getTblAnatomyAnimalService().getAnimalCodeBySessionIdList(sessionIdList,studyNo);
					if(null != mapList && mapList.size()>0){
                		list.add("全部");
            			for(Map<String,Object> map:mapList){
            				String animalCode = (String) map.get("animalCode");//
            				if(!list.contains(animalCode)){
	            				list.add(animalCode);
            				}else{
            					continue;
            				}
            		    }
            			if(null!=list && list.size()>0){
	            			for(String dataBit : list){
	            				animalCodedata.add(dataBit);
	            			}
	            		}
            			animalCodeComboBox.getSelectionModel().selectFirst();
            			String finish = newValue.getFinish();
            			if(finish.equals("1")){
            				addButton.setDisable(true);
            				editButton.setDisable(true);
            				deleteButton.setDisable(true);
            				weighTeditButton.setDisable(true);
            			}
                	}
				}
			}
		});
		
		taskCreaterCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyTask, String> call(
	    			 TableColumn<AnatomyTask, String> param) {
	    		 TableCell<AnatomyTask, String> cell =
	    				 new TableCell<AnatomyTask, String>() {
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
		taskCreateDateCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
			
			@Override
			public TableCell<AnatomyTask, String> call(
					TableColumn<AnatomyTask, String> param) {
				TableCell<AnatomyTask, String> cell =
						new TableCell<AnatomyTask, String>() {
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
		anatomyNumCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
			
			@Override
			public TableCell<AnatomyTask, String> call(
					TableColumn<AnatomyTask, String> param) {
				TableCell<AnatomyTask, String> cell =
						new TableCell<AnatomyTask, String>() {
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
		anatomyRsnCol.setCellFactory(new Callback<TableColumn<AnatomyTask,String>,TableCell<AnatomyTask,String>>(){
			
			@Override
			public TableCell<AnatomyTask, String> call(
					TableColumn<AnatomyTask, String> param) {
				TableCell<AnatomyTask, String> cell =
						new TableCell<AnatomyTask, String>() {
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
	}
	
	/**更新解剖任务表 数据
	 * @param taskIdList2
	 */
	private void updateTable(List<String> taskIdList2) {
		
		data_anatomyTaskTable.clear();
		//查询课题编号，解剖任务序号，任务id，任务创建日期（yyyy-MM-dd）,创建者（姓名）,动物种类，动物数量，解剖原因
		//taskId降序排列
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblAnatomyTaskService().getListByTaskIdList(taskIdList2);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String studyNo = (String) map.get("studyNo");
				String taskId = (String) map.get("taskId");
				Integer anatomyNum = (Integer) map.get("anatomyNum");
				String taskCreateTime = (String) map.get("taskCreateTime");
				String taskCreater = (String) map.get("taskCreater");
				
				String animalType = (String) map.get("animalType");
				Integer animalNum = (Integer) map.get("anatomyNum");
				String anatomyRsn = (String) map.get("anatomyRsn");
				
				String sessionId = taskIdSessionIdMap.get(taskId);
				//可以确认数据，将检查会话是否已确认
			    TblPathSession t=BaseService.getInstance().getTblPathSessionService().getById(sessionId);
			    String finsh = "";
			    if(null != t.getSessionFinishSign()){
			    	finsh = "1";
			    }else{
			    	finsh = "0";
			    }
				
				data_anatomyTaskTable.add(new AnatomyTask(taskId,studyNo,anatomyNum
						,taskCreateTime,taskCreater,animalType,animalNum,anatomyRsn,finsh));
			}
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DataValidation.fxml"));
		Scene scene = new Scene(root, 950, 650);
		stage.setScene(scene);
		stage.setTitle("数据确认");
		stage.setResizable(false);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX((bounds.getMaxX() - 950D) / 2);
		stage.setY((bounds.getMaxY() -650D) / 2);
		stage.setWidth(950);
		stage.setHeight(650);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
    

	/**
	 * 解剖所见添加
	 */
	@FXML
	private void onaddBtnAction(ActionEvent event){
//		try {
//			AddAnatomy.getInstance().start(getStage());
//			String id = null;
//			if( null == anatomyCheckTable.getSelectionModel().getSelectedItem()){
//				
//			}else{
//				id = anatomyCheckTable.getSelectionModel().getSelectedItem().getId();
//			}
//		
//			AddAnatomy.getInstance().loadData(taskIdList,sessionIdList,id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			Stage stage = Main.stageMap.get("EditAnatomy");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				EditAnatomy.getInstance().start(stage);
				Main.stageMap.put("EditAnatomy",stage);
			}else{ 
				stage.show();
			}
//			EditAnatomy.getInstance().start(getStage());
			EditAnatomy.getInstance().loadData(null,"add",null,null);
			EditAnatomy.getInstance().loadDateFromValidate(taskIdList,sessionIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 初始化脏器固定记录表
	 */
	private void initVisceraFixedTable(){
		visceraFixedTable.setItems(data_visceraFixedTable);
		visceraFixedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		fixedIdCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("id"));
		fixedStudyNoCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("studyNo"));
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
								anatomyCheckTable.getSelectionModel().clearSelection();
								visceraWeightTable.getSelectionModel().clearSelection();
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
	    		 return cell;
	    	 }
	     });
	}
	
	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignatureBtnAction(ActionEvent event){
//		List<TblPathSession> sessionList= new ArrayList<TblPathSession>();
		//用于存放需要确认的会话Id
		List<String> needConfirmSessionList =new ArrayList<String>();
		if(null!=sessionIdList && sessionIdList.size()>0){
			TblPathSession tblPathSession=BaseService.getInstance().getTblPathSessionService().getById(sessionIdList.get(0));
			int sessionType=0;
			int sessionFinishSignTime=0;
			if(null!=tblPathSession){
				sessionType=tblPathSession.getSessionType();
			}
			for(String sessionId:sessionIdList){
				//判断是否可以确认数据
			    List<TblAnatomyAnimal> list= BaseService.getInstance().getTblAnatomyAnimalService().getListBySessionTypeAndSessionId(sessionType,sessionId);
			    if(list!=null && list.size()>0){
			    	showErrorMessage("有动物未完成会话动作,请检查！");
			    	return;
			    }
			    //可以确认数据，将检查会话是否已确认
			    TblPathSession t=BaseService.getInstance().getTblPathSessionService().getById(sessionId);
			    if(null != t.getSessionFinishSign()){
			    	sessionFinishSignTime++;
			    }else{
			    	needConfirmSessionList.add(sessionId);
			    }
			}
			if(sessionFinishSignTime>0 && sessionFinishSignTime<sessionIdList.size()){
				if(Messager.showSimpleConfirm("提示","有部分会话数据已签字确认，此次将确认未签字的数据？")){
				}else{
					return;
				}
			}else if(sessionFinishSignTime==sessionIdList.size()){
				showErrorMessage("所有会话数据都已签字确认，无需重复操作！");
				return;
			}
		}
		
		//签字窗口
		SignFrame signFrame = new SignFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("身份验证");
		try {
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//签字通过
		if("OK".equals(SignFrame.getType())){
			String userName = "";
//					Date currentDate = new Date();
			User user = SignFrame.getUser();
			if(null != user){
				userName = user.getUserName();
			}

			BaseService.getInstance().getTblPathSessionService().updateListBySessionIdList(userName,needConfirmSessionList);
			confirmButton.setDisable(true);
			addButton.setDisable(true);
			editButton.setDisable(true);
			deleteButton.setDisable(true);
			weighTeditButton.setDisable(true);
			showMessage("数据确认完成");
			MainPageController.getInstance().updateSessionTable();
		}
		
	}
	
	/**
	 * 解剖所见下打印按钮事件
	 * @throws JRException 
	 */
	@FXML
	private void onAnatomyCheckPrintButton(ActionEvent event) throws JRException{
		JasperReport jr = null;
        JasperPrint jp = null;                                             
        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("PrintAnatomyCheck.jrxml"));
        Map<String,Object> map = new HashMap<String,Object>();
           URL url = this.getClass().getResource("/image/logo.jpg");
           
//         List<Map<String,Object>> maplist =  BaseService.getInstance().getTblAnatomyCheckService().getTblAnatomyCheckPrint(sessionIdList);
           //(子脏器一个有异常，其他显示未见异常)
           List<Map<String,Object>> maplist =  BaseService.getInstance().getTblAnatomyCheckService().getTblAnatomyCheckPrint_new(sessionIdList);
           
           String number = "";
		   number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("动物剖检记录表（二）");
		   map.put("number", number == null ? "":number);;//编号
      	   map.put("logoImage", url);
           map.put("reportName","动物剖检记录表（二）");
           if(null != maplist && maplist.size() > 0){
        	   for(Map<String,Object> obj:maplist){
        		   String animalType = (String) obj.get("animalType");
        		   String animalStrain = (String) obj.get("animalStrain");
        		   if(null != animalStrain || !"".equals(animalStrain)){
			        	if(!animalStrain.contains(animalType)){
			        		animalType = animalType +" "+animalStrain;
			        	}else{
			        		animalType = animalStrain;
			        	}
			       }
        		   obj.put("animalType", animalType);
        	   }
           }
           if(null != maplist && maplist.size() > 0){
        	   for(Map<String,Object> obj:maplist){
        		   String studyNo = (String) obj.get("studyNo");
        		   obj.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
        	   }
           }
      		jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(maplist));     
            Main.getInstance().openJFrame(jp, "动物剖检记录表（二）");
//        }
        return;
	}
	
	
	
	/**
	 * 脏器称重打印
	 */
	@FXML
	private void onPrintVisceraWeightBtnAction(ActionEvent event) throws JRException {
		    Integer viseraWegSize = data_visceraWeightTable.size();
		    Integer anatomySize = data_anatomyTaskTable.size();
		    //String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		    if(viseraWegSize < 1 ){
		    	showMessage("暂无脏器称重信息！");
		    	//&& (null == studyNo || studyNo == "全部" || studyNo.equals("") )
		    }else if( (null ==  anatomyTaskTable.getSelectionModel().getSelectedItem() )  && (null == visceraWeightTable.getSelectionModel().getSelectedItem())&& anatomySize != 1){
		    	showMessage("请先选择打印数据的会话!");
		    }else{
		    	String taskId = "";
		    	String sessionId = "";
		    	if( anatomySize == 1){
		    		taskId = anatomyTaskTable.getItems().get(0).getTaskId();
		    	}else{
		    		if(null !=  anatomyTaskTable.getSelectionModel().getSelectedItem()){
		    			taskId =  anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
			    	}else if(null != visceraWeightTable.getSelectionModel().getSelectedItem()){
			    		sessionId = visceraWeightTable.getSelectionModel().getSelectedItem().getWsessionId();
//			    	}else if( studyNo != "全部" && !studyNo.equals("")&&null != studyNo ){
//			    		for(int i = 0 ;i < anatomySize ;i++ ){
//			    			String tstudyNo = anatomyTaskTable.getItems().get(i).getStudyNo();
//			    			if(tstudyNo.equals(studyNo)){
//	    				String atsessionid = taskIdSessionIdMap.get(anatomyTaskTable.getItems().get(i).getTaskId());
//	    				sessionIdList.add(atsessionid);
//	    			}
//	    		}
			    	}else{
			    		showMessage("请先选择打印数据的会话!");
			    	}
		    	}
		    	if(sessionId.equals("")){
		    		sessionId = taskIdSessionIdMap.get(taskId);
//		    		sessionIdList.add(sessionId);
		    	}
		    	TblAnatomyTask anatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		    	TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getById(anatomyTask.getStudyNo());
		    	TblPathSession pathSession = BaseService.getInstance().getTblPathSessionService().getById(sessionId);
		    	User creator = BaseService.getInstance().getUserService().getByUserName(pathSession.getSessionCreator());
		    	List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraWeightService().getPrintVisceraWeightServiceBySession(sessionId);
		    	if(null == mapList){
		    		showMessage("暂无脏器称重信息！");
		    	}else{
		    		JasperReport jr = null;
			        JasperPrint jp = null;
			        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("PrintVisceraWeight.jrxml"));
			        Map<String,Object> map = new HashMap<String,Object>();
			        map.put("reportName","动物脏器称重记录表（二）");//表名
//			        map.put("studyNo",anatomyTask.getStudyNo());//专题编号
			        map.put("studyNo",StringUtil.studyNoRemoveFN(anatomyTask.getStudyNo()));//专题编号
			        
			        String animalType = studyPlan.getAnimalType();
			        String animalStrain = studyPlan.getAnimalStrain();
			        if(null != animalStrain || !"".equals(animalStrain)){
			        	if(!animalStrain.contains(animalType)){
			        		animalStrain = animalType +" "+animalStrain;
			        	}
			        }else{
			        	animalStrain = animalType;
			        }
				    map.put("animalStrain",animalStrain);//动物种属
				    
				    map.put("anatomyRsn",anatomyTask.getAnatomyRsn());//解剖原因
				    String number = "";
					number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("动物脏器称重记录表（二）");
				    
				    map.put("number", number == null ? "":number);;//编号
				    
				    map.put("people",""+creator.getRealName()+"");//称重者
				    
			        URL url = this.getClass().getResource("/image/logo.jpg");
			   		map.put("logoImage", url);
                    String weightUnit = "";
			   		List<VisceraWeightDataForReport> list = new ArrayList<VisceraWeightDataForReport>();
			   		for(Map<String,Object> obj:mapList){
			   		//报表  表格里的数据
			   			VisceraWeightDataForReport entity= new VisceraWeightDataForReport();
						entity.setAnimalCode((String)obj.get("animalCode"));
						entity.setGender((Integer)obj.get("gender"));
						String visceraName = (String)obj.get("visceraName");
						String subVisceraName = (String)obj.get("subVisceraName");
						Integer snWeight = (Integer) obj.get("snWeight");
						Integer attachedVisceraFlag = (Integer) obj.get("attachedVisceraFlag");
						String attachedVisceraNames = (String) obj.get("attachedVisceraNames");
						Integer snWeight2 = (Integer) obj.get("snWeight2");
						if(null != attachedVisceraFlag && attachedVisceraFlag == 1){
							visceraName =visceraName+"("+attachedVisceraNames+")";
						}
						if(null == subVisceraName || subVisceraName.equals("")  ){
							
						}else{
							visceraName = subVisceraName;
						}
						visceraName =get4Str(snWeight)+get4Str(snWeight2)+visceraName;
						if(null != obj.get("weight") && !obj.get("weight").equals("") ){
							entity.setTestIndexAbbr(visceraName);
							entity.setTestData((String)obj.get("weight"));
						}else{
							entity.setTestData("");
						}
						
						if(weightUnit.equals("")){
							weightUnit = (String)obj.get("weightUnit");
						}
						list.add(entity);
			   		}
			   		map.put("weightUnit", "脏器重量（"+weightUnit+"）");
					jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(list) );
			        Main.getInstance().openJFrame(jp, "动物脏器称量记录表（二）");	
		    	}
		    	
		    }
			
            return;
	}
	
	/**返回长度为4的字符串，不够前面补0
	 * @param sn
	 * @return
	 */
	private String get4Str(Integer sn){
		if(null == sn){
			return "0000";
		}else{
			sn = 10000+sn;
			String snStr = sn+"";
			return snStr.substring(1);
		}
	}
	
	/**
	 * 脏器固定打印
	 */
	@FXML
	private void onPrintVisceraFixBtnAction(ActionEvent event) throws JRException {
		    Integer viseraFixSize = data_visceraFixedTable.size();
		    Integer anatomySize = data_anatomyTaskTable.size();
		    //String studyNo = studyNoComboBox.getSelectionModel().getSelectedItem();
		    if(viseraFixSize < 1 ){
		    	showMessage("暂无脏器固定信息！");
		    	//&& (null == studyNo || studyNo == "全部" || studyNo.equals("") )
		    }else if( (null ==  anatomyTaskTable.getSelectionModel().getSelectedItem() )  && (null == visceraFixedTable.getSelectionModel().getSelectedItem())&& anatomySize != 1){
		    	showMessage("请先选择打印数据的会话!");
		    }else{
		    	String taskId = "";
		    	String sessionId = "";
		    	if( anatomySize == 1){
		    		taskId = anatomyTaskTable.getItems().get(0).getTaskId();
		    	}else{
		    		if(null !=  anatomyTaskTable.getSelectionModel().getSelectedItem()){
		    			taskId =  anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
			    	}else if(null != visceraFixedTable.getSelectionModel().getSelectedItem()){
			    		sessionId = visceraFixedTable.getSelectionModel().getSelectedItem().getSessionId();
//			    	}else if( studyNo != "全部" && !studyNo.equals("")&&null != studyNo ){
//			    		for(int i = 0 ;i < anatomySize ;i++ ){
//			    			String tstudyNo = anatomyTaskTable.getItems().get(i).getStudyNo();
//			    			if(tstudyNo.equals(studyNo)){
//	    				String atsessionid = taskIdSessionIdMap.get(anatomyTaskTable.getItems().get(i).getTaskId());
//	    				sessionIdList.add(atsessionid);
//	    			}
//	    		}
			    	}else{
			    		showMessage("请先选择打印数据的会话!");
			    	}
		    	}
		    	if(sessionId.equals("")){
		    		sessionId = taskIdSessionIdMap.get(taskId);
		    	}
		    	TblAnatomyTask anatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		    	TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getById(anatomyTask.getStudyNo());
		    	TblPathSession pathSession = BaseService.getInstance().getTblPathSessionService().getById(sessionId);
		    	User creator = BaseService.getInstance().getUserService().getByUserName(pathSession.getSessionCreator());
		    	List<String> sessionIdlist = new ArrayList<String>();
		    	sessionIdlist.add(sessionId);
		    	//List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService().getListBySessionIdAnimalCodeVisceraName(sessionIdlist,null,null,null);
		    	List<Map<String,Object>> mapList = BaseService.getInstance().getTblVisceraFixedService().getListBySessionIdAnimalCodeVisceraName2(sessionIdlist,null,null,null);
		    	List<Map<String,Object>> mapList1 = BaseService.getInstance().getTblAnatomyAnimalService().getListBySessionId(sessionId);//固定完成时间
		    	List<Map<String,Object>> mapList2 = BaseService.getInstance().getTblVisceraFixedService().getListBySessionid(sessionId);//备注
		    	if(null == mapList){
		    		showMessage("暂无脏器固定信息！");
		    	}else{
		    		JasperReport jr = null;
			        JasperPrint jp = null;
			        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("visceraFixedPrint.jrxml"));
			        Map<String,Object> map = new HashMap<String,Object>();
			        map.put("reportName","动物脏器摘取和固定确认记录表（二）");//表名
			        
			        map.put("studyNo",StringUtil.studyNoRemoveFN(anatomyTask.getStudyNo()));//专题编号
			    
//				    map.put("animalType",studyPlan.getAnimalType()+" "+studyPlan.getAnimalStrain());//动物种属
				    String animalType = studyPlan.getAnimalType();
			        String animalStrain = studyPlan.getAnimalStrain();
			        if(null != animalStrain || !"".equals(animalStrain)){
			        	if(!animalStrain.contains(animalType)){
			        		animalStrain = animalType +" "+animalStrain;
			        	}
			        }else{
			        	animalStrain = animalType;
			        }
				    map.put("animalType",animalStrain);//动物种属
				    
				    map.put("anatomyRsn",anatomyTask.getAnatomyRsn());//解剖原因
				    
				    String number = "";
					number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("动物脏器摘取和固定确认记录表（二）");
				    
				    map.put("number", number == null ? "":number);;//编号
				    
				    map.put("people",""+creator.getRealName()+"");//称重者
				    
				    String remarks ;
				    if(null != mapList2 && mapList2.size() > 0 ){
				    	remarks = "备注：（";
				    	for(Map<String,Object> mapObj:mapList2){
				  			String animalCode = (String)mapObj.get("animalCode");//动物编号
				  			remarks = remarks+animalCode+" ";
				  			String visceraName =  (String)mapObj.get("visceraName");//脏器名称
				  			if(null == visceraName ){
				  				visceraName = "";
				  			}else{
				  				String subVisceraName = (String)mapObj.get("subVisceraName");
				  				if(null != subVisceraName && !"".equals(subVisceraName)){
				  					visceraName = subVisceraName;
				  				}
				  				remarks = remarks+""+visceraName;
				  			}
//				  			String subVisceraName =  (String)mapObj.get("subVisceraName");//子脏器名称
//				  			if(null == subVisceraName ){
//				  				subVisceraName = "";
//				  			}else{
//				  				remarks = remarks+" "+subVisceraName;
//				  			}
				  			String anatomyPos  =  (String)mapObj.get("anatomyPos");//解剖学所见部位60
				  			if(null == anatomyPos ){
				  				anatomyPos = "";
				  			}else{
				  				remarks = remarks+""+anatomyPos;
				  			}
				  			String bodySurfacePos =  (String)mapObj.get("bodySurfacePos");//体表部位60
				  			if(null == bodySurfacePos ){
				  				bodySurfacePos = "";
				  			}else{
				  				remarks = remarks+""+bodySurfacePos;
				  			}
				  			String anatomyFingding =  (String)mapObj.get("anatomyFingding"); //解剖所见100
				  			if(null == anatomyFingding ){
				  				anatomyFingding = "";
				  			}else{
				  				remarks = remarks+""+" "+anatomyFingding;
				  			}
				  			remarks = remarks+"; ";
				  		}
					    	
				    }else{
				    	 remarks = "备注：";
				    }
			  		
				 
				    
			        URL url = this.getClass().getResource("/image/logo.jpg");
			   		map.put("logoImage", url);
			   		List<VisceraFixDataForReport> list = new ArrayList<VisceraFixDataForReport>();
			   		
			   		int addsize = 0 ;
			   		String animalCodeStr = "";
			   		
//			   		Map<String,List<VisceraFixedCompare>> maps = new HashMap<String,List<VisceraFixedCompare>>();
			   		
			   		for(Map<String,Object> mapObj:mapList){
			   		//报表  表格里的数据	
			   			VisceraFixDataForReport entity= new VisceraFixDataForReport();
						entity.setVisceraName(get4Str((Integer)mapObj.get("snFixed"))+(String)mapObj.get("visceraName"));
						entity.setAnimalIndexCodeDate((String)mapObj.get("animalCode"));
						//entity.setAnimalCode("√");
						//vf._fixedFlag, tblc._autolyzaFlag,missing._missFlag
						String _fixedFlag = (String)mapObj.get("_fixedFlag");
						String _autolyzaFlag = (String)mapObj.get("_autolyzaFlag");
						String _missType = (String)mapObj.get("_missType"); 
						String _missFlag = (String)mapObj.get("_missFlag");
						String missingRsn = (String)mapObj.get("missingRsn");
						
						
						String returnValue="";
						//已固定
						if(_fixedFlag!=null&&"Y".equals(_fixedFlag))
						{
							returnValue="√";
							entity.setAnimalCode(returnValue);
							if(!animalCodeStr.contains((String)mapObj.get("animalCode"))){
								animalCodeStr = animalCodeStr+"#"+(String)mapObj.get("animalCode")+"#";
								addsize++;
							}
							if(null != mapObj.get("visceraName") && !mapObj.get("visceraName").equals("")){
								list.add(entity);
							}
							
							
						}
						 if(_autolyzaFlag!=null&&"Y".equals(_autolyzaFlag))
						{
							
							if((String)mapObj.get("subVisceraName")!=null)
							{//子脏器自溶
								returnValue=(String)mapObj.get("subVisceraName")+"自溶";
								
								boolean flag = false;
								VisceraFixDataForReport temp2 = null;
								for(VisceraFixDataForReport temp:list)
								{
									if(temp.getAnimalIndexCodeDate().equals((String)mapObj.get("animalCode"))&&temp.getVisceraName().substring(4).equals((String)mapObj.get("visceraName")))
									{
										flag = true;
										temp2 = temp;
									}
								}
								if(flag)
								{
									if(!"√".equals(temp2.getAnimalCode())){
										temp2.setAnimalCode(temp2.getAnimalCode()+returnValue);
									}
									
								}else
								{
									entity.setAnimalCode(returnValue);
									if(!animalCodeStr.contains((String)mapObj.get("animalCode"))){
										animalCodeStr = animalCodeStr+"#"+(String)mapObj.get("animalCode")+"#";
										addsize++;
									}
									if(null != mapObj.get("visceraName") && !mapObj.get("visceraName").equals("")){
										list.add(entity);
									}
									
								}
								
							}else
							{
								//主脏器
								returnValue="自溶";
								entity.setAnimalCode(returnValue);
								if(!animalCodeStr.contains((String)mapObj.get("animalCode"))){
									animalCodeStr = animalCodeStr+"#"+(String)mapObj.get("animalCode")+"#";
									addsize++;
								}
								if(null != mapObj.get("visceraName") && !mapObj.get("visceraName").equals("")){
									list.add(entity);
								}
								
							}
						}
						 if(_missFlag!=null&&("Y".equals(_missFlag)||"Y2".equals(_missFlag)))
						{
							if("备注：".equals(remarks))
								remarks = "备注：（";
							if((String) mapObj.get("subVisceraName")!=null)
							{
								returnValue=(String)mapObj.get("subVisceraName")+_missType;
								
								if(missingRsn!=null)
									remarks+=" "+(String)mapObj.get("animalCode")+" "+(String)mapObj.get("subVisceraName")+_missType+":"+missingRsn+";";
								else 
									remarks+=" "+(String)mapObj.get("animalCode")+" "+(String)mapObj.get("subVisceraName")+_missType+";";
								boolean flag = false;
								VisceraFixDataForReport temp2 = null;
								for(VisceraFixDataForReport temp:list)
								{
									if(temp.getAnimalIndexCodeDate().equals((String)mapObj.get("animalCode"))&&temp.getVisceraName().substring(4).equals((String)mapObj.get("visceraName")))
									{
										flag = true;
										temp2=temp;
										
									}
								}
								if(flag)
								{
									System.out.println("===="+temp2.getAnimalCode()+returnValue);
									if(!"√".equals(temp2.getAnimalCode())){
										temp2.setAnimalCode(temp2.getAnimalCode()+returnValue);
									}
								}else
								{
									entity.setAnimalCode(returnValue);
									
									if(!animalCodeStr.contains((String)mapObj.get("animalCode"))){
										animalCodeStr = animalCodeStr+"#"+(String)mapObj.get("animalCode")+"#";
										addsize++;
									}
									if(null != mapObj.get("visceraName") && !mapObj.get("visceraName").equals("")){
										list.add(entity);
									}
									
									
								}
							
							
							}else
							{
								returnValue = _missType;
								entity.setAnimalCode(returnValue);
								
								if(missingRsn!=null)
									remarks+=" "+(String)mapObj.get("animalCode")+" "+(String)mapObj.get("visceraName")+_missType+":"+missingRsn+";";
								else 
									remarks+=" "+(String)mapObj.get("animalCode")+" "+(String)mapObj.get("visceraName")+_missType+";";
								
								if(!animalCodeStr.contains((String)mapObj.get("animalCode"))){
									animalCodeStr = animalCodeStr+"#"+(String)mapObj.get("animalCode")+"#";
									addsize++;
								}
								if(null != mapObj.get("visceraName") && !mapObj.get("visceraName").equals("")){
									list.add(entity);
								}
								
							}
							
						}
						
	
					
					
			   		}
			   		
			   		//填充空白格
			   		int remainder =addsize%8;
			   		if(addsize > 8){
			   			if(remainder != 0 ){
				   			for(int i = 1;i<(9-remainder);i++){
					   			VisceraFixDataForReport entity= new VisceraFixDataForReport();
								entity.setAnimalIndexCodeDate(9999+""+i);
								entity.setAnimalCode("—");
								entity.setVisceraName("9999固定完成时间");
								list.add(entity);
					   		}
				   		}
			   		}else{
			   			for(int i = 1;i<(9-addsize);i++){
				   			VisceraFixDataForReport entity= new VisceraFixDataForReport();
							entity.setAnimalIndexCodeDate(9999+""+i);
							entity.setAnimalCode("—");
							entity.setVisceraName("9999固定完成时间");
							list.add(entity);
				   		}
			   		}
			   		//填充时间
			   		for(Map<String,Object> mapObj: mapList1){
			   			VisceraFixDataForReport entity= new VisceraFixDataForReport();
						entity.setVisceraName("9999固定完成时间");
						entity.setAnimalIndexCodeDate((String)mapObj.get("animalCode"));
						entity.setAnimalCode(DateUtil.dateToString((Date)mapObj.get("visceraFixedFinishTime"),"HH:mm"));
						if( null != mapObj.get("visceraFixedFinishTime") && !mapObj.get("visceraFixedFinishTime").equals("")){
							list.add(entity);
						}
					
			   		}
			   		if(!"备注：".equals(remarks))
			   			remarks = remarks+ "）";
			   		else
			   			remarks += "NA"; 
			   		
			   		remarks = remarks+"\r\n";
			   	    map.put("remarks",remarks);//备注
					jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(list) );
			        Main.getInstance().openJFrame(jp, "动物脏器摘取和固定确认记录表（二）");	
		    	}
		    	
		    }
			
            return;
	}
	
	/**
	 * 编辑
	 * @param event
	 */
	@FXML
	private void onEditBtnAction(ActionEvent event){
		Integer sesssionType=BaseService.getInstance().getTblPathSessionService().getById(sessionIdList.get(0)).getSessionType();
		if(sesssionType==1 || sesssionType==5){
			String id = anatomyCheckTable.getSelectionModel().getSelectedItem().getId();
			try {
//				EditAnatomy.getInstance().start(getStage());
					Stage stage = Main.stageMap.get("EditAnatomy");
					if(null == stage){
						stage =new Stage();
						stage.initOwner(Main.mainWidow);
						stage.initModality(Modality.APPLICATION_MODAL);
						EditAnatomy.getInstance().start(stage);
						Main.stageMap.put("EditAnatomy",stage);
					}else{
						stage.show();
					}
				EditAnatomy.getInstance().loadData(id,"edit",null,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(sesssionType==2 || sesssionType==6){
			String id = visceraWeightTable.getSelectionModel().getSelectedItem().getWid();
			 try {
				 Stage stage = Main.stageMap.get("EditVisceraWeight");
					if(null == stage){
						stage =new Stage();
						stage.initOwner(Main.mainWidow);
						stage.initModality(Modality.APPLICATION_MODAL);
						EditVisceraWeight.getInstance().start(stage);
						Main.stageMap.put("EditVisceraWeight",stage);
					}else{
						stage.show();
					}
//				EditVisceraWeight.getInstance().start(getStage());
				EditVisceraWeight.getInstance().loadData(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(sesssionType==3 || sesssionType==7){
			Integer selectId = tabPane.getSelectionModel().getSelectedIndex();
			if(selectId == 0 ){
				String id = anatomyCheckTable.getSelectionModel().getSelectedItem().getId();
				try {
//					EditAnatomy.getInstance().start(getStage());
						Stage stage = Main.stageMap.get("EditAnatomy");
						if(null == stage){
							stage =new Stage();
							stage.initOwner(Main.mainWidow);
							stage.initModality(Modality.APPLICATION_MODAL);
							EditAnatomy.getInstance().start(stage);
							Main.stageMap.put("EditAnatomy",stage);
						}else{
							stage.show();
						}
					EditAnatomy.getInstance().loadData(id,"edit",null,null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(selectId == 1){
//		        	showMessage("脏器称重编辑！");
				 String id = visceraWeightTable.getSelectionModel().getSelectedItem().getWid();
				 try {
//					EditVisceraWeight.getInstance().start(getStage());
					Stage stage = Main.stageMap.get("EditVisceraWeight");
					if(null == stage){
						stage =new Stage();
						stage.initOwner(Main.mainWidow);
						stage.initModality(Modality.APPLICATION_MODAL);
						EditVisceraWeight.getInstance().start(stage);
						Main.stageMap.put("EditVisceraWeight",stage);
					}else{
						stage.show();
					}
					EditVisceraWeight.getInstance().loadData(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
		        	
	        }else if(selectId == 2){
	        	try{
					VisceraFixedEditPage.getInstance().start(getStage());
				}catch(Exception e){
					e.printStackTrace();
				}
	        }	
		}
		
		
	}
	
	/**
	 * 删除
	 * @param event
	 */
	@FXML
	private void delectBtnAction(ActionEvent event){
		Integer selectId = tabPane.getSelectionModel().getSelectedIndex();
        if(selectId == 0 ){
    		//签字窗口
//    		SignFrame signFrame = new SignFrame("");
//    		Stage stage = new Stage();
//    		stage.initModality(Modality.APPLICATION_MODAL);
//    		stage.setTitle("会话--身份验证");
//    		try {
//    			signFrame.start(stage);
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		}
    		//签字通过
    		if(Sign.openSignWithReasonFrame("删除原因", "解剖所见删除")){
//    			String userName = "";
//    					Date currentDate = new Date();
//    			User user = SignFrame.getUser();
//    			if(null != user){
//    				userName = user.getUserName();
//    			}

    			String id = anatomyCheckTable.getSelectionModel().getSelectedItem().getId();
            	TblAnatomyCheck tblAnatomyCheck = BaseService.getInstance().getTblAnatomyCheckService().getById(id);
            	String currentUserName = SaveUserInfo.getUserName();
//        		Date date = new Date();
            	Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
        		String reason = Sign.getReason();
        		
            	TblAnatomyCheckHis anatomyCheckHis = new TblAnatomyCheckHis();
    	  		  anatomyCheckHis.setId(BaseService.getInstance().getTblAnatomyCheckHisService().getKey());
    	  		  anatomyCheckHis.setAnatomyFindingCode(tblAnatomyCheck.getAnatomyFindingCode());
    	  		  anatomyCheckHis.setAnatomyFindingFlag(tblAnatomyCheck.getAnatomyFindingFlag());
    	  		  anatomyCheckHis.setAnatomyFingding(tblAnatomyCheck.getAnatomyFingding());
    	  		  anatomyCheckHis.setAnatomyPos(tblAnatomyCheck.getAnatomyPos());
    	  		  anatomyCheckHis.setAnatomyPosCode(tblAnatomyCheck.getAnatomyPosCode());
    	  		  anatomyCheckHis.setAnimalCode(tblAnatomyCheck.getAnimalCode());
    	  		  anatomyCheckHis.setAutolyzaFlag(tblAnatomyCheck.getAutolyzaFlag());
    	  		  anatomyCheckHis.setBodySurfacePos(tblAnatomyCheck.getBodySurfacePos());
    	  		  anatomyCheckHis.setColor(tblAnatomyCheck.getColor());
    	  		  anatomyCheckHis.setLesionDegree(tblAnatomyCheck.getLesionDegree());
    	  		  anatomyCheckHis.setNumber(tblAnatomyCheck.getNumber());
    	  		  anatomyCheckHis.setOldId(tblAnatomyCheck.getId());
    	  		  anatomyCheckHis.setOperateDate(date);
    	  		  anatomyCheckHis.setOperator(tblAnatomyCheck.getOperator());
    	  		  anatomyCheckHis.setOperateTime(tblAnatomyCheck.getOperateTime());
    	  		  anatomyCheckHis.setOperate("删除");
    	  		  anatomyCheckHis.setOperateRsn(reason);
    	  		  anatomyCheckHis.setOperater(currentUserName);
    	  		  anatomyCheckHis.setPos(tblAnatomyCheck.getPos());
    	  		  anatomyCheckHis.setRange(tblAnatomyCheck.getRange());
    	  		  anatomyCheckHis.setSessionId(tblAnatomyCheck.getSessionId());
    	  		  anatomyCheckHis.setShape(tblAnatomyCheck.getShape());
    	  		  anatomyCheckHis.setSize(tblAnatomyCheck.getSize());
    	  		  anatomyCheckHis.setSpecialFlag(tblAnatomyCheck.getSpecialFlag());
    	  		  anatomyCheckHis.setStudyNo(tblAnatomyCheck.getStudyNo());
    	  		  anatomyCheckHis.setSubVisceraCode(tblAnatomyCheck.getSubVisceraCode());
    	  		  anatomyCheckHis.setSubVisceraName(tblAnatomyCheck.getSubVisceraName());
    	  		  anatomyCheckHis.setTexture(tblAnatomyCheck.getTexture());
    	  		  anatomyCheckHis.setVisceraCode(tblAnatomyCheck.getVisceraCode());
    	  		  anatomyCheckHis.setVisceraName(tblAnatomyCheck.getVisceraName());
    	  		  anatomyCheckHis.setVisceraType(tblAnatomyCheck.getVisceraType());
    	  		  BaseService.getInstance().getTblAnatomyCheckHisService().save(anatomyCheckHis);
    	  		  
    			BaseService.getInstance().getTblAnatomyCheckService().deleteOne_1(id);
    			
    			String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
    			String sessiond = taskIdSessionIdMap.get(taskId);
    			updateAnatomyCheckTable(sessiond);//初始化解剖所见列表
    			showMessage("删除成功！");
    			
    		}

        	
        
        }
//        else if(selectId == 1){
//        	String id = visceraWeightTable.getSelectionModel().getSelectedItem().getWid();
//        	BaseService.getInstance().getTblVisceraWeightService().delete(id);
//        	String taskId = anatomyTaskTable.getSelectionModel().getSelectedItem().getTaskId();
//			String sessiond = taskIdSessionIdMap.get(taskId);
//        	updateVisceraWeight(sessiond);
//        	showMessage("删除成功！");
//        }else if(selectId == 2){
////    		Tab t=tabPane.getSelectionModel().getSelectedItem();
//    			VisceraFixed visceraFixed=visceraFixedTable.getSelectionModel().getSelectedItem();
////    			int index=visceraFixedTable.getSelectionModel().getSelectedIndex();
//    			if(null!=visceraFixed){
//    				String id=visceraFixed.getId();
//    				Alert2.create(id);return;
//    			}else{
//    				Alert2.create("请选择要删除的数据");
//    			}
//        	
//        }
			
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
	
	
	/**固定记录
	 * @author Administrator
	 *
	 */
	public static class VisceraFixed{
		private StringProperty id;			//id
		private StringProperty studyNo;     //课题编号
		private StringProperty sessionId;	//会话id
		private StringProperty animalCode;  //动物编号
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty operator;	//操作者
		private StringProperty operateTime; //操作时间
		
		public VisceraFixed() {
			super();
		}
		public VisceraFixed(String id,String studyNo,String sessionId, String animalCode,  String visceraName,String operator,String operateTime) {
			this.id = new SimpleStringProperty(id);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.animalCode= new SimpleStringProperty(animalCode);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.operator = (new SimpleStringProperty(operator));
			this.operateTime = (new SimpleStringProperty(operateTime));
		}
		
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getStudyNo(){
			return studyNo.get();
		}
		public void setStudyNo(String studyNo){
			this.studyNo = new SimpleStringProperty(studyNo);
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
		public String getOperateTime() {
			return operateTime.get();
		}
		public void setOperateTime(String operateTime) {
			this.operateTime = new SimpleStringProperty(operateTime);
		}
		public String getOperator() {
			return operator.get();
		}
		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		
		
		
	}
	public static class VisceraFixedCompare{
		private StringProperty id;			//id
		private StringProperty studyNo;     //课题编号
		private StringProperty sessionId;	//会话id
		private StringProperty animalCode;  //动物编号
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty subVisceraCode;  
		private StringProperty subVisceraName;	
		private StringProperty operator;	//操作者
		private StringProperty operateTime; //操作时间
		private StringProperty _fixedFlag ;
		private StringProperty _autolyzaFlag;
		private StringProperty _missFlag ;
		private StringProperty _missType ;
		private StringProperty missingRsn ;
		
		public VisceraFixedCompare () {
			super();
		}
		public VisceraFixedCompare  (String id,String studyNo,String sessionId, String animalCode,  String visceraName,String subVisceraCode,String subVisceraName,String operator,String operateTime
				,String  _fixedFlag,String _autolyzaFlag,String _missFlag,String _missType,String missingRsn) {
			this.id = new SimpleStringProperty(id);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.animalCode= new SimpleStringProperty(animalCode);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.operator = (new SimpleStringProperty(operator));
			this.operateTime = (new SimpleStringProperty(operateTime));
			this._fixedFlag = (new SimpleStringProperty(_fixedFlag));
			this._autolyzaFlag = (new SimpleStringProperty(_autolyzaFlag));
			this._missFlag = (new SimpleStringProperty(_missFlag));
			this._missType = (new SimpleStringProperty(_missType));
			this. missingRsn = (new SimpleStringProperty( missingRsn));
			this.subVisceraCode=(new SimpleStringProperty( subVisceraCode));
			this.subVisceraName=(new SimpleStringProperty( subVisceraName));
			
		}
		
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getStudyNo(){
			return studyNo.get();
		}
		public void setStudyNo(String studyNo){
			this.studyNo = new SimpleStringProperty(studyNo);
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
		public String getOperateTime() {
			return operateTime.get();
		}
		public void setOperateTime(String operateTime) {
			this.operateTime = new SimpleStringProperty(operateTime);
		}
		public String getOperator() {
			return operator.get();
		}
		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String get_fixedFlag() {
			return _fixedFlag.get();
		}
		public void set_fixedFlag(String _fixedFlag) {
			this._fixedFlag = new SimpleStringProperty(_fixedFlag);
		}
		public String get_autolyzaFlag() {
			return _autolyzaFlag.get();
		}
		public void set_autolyzaFlag(String _autolyzaFlag) {
			this._autolyzaFlag = new SimpleStringProperty(_autolyzaFlag);
		}
		public String get_missFlag() {
			return _missFlag.get();
		}
		public void set_missFlag(String _missFlag) {
			this._missFlag = new SimpleStringProperty(_missFlag);
		}
		public String getMissingRsn() {
			return missingRsn.get();
		}
		public void setMissingRsn(String missingRsn) {
			this.missingRsn = new SimpleStringProperty(missingRsn);
		}
		public String get_missType() {
			return _missType.get();
		}
		public void set_missType(String _missType) {
			this._missType = new SimpleStringProperty(_missType);
		}
		public String getSubVisceraCode() {
			return subVisceraCode.get();
		}
		public void setSubVisceraCode(String subVisceraCode) {
			this.subVisceraCode = new SimpleStringProperty(subVisceraCode);
		}
		public String getSubVisceraName() {
			return subVisceraName.get();
		}
		public void setSubVisceraName(String subVisceraName) {
			this.subVisceraName = new SimpleStringProperty(subVisceraName);
		}
		
		
		
	}
	/**
	 * 脏器称重
	 *
	 */
	public static class visceraWeight{
		private StringProperty wid;//主键
		private StringProperty wsessionId;//会话ID
		private StringProperty wstudyNo;//专题编号
		private StringProperty wanimalCode;//动物编号
		private StringProperty wvisceraName;//脏器名称
		private StringProperty weight;//体重
		private StringProperty weightUnit;//体重单位
		private StringProperty fixedWeighFlag;//是否固定后称重   0：否；1：是
		private StringProperty attachedVisceraNames;//附加脏器列表 
		private StringProperty woperator;//操作者
		private StringProperty woperateTime;//操作时间
		private StringProperty balCode;//采集设备编号
		
		public visceraWeight() {
			super();
		}
		
		public visceraWeight(String wid,String wsessionId, String wstudyNo, String wanimalCode,
				String wvisceraName, String weight,String weightUnit,Integer fixedWeighFlag,
				String attachedVisceraNames,String woperator,String woperateTime,String balCode) {
			this.wid = new SimpleStringProperty(wid);
			this.wsessionId = new SimpleStringProperty(wsessionId);
			this.wstudyNo = new SimpleStringProperty(wstudyNo);
			this.wanimalCode = new SimpleStringProperty(wanimalCode);
			this.wvisceraName = new SimpleStringProperty(wvisceraName);
			this.weight = new SimpleStringProperty(weight);
			this.weightUnit = new SimpleStringProperty(weightUnit);
//			this.weightUnit = new SimpleStringProperty(weightUnit);
			String fixedWeighFlagStr = "";
			if(fixedWeighFlag == 0){
				fixedWeighFlagStr = "否";
			}else{
				fixedWeighFlagStr = "是";
			}
			this.fixedWeighFlag = new SimpleStringProperty(fixedWeighFlagStr);
			this.attachedVisceraNames = new SimpleStringProperty(attachedVisceraNames);
			this.woperator = new SimpleStringProperty(woperator);
			this.woperateTime = new SimpleStringProperty(woperateTime);
			this.balCode = new SimpleStringProperty(balCode);
		}

		public String getWid() {
			return wid.get();
		}

		public void setWid(String wid) {
			this.wid =  new SimpleStringProperty(wid);
		}
        
		public String getWsessionId() {
			return wsessionId.get();
		}

		public void setWsessionId(String wsessionId) {
			this.wid =  new SimpleStringProperty(wsessionId);
		}
		
		public String getWstudyNo() {
			return wstudyNo.get();
		}

		public void setWstudyNo(String wstudyNo) {
			this.wstudyNo =  new SimpleStringProperty(wstudyNo);
		}

		public String getWanimalCode() {
			return wanimalCode.get();
		}

		public void setWanimalCode(String wanimalCode) {
			this.wanimalCode =  new SimpleStringProperty(wanimalCode);
		}

		public String getWvisceraName() {
			return wvisceraName.get();
		}

		public void setWvisceraName(String wvisceraName) {
			this.wvisceraName =  new SimpleStringProperty(wvisceraName);
		}

		public String getWeight() {
			return weight.get();
		}

		public void setWeight(String weight) {
			this.weight =  new SimpleStringProperty(weight);
		}

		public StringProperty getWeightUnit() {
			return weightUnit;
		}

		public void setWeightUnit(StringProperty weightUnit) {
			this.weightUnit = weightUnit;
		}

		public String getFixedWeighFlag() {
			return fixedWeighFlag.get();
		}

		public void setFixedWeighFlag(String fixedWeighFlag) {
			this.fixedWeighFlag =  new SimpleStringProperty(fixedWeighFlag);
		}

		public String getAttachedVisceraNames() {
			return attachedVisceraNames.get();
		}

		public void setAttachedVisceraNames(String attachedVisceraNames) {
			this.attachedVisceraNames =  new SimpleStringProperty(attachedVisceraNames);
		}

		public String getWoperator() {
			return woperator.get();
		}

		public void setWoperator(String woperator) {
			this.woperator =  new SimpleStringProperty(woperator);
		}

		public String getWoperateTime() {
			return woperateTime.get();
		}

		public void setWoperateTime(String woperateTime) {
			this.woperateTime =  new SimpleStringProperty(woperateTime);
		}

		public String getBalCode() {
			return balCode.get();
		}

		public void setBalCode(String balCode) {
			this.balCode =  new SimpleStringProperty(balCode);
		}
		
	}
	
	/**
	 * 解剖所见
	 * @author Administrator
	 *
	 */
	public static class AnatomyCheck{
		private StringProperty id;//主键
		private StringProperty sessionId;  //会话ID
		private StringProperty astudyNo;//专题编号
		private StringProperty animalCode;//动物编号
		private StringProperty visceraName;//脏器名称
		private StringProperty anatomyFinding;//解剖所见
		private StringProperty operator;//操作者
		private StringProperty operateTime;//操作时间
		private StringProperty anatomyOperator;	//解剖者
		
		/*private StringProperty anatomyPos;         //解剖学所见部位60
		private StringProperty pos;                //位置60
		private StringProperty shape;              //形状60
		private StringProperty color;              //颜色60
		private StringProperty texture;            //硬度60
		private StringProperty number;             //数量60
		private StringProperty range;              //范围60
		private StringProperty lesionDegree;       //病变程度60
		private StringProperty size;				//大小 20
		private StringProperty bodySurfacePos;     //体表部位60
*/		
		public AnatomyCheck(String animalCode, String visceraName, String anatomyFinding,
				String operator, String operateTime,String astudyNo,String id,String sessionId
				,String anatomyOperator) {
			this.id = new SimpleStringProperty(id);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.animalCode = new SimpleStringProperty(animalCode);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.anatomyFinding = new SimpleStringProperty(anatomyFinding);
			this.operator = new SimpleStringProperty(operator);
			this.operateTime = new SimpleStringProperty(operateTime);
			this.astudyNo = new SimpleStringProperty(astudyNo);
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
			
		}
		
		
		

		

		public AnatomyCheck() {
			super();
		}
		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getAstudyNo() {
			return astudyNo.get();
		}

		public void setAstudyNo(String astudyNo) {
			this.astudyNo = new SimpleStringProperty(astudyNo);
		}

		public String getAnimalCode() {
			return animalCode.get();
		}

		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}

		public String getVisceraName() {
			return visceraName.get();
		}

		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}

		public String getAnatomyFinding() {
			return anatomyFinding.get();
		}

		public void setAnatomyFinding(String anatomyFinding) {
			this.anatomyFinding = new SimpleStringProperty(anatomyFinding);
		}

		public String getOperator() {
			return operator.get();
		}

		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}

		public String getOperateTime() {
			return operateTime.get();
		}

		public void setOperateTime(String operateTime) {
			this.operateTime = new SimpleStringProperty(operateTime);
		}

		public String getSessionId() {
			return sessionId.get();
		}

		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
		}

		public String getAnatomyOperator() {
			return anatomyOperator.get();
		}

		public void setAnatomyOperator(String anatomyOperator) {
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
		}

	
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
