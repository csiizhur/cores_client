package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblBalConnect;
import com.lanen.util.SystemTool;
import com.lanen.view.balreg.AddTblBalCalibration;
import com.lanen.view.main.Main;
import com.lanen.view.test.AnatomyProcessPage.BalanceRrge;

public class EditVisceraWeight_balWeight extends Application implements Initializable, Runnable{
	private Stage stage;
	
	@FXML
	private TableView<BalanceRrge> balanceRrgeTbale;//天平tbale
	private ObservableList<BalanceRrge> data_balanceRrgeTbale = FXCollections.observableArrayList();

	@FXML
	private TableColumn<BalanceRrge, String> balCodeCol; // 天平编号column
	
	@FXML
	private TableColumn<BalanceRrge, String> balStateCol; // 天平状态column
	
	@FXML
	private TableColumn<BalanceRrge, String> baudComboCol; // 天平接入端口column\
	
	@FXML
	private Label studyNoLabel;
	@FXML
	private Label animalCodeLabel;
	@FXML
	private Label visceraNameLabel;
	
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
	public  Enumeration<?> portList;
	//RS-232的串行口 *
//	public static CommPortIdentifier isUsePortId;	//
//	public static SerialPort isUseingSerialPort ;	//备用，用来关闭的
	public static List<CommPortIdentifier> isUsePortIdList = new ArrayList<CommPortIdentifier>();//
	public static List<SerialPort> isUseingSerialPortList = new ArrayList<SerialPort>();//
	//线程
	private Thread readThread;
	private static boolean isOpening =false;	//连接设备中
	
//	private static double otherWeight ;
//	
	/**
     * 以上为  串口 通讯 用   
     */
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	private static EditVisceraWeight_balWeight instance;
	public static EditVisceraWeight_balWeight getInstance(){
		if(null == instance){
			instance = new EditVisceraWeight_balWeight();
		}
		return instance;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initBalanceRrgeTbale();
//		updateBalanceRrgeTbale();
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
    public void updateLabel2(String studyNo,String animalCode,String visceraName){
    	studyNoLabel.setText(studyNo);
    	animalCodeLabel.setText(animalCode);
    	visceraNameLabel.setText(visceraName);
    }
    
    /**加载数据
     * @param studyNo
     * @param animalCode
     * @param visceraName
     */
    public void loadData(String studyNo,String animalCode,String visceraName){
    	updateLabel2(studyNo,animalCode,visceraName);
    	updateBalanceRrgeTbale();
    }
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("EditVisceraWeight_balWeight.fxml"));
		Scene scene = new Scene(root, 397, 280);
		stage.setScene(scene);
		stage.setTitle("脏器称重-编辑-天平采集");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
				closePort();
			}
		});
		stage.setOnHidden(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				// 
				closePort();
			}
			
		});
		stage.show();
		
	}
	
	/**天平校准
	 * @param event
	 */
	@FXML
	private void onBalCalibration(ActionEvent event){
		try{
			closePort();
			AddTblBalCalibration.getInstance().start(getStage());
			String balCode = "";
			if(data_balanceRrgeTbale.size()>0){
				for(int i = 0;i<data_balanceRrgeTbale.size();i++){
					if(data_balanceRrgeTbale.get(i).getBaudCombo().equals("天平未校准")){
						balCode = data_balanceRrgeTbale.get(i).getBalCode();
						break;
					}
				}
				if(balCode.equals("")  ){
					balCode = data_balanceRrgeTbale.get(0).getBalCode();
				}
			}else{
				balCode = null;
			}
			
			AddTblBalCalibration.getInstance().loadData(balCode,"weightEdit");
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	 void onExitBtnAction(ActionEvent event){
		closePort();
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 打开串口 
	 * @param chipReaderMap设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 */
	private String openPort(Map<String,Object> chipReaderMap) {
		if(null == chipReaderMap ){
//			msgLabel.setText("天平的连接信息未设置。");
			return "天平的连接信息未设置。";
		}
		String comPort = (String) chipReaderMap.get("commName");     //波特率                             
		Integer baudRate = (Integer) chipReaderMap.get("baud");      //数据位   5 6 7 8                   
		Integer dataBit = (Integer) chipReaderMap.get("dataBit");    //停止位    1,1.5,2                  
		Integer stopBit = (Integer) chipReaderMap.get("stopBit");    //校验位    None，Even，Odd，Space，Mark 
		if(stopBit == 15){
			stopBit = 3;
		}
		Integer checkMode = (Integer) chipReaderMap.get("parit");
		if(null == comPort || "".equals(comPort) ){
//			msgLabel.setText("天平的连接信息未设置。");
			return "天平的连接信息未设置。";
		}
		//获取系统中所有的通讯端口
		portList=CommPortIdentifier.getPortIdentifiers();
		boolean isExistComPort=false;   //设置的端口是否存在
		while(portList.hasMoreElements()){
			SerialPort serialPort =null;
			System.out.println(portList.hasMoreElements());
			//强制转换为通讯端口类型
			CommPortIdentifier portId=(CommPortIdentifier) portList.nextElement();
			if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
				System.out.println(portId.getName());
				if(portId.getName().equals(comPort)){//判断串口名是否相同
					isExistComPort=true;//串口存在
					try {
						if(portId.isCurrentlyOwned()){
//							msgLabel.setText("天平端口（"+comPort+"）被占用！");
							return "天平端口（"+comPort+"）被占用！";
						}
						serialPort = (SerialPort) portId.open("0001", 2000);//打开端口
						
						//isUsePortId = portId;	//留备份，关闭用
						isUsePortIdList.add(portId);
						isUseingSerialPortList.add(serialPort);
						//isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
//						msgLabel.setText("天平端口（"+comPort+"）被占用！");
						//e1.printStackTrace();
						return "天平端口（"+comPort+"）被占用！";
					}
					//设置串口监听器
					try {
						serialPort.addEventListener(new SerialPortEventListener_WeightEdit());
					} catch (TooManyListenersException e1) {
						serialPort.close(); 
					}
					//侦听到串口有数据,触发串口事件
					serialPort.notifyOnDataAvailable(true);  
					isOpening =true;
					try {
						serialPort.setSerialPortParams(baudRate, 
								dataBit, 
								stopBit, 
								checkMode);
//						msgLabel.setText("天平已连接。");
						return "天平已连接。";
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
//			msgLabel.setText("天平端口（"+comPort+"）不存在！");
			return "天平端口（"+comPort+"）不存在！";
		}else{
			isExistComPort=false;
		}
		readThread=new Thread(this);
		readThread.start();//线程负责每接收一次数据休眠2秒钟
		return "";
	}
	/**
	 * 关闭端口
	 */
	private void closePort(){
		if(null != isUsePortIdList  && isUsePortIdList.size() > 0 ){
			//关闭关闭端口连接
			for(int i = 0;i<isUseingSerialPortList.size();i++){
				isUseingSerialPortList.get(i).removeEventListener();
				isUseingSerialPortList.get(i).notifyOnDataAvailable(false); 
				isUseingSerialPortList.get(i).close();
			}
//			isUseingSerialPort.removeEventListener();
//			isUseingSerialPort.notifyOnDataAvailable(false); 
//			isUseingSerialPort.close();
			isOpening =false;
			
		}
//		if(null != msgLabel){
//			msgLabel.setText("");
//		}
		//isUsePortId = null;
		isUsePortIdList = new ArrayList<CommPortIdentifier>();
//		isUseingSerialPort = null;
		isUseingSerialPortList = new ArrayList<SerialPort>();
		portList = null;
	}
	/**
     * 初始化天平
     */
    private void initBalanceRrgeTbale(){
    	balanceRrgeTbale.setItems( data_balanceRrgeTbale);
    	balCodeCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("balCode"));
    	balStateCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("balState"));
    	baudComboCol.setCellValueFactory(new PropertyValueFactory<BalanceRrge,String>("baudCombo"));
    	balanceRrgeTbale.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BalanceRrge>(){
	    	@Override
			public void changed(ObservableValue<? extends BalanceRrge> arg0, BalanceRrge arg1, BalanceRrge newValue) {
				if(null != newValue){
					balanceRrgeTbale.getSelectionModel().clearSelection();
				}
	    	}
    	});
    }
    //更新天平数据
    public void updateBalanceRrgeTbale(){
    	closePort();
    	data_balanceRrgeTbale.clear();
    	String houstName = SystemTool.getHostName();//计算机名称
		List<TblBalConnect> list = BaseService.getInstance().getTblBalConnectService().findByHostNameList(houstName);
		if(null != list && list.size()>0){
			for(TblBalConnect connect:list){
				String commName = connect.getCommName();
				String balCode = connect.getBalCode();
				//Integer enabledStr = connect.getEnabled();----------------------------------------------------------------------------------------//
				boolean flag = BaseService.getInstance().getTblBalCalibrationIndexService().isExistByBalCode(balCode);
				String balState = "";
				if(!flag){
					balState = "天平未校准";
				}else{
					//查询天平的 设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
					Map<String,Object> balMap = BaseService.getInstance().getTblBalRegService().findBalMapByBalCodeHostName(balCode,houstName);
					if(null == balMap ){
						balState = "连接信息未设置";
					}else{
						balState = openPort(balMap);
					}
				}
				data_balanceRrgeTbale.add(new BalanceRrge(balCode,commName,balState));
			}

		}
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
}
