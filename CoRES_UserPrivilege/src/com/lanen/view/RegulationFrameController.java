package com.lanen.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;




import com.lanen.base.BaseService;
import com.lanen.model.Regulation;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Confirm2;
import com.lanen.zero.main.MainFrame;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegulationFrameController implements Initializable {
	
	@FXML
	private TableView<RegulationInfo> table;
	@FXML
	private TableColumn<RegulationInfo,Integer> idCol;
	@FXML
	private TableColumn<RegulationInfo,String> regulationNameCol;
	@FXML
	private TableColumn<RegulationInfo,String> typeCol;
	@FXML
	private TableColumn<RegulationInfo,String> defaultValueCol;
	@FXML
	private TextField regulationNameText;
	@FXML
	private ComboBox<String> typeComboBox;
	@FXML
	private TextField defaultValueText;
	@FXML
	private Button addBtn;
	private final ObservableList<RegulationInfo> data = FXCollections.observableArrayList();
	
	//添加按钮事件
	@FXML
	private void onAddBtnAction(ActionEvent event){
		
			String regulationName=regulationNameText.getText().trim().toString();
			String type = typeComboBox.getSelectionModel().getSelectedItem();
			String defaultValue = defaultValueText.getText().trim().toString();
			if("".equals(regulationName)||null==type||"".equals(defaultValue)){
				Alert.create("信息不全,请输入。");
			}else{
				
				if(BaseService.getRegulationService().isExist(regulationName)){
					Alert.create("规则名称已存在！");
				}else{
					Regulation regulation = new Regulation();
					
					regulation.setRegulationName(regulationName);
					regulation.setType(type);
					regulation.setDefaultValue(defaultValue);
					Long id=BaseService.getRegulationService().saveReturnId(regulation);
					data.add(new RegulationInfo(id,regulationName,type,defaultValue));
					table.getSelectionModel().select(data.size()-1);
					regulationNameText.setText("");
					defaultValueText.setText("");
					typeComboBox.getSelectionModel().clearSelection();
					Alert.create("添加成功！");
				}
			}
		
	}
	//删除按钮事件
	@FXML
	private void onDeleteBtnAction(ActionEvent event){
		RegulationInfo regulationInfo=table.getSelectionModel().getSelectedItem();
		if(null==regulationInfo){
			Alert.create("请选择要删除的行");
		}else{
			if(Confirm2.create(MainFrame.mainWindow,"确定要删除\""+regulationInfo.getRegulationName()+"\"吗？")){
				table.getItems().remove(regulationInfo);
				BaseService.getRegulationService().delete(regulationInfo.getId());
			}else{
				
			}
		}
		
	}
	//退出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Button)event.getSource()).getScene().getWindow().hide();
	}
	//窗体初始化方法
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();

	}
	//表格初始化
	private void showTable() {
		idCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,Integer>("id"));
		regulationNameCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo, String>("regulationName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,String>("type"));
		defaultValueCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,String>("defaultValue"));
//		data.add(new RegulationInfo("01","admin","admin"));
		List<Regulation> list=BaseService.getRegulationService().findAll();
		if(null!=list&&list.size()>0){
			for(Regulation obj:list){
				data.add(new RegulationInfo(obj.getId(),obj.getRegulationName(),obj.getType(),obj.getDefaultValue()));
			}
		}	
		
		table.setItems(data);
	}
	public class RegulationInfo {
		private SimpleStringProperty regulationName=new SimpleStringProperty(); 
		private SimpleStringProperty type=new SimpleStringProperty(); 
		private SimpleStringProperty defaultValue=new SimpleStringProperty();
		private SimpleLongProperty id=new SimpleLongProperty();
		
		public RegulationInfo(){}
		public RegulationInfo(Long id,String regulationName,String type,String defaultValue){
			this.id=new SimpleLongProperty(id);
			this.regulationName =new SimpleStringProperty(regulationName);
			this.type =new SimpleStringProperty(type);
			this.defaultValue =new SimpleStringProperty(defaultValue);
		}

		public String getRegulationName() {
			return regulationName.get();
		}
		public void setRegulationName(String regulationName) {
			this.regulationName = new SimpleStringProperty(regulationName);
		}
		public String getType() {
			return type.get();
		}
		public void setType(String type) {
			this.type = new SimpleStringProperty(type);
		}
		public String getDefaultValue() {
			return defaultValue.get();
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = new SimpleStringProperty(defaultValue);
		}
		public Long getId() {
			return id.get();
		}
		public void setId(Long id) {
			this.id = new SimpleLongProperty(id);
		}
		
	}

}
