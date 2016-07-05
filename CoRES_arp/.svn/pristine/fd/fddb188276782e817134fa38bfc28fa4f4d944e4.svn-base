package com.lanen.view;
/**
 * 串口监听事件类（生化数据接收）
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.application.Platform;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import com.lanen.base.BaseService;

public class SerialPortEventListener_monkeyId implements SerialPortEventListener {
	
	
   

	private  static  int testItem  = 0 ;//检测项目
	private   String instrumentId  = "" ; //设备Id

	private SerialPort serialPort;
	
	//输入流
	private InputStream inputStream;   
	//输出流
	private OutputStream outputStream; 
	//用于存放刚接收的一个10进制数
	private int numBytes=0;
	//接收的长度
	private int count=0;
	//用于接收存放数据
	private String newData="";
	public SerialPortEventListener_monkeyId(){}
	public SerialPortEventListener_monkeyId(int testItem,String instrumentId){
		super();
		this.testItem=testItem;
		this.instrumentId=instrumentId;
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
    			//关键
    			inputStream = serialPort.getInputStream();
    			
    			outputStream = serialPort.getOutputStream();
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
    		try {
    			//从线路上读取数据流---它只能保证最多读取这么多个字节(最少1个)--比read()效率高
    			byte[] readBuffer = new byte[950];
    			/*while(inputStream.available()>0&&count<950){
    				count+=inputStream.read(readBuffer, count, 950-count);
    			}*/
    			while (inputStream.available() > 0) {
    				//numBytes=inputStream.read(readBuffer);
    				numBytes=inputStream.read();
    				char a=(char)numBytes;
    				newData=newData+a;
    				count++;
    				System.err.println(numBytes);
    				System.out.println(a);
    				if(count%31==0){
    					System.err.println(newData+"====");
    					String str1 = newData.replaceAll(" +", "");

						if (str1 != null) {
							String chipid = str1.substring(5, 20);
							System.err.println(chipid + "---------");
							Platform.runLater(new Task_bioChem(chipid));
						} else {
							Platform.runLater(new Task_msg(newData + ":"
									+ "接收数据格式有误"));
						}
					}
    				  
    			}		
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            break;
        }
	}
	/**保存数据后更新TableView数据
	 * @author Administrator
	 *
	 */
	static class Task_bioChem implements Runnable{
		
		private String monkeyIdData;
		
		public Task_bioChem(String monkeyIdData){
			String monkeyid=BaseService.getIndividualService().getMonkeyIdByChipId(monkeyIdData);
			this.monkeyIdData = monkeyid;
		}
		@Override
		public void run() {
			if(null!=monkeyIdData&&testItem==1){
				//更新动物编号框
				AnimalsWeightFrameController.getInstance().updateTableDate(monkeyIdData);
				//更新过往称重记录
				AnimalsWeightFrameController.getInstance().updateTableData(monkeyIdData);
            }else if(null!=monkeyIdData&&testItem==2){
            	//更新动物编号框
				AnimalsObservationFrameController.getInstance().updateTableDate(monkeyIdData);
				//更新过往观察记录
				AnimalsObservationFrameController.getInstance().updateTableData(monkeyIdData);
            }else{
            	//RFID查询
            	AnimalsInfoFrameController.getInstance().updateTableDate(monkeyIdData);
            }
		}
	}
	
	static class Task_msg implements Runnable{

		private String msg ;
		public Task_msg(String msg){
			this.msg = msg;
		}
		@Override
		public void run() {
			if(testItem==1){
				AnimalsWeightFrameController.getInstance().addMsgList(msg);
			}else if(testItem==2){
				AnimalsObservationFrameController.getInstance().addMsgList(msg);
			}else{
				AnimalsInfoFrameController.getInstance().addMsgList(msg);
			}
			
			
		}
		
	}
}
