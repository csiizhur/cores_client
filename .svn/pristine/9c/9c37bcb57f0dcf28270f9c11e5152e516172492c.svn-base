package com.lanen.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


import com.lanen.base.BaseService;
import com.lanen.model.Module;
import com.lanen.model.Privilege;
import com.lanen.model.Systems;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm2;
import com.lanen.zero.main.MainFrame;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SystemFrameController implements Initializable {
	
	
	@FXML
	private TableView<SystemTableInfo> tableView1;
	@FXML
	private TableColumn<SystemTableInfo, String> idCol;
	@FXML
	private TableColumn<SystemTableInfo, String> nameCol;
	@FXML
	private TableColumn<SystemTableInfo, String> remarkCol;
	@FXML
	private TableView<SystemTableInfo> tableView2;
	@FXML
	private TableColumn<SystemTableInfo, String> moduleNameCol;
	@FXML
	private TextField idText;
	@FXML
	private TextField nameText;
	@FXML
	private TextField remarkText;
	@FXML
	private TextField moduleNameText;
	@FXML
	private CheckBox checkBox1;
	@FXML
	private Button addBtn1;
	@FXML
	private Button delBtn1;
	@FXML
	private Button addBtn2;
	@FXML
	private Button delBtn2;
	private final ObservableList<SystemTableInfo> data1 = FXCollections.observableArrayList();
	private final ObservableList<SystemTableInfo> data2 =FXCollections.observableArrayList();
	private int isEditing=0;//1表示编辑状态
	private SystemTableInfo systemTableInfo_editing;//编辑前值
	// 推出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event) {
		((javafx.scene.control.Button) event.getSource()).getScene()
				.getWindow().hide();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showTable1();
		showTable2();

	}

	// 初始化表1
	private void showTable1() {
		idCol.setCellValueFactory(new PropertyValueFactory<SystemTableInfo, String>(
				"id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<SystemTableInfo, String>(
				"name"));
		remarkCol
				.setCellValueFactory(new PropertyValueFactory<SystemTableInfo, String>(
						"remark"));
		List<Systems> list = BaseService.getSystemService()
				.findAll();
		if (null != list && list.size() > 0) {
			for (Systems obj : list) {
				data1.add(new SystemTableInfo(obj.getId(), obj.getSystemName(),
						obj.getRemark()));
			}
		}
		tableView1.setItems(data1);
		tableView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SystemTableInfo item = tableView1.getSelectionModel()
						.getSelectedItem();
				if (null != item && data1.contains(item)) {
					systemTableInfo_editing = item;
					Systems obj = BaseService.getSystemService()
							.getById(item.getId());
					if (null != obj) {
						idText.setText(item.getId());
						nameText.setText(item.getName());
						remarkText.setText(item.getRemark() == null ? "" : item.getRemark());
						// tableView2初始化
						Set<Module> set = obj.getModules();
						List<Module> modules = new ArrayList<Module>(set);
						if (null == modules || modules.size() < 1) {
							data2.clear();// 清空
						} else {
							data2.clear();// 清空
							for (Module module : modules) {
								data2.add(new SystemTableInfo(module.getId(),module.getModuleName(), ""));
							}
						}
						if (checkBox1.isSelected()) {
							idText.setEditable(false);
							nameText.setEditable(true);
							remarkText.setEditable(true);
							addBtn1.setText("保存");
							isEditing = 1;
						} else {
							idText.setEditable(false);
							nameText.setEditable(false);
							remarkText.setEditable(false);
							addBtn1.setText("新增");
							isEditing = 0;
						}

					}
				}
			}

		});
	}

	// 初始化表2
	private void showTable2() {
		moduleNameCol
				.setCellValueFactory(new PropertyValueFactory<SystemTableInfo, String>(
						"name"));
		tableView2.setItems(data2);
	}

	// 新增按钮1(左边)事件
	@FXML
	private void onAddBtn1Action(ActionEvent event) {
		String flag = addBtn1.getText();
		if ("新增".equals(flag)) {
			isEditing = 0;
			checkBox1.setSelected(false);
			idText.setText("");
			nameText.setText("");
			remarkText.setText("");
			idText.setEditable(true);
			nameText.setEditable(true);
			remarkText.setEditable(true);
			addBtn1.setText("保存");
			isEditing = 0;
			idText.requestFocus();
		} else if ("保存".equals(flag)) {
			String id = idText.getText().toString().trim();
			String name = nameText.getText().toString().trim();
			String remark = remarkText.getText().toString().trim();
			if ("".equals(id) || "".equals(name)) {
				Alert2.create("信息不全,请输入。");
			} else {
				Systems obj = new Systems();
				if (isEditing == 1) {// 编辑状态下的保存
					if (id.equals(systemTableInfo_editing.getId())) {
						obj = BaseService.getSystemService().getById(id);
						obj.setSystemName(name);
						obj.setRemark(remark);
						if (BaseService.getSystemService().isNameExist(
								name, id)) {
							Alert2.create("系统名称已存在！");
						} else {
							data1.set(data1.indexOf(systemTableInfo_editing),new SystemTableInfo(id, name, remark));
							BaseService.getSystemService().update(obj);
							SystemTableInfo item = tableView1.getSelectionModel().getSelectedItem();
							systemTableInfo_editing = item;
							Alert.create("修改成功");
						}
					} else {
						Alert2.create("修改失败");
					}
				} else {// 新增状态下的保存
					if (BaseService.getSystemService().isIdExist(id)) {
						Alert2.create("编号已存在！");
						idText.requestFocus();
					} else if (BaseService.getSystemService().isNameExist(
							name)) {
						Alert2.create("系统名称已存在！");
						nameText.requestFocus();
					} else {
						obj.setId(id);
						obj.setSystemName(name);
						obj.setRemark(remark);
						BaseService.getSystemService().save(obj);
						addBtn1.setText("新增");
						data1.add(new SystemTableInfo(id, name, remark));
						tableView1.getSelectionModel().clearAndSelect(data1.size() - 1);
						// 移除表二 值
						data2.clear();

						Alert.create("保存成功");
						idText.setEditable(false);
						nameText.setEditable(false);
						remarkText.setEditable(false);
					}
				}

			}

		}
	}

	// 复选框1(左边)点击事件
	@FXML
	private void onCheckBox1Action(ActionEvent event) {
		SystemTableInfo item = tableView1.getSelectionModel().getSelectedItem();
		if (null == item || !data1.contains(item)) {// tableView1没有行被选中
			idText.setText("");
			nameText.setText("");
			remarkText.setText("");
		} else if (checkBox1.isSelected()) {
			idText.setText(item.getId());
			nameText.setText(item.getName());
			remarkText.setText(item.getRemark());
		}
		if (checkBox1.isSelected()) {
			idText.setEditable(false);
			nameText.setEditable(true);
			remarkText.setEditable(true);
			addBtn1.setText("保存");
			isEditing = 1;
		} else {
			idText.setEditable(false);
			nameText.setEditable(false);
			remarkText.setEditable(false);
			addBtn1.setText("新增");
			isEditing = 0;
		}
	}

	// 删除按钮1(左边)事件
	@FXML
	private void onDelBtn1Action(ActionEvent event) {
		SystemTableInfo item = tableView1.getSelectionModel().getSelectedItem();
		if (null == item || !data1.contains(item)) {// tableView1没有行被选中
			Alert2.create("请选择要删除的行");
		} else {
			Systems obj = BaseService.getSystemService().getById(item.getId());
			Set<Module> modules = obj.getModules();
			if (null != modules && modules.size() > 0) {
				Alert2.create(item.getName() + "被引用，无法删除。");
			} else {
				if (Confirm2.create(MainFrame.mainWindow,"确定要删除\""
						+ item.getName() + "\"吗？")) {
					int index = tableView1.getSelectionModel()
							.getSelectedIndex();
					data1.remove(item);
					BaseService.getSystemService().delete(item.getId());
					if (data1.size() > index) {// 长度大于当前索引（删除前的）
						tableView1.getSelectionModel().clearAndSelect(index);
						systemTableInfo_editing = tableView1.getSelectionModel().getSelectedItem();
						Systems system = BaseService.getSystemService().getById(
										systemTableInfo_editing.getId());
						if (null != system) {
							// tableView2初始化
							Set<Module> set = obj.getModules();
							List<Module> list = new ArrayList<Module>(set);
							if (null == list || list.size() < 1) {
								data2.clear();// 清空
							} else {
								data2.clear();// 清空
								for (Module module : list) {
									data2.add(new SystemTableInfo(module
											.getId(), module.getModuleName(),""));
								}
							}
						}
					} else {
						tableView1.getSelectionModel().clearSelection();
					}
				}
				idText.setText("");
				nameText.setText("");
				remarkText.setText("");
			}
		}
	}

	// 新增按钮2(右边)事件
	@FXML
	private void onAddBtn2Action(ActionEvent event) {
		SystemTableInfo item_table1 = tableView1.getSelectionModel()
				.getSelectedItem();
		if (null == item_table1 || !data1.contains(item_table1)) {
			Alert2.create("请先选择被增加系统。");
		} else {
			String name = moduleNameText.getText().toString().trim();
			if ("".equals(name)) {
				Alert2.create("信息不全,请输入。");
			} else {
				Module obj = new Module();
				if (BaseService.getModuleService().isIdNameExist(name)) {
					Alert2.create("模块名称已存在！");
					moduleNameText.requestFocus();
				} else {
					obj.setId(name);
					obj.setModuleName(name);
					Systems system = BaseService.getSystemService()
							.getById(item_table1.getId());
					obj.setSystem(system);
					BaseService.getModuleService().save(obj);
					data2.add(new SystemTableInfo(name, name, ""));
					tableView2.getSelectionModel().clearAndSelect(
							data2.size() - 1);
					Alert.create("保存成功");
				}
			}

		}
	}
	// 删除按钮2(右边)事件
		@FXML
		private void onDelBtn2Action(ActionEvent event) {
			SystemTableInfo item = tableView2.getSelectionModel().getSelectedItem();
			if (null == item || !data2.contains(item)) {// tableView2没有行被选中
				Alert2.create("请选择要删除的行");
			} else {
				Module module = new Module();
				module.setId(item.getId());
				List<Privilege> privilegeList = BaseService.getPrivilegeService().getPrivilegeListByModule(module);
				if(null != privilegeList && privilegeList.size()>0){
					Alert2.create("模块被引用，无法删除！");
				}else{
					if (Confirm2.create(MainFrame.mainWindow,"确定要删除\""
							+ item.getName() + "\"吗？")) {
						int index = tableView2.getSelectionModel()
								.getSelectedIndex();
						data2.remove(item);
						BaseService.getModuleService().delete(item.getId());
						if (data2.size() > index) {// 长度大于当前索引（删除前的）
							tableView2.getSelectionModel().clearAndSelect(index);
						}
					}
				}
			}
		}

	
	public class SystemTableInfo{
		private SimpleStringProperty id=new SimpleStringProperty();
		private SimpleStringProperty name=new SimpleStringProperty();
		private SimpleStringProperty remark=new SimpleStringProperty();
		
		public SystemTableInfo(){}
		public SystemTableInfo(String id,String name,String remark){
			this.id =new SimpleStringProperty(id);
			this.name= new SimpleStringProperty(name);
			this.remark=new SimpleStringProperty(remark);
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
			this.name = new SimpleStringProperty(name);
		}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		
		
	}

}
