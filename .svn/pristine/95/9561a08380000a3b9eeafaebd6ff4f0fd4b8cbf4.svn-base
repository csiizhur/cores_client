package com.lanen.view.balreg;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import javafx.application.Application;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblBalCalibration;
import com.lanen.model.path.TblBalCalibrationIndex;
import com.lanen.model.path.TblBalReg;
import com.lanen.model.path.TblCounterpoise;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.balreg.TblBalCalibrationIndexController.TblBalCalibrationTable;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;
import com.lanen.view.test.AnatomyProcessPage;
import com.lanen.view.test.EditVisceraWeight_balWeight;

/**新建校准记录
 * @author Administrator
 *
 */
public class AddTblBalCalibration extends Application implements Initializable,Runnable{
	private Stage stage ;
	@FXML
	private Button okButton;                       //确定按钮 
	@FXML
	private Button cacelButton;                     //取消按钮
	@FXML
	private ComboBox<String> balCodeCombo;            //天平编号下拉框
	private ObservableList<String> balCodedata = FXCollections.observableArrayList();
	@FXML
	private Label precisionLabel;                     //天平精度标签
	@FXML
	private Label calCheckPointLabel;                 //天平校准点数标签
	@FXML
	private TableView<Counterpoise> cpTable;
	private ObservableList<Counterpoise> data_cpTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<Counterpoise,String> cpIdCol;
	@FXML
	private TableColumn<Counterpoise,String> cpCodeCol;
	@FXML
	private TableColumn<Counterpoise,String> cpWeightCol;
//	@FXML
//	private TableColumn<Counterpoise,String> cpToleranceUpperCol;
//	@FXML
//	private TableColumn<Counterpoise,String> cpToleranceLowerCol;
	
	@FXML
	private TableView<TblBalCalibrationTable> tblBalCalibrationTable; 
	private ObservableList<TblBalCalibrationTable> data_tblBalCalibrationTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recCpCodeCol;
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recCpWeightCol;
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recBalValueCol;
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recToleranceUpperCol;
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recToleranceLowerCol;
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recResultCol;
	private String cpCode;
	private TblCounterpoise tblCounterpoise;
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
//	public  CommPortIdentifier portId;  
	private List<CommPortIdentifier> portIdList = new ArrayList<CommPortIdentifier>();
	//Enumeration 为枚举型类,在util中 
	public  Enumeration<?> portList;
	//RS-232的串行口 *
	private List<SerialPort> serialPortList = new ArrayList<SerialPort>();
	public static CommPortIdentifier isUsePortId;	//
	public static SerialPort isUseingSerialPort ;	//备用，用来关闭的
	//线程
	private Thread readThread;
	private static boolean isOpening =false;
	private String devIds ="";//连接成功的设备Id
	private  int stopBitInt=0;
	private static String where;
	
	/**
     * 以上为  串口 通讯 用   
     */
	
	private static AddTblBalCalibration instance;
	public static AddTblBalCalibration getInstance(){
		if(null == instance){
			instance = new AddTblBalCalibration();
		}
		return instance;
	}
	
	public AddTblBalCalibration() {
	}
		
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initCpTable();
		initTblBalCalibrationTable();
		initBalCodeComBo();// 初始化天平选择下拉
//		closePort();
		precisionLabel.setDisable(true);
		okButton.setDisable(true);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AddTblBalCalibration.fxml"));
		Scene scene = new Scene(root, 646, 354);
		stage.setScene(scene);
		stage.setTitle("新建校准");
		stage.setResizable(false);
		stage.setWidth(663);
		stage.setHeight(404);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX((bounds.getMaxX() - 663D) / 2);
		stage.setY((bounds.getMaxY() - 404D) / 2);
		stage.setOnHidden(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
				    //点X关闭
				if(data_tblBalCalibrationTable.size()>0){
					if(Messager.showSimpleConfirm("提示","是否放弃保存本次校准数据？")){
						closePort();
						if(null != where && where.equals("weight")){
							AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
						}
					}else{
						return;
					}
				}else{
					closePort();
					if(null != where && where.equals("weight")){
						AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
					}
				}
				
				
			}
		});
		stage.show();
	}
 	
	/**
	 * 加载数据
	 * @param tblBalReg
	 */
	public void loadData(String balCode,String where){
		updateBalCodeComBo();
		if(null == where ){
			AddTblBalCalibration.where = null;
		}else{
			if(null != balCode){
				balCodeCombo.getSelectionModel().select(balCode);
			}
		
			AddTblBalCalibration.where = where;
		}
	
	}
	
	/**
	 * 砝码表初始化
	 */
	public void initCpTable(){
		cpTable.setItems(data_cpTable);
		cpTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		cpIdCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("id"));
		cpCodeCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("cpCode"));//砝码编号
		cpWeightCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("cpWeight"));//砝码重量
//		balPrecisionCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("balPrecision"));//天平精度
//		cpToleranceLowerCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("toleranceLower"));//允差下限
//		cpToleranceUpperCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("toleranceUpper"));//允差上限
		cpTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<Counterpoise>(){

			@Override
			public void changed(ObservableValue<? extends Counterpoise> arg0, Counterpoise arg1,
					Counterpoise newValue) {
				if(null != newValue){
//					delectBalButton.setDisable(false);
//					updateBalButton.setDisable(false);
					int selectRow = cpTable.getSelectionModel().getSelectedCells().get(0).getRow();
					String id = data_cpTable.get(selectRow).getId();
					tblCounterpoise= BaseService.getInstance().getTblCounterpoiseService().getById(id);
					//String olId = counterpoise.getId();
					cpCode=tblCounterpoise.getCpCode();
//					String cpCode = counterpoise.getCpCode();
//					String cpWeight = counterpoise.getCpWeight();
//					String balPrecision = counterpoise.getBalPrecision();
//					String toleranceLower = counterpoise.getToleranceLower();
//					String toleranceUpper = counterpoise.getToleranceUpper();
//					String value=balValue;           //通过电子秤传输
//					String balValue=SimpleRead.str;
//					if(null!=value && !value.equals("")){
//						Date date=new Date();
//						SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
//						String calTime=sdf.format(date);
//						data_tblBalCalibrationTable.add(new TblBalCalibrationTable("",cpCode,cpWeight,value,toleranceUpper,toleranceLower,calTime));
//						value=""; 
//					}
					
				}else{
//					delectBalButton.setDisable(true);
//					updateBalButton.setDisable(true);
					data_tblBalCalibrationTable.clear();
				}
			}

		});

	}
	/**
	 * 初始化校准记录
	 */
	public void initTblBalCalibrationTable(){
		tblBalCalibrationTable.setItems(data_tblBalCalibrationTable);
		tblBalCalibrationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		recCpCodeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("cpCode"));
		recCpWeightCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("cpWeight"));
		recBalValueCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("balValue"));
		recToleranceUpperCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("toleranceUpper"));
		recToleranceLowerCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("toleranceLower"));
		recResultCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("result"));
		
	}
	/**更新砝码
	 * @param precision
	 */
	public void updateCpTable(String precision){
		data_cpTable.clear();
		List<TblCounterpoise> list=BaseService.getInstance().getTblCounterpoiseService().getByPrecision(precision);
		if(null!=list && list.size()>0){
			for(TblCounterpoise t:list){
				data_cpTable.add(new Counterpoise(t.getId(),t.getCpCode(),t.getCpWeight(),t.getBalPrecision(),t.getToleranceLower(),t.getToleranceUpper()));
			}
			cpTable.getSelectionModel().select(0);
		}
	}
	/**
	 * 更新校准记录
	 */
	public void updateBalCalibrationTable(){
		
	}
	/**关闭按钮事件
	 * @param event
	 */
	@FXML
	private void onCacelBtnAction(ActionEvent event){
		if(data_tblBalCalibrationTable.size()>0){
			if(Messager.showSimpleConfirm("提示","是否放弃保存本次校准数据？")){
				closePort();
				((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
				if(null != where && where.equals("weight")){
					AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
				}
			}else{
				return;
			}
		}else{
			closePort();
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			if(null != where && where.equals("weight")){
				AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
			}
		}
//		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//		closePort();
//		if(null != where && where.equals("weight")){
//			AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
//		}
		
	}
	/**确定按钮事件
	 * @param event
	 * @throws ParseException 
	 */
	@FXML
	private void onOkBtnAction(ActionEvent event) throws ParseException{
//		addBalCalibration("9.999");
		List<TblBalCalibration> list=new ArrayList<TblBalCalibration>();
		TblBalCalibrationIndex  blBalCalibrationIndex=new TblBalCalibrationIndex();
		String indexId=BaseService.getInstance().getTblBalCalibrationIndexService().getKey();
		blBalCalibrationIndex.setId(indexId);
		String balCode=balCodeCombo.getValue();
		blBalCalibrationIndex.setBalCode(balCode);
		
		if(null!=balCode){
			String hostCode=SystemTool.getHostName();;     //计算机编号）
			blBalCalibrationIndex.setHostCode(hostCode);
		}
		int passTime=0;
		List<String> cpCodeList=new ArrayList<String>();
		for(int i=0;i<data_tblBalCalibrationTable.size();i++){
			TblBalCalibration t=new TblBalCalibration();
			t.setId(BaseService.getInstance().getTblBalCalibrationService().getKey());
			t.setCalIndexId(indexId);
			t.setCpCode(data_tblBalCalibrationTable.get(i).getCpCode());
			String cpWeight=data_tblBalCalibrationTable.get(i).getCpWeight();
			t.setCpWeight(cpWeight);
			String balValue=data_tblBalCalibrationTable.get(i).getBalValue();
			t.setBalValue(balValue);
			t.setToleranceLower(data_tblBalCalibrationTable.get(i).getToleranceLower());
			t.setToleranceUpper(data_tblBalCalibrationTable.get(i).getToleranceUpper());
			t.setTolerance((Float.parseFloat(balValue)-Float.parseFloat(cpWeight))+"");
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			t.setCalTime(sdf.parse(data_tblBalCalibrationTable.get(i).getCalTime()));
			if(data_tblBalCalibrationTable.get(i).getResult().equals("通过")){
//				if(!cpCodeList.contains(data_tblBalCalibrationTable.get(i).getCpCode())){
					passTime=passTime+1;
//					cpCodeList.add(data_tblBalCalibrationTable.get(i).getCpCode());
//				}
				
			}
			if(i==0){
				blBalCalibrationIndex.setCalBeginTime(sdf.parse(data_tblBalCalibrationTable.get(i).getCalTime()));
			}
			if(i==(data_tblBalCalibrationTable.size()-1)){
				blBalCalibrationIndex.setCalEndTime(sdf.parse(data_tblBalCalibrationTable.get(i).getCalTime()));
			}
			list.add(t);
		}
		if(list.size()==0){
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			return;
		}
		TblBalReg tblBalReg=BaseService.getInstance().getTblBalRegService().getById(balCode);
//		TblBalCalibrationPoint tblBalCalibrationPoint=BaseService.getInstance().getTblBalCalibrationPointService().getBalCalibrationPointByCalType(1);
		int point=tblBalReg.getCalCheckPoint();
		System.out.println(point);
//		Alert2.create(point+"");
		if(passTime>=point){
			blBalCalibrationIndex.setCalResult(1);
		}else{
			blBalCalibrationIndex.setCalResult(0);
			
//			if(Confirm2.create(getStage(),"校准未通过，是否确定保存数据？")){
//			}else{
//				return;
//			}
		}
		
        //签字窗口
		SignFrame signFrame = new SignFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("身份验证");
		try {
			signFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//签字通过
		if("OK".equals(SignFrame.getType())){
			String userName = "";
//					Date currentDate = new Date();
			User user = SignFrame.getUser();
			if(null != user){
				userName = user.getUserName();
			}
			blBalCalibrationIndex.setCalSign(userName);
			if(null!=list&&list.size()>0){
				BaseService.getInstance().getTblBalCalibrationService().saveBalCalibration(list,blBalCalibrationIndex);
				((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
				closePort();
				if(null != where){
					if(where.equals("weight")){
						AnatomyProcessPage.getInstance().updateBalanceRrgeTbale();
					}else if(where.equals("weightEdit")){
						EditVisceraWeight_balWeight.getInstance().updateBalanceRrgeTbale();
					}else{
						TblBalCalibrationIndexController.getInstance().updateTblBalCalibrationIndexTable();
						TblBalCalibrationIndexController.getInstance().selectRow(blBalCalibrationIndex);
					}
				}
			}
		}
		
	}
	
    /**
     * 初始化天平选择下拉
     */
    public void initBalCodeComBo(){
    	balCodeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					TblBalReg  tblBalReg=BaseService.getInstance().getTblBalRegService().getById(newValue);
					String precision=tblBalReg.getPrecision();
					updateCpTable(precision);
					//更新精度
					precision="(精度  "+precision+" g)";
					precisionLabel.setDisable(false);
					precisionLabel.setText(precision);
					data_tblBalCalibrationTable.clear();
					closePort();
					openPort(tblBalReg);
					int point=tblBalReg.getCalCheckPoint();
					calCheckPointLabel.setDisable(false);
					calCheckPointLabel.setText("(校准点数："+point+" )");
				}else{
					data_cpTable.clear();
				}
			}
		});
    	
    }
    
    //初始化数据
    public void updateBalCodeComBo(){
    	//初始化service
        String hostName=SystemTool.getHostName();
        List<String> list=BaseService.getInstance().getTblBalRegService().getAllBalCode(hostName);
        balCodedata.clear();
		if(null!=list && list.size()>0){
			for(String parit : list){
				balCodedata.add(parit);
			}
		}
		
		balCodeCombo.setItems(balCodedata);
    }
    /**
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
	}
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
	/**
	 * 打开串口 
	 * @param object
	 */
	private void openPort(TblBalReg tblBalReg) {
		String balCode = tblBalReg.getBalCode();
		String hostName=SystemTool.getHostName();
		String comPort = BaseService.getInstance().getTblBalConnectService().getComPort(balCode,hostName);
		Integer baudRate = tblBalReg.getBaud();
		Integer dataBit = tblBalReg.getDataBit();
		Integer stopBit = tblBalReg.getStopBit();
		Integer checkMode = tblBalReg.getParit();
		
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
							showErrorMessage("设备'"+balCode+"'端口"+comPort+"已经被占用!");
							return;
						}
						serialPort = (SerialPort) portId.open(balCode, 2000);//打开端口
						isUsePortId = portId;	//留备份，关闭用
						isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
						showErrorMessage("设备'"+balCode+"'端口"+comPort+"已经被占用!");
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
					devIds =devIds+" "+balCode;
					try {
//						String baudRate="";             //波特率   
//						String dataBit="";              //数据位   5 6 7 8
//						String stopBit="";              //停止位    1,1.5,2
//						String checkMode="";            //校验位    None，Even，Odd，Space，Mark
//						baudRateInt=Integer.parseInt(baudRate);
//						dataBitInt=Integer.parseInt(dataBit);
						stopBitInt=0;
						if(stopBit==1){
							stopBitInt=1;
						}else if(stopBit==15){
							stopBitInt=3;
						}else{
							stopBitInt=2;
						}
//						checkModeInt=0;
//						if(checkMode.equals("None")){
//							checkModeInt=0;
//						}else if(checkMode.equals("Odd")){
//							checkModeInt=1;
//						}else{
//							checkModeInt=2;
//						}
						/*设置串口初始化参数，依次是波特率，数据位，停止位和校验*/ 
//						serialPort.setSerialPortParams(9600, 
//								SerialPort.DATABITS_8, 
//								SerialPort.STOPBITS_1, 
//								SerialPort.PARITY_NONE);
						serialPort.setSerialPortParams(baudRate, 
								dataBit, 
								stopBitInt, 
								checkMode);
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
			showErrorMessage("设备'"+balCode+"'端口"+comPort+"不存在");
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
		if(null != isUsePortId ){
			//关闭关闭端口连接
			isUseingSerialPort.removeEventListener();
			isUseingSerialPort.notifyOnDataAvailable(false); 
			isUseingSerialPort.close();
			isOpening =false;
		}
		isUsePortId = null;
		isUseingSerialPort = null;
		portList = null;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			if(!isOpening){
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	/**如果天平有读数传来，添加数据显示到校准记录
	 * @param balValue
	 */
	public void addBalCalibration(String balValue){
		if(null!=balValue && !"".equals(balValue)){
			int selectRow = cpTable.getSelectionModel().getSelectedCells().get(0).getRow();
			String id = data_cpTable.get(selectRow).getId();
			tblCounterpoise= BaseService.getInstance().getTblCounterpoiseService().getById(id);
			//String olId = counterpoise.getId();
			cpCode=tblCounterpoise.getCpCode();
			String cpCode = tblCounterpoise.getCpCode();
			String cpWeight = tblCounterpoise.getCpWeight();
//			String balPrecision = tblCounterpoise.getBalPrecision();
			String toleranceLower = tblCounterpoise.getToleranceLower();
			String toleranceUpper = tblCounterpoise.getToleranceUpper();
			String value=balValue;           //通过电子秤传输
			String weight=balValue;
//			Date date=new Date();
			Date date=BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			String calTime=sdf.format(date);
			TblBalCalibrationTable t=new TblBalCalibrationTable("",cpCode,cpWeight,weight,toleranceUpper,toleranceLower,calTime);
			String dataConfirm="砝码:"+cpCode+"  重量:"+cpWeight+"  天平读数:"+balValue+"g";
			String dataGet="  校准结果:	"+t.getResult()+" ,是否确认录入？";
//			if(Confirm.create(getStage(),"数据确认",dataConfirm,dataGet)){
//				
//			}else{
//				return;
//			}
			if(null!=value && !value.equals("")){
				
				data_tblBalCalibrationTable.add(new TblBalCalibrationTable("",cpCode,cpWeight,weight,toleranceUpper,toleranceLower,calTime));
				value=""; 
				okButton.setDisable(false);
			}
		}
//		int selectRow = cpTable.getSelectionModel().getSelectedCells().get(0).getRow();
//		String id = data_cpTable.get(selectRow).getId();
//		tblCounterpoise= BaseService.getInstance().getTblCounterpoiseService().getById(id);
//		//String olId = counterpoise.getId();
//		cpCode=tblCounterpoise.getCpCode();
//		String cpCode = tblCounterpoise.getCpCode();
//		String cpWeight = tblCounterpoise.getCpWeight();
////		String balPrecision = tblCounterpoise.getBalPrecision();
//		String toleranceLower = tblCounterpoise.getToleranceLower();
//		String toleranceUpper = tblCounterpoise.getToleranceUpper();
//		String value=balValue;           //通过电子秤传输
//		String weight=balValue;
		
	}
	/**
	 * 砝码允差
	 */
	public static class Counterpoise{
		private StringProperty id; // 主键
		private StringProperty cpCode;//砝码编号
		private StringProperty cpWeight;//砝码重量
		private StringProperty balPrecision;//天平精度
		private StringProperty toleranceLower;//允差下限
		private StringProperty toleranceUpper;//允差上限
		
		public Counterpoise(){
			super();
		}

		public Counterpoise(String id,String cpCode,String cpWeight,String balPrecision,String toleranceLower,String toleranceUpper){
			this.id = new SimpleStringProperty(id);
			this.cpCode = new SimpleStringProperty(cpCode);
			this.cpWeight = new SimpleStringProperty(cpWeight+"g");
			this.balPrecision = new SimpleStringProperty(balPrecision+"g");
			this.toleranceLower = new SimpleStringProperty(toleranceLower+"g");
			this.toleranceUpper = new SimpleStringProperty(toleranceUpper+"g");
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getCpCode() {
			return cpCode.get();
		}

		public void setCpCode(String cpCode) {
			this.cpCode = new SimpleStringProperty(cpCode);
		}

		public String getCpWeight() {
			return cpWeight.get();
		}

		public void setCpWeight(String cpWeight) {
			this.cpWeight = new SimpleStringProperty(cpWeight);
		}

		public String getBalPrecision() {
			return balPrecision.get();
		}

		public void setBalPrecision(String balPrecision) {
			this.balPrecision = new SimpleStringProperty(balPrecision);
		}

		public String getToleranceLower() {
			return toleranceLower.get();
		}

		public void setToleranceLower(String toleranceLower) {
			this.toleranceLower = new SimpleStringProperty(toleranceLower);
		}

		public String getToleranceUpper() {
			return toleranceUpper.get();
		}

		public void setToleranceUpper(String toleranceUpper) {
			this.toleranceUpper = new SimpleStringProperty(toleranceUpper);
		}
		
	}
	
}
