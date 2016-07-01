package com.lanen.zero.main;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.ClinicalTestDataForReport;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanCollectionDataSource;
import com.lanen.util.NumberUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;

public class ChoosePrintFrame extends Application implements Initializable{
	
	//检测项目
	@FXML
	private ComboBox<String> testItemComboBox;
	//标本接收日期
	@FXML
	private ComboBox<String> specimenRecDateComboBox;
	private static ObservableList<String> data_specimenRecDateComboBox = 
			FXCollections.observableArrayList();
	//检测日期
	@FXML
	private ComboBox<String> testDateComboBox;
	private static ObservableList<String> data_testDateComboBox = 
			FXCollections.observableArrayList();
	// 动物信息表
	@FXML
	private TableView<Animal> animalTable;
	@FXML
	private TableColumn<Animal, Boolean> selectCol;
	@FXML
	private TableColumn<Animal, String> animalIdCol;
	@FXML
	private TableColumn<Animal, String> animalCodeCol;
	@FXML
	private TableColumn<Animal, String> genderCol;
	@FXML
	private TableColumn<Animal, String> specimenCodeCol;
	private static ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();

	private static ChoosePrintFrame instance;
	
	private JFrame jFrame=null;
	private JRViewer view=null;
	/**存放报表中的表格数据*/
	List<ClinicalTestDataForReport> reportList = null;
	/**参数*/
	Map<String,Object> map = null;
	/**检测结果     检验编号、动物号、性别、动物Id号 ,检验指标缩写，检测结果，排序aniSerialNum */
	List<?> testDataList  =null;
	private static String studyNo="";
	private static String reqNo="";
	private  int testItem = 0;
	private String testDate ="";
	private String specimenRecDate ="";
	public static ChoosePrintFrame getInstance(){
		if(null == instance){
			instance = new ChoosePrintFrame();
		}
		return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initAnimalTable();
		//初始化testDateComboBox
		initTestDateComboBox();
		//初始化  specimenRecDateComboBox
		initSpecimenRecDateComboBox();
		//检测项目ComboBox
		initTestItemComboBox();
	}
	/**
	 * 初始化testDateComboBox
	 */
	private void initTestDateComboBox() {
		testDateComboBox.setItems(data_testDateComboBox);
		testDateComboBox.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
					testDate = newValue;
					//更新动物列表
					updateAnimalTable();
				}
				
			});
	}
	/**
	 * 更新检测日期ComboBox
	 */
	private void updateTestDateComboBox(){
		data_testDateComboBox.clear();
		if(testItem == 0 || null == specimenRecDate || specimenRecDate.isEmpty() ){
			return ;
		}
		List<String> dateStrList = BaseService
				.getTblClinicalTestDataService().getTestDateStrList(studyNo,reqNo,testItem,specimenRecDate);
		if(null != dateStrList && dateStrList.size()>0){
			for(String dateStr:dateStrList){
				data_testDateComboBox.add(dateStr);
			}
			testDateComboBox.getSelectionModel().select(0);
		}
	}
	
	/**
	 * 初始化  specimenRecDateComboBox
	 */
	private void initSpecimenRecDateComboBox() {
		specimenRecDateComboBox.setItems(data_specimenRecDateComboBox);
		specimenRecDateComboBox.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
					specimenRecDate =newValue;
					//更新检测日期ComboBox
					updateTestDateComboBox();
				}
				
			});
	}
	/**更新specimenRecDateComboBox
	 * 
	 */
	private void updateSpecimenRecDateComboBox(){
		data_specimenRecDateComboBox.clear();
		if(testItem >0  ){
			List<String> specimenRecDateStrList = BaseService.getTblSpecimenService()
					.getRecDateStrList(studyNo,reqNo,testItem);
			if(null != specimenRecDateStrList && specimenRecDateStrList.size()>0){
				for(String dateStr:specimenRecDateStrList){
					data_specimenRecDateComboBox.add(dateStr);
				}
				specimenRecDateComboBox.getSelectionModel().select(0);
			}
		}
	}
	/**
	 * 初始化检测项目 testItemComboBox
	 */
	private void initTestItemComboBox(){
		testItemComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
					if(newValue.intValue()>-1){
						testItem = newValue.intValue()+1;
					}else{
						testItem = 0;
					}
					updateSpecimenRecDateComboBox();
					
				}});
	}
	
	/**
	 * 更新动物列表
	 */
	private void updateAnimalTable(){
		data_animalTable.clear();
		
		if(testItem == 0 || null == testDate || testDate.isEmpty() 
				|| specimenRecDate == null || specimenRecDate.isEmpty()){
			return;
		}
		
		List<?> list = BaseService.getTblClinicalTestDataService()
				.get4StrList(studyNo,reqNo,testItem,specimenRecDate,testDate);
		
		if(null != list){
			for(Object obj :list){
				Object[] objs =(Object[]) obj;
				String specimenCode = (String) objs[0];
				String animalCode = (String) objs[1];
				Integer gender = (Integer) objs[2];
				String sex ="";
				if(gender == 1){
					sex ="♂";
				}else if(gender == 2){
					sex ="♀";
				}
				String animalId = (String) objs[3];
				data_animalTable.add(new Animal(true,animalCode,sex,animalId,specimenCode));
			}
		}

	}
	
	/**
	 * 初始化动物表格  animalTable
	 */
	private void initAnimalTable(){
		data_animalTable.clear();
		selectCol.setCellValueFactory(new PropertyValueFactory<Animal,Boolean>("select"));
		selectCol.setCellFactory(new Callback<TableColumn<Animal,Boolean>,TableCell<Animal,Boolean>>(){

			@Override
			public TableCell<Animal, Boolean> call(TableColumn<Animal, Boolean> p) {
				return new CheckBoxTableCell_1<Animal,Boolean>();
			}});
		animalIdCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalId"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		specimenCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("specimenCode"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("sex"));
		genderCol
		.setCellFactory(new Callback<TableColumn<Animal, String>, TableCell<Animal, String>>() {

			@Override
			public TableCell<Animal, String> call(
					TableColumn<Animal, String> arg0) {
				TableCell<Animal, String> cell = new TableCell<Animal, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}

					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}

				};
				cell.setStyle("-fx-alignment: CENTER;");
				return cell;
			}

		});
		animalTable.setItems(data_animalTable);
		animalTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//单击
				if(event.getButton()==MouseButton.PRIMARY){
					Animal currentSelectItem = animalTable.getSelectionModel().getSelectedItem();
					if(null != currentSelectItem){
						Animal index = new Animal(!currentSelectItem.getSelect(),currentSelectItem.getAnimalCode(),
								currentSelectItem.getSex(),currentSelectItem.getAnimalId(),currentSelectItem.getSpecimenCode());
						animalTable.getItems().set(animalTable.getItems().indexOf(currentSelectItem), index);
						animalTable.getSelectionModel().clearSelection();
					}
				}
				
			}});
	}
	
	

	/**全选
	 * @param event
	 */
	@FXML
	private void onAllSelect2(ActionEvent event){
		int i = 0;
		for(Animal obj :data_animalTable){
			data_animalTable.set(i, new Animal(true,obj.getAnimalCode(),obj.getSex(),obj.getAnimalId(),obj.getSpecimenCode()));
			i++;
		}
	}
	/**全不选
	 * @param event
	 */
	@FXML
	private void onNoneSelect2(ActionEvent event){
		int i = 0;
		for(Animal obj :data_animalTable){
			data_animalTable.set(i, new Animal(false,obj.getAnimalCode(),obj.getSex(),obj.getAnimalId(),obj.getSpecimenCode()));
			i++;
		}
	}
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirm(ActionEvent event){
		
		/**选择的 动物Id号列表*/
		List<String> animalIdList = new ArrayList<String>();
		for(Animal obj :data_animalTable){
			if(obj.getSelect())
				animalIdList.add(obj.getAnimalId());
		}
		if(animalIdList.size()<1){
//			Alert2.create("请先选择动物列表");
			Messager.showWarnMessage("请先选择动物列表！");
			return ;
		}
		
		TblStudyPlan tblStudyPlan = BaseService
				.getTblStudyPlanService().getById(studyNo);
		int reqNoInt = Integer.parseInt(reqNo);
		TblClinicalTestReq tblClinicalTestReq =BaseService
				.getTblClinicalTestReqService().findByStudyNoAndReqNO(studyNo, reqNoInt);
		
		reportList=new ArrayList<ClinicalTestDataForReport>();
		
		testDataList = BaseService.getTblClinicalTestDataService()
				.get9StrListByStudyNoReqNoTestItemAnimalIdList(studyNo,reqNo,testItem,specimenRecDate,testDate,animalIdList);
		
		/**字典，指标缩写，  排序*/
		Map<String,Integer> dictMap=null;
		/**字典，指标缩写， 单位*/
		Map<String,String> dictUnitMap=null;
		
		/**临检申请单-检验指标 缩写列表（报表中 表格里的列）*/
		List<String> indexAbbrList=new ArrayList<String>();
		/**临检申请单-检验指标   列表**/
		List<TblClinicalTestReqIndex> tblClinicalTestReqIndexList =BaseService.getTblClinicalTestReqService()//
				.getReqIndexByReqNo(tblStudyPlan, reqNoInt);
		if(null==tblClinicalTestReqIndexList ||tblClinicalTestReqIndexList.size()<1){
//			Alert2.create("临检申请里没检测指标");
			Messager.showWarnMessage("临检申请里没检测指标！");
			return;
		}
		for(TblClinicalTestReqIndex obj:tblClinicalTestReqIndexList){
			if(testItem==obj.getTestitem()){
				indexAbbrList.add(obj.getTestIndexAbbr());
			}
		}
		//参数
		map = new HashMap<String,Object>();
//		InputStream logoImage =  getClass().getResourceAsStream("/image/clinicaltest/logo.jpg");
		URL logoImage = getClass().getResource("/image/clinicaltest/logo.jpg");
		map.put("logoImage", logoImage);
		String number = BaseService.getDictReportNumberService().getNumberByReportName("检验报告单");
        map.put("number", number == null ? "":number);
		map.put("studyNo", studyNo);
		map.put("sd", tblStudyPlan.getStudydirector());
		String animalType = tblStudyPlan.getAnimalType();
		String animalStrain = tblStudyPlan.getAnimalStrain();
		if(null != animalStrain && null != animalType ){
			if(animalStrain.contains(animalType.trim())){
				map.put("animalStrain", animalStrain);
			}else {
				map.put("animalStrain", animalType+" "+animalStrain);
			}
		}else{
			map.put("animalStrain", animalType);
		}
//		map.put("animalStrain", tblStudyPlan.getAnimalStrain());
		
		map.put("specimenRecDate", specimenRecDate);
		map.put("testDate", testDate);
		switch (testItem) {
		case 1:	//血液生化	
			dictMap=BaseService.getDictBioChemService().getMap();
			dictUnitMap=BaseService.getDictBioChemService().geUnittMap();
			break;
		case 2:	//血常规
			dictMap=BaseService.getDictHematService().getMap();
			dictUnitMap=BaseService.getDictHematService().geUnittMap();
			break;
		case 3://血凝
			dictMap=BaseService.getDictBloodCoagService().getMap();
			dictUnitMap=BaseService.getDictBloodCoagService().geUnittMap();
			break;
		case 4:	//尿液
			dictMap=BaseService.getDictUrineService().getMap(); 
			dictUnitMap=BaseService.getDictUrineService().geUnittMap();
			break;
		default:
			break;
		}
		boolean isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(tblStudyPlan.getAnimalType());
		int i=0;
		Set<String> specimenKindSet = new HashSet<String>();
		if(testDataList.size()>0){
			String animalCode = null;
			String animalId="";
			for(Object obj:testDataList){
				Object[] objs = (Object[]) obj;
				String abbr=(String) objs[4];
				if(null !=objs[7])
					specimenKindSet.add((String)objs[7]);
				
				if(dictMap.keySet().contains(abbr)){//老数据与新指标的冲突，所以检测指标最好别编辑修改，增加可以
					int orderNo=dictMap.get(abbr);
					//报表  表格里的数据
					ClinicalTestDataForReport entity= new ClinicalTestDataForReport();
					animalCode = (String) objs[1];
					 if(!isBigAnimal && null != animalCode && !"".equals(animalCode) ){
						 entity.setAnimalId("NA");
					 }else{
						 //     动物Id号
						 entity.setAnimalId((String)objs[3]);
					 }
					entity.setTestData((String)objs[5]);
					entity.setSpecimenCode((String)objs[0]);
					entity.setGender((Integer)objs[2]);
					
					//  动物编号(空  则动物序号，非空加0000 )排序要用
					if(null==objs[1] || "".equals(objs[1])){
						entity.setAnimalCode(NumberUtil.integerToString((Integer)objs[6])+"NA");
					}else{
						entity.setAnimalCode("0000"+objs[1]);
					}
					//例如    0001+Fe(交叉报表再截取掉0001)+单位
					String indexUnit = dictUnitMap.get(abbr);//单位
					if(null!=indexUnit && !"".equals(indexUnit)){
//						entity.setTestIndexAbbr(NumberUtil.integerToString(orderNo)+abbr+"\n("+objs[8]+")");
						entity.setTestIndexAbbr(NumberUtil.integerToString(orderNo)+abbr+"\n("+indexUnit+")");
					}else{
						entity.setTestIndexAbbr(NumberUtil.integerToString(orderNo)+abbr);
					}
					reportList.add(entity);
					
					//有数据的检测指标缩写移除
					if(indexAbbrList.contains(abbr)){
						indexAbbrList.remove(abbr);
					}
					
					//判断是一个动物还是多个，一个返回动物Id号，多个返回“”
					if(i==0){
						animalId=(String)objs[3];
					}else if(i>0){
						if(!animalId.equals(objs[3])){
							i=-1;
							animalId="";
						}
					}
					i++;
				}
			}
		} else {// 没数据
//			Alert2.create("没有打印数据");
			Messager.showWarnMessage("没有打印数据！");
			return;
		}
		//临检申请单-检验指标     中有的指标，数据中没有，显示在表格中
		if(indexAbbrList.size()>0){
			//  动物Id号
			String animalId=(String) ((Object[])testDataList.get(0))[3];
			// 动物编号(空  加动物序号，非空加0000)
			String animalCode=(String) ((Object[])testDataList.get(0))[3];
			Integer gender = (Integer) ((Object[])testDataList.get(0))[2];
			String specimenCode = (String) ((Object[])testDataList.get(0))[0];
			if(!isBigAnimal && null != animalCode && !"".equals(animalCode) ){
				animalId = "NA";
			}
			
			
			if(null==((Object[])testDataList.get(0))[1] || "".equals(((Object[])testDataList.get(0))[1])){
				animalCode=NumberUtil.integerToString((Integer)((Object[])testDataList.get(0))[6])+"NA";
			}else{
				animalCode="0000"+((Object[])testDataList.get(0))[1];
			}
			
			for(String abbr:indexAbbrList){
//				int orderNo=dictMap.get(abbr);//出现过空指针异常
				int orderNo=100;
				if(null!=dictMap.get(abbr)){
					orderNo=dictMap.get(abbr);
				}
				//报表  表格里的数据
				ClinicalTestDataForReport entity= new ClinicalTestDataForReport();
				
				entity.setAnimalId(animalId);
				entity.setAnimalCode(animalCode);
				entity.setSpecimenCode(specimenCode);
				entity.setGender(gender);
				entity.setTestData("NA");
				//例如    0001+Fe(交叉报表再截取掉0001)
				if(null!=dictUnitMap.get(abbr) && !"".equals(dictUnitMap.get(abbr))){
					entity.setTestIndexAbbr(NumberUtil.integerToString(orderNo)+abbr+"\n("+dictUnitMap.get(abbr)+")");
				}else{
					entity.setTestIndexAbbr(NumberUtil.integerToString(orderNo)+abbr);
				}
				reportList.add(entity);
			}
		}
		
//		//参数   ，检测日期和检验人（签字人）
//		Map<String,Object> map2=null;
//		if("".equals(animalId)){
//			map2=BaseService.getTblClinicalTestDataService().getMapbyClinicalTestReqAndTestItem(tblClinicalTestReq,testItem);
//		}else{
//			map2=BaseService.getTblClinicalTestDataService().getMapbyClinicalTestReqAndTestItemAndAnimalId(tblClinicalTestReq,testItem,animalId);
//		}
//		map.putAll(map2);
		
		//标本类型
		String specimenKind = specimenKindSet.toString();
		specimenKind = specimenKind.replaceAll("\\[", " ").replaceAll("\\]", " ");
		map.put("specimenKind", specimenKind);

		
		JasperReport jasperReport = null; // 报表用
		JasperPrint jasperPrint = null; // 报表用
		JasperDesign design = null ;
		try {
			design = JRXmlLoader.load(getClass().getResourceAsStream("clinicaltestDataReport.jrxml"));
		} catch (JRException e2) {
			e2.printStackTrace();
		}  
		//页头
//		JRDesignBand pageHeaderBand= (JRDesignBand) design.getPageHeader();
//		//标题
//		JRStaticText staticText= (JRStaticText) pageHeaderBand.getChildren().get(0);
		
		String reportName = "";
		switch (testItem) {
			case 1:	reportName= "生化检验报告单";break;
			case 2:reportName="血液检验报告单";break;
			case 3:	reportName="血凝检验报告单";break;
			case 4:reportName="尿液检验报告单";break;
		default:
			break;
		}
		map.put("reportName",reportName);
		
		try {
			jasperReport = JasperCompileManager.compileReport(design);
			jasperPrint = JasperFillManager.fillReport(jasperReport, map, new ClassBeanCollectionDataSource(reportList) );
		} catch (JRException e2) {
			e2.printStackTrace();
		}    
		
//		MainFrame.getInstance().openJFrame(jasperPrint, reportName);
//		
		 if(null!=jFrame){
        	   jFrame.remove(view);
           }else{
        	   jFrame=new JFrame();
        	   jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			   jFrame.setAlwaysOnTop(true);
			   jFrame.setSize(850, 700);
			   
			   //设置居中显示
			   Toolkit tookit =jFrame.getToolkit();
				Dimension dimension =tookit.getScreenSize();
				int screenWidth=dimension.width;
				int screenHeight=dimension.height;
				int frameWidth=jFrame.getWidth();
				int frameHeight=jFrame.getHeight();
				jFrame.setLocation((screenWidth-frameWidth)/2, (screenHeight-frameHeight)/2);
           }
		view=new JRViewer(jasperPrint);
			JRViewerToolbar toolbar=(JRViewerToolbar) view.getComponent(0);
			JPanel jpanel= new JPanel();
			jpanel.setSize(50, 23);
			JButton jbutton = new JButton();
			jpanel.add(jbutton);
//			ImageIcon imageIcon = new ImageIcon("bin/image/clinicaltest/button2.jpg");
			ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/clinicaltest/button2.jpg"));
			
			
			jbutton.setIcon(imageIcon);
			jbutton.setSize(23, 23);
//			jbutton.setContentAreaFilled(false);  
			jbutton.setMargin(new Insets(0, 1, 0, 1));  
//			jbutton.setFocusPainted(false);  
			jbutton.setBorderPainted(true);  
//			jbutton.setBorder(BorderFactory.createBorder());

			toolbar.add(jpanel);
			
			jbutton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked( java.awt.event.MouseEvent e) {
					super.mouseClicked(e);
					try {
						updatePrint(false);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			JRSaveContributor   savePdf=toolbar.getSaveContributors()[1];
			JRSaveContributor   saveDocx=toolbar.getSaveContributors()[4];
			JRSaveContributor   saveXls=toolbar.getSaveContributors()[6];
			toolbar.setSaveContributors(new JRSaveContributor[]{savePdf,saveDocx,saveXls});
			toolbar.remove(2);
				
		
		jFrame.setTitle(reportName);
		jFrame.add(view);
		jFrame.setVisible(true);
		//设置AlwaysOnTop为true
//		jFrame.setAlwaysOnTop(true);
		
		
		//窗口打开后关闭（AlwaysOnTop属性）
		jFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowOpened(WindowEvent e) {
//				super.windowOpened(e);
				JFrame jframe=(JFrame) e.getSource();
				jframe.setAlwaysOnTop(false);
			}
			
		} );
		jFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		//记录 打印_检测结果日志
		TblLog tblLog = new TblLog();
		  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
		  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
		  tblLog.setOperatType("打印");
		  tblLog.setOperatOject("检测结果");
		  User user = SaveUserInfo.getUser();
		  if(null!=user){
			  tblLog.setOperator(user.getRealName());
		  }
		  String testItemStr =reportName;
		 
		  tblLog.setOperatContent(studyNo+":"+reqNoInt+"  "+testItemStr);
		  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
		  BaseService.getTblLogService().save(tblLog);
	
	}
	

	/**报表模式切换
	 * @param isA4
	 * @throws JRException
	 */
	private void updatePrint(boolean isA4) throws JRException{
//		URL logoImage = getClass().getResource("/image/clinicaltest/logo.jpg");
//		map.put("logoImage", logoImage);
//		String number = BaseService.getDictReportNumberService().getNumberByReportName("检验报告单");
//        map.put("number", number == null ? "":number);
		
		JasperReport jasperReport = null; // 报表用
		JasperPrint jasperPrint = null; // 报表用
		JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("clinicaltestDataReport.jrxml"));  
		int width = 0;   //
		int height=0;    
//		打印按钮（检测结果  非A4）
		if(!isA4){
			if(null!=reportList &&reportList.size()>0){
				Set<String> setAbbr= new HashSet<String>();
				Set<String> setAnimalId = new HashSet<String>();
				for(ClinicalTestDataForReport obj:reportList){
					setAbbr.add(obj.getTestIndexAbbr());
					setAnimalId.add(obj.getAnimalId());
				}
				if(setAbbr.size()>0){
					width=20+200+50*setAbbr.size()+20;
				}
				if(setAnimalId.size()>0){
					height=20+30+28*setAnimalId.size()+20;
				}
			}
			
			//页头
			JRDesignBand pageHeaderBand= (JRDesignBand) design.getPageHeader();
			//标题
			pageHeaderBand.getChildren().clear();   //清空页头
			pageHeaderBand.setHeight(0);
			
			//页尾
			JRDesignBand pageFooterBand= (JRDesignBand) design.getPageFooter();
			pageFooterBand.getChildren().clear();   //清空页尾
			pageFooterBand.setHeight(0);
			
			JRDesignBand summaryBand = (JRDesignBand) design.getSummary();
			JRCrosstab crosstab= (JRCrosstab) summaryBand.getChildren().get(0);
			crosstab.setWidth(width-20-20);
		}else{
			
		}
		jasperReport = JasperCompileManager.compileReport(design);    
//			jasperReport = JasperCompileManager.compileReport(xmlFilePath);
		
		
		jasperPrint = JasperFillManager.fillReport(jasperReport, map, new ClassBeanCollectionDataSource(reportList) );
		if(!isA4){
			//设置页宽,页高
			jasperPrint.setPageWidth(width);
			jasperPrint.setPageHeight(height);
		}
		jFrame.remove(view);
		view=new JRViewer(jasperPrint);
		if(!isA4){
			//没有打印按钮
			view=new JRViewer(jasperPrint);
			JRViewerToolbar toolbar=(JRViewerToolbar) view.getComponent(0);
			JPanel jpanel= new JPanel();
			jpanel.setSize(50, 23);
			JButton jbutton = new JButton();
			jpanel.add(jbutton);
			ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/clinicaltest/button.jpg"));
			jbutton.setIcon(imageIcon);
			jbutton.setSize(23, 23);
			jbutton.setMargin(new Insets(0, 1, 0, 1));  
			jbutton.setBorderPainted(true);  

			toolbar.add(jpanel);
			jbutton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					super.mouseClicked(e);
					try {
						updatePrint(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			JRSaveContributor   savePdf=toolbar.getSaveContributors()[1];
			JRSaveContributor   saveDocx=toolbar.getSaveContributors()[4];
			JRSaveContributor   saveXls=toolbar.getSaveContributors()[6];
			toolbar.setSaveContributors(new JRSaveContributor[]{savePdf,saveDocx,saveXls});
			toolbar.remove(1);
		}else{
			JRViewerToolbar toolbar=(JRViewerToolbar) view.getComponent(0);
			JButton jbutton = new JButton();
			JPanel jpanel= new JPanel();
			jpanel.add(jbutton);
			ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/clinicaltest/button2.jpg"));
			jbutton.setIcon(imageIcon);
			jbutton.setSize(23, 23);
			jbutton.setMargin(new Insets(0, 1, 0, 1));  
			jbutton.setBorderPainted(true);  
			toolbar.add(jpanel);
			jbutton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked( java.awt.event.MouseEvent e) {
					super.mouseClicked(e);
					try {
						updatePrint(false);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			JRSaveContributor   savePdf=toolbar.getSaveContributors()[1];
			JRSaveContributor   saveDocx=toolbar.getSaveContributors()[4];
			JRSaveContributor   saveXls=toolbar.getSaveContributors()[6];
			toolbar.setSaveContributors(new JRSaveContributor[]{savePdf,saveDocx,saveXls});
			toolbar.remove(2);
			
		}
				
		jFrame.add(view);
		jFrame.setVisible(true);
	}
	
	/**取消,关闭
	 * @param event
	 */
	@FXML
	private void onCancel(ActionEvent event){
		Button button =(Button) event.getSource();
		button.getScene().getWindow().hide();
	}
	
	/**
	 * 加载数据
	 */
	public void loadData(String studyNo,String reqNo) {
		ChoosePrintFrame.studyNo = studyNo;
		ChoosePrintFrame.reqNo = reqNo;
		
	}
	
	
	/**
	 * 类,动物列表
	 * @author Administrator
	 *
	 */
	public class Animal{
		
		private BooleanProperty select;
		private StringProperty animalId;
		private StringProperty sex;
		private StringProperty animalCode;
		private StringProperty specimenCode;
		public Animal(){}
		public Animal(boolean select,String animalCode,String sex,String animalId,String specimenCode){
			this.select = new SimpleBooleanProperty(select);
			this.animalId = new SimpleStringProperty(animalId);
			this.sex = new SimpleStringProperty(sex);
			this.animalCode = new SimpleStringProperty(animalCode);
			setSpecimenCode(specimenCode);
			this.select.addListener(new ChangeListener<Boolean>(){

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
						Boolean newValue) {
				}});
		}
		public BooleanProperty selectProperty(){return select;};
		public boolean getSelect() {
			return select.get();
		}
		public void setSelect(boolean select) {
			this.select =  new SimpleBooleanProperty(select);
		}
		public String getAnimalId() {
			return animalId.get();
		}
		public void setAnimalId(String animalId) {
			this.animalId =  new SimpleStringProperty(animalId);
		}
		public String getSex() {
			return sex.get();
		}
		public void setSex(String sex) {
			this.sex =  new SimpleStringProperty(sex);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode =  new SimpleStringProperty(animalCode);
		}
		public String getSpecimenCode() {
			return specimenCode.get();
		}
		public void setSpecimenCode(String specimenCode) {
			this.specimenCode = new SimpleStringProperty(specimenCode);
		}
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ChoosePrintFrame.fxml"));
		stage.initOwner(MainFrame.mainWidow);
		stage.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(root,625,450);
		stage.setScene(scene);
		stage.setTitle("选择打印条件");
		stage.setResizable(false);
		stage.showAndWait();
		
	}
	
	// 在单元格里创建多选框
		public static class CheckBoxTableCell_1<S, T> extends TableCell<S, T> {
			private final CheckBox checkBox;
			private ObservableValue<T> ov;

			public CheckBoxTableCell_1() {
				this.checkBox = new CheckBox();
				this.checkBox.setAlignment(Pos.CENTER);
				setAlignment(Pos.CENTER);
				setGraphic(checkBox);
			}

			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					setGraphic(checkBox);
					if (ov instanceof BooleanProperty) {
						checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
					}
					ov = getTableColumn().getCellObservableValue(getIndex());
					if (ov instanceof BooleanProperty) {
						checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
					}
					if (checkBox.isSelected()) {
						// getTableRow().getStylesheets().add("willPayRow");
						getTableRow().setStyle("-fx-background-color:palegreen ;");
						
					} else {
						getTableRow().setStyle("");
					}
				}
			}

		}
}
