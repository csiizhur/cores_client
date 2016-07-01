package com.lanen.view.main;

import java.io.IOException;
import java.net.URL;
import java.rmi.ConnectException;
import java.util.ResourceBundle;

import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.view.quarantine.PlanPaneController;
import com.lanen.view.quarantine.ReceivePaneController;
import com.lanen.view.quarantine.RequestPaneController;
import com.lanen.view.quarantine.SystemSetController;
import com.lanen.view.tblsession.DayToDayController;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainPageController implements Initializable {

	
	@FXML
	private Label userNameLable;
	@FXML
	Hyperlink hyperlinkA,hyperlinkB,hyperlinkC,hyperlinkD,hyperlinkE,hyperlinkF;
	
	@FXML
	AnchorPane titlepane, menupane, central_panel;
	private Hyperlink currentHyperlink  ;
	/**需求申请面板*/
	AnchorPane requestPanel;//需求申请面板
	/**接收登记面板*/
	AnchorPane receivePanel;//接收登记面板
	/**动物分配面板*/
	AnchorPane planPanel;//动物分配面板
	/**日常管理面板*/
	AnchorPane dayToDayPanel;
	/**系统设置*/
	AnchorPane systemSetPanel;//系统设置
	
	
	private static MainPageController instance;

	public static MainPageController getInstance() {
	   return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
	}

	/**进入需求申请面板*/
	@FXML
	private void onLinkButtonActionA(ActionEvent event ){
		if(null ==requestPanel){
			try {
				requestPanel = FXMLLoader.load(getClass().getResource("../../../../com/lanen/view/quarantine/RequestPane.fxml"));
			} catch(ConnectException ce){
				Alert2.create("网络连接失败");
				return;
			}catch (IOException e) {
				e.printStackTrace();
				Alert2.create("加载文件失败");
				return ;
			}
		}
		setPanel(requestPanel, "需求申请");
		if(null!=currentHyperlink && currentHyperlink != hyperlinkA){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkA;
		RequestPaneController.getInstance().loadData();
	}
	/**进入接收登记面板*/
	@FXML
	private void onLinkButtonActionB(ActionEvent event ){
		if(null ==receivePanel){
			try {
				receivePanel = FXMLLoader.load(getClass().getResource("../../../../com/lanen/view/quarantine/ReceivePane.fxml"));
			} catch(ConnectException ce){
				Alert2.create("网络连接失败");
				return;
			}catch (IOException e) {
				e.printStackTrace();
				Alert2.create("加载文件失败");
				return ;
			}
		}
		setPanel(receivePanel, "接收登记");
		if(null!=currentHyperlink && currentHyperlink != hyperlinkB){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkB;
		ReceivePaneController.getInstance().loadData();
	}
	/**进入动物分配面板*/
	@FXML
	private void onLinkButtonActionC(ActionEvent event ){
		if(null ==planPanel){
			try {
				planPanel = FXMLLoader.load(getClass().getResource("../../../../com/lanen/view/quarantine/PlanPane.fxml"));
			} catch(ConnectException ce){
				Alert2.create("网络连接失败");
				return;
			}catch (IOException e) {
				e.printStackTrace();
				Alert2.create("加载文件失败");
				return ;
			}
		}
		setPanel(planPanel, "动物分配");
		if(null!=currentHyperlink && currentHyperlink != hyperlinkC){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkC;
		PlanPaneController.getInstance().loadData();
	}
	/**进入日常管理面板*/
	@FXML
	private void onLinkButtonActionD(ActionEvent event ){
		if(null ==dayToDayPanel){
			try {
				dayToDayPanel = FXMLLoader.load(getClass().getResource("../../../../com/lanen/view/tblsession/DayToDayPane.fxml"));
			} catch(ConnectException ce){
				Alert2.create("网络连接失败");
				return;
			}catch (IOException e) {
				e.printStackTrace();
				Alert2.create("加载文件失败");
				return ;
			}
		}
		setPanel(dayToDayPanel, "日常管理");
		if(null!=currentHyperlink && currentHyperlink != hyperlinkD){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkD;
		DayToDayController.getInstance().loadData();
	}
	@FXML
	private void onLinkButtonActionE(ActionEvent event ){
		if(null!=currentHyperlink && currentHyperlink != hyperlinkE){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkE;
	}
	/**进入系统设置面板*/
	@FXML
	private void onLinkButtonActionF(ActionEvent event ){
		if(null ==systemSetPanel){
			try {
				systemSetPanel = FXMLLoader.load(getClass().getResource("../../../../com/lanen/view/quarantine/SystemSetPane.fxml"));
			} catch(ConnectException ce){
				Alert2.create("网络连接失败");
				return;
			}catch (IOException e) {
				e.printStackTrace();
				Alert2.create("加载文件失败");
				return ;
			}
		}
		setPanel(systemSetPanel, "系统设置");
		if(null!=currentHyperlink && currentHyperlink != hyperlinkF){
			currentHyperlink.setVisited(false);
		}
		currentHyperlink =hyperlinkF;
		SystemSetController.getInstance().loadData();
	}

	/**
	 * 设置中心面板
	 * @param panel
	 * @param name
	 */
	 public void setPanel(final AnchorPane panel, String name) {
	        if (panel == null) {
	            return;
	        }
	        if (central_panel.getChildren().size() > 0 && central_panel.getChildren().get(0) == panel) {
	            return;
	        }
	        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                try {
	                    central_panel.getChildren().clear();
	                    central_panel.getChildren().add(panel);
	                    
	                    titlepane.toFront();
	                    menupane.toFront();
	                } catch (Exception e) {
	                    showErrorMessage(e.getLocalizedMessage());
	                }
	            }
	        };
	        if (central_panel.getChildren().size() > 0) {
	            if (central_panel.getChildren().get(0) == panel) {
	                return;
	            }
	            new Timeline(
	                    new KeyFrame(Duration.seconds(0.15), new KeyValue(central_panel.translateXProperty(), -600), new KeyValue(central_panel.opacityProperty(), 0)),
	                    new KeyFrame(Duration.seconds(0.16), eh),
	                    new KeyFrame(Duration.seconds(0.3), new KeyValue(central_panel.translateXProperty(), 0), new KeyValue(central_panel.opacityProperty(), 1)) //,new KeyFrame(Duration.seconds(0.31), eh2)
	                    ).play();
	        } else {
	            new Timeline(
	                    new KeyFrame(Duration.seconds(0.45), new KeyValue(central_panel.opacityProperty(), 0)),
	                    new KeyFrame(Duration.seconds(0.46), eh),
	                    new KeyFrame(Duration.seconds(0.6), new KeyValue(central_panel.opacityProperty(), 1)) //,new KeyFrame(Duration.seconds(0.31), eh2)
	                    ).play();
	        }
	    }
	/**
	 * 初始化数据
	 */
	public void initPane(){
		//当前用户
		User  currentUser = SaveUserInfo.getUser();
		userNameLable.setText(currentUser != null ? currentUser.getRealName():"");
		//选择“日常管理”
		onLinkButtonActionD(null);
	}
	
	public void showAnimation() {
//	        new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(menupane.translateXProperty(), -200), new KeyValue(titlepane.opacityProperty(), 0)),
//	                new KeyFrame(Duration.seconds(0.3), new KeyValue(menupane.translateXProperty(), -200), new KeyValue(titlepane.opacityProperty(), 1)),
//	                new KeyFrame(Duration.seconds(0.6), new KeyValue(menupane.translateXProperty(), 0), new KeyValue(titlepane.opacityProperty(), 1))).play();
	}
	public void showMessage(String msg) {
	    Alert.create(msg);
	}

	public void showErrorMessage(String msg) {
	    Alert2.create(msg);
	}
}
