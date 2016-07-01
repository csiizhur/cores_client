package com.lanen.view.clinicaltest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.contract.TblNotification;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.Sign;
import com.lanen.zero.main.Main;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class DataAcceptESFrame extends Application implements Initializable {

	    public String date="1970-01-01";//日期
	    public int testItem=0;//检测项目
	    public String studyNo;//课题编号
	    public int reqNo;//申请 编号
//	    date, testItem, studyNo, reqNo, beginCodeStr, endCodeStr);
	    private String copyDate ="1970-01-01";//更新table时备份
	    private int copyTestItem = 0;
	    private String copyStudyNo ="";
	    private int copyReqNo =0;
	    
	    public String beginCodeStr;//检验编号
	    public String endCodeStr;//检验编号
	    public static String type="Cancal";   //"OK", 表示有数据变动，将根据需要更新主界面和接收界面数据
	    
	    @FXML
	    private VBox dateVBox;    //日期
	    private DatePicker datePane=null;
	    @FXML
	    private ComboBox<String> testItemComboBox;//检测项目
	    @FXML
	    private ComboBox<String> studyNoComboBox;//课题编号
	    private ObservableList<String> data_studyNoComboBox =FXCollections.observableArrayList();
	    @FXML
	    private ComboBox<String> reqNoComboBox;//申请编号
	    private ObservableList<String> data_reqNoComboBox = FXCollections.observableArrayList();
	    //存放   课题编号  ：申请编号列表（1:2013-01-01）
	    private Map<String,List<String>> map_studyNoReqNoList =new HashMap<String,List<String>>();
	    @FXML
	    private TextField beginCode;//检验编号      1
	    @FXML
	    private TextField endCode;//检验编号   2
	    
	    
	    private List<String> testItemList = new ArrayList<String>();
	    
	//表格
	
		@FXML
		private TableView<ClinicalTestDataES> table;
		private static  ObservableList<ClinicalTestDataES> data_table=FXCollections.observableArrayList();
		// 看单个动物时用
		private static ObservableList<ClinicalTestDataES> dataOne_table = FXCollections.observableArrayList();
//		//表格数据副本(设置有效无效用)
//		private static ObservableList<ClinicalTestDataES> data_table2 = FXCollections.observableArrayList();
		
		@FXML
		private TableColumn<ClinicalTestDataES,Boolean> selectCol;
		@FXML
		private TableColumn<ClinicalTestDataES,String> dataIdCol;             //数据Id
		@FXML
		private TableColumn<ClinicalTestDataES,String> tblSpecimenIdCol;  //标本接收登记Id
		@FXML
		private TableColumn<ClinicalTestDataES,String> studyNoCol;            //课题编号
		@FXML
		private TableColumn<ClinicalTestDataES,String> reqNoCol;              //申请编号
		@FXML
		private TableColumn<ClinicalTestDataES,String> animalIdCol;           //动物Id
		@FXML
		private TableColumn<ClinicalTestDataES,String> animalCodeCol;         //动物编号
		@FXML
	    private TableColumn<ClinicalTestDataES,String> specimenCodeCol;       //检验编号号
		@FXML
	    private TableColumn<ClinicalTestDataES,String> testItemCol;              //检验项目
		@FXML
	    private TableColumn<ClinicalTestDataES,String> testIndexCol;          //检验指标
		@FXML
	    private TableColumn<ClinicalTestDataES,String> testIndexAbbrCol;      //检验指标缩写
		@FXML
	    private TableColumn<ClinicalTestDataES,String> testDataCol;           //数据
		@FXML
	    private TableColumn<ClinicalTestDataES,String> testIndexUnitCol;//检验指标单位
		@FXML
	    private TableColumn<ClinicalTestDataES,String> collectionModeCol;        //数据采集方式
	    @FXML
	    private TableColumn<ClinicalTestDataES,String> collectionTimeCol;       //数据采集时间(检验时间)
	    @FXML
	    private TableColumn<ClinicalTestDataES,String> acceptTimeCol;        //接收时间
//	    @FXML
//	    private TableColumn<ClinicalTestDataES,String> esCol;               //签字    0：未签字   1：已签字
	    @FXML
	    private TableColumn<ClinicalTestDataES,String> confirmFlagCol;      //1.第一次接收   2，第二次接收
	    @FXML
		private TableColumn<ClinicalTestDataES,Boolean> validFlagCol;    //是否有效
	    @FXML
	    private TableColumn<ClinicalTestDataES,String> remarkCol;
	    @FXML
	    private static CheckBox checkAllBox;
	    
	    @FXML
	    private Button confirmButton;//确定按钮
	    @FXML
	    private Button delButton;//确定按钮
	    
	    @FXML
	    private TableView<SpecimenAbout> aboutTable;
	    private ObservableList<SpecimenAbout> data_aboutTable =  FXCollections.observableArrayList();
	    @FXML
	    private TableColumn<SpecimenAbout,String> specimenCodeCol_about;
	    @FXML
	    private TableColumn<SpecimenAbout,String> animalCodeCol_about;
	    @FXML
	    private TableColumn<SpecimenAbout,String> animalIdCol_about;
	    @FXML
	    private TableColumn<SpecimenAbout,String> testItemCol_about; //检验项目
	    @FXML
	    private TableColumn<SpecimenAbout,String> studyNoCol_about;
	    
//	public DataAcceptESFrame(){
//		type="Cancal";
//		studyNo="";
//		reqNo=0;
//		beginCodeStr ="";
//		endCodeStr="";
//		
//		testItemList.clear();
//		testItemList.add("生化检验");
//		testItemList.add("血液检验");
//		testItemList.add("血凝检验");
//		testItemList.add("尿液检验");
//	}
	    
    private static DataAcceptESFrame instance;
	public static DataAcceptESFrame getInstance(){
		if(null == instance){
			instance = new DataAcceptESFrame();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		testItemList.clear();
		testItemList.add("生化检验");
		testItemList.add("血液检验");
		testItemList.add("血凝检验");
		testItemList.add("尿液检验");
		
		//初始化检测项目下拉框
		initTestItemComboBox();
		
		//初始化   课题编号   下拉框
		initStudyNoComboBox();
		
		//初始化   申请编号 下拉框
		initReqNoComboBox();
		
		//初始化日期选择器
		initDate();
		
		//初始化  表格
		initTableView();
		//初始化boutTable
		initAboutTable();
		
		
	}
	
	/**加载数据
	 * @param dateStr
	 */
	public void loadData(String dateStr){
		
		setDate(dateStr);
		type="Cancal";
		studyNo="";
		reqNo=0;
		beginCodeStr ="";
		endCodeStr="";
		testItem = 0;
		
		datePane.getTextField().clear();
		beginCode.clear();
		endCode.clear();
		
		datePane.getTextField().setText(date);
		testItemComboBox.getSelectionModel().clearAndSelect(testItem);
		//更新表格数据
		updateTableData(date,testItem,studyNo,reqNo,beginCodeStr,endCodeStr);
		//更新 aboutTable 表格 数据 （查询数据库）
		updateAboutTableData(date,testItem,studyNo,reqNo,beginCodeStr,endCodeStr);
	}
	
	/**
	 * 初始化boutTable
	 */
	private void initAboutTable(){
		specimenCodeCol_about.setCellValueFactory(new PropertyValueFactory<SpecimenAbout,String>("specimenCode"));
		animalCodeCol_about.setCellValueFactory(new PropertyValueFactory<SpecimenAbout,String>("animalCode"));
		animalIdCol_about.setCellValueFactory(new PropertyValueFactory<SpecimenAbout,String>("animalId"));
		testItemCol_about.setCellValueFactory(new PropertyValueFactory<SpecimenAbout,String>("testItem"));
		studyNoCol_about.setCellValueFactory(new PropertyValueFactory<SpecimenAbout,String>("studyNo"));
		
		aboutTable.setItems(data_aboutTable);
		
		aboutTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SpecimenAbout>(){

			@Override
			public void changed(ObservableValue<? extends SpecimenAbout> arg0, SpecimenAbout arg1,
					SpecimenAbout newValue) {
				if(null != newValue){
					
					//更新 表格 table 表格，及 data_table 中的   select 值
					updateTable(newValue.getSpecimenCode(),newValue.getAnimalId(),newValue.getTestItem());
				}
				
			}});
	}
	
	/**
	 * 更新 表格 table 表格，及 data_table 中的   select 值      以及是否全选
	 */
	private void updateTable(String specimenCode,String animalId,String testItemStr){
		specimenCode = specimenCode.trim();
		
		for( ClinicalTestDataES obj:dataOne_table)
			data_table.set(obj.getIndex(), new ClinicalTestDataES(obj
					.getSelect(), obj.getDataId(), obj.getSpecimenCode(), obj
					.getStudyNo(), obj.getReqNo(), obj.getAnimalId(), obj
					.getAnimalCode(), obj.getSpecimenCode(), obj.getTestItem(), obj
					.getTestIndex(), obj.getTestIndexAbbr(), obj.getTestData(), obj
					.getTestIndexUnit(), obj.getCollectionMode(), obj
					.getCollectionTime(), obj.getAcceptTime(), obj.getEs(), obj
					.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
					,obj.getIndex()));
		
		dataOne_table.clear();
		if(specimenCode.equals("全部")){
			table.setItems(data_table);
		}else{
			table.setItems(dataOne_table);
			for(ClinicalTestDataES obj :data_table){
				if(specimenCode.equals(obj.getSpecimenCode()) && animalId.equals(obj.getAnimalId())
						 && testItemStr.equals(obj.getTestItem())){
					dataOne_table.add( new ClinicalTestDataES(obj
							.getSelect(), obj.getDataId(), obj.getSpecimenCode(), obj
							.getStudyNo(), obj.getReqNo(), obj.getAnimalId(), obj
							.getAnimalCode(), obj.getSpecimenCode(), obj.getTestItem(), obj
							.getTestIndex(), obj.getTestIndexAbbr(), obj.getTestData(), obj
							.getTestIndexUnit(), obj.getCollectionMode(), obj
							.getCollectionTime(), obj.getAcceptTime(), obj.getEs(), obj
							.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
							,obj.getIndex()));
				}
			}
		}
		
			//设置    选中与否
			checkAllBox.setSelected(tableAllSelected());
	}
	
	/**
	 * 数据修改后，更新数据
	 */
	public void updateTable(List<TblTrace> tblTraceList){
		for(ClinicalTestDataES obj:data_table){
			if(obj.getSelect() && obj.getTestIndexAbbr().equals("RET#")){
				for(TblTrace trace:tblTraceList){
					if(obj.getDataId().equals(trace.getDataId())){
						data_table.set(obj.getIndex(), new ClinicalTestDataES(obj
								.getSelect(), obj.getDataId(), obj.getSpecimenCode(), obj
								.getStudyNo(), obj.getReqNo(), obj.getAnimalId(), obj
								.getAnimalCode(), obj.getSpecimenCode(), obj.getTestItem(), obj
								.getTestIndex(), obj.getTestIndexAbbr(), trace.getNewValue(), obj
								.getTestIndexUnit(), obj.getCollectionMode(), obj
								.getCollectionTime(), obj.getAcceptTime(), obj.getEs(), obj
								.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
								,obj.getIndex()));
					}
				}
			}
		}
	}
	
	/**
	 * 初始化  表格
	 */
	private void initTableView() {
		selectCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,Boolean>("select"));
		selectCol.setCellFactory(new Callback<TableColumn<ClinicalTestDataES, Boolean>, TableCell<ClinicalTestDataES, Boolean>>() {

            public TableCell<ClinicalTestDataES, Boolean> call(TableColumn<ClinicalTestDataES, Boolean> p) {

                return new CheckBoxTableCell<ClinicalTestDataES, Boolean>();

            }

        });
		 dataIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("dataId"));             //数据Id
		 tblSpecimenIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("tblSpecimenId"));  //标本接收登记Id
		 studyNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("studyNo"));            //课题编号
		 reqNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("reqNo"));              //申请编号
		 animalIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("animalId"));           //动物Id
		 animalCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("animalCode"));         //动物编号
	     specimenCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("specimenCode"));       //检验编号号
	     testItemCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testItem"));              //检验项目
	     testIndexCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndex"));          //检验指标
	     testIndexAbbrCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testData"));           //数据
	     testIndexUnitCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("acceptTime"));        //接收时间
//	     esCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("confirmFlag"));      //1.第一次接收   2，第二次接收
	     validFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,Boolean>("validFlag"));             //是否有效
	     remarkCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("remark"));             //是否有效
//	     validFlagCol.setCellFactory(new Callback<TableColumn<ClinicalTestDataES, Boolean>, TableCell<ClinicalTestDataES, Boolean>>() {
//
//	            public TableCell<ClinicalTestDataES, Boolean> call(TableColumn<ClinicalTestDataES, Boolean> p) {
//
//	                return new CheckBoxTableCell2<ClinicalTestDataES, Boolean>();
//
//	            }
//
//	        });
	     testDataCol.setCellFactory(new Callback<TableColumn<ClinicalTestDataES,String>,TableCell<ClinicalTestDataES,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestDataES, String> call(
	    			 TableColumn<ClinicalTestDataES, String> param) {
	    		 TableCell<ClinicalTestDataES, String> cell =
	    				 new TableCell<ClinicalTestDataES, String>() {
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
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
		table.setItems(data_table);
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<ClinicalTestDataES> table = (TableView<ClinicalTestDataES>) event.getSource();
					ClinicalTestDataES obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(!obj
								.getSelect(), obj.getDataId(), obj.getSpecimenCode(), obj
								.getStudyNo(), obj.getReqNo(), obj.getAnimalId(), obj
								.getAnimalCode(), obj.getSpecimenCode(), obj.getTestItem(), obj
								.getTestIndex(), obj.getTestIndexAbbr(), obj.getTestData(), obj
								.getTestIndexUnit(), obj.getCollectionMode(), obj
								.getCollectionTime(), obj.getAcceptTime(), obj.getEs(), obj
								.getConfirmFlag() + "",obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
								,obj.getIndex());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								clinicalTestDataES);
						
						selectSameLotData(clinicalTestDataES);
					}
					table.getSelectionModel().clearSelection();
				}

			}

		});
	     
	}
	
	
	/**
	 * 更新   表格   数据  （查询数据库）
	 * @param endCodeStr2 
	 * @param beginCodeStr2 
	 * @param reqNo2 
	 * @param studyNo2 
	 */
	private void updateTableData(String  date,int testItem, String studyNo2, int reqNo2, String beginCodeStr2, String endCodeStr2){
		data_table.clear();
		dataOne_table.clear();
//		data_table2.clear();
		int es=0;//未签字
		if("".equals(date)){
			date=DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		}
		table.setItems(data_table);
		//临时存放
		ObservableList<ClinicalTestDataES> data2_table = FXCollections.observableArrayList();
		if(null!=date&&!date.equals("")){
			if(date.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
				List<TblClinicalTestData> list=null;
//				if(testItem==0){
//					list=BaseService.getTblClinicalTestDataService().findByDateES(date,es);
//				}else{
//					list=BaseService.getTblClinicalTestDataService().findByDateTestItemES(date,testItem,es);
//				}
				//备份查询条件
				copyDate =date;
				copyTestItem = testItem;
				copyStudyNo =studyNo2;
				copyReqNo =reqNo2;
				list=BaseService.getTblClinicalTestDataService().findByDateTestItemESStudyNOReqNoCode(date,testItem,es,studyNo2,reqNo2,beginCodeStr2,endCodeStr2);
				if(null!=list&&list.size()>0){
					int index =0;
					for(TblClinicalTestData obj:list){
						String testItemStr =testItemList.get(obj.getTestItem()-1);
						String collectionTime="";
						if(null!=obj.getCollectionTime()){
							collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "HH:mm:ss");
						}
						String acceptTime="";
						if(null!=obj.getAcceptTime()){
							acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "HH:mm:ss");
						}
						String remark = obj.getRemark();
						String remark2 ="";
						if(null != remark && remark.contains("@")){
							String[] strs = remark.split("@");
							remark = strs[1];
							remark2 = strs[0];
						}
						ClinicalTestDataES clinicalTestDataES =new ClinicalTestDataES(
								false,
								obj.getDataId(),
								obj.getTblSpecimen().getSpecimenId(),
								obj.getStudyNo(),
								obj.getReqNo(),
								obj.getAnimalId(),
								obj.getAnimalCode(),
								obj.getSpecimenCode(),
								testItemStr,
								obj.getTestIndex(),
								obj.getTestIndexAbbr(),
								obj.getTestData(),
								obj.getTestIndexUnit(),
								obj.getCollectionMode()==1 ? "自动":"手动",
								collectionTime,
								acceptTime,
							    obj.getEs()==0?"否":"是",
							    obj.getConfirmFlag()+"",
							    (obj.getValidFlag()==1),
							    remark,remark2
							    ,index
								);
						data2_table.add(clinicalTestDataES);
//						data_table2.add(clinicalTestDataES);
						index++;
					}
				}
			}
		}
		
		data_table.addAll(data2_table);
		
		//设置未选中
		checkAllBox.setSelected(false);
		
		if(testItem>0){
			confirmButton.setDisable(false);
			delButton.setDisable(false);
		}else{
			confirmButton.setDisable(true);
			delButton.setDisable(true);
		}
		
	}
	/**
	 * 更新  aboutTable 表格   数据  （查询数据库）
	 * @param endCodeStr2 
	 * @param beginCodeStr2 
	 * @param reqNo2 
	 * @param studyNo2 
	 */
	private void updateAboutTableData(String  date,int testItem, String studyNo2, int reqNo2, String beginCodeStr2, String endCodeStr2){
		data_aboutTable.clear();
		int es=0;//未签字
		if("".equals(date)){
			date=DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		}
		ObservableList<SpecimenAbout> data2_aboutTable = FXCollections.observableArrayList();
		data2_aboutTable.add(new SpecimenAbout("    全部","","","",""));
		if(null!=date&&!date.equals("")){
			if(date.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
				//查询数据库得到   检验指标、动物编号、动物Id号、检验项目、专题编号
				List<?> list = BaseService.getTblClinicalTestDataService().findListByDateTestItemESStudyNOReqNoCode(date,testItem,es,studyNo2,reqNo2,beginCodeStr2,endCodeStr2);
				if(null!=list&&list.size()>0){
					for(Object obj:list){
						Object[] objs = (Object[]) obj;
						String specimenCode = (String) objs[0];
						String animalCode = (String) objs[1];
						String animalId = (String) objs[2];
						Integer testItemInt  = (Integer) objs[3];
						String studyNo = (String) objs[4];
						
						String testItemStr =testItemList.get(testItemInt-1);
						SpecimenAbout specimenAbout =new SpecimenAbout(
								specimenCode,animalCode,animalId,testItemStr,studyNo
								);
						data2_aboutTable.add(specimenAbout);
					}
				}
			}
		}
		data_aboutTable.addAll(data2_aboutTable);
//		aboutTable.setItems(data_aboutTable);
		//设置未选中
		checkAllBox.setSelected(false);
	}
	
	/**选中或取消  同编号同一时间检测的数据（限血常规、尿液）
	 * @param obj
	 */
	private void selectSameLotData(ClinicalTestDataES data) {
		if(null != data && (data.getTestItem().equals("血液检验") 
				|| data.getTestItem().equals("尿液检验") )){
			boolean state = data.getSelect();
			String studyNo = data.getStudyNo();
			String reqNo = data.getReqNo();
			String testItem = data.getTestItem();
			String specimenCode = data.getSpecimenCode();
			String collectionTime = data.getCollectionTime();
			if(null != dataOne_table && !dataOne_table.isEmpty()){
				int i=0;
				for (ClinicalTestDataES obj : dataOne_table) {
					if(studyNo.equals(obj.getStudyNo()) && reqNo.equals(obj.getReqNo()) 
							&& testItem.equals(obj.getTestItem()) && specimenCode.equals(obj.getSpecimenCode())
							&& collectionTime.equals(obj.getCollectionTime())){
						ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(state,
								obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
								obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
								obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
								obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
								obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
								obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
								,obj.getIndex());
						dataOne_table.set(i, clinicalTestDataES);
					}
					i++;
				}
			}else{
				int i=0;
				ObservableList<ClinicalTestDataES> data2_table = FXCollections.observableArrayList();
				for (ClinicalTestDataES obj : data_table) {
					if(studyNo.equals(obj.getStudyNo()) && reqNo.equals(obj.getReqNo()) 
							&& testItem.equals(obj.getTestItem()) && specimenCode.equals(obj.getSpecimenCode())
							&& collectionTime.equals(obj.getCollectionTime())){
						ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(state,
								obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
								obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
								obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
								obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
								obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
								obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
								,obj.getIndex());
						data2_table.add(i, clinicalTestDataES);
					}else{
						ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(obj.getSelect(),
								obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
								obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
								obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
								obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
								obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
								obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
								,obj.getIndex());
						data2_table.add(i, clinicalTestDataES);
					}
					i++;
				}
				data_table.clear();
				data_table.addAll(data2_table);
			}
		}
	}
	
	//全选多选框
	@FXML
	private void onCheckBoxAction(ActionEvent event) {
		
		CheckBox checkBox = (CheckBox) event.getSource();
		boolean state = checkBox.isSelected();
		if(null != dataOne_table && !dataOne_table.isEmpty()){
			int i=0;
			for (ClinicalTestDataES obj : dataOne_table) {
				ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(state,
						obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
						obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
						obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
						obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
						obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
						obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
						,obj.getIndex());
				dataOne_table.set(i, clinicalTestDataES);
				i++;
			}
		}else{
			int i=0;
//			for (ClinicalTestDataES obj : data_table) {
//				ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(state,
//						obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
//						obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
//						obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
//						obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
//						obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
//						obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
//						,obj.getIndex());
//				data_table.set(i, clinicalTestDataES);
//				i++;
//			}
			ObservableList<ClinicalTestDataES> data2_table = FXCollections.observableArrayList();
			for (ClinicalTestDataES obj : data_table) {
				ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(state,
						obj.getDataId(), obj.getSpecimenCode(), obj.getStudyNo(), obj.getReqNo(),
						obj.getAnimalId(), obj.getAnimalCode(), obj.getSpecimenCode(),
						obj.getTestItem(), obj.getTestIndex(), obj.getTestIndexAbbr(),
						obj.getTestData(), obj.getTestIndexUnit(), obj.getCollectionMode(),
						obj.getCollectionTime(), obj.getAcceptTime(), obj.getEs(),
						obj.getConfirmFlag(),obj.getValidFlag(),obj.getRemark(),obj.getRemark2()
						,obj.getIndex());
				data2_table.add(i, clinicalTestDataES);
				i++;
			}
			data_table.clear();
			data_table.addAll(data2_table);
		}

	}

	// 查询按钮事件
	@FXML
	public void onSelectBtnAction(ActionEvent event) {
		beginCodeStr = beginCode.getText().toString().trim();
		endCodeStr = endCode.getText().toString().trim();
		//更新  table 表格 数据 （查询数据库）
		updateTableData(date, testItem, studyNo, reqNo, beginCodeStr, endCodeStr);
		//更新 aboutTable 表格 数据 （查询数据库）
		updateAboutTableData(date,testItem,studyNo,reqNo,beginCodeStr,endCodeStr);
				
	}

	/**选中指定检验编号
	 * @param specimenCode
	 */
	public void selectAboutTableLine(String specimenCode){
		if(null != specimenCode){
			int i = 0;
			for(SpecimenAbout specimenAbout:data_aboutTable){
				if(specimenCode.equals(specimenAbout.getSpecimenCode())){
					aboutTable.getSelectionModel().select(i);
					aboutTable.scrollTo(i);
					break;
				}
				i++;
			}
			
		}
	}
	
	
	// 确定按钮事件
	@FXML
	public void onConfirmBtnAction(ActionEvent event) {
		//选中第一个
		aboutTable.getSelectionModel().selectFirst();
		if(copyTestItem==0){
			confirmButton.setDisable(true);
			return ;
		}
		
		if(copyTestItem == 2){
			List<String> retDataIdList = new ArrayList<String>();
			//选中的是血常规，判断是否有  RET#  999.9 问题 TODO
			for(ClinicalTestDataES obj:data_table){
				if(obj.getSelect() && obj.getTestIndexAbbr().equals("RET#") && "999.9".equals(obj.getTestData())){
					retDataIdList.add(obj.getDataId());
				}
			}
			if(null != retDataIdList && retDataIdList.size() > 0){
				if(Messager.showSimpleConfirm("提示", "存在指标RET#数据为999.9，是否修改？")){
//					List<String> noEditRetDataIdList = BaseService.getTblClinicalTestDataService().getNoeditDataIdList(retDataIdList);
					if(null != retDataIdList && retDataIdList.size() > 0){
						try {
							Stage stage = Main.stageMap.get("DataEditPage");
							if(null == stage){
								stage =new Stage();
								stage.initOwner(table.getScene().getWindow());
								stage.initModality(Modality.APPLICATION_MODAL);
								DataEditPage.getInstance().start(stage);
								Main.stageMap.put("DataEditPage",stage);
							}else{
								stage.show();
							}
							DataEditPage.getInstance().loadData(retDataIdList);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return;
					}
				}
			}
		}
		
		/**
		 * 是否全没选
		 */
		boolean noneSelect = allFalse();
		if (noneSelect) {
//			Alert2.create("请先选择数据");
			Messager.showWarnMessage("请先选择数据！");
			return;
		}

		
		//待签字数据dataId列表
		List<String> list = new ArrayList<String>();
		//检验编号：选择的数据列表
		Map<String,List<ClinicalTestDataES>> specimenCodeMap =new HashMap<String,List<ClinicalTestDataES>>();
		//检验编号：未选择的数据列表
		Map<String,List<ClinicalTestDataES>> specimenCodeMap2 =new HashMap<String,List<ClinicalTestDataES>>();
		//临时存放
		ObservableList<ClinicalTestDataES> data2_table = FXCollections.observableArrayList();
		data2_table.addAll(data_table);
		int firstSelectedIndex = 0;
		
		for(ClinicalTestDataES obj:data2_table){
			if(obj.getSelect()){
				list.add(obj.getDataId());
				if(firstSelectedIndex == 0){
					firstSelectedIndex = data2_table.indexOf(obj);
					table.scrollTo(firstSelectedIndex);
				}
				List<ClinicalTestDataES> dataList =  specimenCodeMap.get(obj.getSpecimenCode());
				if(null == dataList){
					dataList = new ArrayList<ClinicalTestDataES>();
				}
				dataList.add(obj);
				specimenCodeMap.put(obj.getSpecimenCode(), dataList);
			}else{
				List<ClinicalTestDataES> dataList =  specimenCodeMap2.get(obj.getSpecimenCode());
				if(null == dataList){
					dataList = new ArrayList<ClinicalTestDataES>();
				}
				dataList.add(obj);
				specimenCodeMap2.put(obj.getSpecimenCode(), dataList);
			}
		}
		
		//1.判断是否存在已签字
		
		//查询对应课题、申请、检测项目、检验编号、指标    是否存在已签字且有效的数据，
		//map中存放  exist：true false ,    msg:提示信息   dataId:待签字数据主键 
		Map<String,Object> resultMap = BaseService.getTblClinicalTestDataService().isHaveValidData(list);
		if((boolean) resultMap.get("exist")){
			String msg = (String) resultMap.get("msg");
			Messager.showWarnMessage(msg);
			return ;
		}
		
		//2.判断选中 中是否有重复的指标
		// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
		Set<String> specimenCodeSet = specimenCodeMap.keySet();
		List<String> specimenCodeList = null ;
		if(null != specimenCodeSet){
			specimenCodeList =new ArrayList<String>(specimenCodeSet);
//			specimenCodeList.
			Collections.sort(specimenCodeList);
			for(String specimenCode :specimenCodeList){
				List<ClinicalTestDataES> dataList = specimenCodeMap.get(specimenCode);
				if(null != dataList){
					int i = 0;
					for(ClinicalTestDataES obj:dataList){
						for(int j = i+1;j < dataList.size();j++){
							boolean isHas = false;
							if(dataList.get(j).getSpecimenCode().equals(obj.getSpecimenCode())
									&& dataList.get(j).getTestItem().equals(obj.getTestItem())
									&& dataList.get(j).getTestIndexAbbr().equals(obj.getTestIndexAbbr())
									&& dataList.get(j).getStudyNo().equals(obj.getStudyNo())
									&& dataList.get(j).getReqNo().equals(obj.getReqNo())){
								isHas = true;
							}
							if(isHas){
								selectAboutTableLine(obj.getSpecimenCode());
								Messager.showWarnMessage(obj.getSpecimenCode() + ":"
										+ obj.getTestIndexAbbr() + "只能确认签字其中一个！");
								return;
							}
						}
						i++;
					}
				}
			}
		}
		
		//2.5检查同一次检验的标本是否都被选中  ，若是继续，否返回；         ，血液或尿液  ，同时删 或  同时确认
		if(copyTestItem == 2 || copyTestItem==4){
			if(null != specimenCodeList){
				for(String specimenCode :specimenCodeList){
					List<ClinicalTestDataES> dataList = specimenCodeMap.get(specimenCode);
					//未选中的数据
					List<ClinicalTestDataES> dataList2 = specimenCodeMap2.get(specimenCode);
					if(null != dataList && null != dataList2){
						for(ClinicalTestDataES obj:dataList){
							String studyNo =obj.getStudyNo();
							String reqNo   =obj.getReqNo();
							String testItem = obj.getTestItem();
							String confirmFlag = obj.getConfirmFlag();
							for(ClinicalTestDataES obj2:dataList2){
								if(obj2.getStudyNo().equals(studyNo)
										&& obj2.getReqNo().equals(reqNo)
										&& obj2.getTestItem().equals(testItem)
										&& obj2.getConfirmFlag().equals(confirmFlag)){
									String testItemStr= testItemList.get(copyTestItem-1);
									selectAboutTableLine(specimenCode);
//										Alert2.create(testItemStr+"确认时,同一检验编号的同一次检验的各指标必须同时确认");
									Messager.showWarnMessage(testItemStr+"确认时,同一检验编号("+specimenCode+")的同一次检验的各指标必须同时确认！");
									return;
								}
							}
						}
					}
				}
			}
		}
		//3.判断  计算指标是否匹配(生化)
		if(null != specimenCodeList && copyTestItem == 1){
			for(String specimenCode :specimenCodeList){
				List<ClinicalTestDataES> dataList = specimenCodeMap.get(specimenCode);
				if(null != dataList){
					int i = 0;
					for(ClinicalTestDataES obj1:dataList){
						String indexAbbr = obj1.getTestIndexAbbr();
						//判断是否是计算指标
						if(indexAbbr.equalsIgnoreCase("GLOB") || indexAbbr.equalsIgnoreCase("A/G") ||
								indexAbbr.equalsIgnoreCase("IBIL")){
							String thisSpecimenCode =obj1.getSpecimenCode();
							String thisTestItemStr = obj1.getTestItem();
							int thisTestItem = testItemList.indexOf(thisTestItemStr) + 1;
							String thisStudyNo =  obj1.getStudyNo();
							String thisReqNo = obj1.getReqNo();
							String remark = obj1.getRemark();
							String remark2 = obj1.getRemark2();
							
							if(indexAbbr.equalsIgnoreCase("GLOB") || indexAbbr.equalsIgnoreCase("A/G")){
								
								String tpConfirmFlag = "";
								String tpTestData ="";
								//3.1 判断 TP是否 已有签字，
								boolean isTPExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
										thisSpecimenCode,
										thisTestItem,
										"TP",thisStudyNo,
										thisReqNo);
								//3.2 若签字，判断是否有其他选中，有报错，无  OK
								if (isTPExist) {
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("TP")){
//											Alert2.create(obj.getSpecimenCode() + ":"
//													+ obj.getTestIndexAbbr() + "存在已签字数据");
											selectAboutTableLine(obj1.getSpecimenCode());
											Messager.showWarnMessage(obj1.getSpecimenCode() + ":"
													+ obj1.getTestIndexAbbr() + "存在已签字数据！");
											return;
										}
									}
									
									tpConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
											thisSpecimenCode,
											thisTestItem,
											"TP",thisStudyNo,
											thisReqNo);
									tpTestData= BaseService.getTblClinicalTestDataService().getTestData(
											thisSpecimenCode,
											thisTestItem,
											"TP",thisStudyNo,
											thisReqNo);
									
								}else{
									//3.3若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
									int i2 = 0;
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("TP")){
											isTPExist = true;
											tpConfirmFlag = obj2.getConfirmFlag();
											tpTestData = obj2.getTestData();
											for (int j = i2 + 1; j < dataList.size(); j++) {
													// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
													boolean isHas = false;
													if (dataList.get(j).getSpecimenCode()
															.equals(thisSpecimenCode)
															&& dataList.get(j).getTestItem()
															.equals(thisTestItemStr)
															&& dataList.get(j).getTestIndexAbbr()
															.equals("TP")
															&& dataList.get(j).getStudyNo()
															.equals(thisStudyNo)
															&& dataList.get(j).getReqNo()
															.equals(thisReqNo)) {
														isHas = true;
													}
													if (isHas) {
//														Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
//																+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
														selectAboutTableLine(obj2.getSpecimenCode());
														Messager.showWarnMessage(obj2.getSpecimenCode() + ":"
																+ obj2.getTestIndexAbbr() + "只能确认签字其中一个！");
														return;
													}
											}
										}
										i2++;
									}
									//不存在
									if(!isTPExist){
//										Alert2.create("请选择与"+thisSpecimenCode + ":"
//												+ indexAbbr + "匹配的  TP 指标");
										selectAboutTableLine(thisSpecimenCode);
										Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
												+ indexAbbr + "匹配的  TP 指标！");
										return;
									}
								}
								
								//  ALB  同理--------------------------------------------
								String albConfirmFlag = "";
								String albTestData ="";
								//3.4 判断ALB是否 已有签字，
								boolean isALBExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
										thisSpecimenCode,
										thisTestItem,
										"ALB",thisStudyNo,
										thisReqNo);
								//3.5 若签字，判断是否有其他选中，有报错，无  OK
								if (isALBExist) {
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("ALB")){
//											Alert2.create(obj.getSpecimenCode() + ":"
//													+ obj.getTestIndexAbbr() + "存在已签字数据");
											selectAboutTableLine(obj2.getSpecimenCode() );
											Messager.showWarnMessage(obj2.getSpecimenCode() + ":"
													+ obj2.getTestIndexAbbr() + "存在已签字数据！");
											return;
										}
									}
									
									albConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
											thisSpecimenCode,
											thisTestItem,
											"ALB",thisStudyNo,
											thisReqNo);
									albTestData =BaseService.getTblClinicalTestDataService().getTestData(
											thisSpecimenCode,
											thisTestItem,
											"ALB",thisStudyNo,
											thisReqNo);
									
								}else{
									//3.6若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
									int i2 = 0;
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("ALB")){
											isALBExist = true;
											albConfirmFlag = obj2.getConfirmFlag();
											albTestData = obj2.getTestData();
											for (int j = i2 + 1; j < dataList.size(); j++) {
													// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
													boolean isHas = false;
													if (dataList.get(j).getSpecimenCode()
															.equals(thisSpecimenCode)
															&& dataList.get(j).getTestItem()
															.equals(thisTestItemStr)
															&& dataList.get(j).getTestIndexAbbr()
															.equals("ALB")
															&& dataList.get(j).getStudyNo()
															.equals(thisStudyNo)
															&& dataList.get(j).getReqNo()
															.equals(thisReqNo)) {
														isHas = true;
													}
													if (isHas) {
//														Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
//																+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
														selectAboutTableLine(dataList.get(j).getSpecimenCode() );
														Messager.showWarnMessage(dataList.get(j).getSpecimenCode() + ":"
																+ dataList.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
														return;
													}
											}
										}
										
										i2++;
									}
									//不存在
									if(!isALBExist){
//										Alert2.create("请选择与"+thisSpecimenCode + ":"
//												+ indexAbbr + "匹配的  ALB 指标");
										selectAboutTableLine(thisSpecimenCode);
										Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
												+ indexAbbr + "匹配的  ALB 指标！");
										return;
									}
								}
								
								//判断  TP  和ALB    与     GLOB     是否匹配
								if(!remark2.contains(tpConfirmFlag+","+albConfirmFlag) ||
										!remark.contains("TP:"+tpTestData+"、"+"ALB:"+albTestData)
										){
//									Alert2.create("指标TP、ALB与"+thisSpecimenCode + ":"
//											+ indexAbbr + "不匹配");
									selectAboutTableLine(thisSpecimenCode);
									Messager.showWarnMessage("指标TP、ALB与"+thisSpecimenCode + ":"
											+ indexAbbr + "不匹配！");
									return;
								}
								
							}else{

								//验证IBIL
								///-----------------------------------------
								String tbilConfirmFlag = "";
								String tbilTestData ="";
								//3.1 判断TBIL是否 已有签字，
								boolean isTBILExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
										thisSpecimenCode,
										thisTestItem,
										"TBIL",thisStudyNo,
										thisReqNo);
								//3.2 若签字，判断是否有其他选中，有报错，无  OK
								if (isTBILExist) {
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("TBIL")){
//											Alert2.create(obj.getSpecimenCode() + ":"
//													+ obj.getTestIndexAbbr() + "存在已签字数据");
											selectAboutTableLine(obj2.getSpecimenCode());
											Messager.showWarnMessage(obj2.getSpecimenCode() + ":"
													+ obj2.getTestIndexAbbr() + "存在已签字数据！");
											return;
										}
									}
									
									tbilConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
											thisSpecimenCode,
											thisTestItem,
											"TBIL",thisStudyNo,
											thisReqNo);
									tbilTestData =BaseService.getTblClinicalTestDataService().getTestData(
											thisSpecimenCode,
											thisTestItem,
											"TBIL",thisStudyNo,
											thisReqNo);
									
								}else{
									//3.3若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
									int i2 = 0;
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("TBIL")){
											isTBILExist = true;
											tbilConfirmFlag = obj2.getConfirmFlag();
											tbilTestData = obj2.getTestData();
											for (int j = i2 + 1; j < dataList.size(); j++) {
													// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
													boolean isHas = false;
													if (dataList.get(j).getSpecimenCode()
															.equals(thisSpecimenCode)
															&& dataList.get(j).getTestItem()
															.equals(thisTestItemStr)
															&& dataList.get(j).getTestIndexAbbr()
															.equals("TBIL")
															&& dataList.get(j).getStudyNo()
															.equals(thisStudyNo)
															&& dataList.get(j).getReqNo()
															.equals(thisReqNo)) {
														isHas = true;
													}
													if (isHas) {
//														Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
//																+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
														selectAboutTableLine(dataList.get(j).getSpecimenCode());
														Messager.showWarnMessage(dataList.get(j).getSpecimenCode() + ":"
																+ dataList.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
														return;
													}
											}
										}
										i2++;
									}
									//不存在
									if(!isTBILExist){
//										Alert2.create("请选择与"+thisSpecimenCode + ":"
//												+ indexAbbr + "匹配的  TBIL 指标");
										selectAboutTableLine(thisSpecimenCode);
										Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
												+ indexAbbr + "匹配的  TBIL 指标！");
										return;
									}
								}
								
							//  DBIL  同理--------------------------------------------
								String dbilConfirmFlag = "";
								String dbilTestData = "";
								//3.4 判断ALB是否 已有签字，
								boolean isDBILExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
										thisSpecimenCode,
										thisTestItem,
										"DBIL",thisStudyNo,
										thisReqNo);
								//3.5 若签字，判断是否有其他选中，有报错，无  OK
								if (isDBILExist) {
									for(ClinicalTestDataES obj2 :dataList){
										if(obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("DBIL")){
//											Alert2.create(obj.getSpecimenCode() + ":"
//													+ obj.getTestIndexAbbr() + "存在已签字数据");
											selectAboutTableLine(obj2.getSpecimenCode());
											Messager.showWarnMessage(obj2.getSpecimenCode() + ":"
													+ obj2.getTestIndexAbbr() + "存在已签字数据！");
											return;
										}
									}
									
									dbilConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
											thisSpecimenCode,
											thisTestItem,
											"DBIL",thisStudyNo,
											thisReqNo);
									dbilTestData =BaseService.getTblClinicalTestDataService().getTestData(
											thisSpecimenCode,
											thisTestItem,
											"DBIL",thisStudyNo,
											thisReqNo);
									
								}else{
									//3.6若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
									int i2 = 0;
									for(ClinicalTestDataES obj2 :dataList){
										if( obj2.getSpecimenCode().equals(thisSpecimenCode) 
												&& obj2.getTestItem().equals(thisTestItemStr) && 
												obj2.getStudyNo().equals(thisStudyNo)&& obj2.getReqNo().equals(thisReqNo)
												&& obj2.getTestIndexAbbr().equals("DBIL")){
											isDBILExist = true;
											dbilConfirmFlag = obj2.getConfirmFlag();
											dbilTestData = obj2.getTestData();
											for (int j = i2 + 1; j < dataList.size(); j++) {
													// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
													boolean isHas = false;
													if (dataList.get(j).getSpecimenCode()
															.equals(thisSpecimenCode)
															&& dataList.get(j).getTestItem()
															.equals(thisTestItemStr)
															&& dataList.get(j).getTestIndexAbbr()
															.equals("DBIL")
															&& dataList.get(j).getStudyNo()
															.equals(thisStudyNo)
															&& dataList.get(j).getReqNo()
															.equals(thisReqNo)) {
														isHas = true;
													}
													if (isHas) {
//														Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
//																+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
														selectAboutTableLine(dataList.get(j).getSpecimenCode());
														Messager.showWarnMessage(dataList.get(j).getSpecimenCode() + ":"
																+ dataList.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
														return;
													}
											}
										}
										i2++;
									}
									//不存在
									if(!isDBILExist){
//										Alert2.create("请选择与"+thisSpecimenCode + ":"
//												+ indexAbbr + "匹配的  ALB 指标");
										selectAboutTableLine(thisSpecimenCode);
										Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
												+ indexAbbr + "匹配的  ALB 指标！");
										return;
									}
								}
								
								//判断  TBIL  和DBIL    与     IBIL     是否匹配
								if(!remark2.contains(tbilConfirmFlag+","+dbilConfirmFlag) ||
										!remark.contains("TBIL:"+tbilTestData+"、"+"DBIL:"+dbilTestData)){
//									Alert2.create("指标TBIL、DBIL与"+thisSpecimenCode + ":"
//											+ indexAbbr + "不匹配");
									selectAboutTableLine(thisSpecimenCode);
									Messager.showWarnMessage("指标TBIL、DBIL与"+thisSpecimenCode + ":"
											+ indexAbbr + "不匹配！");
									return;
								}
								
							
							}
						}
						
						i++;
					}
				}
			}
		}
//		for (int i = 0; i < data2_table.size(); i++) {
//			if (data2_table.get(i).getSelect()) {
				
//				//2.判断选中 中是否有重复的指标
//				// 判断选中的数据当中有没有重复的，（同一个动物动物，同一检验指标只能有一个签字
//				for (int j = i + 1; j < data2_table.size(); j++) {
//					if (data2_table.get(j).getSelect()) {
//						// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
//						boolean isHas = false;
//						if (data2_table.get(j).getSpecimenCode()
//								.equals(data2_table.get(i).getSpecimenCode())
//								&& data2_table.get(j).getTestItem()
//										.equals(data2_table.get(i).getTestItem())
//								&& data2_table.get(j).getTestIndexAbbr()
//										.equals(data2_table.get(i).getTestIndexAbbr())
//								&& data2_table.get(j).getStudyNo()
//										.equals(data2_table.get(i).getStudyNo())
//								&& data2_table.get(j).getReqNo()
//										.equals(data2_table.get(i).getReqNo())) {
//							isHas = true;
//						}
//						if (isHas) {
//							table.scrollTo(i);
////							Alert2.create(data_table.get(j).getSpecimenCode() + ":"
////									+ data_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
//							Messager.showWarnMessage(data2_table.get(j).getSpecimenCode() + ":"
//									+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
//							return;
//						}
//					}
//				}
				//3.
//				//检查同一次检验的标本是否都被选中  ，若是继续，否返回；         ，血液或尿液  ，同时删 或  同时确认
//				if(copyTestItem == 2 || copyTestItem==4){
//					int index = 0;
//					for(ClinicalTestDataES obj :data2_table){
//						if(obj.getSelect()){
//							String studyNo =obj.getStudyNo();
//							String reqNo   =obj.getReqNo();
//							String specimenCode = obj.getSpecimenCode();
//							String testItem = obj.getTestItem();
//							String confirmFlag = obj.getConfirmFlag();
//							
//							for(int j = 0;j<data2_table.size();j++){
//								if(!data2_table.get(j).getSelect()
//										&& data2_table.get(j).getStudyNo().equals(studyNo)
//										&& data2_table.get(j).getReqNo().equals(reqNo)
//										&& data2_table.get(j).getSpecimenCode().equals(specimenCode)
//										&& data2_table.get(j).getTestItem().equals(testItem)
//										&& data2_table.get(j).getConfirmFlag().equals(confirmFlag)){
//									table.scrollTo(index);
//									String testItemStr= testItemList.get(copyTestItem-1);
////										Alert2.create(testItemStr+"确认时,同一检验编号的同一次检验的各指标必须同时确认");
//									Messager.showWarnMessage(testItemStr+"确认时,同一检验编号的同一次检验的各指标必须同时确认！");
//									return;
//								}
//							}
//						}
//						index++;
//					}
//				}
//				//3.判断    计算指标是否匹配(生化)
//				if(copyTestItem == 1){
//					String indexAbbr = data2_table.get(i).getTestIndexAbbr();
//					if(indexAbbr.equalsIgnoreCase("GLOB") || indexAbbr.equalsIgnoreCase("A/G") ||
//							indexAbbr.equalsIgnoreCase("IBIL")){
//
//						String thisSpecimenCode =data2_table.get(i).getSpecimenCode();
//						String thisTestItemStr = data2_table.get(i).getTestItem();
//						int thisTestItem = testItemList.indexOf(thisTestItemStr) + 1;
//						String thisStudyNo =  data2_table.get(i).getStudyNo();
//						String thisReqNo = data2_table.get(i).getReqNo();
//						String remark = data2_table.get(i).getRemark();
//						String remark2 = data2_table.get(i).getRemark2();
//						if(indexAbbr.equalsIgnoreCase("GLOB") || indexAbbr.equalsIgnoreCase("A/G")){
//							
//							String tpConfirmFlag = "";
//							String tpTestData ="";
//							//3.1 判断 TP是否 已有签字，
//							boolean isTPExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
//									thisSpecimenCode,
//									thisTestItem,
//									"TP",thisStudyNo,
//									thisReqNo);
//							//3.2 若签字，判断是否有其他选中，有报错，无  OK
//							if (isTPExist) {
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("TP")){
//										table.scrollTo(obj.getIndex());
////										Alert2.create(obj.getSpecimenCode() + ":"
////												+ obj.getTestIndexAbbr() + "存在已签字数据");
//										Messager.showWarnMessage(obj.getSpecimenCode() + ":"
//												+ obj.getTestIndexAbbr() + "存在已签字数据！");
//										return;
//									}
//								}
//								
//								tpConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
//										thisSpecimenCode,
//										thisTestItem,
//										"TP",thisStudyNo,
//										thisReqNo);
//								tpTestData= BaseService.getTblClinicalTestDataService().getTestData(
//										thisSpecimenCode,
//										thisTestItem,
//										"TP",thisStudyNo,
//										thisReqNo);
//								
//							}else{
//								//3.3若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
//								int thisIndex =0;
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("TP")){
//										isTPExist = true;
//										thisIndex = obj.getIndex();
//										tpConfirmFlag = obj.getConfirmFlag();
//										tpTestData = obj.getTestData();
//										for (int j = obj.getIndex() + 1; j < data2_table.size(); j++) {
//											if (data2_table.get(j).getSelect()) {
//												// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
//												boolean isHas = false;
//												if (data2_table.get(j).getSpecimenCode()
//														.equals(thisSpecimenCode)
//														&& data2_table.get(j).getTestItem()
//														.equals(thisTestItemStr)
//														&& data2_table.get(j).getTestIndexAbbr()
//														.equals("TP")
//														&& data2_table.get(j).getStudyNo()
//														.equals(thisStudyNo)
//														&& data2_table.get(j).getReqNo()
//														.equals(thisReqNo)) {
//													isHas = true;
//												}
//												if (isHas) {
//													table.scrollTo(obj.getIndex());
////													Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
////															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
//													Messager.showWarnMessage(data2_table.get(j).getSpecimenCode() + ":"
//															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
//													return;
//												}
//											}
//										}
//									}
//								}
//								//不存在
//								if(!isTPExist){
//									table.scrollTo(thisIndex);
////									Alert2.create("请选择与"+thisSpecimenCode + ":"
////											+ indexAbbr + "匹配的  TP 指标");
//									Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
//											+ indexAbbr + "匹配的  TP 指标！");
//									return;
//								}
//							}
//							
//							//  ALB  同理--------------------------------------------
//							String albConfirmFlag = "";
//							String albTestData ="";
//							//3.4 判断ALB是否 已有签字，
//							boolean isALBExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
//									thisSpecimenCode,
//									thisTestItem,
//									"ALB",thisStudyNo,
//									thisReqNo);
//							//3.5 若签字，判断是否有其他选中，有报错，无  OK
//							if (isALBExist) {
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("ALB")){
//										table.scrollTo(obj.getIndex());
////										Alert2.create(obj.getSpecimenCode() + ":"
////												+ obj.getTestIndexAbbr() + "存在已签字数据");
//										Messager.showWarnMessage(obj.getSpecimenCode() + ":"
//												+ obj.getTestIndexAbbr() + "存在已签字数据！");
//										return;
//									}
//								}
//								
//								albConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
//										thisSpecimenCode,
//										thisTestItem,
//										"ALB",thisStudyNo,
//										thisReqNo);
//								albTestData =BaseService.getTblClinicalTestDataService().getTestData(
//										thisSpecimenCode,
//										thisTestItem,
//										"ALB",thisStudyNo,
//										thisReqNo);
//								
//							}else{
//								//3.6若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
//								int thisIndex =0;
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("ALB")){
//										isALBExist = true;
//										thisIndex = obj.getIndex();
//										albConfirmFlag = obj.getConfirmFlag();
//										albTestData = obj.getTestData();
//										for (int j = obj.getIndex() + 1; j < data2_table.size(); j++) {
//											if (data2_table.get(j).getSelect()) {
//												// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
//												boolean isHas = false;
//												if (data2_table.get(j).getSpecimenCode()
//														.equals(thisSpecimenCode)
//														&& data2_table.get(j).getTestItem()
//														.equals(thisTestItemStr)
//														&& data2_table.get(j).getTestIndexAbbr()
//														.equals("ALB")
//														&& data2_table.get(j).getStudyNo()
//														.equals(thisStudyNo)
//														&& data2_table.get(j).getReqNo()
//														.equals(thisReqNo)) {
//													isHas = true;
//												}
//												if (isHas) {
//													table.scrollTo(obj.getIndex());
////													Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
////															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
//													Messager.showWarnMessage(data2_table.get(j).getSpecimenCode() + ":"
//															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
//													return;
//												}
//											}
//										}
//									}
//								}
//								//不存在
//								if(!isALBExist){
//									table.scrollTo(thisIndex);
////									Alert2.create("请选择与"+thisSpecimenCode + ":"
////											+ indexAbbr + "匹配的  ALB 指标");
//									Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
//											+ indexAbbr + "匹配的  ALB 指标！");
//									return;
//								}
//							}
//							
//							//判断  TP  和ALB    与     GLOB     是否匹配
//							if(!remark2.contains(tpConfirmFlag+","+albConfirmFlag) ||
//									!remark.contains("TP:"+tpTestData+"、"+"ALB:"+albTestData)
//									){
//								table.scrollTo(i);
////								Alert2.create("指标TP、ALB与"+thisSpecimenCode + ":"
////										+ indexAbbr + "不匹配");
//								Messager.showWarnMessage("指标TP、ALB与"+thisSpecimenCode + ":"
//										+ indexAbbr + "不匹配！");
//								return;
//							}
//							
//						}else{
//							//验证IBIL
//							///-----------------------------------------
//							String tbilConfirmFlag = "";
//							String tbilTestData ="";
//							//3.1 判断TBIL是否 已有签字，
//							boolean isTBILExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
//									thisSpecimenCode,
//									thisTestItem,
//									"TBIL",thisStudyNo,
//									thisReqNo);
//							//3.2 若签字，判断是否有其他选中，有报错，无  OK
//							if (isTBILExist) {
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("TBIL")){
//										table.scrollTo(obj.getIndex());
////										Alert2.create(obj.getSpecimenCode() + ":"
////												+ obj.getTestIndexAbbr() + "存在已签字数据");
//										Messager.showWarnMessage(obj.getSpecimenCode() + ":"
//												+ obj.getTestIndexAbbr() + "存在已签字数据！");
//										return;
//									}
//								}
//								
//								tbilConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
//										thisSpecimenCode,
//										thisTestItem,
//										"TBIL",thisStudyNo,
//										thisReqNo);
//								tbilTestData =BaseService.getTblClinicalTestDataService().getTestData(
//										thisSpecimenCode,
//										thisTestItem,
//										"TBIL",thisStudyNo,
//										thisReqNo);
//								
//							}else{
//								//3.3若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
//								int thisIndex =0;
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("TBIL")){
//										isTBILExist = true;
//										thisIndex = obj.getIndex();
//										tbilConfirmFlag = obj.getConfirmFlag();
//										tbilTestData = obj.getTestData();
//										for (int j = obj.getIndex() + 1; j < data2_table.size(); j++) {
//											if (data2_table.get(j).getSelect()) {
//												// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
//												boolean isHas = false;
//												if (data2_table.get(j).getSpecimenCode()
//														.equals(thisSpecimenCode)
//														&& data2_table.get(j).getTestItem()
//														.equals(thisTestItemStr)
//														&& data2_table.get(j).getTestIndexAbbr()
//														.equals("TBIL")
//														&& data2_table.get(j).getStudyNo()
//														.equals(thisStudyNo)
//														&& data2_table.get(j).getReqNo()
//														.equals(thisReqNo)) {
//													isHas = true;
//												}
//												if (isHas) {
//													table.scrollTo(obj.getIndex());
////													Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
////															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
//													Messager.showWarnMessage(data2_table.get(j).getSpecimenCode() + ":"
//															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
//													return;
//												}
//											}
//										}
//									}
//								}
//								//不存在
//								if(!isTBILExist){
//									table.scrollTo(thisIndex);
////									Alert2.create("请选择与"+thisSpecimenCode + ":"
////											+ indexAbbr + "匹配的  TBIL 指标");
//									Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
//											+ indexAbbr + "匹配的  TBIL 指标！");
//									return;
//								}
//							}
//							
//							//  DBIL  同理--------------------------------------------
//							String dbilConfirmFlag = "";
//							String dbilTestData = "";
//							//3.4 判断ALB是否 已有签字，
//							boolean isDBILExist = BaseService.getTblClinicalTestDataService().isHaveValidData(
//									thisSpecimenCode,
//									thisTestItem,
//									"DBIL",thisStudyNo,
//									thisReqNo);
//							//3.5 若签字，判断是否有其他选中，有报错，无  OK
//							if (isDBILExist) {
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("DBIL")){
//										table.scrollTo(obj.getIndex());
////										Alert2.create(obj.getSpecimenCode() + ":"
////												+ obj.getTestIndexAbbr() + "存在已签字数据");
//										Messager.showWarnMessage(obj.getSpecimenCode() + ":"
//												+ obj.getTestIndexAbbr() + "存在已签字数据！");
//										return;
//									}
//								}
//								
//								dbilConfirmFlag =BaseService.getTblClinicalTestDataService().getConfirmFlag(
//										thisSpecimenCode,
//										thisTestItem,
//										"DBIL",thisStudyNo,
//										thisReqNo);
//								dbilTestData =BaseService.getTblClinicalTestDataService().getTestData(
//										thisSpecimenCode,
//										thisTestItem,
//										"DBIL",thisStudyNo,
//										thisReqNo);
//								
//							}else{
//								//3.6若未签字，判断是否有唯一一个选中，有  ，   多 或无报错
//								int thisIndex =0;
//								for(ClinicalTestDataES obj :data2_table){
//									if(obj.getSelect() && obj.getSpecimenCode().equals(thisSpecimenCode) 
//											&& obj.getTestItem().equals(thisTestItemStr) && 
//											obj.getStudyNo().equals(thisStudyNo)&& obj.getReqNo().equals(thisReqNo)
//											&& obj.getTestIndexAbbr().equals("DBIL")){
//										isDBILExist = true;
//										thisIndex = obj.getIndex();
//										dbilConfirmFlag = obj.getConfirmFlag();
//										dbilTestData = obj.getTestData();
//										for (int j = obj.getIndex() + 1; j < data2_table.size(); j++) {
//											if (data2_table.get(j).getSelect()) {
//												// 是否有 检验编号，检验项目，检验指标，专题编号，申请编号都相同
//												boolean isHas = false;
//												if (data2_table.get(j).getSpecimenCode()
//														.equals(thisSpecimenCode)
//														&& data2_table.get(j).getTestItem()
//														.equals(thisTestItemStr)
//														&& data2_table.get(j).getTestIndexAbbr()
//														.equals("DBIL")
//														&& data2_table.get(j).getStudyNo()
//														.equals(thisStudyNo)
//														&& data2_table.get(j).getReqNo()
//														.equals(thisReqNo)) {
//													isHas = true;
//												}
//												if (isHas) {
//													table.scrollTo(obj.getIndex());
////													Alert2.create(data2_table.get(j).getSpecimenCode() + ":"
////															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个");
//													Messager.showWarnMessage(data2_table.get(j).getSpecimenCode() + ":"
//															+ data2_table.get(j).getTestIndexAbbr() + "只能确认签字其中一个！");
//													return;
//												}
//											}
//										}
//									}
//								}
//								//不存在
//								if(!isDBILExist){
//									table.scrollTo(thisIndex);
////									Alert2.create("请选择与"+thisSpecimenCode + ":"
////											+ indexAbbr + "匹配的  ALB 指标");
//									Messager.showWarnMessage("请选择与"+thisSpecimenCode + ":"
//											+ indexAbbr + "匹配的  ALB 指标！");
//									return;
//								}
//							}
//							
//							//判断  TBIL  和DBIL    与     IBIL     是否匹配
//							if(!remark2.contains(tbilConfirmFlag+","+dbilConfirmFlag) ||
//									!remark.contains("TBIL:"+tbilTestData+"、"+"DBIL:"+dbilTestData)){
//								table.scrollTo(i);
////								Alert2.create("指标TBIL、DBIL与"+thisSpecimenCode + ":"
////										+ indexAbbr + "不匹配");
//								Messager.showWarnMessage("指标TBIL、DBIL与"+thisSpecimenCode + ":"
//										+ indexAbbr + "不匹配！");
//								return;
//							}
//						}
//					}
//				}
//				list.add(data_table.get(i).getDataId());
//			}
//		}
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("签字");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
		
		
//		// 签字通过
//		if ("OK".equals(SignFrame.getType())) {
		User signUser = Sign.openSign("签字", "共选择"+list.size()+"条检验指标");
		//签字通过
		if(null != signUser){
			// 签字并设置有效性
			BaseService.getTblClinicalTestDataService().esAndSetValid(list,
					signUser.getRealName(), null, null);
			
			
			
			// 更新表格数据
			updateTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
			//更新 aboutTable 表格 数据 （查询数据库）
			updateAboutTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
			type = "OK";
//			Alert.create("签字成功");

			// 记录数据接收签字确认日志
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());		// 系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());	// 系统版本
			tblLog.setOperatType("签字");
			tblLog.setOperatOject("数据接收签字");
			User user = signUser;
			if (null != user) {
				tblLog.setOperator(user.getRealName());
			}
			tblLog.setOperatHost(SystemTool.getHostName() + "," + SystemTool.getIPAddress());
			BaseService.getTblLogService().save(tblLog);
			
			//发送通知  
			List<Map<String,Object>> studyNoReqNoUserNameList = BaseService.getTblClinicalTestDataService().getStudyNoReqNoUserNameByIds(list);
			if(null != studyNoReqNoUserNameList && studyNoReqNoUserNameList.size()>0){
				String sender = signUser.getRealName();
				for(Map<String,Object> map :studyNoReqNoUserNameList){
					String studyNo = (String)map.get("studyNo");
					String reqNo = (String)map.get("reqNo");
					String receiver = (String)map.get("userName");
					writeNotification(studyNo,reqNo,sender,receiver);
				}
			}
			
			Messager.showMessage("签字成功！");
		}
		
	}
	
	/**
	 * 发送通知
	 */
	private void writeNotification(String studyNo,String reqNo,String sender,String receiver){
		
		//当前时间
		String currentDate = DateUtil.dateToString(new Date(), "yyyy年MM月dd日 HH时mm分");
		
		//通知消息
		TblNotification tblNotification = new TblNotification();
		tblNotification.setMsgTitle("临检检测数据提醒");
		String msgContent = "SD,您好<br>&nbsp;&nbsp;&nbsp;&nbsp;";
		msgContent = msgContent+""+sender+" 于"+currentDate+"提交了一些检测数据," +
				"该数据对应的专题编号为"+studyNo+",申请单号为"+reqNo+",特此提醒";
		tblNotification.setMsgContent(msgContent);
		tblNotification.setMsgType(1);//系统消息
		
		tblNotification.setSender(sender);
		
		tblNotification.setSendTime(new Date());
		//接收者列表
		List<String> receiverList = new ArrayList<String>();
		receiverList.add(receiver);
		BaseService.getTblNotificationService().save(tblNotification,receiverList);
	}
	
	
	/**
	 * 删除按钮  事件
	 * @param event
	 */
	public void onDeleteButton(ActionEvent event){
		
		if(copyTestItem == 0){
			delButton.setDisable(true);
			return;
		}
		
//		//选中第一个
//		aboutTable.getSelectionModel().selectFirst();
		
		/**
		 * 是否全没选
		 */
		boolean noneSelect = noneSelect();
		if (noneSelect) {
//			Alert2.create("请先选择数据");
			Messager.showWarnMessage("请先选择数据！");
			return;
		}
		
		//检查同一次检验的标本是否都被选中  ，若是继续，否返回；         ，血液或尿液  ，同时删 或  同时确认
		if(copyTestItem == 2 || copyTestItem==4){
			if(null != dataOne_table && !dataOne_table.isEmpty()){
				int index = 0;
				for(ClinicalTestDataES obj :dataOne_table){
					if(obj.getSelect()){
						String studyNo =obj.getStudyNo();
						String reqNo   =obj.getReqNo();
						String specimenCode = obj.getSpecimenCode();
						String testItem = obj.getTestItem();
						String confirmFlag = obj.getConfirmFlag();
						
						for(int j = 0;j<dataOne_table.size();j++){
							if(!dataOne_table.get(j).getSelect()
									&& dataOne_table.get(j).getStudyNo().equals(studyNo)
									&& dataOne_table.get(j).getReqNo().equals(reqNo)
									&& dataOne_table.get(j).getSpecimenCode().equals(specimenCode)
									&& dataOne_table.get(j).getTestItem().equals(testItem)
									&& dataOne_table.get(j).getConfirmFlag().equals(confirmFlag)){
								table.scrollTo(index);
								String testItemStr= testItemList.get(copyTestItem-1);
//								Alert2.create(testItemStr+"删除时,同一检验编号的同一次检验的各指标必须同时删除");
								Messager.showWarnMessage(testItemStr+"删除时,同一检验编号的同一次检验的各指标必须同时删除！");
								return;
							}
						}
					}
					index++;
				}
			}else{
				int index = 0;
				for(ClinicalTestDataES obj :data_table){
					if(obj.getSelect()){
						String studyNo =obj.getStudyNo();
						String reqNo   =obj.getReqNo();
						String specimenCode = obj.getSpecimenCode();
						String testItem = obj.getTestItem();
						String confirmFlag = obj.getConfirmFlag();
						
						for(int j = 0;j<data_table.size();j++){
							if(!data_table.get(j).getSelect()
									&& data_table.get(j).getStudyNo().equals(studyNo)
									&& data_table.get(j).getReqNo().equals(reqNo)
									&& data_table.get(j).getSpecimenCode().equals(specimenCode)
									&& data_table.get(j).getTestItem().equals(testItem)
									&& data_table.get(j).getConfirmFlag().equals(confirmFlag)){
								table.scrollTo(index);
								String testItemStr= testItemList.get(copyTestItem-1);
//								Alert2.create(testItemStr+"删除时,同一检验编号的同一次检验的各指标必须同时删除");
								Messager.showWarnMessage(testItemStr+"删除时,同一检验编号的同一次检验的各指标必须同时删除！");
								return;
							}
						}
					}
					index++;
				}
			}
		}
		//待删除数据dataId列表
		List<String> list = new ArrayList<String>();
		if(null != dataOne_table && !dataOne_table.isEmpty()){
			for (int i = 0; i < dataOne_table.size(); i++) {
				if (dataOne_table.get(i).getSelect()) {
					list.add(dataOne_table.get(i).getDataId());
				}
			}
		}else{
			for (int i = 0; i < data_table.size(); i++) {
				if (data_table.get(i).getSelect()) {
					list.add(data_table.get(i).getDataId());
				}
			}
		}
		int size = list.size();
		if(size < 1){
			Messager.showWarnMessage("请先选择数据！");
			return ;
		}
//		if (Confirm.create(MainFrame.mainWidow, "提示", "您选择了 " + size + " 条数据，删除后将无法恢复！", "确定继续吗？")) {
		if(Messager.showConfirm("提示", "您选择了 " + size + " 条数据，删除后将无法恢复！", "确定继续吗？")){
//			SignFrameWithReason signFrameWithReason = new SignFrameWithReason("");
//			Stage stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.setTitle("签字");
//			try {
//				signFrameWithReason.start(stage);
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
			Map<String,Object> map =Sign.openSignWithReason("签字", "");
			// 签字通过
			if ((boolean) map.get("isPass")) {

				String reason = (String) map.get("reason");
				String realName = "";
				User signUser = (User) map.get("signUser");
				if(null != signUser){
					realName = signUser.getRealName();
				}
				// 删除并记录修改痕迹
				BaseService.getTblClinicalTestDataService().deleteAndRecordTrace(list,
						realName,reason,SystemTool.getHostName() );
				
				SpecimenAbout currentItem = aboutTable.getSelectionModel().getSelectedItem();
				int currentIndex = aboutTable.getSelectionModel().getSelectedIndex();
				// 更新表格数据 
				updateTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
				//更新 aboutTable 表格 数据 （查询数据库）
				updateAboutTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
				type = "OK";
				
				if(null != currentItem && currentIndex>-1){
//					aboutTable.getSelectionModel().selectFirst();
					if(data_aboutTable.size()>currentIndex){
						SpecimenAbout specimenAbout=data_aboutTable.get(currentIndex);
						if(specimenAbout.getSpecimenCode().equals(currentItem.getSpecimenCode())
								&& specimenAbout.getAnimalId().equals(currentItem.getAnimalId())
								&& specimenAbout.getTestItem().equals(currentItem.getTestItem())
								&& specimenAbout.getStudyNo().equals(currentItem.getStudyNo())){
							aboutTable.getSelectionModel().select(currentIndex);
						}
					}
				}

			
			}
		}
		// 签字通过
//		if ("OK".equals(SignFrameWithReason.getType())) {
//			// 删除并记录修改痕迹
//			BaseService.getTblClinicalTestDataService().deleteAndRecordTrace(list,
//					SignFrameWithReason.getUser().getRealName(),SignFrameWithReason.getReason(),SystemTool.getHostName() );
//			
//			SpecimenAbout currentItem = aboutTable.getSelectionModel().getSelectedItem();
//			int currentIndex = aboutTable.getSelectionModel().getSelectedIndex();
//			// 更新表格数据 
//			updateTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
//			//更新 aboutTable 表格 数据 （查询数据库）
//			updateAboutTableData(copyDate, copyTestItem, copyStudyNo, copyReqNo, beginCodeStr, endCodeStr);
//			type = "OK";
//			
//			if(null != currentItem && currentIndex>-1){
////				aboutTable.getSelectionModel().selectFirst();
//				if(data_aboutTable.size()>currentIndex){
//					SpecimenAbout specimenAbout=data_aboutTable.get(currentIndex);
//					if(specimenAbout.getSpecimenCode().equals(currentItem.getSpecimenCode())
//							&& specimenAbout.getAnimalId().equals(currentItem.getAnimalId())
//							&& specimenAbout.getTestItem().equals(currentItem.getTestItem())
//							&& specimenAbout.getStudyNo().equals(currentItem.getStudyNo())){
//						aboutTable.getSelectionModel().select(currentIndex);
//					}
//				}
//			}
//
//		}
	} 
	
		/**
		 * 初始化日期选择器
		 */
		private void initDate(){
			datePane= new DatePicker(Locale.CHINA);
			datePane.getTextField().setEditable(false);
			datePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			datePane.getCalendarView().todayButtonTextProperty().set("今天");
			datePane.getCalendarView().setShowWeeks(false);
			datePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
			datePane.setMaxWidth(148);
			
			dateVBox.getChildren().add(datePane);
			datePane.getTextField().textProperty().addListener(new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(null!=newValue&&!"".equals(newValue)){
						// 更新       的值
						date=newValue;
						upateStudyNoComboBox(newValue);
					}
				}

			});
//			datePane.getTextField().setText(date);
		}
		/**
		 * 更新 课题编号  下拉框
		 * @param newValue
		 */
		private void upateStudyNoComboBox(String newValue) {
			data_studyNoComboBox.clear();
			data_studyNoComboBox.add("");
			map_studyNoReqNoList = BaseService.getTblClinicalTestDataService().getMapByDate(newValue); 
			if(null!=map_studyNoReqNoList && map_studyNoReqNoList.size()>0){
				Set<String> set = map_studyNoReqNoList.keySet();
				if(null!=set && set.size()>0){
					Iterator<String> it = set.iterator();
					while(it.hasNext()){
						String str = it.next();
						data_studyNoComboBox.add(str);
					}
				}
			}
			studyNoComboBox.getSelectionModel().clearAndSelect(0);
		}
		/**
		 * 初始化   检测项目   下拉框
		 */
		private void initTestItemComboBox() {
			testItemComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
					if(newValue != null ){
						if(newValue.intValue()>-1){
							testItem = newValue.intValue();
						}else{
							testItem = 0;
						}
					}
					
				}
				
			});
		}
		/**
		 * 初始化   课题编号   下拉框
		 */
		private void initStudyNoComboBox(){
			studyNoComboBox.setItems(data_studyNoComboBox);
			studyNoComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
					if(null!=newValue){
						updateReqNoComboBox(newValue);
						studyNo =newValue;
					}
				}

				
			});
		}
		/**
		 * 更新 reqNoComboBox   值
		 * @param newValue
		 */
		private void updateReqNoComboBox(String newValue) {
			data_reqNoComboBox.clear();//清空
			data_reqNoComboBox.add("");
			if(null!=map_studyNoReqNoList && map_studyNoReqNoList.size()>0){
				List<String> list=map_studyNoReqNoList.get(newValue);
				if(null!=list&& list.size()>0){
					for(String str:list){
						data_reqNoComboBox.add(str);
					}
				}
			}
			reqNoComboBox.getSelectionModel().select(0);
		}
		/**
		 * 初始化   申请编号   下拉框
		 */
		private void initReqNoComboBox(){
			reqNoComboBox.setItems(data_reqNoComboBox);
			reqNoComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
					if(null!=newValue && !newValue.equals("")){
						if(newValue.contains(":")){
							reqNo =Integer.parseInt(newValue.split(":")[0]);
						}else{
							reqNo = 0;
						}
					}else{
						reqNo =0;
					}
					
				}
				
			});
		}
		//退出按钮事件
		@FXML
		public void onExitBtnAction(ActionEvent event){
			((javafx.scene.control.Control)event.getSource()).getParent().getScene().getWindow().hide();
		}
		/**
		 * 判断是否全没选(限所有数据data_table)
		 * @return
		 */
		private boolean allFalse() {
			for(int i=0;i<data_table.size();i++){
				if(null==data_table.get(i)){
					continue;
				}
				if(data_table.get(i).getSelect()){
					return false;
				}
			}
			return true;
		}
		/**
		 * 判断是否全没选(限当前表格显示数据)
		 * @return
		 */
		private boolean noneSelect() {
			if(null != dataOne_table && !dataOne_table.isEmpty()){
				for(ClinicalTestDataES obj:dataOne_table){
					if(obj.getSelect()){
						return false;
					}
				}
				return true;
			}else if(null!=data_table&&data_table.size()>0){
				for(ClinicalTestDataES obj:data_table){
					if(obj.getSelect()){
						return false;
					}
				}
				return true;
			}
			return false;
		}
		
		
		/**
		 * 判断是否全选(限当前表格显示数据)
		 * @return
		 */
		private static boolean tableAllSelected() {
			if(null != dataOne_table && !dataOne_table.isEmpty()){
				for(ClinicalTestDataES obj:dataOne_table){
					if(!obj.getSelect()){
						return false;
					}
				}
				return true;
			}else if(null!=data_table&&data_table.size()>0){
				for(ClinicalTestDataES obj:data_table){
					if(!obj.getSelect()){
						return false;
					}
				}
				return true;
			}
			return false;
		}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DataAcceptESFrame.fxml"));
		Scene scene = new Scene(root,1010,617);
//		stage.initOwner(MainFrame.mainWidow);
//		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setTitle("数据确认");
		stage.setMinHeight(600);
		stage.setMinWidth(1000);
//		stage.showAndWait();//签完字后，改变接收数据面板里数据状态
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
	
	public class SpecimenAbout{
		private StringProperty specimenCode;       //检验编号号
		private StringProperty animalCode;         //动物编号
		private StringProperty animalId;           //动物Id
	    private StringProperty testItem;              //检验项目
	    private StringProperty studyNo;            //课题编号
	    public SpecimenAbout(
	    		String specimenCode,       //检验编号号
	    		String animalCode,         //动物编号
	    		String animalId,           //动物Id
	    	    String testItem,             //检验项目
	    	    String studyNo          //课题编号
	    		){
	    	setSpecimenCode(specimenCode);
	    	setAnimalCode(animalCode);
	    	setAnimalId(animalId);
	    	setTestItem(testItem);
	    	setStudyNo(studyNo);
	    }
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}
		public String getAnimalId() {
			return animalId.get();
		}
		public void setAnimalId(String animalId) {
			this.animalId = new SimpleStringProperty(animalId);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getSpecimenCode() {
			return specimenCode.get();
		}
		public void setSpecimenCode(String specimenCode) {
			this.specimenCode = new SimpleStringProperty(specimenCode);
		}
		public String getTestItem() {
			return testItem.get();
		}
		public void setTestItem(String testItem) {
			this.testItem = new SimpleStringProperty(testItem);
		}
	    
	}
	public class ClinicalTestDataES {
		private BooleanProperty select;
		private StringProperty dataId;             //数据Id
		private StringProperty tblSpecimenId;  //标本接收登记Id
		private StringProperty studyNo;            //课题编号
		private StringProperty reqNo;              //申请编号
		private StringProperty animalId;           //动物Id
		private StringProperty animalCode;         //动物编号
	    private StringProperty specimenCode;       //检验编号号
	    private StringProperty testItem;              //检验项目
	    private StringProperty testIndex;          //检验指标
	    private StringProperty testIndexAbbr;      //检验指标缩写
	    private StringProperty testData;           //数据
	    private StringProperty testIndexUnit;      //检验指标单位
	    private StringProperty collectionMode;        //数据采集方式
	    private StringProperty collectionTime;       //数据采集时间(检验时间)
	    private StringProperty acceptTime;        //接收时间
	    private StringProperty es;               //签字    0：未签字   1：已签字
	    private StringProperty confirmFlag;      //1.第一次接收   2，第二次接收
	    private BooleanProperty validFlag;     //是否有效
	    
	    private StringProperty remark;    //指标与数值
	    private StringProperty remark2;   //confirmFlag，confirmFlag
	    private IntegerProperty index;
	    
	    public ClinicalTestDataES(){
	    	super();
	    }
	    public ClinicalTestDataES(  
	    		boolean select,
	    		String dataId,        
	    		String tblSpecimenId, 
	    		String studyNo,       
	    		String reqNo,         
	    		String animalId,      
	    		String animalCode,    
	    		String specimenCode,  
	    		String testItem,      
	    		String testIndex,     
	    		String testIndexAbbr, 
	    		String testData,      
	    		String testIndexUnit, 
	    		String collectionMode,
	    		String collectionTime,
	    		String acceptTime,    
	    		String es,            
	    		String confirmFlag ,
	    		boolean validFlag,
	    		String remark,
	    		String remark2,
	    		int index){
	    		this.select=new SimpleBooleanProperty(select);
	    	    this.dataId         =  new SimpleStringProperty(dataId        );
	    	    this.tblSpecimenId  =  new SimpleStringProperty(tblSpecimenId );
	    	    this.studyNo        =  new SimpleStringProperty(studyNo       );
	    	    this.reqNo			=  new SimpleStringProperty(reqNo		)  ;
	    	    this.animalId		=  new SimpleStringProperty(animalId	)  ;
	    	    this.animalCode		=  new SimpleStringProperty(animalCode	)  ;
	    	    this.specimenCode	=  new SimpleStringProperty(specimenCode)  ;
	    	    this.testItem		=  new SimpleStringProperty(testItem	)  ;
	    	    this.testIndex		=  new SimpleStringProperty(testIndex	)  ;
	    	    this.testIndexAbbr	=  new SimpleStringProperty(testIndexAbbr) ;
	    	    this.testData		=  new SimpleStringProperty(testData	)  ;
	    	    this.testIndexUnit	=  new SimpleStringProperty(testIndexUnit) ;
	    	    this.collectionMode	=  new SimpleStringProperty(collectionMode);
	    	    this.collectionTime	=  new SimpleStringProperty(collectionTime);
	    	    this.acceptTime		=  new SimpleStringProperty(acceptTime	)  ;
	    	    this.es				=  new SimpleStringProperty(es			)  ;
	    	    this.confirmFlag	=  new SimpleStringProperty(confirmFlag	  );
	    	    this.validFlag=new SimpleBooleanProperty(validFlag);
	    	    setRemark(remark);
	    	    setRemark2(remark2);
	    	    setIndex(index);
	    }
	    
	    
	    public BooleanProperty selectProperty(){return select;}
	    
		public boolean getSelect() {
			return select.get();
		}
		public void setSelect(boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getDataId() {
			return dataId.get();
		}
		public void setDataId(String dataId) {
			this.dataId =  new SimpleStringProperty(dataId);
		}
		public String getTblSpecimenId() {
			return tblSpecimenId.get();
		}
		public void setTblSpecimenId(String tblSpecimenId) {
			this.tblSpecimenId =  new SimpleStringProperty(tblSpecimenId);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo =  new SimpleStringProperty(studyNo);
		}
		public String getReqNo() {
			return reqNo.get();
		}
		public void setReqNo(String reqNo) {
			this.reqNo =  new SimpleStringProperty(reqNo);
		}
		public String getAnimalId() {
			return animalId.get();
		}
		public void setAnimalId(String animalId) {
			this.animalId =  new SimpleStringProperty(animalId);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode =  new SimpleStringProperty(animalCode);
		}
		public String getSpecimenCode() {
			return specimenCode.get();
		}
		public void setSpecimenCode(String specimenCode) {
			this.specimenCode =  new SimpleStringProperty(specimenCode);
		}
		public String getTestItem() {
			return testItem.get();
		}
		public void setTestItem(String testItem) {
			this.testItem =  new SimpleStringProperty(testItem);
		}
		public String getTestIndex() {
			return testIndex.get();
		}
		public void setTestIndex(String testIndex) {
			this.testIndex =  new SimpleStringProperty(testIndex);
		}
		public String getTestIndexAbbr() {
			return testIndexAbbr.get();
		}
		public void setTestIndexAbbr(String testIndexAbbr) {
			this.testIndexAbbr =  new SimpleStringProperty(testIndexAbbr);
		}
		public String getTestData() {
			return testData.get();
		}
		public void setTestData(String testData) {
			this.testData =  new SimpleStringProperty(testData);
		}
		public String getTestIndexUnit() {
			return testIndexUnit.get();
		}
		public void setTestIndexUnit(String testIndexUnit) {
			this.testIndexUnit =  new SimpleStringProperty(testIndexUnit);
		}
		public String getCollectionMode() {
			return collectionMode.get();
		}
		public void setCollectionMode(String collectionMode) {
			this.collectionMode = new SimpleStringProperty(collectionMode);
		}
		public String getCollectionTime() {
			return collectionTime.get();
		}
		public void setCollectionTime(String collectionTime) {
			this.collectionTime = new SimpleStringProperty(collectionTime);
		}
		public String getAcceptTime() {
			return acceptTime.get();
		}
		public void setAcceptTime(String acceptTime) {
			this.acceptTime = new SimpleStringProperty(acceptTime);
		}
		public String getEs() {
			return es.get();
		}
		public void setEs(String es) {
			this.es =  new SimpleStringProperty(es);
		}
		public String getConfirmFlag() {
			return confirmFlag.get();
		}
		public void setConfirmFlag(String confirmFlag) {
			this.confirmFlag =  new SimpleStringProperty(confirmFlag);
		}
		public boolean getValidFlag() {
			return validFlag.get();
		}
		public void setValidFlag(boolean validFlag) {
			this.validFlag = new SimpleBooleanProperty(validFlag);
		}   
		public BooleanProperty validFlagProperty(){return validFlag;}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		public int getIndex() {
			return index.get();
		}
		public void setIndex(int index) {
			this.index = new SimpleIntegerProperty(index);
		}
		public String getRemark2() {
			return remark2.get();
		}
		public void setRemark2(String remark2) {
			this.remark2 = new SimpleStringProperty(remark2);
		}
		    
			
		
	}

	// 在单元格里创建多选框
	  public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
	        private final CheckBox checkBox;
	        private ObservableValue<T> ov;
		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
			
		}
	        @Override public void updateItem(T item, boolean empty) {
	        	super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                setGraphic(checkBox);
	                if (ov instanceof BooleanProperty) {
	                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
	                }
	                ov = getTableColumn().getCellObservableValue(getIndex());
	                if (ov instanceof BooleanProperty) {
	                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
	                }
	                if(checkBox.isSelected()){
		 				getTableRow().setStyle("-fx-background-color:palegreen ;");
		 				if(tableAllSelected()){
		 					checkAllBox.setSelected(true);
		 				}
					}else{
						getTableRow().setStyle("");
						checkAllBox.setSelected(false);
					}
	                
	            }

	        }
			

	    }
//	  // 在单元格里创建多选框(是否有效)
//	  public static class CheckBoxTableCell2<S, T> extends TableCell<S, T> {
//		  private final CheckBox checkBox2;
//		  private ObservableValue<T> ov;
//		  public CheckBoxTableCell2() {
//			  this.checkBox2 = new CheckBox();
//			  this.checkBox2.setAlignment(Pos.CENTER);
//			  setAlignment(Pos.CENTER);
//			  setGraphic(checkBox2);
//		  }
//		  @Override public void updateItem(T item, boolean empty) {
//			  super.updateItem(item, empty);
//			  if (empty) {
//				  setText(null);
//				  setGraphic(null);
//			  } else {
//				  setGraphic(checkBox2);
//				  if (ov instanceof BooleanProperty) {
//					  checkBox2.selectedProperty().unbindBidirectional((BooleanProperty) ov);
//				  }
//				  ov = getTableColumn().getCellObservableValue(getIndex());
//				  if (ov instanceof BooleanProperty) {
//					  checkBox2.selectedProperty().bindBidirectional((BooleanProperty) ov);
//				  }
////				  if(checkBox.isSelected()){
////					  getTableRow().setStyle("-fx-background-color:palegreen ;");
////					  if(tableAllSelected()){
////						  checkAllBox.setSelected(true);
////					  }
////				  }else{
////					  getTableRow().setStyle("");
////					  checkAllBox.setSelected(false);
////				  }
//			  }
//			  
//		  }
		  
		  
//	  }

	public  String getDate() {
		return date;
	}
	public  void setDate(String date) {
		this.date = date;
	}
	public  int getTestItem() {
		return testItem;
	}
	public  void setTestItem(int testItem) {
		this.testItem = testItem;
	}
	public  String getType() {
		return type;
	}
	public  void setType(String type) {
		DataAcceptESFrame.type = type;
	}
	
}
