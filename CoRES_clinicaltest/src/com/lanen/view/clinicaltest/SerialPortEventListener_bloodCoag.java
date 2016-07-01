package com.lanen.view.clinicaltest;
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

/**
 * 串口监听事件类（血凝数据接收）
 */
public class SerialPortEventListener_bloodCoag implements SerialPortEventListener {
	
	
   

	private    int testItem  = 0 ;//检测项目
	private   String instrumentId  = "" ; //设备Id
	private   TblDataSource tblDataSource =null;    //数据源信息

	//输入流
	private InputStream inputStream;    
	//输出流
	private OutputStream outputStream; 
	//用于存放刚接收的一个16进制数
	private int numBytes=0; 
	//用于存放上一个接手的数
	private int oldByte=0;       //  为  判断  0D  0A
	//表示一行数据已接收了几个
	private int start=3;//  第三个为   H,P,C,O,R,L等标志性字符
	//表示接收到第几行数据
	private int choose=-1;   //0：还没判定    1：表示  H  2：表示P 3：表示O 4：R （第一个）  5：R （第二个）  6：L
	//用于存放接收到的每行数据
	private String hStr="";
	private String pStr="";
	private String cStr="";
	private String oStr="";
	private String rStr="";
	private List<String> rList= new ArrayList<String>();
	public SerialPortEventListener_bloodCoag(){}
	public SerialPortEventListener_bloodCoag(int testItem,String instrumentId,TblDataSource tblDataSource){
		this.testItem=testItem;
		this.instrumentId=instrumentId;
		this.tblDataSource=tblDataSource;
	}
	 @Override
		protected void finalize() throws Throwable {
			if(null!=inputStream){
				inputStream.close();
				System.out.println("输入/输出流i");
			}
			if(null!=outputStream){
				outputStream.close();
				System.out.println("输入/输出流o");
			}
			System.out.println("输入/输出流");
			super.finalize();
		}
	@Override
	public void serialEvent(SerialPortEvent event) {
		SerialPort serialPort=(SerialPort) event.getSource();
		switch(event.getEventType()) {
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
    			//关键哦
    			inputStream = serialPort.getInputStream();
    			
    			outputStream = serialPort.getOutputStream();
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
    		try {
    			//从线路上读取数据流
    			while (inputStream.available() > 0) {
    				numBytes= inputStream.read();
    				//接收到0x05   回复0x06 
    				if(numBytes==0x05){
        				outputStream.write(0x06);
        				continue;
        			}
    				//接收到0x04 （正文结束）
    				if(numBytes==0x04){
    					continue;
    				}
    				
    				if(numBytes==0X0D){
    					oldByte=0X0D;
    					continue;
    				}
    				//接收到0x0D,0x0A    回复0x06
    				if(numBytes==0X0A&&oldByte==0x0D){
    					oldByte=0;
    					outputStream.write(0x06);
        				continue;
    				}
    				start++;
    				//0x02 正文开始
    				if(numBytes==0X02){
    					start=0;
    					choose=0;
    				}else{//非正文开头
    					if(choose==0){//还没判定
    						if(start==2){//
    							if(numBytes==0X48){//H
    								choose=1;
    							}else if(numBytes==0X50){//P
    								choose=2;
    							}else if(numBytes==0X43){//C
    								choose=3;
    							}else if(numBytes==0X4F){//O
    								choose=4;
    							}else if(numBytes==0X52){//R
    								choose=5;
    								if(!"".equals(rStr)){
    									rList.add(rStr);
    									rStr="";
    								}
    							}else if(numBytes==0X4C){//L
    								choose=6;
    							}
    						}
    					}else if(choose>0){
    						char a= (char) numBytes;
    						oldByte=numBytes;
    						switch (choose) {
							case 1:hStr=hStr+a;break;
							case 2:pStr=pStr+a;break;
							case 3:cStr=cStr+a;break;
							case 4:oStr=oStr+a;break;
							case 5:rStr=rStr+a;break;
							case 6:
								rList.add(rStr);
								choose=-1;
								String specimenCode="";
								String passageway="";
								String testData="";
								String testIndexUnit="";
								String collectionTime="";
//								System.out.println(headerStr);
//								System.out.println(patientStr);
								//O   
//								int index=oStr.indexOf("^    ");
//								if(index>-1){
//								oStr=oStr.substring(index+5);
//								specimenCode=oStr.substring(0,oStr.indexOf("^"));//检验编号
//								}
								int index=oStr.indexOf("|1|");
								if(index>-1){
									oStr=oStr.substring(index+3);
									specimenCode=oStr.substring(0,oStr.indexOf("|"));//检验编号
								}
								System.out.print("检验编号"+specimenCode);
								System.out.println("共"+rList.size()+"条数据");
								List<String[]> list= new ArrayList<String[]>();
								String[] strs ;
								if(rList.size()>0){
									//R
									for(String str:rList){
										strs = new String[4];
										System.out.println(str);
										index=-1;
//										index=str.indexOf("^^^^R_");
//										if(index>-1){
//											str=str.substring(index+6);
//											if(str.indexOf("^1|")<0){
//												continue;
//											}
//											passageway=str.substring(0,str.indexOf("^1|"));//通道号
//											
//										}
										index=str.indexOf("^^^");
										if(index>-1){
											str=str.substring(index+3);
											passageway=str.substring(0,str.indexOf("|"));//通道号
										}
										strs[0]=passageway;
										System.out.print("通道号"+passageway);
										
										index=-1;
//										index=str.indexOf("^1|");
//										if(index>-1){
//											str=str.substring(index+3);
//											if(str.indexOf("|")>-1){
//												testData=str.substring(0,str.indexOf("|"));//数据
//											}
//										}
										index=str.indexOf("|");
										if(index>-1){
											str=str.substring(index+1);
											testData=str.substring(0,str.indexOf("|"));//数据
										}
										strs[1]=testData;
										System.out.print("数据"+testData);
										
										index=-1;
//										index=str.indexOf("|");
//										if(index>-1){
//											str=str.substring(index+1);
//											if(str.indexOf("|")>-1){
//												testIndexUnit=str.substring(0,str.indexOf("|"));//单位
//											}
//										}
										index=str.indexOf("|");
										if(index>-1){
											str=str.substring(index+1);
											testIndexUnit=str.substring(0,str.indexOf("|"));//单位
										}
										strs[2]=testIndexUnit;
										System.out.println("单位"+testIndexUnit);
//										if(collectionTime.equals("")){
//											index=-1;
//											index=str.indexOf("xueyuanxu||");
//											if(index>-1){
//												str=str.substring(index+11);
//												if(str.length()>14){
//													collectionTime=str.substring(0,14);//检测完成时间
//												}
//											}
//											System.out.println("检测完成时间"+collectionTime);
											index=-1;
											index=str.indexOf("||||F||||");
											if(index>-1){
												str=str.substring(index+9);
												collectionTime=str.substring(0,14);//检测完成时间
											}
											strs[3]=collectionTime;
											System.out.println("检测完成时间"+collectionTime);
//										}
										list.add(strs);
										passageway="";
										testData="";
										testIndexUnit="";
										collectionTime="";
										
									}
								}
								//保存数据
								int i = 0;
								List<TblClinicalTestData> tblClinicalTestDataList = null;
								while(i < 3){
									try {
										tblClinicalTestDataList =BaseService.getTblClinicalTestDataService()
												.saveAllBySpecimenCodeTestItem_3(specimenCode,instrumentId,tblDataSource,list);
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
								
//								Platform.runLater(new Task(specimenCode,testItem,instrumentId,collectionTime,tblDataSource,list));
								hStr="";
		    	    			pStr="";
		    	    			oStr="";
		    	    			rStr="";
		    	    			cStr="";
		    	    			System.out.println("rList 长度："+rList.size());
		    	    			rList.clear();
								break;
							default:
								break;
							}
    					}
    					
    				}//end  else(非正文开头)
    			}// end while
    		
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            break;
        }
	}
	static class Task_showMsg implements Runnable{
		private List<String> msgList;
		
		public Task_showMsg(List<String> msgList ){
				this.msgList = msgList;
		}
		@Override
		public void run() {
			if(null != msgList && msgList.size() > 0){
				for(String str : msgList){
					DataAcceptFrameController.getInstance().addMsgList(str);
				}
			}
		}
	}
	static class Task_UpdateTable implements Runnable{
		private List<TblClinicalTestData> tblClinicalTestDataList;
		
		public Task_UpdateTable(List<TblClinicalTestData> tblClinicalTestDataList){
				this.tblClinicalTestDataList = tblClinicalTestDataList;
		}
		@Override
		public void run() {
			if(null!=tblClinicalTestDataList && tblClinicalTestDataList.size()>0){
				for(final TblClinicalTestData obj:tblClinicalTestDataList){
					DataAcceptFrameController.getInstance().updateTableDate(obj);
				}
			}
		}
	}
}
