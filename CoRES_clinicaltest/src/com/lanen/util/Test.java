package com.lanen.util;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new BaseService();
//		
//		Thread thread = new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				String id = 	BaseService.getGetIdService().getKey("TblClinicalTestData");
//				System.out.println("1:"+id);
//			}
//		});
//		Thread thread2 = new Thread(new Runnable(){
//			
//			@Override
//			public void run() {
//				String id = 	BaseService.getGetIdService().getKey("TblClinicalTestData");
//				System.out.println("2:"+id);
//			}
//		});
//
//		thread.start();
//		thread2.start();
		int i = 0;
		while(i < 3){
			try {
				int j =1;
			} catch (Exception e) {
				i++;
				if(i < 3){
					continue;
				}else{
					//提示未保存成功数据的 检验编号 通道号
//					e.printStackTrace();
				}
				
			}
				i = 3;
				System.out.println("111");
		}
	}

}
