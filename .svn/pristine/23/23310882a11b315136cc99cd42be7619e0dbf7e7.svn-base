package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblBalConnect;
import com.lanen.model.path.TblPathSession;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.balreg.AddTblBalCalibration;
import com.lanen.view.balreg.BalConnect;
import com.lanen.view.main.Main;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.MainPageController.AnatomyTask;
import com.lanen.view.main.SignMeFrame;

public class PathSessionPage extends Application implements Initializable {
	
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Button dataCollectBtn;//数据采集 Button
	@FXML
	private Button dataConfirmBtn;//数据确认Button
	
	@FXML
	private Button applyBtn;//应用Button
	@FXML
	private Button exitBtn;//取消/关闭Button
	@FXML
	private ListView<CheckBox> anatomyOperatorListView ;
	private ObservableList<CheckBox> data_anatomyOperatorListView = FXCollections.observableArrayList();
	//当前会话操作者（用户名）列表
	private List<String> operatorList = new ArrayList<String>();
	
	@FXML
	private CheckBox anatomyCheckBox;	//动物解剖    CheckBox
	@FXML
	private CheckBox weightCheckBox;	//脏器称重   CheckBox
	@FXML
	private CheckBox fixedCheckBox;		//脏器固定   CheckBox
	@FXML
	private CheckBox fixedWeightBox;	//固定后称重   CheckBox
	//创建会话
	private static boolean createSession = false;
	//任务id列表
	private static List<String> taskIdList = null;
	//会话id列表
	private static List<String> sessionIdList = null;
	
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
	private TableColumn<AnatomyTask,String> animalNumCol;      //动物数量
	@FXML
	private TableColumn<AnatomyTask,String> anatomyRsnCol;      //解剖原因
	private static PathSessionPage instance;
	public static PathSessionPage getInstance(){
		if(null == instance){
			instance = new PathSessionPage();
		}
		return instance;
	}
	
	public PathSessionPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		//初始化CheckBox
		initCheckBox();
		//初始化table
		initTaskTable();
		//初始化 解剖者列表
		initAnatomyOperatorListView();
	}
	
	/**
	 * 初始化 解剖者列表
	 */
	private void initAnatomyOperatorListView() {
		anatomyOperatorListView.setItems(data_anatomyOperatorListView);
		anatomyOperatorListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
			@Override
			public void changed(ObservableValue<? extends CheckBox> arg0, CheckBox arg1,
					CheckBox newValue) {
				anatomyOperatorListView.getSelectionModel().clearSelection();
				if(null != newValue && !newValue.isDisabled()){
					newValue.setSelected(!newValue.isSelected());
				}
				
			}
		});
		anatomyOperatorListView.setDisable(true);
	}

	/**
	 * 初始化CheckBox
	 */
	private void initCheckBox() {
		anatomyCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
					Boolean newValue) {
				if(null != newValue && newValue){
					//不勾选  固定后称重
					fixedWeightBox.setSelected(false);
					
					anatomyOperatorListView.setDisable(false);
					String realName = SaveUserInfo.getUser().getRealName();
					for(int i = 0 ;i<data_anatomyOperatorListView.size();i++){
						String name = data_anatomyOperatorListView.get(i).getText();
						if(name.equals(realName)){
							anatomyOperatorListView.getSelectionModel().select(i);
							break;
						}
					}
					
				}else{
					anatomyOperatorListView.setDisable(true);
					if(createSession){
						for(CheckBox checkbox:data_anatomyOperatorListView){
							checkbox.setSelected(false);
						}
					}
				}
				
			}
		});
		weightCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
					Boolean newValue) {
				if(null != newValue && newValue){
					//不勾选  固定后称重
					fixedWeightBox.setSelected(false);
				}
				
			}
		});
		fixedCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
					Boolean newValue) {
				if(null != newValue && newValue){
					//不勾选  固定后称重
					fixedWeightBox.setSelected(false);
				}
				
			}
		});
		fixedWeightBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
					Boolean newValue) {
				if(null != newValue && newValue){
					//不勾选  解剖，称重，固定
					anatomyCheckBox.setSelected(false);
					weightCheckBox.setSelected(false);
					fixedCheckBox.setSelected(false);
				}
				
			}
		});
		
	}
	/**
	 * 初始化 table
	 */
	private void initTaskTable() {
		anatomyTaskTable.setItems(data_anatomyTaskTable);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("studyNo"));
		anatomyNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("anatomyNum"));
		taskCreateDateCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreateDate"));
		taskCreaterCol
			.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("taskCreater"));
		animalTypeCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("animalType"));
		animalNumCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("animalNum"));
		anatomyRsnCol
		.setCellValueFactory(new PropertyValueFactory<AnatomyTask,String>("anatomyRsn"));
		
		anatomyTaskTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<AnatomyTask>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyTask> arg0, AnatomyTask arg1,
					AnatomyTask newValue) {
				// TODO 任务table chang事件
				if(null != newValue){
					anatomyTaskTable.getSelectionModel().clearSelection();
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
	/**更新表格数据
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
				Integer animalNum = (Integer) map.get("animalNum");
				String anatomyRsn = (String) map.get("anatomyRsn");
				data_anatomyTaskTable.add(new AnatomyTask(taskId,studyNo,anatomyNum
						,taskCreateTime,taskCreater,animalType,animalNum,anatomyRsn));
			}
		}
		
	}
	
	
	
	/**
	 * 数据采集
	 * 
	 * @param event
	 */
	@FXML
	private void onDataCollectBtnAction(ActionEvent event) {
		if(createSession){
			//若还没保存，先保存
			int sessionType = 0;
			if(anatomyCheckBox.isSelected()){
				sessionType = sessionType + 1;
			}
			if(weightCheckBox.isSelected()){
				sessionType = sessionType + 2;
			}
			if(fixedCheckBox.isSelected()){
				sessionType = sessionType + 4;
			}
			if(fixedWeightBox.isSelected()){
				sessionType = 8;
			}
			if(sessionType >0 && null != taskIdList){
				//解剖者  用户名列表(已选中的)
				List<String> userNameList = new ArrayList<String>();
				if(sessionType == 1 || sessionType == 3 || 
						sessionType ==5 || sessionType == 7){
					
					for(CheckBox checkbox:data_anatomyOperatorListView){
						if(checkbox.isSelected()){
							userNameList.add((String)checkbox.getUserData());
						}
					}
					if(userNameList.size() < 1){
						showErrorMessage("请先选择解剖者！");
						return ;
					}
				}
					
//				//签字窗口
//				SignFrame signFrame = new SignFrame("");
//				Stage stage = new Stage();
//				stage.initModality(Modality.APPLICATION_MODAL);
//				stage.setTitle("会话--身份验证");
//				try {
//					signFrame.start(stage);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				//签字窗口
				SignMeFrame signMeFrame = new SignMeFrame("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("会话--身份验证");
				try {
					signMeFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//签字通过
				if("OK".equals(SignMeFrame.getType())){
					String userName = "";
//					Date currentDate = new Date();
					User user = SaveUserInfo.getUser();
					if(null != user){
						userName = user.getUserName();
					}
					//保存会话及操作者列表，并返回会话idList
					sessionIdList = BaseService.getInstance().getTblPathSessionService().saveList(sessionType,userName,taskIdList,userNameList);
					MainPageController.getInstance().updateSessionTable();
					//保存成功了，不是新建了
					createSession = false;
					exitBtn.setText("关闭");
					//更新 4 个CheckBox 的状态
					updateCheckboxState(sessionIdList.get(0));
					
					//更新解剖者列表 及应用按钮状态
					updateData_anatomyOperatorListView();
					
					dataConfirmBtn.setDisable(false);
					
				}
			}else{
				showErrorMessage("请先选择会话类型！");
				return ;
			}
		}
		//打开操作窗口
		if(!createSession){
			if(weightCheckBox.isSelected()||fixedWeightBox.isSelected()){
				String houstName = SystemTool.getHostName();//计算机名称
				List<TblBalConnect> list = BaseService.getInstance().getTblBalConnectService().findByHostNameList(houstName);
				if(null!=list && list.size()>0){
					boolean  isNeedCal=false;
					for(TblBalConnect connect:list){
						String commName = connect.getCommName();
						String balCode = connect.getBalCode();
						//Integer enabledStr = connect.getEnabled();----------------------------------------------------------------------------------------//
						boolean flag = BaseService.getInstance().getTblBalCalibrationIndexService().isExistByBalCode(balCode);
						if(flag){
							isNeedCal=true;
							break;
						}
					}
					if(!isNeedCal){
						if(Messager.showConfirm("提示","天平未校准","是否进行校准？")){
							try{
//								AddTblBalCalibration.getInstance().start(getStage());
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
								AddTblBalCalibration.getInstance().loadData(null, null);
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{
							//无校准记录，否：打开
							//打开解剖窗口
							openAnatomyProcessPage();
						}
						
					}else{
						//已校准记录，直接打开
						//打开解剖窗口
						openAnatomyProcessPage();
					}
				}else{
					if(Messager.showConfirm("提示","无天平接入信息","是否登记接入信息？")){
						try {
//							BalConnect.getInstance().start(getStage());
							Stage stage = Main.stageMap.get("BalConnect");
							if(null == stage){
								stage =new Stage();
								stage.initOwner(Main.mainWidow);
								stage.initModality(Modality.APPLICATION_MODAL);
								BalConnect.getInstance().start(stage);
								Main.stageMap.put("BalConnect",stage);
							}else{
								stage.show();
							}
							BalConnect.getInstance().loadData("1");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						//无接入信息，否：打开
						//打开解剖窗口
						openAnatomyProcessPage();
					}
				}
			}else{
				//无称重，直接打开
				//打开解剖窗口
				openAnatomyProcessPage();
			}
			
			
		}
	}
	
	
	/**
	 * 打开解剖窗口
	 */
	private void  openAnatomyProcessPage(){
		try {
			Integer sessionType =null;
			TblPathSession tblPathSession = BaseService.getInstance().getTblPathSessionService().getById(sessionIdList.get(0));
			if(null != tblPathSession){
				sessionType = tblPathSession.getSessionType();
			}
			Stage stage = Main.stageMap.get("AnatomyProcessPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AnatomyProcessPage.getInstance().start(stage);
				Main.stageMap.put("AnatomyProcessPage",stage);
			}else{
				stage.show();
			}
			AnatomyProcessPage.getInstance().loadData(taskIdList,sessionType,sessionIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**数据确认
	 * @param event
	 */
	@FXML
	private void onDataConfirmBtnAction(ActionEvent event){	
	   try {
				Integer sessionType =null;
				TblPathSession tblPathSession = BaseService.getInstance().getTblPathSessionService().getById(sessionIdList.get(0));
				if(null != tblPathSession){
					sessionType = tblPathSession.getSessionType();
				}
				Stage stage = Main.stageMap.get("DataValidation");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					DataValidation.getInstance().start(stage);
					Main.stageMap.put("DataValidation",stage);
				}else{
					stage.show();
				}
//				DataValidation.getInstance().start(getStage());
			    DataValidation.getInstance().loadData(sessionType,taskIdList,sessionIdList);
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**应用
	 * @param event
	 */
	@FXML
	private void onApplyBtnAction(ActionEvent event){
		if(createSession || sessionIdList.size() != 1){
			return;
		}
		List<String> userNameList = new ArrayList<String>();
		for(CheckBox checkbox:data_anatomyOperatorListView){
			if(checkbox.isSelected()){
				userNameList.add((String)checkbox.getUserData());
			}
		}
		if(userNameList.size() < 1){
			showErrorMessage("请先选择解剖者！");
			return;
		}
		//TODO  
		BaseService.getInstance().getTblPathSessionService().updateOperatorList(sessionIdList.get(0),userNameList);
		
		operatorList = userNameList;
		
		applyBtn.setDisable(true);
		
		showMessage("保存成功！");
	}
	
	
	
	
	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(List<String> taskIdList) {
		//TODO 创建会话
		createSession = true;
		PathSessionPage.taskIdList = taskIdList;
		sessionIdList = null;
		//更新表格数据
		updateTable(taskIdList);
		
		//更新 4 个CheckBox 的状态
		updateCheckboxState(null);
		//更新解剖者列表 及应用按钮状态
		updateData_anatomyOperatorListView();
		initButton(null);
		//数据确认按钮不可用
		dataConfirmBtn.setDisable(true);
		
		//清除所有勾选
		cleanCheckBox();
	}

	/**
	 * 加载数据（打开会话）
	 */
	public void loadData(List<String> sessionIdList,List<String> taskIdList){
		//清除所有勾选
		cleanCheckBox();
		createSession = false;
		exitBtn.setText("关闭");
		PathSessionPage.taskIdList = taskIdList;
		PathSessionPage.sessionIdList = sessionIdList;
		//更新表格数据
		updateTable(taskIdList);
		//更新 4 个CheckBox 的状态
		updateCheckboxState(sessionIdList.get(0));
		
		//更新解剖者列表 及应用按钮状态
		updateData_anatomyOperatorListView();
		initButton(sessionIdList);
//		//数据确认按钮可用
//		dataConfirmBtn.setDisable(false);
		
	}
	
	
	private void cleanCheckBox(){
		anatomyCheckBox.setSelected(false);
		weightCheckBox.setSelected(false);
		fixedCheckBox.setSelected(false);
		fixedWeightBox.setSelected(false);
	}
	
	private void initButton(List<String> sessionIdList ){
		String userId = SaveUserInfo.getUser().getId();
		String userName = SaveUserInfo.getUser().getUserName();
		
		boolean falge = isPathLeaderOrCreator();
		dataConfirmBtn.setDisable(!falge);	//数据确认Button
		dataCollectBtn.setDisable(!falge);//数据采集 Button
        
//		boolean	isSee  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_查看");
//		boolean	isEdit  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_编辑");
////		if(isSee || isEdit ){
//			dataConfirmBtn.setDisable(!isSee);	//数据确认Button
//			dataCollectBtn.setDisable(!isEdit);//数据采集 Button
////		}
////		else{
////			
////		}
	  boolean disable = false;
	   if(!falge){
		   if(null != sessionIdList){
		    	
		    	for(String sessionId:sessionIdList){
					TblPathSession pathSession= BaseService.getInstance().getTblPathSessionService().getById(sessionId);
				    String creator =  pathSession.getSessionCreator();
					if(!userName.equals(creator)){
						disable = true;
						break;
					}
				}	
				dataCollectBtn.setDisable(disable);//数据采集 Button
				dataConfirmBtn.setDisable(disable);	//数据确认Button
		    }
	   }
		   
	   boolean	wight = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_称重");
	   boolean	check  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_剖检");
	   boolean	fix  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定");
	   boolean	fixWight  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定后称重");
	   if(!falge && !disable && (wight||check||fix||fixWight) ){
		   dataCollectBtn.setDisable(false);//数据采集 Button
	   }
	   
	   
		
	}
	
	/**
	 * 更新解剖者列表 及应用按钮状态
	 */
	private void updateData_anatomyOperatorListView() {
		// 1.创建会话的 TODO 
		if(createSession){
			
			//应用按钮不可见
			applyBtn.setVisible(false);
			data_anatomyOperatorListView.clear();
			List<User> list = BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_剖检");
			if(null != list){
				CheckBox checkBox = null;
				for(User user:list){
					String realName = user.getRealName();
					checkBox = new CheckBox(realName);
					checkBox.setUserData(user.getUserName());
					data_anatomyOperatorListView.add(checkBox);
				}
			}
		}else{
			//2.打开会话的
			boolean editFlag = false;//不可以编辑
			if(sessionIdList.size() ==  1){
				editFlag = true;
			}
			applyBtn.setVisible(editFlag);
			applyBtn.setDisable(true);
			data_anatomyOperatorListView.clear();
			operatorList.clear();
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblPathSessionService().getUserNameRealName(sessionIdList);
			Map<String,String> userNameRealNameMap = new HashMap<String,String>();
			if(null != mapList && mapList.size() > 0){
				for(Map<String,Object> map :mapList){
					String userName = (String) map.get("userName");
					String realName = (String) map.get("realName");
					operatorList.add(userName);
					userNameRealNameMap.put(userName, realName);
				}
			}
			
			List<User> list = BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_登录");
			List<String> userNameList = new ArrayList<String>();
			CheckBox checkBox = null;
			if(null != list){
				for(User user:list){
					String realName = user.getRealName();
					String userName = user.getUserName();
					checkBox = new CheckBox(realName);
					checkBox.setUserData(user.getUserName());
					checkBox.setSelected(operatorList.contains(userName));
					checkBox.setDisable(!editFlag);
					data_anatomyOperatorListView.add(checkBox);
					checkBox.selectedProperty().addListener(new ChangeListener<Boolean>(){

						@Override
						public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
								Boolean newValue) {
							updateApplyBtnState();
						}
					});
					userNameList.add(userName);
				}
			}
			
			//若之前有权限的用户，现在无权限但已经被指定为操作者的，也得显示
			for(String operator:operatorList){
				if(!userNameList.contains(operator)){
					checkBox = new CheckBox(userNameRealNameMap.get(operator));
					checkBox.setUserData(operator);
					checkBox.setSelected(true);
					checkBox.setDisable(!editFlag);
					checkBox.selectedProperty().addListener(new ChangeListener<Boolean>(){

						@Override
						public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
								Boolean newValue) {
							updateApplyBtnState();
						}
					});
					data_anatomyOperatorListView.add(checkBox);
					
				}
			}
		}
		
	}
	
	/**
	 * 更新 用户按钮状态
	 */
	private void updateApplyBtnState() {
		// TODO Auto-generated method stub
		applyBtn.setDisable(true);
		if(createSession || sessionIdList.size() != 1){
			applyBtn.setDisable(true);
			return;
		}
		List<String> userNameList = new ArrayList<String>();
		for(CheckBox checkbox:data_anatomyOperatorListView){
			if(checkbox.isSelected()){
				userNameList.add((String)checkbox.getUserData());
			}
		}
//		if(userNameList.size() == 0){
//			applyBtn.setDisable(true);
//			return;
//		}
		if(userNameList.size() != operatorList.size()){
			applyBtn.setDisable(false);
			return;
		}
		for(String userName:userNameList){
			if(!operatorList.contains(userName)){
				applyBtn.setDisable(false);
				return;
			}
		}
		for(String operator:operatorList){
			if(!userNameList.contains(operator)){
				applyBtn.setDisable(false);
				return;
			}
		}
	}
	/**更新4 个CheckBox 的状态
	 * @param string
	 */
	private void updateCheckboxState(String sessionId) {
//		@FXML
//		private CheckBox anatomyCheckBox;	//动物解剖    CheckBox
//		@FXML
//		private CheckBox weightCheckBox;	//脏器称重   CheckBox
//		@FXML
//		private CheckBox fixedCheckBox;		//脏器固定   CheckBox
//		@FXML
//		private CheckBox fixedWeightBox;	//固定后称重   CheckBox
		if(null != sessionId){
			TblPathSession tblPathSession = BaseService.getInstance().getTblPathSessionService().getById(sessionId);
			if(null != tblPathSession){
				anatomyCheckBox.setDisable(true);
				weightCheckBox.setDisable(true);
				fixedCheckBox.setDisable(true);
				fixedWeightBox.setDisable(true);
				switch (tblPathSession.getSessionType()) {
				case 1:
					anatomyCheckBox.setSelected(true);
					break;
				case 2:
					weightCheckBox.setSelected(true);
					break;
				case 3:
					anatomyCheckBox.setSelected(true);
					weightCheckBox.setSelected(true);
					break;
				case 4:
					fixedCheckBox.setSelected(true);
					break;
				case 5:
					anatomyCheckBox.setSelected(true);
					fixedCheckBox.setSelected(true);
					break;
				case 6:
					weightCheckBox.setSelected(true);
					fixedCheckBox.setSelected(true);
					break;
				case 7:
					anatomyCheckBox.setSelected(true);
					weightCheckBox.setSelected(true);
					fixedCheckBox.setSelected(true);
					break;
				case 8:
					fixedWeightBox.setSelected(true);
					break;

				default:
					break;
				}
			}
		}else{
			String userId = SaveUserInfo.getUser().getId();
			boolean weight  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_称重");
			boolean check  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_剖检");
			boolean fix  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定");
			boolean weightAndfix  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_固定后称重");
			anatomyCheckBox.setDisable(!check);
			weightCheckBox.setDisable(!weight);
			fixedCheckBox.setDisable(!fix);
			fixedWeightBox.setDisable(!weightAndfix);
		}
	}
	
	
	
	private boolean isPathLeaderOrCreator(){
		String userId = SaveUserInfo.getUser().getId();
		boolean ishave  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "病理负责人");//病理负责人
		if(ishave){
			return true;
		}
		List<Map<String,Object>> list = BaseService.getInstance().getTblPathSessionService().getUserNameRealName(sessionIdList);
		if(null != list && list.size() >0  ){
			for(Map<String,Object>  map:list){
				String sessionCreator = (String) map.get("sessionCreator");
				if(userId.equals(sessionCreator)){
					return true;
				}
			}
		}
		
		int size = data_anatomyTaskTable.size();
		List<String>  slist = new ArrayList<String>();
		if(size > 1){
			for(int i = 0 ; i < size; i++){
				String studyNo = data_anatomyTaskTable.get(i).getStudyNo();
				slist.add(studyNo);
			}
		  
		}else{
			String studyNo = data_anatomyTaskTable.get(0).getStudyNo();
			slist.add(studyNo);
		}
		
		List<Map<String,Object>> plist  = BaseService.getInstance().getTblPathSessionService().getAllPathSD(slist);//所有病理专题负责人
		boolean falg = false;
		String realName = SaveUserInfo.getUser().getRealName();
		if(null != plist && plist.size() >0  ){
			for(Map<String,Object> pathSD:plist){
				String path = (String) pathSD.get("pathSD");
				if(!realName.equals(path)){
					falg = true;
				}
			}
			if(!falg){
				return false; 
			}else{
				return true;
			}
		}
		return false;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("PathSession.fxml"));
		Scene scene = new Scene(root, 591, 503);
		stage.setScene(scene);
		stage.setTitle("会话");
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
}
