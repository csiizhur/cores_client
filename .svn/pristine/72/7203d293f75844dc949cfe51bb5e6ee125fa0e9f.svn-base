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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.util.DateUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.test.SymptomObservation.Tbwatchlist;

/**源发脏器肿瘤
 * @author Administrator
 *
 */
public class IndividualData extends Application implements Initializable {
	@FXML
	private Label label0;
	
	//
	@FXML
	private TableColumn<Data,String> testIndex_1;
	@FXML
	private TableColumn<Data,String> testIndexAbbr_1;
	@FXML
	private TableColumn<Data,String> testData_1;
	@FXML
	private TableColumn<Data,String> testIndexUnit_1;
	@FXML
	private TableView<Data> table_1;
	ObservableList<Data> data_table_1 = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Data,String> testIndex_2;
	@FXML
	private TableColumn<Data,String> testIndexAbbr_2;
	@FXML
	private TableColumn<Data,String> testData_2;
	@FXML
	private TableColumn<Data,String> testIndexUnit_2;
	@FXML
	private TableView<Data> table_2;
	ObservableList<Data> data_table_2 = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Data,String> testIndex_3;
	@FXML
	private TableColumn<Data,String> testIndexAbbr_3;
	@FXML
	private TableColumn<Data,String> testData_3;
	@FXML
	private TableColumn<Data,String> testIndexUnit_3;
	@FXML
	private TableView<Data> table_3;
	ObservableList<Data> data_table_3 = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Data,String> testIndex_4;
	@FXML
	private TableColumn<Data,String> testIndexAbbr_4;
	@FXML
	private TableColumn<Data,String> testData_4;
	@FXML
	private TableColumn<Data,String> testIndexUnit_4;
	@FXML
	private TableView<Data> table_4;
	ObservableList<Data> data_table_4 = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> testDateComboBox_1;		//检验日期
	ObservableList<String> data_testDateComboBox_1 = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> testDateComboBox_2;		//检验日期
	ObservableList<String> data_testDateComboBox_2 = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> testDateComboBox_3;		//检验日期
	ObservableList<String> data_testDateComboBox_3 = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> testDateComboBox_4;		//检验日期
	ObservableList<String> data_testDateComboBox_4 = FXCollections.observableArrayList();
	
	private String studyNo = null;
	private String animalCode = null;
	
	
	
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
	
	
	private static IndividualData instance;
	public static IndividualData getInstance(){
		if(null == instance){
			instance = new IndividualData();
		}
		return instance;
	}
	
	public IndividualData() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initTable();
		initComboBox();
		
		initTbwatchlistTable();
	}
	/**
	 * 初始化表
	 */
	private void initTbwatchlistTable() {
		tbwatchlistTbale.setItems(data_tbwatchlistTbale);
		tbwatchlistTbale.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
	
	private void initComboBox() {
		testDateComboBox_1.setItems(data_testDateComboBox_1);
		testDateComboBox_1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> (){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				updateTaskTable(1, newValue);
			}});
		testDateComboBox_2.setItems(data_testDateComboBox_2);
		testDateComboBox_2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> (){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				updateTaskTable(2, newValue);
			}});
		testDateComboBox_3.setItems(data_testDateComboBox_3);
		testDateComboBox_3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> (){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				updateTaskTable(3, newValue);
			}});
		testDateComboBox_4.setItems(data_testDateComboBox_4);
		testDateComboBox_4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String> (){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				updateTaskTable(4, newValue);
			}});
		
	}

	private void initTable() {
		table_1.setItems(data_table_1);
		table_1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		testIndex_1.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndex"));
		testIndexAbbr_1.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexAbbr"));
		testData_1.setCellValueFactory(new PropertyValueFactory<Data,String>("testData"));
		testIndexUnit_1.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexUnit"));
		
		testData_1.setCellFactory(new Callback<TableColumn<Data,String>,TableCell<Data,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<Data, String> call(
	    			 TableColumn<Data, String> param) {
	    		 TableCell<Data, String> cell =
	    				 new TableCell<Data, String>() {
	    			 @Override
	    			 public void updateItem(String item, boolean empty) {
	    				 super.updateItem(item, empty);
	    				 setText(empty ? null : getString());
	    				 setGraphic(null);
	    			 }
	    			 
	    			 private String getString() {
	    				 return getItem() == null ? "" : getItem().toString();
	    			 }
	    			 
	    		 };
	    		 cell.setStyle("-fx-alignment: CENTER-RIGHT;");
	    		 return cell;
	    	 }
	     });
		table_2.setItems(data_table_2);
		table_2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		testIndex_2.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndex"));
		testIndexAbbr_2.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexAbbr"));
		testData_2.setCellValueFactory(new PropertyValueFactory<Data,String>("testData"));
		testIndexUnit_2.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexUnit"));
		
		testData_2.setCellFactory(new Callback<TableColumn<Data,String>,TableCell<Data,String>>(){
			
			@Override
			public TableCell<Data, String> call(
					TableColumn<Data, String> param) {
				TableCell<Data, String> cell =
						new TableCell<Data, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER-RIGHT;");
				return cell;
			}
		});
		table_3.setItems(data_table_3);
		table_3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		testIndex_3.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndex"));
		testIndexAbbr_3.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexAbbr"));
		testData_3.setCellValueFactory(new PropertyValueFactory<Data,String>("testData"));
		testIndexUnit_3.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexUnit"));
		
		testData_3.setCellFactory(new Callback<TableColumn<Data,String>,TableCell<Data,String>>(){
			
			@Override
			public TableCell<Data, String> call(
					TableColumn<Data, String> param) {
				TableCell<Data, String> cell =
						new TableCell<Data, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER-RIGHT;");
				return cell;
			}
		});
		table_4.setItems(data_table_4);
		table_4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		testIndex_4.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndex"));
		testIndexAbbr_4.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexAbbr"));
		testData_4.setCellValueFactory(new PropertyValueFactory<Data,String>("testData"));
		testIndexUnit_4.setCellValueFactory(new PropertyValueFactory<Data,String>("testIndexUnit"));
		
		testData_4.setCellFactory(new Callback<TableColumn<Data,String>,TableCell<Data,String>>(){
			
			@Override
			public TableCell<Data, String> call(
					TableColumn<Data, String> param) {
				TableCell<Data, String> cell =
						new TableCell<Data, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}
					
					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
					
				};
				cell.setStyle("-fx-alignment: CENTER-RIGHT;");
				return cell;
			}
		});
		
	}

	
	
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String animalCode) {
		this.studyNo  = studyNo;
		this.animalCode  = animalCode;
		
		updateLabel();
		
		updateTestDateComboBox();
		
		updateTbwatchlistTable(studyNo, animalCode);
	}

	
	/**
	 * 更新解剖所见
	 */
	private void updateLabel() {
		label0.setWrapText(true);//自动换行
		label0.setText("");
		String msg = "";
		
		if(null != animalCode && !"".equals(animalCode)){
			List<TblAnatomyCheck> tblAnatomyCheckList = BaseService.getInstance()
					.getTblAnatomyCheckService().getListByStudyNoAnimalCode(studyNo,animalCode);
			if(null != tblAnatomyCheckList){
				for(TblAnatomyCheck tblAnatomyCheck:tblAnatomyCheckList){
					String visceraName = tblAnatomyCheck.getVisceraName();
					String subVisceraName = tblAnatomyCheck.getSubVisceraName();
					if(null != subVisceraName && !"".equals(subVisceraName)){
						visceraName = subVisceraName;
					}
					String finding = "";
					int autolyzaFlag =  tblAnatomyCheck.getAutolyzaFlag();         //自溶标识       0:否     1:是
					if(autolyzaFlag == 1){
						finding = "自溶";
					}else if(autolyzaFlag == 2){
						String missingRsn = BaseService.getInstance().getTblAnatomyCheckService().getMissRsnByAnatomyCheckId(tblAnatomyCheck.getId());
						if(null !=tblAnatomyCheck.getAnatomyFingding() && null != missingRsn && !"".equals(missingRsn)){
							finding = tblAnatomyCheck.getAnatomyFingding()+":"+missingRsn;
						}else{
							finding = tblAnatomyCheck.getAnatomyFingding();
						}
					}else{
						finding = finding +(isNull(tblAnatomyCheck.getBodySurfacePos()) ? 
								"" :tblAnatomyCheck.getBodySurfacePos()+" "); //体表部位60
						finding = finding + (isNull(tblAnatomyCheck.getAnatomyPos()) ? 
								"" :tblAnatomyCheck.getAnatomyPos()+" ");//解剖学所见部位60
						finding = finding +(isNull(tblAnatomyCheck.getPos()) ? 
								"" :tblAnatomyCheck.getPos()+" ");//位置60
						finding = finding +(isNull(tblAnatomyCheck.getNumber()) ? 
								"" :tblAnatomyCheck.getNumber()+" "); //数量60
						finding = finding +(isNull(tblAnatomyCheck.getRange()) ? 
								"" :tblAnatomyCheck.getRange()+" ");//范围60
						finding = finding +(isNull(tblAnatomyCheck.getSize()) ? 
								"" :tblAnatomyCheck.getSize()+" ");//大小 20
						finding = finding +(isNull(tblAnatomyCheck.getColor()) ? 
								"" :tblAnatomyCheck.getColor()+" ");//颜色60
						finding = finding +(isNull(tblAnatomyCheck.getTexture()) ? 
								"" :tblAnatomyCheck.getTexture()+" ");//硬度60
						finding = finding +(isNull(tblAnatomyCheck.getShape()) ? 
								"" :tblAnatomyCheck.getShape()+" ");//形状60
						finding = finding +(isNull(tblAnatomyCheck.getAnatomyFingding()) ? 
								"" :tblAnatomyCheck.getAnatomyFingding()+" ");  //通用/特殊所见标识     1:通用   2:特殊
						
						finding = finding +(isNull(tblAnatomyCheck.getLesionDegree()) ? 
								"" :tblAnatomyCheck.getLesionDegree()+" ");//病变程度60
					}
					
					
					if(null != visceraName && !"".equals(visceraName)){
						finding = visceraName+" "+finding;
					}
					if(msg.equals("")){
						msg = finding;
					}else{
						msg = msg+"，"+finding;
					}
				}
			}
		}
		if(msg.equals("")){
			msg = "未见异常";
		}else{
			msg = msg +"；";
		}
		label0.setText(msg);
	}

	/**
	 * @return
	 */
	private boolean isNull(String str){
		if(null == str){
			return true;
		}else if("".equals(str.trim())){
			return true;
		}
		return false;
	}
	/**
	 * 更新检测日期ComboBox
	 */
	private void updateTestDateComboBox() {
		data_testDateComboBox_1.clear();
		data_testDateComboBox_2.clear();
		data_testDateComboBox_3.clear();
		data_testDateComboBox_4.clear();
		List<Map<String,Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
				.getClinicalDataByStudyNoAnimalCodeTestItemTestDate(studyNo, animalCode);
		if(null != maplist && maplist.size() > 0){
			for(Map<String, Object> obj:maplist){
					Integer testItem = (Integer) obj.get("testItem");
					String testDate = (String) obj.get("testDate");
					switch (testItem) {
						case 1:
							data_testDateComboBox_1.add(testDate);
							break;
						case 2:
							data_testDateComboBox_2.add(testDate);
							break;
						case 3:
							data_testDateComboBox_3.add(testDate);
							break;
						case 4:
							data_testDateComboBox_4.add(testDate);
							break;
	
						default:
							break;
					}
			}
			
			testDateComboBox_1.getSelectionModel().selectLast();
			testDateComboBox_2.getSelectionModel().selectLast();
			testDateComboBox_3.getSelectionModel().selectLast();
			testDateComboBox_4.getSelectionModel().selectLast();
		}
		
	}

	/**更新表格数据
	 * @param testItem
	 * @param testDate    yyyy-MM-dd
	 */
	private void updateTaskTable(Integer testItem ,String testDate) {
		ObservableList<Data> data_table = null;
		switch (testItem) {
			case 1:data_table_1.clear();data_table = data_table_1;break;
			case 2:data_table_2.clear();data_table = data_table_2;break;
			case 3:data_table_3.clear();data_table = data_table_3;break;
			case 4:data_table_4.clear();data_table = data_table_4;break;
			default:
				break;
		}
		if(null != testDate && !"".equals(testDate)){
			List<Map<String,Object>> maplist = BaseService.getInstance().getTblHistopathCheckService()
					.getClinicalDataByStudyNoAnimalCodeTestItemTestDate(studyNo,animalCode,testItem,testDate);
			if(null != maplist && maplist.size() > 0){
				for(Map<String, Object> obj:maplist){
//				private StringProperty testIndex;
//				private StringProperty testIndexAbbr;
//				private StringProperty testData;
//				private StringProperty testIndexUnit;
					String testIndex = (String) obj.get("testIndex");
					String testIndexAbbr = (String) obj.get("testIndexAbbr");
					String testData = (String) obj.get("testData");
					String testIndexUnit = (String) obj.get("testIndexUnit");
					data_table.add(new Data(testIndex,testIndexAbbr,testData,testIndexUnit));
				}
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("IndividualData.fxml"));
		Scene scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.setTitle("个体数据");
//		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setMinWidth(1024);
		stage.setMinHeight(600);
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight()); 
		stage.setMaxHeight(bounds.getMaxY());
		stage.setMaxWidth(bounds.getMaxX());
		stage.show();
		
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	/**检验数据
	 *
	 */
	public class Data{
		
		private StringProperty testIndex;
		private StringProperty testIndexAbbr;
		private StringProperty testData;
		private StringProperty testIndexUnit;
		
		public Data(String testIndex,String testIndexAbbr,String testData,String testIndexUnit){
			setTestIndex(testIndex);
			setTestIndexAbbr(testIndexAbbr);
			setTestData(testData);
			setTestIndexUnit(testIndexUnit);
		}

		public String getTestIndex() {
			return testIndex.get();
		}

		public void setTestIndex(String testIndex) {
			this.testIndex = new SimpleStringProperty(testIndex);
		}

		public String getTestIndexAbbr() {
			return testIndexAbbr.get();
		}

		public void setTestIndexAbbr(String testIndexAbbr) {
			this.testIndexAbbr = new SimpleStringProperty(testIndexAbbr);;
		}

		public String getTestData() {
			return testData.get();
		}

		public void setTestData(String testData) {
			this.testData = new SimpleStringProperty(testData);;
		}

		public String getTestIndexUnit() {
			return testIndexUnit.get();
		}

		public void setTestIndexUnit(String testIndexUnit) {
			this.testIndexUnit = new SimpleStringProperty(testIndexUnit);;
		}
		
	}

}
