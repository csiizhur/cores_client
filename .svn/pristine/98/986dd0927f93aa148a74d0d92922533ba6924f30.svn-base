package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyReq;
import com.lanen.model.path.TblAnatomyReqAnimalList;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.SignMeFrame;
import com.lanen.view.test.AnatomyReqPage.AnatomyReq;

public class CreateTaskPage extends Application implements Initializable {

	private String reqId ;
	@FXML
	private Label studyNoLabel;			//专题编号   Label
//	@FXML
//	private Label sdLabel;			//SD   Label
	@FXML
	private Label pathDirectorLabel;//专题病理负责人Label
	@FXML
	private Label authorLabel;			//申请人   Label
	@FXML
	private Label reqDateLabel;			//申请日期   Label
	@FXML
	private Label anatomyDateLabel;			//解剖日期   Label
	@FXML
	private Label animalTypeLabel;	//动物种类Label
	@FXML
	private Label animalNumLabel;	//动物数量Label
	@FXML
	private Label anatomyRsnLabel;	//解剖原因Label
	@FXML
	private Label taskCreaterLabel;	//任务创建人Label
	@FXML
	private Label taskCreateTimeLabel;	//任务创建时间Label
	
//	@FXML
//	private CheckBox histopathReviewRequirementCheckBox;	//是否需要复核
//	private int histopathReviewRequirement = 0;			//1:表示需要 复核人	0：不需要
	@FXML
//	private ComboBox<String> histopathReviewerComboBox;		//指定的复审人
//	private ObservableList<String> data_histopathReviewerComboBox = FXCollections.observableArrayList();
	Map<String,String> realNameUserNameMap = new HashMap<String,String>();//  姓名：用户名
	private static CreateTaskPage instance;
	public static CreateTaskPage getInstance(){
		if(null == instance){
			instance = new CreateTaskPage();
		}
		return instance;
	}
	
	public CreateTaskPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化 是否需要复核 CheckBox
//		initHistopathReviewRequirementCheckBox();
		//初始化 复核人 ComboBox
//		initHistopathReviewerComboBox();
	}
	
//	/**
//	 * 初始化 复核人 ComboBox
//	 */
//	private void initHistopathReviewerComboBox() {
//		histopathReviewerComboBox.setItems(data_histopathReviewerComboBox);
//	}
	
//	/**
//	 * 更新复查者列表 ComboBox
//	 */
//	private void updateHistopathReviewComboBox(){
//		
//		data_histopathReviewerComboBox.clear();
//		
//		List<User> pathUserList = BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_同行评议");
//		String currentUserName = SaveUserInfo.getUserName();
//		if(null != pathUserList && pathUserList.size() > 0){
//			for(User obj:pathUserList){
//				if(!obj.getUserName().equals(currentUserName)){
//					data_histopathReviewerComboBox.add(obj.getRealName());
//					realNameUserNameMap.put(obj.getRealName(), obj.getUserName());
//				}
//			}
//		}
//	}

//	/**
//	 * 初始化 是否需要复核 CheckBox
//	 */
//	private void initHistopathReviewRequirementCheckBox() {
//		histopathReviewRequirementCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
//				if(null != newValue){
//					if(newValue){
//						histopathReviewRequirement = 1;
//						histopathReviewerComboBox.setDisable(false);
//					}else{
//						histopathReviewRequirement = 0;
//						histopathReviewerComboBox.getSelectionModel().clearSelection();
//						histopathReviewerComboBox.setDisable(true);
//					}
//				}
//				
//			}
//		});
//		
//	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		
		 TblAnatomyTask tblAnatomyTask = new TblAnatomyTask();
		
		String taskCreater = SaveUserInfo.getUserName();
		
		tblAnatomyTask.setTaskCreater(taskCreater);
		
//		if(histopathReviewRequirement == 1){
//			tblAnatomyTask.setHistopathReviewRequirement(histopathReviewRequirement);
//			String selectItem = histopathReviewerComboBox.getSelectionModel().getSelectedItem();
//			if(null != selectItem){
//				String reviewer = realNameUserNameMap.get(selectItem);
//				tblAnatomyTask.setHistopathReviewer(reviewer);
//			}else{
//				showErrorMessage("请选择复核者！");
//				return;
//			}
//		}
		
		TblAnatomyReq anatomyReq = BaseService.getInstance().getTblAnatomyReqService().getById(reqId);
		List<String> animalCodeList = new ArrayList<String>();
		
		List<TblAnatomyReqAnimalList> animallist = 
				BaseService.getInstance().getTblAnatomyReqAnimalListService().getListByStudyNoAndReqNo(anatomyReq.getStudyNo(), anatomyReq.getReqNo());
		if(null != animallist && animallist.size() > 0){
			for(TblAnatomyReqAnimalList obj :animallist){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		//判断动物时否都传出
		Json json = BaseService.getInstance().getTblAnatomyReqService().isComeOut(anatomyReq.getStudyNo(),animalCodeList);
		//都传出 = false
		boolean isAllComeOut = false;
		if(json.isSuccess()){
			isAllComeOut = true;
		}else{
			isAllComeOut = Messager.showSimpleConfirm("提示", json.getMsg());
		}
		if(isAllComeOut){
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("解剖任务-身份验证");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				Map<String,String> map = BaseService.getInstance().getTblAnatomyTaskService().save(tblAnatomyTask,reqId);
				if(map.get("success").equals("true")){
					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					AnatomyReqPage.getInstance().updateAnatomyReqTable(2);
					final String taskId=map.get("taskId");
					//刷新主页面上的任务树及任务表格
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							MainPageController.getInstance().updateTaskTreeAndTaskTable();
//						MainPageController.getInstance().selectNewTask(tblAnatomyTask);
							final TblAnatomyTask tblAnatomyTask1=BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
							MainPageController.getInstance().selectNewTask(tblAnatomyTask1);
						}
					});
					
					showMessage("解剖任务创建成功！");
				}else{
					showErrorMessage(map.get("msg"));
				}
			}
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**更新面板上各数据
	 * @param anatomyReq
	 */
	public void updateData(AnatomyReq anatomyReq) {
		String studyNo = anatomyReq.getStudyNo();
		reqId = anatomyReq.getId();
//		String reqNo = anatomyReq.getReqNo();
//		studyNoLabel.setText(anatomyReq.getStudyNo()+" （"+sd+"）");
//		sdLabel.setText("");
		pathDirectorLabel.setText("");
		authorLabel.setText(anatomyReq.getSubmitter());
		reqDateLabel.setText(anatomyReq.getSubmitDate());
		String beginDateStr = anatomyReq.getBenginDate();
		String endDateStr = anatomyReq.getEndDate();
		if(beginDateStr.equals(endDateStr)){
			anatomyDateLabel.setText(beginDateStr);
		}else{
			anatomyDateLabel.setText(beginDateStr+"～"+endDateStr);
		}
		animalTypeLabel.setText(anatomyReq.getAnimalType());
		animalNumLabel.setText(anatomyReq.getAnimalNum());
		anatomyRsnLabel.setText("");
		taskCreaterLabel.setText(SaveUserInfo.getRealName());
		Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		taskCreateTimeLabel.setText(DateUtil.dateToString(currentDate, "yyyy-MM-dd HH:mm"));
		
//		TblStudyPlan tblStudyPlan = BaseService.getTblStudyPlanService().getByStudyNo(studyNo);
		
		TblAnatomyReq tblAnatomyReq = BaseService.getInstance().getTblAnatomyReqService().getById(reqId);
		String sd = BaseService.getInstance().getTblStudyPlanService().getSDByStudyNo(studyNo);
		String pathSD = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(studyNo);
		if(null != sd){
			studyNoLabel.setText(anatomyReq.getStudyNo()+" （"+sd+"）");
		}else{
			studyNoLabel.setText(anatomyReq.getStudyNo());
			
		}
		if(null != pathSD){
			pathDirectorLabel.setText(pathSD);
		}
		if(null != tblAnatomyReq){
			int anatomyRsn = tblAnatomyReq.getAnatomyRsn();
			switch (anatomyRsn) {
			case 1:
				anatomyRsnLabel.setText("计划解剖");
				break;
			case 2:
				anatomyRsnLabel.setText("濒死解剖");
				break;
			case 3:
				anatomyRsnLabel.setText("死亡解剖");
				break;
			case 4:
				anatomyRsnLabel.setText("安乐死解剖");
				break;

			default:
				break;
			}
			
		}
		
		//更新复查者列表 ComboBox
//		updateHistopathReviewComboBox();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("CreateTask.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("创建解剖任务");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
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
	public static void main(String[] args) {
		launch(args);
	}

	
}
