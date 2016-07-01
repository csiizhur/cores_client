package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblVisceraWeight;
import com.lanen.model.path.TblVisceraWeightHis;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

public class EditVisceraWeight extends Application implements Initializable {
	
	private Stage stage;
	
	@FXML
	private Label studyNoLabel;
	@FXML
	private Label animalCodeLabel;
	@FXML
	private Label visceraNameLabel;
	@FXML
	private Label isFixedLabel;
	@FXML
	private Label weightUnitLabel;
	@FXML
	private Button balWeight;
	
	@FXML
	private TextField weightTextField;

	//原脏器重量
	private static String oldWeight = "";
	
	private static TblVisceraWeight currentTblVisceraWeight = null;
	
	private static EditVisceraWeight instance;
	public static EditVisceraWeight getInstance(){
		if(null == instance){
			instance = new EditVisceraWeight();
		}
		return instance;
	}
	
	public EditVisceraWeight() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
//		updatePane("20000000003");
	}
	/**
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
	}
	/**天平称量按钮事件
	 * @param event
	 */
	@FXML
	private void onBalWeight(ActionEvent event){
		try {
//			EditVisceraWeight_balWeight.getInstance().start(getStage());
			Stage stage = Main.stageMap.get("EditVisceraWeight_balWeight.fxml");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				EditVisceraWeight_balWeight.getInstance().start(stage);
				Main.stageMap.put("EditVisceraWeight_balWeight.fxml",stage);
			}else{
				stage.show();
			}
			String studyNo=studyNoLabel.getText();
			String animalCode=animalCodeLabel.getText();
			String visceraName=visceraNameLabel.getText();
			EditVisceraWeight_balWeight.getInstance().loadData(studyNo,animalCode,visceraName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**天平读数更新称重数据
	 * @param weight
	 */
	public void changeWeightByBal(String weight){
		String studyNo=studyNoLabel.getText();
		String animalCode=animalCodeLabel.getText();
		String visceraName=visceraNameLabel.getText();
		if(Messager.showConfirm("数据确认","专题编号："+studyNo+"  动物编号："+animalCode,"待称脏器："+visceraName+"称量结果："+weight)){
			
		}else{
			return;
		}
		weightTextField.setText(weight);
//		EditVisceraWeight_balWeight.getInstance().onExitBtnAction(null);
		Stage stage = Main.stageMap.get("EditVisceraWeight_balWeight.fxml");
		if(null!=stage){
			stage.hide();
		}
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String weight = weightTextField.getText();
		if(null == weight || "".equals(weight)){
			showErrorMessage("请先录入脏器重量。");
			return;
		}
		if(!NumberValidationUtils.isPositiveRealNumber(weight)){
			showErrorMessage("脏器重量数据格式不正确！");
			return;
		}
		if(oldWeight.equals(weight.trim())){
			showErrorMessage("脏器重量编辑后再保存！");
			return ;
		}
		
		String currentUserName = SaveUserInfo.getUserName();
//		Date date = new Date();
		Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		//保存历史记录
		TblVisceraWeightHis visceraWeightHis = new TblVisceraWeightHis();
		visceraWeightHis.setId(BaseService.getInstance().getTblVisceraWeightHisService().getKey());
		visceraWeightHis.setAnimalCode(currentTblVisceraWeight.getAnimalCode());
		visceraWeightHis.setAttachedVisceraFlag(currentTblVisceraWeight.getAttachedVisceraFlag());
		visceraWeightHis.setAttachedVisceraNames(currentTblVisceraWeight.getAttachedVisceraNames());
		visceraWeightHis.setBalCode(currentTblVisceraWeight.getBalCode());
		visceraWeightHis.setBalValidDate(currentTblVisceraWeight.getBalValidDate());
		visceraWeightHis.setCalIndexId(currentTblVisceraWeight.getCalIndexId());
		visceraWeightHis.setFixedWeightFlag(currentTblVisceraWeight.getFixedWeightFlag());
		visceraWeightHis.setHostName(currentTblVisceraWeight.getHostName());
		visceraWeightHis.setOldId(currentTblVisceraWeight.getId());
		visceraWeightHis.setOperate("编辑");
		visceraWeightHis.setOperateDate(date);
		visceraWeightHis.setOperater(currentUserName);
		visceraWeightHis.setOperateTime(currentTblVisceraWeight.getOperateTime());
		visceraWeightHis.setOperator(currentTblVisceraWeight.getOperator());
		visceraWeightHis.setSessionId(currentTblVisceraWeight.getSessionId());
		visceraWeightHis.setStudyNo(currentTblVisceraWeight.getStudyNo());
		visceraWeightHis.setSubVisceraCode(currentTblVisceraWeight.getSubVisceraCode());
		visceraWeightHis.setSubVisceraName(currentTblVisceraWeight.getSubVisceraName());
		visceraWeightHis.setVisceraCode(currentTblVisceraWeight.getVisceraCode());
		visceraWeightHis.setVisceraName(currentTblVisceraWeight.getVisceraName());
		visceraWeightHis.setVisceraType(currentTblVisceraWeight.getVisceraType());
		visceraWeightHis.setWeight(currentTblVisceraWeight.getWeight());
		visceraWeightHis.setWeightUnit(currentTblVisceraWeight.getWeightUnit());
		
		//签字通过
		if(Sign.openSignWithReasonFrame("编辑原因", "脏器重量编辑")){
			visceraWeightHis.setOperateRsn(Sign.getReason());
			visceraWeightHis.setOperater(SaveUserInfo.getUserName());
			BaseService.getInstance().getTblVisceraWeightHisService().save(visceraWeightHis);
			currentTblVisceraWeight.setWeight(weight.trim());
			currentTblVisceraWeight.setOperateTime(date);
			currentTblVisceraWeight.setOperator(currentUserName);
			BaseService.getInstance().getTblVisceraWeightService().update(currentTblVisceraWeight);
			DataValidation.getInstance().updateVisceraWeightTable(currentTblVisceraWeight.getId());
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
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
	public void loadData(String id) {
		//更新面板上的  几个Label 及TextField
		updatePane(id);
		
	}
	/**更新面板上的  几个Label 及TextField
	 * @param id
	 */
	public void updatePane(String id ){
		studyNoLabel.setText("");
		animalCodeLabel.setText("");
		visceraNameLabel.setText("");
		isFixedLabel.setText("");
		weightUnitLabel.setText("");
		weightTextField.setText("");
		if(null != id && !"".equals(id)){
//			new BaseService();
			TblVisceraWeight tblVisceraWeight = BaseService.getInstance().getTblVisceraWeightService().getById(id);
			if(null != tblVisceraWeight){
				currentTblVisceraWeight = tblVisceraWeight;
				studyNoLabel.setText(tblVisceraWeight.getStudyNo());
				animalCodeLabel.setText(tblVisceraWeight.getAnimalCode());
				String visceraName = tblVisceraWeight.getVisceraName();
				String subVisceraName = tblVisceraWeight.getSubVisceraName();
				if(null != subVisceraName && !"".equals(subVisceraName)){
					visceraName = subVisceraName;
				}
				
				visceraNameLabel.setText(visceraName);
				String isFixedStr = "否";
				Integer isFixed = tblVisceraWeight.getFixedWeightFlag();
				if(null != isFixed && isFixed == 1 ){
					isFixedStr = "是";
				}
				isFixedLabel.setText(isFixedStr);
				weightUnitLabel.setText(tblVisceraWeight.getWeightUnit());
				weightTextField.setText(tblVisceraWeight.getWeight());
				oldWeight = tblVisceraWeight.getWeight();
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("EditVisceraWeight.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("脏器称重-编辑");
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
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

}
