package com.lanen.view.tblsession;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.TblBal;
import com.lanen.model.quarantine.tblsession.TblBodyWeight;
import com.lanen.model.quarantine.tblsession.TblSession;
import com.lanen.model.quarantine.tblsession.TblSessionAnimals;
import com.lanen.model.tableview.TblBodyWeightForTableView;
import com.lanen.model.tableview.TblTraceForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

public class BodyWeightFrame extends Application implements Initializable , Runnable{

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
	@FXML//采集方式
	private ComboBox<String> collectComboBox;   
	
	@FXML
	private Label weightUnitLabel;
	@FXML
	private Label balIdLabel;
	@FXML
	private ComboBox<String> balIdComboBox;
	@FXML
	private Label weightLabel;
	@FXML
	private ComboBox<String> weightUnitComboBox;
	@FXML
	private TextField weightText;
	@FXML
	private Button signinButton;   //录入
	
	@FXML
	private static TableView<TblBodyWeightForTableView> tblBodyWeightTable;
	private static ObservableList<TblBodyWeightForTableView> data_tblBodyWeightTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblBodyWeightForTableView,String> animalIdCol;
	@FXML
	private TableColumn<TblBodyWeightForTableView,String> genderCol;
	@FXML
	private TableColumn<TblBodyWeightForTableView,String> bodyWeightCol;
	@FXML
	private TableColumn<TblBodyWeightForTableView,String> weightUnitCol;
	@FXML
	private TableColumn<TblBodyWeightForTableView,String> weightTimeCol;
	
	
	@FXML//待称重动物Id号
	private Label currentAnimalIdLabel;
	@FXML
	private static Label collectAnimalIdLabel;
	@FXML
	private static Label collectWeightLabel;
	
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
	private TableColumn<TblTraceForTableView,String> oldValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> newValueCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> operatorCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyReasonCol_2;
	@FXML
	private TableColumn<TblTraceForTableView,String> modifyTimeCol_2;
	
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
//	public  CommPortIdentifier portId;  
	private List<CommPortIdentifier> portIdList = new ArrayList<CommPortIdentifier>();
	//Enumeration 为枚举型类,在util中 
	public  Enumeration<?> portList;
	//RS-232的串行口 *
//	public static SerialPort serialPort =null;
	private List<SerialPort> serialPortList = new ArrayList<SerialPort>();
	//线程
	private Thread readThread;
	private static boolean isOpening =false;
	private String devIds ="";//连接成功的设备Id
	private  int baudRateInt=0;
	private  int dataBitInt=0;
	private  int stopBitInt=0;
	private  int checkModeInt=0;
	
	/**
     * 以上为  串口 通讯 用   
     */
	
	private static int currentIndex = -1;   //tblBodyWeightTable当前行索引
	private static TblSession tblSession = null;
	private static String sessionId ="";
	private static List<TblSessionAnimals> tblSessionAnimalsList =null;
	private static String signId ="";
	private static String checkId="";
	private static Date beginDate =null;   //开始采集时间
	/**离开页面时，是否需要更新DayToDayPane上tblSession表数据*/
	private static boolean updateFlag  = false;//离开页面时，是否需要更新DayToDayPane上tblSession表数据
	
	private String hostName ;
	private static BodyWeightFrame instance;
	public static BodyWeightFrame getInstance(){
		return instance;
	}
	public BodyWeightFrame(){
		updateFlag = false;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		hostName = SystemTool.getHostName();
		//不显示手动录入相关控件
		showControl(false);
		//
		initBalIdComboBox();
		//初始化collectComboBox
		initCollectComboBox();
		//初始化tblBodyWeightTable
		initTblBodyWeight();
		//
		ininTblTraceTalbe();
	}
	
	/**
	 * 初始化称重设备列表
	 */
	private void initBalIdComboBox() {
		balIdComboBox.getItems().clear();
//		new BaseService();
		List<TblBal> list = BaseService.getTblBalService().findAllOrderByOrderNo();
		if(null!= list && list.size()>0){
			for(TblBal tblBal:list){
				balIdComboBox.getItems().add(tblBal.getBalId());
			}
		}
	}

	/**
	 * 初始化tblBodyWeightTable
	 */
	private void initTblBodyWeight() {
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblBodyWeightForTableView, String>("animalId"));
		genderCol.setCellValueFactory(new PropertyValueFactory<TblBodyWeightForTableView, String>("gender"));
		bodyWeightCol.setCellValueFactory(new PropertyValueFactory<TblBodyWeightForTableView, String>("bodyWeight"));
		weightUnitCol.setCellValueFactory(new PropertyValueFactory<TblBodyWeightForTableView, String>("weightUnit"));
		weightTimeCol.setCellValueFactory(new PropertyValueFactory<TblBodyWeightForTableView, String>("weightTime"));
		tblBodyWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tblBodyWeightTable.setItems(data_tblBodyWeightTable);
//		data_tblBodyWeightTable.add(new TblBodyWeightForTableView("12003",1,"56.70","kg",new Date()));
		tblBodyWeightTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				currentIndex =newValue.intValue();
				if(currentIndex>-1){
					//待称重  动物Id号   
					currentAnimalIdLabel.setText(data_tblBodyWeightTable.get(currentIndex).getAnimalId());
				}else{
					//待称重  动物Id号 =""
					currentAnimalIdLabel.setText("");
				}
				
			}});
	}
	/**
	 * 初始化tblTraceTalbe
	 */
	private void ininTblTraceTalbe() {
		animalIdCol_2.setCellValueFactory(
				new PropertyValueFactory<TblTraceForTableView, String>("animalId"));
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
	 * 初始化collectComboBox 采集方式
	 */
	private void initCollectComboBox() {
		collectComboBox.getSelectionModel().selectedIndexProperty()//
		.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(newValue.intValue() ==1 ){
					//如果串口打开中，则关闭 
					//关闭端口
					closePort();
					showControl(true);
					tblBodyWeightTable.scrollTo(currentIndex);
				}else if(newValue.intValue() ==0 ){
					showControl(false);
					tblBodyWeightTable.scrollTo(currentIndex);
					//1.查询数据库，获得连接本机的设备
					List<?> list = BaseService.getTblHostBalService().findListByHostNameEnable(hostName,1);
					if(null!=list && list.size()>0){
						for(int i = 0;i<list.size();i++){
							//打开串口
							openPort((Object[]) list.get(i));
						}
						if(isOpening){
							Alert.create("设备:"+devIds +"连接成功！");
						}
					}else{
						Alert2.create("请先设置本机连接的设备");
					}
					//2.打开串口
				}else{
					//如果串口打开中，则关闭 
					//关闭端口
					closePort();
					//不显示，
					showControl(false);
				}
			}

			});
		
	}
	/**
	 * 打开串口 
	 * @param object
	 */
	private void openPort(Object[] obj) {
		String balId = (String) obj[0];
		String comPort = (String) obj[1];
		String baudRate = (String) obj[2];
		String dataBit = (String) obj[3];
		String stopBit = (String) obj[4];
		String checkMode = (String) obj[5];
		
		//获取系统中所有的通讯端口
		portList=CommPortIdentifier.getPortIdentifiers();
		
		boolean isExistComPort=false;   //设置的端口是否存在
		while(portList.hasMoreElements()){
			SerialPort serialPort =null;
			
			//强制转换为通讯端口类型
			CommPortIdentifier portId=(CommPortIdentifier) portList.nextElement();
			if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
				if(portId.getName().equals(comPort)){//判断串口名是否相同
					isExistComPort=true;//串口存在
					try {
						if(portId.isCurrentlyOwned()){
							Alert2.create("设备'"+balId+"'端口"+comPort+"已经被占用!");
							return;
						}
						serialPort = (SerialPort) portId.open(balId, 2000);//打开端口
						
						portIdList.add(portId);	//留备份，关闭用
						serialPortList.add(serialPort);
					} catch (PortInUseException  e1) {
						Alert2.create("设备'"+balId+"'端口"+comPort+"已经被占用!");
						e1.printStackTrace();
						return;
					}
					//设置串口监听器
					try {
							serialPort.addEventListener(new SerialPortEventListener_bodyWeight());
						
					} catch (TooManyListenersException e1) {
						serialPort.close(); 
					}
					//侦听到串口有数据,触发串口事件
					serialPort.notifyOnDataAvailable(true);  
					isOpening =true;
					devIds =devIds+" "+balId;
					try {
//						String baudRate="";             //波特率   
//						String dataBit="";              //数据位   5 6 7 8
//						String stopBit="";              //停止位    1,1.5,2
//						String checkMode="";            //校验位    None，Even，Odd，Space，Mark
						baudRateInt=Integer.parseInt(baudRate);
						dataBitInt=Integer.parseInt(dataBit);
						stopBitInt=0;
						if(stopBit.equals("1")){
							stopBitInt=1;
						}else if(stopBit.equals("1.5")){
							stopBitInt=3;
						}else{
							stopBitInt=2;
						}
						checkModeInt=0;
						if(checkMode.equals("None")){
							checkModeInt=0;
						}else if(checkMode.equals("Odd")){
							checkModeInt=1;
						}else{
							checkModeInt=2;
						}
						/*设置串口初始化参数，依次是波特率，数据位，停止位和校验*/ 
//						serialPort.setSerialPortParams(9600, 
//								SerialPort.DATABITS_8, 
//								SerialPort.STOPBITS_1, 
//								SerialPort.PARITY_NONE);
						serialPort.setSerialPortParams(baudRateInt, 
								dataBitInt, 
								stopBitInt, 
								checkModeInt);
					} catch (UnsupportedCommOperationException e1) {
						e1.printStackTrace();
					}
					
				}//if end;
				//已经找到端口，退出while循环
				if(isExistComPort){
					break;
				}
			}//if end;
		}//while end;
		if(!isExistComPort){
			Alert2.create("设备'"+balId+"'端口"+comPort+"不存在");
			return;
		}else{
			isExistComPort=false;
		}
		readThread=new Thread(this);
		readThread.start();//线程负责每接收一次数据休眠2秒钟
	}

	/**
	 * 关闭端口
	 */
	private void closePort(){
		int i =0;
		for(CommPortIdentifier portId:portIdList){
			if(portId != null &&portId.isCurrentlyOwned()){
				//关闭关闭端口连接
				serialPortList.get(i).removeEventListener();
				serialPortList.get(i).notifyOnDataAvailable(false); 
				serialPortList.get(i).close();
				isOpening =false;
				devIds="";
			}
			i++;
		}
		portIdList.clear();
		serialPortList.clear();
//		if(portId != null &&portId.isCurrentlyOwned()){
//			//关闭关闭端口连接
//			serialPort.removeEventListener();
//			serialPort.notifyOnDataAvailable(false); 
//			serialPort.close();
//			isOpening =false;
//			devIds="";
//		}
	}
	/**
	 * 保存体重数据，更新表格数据，下移，更新其他控件数据(串口通讯时用)
	 */
	public static void saveOrUpdateTblBodyWeight(TblBodyWeight tblBodyWeight) {
		if (signId == null || "".equals(signId)) {
			// .未签字
			//1.5 如果无 sessionId ,则先保存 会话和会话动物列表
			if(null == sessionId || "".equals(sessionId)){
				sessionId = BaseService.getTblSessionService().
						saveTblSessionAndAnimalList(tblSession, tblSessionAnimalsList);
				tblSession.setSessionId(sessionId);
			}
			
			//保存
			TblBodyWeightForTableView selectItem = data_tblBodyWeightTable.get(currentIndex);
			String animalId = selectItem.getAnimalId();
			int gender = selectItem.getGender().equals("♂") ? 1 : 2;
			tblBodyWeight.setSessionId(sessionId);
			tblBodyWeight.setAnimalId(animalId);
			tblBodyWeight.setGender(gender);
			Date date = new Date();
			tblBodyWeight.setWeightTime(date);
			tblBodyWeight.setTblSession(tblSession);

			String weight = tblBodyWeight.getBodyWeight();
			String weightUnit = tblBodyWeight.getWeightUnit();

			BaseService.getTblBodyWeightService().saveOrUpdateNoId(tblBodyWeight);

			// 3.如果是第一个称重动物， 更新会话里的beginDate
			if (null == beginDate) {
				beginDate = date;
				tblSession.setBeginTime(beginDate);
				BaseService.getTblSessionService().update(tblSession);
				updateFlag = true;
			}

			// 4.保存成功后，选中下一行，或清空，
			// 采集到数据
			collectAnimalIdLabel.setText(animalId);
			collectWeightLabel.setText(weight+" "+weightUnit);
			// 更换表格中数据
			TblBodyWeightForTableView tblBodyWeightForTableView = new TblBodyWeightForTableView(
					animalId, gender, weight, weightUnit, date);
			data_tblBodyWeightTable.set(currentIndex, tblBodyWeightForTableView);
			// 选中下一个
			tblBodyWeightTable.getSelectionModel().selectNext();
			tblBodyWeightTable.scrollTo(currentIndex);
		} else if (checkId == null || "".equals(checkId)) {
			// .已签字，未复核
			
			//1.提示，并电子签名
			if(Confirm.create(Main.getInstance().getStage(), 
					"提示", "称重信息已签字，编辑信息将记录修改痕迹", "确定继续吗？")){
				//电子签名及原因
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
				//2.保存修改痕迹
				TblBodyWeightForTableView selectItem = data_tblBodyWeightTable.get(currentIndex);
				String animalId = selectItem.getAnimalId();
				int gender = selectItem.getGender().equals("♂") ? 1 : 2;
				Date date = new Date();
				String weight = tblBodyWeight.getBodyWeight();
				String weightUnit = tblBodyWeight.getWeightUnit();

				String oldValue =selectItem.getBodyWeight()+" "+selectItem.getWeightUnit();
				//动物Id号+“，”+体重+“ ”+体重单位
				String newValue =animalId+","+tblBodyWeight.getBodyWeight()
						+" "+tblBodyWeight.getWeightUnit();
				TblTrace tblTrace = new TblTrace();
				tblTrace.setTableName("TblBodyWeight");//表名
				tblTrace.setDataId(sessionId); //这里放的是会话Id
				tblTrace.setOperateMode(1);//修改
				tblTrace.setOldValue(oldValue);
				tblTrace.setNewValue(newValue);
				tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
				tblTrace.setHost(SystemTool.getIPAddress());
				tblTrace.setModifyReason(SignFrameWithReason.getReason());
				tblTrace .setModifyTime(date);
				BaseService.getTblTraceservice().save(tblTrace);
				//4.更新数据()
				tblBodyWeight.setSessionId(sessionId);
				tblBodyWeight.setAnimalId(animalId);
				tblBodyWeight.setWeightTime(date);
				BaseService.getTblBodyWeightService().saveOrUpdateNoId(tblBodyWeight);
				//5.保存成功后，选中下一行
				//采集到数据
				collectAnimalIdLabel.setText(animalId);
				collectWeightLabel.setText(weight+" "+weightUnit);
				//更换表格中数据
				TblBodyWeightForTableView tblBodyWeightForTableView = new TblBodyWeightForTableView(
						animalId,gender,weight,weightUnit,date
						);
				data_tblBodyWeightTable.set(currentIndex, tblBodyWeightForTableView);
				//选中下一个
				tblBodyWeightTable.getSelectionModel().selectNext();
				//scroll
				tblBodyWeightTable.scrollTo(currentIndex);
				
				
				//5.  更新修改痕迹表格
				if (null != tblTrace) {
					String[] newValues = newValue.split(",");
					data_tblTraceTable.add(new TblTraceForTableView(newValues[0], tblTrace.getOldValue(),
							newValues[1], tblTrace.getOperator(), tblTrace.getModifyReason(), tblTrace
									.getModifyTime(),1));
				}
			}
		} else {
			Alert2.create("已复核，无法修改数据");
			return;
			
		}
	}
	/**
	 * 是否显示   手动录入相关控件
	 * @param isShow
	 */
	private void showControl(boolean isShow){
		balIdLabel.setVisible(isShow);
		balIdComboBox.setVisible(isShow);
		weightUnitLabel.setVisible(isShow);
		weightLabel.setVisible(isShow);
		weightUnitComboBox.setVisible(isShow);
		weightText.setVisible(isShow);
		signinButton.setVisible(isShow);
		if(isShow){
			weightText.clear();
		}
	}
	/**
	 * 加载tblBodyWeightTable数据并选中待录入行
	 */
	private void loadData_tblBodyWeightTable(){
		data_tblBodyWeightTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			List<?> list = BaseService.getTblBodyWeightService().getListBySessionId(sessionId);
			if(null != list && list.size()>0){
				int index =-1;
				String animalId = "";
				int gender = 0;
				String weight ="";
				String weightUnit ="";
				Date weightTime;
				for(int i = 0; i < list.size();i++){ 
					Object[] object = (Object[])list.get(i);
					animalId =(String) object[0];
					gender = (int) object[1];
					weight = (String) object[2];
					weightUnit = (String) object[3];
					weightTime = (Date) object[4];
					data_tblBodyWeightTable.add(new TblBodyWeightForTableView(
							animalId,gender,weight,weightUnit,weightTime));
					if(index == -1 && (null == weight || "".equals(weight))){
						index =i;
					}
				}
				tblBodyWeightTable.setItems(data_tblBodyWeightTable);
				if(index>-1){
					tblBodyWeightTable.getSelectionModel().select(index);
				}else{
					tblBodyWeightTable.getSelectionModel().select(0);
				}
			}
		}
	}
	/**
	 * 加载tblBodyWeightTable数据并选中第一行（暂无sessionId时用）
	 */
	private void loadData_tblBodyWeightTable(List<TblSessionAnimals> tblSessionAnimalsList){
		data_tblBodyWeightTable.clear();
			if(null != tblSessionAnimalsList && tblSessionAnimalsList.size()>0){
				String animalId = "";
				int gender = 0;
				for(TblSessionAnimals obj:tblSessionAnimalsList){ 
					animalId =obj.getAnimalId();
					gender = obj.getGender();
					data_tblBodyWeightTable.add(new TblBodyWeightForTableView(
							animalId,gender,"","",null));
				}
				tblBodyWeightTable.setItems(data_tblBodyWeightTable);
				
				tblBodyWeightTable.getSelectionModel().select(0);
			}
	}
	
	/**
	 * 更新TblTraceTable数据
	 */
	private void updateTblTraceTable(){
		data_tblTraceTable.clear();
		if(null!=sessionId && !sessionId.isEmpty()){
			List<TblTrace> tblTraceList = BaseService.getTblTraceservice().
					getListByTableNameAndDataId("TblBodyWeight",sessionId);
			if(null != tblTraceList && tblTraceList.size()>0){
				for(TblTrace tblTrace : tblTraceList){
					String newValue = tblTrace.getNewValue();
					String[] newValues = newValue.split(",");
					data_tblTraceTable.add(new TblTraceForTableView(
							newValues[0],tblTrace.getOldValue(),newValues[1],
							tblTrace.getOperator(),
							tblTrace.getModifyReason(),tblTrace.getModifyTime(),
							tblTrace.getOperateMode()
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
			data_tblTraceTable.add(new TblTraceForTableView(newValues[0], tblTrace.getOldValue(),
					newValues[1], tblTrace.getOperator(), tblTrace.getModifyReason(), tblTrace
							.getModifyTime(),tblTrace.getOperateMode()));
		}
	}
	/**
	 * 录入
	 */
	@FXML
	private void onSigninButton(){
		if(signId==null || "".equals(signId)){
			//.未签字
			//1.检查输入项
			String devId = balIdComboBox.getSelectionModel().getSelectedItem();
			if(null == devId ){
				Alert2.create("请先选择称重设备");
				return;
			}
			String weightUnit = weightUnitComboBox.getSelectionModel().getSelectedItem();
			if(null == weightUnit ){
				Alert2.create("请先选择体重单位");
				return;
			}
			String weight = weightText.getText().trim();
			if(weight.equals("")){
				Alert2.create("请先填写体重");
				weightText.requestFocus();
				return;
			}
			if(weight.length()>20){
				Alert2.create("'体重'长度不能超过20");
				weightText.requestFocus();
				return;
			}
			if(!NumberValidationUtils.isPositiveRealNumber(weight)){
				Alert2.create("'体重'格式不正确");
				weightText.requestFocus();
				return;
			}
			//1.5 如果无 sessionId ,则先保存 会话和会话动物列表
			if(null == sessionId || "".equals(sessionId)){
				sessionId = BaseService.getTblSessionService().
						saveTblSessionAndAnimalList(tblSession, tblSessionAnimalsList);
				tblSession.setSessionId(sessionId);
			}
			//2.保存体重数据
			TblBodyWeight tblBodyWeight = new TblBodyWeight();
			TblBodyWeightForTableView selectItem = data_tblBodyWeightTable.get(currentIndex);
			String animalId = selectItem.getAnimalId();
			int gender = selectItem.getGender().equals("♂") ? 1 : 2;
			tblBodyWeight.setSessionId(sessionId);
			tblBodyWeight.setAnimalId(animalId);
			tblBodyWeight.setGender(gender);
			tblBodyWeight.setBodyWeight(weight);
			tblBodyWeight.setDevId(devId);
			tblBodyWeight.setWeightUnit(weightUnit);
			Date date = new Date();
			tblBodyWeight.setWeightTime(date);
			tblBodyWeight.setTblSession(tblSession);
			BaseService.getTblBodyWeightService().saveOrUpdateNoId(tblBodyWeight);
			
			//3.如果是第一个称重动物，  更新会话里的beginDate
			if(null == beginDate){
				beginDate =date;
				tblSession.setBeginTime(beginDate);
				BaseService.getTblSessionService().update(tblSession);
				updateFlag = true;
			}
			
			//4.保存成功后，选中下一行
			//采集到数据
			collectAnimalIdLabel.setText(animalId);
			collectWeightLabel.setText(weight+" "+weightUnit);
			//更换表格中数据
			TblBodyWeightForTableView tblBodyWeightForTableView = new TblBodyWeightForTableView(
					animalId,gender,weight,weightUnit,date
					);
			data_tblBodyWeightTable.set(currentIndex, tblBodyWeightForTableView);
			//选中下一个
			tblBodyWeightTable.getSelectionModel().selectNext();
			
			tblBodyWeightTable.scrollTo(currentIndex);
			
			weightText.clear();
			weightText.requestFocus();
		}else if(checkId==null || "".equals(checkId)){
			//.已签字，未复核
			
			//1.检查输入项
			String devId = balIdComboBox.getSelectionModel().getSelectedItem();
			if(null == devId ){
				Alert2.create("请先选择称重设备");
				return;
			}
			String weightUnit = weightUnitComboBox.getSelectionModel().getSelectedItem();
			if(null == weightUnit ){
				Alert2.create("请先选择体重单位");
				return;
			}
			String weight = weightText.getText().trim();
			if(weight.equals("")){
				Alert2.create("请先填写体重");
				weightText.requestFocus();
				return;
			}
			if(weight.length()>20){
				Alert2.create("'体重'长度不能超过20");
				weightText.requestFocus();
				return;
			}
			if(!NumberValidationUtils.isPositiveRealNumber(weight)){
				Alert2.create("'体重'格式不正确");
				weightText.requestFocus();
				return;
			}
			//2.提示，并电子签名
			if(Confirm.create(Main.getInstance().getStage(), 
					"提示", "称重信息已签字，编辑信息将记录修改痕迹", "确定继续吗？")){
				//电子签名及原因
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
				//3.保存修改痕迹
				TblBodyWeightForTableView selectItem = data_tblBodyWeightTable.get(currentIndex);
				String animalId = selectItem.getAnimalId();
				String oldValue =selectItem.getBodyWeight()+" "+selectItem.getWeightUnit();
				//动物Id号+“，”+体重+“ ”+体重单位
				String newValue =animalId+","+weight+" "+weightUnit;
				Date date = new Date();
				TblTrace tblTrace = new TblTrace();
				tblTrace.setTableName("TblBodyWeight");//表名
				tblTrace.setDataId(sessionId); //这里放的是会话Id
				tblTrace.setOperateMode(1);//修改
				tblTrace.setOldValue(oldValue);
				tblTrace.setNewValue(newValue);
				tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
				tblTrace.setHost(SystemTool.getIPAddress());
				tblTrace.setModifyReason(SignFrameWithReason.getReason());
				tblTrace .setModifyTime(date);
				BaseService.getTblTraceservice().save(tblTrace);
				//4.更新数据()
				TblBodyWeight tblBodyWeight = new TblBodyWeight();
				tblBodyWeight.setSessionId(sessionId);
				tblBodyWeight.setAnimalId(animalId);
				tblBodyWeight.setBodyWeight(weight);
				tblBodyWeight.setDevId(devId);
				tblBodyWeight.setWeightUnit(weightUnit);
				tblBodyWeight.setWeightTime(date);
				BaseService.getTblBodyWeightService().saveOrUpdateNoId(tblBodyWeight);
				//5.保存成功后，选中下一行
				//采集到数据
				collectAnimalIdLabel.setText(animalId);
				collectWeightLabel.setText(weight+" "+weightUnit);
				int gender = selectItem.getGender().equals("♂") ? 1 : 2;
				//更换表格中数据
				TblBodyWeightForTableView tblBodyWeightForTableView = new TblBodyWeightForTableView(
						animalId,gender,weight,weightUnit,date
						);
				data_tblBodyWeightTable.set(currentIndex, tblBodyWeightForTableView);
				//选中下一个
				tblBodyWeightTable.getSelectionModel().selectNext();
				//scroll
				tblBodyWeightTable.scrollTo(currentIndex);
				
				weightText.clear();
				weightText.requestFocus();
				
				//5. 更新修改痕迹表格
				addTblTraceTable(tblTrace);
			}
		}else{
			Alert2.create("已复核，无法修改数据");
			return;
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
		/**
		 * 是否存在未称重动物
		 */
		boolean isExist = false;
		for(TblBodyWeightForTableView tblBodyWeightForTableView: data_tblBodyWeightTable){
			if(tblBodyWeightForTableView.getBodyWeight() ==null || 
					tblBodyWeightForTableView.getBodyWeight().equals("")){
				isExist =true;
				break;
			}
		}
		//存在   未称重
		if(isExist){
			Alert2.create("动物称重未录入完毕，不可签字");
			return;
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
			signId = BaseService.getTblSessionService().sign(sessionId, 10, 
					SignFrame.getUser().getRealName(), "检疫，体重称重签字");
			
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
		
		SignFrame signFrame = new SignFrame();
		try {
			Stage stage = new Stage();
			stage.setTitle("复核");
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("OK".equals(SignFrame.getType())){
			checkId = BaseService.getTblSessionService().check(sessionId, 11, 
					SignFrame.getUser().getRealName(), "检疫，体重称重复核");
			
			updateFlag = true;
			//采集方式，置空禁用
			collectComboBox.getSelectionModel().clearSelection();
			collectComboBox.setDisable(true);
			
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
				jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblBodyWeight.jrxml"));
			} catch (JRException e) {
				e.printStackTrace();
				Alert2.create("报表加载失败");
				return ;
			}
	        
	        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
	        
	        TblSession tblSession  = BaseService.getTblSessionService().getById(sessionId);
	        if(tblSession!=null){
	        	/**编号*/
	        	String number =BaseService.getDictOutputSettingsService().getShowByLabel("动物体重称量记录表-编号");         
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
	        	String balId="";
	        	
	        	 Map<String,Object> map = new HashMap<String,Object>();
	             map.put("logoImage",logoImage);
	             map.put("number",number);
	             map.put("endDate",endDate);
	             map.put("animalStrain",animalStrain);
	             map.put("signerAndDate",signerAndDate);
	             map.put("checkerAndDate",checkerAndDate);
	     		if(studyNo != null && !studyNo.isEmpty()){
	     			 map.put("studyNo",studyNo);
	     			 List<TblBodyWeight> tblBodyWeightList = 
	     					 BaseService.getTblBodyWeightService().getEntityListBySessionId(sessionId);
	     			 if(tblBodyWeightList !=null && tblBodyWeightList.size()>0){
	     				 balId = tblBodyWeightList.get(0).getDevId();
	     				 map.put("balId",balId);
	     				 int size =  tblBodyWeightList.size();
	     				 int count = size%124;
	     				 int max = 0;//增加空行数量
	     				 if(count !=0){
	     					 max = 124 - count;
	     				 }
	     				 for(int i=0;i<max;i++){
	     					tblBodyWeightList.add(new TblBodyWeight());
	     				 }
	    	             try {
	    	     			jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(tblBodyWeightList));
	    	     		} catch (JRException e) {
	    	     			e.printStackTrace();
	    	     			Alert2.create("报表加载失败");
	    	     			return ;
	    	     		}
	    	             Main.getInstance().openJFrame(jp, "动物体重称量记录表");
	    	     		
	     			 }
	     		}else{
	     			String recId = tblSession.getRecId();
	     			List<String> studyNoList = BaseService.getTblBodyWeightService()
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
	     				List<?> list = BaseService.getTblBodyWeightService().
	     						findListByRecIdSessionIdStudyNo(recId,sessionId,studyNo);
	     				List<Map<String,String>> mapList =new ArrayList<Map<String,String>>();
//	     			 List<TblBodyWeight> tblBodyWeightList = new ArrayList<TblBodyWeight>();
//		     			TblBodyWeight tblBodyWeight = null;
	     				Map<String,String> valueMap=null;
	     				if(null != list && list.size()>0){
	     					int count = list.size();
	     					for(int i = 0;i<count;i++){
	     						Object[] obj= (Object[]) list.get(i);
	     						valueMap = new HashMap<String,String>();
	     						valueMap.put("animalId", (String) obj[0]);
	     						valueMap.put("bodyWeight", (String) obj[1]);
	     						valueMap.put("weightUnit", (String) obj[2]);
	     						mapList.add(valueMap);
	     						if(i == 0){
	     							balId = (String) obj[3];
	     						}
	     					}
	     					map.put("balId",balId);
	     				}
	     				int size =  mapList.size();
	     				int count = size%124;
	     				int max = 0;//增加空行数量
	     				if(count !=0){
	     					max = 124 - count;
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
	     				Main.getInstance().openJFrame(jp, "动物体重称量记录表");
	     			}
	     		}
	     			
	     		
	        }
		}else{
			Alert2.create("未复核，复核后才可以打印");
		}
		
	}
	/**
	 * 关闭
	 */
	@FXML
	private void onExitButton(ActionEvent event){
		if((null == sessionId || "".equals(sessionId)) && 
				!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
			
		}else{
			//1.关闭端口
			closePort();
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
		BodyWeightFrame.sessionId =sessionId;
		tblSession = BaseService.getTblSessionService().getById(sessionId);
		BodyWeightFrame.tblSessionAnimalsList=null;
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
			//2.复核后 禁用采集方式
			signId =tblSession.getSignId();
			checkId =tblSession.getCheckId();
			if(null != checkId && !"".equals(checkId)){
				collectComboBox.setDisable(true);
			}
			beginDate =tblSession.getBeginTime();
			//3.加载tblBodyWeightTable 数据并选中待录入行(如何没有，选中第0行)
			loadData_tblBodyWeightTable();
			
			//4.加载修改痕迹表格数据 
			updateTblTraceTable();
		}
	}
	/**
	 * 加载数据，控件，表格（暂无sessionId时用）
	 * @param sessionId
	 */
	public void loadDataByTblSession(TblSession tblSession,List<TblSessionAnimals> tblSessionAnimalsList){
		BodyWeightFrame.sessionId =tblSession.getSessionId();;
		BodyWeightFrame.tblSession = tblSession;
		BodyWeightFrame.tblSessionAnimalsList=tblSessionAnimalsList;
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
			//3.加载tblBodyWeightTable 数据并选中第0行
			loadData_tblBodyWeightTable(tblSessionAnimalsList);
			data_tblTraceTable.clear();
			
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(this.getClass().getResource("BodyWeight.fxml"));
		Scene scene = new Scene(root,650,640);
		stage.setScene(scene);
		stage.setTitle("体重称重");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if((null == sessionId || "".equals(sessionId)) && 
						!Confirm2.create(Main.mainWidow, "确定放弃此次会话吗？")){
					event.consume();
				}else{
					//1.关闭端口
					closePort();
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
	@Override
	public void run() {
		System.out.println("-");
		try {
			Thread.sleep(2000);
			if(!isOpening){
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
