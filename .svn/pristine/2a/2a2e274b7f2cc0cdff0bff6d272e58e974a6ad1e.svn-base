//package com.lanen.view.clinicaltest;
///**
// * 串口监听事件类（血凝数据接收）
// */
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javafx.application.Platform;
//
//import javax.comm.SerialPort;
//import javax.comm.SerialPortEvent;
//import javax.comm.SerialPortEventListener;
//
//import com.lanen.base.BaseService;
//import com.lanen.model.clinicaltest.TblClinicalTestData;
//import com.lanen.model.clinicaltest.TblDataSource;
//
//public class SerialPortEventListener_bloodCoag_old implements SerialPortEventListener {
//	
//	
//   
//
//	private    int testItem  = 0 ;//检测项目
//	private   String instrumentId  = "" ; //设备Id
//	private   TblDataSource tblDataSource =null;    //数据源信息
//
//	//输入流
//	private InputStream inputStream;    
//	//输出流
//	private OutputStream outputStream; 
//	//用于存放刚接收的一个16进制数
//	private int numBytes=0; 
//	//用于存放上一个接手的数
//	private int oldByte=0;       //  为  判断  0D  0A
//	//表示一行数据已接收了几个
//	private int start=3;//  第三个为   H,P,O,R,L等标志性字符
//	//表示接收到第几行数据
//	private int choose=-1;   //0：还没判定    1：表示  H  2：表示P 3：表示O 4：R （第一个）  5：R （第二个）  6：L
//	private int oldChoose=0; 
//	//用于存放接收到的每行数据
//	private String headerStr="";
//	private String patientStr="";
//	private String orderStr="";
//	private String resultStr1="";
//	private String mStr="";
//	private String resultStr2="";
////	private String lStr="";
//	public SerialPortEventListener_bloodCoag_old(){}
//	public SerialPortEventListener_bloodCoag_old(int testItem,String instrumentId,TblDataSource tblDataSource){
//		this.testItem=testItem;
//		this.instrumentId=instrumentId;
//		this.tblDataSource=tblDataSource;
//	}
//	 @Override
//		protected void finalize() throws Throwable {
//			if(null!=inputStream){
//				inputStream.close();
//				System.out.println("输入/输出流i");
//			}
//			if(null!=outputStream){
//				outputStream.close();
//				System.out.println("输入/输出流o");
//			}
//			System.out.println("输入/输出流");
//			super.finalize();
//		}
//	@Override
//	public void serialEvent(SerialPortEvent event) {
//		SerialPort serialPort=(SerialPort) event.getSource();
//		switch(event.getEventType()) {
//        case SerialPortEvent.BI:
//        case SerialPortEvent.OE:
//        case SerialPortEvent.FE:
//        case SerialPortEvent.PE:
//        case SerialPortEvent.CD:
//        case SerialPortEvent.CTS:
//        case SerialPortEvent.DSR:
//        case SerialPortEvent.RI:
//        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
//            break;
//        case SerialPortEvent.DATA_AVAILABLE:
//    		
//    		try {
//    			//关键哦
//    			inputStream = serialPort.getInputStream();
//    			
//    			outputStream = serialPort.getOutputStream();
//    			
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		} 
//    		try {
//    			//从线路上读取数据流
//    			while (inputStream.available() > 0) {
//    				numBytes= inputStream.read();
//    				//接收到0x05   回复0x06 
//    				if(numBytes==0x05){
//        				outputStream.write(0x06);
//        				continue;
//        			}
//    				
//    				if(numBytes==0X0D){
//    					oldByte=0X0D;
//    					continue;
//    				}
//    				//接收到0x0D,0x0A    回复0x06
//    				if(numBytes==0X0A&&oldByte==0x0D){
//    					oldByte=0;
//    					outputStream.write(0x06);
//        				continue;
//    				}
//    				start++;
//    				//0x02 正文开始
//    				if(numBytes==0X02){
//    					start=0;
//    					oldChoose=choose;
//    					choose=0;
//    				}else{//非正文开头
//    					if(choose==0){//还没判定
//    						if(start==2){//
//    							if(numBytes==0X48){//H
//    								choose=1;
//    							}else if(numBytes==0X50){//P
//    								choose=2;
//    							}else if(numBytes==0X4F){//0
//    								choose=3;
//    							}else if(numBytes==0X52){//R
//    								if(oldChoose==3){
//    									choose=4;//R1
//    								}else{
//    									choose=6;//R2
//    								}
//    							}else if(numBytes==0X4D){//M
//    								choose=5;
//    							}else if(numBytes==0x4C){//L
//    								choose=8;
//    							}
//    						}
//    					}else if(choose>0){
//    						char a= (char) numBytes;
//    						oldByte=numBytes;
//    						switch (choose) {
//							case 1:headerStr=headerStr+a;break;
//							case 2:patientStr=patientStr+a;break;
//							case 3:orderStr=orderStr+a;break;
//							case 4:resultStr1=resultStr1+a;break;
//							case 5:mStr=mStr+a;break;
//							case 6:resultStr2=resultStr2+a;break;
//							case 8:
//								choose=-1;
//								String specimenCode="";
//								String passageway="";
//								String testData="";
//								String testIndexUnit="";
//								String collectionTime="";
////								System.out.println(headerStr);
////								System.out.println(patientStr);
//								//O   
//								int index=orderStr.indexOf("|1|");
//								if(index>-1){
//								orderStr=orderStr.substring(index+3);
//								specimenCode=orderStr.substring(0,orderStr.indexOf("|"));//检验编号
//								}
//								System.out.print("检验编号"+specimenCode);
//								index=-1;
//								//R1
//								index=resultStr1.indexOf("^^^");
//								if(index>-1){
//									resultStr1=resultStr1.substring(index+3);
//									passageway=resultStr1.substring(0,resultStr1.indexOf("|"));//通道号
//								}
//								System.out.print("通道号"+passageway);
//								
//								index=-1;
//								index=resultStr1.indexOf("|");
//								if(index>-1){
//									resultStr1=resultStr1.substring(index+1);
//									testData=resultStr1.substring(0,resultStr1.indexOf("|"));//数据
//								}
//								System.out.print("数据"+testData);
//								
//								index=-1;
//								index=resultStr1.indexOf("|");
//								if(index>-1){
//									resultStr1=resultStr1.substring(index+1);
//									testIndexUnit=resultStr1.substring(0,resultStr1.indexOf("|"));//单位
//								}
//								System.out.print("单位"+testIndexUnit);
//								
//								index=-1;
//								index=resultStr1.indexOf("||||F||||");
//								if(index>-1){
//									resultStr1=resultStr1.substring(index+9);
//									collectionTime=resultStr1.substring(0,14);//检测完成时间
//								}
//								System.out.println("检测完成时间"+collectionTime);
//								
//								
//								//保存数据    
//								final TblClinicalTestData tblClinicalTestData=BaseService.getTblClinicalTestDataService().saveBySpecimenCodeTestItemPassageway(
//										specimenCode,testItem,instrumentId,passageway,testData,	testIndexUnit,collectionTime,tblDataSource);
//                                 if(null!=tblClinicalTestData){
//                                	 Platform.runLater(new Runnable(){
//
//                     					@Override
//                     					public void run() {
//                     						DataAcceptFrameController.updateTableDate(tblClinicalTestData);
//                     					}
//										});
//                                 }
//                                 //R2   不为空，再解析一次，保存一次
//                                 if(!"".equals(resultStr2)){
//                                	 System.out.print("检验编号"+specimenCode);
//     								index=resultStr2.indexOf("^^^");
//     								if(index>-1){
//     									resultStr2=resultStr2.substring(index+3);
//     									passageway=resultStr2.substring(0,resultStr2.indexOf("|"));//通道号
//     								}
//     								System.out.print("通道号"+passageway);
//     								
//     								index=-1;
//     								index=resultStr2.indexOf("|");
//     								if(index>-1){
//     									resultStr2=resultStr2.substring(index+1);
//     									testData=resultStr2.substring(0,resultStr2.indexOf("|"));//数据
//     								}
//     								System.out.print("数据"+testData);
//     								
//     								index=-1;
//     								index=resultStr2.indexOf("|");
//     								if(index>-1){
//     									resultStr2=resultStr2.substring(index+1);
//     									testIndexUnit=resultStr2.substring(0,resultStr2.indexOf("|"));//单位
//     								}
//     								System.out.print("单位"+testIndexUnit);
//     								
//     								index=-1;
//     								index=resultStr2.indexOf("||||F||||");
//     								if(index>-1){
//     									resultStr2=resultStr2.substring(index+9);
//     									collectionTime=resultStr2.substring(0,14);//检测完成时间
//     								}
//     								System.out.println("检测完成时间"+collectionTime);
//     								
//     								
//     								//保存数据    
//     								final TblClinicalTestData tblClinicalTestData2=BaseService.getTblClinicalTestDataService().saveBySpecimenCodeTestItemPassageway(
//     										specimenCode,testItem,instrumentId,passageway,testData,	testIndexUnit,collectionTime,tblDataSource);
//                                      if(null!=tblClinicalTestData2){
//                                     	 Platform.runLater(new Runnable(){
//
//                          					@Override
//                          					public void run() {
//                          						DataAcceptFrameController.updateTableDate(tblClinicalTestData2);
//                          					}
//     										});
//                                      } 
//                                 }
//								headerStr="";
//		    	    			patientStr="";
//		    	    			orderStr="";
//		    	    			resultStr1="";
//		    	    			resultStr2="";
//		    	    			mStr="";
////		    	    			lStr="";
//								break;
//							default:
//								break;
//							}
//    					}
//    					
//    				}//end  else(非正文开头)
//    			}// end while
//    		
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}
//            break;
//        }
//	}
//
//}
