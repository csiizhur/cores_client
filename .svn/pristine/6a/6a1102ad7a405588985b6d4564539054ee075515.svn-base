package com.lanen.view.clinicaltest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblInstrumentVerification;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.SignFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class TblInstrumentVerificationFrameController implements Initializable {
	
	
	@FXML
	private ComboBox<String> instrumentIdComboBox;    //设备Id
	@FXML
	private TextField instrumentNameText;            //设备名称
	@FXML
	private TextField verTypeText;                   //检定类型
	@FXML
	private HBox verDateHBox;                     //检定日期
	@FXML
	private HBox valiDateHBox;                    //有效期
	@FXML
	private TextField operatorText;                  //检定人
	@FXML
	private TextField operatUnitText;                //检定单位
	@FXML
	private Button addBtn;
	
	private ObservableList<String> data_comboBox=FXCollections.observableArrayList();
	private Map<String,DictInstrument> map =new HashMap<String,DictInstrument>();
	DatePicker  datePicker=null;
	DatePicker  datePicker2=null;
	
	@FXML
	private TableView<InstrumentVerificationInfo> table;
	private ObservableList<InstrumentVerificationInfo> data= FXCollections.observableArrayList();
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> idCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> instrumentIdCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> instrumentNameCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> verTypeCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> verDateCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> valiDateCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> operatorCol;
	@FXML
	private TableColumn<InstrumentVerificationInfo,String> operatUnitCol;
	
	private boolean isEditing=false;//非编辑中
	private String currentId = "";
	//数据操作次数
		private int opearatTimes =0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		opearatTimes =0;
		initDatePane();//初始化日期选择器
		
		initComboBox();//初始化下拉框
		
		initTable();
	}

	/**
	 * 初始化Table
	 */
	private void initTable() {
		updateTable();
		idCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"id"));
		instrumentIdCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"dictInstrumentId"));
		instrumentNameCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"instrumentName"));
		verTypeCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"verType"));
		verDateCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"verDate"));
		valiDateCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"valiDate"));
		operatorCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"operator"));
		operatUnitCol.setCellValueFactory(new PropertyValueFactory<InstrumentVerificationInfo, String>(
				"operatUnit"));
		
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<InstrumentVerificationInfo>(){

			@Override
			public void changed(ObservableValue<? extends InstrumentVerificationInfo> arg0,
					InstrumentVerificationInfo oldValue, InstrumentVerificationInfo newValue) {
				if(null!=newValue){
					if(isEditing){
						instrumentIdComboBox.getSelectionModel().clearAndSelect(data_comboBox.indexOf(newValue.getDictInstrumentId()));
				    	verTypeText.setText(newValue.getVerType());                   //检定类型
						datePicker.getTextField().setText(newValue.getVerDate());                     //检定日期
						datePicker2.getTextField().setText(newValue.getValiDate());                    //有效期
						operatorText.setText(newValue.getOperator());                  //检定人
						operatUnitText.setText(newValue.getOperatUnit());
						currentId=newValue.getId();
					}
				}else{
					instrumentIdComboBox.getSelectionModel().clearSelection();    //设备Id
//					instrumentNameText.setText("");            //设备名称
					verTypeText.setText("");                   //检定类型
					datePicker.getTextField().setText("");                     //检定日期
					datePicker2.getTextField().setText("");                    //有效期
					operatorText.setText("");                  //检定人
					operatUnitText.setText("");                //检定单位
				}
				
			}});	
	}

	/**
	 * 更新Table 里数据
	 */
	private void updateTable() {
		data.clear();
		List<TblInstrumentVerification> list=BaseService.getTblInstrumentVerificationService().findAll();
		if(null!=list&&list.size()>0){
			for(TblInstrumentVerification obj:list){
				String verDate=DateUtil.dateToString(obj.getVerDate(), "yyyy-MM-dd");
				String valiDate=DateUtil.dateToString(obj.getValiDate(), "yyyy-MM-dd");
				data.add(new InstrumentVerificationInfo(obj.getId()+"",obj.getDictInstrument().getInstrumentId()+"",obj.getInstrumentName(),
						obj.getVerType(),verDate,valiDate,obj.getOperator(),obj.getOperatUnit()));
			}
		}
		table.setItems(data);
	}

	/**
	 * 初始化下拉框
	 */
	private void initComboBox() {
		List<DictInstrument> list=BaseService.getDictInstrumentService().getAll();
		if(null!=list&&list.size()>0){
			for(DictInstrument obj:list){
				data_comboBox.add(obj.getInstrumentId());
				map.put(obj.getInstrumentId(), obj);
			}
		}
		instrumentIdComboBox.setItems(data_comboBox);
		
		instrumentIdComboBox.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue){
					DictInstrument dictInstrument =map.get(newValue);
					instrumentNameText.setText(dictInstrument.getInstrumentName());
				}else{
					instrumentNameText.setText("");
				}
			}
		});
	}

	/**
	 * 初始化日期选择器
	 */
	private void initDatePane() {
		datePicker = new DatePicker(Locale.CHINA);
		datePicker.getTextField().setEditable(false);
		datePicker.getTextField().setMaxWidth(120);
		datePicker.getTextField().setMinWidth(120);
		datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		datePicker.getCalendarView().todayButtonTextProperty().set("今天");
		datePicker.getCalendarView().setShowWeeks(false);
		datePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		datePicker.setMaxWidth(120);
		verDateHBox.getChildren().add(datePicker);
		
		datePicker2 = new DatePicker(Locale.CHINA);
		datePicker2.getTextField().setEditable(false);
		datePicker2.getTextField().setMaxWidth(120);
		datePicker2.getTextField().setMinWidth(120);
		datePicker2.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		datePicker2.getCalendarView().todayButtonTextProperty().set("今天");
		datePicker2.getCalendarView().setShowWeeks(false);
		datePicker2.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		datePicker2.setMaxWidth(120);
		valiDateHBox.getChildren().add(datePicker2);
		
	}
	/**
	 * 复选框单机事件
	 * @param event
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event){
		CheckBox checkBox=(CheckBox) event.getSource();
		if(checkBox.isSelected()){
			isEditing=true;
			instrumentIdComboBox.setDisable(true);
			addBtn.setText("保存");
			
		    InstrumentVerificationInfo verInfo=	table.getSelectionModel().getSelectedItem();
		    if(null!=verInfo){
		    	instrumentIdComboBox.getSelectionModel().clearAndSelect(data_comboBox.indexOf(verInfo.getDictInstrumentId()));
		    	verTypeText.setText(verInfo.getVerType());                   //检定类型
				datePicker.getTextField().setText(verInfo.getVerDate());                     //检定日期
				datePicker2.getTextField().setText(verInfo.getValiDate());                    //有效期
				operatorText.setText(verInfo.getOperator());                  //检定人
				operatUnitText.setText(verInfo.getOperatUnit());    //检定单位
				currentId=verInfo.getId();
		    }
			
		}else{
			isEditing=false;
			instrumentIdComboBox.setDisable(false);
			
			instrumentIdComboBox.getSelectionModel().clearSelection();    //设备Id
//			instrumentNameText.setText("");            //设备名称
			verTypeText.setText("");                   //检定类型
			datePicker.getTextField().setText("");                     //检定日期
			datePicker2.getTextField().setText("");                    //有效期
			operatorText.setText("");                  //检定人
			operatUnitText.setText("");                //检定单位
			
			addBtn.setText("增加");
			
		}
	}
	/**
	 * 添加按钮事件
	 * @param event
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event){
		String instrumentId=instrumentIdComboBox.getSelectionModel().getSelectedItem();    //设备Id
		String instrumentName=	instrumentNameText.getText();           //设备名称
		String verType= verTypeText.getText();              //检定类型
		String verDate=	datePicker.getTextField().getText();                     //检定日期
		String valiDate=	datePicker2.getTextField().getText();              //有效期
		String operator= operatorText.getText();                 //检定人
		String operatUnit=	operatUnitText.getText(); //检定单位
		if(null==instrumentId||instrumentId.equals("")){
//			Alert.create("请选择设备Id");
			Messager.showWarnMessage("请选择设备Id！");
			return ;
		}else 
		if(instrumentName.equals("")){
//			Alert.create("设备名称不能为空");
			Messager.showWarnMessage("设备名称不能为空！");
			return ;
		}else if(verType.equals("")||verDate.equals("")||valiDate.equals("")||operator.equals("")||operatUnit.equals("")){
//			Alert.create("信息填写不全");
			Messager.showWarnMessage("信息填写不全！");
			return ;
		}
		if(!isEditing){//非编辑状态下
			TblInstrumentVerification verification=new TblInstrumentVerification();
			DictInstrument dictInstrument=map.get(instrumentId);
			verification.setDictInstrument(dictInstrument);
			verification.setInstrumentName(instrumentName);
			verification.setVerType(verType);
			verification.setOperator(operator);
			verification.setOperatUnit(operatUnit);
			verification.setVerDate(DateUtil.stringToDate(verDate, "yyyy-MM-dd"));
			verification.setValiDate(DateUtil.stringToDate(valiDate, "yyyy-MM-dd"));
			TblInstrumentVerification newVerification=BaseService.getTblInstrumentVerificationService().saveHaveReturn(verification);
//			updateTable();
			data.add(new InstrumentVerificationInfo(newVerification.getId()+"",newVerification.getDictInstrument().getInstrumentId()+"",newVerification.getInstrumentName(),
					newVerification.getVerType(),verDate,valiDate,newVerification.getOperator(),newVerification.getOperatUnit()));
//			Alert.create("增加成功");
			Messager.showMessage("增加成功！");
			
			instrumentIdComboBox.getSelectionModel().clearSelection();    //设备Id
			instrumentNameText.setText("");            //设备名称
			verTypeText.setText("");                   //检定类型
			datePicker.getTextField().setText("");                     //检定日期
			datePicker2.getTextField().setText("");                    //有效期
			operatorText.setText("");                  //检定人
			operatUnitText.setText("");                //检定单位
		}else{
			TblInstrumentVerification verification=BaseService.getTblInstrumentVerificationService().getById(currentId);
			
			DictInstrument dictInstrument=map.get(instrumentId);
			verification.setDictInstrument(dictInstrument);
			verification.setInstrumentName(instrumentName);
			verification.setVerType(verType);
			verification.setOperator(operator);
			verification.setOperatUnit(operatUnit);
			verification.setVerDate(DateUtil.stringToDate(verDate, "yyyy-MM-dd"));
			verification.setValiDate(DateUtil.stringToDate(valiDate, "yyyy-MM-dd"));
			BaseService.getTblInstrumentVerificationService().update(verification);
			for(InstrumentVerificationInfo obj:data){
				if(obj.getId().equals(currentId)){
					int index=data.indexOf(obj);
					data.remove(index);
					data.add(index, new InstrumentVerificationInfo(verification.getId()+"",verification.getDictInstrument().getInstrumentId()+"",verification.getInstrumentName(),
							verification.getVerType(),verDate,valiDate,verification.getOperator(),verification.getOperatUnit()));
					table.getSelectionModel().clearAndSelect(index);
					break;
				}
			}
//			Alert.create("保存成功");
			Messager.showMessage("保存成功！");
		}
		
		//记录设备检定信息设置日志
		if(opearatTimes==0){
			TblLog tblLog = new TblLog();
			tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			tblLog.setOperatType("数据操作");
			tblLog.setOperatOject("设备检定信息设置");
//			User user = SignFrame.getUser();
			User user = SignFrame.getInstance().getSignUser();
			if(null!=user){
				tblLog.setOperator(user.getRealName());
			}
			tblLog.setOperatContent("");
			tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			BaseService.getTblLogService().save(tblLog);
			opearatTimes++;
		}
		
	}
	/**
	 * 返回按钮事件
	 * @author Administrator
	 *
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		TblLog tblLog = new TblLog();
		tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
		tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
		tblLog.setOperatType("退出面板");
		tblLog.setOperatOject("设备检定信息设置");
//		User user = SignFrame.getUser();
		User user = SignFrame.getInstance().getSignUser();
		if(null!=user){
			tblLog.setOperator(user.getRealName());
		}
		tblLog.setOperatContent("");
		tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
		BaseService.getTblLogService().save(tblLog);
	}

	//表格类
	public class InstrumentVerificationInfo{
		
		private SimpleStringProperty id = new SimpleStringProperty();                           //主键id，自增长
		private SimpleStringProperty dictInstrumentId=new SimpleStringProperty();    //设备
		private SimpleStringProperty instrumentName = new SimpleStringProperty();            //设备名称
		private SimpleStringProperty verType = new SimpleStringProperty();                   //检定类型
		private SimpleStringProperty verDate = new SimpleStringProperty();                     //检定日期
		private SimpleStringProperty valiDate = new SimpleStringProperty();                    //有效期
		private SimpleStringProperty operator = new SimpleStringProperty();                  //检定人
		private SimpleStringProperty operatUnit = new SimpleStringProperty(); //检定单位
		
		public InstrumentVerificationInfo(){}
		public InstrumentVerificationInfo(String id,String dictInstrumentId,String instrumentName,String verType,String verDate,String valiDate,String operator,String operatUnit){
			this.id =new SimpleStringProperty(id);
			this.dictInstrumentId=new SimpleStringProperty(dictInstrumentId);
			this.instrumentName=new SimpleStringProperty(instrumentName);
			this.verType=new SimpleStringProperty(verType);
			this.verDate=new SimpleStringProperty(verDate);
			this.valiDate=new SimpleStringProperty(valiDate);
			this.operator=new SimpleStringProperty(operator);
			this.operatUnit=new SimpleStringProperty(operatUnit);
		}
		
		
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getDictInstrumentId() {
			return dictInstrumentId.get();
		}
		public void setDictInstrumentId(String dictInstrumentId) {
			this.dictInstrumentId = new SimpleStringProperty(dictInstrumentId);
		}
		public String getInstrumentName() {
			return instrumentName.get();
		}
		public void setInstrumentName(String instrumentName) {
			this.instrumentName = new SimpleStringProperty(instrumentName);
		}
		public String getVerType() {
			return verType.get();
		}
		public void setVerType(String verType) {
			this.verType = new SimpleStringProperty(verType);
		}
		public String getVerDate() {
			return verDate.get();
		}
		public void setVerDate(String verDate) {
			this.verDate = new SimpleStringProperty(verDate);
		}
		public String getValiDate() {
			return valiDate.get();
		}
		public void setValiDate(String valiDate) {
			this.valiDate = new SimpleStringProperty(valiDate);
		}
		public String getOperator() {
			return operator.get();
		}
		public void setOperator(String operator) {
			this.operator = new SimpleStringProperty(operator);
		}
		public String getOperatUnit() {
			return operatUnit.get();
		}
		public void setOperatUnit(String operatUnit) {
			this.operatUnit = new SimpleStringProperty(operatUnit);
		}
		
		
	}
}
