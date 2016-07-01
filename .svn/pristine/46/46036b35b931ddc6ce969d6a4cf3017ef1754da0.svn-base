package com.lanen.view.balreg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblBalConnect;
import com.lanen.model.path.TblChipReader;
import com.lanen.util.BackgroundRunner;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

public class ChipReaderController extends Application implements Initializable {

	private static ChipReaderController instance;
	
	
	@FXML   
	private Label hostNameLabel;//计算机编号
	
	@FXML   
	private Label idLabel;//主键
	
	@FXML   
	private ComboBox<String> commNameComboBox;//接入端口初始化  
 	//接入端口下拉框的   值列表
    private ObservableList<String> commNamedata = FXCollections.observableArrayList();
	@FXML   
	private ComboBox<String> enabledComboBox;//是否可用的标志位
 	//是否可用的标志位下拉框的   值列表
    private ObservableList<String> enableddata = FXCollections.observableArrayList();
    
    
	
	private Stage stage ;
	
	public static void main(String[] args) {
			launch(args);
	}
	 
	public static ChipReaderController getInstance(){
		if(null == instance){
			instance = new ChipReaderController();
		}
		return instance;
	}
	
	public ChipReaderController() {
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initCommNameComboBox();
		initEnabledComboBox();
	}
	
	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData() {
		String houstName = SystemTool.getHostName();//计算机名称
		hostNameLabel.setText(houstName);
		List<TblBalConnect>  blist = BaseService.getInstance().getTblBalConnectService().isHaveChipReaderByHostname(houstName);
		if(null != blist && blist.size() > 0 ){
			TblBalConnect connect = blist.get(0);
			String commName = connect.getCommName();
			if(null != commName && !commName.equals("")){
				commNameComboBox.getSelectionModel().select(commName);
			}
			int enabled = connect.getEnabled();
			if(enabled == 0){
				enabledComboBox.getSelectionModel().select("否");
			}else{
				enabledComboBox.getSelectionModel().select("是");
			}
			idLabel.setText(connect.getId());
		}else{
			TblBalConnect balConnect = new TblBalConnect();
			List<TblChipReader>  list = BaseService.getInstance().getTblChipReaderService().findAll();
			TblChipReader chipReader = list.get(0);
			String id =BaseService.getInstance().getTblBalConnectService().getKey();
			idLabel.setText(id);
			balConnect.setId(id);
			balConnect.setBalCode(chipReader.getChipCode());
			balConnect.setHostName(houstName);
			balConnect.setType(2);
			BaseService.getInstance().getTblBalConnectService().save(balConnect);
			
		}
	}

	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 保存端口号等信息
	 * @param event
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		enabledComboBox.requestFocus();
		String id = idLabel.getText();
		String commName =  commNameComboBox.getSelectionModel().getSelectedItem();
		String enabled =enabledComboBox.getSelectionModel().getSelectedItem();
		TblBalConnect balConnect =BaseService.getInstance().getTblBalConnectService().getById(id);
		balConnect.setCommName(commName);
		if(enabled.equals("是")){
			balConnect.setEnabled(1);
		}else{
			balConnect.setEnabled(0);
		}
		BaseService.getInstance().getTblBalConnectService().update(balConnect);
		
		showMessage("设置成功！");
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		
	}
	
	/**
	 * 接入端口初始化  下拉框  initCommNameComboBox
	 */
	private void initCommNameComboBox() {
		//下拉框 onChange事件
		new BackgroundRunner() {
            String prompt;
            boolean isError;
            boolean ok = false;

            @Override
            public void background() throws Exception {
                try {
                	//初始化service
                    List<String> list=new ArrayList<String>();
                    list.add("是");
                    list.add("否");
                    enableddata.clear();
            		if(null!=list && list.size()>0){
            			for(String parit : list){
            				enableddata.add(parit);
            			}
            		}
            		
            		enabledComboBox.setItems(enableddata);
                    ok = true;
                } catch (Exception e) {
                    showError("连接服务器失败");
                    e.printStackTrace();
                    
                }
            }
            public void showError(String prompt) {
                this.prompt = prompt;
                this.isError = true;
                runForeground();
            }

            @Override
            public void foreground() throws Exception {
                if (isError) {
                    showErrorMessage(prompt);
                } else {
                    showMessage(prompt);
                }
            }

            @Override
            public void handleException(Exception e) throws Exception {
                showErrorMessage(e.getLocalizedMessage());
                e.printStackTrace();
            }

            @Override
            public void finish() throws Exception {
                if (ok) {}
            }
        }.run();	
	}
	
	/**
	 * 是否可用的标志位初始化  下拉框  enabledComboBox;//
	 */
	private void initEnabledComboBox() {
		//下拉框 onChange事件
		new BackgroundRunner() {
            String prompt;
            boolean isError;
            boolean ok = false;

            @Override
            public void background() throws Exception {
                try {
                	//初始化service
                    List<String> list=new ArrayList<String>();
                    list.add("COM1");
                    list.add("COM2");
                    list.add("COM3");
                    list.add("COM4");
                    list.add("COM5");
                    commNamedata.clear();
            		if(null!=list && list.size()>0){
            			for(String parit : list){
            				commNamedata.add(parit);
            			}
            		}
            		
            		commNameComboBox.setItems(commNamedata);
                    ok = true;
                } catch (Exception e) {
                    showError("连接服务器失败");
                    e.printStackTrace();
                    
                }
            }
            public void showError(String prompt) {
                this.prompt = prompt;
                this.isError = true;
                runForeground();
            }

            @Override
            public void foreground() throws Exception {
                if (isError) {
                    showErrorMessage(prompt);
                } else {
                    showMessage(prompt);
                }
            }

            @Override
            public void handleException(Exception e) throws Exception {
                showErrorMessage(e.getLocalizedMessage());
                e.printStackTrace();
            }

            @Override
            public void finish() throws Exception {
                if (ok) {}
            }
        }.run();	
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("ChipReader.fxml"));
		Scene scene = new Scene(root, 420, 239);
		stage.setScene(scene);
		stage.setTitle("芯片阅读器");
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
