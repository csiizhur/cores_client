package com.lanen.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import com.lanen.base.BaseService;
import com.lanen.model.Observation;
import com.lanen.model.clinicaltest.ObservationTableData;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.DataAcceptMeaage;
import com.lanen.main.Main;
import com.lanen.main.MainFrameController;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class AnimalsObservationFrameController extends Application implements Initializable , Runnable  {

	@FXML
	private ComboBox<String> instrumentComboBox1;
	private ObservableList<String> data_instrumentComboBox =FXCollections.observableArrayList();
	@FXML
	private HBox hBox1;
	//红色图片
	private Node redNode = new ImageView( new Image(getClass().getResourceAsStream("/image/red.png")) );
	//绿色图片
	private Node greenNode = new ImageView( new Image(getClass().getResourceAsStream("/image/green.png")) );
	@FXML
	private Button acceptBtn1;
	
	@FXML
	private HBox textHBox1;
	private DatePicker datePane=null;	
		
	@FXML
	private TableView<ObservationTableData> table1;
	private ObservableList<ObservationTableData> data_table=FXCollections.observableArrayList();
	@FXML
	private TableColumn<ObservationTableData,String> monkeyIdCol4;           
	@FXML
	private TableColumn<ObservationTableData,String> areaNameCol4;  
	@FXML
	private TableColumn<ObservationTableData,String> animalSexCol4;            
	@FXML
	private TableColumn<ObservationTableData,String> observationContentCol4;              
	@FXML
	private TableColumn<ObservationTableData,String> observationRecorderCol4;           
	@FXML
	private TableColumn<ObservationTableData,String> observationTimeCol4;      
	@FXML
	private TableColumn<ObservationTableData,String> observationDateCol4;
	
    int testItem = 2;//动物观察 标识
    String instrumentId="";    //设备端口
	
	@FXML 
	private Hyperlink hyperlink1;    //错误信息
    List<String> msgList = new ArrayList<String>();
    /**
     * 以下为  串口 通讯 用   
     */
	//检测系统中可用的通讯端口类 *
	public  CommPortIdentifier portId;  
	@SuppressWarnings("rawtypes")
	public  Enumeration portList;
	//RS-232的串行口 *
	public static SerialPort serialPort =null;
	public static boolean isUsed=false;
	//线程
	private Thread readThread;
	private static boolean isOpening =false;
	
	private static int baudRateInt=0;
	private static int dataBitInt=0;
	private static int stopBitInt=0;
	private static int checkModeInt=0;
	
	protected OutputStream outputStream = null;   
	  
	protected InputStream inputStream = null;
	
	@FXML
	private TextField receiveMonkeyId1;
	@FXML
	private TextArea observationContent;
	@FXML
	private ComboBox<String> observerRecorderName;
	private ObservableList<String> data_recorderNameComboBox =FXCollections.observableArrayList();
	private Map<String,Object> data_recorderNameMap=new HashMap<String,Object>();
	private String observerRecorderId=null;
	private boolean isHave=true;//是否已经添加进数据库
	/**
	 * 表示接受到数据（数据有增加）
	 */
	private boolean hasAdd = false;
	@FXML
	private Button dataSubmitButton;
	@FXML
	private Button observationAddButton;
	private static AnimalsObservationFrameController instance;
	public static AnimalsObservationFrameController getInstance(){
		if(null == instance){
			instance = new AnimalsObservationFrameController();
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
		hBox1.getChildren().add(redNode);
		
		datePane= new DatePicker(Locale.CHINA);
		datePane.getTextField().setEditable(false);
		datePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		datePane.getCalendarView().todayButtonTextProperty().set("今天");
		datePane.getCalendarView().setShowWeeks(false);
		datePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		datePane.setMaxWidth(148);
		
		textHBox1.getChildren().add(datePane);
		datePane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					if(receiveMonkeyId1.getText().toString()!=null){
						updateTableData2(receiveMonkeyId1.getText().toString(),newValue);
					}
				}else{
					updateTableData2(null,null);
				}
			}
			});
		//初始化   检测设备   下拉框
		initInstrumentCombobox();
		updateComboBox();		
		//初始化  表格
		initTableView();
		
		//初始化称重人
		initObservationRecorderNameCombobox();
	}

	/**
	 * 加载数据
	 */
	public void loadData(){
		if(instrumentComboBox1.getSelectionModel().getSelectedItem()!=null){
			hasAdd=true;
		}
		datePane.getTextField().setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		//端口未打开
		isUsed=false;
		//按钮状态设为未接受数据
		acceptBtn1.setText("开始接收");
		hBox1.getChildren().clear();
		hBox1.getChildren().add(redNode);//状态    为红灯
		msgList.clear();
		hyperlink1.setVisible(false);
		
		initInstrumentCombobox();
		updateComboBox();
		initObservationRecorderNameCombobox();
	}

	/**添加错误信息
	 * @param msg
	 */
	public void addMsgList(String msg){
		msgList.add(msg);
		if(!hyperlink1.isVisible()){
			hyperlink1.setVisible(true);
		}
		hyperlink1.setText("提示信息（"+msgList.size()+"）");
		Stage stage = Main.stageMap.get("DataAcceptMeaage");
		
		if(null != stage && stage.isShowing()){
			DataAcceptMeaage.getInstance().loadData(msgList);
		}
	}
	
	
	/**
	 * 初始化   检测设备   下拉框
	 */
	private void initInstrumentCombobox() {
		instrumentComboBox1.setItems(data_instrumentComboBox);
		instrumentComboBox1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					acceptBtn1.setDisable(false);//开始接收   按钮   可用
					instrumentId=newValue;
					updateObserverRecorderName();
				}else{
					acceptBtn1.setDisable(true);//开始接收   按钮  不可用
					instrumentId="";
				}
			}});
	}
	
	/**
	 * 更新 检测设备  下拉框     值
	 */
	private void updateComboBox(){
		data_instrumentComboBox.clear();
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
			
			//面板数据清空
			clearReceiveData();
			updateComboBox();
		}else{
			currentWindow.hide();
		}
		//面板按钮复原
		receiveMonkeyId1.setDisable(false);
		observationContent.setDisable(false);
		observerRecorderName.setDisable(false);
		datePane.setDisable(false);
		dataSubmitButton.setDisable(false);
		observationAddButton.setDisable(true);
		instrumentComboBox1.setDisable(false);
		
	}
	
	/**
	 * 初始化  表格
	 */
	private void initTableView() {
		 monkeyIdCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("animalCode"));             
		 areaNameCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("quyuName"));  
		 animalSexCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("animalSex"));           
		 observationContentCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("content"));              
		 observationRecorderCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("observerName"));           
		 observationTimeCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("observationTime"));
		 observationDateCol4.setCellValueFactory(new PropertyValueFactory<ObservationTableData,String>("observationDate"));
	     
	     table1.setItems(data_table);
	     
	     animalSexCol4.setCellFactory(new Callback<TableColumn<ObservationTableData,String>, TableCell<ObservationTableData,String>>() {
			
			@Override
			public TableCell<ObservationTableData, String> call(
							TableColumn<ObservationTableData, String> arg0) {
						TableCell<ObservationTableData, String> cell = new TableCell<ObservationTableData, String>() {
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								setText(empty?null:getItem() == null ? "" : getItem().toString());
							}

						};
						cell.setStyle("-fx-alignment: CENTER;");
						return cell;
					}
		});
	}
	/**
	 * 更新   表格   数据  （查询数据库）
	 */
	public void updateTableData(String  monkeyid){
		data_table.clear();
		
		if(null!=monkeyid&&!monkeyid.equals("")){
				List<?> list=BaseService.getObservationService().getAllObservationById(monkeyid);
				if(null!=list&&list.size()>0){
					for(Object obj:list){
						Object[]objs=(Object[])obj;
						
						data_table.add(new ObservationTableData(
								objs[5]+"",
								objs[7]+"",
								objs[6]+"",
								(String)objs[4],
								objs[2]+":"+objs[3],
								objs[1]+""=="0"?"公":"母",
								(String)objs[8]
								));
					}
					table1.setItems(data_table);
				}
			
		}
		/*observationDateCol4.setCellFactory(new Callback<TableColumn<ObservationTableData,String>, TableCell<ObservationTableData,String>>() {
			
			@Override
			public TableCell<ObservationTableData, String> call(
					TableColumn<ObservationTableData, String> arg0) {
				return new TableCell<ObservationTableData, String>(){
					protected void updateItem(final String str,boolean arg1){
						super.updateItem(str, arg1);
						if(arg1){
							setText(str);
							setGraphic(null);
						}else{
							setText(str);
							setGraphic(new CheckBox());
						}
					}
				};
			}
		});*/
	}
	
	/**
	 * 更新     表格数据
	 */
	private void updateTableData2(String monkeyid,String observationDate){
		data_table.clear();
		
		if(null!=monkeyid&&!monkeyid.equals("")){
			List<?> list=BaseService.getObservationService().getAllObservationById(monkeyid,observationDate);
			if(null!=list&&list.size()>0){
				for(Object obj:list){
					Object[]objs=(Object[])obj;
					
					data_table.add(new ObservationTableData(
							objs[5]+"",
							objs[7]+"",
							objs[6]+"",
							(String)objs[4],
							objs[2]+":"+objs[3],
							objs[1]+""=="0"?"公":"母",
							(String)objs[8]
							));
				}
				table1.setItems(data_table);
			}
		
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
			String instrumentId=instrumentComboBox1.getSelectionModel().getSelectedItem();
			if(null==instrumentId||"".equals(instrumentId)){
				Messager.showWarnMessage("请选择检测设备！");
				return;
			}else{	
				comPort=instrumentComboBox1.getSelectionModel().getSelectedItem();
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
							hBox1.getChildren().clear();
							hBox1.getChildren().add(greenNode);//状态    为绿灯
							button.setText("关闭接收");
						} catch (PortInUseException  e1) {
							Messager.showWarnMessage("端口已经被占用！");
							e1.printStackTrace();
							return;
						}
				        // 给端口添加监听器  
				        try {
				        	//参数 1：动物称重 2：动物观察 0：RFID查询
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
				//关闭端口连接
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isOpening =false;
			}
			isUsed=false;
			MainFrameController.comPortIsUsed=false;
			button.setText("开始接收");
			hBox1.getChildren().clear();
			hBox1.getChildren().add(redNode);			
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
		receiveMonkeyId1.setText(monkeyIdData+"");
	}



	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AnimalsObservationFrame.fxml"));
		Scene scene = new Scene(root,780,565);
		stage.setScene(scene);
		stage.setTitle("动物观察数据接收");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		stage.setMinWidth(760);
		stage.setMinHeight(545);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				Window window = (Window) event.getSource();
				if(AnimalsObservationFrameController.isUsed){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",window)){
						AnimalsObservationFrameController.serialPort.removeEventListener();
						AnimalsObservationFrameController.serialPort.notifyOnDataAvailable(false); 
						AnimalsObservationFrameController.serialPort.close();
						AnimalsObservationFrameController.isUsed =false;
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
	 * 录入之前-确认
	 */
	@FXML
	public void onDataSubmitBtnAction(ActionEvent event) {
		Window currentWindow = ((javafx.scene.control.Control) event
				.getSource()).getScene().getWindow();
		if (receiveMonkeyId1.getText() == null
				|| "".equals(receiveMonkeyId1.getText())) {
			Messager.showWarnMessage("数据还未接受");
			return;
		} else {
			if (observationContent.getText() == null
					|| "".equals(observationContent.getText())) {
				if (Messager.showConfirm("提示", "观察信息为空", "是否还要确认",
						currentWindow)) {
					receiveMonkeyId1.setDisable(true);
					observationContent.setDisable(true);
					observerRecorderName.setDisable(true);
					datePane.setDisable(true);
					dataSubmitButton.setDisable(true);
					observationAddButton.setDisable(false);
					instrumentComboBox1.setDisable(true);
				} else {
					receiveMonkeyId1.setDisable(false);
					observationContent.setDisable(false);
					observerRecorderName.setDisable(false);
					datePane.setDisable(false);
					dataSubmitButton.setDisable(false);
					observationAddButton.setDisable(true);
					instrumentComboBox1.setDisable(false);
				}
			} else {
				receiveMonkeyId1.setDisable(true);
				observationContent.setDisable(true);
				observerRecorderName.setDisable(true);
				datePane.setDisable(true);
				dataSubmitButton.setDisable(true);
				observationAddButton.setDisable(false);
				instrumentComboBox1.setDisable(true);
			}
		}

	}
	/**
	 * 根据动物编号录入称重信息.
	 * @param even
	 */
	@FXML
	public void onAddObservationAction(ActionEvent even){
		String nows=DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String monkeyid=receiveMonkeyId1.getText();
		String contents=observationContent.getText();
		Observation obser=new Observation();
		obser.setMonkeyid(monkeyid);
		if (contents!=null&&!"".equals(contents)) {
			if (observerRecorderId!=null&&!"".equals(observerRecorderId)) {
				obser.setObserver(Long.valueOf(observerRecorderId));
			}
			//obser.setObservationtime(Timestamp.valueOf(DateUtil.dateToString(new Date(), "yyyy-MM-dd hh:mm:ss")));
			obser.setObservationdate(nows.substring(0, 10));
			obser.setObservationtime(nows.substring(11, 19));
			obser.setDeleted(0);
			obser.setContent(contents);
			if (isHave) {
				// 保存数据库
				BaseService.getObservationService().save(obser);
				isHave=false;
				// 更新观察信息
				data_table.clear();
				List<?> list = BaseService.getObservationService()
						.getAllObservationById(monkeyid);
				for (Object ob : list) {
					Object[] objs = (Object[]) ob;
					ObservationTableData wd = new ObservationTableData();
					wd.setAnimalCode(monkeyid);
					wd.setAnimalSex(objs[1] + "" == "0" ? "公" : "母");
					wd.setQuyuName(objs[2] + ":" + objs[3]);
					wd.setContent((String) objs[5]);
					wd.setObservationTime(objs[6] + "");
					wd.setObserverName((String) objs[7]);
					wd.setObservationDate((String) objs[8]);
					data_table.add(wd);
				}
			}else{
				Messager.showWarnMessage("不能重复添加该观察信息");
			}	
		}else{
			Messager.showWarnMessage("请输入观察信息");
		}
		
	}
	/**
	 * 初始化   观察人   下拉框
	 */
	private void initObservationRecorderNameCombobox() {
		observerRecorderName.setItems(data_recorderNameComboBox);

		observerRecorderName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					
					observerRecorderId=data_recorderNameMap.get(newValue).toString();
						
				}
		}});
		instrumentComboBox1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				String monkeyid=receiveMonkeyId1.getText();
				String weightss=observationContent.getText();
				if (monkeyid!=null&&weightss!=null) {
					observerRecorderName.setDisable(false);
				}
			}
		});
	}
	/**
	 * 更新 观察人
	 * @author Administrator
	 *
	 * @param <String>
	 */
	private void updateObserverRecorderName(){
		Map<String, Object> listMap = BaseService.getUserService().getAllEmployee();
		Set set=listMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> m=(Map.Entry<String, Object>) it.next();
			data_recorderNameComboBox.add(m.getKey());
		}
		
		data_recorderNameMap=listMap;
	}
	private static class ListFormatCell<String> extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item!=null) {
				setText(item.toString());
			}
        }
    }
	
	/**
	 * 关闭之后清空面板数据
	 */
	public void clearReceiveData(){
		receiveMonkeyId1.setText("");
		observationContent.setText("");
		data_table.clear();
		data_instrumentComboBox.clear();
		data_recorderNameComboBox.clear();
	}
}

