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
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

/**选择同行评议人
 * @author Administrator
 *
 */
public class SelectPeerreview extends Application implements Initializable {
	
	
	@FXML
	private RadioButton needReviewRadio;	//需要复核
	@FXML
	private RadioButton noNeedReviewRadio;	//不需要复核
	
	@FXML
	private ComboBox<String> histopathReviewerComboBox;		//指定的复审人
	private ObservableList<String> data_histopathReviewerComboBox = FXCollections.observableArrayList();
	Map<String,String> realNameUserNameMap = new HashMap<String,String>();//  姓名：用户名
	Map<String,String> userNameRealNameMap = new HashMap<String,String>();//  用户名：姓名
	
	private int flag = -1;// -1:取消   0：不需要复核 1：需要复核
	private String histopathReviewer;//同行评议人
	private static SelectPeerreview instance;
	public static SelectPeerreview getInstance(){
		if(null == instance){
			instance = new SelectPeerreview();
		}
		return instance;
	}
	
	public SelectPeerreview() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		histopathReviewerComboBox.setItems(data_histopathReviewerComboBox);
		needReviewRadio.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newValue) {
				if(null != newValue){
					if(needReviewRadio.isSelected()){
						histopathReviewerComboBox.setDisable(false);
					}else{
						histopathReviewerComboBox.setDisable(true);
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
		histopathReviewer = null;
		flag = -1;
		
		//1.是否选择了
		if(needReviewRadio.isSelected() || noNeedReviewRadio.isSelected()){
			if(needReviewRadio.isSelected()){
				flag = 1;
				String selectedItem = histopathReviewerComboBox.getSelectionModel().getSelectedItem();
				if(null == selectedItem){
					showErrorMessage("请选择复核者！");
					return ;
				}
				histopathReviewer = realNameUserNameMap.get(selectedItem);
			}else{
				flag = 0;
			}
		}else{
			showErrorMessage("请先选择是否需要复核！");
			return ;
		}
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		flag = -1;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 */
	public void loadData() {
		flag = -1;
		histopathReviewer = null;
		updateHistopathReviewComboBox();
		
		needReviewRadio.setSelected(false);
		histopathReviewerComboBox.setDisable(true);
		noNeedReviewRadio.setSelected(false);
	}
	/**
	 * 更新复查者列表 ComboBox
	 */
	private void updateHistopathReviewComboBox(){
		
		data_histopathReviewerComboBox.clear();
		realNameUserNameMap.clear();
		
		List<User> pathUserList = BaseService.getInstance().getUserService().findByPrivilegeName2("毒性病理_同行评议");
		String currentUserName = SaveUserInfo.getUserName();
		if(null != pathUserList && pathUserList.size() > 0){
			for(User obj:pathUserList){
				if(!obj.getUserName().equals(currentUserName)){
					data_histopathReviewerComboBox.add(obj.getRealName());
					realNameUserNameMap.put(obj.getRealName(), obj.getUserName());
					userNameRealNameMap.put( obj.getUserName(),obj.getRealName());
				}
			}
//			if(null != userName && !"".equals(userName)){
//				String realName = userNameRealNameMap.get(userName);
//				if(null != realName && !"".equals(realName)){
//					histopathReviewerComboBox.getSelectionModel().select(realName);
//				}
//			}
		}
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("SelectPeerreview.fxml"));
		Scene scene = new Scene(root, 503, 328);
		stage.setScene(scene);
		stage.setTitle("提交复核");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
//		stage.show();
		
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getHistopathReviewer() {
		return histopathReviewer;
	}

	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

}
