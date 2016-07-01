package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.lanen.model.path.TblAnimalTargetOrgan;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

/**致死原因页面（列表）
 * @author Administrator
 *
 */
public class TargetOrganPage extends Application implements Initializable {
	
	@FXML
	private Label studyNoLabel;			//专题编号   Label
	
	@FXML
	private Button addButton; //添加按钮
	
	@FXML
	private Button delButton; //删除按钮
	
	@FXML
	private TableView<TargetOrgan> targetOrganTable; //靶器官Table
	private ObservableList<TargetOrgan> data_targetOrganTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<TargetOrgan,String> visceraNameCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> occurPhaseCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> remarkCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> registerCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> regDateCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> genderCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> targetOrganFlagCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> delFlagCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> delRsnCol;  		
	@FXML
	private TableColumn<TargetOrgan,String> delTimeCol;  		

	private String studyNo = "";
	private static TargetOrganPage instance;
	public static TargetOrganPage getInstance(){
		if(null == instance){
			instance = new TargetOrganPage();
		}
		return instance;
	}
	
	public TargetOrganPage() {
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
		targetOrganTable.setItems(data_targetOrganTable);
		
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("visceraName"));
		occurPhaseCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("occurPhase"));
		remarkCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("remark"));
		registerCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("register"));
		regDateCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("regDate"));
		genderCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("gender"));
		targetOrganFlagCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("targetOrganFlag"));
		delFlagCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("delFlag"));
		delRsnCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("delRsn"));
		delTimeCol.setCellValueFactory(new PropertyValueFactory<TargetOrgan,String>("delTime"));
		
		targetOrganTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<TargetOrgan>(){

			@Override
			public void changed(ObservableValue<? extends TargetOrgan> arg0, TargetOrgan arg1,
					TargetOrgan newValue) {
					delButton.setDisable(true);
					if(null != newValue){
						if(null == newValue.getDelFlag() || "".equals(newValue.getDelFlag())){
							delButton.setDisable(false);
						}
					}
				}

		});
		delFlagCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		delTimeCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		registerCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		regDateCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		genderCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		targetOrganFlagCol.setCellFactory(new Callback<TableColumn<TargetOrgan,String>,TableCell<TargetOrgan,String>>(){
			@Override
			public TableCell<TargetOrgan, String> call(
					TableColumn<TargetOrgan, String> param) {
				TableCell<TargetOrgan, String> cell =
						new TableCell<TargetOrgan, String>() {
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
		Stage stage = Main.stageMap.get("TargetOrganRegister");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				TargetOrganRegister.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("TargetOrganRegister",stage);
		}else{
			stage.show();
		}
		TargetOrganRegister.getInstance().loadData(studyNo);
	}
	/**
	 * 删除
	 */
	@FXML
	private void onDelBtnAction(ActionEvent event){
		TargetOrgan selectedItem = targetOrganTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem && 
				(null == selectedItem.getDelFlag() || selectedItem.getDelFlag().equals(""))){
			String visceraName = selectedItem.getVisceraName();
			TblAnimalTargetOrgan TblAnimalTargetOrgan = BaseService.getInstance().getTblAnimalTargetOrganService()
					.getLastTargetOrgan(studyNo,visceraName);
			if(null == TblAnimalTargetOrgan || selectedItem.getId().equals(TblAnimalTargetOrgan.getId())){
				if(Sign.openSignWithReasonFrame("删除原因", "")){
					String id = selectedItem.getId();
					String reason = Sign.getReason();
					String realName = SaveUserInfo.getRealName();
					Json json = BaseService.getInstance().getTblAnimalTargetOrganService().delOne(id,reason,realName);
					if(json.isSuccess()){
						updateTable();
					}else{
						showErrorMessage(json.getMsg());
					}
				}
			}else{
				showErrorMessage("同一靶器官请按时间由后到前顺序删除！");
			}
		}
	}
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String animalStrain) {
		this.studyNo = studyNo;
		
		updateStudyMsg();
		
		//2.删除按钮不可用
		delButton.setDisable(true);
		
		//3.更新表格数据
		updateTable();
		
	}
	
	/**更新课题信息
	 * @param animalStrain 
	 * @param sd 
	 * 
	 */
	private void updateStudyMsg() {
		studyNoLabel.setText("");
		String str = "专题编号："+studyNo+"      ";
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		if(null != studyPlan){
			str = str+"SD："+studyPlan.getStudydirector()+"      ";
			str = str+"动物种类："+studyPlan.getAnimalType()+"      ";
		}
//		String pathSd = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(studyNo);
//		if(null != pathSd ){
//			str = str+"病理专题负责人："+pathSd;
//		}
		
		studyNoLabel.setText(str);
	}

	/**
	 * 更新表格数据
	 */
	public void updateTable() {
		data_targetOrganTable.clear();
		
		List<Map<String,Object>> list = BaseService.getInstance().getTblAnimalTargetOrganService().getListByStudyNo(studyNo);
		if(null != list && list.size() > 0){
			for(Map<String, Object> obj:list){
				
				String id = (String) obj.get("id");
				String visceraName = (String) obj.get("visceraName");
				String occurPhase = (String) obj.get("occurPhase");
				String remark = (String) obj.get("remark");
				String register = (String) obj.get("register");
				Date regDate = (Date) obj.get("regDate");
				Integer gender = (Integer) obj.get("gender");
				Integer targetOrganFlag = (Integer) obj.get("targetOrganFlag");
				Integer delFlag = (Integer) obj.get("delFlag");
				String delRsn = (String) obj.get("delRsn");
				Date delTime = (Date) obj.get("delTime");
				
				data_targetOrganTable.add(new TargetOrgan(id,visceraName,occurPhase,remark,register,regDate,
						gender,targetOrganFlag,delFlag,delRsn,delTime));
			}
			
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TargetOrganPage.fxml"));
		Scene scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.setTitle("靶器官");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
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

	public class TargetOrgan{
		
		private StringProperty id;
		private StringProperty visceraName;	   //靶器官名称
		private StringProperty occurPhase;	   //发现阶段
		private StringProperty remark;         //备注
		private StringProperty register;	   //登记者
		private StringProperty regDate;	   //登记日期
		private StringProperty gender;	   //性别相关
		private StringProperty targetOrganFlag;	   //状态（发生/消失）
		private StringProperty delFlag;        //删除标记  
		private StringProperty delRsn;         //删除原因
		private StringProperty delTime;        //删除时间
		
		public TargetOrgan(){
			
		}
		public TargetOrgan(String id,String visceraName,String occurPhase,String remark,
				String register,Date regDate,Integer gender,Integer targetOrganFlag,
				Integer delFlag,String delRsn,Date delTime){
			setId(id);
			setVisceraName(visceraName);
			setOccurPhase(occurPhase);
			setRemark(remark);
			setRegister(register);
			setRegDate(DateUtil.dateToString(regDate, "yyyy-MM-dd"));
			String genderStr = "";
			if(null != gender && gender == 0){
				genderStr = "性别无关";
			}else if(null != gender && gender == 1){
				genderStr = "雄性相关";
			}else if(null != gender && gender == 2){
				genderStr = "雌性相关";
			}
			setGender(genderStr);
			setTargetOrganFlag(targetOrganFlag == 1 ? "发生":"消失");
			setDelFlag(delFlag == 1 ? "删除":"");
			setDelRsn(delRsn);
			setDelTime(DateUtil.dateToString(delTime, "yyyy-MM-dd HH:mm"));
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getOccurPhase() {
			return occurPhase.get();
		}
		public void setOccurPhase(String occurPhase) {
			this.occurPhase = new SimpleStringProperty(occurPhase);
		}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		public String getRegister() {
			return register.get();
		}
		public void setRegister(String register) {
			this.register = new SimpleStringProperty(register);
		}
		public String getRegDate() {
			return regDate.get();
		}
		public void setRegDate(String regDate) {
			this.regDate = new SimpleStringProperty(regDate);
		}
		public String getGender() {
			return gender.get();
		}
		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}
		public String getTargetOrganFlag() {
			return targetOrganFlag.get();
		}
		public void setTargetOrganFlag(String targetOrganFlag) {
			this.targetOrganFlag = new SimpleStringProperty(targetOrganFlag);
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
