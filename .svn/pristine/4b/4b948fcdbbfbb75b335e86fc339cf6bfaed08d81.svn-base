package com.lanen.zero.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.DepartmentFrame;
import com.lanen.view.PrivilegeFrame;
import com.lanen.view.RegulationFrame;
import com.lanen.view.RegulationSetFrame;
import com.lanen.view.RoleFrame;
import com.lanen.view.SystemFrame;
import com.lanen.view.UserFrame;
import com.lanen.view.UserRoleLogFrame;

public class MainFrameController implements Initializable {
	
	@FXML
	private MenuItem departmentMenu;
	@FXML
	private MenuItem regulationSetMenu;
	@FXML
	private MenuItem userMenu;
	@FXML
	private MenuItem userRoleLogMenu;
	@FXML
	private MenuItem alterPasswordMenu;//修改密码
	@FXML
	private MenuItem exitMenu;
	@FXML
	private MenuItem regulationMenu;
	@FXML
	private MenuItem systemMenu;
	@FXML
	private MenuItem privilegeMenu;
	@FXML
	private MenuItem roleMenu;
	@FXML
	private MenuItem helpMenu;
	@FXML
	private MenuItem aboutMenu;
	
	@FXML
	private Button userButton;
	@FXML
	private Button departmentButton;
	@FXML
	private Button userRoleLogButton;
	@FXML
	private Button regulationSetButton;

	//系统设置-系统规则登记
	@FXML
	private void onRegulationMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		RegulationFrame regulationFrame = new RegulationFrame();
		try {
			regulationFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统设置-系统登记
	@FXML
	private void onSystemTableMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		SystemFrame systemTableFrame = new SystemFrame();
		try {
			systemTableFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统设置-权限登记
	@FXML
	private void onPrivilegeMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		PrivilegeFrame privilegeFrame = new PrivilegeFrame();
		try {
			privilegeFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统设置-角色设置
	@FXML
	private void onRoleMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		RoleFrame frame = new RoleFrame();
		try {
			frame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统管理-系统规则设置
	@FXML
	private void onRegulationSetMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		RegulationSetFrame regulationSetFrame = new RegulationSetFrame();
		try {
			regulationSetFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//系统管理-部门设置
	@FXML
	private void onDepartmentMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		DepartmentFrame departmentFrame = new DepartmentFrame();
		try {
			departmentFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统管理-用户管理
	@FXML
	private void onUserMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.setResizable(false);
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.WINDOW_MODAL);
		UserFrame frame = new UserFrame();
		try {
			frame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统管理-授权变更日志
	@FXML
	private void onUserRoleLogMenuAction(ActionEvent event){
		Stage stage =new Stage();
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		UserRoleLogFrame frame = new UserRoleLogFrame();
		try {
			frame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//系统管理-修改密码
	@FXML
	private void onAlterPasswordAction(ActionEvent event){
//		Window mainWindow=((javafx.scene.control.Control) event.getSource()).getScene().getWindow();
		Stage stage=new Stage();
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.WINDOW_MODAL);
		AlterPassword alterPassword=new AlterPassword(SaveUserInfo.getUser(),"");
		try {
			alterPassword.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改成功
		if(AlterPassword.getType().equals("OK")){
			SaveUserInfo.setUser(AlterPassword.getUser());
		}
	}

	// 系统管理-退出
	@FXML
	private void onExitMenuAction(ActionEvent event) {
		if (Confirm2.create(MainFrame.mainWindow,"确定要退出系统吗？")) {
			System.exit(0);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//系统管理菜单初始化
		initSystemManage();
		//系统设置菜单初始化
		initSystemSet();
		//帮助菜单初始化
		initHelp();
		
	}
	
	//系统管理菜单初始化
	
	private void initSystemManage(){
		Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/部门设置.png")) );
		Node userIcon1 = new ImageView( new Image(getClass().getResourceAsStream("/image/系统规则设置.png")) );
		Node userIcon2 = new ImageView( new Image(getClass().getResourceAsStream("/image/用户管理.png")) );
		Node userIcon3 = new ImageView( new Image(getClass().getResourceAsStream("/image/授权变更日志.png")) );
		Node userIcon4 = new ImageView( new Image(getClass().getResourceAsStream("/image/退出.png")) );
		departmentMenu.setGraphic(userIcon);
		regulationSetMenu.setGraphic(userIcon1);
		userMenu.setGraphic(userIcon2);
		userRoleLogMenu.setGraphic(userIcon3);
		exitMenu.setGraphic(userIcon4);
		
		
		
		if(SaveUserInfo.getUserName() !=null && SaveUserInfo.getUserName().equals("system")){
			departmentMenu.setDisable(true);
			regulationSetMenu.setDisable(true);
			userMenu.setDisable(true);
			userRoleLogMenu.setDisable(true);
			
			userButton.setDisable(true);
			departmentButton.setDisable(true);
			userRoleLogButton.setDisable(true);
			regulationSetButton.setDisable(true);
		}else if (SaveUserInfo.getUserName() !=null && SaveUserInfo.getUserName().equals("admin")) {
			departmentMenu.setDisable(false);
			regulationSetMenu.setDisable(false);
			userMenu.setDisable(false);
			userRoleLogMenu.setDisable(false);
			
			userButton.setDisable(false);
			departmentButton.setDisable(false);
			userRoleLogButton.setDisable(false);
			regulationSetButton.setDisable(false);
		}
		
		
		Node icon5 = new ImageView( new Image(getClass().getResourceAsStream("/image/修改密码m.png")));
		alterPasswordMenu.setGraphic(icon5);
	}
	//系统设置菜单初始化
	
	private void initSystemSet(){
		Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/系统规则登记.png")) );
		Node userIcon1 = new ImageView( new Image(getClass().getResourceAsStream("/image/系统登记.png")) );
		Node userIcon2 = new ImageView( new Image(getClass().getResourceAsStream("/image/权限登记.png")) );
		Node userIcon3 = new ImageView( new Image(getClass().getResourceAsStream("/image/角色设置.png")) );
		regulationMenu.setGraphic(userIcon);
		systemMenu.setGraphic(userIcon1);
		privilegeMenu.setGraphic(userIcon2);
		roleMenu.setGraphic(userIcon3);
		
		if(SaveUserInfo.getUserName() !=null && SaveUserInfo.getUserName().equals("system")){
			regulationMenu.setDisable(false);
			systemMenu.setDisable(false);
			privilegeMenu.setDisable(false);
			roleMenu.setDisable(true);
		}else if (SaveUserInfo.getUserName() !=null && SaveUserInfo.getUserName().equals("admin")) {
			regulationMenu.setDisable(true);
			systemMenu.setDisable(true);
			privilegeMenu.setDisable(true);
			roleMenu.setDisable(false);
		}
	}
	//帮助菜单初始化
	
	private void initHelp(){
		Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/帮助.png")) );
		Node userIcon1 = new ImageView( new Image(getClass().getResourceAsStream("/image/系统规则设置.png")) );
		helpMenu.setGraphic(userIcon);
		aboutMenu.setGraphic(userIcon1);
	}
}
