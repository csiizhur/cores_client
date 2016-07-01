package com.lanen.view.test;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblTissueSliceBatchIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**组织取材索引页
 * @author Administrator
 *
 */
public class TissueSliceBatch extends Application implements Initializable {
	
	
	/**
	 * 专题编号
	 */
	private String studyNo = "";
	
	/**
	 * 组织取材批次索引表
	 */
	@FXML
	private TableView<BatchIndex> batchIndexTable;
	ObservableList<BatchIndex> data_batchIndexTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<BatchIndex,String> batchSnCol;
	@FXML
	private TableColumn<BatchIndex,String> createTimeCol;
	@FXML
	private TableColumn<BatchIndex,String> operatorCol;
	@FXML
	private TableColumn<BatchIndex,String> sliceTypeCol;
	
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
	private TableColumn<Animal,String> anatomyDateCol;
	
	/**
	 * 取材批次脏器列表
	 */
	@FXML
	private TableView<BatchViscera> batchVisceraTable;
	ObservableList<BatchViscera> data_batchVisceraTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<BatchViscera,String> sliceCodeCol;
	@FXML
	private TableColumn<BatchViscera,String> visceraOrTissueNameCol;
	
	/**
	 * 排除表
	 */
	@FXML
	private TableView<BatchExcluded> batchExcludedTable;
	ObservableList<BatchExcluded> data_batchExcludedTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<BatchExcluded,String> animalCodeCol_excluded;
	@FXML
	private TableColumn<BatchExcluded,String> visceraNameCol;
	@FXML
	private TableColumn<BatchExcluded,String> reasonCol;
	
	private static TissueSliceBatch instance;
	public static TissueSliceBatch getInstance(){
		if(null == instance){
			instance = new TissueSliceBatch();
		}
		return instance;
	}

	public TissueSliceBatch() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//1.初始化取材批次索引表
		initBatchIndexTable();
		//2.初始化动物表
		initAnimalTable();
		//3.初始化取材批次脏器表
		initBatchVisceraTable();
		//4.初始化排除表
		initBatchExcludedTable();
	}
	/**
	 * 初始化排除表
	 */
	private void initBatchExcludedTable() {
		batchExcludedTable.setItems(data_batchExcludedTable);
		batchExcludedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol_excluded.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("animalCode"));
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("visceraName"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<BatchExcluded,String>("reason"));
	}
	/**
	 * 初始化取材批次脏器表
	 */
	private void initBatchVisceraTable() {
		batchVisceraTable.setItems(data_batchVisceraTable);
		batchVisceraTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		sliceCodeCol.setCellValueFactory(new PropertyValueFactory<BatchViscera,String>("sliceCode"));
		visceraOrTissueNameCol.
		setCellValueFactory(new PropertyValueFactory<BatchViscera,String>("visceraOrTissueName"));
	}
	/**
	 * 初始化动物表
	 */
	private void initAnimalTable() {
//		@FXML
//		private TableView<BatchIndex> batchIndexTable;
//		ObservableList<BatchIndex> data_batchIndexTable = FXCollections.observableArrayList();
//		@FXML
//		private TableColumn<BatchIndex,String> batchSnCol;
//		@FXML
//		private TableColumn<BatchIndex,String> createTimeCol;
//		@FXML
//		private TableColumn<BatchIndex,String> operatorCol;
//		@FXML
//		private TableColumn<BatchIndex,String> sliceTypeCol;
		batchIndexTable.setItems(data_batchIndexTable);
		batchIndexTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		batchSnCol.setCellValueFactory(new PropertyValueFactory<BatchIndex,String>("batchSn"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<BatchIndex,String>("createTime"));
		operatorCol.setCellValueFactory(new PropertyValueFactory<BatchIndex,String>("operator"));
		sliceTypeCol.setCellValueFactory(new PropertyValueFactory<BatchIndex,String>("sliceType"));
		//序号居中
		batchSnCol.setCellFactory(new Callback<TableColumn<BatchIndex,String>,TableCell<BatchIndex,String>>(){
			
			@Override
			public TableCell<BatchIndex, String> call(
					TableColumn<BatchIndex, String> param) {
				TableCell<BatchIndex, String> cell =
						new TableCell<BatchIndex, String>() {
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
		
		batchIndexTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BatchIndex>(){

			@Override
			public void changed(ObservableValue<? extends BatchIndex> arg0, BatchIndex arg1,
					BatchIndex newValue) {
				if(null != newValue){
					String batchId = newValue.getBatchId();
					//1.更新动物表
					updateAnimalTable(batchId );
					//2.更新脏器表
					updateBatchVisceraTable(batchId);
					//3.更新排除表
					updateBatchExcludedTable(batchId);
				}else{
					//1.更新动物表
					updateAnimalTable(null );
					//2.更新脏器表
					updateBatchVisceraTable(null);
					//3.更新排除表
					updateBatchExcludedTable(null);
				}
			}});
	}

	/**
	 * 初始化取材批次索引表
	 */
	private void initBatchIndexTable() {
		animalTable.setItems(data_animalTable);
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		dosageDescCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("dosageDesc"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		anatomyDateCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("anatomyDate"));
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

	/**新建
	 * @param event
	 */
	@FXML
	private void onBtn0Action(ActionEvent event){
//		打开    创建组织取材_选择切片类型   窗口
		Stage stage = Main.stageMap.get("TissueSliceBatch_select");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceBatch_select.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceBatch_select",stage);
		}
		TissueSliceBatch_select.getInstance().loadData();
		stage.showAndWait();
		
		if(TissueSliceBatch_select.getInstance().isConfirm()){
			//打开    创建组织取材窗口
			Stage stage2 = Main.stageMap.get("TissueSliceBatch_add");
			if(null == stage2){
				stage2 =new Stage();
				stage2.initOwner(Main.mainWidow);
				stage2.initModality(Modality.APPLICATION_MODAL);
				try {
					TissueSliceBatch_add.getInstance().start(stage2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("TissueSliceBatch_add",stage2);
			}else{
				stage2.show();
			}
			TissueSliceBatch_add.getInstance().loadData(studyNo,
					TissueSliceBatch_select.getInstance().getSelectType());
		}
	}
	/**编辑
	 * @param event
	 */
	@FXML
	private void onBtn1Action(ActionEvent event){
		//1.选择数据
		BatchIndex selectedItem = batchIndexTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem ){
			showErrorMessage("请选择待编辑数据！");
			return ;
		}
		//2.查询组织取材批次索引实体
		String batchId = selectedItem.getBatchId();
		TblTissueSliceBatchIndex tblTissueSliceBatchIndex = BaseService.getInstance()
				.getTblTissueSliceBatchService().getById(batchId);
		//3.打开编辑窗口
		//打开    创建组织取材窗口
		Stage stage2 = Main.stageMap.get("TissueSliceBatch_add");
		if(null == stage2){
			stage2 =new Stage();
			stage2.initOwner(Main.mainWidow);
			stage2.initModality(Modality.APPLICATION_MODAL);
			try {
				TissueSliceBatch_add.getInstance().start(stage2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TissueSliceBatch_add",stage2);
		}else{
			stage2.show();
		}
		TissueSliceBatch_add.getInstance().loadData(tblTissueSliceBatchIndex);
	}

	/**打印组织取材表
	 * @param event
	 */
	@FXML
	private void onPrint1BtnAction(ActionEvent event){
		
		//1.选择数据
		BatchIndex selectedItem = batchIndexTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem ){
			showErrorMessage("请选择待编辑数据！");
			return ;
		}
		//2.准备数据
		String batchId = selectedItem.getBatchId();
		JasperReport jr = null;
        JasperPrint jp = null;                                             
        try {
			jr = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/com/lanen/view/test/tissue_qucai.jrxml"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
        //参数
        Map<String,Object> map = new HashMap<String,Object>();
        URL url = this.getClass().getResource("/image/logo.jpg");
        String number = "";
        number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("组织取材表");
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
    	map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
        map.put("animalType", animalType);
//        map.put("remark","备注：");
        map.put("reportName","组织取材表");
        //备注：
        String remark = "备注：";
        List<String> remarkList = BaseService.getInstance().getTblTissueSliceBatchService()
        		.getPrintRemark(batchId,studyNo);
        if(null != remarkList && remarkList.size() > 0){
        	remark = remark+"排除脏器：";
        	int index = 0;
        	for(String str : remarkList){
        		if(index != 0){
        			remark = remark+"、"+str;
        		}else{
        			remark = remark+str;
        		}
        		index++;
        	}
        	remark = remark+"。";
        }else{
        	remark = remark+"NA";
        }
        map.put("remark",remark);
        
        
        List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService().getPrintMapList(batchId,studyNo);
        if(null != mapList && mapList.size() > 0){
        	int i = 0;	//列数
        	int j = 0;	//行数
        	Set<String> animalCodeSet = new HashSet<String>();
        	Set<String> sliceCodeSet = new HashSet<String>();
        	for(Map<String, Object> obj:mapList){
        		String animalCode = (String) obj.get("animalCode");
        		String sliceCode = (String) obj.get("sliceCode");
        		//1.常规
        		if(NumberValidationUtils.isPositiveInteger(sliceCode)){
        			sliceCode = 100+Integer.parseInt(sliceCode)+""+sliceCode;
        		}else if(sliceCode.contains("g")){
        			//2.非常规
        			sliceCode = "200"+sliceCode;
        		}else{
        			//3.追加
        			String sliceCode_ = sliceCode.substring(1);
        			sliceCode = 100+Integer.parseInt(sliceCode_)+""+sliceCode;
        		}
        		obj.put("sliceCode", sliceCode);
        		
        		animalCodeSet.add(animalCode);
        		sliceCodeSet.add(sliceCode);
        	}
        	i = animalCodeSet.size() %10;
        	j = sliceCodeSet.size();
        	if(i != 0 && j > 0){
        		Map<String,Object> fieldMap = null;
        		for(;i<10;i++){
        			Iterator<String> iterator = sliceCodeSet.iterator();
        			while(iterator.hasNext()){
        				fieldMap = new HashMap<String,Object>();
        				fieldMap.put("animalCode","1"+i+"-");
        	        	fieldMap.put("sliceCode",iterator.next());
        	        	fieldMap.put("confirmFlag", "-");
        	        	mapList.add(fieldMap);
        			}
        		}
        	}
        	
        }
  		try {
			jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(mapList));
		} catch (JRException e) {
			e.printStackTrace();
		}     
        Main.getInstance().openJFrame(jp, "组织取材编号表");
	}
	/**打印组织包、切片埋表
	 * @param event
	 */
	@FXML
	private void onPrint2BtnAction(ActionEvent event){
		String reportName = "";
		Button btn = (Button) event.getSource();
		if("打印组织包埋表".equals(btn.getText().trim())){
			reportName ="组织包埋表";
		}else{
			reportName ="组织切片表";
		}
		
		//1.选择数据
		BatchIndex selectedItem = batchIndexTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem ){
			showErrorMessage("请选择待编辑数据！");
			return ;
		}
		//2.准备数据
		String batchId = selectedItem.getBatchId();
		JasperReport jr = null;
        JasperPrint jp = null;                                             
        try {
			jr = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/com/lanen/view/test/tissue_slice.jrxml"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
        //参数
        Map<String,Object> map = new HashMap<String,Object>();
        URL url = this.getClass().getResource("/image/logo.jpg");
        String number = "";
        number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName(reportName);
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
    	map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
        map.put("animalType", animalType);
//		        map.put("remark","备注：");
        map.put("reportName",reportName);
        //备注：
        String remark = "备注：";
        List<String> remarkList = BaseService.getInstance().getTblTissueSliceBatchService()
        		.getPrintRemark(batchId,studyNo);
        if(null != remarkList && remarkList.size() > 0){
        	remark = remark+"排除脏器：";
        	int index = 0;
        	for(String str : remarkList){
        		if(index != 0){
        			remark = remark+"、"+str;
        		}else{
        			remark = remark+str;
        		}
        		index++;
        	}
        	remark = remark+"。";
        }else{
        	remark = remark+"NA";
        }
        map.put("remark",remark);
        
        
        List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService().getPrintMapList(batchId,studyNo);
        if(null != mapList && mapList.size() > 0){
        	int i = 0;	//列数
        	int j = 0;	//行数
        	Set<String> animalCodeSet = new HashSet<String>();
        	Set<String> sliceCodeSet = new HashSet<String>();
        	for(Map<String, Object> obj:mapList){
        		String animalCode = (String) obj.get("animalCode");
        		String sliceCode = (String) obj.get("sliceCode");
        		//1.常规
        		if(NumberValidationUtils.isPositiveInteger(sliceCode)){
        			sliceCode = 100+Integer.parseInt(sliceCode)+""+sliceCode;
        		}else if(sliceCode.contains("g")){
        			//2.非常规
        			sliceCode = "200"+sliceCode;
        		}else{
        			//3.追加
        			String sliceCode_ = sliceCode.substring(1);
        			sliceCode = 100+Integer.parseInt(sliceCode_)+""+sliceCode;
        		}
        		obj.put("sliceCode", sliceCode);
        		
        		animalCodeSet.add(animalCode);
        		sliceCodeSet.add(sliceCode);
        	}
        	i = sliceCodeSet.size() %16;
        	j = animalCodeSet.size();
        	if(i != 0 && j > 0){
        		Map<String,Object> fieldMap = null;
        		for(;i<16;i++){
        			Iterator<String> iterator = animalCodeSet.iterator();
        			while(iterator.hasNext()){
        				fieldMap = new HashMap<String,Object>();
        				fieldMap.put("sliceCode",300+i+"-");
        	        	fieldMap.put("animalCode",iterator.next());
        	        	fieldMap.put("confirmFlag", "-");
        	        	mapList.add(fieldMap);
        			}
        		}
        	}
        	
        }
  		try {
			jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(mapList));
		} catch (JRException e) {
			e.printStackTrace();
		}     
        Main.getInstance().openJFrame(jp, reportName);
	}
	/**打印组织切片表
	 * @param event
	 */
	@FXML
	private void onPrint3BtnAction(ActionEvent event){
		
	}
	/**
	 * 加载数据
	 * @param taskId 
	 */
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		//1.更新索引表
		updateBatchIndexTable(null);
	}
	
	/**
	 * 更新索引表
	 * @param selectBatchId 
	 */
	public void updateBatchIndexTable(String selectBatchId){
		data_batchIndexTable.clear();
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService().getBatchIndexMapListByStudyNo(studyNo);
		if(null != mapList){
			for(Map<String,Object> obj:mapList){
				String batchId = (String) obj.get("batchId");
				Integer batchSn = (Integer) obj.get("batchSn");
				String createTime = (String) obj.get("createTime");
				String operator = (String) obj.get("operator");
				Integer sliceType = (Integer) obj.get("sliceType");
				data_batchIndexTable.add(new BatchIndex(batchId,batchSn+"",createTime,operator,sliceType));
			}
		}
		if(null != selectBatchId){
			for(BatchIndex obj:data_batchIndexTable){
				if(selectBatchId.equals(obj.getBatchId())){
					batchIndexTable.getSelectionModel().select(obj);
					break;
				}
			}
		}
	}
	/**
	 * 更新动物表
	 */
	public void updateAnimalTable(String batchId){
		data_animalTable.clear();
		if(null != batchId){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceBatchService().getBatchAnimalMapListByBatchId(batchId);
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
	/**
	 * 更新脏器表
	 */
	public void updateBatchVisceraTable(String batchId){
		data_batchVisceraTable.clear();
		if(null != batchId){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblTissueSliceBatchService().getBatchVisceraMapListByBatchId(batchId);
			if(null != mapList && mapList.size() > 0){
				for(Map<String, Object> obj:mapList){
					String sliceCode = (String) obj.get("sliceCode");
					String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
					
					data_batchVisceraTable.add(new BatchViscera(sliceCode,visceraOrTissueName));
				}
			}
		}
	}
	/**
	 * 更新排除表
	 */
	public void updateBatchExcludedTable(String batchId){
		data_batchExcludedTable.clear();
		if(null != batchId){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblTissueSliceBatchService().getBatchExcludedMapListByBatchId(batchId);
			if(null != mapList && mapList.size() > 0){
				for(Map<String, Object> obj:mapList){
//					private StringProperty animalCode;
//					private StringProperty visceraName;
//					private StringProperty reason;
					String animalCode = (String) obj.get("animalCode");
//					String visceraName = (String) obj.get("visceraName");
					String reason = (String) obj.get("reason");
					
					
					String visceraName = (String) obj.get("visceraName");
					String subVisceraName = (String) obj.get("subVisceraName");
					if(null != subVisceraName && !"".equals(subVisceraName.trim())){
						visceraName =  subVisceraName;
					}
					
					data_batchExcludedTable.add(new BatchExcluded(animalCode,visceraName,reason));
				}
			}
		}
	}
	

	/**批次索引
	 * @author Administrator
	 *
	 */
	public class BatchIndex{
		private StringProperty batchId;
		private StringProperty batchSn;
		private StringProperty createTime;
		private StringProperty operator;
		private StringProperty sliceType;
		
		public BatchIndex(){}
		public BatchIndex(String batchId,String batchSn,String createTime,String operator,int sliceType){
			setBatchId(batchId);
			setBatchSn(batchSn);
			setCreateTime(createTime);
			setOperator(operator);
			if(sliceType == 1){
				setSliceType("常规组织取材、非常规组织取材");
			}else{
				setSliceType("追加组织取材");
			}
		}
		
		public String getBatchId() {
			return batchId.get();
		}
		public void setBatchId(String batchId) {
			this.batchId = new SimpleStringProperty(batchId);
		}
		public String getBatchSn() {
			return batchSn.get();
		}
		public void setBatchSn(String batchSn) {
			this.batchSn = new SimpleStringProperty(batchSn);
		}
		public String getCreateTime() {
			return createTime.get();
		}
		public void setCreateTime(String createTime) {
			this.createTime = new SimpleStringProperty(createTime);
		}
		public String getOperator() {
			return operator.get();
		}
		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}
		public String getSliceType() {
			return sliceType.get();
		}
		public void setSliceType(String sliceType) {
			this.sliceType = new SimpleStringProperty(sliceType);
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
	
	/**取材批次排除表
	 * @author Administrator
	 *
	 */
	public class BatchExcluded{
		private StringProperty animalCode;
		private StringProperty visceraName;
		private StringProperty reason;
		
		private BatchExcluded(){}
		private BatchExcluded(String animalCode,String visceraName,
				String reason){
			setAnimalCode(animalCode);
			setVisceraName(visceraName);
			setReason(reason);
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
		public String getReason() {
			return reason.get();
		}
		public void setReason(String reason) {
			this.reason = new SimpleStringProperty(reason);
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceBatch.fxml"));
		Scene scene = new Scene(root, 1024, 660);
		stage.setScene(scene);
		stage.setTitle("组织取材");
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
