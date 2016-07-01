package com.lanen.view.clinicaltest;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.view.sign.SignFrame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TblInstrumentVerificationFrame extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TblInstrumentVerificationFrame.fxml"));
		Scene scene =new Scene(root,790,516);
		stage.setScene(scene);
		stage.setTitle("设备检定信息设置");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setResizable(false);
		stage.show();
		 stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent event) {
					//记录设备登记日志
					  TblLog tblLog = new TblLog();
					  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
					  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
					  tblLog.setOperatType("退出面板");
					  tblLog.setOperatOject("设备检定信息设置");
//					  User user = SignFrame.getUser();
					  User user = SignFrame.getInstance().getSignUser();
					  if(null!=user){
						  tblLog.setOperator(user.getRealName());
					  }
					  tblLog.setOperatContent("");
					  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
					  BaseService.getTblLogService().save(tblLog);
				}});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
