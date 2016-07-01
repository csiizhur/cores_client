package com.lanen.zero.main;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;

public class LoginFrameController implements Initializable {
	@FXML
	private TextField userNameText;
	@FXML
	private PasswordField passwordText;
	
	

	@FXML
	private void onExitButtonAction(ActionEvent event){
		System.exit(0);
	}
	@FXML
	private void onEnsureButtonAction(ActionEvent event) throws ParseException{
		String userName  =userNameText.getText().toString();
		String password =passwordText.getText().toString();
		if("".equals(userName)||userName==null){
			Alert.create( "用户名不能为空！");
		}else if("".equals(password)||password==null){
			Alert.create( "密码不能为空！");
		}else{
			if("admin".equals(userName) || "system".equals(userName)){
				User user=BaseService.getUserService().findUserByUserNamePassword(userName,password);
				if(null!=user){
					if(user.getUserName().equals("admin")||user.getUserName().equals("system")){
						SaveUserInfo.setUser(user);
						//初始化权限列表
						//隐藏登陆窗体
						((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
						MainFrame mainFrame = new MainFrame();
						Stage stage =new Stage();
						try {
							mainFrame.start(stage);
						} catch (Exception e) {
							e.printStackTrace();
							Alert2.create("主界面启动失败！");
						}
					}
//						else{
//						if(user.getFlag().equals("停用")){
//							Alert2.create("账号已停用，请联系管理员！");
//						}else {
//							if(null==user.getUpdatePasswordTime()){
//								BaseService.getUserService().stopId(user.getId(),"密码最后修改时间为空");
//								Alert2.create("账号已停用，请联系管理员！");
//							}else{
//								int updatePasswordTime =DateUtil.getBetweenDays(new Date(), user.getUpdatePasswordTime());
//								if(BaseService.getRegulationService().isOverTime(updatePasswordTime)){
//									
//									BaseService.getUserService().stopId(user.getId(),"密码已经超过有效期");
//									Alert2.create("账号已停用，请联系管理员！");
//								}else{
//									
//									SaveUserInfo.setUser(user);
//									//初始化权限列表
//									//隐藏登陆窗体
//									((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
//									MainFrame mainFrame = new MainFrame();
//									Stage stage =new Stage();
//									try {
//										mainFrame.start(stage);
//									} catch (Exception e) {
//										e.printStackTrace();
//										Alert2.create("主界面启动失败！");
//									}
//								}
//							}
//							
//						}
//					}
					
				}else{
					Alert2.create("用户名或密码错误！");
//					userNameText.setText("");
					passwordText.setText("");
					userNameText.requestFocus();
				}
			}else{
				Alert2.create("用户名或密码错误！");
//				userNameText.setText("");
				passwordText.setText("");
				userNameText.requestFocus();
			}
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
