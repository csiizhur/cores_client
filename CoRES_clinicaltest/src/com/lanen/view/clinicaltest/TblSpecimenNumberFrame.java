//package com.lanen.view.clinicaltest;
//
//import com.lanen.model.clinicaltest.Specimen;
//import com.lanen.zero.main.MainFrame;
//
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//
//public class TblSpecimenNumberFrame extends Application {
//
//	private static ObservableList<Specimen> data=FXCollections.observableArrayList();
//	public TblSpecimenNumberFrame(){
//		data.clear();
//	}
//	@Override
//	public void start(Stage stage) throws Exception {
//
//		Parent root = FXMLLoader.load(getClass().getResource("TblSpecimenNumberFrame.fxml"));
//		Scene scene = new Scene(root,780,565);
//		stage.setScene(scene);
//		stage.initOwner(MainFrame.mainWidow);
//		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
//		stage.setTitle("检验编号确认");
//		stage.setMinWidth(760);
//		stage.setMinHeight(545);
////		stage.setResizable(false);
//		stage.showAndWait();
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	public static ObservableList<Specimen> getData() {
//		return data;
//	}
//
//	public void setData(ObservableList<Specimen> data) {
//		TblSpecimenNumberFrame.data = data;
//	}
//
//}
