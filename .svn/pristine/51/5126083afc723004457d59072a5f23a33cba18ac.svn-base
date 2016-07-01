package com.lanen.view.clinicaltest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.sound.midi.SysexMessage;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import com.lanen.base.BaseService;
import com.lanen.model.User;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblTrace;
import com.lanen.util.DateUtil;
import com.lanen.util.SaveUserInfo;
import com.lanen.util.SystemTool;
import com.lanen.util.messager.Messager;
import com.lanen.view.sign.Sign;

public class DataEditPage extends Application implements Initializable {
	
	@FXML
	private TableView<ClinicalTestDataES> table;
	ObservableList<ClinicalTestDataES> data_table=FXCollections.observableArrayList();
	
//	@FXML
//	private TableColumn<ClinicalTestDataES,String> dataIdCol;             //数据Id
//	@FXML
//	private TableColumn<ClinicalTestDataES,String> tblSpecimenIdCol;  //标本接收登记Id
	@FXML
	private TableColumn<ClinicalTestDataES,String> studyNoCol;            //课题编号
	@FXML
	private TableColumn<ClinicalTestDataES,String> reqNoCol;              //申请编号
	@FXML
	private TableColumn<ClinicalTestDataES,String> animalIdCol;           //动物Id
	@FXML
	private TableColumn<ClinicalTestDataES,String> animalCodeCol;         //动物编号
	@FXML
    private TableColumn<ClinicalTestDataES,String> specimenCodeCol;       //检验编号号
	@FXML
    private TableColumn<ClinicalTestDataES,String> testItemCol;              //检验项目
	@FXML
    private TableColumn<ClinicalTestDataES,String> testIndexCol;          //检验指标
	@FXML
    private TableColumn<ClinicalTestDataES,String> testIndexAbbrCol;      //检验指标缩写
	@FXML
    private TableColumn<ClinicalTestDataES,String> testDataCol;           //数据
	@FXML
    private TableColumn<ClinicalTestDataES,String> testIndexUnitCol;//检验指标单位
//	@FXML
//    private TableColumn<ClinicalTestDataES,String> collectionModeCol;        //数据采集方式
    @FXML
    private TableColumn<ClinicalTestDataES,String> collectionTimeCol;       //数据采集时间(检验时间)
    @FXML
    private TableColumn<ClinicalTestDataES,String> acceptTimeCol;        //接收时间
    @FXML
    private TableColumn<ClinicalTestDataES,String> confirmFlagCol;      //1.第一次接收   2，第二次接收
    @FXML
    private TableColumn<ClinicalTestDataES,String> remarkCol;
    
    @FXML
    private TextField editRsnText;
	
	private static DataEditPage instance;
	public static DataEditPage getInstance(){
		if(null == instance){
			instance = new DataEditPage();
		}
		return instance;
	}
	
	public DataEditPage() {
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		initTableView();
		
	}
	
	/**
	 * 初始化  表格
	 */
	private void initTableView() {
		 studyNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("studyNo"));            //课题编号
		 reqNoCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("reqNo"));              //申请编号
		 animalIdCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("animalId"));           //动物Id
		 animalCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("animalCode"));         //动物编号
	     specimenCodeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("specimenCode"));       //检验编号号
	     testItemCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testItem"));              //检验项目
	     testIndexCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndex"));          //检验指标
	     testIndexAbbrCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndexAbbr"));      //检验指标缩写
	     testDataCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testData"));           //数据
	     testIndexUnitCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("testIndexUnit"));//检验指标单位
	     collectionTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("collectionTime"));       //数据采集时间(检验时间)
	     acceptTimeCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("acceptTime"));        //接收时间
	     confirmFlagCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("confirmFlag"));      //1.第一次接收   2，第二次接收
	     remarkCol.setCellValueFactory(new PropertyValueFactory<ClinicalTestDataES,String>("remark"));             //是否有效
//	     testDataCol.setCellFactory(new Callback<TableColumn<ClinicalTestDataES,String>,TableCell<ClinicalTestDataES,String>>(){
//	    	 
//	    	 @Override
//	    	 public TableCell<ClinicalTestDataES, String> call(
//	    			 TableColumn<ClinicalTestDataES, String> param) {
//	    		 TableCell<ClinicalTestDataES, String> cell =
//	    				 new TableCell<ClinicalTestDataES, String>() {
//	    			 @Override
//	    			 public void updateItem(String item, boolean empty) {
//	    				 super.updateItem(item, empty);
//	    				 setText(empty ? null : getString());
//	    				 setGraphic(null);
//	    			 }
//	    			 
//	    			 private String getString() {
//	    				 return getItem() == null ? "" : getItem().toString();
//	    			 }
//	    			 
//	    		 };
//	    		 cell.setStyle("-fx-alignment: CENTER-right;");
//	    		 return cell;
//	    		 
//	    	 }
//	    	 
//	     });
	     Callback<TableColumn<ClinicalTestDataES,String>,TableCell<ClinicalTestDataES,String>> cellFactory = new Callback<TableColumn<ClinicalTestDataES,String>,TableCell<ClinicalTestDataES,String>>(){

				@SuppressWarnings("rawtypes")
				@Override
				public TableCell call(TableColumn p) {
					
					return new TextFieldTableCellImpl();
				}
				
			};
			testDataCol.setCellFactory(cellFactory);
			//更新值
			testDataCol.setOnEditCommit(new EventHandler<CellEditEvent<ClinicalTestDataES,String>>(){

				@Override
				public void handle(CellEditEvent<ClinicalTestDataES, String> t) {
					((ClinicalTestDataES) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())).setTestData(t.getNewValue());
				}});
		table.setItems(data_table);
	     
	}
	
	/**确定
	 * @param event
	 */
	@FXML
	private void onConfirmBtnAction(ActionEvent event){
		//1.已修改数据Id列表
		List<TblTrace> tblTraceList = new ArrayList<TblTrace>();
		TblTrace tblTrace = null;
		for(ClinicalTestDataES obj :data_table){
			if(!obj.getTestData().trim().equals("999.9")){
				tblTrace = new TblTrace();
				tblTrace.setTableName("TblClinicalTestData");
				tblTrace.setDataId(obj.getDataId());
				tblTrace.setOperateMode(1);//编辑
											//原数据
				tblTrace.setNewValue(obj.getTestData().trim());//新数据
//				tblTrace.setOperator(SaveUserInfo.getUser().getRealName());
				tblTrace.setHost(SystemTool.getHostName());
				
				tblTraceList.add(tblTrace);
			}
		}
		if(null == tblTraceList || tblTraceList.size() < 1){
			showErrorMessage("请先编辑数据！");
			return;
		}
		
		//2.修改原因
		String editRsn = editRsnText.getText().trim();
		if("".equals(editRsn)){
			showErrorMessage("请输入修改原因！");
			editRsnText.requestFocus();
			return;
		}
		if(editRsn.getBytes().length > 200){
			showErrorMessage("修改原因长度不能大于200！");
			editRsnText.requestFocus();
			return;
		}
		
		//3.
		User signUser = Sign.openSign("签字", "共修改"+tblTraceList.size()+"条 RET# 检验指标");
		if(null != signUser){
			String realName = signUser.getRealName();
			for(TblTrace obj :tblTraceList){
				obj.setModifyReason(editRsn);
				obj.setOperator(realName);
			}
			BaseService.getTblClinicalTestDataService().editSave(tblTraceList);
			//更新数据	TODO
			DataAcceptESFrame.getInstance().updateTable(tblTraceList);
			((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
		}
	}
	/**取消
	 * @param event
	 */
	@FXML
	private void onExitBtnAction(ActionEvent event){
		((javafx.scene.control.Control)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * 加载数据
	 * @param noEditRetDataIdList 
	 */
	public void loadData(List<String> noEditRetDataIdList) {
		//1.更新表格数据
		updateTable(noEditRetDataIdList);
		//2.清空编辑原因text
		editRsnText.clear();
	}

	private void updateTable(List<String> noEditRetDataIdList) {
		// TODO Auto-generated method stub
		data_table.clear();
		List<TblClinicalTestData> list=BaseService.getTblClinicalTestDataService().getByIdList(noEditRetDataIdList);
		
		if(null!=list&&list.size()>0){
			int index =0;
			for(TblClinicalTestData obj:list){
				String collectionTime="";
				if(null!=obj.getCollectionTime()){
					collectionTime   =DateUtil.dateToString(obj.getCollectionTime(), "HH:mm:ss");
				}
				String acceptTime="";
				if(null!=obj.getAcceptTime()){
					acceptTime   =DateUtil.dateToString(obj.getAcceptTime(), "HH:mm:ss");
				}
				String remark = obj.getRemark();
				String remark2 ="";
				if(null != remark && remark.contains("@")){
					String[] strs = remark.split("@");
					remark = strs[1];
					remark2 = strs[0];
				}
				ClinicalTestDataES clinicalTestDataES = new ClinicalTestDataES(
						false,
						obj.getDataId(),
						obj.getTblSpecimen().getSpecimenId(),
						obj.getStudyNo(),
						obj.getReqNo(),
						obj.getAnimalId(),
						obj.getAnimalCode(),
						obj.getSpecimenCode(),
						"血液检查" ,
						obj.getTestIndex(),
						obj.getTestIndexAbbr(),
						obj.getTestData(),
						obj.getTestIndexUnit(),
						obj.getCollectionMode()==1 ? "自动":"手动",
						collectionTime,
						acceptTime,
					    obj.getEs()==0?"否":"是",
					    obj.getConfirmFlag()+"",
					    (obj.getValidFlag()==1),
					    remark,remark2
					    ,index
						);
				data_table.add(clinicalTestDataES);
				index++;
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("DataEdit.fxml"));
		Scene scene = new Scene(root, 751, 419);
		stage.setScene(scene);
		stage.setTitle("数据修改");
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					//event.consume();
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
	public class ClinicalTestDataES {
		private BooleanProperty select;
		private StringProperty dataId;             //数据Id
		private StringProperty tblSpecimenId;  //标本接收登记Id
		private StringProperty studyNo;            //课题编号
		private StringProperty reqNo;              //申请编号
		private StringProperty animalId;           //动物Id
		private StringProperty animalCode;         //动物编号
	    private StringProperty specimenCode;       //检验编号号
	    private StringProperty testItem;              //检验项目
	    private StringProperty testIndex;          //检验指标
	    private StringProperty testIndexAbbr;      //检验指标缩写
	    private StringProperty testData;           //数据
	    private StringProperty testIndexUnit;      //检验指标单位
	    private StringProperty collectionMode;        //数据采集方式
	    private StringProperty collectionTime;       //数据采集时间(检验时间)
	    private StringProperty acceptTime;        //接收时间
	    private StringProperty es;               //签字    0：未签字   1：已签字
	    private StringProperty confirmFlag;      //1.第一次接收   2，第二次接收
	    private BooleanProperty validFlag;     //是否有效
	    
	    private StringProperty remark;    //指标与数值
	    private StringProperty remark2;   //confirmFlag，confirmFlag
	    private IntegerProperty index;
	    
	    public ClinicalTestDataES(){
	    	super();
	    }
	    public ClinicalTestDataES(  
	    		boolean select,
	    		String dataId,        
	    		String tblSpecimenId, 
	    		String studyNo,       
	    		String reqNo,         
	    		String animalId,      
	    		String animalCode,    
	    		String specimenCode,  
	    		String testItem,      
	    		String testIndex,     
	    		String testIndexAbbr, 
	    		String testData,      
	    		String testIndexUnit, 
	    		String collectionMode,
	    		String collectionTime,
	    		String acceptTime,    
	    		String es,            
	    		String confirmFlag ,
	    		boolean validFlag,
	    		String remark,
	    		String remark2,
	    		int index){
	    		this.select=new SimpleBooleanProperty(select);
	    	    this.dataId         =  new SimpleStringProperty(dataId        );
	    	    this.tblSpecimenId  =  new SimpleStringProperty(tblSpecimenId );
	    	    this.studyNo        =  new SimpleStringProperty(studyNo       );
	    	    this.reqNo			=  new SimpleStringProperty(reqNo		)  ;
	    	    this.animalId		=  new SimpleStringProperty(animalId	)  ;
	    	    this.animalCode		=  new SimpleStringProperty(animalCode	)  ;
	    	    this.specimenCode	=  new SimpleStringProperty(specimenCode)  ;
	    	    this.testItem		=  new SimpleStringProperty(testItem	)  ;
	    	    this.testIndex		=  new SimpleStringProperty(testIndex	)  ;
	    	    this.testIndexAbbr	=  new SimpleStringProperty(testIndexAbbr) ;
	    	    this.testData		=  new SimpleStringProperty(testData	)  ;
	    	    this.testIndexUnit	=  new SimpleStringProperty(testIndexUnit) ;
	    	    this.collectionMode	=  new SimpleStringProperty(collectionMode);
	    	    this.collectionTime	=  new SimpleStringProperty(collectionTime);
	    	    this.acceptTime		=  new SimpleStringProperty(acceptTime	)  ;
	    	    this.es				=  new SimpleStringProperty(es			)  ;
	    	    this.confirmFlag	=  new SimpleStringProperty(confirmFlag	  );
	    	    this.validFlag=new SimpleBooleanProperty(validFlag);
	    	    setRemark(remark);
	    	    setRemark2(remark2);
	    	    setIndex(index);
	    }
 public BooleanProperty selectProperty(){return select;}
	    
		public boolean getSelect() {
			return select.get();
		}
		public void setSelect(boolean select) {
			this.select = new SimpleBooleanProperty(select);
		}
		public String getDataId() {
			return dataId.get();
		}
		public void setDataId(String dataId) {
			this.dataId =  new SimpleStringProperty(dataId);
		}
		public String getTblSpecimenId() {
			return tblSpecimenId.get();
		}
		public void setTblSpecimenId(String tblSpecimenId) {
			this.tblSpecimenId =  new SimpleStringProperty(tblSpecimenId);
		}
		public String getStudyNo() {
			return studyNo.get();
		}
		public void setStudyNo(String studyNo) {
			this.studyNo =  new SimpleStringProperty(studyNo);
		}
		public String getReqNo() {
			return reqNo.get();
		}
		public void setReqNo(String reqNo) {
			this.reqNo =  new SimpleStringProperty(reqNo);
		}
		public String getAnimalId() {
			return animalId.get();
		}
		public void setAnimalId(String animalId) {
			this.animalId =  new SimpleStringProperty(animalId);
		}
		public String getAnimalCode() {
			return animalCode.get();
		}
		public void setAnimalCode(String animalCode) {
			this.animalCode =  new SimpleStringProperty(animalCode);
		}
		public String getSpecimenCode() {
			return specimenCode.get();
		}
		public void setSpecimenCode(String specimenCode) {
			this.specimenCode =  new SimpleStringProperty(specimenCode);
		}
		public String getTestItem() {
			return testItem.get();
		}
		public void setTestItem(String testItem) {
			this.testItem =  new SimpleStringProperty(testItem);
		}
		public String getTestIndex() {
			return testIndex.get();
		}
		public void setTestIndex(String testIndex) {
			this.testIndex =  new SimpleStringProperty(testIndex);
		}
		public String getTestIndexAbbr() {
			return testIndexAbbr.get();
		}
		public void setTestIndexAbbr(String testIndexAbbr) {
			this.testIndexAbbr =  new SimpleStringProperty(testIndexAbbr);
		}
		public String getTestData() {
			return testData.get();
		}
		public void setTestData(String testData) {
			this.testData =  new SimpleStringProperty(testData);
		}
		public String getTestIndexUnit() {
			return testIndexUnit.get();
		}
		public void setTestIndexUnit(String testIndexUnit) {
			this.testIndexUnit =  new SimpleStringProperty(testIndexUnit);
		}
		public String getCollectionMode() {
			return collectionMode.get();
		}
		public void setCollectionMode(String collectionMode) {
			this.collectionMode = new SimpleStringProperty(collectionMode);
		}
		public String getCollectionTime() {
			return collectionTime.get();
		}
		public void setCollectionTime(String collectionTime) {
			this.collectionTime = new SimpleStringProperty(collectionTime);
		}
		public String getAcceptTime() {
			return acceptTime.get();
		}
		public void setAcceptTime(String acceptTime) {
			this.acceptTime = new SimpleStringProperty(acceptTime);
		}
		public String getEs() {
			return es.get();
		}
		public void setEs(String es) {
			this.es =  new SimpleStringProperty(es);
		}
		public String getConfirmFlag() {
			return confirmFlag.get();
		}
		public void setConfirmFlag(String confirmFlag) {
			this.confirmFlag =  new SimpleStringProperty(confirmFlag);
		}
		public boolean getValidFlag() {
			return validFlag.get();
		}
		public void setValidFlag(boolean validFlag) {
			this.validFlag = new SimpleBooleanProperty(validFlag);
		}   
		public BooleanProperty validFlagProperty(){return validFlag;}
		public String getRemark() {
			return remark.get();
		}
		public void setRemark(String remark) {
			this.remark = new SimpleStringProperty(remark);
		}
		public int getIndex() {
			return index.get();
		}
		public void setIndex(int index) {
			this.index = new SimpleIntegerProperty(index);
		}
		public String getRemark2() {
			return remark2.get();
		}
		public void setRemark2(String remark2) {
			this.remark2 = new SimpleStringProperty(remark2);
		}
	}
	
	public  class TextFieldTableCellImpl extends TableCell<ClinicalTestDataES,String>{
		
		private TextField textField;
		private String oldVaule;
		public TextFieldTableCellImpl(){
		}
		
		@Override
		public void startEdit() {
			super.startEdit();
			if(textField ==null){
				createTextField();
			}else{
				textField.clear();
				textField.setText(getString());
			}
			setText(null);
			setGraphic(textField);
			textField.selectAll();
			textField.requestFocus();
		}
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			
			setText((String)getItem());
			setGraphic(null);
		}


		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if(empty){
				setText(null);
				setGraphic(null);
			}else{
				if(isEditing()){
					if(textField != null){
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				}else{
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField(){
			oldVaule=getString();
			textField =new TextField(getString());
			textField.setMinWidth(this.getWidth()-this.getGraphicTextGap()*2);
			textField.setOnKeyReleased(new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent t) {
					if(t.getCode()==KeyCode.ENTER){
//						if(!oldVaule.equals(textField.getText())){
//							commitEdit(textField.getText());
//						}else{
//							cancelEdit();
//						}
						commitEdit(textField.getText());
					}else if(t.getCode()==KeyCode.ESCAPE){
						cancelEdit();
					}
				}
				
			});
			textField.setOnMouseExited(new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
//					if(!oldVaule.equals(textField.getText())){
//						commitEdit(textField.getText());
//					}else{
//						cancelEdit();
//					}
					commitEdit(textField.getText());
				}
			});
		}
		private String getString(){
			return getItem()==null ? "":getItem().toString();
		}
	}
}
