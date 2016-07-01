package com.lanen.view.clinicaltest;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.DictComParam;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.util.DateUtil;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.SignFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class DictInstrumentFrameController implements Initializable {
	@FXML
	private TextField instrumentIdText;        //设备Id
	@FXML
	private TextField instrumentNameText;      //设备名称
	@FXML
	private TextField instrumentIdText2;        //设备Id
	@FXML
	private TextField instrumentNameText2;      //设备名称
	@FXML
	private ComboBox<String> instrumentTypeComboBox; //类别（检测项目）
	@FXML
	private TextField manufacturerText;        //生产厂家
	@FXML
	private TextField modelNumberText;         //型号
	@FXML
	private HBox createDateHBox;            //生产日期
	private DatePicker datePicker=null;   //日期选择器
	
	@FXML
	private TextField directorText;            //负责人
	
	@FXML
	private ComboBox<String> comPortComboBox;           //接入串口名称
	@FXML
	private ComboBox<String> baudRateComboBox;             //波特率
	@FXML
	private ComboBox<String> dataBitComboBox;              //数据位
	@FXML
	private ComboBox<String> stopBitComboBox;              //停止位
	@FXML
	private ComboBox<String> checkModeComboBox;           //校验位
	@FXML
	private CheckBox editCheckBox;           //编辑选中项
	@FXML
	private Button addBtn;           //增加  按钮
	@FXML
	private Button delBtn;          //删除  按钮
	@FXML
	private Button confirmBtn;           //应用  按钮
	@FXML
	private ListView<String> list;
	//列表框的值
	private ObservableList<String> data=FXCollections.observableArrayList();
	private Map<String,DictInstrument> map=new HashMap<String,DictInstrument>();
	private boolean isEditing=false;
	//数据操作次数
	private int opearatTimes =0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initList();
		initDatePane();
		opearatTimes=0;
	}

	private void initDatePane() {
		datePicker = new DatePicker(Locale.CHINA);
		datePicker.getTextField().setEditable(false);
		datePicker.getTextField().setMaxWidth(159);
		datePicker.getTextField().setMinWidth(159);
		datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		datePicker.getCalendarView().todayButtonTextProperty().set("今天");
		datePicker.getCalendarView().setShowWeeks(false);
		datePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		datePicker.setMaxWidth(159);
		createDateHBox.getChildren().add(datePicker);
	}

	/**
	 * 初始化列表框
	 */
	private void initList() {
		data.clear();
		map.clear();
		
		List<DictInstrument> dictInstrumentList=new ArrayList<DictInstrument>();
		dictInstrumentList=BaseService.getDictInstrumentService().getAll();
		if(null!=dictInstrumentList&&dictInstrumentList.size()>0){
			for(DictInstrument obj:dictInstrumentList){
				data.add(obj.getInstrumentId());
				map.put(obj.getInstrumentId(), obj);
			}
		}
		list.setItems(data);
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(isEditing){
					DictInstrument dictInstrument =map.get(newValue);
					if(null!=dictInstrument){
						instrumentIdText.setText(dictInstrument.getInstrumentId());
						instrumentIdText2.setText(dictInstrument.getInstrumentId());
						instrumentNameText.setText(dictInstrument.getInstrumentName());
						instrumentNameText2.setText(dictInstrument.getInstrumentName());
						instrumentTypeComboBox.getSelectionModel().clearAndSelect(dictInstrument.getInstrumentType()-1);
						manufacturerText.setText(dictInstrument.getManufacturer());        //生产厂家
						modelNumberText.setText(dictInstrument.getModelNumber());         //型号
						datePicker.getTextField().setText(DateUtil.dateToString(dictInstrument.getCreateDate(), "yyyy-MM-dd"));    //生产日期
						directorText.setText(dictInstrument.getDirector());            //负责人
						
						DictComParam dictComParam =dictInstrument.getDictComParam();
						if(null!=dictComParam){
							comPortComboBox.getSelectionModel().select(dictComParam.getComPort());           //接入串口名称
							baudRateComboBox.getSelectionModel().select(dictComParam.getBaudRate());             //波特率
							dataBitComboBox.getSelectionModel().select(dictComParam.getDataBit());              //数据位
							stopBitComboBox.getSelectionModel().select(dictComParam.getStopBit());              //停止位
							checkModeComboBox.getSelectionModel().select(dictComParam.getCheckMode());           //校验方式
						}else{
							  comPortComboBox.getSelectionModel().select("");        //接入串口名称
							 baudRateComboBox.getSelectionModel().select("");            //波特率
							  dataBitComboBox.getSelectionModel().select("");            //数据位
							  stopBitComboBox.getSelectionModel().select("");           //停止位
							checkModeComboBox.getSelectionModel().select("");           //校验方式
						}
						
					}else{
						instrumentIdText.setText("");        //设备Id
						 instrumentNameText.setText("");      //设备名称
						 instrumentTypeComboBox.getSelectionModel().clearSelection(); //类别（检测项目）
						 manufacturerText.setText("");        //生产厂家
						 modelNumberText.setText("");         //型号
						 datePicker.getTextField().setText("");			//生产日期
						 directorText.setText("");  //负责人
						 
						 instrumentNameText2.setText("");      //设备名称
						 instrumentIdText2.setText("");        //设备Id
						  comPortComboBox.getSelectionModel().select("");       //接入串口名称
							 baudRateComboBox.getSelectionModel().select("");             //波特率
							  dataBitComboBox.getSelectionModel().select("");          //数据位
							  stopBitComboBox.getSelectionModel().select("");            //停止位
							checkModeComboBox.getSelectionModel().select(""); 
					}
				}
			}
			
		});
	}

	/**
	 * 多选框事件
	 * @param event
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event){
		CheckBox checkBox=(CheckBox) event.getSource();
		if(checkBox.isSelected()){
			isEditing=true;
			addBtn.setText("保存");
			delBtn.setDisable(false);
			instrumentIdText.setEditable(false);        //设备Id
			instrumentNameText.setEditable(false);      //设备名称
			instrumentNameText2.setEditable(false);      //设备名称
			instrumentIdText2.setEditable(false); //设备Id
			
				  comPortComboBox.setDisable(false);            //接入串口名称
				 baudRateComboBox.setDisable(false);          //波特率
				  dataBitComboBox.setDisable(false);           //数据位
				  stopBitComboBox.setDisable(false);           //停止位
				checkModeComboBox.setDisable(false);          //校验方式
			String selectedItem =list.getSelectionModel().getSelectedItem();
			if(null!=selectedItem&&!"".equals(selectedItem)){//list有被选中
				DictInstrument dictInstrument =map.get(selectedItem);
				if(null!=dictInstrument){
					instrumentIdText.setText(dictInstrument.getInstrumentId());
					instrumentIdText2.setText(dictInstrument.getInstrumentId());
					instrumentNameText.setText(dictInstrument.getInstrumentName());
					instrumentNameText2.setText(dictInstrument.getInstrumentName());
					instrumentTypeComboBox.getSelectionModel().clearAndSelect(dictInstrument.getInstrumentType()-1);
					manufacturerText.setText(dictInstrument.getManufacturer());        //生产厂家
					modelNumberText.setText(dictInstrument.getModelNumber());         //型号
					datePicker.getTextField().setText( DateUtil.dateToString(dictInstrument.getCreateDate(), "yyyy-MM-dd"));       //生产日期
					directorText.setText(dictInstrument.getDirector());            //负责人
					
					DictComParam dictComParam =dictInstrument.getDictComParam();
					if(null!=dictComParam){
						comPortComboBox.getSelectionModel().select(dictComParam.getComPort());           //接入串口名称
						baudRateComboBox.getSelectionModel().select(dictComParam.getBaudRate());             //波特率
						dataBitComboBox.getSelectionModel().select(dictComParam.getDataBit());              //数据位
						stopBitComboBox.getSelectionModel().select(dictComParam.getStopBit());              //停止位
						checkModeComboBox.getSelectionModel().select(dictComParam.getCheckMode());           //校验方式
					}
					
				}
			}else{
				instrumentIdText.setText("");        //设备Id
				 instrumentNameText.setText("");      //设备名称
				 instrumentTypeComboBox.getSelectionModel().clearSelection(); //类别（检测项目）
				 manufacturerText.setText("");        //生产厂家
				 modelNumberText.setText("");         //型号
				 datePicker.getTextField().setText("");			//生产日期
				 directorText.setText("");  //负责人
				 
				 instrumentNameText2.setText("");      //设备名称
				 instrumentIdText2.setText("");        //设备Id
				  comPortComboBox.getSelectionModel().select("");       //接入串口名称
					 baudRateComboBox.getSelectionModel().select("");             //波特率
					  dataBitComboBox.getSelectionModel().select("");          //数据位
					  stopBitComboBox.getSelectionModel().select("");            //停止位
					checkModeComboBox.getSelectionModel().select("");   
				
			}
		}else{
			isEditing=false;
			 instrumentIdText.setEditable(true);        //设备Id
			 instrumentNameText.setEditable(true);      //设备名称
			
			 instrumentIdText.setText("");        //设备Id
			 instrumentNameText.setText("");      //设备名称
			 instrumentTypeComboBox.getSelectionModel().clearSelection(); //类别（检测项目）
			 manufacturerText.setText("");        //生产厂家
			 modelNumberText.setText("");         //型号
			 datePicker.getTextField().setText("");            //生产日期
			 directorText.setText("");  //负责人
			 addBtn.setText("添加");
			 delBtn.setDisable(true);
			 instrumentNameText2.setEditable(false);      //设备名称
			 instrumentIdText2.setEditable(false);        //设备Id
			  comPortComboBox.setDisable(true);            //接入串口名称
				 baudRateComboBox.setDisable(true);          //波特率
				  dataBitComboBox.setDisable(true);           //数据位
				  stopBitComboBox.setDisable(true);           //停止位
				checkModeComboBox.setDisable(true);          //校验方式
			 
			 instrumentNameText2.setText("");      //设备名称
			 instrumentIdText2.setText("");        //设备Id
			 comPortComboBox.getSelectionModel().clearSelection();        //接入串口名称
			 baudRateComboBox.getSelectionModel().clearSelection();            //波特率
			  dataBitComboBox.getSelectionModel().clearSelection();            //数据位
			  stopBitComboBox.getSelectionModel().clearSelection();           //停止位
			checkModeComboBox.getSelectionModel().clearSelection();           //校验方式
			
		}
	}
	/**
	 * 退出按钮事件
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		//记录设备登记日志
		TblLog tblLog = new TblLog();
		tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
		tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
		tblLog.setOperatType("退出面板");
		tblLog.setOperatOject("设备登记");
//		User user = SignFrame.getUser();
		User user = SignFrame.getInstance().getSignUser();
		if(null!=user){
			tblLog.setOperator(user.getRealName());
		}
		tblLog.setOperatContent("");
		tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
		BaseService.getTblLogService().save(tblLog);
	}
    
	/**
	 * 增加按钮事件
	 * @param event
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event){
		String instrumentId =instrumentIdText.getText();        //设备Id
		String instrumentName=instrumentNameText.getText();      //设备名称
		String  instrumentType =instrumentTypeComboBox.getSelectionModel().getSelectedItem(); //类别（检测项目）
		String manufacturer=manufacturerText.getText();        //生产厂家
		String modelNumber=modelNumberText.getText();         //型号
		String createDate=datePicker.getTextField().getText();            //生产日期
		String director=directorText.getText();            //负责人
		if(instrumentId.equals("")){
//			Alert.create("设备Id不能为空");
			Messager.showWarnMessage("设备Id不能为空！");
			return ;
		}else {
			if(!isEditing){//非编辑状态下 ，判断设备Id是否存在
				boolean isExist = BaseService.getDictInstrumentService().isExistByInstrumentId(instrumentId);
				if(isExist){
//					Alert.create("设备Id已存在");
					Messager.showWarnMessage("设备Id已存在！");
					return ;
				}
			}
		}
		if(instrumentName.equals("")){
//			Alert.create("设备名称不能为空");
			Messager.showWarnMessage("设备名称不能为空！");
			return ;
		}else if(null==instrumentType||instrumentType.equals("")||manufacturer.equals("")||modelNumber.equals("")||createDate.equals("")||director.equals("")){
//			Alert.create("信息填写不全");
			Messager.showWarnMessage("信息填写不全！");
			return ;
		}
		if(!isEditing){//非编辑状态，
			DictInstrument entity=new DictInstrument();
			entity.setInstrumentId(instrumentId);
			entity.setInstrumentName(instrumentName);
			entity.setInstrumentType(instrumentTypeComboBox.getSelectionModel().getSelectedIndex()+1);
			entity.setManufacturer(manufacturer);
			entity.setModelNumber(modelNumber);
			entity.setCreateDate(DateUtil.stringToDate(createDate, "yyyy-MM-dd"));
			entity.setDirector(director);
			BaseService.getDictInstrumentService().save(entity);
			data.add(instrumentId);
			map.put(instrumentId, entity);
//			Alert.create("添加成功");
			Messager.showMessage("添加成功！");
			
			instrumentIdText.setText("");        //设备Id
			 instrumentNameText.setText("");      //设备名称
			 instrumentTypeComboBox.getSelectionModel().clearSelection(); //类别（检测项目）
			 manufacturerText.setText("");        //生产厂家
			 modelNumberText.setText("");         //型号
			 datePicker.getTextField().setText("");            //生产日期
			 directorText.setText("");  //负责人
		}else{//编辑状态，
			DictInstrument entity=BaseService.getDictInstrumentService().getById(instrumentId);
			entity.setInstrumentId(instrumentId);
			entity.setInstrumentName(instrumentName);
			entity.setInstrumentType(instrumentTypeComboBox.getSelectionModel().getSelectedIndex()+1);
			entity.setManufacturer(manufacturer);
			entity.setModelNumber(modelNumber);
			entity.setCreateDate(DateUtil.stringToDate(createDate, "yyyy-MM-dd"));
			entity.setDirector(director);
			//更新实体
			BaseService.getDictInstrumentService().update(entity);
			//更新map里数据
			map.put(instrumentId, entity);
//			Alert.create("保存成功");
			Messager.showMessage("保存成功！");
		}
		 if(opearatTimes==0){
			  //记录设备登记日志
			  TblLog tblLog = new TblLog();
			  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			  tblLog.setOperatType("数据操作");
			  tblLog.setOperatOject("设备登记");
//			  User user = SignFrame.getUser();
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
	 * 应用按钮事件
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String instrumentId =instrumentIdText2.getText();        //设备Id
		String instrumentName=instrumentNameText2.getText();      //设备名称
		String comPort=comPortComboBox.getSelectionModel().getSelectedItem();           //接入串口名称
		String baudRateString=baudRateComboBox.getSelectionModel().getSelectedItem();             //波特率
		String dataBitString=dataBitComboBox.getSelectionModel().getSelectedItem();              //数据位
		String stopBitString=stopBitComboBox.getSelectionModel().getSelectedItem();              //停止位
		String checkModeString=checkModeComboBox.getSelectionModel().getSelectedItem();          //校验方式
		if(instrumentId.equals("")||instrumentName.equals("")){
//			Alert.create("请选择要编辑的设备");
			Messager.showWarnMessage("请选择要编辑的设备！");
			return ;
		}
		if(null==comPort||null==baudRateString||null==dataBitString||null==stopBitString||null==checkModeString||comPort.equals("")||baudRateString.equals("")||dataBitString.equals("")||stopBitString.equals("")||checkModeString.equals("")){
//			Alert.create("信息填写不全");
			Messager.showWarnMessage("信息填写不全！");
			return ;
		}else{
			DictInstrument obj  =map.get(instrumentId);
			DictComParam dictComParam=obj.getDictComParam();
			if(null==dictComParam){
				dictComParam=new DictComParam();
			}
				dictComParam.setDictInstrument(obj);
				dictComParam.setInstrumentName(obj.getInstrumentName());
				dictComParam.setComPort(comPort);
				dictComParam.setBaudRate(baudRateString);
				dictComParam.setDataBit(dataBitString);
				dictComParam.setStopBit(stopBitString);
				dictComParam.setCheckMode(checkModeString);
				obj.setDictComParam(dictComParam);
			    BaseService.getDictInstrumentService().update(obj);
			    
			    DictInstrument dictInstrument=BaseService.getDictInstrumentService().getById(instrumentId);
			    map.put(instrumentId, dictInstrument);
//			    Alert.create("保存成功");
			    Messager.showMessage("保存成功！");
			  if(opearatTimes==0){
				  //记录设备登记日志
				  TblLog tblLog = new TblLog();
				  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
				  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
				  tblLog.setOperatType("数据操作");
				  tblLog.setOperatOject("设备登记");
//				  User user = SignFrame.getUser();
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
		
	}
	/**
	 * 接口参数，文本框在非编辑状态下
	 * @param event
	 */
	@FXML
	private void onTextAction(MouseEvent event){
		if(!isEditing){
//			Alert.create("当前非编辑状态");
			Messager.showWarnMessage("当前非编辑状态！");
		}
	}
	/**
	 * 删除按钮事件
	 */
	@FXML
	private void OnDelBtnAction(ActionEvent event){
		String instrumentId=list.getSelectionModel().getSelectedItem();
		if(null!=instrumentId&&!"".equals(instrumentId)){
//			if(Confirm.create(MainFrame.mainWidow,"设备删除", "如果删除设备，将同时删除通道号及串口设置", "确定删除吗？")){
			if(Messager.showConfirm("设备删除", "如果删除设备，将同时删除通道号及串口设置", "确定删除吗？")){
			DictInstrument dictInstrument =map.get(instrumentId);
				//删除检定信息
				BaseService.getTblInstrumentVerificationService().deleteByDictInstrument(dictInstrument);
				//产出设备的通道号设置
				BaseService.getPassagewayService().deleteByDictInstrument(dictInstrument);
				//删除设备
				BaseService.getDictInstrumentService().delete(instrumentId);
				
				//更新listView
				initList();
//				Alert.create("删除成功");	
				Messager.showMessage("删除成功！");
				 if(opearatTimes==0){
					  //记录设备登记日志
					  TblLog tblLog = new TblLog();
					  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
					  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
					  tblLog.setOperatType("数据操作");
					  tblLog.setOperatOject("设备登记");
//					  User user = SignFrame.getUser();
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
			
		}else{
//			Alert.create("请先选择设备Id");
			Messager.showWarnMessage("请先选择设备Id！");
			return;
		}
	}
}
