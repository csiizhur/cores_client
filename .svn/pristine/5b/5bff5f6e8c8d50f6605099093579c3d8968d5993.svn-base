package com.lanen.view.balreg;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblBalReg;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

/**
 * 天平信息
 * @author Administrator
 *
 */
public class BalanceRge  extends Application implements Initializable{
	
   private Stage stage ;
   @FXML
   private TableView<TblBalanceReg> balRegTable;		//天平信息表
   private ObservableList<TblBalanceReg> data_balRegTable = FXCollections.observableArrayList();
   @FXML
   private TableColumn<TblBalanceReg,String> balCodeCol;        //天平编号
   @FXML
   private TableColumn<TblBalanceReg,String> balModelCol;        //天平型号
   @FXML
   private TableColumn<TblBalanceReg,String> balNameCol;        //天平名称
   @FXML
   private TableColumn<TblBalanceReg,String> precisionCol;        //精度（克）
   @FXML
   private TableColumn<TblBalanceReg,String> rangeCol;        //最大量程
   @FXML
   private TableColumn<TblBalanceReg,String> baudCol;        //波特率
   @FXML
   private TableColumn<TblBalanceReg,String> dataBitCol;        //数据位
   @FXML
   private TableColumn<TblBalanceReg,String> stopBitCol;        //停止位
   @FXML
   private TableColumn<TblBalanceReg,String> paritCol;        //校验方式
   @FXML
   private TableColumn<TblBalanceReg,String> validDateCol;        //检验有效期
   @FXML
   private TableColumn<TblBalanceReg,String> calCheckPointCol;    //校准检查点数 
   
   
   @FXML
   private Button toBalConnectButton;		//天平接入信息 button
   @FXML
   private Button delectBalButton;//删除天平
   @FXML
   private Button editBalButton;//编辑天平
   @FXML
   private Button addBalButton;//增加天平
   
   private static BalanceRge instance;
	
   public static void main(String[] args) {
		launch(args);
	}
	
	public static BalanceRge getInstance(){
		if(null == instance){
			instance = new BalanceRge();
		}
		return instance;
	}
	
	public BalanceRge() {
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		//初始化登记天平信息表
		initTbalRegTableTable();
		updateTable();
	}
	
	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData() {
			updateTable();
			BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_登录");
			User user = SaveUserInfo.getUser();
			boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
			if(privilege){
				addBalButton.setDisable(false);
			}else{
				addBalButton.setDisable(true);
			}
	}
	
	/**
	 * 初始化 table
	 */
	private void initTbalRegTableTable() {
		balRegTable.setItems(data_balRegTable);
		balCodeCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("balCode"));//天平编号
		balModelCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("balModel"));//天平型号
		balNameCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("balName")); //天平名称
		precisionCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("precision"));  //精度（克）
		rangeCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("range"));   //最大量程
		baudCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("baud"));   //波特率
		dataBitCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("dataBit"));    //数据位
		stopBitCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("stopBit"));    //停止位
		paritCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("parit"));    //校验方式
		validDateCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("validDate"));     //检验有效期
		calCheckPointCol.setCellValueFactory(new PropertyValueFactory<TblBalanceReg,String>("calCheckPoint"));//校准检查点数 
		balRegTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<TblBalanceReg>(){

			@Override
			public void changed(ObservableValue<? extends TblBalanceReg> arg0, TblBalanceReg arg1,
					TblBalanceReg newValue) {
				User user = SaveUserInfo.getUser();
				boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
				if(privilege){
					if(null != newValue){
						delectBalButton.setDisable(false);
						editBalButton.setDisable(false);
					}else{
						//toBalConnectButton.setDisable(true);
						delectBalButton.setDisable(true);
						editBalButton.setDisable(true);
					}
				}else{
					delectBalButton.setDisable(true);
					editBalButton.setDisable(true);
				}
				
			}

		});
	   
		
	}
	
	/**更新表格数据*/
	private void updateTable() {
		data_balRegTable.clear();
 
		List<TblBalReg> list = BaseService.getInstance().getTblBalRegService().findAll();
		if(null != list && list.size()>0){
			for(TblBalReg balreg:list){
				String balCode = balreg.getBalCode();
				String balModel = balreg.getBalModel();
				String balName = balreg.getBalName();
				String precision = balreg.getPrecision();
				String range = balreg.getRange()+"";
				String baud = balreg.getBaud()+"";
				String dataBit = balreg.getDataBit()+"";
				Integer stopBit = balreg.getStopBit();
				Integer parit = balreg.getParit();
				String validDate = "";
				if(null != balreg.getValidDate() && !balreg.getValidDate().equals("")){
					 validDate = DateUtil.dateToString(balreg.getValidDate(), "yyyy-MM-dd");
				}
				String calCheckPoint=balreg.getCalCheckPoint()+"";
				data_balRegTable.add(new TblBalanceReg(balCode,balModel,balName,precision,range,baud,dataBit,stopBit,parit,validDate,calCheckPoint));
				
			}

		}
		
	}
	

	public void selectRow(TblBalReg balreg){
		for(int i = 0;i<data_balRegTable.size();i++){
			if(data_balRegTable.get(i).getBalCode().equals(balreg.getBalCode())){
				balRegTable.getSelectionModel().select(i);
			}
		}
		
	}
	
	/**新建任务 按钮 onAction 相应函数(登记_新增天平  菜单项 )
	 * @param event
	 */
	@FXML
	private void onAddNewBalRegButton(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("AddBalanceRrge");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AddBalanceRrge.getInstance().start(stage);
				Main.stageMap.put("AddBalanceRrge",stage);
			}else{
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**新建任务 按钮 onAction 相应函数(登记_天平登记信息 菜单项 )
	 * @param event
	 */
	@FXML
	private void onToBalConnectButton(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("BalConnect");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				BalConnect.getInstance().start(stage);
				Main.stageMap.put("BalConnect",stage);
			}else{
				stage.show();
			}
			BalConnect.getInstance().loadData("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 编辑
	 * @param event
	 */
	@FXML
	private void onEditBalConnectButton(ActionEvent event){
		try {
			AddBalanceRrge.getInstance().start(getStage());
			int selectRow = balRegTable.getSelectionModel().getSelectedCells().get(0).getRow();
			String balCode = data_balRegTable.get(selectRow).getBalCode();
			TblBalReg tblBalReg =BaseService.getInstance().getTblBalRegService().getById(balCode);
			AddBalanceRrge.getInstance().loadData(tblBalReg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 校准参数设置的按键
	 * @param event
	 */
	@FXML
	private void parameterSetting(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("ParameterSetting");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				ParameterSetting.getInstance().start(stage);
				Main.stageMap.put("ParameterSetting",stage);
			}else{
				stage.show();
			}
			BalConnect.getInstance().loadData("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除天平
	 */
	@FXML
	private void delectBalRegButton(){
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
//			String userName = "";
//			Date currentDate = new Date();
//			User user = SignFrame.getUser();
//			if(null != user){
//				userName = user.getUserName();
//			}
			
			int selectRow = balRegTable.getSelectionModel().getSelectedCells().get(0).getRow();
			String balCode = data_balRegTable.get(selectRow).getBalCode();
			boolean falg = true;
			if(BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode) ){
				falg = false;
			}
			if(falg){
				data_balRegTable.remove(selectRow);
				BaseService.getInstance().getTblBalRegService().delete(balCode);
				showMessage("天平删除成功！");
		    }else{
			    	showErrorMessage("该天平与计算机处于启用状态无法删除！");
		    }
			
		}
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
	

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("BalanceRrge.fxml"));
		Scene scene = new Scene(root, 830, 493);
		stage.setScene(scene);
		stage.setTitle("天平信息");
		stage.setResizable(false);
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.initOwner(Main.mainWidow);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**天平登记信息*/
	public static class TblBalanceReg{
		private StringProperty balCode;      //天平编号
		private StringProperty balModel;     //天平型号
		private StringProperty balName;      //天平名称
		private StringProperty precision;   //精度（克）
		private StringProperty range;        //最大量程
		private StringProperty baud;         //波特率
		private StringProperty dataBit;		//数据位
		private StringProperty stopBit;		//停止位
		private StringProperty parit;		//校验方式
		private StringProperty validDate;	//检验有效期
		private StringProperty calCheckPoint;//校准检查点数
		public TblBalanceReg(){
			super();
		}

		public TblBalanceReg(String balCode,String balModel,String balName,String precision,String range,String baud,String dataBit,Integer stopBit,Integer parit,String validDate,String calCheckPoint){
			this.balCode = new SimpleStringProperty(balCode);
			this.balModel = new SimpleStringProperty(balModel);
			this.balName = new SimpleStringProperty(balName);
			this.precision = new SimpleStringProperty(precision);
			this.baud = new SimpleStringProperty(baud);
			this.range = new SimpleStringProperty(range);
			this.dataBit = new SimpleStringProperty(dataBit);
			String stopBitStr = "";
			if(null != stopBit && stopBit == 1){
				stopBitStr = "1";
			}else if(null != stopBit && stopBit == 15){
				stopBitStr = "1.5";
			}else if(null != stopBit && stopBit == 2){
				stopBitStr = "2";
			}
			this.stopBit = new SimpleStringProperty(stopBitStr);
			String paritStr = "";
			if(null != parit && parit == 0){
				paritStr = "None";
			}else if(null != parit && parit == 1){
				paritStr = "Odd";
			}else if(null != parit && parit == 2){
				paritStr = "Even";
			}else if(null != parit && parit == 3){
				paritStr = "Mark";
			}else if(null != parit && parit == 4){
				paritStr = "Space";
			}
			this.parit = new SimpleStringProperty(paritStr);
			this.validDate = new SimpleStringProperty(validDate);
			this.calCheckPoint = new SimpleStringProperty(calCheckPoint);
		}

		public String getBalCode() {
			return balCode.get();
		}

		public void setBalCode(String balCode) {
			this.balCode =  new SimpleStringProperty(balCode);
		}

		public String getBalModel() {
			return balModel.get();
		}

		public void setBalModel(String balModel) {
			this.balModel =new SimpleStringProperty(balModel);
		}

		public String getBalName() {
			return balName.get();
		}

		public void setBalName(String balName) {
			this.balName = new SimpleStringProperty(balName);
		}

		public String getRange() {
			return range.get();
		}

		public void setRange(String range) {
			this.range = new SimpleStringProperty(range);
		}

		public String getBaud() {
			return baud.get();
		}

		public void setBaud(String baud) {
			this.baud = new SimpleStringProperty(baud);
		}

		public String getDataBit() {
			return dataBit.get();
		}

		public void setDataBit(String dataBit) {
			this.dataBit = new SimpleStringProperty(dataBit);
		}

		public String getStopBit() {
			return stopBit.get();
		}

		public void setStopBit(String stopBit) {
			this.stopBit = new SimpleStringProperty(stopBit);
		}

		public String getParit() {
			return parit.get();
		}

		public void setParit(String parit) {
			this.parit = new SimpleStringProperty(parit);
		}

		public String getValidDate() {
			return validDate.get();
		}

		public void setValidDate(String validDate) {
			this.validDate = new SimpleStringProperty(validDate);
		}

		public String getPrecision() {
			return precision.get();
		}

		public void setPrecision(String precision) {
			this.precision =new SimpleStringProperty(precision) ;
		}

		public String getCalCheckPoint() {
			return calCheckPoint.get();
		}

		public void setCalCheckPoint(String calCheckPoint) {
			this.calCheckPoint = new SimpleStringProperty(calCheckPoint);
		}
		
	}
}
