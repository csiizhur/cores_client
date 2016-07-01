package com.lanen.view.main;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.util.BackgroundRunner;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;

public class LoginPageController implements Initializable {

	@FXML   //用户名
	private ComboBox<String> userNameComboBox;
	      	//用户名下拉框的   值列表
	private ObservableList<String> data = FXCollections.observableArrayList();
	@FXML   //密码
	private PasswordField passwordText;
	@FXML
	private Button okButton ;
	@FXML
	private Button cancelButton ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			//初始化用户名  下拉框
		 	initUserNameComboBox();
	}

	/**
	 * 初始化用户名  下拉框
	 */
	private void initUserNameComboBox() {
		//下拉框 onChange事件
		userNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					passwordText.requestFocus();
				}
		}});
		 new BackgroundRunner() {
	            String prompt;
	            boolean isError;
	            boolean ok = false;

	            @Override
	            public void background() throws Exception {
	                try {
	                	//初始化service
	                    new BaseService();
	                    List<User> list=new ArrayList<User>();
	            		list  = 	BaseService.getUserService().findByPrivilegeName("检疫期管理系统登录");
	            		data.clear();
	            		if(null!=list && list.size()>0){
	            			for(User user : list){
	            				data.add(user.getUserName());
	            			}
	            		}
	            		//存放有  '检疫期管理系统登录'   权限 的用户列表
	            		SaveUserInfo.setQuarantineUserList(list);
	            		userNameComboBox.setItems(data);
	                    ok = true;
	                } catch (Exception e) {
	                    showError("连接服务器失败");
	                    e.printStackTrace();
	                    
	                }
	            }

//	            public void showPrompt(String prompt) {
//	                this.prompt = prompt;
//	                this.isError = false;
//	                runForeground();
//	            }

	            public void showError(String prompt) {
	                this.prompt = prompt;
	                this.isError = true;
	                runForeground();
	            }

	            @Override
	            public void foreground() throws Exception {
	                if (isError) {
	                    showErrorMessage(prompt);
	                } else {
	                    showMessage(prompt);
	                }
	            }

	            @Override
	            public void handleException(Exception e) throws Exception {
	                showErrorMessage(e.getLocalizedMessage());
	                e.printStackTrace();
	            }

	            @Override
	            public void finish() throws Exception {
	                if (ok) {
//	                    Main.getInstance().enterMainScene();
	                	//页面加载完毕，控件置为可用
	                	userNameComboBox.setDisable(false);
	                	if(data.size()>0){
	                		userNameComboBox.getSelectionModel().selectFirst();
	                	}
	                	passwordText.setDisable(false);
	                	okButton.setDisable(false);
	                	cancelButton.setDisable(false);
	                }
	            }
	        }.run();
	        
		
		
	}
	/**
	 * 取消按钮事件（退出窗口）
	 * @param event
	 */
	@FXML
	private void onExitButtonAction(ActionEvent event){
		System.exit(0);
	}
	
	/**
	 * 登录按钮事件
	 * @param event
	 * @throws ParseException
	 */
	@FXML
	private void onEnsureButtonAction(ActionEvent event) throws ParseException {
		String userName = userNameComboBox.getSelectionModel().getSelectedItem();
		String password = passwordText.getText().toString();
		if (null  == userName ||"".equals(userName) ) {
			Alert2.create("用户名不能为空！");
		} else if ("".equals(password) || password == null) {
			Alert2.create("密码不能为空！");
		} else {
		//			map.put("nullUserError", "");// "" 表示用 户不为空
		//			map.put("user", user);       // 用  户
		//			map.put("forbidden", "");    // "" 表示用户账号未停用
		//			map.put("entitle", "");      // "" 表示用户有权限
		//			map.put("overdue", "");      // "" 表示用户密码未过期
			Map<String,Object> map = BaseService.getUserService().checkPrivilege(userName, password,"检疫期管理系统登录");
			//判断用户是否为空
			if("".equals(map.get("nullUserError"))){
				//用户非空
				User user =(User) map.get("user");
				if("".equals(map.get("forbidden"))){
					//账户未停用
					if("".equals(map.get("entitle"))){
						//用户有权限
						if("".equals(map.get("overdue"))){
							//用户密码未过期
							//保存登录用户
							SaveUserInfo.setUser(user);
							//打开主界面  ，关闭当前界面
							openMianPage();
							
						}else{
							//记录登录日志
							TblLog tblLog = new TblLog();
							tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
							tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
							tblLog.setOperatType("登录");
							tblLog.setOperatOject(SystemMessage.getSystemFullName());
							tblLog.setOperator(user.getRealName());
							tblLog.setOperatContent("失败：账户密码已过期");
							tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
							BaseService.getTblLogService().save(tblLog);
							//用户密码已过期
							openAlterPassword(user,map.get("overdue").toString(),event);
						}
					}else{
						//记录登录日志
						TblLog tblLog = new TblLog();
						tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
						tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
						tblLog.setOperatType("登录");
						tblLog.setOperatOject(SystemMessage.getSystemFullName());
						tblLog.setOperator(user.getRealName());
						tblLog.setOperatContent("失败：账户没权限");
						tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
						BaseService.getTblLogService().save(tblLog);
						Alert2.create(map.get("entitle").toString());
					}
				}else{
					//记录登录日志
					TblLog tblLog = new TblLog();
					tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
					tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
					tblLog.setOperatType("登录");
					tblLog.setOperatOject(SystemMessage.getSystemFullName());
					tblLog.setOperator(user.getRealName());
					tblLog.setOperatContent("失败：账户已停用");
					tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
					BaseService.getTblLogService().save(tblLog);
					
					//账户已停用
					Alert2.create(map.get("forbidden").toString());
				}
			}else{
				//记录登录日志
				TblLog tblLog = new TblLog();
				tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
				tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
				tblLog.setOperatType("登录");
				tblLog.setOperatOject(SystemMessage.getSystemFullName());
				tblLog.setOperatContent("失败：用户名密码错误");
				tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
				BaseService.getTblLogService().save(tblLog);
				
				Main.passwordErrorTimes=Main.passwordErrorTimes+1;
				if(Main.passwordErrorTimes<3){
					//用户为空
					Alert2.create((String)map.get("nullUserError"));
					
					passwordText.setText("");
					userNameComboBox.requestFocus();
				}else{//密码录入错误次数达到3次
					Alert2.create("密码输入错误达到3次");
					System.exit(1);
				}
			}
		}		
	}

	/**
	 * 打开主界面(写日志)
	 * @param event
	 */
	private void   openMianPage(){
		
		//记录登录日志
		TblLog tblLog = new TblLog();
		tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
		tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
		tblLog.setOperatType("登录");
		tblLog.setOperatOject(SystemMessage.getSystemFullName());
		User user = SaveUserInfo.getUser();
		if(null!=user){
			tblLog.setOperator(user.getRealName());
			tblLog.setOperatContent("成功，id:"+user.getId()+", userName:"+user.getUserName());
			tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			BaseService.getTblLogService().save(tblLog);
		}
		
//		((javafx.scene.control.Control) event.getSource()).getScene()
//		.getWindow().hide();
		//打开切换到主界面
		Main.getInstance().enterMainScene();
	}
	/**
	 * 打开修改密码界面,修改成功进入主界面
	 * @param user
	 * @param remark
	 * @param evetn
	 */
	private void openAlterPassword(User user,String remark,ActionEvent event){
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
			
			List<User> list=	BaseService.getUserService().findByPrivilegeName("检疫期管理系统登录");
    		//存放有  '检疫期管理系统登录'   权限 的用户列表
    		SaveUserInfo.setQuarantineUserList(list);
			//关闭当前窗口
			((javafx.scene.control.Control) event.getSource()).getScene()
			.getWindow().hide();
			
			//打开主界面  
			openMianPage();
		}
	}
	public void showMessage(String msg) {
	    Alert.create(msg);
	}

	public void showErrorMessage(String msg) {
	    Alert2.create(msg);
	}
}

