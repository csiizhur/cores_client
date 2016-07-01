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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;

/**症状观察
 * @author Administrator
 *
 */
public class WatchPage extends Application implements Initializable {
	
	
	
	@FXML
	private ListView<String> listview;
	private ObservableList<String> data_listview = FXCollections.observableArrayList();
	
	@FXML
	private TableView<WatchResult> table;
	ObservableList<WatchResult> data_table = FXCollections.observableArrayList();
	ObservableList<WatchResult> data_table2 = FXCollections.observableArrayList();
	@FXML
	private TableColumn<WatchResult,String> resultCol;
	@FXML
	private TableColumn<WatchResult,String> dateCol;
	//返回哪里去，执行哪里的方法
	private String toWhere = "";;
	
	
	private static WatchPage instance;
	public static WatchPage getInstance(){
		if(null == instance){
			instance = new WatchPage();
		}
		return instance;
	}
	
	public WatchPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		initListView();
		
		initTable();
	}
	
	/**
	 * 初始化 Table
	 */
	private void initTable() {
		table.setItems(data_table2);
		
		resultCol.setCellValueFactory(new PropertyValueFactory<WatchResult,String>("result"));
		dateCol.setCellValueFactory(new PropertyValueFactory<WatchResult,String>("date"));
		table.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					selectDateAndClosePage();
				}
				
			}

		});
	}
	/**
	 * 选择日期并关闭窗口
	 */
	private void selectDateAndClosePage() {
		
		WatchResult selectedItem = table.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			
			if("HistopathCheckPage".equals(toWhere)){
				HistopathCheckPage.getInstance().setOccurDate(selectedItem.getDate());
			}else if("HistopathCheckEdit".equals(toWhere)){
				HistopathCheckEdit.getInstance().setOccurDate(selectedItem.getDate());
			}
			
			table.getScene().getWindow().hide();
		}
	}

	/**
	 * 初始化listview
	 */
	private void initListView(){
		listview.setItems(data_listview);
		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				System.out.println(newValue);
				updateTable(newValue);
			}
		});
	}
	
	/**更新表格
	 * @param studyNo
	 * @param animalCode
	 */
	private void updateListAndTable(String studyNo,String animalCode){
		data_table.clear();
		data_table2.clear();
		data_listview.clear();
		List<Map<String, Object>> mapList = BaseService.getInstance().getTblAnatomyCheckService().getTbWatchListByStudyNoAndAnimalCode(studyNo, animalCode);
		for(Map<String, Object> map:mapList){
			String a06 = (String) map.get("A06");// 症状
			if(null != a06 && !"".equals(a06) && !"未见异常".equals(a06)){
				
				
				Date date = (Date) map.get("A04");// 观察时间
				String dateStr = null;
				if(null == date ){
					dateStr = "";
				}else{
					dateStr = DateUtil.dateToString(date, "yyyy-MM-dd");
				}
				String fpos = (String) map.get("FPOS");// 部位
				if(null == fpos ){
					fpos = "";
				}
				if(!data_listview.contains(fpos)){
					data_listview.add(fpos);
				}
				String fdegree = (String) map.get("FDEGREE");// 程度
				if(null == fdegree ){
					fdegree = "";
				}
				String fqom = (String) map.get("FQOM");//硬度
				if(null == fqom ){
					fqom = "";
				}
				BigDecimal FLEN = (BigDecimal) map.get("FLEN");// 长
				String flen = null;
				if(null == FLEN ){
					flen = "";
				}else{
					flen = FLEN+"";
				}
				BigDecimal FWIDTH = (BigDecimal) map.get("FWIDTH");// 宽
				String fwidth = null;
				if(null == FWIDTH ){
					fwidth = "";
				}else{
					fwidth = FWIDTH+"";
				}
				Integer fnum = (Integer) map.get("FNUM");// 数量
				
				String fcolor = (String) map.get("FCOLOR");//颜色
				if(null == fcolor ){
					fcolor = "";
				}
				String lenunit = (String) map.get("LENUNIT");//长单位
				if(null == lenunit ){
					lenunit = "";
				}
				String widthunit = (String) map.get("WIDTHUNIT");//宽单位
				if(null == widthunit ){
					widthunit = "";
				}
				Integer symptomdidappear = (Integer) map.get("SYMPTOMDISAPPEAR");// 1 症状消除
				
				String numunit = (String) map.get("NUMUNIT");// 数量单位
				if(null  == numunit){
					numunit = "";
				}
				String watchresult = "";
				watchresult = watchresult+ fpos+a06;
				if(null !=symptomdidappear && symptomdidappear == 1 ){
					watchresult = watchresult+"消失";
				}
				watchresult = watchresult+ fdegree+fqom;
				if(!"".equals(flen) && !"".equals(fwidth) 
						&& !"".equals(lenunit) && !"".equals(widthunit)){
					watchresult = watchresult+flen+lenunit+"*"+fwidth+widthunit;
				}
				if(null != fnum && fnum > 0 && !"".equals(numunit)){
					watchresult = watchresult+fnum+numunit;
				}
				watchresult = watchresult+fcolor;
				
				data_table.add(new WatchResult(fpos, watchresult, dateStr));
			}
			
		}
		if(data_listview.size() > 0){
			listview.getSelectionModel().select(0);
		}
	}
	private void updateTable(String fpos){
		data_table2.clear();
		if(null != fpos){
			for(WatchResult obj:data_table){
				if(fpos.equals(obj.getFpos())){
//					data_table2.add(new WatchResult(obj.getFpos(),obj.getResult(),obj.getDate()));
					data_table2.add(obj);
				}
			}
		}
	}
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		WatchResult selectedItem = table.getSelectionModel().getSelectedItem();
		if(null != selectedItem){
			selectDateAndClosePage();
		}else{
			showErrorMessage("请选择观察结果数据！");
		}
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo ,String animalCode,String toWhere) {
		this.toWhere = toWhere;
		
		updateListAndTable(studyNo,animalCode);
//		updateListAndTable("2013-030-01-14","2128");
	}

	public class WatchResult{
		private StringProperty fpos;
		private StringProperty result;
		private StringProperty date;
		
		public WatchResult(String fpos,String result,String date){
			setFpos(fpos);
			setResult(result);
			setDate(date);
		}
		
		public String getFpos() {
			return fpos.get();
		}
		public void setFpos(String fpos) {
			this.fpos = new SimpleStringProperty(fpos);
		}
		public String getResult() {
			return result.get();
		}
		public void setResult(String result) {
			this.result = new SimpleStringProperty(result);
		}
		public String getDate() {
			return date.get();
		}
		public void setDate(String date) {
			this.date = new SimpleStringProperty(date);
		}
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("WatchPage.fxml"));
		Scene scene = new Scene(root, 591, 419);
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
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}

}
