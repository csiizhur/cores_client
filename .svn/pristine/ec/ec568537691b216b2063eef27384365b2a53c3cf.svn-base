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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblVisceraFixed;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

public class VisceraFixedEditPage extends Application implements Initializable{
	@FXML
	private TableView<VisceraFixed>  visceraFixedTable;
	private ObservableList<VisceraFixed> data_visceraFixedTable=FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<VisceraFixed,String> fixedIdCol;  //脏器固定记录ID
	@FXML
	private TableColumn<VisceraFixed,String> fixedStudyNoCol;  //脏器固定记录ID
	@FXML
	private TableColumn<VisceraFixed,String> animalCodeCol;  //动物编号
	@FXML
	private TableColumn<VisceraFixed,String> visceraNameCol; //脏器名称
	@FXML  
	private TableColumn<VisceraFixed,String> operatorCol;    //操作者
	@FXML
	private TableColumn<VisceraFixed,String> operateTimeCol; //操作时间
	@FXML
	private TableColumn<VisceraFixed,Boolean> operateCol;		//操作
	
	@FXML
	private TableView<AnatomyAnimal> anatomyAnimalTable;		//动物表格
	private ObservableList<AnatomyAnimal> data_anatomyAnimalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnatomyAnimal,String> studyNoCol;        //专题编号
//	@FXML
//	private TableColumn<AnatomyAnimal,String> anatomyNumCol;       //解剖序号(任务序号)
	@FXML
	private TableColumn<AnatomyAnimal,String> animalCodeCol_Animal;       //动物编号
//	@FXML
//	private TableColumn<AnatomyAnimal,String> genderCol;        //动物性别
//	@FXML
//	private TableColumn<AnatomyAnimal,String> animalIdCol;        //动物ID
	@FXML
	private TableColumn<AnatomyAnimal,String> sessionIdCol;       //会话Id
	
	@FXML
	private Button addButton;    //>>添加按钮
		
	
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();
	
	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	
	//会话id列表
	private static List<String> sessionIdList = null;
	//任务id列表
    private static List<String> taskIdList = null;
    //会话类型
    private static Integer sessionType;
	
	private static VisceraFixedEditPage instance;
	
	public static VisceraFixedEditPage getInstance(){
		if(null == instance){
			instance = new VisceraFixedEditPage();
		}
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initVisceraFixedTable();
		updateFixedTab();
		initTaskTable();
//		updateTable(taskIdList,sessionType);
		initTaskTree();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("VisceraFixedEdit.fxml"));
		Scene scene = new Scene(root, 768, 501);
		stage.setScene(scene);
		stage.setTitle("脏器固定数据编辑");
		stage.setResizable(false);
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
	@FXML
	private void onConfirmButton(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * 初始化脏器固定记录表
	 */
	public void initVisceraFixedTable(){
		visceraFixedTable.setItems(data_visceraFixedTable);
		fixedIdCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("id"));
		fixedStudyNoCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("studyNo"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("animalCode"));
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("visceraName"));
		operatorCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("operator"));
		operateTimeCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,String>("operateTime"));
		operateCol.setCellValueFactory(new PropertyValueFactory<VisceraFixed,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<VisceraFixed, Boolean>, TableCell<VisceraFixed, Boolean>>() {

            public TableCell<VisceraFixed, Boolean> call(TableColumn<VisceraFixed, Boolean> p) {

            	HyperlinkCell_Fixed<VisceraFixed, Boolean> cell = new HyperlinkCell_Fixed<VisceraFixed, Boolean>();
                return cell;

            }

        });
		
		
	}
	
	
	/**
	 * 更新脏器固定列表
	 */
	public void updateFixedTab(){
		data_visceraFixedTable.clear();
		sessionIdList=new ArrayList<String>();
		sessionIdList.add("20000000009");
		sessionIdList.add("20000000008");
		List<Map<String,Object>> list=BaseService.getInstance().getTblVisceraFixedService().getListBySessionIdAnimalCodeVisceraName(sessionIdList, null, null,null);
		if(null!=list&&list.size()>0){
			for(Map<String,Object> map:list){
				String id=(String) map.get("id");
				String studyNo=(String) map.get("studyNo");
				String animalCode=(String) map.get("animalCode");
				String visceraName=(String) map.get("visceraName");
				if(null!=(String) map.get("subVisceraName")){
					visceraName=visceraName+","+(String) map.get("subVisceraName");
				}
				String operator=(String) map.get("operator");
				SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
				Date operateTime=(Date) map.get("operateTime");
				String operateTimeStr= sdf.format(operateTime);
				data_visceraFixedTable.add(new VisceraFixed(id,studyNo,animalCode,visceraName,operator,operateTimeStr,true));
			}
		}
	}
	
	@FXML
	private void onAddButton(){
		AnatomyAnimal animal = anatomyAnimalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null == treeSelectedItem){
			showErrorMessage("请先选择脏器！");
			return ;
		}
		String animalCode = animal.getAnimalCode();
		String visceraName = treeSelectedItem.getValue();
//		Alert2.create(visceraName);
		Boolean flag=false;
		for(int i=0;i<data_visceraFixedTable.size();i++){
			if(data_visceraFixedTable.get(i).getAnimalCode().equals(animalCode) && 
			   data_visceraFixedTable.get(i).getVisceraName().equals(visceraName)){
				flag=true;
			}
		}
		if(flag==true){
			showErrorMessage("重复固定的脏器数据将忽略！");
			return;
		}else{
			String currentUserName = SaveUserInfo.getUserName();
//			Date date = new Date();
			Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
			String operateTimeStr= sdf.format(date);
			String id= BaseService.getInstance().getTblVisceraFixedService().getKey();
			String studyNo = animal.getStudyNo();
			String sessionId= animal.getSessionId();
			TblVisceraFixed tblVisceraFixed = new TblVisceraFixed();
			tblVisceraFixed.setId(id);
			tblVisceraFixed.setSessionId(sessionId);
			tblVisceraFixed.setStudyNo(studyNo);
			tblVisceraFixed.setAnimalCode(animalCode);
			Map<String,Object> visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
			Integer visceraType = (Integer) visceraMap.get("visceraType");
			String visceraCode = (String) visceraMap.get("visceraCode");
//			String visceraName = (String) visceraMap.get("visceraName");
			String subVisceraCode = (String) visceraMap.get("subVisceraCode");
			String subVisceraName = (String) visceraMap.get("subVisceraName");
			tblVisceraFixed.setVisceraType(visceraType);
			tblVisceraFixed.setVisceraName(visceraName);
			tblVisceraFixed.setVisceraCode(visceraCode);
			tblVisceraFixed.setSubVisceraCode(subVisceraCode);
			tblVisceraFixed.setSubVisceraName(subVisceraName);
			tblVisceraFixed.setOperator(currentUserName);
			tblVisceraFixed.setOperateTime(date);
			BaseService.getInstance().getTblVisceraFixedService().save(tblVisceraFixed);
			data_visceraFixedTable.add(new VisceraFixed(id,studyNo,animalCode,visceraName,currentUserName,operateTimeStr,true));
			
		}
	}
	
	/**
	 * 初始化动物 table
	 */
	private void initTaskTable() {
		anatomyAnimalTable.setItems(data_anatomyAnimalTable);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("studyNo"));
//		animalIdCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("animalId"));
		animalCodeCol_Animal.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("animalCode"));
//		genderCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("gender"));
//		anatomyNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("anatomyNum"));
		anatomyAnimalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyAnimal>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyAnimal> arg0, AnatomyAnimal arg1, AnatomyAnimal newValue) {
				if(null != newValue){
					//  动物表格 onChange
					
					String taskId = newValue.getTaskId();
//					taskId="20000000010";
//					updateVisceraTree(taskId);
				}
			}
			
			
		});
	}
//	/**更新动物表格数据
//	 * @param taskIdList2
//	 */
//	private void updateTable(List<String> taskIdList2,Integer sessionType) {
//		sessionIdList=new ArrayList<String>();
//		sessionIdList.add("20000000009");
//		sessionIdList.add("20000000008");
//		data_anatomyAnimalTable.clear();
//		sessionType=2;
//		new BaseService();
//		List<Map<String,Object>> taskMapList = 
//				BaseService.getTblAnatomyAnimalService().getListBySessionIdList2(sessionIdList);
//		if(null != taskMapList && taskMapList.size()>0){
//			for(Map<String,Object> map:taskMapList){
//				String studyNo = (String) map.get("studyNo");
////				String animalId = (String) map.get("animalId");
//				String animalCode = (String) map.get("animalCode");
////				Integer gender = (Integer) map.get("gender");
////				Integer anatomyNum = (Integer) map.get("anatomyNum");
//				String taskId = (String) map.get("taskId");
//				String sessionId = (String) map.get("sessionId");
////				Alert2.create(sessionId);
//				
//				data_anatomyAnimalTable.add(new AnatomyAnimal(studyNo,null,animalCode,null,null,taskId,sessionId));
//			}
//		}
//		
//	}
	
	/**
	 * 初始化选择脏器树
	 */
	private void initTaskTree() {
		rootNode.setValue("选择脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				// 选择脏器树      change事件
				if(null != newValue){
					String visceraName = newValue.getValue();
					Map<String,Object> map = visceraName2MapMap.get(visceraName);
					if(null != map){
						String visceraCode = (String) map.get("visceraCode");
					}
				}else{
				}
				
			}
			
		});
	}
	
	/**
	 * 更新 选择脏器树 ，根据任务id
	 */
//	private void updateVisceraTree(String taskId){
//		//清空树
//		rootNode.getChildren().clear();
//		
//		visceraName2MapMap.clear();
//		visceraCodeTreeItemMap.clear();
//		if(null != taskId){
////			List<Map<String,Object>> mapList = BaseService
////					.getTblAnatomyCheckService().getVisceraCodeAndNameByTaskId(taskId);
//			if(null != mapList && mapList.size() > 0 ){
//				for(Map<String,Object> map:mapList){
//					String visceraCode = (String) map.get("visceraCode");
//					String visceraName = (String) map.get("visceraName");
//					String subVisceraCode = (String) map.get("subVisceraCode");
//					String subVisceraName = (String) map.get("subVisceraName");
//					TreeItem<String> depNode = null;
//					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
//						depNode = visceraCodeTreeItemMap.get(visceraCode);
//					}else{
//						depNode = new TreeItem<String>(visceraName);
//						visceraName2MapMap.put(visceraName, map);
//						rootNode.getChildren().add(depNode);
//						visceraCodeTreeItemMap.put(visceraCode, depNode);
//					}
//					if(null != subVisceraCode && !"".equals(subVisceraCode)){
//						TreeItem<String> leafNode = new TreeItem<String>();
//						leafNode.setValue(subVisceraName);
//						visceraName2MapMap.put(subVisceraName, map);
//						depNode.getChildren().add(leafNode);
//					}
//				}
//			}
//		}
//	}
	
	/**固定记录
	 * @author Administrator
	 *
	 */
	public static class VisceraFixed{
		private StringProperty id;			//id
		private StringProperty studyNo;     //课题编号
//		private StringProperty sessionId;	//会话id
		private StringProperty animalCode;  //动物编号
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty operator;	//操作者
		private StringProperty operateTime; //操作时间
		private BooleanProperty  operate;	//操作
		
		public VisceraFixed() {
			super();
		}
		public VisceraFixed(String id,String studyNo, String animalCode,  String visceraName,String operator,String operateTime,boolean operate) {
			this.id = new SimpleStringProperty(id);
			this.studyNo = new SimpleStringProperty(studyNo);
			this.animalCode= new SimpleStringProperty(animalCode);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.operator = (new SimpleStringProperty(operator));
			this.operateTime = (new SimpleStringProperty(operateTime));
			this.operate = new SimpleBooleanProperty(operate);
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
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
		
	}
	
	
	
	/**固定信息专用
	 * @author Administrator
	 *
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell_Fixed<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
		private ObservableValue<T> ov;  
		private Map<String,String> vmap;  
		
		public Map<String, String> getVmap() {  
			return vmap;  
		}  
		
		public void setVmap(Map<String, String> vmap) {  
			this.vmap = vmap;  
		}  
		
		public HyperlinkCell_Fixed() {  
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
					}
				} 
				//	            System.out.println(this.getTableRow().getItem().getClass().getSimpleName());
				hyperlink.setText("删除");
				hyperlink.setPrefWidth(50);
				hyperlink.setPrefHeight(20);
				hyperlink.setUserData(this.getTableRow().getItem());
				final TableView<?> tableView = this.getTableView();
				hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){
					
					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						VisceraFixed visceraFixed = (VisceraFixed) hyperlink.getUserData();
//						Json json = BaseService.getTblVisceraFixedService().deleteOne(visceraFixed.getId());
//						if(json.isSuccess()){
//							tableView.getItems().remove(visceraFixed);
//						}else{
//							showErrorMessage(json.getMsg());
//						}
						BaseService.getInstance().getTblVisceraFixedService().delete(visceraFixed.getId());
						tableView.getItems().remove(visceraFixed);
					}
				});
			}  
		}  
	} 
	/**解剖动物*/
	public static class AnatomyAnimal{
		private StringProperty studyNo; // 专题编号
		private StringProperty animalId;//申请编号
		private StringProperty animalCode;//动物编号
		private StringProperty gender;//动物性别
		private StringProperty anatomyNum;//解剖序号(任务序号)
		private StringProperty taskId;		//任务Id号
		private StringProperty sessionId;   //会话Id
		public AnatomyAnimal(){
			super();
		}

		public AnatomyAnimal(String studyNo,Integer anatomyNum,String animalCode,Integer gender,String animalId,String taskId,String sessionId){
			this.studyNo = new SimpleStringProperty(studyNo);
			this.animalId = new SimpleStringProperty(animalId);
			this.animalCode = new SimpleStringProperty(animalCode);
			String genderStr = "";
			if(null != gender && gender == 1){
				genderStr = "♂";
			}else if(null != gender && gender == 2){
				genderStr = "♀";
			}
			this.gender = new SimpleStringProperty(genderStr);
			this.anatomyNum = new SimpleStringProperty(anatomyNum == null ? "":anatomyNum+"");
			this.taskId = new SimpleStringProperty(taskId);
			this.sessionId = new SimpleStringProperty(sessionId);
		}
		
		public String getStudyNo() {
			return studyNo.get();
		}

		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
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

		public String getAnatomyNum() {
			return anatomyNum.get();
		}

		public void setAnatomyNum(String anatomyNum) {
			this.anatomyNum =new SimpleStringProperty(anatomyNum);
		}

		public String getAnimalId() {
			return animalId.get();
		}

		public void setAnimalId(String animalId) {
			this.animalId = new SimpleStringProperty(animalId);
		}

		public String getTaskId() {
			return taskId.get();
		}

		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}

		public String getSessionId() {
			return sessionId.get();
		}

		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
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
