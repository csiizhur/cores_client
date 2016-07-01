package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblDeadAnimalDeathReason;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

/**致死原因页面（列表）
 * @author Administrator
 *
 */
public class DeathReasonPage extends Application implements Initializable {
	
	@FXML
	private Label animalCodeLabel;
//	@FXML
//	private Label studyNoLabel;			//专题编号   Label
//	@FXML
//	private Label animalTypeLabel;		//动物种类Label
//	@FXML
//	private Label anatomyRsnLabel;		//解剖原因Label
	
	@FXML
	private Button addButton; //添加按钮
	
	@FXML
	private Button delButton; //删除按钮
	
	@FXML
	private TableView<DeathReason> deathReasonTable; //致死原因表
	private ObservableList<DeathReason> data_deathReasonTable = FXCollections.observableArrayList();
//	private StringProperty deathReason;	   //致死原因
//	private StringProperty remark;         //备注
//	private StringProperty delFlag;        //删除标记  
//	private StringProperty delRsn;         //删除原因
//	private StringProperty delTime;        //删除时间
	@FXML
	private TableColumn<DeathReason,String> deathReasonCol;  		
	@FXML
	private TableColumn<DeathReason,String> remarkCol;  		
	@FXML
	private TableColumn<DeathReason,String> delFlagCol;  		
	@FXML
	private TableColumn<DeathReason,String> delRsnCol;  		
	@FXML
	private TableColumn<DeathReason,String> delTimeCol;  		

	private String studyNo = "";
	private String animalCode = "";
	private String anatomyRsn = "";
	private String animalStrain = "";
	private static DeathReasonPage instance;
	public static DeathReasonPage getInstance(){
		if(null == instance){
			instance = new DeathReasonPage();
		}
		return instance;
	}
	
	public DeathReasonPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTable();
	}
	
	
	/**
	 * 初始化表格
	 */
	private void initTable() {
		deathReasonTable.setItems(data_deathReasonTable);
		deathReasonCol.setCellValueFactory(new PropertyValueFactory<DeathReason,String>("deathReason"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<DeathReason,String>("remark"));
		delFlagCol.setCellValueFactory(new PropertyValueFactory<DeathReason,String>("delFlag"));
		delRsnCol.setCellValueFactory(new PropertyValueFactory<DeathReason,String>("delRsn"));
		delTimeCol.setCellValueFactory(new PropertyValueFactory<DeathReason,String>("delTime"));
		
		deathReasonTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<DeathReason>(){

			@Override
			public void changed(ObservableValue<? extends DeathReason> arg0, DeathReason arg1,
					DeathReason newValue) {
					delButton.setDisable(true);
					if(null != newValue){
						if(null == newValue.getDelFlag() || "".equals(newValue.getDelFlag())){
							delButton.setDisable(false);
						}
					}
				}

		});
		delFlagCol.setCellFactory(new Callback<TableColumn<DeathReason,String>,TableCell<DeathReason,String>>(){
			@Override
			public TableCell<DeathReason, String> call(
					TableColumn<DeathReason, String> param) {
				TableCell<DeathReason, String> cell =
						new TableCell<DeathReason, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
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
		delTimeCol.setCellFactory(new Callback<TableColumn<DeathReason,String>,TableCell<DeathReason,String>>(){
			@Override
			public TableCell<DeathReason, String> call(
					TableColumn<DeathReason, String> param) {
				TableCell<DeathReason, String> cell =
						new TableCell<DeathReason, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
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
	}
	/**
	 * 登记
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event){
		//打开致死原因登记窗口
		Stage stage = Main.stageMap.get("DeathReasonRegister");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				DeathReasonRegister.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("DeathReasonRegister",stage);
		}else{
			stage.show();
		}
		DeathReasonRegister.getInstance().loadData(studyNo,animalCode,anatomyRsn,animalStrain);
	}
	/**
	 * 删除
	 */
	@FXML
	private void onDelBtnAction(ActionEvent event){
		DeathReason selectedItem = deathReasonTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem && 
				(null == selectedItem.getDelFlag() || selectedItem.getDelFlag().equals(""))){
			if(Sign.openSignWithReasonFrame("删除原因", "")){
				String id = selectedItem.getId();
				String reason = Sign.getReason();
				String realName = SaveUserInfo.getRealName();
				Json json = BaseService.getInstance().getTblDeadAnimalDeathReasonService().delOne(id,reason,realName);
				if(json.isSuccess()){
					updateTable();
				}else{
					showErrorMessage(json.getMsg());
				}
			}
		}
	}
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String animalCode,String anatomyRsn,String animalStrain) {
		this.studyNo = studyNo;
		this.animalCode = animalCode;
		this.anatomyRsn = anatomyRsn;
		this.animalStrain = animalStrain;
		
		//1.
		animalCodeLabel.setText(animalCode);
//		studyNoLabel.setText(studyNo);
//		anatomyRsnLabel.setText(anatomyRsn);
//		animalTypeLabel.setText(animalStrain);
		updateAnimalMsg();
		
		//2.删除按钮不可用
		delButton.setDisable(true);
		
		//3.更新表格数据
		updateTable();
		
	}
	
	/**更新动物信息
	 * 
	 */
	private void updateAnimalMsg() {
		animalCodeLabel.setText("");
		String msg = "";
		msg = msg+"动物编号："+animalCode;
		msg = msg+"    专题编号："+studyNo;
		if(null != animalStrain && !"".equals(animalStrain)){
			msg = msg+"    动物种类："+animalStrain;
		}
		if(null != anatomyRsn && !"".equals(anatomyRsn)){
			msg = msg+"    解剖原因："+anatomyRsn;
		}
		animalCodeLabel.setText(msg);
	}

	/**
	 * 更新表格数据
	 */
	public void updateTable() {
		data_deathReasonTable.clear();
		
		List<TblDeadAnimalDeathReason> list = BaseService.getInstance().getTblDeadAnimalDeathReasonService().getListByStudyNoAnimalCode(studyNo,animalCode);
		if(null != list && list.size() > 0){
			for(TblDeadAnimalDeathReason obj:list){
				data_deathReasonTable.add(new DeathReason(obj.getId(),obj.getDeathReason(),obj.getRemark(),
						obj.getDelFlag(),obj.getDelRsn(),obj.getDelTime()));
			}
			
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DeathReasonPage.fxml"));
		Scene scene = new Scene(root, 636, 412);
		stage.setScene(scene);
		stage.setTitle("致死原因");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				HistopathCheckPage.getInstance().updateDeathReasonLabel_01();
			}
		});
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

	public class DeathReason{
		private StringProperty id;
		private StringProperty deathReason;	   //致死原因
		private StringProperty remark;         //备注
		private StringProperty delFlag;        //删除标记  
		private StringProperty delRsn;         //删除原因
		private StringProperty delTime;        //删除时间
		
		public DeathReason(){
			
		}
		public DeathReason(String id,String deathReason,String remark,Integer delFlag,String delRsn,Date delTime){
			setId(id);
			setDeathReason(deathReason);
			setRemark(remark);
			if(delFlag == 1){
				setDelFlag("删除");
			}else{
				setDelFlag("");
			}
			setDelRsn(delRsn);
			setDelTime(DateUtil.dateToString(delTime, "yyyy-MM-dd HH:mm"));
		}
		
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getDeathReason() {
			return deathReason.get();
		}
		public void setDeathReason(String deathReason) {
			this.deathReason = new SimpleStringProperty(deathReason);
		}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		public String getDelFlag() {
			return delFlag.get();
		}
		public void setDelFlag(String delFlag) {
			this.delFlag = new SimpleStringProperty(delFlag);
		}
		public String getDelRsn() {
			return delRsn.get();
		}
		public void setDelRsn(String delRsn) {
			this.delRsn = new SimpleStringProperty(delRsn);
		}
		public String getDelTime() {
			return delTime.get();
		}
		public void setDelTime(String delTime) {
			this.delTime = new SimpleStringProperty(delTime);
		}
		
	}
}
