package com.lanen.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.lanen.base.BaseService;
import com.lanen.model.Module;
import com.lanen.model.Privilege;
import com.lanen.model.Role;
import com.lanen.model.Systems;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.zero.main.MainFrame;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoleFrameController implements Initializable {
	
	@FXML
	private TableView<RoleInfo> table;
	@FXML
	private TableColumn<RoleInfo,String> idCol;
	@FXML
	private TableColumn<RoleInfo,String> nameCol;
	@FXML
	private TableColumn<RoleInfo,String> remarkCol;
	@FXML
	private TreeView<CheckBox> tree;
	@FXML
	private TextField idText;
	@FXML
	private TextField nameText;
	@FXML
	private TextArea remarkText;
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private CheckBox checkBox;
	private int isEditing=0;//1表示处于编辑状态
	private Map<String,Systems> systemMap=new HashMap<String,Systems>();//系统列表（Map）
	private ObservableList<String> data_comboBox=FXCollections.observableArrayList();//comboBox  值
	private ObservableList<RoleInfo> data_table=FXCollections.observableArrayList();//table 值
	private TreeItem<CheckBox> rootNode =new TreeItem<CheckBox>(); //tree 的root节点

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		comboBox.setItems(data_comboBox);
		initTable();
		initComboBox();

	}

	/**
	 *初始化TableView
	 * 
	 */
	private void initTable() {
		idCol.setCellValueFactory(new PropertyValueFactory<RoleInfo,String>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<RoleInfo,String>("name"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<RoleInfo,String>("remark"));
		table.setItems(data_table);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoleInfo>(){

			@Override
			public void changed(ObservableValue<? extends RoleInfo> arg0, RoleInfo arg1,
					RoleInfo newValue) {
				if(newValue!=null){
					idText.setText(newValue.getId());
					nameText.setText(newValue.getName());
					remarkText.setText(newValue.getRemark());
					//权限列表
//					Role role=BaseService.getRoleService().getById(newValue.getId());
//					Set<Privilege> privilegeSet=role.getPrivileges();
//					List<Privilege> privilegeList=new ArrayList<Privilege>(privilegeSet);
//					List<String> privilegeNameList=new ArrayList<String>();
//					if(null!=privilegeList&&privilegeList.size()>0){
//						for(Privilege privilege:privilegeList){
//							privilegeNameList.add(privilege.getPrivilegeName());
//						}
//					}
					List<String> privilegeNameList=BaseService.getPrivilegeService().getPrivilegeNameByRoleId(newValue.getId());
					
					
					
					cancelSelected(tree);
					TreeItem<CheckBox> rootNode=tree.getRoot();// 以下几行处理树节点被选中
					List<TreeItem<CheckBox>> nodeList=rootNode.getChildren();
					if(null!=nodeList&&nodeList.size()>0){
						for(TreeItem<CheckBox> node:nodeList){
							node.getValue().setSelected(false);
							List<TreeItem<CheckBox>> leafList=node.getChildren();
							if(null!=leafList&&leafList.size()>0){
								for(TreeItem<CheckBox> leaf:leafList){
									if(privilegeNameList.contains(leaf.getValue().getText())){
										leaf.getValue().setSelected(true);
										leaf.getParent().getValue().setSelected(true);
									}
								}
							}
						}
					}
					
				}
			}});
	}

	/**
	 *初始化ComboBox(系统下拉框)
	 * 
	 */
	private void initComboBox() {
		updateSystemMap();
		if(null!=systemMap&&systemMap.size()>0){
			List<String> list=new ArrayList<String>(systemMap.keySet());
			for(String str:list){
				data_comboBox.add(str);
			}
			comboBox.getSelectionModel().select(0);
			String selectedItem=comboBox.getSelectionModel().getSelectedItem();//下拉框被选中项
			initTableData(selectedItem);//加载表 角色表的值
			initTreeView(selectedItem);//初始化tree
		}
		comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				initTableData(newValue);//加载表 角色表的值
				initTreeView(newValue);//初始化tree
				initOthers();
			}

			});
	}
	/**
	 * 初始化其他控件
	 */
	private void initOthers() {
		idText.setText("");
		nameText.setText("");
		remarkText.setText("");
		idText.setEditable(false);
		if(isEditing==1){
			checkBox.setSelected(true);
			addBtn.setText("保存");
			nameText.setEditable(true);
			remarkText.setEditable(true);
		}else{
			checkBox.setSelected(false);
			addBtn.setText("新增");
			nameText.setEditable(false);
			remarkText.setEditable(false);
		}
		
	}

	/**
	 * 初始化 树tree
	 * 
	 * @param selectedItem
	 */
	private void initTreeView(String selectedItem) {
		rootNode.getChildren().clear();
		rootNode.setValue(new CheckBox(selectedItem));
		rootNode.setExpanded(true);
		Systems system = systemMap.get(selectedItem);
		List<Module> moduleList = new ArrayList<Module>(system.getModules());
		if (null != moduleList && moduleList.size() > 0) {
			for (Module obj : moduleList) {
				final TreeItem<CheckBox> depNode = new TreeItem<CheckBox>(new CheckBox(
						obj.getModuleName()));//节点
				rootNode.getChildren().add(depNode);
				depNode.getValue().setOnAction(new EventHandler<ActionEvent>(){//节点事件

					@Override
					public void handle(ActionEvent event) {
						CheckBox checkBox=(CheckBox) event.getSource();
						List<TreeItem<CheckBox>> list=depNode.getChildren();
						if(null!=list&&list.size()>0){
							boolean state=false;
							if(checkBox.isSelected()){
								state=true;
							}else{
								state=false;
							}
							for(TreeItem<CheckBox> item:list){
								item.getValue().setSelected(state);
							}
						}
					}});
//				List<Privilege> privilegeList = new ArrayList<Privilege>(obj.getPrivileges());
				List<Privilege> privilegeList = BaseService.getPrivilegeService().getPrivilegeListByModule(obj);
				if (null != privilegeList && privilegeList.size() > 0) {
					for (Privilege privilege : privilegeList) {
						CheckBox leafCheckBox=new CheckBox(privilege.getPrivilegeName());
						final TreeItem<CheckBox> empLeaf = new TreeItem<CheckBox>(leafCheckBox);//节点下的叶子
						empLeaf.getValue().setOnAction(new EventHandler<ActionEvent>(){//叶子事件
							@Override
							public void handle(ActionEvent event) {
								CheckBox checkBox=(CheckBox) event.getSource();
								if(!checkBox.isSelected()){
									List<TreeItem<CheckBox>> list=empLeaf.getParent().getChildren();
									boolean found = false;
									for(TreeItem<CheckBox> item:list){
										if(item.getValue().isSelected()){
											found=true;
										}
									}
									if(!found){
										empLeaf.getParent().getValue().setSelected(false);
									}
								}else{
									empLeaf.getParent().getValue().setSelected(true);
								}
							}

							
						});
						depNode.getChildren().add(empLeaf);
					}
				}
			}
		}
		tree.setRoot(rootNode);
	}

	/**
	 * 加载表角色表的值
	 * @param selectedItem
	 */
	private void initTableData(String selectedItem) {
		data_table.clear();
		Systems systemTable=systemMap.get(selectedItem);
//		List<Role> roleList=new ArrayList<Role>(systemTable.getRoles());
		List<Role> roleList = BaseService.getSystemService().getRoleListBySystem(systemTable);
		if(null!=roleList&&roleList.size()>0){
			for(Role role:roleList){
				data_table.add(new RoleInfo(role.getId(),role.getRoleName(),role.getRemark()));
			}
		}
	}

	/**
	 * 重新加载systemMap
	 */
	private void updateSystemMap() {
		List<Systems> systemList = new ArrayList<Systems>();
		systemList = BaseService.getSystemService()
				.findAll();
		if (null != systemList && systemList.size() > 0) {// 系统列表不为空
			for (Systems entity : systemList) {
				systemMap.put(entity.getSystemName(), entity);
			}
		}
	}
	/**
	 * 新增按钮事件
	 * 
	 * @param event
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event) {
		String flag = addBtn.getText();
		if ("新增".equals(flag)) {
			checkBox.setSelected(false);
			idText.setText("");
			nameText.setText("");
			remarkText.setText("");
			idText.setEditable(true);
			nameText.setEditable(true);
			remarkText.setEditable(true);
			addBtn.setText("保存");
			isEditing = 0;
			idText.requestFocus();
			table.getSelectionModel().clearSelection();
			cancelSelected(tree);// 处理树所有节点都没被选中
			
		} else if ("保存".equals(flag)) {
			String id = idText.getText().toString().trim();
			String name = nameText.getText().toString().trim();
			String remark = remarkText.getText().toString().trim();
			String systemName = comboBox.getSelectionModel().getSelectedItem();
			List<String> privilegeNameList=new ArrayList<String>();
			TreeItem<CheckBox> rootNode=tree.getRoot();
			List<TreeItem<CheckBox>> nodeList=rootNode.getChildren();
			if(null!=nodeList&&nodeList.size()>0){
				for(TreeItem<CheckBox> node:nodeList){
					if(node.getValue().isSelected()){
						List<TreeItem<CheckBox>> leafList=node.getChildren();
						if(null!=leafList&&leafList.size()>0){
							for(TreeItem<CheckBox> leaf:leafList){
								if(leaf.getValue().isSelected()){
									privilegeNameList.add(leaf.getValue().getText());
								}
							}
						}
					}
				}
			}
			if (null == systemName || systemName.equals("")) {
				Alert2.create("请选择系统名称。");
			} else if ("".equals(id) || "".equals(name)) {
				Alert2.create("信息不全,请输入。");
			} else if(null==privilegeNameList||privilegeNameList.size()<1){
				Alert2.create("请赋予一些权限。");
			}else {
				Role role=new Role();
				if (isEditing == 1) {// 编辑状态下的保存
					role=BaseService.getRoleService().getById(id);
//					role.setId(id);
					role.setRoleName(name);
					role.setRemark(remark);
					if (BaseService.getRoleService().isNameExist(name, id)) {
						Alert2.create("角色名称已存在！");
						nameText.requestFocus();
					} else {
						//与与另一系统同步
						BaseService.getRoleService()
								.updateWithPrivilege(role,privilegeNameList);
						if(null!=table.getSelectionModel().getSelectedItem()){
							data_table.set(data_table.indexOf(table.getSelectionModel().getSelectedItem()), new RoleInfo(id,name,remark));
						}else{
							Alert2.create("请先选择角色");
							return;
						}
						// 重新加载systemMap
						updateSystemMap();
						Alert.create("修改成功");
					}
					
					
					
				} else {// 新增状态下的保存
					if (BaseService.getRoleService().isIdExist(id)) {
						Alert.create("编号已存在！");
						idText.requestFocus();
					} else if (BaseService.getRoleService().isNameExist(
							name)) {
						Alert.create("角色名称已存在！");
						nameText.requestFocus();
					} else {
						role.setId(id);
						role.setRoleName(name);
						role.setRemark(remark);
						User user = SaveUserInfo.getUser();
						if (null != user) {
							role.setUser(user);
						}
						BaseService.getRoleService()
								.saveWithModuleAndTimeAndPrivilege(role, systemName,privilegeNameList);
						addBtn.setText("新增");
						data_table.add(new RoleInfo(id,name,remark));
						table.getSelectionModel().clearAndSelect(data_table.size()-1);
						// 重新加载systemMap
						updateSystemMap();

						Alert.create("保存成功");
						idText.setEditable(false);
						nameText.setEditable(false);
						remarkText.setEditable(false);
					}
				}
			}
		}
	}
	//取消树的所有选择
	private void cancelSelected(TreeView<CheckBox> tree) {
		TreeItem<CheckBox> rootNode=tree.getRoot();//
		List<TreeItem<CheckBox>> nodeList=rootNode.getChildren();
		if(null!=nodeList&&nodeList.size()>0){
			for(TreeItem<CheckBox> node:nodeList){
				node.getValue().setSelected(false);
				List<TreeItem<CheckBox>> leafList=node.getChildren();
				if(null!=leafList&&leafList.size()>0){
					for(TreeItem<CheckBox> leaf:leafList){
						leaf.getValue().setSelected(false);
					}
				}
			}
		}
		
	}

	/**
	 * 复选框点击事件
	 * 
	 * @param event
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event) {
		if (checkBox.isSelected()) {
			idText.setEditable(false);
			nameText.setEditable(true);
			remarkText.setEditable(true);
			addBtn.setText("保存");
			isEditing = 1;
		} else {
			idText.setEditable(false);
			nameText.setEditable(false);
			remarkText.setEditable(false);
			addBtn.setText("新增");
			isEditing = 0;
		}
	}

	/**
	 * 删除按钮1事件
	 * 
	 * @param event
	 */
	@FXML
	private void onDelBtnAction(ActionEvent event) {
		RoleInfo item = table.getSelectionModel().getSelectedItem();
		if (null == item || !data_table.contains(item)) {// table没有行被选中
			Alert2.create("请选择要删除的行");
		} else {
			
			Role obj = BaseService.getRoleService().getById(item.getId());
//			Set<User> users = obj.getUsers();
			
			List<User> users = BaseService.getRoleService().getUserListByRole(obj);
					
			if (null != users && users.size() > 0) {
				Alert2.create(item.getName() + "被引用，无法删除。");
			} else {
				if (Confirm2.create(MainFrame.mainWindow,"确定要删除\"" + item.getName()+ "\"吗？")) {
					data_table.remove(item);
					BaseService.getRoleService().delete(item.getId());
					table.getSelectionModel().clearSelection();
					idText.setText("");
					nameText.setText("");
					remarkText.setText("");
					cancelSelected(tree);
					}
				}
			}
	}
	// 退出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event) {
		((javafx.scene.control.Button) event.getSource()).getScene().getWindow().hide();
	}
	//table专用类
	public class RoleInfo{
		private SimpleStringProperty id=new SimpleStringProperty();
		private SimpleStringProperty name=new SimpleStringProperty();
		private SimpleStringProperty remark=new SimpleStringProperty();
		public RoleInfo(){}
		public RoleInfo(String id,String name,String remark){
			this.id=new SimpleStringProperty(id);
			this.name =new SimpleStringProperty(name);
			this.remark =new SimpleStringProperty(remark);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name =  new SimpleStringProperty(name);
		}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark =  new SimpleStringProperty(remark);
		}
		
		
	}

}
