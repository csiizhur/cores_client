package com.lanen.view.test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.DictLevel;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyAnimal;
import com.lanen.model.path.TblHistopathCheck;
import com.lanen.util.DateUtil;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.sign.Sign;

import datecontrol.DatePicker;
import datecontrol.Junit;

/**
 * @author Administrator
 *
 */
/**复检后  编辑
 * @author Administrator
 *
 */
public class HistopathCheckEdit extends Application implements Initializable {
	
	
	//组织学所见tab----------------------------------------------------------------------
	
	@FXML
	private Label animalCodeVisceraNameLabel;
	
	@FXML	
	private Button registerResultBtn_1;	//登记结果——动物、脏器tab
	@FXML
	private Button referToBtn;		//参照所见
	@FXML
	private Button checkFinishBtn;		//检查完成
	
	@FXML
	private ComboBox<String> searchComboBox; // 快速查找ComboBox
	@FXML
	private TextField searchTextField;//快速查找TextField
	@FXML
	private ListView<String> searchListView ; //快速查找ListView
	private ObservableList<String> data_searchListView = FXCollections.observableArrayList();
	
	/**
	 * 组织学用语ListView
	 */
	@FXML
	private ListView<String> histoPoListView;
	private ObservableList<String> data_histoPoListView = FXCollections.observableArrayList();
	/**
	 * 中文名称-DictPathCommon
	 */
	private Map<String,DictPathCommon> descCNDictPathCommonMap_histoPoListView = new HashMap<String,DictPathCommon>();
	private Map<String,String> pyDescCNMap_histoPoListView = new HashMap<String,String>();//拼音首字母-中文名称
	/**
	 * 病变描述ListView
	 */
	@FXML
	private ListView<String> lesionFindingListView;
	private ObservableList<String> data_lesionFindingListView = FXCollections.observableArrayList();
	/**
	 * 中文名称-DictPathCommon
	 */
	private Map<String,DictPathCommon> descCNDictPathCommonMap_lesionFindingListView = new HashMap<String,DictPathCommon>();
	private Map<String,String> pyDescCNMap_lesionFindingListView = new HashMap<String,String>();//拼音首字母-中文名称
	@FXML
	private Label histopathCheckLabel;				//解剖学所见Label
	
	@FXML
	private ComboBox<String> dictSortMethodComboBox; // 字典排序方式
	/**
	 * 字典排序方式  :1,字典排序  2，字母排序 3,使用频度
	 */
	private static int dictSortMethod = 1;	//字典排序方式  :1,字典排序  2，字母排序 3,使用频度
	
	@FXML
	private HBox hbox;
	@FXML
	private Label tumorNumberLabel;			//肿瘤数量Label
	@FXML
	private ComboBox<String> tumorNumberComboBox;			//肿瘤数量ComboBox
	@FXML
	private Label levelLabel;			//程度Label
	@FXML
	private ComboBox<String> levelComboBox;			//肿瘤数量ComboBox
	private ObservableList<String> data_levelComboBox = FXCollections.observableArrayList();
	
	@FXML
	private TextField remarkText;		//备注TextField
	
	
	/**
	 * 未见异常 RadioButton
	 */
	@FXML
	private RadioButton noAbnormalRadio;
	/**
	 * 异常 RadioButton
	 */
	@FXML
	private RadioButton abnormalRadio;
	/**
	 * 非肿瘤 RadioButton
	 */
	@FXML
	private RadioButton noTumorRadio;
	/**
	 * 良性(肿瘤) RadioButton
	 */
	@FXML
	private RadioButton lxTumorRadio;
	/**
	 * 恶性（肿瘤） RadioButton
	 */
	@FXML
	private RadioButton exTumorRadio;
	/**
	 * 原发 RadioButton
	 */
	@FXML
	private RadioButton noMetastasisRadio;
	/**
	 * 侵袭 RadioButton
	 */
	@FXML
	private RadioButton qinxiRadio;
	/**
	 * 转移 RadioButton
	 */
	@FXML
	private RadioButton metastasisRadio;
	
	/**
	 * 源发脏器  Hyperlink
	 */
	@FXML
	private Hyperlink primaryViscerHyperlink;
	
	
	@FXML
	private AnchorPane tumorPosPane;		//肿瘤位置AnchorPane
	@FXML
	private AnchorPane tumorCharacterPane;	//肿瘤特性AnchorPane		
	@FXML
	private AnchorPane tumorOccurPane;		//肿瘤发生日期	AnchorPane
	
	@FXML
	private RadioButton tumorPosRadio1;			//肿瘤位置  1：浅表
	@FXML
	private RadioButton tumorPosRadio2;			//肿瘤位置  2：深部
	@FXML
	private RadioButton tumorCharacterRadio1;			//肿瘤特性  1：偶发
	@FXML
	private RadioButton tumorCharacterRadio2;			//肿瘤特性  2：致死
	@FXML
	private RadioButton tumorCharacterRadio3;			//肿瘤特性  3：不确定
	
	@FXML
	private HBox tumorOccurDateHbox;			//肿瘤发生日期Hbox
	
	DatePicker  occurDatePicker = null;
	
	
	
	/**
	 * 组织学所见TableView(tab2上的)
	 */
	@FXML	
	private TableView<HistopathResult> histopathResultTable;
	private ObservableList<HistopathResult> data_histopathResultTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<HistopathResult,String> tumorFlagCol;
	@FXML
	private TableColumn<HistopathResult,String> metastasisFlagCol;
	@FXML
	private TableColumn<HistopathResult,String> checkResultCol_1;
	@FXML
	private TableColumn<HistopathResult,String> primaryVisceraTumorCol;
	@FXML
	private TableColumn<HistopathResult,String> stateCol;
	@FXML
	private TableColumn<HistopathResult,Boolean> operateCol;
	
	@FXML
	private TableColumn<HistopathResult,String> levelCol_1;	//病变程度
	@FXML
	private TableColumn<HistopathResult,String> tumorNumCol_1;	//肿瘤数量
	@FXML
	private TableColumn<HistopathResult,String> tumorPosCol_1;	//肿瘤位置
	@FXML
	private TableColumn<HistopathResult,String> tumorOccurDateCol_1;	//肿瘤发生日期
	@FXML
	private TableColumn<HistopathResult,String> tumorCharacterCol_1;	//肿瘤特性
	@FXML
	private TableColumn<HistopathResult,String> remarkCol_1;	//备注
	 
	
	
	private String studyNo = "";
	private String animalCode = "";
	private String sliceVisceraId = "";
	private String visceraCode = "";
	private Integer visceraType = 0;
	private String visceraName = "";
	private String subVisceraCode = "";
	private String subVisceraName = "";
	private String visceraOrTissueName = "";
	
	/**
	 * 登记检查结果时清空，metastasisRadio not Selected 是清空
	 */
	private String primaryVisceraCode = null;	//源发脏器编号
	private String primaryVisceraName = null;	//源发脏器名称
	private String primaryTumor = null;			//源发肿瘤
	
	
	
	/**
	 * 个体数据
	 */
	@FXML
	private void onIndividualDataHyperlink(ActionEvent event){
		
		Stage stage = Main.stageMap.get("IndividualData");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				IndividualData.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("IndividualData",stage);
		}else{
			stage.show();
		}
		IndividualData.getInstance().loadData(studyNo,animalCode);
	}
	
	/**
	 * 原发脏器/肿瘤
	 */
	@FXML
	private void onPrimaryVisceraHyperlink(ActionEvent event){
		Stage stage = Main.stageMap.get("PrimaryViscera");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				PrimaryViscera.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("PrimaryViscera",stage);
		}else{
			stage.show();
		}
		PrimaryViscera.getInstance().loadData(studyNo,animalCode,visceraCode,"HistopathCheckEdit");
		
	}
	
	/**
	 * 组织学用语
	 */
	@FXML
	private void onHistoposHyperlink(ActionEvent event){
		updateHistoPoListView();
	}
	/**
	 * 病变描述
	 */
	@FXML
	private void onLesionHyperlink(ActionEvent event){
		updateLesionFindingListView();
	}
	/**更新使用频度
	 * @param event
	 */
	@FXML
	void onUpdateUserFrequentnessHyperlink(ActionEvent event){
		BaseService.getInstance().getTblHistopathCheckService().updateFreqCount();
		showMessage("使用频度更新成功！");
	}
	
	/**检查完成
	 * @param event
	 */
	@FXML
	private void onCheckFinishBtn(ActionEvent event){
		boolean isHasResult = false;
		if(data_histopathResultTable.size() > 0){
			for(HistopathResult obj:data_histopathResultTable){
				if(obj.getState().equals("")){
					isHasResult = true;
					break;
				}
			}
		}
		if(!isHasResult){
			showErrorMessage("请登记检查结果！");
			return ;
		}else{
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}
		HistopathReview.getInstance().updateHistopathCheckTable();
	}
	
	/**参照所见
	 * @param event
	 */
	@FXML
	private void onReferBtn(ActionEvent event){
		
		String visceraCode = this.visceraCode;
		if(null == visceraName || "".equals(visceraName)){
			visceraCode = null;
		}
		
		
		//是否已登记缺失
		boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
		if(isRegisterMissing){
			showErrorMessage("当前脏器已登记缺失！");
			return;
		}
		
		//2.2判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
		Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
		if(!json.isSuccess()){
			if("1".equals(json.getMsg())){
				showErrorMessage("对应切片已登记‘未见异常’！");
				return;
			}
		}
		
		//3.
		Stage stage = Main.stageMap.get("ReferFinding");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				ReferFinding.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("ReferFinding",stage);
		}else{
			stage.show();
		}
		ReferFinding.getInstance().loadData(studyNo,animalCode,visceraCode,"HistopathCheckEdit");
	}
	
	/**
	 * 肿瘤发生日期 选择日期
	 */
	@FXML
	private void onTumorOccurDateHyperlink(ActionEvent event){
		//1.动物
//		HistopathAnimal selectedItem = histopathAnimalTable.getSelectionModel().getSelectedItem();
//		String animalCode = null;
//		
//		if(null == selectedItem){
//			showErrorMessage("请先选择动物编号！");
//			return;
//		}else{
//			animalCode = selectedItem.getAnimalCode();
//		}
		//2.
		Stage stage = Main.stageMap.get("WatchPage");
		if(null == stage){
			stage =new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
			try {
				WatchPage.getInstance().start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Main.stageMap.put("WatchPage",stage);
		}else{
			stage.show();
		}
		WatchPage.getInstance().loadData(studyNo,animalCode,"HistopathCheckEdit");
	}
	
	/**增加参照所见
	 * @param id
	 * @param tumorNum
	 */
	public void AddReferItem(String referId, int tumorNum) {
		String visceraCode = this.visceraCode;
		if(null == visceraName || "".equals(visceraName)){
			visceraCode = null;
		}
		//2.2判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
		Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
		if(!json.isSuccess()){
			if("1".equals(json.getMsg())){
				showErrorMessage("当前脏器或组织已登记‘未见异常’！");
				return;
			}
		}
		
		String operator = SaveUserInfo.getUserName();
		TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
		tblHistopathCheck.setTaskId("");
		tblHistopathCheck.setStudyNo(studyNo);
		tblHistopathCheck.setAnimalCode(animalCode);
		tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
		tblHistopathCheck.setOperator(operator);
		tblHistopathCheck.setTumorNum(tumorNum);
		
		tblHistopathCheck.setVisceraType(visceraType);
		tblHistopathCheck.setVisceraCode(visceraCode);
		tblHistopathCheck.setVisceraName(visceraName);
		tblHistopathCheck.setSubVisceraCode(subVisceraCode);
		tblHistopathCheck.setSubVisceraName(subVisceraName);
		
		BaseService.getInstance().getTblHistopathCheckService().saveOne(referId,tblHistopathCheck);
		updateHistopathResultTable();
	}
	/**登记结果(组织学所见tab)
	 * @param event
	 */
	@FXML
	private void onRegisterResultBtn_1(ActionEvent event){
		
		String visceraCode = this.visceraCode;
		if(null == visceraName || "".equals(visceraName)){
			visceraCode = null;
		}
		
		//3.开始准备数据
		String operator = SaveUserInfo.getUserName();
		TblHistopathCheck tblHistopathCheck = new TblHistopathCheck();
		tblHistopathCheck.setTaskId("");
		tblHistopathCheck.setStudyNo(studyNo);
		tblHistopathCheck.setAnimalCode(animalCode);
		tblHistopathCheck.setTissueSliceVisceraRecordId(sliceVisceraId);
		tblHistopathCheck.setOperator(operator);
		
		
		
		//是否已登记缺失
		boolean isRegisterMissing = BaseService.getInstance().getTblHistopathCheckService().isRegisterMissing(studyNo,animalCode,visceraCode,subVisceraCode);
		if(isRegisterMissing){
			showErrorMessage("当前脏器已登记缺失！");
			return;
		}
		
		//未见异常
		int isNoAbnormal = 0;
		if(noAbnormalRadio.isSelected()){
			//3.1判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
				}else{
					showErrorMessage("当前脏器或组织已登记‘异常’！");
				}
				return;
			}
			
			isNoAbnormal = 1;
			tblHistopathCheck.setIsNoAbnormal(isNoAbnormal);
		}else{
			//异常
			
			//3.2判断该  专题、该动物、该组织切片脏器记录Id 是否有记录
			Json json = BaseService.getInstance().getTblHistopathCheckService().checkRecord(studyNo,animalCode,sliceVisceraId,visceraCode,subVisceraCode);
			if(!json.isSuccess()){
				if("1".equals(json.getMsg())){
					showErrorMessage("当前脏器或组织已登记‘未见异常’！");
					return;
				}
			}
			String histoPos = histoPoListView.getSelectionModel().getSelectedItem();
//			if(null == histoPos){
//				showErrorMessage("请选择’解剖学用语‘！");
//				searchComboBox.getSelectionModel().select(0);
//				return ;
//			}
			String lesionFinding = lesionFindingListView.getSelectionModel().getSelectedItem();
			if(null == lesionFinding){
				showErrorMessage("请选择’病变描述‘！");
				searchComboBox.getSelectionModel().select(1);
				return ;
			}
			
			
			int tumorFlag = 0;			//是否肿瘤
			int metastasisFlag = 0;		//是否转移
			int tumorNumber = 0;
//			String primaryVisceraCode = null;       //原发脏器编号20
//			String primaryViscera = null;           //原发脏器100
//			String primaryTumor = null;             //原发肿瘤100
			if(noTumorRadio.isSelected()){
				tumorFlag = 0;
				String level = levelComboBox.getSelectionModel().getSelectedItem();
				tblHistopathCheck.setLevel(level);
			}else {
				if(lxTumorRadio.isSelected()){
					tumorFlag = 1;
				}else{
					tumorFlag = 2;
					if(noMetastasisRadio.isSelected()){
						metastasisFlag = 0;
					}else if(metastasisRadio.isSelected()){
						metastasisFlag = 1;
						if(null == primaryTumor || "".equals(primaryTumor)){
							showErrorMessage("请选择原发脏器肿瘤！");
							return ;
						}
					}else{
						metastasisFlag = 2;
					}
				}
				String tumorNumberStr = tumorNumberComboBox.getSelectionModel().getSelectedItem();
				if(!NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
					showErrorMessage("请选择或录入正确的肿瘤数量！");
					tumorNumberComboBox.requestFocus();
					return ;
				}
				tumorNumber = Integer.parseInt(tumorNumberStr);
			}
			if(tumorFlag > 0){
				int tumorPos = 0;
				int tumorCharacter = 0;
				Date tumorOccurDate = null;
				
				if(tumorPosRadio1.isSelected() || tumorPosRadio2.isSelected()){
					if(tumorPosRadio2.isSelected()){
						tumorPos = 2;
						if(tumorCharacterRadio1.isSelected()){
							tumorCharacter = 1;
						}else if(tumorCharacterRadio2.isSelected()){
							tumorCharacter = 2;
						}else if(tumorCharacterRadio3.isSelected()){
							tumorCharacter = 3;
						}
						if(tumorCharacter == 0){
							showErrorMessage("请选择肿瘤特性！");
							return;
						}
					}else{
						tumorPos = 1;
						String tumorOccurDateStr = "";
						tumorOccurDateStr = occurDatePicker.getTextField().getText().trim();
						if(null != tumorOccurDateStr && !"".equals(tumorOccurDateStr)){
							tumorOccurDate = DateUtil.stringToDate(tumorOccurDateStr, "yyyy-MM-dd");
						}else{
							showErrorMessage("请选择肿瘤发生日期！");
							return;
						}
					}
					
					tblHistopathCheck.setTumorPos(tumorPos);
					tblHistopathCheck.setTumorCharacter(tumorCharacter);
					tblHistopathCheck.setTumorOccurDate(tumorOccurDate);
					
				}else{
					showErrorMessage("请选择肿瘤位置！");
					return;
				}
			}
			
			tblHistopathCheck.setTumorFlag(tumorFlag);
			tblHistopathCheck.setMetastasisFlag(metastasisFlag);
			tblHistopathCheck.setTumorNum(tumorNumber);
			if(null != histoPos){
				tblHistopathCheck.setHistoPos(histoPos);
				String histoPosCode = descCNDictPathCommonMap_histoPoListView.get(histoPos).getItemCode();
				tblHistopathCheck.setHistoPosCode(histoPosCode);
			}
			tblHistopathCheck.setLesionFinding(lesionFinding);
			String lesionFindingCode = descCNDictPathCommonMap_lesionFindingListView.get(lesionFinding).getItemCode();
			tblHistopathCheck.setLesionFindingCode(lesionFindingCode );
			
			tblHistopathCheck.setPrimaryTumor(primaryTumor);
			tblHistopathCheck.setPrimaryViscera(primaryVisceraName);
			tblHistopathCheck.setPrimaryVisceraCode(primaryVisceraCode);
			
		}
		
		
		tblHistopathCheck.setVisceraType(visceraType);
		tblHistopathCheck.setVisceraCode(visceraCode);
		tblHistopathCheck.setVisceraName(visceraName);
		tblHistopathCheck.setSubVisceraCode(subVisceraCode);
		tblHistopathCheck.setSubVisceraName(subVisceraName);
		
		//3.5备注
		String remark = remarkText.getText().trim();
		if(remark.getBytes().length > 199){
			showErrorMessage("备注长度不能大于200！");
			remarkText.requestFocus();
			return ;
		}
		tblHistopathCheck.setRemark(remark);
		//4.保存记录
		
		//id,operateTime 在服务器端设置，保存
		BaseService.getInstance().getTblHistopathCheckService().saveOne(tblHistopathCheck );
		
		updateHistopathResultTable();
		
		primaryVisceraCode = null;	//源发脏器编号
		primaryVisceraName = null;	//源发脏器名称
		primaryTumor = null;	
		
		histoPoListView.getSelectionModel().clearSelection();
		lesionFindingListView.getSelectionModel().clearSelection();
		
		remarkText.clear();
		levelComboBox.getSelectionModel().select(0);
		
	}
	
	/**快速查找TextField  键盘按下起来事件
	 * @param event
	 */
	@FXML
	private void OnSearchTextFieldKeyReleased(KeyEvent event){
//		"Enter";
		System.out.println(event.getCode().getName());
		if("Down".equals(event.getCode().getName())){
			searchListView.getSelectionModel().select(0);
			searchListView.requestFocus();
		}else{
			showSearchListView();
		}
	}
	/**
	 * 显示 快速查找ListView 
	 */
	private void showSearchListView() {
		data_searchListView.clear();
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		String py = searchTextField.getText();
		if("组织学用语".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_histoPoListView,py);
		}else{
			updateData_searchListView(pyDescCNMap_lesionFindingListView,py);
		}
		int height = data_searchListView.size() * 25;	
		if(height > 175){
			height = 175;
		}
		if(height == 0){
			searchListView.setVisible(false);
		}else{
			height = height+1;
			searchListView.setPrefHeight(height);
			searchListView.setVisible(true);
		}
	}
	/**更新searchListView的值
	 * @param map
	 * @param py
	 */
	private void updateData_searchListView(Map<String,String> map,String py){
		if(null != py && !"".equals(py)){
			List<String> pyList = new ArrayList<String>(map.keySet());
			if(null != pyList){
				py = py.toLowerCase();
				for(String str:pyList){
					if(str.toLowerCase().contains(py)){
						data_searchListView.add(map.get(str));
					}
				}
			}
		}
	}
	


	private static HistopathCheckEdit instance;
	public static HistopathCheckEdit getInstance(){
		if(null == instance){
			instance = new HistopathCheckEdit();
		}
		return instance;
	}
	
	public HistopathCheckEdit() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		//3.初始化组织学所见Tab
		initHistopathTab();
		
		initlevelComboBox();
	}
	private void initlevelComboBox() {
		levelComboBox.setItems(data_levelComboBox);
	}
	/**
	 * 初始化组织学所见Tab
	 */
	private void initHistopathTab() {
		//1.快速查找ComboBox
		initSerachComboBox();
		//2.0快速查找TextField
		initSearchTextField();
		//2.1初始化快速查找ListView 
		initSearchListView();
		//3.字典排序方式ComboBox
		initDictSortMethodComboBox();
		//4.异常类型 RadioButton
		initRadioButton();
		//5.组织学用语ListView
		initHistoPoListView();
		//6.病变描述ListView
		initLesionFindingListView();
		//7.肿瘤数量ComboBox及程度ComboBox
		initLevelComboBox();
		//8.组织学所见Table_1
		initHistopathResultTable();
		//9.肿瘤位置 RaidoButton
		initTumorPosRadio();
		//10.初始化发生日期
		initOccurDate();
		
	}
	
	/**
	 * 初始化发生日期
	 */
	private void initOccurDate() {
		occurDatePicker = new DatePicker(Locale.CHINA);
		occurDatePicker.getTextField().setEditable(false);
		occurDatePicker.getTextField().setFocusTraversable(true);
		occurDatePicker.getTextField().setMaxWidth(100);
		occurDatePicker.getTextField().setMinWidth(100);
		occurDatePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		occurDatePicker.getCalendarView().todayButtonTextProperty().set("今天");
		occurDatePicker.getCalendarView().setShowWeeks(false);
		occurDatePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());
		occurDatePicker.setMaxWidth(100);
		tumorOccurDateHbox.getChildren().add(occurDatePicker);
		occurDatePicker.getTextField().setFocusTraversable(false);
		
	}
	/**
	 * 初始化肿瘤位置 RaidoButton
	 */
	private void initTumorPosRadio() {
		tumorPosRadio1.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue,
					Toggle newValue) {
				tumorCharacterPane.setVisible(false);
				tumorOccurPane.setVisible(false);
				if(tumorPosRadio1.isSelected()){
					tumorOccurPane.setVisible(true);
					occurDatePicker.getTextField().clear();
					if(null != studyNo && !"".equals(studyNo) && null != animalCode && !"".equals(animalCode)){
						TblAnatomyAnimal anatomyAnimal = BaseService.getInstance().getTblAnatomyAnimalService()
								.getByStudyNoAnimalCode(studyNo, animalCode);
						if(null != anatomyAnimal && null != anatomyAnimal.getAnatomyBeginTime()){
							occurDatePicker.getTextField().setText(DateUtil.dateToString(anatomyAnimal.getAnatomyBeginTime(), "yyyy-MM-dd"));
						}
					}
				}else{
					if(tumorPosRadio2.isSelected()){
						tumorCharacterPane.setVisible(true);
						tumorCharacterRadio1.setSelected(false);
						tumorCharacterRadio2.setSelected(false);
						tumorCharacterRadio3.setSelected(false);
					}
				}
				
			}
		});
		
	}
	public void setOccurDate(String date){
		occurDatePicker.getTextField().setText(date);
	}
	/**
	 * 肿瘤数量ComboBox及程度ComboBox
	 */
	private void initLevelComboBox() {
		levelComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					updateHistopathCheckLabelText();
				}
			}
		});
		tumorNumberComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					//TODO 
					updateHistopathCheckLabelText();
				}
			}
		});
		
	}
	/**
	 * 组织学所见Table_1
	 */
	private void initHistopathResultTable() {
		histopathResultTable.setItems(data_histopathResultTable);
//		histopathResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tumorFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorFlag"));
		metastasisFlagCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("metastasisFlag"));
		stateCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("state"));
		checkResultCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("checkResult"));
		primaryVisceraTumorCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("primaryVisceraTumor"));
		
		levelCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("level"));
		tumorNumCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorNum"));
		tumorPosCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorPos"));
		tumorOccurDateCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorOccurDate"));
		tumorCharacterCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("tumorCharacter"));
		remarkCol_1.setCellValueFactory(new PropertyValueFactory<HistopathResult,String>("remark"));
		
		metastasisFlagCol.setCellFactory(new Callback<TableColumn<HistopathResult,String>,TableCell<HistopathResult,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<HistopathResult, String> call(
	    			 TableColumn<HistopathResult, String> param) {
	    		 TableCell<HistopathResult, String> cell =
	    				 new TableCell<HistopathResult, String>() {
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
	    		 cell.setStyle("-fx-alignment: CENTER;");
	    		 return cell;
	    	 }
	     });
		operateCol.setCellValueFactory(new PropertyValueFactory<HistopathResult,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<HistopathResult, Boolean>, TableCell<HistopathResult, Boolean>>() {

            public TableCell<HistopathResult, Boolean> call(TableColumn<HistopathResult, Boolean> p) {

            	HyperlinkCell<HistopathResult, Boolean> cell = new HyperlinkCell<HistopathResult, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * 病变描述ListView
	 */
	private void initLesionFindingListView() {
		
		lesionFindingListView.setItems(data_lesionFindingListView);
		lesionFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				lesionFindingListView.setUserData(true);
				if(null != newValue){
					lesionFindingListView.setId(newValue);
				}else{
					lesionFindingListView.setId("");
				}
				// 更新 histopathCheckLabel 值
				updateHistopathCheckLabelText();
			}
		});
		
		lesionFindingListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("病变描述");
			}
		});
	}
	/**
	 * 组织学用语ListView
	 */
	private void initHistoPoListView() {
		
		histoPoListView.setItems(data_histoPoListView);
		histoPoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				histoPoListView.setUserData(true);
				if(null != newValue){
					histoPoListView.setId(newValue);
				}else{
					histoPoListView.setId("");
				}
				// 更新 histopathCheckLabel 值
				updateHistopathCheckLabelText();
			}
		});
		
		histoPoListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
					}
				}
				list.setUserData(false);
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("组织学用语");
			}
		});
	}

	/**
	 * 更新 histopathCheckLabel 值
	 */
	private void updateHistopathCheckLabelText(){
		String finding = "组织学所见：";
		
		finding = finding + (histoPoListView.getSelectionModel().getSelectedItem() == null ?
				"":histoPoListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (lesionFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		if(null != primaryVisceraName || null != primaryTumor){
			finding = finding+"  原发于";
			finding = finding + (primaryVisceraName == null ?
					"":primaryVisceraName+" ");
			finding = finding + (primaryTumor == null ?
					"":primaryTumor+"");
		}
		if(!"组织学所见：".equals(finding.trim())){
			if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
				String tumorNumberStr = tumorNumberComboBox.getSelectionModel().getSelectedItem();
				if(NumberValidationUtils.isPositiveInteger(tumorNumberStr)){
					finding = finding +"（ 数量 "+tumorNumberStr+"个 ）";
				}
			}else if(noTumorRadio.isSelected()){
				String selectedItem = levelComboBox.getSelectionModel().getSelectedItem();
				if(null != selectedItem && !"".equals(selectedItem)){
					finding = finding +"（ 程度 "+selectedItem+" ）";
				}
			}
			
		}
		
		histopathCheckLabel.setText(finding);
	}
	
	/**
	 * 初始化快速查找TextField
	 */
	private void initSearchTextField() {
		searchTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null == newValue || !newValue){
					if(!searchListView.isFocused()){
						searchListView.setVisible(false);
						searchTextField.clear();
					}
				}
				
			}});
	}
	
	/**
	 * 初始化快速查找ListView 
	 */
	private void initSearchListView() {
		searchListView.setItems(data_searchListView);
		searchListView.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				String newValue = searchListView.getSelectionModel().getSelectedItem();
				if(null != newValue){
					//快速查找输入框清空
					searchTextField.clear();
					searchComboBox.requestFocus();
					selectandScrollToValue(newValue);
				}
			}
		});
		
		searchListView.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				if("Enter".equals(event.getCode().getName())){
					String newValue = searchListView.getSelectionModel().getSelectedItem();
					if(null != newValue){
						//快速查找输入框清空
						searchTextField.clear();
						searchComboBox.requestFocus();
						selectandScrollToValue(newValue);
					}
				}
			}

		});
		
		searchListView.focusedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(null == newValue || !newValue){
					searchListView.setVisible(false);
				}
			}
		});
	}
	
	/**快速查找，选择指定项，并移到指定行
	 * @param newValue
	 */
	private void selectandScrollToValue(String newValue) {
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		if("组织学用语".equals(selectItem)){
			histoPoListView.getSelectionModel().select(newValue);
			int index=histoPoListView.getSelectionModel().getSelectedIndex();
			histoPoListView.getFocusModel().focus(index);
			histoPoListView.scrollTo(getIndex(data_histoPoListView,newValue));
		}else{
			lesionFindingListView.getSelectionModel().select(newValue);
			int index=lesionFindingListView.getSelectionModel().getSelectedIndex();
			lesionFindingListView.getFocusModel().focus(index);
			lesionFindingListView.scrollTo(getIndex(data_lesionFindingListView,newValue));
		}
	}
	/**获取索引
	 * @param data_ListView
	 * @param newValue
	 * @return
	 */
	private int getIndex(ObservableList<String> data_ListView, String newValue) {
		int i = 0;
		for(String str : data_ListView){
			if(str.equals(newValue)){
				return i;
			}
			i++;
		}
		return 0;
	}
	/**
	 * 初始化快速查找ComboBox
	 */
	private void initSerachComboBox(){
		searchComboBox.getSelectionModel().select(0);
		histoPoListView.setStyle("-fx-border-color:blue;");
		searchComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null != newValue){
					searchTextField.setPromptText("快速检索文本(拼音首字母)");
					setStyle(newValue);
				}
				if(null != oldValue){
					clearStyle(oldValue);
				}
			}});
	}

	/**设置ListView 的边框颜色
	 * @param listName
	 */
	private void setStyle(String listName){
		if("组织学用语".equals(listName)){
			histoPoListView.setStyle("-fx-border-color:blue;");
		}else{
			lesionFindingListView.setStyle("-fx-border-color:blue;");
		}
	}
	/**清除ListView 的边框颜色
	 * @param listName
	 */
	private void clearStyle(String listName){
		if("组织学用语".equals(listName)){
			histoPoListView.setStyle("-fx-border-color: #919191;");
		}else{
			lesionFindingListView.setStyle("-fx-border-color: #919191;");
		}
	}
	/**
	 * 异常类型 RadioButton
	 */
	private void initRadioButton() {
		//异常
		abnormalRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(abnormalRadio.isSelected()){
					//非肿瘤选中
					if(null ==noTumorRadio.getToggleGroup().getSelectedToggle()){
						noTumorRadio.setSelected(true);
					}
					
				}else{
					noTumorRadio.setSelected(false);
					lxTumorRadio.setSelected(false);
					exTumorRadio.setSelected(false);
					noMetastasisRadio.setSelected(false);
					qinxiRadio.setSelected(false);
					metastasisRadio.setSelected(false);
					
					updateTumorNumberComboBoxState();
				}
				
				if(null !=newValue && newValue ){
					histoPoListView.setDisable(false);
					lesionFindingListView.setDisable(false);
				}else{
					//未见异常，组织学用语和病变描述 不可用
					histoPoListView.setDisable(true);
					lesionFindingListView.setDisable(true);
				}
			}});
		
		//恶性肿瘤
		exTumorRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(exTumorRadio.isSelected()){
					if(null == noMetastasisRadio.getToggleGroup().getSelectedToggle()){
						noMetastasisRadio.setSelected(true);
					}
				}else{
					noMetastasisRadio.setSelected(false);
					qinxiRadio.setSelected(false);
					metastasisRadio.setSelected(false);
				}
				
			}
		});
		//非肿瘤、良性肿瘤、恶性肿瘤
		exTumorRadio.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle oldValue, Toggle newValue) {
				if(null != newValue){
					abnormalRadio.setSelected(true);
				}
				
				if(null == oldValue || null == newValue || ((RadioButton)oldValue).getText().equals("非肿瘤")
						|| ((RadioButton)newValue).getText().equals("非肿瘤")){
					//更新 病变描述语ListView  
					updateLesionFindingListView();
					//更新肿瘤数量ComboBox状态
					updateTumorNumberComboBoxState();
					
					updateTumorPosPaneState();
				}
			}

		});
		
		//转移
		metastasisRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(metastasisRadio.isSelected()){
					primaryViscerHyperlink.setVisible(true);
				}else{
					primaryViscerHyperlink.setVisible(false);
					
					primaryVisceraCode = null;	//源发脏器编号
					primaryVisceraName = null;	//源发脏器名称
					primaryTumor = null;	
					
					updateHistopathCheckLabelText();
				}
				
			}
		});
		
		metastasisRadio.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newValue) {
				if(null != newValue){
					exTumorRadio.setSelected(true);
				}
				
			}
		});
		
		noTumorRadio.setSelected(false);
		lxTumorRadio.setSelected(false);
		exTumorRadio.setSelected(false);
		noMetastasisRadio.setSelected(false);
		qinxiRadio.setSelected(false);
		metastasisRadio.setSelected(false);
		
		primaryViscerHyperlink.setVisible(false);
	}

	/**
	 * 更新 tumorPosPane hidden与否
	 */
	private void updateTumorPosPaneState(){
		if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
			tumorPosPane.setVisible(true);
		}else{
			tumorPosPane.setVisible(false);
			tumorPosRadio1.setSelected(false);
			tumorPosRadio2.setSelected(false);
		}
	}
	
	/**
	 * 更新肿瘤数量ComboBox状态
	 */
	private void updateTumorNumberComboBoxState() {
		hbox.getChildren().remove(tumorNumberLabel);
		hbox.getChildren().remove(tumorNumberComboBox);
		hbox.getChildren().remove(levelLabel);
		hbox.getChildren().remove(levelComboBox);
		levelComboBox.getSelectionModel().select("");
		tumorNumberComboBox.getSelectionModel().select(0);
		if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
//			tumorNumberComboBox.setDisable(false);
//			tumorNumberLabel.setVisible(true);
//			tumorNumberComboBox.setVisible(true);
			String selectedItem = tumorNumberComboBox.getSelectionModel().getSelectedItem();
			if(null == selectedItem || "".equals(selectedItem) || "0".equals(selectedItem)){
				tumorNumberComboBox.getSelectionModel().select(0);
			}
			
//			levelLabel.setVisible(false);
//			levelComboBox.setVisible(false);
//			levelComboBox.getSelectionModel().select("");
			hbox.getChildren().add(0, tumorNumberLabel);
			hbox.getChildren().add(1, tumorNumberComboBox);
		}else if(noTumorRadio.isSelected()){
			//非肿瘤
//			tumorNumberLabel.setVisible(false);
//			tumorNumberComboBox.setVisible(false);
//			tumorNumberComboBox.getSelectionModel().select("0");
			
//			levelLabel.setVisible(true);
//			levelComboBox.setVisible(true);
			levelComboBox.getSelectionModel().select("");
			hbox.getChildren().add(0, levelLabel);
			hbox.getChildren().add(1, levelComboBox);
		}else{
			//未见异常
//			tumorNumberLabel.setVisible(false);
//			tumorNumberComboBox.setVisible(false);
//			tumorNumberComboBox.getSelectionModel().select("0");
//			
//			levelLabel.setVisible(false);
//			levelComboBox.setVisible(false);
//			levelComboBox.getSelectionModel().select("");
		}
		
	}
	

	/**
	 * 初始化 字典排序ComboBox
	 */
	private void initDictSortMethodComboBox() {
		//  字典排序
		dictSortMethodComboBox.getSelectionModel().select(0);
		dictSortMethod = 1;
		dictSortMethodComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					dictSortMethod = newValue.intValue()+1;
					updateHistoPoListView();
					updateLesionFindingListView();
				}
				
			}
		});
	}
	
	

	
	/**
	 * 更新组织学所见表（tab1）
	 */
	void updateHistopathResultTable(){
		data_histopathResultTable.clear();
		
		//3.查询数据准备数据
		List<TblHistopathCheck> tblHistopathCheckList = BaseService.getInstance().getTblHistopathCheckService()
				.getListByStudyNoAnimalCodeSliceVisceraId2(studyNo,animalCode,sliceVisceraId);
		if(null != tblHistopathCheckList){
			for(TblHistopathCheck obj:tblHistopathCheckList){
				boolean operate = true;
				if(obj.getHistoryFlag() != 0){
					operate = false;
				}
				String tumorOccurDate = DateUtil.dateToString(obj.getTumorOccurDate(), "yyyy-MM-dd");
				data_histopathResultTable.add(new HistopathResult(obj.getId(),obj.getIsNoAbnormal(),obj.getTumorFlag()
						,obj.getMetastasisFlag(),obj.getHistoPos(),obj.getLesionFinding()
						,obj.getPrimaryViscera(),obj.getPrimaryTumor(),operate,obj.getHistoryFlag(),
						obj.getLevel(),obj.getTumorNum(),obj.getTumorPos(),tumorOccurDate ,obj.getTumorCharacter(),obj.getRemark()));
			}
		}
		
	}
	


	/**更新 组织学用语ListView
	 * @param visceraCode
	 */
	private void updateHistoPoListView() {
		histoPoListView.getSelectionModel().clearSelection();
		data_histoPoListView.clear();
		pyDescCNMap_histoPoListView.clear();
		descCNDictPathCommonMap_histoPoListView.clear();
		if(null != visceraCode && !"".equals(visceraCode)){
			List<DictPathCommon> dictPathCommon13List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(13, visceraCode,dictSortMethod);
			if(null != dictPathCommon13List && dictPathCommon13List.size() > 0){
				for(DictPathCommon obj:dictPathCommon13List){
					data_histoPoListView.add(obj.getDescCn());
					descCNDictPathCommonMap_histoPoListView.put(obj.getDescCn(), obj);
					pyDescCNMap_histoPoListView.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}
	/**更新 病变描述语ListView  
	 * @param visceraCode
	 */
	private void updateLesionFindingListView() {
//		lesionFindingListView;
		lesionFindingListView.getSelectionModel().clearSelection();
		data_lesionFindingListView.clear();
		pyDescCNMap_lesionFindingListView.clear();
		descCNDictPathCommonMap_lesionFindingListView.clear();
		if(null != visceraCode && !"".equals(visceraCode)){
			List<DictPathCommon> dictPathCommon146List = null;
			if(noTumorRadio.isSelected()){
				dictPathCommon146List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(16, visceraCode,dictSortMethod);
			}else if(lxTumorRadio.isSelected() || exTumorRadio.isSelected()){
				dictPathCommon146List = BaseService.getInstance()
						.getDictPathCommonService().getListByDictTypeAndVisceraCode(14, visceraCode,dictSortMethod);
			}
			if(null != dictPathCommon146List && dictPathCommon146List.size() > 0){
				for(DictPathCommon obj:dictPathCommon146List){
					data_lesionFindingListView.add(obj.getDescCn());
					descCNDictPathCommonMap_lesionFindingListView.put(obj.getDescCn(), obj);
					pyDescCNMap_lesionFindingListView.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}

	/**
	 * 加载数据
	 * @param subVisceraName 
	 * @param subVisceraCode 
	 * @param visceraName 
	 * @param visceraType 
	 * @param taskId 
	 */
	public void loadData(String studyNo,String animalCode,String sliceVisceraId,String visceraCode,String visceraOrTissueName, Integer visceraType, String visceraName, String subVisceraCode, String subVisceraName) {
		this.studyNo = studyNo;
		this.animalCode = animalCode;
		this.sliceVisceraId = sliceVisceraId;
		this.visceraCode = visceraCode;
		this.visceraOrTissueName = visceraOrTissueName;
		
		this.visceraType = visceraType;
		this.visceraName = visceraName;
		this.subVisceraCode = subVisceraCode;
		this.subVisceraName = subVisceraName;
		
		
		primaryVisceraCode = null;	//源发脏器编号
		primaryVisceraName = null;	//源发脏器名称
		primaryTumor = null;	
		
		//1.更新动物编号  脏器或组织Label
		updateAnimalCodeVisceraNameLabel();
		//2.更新 组织学用语ListView
		updateHistoPoListView();
		//3.
		noAbnormalRadio.setSelected(true);
		abnormalRadio.setSelected(true);
		//4.更新组织学所见表（tab2）
		updateHistopathResultTable();
		
		updatelevelComboBox();
	}
	
	/**
	 * 更新病变程度levelComboBox
	 */
	private void updatelevelComboBox(){
		data_levelComboBox.clear();
		List<DictLevel> list = BaseService.getInstance().getDictLevelService().findAll();
		data_levelComboBox.add("");
		if(null != list && list.size() > 0){
			for(DictLevel obj:list){
				data_levelComboBox.add(obj.getLevel());
			}
		}
	}
	
	/**
	 * 更新动物编号  脏器或组织Label
	 */
	private void updateAnimalCodeVisceraNameLabel() {
		String text = "";
		text = text+"动物编号："+animalCode+"    ";
		text = text+"所选脏器或组织："+visceraOrTissueName;
		animalCodeVisceraNameLabel.setText(text);
	}

	/**设置源发脏器肿瘤值
	 * @param visceraCode
	 * @param visceraName
	 * @param primaryTumor
	 */
	public void setPrimaryViscerTumor(String visceraCode,String visceraName,String primaryTumor){
		
		this.primaryVisceraCode = visceraCode;
		this.primaryVisceraName = visceraName;
		this.primaryTumor = primaryTumor;
		updateHistopathCheckLabelText();
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("HistopathEdit.fxml"));
		Scene scene = new Scene(root, 1014, 590);
		stage.setScene(scene);
		stage.setTitle("数据修改");
//		stage.setResizable(false);
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				HistopathReview.getInstance().updateHistopathCheckTable();
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
		stage.show();
	}
	public void showMessage(String msg) {
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
		Messager.showWarnMessage(msg);
	}
	
	
	
	
	/**组织学检查_1
	 * @author Administrator
	 *
	 */
	public class HistopathResult{
		private StringProperty id;			//
		private StringProperty tumorFlag;		//性质（非肿瘤、良性肿瘤、恶性肿瘤）
		private StringProperty metastasisFlag;	//是否转移
		private StringProperty checkResult;		//检查结果
		private StringProperty primaryVisceraTumor;	//原发脏器肿瘤
		private BooleanProperty  operate;	//操作
		private StringProperty  state;	//状态
		
		private StringProperty level;	//病变程度
		private StringProperty tumorNum;	//肿瘤数量
		private StringProperty tumorPos;	//肿瘤位置
		private StringProperty tumorOccurDate;	//肿瘤发生日期
		private StringProperty tumorCharacter;	//肿瘤特性
		private StringProperty remark;	//备注
		
		public HistopathResult(String id,Integer isNoAbnormal,Integer tumorFlag,Integer metastasisFlag,
				String histoPos,String lesionFinding,String primaryViscera,String primaryTumor,boolean operate,Integer historyFlag,
				String level,Integer tumorNum,Integer tumorPos,String tumorOccurDate,Integer tumorCharacter,String remark){
			setId(id);
			if(isNoAbnormal == 0){
				if(tumorFlag == 0 || tumorFlag == 1 ){
					setTumorFlag(tumorFlag == 0 ? "非肿瘤":"良性肿瘤");
					setMetastasisFlag("");
					setPrimaryVisceraTumor("");
				}else{
					setTumorFlag("恶性肿瘤");
//					setMetastasisFlag(metastasisFlag == 1 ? "是":"否");
//					if(metastasisFlag == 1){
//						setMetastasisFlag("是");
//						setPrimaryVisceraTumor((primaryViscera != null ? primaryViscera+" " :"")+(primaryTumor != null ? primaryTumor:""));
//					}else{
//						setMetastasisFlag("否");
//						setPrimaryVisceraTumor("");
//					}
					if(metastasisFlag == 1){
						setMetastasisFlag("转移");
						setPrimaryVisceraTumor((primaryViscera != null ? primaryViscera+" " :"")+(primaryTumor != null ? primaryTumor:""));
					}else if(metastasisFlag == 2){
						setMetastasisFlag("侵袭");
						setPrimaryVisceraTumor("");
					}else{
						setMetastasisFlag("原发");
						setPrimaryVisceraTumor("");
					}
				}
				String checkResult = "";
				if(null != histoPos && !"".equals(histoPos)){
					checkResult = histoPos+" "+lesionFinding;
				}else{
					checkResult = lesionFinding;
				}
				setCheckResult(checkResult);
//				setCheckResult(histoPos+" "+lesionFinding);
			}else if(isNoAbnormal == 1){
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("未见异常");
				setPrimaryVisceraTumor("");
			}else if(isNoAbnormal == -1){
				setTumorFlag("");
				setMetastasisFlag("");
				setCheckResult("缺失");
				setPrimaryVisceraTumor("");
			}
			
			setOperate(operate);
			if(historyFlag == 0){
				setState("");
			}else{
				setState("已删除");
			}
			
			setLevel(level != null ? level :"");
			setTumorNum(tumorNum+"");
			if(null != tumorPos && tumorPos == 1){
				setTumorPos("浅表");
				setTumorOccurDate(tumorOccurDate);
				setTumorCharacter("");
			}else if(null != tumorPos && tumorPos == 2){
				setTumorPos("深部");
				setTumorOccurDate("");
//				1：偶发；2：致死；3：不明确
				switch (tumorCharacter) {
				case 1:	setTumorCharacter("偶发");break;
				case 2:	setTumorCharacter("致死");break;
				case 3:	setTumorCharacter("不明确");break;

				default:setTumorCharacter("");
					break;
				}
			}else{
				setTumorPos("");
				setTumorOccurDate("");
				setTumorCharacter("");
			}
			setRemark(remark != null ? remark :"");
			
		}
		
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		public String getId() {
			return id.get();
		}


		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}


		public String getTumorFlag() {
			return tumorFlag.get();
		}


		public void setTumorFlag(String tumorFlag) {
			this.tumorFlag = new SimpleStringProperty(tumorFlag);
		}


		public String getMetastasisFlag() {
			return metastasisFlag.get();
		}


		public void setMetastasisFlag(String metastasisFlag) {
			this.metastasisFlag = new SimpleStringProperty(metastasisFlag);
		}


		public String getPrimaryVisceraTumor() {
			return primaryVisceraTumor.get();
		}


		public void setPrimaryVisceraTumor(String primaryVisceraTumor) {
			this.primaryVisceraTumor = new SimpleStringProperty(primaryVisceraTumor);
		}


		public String getCheckResult() {
			return checkResult.get();
		}
		public void setCheckResult(String checkResult) {
			this.checkResult = new SimpleStringProperty(checkResult);
		}

		public String getState() {
			return state.get();
		}

		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}
		
		public String getLevel() {
			return level.get();
		}

		public void setLevel(String level) {
			this.level = new SimpleStringProperty(level);
		}

		public String getTumorNum() {
			return tumorNum.get();
		}

		public void setTumorNum(String tumorNum) {
			this.tumorNum = new SimpleStringProperty(tumorNum);
		}

		public String getTumorPos() {
			return tumorPos.get();
		}

		public void setTumorPos(String tumorPos) {
			this.tumorPos = new SimpleStringProperty(tumorPos);
		}

		public String getTumorOccurDate() {
			return tumorOccurDate.get();
		}

		public void setTumorOccurDate(String tumorOccurDate) {
			this.tumorOccurDate = new SimpleStringProperty(tumorOccurDate);
		}

		public String getTumorCharacter() {
			return tumorCharacter.get();
		}

		public void setTumorCharacter(String tumorCharacter) {
			this.tumorCharacter = new SimpleStringProperty(tumorCharacter);
		}

		public String getRemark() {
			return remark.get();
		}

		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
	}
	/**删除用
	 * @author Administrator
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
	    private ObservableValue<T> ov;  
	    private Map<String,String> vmap;  
	      
	    public Map<String, String> getVmap() {  
	        return vmap;  
	    }  
	  
	    public void setVmap(Map<String, String> vmap) {  
	        this.vmap = vmap;  
	    }  
	  
	    public HyperlinkCell() {  
	        this.hyperlink = new Hyperlink();  
	        this.hyperlink.setUnderline(true);  
	        setAlignment(Pos.CENTER);  
	        setGraphic(hyperlink);  
	    }  
	  
	    @Override  
	    protected void updateItem(T item, boolean empty) {  
	        super.updateItem(item, empty);  
	        if (empty) {  
	            setText(null);  
	            setGraphic(null);  
	        } else {  
	            setGraphic(hyperlink);  
	            ov = getTableColumn().getCellObservableValue(getIndex());  
	            if (ov instanceof SimpleBooleanProperty) {  
	            	SimpleBooleanProperty booleanValue = (SimpleBooleanProperty) ov;
	            	if(!booleanValue.get()){
	            		hyperlink.setDisable(true);
	            	}else
	            	{
	            		hyperlink.setDisable(false);
	            	}
	            } 
	            hyperlink.setText("删除");
	            hyperlink.setPrefWidth(50);
	            hyperlink.setPrefHeight(20);
	            hyperlink.setUserData(this.getTableRow().getItem());
	            HistopathResult histopathResult = (HistopathResult) this.getTableRow().getItem();
	            if(histopathResult.getCheckResult().equals("缺失")){
	            	hyperlink.setDisable(true);
	            }
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						HistopathResult histopathResult = (HistopathResult) hyperlink.getUserData();
						if(Sign.openSignWithReasonFrame("删除原因","称重数据删除")){
							String reason = Sign.getReason();
							String operator = SaveUserInfo.getRealName();
							Json json = BaseService.getInstance().getTblHistopathCheckService().deleteOne2(histopathResult.getId(),reason,operator);
							if(json.isSuccess()){
								//更新
								updateHistopathResultTable();
							}else{
								showErrorMessage(json.getMsg());
							}
							
						}
					}
				});
	        }  
	    }  
	}
	
}
