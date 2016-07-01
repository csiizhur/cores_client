package com.lanen.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;





import com.lanen.base.BaseService;
import com.lanen.model.Regulation;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Confirm2;
import com.lanen.zero.main.MainFrame;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class RegulationSetFrameController implements Initializable {

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
	private TableColumn<RegulationInfo,String> setValueCol;
	private final ObservableList<RegulationInfo> data = FXCollections.observableArrayList();
	
	public static int isEdited=0;
	//确定按钮事件
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		Regulation regulation=null;
		List<Regulation> list =new ArrayList<Regulation>();
		if(null!=data&&data.size()>0){
			for(RegulationInfo obj:data){
				regulation=new Regulation();
				regulation.setId(obj.getId());
				regulation.setRegulationName(obj.getRegulationName());
				regulation.setType(obj.getType());
				regulation.setDefaultValue(obj.getDefaultValue());
				regulation.setSetValue(obj.getSetValue());
				if(null!=SaveUserInfo.getUser()){
					
					regulation.setSetter(SaveUserInfo.getUser().getUserName());
				}
				list.add(regulation);
			}
			BaseService.getRegulationService().updateList(list);
		}
		((javafx.scene.control.Button)event.getSource()).getScene().getWindow().hide();
	}
	//退出按钮事件
	@FXML
	private void onExitBtnAction(ActionEvent event){
		if(isEdited==1){
			if(Confirm2.create(MainFrame.mainWindow,"你确定要放弃刚刚的修改吗？")){
				((javafx.scene.control.Button)event.getSource()).getScene().getWindow().hide();
			}else{
				return ;
			}
		}else{
			((javafx.scene.control.Button)event.getSource()).getScene().getWindow().hide();
		}
	
	}
	//窗体初始化方法
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isEdited=0;
		showTable();
		table.setEditable(true);
		Callback<TableColumn<RegulationInfo,String>,TableCell<RegulationInfo,String>> cellFactory = new Callback<TableColumn<RegulationInfo,String>,TableCell<RegulationInfo,String>>(){

			@SuppressWarnings("rawtypes")
			@Override
			public TableCell call(TableColumn p) {
				
				return new TextFieldTableCellImpl();
			}
			
		};
		setValueCol.setCellFactory(cellFactory);
		//更新值
		setValueCol.setOnEditCommit(new EventHandler<CellEditEvent<RegulationInfo,String>>(){

			@Override
			public void handle(CellEditEvent<RegulationInfo, String> t) {
				((RegulationInfo) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setSetValue(t.getNewValue());
			}});
	}
	//表格初始化
	private void showTable() {
		idCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,Integer>("id"));
		regulationNameCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo, String>("regulationName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,String>("type"));
		defaultValueCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,String>("defaultValue"));
		setValueCol.setCellValueFactory(new PropertyValueFactory<RegulationInfo,String>("setValue") );
		List<Regulation> list=BaseService.getRegulationService().findAll();
		if(null!=list&&list.size()>0){
			for(Regulation obj:list){
				data.add(new RegulationInfo(obj.getId(),obj.getRegulationName(),obj.getType(),obj.getDefaultValue(),obj.getSetValue()==null ? "":obj.getSetValue()));
			}
		}	
		
		table.setItems(data);
	}
	public class RegulationInfo {
		private SimpleStringProperty regulationName=new SimpleStringProperty(); 
		private SimpleStringProperty type=new SimpleStringProperty(); 
		private SimpleStringProperty defaultValue=new SimpleStringProperty();
		private SimpleLongProperty id=new SimpleLongProperty();
		private SimpleStringProperty setValue =new SimpleStringProperty();
		
		public RegulationInfo(){}
		public RegulationInfo(Long id,String regulationName,String type,String defaultValue,String setValue){
			this.id=new SimpleLongProperty(id);
			this.regulationName =new SimpleStringProperty(regulationName);
			this.type =new SimpleStringProperty(type);
			this.defaultValue =new SimpleStringProperty(defaultValue);
			this.setValue=new SimpleStringProperty(setValue);
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
		public String getSetValue() {
			return setValue.get();
		}
		public void setSetValue(String setValue) {
			this.setValue =new SimpleStringProperty(setValue);
		}
		
	}

	public  class TextFieldTableCellImpl extends TableCell<RegulationInfo,String>{
		
		private TextField textField;
		private String oldVaule;
		public TextFieldTableCellImpl(){
		}
		
		@Override
		public void startEdit() {
			super.startEdit();
			if(textField ==null){
				createTextField();
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
		}
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			
			setText((String)getItem());
			setGraphic(null);
		}


		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if(empty){
				setText(null);
				setGraphic(null);
			}else{
				if(isEditing()){
					if(textField != null){
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				}else{
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField(){
			oldVaule=getString();
			textField =new TextField(getString());
			textField.setMinWidth(this.getWidth()-this.getGraphicTextGap()*2);
			textField.setOnKeyReleased(new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent t) {
					if(t.getCode()==KeyCode.ENTER){
						if(!oldVaule.equals(textField.getText())){
							isEdited=1;
							commitEdit(textField.getText());
						}else{
							cancelEdit();
						}
					}else if(t.getCode()==KeyCode.ESCAPE){
						cancelEdit();
					}
				}
				
			});
			textField.setOnMouseExited(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					if(!oldVaule.equals(textField.getText())){
						isEdited=1;
						commitEdit(textField.getText());
					}else{
						cancelEdit();
					}
				}
			});
		}
		private String getString(){
			return getItem()==null ? "":getItem().toString();
		}
	}
}
