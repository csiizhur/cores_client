package com.lanen.view.test;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;
import com.lanen.view.test.HistopathReview.HistopathCheck;

/**提交复查、复查完成、最终签字 前的信息展示
 * @author Administrator
 *
 */
public class HReviewFinish extends Application implements Initializable {
	
	@FXML
	private AnchorPane anchorPane1;//动物编号AnchorPane
	@FXML
	private AnchorPane anchorPane2;//脏器或组织AnchorPane
	@FXML
	private AnchorPane anchorPane3;//复查情况AnchorPane
	@FXML
	private Label msgLabel;//复查情况AnchorPane
	
	@FXML
	private ComboBox<String> animalCodeComboBox;
	ObservableList<String> data_animalCodeComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> visceraOrTissueComboBox;
	ObservableList<String> data_visceraOrTissueComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> opinionComboBox;
	ObservableList<String> data_opinionComboBox = FXCollections.observableArrayList();
	
	
	@FXML
	private TableColumn<HistopathCheck,String> animalCodeCol_1;
	@FXML
	private TableColumn<HistopathCheck,String> visceraOrTissueNameCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkResultCol;
	@FXML
	private TableColumn<HistopathCheck,String> checkTimeCol;
	@FXML
	private TableColumn<HistopathCheck,String> opinionCol;
	
	/**
	 * 组织学所见
	 */
	@FXML
	private TableView<HistopathCheck> histopathCheckTable;
	private ObservableList<HistopathCheck> data_histopathCheckTable = FXCollections.observableArrayList();
	private ObservableList<HistopathCheck> data_histopathCheckTable2 = FXCollections.observableArrayList();
	
	
	private String taskId = "";
	/**
	 * 1:提交复查   2:复查完成  3：最终签字
	 */
	private int toWhere ;
	
	private static HReviewFinish instance;
	public static HReviewFinish getInstance(){
		if(null == instance){
			instance = new HReviewFinish();
		}
		return instance;
	}
	
	public HReviewFinish() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initHistopathCheckTable();
		initComboBox();
	}
	
	/**
	 * 初始化 3 个ComboBox
	 */
	private void initComboBox() {
		animalCodeComboBox.setItems(data_animalCodeComboBox);
		animalCodeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTable();
				}
			}});
		visceraOrTissueComboBox.setItems(data_visceraOrTissueComboBox);
		visceraOrTissueComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTable();
				}
			}});
		
		data_opinionComboBox.add("全部");
		data_opinionComboBox.add("已复查");
		data_opinionComboBox.add("未复查");
		opinionComboBox.setItems(data_opinionComboBox);
		opinionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTable();
				}
			}});
		
	}

	/**
	 * 初始化 histopathCheckTable
	 */
	private void initHistopathCheckTable() {
		histopathCheckTable.setItems(data_histopathCheckTable2);
		histopathCheckTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol_1.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("animalCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("visceraOrTissueName"));
		checkResultCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkResult"));
		checkTimeCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("checkTime"));
		opinionCol.setCellValueFactory(new PropertyValueFactory<HistopathCheck,String>("opinion"));
	}
	
	/**选择同行评议人
	 * @param userName
	 * @return
	 */
	private String selectPeerreview(String userName){
		
		Stage stage = Main.stageMap.get("SelectPeerreview");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SelectPeerreview.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SelectPeerreview",stage);
			
		}
		stage.setTitle("同行评议人");
		SelectPeerreview.getInstance().loadData();
		stage.showAndWait();
		return SelectPeerreview.getInstance().getHistopathReviewer();
	}
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		//1:提交复查   2:复查完成  3：最终签字
//		toWhere ;
		if(toWhere == 1){
			
			TblAnatomyTask task = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
			int histopathReviewRequirement = task.getHistopathReviewRequirement();
			String histopathReviewer = task.getHistopathReviewer();
			if(histopathReviewRequirement == 0){
				if(Messager.showConfirm("提示", "当前组织学检查未指定同行评议者，", "是否指定？")){
					histopathReviewer = selectPeerreview(histopathReviewer);
					if(null != histopathReviewer && !"".equals(histopathReviewer)){
						histopathReviewRequirement = 1;
					}else{
						return;
					}
				}
			}else{
				histopathReviewer = selectPeerreview(histopathReviewer);
				if(null != histopathReviewer && !"".equals(histopathReviewer)){
					histopathReviewRequirement = 1;
				}else{
					return;
				}
			}
			
			//2.签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("提交");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			if("OK".equals(SignMeFrame.getType())){
				//3.提交，更新状态      histopathCheckFinishSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
				String operator = SaveUserInfo.getRealName();
//				BaseService.getInstance().getTblHistopathCheckService().checkFinishSign(taskId,operator,histopathReviewRequirement,histopathReviewer);
				
				
				HistopathCheckPage.getInstance().updateBtnState();
				HistopathReview.getInstance().updateBtnState();
				showMessage("提交成功！");
			}
		}else if(toWhere == 2){
			//2.签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("复核完成");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			if("OK".equals(SignMeFrame.getType())){
				//3.提交，更新状态      histopathCheckFinishSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
				String operator = SaveUserInfo.getRealName();
				BaseService.getInstance().getTblHistopathCheckService().reviewFinishSign(taskId,operator);
				HistopathReview.getInstance().updateBtnState();
				showMessage("复核完成！");
			}
		}else{
			//3.签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("最终签字");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			if("OK".equals(SignMeFrame.getType())){
				//3.最终签字，更新状态      histopathReviewFinalSign,histopathReviewFlag(0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）)
				String operator = SaveUserInfo.getRealName();
				BaseService.getInstance().getTblHistopathCheckService().reviewFinalSign(taskId,operator);
				HistopathReview.getInstance().updateBtnState();
				showMessage("最终签字完成！");
			}
		}
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
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
	 * @param data_histopathCheckTable3 
	 */
	public void loadData(String taskId, ObservableList<HistopathCheck> data_histopathCheckTable3,int toWhere) {
		this.taskId = taskId;
		this.data_histopathCheckTable = data_histopathCheckTable3;
		this.toWhere = toWhere;
		
		//1.清空
		animalCodeComboBox.getSelectionModel().clearSelection();
		visceraOrTissueComboBox.getSelectionModel().clearSelection();
		opinionComboBox.getSelectionModel().clearSelection();
		
		data_histopathCheckTable2.clear();
		data_animalCodeComboBox.clear();
		data_visceraOrTissueComboBox.clear();
		
		//2.处理状态
		if(toWhere == 1){
			opinionCol.setVisible(false);
			anchorPane3.setVisible(false);
			opinionComboBox.getSelectionModel().select(0);
		}else if(toWhere == 2){
			opinionCol.setVisible(true);
			anchorPane3.setVisible(true);
			opinionComboBox.getSelectionModel().select(1);
		}else{
			opinionCol.setVisible(true);
			anchorPane3.setVisible(true);
			opinionComboBox.getSelectionModel().select(0);
		}
		
		//3.
		updateAnimalCodeComboBox();
		
		updateVisceraOrTissueComboBox();
		
		//4.
		updateMsgLabel();
	}

	private void updateMsgLabel() {
		String text = "";
		if(toWhere == 1){
			text = "以上为镜检记录，确定提交签字？";
		}else if(toWhere == 2){
			text = "以上为复查记录，确定提交复查完成签字？";
		}else{
			text = "以上为镜检及复查记录，确定提交最终签字？";
		}
		msgLabel.setText(text);
	}

	private void updateVisceraOrTissueComboBox() {
		data_visceraOrTissueComboBox.clear();
		data_visceraOrTissueComboBox.add("全部");
		if(null != data_histopathCheckTable && data_histopathCheckTable.size() > 0){
			for(HistopathCheck obj:data_histopathCheckTable){
				if(!data_visceraOrTissueComboBox.contains(obj.getVisceraOrTissueName())){
					data_visceraOrTissueComboBox.add(obj.getVisceraOrTissueName());
				}
			}
		}
		visceraOrTissueComboBox.getSelectionModel().select(0);
		
	}

	private void updateAnimalCodeComboBox() {
		data_animalCodeComboBox.clear();
		data_animalCodeComboBox.add("全部");
		if(null != data_histopathCheckTable && data_histopathCheckTable.size() > 0){
			for(HistopathCheck obj:data_histopathCheckTable){
				if(!data_animalCodeComboBox.contains(obj.getAnimalCode())){
					data_animalCodeComboBox.add(obj.getAnimalCode());
				}
			}
		}
		animalCodeComboBox.getSelectionModel().select(0);
	}

	private void updateTable(){
		data_histopathCheckTable2.clear();
		if(null != data_histopathCheckTable && data_histopathCheckTable.size() > 0){
			String animalCode = animalCodeComboBox.getSelectionModel().getSelectedItem();
			String visceraOrTissue = visceraOrTissueComboBox.getSelectionModel().getSelectedItem();
			String opinion = opinionComboBox.getSelectionModel().getSelectedItem();
			if(null != animalCode && null != visceraOrTissue && null != opinion){
				for(HistopathCheck obj:data_histopathCheckTable){
					if((animalCode.equals("全部") || animalCode.equals(obj.getAnimalCode()))&&
					   (visceraOrTissue.equals("全部") || visceraOrTissue.equals(obj.getVisceraOrTissueName()))&&
					   (opinion.equals("全部") || opinion.equals("已复查")&& null !=obj.getOpinion() &&!obj.getOpinion().equals("") 
					     ||  opinion.equals("未复查")&& (null == obj.getOpinion()|| obj.getOpinion().equals("")))){
						data_histopathCheckTable2.add(obj);
					}
				}
			}
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HReviewFinish.fxml"));
		Scene scene = new Scene(root, 650, 408);
		stage.setScene(scene);
//		stage.setTitle("复查完成");
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
	public static void main(String[] args) {
		launch(args);
		HReviewFinish.getInstance().loadData("", null,1);
	}
}
