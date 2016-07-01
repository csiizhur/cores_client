package com.lanen.view.quarantine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblES;
import com.lanen.model.quarantine.TblAnimalRecIndex;
import com.lanen.model.quarantine.TblAnimalRecList;
import com.lanen.model.tableview.TblAnimalRecListForTableView;
import com.lanen.util.DateUtil;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.view.main.Main;

public class AnimalRegisterFrame extends Application implements Initializable  {

	private static String recId ="";
	private static String receiver="";
	private  FileChooser fileChooser = new FileChooser();  //文件选择器
	private static Stage stage = null; 
	private File file = null;  //excel文件
	
	private Map<String,String> studyNoQRRIdMap =null;  //   studyNo:qrrId
	private List<String> studyNoList ;
	private List<String> roomList = new ArrayList<String>();
	
	@FXML
	private TextField fileNameText;
	
	@FXML
	private ComboBox<String> snComboBox;//序号下拉框
	@FXML
	private TextField animalIdText_tab1;
	@FXML
	private ComboBox<String> studyNoComboBox_tab1;
	@FXML
	private ComboBox<String> roomComboBox_tab1;
	@FXML
	private ComboBox<String> genderCombobox_tab1;
	@FXML
	private ComboBox<String> beginSNComboBox;
	@FXML
	private ComboBox<String> endSNComboBox;
	@FXML
	private TextField animalIdText_tab2;
	@FXML
	private ComboBox<String> studyNoComboBox_tab2;
	@FXML
	private ComboBox<String> roomComboBox_tab2;
	@FXML
	private ComboBox<String> genderCombobox_tab2;
	@FXML
	private HBox editableHBox;
	
	@FXML
	private TableView<TblAnimalRecListForTableView> animalTable;
	private static ObservableList<TblAnimalRecListForTableView> data_animalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> snCol ;
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> recIdCol;			//	接收单号	RecID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalTypeCol;		//	动物种类	AnimalType	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalStrainCol;	//	动物品系	AnimalStrain	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> animalIdCol;		//	动物ID号	AnimalID	varchar(30)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> genderCol;				//	性别	Gender	integer			
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> roomCol;			//	申请单号	QRRID	varchar(20)	
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> planStudyNoCol;		//	分配课题编号	PlanStudyNo	varchar(20)
	@FXML
	private TableColumn<TblAnimalRecListForTableView,String> stateCol;           //状态   死亡  ，移交，备库
	
	private  static AnimalRegisterFrame instance;
	
	public static AnimalRegisterFrame getInstance(){
		return instance;
	}
	public AnimalRegisterFrame(){
		recId="";
		receiver="";
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//初始化表格
		initAnimalTable();
		
	}
	

	/**
	 * 初始化表格
	 */
	private void initAnimalTable() {
//		 snCol ;
//		 recIdCol;			//	接收单号	RecID	varchar(20)	
//		 animalTypeCol;		//	动物种类	AnimalType	varchar(20)	
//		 animalStrainCol;	//	动物品系	AnimalStrain	varchar(20)	
//		 animalIdCol;		//	动物ID号	AnimalID	varchar(30)	
//		 genderCol;				//	性别	Gender	integer			
//		 qrRIDCol;			//	申请单号	QRRID	varchar(20)	
//		 planStudyNoCol;		//	分配课题编号	PlanStudyNo	varchar(20)
//		 stateCol;     
		snCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("sn")); 
		recIdCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("recId")); 
		animalTypeCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalType")); 
		animalStrainCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalStrain")); 
		animalIdCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("animalId")); 
		genderCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("gender")); 
		roomCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("room")); 
		planStudyNoCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("planStudyNo")); 
		stateCol.setCellValueFactory(new PropertyValueFactory<TblAnimalRecListForTableView,String>("state")); 
		animalTable.setItems(data_animalTable);
	}
	/**
	 * 初始化其他控件（sn下拉框   ，课题编号下拉框，分配房间下拉框）
	 */
	private void initOtherControl() {
		TblAnimalRecIndex tblAnimalRecIndex = BaseService.getTblAnimalRecIndexService().getById(recId);
		snComboBox.getItems().clear();
		beginSNComboBox.getItems().clear();
		endSNComboBox.getItems().clear();
		studyNoComboBox_tab1.getItems().clear();
		studyNoComboBox_tab2.getItems().clear();
		roomComboBox_tab1.getItems().clear();
		roomComboBox_tab2.getItems().clear();
		if(null!=tblAnimalRecIndex){
			int total = tblAnimalRecIndex.getNumMale()+tblAnimalRecIndex.getNumFemale();
			for(int i =0;i<total;i++){
				snComboBox.getItems().add(i+1+"");
				beginSNComboBox.getItems().add(i+1+"");
				endSNComboBox.getItems().add(i+1+"");
			}
			String room = tblAnimalRecIndex.getRoom();
			roomComboBox_tab1.getItems().add("");
			roomComboBox_tab2.getItems().add("");
			if(null !=room ){
				if(room.contains(",")){
					String[] rooms =room.split(",");
					if(rooms != null && rooms.length>0){
						for(int i=0;i<rooms.length;i++){
							roomComboBox_tab1.getItems().add(rooms[i]);
							roomComboBox_tab2.getItems().add(rooms[i]);
							roomList.add(rooms[i]);
						}
					}
				}else{
					roomComboBox_tab1.getItems().add(room);
					roomComboBox_tab2.getItems().add(room);
					roomList.add(room);
				}
			}
			studyNoList = BaseService.getTblQRRequestStudyNoService().getStudyNoListByRecId(recId);
			//
			studyNoQRRIdMap =BaseService.getTblQRRequestStudyNoService().getStudyNoQRRIdMapByRecId(recId);
			
			studyNoComboBox_tab1.getItems().add("");
			studyNoComboBox_tab2.getItems().add("");
			if(null != studyNoList && studyNoList.size()>0){
				for(String studyNo : studyNoList){
					studyNoComboBox_tab1.getItems().add(studyNo);
					studyNoComboBox_tab2.getItems().add(studyNo);
				}
			}
		}
		
	}
	/**
	 * 打印button  点击事件
	 * @param event
	 */
	@FXML
	private void onPrintButton(ActionEvent event){
		//TODO 打印
		String recId =AnimalRegisterFrame.recId;
		if(null==recId ||recId.equals("")){
			Alert2.create("请先保存接收信息");
			return ;
		}
 		JasperReport jr = null;
 		JasperPrint jp = null;
        try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tblAnimalRecList.jrxml"));
		} catch (JRException e) {
			e.printStackTrace();
			Alert2.create("报表加载失败");
			return ;
		}
        
        InputStream logoImage =  this.getClass().getResourceAsStream("/image/logo_xishan.jpg");
        
        TblAnimalRecIndex tblAnimalRecIndex = BaseService.getTblAnimalRecIndexService().getById(recId);
        if(tblAnimalRecIndex!=null){
        	String producer =tblAnimalRecIndex.getProducer();//            
        	String certificate =tblAnimalRecIndex.getCertificate();//         
        	String animalStrain=tblAnimalRecIndex.getAnimalStrain();//         
        	String recDate=DateUtil.dateToString(tblAnimalRecIndex.getRecDate(), "yyyy-MM-dd") ;//           
        	
        	 Map<String,Object> map = new HashMap<String,Object>();
        	 /**编号*/
          	String number =BaseService.getDictOutputSettingsService().getShowByLabel("试验动物登记表-编号");
          	map.put("number",number);
             map.put("logoImage",logoImage);
             map.put("producer",producer);
             map.put("certificate",certificate);
             map.put("animalStrain",animalStrain);
     		
     			
     		String receiverDate ="";
     		String signId=tblAnimalRecIndex.getSignId(); 
     		if(null!=signId && !"".equals(signId)){
     			TblES tblES = BaseService.getTblESService().getById(signId);
     			if(null !=tblES){
     				receiverDate =tblES.getSigner()+"/"+recDate;	
     			}
     		}
     		map.put("receiverDate",receiverDate);
     		List<TblAnimalRecList> tblAnimalRecLists = 
     				BaseService.getTblAnimalRecListService().getListByRecId(recId);
     		int size =0;
     		if(tblAnimalRecLists!=null && tblAnimalRecLists.size()>0){
     			size = tblAnimalRecLists.size()%48;
     			if(size>0){
     				for(int i=size;i<48;i++){
     					tblAnimalRecLists.add(new TblAnimalRecList());
     				}
     			}
     		}
             try {
     			jp = JasperFillManager.fillReport(jr, map,new JRBeanCollectionDataSource(tblAnimalRecLists));
     		} catch (JRException e) {
     			e.printStackTrace();
     			Alert2.create("报表加载失败");
     			return ;
     		}
     		Main.getInstance().openJFrame(jp, "试验动物登记表");
        }
		
	}
	/**
	 * 关闭button  点击事件
	 * @param event
	 */
	@FXML
	private void onCloseButton(ActionEvent event){
		//更新ReceiveRegisterFrame 上的动物列表
		if(null!=ReceiveRegisterFrame.getInstance()){
			ReceiveRegisterFrame.getInstance().updateAnimalTable();
		}
		// 关闭
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 保存button  点击事件
	 * @param event
	 */
	@FXML
	private void onSaveButton(ActionEvent event){
		if(receiver == null || "".equals(receiver)){
			int sn = snComboBox.getSelectionModel().getSelectedIndex()+1;
			String animalId = animalIdText_tab1.getText().trim();
			int gender = 0; 
			String studyNo = studyNoComboBox_tab1.getSelectionModel().getSelectedItem();
			String room = roomComboBox_tab1.getSelectionModel().getSelectedItem();
			if ("".equals(animalId)) {// 文本内无数据 则不添加（直接返回）
				Alert2.create("动物Id号不能为空");
				animalIdText_tab1.requestFocus();
				return ;
			}
			if(animalId.getBytes().length>30){
				Alert2.create("动物Id号长度不能超过30");
				animalIdText_tab1.requestFocus();
				return ;
			}
			
			if(null == genderCombobox_tab1.getSelectionModel().getSelectedItem() ){
				Alert2.create("请选择动物性别");
				genderCombobox_tab1.requestFocus();
				return ;
			}else{
				gender =genderCombobox_tab1.getSelectionModel().getSelectedIndex()+1;
			}
			
			//判断该接收单号下，动物Id号的唯一性
//			boolean isExist = BaseService.getTblAnimalRecListService().isExistByRecIdAnimalId(recId,animalId);
			//判断动物Id号的唯一性
			boolean isExist = BaseService.getTblAnimalRecListService().isExistByAnimalId(animalId);
			if(isExist){
				Alert2.create("动物Id号已存在");
				return ;
			}
			
			if(data_animalTable.size()>=sn){
				String id = data_animalTable.get(sn-1).getId();
				TblAnimalRecList tblAnimalRecList = BaseService.getTblAnimalRecListService().getById(id);
				if(null != tblAnimalRecList){
					tblAnimalRecList.setAnimalId(animalId);
					tblAnimalRecList.setGender(gender);
					if(null!=studyNo && !"".equals(studyNo)){
						tblAnimalRecList.setPlanStudyNo(studyNo);
						tblAnimalRecList.setQrRID(studyNoQRRIdMap.get(studyNo));
					}else{
						tblAnimalRecList.setPlanStudyNo("");
						tblAnimalRecList.setQrRID("");
					}
					if(null!=room){
						tblAnimalRecList.setRoom(room);
					}else{
						tblAnimalRecList.setRoom("");
					}
					//更新
					BaseService.getTblAnimalRecListService().update(tblAnimalRecList);
					
					
					String state="";
					if(tblAnimalRecList.getTransFlag() == 1){
						state="移交";
					}
					if(tblAnimalRecList.getReserveFlag() == 1){
						state="备库";
					}
					if(tblAnimalRecList.getDeadFlag() == 1){
						state="死亡";
					}
					TblAnimalRecListForTableView element =new TblAnimalRecListForTableView(
							tblAnimalRecList.getId(),tblAnimalRecList.getSn(),tblAnimalRecList.getRecId(),
							tblAnimalRecList.getAnimalType(),tblAnimalRecList.getAnimalStrain(),
							tblAnimalRecList.getAnimalId(),tblAnimalRecList.getGender(),
							tblAnimalRecList.getRoom(),tblAnimalRecList.getPlanStudyNo(),state,tblAnimalRecList.getAnimalLevel()
							);
					data_animalTable.remove(sn-1);
					data_animalTable.add(sn-1, element);
					animalTable.getSelectionModel().select(sn-1);
//					Alert.create("保存成功");
					
					//序号 ，选择下一个，    动物Id号   置空
					if(sn!=snComboBox.getItems().size()){
						snComboBox.getSelectionModel().select(sn);
						beginSNComboBox.getSelectionModel().select(sn);
						endSNComboBox.getSelectionModel().select(sn);
					}else{
						snComboBox.getSelectionModel().selectLast();
						beginSNComboBox.getSelectionModel().selectLast();
						endSNComboBox.getSelectionModel().selectLast();
					}
					animalIdText_tab1.setText("");
					animalIdText_tab1.requestFocus();
					animalTable.scrollTo(sn);
				}
			}
			
		}
	}
	
	/**
	 * 保存（批量）button  点击事件
	 * @param event
	 */
	@FXML
	private void onMultipleSaveButton(ActionEvent event){
		//TODO 保存（批量）
		if(receiver == null || "".equals(receiver)){
			int beginSN = beginSNComboBox.getSelectionModel().getSelectedIndex()+1;
			int endSN = endSNComboBox.getSelectionModel().getSelectedIndex()+1;
			String animalId = animalIdText_tab2.getText().trim();
			int gender = 0; 
			String studyNo = studyNoComboBox_tab2.getSelectionModel().getSelectedItem();
			String room = roomComboBox_tab2.getSelectionModel().getSelectedItem();
			if(beginSN>endSN){
				Alert2.create("起始序号不能大于结束序号");
				endSNComboBox.requestFocus();
				return ;
			}
			if ("".equals(animalId)) {// 文本内无数据 则不添加（直接返回）
				Alert2.create("动物Id号不能为空");
				animalIdText_tab2.requestFocus();
				return ;
			}
			if(!animalId.matches("[1-9]{1}[0-9]{0,9}")){
				Alert2.create("动物Id号请输入正整数");
				animalIdText_tab2.requestFocus();
				return ;
			}
			if(animalId.getBytes().length>30){
				Alert2.create("动物Id号长度不能超过30");
				animalIdText_tab2.requestFocus();
				return ;
			}
			
			if(null == genderCombobox_tab2.getSelectionModel().getSelectedItem() ){
				Alert2.create("请选择动物性别");
				genderCombobox_tab2.requestFocus();
				return ;
			}else{
				gender =genderCombobox_tab2.getSelectionModel().getSelectedIndex()+1;
			}
			
			
			if(data_animalTable.size()>=endSN){
				int minAnimalId = Integer.valueOf(animalId.trim());
				//动物Id号列表
				List<String> animalIdList = new ArrayList<String>();
				//id号列表
				List<String> idList = new ArrayList<String>();
				for(int i=beginSN;i<=endSN;i++){
					animalIdList.add(minAnimalId+"");
					idList.add(data_animalTable.get(i-1).getId());
					minAnimalId++;
				}
				int j=1;
				//判断该接收单号下，动物Id号的唯一性
//				boolean isExist = false;
				String errorMsg = "";
//				for(TblAnimalRecListForTableView obj:data_animalTable){
//					if(null !=obj.getAnimalId()){
//						if(j>endSN ||j<beginSN){
//							if(animalIdList.contains(obj.getAnimalId().trim())){
//								isExist = true;
//								errorMsg=errorMsg+obj.getAnimalId().trim();
//								break;
//							}
//						}
//					}
//					j++;
//				}
				errorMsg = BaseService.getTblAnimalRecListService()
						.isExistByAnimalIdListAndIdList(animalIdList,idList);
				if(errorMsg !=null && !errorMsg.isEmpty()){
					Alert2.create("动物Id号"+errorMsg+"已存在");
					return ;
				}
				String[] ids = new String[idList.size()];
				idList.toArray(ids);
				List<TblAnimalRecList> tblAnimalRecLists = 
						BaseService.getTblAnimalRecListService().getByIds(ids);
				if(null != tblAnimalRecLists){
					if(null==studyNo){
						studyNo="";
					}
					if(null==room){
						room="";
					}
					int index =0;
					for(TblAnimalRecList obj:tblAnimalRecLists){
						obj.setAnimalId(animalIdList.get(index));
						obj.setGender(gender);
						obj.setPlanStudyNo(studyNo);
						if(!"".equals(studyNo)){
							obj.setQrRID(studyNoQRRIdMap.get(studyNo));
						}else{
							obj.setQrRID("");
						}
						obj.setRoom(room);
						index++;
					}
				}
				BaseService.getTblAnimalRecListService().updateList(tblAnimalRecLists);
					
				if(null != tblAnimalRecLists){
					String state="";
					int index =beginSN-1;
					for(TblAnimalRecList obj:tblAnimalRecLists){
						if(obj.getTransFlag() == 1){
							state="移交";
						}
						if(obj.getReserveFlag() == 1){
							state="备库";
						}
						if(obj.getDeadFlag() == 1){
							state="死亡";
						}
						TblAnimalRecListForTableView element =new TblAnimalRecListForTableView(
								obj.getId(),obj.getSn(),obj.getRecId(),obj.getAnimalType(),
								obj.getAnimalStrain(),obj.getAnimalId(),obj.getGender(),
								obj.getRoom(),obj.getPlanStudyNo(),state,obj.getAnimalLevel()
								);
						data_animalTable.remove(index);
						data_animalTable.add(index, element);
						animalTable.getSelectionModel().select(index);
						index++;
					}
				}	
				animalTable.scrollTo(endSN);
					Alert.create("保存成功");
					
					//序号 ，选择下一个，    动物Id号   置空
					if(endSN!=snComboBox.getItems().size()){
						snComboBox.getSelectionModel().select(endSN);
						beginSNComboBox.getSelectionModel().select(endSN);
						endSNComboBox.getSelectionModel().select(endSN);
					}else{
						snComboBox.getSelectionModel().selectLast();
						beginSNComboBox.getSelectionModel().selectLast();
						endSNComboBox.getSelectionModel().selectLast();
					}
					animalIdText_tab2.setText("");
					animalIdText_tab2.requestFocus();
			}
		}
	}
	/**
	 * 浏览button  点击事件
	 * @param event
	 */
	@FXML
	private void onBrowseButton(ActionEvent event){
		fileChooser.setTitle("Excel文件选择");
		List<String> list = new ArrayList<String>();
		list.add("*.xls");
		list.add("*.xlsx");
		fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("Excel",list)
	            );
		
		File file = fileChooser.showOpenDialog(stage);
		if(file!=null){
			this.file =file;
			fileNameText.setText(file.getPath());
		}
	}

	/**
	 * 导入button 点击事件
	 * 
	 * @param event
	 */
	@FXML
	private void onLeadingInButton(ActionEvent event) {
		String fileFullName = fileNameText.getText();
		if ("".equals(fileFullName) || file == null) {
			Alert2.create("请选选择excel文件");
			return;
		}
		int total = data_animalTable.size();
		int sn = 0;
		String snStr = "";
		String animalId = "";
		String genderStr = "";
		String studyNo = "";
		String room = "";
		List<Integer> snList = new ArrayList<Integer>();
		List<String> animalIdList = new ArrayList<String>();
		Map<Integer, String> snAnimalIdMap = new HashMap<Integer, String>();
		Map<Integer, Integer> snGenderMap = new HashMap<Integer, Integer>();
		Map<Integer, String> snStudyNoMap = new HashMap<Integer, String>();
		Map<Integer, String> snRoomMap = new HashMap<Integer, String>();
		// 2007版读取方法
		Workbook workbook = null;

		if (file != null) {
			String path = file.getAbsolutePath();// 获取文件的路径
			try {
				workbook = new XSSFWorkbook(path);
				if (workbook.getNumberOfSheets() > 0) {// sheet
					if (null != workbook.getSheetAt(0)) {
						// 获取第一个sheet
						XSSFSheet aSheet = (XSSFSheet) workbook.getSheetAt(0);
						if (aSheet.getLastRowNum() > 1) {
							for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
								// 进入aSheet的行(row)的循环
								if (null != aSheet.getRow(rowNumOfSheet)) {
									XSSFRow aRow = aSheet.getRow(rowNumOfSheet);
									if (rowNumOfSheet == 0) {// 表头
										int lastCellNum = aRow.getLastCellNum();
										if (lastCellNum < 6) {
											XSSFCell aCell = aRow.getCell(0);
											XSSFCell bCell = aRow.getCell(1);
											XSSFCell cCell = aRow.getCell(2);
											XSSFCell dCell = aRow.getCell(3);
											XSSFCell eCell = aRow.getCell(4);
											if (null != aCell) {
												aCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												if (!aCell.getStringCellValue().replace('\t', ' ')
														.replace('\n', ' ').replace('\r', ' ')
														.trim().equals("序号")) {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
											if (null != bCell) {
												bCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												if (!bCell.getStringCellValue().replace('\t', ' ')
														.replace('\n', ' ').replace('\r', ' ')
														.trim().equals("动物Id号")) {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
											if (null != cCell) {
												cCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												if (!cCell.getStringCellValue().replace('\t', ' ')
														.replace('\n', ' ').replace('\r', ' ')
														.trim().equals("性别")) {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
											if (null != dCell) {
												dCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												if (!dCell.getStringCellValue().replace('\t', ' ')
														.replace('\n', ' ').replace('\r', ' ')
														.trim().equals("所属课题")) {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
											if (null != eCell) {
												eCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												if (!eCell.getStringCellValue().replace('\t', ' ')
														.replace('\n', ' ').replace('\r', ' ')
														.trim().equals("安置房间号")) {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
										} else {
											Alert2.create("请检查表头");
											return;
										}
									} else {
										// 数据行
										XSSFCell aCell = aRow.getCell(0);
										XSSFCell bCell = aRow.getCell(1);
										XSSFCell cCell = aRow.getCell(2);
										XSSFCell dCell = aRow.getCell(3);
										XSSFCell eCell = aRow.getCell(4);
										if (null != aCell) {// 序号为空，则其他就不检查了
											aCell.setCellType(XSSFCell.CELL_TYPE_STRING);
											snStr = aCell.getStringCellValue().replace('\t', ' ')
													.replace('\n', ' ').replace('\r', ' ').trim();
											if (null != snStr && snStr.contains(".")) {
												int index = snStr.indexOf(".");
												snStr = snStr.substring(0, index);
											}
											if (null == snStr
													|| !snStr.matches("[1-9]{1}[0-9]{0,9}")) {
												Alert2.create("序号‘" + snStr + "'格式错误");
												return;
											}
											sn = Integer.valueOf(snStr);
											if (sn > total) {
												Alert2.create("序号‘" + sn + "'不存在");
												return;
											}
											if (snList.contains(sn)) {
												Alert2.create("序号‘" + sn + "'重复");
												return;
											}
											// 添加序号到snList
											snList.add(sn);
											if (null != bCell) {// 动物id号
												bCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												animalId = bCell.getStringCellValue()
														.replace('\t', ' ').replace('\n', ' ')
														.replace('\r', ' ').trim();
												if (animalId.contains(".")) {
													int index = animalId.indexOf(".");
													animalId = animalId.substring(0, index);
												}
												if (null == animalId || animalId.equals("")) {
													Alert2.create("动物Id号不能为空");
													return;
												}
												if (animalIdList.contains(animalId)) {
													Alert2.create("动物Id号'" + animalId + "'重复");
													return;
												}
												if (animalId.getBytes().length < 30
														&& animalId.matches("[1-9]{1}[0-9]{0,9}")) {
													// 添加到动物id号列表
													animalIdList.add(animalId);
													// 添加到编号Id号map中
													snAnimalIdMap.put(sn, animalId);
												} else {
													Alert2.create("动物Id号'" + animalId + "'格式错误");
													return;
												}
											} else {
												Alert2.create("动物Id号不能为空");
												return;
											}
											if (null != cCell) {// 性别
												cCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												genderStr = cCell.getStringCellValue()
														.replace('\t', ' ').replace('\n', ' ')
														.replace('\r', ' ').trim();
												if (null == genderStr || genderStr.equals("")) {
													Alert2.create("性别不能为空");
													return;
												}
												if (genderStr.equals("♂")) {
													snGenderMap.put(sn, 1);
												} else if (genderStr.equals("♀")) {
													snGenderMap.put(sn, 2);
												} else {
													Alert2.create("性别'" + genderStr + "'格式错误");
													return;
												}
											} else {
												Alert2.create("性别不能为空");
												return;
											}
											if (null != dCell) {// 课题编号
												dCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												studyNo = dCell.getStringCellValue()
														.replace('\t', ' ').replace('\n', ' ')
														.replace('\r', ' ').trim();
												if (studyNo.contains(".")) {
													int index = studyNo.indexOf(".");
													studyNo = studyNo.substring(0, index);
												}
												if (!"".equals(studyNo)
														&& !studyNoList.contains(studyNo)) {
													Alert2.create("课题编号'" + studyNo + "'不存在");
													return;
												} else {
													snStudyNoMap.put(sn, studyNo);
												}

											} else {
												snStudyNoMap.put(sn, "");
											}
											if (null != eCell) {// 房间号
												eCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												room = eCell.getStringCellValue()
														.replace('\t', ' ').replace('\n', ' ')
														.replace('\r', ' ').trim();
												if (room.contains(".")) {
													int index = room.indexOf(".");
													room = room.substring(0, index);
												}
												if (!"".equals(room) && !roomList.contains(room)) {
													Alert2.create("房间号'" + room + "'不存在");
													return;
												} else {
													snRoomMap.put(sn, room);
												}

											} else {
												snRoomMap.put(sn, "");
											}
											snStr = "";
											animalId = "";
											studyNo = "";
											room = "";
											genderStr = "";
											sn = 0;
										} else {
											Alert2.create("序号不能为空");
											return;
										}
									}
								} else {// end 当前行不为空
									if (rowNumOfSheet == 0) {
										Alert2.create("请检查表头");
										return;
									}
								}
							}// end aSheet的行的循环
						} else {
							Alert2.create("excel数据为空");
							return;
						}
					} else {// end 获取第一个sheet是否为空
						Alert2.create("excel为空");
						return;
					}
				} else { // end sheets >0
					Alert2.create("excel为空");
					return;
				}
			} catch (Exception e) {
				// 下面使用的是2003（workbook的赋值不同，其他与2007基本相同）
				InputStream is = null;
				try {
					is = new FileInputStream(path);
					workbook = new HSSFWorkbook(is);
					if (workbook.getNumberOfSheets() > 0) {// sheet
						if (null != workbook.getSheetAt(0)) {
							// 获取第一个sheet
							HSSFSheet aSheet = (HSSFSheet) workbook.getSheetAt(0);
							if (aSheet.getLastRowNum() > 1) {
								for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
									// 进入aSheet的行(row)的循环
									if (null != aSheet.getRow(rowNumOfSheet)) {
										HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
										if (rowNumOfSheet == 0) {// 表头
											int lastCellNum = aRow.getLastCellNum();
											if (lastCellNum < 6) {
												HSSFCell aCell = aRow.getCell(0);
												HSSFCell bCell = aRow.getCell(1);
												HSSFCell cCell = aRow.getCell(2);
												HSSFCell dCell = aRow.getCell(3);
												HSSFCell eCell = aRow.getCell(4);
												if (null != aCell) {
													aCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													if (!aCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim().equals("序号")) {
														Alert2.create("请检查表头");
														return;
													}
												} else {
													Alert2.create("请检查表头");
													return;
												}
												if (null != bCell) {
													bCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													if (!bCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim()
															.equals("动物Id号")) {
														Alert2.create("请检查表头");
														return;
													}
												} else {
													Alert2.create("请检查表头");
													return;
												}
												if (null != cCell) {
													cCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													if (!cCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim().equals("性别")) {
														Alert2.create("请检查表头");
														return;
													}
												} else {
													Alert2.create("请检查表头");
													return;
												}
												if (null != dCell) {
													dCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													if (!dCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim()
															.equals("所属课题")) {
														Alert2.create("请检查表头");
														return;
													}
												} else {
													Alert2.create("请检查表头");
													return;
												}
												if (null != eCell) {
													eCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													if (!eCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim()
															.equals("安置房间号")) {
														Alert2.create("请检查表头");
														return;
													}
												} else {
													Alert2.create("请检查表头");
													return;
												}
											} else {
												Alert2.create("请检查表头");
												return;
											}
										} else {
											// 数据行
											HSSFCell aCell = aRow.getCell(0);
											HSSFCell bCell = aRow.getCell(1);
											HSSFCell cCell = aRow.getCell(2);
											HSSFCell dCell = aRow.getCell(3);
											HSSFCell eCell = aRow.getCell(4);
											if (null != aCell) {// 序号为空，则其他就不检查了
												aCell.setCellType(XSSFCell.CELL_TYPE_STRING);
												snStr = aCell.getStringCellValue()
														.replace('\t', ' ').replace('\n', ' ')
														.replace('\r', ' ').trim();
												if (null != snStr && snStr.contains(".")) {
													int index = snStr.indexOf(".");
													snStr = snStr.substring(0, index);
												}
												if (null == snStr
														|| !snStr.matches("[1-9]{1}[0-9]{0,9}")) {
													Alert2.create("序号‘" + snStr + "'格式错误");
													return;
												}
												sn = Integer.valueOf(snStr);
												if (sn > total) {
													Alert2.create("序号‘" + sn + "'不存在");
													return;
												}
												if (snList.contains(sn)) {
													Alert2.create("序号‘" + sn + "'重复");
													return;
												}
												// 添加序号到snList
												snList.add(sn);
												if (null != bCell) {// 动物id号
													bCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													animalId = bCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim();
													if (animalId.contains(".")) {
														int index = animalId.indexOf(".");
														animalId = animalId.substring(0, index);
													}
													if (null == animalId || animalId.equals("")) {
														Alert2.create("动物Id号不能为空");
														return;
													}
													if (animalIdList.contains(animalId)) {
														Alert2.create("动物Id号'" + animalId + "'重复");
														return;
													}
													if (animalId.getBytes().length < 30
															&& animalId
																	.matches("[1-9]{1}[0-9]{0,9}")) {
														// 添加到动物id号列表
														animalIdList.add(animalId);
														// 添加到编号Id号map中
														snAnimalIdMap.put(sn, animalId);
													} else {
														Alert2.create("动物Id号'" + animalId + "'格式错误");
														return;
													}
												} else {
													Alert2.create("动物Id号不能为空");
													return;
												}
												if (null != cCell) {// 性别
													cCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													genderStr = cCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim();
													if (null == genderStr || genderStr.equals("")) {
														Alert2.create("性别不能为空");
														return;
													}
													if (genderStr.equals("♂")) {
														snGenderMap.put(sn, 1);
													} else if (genderStr.equals("♀")) {
														snGenderMap.put(sn, 2);
													} else {
														Alert2.create("性别'" + genderStr + "'格式错误");
														return;
													}
												} else {
													Alert2.create("性别不能为空");
													return;
												}
												if (null != dCell) {// 课题编号
													dCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													studyNo = dCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim();
													if (studyNo.contains(".")) {
														int index = studyNo.indexOf(".");
														studyNo = studyNo.substring(0, index);
													}
													if (!"".equals(studyNo)
															&& !studyNoList.contains(studyNo)) {
														Alert2.create("课题编号'" + studyNo + "'不存在");
														return;
													} else {
														snStudyNoMap.put(sn, studyNo);
													}

												} else {
													snStudyNoMap.put(sn, "");
												}
												if (null != eCell) {// 房间号
													eCell.setCellType(XSSFCell.CELL_TYPE_STRING);
													room = eCell.getStringCellValue()
															.replace('\t', ' ').replace('\n', ' ')
															.replace('\r', ' ').trim();
													if (room.contains(".")) {
														int index = room.indexOf(".");
														room = room.substring(0, index);
													}
													if (!"".equals(room)
															&& !roomList.contains(room)) {
														Alert2.create("房间号'" + room + "'不存在");
														return;
													} else {
														snRoomMap.put(sn, room);
													}

												} else {
													snRoomMap.put(sn, "");
												}
												snStr = "";
												animalId = "";
												studyNo = "";
												room = "";
												genderStr = "";
												sn = 0;
											} else {
												Alert2.create("序号不能为空");
												return;
											}
										}
									} else {// end 当前行不为空
										if (rowNumOfSheet == 0) {
											Alert2.create("请检查表头");
											return;
										}
									}
								}// end aSheet的行的循环
							} else {
								Alert2.create("excel数据为空");
								return;
							}
						} else {// end 获取第一个sheet是否为空
							Alert2.create("excel为空");
							return;
						}
					} else { // end sheets >0
						Alert2.create("excel为空");
						return;
					}

				} catch (Exception ex) {
					Alert2.create("文件读取失败");
					return;
				} finally {
					if (is != null)
						try {
							is.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			}
		} else {
			Alert2.create("文件读取失败");
			return;
		}

		// 检查sn
		if (snList.size() == total) {
			for (int i = 0; i < total; i++) {
				if (!snList.contains(i + 1)) {
					Alert2.create("缺少序号" + (i + 1) + "");
					return;
				}
			}
			// TODO 保存（批量）
			if (receiver == null || "".equals(receiver)) {
				List<String> idList = new ArrayList<String>();
				for (TblAnimalRecListForTableView obj : data_animalTable) {
					idList.add(obj.getId());
				}
				
			//检查 动物Id号是否存在
				String errorMsg = BaseService.getTblAnimalRecListService()
						.isExistByAnimalIdListAndIdList(animalIdList,idList);
				if(errorMsg !=null && !errorMsg.isEmpty()){
					Alert2.create("动物Id号"+errorMsg+"已存在");
					return ;
				}
				
				
				String[] ids = new String[idList.size()];
				idList.toArray(ids);
				List<TblAnimalRecList> tblAnimalRecLists = BaseService.getTblAnimalRecListService()
						.getByIds(ids);
				if (null != tblAnimalRecLists) {
					for (TblAnimalRecList obj : tblAnimalRecLists) {
						int currentSN = obj.getSn();
						obj.setAnimalId(snAnimalIdMap.get(currentSN));
						obj.setGender(snGenderMap.get(currentSN));
						obj.setPlanStudyNo(snStudyNoMap.get(currentSN));
						obj.setRoom(snRoomMap.get(currentSN));
					}
				}
				BaseService.getTblAnimalRecListService().updateList(tblAnimalRecLists);

				if (null != tblAnimalRecLists) {
					String state = "";
					int index = 0;
					for (TblAnimalRecList obj : tblAnimalRecLists) {
						if (obj.getTransFlag() == 1) {
							state = "移交";
						}
						if (obj.getReserveFlag() == 1) {
							state = "备库";
						}
						if (obj.getDeadFlag() == 1) {
							state = "死亡";
						}
						TblAnimalRecListForTableView element = new TblAnimalRecListForTableView(
								obj.getId(), obj.getSn(), obj.getRecId(), obj.getAnimalType(),
								obj.getAnimalStrain(), obj.getAnimalId(), obj.getGender(),
								obj.getRoom(), obj.getPlanStudyNo(), state,obj.getAnimalLevel());
						data_animalTable.remove(index);
						data_animalTable.add(index, element);
						animalTable.getSelectionModel().select(index);
						index++;
					}
				}
				animalTable.scrollTo(total);
				Alert.create("导入成功");

				snComboBox.getSelectionModel().select(0);
				beginSNComboBox.getSelectionModel().select(0);
				endSNComboBox.getSelectionModel().select(0);
			}
		} else {
			Alert2.create("动物数量不对");
			return;
		}

	}
	
	//加载数据
	public void loadData(){
		//初始化其他控件（sn下拉框   ，课题编号下拉框，分配房间下拉框）
		initOtherControl();
		//初始化数据（表格，下拉框选中项以及是否可以编辑）
		initData();
		
		//1.查询动物种类
		String animalType = BaseService.getTblAnimalRecIndexService().getAnimalTypeByRecId(recId);
		//2.根据动物种类判断   是大动物   还是小动物
		if(null != animalType && !animalType.isEmpty()){
			boolean isBigAnimal = BaseService.getDictAnimalTypeService().isBigAnimal(animalType);
			//3.如果 是小动物
			if(!isBigAnimal){
				//3.1 判断是否是一个未录入
				boolean isNoAnimalId = BaseService.getTblAnimalRecListService().isNoAnimalId(recId);
				//3.2如何一个未录入，查询小动物列表中最大的临时编号+1
				if(isNoAnimalId){
					String nextAnimalId =BaseService.getTblAnimalRecListService().getNextAnimalId();
					//3.3animalIdText设置默认是
					animalIdText_tab1.setText(nextAnimalId);
					animalIdText_tab2.setText(nextAnimalId);
				}
				
			}
		}
		
	}
	/**
	 * 初始化数据（表格，下拉框选中项以及是否可以编辑）
	 */
	private void initData() {
		data_animalTable.clear();
		List<TblAnimalRecList> tblAnimalRecLists =BaseService.getTblAnimalRecListService().getListByRecId(recId);
		if(null!=tblAnimalRecLists && tblAnimalRecLists.size()>0){
			String state="";
			for(TblAnimalRecList obj:tblAnimalRecLists){
				state="";
				if(obj.getTransFlag() == 1){
					state="移交";
				}
				if(obj.getReserveFlag() == 1){
					state="备库";
				}
				if(obj.getDeadFlag() == 1){
					state="死亡";
				}
				data_animalTable.add(new TblAnimalRecListForTableView(
						obj.getId(),obj.getSn(),obj.getRecId(),obj.getAnimalType(),obj.getAnimalStrain(),
						obj.getAnimalId(),obj.getGender(),obj.getRoom(),obj.getPlanStudyNo(),state,obj.getAnimalLevel()
						));
			}
		}
		
		int index =0;
		for(int i=0;i<data_animalTable.size();i++){
			index = i;
			if(data_animalTable.get(i).getAnimalId() == null 
					||"".equals(data_animalTable.get(i).getAnimalId())){
				break ;
			}
		}
		animalTable.getSelectionModel().select(index);
		snComboBox.getSelectionModel().select(index);
		beginSNComboBox.getSelectionModel().select(index);
		endSNComboBox.getSelectionModel().select(index);
		if(receiver != null && !"".equals(receiver)){
			editableHBox.setVisible(true);// 不可编辑
		}else{
			editableHBox.setVisible(false);
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		 stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
				@Override
				public void handle(WindowEvent event) {
					//更新ReceiveRegisterFrame 上的动物列表
					if(null!=ReceiveRegisterFrame.getInstance()){
						ReceiveRegisterFrame.getInstance().updateAnimalTable();
					}
				}});
		AnimalRegisterFrame.stage = stage;
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("AnimalRegisterFrame.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("动物登记列表");
		stage.setResizable(false);
		stage.show();
	}

	public static String getRecId() {
		return recId;
	}

	public static void setRecId(String recId) {
		AnimalRegisterFrame.recId = recId;
	}
	public static String getReceiver() {
		return receiver;
	}
	public static void setReceiver(String receiver) {
		AnimalRegisterFrame.receiver = receiver;
	}
	

}
