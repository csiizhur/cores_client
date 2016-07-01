package com.lanen.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import com.lanen.base.BaseService;
import com.lanen.model.Department;
import com.lanen.model.User;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DepartmentFrameController implements Initializable {

	@FXML
	private TextField id;
	@FXML
	private TextField name;
	@FXML
	private TextArea remark;

	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private CheckBox checkBox;
	@FXML
	private ListView<String> listView;
	private ObservableList<String> data = FXCollections.observableArrayList();
	private int isEditing = 0;// 编辑状态为1

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initListView();
	}

	//初始化
	private void initListView() {
//		List<Department> list = BaseService.getDepartmentService().findAll();
		List<String> nameList = BaseService.getDepartmentService().findAllName_1();
//		if (null != list && list.size() > 0) {
//			for (Department obj : list) {
//				data.add(obj.getName());
//			}
//		}
		if (null != nameList && nameList.size() > 0) {
			for (String obj : nameList) {
				data.add(obj);
			}
		}
		listView.setItems(data);
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(isEditing==1){
					Department department = BaseService.getDepartmentService().getByName_1(newValue);
					if (null != department) {
						id.setText(department.getId());
						name.setText(department.getName());
						remark.setText(department.getRemark() == null ? ""
								: department.getRemark());
					}
				}
				
				
			}});
	}

	// 复选框点击事件
	@FXML
	private void onCheckBoxAction(ActionEvent event) {
		if (checkBox.isSelected()) {
			id.setEditable(false);
			addBtn.setText("确定");
			isEditing = 1;
			String item = listView.getSelectionModel().getSelectedItem();
			Department department = BaseService.getDepartmentService().getByName_1(item);
			if (null != department) {
				id.setText(department.getId());
				name.setText(department.getName());
				remark.setText(department.getRemark() == null ? ""
						: department.getRemark());
			}
		} else {
			 id.setText("");
			 name.setText("");
			 remark.setText("");
			id.setEditable(true);
			addBtn.setText("添加");
			isEditing = 0;
		}
	}

	// 新增按钮事件
	@FXML
	private void onAddBtnAction(ActionEvent event) {
			String department_id = id.getText().toString().trim();
			String department_name = name.getText().toString().trim();
			String department_remark = remark.getText().toString().trim();
			if ("".equals(department_id) || "".equals(department_name)) {
				Alert2.create("信息不全,请输入。");
			} else {
				Department department = new Department();
				if (isEditing == 1) {
					department = BaseService.getDepartmentService().getById(department_id);
					department.setId(department_id);
					department.setName(department_name);
					department.setRemark(department_remark);
					if (BaseService.getDepartmentService().isNameExist(
							department_name, department_id)) {
						Alert2.create("部门名称已存在！");
					} else {
						int  index=listView.getSelectionModel().getSelectedIndex();
						data.set(index, department_name);
						BaseService.getDepartmentService().update(department);
						Alert.create("修改成功");
					}
				} else {
					if (BaseService.getDepartmentService().isIdExist(
							department_id)) {
						Alert2.create("编号已存在！");
					} else if (BaseService.getDepartmentService().isNameExist(
							department_name)) {
						Alert2.create("部门名称已存在！");
					} else {
						department.setId(department_id);
						department.setName(department_name);
						department.setRemark(department_remark);
						BaseService.getDepartmentService().save(department);
						id.setText("");
						name.setText("");
						remark.setText("");
						listView.getSelectionModel().select(department_name);
						data.add(department_name);
						Alert.create("保存成功");
						DepartmentFrame2.setDepartmentName(department_name);
					}
				}

			}

	}
	//删除按钮事件
	@FXML
	private void onDelBtnAction(ActionEvent event){
		String item=listView.getSelectionModel().getSelectedItem();
		if(null==item||!data.contains(item)){
			Alert2.create("请选择要删除的行");
		}else{
			Department department=BaseService.getDepartmentService().getByName(item);
			
			List<User> userList = BaseService.getUserService().getUserListByDepartmentId(department.getId());
					
			if(null!=department && null!=userList && userList.size()>0){
				Alert2.create("部门\""+item+"\"被引用，无法删除！");
			}else if(Confirm2.create(MainFrame.mainWindow,"确定要删除\""+item+"\"吗？")){
				data.remove(item);
				BaseService.getDepartmentService().deleteByName(item);
				int index =listView.getSelectionModel().getSelectedIndex();
				if(data.size()>index){
					listView.getSelectionModel().clearAndSelect(index);
				}else{
					listView.getSelectionModel().clearAndSelect(data.size()-1);
				}
				if(DepartmentFrame2.getDepartmentName().equals(item)){
					DepartmentFrame2.setDepartmentName("");
				}
			}
		}
	}

	//退出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Button)event.getSource()).getScene().getWindow().hide();
	}
}
