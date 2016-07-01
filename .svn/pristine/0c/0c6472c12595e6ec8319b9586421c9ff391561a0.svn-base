package com.lanen.view.balreg;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.lanen.base.BaseService;
import com.lanen.model.path.TblCounterpoise;
import com.lanen.util.NumberValidationUtils;
import com.lanen.view.balreg.TblBalCalibrationIndexController.TblBalCalibrationTable;

public class SerialPortEventListener_bodyWeight implements SerialPortEventListener{
//	private ObservableList<TblBalCalibrationTable> data_tblBalCalibrationTable= FXCollections.observableArrayList();
//    private TblCounterpoise  tblCounterpoise;
//	private String cpCode  = "" ; //设备Id

	private String weight ;
	private String weightUnit;
	private String collectData="";
	private SerialPort serialPort;
	
	//输入流
	private InputStream inputStream;  
	
	//用于存放刚接收的一个16进制数
	private int newByte=0; 
	//用于存放上一个接手的数
	private int oldByte=0;       //  为  判断  0D  0A
	
	public SerialPortEventListener_bodyWeight(){}
//	public SerialPortEventListener_bodyWeight(TblCounterpoise tblCounterpoise,ObservableList<TblBalCalibrationTable> TblBalCalibrationTable){
//		this.tblCounterpoise = tblCounterpoise;
//		this.data_tblBalCalibrationTable=TblBalCalibrationTable;
//	}
	 @Override
		protected void finalize() throws Throwable {
			if(null!=inputStream){
				inputStream.close();
				System.out.println("输入流关闭");
			}
			super.finalize();
		}
	@Override
	public void serialEvent(SerialPortEvent event) {
		serialPort=(SerialPort) event.getSource();
				
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
		    			
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		} 
		    		try {
		    			//从线路上读取数据流
		    			while (inputStream.available() > 0) {
		    				newByte= inputStream.read();
//		    				System.out.println(newByte);
		    				char a= (char) newByte;
//		    				//打印接收到的数据
//		    				if(newByte==0X0D){
//		    					oldByte=0X0D;
//		    					continue;
//		    				}
		    				if(newByte==0X0D){
		    					oldByte=0X0D;
		    					continue;
		    				}
//		    				if(newByte==0X0A && oldByte==0x0D){
		    				if(a == 'g' ){
		    					collectData = collectData+a;
		    					int unitIndex=collectData.indexOf("g");
		    					int numberIndex=0;
		    					for(int i=0;i<collectData.length();i++){
		    						char x=collectData.charAt(i);
		    						if(Character.isDigit(x)==true || '-' == x){
		    							numberIndex=i;
		    							break;
		    						}
		    					}
		    					if(unitIndex> -1 && numberIndex> -1){
		    						collectData=collectData.substring(numberIndex, unitIndex).trim();
//		    					collectData = collectData.trim();
//		    					String [] t = collectData.split(" ");
//		    					System.out.println(collectData);
		    						weight = collectData;
		    						weightUnit = "g";
		    						if(NumberValidationUtils.isRealNumber(weight)){
		    							Platform.runLater(new Runnable() {
		    								
		    								@Override
		    								public void run() {
		    									AddTblBalCalibration.getInstance().addBalCalibration(weight);
		    								}
		    							});
		    						}
		    					}
								System.out.println(weight + "-----" + weightUnit);
		    					newByte=0;
		    					oldByte=0;
		    					collectData="";
		    					continue;
		    				}
		    				collectData = collectData+a;
		    				oldByte = newByte;
		    					
		    			
		    			}// end while
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		            break;
		        }
				
		}


}
