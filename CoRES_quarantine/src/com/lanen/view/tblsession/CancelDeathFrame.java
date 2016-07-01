package com.lanen.view.tblsession;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.model.quarantine.tblsession.TblAnimalDeath;
import com.lanen.model.tableview.TblSessionAnimalForTableView;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.view.main.Main;
import com.lanen.view.tblsession.DayToDayController.CheckBoxTableCell;

import javafx.application.Application;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CancelDeathFrame extends Application implements Initializable{
	/**
	 * 动物列表
	 */
	@FXML
	private static TableView<TblSessionAnimalForTableView> animalTable;
	private static ObservableList<TblSessionAnimalForTableView> data_animalTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblSessionAnimalForTableView,Boolean> selectCol;        //选择框
	@FXML
	private TableColumn<TblSessionAnimalForTableView,String> animalIdCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView,String> genderCol;
	@FXML
	private TableColumn<TblSessionAnimalForTableView,String> roomCol;
	
	private static String sessionId = "";
	
	private static CancelDeathFrame instance;
	public static CancelDeathFrame getInstance(){
		if(null == instance){
			instance = new CancelDeathFrame();
		}
		return instance;
	}
	public CancelDeathFrame(){
		
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		instance =this;
		initAnimalTable();
	}
	/**
	 * 初始化animalTable
	 */
	private void initAnimalTable() {
		selectCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView,Boolean>("select"));
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>("animalId"));
		genderCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>("gender"));
		roomCol.setCellValueFactory(new PropertyValueFactory<TblSessionAnimalForTableView, String>("room"));
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		selectCol.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView,Boolean>,TableCell<TblSessionAnimalForTableView,Boolean>>(){

			@Override
			public TableCell<TblSessionAnimalForTableView, Boolean> call(TableColumn<TblSessionAnimalForTableView, Boolean> p) {
				CheckBoxTableCell<TblSessionAnimalForTableView,Boolean> checkBoxTableCell =new CheckBoxTableCell<TblSessionAnimalForTableView,Boolean>();
//				checkBoxTableCell.setDisable(true);
				return checkBoxTableCell;
			}});
		genderCol.setCellFactory(new Callback<TableColumn<TblSessionAnimalForTableView, String>, TableCell<TblSessionAnimalForTableView, String>>(){

			@Override
			public TableCell<TblSessionAnimalForTableView, String> call(
					TableColumn<TblSessionAnimalForTableView, String> arg0) {
				TableCell<TblSessionAnimalForTableView, String> cell = 
						new TableCell<TblSessionAnimalForTableView, String>(){

							@Override
							protected void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
			    				 setText(empty ? null : getString());
			    				 setGraphic(null);
			    			 }
			    			 
			    			 private String getString() {
			    				 return getItem() == null ? "" : getItem().toString();
			    			 }
					
				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}
			
		});
		animalTable.setItems(data_animalTable);
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					TblSessionAnimalForTableView currentSelectItem = animalTable.getSelectionModel().getSelectedItem();
					if(null != currentSelectItem){
						TblSessionAnimalForTableView index = new TblSessionAnimalForTableView(
								currentSelectItem.getAnimalId(),(currentSelectItem.getGender().equals("♂") ? 1 : 2)
								,currentSelectItem.getRoom());
						index.setSelect(!currentSelectItem.getSelect());
						animalTable.getItems().set(animalTable.getItems().indexOf(currentSelectItem), index);
						animalTable.getSelectionModel().clearSelection();
					}
				}
				
			}});
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmButton(ActionEvent event){
		//1.检查是否选择动物
		List<String> animalIdList = new ArrayList<String>();
		for(TblSessionAnimalForTableView obj:data_animalTable){
			if(obj.getSelect()){
				animalIdList.add(obj.getAnimalId());
			}
		}
		if(animalIdList.size()<1){
			Alert2.create("请先选择待撤销动物");
			return ;
		}else{
			//2.提示，并电子签名
			if(Confirm.create(Main.getInstance().getStage(), 
					"提示", "死亡登记已签字，撤销登记将记录修改痕迹", "确定继续吗？")){
				//电子签名及原因
				SignFrameWithReason signFrameWithReason = new SignFrameWithReason("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("身份确认");
				try {
					signFrameWithReason.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 3.签字通过
			if ("OK".equals(SignFrameWithReason.getType())) {
				//3.1清除接收动物列表中对应的死亡标记等
				BaseService.getTblAnimalRecListService().updateAnimalDeadState
				(0, null, "", "", null, animalIdList);
				//3.2删除死亡登记对应动物列表
				BaseService.getTblAnimalDeathService().deleteAnimalBySessionIdAnimalIdList
				(sessionId,animalIdList);
				//3.3记录修改痕迹
				List<TblTrace> trbTraceList = new ArrayList<TblTrace>();
				TblTrace tblTrace = null ;
				Date date = new Date();
				TblAnimalDeath tblAnimalDeath = BaseService.getTblAnimalDeathService().getById(sessionId);
				String oldValue = tblAnimalDeath.getDeadType();//死亡原因
				for(String animalId:animalIdList){
					tblTrace = new TblTrace();
					String newValue =animalId+","+"-撤销-";
					tblTrace.setTableName("TblAnimalDeath");//表名
					tblTrace.setDataId(sessionId); //这里放的是会话Id
					tblTrace.setOperateMode(2);//删除
					tblTrace.setOldValue(oldValue);
					tblTrace.setNewValue(newValue);
					tblTrace.setOperator(SignFrameWithReason.getUser().getRealName());
					tblTrace.setHost(SystemTool.getIPAddress());
					tblTrace.setModifyReason(SignFrameWithReason.getReason());
					tblTrace .setModifyTime(date);
					trbTraceList.add(tblTrace);
				}
				BaseService.getTblTraceservice().saveList(trbTraceList);
				//3.4更新死亡登记窗口中的动物列表和修改痕迹列表
				AnimalDeathFrame.getInstance().loadData_animalTable();
				AnimalDeathFrame.getInstance().updateTblTraceTable();
				//3.5关闭窗口
				Button button = (Button) event.getSource();
				button.getScene().getWindow().hide();
			}
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onCancelButton(ActionEvent event){
		//关闭窗口
		Button button = (Button) event.getSource();
		button.getScene().getWindow().hide();
	}
	/**打开窗口是加载数据
	 * @param sessionId
	 * @param data_animalTable
	 */
	public void loadData(String sessionId,ObservableList<TblSessionAnimalForTableView> data_animalTable){
		CancelDeathFrame.sessionId = sessionId;
		CancelDeathFrame.data_animalTable = data_animalTable;
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("CancelDeathFrame.fxml"));
		stage.initOwner(Main.mainWidow);
		stage.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(root,400,340);
		stage.setScene(scene);
		stage.setTitle("撤销死亡登记");
		stage.setResizable(false);
		stage.showAndWait();
		
	}
}
