package com.lanen.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.Department;
import com.lanen.model.Role;
import com.lanen.model.Systems;
import com.lanen.model.User;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;

public class UserFrameController implements Initializable {
	
	@FXML
	private TreeView<String> departmentTree;
	@SuppressWarnings("rawtypes")
	@FXML
	private TreeView roleTree;
//	@FXML
//	private TextField idText;
	@FXML
	private TextField userNameText;
	@FXML
	private TextField realNameText;
	@FXML
	private TextField userNumberText;
	@FXML
	private TextField phoneText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField createUserText;
	@FXML
	private TextField createTimeText;
	@FXML
	private Label label2;
	@FXML
	private TextArea flagRemarkText;
	@FXML
	private Button newBtn;
	@FXML
	private Button addBtn;
	@FXML
	private Button confirmBtn;
	@FXML
	private Button resetPasswordBtn;
	@FXML
	private Button noUseBtn;
	@FXML
	private ComboBox<String> departmentComboBox;
	@FXML
	private ComboBox<String> systemComboBox;
	@FXML
	private CheckBox editCheckBox;
	@FXML
	private CheckBox showCheckBox;
	private TreeItem<String> rootNode_departmentTree=new TreeItem<String>();
	@SuppressWarnings("rawtypes")
	private TreeItem rootNode_roleTree=new TreeItem();
	private Map<String,Department> departmentMap=new HashMap<String,Department>();
	private ObservableList<String> data_departmentComboBox=FXCollections.observableArrayList();
	private ObservableList<String> data_systemComboBox=FXCollections.observableArrayList();
//	private Map<String,SystemTable> systemMap=new HashMap<String,SystemTable>();
	private Map<String,List<StrBoolean>> strBooleanMap = new HashMap<String,List<StrBoolean>>();//key 为系统名称     ;value 为 角色名称+boolean 列表 
	private int isEditing=0;//1、表示编辑状态
	
	private int isShowStopUser=1;//1表示停用

	private List<TreeItem<String>> leafList=new ArrayList<TreeItem<String>>();//显示停用账户  i为node，i+1为leaf

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		initDepartmentTree();
		initDepartmentComboBox();
		initSystemComboBox();
		
		
		User user = SaveUserInfo.getUser();
		if (null != user && null != user.getRealName()) {
			createUserText.setText(user.getRealName());
		}
		createTimeText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
	}


	/**
	 * 初始化树（部门-人员）
	 */
	private void initDepartmentTree() {

		departmentTree.setRoot(rootNode_departmentTree);
		List<Department> departmentList = BaseService.getDepartmentService().findAll_1();
		if (null != departmentList && departmentList.size() > 0) {// 部门列表不为空
			for (Department department : departmentList) {
				TreeItem<String> node = new TreeItem<String>(department.getName());
				departmentMap.put(department.getName(), department);// 填充map数据
				rootNode_departmentTree.getChildren().add(node);
//				Set<User> userSet = department.getUsers();
				List<User> userList = BaseService.getUserService().findUserListByDepartmentId_1(department.getId());
				if (null != userList && userList.size() > 0) {// 用户列表不为空
//					List<User> userList = new ArrayList<User>(userSet);
//					Collections.sort(userList, new Comparator<User>(){
//
//						@Override
//						public int compare(User o1, User o2) {
//							return o1.getId().compareTo(o2.getId());
//						}});
					for (User user : userList) {
//						node.getChildren().add(leaf);
							if("停用".equals(user.getFlag())){
								Node stopUserIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person2.png")) );
								TreeItem<String> leaf = new TreeItem<String>(user.getUserName(),stopUserIcon);
								leafList.add(node);
								leafList.add(leaf);
							}else{
								Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person.png")) );
								TreeItem<String> leaf = new TreeItem<String>(user.getUserName(),userIcon);
								node.getChildren().add(leaf);
							}
					}
				}
			}
		}

		departmentTree.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<String>>() {// 选中改变监听事件

						@Override
						public void changed(
									ObservableValue<? extends TreeItem<String>> observable,
									TreeItem<String> oldValue, TreeItem<String> newValue) {
								if (null != newValue) {
									if (data_departmentComboBox.contains(newValue.getValue())) {//选中的是部门
										int index = data_departmentComboBox.indexOf(newValue
												.getValue());
										departmentComboBox.getSelectionModel()
												.clearAndSelect(index);
										userNameText.setText("");
//										idText.setText("");
										realNameText.setText("");
										userNumberText.setText("");
										phoneText.setText("");
										emailText.setText("");
										
										if(isEditing==1){
											createUserText.setText("");
											createTimeText.setText("");
										}
										
										label2.setVisible(false);
										flagRemarkText.setVisible(false);
										
										emptyStrBooleanMap();// 清空strBooleanMap里的true！！
										systemComboBox.getSelectionModel().clearSelection();
										if(data_systemComboBox.size()>0)
											systemComboBox.getSelectionModel().select(0);
									} else {
										int index = data_departmentComboBox.indexOf(newValue
												.getParent().getValue());
										departmentComboBox.getSelectionModel()
												.clearAndSelect(index);
										// 选中权限（叶子节点），填充文本框
										User user = BaseService.getUserService().getByUserName_1(
												newValue.getValue());
										if (null != user&&isEditing==1) {
											userNameText.setText(user.getUserName());
//											idText.setText(user.getId());
											realNameText.setText(user.getRealName() == null ? ""
													: user.getRealName());
											userNumberText.setText(user.getUserNumber() == null ? ""
													: user.getUserNumber());
											phoneText.setText(user.getPhone() == null ? "" : user
													.getPhone());
											emailText.setText(user.getEmail() == null ? "" : user
													.getEmail());
											createUserText.setText(user.getUser() == null ? ""
													: user.getUser().getRealName());
											if (null != user.getUser()) {
												if (null != user.getCreateTime()) {
													createTimeText.setText(DateUtil.dateToString(
															user.getCreateTime(),
															"yyyy-MM-dd"));
												} else {
													createTimeText.setText("");
												}
											} else {
												createTimeText.setText("");
											}
//											Set<Role> roleSet=user.getRoles();
											List<Role> roleList = BaseService.getRoleService().findRoleListByUserId_1(user.getId());
											emptyStrBooleanMap();//清空
											List<String> roleNameList=new ArrayList<String>();
											if(null!=roleList && roleList.size()>0){
//												List<Role> roleList=new ArrayList<Role>(roleSet);
												for(Role role:roleList){
													roleNameList.add(role.getRoleName());
												}
											}
											setRoleNameList(roleNameList);//加载数据
											systemComboBox.getSelectionModel().clearSelection();
											if(data_systemComboBox.size()>0)
												systemComboBox.getSelectionModel().select(0);
											
//											addBtn.setText("新增");
											if("停用".equals(user.getFlag())){
												label2.setVisible(true);
												flagRemarkText.setVisible(true);
												flagRemarkText.setText(user.getFlagRemark());
											}else{
												label2.setVisible(false);
												flagRemarkText.setVisible(false);
												flagRemarkText.setText("");
											}
										 }
									}
								}
						}
				});
	}

	/**
	 * 部门下拉框初始化
	 */
	private void initDepartmentComboBox() {
		
		List<String> departmentNameList=new ArrayList<String>(departmentMap.keySet());
		if(null!=departmentNameList&&departmentNameList.size()>0){
			for(String str:departmentNameList){
				data_departmentComboBox.add(str);
			}
		}
		departmentComboBox.setItems(data_departmentComboBox);
	}
	/**
	 * 系统（SystemTable）下拉框初始化
	 */
	private void initSystemComboBox() {
		data_systemComboBox.clear();
		List<Systems> systemList=BaseService.getSystemService().findAll_1();
		if(null!=systemList&&systemList.size()>0){
			for(Systems systemItem:systemList){
				data_systemComboBox.add(systemItem.getSystemName());
//				Set<Role> roleSet=systemItem.getRoles();
				List<Role> roleList = BaseService.getRoleService().findRoleListBySystemId_1(systemItem.getId());
				if(null!=roleList&&roleList.size()>0){
//					List<Role> roleList=new ArrayList<Role>(roleSet);
					List<StrBoolean> strBooleanList=new ArrayList<StrBoolean>();
					for(Role role:roleList){
						StrBoolean strBoolean =new StrBoolean(role.getRoleName(),false);
						strBooleanList.add(strBoolean);
					}
					strBooleanMap.put(systemItem.getSystemName(), strBooleanList);
				}else{
					strBooleanMap.put(systemItem.getSystemName(), null);
				}
			}
		}
		systemComboBox.setItems(data_systemComboBox);
		systemComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				initRoleTree(newValue);
			}
		});
		if(data_systemComboBox.size()>0)
			systemComboBox.getSelectionModel().select(0);
	}
	/**
	 * 初始化树（系统-角色）
	 */
	@SuppressWarnings("unchecked")
	private void initRoleTree(String item) {
		rootNode_roleTree.setValue(item);
		rootNode_roleTree.setExpanded(true);
		rootNode_roleTree.getChildren().clear();
		roleTree.setRoot(rootNode_roleTree);
		List<StrBoolean> list = strBooleanMap.get(item);
		if (null != list && list.size() > 0) {
			for (final StrBoolean strBoolean : list) {
				CheckBox checkBox = new CheckBox(strBoolean.getStr());
				checkBox.setSelected(strBoolean.isFlag());
				checkBox.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						CheckBox currentCheckBox = (CheckBox) event.getSource();
						if (isEditing == 1) {// 编辑状态
							if ("".equals(userNameText.getText().trim())) {
								if (currentCheckBox.isSelected()) {
									currentCheckBox.setSelected(false);
								} else {
									currentCheckBox.setSelected(true);
								}
								Alert2.create("请先选择要编辑的用户");
							} else {
								if (currentCheckBox.isSelected()) {
									strBoolean.setFlag(true);
								} else {
									strBoolean.setFlag(false);
								}
							}
						} else {
							if (currentCheckBox.isSelected()) {
								currentCheckBox.setSelected(false);
							} else {
								currentCheckBox.setSelected(true);
							}
							Alert2.create("当前不是编辑状态。");
						}
					}

				});
				TreeItem<CheckBox> node = new TreeItem<CheckBox>(checkBox);
				rootNode_roleTree.getChildren().add(node);
			}
		}

	}
	
	/**
	 * 清空strBooleanMap里的true！！
	 */
	private void emptyStrBooleanMap(){
		List<String> systemNameList=new ArrayList<String>(strBooleanMap.keySet());
		if(null!=systemNameList&&systemNameList.size()>0){
			for(String str:systemNameList){
				List<StrBoolean> strBooleanList =strBooleanMap.get(str);
				if(null!=strBooleanList&&strBooleanList.size()>0){
					for(StrBoolean strBoolean:strBooleanList){
						strBoolean.setFlag(false);
					}
				}
			}
		}
	}
	
	/**
	 * 从 strBooleanMap 里，获得所有角色名称
	 * @return
	 */
	private List<String> getRoleNameList() {
		List<String> roleNameList=new ArrayList<String>();
		List<String> systemNameList=new ArrayList<String>(strBooleanMap.keySet());
		if(null!=systemNameList&&systemNameList.size()>0){
			for(String str:systemNameList){
				List<StrBoolean> strBooleanList =strBooleanMap.get(str);
				if(null!=strBooleanList&&strBooleanList.size()>0){
					for(StrBoolean strBoolean:strBooleanList){
						if(strBoolean.isFlag()){
							roleNameList.add(strBoolean.getStr());
						}
					}
				}
			}
		}
		return roleNameList;
	}
	/**
	 * 设置 strBooleanMap 里，该有角色名称
	 * @return
	 */
	private void setRoleNameList(List<String> roleNameList) {
		List<String> systemNameList=new ArrayList<String>(strBooleanMap.keySet());
		if(null!=systemNameList&&systemNameList.size()>0){
			for(String str:systemNameList){
				List<StrBoolean> strBooleanList =strBooleanMap.get(str);
				if(null!=strBooleanList&&strBooleanList.size()>0){
					int i=0;
					for(StrBoolean strBoolean:strBooleanList){
						if(roleNameList.contains(strBoolean.getStr())){
							strBooleanList.get(i).setFlag(true);
						}
						i++;
					}
					strBooleanMap.put(str, strBooleanList);
				}
			}
		}
	}
	
	/** 
	 * 新建(部门)按钮事件
	 * @param event
	 */
	@FXML
	private void onNewDepartmentAction(ActionEvent event){
		DepartmentFrame2.newDepartment();
		System.out.println(DepartmentFrame2.getDepartmentName());
		//先清空
		rootNode_departmentTree.getChildren().clear();//先清空
		departmentMap.clear();	
		leafList.clear();
		data_departmentComboBox.clear();
		data_systemComboBox.clear();
		//再重新加载
		initDepartmentTree();
		initDepartmentComboBox();
		initSystemComboBox();
		departmentComboBox.getSelectionModel().select(DepartmentFrame2.getDepartmentName());
		
	}
	
	/**
	 * 新增按钮事件
	 * 
	 * @param event
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event) {
		
//			String id = idText.getText().toString().trim();
			String userName = userNameText.getText().toString().trim();
			String realName=realNameText.getText().trim();
			String userNumber=userNumberText.getText().trim();
			String phone=phoneText.getText().trim();
			String email=emailText.getText().trim();
			String departmentName = departmentComboBox.getSelectionModel().getSelectedItem();
			
			if (null == departmentName || departmentName.equals("")) {
				Alert2.create("请选择部门名称。");
			} else if ("".equals(realName) || "".equals(userName)) {
				Alert2.create("用户名和姓名为必填项。");
			} else {
				User entity=new User();
				if (isEditing == 1) {// 编辑状态下的保存
					entity=BaseService.getUserService().getByUserName_1(userName);
//					entity.setRealName(realName);
					entity.setUserNumber(userNumber);
					entity.setPhone(phone);
					entity.setEmail(email);
					entity.setUser(SaveUserInfo.getUser());
					if(!departmentName.equals(BaseService.getUserService().getPartmentNameByUsreName_1(userName))){//用户的部门改变了
						TreeItem<String> newNode =new TreeItem<String>();
						TreeItem<String> newLeaf =new TreeItem<String>();
						List<TreeItem<String>> nodeList=departmentTree.getRoot().getChildren();
						for(TreeItem<String> node:nodeList){
							if(node.getValue().equals(departmentName)){
								newNode=node;
							}
							for(TreeItem<String> leaf:node.getChildren()){
								if(userName.equals(leaf.getValue())){
									newLeaf=leaf;
								}
							}
						}
						Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person.png")) );
						TreeItem<String> userLeaf=null;
						if(entity.getFlag().equals("停用")){
							userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person2.png")) );
							userLeaf=new TreeItem<String>(userName,userIcon);
							int i=0;
							for(TreeItem<String> leaf:leafList){
								if(leaf.equals(newLeaf)){
									leafList.set(i-1, newNode);
									leafList.set(i, userLeaf);
								}
								i++;
							}
						}else{
							userLeaf=new TreeItem<String>(userName,userIcon);
						}
						
						newNode.getChildren().add(userLeaf);//添加用户节点
						newLeaf.getParent().getChildren().remove(newLeaf);//删除老的用户节点
						for(TreeItem<String> leaf:newNode.getChildren()){//设置选中项
							if(leaf.getValue().equals(userName)){
								departmentTree.getSelectionModel().select(leaf);
							}
						}
						
					}
					BaseService.getUserService().update(entity,departmentName);
					Alert.create("修改成功");
					
				} else {// 新增状态下的保存
					if (BaseService.getUserService().isIdExist_1(userName)) {
						Alert2.create("用户名已存在！");
						userNameText.requestFocus();
					} else if (BaseService.getUserService().isNameExist_1(
							userName)) {
						Alert2.create("用户名已存在！");
						userNameText.requestFocus();
					} else if (BaseService.getUserService().isRealNameExist_1(
							realName)) {
						Alert2.create("姓名已存在！");
						realNameText.requestFocus();
					}else {
						entity.setId(userName);
						entity.setUserName(userName);
						entity.setRealName(realName);
						entity.setUserNumber(userNumber);
						entity.setPhone(phone);
						entity.setEmail(email);
						entity.setFlag("可用");
						User author = SaveUserInfo.getUser();
						if (null != author) {
							entity.setUser(author);
						}
						BaseService.getUserService().saveWithDepartmentAndTimeAndRole(entity, departmentName,null);

						List<TreeItem<String>> nodeList=departmentTree.getRoot().getChildren();
						Node userIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person.png")) );
						for(TreeItem<String> node:nodeList){
							if(node.getValue().equals(departmentName)){
								node.getChildren().add(new TreeItem<String>(userName,userIcon));
								for(TreeItem<String> leaf:node.getChildren()){
									if(userName.equals(leaf.getValue())){
										departmentTree.getSelectionModel().select(leaf);
									}
								}
							}
						}
						userNameText.setText("");
						userNameText.setText("");
						realNameText.setText("");
						userNumberText.setText("");
						phoneText.setText("");
						emailText.setText("");

						User user = SaveUserInfo.getUser();
						if (null != user && null != user.getRealName()) {
							createUserText.setText(user.getRealName());
						}
						createTimeText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						Alert.create("保存成功");
					}
				}
			}
	}
	/**
	 * 应用按钮事件
	 * @param event
	 */
	@FXML
	private void OnConfirmBtnAction(ActionEvent event){
		List<String> roleNameList=getRoleNameList();
		if(isEditing==1){//编辑状态
			if("".equals(userNameText.getText().trim())){
				Alert2.create("请先选择要编辑的用户");
			}else{
				BaseService.getUserService().update(userNameText.getText().trim(),roleNameList,SaveUserInfo.getUser());
				Alert.create("授权成功。");
			}
		}else{
			Alert.create("当前不是编辑状态。");
		}
	}

	/**
	 * 复选框editCheckBox点击事件
	 * 
	 * @param event
	 */
	@FXML
	private void onEditCheckBoxAction(ActionEvent event) {
		if (editCheckBox.isSelected()) {
			userNameText.setEditable(false);
			realNameText.setEditable(false);
			addBtn.setText("确定");
			isEditing = 1;
			TreeItem<String> newValue = departmentTree.getSelectionModel().getSelectedItem();
			if (null != newValue) {
				if (data_departmentComboBox.contains(newValue.getValue())) {// 选中的是部门
					int index = data_departmentComboBox.indexOf(newValue.getValue());
					departmentComboBox.getSelectionModel().clearAndSelect(index);
					userNameText.setText("");
//					idText.setText("");
					realNameText.setText("");
					userNumberText.setText("");
					phoneText.setText("");
					emailText.setText("");

					createUserText.setText("");
					createTimeText.setText("");

					label2.setVisible(false);
					flagRemarkText.setVisible(false);

					emptyStrBooleanMap();// 清空strBooleanMap里的true！！
					systemComboBox.getSelectionModel().clearSelection();
					if (data_systemComboBox.size() > 0)
						systemComboBox.getSelectionModel().select(0);
				} else {
					int index = data_departmentComboBox.indexOf(newValue.getParent().getValue());
					departmentComboBox.getSelectionModel().clearAndSelect(index);
					// 选中权限（叶子节点），填充文本框
					User user = BaseService.getUserService().getByUserName_1(newValue.getValue());
					if (null != user) {
						userNameText.setText(user.getUserName());
//						idText.setText(user.getId());
						realNameText.setText(user.getRealName() == null ? "" : user.getRealName());
						userNumberText.setText(user.getUserNumber() == null ? "" : user
								.getUserNumber());
						phoneText.setText(user.getPhone() == null ? "" : user.getPhone());
						emailText.setText(user.getEmail() == null ? "" : user.getEmail());
						createUserText.setText(user.getUser() == null ? "" : user.getUser()
								.getRealName());
						if (null != user.getUser()) {
							if (null != user.getCreateTime()) {
								createTimeText.setText(DateUtil.dateToString(user
										.getCreateTime(), "yyyy-MM-dd"));
							} else {
								createTimeText.setText("");
							}
						} else {
							createTimeText.setText("");
						}
//						Set<Role> roleSet = user.getRoles();
						List<Role> roleList = BaseService.getRoleService().findRoleListByUserId_1(user.getId());
						emptyStrBooleanMap();// 清空
						List<String> roleNameList = new ArrayList<String>();
						if (null != roleList && roleList.size() > 0) {
//							List<Role> roleList = new ArrayList<Role>(roleSet);
							for (Role role : roleList) {
								roleNameList.add(role.getRoleName());
							}
						}
						setRoleNameList(roleNameList);// 加载数据
						systemComboBox.getSelectionModel().clearSelection();
						if (data_systemComboBox.size() > 0)
							systemComboBox.getSelectionModel().select(0);

						if ("停用".equals(user.getFlag())) {
							label2.setVisible(true);
							flagRemarkText.setVisible(true);
							flagRemarkText.setText(user.getFlagRemark());
						} else {
							label2.setVisible(false);
							flagRemarkText.setVisible(false);
							flagRemarkText.setText("");
						}
					}
				}
			}
		} else {
			userNameText.setEditable(true);
			realNameText.setEditable(true);
			addBtn.setText("增加");
			isEditing = 0;
			userNameText.setText("");
//			idText.setText("");
			realNameText.setText("");
			userNumberText.setText("");
			phoneText.setText("");
			emailText.setText("");
			User user = SaveUserInfo.getUser();
			if (null != user && null != user.getRealName()) {
				createUserText.setText(user.getRealName());
			}
			createTimeText.setText(DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			label2.setVisible(false);
			flagRemarkText.setVisible(false);
		}
	}
	
	/**
	 * 显示停用账户 复选框事件
	 */
	@FXML
	private void onShowCheckBoxAciton(ActionEvent event){
		departmentTree.getSelectionModel().clearSelection();
		if(isEditing==1){
			userNameText.setText("");
//			idText.setText("");
			realNameText.setText("");
			userNumberText.setText("");
			phoneText.setText("");
			emailText.setText("");
			
			createUserText.setText("");
			createTimeText.setText("");
		}
		
		if(showCheckBox.isSelected()){
			if(null!=leafList&&leafList.size()>0){
				for(int i=0;i<leafList.size();i=i+2){
					leafList.get(i).getChildren().add(leafList.get(i+1));
				}
			}
			isShowStopUser=1;
			departmentTree.getSelectionModel().clearSelection();
		}else{
			if(null!=leafList&&leafList.size()>0){
				for(int i=0;i<leafList.size();i=i+2){
					leafList.get(i).getChildren().remove(leafList.get(i+1));
				}
			}
			isShowStopUser=0;
		}
	}
	
	

	/**
	 * 重置密码事件
	 * @param event
	 */
	@FXML
	private void onResetpasswordBtnAction(ActionEvent event){
		TreeItem<String> selectedNode=departmentTree.getSelectionModel().getSelectedItem();
		if(null==selectedNode){
			Alert.create("请选择用户。");
		}else{
			if(departmentTree.getRoot().getChildren().contains(selectedNode)){
				Alert.create("请选择用户。");
			}else{
				String userName=selectedNode.getValue();
				
				Stage stage=new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setIconified(false);
				stage.setResizable(false);
				PasswordResetFrame passwordResetFrame=new PasswordResetFrame("确定要重置用户\""+userName+"\"密码吗？");
				try {
					passwordResetFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if("OK".equals(PasswordResetFrame.getType())){
					BaseService.getUserService().resetPassword(userName);
					Alert.create("密码重置成功！");
				}
			}
		}
	}
	
	/**
	 * 停用账户按钮事件
	 * @param event
	 */
	@FXML
	private void onNoUseBtnAction(ActionEvent event){
		TreeItem<String> selectedNode=departmentTree.getSelectionModel().getSelectedItem();
		if(null==selectedNode){
			Alert.create("请选择用户。");
		}else{
			if(departmentTree.getRoot().getChildren().contains(selectedNode)){
				Alert.create("请选择用户。");
			}else{
				String userName=selectedNode.getValue();
				User user=BaseService.getUserService().getByUserName(userName);
				if(!"停用".equals(user.getFlag())){
					Stage stage=new Stage();
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setIconified(false);
					stage.setResizable(false);
					StopUserFrame stopUserFrame=new StopUserFrame();
					try {
						stopUserFrame.start(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if("OK".equals(StopUserFrame.getType())){
						BaseService.getUserService().stopUserName(userName, StopUserFrame.getRemark());
						Node stopUserIcon = new ImageView( new Image(getClass().getResourceAsStream("/image/person2.png")) );
						leafList.add(selectedNode.getParent());
						if(isShowStopUser==0){
							leafList.add(new TreeItem<String>(userName,stopUserIcon));
							departmentTree.getSelectionModel().getSelectedItem().getParent().getChildren().remove(selectedNode);
						}else{
							TreeItem<String> parentNode=departmentTree.getSelectionModel().getSelectedItem().getParent();
							departmentTree.getSelectionModel().getSelectedItem().getParent().getChildren().remove(selectedNode);
//						parentNode.getChildren().add(new TreeItem<String>(userName,stopUserIcon));
							leafList.add(selectedNode);
							selectedNode.setGraphic(stopUserIcon);
							parentNode.getChildren().add(selectedNode);
						}
					}
				}else{
					Alert.create("该用户已经停用。");
				}
			}
		}
	}
	/**
	 * 退出按钮事件
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event) {
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	
	//系统-角色专用类
	public class StrBoolean{
		private String str;
		private boolean flag;
		public StrBoolean(){}
		public StrBoolean(String str,boolean flag){
			this.str=str;
			this.flag=flag;
		}
		public String getStr() {
			return str;
		}
		public void setStr(String str) {
			this.str = str;
		}
		public boolean isFlag() {
			return flag;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		
	}
	
}
