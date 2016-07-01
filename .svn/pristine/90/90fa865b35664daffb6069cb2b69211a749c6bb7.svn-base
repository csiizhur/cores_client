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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblAnimalTargetOrgan;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.SignMeFrame;

/**致死原因登记
 * @author Administrator
 *
 */
public class TargetOrganRegister extends Application implements Initializable {
	
	@FXML
	private RadioButton targetOrganFlagRB1;//发生
	@FXML
	private RadioButton targetOrganFlagRB2;//消失
	
	@FXML
	private RadioButton genderRB0;//性别无关
	@FXML
	private RadioButton genderRB1;//雄
	@FXML
	private RadioButton genderRB2;//雌
	
	@FXML
	private ComboBox<String> visceraNameComboBox;
	ObservableList<String> data_visceraNameComboBox = FXCollections.observableArrayList();
	private Map<String,Map<String,Object>> visceraNameMapMap = new HashMap<String,Map<String,Object>>();
	
	@FXML
	private TextField occurPhaseText;
	@FXML
	private TextArea remarkText;

	private String studyNo = "";
//	private String taskId = "";
	
	private static TargetOrganRegister instance;
	public static TargetOrganRegister getInstance(){
		if(null == instance){
			instance = new TargetOrganRegister();
		}
		return instance;
	}
	
	public TargetOrganRegister() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTargetOrganFlagRB();
		initVisceraNameComboBox();
		initTargetOrganFlagRB();
	}
	
	/**
	 * 靶器官状态
	 */
	private void initTargetOrganFlagRB() {
		targetOrganFlagRB1.getToggleGroup().selectedToggleProperty()
		.addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(null != newValue && targetOrganFlagRB1.isSelected()){
					updateGenderRBState(true);
				}else{
					//更新 性别相关RadioButton 状态
					updateGenderRBState(false);
				}
				//更新脏器名称ComboBox 
				updateVisceraNameComboBox();
			}
		});
	}
	/**更新 性别相关RadioButton 状态
	 * @param flag
	 */
	private void updateGenderRBState(boolean flag){
		genderRB0.setSelected(false);
		genderRB1.setSelected(false);
		genderRB2.setSelected(false);
		
		genderRB0.setDisable(!flag);
		genderRB1.setDisable(!flag);
		genderRB2.setDisable(!flag);
	}
	

	/**
	 * 更新脏器名称ComboBox 
	 */
	private void updateVisceraNameComboBox(){
		//TODO 
		data_visceraNameComboBox.clear();
		visceraNameMapMap.clear();
		
		if(targetOrganFlagRB1.isSelected()){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnimalTargetOrganService().getfsMapListByStudyNo(studyNo);
			if(null != mapList && mapList.size() > 0){
				for(Map<String,Object> map:mapList){
//					String visceraCode = (String) map.get("visceraCode");
//					Integer visceraType = (Integer) map.get("visceraType");
					String visceraName = (String) map.get("visceraName");
					data_visceraNameComboBox.add(visceraName);
					visceraNameMapMap.put(visceraName, map);
				}
			}
		}else if(targetOrganFlagRB2.isSelected()){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnimalTargetOrganService().getMapListByStudyNo(studyNo);
			if(null != mapList && mapList.size() > 0){
				for(Map<String,Object> map:mapList){
					String visceraName = (String) map.get("visceraName");     
					data_visceraNameComboBox.add(visceraName);
					visceraNameMapMap.put(visceraName, map);
				}
			}
		}
	}
	/**
	 * 初始化 脏器名称ComboBox
	 */
	private void initVisceraNameComboBox() {
		visceraNameComboBox.setItems(data_visceraNameComboBox);
		visceraNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					if(targetOrganFlagRB2.isSelected()){
						int gender = BaseService.getInstance().getTblAnimalTargetOrganService()
								.getGenderByStudyNoVisceraName(studyNo,newValue);
						if(gender == 0){
							genderRB0.setSelected(true);
						}else if(gender == 1){
							genderRB1.setSelected(true);
						}else if(gender == 2){
							genderRB2.setSelected(true);
						}
					}
				}
				
			}
		});
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		
		int targetOrganFlag = 0;
		if(targetOrganFlagRB1.isSelected()){
			targetOrganFlag = 1;
		}else if(targetOrganFlagRB2.isSelected()){
			targetOrganFlag = 2;
		}else{
			showErrorMessage("请选择靶器官状态！");
			return;
		}
		String visceraName = visceraNameComboBox.getSelectionModel().getSelectedItem();
		if(null == visceraName){
			showErrorMessage("请选择靶器官名称！");
			return;
		}
		Map<String,Object> visceraMap = visceraNameMapMap.get(visceraName);
		Integer visceraType = (Integer) visceraMap.get("visceraType");
		String visceraCode = (String) visceraMap.get("visceraCode");
		
		//判断是否有逻辑错误
		TblAnimalTargetOrgan tblAnimalTargetOrgan2 = BaseService.getInstance().getTblAnimalTargetOrganService()
				.getLastTargetOrgan(studyNo,visceraName);
		if(null != tblAnimalTargetOrgan2){
			if(targetOrganFlag == tblAnimalTargetOrgan2.getTargetOrganFlag()){
				showErrorMessage("逻辑错误！");
				return;
			}
		}else{
			if(targetOrganFlag == 2){
				showErrorMessage("逻辑错误！");
				return;
			}
		}
		
		int gender = -1;
		if(genderRB0.isSelected()){
			gender = 0;
		}else if(genderRB1.isSelected()){
			gender = 1;
		}else if(genderRB2.isSelected()){
			gender = 2;
		}else {
			showErrorMessage("请选择性别相关！");
			return;
		}
		
		
		String occurPhase = occurPhaseText.getText().trim();
		if(null == occurPhase || "".equals(occurPhase)){
			showErrorMessage("请输入发现阶段！");
			return;
		}
		
		String remark = remarkText.getText();
		if(null != remark && remark.getBytes().length > 200){
			showErrorMessage("备注内容长度不能大于200字节！");
			remarkText.requestFocus();
			return;
		}
		
		TblAnimalTargetOrgan tblAnimalTargetOrgan = new TblAnimalTargetOrgan();
		
		tblAnimalTargetOrgan.setStudyNo(studyNo);
		tblAnimalTargetOrgan.setTargetOrganFlag(targetOrganFlag);
		tblAnimalTargetOrgan.setVisceraType(visceraType);
		tblAnimalTargetOrgan.setVisceraCode(visceraCode);
		tblAnimalTargetOrgan.setVisceraName(visceraName);
		tblAnimalTargetOrgan.setGender(gender);
		tblAnimalTargetOrgan.setOccurPhase(occurPhase);
		tblAnimalTargetOrgan.setRemark(remark);
		
		//签字窗口
		SignMeFrame signMeFrame = new SignMeFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("靶器官登记");
		try {
			signMeFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//签字通过
		if("OK".equals(SignMeFrame.getType())){
			String operator = SaveUserInfo.getRealName();
			
			BaseService.getInstance().getTblAnimalTargetOrganService().addOne(tblAnimalTargetOrgan,operator);
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			
			//TODO 更新表数据
			TargetOrganPage.getInstance().updateTable();
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
	public void loadData(String studyNo) {
		this.studyNo = studyNo;
		//1.靶器官状态RadioButton 置为都不选
		targetOrganFlagRB1.setSelected(false);
		targetOrganFlagRB2.setSelected(false);
		//2.设置发现阶段默认值（对应解剖申请的试验阶段）
		setOccurPhaseDefaultValue();
		//3.备注清空
		remarkText.clear();
	}

	
	/**
	 * 设置发现阶段默认值（对应解剖申请的试验阶段）
	 */
	private void setOccurPhaseDefaultValue() {
		occurPhaseText.clear();
//		String teakPhase = BaseService.getInstance().getTblAnimalTargetOrganService().getTestPhaseByTaskId(taskId);
//		if(null != teakPhase && !"".equals(teakPhase)){
//			occurPhaseText.setText(teakPhase);
//		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TargetOrganRegister.fxml"));
		Scene scene = new Scene(root, 489, 344);
		stage.setScene(scene);
		stage.setTitle("靶器官登记");
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
