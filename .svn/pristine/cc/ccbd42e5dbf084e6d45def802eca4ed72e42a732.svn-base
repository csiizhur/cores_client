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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PrivilegeFrameController implements Initializable {
	
	
	@FXML
	private ComboBox<String> systemComboBox;
	@FXML
	private ComboBox<String> moduleComboBox;
	@FXML
	private TreeView<String> treeView;
	@FXML
	private TextField idText;
	@FXML
	private TextField nameText;
	
	@FXML
	private TextField pathText;   //webURL
	
	
	@FXML
	private TextArea remarkText;
	@FXML
	private CheckBox checkBox;
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	private ObservableList<String> data_systemComboBox=FXCollections.observableArrayList();//system下拉框值
	private Map<String,Systems> systemMap=new HashMap<String,Systems>();//
	private ObservableList<String> data_moduleComboBox=FXCollections.observableArrayList();//module下拉框值
	
	private TreeItem<String> rootNode =new TreeItem<String>(); 

	private int isEditing=0;//复选框被选中，处于编辑状态
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initSystemComboBox();
	}

	/**
	 * systemComboBox 初始化
	 */
	private void initSystemComboBox() {
		List<Systems> systemList=new ArrayList<Systems>();
		systemList=BaseService.getSystemService().findAll();
		if(null!=systemList&&systemList.size()>0){//系统列表不为空
			for(Systems obj:systemList){
				systemMap.put(obj.getSystemName(), obj);
			}
			data_systemComboBox.addAll(systemMap.keySet());
		}
		systemComboBox.setItems(data_systemComboBox);
		if(null!=data_systemComboBox&&data_systemComboBox.size()>0){
			systemComboBox.getSelectionModel().select(0);
			//初始化树
			initTreeView(systemComboBox.getSelectionModel().getSelectedItem());
			//初始化module下拉框
			initModuleConboBox(systemComboBox.getSelectionModel().getSelectedItem());
			
		}
		//system 下拉框的selected改变事件
        systemComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				//初始化树
				initTreeView(newValue);
				//初始化module下拉框
				initModuleConboBox(newValue);
			}
			});
	}

	/**
	 * moduleComboBox 初始化
	 * @param selectedItem
	 */
	private void initModuleConboBox(String selectedItem) {
		data_moduleComboBox.clear();
		Systems system =systemMap.get(selectedItem);
		List<Module> moduleList=new ArrayList<Module>(system.getModules());
		if(null!=moduleList&&moduleList.size()>0){
			for(Module module:moduleList){
				data_moduleComboBox.add(module.getModuleName());
			}
		}
		moduleComboBox.setItems(data_moduleComboBox);
		moduleComboBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String arg1, String newValue) {
						if(null!=newValue){
							List<TreeItem<String>> treeItems = treeView.getRoot()
									.getChildren();
							TreeItem<String> item = treeView.getSelectionModel()
									.getSelectedItem();
							if (null != item && !treeItems.contains(item)) {//选中的是权限项
								for (TreeItem<String> node : treeItems) {
									if (node.getValue().equals(newValue)) {//找到模块项
										if(!node.equals(item.getParent())){//模块项不是选中项的上级
											treeView.getSelectionModel().select(node);
										}
									}
								}
							} else {//没有选中项或者选中选中的是模块
								for (TreeItem<String> node : treeItems) {
									if (node.getValue().equals(newValue)) {
										treeView.getSelectionModel().select(node);
									}
								}
							}
						}
					}
				});

	}

	/**
	 * 初始化树
	 * @param selectedItem
	 */
	private void initTreeView(String selectedItem) {
		rootNode.getChildren().clear();
		rootNode.setValue(selectedItem);
		rootNode.setExpanded(true);
		Systems system =systemMap.get(selectedItem);
		List<Module> moduleList=new ArrayList<Module>(system.getModules());
		if(null!=moduleList&&moduleList.size()>0){
			for(Module obj:moduleList){
				TreeItem<String> depNode = new TreeItem<String>(obj.getModuleName());
				rootNode.getChildren().add(depNode);
				
//				List<Privilege> privilegeList=new ArrayList<Privilege>(obj.getPrivileges());
				
				List<Privilege> privilegeList = BaseService.getPrivilegeService().getPrivilegeListByModule(obj);
				
				if(null!=privilegeList&&privilegeList.size()>0){
					for(Privilege privilege:privilegeList){
						TreeItem<String> empLeaf =new TreeItem<String>(privilege.getPrivilegeName());
						depNode.getChildren().add(empLeaf);
					}
				}
			}
		}
		treeView.setRoot(rootNode);
		//被选中节点的变化
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(
					ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
					if(null!=newValue){
						if(data_moduleComboBox.contains(
								newValue.getValue())){
							int index =data_moduleComboBox.indexOf(newValue.getValue());
							moduleComboBox.getSelectionModel().clearAndSelect(index);
//							idText.setText("");
//							nameText.setText("");
//							remarkText.setText("");
						}else{
							int index=data_moduleComboBox.indexOf(newValue.getParent().getValue());
							moduleComboBox.getSelectionModel().clearAndSelect(index);
							//选中权限（叶子节点），填充文本框
							Privilege privilege =BaseService.getPrivilegeService().getByName(newValue.getValue());
							if(null!=privilege){
								idText.setText(privilege.getId());
								nameText.setText(privilege.getPrivilegeName());
								
								pathText.setText(privilege.getPrivilegePath()==null ? "":privilege.getPrivilegePath());
								
								remarkText.setText(privilege.getRemark());
								if(isEditing==0){
									addBtn.setText("新增");
									idText.setEditable(false);
									
									nameText.setEditable(false);
									pathText.setEditable(false);
									
									
									remarkText.setEditable(false);
								}
							}
						}
					}
			}});
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
			
			pathText.setText("");
			
			remarkText.setText("");
			idText.setEditable(true);
			nameText.setEditable(true);

			pathText.setEditable(true);
			
			remarkText.setEditable(true);
			addBtn.setText("保存");
			isEditing = 0;
			idText.requestFocus();
			
			String moduleName= moduleComboBox.getSelectionModel().getSelectedItem();
			if(null != moduleName){
				moduleComboBox.getSelectionModel().clearSelection();
				moduleComboBox.getSelectionModel().select(moduleName);
			}
			
		} else if ("保存".equals(flag)) {
			String id = idText.getText().toString().trim();
			String name = nameText.getText().toString().trim();
			
			String path = pathText.getText().toString().trim();
			
			String remark = remarkText.getText().toString().trim();
			String moduleName = moduleComboBox.getSelectionModel()
					.getSelectedItem();
			if (null == moduleName || moduleName.equals("")) {
				Alert2.create("请选择模块名称。");
			} else if ("".equals(id) || "".equals(name)) {
				Alert2.create("信息不全,请输入。");
			} else {
				Privilege obj = new Privilege();
				if (isEditing == 1) {// 编辑状态下的保存
					obj = BaseService.getPrivilegeService().getById(id);
					String oldName = obj.getPrivilegeName();
					String oldModuleName = obj.getModule().getModuleName();
					obj.setPrivilegeName(name);
					obj.setPrivilegePath(path);
					obj.setRemark(remark);
					if (BaseService.getPrivilegeService().isNameExist(name, id)) {
						Alert2.create("权限名称已存在！");
					} else {
						TreeItem<String> oldNode = null;// 之前的节点（非叶子）
						TreeItem<String> newLeaf = null;// 待处理的叶子节点
						if (oldModuleName.equals(moduleName)) {
							BaseService.getPrivilegeService().update(obj);
						} else {
							BaseService.getPrivilegeService().update(obj,
									moduleName);
						}
						// 删除旧节点
						List<TreeItem<String>> treeItems = treeView.getRoot()
								.getChildren();
						if (null != treeItems && treeItems.size() > 0) {
							for (TreeItem<String> node : treeItems) {
								if (node.getValue().equals(oldModuleName)) {
									List<TreeItem<String>> leafList = node
											.getChildren();
									if (null != leafList && leafList.size() > 0) {
										for (TreeItem<String> leaf : leafList) {
											if (leaf.getValue().equals(oldName)) {
												oldNode = node;
												leaf.setValue(name);
												newLeaf = leaf;// 待处理的叶子节点
											}
										}

									}
								}
							}
						}
						// 不是老的模块
						if (!moduleName.equals(oldModuleName)) {
							oldNode.getChildren().remove(newLeaf);
							treeView.getSelectionModel().getSelectedItem()
									.getChildren().add(newLeaf);
						}
						treeView.getSelectionModel().select(newLeaf);
						// 重新加载systemMap
						updateSystemMap();
						Alert.create("修改成功");
					}
				} else {// 新增状态下的保存
					if (BaseService.getPrivilegeService().isIdExist(id)) {
						Alert2.create("编号已存在！");
						idText.requestFocus();
					} else if (BaseService.getPrivilegeService().isNameExist(
							name)) {
						Alert2.create("系统名称已存在！");
						nameText.requestFocus();
					} else {
						obj.setId(id);
						obj.setPrivilegeName(name);
						
						obj.setPrivilegePath(path);
						
						obj.setRemark(remark);
						User user = SaveUserInfo.getUser();
						if (null != user) {
							obj.setUser(user);
						}
						BaseService.getPrivilegeService()
								.saveWithModuleAndTime(obj, moduleName);
						addBtn.setText("新增");
						
						
						
						TreeItem<String> selectedItem =treeView.getSelectionModel().getSelectedItem();
						if(null != selectedItem){
							if(moduleName.equals(selectedItem.getValue())){
								selectedItem.getChildren().add(new TreeItem<String>(name));
							}else{
								selectedItem.getParent().getChildren().add(new TreeItem<String>(name));
							}
						}else{
//							TreeItem<String> arg0 = new TreeItem<String>();
//							treeView.getSelectionModel().select(arg0 );
						}
						// 重新加载systemMap
						updateSystemMap();

						Alert.create("保存成功");
						idText.setEditable(false);
						nameText.setEditable(false);
						
						pathText.setEditable(false);
						
						remarkText.setEditable(false);
					}
				}

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
	 * 复选框点击事件
	 * 
	 * @param event
	 */
	@FXML
	private void onCheckBoxAction(ActionEvent event) {
		if (checkBox.isSelected()) {
			idText.setEditable(false);
			nameText.setEditable(true);
			
			pathText.setEditable(true);

			remarkText.setEditable(true);
			addBtn.setText("保存");
			isEditing = 1;
		} else {
			idText.setEditable(false);
			nameText.setEditable(false);
			
			pathText.setEditable(false);
			
			remarkText.setEditable(false);
			addBtn.setText("新增");
			isEditing = 0;
		}
	}

	/**
	 * 删除按钮事件
	 * 
	 * @param event
	 */
	@FXML
	private void onDelBtnAction(ActionEvent event) {
		TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
		if (null == item) {//
			Alert2.create("请选择要删除项");
		} else {
			List<TreeItem<String>> nodeList = treeView.getRoot().getChildren();
			boolean found = false;
			if (null != nodeList && nodeList.size() > 0) {
				for (TreeItem<String> node : nodeList) {
					if (node.equals(item)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				Alert2.create("不能删除模块！");
			} else{
				Privilege privilege =BaseService.getPrivilegeService().getByName(item.getValue());
//				Set<Role> set=privilege.getRoles();
				
				List<Role> set = BaseService.getPrivilegeService().getRoleListByPrivilege(privilege);
				
				if(null!=set&&set.size()>0){
					Alert2.create("被引用，无法删除！");
				}else if (Confirm2.create(MainFrame.mainWindow,"确定要删除\""
						+ item.getValue() + "\"吗？")) {
					item.getParent().getChildren().remove(item);
					BaseService.getPrivilegeService().deleteByName(item.getValue());
					updateSystemMap();
					treeView.getSelectionModel().clearSelection();
					idText.setText("");
					nameText.setText("");
					remarkText.setText("");
				}
			}
		}
	}

	// 退出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event) {
		((javafx.scene.control.Button) event.getSource()).getScene()
				.getWindow().hide();
	}

	
}
