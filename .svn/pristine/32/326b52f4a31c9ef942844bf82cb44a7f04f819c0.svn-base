package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**组织取材编号
 * @author Administrator
 *
 */
public class TissueSlicePage extends Application implements Initializable {
	
	@FXML
	private TableView<SliceIndex> sliceIndexTable ;
	private ObservableList<SliceIndex> data_sliceIndexTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<SliceIndex,String> sliceCodeTypeCol;
	@FXML
	private TableColumn<SliceIndex,String> creatorCol;
	@FXML
	private TableColumn<SliceIndex,String> createDateCol;
	@FXML
	private TableColumn<SliceIndex,String> stateCol;
	
	
	@FXML
	private TableView<SliceCodeVisceraName> sliceCodeTable;
	private ObservableList<SliceCodeVisceraName> data_sliceCodeTable = FXCollections.observableArrayList();
	private ObservableList<SliceCodeVisceraName> data_sliceCodeTable2 = FXCollections.observableArrayList();
	@FXML
	private TableColumn<SliceCodeVisceraName,String> sliceCodeCol;
	@FXML
	private TableColumn<SliceCodeVisceraName,String> visceraNameCol;
	
	/**
	 * 专题编号
	 */
	private String studyNo = "";
	
	@FXML
	private Button button0;//常规取材编号
	@FXML
	private Button button1;//非常规组织取材编号
	@FXML
	private Button button2;//追加取材编号
	@FXML
	private Button printBtn;//打印
	
	private static TissueSlicePage instance;
	public static TissueSlicePage getInstance(){
		if(null == instance){
			instance = new TissueSlicePage();
		}
		return instance;
	}

	public TissueSlicePage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化组织取材编号索引表
		initSliceIndexTable();
		//初始化编号表
		initSliceCodeTable();
	}
	
	/**
	 * 初始化编号表
	 */
	private void initSliceCodeTable() {
		sliceCodeTable.setItems(data_sliceCodeTable);
		sliceCodeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sliceCodeCol.setCellValueFactory(new PropertyValueFactory<SliceCodeVisceraName,String>("sliceCode"));
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<SliceCodeVisceraName,String>("visceraName"));
		
	}

	public void updateSliceCodeTable(){
		sliceCodeTable.getSelectionModel().clearSelection();
		
		data_sliceCodeTable2.clear();
		data_sliceCodeTable.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService().getSliceCodeByStudyNo(studyNo);
		if(null != mapList){
			String preIndexId = "";
			String preSliceCode="";
			String preVisceraOrTissueName = "";
			for(Map<String,Object> map:mapList){
				String indexId = (String) map.get("indexId");
				String sliceCode = (String) map.get("sliceCode");
				String visceraOrTissueName = (String) map.get("visceraOrTissueName");
				if("".equals(preSliceCode)){
					preIndexId = indexId;
					preSliceCode = sliceCode;
					preVisceraOrTissueName = visceraOrTissueName;
				}else if(preSliceCode.equals(sliceCode)){
					preVisceraOrTissueName += "、"+visceraOrTissueName;
				}else{
					data_sliceCodeTable.add(new SliceCodeVisceraName(preIndexId,preSliceCode,preVisceraOrTissueName));
					data_sliceCodeTable2.add(new SliceCodeVisceraName(preIndexId,preSliceCode,preVisceraOrTissueName));
					
					preIndexId = indexId;
					preSliceCode = sliceCode;
					preVisceraOrTissueName = visceraOrTissueName;
				}
				
			}
			if(!"".equals(preSliceCode)){
				data_sliceCodeTable.add(new SliceCodeVisceraName(preIndexId,preSliceCode,preVisceraOrTissueName));
				data_sliceCodeTable2.add(new SliceCodeVisceraName(preIndexId,preSliceCode,preVisceraOrTissueName));
			}
		}
		
	} 
	public void updateSliceCodeTable2(){
		data_sliceCodeTable.clear();
		List<String> indexIdList = new ArrayList<String>();
		
		ObservableList<SliceIndex> selectedItems = sliceIndexTable.getSelectionModel().getSelectedItems();
		if(null != selectedItems && selectedItems.size() > 0){
			for(SliceIndex obj:selectedItems){
				indexIdList.add(obj.getId());
			}
		}
		
		for(SliceCodeVisceraName obj:data_sliceCodeTable2){
			if(indexIdList.size() == 0 || indexIdList.contains("") || indexIdList.contains(obj.getIndexId())){
				data_sliceCodeTable.add(obj);
			}
		}
	}
	
	/**
	 * 初始化组织取材编号索引表
	 */
	private void initSliceIndexTable() {
		
		sliceCodeTypeCol.setCellValueFactory(new PropertyValueFactory<SliceIndex,String>("sliceCodeType"));
		creatorCol.setCellValueFactory(new PropertyValueFactory<SliceIndex,String>("creator"));
		createDateCol.setCellValueFactory(new PropertyValueFactory<SliceIndex,String>("createDate"));
		stateCol.setCellValueFactory(new PropertyValueFactory<SliceIndex,String>("state"));
		
		sliceIndexTable.setItems(data_sliceIndexTable);
		sliceIndexTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		sliceIndexTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				updateSliceCodeTable2();
			}});
		
	}

	/**常规取材编号
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
//		TblAnatomyTask tblAnatomyTask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
//		String anatomyCheckFinishSign = tblAnatomyTask.getAnatomyCheckFinishSign();
//		if(null == anatomyCheckFinishSign || "".equals(anatomyCheckFinishSign)){
//			showErrorMessage("解剖任务未签字！");
//			return;
//		}
		
		Stage stage = Main.stageMap.get("TissueSliceAdd");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceAdd.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceAdd",stage);
		}else{
			stage.show();
		}
		TissueSliceAdd.getInstance().loadData(studyNo,0);  
	}
	/**非常规组织取材编号
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		TblTissueSliceIndex taskIdSliceCodeType = BaseService.getInstance().getTblTissueSliceIndexService().getByStudyNo(studyNo);
		if(null == taskIdSliceCodeType || null == taskIdSliceCodeType.getOperatorSign() 
				|| "".equals(taskIdSliceCodeType.getOperatorSign())){
			showErrorMessage("常规组织取材编号未签字确认！");
			return;
		}
		
		Stage stage = Main.stageMap.get("TissueSliceFeichanggui");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceFeichanggui.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceFeichanggui",stage);
		}else{
			stage.show();
		}
		TissueSliceFeichanggui.getInstance().loadData(studyNo); 
	}
	/**追加取材编号
	 * @param event
	 */
	@FXML
	private void onBtn2Action(ActionEvent event){
		TblTissueSliceIndex taskIdSliceCodeType = BaseService.getInstance().getTblTissueSliceIndexService().getByStudyNo(studyNo);
		if(null == taskIdSliceCodeType || null == taskIdSliceCodeType.getOperatorSign() 
				|| "".equals(taskIdSliceCodeType.getOperatorSign())){
			showErrorMessage("请先创建常规组织取材编号！");
			return;
		}
		Stage stage = Main.stageMap.get("TissueSliceAdd");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceAdd.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceAdd",stage);
		}else{
			stage.show();
		}
		TissueSliceAdd.getInstance().loadData(studyNo,2);
	}
	
	/**组织取材（批次）
	 * @param event
	 */
	@FXML
	private void onTissueSliceBatchBtnAction(ActionEvent event){
		Stage stage = Main.stageMap.get("TissueSliceBatch");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceBatch.getInstance().start(stage);
				stage.setTitle("组织取材（"+studyNo+"）");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceBatch",stage);
		}else{
			stage.setTitle("组织取材（"+studyNo+"）");
			stage.show();
		}
		TissueSliceBatch.getInstance().loadData(studyNo);  
	}	
	/**打印
	 * @param event
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event){
		
		if(data_sliceCodeTable.size() < 1){
			showErrorMessage("切片编号列表为空！");
			return;
		}
		
		JasperReport jr = null;
        JasperPrint jp = null;                                             
        try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tissueSliceCode.jrxml"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
        Map<String,Object> map = new HashMap<String,Object>();
        URL url = this.getClass().getResource("/image/logo.jpg");
        String number = "";
        number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("组织取材编号表");
        map.put("number", number == null ? "":number);;//编号
        map.put("logoImage", url);
        String animalType  = "";
    	TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getById(studyNo);
    	if(null != studyPlan){
    		animalType = studyPlan.getAnimalType();
    		String animalStrain = studyPlan.getAnimalStrain();
    		if(null != animalStrain || !"".equals(animalStrain)){
    			if(!animalStrain.contains(animalType)){
    				animalType = animalType +" "+animalStrain;
    			}else{
    				animalType = animalStrain;
    			}
    		}
    	}
//        map.put("studyNo", studyNo);
    	map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
        map.put("animalType", animalType);
        
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> fieldMap = null;
        for(SliceCodeVisceraName obj:data_sliceCodeTable){
        	fieldMap = new HashMap<String,Object>();
        	fieldMap.put("sliceCode", obj.getSliceCode());
        	fieldMap.put("visceraOrTissueName", obj.getVisceraName());
        	mapList.add(fieldMap);
        }
  		try {
			jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(mapList));
		} catch (JRException e) {
			e.printStackTrace();
		}     
        Main.getInstance().openJFrame(jp, "组织取材编号表");
	}
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		
		//2.更新sliceIndexTable 表格数据
		updateSliceIndexTable();
		
		//3.
		updateSliceCodeTable();
	}

	
	/**
	 * 更新sliceIndexTable 表格数据
	 */
	public void updateSliceIndexTable() {
		data_sliceIndexTable.clear();
		//id,sliceCodeType,createTime,creator
		List<Map<String,Object>> maplist = BaseService.getInstance().getTblTissueSliceIndexService().getListByStudyNo(studyNo);
		data_sliceIndexTable.add(new SliceIndex("",-1,"","",""));
		if(null != maplist && maplist.size() > 0){
			for(Map<String,Object> map:maplist){
				String id = (String) map.get("id");
				Integer sliceCodeType = (Integer) map.get("sliceCodeType");
				String createTime = (String) map.get("createTime");
				String creator = (String) map.get("creator");
				String operatorSign = (String) map.get("operatorSign");
				
				String state = "";
				if(null != operatorSign && !"".equals(operatorSign)){
					state = "已确认";
				}
				data_sliceIndexTable.add(new SliceIndex(id,sliceCodeType,creator,createTime,state));
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSlice.fxml"));
		Scene scene = new Scene(root, 984, 638);
		stage.setScene(scene);
		stage.setTitle("组织取材编号");
//		stage.setResizable(false);
		stage.setMinWidth(984);
		stage.setMinHeight(638);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	
	/**切片编号，脏器名称
	 * @author Administrator
	 *
	 */
	public class SliceCodeVisceraName{
		
		private StringProperty indexId;
		private StringProperty sliceCode;
		private StringProperty visceraName;
		
		public SliceCodeVisceraName(){}
		
		public SliceCodeVisceraName(String indexId,String sliceCode,String visceraName){
			setSliceCode(sliceCode);
			setVisceraName(visceraName);
			setIndexId(indexId);
		}
		
		
		public String getIndexId() {
			return indexId.get();
		}

		public void setIndexId(String indexId) {
			this.indexId = new SimpleStringProperty(indexId);
		}

		public String getSliceCode() {
			return sliceCode.get();
		}
		public void setSliceCode(String sliceCode) {
			this.sliceCode = new SimpleStringProperty(sliceCode);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
	}
	
	
	/**切片索引
	 * @author Administrator
	 *
	 */
	public class  SliceIndex{
		private StringProperty id;
		private StringProperty sliceCodeType;
		private StringProperty creator;
		private StringProperty createDate;
		private StringProperty state;
		public SliceIndex(){
			super();
		}
		public SliceIndex(String id,int sliceCodeType,String creator,String createDate,String state){
			setId(id);
			setCreateDate(createDate);
			setCreator(creator);
			setState(state);
			switch (sliceCodeType) {
			case -1:
				setSliceCodeType("全部");
				break;
			case 0:
				setSliceCodeType("常规取材编号");
				break;
			case 1:
				setSliceCodeType("非常规取材编号");
				break;
			case 2:
				setSliceCodeType("追加取材编号");
				break;

			default:
				break;
			}
		}
		
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id =  new SimpleStringProperty(id);
		}
		public String getSliceCodeType() {
			return sliceCodeType.get();
		}
		public void setSliceCodeType(String sliceCodeType) {
			this.sliceCodeType =  new SimpleStringProperty(sliceCodeType);
		}
		public String getCreator() {
			return creator.get();
		}
		public void setCreator(String creator) {
			this.creator =  new SimpleStringProperty(creator);
		}
		public String getCreateDate() {
			return createDate.get();
		}
		public void setCreateDate(String createDate) {
			this.createDate =  new SimpleStringProperty(createDate);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}
	}
	
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

}
