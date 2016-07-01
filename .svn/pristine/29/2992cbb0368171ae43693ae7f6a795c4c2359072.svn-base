package com.lanen.util.messager;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.lanen.base.BaseService;
import com.lanen.util.DateUtil;

import datecontrol.DatePicker;
import datecontrol.Junit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChooseOneDate extends Application implements Initializable {
	
	@FXML
	private HBox chooseDateVbox;
	DatePicker  chooseDatePicker = null;	//动物死亡日期
	Date chooseDate = null;
	boolean isOk = false;
	
	/**
	 * 确定
	 * @param event
	 */
	@FXML
	private void onOkBtnAction(ActionEvent event){
		
		String deadDateStr = chooseDatePicker.getTextField().getText();
		if(null == deadDateStr || "".equals(deadDateStr)){
			
			chooseDate = null;
		}else{
			chooseDate = DateUtil.stringToDate(deadDateStr, "yyyy-MM-dd");
		}
		isOk = true;
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	private static ChooseOneDate instance;
	public static ChooseOneDate getInstance(){
		if(null == instance){
			instance = new ChooseOneDate();
		}
		return instance;
	}
	
	public void loadData(){
		isOk = false;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initDeadDate();
	}
	
	/**
	 * 初始化动物信息-死亡日期
	 */
	private void initDeadDate() {
		chooseDatePicker = new DatePicker(Locale.CHINA);
		chooseDatePicker.getTextField().setEditable(false);
		chooseDatePicker.getTextField().setFocusTraversable(true);
		chooseDatePicker.getTextField().setMaxWidth(100);
		chooseDatePicker.getTextField().setMinWidth(100);
		chooseDatePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		chooseDatePicker.getCalendarView().todayButtonTextProperty().set("今天");
		chooseDatePicker.getCalendarView().setShowWeeks(false);
		chooseDatePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		chooseDatePicker.setMaxWidth(100);
		chooseDateVbox.getChildren().add(chooseDatePicker);
		chooseDatePicker.getTextField().setFocusTraversable(false);
		
		Date date = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		chooseDatePicker.getTextField().setText(DateUtil.dateToString(calendar.getTime(), "yyyy-MM-dd"));
		
	}

	/*public void loadData(String message){
		pass = false;
		
		
	}*/
	
	@Override
	public  void start(final Stage stage) throws Exception {
		Parent root =FXMLLoader.load(getClass().getResource("ChooseOneDate.fxml"));
		/*stage.addEventFilter(KeyEvent.KEY_PRESSED, (new EventHandler<KeyEvent>(){
		    @Override
		    public void handle(KeyEvent event) {
		     if(event.getCode()==KeyCode.Y){
		    	 pass=true;
		    	 stage.hide();
		     }else if(event.getCode()==KeyCode.N){
		    	 pass=false;
		    	 stage.hide();
		     }
		    }
		   }));*/
		stage.setScene(new Scene(root,300,100));
		stage.initStyle(StageStyle.DECORATED);
		stage.setTitle("选择日期");
		stage.setResizable(false);
		stage.setIconified(false);
	}
	
	public Date getChooseDate() {
		return chooseDate;
	}
	
	public boolean getIsOKClick() {
		return isOk;
	}


}
