package com.lanen.view.test;

import java.net.URL;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.util.messager.Messager;

/**确认其余未见异常， 选择解剖者
 * @author Administrator
 *
 */
public class ConfirmAnatomy extends Application implements Initializable {
	
	@FXML
	private ListView<String> pListView;
	private ObservableList<String> data_pListView = FXCollections.observableArrayList();
	@FXML
	private Label animalCodeLabel;
	
	private static String realName = null;
	
	private static boolean success = false;

	private static ConfirmAnatomy instance;
	public static ConfirmAnatomy getInstance(){
		if(null == instance){
			instance = new ConfirmAnatomy();
		}
		return instance;
	}
	
	public ConfirmAnatomy() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initListView();
	}
	
	private void initListView() {
		pListView.setItems(data_pListView);
		
	}
	
	public void updateListView(List<String> list){
		pListView.getSelectionModel().clearSelection();
		data_pListView.clear();
		if(null != list && list.size() > 0){
			for(String realName:list){
				data_pListView.add(realName);
			}
		}
	}

	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		String selectedName = pListView.getSelectionModel().getSelectedItem();
		if(null == selectedName || "".equals(selectedName)){
			showErrorMessage("请确认解剖者！");
			return;
		}
		if(!realName.equals(selectedName)){
			showErrorMessage("所选动物与解剖者不匹配！");
			return;
		}
		success = true;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		success = false;
	}
	/**
	 * 加载数据
	 */
	public void loadData(List<String> reanNameList,String realName,String animalCode) {
		updateListView(reanNameList);
		ConfirmAnatomy.realName = realName;
		animalCodeLabel.setText(animalCode);
		success = false;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("ConfirmAnatomy.fxml"));
		Scene scene = new Scene(root, 334, 200);
		stage.setScene(scene);
		stage.setTitle("确认");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
//		stage.showAndWait();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

	public boolean isSuccess() {
		return success;
	}

}
