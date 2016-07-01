package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
import java.util.Map;
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
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.path.TblVisceraMissing;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.SignFrame;


/**脏器缺失登记
 * @author Administrator
 *
 */
public class MissingVisceraRecord extends Application implements Initializable{
	
	
	private static boolean isSuccess = false;
	private static boolean isQueRu = false;
	private static String missingRsn = "";
	
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextArea missingRsnText;
	
	@FXML
	private Label  animalCodeLable;
	@FXML
	private Label visceraNameLable;
	private String taskId;
	private String sessionId;
	private String studyNo;
	private String animalCode;
	private String visceraName;
	private Integer sessionType;
	private Map<String,Object> map;
	private static MissingVisceraRecord instance;
	public static MissingVisceraRecord getInstance(){
		if(null == instance){
			instance = new MissingVisceraRecord();
		}
		return instance;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("MissingVisceraRecord.fxml"));
		Scene scene = new Scene(root, 425, 235);
		stage.setScene(scene);
		stage.setTitle("缺失脏器登记");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
//		stage.show();
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		isSuccess = false;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 确定
	 */
	@FXML
	private void onConfirmButton(ActionEvent event){
		if(null!=missingRsnText.getText() && !"".equals(missingRsnText.getText())){
			if(!isQueRu){
				//非缺如
				
				//签字窗口
				SignFrame signFrame = new SignFrame("");
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("身份验证");
				try {
					signFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//签字通过
				if("OK".equals(SignFrame.getType())){
						String userName = "";
						Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
						User user = SignFrame.getUser();
						if(null != user){
							userName = user.getUserName();
						}
						TblVisceraMissing t=new TblVisceraMissing();
						String id=BaseService.getInstance().getTblVisceraMissingService().getKey();
						t.setId(id);
						t.setAnimalCode(animalCode);
						t.setSessionId(sessionId);
						t.setStudyNo(studyNo);
						t.setMissingRsn(missingRsnText.getText());
						t.setOperateTime(currentDate);
						t.setOperator(userName);
						t.setVisceraCode((String)map.get("visceraCode"));
						t.setVisceraName((String)map.get("visceraName"));
						t.setVisceraType((Integer)map.get("visceraType"));
						if(map.get("subVisceraCode") != null && !map.get("subVisceraCode").equals("")  ){
							t.setSubVisceraCode((String)map.get("subVisceraCode"));
							t.setSubVisceraName((String)map.get("subVisceraName"));
						}
						
						BaseService.getInstance().getTblVisceraMissingService().save(t);
	//				BaseService.getTblPathSessionService().updateListBySessionIdList(userName,needConfirmSessionList);
	//				showMessage("脏器缺失登记完成");
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
						AnatomyProcessPage.getInstance().updateData_visceraListView(taskId,sessionId,animalCode);
						AnatomyProcessPage.getInstance().updateVisceraTree_weight(taskId,studyNo,animalCode,sessionType == 8);
						showMessage("脏器缺失登记完成");
				}
			}else{
				missingRsn = missingRsnText.getText();
				isSuccess = true;
				((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
			}
		}else{
			showErrorMessage("请填写缺失原因");
		}
	}
	public void loadDate(String taskId,String sessionId,String studyNo,String animalCode,String visceraName,
			Map<String,Object> map,Integer sessionType,boolean isQueRu){
		
		missingRsn = "";
		
		this.taskId = taskId;
		this.sessionId=sessionId;
		this.studyNo=studyNo;
		this.animalCode=animalCode;
		this.visceraName=visceraName;
		this.sessionType = sessionType;
		this.map=map;
		animalCodeLable.setText(animalCode);
		visceraNameLable.setText(visceraName);
		missingRsnText.setText("");
		MissingVisceraRecord.isQueRu = isQueRu;
		isSuccess = false;
	}

	

	public String getAnimalCode() {
		return animalCode;
	}

	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}

	public String getStudyNo() {
		return studyNo;
	}

	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getVisceraName() {
		return visceraName;
	}

	public void setVisceraName(String visceraName) {
		this.visceraName = visceraName;
	}

	public Map<String,Object> getMap() {
		return map;
	}
    
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setMap(Map<String,Object> map) {
		this.map = map;
	}
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getMissingRsn() {
		return missingRsn;
	}

}
