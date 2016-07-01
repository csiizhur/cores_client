package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.DictPathCommon;
import com.lanen.model.path.TblAnatomyCheck;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**补录解剖所见
 * @author Administrator
 *
 */
public class AdditionalPage_anatomy extends Application implements Initializable {

	
	/**
	 * 已签字，不可编辑，
	 */
	private boolean isSigned = true;	//已签字，不可编辑，
	private String studyNo = "";
	private String taskId = "";
	
	
	@FXML
	private Label handlersLabel; // 当前操作者
	@FXML
	private Label msgLabel; // 称重页面提示信息label
	
	@FXML
	private TableView<Animal> animalTable; // 动物table
	private ObservableList<Animal> data_animalTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<Animal, String> animalCodeCol; // 动物编号column

	@FXML
	private TableColumn<Animal, String> stateCol; // 状态column

	@FXML
	private TableColumn<Animal, String> genderCol; // 解剖者column


	@FXML
	private Button autolyzeBtn; // 自溶button（动物自溶）

	@FXML
	private TabPane tabPane; // 标签面板TabPane

	@FXML
	private Tab anatomyTab; // 动物解剖Tab
	@FXML
	private AnchorPane anatomyPane;
	

	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode=new TreeItem<String>();//visceraTree 根节点
	/**
	 * 存放脏器名称及对应的map（脏器名称，编号，子脏器名称，子编号，脏器类型）
	 */
	private Map<String,Map<String,Object>> visceraName2MapMap = new HashMap<String,Map<String,Object>>();
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	Map<String,TreeItem<String>> visceraCodeTreeItemMap = new HashMap<String,TreeItem<String>>();
	@FXML		
	private Button otherVisceraBtn;			//其他脏器
	@FXML
	private ListView<String> anatomyPosListView; // 解剖学所见部位ListView
	private ObservableList<String> data_anatomyPosListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_anatomyPos = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所所见部位：毒性病理字典
	private Map<String,DictPathCommon> anatomyPosDictPathCommonMap = new HashMap<String,DictPathCommon>();
	@FXML
	private ListView<String> anatomyFindingListView; // 解剖所见ListView
	private ObservableList<String> data_anatomyFindingListView_tongyong = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_tongyong = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所通用所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tongyongDictPathCommonMap = new HashMap<String,DictPathCommon>();
	private ObservableList<String> data_anatomyFindingListView_tesu = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_teshu = new HashMap<String,String>();//处方拼音首字母-中文名称
	//存放解剖所特殊所见：毒性病理字典
	private Map<String,DictPathCommon> anatomyFinding_tesuDictPathCommonMap = new HashMap<String,DictPathCommon>();

	@FXML
	private ListView<String> bodySufacePosListView; // 体表部位ListView
	private ObservableList<String> data_bodySufacePosListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_bodySuface = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> posListView; // 位置ListView
	private ObservableList<String> data_posListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_pos = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> shapeListView; // 形状ListView
	private ObservableList<String> data_shapeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_shape = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> colorListView; // 颜色ListView
	private ObservableList<String> data_colorListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_color = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> textureListView; // 硬度ListView
	private ObservableList<String> data_textureListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_texture = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> numberListView; // 数量ListView
	private ObservableList<String> data_numberListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_number = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> rangeListView; // 范围ListView
	private ObservableList<String> data_rangeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_range = new HashMap<String,String>();//处方拼音首字母-中文名称

	@FXML
	private ListView<String> lesionDegreeListView; // 病变程度ListView
	private ObservableList<String> data_lesionDegreeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_lesionDegree = new HashMap<String,String>();//处方拼音首字母-中文名称
	
	@FXML
	private ListView<String> sizeListView; // 大小ListView
	private ObservableList<String> data_sizeListView = FXCollections.observableArrayList();
	private Map<String,String> pyDescCNMap_size = new HashMap<String,String>();//处方拼音首字母-中文名称
	@FXML
	private TextField sizeTextField;
	@FXML
	private TableView<AnatomyCheck> anatomyResultTable; // 解剖结果TableView
	/**
	 * anatomyResultTalbe 数据集
	 */
	private ObservableList<AnatomyCheck> data_anatomyResultTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<AnatomyCheck, String> visceraNameCol; // 脏器column

	@FXML
	private TableColumn<AnatomyCheck, String> findingCol; // 解剖所见column
	@FXML
	private TableColumn<AnatomyCheck, Boolean> operateCol; // 解剖所见column

	@FXML
	private Button registerBtn; // 登记button
	
	@FXML
	private Button autolyzeBtn2; // 自溶button（脏器）

	@FXML
	private Button symptomObservationBtn; // 症状观察

//	@FXML
//	private Button confirmBtn; // 完成button

	@FXML
	private ComboBox<String> searchComboBox; // 快速查找ComboBox
	@FXML
	private TextField searchTextField;//快速查找TextField
	@FXML
	private ListView<String> searchListView ; //快速查找ListView
	private ObservableList<String> data_searchListView = FXCollections.observableArrayList();
	
	
	@FXML
	private ComboBox<String> dictSortMethodComboBox; // 字典排序方式
	private static int sortMethod = 1;	//排序方式 sortMethod :1,字典排序  2，字母排序 3,使用频度

	@FXML
	private ToggleGroup tg;

	@FXML
	private RadioButton tongyongRadioButton; // 普通所见RadioButton
	
	@FXML
	private RadioButton tesuRadioButton; // 特殊所见RadioButton
	@FXML
	private Label anatomyFindingLabel;
	
	
	@FXML
	private Label animalCodeLabel_anatomy;	//动物编号
	
	
	/**
	 * 自溶（动物）
	 * 
	 * @param event
	 */
	@FXML
	void onAutolyzeBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		if(null != data_anatomyResultTable && data_anatomyResultTable.size() > 0){
			showErrorMessage("该动物已有剖检结果，不可设为自溶！");
			return ;
		}
		if(Messager.showSimpleConfirm("提示","确定将该动物标识为‘自溶’吗？")){
			//签字窗口
			SignMeFrame signMeFrame = new SignMeFrame("");
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("动物自溶--身份验证");
			try {
				signMeFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//签字通过
			if("OK".equals(SignMeFrame.getType())){
				String animalCode = animal.getAnimalCode();
				Json json = BaseService.getInstance().getTblAnatomyAnimalService()
						.setAutolyzeFlag(taskId,animalCode);
				if(json.isSuccess()){
					updateAnimalTable(taskId);
					//选中指定动物
					for(Animal obj:data_animalTable){
						if(animalCode.equals(obj.getAnimalCode())){
							animalTable.getSelectionModel().select(obj);
							break;
						}
					}
				}else{
					showErrorMessage(json.getMsg());
				}
			}
		}
	}

	/**更新使用频度
	 * @param event
	 */
	@FXML
	void onUpdateUserFrequentnessHyperlink(ActionEvent event){
//		tabPane.getSelectionModel().select(1);
//		tabPane.getSelectionModel().selectNext();
		BaseService.getInstance().getTblAnatomyCheckService().updateFreqCount();
		showMessage("使用频度更新成功！");
	}
	
	/**
	 * 其他脏器
	 * 
	 * @param event
	 */
	@FXML
	void onOtherVisceraBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null != animal){
			try {
				
//				OtherViscera.getInstance().start(getStage());
				Stage stage = Main.stageMap.get("OtherViscera");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					OtherViscera.getInstance().start(stage);
					Main.stageMap.put("OtherViscera",stage);
				}else{
					stage.show();
				}
				OtherViscera.getInstance().loadData("AdditionalPage_anatomy",taskId,studyNo,animal.getAnimalCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 登记
	 * 
	 * @param event
	 */
	@FXML
	void onRegisterBtnAction(ActionEvent event) {
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null == treeSelectedItem){
			String bodyPos = bodySufacePosListView.getSelectionModel().getSelectedItem();
			if(null == bodyPos){
				showErrorMessage("请先选择脏器或体表部位！");
				return ;
			}
		}
		String anatomyFinding = anatomyFindingListView.getSelectionModel().getSelectedItem();
		if(null == anatomyFinding){
			showErrorMessage("请先选择解剖学所见！");
			return ;
		}
		
		String msgStr = getAnatomyFinding();
		
		String animalCode = animal.getAnimalCode();
		Integer visceraType = 100;
		String visceraCode = null;
		String visceraName = null;
		String subVisceraCode = null;
		String subVisceraName = null;
		
		Integer anatomyFindingFlag = 0;
		Integer autolyzaFlag = 0;
		Integer specialFlag = 0;//是否肿瘤，
		String anatomyFindingCode = null;
		if(tesuRadioButton.isSelected()){
			anatomyFindingFlag = 1;
			DictPathCommon dictPathCommon2 = anatomyFinding_tesuDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon2.getItemCode();
			specialFlag = dictPathCommon2.getSpecicalFlag();
		}else{
			DictPathCommon dictPathCommon3 = anatomyFinding_tongyongDictPathCommonMap.get(anatomyFinding);
			anatomyFindingCode = dictPathCommon3.getItemCode();
			specialFlag = dictPathCommon3.getSpecicalFlag();
		}
		if(specialFlag == 2){
			autolyzaFlag = 2;
			specialFlag = 0;
		}
		if(autolyzaFlag == 2 && null == treeSelectedItem){
			showErrorMessage("请选择脏器！");
			return ;
		}
		
		Map<String,Object> visceraMap = null;
		if(null != treeSelectedItem){
			visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
			
			if(treeSelectedItem.getParent() == rootNode){
				//父节点
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
			}else{
				visceraType = (Integer) visceraMap.get("visceraType");
				visceraCode = (String) visceraMap.get("visceraCode");
				visceraName = (String) visceraMap.get("visceraName");
				subVisceraCode = (String) visceraMap.get("subVisceraCode");
				subVisceraName = (String) visceraMap.get("subVisceraName");
				
			}
			if(autolyzaFlag == 2 ){
				//判断该脏器是否已经标记自溶
				boolean isHasRecord = BaseService.getInstance().getTblAnatomyCheckService().isHasRecord_1(studyNo,animalCode,visceraCode,subVisceraCode);
				if(isHasRecord){
					showErrorMessage("该脏器已有异常记录或已标记自溶！");
					return ;
				}
			}
			//判断该脏器是否已经标记自溶
			boolean isHasAutolyze = BaseService.getInstance().getTblAnatomyCheckService().isHasAutolyze_1(studyNo,animalCode,visceraCode,subVisceraCode);
			if(isHasAutolyze){
				showErrorMessage("该脏器已标记自溶！");
				return ;
			}
			boolean isMissing = BaseService.getInstance().getTblAnatomyCheckService().isMissing_1(studyNo,animalCode,visceraCode,subVisceraCode);
			if(isMissing){
				showErrorMessage("该脏器已标记缺失！");
				return ;
			}
			if(data_anatomyResultTable.size() > 0){
				for(AnatomyCheck anatomycheck:data_anatomyResultTable){
					if(anatomycheck.getFinding().trim().replaceAll(" ", "").equals(msgStr.trim().replaceAll(" ", ""))
							&&((visceraName.equals(anatomycheck.getVisceraName()) 
									&& null == subVisceraCode)||
									anatomycheck.getVisceraName().equals(subVisceraName))){
						showErrorMessage("不可重复添加！");
						return ;
					}
				}
			}
		}else{
			if(data_anatomyResultTable.size() > 0){
				for(AnatomyCheck anatomycheck:data_anatomyResultTable){
					if(anatomycheck.getFinding().trim().replaceAll(" ", "").equals(msgStr.trim().replaceAll(" ", ""))){
						showErrorMessage("不可重复添加！");
						return ;
					}
				}
			}
		}
		
		String anatomyPosCode = null;
		String anatomyPos = anatomyPosListView.getSelectionModel().getSelectedItem();
		if(null != anatomyPos){
			DictPathCommon dictPathCommon1 = anatomyPosDictPathCommonMap.get(anatomyPos);
			anatomyPosCode = dictPathCommon1.getItemCode();
		}
		
		boolean isQueRu = false;
		//确如，填写缺如原因
		if(autolyzaFlag == 2 ){
			String currentVisceraName = (String) visceraMap.get("visceraName");
			String subvisceraName = (String) visceraMap.get("subVisceraName");
			if(null != subvisceraName && !subvisceraName.equals("")){
				currentVisceraName = currentVisceraName +" - "+subvisceraName;
    		}
			
			try{
//    			MissingVisceraRecord.getInstance().start(getStage());
    			Stage stage = Main.stageMap.get("MissingVisceraRecord");
    			if(null == stage){
    				stage =new Stage();
    				stage.initOwner(Main.mainWidow);
    				stage.initModality(Modality.APPLICATION_MODAL);
    				MissingVisceraRecord.getInstance().start(stage);
    				Main.stageMap.put("MissingVisceraRecord",stage);
    			}
    			
    			MissingVisceraRecord.getInstance().loadDate(taskId,"", studyNo, animalCode, currentVisceraName,visceraMap,1,true);
    			stage.showAndWait();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
			if(MissingVisceraRecord.getInstance().isSuccess()){
				isQueRu = true;
			}else{
				return;
			}
		}
		
		
		String currentUserName = SaveUserInfo.getUserName();
		Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
		
		TblAnatomyCheck tblAnatomyCheck = new TblAnatomyCheck();
//		tblAnatomyCheck.setSessionId(sessionId);
		tblAnatomyCheck.setStudyNo(studyNo);
		tblAnatomyCheck.setAnimalCode(animalCode);
		tblAnatomyCheck.setVisceraType(visceraType);
		tblAnatomyCheck.setVisceraCode(visceraCode);
		tblAnatomyCheck.setVisceraName(visceraName);
		
		tblAnatomyCheck.setSubVisceraCode(subVisceraCode);
		tblAnatomyCheck.setSubVisceraName(subVisceraName);
		
		
		tblAnatomyCheck.setAnatomyPosCode(anatomyPosCode);
		tblAnatomyCheck.setAnatomyPos(anatomyPos);
		tblAnatomyCheck.setAnatomyFindingFlag(anatomyFindingFlag);
		
		tblAnatomyCheck.setSpecialFlag(specialFlag);
		
		tblAnatomyCheck.setAnatomyFindingCode(anatomyFindingCode);
		tblAnatomyCheck.setAnatomyFingding(anatomyFinding);
		String bodySurfacePos = bodySufacePosListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setBodySurfacePos(bodySurfacePos);
		String pos = posListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setPos(pos);
		String shape = shapeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setShape(shape);
		String color = colorListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setColor(color);
		String texture = textureListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setTexture(texture);
		String number = numberListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setNumber(number);
		String range = rangeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setRange(range);
		String lesionDegree = lesionDegreeListView.getSelectionModel().getSelectedItem();
		tblAnatomyCheck.setLesionDegree(lesionDegree);
		String size = sizeTextField.getText();
		tblAnatomyCheck.setSize(size);
		
		tblAnatomyCheck.setOperator(currentUserName);
		tblAnatomyCheck.setOperateTime(currentDate);
		tblAnatomyCheck.setAutolyzaFlag(autolyzaFlag);
		
		Json json =  null;
		if(isQueRu){
			json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck,MissingVisceraRecord.getInstance().getMissingRsn());
		}else{
			json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck);
		}
		
		
		if(json.isSuccess()){
			//
			tblAnatomyCheck.setId(json.getMsg());
			//增加一行 解剖结果 放在最后一行，并选中最后一行
			AddOneDataToAnatomyResultTable(tblAnatomyCheck);
			//清空 11个ListView 的选择
			clear11ListViewSelect();
			//脏器树 选择下一个
		}else{
			showMessage(json.getMsg());
		}
	}

	/**
	 * 自溶（脏器）
	 * 
	 * @param event
	 */
	//--------------------------------增加签字
	@FXML
	void onAutolyzeBtn2Action(ActionEvent event) {
		
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			showErrorMessage("请先选择动物！");
			return ;
		}
		TreeItem<String> treeSelectedItem = visceraTree.getSelectionModel().getSelectedItem();
		if(null == treeSelectedItem){
			showErrorMessage("请先选择脏器！");
			return ;
		}
		String animalCode = animal.getAnimalCode();
		Map<String,Object> visceraMap = visceraName2MapMap.get(treeSelectedItem.getValue());
		Integer visceraType = (Integer) visceraMap.get("visceraType");
		String visceraCode = (String) visceraMap.get("visceraCode");
		String visceraName = (String) visceraMap.get("visceraName");
		String subVisceraCode = null;
		String subVisceraName = null;
		if(treeSelectedItem.getParent() != rootNode ){
			subVisceraCode = (String) visceraMap.get("subVisceraCode");
			subVisceraName = (String) visceraMap.get("subVisceraName");
		}
		//判断该脏器是否已经标记自溶
		boolean isHasRecord = BaseService.getInstance().getTblAnatomyCheckService().isHasRecord_1(studyNo,animalCode,visceraCode,subVisceraCode);
		if(isHasRecord){
			showErrorMessage("该脏器已有异常记录或已标记自溶！");
			return ;
		}
		if(Messager.showSimpleConfirm("提示","确定将该脏器标识为‘自溶’吗？")){
				
				Integer autolyzeFlag = 1;//自溶
				
				String currentUserName = SaveUserInfo.getUserName();
				Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
				
				TblAnatomyCheck tblAnatomyCheck = new TblAnatomyCheck();
//				tblAnatomyCheck.setSessionId(sessionId);
				tblAnatomyCheck.setStudyNo(studyNo);
				tblAnatomyCheck.setAnimalCode(animalCode);
				tblAnatomyCheck.setVisceraType(visceraType);
				tblAnatomyCheck.setVisceraCode(visceraCode);
				tblAnatomyCheck.setVisceraName(visceraName);
				if(treeSelectedItem.getParent() != rootNode ){
					tblAnatomyCheck.setSubVisceraCode(subVisceraCode);
					tblAnatomyCheck.setSubVisceraName(subVisceraName);
				}
				
				tblAnatomyCheck.setAutolyzaFlag(autolyzeFlag);//自溶解剖1
				
				tblAnatomyCheck.setOperator(currentUserName);
				tblAnatomyCheck.setOperateTime(currentDate);	
				
				Json json = BaseService.getInstance().getTblAnatomyCheckService().saveOne(tblAnatomyCheck);
				if(json.isSuccess()){
					//
					tblAnatomyCheck.setId(json.getMsg());
					//增加一行 解剖结果 放在最后一行，并选中最后一行
					AddOneDataToAnatomyResultTable(tblAnatomyCheck);
					//清空 11个ListView 的选择
					clear11ListViewSelect();
				}else{
					showMessage(json.getMsg());
				}
				
		}
		
	}

	/**解剖学所见部位(刷新ListView)
	 * @param event
	 */
	@FXML
	void onanatomyPosHyperlink(ActionEvent event){
		
		String selectItem = anatomyPosListView.getSelectionModel().getSelectedItem();
		
		//解剖学所见部位
		TreeItem<String> newValue = visceraTree.getSelectionModel().getSelectedItem();
		if(null != newValue){
			String visceraName = newValue.getValue();
			Map<String,Object> map = visceraName2MapMap.get(visceraName);
			if(null != map){
				String visceraCode = (String) map.get("visceraCode");
				//根据脏器编号更新 解剖学所见部位
				updateAnatomyPosListViewData(visceraCode);
			}
		}else{
			//清空ListView
			updateAnatomyPosListViewData(null);
		}
		if(null != selectItem && data_anatomyPosListView.size() > 0){
			for(String str : data_anatomyPosListView){
				if(str.equals(selectItem)){
					anatomyPosListView.getSelectionModel().select(str);
					break;
				}
			}
		}
	}

	/**解剖所见(刷新ListView)
	 * @param event
	 */
	@FXML
	void onanatomyFindingHyperlink(ActionEvent event){
		//解剖所见
		String selectItem  = anatomyFindingListView.getSelectionModel().getSelectedItem();
		
		//解剖通用所见
			data_anatomyFindingListView_tongyong.clear();
			pyDescCNMap_tongyong.clear();
			anatomyFinding_tongyongDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictType(2,sortMethod);
			if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
				for(DictPathCommon obj:dictPathCommon2List){
					data_anatomyFindingListView_tongyong.add(obj.getDescCn());
					if(obj.getDescCn().equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(selectItem);
					}
					anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
					pyDescCNMap_tongyong.put(obj.getPy(), obj.getDescCn());
				}
			}
			
			//解剖特殊所见
			//更新ListView  
			TreeItem<String> treeSelectItem = visceraTree.getSelectionModel().getSelectedItem();
			if(null != treeSelectItem){
				String visceraName = treeSelectItem.getValue();
				Map<String,Object> map = visceraName2MapMap.get(visceraName);
				if(null != map){
					String visceraCode = (String) map.get("visceraCode");
					//根据脏器编号更新 解剖学所见(通用，特殊)
					updateAnatomyFindingListViewData(visceraCode);
				}
			}else{
				updateAnatomyFindingListViewData(null);
			}
			
		if(tongyongRadioButton.isSelected()){
			if(null != selectItem && data_anatomyFindingListView_tongyong.size() > 0){
				for(String str : data_anatomyFindingListView_tongyong){
					if(str.equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(str);
						break;
					}
				}
			}
		}else{
			if(null != selectItem && data_anatomyFindingListView_tesu.size() > 0){
				for(String str : data_anatomyFindingListView_tesu){
					if(str.equals(selectItem)){
						anatomyFindingListView.getSelectionModel().select(str);
						break;
					}
				}
			}
		}
		
	}
	/**体表部位(刷新ListView)
	 * @param event
	 */
	@FXML
	void onbodySufaceHyperlink(ActionEvent event){
		//4:体表部位
		String selectItem  = bodySufacePosListView.getSelectionModel().getSelectedItem();
		data_bodySufacePosListView.clear();
		pyDescCNMap_bodySuface.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,sortMethod);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					bodySufacePosListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_bodySuface.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**位置(刷新ListView)
	 * @param event
	 */
	@FXML
	void onposHyperlink(ActionEvent event){
		//5:位置
		String selectItem  = posListView.getSelectionModel().getSelectedItem();
		data_posListView.clear();
		pyDescCNMap_pos.clear();
		List<DictPathCommon> dictPathCommon5List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(5,sortMethod);
		if(null != dictPathCommon5List && dictPathCommon5List.size() > 0){
			for(DictPathCommon obj:dictPathCommon5List){
				data_posListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					posListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_pos.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**范围(刷新ListView)
	 * @param event
	 */
	@FXML
	void onrangeHyperlink(ActionEvent event){
		//6:分布、范围
		String selectItem  = rangeListView.getSelectionModel().getSelectedItem();
		data_rangeListView.clear();
		pyDescCNMap_range.clear();
		List<DictPathCommon> dictPathCommon6List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(6,sortMethod);
		if(null != dictPathCommon6List && dictPathCommon6List.size() > 0){
			for(DictPathCommon obj:dictPathCommon6List){
				data_rangeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					rangeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_range.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**数量(刷新ListView)
	 * @param event
	 */
	@FXML
	void onnumberHyperlink(ActionEvent event){
		//7:数量
		String selectItem  = numberListView.getSelectionModel().getSelectedItem();
		data_numberListView.clear();
		pyDescCNMap_number.clear();
		List<DictPathCommon> dictPathCommon7List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(7,sortMethod);
		if(null != dictPathCommon7List && dictPathCommon7List.size() > 0){
			for(DictPathCommon obj:dictPathCommon7List){
				data_numberListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					numberListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_number.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**形状(刷新ListView)
	 * @param event
	 */
	@FXML
	void onshapeHyperlink(ActionEvent event){
		//8:形状
		String selectItem  = shapeListView.getSelectionModel().getSelectedItem();
		data_shapeListView.clear();
		pyDescCNMap_shape.clear();
		List<DictPathCommon> dictPathCommon8List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(8,sortMethod);
		if(null != dictPathCommon8List && dictPathCommon8List.size() > 0){
			for(DictPathCommon obj:dictPathCommon8List){
				data_shapeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					shapeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_shape.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**颜色(刷新ListView)
	 * @param event
	 */
	@FXML
	void oncolorHyperlink(ActionEvent event){
		//9:颜色
		String selectItem  = colorListView.getSelectionModel().getSelectedItem();
		data_colorListView.clear();
		pyDescCNMap_color.clear();
		List<DictPathCommon> dictPathCommon9List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(9,sortMethod);
		if(null != dictPathCommon9List && dictPathCommon9List.size() > 0){
			for(DictPathCommon obj:dictPathCommon9List){
				data_colorListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					colorListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_color.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**硬度(刷新ListView)
	 * @param event
	 */
	@FXML
	void ontextureHyperlink(ActionEvent event){
		//10:硬度
		String selectItem  = textureListView.getSelectionModel().getSelectedItem();
		data_textureListView.clear();
		pyDescCNMap_texture.clear();
		List<DictPathCommon> dictPathCommon10List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(10,sortMethod);
		if(null != dictPathCommon10List && dictPathCommon10List.size() > 0){
			for(DictPathCommon obj:dictPathCommon10List){
				data_textureListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					textureListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_texture.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**大小(刷新ListView)
	 * @param event
	 */
	@FXML
	void onSizeHyperlink(ActionEvent event){
		//11:大小
		String selectItem  = sizeListView.getSelectionModel().getSelectedItem();
		data_sizeListView.clear();
		pyDescCNMap_size.clear();
		List<DictPathCommon> dictPathCommon11List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(11,sortMethod);
		if(null != dictPathCommon11List && dictPathCommon11List.size() > 0){
			for(DictPathCommon obj:dictPathCommon11List){
				data_sizeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					sizeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_size.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}
	/**病变程度(刷新ListView)
	 * @param event
	 */
	@FXML
	void onlesionHyperlink(ActionEvent event){
		//12:病变程度
		String selectItem  = lesionDegreeListView.getSelectionModel().getSelectedItem();
		data_lesionDegreeListView.clear();
		pyDescCNMap_lesionDegree.clear();
		List<DictPathCommon> dictPathCommon12List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(12,sortMethod);
		if(null != dictPathCommon12List && dictPathCommon12List.size() > 0){
			for(DictPathCommon obj:dictPathCommon12List){
				data_lesionDegreeListView.add(obj.getDescCn());
				if(obj.getDescCn().equals(selectItem)){
					lesionDegreeListView.getSelectionModel().select(selectItem);
				}
				pyDescCNMap_lesionDegree.put(obj.getPy(), obj.getDescCn());
			}
		}
		
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
	 * 关闭
	 * 
	 * @param event
	 */
	@FXML
	void onExitBtn(ActionEvent event) {
		
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	private static AdditionalPage_anatomy instance;

	public static AdditionalPage_anatomy getInstance() {
		if (null == instance) {
			instance = new AdditionalPage_anatomy();
		}
		return instance;
	}

	public AdditionalPage_anatomy() {
	}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO 初始化
		instance = this;
		//1.初始化 当前操作者
		initHandlerLabel();
		//2.初始化动物表
		initAnimalTable();
		//3.初始化动物解剖标签页
		initAnatomyTab();
	}
	

	/**
	 * 初始化 当前操作者
	 */
	private void initHandlerLabel() {
		handlersLabel.setText(SaveUserInfo.getRealName() != null ? SaveUserInfo.getRealName():"");
	}
    
	/**
	 * 初始化动物表
	 */
	private void initAnimalTable() {
		animalTable.setItems(data_animalTable);
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("animalCode"));
		stateCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("state"));
		genderCol.setCellValueFactory(new PropertyValueFactory<Animal,String>("gender"));
		animalTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Animal>(){

			@Override
			public void changed(ObservableValue<? extends Animal> arg0, Animal arg1, Animal newValue) {
				if(null != newValue){
					//  动物表格 onChange
					String animalCode = newValue.getAnimalCode();
						//更新 选择脏器树 
						updateVisceraTree(taskId,studyNo,animalCode);
						//设置5按钮状态,true可用，false不可用
						set5BtnState(newValue.getAutolyzeFlag() == 0);
						//更新解剖结果表格（anatomyResultTalbe）
						updateAnatomyResultTableData(studyNo,animalCode);
						
						//动物编号  解剖者  解剖开始时间 Label
						animalCodeLabel_anatomy.setText(newValue.getAnimalCode());
				}else{
					
						//更新 选择脏器树 ，根据任务id
						updateVisceraTree(null,null,null);
						set5BtnState(false);
						updateAnatomyResultTableData(null,null);
						
						//动物编号  解剖者  解剖开始时间 Label
						animalCodeLabel_anatomy.setText("");
						
				}
				
			}
			
		});
		animalCodeCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
	    	 
	    	 @Override
	    	 public TableCell<Animal, String> call(
	    			 TableColumn<Animal, String> param) {
	    		 TableCell<Animal, String> cell =
	    				 new TableCell<Animal, String>() {
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
		stateCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
			@Override
			public TableCell<Animal, String> call(
					TableColumn<Animal, String> param) {
				TableCell<Animal, String> cell =
						new TableCell<Animal, String>() {
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
		genderCol.setCellFactory(new Callback<TableColumn<Animal,String>,TableCell<Animal,String>>(){
			
			@Override
			public TableCell<Animal, String> call(
					TableColumn<Animal, String> param) {
				TableCell<Animal, String> cell =
						new TableCell<Animal, String>() {
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
	}

	/**
	 * 初始化动物解剖标签页
	 */
	private void initAnatomyTab() {
		//初始化选择脏器树
		initTaskTree();
		initBodySufacePosListView();
		//初始化通用/特殊 所见 RadioButton
		inittgRadioButton();
		//初始化 11个  ListView
		init11ListView();
		//10个ListView填充数据(2,4,5,6,7,8,9,10,11,12)
		initData10ListView();
		//初始化 字典排序ComboBox
		initDictSortMethodComboBox();
		//初始化 解剖信息表（anatomyResultTable）
		initAnatomyResultTalbe();
		//初始化快速查找ComboBox
		initSerachComboBox();
		//初始化快速查找TextField
		initSearchTextField();
		//初始化快速查找ListView 
		initSearchListView();
//		bodySufacePosListView.setStyle("-fx-border-color:blue;");
//		bodySufacePosListView.setStyle("");
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
//		searchListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				String newValue = searchListView.getSelectionModel().getSelectedItem();
//				System.out.println("searchListView 点击事件");
//				if(null != newValue){
//					//快速查找输入框清空
//					searchTextField.clear();
//					searchTextField.requestFocus();
//					selectandScrollToValue(newValue);
//				}
//			}
//		});
		
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
//		searchListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
//
//			@Override
//			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
//				if(null != newValue){
//					selectandScrollToValue(newValue);
//				}
//				
//			}
//
//		});
	}
	
	/**快速查找，选择指定项，并移到指定行
	 * @param newValue
	 */
	private void selectandScrollToValue(String newValue) {
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		if("解剖学所见部位".equals(selectItem)){
			anatomyPosListView.getSelectionModel().select(newValue);
			int index=anatomyPosListView.getSelectionModel().getSelectedIndex();
			anatomyPosListView.getFocusModel().focus(index);
			anatomyPosListView.scrollTo(getIndex(data_anatomyPosListView,newValue));
		}else if("解剖通用所见".equals(selectItem)){
			anatomyFindingListView.getSelectionModel().select(newValue);
//			anatomyFindingListView.getFocusModel().
			int index=anatomyFindingListView.getSelectionModel().getSelectedIndex();
			anatomyFindingListView.getFocusModel().focus(index);
			anatomyFindingListView.scrollTo(getIndex(data_anatomyFindingListView_tongyong,newValue));
		}else if("解剖特殊所见".equals(selectItem)){
			anatomyFindingListView.getSelectionModel().select(newValue);
			showMessage(getIndex(data_anatomyFindingListView_tongyong,newValue)+"");
			anatomyFindingListView.scrollTo(getIndex(data_anatomyFindingListView_tesu,newValue));
		}else if("体表部位".equals(selectItem)){
			bodySufacePosListView.getSelectionModel().select(newValue);
			bodySufacePosListView.scrollTo(getIndex(data_bodySufacePosListView,newValue));
		}else if("位置".equals(selectItem)){
			posListView.getSelectionModel().select(newValue);
			posListView.scrollTo(getIndex(data_posListView,newValue));
		}else if("形状".equals(selectItem)){
			shapeListView.getSelectionModel().select(newValue);
			shapeListView.scrollTo(getIndex(data_shapeListView,newValue));
		}else if("颜色".equals(selectItem)){
			colorListView.getSelectionModel().select(newValue);
			colorListView.scrollTo(getIndex(data_colorListView,newValue));
		}else if("硬度".equals(selectItem)){
			textureListView.getSelectionModel().select(newValue);
			textureListView.scrollTo(getIndex(data_textureListView,newValue));
		}else if("数量".equals(selectItem)){
			numberListView.getSelectionModel().select(newValue);
			numberListView.scrollTo(getIndex(data_numberListView,newValue));
		}else if("范围".equals(selectItem)){
			rangeListView.getSelectionModel().select(newValue);
			rangeListView.scrollTo(getIndex(data_rangeListView,newValue));
		}else if("病变程度".equals(selectItem)){
			lesionDegreeListView.getSelectionModel().select(newValue);
			lesionDegreeListView.scrollTo(getIndex(data_lesionDegreeListView,newValue));
		}else if("大小".equals(selectItem)){
			sizeListView.getSelectionModel().select(newValue);
			sizeListView.scrollTo(getIndex(data_sizeListView,newValue));
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
	 * 显示 快速查找ListView 
	 */
	private void showSearchListView() {
		data_searchListView.clear();
		String selectItem = searchComboBox.getSelectionModel().getSelectedItem();
		String py = searchTextField.getText();
		if("解剖学所见部位".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_anatomyPos,py);
		}else if("解剖通用所见".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_tongyong,py);
		}else if("解剖特殊所见".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_teshu,py);
		}else if("体表部位".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_bodySuface,py);
		}else if("位置".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_pos,py);
		}else if("形状".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_shape,py);
		}else if("颜色".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_color,py);
		}else if("硬度".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_texture,py);
		}else if("数量".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_number,py);
		}else if("范围".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_range,py);
		}else if("病变程度".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_lesionDegree,py);
		}else if("大小".equals(selectItem)){
			updateData_searchListView(pyDescCNMap_size,py);
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
	 * 初始化 解剖信息表（anatomyResultTable）
	 */
	private void initAnatomyResultTalbe() {
		// 
		anatomyResultTable.setItems(data_anatomyResultTable);
		anatomyResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//清空选择
		anatomyResultTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnatomyCheck>(){

			@Override
			public void changed(ObservableValue<? extends AnatomyCheck> arg0, AnatomyCheck arg1,
					AnatomyCheck arg2) {
				anatomyResultTable.getSelectionModel().clearSelection();
			}
		});
		visceraNameCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("visceraName"));
		findingCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,String>("finding"));
		operateCol.setCellValueFactory(new PropertyValueFactory<AnatomyCheck,Boolean>("operate"));
		operateCol.setCellFactory(new Callback<TableColumn<AnatomyCheck, Boolean>, TableCell<AnatomyCheck, Boolean>>() {

            public TableCell<AnatomyCheck, Boolean> call(TableColumn<AnatomyCheck, Boolean> p) {

            	HyperlinkCell<AnatomyCheck, Boolean> cell = new HyperlinkCell<AnatomyCheck, Boolean>();
                return cell;

            }

        });
	}

	/**
	 * 初始化快速查找ComboBox
	 */
	private void initSerachComboBox(){
		searchComboBox.getSelectionModel().select(0);
		anatomyPosListView.setStyle("-fx-border-color:blue;");
		searchComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if(null != newValue){
					searchTextField.setPromptText("快速检索文本(拼音首字母)");
					if("解剖通用所见".equals(newValue)){
						if(!tongyongRadioButton.isSelected()){
							tongyongRadioButton.setSelected(true);
						}
					}else if("解剖特殊所见".equals(newValue)){
						if(!tesuRadioButton.isSelected()){
							tesuRadioButton.setSelected(true);
						}
					}
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
		if("解剖学所见部位".equals(listName)){
			anatomyPosListView.setStyle("-fx-border-color:blue;");
		}else if("解剖通用所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color:blue;");
		}else if("解剖特殊所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color:blue;");
		}else if("体表部位".equals(listName)){
			bodySufacePosListView.setStyle("-fx-border-color:blue;");
		}else if("位置".equals(listName)){
			posListView.setStyle("-fx-border-color:blue;");
		}else if("形状".equals(listName)){
			shapeListView.setStyle("-fx-border-color:blue;");
		}else if("颜色".equals(listName)){
			colorListView.setStyle("-fx-border-color:blue;");
		}else if("硬度".equals(listName)){
			textureListView.setStyle("-fx-border-color:blue;");
		}else if("数量".equals(listName)){
			numberListView.setStyle("-fx-border-color:blue;");
		}else if("范围".equals(listName)){
			rangeListView.setStyle("-fx-border-color:blue;");
		}else if("病变程度".equals(listName)){
			lesionDegreeListView.setStyle("-fx-border-color:blue;");
		}else if("大小".equals(listName)){
			sizeListView.setStyle("-fx-border-color:blue;");
		}
	}
	/**清除ListView 的边框颜色
	 * @param listName
	 */
	private void clearStyle(String listName){
		if("解剖学所见部位".equals(listName)){
			anatomyPosListView.setStyle("-fx-border-color: #919191;");
		}else if("解剖通用所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color: #919191;");
		}else if("解剖特殊所见".equals(listName)){
			anatomyFindingListView.setStyle("-fx-border-color: #919191;");
		}else if("体表部位".equals(listName)){
			bodySufacePosListView.setStyle("-fx-border-color: #919191;");
		}else if("位置".equals(listName)){
			posListView.setStyle("-fx-border-color: #919191;");
		}else if("形状".equals(listName)){
			shapeListView.setStyle("-fx-border-color: #919191;");
		}else if("颜色".equals(listName)){
			colorListView.setStyle("-fx-border-color: #919191;");
		}else if("硬度".equals(listName)){
			textureListView.setStyle("-fx-border-color: #919191;");
		}else if("数量".equals(listName)){
			numberListView.setStyle("-fx-border-color: #919191;");
		}else if("范围".equals(listName)){
			rangeListView.setStyle("-fx-border-color: #919191;");
		}else if("病变程度".equals(listName)){
			lesionDegreeListView.setStyle("-fx-border-color: #919191;");
		}else if("大小".equals(listName)){
			sizeListView.setStyle("-fx-border-color: #919191;");
		}
	}
	
	/**
	 * 初始化 字典排序ComboBox
	 */
	private void initDictSortMethodComboBox() {
		//  字典排序
		dictSortMethodComboBox.getSelectionModel().select(0);
		sortMethod = 1;
		dictSortMethodComboBox.getSelectionModel().selectedIndexProperty()
			.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
				if(null != newValue){
					sortMethod = newValue.intValue()+1;
					//解剖学所见部位(刷新ListView)
					 onanatomyPosHyperlink(null);
					//解剖所见(刷新ListView)
						onanatomyFindingHyperlink(null);
						//体表部位(刷新ListView)
						onbodySufaceHyperlink(null);
						//位置(刷新ListView)
						onposHyperlink(null);
						//范围(刷新ListView)
						onrangeHyperlink(null);
						//数量(刷新ListView)
						onnumberHyperlink(null);
						//形状(刷新ListView)
						onshapeHyperlink(null);
						//颜色(刷新ListView)
						oncolorHyperlink(null);
						//硬度(刷新ListView)
						 ontextureHyperlink(null);
						//大小(刷新ListView)
						 onSizeHyperlink(null);
						//病变程度(刷新ListView)
						 onlesionHyperlink(null);
				}
				
			}
		});
	}

	/**
	 * 10个ListView填充数据(2,4,5,6,7,8,9,10,11,12)
	 */
	private void initData10ListView() {
		//2:解剖通用所见 TODO 
		data_anatomyFindingListView_tongyong.clear();
		pyDescCNMap_tongyong.clear();
		anatomyFinding_tongyongDictPathCommonMap.clear();
		List<DictPathCommon> dictPathCommon2List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(2,sortMethod);
		if(null != dictPathCommon2List && dictPathCommon2List.size() > 0){
			for(DictPathCommon obj:dictPathCommon2List){
				data_anatomyFindingListView_tongyong.add(obj.getDescCn());
				anatomyFinding_tongyongDictPathCommonMap.put(obj.getDescCn(), obj);
				pyDescCNMap_tongyong.put(obj.getPy(), obj.getDescCn());
			}
		}
		//4:体表部位
		data_bodySufacePosListView.clear();
		pyDescCNMap_bodySuface.clear();
		List<DictPathCommon> dictPathCommon4List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(4,sortMethod);
		if(null != dictPathCommon4List && dictPathCommon4List.size() > 0){
			for(DictPathCommon obj:dictPathCommon4List){
				data_bodySufacePosListView.add(obj.getDescCn());
				pyDescCNMap_bodySuface.put(obj.getPy(), obj.getDescCn());
			}
		}
		//5:位置
		data_posListView.clear();
		pyDescCNMap_pos.clear();
		List<DictPathCommon> dictPathCommon5List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(5,sortMethod);
		if(null != dictPathCommon5List && dictPathCommon5List.size() > 0){
			for(DictPathCommon obj:dictPathCommon5List){
				data_posListView.add(obj.getDescCn());
				pyDescCNMap_pos.put(obj.getPy(), obj.getDescCn());
			}
		}
		//6:分布、范围
		data_rangeListView.clear();
		pyDescCNMap_range.clear();
		List<DictPathCommon> dictPathCommon6List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(6,sortMethod);
		if(null != dictPathCommon6List && dictPathCommon6List.size() > 0){
			for(DictPathCommon obj:dictPathCommon6List){
				data_rangeListView.add(obj.getDescCn());
				pyDescCNMap_range.put(obj.getPy(), obj.getDescCn());
			}
		}
		//7:数量
		data_numberListView.clear();
		pyDescCNMap_number.clear();
		List<DictPathCommon> dictPathCommon7List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(7,sortMethod);
		if(null != dictPathCommon7List && dictPathCommon7List.size() > 0){
			for(DictPathCommon obj:dictPathCommon7List){
				data_numberListView.add(obj.getDescCn());
				pyDescCNMap_number.put(obj.getPy(), obj.getDescCn());
			}
		}
		//8:形状
		data_shapeListView.clear();
		pyDescCNMap_shape.clear();
		List<DictPathCommon> dictPathCommon8List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(8,sortMethod);
		if(null != dictPathCommon8List && dictPathCommon8List.size() > 0){
			for(DictPathCommon obj:dictPathCommon8List){
				data_shapeListView.add(obj.getDescCn());
				pyDescCNMap_shape.put(obj.getPy(), obj.getDescCn());
			}
		}
		//9:颜色
		data_colorListView.clear();
		pyDescCNMap_color.clear();
		List<DictPathCommon> dictPathCommon9List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(9,sortMethod);
		if(null != dictPathCommon9List && dictPathCommon9List.size() > 0){
			for(DictPathCommon obj:dictPathCommon9List){
				data_colorListView.add(obj.getDescCn());
				pyDescCNMap_color.put(obj.getPy(), obj.getDescCn());
			}
		}
		//10:硬度
		data_textureListView.clear();
		pyDescCNMap_texture.clear();
		List<DictPathCommon> dictPathCommon10List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(10,sortMethod);
		if(null != dictPathCommon10List && dictPathCommon10List.size() > 0){
			for(DictPathCommon obj:dictPathCommon10List){
				data_textureListView.add(obj.getDescCn());
				pyDescCNMap_texture.put(obj.getPy(), obj.getDescCn());
			}
		}
		//11:大小
		data_sizeListView.clear();
		pyDescCNMap_size.clear();
		List<DictPathCommon> dictPathCommon11List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(11,sortMethod);
		if(null != dictPathCommon11List && dictPathCommon11List.size() > 0){
			for(DictPathCommon obj:dictPathCommon11List){
				data_sizeListView.add(obj.getDescCn());
				pyDescCNMap_size.put(obj.getPy(), obj.getDescCn());
			}
		}
		//12:病变程度
		data_lesionDegreeListView.clear();
		pyDescCNMap_lesionDegree.clear();
		List<DictPathCommon> dictPathCommon12List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictType(12,sortMethod);
		if(null != dictPathCommon12List && dictPathCommon12List.size() > 0){
			for(DictPathCommon obj:dictPathCommon12List){
				data_lesionDegreeListView.add(obj.getDescCn());
				pyDescCNMap_lesionDegree.put(obj.getPy(), obj.getDescCn());
			}
		}
		
	}

	/**
	 * 初始化 11个  ListView
	 */
	private void init11ListView() {
		//解剖学所见部位
		anatomyPosListView.setItems(data_anatomyPosListView);
		anatomyPosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				anatomyPosListView.setUserData(true);
				if(null != newValue){
					anatomyPosListView.setId(newValue);
				}else{
					anatomyPosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		anatomyPosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("解剖学所见部位");
			}
		});
		
		//解剖所见(通用)
		anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
		anatomyFindingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
//				if(tongyongRadioButton.isSelected()){
//					//快速查找选中指定项
//					searchComboBox.getSelectionModel().select("解剖通用所见");
//				}else{
//					//快速查找选中指定项
//					searchComboBox.getSelectionModel().select("解剖特殊所见");
//				}
			}
		});
		anatomyFindingListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(tongyongRadioButton.isSelected()){
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖通用所见");
				}else{
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖特殊所见");
				}
			}});
		
		//体表部位
		bodySufacePosListView.setItems(data_bodySufacePosListView);
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				bodySufacePosListView.setUserData(true);
				if(null != newValue){
					bodySufacePosListView.setId(newValue);
				}else{
					bodySufacePosListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		bodySufacePosListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("体表部位");
			}
		});
		//位置
		posListView.setItems(data_posListView);
		posListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				posListView.setUserData(true);
				if(null != newValue){
					posListView.setId(newValue);
				}else{
					posListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		posListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("位置");
			}
		});
		//形状
		shapeListView.setItems(data_shapeListView);
		shapeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				shapeListView.setUserData(true);
				if(null != newValue){
					shapeListView.setId(newValue);
				}else{
					shapeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		shapeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("形状");
			}
		});
		//颜色
		colorListView.setItems(data_colorListView);
		colorListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				colorListView.setUserData(true);
				if(null != newValue){
					colorListView.setId(newValue);
				}else{
					colorListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		colorListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("颜色");
			}
		});
		//硬度
		textureListView.setItems(data_textureListView);
		textureListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				textureListView.setUserData(true);
				if(null != newValue){
					textureListView.setId(newValue);
				}else{
					textureListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		textureListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("硬度");
			}
		});
		
		//数量
		numberListView.setItems(data_numberListView);
		numberListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				numberListView.setUserData(true);
				if(null != newValue){
					numberListView.setId(newValue);
				}else{
					numberListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		numberListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("数量");
			}
		});
		//范围
		rangeListView.setItems(data_rangeListView);
		rangeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				rangeListView.setUserData(true);
				if(null != newValue){
					rangeListView.setId(newValue);
				}else{
					rangeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		rangeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("范围");
			}
		});
		//病变程度
		lesionDegreeListView.setItems(data_lesionDegreeListView);
		lesionDegreeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				lesionDegreeListView.setUserData(true);
				if(null != newValue){
					lesionDegreeListView.setId(newValue);
				}else{
					lesionDegreeListView.setId("");
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
		lesionDegreeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

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
				searchComboBox.getSelectionModel().select("病变程度");
			}
		});
		//大小
		sizeListView.setItems(data_sizeListView);
		sizeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				sizeListView.setUserData(true);
				if(null != newValue){
					sizeListView.setId(newValue);
					
					sizeTextField.setText(newValue);
					
				}else{
					sizeListView.setId("");
				}
				
			}
		});
		sizeListView.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				@SuppressWarnings("unchecked")
				ListView<String> list = (ListView<String>) event.getSource();
				Boolean isChange = (Boolean) list.getUserData();
				if(null != isChange && !isChange){
					String selectItem = list.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.equals(list.getId())){
						list.getSelectionModel().clearSelection();
						
						sizeTextField.setText("");
					}
				}
				list.setUserData(false);
				
				//快速查找选中指定项
				searchComboBox.getSelectionModel().select("大小");
			}
		});
		
		sizeTextField.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue && !"".equals(newValue)){
					sizeListView.getSelectionModel().clearSelection();
					if(data_sizeListView.size() > 0){
						for(String str :data_sizeListView){
							if(newValue.equals(str)){
								sizeListView.getSelectionModel().select(str);
							}
						}
					}
				}else{
					sizeListView.getSelectionModel().clearSelection();
				}
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
				
			}
		});
	}

	/**
	 * 初始化选择脏器树
	 */
	private void initTaskTree() {
		rootNode.setValue("选择脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				// 选择脏器树      change事件
				visceraTree.setUserData(true);
				if(null != newValue){
					String visceraName = newValue.getValue();
					bodySufacePosListView.getSelectionModel().clearSelection();
					Map<String,Object> map = visceraName2MapMap.get(visceraName);
					if(null != map){
						String visceraCode = (String) map.get("visceraCode");
						//根据脏器编号更新 解剖学所见部位
						updateAnatomyPosListViewData(visceraCode);
						//根据脏器编号更新 解剖学所见(通用，特殊)
						updateAnatomyFindingListViewData(visceraCode);
					}
					visceraTree.setId(visceraName);
					
					
				}else{
					//清空ListView
					updateAnatomyPosListViewData(null);
					updateAnatomyFindingListViewData(null);
					
					visceraTree.setId("");
					updateAnatomyFindingLabelText();
				}
				
			}
			
		});
		
		visceraTree.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				Boolean isChange = (Boolean) visceraTree.getUserData();
				if(null != isChange && !isChange){
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem && selectItem.getValue().equals(visceraTree.getId())){
						visceraTree.getSelectionModel().clearSelection();
					}
				}
				visceraTree.setUserData(false);
				
				// 
				//更新 anatomyFindingLabel 值
				updateAnatomyFindingLabelText();
			}
		});
	}
	public void initBodySufacePosListView(){
		bodySufacePosListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null != newValue){
					visceraTree.getSelectionModel().clearSelection();
					tongyongRadioButton.setSelected(true);
				}
			}
		});

		
	}
	/**
	 * 初始化通用/特殊 所见 RadioButton
	 */
	private void inittgRadioButton() {
		
		tongyongRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView
					anatomyFindingListView.getSelectionModel().clearSelection();
					anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
					
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖通用所见");
				}
			}});
		
		tesuRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>(){
			
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if(newValue){
					//更新ListView  
					TreeItem<String> selectItem = visceraTree.getSelectionModel().getSelectedItem();
					if(null != selectItem){
						String visceraName = selectItem.getValue();
						Map<String,Object> map = visceraName2MapMap.get(visceraName);
						if(null != map){
							String visceraCode = (String) map.get("visceraCode");
							//根据脏器编号更新 解剖学所见(通用，特殊)
							updateAnatomyFindingListViewData(visceraCode);
						}
					}else{
						updateAnatomyFindingListViewData(null);
					}
					
					//快速查找选中指定项
					searchComboBox.getSelectionModel().select("解剖特殊所见");
				}
			}});
	}
	

	
	/**
	 * 症状观察 
	 */
	@FXML
	private void symptomObservationBtnAction(){
		if(null == animalTable.getSelectionModel().getSelectedItem()){
			showErrorMessage("请先选择相应动物！");
			return;
		}
		String  animalCode = animalTable.getSelectionModel().getSelectedItem().getAnimalCode();
		try {
//			SymptomObservation.getInstance().start(getStage());
			 Stage stage = Main.stageMap.get("SymptomObservation");
				if(null == stage){
					stage =new Stage();
					stage.initOwner(Main.mainWidow);
					stage.initModality(Modality.APPLICATION_MODAL);
					SymptomObservation.getInstance().start(stage);
					Main.stageMap.put("SymptomObservation",stage);
				}else{
					stage.show();
				}
			SymptomObservation.getInstance().loadData(studyNo, animalCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	


	/**
	 * 加载数据
	 * @param sessionType 
	 * @param taskIdList 
	 * @param sessionIdList 
	 */
	public void loadData(String studyNo,String taskId) {
		this.studyNo = studyNo;
		this.taskId = taskId;
		//1.无按钮设为不可用，设置  isSigned 值
		isSigned = true;
		set5BtnState(false);
		TblAnatomyTask anatomytask = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		if(null != anatomytask && null != anatomytask.getAnatomyCheckFinishSign() && !"".equals(anatomytask.getAnatomyCheckFinishSign())){
			isSigned = true;
		}else{
			isSigned = false;
		}
		// 2.动物数据
		updateAnimalTable(taskId);
		
		//3.
		initHandlerLabel();
		
	}


	/**更新动物表格数据
	 * @param taskId
	 */
	private void updateAnimalTable(String taskId) {
		animalTable.getSelectionModel().clearSelection();
		data_animalTable.clear();
//		根据任务Id查询动物列表，animalCode,gender,autolyzeFlag
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblAnatomyAnimalService()
				.getMapListByTaskId(taskId);
		if(null != mapList && mapList.size() > 0){
			for(Map<String,Object> map:mapList){
				String animalCode = (String) map.get("animalCode");
				Integer autolyzeFlag = (Integer) map.get("autolyzeFlag");
				Integer gender = (Integer) map.get("gender");
				
				data_animalTable.add(new Animal(animalCode,gender,autolyzeFlag));
			}
		}
	}

	/**更新 选择脏器树 
	 * @param taskId		任务id号
	 * @param studyNo		课题编号
	 * @param animalCode	动物编号
	 */
	private void updateVisceraTree(String taskId,String studyNo,String animalCode){
		//更新脏器树（解剖tab）	TODO
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		visceraName2MapMap.clear();
		visceraCodeTreeItemMap.clear();
		if(null != taskId){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblAnatomyCheckService().getVisceraCodeAndName_additional(taskId,studyNo,animalCode);
			if(null != mapList && mapList.size() > 0 ){
				for(Map<String,Object> map:mapList){
					String visceraCode = (String) map.get("visceraCode");
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					TreeItem<String> depNode = null;
					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap.get(visceraCode);
					}else{
						depNode = new TreeItem<String>(visceraName);
						visceraName2MapMap.put(visceraName, map);
						rootNode.getChildren().add(depNode);
						visceraCodeTreeItemMap.put(visceraCode, depNode);
					}
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						TreeItem<String> leafNode = new TreeItem<String>();
						leafNode.setValue(subVisceraName);
						visceraName2MapMap.put(subVisceraName, map);
						depNode.getChildren().add(leafNode);
					}
				}
			}
		}
		
	}
	
	/**
	 * viseraTree增加一数据并，选中它；成功返回true，失败返回false：msg
	 * @param map
	 * @return
	 */
	public Json addOneItemAndSelectIt(String currentVisceraName,Map<String, Object> map) {
		Json json = new Json();
		
		Animal animal = animalTable.getSelectionModel().getSelectedItem();
		if(null == animal){
			json.setMsg("请先在解剖窗口中选择动物！");
			return json;
		}
		if(null != map && null != currentVisceraName){
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			//String subVisceraCode = (String) map.get("subVisceraCode");
			String subVisceraName = (String) map.get("subVisceraName");
			
			//判断是否存在
			if(visceraName2MapMap.keySet().contains(currentVisceraName)){
				json.setMsg("脏器（"+currentVisceraName+"）已存在!");
			}else{
				TreeItem<String> depNode = null;
				//是子节点
				if(currentVisceraName.equals(subVisceraName)){
					if(visceraCodeTreeItemMap.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap.get(visceraCode);
					}else{
						depNode = new TreeItem<String>(visceraName);
						visceraName2MapMap.put(visceraName, map);
						rootNode.getChildren().add(depNode);
						visceraCodeTreeItemMap.put(visceraCode, depNode);
					}
					//增加子节点
					TreeItem<String> leafNode = new TreeItem<String>();
					leafNode.setValue(subVisceraName);
					visceraName2MapMap.put(subVisceraName, map);
					depNode.getChildren().add(leafNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(leafNode);
					visceraTree.getFocusModel().focus(rootNode.getChildren().size()-1);
					visceraTree.scrollTo(rootNode.getChildren().size());
				}else{
					depNode = new TreeItem<String>(visceraName);
					visceraName2MapMap.put(visceraName, map);
					rootNode.getChildren().add(depNode);
					visceraCodeTreeItemMap.put(visceraCode, depNode);
					//成功
					json.setSuccess(true);
					visceraTree.getSelectionModel().select(depNode);
					visceraTree.getFocusModel().focus(rootNode.getChildren().size());
					visceraTree.scrollTo(rootNode.getChildren().size());
				}
				
			}
			
			
		}
		return json;
	}
	
	/**根据脏器编号更新    解剖学所见部位
	 * @param visceraCode
	 */
	private void updateAnatomyPosListViewData(String visceraCode){
		data_anatomyPosListView.clear();
		anatomyPosDictPathCommonMap.clear();
		pyDescCNMap_anatomyPos.clear();
		List<DictPathCommon> dictPathCommon1List = BaseService.getInstance()
				.getDictPathCommonService().getListByDictTypeAndVisceraCode(1, visceraCode,sortMethod);
		if(null != dictPathCommon1List && dictPathCommon1List.size() > 0){
			for(DictPathCommon obj:dictPathCommon1List){
				data_anatomyPosListView.add(obj.getDescCn());
				anatomyPosDictPathCommonMap.put(obj.getDescCn(), obj);
				pyDescCNMap_anatomyPos.put(obj.getPy(), obj.getDescCn());
			}
		}
	}
	
	/**根据脏器编号更新    解剖学所见(通用，特殊)
	 * @param visceraCode
	 */
	private void updateAnatomyFindingListViewData(String visceraCode){
		if(tongyongRadioButton.isSelected()){
			if(visceraCode != null){
//				anatomyFindingListView.getSelectionModel().clearSelection();
				anatomyFindingListView.setItems(data_anatomyFindingListView_tongyong);
			}
			
		}else{
			anatomyFindingListView.getSelectionModel().clearSelection();
			anatomyFindingListView.setItems(data_anatomyFindingListView_tesu);
			data_anatomyFindingListView_tesu.clear();
			pyDescCNMap_teshu.clear();
			anatomyFinding_tesuDictPathCommonMap.clear();
			List<DictPathCommon> dictPathCommon3List = BaseService.getInstance()
					.getDictPathCommonService().getListByDictTypeAndVisceraCode(3, visceraCode,sortMethod);
			if(null != dictPathCommon3List && dictPathCommon3List.size() > 0){
				for(DictPathCommon obj:dictPathCommon3List){
					data_anatomyFindingListView_tesu.add(obj.getDescCn());
					anatomyFinding_tesuDictPathCommonMap.put(obj.getDescCn(), obj);
					pyDescCNMap_teshu.put(obj.getPy(), obj.getDescCn());
				}
			}
		}
	}
	
	/**
	 * 设置5按钮状态,true可用，false不可用
	 */
	private void set5BtnState(boolean flag){
		if(!isSigned){
			autolyzeBtn.setDisable(!flag);
			otherVisceraBtn.setDisable(!flag);
			registerBtn.setDisable(!flag);
			autolyzeBtn2.setDisable(!flag);
		}else{
			autolyzeBtn.setDisable(true);
			otherVisceraBtn.setDisable(true);
			registerBtn.setDisable(true);
			autolyzeBtn2.setDisable(true);
		}
	}
	
	/**
	 * 更新 anatomyFindingLabel 值
	 */
	private void updateAnatomyFindingLabelText(){
		String finding = "";
		
		finding = finding + (visceraTree.getSelectionModel().getSelectedItem() == null ?
				"":visceraTree.getSelectionModel().getSelectedItem().getValue()+" ");
		finding = finding + (bodySufacePosListView.getSelectionModel().getSelectedItem() == null ?
				"":bodySufacePosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyPosListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyPosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (posListView.getSelectionModel().getSelectedItem() == null ?
				"":posListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (numberListView.getSelectionModel().getSelectedItem() == null ?
				"":numberListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (rangeListView.getSelectionModel().getSelectedItem() == null ?
				"":rangeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + 
				((sizeTextField.getText() == null || "".equals(sizeTextField.getText())) ? 
				"":sizeTextField.getText()+" ");
		finding = finding + (colorListView.getSelectionModel().getSelectedItem() == null ?
				"":colorListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (textureListView.getSelectionModel().getSelectedItem() == null ?
				"":textureListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (shapeListView.getSelectionModel().getSelectedItem() == null ?
				"":shapeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		finding = finding + (lesionDegreeListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionDegreeListView.getSelectionModel().getSelectedItem()+" ");
		
		anatomyFindingLabel.setText(finding);
	}
	
	/**不含脏器名称
	 * @return
	 */
	private String getAnatomyFinding(){
		String finding = "";
		
//		finding = finding + (visceraTree.getSelectionModel().getSelectedItem() == null ?
//				"":visceraTree.getSelectionModel().getSelectedItem().getValue()+" ");
		finding = finding + (bodySufacePosListView.getSelectionModel().getSelectedItem() == null ?
				"":bodySufacePosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyPosListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyPosListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (posListView.getSelectionModel().getSelectedItem() == null ?
				"":posListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (numberListView.getSelectionModel().getSelectedItem() == null ?
				"":numberListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (rangeListView.getSelectionModel().getSelectedItem() == null ?
				"":rangeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + 
				((sizeTextField.getText() == null || "".equals(sizeTextField.getText())) ? 
				"":sizeTextField.getText()+" ");
		finding = finding + (colorListView.getSelectionModel().getSelectedItem() == null ?
				"":colorListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (textureListView.getSelectionModel().getSelectedItem() == null ?
				"":textureListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (shapeListView.getSelectionModel().getSelectedItem() == null ?
				"":shapeListView.getSelectionModel().getSelectedItem()+" ");
		finding = finding + (anatomyFindingListView.getSelectionModel().getSelectedItem() == null ?
				"":anatomyFindingListView.getSelectionModel().getSelectedItem()+" ");
		
		finding = finding + (lesionDegreeListView.getSelectionModel().getSelectedItem() == null ?
				"":lesionDegreeListView.getSelectionModel().getSelectedItem()+" ");
		
		return finding;
	}
	
	
	/**
	 * 清空 11个ListView 的选择
	 */
	private void clear11ListViewSelect(){
		
		anatomyPosListView.getSelectionModel().clearSelection(); // 解剖学所见部位ListView
		anatomyFindingListView.getSelectionModel().clearSelection(); // 解剖所见ListView

		bodySufacePosListView.getSelectionModel().clearSelection(); // 体表部位ListView

		posListView.getSelectionModel().clearSelection(); // 位置ListView
		
		shapeListView.getSelectionModel().clearSelection(); // 形状ListView
		
		colorListView.getSelectionModel().clearSelection(); // 颜色ListView

		textureListView.getSelectionModel().clearSelection(); // 硬度ListView
		
		numberListView.getSelectionModel().clearSelection(); // 数量ListView

		rangeListView.getSelectionModel().clearSelection(); // 范围ListView

		lesionDegreeListView.getSelectionModel().clearSelection(); // 病变程度ListView
		
		sizeTextField.setText("");
		sizeListView.getSelectionModel().clearSelection(); // 大小ListView

	}
	
	/**更新解剖结果表格（anatomyResultTalbe）
	 * @param sessionId
	 * @param flag
	 */
	private void updateAnatomyResultTableData(String studyNo,String animalCode){
		//TODO 
		data_anatomyResultTable.clear();
		List<TblAnatomyCheck> tblAnatomyCheckList = BaseService.getInstance()
				.getTblAnatomyCheckService().getListByStudyNoAnimalCode(studyNo,animalCode);
		if(null != tblAnatomyCheckList){
			for(TblAnatomyCheck tblAnatomyCheck:tblAnatomyCheckList){
				String id = tblAnatomyCheck.getId();
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
				data_anatomyResultTable.add(new AnatomyCheck(id,"",visceraName,finding,!isSigned));
			}
		}
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
	 * 增加一行   解剖结果   放在最后一行，并选中最后一行
	 */
	private void AddOneDataToAnatomyResultTable(TblAnatomyCheck tblAnatomyCheck){
		if(null != tblAnatomyCheck){
			String id = tblAnatomyCheck.getId();
			String visceraName = tblAnatomyCheck.getVisceraName();
			String subVisceraName = tblAnatomyCheck.getSubVisceraName();
			if(null != subVisceraName && !"".equals(subVisceraName)){
				visceraName = subVisceraName;
			}
			String finding = "";
			int autolyzaFlag =  tblAnatomyCheck.getAutolyzaFlag();     //自溶标识       0:否     1:是
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
			data_anatomyResultTable.add(new AnatomyCheck(id,tblAnatomyCheck.getSessionId(),visceraName,finding,true));
			anatomyResultTable.getSelectionModel().selectLast();
		}
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AdditionalPage_anatomy.fxml"));
		Scene scene = new Scene(root, 1100, 650);
		stage.setScene(scene);
		stage.setTitle("补录解剖所见");
		 stage.setResizable(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
//				 event.consume();
			}
		});
		Screen screen2 = Screen.getPrimary();  
		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setMinWidth(1124);
		stage.setMinHeight(700);
		stage.setX(bounds.getMinX());  
		stage.setY(bounds.getMinY());  
		stage.setWidth(bounds.getWidth());  
		stage.setHeight(bounds.getHeight());  
//		stage.show();

	}

	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
	void showmsgLabel(String msg){
		msgLabel.setText(msg);
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new KeyValue(msgLabel.opacityProperty(), 1), new KeyValue(msgLabel.translateZProperty(), 10)),
                new KeyFrame(Duration.seconds(3.0), new KeyValue(msgLabel.opacityProperty(), 0), new KeyValue(msgLabel.translateZProperty(), 0)));
        tl.play();
	}
	/**
	 * 病理检查会话
	 * @author Administrator
	 *
	 */
	public static class Animal{
		
		private StringProperty animalCode;			//动物编号
		private StringProperty gender;				//性别
		private StringProperty state;             	//状态
		private IntegerProperty autolyzeFlag;		//自溶标识  0,1
		
		
		public Animal() {
			super();
		}
		
		public Animal(String animalCode,Integer gender,
						Integer autolyzeFlag) {
			this.animalCode = new SimpleStringProperty(animalCode);
			this.state = new SimpleStringProperty(autolyzeFlag == 0 ? "":"自溶");
			setGender(gender == 1 ? "♂":"♀");
			setAutolyzeFlag(autolyzeFlag);
		}

		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode = new SimpleStringProperty(animalCode);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}

		public String getGender() {
			return gender.get();
		}

		public void setGender(String gender) {
			this.gender = new SimpleStringProperty(gender);
		}

		public Integer getAutolyzeFlag() {
			return autolyzeFlag.get();
		}
		public void setAutolyzeFlag(Integer autolyzeFlag) {
			this.autolyzeFlag = new SimpleIntegerProperty(autolyzeFlag);
		}
	}
	
	/**剖检记录
	 * @author Administrator
	 *
	 */
	public static class AnatomyCheck{
		private StringProperty id;			//id
		private StringProperty sessionId;	//会话id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty finding;		//解剖所见
		private BooleanProperty  operate;	//操作
		
		public AnatomyCheck() {
			super();
		}
		public AnatomyCheck(String id, String sessionId, String visceraName, String finding,
				boolean operate) {
			this.id = new SimpleStringProperty(id);
			this.sessionId = new SimpleStringProperty(sessionId);
			this.visceraName = new SimpleStringProperty(visceraName);
			this.finding = new SimpleStringProperty(finding == null ? "":finding);
			this.operate = new SimpleBooleanProperty(operate);
		}


		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getSessionId() {
			return sessionId.get();
		}
		public void setSessionId(String sessionId) {
			this.sessionId = new SimpleStringProperty(sessionId);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getFinding() {
			return finding.get();
		}
		public void setFinding(String finding) {
			this.finding = new SimpleStringProperty(finding);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
		
		
	}
	
	
	/**剖检信息专用
	 * @author Administrator
	 *
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
//	            System.out.println(this.getTableRow().getItem().getClass().getSimpleName());
//				AnatomyCheck ck = (AnatomyCheck) getTableRow().getItem();
//				if(ck.getFinding().equals("自溶")){
//					hyperlink.setDisable(true);
//				}
	            hyperlink.setText("删除");
	            hyperlink.setPrefWidth(50);
	            hyperlink.setPrefHeight(20);
	            hyperlink.setUserData(this.getTableRow().getItem());
	            final TableView<?> tableView = this.getTableView();
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						AnatomyCheck anatomyCheck = (AnatomyCheck) hyperlink.getUserData();
						Json json = BaseService.getInstance().getTblAnatomyCheckService().deleteOne(anatomyCheck.getId());
						if(json.isSuccess()){
							tableView.getItems().remove(anatomyCheck);
						}else{
							showErrorMessage(json.getMsg());
						}
					}
				});
	        }  
	    }  
	}  
	
}
