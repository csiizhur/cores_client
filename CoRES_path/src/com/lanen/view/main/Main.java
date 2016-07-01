package com.lanen.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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

public class Main extends Application {

	private static Main instance;
	private Stage stage;
	public static Window mainWidow = null;
	private JFrame jFrame;
	private JRViewer view = null;
	static AnchorPane loginPage;// 登录面板
	static AnchorPane mainPage;// 主面板
	Business business = new Business();

	public static Map<String,Stage> stageMap = new HashMap<String,Stage>();
	
	// 密码录入错误次数
	public static int passwordErrorTimes = 0;

	public Main() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 实例化JFrame
				business.initMyJFrame();
			}
		});
		thread.setPriority(4);
		thread.start();
	}

	public static Main getInstance() {
		if (null == instance) {
			instance = new Main();
		}
		return instance;
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainWidow = stage;
		instance = this;
		this.stage = stage;
		loginPage = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		stage.setTitle("毒性病理管理系统v2.0");
		stage.getIcons().add(
				new Image(getClass().getResourceAsStream("/image/icon.png")));
		stage.setHeight(237);
		stage.setWidth(352);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.setScene(new Scene(loginPage));
		stage.show();
	}

	/**
	 * 进入主界面
	 * 
	 */
	public void enterMainScene() {
		this.stage.hide();
		this.stage.setResizable(true);
		
		  
		
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight()); 

//		this.stage.setX((bounds.getMaxX() - 1100D) / 2);
//		this.stage.setY((bounds.getMaxY() - 720D) / 2);
		this.stage.setMinHeight(700);
		this.stage.setMinWidth(1024);
//		this.stage.setHeight(700);
//		this.stage.setWidth(1116);
		this.stage.setMaxHeight(bounds.getMaxY());
		this.stage.setMaxWidth(bounds.getMaxX());
		// this.stage.setHeight(bounds.getMaxY());
		// this.stage.setWidth(bounds.getMaxX());
		try {
			mainPage = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.stage.setScene(new Scene(mainPage));
		this.stage.show();

		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
//				Window mainWidow = (javafx.stage.Stage) event.getSource();
				if (Messager.showSimpleConfirm("提出系统", "您确定要退出系统吗？")) {
					// 记录退出系统日志
					TblLog tblLog = new TblLog();
					tblLog.setSystemName(SystemMessage.getSystemName());// 系统全称
					tblLog.setSystemVersion(SystemMessage.getSystemVersion());// 系统版本
					tblLog.setOperatType("退出系统");
					tblLog.setOperatOject(SystemMessage.getSystemFullName());
					User user = SaveUserInfo.getUser();
					if (null != user) {
						tblLog.setOperator(user.getRealName());
					}
					tblLog.setOperatContent("");
					tblLog.setOperatHost(SystemTool.getHostName() + ","
							+ SystemTool.getIPAddress());
					try {

						BaseService.getInstance().getTblLogService().save(tblLog);
					} catch (Exception e) {
						System.exit(1);
					}
					System.exit(1);
					return;
				} else {
					event.consume();
				}
			}
		});
		// MainPageController.getInstance().showAnimation();
		MainPageController.getInstance().initPane();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 实例化JFrame
				business.initMyJFrame();
			}
		});
		thread.setPriority(4);
		thread.start();
	}

	/**
	 * 切换用户,切换到登录界面
	 * 
	 */
	public void enterLoginScene() {
		this.stage.hide();
		this.stage.setResizable(false);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		this.stage.setX((bounds.getMaxX() - 253D) / 2);
		this.stage.setY((bounds.getMaxY() - 237D) / 2);
		this.stage.setHeight(237);
		this.stage.setWidth(352);
		this.stage.setMaxHeight(237);
		this.stage.setMaxWidth(352);
		try {
			loginPage = FXMLLoader.load(getClass()
					.getResource("LoginPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.stage.setScene(new Scene(loginPage));
		this.stage.show();

	}

	/**
	 * 打开报表窗口(外窗口调用)
	 * 
	 * @param jp
	 * @param reportName
	 */
	public void openJFrame(final JasperPrint jp, final String reportName) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 打开JFrame
				business.openMyJFrame(jp, reportName);
			}
		});
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
		URL url = this.getClass().getResource("/image/icon.png");
		java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(url);
		jFrame.setIconImage(icon);
		// 设置居中显示
		Toolkit tookit = jFrame.getToolkit();
		Dimension dimension = tookit.getScreenSize();
		int screenWidth = dimension.width;
		int screenHeight = dimension.height;
		int frameWidth = jFrame.getWidth();
		int frameHeight = jFrame.getHeight();
		jFrame.setLocation((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 2);

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
			jr = JasperCompileManager.compileReport(getClass()
					.getResourceAsStream("initReport.jrxml"));
			jp = JasperFillManager.fillReport(jr, null,
					new JRBeanCollectionDataSource(null));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		view = instanceView(jp);
		jFrame.setVisible(false);

	}

	/** 打开JFrame并加载表格 */
	private void openjFrame(JasperPrint jp, String reportName) {

		jFrame.setTitle(reportName);
		if (null != view) {
			jFrame.remove(view);
		}
		view = new JRViewer(jp);

		JRViewerToolbar toolbar = (JRViewerToolbar) view.getComponent(0);
		JRSaveContributor savePdf = toolbar.getSaveContributors()[1];
		JRSaveContributor saveDocx = toolbar.getSaveContributors()[4];
		JRSaveContributor saveXls = toolbar.getSaveContributors()[6];
		toolbar.setSaveContributors(new JRSaveContributor[] { savePdf,
				saveDocx, saveXls });
		jFrame.add(view);
		jFrame.setVisible(true);
		jFrame.setFocusable(true);
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

		public synchronized void openMyJFrame(JasperPrint jp, String reportName) {
			while (isInit) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 执行表格的打印
			openjFrame(jp, reportName);
			this.notify();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getStage() {
		return stage;
	}

	public JFrame getJFrame() {
		return jFrame;
	}

	public JRViewer getView() {
		return view;
	}

	public JRViewer instanceView(JasperPrint jp) {
		view = new JRViewer(jp);
		return view;
	}

}
