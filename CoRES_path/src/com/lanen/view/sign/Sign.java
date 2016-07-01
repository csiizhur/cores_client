package com.lanen.view.sign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.model.User;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

public class Sign {

	/**显示签字窗口（含原因）
	 * @param reasonLabelMsg 操作原因label
	 * @param title   窗口标题
	 * @return
	 */
	public static boolean openSignWithReasonFrame(String reasonLabelMsg,String title){
		Stage stage = Main.stageMap.get("SignFrameWithReason");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignFrameWithReason.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignFrameWithReason",stage);
			
		}
		stage.setTitle(title);
		SignFrameWithReason.getInstance().loadData(reasonLabelMsg);
		stage.showAndWait();
		return SignFrameWithReason.getInstance().isPass();
	}
	/**显示签字窗口（含原因,用户列表）
	 * @param reasonLabelMsg 操作原因label
	 * @param title   窗口标题
	 * @return
	 */
	public static Map<String,Object> openSignUserRsnFrame(String title,String reasonLabelMsg,List<String> userNameList){
		Stage stage = Main.stageMap.get("SignFrameUserRsn");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignFrameUserRsn.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignFrameUserRsn",stage);
			
		}
		stage.setTitle(title);
		SignFrameUserRsn.getInstance().loadData(reasonLabelMsg,userNameList);
		stage.showAndWait();
		if(SignFrameUserRsn.getInstance().isPass()){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user", SignFrameUserRsn.getInstance().getSignUser());
			map.put("reason", SignFrameUserRsn.getInstance().getReason());
			return map;
		}
		return null;
	}

	/**返回操作原因
	 * @return
	 */
	public static String getReason(){
		return SignFrameWithReason.getInstance().getReason();
	}
	
	
	/**打开签字窗口(选择用户)，返回签字者（用户权限）
	 * @param title
	 * @param msg
	 * @return
	 */
	public static User openSignWithPrivilegeName(String title,String msg,String privilegeName){
		Stage stage = Main.stageMap.get("SignPrivilegeFrame");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignPrivilegeFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignPrivilegeFrame",stage);
			
		}
		stage.setTitle(title);
		SignPrivilegeFrame.getInstance().loadData(msg,privilegeName);
		stage.showAndWait();
		return SignPrivilegeFrame.getInstance().getSignUser();
	}
	/**打开签字窗口(选择用户)，返回签字者(传入签字者列表用户名)
	 * @param title
	 * @param msg
	 * @return
	 */
	public static User openSignWithUserNameList(String title,String msg,List<String> userNameList){
		Stage stage = Main.stageMap.get("SignPrivilegeFrame");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				SignPrivilegeFrame.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("SignPrivilegeFrame",stage);
			
		}
		stage.setTitle(title);
		SignPrivilegeFrame.getInstance().loadData(msg,userNameList);
		stage.showAndWait();
		return SignPrivilegeFrame.getInstance().getSignUser();
	}
}
