package com.lanen.view.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.lanen.base.BaseService;
import com.lanen.jsonAndModel.Json;
import com.lanen.model.path.TblTissueSliceIndex;
import com.lanen.model.studyplan.TblStudyPlan;
import com.lanen.util.ClassBeanPathDataSource;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.StringUtil;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;
import com.lanen.view.main.SignMeFrame;

/**常规取值取材编号/追加取材编号
 * @author Administrator
 *
 */
public class TissueSliceAdd extends Application implements Initializable {
	
	
	/**
	 * 专题编号
	 */
	private String studyNo = null;
	/**
	 * 切片编号类型
	 */
	private int sliceCodeType = 0;
	
	private TblTissueSliceIndex tblTissueSliceIndex = null;
	
	/**
	 * 常规或追加 适用过的切片编号个数（已签字）
	 */
	private Integer sliceCodeNum = 0;
	
	
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
	private CheckBox showHistopathVisceraCheckBox;//仅列出需要镜检的脏器
	
	@FXML
	private Button otherBtn;		//其他脏器
	
	@FXML
	private Button addToSelectedSliceBtn;//加入到所选蜡块
	@FXML
	private Button addToNewSliceBtn;//加入到新蜡块
	@FXML
	private Button deleteBtn;//删除
	@FXML
	private Button upBtn;//上移
	@FXML
	private Button downBtn;//下移

	@FXML
	private Label studyNoLabel;			//专题编号
	@FXML
	private Label sdLabel;			//SD
	@FXML
	private Label animalTypeLabel;		//动物类型
	
	
	@FXML
	private TreeView<String> visceraTree; // 选择脏器tree
	private TreeItem<String> rootNode = new TreeItem<String>();//visceraTree 根节点 
	/**
	 * 脏器编号（一级脏器编号）->一级脏器树节点
	 */
	private Map<String,TreeItem<String>> visceraCode2TreeItemMap = new HashMap<String,TreeItem<String>>();
	@FXML
	private TreeView<String> tissueSliceTree; // 组织取材编号编号tree
	private TreeItem<String> rootNode_tissueSliceTree = new TreeItem<String>();//tissueSliceTree 根节点 
	/**
	 * 切片编号->切片节点
	 */
	private Map<String,TreeItem<String>> sliceCode2SliceTreeItemMap = new HashMap<String,TreeItem<String>>();
	/**
	 * 脏器编号set，存放的一级脏器列表
	 */
	private Set<String> visceraCodeSet = new HashSet<String>();
	/**
	 * 子脏器编号set，存放的是子脏器列表(是子脏器，则不保存一级脏器编号)
	 */
	private Set<String> subVisceraCodeSet = new HashSet<String>();
	
	
	
	private static TissueSliceAdd instance;
	public static TissueSliceAdd getInstance(){
		if(null == instance){
			instance = new TissueSliceAdd();
		}
		return instance;
	}
	
	public TissueSliceAdd() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		//初始化待选择脏器树初始化
		initVisceraTree();
		//初始化组织取材编号编号tree
		initTissueSliceTree();
		
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
					updateData();
				}
				
			}});
	}

	

	/**
	 * 更新comboBox
	 */
	private void updateCombBox(){
		data_comboBox.clear();
		tissueSliceIndexList.clear();
		comboBox.getSelectionModel().clearSelection();
		if(sliceCodeType == 0){
			data_comboBox.add("常规取材编号");
			
			tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService().getByStudyNo(studyNo);
			tissueSliceIndexList.add(tblTissueSliceIndex);
			comboBox.getSelectionModel().select(0);
		}else{
			tissueSliceIndexList = BaseService.getInstance().getTblTissueSliceIndexService().getAddToListByStudyNo(studyNo);
			if(null != tissueSliceIndexList && tissueSliceIndexList.size() > 0){
				int i = 1;
				String item = "";
				for(TblTissueSliceIndex obj:tissueSliceIndexList){
					item = "追加组织取材编号（第"+i+"次）";
					if(null == obj.getOperatorSign() || "".equals(obj.getOperatorSign())){
						item = "追加组织取材编号（第"+i+"次,未签字）";
					}
					data_comboBox.add(item);
					i++;
				}
				//如果都已签字，添加  追加组织取材编号-新建
				if(null != tissueSliceIndexList.get(i-2).getOperatorSign() 
						&& !"".equals(tissueSliceIndexList.get(i-2).getOperatorSign())){
					data_comboBox.add("追加组织取材编号-新建");
					tissueSliceIndexList.add(null);
				}
			}else{
				tissueSliceIndexList = new ArrayList<TblTissueSliceIndex>();
				data_comboBox.add("追加组织取材编号-新建");
				tissueSliceIndexList.add(null);
			}
			comboBox.getSelectionModel().select(tissueSliceIndexList.size()-1);
		}
	}
	
	
	/**
	 *  初始化组织取材编号编号tree
	 */
	private void initTissueSliceTree() {
		rootNode_tissueSliceTree.setValue("1");
		rootNode_tissueSliceTree.setGraphic(new Label("组织取材编号"));
		rootNode_tissueSliceTree.setExpanded(true);
		tissueSliceTree.setRoot(rootNode_tissueSliceTree);
		
	}

	/**
	 * 初始化待选择脏器树初始化
	 */
	private void initVisceraTree() {
		rootNode.setValue("选择脏器");
		rootNode.setExpanded(true);
		visceraTree.setRoot(rootNode);
		visceraTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	/**仅列出需要镜检的脏器
	 * @param event
	 */
	@FXML
	private void onShowHistopathVisceraCheckBox(ActionEvent event){
		updateVisceraTree();
	}
	
	/**
	 * 其他脏器   
	 */
	@FXML
	private void onOtherBtn(ActionEvent event){
		try {
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
			OtherViscera.getInstance().loadData2("TissueSliceAdd",studyNo,showHistopathVisceraCheckBox.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**签字确认
	 * @param event
	 */
	@FXML
	private void onSignBtnAction(ActionEvent event){
		//1.保存
		//1.3.组织取材编号检查
		if(rootNode_tissueSliceTree.getChildren().size() <1){
			showErrorMessage("请先添加组织取材编号！");
			return;
		}

		//组织取编号列表
		List<String> sliceCodeList = new ArrayList<String>(sliceCode2SliceTreeItemMap.keySet());
		//排序
		if(sliceCodeType == 0){
			Collections.sort(sliceCodeList);
		}else{
			Collections.sort(sliceCodeList, new Comparator<String>(){
				
				@Override
				public int compare(String o1, String o2) {
					int num1 = Integer.parseInt(o1.replaceAll("T", ""));
					int num2 = Integer.parseInt(o2.replaceAll("T", ""));
					return num1 - num2;
				}
				
			});
			
		}
		//脏器列表
		List<Map<String,Object>> visceraMapList = new ArrayList<Map<String,Object>>();
		for(String sliceCode:sliceCodeList){
			TreeItem<String> sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
			ObservableList<TreeItem<String>> children = sliceTreeItem.getChildren();
			for(TreeItem<String> child:children){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) child.getGraphic().getUserData();
				map.put("sliceCode", sliceCode);
				visceraMapList.add(map);
			}
		}
		if(null == tblTissueSliceIndex){
			tblTissueSliceIndex = new TblTissueSliceIndex();
			tblTissueSliceIndex.setSliceCodeType(sliceCodeType);
			tblTissueSliceIndex.setStudyNo(studyNo);
			
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService().addOneFor02(tblTissueSliceIndex,sliceCodeList,visceraMapList);
		}else{
			BaseService.getInstance().getTblTissueSliceIndexService().updateOneFor02(tblTissueSliceIndex,sliceCodeList,visceraMapList);
		}
		
		//2.检查需镜检的脏器是否全部
		if(sliceCodeType == 0){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService().getRemainVisceraMapListByStudyNo(studyNo);
			if(null != mapList && mapList.size() > 0){
				Map<String,Object> map = mapList.get(0);
				String visceraName = (String) map.get("visceraName");
				showErrorMessage("脏器（"+visceraName+"）未组织取材编号！");
				return;
			}
		}
		//3.签字保存
		//签字窗口
		SignMeFrame signMeFrame = new SignMeFrame("");
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("签字确认");
		try {
			signMeFrame.start(stage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//签字通过
		if("OK".equals(SignMeFrame.getType())){
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService()
					.signTissueSliceIndex(tblTissueSliceIndex,SaveUserInfo.getRealName());
			if(sliceCodeType == 0){
				updateState();
			}else{
				updateCombBox();
				comboBox.getSelectionModel().select(data_comboBox.size()-2);
			}
			exitBtn.setText("关闭");
			showMessage("签字成功！");
		}
	}
	/**保存
	 * @param event
	 */
	@FXML
	private void onSaveBtnAction(ActionEvent event){
		//3.组织取材编号检查
		if(rootNode_tissueSliceTree.getChildren().size() <1){
			showErrorMessage("请先添加组织取材编号！");
			return;
		}

		//组织取编号列表
		List<String> sliceCodeList = new ArrayList<String>(sliceCode2SliceTreeItemMap.keySet());
		//排序
		if(sliceCodeType == 0){
			Collections.sort(sliceCodeList);
		}else{
			Collections.sort(sliceCodeList, new Comparator<String>(){
				
				@Override
				public int compare(String o1, String o2) {
					int num1 = Integer.parseInt(o1.replaceAll("T", ""));
					int num2 = Integer.parseInt(o2.replaceAll("T", ""));
					return num1 - num2;
				}
				
			});
			
		}
		//脏器列表
		List<Map<String,Object>> visceraMapList = new ArrayList<Map<String,Object>>();
		for(String sliceCode:sliceCodeList){
			TreeItem<String> sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
			ObservableList<TreeItem<String>> children = sliceTreeItem.getChildren();
			for(TreeItem<String> child:children){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) child.getGraphic().getUserData();
				map.put("sliceCode", sliceCode);
				visceraMapList.add(map);
			}
		}
		if(null == tblTissueSliceIndex){
			tblTissueSliceIndex = new TblTissueSliceIndex();
			tblTissueSliceIndex.setSliceCodeType(sliceCodeType);
			tblTissueSliceIndex.setStudyNo(studyNo);
			
			tblTissueSliceIndex = BaseService.getInstance().getTblTissueSliceIndexService().addOneFor02(tblTissueSliceIndex,sliceCodeList,visceraMapList);
		}else{
			BaseService.getInstance().getTblTissueSliceIndexService().updateOneFor02(tblTissueSliceIndex,sliceCodeList,visceraMapList);
		}
		if(sliceCodeType == 2){
			updateCombBox();
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
		ObservableList<TreeItem<String>> children = rootNode_tissueSliceTree.getChildren();
		if(null == children || children.size() < 1){
			showErrorMessage("切片编号列表为空！");
			return;
		}
		
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
//    	map.put("studyNo", studyNo);
        map.put("studyNo", StringUtil.studyNoRemoveFN(studyNo));
        map.put("animalType", animalType);
        
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> fieldMap = null;
        String visceraOrTissueName = "";
        boolean isFirstLeaf = true;
        for(TreeItem<String> obj:children){
        	fieldMap = new HashMap<String,Object>();
        	fieldMap.put("sliceCode", obj.getGraphic().getUserData());
        	ObservableList<TreeItem<String>> subChildren = obj.getChildren();
        	isFirstLeaf = true;
        	for(TreeItem<String> subChild:subChildren){
        		if(isFirstLeaf){
        			visceraOrTissueName = ((Label)subChild.getGraphic()).getText();
        			isFirstLeaf= false;
        		}else{
        			visceraOrTissueName += "、"+((Label)subChild.getGraphic()).getText();
        		}
        	}
        	fieldMap.put("visceraOrTissueName",visceraOrTissueName);
        	mapList.add(fieldMap);
        }
  		try {
			jp = JasperFillManager.fillReport(jr, map,new ClassBeanPathDataSource(mapList));
		} catch (JRException e) {
			e.printStackTrace();
		}     
        Main.getInstance().openJFrame(jp, "组织取材编号表");
	}
	/**添加到所选蜡块
	 * @param event
	 */
	@FXML
	private void onAddToSelectedSliceBtnAction(ActionEvent event){
		//1.选择蜡块
		TreeItem<String> sliceTreeItem = null;//待添加的蜡块节点
		TreeItem<String> selectedSliceItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null == selectedSliceItem){
			showErrorMessage("请先选择蜡块！");
			return;
		}else{
			if(selectedSliceItem.isLeaf()){
				sliceTreeItem = selectedSliceItem.getParent();
			}else{
				sliceTreeItem = selectedSliceItem;
			}
		}
		//2.选择脏器
		ObservableList<TreeItem<String>> selectedVisceraItems = visceraTree.getSelectionModel().getSelectedItems();
		if(null == selectedVisceraItems || selectedVisceraItems.size() == 0){
			showErrorMessage("请先选择脏器！");
			return;
		}
		//3.子脏器被选中，其父脏器未被选中，且父脏器未添加到蜡块中。
		//  父脏器被选中，a.无子脏器 通过   b.有子脏器，子脏器未被选中 且未添加到蜡块中
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
			TreeItem<String> parentItem = item.getParent();
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraName = (String) map.get("subVisceraName");
			if("2".equals(level)){
				//子脏器
				if(selectedVisceraItems.contains(parentItem)){
					treeScrollTo(visceraTree,parentItem);
					showErrorMessage(visceraName+" 与 "+subVisceraName+" 不能同时添加到蜡块中！");
					return;
				}else{
					if(visceraCodeSet.contains(visceraCode)){
						treeScrollTo(visceraTree,parentItem);
						showErrorMessage(visceraName+" 与 "+subVisceraName+" 不能同时添加到蜡块中！");
						return;
					}
				}
			}else{
				//一级脏器
				if(!item.isLeaf()){
					//蜡块中无父脏器编号 
					ObservableList<TreeItem<String>> children = rootNode_tissueSliceTree.getChildren();
					for(TreeItem<String> sliceItem:children){
						ObservableList<TreeItem<String>> viscerChildren = sliceItem.getChildren();
						for(TreeItem<String> visceraItem:viscerChildren){
							@SuppressWarnings("unchecked")
							Map<String,Object> map2 = (Map<String, Object>) visceraItem.getGraphic().getUserData();
							String currentVisceraCode = (String) map2.get("visceraCode");
							if(visceraCode.equals(currentVisceraCode)){
								String currentSubVisceraName = (String) map2.get("subVisceraName");
								showErrorMessage(visceraName+" 与 "+currentSubVisceraName+" 不能同时添加到蜡块中！");
								return;
							}
						}
					}
				}
			}
		}
		//
		//4.添加到蜡块中，添加两set
		String sliceCode = (String) sliceTreeItem.getGraphic().getUserData();
		TreeItem<String> lastItem = null;
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
//			TreeItem<String> parentItem = item.getParent();
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraName = (String) map.get("subVisceraName");
			String subVisceraCode = (String) map.get("subVisceraCode");
			
			
			TreeItem<String> leafNode = new TreeItem<String>("");
			Label leafLabel = new Label();
			if("1".equals(level)){
				//一级脏器
				leafLabel.setText(visceraName);
				leafLabel.setId(1+"");
				visceraCodeSet.add(visceraCode);
				map.remove("subVisceraName");
				map.remove("subVisceraCode");
			}else{
				leafLabel.setText(subVisceraName);
				leafLabel.setId(2+"");
				subVisceraCodeSet.add(subVisceraCode);
			}
			map.put("sliceCode", sliceCode);
			leafLabel.setUserData(map);
			leafNode.setGraphic(leafLabel);
			
			sliceTreeItem.getChildren().add(leafNode);
			lastItem = leafNode;
		}
		//选最后一个，
		tissueSliceTree.getSelectionModel().select(lastItem);
		//tissueSliceTree树滚到对应节点
		treeScrollTo(tissueSliceTree,lastItem);
		//5.删除脏器列表中被选中的，visceraCode2TreeItemMap移除对应项
		//待删除项
		List<TreeItem<String>> itemList = new ArrayList<TreeItem<String>>();
		//待删除项-> 父节点
		Map<TreeItem<String>,TreeItem<String>> treeitemMap = new HashMap<TreeItem<String>,TreeItem<String>>();
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
			String visceraCode = (String) map.get("visceraCode");
			
			if("1".equals(level)){
				//一级脏器
//						rootNode.getChildren().remove(item);
				itemList.add(item);
				treeitemMap.put(item, rootNode);
				visceraCode2TreeItemMap.remove(visceraCode);
			}else{
				TreeItem<String> parent = item.getParent();
				if(parent.getChildren().size() == 1){
//							rootNode.getChildren().remove(parent);
					itemList.add(parent);
					treeitemMap.put(parent, rootNode);
					visceraCode2TreeItemMap.remove(visceraCode);
				}else{
//							parent.getChildren().remove(item);
					itemList.add(item);
					treeitemMap.put(item, parent);
				}
			}
		}
		visceraTree.getSelectionModel().clearSelection();
		for(TreeItem<String> item:itemList){
			TreeItem<String> parentNode = treeitemMap.get(item);
			parentNode.getChildren().remove(item);
			if(parentNode.getChildren().size() == 0 && !parentNode.equals(rootNode)){
				rootNode.getChildren().remove(parentNode);
				@SuppressWarnings("unchecked")
				Map<String,Object> currentMap =(Map<String, Object>) parentNode.getGraphic().getUserData();
				String visceraCode = (String) currentMap.get("visceraCode");
				visceraCode2TreeItemMap.remove(visceraCode);
			}
		}
		visceraTree.getSelectionModel().clearSelection();
		
		exitBtn.setText("取消");
	}
	

	/**树滚到对应节点
	 * @param parentItem
	 */
	private void treeScrollTo(TreeView<String> tree,TreeItem<String> item) {
		int index = -1;
		ObservableList<TreeItem<String>> children = tree.getRoot().getChildren();
		if(null != children){
			for(TreeItem<String> child:children){
				index++;
				if(child.equals(item)){
					break;
				}
				if(!child.isLeaf()){
					ObservableList<TreeItem<String>> children2 = child.getChildren();
					if(null != children2){
						for(TreeItem<String> child2:children2){
							index++;
							if(child2.equals(item)){
								break;
							}
						}
					}
				}
			}
		}
		tree.scrollTo(index);
	}

	/**添加到新蜡块
	 * @param event
	 */
	@FXML
	private void onAddToNewSliceBtnAction(ActionEvent event){
		
		//1.选择脏器
		ObservableList<TreeItem<String>> selectedVisceraItems = visceraTree.getSelectionModel().getSelectedItems();
		if(null == selectedVisceraItems || selectedVisceraItems.size() == 0){
			showErrorMessage("请先选择脏器！");
			return;
		}
		//2.子脏器被选中，其父脏器未被选中，且父脏器未添加到蜡块中。
		//  父脏器被选中，a.无子脏器 通过   b.有子脏器，蜡块中无父脏器编号
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
			TreeItem<String> parentItem = item.getParent();
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraName = (String) map.get("subVisceraName");
			if("2".equals(level)){
				//子脏器
				if(selectedVisceraItems.contains(parentItem)){
					treeScrollTo(visceraTree,parentItem);
					showErrorMessage(visceraName+" 与 "+subVisceraName+" 不能同时添加到蜡块中！");
					return;
				}else{
					if(visceraCodeSet.contains(visceraCode)){
						treeScrollTo(visceraTree,parentItem);
						showErrorMessage(visceraName+" 与 "+subVisceraName+" 不能同时添加到蜡块中！");
						return;
					}
				}
			}else{
				//一级脏器
				if(!item.isLeaf()){
					//蜡块中无父脏器编号 TODO
					ObservableList<TreeItem<String>> children = rootNode_tissueSliceTree.getChildren();
					for(TreeItem<String> sliceItem:children){
						ObservableList<TreeItem<String>> viscerChildren = sliceItem.getChildren();
						for(TreeItem<String> visceraItem:viscerChildren){
							@SuppressWarnings("unchecked")
							Map<String,Object> map2 = (Map<String, Object>) visceraItem.getGraphic().getUserData();
							String currentVisceraCode = (String) map2.get("visceraCode");
							if(visceraCode.equals(currentVisceraCode)){
								String currentSubVisceraName = (String) map2.get("subVisceraName");
								showErrorMessage(visceraName+" 与 "+currentSubVisceraName+" 不能同时添加到蜡块中！");
								return;
							}
						}
					}
				}
			}
		}
		//3.新建蜡块节点
		String sliceCode = "";
		if(sliceCodeType == 0){
			sliceCode = rootNode_tissueSliceTree.getChildren().size()+1+"";
		}else if(sliceCodeType == 2 ){
			sliceCode = "T"+(rootNode_tissueSliceTree.getChildren().size()+1+sliceCodeNum);
		}
		TreeItem<String> sliceTreeItem = null;
		sliceTreeItem = new TreeItem<String>("");
		Label sliceLabel = new Label("蜡块"+sliceCode);
		//label的UserData存放的是sliceCode
		sliceLabel.setUserData(sliceCode);
		sliceTreeItem.setGraphic(sliceLabel);
		sliceTreeItem.setExpanded(true);
		rootNode_tissueSliceTree.getChildren().add(sliceTreeItem);
		sliceCode2SliceTreeItemMap.put(sliceCode, sliceTreeItem);
		
		//4.添加到蜡块中，添加两set
//		String sliceCode = (String) sliceTreeItem.getGraphic().getUserData();
		TreeItem<String> lastItem = null;
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
//			TreeItem<String> parentItem = item.getParent();
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraName = (String) map.get("subVisceraName");
			String subVisceraCode = (String) map.get("subVisceraCode");
			
			
			TreeItem<String> leafNode = new TreeItem<String>("");
			Label leafLabel = new Label();
			if("1".equals(level)){
				//一级脏器
				leafLabel.setText(visceraName);
				leafLabel.setId(1+"");
				visceraCodeSet.add(visceraCode);
				map.remove("subVisceraName");
				map.remove("subVisceraCode");
			}else{
				leafLabel.setText(subVisceraName);
				leafLabel.setId(2+"");
				subVisceraCodeSet.add(subVisceraCode);
			}
			map.put("sliceCode", sliceCode);
			leafLabel.setUserData(map);
			leafNode.setGraphic(leafLabel);
			
			sliceTreeItem.getChildren().add(leafNode);
			lastItem = leafNode;
		}
		//选最后一个，
		tissueSliceTree.getSelectionModel().select(lastItem);
		//tissueSliceTree树滚到对应节点
		treeScrollTo(tissueSliceTree,lastItem);
		//5.删除脏器列表中被选中的，visceraCode2TreeItemMap移除对应项
//		visceraCode2TreeItemMap.remove(key);
		//待删除项
		List<TreeItem<String>> itemList = new ArrayList<TreeItem<String>>();
		//待删除项-> 父节点
		Map<TreeItem<String>,TreeItem<String>> treeitemMap = new HashMap<TreeItem<String>,TreeItem<String>>();
		for(TreeItem<String> item:selectedVisceraItems){
			Label label = (Label) item.getGraphic();
			String level = label.getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) label.getUserData();
			String visceraCode = (String) map.get("visceraCode");
			
			if("1".equals(level)){
				//一级脏器
//				rootNode.getChildren().remove(item);
				itemList.add(item);
				treeitemMap.put(item, rootNode);
				visceraCode2TreeItemMap.remove(visceraCode);
			}else{
				TreeItem<String> parent = item.getParent();
				if(parent.getChildren().size() == 1){
//					rootNode.getChildren().remove(parent);
					itemList.add(parent);
					treeitemMap.put(parent, rootNode);
					visceraCode2TreeItemMap.remove(visceraCode);
				}else{
//					parent.getChildren().remove(item);
					itemList.add(item);
					treeitemMap.put(item, parent);
				}
			}
		}
		visceraTree.getSelectionModel().clearSelection();
		for(TreeItem<String> item:itemList){
			TreeItem<String>  parentNode = null;
			parentNode = treeitemMap.get(item);
			parentNode.getChildren().remove(item);
			if(parentNode.getChildren().size() == 0 && !parentNode.equals(rootNode)){
				rootNode.getChildren().remove(parentNode);
				@SuppressWarnings("unchecked")
				Map<String,Object> currentMap =(Map<String, Object>) parentNode.getGraphic().getUserData();
				String visceraCode = (String) currentMap.get("visceraCode");
				visceraCode2TreeItemMap.remove(visceraCode);
			}
		}
		exitBtn.setText("取消");
	}
	/**删除
	 * @param event
	 */
	@FXML
	private void onDeleteBtnAction(ActionEvent event){
		//1.选择蜡块或脏器  
		TreeItem<String> selectedItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择蜡块或脏器！");
			return;
		}
		//待恢复的脏器列表
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		String currentVisceraName = "";
		
		if(selectedItem.isLeaf()){
			//2.选中的是脏器
			TreeItem<String> parentNode = selectedItem.getParent();
			String level = selectedItem.getGraphic().getId();
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String, Object>) selectedItem.getGraphic().getUserData();
			if("1".equals(level)){
				//一级脏器
				String visceraCode = (String) map.get("visceraCode");
				currentVisceraName = (String) map.get("visceraName");
				visceraCodeSet.remove(visceraCode);
				List<Map<String,Object>> subMapList = BaseService.getInstance().getTblTissueSliceIndexService().getVisceraMapListByStudyNoVisceraCode(studyNo,visceraCode);
				mapList.addAll(subMapList);
			}else{
				String subVisceraCode = (String) map.get("subVisceraCode");
				subVisceraCodeSet.remove(subVisceraCode);
				currentVisceraName = (String) map.get("subVisceraName");
				mapList.add(map);
			}
			parentNode.getChildren().remove(selectedItem);
			
			if(parentNode.getChildren().size() < 1){
				String sliceCode = (String) parentNode.getGraphic().getUserData();
				sliceCode2SliceTreeItemMap.remove(sliceCode);
				rootNode_tissueSliceTree.getChildren().remove(parentNode);
				//纠正编号错误
				correctSliceCode();
			}
		}else{
			//3.选中的是蜡块节点
			ObservableList<TreeItem<String>> children = selectedItem.getChildren();
			//暂时保存一级脏器，待最后统一查询子脏器
			List<String> visceraCodeList = new ArrayList<String>();
			for(TreeItem<String> child:children){
				String level = child.getGraphic().getId();
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) child.getGraphic().getUserData();
				if("1".equals(level)){
					//一级脏器
					String visceraCode = (String) map.get("visceraCode");
					visceraCodeSet.remove(visceraCode);
					currentVisceraName = (String) map.get("visceraName");
//					String subVisceraCode = (String) map.get("subVisceraCode");
					visceraCodeList.add(visceraCode);
				}else{
					String subVisceraCode = (String) map.get("subVisceraCode");
					subVisceraCodeSet.remove(subVisceraCode);
					currentVisceraName = (String) map.get("subVisceraName");
					mapList.add(map);
				}
			}
			if(null != visceraCodeList && visceraCodeList.size() > 0){
				List<Map<String,Object>> subMapList = BaseService.getInstance().getTblTissueSliceIndexService().getVisceraMapListByStudyNoVisceraCodeList(studyNo,visceraCodeList);
				mapList.addAll(subMapList);
			}
			String sliceCode = (String) selectedItem.getGraphic().getUserData();
			sliceCode2SliceTreeItemMap.remove(sliceCode);
			rootNode_tissueSliceTree.getChildren().remove(selectedItem);
			//纠正编号错误
			correctSliceCode();
		}
		
		//4.取消选择，更新脏器树
		tissueSliceTree.getSelectionModel().clearSelection();
		addOneItemAndSelectIt_2(currentVisceraName,mapList);
		exitBtn.setText("取消");
	}
	
	/**
	 * 纠正编号错误
	 */
	private void correctSliceCode(){
		ObservableList<TreeItem<String>> children = rootNode_tissueSliceTree.getChildren();
		if(rootNode_tissueSliceTree.getChildren().size() > 0){
			int i = 1;
			String sliceCode = "";
			for(TreeItem<String> child:children){
				if(sliceCodeType == 0){
					sliceCode = i+"";
				}else{
					sliceCode= "T"+(sliceCodeNum+i);
				}
				if(!sliceCode.equals(child.getGraphic().getUserData())){
					String oldSliceCode = (String) child.getGraphic().getUserData();
					sliceCode2SliceTreeItemMap.remove(oldSliceCode);
					child.getGraphic().setUserData(sliceCode);
					Label label = (Label) child.getGraphic();
					label.setText("蜡块"+sliceCode);
					sliceCode2SliceTreeItemMap.put(sliceCode, child);
				}
				i++;
			}
		}
	}
	
	
	/**上移
	 * @param event
	 */
	@FXML
	private void onUPBtnAction(ActionEvent event){
		//1.选择蜡块或脏器  
		TreeItem<String> selectedItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择蜡块或脏器！");
			return;
		}
		//2.当前蜡块
		TreeItem<String> currentItem = null; 
		if(selectedItem.isLeaf()){
			currentItem = selectedItem.getParent();
		}else{
			currentItem = selectedItem;
		}
		//3.上一个蜡块
		TreeItem<String> preItem = null; 
		int index = rootNode_tissueSliceTree.getChildren().indexOf(currentItem);
		if(index > 0){
			preItem = rootNode_tissueSliceTree.getChildren().get(index-1);
			String preSliceCode = (String) preItem.getGraphic().getUserData();
			String currentSliceCode = (String) currentItem.getGraphic().getUserData();
			
			//交换
			TreeItem<String> newPreItem = new TreeItem<String>("");
			Label newPreLabel = new  Label("蜡块"+preSliceCode);
			newPreLabel.setUserData(preSliceCode);
			newPreItem.setGraphic(newPreLabel);
			for(TreeItem<String> item:currentItem.getChildren()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) item.getGraphic().getUserData();
				map.put("sliceCode", preSliceCode);
				item.getGraphic().setUserData(map);
				newPreItem.getChildren().add(item);
			}
			sliceCode2SliceTreeItemMap.put(preSliceCode, newPreItem);
			
			TreeItem<String> newCurrentItem = new TreeItem<String>("");
			newCurrentItem.setExpanded(true);
			Label newCurrentLabel = new  Label("蜡块"+currentSliceCode);
			newCurrentLabel.setUserData(currentSliceCode);
			newCurrentItem.setGraphic(newCurrentLabel);
			for(TreeItem<String> item:preItem.getChildren()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) item.getGraphic().getUserData();
				map.put("sliceCode", currentSliceCode);
				item.getGraphic().setUserData(map);
				newCurrentItem.getChildren().add(item);
			}
			sliceCode2SliceTreeItemMap.put(currentSliceCode, newCurrentItem);
			rootNode_tissueSliceTree.getChildren().set(index, newCurrentItem);
			rootNode_tissueSliceTree.getChildren().set(index-1, newPreItem);
			tissueSliceTree.getSelectionModel().select(newPreItem);
		}
		
		exitBtn.setText("取消");
	}
	/**下移
	 * @param event
	 */
	@FXML
	private void onDownBtnAction(ActionEvent event){
		//1.选择蜡块或脏器  
		TreeItem<String> selectedItem = tissueSliceTree.getSelectionModel().getSelectedItem();
		if(null == selectedItem){
			showErrorMessage("请先选择蜡块或脏器！");
			return;
		}
		//2.当前蜡块
		TreeItem<String> currentItem = null; 
		if(selectedItem.isLeaf()){
			currentItem = selectedItem.getParent();
		}else{
			currentItem = selectedItem;
		}
		//3.下一个蜡块
		TreeItem<String> nextItem = null; 
		int index = rootNode_tissueSliceTree.getChildren().indexOf(currentItem);
		if(index < rootNode_tissueSliceTree.getChildren().size()-1){
			nextItem = rootNode_tissueSliceTree.getChildren().get(index+1);
			String nextSliceCode = (String) nextItem.getGraphic().getUserData();
			String currentSliceCode = (String) currentItem.getGraphic().getUserData();
			
			//交换
			TreeItem<String> newNextItem = new TreeItem<String>("");
			Label newNextLabel = new  Label("蜡块"+nextSliceCode);
			newNextLabel.setUserData(nextSliceCode);
			newNextItem.setGraphic(newNextLabel);
			for(TreeItem<String> item:currentItem.getChildren()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) item.getGraphic().getUserData();
				map.put("sliceCode", nextSliceCode);
				item.getGraphic().setUserData(map);
				newNextItem.getChildren().add(item);
			}
			sliceCode2SliceTreeItemMap.put(nextSliceCode, newNextItem);
			
			TreeItem<String> newCurrentItem = new TreeItem<String>("");
			newCurrentItem.setExpanded(true);
			Label newCurrentLabel = new  Label("蜡块"+currentSliceCode);
			newCurrentLabel.setUserData(currentSliceCode);
			newCurrentItem.setGraphic(newCurrentLabel);
			for(TreeItem<String> item:nextItem.getChildren()){
				@SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) item.getGraphic().getUserData();
				map.put("sliceCode", currentSliceCode);
				item.getGraphic().setUserData(map);
				newCurrentItem.getChildren().add(item);
			}
			sliceCode2SliceTreeItemMap.put(currentSliceCode, newCurrentItem);
			rootNode_tissueSliceTree.getChildren().set(index, newCurrentItem);
			rootNode_tissueSliceTree.getChildren().set(index+1, newNextItem);
			tissueSliceTree.getSelectionModel().select(newNextItem);
		}
		
		exitBtn.setText("取消");
	}
	
	/**加载数据（仅限常规组织取材切片或追加）
	 * @param studyNo
	 * @param sliceCodeType
	 */
	public void loadData(String studyNo,int sliceCodeType) {
		
		//1.赋值sliceCodeType，studyNo
		this.studyNo = studyNo;
		this.sliceCodeType = sliceCodeType;
		if(sliceCodeType == 2){
			sliceCodeNum = BaseService.getInstance().getTblTissueSliceIndexService().getSliceCodeNum(studyNo);
		}
		//2.更新几个Label 及 studyNo赋值
		updateLabel();
		
		//3.更新ComboBox及选中某行
		updateCombBox();
		
		//4.更新exitBtn 的Text值
		updateExitBtnText();

	}

	/**
	 * 更新exitBtn 的Text值
	 */
	private void updateExitBtnText() {
		if(null == tblTissueSliceIndex){
			if(rootNode_tissueSliceTree.getChildren().size() > 0){
				exitBtn.setText("取消");
			}else{
				exitBtn.setText("关闭");
			}
		}else{
			exitBtn.setText("关闭");
		}
	}

	/**
	 * 更新页面值（包括按钮、多选框、表格、树等值及状态）
	 */
	protected void updateData() {
		//1.恢复成初始状态
		backButtonCheckBoxState();
		
		//4.更新组织取材编号树
		updateTissueSliceTree();
		
		//5.更新脏器树
		updateVisceraTree();
		
		//7.更新整个面变状态，签字后不可操作
		updateState();
		
	}
	
	
	/**
	 * 更新整个面变状态，签字后不可操作
	 */
	private void updateState() {
//		TblAnatomyTask task = BaseService.getInstance().getTblAnatomyTaskService().getById(taskId);
		if(null != tblTissueSliceIndex && null != tblTissueSliceIndex.getOperatorSign() 
				&& !"".equals(tblTissueSliceIndex.getOperatorSign())){
			signBtn.setDisable(true);
			saveBtn.setDisable(true);
			
			showHistopathVisceraCheckBox.setDisable(true);
			addToSelectedSliceBtn.setDisable(true);
			addToNewSliceBtn.setDisable(true);
			deleteBtn.setDisable(true);
			upBtn.setDisable(true);
			downBtn.setDisable(true);
			otherBtn.setDisable(true);
		}
	}


	/**
	 * 更新组织取材编号树
	 */
	private void updateTissueSliceTree() {
		tissueSliceTree.getSelectionModel().clearSelection();
		rootNode_tissueSliceTree.getChildren().clear();
		sliceCode2SliceTreeItemMap.clear();
		visceraCodeSet.clear();
		subVisceraCodeSet.clear();
		
		if(null != tblTissueSliceIndex){
			List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService()
					.getSliceSnVisceraMapListByIndexId(tblTissueSliceIndex.getId());
			if(null != mapList){
				for(Map<String,Object> map:mapList){
					String sliceCode = (String) map.get("sliceCode");
//					Integer visceraType = (Integer) map.get("visceraType");
					String visceraCode = (String) map.get("visceraCode");
					String visceraName = (String) map.get("visceraName");
					String subVisceraCode = (String) map.get("subVisceraCode");
					String subVisceraName = (String) map.get("subVisceraName");
//					Integer sn = (Integer) map.get("sn");
//					Integer subSn = (Integer) map.get("subSn");
					TreeItem<String> sliceTreeItem = null;
					if(sliceCode2SliceTreeItemMap.keySet().contains(sliceCode)){
						sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
					}else{
						sliceTreeItem = new TreeItem<String>("");
						Label sliceLabel = new Label("蜡块"+sliceCode);
						//label的UserData存放的是sliceCode
						sliceLabel.setUserData(sliceCode);
						sliceTreeItem.setGraphic(sliceLabel);
						sliceTreeItem.setExpanded(true);
						rootNode_tissueSliceTree.getChildren().add(sliceTreeItem);
						sliceCode2SliceTreeItemMap.put(sliceCode, sliceTreeItem);
					}
					TreeItem<String> leafNode = new TreeItem<String>("");
					Label leafLabel = new Label();
					if(null != subVisceraCode && !"".equals(subVisceraCode)){
						leafLabel.setText(subVisceraName);
						//二级脏器
						leafLabel.setId(2+"");
						subVisceraCodeSet.add(subVisceraCode);
					}else{
						leafLabel.setText(visceraName);
						leafLabel.setId(1+"");
						visceraCodeSet.add(visceraCode);
					}
					leafLabel.setUserData(map);
					leafNode.setGraphic(leafLabel);
					
					sliceTreeItem.getChildren().add(leafNode);
				}
			}
		}else{
			if(sliceCodeType == 0){
				//加载同一课题下，相近的常规组织取材编号对应的任务Id
				String preTaskId = BaseService.getInstance().getTblTissueSliceIndexService()
						.getPreTaskIdByStudyNo(studyNo);
				
				//sliceCode,visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn 
				List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService()
						.getSliceSnVisceraMapListByTaskId(preTaskId);
				if(null != mapList){
					for(Map<String,Object> map:mapList){
						String sliceCode = (String) map.get("sliceCode");
//						Integer visceraType = (Integer) map.get("visceraType");
						String visceraCode = (String) map.get("visceraCode");
						String visceraName = (String) map.get("visceraName");
						String subVisceraCode = (String) map.get("subVisceraCode");
						String subVisceraName = (String) map.get("subVisceraName");
//						Integer sn = (Integer) map.get("sn");
//						Integer subSn = (Integer) map.get("subSn");
						TreeItem<String> sliceTreeItem = null;
						if(sliceCode2SliceTreeItemMap.keySet().contains(sliceCode)){
							sliceTreeItem = sliceCode2SliceTreeItemMap.get(sliceCode);
						}else{
							sliceTreeItem = new TreeItem<String>("");
							Label sliceLabel = new Label("蜡块"+sliceCode);
							//label的UserData存放的是sliceCode
							sliceLabel.setUserData(sliceCode);
							sliceTreeItem.setGraphic(sliceLabel);
							sliceTreeItem.setExpanded(true);
							rootNode_tissueSliceTree.getChildren().add(sliceTreeItem);
							sliceCode2SliceTreeItemMap.put(sliceCode, sliceTreeItem);
						}
						TreeItem<String> leafNode = new TreeItem<String>("");
						Label leafLabel = new Label();
						if(null != subVisceraCode && !"".equals(subVisceraCode)){
							leafLabel.setText(subVisceraName);
							//二级脏器
							leafLabel.setId(2+"");
							subVisceraCodeSet.add(subVisceraCode);
						}else{
							leafLabel.setText(visceraName);
							leafLabel.setId(1+"");
							visceraCodeSet.add(visceraCode);
						}
						leafLabel.setUserData(map);
						leafNode.setGraphic(leafLabel);
						
						sliceTreeItem.getChildren().add(leafNode);
					}
				}
			}
		}
		
		
	}

	/**
	 * 更新几个Label
	 */
	private void updateLabel() {

		TblStudyPlan tblStudyPlan = BaseService.getInstance().getTblStudyPlanService().getByStudyNo(studyNo);
		studyNoLabel.setText(studyNo);
		if(null != tblStudyPlan){
			sdLabel.setText(tblStudyPlan.getStudydirector());
			animalTypeLabel.setText(tblStudyPlan.getAnimalType() != null ? tblStudyPlan.getAnimalType():"");
		}else{
			sdLabel.setText("");
			animalTypeLabel.setText("");
		}
	}

	
	/**
	 * 恢复成初始状态
	 */
	public void backButtonCheckBoxState(){
		showHistopathVisceraCheckBox.setSelected(true);
		
		signBtn.setDisable(false);
		saveBtn.setDisable(false);
		
		showHistopathVisceraCheckBox.setDisable(false);
		addToSelectedSliceBtn.setDisable(false);
		addToNewSliceBtn.setDisable(false);
		deleteBtn.setDisable(false);
		upBtn.setDisable(false);
		downBtn.setDisable(false);
		otherBtn.setDisable(false);
	}
	
	/**根据解剖任务Id，更新脏器树
	 * @param string
	 */
	private void updateVisceraTree() {
		visceraTree.getSelectionModel().clearSelection();
		rootNode.getChildren().clear();
		visceraCode2TreeItemMap.clear();
		//查询专题对应病理计划的脏器列表，若无病理计划，则 查询解剖申请的脏器列表的合集（剖检 或镜检）， 
		//		visceraType,visceraCode,visceraName,subVisceraCode,subVisceraName,sn,subSn
		List<Map<String,Object>> mapList = BaseService.getInstance().getTblTissueSliceIndexService()
				.getVisceraMapListByStudyNo(studyNo,showHistopathVisceraCheckBox.isSelected());
		if(null != mapList){
			for(Map<String,Object> map:mapList){
//				Integer visceraType = (Integer) map.get("visceraType");
				String visceraCode = (String) map.get("visceraCode");
				String visceraName = (String) map.get("visceraName");
				String subVisceraCode = (String) map.get("subVisceraCode");
				String subVisceraName = (String) map.get("subVisceraName");
//				Integer sn = (Integer) map.get("sn");
//				Integer subSn = (Integer) map.get("subSn");
				TreeItem<String> treeItem = null;
				if(visceraCodeSet.contains(visceraCode)){
					continue;
				}
				
				TreeItem<String> leafNode = new TreeItem<String>("");
				Label leafLabel = new Label();
				//有子脏器	TODO
				if(null != subVisceraCode && !"".equals(subVisceraCode)){
					if(subVisceraCodeSet.contains(subVisceraCode)){
						continue;
					}else{
						if(visceraCode2TreeItemMap.keySet().contains(visceraCode)){
							treeItem = visceraCode2TreeItemMap.get(visceraCode);
						}else{
							treeItem = new TreeItem<String>("");
							Label visceraNameLabel = new Label(visceraName);
							//label的UserData存放的是map
							visceraNameLabel.setUserData(map);
							//一级脏器
							visceraNameLabel.setId(1+"");
							treeItem.setGraphic(visceraNameLabel);
							treeItem.setExpanded(true);
							rootNode.getChildren().add(treeItem);
							visceraCode2TreeItemMap.put(visceraCode, treeItem);
						}
					}
					leafLabel.setText(subVisceraName);
					//二级脏器
					leafLabel.setId(2+"");
					leafLabel.setUserData(map);
					leafNode.setGraphic(leafLabel);
					treeItem.getChildren().add(leafNode);
				}else{
					if(visceraCode2TreeItemMap.keySet().contains(visceraCode)){
						treeItem = visceraCode2TreeItemMap.get(visceraCode);
					}else{
						treeItem = new TreeItem<String>("");
						Label visceraNameLabel = new Label(visceraName);
						//label的UserData存放的是map
						visceraNameLabel.setUserData(map);
						//一级脏器
						visceraNameLabel.setId(1+"");
						treeItem.setGraphic(visceraNameLabel);
						treeItem.setExpanded(true);
						rootNode.getChildren().add(treeItem);
						visceraCode2TreeItemMap.put(visceraCode, treeItem);
					}
				}
				
			}
		}
	}
	/**viseraTree增加一脏器（）并选中它(判断是否已存在或已切片)；成功返回true，失败返回false：msg
	 * @param visceraName
	 * @param mapList
	 * @return
	 */
	public Json addOneItemAndSelectIt(String currentVisceraName,String currentVisceraCode, List<Map<String, Object>> mapList) {
		Json json = new Json();
		if(null != mapList && null != currentVisceraName && null != currentVisceraCode){
			boolean isExist = false;
			
			if(visceraCode2TreeItemMap.keySet().contains(currentVisceraCode) ||
				visceraCodeSet.contains(currentVisceraCode) || 
				subVisceraCodeSet.contains(currentVisceraCode)){
				json.setMsg("脏器（"+currentVisceraName+"）已存在!");
				isExist = true;
			}else{
				String visceraCode = (String) mapList.get(0).get("visceraCode");
				if(visceraCode2TreeItemMap.keySet().contains(visceraCode) ||
						visceraCodeSet.contains(visceraCode) ){
					json.setMsg("脏器（"+currentVisceraName+"）已存在!");
					isExist = true;
				}
			}
			
			if(!isExist){
				//viseraTree增加一数据并选中它
				addOneItemAndSelectIt_2(currentVisceraName,mapList);
				json.setSuccess(true);
			}
		}
		return json;
	}
	
	/**viseraTree增加一脏器并选中它
	 * @param currentVisceraName
	 * @param map
	 */
	public void addOneItemAndSelectIt_2(String currentVisceraName, List<Map<String, Object>> mapList) {
		TreeItem<String> selectedItem = null;//待选中节点
		//父节点
		TreeItem<String> treeItem = null;
		for(Map<String,Object> map :mapList){
			String visceraCode = (String) map.get("visceraCode");
			String visceraName = (String) map.get("visceraName");
			String subVisceraCode = (String) map.get("subVisceraCode");
			String subVisceraName = (String) map.get("subVisceraName");
			Integer sn = (Integer) map.get("sn");
			Integer subSn = (Integer) map.get("subSn");
			
			TreeItem<String> leafNode = new TreeItem<String>("");
			Label leafLabel = new Label();
			
			if(visceraCode2TreeItemMap.keySet().contains(visceraCode)){
				treeItem = visceraCode2TreeItemMap.get(visceraCode);
			}else{
				treeItem = new TreeItem<String>("");
				Label visceraNameLabel = new Label(visceraName);
				//label的UserData存放的是map
				visceraNameLabel.setUserData(map);
				//一级脏器
				visceraNameLabel.setId(1+"");
				treeItem.setGraphic(visceraNameLabel);
				treeItem.setExpanded(true);
				rootNode.getChildren().add(getIndexofVisceraTree(sn),treeItem);
				visceraCode2TreeItemMap.put(visceraCode, treeItem);
				if(currentVisceraName.equals(visceraName)){
					selectedItem = treeItem;
				}
			}
			//有子脏器
			if(null != subVisceraCode && !"".equals(subVisceraCode)){
				if(subVisceraCodeSet.contains(subVisceraCode)){
					continue;
				}else{
					leafLabel.setText(subVisceraName);
					//二级脏器
					leafLabel.setId(2+"");
					leafLabel.setUserData(map);
					leafNode.setGraphic(leafLabel);
					treeItem.getChildren().add(getIndex(treeItem, subSn),leafNode);
					if(currentVisceraName.equals(subVisceraName)){
						selectedItem = leafNode;
					}
				}
				
			}
			
		}
		//清除其他选择，选中现在的
		visceraTree.getSelectionModel().clearSelection();
		visceraTree.getSelectionModel().select(selectedItem);
		treeScrollTo(visceraTree,selectedItem);
	}
	
	/**获取visceraTree中索引
	 * @param sn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getIndexofVisceraTree(Integer sn) {
		int i = 0;
		ObservableList<TreeItem<String>> children = rootNode.getChildren();
		for( TreeItem<String> obj:children){
			Map<String,Object> map = (Map<String, Object>) obj.getGraphic().getUserData();
			Integer currentSn = (Integer) map.get("sn");
			if(sn < currentSn){
				break;
			}
			i++;
		}
		return i;
	}
	
	/**获取索引
	 * @param parentItem
	 * @param subSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int getIndex(TreeItem<String> parentItem,int subSn){
		int i = 0;
		ObservableList<TreeItem<String>> children = parentItem.getChildren();
		for( TreeItem<String> obj:children){
			Map<String,Object> map = (Map<String, Object>) obj.getGraphic().getUserData();
			Integer currentSubSn = (Integer) map.get("subSn");
			if(subSn < currentSubSn){
				break;
			}
			i++;
		}
		return i;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("TissueSliceAdd.fxml"));
		Scene scene = new Scene(root, 784, 608);
		stage.setScene(scene);
		stage.setTitle("组织取材编号");
//		stage.setResizable(false);
		stage.setMinWidth(784);
		stage.setMinHeight(608);
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
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}

	/**剂量组
	 * @author Administrator
	 *
	 */
	public class  DoseSetting{
		private BooleanProperty select;
		private StringProperty dosageNum;
		private StringProperty dosageDesc;
		public DoseSetting(){
			super();
		}
		public DoseSetting(boolean select,int dosageNum,String dosageDesc){
			setSelect(select);
			setDosageNum(dosageNum+"");
			setDosageDesc(dosageDesc);
		}
		
		 public BooleanProperty selectProperty(){return select;}
		public Boolean getSelect() {
			return select.getValue();
		}
		public void setSelect(Boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getDosageNum() {
			return dosageNum.get();
		}
		public void setDosageNum(String dosageNum) {
			this.dosageNum = new SimpleStringProperty(dosageNum);
		}
		public String getDosageDesc() {
			return dosageDesc.get();
		}
		public void setDosageDesc(String dosageDesc) {
			this.dosageDesc = new SimpleStringProperty(dosageDesc);
		}
		
	}

	public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
		private final CheckBox checkBox;
		private ObservableValue<T> ov;

		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
		}

		@Override
		public void updateItem(T item, boolean empty) {
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
					getTableRow().setStyle("-fx-background-color:palegreen ;");
				} else {
					getTableRow().setStyle("");
				}

			}

		}

	}

	
}
