package com.lanen.view;

import com.lanen.zero.main.MainFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartmentFrame2 extends Application {
	
	
	private static String departmentName="";

	public static void newDepartment(){
		Stage stage=new Stage();
		stage.initOwner(MainFrame.mainWindow);
		stage.initModality(Modality.APPLICATION_MODAL);
		DepartmentFrame2 departmentFrame2=new DepartmentFrame2();
		try {
			departmentFrame2.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DepartmentFrame.fxml"));
		
		Scene secne = new Scene(root,510,388);
		stage.setScene(secne);
		stage.setTitle("部门设置");
		DepartmentFrame2.departmentName="";
		stage.setResizable(false);
		stage.showAndWait();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);

	}

	public static String getDepartmentName() {
		return departmentName;
	}

	public static void setDepartmentName(String departmentName) {
		DepartmentFrame2.departmentName = departmentName;
	}
	

}
