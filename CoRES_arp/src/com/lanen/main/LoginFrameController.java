package com.lanen.main;

import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.Employee;
import com.lanen.model.Iplogin;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;

public class LoginFrameController implements Initializable {
	@FXML
	private ComboBox<String> userNameComboBox;
	private ObservableList<String> data = FXCollections.observableArrayList();
	@FXML
	private PasswordField passwordText;
	
	

	@FXML
	private void onExitButtonAction(ActionEvent event){
		System.exit(0);
	}
	
/**
 * 登录
 * @param event
 * @throws ParseException
 */
	@FXML
	private void onEnsureButtonAction(ActionEvent event) throws ParseException {
		String userName = userNameComboBox.getSelectionModel().getSelectedItem();
		String password = passwordText.getText().toString();
		if (null  == userName ||"".equals(userName) ) {
//			Alert2.create("用户名不能为空！");
			Messager.showWarnMessage("用户名不能为空！");
		} else if ("".equals(password) || password == null) {
//			Alert2.create("密码不能为空！");
			Messager.showWarnMessage("密码不能为空！");
		} else {
//			map.put("nullUserError", "");// "" 表示用 户不为空
//			map.put("user", user);       // 用  户
//			map.put("forbidden", "");    // "" 表示用户账号未停用
//			map.put("entitle", "");      // "" 表示用户有权限
//			map.put("overdue", "");      // "" 表示用户密码未过期
			Map<String,Object> map = BaseService.getUserService().checkPrivilege(userName, password,"客户端_登录");
			//判断用户是否为空
			if("".equals(map.get("nullUserError"))){
				//用户非空
				Employee user =(Employee) map.get("user");
				if("".equals(map.get("forbidden"))){
					//账户未停用
					if("".equals(map.get("entitle"))){
						//用户有权限
						if("".equals(map.get("overdue"))){
							//用户密码未过期
							//保存登录用户
							SaveUserInfo.setUser(user);
							//打开主界面  ，关闭当前界面
							openMianFrameAndCloseThisFrame(event);
							
						}else{
							//记录登录日志
							Iplogin tblLog = new Iplogin();
							tblLog.setIp(SystemTool.getHostName());
							BaseService.getIploginService().save(tblLog);
							//用户密码已过期
							openAlterPassword(user,map.get("overdue").toString(),event);
						}
					}else{
						//记录登录日志
						Iplogin tblLog = new Iplogin();
						tblLog.setIp(SystemTool.getHostName());
						BaseService.getIploginService().save(tblLog);
						Messager.showWarnMessage(map.get("entitle").toString());
					}
				}else{
					//记录登录日志
					Iplogin tblLog = new Iplogin();
					tblLog.setIp(SystemTool.getHostName());
					BaseService.getIploginService().save(tblLog);
					
					Messager.showWarnMessage(map.get("forbidden").toString());
				}
			}else{
				//记录登录日志
				Iplogin tblLog = new Iplogin();
				tblLog.setIp(SystemTool.getHostName());
				BaseService.getIploginService().save(tblLog);
				
				LoginFrame.passwordErrorTimes=LoginFrame.passwordErrorTimes+1;
				if(LoginFrame.passwordErrorTimes<3){
					
					Messager.showWarnMessage((String)map.get("nullUserError"));
					passwordText.setText("");
					userNameComboBox.requestFocus();
				}else{//密码录入错误次数达到3次
					Messager.showWarnMessage("密码输入错误达到3次！");
					System.exit(1);
				}
			}
		}		
	}

	/**
	 * 打开主界面  ，关闭当前界面
	 * @param event
	 */
	private void   openMianFrameAndCloseThisFrame(ActionEvent event){
		
		//记录登录日志
		Iplogin tblLog = new Iplogin();
		Employee user = SaveUserInfo.getUser();
		if(null!=user){
			tblLog.setIp(SystemTool.getHostName());
			BaseService.getIploginService().save(tblLog);
		}
		
		// 初始化权限列表
		// 隐藏登陆窗体
		((javafx.scene.control.Control) event.getSource()).getScene().getWindow().hide();
		MainFrame mainFrame = MainFrame.getInstance();
		Stage stage = new Stage();
		try {
			mainFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
			Messager.showWarnMessage("主界面启动失败！");
		}
	}
	/**
	 * 打开修改密码界面,修改成功进入主界面
	 * @param user
	 * @param remark
	 * @param evetn
	 */
	private void openAlterPassword(Employee user,String remark,ActionEvent event){
		AlterPassword alterFrame= new AlterPassword(user,remark);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			alterFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//修改成功进入主界面
		if(AlterPassword.getType().equals("OK")){
			//保存用户信息
			SaveUserInfo.setUser(AlterPassword.getUser());
			//打开主界面  ，关闭当前界面
			openMianFrameAndCloseThisFrame(event);
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			//临检申请系统，有临检登录     权限的用户才可以登录
		
			//初始化用户名  下拉框
		 	initUserNameComboBox();
	}

	/**
	 * 初始化用户名  下拉框
	 */
	private void initUserNameComboBox() {
		List<Employee> list=BaseService.getUserService().findByPrivilegeName("客户端_登录");
		data.clear();
		if(null!=list&&list.size()>0){
			for(Employee user : list){
				if(user.getDeleted()!=1){
					data.add(user.getUserid());
				}
			}
		}
		//存放有   临检登录   权限 的用户列表
		SaveUserInfo.setClinicalTestUserList(list);
		userNameComboBox.setItems(data);
//		if(data.size()>0){
//			userNameComboBox.getSelectionModel().select(0);
//		}
		userNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					passwordText.requestFocus();
				}
			}});
	}
}
