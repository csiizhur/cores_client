package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

import datecontrol.DatePicker;
import datecontrol.Junit;

/**
 * 解剖申请list 
 * @author Administrator
 *
 */
public class AnatomyReqPage extends Application implements Initializable  {
	
	@FXML
	private HBox beginDateHBox;
	@FXML
	private HBox endDateHBox;
	DatePicker  beginPane=null;
	DatePicker  endPane=null;
	
	@FXML
	private TableView<AnatomyReq> anatomyReqTable;
	private ObservableList<AnatomyReq> data_anatomyReqTable = FXCollections.observableArrayList();

	@FXML
	private TableColumn<AnatomyReq,String> studyNo;        //专题编号
	@FXML
	private TableColumn<AnatomyReq,String> submitter;      //申请人（提交人）
	@FXML
	private TableColumn<AnatomyReq,String> submitDate;     //申请日期（提交日期）
	@FXML
	private TableColumn<AnatomyReq,String> benginDate;     //解剖日期（始）
	@FXML
	private TableColumn<AnatomyReq,String> endDate;        //解剖日期（末）
	@FXML
	private TableColumn<AnatomyReq,String> animalType;     //动物种类
	@FXML
	private TableColumn<AnatomyReq,String> animalNum;      //动物数量
	@FXML
	private TableColumn<AnatomyReq,String> tempFlag;       //临时   ，0：否     1：是
	@FXML
	private TableColumn<AnatomyReq,String> state;          //状态，未接受，已接收，已完成,已撤销
	
	@FXML
	private Button onCreateTempReqBut;//创建临时任务
	
	@FXML
	private Button onCreateAnatomyTaskBut;//创建任务
	
	boolean onCreateTempReq  = false;	//是否可以 创建临时任务
	static boolean onCreateAnatomyTask = false;	//是否可以 创建任务
	
	private Stage stage ;
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

	private static AnatomyReqPage instance;
	public static AnatomyReqPage getInstance(){
		if(null == instance){
			instance = new AnatomyReqPage();
		}
		return instance;
	}
	
	public AnatomyReqPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		//初始化日期选择器
		initDatePane();
		
		//初始化表格
		initAnatomyReqTable();
		instance = this;
	}
	/**
	 * 加载数据
	 */
	public void loadData(){
		
		//更新表格数据
		updateAnatomyReqTable(0);
		String userId = SaveUserInfo.getUser().getId();
		onCreateTempReq  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_创建临时任务");
		onCreateAnatomyTask  = 	BaseService.getInstance().getUserService().isHasPrivilege(userId, "毒性病理_创建任务");
		if(onCreateTempReq){
			onCreateTempReqBut.setDisable(false);
		}else{
			onCreateTempReqBut.setDisable(true);
		}
//		if(onCreateAnatomyTask){
//			onCreateAnatomyTaskBut.setDisable(false);
//		}else{
//			onCreateAnatomyTaskBut.setDisable(true);
//		}
		//创建任务按钮
		onCreateAnatomyTaskBut.setDisable(true);
	}

	

	/**
	 * 查询，onAction 响应函数
	 * @param event
	 */
	@FXML
	private void onQuery(ActionEvent event){
		//更新表格数据 TODO
		updateAnatomyReqTable(2);
	}
	/**
	 * 创建临时申请，onAction 响应函数
	 * @param event
	 */
	@FXML
	private void onCreateTempReq(ActionEvent event){
		try {
			TempReqPage.getInstance().start(getStage());
			TempReqPage.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建解剖任务，onAction 响应函数
	 * @param event
	 */
	@FXML
	private void onCreateAnatomyTask(ActionEvent event){
		
//		AnatomyReq anatomyReq = anatomyReqTable.getSelectionModel().getSelectedItem();
//		if(null != anatomyReq){
//			if(anatomyReq.getState().equals("未接受")){
//				try {
//					CreateTaskPage.getInstance().start(getStage());
//					CreateTaskPage.getInstance().updateData(anatomyReq);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}else{
//				showErrorMessage("该解剖申请已创建解剖任务！");
//			}
//		}else{
//			showErrorMessage("请先选择解剖申请！");
//		}
		showAnatomyReqView();
	}
	
	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		beginPane = new DatePicker(Locale.CHINA);
		beginPane.getTextField().setEditable(false);
		beginPane.getTextField().setFocusTraversable(true);
		beginPane.getTextField().setMaxWidth(100);
		beginPane.getTextField().setMinWidth(100);
		beginPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		beginPane.getCalendarView().todayButtonTextProperty().set("今天");
		beginPane.getCalendarView().setShowWeeks(false);
		beginPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		beginPane.setMaxWidth(100);
		beginDateHBox.getChildren().add(beginPane);
		
		endPane = new DatePicker(Locale.CHINA);
		endPane.getTextField().setEditable(false);
		endPane.getTextField().setFocusTraversable(true);
		endPane.getTextField().setMaxWidth(100);
		endPane.getTextField().setMinWidth(100);
		endPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endPane.getCalendarView().todayButtonTextProperty().set("今天");
		endPane.getCalendarView().setShowWeeks(false);
		endPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endPane.setMaxWidth(100);
		endDateHBox.getChildren().add(endPane);
		
		//设置日期
		String currentDate = DateUtil.getNow("yyyy-MM-dd");
		String beginDate = DateUtil.getDateAgo(6);
		beginPane.getTextField().setText(beginDate);
		endPane.getTextField().setText(currentDate);
		//
		beginPane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					//更新表格数据 
					updateAnatomyReqTable(2);
				}
			}
			});
		endPane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					//更新表格数据 
					updateAnatomyReqTable(2);
				}
			}
		});
		
	}
	
	/**
	 * 初始化表格
	 */
	private void initAnatomyReqTable(){
		
		studyNo.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("studyNo"));    
		submitter.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("submitter"));    
		submitDate.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("submitDate"));    
		benginDate.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("benginDate"));    
		endDate.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("endDate"));    
		animalType.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("animalType"));    
		animalNum.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("animalNum"));    
		tempFlag.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("tempFlag"));    
		state.setCellValueFactory(new PropertyValueFactory<AnatomyReq,String>("state"));   
		anatomyReqTable.setItems(data_anatomyReqTable);
		anatomyReqTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		anatomyReqTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<AnatomyReq>() {

			@Override
			public void changed(
					ObservableValue<? extends AnatomyReq> arg0,
					AnatomyReq arg1, AnatomyReq newValue) {
				if(null != newValue){
					if(onCreateAnatomyTask){
						if(newValue.getState().equals("未接受")){
							onCreateAnatomyTaskBut.setDisable(false);
						}else{
							onCreateAnatomyTaskBut.setDisable(true);
						}
					}
				}else{
					onCreateAnatomyTaskBut.setDisable(true);
				}
				
			}
		});
		submitter.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyReq, String> call(
	    			 TableColumn<AnatomyReq, String> param) {
	    		 TableCell<AnatomyReq, String> cell =
	    				 new TableCell<AnatomyReq, String>() {
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
		submitDate.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		benginDate.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		endDate.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		animalType.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		tempFlag.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		state.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		animalNum.setCellFactory(new Callback<TableColumn<AnatomyReq,String>,TableCell<AnatomyReq,String>>(){
			
			@Override
			public TableCell<AnatomyReq, String> call(
					TableColumn<AnatomyReq, String> param) {
				TableCell<AnatomyReq, String> cell =
						new TableCell<AnatomyReq, String>() {
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
		
		anatomyReqTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					showAnatomyReqView();
				}
				
			}});
	}
	
	
	/**
	 * 显示解剖申请
	 */
	public void showAnatomyReqView(){
		
		AnatomyReq anatomyReq = anatomyReqTable.getSelectionModel().getSelectedItem();
		if(null != anatomyReq){
			Stage stage = Main.stageMap.get("AnatomyReqView");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				try {
					AnatomyReqView.getInstance().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("AnatomyReqView",stage);
			}else{
				stage.show();
			}
			AnatomyReqView.getInstance().loadData(anatomyReq);
		}
	}
	
	/**
	 * 更新表格数据(select 1： 选择第一行  ，0：不处理     2：选择原选择行)
	 */
	public void updateAnatomyReqTable(int select) {
		String beginDateStr = beginPane.getTextField().getText().trim();
		String endDateStr = endPane.getTextField().getText().trim();
		
		AnatomyReq selectItem =  anatomyReqTable.getSelectionModel().getSelectedItem();
		
		data_anatomyReqTable.clear();
		
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyReqService().getMapListBySubmitDate(beginDateStr,endDateStr);
		if(null != mapList){
			for(Map<String,Object> map :mapList){
				String state = "";
				String taskId = (String) map.get("taskId");
				Integer submitFlag = (Integer) map.get("submitFlag");
				if(null == taskId || "".equals(taskId)){
					if(submitFlag == 1){
						state = "未接受";
					}else{
						state = "已撤销";
					}
				}else{
					state = "已接受";
					boolean finishFlag = true;//已完成标识
					Integer anatomyCheckFlag = (Integer) map.get("anatomyCheckFlag");//需剖检标识
					Integer histopathCheckFlag = (Integer) map.get("histopathCheckFlag");//需镜检标识
					Integer visceraWeighFlag = (Integer) map.get("visceraWeighFlag");//需称重标识
					Integer visceraFixedFlag = (Integer) map.get("visceraFixedFlag");//需固定后称重标识
					Integer histopathReviewRequirement = (Integer) map.get("histopathReviewRequirement");//镜检需复核标识
					
					String anatomyCheckFinishSign = (String) map.get("anatomyCheckFinishSign");//剖检完签字
					String histopathCheckFinishSign = (String) map.get("histopathCheckFinishSign");//镜检完签字
					String visceraWeightFinishSign = (String) map.get("visceraWeightFinishSign");//称重完签字
					String visceraFixedWeightFinishSign = (String) map.get("visceraFixedWeightFinishSign");//固定后称重完签字
					String histopathReviewFinalSign = (String) map.get("histopathReviewFinalSign");//复核最终签字
					
					if(null != anatomyCheckFlag && anatomyCheckFlag == 1 
							&& (null == anatomyCheckFinishSign || "".equals(anatomyCheckFinishSign) ) ){
						finishFlag = false;
					}
					if(null != histopathCheckFlag && histopathCheckFlag == 1 
							&& (null == histopathCheckFinishSign || "".equals(histopathCheckFinishSign) ) ){
						finishFlag = false;
					}
					if(null != visceraWeighFlag && visceraWeighFlag == 1 
							&& (null == visceraWeightFinishSign || "".equals(visceraWeightFinishSign) ) ){
						finishFlag = false;
					}
					if(null != visceraFixedFlag && visceraFixedFlag == 1 
							&& (null == visceraFixedWeightFinishSign || "".equals(visceraFixedWeightFinishSign) ) ){
						finishFlag = false;
					}
					if(null != histopathReviewRequirement && histopathReviewRequirement == 1 
							&& (null == histopathReviewFinalSign || "".equals(histopathReviewFinalSign) ) ){
						finishFlag = false;
					}
					
					if(finishFlag){
						state = "已完成";
					}
				}
				data_anatomyReqTable.add(new AnatomyReq(
							(String)map.get("id"),
							(Integer)map.get("reqNo"),
							(String)map.get("studyNo"),
							(String)map.get("submitter"),
							(String)map.get("submitDate"),
							(String)map.get("beginDate"),
							(String)map.get("endDate"),
							(String)map.get("animalType"),
							(Integer)map.get("animalNum") != null ? (Integer)map.get("animalNum"):0,
							(Integer)map.get("tempFlag"),
							state));
			}
		}
		if(data_anatomyReqTable.size()>0 && select == 1){
			//选择第一行
			anatomyReqTable.getSelectionModel().selectFirst();
		}else if(null != selectItem && data_anatomyReqTable.size()>0 && select == 2){
			
			int rowIndex = -1;
			int i = 0;
			for(AnatomyReq obj:data_anatomyReqTable){
				if(obj.getId().equals(selectItem.getId())){
					rowIndex = i;
					break;
				}
				i++;
			}
			if(rowIndex > -1){
				//选择原来行
				anatomyReqTable.getSelectionModel().clearAndSelect(rowIndex);
			}
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AnatomyReq.fxml"));
		Scene scene = new Scene(root, 791, 493);
		stage.setScene(scene);
		stage.setTitle("解剖申请");
//		stage.setResizable(false);
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.initOwner(Main.mainWidow);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}

	public static class AnatomyReq{
		
		private StringProperty id;             //id
		private StringProperty reqNo;          //申请编号
		private StringProperty studyNo;        //专题编号
		private StringProperty submitter;      //申请人（提交人）
		private StringProperty submitDate;     //申请日期（提交日期）
		private StringProperty benginDate;     //解剖日期（始）
		private StringProperty endDate;        //解剖日期（末）
		private StringProperty animalType;     //动物种类
		private StringProperty animalNum;      //动物数量
		private StringProperty tempFlag;       //临时   ，0：否     1：是 2:是（已确认）
		private StringProperty state;          //状态，未接受，已接收，已完成
		
		
		public AnatomyReq() {
		}
		public AnatomyReq(String id, int reqNo, String studyNo,
				String submitter, String submitDate, String benginDate,
				String endDate, String animalType, int animalNum,
				int tempFlag, String state) {
			super();
			this.id =  new SimpleStringProperty(id);
			this.reqNo =  new SimpleStringProperty(reqNo+"");
			this.studyNo =  new SimpleStringProperty(studyNo);
			this.submitter =  new SimpleStringProperty(submitter);
			this.submitDate =  new SimpleStringProperty(submitDate);
			this.benginDate =  new SimpleStringProperty(benginDate);
			this.endDate =  new SimpleStringProperty(endDate);
			this.animalType =  new SimpleStringProperty(animalType);
			this.animalNum =  new SimpleStringProperty(animalNum+"");
			if(tempFlag == 1){
				this.tempFlag =  new SimpleStringProperty("是");
			}else if(tempFlag == 2){
				this.tempFlag =  new SimpleStringProperty("是(已确认)");
			}else{
				this.tempFlag =  new SimpleStringProperty("否");
			}
			this.state =  new SimpleStringProperty(state);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id =  new SimpleStringProperty(id);
		}
		public String getReqNo() {
			return reqNo.get();
		}
		public void setReqNo(String reqNo) {
			this.reqNo =  new SimpleStringProperty(reqNo);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo =  new SimpleStringProperty(studyNo);
		}
		public String getSubmitter() {
			return submitter.get();
		}
		public void setSubmitter(String submitter) {
			this.submitter =  new SimpleStringProperty(submitter);
		}
		public String getSubmitDate() {
			return submitDate.get();
		}
		public void setSubmitDate(String submitDate) {
			this.submitDate =  new SimpleStringProperty(submitDate);
		}
		public String getBenginDate() {
			return benginDate.get();
		}
		public void setBenginDate(String benginDate) {
			this.benginDate =  new SimpleStringProperty(benginDate);
		}
		public String getEndDate() {
			return endDate.get();
		}
		public void setEndDate(String endDate) {
			this.endDate =  new SimpleStringProperty(endDate);
		}
		public String getAnimalType() {
			return animalType.get();
		}
		public void setAnimalType(String animalType) {
			this.animalType =  new SimpleStringProperty(animalType);
		}
		public String getAnimalNum() {
			return animalNum.get();
		}
		public void setAnimalNum(String animalNum) {
			this.animalNum =  new SimpleStringProperty(animalNum);
		}
		public String getTempFlag() {
			return tempFlag.get();
		}
		public void setTempFlag(String tempFlag) {
			this.tempFlag =  new SimpleStringProperty(tempFlag);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state =  new SimpleStringProperty(state);
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
