package com.lanen.view.tblsession;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.DictGeneralObservation;
import com.lanen.model.quarantine.tblsession.TblGeneralObservation;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.TblGeneralObservationForTableView;
import com.lanen.model.tableview.TblSessionAnimalForTableView;
import com.lanen.model.tableview.TblTraceForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

public class GeneralObservationFrame extends Application implements Initializable {

	@FXML
	private Label createrLabel;
	@FXML
	private Label animalTypeLabel;
	@FXML
	private Label createTimeLabel;
	@FXML
	private Label sessionTypeLabel;
	@FXML
	private Label studyNoLabel;
	
	
	/**
	 * 动物列表
	 */
	@FXML
	private static TableView<TblSessionAnimalForTableView> animalTable;
	private static ObservableList<TblSessionAnimalForTableView> data_animalTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblSessionAnimalForTableView,String> animalIdCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView,String> genderCol;
	
	/**
	 * 一般观察树
	 */
	@FXML
	private TreeView<String> treeView;  //一般观察树
	private TreeItem<String> rootNode_treeView=new TreeItem<String>("一般观察树");
	/**
	 * 未见异常
	 */
	@FXML
	private CheckBox checkBox;
	
	//选中一般观察树中的   观察所见
	private String observation="";
	private String observationCode="";
	
	//选中的动物列表中的动物
	private String animalId ="";
	private int gender = 0;
	
	/**
	 * 一般观察表
	 */
	@FXML
	private TableView<TblGeneralObservationForTableView> tblGeneralObservationTable;
	private ObservableList<TblGeneralObservationForTableView> data_tblGeneralObservationTable=
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblGeneralObservationForTableView,String> animalIdCol_1;
	@FXML
	private TableColumn<TblGeneralObservationForTableView,String> observationCol_1;
	@FXML
	private TableColumn<TblGeneralObservationForTableView,String> recordTimeCol_1;
	
	/**
	 * 添加 button
	 */
	@FXML
	private Button addButton;
	/**
	 * 删除  button
	 */
	@FXML
	private Button delButton;
	/**
	 * 其余动物未见异常 button
	 */
	@FXML
	private Button othersButton;
	
	/**
	 * 修改痕迹表格
	 */
	@FXML
	private TableView<TblTraceForTableView> tblTraceTable;
	/**
	 * 修改痕迹表格数据集
	 */
	private static ObservableList<TblTraceForTableView> data_tblTraceTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblTraceForTableView,String> animalIdCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> operateModeCode_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> oldValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> newValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> operatorCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyReasonCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyTimeCol_2;
	
	
	private static TblSession tblSession = null;
	private static String sessionId ="";
	private static List<TblSessionAnimals> tblSessionAnimalsList = null;
	private static String signId ="";
	private static String checkId="";
	private static Date beginDate =null;   //开始采集时间
	/**离开页面时，是否需要更新DayToDayPane上tblSession表数据*/
	private static boolean updateFlag  = false;//离开页面时，是否需要更新DayToDayPane上tblSession表数据
	
	private static GeneralObservationFrame instance;
	public static GeneralObservationFrame getInstance(){
		if(null == instance){
			instance = new GeneralObservationFrame();
		}
		updateFlag = false;
		return instance;
	}
	public GeneralObservationFrame(){
		updateFlag = false;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化animalTable
		initAnimalTable();
		//初始化一般观察树并加载数据
		initTreeView();
		//修改痕迹
		ininTblTraceTalbe();
		//初始化tblGeneralObservationTable及更新数据
		ininTblGeneralObservationTable();
		
		othersButton.setTooltip(new Tooltip("其余动物未见异常"));
	}
	

	/**
	 * 初始化animalTable
	 */
	private void initAnimalTable() {
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>("animalId"));
		genderCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>("gender"));
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		genderCol.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView, String>, TableCell<TblSessionAnimalForTableView, String>>(){

			@Override
			public TableCell<TblSessionAnimalForTableView, String> call(
					TableColumn<TblSessionAnimalForTableView, String> arg0) {
				TableCell<TblSessionAnimalForTableView, String> cell = 
						new TableCell<TblSessionAnimalForTableView, String>(){

							@Override
							protected void updateItem(String item, boolean empty) {
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
		animalTable.setItems(data_animalTable);
		animalTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<TblSessionAnimalForTableView>() {

			@Override
			public void changed(ObservableValue<? extends TblSessionAnimalForTableView> arg0,
					TblSessionAnimalForTableView arg1, TblSessionAnimalForTableView newValue) {
				if(null != newValue){
					animalId = newValue.getAnimalId();
					if(newValue.getGender().equals("♂")){
						gender = 1;
					}else{
						gender = 2;
					}
				}else{
					animalId = "";
					gender = 0;
				}
			}
		});
	}
	/**
	 * 初始化一般观察树并加载数据
	 */
	private void initTreeView() {
		treeView.setRoot(rootNode_treeView);
		rootNode_treeView.setExpanded(true);
		List<DictGeneralObservation> nodeList = 
				BaseService.getDictGeneralObservationService().getNodeList();
		if(null!=nodeList && nodeList.size()>0){
			for(DictGeneralObservation obj:nodeList){
				String nodeName = obj.getItemType();
				TreeItem<String> node = new TreeItem<String>(nodeName);
				node.setExpanded(true);//父节点展开
				rootNode_treeView.getChildren().add(node);
				List<DictGeneralObservation> leafList =
						BaseService.getDictGeneralObservationService().getLeafList(nodeName);
				if(null!=leafList && leafList.size()>0){
					for(DictGeneralObservation entity:leafList){
						TreeItem<String> leaf = new TreeItem<String>(entity.getItemName()+"("+entity.getObservationCode()+")");
						node.getChildren().add(leaf);
					}
				}
			}
		}
		
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> ob,
					TreeItem<String> oldValue, TreeItem<String> newValue) {
				if (null != newValue) {
					if(newValue.isLeaf()){
						observation = newValue.getValue();
						int index0 = observation.indexOf("(");
						int index1 = observation.indexOf(")");
						if(index0+1<index1){
							observationCode = observation.substring(index0+1,index1);
						}else{
							observationCode = "";
						}
						observation = observation.substring(0,index0);
					}else{
						observation ="";
						observationCode ="";
					}
					//按钮置空
					checkBox.setSelected(false);
				}else{
					observation ="";
					observationCode ="";
				}
				
			}});
	}
	/**未见异常多选框    onAction
	 * @param event
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event){
		if(checkBox.isSelected()){
			treeView.getSelectionModel().clearSelection();
			observation="-";
			observationCode="00";
			
		}else{
			observation="";
			observationCode="";
		}
	}
	/**
	 * 初始化tblGeneralObservationTable及更新数据
	 */
	private void ininTblGeneralObservationTable() {
		animalIdCol_1.setCellValueFactory(
				new PropertyValueFactory<TblGeneralObservationForTableView, String>("animalId"));
		observationCol_1.setCellValueFactory(
				new PropertyValueFactory<TblGeneralObservationForTableView, String>("observation"));
		recordTimeCol_1.setCellValueFactory(
				new PropertyValueFactory<TblGeneralObservationForTableView, String>("recordTime"));
		observationCol_1.setCellFactory(new Callback<TableColumn<TblGeneralObservationForTableView,String>,TableCell<TblGeneralObservationForTableView,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<TblGeneralObservationForTableView, String> call(
	    			 TableColumn<TblGeneralObservationForTableView, String> param) {
	    		 TableCell<TblGeneralObservationForTableView, String> cell =
	    				 new TableCell<TblGeneralObservationForTableView, String>() {
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
		tblGeneralObservationTable.setItems(data_tblGeneralObservationTable);
		updateTblGeneralObservationTable();
	}
	/**
	 * 更新表格数据tblGeneralObservationTable
	 */
	private void updateTblGeneralObservationTable(){
		data_tblGeneralObservationTable.clear();
		List<TblGeneralObservation> tblGeneralObservationList = 
				BaseService.getTblGeneralObservationService().getListBySessionId(sessionId);
		if(null !=tblGeneralObservationList && tblGeneralObservationList.size()>0){
			for(TblGeneralObservation obj:tblGeneralObservationList){
				data_tblGeneralObservationTable.add(new TblGeneralObservationForTableView(
						obj.getId(),obj.getAnimalId(),obj.getObservation(),obj.getRecordTime()));
			}
		}
		
	}
	
	
	
	
	/**
	 * 初始化tblTraceTalbe
	 */
	private void ininTblTraceTalbe() {
		animalIdCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("animalId"));
		operateModeCode_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("operateMode"));
		oldValueCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("oldValue"));
		newValueCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("newValue"));
		operatorCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("operator"));
		modifyReasonCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("modifyReason"));
		modifyTimeCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("modifyTime"));
		
		tblTraceTable.setItems(data_tblTraceTable);
	}



	/**
	 * 加载animalTable数据
	 */
	private void loadData_animalTable(){
		data_animalTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			List<TblSessionAnimals> animalList = BaseService.getTblSessionAnimalsService().getListBySessionId(sessionId);
			if(null != animalList && animalList.size()>0){
				String animalId = "";
				int gender = 0;
				String room = "";
				for(TblSessionAnimals obj:animalList){ 
					animalId =obj.getAnimalId();
					gender = obj.getGender();
					room = obj.getRoom();
					data_animalTable.add(new TblSessionAnimalForTableView(
							animalId,gender,room));
				}
			}
		}
	}
	/**
	 * 加载animalTable数据(暂无sessionId 使用)
	 */
	private void loadData_animalTable(List<TblSessionAnimals> tblSessionAnimalsList){
		data_animalTable.clear();
			if(null != tblSessionAnimalsList && tblSessionAnimalsList.size()>0){
				String animalId = "";
				int gender = 0;
				String room = "";
				for(TblSessionAnimals obj:tblSessionAnimalsList){ 
					animalId =obj.getAnimalId();
					gender = obj.getGender();
					room = obj.getRoom();
					data_animalTable.add(new TblSessionAnimalForTableView(
							animalId,gender,room));
				}
			}
	}
	
	/**
	 * 更新TblTraceTable数据
	 */
	private void updateTblTraceTable(){
		data_tblTraceTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			List<TblTrace> tblTraceList = BaseService.getTblTraceservice().
					getListByTableNameAndDataId("TblGeneralObservation",sessionId);
			if(null != tblTraceList && tblTraceList.size()>0){
				for(TblTrace tblTrace : tblTraceList){
					String newValue = tblTrace.getNewValue();
					String[] newValues = newValue.split(",");
					String animalId = newValues[0];
					switch (tblTrace.getOperateMode()) {
					case 1:
						newValue = newValues[1];
						break;
					case 2:
						newValue ="";
						break;
					case 3:
						newValue = newValues[1];
						break;
					default:
						break;
					}
					data_tblTraceTable.add(new TblTraceForTableView(
							animalId,tblTrace.getOldValue(),newValue,
							tblTrace.getOperator(),tblTrace.getModifyReason(),
							tblTrace.getModifyTime(),tblTrace.getOperateMode()
							));
				}
			}
		}
	}
	/**
	 * 增加修改痕迹数据
	 */
	private void addTblTraceTable(TblTrace tblTrace) {
		if (null != tblTrace) {
			String newValue = tblTrace.getNewValue();
			String[] newValues = newValue.split(",");
			String animalId = newValues[0];
			switch (tblTrace.getOperateMode()) {
			case 1:
				newValue = newValues[1];
				break;
			case 2:
				newValue ="";
				break;
			case 3:
				newValue = newValues[1];
				break;
			default:
				break;
			}
			data_tblTraceTable.add(new TblTraceForTableView(
					animalId,tblTrace.getOldValue(),newValue,
					tblTrace.getOperator(),tblTrace.getModifyReason(),
					tblTrace.getModifyTime(),tblTrace.getOperateMode()
					));
		}
	}
	
	
	/**
	 * 增加按钮
	 */
	@FXML
	private void onAddButton() {
		// 添加
		if (signId == null || "".equals(signId)) {
			// .未签字
			// 1.检查输入项
			if (null == animalId || animalId.isEmpty()) {
				Alert2.create("请先选择动物");
				return;
			}
			if (0 == gender) {
				Alert2.create("请先选择动物");
				return;
			}
			if (null == observation || observation.isEmpty()) {
				Alert2.create("请先选择观察所见内容");
				return;
			}
			if (null == observationCode || observationCode.isEmpty()) {
				Alert2.create("请先选择观察所见内容");
				return;
			}
			
			//1.5 如果无 sessionId ,则先保存 会话和会话动物列表
			if(null == sessionId || "".equals(sessionId)){
				sessionId = BaseService.getTblSessionService().
						saveTblSessionAndAnimalList(tblSession, tblSessionAnimalsList);
				tblSession.setSessionId(sessionId);
			}
			
			//判断是否已存在
			boolean isExist = false;
			if(observation.equals("-")){
				//待添加的正常，则不可以存在  观察结果
				isExist = BaseService.getTblGeneralObservationService().isExist(sessionId, animalId);
				if(isExist){
					Alert2.create("该动物已有观察结果，不可提交'正常'结果");
					return;
				}
			}else{
				isExist = BaseService.getTblGeneralObservationService().isExist(sessionId,animalId,observation);
				if(isExist){
					Alert2.create("该记录已存在或存在‘正常’结果");
					return;
				}
				
			}
			// 2.一般观察内容
			TblGeneralObservation tblGeneralObservation = new TblGeneralObservation();
			tblGeneralObservation.setSessionId(sessionId);
			tblGeneralObservation.setAnimalId(animalId);
			tblGeneralObservation.setGender(gender);
			tblGeneralObservation.setObservation(observation);
			tblGeneralObservation.setObservationCode(observationCode);
			Date date = new Date();
			tblGeneralObservation.setRecordTime(date);
			tblGeneralObservation.setTblSession(tblSession);
			String id = BaseService.getTblGeneralObservationService().saveNoIdReturnId(tblGeneralObservation);

			// 3.如果是第一个 一般观察， 更新会话里的beginDate
			if (null == beginDate) {
				beginDate = date;
				tblSession.setBeginTime(beginDate);
				BaseService.getTblSessionService().update(tblSession);
				updateFlag = true;
			}

			// 4.tblGeneralObservationTable表中添加一行记录
			TblGeneralObservationForTableView tblGeneralObservationForTableView = new TblGeneralObservationForTableView(
					id,animalId, observation, date);
			data_tblGeneralObservationTable.add(tblGeneralObservationForTableView);
			if(data_tblGeneralObservationTable.size()>2){
				tblGeneralObservationTable.scrollTo(1000);
				tblGeneralObservationTable.getSelectionModel().selectLast();
			}
			
			
		} else if (checkId == null || "".equals(checkId)) {
			// .已签字，未复核
			// 1.检查输入项
			if (null == animalId || animalId.isEmpty()) {
				Alert2.create("请先选择动物");
				return;
			}
			if (0 == gender) {
				Alert2.create("请先选择动物");
				return;
			}
			if (null == observation || observation.isEmpty()) {
				Alert2.create("请先选择观察所见内容");
				return;
			}
			if (null == observationCode || observationCode.isEmpty()) {
				Alert2.create("请先选择观察所见内容");
				return;
			}
			//判断是否已存在
			boolean isExist = false;
			if(observation.equals("-")){
				//待添加的正常，则不可以存在  观察结果
				isExist = BaseService.getTblGeneralObservationService().isExist(sessionId, animalId);
				if(isExist){
					Alert2.create("该动物已有观察结果，不可提交'正常'结果");
					return;
				}
			}else{
				isExist = BaseService.getTblGeneralObservationService().isExist(sessionId,animalId,observation);
				if(isExist){
					Alert2.create("该记录已存在或存在‘正常’结果");
					return;
				}
				
			}
			// 2.提示，并电子签名
			if (Confirm
					.create(Main.getInstance().getStage(), "提示", "一般观察已签字，新增信息将记录修改痕迹", "确定继续吗？")) {
				// 电子签名及原因
				SignFrameWithReason signFrameWithReason = new SignFrameWithReason("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("身份确认");
				try {
					signFrameWithReason.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 签字通过
			if ("OK".equals(SignFrameWithReason.getType())) {
				// 3.保存修改痕迹
				// 动物Id号+“，”+观察所见
				String newValue = animalId + "," +observation;
				Date date = new Date();
				TblTrace tblTrace = new TblTrace();
				tblTrace.setTableName("TblGeneralObservation");// 表名
				tblTrace.setDataId(sessionId); // 这里放的是会话Id
				tblTrace.setOperateMode(3);// 增加
				tblTrace.setOldValue("");
				tblTrace.setNewValue(newValue);
				tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
				tblTrace.setHost(SystemTool.getIPAddress());
				tblTrace.setModifyReason(SignFrameWithReason.getReason());
				tblTrace.setModifyTime(date);
				BaseService.getTblTraceservice().save(tblTrace);
				// 4.增加数据()
				TblGeneralObservation tblGeneralObservation = new TblGeneralObservation();
				tblGeneralObservation.setSessionId(sessionId);
				tblGeneralObservation.setAnimalId(animalId);
				tblGeneralObservation.setGender(gender);
				tblGeneralObservation.setObservation(observation);
				tblGeneralObservation.setObservationCode(observationCode);
				tblGeneralObservation.setRecordTime(date);
				tblGeneralObservation.setTblSession(tblSession);
				String id = BaseService.getTblGeneralObservationService().saveNoIdReturnId(tblGeneralObservation);
				// 5.tblGeneralObservationTable表中添加一行记录
				TblGeneralObservationForTableView tblGeneralObservationForTableView = new TblGeneralObservationForTableView(
						id,animalId, observation, date);
				data_tblGeneralObservationTable.add(tblGeneralObservationForTableView);
				tblGeneralObservationTable.scrollTo(1000);
				tblGeneralObservationTable.getSelectionModel().selectLast();
				// 6. 更新修改痕迹表格
				addTblTraceTable(tblTrace);
			}
		} else {
			Alert2.create("已复核，无法修改数据");
			return;
		}
	}
	/**
	 * 删除按钮
	 */
	@FXML
	private void onDelButton() {
		if (signId == null || "".equals(signId)) {
			// .未签字
			// 1.检查
			TblGeneralObservationForTableView selectItem = 
					tblGeneralObservationTable.getSelectionModel().getSelectedItem();
			if (null == selectItem) {
				Alert2.create("请先选择待删除记录");
				return;
			}

			// 2.删除
			BaseService.getTblGeneralObservationService().delete(selectItem.getId());

			// 3.删除 tblGeneralObservationTable表中记录
			data_tblGeneralObservationTable.remove(selectItem);
			tblGeneralObservationTable.getSelectionModel().clearSelection();

		} else if (checkId == null || "".equals(checkId)) {
			// .已签字，未复核
			// 1.检查
			TblGeneralObservationForTableView selectItem = tblGeneralObservationTable
					.getSelectionModel().getSelectedItem();
			if (null == selectItem) {
				Alert2.create("请先选择待删除记录");
				return;
			}
			// 2.提示，并电子签名
			if (Confirm
					.create(Main.getInstance().getStage(), "提示", "一般观察已签字，删除信息将记录修改痕迹", "确定继续吗？")) {
				// 电子签名及原因
				SignFrameWithReason signFrameWithReason = new SignFrameWithReason("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("身份确认");
				try {
					signFrameWithReason.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 签字通过
			if ("OK".equals(SignFrameWithReason.getType())) {
				// 3.保存修改痕迹
				// 动物Id号+“，”+观察所见
				String animalId = selectItem.getAnimalId();
				String observation = selectItem.getObservation();
				String oldValue = observation;
				String newValue = animalId;
				Date date = new Date();
				TblTrace tblTrace = new TblTrace();
				tblTrace.setTableName("TblGeneralObservation");// 表名
				tblTrace.setDataId(sessionId); // 这里放的是会话Id
				tblTrace.setOperateMode(2);// 删除
				tblTrace.setOldValue(oldValue);
				tblTrace.setNewValue(newValue);
				tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
				tblTrace.setHost(SystemTool.getIPAddress());
				tblTrace.setModifyReason(SignFrameWithReason.getReason());
				tblTrace.setModifyTime(date);
				BaseService.getTblTraceservice().save(tblTrace);
				// 2.删除
				BaseService.getTblGeneralObservationService().delete(selectItem.getId());

				// 3.删除 tblGeneralObservationTable表中记录
				data_tblGeneralObservationTable.remove(selectItem);
				tblGeneralObservationTable.getSelectionModel().clearSelection();
				// 6. 更新修改痕迹表格
				addTblTraceTable(tblTrace);
			}
		} else {
			Alert2.create("已复核，无法修改数据");
			return;
		}
	}
	/**
	 * 其他未见异常
	 */
	@FXML
	private void onOthersButton(){
		//未签字可以使用，签完字后不可用
		if (signId == null || "".equals(signId)) {
			//是否存在        未观察动物
			boolean isExist = false;
			isExist = BaseService.getTblGeneralObservationService().isExistNoObservation(sessionId);
			if(isExist){
				//其他动物未见异常
				BaseService.getTblGeneralObservationService().observationOthers(sessionId);
				//更新一般观察表
				updateTblGeneralObservationTable();
				tblGeneralObservationTable.scrollTo(1000);
			}else{
				Alert2.create("不存在未记录观察结果的动物");
			}
		}
	}
	/**
	 * 签字
	 */
	
	@FXML
	private void onSignButton(){
		if(null != signId && !signId.isEmpty()){
			Alert2.create("已签字，不可重复签字！");
			return;
		}
		//是否存在        未观察动物
		boolean isExist = false;
		if(null ==sessionId || "".equals(sessionId)){
			isExist =true;
		}else{
			isExist = BaseService.getTblGeneralObservationService().isExistNoObservation(sessionId);
		}
		if(isExist){
			Alert2.create("存在未记录观察结果的动物");
			return ;
		}
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("签字");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(SignFrame.getType())){
			signId = BaseService.getTblSessionService().sign(sessionId, 12, 
					SignFrame.getUser().getRealName(), "检疫，一般观察签字");
			othersButton.setDisable(true);
			updateFlag = true;
			Alert.create("签字成功");
		}
	}
	/**
	 * 复核
	 */
	@FXML
	private void onCheckButton(){
		if(null == signId || signId.isEmpty()){
			Alert2.create("未签字，请先签字！");
			return;
		}
		if(null != checkId && !checkId.isEmpty()){
			Alert2.create("已复核，不可重复复核！");
			return;
		}
		//是否存在        未观察动物
		boolean isExist = false;
		isExist = BaseService.getTblGeneralObservationService().isExistNoObservation(sessionId);
		if(isExist){
			Alert2.create("存在未记录观察结果的动物");
			return ;
		}
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("复核");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(SignFrame.getType())){
			checkId = BaseService.getTblSessionService().check(sessionId, 13, 
					SignFrame.getUser().getRealName(), "检疫，一般观察复核");
			
			updateFlag = true;
			addButton.setDisable(true);
			delButton.setDisable(true);
			othersButton.setDisable(true);
			Alert.create("复核成功");
		}
		
	}
	/**
	 * 打印
	 */
	@FXML
	private void onPrintButton(){
		if(null != checkId && !checkId.isEmpty()){
	 		JasperReport jr = null;
	 		JasperPrint jp = null;
	        try {
				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblGeneralObservation.jrxml"));
			} catch (JRException e) {
				e.printStackTrace();
				Alert2.create("报表加载失败");
				return ;
			}
	        
	        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
	        
	        TblSession tblSession  = BaseService.getTblSessionService().getById(sessionId);
	        if(tblSession!=null){
	        	/**编号*/
	        	String number =BaseService.getDictOutputSettingsService().getShowByLabel("动物一般观察记录表-编号");         
	        	String endDate =DateUtil.dateToString(tblSession.getEndTime(), "yyyy-MM-dd");//  
	        	String studyNo = tblSession.getStudyNo();
	        	String animalStrain=tblSession.getAnimalStrain();//   
	        	String signerAndDate ="";
	        	String checkerAndDate ="";
	        	signId = tblSession.getSignId();
	        	checkId= tblSession.getCheckId();
	        	TblES tblES_signer = BaseService.getTblESService().getById(signId);
	        	TblES tblES_checker =  BaseService.getTblESService().getById(checkId);
	        	if(null != tblES_signer){
	        		signerAndDate =tblES_signer.getSigner()+" "+
	        				DateUtil.dateToString(tblES_signer.getDateTime(), "yyyy-MM-dd");
	        	}
	        	if(null != tblES_checker){
	        		checkerAndDate =tblES_checker.getSigner()+" "+
	        				DateUtil.dateToString(tblES_checker.getDateTime(), "yyyy-MM-dd");
	        	}
	        	
	        	 Map<String,Object> map = new HashMap<String,Object>();
	             map.put("logoImage",logoImage);
	             map.put("number",number);
	             map.put("endDate",endDate);
	             map.put("animalStrain",animalStrain);
	             map.put("signerAndDate",signerAndDate);
	             map.put("checkerAndDate",checkerAndDate);
	     		if(studyNo != null && !studyNo.isEmpty()){
	     			 map.put("studyNo",studyNo);
	     			 List<TblGeneralObservation> tblGeneralObservationList = 
	     					 BaseService.getTblGeneralObservationService().getListBySessionId(sessionId);
	     			 List<Map<String,String>> mapList = getMapList(tblGeneralObservationList);
	     			 if(mapList !=null && mapList.size()>0){
	     				 
	     				 int size =  mapList.size();
	     				 int count = size%62;
	     				 int max = 0;//增加空行数量
	     				 if(count !=0){
	     					 max = 62 - count;
	     				 }
	     				 for(int i=0;i<max;i++){
	     					mapList.add(new HashMap<String,String>());
	     				 }
	    	             try {
	    	     			jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(mapList));
	    	     		} catch (JRException e) {
	    	     			e.printStackTrace();
	    	     			Alert2.create("报表加载失败");
	    	     			return ;
	    	     		}
	    	             Main.getInstance().openJFrame(jp, "动物一般观察记录表");
	    	     		
	     			 }
	     		}else{
	     			String recId = tblSession.getRecId();
	     			List<String> studyNoList = BaseService.getTblGeneralObservationService()
	     					.findStudyNoListByRecIdSessionId(recId,sessionId);
	     			int listSize = 0;
	     			if(null !=studyNoList && studyNoList.size()>1){
	     				//选择课题编号
	     				ChooseStudyNoFrame chooseStudyNoFrame =new  ChooseStudyNoFrame();
	     				try {
	     					
	     					chooseStudyNoFrame.updateData(studyNoList);
	     					chooseStudyNoFrame.start(new Stage());
	     				} catch (Exception e1) {
	     					e1.printStackTrace();
	     				}
	     			}else if(null !=studyNoList && studyNoList.size()==1){
	     				listSize = 1;
	     				studyNo = studyNoList.get(0);
	     			}else{
	     				
	     				return ;
	     			}
	     			if("OK".equals(ChooseStudyNoFrame.getType()) || listSize == 1){
	     				if(listSize != 1){
	     					studyNo=ChooseStudyNoFrame.getStudyNo();
	     				}
	     				map.put("studyNo",studyNo);
	     				List<?> list = BaseService.getTblGeneralObservationService().
	     						findListByRecIdSessionIdStudyNo(recId,sessionId,studyNo);
	     				
	     				List<Map<String,String>> mapList =getMapListByList(list);
	     				int size =  mapList.size();
	     				int count = size%62;
	     				int max = 0;//增加空行数量
	     				if(count !=0){
	     					max = 62 - count;
	     				}
	     				for(int i=0;i<max;i++){
	     					mapList.add(new HashMap<String,String>());
	     				}
	     				try {
	     					jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(mapList));
	     				} catch (JRException e) {
	     					e.printStackTrace();
	     					Alert2.create("报表加载失败");
	     					return ;
	     				}
	     				Main.getInstance().openJFrame(jp, "动物一般观察记录表");
	     			}
	     		}
	     			
	     		
	        }
		}else{
			Alert2.create("未复核，复核后才可以打印");
		}
		
	}
	
	/**根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String,String>> getMapList(List<TblGeneralObservation> tblGeneralObservationList){
		if(null != tblGeneralObservationList && tblGeneralObservationList.size()>0){
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
			Map<String,String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();
			for(TblGeneralObservation obj: tblGeneralObservationList){
				String animalId = obj.getAnimalId();
				String observation = obj.getObservation();
				if(!animalIdList.contains(animalId)){
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				}else{
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2+"、"+observation);
				}
			}
			Map<String,String> map =null;
			for(String animalId:animalIdList){
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		} 
		return null;
	}
	/**根据一般观察列表，把同一个animalId 对应的observation 合并在一起
	 * @param tblGeneralObservationList
	 * @return
	 */
	List<Map<String,String>> getMapListByList(List<?> list){
		if(null != list && list.size()>0){
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
			Map<String,String> animalIdObservationMap = new HashMap<String, String>();
			List<String> animalIdList = new ArrayList<String>();
			
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				String animalId = (String) obj[0];
				String observation = (String) obj[1];
				if(!animalIdList.contains(animalId)){
					animalIdList.add(animalId);
					animalIdObservationMap.put(animalId, observation);
				}else{
					String observation2 = animalIdObservationMap.get(animalId);
					animalIdObservationMap.put(animalId, observation2+"、"+observation);
				}
			}
			Map<String,String> map =null;
			for(String animalId:animalIdList){
				map = new HashMap<String, String>();
				map.put("animalId", animalId);
				map.put("observation", animalIdObservationMap.get(animalId));
				mapList.add(map);
			}
			return mapList;
		} 
		return null;
	}
	
	/**
	 * 关闭
	 */
	@FXML
	private void onExitButton(ActionEvent event){
		if((null == sessionId || "".equals(sessionId)) && 
				!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
			
		}else{
			//2.判断是否需要更新DayToDayPane上tblSession表数据
			if(updateFlag){
				DayToDayController.getInstance().loadData();
			}
			//3.关闭窗口
			
			Button button = (Button) event.getSource();
			button.getScene().getWindow().hide();
		}
	}
	/**
	 * 加载数据，控件，表格
	 * @param sessionId
	 */
	public void loadData(String sessionId){
		GeneralObservationFrame.sessionId =sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		GeneralObservationFrame.tblSessionAnimalsList=null;
		if(null!=tblSession){
			
			//1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(
					tblSession.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if(null != studyNo && !"".equals(studyNo)){
				studyNoLabel.setText("课题编号："+studyNo);
			}else{
				studyNoLabel.setText("接收批号："+tblSession.getRecId());
			}
			//2.复核后 禁用  addButton,delButton ,othersButton
			signId =tblSession.getSignId();
			if(null != signId && !"".equals(signId)){
				//签字后禁用   othersButton
				othersButton.setDisable(true);
			}
			checkId =tblSession.getCheckId();
			if(null != checkId && !"".equals(checkId)){
				addButton.setDisable(true);
				delButton.setDisable(true);
				othersButton.setDisable(true);
			}
			beginDate =tblSession.getBeginTime();
			//3.加载animalTable数据
			loadData_animalTable();
			
			//4.加载修改痕迹表格数据 
			updateTblTraceTable();
			//5.更新tblGeneralObservationTable 数据
			updateTblGeneralObservationTable();
		}
	}
	/**
	 * 加载数据，控件，表格（暂无sessionId时用）
	 * @param sessionId
	 */
	public void loadDataByTblSession(TblSession tblSession,List<TblSessionAnimals> tblSessionAnimalsList){
		GeneralObservationFrame.sessionId =tblSession.getSessionId();;
		GeneralObservationFrame.tblSession = tblSession;
		GeneralObservationFrame.tblSessionAnimalsList=tblSessionAnimalsList;
		if(null!=tblSession){
			
			//1.填充控件
			createrLabel.setText(tblSession.getCreater());
			createTimeLabel.setText(DateUtil.dateToString(
					tblSession.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			sessionTypeLabel.setText(tblSession.getSessionType());
			animalTypeLabel.setText(tblSession.getAnimalType());
			String studyNo = tblSession.getStudyNo();
			if(null != studyNo && !"".equals(studyNo)){
				studyNoLabel.setText("课题编号："+studyNo);
			}else{
				studyNoLabel.setText("接收批号："+tblSession.getRecId());
			}
			
			signId = null;
			checkId=null;
			beginDate =null;
			//3.加载animalTable数据
			loadData_animalTable(tblSessionAnimalsList);
			
			
			data_tblGeneralObservationTable.clear();
			data_tblTraceTable.clear();
			
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(this.getClass().getResource("GeneralObservation.fxml"));
		Scene scene = new Scene(root,650,640);
		stage.setScene(scene);
		stage.setTitle("一般观察");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if((null == sessionId || "".equals(sessionId)) && 
						!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
					event.consume();
				}else{
					//2.判断是否需要更新DayToDayPane上tblSession表数据
					if(updateFlag){
						DayToDayController.getInstance().loadData();
					}
				}
			}});
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
