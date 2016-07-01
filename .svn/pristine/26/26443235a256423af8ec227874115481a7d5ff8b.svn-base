package com.lanen.view.sign;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.model.User;
import com.lanen.zero.main.Main;
import com.lanen.zero.main.MainFrame;

public class Sign {
	
	
	
	/**打开签字窗口（输入用户），返回签字者
	 * @param title
	 * @param msg
	 * @return
	 */
	public static User openSignNoUserName(String title,String msg){
		Stage stage = Main.stageMap.get("SignFrameNoUserName");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(MainFrame.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignFrameNoUserName.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignFrameNoUserName",stage);
			
		}
		stage.setTitle(title);
		SignFrameNoUserName.getInstance().loadData(msg);
		stage.showAndWait();
		return SignFrameNoUserName.getInstance().getSignUser();
	}
	/**打开签字窗口(选择用户)，返回签字者
	 * @param title
	 * @param msg
	 * @return
	 */
	public static User openSign(String title,String msg){
		Stage stage = Main.stageMap.get("SignFrame");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(MainFrame.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignFrame",stage);
			
		}
		stage.setTitle(title);
		SignFrame.getInstance().loadData(msg);
		stage.showAndWait();
		return SignFrame.getInstance().getSignUser();
	}

	/**打开签字窗口(删除原因，选择用户)，返回map(isPass,signUser,reason)
	 * @param title
	 * @param msg
	 * @return
	 */
	public static Map<String,Object> openSignWithReason(String title,String msg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isPass", false);
		
		Stage stage = Main.stageMap.get("SignFrameWithReason");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(MainFrame.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignFrameWithReason.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignFrameWithReason",stage);
			
		}
		stage.setTitle(title);
		SignFrameWithReason.getInstance().loadData(msg);
		stage.showAndWait();
		if(SignFrameWithReason.getInstance().isPass()){
			map.put("isPass", true);
			map.put("signUser", SignFrameWithReason.getInstance().getSignUser());
			map.put("reason", SignFrameWithReason.getInstance().getReason());
		}
		return map;
	} 
}
