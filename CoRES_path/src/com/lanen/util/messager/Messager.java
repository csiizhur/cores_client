package com.lanen.util.messager;

import java.util.Date;

import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.view.main.Main;

/** 弹出对话框
 * @author Administrator
 *
 */
public class Messager {

	/**显示消息（信息）
	 * @param message
	 */
	public static void showMessage(String message){
		Stage stage = Main.stageMap.get("Alert");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				Alert.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("Alert",stage);
			
		}
		Alert.getInstance().loadData(message);
		stage.showAndWait();
	}
	
	/**显示错误消息（信息）
	 * @param message
	 */
	public static void showErrorMessage(String message){
		Stage stage = Main.stageMap.get("AlertError");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				AlertError.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AlertError",stage);
			
		}
		AlertError.getInstance().loadData(message);
		stage.showAndWait();
	}
	
	/**显示警告消息（信息）
	 * @param message
	 */
	public static void showWarnMessage(String message){
		Stage stage = Main.stageMap.get("AlertWarn");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				AlertWarn.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("AlertWarn",stage);
			
		}
		AlertWarn.getInstance().loadData(message);
		stage.showAndWait();
	}
	
	/**显示简单confirm窗口
	 * @param title 标题
	 * @param msg   信息
	 * @return
	 */
	public static boolean showSimpleConfirm(String title,String msg){
		Stage stage = Main.stageMap.get("Confirm_Simple");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				Confirm_Simple.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("Confirm_Simple",stage);
			
		}
		stage.setTitle(title);
		Confirm_Simple.getInstance().loadData(msg);
		stage.showAndWait();
		return Confirm_Simple.getInstance().isPass();
	}
	
	/**显示confirm窗口
	 * @param title 标题
	 * @param msg1   信息1
	 * @param msg2   信息2
	 * @return
	 */
	public static boolean showConfirm(String title,String msg1,String msg2){
		Stage stage = Main.stageMap.get("Confirm");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				Confirm.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("Confirm",stage);
			
		}
		stage.setTitle(title);
		Confirm.getInstance().loadData(msg1,msg2);
		stage.showAndWait();
		return Confirm.getInstance().isPass();
	}
	/**显示Confirm_animalCode窗口
	 * @param title 标题
	 * @param msg1   信息1
	 * @param msg2   信息2
	 * @return
	 */
	public static boolean showConfirm_animalCode(String title,String studyNo,String animalCode){
		Stage stage = Main.stageMap.get("Confirm_animalCode");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				Confirm_animalCode.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("Confirm_animalCode",stage);
			
		}
		stage.setTitle(title);
		Confirm_animalCode.getInstance().loadData(studyNo,animalCode);
		stage.showAndWait();
		return Confirm_animalCode.getInstance().isPass();
	}
	
	/**显示选择日期窗口
	 * @param title 标题
	 * @param msg   信息
	 * @return
	 */
	public static void showChooseDate(String title){
		
		Stage stage = Main.stageMap.get("ChooseOneDate");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				ChooseOneDate.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("ChooseOneDate",stage);
			
		}
		
		stage.setTitle(title);
		ChooseOneDate.getInstance().loadData();
		
		stage.showAndWait();
		
		//return ChooseOneDate.getInstance().getChooseDate();
		
	}
}
