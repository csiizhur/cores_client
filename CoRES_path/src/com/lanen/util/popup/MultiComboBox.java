//package com.lanen.util.popup;
//
//
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.scene.Node;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.StackPaneBuilder;
//import javafx.stage.Popup;
//import javafx.stage.Window;
//
//public class MultiComboBox {
//
//	private  TextField textField;
//	private  Popup popup;
//	private  ListView<CheckBox> listView;
//	private  ObservableList<CheckBox> data_listView = FXCollections.observableArrayList();
//	private  boolean isOnTextField =false;  //当点击textField 时，不改变其值
//	public   TextField getTextField(){
//		if(textField == null){
//			textField= new TextField();
//			textField.setEditable(false);
//		}
//		textField.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				isOnTextField =true;
//				TextField textField = (TextField) event.getSource();
//				if(!textField.getText().equals("") && data_listView.size()>0){
//					String text =textField.getText();
//					for(CheckBox checkBox : data_listView){
//						if(text.contains(checkBox.getText())){
//							checkBox.setSelected(true);
//						}
//					}
//				}
//				showPopup(textField);
//				isOnTextField= false;
//			}
//
//			
//		});
//		return textField;
//	}
//	private  void showPopup(TextField textField) {
//		if (popup == null) {
//			popup = new Popup();
//			popup.setAutoHide(true);
//			popup.setHideOnEscape(true);
//			popup.setAutoFix(true);
//			listView =getListView();
//			StackPane stackpane = StackPaneBuilder.create().prefWidth(textField.getWidth()).prefHeight(150).style("-fx-border-width:1px;-fx-border-color:blue;-fx-background-color:blue;").build();
//			stackpane.getChildren().add(listView);
//			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
//				@Override
//				public void changed(ObservableValue<? extends CheckBox> observable,
//						CheckBox oldValue, CheckBox newValue) {
//					if(newValue!=null){
//						if(newValue.isSelected()){
//							newValue.setSelected(false);
//						}else{
//							newValue.setSelected(true);
//						}
//					}
//					listView.getSelectionModel().clearSelection();
//				}});
//			popup.getContent().add(stackpane);
//		}
//		showPopupWithinBounds(textField, popup);
//		
//	}
//	private  ListView<CheckBox> getListView() {
//		if(listView == null){
//			listView = new ListView<CheckBox>();
//			listView.setItems(data_listView);
//		}
//		return listView;
//	}
//	private static void showPopupWithinBounds(final Node node, final Popup popup) {
//		final Window window = node.getScene().getWindow();
//		double x = window.getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
//		double y = window.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + node.getBoundsInParent().getHeight();
//		popup.show(window, x-5, y-5);
//		
//	}
//	public  void updateData_listView(ObservableList<CheckBox> data_listView){
//		this.data_listView.clear();
//		if(null!=data_listView && data_listView.size()>0){
//			for(final CheckBox checkBox:data_listView){
//				checkBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
//
//					@Override
//					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
//							Boolean newValue) {
//						updateTextFiledValue(newValue,checkBox.getText());	
//					}
//				});
//				this.data_listView.add(checkBox);
//			}
//		}
//	}
//	private  void updateTextFiledValue(Boolean selected, String text) {
//		if(!isOnTextField){
//			String textValue = "";
//			for(CheckBox checkBox :data_listView){
//				if(checkBox.isSelected()){
//					if("".equals(textValue)){
//						textValue=checkBox.getText();
//					}else{
//						textValue=textValue+","+checkBox.getText();
//					}
//				}
//			}
//			textField.setText(textValue);
//		}
//			
//	}
//}
