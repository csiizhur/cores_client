package com.lanen.view.clinicaltest;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import com.lanen.model.clinicaltest.ClinicalTestData;
import com.lanen.model.clinicaltest.DictComParam;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblDataSource;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.zero.main.Main;
import com.lanen.zero.main.MainFrame;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class DataAcceptFrameController extends Application implements Initializable , Runnable  {

	@FXML
	private ComboBox<String> testItemComboBox;
	@FXML
	private ComboBox<String> instrumentComboBox;
	private ObservableList<String> data_instrumentComboBox =FXCollections.observableArrayList();
	private Map<String,DictInstrument> map_instrumentComboBox = new HashMap<String,DictInstrument>();
	@FXML
	private HBox hBox;
	//红色图片
	private Node redNode = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/red.png")) );
	//绿色图片
	private Node greenNode = new ImageView( new Image(getClass().getResourceAsStream("/image/clinicaltest/green.png")) );
	@FXML
	private Button acceptBtn;
	
	@FXML
	private HBox textHBox;
	private DatePicker datePane=null;
	@FXML
	private static ListView<String> list;
	private static ObservableList<String> data_list = FXCollections.observableArrayList();
	
	
	//表格
	
	@FXML
	private static TableView<ClinicalTestData> table;
	private static ObservableList<ClinicalTestData> data_table=FXCollections.observableArrayList();
	private ObservableList<ClinicalTestData> data_table2= FXCollections.observableArrayList();
	@FXML
	private TableColumn<ClinicalTestData,String> dataIdCol;             //数据Id
	@FXML
	private TableColumn<ClinicalTestData,String> tblSpecimenIdCol;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestData,String> studyNoCol;            //课题编号
	@FXML
	private TableColumn<ClinicalTestData,String> reqNoCol;              //申请编号
	@FXML
	private TableColumn<ClinicalTestData,String> animalIdCol;           //动物Id
	@FXML
	private TableColumn<ClinicalTestData,String> animalCodeCol;         //动物编号
	@FXML
    private TableColumn<ClinicalTestData,String> specimenCodeCol;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestData,String> testItemCol;              //检验项目
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexCol;          //检验指标
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexAbbrCol;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestData,String> testDataCol;           //数据
	@FXML
    private TableColumn<ClinicalTestData,String> testIndexUnitCol;//检验指标单位
	@FXML
    private TableColumn<ClinicalTestData,String> collectionModeCol;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestData,String> collectionTimeCol;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestData,String> acceptTimeCol;        //接收时间
    @FXML
    private TableColumn<ClinicalTestData,String> esCol;               //签字    0：未签字   1：已签字
    @FXML
    private TableColumn<ClinicalTestData,String> confirmFlagCol;      //1.第一次接收   2，第二次接收

    int testItem = 0;//检测项目
    String instrumentId="";    //设备Id
    private TblDataSource tblDataSource = new TblDataSource();
	@FXML 
	HBox disableHBox1;    //让其他   控件无法使用（放在其他控件之上）
	@FXML 
	HBox disableHBox2;    //让其他   控件无法使用
	@FXML 
	private Hyperlink hyperlink;    //错误信息
	//
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
	
	private static int baudRateInt=0;
	private static int dataBitInt=0;
	private static int stopBitInt=0;
	private static int checkModeInt=0;
	
	/**
	 * 表示接受到数据（数据有增加）
	 */
	private boolean hasAdd = false;
	
	private static DataAcceptFrameController instance;
	public static DataAcceptFrameController getInstance(){
		if(null == instance){
			instance = new DataAcceptFrameController();
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
		//初始化   检测项目   下拉框
		initTestItemComboBox();
		
		//初始化   检测设备   下拉框
		initInstrumentCombobox();
		
		//初始化   列表框 
		initListView();
		
		//初始化  表格
		initTableView();
	}

	/**
	 * 加载数据
	 */
	public void loadData(){
		hasAdd = false;
		disableHBox2.setVisible(false);
		//检测项目，清空选择
		testItemComboBox.getSelectionModel().clearSelection();
		datePane.getTextField().setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
//		datePane.getTextField().setText("2015-03-12");
		//端口未打开
		isUsed=false;
		//按钮状态设为未接受数据
		acceptBtn.setText("开始接收");
		hBox.getChildren().clear();
		hBox.getChildren().add(redNode);//状态    为红灯
		disableHBox1.setVisible(false);
		
		msgList.clear();
		hyperlink.setVisible(false);
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
					// 更新  list   table     的值
					updateList(newValue);
					
					updateTableData(newValue);
				}else{
					updateList(null);
					updateTableData(null);
				}
			}
			});
//		datePane.getTextField().setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
	}
	
	/**
	 * 初始化   检测项目   下拉框
	 */
	private void initTestItemComboBox() {
		testItemComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					
					if("生化检验".equals(newValue)){
						testItem=1;
					}else if("血液检验".equals(newValue)){
						testItem=2;
					}else if("血凝检验".equals(newValue)){
						testItem=3;
					}else if("尿液检验".equals(newValue)){
						testItem=4;
					}
					if(testItem!=0){
						updateComboBox(testItem);
						instrumentComboBox.setDisable(false);
						
						String dateStr = datePane.getTextField().getText().toString();
						// 更新  list   table     的值
						updateList(dateStr);
						
						updateTableData(dateStr);
					}
				}else{
					testItem = 0;
					updateComboBox(testItem);
					String dateStr = datePane.getTextField().getText().toString();
					// 更新  list   table     的值
					updateList(dateStr);
					
					updateTableData(dateStr);
				}
			}});
	}
	/**
	 * 初始化   检测设备   下拉框
	 */
	private void initInstrumentCombobox() {
		instrumentComboBox.setItems(data_instrumentComboBox);
		instrumentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					acceptBtn.setDisable(false);//开始接收   按钮   可用
					instrumentId=newValue;
//					tblDataSource;
					InetAddress inetAddress= null;
					try {
						inetAddress=	InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					String computerName=inetAddress.getHostName();//计算机名
					String  instrumentName= map_instrumentComboBox.get(newValue).getInstrumentName();
					String softName="cores";
					String softVersion="1.0";
					tblDataSource.setDsHost(computerName);
					tblDataSource.setDsInstrumentId(instrumentId);
					tblDataSource.setDsInstrumentName(instrumentName);
					tblDataSource.setDsSoftware(softName);
					tblDataSource.setDsVersion(softVersion);
				}else{
					acceptBtn.setDisable(true);//开始接收   按钮  不可用
					instrumentId="";
				}
			}});
	}
	
	/**
	 * 更新 检测设备  下拉框     值
	 */
	private void updateComboBox(int testItem){
		data_instrumentComboBox.clear();//清空
		map_instrumentComboBox.clear();
		instrumentComboBox.setDisable(true);
		if(testItem > 0){
			List<DictInstrument> list=BaseService.getDictInstrumentService().findByTestItem(testItem);
			if(null!=list&&list.size()>0){
				for(DictInstrument obj:list){
					data_instrumentComboBox.add(obj.getInstrumentId());
					map_instrumentComboBox.put(obj.getInstrumentId(), obj);
				}
				instrumentComboBox.getSelectionModel().select(0);
			}
			instrumentComboBox.setDisable(false);
		}
	}
	
	/**
	 * 初始化   列表框 
	 */
	private void initListView() {
		list.setItems(data_list);
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!newValue.equals("")){
					if("    查看全部".equals(newValue)){
						table.setItems(data_table);
					}else{
						//  更新    table  的值
						updateTableData2(newValue);
					}
					
					
				}else{
					//  更新    table  的值
					table.setItems(data_table);
					
					
				}
				
			}});
	}
	/**
	 * 更新 ListView
	 * @param newValue
	 */
	private void updateList(String date) {
		data_list.clear();
		
		if(!date.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
			return;
		}
//		List<String> list=BaseService.getTblClinicalTestDataService().getStudyPlanByDate(date);
		List<String> list=BaseService.getTblClinicalTestDataService().getStudyPlanByDate(date);
		if(null!=list&&list.size()>0){
			for(String str:list){
				data_list.add(str);
			}
			DataAcceptFrameController.list.setItems(data_list);
		}
		if(data_list.size()>1){
			data_list.add("    查看全部");
		}
	}
	/**
	 * 更新list  （判断是否为新编号，是则添加  ，否则pass）
	 * @param studyNo
	 */
	private static void updateListData(String studyNo) {
		if(null!=data_list&&data_list.size()>0){
			data_list.remove("    查看全部");
			if(!data_list.contains(studyNo)){
				data_list.add(studyNo);
			}
		}else{//为空  ，直接添加
			data_list.add(studyNo);
		}
		//数据有两行
		if(null!=data_list && data_list.size()==2){
			data_list.add("    查看全部");
		}
		list.setItems(data_list);
		
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
//				stage.initModality(Modality.WINDOW_MODAL);
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
//			if(Confirm.create(MainFrame.mainWidow,"提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？")){
			if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",currentWindow)){
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isUsed =false;
				currentWindow.hide();
			}
		}else{
			currentWindow.hide();
		}
	}
	
	/**
	 * 初始化  表格
	 */
	private void initTableView() {
		 dataIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("dataId"));             //数据Id
		 tblSpecimenIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("tblSpecimenId"));  //标本接收登记Id
		 studyNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("studyNo"));            //课题编号
		 reqNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("reqNo"));              //申请编号
		 animalIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalId"));           //动物Id
		 animalCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("animalCode"));         //动物编号
	     specimenCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("specimenCode"));       //检验编号号
	     testItemCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testItem"));              //检验项目
	     testIndexCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndex"));          //检验指标
	     testIndexAbbrCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testData"));           //数据
	     testIndexUnitCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("testIndexUnit"));//检验指标单位
	     collectionModeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionMode"));        //数据采集方式
	     collectionTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("acceptTime"));        //接收时间
	     esCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("es"));               //签字    0：未签字   1：已签字
	     confirmFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestData,String>("confirmFlag"));      //1.第一次接收   2，第二次接收
	     testDataCol.setCellFactory(new Callback<TableColumn<ClinicalTestData,String>,TableCell<ClinicalTestData,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<ClinicalTestData, String> call(
	    			 TableColumn<ClinicalTestData, String> param) {
	    		 TableCell<ClinicalTestData, String> cell =
	    				 new TableCell<ClinicalTestData, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-right;");
	    		 return cell;
	    		 
	    	 }
	    	 
	     });
	     table.setItems(data_table);
	}
	/**
	 * 更新   表格   数据  （查询数据库）
	 */
	private void updateTableData(String  date){
		data_table.clear();
		
		if(null!=date&&!date.equals("")){
			if(date.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")){
				List<TblClinicalTestData> list=BaseService.getTblClinicalTestDataService().findByDate(date);
				if(null!=list&&list.size()>0){
					for(TblClinicalTestData obj:list){
						if(testItem != 0 && testItem != obj.getTestItem() ){
							continue;
						}
						String testItemStr ="";
						switch (obj.getTestItem()) {
						case 1:testItemStr ="生化检验";
							break;
						case 2:testItemStr ="血液检验";
							break;
						case 3:testItemStr ="血凝检验";
							break;
						case 4:testItemStr ="尿液检验";
							break;
						default:
							break;
						}
						String collectionTime="";
						if(null!=obj.getCollectionTime()){
							collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "HH:mm:ss");
						}
						String acceptTime="";
						if(null!=obj.getAcceptTime()){
							acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "HH:mm:ss");
						}
						data_table.add(new ClinicalTestData(
								obj.getDataId(),
								obj.getTblSpecimen().getSpecimenId(),
								obj.getStudyNo(),
								obj.getReqNo(),
								obj.getAnimalId(),
								obj.getAnimalCode(),
								obj.getSpecimenCode(),
								testItemStr,
								obj.getTestIndex(),
								obj.getTestIndexAbbr(),
								obj.getTestData(),
								obj.getTestIndexUnit(),
								obj.getCollectionMode()==1 ? "自动":"手动",
								collectionTime,
								acceptTime,
							    obj.getEs()==0?"否":"是",
							    obj.getConfirmFlag()+"",
							    obj.getValidFlag()==0?"否":"是"
								));
					}
					table.setItems(data_table);
				}
			}
		}
	}
	
	/**
	 * 更新     表格数据2（不查询数据库）
	 */
	private void updateTableData2(String studyNo){
		data_table2.clear();
		if(data_table.size()>0){
			for(ClinicalTestData obj:data_table){
				if(obj.getStudyNo().equals(studyNo)){
					data_table2.add(obj);
				}
			}
			
		}
		table.setItems(data_table2);
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
			String instrumentId=instrumentComboBox.getSelectionModel().getSelectedItem();
			if(null==instrumentId||"".equals(instrumentId)){
//				Alert2.create("请选择检测设备");
				Messager.showWarnMessage("请选择检测设备！");
				return;
			}else{
				DictInstrument instrument = map_instrumentComboBox.get(instrumentId);
				//串口参数
				DictComParam dictComParam =instrument.getDictComParam();
				if(null==dictComParam){
//					Alert2.create("请设置设备接口参数");
					Messager.showWarnMessage("请设置设备接口参数！");
					return;
				}else{
					//串口信息
					comPort=dictComParam.getComPort();
					baudRate=dictComParam.getBaudRate();
					dataBit=dictComParam.getDataBit();
					stopBit=dictComParam.getStopBit();
					checkMode=dictComParam.getCheckMode();
				}
			}
			if("".equals(comPort)||"".equals(baudRate)||"".equals(dataBit)||"".equals(stopBit)||"".equals(checkMode)){
//				Alert2.create("请设置设备接口参数");
				Messager.showWarnMessage("请设置设备接口参数！");
				return;
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
//							System.out.println(portId.getCurrentOwner());
							if(portId.isCurrentlyOwned()){
//								Alert2.create("端口已经被占用!");
								Messager.showWarnMessage("端口已经被占用！");
								return;
							}
							serialPort = (SerialPort) portId.open("ReadComm", 2000);//打开端口
							isUsed=true;
							hBox.getChildren().clear();
							hBox.getChildren().add(greenNode);//状态    为绿灯
							disableHBox1.setVisible(true);
							disableHBox2.setVisible(true);
							button.setText("关闭接收");
							if(datePane.getTextField().getText().equals(DateUtil.dateToString(new Date(), "yyyy-MM-dd"))){
								list.getSelectionModel().clearSelection();
							}else{
								datePane.getTextField().setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							}
//							serialPort = (SerialPort) driver.getCommPort("COM8", CommPortIdentifier.PORT_SERIAL); 
							
						} catch (PortInUseException  e1) {
//							Alert2.create("端口已经被占用!");
							Messager.showWarnMessage("端口已经被占用！");
							e1.printStackTrace();
							return;
						}
						//设置串口监听器
						try {
							switch (testItem) {
							case 1://生化数据
								serialPort.addEventListener(new SerialPortEventListener_bioChem(testItem,instrumentId,tblDataSource));
								break;
							case 2:
								serialPort.addEventListener(new SerialPortEventListener_hemat(testItem,instrumentId,tblDataSource));
								break;
							case 3://血凝
								serialPort.addEventListener(new SerialPortEventListener_bloodCoag(testItem,instrumentId,tblDataSource));
								break;
							case 4:
								serialPort.addEventListener(new SerialPortEventListener_urine(testItem,instrumentId,tblDataSource));
								break;

							default:
								break;
							}
							
						} catch (TooManyListenersException e1) {
							serialPort.close(); 
						}
						//侦听到串口有数据,触发串口事件
						serialPort.notifyOnDataAvailable(true);  
						isOpening =true;
						try {
//							String baudRate="";             //波特率   
//							String dataBit="";              //数据位   5 6 7 8
//							String stopBit="";              //停止位    1,1.5,2
//							String checkMode="";            //校验位    None，Even，Odd，Space，Mark
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
//							serialPort.setSerialPortParams(9600, 
//									SerialPort.DATABITS_8, 
//									SerialPort.STOPBITS_1, 
//									SerialPort.PARITY_NONE);
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
			//
			if(!isExistComPort){
//				Alert.create("请检查设备连接或端口号设置");
				Messager.showWarnMessage("请检查设备连接或端口号设置！");
				return;
			}else{
				isExistComPort=false;
			}
			readThread=new Thread(this);
			readThread.start();//线程负责每接收一次数据休眠2秒钟
		}else {//end if
			while(portId.isCurrentlyOwned()){
				//关闭关闭端口连接
				serialPort.removeEventListener();
				serialPort.notifyOnDataAvailable(false); 
				serialPort.close();
				isOpening =false;
				
//				timer.cancel();
			}
			isUsed=false;
			button.setText("开始接收");
			hBox.getChildren().clear();
			hBox.getChildren().add(redNode);//状态    为绿灯
			disableHBox1.setVisible(false);
			disableHBox2.setVisible(false);
			
		}
		
		
		
//		list.getSelectionModel().clearSelection();
		
	}

	

	/**
		 * 删除相同主键的(数据库数据被更新，删除表格被更新的行，准备重新添加)
		 * @param dataId
		 */
	private static void deleteTableData(String dataId) {
		if(data_table.size()<1){//表格没数据之间返回
			return;
		}else{
			for(ClinicalTestData obj:data_table){
				if(obj.getDataId().equals(dataId)){
					data_table.remove(obj);//找到   移除
					return;
				}
			}
		}
		
	}

	/**
	 * 表格添加数据
	 * @param tblClinicalTestData
	 */
	public void addTableData(TblClinicalTestData obj) {
		String testItemStr ="";
		switch (obj.getTestItem()) {
		case 1:testItemStr ="生化检验";
			break;
		case 2:testItemStr ="血液检验";
			break;
		case 3:testItemStr ="血凝检验";
			break;
		case 4:testItemStr ="尿液检验";
			break;
		default:
			break;
		}
		String collectionTime="";
		if(null!=obj.getCollectionTime()){
			collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "HH:mm:ss");
		}
		String acceptTime="";
		if(null!=obj.getAcceptTime()){
			acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "HH:mm:ss");
		}
		data_table.add(new ClinicalTestData(
				obj.getDataId(),
				obj.getTblSpecimen().getSpecimenId(),
				obj.getStudyNo(),
				obj.getReqNo(),
				obj.getAnimalId(),
				obj.getAnimalCode(),
				obj.getSpecimenCode(),
				testItemStr,
				obj.getTestIndex(),
				obj.getTestIndexAbbr(),
				obj.getTestData(),
				obj.getTestIndexUnit(),
				obj.getCollectionMode()==1 ? "自动":"手动",
				collectionTime,
				acceptTime,
			    obj.getEs()==0?"否":"是",
			    obj.getConfirmFlag()+"",
			    obj.getValidFlag()==0?"否":"是"
				));
		
		//增减了数据
//		DataAcceptFrame.isAdd = true;
		hasAdd = true;
		table.setItems(data_table);
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
				DataAcceptESFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DataAcceptESFrame",stage);
		}
		if(!stage.isShowing()){
			DataAcceptESFrame.getInstance().loadData(datePane.getTextField().getText());
			stage.showAndWait();
			if("OK".equals(DataAcceptESFrame.getInstance().getType())){
				updateTableData(datePane.getTextField().getText());
				list.getSelectionModel().clearSelection();
			}
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


	/**更新表格数据及listView数据*/
	public void updateTableDate(TblClinicalTestData tblClinicalTestData) {
		//删除相同主键的
		deleteTableData(tblClinicalTestData.getDataId());
		//添加数据
		addTableData(tblClinicalTestData);
		//更新listView
		updateListData(tblClinicalTestData.getStudyNo());
		
	}



	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DataAcceptFrame.fxml"));
		Scene scene = new Scene(root,780,565);
		stage.setScene(scene);
		stage.setTitle("数据接收");
//		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setMinWidth(760);
		stage.setMinHeight(545);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				Window window = (Window) event.getSource();
				if(DataAcceptFrameController.isUsed){
//					if(Confirm.create(MainFrame.mainWidow,"提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？")){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，关闭将无法接收数据", "确定要关闭吗？",window)){
						DataAcceptFrameController.serialPort.removeEventListener();
						DataAcceptFrameController.serialPort.notifyOnDataAvailable(false); 
						DataAcceptFrameController.serialPort.close();
						DataAcceptFrameController.isUsed =false;
					}else{
						event.consume();
					}
					
				}
			}});
//		stage.showAndWait();
		
	}

	public boolean isHasAdd() {
		return hasAdd;
	}

	public void setHasAdd(boolean hasAdd) {
		this.hasAdd = hasAdd;
	}

	
}

