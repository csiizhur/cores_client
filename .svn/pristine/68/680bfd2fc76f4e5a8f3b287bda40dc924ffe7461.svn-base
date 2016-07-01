package com.lanen.view.test;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;

public class SymptomObservation extends Application implements Initializable{
	
	@FXML
	private Label studyNoAndAnimalCodeLabel;
	
	@FXML
	private TableView<Tbwatchlist> tbwatchlistTbale;//症状观察tbale
	private ObservableList<Tbwatchlist> data_tbwatchlistTbale = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Tbwatchlist, String> A04Col; // // 观察时间column
	@FXML
	private TableColumn<Tbwatchlist, String> A06Col; // 症状column
	@FXML
	private TableColumn<Tbwatchlist, String> FPOSCol; // 部位column
	@FXML
	private TableColumn<Tbwatchlist, String> FDEGREECol; // 程度column
	@FXML
	private TableColumn<Tbwatchlist, String> FQOMCol; // 硬度column
	@FXML
	private TableColumn<Tbwatchlist, String> FLENCol; // 长column
	@FXML
	private TableColumn<Tbwatchlist, String> FWIDTHCol; //  宽column
	@FXML
	private TableColumn<Tbwatchlist, String> FNUMCol; // 数量column
	@FXML
	private TableColumn<Tbwatchlist, String> FCOLORCol; // 颜色column
	@FXML
	private TableColumn<Tbwatchlist, String> SYMPTOMDISAPPEARCol; // 1 症状消除column
	
	private static SymptomObservation instance;
	
	public static SymptomObservation getInstance(){
		if(null == instance){
			instance = new SymptomObservation();
		}
		return instance;
	}
	
	public SymptomObservation() {
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTbwatchlistTable();
	}
	
	public void loadData(String studyNo, String animalCode ){
		studyNoAndAnimalCodeLabel.setText("专题编号："+studyNo+"	动物号:"+animalCode);
		updateTbwatchlistTable(studyNo, animalCode);

	}
	
	/**
	 * 初始化表
	 */
	private void initTbwatchlistTable() {
		tbwatchlistTbale.setItems(data_tbwatchlistTbale);
		A04Col.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("A04"));// 观察时间column
		A06Col.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("A06")); // 症状column
		FPOSCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FPOS")); // 部位column
		FDEGREECol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FDEGREE"));  // 程度column
		FQOMCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FQOM")); // 硬度column
		FLENCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FLEN")); // 长column
		FWIDTHCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FWIDTH")) ; //  宽column
		FNUMCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FNUM")) ; // 数量column
		FCOLORCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("FCOLOR")) ;  // 颜色column
		SYMPTOMDISAPPEARCol.setCellValueFactory(new PropertyValueFactory<Tbwatchlist,String>("SYMPTOMDISAPPEAR")) ;   // 1 症状消除column
	}
	
    //更新表
	private void updateTbwatchlistTable(String studyNo, String animalCode){
		data_tbwatchlistTbale.clear();
		List<Map<String, Object>> mapList = BaseService.getInstance().getTblAnatomyCheckService().getTbWatchListByStudyNoAndAnimalCode(studyNo, animalCode);
		for(Map<String, Object> map:mapList){
			Date A04 = (Date) map.get("A04");// 观察时间
			String DateStr = null;
			if(null == A04 ){
				DateStr = "";
			}else{
				DateStr = DateUtil.dateToString(A04, "yyyy-MM-dd HH:mm");
			}
			String A06 = (String) map.get("A06");// 症状
			if(null == A06 ){
				A06 = "";
			}
			String FPOS = (String) map.get("FPOS");// 部位
			if(null == FPOS ){
				FPOS = "";
			}
			String FDEGREE = (String) map.get("FDEGREE");// 程度
			if(null == FDEGREE ){
				FDEGREE = "";
			}
			String FQOM = (String) map.get("FQOM");//硬度
			if(null == FQOM ){
				FQOM = "";
			}
			BigDecimal FLEN = (BigDecimal) map.get("FLEN");// 长
			String FLENStr = null;
			if(null == FLEN ){
				FLENStr = "";
			}else{
				FLENStr = FLEN+"";
			}
			BigDecimal FWIDTH = (BigDecimal) map.get("FWIDTH");// 宽
			String FWIDTHStr = null;
			if(null == FWIDTH ){
				FWIDTHStr = "";
			}else{
				FWIDTHStr = FWIDTH+"";
			}
			Integer FNUM = (Integer) map.get("FNUM");// 数量
			String FNUMStr = null;
			if(null == FNUM ){
				FNUMStr = "";
			}else{
				FNUMStr = FNUM+"";
			}
			String FCOLOR = (String) map.get("FCOLOR");//颜色
			if(null == FCOLOR ){
				FCOLOR = "";
			}
			String LENUNIT = (String) map.get("LENUNIT");//长单位
			if(null == LENUNIT ){
				LENUNIT = "";
			}
			String WIDTHUNIT = (String) map.get("WIDTHUNIT");//宽单位
			if(null == WIDTHUNIT ){
				WIDTHUNIT = "";
			}
			Integer SYMPTOMDISAPPEAR = (Integer) map.get("SYMPTOMDISAPPEAR");// 1 症状消除
			String pear = "";
			if(SYMPTOMDISAPPEAR == 1){
				pear = "是";
			}else{
				pear = "否";
			}
			
			String NUMUNIT = (String) map.get("NUMUNIT");// 数量单位
			if(null  == NUMUNIT){
				NUMUNIT = "";
			}
			if(!FLENStr.equals("")){
				FLENStr =  FLENStr+"("+LENUNIT+")" ;
			}
			if(!FWIDTHStr.equals("")){
				FWIDTHStr =  FWIDTHStr+"("+WIDTHUNIT+")" ;
			}
			if(!FNUMStr.equals("")){
				FNUMStr =  FNUMStr+"("+NUMUNIT+")" ;
			}
			if(!A06.equals("未见异常")){
				data_tbwatchlistTbale.add(new Tbwatchlist(DateStr, A06, FPOS, FDEGREE, FQOM,FLENStr, FWIDTHStr,FNUMStr, FCOLOR, pear));
			}
			
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("SymptomObservation.fxml"));
		Scene scene = new Scene(root, 861, 419);
		stage.setScene(scene);
		stage.setTitle("症状观察");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	public static class Tbwatchlist{
//		A01 专题编号,A02 动物号 ,A04 观察时间,A06 症状,FPOS 部位,FDEGREE 程度,FQOM硬度 ,FLEN 长 "+
//		 "     ,FWIDTH 宽,FNUM 数量,FCOLOR 颜色, "+
//		 "     ,LENUNIT 长单位,WIDTHUNIT 宽单位,SYMPTOMDISAPPEAR 1 症状消除,NUMUNIT 数量单位
		private StringProperty A01;//专题编号
		private StringProperty A02;//动物号
		private StringProperty A04;// 观察时间
		private StringProperty A06;// 症状
		private StringProperty FPOS;// 部位
		private StringProperty FDEGREE;// 程度
		private StringProperty FQOM;//硬度
		private StringProperty FLEN;// 长
		private StringProperty FWIDTH;// 宽
		private StringProperty FNUM;// 数量
		private StringProperty FCOLOR;//颜色
		private StringProperty LENUNIT;//长单位
		private StringProperty WIDTHUNIT;//宽单位
		private StringProperty SYMPTOMDISAPPEAR;// 1 症状消除
		private StringProperty NUMUNIT;// 数量单位
		
		public Tbwatchlist(String A04,String A06,String FPOS,String FDEGREE,String FQOM,String FLEN,String FWIDTH,String FNUM,String FCOLOR,String SYMPTOMDISAPPEAR ){
			this.A04 =  new  SimpleStringProperty(A04);
			this.A06 = new  SimpleStringProperty(A06);
			this.FPOS = new SimpleStringProperty(FPOS);
			
			this.FDEGREE =  new  SimpleStringProperty(FDEGREE);
			this.FQOM = new  SimpleStringProperty(FQOM);
			this.FLEN = new SimpleStringProperty(FLEN);
			
			this.FWIDTH =  new  SimpleStringProperty(FWIDTH);
			this.FNUM = new  SimpleStringProperty(FNUM);
			this.FCOLOR = new SimpleStringProperty(FCOLOR);
			this.SYMPTOMDISAPPEAR = new SimpleStringProperty(SYMPTOMDISAPPEAR);
		}
		
		
		public String getA01() {
			return A01.get();
		}
		public void setA01(String a01) {
			A01 = new SimpleStringProperty(a01);
		}
		public String getA02() {
			return A02.get();
		}
		public void setA02(String a02) {
			A02 = new SimpleStringProperty(a02);
		}
		public String getA04() {
			return A04.get();
		}
		public void setA04(String a04) {
			A04 = new SimpleStringProperty(a04);
		}
		public String getA06() {
			return A06.get();
		}
		public void setA06(String a06) {
			A06 = new SimpleStringProperty(a06);
		}
		public String getFPOS() {
			return FPOS.get();
		}
		public void setFPOS(String fPOS) {
			FPOS = new SimpleStringProperty(fPOS);
		}
		public String getFDEGREE() {
			return FDEGREE.get();
		}
		public void setFDEGREE(String fDEGREE) {
			FDEGREE = new SimpleStringProperty(fDEGREE);
		}
		public String getFQOM() {
			return FQOM.get();
		}
		public void setFQOM(String fQOM) {
			FQOM = new SimpleStringProperty(fQOM);
		}
		public String getFLEN() {
			return FLEN.get();
		}
		public void setFLEN(String fLEN) {
			FLEN = new SimpleStringProperty(fLEN);
		}
		public String getFWIDTH() {
			return FWIDTH.get();
		}
		public void setFWIDTH(String fWIDTH) {
			FWIDTH = new SimpleStringProperty(fWIDTH);
		}
		public String getFNUM() {
			return FNUM.get();
		}
		public void setFNUM(String fNUM) {
			FNUM = new SimpleStringProperty(fNUM);
		}
		public String getFCOLOR() {
			return FCOLOR.get();
		}
		public void setFCOLOR(String fCOLOR) {
			FCOLOR = new SimpleStringProperty(fCOLOR);
		}
		public String getLENUNIT() {
			return LENUNIT.get();
		}
		public void setLENUNIT(String lENUNIT) {
			LENUNIT = new SimpleStringProperty(lENUNIT);
		}
		public String getWIDTHUNIT() {
			return WIDTHUNIT.get();
		}
		public void setWIDTHUNIT(String wIDTHUNIT) {
			WIDTHUNIT = new SimpleStringProperty(wIDTHUNIT);
		}
		public String getSYMPTOMDISAPPEAR() {
			return SYMPTOMDISAPPEAR.get();
		}
		public void setSYMPTOMDISAPPEAR(String sYMPTOMDISAPPEAR) {
			SYMPTOMDISAPPEAR = new SimpleStringProperty(sYMPTOMDISAPPEAR);
		}
		public String getNUMUNIT() {
			return NUMUNIT.get();
		}
		public void setNUMUNIT(String nUMUNIT) {
			NUMUNIT = new SimpleStringProperty(nUMUNIT);
		}
		
	}
	
	
	
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

}
