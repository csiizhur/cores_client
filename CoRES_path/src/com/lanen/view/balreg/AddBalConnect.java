package com.lanen.view.balreg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

public class AddBalConnect extends Application implements Initializable{
	
	private Stage stage;
	
//	@FXML   
//	private TextField balCodeText;//天平编号
	   
	@FXML   
	private TextField hostnameText;//计算机名称
	
	@FXML   
	private TextField idText;//主键
	@FXML   
	private ComboBox<String> balRegComboBox;//天平初始化  
 	//接入端口下拉框的   值列表
    private ObservableList<String> balRegdata = FXCollections.observableArrayList();
	@FXML   
	private ComboBox<String> commNameComboBox;//接入端口初始化  
 	//接入端口下拉框的   值列表
    private ObservableList<String> commNamedata = FXCollections.observableArrayList();
	@FXML   
	private ComboBox<String> enabledComboBox;//是否可用的标志位
 	//是否可用的标志位下拉框的   值列表
    private ObservableList<String> enableddata = FXCollections.observableArrayList();
    
    @FXML   
	private TextField typeText;
	
	private static AddBalConnect instance;
	
    public static void main(String[] args) {
		launch(args);
	}
	
	public static AddBalConnect getInstance(){
		if(null == instance){
			instance = new AddBalConnect();
		}
		return instance;
	}
	
	public AddBalConnect() {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initCommNameComboBox_new();
		initEnabledComboBox_new();
	}
	private void initEnabledComboBox_new() {
		// TODO Auto-generated method stub
		enabledComboBox.setItems(enableddata);
		enableddata.add("是");
		enableddata.add("否");
	}

	private void initCommNameComboBox_new() {
		//TODO
		commNameComboBox.setItems(commNamedata);
		commNamedata.add("COM1");
		commNamedata.add("COM2");
		commNamedata.add("COM3");
		commNamedata.add("COM4");
		commNamedata.add("COM5");
		commNamedata.add("COM6");
		commNamedata.add("COM7");
		commNamedata.add("COM8");
		commNamedata.add("COM9");
	}

	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData(String addOrEdit,String type) {
		   String houstName = SystemTool.getHostName();
		   typeText.clear();
		   typeText.setText(type);
		   typeText.setDisable(true);
		   idText.clear();
		   hostnameText.clear();
		   hostnameText.setText(houstName);
		   hostnameText.setDisable(true);
		   updateBalRegComboBox();
		   updateCommComboBox();
		   updateEnableComboBox();
		   if(addOrEdit.equals("edit")){
			   
		   }
		  
	}
	public void loadData(String balCode,String addOrEdit,	TblBalConnect  connect,String type) {
		
		   typeText.setText(type);
		   typeText.setDisable(true);
		   
		   balRegdata.clear();
   		   balRegdata.add(balCode);
   		   balRegComboBox.setItems(balRegdata);
   		   balRegComboBox.getSelectionModel().select(balCode);
   		   balRegComboBox.setDisable(true);
		   
   		   if(addOrEdit.equals("edit")){
			   hostnameText.clear();
			   hostnameText.setText(connect.getHostName());
			   idText.clear();
			   idText.setText(connect.getId());
			   
			   updateCommComboBox();
			   updateEnableComboBox();
		   }
		   User user = SaveUserInfo.getUser();
		   boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
		   if(privilege){
			   enabledComboBox.setDisable(false);
		   }else{
			   enabledComboBox.setDisable(true);
		   }
		   hostnameText.setDisable(true);
		   
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("addBalConnect.fxml"));
		Scene scene = new Scene(root, 591, 230);
		stage.setScene(scene);
		stage.setTitle("设备接入信息");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	/**
	 * 保存天平接入信息
	 * @throws Exception
	 */
	@FXML
	public void saveAddBalConnect(ActionEvent event) throws Exception {
		Boolean right = true;
		String id = idText.getText().toString();//主键
		String balCode = balRegComboBox.getSelectionModel().getSelectedItem();//天平编号
		String hostname = hostnameText.getText().toString();//计算机名称
		String commName = commNameComboBox.getSelectionModel().getSelectedItem();//端口
		String enabledStr = enabledComboBox.getSelectionModel().getSelectedItem();//可用标记
		TblBalConnect balConnect ;
		if(null == id || id.equals("")){
			 balConnect = new TblBalConnect();
			 balConnect.setId(BaseService.getInstance().getTblBalConnectService().getKey());
		}else{
			 balConnect = BaseService.getInstance().getTblBalConnectService().getById(id);
		}
		String errorStr = "";
		if( null == balCode || balCode.equals("")){
			errorStr = "请输入正确的设备编号";
			right = false;
		}else if(null == hostname || hostname.equals("")){
			errorStr = "请输入正确的计算机名称或IP";
			right = false;
		}else if(null == commName || commName.equals("")){
			errorStr = "请选择正确的端口号";
			right = false;
		}else if(null == enabledStr || enabledStr.equals("")){
			errorStr = "请选择是否启用";
			right = false;
		}
		Integer  enabled = null;
		if(null == id || id.equals("")){
			if( null != balCode && !balCode.equals("") && null != hostname && !hostname.equals("") &&
					BaseService.getInstance().getTblBalConnectService().isExistByHostnameAndBalCode(hostname,balCode)){
				errorStr = "此计算机已和该设备接入";
				right = false;
			}
		}
		String type = typeText.getText();
//		if(type.equals("1")){
//			if(null == id || id.equals("")){
//				if(  enabledStr.equals("是") && BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode)){
//					errorStr = "此天平已在其他计算机启动";
//					right = false;
//				}
//			}else{
//				if( balConnect.getEnabled() != 1 && enabledStr.equals("是") && BaseService.getInstance().getTblBalConnectService().isEnabledByBalCode(balCode)){
//					errorStr = "此天平已在其他计算机启动";
//					right = false;
//				}
//			}	
//		}
			
		
			
		if(right){
			if(enabledStr.equals("是")){
				enabled = 1;
			}else if(enabledStr.equals("否")){
				enabled = 0;
			}
		
			
			
			balConnect.setBalCode(balCode);
			balConnect.setHostName(hostname);
			balConnect.setCommName(commName);
			balConnect.setEnabled(enabled);
			if(type.equals("1")){
				balConnect.setType(1);
			}else{
				balConnect.setType(2);
			}
		
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			try {
				if(null == id || id.equals("")){
					BaseService.getInstance().getTblBalConnectService().save(balConnect);
				}else{
					BaseService.getInstance().getTblBalConnectService().update(balConnect);
				}
			
				BalConnect.getInstance().loadData(type);
				BalConnect.getInstance().selectRow(balConnect);
				((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			showErrorMessage(errorStr);
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
	
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	
	/**
	 * 天平初始化  下拉框  initBalRegComboBox
	 */
	private void updateBalRegComboBox() {
                	//初始化service
        List<String> list=new ArrayList<String>();
        String hostname = hostnameText.getText().toString();//计算机名称
        List<Map<String,Object>>  bList= BaseService.getInstance().getTblBalRegService().getNoHaveTblBalRegByHostName(hostname); 
        if(null != bList && bList.size() > 0){
        	for(Map<String,Object> balReg:bList){
        		list.add((String)balReg.get("balCode"));
        	}
        }
        balRegdata.clear();
		if(null!=list && list.size()>0){
			for(String parit : list){
				balRegdata.add(parit);
			}
		}
		
		balRegComboBox.setItems(balRegdata);
	}
	
	
	/**
	 * 接入端口初始化  下拉框  initCommNameComboBox
	 */
	private void updateEnableComboBox() {
		
    	//页面加载完毕，控件置为可用
    	enabledComboBox.setDisable(false);
    	if(enableddata.size()>0){
    		//enabledComboBox.getSelectionModel().selectFirst();
    		String id = idText.getText().toString();//主键
    		if(null != id && !id.equals("")){
    			TblBalConnect balConnect = BaseService.getInstance().getTblBalConnectService().getById(id);
        		Integer enabled = balConnect.getEnabled();
        		if(enabled == 1){
        			enabledComboBox.getSelectionModel().select("是");
        		}else{
        			enabledComboBox.getSelectionModel().select("否");
        		}
        		User user = SaveUserInfo.getUser();
        		boolean  privilege = BaseService.getInstance().getUserService().isHasPrivilege(user.getUserName(),"天平管理_登记");
        		if(privilege){
        			   enabledComboBox.setDisable(false);
        		}else{
        			   enabledComboBox.setDisable(true);
        		}
    		}
    		
    		
    		
    	}
	}
	
	/**
	 * 
	 */
	private void updateCommComboBox() {
    	commNameComboBox.setDisable(false);
    	if(commNamedata.size()>0){
    		//commNameComboBox.getSelectionModel().selectFirst();
    		String id = idText.getText().toString();//主键
    		if(null != id && !id.equals("")){
        		TblBalConnect balConnect = BaseService.getInstance().getTblBalConnectService().getById(id);
        		String commName = balConnect.getCommName();
        		commNameComboBox.getSelectionModel().select(commName);
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
}
