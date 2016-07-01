package com.lanen.zero.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.clinicaltest.DataAcceptFrameController;

public class MainFrame extends Application {
	
	
	public static Window mainWidow = null;
	//以下三个位打印报表用
	private JFrame jFrame;
	private JRViewer view = null;
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
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
//		Scene scene = new Scene(root,1190,670);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("CoRES临检系统");
		stage.setMinHeight(650);
		stage.setMinWidth(1100);
//		stage.setResizable(true);
//		stage.setWidth(1126);
		stage.show();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				if(DataAcceptFrameController.isUsed){
//					if(Confirm.create(MainFrame.mainWidow,"提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
					if(Messager.showConfirm("提示", "当前处于数据接收状态，退出系统将无法接收数据", "确定要退出系统吗？")){
					    DataAcceptFrameController.serialPort.removeEventListener();
						DataAcceptFrameController.serialPort.notifyOnDataAvailable(false); 
						DataAcceptFrameController.serialPort.close();
						DataAcceptFrameController.isUsed =false;
						
						
						//记录退出系统日志
						TblLog tblLog = new TblLog();
						tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
						tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
						tblLog.setOperatType("退出系统");
						tblLog.setOperatOject(SystemMessage.getSystemFullName());
						User user = SaveUserInfo.getUser();
						if(null!=user){
							tblLog.setOperator(user.getRealName());
						}
						tblLog.setOperatContent("");
						tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
						try {
							BaseService.getTblLogService().save(tblLog);
						} catch (Exception e) {
							System.exit(1);
						}
						System.exit(1);
						return ;
					}else{
						event.consume();
					}
					
				}else{
//					if(Confirm2.create(MainFrame.mainWidow,"您确定要退出系统吗？")){
					if(Messager.showSimpleConfirm("提示", "您确定要退出系统吗？")){
						//记录退出系统日志
						TblLog tblLog = new TblLog();
						tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
						tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
						tblLog.setOperatType("退出系统");
						tblLog.setOperatOject(SystemMessage.getSystemFullName());
						User user = SaveUserInfo.getUser();
						if(null!=user){
							tblLog.setOperator(user.getRealName());
						}
						tblLog.setOperatContent("");
						tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
						try {
							BaseService.getTblLogService().save(tblLog);
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
	 /**
	  * 打开报表窗口(外窗口调用)
	  * @param jp
	  * @param reportName
	  */
	public void openJFrame(final JasperPrint jp,final String reportName){
		 Thread thread =   new Thread(new Runnable(){
				@Override
				public void run() {
					//打开JFrame
					business.openMyJFrame(jp,reportName);
				}});
	        thread.start();
	}
	/** 实例化JFrame */
	private void initJFrame() {
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.setAlwaysOnTop(true);
		jFrame.setSize(850, 700);
		// stage.getIcons().add(new
		// Image(getClass().getResourceAsStream("../../../../image/icon.png")));
		URL url = this.getClass().getResource("/image/clinicaltest/icon.png");
		java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(url);
		jFrame.setIconImage(icon);
		// 设置居中显示
		Toolkit tookit = jFrame.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		int screenWidth = dimension.width;
		int screenHeight = dimension.height;
		int frameWidth = jFrame.getWidth();
		int frameHeight = jFrame.getHeight();
		jFrame.setLocation((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2);

		// 窗口打开后关闭（AlwaysOnTop属性）
		jFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				// super.windowOpened(e);
				JFrame jframe = (JFrame) e.getSource();
				jframe.setAlwaysOnTop(false);
			}

		});
		JasperReport jr = null;
		JasperPrint jp = null;
		try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("initReport.jrxml"));
			jp = JasperFillManager.fillReport(jr, null, new JRBeanCollectionDataSource(
					null));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		view = instanceView(jp);
		jFrame.setVisible(false);

	}
	/** 打开JFrame并加载表格 */
	private void openjFrame( JasperPrint jp, String reportName){
			
			jFrame.setTitle(reportName);
			if(null != view){
				jFrame.remove(view);
			}
			view =new JRViewer(jp);
			
			JRViewerToolbar toolbar = (JRViewerToolbar) view.getComponent(0);
			JRSaveContributor savePdf = toolbar.getSaveContributors()[1];
			JRSaveContributor saveDocx = toolbar.getSaveContributors()[4];
			JRSaveContributor saveXls = toolbar.getSaveContributors()[6];
			toolbar.setSaveContributors(new JRSaveContributor[] { savePdf, saveDocx, saveXls });
			jFrame.add(view);
			jFrame.setVisible(true);
			jFrame.setFocusable(true);
			jFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
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
			initJFrame();
			isInit = false;
			this.notify();
		}

		public synchronized void openMyJFrame(JasperPrint jp,String reportName){
			while (isInit) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//执行表格的打印
			openjFrame(jp,reportName);
			this.notify();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	public JRViewer instanceView(JasperPrint jp ) {
		view = new JRViewer(jp);
		return view;
	}
}
