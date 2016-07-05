package com.lanen.view;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import com.lanen.base.BaseService;
import com.lanen.model.Individual;
import com.lanen.model.Weight;
import com.lanen.model.clinicaltest.WeightTableData;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.DataAcceptMeaage;
import com.lanen.main.Main;
import datecontrol.DatePicker;
import datecontrol.Junit;

public class AnimalsWeightFrameController extends Application implements Initializable , Runnable  {

	@FXML
	private ComboBox<String> instrumentComboBox;
	private ObservableList<String> data_instrumentComboBox =FXCollections.observableArrayList();
	@FXML
	private HBox hBox;
	//红色图片
	private Node redNode = new ImageView( new Image(getClass().getResourceAsStream("/image/red.png")) );
	//绿色图片
	private Node greenNode = new ImageView( new Image(getClass().getResourceAsStream("/image/green.png")) );
	@FXML
	private Button acceptBtn;
	
	@FXML
	private HBox textHBox;
	private DatePicker datePane=null;
	@FXML
	private static ListView<String> list;//静态关键字
	private static ObservableList<String> data_list = FXCollections.observableArrayList();
	
	
	//表格
	@FXML
	private TableView<WeightTableData> table;
	private ObservableList<WeightTableData> data_table=FXCollections.observableArrayList();
	@FXML
	private TableColumn<WeightTableData,String> monkeyIdCol;           
	@FXML
	private TableColumn<WeightTableData,String> areaNameCol3;  
	@FXML
	private TableColumn<WeightTableData,String> animalSexCol3;            
	@FXML
	private TableColumn<WeightTableData,String> weightCol3;              
	@FXML
	private TableColumn<WeightTableData,String> weightRecorderCol;           
	@FXML
	private TableColumn<WeightTableData,String> weightDateCol3;       

    int testItem = 1;//动物称重标识
    String instrumentId="";//设备端口
	
	@FXML 
	private Hyperlink hyperlink;    //错误信息
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
	
	@FXML
	private TextField receiveMonkeyId;
	@FXML
	private TextField currentWeight;
	@FXML
	private ComboBox<String> weightRecorderName;
	private ObservableList<String> data_recorderNameComboBox =FXCollections.observableArrayList();
	private Map<String,Object> map_recorderNameComboBox = new HashMap<String,Object>();
	private String weightRecorderId;
	private boolean isHave=true;//是否已经添加进数据库
	/**
	 * 表示接受到数据（数据有增加）
	 */
	private boolean hasAdd = false;
	@FXML
	private Button dataSubmitButton;
	@FXML
	private Button dataAddButton;
	private static AnimalsWeightFrameController instance;
	public static AnimalsWeightFrameController getInstance(){
		if(null == instance){
			instance = new AnimalsWeightFrameController();
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
		hBox.getChildren().add(redNode);
		//初始化日期选择器
		initDate();
		
		//初始化   检测设备   下拉框
		initInstrumentCombobox();
				
		//初始化  表格
		initTableView();
		
		//初始化称重人
		initWeightRecorderNameCombobox();
		
	}

	/**
	 * 加载数据
	 */
	public void loadData(){
		if(instrumentComboBox.getSelectionModel().getSelectedItem()!=null){
			hasAdd=true;
		}
		datePane.getTextField().setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		//端口未打开
		isUsed=false;
		//按钮状态设为未接受数据
		acceptBtn.setText("开始接收");
		hBox.getChildren().clear();
		hBox.getChildren().add(redNode);
		msgList.clear();
		hyperlink.setVisible(false);
		
		initInstrumentCombobox();
		initWeightRecorderNameCombobox();
	}

	/**添加错误信息
	 * @param msg
	 */
	public void addMsgList(String msg){
		msgList.add(msg);
		if(!hyperlink.isVisible()){
			hyperlink.setVisible(true);
		}
		hyperlink.setText("提示信息（"+msgList.size()+"）");
		Stage stage = Main.stageMap.get("DataAcceptMeaage");
		
		if(null != stage && stage.isShowing()){
			DataAcceptMeaage.getInstance().loadData(msgList);
		}
	}
	/**
	 * 初始化日期选择器
	 */
	private void initDate(){
		datePane= new DatePicker(Locale.CHINA);
		datePane.getTextField().setEditable(false);
		datePane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		datePane.getCalendarView().todayButtonTextProperty().set("今天");
		datePane.getCalendarView().setShowWeeks(false);
		datePane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		datePane.setMaxWidth(148);
		
		textHBox.getChildren().add(datePane);
		datePane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					if(receiveMonkeyId.getText().toString()!=null){
						updateTableData2(receiveMonkeyId.getText().toString(),newValue);
					}
				}else{
					updateTableData2(null,null);
				}
			}
			});
	}
	
	/**
	 * 初始化   检测设备   下拉框
	 */
	private void initInstrumentCombobox() {
		instrumentComboBox.setItems(data_instrumentComboBox);
		updateComboBox();
		instrumentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					acceptBtn.setDisable(false);//开始接收   按钮   可用
					instrumentId=newValue;
					updateRecorderName();
				}else{
					acceptBtn.setDisable(true);//开始接收   按钮  不可用
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
				currentWindow.hide();
			}
			
			//面板数据清空
			receiveMonkeyId.clear();
			currentWeight.clear();
			data_table.clear();
			data_instrumentComboBox.clear();
			data_recorderNameComboBox.clear();
			updateComboBox();
		}else{
			currentWindow.hide();
		}
		//面板按钮复原
		datePane.setDisable(false);
		weightRecorderName.setDisable(false);
		currentWeight.setDisable(false);
		receiveMonkeyId.setDisable(false);
		dataSubmitButton.setDisable(false);
		dataAddButton.setDisable(true);
		instrumentComboBox.setDisable(false);
		
	}
	
	/**
	 * 初始化  表格
	 */
	private void initTableView() {
		 monkeyIdCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("animalCode"));             
		 areaNameCol3.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("quyuName"));  
		 animalSexCol3.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("animalSex"));           
		 weightCol3.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("weight"));              
		 weightRecorderCol.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("bossName"));           
		 weightDateCol3.setCellValueFactory(new PropertyValueFactory<WeightTableData,String>("weightDate"));         
	       
	     table.setItems(data_table);
	}
	/**
	 * 更新   表格   数据  （查询数据库）
	 */
	public void updateTableData(String  monkeyid){
		data_table.clear();
		
		if(null!=monkeyid&&!monkeyid.equals("")){
				List<?> list=BaseService.getWeightService().getAllWeightById(monkeyid);
				if(null!=list&&list.size()>0){
					for(Object obj:list){
						Object[]objs=(Object[])obj;
						
						String quyuName=objs[6]+":"+objs[7];
						data_table.add(new WeightTableData(
								objs[2]+"",
								(String)objs[3],
								(String)objs[4],
								(String)objs[1],
								quyuName,
								objs[5]+""=="0"?"公":"母"
								
								));
					}
					table.setItems(data_table);
				}
			
		}
	}
	
	/**
	 * 更新     表格数据
	 */
	private void updateTableData2(String monkeyid,String weightdate){
		data_table.clear();
		if(null!=monkeyid&&!monkeyid.equals("")){
			List<?> list=BaseService.getWeightService().getAllWeightById(monkeyid,weightdate);
			if(null!=list&&list.size()>0){
				for(Object obj:list){
					Object[]objs=(Object[])obj;
					
					String quyuName=objs[6]+":"+objs[7];
					data_table.add(new WeightTableData(
							objs[2]+"",
							(String)objs[3],
							(String)objs[4],
							(String)objs[1],
							quyuName,
							objs[5]+""=="0"?"公":"母"
							
							));
				}
				table.setItems(data_table);
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
		if(button.getText().equals("关闭接收")){
			//disable
			dataAddButton.setDisable(true);
			acceptBtn.setDisable(false);
			receiveMonkeyId.setDisable(false);
			currentWeight.setDisable(false);
			currentWeight.clear();
			weightRecorderName.setDisable(false);
			//data_recorderNameComboBox.clear();
			datePane.setDisable(false);
			dataSubmitButton.setDisable(false);
		}
		if(button.getText().equals("开始接收")){
			//获取下拉框  选定的设备
			String instrumentId=instrumentComboBox.getSelectionModel().getSelectedItem();
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
							hBox.getChildren().clear();
							hBox.getChildren().add(greenNode);//状态    为绿灯
							button.setText("关闭接收");
							if(datePane.getTextField().getText().equals(DateUtil.dateToString(new Date(), "yyyy-MM-dd"))){
							}else{
							}
							
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
				//关闭端口连接
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isOpening =false;
				
			}
			isUsed=false;
			button.setText("开始接收");
			hBox.getChildren().clear();
			hBox.getChildren().add(redNode);//状态    为绿灯			
		}
				
	}

	

	/**
		 * 删除相同主键的(数据库数据被更新，删除表格被更新的行，准备重新添加)
		 * @param dataId
		 */
	private void deleteTableData(String dataId) {
		if(data_table.size()<1){//表格没数据之间返回
			return;
		}else{
			for(WeightTableData obj:data_table){
				/*if(obj.getDataId().equals(dataId)){
					data_table.remove(obj);//找到   移除
					return;
				}*/
			}
		}
		
	}


	/**
	 * 数据确认
	 * @param event
	 */
	@FXML
	private void onESAction(ActionEvent event ){
		Window currentWindow =((javafx.scene.control.Control)event.getSource()).getScene().getWindow();
		if(receiveMonkeyId.getText()==null||"".equals(receiveMonkeyId.getText())){
			Messager.showWarnMessage("还未接收数据，不能确认");
			return;
		}
		if (receiveMonkeyId.getText()!=null&&!"".equals(receiveMonkeyId.getText())) {
			if(currentWeight.getText()==null||"".equals(currentWeight.getText())){
				if(Messager.showConfirm("提示", "称重信息为空", "是否确认", currentWindow)){
					datePane.setDisable(true);
					weightRecorderName.setDisable(true);
					currentWeight.setDisable(true);
					receiveMonkeyId.setDisable(true);
					dataSubmitButton.setDisable(true);
					dataAddButton.setDisable(false);
					instrumentComboBox.setDisable(true);
				}else{
					datePane.setDisable(false);
					weightRecorderName.setDisable(false);
					currentWeight.setDisable(false);
					receiveMonkeyId.setDisable(false);
					dataSubmitButton.setDisable(false);
					dataAddButton.setDisable(true);
					instrumentComboBox.setDisable(false);
				}
			}else{
				datePane.setDisable(true);
				weightRecorderName.setDisable(true);
				currentWeight.setDisable(true);
				receiveMonkeyId.setDisable(true);
				dataSubmitButton.setDisable(true);
				dataAddButton.setDisable(false);
				instrumentComboBox.setDisable(true);
			}
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
		receiveMonkeyId.setText(monkeyIdData+"");
	}



	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("AnimalsWeightFrame.fxml"));
		Scene scene = new Scene(root,780,565);
		stage.setScene(scene);
		stage.setTitle("动物称重数据接收");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		stage.setMinWidth(760);
		stage.setMinHeight(545);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				Window window = (Window) event.getSource();
				if(AnimalsWeightFrameController.isUsed){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",window)){
						AnimalsWeightFrameController.serialPort.removeEventListener();
						AnimalsWeightFrameController.serialPort.notifyOnDataAvailable(false); 
						AnimalsWeightFrameController.serialPort.close();
						AnimalsWeightFrameController.isUsed =false;
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
	 * 根据动物编号录入称重信息.判断是否重复add。
	 * 1,提交之前进行确认将空间置为灰色.
	 * @param even
	 */
	@FXML
	public void onAddWeightAction(ActionEvent even){
		String monkeyid=receiveMonkeyId.getText();
		String weightss=currentWeight.getText();
		Weight weight=new Weight();
		weight.setMonkeyid(monkeyid);
		if (weightss!=null&&!"".equals(weightss)) {
			weight.setWeight(Float.valueOf(weightss));
			weight.setDeleted((byte)0);
			weight.setWeighttype(14);
			weight.setWeightdate(datePane.getTextField().getText());
			weight.setBoss(weightRecorderId);
			/*if (isHave) {*/
				// 保存数据库
				BaseService.getWeightService().save(weight);
				Individual i=BaseService.getIndividualService().getByMonkeyid(monkeyid);
				if(i==null){
					Messager.showErrorMessage("该动物不存在");
					return;
				}
				i.setCurrentweight(Double.valueOf(weightss));
				i.setWeighingDate(DateUtil.stringToDate(datePane.getTextField().getText(), "yyyy-MM-dd"));
				BaseService.getIndividualService().update(i);
				isHave = false;
				// 更新称重信息
				data_table.clear();
				List<?> list = BaseService.getWeightService().getAllWeightById(
						monkeyid);
				for (Object ob : list) {
					Object[] objs = (Object[]) ob;
					WeightTableData wd = new WeightTableData();
					wd.setAnimalCode(monkeyid);
					wd.setAnimalSex(objs[5] + "" == "0" ? "公" : "母");
					wd.setQuyuName(objs[6] + ":" + objs[7]);
					if (objs[2] != null && !"".equals(objs[2])) {
						wd.setWeight(Float.valueOf(objs[2] + ""));
					}
					wd.setWeightDate((String) objs[3]);
					wd.setBossName((String) objs[4]);

					data_table.add(wd);
				}
			/*}else{
				Messager.showWarnMessage("不能重复添加该称重信息");
			}*/
		}else{
			Messager.showWarnMessage("请输入体重信息");
		}
		dataAddButton.setDisable(true);
		instrumentComboBox.setDisable(false);
		
	}
	/**
	 * 初始化   称重人   下拉框
	 */
	private void initWeightRecorderNameCombobox() {
		weightRecorderName.setItems(data_recorderNameComboBox);
		
		weightRecorderName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
						weightRecorderId=map_recorderNameComboBox.get(newValue).toString();
						
				}
		}});
		instrumentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				String monkeyid=receiveMonkeyId.getText();
				String weightss=currentWeight.getText();
				if (monkeyid!=null&&weightss!=null) {
					weightRecorderName.setDisable(false);
				}
			}
		});
	}
	/**
	 * 更新称重人
	 */
	private void updateRecorderName(){
		Map<String, Object> listMap = BaseService.getUserService().getAllEmployee();
		Iterator it=listMap.keySet().iterator();
		while(it.hasNext()){
			data_recorderNameComboBox.add(it.next()+"");
		}
		map_recorderNameComboBox=listMap;
	}
	/**
	 * 关闭之后清空面板数据
	 */
	public void clearReceiveData(){
		receiveMonkeyId.setText("");
		currentWeight.setText("");
		data_table.clear();
		data_instrumentComboBox.clear();
		data_recorderNameComboBox.clear();
	}
}

