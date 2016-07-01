package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.path.TblTissueSliceSn;
import com.lanen.model.path.TblTissueSliceViscera;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**非常规取值取材编号
 * @author Administrator
 *
 */
public class TissueSliceFeichanggui extends Application implements Initializable {
	
	/**
	 * 切片编号类型 ComboBox
	 */
	@FXML
	private ComboBox<String> comboBox;
	ObservableList<String> data_comboBox = FXCollections.observableArrayList();
	List<TblTissueSliceIndex> tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
	
	@FXML
	private Button signBtn;//签字确认
	@FXML
	private Button saveBtn;//保存
	@FXML
	private Button exitBtn;//取消
	@FXML
	private Button printBtn;//打印组织取材编号表
	
	@FXML
	private Button otherBtn;//其他脏器或组织
	
	@FXML
	private Button addBtn;//添加
	@FXML
	private Button delBtn;//删除
	
	@FXML
	private Button upBtn;//上移
	@FXML
	private Button downBtn;//下移
	
	@FXML
	private TableView<AnimalTissueCode> animalVisceraTable;//待选动物脏器
	private ObservableList<AnimalTissueCode> data_animalVisceraTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnimalTissueCode,String> animalCodeCol ;			//动物编号
	@FXML
	private TableColumn<AnimalTissueCode,String> visceraOrTissueNameCol ;	//脏器或组织
	
	
	@FXML
	private TableView<AnimalTissueCode> sliceCodeTable;		//切片编号表
	private ObservableList<AnimalTissueCode> data_sliceCodeTable = FXCollections.observableArrayList();
	/**
	 * 动物编号->对应切片编号数量(非常规，签字确认过的)
	 */
	private Map<String,Integer> animalCode2NumberMap_old = new HashMap<String,Integer>();
	/**
	 * 动物编号->对应切片编号数量(实时更新的)
	 */
	private Map<String,Integer> animalCode2NumberMap = new HashMap<String,Integer>();
	
	@FXML
	private TableColumn<AnimalTissueCode,Integer> snCol;
	@FXML
	private TableColumn<AnimalTissueCode,String> sliceCodeCol;
	@FXML
	private TableColumn<AnimalTissueCode,String> visceraOrTissueNameCol_2 ;	//脏器或组织
	
	/**
	 * 专题编号
	 */
	private String studyNo;
	/**
	 * 组织切片编号索引
	 */
	private static TblTissueSliceIndex tblTissueSliceIndex;
	
	private static TissueSliceFeichanggui instance;
	public static TissueSliceFeichanggui getInstance(){
		if(null == instance){
			instance = new TissueSliceFeichanggui();
		}
		return instance;
	}
	
	public TissueSliceFeichanggui() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initAnimalVisceraTable();
		initSliceCodeTable();
		initComBoBox();
	}
	/**
	 * 初始化ComboBox
	 */
	private void initComBoBox() {
		comboBox.setItems(data_comboBox);
		comboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				int index = newValue.intValue();
				if( index > -1){
					tblTissueSliceIndex = tissueSliceIndexList.get(index);
					//更新值 
					updatePageData();
				}
				
			}});
	}

	/**
	 * 初始化sliceCodeTable
	 */
	private void initSliceCodeTable() {
		sliceCodeTable.setItems(data_sliceCodeTable);
		sliceCodeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		snCol.setCellValueFactory(new PropertyValueFactory<AnimalTissueCode,Integer>("sn"));
		sliceCodeCol.setCellValueFactory(new PropertyValueFactory<AnimalTissueCode,String>("sliceCode"));
		visceraOrTissueNameCol_2.setCellValueFactory(new PropertyValueFactory<AnimalTissueCode,String>("visceraOrTissueName"));
		
		sliceCodeTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newIndex) {
				updateUpDownBtnDisable(newIndex);
			}

			
		});
		
	}
	/**更新 上移下移按钮状态
	 * @param newIndex
	 */
	private void updateUpDownBtnDisable(Number newIndex) {
		upBtn.setDisable(true);
		downBtn.setDisable(true);
		//编号表有选择
		if(null != newIndex && newIndex.intValue() > -1){
			//对应编号索引未签字
			if(null == tblTissueSliceIndex || null == tblTissueSliceIndex.getOperatorSign()
					|| "".equals(tblTissueSliceIndex.getOperatorSign().trim())){
				int index = newIndex.intValue();
				int nextIndex = 0;
				//1.先设置上移按钮状态
				if(index != 0){
					nextIndex = index -1;
					AnimalTissueCode animalTissueCode = data_sliceCodeTable.get(index);
					AnimalTissueCode animalTissueCode_next = data_sliceCodeTable.get(nextIndex);
					if(animalTissueCode.getAnimalCode().equals(animalTissueCode_next.getAnimalCode())){
						upBtn.setDisable(false);
					}
				}
				//2.设置下移按钮状态
				if(index != data_sliceCodeTable.size() -1 ){
					nextIndex = index +1;
					AnimalTissueCode animalTissueCode = data_sliceCodeTable.get(index);
					AnimalTissueCode animalTissueCode_next = data_sliceCodeTable.get(nextIndex);
					if(animalTissueCode.getAnimalCode().equals(animalTissueCode_next.getAnimalCode())){
						downBtn.setDisable(false);
					}
				}
			}
		}
		
	}

	/**
	 * 初始化animalVisceraTable
	 */
	private void initAnimalVisceraTable() {
		animalVisceraTable.setItems(data_animalVisceraTable);
		animalVisceraTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<AnimalTissueCode,String>("animalCode"));
		visceraOrTissueNameCol.setCellValueFactory(new PropertyValueFactory<AnimalTissueCode,String>("visceraOrTissueName"));
	}

	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignBtnAction(ActionEvent event){
		//1.组织切片检查
		if(data_sliceCodeTable.size() < 1){
			showErrorMessage("请先添加组织取材编号！");
			return;
		}
		//2.准备编号和脏器
		List<TblTissueSliceSn> tblTissueSliceSnList = new ArrayList<TblTissueSliceSn>();
		List<TblTissueSliceViscera> tblTissueSliceVisceraList = new ArrayList<TblTissueSliceViscera>();
		
		TblTissueSliceSn tblTissueSliceSn = null;
		TblTissueSliceViscera tblTissueSliceViscera = null;
		for(AnimalTissueCode obj:data_sliceCodeTable){
			String animalCode = obj.getAnimalCode();
//					String visceraOrTissueName = obj.getVisceraOrTissueName();
			String visceraCode = obj.getVisceraCode();
			Integer visceraType = obj.getVisceraType();
			String visceraFixedRecordId = obj.getVisceraFixedRecordId();
			String sliceCode = obj.getSliceCode();
			
			tblTissueSliceSn = new TblTissueSliceSn();
			tblTissueSliceViscera = new TblTissueSliceViscera();
			
			tblTissueSliceSn.setAnimalCode(animalCode);
			tblTissueSliceSn.setSliceCode(sliceCode);
			
			tblTissueSliceViscera.setVisceraType(visceraType);
			tblTissueSliceViscera.setVisceraCode(visceraCode);
			tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
			
			tblTissueSliceViscera.setVisceraName(obj.getVisceraName());
			tblTissueSliceViscera.setSubVisceraCode(obj.getSubVisceraCode());
			tblTissueSliceViscera.setSubVisceraName(obj.getSubVisceraName());
			
			tblTissueSliceViscera.setIsHandwork(obj.getIsHandwork());
			
			tblTissueSliceViscera.setSpecialFlag(obj.getSpecialFlag());
			tblTissueSliceViscera.setAnatomyPos(obj.getAnatomyPos());
			tblTissueSliceViscera.setAnatomyPosCode(obj.getAnatomyPosCode());
			tblTissueSliceViscera.setAnatomyFindingCode(obj.getAnatomyFindingCode());
			tblTissueSliceViscera.setAnatomyFindingFlag(obj.getAnatomyFindingFlag());
			tblTissueSliceViscera.setAnatomyFingding(obj.getAnatomyFingding());
			tblTissueSliceViscera.setBodySurfacePos(obj.getBodySurfacePos());
			
			tblTissueSliceSnList.add(tblTissueSliceSn);
			tblTissueSliceVisceraList.add(tblTissueSliceViscera);
		}
		
		//3.
		if(null == tblTissueSliceIndex){
			tblTissueSliceIndex = new TblTissueSliceIndex();
			tblTissueSliceIndex.setSliceCodeType(1);
			tblTissueSliceIndex.setStudyNo(studyNo);
			
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService().
					addOneFor1(tblTissueSliceIndex,tblTissueSliceSnList,tblTissueSliceVisceraList);
		}else{
			BaseService.getInstance().getTblTissueSliceIndexService().
				updateOne(tblTissueSliceIndex,tblTissueSliceSnList,tblTissueSliceVisceraList);
		}
		//4.检查固定的解剖异常组织是否都编号了，没有的话提示
		boolean isContinue = true;//是否继续
		if(data_animalVisceraTable.size() > 0){
			isContinue = Messager.showSimpleConfirm("提示", "脏器或组织未全部编号，是否继续签字？");
		}
		if(isContinue){
			//5.签字(保存或更新)
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("签字确认");
			try {
				signMeFrame.start(stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//6.签字通过
			if("OK".equals(SignMeFrame.getType())){
				tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService()
						.signTissueSliceIndex(tblTissueSliceIndex,SaveUserInfo.getRealName());
				updateCombBox();
				comboBox.getSelectionModel().select(data_comboBox.size()-2);
				
				animalCode2NumberMap_old = BaseService.getInstance().getTblTissueSliceIndexService().
						getAnimalCode2NumberMap(studyNo);
				
				exitBtn.setText("关闭");
				showMessage("签字成功！");
				
			}
		}
	}
	/**保存
	 * @param event
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		//1.组织切片检查
		if(data_sliceCodeTable.size() < 1){
			showErrorMessage("请先添加组织取材编号！");
			return;
		}
		//2.准备编号和脏器
		List<TblTissueSliceSn> tblTissueSliceSnList = new ArrayList<TblTissueSliceSn>();
		List<TblTissueSliceViscera> tblTissueSliceVisceraList = new ArrayList<TblTissueSliceViscera>();
		
		TblTissueSliceSn tblTissueSliceSn = null;
		TblTissueSliceViscera tblTissueSliceViscera = null;
		for(AnimalTissueCode obj:data_sliceCodeTable){
			String animalCode = obj.getAnimalCode();
//			String visceraOrTissueName = obj.getVisceraOrTissueName();
			String visceraCode = obj.getVisceraCode();
			Integer visceraType = obj.getVisceraType();
			String visceraFixedRecordId = obj.getVisceraFixedRecordId();
			String sliceCode = obj.getSliceCode();
			
			tblTissueSliceSn = new TblTissueSliceSn();
			tblTissueSliceViscera = new TblTissueSliceViscera();
			
			tblTissueSliceSn.setAnimalCode(animalCode);
			tblTissueSliceSn.setSliceCode(sliceCode);
			
			tblTissueSliceViscera.setVisceraType(visceraType);
			tblTissueSliceViscera.setVisceraCode(visceraCode);
			tblTissueSliceViscera.setVisceraFixedRecordId(visceraFixedRecordId);
			
			tblTissueSliceViscera.setVisceraName(obj.getVisceraName());
			tblTissueSliceViscera.setSubVisceraCode(obj.getSubVisceraCode());
			tblTissueSliceViscera.setSubVisceraName(obj.getSubVisceraName());
			
			tblTissueSliceViscera.setIsHandwork(obj.getIsHandwork());
			
			tblTissueSliceViscera.setSpecialFlag(obj.getSpecialFlag());
			tblTissueSliceViscera.setAnatomyPos(obj.getAnatomyPos());
			tblTissueSliceViscera.setAnatomyPosCode(obj.getAnatomyPosCode());
			tblTissueSliceViscera.setAnatomyFindingCode(obj.getAnatomyFindingCode());
			tblTissueSliceViscera.setAnatomyFindingFlag(obj.getAnatomyFindingFlag());
			tblTissueSliceViscera.setAnatomyFingding(obj.getAnatomyFingding());
			tblTissueSliceViscera.setBodySurfacePos(obj.getBodySurfacePos());
			
			tblTissueSliceSnList.add(tblTissueSliceSn);
			tblTissueSliceVisceraList.add(tblTissueSliceViscera);
		}
		
		//3.
		if(null == tblTissueSliceIndex){
			tblTissueSliceIndex = new TblTissueSliceIndex();
			tblTissueSliceIndex.setSliceCodeType(1);
			tblTissueSliceIndex.setStudyNo(studyNo);
			
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService().
					addOneFor1(tblTissueSliceIndex,tblTissueSliceSnList,tblTissueSliceVisceraList);
			updateCombBox();
		}else{
			
			BaseService.getInstance().getTblTissueSliceIndexService().
				updateOne(tblTissueSliceIndex,tblTissueSliceSnList,tblTissueSliceVisceraList);
		}
		exitBtn.setText("关闭");
		showMessage("保存成功！");
		
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		TissueSlicePage.getInstance().updateSliceIndexTable();
		TissueSlicePage.getInstance().updateSliceCodeTable();
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**打印组织取材编号表
	 * @param event
	 */
	@FXML
	private void onPrintBtnAction(ActionEvent event){
		//1.组织切片检查
		if(data_sliceCodeTable.size() < 1){
			showErrorMessage("切片编号列表为空！");
			return;
		}
		//2.
		JasperReport jr = null;
        JasperPrint jp = null;                                             
        try {
			jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("tissueSliceCode.jrxml"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
        Map<String,Object> map = new HashMap<String,Object>();
        URL url = this.getClass().getResource("/image/logo.jpg");
        String number = "";
        number = BaseService.getInstance().getDictReportNumberService().getNumberByReportName("组织取材编号表");
        map.put("number", number == null ? "":number);;//编号
        map.put("logoImage", url);
        
        String animalType  = "";
    	TblStudyPlan studyPlan = BaseService.getInstance().getTblStudyPlanService().getById(studyNo);
    	if(null != studyPlan){
    		animalType = studyPlan.getAnimalType();
    		String animalStrain = studyPlan.getAnimalStrain();
    		if(null != animalStrain || !"".equals(animalStrain)){
    			if(!animalStrain.contains(animalType)){
    				animalType = animalType +" "+animalStrain;
    			}else{
    				animalType = animalStrain;
    			}
    		}
    	}
        map.put("studyNo", studyNo);
        map.put("animalType", animalType);
        
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> fieldMap = null;
        for(AnimalTissueCode obj:data_sliceCodeTable){
        	fieldMap = new HashMap<String,Object>();
        	fieldMap.put("sliceCode", obj.getSliceCode());
        	fieldMap.put("visceraOrTissueName", obj.getVisceraOrTissueName());
        	mapList.add(fieldMap);
        }
  		try {
			jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(mapList));
		} catch (JRException e) {
			e.printStackTrace();
		}     
        Main.getInstance().openJFrame(jp, "组织取材编号表");
		
	}
	
	/**向右移
	 * @param event
	 */
	@FXML
	private void onAddBtnAction(ActionEvent event){
		AnimalTissueCode selectedItem = animalVisceraTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择数据！");
			return ;
		}
		sliceCodeTablePlusOne(selectedItem);
		exitBtn.setText("取消");
	}
	
	/**切片编号表加 1，另一表减 1
	 * @param selectedItem
	 */
	private void sliceCodeTablePlusOne(AnimalTissueCode selectedItem) {
		//1.先减一
		animalVisceraTable.getSelectionModel().clearSelection();
		data_animalVisceraTable.remove(selectedItem);
		//2.加1
		String selectedAnimalCode = selectedItem.getAnimalCode();
//		String selectedVisceraOrTissueName = selectedItem.getVisceraOrTissueName();
//		String selectedVisceraCode = selectedItem.getVisceraCode();
//		Integer selectedVisceraType = selectedItem.getVisceraType();
//		String selectedVisceraFixedRecordId = selectedItem.getVisceraFixedRecordId();
		ObservableList<AnimalTissueCode> data_sliceCodeTable2 = FXCollections.observableArrayList(data_sliceCodeTable);
		data_sliceCodeTable.clear();
		animalCode2NumberMap = new HashMap<String,Integer>(animalCode2NumberMap_old);
		int sn = 0;
		int selected = 0;
		boolean hasPlus = false; 
		for(AnimalTissueCode obj:data_sliceCodeTable2){
			sn++;
			String animalCode = obj.getAnimalCode();
			if(!hasPlus && selectedAnimalCode.compareTo(animalCode) < 0){
				selected = sn -1;
				Integer number = animalCode2NumberMap.get(selectedAnimalCode);
				if(null == number){
					number = 1;
				}else{
					number++;
				}
				animalCode2NumberMap.put(selectedAnimalCode, number);
				String sliceCode = selectedAnimalCode+"g-"+number;
//				data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,selectedAnimalCode,
//				selectedVisceraOrTissueName,selectedVisceraType,selectedVisceraCode,selectedVisceraFixedRecordId));
				data_sliceCodeTable.add(new AnimalTissueCode(sn, sliceCode, selectedAnimalCode, selectedItem.getVisceraOrTissueName(), selectedItem.getVisceraType(), 
						selectedItem.getVisceraCode(), selectedItem.getVisceraName(), selectedItem.getSubVisceraCode(),
						selectedItem.getSubVisceraName(), selectedItem.getVisceraFixedRecordId(), selectedItem.getIsHandwork(), 
						selectedItem.getSpecialFlag(), selectedItem.getAnatomyPosCode(), selectedItem.getAnatomyPos(),
						selectedItem.getAnatomyFindingFlag(), selectedItem.getAnatomyFindingCode(), 
						selectedItem.getAnatomyFingding(), selectedItem.getBodySurfacePos()));
				sn++;
				hasPlus = true;
			}
//			String visceraOrTissueName = obj.getVisceraOrTissueName();
//			String visceraCode = obj.getVisceraCode();
//			Integer visceraType = obj.getVisceraType();
//			String visceraFixedRecordId = obj.getVisceraFixedRecordId();
			Integer number = animalCode2NumberMap.get(animalCode);
			if(null == number){
				number = 1;
			}else{
				number++;
			}
			animalCode2NumberMap.put(animalCode, number);
			String sliceCode = animalCode+"g-"+number;
//			data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId));
			data_sliceCodeTable.add(new AnimalTissueCode(sn, sliceCode, animalCode, obj.getVisceraOrTissueName(), obj.getVisceraType(), 
					obj.getVisceraCode(), obj.getVisceraName(), obj.getSubVisceraCode(),
					obj.getSubVisceraName(), obj.getVisceraFixedRecordId(), obj.getIsHandwork(), 
					obj.getSpecialFlag(), obj.getAnatomyPosCode(), obj.getAnatomyPos(),
					obj.getAnatomyFindingFlag(), obj.getAnatomyFindingCode(), 
					obj.getAnatomyFingding(), obj.getBodySurfacePos()));
		}
		
		if(!hasPlus){
			sn++;
			selected = sn -1;
			Integer number = animalCode2NumberMap.get(selectedAnimalCode);
			if(null == number){
				number = 1;
			}else{
				number++;
			}
			animalCode2NumberMap.put(selectedAnimalCode, number);
			String sliceCode = selectedAnimalCode+"g-"+number;
//			data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,selectedAnimalCode,
//			selectedVisceraOrTissueName,selectedVisceraType,selectedVisceraCode,selectedVisceraFixedRecordId));
			data_sliceCodeTable.add(new AnimalTissueCode(sn, sliceCode, selectedAnimalCode, selectedItem.getVisceraOrTissueName(), selectedItem.getVisceraType(), 
					selectedItem.getVisceraCode(), selectedItem.getVisceraName(), selectedItem.getSubVisceraCode(),
					selectedItem.getSubVisceraName(), selectedItem.getVisceraFixedRecordId(), selectedItem.getIsHandwork(), 
					selectedItem.getSpecialFlag(), selectedItem.getAnatomyPosCode(), selectedItem.getAnatomyPos(),
					selectedItem.getAnatomyFindingFlag(), selectedItem.getAnatomyFindingCode(), 
					selectedItem.getAnatomyFingding(), selectedItem.getBodySurfacePos()));
		}
		
		sliceCodeTable.getSelectionModel().select(selected);
	}

	/**向左移
	 * @param event
	 */
	@FXML
	private void onDelBtnAction(ActionEvent event){
		AnimalTissueCode selectedItem = sliceCodeTable.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择数据！");
			return ;
		}
		sliceCodeTableMinusOne(selectedItem);
		exitBtn.setText("取消");
	}
	/**向上移
	 * @param event
	 */
	@FXML
	private void onUpBtnAction(ActionEvent event){
		int selectedIndex = sliceCodeTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex > -1 ){
			//对应编号索引未签字
			if(null == tblTissueSliceIndex || null == tblTissueSliceIndex.getOperatorSign()
					|| "".equals(tblTissueSliceIndex.getOperatorSign().trim())){
				int nextIndex = 0;
				//上移
				if(selectedIndex != 0){
					nextIndex = selectedIndex -1;
					AnimalTissueCode animalTissueCode = data_sliceCodeTable.get(selectedIndex);
					AnimalTissueCode animalTissueCode_next = data_sliceCodeTable.get(nextIndex);
					if(animalTissueCode.getAnimalCode().equals(animalTissueCode_next.getAnimalCode())){
						//selectedIndex
						AnimalTissueCode newAnimalTissueCode = new AnimalTissueCode(animalTissueCode.getSn(), animalTissueCode.getSliceCode(),
								animalTissueCode.getAnimalCode(), 
								animalTissueCode_next.getVisceraOrTissueName(), animalTissueCode_next.getVisceraType(), 
								animalTissueCode_next.getVisceraCode(), animalTissueCode_next.getVisceraName(),
								animalTissueCode_next.getSubVisceraCode(),
								animalTissueCode_next.getSubVisceraName(), animalTissueCode_next.getVisceraFixedRecordId(), 
								animalTissueCode_next.getIsHandwork(), 
								animalTissueCode_next.getSpecialFlag(), animalTissueCode_next.getAnatomyPosCode(), 
								animalTissueCode_next.getAnatomyPos(),
								animalTissueCode_next.getAnatomyFindingFlag(), animalTissueCode_next.getAnatomyFindingCode(), 
								animalTissueCode_next.getAnatomyFingding(), animalTissueCode_next.getBodySurfacePos());
						//nextIndex
						AnimalTissueCode newAnimalTissueCode_next = new AnimalTissueCode(animalTissueCode_next.getSn(), animalTissueCode_next.getSliceCode(),
								animalTissueCode_next.getAnimalCode(), 
								animalTissueCode.getVisceraOrTissueName(), animalTissueCode.getVisceraType(), 
								animalTissueCode.getVisceraCode(), animalTissueCode.getVisceraName(),
								animalTissueCode.getSubVisceraCode(),
								animalTissueCode.getSubVisceraName(), animalTissueCode.getVisceraFixedRecordId(), 
								animalTissueCode.getIsHandwork(), 
								animalTissueCode.getSpecialFlag(), animalTissueCode.getAnatomyPosCode(), 
								animalTissueCode.getAnatomyPos(),
								animalTissueCode.getAnatomyFindingFlag(), animalTissueCode.getAnatomyFindingCode(), 
								animalTissueCode.getAnatomyFingding(), animalTissueCode.getBodySurfacePos());
						data_sliceCodeTable.set(selectedIndex, newAnimalTissueCode);
						data_sliceCodeTable.set(nextIndex, newAnimalTissueCode_next);
						sliceCodeTable.getSelectionModel().selectPrevious();
					}
				}
			}
		}
	}
	/**向下移
	 * @param event
	 */
	@FXML
	private void onDownBtnAction(ActionEvent event){
		int selectedIndex = sliceCodeTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex > -1 ){
			//对应编号索引未签字
			if(null == tblTissueSliceIndex || null == tblTissueSliceIndex.getOperatorSign()
					|| "".equals(tblTissueSliceIndex.getOperatorSign().trim())){
				int nextIndex = 0;
				//下移
				if(selectedIndex != data_sliceCodeTable.size() - 1){
					nextIndex = selectedIndex +1 ;
					AnimalTissueCode animalTissueCode = data_sliceCodeTable.get(selectedIndex);
					AnimalTissueCode animalTissueCode_next = data_sliceCodeTable.get(nextIndex);
					if(animalTissueCode.getAnimalCode().equals(animalTissueCode_next.getAnimalCode())){
						//selectedIndex
						AnimalTissueCode newAnimalTissueCode = new AnimalTissueCode(animalTissueCode.getSn(), animalTissueCode.getSliceCode(),
								animalTissueCode.getAnimalCode(), 
								animalTissueCode_next.getVisceraOrTissueName(), animalTissueCode_next.getVisceraType(), 
								animalTissueCode_next.getVisceraCode(), animalTissueCode_next.getVisceraName(),
								animalTissueCode_next.getSubVisceraCode(),
								animalTissueCode_next.getSubVisceraName(), animalTissueCode_next.getVisceraFixedRecordId(), 
								animalTissueCode_next.getIsHandwork(), 
								animalTissueCode_next.getSpecialFlag(), animalTissueCode_next.getAnatomyPosCode(), 
								animalTissueCode_next.getAnatomyPos(),
								animalTissueCode_next.getAnatomyFindingFlag(), animalTissueCode_next.getAnatomyFindingCode(), 
								animalTissueCode_next.getAnatomyFingding(), animalTissueCode_next.getBodySurfacePos());
						//nextIndex
						AnimalTissueCode newAnimalTissueCode_next = new AnimalTissueCode(animalTissueCode_next.getSn(), animalTissueCode_next.getSliceCode(),
								animalTissueCode_next.getAnimalCode(), 
								animalTissueCode.getVisceraOrTissueName(), animalTissueCode.getVisceraType(), 
								animalTissueCode.getVisceraCode(), animalTissueCode.getVisceraName(),
								animalTissueCode.getSubVisceraCode(),
								animalTissueCode.getSubVisceraName(), animalTissueCode.getVisceraFixedRecordId(), 
								animalTissueCode.getIsHandwork(), 
								animalTissueCode.getSpecialFlag(), animalTissueCode.getAnatomyPosCode(), 
								animalTissueCode.getAnatomyPos(),
								animalTissueCode.getAnatomyFindingFlag(), animalTissueCode.getAnatomyFindingCode(), 
								animalTissueCode.getAnatomyFingding(), animalTissueCode.getBodySurfacePos());
						data_sliceCodeTable.set(selectedIndex, newAnimalTissueCode);
						data_sliceCodeTable.set(nextIndex, newAnimalTissueCode_next);
						sliceCodeTable.getSelectionModel().selectNext();
					}
				}
			}
		}
	}
	
	
	
	
	/**切片编号表减 1，另一表加 1
	 * @param selectedItem
	 */
	private void sliceCodeTableMinusOne(AnimalTissueCode selectedItem) {
		
		String selectedAnimalCode = selectedItem.getAnimalCode();
//		String selectedVisceraOrTissueName = selectedItem.getVisceraOrTissueName();
//		String selectedVisceraCode = selectedItem.getVisceraCode();
//		Integer selectedVisceraType = selectedItem.getVisceraType();
//		String selectedVisceraFixedRecordId = selectedItem.getVisceraFixedRecordId();
		
		//1.先减一
		sliceCodeTable.getSelectionModel().clearSelection();
		data_sliceCodeTable.remove(selectedItem);
		ObservableList<AnimalTissueCode> data_sliceCodeTable2 = FXCollections.observableArrayList(data_sliceCodeTable);
		data_sliceCodeTable.clear();
		animalCode2NumberMap = new HashMap<String,Integer>(animalCode2NumberMap_old);
		int sn = 0;
		for(AnimalTissueCode obj:data_sliceCodeTable2){
			sn++;
			String animalCode = obj.getAnimalCode();
//			String visceraOrTissueName = obj.getVisceraOrTissueName();
//			String visceraCode = obj.getVisceraCode();
//			Integer visceraType = obj.getVisceraType();
//			String visceraFixedRecordId = obj.getVisceraFixedRecordId();
			Integer number = animalCode2NumberMap.get(animalCode);
			if(null == number){
				number = 1;
			}else{
				number++;
			}
			animalCode2NumberMap.put(animalCode, number);
			String sliceCode = animalCode+"g-"+number;
//			data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId));
		
			data_sliceCodeTable.add(new AnimalTissueCode(sn, sliceCode, animalCode, obj.getVisceraOrTissueName(), obj.getVisceraType(), 
					obj.getVisceraCode(), obj.getVisceraName(), obj.getSubVisceraCode(),
					obj.getSubVisceraName(), obj.getVisceraFixedRecordId(), obj.getIsHandwork(), 
					obj.getSpecialFlag(), obj.getAnatomyPosCode(), obj.getAnatomyPos(),
					obj.getAnatomyFindingFlag(), obj.getAnatomyFindingCode(), 
					obj.getAnatomyFingding(), obj.getBodySurfacePos()));
		}
		
		
		//2.另一表 加 1
		AnimalTissueCode newItem =null;
//			newItem = new AnimalTissueCode(0,"",selectedAnimalCode,selectedVisceraOrTissueName,
//				selectedVisceraType,selectedVisceraCode,selectedVisceraFixedRecordId);
		newItem = new AnimalTissueCode(0, "", selectedAnimalCode, selectedItem.getVisceraOrTissueName(), selectedItem.getVisceraType(), 
				selectedItem.getVisceraCode(), selectedItem.getVisceraName(), selectedItem.getSubVisceraCode(),
				selectedItem.getSubVisceraName(), selectedItem.getVisceraFixedRecordId(), selectedItem.getIsHandwork(), 
				selectedItem.getSpecialFlag(), selectedItem.getAnatomyPosCode(), selectedItem.getAnatomyPos(),
				selectedItem.getAnatomyFindingFlag(), selectedItem.getAnatomyFindingCode(), 
				selectedItem.getAnatomyFingding(), selectedItem.getBodySurfacePos());
		int selectedIndex = 0;
		for(AnimalTissueCode obj:data_animalVisceraTable){
			String animalCode = obj.getAnimalCode();
			if(selectedAnimalCode.compareTo(animalCode)<1){
				break;
			}
			selectedIndex++;
		}
		data_animalVisceraTable.add(selectedIndex, newItem);
		
		animalVisceraTable.getSelectionModel().select(selectedIndex);
	}
	/**脏器表加 1，并选中
	 * @param selectedItem
	 */
	public void animalVisceraTablePlusOne(AnimalTissueCode selectedItem) {
		
		String selectedAnimalCode = selectedItem.getAnimalCode();
		//2.另一表 加 1
		AnimalTissueCode newItem =null;
		newItem = new AnimalTissueCode(0, "", selectedAnimalCode, selectedItem.getVisceraOrTissueName(), selectedItem.getVisceraType(), 
				selectedItem.getVisceraCode(), selectedItem.getVisceraName(), selectedItem.getSubVisceraCode(),
				selectedItem.getSubVisceraName(), selectedItem.getVisceraFixedRecordId(), selectedItem.getIsHandwork(), 
				selectedItem.getSpecialFlag(), selectedItem.getAnatomyPosCode(), selectedItem.getAnatomyPos(),
				selectedItem.getAnatomyFindingFlag(), selectedItem.getAnatomyFindingCode(), 
				selectedItem.getAnatomyFingding(), selectedItem.getBodySurfacePos());
		int selectedIndex = 0;
		for(AnimalTissueCode obj:data_animalVisceraTable){
			String animalCode = obj.getAnimalCode();
			if(selectedAnimalCode.compareTo(animalCode)<1){
				break;
			}
			selectedIndex++;
		}
		data_animalVisceraTable.add(selectedIndex, newItem);
		
		animalVisceraTable.getSelectionModel().select(selectedIndex);
	}
	
	/**判断是否存在
	 * @param animalCode
	 * @param visceraOrTissueName
	 * @return
	 */
	public boolean isExist(String animalCode,String visceraOrTissueName){
		boolean flag = false;
		String visceraOrTissueName2 = visceraOrTissueName.replaceAll(" ", "");
		for(AnimalTissueCode obj:data_animalVisceraTable){
			if(animalCode.equals(obj.getAnimalCode()) && visceraOrTissueName2.equals(obj.getVisceraOrTissueName().replaceAll(" ", ""))){
				flag = true;
				break;
			}
		}
		if(!flag){
			for(AnimalTissueCode obj:data_sliceCodeTable){
				if(animalCode.equals(obj.getAnimalCode()) && visceraOrTissueName2.equals(obj.getVisceraOrTissueName().replaceAll(" ", ""))){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	/**其他脏器或组织
	 * @param event
	 */
	@FXML
	private void onOtherBtnAction(ActionEvent event){
		Stage stage = Main.stageMap.get("OtherTissue");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				OtherTissue.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("OtherTissue",stage);
		}else{
			stage.show();
		}
		OtherTissue.getInstance().loadData(studyNo);
	}
	
	/**加载数据
	 * @param studyNo
	 * 
	 */
	public void loadData(String studyNo) {
		//1.
		this.studyNo = studyNo;
		//2.动物编号->对应切片编号数量(非常规，签字确认过的)
		//加载时，或签字确认是更新
		animalCode2NumberMap_old = BaseService.getInstance().getTblTissueSliceIndexService().
				getAnimalCode2NumberMap(studyNo);
		//3.
		updateCombBox();
	}
	
	/**
	 * 更新页面值（包括按钮、多选框、表格等状态和值）
	 */
	protected void updatePageData() {
		//1.把几按钮置为最初状态
		backButtonState();
		//2.若签过字，签字、保存、左移、右移按钮不可用
		updateButtonState();
		//3.更新切片编号表
		updateSliceCodeTable();
		//4.更新动物脏器表
		updateAnimalTissueCodeTable();
		//5.更新exitBtn 的Text值
		updateExitBtnText();
		
	}
	
	/**
	 * 更新comboBox
	 */
	private void updateCombBox(){
		data_comboBox.clear();
		tissueSliceIndexList.clear();
		comboBox.getSelectionModel().clearSelection();
		tissueSliceIndexList = BaseService.getInstance().getTblTissueSliceIndexService().getFCGListByStudyNo(studyNo);
		if(null != tissueSliceIndexList && tissueSliceIndexList.size() > 0){
			int i = 1;
			String item = "";
			for(TblTissueSliceIndex obj:tissueSliceIndexList){
				item = "非常规组织取材编号（第"+i+"次）";
				if(null == obj.getOperatorSign() || "".equals(obj.getOperatorSign())){
					item = "非常规组织取材编号（第"+i+"次,未签字）";
				}
				data_comboBox.add(item);
				i++;
			}
			//如果都已签字，添加  追加组织取材编号-新建
			if(null != tissueSliceIndexList.get(i-2).getOperatorSign() 
					&& !"".equals(tissueSliceIndexList.get(i-2).getOperatorSign())){
				data_comboBox.add("非常规组织取材编号-新建");
				tissueSliceIndexList.add(null);
			}
		}else{
			tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
			data_comboBox.add("非常规组织取材编号-新建");
			tissueSliceIndexList.add(null);
		}
		comboBox.getSelectionModel().selectLast();
	}
	
	/**
	 * 更新exitBtn 的Text值
	 */
	private void updateExitBtnText() {
		if(null == tblTissueSliceIndex){
			if(data_sliceCodeTable.size() > 0){
				exitBtn.setText("取消");
			}else{
				exitBtn.setText("关闭");
			}
		}else{
			exitBtn.setText("关闭");
		}
	}
		
	/**
	 * 更新animalVisceraTable
	 */
	private void updateAnimalTissueCodeTable() {
		data_animalVisceraTable.clear();
		
		if(null != tblTissueSliceIndex){
			//编辑，加载对应列表
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService()
					.getNoSliceCodeListByStudyNo(studyNo);
			if(null != mapList){
				for(Map<String,Object> map:mapList){
					String animalCode = (String) map.get("animalCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					String visceraCode = (String) map.get("visceraCode");
					Integer visceraType = (Integer) map.get("visceraType");
					String visceraFixedRecordId = (String) map.get("visceraFixedRecordId");
//					animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId,"+
//					 *	visceraName,subVisceraCode,subVisceraName,"+
//					 *	specialFlag,anatomyPosCode,anatomyPos,anatomyFindingCode,"+
//					 *	anatomyFindingFlag,anatomyFingding,bodySurfacePos
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					Integer specialFlag = (Integer) map.get("specialFlag");
					String anatomyPosCode = (String) map.get("anatomyPosCode");
					String anatomyPos = (String) map.get("anatomyPos");
					String anatomyFindingCode = (String) map.get("anatomyFindingCode");
					Integer anatomyFindingFlag = (Integer) map.get("anatomyFindingFlag");
					String anatomyFingding = (String) map.get("anatomyFingding");
					String bodySurfacePos = (String) map.get("bodySurfacePos");
					
//					data_animalVisceraTable.add(new AnimalTissueCode(0,"",animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId));
					data_animalVisceraTable.add(new AnimalTissueCode(0, "", animalCode, visceraOrTissueName, visceraType, visceraCode, visceraName, subVisceraCode, subVisceraName, 
							visceraFixedRecordId, 0, specialFlag, anatomyPosCode, anatomyPos, anatomyFindingFlag, anatomyFindingCode, anatomyFingding, bodySurfacePos));
				}
			}
		}else{
			//新增，加载专题对应的
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService().getAnatomyCheckTissueCodeByStudyNo(studyNo);
			if(null != mapList){
				//r.animalCode,r.visceraOrTissueName,r.visceraType,r.visceraCode,r.visceraFixedRecordId
				for(Map<String,Object> map:mapList){
					String animalCode = (String) map.get("animalCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					String visceraCode = (String) map.get("visceraCode");
					Integer visceraType = (Integer) map.get("visceraType");
					String visceraFixedRecordId = (String) map.get("visceraFixedRecordId");
					
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					Integer specialFlag = (Integer) map.get("specialFlag");
					String anatomyPosCode = (String) map.get("anatomyPosCode");
					String anatomyPos = (String) map.get("anatomyPos");
					String anatomyFindingCode = (String) map.get("anatomyFindingCode");
					Integer anatomyFindingFlag = (Integer) map.get("anatomyFindingFlag");
					String anatomyFingding = (String) map.get("anatomyFingding");
					String bodySurfacePos = (String) map.get("bodySurfacePos");
					
//					data_animalVisceraTable.add(new AnimalTissueCode(0,"",animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId));
					data_animalVisceraTable.add(new AnimalTissueCode(0, "", animalCode, visceraOrTissueName, visceraType, visceraCode, visceraName, subVisceraCode, subVisceraName, 
							visceraFixedRecordId, 0, specialFlag, anatomyPosCode, anatomyPos, anatomyFindingFlag, anatomyFindingCode, anatomyFingding, bodySurfacePos));
				}
			}
			
		}
	}

	/**
	 * 更新切片编号表
	 */
	private void updateSliceCodeTable() {
		data_sliceCodeTable.clear();
		animalCode2NumberMap = new HashMap<String,Integer>(animalCode2NumberMap_old);
		if(null != tblTissueSliceIndex){
			//编辑，加载对应列表
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService()
					.getSliceCodeByIndexId(tblTissueSliceIndex.getId());
			if(null != mapList){
				//r.animalCode,r.visceraOrTissueName,r.visceraType,r.visceraCode,r.visceraFixedRecordId
				int sn = 0;
				for(Map<String,Object> map:mapList){
					sn++;
					String animalCode = (String) map.get("animalCode");
					String sliceCode = (String) map.get("sliceCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					String visceraCode = (String) map.get("visceraCode");
					Integer visceraType = (Integer) map.get("visceraType");
					String visceraFixedRecordId = (String) map.get("visceraFixedRecordId");
					Integer number = animalCode2NumberMap.get(animalCode);
					if(null == number){
						number = 1;
					}else{
						number++;
					}
					animalCode2NumberMap.put(animalCode, number);
//					data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,animalCode,visceraOrTissueName,
//					visceraType,visceraCode,visceraFixedRecordId));
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					Integer specialFlag = (Integer) map.get("specialFlag");
					String anatomyPosCode = (String) map.get("anatomyPosCode");
					String anatomyPos = (String) map.get("anatomyPos");
					String anatomyFindingCode = (String) map.get("anatomyFindingCode");
					Integer anatomyFindingFlag = (Integer) map.get("anatomyFindingFlag");
					String anatomyFingding = (String) map.get("anatomyFingding");
					String bodySurfacePos = (String) map.get("bodySurfacePos");
					Integer isHandwork = (Integer) map.get("isHandwork");
					
					data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode, animalCode, visceraOrTissueName, 
							visceraType, visceraCode, visceraName, subVisceraCode, subVisceraName, 
							visceraFixedRecordId, isHandwork, specialFlag, anatomyPosCode, anatomyPos, 
							anatomyFindingFlag, anatomyFindingCode, anatomyFingding, bodySurfacePos));
				}
			}
		}else{
			//新增，加载专题对应的
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService().getFixedVisceraTissueCodeByStudyNo(studyNo);
			if(null != mapList){
				//r.animalCode,r.visceraOrTissueName,r.visceraType,r.visceraCode,r.visceraFixedRecordId
				int sn = 0;
				for(Map<String,Object> map:mapList){
					sn++;
					String animalCode = (String) map.get("animalCode");
					String visceraOrTissueName = (String) map.get("visceraOrTissueName");
					String visceraCode = (String) map.get("visceraCode");
					Integer visceraType = (Integer) map.get("visceraType");
					String visceraFixedRecordId = (String) map.get("visceraFixedRecordId");
					Integer number = animalCode2NumberMap.get(animalCode);
					if(null == number){
						number = 1;
					}else{
						number++;
					}
					animalCode2NumberMap.put(animalCode, number);
					String sliceCode = animalCode+"g-"+number;
//					data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode,animalCode,visceraOrTissueName,visceraType,visceraCode,visceraFixedRecordId));
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					Integer specialFlag = (Integer) map.get("specialFlag");
					String anatomyPosCode = (String) map.get("anatomyPosCode");
					String anatomyPos = (String) map.get("anatomyPos");
					String anatomyFindingCode = (String) map.get("anatomyFindingCode");
					Integer anatomyFindingFlag = (Integer) map.get("anatomyFindingFlag");
					String anatomyFingding = (String) map.get("anatomyFingding");
					String bodySurfacePos = (String) map.get("bodySurfacePos");
					
					data_sliceCodeTable.add(new AnimalTissueCode(sn,sliceCode, animalCode, visceraOrTissueName, visceraType, visceraCode, visceraName, subVisceraCode, subVisceraName, 
							visceraFixedRecordId, 0, specialFlag, anatomyPosCode, anatomyPos, anatomyFindingFlag, anatomyFindingCode, anatomyFingding, bodySurfacePos));
				}
			}
			
		}
		
	}

	/**
	 * 更新按钮状态（若签过字，签字、保存、左移、右移按钮不可用）
	 */
	private void updateButtonState() {
		if(null != tblTissueSliceIndex && null != tblTissueSliceIndex.getOperatorSign()
				&& !"".equals(tblTissueSliceIndex.getOperatorSign()) ){
			signBtn.setDisable(true);
			saveBtn.setDisable(true);
			addBtn.setDisable(true);
			delBtn.setDisable(true);
			otherBtn.setDisable(true);
		}
		
	}

	/**
	 * 把几按钮置为最初状态
	 */
	private void backButtonState() {
		signBtn.setDisable(false);
		saveBtn.setDisable(false);
		addBtn.setDisable(false);
		delBtn.setDisable(false);
		printBtn.setDisable(false);
		otherBtn.setDisable(false);
		
		upBtn.setDisable(true);
		downBtn.setDisable(true);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceFeichanggui.fxml"));
		Scene scene = new Scene(root, 758, 419);
		stage.setScene(scene);
		stage.setTitle("非常规组织取材编号");
		stage.setResizable(false);
		stage.setMinWidth(608);
		stage.setMinHeight(429);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				TissueSlicePage.getInstance().updateSliceIndexTable();
				TissueSlicePage.getInstance().updateSliceCodeTable();
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

	/**动物、脏器或组织、编号等
	 * @author Administrator
	 *
	 */
	public static class  AnimalTissueCode{
		
		private IntegerProperty sn;						//序号
		private StringProperty sliceCode;               //蜡块编号
		private StringProperty animalCode;              //动物号
		private StringProperty visceraOrTissueName;     //脏器或组织
		private IntegerProperty visceraType;            //脏器类型
		private StringProperty visceraCode;             //脏器编号
		private StringProperty visceraName;				//
		private StringProperty subVisceraCode;				//
		private StringProperty subVisceraName;				//
		private StringProperty visceraFixedRecordId;   //脏器固定记录Id
		
		private IntegerProperty isHandwork;			//0:否   1：是
		private IntegerProperty specialFlag; 		//是否非常规
		private StringProperty anatomyPosCode;     //解剖学所见部位编号20
		private StringProperty anatomyPos;         //解剖学所见部位60
		private IntegerProperty anatomyFindingFlag;	   //通用/特殊所见标识    0:通用   1:特殊
		private StringProperty anatomyFindingCode; //解剖所见编号20
		private StringProperty anatomyFingding;    //解剖所见100
		private StringProperty bodySurfacePos;     //体表部位60
		
		public AnimalTissueCode(){
			super();
		}
		public AnimalTissueCode(int sn,String sliceCode,String animalCode,String visceraOrTissueName,
				int visceraType,String visceraCode,String visceraName,
				String subVisceraCode,String subVisceraName,String visceraFixedRecordId,
				int isHandwork,int specialFlag,String anatomyPosCode,String anatomyPos ,
				int anatomyFindingFlag ,String  anatomyFindingCode,String anatomyFingding,String bodySurfacePos){
			setSn(sn);
			setSliceCode(sliceCode);
			setAnimalCode(animalCode);
			setVisceraOrTissueName(visceraOrTissueName);
			setVisceraType(visceraType);
			setVisceraCode(visceraCode);
			setVisceraName(visceraName);
			setSubVisceraName(subVisceraName);
			setSubVisceraCode(subVisceraCode);
			setVisceraFixedRecordId(visceraFixedRecordId);
			
			setIsHandwork(isHandwork);
			setSpecialFlag(specialFlag);
			setAnatomyPosCode(anatomyPosCode);
			setAnatomyPos(anatomyPos);
			setAnatomyFindingFlag(anatomyFindingFlag);
			setAnatomyFindingCode(anatomyFindingCode);
			setAnatomyFingding(anatomyFingding);
			setBodySurfacePos(bodySurfacePos);
		}
		public Integer getSn() {
			return sn.get();
		}
		public void setSn(Integer sn) {
			this.sn = new SimpleIntegerProperty(sn);
		}
		public String getSliceCode() {
			return sliceCode.get();
		}
		public void setSliceCode(String sliceCode) {
			this.sliceCode = new SimpleStringProperty(sliceCode);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getVisceraOrTissueName() {
			return visceraOrTissueName.get();
		}
		public void setVisceraOrTissueName(String visceraOrTissueName) {
			this.visceraOrTissueName = new SimpleStringProperty(visceraOrTissueName);
		}
		public Integer getVisceraType() {
			return visceraType.get();
		}
		public void setVisceraType(Integer visceraType) {
			this.visceraType = new SimpleIntegerProperty(visceraType);
		}
		public String getVisceraCode() {
			return visceraCode.get();
		}
		public void setVisceraCode(String visceraCode) {
			this.visceraCode = new SimpleStringProperty(visceraCode);
		}
		public String getVisceraFixedRecordId() {
			return visceraFixedRecordId.get();
		}
		public void setVisceraFixedRecordId(String visceraFixedRecordId) {
			this.visceraFixedRecordId = new SimpleStringProperty(visceraFixedRecordId);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getSubVisceraCode() {
			return subVisceraCode.get();
		}
		public void setSubVisceraCode(String subVisceraCode) {
			this.subVisceraCode = new SimpleStringProperty(subVisceraCode);
		}
		public String getSubVisceraName() {
			return subVisceraName.get();
		}
		public void setSubVisceraName(String subVisceraName) {
			this.subVisceraName = new SimpleStringProperty(subVisceraName);
		}
		public Integer getIsHandwork() {
			return isHandwork.get();
		}
		public void setIsHandwork(Integer isHandwork) {
			this.isHandwork = new SimpleIntegerProperty(isHandwork);
		}
		public Integer getSpecialFlag() {
			return specialFlag.get();
		}
		public void setSpecialFlag(Integer specialFlag) {
			this.specialFlag = new SimpleIntegerProperty(specialFlag);
		}
		public String getAnatomyPosCode() {
			return anatomyPosCode.get();
		}
		public void setAnatomyPosCode(String anatomyPosCode) {
			this.anatomyPosCode = new SimpleStringProperty(anatomyPosCode);
		}
		public String getAnatomyPos() {
			return anatomyPos.get();
		}
		public void setAnatomyPos(String anatomyPos) {
			this.anatomyPos = new SimpleStringProperty(anatomyPos);
		}
		public Integer getAnatomyFindingFlag() {
			return anatomyFindingFlag.get();
		}
		public void setAnatomyFindingFlag(Integer anatomyFindingFlag) {
			this.anatomyFindingFlag = new SimpleIntegerProperty(anatomyFindingFlag);
		}
		public String getAnatomyFindingCode() {
			return anatomyFindingCode.get();
		}
		public void setAnatomyFindingCode(String anatomyFindingCode) {
			this.anatomyFindingCode = new SimpleStringProperty(anatomyFindingCode);
		}
		public String getAnatomyFingding() {
			return anatomyFingding.get();
		}
		public void setAnatomyFingding(String anatomyFingding) {
			this.anatomyFingding = new SimpleStringProperty(anatomyFingding);
		}
		public String getBodySurfacePos() {
			return bodySurfacePos.get();
		}
		public void setBodySurfacePos(String bodySurfacePos) {
			this.bodySurfacePos = new SimpleStringProperty(bodySurfacePos);
		}
		
		
		
	}
	
}
