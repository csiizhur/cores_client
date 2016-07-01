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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import com.lanen.util.BackgroundRunner;
import com.lanen.util.messager.Messager;

public class CommonPage extends Application implements Initializable {
	
	@FXML
	private TextField textField;
	private  Popup popup;
	@FXML
	private ListView<String> pListView;
	private ObservableList<String> data_pListView = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> listview;
	private ObservableList<String> data_listview = FXCollections.observableArrayList();

	private static CommonPage instance;
	public static CommonPage getInstance(){
		if(null == instance){
			instance = new CommonPage();
		}
		return instance;
	}
	
	public CommonPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		listview.setItems(data_listview);
		data_listview.add("111");
		data_listview.add("222");
		data_listview.add("333");
		
		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				listview.setUserData(true);
				if(null != newValue){
					listview.setId(newValue);
				}else{
					listview.setId("");
				}
			}
		});
		listview.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
			}
		});
		
		
		
		///           combobox------------------------
		initTextFeild();
		
	}
	private void initTextFeild() {
//		textField.textProperty().addListener(new ChangeListener<String>(){
//
//			@Override
//			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
//				if(null != newValue && !"".equals(newValue)){
//					showPopup();
//				}
//				
//			}
//		});
		textField.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				showPopup();
			}
		});
	}
	private  void showPopup() {
		if (popup == null) {
			popup = new Popup();
			popup.setAutoHide(true);
			popup.setHideOnEscape(true);
			popup.setAutoFix(true);
			pListView =getListView();
//			StackPane stackpane = StackPaneBuilder.create().prefWidth(textField.getWidth())
//				.prefHeight(150).style("-fx-border-width:1; -fx-focus-color: #F6F7F9;-fx-border-color:#c8ceda;" +
//						".list-cell:selected{-fx-background-color:#0092DC;}.list-cell{-fx-background-color:white;}" +
//						"-fx-background-color:white;").build();
//			stackpane.getChildren().add(pListView);
//			pListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
//				@Override
//				public void changed(ObservableValue<? extends String> observable,
//						String oldValue, String newValue) {
//					if(null != newValue){
//						textField.setText(newValue);
//						textField.setUserData(newValue);
//						textField.end();
//						popup.hide();
//					}
//				}});
			popup.getContent().add(pListView);
		}
		showPopupWithinBounds(textField, popup);
		
	}
	private  ListView<String> getListView() {
		if(null == pListView.getItems()){
//			pListView = new ListView<String>();
			pListView.setStyle("-fx-background-color:white;");
			pListView.setItems(data_pListView);
			data_pListView.add("aaaaaa");
			data_pListView.add("cccccc");
			data_pListView.add("dddddd");
		}
		return pListView;
	}
	private static void showPopupWithinBounds(final Node node, final Popup popup) {
		final Window window = node.getScene().getWindow();
		double x = window.getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
		double y = window.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + node.getBoundsInParent().getHeight();
		popup.show(window, x-5, y-5);
		
	}
	/**
	 * 多线程使用样例
	 */
	private void updateBaseinfoByStudyNo() {
		
		 new BackgroundRunner() {

	         
			@Override
			public void background() throws Exception {
			}

			@Override
			public void handleException(Exception e) throws Exception {
				showErrorMessage(e.getLocalizedMessage());
                e.printStackTrace();
				
			}

			@Override
			public void finish() throws Exception {
				
			}}.run();
		
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
	public void loadData() {
		//根据课题编号，更新SD，主题病理负责人，动物种类
		updateBaseinfoByStudyNo();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("Common.fxml"));
		Scene scene = new Scene(root, 591, 419);
		stage.setScene(scene);
		stage.setTitle("Common");
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
