package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblAnatomyReq;
import com.lanen.model.path.TblAnatomyReqAnimalList;
import com.lanen.model.path.TblAnatomyReqAttachedViscera;
import com.lanen.model.path.TblAnatomyReqPathPlanCheck;
import com.lanen.model.path.TblAnatomyReqVisceraWeigh;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.MainPageController;
import com.lanen.view.main.SignMeFrame;
import com.lanen.view.test.AnatomyReqPage.AnatomyReq;

public class AnatomyReqView extends Application implements Initializable {
	
	@FXML
	private Button confirmBtn;
	
	@FXML
	private Label studyNoLabel;         //专题编号
	@FXML                               //
	private Label animalTypeLabel;      //动物种类
	@FXML                               //
	private Label testPhaseLabel;       //实验阶段
	@FXML                               //
	private Label sdLabel;          //sd
	@FXML                               //
	private Label pathSDLabel;          //病理状体负责人
	@FXML                               //
	private Label anatomyRsnLabel;      //解剖原因
	@FXML                               //
	private Label anatomyDateLabel;     //计划解剖日期
	@FXML                               //
	private Label createDateLabel;     //申请日期
	@FXML                               //
	private Label animalNumLabel;		//动物数量
	@FXML                               //
	private Label visceraNumLabel;		//脏器数量

	@FXML
	private CheckBox anatomyCheckBox;    //异常脏器和组织剖检
	@FXML                                //
	private CheckBox fixedCheckBox;      //异常脏器和组织固定
	@FXML                                //
	private CheckBox histopathCheckBox;  //异常脏器和组织镜检
	
	@FXML
	private TableView<Animal> animalTable;
	ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Animal,String> animalCodeCol;
	@FXML
	private TableColumn<Animal,String> genderCol;
	
	
	@FXML
	private TableView<AnatomyViscera> anatomyTable;
	ObservableList<AnatomyViscera> data_anatomyTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnatomyViscera,String> visceraNameCol;
	@FXML
	private TableColumn<AnatomyViscera,String> anatomyCol;
	@FXML
	private TableColumn<AnatomyViscera,String> fixedCol;
	@FXML
	private TableColumn<AnatomyViscera,String> histopathCol;
	
	@FXML
	private TableView<WeighViscera> weighTable;
	ObservableList<WeighViscera> data_weighTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<WeighViscera,String> visceraNameCol_1;
	@FXML
	private TableColumn<WeighViscera,String> partCol;
	@FXML
	private TableColumn<WeighViscera,String> fixedWeighCol;
	@FXML
	private TableColumn<WeighViscera,String> attachedVisceraNameCol;
	
	private String studyNo ;
	private Integer reqNo ;
	private String reqId;
	
	private static AnatomyReqView instance;
	public static AnatomyReqView getInstance(){
		if(null == instance){
			instance = new AnatomyReqView();
		}
		return instance;
	}
	
	public AnatomyReqView() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initAnimalTable();
		initAnatomyTable();
		initWeighTable();
		
	}
	
	
	private void initWeighTable() {
		weighTable.setItems(data_weighTable);
		weighTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		visceraNameCol_1.setCellValueFactory(new PropertyValueFactory<WeighViscera,String>("visceraName"));
		partCol.setCellValueFactory(new PropertyValueFactory<WeighViscera,String>("part"));
		fixedWeighCol.setCellValueFactory(new PropertyValueFactory<WeighViscera,String>("fixedWeigh"));
		attachedVisceraNameCol.setCellValueFactory(new PropertyValueFactory<WeighViscera,String>("attachedVisceraName"));
		
		fixedWeighCol.setCellFactory(new Callback<TableColumn<WeighViscera,String>,TableCell<WeighViscera,String>>(){
			
			@Override
			public TableCell<WeighViscera, String> call(
					TableColumn<WeighViscera, String> param) {
				TableCell<WeighViscera, String> cell =
						new TableCell<WeighViscera, String>() {
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
		partCol.setCellFactory(new Callback<TableColumn<WeighViscera,String>,TableCell<WeighViscera,String>>(){
			
			@Override
			public TableCell<WeighViscera, String> call(
					TableColumn<WeighViscera, String> param) {
				TableCell<WeighViscera, String> cell =
						new TableCell<WeighViscera, String>() {
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

	private void initAnatomyTable() {
		anatomyTable.setItems(data_anatomyTable);
		anatomyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyViscera,String>("visceraName"));
		anatomyCol.setCellValueFactory(new PropertyValueFactory<AnatomyViscera,String>("anatomy"));
		fixedCol.setCellValueFactory(new PropertyValueFactory<AnatomyViscera,String>("fixed"));
		histopathCol.setCellValueFactory(new PropertyValueFactory<AnatomyViscera,String>("histopath"));
		
		anatomyCol.setCellFactory(new Callback<TableColumn<AnatomyViscera,String>,TableCell<AnatomyViscera,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<AnatomyViscera, String> call(
	    			 TableColumn<AnatomyViscera, String> param) {
	    		 TableCell<AnatomyViscera, String> cell =
	    				 new TableCell<AnatomyViscera, String>() {
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
		histopathCol.setCellFactory(new Callback<TableColumn<AnatomyViscera,String>,TableCell<AnatomyViscera,String>>(){
			
			@Override
			public TableCell<AnatomyViscera, String> call(
					TableColumn<AnatomyViscera, String> param) {
				TableCell<AnatomyViscera, String> cell =
						new TableCell<AnatomyViscera, String>() {
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
		fixedCol.setCellFactory(new Callback<TableColumn<AnatomyViscera,String>,TableCell<AnatomyViscera,String>>(){
			
			@Override
			public TableCell<AnatomyViscera, String> call(
					TableColumn<AnatomyViscera, String> param) {
				TableCell<AnatomyViscera, String> cell =
						new TableCell<AnatomyViscera, String>() {
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

	private void initAnimalTable() {
		animalTable.setItems(data_animalTable);
		animalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		
		genderCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<Animal, String> call(
	    			 TableColumn<Animal, String> param) {
	    		 TableCell<Animal, String> cell =
	    				 new TableCell<Animal, String>() {
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
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		
		 TblAnatomyTask tblAnatomyTask = new TblAnatomyTask();
		
		String taskCreater = SaveUserInfo.getUserName();
		
		tblAnatomyTask.setTaskCreater(taskCreater);
		
		TblAnatomyReq anatomyReq = BaseService.getInstance().getTblAnatomyReqService().getById(reqId);
		List<String> animalCodeList = new ArrayList<String>();
		
		List<TblAnatomyReqAnimalList> animallist = 
				BaseService.getInstance().getTblAnatomyReqAnimalListService().getListByStudyNoAndReqNo(anatomyReq.getStudyNo(), anatomyReq.getReqNo());
		if(null != animallist && animallist.size() > 0){
			for(TblAnatomyReqAnimalList obj :animallist){
				animalCodeList.add(obj.getAnimalCode());
			}
		}
		//判断动物时否都传出
		Json json = BaseService.getInstance().getTblAnatomyReqService().isComeOut(anatomyReq.getStudyNo(),animalCodeList);
		//都传出 = false
		boolean isAllComeOut = false;
		if(json.isSuccess()){
			isAllComeOut = true;
		}else{
			isAllComeOut = Messager.showSimpleConfirm("提示", json.getMsg());
		}
		if(isAllComeOut){
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("签字确认");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				Map<String,String> map = BaseService.getInstance().getTblAnatomyTaskService().save(tblAnatomyTask,reqId);
				if(map.get("success").equals("true")){
					((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
					AnatomyReqPage.getInstance().updateAnatomyReqTable(2);
					final String taskId=map.get("taskId");
					//刷新主页面上的任务树及任务表格
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							MainPageController.getInstance().updateTaskTreeAndTaskTable();
							final TblAnatomyTask tblAnatomyTask1=BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
							MainPageController.getInstance().selectNewTask(tblAnatomyTask1);
						}
					});
					
					showMessage("解剖任务创建成功！");
				}else{
					showErrorMessage(map.get("msg"));
				}
			}
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 * @param anatomyReq 
	 */
	public void loadData(AnatomyReq anatomyReq) {
		studyNo = anatomyReq.getStudyNo();
		reqNo = Integer.parseInt(anatomyReq.getReqNo());
		reqId = anatomyReq.getId();
		
		//1.更新 n个Label
		updateLabel(anatomyReq);
		//2.加载动物
		updateAnimalTable();
		//3.加载剖检脏器列表
		updateAnatomyVisceraTable();
		//4.加载称重脏器列表
		updateWeighTable();
		//5.更新脏器数量
		updateNumberLabel();

		//6.更新CheckBox
		update3CheckBox();
		
		//7.确定按钮状态
		confirmBtn.setDisable(!"未接受".equals(anatomyReq.getState()));
	}
	
	/**
	 * 更新 三个 CheckBox
	 */
	private void update3CheckBox() {
		TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		if(null != studyPlan){
			anatomyCheckBox.setSelected(studyPlan.getAbnVisceraAnatomyCheck() == 1);
			fixedCheckBox.setSelected(studyPlan.getAbnVisceraFixedFlag() == 1);
			histopathCheckBox.setSelected(studyPlan.getAbnVisceraHistopathCheckFlag() == 1);
		}
		
	}

	/**
	 * 更新需剖检脏器、需固定脏器、需镜检脏器、需称重脏器数量
	 */
	private void updateNumberLabel() {
		int   anatomyNum = 0;
		int   fixedNum = 0;
		int   histopathNum = 0;
		
		for(AnatomyViscera obj:data_anatomyTable){
			if("是" .equals(obj.getAnatomy())){
				anatomyNum++;
				if("是" .equals(obj.getFixed())){
					fixedNum++;
					if("是" .equals(obj.getHistopath())){
						histopathNum++;
					}
				}
			}
			
		}
		
		int weighNum = data_weighTable.size();
		
		
		String text = "";
		text = text+"需剖检脏器："+anatomyNum+" 个";
		text = text+"  需固定脏器："+fixedNum+" 个";
		text = text+"  需镜检脏器："+histopathNum+" 个";
		text = text+"  需称重脏器："+weighNum+" 个";
		
		visceraNumLabel.setText(text);
		
	}

	/**
	 * 加载称重脏器列表
	 */
	private void updateWeighTable() {
		data_weighTable.clear();
		List<TblAnatomyReqVisceraWeigh> list=BaseService.getInstance().
				getTblAnatomyReqVisceraWeighService().getListByStudyAndReqNo(studyNo,reqNo);
		if(null != list){
			for(TblAnatomyReqVisceraWeigh obj:list){
				//根据解剖申请-脏器称重ID，查询对应的附加脏器
				List<TblAnatomyReqAttachedViscera> list3=BaseService.getInstance().
						getTblAnatomyReqAttachedVisceraService().getListByPid(obj.getId());
				String attachedViscera="";
				if(list3!=null&&list3.size()>0){
					for(TblAnatomyReqAttachedViscera aav:list3){
						if(aav!=list3.get(list3.size()-1)){
							//附加脏器有多个名字用顿号隔开
							attachedViscera=attachedViscera+aav.getVisceraName()+"、";
						}else{
							attachedViscera=attachedViscera+aav.getVisceraName();
						}
					}
				}
				data_weighTable.add(new WeighViscera(obj.getVisceraName(),obj.getPartVisceraSeparateWeigh(),obj.getFixedWeighFlag(),attachedViscera));
			}
		} 
	}

	/**
	 * 加载剖检脏器列表
	 */
	private void updateAnatomyVisceraTable() {
		data_anatomyTable.clear();
		List<TblAnatomyReqPathPlanCheck> list=BaseService.getInstance().
				getTblAnatomyReqPathPlanCheckService().getListByStudyNoAndReqNo(studyNo,reqNo);
		if(null != list){
			for(TblAnatomyReqPathPlanCheck obj:list){
				data_anatomyTable.add(new AnatomyViscera(obj.getVisceraName(),
						obj.getAtanomyCheckFlag(),obj.getVisceraFixedFlag(),
						obj.getHistopathCheckFlag()));
			}
		}
		
	}

	/**
	 * 加载动物列表
	 */
	private void updateAnimalTable() {
		data_animalTable.clear();
		List<TblAnatomyReqAnimalList> list=BaseService.getInstance()
				.getTblAnatomyReqAnimalListService().getListByStudyNoAndReqNo(studyNo,reqNo);
		if(null != list){
			for(TblAnatomyReqAnimalList obj:list){
				data_animalTable.add(new Animal(obj.getAnimalCode(),obj.getGender()));
			}
		}
		
	}

	/**
	 * 更新 n个Label
	 */
	private void updateLabel(AnatomyReq anatomyReq) {
		
		String sd = BaseService.getInstance().getTblStudyPlanService().getSDByStudyNo(studyNo);
		studyNoLabel.setText(anatomyReq.getStudyNo());
		sdLabel.setText(sd);
		
		animalTypeLabel.setText(anatomyReq.getAnimalType());
		
		TblAnatomyReq tblAnatomyReq = BaseService.getInstance().getTblAnatomyReqService().getById(anatomyReq.getId());
		if(null != tblAnatomyReq){
			testPhaseLabel.setText(tblAnatomyReq.getTestPhase());
			int anatomyRsn = tblAnatomyReq.getAnatomyRsn();
			if(anatomyRsn == 1){
				anatomyRsnLabel.setText("计划解剖");
			}else if(anatomyRsn == 2){
				anatomyRsnLabel.setText("濒死解剖");
			}else  if(anatomyRsn == 3){
				anatomyRsnLabel.setText("死亡解剖");
			}else  if(anatomyRsn == 4){
				anatomyRsnLabel.setText("安乐死解剖");
			}
		}else{
			testPhaseLabel.setText("");
			anatomyRsnLabel.setText("");
		}
		String pathSd = BaseService.getInstance().getTblStudyPlanService().getPathSDByStudyNo(studyNo);
		pathSDLabel.setText(pathSd);
		//申请日期
		createDateLabel.setText(anatomyReq.getSubmitDate());
		
		anatomyDateLabel.setText(anatomyReq.getBenginDate());
		if(anatomyReq.getBenginDate().equals(anatomyReq.getEndDate())){
			anatomyDateLabel.setText(anatomyReq.getBenginDate());
		}else{
			anatomyDateLabel.setText(anatomyReq.getBenginDate()+" ~ "+anatomyReq.getEndDate());
		}
		
		animalNumLabel.setText("动物数量："+anatomyReq.getAnimalNum()+" 只");
		
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AnatomyReqView.fxml"));
		Scene scene = new Scene(root, 1100, 650);
		stage.setScene(scene);
		
		stage.setMinWidth(800);
		stage.setMinHeight(500);
		stage.setResizable(false);
		stage.setTitle("创建解剖任务");
		
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	
	/**动物
	 * @author Administrator
	 *
	 */
	public class Animal{
		private StringProperty animalCode;
		private StringProperty gender;
		
		public Animal(){
		}
		public Animal(String animalCode,Integer gender){
			setAnimalCode(animalCode);
			setGender(gender == 1 ? "♂":"♀");
		}
		
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getGender() {
			return gender.get();
		}
		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}
	}
	
	/**解剖/固定/镜检内容
	 * @author Administrator
	 *
	 */
	public class AnatomyViscera{
		private StringProperty visceraName;
		private StringProperty anatomy;
		private StringProperty fixed;
		private StringProperty histopath;
		
		public AnatomyViscera(){
			
		}
		public AnatomyViscera(String visceraName,Integer anatomy ,Integer fixed,Integer histopath){
			setVisceraName(visceraName);
			setAnatomy(anatomy == 1 ? "是":"否");
			setFixed(fixed == 1 ? "是":"否");
			setHistopath(histopath == 1 ? "是":"否");
		}
		
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getAnatomy() {
			return anatomy.get();
		}
		public void setAnatomy(String anatomy) {
			this.anatomy = new SimpleStringProperty(anatomy);
		}
		public String getFixed() {
			return fixed.get();
		}
		public void setFixed(String fixed) {
			this.fixed = new SimpleStringProperty(fixed);
		}
		public String getHistopath() {
			return histopath.get();
		}
		public void setHistopath(String histopath) {
			this.histopath = new SimpleStringProperty(histopath);
		}
		
	}
	/**称重脏器内容
	 * @author Administrator
	 *
	 */
	public class WeighViscera{
		private StringProperty visceraName;
		private StringProperty part;	//成对脏器分开称重
		private StringProperty fixedWeigh;
		private StringProperty attachedVisceraName;
		
		public WeighViscera(){
			
		}
		public WeighViscera(String visceraName,Integer part ,Integer fixedWeigh,String attachedVisceraName){
			setVisceraName(visceraName);
			setPart(part == 1 ? "是":"否");
			setFixedWeigh(fixedWeigh == 1 ? "是":"否");
			setAttachedVisceraName(attachedVisceraName);
		}
		
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getPart() {
			return part.get();
		}
		public void setPart(String part) {
			this.part =  new SimpleStringProperty(part);
		}
		public String getFixedWeigh() {
			return fixedWeigh.get();
		}
		public void setFixedWeigh(String fixedWeigh) {
			this.fixedWeigh =  new SimpleStringProperty(fixedWeigh);
		}
		public String getAttachedVisceraName() {
			return attachedVisceraName.get();
		}
		public void setAttachedVisceraName(String attachedVisceraName) {
			this.attachedVisceraName =  new SimpleStringProperty(attachedVisceraName);
		}
		
	}

}
