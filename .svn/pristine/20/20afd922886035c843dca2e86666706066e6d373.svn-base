package com.lanen.view.clinicaltest;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.DictInstrument;
import com.lanen.model.clinicaltest.TblLog;
import com.lanen.model.clinicaltest.TblPassageway;
import com.lanen.model.studyplan.DictBioChem;
import com.lanen.model.studyplan.DictBloodCoag;
import com.lanen.model.studyplan.DictHemat;
import com.lanen.model.studyplan.DictUrine;
import com.lanen.util.SystemMessage;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.SignFrame;

public class PassagewayFrameController implements Initializable {
	@FXML
	private Label label;
	@FXML
	private ComboBox<String> instrumentIdComboBox;
	private ObservableList<String> data= FXCollections.observableArrayList();
	private Map<String,DictInstrument> map =new HashMap<String,DictInstrument>();
	@FXML
	private TextField instrumentNameText;
	@FXML
	private TextField passagewayText;
	@FXML
	private Button addBtn;
	@FXML
	private Button delBtn;
	@FXML
	private ListView<String> list;
	private ObservableList<String> data_list=FXCollections.observableArrayList();
	@FXML
	private ListView<String> currentList;//具体哪个指标的通道号列表
	private ObservableList<String> data_currentList=FXCollections.observableArrayList();
	private Map<String,Object> map_list=new HashMap<String,Object>();
	//vBox 里  文本框的个数
	@SuppressWarnings("unused")
	private int length=0;
	//
	private int testItem=0;
	//数据操作次数
	private int opearatTimes =0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		opearatTimes=0;
		initComboBox();
		
		//初始化  列表框
		initList();
		currentList.setItems(data_currentList);
	}

	/**
	 * 初始化  列表框
	 */
	private void initList() {
		list.setItems(data_list);
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				length=0;
				//vBox.getChildren().clear();
				data_currentList.clear();
				passagewayText.setText("");
				if(null!=newValue&&!"".equals(newValue)){
					addBtn.setDisable(false);
					delBtn.setDisable(false);
					passagewayText.setEditable(true);
					
					switch (testItem) {
					case 1:
						DictBioChem entity= (DictBioChem) map_list.get(newValue);
						List<TblPassageway> list=BaseService.getPassagewayService().getByTestItem(testItem,entity.getAbbr(),instrumentIdComboBox.getSelectionModel().getSelectedItem());
						if(null!=list&&list.size()>0){
							for(TblPassageway obj:list){
								data_currentList.add(obj.getPassageway());
								length++;
							}
						}
						break;
					case 2:
						DictHemat entity1= (DictHemat) map_list.get(newValue);
						List<TblPassageway> list1=BaseService.getPassagewayService().getByTestItem(testItem,entity1.getAbbr(),instrumentIdComboBox.getSelectionModel().getSelectedItem());
						if(null!=list1&&list1.size()>0){
							for(TblPassageway obj:list1){
								data_currentList.add(obj.getPassageway());
								length++;
							}
						}
						break;
					case 3:
						DictBloodCoag entity2= (DictBloodCoag) map_list.get(newValue);
						List<TblPassageway> list2=BaseService.getPassagewayService().getByTestItem(testItem,entity2.getAbbr(),instrumentIdComboBox.getSelectionModel().getSelectedItem());
						if(null!=list2&&list2.size()>0){
							for(TblPassageway obj:list2){
								data_currentList.add(obj.getPassageway());
								length++;
							}
						}
						break;
					case 4:
						DictUrine entity3= (DictUrine) map_list.get(newValue);
						List<TblPassageway> list3=BaseService.getPassagewayService().getByTestItem(testItem,entity3.getAbbr(),instrumentIdComboBox.getSelectionModel().getSelectedItem());
						if(null!=list3&&list3.size()>0){
							for(TblPassageway obj:list3){
								data_currentList.add(obj.getPassageway());
								length++;
							}
						}
						break;

					default:
						break;
					}
				}else{
					
					addBtn.setDisable(true);
					delBtn.setDisable(true);
					passagewayText.setEditable(false);
				}
			}});
	}

	/**
	 * 更新  列表框 值
	 * @param testItem
	 */
	private void updateList(int testItem){
		this.testItem=testItem;
		switch (testItem) {
		case 1:
			label.setText("生化检验设备");
			List<DictBioChem> list =BaseService.getDictBioChemService().getAll();
			map_list.clear();
			data_list.clear();
			if(null!=list&&list.size()>0){
				for(DictBioChem obj:list){
					map_list.put(obj.getName()+"("+obj.getAbbr()+")", obj);
					data_list.add(obj.getName()+"("+obj.getAbbr()+")");
				}
			}
			break;
		case 2:
			label.setText("血液检验设备");
			List<DictHemat> list1 =BaseService.getDictHematService().getAll();
			map_list.clear();
			data_list.clear();
			if(null!=list1&&list1.size()>0){
				for(DictHemat obj:list1){
					map_list.put(obj.getName()+"("+obj.getAbbr()+")", obj);
					data_list.add(obj.getName()+"("+obj.getAbbr()+")");
				}
			}
			break;
		case 3:
			label.setText("血凝检验设备");
			List<DictBloodCoag> list11 =BaseService.getDictBloodCoagService().getAll();
			map_list.clear();
			data_list.clear();
			if(null!=list11&&list11.size()>0){
				for(DictBloodCoag obj:list11){
					map_list.put(obj.getName()+"("+obj.getAbbr()+")", obj);
					data_list.add(obj.getName()+"("+obj.getAbbr()+")");
				}
			}
			break;
		case 4:
			label.setText("尿液检验设备");
			List<DictUrine> list111 =BaseService.getDictUrineService().getAll();
			map_list.clear();
			data_list.clear();
			if(null!=list111&&list111.size()>0){
				for(DictUrine obj:list111){
					map_list.put(obj.getName()+"("+obj.getAbbr()+")", obj);
					data_list.add(obj.getName()+"("+obj.getAbbr()+")");
				}
			}
			break;

		default:
			break;
		}
	}
	/**
	 * 初始化下拉框
	 */
	private void initComboBox() {
		List<DictInstrument> list=BaseService.getDictInstrumentService().getAll();
		if(null!=list&&list.size()>0){
			for(DictInstrument obj:list){
				data.add(obj.getInstrumentId());
				map.put(obj.getInstrumentId(), obj);
			}
		}
		instrumentIdComboBox.setItems(data);
		instrumentIdComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue&&!"".equals(newValue)){
					DictInstrument dictInsturment=map.get(newValue);
					instrumentNameText.setText(dictInsturment.getInstrumentName());
					updateList(dictInsturment.getInstrumentType());
					length=0;
					//vBox.getChildren().clear();
					data_currentList.clear();
				}
			}});
		if(data.size()>0){
			instrumentIdComboBox.getSelectionModel().select(0);
		}
	}

//	/**
//	 * 应用按钮事件
//	 * @param event
//	 */
//	@FXML
//	private void onConfirmAction(ActionEvent event){
//		String instrumentId=instrumentIdComboBox.getSelectionModel().getSelectedItem();
//		if(null==instrumentId||instrumentId.equals("")){
//			Alert.create("请选择先选择设备");
//			return;
//		}
//		String selectedItem=list.getSelectionModel().getSelectedItem();
//		if(null==selectedItem||selectedItem.equals("")){
//			Alert.create("请选择检测指标");
//			return;
//		}
//		String passagewayName=passagewayText.getText();
//		if("".equals(passagewayName)||passagewayName.length()>10){
//			Alert.create("请填写通道号");
//			return;
//		}
//		List<String> strList=new ArrayList<String>();    //改指标 通道号
//		strList.add(passagewayName);
//		if(length>0){
//			for(int i=0;i<length;i++){
////				String text=((TextField)vBox.getChildren().get(i)).getText();
////				if(!"".equals(text)&&text.length()<11){
////					strList.add(text);
////				}
//			}
//		}
//		
//		String abbr="";
//		switch (testItem) {
//		case 1:
//			DictBioChem entity= (DictBioChem) map_list.get(selectedItem);
//			abbr=entity.getAbbr();
//			break;
//		case 2:
//			DictHemat entity1= (DictHemat) map_list.get(selectedItem);
//			abbr=entity1.getAbbr();
//			break;
//		case 3:
//			DictBloodCoag entity11= (DictBloodCoag) map_list.get(selectedItem);
//			abbr=entity11.getAbbr();
//			break;
//		case 4:
//			DictUrine entity111= (DictUrine) map_list.get(selectedItem);
//			abbr=entity111.getAbbr();
//			break;
//
//		default:
//			break;
//		}
//		 //该检测项目、该设置下   非该指标   的通道号是否存在
////		if(BaseService.getPassagewayService().isExist(testItem,instrumentId,abbr,strList)){
////			Alert.create("通道号已经被其他指标占用");
////			return;
////		}
//		//保存
//		BaseService.getPassagewayService().saveAll(testItem,instrumentId,abbr,strList);
//		
//		Alert.create("保存成功");
//	}
	/**
	 * ADD 按钮事件
	 * @param event
	 */
	@FXML
	private void onAddAction(ActionEvent event){
		String instrumentId=instrumentIdComboBox.getSelectionModel().getSelectedItem();
		if(null==instrumentId||instrumentId.equals("")){
//			Alert2.create("请选择先选择设备");
			Messager.showWarnMessage("请选择先选择设备！");
			return;
		}
		String selectedItem=list.getSelectionModel().getSelectedItem();
		if(null==selectedItem||selectedItem.equals("")){
//			Alert2.create("请选择检测指标");
			Messager.showWarnMessage("请选择检测指标！");
			return;
		}
		String passagewayName=passagewayText.getText().toString().trim();
		if("".equals(passagewayName)||passagewayName.length()>10){
//			Alert2.create("请填写通道号");
			Messager.showWarnMessage("请填写通道号！");
			return;
		}
		 //该检测项目、该设备下   该通道号是否存在
		if(BaseService.getPassagewayService().isExist(testItem,instrumentId,passagewayName)){
//			Alert2.create("通道号已存在");
			Messager.showWarnMessage("通道号已存在！");
			return;
		}
		String abbr="";
		switch (testItem) {
		case 1:
			DictBioChem entity= (DictBioChem) map_list.get(selectedItem);
			abbr=entity.getAbbr();
			break;
		case 2:
			DictHemat entity1= (DictHemat) map_list.get(selectedItem);
			abbr=entity1.getAbbr();
			break;
		case 3:
			DictBloodCoag entity11= (DictBloodCoag) map_list.get(selectedItem);
			abbr=entity11.getAbbr();
			break;
		case 4:
			DictUrine entity111= (DictUrine) map_list.get(selectedItem);
			abbr=entity111.getAbbr();
			break;

		default:
			break;
		}
		//保存
		BaseService.getPassagewayService().save(testItem,instrumentId,abbr,passagewayName);
		//
		data_currentList.add(passagewayName);
		//文本框置空、获得焦点
		passagewayText.setText("");
		passagewayText.requestFocus();
		 if(opearatTimes==0){
			  //记录设备登记日志
			  TblLog tblLog = new TblLog();
			  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
			  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
			  tblLog.setOperatType("数据操作");
			  tblLog.setOperatOject("通道号设置");
//			  User user = SignFrame.getUser();
			  User user = SignFrame.getInstance().getSignUser();
			  if(null!=user){
				  tblLog.setOperator(user.getRealName());
			  }
			  tblLog.setOperatContent("");
			  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
			  BaseService.getTblLogService().save(tblLog);
			  opearatTimes++;
		  }
		
	}
	/**
	 * DEL按钮事件
	 * @param event
	 */
	@FXML
	private void onDelAction(ActionEvent event){
		String selected= currentList.getSelectionModel().getSelectedItem();
		if(null!=selected && !"".equals(selected)){
			if(data_currentList.remove(selected)){
				BaseService.getPassagewayService().deleteByPassageway(selected);
				//清除选定
				currentList.getSelectionModel().clearSelection();
				
				 if(opearatTimes==0){
					  //记录设备登记日志
					  TblLog tblLog = new TblLog();
					  tblLog.setSystemName(SystemMessage.getSystemName());//系统全称
					  tblLog.setSystemVersion(SystemMessage.getSystemVersion());//系统版本
					  tblLog.setOperatType("数据操作");
					  tblLog.setOperatOject("通道号设置");
//					  User user = SignFrame.getUser();
					  User user = SignFrame.getInstance().getSignUser();
					  if(null!=user){
						  tblLog.setOperator(user.getRealName());
					  }
					  tblLog.setOperatContent("");
					  tblLog.setOperatHost(SystemTool.getHostName()+","+SystemTool.getIPAddress());
					  BaseService.getTblLogService().save(tblLog);
					  opearatTimes++;
				  }
			}
		}else{
//			Alert2.create("请先选择删除项");
			Messager.showWarnMessage("请先选择删除项！");
		}
	}
	
	
}
