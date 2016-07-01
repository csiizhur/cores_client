package com.lanen.view.balreg;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblBalCalibration;
import com.lanen.model.path.TblBalCalibrationIndex;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.test.ClassBean;

import datecontrol.DatePicker;
import datecontrol.Junit;

public class TblBalCalibrationIndexController extends Application implements Initializable{
	private Stage stage ;
	
	@FXML
	private Button addButton;                       //新增校准记录  
	@FXML
	private Button printButton;                     //打印校准记录
	@FXML
	private Button closeButon;                      //关闭窗口
	@FXML
	private Button searchButton;                    //查找按钮（OK）
	@FXML
	private ComboBox<String> balCodeCombo;                  //天平选择下拉框
	private ObservableList<String> balCodedata = FXCollections.observableArrayList();
	@FXML
	private TableView<TblBalCalibrationIndexTable> balCalibrationIndexTable;        //校准索引
	private ObservableList<TblBalCalibrationIndexTable> data_balCalibrationIndexTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> indexIdCol;        //校准索引ID
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> hostCodeCol;       //计算机编号
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> balCodeCol;        //天平编号
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> calBeginTimeCol;   //校准开始时间
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> calEndTimeCol;     //校准结束时间
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> calResultCol;      //校准结果
	@FXML
	private TableColumn<TblBalCalibrationIndexTable,String> calSignCol;        //签字确认
	@FXML
	private TableView<TblBalCalibrationTable> balCalibrationTable;             //校准记录
	private ObservableList<TblBalCalibrationTable> data_balCalibrationTable=FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblBalCalibrationTable,String> recIdCol;               //校准记录ID
	@FXML
	private TableColumn<TblBalCalibrationTable,String> calIndexIDCol;          //校准索引ID(外键)
	@FXML
	private TableColumn<TblBalCalibrationTable,String> cpCodeCol;              //砝码编号
	@FXML
	private TableColumn<TblBalCalibrationTable,String> cpWeightCol;            //砝码重量
	@FXML
	private TableColumn<TblBalCalibrationTable,String> balValueCol;            //天平读数
	@FXML
	private TableColumn<TblBalCalibrationTable,String> toleranceCol;           //偏差
	@FXML
	private TableColumn<TblBalCalibrationTable,String> toleranceUpperCol;      //允差上限
	@FXML
	private TableColumn<TblBalCalibrationTable,String> toleranceLowerCol;      //允差下限
	@FXML
	private TableColumn<TblBalCalibrationTable,String> calTimeCol;             //校准时间
	@FXML
	private TableColumn<TblBalCalibrationTable,String> resultCol;              //校准结果
	//日期查询
	@FXML
	private HBox beginHbox;
	@FXML
	private HBox endHbox;
	DatePicker  beginPane=null;  //查找条件开始日期
	DatePicker  endPane=null;    //查找条件结束日期
	
	private static TblBalCalibrationIndexController instance;
		
		
		public static TblBalCalibrationIndexController getInstance(){
			if(null == instance){
				instance = new TblBalCalibrationIndexController();
			}
			return instance;
		}
		
		public TblBalCalibrationIndexController() {
		}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TblBalCalibrationIndex.fxml"));
		Scene scene = new Scene(root, 769, 469);
		stage.setScene(scene);
		stage.setTitle("天平校准");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	public static void main(String[] args) {
			
			launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		initTblBalCalibrationIndexTable();
		initTblBalCalibrationTable();
		initDatePane();
		initBalCodeComBo();
	}
	/**关闭
	 * @param event
	 */
	@FXML
	private void onCloseBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 初始化天平校准索引表格
	 */
	public void initTblBalCalibrationIndexTable (){
		balCalibrationIndexTable.setItems(data_balCalibrationIndexTable);
		indexIdCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndexTable,String>("indexId"));
//		hostCodeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndex,String>("hostCode"));
		balCodeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndexTable,String>("balCode"));
//		calBeginTimeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndex,String>("calBeginTime"));
		calEndTimeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndexTable,String>("calEndTime"));
		calResultCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndexTable,String>("calResult"));
		calSignCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationIndexTable,String>("calSign"));
		
		balCalibrationIndexTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<TblBalCalibrationIndexTable>(){

			@Override
			public void changed(ObservableValue<? extends TblBalCalibrationIndexTable> arg0, TblBalCalibrationIndexTable arg1,
					TblBalCalibrationIndexTable newValue) {
				if(null != newValue){
//					delectBalButton.setDisable(false);
//					updateBalButton.setDisable(false);
//					int selectRow = balCalibrationIndexTable.getSelectionModel().getSelectedCells().get(0).getRow();
					int selectRow = balCalibrationIndexTable.getSelectionModel().getSelectedIndex();
					String indexId = data_balCalibrationIndexTable.get(selectRow).getIndexId();
//					Alert2.create(indexId);
					List<TblBalCalibration> list=BaseService.getInstance().getTblBalCalibrationService().loadListByCalIndexID(indexId);
//					List<TblBalCalibration> list=new ArrayList<TblBalCalibration>();
//					TblBalCalibration t=new TblBalCalibration();
//					t.setId("1");
//					t.setCpCode("001");
//					t.setCpWeight("10.00");
//					t.setBalValue("10.01");
//					t.setToleranceUpper("0.01");
//					t.setToleranceLower("0.01");
//					list.add(t);
					data_balCalibrationTable.clear();
					if(null!=list && list.size()>0){
						for(TblBalCalibration tblBalCalibration:list){
							String  id=tblBalCalibration.getId();
							String  cpCode=tblBalCalibration.getCpCode();
							String  cpWeight=tblBalCalibration.getCpWeight();
							String  balValue=tblBalCalibration.getBalValue();
							String  toleranceUpper=tblBalCalibration.getToleranceUpper();
							String  toleranceLower=tblBalCalibration.getToleranceLower();
							data_balCalibrationTable.add(new TblBalCalibrationTable(id,cpCode,cpWeight,balValue,toleranceUpper,toleranceLower,""));
						}
					}
					
				}else{
		//			delectBalButton.setDisable(true);
		//			updateBalButton.setDisable(true);
				}
			}

		});
		
    }  
	/**
	 * 更新天平校准索引
	 * @throws ParseException 
	 */
	public void updateTblBalCalibrationIndexTable() {
		data_balCalibrationIndexTable.clear();
		String beginDateStr=beginPane.getTextField().getText()+" 00:00:00";
		String endDateStr = endPane.getTextField().getText()+" 23:59:59";
//		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//		Date beginDate=sdf.parse(beginDateStr);
//		Date endDate=sdf.parse(endDateStr);
		Date beginDate=DateUtil.stringToDate(beginDateStr, "yyyy-MM-dd HH:mm:ss");
		Date endDate=DateUtil.stringToDate(endDateStr, "yyyy-MM-dd HH:mm:ss");
		
		String balCode=balCodeCombo.getValue();
		List<?>  list=BaseService.getInstance().getTblBalCalibrationIndexService().loadListByCondition(beginDate, endDate, balCode);
//		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("indexId", "00001");
//		map.put("calEndTime", "2015-02-02 14:35:12");
//		map.put("balCode", "001");
//		map.put("calSign", "张三");
//		map.put("calResult", "1");
//		list.add(map);
		if(null!=list&&list.size()>0){
			for(Object obj:list){
				Object[] objs=(Object[])obj;
				String indexId=(String)objs[0];
				SimpleDateFormat sdf2 =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
//				Date beginTime=(Date)objs[3];
//				String calBeginTime=sdf2.format(beginTime);
				Date endTime=(Date)objs[4];
				String calEndTime=sdf2.format(endTime);
				String balCode2=(String)objs[2];
				String calSign=(String)objs[6];
				String calResult=(Integer)objs[5]+"";
				data_balCalibrationIndexTable.add(new TblBalCalibrationIndexTable(indexId,balCode2,calEndTime,calSign,calResult));
			}
		}
	}
	/**新建校准索引后选择
	 * @param tblBalCalibrationIndex
	 */
	public void selectRow(TblBalCalibrationIndex tblBalCalibrationIndex){
		for(int i = 0;i<data_balCalibrationIndexTable.size();i++){
			if(data_balCalibrationIndexTable.get(i).getIndexId().equals(tblBalCalibrationIndex.getId())){
				balCalibrationIndexTable.getSelectionModel().select(i);
			}
		}
		
	}
//	/**OK按钮事件
//	 * @throws ParseException
//	 */
//	public void on_OkButton() throws ParseException{
//		updateTblBalCalibrationIndexTable();
//	}
	/**
	 * 初始化天平校准记录
	 */
	public void initTblBalCalibrationTable(){
		balCalibrationTable.setItems(data_balCalibrationTable);
//		recIdCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibration,String>("id"));
//		calIndexIDCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibration,String>("calIndexId"));
		cpCodeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("cpCode"));
		cpWeightCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("cpWeight"));
		balValueCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("balValue"));
//		toleranceCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibration,String>("tolerance"));
		toleranceUpperCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("toleranceUpper"));
		toleranceLowerCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("toleranceLower"));
		resultCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibrationTable,String>("result"));
//		calTimeCol.setCellValueFactory(new PropertyValueFactory<TblBalCalibration,String>("calTime"));
	}
	/**
	 * 加载数据
	 */
	public void loadData(){
		// TODO
		//更新天平下拉框
		updateBalCodeCombox();
		//更新天平登记所有表
		updateTblBalCalibrationIndexTable();
		
		data_balCalibrationTable.clear();
	}
	
	/**
	 * 更新天平下拉框值
	 */
	public void updateBalCodeCombox(){
		
//		String hostName=SystemTool.getHostName();
//      List<String> list=BaseService.getInstance().getTblBalRegService().getAllBalCode(hostName);
        List<String> list=BaseService.getInstance().getTblBalRegService().getAllBalCode();
        balCodedata.clear();
		if(null!=list && list.size()>0){
			for(String parit : list){
				balCodedata.add(parit);
			}
		}
		
             
	}
	
	/**
	 * 初始化天平下拉框
	 */
	public void initBalCodeComBo(){
		balCodeCombo.setItems(balCodedata);
		balCodeCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTblBalCalibrationIndexTable();
				}
			}
		});
			
	}
	/**
	 * 新建校准
	 * @param event
	 */
	@FXML
	private void onAddButton(ActionEvent event){
		try {
			Stage stage = Main.stageMap.get("AddTblBalCalibration");
			if(null == stage){
				stage =new Stage();
				stage.initOwner(Main.mainWidow);
				stage.initModality(Modality.APPLICATION_MODAL);
				AddTblBalCalibration.getInstance().start(stage);
				Main.stageMap.put("AddTblBalCalibration",stage);
			}else{
				stage.show();
			}
			AddTblBalCalibration.getInstance().loadData(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 初始化日期区间
	 */
	private void initDatePane() {
		beginPane = new DatePicker(Locale.CHINA);
		beginPane.getTextField().setEditable(false);
		beginPane.getTextField().setFocusTraversable(true);
		beginPane.getTextField().setMaxWidth(105);
		beginPane.getTextField().setMinWidth(105);
		beginPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		beginPane.getCalendarView().todayButtonTextProperty().set("今天");
		beginPane.getCalendarView().setShowWeeks(false);
		beginPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		beginPane.setMaxWidth(105);
		beginHbox.getChildren().add(beginPane);
		beginPane.getTextField().setText(DateUtil.getDateAgo(30));
		beginPane.getTextField().setFocusTraversable(false);
//		beginHbox.
		
		endPane = new DatePicker(Locale.CHINA);
		endPane.getTextField().setEditable(false);
		endPane.getTextField().setFocusTraversable(true);
		endPane.getTextField().setMaxWidth(105);
		endPane.getTextField().setMinWidth(105);
		endPane.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		endPane.getCalendarView().todayButtonTextProperty().set("今天");
		endPane.getCalendarView().setShowWeeks(false);
		endPane.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		endPane.setMaxWidth(105);
		endHbox.getChildren().add(endPane);
		endPane.getTextField().setText(DateUtil.getNow("yyyy-MM-dd"));
		endPane.getTextField().setFocusTraversable(false);
		
		beginPane.getTextField().textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTblBalCalibrationIndexTable();
				}
			}
			
		});
		endPane.getTextField().textProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateTblBalCalibrationIndexTable();
				}
			}
			
		});
	}
	
	/**
	 * 打印 按钮事件      
	 * @throws JRException 
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event) throws JRException {
		JasperReport jr = null;
        JasperPrint jp = null;
        jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("balCalibration.jrxml"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("titleName","打印天平校准记录");
        URL url = this.getClass().getResource("/image/logo.jpg");
   		map.put("logoImage", url);
   		List<ClassBean> list = new ArrayList<ClassBean>();
   		ClassBean bean = new ClassBean("1", "1", "1", "1");
   		ClassBean bean1 = new ClassBean("2", "2", "2", "2");
   		ClassBean bean2 = new ClassBean("2", "2", "2", "2");
   		ClassBean bean3 = new ClassBean("2", "2", "2", "2");
   		list.add(bean);
   		list.add(bean1);
   		list.add(bean2);
   		list.add(bean3);
   	    jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(list));     
        Main.getInstance().openJFrame(jp, "临床检验申请单");
        return;
	}
	
	/**
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
	}
	
	/**
	 * 天平校准索引
	 * @author Administrator
	 */
	public static class TblBalCalibrationIndexTable{
		private StringProperty indexId;         //20
		private StringProperty hostCode;   //计算机编号20
		private StringProperty balCode;    //天平编号20
		private StringProperty calBeginTime; //校准开始时间
		private StringProperty calEndTime;   //校准结束时间
		private StringProperty calResult;     //校准结果  		0未通过；1通过
		private StringProperty calSign;    //校准人签字20
		
		public TblBalCalibrationIndexTable(){
			super();
		}
		public TblBalCalibrationIndexTable(String indexId, String balCode,String calEndTime,String calSign,String calResult){
			this.indexId=new SimpleStringProperty(indexId);
			this.balCode=new SimpleStringProperty(balCode);
			this.calEndTime=new SimpleStringProperty(calEndTime);
			this.calSign=new SimpleStringProperty(calSign);
			String calResultStr="";
			if(null!=calResult&&calResult.equals("0")){
				calResultStr="未通过";
			}else if(null!=calResult&&calResult.equals("1")){
				calResultStr="通过";
			}
			this.calResult=new SimpleStringProperty(calResultStr);
		}
		
		public String getIndexId() {
			return indexId.get();
		}
		public void setIndexId(String id) {
			this.indexId = new SimpleStringProperty(id);
		}
		public String getHostCode() {
			return hostCode.get();
		}
		public void setHostCode(String hostCode) {
			this.hostCode = new SimpleStringProperty(hostCode);
		}
		public String getBalCode() {
			return balCode.get();
		}
		public void setBalCode(String balCode) {
			this.balCode = new SimpleStringProperty(balCode);
		}
		public String getCalBeginTime() {
			return calBeginTime.get();
		}
		public void setCalBeginTime(String calBeginTime) {
			this.calBeginTime = new SimpleStringProperty(calBeginTime);
		}
		public String getCalEndTime() {
			return calEndTime.get();
		}
		public void setCalEndTime(String calEndTime) {
			this.calEndTime = new SimpleStringProperty(calEndTime);
		}
		public String getCalResult() {
			return calResult.get();
		}
		public void setCalResult(String calResult) {
			this.calResult = new SimpleStringProperty(calResult);
		}
		public String getCalSign() {
			return calSign.get();
		}
		public void setCalSign(String calSign) {
			this.calSign = new SimpleStringProperty(calSign);
		}
		
	}
	/**
	 * 天平校准记录
	 * @author Administrator
	 */
	public static class TblBalCalibrationTable{
		private StringProperty id;              // 20
		private StringProperty calIndexId;      //校准索引id 20
		private StringProperty cpCode;          //砝码编号 20
		private StringProperty cpWeight;        //砝码重量 20
		private StringProperty balValue;        //天平读数 20
		private StringProperty tolerance;       //偏差 20
		private StringProperty toleranceUpper;  //允差上限 20
		private StringProperty toleranceLower;  //允差下限 20
		private StringProperty calTime;           //校准时间
		private StringProperty result;          //结果
		
		public TblBalCalibrationTable(){
			super();
		}
		public TblBalCalibrationTable(String id,String cpCode,String cpWeight,String balValue,String toleranceUpper,String toleranceLower,String calTime){
			this.id=new SimpleStringProperty(id);
			this.cpCode=new SimpleStringProperty(cpCode);
			this.cpWeight=new SimpleStringProperty(cpWeight);
			this.balValue=new SimpleStringProperty(balValue);
			this.toleranceUpper=new SimpleStringProperty(toleranceUpper);
			this.toleranceLower=new SimpleStringProperty(toleranceLower);
			if((Float.parseFloat(balValue)>=(Float.parseFloat(cpWeight)+Float.parseFloat(toleranceLower)))
			    && (Float.parseFloat(balValue)<=(Float.parseFloat(cpWeight)+Float.parseFloat(toleranceUpper)))){
				this.result=new SimpleStringProperty("通过");
			}else{
				this.result=new SimpleStringProperty("未通过");
			}
			this.calTime=new SimpleStringProperty(calTime);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getCalIndexId() {
			return calIndexId.get();
		}
		public void setCalIndexId(String calIndexId) {
			this.calIndexId = new SimpleStringProperty(calIndexId);
		}
		public String getCpCode() {
			return cpCode.get();
		}
		public void setCpCode(String cpCode) {
			this.cpCode = new SimpleStringProperty(cpCode);
		}
		public String getCpWeight() {
			return cpWeight.get();
		}
		public void setCpWeight(String cpWeight) {
			this.cpWeight = new SimpleStringProperty(cpWeight);
		}
		public String getBalValue() {
			return balValue.get();
		}
		public void setBalValue(String balValue) {
			this.balValue = new SimpleStringProperty(balValue);
		}
		public String getTolerance() {
			return tolerance.get();
		}
		public void setTolerance(String tolerance) {
			this.tolerance = new SimpleStringProperty(tolerance);
		}
		public String getToleranceUpper() {
			return toleranceUpper.get();
		}
		public void setToleranceUpper(String toleranceUpper) {
			this.toleranceUpper = new SimpleStringProperty(toleranceUpper);
		}
		public String getToleranceLower() {
			return toleranceLower.get();
		}
		public void setToleranceLower(String toleranceLower) {
			this.toleranceLower = new SimpleStringProperty(toleranceLower);
		}
		public String getCalTime() {
			return calTime.get();
		}
		public void setCalTime(String calTime) {
			this.calTime = new SimpleStringProperty(calTime);
		}
		public String getResult(){
			return result.get();
		}
		public void setResult(String result){
			this.result=new SimpleStringProperty(result);
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
