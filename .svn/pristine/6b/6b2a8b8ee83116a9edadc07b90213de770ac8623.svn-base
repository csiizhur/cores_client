package com.lanen.view.test;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.messager.Messager;

/**选择任务（组织学检查）
 * @author Administrator
 *
 */
public class SelectTask extends Application implements Initializable {
	@FXML
	private TableColumn<Task,String> creator;
	@FXML
	private TableColumn<Task,String> creatDate;
	@FXML
	private TableColumn<Task,String> anatomyRsn;
	@FXML
	private TableColumn<Task,String> animalNum;
	@FXML
	private TableColumn<Task,String> beginDate;
	@FXML
	private TableColumn<Task,String> endDate;
	@FXML
	private TableView<Task> taskTable;
	ObservableList<Task> data_taskTable = FXCollections.observableArrayList();

	@FXML
	private Label label;
	private static String studyNo = null;
	
	private static SelectTask instance;
	public static SelectTask getInstance(){
		if(null == instance){
			instance = new SelectTask();
		}
		return instance;
	}
	
	public SelectTask() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		initTable();
		
	}
	
	private void initTable() {
		taskTable.setItems(data_taskTable);
		creator.setCellValueFactory(new PropertyValueFactory<Task,String>("creator"));
		creatDate.setCellValueFactory(new PropertyValueFactory<Task,String>("creatDate"));
		anatomyRsn.setCellValueFactory(new PropertyValueFactory<Task,String>("anatomyRsn"));
		animalNum.setCellValueFactory(new PropertyValueFactory<Task,String>("animalNum"));
		beginDate.setCellValueFactory(new PropertyValueFactory<Task,String>("beginDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<Task,String>("endDate"));
		animalNum.setCellFactory(new Callback<TableColumn<Task,String>,TableCell<Task,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<Task, String> call(
	    			 TableColumn<Task, String> param) {
	    		 TableCell<Task, String> cell =
	    				 new TableCell<Task, String>() {
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
		taskTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					Task selectedItem = taskTable.getSelectionModel().getSelectedItem();
					if(null != selectedItem){
						//2.保存解剖动物信息
						String taskId = selectedItem.getTaskId();
						
						HistopathCheckPage.getInstance().loadData(taskId);
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					}
				}
				
			}});
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		Task selectedItem = taskTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			//2.保存解剖动物信息
			String taskId = selectedItem.getTaskId();
			
			HistopathCheckPage.getInstance().loadData(taskId);
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}else{
			showErrorMessage("请先选择解剖任务！");
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String studyNoLabelText) {
		SelectTask.studyNo  = studyNo;
		label.setText(studyNoLabelText);
		updateLabel();
		
		updateTaskTable();
	}

	
	private void updateTaskTable() {
		
		data_taskTable.clear();
		List<Map<String,Object>> maplist = BaseService.getInstance().getTblAnatomyTaskService().getMapListByStudyNo(SelectTask.studyNo);
//		select task.taskId,task.anatomyRsn,convert(varchar(10),task.taskCreateTime,120) as createDate"+
//		" 	,convert(varchar(10),req.beginDate,120) as beginDate,"+
//		" 	convert(varchar(10),req.endDate,120) as endDate,u.realName as creator,animal2.animalNum"+
		if(null != maplist && maplist.size() > 0){
			for(Map<String, Object> obj:maplist){
				String taskId = (String) obj.get("taskId");
				String anatomyRsn = (String) obj.get("anatomyRsn");
				String createDate = (String) obj.get("createDate");
				String beginDate = (String) obj.get("beginDate");
				String endDate = (String) obj.get("endDate");
				String creator = (String) obj.get("creator");
				Integer animalNum = (Integer) obj.get("animalNum");
				data_taskTable.add(new Task(taskId,creator,anatomyRsn,animalNum+"",createDate,beginDate,endDate));
			}
		}
	}

	private void updateLabel() {
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		if(null != studyPlan){
			label.setText(label.getText()+"    动物种类："+studyPlan.getAnimalType());
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("SelectTask.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("解剖任务");
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
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	/**任务
	 * @author Administrator
	 *
	 */
	public class Task{
		
		private StringProperty taskId;
		private StringProperty creator;
		private StringProperty creatDate;
		private StringProperty anatomyRsn;
		private StringProperty animalNum;
		private StringProperty beginDate;
		private StringProperty endDate;
		
		public Task(String taskId,String creator,String anatomyRsn,String animalNum,String creatDate,String beginDate,String endDate){
			setTaskId(taskId);
			setCreator(creator);
			setCreatDate(creatDate);
			setBeginDate(beginDate);
			setEndDate(endDate);
			setAnatomyRsn(anatomyRsn);
			setAnimalNum(animalNum);
		}
		
		public String getTaskId() {
			return taskId.get();
		}

		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}

		public String getCreator() {
			return creator.get();
		}
		public void setCreator(String creator) {
			this.creator = new SimpleStringProperty(creator);
		}
		public String getCreatDate() {
			return creatDate.get();
		}
		public void setCreatDate(String creatDate) {
			this.creatDate = new SimpleStringProperty(creatDate);
		}
		public String getBeginDate() {
			return beginDate.get();
		}
		public void setBeginDate(String beginDate) {
			this.beginDate = new SimpleStringProperty(beginDate);
		}
		public String getEndDate() {
			return endDate.get();
		}
		public void setEndDate(String endDate) {
			this.endDate = new SimpleStringProperty(endDate);
		}

		public String getAnatomyRsn() {
			return anatomyRsn.get();
		}

		public void setAnatomyRsn(String anatomyRsn) {
			this.anatomyRsn = new SimpleStringProperty(anatomyRsn);
		}

		public String getAnimalNum() {
			return animalNum.get();
		}

		public void setAnimalNum(String animalNum) {
			this.animalNum = new SimpleStringProperty(animalNum);
		}
		
		
	}

}
