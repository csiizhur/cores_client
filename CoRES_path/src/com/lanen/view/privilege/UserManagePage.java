package com.lanen.view.privilege;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.Role;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

/**
 * 用户管理
 * @author Administrator
 *
 */
public class UserManagePage extends Application implements Initializable{
	
	//用户列表
	@FXML
	private ListView<String> userNameListView;
	private ObservableList<String> data_userNameListView = FXCollections.observableArrayList();
	private Map<String,User> userMap = null;
	
	//用户权限
	@FXML
	private ListView<CheckBox> roleListView;
	private ObservableList<CheckBox> data_roleListView = FXCollections.observableArrayList();
	//病理系统里，所有角色名称（登录除外）
	private List<String> showRoleNameList ;
	
	@FXML
	private CheckBox editCheckBox;
	
	@FXML
	private Button saveButton;
	
	

	private static UserManagePage instance;
	public static UserManagePage getInstance(){
		if(null == instance){
			instance = new UserManagePage();
		}
		return instance;
	}
	
	public UserManagePage() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		
		//初始化用户列表ListView
		initUserNameListView();
		//初始化用户权限列表ListView
		initRoleListView();
//		//加载用户列表数据
//		loadData_userNameListView();
//		//加载角色列表数据
//		loadData_roleListView();
	}
	

	/**
	 * 加载角色列表数据
	 */
	private void loadData_roleListView() {
		List<Role> roleList = BaseService.getInstance().getRoleService().findRoleListBySystemId_1("path");
		data_roleListView.clear();
		showRoleNameList = new ArrayList<String>();
		String roleName = "";
		CheckBox checkBox ;
		if(null != roleList){
			for(Role role :roleList){
				roleName = role.getRoleName();
				if(null != roleName && !"毒性病理_登录".equals(roleName)){
					showRoleNameList.add(roleName);
					checkBox = new CheckBox(roleName) ;
					checkBox.setFocusTraversable(false);
					checkBox.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							CheckBox currentCheckBox = (CheckBox) event.getSource();
							if (editCheckBox.isSelected()) {// 编辑状态
								String selectItem = userNameListView.getSelectionModel().getSelectedItem();
								if(null == selectItem){
									Messager.showWarnMessage("请先选择要编辑的用户");
								}else{
									//自己不能把自己的病理负责人权限去掉
									if("病理负责人".equals(currentCheckBox.getText()) ){
										//当前用户
										String userName = SaveUserInfo.getUserName();
										if(null != userName && selectItem.contains(userName)){
											currentCheckBox.setSelected(!currentCheckBox.isSelected());
										}
									}
								}
							} else {
								currentCheckBox.setSelected(!currentCheckBox.isSelected());
							}
						}

					});
					data_roleListView.add(checkBox);
				}
			}
		}
	}

	/**
	 * 加载用户列表数据
	 */
	private void loadData_userNameListView() {
		List<User> list = BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_登录");
		data_userNameListView.clear();
		userMap = new HashMap<String,User>();
		String userNameRealName = "";//src0129(李科)
		if(null != list){
			for(User user:list){
				userNameRealName = " "+user.getUserName()+" ( "+user.getRealName()+" ) ";
				userMap.put(userNameRealName, user);
				data_userNameListView.add(userNameRealName);
			}
		}
	}

	/**
	 * 初始化用户列表ListView
	 */
	private void initUserNameListView() {
		userNameListView.setItems(data_userNameListView);
		userNameListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if(null != newValue){
					//更新选中的角色列表，通过用户
					updateSelectedRoleList(newValue);
				}
				
			}

			});
	}
	/**
	 * 更新选中的角色列表，通过用户
	 * @param user
	 */
	private void updateSelectedRoleList(String  selectedItem) {
		if(null != selectedItem){
			User user = userMap.get(selectedItem);
			if(null != user){
				for(CheckBox checkbox :data_roleListView){
					checkbox.setSelected(false);
				}
				List<Role> roleList = BaseService.getInstance().getRoleService().findRoleListByUserId_1(user.getId());
				if(null != roleList){
					String roleName = "";
					for(Role role:roleList){
						roleName = role.getRoleName();
						if(showRoleNameList.contains(roleName)){
							for(CheckBox checkbox :data_roleListView){
								if(checkbox.getText().equals(roleName))
									checkbox.setSelected(true);
							}
						}
					}
				}
			}
		}
		
	}
	/**
	 * 初始化用户权限列表ListView
	 */
	private void initRoleListView() {
		roleListView.setItems(data_roleListView);
		roleListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){

			@Override
			public void changed(ObservableValue<? extends CheckBox> observable, CheckBox oldValue,
					CheckBox newValue) {
				if(null != newValue){
//					 newValue.setSelected(newValue.isSelected());
				}
				roleListView.getSelectionModel().clearSelection();
			}
		});
	}

	/**
	 * 保存 按钮
	 * @param event
	 */
	@FXML
	private void onSaveButton(ActionEvent event){
		String selectedItem = userNameListView.getSelectionModel().getSelectedItem();
		if(editCheckBox.isSelected() && null != selectedItem){
			User user = userMap.get(selectedItem);
			if(null != user){
				List<Role> oldRoleList = BaseService.getInstance().getRoleService().findRoleListByUserId_1(user.getId());
				List<String> newRoleNameList = new ArrayList<String>();
				String roleName = "";
				if(null != oldRoleList){
					for(Role role:oldRoleList){
						roleName = role.getRoleName();
						if(!showRoleNameList.contains(roleName) && !"病理总负责人角色".equals(roleName) 
								&& !"病理专题负责人角色".equals(roleName)){
							newRoleNameList.add(roleName);
						}
					}
				}
//				病理专题负责人角色
//				病理总负责人角色     (含病理权限)

				for(CheckBox checkbox :data_roleListView){
					if(checkbox.isSelected()){
						newRoleNameList.add(checkbox.getText());
						if(checkbox.getText().trim().equals("病理负责人")){
							newRoleNameList.add("病理总负责人角色");
						}else if(checkbox.getText().trim().equals("病理专题负责人")){
							newRoleNameList.add("病理专题负责人角色");
						}
					}
				}
				//更新用户角色，
				BaseService.getInstance().getUserService().update(user.getId(), newRoleNameList, SaveUserInfo.getUser());
				Messager.showMessage("保存成功！");
			}
		}
	}
	/**
	 * 多选框
	 * @param event
	 */
	@FXML
	private void onEditCheckBox(ActionEvent event){
		CheckBox checkBox = (CheckBox) event.getSource();
		if(checkBox.isSelected()){
			saveButton.setDisable(false);
		}else{
			saveButton.setDisable(true);
			String selectedItem = userNameListView.getSelectionModel().getSelectedItem();
			//更新选中的角色列表，
			updateSelectedRoleList(selectedItem);
			
		}
	}
	
	/**
	 * 加载数据
	 */
	public void loadData(){
		editCheckBox.setSelected(false);
		//加载用户列表数据
		loadData_userNameListView();
		//加载角色列表数据
		loadData_roleListView();
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("UserManage.fxml"));
		Scene scene = new Scene(root, 464, 352);
		stage.setScene(scene);
		stage.setTitle("用户管理");
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

}
