package com.lanen.view.test;

import java.net.URL;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.SignMeFrame;

public class TaskInfoPage extends Application implements Initializable {

	@FXML
	private Label studyNoLabel;			//专题编号   Label
//	@FXML
//	private Label sdLabel;				//SD   Label
	@FXML
	private Label reqDateLabel;			//申请日期   Label
	@FXML
	private Label animalTypeLabel;		//动物种类Label
	@FXML
	private Label animalNumLabel;		//动物数量Label
	@FXML
	private Label anatomyRsnLabel;		//解剖原因Label
	@FXML
	private Label taskCreaterLabel;		//任务创建者Label
	@FXML
	private Label taskCreateTimeLabel;	//任务创建日期Label
	@FXML
	private Label planAnatomyDateLabel;	//任务创建日期Label
	
	@FXML
	private TableView<TaskInfo> taskinfoTable; // 解剖结果TableView
	private ObservableList<TaskInfo> data_taskinfoTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TaskInfo, String> taskNameCol; // 脏器column
	@FXML
	private TableColumn<TaskInfo, String> signerCol; // 签字者column
	@FXML
	private TableColumn<TaskInfo, String> signDateCol; // 签字日期column
	@FXML
	private TableColumn<TaskInfo, Boolean> operateCol; // 操作column
	
	private static String taskId = "";
	/**
	 * 是否有签字权限
	 */
	private static boolean hasSignPrivilege = false;
	
	private static TaskInfoPage instance;
	public static TaskInfoPage getInstance(){
		if(null == instance){
			instance = new TaskInfoPage();
		}
		return instance;
	}
	
	public TaskInfoPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTaskInfoTable();
	}
	
	/**
	 * 初始化table
	 */
	private void initTaskInfoTable() {
		data_taskinfoTable.clear();
		taskinfoTable.setItems(data_taskinfoTable);
		taskNameCol.setCellValueFactory(new PropertyValueFactory<TaskInfo,String>("taskName"));
		signerCol.setCellValueFactory(new PropertyValueFactory<TaskInfo,String>("signer"));
		signDateCol.setCellValueFactory(new PropertyValueFactory<TaskInfo,String>("signDate"));
		operateCol.setCellValueFactory(new PropertyValueFactory<TaskInfo,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<TaskInfo, Boolean>, TableCell<TaskInfo, Boolean>>() {

            public TableCell<TaskInfo, Boolean> call(TableColumn<TaskInfo, Boolean> p) {

            	HyperlinkCell<TaskInfo, Boolean> cell = new HyperlinkCell<TaskInfo, Boolean>();
                return cell;

            }

        });
		taskinfoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaskInfo>(){

			@Override
			public void changed(ObservableValue<? extends TaskInfo> arg0, TaskInfo arg1,
					TaskInfo arg2) {
				taskinfoTable.getSelectionModel().clearSelection();
			}
		});
		
	}

	/**更新面板上各数据
	 * @param anatomyReq
	 */
	public void updateData(String taskId) {
		//TODO
		Map<String,Object> map = BaseService.getInstance().getTblAnatomyTaskService().getMap(taskId);
		
		String studyNo = "";
		String sd = "";
		String reqDateStr = "";
		String animalType = "";
		Integer animalNum = 0;
		String taskCreater = "";
		String taskDateStr = "";
		String anatomyRsn = "";
		//,reqBeginDateStr,reqEndDateStr
		String planAnatomyDate = "";
		if(null != map){
			studyNo = (String) map.get("studyNo");
			sd = (String) map.get("sd");
			reqDateStr = (String) map.get("reqDateStr");
			animalType = (String) map.get("animalType");
			animalNum = (Integer) map.get("animalNum");
			anatomyRsn = (String) map.get("anatomyRsn");
			taskCreater = (String) map.get("taskCreater");
			taskDateStr = (String) map.get("taskDateStr");
			String reqBeginDateStr = (String) map.get("reqBeginDateStr");
			String reqEndDateStr = (String) map.get("reqEndDateStr");
			if(null != reqBeginDateStr && reqBeginDateStr.equals(reqEndDateStr)){
				planAnatomyDate = reqBeginDateStr;
			}else if(null != reqBeginDateStr && null != reqEndDateStr){
				planAnatomyDate = reqBeginDateStr+" ~ "+reqEndDateStr;
			}
		}
		
		studyNoLabel.setText(studyNo+" （"+sd+"）");
//		sdLabel.setText(sd);
		reqDateLabel.setText(reqDateStr);
		animalTypeLabel.setText(animalType);
		animalNumLabel.setText(animalNum+"");
		anatomyRsnLabel.setText(anatomyRsn);
		taskCreaterLabel.setText(taskCreater);
		taskCreateTimeLabel.setText(taskDateStr);
		//TODO
		planAnatomyDateLabel.setText(planAnatomyDate);
			
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TaskInfo.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("任务信息");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	/**加载数据
	 * @param taskId
	 */
	public void loadData(String taskId){
		
		TaskInfoPage.taskId = taskId;
		hasSignPrivilege = isHasSignPrivielge(taskId);
		//更新面板上各数据
		updateData(taskId);
		//更新表格数据
		updateTable(taskId);
	}
	/**是否有签字权限
	 * @param taskId2
	 * @return
	 */
	private boolean isHasSignPrivielge(String taskId) {
		String userName = SaveUserInfo.getUserName();
		String realName = SaveUserInfo.getRealName();
		if(null != userName && null != realName){
			// 1. 自己创建的任务可以签字
			TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
			if(null != tblAnatomyTask){
				if(userName.equals(tblAnatomyTask.getTaskCreater())){
					return true;
				}
				// 2. 对应专题的  病理专题负责人
				String studyNo = tblAnatomyTask.getStudyNo();
				String pathSD = BaseService.getInstance().getTblPathSessionService().getPathSD(studyNo);
				
				if(realName.equals(pathSD)){
					return true;
				}
			}
			// 3.病理负责人可以签字
			boolean ishave  = 	BaseService.getInstance().getUserService().isHasPrivilege(userName, "病理负责人");
			if(ishave){
				return true;
			}
		}
		return false;
	}

	/**更新表格数据
	 * @param taskId
	 */
	private void updateTable(String taskId) {
		data_taskinfoTable.clear();
		
		TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		String anatomyCheckFinishSign = tblAnatomyTask.getAnatomyCheckFinishSign();
		String visceraFixedWeightFinishSign = tblAnatomyTask.getVisceraFixedWeightFinishSign();
		TaskInfo taskinfo_anatomycheck = new TaskInfo("解剖","","",true);
		if(null != anatomyCheckFinishSign && !"".equals(anatomyCheckFinishSign)){
			TblES tbles = BaseService.getInstance().getTblESService().getById(anatomyCheckFinishSign);
			String signer = tbles.getSigner();
			String signDate = DateUtil.dateToString(tbles.getDateTime(), "yyyy-MM-dd");
			taskinfo_anatomycheck.setSigner(signer);
			taskinfo_anatomycheck.setSignDate(signDate);
			taskinfo_anatomycheck.setOperate(false);
		}
		data_taskinfoTable.add(taskinfo_anatomycheck);
		boolean hasVisceraFixedWeigh = BaseService.getInstance().getTblAnatomyTaskService().hasFixedWeigh(taskId);
		if(hasVisceraFixedWeigh){
			TaskInfo taskinfo_fixedWeigh = new TaskInfo("固定后称重","","",true);
			if(null != visceraFixedWeightFinishSign && !"".equals(visceraFixedWeightFinishSign)){
				TblES tbles = BaseService.getInstance().getTblESService().getById(visceraFixedWeightFinishSign);
				String signer = tbles.getSigner();
				String signDate = DateUtil.dateToString(tbles.getDateTime(), "yyyy-MM-dd");
				taskinfo_fixedWeigh.setSigner(signer);
				taskinfo_fixedWeigh.setSignDate(signDate);
				taskinfo_fixedWeigh.setOperate(false);
			}
			data_taskinfoTable.add(taskinfo_fixedWeigh);
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
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	public static class TaskInfo{
		
		private StringProperty taskName;	//任务（如：解剖，固定后称重）
		private StringProperty signer;		//签字者
		private StringProperty signDate;	//签字日期
		private BooleanProperty  operate;	//操作
		
		public TaskInfo(String taskName,String signer,String signDate,boolean operate){
			setTaskName(taskName);
			setSigner(signer);
			setSignDate(signDate);
			setOperate(operate);
		}
		
		public String getTaskName() {
			return taskName.get();
		}
		public void setTaskName(String taskName) {
			this.taskName = new SimpleStringProperty(taskName);
		}
		public String getSigner() {
			return signer.get();
		}
		public void setSigner(String signer) {
			this.signer = new SimpleStringProperty(signer);
		}
		public String getSignDate() {
			return signDate.get();
		}
		public void setSignDate(String signDate) {
			this.signDate = new SimpleStringProperty(signDate);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
	}
	/** 表格 操作列 用
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
	            ov = getTableColumn().getCellObservableValue(getIndex());  
	            if (ov instanceof SimpleBooleanProperty) {  
	            	SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
	            	if(!booleanValue.get()){
	            		hyperlink.setDisable(true);
	            		setGraphic(new Label("完成"));
	            	}else{
	            		setGraphic(hyperlink);  
	            		hyperlink.setText("签字确认");
	            		if(!hasSignPrivilege){
	            			hyperlink.setDisable(true);
	            		}else{
	            			hyperlink.setDisable(false);
	            		}
	            		hyperlink.setPrefWidth(60);
	            		hyperlink.setPrefHeight(20);
	            		hyperlink.setUserData(this.getTableRow().getItem());
//	            		final TableView<?> tableView = this.getTableView();
	            		hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){
	            			
	            			@Override
	            			public void handle(MouseEvent e) {
	            				Hyperlink hyperlink = (Hyperlink) e.getSource();
	            				TaskInfo taskInfo = (TaskInfo) hyperlink.getUserData();
								Json json = BaseService.getInstance().getTblAnatomyTaskService().checkBeforeSign(taskId,taskInfo.getTaskName());
								if(json.isSuccess()){
									//签字窗口
									SignMeFrame signMeFrame = new SignMeFrame("");
									Stage stage = new Stage();
									stage.initModality(Modality.APPLICATION_MODAL);
									stage.setTitle("会话--身份验证");
									try {
										signMeFrame.start(stage);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
									//签字通过
									if("OK".equals(SignMeFrame.getType())){
										BaseService.getInstance().getTblAnatomyTaskService().sign(taskId,taskInfo.getTaskName(),SaveUserInfo.getRealName());
										updateTable(taskId);
										MainPageController.getInstance().updateRightSide();
									}
								}else{
									showErrorMessage(json.getMsg());
								}
	            			}
	            		});
	            	}
	            } 
	        }  
	    }  
	}  
}
