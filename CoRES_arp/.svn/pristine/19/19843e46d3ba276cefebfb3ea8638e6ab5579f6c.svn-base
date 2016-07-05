package com.lanen.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import com.lanen.base.BaseService;
import com.lanen.model.Employee;
import com.lanen.model.Iplogin;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.AnimalsInfoFrameController;
import com.lanen.view.AnimalsObservationFrameController;
import com.lanen.view.AnimalsWeightFrameController;

public class MainFrame extends Application {
	
	
	public static Window mainWidow = null;
	Business business = new Business();
	
	private static MainFrame instance;
	public MainFrame(){
		Thread thread =   new Thread(new Runnable(){
			@Override
			public void run() {
				//实例化JFrame
				business.initMyJFrame();
			}});
		thread.setPriority(4);
		thread.start();
	}
	public static MainFrame getInstance(){
		if(null == instance){
			instance = new MainFrame();
		}
		return instance;
	}
	@Override
	public void start(Stage stage) throws Exception {
		 
		mainWidow=stage;
		Parent root = FXMLLoader.load(getClass().getResource("MainFrame.fxml"));
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/logo_tuopan.png")));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("CoRES灵长类实验动物资源管理系统");
		stage.setMinHeight(650);
		stage.setMinWidth(1100);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if(MainFrameController.comPortIsUsed){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
					    if (MainFrameController.dataAcceptItem==0) {
							AnimalsInfoFrameController.serialPort
									.removeEventListener();
							AnimalsInfoFrameController.serialPort
									.notifyOnDataAvailable(false);
							AnimalsInfoFrameController.serialPort.close();
							AnimalsInfoFrameController.isUsed = false;
							MainFrameController.comPortIsUsed=false;
						}
						if (MainFrameController.dataAcceptItem==1) {
							AnimalsWeightFrameController.serialPort
									.removeEventListener();
							AnimalsWeightFrameController.serialPort
									.notifyOnDataAvailable(false);
							AnimalsWeightFrameController.serialPort.close();
							AnimalsWeightFrameController.isUsed = false;
							MainFrameController.comPortIsUsed=false;
						}
						if (MainFrameController.dataAcceptItem==2) {
							AnimalsObservationFrameController.serialPort
									.removeEventListener();
							AnimalsObservationFrameController.serialPort
									.notifyOnDataAvailable(false);
							AnimalsObservationFrameController.serialPort
									.close();
							AnimalsObservationFrameController.isUsed = false;
							MainFrameController.comPortIsUsed=false;
						}
						//记录退出系统日志
						Iplogin tblLog = new Iplogin();
						Employee user = SaveUserInfo.getUser();
						if(null!=user){
							tblLog.setCreated_by(user.getId());
						}
						tblLog.setIp(SystemTool.getIPAddress());
						try {
							BaseService.getIploginService().save(tblLog);
						} catch (Exception e) {
							System.exit(1);
						}
						System.exit(1);
						return ;
					}else{
						event.consume();
					}
					
				}else{
					if(Messager.showSimpleConfirm("提示", "您确定要退出系统吗？")){
						//记录退出系统日志
						Iplogin tblLog = new Iplogin();
						Employee user = SaveUserInfo.getUser();
						if(null!=user){
							tblLog.setCreated_by(user.getId());
						}
						tblLog.setIp(SystemTool.getIPAddress());
						try {
							BaseService.getIploginService().save(tblLog);
						} catch (Exception e) {
							System.exit(1);
						}
						System.exit(1);
						return ;
					}else{
						event.consume();
					}
				}
			}});
		

		
		
	}
	 class Business {
			
		private boolean isInit = true;

		public synchronized void initMyJFrame() {
			while (!isInit) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//initJFrame();
			isInit = false;
			this.notify();
		}

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
