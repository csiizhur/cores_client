package com.lanen.view.quarantine;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.quarantine.TblQRRequest;
import com.lanen.model.studyplan.DictAnimalStrain;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert2;
import com.lanen.view.main.SignFrame;
/**
 * 登记需求申请
 * @author Administrator
 *
 */
public class RegisterRequestFrame extends Application implements Initializable {

	@FXML
	private TextField iacucCodeText;			//	IACUC编号	IACUCCode		
	@FXML
	private CheckBox studyPlanFinishedCheckBox;	//	有无试验方案	StudyPlanFinished				
	@FXML
	private ComboBox<String> animalTypeComboBox;			//	动物种类	AnimalType	
	private ObservableList<String> data_animalTypeComboBox = FXCollections.observableArrayList();
	private Map<String,String> animalTypeNameIdMap = new HashMap<String,String>();
	@FXML
	private ComboBox<String> animalStrainComboBox;        //	动物品系	AnimalStrain
	private ObservableList<String> data_animalStrainComboBox = FXCollections.observableArrayList();
	@FXML
	private RadioButton animalLevelA;         //	动物级别	AnimalLevel	1
	@FXML
	private RadioButton animalLevelB;         //	动物级别	AnimalLevel	2
	@FXML
	private RadioButton animalLevelC;         //	动物级别	AnimalLevel	3
	@FXML
	private RadioButton animalLevelD;         //	动物级别	AnimalLevel	4
	@FXML
	private TextField numMaleText;                 //	数量雄	NumMale	integer			
	@FXML
	private TextField numFemaleText;              //	数量雌	NumFemale	integer			
	@FXML
	private TextField ageMaleUText;       //	年龄雄上	AgeMaleU	decimal(8,3)	
	@FXML
	private TextField ageMaleLText;        //	年龄雄下	AgeMaleL	decimal(8,3)	
	@FXML
	private TextField ageFemaleUText;      //	年龄雌上	AgeFemaleU	decimal(8,3)	
	@FXML
	private TextField ageFemaleLText;      //	年龄雌下	AgeFemaleL	decimal(8,3)	
	@FXML
	private ComboBox<String> ageUnitComboBox;             //	年龄单位	AgeUnit		
	@FXML
	private TextField weightMaleUText;     //	体重雄上	WeightMaleU	decimal(8,3)	
	@FXML
	private TextField weightMaleLText;     //	体重雄下	WeightMaleL	decimal(8,3)	
	@FXML
	private TextField weightFemaleUText;   //	体重雌上	WeightFemaleU	decimal(8,3)	
	@FXML
	private TextField weightFemaleLText;   //	体重雌下	WeightFemaleL	decimal(8,3)	
	@FXML
	private ComboBox<String> weightUnitComboBox;          //	体重单位	WeightUnit		
	@FXML
	private TextArea specialRearReqText;      //	特殊饲养要求	SpecialRearReq		
	@FXML
	private TextField intendRoomText;          //	拟占用房间	IntendRoom		
	@FXML
	private TextArea remarkText;  				//备注
	@FXML
	private ListView<String> studyNoListView;     //课题编号列表
	private ObservableList<String> data_studyNoListView = FXCollections.observableArrayList();
	@FXML
	private TextField studyNoText;
	@FXML
	private TextField numMaleText2;
	@FXML
	private TextField numFemaleText2;
	
	
	
	private ToggleGroup group = new ToggleGroup();
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		instance = this;
		//单选按钮置为一组
		groupAllRadio();
		
		//初始化动物种类与动物品系下拉框
		initAnimalTypeAndStrainComboBox();
		//初始化课题编号列表框
		initStudyNoListView();
	}
	
	/**单选按钮置为一组*/
	private void groupAllRadio(){
		animalLevelA.setToggleGroup(group);
		animalLevelB.setToggleGroup(group);
		animalLevelC.setToggleGroup(group);
		animalLevelD.setToggleGroup(group);
	}
	/**初始化动物种类与动物品系下拉框*/
	private void initAnimalTypeAndStrainComboBox(){
		List<DictAnimalType> dictAnimalTypeList = 
				BaseService.getDictAnimalTypeService().findAllOrderByOrderNo();
		if(null != dictAnimalTypeList && dictAnimalTypeList.size()>0){
			for(DictAnimalType obj:dictAnimalTypeList){
				data_animalTypeComboBox.add(obj.getTypeName());
				animalTypeNameIdMap.put(obj.getTypeName(), obj.getId());
			}
		}
		animalTypeComboBox.setItems(data_animalTypeComboBox);
		animalStrainComboBox.setItems(data_animalStrainComboBox);
		animalTypeComboBox.getSelectionModel().selectedItemProperty()//
		.addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				animalStrainComboBox.getSelectionModel().clearSelection();
				data_animalStrainComboBox.clear();
				if(null!=newValue && !"".equals(newValue)){
					String id= animalTypeNameIdMap.get(newValue);
					if(null!=id && !"".equals(id)){
						List<DictAnimalStrain> dictAnimalStrainList = 
								BaseService.getDictAnimalStrainService().findByTypeId(id);
						if(null != dictAnimalStrainList && dictAnimalStrainList.size()>0){
							for(DictAnimalStrain obj:dictAnimalStrainList){
								data_animalStrainComboBox.add(obj.getStrainName());
							}
						}
					}
				}
				
			}
			
		});
		
	}
	/**初始化课题编号列表框*/
	private void initStudyNoListView() {
		studyNoListView.setItems(data_studyNoListView);
	}
	/**
	 * ADD 按钮事件（+）
	 * 
	 * @param event
	 */
	@FXML
	private void onAddAction(ActionEvent event) {

		String studyNo = studyNoText.getText().toString().trim();
		if ("".equals(studyNo)) {// 文本内无数据 则不添加（直接返回）
			return;
		}
		if(studyNo.getBytes().length>20){
			Alert2.create("课题编号长度不能超过20");
			return;
		}
		String numMaleStr = numMaleText2.getText().toString().trim();
		if(null == numMaleStr || "".equals(numMaleStr)){
			numMaleText2.requestFocus();
			return ;
		}
		if(!numMaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雄性动物数量请输入正整数");
			numMaleText2.requestFocus();
			return ;
		}
		String numFemaleStr = numFemaleText2.getText().toString().trim();
		if(null == numFemaleStr || "".equals(numFemaleStr)){
			numFemaleText2.requestFocus();
			return ;
		}
		if(!numFemaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雄性动物数量请输入正整数");
			numFemaleText2.requestFocus();
			return ;
		}
		
		if(null!=data_studyNoListView &&data_studyNoListView.size()>0){
			for(String obj :data_studyNoListView){
				if(obj.contains(studyNo)){
					Alert2.create("课题编号重复");
					return;
				}
			}
		}
		boolean isExist = BaseService.getTblQRRequestStudyNoService().isExistByStudyNo(studyNo);
		if(isExist){
			Alert2.create("课题编号已存在");
			return;
		}
		data_studyNoListView.add(studyNo+"♂"+numMaleStr+"♀"+numFemaleStr);
		studyNoText.setText("");
		numMaleText2.clear();
		numFemaleText2.clear();
		studyNoText.requestFocus();
	}
	
	/**
	 * DEL按钮事件（-）
	 * 
	 * @param event
	 */
	@FXML
	private void onDelAction(ActionEvent event) {
		String selected= studyNoListView.getSelectionModel().getSelectedItem();
		if(null!=selected && !"".equals(selected)){
			
			data_studyNoListView.remove(selected);
			studyNoListView.getSelectionModel().clearSelection();
			
		}else{
			Alert2.create("请先选择要删除的数据");
			return;
		}
	}
	/***
	 * 确认签字
	 * @param event
	 */
	@FXML
	private void onConfrimSignAction(ActionEvent event){
		//检查通过
		if(checkAllData()){
			//电子签名
			Stage stage = new Stage();
			SignFrame signFrame = new SignFrame();
			try {
				signFrame.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if("OK".equals(SignFrame.getType())){
				//如果签字通过，保存
				String iacucCode = iacucCodeText.getText().toString().trim();
				int studyPlanFinished =0;
				if(studyPlanFinishedCheckBox.isSelected()){
					studyPlanFinished =1;
				}
				
				String animalType =animalTypeComboBox.getSelectionModel().getSelectedItem();
				String animalStrain = animalStrainComboBox.getSelectionModel().getSelectedItem();
				String animalLevel =((RadioButton)group.getSelectedToggle()).getId();
				if(animalLevel.equals("animalLevelA")){
					animalLevel="普通级";
				}else if(animalLevel.equals("animalLevelB")){
					animalLevel="清洁级";
				}else if(animalLevel.equals("animalLevelC")){
					animalLevel="SPF级";
				}else{
					animalLevel="无菌级";
				}
				List<Map<String,String>> studyNoMapList = new ArrayList<Map<String,String>>();
				Map<String,String> map = null;
				for(String str:data_studyNoListView){
					map = new HashMap<String,String>();
					int index  = str.indexOf("♂");
					int index1 = str.indexOf("♀");
					map.put("studyNo", str.substring(0, index));
					map.put("numMale",str.substring(index+1, index1));
					map.put("numFemale",str.substring(index1+1)	);			
					studyNoMapList.add(map);
				}
				
				String numMaleStr = numMaleText.getText().toString().trim();
				String numFemaleStr = numFemaleText.getText().toString().trim();
				int numMale = Integer.valueOf(numMaleStr);
				int numFemale = Integer.valueOf(numFemaleStr);
				String weightMaleUStr = weightMaleUText.getText().toString().trim();
				String weightMaleLStr = weightMaleLText.getText().toString().trim();
				String weightFemaleUStr = weightFemaleUText.getText().toString().trim();
				String weightFemaleLStr = weightFemaleLText.getText().toString().trim();
				float weightMaleU = Float.valueOf(weightMaleUStr);
				float weightMaleL = Float.valueOf(weightMaleLStr);
				float weightFemaleU = Float.valueOf(weightFemaleUStr);
				float weightFemaleL = Float.valueOf(weightFemaleLStr);
				String weightUnit = weightUnitComboBox.getSelectionModel().getSelectedItem();
				String ageMaleUStr = ageMaleUText.getText().toString().trim();
				String ageMaleLStr = ageMaleLText.getText().toString().trim();
				String ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
				String ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
				float ageMaleU = Float.valueOf(ageMaleUStr);
				float ageMaleL = Float.valueOf(ageMaleLStr);
				float ageFemaleU = Float.valueOf(ageFemaleUStr);
				float ageFemaleL = Float.valueOf(ageFemaleLStr);
				String ageUnit = ageUnitComboBox.getSelectionModel().getSelectedItem();
				
				String intendRoom = intendRoomText.getText().toString().trim();
				String specialRearReq = specialRearReqText.getText().toString().trim();
				String remark = remarkText.getText().toString().trim();
				
				TblQRRequest tblQRRequest = new TblQRRequest();
				tblQRRequest.setIacucCode(iacucCode);
				tblQRRequest.setStudyPlanFinished(studyPlanFinished);
				tblQRRequest.setAnimalType(animalType);
				tblQRRequest.setAnimalStrain(animalStrain);
				tblQRRequest.setAnimalLevel(animalLevel);
				tblQRRequest.setNumMale(numMale);
				tblQRRequest.setNumFemale(numFemale);
				tblQRRequest.setWeightMaleU(weightMaleU);
				tblQRRequest.setWeightMaleL(weightMaleL);
				tblQRRequest.setWeightFemaleU(weightFemaleU);
				tblQRRequest.setWeightFemaleL(weightFemaleL);
				tblQRRequest.setWeightUnit(weightUnit);
				tblQRRequest.setAgeMaleU(ageMaleU);
				tblQRRequest.setAgeMaleL(ageMaleL);
				tblQRRequest.setAgeFemaleU(ageFemaleU);
				tblQRRequest.setAgeFemaleL(ageFemaleL);
				tblQRRequest.setAgeUnit(ageUnit);
				tblQRRequest.setSpecialRearReq(specialRearReq);
				tblQRRequest.setIntendRoom(intendRoom);
				tblQRRequest.setRemark(remark);
				
				User user = SignFrame.getUser();
				tblQRRequest.setApplicant(
						user !=null ? user.getRealName():"");
				
//			tblQRRequest.setApplyDate(new Date());//保存时取服务器时间
//			tblQRRequest.setSubmitTime(new Date());//保存时取服务器时间
			String qrRId =	BaseService.getTblQRRequestService().saveOneselfAndStudyNoMapList(tblQRRequest,studyNoMapList);
			
				//保存日志
				 TblLog tblLog = new TblLog();
				  tblLog.setSystemName(SystemMessage.getSystemName());//系统名称
				  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
				  tblLog.setOperatType("提交");
				  tblLog.setOperatOject("检疫，需求申请");
				  tblLog.setOperator(tblQRRequest.getApplicant());
				  tblLog.setOperatContent("申请单号："+qrRId);
				  tblLog.setOperatHost(SystemTool.getIPAddress());
				 BaseService.getTblLogService().save(tblLog);
				
				//关闭当前窗口
				((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
				RequestPaneController.getInstance().loadData();
			}
		}
	}
	
	/***
	 * 取消
	 * @param event
	 */
	@FXML
	private void onCancelAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	
	/**
	 * 检查所有数据
	 */
	private boolean checkAllData(){
		String iacucCode = iacucCodeText.getText().toString().trim();
		if ("".equals(iacucCode)) {// 文本内无数据 则不添加（直接返回）
			Alert2.create("IACUC编号不能为空");
			iacucCodeText.requestFocus();
			return false;
		}
		if(iacucCode.getBytes().length>40){
			Alert2.create("IACUC编号长度不能超过40");
			iacucCodeText.requestFocus();
			return false;
		}
		
		int studyNosLength = data_studyNoListView.size();
		if(studyNosLength<1){
			Alert2.create("课题编号列表不能为空");
			studyNoText.requestFocus();
			return false;
		}
		if(null == animalTypeComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择动物种类");
			animalTypeComboBox.requestFocus();
			return false;
		}
		if(null == animalStrainComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择动物品系");
			animalStrainComboBox.requestFocus();
			return false;
		}
		if(null == group.getSelectedToggle()){
			Alert2.create("请选择动物级别");
			animalLevelA.requestFocus();
			return false;
		}
		
		String numMaleStr = numMaleText.getText().toString().trim();
		if(null == numMaleStr || "".equals(numMaleStr)){
			Alert2.create("雄性动物数量不能为空");
			numMaleText.requestFocus();
			return false;
		}
		if(!numMaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雄性动物数量请输入正整数");
			numMaleText.requestFocus();
			return false;
		}
		String numFemaleStr = numFemaleText.getText().toString().trim();
		if(null == numFemaleStr || "".equals(numFemaleStr)){
			Alert2.create("雌性动物数量不能为空");
			numFemaleText.requestFocus();
			return false;
		}
		if(!numFemaleStr.matches("[1-9]{1}[0-9]{0,9}")){
			Alert2.create("雌性动物数量请输入正整数");
			numFemaleText.requestFocus();
			return false;
		}
		
		int numMale = Integer.valueOf(numMaleStr);
		int numFemale = Integer.valueOf(numFemaleStr);
		int numMales = 0;
		int numFemales = 0;
		for(String str:data_studyNoListView){
			int index  = str.indexOf("♂");
			int index1 = str.indexOf("♀");
			if(index>-1 && index1>-1 && index<index1){
				numMales =numMales+Integer.valueOf(str.substring(index+1, index1));
				numFemales =numFemales+Integer.valueOf(str.substring(index1+1));
			}else{
				Alert2.create("课题编号雌雄数量格式不对");
				return false;
			}
			
			if(numMales>numMale){
				Alert2.create("雄性动物数量不能低于课题列表对应雄性动物数量总和");
				numMaleText.requestFocus();
				return false;
			}
			if(numFemales>numFemale){
				Alert2.create("雌性动物数量不能低于课题编号列表对应雌性动物数量总和");
				numFemaleText.requestFocus();
				return false;
			}
		}
		
		
		String weightMaleUStr = weightMaleUText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(weightMaleUStr)){
			Alert2.create("请正确填写体重范围");
			weightMaleUText.requestFocus();
			return false;
		}
		String weightMaleLStr = weightMaleLText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(weightMaleLStr)){
			Alert2.create("请正确填写体重范围");
			weightMaleLText.requestFocus();
			return false;
		}
		if(Float.valueOf(weightMaleUStr) > Float.valueOf(weightMaleLStr)){
			Alert2.create("体重范围前项不能大于后项");
			weightMaleUText.requestFocus();
			return false;
		}
		String weightFemaleUStr = weightFemaleUText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(weightFemaleUStr)){
			Alert2.create("请正确填写体重范围");
			weightFemaleUText.requestFocus();
			return false;
		}
		String weightFemaleLStr = weightFemaleLText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(weightFemaleLStr)){
			Alert2.create("请正确填写体重范围");
			weightFemaleLText.requestFocus();
			return false;
		}
		
		if(Float.valueOf(weightFemaleUStr) > Float.valueOf(weightFemaleLStr)){
			Alert2.create("体重范围前项不能大于后项");
			weightFemaleUText.requestFocus();
			return false;
		}
		if(null == weightUnitComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择体重单位");
			weightUnitComboBox.requestFocus();
			return false;
		}
		String ageMaleUStr = ageMaleUText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(ageMaleUStr)){
			Alert2.create("请正确填写年龄范围");
			ageMaleUText.requestFocus();
			return false;
		}
		String ageMaleLStr = ageMaleLText.getText().toString().trim();
		if(!NumberValidationUtils.isPositiveRealNumber(ageMaleLStr)){
			Alert2.create("请正确填写年龄范围");
			ageMaleLText.requestFocus();
			return false;
		}
		if(Float.valueOf(ageMaleUStr) > Float.valueOf(ageMaleLStr)){
			Alert2.create("年龄范围前项不能大于后项");
			ageMaleUText.requestFocus();
			return false;
		}
		String ageFemaleUStr = ageFemaleUText.getText().toString().trim(); 
		if(!NumberValidationUtils.isPositiveRealNumber(ageFemaleUStr)){
			Alert2.create("请正确填写年龄范围");
			ageFemaleUText.requestFocus();
			return false;
		}
		String ageFemaleLStr = ageFemaleLText.getText().toString().trim(); 
		if(!NumberValidationUtils.isPositiveRealNumber(ageFemaleLStr)){
			Alert2.create("请正确填写年龄范围");
			ageFemaleLText.requestFocus();
			return false;
		}
		if(Float.valueOf(ageFemaleUStr) > Float.valueOf(ageFemaleLStr)){
			Alert2.create("年龄范围前项不能大于后项");
			ageFemaleUText.requestFocus();
			return false;
		}
		if(null == ageUnitComboBox.getSelectionModel().getSelectedItem()){
			Alert2.create("请选择年龄单位");
			ageUnitComboBox.requestFocus();
			return false;
		}
		String intendRoom = intendRoomText.getText().toString().trim();
		if(intendRoom.getBytes().length>60){
			Alert2.create("拟占用房间长度不能超过60");
			intendRoomText.requestFocus();
			return false;
		}
		String specialRearReq = specialRearReqText.getText().toString().trim();
		if(specialRearReq.getBytes().length>400){
			Alert2.create("特殊饲养要求长度不能超过400");
			specialRearReqText.requestFocus();
			return false;
		}
		String remark = remarkText.getText().toString().trim();
		if(remark.getBytes().length>200){
			Alert2.create("备注长度不能超过200");
			remarkText.requestFocus();
			return false;
		}
		
		return true;
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("RegisterRequestFrame.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("需求申请");
		stage.setResizable(false);
		stage.showAndWait();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
