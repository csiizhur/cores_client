package com.lanen.view.quarantine;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.quarantine.DictAnimalDeath;
import com.lanen.model.quarantine.DictGeneralObservation;
import com.lanen.model.quarantine.DictOutputSettings;
import com.lanen.model.quarantine.DictPhysicalExamItem;
import com.lanen.model.quarantine.TblAnimalHouse;
import com.lanen.model.quarantine.TblBal;
import com.lanen.model.quarantine.TblHostBal;
import com.lanen.model.studyplan.DictAnimalType;
import com.lanen.model.tableview.DictAnimalDeathForTableView;
import com.lanen.model.tableview.DictOutputSettingsForTableView;
import com.lanen.model.tableview.DictPhysicalExamItemForTableView;
import com.lanen.model.tableview.TblAnimalHouseForTableView;
import com.lanen.model.tableview.TblBalForTableView;
import com.lanen.model.tableview.TblHostBalForTableView;
import com.lanen.util.SystemTool;
import com.lanen.util.popup.Alert;
import com.lanen.util.popup.Alert2;
import com.lanen.util.popup.Confirm;
import com.lanen.util.popup.Confirm2;
import com.lanen.view.main.Main;

public class SystemSetController implements Initializable {

	@FXML
	private TextField itemTypeText;      //项目
	@FXML
	private TextField itemNameText;      //所见观察
	@FXML
	private TextField observationCodeText;//观察所见编号
	@FXML
	private TextArea remarkText;      	//备注
	@FXML
	private Button upButton;      
	@FXML
	private Button downButton;
	@FXML
	private TreeView<String> treeView;  //一般观察树
	private TreeItem<String> rootNode_treeView=new TreeItem<String>("一般观察树");
	private List<String> nodeNameList;    //节点名称列表（非叶子节点）
	
	// ----- tab  2
	@FXML
	private RadioButton itemTypeRadioButtonA_2;      //
	@FXML
	private RadioButton itemTypeRadioButtonB_2;      //
	private ToggleGroup group = new ToggleGroup();
	@FXML
	private TextField itemNameText_2;      //
	@FXML
	private TextArea remarkText_2;      	//备注
	@FXML
	private Button upButton_2;      
	@FXML
	private Button downButton_2;
	@FXML
	private TableView<DictPhysicalExamItemForTableView> dictPhysicalExamItemTable ;
	private static ObservableList<DictPhysicalExamItemForTableView> data_dictPhysicalExamItemTable =
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<DictPhysicalExamItemForTableView,String> itemTypeCol;
	@FXML
	private TableColumn<DictPhysicalExamItemForTableView,String> itemNameCol;
	@FXML
	private TableColumn<DictPhysicalExamItemForTableView,String> remarkCol;
	
	//----tab 3
	@FXML
	private TextField deadTypeText;
	@FXML
	private TextArea deadRsnText;
	@FXML
	private Button upButton_3;
	@FXML
	private Button downButton_3;
	@FXML
	private TableView<DictAnimalDeathForTableView> dictAnimalDeathTable;
	private ObservableList<DictAnimalDeathForTableView> data_dictAnimalDeathTable = 
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<DictAnimalDeathForTableView,String> deadTypeCol;
	@FXML
	private TableColumn<DictAnimalDeathForTableView,String> deadRsnCol;
	
	//----tab 4
	@FXML
	private TextField roomText;    //房间号
	@FXML
	private TextField floorText;   //楼层
	@FXML
	private TextField buildingText;//建筑名称
	@FXML
	private ListView<CheckBox> animalTypeListView;
	private ObservableList<CheckBox> data_animalTypeListView = 
			FXCollections.observableArrayList();
	@FXML
	private TableView<TblAnimalHouseForTableView> tblAnimalHouseTable;
	private static ObservableList<TblAnimalHouseForTableView> data_tblAnimalHouseTable =
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblAnimalHouseForTableView,String> roomCol;
	@FXML
	private TableColumn<TblAnimalHouseForTableView,String> floorCol;
	@FXML
	private TableColumn<TblAnimalHouseForTableView,String> buildingCol;
	@FXML
	private TableColumn<TblAnimalHouseForTableView,String> animalTypeCol;
	
	@FXML
	private Button upButton_4;
	@FXML
	private Button downButton_4;
	
	
	//----tab 5
	@FXML
	private TextField balIdText;    //天平Id号
	@FXML
	private TextField balDescText;   //设备名称
	@FXML
	private ComboBox<String> commEnableComboBox;//是否自动采集    
//	@FXML
//	private ComboBox<String> comPortComboBox;           //接入串口名称
	@FXML
	private ComboBox<String> baudRateComboBox;             //波特率
	@FXML
	private ComboBox<String> dataBitComboBox;              //数据位
	@FXML
	private ComboBox<String> stopBitComboBox;              //停止位
	@FXML
	private ComboBox<String> checkModeComboBox;           //校验位
	
	@FXML
	private Button upButton_5;
	@FXML
	private Button downButton_5;
	@FXML
	private TableView<TblBalForTableView> tblBalTable;
	private ObservableList<TblBalForTableView> data_tblBalTable = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn<TblBalForTableView,String> balIdCol;    //天平Id
	@FXML
	private TableColumn<TblBalForTableView,String> balDescCol;				// 设备描述
	@FXML
	private TableColumn<TblBalForTableView,String> commEnableCol;              //是否自动采集
	@FXML
	private TableColumn<TblBalForTableView,String> baudRateCol;             //波特率   
	@FXML
	private TableColumn<TblBalForTableView,String> dataBitCol;              //数据位   5 6 7 8
	@FXML
	private TableColumn<TblBalForTableView,String> stopBitCol;              //停止位    1,1.5,2
	@FXML
	private TableColumn<TblBalForTableView,String> checkModeCol;            //校验位    None，Even，Odd，Space，Mark 
	
	//----tab 52
	@FXML
	private ComboBox<String> balIdComboBox;
	private ObservableList<String> data_balIdComboBox = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> comPortComboBox;
	@FXML
	private ComboBox<String> enableComboBox;
	
	@FXML
	private TableView<TblHostBalForTableView> tblHostBalTable;
	private ObservableList<TblHostBalForTableView> data_tblHostBalTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<TblHostBalForTableView,String> balIdCol_2;
	@FXML
	private TableColumn<TblHostBalForTableView,String> comPortCol_2;
	@FXML
	private TableColumn<TblHostBalForTableView,String> enableCol_2;
	
	//----tab 6
	@FXML
	private TableView<DictOutputSettingsForTableView> dictOutputSettingsTable;
	private ObservableList<DictOutputSettingsForTableView> data_dictOutputSettingsTable=
			FXCollections.observableArrayList();
	private ObservableList<DictOutputSettingsForTableView> copyData_dictOutputSettingsTable=
			FXCollections.observableArrayList();
	@FXML
	private TableColumn<DictOutputSettingsForTableView,String> labelCol;
	@FXML
	private TableColumn<DictOutputSettingsForTableView,String> showCol;
	
	private String hostName;    //当前计算机名
	
	private static SystemSetController instance;
	public static SystemSetController getInstance(){
		return instance;
	}
	@Override
	public void initialize(URL url, ResourceBundle sb) {
		instance = this;
		//初始化一般观察树并加载数据
		initTreeView();
		
		//tab2  动物体检类容
		//单选项置为一组
		itemTypeRadioButtonA_2.setToggleGroup(group);
		itemTypeRadioButtonB_2.setToggleGroup(group);
		//初始化 动物体检内容表格
		initDictPhysicalExamItemTable();
		
		//tab3  动物死亡原因
		//初始化 动物死亡原因表格  及加载数据
		initDictAnimalDeathTable();
		
		//tab4 动物安置房间
		//初始化动物种类listView 及数据
		initAnimalTypeListView();
		//初始化动物安置房间Table及数据
		initTblAnimalHouse();
		
		//tab 5 称重设备设置
		//初始化天平Table及数据
		initTblBalTable();
		//改变 4  ComboBox状态
		change4ComboBoxDisable(true);
		//初始化  是否采集下拉框
		initCommEnableComboBox();
		
		//tab 52
		
		//当前计算机主机名
		hostName = SystemTool.getHostName();
		balIdComboBox.setItems(data_balIdComboBox);
		//更新 balIdComboBox数据
		updateBalIdComboBox();
		//初始化连接天平表格tblHostBalTable及其数据
		initTblHostBalTable();
		
		//tab6
		//初始化界面输出设置表格及其数据
		initDictOutputSettingsTable();
	}
	
	/**
	 * 初始化一般观察树并加载数据
	 */
	private void initTreeView() {
		treeView.setRoot(rootNode_treeView);
		rootNode_treeView.setExpanded(true);
		List<DictGeneralObservation> nodeList = 
				BaseService.getDictGeneralObservationService().getNodeList();
		if(null!=nodeList && nodeList.size()>0){
			nodeNameList = new ArrayList<String>();
			for(DictGeneralObservation obj:nodeList){
				String nodeName = obj.getItemType();
				nodeNameList.add(nodeName);
				TreeItem<String> node = new TreeItem<String>(nodeName);
				node.setExpanded(true);//父节点展开
				rootNode_treeView.getChildren().add(node);
				List<DictGeneralObservation> leafList =
						BaseService.getDictGeneralObservationService().getLeafList(nodeName);
				if(null!=leafList && leafList.size()>0){
					for(DictGeneralObservation entity:leafList){
						TreeItem<String> leaf = new TreeItem<String>(entity.getItemName()+"("+entity.getObservationCode()+")");
						node.getChildren().add(leaf);
					}
				}
			}
		}
		
		treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> ob,
					TreeItem<String> oldValue, TreeItem<String> newValue) {
				if (null != newValue) {
					//非叶子节点
					if(nodeNameList.contains(newValue.getValue())){
						itemTypeText.setText(newValue.getValue());
						
						if(rootNode_treeView.getChildren().get(0).equals(newValue)){
							//第一个，不移动
							upButton.setDisable(true);
							if(rootNode_treeView.getChildren().size() ==1 ){
								downButton.setDisable(true);
							}else{
								downButton.setDisable(false);
							}
						}else 	if(rootNode_treeView.getChildren().lastIndexOf(newValue)==(rootNode_treeView.getChildren().size()-1)){
							//最后一个，不移动
							upButton.setDisable(false);
							downButton.setDisable(true);
						}else{
							upButton.setDisable(false);
							downButton.setDisable(false);
						}
						
					}else{
						//叶子节点
						itemTypeText.setText(newValue.getParent().getValue());
						TreeItem<String> parentNode = newValue.getParent();
						if(parentNode.getChildren().get(0).equals(newValue)){
							//第一个，不移动
							upButton.setDisable(true);
							if(parentNode.getChildren().size() ==1 ){
								downButton.setDisable(true);
							}else{
								downButton.setDisable(false);
							}
						}else 	if(parentNode.getChildren().lastIndexOf(newValue)==(parentNode.getChildren().size()-1)){
							//最后一个，不移动
							upButton.setDisable(false);
							downButton.setDisable(true);
						}else{
							upButton.setDisable(false);
							downButton.setDisable(false);
						}
					}
				}else{
					itemTypeText.setText("");
					
					upButton.setDisable(true);
					downButton.setDisable(true);
				}
				
			}});
	}
	/**
	 * 向右   添加
	 * @param event
	 */
	@FXML
	private void onRightButton(ActionEvent event){
		String itemType =itemTypeText.getText().toString().trim();
		if("".equals(itemType)){
			Alert2.create("‘项目’不能为空");
			itemTypeText.requestFocus();
			return;
		}
		if(itemType.getBytes().length>60){
			Alert2.create("'项目'长度不能大于60");
			itemTypeText.requestFocus();
			return;
		}
		String itemName =itemNameText.getText().trim();
		if("".equals(itemName)){
			Alert2.create("‘观察所见’不能为空");
			itemNameText.requestFocus();
			return;
		}
		if(itemName.getBytes().length>60){
			Alert2.create("'观察所见'长度不能大于60");
			itemNameText.requestFocus();
			return;
		}
		boolean isExist = true;
		isExist = BaseService.getDictGeneralObservationService().isExistByItemName(itemName);
		if(isExist){
			Alert2.create("观察所见‘"+itemName+"’已存在");
			itemNameText.requestFocus();
			return;
		}
		String observationCode =observationCodeText.getText().trim();
		if("".equals(observationCode)){
			Alert2.create("‘观察所见编号’不能为空");
			observationCodeText.requestFocus();
			return;
		}
		if(observationCode.getBytes().length>20){
			Alert2.create("'观察所见编号'长度不能大于20");
			observationCodeText.requestFocus();
			return;
		}
		boolean isExistCode = true;
		isExistCode = BaseService.getDictGeneralObservationService().isExistByObservationCode(observationCode);
		if(isExistCode){
			Alert2.create("观察所见编号‘"+observationCode+"’已存在");
			observationCodeText.requestFocus();
			return;
		}
		String remark =remarkText.getText().trim();
		if(remark.getBytes().length>200){
			Alert2.create("'备注'长度不能大于200");
			remarkText.requestFocus();
			return;
		}
		DictGeneralObservation dictGeneralObservation = new DictGeneralObservation();
		dictGeneralObservation.setItemType(itemType);
		dictGeneralObservation.setItemName(itemName);
		dictGeneralObservation.setObservationCode(observationCode);
		dictGeneralObservation.setRemark(remark);
		BaseService.getDictGeneralObservationService().saveNodeOrLeaf(dictGeneralObservation);
		
		//前台增加数据
		if(nodeNameList.contains(itemType)){
			List<TreeItem<String>> nodeList = rootNode_treeView.getChildren();
			if(null!=nodeList && nodeList.size()>0){
				for(TreeItem<String> node:nodeList){
					if(node.getValue().equals(itemType)){
						TreeItem<String> currentLeaf= new TreeItem<String>(itemName+"("+observationCode+")");
						node.getChildren().add(currentLeaf);
						treeView.getSelectionModel().select(currentLeaf);
					}
				}
			}
		}else{
			nodeNameList.add(itemType);
			TreeItem<String> node= new TreeItem<String>(itemType);
			rootNode_treeView.getChildren().add(node);
			TreeItem<String> currentLeaf= new TreeItem<String>(itemName+"("+observationCode+")");
			node.getChildren().add(currentLeaf);
			treeView.getSelectionModel().select(currentLeaf);
		}
		
		itemNameText.setText("");
		observationCodeText.setText("");
		remarkText.setText("");
	}
	/**
	 * 向左
	 * @param event
	 */
	@FXML
	private void onLeftButton(ActionEvent event){
		if(null!=treeView.getSelectionModel().getSelectedItem()){
			TreeItem<String> selectNode = treeView.getSelectionModel().getSelectedItem();
			if(nodeNameList.contains(selectNode.getValue())){//选择的是父节点
				if(Confirm.create(Main.getInstance().getStage(), "提示",
						"删除‘"+selectNode.getValue()+"'节点,将同时删除其所有子节点项",
						"确定要删除吗？")){
					
					BaseService.getDictGeneralObservationService().deleteByItemType(selectNode.getValue());
					treeView.getRoot().getChildren().remove(selectNode);
					treeView.getSelectionModel().clearSelection();
					
					nodeNameList.remove(selectNode.getValue());
					itemTypeText.setText(selectNode.getValue());
					itemNameText.setText("");
					observationCodeText.setText("");
				}
			}else{//选择的是叶子节点
				if(Confirm2.create(Main.getInstance().getStage(),
						"确定要删除'"+selectNode.getValue()+"'吗？")){
					String itemType = selectNode.getParent().getValue();
					int size = selectNode.getParent().getChildren().size();
					if(size>1){
						String itemName = selectNode.getValue();
						int index0 = itemName.indexOf("(");
						itemName = itemName.substring(0,index0);
						BaseService.getDictGeneralObservationService().deleteByItemName(itemName);
						selectNode.getParent().getChildren().remove(selectNode);
					}else{
						BaseService.getDictGeneralObservationService().deleteByItemType(itemType);
						treeView.getRoot().getChildren().remove(selectNode.getParent());
						nodeNameList.remove(itemType);
					}
					treeView.getSelectionModel().clearSelection();
					String itemName = selectNode.getValue();
					int index0 = itemName.indexOf("(");
					int index1 = itemName.indexOf(")");
					String observattionCode  ="";
					if(index0+1 < index1){
						observattionCode= itemName.substring(index0+1,index1);
					}
					itemName = itemName.substring(0,index0);
					itemNameText.setText(itemName);
					observationCodeText.setText(observattionCode);
					itemTypeText.setText(itemType);
				}
			}
			
		}else{
			Alert2.create("请先选择删除项");
			return ;
		}
	}
	/**
	 * 向上
	 * @param event
	 */
	@FXML
	private void onUpButton(ActionEvent event ){
		if(null!=treeView.getSelectionModel().getSelectedItem()){
			TreeItem<String> selectNode = treeView.getSelectionModel().getSelectedItem();
			if(selectNode.isLeaf()){//叶子节点
				TreeItem<String> parentNode = selectNode.getParent();
				if(parentNode.getChildren().get(0).equals(selectNode)){
					//第一个，不移动
				}else{
//					String currentName = selectNode.getValue();
					String currentName = selectNode.getValue();
					int index0 = currentName.indexOf("(");
					currentName = currentName.substring(0,index0);
					BaseService.getDictGeneralObservationService().upOrderNoByItemName(currentName);
					
					List<TreeItem<String>> leafList = parentNode.getChildren();
					if(null!=leafList && leafList.size()>1){
						int i=0;
						for(TreeItem<String> leaf:leafList){
							if(leaf.equals(selectNode)){
								String itemName= leaf.getValue();
								leaf.setValue(parentNode.getChildren().get(i-1).getValue());
								parentNode.getChildren().get(i-1).setValue(itemName);
								treeView.getSelectionModel().select(parentNode.getChildren().get(i-1));
								break;
							}
							i++;
						}
					}
				}
				
			}else{//父节点
				if(rootNode_treeView.getChildren().get(0).equals(selectNode)){
					//第一个，不移动
				}else{
					String currentType = selectNode.getValue();
					BaseService.getDictGeneralObservationService().upOrderNoByItemType(currentType);
					
					List<TreeItem<String>> nodeList = rootNode_treeView.getChildren();
					if(null!=nodeList && nodeList.size()>1){
						int i=0;
						for(TreeItem<String> node:nodeList){
							if(node.equals(selectNode)){
								TreeItem<String> newNode = new TreeItem<String>(node.getValue());
								newNode.setExpanded(true);
								List<TreeItem<String>> leafList = node.getChildren();
								TreeItem<String> newLeaf =null;
								for(TreeItem<String> leaf:leafList){
									newLeaf = new TreeItem<String>(leaf.getValue());
									newNode.getChildren().add(newLeaf);
								}
								rootNode_treeView.getChildren().add(i-1,newNode);
								rootNode_treeView.getChildren().remove(i+1);
								treeView.getSelectionModel().select(newNode);
								break;
							}
							i++;
						}
					}
				}
			}
		}else{
			Alert2.create("请先选择待移动项");
			return ;
		}
	}
	/**
	 * 向下
	 * @param event
	 */
	@FXML
	private void onDownButton(ActionEvent event ){
		if(null!=treeView.getSelectionModel().getSelectedItem()){
			TreeItem<String> selectNode = treeView.getSelectionModel().getSelectedItem();
			if(selectNode.isLeaf()){//叶子节点
				TreeItem<String> parentNode = selectNode.getParent();
				if(parentNode.getChildren().lastIndexOf(selectNode)==(parentNode.getChildren().size()-1)){
					//最后一个，不移动
				}else{
					String currentName = selectNode.getValue();
					int index0 = currentName.indexOf("(");
					currentName = currentName.substring(0,index0);
					BaseService.getDictGeneralObservationService().downOrderNoByItemName(currentName);
					
					List<TreeItem<String>> leafList = parentNode.getChildren();
					if(null!=leafList && leafList.size()>1){
						int i=0;
						for(TreeItem<String> leaf:leafList){
							if(leaf.equals(selectNode)){
								String itemName= leaf.getValue();
								leaf.setValue(parentNode.getChildren().get(i+1).getValue());
								parentNode.getChildren().get(i+1).setValue(itemName);
								treeView.getSelectionModel().select(parentNode.getChildren().get(i+1));
								break;
							}
							i++;
						}
					}
				}
				
			}else{//父节点
				if(rootNode_treeView.getChildren().lastIndexOf(selectNode)==(rootNode_treeView.getChildren().size()-1)){
					//最后一个，不移动
				}else{
					String currentType = selectNode.getValue();
					BaseService.getDictGeneralObservationService().downOrderNoByItemType(currentType);
					
					List<TreeItem<String>> nodeList = rootNode_treeView.getChildren();
					if(null!=nodeList && nodeList.size()>1){
						int i=0;
						for(TreeItem<String> node:nodeList){
							if(node.equals(selectNode)){
								TreeItem<String> newNode = new TreeItem<String>(node.getValue());
								newNode.setExpanded(true);
								List<TreeItem<String>> leafList = node.getChildren();
								TreeItem<String> newLeaf =null;
								for(TreeItem<String> leaf:leafList){
									newLeaf = new TreeItem<String>(leaf.getValue());
									newNode.getChildren().add(newLeaf);
								}
								rootNode_treeView.getChildren().add(i+2,newNode);
								rootNode_treeView.getChildren().remove(i);
								treeView.getSelectionModel().select(newNode);
								break;
							}
							i++;
						}
					}
				}
			}
		}else{
			Alert2.create("请先选择待移动项");
			return ;
		}
	}
	
	//-----------tab2
	
	/**
	 * 初始化 动物体检 表格
	 */
	private void initDictPhysicalExamItemTable() {
		itemTypeCol.setCellValueFactory(new PropertyValueFactory<DictPhysicalExamItemForTableView,String>("itemType")); 
		itemNameCol.setCellValueFactory(new PropertyValueFactory<DictPhysicalExamItemForTableView,String>("itemName")); 
		remarkCol.setCellValueFactory(new PropertyValueFactory<DictPhysicalExamItemForTableView,String>("remark")); 
		itemTypeCol.setCellFactory(new Callback<TableColumn<DictPhysicalExamItemForTableView,String>
		,TableCell<DictPhysicalExamItemForTableView,String>>(){

			@Override
			public TableCell<DictPhysicalExamItemForTableView, String> call(
					TableColumn<DictPhysicalExamItemForTableView, String> p) {
					TableCell<DictPhysicalExamItemForTableView, String> cell = 
							new TableCell<DictPhysicalExamItemForTableView, String>() {
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

			}});
		data_dictPhysicalExamItemTable.clear();
		dictPhysicalExamItemTable.setItems(data_dictPhysicalExamItemTable);
		dictPhysicalExamItemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		List<DictPhysicalExamItem> list = BaseService.getDictPhysicalExamItemService().findAllOrderByOrderNo();
		if(null != list && list.size()>0){
			for(DictPhysicalExamItem obj:list){
				data_dictPhysicalExamItemTable.add(new DictPhysicalExamItemForTableView(
						obj.getId(),obj.getItemType(),obj.getItemName(),obj.getRemark(),obj.getOrderNo()));
			}
		}
		dictPhysicalExamItemTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<DictPhysicalExamItemForTableView>(){
			@Override
			public void changed(ObservableValue<? extends DictPhysicalExamItemForTableView> arg0,
					DictPhysicalExamItemForTableView arg1, DictPhysicalExamItemForTableView newValue) {
				if(null!=newValue){
					if(data_dictPhysicalExamItemTable.get(0).equals(newValue)){
						//第一个
						upButton_2.setDisable(true);
						if(data_dictPhysicalExamItemTable.size()==1){
							downButton_2.setDisable(true);
						}else{
							downButton_2.setDisable(false);
						}
					}else if(data_dictPhysicalExamItemTable.lastIndexOf(newValue)== (data_dictPhysicalExamItemTable.size()-1)){
						upButton_2.setDisable(false);
						downButton_2.setDisable(true);
					}else{
						upButton_2.setDisable(false);
						downButton_2.setDisable(false);
					}
				}else{
					
					//按钮不可用
					upButton_2.setDisable(true);
					downButton_2.setDisable(true);
				}
				
			}});
	}
	/**
	 * 向右   添加
	 * @param event
	 */
	@FXML
	private void onRightButton_2(ActionEvent event){
		String itemType ="";
		String itemName ="";
		String remark="";
		if(itemTypeRadioButtonA_2.isSelected()){
			itemType="A";
		}else if(itemTypeRadioButtonB_2.isSelected()){
			itemType="B";
		}else{
			Alert2.create("请选择’类别‘");
			itemTypeRadioButtonA_2.requestFocus();
			return ;
		}
		itemName= itemNameText_2.getText().trim();
		if("".equals(itemName)){
			Alert2.create("请填写‘检查项目’");
			itemNameText_2.requestFocus();
			return ;
		}
		if(itemName.getBytes().length>60){
			Alert2.create("‘检查项目’长度不能大于60");
			itemNameText_2.requestFocus();
			return ;
		}
		boolean isExist = BaseService.getDictPhysicalExamItemService().isExistByItemName(itemName);
		if(isExist){
			Alert2.create("‘检查项目’"+itemName+"已存在");
			itemNameText_2.requestFocus();
			return ;
		}
		remark= remarkText_2.getText().trim();
		if("".equals(remark)){
			Alert2.create("请填写‘观察方法’");
			remarkText_2.requestFocus();
			return ;
		}
		if(remark.getBytes().length>200){
			Alert2.create("‘观察方法’长度不能大于200");
			remarkText_2.requestFocus();
			return ;
		}
		
		DictPhysicalExamItem dictPhysicalExamItem = new DictPhysicalExamItem();
		dictPhysicalExamItem.setItemType(itemType);
		dictPhysicalExamItem.setItemName(itemName);
		dictPhysicalExamItem.setRemark(remark);
		
		int orderNo = BaseService.getDictPhysicalExamItemService().saveAndReturnOrderNo(dictPhysicalExamItem);
		
		DictPhysicalExamItemForTableView dictPhysicalExamItemForTableView = new DictPhysicalExamItemForTableView(
				"",itemType,itemName,remark,orderNo);
		data_dictPhysicalExamItemTable.add(dictPhysicalExamItemForTableView);
		dictPhysicalExamItemTable.getSelectionModel().select(dictPhysicalExamItemForTableView);
		
		itemTypeRadioButtonA_2.setSelected(false);
		itemTypeRadioButtonB_2.setSelected(false);
		itemNameText_2.clear();
		remarkText_2.clear();
	}
	/**
	 * 向左删除
	 * @param event
	 */
	@FXML
	private void onLeftButton_2(ActionEvent event){
		if(null!=dictPhysicalExamItemTable.getSelectionModel().getSelectedItem()){
			DictPhysicalExamItemForTableView selectedItem =
					dictPhysicalExamItemTable.getSelectionModel().getSelectedItem();
			if(Confirm2.create(Main.getInstance().getStage(), 
					"确定要删除‘"+selectedItem.getItemName()+"'吗？")){
				BaseService.getDictPhysicalExamItemService().deleteByOrderNo(selectedItem.getOrderNo());	
				data_dictPhysicalExamItemTable.remove(selectedItem);
				dictPhysicalExamItemTable.getSelectionModel().clearSelection();
				
				if("A".equals(selectedItem.getItemType())){
					itemTypeRadioButtonA_2.setSelected(true);
				}else{
					itemTypeRadioButtonB_2.setSelected(true);
				}
				itemNameText_2.setText(selectedItem.getItemName());
				remarkText_2.setText(selectedItem.getRemark());
				
					}
		}else{
			Alert2.create("请先选择待删除项");
		}
	}
	/**
	 * 向上移动
	 * @param event
	 */
	@FXML
	private void onUpButton_2(ActionEvent event){
		if(null!=dictPhysicalExamItemTable.getSelectionModel().getSelectedItem()){
			DictPhysicalExamItemForTableView selectedItem = 
					dictPhysicalExamItemTable.getSelectionModel().getSelectedItem();
			if(!data_dictPhysicalExamItemTable.get(0).equals(selectedItem)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_dictPhysicalExamItemTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_dictPhysicalExamItemTable.get(i-1).getOrderNo();
				
				DictPhysicalExamItemForTableView nextItem = data_dictPhysicalExamItemTable.get(i-1);
				
				DictPhysicalExamItemForTableView newItem =new DictPhysicalExamItemForTableView();
				newItem.setItemType(selectedItem.getItemType());
				newItem.setItemName(selectedItem.getItemName());
				newItem.setRemark(selectedItem.getRemark());
				newItem.setOrderNo(nextOrderNo);
				
				nextItem.setOrderNo(currentOrderNo);
				
				data_dictPhysicalExamItemTable.add(i-1,newItem);
				data_dictPhysicalExamItemTable.remove(i+1);
				
				dictPhysicalExamItemTable.getSelectionModel().select(i-1);
				BaseService.getDictPhysicalExamItemService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	/**
	 * 向下移动
	 * @param event
	 */
	@FXML
	private void onDownButton_2(ActionEvent event){
		if(null!=dictPhysicalExamItemTable.getSelectionModel().getSelectedItem()){
			DictPhysicalExamItemForTableView selectedItem = 
					dictPhysicalExamItemTable.getSelectionModel().getSelectedItem();
			if(data_dictPhysicalExamItemTable.lastIndexOf(selectedItem) !=(data_dictPhysicalExamItemTable.size()-1)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_dictPhysicalExamItemTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_dictPhysicalExamItemTable.get(i+1).getOrderNo();
				
				DictPhysicalExamItemForTableView nextItem = data_dictPhysicalExamItemTable.get(i+1);
				DictPhysicalExamItemForTableView newItem =new DictPhysicalExamItemForTableView();
				newItem.setItemType(nextItem.getItemType());
				newItem.setItemName(nextItem.getItemName());
				newItem.setRemark(nextItem.getRemark());
				newItem.setOrderNo(currentOrderNo);
				
				selectedItem.setOrderNo(nextOrderNo);
				dictPhysicalExamItemTable.getSelectionModel().clearSelection();
				data_dictPhysicalExamItemTable.add(i,newItem);
				data_dictPhysicalExamItemTable.remove(i+2);
				
				dictPhysicalExamItemTable.getSelectionModel().clearAndSelect(i+1);
				BaseService.getDictPhysicalExamItemService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	//-----tab3
	
	/**
	 * 初始化 动物死亡原因表格  及加载数据
	 */
	private void initDictAnimalDeathTable() {
		// 
		deadTypeCol.setCellValueFactory(new PropertyValueFactory<DictAnimalDeathForTableView,String>("deadType")); 
		deadRsnCol.setCellValueFactory(new PropertyValueFactory<DictAnimalDeathForTableView,String>("deadRsn")); 
		data_dictAnimalDeathTable.clear();
		dictAnimalDeathTable.setItems(data_dictAnimalDeathTable);
		
		dictAnimalDeathTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		List<DictAnimalDeath> list = BaseService.getDictAnimalDeathService().findAllOrderByOrderNo();
		if(null != list && list.size()>0){
			for(DictAnimalDeath obj:list){
				data_dictAnimalDeathTable.add(new DictAnimalDeathForTableView(
						obj.getDeadType(),obj.getDeadRsn(),obj.getOrderNo()));
			}
		}
		dictAnimalDeathTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<DictAnimalDeathForTableView>(){
			@Override
			public void changed(ObservableValue<? extends DictAnimalDeathForTableView> arg0,
					DictAnimalDeathForTableView arg1, DictAnimalDeathForTableView newValue) {
				if(null!=newValue){
					if(data_dictAnimalDeathTable.get(0).equals(newValue)){
						//第一个
						upButton_3.setDisable(true);
						if(data_dictAnimalDeathTable.size()==1){
							downButton_3.setDisable(true);
						}else{
							downButton_3.setDisable(false);
						}
					}else if(data_dictAnimalDeathTable.lastIndexOf(newValue)== (data_dictAnimalDeathTable.size()-1)){
						upButton_3.setDisable(false);
						downButton_3.setDisable(true);
					}else{
						upButton_3.setDisable(false);
						downButton_3.setDisable(false);
					}
				}else{
					
					//按钮不可用
					upButton_3.setDisable(true);
					downButton_3.setDisable(true);
				}
				
			}});
	}
	/**
	 * 向右，添加
	 * @param event
	 */
	@FXML
	private void onRightButton_3(ActionEvent event){
		String deadType ="";
		String deadRsn ="";
		
		deadType= deadTypeText.getText().trim();
		if("".equals(deadType)){
			Alert2.create("请填写‘死亡原因’");
			deadTypeText.requestFocus();
			return ;
		}
		if(deadType.getBytes().length>20){
			Alert2.create("‘死亡原因’长度不能大于20");
			deadTypeText.requestFocus();
			return ;
		}
		boolean isExist = BaseService.getDictAnimalDeathService().isExistByDeadType(deadType);
		if(isExist){
			Alert2.create("‘死亡原因’"+deadType+"已存在");
			deadTypeText.requestFocus();
			return ;
		}
		deadRsn= deadRsnText.getText().trim();
		if("".equals(deadRsn)){
			Alert2.create("请填写‘死亡描述’");
			deadRsnText.requestFocus();
			return ;
		}
		if(deadRsn.getBytes().length>50){
			Alert2.create("‘死亡描述’长度不能大于50");
			deadRsnText.requestFocus();
			return ;
		}
		
		DictAnimalDeath dictAnimalDeath = new DictAnimalDeath();
		dictAnimalDeath.setDeadType(deadType);
		dictAnimalDeath.setDeadRsn(deadRsn);
		
		int orderNo = BaseService.getDictAnimalDeathService().saveAndReturnOrderNo(dictAnimalDeath);
		
		DictAnimalDeathForTableView dictAnimalDeathForTableView = new DictAnimalDeathForTableView(
				deadType,deadRsn,orderNo);
		data_dictAnimalDeathTable.add(dictAnimalDeathForTableView);
		dictAnimalDeathTable.getSelectionModel().select(dictAnimalDeathForTableView);
		
		deadTypeText.clear();
		deadRsnText.clear();
	}
	/**
	 * 向左，删除
	 * @param event
	 */
	@FXML
	private void onLeftButton_3(ActionEvent event){
		if(null!=dictAnimalDeathTable.getSelectionModel().getSelectedItem()){
			DictAnimalDeathForTableView selectedItem =
					dictAnimalDeathTable.getSelectionModel().getSelectedItem();
			if(Confirm2.create(Main.getInstance().getStage(), 
					"确定要删除‘"+selectedItem.getDeadType()+"'吗？")){
				BaseService.getDictAnimalDeathService().deleteByOrderNo(selectedItem.getOrderNo());	
				data_dictAnimalDeathTable.remove(selectedItem);
				dictAnimalDeathTable.getSelectionModel().clearSelection();
				
				
				deadTypeText.setText(selectedItem.getDeadType());
				deadRsnText.setText(selectedItem.getDeadRsn());
				
			}
		}else{
			Alert2.create("请先选择待删除项");
		}
	}
	/**
	 * 向上，向上移动
	 * @param event
	 */
	@FXML
	private void onUpButton_3(ActionEvent event){
		if(null!=dictAnimalDeathTable.getSelectionModel().getSelectedItem()){
			DictAnimalDeathForTableView selectedItem = 
					dictAnimalDeathTable.getSelectionModel().getSelectedItem();
			if(!data_dictAnimalDeathTable.get(0).equals(selectedItem)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_dictAnimalDeathTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_dictAnimalDeathTable.get(i-1).getOrderNo();
				
				DictAnimalDeathForTableView nextItem = data_dictAnimalDeathTable.get(i-1);
				
				DictAnimalDeathForTableView newItem =new DictAnimalDeathForTableView();
				newItem.setDeadType(selectedItem.getDeadType());
				newItem.setDeadRsn(selectedItem.getDeadRsn());
				newItem.setOrderNo(nextOrderNo);
				
				nextItem.setOrderNo(currentOrderNo);
				
				data_dictAnimalDeathTable.add(i-1,newItem);
				data_dictAnimalDeathTable.remove(i+1);
				
				dictAnimalDeathTable.getSelectionModel().select(i-1);
				BaseService.getDictAnimalDeathService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	/**
	 * 向下，向下移动
	 * @param event
	 */
	@FXML
	private void onDownButton_3(ActionEvent event){
		if(null!=dictAnimalDeathTable.getSelectionModel().getSelectedItem()){
			DictAnimalDeathForTableView selectedItem = 
					dictAnimalDeathTable.getSelectionModel().getSelectedItem();
			if(data_dictAnimalDeathTable.lastIndexOf(selectedItem) !=(data_dictAnimalDeathTable.size()-1)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_dictAnimalDeathTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_dictAnimalDeathTable.get(i+1).getOrderNo();
				
				DictAnimalDeathForTableView nextItem = data_dictAnimalDeathTable.get(i+1);
				DictAnimalDeathForTableView newItem =new DictAnimalDeathForTableView();
				newItem.setDeadType(nextItem.getDeadType());
				newItem.setDeadRsn(nextItem.getDeadRsn());
				newItem.setOrderNo(currentOrderNo);
				
				selectedItem.setOrderNo(nextOrderNo);
				dictAnimalDeathTable.getSelectionModel().clearSelection();
				
				data_dictAnimalDeathTable.add(i,newItem);
				data_dictAnimalDeathTable.remove(i+2);
				
				dictAnimalDeathTable.getSelectionModel().clearAndSelect(i+1);
				
				BaseService.getDictAnimalDeathService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	//-----------tab4
	
	/**
	 * 初始化动物listView 及数据
	 */
	private void initAnimalTypeListView() {
		data_animalTypeListView.clear();
		animalTypeListView.setItems(data_animalTypeListView);
		animalTypeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>(){
			@Override
			public void changed(ObservableValue<? extends CheckBox> ob, CheckBox arg1,
					CheckBox newValue) {
				if(null!=newValue){
					if(newValue.isSelected()){
						newValue.setSelected(false);
					}else{
						newValue.setSelected(true);
					}
					animalTypeListView.getSelectionModel().clearSelection();
				}
				
			}
		});
		List<DictAnimalType> dictAnimalTypeList = 
				BaseService.getDictAnimalTypeService().findAllOrderByOrderNo();
		if(null != dictAnimalTypeList && dictAnimalTypeList.size()>0){
			for(DictAnimalType obj:dictAnimalTypeList){
				data_animalTypeListView.add(new CheckBox(obj.getTypeName()));
			}
		}
		
	}
	/**
	 * 初始化动物安置房间Table及数据
	 */
	private void initTblAnimalHouse() {
		data_tblAnimalHouseTable.clear();
		roomCol.setCellValueFactory(new PropertyValueFactory<TblAnimalHouseForTableView,String>("room"));
		floorCol.setCellValueFactory(new PropertyValueFactory<TblAnimalHouseForTableView,String>("floor"));
		buildingCol.setCellValueFactory(new PropertyValueFactory<TblAnimalHouseForTableView,String>("building"));
		animalTypeCol.setCellValueFactory(new PropertyValueFactory<TblAnimalHouseForTableView,String>("animalType"));
		
		tblAnimalHouseTable.setItems(data_tblAnimalHouseTable);
		tblAnimalHouseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		List<Map<String,String>> list = BaseService.getTblAnimalHouseService().findListOrderbyOrderNo();
		if(null != list && list.size()>0){
			for(Map<String,String> map:list){
				data_tblAnimalHouseTable.add(new TblAnimalHouseForTableView(
						map.get("room"),map.get("floor"),map.get("building"),
						map.get("animalType"),Integer.parseInt(map.get("orderNo"))));
			}
		}
		
		tblAnimalHouseTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<TblAnimalHouseForTableView>(){
			@Override
			public void changed(ObservableValue<? extends TblAnimalHouseForTableView> arg0,
					TblAnimalHouseForTableView arg1, TblAnimalHouseForTableView newValue) {
				if(null!=newValue){
					if(data_tblAnimalHouseTable.get(0).equals(newValue)){
						//第一个
						upButton_4.setDisable(true);
						if(data_tblAnimalHouseTable.size()==1){
							downButton_4.setDisable(true);
						}else{
							downButton_4.setDisable(false);
						}
					}else if(data_tblAnimalHouseTable.lastIndexOf(newValue)== (data_tblAnimalHouseTable.size()-1)){
						upButton_4.setDisable(false);
						downButton_4.setDisable(true);
					}else{
						upButton_4.setDisable(false);
						downButton_4.setDisable(false);
					}
				}else{
					
					//按钮不可用
					upButton_4.setDisable(true);
					downButton_4.setDisable(true);
				}
				
			}});
	}
	/**
	 * 向右，添加
	 * @param event
	 */
	@FXML
	private void onRightButton_4(ActionEvent event ){
		String room ="";
		String floor="";
		String building="";
		String animalType="";
		
		room = roomText.getText().trim();
		if("".equals(room)){
			Alert2.create("请填写‘房间号’");
			roomText.requestFocus();
			return ;
		}
		if(room.getBytes().length>20){
			Alert2.create("‘房间号’长度不能超过20");
			roomText.requestFocus();
			return ;
		}
		boolean isExist = BaseService.getTblAnimalHouseService().isExistByRoom(room);
		if(isExist){
			Alert2.create("‘房间号’"+room+" 已存在");
			roomText.requestFocus();
			return ;
		}
		
		floor =floorText.getText().trim();
		if("".equals(floor)){
			Alert2.create("请填写‘楼层’");
			floorText.requestFocus();
			return ;
		}
		if(floor.getBytes().length>20){
			Alert2.create("‘楼层’长度不能超过20");
			floorText.requestFocus();
			return ;
		}
		
		building = buildingText.getText().trim();
		if("".equals(building)){
			Alert2.create("请填写‘建筑名称’");
			buildingText.requestFocus();
			return ;
		}
		if(building.getBytes().length>50){
			Alert2.create("‘建筑名称’长度不能超过50");
			buildingText.requestFocus();
			return ;
		}
		
		for(CheckBox checkBox:data_animalTypeListView){
			if(checkBox.isSelected()){
				animalType=animalType+checkBox.getText()+",";
			}
		}
		if("".equals(animalType)){
			Alert2.create("请选择动物种类");
			animalTypeListView.requestFocus();
			return ;
		}
		animalType =animalType.substring(0, (animalType.length()-1));
		
		TblAnimalHouse tblAnimalHouse = new  TblAnimalHouse();
		tblAnimalHouse.setRoom(room);
		tblAnimalHouse.setFloor(floor);
		tblAnimalHouse.setBuilding(building);
		int orderNo = BaseService.getTblAnimalHouseService().saveAndReturnOrderNo(tblAnimalHouse,animalType);
		
		TblAnimalHouseForTableView tblAnimalHouseForTableView =  new TblAnimalHouseForTableView(
					room,floor,building,animalType,orderNo);
		data_tblAnimalHouseTable.add( tblAnimalHouseForTableView);
		tblAnimalHouseTable.getSelectionModel().select(tblAnimalHouseForTableView);
		
		roomText.clear();
		floorText.clear();
		buildingText.clear();
		for(CheckBox checkBox:data_animalTypeListView){
			checkBox.setSelected(false);
		}
		
	}
	/**
	 * 向左，删除
	 * @param event
	 */
	@FXML
	private void onLeftButton_4(ActionEvent event ){
		if(null!=tblAnimalHouseTable.getSelectionModel().getSelectedItem()){
			TblAnimalHouseForTableView selectedItem =
					tblAnimalHouseTable.getSelectionModel().getSelectedItem();
			if(Confirm2.create(Main.getInstance().getStage(), 
					"确定要删除房间号‘"+selectedItem.getRoom()+"'吗？")){
				BaseService.getTblAnimalHouseService().deleteByOrderNo(selectedItem.getOrderNo());	
				data_tblAnimalHouseTable.remove(selectedItem);
				tblAnimalHouseTable.getSelectionModel().clearSelection();
				
				
				roomText.setText(selectedItem.getRoom());
				floorText.setText(selectedItem.getFloor());
				buildingText.setText(selectedItem.getBuilding());
				for(CheckBox checkBox:data_animalTypeListView){
					if(selectedItem.getAnimalType().contains(checkBox.getText())){
						checkBox.setSelected(true);
					}else{
						checkBox.setSelected(false);
					}
				}
				
			}
		}else{
			Alert2.create("请先选择待删除项");
		}
	}
	/**
	 * 向上移动
	 * @param event
	 */
	@FXML
	private void onUpButton_4(ActionEvent event ){
		if(null!=tblAnimalHouseTable.getSelectionModel().getSelectedItem()){
			TblAnimalHouseForTableView selectedItem = 
					tblAnimalHouseTable.getSelectionModel().getSelectedItem();
			if(!data_tblAnimalHouseTable.get(0).equals(selectedItem)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_tblAnimalHouseTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_tblAnimalHouseTable.get(i-1).getOrderNo();
				
				TblAnimalHouseForTableView nextItem = data_tblAnimalHouseTable.get(i-1);
				
				TblAnimalHouseForTableView newItem =new TblAnimalHouseForTableView();
				newItem.setRoom(selectedItem.getRoom());
				newItem.setFloor(selectedItem.getFloor());
				newItem.setBuilding(selectedItem.getBuilding());
				newItem.setAnimalType(selectedItem.getAnimalType());
				newItem.setOrderNo(nextOrderNo);
				
				nextItem.setOrderNo(currentOrderNo);
				
				data_tblAnimalHouseTable.add(i-1,newItem);
				data_tblAnimalHouseTable.remove(i+1);
				
				tblAnimalHouseTable.getSelectionModel().select(i-1);
				BaseService.getTblAnimalHouseService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	/**
	 * 向下移动
	 * @param event
	 */
	@FXML
	private void onDownButton_4(ActionEvent event ){
		if(null!=tblAnimalHouseTable.getSelectionModel().getSelectedItem()){
			TblAnimalHouseForTableView selectedItem = 
					tblAnimalHouseTable.getSelectionModel().getSelectedItem();
			if(data_tblAnimalHouseTable.lastIndexOf(selectedItem) !=(data_tblAnimalHouseTable.size()-1)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_tblAnimalHouseTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_tblAnimalHouseTable.get(i+1).getOrderNo();
				
				TblAnimalHouseForTableView nextItem = data_tblAnimalHouseTable.get(i+1);
				TblAnimalHouseForTableView newItem =new TblAnimalHouseForTableView();
				newItem.setRoom(nextItem.getRoom());
				newItem.setFloor(nextItem.getFloor());
				newItem.setBuilding(nextItem.getBuilding());
				newItem.setAnimalType(nextItem.getAnimalType());
				newItem.setOrderNo(currentOrderNo);
				
				selectedItem.setOrderNo(nextOrderNo);
				tblAnimalHouseTable.getSelectionModel().clearSelection();
				
				data_tblAnimalHouseTable.add(i,newItem);
				data_tblAnimalHouseTable.remove(i+2);
				
				tblAnimalHouseTable.getSelectionModel().clearAndSelect(i+1);
				
				BaseService.getTblAnimalHouseService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	
	//=======tab 5 1
	
	
	/**
	 * 初始化天平Table及数据
	 */
	private void initTblBalTable() {
		data_tblBalTable.clear();

		balIdCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("balId"));
		balDescCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("balDesc"));
		commEnableCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("commEnable"));
		baudRateCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("baudRate"));
		dataBitCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("dataBit"));
		stopBitCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("stopBit"));
		checkModeCol.setCellValueFactory(new PropertyValueFactory<TblBalForTableView,String>("checkMode"));
		
		tblBalTable.setItems(data_tblBalTable);
		tblBalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		List<TblBal> list = BaseService.getTblBalService().findAllOrderByOrderNo();
		if(null != list && list.size()>0){
			for(TblBal tblBal:list){
				data_tblBalTable.add(new TblBalForTableView(
						tblBal.getBalId(),tblBal.getBalDesc(),tblBal.getCommEnable(),tblBal.getBaudRate()
						,tblBal.getDataBit(),tblBal.getStopBit(),tblBal.getCheckMode(),tblBal.getOrderNo()));
			}
		}
		
		tblBalTable.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<TblBalForTableView>(){
			@Override
			public void changed(ObservableValue<? extends TblBalForTableView> arg0,
					TblBalForTableView arg1, TblBalForTableView newValue) {
				if(null!=newValue){
					if(data_tblBalTable.get(0).equals(newValue)){
						//第一个
						upButton_5.setDisable(true);
						if(data_tblBalTable.size()==1){
							downButton_5.setDisable(true);
						}else{
							downButton_5.setDisable(false);
						}
					}else if(data_tblBalTable.lastIndexOf(newValue)== (data_tblBalTable.size()-1)){
						upButton_5.setDisable(false);
						downButton_5.setDisable(true);
					}else{
						upButton_5.setDisable(false);
						downButton_5.setDisable(false);
					}
				}else{
					
					//按钮不可用
					upButton_5.setDisable(true);
					downButton_5.setDisable(true);
				}
				
			}});
	}
	/**
	 * 改变 4  ComboBox状态
	 * @param b
	 */
	private void change4ComboBoxDisable(boolean b) {
		if(b){
			baudRateComboBox.getSelectionModel().clearSelection();
			dataBitComboBox.getSelectionModel().clearSelection();
			stopBitComboBox.getSelectionModel().clearSelection();
			checkModeComboBox.getSelectionModel().clearSelection();
		}
		baudRateComboBox.setDisable(b);
		dataBitComboBox.setDisable(b);
		stopBitComboBox.setDisable(b);
		checkModeComboBox.setDisable(b);
	}
	/**
	 * 初始化  是否采集下拉框
	 */
	private void initCommEnableComboBox() {
		commEnableComboBox.getSelectionModel().selectedItemProperty().
		addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if(null!=newValue && "是".equals(newValue)){
					change4ComboBoxDisable(false);
				}else{
					change4ComboBoxDisable(true);
				}
				
			}});
		
	}
	/**
	 * 向右，添加
	 * @param event
	 */
	@FXML
	private void onRightButton_51(ActionEvent event ){
		String balId ="";				//天平Id
		String balDesc="";				// 设备描述
		int commEnable=0;              //是否自动采集
		String baudRate="";             //波特率   
		String dataBit="";              //数据位   5 6 7 8
		String stopBit="";              //停止位    1,1.5,2
		String checkMode="";            //校验位    None，Even，Odd，Space，Mark 
		
		balId = balIdText.getText().trim();
		if("".equals(balId)){
			Alert2.create("请填写‘天平Id号’");
			balIdText.requestFocus();
			return ;
		}
		if(balId.getBytes().length>20){
			Alert2.create("‘天平Id号’长度不能超过20");
			balIdText.requestFocus();
			return ;
		}
		boolean isExist = BaseService.getTblAnimalHouseService().isExistById(balId);
		if(isExist){
			Alert2.create("‘天平Id号’"+balId+" 已存在");
			roomText.requestFocus();
			return ;
		}
		
		balDesc =balDescText.getText().trim();
		if("".equals(balDesc)){
			Alert2.create("请填写‘设备名称’");
			balDescText.requestFocus();
			return ;
		}
		if(balDesc.getBytes().length>50){
			Alert2.create("‘设备名称’长度不能超过50");
			balDescText.requestFocus();
			return ;
		}
		
		String commEnableStr = commEnableComboBox.getSelectionModel().getSelectedItem();
		if(null == commEnableStr){
			Alert2.create("请先选择‘采集方式’");
			return ;
		}
		if("是".equals(commEnableStr)){
			commEnable = 1;
			baudRate = baudRateComboBox.getSelectionModel().getSelectedItem();
			if(null == baudRate){
				Alert2.create("请先选择‘波特率’");
				return ;
			}
			dataBit = dataBitComboBox.getSelectionModel().getSelectedItem();
			if(null == dataBit){
				Alert2.create("请先选择‘数据位’");
				return ;
			}
			stopBit = stopBitComboBox.getSelectionModel().getSelectedItem();
			if(null == stopBit){
				Alert2.create("请先选择‘停止位’");
				return ;
			}
			checkMode = checkModeComboBox.getSelectionModel().getSelectedItem();
			if(null == checkMode){
				Alert2.create("请先选择‘校验位’");
				return ;
			}
		}else{
			commEnable = 0;
		}
		
		TblBal tblBal = new  TblBal();
		tblBal.setBalId(balId);
		tblBal.setBalDesc(balDesc);
		tblBal.setCommEnable(commEnable);
		tblBal.setBaudRate(baudRate);
		tblBal.setDataBit(dataBit);
		tblBal.setStopBit(stopBit);
		tblBal.setCheckMode(checkMode);
		
		int orderNo = BaseService.getTblBalService().saveAndReturnOrderNo(tblBal);
		
		TblBalForTableView tblBalForTableView =  new TblBalForTableView(balId,balDesc,commEnable,baudRate,
				dataBit,stopBit,checkMode,orderNo);
		data_tblBalTable.add( tblBalForTableView);
		tblBalTable.getSelectionModel().select(tblBalForTableView);
		
		balIdText.clear();
		balDescText.clear();
		commEnableComboBox.getSelectionModel().clearSelection();
		
		//更新 balIdComboBox数据
		updateBalIdComboBox();
		
	}
	/**
	 * 向左，删除
	 * @param event
	 */
	@FXML
	private void onLeftButton_51(ActionEvent event ){
		if(null!=tblBalTable.getSelectionModel().getSelectedItem()){
			TblBalForTableView selectedItem =
					tblBalTable.getSelectionModel().getSelectedItem();
			if(Confirm.create(Main.getInstance().getStage(), "提示", 
					"删除设备信息,将同时删除与之相关的主机信息", "确定删除吗？")){
				BaseService.getTblBalService().deleteByBalId(selectedItem.getBalId())	;
				data_tblBalTable.remove(selectedItem);
				tblBalTable.getSelectionModel().clearSelection();
				
				
				balIdText.setText(selectedItem.getBalId());
				balDescText.setText(selectedItem.getBalDesc());
				if(selectedItem.getCommEnable().equals("是")){
					commEnableComboBox.getSelectionModel().select(0);
					baudRateComboBox.getSelectionModel().select(selectedItem.getBaudRate());
					dataBitComboBox.getSelectionModel().select(selectedItem.getDataBit());
					stopBitComboBox.getSelectionModel().select(selectedItem.getStopBit());
					checkModeComboBox.getSelectionModel().select(selectedItem.getCheckMode());
					//更新 balIdComboBox数据
					updateBalIdComboBox();
					//更新tblHostBalTable数据
					updateTblHostBalTable();
				}else{
					commEnableComboBox.getSelectionModel().select(1);
				}
			}
		}else{
			Alert2.create("请先选择待删除项");
		}
	}
	/**
	 * 向上移动
	 * @param event
	 */
	@FXML
	private void onUpButton_51(ActionEvent event ){
		if(null!=tblBalTable.getSelectionModel().getSelectedItem()){
			TblBalForTableView selectedItem = 
					tblBalTable.getSelectionModel().getSelectedItem();
			if(!data_tblBalTable.get(0).equals(selectedItem)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_tblBalTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_tblBalTable.get(i-1).getOrderNo();
				
				TblBalForTableView nextItem = data_tblBalTable.get(i-1);
				
				TblBalForTableView newItem =new TblBalForTableView();
				newItem.setBalId(selectedItem.getBalId());
				newItem.setBalDesc(selectedItem.getBalDesc());
				newItem.setCommEnable(selectedItem.getCommEnable().equals("是") ? 1:0);
				newItem.setBaudRate(selectedItem.getBaudRate());
				newItem.setDataBit(selectedItem.getDataBit());
				newItem.setStopBit(selectedItem.getStopBit());
				newItem.setCheckMode(selectedItem.getCheckMode());
				
				newItem.setOrderNo(nextOrderNo);
				
				nextItem.setOrderNo(currentOrderNo);
				
				data_tblBalTable.add(i-1,newItem);
				data_tblBalTable.remove(i+1);
				
				tblBalTable.getSelectionModel().select(i-1);
				BaseService.getTblBalService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	/**
	 * 向下移动
	 * @param event
	 */
	@FXML
	private void onDownButton_51(ActionEvent event ){
		if(null!=tblBalTable.getSelectionModel().getSelectedItem()){
			TblBalForTableView selectedItem = 
					tblBalTable.getSelectionModel().getSelectedItem();
			if(data_tblBalTable.lastIndexOf(selectedItem) !=(data_tblBalTable.size()-1)){
				int currentOrderNo = selectedItem.getOrderNo();
				int i=data_tblBalTable.lastIndexOf(selectedItem);
				int nextOrderNo = data_tblBalTable.get(i+1).getOrderNo();
				
				TblBalForTableView nextItem = data_tblBalTable.get(i+1);
				TblBalForTableView newItem =new TblBalForTableView();
				newItem.setBalId(nextItem.getBalId());
				newItem.setBalDesc(nextItem.getBalDesc());
				newItem.setCommEnable(nextItem.getCommEnable().equals("是") ? 1:0);
				newItem.setBaudRate(nextItem.getBaudRate());
				newItem.setDataBit(nextItem.getDataBit());
				newItem.setStopBit(nextItem.getStopBit());
				newItem.setCheckMode(nextItem.getCheckMode());
				
				newItem.setOrderNo(currentOrderNo);
				
				selectedItem.setOrderNo(nextOrderNo);
				tblBalTable.getSelectionModel().clearSelection();
				
				data_tblBalTable.add(i,newItem);
				data_tblBalTable.remove(i+2);
				
				tblBalTable.getSelectionModel().clearAndSelect(i+1);
				
				BaseService.getTblBalService().updateOrderNo(currentOrderNo,nextOrderNo);
			}
		}else{
			Alert2.create("请先选择待移动项");
		}
	}
	
	///  tab   52
	
	/**
	 * 更新 balIdComboBox数据
	 */
	private void updateBalIdComboBox() {
		data_balIdComboBox.clear();
		List<String> balIdList = BaseService.getTblBalService().getBalIdListByCommEnable(1);
		if(null!=balIdList){
			for(String str:balIdList){
				data_balIdComboBox.add(str);
			}
		}
	}
	
	/**
	 * 初始化连接天平表格tblHostBalTable及其数据
	 */
	private void initTblHostBalTable() {
		data_tblHostBalTable.clear();
		balIdCol_2.setCellValueFactory(new PropertyValueFactory<TblHostBalForTableView, String>("balId"));
		comPortCol_2.setCellValueFactory(new PropertyValueFactory<TblHostBalForTableView, String>("comPort"));
		enableCol_2.setCellValueFactory(new PropertyValueFactory<TblHostBalForTableView, String>("enable"));
		tblHostBalTable.setItems(data_tblHostBalTable);
		tblHostBalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		List<TblHostBal> tblHostBalList = BaseService.getTblHostBalService().findListByHostName(hostName);
		if(null != tblHostBalList){
			for(TblHostBal tblHostBal:tblHostBalList){
				data_tblHostBalTable.add(new TblHostBalForTableView(
						tblHostBal.getId(),tblHostBal.getBalId(),tblHostBal.getComPort(),tblHostBal.getEnable()
						));
			}
		}
	}
	/**
	 * 更新tblHostBalTable数据
	 */
	private void updateTblHostBalTable(){
		data_tblHostBalTable.clear();
		List<TblHostBal> tblHostBalList = BaseService.getTblHostBalService().findListByHostName(hostName);
		if(null != tblHostBalList){
			for(TblHostBal tblHostBal:tblHostBalList){
				data_tblHostBalTable.add(new TblHostBalForTableView(
						tblHostBal.getId(),tblHostBal.getBalId(),tblHostBal.getComPort(),tblHostBal.getEnable()
						));
			}
		}
	}
	/**
	 * 向右，添加
	 * @param event
	 */
	@FXML
	private void onRightButton_52(ActionEvent event ){
		String balId ="";				//天平Id
		String comPort="";				// 设备描述
		int enable = 0;              //是否启用
		
		
		balId = balIdComboBox.getSelectionModel().getSelectedItem();
		if(null == balId){
			Alert2.create("请先选择‘设备Id号’");
			return ;
		}

		boolean isExist = BaseService.getTblHostBalService().isExistByHostNameBalId(hostName, balId);
		if(isExist){
			Alert2.create("‘设备Id号’"+balId+" 已存在");
			return ;
		}
		comPort = comPortComboBox.getSelectionModel().getSelectedItem();
		if(null == comPort){
			Alert2.create("请先选择‘串口名称’");
			return ;
		}
		boolean isExistCom = BaseService.getTblHostBalService().isExistByHostNameCom(hostName, comPort);
		if(isExistCom){
			Alert2.create("‘串口’"+comPort+" 已占用");
			return ;
		}
		String enableStr = enableComboBox.getSelectionModel().getSelectedItem();
		if(null == enableStr){
			Alert2.create("请先选择‘是否启用’");
			return ;
		}
		if("是".equals(enableStr)){
			enable = 1;
		}
	
		
		TblHostBal tblHostBal = new  TblHostBal();
		
		tblHostBal.setBalId(balId);
		tblHostBal.setHostName(hostName);
		tblHostBal.setEnable(enable);
		tblHostBal.setComPort(comPort);
		
		TblBal tblBal = new TblBal();
		tblBal.setBalId(balId);
		
		tblHostBal.setTblBal(tblBal);
		
		String id = BaseService.getTblHostBalService().saveReturnId(tblHostBal);
		
		TblHostBalForTableView tblHostBalForTableView =  new TblHostBalForTableView(
				id,balId,comPort,enable);
		data_tblHostBalTable.add( tblHostBalForTableView);
		tblHostBalTable.getSelectionModel().select(tblHostBalForTableView);
		
		balIdComboBox.getSelectionModel().clearSelection();
		comPortComboBox.getSelectionModel().clearSelection();
		enableComboBox.getSelectionModel().clearSelection();
		
	}
	/**
	 * 向左，删除
	 * @param event
	 */
	@FXML
	private void onLeftButton_52(ActionEvent event ){
		if(null!=tblHostBalTable.getSelectionModel().getSelectedItem()){
			TblHostBalForTableView selectedItem =
					tblHostBalTable.getSelectionModel().getSelectedItem();
			if(Confirm2.create(Main.getInstance().getStage(), "确定删除'"+
					selectedItem.getBalId()+"'的连接信息吗？")){
				BaseService.getTblHostBalService().delete(selectedItem.getId());
				data_tblHostBalTable.remove(selectedItem);
				tblHostBalTable.getSelectionModel().clearSelection();
				
				
				balIdComboBox.getSelectionModel().select(selectedItem.getBalId());
				comPortComboBox.getSelectionModel().select(selectedItem.getComPort());
				if(selectedItem.getEnable().equals("是")){
					enableComboBox.getSelectionModel().select(0);
					
				}else{
					enableComboBox.getSelectionModel().select(1);
				}
			}
		}else{
			Alert2.create("请先选择待删除项");
		}
	}
	//----tab6
	/**
	 * 初始化界面输出设置表格及其数据
	 */
	private void initDictOutputSettingsTable() {
		labelCol.setCellValueFactory(new PropertyValueFactory<DictOutputSettingsForTableView,String>("label"));
		showCol.setCellValueFactory(new PropertyValueFactory<DictOutputSettingsForTableView,String>("show"));
		
		Callback<TableColumn<DictOutputSettingsForTableView,String>,TableCell<DictOutputSettingsForTableView,String>> cellFactory = 
				new Callback<TableColumn<DictOutputSettingsForTableView,String>,TableCell<DictOutputSettingsForTableView,String>>(){

			@Override
			public TableCell call(TableColumn p) {
				
				return new TextFieldTableCellImpl();
			}
			
		};
		showCol.setCellFactory(cellFactory);
		dictOutputSettingsTable.setItems(data_dictOutputSettingsTable);
		updateDictOutputSettingsTable();
	}
	/**
	 * 更新dictOutputSettingsTable数据
	 */
	private void updateDictOutputSettingsTable(){
		data_dictOutputSettingsTable.clear();
		copyData_dictOutputSettingsTable.clear();
		List<DictOutputSettings> dictOutputSettingsList = BaseService
				.getDictOutputSettingsService().getListOrderByOrderNo();
		if(null != dictOutputSettingsList && dictOutputSettingsList.size()>0){
			for(DictOutputSettings obj:dictOutputSettingsList){
				data_dictOutputSettingsTable.add(new DictOutputSettingsForTableView(
						obj.getId(),obj.getLabel(),obj.getShow(),obj.getOrderNo()));
				copyData_dictOutputSettingsTable.add(new DictOutputSettingsForTableView(
						obj.getId(),obj.getLabel(),obj.getShow(),obj.getOrderNo()));
			}
		}
	}
	/**保存设置按钮
	 * @param event
	 */
	@FXML
	private void onSaveSetButton(ActionEvent event){
		int index =0;
		List<DictOutputSettings> list = new ArrayList<DictOutputSettings>();
		DictOutputSettings dictOutputSettings =null;
		for(DictOutputSettingsForTableView obj:data_dictOutputSettingsTable){
			if(!obj.getShow().equals(copyData_dictOutputSettingsTable.get(index).getShow())){
				dictOutputSettings = new DictOutputSettings();
				dictOutputSettings.setId(obj.getId());
				dictOutputSettings.setLabel(obj.getLabel());
				dictOutputSettings.setShow(obj.getShow());
				dictOutputSettings.setOrderNo(obj.getOrderNo());
				list.add(dictOutputSettings);
			}
			index++;
		}
		if(list.size()>0){
			BaseService.getDictOutputSettingsService().updateList(list);
			updateDictOutputSettingsTable();
			Alert.create("设置保存成功");
		}
		
	}
	/**
	 * 加载数据
	 */
	public void loadData(){
		//tab6  更新dictOutputSettingsTable数据
		updateDictOutputSettingsTable();
	}

	public class TextFieldTableCellImpl extends TableCell<DictOutputSettingsForTableView, String> {

		private TextField textField;

		public TextFieldTableCellImpl() {
			super();
//			this.setOnMouseClicked(new EventHandler<MouseEvent>(){
//
//				@Override
//				public void handle(MouseEvent arg0) {
//					startEdit();
//				}
//				
//			});
		}

		@Override
		public void startEdit() {
			super.startEdit();
//			if (textField == null) {
				createTextField();
//			}
			textField.setEditable(true);
			setText(null);
			setGraphic(textField);
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(null);
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);

				} else {
					getTableView().getItems().get(getIndex()).setShow(getString());
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						commitEdit(textField.getText());
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}

			});
			textField.setOnMouseMoved(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					textField.requestFocus();
					textField.end();
				}
			});
//			getTableRow().setOnMouseExited(new EventHandler<MouseEvent>() {
//
//				@Override
//				public void handle(MouseEvent arg0) {
//					commitEdit(textField.getText());
//				}
//			});
			getTableRow().selectedProperty().addListener(new ChangeListener<Boolean>(){

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
						Boolean newValue) {
					if(!newValue){
						commitEdit(textField.getText());
					}
					
				}});
			getTableView().setOnMouseExited(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					commitEdit(textField.getText());
				}});
		}

		@Override
		public void commitEdit(String text) {
			super.commitEdit(text);
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}
}
