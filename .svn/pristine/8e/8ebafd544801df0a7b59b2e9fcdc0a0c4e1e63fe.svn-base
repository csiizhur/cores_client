package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TooManyListenersException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;

import com.lanen.base.BaseService;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**动物芯片查询
 * @author Administrator
 *
 */
public class AnimalChipSelectPage extends Application implements Initializable , Runnable {
	
	
	@FXML
	private TableView<ChipAnimal> itable;		//动物表格
	private ObservableList<ChipAnimal> data_itable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<ChipAnimal,Boolean> selectCol;        //
	@FXML
	private TableColumn<ChipAnimal,String> chipCodeCol;        //芯片编号
	@FXML
	private TableColumn<ChipAnimal,String> studyNoCol;       //专题编号
	@FXML
	private TableColumn<ChipAnimal,String> animalCodeCol;     //动物编号
	
	@FXML
	private Label msgLabel;				//
	
	/**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
//	public  CommPortIdentifier portId;  
	//Enumeration 为枚举型类,在util中 
	public  Enumeration<?> portList;
	//RS-232的串行口 *
//	public static SerialPort serialPort =null;
	public static CommPortIdentifier isUsePortId;	//
	public static SerialPort isUseingSerialPort ;	//备用，用来关闭的
	//线程
	private Thread readThread;
	private static boolean isOpening =false;	//连接设备中
//	private String devIds ="";//连接成功的设备Id
//	private  int baudRateInt=0;
//	private  int dataBitInt=0;
//	private  int stopBitInt=0;
//	private  int checkModeInt=0;
//	
	/**
     * 以上为  串口 通讯 用   
     */
	private Window window ;
	
	private static AnimalChipSelectPage instance;
	
	
	public static AnimalChipSelectPage getInstance(){
		if(null == instance){
			instance = new AnimalChipSelectPage();
		}
		return instance;
	}
	
	public AnimalChipSelectPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化table
		initTable();
	}
	
	
	/**创建临时任务
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		List<String> animalCodeList = new ArrayList<String>();
		Set<String> studyNoSet = new HashSet<String>();
		String studyNo = "";
		//1.是否已经选择了动物
		for(ChipAnimal obj:data_itable){
			if(obj.getSelect()){
				animalCodeList.add(obj.getAnimalCode());
				studyNoSet.add(obj.getStudyNo());
				studyNo = obj.getStudyNo();
			}
		}
		if(animalCodeList.size() < 1){
			showErrorMessage("请先选择动物！");
			return;
		}
		//2.选择的动物是否在同一个专题下
		if(studyNoSet.size() != 1){
			showErrorMessage("请选择同一个专题下的动物！");
			return;
		}
		//3.
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo2(studyNo);
		if(null == studyPlan){
			showErrorMessage("该专题编号不存在或该专题无病理计划！");
			return;
		}
		//4.关闭串口连接
		closePort();
		//5.打开创建临时任务窗口
		try {
			Stage stage = Main.stageMap.get("TempReqPage");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				TempReqPage.getInstance().start(stage);
				Main.stageMap.put("TempReqPage",stage);
			}else{
				stage.show();
			}
			TempReqPage.getInstance().loadData(studyNo,animalCodeList,studyPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		closePort();
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * 关闭窗口（确定打开其他窗口时，给其他窗口调用）
	 */
	public void closeWindow(){
		closePort();
		window = itable.getScene().getWindow();
		window.hide();
	}
	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData() {
		data_itable.clear();
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				//打开串口
				openPort();
			}
			
		});
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("animalChip.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("动物芯片查询");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				closePort_x();
			}
		});
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	/**
	 * 初始化 table
	 */
	private void initTable() {
		itable.setItems(data_itable);
		itable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol.setCellValueFactory(new PropertyValueFactory<ChipAnimal,Boolean>("select"));
		chipCodeCol.setCellValueFactory(new PropertyValueFactory<ChipAnimal,String>("chipCode"));
		studyNoCol.setCellValueFactory(new PropertyValueFactory<ChipAnimal,String>("studyNo"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<ChipAnimal,String>("animalCode"));
		
		selectCol.setCellFactory(new Callback<TableColumn<ChipAnimal,Boolean>,TableCell<ChipAnimal,Boolean>>(){

			@Override
			public TableCell<ChipAnimal, Boolean> call(TableColumn<ChipAnimal, Boolean> arg0) {
				return new CheckBoxTableCell<ChipAnimal,Boolean>();
			}
		});
		
		//单击事件
		itable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent event) {
				// 单击
				if (event.getButton() == MouseButton.PRIMARY) {
					TableView<ChipAnimal> table = (TableView<ChipAnimal>) event.getSource();
					ChipAnimal obj = table.getSelectionModel().getSelectedItem();
					if (null != obj) {
						ChipAnimal obj2 = new ChipAnimal(!obj.getSelect(),obj.getChipCode(),
								obj.getStudyNo(),obj.getAnimalCode());
						
						table.getItems().set(table.getSelectionModel().getSelectedIndex(),
								obj2);
					}
					table.getSelectionModel().clearSelection();
				}
				
			}
			
		});
	}
	
	
	
	/**
	 * 打开串口
	 */
	public  void openPort(){
		String hostName = SystemTool.getHostName();
		//查询询芯片阅读器的 设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
		Map<String,Object> chipReaderMap = BaseService.getInstance().getTblChipReaderService().findChipReaderMapByHostNameEnable(hostName,1);
		//打开串口
		openPort(chipReaderMap);
	}
	
	/**
	 * 打开串口 
	 * @param chipReaderMap设备Id:chipCode,COM口：commName,波特率：baud,数据位：dataBit,停止位：stopBit,校验位：parit 
	 */
	private void openPort(Map<String,Object> chipReaderMap) {
		if(null == chipReaderMap ){
			msgLabel.setText("芯片阅读器的连接信息未设置。");
			return ;
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
			msgLabel.setText("芯片阅读器的连接信息未设置。");
			return ;
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
							msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用！");
							return;
						}
						serialPort = (SerialPort) portId.open("0001", 2000);//打开端口
						
						isUsePortId = portId;	//留备份，关闭用
						isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
						msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用！");
						//e1.printStackTrace();
						return;
					}
					//设置串口监听器
					try {
						serialPort.addEventListener(new SerialPortEventListener_chipAnimalSelect());
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
						msgLabel.setText("芯片阅读器已连接。");
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
			msgLabel.setText("芯片阅读器端口（"+comPort+"）不存在！");
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
		msgLabel.setText("芯片阅读器未连接。");
	}
	/**
	 * 关闭端口
	 */
	private void closePort_x(){
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

	/** 根据 iCardCode,查询动物并显示
	 * @param iCardCode
	 */
	public void selectAnimalByICardCode(String iCardCode) {
		//1.
		if(null == iCardCode || "".equals(iCardCode) ){
			showErrorMessage("请重新扫描！");
			return;
		}
		//2.是否已扫描
		boolean isExist = false;
		for(ChipAnimal obj :data_itable){
			if(iCardCode.equals(obj.getChipCode())){
				isExist = true;
				break;
			}
		}
		if(!isExist){
			Map<String,Object> studyNoAnimalCodeMap = BaseService.getInstance().getTblChipReaderService().getStudyNoAnimalCodeMapByICardCode(iCardCode);
			if(null != studyNoAnimalCodeMap){
				String studyNo = (String) studyNoAnimalCodeMap.get("studyNo");
				String animalCode = (String) studyNoAnimalCodeMap.get("animalCode");
				data_itable.add(new ChipAnimal(false,iCardCode,studyNo,animalCode));
			}else{
				showErrorMessage("卡号（"+iCardCode+"）不存在！");
			}
		}else{
			showErrorMessage("重复扫描！");
		}
	}

	
	// 在单元格里创建多选框
	public class CheckBoxTableCell<S, T> extends TableCell<S, T> {
			        private final CheckBox checkBox;
			        private ObservableValue<T> ov;
		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
			
		}
        @Override 
        public void updateItem(T item, boolean empty) {
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
//		 				getTableRow().setStyle("-fx-background-color:palegreen ;");
	 				getTableRow().setStyle("-fx-background-color:#0092DC;");
                }else{
                	getTableRow().setStyle("");
                }
            }
        }
	}
	
	/**芯片动物
	 * @author Administrator
	 *
	 */
	public class ChipAnimal{
		
		private BooleanProperty select;
		private StringProperty chipCode;
		private StringProperty studyNo;
		private StringProperty animalCode;
		
		public ChipAnimal(){}
		public ChipAnimal(boolean select,String chipCode,String studyNo,String animalCode){
			setSelect(select);
			setChipCode(chipCode);
			setStudyNo(studyNo);
			setAnimalCode(animalCode);
		}
		
		public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.get();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getChipCode() {
			return chipCode.get();
		}
		public void setChipCode(String chipCode) {
			this.chipCode = new SimpleStringProperty(chipCode);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
	}

	/**处理动物芯片查询，去除已建任务动物，如果无动物剩下，则关闭动物芯片查询，否则打开串口
	 * @param studyNo
	 * @param animalCodeList
	 */
	public void delTable(String studyNo, List<String> animalCodeList) {
		//待删除动物列表
		List<ChipAnimal> delList = new ArrayList<ChipAnimal>();
		//1.查询待删除列表
		for(ChipAnimal obj:data_itable){
			if(studyNo.equals(obj.getStudyNo()) && animalCodeList.contains(obj.getAnimalCode())){
				delList.add(obj);
			}
		}
		//2.删除列表
		data_itable.removeAll(delList);
		//3.
		if(data_itable.size() > 0){
			openPort();
		}else{
			closeWindow();
		}
	}
}
