package com.lanen.view.test;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblDeadAnimalDeathReason;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**致死原因登记
 * @author Administrator
 *
 */
public class DeathReasonRegister extends Application implements Initializable {
	
	@FXML
	private Label animalCodeLabel;
	
	@FXML
	private RadioButton dictRadioButton;
	@FXML
	private RadioButton histopathRadioButton;
	
	@FXML
	private ComboBox<String> deathReasonComboBox;
	ObservableList<String> data_deathReasonComboBox = FXCollections.observableArrayList();
	/**
	 * 索引——>镜检id
	 */
	Map<Integer,String> histopathCheckIdMap = new HashMap<Integer,String>(); 
	
	@FXML
	private TextArea remarkText;

	private String studyNo = "";
	private String animalCode = "";
	private String anatomyRsn = "";
	private String animalStrain = "";
	//anatomyRsn,animalStrain
	private static DeathReasonRegister instance;
	public static DeathReasonRegister getInstance(){
		if(null == instance){
			instance = new DeathReasonRegister();
		}
		return instance;
	}
	
	public DeathReasonRegister() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initDeathReasonComboBox();
		initRadioButton();
	}
	
	/**
	 * 初始化 RadioButton
	 */
	private void initRadioButton() {
		dictRadioButton.getToggleGroup().selectedToggleProperty()
			.addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
					if(null != newValue){
						if(dictRadioButton.isSelected()){
							updateDeathReasonComboBox(1);
						}else{
							updateDeathReasonComboBox(2);
						}
					}
				}
			});
	}

	/**
	 * @param flag 1:字典    2：镜检
	 */
	private void updateDeathReasonComboBox(int flag){
		
		data_deathReasonComboBox.clear();
		if(flag == 1){
			//来自字典
			List<DictPathCommon> list = BaseService.getInstance().getDictPathCommonService().getListByDictType(17, 1);
			if(null != list && list.size() > 0){
				for(DictPathCommon obj:list){
					data_deathReasonComboBox.add(obj.getDescCn());
				}
			}
		}else{
			histopathCheckIdMap.clear();
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblHistopathCheckService().getMapListForDeathReason(studyNo,animalCode);
			if(null != mapList && mapList.size() > 0){
				int i = 0;
				for(Map<String, Object> obj : mapList){
					String id = (String) obj.get("id");
					String lesionFinding = (String) obj.get("lesionFinding");
					data_deathReasonComboBox.add(lesionFinding);
					histopathCheckIdMap.put(i, id);
					i++;
				}
			}
		}
	}
	/**
	 * 初始哈 致死原因ComboBox
	 */
	private void initDeathReasonComboBox() {
		deathReasonComboBox.setItems(data_deathReasonComboBox);
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		
		int deathReasonType = 1;
		if(histopathRadioButton.isSelected()){
			deathReasonType = 2;
		}
		remarkText.requestFocus();
		String deathReason = deathReasonComboBox.getSelectionModel().getSelectedItem();
		if(null == deathReason){
			showErrorMessage("请选择或录入致死原因！");
			deathReasonComboBox.requestFocus();
			return;
		}
		String histopathCheckId = null;
		if(deathReasonType == 2){
			histopathCheckId = histopathCheckIdMap.get(deathReasonComboBox.getSelectionModel().getSelectedIndex());
		}
		
		String remark = remarkText.getText();
		if(null != remark && remark.getBytes().length > 200){
			showErrorMessage("备注内容长度不能大于200字节！");
			remarkText.requestFocus();
			return;
		}
		
		TblDeadAnimalDeathReason tblDeadAnimalDeathReason = new TblDeadAnimalDeathReason();
		
		tblDeadAnimalDeathReason.setStudyNo(studyNo);
		tblDeadAnimalDeathReason.setAnimalCode(animalCode);
		tblDeadAnimalDeathReason.setDeathReasonType(deathReasonType);
		tblDeadAnimalDeathReason.setDeathReason(deathReason);
		tblDeadAnimalDeathReason.setHistopathCheckId(histopathCheckId);
		tblDeadAnimalDeathReason.setRemark(remark);
		
		//签字窗口
		SignMeFrame signMeFrame = new SignMeFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("致死原因登记");
		try {
			signMeFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//签字通过
		if("OK".equals(SignMeFrame.getType())){
			String operator = SaveUserInfo.getRealName();
			
			BaseService.getInstance().getTblDeadAnimalDeathReasonService().addOne(tblDeadAnimalDeathReason,operator);
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			
			Stage stage2 = Main.stageMap.get("DeathReasonPage");
			if(null == stage2){
				stage2 =new Stage();
				stage2.initOwner(Main.mainWidow);
				stage2.initModality(Modality.APPLICATION_MODAL);
				try {
					DeathReasonPage.getInstance().start(stage2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.stageMap.put("DeathReasonPage",stage2);
				DeathReasonPage.getInstance().loadData(studyNo,animalCode,anatomyRsn,animalStrain);
			}else{
				if(stage2.isShowing()){
					DeathReasonPage.getInstance().updateTable();
				}else{
					stage2.show();
					DeathReasonPage.getInstance().loadData(studyNo,animalCode,anatomyRsn,animalStrain);
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
	 */
	public void loadData(String studyNo,String animalCode,String anatomyRsn,String animalStrain) {
		this.studyNo = studyNo;
		this.animalCode = animalCode;
		this.anatomyRsn = anatomyRsn;
		this.animalStrain = animalStrain;
		
		//1.
		animalCodeLabel.setText(animalCode);
		
		//2.
		dictRadioButton.setSelected(true);
		
		//3.
		deathReasonComboBox.getSelectionModel().clearSelection();
		
		//4.
		remarkText.clear();
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DeathReasonRegister.fxml"));
		Scene scene = new Scene(root, 489, 312);
		stage.setScene(scene);
		stage.setTitle("致死原因登记");
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

}
