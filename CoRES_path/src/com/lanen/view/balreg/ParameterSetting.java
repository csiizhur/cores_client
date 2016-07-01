package com.lanen.view.balreg;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblBalCalibrationPoint;
import com.lanen.model.path.TblCounterWeight;
import com.lanen.model.path.TblCounterpoise;
import com.lanen.util.messager.Messager;
import com.lanen.view.main.Main;

/**
 * 校准参数设置
 * @author Administrator
 *
 */
public class ParameterSetting extends Application implements Initializable{

	private Stage stage ;
	
	private static ParameterSetting instance;
	
	@FXML   
	private TextField calCheckPoint;//校准检查点数
	
	@FXML   
	private TextField cPCodeText;//砝码编号
	
	@FXML   
	private TextField cPWeightText;//砝码重量
	
	@FXML   
	private TextField balPrecisionText;//天平精度
	
	@FXML   
	private TextField toleranceLowerText;//允差下限
	
	@FXML   
	private TextField toleranceUpperText;//允差上限
	
	
	
	@FXML
	private TableView<CounterWeight> counterWeightTable;		//砝码表格
	private ObservableList<CounterWeight> data_counterWeightTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<CounterWeight,String> widCol;        //主键
	@FXML
	private TableColumn<CounterWeight,String> cPCodeCol;        //砝码编号
	@FXML
	private TableColumn<CounterWeight,String> cPWeightCol;        //砝码重量
	
	
	
	@FXML
	private TableView<Counterpoise> counterpoiseTable;		//砝码表格
	private ObservableList<Counterpoise> data_counterpoiseTable = FXCollections.observableArrayList();
	@FXML
	private TableColumn<Counterpoise,String> idCol;        //主键
	@FXML
	private TableColumn<Counterpoise,String> balPrecisionCol;        //天平精度
	@FXML
	private TableColumn<Counterpoise,String> toleranceLowerCol;        //允差下限
	@FXML
	private TableColumn<Counterpoise,String> toleranceUpperCol;        //允差上限
	
	@FXML
	private Button delectBalButton;//删除砝码校准
	
	@FXML
	private Button saveBalButton;//保存砝码校准
	
	@FXML
	private Button updateBalButton;//更新砝码校准
	
	@FXML
	private Button updateWeightBalButton;//更新砝码

	@FXML
	private Button delectWeightBalButton;//删除砝码
	
	@FXML
	private Button savWeighteBalButton;//保存砝码
	
	public static ParameterSetting getInstance(){
		if(null == instance){
			instance = new ParameterSetting();
		}
		return instance;
	}
	
	public ParameterSetting() {
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		initCounterWeightTable();//初始化砝码列表
		initCounterpoiseTable();//初始化校准参数
		
	}

	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * 校验是否是数字
	 * @param str
	 * @return
	 */
	private static boolean isNumber(String str)
	    {
	        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^\\d+(\\.\\d+)?$");
	        java.util.regex.Matcher match=pattern.matcher(str);
	        if(match.matches()==false){
	             return false;
	        }else{
	             return true;
	        }
	    }
	/**
	 * 校验是否是数字
	 * @param str
	 * @return
	 */
	private static boolean isNumber2(String str){
	        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^-\\d+(\\.\\d+)?$");
	        java.util.regex.Matcher match=pattern.matcher(str);
	        if(match.matches()==false){
	             return false;
	        }else{
	             return true;
	        }
	}
	
	/**
	 * 保存砝码
	 */
	@FXML        
	private void saveCounterWeightBtnAction(){
		String cPCode = cPCodeText.getText().toString();//砝码编号
		String cPWeight = cPWeightText.getText().toString();//砝码重量
		Boolean falg = true;
		String errorStr = "";
		if( null == cPCode || cPCode.equals("")){
			errorStr = "请输入砝码编号";
			falg = false;
		}else if(  null == cPWeight || cPWeight.equals("")){
			errorStr = "请输入砝码重量";
			falg = false;
		}else if(cPCode.length()>20){
			errorStr = "输入的砝码编号过长";
			falg = false;
		}else if(cPWeight.length()>20){
			errorStr = "输入的砝码重量过长";
			falg = false;
		}else if(!isNumber(cPWeight)){
			errorStr = "请输入正确的砝码重量";
			falg = false;
		}else if(BaseService.getInstance().getTblCounterWeightService().isHaveCpCodeByCpCode(cPCode)){
			errorStr = "砝码编号已存在";
			falg = false;
		}
		if(falg){
			TblCounterWeight counterWeight = new TblCounterWeight();
			counterWeight.setId( BaseService.getInstance().getTblCounterWeightService().getKey());
			counterWeight.setCpCode(cPCode);
			counterWeight.setCpWeight(cPWeight);
			BaseService.getInstance().getTblCounterWeightService().save(counterWeight);
			cPCodeText.setText("");
			cPWeightText.setText("");
			updateWeightTable();
			selectWRow(counterWeight);
		}else{
			showErrorMessage(errorStr);
		}
	}
	
	
	/**
	 * 更新砝码
	 */
	@FXML
	private void updateCounterWeightBtnAction(){
		String cPCode = cPCodeText.getText().toString();//砝码编号
		String cPWeight = cPWeightText.getText().toString();//砝码重量
		Boolean falg = true;
		String errorStr = "";
		if( null == cPCode || cPCode.equals("")){
			errorStr = "请输入砝码编号";
			falg = false;
		}else if(  null == cPWeight || cPWeight.equals("")){
			errorStr = "请输入砝码重量";
			falg = false;
		}else if(cPCode.length()>20){
			errorStr = "输入的砝码编号过长";
			falg = false;
		}else if(cPWeight.length()>20){
			errorStr = "输入的砝码重量过长";
			falg = false;
		}else if(!isNumber(cPWeight)){
			errorStr = "请输入正确的砝码重量";
			falg = false;
		}
		
		String id = counterWeightTable.getSelectionModel().getSelectedItem().getWid();
		TblCounterWeight counterWeight= BaseService.getInstance().getTblCounterWeightService().getById(id);
		String cpCode = counterWeight.getCpCode();
		if(!cpCode.equals(cPCode) && BaseService.getInstance().getTblCounterWeightService().isHaveCpCodeByCpCode(cPCode)){
			errorStr = "砝码编号已存在";
			falg = false;
		}
		
		
		if(falg){
			counterWeight.setCpCode(cPCode);
			counterWeight.setCpWeight(cPWeight);
			 BaseService.getInstance().getTblCounterWeightService().update(counterWeight);
			cPCodeText.clear();
			cPWeightText.clear();
			updateWeightTable();
			selectWRow(counterWeight);
		}else{
			showErrorMessage(errorStr);
		}
	
		
	}
	
	
	
	/**
	 * 保存砝码允差
	 */
	@FXML
	private void saveCounterpoiseBtnAction(){
		
		String balPrecision = balPrecisionText.getText().toString();//天平精度
		String toleranceLower = toleranceLowerText.getText().toString();//允差下限
		String toleranceUpper = toleranceUpperText.getText().toString();//允差上限
		
		String cPCode = cPCodeText.getText().toString();//砝码编号
		String cPWeight = cPWeightText.getText().toString();//砝码重量
		
		Boolean falg = true;
		String errorStr = "";
	   if(  null == balPrecision || balPrecision.equals("")){
			errorStr = "请输入天平精度";
			falg = false;
		}else if(  null == toleranceLower || toleranceLower.equals("")){
			errorStr = "请输入允差下限";
			falg = false;
		}else if(  null == toleranceUpper || toleranceUpper.equals("")){
			errorStr = "请输入允差上限";
			falg = false;
		}else if(balPrecision.length()>20){
			errorStr = "输入的天平精度过长";
			falg = false;
		}else if(toleranceLower.length()>20){
			errorStr = "输入的允差下限过长";
			falg = false;
		}else if(toleranceUpper.length()>20){
			errorStr = "输入的允差上限过长";
			falg = false;
		}else if(!isNumber(balPrecision)){
			errorStr = "请输入正确的天平精度";
			falg = false;
		}else if(!isNumber2(toleranceLower)){
			errorStr = "请输入正确的允差下限";
			falg = false;
		}else if(!isNumber(toleranceUpper)){
			errorStr = "请输入正确的允差上限";
			falg = false;
		}else if(BaseService.getInstance().getTblCounterpoiseService().isEnabledByCpWeightAndCalPrecision(cPCode,cPWeight,balPrecision)){
			errorStr = "砝码天平精度不能重复！";
			falg = false;
		}
		
		if(falg){
			TblCounterpoise counterpoise = new TblCounterpoise();
			counterpoise.setId( BaseService.getInstance().getTblCounterpoiseService().getKey());
			counterpoise.setCpCode(cPCode);
			counterpoise.setCpWeight(cPWeight);
			counterpoise.setBalPrecision(balPrecision);
			counterpoise.setToleranceLower(toleranceLower);
			counterpoise.setToleranceUpper(toleranceUpper);
			BaseService.getInstance().getTblCounterpoiseService().save(counterpoise);
			balPrecisionText.clear();
			toleranceLowerText.clear();
			toleranceUpperText.clear();
			updateTable();
			selectRow(counterpoise);
		}else{
			showErrorMessage(errorStr);
		}
	
		
	}
	
	
	
	/**
	 * 更新砝码
	 */
	@FXML
	private void updateCounterpoiseBtnAction(){
		String cPCode = cPCodeText.getText().toString();//砝码编号
		String cPWeight = cPWeightText.getText().toString();//砝码重量
		String balPrecision = balPrecisionText.getText().toString();//天平精度
		String toleranceLower = toleranceLowerText.getText().toString();//允差下限
		String toleranceUpper = toleranceUpperText.getText().toString();//允差上限
		Boolean falg = true;
		String errorStr = "";
	   if(  null == balPrecision || balPrecision.equals("")){
			errorStr = "请输入天平精度";
			falg = false;
		}else if(  null == toleranceLower || toleranceLower.equals("")){
			errorStr = "请输入允差下限";
			falg = false;
		}else if(  null == toleranceUpper || toleranceUpper.equals("")){
			errorStr = "请输入允差上限";
			falg = false;
		}else if(cPCode.length()>20){
			errorStr = "输入的砝码编号过长";
			falg = false;
		}else if(cPWeight.length()>20){
			errorStr = "输入的砝码重量过长";
			falg = false;
		}else if(balPrecision.length()>20){
			errorStr = "输入的天平精度过长";
			falg = false;
		}else if(toleranceLower.length()>20){
			errorStr = "输入的允差下限过长";
			falg = false;
		}else if(toleranceUpper.length()>20){
			errorStr = "输入的允差上限过长";
			falg = false;
		}else if(!isNumber(cPWeight)){
			errorStr = "请输入正确的砝码重量";
			falg = false;
		}else if(!isNumber(balPrecision)){
			errorStr = "请输入正确的天平精度";
			falg = false;
		}else if(!isNumber2(toleranceLower)){
			errorStr = "请输入正确的允差下限";
			falg = false;
		}else if(!isNumber(toleranceUpper)){
			errorStr = "请输入正确的允差上限";
			falg = false;
		} 
		
	    String id = counterpoiseTable.getSelectionModel().getSelectedItem().getId();
		TblCounterpoise counterpoise= BaseService.getInstance().getTblCounterpoiseService().getById(id);
		String cpCode = counterpoise.getCpCode();
		if(!cpCode.equals(cPCode) && BaseService.getInstance().getTblCounterpoiseService().isEnabledByCpCode(cPCode)){
			errorStr = "砝码编号已存在";
			falg = false;
		}
		
		
		if(falg){
			//TblCounterpoise counterpoise = new TblCounterpoise();
			counterpoise.setBalPrecision(balPrecision);
			counterpoise.setToleranceLower(toleranceLower);
			counterpoise.setToleranceUpper(toleranceUpper);
			BaseService.getInstance().getTblCounterpoiseService().update(counterpoise);
			updateTable();
			selectRow(counterpoise);
		}else{
			showErrorMessage(errorStr);
		}
	
		
	}
	
	/**
	 * 加载数据
	 * @param sessionIdList 
	 */
	public void loadData() {
//		TblBalCalibrationPoint point = 
//				 BaseService.getInstance().getTblBalCalibrationPointService().getBalCalibrationPointByCalType(1);
//		if(null != point ){
//			Integer checkPoint=point.getCalCheckPoint();
//			calCheckPoint.setText(checkPoint+"");//如果有校准点数赋值
//		}
		updateWeightTable();
	}
	
	

	
	/**
	 * 初始化 table
	 */
	private void initCounterWeightTable() {
		counterWeightTable.setItems(data_counterWeightTable);
		widCol.setCellValueFactory(new PropertyValueFactory<CounterWeight,String>("wid")); // 主键
		cPCodeCol.setCellValueFactory(new PropertyValueFactory<CounterWeight,String>("cpCode"));//砝码编号
		cPWeightCol.setCellValueFactory(new PropertyValueFactory<CounterWeight,String>("cpWeight"));//砝码重量
		counterWeightTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<CounterWeight>(){

			@Override
			public void changed(ObservableValue<? extends CounterWeight> arg0, CounterWeight arg1,
					CounterWeight newValue) {
				if(null != newValue){
					delectWeightBalButton.setDisable(false);
					updateWeightBalButton.setDisable(false);
					String id = counterWeightTable.getSelectionModel().getSelectedItem().getWid();
					TblCounterWeight counterWeight= BaseService.getInstance().getTblCounterWeightService().getById(id);
//					String olId = counterWeight.getId();
					String cpCode = counterWeight.getCpCode();
					String cpWeight = counterWeight.getCpWeight();
					cPCodeText.setText(cpCode);
					cPWeightText.setText(cpWeight);
					updateTable();
					balPrecisionText.clear();
					toleranceLowerText.clear();
					toleranceUpperText.clear();
				}else{
					delectWeightBalButton.setDisable(true);
					updateWeightBalButton.setDisable(true);
					data_counterpoiseTable.clear();
				}
			}

		});
		

	}
	
	/**更新表格数据
	 * @param taskIdList2
	 */
	private void updateWeightTable() {
		data_counterWeightTable.clear();
		List<TblCounterWeight> list = BaseService.getInstance().getTblCounterWeightService().findAll();
		if(null != list && list.size()>0){
			for(TblCounterWeight obj:list){
				String id = obj.getId();
				String cpCode = obj.getCpCode();
				String cpWeight = obj.getCpWeight();
				data_counterWeightTable.add(new CounterWeight(id,cpCode,cpWeight));
			}
		}
		
	}
	
	/**
	 * 初始化 table
	 */
	private void initCounterpoiseTable() {
		counterpoiseTable.setItems(data_counterpoiseTable);
		idCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("id")); // 主键
		balPrecisionCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("balPrecision"));//天平精度
		toleranceLowerCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("toleranceLower"));//允差下限
		toleranceUpperCol.setCellValueFactory(new PropertyValueFactory<Counterpoise,String>("toleranceUpper"));//允差上限
		counterpoiseTable.getSelectionModel()
		.selectedItemProperty().addListener(new ChangeListener<Counterpoise>(){

			@Override
			public void changed(ObservableValue<? extends Counterpoise> arg0, Counterpoise arg1,
					Counterpoise newValue) {
				if(null != newValue){
					delectBalButton.setDisable(false);
					updateBalButton.setDisable(false);
					int selectRow = counterpoiseTable.getSelectionModel().getSelectedCells().get(0).getRow();
					String id = data_counterpoiseTable.get(selectRow).getId();
					TblCounterpoise counterpoise= BaseService.getInstance().getTblCounterpoiseService().getById(id);
					//String olId = counterpoise.getId();
					String cpCode = counterpoise.getCpCode();
					String cpWeight = counterpoise.getCpWeight();
					String balPrecision = counterpoise.getBalPrecision();
					String toleranceLower = counterpoise.getToleranceLower();
					String toleranceUpper = counterpoise.getToleranceUpper();
					cPCodeText.setText(cpCode);
					cPWeightText.setText(cpWeight);
					balPrecisionText.setText(balPrecision);
					toleranceLowerText.setText(toleranceLower);
					toleranceUpperText.setText(toleranceUpper);
					
				}else{
					delectBalButton.setDisable(true);
					updateBalButton.setDisable(true);
				}
			}

		});
		

	}
	/**更新表格数据
	 * @param taskIdList2
	 */
	private void updateTable() {
		data_counterpoiseTable.clear();
		String wid = counterWeightTable.getSelectionModel().getSelectedItem().getWid();
		TblCounterWeight counterWeight= BaseService.getInstance().getTblCounterWeightService().getById(wid);
		List<TblCounterpoise> list = BaseService.getInstance().getTblCounterpoiseService().getListByCpCode(counterWeight.getCpCode());
		if(null != list && list.size()>0){
			for(TblCounterpoise obj:list){
				String id = obj.getId();
				String balPrecision = (String)obj.getBalPrecision();
				String toleranceLower = (String)obj.getToleranceLower();
				String toleranceUpper =(String)obj.getToleranceUpper();
				data_counterpoiseTable.add(new Counterpoise(id,balPrecision,toleranceLower,toleranceUpper));
			}
		}
		
	}
	
	/**
	 * 删除砝码
	 */
	@FXML
	private void delectCounterWeight(){
		String id = counterWeightTable.getSelectionModel().getSelectedItem().getWid();
		BaseService.getInstance().getTblCounterWeightService().delectAll(id);
		updateWeightTable();
	}
	/**
	 * 删除砝码校准
	 */
	@FXML
	private void delectCounterpoise(){
		
		//签字窗口
				//SignFrame signFrame = new SignFrame("");
				//Stage stage = new Stage();
				//stage.initModality(Modality.APPLICATION_MODAL);
				//stage.setTitle("会话--身份验证");
				//try {
				//	signFrame.start(stage);
				//} catch (Exception e) {
				//	e.printStackTrace();
				//}
				//签字通过
				//if("OK".equals(SignFrame.getType())){
					//String userName = "";
//					Date currentDate = new Date();
					//User user = SignFrame.getUser();
					//if(null != user){
					//	userName = user.getUserName();
					//}
					int selectRow = counterpoiseTable.getSelectionModel().getSelectedCells().get(0).getRow();
					String id = data_counterpoiseTable.get(selectRow).getId();
					BaseService.getInstance().getTblCounterpoiseService().delete(id);
					updateTable();
					
				//}
	}
	
	/**
	 * 选择一行
	 * @param counterpoise
	 */
	public void selectWRow(TblCounterWeight counterWeight){
		for(int i = 0;i<data_counterWeightTable.size();i++){
			if(data_counterWeightTable.get(i).getWid().equals(counterWeight.getId())){
				counterWeightTable.getSelectionModel().select(i);
			}
		}
		
	}
	/**
	 * 选择一行
	 * @param counterpoise
	 */
	public void selectRow(TblCounterpoise counterpoise){
		for(int i = 0;i<data_counterpoiseTable.size();i++){
			if(data_counterpoiseTable.get(i).getId().equals(counterpoise.getId())){
				counterpoiseTable.getSelectionModel().select(i);
			}
		}
		
	}
	
	/**
	 * 保存天平校准检查点数设置
	 */
	@FXML
	private void saveBalCalibrationPoint(){
		//签字窗口
				//SignFrame signFrame = new SignFrame("");
				//Stage stage = new Stage();
				//stage.initModality(Modality.APPLICATION_MODAL);
				//stage.setTitle("会话--身份验证");
				//try {
				//	signFrame.start(stage);
				//} catch (Exception e) {
				//	e.printStackTrace();
				//}
				//签字通过
				//if("OK".equals(SignFrame.getType())){
				//	String userName = "";
//					Date currentDate = new Date();
				//	User user = SignFrame.getUser();
				//	if(null != user){
				//		userName = user.getUserName();
				///	}
					TblBalCalibrationPoint point = 
							 BaseService.getInstance().getTblBalCalibrationPointService().getBalCalibrationPointByCalType(1);
					String calCheckPointStr = calCheckPoint.getText().toString();
					Integer calCheckPoint = Integer.parseInt(calCheckPointStr);
					if(null == point ){
						point = new  TblBalCalibrationPoint();
						point.setId(BaseService.getInstance().getTblBalCalibrationPointService().getKey());
						point.setCalType(1);
						point.setCalCheckPoint(calCheckPoint);
						BaseService.getInstance().getTblBalCalibrationPointService().save(point);
					}else{
						point.setCalCheckPoint(calCheckPoint);
						BaseService.getInstance().getTblBalCalibrationPointService().update(point);
					}
					showMessage("设置成功");
				//}
		
	}
	
	public void showMessage(String msg) {
//		Alert.create(msg);
		Messager.showMessage(msg);
	}

	public void showErrorMessage(String msg) {
//		Alert2.create(msg);
		Messager.showWarnMessage(msg);
	}
	

	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("ParameterSetting.fxml"));
		Scene scene = new Scene(root, 690, 419);
		stage.setScene(scene);
		stage.setTitle("校准参数设置");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
			}
		});
		stage.show();
		
	}
	
	
	
	
	/**
	 * 获取当前页面定义的  stage
	 * @return
	 */
	public Stage getStage(){
		if(null == stage){
			stage = new Stage();
			stage.initOwner(Main.mainWidow);
			stage.initModality(Modality.APPLICATION_MODAL);
		}
		return stage;
	
	}
	
	/**
	 * 砝码
	 */
	public static class CounterWeight{
		
		private StringProperty wid; // 主键
		private StringProperty cpCode;//砝码编号
		private StringProperty cpWeight;//砝码重量
		
		public CounterWeight(){
			super();
		}

		public CounterWeight(String id,String cpCode,String cpWeight){
			this.wid = new SimpleStringProperty(id);
			this.cpCode = new SimpleStringProperty(cpCode);
			this.cpWeight = new SimpleStringProperty(cpWeight+"g");
		}
		
		public String getWid() {
			return wid.get();
		}
		public void setWid(String wid) {
			this.wid = new SimpleStringProperty(wid);
		}
		public String getCpCode() {
			return cpCode.get();
		}
		public void setCpCode(String cpCode) {
			this.cpCode = new SimpleStringProperty(cpCode);
		}
		public String getCpWeight() {
			return cpWeight.get();
		}
		public void setCpWeight(String cpWeight) {
			this.cpWeight = new SimpleStringProperty(cpWeight);
		}
		
		
	}

	/**
	 * 砝码允差
	 */
	public static class Counterpoise{
		private StringProperty id; // 主键
		private StringProperty cpCode;//砝码编号
		private StringProperty cpWeight;//砝码重量
		private StringProperty balPrecision;//天平精度
		private StringProperty toleranceLower;//允差下限
		private StringProperty toleranceUpper;//允差上限
		
		public Counterpoise(){
			super();
		}

		public Counterpoise(String id,String balPrecision,String toleranceLower,String toleranceUpper){
			this.id = new SimpleStringProperty(id);
			this.balPrecision = new SimpleStringProperty(balPrecision+"g");
			this.toleranceLower = new SimpleStringProperty(toleranceLower+"g");
			this.toleranceUpper = new SimpleStringProperty(toleranceUpper+"g");
		}

		public String getId() {
			return id.get();
		}

		public void setId(String id) {
			this.id = new SimpleStringProperty(id);
		}

		public String getCpCode() {
			return cpCode.get();
		}

		public void setCpCode(String cpCode) {
			this.cpCode = new SimpleStringProperty(cpCode);
		}

		public String getCpWeight() {
			return cpWeight.get();
		}

		public void setCpWeight(String cpWeight) {
			this.cpWeight = new SimpleStringProperty(cpWeight);
		}

		public String getBalPrecision() {
			return balPrecision.get();
		}

		public void setBalPrecision(String balPrecision) {
			this.balPrecision = new SimpleStringProperty(balPrecision);
		}

		public String getToleranceLower() {
			return toleranceLower.get();
		}

		public void setToleranceLower(String toleranceLower) {
			this.toleranceLower = new SimpleStringProperty(toleranceLower);
		}

		public String getToleranceUpper() {
			return toleranceUpper.get();
		}

		public void setToleranceUpper(String toleranceUpper) {
			this.toleranceUpper = new SimpleStringProperty(toleranceUpper);
		}
		
	}
	
}
