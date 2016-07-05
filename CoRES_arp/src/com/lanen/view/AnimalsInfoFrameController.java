package com.lanen.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import com.lanen.base.BaseService;
import com.lanen.model.Individual_Json;
import com.lanen.util.messager.Messager;
import com.lanen.view.DataAcceptMeaage;
import com.lanen.main.Main;
import com.lanen.main.MainFrame;
import com.lanen.main.MainFrameController;

public class AnimalsInfoFrameController extends Application implements Initializable , Runnable  {

	@FXML
	private ComboBox<String> instrumentComboBox2;
	private ObservableList<String> data_instrumentComboBox =FXCollections.observableArrayList();
	@FXML
	private HBox hBox2;
	//红色图片
	private Node redNode = new ImageView( new Image(getClass().getResourceAsStream("/image/red.png")) );
	//绿色图片
	private Node greenNode = new ImageView( new Image(getClass().getResourceAsStream("/image/green.png")) );
	@FXML
	private Button acceptBtn2;

    int testItem = 0;//Rfid查询标识
    String instrumentId="";//设备端口
	
	@FXML 
	private Hyperlink hyperlink2;    //错误信息
    List<String> msgList = new ArrayList<String>();
    /**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
	public  CommPortIdentifier portId;  
	//Enumeration 为枚举型类,在util中
	@SuppressWarnings("rawtypes")
	public  Enumeration portList;
	//RS-232的串行口 *
	public static SerialPort serialPort =null;
	public static boolean isUsed=false;
	//线程
	private Thread readThread;
	private static boolean isOpening =false;
		
	protected OutputStream outputStream = null;   
	  
	protected InputStream inputStream = null;
	public String monkeyId;
	@FXML
	private TextField receiveMonkeyId2;
	@FXML
	private TextField monkeyType;
	@FXML
	private TextField areaName;
	@FXML
	private TextField sex;
	@FXML
	private TextField ageType;
	@FXML
	private TextField weight;
	@FXML
	private TextField weightDate;
	@FXML
	private TextField keeperName;
	@FXML
	private TextField birthday;
	@FXML
	private TextField birthdayWeight;
	@FXML
	private TextField leavebreastDate;
	@FXML
	private TextField leavebreastWeight;
	@FXML
	private TextField fatherId;
	@FXML
	private TextField motherId;
	@FXML
	private TextField yjAddress;
	@FXML
	private TextArea remarks;
	@FXML
	private TextField chipid;
	
	/**
	 * 表示接受到数据（数据是否有增加）
	 */
	private boolean hasAdd = false;
	
	private static AnimalsInfoFrameController instance;
	public static AnimalsInfoFrameController getInstance(){
		if(null == instance){
			instance = new AnimalsInfoFrameController();
		}
		return instance;
	}
	
	/**
     * 以上为  串口 通讯 用   
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		
		//初始化状态
		hBox2.getChildren().add(redNode);
		
		//初始化   检测设备   下拉框
		initInstrumentCombobox();
						
	}

	/**
	 * 加载数据
	 */
	public void loadData(){
		hasAdd = false;
		//端口未打开
		isUsed=false;
		MainFrameController.comPortIsUsed=false;
		//按钮状态设为未接受数据
		acceptBtn2.setText("开始接收");
		hBox2.getChildren().clear();
		hBox2.getChildren().add(redNode);//状态    为红灯
		msgList.clear();
		hyperlink2.setVisible(false);
		
		initInstrumentCombobox();
	}

	/**添加错误信息
	 * @param msg
	 */
	public void addMsgList(String msg){
		msgList.add(msg);
		if(!hyperlink2.isVisible()){
			hyperlink2.setVisible(true);
		}
		hyperlink2.setText("提示信息（"+msgList.size()+"）");
		Stage stage = Main.stageMap.get("DataAcceptMeaage");
		
		if(null != stage && stage.isShowing()){
			DataAcceptMeaage.getInstance().loadData(msgList);
		}
	}
	
	/**
	 * 初始化   检测设备   下拉框
	 */
	private void initInstrumentCombobox() {
		instrumentComboBox2.setItems(data_instrumentComboBox);
		updateComboBox();
		instrumentComboBox2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					acceptBtn2.setDisable(false);//开始接收   按钮   可用
					instrumentId=newValue;			
				}else{
					acceptBtn2.setDisable(true);//开始接收   按钮  不可用
					instrumentId="";
				}
			}});
	}
	
	/**
	 * 更新 检测设备  下拉框     值
	 */
	private void updateComboBox(){
		data_instrumentComboBox.clear();//清空
		data_instrumentComboBox.add("COM4");
	}

	/**
	 * 错误信息  点击事件 TODO
	 */
	@FXML
	private void onHyperlinkAction(ActionEvent event){
		Stage stage = Main.stageMap.get("DataAcceptMeaage");
		
		if(null == stage){
			try {
				stage =new Stage();
				stage.initOwner(((javafx.scene.control.Control)event.getSource()).getScene().getWindow());
				DataAcceptMeaage.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DataAcceptMeaage",stage);
		}
		if(!stage.isShowing()){
			DataAcceptMeaage.getInstance().loadData(msgList);
			stage.show();
		}else{
			stage.toFront();
		}
	}
	/**
	 * 关闭按钮  点击事件
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		Window currentWindow =((javafx.scene.control.Control)event.getSource()).getScene().getWindow();
		if(isUsed){
			if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",currentWindow)){
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isUsed =false;
				MainFrameController.comPortIsUsed=false;
				currentWindow.hide();
			}
			
			//清空面板数据
			clearReceiveData();
			updateComboBox();
		}else{
			currentWindow.hide();
		}	
	}

	/**
	 * 开始接收  按钮事件  TODO
	 */
	@FXML
	public void onAcceptBtnAction(ActionEvent event){
		//获取按钮
		Button button =(Button) event.getSource();
		String comPort="";           //接入串口名称
		String baudRate="";             //波特率   
		String dataBit="";              //数据位   5 6 7 8
		String stopBit="";              //停止位    1,1.5,2
		String checkMode="";            //校验位    None，Even，Odd，Space，Mark
		if(button.getText().equals("开始接收")){
			//获取下拉框  选定的设备
			String instrumentId=instrumentComboBox2.getSelectionModel().getSelectedItem();
			if(null==instrumentId||"".equals(instrumentId)){
				Messager.showWarnMessage("请选择检测设备！");
				return;
			}else{
				comPort=instrumentId;
			}
			
			//获取系统中所有的通讯端口
			portList=CommPortIdentifier.getPortIdentifiers();
			
			boolean isExistComPort=false;   //设置的端口是否存在
			while(portList.hasMoreElements()){
				//强制转换为通讯端口类型
				portId=(CommPortIdentifier) portList.nextElement();
				if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
					if(portId.getName().equals(comPort)){//判断串口名是否相同
						isExistComPort=true;//串口存在
						try {
							if(portId.isCurrentlyOwned()){
								Messager.showWarnMessage("端口已经被占用！");
								return;
							}
							serialPort = (SerialPort) portId.open("ReadComm", 2000);//打开端口
							isUsed=true;
							MainFrameController.comPortIsUsed=true;
							hBox2.getChildren().clear();
							hBox2.getChildren().add(greenNode);//状态    为绿灯
							button.setText("关闭接收");
							
						} catch (PortInUseException  e1) {
							Messager.showWarnMessage("端口已经被占用！");
							e1.printStackTrace();
							return;
						}
				        // 给端口添加监听器  
				        try {   
				            serialPort.addEventListener(new SerialPortEventListener_monkeyId(testItem,comPort));   
				        } catch (TooManyListenersException e) {  
				            e.getMessage();  
				        }
						//侦听到串口有数据,触发串口事件
						serialPort.notifyOnDataAvailable(true);  
						isOpening =true;
						
							//设置串口初始化参数，依次是波特率，数据位，停止位和校验 
							try {
								 
					           serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
							} catch (UnsupportedCommOperationException e) {
								e.printStackTrace();
							}
						
					}
					//已经找到端口，退出while循环
					if(isExistComPort){
						break;
					}
				}
			}
			
			if(!isExistComPort){
				Messager.showWarnMessage("请检查设备连接或端口号设置！");
				return;
			}else{
				isExistComPort=false;
			}
			readThread=new Thread(this);
			readThread.start();//线程负责每接收一次数据休眠2秒钟
		}else {
			while(portId.isCurrentlyOwned()){
				//关闭关闭端口连接
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isOpening =false;
				
			}
			isUsed=false;
			MainFrameController.comPortIsUsed=false;
			button.setText("开始接收");
			hBox2.getChildren().clear();
			hBox2.getChildren().add(redNode);//状态    为绿灯			
		}
				
	}

	/**
	 * 签字管理  （数据确认） 
	 * @param event
	 */
	@FXML
	private void onESAction(ActionEvent event ){
		Stage stage = Main.stageMap.get("DataAcceptESFrame");
		if(null == stage){
			try {
				stage =new Stage();
				stage.initOwner(MainFrame.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				//DataAcceptESFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DataAcceptESFrame",stage);
		}
		if(!stage.isShowing()){
		//	DataAcceptESFrame.getInstance().loadData(datePane.getTextField().getText());
			stage.showAndWait();
			/*if("OK".equals(DataAcceptESFrame.getInstance().getType())){
				updateTableData(datePane.getTextField().getText());
				list.getSelectionModel().clearSelection();
			}*/
		}else{
			stage.toFront();
		}
	}


	/**
	 * 多线程（接收数据后休眠两秒）
	 */
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


	/**更新表格数据*/
	public void updateTableDate(String monkeyIdData) {
		hasAdd=true;
		monkeyId=monkeyIdData;
		receiveMonkeyId2.setText(monkeyIdData+"");
		Individual_Json ij=BaseService.getIndividualService().getIndividualJsonById(monkeyIdData);
		if (ij!=null) {
			monkeyType.setText(ij.getAnimaltypeName());
			areaName.setText(ij.getQuyu() + ":" + ij.getRoomName());
			sex.setText(ij.getSex() == 0 ? "公" : "母");
			ageType.setText(ij.getAgetype() == 1 ? "仔猴"
					: ij.getAgetype() == 2 ? "育成猴" : "成年猴");
			weight.setText(ij.getCurrentweight()==null?"":ij.getCurrentweight()+"");
			weightDate.setText(ij.getWeighingDate()==null?"":ij.getWeighingDate()+"");
			keeperName.setText(ij.getKeeperp());
			birthday.setText(ij.getBirthday());
			birthdayWeight.setText(ij.getBirthdayweight()==null?"":ij.getBirthdayweight()+"");
			leavebreastDate.setText(ij.getLeavebreastdate());
			leavebreastWeight.setText(ij.getLeavebreastweight()==null?"":ij.getLeavebreastweight()+"");
			fatherId.setText(ij.getFatherid());
			motherId.setText(ij.getMotherid());
			yjAddress.setText(ij.getYjaddress());
			remarks.setText(ij.getRemark());
			chipid.setText(ij.getChipid());
		}else{
			Messager.showWarnMessage("没有该动物信息，请与管理员联系");
		}
	}



	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AnimalsInfoFrame.fxml"));
		Scene scene = new Scene(root,780,365);
		stage.setScene(scene);
		stage.setTitle("动物详细信息");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		stage.setMinWidth(760);
		stage.setMinHeight(345);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				Window window = (Window) event.getSource();
				if(AnimalsInfoFrameController.isUsed){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",window)){
						AnimalsInfoFrameController.serialPort.removeEventListener();
						AnimalsInfoFrameController.serialPort.notifyOnDataAvailable(false); 
						AnimalsInfoFrameController.serialPort.close();
						AnimalsInfoFrameController.isUsed =false;
						MainFrameController.comPortIsUsed=false;
					}else{
						event.consume();
					}
					
				}
			}});
		
	}

	public boolean isHasAdd() {
		return hasAdd;
	}

	public void setHasAdd(boolean hasAdd) {
		this.hasAdd = hasAdd;
	}
	/**
	 * 关闭之后清空
	 */
	public void clearReceiveData(){
		receiveMonkeyId2.setText("");
		monkeyType.setText("");
		areaName.setText("");
		sex.setText("");
		ageType.setText("");
		weight.setText("");
		weightDate.setText("");
		keeperName.setText("");
		birthday.setText("");
		birthdayWeight.setText("");
		leavebreastDate.setText("");
		leavebreastWeight.setText("");
		fatherId.setText("");
		motherId.setText("");
		yjAddress.setText("");
		remarks.setText("");
		chipid.setText("");
		data_instrumentComboBox.clear();
	}

	public String getMonkeyId() {
		return monkeyId;
	}

	public void setMonkeyId(String monkeyId) {
		this.monkeyId = monkeyId;
	}
	
}

