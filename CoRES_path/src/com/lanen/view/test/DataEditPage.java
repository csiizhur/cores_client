package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.path.TblPathSession;
import com.lanen.model.path.VisceraFixDataForReport;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

/**数据修改
 * @author Administrator
 *
 */
public class DataEditPage  extends Application implements Initializable  {

	@FXML
	private Label studyNoLabel;			//专题编号   Label
	@FXML
	private Label animalTypeLabel;		//动物种类Label
	@FXML
	private Label animalNumLabel;		//动物数量Label
	@FXML
	private Label anatomyRsnLabel;		//解剖原因Label
	@FXML
	private Label taskCreaterLabel;		//任务创建者Label
	@FXML
	private Label taskCreateTimeLabel;	//任务创建日期Label
	
	@FXML
	private Button addButton; //添加按钮
	
	@FXML
	private Button editButton; //编辑按钮
	
	@FXML
	private Button deleteButton; //删除按钮
	@FXML
	private Button printButton; //删除按钮
	
	@FXML
	private TableView<AnatomyCheckEdit> anatomyCheckTable; //解剖所见
	private ObservableList<AnatomyCheckEdit> data_anatomyCheckTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<AnatomyCheckEdit,String> studyNoCol;  		//专题编号
	@FXML
	private TableColumn<AnatomyCheckEdit,String> animalCodeCol;		//动物编号
	@FXML
	private TableColumn<AnatomyCheckEdit,String> visceraNameCol;	//脏器名称
	@FXML
	private TableColumn<AnatomyCheckEdit,String> anatomyFindingCol;	//解剖所见
	
	@FXML
	private TableColumn<AnatomyCheckEdit,String> anatomyOperatorCol;//解剖者
	@FXML
	private TableColumn<AnatomyCheckEdit,String> operateTimeCol;	//记录时间
	@FXML
	private TableColumn<AnatomyCheckEdit,String> editTypeCol;//操作类型
	@FXML
	private TableColumn<AnatomyCheckEdit,String> editTimeCol;	//操作时间
	@FXML
	private TableColumn<AnatomyCheckEdit,String> editRsnCol;	//操作原因
	
	
	private String taskId;//解剖任务Id
	
	private static DataEditPage instance;
	public static DataEditPage getInstance(){
		if(null == instance){
			instance = new DataEditPage();
		}
		return instance;
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initAnatomyCheckTable();
	}
	
	/**
	 * 加载数据（创建会话）
	 * @param taskIdList 
	 */
	public void loadData(String taskId) {
		editButton.setDisable(true);
		deleteButton.setDisable(true);
		
		this.taskId = taskId;
		//更新任务信息
		updateData();
		data_anatomyCheckTable.clear();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				//更新解剖所见信息
				updateAnatomyCheckTable();
			}
		});
		
	}
	
	/**更新任务信息
	 * @param anatomyReq
	 */
	public void updateData() {
		Map<String,Object> map = BaseService.getInstance().getTblAnatomyTaskService().getMap(taskId);
		
		String studyNo = "";
		String sd = "";
		String animalType = "";
		Integer animalNum = 0;
		String taskCreater = "";
		String taskDateStr = "";
		String anatomyRsn = "";
		if(null != map){
			studyNo = (String) map.get("studyNo");
			sd = (String) map.get("sd");
			animalType = (String) map.get("animalType");
			animalNum = (Integer) map.get("animalNum");
			anatomyRsn = (String) map.get("anatomyRsn");
			taskCreater = (String) map.get("taskCreater");
			taskDateStr = (String) map.get("taskDateStr");
		}
		
		studyNoLabel.setText(studyNo+" （"+sd+"）");
		animalTypeLabel.setText(animalType);
		animalNumLabel.setText(animalNum+"");
		anatomyRsnLabel.setText(anatomyRsn);
		taskCreaterLabel.setText(taskCreater);
		taskCreateTimeLabel.setText(taskDateStr);
			
	}
	
	/**
	 * 初始化解剖所见表格
	 */
	private void initAnatomyCheckTable(){
		anatomyCheckTable.setItems(data_anatomyCheckTable);
//		anatomyCheckTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		studyNoCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("studyNo"));//专题编号
	    animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("animalCode"));//动物编号
        visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("visceraName"));;//脏器名称
		anatomyFindingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("anatomyFinding"));;//解剖所见
		anatomyOperatorCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("anatomyOperator"));;//解剖者
		operateTimeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("operateTime"));;//记录时间
		editTypeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("editType"));;//操作类型
		editTimeCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("editTime"));;//操作时间
		editRsnCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheckEdit,String>("editRsn"));;//操作时间
		anatomyCheckTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<AnatomyCheckEdit>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyCheckEdit> arg0, AnatomyCheckEdit arg1,
					AnatomyCheckEdit newValue) {
							if(null != newValue){
								if("自溶".equals(newValue.getAnatomyFinding().trim())){
									editButton.setDisable(true);
									deleteButton.setDisable(true);
								}else if(null != newValue.getId() && !"".equals(newValue.getId())){
									editButton.setDisable(true);
									deleteButton.setDisable(false);
								}else{
									editButton.setDisable(false);
									deleteButton.setDisable(false);
								}
							}else{
								editButton.setDisable(true);
								deleteButton.setDisable(true);
							}
				}

		});
		
		operateTimeCol.setCellFactory(new Callback<TableColumn<AnatomyCheckEdit,String>,TableCell<AnatomyCheckEdit,String>>(){
	    	 @Override
	    	 public TableCell<AnatomyCheckEdit, String> call(
	    			 TableColumn<AnatomyCheckEdit, String> param) {
	    		 TableCell<AnatomyCheckEdit, String> cell =
	    				 new TableCell<AnatomyCheckEdit, String>() {
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
		editTimeCol.setCellFactory(new Callback<TableColumn<AnatomyCheckEdit,String>,TableCell<AnatomyCheckEdit,String>>(){
			@Override
			public TableCell<AnatomyCheckEdit, String> call(
					TableColumn<AnatomyCheckEdit, String> param) {
				TableCell<AnatomyCheckEdit, String> cell =
						new TableCell<AnatomyCheckEdit, String>() {
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
	
	/**更新解剖所见表格数据
	 * @param taskIdList2
	 */
	public void updateAnatomyCheckTable() {
		data_anatomyCheckTable.clear();
		List<Map<String,Object>> taskMapList = 
				BaseService.getInstance().getTblAnatomyCheckEditService().getMapListByTaskId(taskId);
			for(Map<String,Object> map:taskMapList){
				String oldId = (String) map.get("oldId");//原Id
				String studyNo = (String) map.get("studyNo");//专题编号
				String animalCode = (String) map.get("animalCode");//动物编号
				String  visceraName = (String) map.get("visceraName");//脏器名称
				String subVisceraName = (String) map.get("subVisceraName");//子脏器名称
				String  anatomyFinding = (String) map.get("anatomyFinding");//解剖所见
//				Integer  autolyzaFlag = (Integer) map.get("autolyzaFlag");//自溶标识
				String  operateTime = (String) map.get("operateTime");//操作时间
				String anatomyOperator = (String) map.get("anatomyOperator") ;//解剖者
				
				Integer editType = (Integer) map.get("editType") ;//操作类型
				String editTime = (String) map.get("editTime") ;//操作时间
				String editRsn = (String) map.get("editRsn") ;//操作原因
//				Integer delFlag = (Integer) map.get("delFlag") ;//删除标识
//				String delTime = (String) map.get("delTime") ;//删除时间
//				String delRsn = (String) map.get("delRsn") ;//删除原因
				String id = (String) map.get("id") ;//
				
				if( null != subVisceraName && !subVisceraName.equals("") ){
					visceraName = subVisceraName;
				}

				data_anatomyCheckTable.add(new AnatomyCheckEdit(oldId,studyNo,animalCode,visceraName,anatomyFinding,anatomyOperator,operateTime,editType,editTime,editRsn,id));
			}
		
	}
	
	/**选中指定行
	 * @param id
	 */
	public void selectRowById(String id) {
		if(null != id && !"".equals(id)){
			int i = 0;
			for(AnatomyCheckEdit obj:data_anatomyCheckTable){
				if(null != obj.getId() && id.equals(obj.getId())){
					anatomyCheckTable.getSelectionModel().select(obj);
					anatomyCheckTable.scrollTo(i);
					break;
				}
				i++;
			}
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DataEdit.fxml"));
		Scene scene = new Scene(root, 960, 600);
		stage.setScene(scene);
		stage.setTitle("解剖数据修改");
		stage.setResizable(false);
//		Screen screen = Screen.getPrimary();
//		Rectangle2D bounds = screen.getVisualBounds();
//		stage.setX((bounds.getMaxX() - 960D) / 2);
//		stage.setY((bounds.getMaxY() -550D) / 2);
//		stage.setWidth(950);
//		stage.setHeight(650);
//		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//
//			@Override
//			public void handle(WindowEvent event) {
//					//event.consume();
//			}
//		});
		stage.show();
		
	}
	


	/**
	 * 解剖所见添加
	 */
	@FXML
	private void onaddBtnAction(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("DataEdit_AddEdit");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				DataEdit_AddEdit.getInstance().start(stage);
				Main.stageMap.put("DataEdit_AddEdit",stage);
			}else{ 
				stage.show();
			}
			DataEdit_AddEdit.getInstance().loadData(null,"add",taskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑
	 * @param event
	 */
	@FXML
	private void onEditBtnAction(ActionEvent event){
		AnatomyCheckEdit selectedItem = anatomyCheckTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			String oldId = selectedItem.getOldId();
			try {
				Stage stage = Main.stageMap.get("DataEdit_AddEdit");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					DataEdit_AddEdit.getInstance().start(stage);
					Main.stageMap.put("DataEdit_AddEdit",stage);
				}else{
					stage.show();
				}
				DataEdit_AddEdit.getInstance().loadData(oldId,"edit",taskId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 打印
	 * @param event
	 * @throws JRException 
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event) throws JRException{
		JasperReport jr = null;
		JasperPrint jp = null;
		jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(
				"PrintAnatomyCheckEdit.jrxml"));
		Map<String, Object> map = new HashMap<String, Object>();
		URL url = this.getClass().getResource("/image/logo.jpg");
		


		String number = "";
		number = BaseService.getInstance().getDictReportNumberService()
				.getNumberByReportName("动物剖检修改记录");
		map.put("number", number == null ? "" : number);
		;// 编号
		map.put("logoImage", url);
		map.put("reportName", "动物剖检修改记录");
		
		 Map<String,Object> taskMap = BaseService.getInstance().getTblAnatomyTaskService().getMap(taskId);
		String studyNo = (String) taskMap.get("studyNo");
		String animalType = (String) taskMap.get("animalType");
		String animalStrain = (String) taskMap.get("animalStrain");
		String anatomyRsn = (String) taskMap.get("anatomyRsn");
		if(null != animalStrain || !"".equals(animalStrain)){
        	if(!animalStrain.contains(animalType)){
        		animalType = animalType +" "+animalStrain;
        	}else{
        		animalType = animalStrain;
        	}
		}
		map.put("animalType", animalType);
		map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
		map.put("anatomyRsn",anatomyRsn);
		
		List<Map<String, Object>> maplist = BaseService.getInstance().getTblAnatomyCheckEditService()
				.getAnatomyCheckEditPrint(taskId);
		if(null != maplist && maplist.size() > 0){
		   for(Map<String,Object> obj:maplist){
			   String visceraName = (String) obj.get("visceraName");
			   if(null == visceraName || "".equals(visceraName)){
				   obj.put("visceraName", "-");
			   }
			   String anatomyCheckResult = (String) obj.get("anatomyCheckResult");
			   if(null == anatomyCheckResult || "".equals(anatomyCheckResult.trim())){
				   obj.put("anatomyCheckResult", "-");
			   }
			   String anatomyCheckEditResult = (String) obj.get("anatomyCheckEditResult");
			   if(null == anatomyCheckEditResult || "".equals(anatomyCheckEditResult.trim())){
				   obj.put("anatomyCheckEditResult", "-");
			   }
		   }
		}
		jp = JasperFillManager.fillReport(jr, map, new ClassBeanPathDataSource(maplist));
		Main.getInstance().openJFrame(jp, "动物剖检修改记录");
	}
	
	/**
	 * 删除
	 * @param event
	 */
	@FXML
	private void delectBtnAction(ActionEvent event){
		
		AnatomyCheckEdit selectedItem = anatomyCheckTable.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			//签字通过
			if(Sign.openSignWithReasonFrame("删除原因", "")){
				
				String id = selectedItem.getId();
				String oldId = selectedItem.getOldId();
				String currentRealName = SaveUserInfo.getRealName();
				String reason = Sign.getReason();
				BaseService.getInstance().getTblAnatomyCheckEditService().deleteOne(id,oldId,currentRealName,reason,taskId);
				updateAnatomyCheckTable();
				showMessage("删除成功！");
			}
		}
		

        	
        
			
	}

	
	/**
	 * 解剖所见编辑记录
	 * @author Administrator
	 *
	 */
	public class AnatomyCheckEdit{
		private StringProperty oldId;//原主键
		private StringProperty studyNo;//专题编号
		private StringProperty animalCode;//动物编号
		private StringProperty visceraName;//脏器名称
		private StringProperty anatomyFinding;//解剖所见
		private StringProperty anatomyOperator;	//解剖者
//		private StringProperty operator;//操作者
		private StringProperty operateTime;//记录时间
		
		private StringProperty editType;	//操作类型
		private StringProperty editTime;	//操作时间
		private StringProperty editRsn;	//操作时间
		private StringProperty id;			//数据修改表的id
		
		
		public AnatomyCheckEdit(String oldId,String studyNo,String animalCode, String visceraName, String anatomyFinding,
				String anatomyOperator, String operateTime,int editType,String editTime,String editRsn,String id) {
			setOldId(oldId);
			setStudyNo(studyNo);
			this.animalCode = new SimpleStringProperty(animalCode);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.anatomyFinding = new SimpleStringProperty(anatomyFinding);
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
			this.operateTime = new SimpleStringProperty(operateTime);
			switch (editType) {
			case 0:
				setEditType("");
				break;
			case 1:
				setEditType("新增");
				break;
			case 2:
				setEditType("编辑");
				break;
			case 3:
				setEditType("删除");
				break;

			default:
				break;
			}
			setEditTime(editTime);
			this.editRsn = new SimpleStringProperty(editRsn);
			this.id = new SimpleStringProperty(id);
		}
		

		public AnatomyCheckEdit() {
			super();
		}
		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getAnimalCode() {
			return animalCode.get();
		}

		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}

		public String getVisceraName() {
			return visceraName.get();
		}

		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}

		public String getAnatomyFinding() {
			return anatomyFinding.get();
		}

		public void setAnatomyFinding(String anatomyFinding) {
			this.anatomyFinding = new SimpleStringProperty(anatomyFinding);
		}

		public String getOperateTime() {
			return operateTime.get();
		}

		public void setOperateTime(String operateTime) {
			this.operateTime = new SimpleStringProperty(operateTime);
		}

		public String getAnatomyOperator() {
			return anatomyOperator.get();
		}

		public void setAnatomyOperator(String anatomyOperator) {
			this.anatomyOperator = new SimpleStringProperty(anatomyOperator);
		}


		public String getOldId() {
			return oldId.get();
		}


		public void setOldId(String oldId) {
			this.oldId = new SimpleStringProperty(oldId);
		}


		public String getEditType() {
			return editType.get();
		}


		public void setEditType(String editType) {
			this.editType = new SimpleStringProperty(editType);
		}


		public String getEditTime() {
			return editTime.get();
		}


		public void setEditTime(String editTime) {
			this.editTime = new SimpleStringProperty(editTime);
		}


		public String getStudyNo() {
			return studyNo.get();
		}


		public void setStudyNo(String studyNo) {
			this.studyNo = new SimpleStringProperty(studyNo);
		}


		public String getEditRsn() {
			return editRsn.get();
		}


		public void setEditRsn(String editRsn) {
			this.editRsn = new SimpleStringProperty(editRsn);
		}

	
	}
	
	
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
}
