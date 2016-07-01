package com.lanen.view.quarantine;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.TblAnimalRecIndex;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.studyplan.DictAnimalStrain;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.TblAnimalRecListForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.util.popup.MultiComboBox;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class ReceiveRegisterFrame extends Application implements Initializable {

	private static  String recId ="";              //	接收单号	RecID	
	private static String receiver="";
	private static String checker="";
	
	String producer ="";//            
	String certificate ="";//         
	String animalLevel ="";//         
	String animalType ="";//          
	String animalStrain="";//         
	String recDateStr="";//           
	String room ="";//                
	int hasAirConditioner =0;//       
	String numMaleStr ="";//          
	String numFemaleStr ="";//        
	String ageMaleUStr ="";//         
	String ageMaleLStr ="";//         
	String ageFemaleUStr ="";//       
	String ageFemaleLStr ="";//       
	String ageUnit ="";//             
	
	String cerHealthRpt="";//         
	String cerBackInfo="";//          
	String cerReceipt="";//                   
	String cerOther ="";//                    
	
	String reqDeviation ="";//              
	String packDamaged ="";//               
	String abnormalInfo ="";//              
	String thimerosal ="";//                  
	String remark ="";//                      
	List<String> qrrIdList =null;//存放已选中的申请单号
	 @FXML
	private TextField producerText;           //	实验动物生产商	Producer		
	 @FXML
	private TextField certificateText;        //	动物合格证号	Certificate	
	 @FXML
	private VBox recDateVBox;              //	接收日期	RecDate		
	 private DatePicker recDatePane=null;
	 @FXML
	private ComboBox<String> animalLevelComboBox;        //	动物级别	AnimalLevel
	 @FXML
	private ComboBox<String> animalTypeComboBox;          //	动物种类	AnimalType	
	 private ObservableList<String> data_animalTypeComboBox = FXCollections.observableArrayList();
	 private Map<String,String> animalTypeNameIdMap = new HashMap<String,String>();
	 @FXML
	private ComboBox<String> animalStrainComboBox;       //	动物品系	AnimalStrain
	 private ObservableList<String> data_animalStrainComboBox = FXCollections.observableArrayList();
	 @FXML
	 private HBox roomHBox;
	 private TextField roomText;               //	安置房间号	Room	
	 private MultiComboBox multiComboBox =new MultiComboBox();      
	 @FXML
	private RadioButton hasAirConditionerA;     //	车辆有空调	HasAirConditioner				
	 @FXML
	private RadioButton hasAirConditionerB;     //	车辆无空调	HasAirConditioner				
	 @FXML
	private TextField numMaleText;               //	数量雄	NumMale
	 @FXML
	private TextField numFemaleText;             //	数量雌	NumFemale	
	 @FXML
	private TextField ageMaleUText;       //	年龄雄上	AgeMaleU	
	 @FXML
	private TextField ageMaleLText;       //	年龄雄下	AgeMaleL	
	 @FXML
	private TextField ageFemaleUText;     //	年龄雌上	AgeFemalU	
	 @FXML
	private TextField ageFemaleLText;     //	年龄雌下	AgeFemalL	
	 @FXML
	private ComboBox<String> ageUnitComboBox;            //	年龄单位	AgeUnit	
	 @FXML
	private CheckBox cerHealthRptCheckBox;       //	证明：健康监测报告	CerHealthRpt	
	 @FXML
	private CheckBox cerBackInfoCheckBox;        //	证明：个体背景资料	CerBackInfo	
	 @FXML
	private CheckBox cerReceiptCheckBox;         //	证明：发票	CerReceipt	
	 @FXML
	private CheckBox cerOtherCheckBox;           //	证明：其他	CerOther	
	 @FXML
	private TextField cerOtherText;           //	证明：其他	CerOther	
	 @FXML
	private TextArea reqDeviationText;       //	与需求偏差	ReqDeviation	
	 @FXML
	private TextArea packDamagedText;        //	运输箱是否破损	PackDamaged
	 @FXML
	private TextArea abnormalInfoText;       //	动物异常情况	AbnormalInfo	
	 @FXML
	private ComboBox<String> thimerosalComboBox;         //	消毒液	Thimerosal	
    @FXML
	private TextArea remarkText;             //	备注	Remark		
    
    @FXML
    private Button saveButton;
    @FXML
    private Button signButton;
    @FXML
    private Button checkButton;
    @FXML
    private Button animalRegisterButton;
    @FXML
    private Button printButton;
    
    @FXML
    private HBox editableHBox;
    @FXML
    private HBox editableHBox2;
    @FXML
    private HBox editableHBox3;
    @FXML
    private HBox editableHBox4;
    
    
    private ToggleGroup group1 = new ToggleGroup();
    /**
     * 申请单号列表
     */
	@FXML
	private ListView<CheckBox> qrrIdListView;
	private ObservableList<CheckBox> data_qrrIdListView = FXCollections.observableArrayList();
	
	
	//动物信息面板
	@FXML
	private TableView<TblAnimalRecListForTableView> animalTable;
	private static ObservableList<TblAnimalRecListForTableView> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> snCol ;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> recIdCol;			//	接收单号	RecID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalTypeCol;		//	动物种类	AnimalType	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalStrainCol;	//	动物品系	AnimalStrain	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalIdCol;		//	动物ID号	AnimalID	varchar(30)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> genderCol;				//	性别	Gender	integer			
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> roomCol;			//	申请单号	QRRID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> planStudyNoCol;		//	分配课题编号	PlanStudyNo	varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> stateCol;           //状态   死亡  ，移交，备库
	
	
	private static ReceiveRegisterFrame instance;
	
	public static ReceiveRegisterFrame getInstance(){
		return instance;
	}
	public ReceiveRegisterFrame(){
		ReceiveRegisterFrame.recId="";
		ReceiveRegisterFrame.receiver = "";
		ReceiveRegisterFrame.checker = "";
		
	}
//	public ReceiveRegisterFrame(String recId,String receiver,String checker){
//		ReceiveRegisterFrame.recId=recId;
//		ReceiveRegisterFrame.receiver = receiver;
//		ReceiveRegisterFrame.checker = checker;
//		
//	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance= this;
//		new BaseService();
		//单选按钮置为一组
		groupAllRadio();
		//初始化日期选择器
		initDate();
		//初始化  roomText
		initRommText();
		//初始化动物种类与动物品系下拉框
		initAnimalTypeAndStrainComboBox();
		//初始化动物级别下拉框
		initAnimalLevelComboBox();
		//初始化申请单号ListView
		qrrIdListView.setItems(data_qrrIdListView);
		//初始化表格
		initAnimalTable();
		//初始化不可编辑区域
		initEditableHBox();
	}
	
	/**
	 * 初始化  roomText
	 */
	private void initRommText() {
		roomText = multiComboBox.getTextField();
		roomHBox.getChildren().add(roomText);
	}
	/**
	 *更新房间号列表
	 */
	private void updateRoomList(String animalType){
		roomText.clear();
		ObservableList<CheckBox> data_roomListView = FXCollections.observableArrayList();
		if(null!=animalType &&!"".equals(animalType)){
			List<String> roomList =BaseService.getTblAnimalHouseService().getRoomListByAnimalType(animalType);
			if(roomList!=null && roomList.size()>0){
				CheckBox checkBox = null;
				for(String str:roomList){
					checkBox = new CheckBox(str);
					data_roomListView.add(checkBox);
				}
			}
		}
		multiComboBox.updateData_listView(data_roomListView);
	}
	/**单选按钮置为一组*/
	private void groupAllRadio(){
		hasAirConditionerA.setToggleGroup(group1);
		hasAirConditionerB.setToggleGroup(group1);
	}
	/**
	 * 初始化日期选择器
	 */
	private void initDate(){
		recDatePane= new DatePicker(Locale.CHINA);
		recDatePane.getTextField().setEditable(false);
		recDatePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		recDatePane.getCalendarView().todayButtonTextProperty().set("今天");
		recDatePane.getCalendarView().setShowWeeks(false);
		recDatePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		recDatePane.setMaxWidth(148);
		
		recDateVBox.getChildren().add(recDatePane);
		String recDateStr= DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		recDatePane.getTextField().setText(recDateStr);
	}
	
	/**初始化动物种类与动物品系下拉框*/
	private void initAnimalTypeAndStrainComboBox(){
		
		List<DictAnimalType> dictAnimalTypeList = 
				BaseService.getDictAnimalTypeService().findAllOrderByOrderNo();
		if(null != dictAnimalTypeList && dictAnimalTypeList.size()>0){
			for(DictAnimalType obj:dictAnimalTypeList){
				data_animalTypeComboBox.add(obj.getTypeName());
				animalTypeNameIdMap.put(obj.getTypeName(), obj.getId());
			}
		}
		animalTypeComboBox.setItems(data_animalTypeComboBox);
		animalStrainComboBox.setItems(data_animalStrainComboBox);
		//监听事件
		animalTypeComboBox.getSelectionModel().selectedItemProperty()//
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				animalStrainComboBox.getSelectionModel().clearSelection();
				data_animalStrainComboBox.clear();
				if(null!=newValue && !"".equals(newValue)){
					String id= animalTypeNameIdMap.get(newValue);
					if(null!=id && !"".equals(id)){
						List<DictAnimalStrain> dictAnimalStrainList = 
								BaseService.getDictAnimalStrainService().findByTypeId(id);
						if(null != dictAnimalStrainList && dictAnimalStrainList.size()>0){
							for(DictAnimalStrain obj:dictAnimalStrainList){
								data_animalStrainComboBox.add(obj.getStrainName());
							}
						}
					}
				}
				//更新roomListView
				updateRoomList(newValue);
			}
		});
		//监听事件
		animalStrainComboBox.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && !"".equals(newValue)){
					//更新课题编号列表的值
					updateQRRIdListView();
				}else{
					//申请单号列表清空
					data_qrrIdListView.clear();
				}
				
			}
			
		});
		
	}

	/** 初始化动物级别下拉框 */
	private void initAnimalLevelComboBox() {
		// 监听事件
		animalLevelComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1,
							String newValue) {
						if (null != newValue && !"".equals(newValue)) {
							//更新申请单号列表的值
							updateQRRIdListView();
						} else {
							// 申请单号列表清空
							data_qrrIdListView.clear();
						}
					}

				});
	}
	/**更新申请单号列表的值*/
	private void updateQRRIdListView() {
		// 申请单号列表清空
		data_qrrIdListView.clear();
		
		String animalTypeStr = animalTypeComboBox.getSelectionModel().getSelectedItem();
		String animalStrainStr = animalStrainComboBox.getSelectionModel().getSelectedItem();
		String animalLevelStr = animalLevelComboBox.getSelectionModel().getSelectedItem();
		if(null == animalTypeStr || "".equals(animalTypeStr) ||null == animalStrainStr 
				|| "".equals(animalStrainStr) ||null == animalLevelStr || "".equals(animalLevelStr) ){
			// 课题编号列表清空
			data_qrrIdListView.clear();
		}else{
			@SuppressWarnings("rawtypes")
			List list = BaseService.getTblQRRequestService()
					.findQRRIdRecIdByAnimalTypeStrainLevel(animalTypeStr,animalStrainStr,animalLevelStr);
			if(null!=list &&list.size()>0){
				CheckBox checkBox = null;
				String qrRId=	"";
				String recId=null;
				qrrIdList = new ArrayList<String>();//初始化已选中的申请单号list
				for(int i = 0; i < list.size();i++){ 
					Object[] object = (Object[])list.get(i);
						qrRId=	(String)object[0];
						recId=(String)object[1];
						if(null==recId || recId.equals("") ||recId.equals(ReceiveRegisterFrame.recId)){
							checkBox = new CheckBox(qrRId);
							if(null!=recId && !recId.equals("")&&recId.equals(ReceiveRegisterFrame.recId)){
								checkBox.setSelected(true);
								qrrIdList.add(qrRId);
							}
							data_qrrIdListView.add(checkBox);
						}
				}
			}
		}
		
	}
	/***
	 * 其他  CheckBox   点击事件 
	 * @param event
	 */
	@FXML
	private void onRecOtherCheckBox(ActionEvent event){
		CheckBox checkBox =(CheckBox) event.getSource();
		if(checkBox.isSelected()){
			cerOtherText.setDisable(false);
			cerOtherText.requestFocus();
		}else{
			cerOtherText.setText("");
			cerOtherText.setDisable(true);
			
		}
	}
	/**
	 * 初始化表格
	 */
	private void initAnimalTable() {
//		 snCol ;
//		 recIdCol;			//	接收单号	RecID	varchar(20)	
//		 animalTypeCol;		//	动物种类	AnimalType	varchar(20)	
//		 animalStrainCol;	//	动物品系	AnimalStrain	varchar(20)	
//		 animalIdCol;		//	动物ID号	AnimalID	varchar(30)	
//		 genderCol;				//	性别	Gender	integer			
//		 qrRIDCol;			//	申请单号	QRRID	varchar(20)	
//		 planStudyNoCol;		//	分配课题编号	PlanStudyNo	varchar(20)
//		 stateCol;     
		snCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("sn")); 
		recIdCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("recId")); 
		animalTypeCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalType")); 
		animalStrainCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalStrain")); 
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalId")); 
		genderCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("gender")); 
		roomCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("room")); 
		planStudyNoCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("planStudyNo")); 
		stateCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("state")); 
		animalTable.setItems(data_animalTable);
	}
	/***
	 * 根据接收单号，更新表格数据
	 */
	public void updateAnimalTable(){
		data_animalTable.clear();
		if(null==recId ||"".equals(recId)){
			return ;
		}
		List<TblAnimalRecList> tblAnimalRecLists =BaseService.getTblAnimalRecListService().getListByRecId(recId);
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
				data_animalTable.add(new TblAnimalRecListForTableView(
						obj.getId(),obj.getSn(),obj.getRecId(),obj.getAnimalType(),obj.getAnimalStrain(),
						obj.getAnimalId(),obj.getGender(),obj.getRoom(),obj.getPlanStudyNo(),state,obj.getAnimalLevel()
						));
			}
		}
	}
	/**
	 * 填充其他控件的值(表格除外的其他)
	 */
	public  void updateOtherData(String recId){
		if(null==recId ||"".equals(recId)){
			System.out.println("空");
			return ;
		}
		//初始化不可编辑区域
		initEditableHBox();
		TblAnimalRecIndex tblAnimalRecIndex =BaseService.getTblAnimalRecIndexService().getById(recId);
		if(null != tblAnimalRecIndex){
			this.producer = tblAnimalRecIndex.getProducer();
			producerText.setText(producer);
			this.certificate =tblAnimalRecIndex.getCertificate();
			certificateText.setText(certificate);
			this.animalLevel =	tblAnimalRecIndex.getAnimalLevel();
			animalLevelComboBox.getSelectionModel().select(animalLevel);
			this.animalType =	tblAnimalRecIndex.getAnimalType();
			animalTypeComboBox.getSelectionModel().select(animalType);
			this.animalStrain=	tblAnimalRecIndex.getAnimalStrain();
			animalStrainComboBox.getSelectionModel().select(animalStrain);
			Date recDate =tblAnimalRecIndex.getRecDate();
			this.recDateStr=recDate == null ?"":DateUtil.dateToString(recDate, "yyyy-MM-dd");
			recDatePane.getTextField().setText(recDateStr);
			this.room = tblAnimalRecIndex.getRoom();
			roomText.setText(room);
			this.hasAirConditioner = tblAnimalRecIndex.getHasAirConditioner();
			if(hasAirConditioner == 1){
				hasAirConditionerA.setSelected(true);
			}else if(hasAirConditioner == 0){
				hasAirConditionerB.setSelected(true);
			}
			this.numMaleStr =tblAnimalRecIndex.getNumMale()+"";
			numMaleText.setText(numMaleStr);
			this.numFemaleStr =tblAnimalRecIndex.getNumFemale()+"";
			numFemaleText.setText(numFemaleStr);
			this.ageMaleUStr = floatToStr(tblAnimalRecIndex.getAgeMaleU());
			ageMaleUText.setText(ageMaleUStr);
			this.ageMaleLStr = floatToStr(tblAnimalRecIndex.getAgeMaleL());
			ageMaleLText.setText(ageMaleLStr);
			this.ageFemaleUStr =floatToStr(tblAnimalRecIndex.getAgeFemaleU());
			ageFemaleUText.setText(ageFemaleUStr);
			this.ageFemaleLStr = floatToStr(tblAnimalRecIndex.getAgeFemaleL());
			ageFemaleLText.setText(ageFemaleLStr);
			this.ageUnit =tblAnimalRecIndex.getAgeUnit();
			ageUnitComboBox.getSelectionModel().select(ageUnit);
			this.cerHealthRpt=tblAnimalRecIndex.getCerHealthRpt();
			if(cerHealthRpt.equals("1")){
				cerHealthRptCheckBox.setSelected(true);
			}else{
				cerHealthRptCheckBox.setSelected(false);
			}
			this.cerBackInfo=tblAnimalRecIndex.getCerBackInfo();
			if(cerBackInfo.equals("1")){
				cerBackInfoCheckBox.setSelected(true);
			}else{
				cerBackInfoCheckBox.setSelected(false);
			}
			this.cerReceipt=tblAnimalRecIndex.getCerReceipt();
			if(cerReceipt.equals("1")){
				cerReceiptCheckBox.setSelected(true);
			}else{
				cerReceiptCheckBox.setSelected(false);
			}
			this.cerOther =tblAnimalRecIndex.getCerOther();
			if(!cerOther.equals("")){
				cerOtherCheckBox.setSelected(true);
				cerOtherText.setDisable(false);
				cerOtherText.setText(cerOther);
			}else{
				cerOtherCheckBox.setSelected(false);
				cerOtherText.setDisable(true);
			}
			this.reqDeviation = tblAnimalRecIndex.getReqDeviation();
			reqDeviationText.setText(reqDeviation);
			this.packDamaged =tblAnimalRecIndex.getPackDamaged();
			packDamagedText.setText(packDamaged);
			this.abnormalInfo = tblAnimalRecIndex.getAbnormalInfo();
			abnormalInfoText.setText(abnormalInfo);
			this.thimerosal =tblAnimalRecIndex.getThimerosal();
			if(null!=thimerosal){
				thimerosalComboBox.getSelectionModel().select(thimerosal);
			}
			this.remark = tblAnimalRecIndex.getRemark();
			remarkText.setText(remark);
		}
	}
	/**
	 * 保存  button 事件
	 * @param event
	 */
	@FXML
	private void onSaveButton(ActionEvent event){
		if(checkAllData()){
			if("".equals(recId)){//新建保存
				 producer = producerText.getText().toString().trim();
				 certificate = certificateText.getText().toString().trim();
				 animalLevel =	animalLevelComboBox.getSelectionModel().getSelectedItem().trim();
				 animalType =	animalTypeComboBox.getSelectionModel().getSelectedItem().trim();
				 animalStrain=	animalStrainComboBox.getSelectionModel().getSelectedItem().trim();
				this.recDateStr=recDatePane.getTextField().getText().toString().trim();
				Date recDate = DateUtil.stringToDate(recDateStr,"yyyy-MM-dd");
				room = roomText.getText().toString().trim();
				 hasAirConditioner = 0;
				if(hasAirConditionerA.isSelected()){
					hasAirConditioner = 1;
				}
				numMaleStr = numMaleText.getText().toString().trim();
				numFemaleStr = numFemaleText.getText().toString().trim();
				int numMale = Integer.valueOf(numMaleStr);
				int numFemale = Integer.valueOf(numFemaleStr);
				ageMaleUStr = ageMaleUText.getText().toString().trim();
				ageMaleLStr = ageMaleLText.getText().toString().trim();
				ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
				ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
				float ageMaleU = Float.valueOf(ageMaleUStr);
				float ageMaleL = Float.valueOf(ageMaleLStr);
				float ageFemaleU = Float.valueOf(ageFemaleUStr);
				float ageFemaleL = Float.valueOf(ageFemaleLStr);
				ageUnit = ageUnitComboBox.getSelectionModel().getSelectedItem();
				
				cerHealthRpt="0";
				if(cerHealthRptCheckBox.isSelected()){
					cerHealthRpt="1";
				}
				cerBackInfo="0";
				if(cerBackInfoCheckBox.isSelected()){
					cerBackInfo="1";
				}
				cerReceipt="0";
				if(cerReceiptCheckBox.isSelected()){
					cerReceipt="1";
				}
				cerOther =cerOtherText.getText().trim();
				
				reqDeviation = reqDeviationText.getText().toString().trim();
				packDamaged = packDamagedText.getText().toString().trim();
				abnormalInfo = abnormalInfoText.getText().toString().trim();
				thimerosal ="";
				if(null!=thimerosalComboBox.getSelectionModel()){
					thimerosal=thimerosalComboBox.getSelectionModel().getSelectedItem();
				}
				remark = remarkText.getText().toString().trim();
				TblAnimalRecIndex tblAnimalRecIndex = new TblAnimalRecIndex();
				tblAnimalRecIndex.setRecDate(recDate);
				tblAnimalRecIndex.setAnimalLevel(animalLevel);
				tblAnimalRecIndex.setAnimalType(animalType);
				tblAnimalRecIndex.setAnimalStrain(animalStrain);
				tblAnimalRecIndex.setNumMale(numMale);
				tblAnimalRecIndex.setNumFemale(numFemale);
				tblAnimalRecIndex.setAgeMaleU(ageMaleU);
				tblAnimalRecIndex.setAgeMaleL(ageMaleL);
				tblAnimalRecIndex.setAgeFemaleU(ageFemaleU);
				tblAnimalRecIndex.setAgeFemaleL(ageFemaleL);
				tblAnimalRecIndex.setAgeUnit(ageUnit);
				tblAnimalRecIndex.setProducer(producer);
				tblAnimalRecIndex.setCertificate(certificate);
				tblAnimalRecIndex.setRoom(room);
				tblAnimalRecIndex.setHasAirConditioner(hasAirConditioner);
				tblAnimalRecIndex.setReqDeviation(reqDeviation);
				tblAnimalRecIndex.setPackDamaged(packDamaged);
				tblAnimalRecIndex.setAbnormalInfo(abnormalInfo);
				tblAnimalRecIndex.setThimerosal(thimerosal);
				tblAnimalRecIndex.setCerHealthRpt(cerHealthRpt);
				tblAnimalRecIndex.setCerBackInfo(cerBackInfo);
				tblAnimalRecIndex.setCerReceipt(cerReceipt);
				tblAnimalRecIndex.setCerOther(cerOther);
				tblAnimalRecIndex.setRemark(remark);
				
				qrrIdList = new ArrayList<String>();
				if(data_qrrIdListView.size()>0){
					for(CheckBox checkBox :data_qrrIdListView){
						if(checkBox.isSelected()){
							qrrIdList.add(checkBox.getText().trim());
						}
					}
				}
				recId = BaseService.getTblAnimalRecIndexService().saveAndUpdateTblQRRequestStudyNo
						(tblAnimalRecIndex,qrrIdList);
				//更新动物列表数据
				updateAnimalTable();
				ReceivePaneController.getInstance().loadData(recId);
				Alert.create("保存成功");
			}else{//编辑修改
				//是否改变动物种类，品系，级别，数量，安置房间号码，申请单号list
				boolean isChangeCore = false;
				if("".equals(receiver)){//未签字前 编辑保存
					
					 producer = producerText.getText().toString().trim();
					 certificate = certificateText.getText().toString().trim();
					String animalLevel =	animalLevelComboBox.getSelectionModel().getSelectedItem().trim();
					if(!this.animalLevel.equals(animalLevel)){
						isChangeCore =true;
						this.animalLevel=animalLevel;
					}
					String animalType =	animalTypeComboBox.getSelectionModel().getSelectedItem().trim();
					 if(!this.animalType.equals(animalType)){
							isChangeCore =true;
							this.animalType=animalType;
						}
					String animalStrain=	animalStrainComboBox.getSelectionModel().getSelectedItem().trim();
					 if(!this.animalStrain.equals(animalStrain)){
							isChangeCore =true;
							this.animalStrain=animalStrain;
						}
					this.recDateStr=recDatePane.getTextField().getText().toString().trim();
					Date recDate = DateUtil.stringToDate(recDateStr,"yyyy-MM-dd");
					String room = roomText.getText().toString().trim();
					if(!this.room.equals(room)){
							isChangeCore =true;
							this.room=room;
					}
					 hasAirConditioner = 0;
					if(hasAirConditionerA.isSelected()){
						hasAirConditioner = 1;
					}
					String numMaleStr = numMaleText.getText().toString().trim();
					 if(!this.numMaleStr.equals(numMaleStr)){
							isChangeCore =true;
							this.numMaleStr=numMaleStr;
						}
					String numFemaleStr = numFemaleText.getText().toString().trim();
					 if(!this.numFemaleStr.equals(numFemaleStr)){
							isChangeCore =true;
							this.numFemaleStr=numFemaleStr;
					}
					int numMale = Integer.valueOf(numMaleStr);
					int numFemale = Integer.valueOf(numFemaleStr);
					ageMaleUStr = ageMaleUText.getText().toString().trim();
					ageMaleLStr = ageMaleLText.getText().toString().trim();
					ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
					ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
					float ageMaleU = Float.valueOf(ageMaleUStr);
					float ageMaleL = Float.valueOf(ageMaleLStr);
					float ageFemaleU = Float.valueOf(ageFemaleUStr);
					float ageFemaleL = Float.valueOf(ageFemaleLStr);
					ageUnit = ageUnitComboBox.getSelectionModel().getSelectedItem();
					
					cerHealthRpt="0";
					if(cerHealthRptCheckBox.isSelected()){
						cerHealthRpt="1";
					}
					cerBackInfo="0";
					if(cerBackInfoCheckBox.isSelected()){
						cerBackInfo="1";
					}
					cerReceipt="0";
					if(cerReceiptCheckBox.isSelected()){
						cerReceipt="1";
					}
					cerOther =cerOtherText.getText().trim();
					
					reqDeviation = reqDeviationText.getText().toString().trim();
					packDamaged = packDamagedText.getText().toString().trim();
					abnormalInfo = abnormalInfoText.getText().toString().trim();
					thimerosal ="";
					if(null !=thimerosalComboBox.getSelectionModel()){
						thimerosal=thimerosalComboBox.getSelectionModel().getSelectedItem();
					}
					remark = remarkText.getText().toString().trim();
					//
					List<String> qrrIdList = new ArrayList<String>();
					if(data_qrrIdListView.size()>0){
						for(CheckBox checkBox :data_qrrIdListView){
							if(checkBox.isSelected()){
								qrrIdList.add(checkBox.getText().trim());
							}
						}
					}
					if(!this.qrrIdList.equals(qrrIdList)){
						isChangeCore =true;
						this.qrrIdList=qrrIdList;
					}
					if(isChangeCore){//动物列表和申请单号
						TblAnimalRecIndex tblAnimalRecIndex = 
								BaseService.getTblAnimalRecIndexService().getById(recId);
						tblAnimalRecIndex.setRecDate(recDate);
						tblAnimalRecIndex.setAnimalLevel(animalLevel);
						tblAnimalRecIndex.setAnimalType(animalType);
						tblAnimalRecIndex.setAnimalStrain(animalStrain);
						tblAnimalRecIndex.setNumMale(numMale);
						tblAnimalRecIndex.setNumFemale(numFemale);
						tblAnimalRecIndex.setAgeMaleU(ageMaleU);
						tblAnimalRecIndex.setAgeMaleL(ageMaleL);
						tblAnimalRecIndex.setAgeFemaleU(ageFemaleU);
						tblAnimalRecIndex.setAgeFemaleL(ageFemaleL);
						tblAnimalRecIndex.setAgeUnit(ageUnit);
						tblAnimalRecIndex.setProducer(producer);
						tblAnimalRecIndex.setCertificate(certificate);
						tblAnimalRecIndex.setRoom(room);
						tblAnimalRecIndex.setHasAirConditioner(hasAirConditioner);
						tblAnimalRecIndex.setReqDeviation(reqDeviation);
						tblAnimalRecIndex.setPackDamaged(packDamaged);
						tblAnimalRecIndex.setAbnormalInfo(abnormalInfo);
						tblAnimalRecIndex.setThimerosal(thimerosal);
						tblAnimalRecIndex.setCerHealthRpt(cerHealthRpt);
						tblAnimalRecIndex.setCerBackInfo(cerBackInfo);
						tblAnimalRecIndex.setCerReceipt(cerReceipt);
						tblAnimalRecIndex.setCerOther(cerOther);
						tblAnimalRecIndex.setRemark(remark);
						BaseService.getTblAnimalRecIndexService().UpdateOneselfAndTblQRRequestStudyNo
								(tblAnimalRecIndex,qrrIdList);
						//更新动物列表数据
						updateAnimalTable();
						ReceivePaneController.getInstance().loadData(recId);
						Alert.create("保存成功");
					}else{
						TblAnimalRecIndex tblAnimalRecIndex = 
								BaseService.getTblAnimalRecIndexService().getById(recId);
						tblAnimalRecIndex.setRecDate(recDate);
						tblAnimalRecIndex.setAgeMaleU(ageMaleU);
						tblAnimalRecIndex.setAgeMaleL(ageMaleL);
						tblAnimalRecIndex.setAgeFemaleU(ageFemaleU);
						tblAnimalRecIndex.setAgeFemaleL(ageFemaleL);
						tblAnimalRecIndex.setAgeUnit(ageUnit);
						tblAnimalRecIndex.setProducer(producer);
						tblAnimalRecIndex.setCertificate(certificate);
						tblAnimalRecIndex.setHasAirConditioner(hasAirConditioner);
						tblAnimalRecIndex.setReqDeviation(reqDeviation);
						tblAnimalRecIndex.setPackDamaged(packDamaged);
						tblAnimalRecIndex.setAbnormalInfo(abnormalInfo);
						tblAnimalRecIndex.setThimerosal(thimerosal);
						tblAnimalRecIndex.setCerHealthRpt(cerHealthRpt);
						tblAnimalRecIndex.setCerBackInfo(cerBackInfo);
						tblAnimalRecIndex.setCerReceipt(cerReceipt);
						tblAnimalRecIndex.setCerOther(cerOther);
						tblAnimalRecIndex.setRemark(remark);
						BaseService.getTblAnimalRecIndexService().update(tblAnimalRecIndex);
						ReceivePaneController.getInstance().loadData(recId);
						Alert.create("保存成功");
					}
				}else if("".equals(checker)){//签字但未复核 编辑保存
							if(Confirm.create(Main.getInstance().getStage(), 
								"提示", "动物信息已签字，编辑信息将记录修改痕迹", "确定继续吗？")){
							//电子签名
							Stage stage = new Stage();
							SignFrame signFrame = new SignFrame();
							try {
								signFrame.start(stage);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							if("OK".equals(SignFrame.getType())){
								//签字通过
								User user = SignFrame.getUser();
								String realName =(
										user !=null ? user.getRealName():"");
								//修改痕迹列表
								List<TblTrace> tblTraceList = new ArrayList<TblTrace>();
								
								String producer = producerText.getText().toString().trim();
								if(!this.producer.equals(producer)){
									addTblTrace(tblTraceList, "producer", this.producer, producer, realName);
									this.producer = producer;
								}
								String certificate = certificateText.getText().toString().trim();
								 if(!this.certificate.equals(certificate)){
										addTblTrace(tblTraceList, "certificate", this.certificate, certificate, realName);
										this.certificate = certificate;
									}
								String recDateStr=recDatePane.getTextField().getText().toString().trim();
								if(!this.recDateStr.equals(recDateStr)){
									addTblTrace(tblTraceList, "recDate", this.recDateStr, recDateStr, realName);
									this.recDateStr = recDateStr;
								}
								Date recDate = DateUtil.stringToDate(recDateStr,"yyyy-MM-dd");
								int hasAirConditioner = 0;
								if(hasAirConditionerA.isSelected()){
									hasAirConditioner = 1;
								}
								if(this.hasAirConditioner != hasAirConditioner){
									addTblTrace(tblTraceList, "hasAirConditioner", this.hasAirConditioner == 0? "无":"有", hasAirConditioner == 0? "无":"有"+"", realName);
									this.hasAirConditioner = hasAirConditioner;
								}
								String ageMaleUStr = ageMaleUText.getText().toString().trim();
								if(!this.ageMaleUStr.equals(ageMaleUStr)){
									addTblTrace(tblTraceList, "ageMaleU", this.ageMaleUStr, ageMaleUStr, realName);
									this.ageMaleUStr = ageMaleUStr;
								}
								String ageMaleLStr = ageMaleLText.getText().toString().trim();
								if(!this.ageMaleLStr.equals(ageMaleLStr)){
									addTblTrace(tblTraceList, "ageMaleL", this.ageMaleLStr, ageMaleLStr, realName);
									this.ageMaleLStr = ageMaleLStr;
								}
								String ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
								if(!this.ageFemaleUStr.equals(ageFemaleUStr)){
									addTblTrace(tblTraceList, "ageFemaleU", this.ageFemaleUStr, ageFemaleUStr, realName);
									this.ageFemaleUStr = ageFemaleUStr;
								}
								String ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
								if(!this.ageFemaleLStr.equals(ageFemaleLStr)){
									addTblTrace(tblTraceList, "ageFemaleL", this.ageFemaleLStr, ageFemaleLStr, realName);
									this.ageFemaleLStr = ageFemaleLStr;
								}
								float ageMaleU = Float.valueOf(ageMaleUStr);
								float ageMaleL = Float.valueOf(ageMaleLStr);
								float ageFemaleU = Float.valueOf(ageFemaleUStr);
								float ageFemaleL = Float.valueOf(ageFemaleLStr);
								String ageUnit = ageUnitComboBox.getSelectionModel().getSelectedItem();
								if(!this.ageUnit.equals(ageUnit)){
									addTblTrace(tblTraceList, "ageUnit", this.ageUnit, ageUnit, realName);
									this.ageUnit = ageUnit;
								}
								
								String cerHealthRpt="0";
								if(cerHealthRptCheckBox.isSelected()){
									cerHealthRpt="1";
								}
								if(!this.cerHealthRpt.equals(cerHealthRpt)){
									addTblTrace(tblTraceList, "cerHealthRpt", this.cerHealthRpt.equals("0") ?"无":"有" , cerHealthRpt.equals("0") ?"无":"有", realName);
									this.cerHealthRpt = cerHealthRpt;
								}
								String cerBackInfo="0";
								if(cerBackInfoCheckBox.isSelected()){
									cerBackInfo="1";
								}
								if(!this.cerBackInfo.equals(cerBackInfo)){
									addTblTrace(tblTraceList, "cerBackInfo", this.cerBackInfo.equals("0") ?"无":"有", cerBackInfo.equals("0") ?"无":"有", realName);
									this.cerBackInfo = cerBackInfo;
								}
								String cerReceipt="0";
								if(cerReceiptCheckBox.isSelected()){
									cerReceipt="1";
								}
								if(!this.cerReceipt.equals(cerReceipt)){
									addTblTrace(tblTraceList, "cerReceipt", this.cerReceipt.equals("0") ?"无":"有", cerReceipt.equals("0") ?"无":"有", realName);
									this.cerReceipt = cerReceipt;
								}
								String cerOther =cerOtherText.getText().trim();
								if(!this.cerOther.equals(cerOther)){
									addTblTrace(tblTraceList, "cerOther", this.cerOther, cerOther, realName);
									this.cerOther = cerOther;
								}
								String reqDeviation = reqDeviationText.getText().toString().trim();
								if(!this.reqDeviation.equals(reqDeviation)){
									addTblTrace(tblTraceList, "reqDeviation", this.reqDeviation, reqDeviation, realName);
									this.reqDeviation = reqDeviation;
								}
								String packDamaged = packDamagedText.getText().toString().trim();
								if(!this.packDamaged.equals(packDamaged)){
									addTblTrace(tblTraceList, "packDamaged", this.packDamaged, packDamaged, realName);
									this.packDamaged = packDamaged;
								}
								String abnormalInfo = abnormalInfoText.getText().toString().trim();
								if(!this.abnormalInfo.equals(abnormalInfo)){
									addTblTrace(tblTraceList, "abnormalInfo", this.abnormalInfo, abnormalInfo, realName);
									this.abnormalInfo = abnormalInfo;
								}
								String thimerosal ="";
								if(null!=thimerosalComboBox.getSelectionModel()){
									thimerosal = thimerosalComboBox.getSelectionModel().getSelectedItem();
								}
								if(!this.thimerosal.equals(thimerosal)){
									addTblTrace(tblTraceList, "thimerosal", this.thimerosal, thimerosal, realName);
									this.thimerosal = thimerosal;
								}
								String remark = remarkText.getText().toString().trim();
								if(!this.remark.equals(remark)){
									addTblTrace(tblTraceList, "remark", this.remark, remark, realName);
									this.remark = remark;
								}
								
									TblAnimalRecIndex tblAnimalRecIndex = 
											BaseService.getTblAnimalRecIndexService().getById(recId);
									tblAnimalRecIndex.setRecDate(recDate);
									tblAnimalRecIndex.setAgeMaleU(ageMaleU);
									tblAnimalRecIndex.setAgeMaleL(ageMaleL);
									tblAnimalRecIndex.setAgeFemaleU(ageFemaleU);
									tblAnimalRecIndex.setAgeFemaleL(ageFemaleL);
									tblAnimalRecIndex.setAgeUnit(ageUnit);
									tblAnimalRecIndex.setProducer(producer);
									tblAnimalRecIndex.setCertificate(certificate);
									tblAnimalRecIndex.setHasAirConditioner(hasAirConditioner);
									tblAnimalRecIndex.setReqDeviation(reqDeviation);
									tblAnimalRecIndex.setPackDamaged(packDamaged);
									tblAnimalRecIndex.setAbnormalInfo(abnormalInfo);
									tblAnimalRecIndex.setThimerosal(thimerosal);
									tblAnimalRecIndex.setCerHealthRpt(cerHealthRpt);
									tblAnimalRecIndex.setCerBackInfo(cerBackInfo);
									tblAnimalRecIndex.setCerReceipt(cerReceipt);
									tblAnimalRecIndex.setCerOther(cerOther);
									tblAnimalRecIndex.setRemark(remark);
									BaseService.getTblAnimalRecIndexService().update(tblAnimalRecIndex);
									//保存修改痕迹列表
									BaseService.getTblTraceservice().saveList(tblTraceList);
									ReceivePaneController.getInstance().loadData(recId);
									Alert.create("保存成功");
							}
						}
				}else{//已复核
					Alert2.create("已复核，无法修改");
				}
			}
		}
	}
	/**
	 * 记录修改痕迹
	 * @param tblTraceList
	 * @param columnName
	 * @param oldValue
	 * @param newVale
	 * @param operator
	 */
	private void  addTblTrace(final List<TblTrace> tblTraceList,String columnName,
			String oldValue,String newVale,String operator){
		//记录修改痕迹  
		TblTrace tblTrace = new TblTrace();
		tblTrace.setTableName("TblAnimalRecIndex");//表名
		tblTrace.setDataId(recId+","+columnName); //检测数据表主键
		tblTrace.setOperateMode(1);//修改
		tblTrace.setOldValue(oldValue);
		tblTrace.setNewValue(newVale);
		tblTrace.setOperator(operator);
		tblTrace.setHost(SystemTool.getIPAddress());
		tblTrace.setModifyReason("");
		tblTraceList.add(tblTrace);
	}
	/**
	 * 签字  button   点击事件
	 * */
	@FXML
	private void onSignButton(ActionEvent event){
		if(!receiver.equals("")){
			Alert2.create("已签字，不可重复签字");
			return ;
		}
		if(recId.equals("")){
			Alert2.create("请先保存基本信息");
			return ;
		}
		boolean isAnimalRegister = true;  //动物登记已完毕
		int total = Integer.valueOf(numMaleStr) +Integer.valueOf(numFemaleStr);
		//存放课题编号列表
		Set<String> studyNoSet = new HashSet<String>();
		if(data_animalTable.size() == total){
			for(TblAnimalRecListForTableView rowData:data_animalTable){
				if( null == rowData.getAnimalId() || rowData.getAnimalId().equals("") 
					||null == rowData.getGender()	||rowData.getGender().equals("")){
					//为录入完毕
					isAnimalRegister = false;
					break;
				}
				if(null!=rowData.getPlanStudyNo() && !"".equals(rowData.getPlanStudyNo())){
					studyNoSet.add(rowData.getPlanStudyNo());
				}
			}
		}else{
			isAnimalRegister = false;
		}
		if(!isAnimalRegister){
			Alert2.create("动物信息未录入完毕");
			return ;
		}
		//电子签名
		Stage stage = new Stage();
		SignFrame signFrame = new SignFrame();
		try {
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if("OK".equals(SignFrame.getType())){
			//签字通过
			User user = SignFrame.getUser();
			String realName =(
					user !=null ? user.getRealName():"");
			//新建 日志（暂未保存）
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());// 系统名称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());// 系统版本
			tblLog.setOperatType("接收签字");
			tblLog.setOperatOject("检疫，动物接收");
			tblLog.setOperator(realName);
			tblLog.setOperatContent("接收单号：" + recId);
			tblLog.setOperatHost(SystemTool.getIPAddress());
			//更新接收单中的  SignId字段   和 tabQRRequest中的分配状态（planState）  ,保存日志和电子签字   并返回signId
			
			String signId = BaseService.getTblAnimalRecIndexService().updateSignIdPlanStateAndSaveTblESAndTblLog(tblLog,recId,studyNoSet);
			
			//4.更新 receiver
			ReceiveRegisterFrame.receiver=signId;
			ReceivePaneController.getInstance().loadData(recId);
			//初始化不可编辑区域
			initEditableHBox();
			Alert.create("签字成功");
		}
		
	}
	/**
	 * 复核  button   点击事件
	 * */
	@FXML
	private void onCheckButton(ActionEvent event){
		if(recId.equals("")){
			Alert2.create("请先保存基本信息");
			return ;
		}
		if(receiver.equals("")){
			Alert2.create("请先签字确认");
			return ;
		}
		if(!checker.equals("")){
			Alert2.create("已复核，不可重复复核");
			return ;
		}
		//电子签名
				Stage stage = new Stage();
				SignFrame signFrame = new SignFrame();
				try {
					signFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if("OK".equals(SignFrame.getType())){
					//签字通过
					User user = SignFrame.getUser();
					String realName =(
							user !=null ? user.getRealName():"");
					//新建 日志（暂未保存）
					TblLog tblLog = new TblLog();
					tblLog.setSystemName(SystemMessage.getSystemName());// 系统名称
					tblLog.setSystemVersion(SystemMessage.getSystemVersion());// 系统版本
					tblLog.setOperatType("接收复核");
					tblLog.setOperatOject("检疫，动物接收复核");
					tblLog.setOperator(realName);
					tblLog.setOperatContent("接收单号：" + recId);
					tblLog.setOperatHost(SystemTool.getIPAddress());
					//更新接收单中的  checkId字段,保存日志和电子签字并返回signId
					
					String signId = BaseService.getTblAnimalRecIndexService().updateCheckIdAndSaveTblESAndTblLog(tblLog,recId);
					
					//4.更新 checker
					ReceiveRegisterFrame.checker=signId;
					ReceivePaneController.getInstance().loadData(recId);
					//初始化不可编辑区域
					initEditableHBox();
					Alert.create("复核成功");
				}
	}
	/**
	 * 动物登记  button   点击事件
	 * */
	@FXML
	private void onAnimalRegisterButton(ActionEvent event){
		if(recId.equals("")){
			Alert2.create("请先保存基本信息");
			return ;
		}
		Stage stage = new Stage();
		stage.initOwner(Main.getInstance().getStage());
		AnimalRegisterFrame animalRegisterFrame = new AnimalRegisterFrame();
		try {
			animalRegisterFrame.start(stage);
			AnimalRegisterFrame.setRecId(recId);
			AnimalRegisterFrame.setReceiver(receiver);
			AnimalRegisterFrame.getInstance().loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 打印动物接收登记表      button    
	 * @param event
	 */
	@FXML
	private void onPrintAnimalRecIndexButton(ActionEvent event){
		String recId =ReceiveRegisterFrame.recId;
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
        
        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
        
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
	 * 初始化不可编辑区域
	 */
	private void initEditableHBox(){
		editableHBox.setVisible(false);
		editableHBox2.setVisible(false);
		editableHBox3.setVisible(false);
		editableHBox4.setVisible(false);
		if(!"".equals(receiver)){
			editableHBox.setVisible(true);
			editableHBox2.setVisible(true);
			editableHBox3.setVisible(true);
		}
		if(!"".equals(checker)){
			editableHBox4.setVisible(true);
		}
	}
	
	/**
	 * 检查所有数据
	 */
	private boolean checkAllData(){
		String producer = producerText.getText().toString().trim();
		if ("".equals(producer)) {// 文本内无数据 则不添加（直接返回）
			Alert2.create("试验动物生产单不能为空");
			producerText.requestFocus();
			return false;
		}
		if(producer.getBytes().length>200){
			Alert2.create("试验动物生产单长度不能超过200");
			producerText.requestFocus();
			return false;
		}
		String certificate = certificateText.getText().toString().trim();
		if ("".equals(certificate)) {// 文本内无数据 则不添加（直接返回）
			Alert2.create("动物合格证号不能为空");
			certificateText.requestFocus();
			return false;
		}
		if(certificate.getBytes().length>50){
			Alert2.create("动物合格证号长度不能超过50");
			certificateText.requestFocus();
			return false;
		}
		
		
		if(null == animalLevelComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择动物级别");
			animalLevelComboBox.requestFocus();
			return false;
		}
		if(null == animalTypeComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择动物种类");
			animalTypeComboBox.requestFocus();
			return false;
		}
		if(null == animalStrainComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择动物品系");
			animalStrainComboBox.requestFocus();
			return false;
		}
		
		String room = roomText.getText().toString().trim();
		if ("".equals(room)) {// 文本内无数据 则不添加（直接返回）
			Alert2.create("安置房间号码不能为空");
			roomText.requestFocus();
			return false;
		}
		if(room.getBytes().length>50){
			Alert2.create("安置房间号码长度不能超过50");
			roomText.requestFocus();
			return false;
		}
		
		if(null == group1.getSelectedToggle()){
			Alert2.create("请选择运输车辆有无空调");
			hasAirConditionerA.requestFocus();
			return false;
		}
		
		String numMaleStr = numMaleText.getText().toString().trim();
		if(null == numMaleStr || "".equals(numMaleStr)){
			Alert2.create("雄性动物数量不能为空");
			numMaleText.requestFocus();
			return false;
		}
		if(!numMaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雄性动物数量请输入正整数");
			numMaleText.requestFocus();
			return false;
		}
		String numFemaleStr = numFemaleText.getText().toString().trim();
		if(null == numFemaleStr || "".equals(numFemaleStr)){
			Alert2.create("雌性动物数量不能为空");
			numFemaleText.requestFocus();
			return false;
		}
		if(!numFemaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雌性动物数量请输入正整数");
			numFemaleText.requestFocus();
			return false;
		}
		
		String ageMaleUStr = ageMaleUText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(ageMaleUStr)){
			Alert2.create("请正确填写年龄范围");
			ageMaleUText.requestFocus();
			return false;
		}
		String ageMaleLStr = ageMaleLText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(ageMaleLStr)){
			Alert2.create("请正确填写年龄范围");
			ageMaleLText.requestFocus();
			return false;
		}
		if(Float.valueOf(ageMaleUStr) > Float.valueOf(ageMaleLStr)){
			Alert2.create("年龄范围前项不能大于后项");
			ageMaleUText.requestFocus();
			return false;
		}
		String ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
		if(!NumberValidationUtils.isPositiveRealNumber(ageFemaleUStr)){
			Alert2.create("请正确填写年龄范围");
			ageFemaleUText.requestFocus();
			return false;
		}
		String ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
		if(!NumberValidationUtils.isPositiveRealNumber(ageFemaleLStr)){
			Alert2.create("请正确填写年龄范围");
			ageFemaleLText.requestFocus();
			return false;
		}
		if(Float.valueOf(ageFemaleUStr) > Float.valueOf(ageFemaleLStr)){
			Alert2.create("年龄范围前项不能大于后项");
			ageFemaleUText.requestFocus();
			return false;
		}
		if(null == ageUnitComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择年龄单位");
			ageUnitComboBox.requestFocus();
			return false;
		}
		String cerOther =cerOtherText.getText().trim();
		if(cerOther.getBytes().length>50){
			Alert2.create("’其他‘长度不能超过50");
			cerOtherText.requestFocus();
			return false;
		}
		String reqDeviation = reqDeviationText.getText().toString().trim();
		if(reqDeviation.getBytes().length>200){
			Alert2.create("’与需求单要求的偏差‘长度不能超过200");
			reqDeviationText.requestFocus();
			return false;
		}
		String packDamaged = packDamagedText.getText().toString().trim();
		if(packDamaged.getBytes().length>200){
			Alert2.create("’运输动物箱的破损情况‘长度不能超过200");
			packDamagedText.requestFocus();
			return false;
		}
		String abnormalInfo = abnormalInfoText.getText().toString().trim();
		if(abnormalInfo.getBytes().length>100){
			Alert2.create("’动物异常情况‘长度不能超过100");
			abnormalInfoText.requestFocus();
			return false;
		}
		String remark = remarkText.getText().toString().trim();
		if(remark.getBytes().length>100){
			Alert2.create("备注长度不能超过100");
			remarkText.requestFocus();
			return false;
		}
		
		return true;
	}
	//float  转成  String
		private String floatToStr(float obj){
			int a= (int) obj;
			if(a == obj){
				return a+"";
			}
			return obj+"";
		}
	/**
	 * Main
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("ReceiveRegisterFrame.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("接收登记");
		stage.setResizable(false);
		stage.show();
		
	}
	public static String getRecId() {
		return recId;
	}
	public static void setRecId(String recId) {
		ReceiveRegisterFrame.recId = recId;
	}
	public static String getReceiver() {
		return receiver;
	}
	public static void setReceiver(String receiver) {
		ReceiveRegisterFrame.receiver = receiver;
	}
	public static String getChecker() {
		return checker;
	}
	public static void setChecker(String checker) {
		ReceiveRegisterFrame.checker = checker;
	}

}
