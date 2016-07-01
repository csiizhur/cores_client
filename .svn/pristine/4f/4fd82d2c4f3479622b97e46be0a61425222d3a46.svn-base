package com.lanen.view.test;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;

import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

/**动物芯片查询
 * @author Administrator
 *
 */
public class SerialPortEventListener_tempReqPage implements SerialPortEventListener{

	private String iCardCode ;
	private String collectData="";
	private SerialPort serialPort;
	
	//输入流
	private InputStream inputStream;  
	
	//用于存放刚接收的一个16进制数
	private int newByte=0; 
	
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
    				char a= (char) newByte;
    				//打印接收到的数据
    				if(newByte==0X0D){
    					iCardCode = collectData.trim();
    					int i,endIndex;
    					i = iCardCode.indexOf("FDXB");
    					endIndex = iCardCode.indexOf("*");
    					if(i > -1 && endIndex > -1 && endIndex > i+4){
    						iCardCode = iCardCode.substring(i+4,endIndex);
    						iCardCode = iCardCode.trim();
    						iCardCode = iCardCode.replace(" ","");
    					}
    					System.out.println("接收到数据为:"+iCardCode);
						Platform.runLater(new Task(iCardCode));
    					newByte=0;
    					iCardCode = "";
    					collectData="";
    					continue;
    				}
    				collectData = collectData+a;
    					
    			
    			}// end while
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            break;
        }
		
	}
	static class Task implements Runnable{
		private String iCardCode;
		public Task(String iCardCode){
			this.iCardCode = iCardCode;
		}
		@Override
		public void run() {
			TempReqPage.getInstance().selectAnimalByICardCode(iCardCode);
		}
	}
}