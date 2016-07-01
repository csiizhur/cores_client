package com.lanen.view.quarantine;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.lanen.base.BaseService;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.studyplan.DictAnimalStrain;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.TblAnimalRecListForTableView;
import com.lanen.util.popup.Alert2;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PlanPaneController implements Initializable{
	
	@FXML
	private ComboBox<String> animalLevelComboBox; // 动物级别 AnimalLevel
	@FXML
	private ComboBox<String> animalTypeComboBox; // 动物种类 AnimalType
	private ObservableList<String> data_animalTypeComboBox = FXCollections.observableArrayList();
	private Map<String, String> animalTypeNameIdMap = new HashMap<String, String>();
	@FXML
	private ComboBox<String> animalStrainComboBox; // 动物品系 AnimalStrain
	private ObservableList<String> data_animalStrainComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> recIdComboBox;  //接收单号
	private ObservableList<String> data_recIdComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> studyNoComboBox;//课题编号
	private ObservableList<String> data_studyNoComboBox = FXCollections.observableArrayList();
	
	@FXML
	private TableView<TblAnimalRecListForTableView> noPlanAnimalTable;   //未分配动物表格
	private static ObservableList<TblAnimalRecListForTableView> data_noPlanAnimalTable = FXCollections.observableArrayList();
	private VirtualFlow flow;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> snCol_1;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalIdCol_1;		//	动物ID号	AnimalID	varchar(30)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> genderCol_1;				//	性别	Gender	integer			
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> roomCol_1;			//	申请单号	QRRID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> stateCol_1;           //状态   死亡  ，移交，备库
	
	@FXML
	private TableView<TblAnimalRecListForTableView> planAnimalTable;   //已分配动物表格
	private static ObservableList<TblAnimalRecListForTableView> data_planAnimalTable = FXCollections.observableArrayList();
	private VirtualFlow flow_2;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> snCol_2;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> recIdCol_2;			//	接收单号	RecID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalIdCol_2;		//	动物ID号	AnimalID	varchar(30)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> genderCol_2;				//	性别	Gender	integer			
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> planStudyNoCol_2;		//	分配课题编号	PlanStudyNo	varchar(20)
	@FXML
	private Label selectNumLabel_1;
	@FXML
	private Label totalNumLabel_1;
	@FXML
	private Label selectNumLabel_2;
	@FXML
	private Label totalNumLabel_2;
	
	private String animalType="";
	private String animalStrain="";
	private String animalLevel="";
	private String recId="";
	private String studyNo="";
	private  static PlanPaneController instance;
	public static PlanPaneController getInstance(){
		return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化动物种类与动物品系下拉框
		initAnimalTypeAndStrainComboBox();
		//初始化动物级别下拉框
		initAnimalLevelComboBox();
		//初始化接收单号下拉框
		initRecIdComboBox();
		//初始化课题编号下拉框
		initStudyNoComboBox();
		
		//初始化未分配动物表格
		initNoPlanAnimalTable();
		//初始化已分配动物表格
		initPlanAnimalTable();
	}
	/**
	 * 初始化动物级别下拉框
	 */
	private void initAnimalLevelComboBox() {
		animalLevelComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					animalLevel =newValue;
				}else{
					animalLevel = "";
				}
				//更新  recIdComboBox值
				updateRecIdComboBox();
			}

			
			
		});
		
	}
	/**
	 * 初始化接收单号下拉框
	 */
	private void initRecIdComboBox() {
		recIdComboBox.setItems(data_recIdComboBox);
		recIdComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					recId= newValue;
				}else{
					recId = "";
				}
				// 更新  studyNoComboBox值
				updateStudyNoComboBox();
				//更新  noPlanAnimalTable值
				updateNoPlanAnimalTable();
			}
		});
		
	}
	/**
	 * 初始化课题编号下拉框
	 */
	private void initStudyNoComboBox() {
		studyNoComboBox.setItems(data_studyNoComboBox);
		studyNoComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					studyNo=newValue;
				}else{
					studyNo ="";
				}
				//更新 planAnimalTable值
				updatePlanAnimalTable();
			}
		});
		
	}
	/**
	 * 初始化未分配动物表格
	 */
	@SuppressWarnings("rawtypes")
	private void initNoPlanAnimalTable() {
		snCol_1.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("sn")); 
		animalIdCol_1.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalId")); 
		genderCol_1.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("gender")); 
		roomCol_1.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("room")); 
		stateCol_1.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("state")); 
		noPlanAnimalTable.setItems(data_noPlanAnimalTable);
		noPlanAnimalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		noPlanAnimalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		noPlanAnimalTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				//更新noPlanAnimalTable 状态栏
				updateNoPlanState();
			}
			
		});
		noPlanAnimalTable.skinProperty().addListener(new ChangeListener<Skin>(){

			@Override
			public void changed(ObservableValue<? extends Skin> ov, Skin t, Skin t1) {
				 if (t1 == null) { return; }
	                TableViewSkin tvs = (TableViewSkin)t1;
	                ObservableList<Node> kids = tvs.getChildrenUnmodifiable();
	                if (kids == null || kids.isEmpty()) { return; }
	                flow = (VirtualFlow)kids.get(1);
			}});
		data_noPlanAnimalTable.addListener(new ListChangeListener<TblAnimalRecListForTableView>(){

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends TblAnimalRecListForTableView> change) {
				while (change.next())
                {
                    if (change.wasAdded())
                    {
                        if (flow == null) { return; }
                        if(null!=flow.getFirstVisibleCell()){
                        	int first = flow.getFirstVisibleCell().getIndex();
                        	int last = flow.getLastVisibleCell().getIndex();
                        	int selected = noPlanAnimalTable.getSelectionModel().getSelectedIndex();
                        	
                        	if (selected < first || selected > last){
                        		flow.show(selected);
                        	}
                        }
                    }
                }
				
			}
			
		});
	}
	/**
	 * 初始化已分配动物表格
	 */
	@SuppressWarnings("rawtypes")
	private void initPlanAnimalTable() {
		snCol_2.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("sn")); 
		recIdCol_2.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("recId")); 
		animalIdCol_2.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalId")); 
		genderCol_2.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("gender")); 
		planStudyNoCol_2.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("planStudyNo")); 
		planAnimalTable.setItems(data_planAnimalTable);
		
		planAnimalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		planAnimalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		planAnimalTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				//更新planAnimalTable 状态栏
				updatePlanState();
			}
			
		});
		planAnimalTable.skinProperty().addListener(new ChangeListener<Skin>(){

			@Override
			public void changed(ObservableValue<? extends Skin> ov, Skin t, Skin t1) {
				 if (t1 == null) { return; }
	                TableViewSkin tvs = (TableViewSkin)t1;
	                ObservableList<Node> kids = tvs.getChildrenUnmodifiable();
	                if (kids == null || kids.isEmpty()) { return; }
	                flow_2 = (VirtualFlow)kids.get(1);
			}});
		data_planAnimalTable.addListener(new ListChangeListener<TblAnimalRecListForTableView>(){

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends TblAnimalRecListForTableView> change) {
				while (change.next())
                {
                    if (change.wasAdded())
                    {
                        if (flow_2 == null) { return; }
                        if(null!=flow_2.getFirstVisibleCell()){
                        	int first = flow_2.getFirstVisibleCell().getIndex();
                        	int last = flow_2.getLastVisibleCell().getIndex();
                        	int selected = planAnimalTable.getSelectionModel().getSelectedIndex();
                        	
                        	if (selected < first || selected > last){
                        		flow_2.show(selected);
                        	}
                        }
                    }
                }
				
			}
			
		});
		
	}
	/**初始化动物种类与动物品系下拉框以及填充动物种类comboBox 数据*/
	private void initAnimalTypeAndStrainComboBox(){
		//动物种类下拉框初始化
		animalTypeComboBox.setItems(data_animalTypeComboBox);
		//动物品系下拉框初始化
		animalStrainComboBox.setItems(data_animalStrainComboBox);
		//动物种类选中项监听事件
		animalTypeComboBox.getSelectionModel().selectedItemProperty()//
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				animalStrainComboBox.getSelectionModel().clearSelection();
				data_animalStrainComboBox.clear();
				if(null!=newValue && !"".equals(newValue)){
					String id= animalTypeNameIdMap.get(newValue);
					if(null!=id && !"".equals(id)){
						animalType=newValue;
						List<DictAnimalStrain> dictAnimalStrainList = 
								BaseService.getDictAnimalStrainService().findByTypeId(id);
						if(null != dictAnimalStrainList && dictAnimalStrainList.size()>0){
							for(DictAnimalStrain obj:dictAnimalStrainList){
								data_animalStrainComboBox.add(obj.getStrainName());
							}
						}
					}
				}else{
					animalType="";
				}
				
			}
			
		});
		animalStrainComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					animalStrain=newValue;
				}else{
					animalStrain="";
				}
				//更新  recIdComboBox值
				updateRecIdComboBox();
			}
			
		});
		List<DictAnimalType> dictAnimalTypeList = 
				BaseService.getDictAnimalTypeService().findAllOrderByOrderNo();
		if(null != dictAnimalTypeList && dictAnimalTypeList.size()>0){
			for(DictAnimalType obj:dictAnimalTypeList){
				data_animalTypeComboBox.add(obj.getTypeName());
				animalTypeNameIdMap.put(obj.getTypeName(), obj.getId());
			}
		}
		
	}
	/**
	 * 更新 recIdComboBox值
	 */
	private void updateRecIdComboBox() {
		data_recIdComboBox.clear();
		if(!"".equals(animalType) && !"".equals(animalStrain) && !"".equals(animalLevel)){
			List<String> recIdList = BaseService.getTblAnimalRecIndexService()
					.getRecIdListByAnimal(animalType,animalStrain,animalLevel);
			if(null!=recIdList && recIdList.size()>0){
				for(String recId:recIdList){
					data_recIdComboBox.add(recId);
				}
			}
		}
		
	}
	/**
	 * 更新noPlanAnimalTable值
	 */
	private void updateNoPlanAnimalTable(){
		data_noPlanAnimalTable.clear();
		if(!recId.equals("")){
			List<TblAnimalRecList> tblAnimalRecLists = BaseService.getTblAnimalRecListService()
					.getNoStudyNoListByRecId(recId);
			if(null!=tblAnimalRecLists && tblAnimalRecLists.size()>0){
				String state="";
				for(TblAnimalRecList obj:tblAnimalRecLists){
					state="";
					if(obj.getTransFlag() == 1){
						state="移交";
					}
					if(obj.getReserveFlag() == 1){
						state="备库";
					}
					if(obj.getDeadFlag() == 1){
						state="死亡";
					}
					data_noPlanAnimalTable.add(new TblAnimalRecListForTableView(
							obj.getId(),obj.getSn(),obj.getRecId(),obj.getAnimalType(),obj.getAnimalStrain(),
							obj.getAnimalId(),obj.getGender(),obj.getRoom(),obj.getPlanStudyNo(),state,obj.getAnimalLevel()
							));
				}
			}
		}
		//更新noPlanAnimalTable的状态栏
		updateNoPlanState();
	}
	/**
	 * 更新studyNoComboBox值
	 */
	private void updateStudyNoComboBox(){
		data_studyNoComboBox.clear();
		if(!"".equals(animalType) && !"".equals(animalStrain) 
				&& !"".equals(animalLevel) && !"".equals(recId)){
			List<String> studyNoList = BaseService.getTblQRRequestService()
					.getStudyNoListByAnimalRecId(animalType,animalStrain,animalLevel,recId);
			if(null!=studyNoList && studyNoList.size()>0){
				for(String studyNo :studyNoList){
					data_studyNoComboBox.add(studyNo);
				}
			}
		}
	}
	/**
	 * 更新PlanAnimalTable值
	 */
	private void updatePlanAnimalTable(){
		data_planAnimalTable.clear();
		if(!"".equals(studyNo)){
			List<TblAnimalRecList> tblAnimalRecLists = BaseService.getTblAnimalRecListService()
					.getListByStudyNo(studyNo);
			if(null!=tblAnimalRecLists && tblAnimalRecLists.size()>0){
				String state="";
				for(TblAnimalRecList obj:tblAnimalRecLists){
					state="";
					if(obj.getTransFlag() == 1){
						state="移交";
					}
					if(obj.getReserveFlag() == 1){
						state="备库";
					}
					if(obj.getDeadFlag() == 1){
						state="死亡";
					}
					data_planAnimalTable.add(new TblAnimalRecListForTableView(
							obj.getId(),obj.getSn(),obj.getRecId(),obj.getAnimalType(),obj.getAnimalStrain(),
							obj.getAnimalId(),obj.getGender(),obj.getRoom(),obj.getPlanStudyNo(),state,obj.getAnimalLevel()
							));
				}
			}
		}
		//更新planAnimalTable状态栏
		updatePlanState();
	}
	/**
	 * 更新noPlanAnimalTable状态栏的值
	 */
	private void updateNoPlanState(){
		int total = data_noPlanAnimalTable.size();
		totalNumLabel_1.setText(total+"");
		int selectNum =0;
		if(total>0){
			selectNum = noPlanAnimalTable.getSelectionModel().getSelectedItems().size();
		}
		selectNumLabel_1.setText(selectNum+"");
	}
	/**
	 * 更新planAnimalTable状态栏的值
	 */
	private void updatePlanState(){
		int total = data_planAnimalTable.size();
		totalNumLabel_2.setText(total+"");
		int selectNum =0;
		if(total>0){
			selectNum = planAnimalTable.getSelectionModel().getSelectedItems().size();
		}
		selectNumLabel_2.setText(selectNum+"");
	}
	/**
	 * 向右
	 * @param event
	 */
	@FXML
	private void onRightButton(ActionEvent event){
		 int total = data_noPlanAnimalTable.size();
		 if(total>0){
			 int selectNum = noPlanAnimalTable.getSelectionModel().getSelectedItems().size();
			 if(selectNum>0){
				 if(!"".equals(studyNo)){
					 ObservableList<TblAnimalRecListForTableView> selectList = 
							 FXCollections.observableArrayList(noPlanAnimalTable.getSelectionModel().getSelectedItems());
					 for(TblAnimalRecListForTableView obj:selectList){
						 if(null!=obj.getState() && !"".equals(obj.getState())){
							 Alert2.create("序号:"+obj.getSn() +"动物已"+obj.getState());
							 return ;
						 }
					 }
					 //左边  删除选中项
					 data_noPlanAnimalTable.removeAll(selectList);
					 //左边  清空选中项
					 noPlanAnimalTable.getSelectionModel().clearSelection();
					 
					 
					 int oldTotal =data_planAnimalTable.size();
					 //右边 添加选中项
					 for(TblAnimalRecListForTableView obj:selectList){
						 obj.setPlanStudyNo(studyNo);
						 data_planAnimalTable.add(obj);
					 }
					 //右边 清楚原选中项
					 planAnimalTable.getSelectionModel().clearSelection();
					 //右边 排序
					 Collections.sort(data_planAnimalTable,new Comparator<TblAnimalRecListForTableView>(){
						@Override
						public int compare(TblAnimalRecListForTableView o1,
								TblAnimalRecListForTableView o2) {
							return Integer.valueOf(o1.getSn())-Integer.valueOf(o2.getSn());
						}
					 });
					 //右边  选择选中项
					 for(TblAnimalRecListForTableView obj:selectList){
						 planAnimalTable.getSelectionModel().select(obj);
					 }
					int index = planAnimalTable.getSelectionModel().getSelectedIndices().get(0);
					planAnimalTable.scrollTo(index);
					
					
					//更新后台数据
					 List<String> idList = new ArrayList<String>();
					 for(TblAnimalRecListForTableView obj:selectList){
						 idList.add(obj.getId());
					 }
					 BaseService.getTblAnimalRecListService().updateList(idList,studyNo);
					//判断是否要更新  TblQRRequest中字段planState  
					if(oldTotal<1){
						BaseService.getTblQRRequestService().updatePlanStateToOne(recId,studyNo);
					}
					
					 //更新noPlanAnimalTable的状态栏
					 updateNoPlanState();
					//更新planAnimalTable状态栏
					 updatePlanState();
					 
				 }else{
					 Alert2.create("请先选择课题编号");
					 return ; 
				 }
			 }else{
				 Alert2.create("请先选择数据");
				 return ;
			 }
		 }
	}
	/**
	 * 向左
	 * @param event
	 */
	@FXML
	private void onLeftButton(ActionEvent event){
		 int total = data_planAnimalTable.size();
		 if(total>0){
			 int selectNum = planAnimalTable.getSelectionModel().getSelectedItems().size();
			 if(selectNum>0){
				 if(!"".equals(recId)){
					 ObservableList<TblAnimalRecListForTableView> selectList = 
							 FXCollections.observableArrayList(planAnimalTable.getSelectionModel().getSelectedItems());
					 for(TblAnimalRecListForTableView obj:selectList){
						 if(!obj.getRecId().equals(recId)){
							Alert2.create("序号:"+obj.getSn() +"动物接收单号不符");
							return ; 
						 }
					 }
					 //右边  删除选中项
					 data_planAnimalTable.removeAll(selectList);
					 //右边  清空选中项
					 planAnimalTable.getSelectionModel().clearSelection();
					 
					 
					 //左边 添加选中项
					 for(TblAnimalRecListForTableView obj:selectList){
						 obj.setPlanStudyNo("");
						 data_noPlanAnimalTable.add(obj);
					 }
					 //左边 清楚原选中项
					 noPlanAnimalTable.getSelectionModel().clearSelection();
					 //左边 排序
					 Collections.sort(data_noPlanAnimalTable,new Comparator<TblAnimalRecListForTableView>(){
						@Override
						public int compare(TblAnimalRecListForTableView o1,
								TblAnimalRecListForTableView o2) {
							return Integer.valueOf(o1.getSn())-Integer.valueOf(o2.getSn());
						}
					 });
					 //左边  选择选中项
					 for(TblAnimalRecListForTableView obj:selectList){
						 noPlanAnimalTable.getSelectionModel().select(obj);
					 }
					int index = noPlanAnimalTable.getSelectionModel().getSelectedIndices().get(0);
					noPlanAnimalTable.scrollTo(index);
					
					//更新后台数据
					 List<String> idList = new ArrayList<String>();
					 for(TblAnimalRecListForTableView obj:selectList){
						 idList.add(obj.getId());
					 }
					 BaseService.getTblAnimalRecListService().updateList(idList,"");
					//判断是否要更新  TblQRRequest中字段planState  
					if(total==selectNum){
						BaseService.getTblQRRequestService().updatePlanStateToOneOrZero(studyNo);
					}
					
					 //更新noPlanAnimalTable的状态栏
					 updateNoPlanState();
					//更新planAnimalTable状态栏
					 updatePlanState();
				 }else{
					 Alert2.create("请先选择接收单号");
					 return ; 
				 }
			 }else{
				 Alert2.create("请先选择数据");
				 return ;
			 }
		 }
		
	}
	/**
	 * 全选
	 * @param event
	 */
	@FXML
	private void onSelectAllButton(ActionEvent event){
		if(data_noPlanAnimalTable.size()>0){
			noPlanAnimalTable.getSelectionModel().selectAll();
		}
		updateNoPlanState();
	}
	/**
	 * 全不选
	 * @param event
	 */
	@FXML
	private void onSelectNoneButton(ActionEvent event){
		if(data_noPlanAnimalTable.size()>0){
			noPlanAnimalTable.getSelectionModel().clearSelection();
		}
		updateNoPlanState();
	}
	/**
	 * 反选
	 * @param event
	 */
	@FXML
	private void onSelectInvertButton(ActionEvent event){
		if(data_noPlanAnimalTable.size()>0){
			for(int i=0;i<data_noPlanAnimalTable.size();i++){
				if(noPlanAnimalTable.getSelectionModel().isSelected(i)){
					noPlanAnimalTable.getSelectionModel().clearSelection(i);
				}else{
					noPlanAnimalTable.getSelectionModel().select(i);
				}
			}
		}
		updateNoPlanState();
	}
	/**
	 * 全选——右
	 * @param event
	 */
	@FXML
	private void onSelectAllButton_2(ActionEvent event){
		if(data_planAnimalTable.size()>0){
			planAnimalTable.getSelectionModel().selectAll();
		}
		updatePlanState();
	}
	/**
	 * 全不选——右
	 * @param event
	 */
	@FXML
	private void onSelectNoneButton_2(ActionEvent event){
		if(data_planAnimalTable.size()>0){
			planAnimalTable.getSelectionModel().clearSelection();
		}
		updatePlanState();
	}
	/**
	 * 反选——右
	 * @param event
	 */
	@FXML
	private void onSelectInvertButton_2(ActionEvent event){
		if(data_planAnimalTable.size()>0){
			for(int i=0;i<data_planAnimalTable.size();i++){
				if(planAnimalTable.getSelectionModel().isSelected(i)){
					planAnimalTable.getSelectionModel().clearSelection(i);
				}else{
					planAnimalTable.getSelectionModel().select(i);
				}
			}
		}
		updatePlanState();
	}
	/**加载数据*/
	public void loadData(){
		//更新接收单号列表
		updateRecIdComboBox();
	}
}
