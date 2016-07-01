//package com.lanen.view.clinicaltest;
//
//import com.lanen.model.studyplan.TblClinicalTestReq;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//
//public class TblSpecimenFrame extends Application {
//
//	private static TblClinicalTestReq tblClinicalTestReq=null;
//	
//	public TblSpecimenFrame(){
//		TblSpecimenFrame.tblClinicalTestReq=null;
//	}
//	@Override
//	public void start(Stage stage) throws Exception {
//
//		Parent root=FXMLLoader.load(getClass().getResource("TblSpecimenFrame.fxml"));
//		Scene scene =new Scene(root,780,565);
//		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
//		stage.setScene(scene);
//		stage.setTitle("标本接收");
//		stage.setMinWidth(760);
//		stage.setMinHeight(575);
////		stage.setResizable(false);
//		stage.show();
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		launch(args);
//	}
//	public static TblClinicalTestReq getTblClinicalTestReq() {
//		return tblClinicalTestReq;
//	}
//	public static void setTblClinicalTestReq(TblClinicalTestReq tblClinicalTestReq) {
//		TblSpecimenFrame.tblClinicalTestReq = tblClinicalTestReq;
//	}
//
//}
