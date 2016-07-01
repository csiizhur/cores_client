package com.lanen.view.test;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.messager.Messager;

/**源发脏器肿瘤
 * @author Administrator
 *
 */
public class ReferFinding extends Application implements Initializable {
	@FXML
	private TableColumn<Refer,String> animalCodeCol;
	@FXML
	private TableColumn<Refer,String> visceraOrTissueNameCol;
	@FXML
	private TableColumn<Refer,String> checkResultCol;
	@FXML
	private TableColumn<Refer,String> tumorFlagCol;
	@FXML
	private TableColumn<Refer,String> metastasisFlagCol;
	
	@FXML
	private TableView<Refer> table;
	ObservableList<Refer> data_table = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> tumorNumComboBox;		//肿瘤数量
	
	private String studyNo = null;
	private String animalCode = null;
	private String visceraCode = null;
	
	//选择数据后放到哪里
	private String toWhere = null;
	
	private static ReferFinding instance;
	public static ReferFinding getInstance(){
		if(null == instance){
			instance = new ReferFinding();
		}
		return instance;
	}
	
	public ReferFinding() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTable();
		
	}
	
	private void initTable() {
		table.setItems(data_table);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Refer,String>("animalCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<Refer,String>("visceraOrTissueName"));
		tumorFlagCol.setCellValueFactory(new PropertyValueFactory<Refer,String>("tumorFlag"));
		metastasisFlagCol.setCellValueFactory(new PropertyValueFactory<Refer,String>("metastasisFlag"));
		checkResultCol.setCellValueFactory(new PropertyValueFactory<Refer,String>("checkResult"));
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Refer>(){

			@Override
			public void changed(ObservableValue<? extends Refer> arg0, Refer arg1, Refer newValue) {
				if(null != newValue && !newValue.getTumorFlag().equals("非肿瘤")){
					tumorNumComboBox.setDisable(false);
					tumorNumComboBox.getSelectionModel().select("1");
				}else{
					tumorNumComboBox.setDisable(true);
					tumorNumComboBox.getSelectionModel().select("0");
				}
				
			}});
		
		metastasisFlagCol.setCellFactory(new Callback<TableColumn<Refer,String>,TableCell<Refer,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<Refer, String> call(
	    			 TableColumn<Refer, String> param) {
	    		 TableCell<Refer, String> cell =
	    				 new TableCell<Refer, String>() {
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
		Refer selectedItem = table.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			int tumorNum = 0;
			if(!selectedItem.getTumorFlag().equals("非肿瘤")){
				String tumorNumberStr = tumorNumComboBox.getSelectionModel().getSelectedItem();
				if(!NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
					showErrorMessage("请选择或录入正确的肿瘤数量！");
					tumorNumComboBox.requestFocus();
					return ;
				}
				tumorNum = Integer.parseInt(tumorNumberStr);
			}
	
			
			if("HistopathCheckPage".equals(toWhere)){
				HistopathCheckPage.getInstance().AddReferItem(selectedItem.getId(),tumorNum);
			}else if("HistopathCheckEdit".equals(toWhere)){
				HistopathCheckEdit.getInstance().AddReferItem(selectedItem.getId(),tumorNum);
			}
			
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}else{
			showErrorMessage("请先选择数据！");
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
	 */
	public void loadData(String studyNo,String animalCode,String visceraCode,String toWhere) {
		this.studyNo  = studyNo;
		this.animalCode  = animalCode;
		this.visceraCode  = visceraCode;
		this.toWhere  = toWhere;
		
		tumorNumComboBox.getSelectionModel().select("0");
		tumorNumComboBox.setDisable(true);
		
		updateTaskTable();
		
		
	}

	
	private void updateTaskTable() {
		
		data_table.clear();
		List<Map<String,Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getReferMapListByStudyNoAnimalCodeVisceraCode(studyNo,animalCode,visceraCode);
		if(null != maplist && maplist.size() > 0){
			for(Map<String, Object> obj:maplist){
				String id = (String) obj.get("id");
				String animalCode = (String) obj.get("animalCode");
				String visceraOrTissueName = (String) obj.get("visceraOrTissueName");
				String histoPos = (String) obj.get("histoPos");
				String lesionFinding = (String) obj.get("lesionFinding");
				Integer tumorFlag = (Integer) obj.get("tumorFlag");
				Integer metastasisFlag = (Integer) obj.get("metastasisFlag");
				data_table.add(new Refer(id,animalCode,visceraOrTissueName,tumorFlag,metastasisFlag,histoPos,lesionFinding));
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("ReferFinding.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("参照所见");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
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
	
	/**参照所见
	 * @author Administrator
	 *
	 */
	public class Refer{
		
		private StringProperty id;
		private StringProperty animalCode;
		private StringProperty visceraOrTissueName;
		private StringProperty tumorFlag;
		private StringProperty metastasisFlag;
		private StringProperty checkResult;
		
		public Refer(String id,String animalCode,String visceraOrTissueName,Integer tumorFlag,Integer metastasisFlag,String histoPos,String lesionFinding){
			setId(id);
			setVisceraOrTissueName(visceraOrTissueName);
			setAnimalCode(animalCode);
			if(tumorFlag == 0){
				setTumorFlag("非肿瘤");
				setMetastasisFlag("");
			}else if(tumorFlag == 1){
				setTumorFlag("良性肿瘤");
				setMetastasisFlag("");
			}else if(tumorFlag == 2){
				setTumorFlag("恶性肿瘤");
				if(metastasisFlag == 0){
					setMetastasisFlag("否");
				}else{
					setMetastasisFlag("");
				}
			}
			String checkResult = "";
			if(null != histoPos && !"".equals(histoPos)){
				checkResult = histoPos+" "+lesionFinding;
			}else{
				checkResult = lesionFinding;
			}
			setCheckResult(checkResult);
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
		public String getTumorFlag() {
			return tumorFlag.get();
		}
		public void setTumorFlag(String tumorFlag) {
			this.tumorFlag = new SimpleStringProperty(tumorFlag);
		}
		public String getMetastasisFlag() {
			return metastasisFlag.get();
		}
		public void setMetastasisFlag(String metastasisFlag) {
			this.metastasisFlag = new SimpleStringProperty(metastasisFlag);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}

		public String getCheckResult() {
			return checkResult.get();
		}

		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}
		
		
		
	}

}
