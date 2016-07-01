package com.lanen.view.clinicaltest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.Specimen;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TblSpecimen;
import com.lanen.model.studyplan.TblClinicalTestReq;
import com.lanen.model.studyplan.TblClinicalTestReqIndex2;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.Sign;
import com.lanen.zero.main.MainFrame;

public class TblSpecimenNumberFrameController extends Application implements Initializable {
	
	@FXML
	private TableView<Specimen> table;
	private ObservableList<Specimen> data=FXCollections.observableArrayList();
	@FXML
	private TableColumn<Specimen,String> studyPlanNoCol;
	@FXML
	private TableColumn<Specimen,String> reqNoCol;
	@FXML
	private TableColumn<Specimen,String> animalIdCol;
	@FXML
	private TableColumn<Specimen,String> animalCodeCol;
	@FXML
	private TableColumn<Specimen,String> recDateCol;
	@FXML
	private TableColumn<Specimen,String> recTimeCol;
	@FXML
	private TableColumn<Specimen,String> testItemCol;
	@FXML
	private TableColumn<Specimen,String> specimenCodeCol;
	@FXML
	private TableColumn<Specimen,String> specimenKindCol;
	
	private boolean isPass =false;//已确认
	
	private String sender = ""; 
	@FXML
	private Label senderLabel;//送检人
	@FXML
	private Label receiverLabel;//接收人
	
	
	@FXML
	private Button confirmBtn;
	
	private static TblSpecimenNumberFrameController instance;
	public static TblSpecimenNumberFrameController getInstance(){
		if(null == instance){
			instance = new TblSpecimenNumberFrameController();
		}
		return instance;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		initTable();
		
	}

	/**
	 * 加载数据
	 */
	public void loadData(String sender,ObservableList<Specimen> data_number){
		senderLabel.setText(sender);
		receiverLabel.setText("");
		updateData(data_number);
		isPass = false;
		this.sender = sender;
	}
	/**
	 * 更新表格的值
	 */
	private void updateData(ObservableList<Specimen> data_number) {
		data.clear();
		confirmBtn.setDisable(true);
		data=data_number;
		if(null!=data&&data.size()>0){
			confirmBtn.setDisable(false);
		}
		table.setItems(data);
	}

	/**
	 * 初始化表
	 */
	private void initTable() {
		studyPlanNoCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("studyPlanNo"));
		reqNoCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("reqNo"));
		animalIdCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("animalId"));
		animalCodeCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("animalCode"));
		recDateCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("recDate"));
		recTimeCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("recTime"));
		testItemCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("testItem"));
		specimenCodeCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("specimenCode"));
		specimenKindCol.setCellValueFactory(new PropertyValueFactory<Specimen,String>("specimenKind"));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setItems(data);
	}
	
	//确定按钮事件
	@FXML
	public void onConfirmBtnAction(ActionEvent event){
		
		if(isPass){
//			Alert2.create("已确认签字，不要重复确认");
			Messager.showWarnMessage("已确认签字，不要重复确认！");
			return;
		}
//		SignFrame signFrame = new SignFrame("");
//		Stage stage = new Stage();
//		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setTitle("签字确认");
//		try {
//			signFrame.start(stage);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
		User signUser = Sign.openSign("签字确认", "");
		//签字通过
		if(null != signUser){
			//记录标本接收日志——操作内容
			String operatContent="";
			
			List<TblSpecimen> list=new ArrayList<TblSpecimen>();
			TblStudyPlan tblStudyPlan=null;
			TblClinicalTestReq tblClinicalTestReq=null;
			TblClinicalTestReqIndex2 tblClinicalTestReqIndex2=null;
			List<TblClinicalTestReqIndex2> list2=null;
			for(Specimen obj:data){
				if(null==tblStudyPlan){
					tblStudyPlan=new TblStudyPlan();
					tblStudyPlan.setStudyNo(obj.getStudyPlanNo());
				}
				if(null==tblClinicalTestReq){
					tblClinicalTestReq=BaseService.getTblClinicalTestReqService().findByStudyNoAndReqNO(obj.getStudyPlanNo(), Integer.parseInt(obj.getReqNo()));
				    //java.util.Set<TblClinicalTestReqIndex2> set =tblClinicalTestReq.getTblClinicalTestReqIndex2s();
					//
//				    if(null!=set&&set.size()>0){
//				    	list2 = new ArrayList<TblClinicalTestReqIndex2>(set);
//				    }
					//懒加载问题
					list2 = BaseService.getTblClinicalTestReqService().getTblClinicalTestReqIndex2ListByTblClinicalTestReq(tblClinicalTestReq);
					
				}
				for(TblClinicalTestReqIndex2 index2:list2){
					if(index2.getAnimalId().equals(obj.getAnimalId())){
						tblClinicalTestReqIndex2=index2;
						break;
					}
				}
				TblSpecimen tblSpecimen =new TblSpecimen();
				tblSpecimen.setTblStudyPlan(tblStudyPlan);//试验计划
				tblSpecimen.setReqNo(Integer.parseInt(obj.getReqNo()));
				tblSpecimen.setTblClinicalTestReq(tblClinicalTestReq);//临检申请
				tblSpecimen.setTblClinicalTestReqIndex2(tblClinicalTestReqIndex2);//临检申请单-动物编号
				tblSpecimen.setSpecimenCode(obj.getSpecimenCode());//检验编号
				tblSpecimen.setAnimalId(obj.getAnimalId());//动物ID
				tblSpecimen.setAnimalCode(obj.getAnimalCode());//动物编号
				tblSpecimen.setSpecimenKind(obj.getSpecimenKind());//标本类型
				tblSpecimen.setAniSerialNum(tblClinicalTestReqIndex2.getAniSerialNum());
				if("生化检验".equals(obj.getTestItem())){
					tblSpecimen.setTestItem(1);
				}else if("血液检验".equals(obj.getTestItem())){
					tblSpecimen.setTestItem(2);
				}else if("血凝检验".equals(obj.getTestItem())){
					tblSpecimen.setTestItem(3);
				}else if("尿液检验".equals(obj.getTestItem())){
					tblSpecimen.setTestItem(4);
				}
				list.add(tblSpecimen);
				operatContent=operatContent+obj.getStudyPlanNo()+":"+obj.getReqNo()+" "+obj.getAnimalId()+" "+obj.getTestItem()+obj.getSpecimenCode()+" ,";
			}
//			BaseService.getTblSpecimenService().saveList(list,SignFrame.getUser().getRealName());
			//多了个送检人签字记录
			BaseService.getTblSpecimenService().saveList(list,signUser.getRealName(),sender);
			
//			((javafx.scene.control.Control)event.getSource()).getParent().getScene().getWindow().hide();
			
			
			//记录标本接收日志
			  TblLog tblLog = new TblLog();
			  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			  tblLog.setOperatType("数据操作");
			  tblLog.setOperatOject("标本接收");
			  User user = signUser;
			  if(null!=user){
				  tblLog.setOperator(user.getRealName());
			  }
			  while(operatContent.getBytes().length>990){
				  operatContent=operatContent.substring(0,operatContent.length()-100);
			  }
			  operatContent=operatContent+"...";
			  tblLog.setOperatContent(operatContent);
			  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			  BaseService.getTblLogService().save(tblLog);
			   //已确认签字
			  isPass =true;
				confirmBtn.setDisable(true);
				receiverLabel.setText(signUser.getRealName());
//				Alert.create("签字确认成功");
				Messager.showMessage("签字确认成功！");
		}else{//签字未通过
			
		}
	}
	//退出按钮事件
	@FXML
	public void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getParent().getScene().getWindow().hide();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TblSpecimenNumberFrame.fxml"));
		Scene scene = new Scene(root,780,565);
		stage.setScene(scene);
		stage.initOwner(MainFrame.mainWidow);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/clinicaltest/logo_tuopan.png")));
		stage.setTitle("检验编号确认");
		stage.setMinWidth(760);
		stage.setMinHeight(545);
//		stage.setResizable(false);
//		stage.showAndWait();
		
	}
	public boolean isPass() {
		return isPass;
	}


	
	
}
