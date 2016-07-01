package com.lanen.view.test;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import com.lanen.jsonAndModel.Json;
import com.lanen.model.User;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

public class AnatomyAnimalPage extends Application implements Initializable , Runnable {
	
	
	//会话id列表
	private static List<String> sessionIdList = null;
	private static List<String> taskIdList = null;
	private static Integer sessionType;
	@FXML
	private TableView<AnatomyAnimal> anatomyAnimalTable;		//动物表格
	private ObservableList<AnatomyAnimal> data_anatomyAnimalTable = FXCollections.observableArrayList();
	// 右击菜单
	private ContextMenu contextMenu = new ContextMenu();
	// 右击菜单项（删除）
	private	MenuItem menuItem_delete;
	@FXML
	private TableColumn<AnatomyAnimal,String> studyNoCol;        //专题编号
	@FXML
	private TableColumn<AnatomyAnimal,String> anatomyNumCol;       //解剖序号(任务序号)
	@FXML
	private TableColumn<AnatomyAnimal,String> animalCodeCol;       //动物编号
	@FXML
	private TableColumn<AnatomyAnimal,String> genderCol;        //动物性别
	@FXML
	private TableColumn<AnatomyAnimal,String> animalIdCol;        //动物Id号
	@FXML
	private TableColumn<AnatomyAnimal,String> anatomyOperatorCol;        //申请编号
	@FXML
	private TableColumn<AnatomyAnimal,String> anatomyBeginTimeCol;        //申请编号
	
	/**
	 * 显示已解剖单且尚未完成动物
	 */
	@FXML
	private CheckBox showAnatomyNoFinishAnimalCheckBox;
	boolean showAnatomyNoFinishAnimal = false;
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
	
	private static AnatomyAnimalPage instance;
	
	
	public static AnatomyAnimalPage getInstance(){
		if(null == instance){
			instance = new AnatomyAnimalPage();
		}
		return instance;
	}
	
	public AnatomyAnimalPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化table
		initTaskTable();
		initCheckBox();
	}
	
	
	private void initCheckBox() {
		showAnatomyNoFinishAnimalCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null != newValue && newValue){
					showAnatomyNoFinishAnimal = true;
				}else{
					showAnatomyNoFinishAnimal = false;
				}
				updateTable(taskIdList, sessionType);
			}
		});
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		//window = ((javafx.scene.control.Control)event.getSource()).getScene().getWindow();
		//1.选择数据  
		AnatomyAnimal anatomyAnimal = anatomyAnimalTable.getSelectionModel().getSelectedItem();
		if(null != anatomyAnimal){
			//2.保存解剖动物信息
			String taskId = anatomyAnimal.getTaskId();
			String sessionId = BaseService.getInstance().getTblPathSessionService()
					.getSessionIdByTaskIdSessionIdList(taskId,sessionIdList);
			try {
				Stage stage = Main.stageMap.get("AnatomyAnimalConfirm");
				if(null == stage){
					stage = new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					AnatomyAnimalConfirm.getInstance().start(stage);
					Main.stageMap.put("AnatomyAnimalConfirm",stage);
				}else{
					stage.show();
				}
				AnatomyAnimalConfirm.getInstance().loadData(taskId,sessionId,sessionType,
						anatomyAnimal.getStudyNo(),anatomyAnimal.getAnimalCode(),anatomyAnimal.getAnatomyOperator());
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if(false){
//				Map<String,Object> map = BaseService.getInstance().getTblAnatomyAnimalService().saveOrUpdate(taskId,sessionId,sessionType,anatomyAnimal.getAnimalCode());
//				
//				if(null != map && null != map.get("msg") && "".equals(map.get("msg"))){
//					//3,病理操作窗口上的动物增加一行数据，并选中最后一行
//					AnatomyProcessPage.getInstance().AddOneDataToAnimalTable(map);
//					//4.关闭当前窗口
//					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//				}else{
//					showErrorMessage(map.get("msg")+"");
//				}
//			}
			
		}else{
			showErrorMessage("请先选择动物！");
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
		window = anatomyAnimalTable.getScene().getWindow();
		window.hide();
	}
	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData(List<String> taskIdList,Integer sessionType, List<String> sessionIdList) {
		AnatomyAnimalPage.sessionType = sessionType;
		AnatomyAnimalPage.sessionIdList = sessionIdList;
		AnatomyAnimalPage.taskIdList = taskIdList;
		//更新表格数据
		updateTable(taskIdList, sessionType);
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				//  
				//打开串口
				openPort();
			}
			
		});
		if(sessionType == 1 || sessionType == 3 || sessionType== 5 || sessionType== 7){
//			menuItem_delete.setVisible(true);
			contextMenu.getItems().clear();
			contextMenu.getItems().add(menuItem_delete);
		}else{
			contextMenu.getItems().clear();
//			menuItem_delete.setVisible(false);
		}
		if(sessionType == 2 || sessionType == 6){
			int delHight = 0;
			if(!showAnatomyNoFinishAnimalCheckBox.isVisible()){
				delHight = 20;
			}
			showAnatomyNoFinishAnimalCheckBox.setVisible(true);
			showAnatomyNoFinishAnimal = false;
			showAnatomyNoFinishAnimalCheckBox.setSelected(false);
			
			anatomyAnimalTable.setPrefHeight(anatomyAnimalTable.getPrefHeight()-delHight);
		}else{
			int addHight = 0;
			if(showAnatomyNoFinishAnimalCheckBox.isVisible()){
				addHight = 20;
			}
			showAnatomyNoFinishAnimal = false;
			showAnatomyNoFinishAnimalCheckBox.setVisible(false);
			anatomyAnimalTable.setPrefHeight(anatomyAnimalTable.getPrefHeight()+addHight);
		}
	}

	public static void main(String[] args) {
		
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AnatomyAnimal.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("选择动物");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				closePort();
			}
		});
		stage.show();
		
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
	 * 初始化 table
	 */
	private void initTaskTable() {
		
		anatomyAnimalTable.setItems(data_anatomyAnimalTable);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("studyNo"));
		animalIdCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("animalId"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("gender"));
		anatomyNumCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("anatomyNum"));
		anatomyOperatorCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("anatomyOperator"));
		anatomyBeginTimeCol.setCellValueFactory(new PropertyValueFactory<AnatomyAnimal,String>("anatomyBeginTime"));
		
		anatomyAnimalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyAnimal>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyAnimal> arg0, AnatomyAnimal arg1,
					AnatomyAnimal newValue) {
				if(sessionType == 1 || sessionType == 3 || sessionType == 5 || sessionType == 7){
					if(null != newValue){
						if(null != newValue.getAnatomyRsn() && newValue.getAnatomyRsn() == 1){
								contextMenu.getItems().clear();
								contextMenu.getItems().add(menuItem_delete);
						}else{
							contextMenu.getItems().clear();
						}
					}else{
						contextMenu.getItems().clear();
						contextMenu.getItems().add(menuItem_delete);
					}
				}
				
			}
		});
		
		
		// 右击菜单项（标记不解剖）
		menuItem_delete = new MenuItem("从解剖任务中排除该动物");
		
		contextMenu.getItems().add(menuItem_delete);
		
		menuItem_delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				AnatomyAnimal selectItem = anatomyAnimalTable.getSelectionModel().getSelectedItem();
				if (null != selectItem) {
					if(Messager.showConfirm_animalCode("确认", selectItem.getStudyNo(),selectItem.getAnimalCode())){
						//病理负责人和对应的病理专题负责人
						List<String> userNameList = BaseService.getInstance().getTblAnatomyReqService()
								.getPathSdCodeList(selectItem.getStudyNo());
						Map<String,Object> map = Sign.openSignUserRsnFrame("签字", "排除原因", userNameList);
						if(null != map){
							//  
							String reason = (String) map.get("reason");
							User user = (User) map.get("user");
							
							Json json = BaseService.getInstance().getTblAnatomyReqService().setCancel(selectItem.getStudyNo(),selectItem.getAnimalCode(),user,reason);
							if(json.isSuccess()){
								updateTable(taskIdList, sessionType);
							}else{
								showErrorMessage(json.getMsg());
							}
						}
					}
				}
			}

		});
		// 右击菜单(在表格中实现)
		anatomyAnimalTable.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				@SuppressWarnings("unchecked")
				TableView<AnatomyAnimal> t = (TableView<AnatomyAnimal>) e.getSource();
				if (e.getButton() == MouseButton.SECONDARY) {
					contextMenu.show(t, e.getScreenX(), e.getScreenY());
				} else {
					contextMenu.hide();
				}
			}
		});
		
//		
//		anatomyAnimalTable.getSelectionModel()
//		.selectedItemProperty().addListener(new ChangeListener<AnatomyAnimal>(){
//
//			@Override
//			public void changed(ObservableValue<? extends AnatomyAnimal> arg0, AnatomyAnimal arg1,
//					AnatomyAnimal newValue) {
//			}
//		});
		//显示居中
		genderCol.setCellFactory(new Callback<TableColumn<AnatomyAnimal,String>,TableCell<AnatomyAnimal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyAnimal, String> call(
	    			 TableColumn<AnatomyAnimal, String> param) {
	    		 TableCell<AnatomyAnimal, String> cell =
	    				 new TableCell<AnatomyAnimal, String>() {
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
	    		 cell.setStyle("-fx-alignment: CENTER;");
	    		 return cell;
	    	 }
	     });
		
		anatomyNumCol.setCellFactory(new Callback<TableColumn<AnatomyAnimal,String>,TableCell<AnatomyAnimal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyAnimal, String> call(
	    			 TableColumn<AnatomyAnimal, String> param) {
	    		 TableCell<AnatomyAnimal, String> cell =
	    				 new TableCell<AnatomyAnimal, String>() {
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
	    		 cell.setStyle("-fx-alignment: CENTER;");
	    		 return cell;
	    	 }
	     });

	}
	
	
	
	/**更新表格数据
	 * @param taskIdList2
	 */
	private void updateTable(List<String> taskIdList2,Integer sessionType) {
		data_anatomyAnimalTable.clear();
		List<Map<String,Object>> taskMapList = 
//				BaseService.getInstance().getTblAnatomyAnimalService().getMapListByTaskIdListSessionType(taskIdList2, sessionType);
				BaseService.getInstance().getTblAnatomyAnimalService().getMapListByTaskIdListSessionType(taskIdList2, sessionType,showAnatomyNoFinishAnimal);
		if(null != taskMapList && taskMapList.size()>0){
			for(Map<String,Object> map:taskMapList){
				String studyNo = (String) map.get("studyNo");
				String animalId = (String) map.get("animalId");
				String animalCode = (String) map.get("animalCode");
				Integer gender = (Integer) map.get("gender");
				Integer anatomyNum = (Integer) map.get("anatomyNum");
				String taskId = (String) map.get("taskId");
				String anatomyOperator = (String) map.get("anatomyOperator");
				
				Integer anatomyRsn = (Integer) map.get("anatomyRsn");
				String anatomyBeginTime = (String) map.get("anatomyBeginTime");
				if(null != anatomyBeginTime && !"".equals(anatomyBeginTime.trim()) && anatomyBeginTime.trim().length() == 19){
					anatomyBeginTime = anatomyBeginTime.trim().substring(11, 19);
				}
				data_anatomyAnimalTable.add(new AnatomyAnimal(studyNo,anatomyNum,animalCode,gender,animalId,taskId,anatomyOperator,anatomyRsn,anatomyBeginTime));
			}
		}
		
	}
	
	
	/**
	 * 打开串口
	 */
	public  void openPort(){
		//  
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
							msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用1！");
							return;
						}
						serialPort = (SerialPort) portId.open("0001", 2000);//打开端口
						
						isUsePortId = portId;	//留备份，关闭用
						isUseingSerialPort = serialPort;
					} catch (PortInUseException  e1) {
						msgLabel.setText("芯片阅读器端口（"+comPort+"）被占用2！");
						//e1.printStackTrace();
						return;
					}
					//设置串口监听器
					try {
						serialPort.addEventListener(new SerialPortEventListener_chipReader());
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
	}
	
	/**解剖动物*/
	public static class AnatomyAnimal{
		private StringProperty studyNo; // 专题编号
		private StringProperty animalId;//申请编号
		private StringProperty animalCode;//动物编号
		private StringProperty gender;//动物性别
		private StringProperty anatomyNum;//解剖序号(任务序号)
		private StringProperty taskId;		//任务Id号
		private StringProperty anatomyOperator;		//任务Id号
		private IntegerProperty anatomyRsn;		//任务Id号
		private StringProperty anatomyBeginTime;	//解剖开始时间
		public AnatomyAnimal(){
			super();
		}

		public AnatomyAnimal(String studyNo,Integer anatomyNum,String animalCode,Integer gender,String animalId,String taskId,String anatomyOperator,Integer anatomyRsn,String anatomyBeginTime){
			this.studyNo = new SimpleStringProperty(studyNo);
			this.animalId = new SimpleStringProperty(animalId);
			this.animalCode = new SimpleStringProperty(animalCode);
			String genderStr = "";
			if(null != gender && gender == 1){
				genderStr = "♂";
			}else if(null != gender && gender == 2){
				genderStr = "♀";
			}
			this.gender = new SimpleStringProperty(genderStr);
			this.anatomyNum = new SimpleStringProperty(anatomyNum == null ? "":anatomyNum+"");
			this.taskId = new SimpleStringProperty(taskId);
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
			this.anatomyRsn = new SimpleIntegerProperty(anatomyRsn == null ? 0:anatomyRsn);
			
			setAnatomyBeginTime(anatomyBeginTime);
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

		public String getGender() {
			return gender.get();
		}

		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}

		public String getAnatomyNum() {
			return anatomyNum.get();
		}

		public void setAnatomyNum(String anatomyNum) {
			this.anatomyNum =new SimpleStringProperty(anatomyNum);
		}

		public String getAnimalId() {
			return animalId.get();
		}

		public void setAnimalId(String animalId) {
			this.animalId = new SimpleStringProperty(animalId);
		}

		public String getTaskId() {
			return taskId.get();
		}

		public void setTaskId(String taskId) {
			this.taskId = new SimpleStringProperty(taskId);
		}

		public String getAnatomyOperator() {
			return anatomyOperator.get();
		}

		public void setAnatomyOperator(String anatomyOperator) {
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
		}

		public Integer getAnatomyRsn() {
			return anatomyRsn.get();
		}

		public void setAnatomyRsn(Integer anatomyRsn) {
			this.anatomyRsn = new SimpleIntegerProperty(anatomyRsn);
		}

		public String getAnatomyBeginTime() {
			return anatomyBeginTime.get();
		}

		public void setAnatomyBeginTime(String anatomyBeginTime) {
			this.anatomyBeginTime = new SimpleStringProperty(anatomyBeginTime);
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

	/** 根据 iCardCode
	 * @param iCardCode
	 */
	public void selectAnimalByICardCode(String iCardCode) {
		// 
		Map<String,Object> studyNoAnimalCodeMap = BaseService.getInstance().getTblChipReaderService().getStudyNoAnimalCodeMapByICardCode(iCardCode);
		if(null != studyNoAnimalCodeMap){
			String studyNo = (String) studyNoAnimalCodeMap.get("studyNo");
			String animalCode = (String) studyNoAnimalCodeMap.get("animalCode");
			selectAniamlByStudyNoAnimalCode(studyNo,animalCode);
			//点击确定
			onConfirmBtnAction(null);
		}else{
			if(null == iCardCode || "".equals(iCardCode) ){
				showErrorMessage("请重新扫描！");
			}else{
				showErrorMessage("卡号（"+iCardCode+"）不存在！");
			}
		}
	}

	/**选中表格中的对应动物
	 * @param studyNo
	 * @param animalCode
	 */
	private void selectAniamlByStudyNoAnimalCode(String studyNo, String animalCode) {
		int i = 0 ;
		for(AnatomyAnimal obj:data_anatomyAnimalTable){
			if(obj.getStudyNo().equals(studyNo) && obj.getAnimalCode().equals(animalCode)){
				anatomyAnimalTable.getSelectionModel().select(obj);
				anatomyAnimalTable.scrollTo(i);
				return ;
			}
			i++;
		}
		anatomyAnimalTable.getSelectionModel().clearSelection();
		showErrorMessage("此次会话中无此动物（专题编号:"+studyNo+"，动物编号"+animalCode+"）！");
	}
	
	
}
