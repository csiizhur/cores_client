package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblVisceraWeight;
import com.lanen.model.path.VisceraWeightDataForReport;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;
import com.lanen.view.main.SignMeFrame;

/**补录数据
 * @author Administrator
 *
 */
public class AdditionalPage extends Application implements Initializable {
	
	
	/**
	 * 专题编号
	 */
	private String studyNo = "";
	
	@FXML
	private Button additionalTaskBtn;
	@FXML
	private Button editTaskBtn;
	@FXML
	private Button anatomyCheckBtn;
	@FXML
	private Button weighBtn;
	@FXML
	private Button signBtn;
	
	@FXML
	private ComboBox<String> studyNoCombobox;
	private ObservableList<String> data_studyNoCombobox = FXCollections.observableArrayList();
	
	/**
	 * 任务表
	 */
	@FXML
	private TableView<Task> taskTable;
	ObservableList<Task> data_taskTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Task,String> anatomyDateCol;
	@FXML
	private TableColumn<Task,String> animalNumCol;
	@FXML
	private TableColumn<Task,String> signerCol;
	@FXML
	private TableColumn<Task,String> anatomyRsnCol;
	
	/**
	 * 已选动物表格
	 */
	@FXML
	private TableView<Animal> animalTable;
	ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Animal,String> animalCodeCol;
	@FXML
	private TableColumn<Animal,String> genderCol;
	@FXML
	private TableColumn<Animal,String> dosageDescCol;
	@FXML
	private TableColumn<Animal,String> anatomyDateCol_1;
	
	/*
	 * 脏器称重记录   table
	 */
	@FXML 
	private TableView<VisceraWeight> visceraWeightTable;
	private ObservableList<VisceraWeight> data_visceraWeightTable = FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<VisceraWeight,String> animalCodeCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,String> viscerNameCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,String> weightCol_visceraWeightTable;
	
	/**
	 * 解剖结果TableView
	 */
	@FXML
	private TableView<AnatomyCheck> anatomyResultTable; // 解剖结果TableView
	private ObservableList<AnatomyCheck> data_anatomyResultTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnatomyCheck, String> animalCodeCol_anatomycheck; // 脏器column
	@FXML
	private TableColumn<AnatomyCheck, String> visceraNameCol; // 脏器column
	@FXML
	private TableColumn<AnatomyCheck, String> findingCol; // 解剖所见column
	
	private static AdditionalPage instance;
	public static AdditionalPage getInstance(){
		if(null == instance){
			instance = new AdditionalPage();
		}
		return instance;
	}

	public AdditionalPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		//1.初始化专题编号ComboBox
		initStudyNoCombobox();
		//2.初始化任务表
		initTaskTable();
		//3.初始化动物表
		initAnimalTable();
		//4.初始化称重记录表格
		initVisceraWeightTable();
		//5. 初始化 解剖信息表（anatomyResultTable）
		initAnatomyResultTalbe();
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
		animalCodeCol_anatomycheck.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("animalCode"));
		findingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("finding"));
	}
	
	/**
	 * 初始化称重记录表格
	 */
	private void initVisceraWeightTable() {
		visceraWeightTable.setItems(data_visceraWeightTable);
		visceraWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		viscerNameCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("visceraName"));
		animalCodeCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("animalCode"));
		weightCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("weight"));
	}
	
	/**
	 * 初始化专题编号ComboBox
	 */
	private void initStudyNoCombobox() {
		studyNoCombobox.setItems(data_studyNoCombobox);
		studyNoCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					studyNo = newValue;
				}else{
					studyNo = "";
				}
				// TODO 更新解剖任务表
				updateTaskTable();
			}
		});
	}

	
	
	/**
	 * 初始化任务表
	 */
	private void initTaskTable() {
		taskTable.setItems(data_taskTable);
		taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		anatomyDateCol.setCellValueFactory(new PropertyValueFactory<Task,String>("anatomyDate"));
		animalNumCol.setCellValueFactory(new PropertyValueFactory<Task,String>("animalNum"));
		signerCol.setCellValueFactory(new PropertyValueFactory<Task,String>("signer"));
		anatomyRsnCol.setCellValueFactory(new PropertyValueFactory<Task,String>("anatomyRsn"));
		taskTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>(){

			@Override
			public void changed(ObservableValue<? extends Task> arg0, Task arg1,
					Task newValue) {
				if(null != newValue){
					//更新动物表 TODO
					updateAnimalTable(newValue.getTaskId());
					
					updateAnatomyResultTableData(newValue.getTaskId());
					updateVisceraWeightTable(newValue.getTaskId());
					boolean isnull = isNull(newValue.getSigner());
					resetBtn(isnull,true);
				}else{
					resetBtn(false,false);
					updateAnimalTable(null);
					
					updateAnatomyResultTableData(null);
					updateVisceraWeightTable(null);
				}
			}});
	}

	/**
	 * 初始化动物表
	 */
	private void initAnimalTable() {
		animalTable.setItems(data_animalTable);
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		anatomyDateCol_1.setCellValueFactory(new PropertyValueFactory<Animal,String>("anatomyDate"));
		//动物性别居中
		genderCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
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
	}
	
	/**更新解剖结果表格（anatomyResultTalbe）
	 * @param taskId
	 */
	private void updateAnatomyResultTableData(String taskId){
		//TODO 
		data_anatomyResultTable.clear();
		if(null != taskId && !"".equals(taskId)){
			List<TblAnatomyCheck> tblAnatomyCheckList = BaseService.getInstance()
					.getTblAnatomyCheckService().getListByTaskId(taskId);
			if(null != tblAnatomyCheckList){
				for(TblAnatomyCheck tblAnatomyCheck:tblAnatomyCheckList){
					String id = tblAnatomyCheck.getId();
					String animalCode = tblAnatomyCheck.getAnimalCode();
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
					data_anatomyResultTable.add(new AnatomyCheck(id,animalCode,visceraName,finding,false));
				}
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
	
	/**更新表格数据并选中指定项
	 * @param animalCode
	 * @param visceraWeighId
	 */
	private void updateVisceraWeightTable(String taskId) {
		data_visceraWeightTable.clear();
		if(null != taskId){
			List<TblVisceraWeight> tblVisceraWeightList = BaseService.getInstance().getTblVisceraWeightService().getListByTaskId(taskId);
			if(null != tblVisceraWeightList && tblVisceraWeightList.size() > 0){
				for(TblVisceraWeight obj:tblVisceraWeightList){
					String visceraName = obj.getVisceraName();
					String subVisceraName = obj.getSubVisceraName();
					int attachedVisceraFlag = obj.getAttachedVisceraFlag();
					String attachedVisceraNames = obj.getAttachedVisceraNames();
					if(attachedVisceraFlag == 1){
						visceraName = visceraName+"("+attachedVisceraNames+")";
					}
					data_visceraWeightTable.add(new VisceraWeight(obj.getAnimalCode(),visceraName,
							subVisceraName,obj.getWeight(),obj.getWeightUnit(),false));
				}
			}
		}
	}

	/**补录解剖任务
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
//		打开    创建组织取材_选择切片类型   窗口
		Stage stage = Main.stageMap.get("AdditionalPage_task");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			Main.stageMap.put("AdditionalPage_task",stage);
		}
		try {
			AdditionalPage_task.getInstance().start(stage);
			AdditionalPage_task.getInstance().loadData(studyNo,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**补录解剖任务
	 * @param event
	 */
	@FXML
	private void onEditAdditionalTaskAction(ActionEvent event){
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			Stage stage = Main.stageMap.get("AdditionalPage_task");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				Main.stageMap.put("AdditionalPage_task",stage);
			}
			try {
				AdditionalPage_task.getInstance().start(stage);
				AdditionalPage_task.getInstance().loadData(studyNo,task.getTaskId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
		
	}
	/**补录解剖所见
	 * @param event
	 */
	@FXML
	private void onAdditionalAnatomyCheckAction(ActionEvent event){
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			Stage stage = Main.stageMap.get("AdditionalPage_anatomy");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				Main.stageMap.put("AdditionalPage_anatomy",stage);
			}
			try {
				AdditionalPage_anatomy.getInstance().start(stage);
				AdditionalPage_anatomy.getInstance().loadData(studyNo,task.getTaskId());
				stage.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			taskTable.getSelectionModel().clearSelection();
			taskTable.getSelectionModel().select(task);
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
	}

	/**补录称重数据
	 * @param event
	 */
	@FXML
	private void onAdditionalVisceraWeighAction(ActionEvent event){
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			Stage stage = Main.stageMap.get("AdditionalPage_weigh");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				Main.stageMap.put("AdditionalPage_weigh",stage);
			}
			try {
				AdditionalPage_weigh.getInstance().start(stage);
				AdditionalPage_weigh.getInstance().loadData(studyNo,task.getTaskId());
				stage.showAndWait();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			taskTable.getSelectionModel().clearSelection();
			taskTable.getSelectionModel().select(task);
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
		
	}
	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignConfirmAction(ActionEvent event){
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			
			if(null ==data_animalTable || data_animalTable.size() < 1){
				showErrorMessage("请先补录动物信息！");
				return;
			}
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("签字确认");
			try {
				signMeFrame.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				//复核人 签字窗口
				//签字窗口
				SignFrame signFrame = new SignFrame("");
				Stage stage2 = new Stage();
				stage2.initModality(Modality.APPLICATION_MODAL);
				stage2.setTitle("复核人 身份验证");
				try {
					signFrame.start(stage2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//签字通过
				if("OK".equals(SignFrame.getType())){
						
						User user = SignFrame.getUser();
						String peerRealName = "";
						if(null != user ){
							peerRealName = user.getRealName();
							if(!SaveUserInfo.getRealName().equals(user.getRealName())){
								
								//补录任务签字签字确认
								BaseService.getInstance().getTblAnatomyTaskService().additionalTaskSign(task.getTaskId(),SaveUserInfo.getRealName(),peerRealName);
								updateTaskTable(task.getTaskId());
								
							}else{
								showErrorMessage("复核人和录入人不可以是同一个人！");
							}
						}else{
							showErrorMessage("请选择复核人！");
						}
					
					
					
				
				}

			}
			
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
	}
	
	/**打印补录解剖所见
	 * @param event
	 * @throws JRException 
	 */
	@FXML
	private void onSignPrintAction(ActionEvent event) throws JRException{
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			
			if(null ==data_animalTable || data_animalTable.size() < 1){
				showErrorMessage("请先补录动物信息！");
				return;
			}
		//	task.setTempFlag(2);//补录
			
			//打印补录数据
			JasperReport jr = null;
	        JasperPrint jp = null;
	        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("additionalCheck.jrxml"));
	        Map<String,Object> map = new HashMap<String,Object>();
	      
	        map.put("taskId",task.getAnatomyDate());//任务
	        
	      
	        URL url = this.getClass().getResource("/image/logo.jpg");
	   		map.put("logoImage", url);
            String weightUnit = "";
	   		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	   		for(AnatomyCheck obj:data_anatomyResultTable){
	   		//报表  表格里的数据
	   			Map<String,Object> entity = new HashMap<>();
	   		//	animalCode,visceraName,finding
				entity.put("animalCode",obj.getAnimalCode());
				entity.put("" +
						"visceraName",obj.getVisceraName());
				entity.put("finding",obj.getFinding());
			
				list.add(entity);
	   		}
	   		
			try {
				jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(list) );
				Main.getInstance().openJFrame(jp, "补录解剖数据表");	
			} catch (JRException e) {
				showMessage("参数错误！");
			}
		
			
				
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
	}
	
	/**打印补录称重
	 * @param event
	 * @throws JRException 
	 */
	@FXML
	private void onSignPrint2Action(ActionEvent event) throws JRException{
		Task task = taskTable.getSelectionModel().getSelectedItem();
		if(null != task){
			
			if(null ==data_animalTable || data_animalTable.size() < 1){
				showErrorMessage("请先补录动物信息！");
				return;
			}
		//	task.setTempFlag(2);//补录
			
			//打印补录数据
			JasperReport jr = null;
	        JasperPrint jp = null;
	        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("additionalWeight.jrxml"));
	        Map<String,Object> map = new HashMap<String,Object>();
	      
	        map.put("taskId",task.getAnatomyDate());//任务id
	        
	      
	        URL url = this.getClass().getResource("/image/logo.jpg");
	   		map.put("logoImage", url);
	   		
	   		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	   		for(VisceraWeight obj:data_visceraWeightTable){
	   		//报表  表格里的数据
	   			Map<String,Object> entity = new HashMap<String,Object>();
	   			// VisceraWeight(obj.getAnimalCode(),visceraName,	subVisceraName,obj.getWeight(),obj.getWeightUnit(),false));
	   			entity.put("animalCode",obj.getAnimalCode());
	   			entity.put("visceraName", obj.getVisceraName());
	   			entity.put("weight", obj.getWeight());
			
				list.add(entity);
	   		}
			jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(list) );
	        Main.getInstance().openJFrame(jp, "补录称重数据表");	
		
			
				
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
	}
	
	
	/**更新专题编号ComboBox ，选中对应任务
	 * @param studyNo
	 * @param taskId
	 */
	public void updateStudyNoComboBoxAndSelectTask(String studyNo,String taskId){
		//1.更新专题编号ComboBox
		updateStudyNoComboBox();
		//2.选中对应专题编号
		studyNoCombobox.getSelectionModel().select(studyNo);
		//3.选中对应任务
		for(Task obj :data_taskTable){
			if(taskId.equals(obj.getTaskId())){
				taskTable.getSelectionModel().select(obj);
				break;
			}
		}
	}
	
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		
		//0.重置
		resetBtn(false,false);
		//1.更新专题编号ComboBox
		updateStudyNoComboBox();
		
//		//2.如何有专题，默认选最后一个
//		if(data_studyNoCombobox.size() > 0){
//			studyNoCombobox.getSelectionModel().selectLast();
//		}
		if(!data_studyNoCombobox.contains(studyNo)){
			data_studyNoCombobox.add(studyNo);
		}
		studyNoCombobox.getSelectionModel().select(studyNo);
	}
	
	/**
	 * 按钮状态重置 true :可编辑
	 */
	/**按钮状态重置 
	 * @param flag true :可编辑
	 * @param selectedData 已选中数据
	 */
	private void resetBtn(boolean flag,boolean selectedData) {
		
		if(selectedData){
			editTaskBtn.setDisable(false);
			anatomyCheckBtn.setDisable(false);
			weighBtn.setDisable(false);
		}else{
			editTaskBtn.setDisable(true);
			anatomyCheckBtn.setDisable(true);
			weighBtn.setDisable(true);
		}
		if(flag){
			editTaskBtn.setText("编辑解剖任务");
			anatomyCheckBtn.setText("补录解剖所见");
			weighBtn.setText("补录称重数据");
			signBtn.setDisable(false);
		}else{
			editTaskBtn.setText("查看解剖任务");
			anatomyCheckBtn.setText("查看解剖所见");
			weighBtn.setText("查看称重数据");
			signBtn.setDisable(true);
		}
	}

	/**
	 * 更新专题编号ComboBox（有补录解剖任务的专题编号列表）
	 */
	private void updateStudyNoComboBox() {
		data_studyNoCombobox.clear();
		List<String> studyNoList = BaseService.getInstance().getTblAnatomyTaskService().getStudyNoListHasAdditionalTask();
		if(null != studyNoList && studyNoList.size() > 0){
			for(String studyNo:studyNoList){
				data_studyNoCombobox.add(studyNo);
			}
		}
	}

	/**
	 * 更新任务表
	 */
	private void updateTaskTable(){
		data_taskTable.clear();
		if(null != studyNo && !"".equals(studyNo)){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyTaskService().getAdditionalTaskMapListByStudyNo(studyNo);
			if(null != mapList){
				for(Map<String,Object> obj:mapList){
					String taskId = (String) obj.get("taskId");
					String anatomyDate = (String) obj.get("anatomyDate");
					Integer animalNum = (Integer) obj.get("animalNum");
					String signer = (String) obj.get("signer");
					String anatomyRsn = (String) obj.get("anatomyRsn");
					data_taskTable.add(new Task(taskId,anatomyDate,animalNum+"",signer,anatomyRsn));
				}
			}
		}
	}
	/**
	 * 更新任务表
	 */
	private void updateTaskTable(String selectedTaskId){
		data_taskTable.clear();
		if(null != studyNo && !"".equals(studyNo)){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyTaskService().getAdditionalTaskMapListByStudyNo(studyNo);
			Task selectedTask = null;
			if(null != mapList){
				for(Map<String,Object> obj:mapList){
					String taskId = (String) obj.get("taskId");
					String anatomyDate = (String) obj.get("anatomyDate");
					Integer animalNum = (Integer) obj.get("animalNum");
					String signer = (String) obj.get("signer");
					String anatomyRsn = (String) obj.get("anatomyRsn");
					Task task = new Task(taskId,anatomyDate,animalNum+"",signer,anatomyRsn);
					data_taskTable.add(task);
					if(taskId.equals(selectedTaskId)){
						selectedTask = task;
					}
				}
			}
			if(null != selectedTask){
				taskTable.getSelectionModel().select(selectedTask);
			}
		}
	}
	/**
	 * 更新动物表
	 */
	public void updateAnimalTable(String taskId){
		data_animalTable.clear();
		if(null != taskId && !"".equals(taskId)){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyTaskService().getSelectAnimalByTaskId(taskId);
			if(null != mapList && mapList.size() > 0){
				for(Map<String, Object> obj:mapList){
//					animalCode,gender,dosageNum,dosageDesc,anatomyDate
					String animalCode = (String) obj.get("animalCode");
					String dosageDesc = (String) obj.get("dosageDesc");
					Integer gender = (Integer) obj.get("gender");
					String anatomyDate = (String) obj.get("anatomyDate");
					data_animalTable.add(new Animal(animalCode,gender,dosageDesc,anatomyDate));
				}
			}
		}
	}
	

	/**补录解剖任务表
	 * @author Administrator
	 *
	 */
	public class Task{
		private StringProperty taskId;
		private StringProperty anatomyDate;
		private StringProperty animalNum;
		private StringProperty signer;
		private StringProperty anatomyRsn;
		
		public Task(){}
		public Task(String taskId,String anatomyDate,String animalNum,String signer,String anatomyRsn){
			setTaskId(taskId);
			setAnatomyDate(anatomyDate);
			setAnimalNum(animalNum);
			setSigner(signer);
			setAnatomyRsn(anatomyRsn);
		}
		public String getTaskId() {
			return taskId.get();
		}
		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}
		public String getAnatomyDate() {
			return anatomyDate.get();
		}
		public void setAnatomyDate(String anatomyDate) {
			this.anatomyDate = new SimpleStringProperty(anatomyDate);
		}
		public String getAnimalNum() {
			return animalNum.get();
		}
		public void setAnimalNum(String animalNum) {
			this.animalNum = new SimpleStringProperty(animalNum);
		}
		public String getSigner() {
			return signer.get();
		}
		public void setSigner(String signer) {
			this.signer = new SimpleStringProperty(signer);
		}
		public String getAnatomyRsn() {
			return anatomyRsn.get();
		}
		public void setAnatomyRsn(String anatomyRsn) {
			this.anatomyRsn = new SimpleStringProperty(anatomyRsn);
		}
	}
	
	/**动物
	 * @author Administrator
	 *
	 */
	public class Animal{
		private StringProperty dosageDesc;
		private StringProperty animalCode;
		private StringProperty gender;
		private StringProperty anatomyDate;
		
		public Animal(){}
		public Animal(String animalCode,int gender,String dosageDesc,String anatomyDate){
			setDosageDesc(dosageDesc);
			setAnimalCode(animalCode);
			setGender(gender == 1 ? "♂":"♀" );
			setAnatomyDate(anatomyDate);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getGender() {
			return gender.get();
		}
		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}
		public String getAnatomyDate() {
			return anatomyDate.get();
		}
		public void setAnatomyDate(String anatomyDate) {
			this.anatomyDate = new SimpleStringProperty(anatomyDate);
		}
		public String getDosageDesc() {
			return dosageDesc.get();
		}
		public void setDosageDesc(String dosageDesc) {
			this.dosageDesc = new SimpleStringProperty(dosageDesc);
		}
	}
	
	/**取材批次脏器
	 * @author Administrator
	 *
	 */
	public class BatchViscera{
		private StringProperty sliceCode;
		private StringProperty visceraOrTissueName;
		
		private BatchViscera(){}
		private BatchViscera(String sliceCode,
				String visceraOrTissueName){
			setSliceCode(sliceCode);
			setVisceraOrTissueName(visceraOrTissueName);
		}
		public String getSliceCode() {
			return sliceCode.get();
		}
		public void setSliceCode(String sliceCode) {
			this.sliceCode = new SimpleStringProperty(sliceCode);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}
	}
	
	/**剖检记录
	 * @author Administrator
	 *
	 */
	public static class AnatomyCheck{
		private StringProperty id;			//id
		private StringProperty animalCode;	//会话id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty finding;		//解剖所见
		private BooleanProperty  operate;	//操作
		
		public AnatomyCheck() {
			super();
		}
		public AnatomyCheck(String id, String animalCode, String visceraName, String finding,
				boolean operate) {
			this.id = new SimpleStringProperty(id);
			this.animalCode = new SimpleStringProperty(animalCode);
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
	/**
	 * 脏器称重记录
	 */
	public static class VisceraWeight{
		private StringProperty animalCode;			//id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty weight;		//重量
		private BooleanProperty  operate;	//操作
		public VisceraWeight(){}
		public VisceraWeight(String animalCode,String visceraName,String subVisceraName,
				String weight,String weightUnit,boolean operate){
			setAnimalCode(animalCode);
			if(null != subVisceraName && !"".equals(subVisceraName) ){
				setVisceraName(subVisceraName);
			}else{
				setVisceraName(visceraName);
			}
			setWeight(weight+" "+weightUnit);
			setOperate(operate);
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
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AdditionalPage.fxml"));
		Scene scene = new Scene(root, 1024, 660);
		stage.setScene(scene);
		stage.setTitle("补录解剖数据");
		stage.setMinWidth(1024);
		stage.setMinHeight(660);
		
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight());
		
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
}
