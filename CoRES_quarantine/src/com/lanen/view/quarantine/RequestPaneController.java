package com.lanen.view.quarantine;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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
import com.lanen.model.User;
import com.lanen.model.quarantine.TblQRRequest;
import com.lanen.model.studyplan.DictAnimalStrain;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.TblQRRequestForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;

import datecontrol.DatePicker;
import datecontrol.Junit;
/**
 * 需求申请
 * @author Administrator
 *
 */
public class RequestPaneController implements Initializable {

	
	@FXML
	private RadioButton radioButtonA;//未受理
	@FXML
	private RadioButton radioButtonB;//已受理
	@FXML
	private RadioButton radioButtonC;//未分配
	@FXML
	private RadioButton radioButtonD;//已分配
	@FXML
	private RadioButton radioButtonE;//已移交
	@FXML
	private RadioButton radioButtonF;//全部
	private ToggleGroup group = new ToggleGroup();
	
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
	private TableView<TblQRRequestForTableView> tblQRRequestTable; 
	private ObservableList<TblQRRequestForTableView> data_tblQRRequestTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblQRRequestForTableView, String> qrRIDCol;
	@FXML
	private TableColumn<TblQRRequestForTableView, String> iacucCodeCol; // IACUC编号
	@FXML
	private TableColumn<TblQRRequestForTableView, String> studyPlanFinishedCol; // 有无试验方案 0：无 1：有
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalTypeCol; // 动物种类
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalStrainCol; // 动物品系
	@FXML
	private TableColumn<TblQRRequestForTableView, String> animalLevelCol; // 动物级别
	@FXML
	private TableColumn<TblQRRequestForTableView, String> numMaleCol; // 数量雄
	@FXML
	private TableColumn<TblQRRequestForTableView, String> numFemaleCol; // 数量雌
	@FXML
	private TableColumn<TblQRRequestForTableView, String> ageMaleULCol; // 雄年龄 范围
	@FXML
	private TableColumn<TblQRRequestForTableView, String> ageFemaleULCol; // 雌年龄范围
	@FXML
	private TableColumn<TblQRRequestForTableView, String> weightMaleULCol; // 雄体重范围
	@FXML
	private TableColumn<TblQRRequestForTableView, String> weightFemaleULCol; // 雌体重范围
	@FXML
	private TableColumn<TblQRRequestForTableView, String> applicantCol; // 申请人
	@FXML
	private TableColumn<TblQRRequestForTableView, String> submitTimeCol; // 提交时间
	@FXML
	private TableColumn<TblQRRequestForTableView, String> acceptStateCol; // 受理状态
	@FXML
	private TableColumn<TblQRRequestForTableView, String> transferStateCol; // 提交状态
	@FXML
	private TableColumn<TblQRRequestForTableView, String> planStateCol; // 分配状态
	
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
	@FXML
	private Label stateLabel;
	
	private String startDateStr="";
	private String endDateStr="";
	private String animalTypeStr="";
	private String animalStrainStr="";
	private String stateStr="";
	private String totalStr="";
	private int stateInt=6;// 1未受理  2已受理 3未分配 4已分配   5已移交   6全部
	
	
	private static RequestPaneController instance;
	
	public static RequestPaneController getInstance(){
		return instance;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		// 单选按钮置为一组,默认选“全部”
		initToggleGroup();
		//初始化日期选择器
		initDate();
		//初始化动物种类与动物品系下拉框
		initAnimalTypeAndStrainComboBox();
		//初始化表格
		initTable();
	}
	/**
	 *  单选按钮置为一组,默认选“全部”
	 * */
	private void initToggleGroup() {
		radioButtonA.setToggleGroup(group);
		radioButtonB.setToggleGroup(group);
		radioButtonC.setToggleGroup(group);
		radioButtonD.setToggleGroup(group);
		radioButtonE.setToggleGroup(group);
		radioButtonF.setToggleGroup(group);
		group.selectToggle(radioButtonF);
		stateStr="全部";
		stateInt=6;
		
	}

	/**登记需求申请  按钮事件*/
	@FXML
	private void onRegisterRequestButtonAction(ActionEvent event){
		Stage stage = new Stage();
		stage.initOwner(Main.getInstance().getStage());
		RegisterRequestFrame registerRequestFrame = new RegisterRequestFrame();
		try {
			registerRequestFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**确认受理  按钮事件*/
	@FXML
	private void onEnterAcceptButtonAction(ActionEvent event){
		TblQRRequestForTableView tblQRRequestForTableView = tblQRRequestTable.getSelectionModel().getSelectedItem();
		if(null == tblQRRequestForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		if(tblQRRequestForTableView.getAcceptState().equals("是")){
			Alert2.create("该申请已受理");
			return ;
		}
//		//电子签名
//		Stage stage = new Stage();
//		SignFrame signFrame = new SignFrame(Main.getInstance().getStage());
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if("OK".equals(SignFrame.getType())){
		if(Confirm2.create(Main.getInstance().getStage(), 
				"确定受理需求申请("+tblQRRequestForTableView.getQrRID()+")吗？")){
			TblQRRequestForTableView newTblQRRequestForTableView = new TblQRRequestForTableView();
			
			newTblQRRequestForTableView.setQrRID(tblQRRequestForTableView.getQrRID());
			newTblQRRequestForTableView.setIacucCode(tblQRRequestForTableView.getIacucCode());
			newTblQRRequestForTableView.setStudyPlanFinished(tblQRRequestForTableView.getStudyPlanFinished());
			newTblQRRequestForTableView.setAcceptState("是");
			newTblQRRequestForTableView.setPlanState(tblQRRequestForTableView.getPlanState());
			newTblQRRequestForTableView.setTransferState(tblQRRequestForTableView.getTransferState());
			newTblQRRequestForTableView.setAnimalType(tblQRRequestForTableView.getAnimalType());
			newTblQRRequestForTableView.setAnimalStrain(tblQRRequestForTableView.getAnimalStrain());
			newTblQRRequestForTableView.setAnimalLevel(tblQRRequestForTableView.getAnimalLevel());
			newTblQRRequestForTableView.setNumMale(tblQRRequestForTableView.getNumMale());
			newTblQRRequestForTableView.setNumFemale(tblQRRequestForTableView.getNumFemale());
			newTblQRRequestForTableView.setAgeMaleUL(tblQRRequestForTableView.getAgeMaleUL());
			newTblQRRequestForTableView.setAgeFemaleUL(tblQRRequestForTableView.getAgeFemaleUL());
			newTblQRRequestForTableView.setWeightMaleUL(tblQRRequestForTableView.getWeightMaleUL());
			newTblQRRequestForTableView.setWeightFemaleUL(tblQRRequestForTableView.getWeightFemaleUL());
			newTblQRRequestForTableView.setApplicant(tblQRRequestForTableView.getApplicant());
			newTblQRRequestForTableView.setSubmitTime(tblQRRequestForTableView.getSubmitTime());
			
			data_tblQRRequestTable.set(tblQRRequestTable.getSelectionModel().getSelectedIndex(), newTblQRRequestForTableView);
			
			
			//受理人，受理时间
			User user = SaveUserInfo.getUser();
			String realName ="";
			if(null!=user){
				realName=user.getRealName();
			}
			BaseService.getTblQRRequestService().updateAcceptStateByQRRId(tblQRRequestForTableView.getQrRID(),1,realName);
		}
	}
	/**取消申请  按钮事件*/
	@FXML
	private void onCancelRequestButtonAction(ActionEvent event){
		TblQRRequestForTableView tblQRRequestForTableView = tblQRRequestTable.getSelectionModel().getSelectedItem();
		if(null == tblQRRequestForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		if(tblQRRequestForTableView.getAcceptState().equals("是")){
			Alert2.create("该申请已受理,无法取消");
			return ;
		}
		if(Confirm2.create(Main.getInstance().getStage(), 
				"确定取消需求申请("+tblQRRequestForTableView.getQrRID()+")吗？")){
			
			data_tblQRRequestTable.remove(tblQRRequestTable.getSelectionModel().getSelectedIndex());
			tblQRRequestTable.getSelectionModel().clearSelection();
			//受理人，受理时间
			User user = SaveUserInfo.getUser();
			String realName ="";
			if(null!=user){
				realName=user.getRealName();
			}
			BaseService.getTblQRRequestService().updateAcceptStateByQRRId(tblQRRequestForTableView.getQrRID(),-1,realName);
		}
	}
	/**打印   按钮事件*/
	@FXML
	private void onPrintButtonAction(ActionEvent event){
		TblQRRequestForTableView tblQRRequestForTableView = tblQRRequestTable.getSelectionModel().getSelectedItem();
		if(null == tblQRRequestForTableView){
			Alert2.create("请先选择行");
			return ;
		}
		JasperReport jr = null;
        JasperPrint jp = null;
        try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblQRRequest.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return ;
		}
        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
        String studyNoListStr = BaseService.getTblQRRequestStudyNoService().getStudyNoListStrByQRRId(tblQRRequestForTableView.getQrRID());
        TblQRRequest tblQRRequest = BaseService.getTblQRRequestService().getById(tblQRRequestForTableView.getQrRID());
	   
        String iacucCode=tblQRRequestForTableView.getIacucCode();
	    String isIacucCode ="是";
	    if(null == iacucCode || "".equals(iacucCode)){
	    	isIacucCode="否";
	    }
	    String studyPlanFinished=tblQRRequestForTableView.getStudyPlanFinished();
	    if(null!=studyPlanFinished ||"有".equals(studyPlanFinished)){
	    	studyPlanFinished="已制定试验方案初稿";
	    }else{
	    	studyPlanFinished="未制定试验方案初稿";
	    }
	    String animalTypeStrain = tblQRRequestForTableView.getAnimalType()+","+tblQRRequestForTableView.getAnimalStrain(); 
	    String animalLevel=tblQRRequestForTableView.getAnimalLevel();
	    String numMale =tblQRRequestForTableView.getNumMale();
	    String numFemale=tblQRRequestForTableView.getNumFemale();
	    String ageMaleUL=tblQRRequestForTableView.getAgeMaleUL();
	    String ageFemaleUL=tblQRRequestForTableView.getAgeFemaleUL();
	    String weightMaleUL=tblQRRequestForTableView.getWeightMaleUL();
	    String weightFemaleUL=tblQRRequestForTableView.getWeightFemaleUL();
	    
	    String specialRearReq="";
        String intendRoom ="";
        String remark="       ";
        String other="       ";
        if(null!=tblQRRequest){
        	specialRearReq=tblQRRequest.getSpecialRearReq();
        	intendRoom=tblQRRequest.getIntendRoom();
        	remark += tblQRRequest.getRemark();
        }
        Map<String,Object> map = new HashMap<String,Object>();
        /**编号*/
    	String number =BaseService.getDictOutputSettingsService().getShowByLabel("试验动物需求单-编号");
    	map.put("number",number);
        map.put("logoImage",logoImage);
        map.put("studyNoListStr",studyNoListStr);
        map.put("isIacucCode",isIacucCode);
        map.put("iacucCode",iacucCode);
        map.put("studyPlanFinished",studyPlanFinished);
        map.put("animalTypeStrain",animalTypeStrain);
        map.put("animalLevel",animalLevel);
        map.put("numMale",numMale);
        map.put("numFemale",numFemale);
        map.put("ageMaleUL",ageMaleUL);
        map.put("ageFemaleUL",ageFemaleUL);
        map.put("weightMaleUL",weightMaleUL);
        map.put("weightFemaleUL",weightFemaleUL);
        map.put("specialRearReq",specialRearReq);
        map.put("intendRoom",intendRoom);
        map.put("remark",remark);
        map.put("other",other);
        
        try {
			jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(null));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return ;
		}
		Main.getInstance().openJFrame(jp, "试验动物需求单");
	}
	/**
	 * 单选按钮
	 * @param event
	 */
	@FXML
	private void onRadioButtonAction(ActionEvent event) {
		String selectedRadioButtonId = ((RadioButton) event.getSource()).getId();
		// stateInt 1未受理 2已受理 3未分配 4已分配 5已移交 6全部
		if (selectedRadioButtonId.equals("radioButtonA")) {
			stateInt = 1;
		} else if (selectedRadioButtonId.equals("radioButtonB")) {
			stateInt = 2;
		} else if (selectedRadioButtonId.equals("radioButtonC")) {
			stateInt = 3;
		} else if (selectedRadioButtonId.equals("radioButtonD")) {
			stateInt = 4;
		} else if (selectedRadioButtonId.equals("radioButtonE")) {
			stateInt = 5;
		} else {
			stateInt = 6;
		}
	}
	/**查询  按钮事件*/
	@FXML
	private void onSearchButton(ActionEvent event){
//		stateStr =((RadioButton)group.getSelectedToggle()).getText();
//		String selectedRadioButtonId = ((RadioButton)group.getSelectedToggle()).getId();
//		//stateInt  1未受理  2已受理 3未分配 4已分配   5已移交   6全部
//		if(selectedRadioButtonId.equals("radioButtonA")){
//			stateInt=1;
//		}else if(selectedRadioButtonId.equals("radioButtonB")){
//			stateInt=2;
//		}else if(selectedRadioButtonId.equals("radioButtonC")){
//			stateInt=3;
//		}else if(selectedRadioButtonId.equals("radioButtonD")){
//			stateInt=4;
//		}else if(selectedRadioButtonId.equals("radioButtonE")){
//			stateInt=5;
//		}else{
//			stateInt=6;
//		}
		if(startDateStr.compareTo(endDateStr)>0){
			Alert2.create("请前项日期不能晚于后项日期");
			return ;
		}
		//更新表格数据
		updateTableData(startDateStr,endDateStr,stateInt,animalTypeStr,animalStrainStr);
		
	}
	

	/**更新状态栏*/
	private void updateState() {
		startDateLabel.setText(startDateStr);
		endDateLabel.setText(endDateStr);
		stateLabel.setText(stateStr);
		totalLabel.setText(totalStr);
		animalTypeLabel.setText(animalTypeStr);
		animalStrainLabel.setText(animalStrainStr);
	}

	/**加载数据*/
	public void loadData(){
		//加载表格数据
		//更新表格数据
		updateTableData(startDateStr,endDateStr,stateInt,animalTypeStr,animalStrainStr);

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
		startDatePane.setMaxWidth(148);
		
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
		endDatePane.setMaxWidth(148);
		
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
		qrRIDCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("qrRID")); 
		iacucCodeCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("iacucCode")); 
		studyPlanFinishedCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("studyPlanFinished")); 
		animalTypeCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalType")); 
		animalStrainCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalStrain")); 
		animalLevelCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("animalLevel")); 
		numMaleCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("numMale")); 
		numFemaleCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("numFemale")); 
		ageMaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("ageMaleUL")); 
		ageFemaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("ageFemaleUL")); 
		weightMaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("weightMaleUL")); 
		weightFemaleULCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("weightFemaleUL")); 
		applicantCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("applicant")); 
		submitTimeCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("submitTime")); 
		acceptStateCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("acceptState")); 
		transferStateCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("transferState")); 
		planStateCol.setCellValueFactory(new PropertyValueFactory<TblQRRequestForTableView,String>("planState")); 
		tblQRRequestTable.setItems(data_tblQRRequestTable);
	}
	
	/**更新表格数据*/
	private void updateTableData(String startDateStr, String endDateStr, int stateInt,
			String animalTypeStr, String animalStrainStr) {
		data_tblQRRequestTable.clear();
		String ageMaleUL ="";
		String ageFemaleUL="";
		String weightMaleUL="";
		String weightFemaleUL="";
		List<TblQRRequest> tblQRRequestList = BaseService.getTblQRRequestService()//
				.findByStateDateAnimal(startDateStr,endDateStr,stateInt,animalTypeStr,animalStrainStr);
		if(null!=tblQRRequestList && tblQRRequestList.size()>0){
			//总数，状态栏用
			totalStr=tblQRRequestList.size()+"";
			for(TblQRRequest obj:tblQRRequestList){
				ageMaleUL=floatToStr(obj.getAgeMaleU())+"-"+floatToStr(obj.getAgeMaleL())+obj.getAgeUnit();
				ageFemaleUL=floatToStr(obj.getAgeFemaleU())+"-"+floatToStr(obj.getAgeFemaleL())+obj.getAgeUnit();
				weightMaleUL=floatToStr(obj.getWeightMaleU())+"-"+floatToStr(obj.getWeightMaleL())+obj.getWeightUnit();
				weightFemaleUL=floatToStr(obj.getWeightFemaleU())+"-"+floatToStr(obj.getWeightFemaleL())+obj.getWeightUnit();
				data_tblQRRequestTable.add(new TblQRRequestForTableView(obj.getQrRID(),obj.getIacucCode(),
						obj.getStudyPlanFinished(),obj.getAnimalType(),obj.getAnimalStrain(),obj.getAnimalLevel(),
						obj.getNumMale(),obj.getNumFemale(),ageMaleUL,ageFemaleUL,weightMaleUL,weightFemaleUL,
						obj.getApplicant(),obj.getSubmitTime(),obj.getAcceptState(),obj.getTransferState(),obj.getPlanState()));
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
}
