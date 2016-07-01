package com.lanen.view.test;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblAnatomyTask;
import com.lanen.model.path.TblVisceraWeight;
import com.lanen.util.NumberValidationUtils;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.messager.Messager;

/**补录脏器称重
 * @author Administrator
 *
 */
public class AdditionalPage_weigh extends Application implements Initializable {

	
	/**
	 * 已签字，不可编辑，
	 */
	private boolean isSigned = true;	//已签字，不可编辑，
	private String studyNo = "";
	private String taskId = "";
	
	
	@FXML
	private Label handlersLabel; // 当前操作者
	
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
	private Label animalCodeLabel;
	@FXML
	private Label visceraNameLabel;
	
	@FXML
	private TextField weighText;
	
	@FXML
	private Button inputButton;
	
	/*
	 * 脏器称重记录   table
	 */
	@FXML 
	private TableView<VisceraWeight> visceraWeightTable;
	private ObservableList<VisceraWeight> data_visceraWeightTable = FXCollections.observableArrayList(); 
	@FXML
	private TableColumn<VisceraWeight,String> viscerNameCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,String> weightCol_visceraWeightTable;
	@FXML
	private TableColumn<VisceraWeight,Boolean> operateCol_visceraWeightTable;
	
	//--------------------------------脏器称重-----------------------------
	@FXML
	private TreeView<String> visceraTree_weight; // 选择脏器tree
	private TreeItem<String> rootNode_weight=new TreeItem<String>();//visceraTree_weight 根节点
	/**
	 * 存放visceraCode,及对应树节点(父脏器)
	 */
	private Map<String,TreeItem<String>> visceraCodeTreeItemMap_weight = new HashMap<String,TreeItem<String>>();
	
	private static AdditionalPage_weigh instance;

	public static AdditionalPage_weigh getInstance() {
		if (null == instance) {
			instance = new AdditionalPage_weigh();
		}
		return instance;
	}

	public AdditionalPage_weigh() {
	}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO 初始化
		instance = this;
		//1.初始化 当前操作者
		initHandlerLabel();
		//2.初始化动物表
		initAnimalTable();
		//3.初始化称重记录表格
		initVisceraWeightTable();
		//4.初始化 称重脏器树
		initVisceraTree_weight();
	}
	
	/**
	 * .初始化 称重标签页，选择脏器树
	 */
	private void initVisceraTree_weight() {
		
		rootNode_weight.setValue("称重脏器");
		rootNode_weight.setExpanded(true);
		visceraTree_weight.setRoot(rootNode_weight);
		visceraTree_weight.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>(){

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> arg0,
					TreeItem<String> arg1, TreeItem<String> newValue) {
				if(null != newValue){
					visceraNameLabel.setText(newValue.getValue());
				}else{
					visceraNameLabel.setText("");
				}
			}
		});
	}
	
	/**
	 * 初始化称重记录表格
	 */
	private void initVisceraWeightTable() {
		visceraWeightTable.setItems(data_visceraWeightTable);
		visceraWeightTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		viscerNameCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("visceraName"));
		weightCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,String>("weight"));
		operateCol_visceraWeightTable.setCellValueFactory(new PropertyValueFactory<VisceraWeight,Boolean>("operate"));
		operateCol_visceraWeightTable.setCellFactory(new Callback<TableColumn<VisceraWeight, Boolean>, TableCell<VisceraWeight, Boolean>>() {

            public TableCell<VisceraWeight, Boolean> call(TableColumn<VisceraWeight, Boolean> p) {
            	HyperlinkCell_weight<VisceraWeight, Boolean> cell = new HyperlinkCell_weight<VisceraWeight, Boolean>();
                return cell;

            }

        });
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
	/**
	 *录入
	 * 
	 * @param event
	 */
	@FXML
	void onInputBtn(ActionEvent event) {
		String weight = weighText.getText().trim();
		if(null == weight || "".equals(weight)){
			weighText.requestFocus();
			return ;
		}
		if(!NumberValidationUtils.isPositiveRealNumber(weight)){
			showErrorMessage("数据格式不对！");
			return ;
		}
		
		addOneWeightAndUpdateVisceraWeightTable(weight);
	}
	
	/**
	 * 增加一行称重信息，并刷新称重结果表
	 */
	private void addOneWeightAndUpdateVisceraWeightTable(String weight){
		if(!isSigned){
			//1.选择的动物
			Animal animal = animalTable.getSelectionModel().getSelectedItem();
			if(null == animal){
				showErrorMessage("请先选择动物！");
				return ;
			}
			String animalCode = animal.getAnimalCode();
			//2.选择的脏器
			TreeItem<String> treeSelectedItem = visceraTree_weight.getSelectionModel().getSelectedItem();
			if(null == treeSelectedItem){
				showErrorMessage("请先选择脏器！");
				return ;
			}
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>)treeSelectedItem.getGraphic().getUserData();
			Integer visceraType = (Integer) map.get("visceraType");
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraCode = null;
			String subVisceraName = null;
			if(treeSelectedItem.isLeaf()){
				subVisceraCode = (String) map.get("subVisceraCode");
				subVisceraName = (String) map.get("subVisceraName");
			}
//			Integer partVisceraSeparateWeigh = (Integer) map.get("partVisceraSeparateWeigh");
			Integer attachedVisceraFlag = (Integer) map.get("attachedVisceraFlag");
			String attachedVisceraNames = (String) map.get("attachedVisceraNames");
			Integer gender = (Integer) map.get("gender");

			String operator = SaveUserInfo.getUserName();
			Date currentDate = BaseService.getInstance().getTblAnatomyAnimalService().getCurrentDate();
			
			//3.性别是否匹配
			if(null != gender && gender != 0){
				if(gender == 1 && animal.getGender().equals("♀") || gender == 2 && animal.getGender().equals("♂")){
					showErrorMessage("动物性别和脏器不匹配！");
					return ;
				}
			}
			
			//4。是否脏器已自溶或缺如
			boolean hasAutolyze = BaseService.getInstance().getTblAnatomyCheckService().isHasAutolyze_1(studyNo, animalCode, visceraCode, subVisceraCode);
			if(hasAutolyze){
				showErrorMessage("动物脏器已自溶！");
				return ;
			}
			boolean hasMissing = BaseService.getInstance().getTblAnatomyCheckService().isMissing_1(studyNo, animalCode, visceraCode, subVisceraCode);
			if(hasMissing){
				showErrorMessage("动物脏器已缺失！");
				return ;
			}
			//5.是否已称重
			String currentVisceraName = visceraName;
			if(null != subVisceraName && !"".equals(subVisceraName)){
				currentVisceraName = subVisceraName;
			}
			boolean isExist = false;
			for(VisceraWeight obj:data_visceraWeightTable){
				if(currentVisceraName.equals(obj.getVisceraName())){
					isExist = true;
					break;
				}
			}
			if(isExist){
				if(!Messager.showSimpleConfirm("提示","该脏器已称重，是否对该脏器重新称重？")){
					weighText.requestFocus();
					return;
				}
			}
			
			
			//6.保存
			TblVisceraWeight tblVisceraWeight = new TblVisceraWeight();
			
//			tblVisceraWeight.setSessionId(sessionId);
			tblVisceraWeight.setStudyNo(studyNo);
			tblVisceraWeight.setAnimalCode(animalCode);
			tblVisceraWeight.setVisceraType(visceraType);
			tblVisceraWeight.setVisceraCode(visceraCode);
			tblVisceraWeight.setVisceraName(visceraName);
			if(treeSelectedItem.isLeaf()){
				tblVisceraWeight.setSubVisceraCode(subVisceraCode);
				tblVisceraWeight.setSubVisceraName(subVisceraName);
			}
			tblVisceraWeight.setWeight(weight);
			tblVisceraWeight.setWeightUnit("g");
			tblVisceraWeight.setAttachedVisceraFlag(attachedVisceraFlag);
			tblVisceraWeight.setFixedWeightFlag(0);
			tblVisceraWeight.setAttachedVisceraNames(attachedVisceraNames);
			tblVisceraWeight.setOperator(operator);
			tblVisceraWeight.setOperateTime(currentDate);
//			tblVisceraWeight.setBalCode(balCode);
//			tblVisceraWeight.setBalValidDate(balValidDate);
//			tblVisceraWeight.setHostName(hostName);
//			tblVisceraWeight.setCalIndexId(calIndexId);
//			TblVisceraWeight oldTblVisceraWeight = BaseService.getInstance().getTblVisceraWeightService().getOne(tblVisceraWeight.getStudyNo(),
//					tblVisceraWeight.getAnimalCode(), tblVisceraWeight.getVisceraCode()
//					,tblVisceraWeight.getSubVisceraCode());
//			
//			if(null != oldTblVisceraWeight ){
//				if(!Messager.showSimpleConfirm("提示","该脏器已称重，是否对该脏器重新称重？")){
//					weighText.requestFocus();
//					return;
//				}else{
//				};
//			}
			
			String id = BaseService.getInstance().getTblVisceraWeightService().saveOrUpdateOne(tblVisceraWeight);
			updateVisceraWeightTable(animalCode,id);
			
			//7.判断是否有打钩脏器
			boolean hasChecked = hasCheckedVisceraTree();
			if(hasChecked){
				//1.当前selected的下面是否有性别匹配的checked脏器，如果有，select；没有返回 false
				int gender_ = 1;
				if("♀".equals(animal.getGender())){
					gender_ = 2;
				}
				boolean hasNextChecked = hasNextCheckedByGender(gender_);
				
				//2.如果没有，看是否有下一个动物，
				if(!hasNextChecked){
					//选中下一个完成
					boolean selectNextFinish = false;
					while(!selectNextFinish){
						Animal oldSelectedAnimal = animalTable.getSelectionModel().getSelectedItem();
						//选中下一个动物
						animalTable.getSelectionModel().selectNext();
						Animal newSelectedAnimal = animalTable.getSelectionModel().getSelectedItem();
						if(oldSelectedAnimal.equals(newSelectedAnimal)){
							//已是最后一个动物
							break;
						}
						gender_ = 1;
						if("♀".equals(newSelectedAnimal.getGender())){
							gender_ = 2;
						}
						selectNextFinish = hasCheckedByGender(gender_);
					}
					if(!selectNextFinish){
						animalTable.getSelectionModel().select(animal);
					}
				}
				
				
			}
			
			//8.
				weighText.clear();
				weighText.requestFocus();
			
		}
	}
	/**判断是否有性别匹配的待选者，有选中 ，没有 返回false
	 * @param gender_
	 * @return
	 */
	private boolean hasCheckedByGender(int gender_) {
		boolean hasNextChecked = false;
		
		ObservableList<TreeItem<String>> children = rootNode_weight.getChildren();
		if(null != children && children.size() > 0){
			for(TreeItem<String> treeitem:children){
				CheckBox graphic = (CheckBox) treeitem.getGraphic();
				if(graphic.isSelected()){
					@SuppressWarnings("unchecked")
					Map<String,Object> map = (Map<String, Object>) graphic.getUserData();
					Integer gender = (Integer) map.get("gender");
					if(gender == 0 || gender == gender_){
						visceraTree_weight.getSelectionModel().select(treeitem);
						hasNextChecked = true;
						break;
					}
				}
				if(!treeitem.isLeaf()){
					ObservableList<TreeItem<String>> leafs = treeitem.getChildren();
					if(null != leafs && leafs.size() > 0){
						for(TreeItem<String> leaf:leafs){
							CheckBox checkbox = (CheckBox) leaf.getGraphic();
							if(checkbox.isSelected()){
								@SuppressWarnings("unchecked")
								Map<String,Object> map = (Map<String, Object>) checkbox.getUserData();
								Integer gender = (Integer) map.get("gender");
								if(gender == 0 || gender == gender_){
									treeitem.setExpanded(true);
									visceraTree_weight.getSelectionModel().select(leaf);
									hasNextChecked = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		return hasNextChecked;
	}

	/**检查脏器树,在当前选中脏器之后 是否有打钩的且性别匹配，有的话 选中
	 * @param gender_
	 * @return
	 */
	private boolean hasNextCheckedByGender(int gender_) {
		boolean hasNextChecked = false;
		
		//是否是在选中的下面
		boolean isNext = false;
		TreeItem<String> selectedItem = visceraTree_weight.getSelectionModel().getSelectedItem();
		
		ObservableList<TreeItem<String>> children = rootNode_weight.getChildren();
		if(null != children && children.size() > 0){
			for(TreeItem<String> treeitem:children){
				CheckBox graphic = (CheckBox) treeitem.getGraphic();
				if(isNext && graphic.isSelected()){
					@SuppressWarnings("unchecked")
					Map<String,Object> map = (Map<String, Object>) graphic.getUserData();
					Integer gender = (Integer) map.get("gender");
					if(gender == 0 || gender == gender_){
						visceraTree_weight.getSelectionModel().select(treeitem);
						hasNextChecked = true;
						break;
					}
				}
				if(selectedItem.equals(treeitem)){
					isNext = true;
				}
				if(!treeitem.isLeaf()){
					ObservableList<TreeItem<String>> leafs = treeitem.getChildren();
					if(null != leafs && leafs.size() > 0){
						for(TreeItem<String> leaf:leafs){
							CheckBox checkbox = (CheckBox) leaf.getGraphic();
							if(isNext && checkbox.isSelected()){
								@SuppressWarnings("unchecked")
								Map<String,Object> map = (Map<String, Object>) checkbox.getUserData();
								Integer gender = (Integer) map.get("gender");
								if(gender == 0 || gender == gender_){
									treeitem.setExpanded(true);
									visceraTree_weight.getSelectionModel().select(leaf);
									hasNextChecked = true;
									break;
								}
							}
							if(selectedItem.equals(leaf)){
								isNext = true;
							}
						}
					}
				}
			}
		}
		return hasNextChecked;
	}

	/**检查脏器树是否有打钩的
	 * @return
	 */
	private boolean hasCheckedVisceraTree() {
		boolean hasChecked = false;
		ObservableList<TreeItem<String>> children = rootNode_weight.getChildren();
		if(null != children && children.size() > 0){
			for(TreeItem<String> treeitem:children){
				CheckBox graphic = (CheckBox) treeitem.getGraphic();
				if(graphic.isSelected()){
					hasChecked = true;
					break;
				}
				if(!treeitem.isLeaf()){
					ObservableList<TreeItem<String>> leafs = treeitem.getChildren();
					if(null != leafs && leafs.size() > 0){
						for(TreeItem<String> leaf:leafs){
							CheckBox checkbox = (CheckBox) leaf.getGraphic();
							if(checkbox.isSelected()){
								hasChecked = true;
								break;
							}
						}
					}
				}
			}
		}
		return hasChecked;
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
					setBtnTextDisabled(newValue.getAutolyzeFlag() == 1);
					animalCodeLabel.setText(animalCode);
					//TODO 更新称重结果
					updateVisceraWeightTable(animalCode,null);
				}else{
					setBtnTextDisabled(true);
					animalCodeLabel.setText("");
					updateVisceraWeightTable("",null);
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

	/**更新表格数据并选中指定项
	 * @param animalCode
	 * @param visceraWeighId
	 */
	private void updateVisceraWeightTable(String animalCode,String visceraWeighId) {
		data_visceraWeightTable.clear();
		if(null == animalCode){
			Animal animal = animalTable.getSelectionModel().getSelectedItem();
			if(null != animal){
				animalCode = animal.getAnimalCode();
			}
		}
		if(null != animalCode){
			List<TblVisceraWeight> tblVisceraWeightList = BaseService.getInstance().getTblVisceraWeightService().getList(studyNo,animalCode,false);
			if(null != tblVisceraWeightList && tblVisceraWeightList.size() > 0){
				for(TblVisceraWeight obj:tblVisceraWeightList){
					String visceraName = obj.getVisceraName();
					String subVisceraName = obj.getSubVisceraName();
					int attachedVisceraFlag = obj.getAttachedVisceraFlag();
					String attachedVisceraNames = obj.getAttachedVisceraNames();
					if(attachedVisceraFlag == 1){
						visceraName = visceraName+"("+attachedVisceraNames+")";
					}
					data_visceraWeightTable.add(new VisceraWeight(obj.getId(),visceraName,
							subVisceraName,obj.getWeight(),obj.getWeightUnit(),!isSigned));
				}
			}
			
			if(null != visceraWeighId && !"".equals(visceraWeighId) && data_visceraWeightTable.size() > 0){
				for(VisceraWeight obj :data_visceraWeightTable){
					if(visceraWeighId.equals(obj.getId())){
						visceraWeightTable.getSelectionModel().select(obj);
					}
				}
			}
			
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
		//4.
		animalCodeLabel.setText("");
		visceraNameLabel.setText("");
		weighText.setText("");
		
		//5.更新 选择脏器树 ，根据任务id、专题编号
		updateVisceraTree_weight();
		//6.
		setBtnTextDisabled(true);
	}
	
	/**
	 * 更新 选择脏器树 ，根据任务id、专题编号
	 * 
	 */
	public void updateVisceraTree_weight(){
		rootNode_weight.getChildren().clear();
		visceraCodeTreeItemMap_weight.clear();
		visceraTree_weight.getSelectionModel().clearSelection();
		if(null != taskId && null != studyNo){
			List<Map<String,Object>> mapList = BaseService.getInstance()
					.getTblVisceraWeightService().getVisceraMapListByTaskIdStudyNo(taskId,studyNo);
		
			if(null != mapList && mapList.size() > 0 ){
				for(Map<String,Object> map:mapList){
					String visceraCode = (String) map.get("visceraCode");
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
					TreeItem<String> depNode = null;
					if(visceraCodeTreeItemMap_weight.keySet().contains(visceraCode)){
						depNode = visceraCodeTreeItemMap_weight.get(visceraCode);
					}else{
						Integer attachedVisceraFlag = (Integer) map.get("attachedVisceraFlag"); //有无附加脏器
						if(null != attachedVisceraFlag && attachedVisceraFlag == 1){
							String attachedVisceraNames = (String) map.get("attachedVisceraNames");
							visceraName = visceraName+"("+attachedVisceraNames+")";
						}
						CheckBox checkBox = new CheckBox("");
						checkBox.setUserData(map);
//						checkBox.setMinSize(20, 20);
						depNode = new TreeItem<String>(visceraName);
						depNode.setGraphic(checkBox);
						depNode.setExpanded(false);
						rootNode_weight.getChildren().add(depNode);
						visceraCodeTreeItemMap_weight.put(visceraCode, depNode);
					}
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						TreeItem<String> leafNode = new TreeItem<String>();
						CheckBox checkBox = new CheckBox("");
						checkBox.setUserData(map);
						leafNode.setValue(subVisceraName);
						leafNode.setGraphic(checkBox);
//						checkBox.setMinSize(20, 20);
						depNode.getChildren().add(leafNode);
					}
				}
			}
		}
	}

	/**设置输入框及按钮状态
	 * @param flag
	 */
	private void setBtnTextDisabled(boolean flag){
		if(isSigned){
			inputButton.setDisable(true);
			weighText.setDisable(true);
		}else{
			inputButton.setDisable(flag);
			weighText.setDisable(flag);
		}
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

	
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("AdditionalPage_weigh.fxml"));
		Scene scene = new Scene(root, 800, 500);
		stage.setScene(scene);
		stage.setTitle("补录脏器重量");
		 stage.setResizable(true);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// event.consume();
			}
		});
//		Screen screen2 = Screen.getPrimary();  
//		Rectangle2D bounds = screen2.getVisualBounds();  
		stage.setMinWidth(800);
		stage.setMinHeight(500);
//		stage.setX(bounds.getMinX());  
//		stage.setY(bounds.getMinY());  
//		stage.setWidth(bounds.getWidth());  
//		stage.setHeight(bounds.getHeight());  
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
	
	/**
	 * 动物
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
	/**
	 * 脏器称重记录
	 */
	public static class VisceraWeight{
		private StringProperty id;			//id
		private StringProperty visceraName;	//脏器名称（脏器或子脏器）
		private StringProperty weight;		//重量
		private BooleanProperty  operate;	//操作
		public VisceraWeight(){}
		public VisceraWeight(String id,String visceraName,String subVisceraName,
				String weight,String weightUnit,boolean operate){
			setId(id);
			if(null != subVisceraName && !"".equals(subVisceraName) ){
				setVisceraName(subVisceraName);
			}else{
				setVisceraName(visceraName);
			}
			setWeight(weight+" "+weightUnit);
			setOperate(operate);
		}
		public String getId() {
			return id.get();
		}
		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}
		public String getVisceraName() {
			return visceraName.get();
		}
		public void setVisceraName(String visceraName) {
			this.visceraName = new SimpleStringProperty(visceraName);
		}
		public String getWeight() {
			return weight.get();
		}
		public void setWeight(String weight) {
			this.weight = new SimpleStringProperty(weight);
		}
		public boolean getOperate() {
			return operate.get();
		}
		public void setOperate(boolean operate) {
			this.operate = new SimpleBooleanProperty(operate);
		}
		public BooleanProperty operateProperty(){return operate;}
	}
	/**称重记录专用
	 * @author Administrator
	 * @param <S>
	 * @param <T>
	 */
	public class HyperlinkCell_weight<S, T> extends TableCell<S, T> {
		private Hyperlink hyperlink;  
	    private ObservableValue<T> ov;  
	    private Map<String,String> vmap;  
	      
	    public Map<String, String> getVmap() {  
	        return vmap;  
	    }  
	  
	    public void setVmap(Map<String, String> vmap) {  
	        this.vmap = vmap;  
	    }  
	  
	    public HyperlinkCell_weight() {  
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
	            hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent e) {
						Hyperlink hyperlink = (Hyperlink) e.getSource();
						VisceraWeight visceraWeight = (VisceraWeight) hyperlink.getUserData();
//							String operator = SaveUserInfo.getUserName();
							BaseService.getInstance().getTblVisceraWeightService().delete(visceraWeight.getId());
								updateVisceraWeightTable(null,null);
					}
				});
	        }  
	    }  
	} 
}
