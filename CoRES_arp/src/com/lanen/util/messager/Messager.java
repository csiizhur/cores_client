package com.lanen.util.messager;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.lanen.main.Main;
import com.lanen.main.MainFrame;

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
			stage.initOwner(MainFrame.mainWidow);
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
			stage.initOwner(MainFrame.mainWidow);
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
			stage.initOwner(MainFrame.mainWidow);
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
			stage.initOwner(MainFrame.mainWidow);
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
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(MainFrame.mainWidow);
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
	/**显示confirm窗口
	 * @param title标题
	 * @param msg1信息1
	 * @param msg2信息2
	 * @param window 父级窗口
	 * @return
	 */
	public static boolean showConfirm(String title,String msg1,String msg2,Window window){
		//接受数据窗口用
		Stage stage = Main.stageMap.get("Confirm_accessData");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(window);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				Confirm.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("Confirm_accessData",stage);
			
		}
		stage.setTitle(title);
		Confirm.getInstance().loadData(msg1,msg2);
		stage.showAndWait();
		return Confirm.getInstance().isPass();
	}
}
