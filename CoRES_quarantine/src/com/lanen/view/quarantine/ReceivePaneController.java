package com.lanen.view.quarantine;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.quarantine.TblAnimalRecIndex;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.studyplan.DictAnimalStrain;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.TblAnimalRecIndexForTableView;
import com.lanen.model.tableview.TblQRRequestForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.popup.Alert2;
import com.lanen.view.main.Main;

import datecontrol.DatePicker;
import datecontrol.Junit;

/**
 * 接收登记
 * @author Administrator
 *
 */
public class ReceivePaneController implements Initializable{

	@FXML
	private HBox startDateHBox;   //日期
	@FXML
	private HBox endDateHBox;
	private DatePicker startDatePane=null;
	private DatePicker endDatePane=null;
	
	@FXML
	private ComboBox<String> animalTypeComboBox;			//	动物种类	AnimalType	
	private  ObservableList<String> data_animalTypeComboBox = FXCollections.observableArrayList();
	private Map<String,String> animalTypeNameIdMap = new HashMap<String,String>();
	@FXML
	private ComboBox<String> animalStrainComboBox;        //	动物品系	AnimalStrain
	private  ObservableList<String> data_animalStrainComboBox = FXCollections.observableArrayList();
	
	@FXML
	private TableView<TblAnimalRecIndexForTableView> tblAnimalRecIndexTable; 
	private ObservableList<TblAnimalRecIndexForTableView> data_tblAnimalRecIndexTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> recIdCol;              //	接收单号	RecID	varchar(20)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> recDateCol;              //	接收日期	RecDate	datetime		
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalTypeCol;         //	动物种类	AnimalType	varchar(20)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalStrainCol;       //	动物品系	AnimalStrain	varchar(20)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalLevelCol;        //	动物级别	AnimalLevel	varchar(20)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> producerCol;           //	实验动物生产商	Producer	varchar(200)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> certificateCol;        //	动物合格证号	Certificate	varchar(50)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> roomCol;               //	安置房间号	Room	varchar(50)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> numMaleCol;               //	数量雄	NumMale	integer			
	@FXML
	private TableColumn<TblQRRequestForTableView, String> numFemaleCol;             //	数量雌	NumFemale	integer			
	@FXML
	private TableColumn<TblQRRequestForTableView, String> ageMaleULCol;       //	年龄雄范围
	@FXML
	private TableColumn<TblQRRequestForTableView, String> ageFemaleULCol;     //	年龄雌范围	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> receiverCol;           //	接收人	Receiver	varchar(20)	
	@FXML
	private TableColumn<TblQRRequestForTableView, String> checkerCol;            //	复核者	Checker	varchar(20)	
	
	//状态栏
	@FXML
	private Label startDateLabel;
	@FXML
	private Label endDateLabel;
	@FXML
	private Label animalTypeLabel;
	@FXML
	private Label animalStrainLabel;
	@FXML
	private Label totalLabel;
	
	private String startDateStr="";
	private String endDateStr="";
	private String animalTypeStr="";
	private String animalStrainStr="";
	private String totalStr="";
	private  static ReceivePaneController instance;
	
	public static ReceivePaneController getInstance(){
		return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化日期选择器
		initDate();
		//初始化动物种类与动物品系下拉框
		initAnimalTypeAndStrainComboBox();
		//初始化表格
		initTable();
	}
	/**
	 * 初始化日期选择器
	 */
	private void initDate(){
		startDatePane= new DatePicker(Locale.CHINA);
		startDatePane.getTextField().setEditable(false);
		startDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		startDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		startDatePane.getCalendarView().setShowWeeks(false);
		startDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		startDatePane.setMaxWidth(110);
		
		startDateHBox.getChildren().add(startDatePane);
		startDatePane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					// 更新       的值
					startDateStr=newValue;
				}
			}

		});
		startDateStr= DateUtil.getDateAgo(6);
		startDatePane.getTextField().setText(startDateStr);
		
		endDatePane= new DatePicker(Locale.CHINA);
		endDatePane.getTextField().setEditable(false);
		endDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		endDatePane.getCalendarView().setShowWeeks(false);
		endDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endDatePane.setMaxWidth(110);
		
		endDateHBox.getChildren().add(endDatePane);
		endDatePane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					// 更新       的值
					endDateStr=newValue;
				}
			}
			
		});
		endDateStr= DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		endDatePane.getTextField().setText(endDateStr);
	}
	
	/**初始化动物种类与动物品系下拉框*/
	private void initAnimalTypeAndStrainComboBox(){
		animalTypeComboBox.setItems(data_animalTypeComboBox);
		animalStrainComboBox.setItems(data_animalStrainComboBox);
		animalTypeComboBox.getSelectionModel().selectedItemProperty()//
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				animalStrainComboBox.getSelectionModel().clearSelection();
				data_animalStrainComboBox.clear();
				if(null!=newValue && !"".equals(newValue)){
					String id= animalTypeNameIdMap.get(newValue);
					if(null!=id && !"".equals(id)){
						animalTypeStr=newValue;
						List<DictAnimalStrain> dictAnimalStrainList = 
								BaseService.getDictAnimalStrainService().findByTypeId(id);
						if(null != dictAnimalStrainList && dictAnimalStrainList.size()>0){
							for(DictAnimalStrain obj:dictAnimalStrainList){
								data_animalStrainComboBox.add(obj.getStrainName());
							}
						}
					}
				}else{
					animalTypeStr="";
				}
				
			}
			
		});
		animalStrainComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					animalStrainStr=newValue;
				}else{
					animalStrainStr="";
				}
				
			}
			
		});
		
		List<DictAnimalType> dictAnimalTypeList = 
				BaseService.getDictAnimalTypeService().findAllOrderByOrderNo();
		if(null != dictAnimalTypeList && dictAnimalTypeList.size()>0){
			data_animalTypeComboBox.add("");
			for(DictAnimalType obj:dictAnimalTypeList){
				data_animalTypeComboBox.add(obj.getTypeName());
				animalTypeNameIdMap.put(obj.getTypeName(), obj.getId());
			}
		}
				
	}
	/**初始化表格*/
	private void initTable() {
		recIdCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("recId")); 
		recDateCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("recDate")); 
		animalTypeCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalType")); 
		animalStrainCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalStrain")); 
		animalLevelCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalLevel")); 
		numMaleCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("numMale")); 
		numFemaleCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("numFemale")); 
		ageMaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("ageMaleUL")); 
		ageFemaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("ageFemaleUL")); 
		producerCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("producer")); 
		certificateCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("certificate")); 
		roomCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("room")); 
		receiverCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("receiver")); 
		checkerCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("checker")); 
		tblAnimalRecIndexTable.setItems(data_tblAnimalRecIndexTable);
	}
	/**更新表格数据*/
	private void updateTableData(String startDateStr, String endDateStr, 
			String animalTypeStr, String animalStrainStr) {
		data_tblAnimalRecIndexTable.clear();
		String ageMaleUL ="";
		String ageFemaleUL="";
		String receiver ="";
		String checker="";
		List<TblAnimalRecIndex> tblAnimalRecIndexlist = BaseService.getTblAnimalRecIndexService()//
				.findByDateAnimal(startDateStr,endDateStr,animalTypeStr,animalStrainStr);
		if(null!=tblAnimalRecIndexlist && tblAnimalRecIndexlist.size()>0){
			//总数，状态栏用
			totalStr=tblAnimalRecIndexlist.size()+"";
			List<String> esIdList = new ArrayList<String>();
			String signId="";
			String checkId="";
			for(TblAnimalRecIndex obj:tblAnimalRecIndexlist){
				signId=obj.getSignId();
				if(null!=signId  && !"".equals(signId)){
					esIdList.add(signId);
					checkId=obj.getCheckId();
					if(null!=checkId && !"".equals(checkId)){
						esIdList.add(checkId);
					}
				}
			}
			String[] ids= new String[esIdList.size()];
					 esIdList.toArray(ids);
			List<TblES> tblESList = BaseService.getTblESService().getByIds(ids);
			Map<String,String> esIdSignerMap = new HashMap<String,String>();
			if(null!=tblESList && tblESList.size()>0){
				for(TblES tblES:tblESList){
					esIdSignerMap.put(tblES.getEsId(), tblES.getSigner());
				}
			}
			for(TblAnimalRecIndex obj:tblAnimalRecIndexlist){
				ageMaleUL=floatToStr(obj.getAgeMaleU())+"-"+floatToStr(obj.getAgeMaleL())+obj.getAgeUnit();
				ageFemaleUL=floatToStr(obj.getAgeFemaleU())+"-"+floatToStr(obj.getAgeFemaleL())+obj.getAgeUnit();
				receiver = obj.getSignId();
				if(null!=receiver && !"".equals(receiver)){
					receiver =esIdSignerMap.get(receiver);
					checker =obj.getCheckId();
					if(null!=checker && !"".equals(checker)){
						checker =esIdSignerMap.get(checker);
					}else{
						checker ="";
					}
				}else{
					receiver ="";
					checker ="";
				}
//				 String recId,              //	接收单号	RecID	varchar(20)	
//				 Date recDate,              //	接收日期	RecDate	datetime		
//				 String animalType,         //	动物种类	AnimalType	varchar(20)	
//				 String animalStrain,       //	动物品系	AnimalStrain	varchar(20)	
//				 String animalLevel,        //	动物级别	AnimalLevel	varchar(20)	
//				 String producer,           //	实验动物生产商	Producer	varchar(200)	
//				 String certificate,        //	动物合格证号	Certificate	varchar(50)	
//				 String room,               //	安置房间号	Room	varchar(50)	
//				 int numMale,               //	数量雄	NumMale	integer			
//				 int numFemale,             //	数量雌	NumFemale	integer			
//				 String ageMaleUL,       //	年龄雄范围
//				 String ageFemaleUL,     //	年龄雌范围	
//				 String receiver,           //	接收人	Receiver	varchar(20)	
//				 String checker 
				data_tblAnimalRecIndexTable.add(new TblAnimalRecIndexForTableView(obj.getRecId(),obj.getRecDate(),
						obj.getAnimalType(),obj.getAnimalStrain(),obj.getAnimalLevel(),obj.getProducer(),
						obj.getCertificate(),obj.getRoom(),obj.getNumMale(),obj.getNumFemale(),ageMaleUL,ageFemaleUL,
						receiver,checker));
			}
		}else{
			totalStr="0";
		}
		//更新状态栏
		updateState();
	}
	//float  转成  String
	private String floatToStr(float obj){
		int a= (int) obj;
		if(a == obj){
			return a+"";
		}
		return obj+"";
	}
	
	/**更新状态栏*/
	private void updateState() {
		startDateLabel.setText(startDateStr);
		endDateLabel.setText(endDateStr);
		totalLabel.setText(totalStr);
		animalTypeLabel.setText(animalTypeStr);
		animalStrainLabel.setText(animalStrainStr);
	}
	/**
	 * 接收登记      button   点击事件
  	 * */
	@FXML
	private void onReceiveButton(ActionEvent event ){
		Stage stage = new Stage();
		stage.initOwner(Main.getInstance().getStage());
		ReceiveRegisterFrame receiveRegisterFrame = new ReceiveRegisterFrame();
		try {
			receiveRegisterFrame.start(stage);
			ReceiveRegisterFrame.getInstance().updateAnimalTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 编辑      button   点击事件
	 * */
	@FXML
	private void onEditButton(ActionEvent event ){
		TblAnimalRecIndexForTableView tblAnimalRecIndexForTableView = tblAnimalRecIndexTable.getSelectionModel().getSelectedItem();
		if(null == tblAnimalRecIndexForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		Stage stage = new Stage();
		stage.initOwner(Main.getInstance().getStage());
		ReceiveRegisterFrame receiveRegisterFrame = new ReceiveRegisterFrame();
		try {
			receiveRegisterFrame.start(stage);
			ReceiveRegisterFrame.setRecId(tblAnimalRecIndexForTableView.getRecId());
			ReceiveRegisterFrame.setReceiver(tblAnimalRecIndexForTableView.getReceiver());
			ReceiveRegisterFrame.setChecker(tblAnimalRecIndexForTableView.getChecker());
			ReceiveRegisterFrame.getInstance().updateAnimalTable();
			ReceiveRegisterFrame.getInstance().updateOtherData(tblAnimalRecIndexForTableView.getRecId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *动物登记  button 点击事件
	 * @param event
	 */
	@FXML
	private void onAnimalRegisterButton(ActionEvent event ){
		TblAnimalRecIndexForTableView tblAnimalRecIndexForTableView = tblAnimalRecIndexTable.getSelectionModel().getSelectedItem();
		if(null == tblAnimalRecIndexForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		Stage stage = new Stage();
		stage.initOwner(Main.getInstance().getStage());
		AnimalRegisterFrame animalRegisterFrame = new AnimalRegisterFrame();
		try {
			animalRegisterFrame.start(stage);
			AnimalRegisterFrame.setRecId(tblAnimalRecIndexForTableView.getRecId());
			AnimalRegisterFrame.setReceiver(tblAnimalRecIndexForTableView.getReceiver());
			AnimalRegisterFrame.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *打印接收登记登记  button 点击事件
	 * @param event
	 */
	@FXML
	private void onPrintReceiveRegisterButton(ActionEvent event ){
		TblAnimalRecIndexForTableView tblAnimalRecIndexForTableView = tblAnimalRecIndexTable.getSelectionModel().getSelectedItem();
		if(null == tblAnimalRecIndexForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		String recId =tblAnimalRecIndexForTableView.getRecId();
		if(null==recId ||recId.equals("")){
			Alert2.create("请先保存接收信息");
			return ;
		}
        //动物Id号  
 		String animalIdListStr ="";
 		List<TblAnimalRecList> tblAnimalRecLists =BaseService.getTblAnimalRecListService().getListByRecId(recId);
 		if(null!=tblAnimalRecLists && tblAnimalRecLists.size()>0 ){
 			List<String> animalIdList = new ArrayList<String>();
 			for(TblAnimalRecList obj:tblAnimalRecLists){
 				if(null!=obj.getAnimalId() && !"".equals(obj.getAnimalId())){
 					animalIdList.add(obj.getAnimalId());
 				}
 			}
 			animalIdListStr = ListToString(animalIdList);
 		}
 		JasperReport jr = null;
 		JasperPrint jp = null;
        try {
//        	JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("tblAnimalRecIndex.jrxml"));  
//			//页头
//			JRDesignBand pageHeaderBand= (JRDesignBand) design.getPageHeader();
//			JRTextField textField = (JRTextField) pageHeaderBand.getElementByKey("animalIdList");
//			jr = JasperCompileManager.compileReport(design);   
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblAnimalRecIndex.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return ;
		}
        
        InputStream logoImage =  this.getClass().getResourceAsStream("../../../../image/logo_xishan.jpg");
        
        TblAnimalRecIndex tblAnimalRecIndex = BaseService.getTblAnimalRecIndexService().getById(recId);
        if(tblAnimalRecIndex!=null){
        	String producer =tblAnimalRecIndex.getProducer();//            
        	String certificate =tblAnimalRecIndex.getCertificate();//         
        	String animalLevel =tblAnimalRecIndex.getAnimalLevel();//         
        	String animalType =tblAnimalRecIndex.getAnimalType();//          
        	String animalStrain=tblAnimalRecIndex.getAnimalStrain();//         
        	String recDate=DateUtil.dateToString(tblAnimalRecIndex.getRecDate(), "yyyy-MM-dd") ;//           
        	String room =tblAnimalRecIndex.getRoom();//                
        	String numMale =tblAnimalRecIndex.getNumMale()+"";//          
        	String numFemale =tblAnimalRecIndex.getNumFemale()+"";//        
        	String reqDeviation =""+tblAnimalRecIndex.getReqDeviation();//              
        	String packDamaged  =""+tblAnimalRecIndex.getPackDamaged();//               
        	String abnormalInfo =""+tblAnimalRecIndex.getAbnormalInfo();//              
        	String cerOtherStr  =tblAnimalRecIndex.getCerOther();
        	String remark =""+tblAnimalRecIndex.getRemark();//    
        	
        	 Map<String,Object> map = new HashMap<String,Object>();
        	 /**编号*/
          	String number =BaseService.getDictOutputSettingsService().getShowByLabel("试验动物接收登记表-编号");
          	map.put("number",number);
             map.put("logoImage",logoImage);
             map.put("producer",producer);
             map.put("certificate",certificate);
             map.put("animalLevel",animalLevel);
             map.put("animalType",animalType);
             map.put("animalStrain",animalStrain);
             map.put("recDate",recDate);
             map.put("room",room);
             map.put("numMale",numMale);
             map.put("numFemale",numFemale);
             map.put("reqDeviation",reqDeviation);
             map.put("packDamaged",packDamaged);
             map.put("abnormalInfo",abnormalInfo);
             map.put("cerOtherStr",cerOtherStr);
             map.put("remark",remark);
             
             int hasAirConditioner =tblAnimalRecIndex.getHasAirConditioner();
             InputStream hasAirConditionerA = null;
             InputStream hasAirConditionerB = null;
            if(hasAirConditioner == 1){//
            	hasAirConditionerA = this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
            	hasAirConditionerB = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
             }else{
            	 hasAirConditionerA = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
            	 hasAirConditionerB = this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
             }
            map.put("hasAirConditionerA",hasAirConditionerA);
            map.put("hasAirConditionerB",hasAirConditionerB);
            InputStream thimerosalA =null;
            InputStream thimerosalB =null;
            String thimerosal   =tblAnimalRecIndex.getThimerosal();
            if(null == thimerosal || thimerosal.equals("")){
            	thimerosalA = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
            	thimerosalB = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
            }else if(thimerosal.equals("84消毒液")){
            	thimerosalA = this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
            	thimerosalB = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
            }else if(thimerosal.equals("百毒杀")){
            	thimerosalA = this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
            	thimerosalB = this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
            }
            map.put("thimerosalA",thimerosalA);
            map.put("thimerosalB",thimerosalB);
            
            InputStream cerHealthRpt =null;
            InputStream cerBackInfo =null;
            InputStream cerReceipt =null;
            InputStream cerOther =null;
     		String cerHealthRptStr=tblAnimalRecIndex.getCerHealthRpt();  
     		if("0".equals(cerHealthRptStr)){
     			cerHealthRpt =this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
     		}else if("1".equals(cerHealthRptStr)){
     			cerHealthRpt =this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
     		}
     		map.put("cerHealthRpt",cerHealthRpt);
     		
     		String cerBackInfoStr=tblAnimalRecIndex.getCerBackInfo();// in  
     		if("0".equals(cerBackInfoStr)){
     			cerBackInfo =this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
     		}else if("1".equals(cerBackInfoStr)){
     			cerBackInfo =this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
     		}
     		map.put("cerBackInfo",cerBackInfo);
     		
     		String cerReceiptStr=tblAnimalRecIndex.getCerReceipt();//   in  
     		if("0".equals(cerReceiptStr)){
     			cerReceipt =this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
     		}else if("1".equals(cerReceiptStr)){
     			cerReceipt =this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
     		}
     		map.put("cerReceipt",cerReceipt);
     		
     		if(null == cerOtherStr ||"".equals(cerOtherStr)){
     			cerOther =this.getClass().getResourceAsStream("../../../../image/checkbox_no.jpg");
     		}else{
     			cerOther =this.getClass().getResourceAsStream("../../../../image/checkbox_yes.jpg");
     		}
     		map.put("cerOther",cerOther);
     			
     		String receiverDate ="";
     		String signId=tblAnimalRecIndex.getSignId(); 
     		if(null!=signId && !"".equals(signId)){
     			TblES tblES = BaseService.getTblESService().getById(signId);
     			if(null !=tblES){
     				receiverDate =tblES.getSigner()+"/"+recDate;	
     			}
     		}
     		map.put("receiverDate",receiverDate);
     		String checkerDate="";
     		String checkId=tblAnimalRecIndex.getCheckId();
     		if(null!=checkId && !"".equals(checkId)){
     			TblES tblES = BaseService.getTblESService().getById(checkId);
     			if(null !=tblES){
     				checkerDate =tblES.getSigner()+"/"
     			+DateUtil.dateToString(tblES.getDateTime(), "yyyy-MM-dd");	
     			}
     		}
     		map.put("checkerDate",checkerDate);
     		
     		map.put("animalIdList",animalIdListStr);
             try {
     			jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(null));
     		} catch (JRException e) {
     			e.printStackTrace();
     			Alert2.create("报表加载失败");
     			return ;
     		}
     		Main.getInstance().openJFrame(jp, "试验动物接收登记表");
        }
	}
	/**
	 *动物Id号List   转成   String  
	 * @param animalIdList
	 * @return 
	 */
	private String ListToString(List<String> animalIdList) {
		String animalIds = "";
		Collections.sort(animalIdList);
		if(null!=animalIdList && animalIdList.size()>0){
			int start =0;
			int end =0;
			int current =0;
			int size = 0;
			for(String obj:animalIdList){
				if(obj.matches("[1-9]{1}[0-9]{0,9}")){
					current =Integer.valueOf(obj);
					if(size == 0){
						start =current ;
						size =1;
						end =0;
					}else if(size == 1){
						if((current - start)==1){
							size=2;
							end = current;
						}else{
							animalIds=animalIds+start+" ,";
							start = current ;
							size=1;
							end =0;
						}
					}else if(size>=2){
						if((current - end)==1){
							size++;
							end = current;
						}else{
							animalIds=animalIds+start+" ~ "+end+" ,";
							start = current ;
							size=1;
							end =0;
						}
					}
				}else{//当前Id号不是数字
					if(size>=2){
						animalIds=animalIds+start+" ~ "+end+" ,";
						animalIds=animalIds+obj+" ,";
					}else if(size==1){
						animalIds=animalIds+start+" ,";
						animalIds=animalIds+obj+" ,";
					}else{
						animalIds=animalIds+obj+" ,";
					}
					start =0;
					end =0 ;
					current =0;
					size=0;
				}
			}
			
			if(size>=2){
				animalIds=animalIds+start+" ~ "+end+" ,";
			}else if(size == 1){
				animalIds=animalIds+start+" ,";
			}
		}
		if(animalIds.endsWith(",")){
			animalIds = animalIds.substring(0, animalIds.length()-1);
		}
		return animalIds;
	}
	/**
	 * 查询  button  点击事件
	 * @param event
	 */
	@FXML
	private void onSearchButton(ActionEvent event){
		if(startDateStr.compareTo(endDateStr)>0){
			Alert2.create("请前项日期不能晚于后项日期");
			return ;
		}
		//更新表格数据
		updateTableData(startDateStr,endDateStr,animalTypeStr,animalStrainStr);
	}
	
	/**
	 * 选中指定行
	 */
	private  void vistedRow(String recId){
		if(data_tblAnimalRecIndexTable.size()>0){
			for(TblAnimalRecIndexForTableView rowData:data_tblAnimalRecIndexTable){
				if(recId !=null && recId.equals(rowData.getRecId())){
					tblAnimalRecIndexTable.getSelectionModel().select(rowData);
					break;
				}
			}
		}
	}
	/**加载数据*/
	public void loadData(){
		//加载表格数据
		updateTableData( startDateStr,  endDateStr, 
				 animalTypeStr,  animalStrainStr);
	}
	/**加载数据*/
	public void loadData(String recId){
		//加载表格数据
		updateTableData( startDateStr,  endDateStr, 
				animalTypeStr,  animalStrainStr);
		//选中该行
		vistedRow(recId);
	}
}
