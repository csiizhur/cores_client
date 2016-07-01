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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblBalConnect;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignFrame;

/**
 * 天平接入信息
 * @author Administrator
 *
 */
public class BalConnect extends Application implements Initializable{
	
	private Stage stage ;

	@FXML   
	private TextField houstNameText;
	
	@FXML   
	private TextField typeText;
	
	@FXML
	private TableView<tblBalConnect>  tbalConnectTable;		//天平接入信息表
	private ObservableList<tblBalConnect> data_tbalConnectTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<tblBalConnect,String> hostnameCol;        //计算机编号
	@FXML
	private TableColumn<tblBalConnect,String> idCol;        //主键
	@FXML
	private TableColumn<tblBalConnect,String> balCodeCol;        //天平编号
	@FXML
	private TableColumn<tblBalConnect,String> commNameCol;        //接入端口
	@FXML
	private TableColumn<tblBalConnect,String> enabledCol;        //是否启用
	
	private static BalConnect instance;
	
	@FXML
    private Button updatEnabledButton;//启用/停用
	@FXML
	private Button delectBalConButton;//删除天平接入信息
	@FXML
	private Button editBalConButtion;//编辑天平接入信息
	@FXML
	private Button addBalConButtion;//增加天平接入信息
	
	public static BalConnect getInstance(){
		if(null == instance){
			instance = new BalConnect();
		}
		return instance;
	}
	
	public BalConnect() {
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initConnectTableTable();
	}
			

	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData(String type) {
	   typeText.clear();
	   typeText.setText(type);
	   typeText.setDisable(true);
	   String houstName = SystemTool.getHostName();//计算机名称
	   houstNameText.clear();
	   houstNameText.setText(houstName);
	   houstNameText.setDisable(true);
	   updateTable(houstName); 
	   User user = SaveUserInfo.getUser();
		boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
		if(privilege){
			addBalConButtion.setDisable(false);
		}else{
			addBalConButtion.setDisable(true);
		}
		
	}
	
	
	/**
	 * 初始化 table
	 */
	private void initConnectTableTable() {
		tbalConnectTable.setItems(data_tbalConnectTable);
		balCodeCol.setCellValueFactory(new PropertyValueFactory<tblBalConnect,String>("balCode"));//计算机名称
		idCol.setCellValueFactory(new PropertyValueFactory<tblBalConnect,String>("id"));//主键
		commNameCol.setCellValueFactory(new PropertyValueFactory<tblBalConnect,String>("commName"));//端口
		enabledCol.setCellValueFactory(new PropertyValueFactory<tblBalConnect,String>("enabled"));//使用状态
		tbalConnectTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<tblBalConnect>(){

			@Override
			public void changed(ObservableValue<? extends tblBalConnect> arg0, tblBalConnect arg1,
									tblBalConnect newValue) {
					String type = typeText.getText();
					if(type.equals("1")){	
								 User user = SaveUserInfo.getUser();
									boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
									if(privilege){
										if(null != newValue){
											String balCode = tbalConnectTable.getSelectionModel().getSelectedItem().getBalCode();
											String enabled = tbalConnectTable.getSelectionModel().getSelectedItem().getEnabled();
//											&& !BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode)
											if(BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode)){
												if( enabled.equals("是")  ){
												    updatEnabledButton.setDisable(false);
												    delectBalConButton.setDisable(true);
												}else{
												    updatEnabledButton.setDisable(true);
												    delectBalConButton.setDisable(false);
												}   
											}else{
												 updatEnabledButton.setDisable(false);
												 delectBalConButton.setDisable(false);
											}
											editBalConButtion.setDisable(false);
										}else{
											updatEnabledButton.setDisable(true);
											delectBalConButton.setDisable(true);
											editBalConButtion.setDisable(true);
										}
									}else{
										if(null != newValue){
											editBalConButtion.setDisable(false);
										}else{
											editBalConButtion.setDisable(true);
										}
										updatEnabledButton.setDisable(true);
										delectBalConButton.setDisable(true);
										
										
									}
					}else{
						if(null != newValue){
							editBalConButtion.setDisable(false);
							updatEnabledButton.setDisable(false);
							delectBalConButton.setDisable(false);
						}else{
							editBalConButtion.setDisable(true);
							updatEnabledButton.setDisable(true);
							delectBalConButton.setDisable(true);
						}
						
					}
				}

		});
	}
	
	/**更新表格数据*/
	private void updateTable(String houstName) {
		data_tbalConnectTable.clear();
		List<TblBalConnect> list = BaseService.getInstance().getTblBalConnectService().findByHostNameList(houstName);
		if(null != list && list.size()>0){
			for(TblBalConnect connect:list){
				String commName = connect.getCommName();
				String balCode = connect.getBalCode();
				Integer enabledStr = connect.getEnabled();
				String id = connect.getId();
				data_tbalConnectTable.add(new tblBalConnect(id,balCode,commName,enabledStr));
			}

		}
		
	}
	
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**新增
	 * @param event
	 */
	@FXML
	private void onaddBalConnectAction(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("AddBalConnect");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AddBalConnect.getInstance().start(stage);
				Main.stageMap.put("AddBalConnect",stage);
			}else{
				stage.show();
			}
			String type = typeText.getText();
			AddBalConnect.getInstance().loadData("add",type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**编辑
	 * @param event
	 */
	@FXML
	private void onEditBalConnectAction(ActionEvent event){
		try {
			AddBalConnect.getInstance().start(getStage());
			String balCode = tbalConnectTable.getSelectionModel().getSelectedItem().getBalCode();
			String id = tbalConnectTable.getSelectionModel().getSelectedItem().getId();
			TblBalConnect  connect =  BaseService.getInstance().getTblBalConnectService().getById(id);
			String type = typeText.getText();
			AddBalConnect.getInstance().loadData(balCode,"edit",connect,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**删除
	 * @param event
	 */
	@FXML
	private void delectBalConnectButton(ActionEvent event){
		
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
//			Date currentDate = new Date();
			User user = SignFrame.getUser();
			if(null != user){
				userName = user.getUserName();
			}
			boolean falg = true;
			String houstName = houstNameText.getText();//计算机名称
			String id = tbalConnectTable.getSelectionModel().getSelectedItem().getId();
			TblBalConnect balConnect= BaseService.getInstance().getTblBalConnectService().getById(id);
			Integer enabled = balConnect.getEnabled();
			if(enabled == 1 ){
				falg = false;
			}
			if(falg){
				BaseService.getInstance().getTblBalConnectService().delete(id);
				updateTable(houstName);
		    }else{
			    	showErrorMessage("请先停用该天平与其他计算机后再删除");
		    }
			
		}

	}
	
	
	/**
	 * 更新接入状态
	 */
	@FXML
	private void updateEnabledButton(){
		boolean falg = true;
		String balCode = tbalConnectTable.getSelectionModel().getSelectedItem().getBalCode();
		String id = tbalConnectTable.getSelectionModel().getSelectedItem().getId();
		TblBalConnect balConnect= BaseService.getInstance().getTblBalConnectService().getById(id);
		Integer enabled = balConnect.getEnabled();
		if(enabled == 1 ){
			balConnect.setEnabled(0);
		}else{
			String type = typeText.getText();
			if(type.equals("1")){	
				if(BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode)){
					 falg = false;
				}else{
					balConnect.setEnabled(1);
				}
			}else{
				balConnect.setEnabled(1);
			}
			
			
		}
		BaseService.getInstance().getTblBalConnectService().update(balConnect);
	    if(falg){
	    	String houstName = houstNameText.getText();//计算机名称
	    	updateTable(houstName);
			selectRow(balConnect);
	    }else{
	    	showErrorMessage("请先停用该天平与其他计算机后再启用");
	    }
		
	}

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("BalConnect.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("设备接入信息");
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
	
	public void selectRow(TblBalConnect balConnect){
		for(int i = 0;i<data_tbalConnectTable.size();i++){
			if(data_tbalConnectTable.get(i).getBalCode().equals(balConnect.getBalCode())){
				tbalConnectTable.getSelectionModel().select(i);
			}
		}
		
	}
	
	/**天平登记信息*/
	public static class tblBalConnect{
		private StringProperty id; 			//						varchar(20)
		private StringProperty hostName;	//计算机编号 				varchar(100)
		private StringProperty balCode;     //设备编号(TblBalReg)	varchar(60)
		private StringProperty commName;    //接入端口				varchar(10)
		private StringProperty enabled;        //是否启用       0:否      1:是
		
		public tblBalConnect(){
			super();
		}
		
		public tblBalConnect(String id,String balCode,String commName,Integer enabled){
			this.id = new SimpleStringProperty(id);
			this.balCode = new SimpleStringProperty(balCode);
			//this.hostName = new SimpleStringProperty(hostName);
			this.commName = new SimpleStringProperty(commName);
			String enabledStr="";
			if(enabled == 0){
				enabledStr = "否";
			}else {
				enabledStr = "是";
			}
			this.enabled = new SimpleStringProperty(enabledStr);
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id =  new SimpleStringProperty(id);
		}

		public String getHostName() {
			return hostName.get();
		}

		public void setHostName(String hostName) {
			this.hostName =  new SimpleStringProperty(hostName);
		}

		public String getBalCode() {
			return balCode.get();
		}

		public void setBalCode(String balCode) {
			this.balCode =  new SimpleStringProperty(balCode);
		}

		public String getCommName() {
			return commName.get();
		}

		public void setCommName(String commName) {
			this.commName =  new SimpleStringProperty(commName);
		}

		public String getEnabled() {
			return enabled.get();
		}

		public void setEnabled(String enabled) {
			this.enabled =  new SimpleStringProperty(enabled);
		}
		
		
		
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
}
