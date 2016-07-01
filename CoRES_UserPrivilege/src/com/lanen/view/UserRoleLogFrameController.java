package com.lanen.view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.lanen.base.BaseService;
import com.lanen.model.UserRoleLog;
import com.lanen.util.DateUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserRoleLogFrameController implements Initializable {
	
	@FXML
	private TableView<UserRoleLogInfo> table;
	@FXML
	private TableColumn<UserRoleLogInfo,String> idCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> userNameCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> systemIdCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> roleIdCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> typeCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> createTimeCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> createUserCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> privilegeListCol;
	@FXML
	private TableColumn<UserRoleLogInfo,String> md5Col;
	private ObservableList<UserRoleLogInfo> data= FXCollections.observableArrayList();
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<UserRoleLog> list=BaseService.getUserRoleLogService().findAll();
		if(null!=list){
			for(UserRoleLog userRoleLog:list){
				String str="";
				if(null!=userRoleLog.getUser()){
					str=userRoleLog.getUser().getUserName();
				}
				data.add(new UserRoleLogInfo(userRoleLog.getId()+"",userRoleLog.getUserName(),
						userRoleLog.getSystemId(),
						userRoleLog.getRoleId(),userRoleLog.getType(),
						userRoleLog.getCreateTime(),str,
						userRoleLog.getPrivilegeList(),userRoleLog.getMD5()
						));
			}
		}
		table.setItems(data);
		
		idCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"id"));
		userNameCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"userName"));
		systemIdCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"systemId"));
		roleIdCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"roleId"));
		typeCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"type"));
		createTimeCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"createTime"));
		createUserCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"createUser"));
		privilegeListCol.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"privilegeList"));
		md5Col.setCellValueFactory(new PropertyValueFactory<UserRoleLogInfo, String>(
				"md5"));

	}

	/**
	 * 返回按钮事件
	 * @author Administrator
	 *
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	
	
	public class UserRoleLogInfo{
		private SimpleStringProperty id=new SimpleStringProperty();
		private SimpleStringProperty userName=new SimpleStringProperty();
		private SimpleStringProperty systemId=new SimpleStringProperty();
		private SimpleStringProperty roleId=new SimpleStringProperty();
		private SimpleStringProperty type=new SimpleStringProperty();
		private SimpleStringProperty createTime=new SimpleStringProperty();
		private SimpleStringProperty createUser=new SimpleStringProperty();
		private SimpleStringProperty privilegeList=new SimpleStringProperty();
		private SimpleStringProperty md5=new SimpleStringProperty();
		
		public UserRoleLogInfo(){}
		public UserRoleLogInfo(String id,String userName,String systemId,String roleId,String type,Date createTime,String createUser,String privilegeList,String md5){
			this.id =new SimpleStringProperty(id);
			this.userName=new SimpleStringProperty(userName);
			this.systemId=new SimpleStringProperty(systemId);
			this.roleId=new SimpleStringProperty(roleId);
			this.type=new SimpleStringProperty(type);
			this.createTime=new SimpleStringProperty(DateUtil.dateToString(createTime, "yyyy-MM-dd hh:mm:ss"));
			this.createUser=new SimpleStringProperty(createUser);
			this.privilegeList=new SimpleStringProperty(privilegeList);
			this.md5=new SimpleStringProperty(md5);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getUserName() {
			return userName.get();
		}
		public void setUserName(String userName) {
			this.userName = new SimpleStringProperty(userName);
		}
		public String getSystemId() {
			return systemId.get();
		}
		public void setSystemId(String systemId) {
			this.systemId = new SimpleStringProperty(systemId);
		}
		public String getRoleId() {
			return roleId.get();
		}
		public void setRoleId(String roleId) {
			this.roleId = new SimpleStringProperty(roleId);
		}
		public String getType() {
			return type.get();
		}
		public void setType(String type) {
			this.type = new SimpleStringProperty(type);
		}
		public String getCreateTime() {
			return createTime.get();
		}
		public void setCreateTime(Date createTime) {
			this.createTime = new SimpleStringProperty(DateUtil.dateToString(createTime, "yyyy-MM-dd hh:mm:ss"));
		}
		public String getCreateUser() {
			return createUser.get();
		}
		public void setCreateUser(String createUser) {
			this.createUser = new SimpleStringProperty(createUser);
		}
		public String getPrivilegeList() {
			return privilegeList.get();
		}
		public void setPrivilegeList(String privilegeList) {
			this.privilegeList = new SimpleStringProperty(privilegeList);
		}
		public String getMd5() {
			return md5.get();
		}
		public void setMd5(String md5) {
			this.md5 = new SimpleStringProperty(md5);
		}
	}
}
