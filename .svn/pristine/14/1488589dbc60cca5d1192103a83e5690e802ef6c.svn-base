package com.lanen.view.clinicaltest;

/**
 * 串口监听事件类（尿液数据接收）
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.lanen.base.BaseService;
import com.lanen.model.clinicaltest.TblClinicalTestData;
import com.lanen.model.clinicaltest.TblDataSource;
import com.lanen.view.clinicaltest.SerialPortEventListener_bloodCoag.Task_UpdateTable;
import com.lanen.view.clinicaltest.SerialPortEventListener_bloodCoag.Task_showMsg;

public class SerialPortEventListener_urine implements SerialPortEventListener {

	private int testItem = 0;// 检测项目
	private String instrumentId = ""; // 设备Id
	private TblDataSource tblDataSource = null; // 数据源信息

	// 输入流
	private InputStream inputStream;
	// 输出流
	private OutputStream outputStream;
	// 用于存放刚接收的一个16进制数
	private int numBytes = 0;
	// 表示接收到第几块数据
	private int choose = 0; // 0：还没判定
	// 用于存放CPT-004接收到的每块数据
	private String oneStr = "";
	private String twoStr = "";
	private String threeStr = "";
	private String fourStr = "";
	
	// 用于存放CPT-010接收到的每块数据
	String dataStr = "";

	public SerialPortEventListener_urine() {
	}

	public SerialPortEventListener_urine(int testItem, String instrumentId,
			TblDataSource tblDataSource) {
		this.testItem = testItem;
		this.instrumentId = instrumentId;
		this.tblDataSource = tblDataSource;
	}

	@Override
	protected void finalize() throws Throwable {
		if (null != inputStream) {
			inputStream.close();
			System.out.println("输入/输出流i");
		}
		if (null != outputStream) {
			outputStream.close();
			System.out.println("输入/输出流o");
		}
		System.out.println("输入/输出流");
		super.finalize();
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		SerialPort serialPort = (SerialPort) event.getSource();
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:

			try {
				// 关键哦
				inputStream = serialPort.getInputStream();

				outputStream = serialPort.getOutputStream();

			} catch (IOException e) {
				e.printStackTrace();
			}
			if("CPT-004".equals(instrumentId))
				dealCPT004Data();
			if("CPT-010".equals(instrumentId))
				dealCPT010Data();
			
			break;
		}
	}
	
	//CPT-010
	public void dealCPT010Data()
	{
		try {
			// 从线路上读取数据流
			while (inputStream.available() > 0) {
				numBytes = inputStream.read();

				// 0x02 正文开始
				if (numBytes == 0X02) {
					choose++;
				}
				// 0x03 正文结束
				if(numBytes == 0X03){
					//System.out.println("dataStr===="+dataStr);
					
					String specimenCode = "";
					String testData = "";
					String collectionTime = "";
					List<String[]> list=new ArrayList<String[]>();
					String[] strs;
					System.out.println("length = "+dataStr.length());
					if(!(dataStr.length()<305)){	
						specimenCode = dataStr.substring(9,9+4);//六位的前两位不要，只要后四位。应该是从7开始取。
						System.out.print("检验编号'"+specimenCode+"'");
						
						collectionTime=dataStr.substring(15, 15+10+23).replaceAll("-","").replaceAll("\n","").replaceAll("\r", "").replaceAll(" ","").replaceAll(":","");
						System.out.println("检测完成时间'"+collectionTime+"'");
						//下面是各个指标的值
						for(int i=0;i<11;i++)
						{
							strs=new String[3];
							strs[0]=dataStr.substring(53+i*23,53+i*23+3).trim();
							
							if(53+i*23+23<dataStr.length())
								strs[2]=dataStr.substring(53+i*23+7,53+i*23+23).trim();
							else
								strs[2]=dataStr.substring(53+i*23+7,dataStr.length()).trim();
							testData=dataStr.substring(53+i*23+4,53+i*23+6).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=strs[2];
								strs[2]="";
							}
							list.add(strs);
							System.out.println(strs[0]+"    "+strs[1]+"    "+strs[2]);
						}
								
							
						
						//保存数据
						int i = 0;
						List<TblClinicalTestData> tblClinicalTestDataList = null;
						while(i < 3){
							try {
								tblClinicalTestDataList =BaseService.getTblClinicalTestDataService()
										.saveAllBySpecimenCodeTestItem(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list);
							} catch (Exception e) {
								i++;
								if(i < 3){
									continue;
								}else{
									List<String> msgList = new ArrayList<String>();
									for(String[] str :list){
										msgList.add("数据："+specimenCode+", "+str[0]+", "+ str[1] +" 未接收成功！");
									}
									//提示未保存成功数据的 检验编号 通道号
									Platform.runLater(new Task_showMsg(msgList));
									e.printStackTrace();
								}
							}
							
							i = 3;
						}
						
						if(null!=tblClinicalTestDataList && tblClinicalTestDataList.size()>0){
							Platform.runLater(new Task_UpdateTable(tblClinicalTestDataList));
						}

//						Platform.runLater(new Task(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list));
						
					}else{
						System.out.println("这条数据无效");
					}
					choose=0;
					dataStr = "";
					continue;
				}
				if (choose > 0) {
					char a = (char) numBytes;
					dataStr = dataStr + a;
				}
			}// end while

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//CPT-004
		public void dealCPT004Data()
		{
			try {
				// 从线路上读取数据流
				while (inputStream.available() > 0) {
					numBytes = inputStream.read();

					// 0x02 正文开始
					if (numBytes == 0X02) {
						choose++;
//						continue;
					}
					// 0x03 正文结束
					if(numBytes == 0X03){
						
						String specimenCode = "";
//						String passageway = "";
						String testData = "";
//						String testIndexUnit = "";
						String collectionTime = "";
						List<String[]> list=new ArrayList<String[]>();
						String[] strs;
						if(!(oneStr.length()<75||twoStr.length()<151||threeStr.length()<151||fourStr.length()<68)){
							//第一组块
//							System.out.println("第一块"+oneStr);
//							System.out.print("yyyy/MM/dd"+oneStr.substring(33, 43));
//							System.out.print("HH:mm"+oneStr.substring(44, 49));
//							System.out.println("检验编号"+oneStr.substring(53, 57));
							specimenCode = oneStr.substring(53, 57);
							System.out.print("检验编号"+specimenCode);
							collectionTime=oneStr.substring(33, 43).replaceAll("/", "")+oneStr.substring(44, 49).replaceFirst(":", "")+"00";
							System.out.println("检测完成时间"+collectionTime);
							//第二组块
//							System.out.println("第二块"+twoStr);
//							System.out.println(twoStr.substring(1,5)+"    "+twoStr.substring(20,26));
							strs=new String[3];
							strs[0]=twoStr.substring(1,5).trim();
							strs[2]=twoStr.substring(20,26).trim();
							testData=twoStr.substring(8,14).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(14,20).trim();
							}
							list.add(strs);//1
//							System.out.println(twoStr.substring(26,31)+"    "+twoStr.substring(45,51));
							strs=new String[3];
							strs[0]=twoStr.substring(26,31).trim();
							strs[2]=twoStr.substring(45,51).trim();
							testData=twoStr.substring(33,39).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(39,45).trim();
							}
							list.add(strs);//2
//							System.out.println(twoStr.substring(51,56)+"    "+twoStr.substring(70,76));
							strs=new String[3];
							strs[0]=twoStr.substring(51,56).trim();
							strs[2]=twoStr.substring(70,76).trim();
							testData=twoStr.substring(58,64).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(64,70).trim();
							}
							list.add(strs);//3
//							System.out.println(twoStr.substring(76,81)+"    "+twoStr.substring(95,101));
							strs=new String[3];
							strs[0]=twoStr.substring(76,81).trim();
							strs[2]=twoStr.substring(95,101).trim();
							testData=twoStr.substring(83,89).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(89,95).trim();
							}
							list.add(strs);//4
//							System.out.println(twoStr.substring(101,105)+"    "+twoStr.substring(120,126));
//							System.out.println(101126+twoStr.substring(100,126));
							strs=new String[3];
							strs[0]=twoStr.substring(101,105).trim();
							strs[2]=twoStr.substring(120,126).trim();
							testData=twoStr.substring(108,114).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(114,120).trim();
							}
							list.add(strs);//5
//							System.out.println(twoStr.substring(126,131)+"    "+twoStr.substring(145,151));
							strs=new String[3];
							strs[0]=twoStr.substring(126,131).trim();
							strs[2]=twoStr.substring(145,151).trim();
							testData=twoStr.substring(133,139).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=twoStr.substring(139,145).trim();
							}
							list.add(strs);//6
							//第三组块
//							System.out.println("第三块"+threeStr);
//							System.out.println(threeStr.substring(1,5)+"    "+threeStr.substring(20,26));
							strs=new String[3];
							strs[0]=threeStr.substring(1,5).trim();
							strs[2]=threeStr.substring(20,26).trim();
							testData=threeStr.substring(8,14).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=threeStr.substring(14,20).trim();
							}
							list.add(strs);//7
//							System.out.println(threeStr.substring(26,31)+"    "+threeStr.substring(45,51));
							strs=new String[3];
							strs[0]=threeStr.substring(26,31).trim();
							strs[2]=threeStr.substring(45,51).trim();
							testData=threeStr.substring(33,39).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=threeStr.substring(39,45).trim();
							}
							list.add(strs);//8
//							System.out.println(threeStr.substring(51,56)+"    "+threeStr.substring(70,76));
							strs=new String[3];
							strs[0]=threeStr.substring(51,56).trim();
							strs[2]=threeStr.substring(70,76).trim();
							testData=threeStr.substring(58,64).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=threeStr.substring(64,70).trim();
							}
							list.add(strs);//9
//							System.out.println(threeStr.substring(76,81)+"    "+threeStr.substring(95,101));
							strs=new String[3];
							strs[0]=threeStr.substring(76,81).trim();
							strs[2]=threeStr.substring(95,101).trim();
							testData=threeStr.substring(83,89).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=threeStr.substring(89,95).trim();
							}
							list.add(strs);//10
//							System.out.println(threeStr.substring(101,105)+"    "+threeStr.substring(120,126));
							strs=new String[3];
							strs[0]=threeStr.substring(101,105).trim();
							strs[2]=threeStr.substring(120,126).trim();
							testData=threeStr.substring(108,114).trim();
							if(!"".equals(testData)){
								strs[1]=testData;
								testData="";
							}else{
								strs[1]=threeStr.substring(114,120).trim();
							}
							list.add(strs);//11
							//浊度检测
//							System.out.println(threeStr.substring(126,131)+"    "+threeStr.substring(145,151));
//							strs=new String[3];
//							strs[0]=threeStr.substring(126,131).trim();
//							strs[2]=threeStr.substring(145,151).trim();
//							testData=threeStr.substring(133,139).trim();
//							if(!"".equals(testData)){
//								strs[1]=testData;
//								testData="";
//							}else{
//								strs[1]=threeStr.substring(139,145).trim();
//							}
//							list.add(strs);
							strs=new String[3];
							strs[0]=threeStr.substring(126,130).trim();
							strs[1]=threeStr.substring(133,147).trim();
							strs[2]="";
							list.add(strs);//12
							
							//第四组块
//							System.out.println("第四块"+fourStr);
							//比重
							strs=new String[3];
							strs[0]=fourStr.substring(1,5).trim();
							strs[1]=fourStr.substring(14,26).trim();
							strs[2]="";
							list.add(strs);
							//色调
							strs=new String[3];
							strs[0]="COLOR";
							strs[1]=fourStr.substring(27,39).trim();
							strs[2]="";
							list.add(strs);
							// 保存数据
							if(list.size()>0){
								for(String[] str :list){
									if(!"".equals(str[0])){
										System.out.println(str[0]+"    "+str[1]+"    "+str[2]);
//										final TblClinicalTestData tblClinicalTestData = BaseService
//												.getTblClinicalTestDataService()
//												.saveBySpecimenCodeTestItemPassageway(specimenCode, testItem,
//														instrumentId, str[0], str[1], str[2],
//														collectionTime, tblDataSource);
//										if (null != tblClinicalTestData) {
//											Platform.runLater(new Runnable() {
//												
//												@Override
//												public void run() {
//													DataAcceptFrameController
//													.updateTableDate(tblClinicalTestData);
//												}
//											});
//										}
									}
									
								}
							}
							//保存数据
							int i = 0;
							List<TblClinicalTestData> tblClinicalTestDataList = null;
							while(i < 3){
								try {
									tblClinicalTestDataList =BaseService.getTblClinicalTestDataService()
											.saveAllBySpecimenCodeTestItem(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list);
								} catch (Exception e) {
									i++;
									if(i < 3){
										continue;
									}else{
										List<String> msgList = new ArrayList<String>();
										for(String[] str :list){
											msgList.add("数据："+specimenCode+", "+str[0]+", "+ str[1] +" 未接收成功！");
										}
										//提示未保存成功数据的 检验编号 通道号
										Platform.runLater(new Task_showMsg(msgList));
										e.printStackTrace();
									}
								}
								
								i = 3;
							}
							
							if(null!=tblClinicalTestDataList && tblClinicalTestDataList.size()>0){
								Platform.runLater(new Task_UpdateTable(tblClinicalTestDataList));
							}
//							Map<String,Object> map =BaseService.getTblClinicalTestDataService()
//									.saveAllBySpecimenCodeTestItem(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list);
//							if(null != map){
//								Platform.runLater(new Task(map));
//							}
//							final List<TblClinicalTestData> tblClinicalTestDataList =BaseService.getTblClinicalTestDataService()
//									.saveAllBySpecimenCodeTestItem(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list);
//							if(null!=tblClinicalTestDataList && tblClinicalTestDataList.size()>0){
//									Platform.runLater(new Runnable(){
//										@Override
//										public void run() {
//											for(final TblClinicalTestData obj:tblClinicalTestDataList){
//												DataAcceptFrameController.getInstance().updateTableDate(obj);
//											}
//										}
//										
//									});
//							}
//							Platform.runLater(new Task(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list));
							
						}
						choose=0;
						oneStr = "";
						twoStr = "";
						threeStr = "";
						fourStr = "";
						continue;
					}
					if (choose > 0) {
						char a = (char) numBytes;
						switch (choose) {
						case 1:
							oneStr = oneStr + a;
							break;
						case 2:
							twoStr = twoStr + a;
							break;
						case 3:
							threeStr = threeStr + a;
							break;
						case 4:
							fourStr = fourStr + a;
							break;
						default:
							break;
						}
					}
				}// end while

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
